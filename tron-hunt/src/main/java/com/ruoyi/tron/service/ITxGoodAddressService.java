package com.ruoyi.tron.service;

import com.ruoyi.tron.domain.TxGoodAddress;

import java.util.List;

/**
 * 波场靓号Service接口
 */
public interface ITxGoodAddressService {
    /**
     * 查询波场靓号
     *
     * @param id 波场靓号主键
     * @return 波场靓号
     */
    public TxGoodAddress selectTxGoodAddressById(Long id);

    /**
     * 查询波场靓号列表
     *
     * @param txGoodAddress 波场靓号
     * @return 波场靓号集合
     */
    public List<TxGoodAddress> selectTxGoodAddressList(TxGoodAddress txGoodAddress);

    /**
     * 新增波场靓号
     *
     * @param txGoodAddress 波场靓号
     * @return 结果
     */
    public int insertTxGoodAddress(TxGoodAddress txGoodAddress);

    /**
     * 修改波场靓号
     *
     * @param txGoodAddress 波场靓号
     * @return 结果
     */
    public int updateTxGoodAddress(TxGoodAddress txGoodAddress);

    /**
     * 批量删除波场靓号
     *
     * @param ids 需要删除的波场靓号主键集合
     * @return 结果
     */
    public int deleteTxGoodAddressByIds(Long[] ids);

    /**
     * 删除波场靓号信息
     *
     * @param id 波场靓号主键
     * @return 结果
     */
    public int deleteTxGoodAddressById(Long id);
}
