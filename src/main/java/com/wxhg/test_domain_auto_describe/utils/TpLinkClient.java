package com.wxhg.test_domain_auto_describe.utils;

import com.alibaba.fastjson.JSON;
import okhttp3.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 访问路由器管理页面爬取公网IP
 * @author zhangkeyuan
 * @date 2020/8/8 13:33
 */
public class TpLinkClient {
    private static String sysAuth = "";
    private static String stok = "";

    /**
     * TP_LINK 登录
     *
     */
    public static void login() throws IOException {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded; charset=UTF-8");
        RequestBody body = RequestBody.Companion.create( "",mediaType);
        Request request = new Request.Builder()
                .url("http://192.168.10.1/cgi-bin/luci/;stok=/login?form=login")
                .method("POST", body)
                .addHeader("Connection", "keep-alive")
                .addHeader("Accept", "application/json, text/javascript, */*; q=0.01")
                .addHeader("X-Requested-With", "XMLHttpRequest")
                .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/84.0.4147.105 Safari/537.36")
                .addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
                .addHeader("Origin", "http://192.168.10.1")
                .addHeader("Referer", "http://192.168.10.1/webpages/login.html")
                .addHeader("Accept-Language", "zh,zh-CN;q=0.9")
                .build();
        Response response = client.newCall(request).execute();
        String s = Objects.requireNonNull(response.body()).string();
        Map hashMap = JSON.parseObject(s, HashMap.class);
        boolean result = hashMap.containsKey("result");
        if (result) {
            hashMap = JSON.parseObject(hashMap.get("result").toString(), HashMap.class);
            stok = hashMap.get("stok").toString();
        }
        String header = response.header("Set-Cookie");
        if (null != header) {
            sysAuth = header;
        }
        LogUtils.print("登录成功 stok=" + stok + " ; sysauth=" + header);
    }

    public static String getPublicIp() throws IOException {
        login();
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded; charset=UTF-8");
        RequestBody body = RequestBody.create(mediaType, "data=%7B%22method%22%3A%22get%22%2C%22params%22%3A%7B%22wan_id%22%3A1%7D%7D");
        Request request = new Request.Builder()
                .url("http://192.168.10.1/cgi-bin/luci/;stok=" + stok + "/admin/interface_wan?form=status")
                .method("POST", body)
                .addHeader("Connection", "keep-alive")
                .addHeader("Accept", "application/json, text/javascript, */*; q=0.01")
                .addHeader("X-Requested-With", "XMLHttpRequest")
                .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/84.0.4147.105 Safari/537.36")
                .addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
                .addHeader("Origin", "http://192.168.10.1")
                .addHeader("Referer", "http://192.168.10.1/webpages/index.html")
                .addHeader("Accept-Language", "zh,zh-CN;q=0.9")
                .addHeader("Cookie", sysAuth)
                .build();
        Response response = client.newCall(request).execute();
        String resultStr = Objects.requireNonNull(response.body()).string();
        HashMap resultMap = JSON.parseObject(resultStr, HashMap.class);
        if (resultMap.containsKey("result")) {
            HashMap ipInfo = JSON.parseObject(resultMap.get("result").toString(), HashMap.class);
            if (ipInfo != null && ipInfo.containsKey("ipaddr")) {
                LogUtils.print("本地公网 IP res:" + ipInfo.get("ipaddr").toString());
                return ipInfo.get("ipaddr").toString();
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

}
