package com.ruoyi.system.domain;

import com.ruoyi.common.core.domain.BaseEntity;

public class playersDist extends BaseEntity {
    private static final long serialVersionUID = 1L;

    private String season;

    private String per;

    private String pointSpg;

    public String getSeason() {
        return season;
    }

    public void setSeason(String season) {
        this.season = season;
    }

    public String getPer() {
        return per;
    }

    public void setPer(String per) {
        this.per = per;
    }

    public String getPointSpg() {
        return pointSpg;
    }

    public void setPointSpg(String pointSpg) {
        this.pointSpg = pointSpg;
    }
}
