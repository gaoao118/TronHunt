package com.ruoyi.web.controller.tron;

import cn.hutool.core.date.DateUtil;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.constant.CacheConstants;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.tron.domain.TxApiKey;
import com.ruoyi.tron.service.ITxApiKeyService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 请求keyController
 */
@RestController
@RequestMapping("/apiKey/apiKey")
public class TxApiKeyController extends BaseController {
    @Resource
    private ITxApiKeyService txApiKeyService;
    @Resource
    private RedisCache redisCache;

    /**
     * 查询请求key列表
     */
    @PreAuthorize("@ss.hasPermi('apiKey:apiKey:list')")
    @GetMapping("/list")
    public TableDataInfo list(TxApiKey txApiKey) {
        startPage();
        List<TxApiKey> list = txApiKeyService.selectTxApiKeyList(txApiKey);
        for (TxApiKey apiKey : list) {
            String limitKey = String.format(CacheConstants.TRON_API_KEY_COUNT, DateUtil.today(), apiKey.getApiKey());
            long limit = redisCache.getLimitAmount(limitKey);
            apiKey.setUsable(apiKey.getUsable() - limit);
            if (apiKey.getUsable() <= 0) {
                apiKey.setStatus(2);
            }
        }
        return getDataTable(list);
    }

    /**
     * 导出请求key列表
     */
    @PreAuthorize("@ss.hasPermi('apiKey:apiKey:export')")
    @Log(title = "请求key", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, TxApiKey txApiKey) {
        List<TxApiKey> list = txApiKeyService.selectTxApiKeyList(txApiKey);
        ExcelUtil<TxApiKey> util = new ExcelUtil<TxApiKey>(TxApiKey.class);
        util.exportExcel(response, list, "请求key数据");
    }

    /**
     * 获取请求key详细信息
     */
    @PreAuthorize("@ss.hasPermi('apiKey:apiKey:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id) {
        return success(txApiKeyService.selectTxApiKeyById(id));
    }

    /**
     * 新增请求key
     */
    @PreAuthorize("@ss.hasPermi('apiKey:apiKey:add')")
    @Log(title = "请求key", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TxApiKey txApiKey) {
        return toAjax(txApiKeyService.insertTxApiKey(txApiKey));
    }

    /**
     * 修改请求key
     */
    @PreAuthorize("@ss.hasPermi('apiKey:apiKey:edit')")
    @Log(title = "请求key", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TxApiKey txApiKey) {
        return toAjax(txApiKeyService.updateTxApiKey(txApiKey));
    }

    /**
     * 删除请求key
     */
    @PreAuthorize("@ss.hasPermi('apiKey:apiKey:remove')")
    @Log(title = "请求key", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids) {
        return toAjax(txApiKeyService.deleteTxApiKeyByIds(ids));
    }
}
