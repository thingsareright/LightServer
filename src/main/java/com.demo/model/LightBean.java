package com.demo.model;

import java.io.Serializable;

/**
 * 这个类用来表明灯的状态
 */
public class LightBean implements Serializable{

    private String lightPhoneId;    //手电筒所在设备的唯一ID
    private boolean state;          //灯的状态，true为开启，false为关闭
    private int luminance;          //灯的亮度，目前为1,2,3,4,5五个级别
    private long lastActiveTime;   //安卓的上次活跃时间

    public LightBean() {
    }

    public LightBean(String lightPhoneId, boolean state, int luminance, long lastActiveTime) {
        this.lightPhoneId = lightPhoneId;
        this.state = state;
        this.luminance = luminance;
        this.lastActiveTime = lastActiveTime;
    }

    public String getLightPhoneId() {
        return lightPhoneId;
    }

    public void setLightPhoneId(String lightPhoneId) {
        this.lightPhoneId = lightPhoneId;
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public int getLuminance() {
        return luminance;
    }

    public void setLuminance(int luminance) {
        this.luminance = luminance;
    }

    public long getLastActiveTime() {
        return lastActiveTime;
    }

    public void setLastActiveTime(long lastActiveTime) {
        this.lastActiveTime = lastActiveTime;
    }

}
