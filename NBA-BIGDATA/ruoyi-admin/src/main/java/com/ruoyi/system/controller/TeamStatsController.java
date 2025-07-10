package com.ruoyi.system.controller;

import java.util.List;
import jakarta.servlet.http.HttpServletResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.system.domain.TeamStats;
import com.ruoyi.system.service.ITeamStatsService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 球队信息Controller
 * 提供球队基本资料的增删改查功能
 */
@RestController
@RequestMapping("/system/teamstats")
@Tag(name = "球队信息接口", description = "提供球队基础信息管理接口")
public class TeamStatsController extends BaseController {

    @Autowired
    private ITeamStatsService teamStatsService;

    @Operation(summary = "查询球队信息列表")
    @GetMapping("/list")
    public TableDataInfo list(TeamStats teamStats) {
        startPage();
        List<TeamStats> list = teamStatsService.selectTeamStatsList(teamStats);
        return getDataTable(list);
    }

    @Operation(summary = "导出球队信息")
    @PreAuthorize("@ss.hasPermi('system:stats:export')")
    @Log(title = "球队信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, TeamStats teamStats) {
        List<TeamStats> list = teamStatsService.selectTeamStatsList(teamStats);
        ExcelUtil<TeamStats> util = new ExcelUtil<>(TeamStats.class);
        util.exportExcel(response, list, "球队信息数据");
    }

    @Operation(summary = "获取球队详情")
    @PreAuthorize("@ss.hasPermi('system:stats:query')")
    @GetMapping("/{teamId}")
    public AjaxResult getInfo(@Parameter(description = "球队ID") @PathVariable("teamId") Long teamId) {
        return success(teamStatsService.selectTeamStatsByTeamId(teamId));
    }

    @Operation(summary = "新增球队信息")
    @PreAuthorize("@ss.hasPermi('system:stats:add')")
    @Log(title = "球队信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TeamStats teamStats) {
        return toAjax(teamStatsService.insertTeamStats(teamStats));
    }

    @Operation(summary = "修改球队信息")
    @PreAuthorize("@ss.hasPermi('system:stats:edit')")
    @Log(title = "球队信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TeamStats teamStats) {
        return toAjax(teamStatsService.updateTeamStats(teamStats));
    }

    @Operation(summary = "删除球队信息")
    @PreAuthorize("@ss.hasPermi('system:stats:remove')")
    @Log(title = "球队信息", businessType = BusinessType.DELETE)
    @DeleteMapping("/{teamIds}")
    public AjaxResult remove(@Parameter(description = "球队ID数组") @PathVariable Long[] teamIds) {
        return toAjax(teamStatsService.deleteTeamStatsByTeamIds(teamIds));
    }
}
