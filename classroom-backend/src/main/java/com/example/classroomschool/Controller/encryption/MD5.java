package com.example.classroomschool.Controller.encryption;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Base64.Encoder;

/**
 * 加密
 * */
public class MD5 {
    public String encoderByMd5(String str) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        //确定算法
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        Encoder base64en = Base64.getEncoder();
        //加密后的字符串
        return base64en.encodeToString(md5.digest(str.getBytes(StandardCharsets.UTF_8)));
    }

}
