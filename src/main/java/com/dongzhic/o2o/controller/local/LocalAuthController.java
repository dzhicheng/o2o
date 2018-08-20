package com.dongzhic.o2o.controller.local;

import com.dongzhic.o2o.dto.LocalAuthExecution;
import com.dongzhic.o2o.enums.LocalAuthStateEnum;
import com.dongzhic.o2o.exception.LocalAuthOperationException;
import com.dongzhic.o2o.pojo.LocalAuth;
import com.dongzhic.o2o.pojo.PersonInfo;
import com.dongzhic.o2o.service.LocalAuthService;
import com.dongzhic.o2o.util.CodeUtil;
import com.dongzhic.o2o.util.HttpServletRequestUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author dongzc
 * @date 2018/8/18 18:20
 */
@Controller
@RequestMapping(value = "local", method = {RequestMethod.GET, RequestMethod.POST})
public class LocalAuthController {

    private static final Logger logger = LoggerFactory.getLogger(LocalAuthController.class);

    @Autowired
    private LocalAuthService localAuthService;

    /**
     * 将用户信息与平台帐号绑定
     * @param request
     * @return
     */
    @RequestMapping(value = "/bindLocalAuth", method = RequestMethod.POST)
    @ResponseBody
    private Map<String, Object> bindLocalAuth (HttpServletRequest request) {
        Map<String, Object> modalMap = new HashMap<>(16);
        // 验证码校验
        if (!CodeUtil.checkVerifyCode(request)) {
            modalMap.put("success", false);
            modalMap.put("errMsg", "验证码输入错误");
            return modalMap;
        }
        // 获取前台传入的信息
        String userName = HttpServletRequestUtil.getString(request, "userName");
        String password = HttpServletRequestUtil.getString(request, "password");
        PersonInfo user = (PersonInfo) request.getSession().getAttribute("user");
        if (userName != null && password != null && user != null && user.getUserId() != null) {
            try {
                LocalAuth localAuth = new LocalAuth();
                localAuth.setUserName(userName);
                localAuth.setPassword(password);
                localAuth.setPersonInfo(user);

                LocalAuthExecution execution = localAuthService.bindLocalAuth(localAuth);
                if (execution.getState() == LocalAuthStateEnum.SUCCESS.getState()) {
                    modalMap.put("success", true);
                } else {
                    modalMap.put("success", false);
                    modalMap.put("errMsg", execution.getStateInfo());
                }
            } catch (LocalAuthOperationException e) {
                modalMap.put("success", false);
                modalMap.put("errMsg", e.getMessage());
                logger.error("bindLocalAuth, " + e.getMessage());
            }
        } else {
            modalMap.put("success", false);
            modalMap.put("errMsg", "用户名和密码均不能为空");
        }
        return modalMap;
    }

    @RequestMapping(value = "/changeLocalPwd", method = RequestMethod.POST)
    @ResponseBody
    private Map<String, Object> changeLocalPwd (HttpServletRequest request) {
        Map<String, Object> modalMap = new HashMap<>(16);
        // 验证码校验
        if (!CodeUtil.checkVerifyCode(request)) {
            modalMap.put("success", false);
            modalMap.put("errMsg", "验证码输入错误");
            return modalMap;
        }
        // 获取前台传入的信息
        String userName = HttpServletRequestUtil.getString(request, "userName");
        String password = HttpServletRequestUtil.getString(request, "password");
        String newPassword = HttpServletRequestUtil.getString(request, "newPassword");
        // 从session中获取当前用户信息(用户一旦通过微信登录之后，便能获取到用户的信息)
        PersonInfo user = (PersonInfo) request.getSession().getAttribute("user");
        // 非空判断，要求帐号新旧密码以及当前的用户session非空，且新旧密码不相同
        if (userName != null && password != null && newPassword != null &&
             !password.equals(newPassword) && user != null && user.getUserId() != null) {
            // 查看原先帐号，看看与输入的帐号是否一致，不一致则认为是非法操作
            LocalAuth localAuth = localAuthService.getLocalAuthByUserId(user.getUserId());
            if (localAuth != null && localAuth.getUserName().equals(userName)) {
                try {
                    // 修改平台帐号的用户密码
                    LocalAuthExecution execution = localAuthService.modifyLocalAuth(user.getUserId(), userName, password, newPassword);
                    if (execution.getState() == LocalAuthStateEnum.SUCCESS.getState()) {
                        modalMap.put("success", true);
                    } else {
                        modalMap.put("success", false);
                        modalMap.put("errMsg", execution.getStateInfo());
                    }
                } catch (LocalAuthOperationException e) {
                    modalMap.put("success", false);
                    modalMap.put("errMsg", e.getMessage());
                    logger.error("changeLocalPwd, " + e.getMessage());
                }
            } else {
                modalMap.put("success", false);
                modalMap.put("errMsg", "输入的帐号非本次登录的帐号");
            }
        } else {
            modalMap.put("success", false);
            modalMap.put("errMsg", "请输入密码");
        }
        return modalMap;
    }

    @RequestMapping(value = "/loginCheck", method = RequestMethod.POST)
    @ResponseBody
    private Map<String, Object> loginCheck (HttpServletRequest request) {
        Map<String, Object> modalMap = new HashMap<>(16);

        // 获取是否需要进行验证码校验的标识符
        boolean needVerify = HttpServletRequestUtil.getBoolean(request, "needVerify");
        // 验证码校验
        if (needVerify && !CodeUtil.checkVerifyCode(request)) {
            modalMap.put("success", false);
            modalMap.put("errMsg", "验证码输入错误");
            return modalMap;
        }
        String userName = HttpServletRequestUtil.getString(request, "userName");
        String password = HttpServletRequestUtil.getString(request, "password");
        // 非空校验
        if (userName != null && password != null) {
           try {
               // 传入帐号和密码去获取平台帐号信息
               LocalAuth localAuth = localAuthService.getLocalAuthByUserNameAmdPwd(userName, password);
               if (localAuth != null) {
                   modalMap.put("success", true);
                   request.getSession().setAttribute("user", localAuth.getPersonInfo());
               } else {
                   modalMap.put("success", false);
                   modalMap.put("errMsg", "用户名或密码错误");
               }
           } catch (LocalAuthOperationException e) {
               modalMap.put("success", false);
               modalMap.put("errMsg", "loginCheck, " + e.getMessage());
           }
        } else {
            modalMap.put("success", false);
            modalMap.put("errMsg", "用户名和密码均不能为空");
        }
        return modalMap;
    }

    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    @ResponseBody
    private Map<String, Object> logout (HttpServletRequest request) {
        Map<String, Object> modalMap = new HashMap<>(16);
        request.getSession().setAttribute("user", null);
        modalMap.put("success", true);
        return modalMap;
    }




}
