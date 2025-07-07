package com.ruoyi.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.TeamStatsMapper;
import com.ruoyi.system.domain.TeamStats;
import com.ruoyi.system.service.ITeamStatsService;

/**
 * 球队信息Service业务层处理
 * 
 * @author ruoyi
 * @date 2025-06-27
 */
@Service
public class TeamStatsServiceImpl implements ITeamStatsService 
{
    @Autowired
    private TeamStatsMapper teamStatsMapper;

    /**
     * 查询球队信息
     * 
     * @param teamId 球队信息主键
     * @return 球队信息
     */
    @Override
    public TeamStats selectTeamStatsByTeamId(Long teamId)
    {
        return teamStatsMapper.selectTeamStatsByTeamId(teamId);
    }

    /**
     * 查询球队信息列表
     * 
     * @param teamStats 球队信息
     * @return 球队信息
     */
    @Override
    public List<TeamStats> selectTeamStatsList(TeamStats teamStats)
    {
        return teamStatsMapper.selectTeamStatsList(teamStats);
    }

    /**
     * 新增球队信息
     * 
     * @param teamStats 球队信息
     * @return 结果
     */
    @Override
    public int insertTeamStats(TeamStats teamStats)
    {
        return teamStatsMapper.insertTeamStats(teamStats);
    }

    /**
     * 修改球队信息
     * 
     * @param teamStats 球队信息
     * @return 结果
     */
    @Override
    public int updateTeamStats(TeamStats teamStats)
    {
        return teamStatsMapper.updateTeamStats(teamStats);
    }

    /**
     * 批量删除球队信息
     * 
     * @param teamIds 需要删除的球队信息主键
     * @return 结果
     */
    @Override
    public int deleteTeamStatsByTeamIds(Long[] teamIds)
    {
        return teamStatsMapper.deleteTeamStatsByTeamIds(teamIds);
    }

    /**
     * 删除球队信息信息
     * 
     * @param teamId 球队信息主键
     * @return 结果
     */
    @Override
    public int deleteTeamStatsByTeamId(Long teamId)
    {
        return teamStatsMapper.deleteTeamStatsByTeamId(teamId);
    }
}
