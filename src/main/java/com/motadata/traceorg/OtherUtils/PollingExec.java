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
//    String deviceName;
//    String deviceIP;
//    String deviceType;
//    String deviceTag;
//    String status;

//    public PollingExec(String deviceName, String deviceIP, String deviceType, String deviceTag, String status) {
//        this.deviceName = deviceName;
//        this.deviceIP = deviceIP;
//        this.deviceType = deviceType;
//        this.deviceTag = deviceTag;
//        this.status = status;
//    }

    @Override
    public void run() {
        while (true)
        {
            HashMap<String,String> request=PollingUtil.takePollRequest();
            if(request!=null)
            {
//                String toPrint = deviceTag;
                String toPrint = request.get("deviceName");
                String deviceName=request.get("deviceName");
                String deviceIP=request.get("deviceIP");
                String deviceTag=request.get("deviceTag");
                System.out.println("threadInfo: " + toPrint);
                try {
//            Statement st;
                    int value;
//            Connection con,con1,con2;
                    if (deviceTag.equalsIgnoreCase("ping")) {
                        ArrayList<String> commandList = new ArrayList<String>();

                        commandList.add("ping");
                        commandList.add("-c");
                        commandList.add("5");
                        commandList.add(deviceIP);

                        ProcessBuilder build = new ProcessBuilder(commandList);
                        Process process = build.start();
                        // to read the output
                        BufferedReader input = new BufferedReader(new InputStreamReader
                                (process.getInputStream()));
                        String s = null;
                        String finalString = "";
                        String outcome = "";
                        while ((s = input.readLine()) != null) {
//                finalString += s + "\n";
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
                        PreparedStatement ps = populateCon.prepareStatement(sql);
                        ps.executeUpdate(sql);
//                st = populateCon.createStatement();
//                value = st
//                        .executeUpdate("INSERT INTO DataDump(deviceIP,metric,value,timestamp)"
//                                + "VALUES('"
//                                + deviceIP
//                                + "','"
//                                + deviceTag
//                                + "','"
//                                + pingResultInString
//                                + "','"
//                                + sdf3.format(new Timestamp(System.currentTimeMillis()))
//                                +
//                                "')");


                        //code for dumping the data ends here


                        if (pingResult < 100) {
                            if(populateCon==null)
                                populateCon = getSqlConnectionObj.getCon();
                            sql="UPDATE Monitors SET status='up' WHERE deviceIP='"+deviceIP+"'";
                            ps = populateCon.prepareStatement(sql);
                            ps.executeUpdate(sql);
//                    st = populateCon.createStatement();
//                    value = st
//                            .executeUpdate("UPDATE Monitors SET status='up'"
//                                    + "WHERE deviceIP='" + deviceIP + "'");
//

                        } else if (pingResult == 100) {
                            if(populateCon==null)
                                populateCon = getSqlConnectionObj.getCon();
                            sql="UPDATE Monitors SET status='down' WHERE deviceIP='"+deviceIP+"'";
                            ps = populateCon.prepareStatement(sql);
                            ps.executeUpdate(sql);
//                    st = populateCon.createStatement();
//                    value = st
//                            .executeUpdate("UPDATE Monitors SET status='down'"
//                                    + "WHERE deviceIP='" + deviceIP + "'");

                        }
                    } else if (deviceTag.equalsIgnoreCase("ssh")) {
                        String user = "";
                        String password = "";
//                Connection populateCon = null;
                        ResultSet rs = null;
//            retrieve credentials

//                        if(populateCon==null)
                            populateCon = getSqlConnectionObj.getCon();

                        String sql = "SELECT userName,password FROM CredentialsSSH WHERE deviceIP='" + deviceIP + "' and deviceTag='" + deviceTag + "';";
                        PreparedStatement ps = populateCon.prepareStatement(sql);
                        rs = ps.executeQuery(sql);


                        if (rs != null) {
                            while (rs.next()) {
                                user = rs.getString("userName");
                                password = rs.getString("password");

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
                            ps = populateCon.prepareStatement(sql);
                            ps.executeUpdate(sql);

//                    st = populateCon.createStatement();
//                    value = st
//                            .executeUpdate("UPDATE Monitors SET status='up'"
//                                    + "WHERE deviceIP='" + host + "'"
//                            );

                            //code foe dumping data begins
                            if(populateCon==null)
                                populateCon = getSqlConnectionObj.getCon();

                            sql="INSERT INTO DataDump(deviceIP,metric,value,timestamp) VALUES('"+deviceIP+"','"+deviceTag+"','"+toInsert+"','"+sdf3.format(new Timestamp(System.currentTimeMillis()))+"')";
                            ps = populateCon.prepareStatement(sql);
                            ps.executeUpdate(sql);
//
//                    st = populateCon.createStatement();
//                    value = st
//                            .executeUpdate("INSERT INTO DataDump(deviceIP,metric,value,timestamp)"
//                                    + "VALUES('"
//                                    + deviceIP
//                                    + "','"
//                                    + deviceTag
//                                    + "','"
//                                    + toInsert
//                                    + "','"
//                                    + sdf3.format(new Timestamp(System.currentTimeMillis()))
//                                    +
//                                    "')");

                            //code for dumping data ends
                        } else if (responseString == null) {
                            if(populateCon==null)
                                populateCon = getSqlConnectionObj.getCon();
                            sql="UPDATE Monitors SET status='down' WHERE deviceIP='"+host+"'";
                            ps = populateCon.prepareStatement(sql);
                            ps.executeUpdate(sql);
//                    st = populateCon.createStatement();
//                    value = st
//                            .executeUpdate("UPDATE Monitors SET status='down'"
//                                    + "WHERE deviceIP='" + host + "'"
//                            );

                            //code foe dumping data begins
                            if(populateCon==null)
                                populateCon = getSqlConnectionObj.getCon();

                            sql="INSERT INTO DataDump(deviceIP,metric,value,timestamp) VALUES('"+deviceIP+"','"+deviceTag+"','"+responseString+"','"+sdf3.format(new Timestamp(System.currentTimeMillis()))+"')";
                            ps = populateCon.prepareStatement(sql);
                            ps.executeUpdate(sql);

//                    st = populateCon.createStatement();
//                    value = st
//                            .executeUpdate("INSERT INTO DataDump(deviceIP,metric,value,timestamp)"
//                                    + "VALUES('"
//                                    + deviceIP
//                                    + "','"
//                                    + deviceTag
//                                    + "','"
//                                    + responseString
//                                    + "','"
//                                    + sdf3.format(new Timestamp(System.currentTimeMillis()))
//                                    +
//                                    "')");

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
