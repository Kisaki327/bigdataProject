package com.ruoyi.system.service;

import java.util.List;
import java.util.Map;

import com.ruoyi.system.domain.*;

/**
 * NBA球员基本信息Service接口
 * 
 * @author ruoyi
 * @date 2025-06-27
 */
public interface IPlayersService 
{
    /**
     * 查询NBA球员基本信息
     * 
     * @param playerId NBA球员基本信息主键
     * @return NBA球员基本信息
     */
    public Players selectPlayersByPlayerId(Long playerId);

    /**
     * 查询NBA球员基本信息列表
     * 
     * @param players NBA球员基本信息
     * @return NBA球员基本信息集合
     */
    public List<Players> selectPlayersList(Players players);

    /**
     * 新增NBA球员基本信息
     * 
     * @param players NBA球员基本信息
     * @return 结果
     */
    public int insertPlayers(Players players);

    /**
     * 修改NBA球员基本信息
     * 
     * @param players NBA球员基本信息
     * @return 结果
     */
    public int updatePlayers(Players players);

    /**
     * 批量删除NBA球员基本信息
     * 
     * @param playerIds 需要删除的NBA球员基本信息主键集合
     * @return 结果
     */
    public int deletePlayersByPlayerIds(Long[] playerIds);

    /**
     * 删除NBA球员基本信息信息
     * 
     * @param playerId NBA球员基本信息主键
     * @return 结果
     */
    public int deletePlayersByPlayerId(Long playerId);

    /**
     * 根据 teamCity 分组统计球队数量
     *
     * @return List of Map，key 包括 "teamCity"（城市）和 "count"（数量）
     */
    List<Map<String, Object>> selectCountGroupByTeamCity();

    public List<NbaPlayerSeasonStats> getPlayerSeasonStats(String playerId, String season);

    /**
     * 查询每赛季top10球员
     * @param season
     * @return list of top_10_player
     */
    public List<top_10_player> getTop10Players(String season,String sort);

    /**
     *
     * @param season
     * @return list of playersDist
     */
    public  List<playersDist> getPlayerDist(String season);

    /**
     * 各位置得分前五球员
     * @param season
     * @param pos
     * @return List<NbaPlayerSeasonStats>
     */
    public List<NbaPlayerSeasonStats> getBestPlayersByPos(String season,String pos);

    public List<playerStats> getPlayerGamesStats();
}
