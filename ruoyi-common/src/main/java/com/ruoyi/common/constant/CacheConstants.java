package com.ruoyi.common.constant;

/**
 * 缓存的key 常量
 *
 * @author ruoyi
 */
public class CacheConstants {
    /**
     * 登录用户 redis key
     */
    public static final String LOGIN_TOKEN_KEY = "login_tokens:";

    /**
     * 验证码 redis key
     */
    public static final String CAPTCHA_CODE_KEY = "captcha_codes:";

    /**
     * 参数管理 cache key
     */
    public static final String SYS_CONFIG_KEY = "sys_config:";

    /**
     * 字典管理 cache key
     */
    public static final String SYS_DICT_KEY = "sys_dict:";

    /**
     * 防重提交 redis key
     */
    public static final String REPEAT_SUBMIT_KEY = "repeat_submit:";

    /**
     * 限流 redis key
     */
    public static final String RATE_LIMIT_KEY = "rate_limit:";

    /**
     * 登录账户密码错误次数 redis key
     */
    public static final String PWD_ERR_CNT_KEY = "pwd_err_cnt:";

    /**
     * 波场请求Key集合
     */
    public static final String TRON_API_KEY_LIST = "tron_api_key_list:";

    /**
     * 波场请求key日期标识
     */
    public static final String TRON_API_KEY_DAY = "tron_api_key_day:";

    /**
     * 波场请求key统计
     */
    public static final String TRON_API_KEY_COUNT = "tron_api_key_count:%s:%s";

    /**
     * 波场请求key限流
     */
    public static final String TRON_API_KEY_LIMIT = "tron_api_key_limit:";

    /**
     * 波场请求每日统计
     */
    public static final String TRON_APPLY_DAILY_TOTAL = "tron_apply_daily_total:";

}
