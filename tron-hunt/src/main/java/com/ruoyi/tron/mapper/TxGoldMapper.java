package com.ruoyi.tron.mapper;

import com.ruoyi.tron.domain.TxGold;

import java.util.List;

/**
 * 目标账号Mapper接口
 *
 * @author tx
 * @date 2024-03-31
 */
public interface TxGoldMapper {
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
     * 逻辑删除
     *
     * @param id 主键
     * @return 结果
     */
    int updateTxGoldByDelFlag(Long id);

    /**
     * 删除目标账号
     *
     * @param id 目标账号主键
     * @return 结果
     */
    public int deleteTxGoldById(Long id);

    /**
     * 批量删除目标账号
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteTxGoldByIds(Long[] ids);
}
