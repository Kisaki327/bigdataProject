package com.ruoyi.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 球队信息对象 team_stats
 * 
 * @author ruoyi
 * @date 2025-06-27
 */
public class TeamStats extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 球队 ID */
    private Long teamId;

    /** 球队名称 */
    @Excel(name = "球队名称")
    private String teamName;

    /** 平均身高（米） */
    @Excel(name = "平均身高", readConverterExp = "米=")
    private Double avgheightM;

    /** 平均体重（公斤） */
    @Excel(name = "平均体重", readConverterExp = "公=斤")
    private Double avgweightKg;

    /** 平均 NBA 经验（年） */
    @Excel(name = "平均 NBA 经验", readConverterExp = "年=")
    private Double avgexperienceYears;

    /** 球员数量 */
    @Excel(name = "球员数量")
    private Long playerCount;

    public void setTeamId(Long teamId) 
    {
        this.teamId = teamId;
    }

    public Long getTeamId() 
    {
        return teamId;
    }

    public void setTeamName(String teamName) 
    {
        this.teamName = teamName;
    }

    public String getTeamName() 
    {
        return teamName;
    }

    public void setAvgheightM(Double avgheightM)
    {
        this.avgheightM = avgheightM;
    }

    public Double getAvgheightM()
    {
        return avgheightM;
    }

    public void setAvgweightKg(Double avgweightKg)
    {
        this.avgweightKg = avgweightKg;
    }

    public Double getAvgweightKg()
    {
        return avgweightKg;
    }

    public void setAvgexperienceYears(Double avgexperienceYears)
    {
        this.avgexperienceYears = avgexperienceYears;
    }

    public Double getAvgexperienceYears()
    {
        return avgexperienceYears;
    }

    public void setPlayerCount(Long playerCount) 
    {
        this.playerCount = playerCount;
    }

    public Long getPlayerCount() 
    {
        return playerCount;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("teamId", getTeamId())
            .append("teamName", getTeamName())
            .append("avgheightM", getAvgheightM())
            .append("avgweightKg", getAvgweightKg())
            .append("avgexperienceYears", getAvgexperienceYears())
            .append("playerCount", getPlayerCount())
            .toString();
    }
}
