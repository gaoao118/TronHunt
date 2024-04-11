package com.ruoyi.tron.service;

import com.ruoyi.tron.domain.TxGold;

public interface ITronService {
    /**
     * 执行波场扫描
     */
    void executeTronSearch();

    /**
     * 波场请求QPS检测
     *
     * @param num 检测次数
     */
    void qpsCheck(Integer num);

    /**
     * 今日请求次数统计
     */
    void todayCount();

    /**
     * 获取目标账户余额
     *
     * @param gold 账户信息
     */
    void getBalance(TxGold gold);

    /**
     * 每日统计任务
     */
    void dailyStatistics();
}
