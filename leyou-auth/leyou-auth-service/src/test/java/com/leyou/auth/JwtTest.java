package com.leyou.auth;

import com.leyou.auth.utils.RsaUtils;
import org.junit.Test;

import java.security.PrivateKey;
import java.security.PublicKey;

public class JwtTest {
    private static final String pubKeyPath = "D:\\tmp\\ras\\rsa.pub";
    private static final String priKeyPath = "D:\\tmp\\ras\\rsa.pri";

    private PublicKey publicKey;
    private PrivateKey privateKey;

    @Test
    public void testRsa() throws Exception{
        RsaUtils.generateKey(pubKeyPath,priKeyPath,"234");
    }
}
