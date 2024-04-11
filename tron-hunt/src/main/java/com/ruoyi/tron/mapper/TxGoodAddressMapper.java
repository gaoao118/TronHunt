package com.ruoyi.tron.mapper;

import com.ruoyi.tron.domain.TxGoodAddress;

import java.util.List;

/**
 * 波场靓号Mapper接口
 *
 * @author tx
 * @date 2024-03-31
 */
public interface TxGoodAddressMapper {
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
     * 逻辑删除
     *
     * @param id 主键
     * @return 结果
     */
    int updateGoodAddressByDelFlag(Long id);

    /**
     * 删除波场靓号
     *
     * @param id 波场靓号主键
     * @return 结果
     */
    public int deleteTxGoodAddressById(Long id);

    /**
     * 批量删除波场靓号
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteTxGoodAddressByIds(Long[] ids);
}
