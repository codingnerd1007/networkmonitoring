package com.motadata.traceorg.action;
import com.motadata.traceorg.executor.RefreshMonitorsExecutor;
import com.opensymphony.xwork2.ActionSupport;

public class RefreshMonitors extends ActionSupport {


    public String execute() throws Exception {
        RefreshMonitorsExecutor refreshMonitorsExecutorObj=new RefreshMonitorsExecutor();
        return refreshMonitorsExecutorObj.refreshMonitorGridTable();
    }
}
