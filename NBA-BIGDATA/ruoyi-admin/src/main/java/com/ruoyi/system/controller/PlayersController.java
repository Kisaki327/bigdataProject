package com.ruoyi.system.controller;

import java.time.Duration;
import java.util.List;
import java.util.Map;

import com.ruoyi.system.domain.*;
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
import com.ruoyi.system.service.IPlayersService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

@RestController
@RequestMapping("/system/players")
@Tag(name = "NBA球员接口", description = "提供球员基本信息查询、统计、分析等功能")
public class PlayersController extends BaseController {

    @Autowired
    private IPlayersService playersService;

    @Autowired
    private RedisCacheService redisCacheService;

    @Operation(summary = "查询球员列表", description = "根据条件分页查询球员信息")
    @PreAuthorize("@ss.hasPermi('system:players:list')")
    @GetMapping("/list")
    public TableDataInfo list(Players players) {
        startPage();
        List<Players> list = playersService.selectPlayersList(players);
        return getDataTable(list);
    }

    @Operation(summary = "导出球员信息", description = "将球员信息导出为 Excel 文件")
    @PreAuthorize("@ss.hasPermi('system:players:export')")
    @Log(title = "NBA球员基本信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Players players) {
        List<Players> list = playersService.selectPlayersList(players);
        ExcelUtil<Players> util = new ExcelUtil<>(Players.class);
        util.exportExcel(response, list, "NBA球员基本信息数据");
    }

    @Operation(summary = "根据ID查询球员", description = "根据主键 ID 查询球员详细信息")
    @PreAuthorize("@ss.hasPermi('system:players:query')")
    @GetMapping("/{playerId}")
    public AjaxResult getInfo(
            @Parameter(description = "球员ID", required = true)
            @PathVariable("playerId") Long playerId) {
        return success(playersService.selectPlayersByPlayerId(playerId));
    }

    @Operation(summary = "新增球员信息")
    @PreAuthorize("@ss.hasPermi('system:players:add')")
    @Log(title = "NBA球员基本信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Players players) {
        return toAjax(playersService.insertPlayers(players));
    }

    @Operation(summary = "修改球员信息")
    @PreAuthorize("@ss.hasPermi('system:players:edit')")
    @Log(title = "NBA球员基本信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Players players) {
        return toAjax(playersService.updatePlayers(players));
    }

    @Operation(summary = "删除球员信息")
    @PreAuthorize("@ss.hasPermi('system:players:remove')")
    @Log(title = "NBA球员基本信息", businessType = BusinessType.DELETE)
    @DeleteMapping("/{playerIds}")
    public AjaxResult remove(
            @Parameter(description = "球员ID数组", required = true)
            @PathVariable Long[] playerIds) {
        return toAjax(playersService.deletePlayersByPlayerIds(playerIds));
    }

    @Operation(summary = "城市分布统计", description = "按球队所在城市统计球员数量")
    @GetMapping("/groupByCity")
    public AjaxResult groupByCity() {
        List<Map<String, Object>> list = playersService.selectCountGroupByTeamCity();
        return success(list);
    }

    @Operation(summary = "根据赛季或ID查球员赛季数据")
    @PreAuthorize("@ss.hasPermi('system:playerComp:index')")
    @GetMapping("/getPlayers")
    @Parameters({
            @Parameter(name = "playerId", description = "球员ID", required = false),
            @Parameter(name = "season", description = "赛季", required = true)
    })
    public AjaxResult getPlayers(@RequestParam(required = false) String playerId,
                                 @RequestParam() String season) {
        String key = "nba:players:" + season;
        List<NbaPlayerSeasonStats> list = redisCacheService.getOrCacheList(
                key,
                NbaPlayerSeasonStats.class,
                () -> playersService.getPlayerSeasonStats(playerId,season),
                Duration.ofMinutes(30)
        );
        return success(list);
    }

    @Operation(summary = "获取Top10球员", description = "返回赛季得分Top10或生涯总得分Top10")
    @GetMapping("/getTop10")
    @Parameters({
            @Parameter(name = "season", description = "赛季", required = true),
            @Parameter(name = "sort", description = "是否按生涯总分排序", required = false)
    })
    public AjaxResult getTop10(@RequestParam String season,
                               @RequestParam(required = false) String sort) {
        String key = (sort != null && !sort.isEmpty())
                ? "nba:top10:career:"+ season
                : "nba:top10:season:" + season;
        List<top_10_player> top10 = redisCacheService.getOrCacheList(
                key, top_10_player.class,
                () -> playersService.getTop10Players(season, sort),
                Duration.ofMinutes(30)
        );
        return success(top10);
    }

    @Operation(summary = "赛季球员分布", description = "按赛季返回球员分布信息")
    @GetMapping("/getPlayerDist")
    @Parameter(name = "season", description = "赛季", required = false)
    public AjaxResult getPlayerDist(@RequestParam(required = false) String season) {
        String key = "nba:playerDist:" + season;
        List<playersDist> dists  = redisCacheService.getOrCacheList(
                key,
                playersDist.class,
                ()-> playersService.getPlayerDist(season),
                Duration.ofMinutes(30)
        );
        return success(dists);
    }

    @Operation(summary = "按位置和赛季获取球员", description = "根据位置和赛季获取对应球员列表")
    @GetMapping("/getPlayersPos")
    @Parameters({
            @Parameter(name = "season", description = "赛季", required = true),
            @Parameter(name = "pos", description = "球员位置", required = true)
    })
    public AjaxResult getBestPlayersByPos(@RequestParam String season,
                                          @RequestParam String pos) {

        String key = "nba:playerPos:" + season + ":" + pos;
        List<NbaPlayerSeasonStats> list = redisCacheService.getOrCacheList(
                key,
                NbaPlayerSeasonStats.class,
                () -> playersService.getBestPlayersByPos(season,pos),
                Duration.ofMinutes(30)
        );
        return success(list);
    }

    @Operation(summary = "获取球员近十场比赛数据及预测")
    @GetMapping("/getPlayerGames")
    public AjaxResult getPlayerGames() {
        String key = "nba:playerGames";
        List<playerStats> list = redisCacheService.getOrCacheList(
                key,
                playerStats.class,
                () -> playersService.getPlayerGamesStats(),
                Duration.ofMinutes(30)
        );
        return success(list);
    }
}
