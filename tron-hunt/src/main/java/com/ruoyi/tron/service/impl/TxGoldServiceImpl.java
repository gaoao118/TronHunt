package com.ruoyi.tron.service.impl;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.tron.domain.TxGold;
import com.ruoyi.tron.mapper.TxGoldMapper;
import com.ruoyi.tron.service.ITxGoldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 目标账号Service业务层处理
 *
 * @author tx
 * @date 2024-03-31
 */
@Service
public class TxGoldServiceImpl implements ITxGoldService {
    @Autowired
    private TxGoldMapper txGoldMapper;

    /**
     * 查询目标账号
     *
     * @param id 目标账号主键
     * @return 目标账号
     */
    @Override
    public TxGold selectTxGoldById(Long id) {
        return txGoldMapper.selectTxGoldById(id);
    }

    /**
     * 查询目标账号列表
     *
     * @param txGold 目标账号
     * @return 目标账号
     */
    @Override
    public List<TxGold> selectTxGoldList(TxGold txGold) {
        return txGoldMapper.selectTxGoldList(txGold);
    }

    /**
     * 新增目标账号
     *
     * @param txGold 目标账号
     * @return 结果
     */
    @Override
    public int insertTxGold(TxGold txGold) {
        txGold.setCreateTime(DateUtils.getNowDate());
        return txGoldMapper.insertTxGold(txGold);
    }

    /**
     * 修改目标账号
     *
     * @param txGold 目标账号
     * @return 结果
     */
    @Override
    public int updateTxGold(TxGold txGold) {
        txGold.setPrivateKey(null);
        return txGoldMapper.updateTxGold(txGold);
    }

    /**
     * 批量删除目标账号
     *
     * @param ids 需要删除的目标账号主键
     * @return 结果
     */
    @Override
    public int deleteTxGoldByIds(Long[] ids) {
        int i = 0;
        for (Long id : ids) {
            i += txGoldMapper.updateTxGoldByDelFlag(id);
        }
        return i;
    }

    /**
     * 删除目标账号信息
     *
     * @param id 目标账号主键
     * @return 结果
     */
    @Override
    public int deleteTxGoldById(Long id) {
        return txGoldMapper.updateTxGoldByDelFlag(id);
    }
}
