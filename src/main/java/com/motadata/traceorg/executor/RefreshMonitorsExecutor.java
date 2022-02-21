package com.motadata.traceorg.executor;


import com.motadata.traceorg.bean.NmsBeanMonitorStatus;
import com.motadata.traceorg.dao.GetSqlConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import static com.opensymphony.xwork2.Action.SUCCESS;

public class RefreshMonitorsExecutor {

    ResultSet resultsetObj = null;
    NmsBeanMonitorStatus bean = null;
    List<NmsBeanMonitorStatus> beanList = null;

    GetSqlConnection getSqlConnectionObj = new GetSqlConnection();
    public List<NmsBeanMonitorStatus> getBeanList() {
        return beanList;
    }

    public void setBeanList(List<NmsBeanMonitorStatus> beanList) {
        this.beanList = beanList;
    }


    public String refreshMonitorGridTable(){

        Connection populateCon = null;
        try {
//            GetSqlConnection getSqlConnectionObj = new GetSqlConnection();
            populateCon = getSqlConnectionObj.getCon();


            String sql = "SELECT deviceName,deviceIP,deviceType,deviceTag,status FROM Monitors";
            PreparedStatement preparedStatementObj = populateCon.prepareStatement(sql);
            resultsetObj = preparedStatementObj.executeQuery(sql);


            beanList = new ArrayList<NmsBeanMonitorStatus>();

            if (resultsetObj != null) {
                while (resultsetObj.next()) {
                    bean = new NmsBeanMonitorStatus();
                    bean.setDeviceName(resultsetObj.getString("deviceName"));
                    bean.setDeviceIP(resultsetObj.getString("deviceIP"));
                    bean.setDeviceType(resultsetObj.getString("deviceType"));
                    bean.setDeviceTag(resultsetObj.getString("deviceTag"));
                    bean.setDeviceTag(resultsetObj.getString("status"));
                    beanList.add(bean);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (populateCon != null) {
                try
                {
                    populateCon.close();
                }
                catch (Exception populateConException)
                {
                    populateConException.printStackTrace();
                }
            }
        }
        return SUCCESS;
    }
}
