package com.dongzhic.o2o.util;

import com.google.code.kaptcha.Constants;

import javax.servlet.http.HttpServletRequest;

/**
 * 验证码后台处理类
 * @author dongzc
 */
public class CodeUtil {

    public static boolean checkVerifyCode(HttpServletRequest request) {
        String verifyCodeExpected = (String) request.getSession()
                .getAttribute(Constants.KAPTCHA_SESSION_KEY);
        String verifyCodeActualTemp = HttpServletRequestUtil.getString(request, "verifyCodeActual");
        String verifyCodeActual = verifyCodeActualTemp.toUpperCase();
        if (verifyCodeActual == null  || !verifyCodeActual.equals(verifyCodeExpected)) {
            return false;
        }
        return true;
    }
}
