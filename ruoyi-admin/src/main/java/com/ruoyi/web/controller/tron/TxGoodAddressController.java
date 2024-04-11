package com.ruoyi.web.controller.tron;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.tron.domain.TxGoodAddress;
import com.ruoyi.tron.service.ITxGoodAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 波场靓号Controller
 *
 * @author tx
 * @date 2024-03-31
 */
@RestController
@RequestMapping("/tron/goodAddress")
public class TxGoodAddressController extends BaseController {
    @Autowired
    private ITxGoodAddressService txGoodAddressService;

    /**
     * 查询波场靓号列表
     */
    @PreAuthorize("@ss.hasPermi('tron:goodAddress:list')")
    @GetMapping("/list")
    public TableDataInfo list(TxGoodAddress txGoodAddress) {
        startPage();
        List<TxGoodAddress> list = txGoodAddressService.selectTxGoodAddressList(txGoodAddress);
        for (TxGoodAddress address : list) {
            address.setPrivateKey(null);
        }
        return getDataTable(list);
    }

    /**
     * 获取波场靓号详细信息
     */
    @PreAuthorize("@ss.hasPermi('tron:goodAddress:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(txGoodAddressService.selectTxGoodAddressById(id));
    }

    /**
     * 修改波场靓号
     */
    @PreAuthorize("@ss.hasPermi('tron:goodAddress:edit')")
    @Log(title = "波场靓号", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TxGoodAddress txGoodAddress) {
        return toAjax(txGoodAddressService.updateTxGoodAddress(txGoodAddress));
    }

    /**
     * 删除波场靓号
     */
    @PreAuthorize("@ss.hasPermi('tron:goodAddress:remove')")
    @Log(title = "波场靓号", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(txGoodAddressService.deleteTxGoodAddressByIds(ids));
    }
}
