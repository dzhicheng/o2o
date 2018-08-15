package com.dongzhic.o2o.dao;

import com.dongzhic.o2o.pojo.LocalAuth;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

/**
 * @author dongzc
 * @date 2018/8/13 13:31
 */
public interface LocalAuthDao {

    /**
     * 通过账号和密码查询对应信息，登陆用
     * @param userName
     * @param password
     * @return
     */
    LocalAuth queryLocalByUserNameAmdPwd (@Param("userName") String userName, @Param("password") String password);

    /**
     * 通过用户Id查询对应LocalAuth
     * @param userId
     * @return
     */
    LocalAuth queryLocalByUserId (@Param("userId") long userId);

    /**
     * 添加平台账号
     * @param localAuth
     * @return
     */
    int insertLocalAuth (LocalAuth localAuth);

    /**
     * 更改密码
     * @param userId 用户id
     * @param userName 用户名
     * @param password 密码
     * @param newPassword 新密码
     * @param lastEditTime 最后修改时间
     * @return
     */
    int updateLocalAuth(@Param("userId") long userId, @Param("userName") String userName,
                         @Param("password") String password,@Param("newPassword") String newPassword,
                         @Param("lastEditTime") Date lastEditTime);

}
