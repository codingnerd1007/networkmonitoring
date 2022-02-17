package com.motadata.traceorg.action;

import com.motadata.traceorg.executor.AddDeviceExecutor;
import com.opensymphony.xwork2.ActionSupport;

import java.io.InputStream;

public class AddDevice extends ActionSupport {

    private String deviceName;
    private String deviceIP;
    private String deviceType;
    private String deviceTag;

    private InputStream inputStream;
    public InputStream getInputStream() {
        return inputStream;
    }

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

    public String execute()throws Exception{

        AddDeviceExecutor addDeviceExecutorObj=new AddDeviceExecutor();
        inputStream = addDeviceExecutorObj.addDevice(deviceName,deviceIP,deviceType,deviceTag);
        return SUCCESS;
    }
}
