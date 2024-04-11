package com.ruoyi.tron.utils;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import com.ruoyi.common.exception.ServiceException;
import org.tron.trident.abi.FunctionEncoder;
import org.tron.trident.abi.FunctionReturnDecoder;
import org.tron.trident.abi.TypeReference;
import org.tron.trident.abi.datatypes.Address;
import org.tron.trident.abi.datatypes.Function;
import org.tron.trident.abi.datatypes.generated.Uint256;
import org.tron.trident.core.ApiWrapper;
import org.tron.trident.proto.Contract;
import org.tron.trident.proto.Response;
import org.tron.trident.utils.Numeric;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collections;

/**
 * 支付模块工具类
 */
public class TronUtil {
    /**
     * app公钥
     */
    private static final String PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDaK21wRjOioVdhXGaOdMWn7+nSo9hMzLDEgH0126QmD9A/A+c19jtjVStUXO+blsP+WAS6cwo5n18diAlkJQngUBG5+qzxL5QhNPbZ5l+TltOis6Kbky/EIfhpVpovgbk2YtRm2IstnzTr8rrthl0NtG6ZMgQFOxGdraecHZ5XVQIDAQAB";

    /**
     * app私钥
     */
    private static final String PRIVATE_KEY = "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBANorbXBGM6KhV2FcZo50xafv6dKj2EzMsMSAfTXbpCYP0D8D5zX2O2NVK1Rc75uWw/5YBLpzCjmfXx2ICWQlCeBQEbn6rPEvlCE09tnmX5OW06KzopuTL8Qh+GlWmi+BuTZi1GbYiy2fNOvyuu2GXQ20bpkyBAU7EZ2tp5wdnldVAgMBAAECgYAP6kjD/s2yffGUD9EIGwWg5XQEYovGDSKSBcuHMRwi41bDhLiKVl7pCqQBYKbhY0NYm7l7PnwkTaIl0zAtht7W7zRDNLjJkEnQg5opQ/6mvzyTVwk3TCZrO5J6KABKCXWAK+4YbPEowzaLmTGT7VYT+2DktHrU97IOy5u36YYRCwJBAPjhaJ08MA+WT2xGkjsr0q+ULLW7GTHa1T0WhP3fJhwoxUckVSTeWe0OqDMY/XUMknlZFWpUg9f6CEOUsaybSEsCQQDgaR5KcMQQ5dBsRlE1q61h6Dp+tYFTSnQA+8B/eZLCN3UTCNcXrcVaIx2OgKbYtLIXmQ7Lkx6L5xIHC++K0FrfAkAubsd4xljlH5LzzXrMxVFKZqesxBpgPcaY3hGz16Uhjc24hyPHfCBk5N4molvvMYhGUYN4UgpDCifui9hWLqZfAkBOR7zeQLnUHyZ2Lo0ziKBhj82f15a2RDl1Alyi2vi7sPaw3huR0fcOu0MbK3uICng5TRXhI3+7U65xMNOte4LHAkB0R49++Z+dkAiVu0MCuzaEYhRxAu5TYOOmgGGsTm/zILlSFPoBAbX7tnW7892AkX4y3jhcqPqOhR78ffEXeF0Q";

    private static final RSA RSA;

    static {
        RSA = new RSA(PRIVATE_KEY, PUBLIC_KEY);
    }

    /**
     * 私钥解密
     *
     * @param data 已加密数据
     * @return 结果
     */
    public static String decryptByPrivateKey(String data) {
        if (StrUtil.isBlank(data)) {
            throw new ServiceException("加密数据不能为空！");
        }
        String string = RSA.decryptStr(data, KeyType.PrivateKey);
        return string;
    }

    /**
     * 公钥加密
     *
     * @param data 待加密数据
     * @return 结果
     */
    public static String encryptByPublicKey(String data) {
        return RSA.encryptBase64(data, KeyType.PublicKey);
    }

    /**
     * 查询trc20余额
     *
     * @param contractAddress 合约地址
     * @param address         账户地址
     * @return 余额
     */
    public static BigDecimal trc20BalanceOf(String contractAddress, String address, ApiWrapper wrapper) {
        // 构造trc20查余额函数
        Function balanceOf = new Function(
                "balanceOf",
                Collections.singletonList(new Address(address)),
                Collections.singletonList(new TypeReference<Uint256>() {
                })
        );
        // 编码
        String encodedHex = FunctionEncoder.encode(balanceOf);
        // 构造trc20合约信息
        Contract.TriggerSmartContract contract = Contract.TriggerSmartContract.newBuilder()
                .setContractAddress(ApiWrapper.parseAddress(contractAddress))
                .setData(ApiWrapper.parseHex(encodedHex))
                .build();
        // 查询余额
        Response.TransactionExtention tx = wrapper.blockingStub.triggerConstantContract(contract);
        // 余额
        String result = Numeric.toHexString(tx.getConstantResult(0).toByteArray());
        BigInteger balance = (BigInteger) FunctionReturnDecoder.decode(result, balanceOf.getOutputParameters()).get(0).getValue();
        return new BigDecimal(balance);
    }
}
