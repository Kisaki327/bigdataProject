package com.ruoyi.system.controller;

import java.util.List;
import java.util.Map;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.system.domain.Players;
import com.ruoyi.system.service.IPlayersService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * NBA球员基本信息Controller
 * 
 * @author ruoyi
 * @date 2025-06-27
 */
@RestController
@RequestMapping("/system/players")
public class PlayersController extends BaseController
{
    @Autowired
    private IPlayersService playersService;

    /**
     * 查询NBA球员基本信息列表
     */
    @PreAuthorize("@ss.hasPermi('system:players:list')")
    @GetMapping("/list")
    public TableDataInfo list(Players players)
    {
        startPage();
        List<Players> list = playersService.selectPlayersList(players);
        return getDataTable(list);
    }

    /**
     * 导出NBA球员基本信息列表
     */
    @PreAuthorize("@ss.hasPermi('system:players:export')")
    @Log(title = "NBA球员基本信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Players players)
    {
        List<Players> list = playersService.selectPlayersList(players);
        ExcelUtil<Players> util = new ExcelUtil<Players>(Players.class);
        util.exportExcel(response, list, "NBA球员基本信息数据");
    }

    /**
     * 获取NBA球员基本信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:players:query')")
    @GetMapping(value = "/{playerId}")
    public AjaxResult getInfo(@PathVariable("playerId") Long playerId)
    {
        return success(playersService.selectPlayersByPlayerId(playerId));
    }

    /**
     * 新增NBA球员基本信息
     */
    @PreAuthorize("@ss.hasPermi('system:players:add')")
    @Log(title = "NBA球员基本信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Players players)
    {
        return toAjax(playersService.insertPlayers(players));
    }

    /**
     * 修改NBA球员基本信息
     */
    @PreAuthorize("@ss.hasPermi('system:players:edit')")
    @Log(title = "NBA球员基本信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Players players)
    {
        return toAjax(playersService.updatePlayers(players));
    }

    /**
     * 删除NBA球员基本信息
     */
    @PreAuthorize("@ss.hasPermi('system:players:remove')")
    @Log(title = "NBA球员基本信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{playerIds}")
    public AjaxResult remove(@PathVariable Long[] playerIds)
    {
        return toAjax(playersService.deletePlayersByPlayerIds(playerIds));
    }

    /**
     * 查询各城市球队数量统计
     *
     * @return List of { teamCity, count }
     */
//    @PreAuthorize("@ss.hasPermi('system:players:chart')")
    @GetMapping("/groupByCity")
    public AjaxResult groupByCity()
    {
        List<Map<String, Object>> list = playersService.selectCountGroupByTeamCity();
        return success(list);
    }

    @PreAuthorize("@ss.hasPermi('system:playerComp:index')")
    @GetMapping("/getPlayers")
    public AjaxResult getPlayers(@RequestParam(required = false) String playerId, @RequestParam(required = true) String season){
        return success(playersService.getPlayerSeasonStats(playerId,season));
    }

//    @PreAuthorize("@ss.hasPermi('system:playerRace:list')")
    @GetMapping("/getTop10")
    public AjaxResult getTop10(@RequestParam(required = true) String season,@RequestParam(required = false) String sort){
        return success(playersService.getTop10Players(season,sort));
    }

//    @PreAuthorize("@ss.hasPermi('system:playersdist:index')")
    @GetMapping("/getPlayerDist")
    public AjaxResult getPlayerDist(@RequestParam(required = false) String season){
        return success(playersService.getPlayerDist(season));
    }

    @GetMapping("getPlayersPos")
    public AjaxResult getBestPlayersByPos(@RequestParam String season, @RequestParam String pos){
        return success(playersService.getBestPlayersByPos(season,pos));
    }

    @GetMapping("/getPlayerGames")
    public AjaxResult getPlayerGames(){
        return success(playersService.getPlayerGamesStats());
    }
}
