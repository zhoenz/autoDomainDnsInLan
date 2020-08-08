package com.wxhg.test_domain_auto_describe.utils;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.alidns.model.v20150109.DescribeDomainRecordsRequest;
import com.aliyuncs.alidns.model.v20150109.DescribeDomainRecordsResponse;
import com.aliyuncs.alidns.model.v20150109.UpdateDomainRecordRequest;
import com.aliyuncs.alidns.model.v20150109.UpdateDomainRecordResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.profile.DefaultProfile;
import com.google.gson.Gson;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zhangkeyuan
 * @date 2020/8/8 14:15
 */
public class AliyunUtils {
    /**这里填写aliyun accseeKeyId*/
    public static final String A_K_I = "";
    /**这里填写aliyun accseeSecret*/
    public static final String A_S = "";
    private static String recordId;
    private static boolean needUpdate = false;

    public static void getHostDescribeList(Long page, String ip) {

        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", A_K_I, A_S);
        IAcsClient client = new DefaultAcsClient(profile);

        DescribeDomainRecordsRequest request = new DescribeDomainRecordsRequest();
        request.setDomainName("wxhg.net");
        if (page != null && page >= 1) {
            request.setPageNumber(page);
        }
        try {
            DescribeDomainRecordsResponse response = client.getAcsResponse(request);
            List<DescribeDomainRecordsResponse.Record> list =
                    response.getDomainRecords().stream().filter(a -> "testserver".equals(a.getRR())).collect(Collectors.toList());
            if (list.size() > 0) {
                if (!list.get(0).getValue().equals(ip)) {
                    needUpdate = true;
                    recordId = list.get(0).getRecordId();
                }else{
                    LogUtils.print("IP 解析记录已存在");
                }
            } else {
                int totalPage = response.getTotalCount() / response.getPageSize() + response.getTotalCount() % response.getPageSize() == 0 ? 0 : 1;
                if (totalPage > response.getPageNumber()) {
                    getHostDescribeList(response.getPageNumber() + 1, ip);
                }
            }
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            LogUtils.print("ErrCode:" + e.getErrCode());
            LogUtils.print("ErrMsg:" + e.getErrMsg());
            LogUtils.print("RequestId:" + e.getRequestId());
        }
    }

    public static void updateDescribeInfo(String ip) {
        if(needUpdate) {
            DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", A_K_I, A_S);
            IAcsClient client = new DefaultAcsClient(profile);

            UpdateDomainRecordRequest request = new UpdateDomainRecordRequest();
            request.setRecordId(recordId);
            request.setRR("testserver");
            request.setType("A");
            request.setValue(ip);
            try {
                UpdateDomainRecordResponse response = client.getAcsResponse(request);
                LogUtils.print(new Gson().toJson(response));
            } catch (ServerException e) {
                e.printStackTrace();
            } catch (ClientException e) {
                LogUtils.print("ErrCode:" + e.getErrCode());
                LogUtils.print("ErrMsg:" + e.getErrMsg());
                LogUtils.print("RequestId:" + e.getRequestId());
            }
        }
    }

}
