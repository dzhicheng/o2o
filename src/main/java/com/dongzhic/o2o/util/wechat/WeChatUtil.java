package com.dongzhic.o2o.util.wechat;

import com.dongzhic.o2o.dto.UserAccessToken;
import com.dongzhic.o2o.dto.WeChatUser;
import com.dongzhic.o2o.pojo.PersonInfo;
import com.dongzhic.o2o.pojo.WeChatAuth;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import java.io.*;
import java.net.ConnectException;
import java.net.URL;
import java.security.SecureRandom;

/**
 *  微信工具类：提交http请求给微信，从来获取用户信息
 * @author dongzc
 * @date 2018/7/17 17:59
 */
public class WeChatUtil {

    private static Logger log = LoggerFactory.getLogger(WeChatUtil.class);

    /**
     * 获取UserAccessToken实体类
     * @param code
     * @return
     */
    public static UserAccessToken getUserAccessToken (String code) {
        // 测试号信息里的appId
        String appId = "wxe30612cb1fe43904";
        log.debug("appId:"+appId);
        // 测试号信息里的appsecret
        String appsecret = "0d2947ab7c78f6cfe2e22035aad82064";
        log.debug("appsecret:"+appsecret);
        // 根据传入的code,拼接出访问微信定义号的接口的URL
        String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid="+appId+"&secret=" +appsecret
                + "&code=" + code + "&grant_type=authorization_code";
        // 向相应的url发起请求，获取token json字符串
        String tokenStr = httpsRequest(url, "GET", null);
        log.debug("userAccessToken:"+tokenStr);
        UserAccessToken token = new UserAccessToken();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            token = objectMapper.readValue(tokenStr, UserAccessToken.class);
        } catch (JsonParseException e) {
            log.error("获取用户accessToken失败: " + e.getMessage()); e.printStackTrace();
        } catch (JsonMappingException e) {
            log.error("获取用户accessToken失败: " + e.getMessage()); e.printStackTrace();
        } catch (IOException e) {
            log.error("获取用户accessToken失败: " + e.getMessage()); e.printStackTrace();
        }
        if (token == null) {
            log.error("获取用户accessToken失败");
            return null;
        }
        return token;
    }

    public static WeChatUser getUserInfo (String accessToken, String openId) {
        // 根据传入的accessToken以及openId拼接出访问微信定义的端口并获取用户信息的URL
        String url = "https://api.weixin.qq.com/sns/userinfo?access_token="+accessToken+"&openid="+openId+"&lang=zh_CN";
        // 根据url获取用户信息json字符串
        String userStr = httpsRequest(url, "GET", null);
        log.debug("user info :"+userStr);
        WeChatUser user = new WeChatUser();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            user = objectMapper.readValue(userStr, WeChatUser.class);
        }catch (JsonParseException e) {
            log.error("获取用户信息失败: " + e.getMessage());
            e.printStackTrace();
        } catch (JsonMappingException e) {
            log.error("获取用户信息失败: " + e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            log.error("获取用户信息失败: " + e.getMessage());
            e.printStackTrace();
        }
        if (user == null) {
            log.error("获取用户信息失败");
            return null;
        }

        return user;


    }

    /**
     * 发起https请求并获取结果
     * @param requestUrl 请求地址
     * @param requestMethod 请求方式（GET、POST）
     * @param outputStr 提交的数据
     * @return json字符串
     */
    public static String httpsRequest (String requestUrl, String requestMethod, String outputStr) {
        StringBuffer buffer = new StringBuffer();
        BufferedReader bufferedReader = null;
        InputStreamReader inputStreamReader = null;
        InputStream inputStream = null;
        HttpsURLConnection httpsURLConn = null;
        try {
            // 创建SSLContext对象，并使用指定的信任管理器初始化
            TrustManager[] tm = {new MyX509TrustManager()};
            SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
            sslContext.init(null, tm, new SecureRandom());
            // 从上述的SSLContext对象中获取SSLSocketFactory对象
            SSLSocketFactory ssf = sslContext.getSocketFactory();

            URL url = new URL(requestUrl);
            httpsURLConn = (HttpsURLConnection) url.openConnection();
            httpsURLConn.setSSLSocketFactory(ssf);
            httpsURLConn.setDoOutput(true);
            httpsURLConn.setDoInput(true);
            httpsURLConn.setUseCaches(false);
            //设置请求方式（GET/POST）
            httpsURLConn.setRequestMethod(requestMethod);

            if ("GET".equals(requestMethod)) {
                httpsURLConn.connect();
            }

            // 当有数据需要提交时
            if (null != outputStr) {
                OutputStream outputStream = httpsURLConn.getOutputStream();
                outputStream.write(outputStr.getBytes("UTF-8"));
                outputStream.close();
            }

            //将返回的输入流转换成字符串
            inputStream = httpsURLConn.getInputStream();
            inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
            bufferedReader = new BufferedReader(inputStreamReader);

            String str = null;
            while ((str = bufferedReader.readLine()) != null){
                buffer.append(str);
            }
            inputStream = null;
            log.debug("https buffer:"+buffer.toString());
        } catch (ConnectException e) {
            log.error("weChat server connection timed out .");
        } catch (Exception e) {
            log.error("https request error:{}", e);
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try {
                if (inputStreamReader != null) {
                    inputStreamReader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            httpsURLConn.disconnect();
        }
        return buffer.toString();
    }

    /**
     * 将WeChatUser里面的信息转换成PersonInfo的信息
     * @param weChatUser
     * @return
     */
    public static PersonInfo getPersonInfoFromRequest (WeChatUser weChatUser) {
        PersonInfo personInfo = new PersonInfo();
        personInfo.setName(weChatUser.getNickName());
        personInfo.setGender(weChatUser.getSex());
        personInfo.setProfileImg(weChatUser.getHeadImgUrl());
        personInfo.setEnableStatus(1);
        return personInfo;
    }



}
