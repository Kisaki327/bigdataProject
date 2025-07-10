package com.ruoyi.system.controller;

import java.time.Duration;
import java.util.List;

import com.ruoyi.system.domain.TeamStatsVO;
import com.ruoyi.system.service.impl.RedisCacheService;
import jakarta.servlet.http.HttpServletResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;

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
 * 提供球队数据的增删改查与赛季分析
 */
@RestController
@RequestMapping("/system/stats")
@Tag(name = "球队统计接口", description = "提供球队得分、胜率、排名等统计分析能力")
public class PlayerTeamStatsController extends BaseController {

    @Autowired
    private IPlayerTeamStatsService playerTeamStatsService;

    @Autowired
    private RedisCacheService redisCacheService;

    @Operation(summary = "查询球队统计信息列表")
    @PreAuthorize("@ss.hasPermi('system:stats:list')")
    @GetMapping("/list")
    public TableDataInfo list(PlayerTeamStats playerTeamStats) {
        startPage();
        List<PlayerTeamStats> list = playerTeamStatsService.selectPlayerTeamStatsList(playerTeamStats);
        return getDataTable(list);
    }

    @Operation(summary = "导出球队统计信息")
    @PreAuthorize("@ss.hasPermi('system:stats:export')")
    @Log(title = "球队统计信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, PlayerTeamStats playerTeamStats) {
        List<PlayerTeamStats> list = playerTeamStatsService.selectPlayerTeamStatsList(playerTeamStats);
        ExcelUtil<PlayerTeamStats> util = new ExcelUtil<>(PlayerTeamStats.class);
        util.exportExcel(response, list, "球队统计信息数据");
    }

    @Operation(summary = "获取球队统计详细信息")
    @PreAuthorize("@ss.hasPermi('system:stats:query')")
    @GetMapping("/{id}")
    public AjaxResult getInfo(@Parameter(description = "主键ID") @PathVariable("id") Long id) {
        return success(playerTeamStatsService.selectPlayerTeamStatsById(id));
    }

    @Operation(summary = "新增球队统计信息")
    @PreAuthorize("@ss.hasPermi('system:stats:add')")
    @Log(title = "球队统计信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody PlayerTeamStats playerTeamStats) {
        return toAjax(playerTeamStatsService.insertPlayerTeamStats(playerTeamStats));
    }

    @Operation(summary = "修改球队统计信息")
    @PreAuthorize("@ss.hasPermi('system:stats:edit')")
    @Log(title = "球队统计信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody PlayerTeamStats playerTeamStats) {
        return toAjax(playerTeamStatsService.updatePlayerTeamStats(playerTeamStats));
    }

    @Operation(summary = "删除球队统计信息")
    @PreAuthorize("@ss.hasPermi('system:stats:remove')")
    @Log(title = "球队统计信息", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@Parameter(description = "主键ID数组") @PathVariable Long[] ids) {
        return toAjax(playerTeamStatsService.deletePlayerTeamStatsByIds(ids));
    }

    @Operation(summary = "获取赛季球队统计分析", description = "根据赛季返回各支球队的综合统计数据")
    @GetMapping("/getTeams")
    public AjaxResult getTeamStats(@Parameter(description = "赛季年度", required = true) @RequestParam String season) {
        String key = "nba:teams:" + season;
        List<TeamStatsVO> list = redisCacheService.getOrCacheList(
                key,
                TeamStatsVO.class,
                () -> playerTeamStatsService.getTeamStats(season),
                Duration.ofMinutes(30)
        );
        return success(list);
    }
}