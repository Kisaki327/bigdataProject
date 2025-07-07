package com.ruoyi.system.controller;

import java.util.List;

import com.ruoyi.system.domain.TeamStatsVO;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.system.domain.PlayerTeamStats;
import com.ruoyi.system.service.IPlayerTeamStatsService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 球队统计信息Controller
 * 
 * @author mds
 * @date 2025-07-04
 */
@RestController
@RequestMapping("/system/stats")
public class PlayerTeamStatsController extends BaseController
{
    @Autowired
    private IPlayerTeamStatsService playerTeamStatsService;

    /**
     * 查询球队统计信息列表
     */
    @PreAuthorize("@ss.hasPermi('system:stats:list')")
    @GetMapping("/list")
    public TableDataInfo list(PlayerTeamStats playerTeamStats)
    {
        startPage();
        List<PlayerTeamStats> list = playerTeamStatsService.selectPlayerTeamStatsList(playerTeamStats);
        return getDataTable(list);
    }

    /**
     * 导出球队统计信息列表
     */
    @PreAuthorize("@ss.hasPermi('system:stats:export')")
    @Log(title = "球队统计信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, PlayerTeamStats playerTeamStats)
    {
        List<PlayerTeamStats> list = playerTeamStatsService.selectPlayerTeamStatsList(playerTeamStats);
        ExcelUtil<PlayerTeamStats> util = new ExcelUtil<PlayerTeamStats>(PlayerTeamStats.class);
        util.exportExcel(response, list, "球队统计信息数据");
    }

    /**
     * 获取球队统计信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:stats:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(playerTeamStatsService.selectPlayerTeamStatsById(id));
    }

    /**
     * 新增球队统计信息
     */
    @PreAuthorize("@ss.hasPermi('system:stats:add')")
    @Log(title = "球队统计信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody PlayerTeamStats playerTeamStats)
    {
        return toAjax(playerTeamStatsService.insertPlayerTeamStats(playerTeamStats));
    }

    /**
     * 修改球队统计信息
     */
    @PreAuthorize("@ss.hasPermi('system:stats:edit')")
    @Log(title = "球队统计信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody PlayerTeamStats playerTeamStats)
    {
        return toAjax(playerTeamStatsService.updatePlayerTeamStats(playerTeamStats));
    }

    /**
     * 删除球队统计信息
     */
    @PreAuthorize("@ss.hasPermi('system:stats:remove')")
    @Log(title = "球队统计信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(playerTeamStatsService.deletePlayerTeamStatsByIds(ids));
    }

    /**
     *获取球队信息
     * @param season
     * @return
     */
    @GetMapping("/getTeams")
    public AjaxResult getTeamStats(@RequestParam String season){
        return success(playerTeamStatsService.getTeamStats(season));
    }
}
