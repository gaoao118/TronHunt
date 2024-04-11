package com.ruoyi.tron.utils;

import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.URLUtil;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson2.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * telegram工具类
 */
public class TelegramUtil {
    private static final Logger log = LoggerFactory.getLogger(TelegramUtil.class);

    /**
     * 机器人Key
     */
    private static final String KEY = "bot6647024281:AAG6QcimBdbXEPIBOjVD6S7qetQ0XvVOXcs";

    /**
     * 群聊消息id
     */
    private static final String GROUP_CHAT_ID = "-1002074951296";

    //TEST：-1001975234722
    //OKM：-1002029477160
    //YH：-1001850602081
    //OKC：-1002073885961
    //PAY：-1002083520131
    //波场搜索：-1002074951296

    //https://api.telegram.org/bot6647024281:AAG6QcimBdbXEPIBOjVD6S7qetQ0XvVOXcs/sendMessage?chat_id=-1001850602081&text=hellohellohello

    //获取机器人ChatId
    //https://api.telegram.org/bot6647024281:AAG6QcimBdbXEPIBOjVD6S7qetQ0XvVOXcs/getUpdates

    public static void sendGroupMsg(String text) {
        String url = StrUtil.format("https://api.telegram.org/{}/sendMessage?chat_id={}&text={}", KEY, GROUP_CHAT_ID, text);
        String result = HttpUtil.get("https://thingproxy.freeboard.io/fetch/" + URLUtil.encode(url));
        JSONObject json = JSONObject.parseObject(result);
        if (!json.getBoolean("ok")) {
            log.error("Telegram群聊信息发送失败");
        }
    }


}
