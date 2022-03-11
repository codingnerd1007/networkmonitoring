package com.motadata.traceorg.OtherUtils;



import com.motadata.traceorg.dao.GetSqlConnection;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;

public class Polling implements Job {

    private Connection populateCon = null;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.out.println("\n This is executing\n");

        try {


            GetSqlConnection getSqlConnectionObj = new GetSqlConnection();
            populateCon = getSqlConnectionObj.getCon();


            String sql = "SELECT deviceName,deviceIP,deviceType,deviceTag,status FROM Monitors";
            PreparedStatement preparedStatementObj = populateCon.prepareStatement(sql);
            ResultSet resultsetObj = preparedStatementObj.executeQuery(sql);

            if (resultsetObj != null) {
                while (resultsetObj.next()) {
                    HashMap<String,String> request=new HashMap<>();
                    request.put("deviceName", resultsetObj.getString("deviceName"));
                    request.put("deviceIP", resultsetObj.getString("deviceIP"));
                    request.put("deviceType", resultsetObj.getString("deviceType"));
                    request.put("deviceTag", resultsetObj.getString("deviceTag"));
                    request.put("status", resultsetObj.getString("status"));
                    PollingUtil.putPollRequest(request);

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (populateCon != null) {
                try {
                    populateCon.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
