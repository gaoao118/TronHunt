package com.ruoyi.quartz.task;

import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.tron.service.ITronService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 定时任务调度测试
 *
 * @author ruoyi
 */
@Component("ryTask")
public class RyTask {
    @Resource
    private ITronService tronService;

    public void ryMultipleParams(String s, Boolean b, Long l, Double d, Integer i) {
        System.out.println(StringUtils.format("执行多参方法： 字符串类型{}，布尔类型{}，长整型{}，浮点型{}，整形{}", s, b, l, d, i));
    }

    public void ryParams(String params) {
        System.out.println("执行有参方法：" + params);
    }

    public void ryNoParams() {
        System.out.println("执行无参方法");
    }

    /**
     * 每日请求次数统计
     */
    public void dailyActivity() {
        tronService.dailyStatistics();
    }

    /**
     * 实时数据统计
     */
    public void toDayCount() {
        tronService.todayCount();
    }

}
