package com.wxhg.test_domain_auto_describe;

import com.wxhg.test_domain_auto_describe.utils.AliyunUtils;
import com.wxhg.test_domain_auto_describe.utils.TpLinkClient;
import com.wxhg.test_domain_auto_describe.utils.TpLinkClientZZ;
import com.wxhg.test_domain_auto_describe.utils.TpLinkClientZZNew;

import java.io.IOException;

/**
 * @author zhangkeyuan
 * @date 2020/8/8 11:08
 */
public class Application {

    public static void main(String[] args) throws IOException {
        String ip = TpLinkClientZZNew.getPublicIp();
        AliyunUtils.getHostDescribeList(1L,ip);
        AliyunUtils.updateDescribeInfo(ip);
    }
}
