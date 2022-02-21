package com.motadata.traceorg.executor;


import com.motadata.traceorg.action.ReturnMemoryData;
import com.motadata.traceorg.bean.NMSMemoryDataBean;
import com.motadata.traceorg.dao.GetSqlConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.opensymphony.xwork2.Action.SUCCESS;

public class ReturnMemoryDataExecutor {

    ResultSet resultsetObj = null;
    GetSqlConnection getSqlConnectionObj = new GetSqlConnection();

    public String getDataForTimeSeriesBarChart(String deviceIP, String deviceTag) {
        Connection populateCon = null;
        try {

            String currentDate = String.valueOf(LocalDate.now());
            String currentTimestamp = currentDate + " 00:00:00";
            String currentTimestampEnd = currentDate + " 23:59:59";
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

            populateCon = getSqlConnectionObj.getCon();

            List<NMSMemoryDataBean> beanList = new ArrayList<NMSMemoryDataBean>();

            String sql = "SELECT  value , timestamp FROM NMS.DataDump where deviceIP='" + deviceIP + "' and metric='" + deviceTag + "' and value!='null' and timestamp between '" + currentTimestamp + "' and '" + currentTimestampEnd + "';";
            PreparedStatement preparedStatementObj = populateCon.prepareStatement(sql);
            resultsetObj = preparedStatementObj.executeQuery(sql);

            if (resultsetObj != null) {

                while (resultsetObj.next()) {
                    NMSMemoryDataBean bean = new NMSMemoryDataBean();
                    bean.setValue(resultsetObj.getString("value"));
                    bean.setTimestamp(resultsetObj.getString("timestamp"));
                    beanList.add(bean);
                }
                ReturnMemoryData.setBeanList(beanList);
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
