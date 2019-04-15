package com.bitedu.pojo.fabric;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * @Auther: 陈洲
 * @Date: 2019/3/17
 * @Description: com.bitedu.pojo.fabric
 * @version: 1.0
 */
public class Supervice implements Serializable {

    @JsonProperty(value = "$class")
    private String className;

    private String id;

    private Integer dayComTimes;

    private Double dayComNums;

    private Integer monthComTimes;

    private Double monthComNums;

    private Integer dayRecTimes;

    private Double dayRecNums;

    private Integer monthRecTimes;

    private Double monthRecNums;

    private Integer dayPubTimes;

    private Integer monthPubNums;

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getDayComTimes() {
        return dayComTimes;
    }

    public void setDayComTimes(Integer dayComTimes) {
        this.dayComTimes = dayComTimes;
    }

    public Double getDayComNums() {
        return dayComNums;
    }

    public void setDayComNums(Double dayComNums) {
        this.dayComNums = dayComNums;
    }

    public Integer getMonthComTimes() {
        return monthComTimes;
    }

    public void setMonthComTimes(Integer monthComTimes) {
        this.monthComTimes = monthComTimes;
    }

    public Double getMonthComNums() {
        return monthComNums;
    }

    public void setMonthComNums(Double monthComNums) {
        this.monthComNums = monthComNums;
    }

    public Integer getDayRecTimes() {
        return dayRecTimes;
    }

    public void setDayRecTimes(Integer dayRecTimes) {
        this.dayRecTimes = dayRecTimes;
    }

    public Double getDayRecNums() {
        return dayRecNums;
    }

    public void setDayRecNums(Double dayRecNums) {
        this.dayRecNums = dayRecNums;
    }

    public Integer getMonthRecTimes() {
        return monthRecTimes;
    }

    public void setMonthRecTimes(Integer monthRecTimes) {
        this.monthRecTimes = monthRecTimes;
    }

    public Double getMonthRecNums() {
        return monthRecNums;
    }

    public void setMonthRecNums(Double monthRecNums) {
        this.monthRecNums = monthRecNums;
    }

    public Integer getDayPubTimes() {
        return dayPubTimes;
    }

    public void setDayPubTimes(Integer dayPubTimes) {
        this.dayPubTimes = dayPubTimes;
    }

    public Integer getMonthPubNums() {
        return monthPubNums;
    }

    public void setMonthPubNums(Integer monthPubNums) {
        this.monthPubNums = monthPubNums;
    }
}
