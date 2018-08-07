package com.dongzhic.o2o.controller.wechat;

import com.dongzhic.o2o.dto.UserAccessToken;
import com.dongzhic.o2o.dto.WeChatAuthExecution;
import com.dongzhic.o2o.dto.WeChatUser;
import com.dongzhic.o2o.enums.WeChatAuthStateEnum;
import com.dongzhic.o2o.pojo.PersonInfo;
import com.dongzhic.o2o.pojo.WeChatAuth;
import com.dongzhic.o2o.service.PersonInfoService;
import com.dongzhic.o2o.service.WeChatAuthService;
import com.dongzhic.o2o.util.wechat.WeChatUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import sun.awt.SunHints;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 获取已关注微信号用户信息，并作出相应处理
 * 获取关注公众号之后的微信用户信息的接口，如果在微信浏览器里访问
 *  https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxe30612cb1fe43904&
 *      redirect_uri=http://o2o.dongzhic.cn/o2o/weChatLogin/loginCheck&role_type=1&
 *      response_type=code&scope=snsapi_userinfo&state=1#wechat_redirect
 *  则这里将会获取到code,之后再可以通过code获取到access_token 进而获取到用户信息
 * @author dongzc
 * @date 2018/7/17 15:59
 */
@Controller
@RequestMapping("/weChatLogin")
public class WeChatLoginController {

    private Logger log = LoggerFactory.getLogger(WeChatController.class);

    /**
     * 用户界面
     */
    private final String FRONTEND = "1";
    /**
     * 商家管理界面
     */
    private final String SHOPEND = "2";

    @Autowired
    private PersonInfoService personInfoService;
    @Autowired
    private WeChatAuthService weChatAuthService;

    @RequestMapping(value = "/loginCheck", method = RequestMethod.GET)
    public String doGet (HttpServletRequest request, HttpServletResponse response) {
        log.debug("weChat login get ...");
        // 获取微信公众号传过来的code，通过code获取access_token，进而获取用户信息
        String code = request.getParameter("code");
        log.debug("weChat code : "+ code);
        // state用来传我们定义的内容，方便程序调用
        String roleType = request.getParameter("state");
        WeChatUser weChatUser = null;
        String openId = null;
        WeChatAuth weChatAuth = null;
        if (null != code ) {
            UserAccessToken token;
            // 通过code获得access_token
            token = WeChatUtil.getUserAccessToken(code);
            log.debug("weChat login token :"+token);
            // 通过token获取AccessToken
            String acessToken = token.getAccessToken();
            // 通过token获的openId
            openId = token.getOpenId();
            // 通过access_token和openId获取用户昵称等信息
            weChatUser = WeChatUtil.getUserInfo(acessToken, openId);
            log.debug("weChat login usr : "+weChatUser.toString());
            request.getSession().setAttribute("openId", openId);
            weChatAuth = weChatAuthService.getWeChatAuthByOpenId(openId);
        }

        if (weChatAuth == null) {
            PersonInfo personInfo = WeChatUtil.getPersonInfoFromRequest(weChatUser);
            weChatAuth = new WeChatAuth();
            weChatAuth.setOpenId(openId);
            if (FRONTEND.equals(roleType)) {
                personInfo.setUserType(1);
            } else {
                personInfo.setUserType(2);
            }
            weChatAuth.setPersonInfo(personInfo);
            WeChatAuthExecution we = weChatAuthService.register(weChatAuth);
            if (we.getState() != WeChatAuthStateEnum.SUCCESS.getState()) {
                return null;
            } else {
                personInfo = personInfoService.getPersonInfoById(weChatAuth.getPersonInfo().getUserId());
                request.getSession().setAttribute("user", personInfo);
            }
        }
        if (FRONTEND.equals(roleType)) {
            return "frontend/index";
        } else {
            return "shopAdmin/shopList";
        }
    }

}
