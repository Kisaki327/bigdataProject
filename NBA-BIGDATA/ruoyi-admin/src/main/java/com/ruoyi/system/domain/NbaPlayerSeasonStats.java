package com.ruoyi.system.domain;

import java.math.BigDecimal;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * NBA 球员赛季统计数据实体类
 */
public class NbaPlayerSeasonStats extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /** 自增主键 */
    private Long id;

    /** 赛季 */
    private String season;

    /** 球员姓名 */
    private String name;

    /**球员英文名*/
    private  String enName;

    /** 球员唯一ID */
    private String playerid;

    /** 赛季总得分 */
    private Integer points;

    /** 总出场场次 */
    private Integer games;

    /** 首发场次 */
    private Integer gamesstarted;

    /** 总上场时间（分钟） */
    private Integer mins;

    /** 进攻篮板 */
    private Integer offrebs;

    /** 防守篮板 */
    private Integer defrebs;

    /** 失误次数 */
    private Integer turnovers;

    /** 犯规次数 */
    private Integer fouls;

    /** 场均得分 */
    private BigDecimal pointspg;

    /** 场均篮板 */
    private BigDecimal rebspg;

    /** 场均助攻 */
    private BigDecimal assistspg;

    /** 场均抢断 */
    private BigDecimal stealspg;

    /** 场均盖帽 */
    private BigDecimal blockspg;

    /** 投篮命中率（例如 "52.3%"） */
    private String fgpctStr;

    /** 三分命中率 */
    private String tppctStr;

    /** 罚球命中率 */
    private String ftpctStr;

    /** 简化版 PER（球员效率值） */
    private BigDecimal per;

    /**球员头像*/
    private  String avatar;

    /**球队logo*/
    private  String logo;

    /**球员位置*/
    private String playerAddr;

    /**球员球衣号码*/
    private Integer num;

    private String teamname;

    private String firstNameEn;

    // ====== Getter & Setter ======

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSeason() {
        return season;
    }

    public void setSeason(String season) {
        this.season = season;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlayerid() {
        return playerid;
    }

    public void setPlayerid(String playerid) {
        this.playerid = playerid;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public Integer getGames() {
        return games;
    }

    public void setGames(Integer games) {
        this.games = games;
    }

    public Integer getGamesstarted() {
        return gamesstarted;
    }

    public void setGamesstarted(Integer gamesstarted) {
        this.gamesstarted = gamesstarted;
    }

    public Integer getMins() {
        return mins;
    }

    public void setMins(Integer mins) {
        this.mins = mins;
    }

    public Integer getOffrebs() {
        return offrebs;
    }

    public void setOffrebs(Integer offrebs) {
        this.offrebs = offrebs;
    }

    public Integer getDefrebs() {
        return defrebs;
    }

    public void setDefrebs(Integer defrebs) {
        this.defrebs = defrebs;
    }

    public Integer getTurnovers() {
        return turnovers;
    }

    public void setTurnovers(Integer turnovers) {
        this.turnovers = turnovers;
    }

    public Integer getFouls() {
        return fouls;
    }

    public void setFouls(Integer fouls) {
        this.fouls = fouls;
    }

    public BigDecimal getPointspg() {
        return pointspg;
    }

    public void setPointspg(BigDecimal pointspg) {
        this.pointspg = pointspg;
    }

    public BigDecimal getRebspg() {
        return rebspg;
    }

    public void setRebspg(BigDecimal rebspg) {
        this.rebspg = rebspg;
    }

    public BigDecimal getAssistspg() {
        return assistspg;
    }

    public void setAssistspg(BigDecimal assistspg) {
        this.assistspg = assistspg;
    }

    public BigDecimal getStealspg() {
        return stealspg;
    }

    public void setStealspg(BigDecimal stealspg) {
        this.stealspg = stealspg;
    }

    public BigDecimal getBlockspg() {
        return blockspg;
    }

    public void setBlockspg(BigDecimal blockspg) {
        this.blockspg = blockspg;
    }

    public String getFgpctStr() {
        return fgpctStr;
    }

    public void setFgpctStr(String fgpctStr) {
        this.fgpctStr = fgpctStr;
    }

    public String getTppctStr() {
        return tppctStr;
    }

    public void setTppctStr(String tppctStr) {
        this.tppctStr = tppctStr;
    }

    public String getFtpctStr() {
        return ftpctStr;
    }

    public void setFtpctStr(String ftpctStr) {
        this.ftpctStr = ftpctStr;
    }

    public BigDecimal getPer() {
        return per;
    }

    public void setPer(BigDecimal per) {
        this.per = per;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getPlayerAddr() {
        return playerAddr;
    }

    public void setPlayerAddr(String playerAddr) {
        this.playerAddr = playerAddr;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public String getEnName() {
        return enName;
    }

    public void setEnName(String enName) {
        this.enName = enName;
    }

    public String getTeamname() {
        return teamname;
    }

    public void setTeamname(String teamname) {
        this.teamname = teamname;
    }

    public String getFirstNameEn() {
        return firstNameEn;
    }

    public void setFirstNameEn(String firstNameEn) {
        this.firstNameEn = firstNameEn;
    }
}
