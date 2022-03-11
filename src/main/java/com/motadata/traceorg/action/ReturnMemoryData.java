package com.motadata.traceorg.action;

import com.motadata.traceorg.bean.NMSMemoryDataBean;
import com.motadata.traceorg.executor.ReturnMemoryDataExecutor;
import com.opensymphony.xwork2.ActionSupport;


import java.sql.Connection;
import java.sql.ResultSet;
import java.util.List;

public class ReturnMemoryData extends ActionSupport {
    private String deviceIP;
    private String deviceTag;
    private NMSMemoryDataBean bean=null;
    private static List<NMSMemoryDataBean> beanList=null;
    Connection populateCon=null;
    ResultSet resultsetObj=null;

    public static void setBeanList(List<NMSMemoryDataBean> beanList) {
        ReturnMemoryData.beanList = beanList;
    }

    public List<NMSMemoryDataBean> getBeanList() {
        return beanList;
    }

    public void setDeviceTag(String deviceTag) {
        this.deviceTag = deviceTag;
    }

    public void setDeviceIP(String deviceIP) {
        this.deviceIP = deviceIP;
    }

    public String getDeviceTag() {
        return deviceTag;
    }

    public String getDeviceIP() {
        return deviceIP;
    }

    public String getDataForTimeSeriesChart() throws Exception{
        ReturnMemoryDataExecutor returnMemoryDataExecutorObj=new ReturnMemoryDataExecutor();
        return returnMemoryDataExecutorObj.getDataForTimeSeriesBarChart(deviceIP,deviceTag);
    }
}
