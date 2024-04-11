package com.ruoyi.tron.utils;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.HMac;
import cn.hutool.crypto.digest.HmacAlgorithm;
import cn.hutool.http.HttpRequest;
import com.alibaba.fastjson2.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * 能量租赁API
 */
public class MeiFreeUtil {
    private static final Logger log = LoggerFactory.getLogger(MeiFreeUtil.class);

    /**
     * 能量租赁密钥
     */
    private static final String API_SECRET = "21fee30c331298a9ee59232d0e1a2b7e";

    /**
     * 波场能量租赁
     *
     * @param tronAddress 租赁地址
     */
    public static void energyLease(String tronAddress) {
        HMac mac = new HMac(HmacAlgorithm.HmacSHA256, API_SECRET.getBytes(StandardCharsets.UTF_8));
        SimpleDateFormat isoDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        isoDateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
        String timestamp = isoDateFormat.format(new Date());

        String url = StrUtil.format("/api/order?period={}&quantity={}&target_address={}", 1, 32000, tronAddress);
        String value = timestamp + "POST" + url;
        String sign = mac.digestBase64(value, false);

        String result = HttpRequest.post("https://api.mefree.net" + url)
                .header("MF-ACCESS-KEY", "6892420078")
                .header("MF-ACCESS-SIGN", sign)
                .header("MF-ACCESS-TIMESTAMP", timestamp)
                .execute().body();

        System.out.println(result);
        //{"code":0,"data":{"pay_hash":"cbbd0daa-7b08-4886-b708-1b787814669f","timestamp":0,"pay_address":"TUTuD2TEXsDLpr93REpveapPt5coNVAzx9","amount_sun":3000000,"energy_quantity":32000,"energy_period":1,"energy_expired_timestamp":0,"status":"TRANSFER_SUCCESS"}}
        JSONObject json = JSONObject.parseObject(result);
        if (json.getInteger("code") != 0) {
            System.out.println("请求成功");
            log.error("能量租赁失败，msg：{}，address：{}，----result：{}", json.getString("msg"), tronAddress, result);
        }
    }

}
