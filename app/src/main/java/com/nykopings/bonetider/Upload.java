package com.nykopings.bonetider;

import java.io.Serializable;

public class Upload implements Serializable {
    private String date,day,fazr,shorook,zuhur,asr,maghrib,isha;

    public Upload(){

    }

    public Upload(String date, String day, String fazr, String shorook, String zuhur, String asr, String maghrib, String isha) {
        this.date = date;
        this.day = day;
        this.fazr = fazr;
        this.shorook = shorook;
        this.zuhur = zuhur;
        this.asr = asr;
        this.maghrib = maghrib;
        this.isha = isha;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getFazr() {
        return fazr;
    }

    public void setFazr(String fazr) {
        this.fazr = fazr;
    }

    public String getShorook() {
        return shorook;
    }

    public void setShorook(String shorook) {
        this.shorook = shorook;
    }

    public String getZuhur() {
        return zuhur;
    }

    public void setZuhur(String zuhur) {
        this.zuhur = zuhur;
    }

    public String getAsr() {
        return asr;
    }

    public void setAsr(String asr) {
        this.asr = asr;
    }

    public String getMaghrib() {
        return maghrib;
    }

    public void setMaghrib(String maghrib) {
        this.maghrib = maghrib;
    }

    public String getIsha() {
        return isha;
    }

    public void setIsha(String isha) {
        this.isha = isha;
    }
}
