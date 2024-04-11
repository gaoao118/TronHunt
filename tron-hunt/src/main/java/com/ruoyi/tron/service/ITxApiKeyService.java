package com.ruoyi.tron.service;

import com.ruoyi.tron.domain.TxApiKey;

import java.util.List;

/**
 * 请求keyService接口
 *
 * @author tx
 * @date 2024-03-31
 */
public interface ITxApiKeyService {
    /**
     * 查询请求key
     *
     * @param id 请求key主键
     * @return 请求key
     */
    public TxApiKey selectTxApiKeyById(String id);

    /**
     * 查询请求key列表
     *
     * @param txApiKey 请求key
     * @return 请求key集合
     */
    public List<TxApiKey> selectTxApiKeyList(TxApiKey txApiKey);

    /**
     * 获取可用请求key集合
     *
     * @return 结果
     */
    List<TxApiKey> selectTxApiKeyUsable();

    /**
     * 新增请求key
     *
     * @param txApiKey 请求key
     * @return 结果
     */
    public int insertTxApiKey(TxApiKey txApiKey);

    /**
     * 修改请求key
     *
     * @param txApiKey 请求key
     * @return 结果
     */
    public int updateTxApiKey(TxApiKey txApiKey);

    /**
     * 批量删除请求key
     *
     * @param ids 需要删除的请求key主键集合
     * @return 结果
     */
    public int deleteTxApiKeyByIds(String[] ids);

    /**
     * 删除请求key信息
     *
     * @param id 请求key主键
     * @return 结果
     */
    public int deleteTxApiKeyById(String id);
}
