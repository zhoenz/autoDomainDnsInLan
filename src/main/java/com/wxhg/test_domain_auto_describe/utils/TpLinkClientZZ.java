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
public class TpLinkClientZZ {
    private static String sysAuth = "";
    private static String stok = "";

    /**
     * TP_LINK 登录
     *
     */
    public static void login() throws IOException {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("application/json; charset=UTF-8");
        RequestBody body = RequestBody.create(mediaType, "{\"method\":\"do\",\"login\":{\"password\":\"Hlc8rn008ydfbwK\"}}");
        Request request = new Request.Builder()
                .url("http://192.168.10.1/")
                .method("POST", body)
                .addHeader("Content-Type", "application/json; charset=UTF-8")
                .build();
        Response response = client.newCall(request).execute();



        String s = Objects.requireNonNull(response.body()).string();
        Map hashMap = JSON.parseObject(s, HashMap.class);
        boolean result = hashMap.containsKey("stok");
        if (result) {
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
        MediaType mediaType = MediaType.parse("text/plain");
        RequestBody body = RequestBody.create(mediaType, "{\"network\":{\"name\":[\"wan_status\"]},\"method\":\"get\"}");
        Request request = new Request.Builder()
                .url("http://192.168.10.1/stok="+stok+"/ds")
                .method("POST", body)
                .addHeader("Content-Type", "text/plain")
                .build();
        Response response = client.newCall(request).execute();
        String resultStr = Objects.requireNonNull(response.body()).string();
        HashMap resultMap = JSON.parseObject(resultStr, HashMap.class);
        if (resultMap.containsKey("network")) {
            HashMap ipInfo = JSON.parseObject(resultMap.get("network").toString(), HashMap.class);
            if (ipInfo != null && ipInfo.containsKey("wan_status")) {
                ipInfo = JSON.parseObject(ipInfo.get("wan_status").toString(), HashMap.class);
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
