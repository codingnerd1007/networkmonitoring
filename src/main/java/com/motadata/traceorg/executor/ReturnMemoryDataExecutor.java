package com.motadata.traceorg.executor;


import com.motadata.traceorg.action.ReturnMemoryData;
import com.motadata.traceorg.bean.NMSMemoryDataBean;
import com.motadata.traceorg.dao.GetSqlConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.opensymphony.xwork2.Action.SUCCESS;

public class ReturnMemoryDataExecutor {

//    Connection populateCon = null;
    ResultSet rs = null;
    GetSqlConnection getSqlConnectionObj = new GetSqlConnection();

    public String getDataForTimeSeriesBarChart(String deviceIP, String deviceTag) throws Exception {
        Connection populateCon = null;
        try {


            String currentDate = String.valueOf(LocalDate.now());
            String currentTimestamp = currentDate + " 00:00:00";
            String currentTimestampEnd = currentDate + " 23:59:59";
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");


//            GetSqlConnection getSqlConnectionObj = new GetSqlConnection();
            populateCon = getSqlConnectionObj.getCon();


            List<NMSMemoryDataBean> beanList = new ArrayList<NMSMemoryDataBean>();


            String sql = "SELECT  value , timestamp FROM NMS.DataDump where deviceIP='" + deviceIP + "' and metric='" + deviceTag + "' and value!='null' and timestamp between '" + currentTimestamp + "' and '" + currentTimestampEnd + "';";
            PreparedStatement ps = populateCon.prepareStatement(sql);
            rs = ps.executeQuery(sql);


            if (rs != null) {

                while (rs.next()) {
                    NMSMemoryDataBean bean = new NMSMemoryDataBean();
                    bean.setValue(rs.getString("value"));
                    bean.setTimestamp(rs.getString("timestamp"));
                    beanList.add(bean);
                }
                ReturnMemoryData.setBeanList(beanList);
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
