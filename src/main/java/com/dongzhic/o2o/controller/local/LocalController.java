package com.dongzhic.o2o.controller.local;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author dongzc
 * @date 2018/8/18 23:42
 */
@Controller
@RequestMapping("/local")
public class LocalController {

    /**
     * 账号绑定跳转
     * @return
     */
    @RequestMapping(value = "accountBind", method = RequestMethod.GET)
    public String accountBind () {
        return "/local/accountBind";
    }

    /**
     * 修改密码页面跳转
     * @return
     */
    @RequestMapping(value = "changePassword", method = RequestMethod.GET)
    public String changePassword () {
        return "/local/changePassword";
    }

    /**
     * 登陆页跳转
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login () {
        return "/local/login";
    }
}
