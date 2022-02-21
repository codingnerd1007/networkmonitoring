package com.motadata.traceorg.executor;



import com.motadata.traceorg.action.RefreshRunDiscovery;
import com.motadata.traceorg.bean.NmsBean;
import com.motadata.traceorg.dao.GetSqlConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.opensymphony.xwork2.Action.SUCCESS;

public class RefreshRunDiscoveryExecutor {

    ResultSet resultsetObj = null;
    NmsBean bean = null;
    List<NmsBean> beanList = null;
    GetSqlConnection getSqlConnectionObj = new GetSqlConnection();

    public String refreshDiscoveryTable(){
        Connection populateCon=null;
        try {
            populateCon = getSqlConnectionObj.getCon();

            String sql = "SELECT deviceName,deviceIP,deviceType,deviceTag FROM AddedDevices";
            PreparedStatement preparedStatementObj = populateCon.prepareStatement(sql);
            resultsetObj = preparedStatementObj.executeQuery(sql);


            beanList = new ArrayList<NmsBean>();

            if (resultsetObj != null) {
                while (resultsetObj.next()) {
                    bean = new NmsBean();
                    bean.setDeviceName(resultsetObj.getString("deviceName"));
                    bean.setDeviceIP(resultsetObj.getString("deviceIP"));
                    bean.setDeviceType(resultsetObj.getString("deviceType"));
                    bean.setDeviceTag(resultsetObj.getString("deviceTag"));
                    beanList.add(bean);
                }
                RefreshRunDiscovery.setBeanList(beanList);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (populateCon != null) {
                try
                {
                    populateCon.close();
                } catch (SQLException e)
                {
                    e.printStackTrace();
                }
            }
        }
        return SUCCESS;
    }
}
