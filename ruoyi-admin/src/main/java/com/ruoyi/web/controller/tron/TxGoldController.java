package com.ruoyi.web.controller.tron;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.tron.domain.TxGold;
import com.ruoyi.tron.service.ITronService;
import com.ruoyi.tron.service.ITxGoldService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 目标账号Controller
 *
 * @author tx
 * @date 2024-03-31
 */
@RestController
@RequestMapping("/tron/gold")
public class TxGoldController extends BaseController {
    @Resource
    private ITxGoldService txGoldService;
    @Resource
    private ITronService tronService;

    /**
     * 查询目标账号列表
     */
    @PreAuthorize("@ss.hasPermi('tron:gold:list')")
    @GetMapping("/list")
    public TableDataInfo list(TxGold txGold) {
        startPage();
        List<TxGold> list = txGoldService.selectTxGoldList(txGold);
        for (TxGold gold : list) {
            gold.setPrivateKey(null);
            try {
                tronService.getBalance(gold);
            } catch (Exception e) {
                //异常忽略
            }
        }
        return getDataTable(list);
    }

    /**
     * 获取目标账号详细信息
     */
    @PreAuthorize("@ss.hasPermi('tron:gold:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(txGoldService.selectTxGoldById(id));
    }

    /**
     * 修改目标账号
     */
    @PreAuthorize("@ss.hasPermi('tron:gold:edit')")
    @Log(title = "目标账号", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TxGold txGold) {
        return toAjax(txGoldService.updateTxGold(txGold));
    }

    /**
     * 删除目标账号
     */
    @PreAuthorize("@ss.hasPermi('tron:gold:remove')")
    @Log(title = "目标账号", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(txGoldService.deleteTxGoldByIds(ids));
    }
}
