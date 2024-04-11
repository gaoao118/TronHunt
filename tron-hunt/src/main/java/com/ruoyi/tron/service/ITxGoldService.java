package com.ruoyi.tron.service;

import com.ruoyi.tron.domain.TxGold;

import java.util.List;

/**
 * 目标账号Service接口
 */
public interface ITxGoldService {
    /**
     * 查询目标账号
     *
     * @param id 目标账号主键
     * @return 目标账号
     */
    public TxGold selectTxGoldById(Long id);

    /**
     * 查询目标账号列表
     *
     * @param txGold 目标账号
     * @return 目标账号集合
     */
    public List<TxGold> selectTxGoldList(TxGold txGold);

    /**
     * 新增目标账号
     *
     * @param txGold 目标账号
     * @return 结果
     */
    public int insertTxGold(TxGold txGold);

    /**
     * 修改目标账号
     *
     * @param txGold 目标账号
     * @return 结果
     */
    public int updateTxGold(TxGold txGold);

    /**
     * 批量删除目标账号
     *
     * @param ids 需要删除的目标账号主键集合
     * @return 结果
     */
    public int deleteTxGoldByIds(Long[] ids);

    /**
     * 删除目标账号信息
     *
     * @param id 目标账号主键
     * @return 结果
     */
    public int deleteTxGoldById(Long id);
}
