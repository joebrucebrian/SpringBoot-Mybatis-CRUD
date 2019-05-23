package com.lty.springbootmybatiscrud.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 加密
 */
public class FunctionUtils {
    private static final String KEY = "我爱北京天安门";
    public static String md5Encrypt(String s){
        s = s==null ? "" : s + KEY;
        char[] md5Digist = {'0','1','2','3','4','5','6','7','8','9','a','b','c','d','e','f'};
        char[] str = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(s.getBytes());
            byte[] s1 = md.digest();
            int k = 0;
            str = new char[s1.length * 2];
            for (int i = 0; i < s1.length; i++) {
                str[k++] = md5Digist[s1[i] >>> 4 & md5Digist.length-1];
                str[k++] = md5Digist[s1[i] & md5Digist.length-1];
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } finally {
            return new String(str);
        }
    }
}
