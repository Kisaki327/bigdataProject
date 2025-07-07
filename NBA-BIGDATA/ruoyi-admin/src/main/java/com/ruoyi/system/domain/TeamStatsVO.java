package com.ruoyi.system.domain;

public class TeamStatsVO {

    // 赛季（如 2023-24）
    private String season;

    // 球队 ID
    private Long teamid;

    // 球队 Logo（暗色风格）
    private String logoDark;

    public String getLogoDark() {
        return logoDark;
    }

    public void setLogoDark(String logoDark) {
        this.logoDark = logoDark;
    }

    public Double getFouls() {
        return fouls;
    }

    public void setFouls(Double fouls) {
        this.fouls = fouls;
    }

    public Double getTurnovers() {
        return turnovers;
    }

    public void setTurnovers(Double turnovers) {
        this.turnovers = turnovers;
    }

    public Double getDefrebs() {
        return defrebs;
    }

    public void setDefrebs(Double defrebs) {
        this.defrebs = defrebs;
    }

    public Double getOffrebs() {
        return offrebs;
    }

    public void setOffrebs(Double offrebs) {
        this.offrebs = offrebs;
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

    public String getFgpctStr() {
        return fgpctStr;
    }

    public void setFgpctStr(String fgpctStr) {
        this.fgpctStr = fgpctStr;
    }

    public Double getBlockspg() {
        return blockspg;
    }

    public void setBlockspg(Double blockspg) {
        this.blockspg = blockspg;
    }

    public Double getStealspg() {
        return stealspg;
    }

    public void setStealspg(Double stealspg) {
        this.stealspg = stealspg;
    }

    public Double getAssistspg() {
        return assistspg;
    }

    public void setAssistspg(Double assistspg) {
        this.assistspg = assistspg;
    }

    public Double getRebspg() {
        return rebspg;
    }

    public void setRebspg(Double rebspg) {
        this.rebspg = rebspg;
    }

    public Double getPointspg() {
        return pointspg;
    }

    public void setPointspg(Double pointspg) {
        this.pointspg = pointspg;
    }

    public String getTeamname() {
        return teamname;
    }

    public void setTeamname(String teamname) {
        this.teamname = teamname;
    }

    // 球队名称
    private String teamname;

    // 场均得分 Points Per Game
    private Double pointspg;

    // 场均篮板 Rebounds Per Game
    private Double rebspg;

    // 场均助攻 Assists Per Game
    private Double assistspg;

    // 场均抢断 Steals Per Game
    private Double stealspg;

    // 场均盖帽 Blocks Per Game
    private Double blockspg;

    // 投篮命中率（格式化字符串，如 "47.1%"）
    private String fgpctStr;

    // 三分命中率（格式化字符串）
    private String tppctStr;

    // 罚球命中率（格式化字符串）
    private String ftpctStr;

    // 场均前场篮板
    private Double offrebs;

    // 场均后场篮板
    private Double defrebs;

    // 场均失误
    private Double turnovers;

    // 场均犯规
    private Double fouls;

    public String getSeason() {
        return season;
    }

    public void setSeason(String season) {
        this.season = season;
    }

    public Long getTeamid() {
        return teamid;
    }

    public void setTeamid(Long teamid) {
        this.teamid = teamid;
    }
}
