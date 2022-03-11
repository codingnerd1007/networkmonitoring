package com.motadata.traceorg.executor;



import com.motadata.traceorg.action.ReturnDumpedData;
import com.motadata.traceorg.bean.NmsDashboardBean;
import com.motadata.traceorg.dao.GetSqlConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.opensymphony.xwork2.Action.SUCCESS;

public class ReturnDumpedDataExecutor {
    NmsDashboardBean bean = null;
    List<NmsDashboardBean> beanList = null;
    GetSqlConnection getSqlConnectionObj = new GetSqlConnection();
    ResultSet resultsetObj = null;

    public String retriveDumpedData(){
        Connection populateCon=null;
        try {
            String sql;
            PreparedStatement preparedStatementObj;

            populateCon = getSqlConnectionObj.getCon();

            beanList = new ArrayList<NmsDashboardBean>();
            bean = new NmsDashboardBean();


            sql = "SELECT distinct count(deviceIP) as total FROM NMS.Monitors;";
            preparedStatementObj = populateCon.prepareStatement(sql);
            resultsetObj = preparedStatementObj.executeQuery(sql);


            if (resultsetObj != null) {
                while (resultsetObj.next())
                    bean.setTotal(resultsetObj.getString("total"));
            }


            sql = "SELECT count(status) as active FROM NMS.Monitors where status='up';";
            preparedStatementObj = populateCon.prepareStatement(sql);
            resultsetObj = preparedStatementObj.executeQuery(sql);


            if (resultsetObj != null) {
                while (resultsetObj.next())
                    bean.setActive(resultsetObj.getString("active"));
            }


            sql = "SELECT count(status) as unknown FROM NMS.Monitors where status='unknown';";
            preparedStatementObj = populateCon.prepareStatement(sql);
            resultsetObj = preparedStatementObj.executeQuery(sql);


            if (resultsetObj != null) {
                while (resultsetObj.next())
                    bean.setUnknown(resultsetObj.getString("unknown"));
            }


            sql = "SELECT count(status) as inactive FROM NMS.Monitors where status='down';";
            preparedStatementObj = populateCon.prepareStatement(sql);
            resultsetObj = preparedStatementObj.executeQuery(sql);


            if (resultsetObj != null) {
                while (resultsetObj.next())
                    bean.setInactive(resultsetObj.getString("inactive"));
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
        beanList.add(bean);
        ReturnDumpedData.setBeanList(beanList);
        return SUCCESS;
    }
}
