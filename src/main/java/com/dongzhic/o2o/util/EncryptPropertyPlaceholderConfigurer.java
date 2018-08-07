package com.dongzhic.o2o.util;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

/**
 * @author dongzc
 * @date 2018/8/7 21:12
 */
public class EncryptPropertyPlaceholderConfigurer  extends PropertyPlaceholderConfigurer {

    /**
     * 需要加密的字段数组
     */
    private String[] encryptPropNames = {"jdbc.username", "jdbc.password"};

    /**
     * 对关键的属性进行转换
     * @param propertyName
     * @param propertyValue
     * @return
     */
    @Override
    protected String convertProperty(String propertyName, String propertyValue) {
        if (isEncryptProp(propertyName)) {
            String decryptValue = DESUtil.getDecryptString(propertyValue);
            return decryptValue;
        } else {
            return propertyValue;
        }
    }

    /**
     * 该属性是否已加密
     * @param propertyName
     * @return
     */
    private boolean isEncryptProp (String propertyName) {
        for (String encryptpropertyName : encryptPropNames) {
            if (encryptpropertyName.equals(propertyName)) {
                return true;
            }
        }
        return false;
    }
}
