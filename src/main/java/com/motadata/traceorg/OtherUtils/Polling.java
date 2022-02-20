package com.motadata.traceorg.OtherUtils;



import com.motadata.traceorg.dao.GetSqlConnection;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Polling implements Job {

    private Connection populateCon = null;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.out.println("\n This is executing\n");

        try {


            GetSqlConnection getSqlConnectionObj = new GetSqlConnection();
            populateCon = getSqlConnectionObj.getCon();


            String sql = "SELECT deviceName,deviceIP,deviceType,deviceTag,status FROM Monitors";
            PreparedStatement ps = populateCon.prepareStatement(sql);
            ResultSet rs = ps.executeQuery(sql);

//
//            Thread t1 = null;
//            Thread t2 = null;
//            ExecutorService executorService = Executors.newFixedThreadPool(5);
//            executorService.execute(new Runnable() {
//
//                @Override
//                public void run() {
//                    System.out.println("ExecutorService");
//
//                }
//            });
//            executorService.shutdown();
//

            if (rs != null) {
                while (rs.next()) {
//
                    HashMap<String,String> request=new HashMap<>();
                    request.put("deviceName", rs.getString("deviceName"));
                    request.put("deviceIP", rs.getString("deviceIP"));
                    request.put("deviceType", rs.getString("deviceType"));
                    request.put("deviceTag", rs.getString("deviceTag"));
                    request.put("status", rs.getString("status"));
                    PollingUtil.putPollRequest(request);
//                    String deviceName = rs.getString("deviceName");
//                    String deviceIP = rs.getString("deviceIP");
//                    String deviceType = rs.getString("deviceType");
//                    String deviceTag = rs.getString("deviceTag");
//                    String status = rs.getString("status");
//                    if (deviceTag.equalsIgnoreCase("ssh")) {
//                        t1 = new Thread(new PollingExec(deviceName, deviceIP, deviceType, deviceTag, status));
//                        t1.start();
//                    } else if (deviceTag.equalsIgnoreCase("ping")) {
//                        t2 = new Thread(new PollingExec(deviceName, deviceIP, deviceType, deviceTag, status));
//                        t2.start();
//                    }

                }
            }
//            t1.join();
//            t2.join();

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
