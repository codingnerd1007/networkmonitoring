package com.motadata.traceorg.OtherUtils;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.motadata.traceorg.dao.GetSqlConnection;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStreamReader;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;

public class PollingExec implements Runnable {
    private static final SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private final GetSqlConnection getSqlConnectionObj = new GetSqlConnection();
    private Statement st;
    private Connection populateCon;

    @Override
    public void run() {
        while (true)
        {
            HashMap<String,String> request=PollingUtil.takePollRequest();
            if(request!=null)
            {

                String toPrint = request.get("deviceName");
                String deviceName=request.get("deviceName");
                String deviceIP=request.get("deviceIP");
                String deviceTag=request.get("deviceTag");
                System.out.println("threadInfo: " + toPrint);
                try {
                    int value;
                    if (deviceTag.equalsIgnoreCase("ping")) {
                        ArrayList<String> commandList = new ArrayList<String>();

                        commandList.add("ping");
                        commandList.add("-c");
                        commandList.add("5");
                        commandList.add(deviceIP);

                        ProcessBuilder build = new ProcessBuilder(commandList);
                        Process process = build.start();

                        BufferedReader input = new BufferedReader(new InputStreamReader
                                (process.getInputStream()));
                        String s = null;
                        String outcome = "";
                        while ((s = input.readLine()) != null) {
                            char elementAtIndexOne = s.charAt(0);
                            char elementToCompareWith = '5';

                            if (elementAtIndexOne == elementToCompareWith) {
                                int startIndex = (s.lastIndexOf(',') + 2);
                                int endIndex = s.lastIndexOf('p') - 2;
                                outcome = s.substring(startIndex, endIndex);
                            }
                        }
                        String pingResultInString = outcome.trim();
                        int pingResult = Integer.parseInt(outcome.trim());

                        //code for dumping the data starts here
                        populateCon = getSqlConnectionObj.getCon();
                        String sql="INSERT INTO DataDump(deviceIP,metric,value,timestamp) VALUES('"+deviceIP+"','"+deviceTag+"','"+pingResultInString+"','"+sdf3.format(new Timestamp(System.currentTimeMillis()))+"')";
                        PreparedStatement preparedStatementObj = populateCon.prepareStatement(sql);
                        preparedStatementObj.executeUpdate(sql);

                        //code for dumping the data ends here


                        if (pingResult < 100) {
                            if(populateCon==null)
                                populateCon = getSqlConnectionObj.getCon();
                            sql="UPDATE Monitors SET status='up' WHERE deviceIP='"+deviceIP+"'";
                            preparedStatementObj = populateCon.prepareStatement(sql);
                            preparedStatementObj.executeUpdate(sql);

                        } else if (pingResult == 100) {
                            if(populateCon==null)
                                populateCon = getSqlConnectionObj.getCon();
                            sql="UPDATE Monitors SET status='down' WHERE deviceIP='"+deviceIP+"'";
                            preparedStatementObj = populateCon.prepareStatement(sql);
                            preparedStatementObj.executeUpdate(sql);

                        }
                    } else if (deviceTag.equalsIgnoreCase("ssh")) {
                        String user = "";
                        String password = "";

                        ResultSet resultsetObj = null;
//            retrieve credentials

                            populateCon = getSqlConnectionObj.getCon();

                        String sql = "SELECT userName,password FROM CredentialsSSH WHERE deviceIP='" + deviceIP + "' and deviceTag='" + deviceTag + "';";
                        PreparedStatement preparedStatementObj = populateCon.prepareStatement(sql);
                        resultsetObj = preparedStatementObj.executeQuery(sql);


                        if (resultsetObj != null) {
                            while (resultsetObj.next()) {
                                user = resultsetObj.getString("userName");
                                password = resultsetObj.getString("password");

                            }
                        }


//            credentials end

                        String host = deviceIP;
                        String command = "free";
                        String responseString = null;
                        String[] splitString = null;
                        String secondRow = null;
                        String[] allValues = null;
                        String freeRam = null;
                        String totalRam = null;
                        String toInsert = null;
                        int port = 22;
                        Session session = null;
                        ChannelExec channel = null;

                        session = new JSch().getSession(user, host, port);
                        session.setPassword(password);
                        session.setConfig("StrictHostKeyChecking", "no");
                        session.setTimeout(10000);

                        session.connect(10000);
                        boolean valueAgain = session.isConnected();
                        if (valueAgain) {
                            channel = (ChannelExec) session.openChannel("exec");
                            channel.setCommand(command);
                            ByteArrayOutputStream responseStream = new ByteArrayOutputStream();
                            channel.setOutputStream(responseStream);
                            channel.connect();
                            if (channel.isConnected()) {
                                Thread.sleep(100);
//                    channel.disconnect();
                            }
                            responseString = new String(responseStream.toByteArray());
                            if (responseString != null) {
                                splitString = responseString.split("\n");
                                secondRow = splitString[1];
                                allValues = secondRow.split(" +");
                                totalRam = allValues[1];
                                freeRam = allValues[3];
                                toInsert = totalRam + "_" + freeRam;
                            }
                        } else {
                            System.out.println("we r in else part");
                        }

                        if (responseString != null) {
                            if(populateCon==null)
                                populateCon = getSqlConnectionObj.getCon();

                            sql="UPDATE Monitors SET status='up' WHERE deviceIP='"+host+"'";
                            preparedStatementObj = populateCon.prepareStatement(sql);
                            preparedStatementObj.executeUpdate(sql);

                            //code foe dumping data begins
                            if(populateCon==null)
                                populateCon = getSqlConnectionObj.getCon();

                            sql="INSERT INTO DataDump(deviceIP,metric,value,timestamp) VALUES('"+deviceIP+"','"+deviceTag+"','"+toInsert+"','"+sdf3.format(new Timestamp(System.currentTimeMillis()))+"')";
                            preparedStatementObj = populateCon.prepareStatement(sql);
                            preparedStatementObj.executeUpdate(sql);

                            //code for dumping data ends
                        } else if (responseString == null) {
                            if(populateCon==null)
                                populateCon = getSqlConnectionObj.getCon();
                            sql="UPDATE Monitors SET status='down' WHERE deviceIP='"+host+"'";
                            preparedStatementObj = populateCon.prepareStatement(sql);
                            preparedStatementObj.executeUpdate(sql);

                            //code foe dumping data begins
                            if(populateCon==null)
                                populateCon = getSqlConnectionObj.getCon();

                            sql="INSERT INTO DataDump(deviceIP,metric,value,timestamp) VALUES('"+deviceIP+"','"+deviceTag+"','"+responseString+"','"+sdf3.format(new Timestamp(System.currentTimeMillis()))+"')";
                            preparedStatementObj = populateCon.prepareStatement(sql);
                            preparedStatementObj.executeUpdate(sql);

                            //code for dumping data ends

                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    try {
                        if (st != null) {
                            st.close();
                        }
                        if (populateCon != null) {
                            populateCon.close();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }

    }
}
