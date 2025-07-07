package com.ruoyi.system.mapper;

import java.util.List;
import java.util.Map;

import com.ruoyi.system.domain.*;

/**
 * NBA球员基本信息Mapper接口
 * 
 * @author ruoyi
 * @date 2025-06-27
 */
public interface PlayersMapper 
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
     * 删除NBA球员基本信息
     * 
     * @param playerId NBA球员基本信息主键
     * @return 结果
     */
    public int deletePlayersByPlayerId(Long playerId);

    /**
     * 批量删除NBA球员基本信息
     * 
     * @param playerIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deletePlayersByPlayerIds(Long[] playerIds);

    /**
     * 根据 teamCity 分组统计球队数量
     *
     * @return 每个城市及其数量的列表，包含字段 teamCity 和 count
     */
    List<Map<String, Object>> selectCountGroupByTeamCity();

    /**
     * 根据赛季或球员id返回球员基本信息
     * @param playerId
     * @param season
     * @return 球员信息列表
     */
    public List<NbaPlayerSeasonStats> getPlayerSeasonStats(String playerId, String season);

    /**
     * 根据赛季和排序字段返回前十球员信息
     * @param season
     * @param sort
     * @return 前10球员信息列表
     */
    public  List<top_10_player> getTop10Players(String season,String sort);

    /**
     * 根据赛季返回球员得分分布信息
     * @param season
     * @return 结果
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
