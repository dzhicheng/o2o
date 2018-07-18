package com.dongzhic.o2o.controller.wechat;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 获取已关注微信号用户信息，并作出相应处理
 * 获取关注公众号之后的微信用户信息的接口，如果在微信浏览器里访问
 *  https://open.weixin.qq.com/connect/oauth2/authorize?appid=您的appId&
 *      redirect_uri=http://o2o.dongzhic.com/o2o/weChatlogin/logincheck&role_type=1&
 *      response_type=code&scope=snsapi_userinfo&state=1#wechat_redirect *
 *  则这里将会获取到code,之后再可以通过code获取到access_token 进而获取到用户信息 *
 * @author dongzc
 * @date 2018/7/17 15:59
 */
@Controller
@RequestMapping("/weChatLogin")
public class WeChatLoginController {

}
