package com.ruoyi.tron.mapper;

import com.ruoyi.tron.domain.TxApiKey;

import java.util.List;

/**
 * 请求keyMapper接口
 */
public interface TxApiKeyMapper {
    /**
     * 获取可用请求key集合
     *
     * @return 结果
     */
    List<TxApiKey> selectTxApiKeyUsable();

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
     * 删除请求key
     *
     * @param id 请求key主键
     * @return 结果
     */
    public int deleteTxApiKeyById(String id);

    /**
     * 批量删除请求key
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteTxApiKeyByIds(String[] ids);
}
