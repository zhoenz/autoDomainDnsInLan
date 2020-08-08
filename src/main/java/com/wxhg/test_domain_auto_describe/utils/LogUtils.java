package com.wxhg.test_domain_auto_describe.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author zhangkeyuan
 * @date 2020/8/8 15:23
 */
public class LogUtils {
    public static void print(String text){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = simpleDateFormat.format(new Date());
        System.out.println(format + "----" + text);
    }
}
