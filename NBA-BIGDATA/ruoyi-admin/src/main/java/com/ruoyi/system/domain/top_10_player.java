package com.ruoyi.system.domain;

import com.ruoyi.common.core.domain.BaseEntity;
/**
 * 每赛季top10球员信息
 * */
public class top_10_player extends BaseEntity {
    private static final long serialVersionUID = 1L;

    private String name;

    private String avatar;

    private String points;

    private String season;

    private String careerPoints;



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getSeason() {
        return season;
    }

    public void setSeason(String season) {
        this.season = season;
    }

    public String getPoints() {
        return points;
    }

    public void setPoints(String points) {
        this.points = points;
    }

    public String getCareerPoints() {
        return careerPoints;
    }

    public void setCareerPoints(String careerPoints) {
        this.careerPoints = careerPoints;
    }
}
