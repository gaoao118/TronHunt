package com.ruoyi.test;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.core.util.StrUtil;
import com.ruoyi.RuoYiApplication;
import com.ruoyi.common.constant.CacheConstants;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.tron.domain.TxApiKey;
import com.ruoyi.tron.service.ITronService;
import com.ruoyi.tron.service.ITxApiKeyService;
import com.ruoyi.tron.utils.TelegramUtil;
import com.ruoyi.tron.utils.TronUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.tron.trident.core.ApiWrapper;
import org.tron.trident.core.key.KeyPair;
import org.tron.trident.proto.Response;
import org.tron.trident.utils.Convert;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RuoYiApplication.class)
public class TronTest {
    private static final Logger log = LoggerFactory.getLogger(TronTest.class);
    private ITronService tronTestService;
    @Resource
    private RedisCache redisCache;
    @Resource
    private ITxApiKeyService apiKeyService;

    /**
     * # ApiWrapper原装 - 获取trx余额
     */
    @Test
    public void simple_balanceOfTrx() {
        ApiWrapper wrapper = ApiWrapper.ofMainnet("787f3a1b1cc8b4ef259402b0b5c71ba4be1c3267206084c1a92bfd416c80a68f", "705ccf76-cdb1-4da4-8183-5154c636ee8c");


        KeyPair keyPair = KeyPair.generate();
        String address = keyPair.toBase58CheckAddress();
        // 地址
//        String address = wrapper.keyPair.toBase58CheckAddress();
        // 获取账户信息
        Response.Account account = wrapper.getAccount(address);
        String string = account.toString();
        System.out.println(string);
        System.out.println(StringUtils.isEmpty(account.toString()));
        System.out.println("-----------");

        System.out.println(account.isInitialized());

        // 余额
        long balance = account.getBalance();
        // 真实余额
        BigDecimal trx = Convert.fromSun(new BigDecimal(balance), Convert.Unit.TRX);
        log.debug("trx余额：{}", trx.toString());

        long sum = account.getFrozenList().stream().mapToLong(Response.Account.Frozen::getFrozenBalance).sum();
        long frozenBalance = account.getAccountResource().getFrozenBalanceForEnergy().getFrozenBalance();
        long frozen = sum + frozenBalance;
        BigDecimal frozenTrx = Convert.fromSun(new BigDecimal(frozen), Convert.Unit.TRX);
        log.debug("trx质押：{}", frozenTrx);

        log.debug("trx 总余额：{}", trx.add(frozenTrx));
    }

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            KeyPair keyPair = KeyPair.generate();
            System.out.println(keyPair.toBase58CheckAddress());
            System.out.println(keyPair.toPrivateKey());
            System.out.println("---------------------------------");
        }
    }

    @Test
    public void testRedis() {
        String key = "gyntrSKiKa3UT7OUCDnPyVRKerMghT6iPsJ+s7P6abx6m22VWjb1V289FyQwJol2TWcZo27E6dXJE3Zn3XJHOw6Sz/c3LYSG+38CIRBbSW0sGAGfxMqkM1TMreD/SMDOjspOLopEctnn9OzQAXJXo5wRCHf2dKnanW7idbAOpNc=";
        KeyPair pair = new KeyPair(TronUtil.decryptByPrivateKey(key));
        System.out.println(pair.toBase58CheckAddress());
        System.out.println(pair.toPrivateKey());
    }

    @Test
    public void testRedisList() {
        List<TxApiKey> apiKeyList = apiKeyService.selectTxApiKeyUsable();
        List<String> list = apiKeyList.stream().map(i -> String.format(CacheConstants.TRON_API_KEY_COUNT, DateUtil.today(), i.getApiKey())).collect(Collectors.toList());
        long lastCount = this.getCount(list);
        long lastTime = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++) {
            ThreadUtil.sleep(1000);
            long endTime = System.currentTimeMillis();
            long count = this.getCount(list);
            System.out.println("----------------------------");
            System.out.println(count - lastCount);
            lastCount = count;
        }
    }


    private long getCount(List<String> keyList) {
        long count = 0;
        for (String key : keyList) {
            count += redisCache.getLimitAmount(key);
        }
        return count;
    }
}
