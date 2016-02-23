package cn.ixuehu.phoneguard.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * 项目名：PhoneGuard-master
 * 包名：cn.ixuehu.phoneguard.utils
 * Created by daimaren on 2016/2/23.
 */
public class Stream2String {
    public static String process(InputStream inputStream)
    {
        String res = "";
        //使用
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        //一行一行读，然后连接
        String readLine;
        try {
            readLine = bufferedReader.readLine();
            while(readLine != null)
            {
                res += readLine;
                readLine = bufferedReader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return res;
    }
}
