package com.wxhg.test_domain_auto_describe.utils;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 访问百度页面爬取公网IP
 *
 * @author zxx
 * @date 2023/4/1
 */
@Slf4j
public class TpLinkClientZZNew {
    public static String sid = null;
    public static final String NATLIST = "[{\"domain\":\"InternetGatewayDevice.WANDevice.1.WANConnectionDevice.2.WANPPPConnection.1.PortMapping.1\",\"ProtMapEnabled\":\"1\",\"Protocol\":\"TCP/UDP\",\"RemoteHost\":\"\",\"ExPort\":\"8893\",\"ExEndPort\":\"8893\",\"InPort\":\"8893\",\"InEndPort\":\"0\",\"InClient\":\"192.168.10.51\",\"ExSrcPort\":\"0\",\"ExSrcEndPort\":\"0\",\"Description\":\"\",\"ExternalIP\":\"\",\"Interface\":\"2_INTERNET_R_VID_22\",\"flag\":0},{\"domain\":\"InternetGatewayDevice.WANDevice.1.WANConnectionDevice.2.WANPPPConnection.1.PortMapping.2\",\"ProtMapEnabled\":\"1\",\"Protocol\":\"TCP/UDP\",\"RemoteHost\":\"\",\"ExPort\":\"8891\",\"ExEndPort\":\"8891\",\"InPort\":\"8891\",\"InEndPort\":\"0\",\"InClient\":\"192.168.10.51\",\"ExSrcPort\":\"0\",\"ExSrcEndPort\":\"0\",\"Description\":\"\",\"ExternalIP\":\"\",\"Interface\":\"2_INTERNET_R_VID_22\",\"flag\":0},{\"domain\":\"InternetGatewayDevice.WANDevice.1.WANConnectionDevice.2.WANPPPConnection.1.PortMapping.3\",\"ProtMapEnabled\":\"1\",\"Protocol\":\"TCP/UDP\",\"RemoteHost\":\"\",\"ExPort\":\"8890\",\"ExEndPort\":\"8890\",\"InPort\":\"8890\",\"InEndPort\":\"0\",\"InClient\":\"192.168.10.198\",\"ExSrcPort\":\"0\",\"ExSrcEndPort\":\"0\",\"Description\":\"\",\"ExternalIP\":\"\",\"Interface\":\"2_INTERNET_R_VID_22\",\"flag\":0},{\"domain\":\"InternetGatewayDevice.WANDevice.1.WANConnectionDevice.2.WANPPPConnection.1.PortMapping.4\",\"ProtMapEnabled\":\"1\",\"Protocol\":\"TCP/UDP\",\"RemoteHost\":\"\",\"ExPort\":\"8555\",\"ExEndPort\":\"8555\",\"InPort\":\"8930\",\"InEndPort\":\"0\",\"InClient\":\"192.168.10.14\",\"ExSrcPort\":\"0\",\"ExSrcEndPort\":\"0\",\"Description\":\"\",\"ExternalIP\":\"\",\"Interface\":\"2_INTERNET_R_VID_22\",\"flag\":0},{\"domain\":\"InternetGatewayDevice.WANDevice.1.WANConnectionDevice.2.WANPPPConnection.1.PortMapping.5\",\"ProtMapEnabled\":\"1\",\"Protocol\":\"TCP/UDP\",\"RemoteHost\":\"\",\"ExPort\":\"8935\",\"ExEndPort\":\"8957\",\"InPort\":\"8935\",\"InEndPort\":\"8957\",\"InClient\":\"192.168.10.198\",\"ExSrcPort\":\"0\",\"ExSrcEndPort\":\"0\",\"Description\":\"\",\"ExternalIP\":\"\",\"Interface\":\"2_INTERNET_R_VID_22\",\"flag\":0},{\"domain\":\"InternetGatewayDevice.WANDevice.1.WANConnectionDevice.2.WANPPPConnection.1.PortMapping.6\",\"ProtMapEnabled\":\"1\",\"Protocol\":\"TCP/UDP\",\"RemoteHost\":\"\",\"ExPort\":\"10034\",\"ExEndPort\":\"10054\",\"InPort\":\"10034\",\"InEndPort\":\"10054\",\"InClient\":\"192.168.10.198\",\"ExSrcPort\":\"0\",\"ExSrcEndPort\":\"0\",\"Description\":\"\",\"ExternalIP\":\"\",\"Interface\":\"2_INTERNET_R_VID_22\",\"flag\":0},{\"domain\":\"InternetGatewayDevice.WANDevice.1.WANConnectionDevice.2.WANPPPConnection.1.PortMapping.7\",\"ProtMapEnabled\":\"1\",\"Protocol\":\"TCP/UDP\",\"RemoteHost\":\"\",\"ExPort\":\"8446\",\"ExEndPort\":\"8446\",\"InPort\":\"8446\",\"InEndPort\":\"0\",\"InClient\":\"192.168.10.198\",\"ExSrcPort\":\"0\",\"ExSrcEndPort\":\"0\",\"Description\":\"\",\"ExternalIP\":\"\",\"Interface\":\"2_INTERNET_R_VID_22\",\"flag\":0},{\"domain\":\"InternetGatewayDevice.WANDevice.1.WANConnectionDevice.2.WANPPPConnection.1.PortMapping.8\",\"ProtMapEnabled\":\"1\",\"Protocol\":\"TCP/UDP\",\"RemoteHost\":\"\",\"ExPort\":\"8889\",\"ExEndPort\":\"8889\",\"InPort\":\"8889\",\"InEndPort\":\"0\",\"InClient\":\"192.168.10.51\",\"ExSrcPort\":\"0\",\"ExSrcEndPort\":\"0\",\"Description\":\"\",\"ExternalIP\":\"\",\"Interface\":\"2_INTERNET_R_VID_22\",\"flag\":0},{\"domain\":\"InternetGatewayDevice.WANDevice.1.WANConnectionDevice.2.WANPPPConnection.1.PortMapping.9\",\"ProtMapEnabled\":\"1\",\"Protocol\":\"TCP/UDP\",\"RemoteHost\":\"\",\"ExPort\":\"6379\",\"ExEndPort\":\"6379\",\"InPort\":\"6379\",\"InEndPort\":\"0\",\"InClient\":\"192.168.10.198\",\"ExSrcPort\":\"0\",\"ExSrcEndPort\":\"0\",\"Description\":\"\",\"ExternalIP\":\"\",\"Interface\":\"2_INTERNET_R_VID_22\",\"flag\":0},{\"domain\":\"InternetGatewayDevice.WANDevice.1.WANConnectionDevice.2.WANPPPConnection.1.PortMapping.10\",\"ProtMapEnabled\":\"1\",\"Protocol\":\"TCP/UDP\",\"RemoteHost\":\"\",\"ExPort\":\"3307\",\"ExEndPort\":\"3307\",\"InPort\":\"3306\",\"InEndPort\":\"0\",\"InClient\":\"192.168.10.198\",\"ExSrcPort\":\"0\",\"ExSrcEndPort\":\"0\",\"Description\":\"\",\"ExternalIP\":\"\",\"Interface\":\"2_INTERNET_R_VID_22\",\"flag\":0},{\"domain\":\"InternetGatewayDevice.WANDevice.1.WANConnectionDevice.2.WANPPPConnection.1.PortMapping.11\",\"ProtMapEnabled\":\"1\",\"Protocol\":\"TCP/UDP\",\"RemoteHost\":\"\",\"ExPort\":\"11256\",\"ExEndPort\":\"11256\",\"InPort\":\"3389\",\"InEndPort\":\"0\",\"InClient\":\"192.168.10.51\",\"ExSrcPort\":\"0\",\"ExSrcEndPort\":\"0\",\"Description\":\"\",\"ExternalIP\":\"\",\"Interface\":\"2_INTERNET_R_VID_22\",\"flag\":0},{\"domain\":\"InternetGatewayDevice.WANDevice.1.WANConnectionDevice.2.WANPPPConnection.1.PortMapping.12\",\"ProtMapEnabled\":\"1\",\"Protocol\":\"TCP/UDP\",\"RemoteHost\":\"\",\"ExPort\":\"8080\",\"ExEndPort\":\"8200\",\"InPort\":\"8080\",\"InEndPort\":\"8200\",\"InClient\":\"192.168.10.198\",\"ExSrcPort\":\"0\",\"ExSrcEndPort\":\"0\",\"Description\":\"\",\"ExternalIP\":\"\",\"Interface\":\"2_INTERNET_R_VID_22\",\"flag\":0},{\"domain\":\"InternetGatewayDevice.WANDevice.1.WANConnectionDevice.2.WANPPPConnection.1.PortMapping.13\",\"ProtMapEnabled\":\"1\",\"Protocol\":\"TCP/UDP\",\"RemoteHost\":\"\",\"ExPort\":\"11257\",\"ExEndPort\":\"11257\",\"InPort\":\"11257\",\"InEndPort\":\"0\",\"InClient\":\"192.168.10.51\",\"ExSrcPort\":\"0\",\"ExSrcEndPort\":\"0\",\"Description\":\"\",\"ExternalIP\":\"\",\"Interface\":\"2_INTERNET_R_VID_22\",\"flag\":0},null]";

    public static String getPublicIp() throws IOException {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("text/plain");
        RequestBody body = RequestBody.create(mediaType, "");
        Request request = new Request.Builder()
                .url("https://api.ipify.org/?format=json")
                .method("GET", null)
                .build();
        Response response = client.newCall(request).execute();
        String resultStr = Objects.requireNonNull(response.body()).string();
        return JSONObject.parseObject(resultStr).get("ip").toString();

    }

    public static void checkNATData() {
        if (sid != null && !sid.equals("")) {
            String logoutToken = getLogoutToken();
            if (logoutToken == null || logoutToken.equals("")) {
                sid = null;
                checkNATData();
            }
        } else {
            String logToken = getLogToken();
            String logSid = doLogin(logToken);
            if (logSid != null && !logSid.equals("")) {
                sid = logSid;
                List list = queryNATConfigList();
                if (list != null && list.isEmpty()) {
                    addNATData();
                }
                String logoutToken = getLogoutToken();
                if (logoutToken != null && !logoutToken.equals("")) {
                    doLogout(logoutToken);
                }
            }
        }

    }

    public static String getLogToken() {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("text/plain");
        RequestBody body = RequestBody.create(mediaType, "");
        Request request = new Request.Builder()
                .url("http://192.168.10.1/asp/GetRandCount.asp")
                .method("POST", body)
                .addHeader("Connection", "keep-alive")
                .addHeader("Content-Length", "0")
                .addHeader("Pragma", "no-cache")
                .addHeader("Cache-Control", "no-cache")
                .addHeader("Accept", "*/*")
                .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/99.0.4844.74 Safari/537.36")
                .addHeader("X-Requested-With", "XMLHttpRequest")
                .addHeader("Origin", "http://192.168.10.1")
                .addHeader("Referer", "http://192.168.10.1/CU.html")
                .addHeader("Accept-Language", "zh,zh-CN;q=0.9,en;q=0.8")
                .build();
        try {
            Response response = client.newCall(request).execute();
            if (response.code() == 200 && response.body() != null) {
                String s = Objects.requireNonNull(response.body()).string();
                log.info("获取登录令牌：" + s);
                return s;
            } else {
                log.info("获取登录令牌异常，响应码" + response.code());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public static String doLogin(String logToken) {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        RequestBody body = RequestBody.create(mediaType, "UserName=CUAdmin&PassWord=Q1VBZG1pbjI1NTQwNjk1&Language=chinese&x.X_HW_Token=" + logToken);
        Request request = new Request.Builder()
                .url("http://192.168.10.1/login.cgi")
                .method("POST", body)
                .addHeader("Connection", "keep-alive")
                .addHeader("Pragma", "no-cache")
                .addHeader("Cache-Control", "no-cache")
                .addHeader("Upgrade-Insecure-Requests", "1")
                .addHeader("Origin", "http://192.168.10.1")
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/99.0.4844.74 Safari/537.36")
                .addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9")
                .addHeader("Referer", "http://192.168.10.1/CU.html")
                .addHeader("Accept-Language", "zh,zh-CN;q=0.9,en;q=0.8")
                .build();
        try {
            Response response = client.newCall(request).execute();
            if (response.code() == 200) {
                String curLogCookie = response.header("Set-cookie");
                if (curLogCookie != null && !curLogCookie.equals("")) {

                    Matcher matcher = Pattern.compile("sid\\=(\\w*):").matcher(curLogCookie);
                    if(matcher.find()){
                        String curSid = matcher.group(1);
                        log.info("登录成功，sid ：" + curSid);
                        return curSid;
                    }
                } else {
                    log.info("登录异常，未获取到cookie" + response.code());

                }
            } else {
                log.info("登录异常，响应码" + response.code());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public static String getLogoutToken() {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("text/plain");
        RequestBody body = RequestBody.create(mediaType, "");
        Request request = new Request.Builder()
                .url("http://192.168.10.1/html/ssmp/common/GetRandToken.asp")
                .method("POST", body)
                .addHeader("Connection", "keep-alive")
                .addHeader("Content-Length", "0")
                .addHeader("Pragma", "no-cache")
                .addHeader("Cache-Control", "no-cache")
                .addHeader("Accept", "*/*")
                .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/99.0.4844.74 Safari/537.36")
                .addHeader("X-Requested-With", "XMLHttpRequest")
                .addHeader("Origin", "http://192.168.10.1")
                .addHeader("Referer", "http://192.168.10.1/CtcApp/preindex.asp")
                .addHeader("Accept-Language", "zh,zh-CN;q=0.9,en;q=0.8")
                .addHeader("Cookie", "Cookie=sid=" + sid + ":Language:chinese:id=1")
                .build();
        try {
            Response response = client.newCall(request).execute();
            if (response.code() == 200) {
                String curLogSid = response.body().string();
                if (curLogSid != null && !curLogSid.equals("")) {
                    log.info("获取退出令牌成功，sid ：" + curLogSid);
                    return curLogSid;
                } else {
                    log.info("获取退出令牌异常，未获取到退出令牌" + response.code());

                }
            } else {
                log.info("获取退出令牌异常，响应码" + response.code());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public static void doLogout(String logoutToken) {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        RequestBody body = RequestBody.create(mediaType, "x.X_HW_Token="+logoutToken);
        Request request = new Request.Builder()
                .url("http://192.168.10.1/logout.cgi?&RequestFile=/html/logout.html")
                .method("POST", body)
                .addHeader("Connection", "keep-alive")
                .addHeader("Pragma", "no-cache")
                .addHeader("Cache-Control", "no-cache")
                .addHeader("Upgrade-Insecure-Requests", "1")
                .addHeader("Origin", "http://192.168.10.1")
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/99.0.4844.74 Safari/537.36")
                .addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9")
                .addHeader("Referer", "http://192.168.10.1/CtcApp/preindex.asp")
                .addHeader("Accept-Language", "zh,zh-CN;q=0.9,en;q=0.8")
                .addHeader("Cookie", "Cookie=sid=" + sid + ":Language:chinese:id=1")
                .build();
        Response response = null;
        try {
            response = client.newCall(request).execute();
            if (response.code() == 200) {
                    log.info("sid:{}退出成功",sid);
                    sid=null;
            } else {
                log.info("获取退出令牌异常，响应码" + response.code());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static List queryNATConfigList() {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        Request request = new Request.Builder()
                .url("http://192.168.10.1/html/bbsp/portmapping/portmappingold.asp")
                .method("GET", null)
                .addHeader("Connection", "keep-alive")
                .addHeader("Pragma", "no-cache")
                .addHeader("Cache-Control", "no-cache")
                .addHeader("Upgrade-Insecure-Requests", "1")
                .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/99.0.4844.74 Safari/537.36")
                .addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9")
                .addHeader("Accept-Language", "zh,zh-CN;q=0.9,en;q=0.8")
                .addHeader("Cookie", "Cookie=sid=" + sid + ":Language:chinese:id=1")
                .build();
        try {
            Response response = client.newCall(request).execute();
            if (response.code() == 200) {
                String bodyHtml = response.body().string();
                if (bodyHtml != null && !bodyHtml.equals("")) {
                    log.info("获取NAT配置项成功");
                    String arrItemRegex = "new stPortMap\\([\\w,\"\\.\\\\]*\\)";
                    Pattern pattern = Pattern.compile("var WanPPPPortMapping \\= new Array\\([" + arrItemRegex + "]{1,}\\)");
                    Pattern pattern2 = Pattern.compile(arrItemRegex);
                    Matcher matcher = pattern.matcher(bodyHtml);
                    if (matcher.find()) {
                        List<String> natList = new ArrayList<>();
                        String rows = matcher.group(0);
                        log.info(rows);
                        Matcher matcher1 = pattern2.matcher(rows);
                        while (matcher1.find()) {
                            String curMatcherStr = null;
                            try {
                                curMatcherStr = URLDecoder.decode(matcher1.group(0).replaceAll("\\\\x", "%"), "UTF-8");
                                log.info(curMatcherStr);
                                natList.add(curMatcherStr);
                            } catch (UnsupportedEncodingException e) {
                                throw new RuntimeException(e);
                            }
                        }
                        log.info("查询当前NAT配置，共{}条", natList.size());
                        return natList;
                    } else {
                        log.info("查询当前NAT配置页面响应异常，未找到对应配置");
                    }
                } else {
                    log.info("查询当前NAT配置异常，未获取到退出令牌" + response.code());

                }
            } else {
                log.info("查询当前NAT配置异常，响应码" + response.code());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public static void addNATData() {
        log.info("NAT配置为空，重新添加NAT配置项");
    }


    public static void main(String[] args) {
//        sid = "274571b249ff8255fc9c37fdd2db50762ed35404fe7cb479162b74e86c113db9";
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        Request request = new Request.Builder()
                .url("http://192.168.10.1/CU.html")
                .method("GET", null)
                .addHeader("Connection", "keep-alive")
                .addHeader("Pragma", "no-cache")
                .addHeader("Cache-Control", "no-cache")
                .addHeader("Upgrade-Insecure-Requests", "1")
                .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/99.0.4844.74 Safari/537.36")
                .addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9")
                .addHeader("Accept-Language", "zh,zh-CN;q=0.9,en;q=0.8")
                .build();
        try {
            Response response = client.newCall(request).execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        checkNATData();
    }
}
