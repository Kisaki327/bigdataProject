package com.ruoyi.system.domain;

public class playerStats {

    private Integer dataType; // 1 为预测数据
    private String playerid;
    private String firstName;
    private String lastName;
    private String firstNameEn;
    private String lastNameEn;
    private String teamname;
    private String avatar;
    private String logo;

    public double getTppctStr() {
        return tppctStr;
    }

    public void setTppctStr(double tppctStr) {
        this.tppctStr = tppctStr;
    }

    public Integer getTpa() {
        return tpa;
    }

    public void setTpa(Integer tpa) {
        this.tpa = tpa;
    }

    public Integer getSteals() {
        return steals;
    }

    public void setSteals(Integer steals) {
        this.steals = steals;
    }

    public Integer getRebs() {
        return rebs;
    }

    public void setRebs(Integer rebs) {
        this.rebs = rebs;
    }

    public Integer getMins() {
        return mins;
    }

    public void setMins(Integer mins) {
        this.mins = mins;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public double getFgpctStr() {
        return fgpctStr;
    }

    public void setFgpctStr(double fgpctStr) {
        this.fgpctStr = fgpctStr;
    }

    public Integer getFga() {
        return fga;
    }

    public void setFga(Integer fga) {
        this.fga = fga;
    }

    public Integer getAssists() {
        return assists;
    }

    public void setAssists(Integer assists) {
        this.assists = assists;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getTeamname() {
        return teamname;
    }

    public void setTeamname(String teamname) {
        this.teamname = teamname;
    }

    public String getLastNameEn() {
        return lastNameEn;
    }

    public void setLastNameEn(String lastNameEn) {
        this.lastNameEn = lastNameEn;
    }

    public String getFirstNameEn() {
        return firstNameEn;
    }

    public void setFirstNameEn(String firstNameEn) {
        this.firstNameEn = firstNameEn;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getPlayerid() {
        return playerid;
    }

    public void setPlayerid(String playerid) {
        this.playerid = playerid;
    }

    private Integer assists;
    private Integer fga;
    private double fgpctStr;
    private Integer mins;
    private Integer points;
    private Integer rebs;
    private Integer steals;
    private Integer tpa;
    private double tppctStr;


    public Integer getDataType() {
        return dataType;
    }

    public void setDataType(Integer dataType) {
        this.dataType = dataType;
    }
}
