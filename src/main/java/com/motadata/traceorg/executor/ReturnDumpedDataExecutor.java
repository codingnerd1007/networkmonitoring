package com.motadata.traceorg.executor;



import com.motadata.traceorg.action.ReturnDumpedData;
import com.motadata.traceorg.bean.NmsDashboardBean;
import com.motadata.traceorg.dao.GetSqlConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import static com.opensymphony.xwork2.Action.SUCCESS;

public class ReturnDumpedDataExecutor {
    NmsDashboardBean bean = null;
    List<NmsDashboardBean> beanList = null;
    GetSqlConnection getSqlConnectionObj = new GetSqlConnection();
//    Connection populateCon = null;
    ResultSet rs = null;

    public String retriveDumpedData() throws Exception {
        Connection populateCon=null;
        try {
            String sql;
            PreparedStatement ps;
//            GetSqlConnection getSqlConnectionObj = new GetSqlConnection();
            populateCon = getSqlConnectionObj.getCon();


            beanList = new ArrayList<NmsDashboardBean>();
            bean = new NmsDashboardBean();


            sql = "SELECT distinct count(deviceIP) as total FROM NMS.Monitors;";
            ps = populateCon.prepareStatement(sql);
            rs = ps.executeQuery(sql);


            if (rs != null) {
                while (rs.next())
                    bean.setTotal(rs.getString("total"));
            }


            sql = "SELECT count(status) as active FROM NMS.Monitors where status='up';";
            ps = populateCon.prepareStatement(sql);
            rs = ps.executeQuery(sql);


            if (rs != null) {
                while (rs.next())
                    bean.setActive(rs.getString("active"));
            }


            sql = "SELECT count(status) as unknown FROM NMS.Monitors where status='unknown';";
            ps = populateCon.prepareStatement(sql);
            rs = ps.executeQuery(sql);


            if (rs != null) {
                while (rs.next())
                    bean.setUnknown(rs.getString("unknown"));
            }


            sql = "SELECT count(status) as inactive FROM NMS.Monitors where status='down';";
            ps = populateCon.prepareStatement(sql);
            rs = ps.executeQuery(sql);


            if (rs != null) {
                while (rs.next())
                    bean.setInactive(rs.getString("inactive"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (populateCon != null) {
                populateCon.close();
            }
        }
        beanList.add(bean);
        ReturnDumpedData.setBeanList(beanList);
        return SUCCESS;
    }
}
