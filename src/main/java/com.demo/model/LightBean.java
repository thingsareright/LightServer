package com.demo.model;

/**
 * 这个类用来表明灯的状态
 */
public class LightBean {

    private String lightPhoneId;    //手电筒所在设备的唯一ID
    private boolean state;          //灯的状态，true为开启，false为关闭
    private int luminance;          //灯的亮度，目前为1,2,3,4,5五个级别

    public LightBean() {
    }

    public LightBean(String lightPhoneId, boolean state, int luminance) {
        this.lightPhoneId = lightPhoneId;
        this.state = state;
        this.luminance = luminance;
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
}
