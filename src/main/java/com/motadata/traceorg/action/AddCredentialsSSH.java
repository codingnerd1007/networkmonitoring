package com.motadata.traceorg.action;

import com.motadata.traceorg.executor.AddCredentialsSshExecutor;
import com.opensymphony.xwork2.ActionSupport;


public class AddCredentialsSSH extends ActionSupport {

    private String deviceIP;
    private String deviceTag;
    private String userName;
    private String password;

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public String getDeviceIP() {
        return deviceIP;
    }

    public String getDeviceTag() {
        return deviceTag;
    }

    public void setDeviceIP(String deviceIP) {
        this.deviceIP = deviceIP;
    }

    public void setDeviceTag(String deviceTag) {
        this.deviceTag = deviceTag;
    }

    public String execute()throws Exception{

        AddCredentialsSshExecutor addCredentialsSshExecutorObj=new AddCredentialsSshExecutor();
        return addCredentialsSshExecutorObj.addSshCredentials(deviceIP,deviceTag,userName,password);
    }
}
