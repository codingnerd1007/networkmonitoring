package com.motadata.traceorg.action;



import com.motadata.traceorg.bean.NmsDashboardBean;
import com.motadata.traceorg.executor.ReturnDumpedDataExecutor;

import java.util.List;

public class ReturnDumpedData {

    static List<NmsDashboardBean> beanList = null;

    public List<NmsDashboardBean> getBeanList() {
        return beanList;
    }

    public static void setBeanList(List<NmsDashboardBean> beanList) {
        ReturnDumpedData.beanList = beanList;
    }

    public String getDataForDashboard() throws Exception{

        ReturnDumpedDataExecutor returnDumpedDataExecutorObj=new ReturnDumpedDataExecutor();
        return returnDumpedDataExecutorObj.retriveDumpedData();
    }
}
