package com.ruoyi.tron.service.impl;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.tron.domain.TxGoodAddress;
import com.ruoyi.tron.mapper.TxGoodAddressMapper;
import com.ruoyi.tron.service.ITxGoodAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 波场靓号Service业务层处理
 *
 * @author tx
 * @date 2024-03-31
 */
@Service
public class TxGoodAddressServiceImpl implements ITxGoodAddressService {
    @Autowired
    private TxGoodAddressMapper txGoodAddressMapper;

    /**
     * 查询波场靓号
     *
     * @param id 波场靓号主键
     * @return 波场靓号
     */
    @Override
    public TxGoodAddress selectTxGoodAddressById(Long id) {
        return txGoodAddressMapper.selectTxGoodAddressById(id);
    }

    /**
     * 查询波场靓号列表
     *
     * @param txGoodAddress 波场靓号
     * @return 波场靓号
     */
    @Override
    public List<TxGoodAddress> selectTxGoodAddressList(TxGoodAddress txGoodAddress) {
        return txGoodAddressMapper.selectTxGoodAddressList(txGoodAddress);
    }

    /**
     * 新增波场靓号
     *
     * @param txGoodAddress 波场靓号
     * @return 结果
     */
    @Override
    public int insertTxGoodAddress(TxGoodAddress txGoodAddress) {
        txGoodAddress.setCreateTime(DateUtils.getNowDate());
        return txGoodAddressMapper.insertTxGoodAddress(txGoodAddress);
    }

    /**
     * 修改波场靓号
     *
     * @param txGoodAddress 波场靓号
     * @return 结果
     */
    @Override
    public int updateTxGoodAddress(TxGoodAddress txGoodAddress) {
        txGoodAddress.setPrivateKey(null);
        return txGoodAddressMapper.updateTxGoodAddress(txGoodAddress);
    }

    /**
     * 批量删除波场靓号
     *
     * @param ids 需要删除的波场靓号主键
     * @return 结果
     */
    @Override
    public int deleteTxGoodAddressByIds(Long[] ids) {
        int i = 0;
        for (Long id : ids) {
            i += txGoodAddressMapper.updateGoodAddressByDelFlag(id);
        }
        return i;
    }

    /**
     * 删除波场靓号信息
     *
     * @param id 波场靓号主键
     * @return 结果
     */
    @Override
    public int deleteTxGoodAddressById(Long id) {
        return txGoodAddressMapper.deleteTxGoodAddressById(id);
    }
}
