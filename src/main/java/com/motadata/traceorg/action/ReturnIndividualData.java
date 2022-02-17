package com.motadata.traceorg.action;



import com.motadata.traceorg.bean.NmsDataVisualisationBean;
import com.motadata.traceorg.executor.ReturnIndividualDataExecutor;

import java.util.List;

public class ReturnIndividualData {
    private String deviceIP;
    private String deviceTag;
    NmsDataVisualisationBean bean=null;
    static List<NmsDataVisualisationBean> beanList=null;
    public List<NmsDataVisualisationBean> getBeanList() {
        return beanList;
    }

    public static void setBeanList(List<NmsDataVisualisationBean> beanList) {
        ReturnIndividualData.beanList = beanList;
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

    public String getDataForCharts() throws Exception{

        ReturnIndividualDataExecutor returnIndividualDataExecutorObj=new ReturnIndividualDataExecutor();
        return returnIndividualDataExecutorObj.retrivePieChartData(deviceIP,deviceTag);
    }
}
