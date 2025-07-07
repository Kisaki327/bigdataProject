package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.PlayerTeamStats;
import com.ruoyi.system.domain.TeamStatsVO;

/**
 * 球队统计信息Service接口
 * 
 * @author mds
 * @date 2025-07-04
 */
public interface IPlayerTeamStatsService 
{
    /**
     * 查询球队统计信息
     * 
     * @param id 球队统计信息主键
     * @return 球队统计信息
     */
    public PlayerTeamStats selectPlayerTeamStatsById(Long id);

    /**
     * 查询球队统计信息列表
     * 
     * @param playerTeamStats 球队统计信息
     * @return 球队统计信息集合
     */
    public List<PlayerTeamStats> selectPlayerTeamStatsList(PlayerTeamStats playerTeamStats);

    /**
     * 新增球队统计信息
     * 
     * @param playerTeamStats 球队统计信息
     * @return 结果
     */
    public int insertPlayerTeamStats(PlayerTeamStats playerTeamStats);

    /**
     * 修改球队统计信息
     * 
     * @param playerTeamStats 球队统计信息
     * @return 结果
     */
    public int updatePlayerTeamStats(PlayerTeamStats playerTeamStats);

    /**
     * 批量删除球队统计信息
     * 
     * @param ids 需要删除的球队统计信息主键集合
     * @return 结果
     */
    public int deletePlayerTeamStatsByIds(Long[] ids);

    /**
     * 删除球队统计信息信息
     * 
     * @param id 球队统计信息主键
     * @return 结果
     */
    public int deletePlayerTeamStatsById(Long id);

    /**
     *
     * @param season
     * @return List<TeamStatsVO>
     */
    public List<TeamStatsVO> getTeamStats(String season);
}
