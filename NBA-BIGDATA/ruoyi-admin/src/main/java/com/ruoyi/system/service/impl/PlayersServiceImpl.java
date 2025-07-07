package com.ruoyi.system.service.impl;

import java.util.List;
import java.util.Map;

import com.ruoyi.system.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.PlayersMapper;
import com.ruoyi.system.service.IPlayersService;

/**
 * NBA球员基本信息Service业务层处理
 * 
 * @author ruoyi
 * @date 2025-06-27
 */
@Service
public class PlayersServiceImpl implements IPlayersService 
{
    @Autowired
    private PlayersMapper playersMapper;

    /**
     * 查询NBA球员基本信息
     * 
     * @param playerId NBA球员基本信息主键
     * @return NBA球员基本信息
     */
    @Override
    public Players selectPlayersByPlayerId(Long playerId)
    {
        return playersMapper.selectPlayersByPlayerId(playerId);
    }

    /**
     * 查询NBA球员基本信息列表
     * 
     * @param players NBA球员基本信息
     * @return NBA球员基本信息
     */
    @Override
    public List<Players> selectPlayersList(Players players)
    {
        return playersMapper.selectPlayersList(players);
    }

    /**
     * 新增NBA球员基本信息
     * 
     * @param players NBA球员基本信息
     * @return 结果
     */
    @Override
    public int insertPlayers(Players players)
    {
        return playersMapper.insertPlayers(players);
    }

    /**
     * 修改NBA球员基本信息
     * 
     * @param players NBA球员基本信息
     * @return 结果
     */
    @Override
    public int updatePlayers(Players players)
    {
        return playersMapper.updatePlayers(players);
    }

    /**
     * 批量删除NBA球员基本信息
     * 
     * @param playerIds 需要删除的NBA球员基本信息主键
     * @return 结果
     */
    @Override
    public int deletePlayersByPlayerIds(Long[] playerIds)
    {
        return playersMapper.deletePlayersByPlayerIds(playerIds);
    }

    /**
     * 删除NBA球员基本信息信息
     * 
     * @param playerId NBA球员基本信息主键
     * @return 结果
     */
    @Override
    public int deletePlayersByPlayerId(Long playerId)
    {
        return playersMapper.deletePlayersByPlayerId(playerId);
    }

    @Override
    public List<Map<String, Object>> selectCountGroupByTeamCity()
    {
        return playersMapper.selectCountGroupByTeamCity();
    }

    @Override
    public List<NbaPlayerSeasonStats> getPlayerSeasonStats(String playerId, String season) {
        return playersMapper.getPlayerSeasonStats(playerId, season);
    }

    @Override
    public List<top_10_player> getTop10Players(String season,String orderBy) {
        return playersMapper.getTop10Players(season,orderBy);
    }

    @Override
    public List<playersDist> getPlayerDist(String season) {
        return playersMapper.getPlayerDist(season);
    }

    @Override
    public List<NbaPlayerSeasonStats> getBestPlayersByPos(String season, String pos) {
        return playersMapper.getBestPlayersByPos(season,pos);
    }

    @Override
    public List<playerStats> getPlayerGamesStats() {
        return playersMapper.getPlayerGamesStats();
    }
}
