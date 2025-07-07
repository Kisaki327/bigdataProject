package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.TeamStats;
import com.ruoyi.system.domain.TeamStatsVO;

/**
 * 球队信息Mapper接口
 * 
 * @author ruoyi
 * @date 2025-06-27
 */
public interface TeamStatsMapper 
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
     * 删除球队信息
     * 
     * @param teamId 球队信息主键
     * @return 结果
     */
    public int deleteTeamStatsByTeamId(Long teamId);

    /**
     * 批量删除球队信息
     * 
     * @param teamIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteTeamStatsByTeamIds(Long[] teamIds);

    /**
     *
     * @param season
     * @return List<TeamStatsVO>
     */
    public List<TeamStatsVO> getTeamStats(String season);
}
