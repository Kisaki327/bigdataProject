package com.ruoyi.system.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * NBA球员基本信息对象 players
 * 
 * @author ruoyi
 * @date 2025-06-27
 */
public class Players extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 球员ID */
    private Long playerId;

    /** 头像URL */
    @Excel(name = "头像URL")
    private String avatar;

    /** 出生日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "出生日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date birthDate;

    /** 国家/地区 */
    @Excel(name = "国家/地区")
    private String country;

    /** 显示名称（英文全名） */
    @Excel(name = "显示名称", readConverterExp = "英文全名")
    private String displayName;

    /** 选秀年份 */
    @Excel(name = "选秀年份")
    private Long draftSeason;

    /** NBA经验（年数） */
    @Excel(name = "NBA经验", readConverterExp = "年数")
    private String experience;

    /** 中文名字 */
    @Excel(name = "中文名字")
    private String firstName;

    /** 英文名字 */
    @Excel(name = "英文名字")
    private String firstNameEn;

    /** 半身大图URL */
    @Excel(name = "半身大图URL")
    private String halfPhotoBig;

    /** 身高（英制） */
    @Excel(name = "身高", readConverterExp = "英制")
    private String height;

    /** 身高（公制） */
    @Excel(name = "身高", readConverterExp = "公制")
    private String heightMetric;

    /** 球衣号码 */
    @Excel(name = "球衣号码")
    private String jerseyNo;

    /** 中文姓氏 */
    @Excel(name = "中文姓氏")
    private String lastName;

    /** 英文姓氏 */
    @Excel(name = "英文姓氏")
    private String lastNameEn;

    /** 球员代码（拼音/英文唯一标识） */
    @Excel(name = "球员代码", readConverterExp = "拼音/英文唯一标识")
    private String playerCode;

    /** 球员位置 */
    @Excel(name = "球员位置")
    private String position;

    /** 退役年份 */
    @Excel(name = "退役年份")
    private Long retireYear;

    /** 新秀赛季（0表示未知） */
    @Excel(name = "新秀赛季", readConverterExp = "0=表示未知")
    private Long rookieSeason;

    /** 毕业学校 */
    @Excel(name = "毕业学校")
    private String school;

    /** 赛季（0表示当前或未指定） */
    @Excel(name = "赛季", readConverterExp = "0=表示当前或未指定")
    private Long season;

    /** 开始职业生涯年份 */
    @Excel(name = "开始职业生涯年份")
    private Long startYear;

    /** 球队缩写 */
    @Excel(name = "球队缩写")
    private String teamAbbr;

    /** 球队所在城市 */
    @Excel(name = "球队所在城市")
    private String teamCity;

    /** 所属球队ID */
    @Excel(name = "所属球队ID")
    private Long teamId;

    /** 球队深色Logo链接 */
    @Excel(name = "球队深色Logo链接")
    private String teamLogoDark;

    /** 球队浅色Logo链接 */
    @Excel(name = "球队浅色Logo链接")
    private String teamLogoLight;

    /** 球队中文名 */
    @Excel(name = "球队中文名")
    private String teamName;

    /** 体重（英制） */
    @Excel(name = "体重", readConverterExp = "英=制")
    private String weight;

    /** 体重（公制） */
    @Excel(name = "体重", readConverterExp = "公=制")
    private String weightMetric;

    public void setPlayerId(Long playerId) 
    {
        this.playerId = playerId;
    }

    public Long getPlayerId() 
    {
        return playerId;
    }

    public void setAvatar(String avatar) 
    {
        this.avatar = avatar;
    }

    public String getAvatar() 
    {
        return avatar;
    }

    public void setBirthDate(Date birthDate) 
    {
        this.birthDate = birthDate;
    }

    public Date getBirthDate() 
    {
        return birthDate;
    }

    public void setCountry(String country) 
    {
        this.country = country;
    }

    public String getCountry() 
    {
        return country;
    }

    public void setDisplayName(String displayName) 
    {
        this.displayName = displayName;
    }

    public String getDisplayName() 
    {
        return displayName;
    }

    public void setDraftSeason(Long draftSeason) 
    {
        this.draftSeason = draftSeason;
    }

    public Long getDraftSeason() 
    {
        return draftSeason;
    }

    public void setExperience(String experience) 
    {
        this.experience = experience;
    }

    public String getExperience() 
    {
        return experience;
    }

    public void setFirstName(String firstName) 
    {
        this.firstName = firstName;
    }

    public String getFirstName() 
    {
        return firstName;
    }

    public void setFirstNameEn(String firstNameEn) 
    {
        this.firstNameEn = firstNameEn;
    }

    public String getFirstNameEn() 
    {
        return firstNameEn;
    }

    public void setHalfPhotoBig(String halfPhotoBig) 
    {
        this.halfPhotoBig = halfPhotoBig;
    }

    public String getHalfPhotoBig() 
    {
        return halfPhotoBig;
    }

    public void setHeight(String height) 
    {
        this.height = height;
    }

    public String getHeight() 
    {
        return height;
    }

    public void setHeightMetric(String heightMetric) 
    {
        this.heightMetric = heightMetric;
    }

    public String getHeightMetric() 
    {
        return heightMetric;
    }

    public void setJerseyNo(String jerseyNo) 
    {
        this.jerseyNo = jerseyNo;
    }

    public String getJerseyNo() 
    {
        return jerseyNo;
    }

    public void setLastName(String lastName) 
    {
        this.lastName = lastName;
    }

    public String getLastName() 
    {
        return lastName;
    }

    public void setLastNameEn(String lastNameEn) 
    {
        this.lastNameEn = lastNameEn;
    }

    public String getLastNameEn() 
    {
        return lastNameEn;
    }

    public void setPlayerCode(String playerCode) 
    {
        this.playerCode = playerCode;
    }

    public String getPlayerCode() 
    {
        return playerCode;
    }

    public void setPosition(String position) 
    {
        this.position = position;
    }

    public String getPosition() 
    {
        return position;
    }

    public void setRetireYear(Long retireYear) 
    {
        this.retireYear = retireYear;
    }

    public Long getRetireYear() 
    {
        return retireYear;
    }

    public void setRookieSeason(Long rookieSeason) 
    {
        this.rookieSeason = rookieSeason;
    }

    public Long getRookieSeason() 
    {
        return rookieSeason;
    }

    public void setSchool(String school) 
    {
        this.school = school;
    }

    public String getSchool() 
    {
        return school;
    }

    public void setSeason(Long season) 
    {
        this.season = season;
    }

    public Long getSeason() 
    {
        return season;
    }

    public void setStartYear(Long startYear) 
    {
        this.startYear = startYear;
    }

    public Long getStartYear() 
    {
        return startYear;
    }

    public void setTeamAbbr(String teamAbbr) 
    {
        this.teamAbbr = teamAbbr;
    }

    public String getTeamAbbr() 
    {
        return teamAbbr;
    }

    public void setTeamCity(String teamCity) 
    {
        this.teamCity = teamCity;
    }

    public String getTeamCity() 
    {
        return teamCity;
    }

    public void setTeamId(Long teamId) 
    {
        this.teamId = teamId;
    }

    public Long getTeamId() 
    {
        return teamId;
    }

    public void setTeamLogoDark(String teamLogoDark) 
    {
        this.teamLogoDark = teamLogoDark;
    }

    public String getTeamLogoDark() 
    {
        return teamLogoDark;
    }

    public void setTeamLogoLight(String teamLogoLight) 
    {
        this.teamLogoLight = teamLogoLight;
    }

    public String getTeamLogoLight() 
    {
        return teamLogoLight;
    }

    public void setTeamName(String teamName) 
    {
        this.teamName = teamName;
    }

    public String getTeamName() 
    {
        return teamName;
    }

    public void setWeight(String weight) 
    {
        this.weight = weight;
    }

    public String getWeight() 
    {
        return weight;
    }

    public void setWeightMetric(String weightMetric) 
    {
        this.weightMetric = weightMetric;
    }

    public String getWeightMetric() 
    {
        return weightMetric;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("playerId", getPlayerId())
            .append("avatar", getAvatar())
            .append("birthDate", getBirthDate())
            .append("country", getCountry())
            .append("displayName", getDisplayName())
            .append("draftSeason", getDraftSeason())
            .append("experience", getExperience())
            .append("firstName", getFirstName())
            .append("firstNameEn", getFirstNameEn())
            .append("halfPhotoBig", getHalfPhotoBig())
            .append("height", getHeight())
            .append("heightMetric", getHeightMetric())
            .append("jerseyNo", getJerseyNo())
            .append("lastName", getLastName())
            .append("lastNameEn", getLastNameEn())
            .append("playerCode", getPlayerCode())
            .append("position", getPosition())
            .append("retireYear", getRetireYear())
            .append("rookieSeason", getRookieSeason())
            .append("school", getSchool())
            .append("season", getSeason())
            .append("startYear", getStartYear())
            .append("teamAbbr", getTeamAbbr())
            .append("teamCity", getTeamCity())
            .append("teamId", getTeamId())
            .append("teamLogoDark", getTeamLogoDark())
            .append("teamLogoLight", getTeamLogoLight())
            .append("teamName", getTeamName())
            .append("weight", getWeight())
            .append("weightMetric", getWeightMetric())
            .toString();
    }
}
