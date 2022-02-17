package com.motadata.traceorg.bean;

public class NmsBeanMonitorStatus {
    private String deviceName;
    private String deviceIP;
    private String deviceType;
    private String deviceTag;
    private String status;
    public String getDeviceName() {
        return deviceName;
    }

    public String getDeviceIP() {
        return deviceIP;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public String getDeviceTag() {
        return deviceTag;
    }

    public String getStatus() {
        return status;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public void setDeviceIP(String deviceIP) {
        this.deviceIP = deviceIP;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public void setDeviceTag(String deviceTag) {
        this.deviceTag = deviceTag;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
