package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.TeamStats;

/**
 * 球队信息Service接口
 * 
 * @author ruoyi
 * @date 2025-06-27
 */
public interface ITeamStatsService 
{
    /**
     * 查询球队信息
     * 
     * @param teamId 球队信息主键
     * @return 球队信息
     */
    public TeamStats selectTeamStatsByTeamId(Long teamId);

    /**
     * 查询球队信息列表
     * 
     * @param teamStats 球队信息
     * @return 球队信息集合
     */
    public List<TeamStats> selectTeamStatsList(TeamStats teamStats);

    /**
     * 新增球队信息
     * 
     * @param teamStats 球队信息
     * @return 结果
     */
    public int insertTeamStats(TeamStats teamStats);

    /**
     * 修改球队信息
     * 
     * @param teamStats 球队信息
     * @return 结果
     */
    public int updateTeamStats(TeamStats teamStats);

    /**
     * 批量删除球队信息
     * 
     * @param teamIds 需要删除的球队信息主键集合
     * @return 结果
     */
    public int deleteTeamStatsByTeamIds(Long[] teamIds);

    /**
     * 删除球队信息信息
     * 
     * @param teamId 球队信息主键
     * @return 结果
     */
    public int deleteTeamStatsByTeamId(Long teamId);
}
