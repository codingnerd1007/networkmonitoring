package com.motadata.traceorg.executor;



import com.motadata.traceorg.action.RefreshRunDiscovery;
import com.motadata.traceorg.bean.NmsBean;
import com.motadata.traceorg.dao.GetSqlConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import static com.opensymphony.xwork2.Action.SUCCESS;

public class RefreshRunDiscoveryExecutor {

    ResultSet rs = null;
    NmsBean bean = null;
    List<NmsBean> beanList = null;
//    Connection populateCon = null;
    GetSqlConnection getSqlConnectionObj = new GetSqlConnection();

    public String refreshDiscoveryTable() throws Exception {
        Connection populateCon=null;
        try {
//            GetSqlConnection getSqlConnectionObj = new GetSqlConnection();
            populateCon = getSqlConnectionObj.getCon();


            String sql = "SELECT deviceName,deviceIP,deviceType,deviceTag FROM AddedDevices";
            PreparedStatement ps = populateCon.prepareStatement(sql);
            rs = ps.executeQuery(sql);


            beanList = new ArrayList<NmsBean>();

            if (rs != null) {
                while (rs.next()) {
                    bean = new NmsBean();
                    bean.setDeviceName(rs.getString("deviceName"));
                    bean.setDeviceIP(rs.getString("deviceIP"));
                    bean.setDeviceType(rs.getString("deviceType"));
                    bean.setDeviceTag(rs.getString("deviceTag"));
                    beanList.add(bean);
                }
                RefreshRunDiscovery.setBeanList(beanList);
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
