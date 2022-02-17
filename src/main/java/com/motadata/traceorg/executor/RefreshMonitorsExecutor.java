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

    ResultSet rs = null;
    NmsBeanMonitorStatus bean = null;
    List<NmsBeanMonitorStatus> beanList = null;
//    Connection populateCon = null;
    GetSqlConnection getSqlConnectionObj = new GetSqlConnection();
    public List<NmsBeanMonitorStatus> getBeanList() {
        return beanList;
    }

    public void setBeanList(List<NmsBeanMonitorStatus> beanList) {
        this.beanList = beanList;
    }


    public String refreshMonitorGridTable() throws Exception {

        Connection populateCon = null;
        try {
//            GetSqlConnection getSqlConnectionObj = new GetSqlConnection();
            populateCon = getSqlConnectionObj.getCon();


            String sql = "SELECT deviceName,deviceIP,deviceType,deviceTag,status FROM Monitors";
            PreparedStatement ps = populateCon.prepareStatement(sql);
            rs = ps.executeQuery(sql);


            beanList = new ArrayList<NmsBeanMonitorStatus>();

            if (rs != null) {
                while (rs.next()) {
                    bean = new NmsBeanMonitorStatus();
                    bean.setDeviceName(rs.getString("deviceName"));
                    bean.setDeviceIP(rs.getString("deviceIP"));
                    bean.setDeviceType(rs.getString("deviceType"));
                    bean.setDeviceTag(rs.getString("deviceTag"));
                    bean.setDeviceTag(rs.getString("status"));
                    beanList.add(bean);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (populateCon != null) {
                populateCon.close();
            }
        }
        return SUCCESS;
    }
}
