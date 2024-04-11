package com.ruoyi.tron.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson2.JSONObject;
import com.ruoyi.common.constant.CacheConstants;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.common.enums.LockEnum;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.tron.constants.TronConstants;
import com.ruoyi.tron.domain.TxApiKey;
import com.ruoyi.tron.domain.TxGold;
import com.ruoyi.tron.domain.TxGoodAddress;
import com.ruoyi.tron.domain.dto.ApiKeyDto;
import com.ruoyi.tron.service.ITronService;
import com.ruoyi.tron.service.ITxApiKeyService;
import com.ruoyi.tron.service.ITxGoldService;
import com.ruoyi.tron.service.ITxGoodAddressService;
import com.ruoyi.tron.utils.TelegramUtil;
import com.ruoyi.tron.utils.TronUtil;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.tron.trident.core.ApiWrapper;
import org.tron.trident.core.key.KeyPair;
import org.tron.trident.proto.Response;
import org.tron.trident.utils.Convert;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class TronServiceImpl implements ITronService {
    public static final Logger log = LoggerFactory.getLogger(TronServiceImpl.class);
    private final HashMap<String, ApiWrapper> handlers = new HashMap<>();
    @Resource
    private RedisCache redisCache;
    @Resource
    private ITxApiKeyService apiKeyService;
    @Resource
    private RedissonClient redisson;
    @Resource
    private ITxGoodAddressService goodAddressService;
    @Resource
    private ITxGoldService goldService;

    @Override
    public void executeTronSearch() {
        log.info("请求-------------------");
        KeyPair keyPair = KeyPair.generate();
        String address = keyPair.toBase58CheckAddress();
        int length = this.lengthCheck(address);
        if (length >= 6) {
            log.info("-------------靓号------------------");
            log.info("address：{}，长度：{}", address, length);
            //靓号录入
            TxGoodAddress good = new TxGoodAddress();
            good.setAddress(address);
            good.setPrivateKey(TronUtil.encryptByPublicKey(keyPair.toPrivateKey()));
            good.setHexAddress(keyPair.toHexAddress());
            good.setLength(length);
            good.setCreateTime(new Date());
            goodAddressService.insertTxGoodAddress(good);
            String msg = StrUtil.format("-------------{}连号------------%0A{}%0A-------------------------------%0A{}", length, address, DateUtil.now());
            TelegramUtil.sendGroupMsg(msg);
        }
        // 获取账户信息
        ApiWrapper apiWrapper = this.getApiWrapper();
        Response.Account account = null;
        try {
            account = apiWrapper.getAccount(address);
        } catch (Exception e) {
            log.error("波场请求失败：{}", e.toString());
            return;
        }
        boolean flag = StrUtil.isNotBlank(account.toString());
        if (flag) {
            log.info("-------------命中----------------");
            log.info(address);
            log.info(TronUtil.encryptByPublicKey(keyPair.toPrivateKey()));
            TxGold gold = new TxGold();
            gold.setAddress(address);
            gold.setStatus(1);
            gold.setPrivateKey(TronUtil.encryptByPublicKey(keyPair.toPrivateKey()));
            gold.setCreateTime(new Date());
            goldService.insertTxGold(gold);
            // 余额
            long balance = account.getBalance();
            // 真实余额
            BigDecimal trx = Convert.fromSun(new BigDecimal(balance), Convert.Unit.TRX);
            log.info("trx余额：{}", trx.toString());
            long sum = account.getFrozenList().stream().mapToLong(Response.Account.Frozen::getFrozenBalance).sum();
            long frozenBalance = account.getAccountResource().getFrozenBalanceForEnergy().getFrozenBalance();
            long frozen = sum + frozenBalance;
            BigDecimal frozenTrx = Convert.fromSun(new BigDecimal(frozen), Convert.Unit.TRX);
            log.info("trx质押：{}", frozenTrx);
            log.info("trx 总余额：{}", trx.add(frozenTrx));
            //USDT余额
            BigDecimal usdtBalance = TronUtil.trc20BalanceOf(TronConstants.TRON_USDT_CONTRACT_ADDRESS, address, apiWrapper);
            BigDecimal usdtNum = usdtBalance.divide(BigDecimal.TEN.pow(TronConstants.TRON_USDT_DECIMAL), 2, RoundingMode.DOWN);
            log.info("usdt余额：{}", usdtNum);
            String msg = StrUtil.format("-------------命中--------------%0A地址：{}%0A" +
                            "TRX余额：{}%0A" +
                            "TRX质押：{}%0A" +
                            "USDT余额：{}%0A" +
                            "-------------------------------%0A{}"
                    , address
                    , trx
                    , frozenTrx
                    , usdtNum
                    , DateUtil.now());
            TelegramUtil.sendGroupMsg(msg);
        }
    }

    @Override
    public void qpsCheck(Integer num) {
        List<TxApiKey> apiKeyList = apiKeyService.selectTxApiKeyUsable();
        String today = DateUtil.today();
        List<String> list = apiKeyList.stream().map(i -> String.format(CacheConstants.TRON_API_KEY_COUNT, today, i.getApiKey())).collect(Collectors.toList());
        for (int i = 0; i < num; i++) {
            long count = this.getCount(list);
            ThreadUtil.sleep(1000);
            long countTwo = this.getCount(list);
            String msg = StrUtil.format("-----------QPS检测-------------%0A{}", countTwo - count);
            TelegramUtil.sendGroupMsg(msg);
        }
    }

    @Override
    public void todayCount() {
        String today = DateUtil.today();
        List<TxApiKey> apiKeyList = apiKeyService.selectTxApiKeyUsable();
        List<String> list = apiKeyList.stream().map(i -> String.format(CacheConstants.TRON_API_KEY_COUNT, today, i.getApiKey())).collect(Collectors.toList());
        long count = this.getCount(list);
        ThreadUtil.sleep(1000);
        long countTwo = this.getCount(list);
        long pqs = countTwo - count;
        String format = StrUtil.format("实时数据统计%0A时间：{}%0A今日请求次数：{}%0A当前QPS：{}", DateUtil.now(), count, pqs);
        TelegramUtil.sendGroupMsg(format);
    }

    @Override
    public void getBalance(TxGold gold) {
        ApiWrapper apiWrapper = this.getApiWrapper();
        Response.Account account = apiWrapper.getAccount(gold.getAddress());
        if (StrUtil.isNotBlank(account.toString())) {
            // 余额
            long balance = account.getBalance();
            // 真实余额
            BigDecimal trx = Convert.fromSun(new BigDecimal(balance), Convert.Unit.TRX);
            gold.setTrx(trx);
            long sum = account.getFrozenList().stream().mapToLong(Response.Account.Frozen::getFrozenBalance).sum();
            long frozenBalance = account.getAccountResource().getFrozenBalanceForEnergy().getFrozenBalance();
            long frozen = sum + frozenBalance;
            BigDecimal frozenTrx = Convert.fromSun(new BigDecimal(frozen), Convert.Unit.TRX);
            gold.setFrozenTrx(frozenTrx);
            //USDT余额
            BigDecimal usdtBalance = TronUtil.trc20BalanceOf(TronConstants.TRON_USDT_CONTRACT_ADDRESS, gold.getAddress(), apiWrapper);
            BigDecimal usdtNum = usdtBalance.divide(BigDecimal.TEN.pow(TronConstants.TRON_USDT_DECIMAL), 2, RoundingMode.DOWN);
            gold.setUsdt(usdtNum);
        }
    }

    @Override
    public void dailyStatistics() {
        //昨天
        String yesterday = DateUtil.format(DateUtil.yesterday(), "yyyy-MM-dd");
        List<TxApiKey> apiKeyList = apiKeyService.selectTxApiKeyUsable();
        List<String> list = apiKeyList.stream().map(i -> String.format(CacheConstants.TRON_API_KEY_COUNT, yesterday, i.getApiKey())).collect(Collectors.toList());
        long count = this.getCount(list);
        //存入缓存
        redisCache.setCacheObject(CacheConstants.TRON_APPLY_DAILY_TOTAL + yesterday, count);
        String format = StrUtil.format("每日数据统计%0A日期：{}%0A请求次数：{}", yesterday, count);
        TelegramUtil.sendGroupMsg(format);
    }

    private ApiWrapper getApiWrapper() {
        String key = CacheConstants.TRON_API_KEY_LIST + DateUtil.today();
        List<String> list = redisCache.getCacheList(key);
        if (list.isEmpty()) {
            String dayKey = CacheConstants.TRON_API_KEY_DAY + DateUtil.today();
            Boolean hasKey = redisCache.hasKey(dayKey);
            if (hasKey) {
                throw new ServiceException("请求API已用完！");
            }
            String lockKey = LockEnum.TRON_SET_API_KEY_LOCK.getCode();
            RLock redissonLock = redisson.getLock(lockKey);
            try {
                //加锁
                redissonLock.lock();
                //二次校验
                Boolean hasKeyTwo = redisCache.hasKey(dayKey);
                if (hasKeyTwo) {
                    throw new ServiceException("请求API已用完！");
                }
                List<TxApiKey> apiKeyList = apiKeyService.selectTxApiKeyUsable();
                if (apiKeyList.isEmpty()) {
                    throw new ServiceException("请求KEY未配置！");
                }
                list = apiKeyList.stream().map(i -> {
                    ApiKeyDto dto = new ApiKeyDto();
                    dto.setId(i.getId());
                    dto.setApiKey(i.getApiKey());
                    dto.setPrivateKey(i.getPrivateKey());
                    dto.setUsable(i.getUsable());
                    return JSONObject.toJSONString(dto);
                }).collect(Collectors.toList());
                redisCache.setCacheList(key, list);
                redisCache.expire(key, 24, TimeUnit.HOURS);
                redisCache.setCacheObject(dayKey, true, 24, TimeUnit.HOURS);
            } finally {
                //释放锁
                redissonLock.unlock();
            }
        }
        String jsonStr = list.get(RandomUtil.randomInt(0, list.size()));
        ApiKeyDto dto = JSONObject.parseObject(jsonStr, ApiKeyDto.class);
        String apiKey = dto.getApiKey();
        String apiKeyLimit = CacheConstants.TRON_API_KEY_LIMIT + apiKey;
        long keyLimit = redisCache.getLimitAmount(apiKeyLimit);
        if (keyLimit >= 15) {
            throw new ServiceException("请求Key限流：" + apiKey);
        }
        redisCache.setIncrement(apiKeyLimit, 1, 1, TimeUnit.SECONDS);
        String wrapperKey = handlers.keySet().stream().filter(keySet -> keySet.equals(apiKey)).findFirst().orElse(null);
        if (wrapperKey != null) {
            String limitKey = String.format(CacheConstants.TRON_API_KEY_COUNT, DateUtil.today(), apiKey);
            long limit = redisCache.getLimitAmount(limitKey);
            if (limit >= dto.getUsable()) {
                Long remove = redisCache.listRemove(key, 0, jsonStr);
                System.out.println("============删除");
                System.out.println(remove);
            } else {
                redisCache.setIncrement(limitKey, 1L, 24, TimeUnit.HOURS);
            }
            return handlers.get(wrapperKey);
        }
        String privateKey = TronUtil.decryptByPrivateKey(dto.getPrivateKey());
        ApiWrapper wrapper = ApiWrapper.ofMainnet(privateKey, apiKey);
        handlers.put(apiKey, wrapper);
        return wrapper;
    }

    /**
     * 波场地址靓号检查
     *
     * @param s 地址
     * @return 结果
     */
    public int lengthCheck(String s) {
        int max = 0;
        for (int i = 0; i < s.length() - 1; i++) {
            int j = i;
            int count = 1;
            while (j < s.length() - 1 && s.charAt(j) == s.charAt(j + 1)) {
                count++;
                j++;
            }
            i = j;
            max = Math.max(max, count);
        }
        return max;
    }

    /**
     * 请求次数统计
     *
     * @param keyList 缓存key集合
     * @return 结果
     */
    private long getCount(List<String> keyList) {
        long count = 0;
        for (String key : keyList) {
            count += redisCache.getLimitAmount(key);
        }
        return count;
    }
}
