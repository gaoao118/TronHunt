package com.ruoyi.tron.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson2.JSONObject;
import com.ruoyi.common.constant.CacheConstants;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.tron.domain.TxApiKey;
import com.ruoyi.tron.domain.dto.ApiKeyDto;
import com.ruoyi.tron.mapper.TxApiKeyMapper;
import com.ruoyi.tron.service.ITxApiKeyService;
import com.ruoyi.tron.utils.TronUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.tron.trident.core.ApiWrapper;
import org.tron.trident.core.key.KeyPair;
import org.tron.trident.proto.Response;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 请求keyService业务层处理
 */
@Service
public class TxApiKeyServiceImpl implements ITxApiKeyService {
    @Resource
    private TxApiKeyMapper txApiKeyMapper;
    @Resource
    private RedisCache redisCache;
    private static final Logger log = LoggerFactory.getLogger(TxApiKeyServiceImpl.class);

    /**
     * 查询请求key
     *
     * @param id 请求key主键
     * @return 请求key
     */
    @Override
    public TxApiKey selectTxApiKeyById(String id) {
        return txApiKeyMapper.selectTxApiKeyById(id);
    }

    /**
     * 查询请求key列表
     *
     * @param txApiKey 请求key
     * @return 请求key
     */
    @Override
    public List<TxApiKey> selectTxApiKeyList(TxApiKey txApiKey) {
        return txApiKeyMapper.selectTxApiKeyList(txApiKey);
    }

    @Override
    public List<TxApiKey> selectTxApiKeyUsable() {
        return txApiKeyMapper.selectTxApiKeyUsable();
    }

    /**
     * 新增请求key
     *
     * @param txApiKey 请求key
     * @return 结果
     */
    @Override
    public int insertTxApiKey(TxApiKey txApiKey) {
        if (StrUtil.isBlank(txApiKey.getPrivateKey())) {
            KeyPair keyPair = KeyPair.generate();
            txApiKey.setPrivateKey(keyPair.toPrivateKey());
        } else {
            txApiKey.setPrivateKey(StrUtil.cleanBlank(txApiKey.getPrivateKey()));
        }
        txApiKey.setApiKey(StrUtil.cleanBlank(txApiKey.getApiKey()));
        ApiWrapper wrapper = ApiWrapper.ofMainnet(txApiKey.getPrivateKey(), txApiKey.getApiKey());
        Response.Account account = wrapper.getAccount(wrapper.keyPair.toBase58CheckAddress());
        log.info(account.toString());
        txApiKey.setPrivateKey(TronUtil.encryptByPublicKey(txApiKey.getPrivateKey()));
        txApiKey.setStatus(1);
        txApiKey.setCreateTime(DateUtils.getNowDate());
        int i = txApiKeyMapper.insertTxApiKey(txApiKey);
        Boolean hasKey = redisCache.hasKey(CacheConstants.TRON_API_KEY_DAY + DateUtil.today());
        if (hasKey) {
            String key = CacheConstants.TRON_API_KEY_LIST + DateUtil.today();
            ApiKeyDto dto = new ApiKeyDto();
            dto.setId(txApiKey.getId());
            dto.setApiKey(txApiKey.getApiKey());
            dto.setPrivateKey(txApiKey.getPrivateKey());
            dto.setUsable(txApiKey.getUsable());
            String json = JSONObject.toJSONString(dto);
            List<String> list = new ArrayList<>();
            list.add(json);
            redisCache.setCacheList(key, list);
        }
        return i;
    }

    /**
     * 修改请求key
     *
     * @param txApiKey 请求key
     * @return 结果
     */
    @Override
    public int updateTxApiKey(TxApiKey txApiKey) {
        ApiWrapper wrapper = ApiWrapper.ofMainnet(TronUtil.decryptByPrivateKey(txApiKey.getPrivateKey()), txApiKey.getApiKey());
        Response.Account account = wrapper.getAccount(wrapper.keyPair.toBase58CheckAddress());
        log.info(account.toString());
        txApiKey.setPrivateKey(null);
        return txApiKeyMapper.updateTxApiKey(txApiKey);
    }

    /**
     * 批量删除请求key
     *
     * @param ids 需要删除的请求key主键
     * @return 结果
     */
    @Override
    public int deleteTxApiKeyByIds(String[] ids) {
        return txApiKeyMapper.deleteTxApiKeyByIds(ids);
    }

    /**
     * 删除请求key信息
     *
     * @param id 请求key主键
     * @return 结果
     */
    @Override
    public int deleteTxApiKeyById(String id) {
        return txApiKeyMapper.deleteTxApiKeyById(id);
    }
}
