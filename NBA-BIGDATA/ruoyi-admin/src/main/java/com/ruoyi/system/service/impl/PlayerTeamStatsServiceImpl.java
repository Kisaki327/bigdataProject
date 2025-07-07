package com.ruoyi.system.service.impl;

import java.util.List;

import com.ruoyi.system.domain.TeamStatsVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.PlayerTeamStatsMapper;
import com.ruoyi.system.domain.PlayerTeamStats;
import com.ruoyi.system.service.IPlayerTeamStatsService;

/**
 * 球队统计信息Service业务层处理
 * 
 * @author mds
 * @date 2025-07-04
 */
@Service
public class PlayerTeamStatsServiceImpl implements IPlayerTeamStatsService 
{
    @Autowired
    private PlayerTeamStatsMapper playerTeamStatsMapper;

    /**
     * 查询球队统计信息
     * 
     * @param id 球队统计信息主键
     * @return 球队统计信息
     */
    @Override
    public PlayerTeamStats selectPlayerTeamStatsById(Long id)
    {
        return playerTeamStatsMapper.selectPlayerTeamStatsById(id);
    }

    /**
     * 查询球队统计信息列表
     * 
     * @param playerTeamStats 球队统计信息
     * @return 球队统计信息
     */
    @Override
    public List<PlayerTeamStats> selectPlayerTeamStatsList(PlayerTeamStats playerTeamStats)
    {
        return playerTeamStatsMapper.selectPlayerTeamStatsList(playerTeamStats);
    }

    /**
     * 新增球队统计信息
     * 
     * @param playerTeamStats 球队统计信息
     * @return 结果
     */
    @Override
    public int insertPlayerTeamStats(PlayerTeamStats playerTeamStats)
    {
        return playerTeamStatsMapper.insertPlayerTeamStats(playerTeamStats);
    }

    /**
     * 修改球队统计信息
     * 
     * @param playerTeamStats 球队统计信息
     * @return 结果
     */
    @Override
    public int updatePlayerTeamStats(PlayerTeamStats playerTeamStats)
    {
        return playerTeamStatsMapper.updatePlayerTeamStats(playerTeamStats);
    }

    /**
     * 批量删除球队统计信息
     * 
     * @param ids 需要删除的球队统计信息主键
     * @return 结果
     */
    @Override
    public int deletePlayerTeamStatsByIds(Long[] ids)
    {
        return playerTeamStatsMapper.deletePlayerTeamStatsByIds(ids);
    }

    /**
     * 删除球队统计信息信息
     * 
     * @param id 球队统计信息主键
     * @return 结果
     */
    @Override
    public int deletePlayerTeamStatsById(Long id)
    {
        return playerTeamStatsMapper.deletePlayerTeamStatsById(id);
    }

    @Override
    public List<TeamStatsVO> getTeamStats(String season) {
        return playerTeamStatsMapper.getTeamStats(season);
    }
}
