package com.ruoyi.web.controller.api;

import cn.hutool.core.thread.ThreadUtil;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.tron.service.ITronService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api")
public class ApiController {
    @Resource
    private ITronService tronService;

    /**
     * 执行扫描
     *
     * @param num 线程数量
     * @return 结果
     */
    @GetMapping("/tron")
    public R<?> executeTron(@RequestParam Integer num) {
        for (int i = 0; i < num; i++) {
            ThreadUtil.execute(() -> {
                do {
                    try {
                        tronService.executeTronSearch();
                    } catch (Exception e) {
                        //忽略
                    }
                } while (true);
            });
        }
        return R.ok();
    }

    /**
     * PQS检测
     *
     * @param num 检测次数
     */
    @GetMapping("/qpsCheck")
    public R<?> qpsCheck(@RequestParam Integer num) {
        tronService.qpsCheck(num);
        return R.ok();
    }

    /**
     * 实时数据统计
     */
    @GetMapping("/todayCount")
    public R<?> todayCount() {
        tronService.todayCount();
        return R.ok();
    }

}
