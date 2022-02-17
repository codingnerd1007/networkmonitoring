package com.motadata.traceorg.executor;



import com.motadata.traceorg.action.ReturnIndividualData;
import com.motadata.traceorg.bean.NmsDataVisualisationBean;
import com.motadata.traceorg.dao.GetSqlConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.opensymphony.xwork2.Action.SUCCESS;

public class ReturnIndividualDataExecutor {

    NmsDataVisualisationBean bean = null;
    List<NmsDataVisualisationBean> beanList = null;
    GetSqlConnection getSqlConnectionObj = new GetSqlConnection();
//    Connection populateCon = null;
    ResultSet rs = null;


    public String retrivePieChartData(String deviceIP, String deviceTag) throws Exception {
        Connection populateCon=null;
        try {

            String sql;
            PreparedStatement ps;
            String currentDate = String.valueOf(LocalDate.now());
            String currentTimestamp = currentDate + " 00:00:00";
            String currentTimestampEnd = currentDate + " 23:59:59";

//            GetSqlConnection getSqlConnectionObj = new GetSqlConnection();
            populateCon = getSqlConnectionObj.getCon();


            beanList = new ArrayList<NmsDataVisualisationBean>();


            sql = "SELECT  value , count(value) as frequency FROM NMS.DataDump where deviceIP='" + deviceIP + "' and metric='" + deviceTag + "' and value='0' and timestamp between '" + currentTimestamp + "' and '" + currentTimestampEnd + "';";
            ps = populateCon.prepareStatement(sql);
            rs = ps.executeQuery(sql);


            if (rs != null) {
                bean = new NmsDataVisualisationBean();
                while (rs.next()) {

                    if (rs.getString("value") == null) {
                        bean.setValue("0");
                    } else {
                        bean.setValue(rs.getString("value"));
                    }
                    bean.setFrequency(rs.getString("frequency"));
                }
                beanList.add(bean);
                ReturnIndividualData.setBeanList(beanList);
            }


            sql = "SELECT  value , count(value) as frequency FROM NMS.DataDump where deviceIP='" + deviceIP + "' and metric='" + deviceTag + "' and value='100' and timestamp between '" + currentTimestamp + "' and '" + currentTimestampEnd + "';";
            ps = populateCon.prepareStatement(sql);
            rs = ps.executeQuery(sql);


            if (rs != null) {
                bean = new NmsDataVisualisationBean();
                while (rs.next()) {

                    if (rs.getString("value") == null) {
                        bean.setValue("100");
                    } else {
                        bean.setValue(rs.getString("value"));
                    }
                    bean.setFrequency(rs.getString("frequency"));
                }
                beanList.add(bean);
                ReturnIndividualData.setBeanList(beanList);
            }


            sql = "SELECT  value , count(value) as frequency FROM NMS.DataDump where deviceIP='" + deviceIP + "' and metric='" + deviceTag + "' and value='20' and timestamp between '" + currentTimestamp + "' and '" + currentTimestampEnd + "';";
            ps = populateCon.prepareStatement(sql);
            rs = ps.executeQuery(sql);


            if (rs != null) {
                bean = new NmsDataVisualisationBean();
                while (rs.next()) {

                    if (rs.getString("value") == null) {
                        bean.setValue("20");
                    } else {
                        bean.setValue(rs.getString("value"));
                    }
                    bean.setFrequency(rs.getString("frequency"));
                }
                beanList.add(bean);
                ReturnIndividualData.setBeanList(beanList);
            }


            sql = "SELECT  value , count(value) as frequency FROM NMS.DataDump where deviceIP='" + deviceIP + "' and metric='" + deviceTag + "' and value='40' and timestamp between '" + currentTimestamp + "' and '" + currentTimestampEnd + "';";
            ps = populateCon.prepareStatement(sql);
            rs = ps.executeQuery(sql);


            if (rs != null) {
                bean = new NmsDataVisualisationBean();
                while (rs.next()) {

                    if (rs.getString("value") == null) {
                        bean.setValue("40");
                    } else {
                        bean.setValue(rs.getString("value"));
                    }
                    bean.setFrequency(rs.getString("frequency"));
                }
                beanList.add(bean);
                ReturnIndividualData.setBeanList(beanList);
            }


            sql = "SELECT  value , count(value) as frequency FROM NMS.DataDump where deviceIP='" + deviceIP + "' and metric='" + deviceTag + "' and value='60' and timestamp between '" + currentTimestamp + "' and '" + currentTimestampEnd + "';";
            ps = populateCon.prepareStatement(sql);
            rs = ps.executeQuery(sql);


            if (rs != null) {
                bean = new NmsDataVisualisationBean();
                while (rs.next()) {
                    if (rs.getString("value") == null) {
                        bean.setValue("60");
                    } else {
                        bean.setValue(rs.getString("value"));
                    }
                    bean.setFrequency(rs.getString("frequency"));
                }
                beanList.add(bean);
                ReturnIndividualData.setBeanList(beanList);
            }


            sql = "SELECT  value , count(value) as frequency FROM NMS.DataDump where deviceIP='" + deviceIP + "' and metric='" + deviceTag + "' and value='80' and timestamp between '" + currentTimestamp + "' and '" + currentTimestampEnd + "';";
            ps = populateCon.prepareStatement(sql);
            rs = ps.executeQuery(sql);


            if (rs != null) {
                bean = new NmsDataVisualisationBean();
                while (rs.next()) {
                    if (rs.getString("value") == null) {
                        bean.setValue("80");
                    } else {
                        bean.setValue(rs.getString("value"));
                    }
                    bean.setFrequency(rs.getString("frequency"));
                }
                beanList.add(bean);
                ReturnIndividualData.setBeanList(beanList);
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
