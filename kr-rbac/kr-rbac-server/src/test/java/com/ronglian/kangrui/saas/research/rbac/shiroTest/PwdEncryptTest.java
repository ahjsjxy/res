package com.ronglian.kangrui.saas.research.rbac.shiroTest;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

import org.junit.Test;

public class PwdEncryptTest {

    private Object encrypt(String username, String enablePassword) {

        String hashAlgorithmName = "md5";//加密算法

        String passwordSalt = "5371f568a45e5ab1f442c38e0932aef24447139b";//密钥

        String salt = passwordSalt + username + passwordSalt; //盐值

        int hashIterations = 1024; //散列次数

        ByteSource credentialsSalt = ByteSource.Util.bytes(salt);//盐

        return new SimpleHash(hashAlgorithmName, enablePassword, credentialsSalt, hashIterations);

    }


    /**
     * 加密测试
     */
    @Test
    public void encryption() {

        Object securePassword = encrypt("admin", "87654321");

        System.out.println("admin的加密后密码 : " + securePassword);

        Object zhangsanPassword = encrypt("test1", "12345");

        System.out.println("test1的加密后密码 : " + zhangsanPassword);

        Object lisiPassword = encrypt("test2", "12345");

        System.out.println("test2的加密后密码 : " + lisiPassword);
    }


}
