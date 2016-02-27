package cn.ixuehu.phoneguard.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 项目名：PhoneGuard-master
 * 包名：cn.ixuehu.phoneguard.utils
 * Created by daimaren on 2016/2/27.
 */
public class Md5Utils {
    public static String Md5Encode(String source){
        String res = "";
        try {
            byte[] digest = MessageDigest.getInstance("MD5").digest(source.getBytes());
            for (byte b:digest){
                String hex = Integer.toHexString(b & 0xFF);
                if (hex.length() == 1){
                    hex = "0" + hex;
                }
                res += hex;
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return res;
    }
}
