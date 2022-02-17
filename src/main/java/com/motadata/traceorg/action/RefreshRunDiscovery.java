package com.motadata.traceorg.action;

import com.motadata.traceorg.bean.NmsBean;
import com.motadata.traceorg.executor.RefreshRunDiscoveryExecutor;
import com.opensymphony.xwork2.ActionSupport;


import java.util.List;

public class RefreshRunDiscovery extends ActionSupport {

    NmsBean bean = null;
    static List<NmsBean> beanList = null;

    public List<NmsBean> getBeanList() {
        return beanList;
    }

    public static void setBeanList(List<NmsBean> beanList) {
        RefreshRunDiscovery.beanList = beanList;
    }

    public String execute() throws Exception {


        RefreshRunDiscoveryExecutor refreshRunDiscoveryExecutorObj=new RefreshRunDiscoveryExecutor();
        return refreshRunDiscoveryExecutorObj.refreshDiscoveryTable();
    }
}
