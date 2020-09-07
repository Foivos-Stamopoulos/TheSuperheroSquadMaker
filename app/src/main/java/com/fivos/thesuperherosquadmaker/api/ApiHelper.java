package com.fivos.thesuperherosquadmaker.api;

import com.fivos.thesuperherosquadmaker.util.Config;

import java.sql.Timestamp;

public class ApiHelper {

    public static String getTimeStamp() {
        return new Timestamp(System.currentTimeMillis()).toString();
    }

    public static String getHash(String timestamp) {
        return getMD5Hash(timestamp + Config.API_PRIVATE_KEY + Config.API_PUBLIC_KEY);
    }

    private static String getMD5Hash(String md5) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            byte[] array = md.digest(md5.getBytes());
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < array.length; ++i) {
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1,3));
            }
            return sb.toString();
        } catch (java.security.NoSuchAlgorithmException e) {
        }
        return null;
    }

}

