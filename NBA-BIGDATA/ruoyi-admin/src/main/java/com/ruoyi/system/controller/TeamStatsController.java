package com.ruoyi.system.controller;

import java.util.List;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
 * 
 * @author ruoyi
 * @date 2025-06-27
 */
@RestController
@RequestMapping("/system/teamstats")
public class TeamStatsController extends BaseController
{
    @Autowired
    private ITeamStatsService teamStatsService;

    /**
     * 查询球队信息列表
     */
//    @PreAuthorize("@ss.hasPermi('system:stats:list')")
    @GetMapping("/list")
    public TableDataInfo list(TeamStats teamStats)
    {
        startPage();
        List<TeamStats> list = teamStatsService.selectTeamStatsList(teamStats);
        return getDataTable(list);
    }

    /**
     * 导出球队信息列表
     */
    @PreAuthorize("@ss.hasPermi('system:stats:export')")
    @Log(title = "球队信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, TeamStats teamStats)
    {
        List<TeamStats> list = teamStatsService.selectTeamStatsList(teamStats);
        ExcelUtil<TeamStats> util = new ExcelUtil<TeamStats>(TeamStats.class);
        util.exportExcel(response, list, "球队信息数据");
    }

    /**
     * 获取球队信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:stats:query')")
    @GetMapping(value = "/{teamId}")
    public AjaxResult getInfo(@PathVariable("teamId") Long teamId)
    {
        return success(teamStatsService.selectTeamStatsByTeamId(teamId));
    }

    /**
     * 新增球队信息
     */
    @PreAuthorize("@ss.hasPermi('system:stats:add')")
    @Log(title = "球队信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TeamStats teamStats)
    {
        return toAjax(teamStatsService.insertTeamStats(teamStats));
    }

    /**
     * 修改球队信息
     */
    @PreAuthorize("@ss.hasPermi('system:stats:edit')")
    @Log(title = "球队信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TeamStats teamStats)
    {
        return toAjax(teamStatsService.updateTeamStats(teamStats));
    }

    /**
     * 删除球队信息
     */
    @PreAuthorize("@ss.hasPermi('system:stats:remove')")
    @Log(title = "球队信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{teamIds}")
    public AjaxResult remove(@PathVariable Long[] teamIds)
    {
        return toAjax(teamStatsService.deleteTeamStatsByTeamIds(teamIds));
    }
}
