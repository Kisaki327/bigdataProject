package com.ruoyi.system.domain;

import java.math.BigDecimal;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 球队统计信息对象 player_team_stats
 * 
 * @author mds
 * @date 2025-07-04
 */
public class PlayerTeamStats extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 自增主键 */
    private Long id;

    /** 赛季，例如 2023-24 */
    @Excel(name = "赛季，例如 2023-24")
    private String Season;

    /** 球队唯一ID */
    @Excel(name = "球队唯一ID")
    private String Teamid;

    /** 球队深色Logo图片URL */
    @Excel(name = "球队深色Logo图片URL")
    private String LogoDark;

    /** 球队浅色Logo图片URL */
    @Excel(name = "球队浅色Logo图片URL")
    private String LogoLight;

    /** 球队名称 */
    @Excel(name = "球队名称")
    private String teamname;

    /** 出场总场数 */
    @Excel(name = "出场总场数")
    private Long Games;

    /** 首发场次数 */
    @Excel(name = "首发场次数")
    private Long Gamesstarted;

    /** 总上场分钟数 */
    @Excel(name = "总上场分钟数")
    private Long Mins;

    /** 场均得分 */
    @Excel(name = "场均得分")
    private BigDecimal Pointspg;

    /** 场均篮板 */
    @Excel(name = "场均篮板")
    private BigDecimal Rebspg;

    /** 场均助攻 */
    @Excel(name = "场均助攻")
    private BigDecimal Assistspg;

    /** 场均抢断 */
    @Excel(name = "场均抢断")
    private BigDecimal Stealspg;

    /** 场均盖帽 */
    @Excel(name = "场均盖帽")
    private BigDecimal Blockspg;

    /** 投篮命中率，字符串格式，例如 "53.2%" */
    @Excel(name = "投篮命中率，字符串格式")
    private String FgpctStr;

    /** 三分命中率，字符串格式，例如 "40.1%" */
    @Excel(name = "三分命中率，字符串格式")
    private String TppctStr;

    /** 罚球命中率，字符串格式，例如 "88.5%" */
    @Excel(name = "罚球命中率，字符串格式")
    private String FtpctStr;

    /** 进攻篮板总数 */
    @Excel(name = "进攻篮板总数")
    private Long Offrebs;

    /** 防守篮板总数 */
    @Excel(name = "防守篮板总数")
    private Long Defrebs;

    /** 失误次数 */
    @Excel(name = "失误次数")
    private Long Turnovers;

    /** 犯规次数 */
    @Excel(name = "犯规次数")
    private Long Fouls;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }

    public void setSeason(String Season) 
    {
        this.Season = Season;
    }

    public String getSeason() 
    {
        return Season;
    }

    public void setTeamid(String Teamid) 
    {
        this.Teamid = Teamid;
    }

    public String getTeamid() 
    {
        return Teamid;
    }

    public void setLogoDark(String LogoDark) 
    {
        this.LogoDark = LogoDark;
    }

    public String getLogoDark() 
    {
        return LogoDark;
    }

    public void setLogoLight(String LogoLight) 
    {
        this.LogoLight = LogoLight;
    }

    public String getLogoLight() 
    {
        return LogoLight;
    }

    public void setTeamname(String teamname) 
    {
        this.teamname = teamname;
    }

    public String getTeamname() 
    {
        return teamname;
    }

    public void setGames(Long Games) 
    {
        this.Games = Games;
    }

    public Long getGames() 
    {
        return Games;
    }

    public void setGamesstarted(Long Gamesstarted) 
    {
        this.Gamesstarted = Gamesstarted;
    }

    public Long getGamesstarted() 
    {
        return Gamesstarted;
    }

    public void setMins(Long Mins) 
    {
        this.Mins = Mins;
    }

    public Long getMins() 
    {
        return Mins;
    }

    public void setPointspg(BigDecimal Pointspg) 
    {
        this.Pointspg = Pointspg;
    }

    public BigDecimal getPointspg() 
    {
        return Pointspg;
    }

    public void setRebspg(BigDecimal Rebspg) 
    {
        this.Rebspg = Rebspg;
    }

    public BigDecimal getRebspg() 
    {
        return Rebspg;
    }

    public void setAssistspg(BigDecimal Assistspg) 
    {
        this.Assistspg = Assistspg;
    }

    public BigDecimal getAssistspg() 
    {
        return Assistspg;
    }

    public void setStealspg(BigDecimal Stealspg) 
    {
        this.Stealspg = Stealspg;
    }

    public BigDecimal getStealspg() 
    {
        return Stealspg;
    }

    public void setBlockspg(BigDecimal Blockspg) 
    {
        this.Blockspg = Blockspg;
    }

    public BigDecimal getBlockspg() 
    {
        return Blockspg;
    }

    public void setFgpctStr(String FgpctStr) 
    {
        this.FgpctStr = FgpctStr;
    }

    public String getFgpctStr() 
    {
        return FgpctStr;
    }

    public void setTppctStr(String TppctStr) 
    {
        this.TppctStr = TppctStr;
    }

    public String getTppctStr() 
    {
        return TppctStr;
    }

    public void setFtpctStr(String FtpctStr) 
    {
        this.FtpctStr = FtpctStr;
    }

    public String getFtpctStr() 
    {
        return FtpctStr;
    }

    public void setOffrebs(Long Offrebs) 
    {
        this.Offrebs = Offrebs;
    }

    public Long getOffrebs() 
    {
        return Offrebs;
    }

    public void setDefrebs(Long Defrebs) 
    {
        this.Defrebs = Defrebs;
    }

    public Long getDefrebs() 
    {
        return Defrebs;
    }

    public void setTurnovers(Long Turnovers) 
    {
        this.Turnovers = Turnovers;
    }

    public Long getTurnovers() 
    {
        return Turnovers;
    }

    public void setFouls(Long Fouls) 
    {
        this.Fouls = Fouls;
    }

    public Long getFouls() 
    {
        return Fouls;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("Season", getSeason())
            .append("Teamid", getTeamid())
            .append("LogoDark", getLogoDark())
            .append("LogoLight", getLogoLight())
            .append("teamname", getTeamname())
            .append("Games", getGames())
            .append("Gamesstarted", getGamesstarted())
            .append("Mins", getMins())
            .append("Pointspg", getPointspg())
            .append("Rebspg", getRebspg())
            .append("Assistspg", getAssistspg())
            .append("Stealspg", getStealspg())
            .append("Blockspg", getBlockspg())
            .append("FgpctStr", getFgpctStr())
            .append("TppctStr", getTppctStr())
            .append("FtpctStr", getFtpctStr())
            .append("Offrebs", getOffrebs())
            .append("Defrebs", getDefrebs())
            .append("Turnovers", getTurnovers())
            .append("Fouls", getFouls())
            .toString();
    }
}
