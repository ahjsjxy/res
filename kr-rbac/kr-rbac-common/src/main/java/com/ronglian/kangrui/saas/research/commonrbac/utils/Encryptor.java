package com.ronglian.kangrui.saas.research.commonrbac.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

public class Encryptor {
    
    public static String DEFAULT_SALT = "5371f568a45e5ab1f442c38e0932aef24447139b";
    public static String DEFAULT_PASSWORD = "987654";
    
    public static Object encrypt(String username, String password, String saltString) {

        String hashAlgorithmName = "md5";//加密算法

        String passwordSalt = DEFAULT_SALT;
        if(StringUtils.isNotBlank(saltString))
            passwordSalt = saltString;

        String salt = passwordSalt + username + passwordSalt; //盐值

        int hashIterations = 1024; //散列次数

        ByteSource credentialsSalt = ByteSource.Util.bytes(salt);//盐

        return new SimpleHash(hashAlgorithmName, password, credentialsSalt, hashIterations);

    }

}
