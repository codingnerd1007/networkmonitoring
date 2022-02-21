package com.motadata.traceorg.action;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.motadata.traceorg.bean.NmsBeanMonitorStatus;
import com.motadata.traceorg.dao.GetSqlConnection;
import com.opensymphony.xwork2.ActionSupport;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RunDiscoveryOptions extends ActionSupport {
    ResultSet resultsetObj = null;
    NmsBeanMonitorStatus bean = null;
    List<NmsBeanMonitorStatus> beanList = null;
    Connection populateCon=null;
    private String entryToDelete;
    private String deviceName;
    private String deviceIP;
    private String deviceType;
    private String deviceTag;
    private String status;

    private InputStream inputStream;
    public InputStream getInputStream() {
        return inputStream;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public String getDeviceIP() {
        return deviceIP;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public String getDeviceTag() {
        return deviceTag;
    }

    public String getStatus() {
        return status;
    }

    public String getEntryToDelete() {
        return entryToDelete;
    }

    public List<NmsBeanMonitorStatus> getBeanList() {
        return beanList;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public void setDeviceIP(String deviceIP) {
        this.deviceIP = deviceIP;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public void setDeviceTag(String deviceTag) {
        this.deviceTag = deviceTag;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setEntryToDelete(String entryToDelete) {
        this.entryToDelete = entryToDelete;
    }

    public void setBeanList(List<NmsBeanMonitorStatus> beanList) {
        this.beanList = beanList;
    }

    public String delete() throws Exception {

        try {
            GetSqlConnection getSqlConnectionObj=new GetSqlConnection();
            populateCon = getSqlConnectionObj.getCon();
        } catch (Exception e) {
            e.printStackTrace();

        }

        try {

            String sql = "DELETE FROM AddedDevices WHERE deviceIP='"+entryToDelete+"'";
            PreparedStatement preparedStatementObj = populateCon.prepareStatement(sql);
            preparedStatementObj.executeUpdate(sql);

        } catch (SQLException ex) {
            System.out.println("SQL statement is not executed!" + ex);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (populateCon != null) {
                populateCon.close();
            }
        }

        return SUCCESS;
    }

    public String run() throws Exception{
        if(deviceTag.equalsIgnoreCase("ping")) {
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

            String outcome = null;
            while ((s = input.readLine()) != null) {

                char elementAtIndexOne=s.charAt(0);
                char elementToCompareWith='5';

                if(elementAtIndexOne == elementToCompareWith){
                    int startIndex = (s.lastIndexOf(',') + 2);
                    int endIndex = s.lastIndexOf('p') - 2;
                    outcome = s.substring(startIndex, endIndex);
                }
            }

            int pingResult = Integer.parseInt(outcome.trim());
            if (pingResult==100){
                inputStream=new ByteArrayInputStream("The Device Is Not Pingable".getBytes());
                return SUCCESS;
            }
            if (pingResult < 100) {
                GetSqlConnection getSqlConnectionObj=new GetSqlConnection();
                Connection con = getSqlConnectionObj.getCon();

                try {

                    String sql="INSERT INTO Monitors(deviceName,deviceIP,deviceType,deviceTag,status) VALUES('"+deviceName+"','"+deviceIP+"','"+deviceType+"','"+deviceTag+"','"+"unknown"+"')";
                    PreparedStatement preparedStatementObj = con.prepareStatement(sql);
                    preparedStatementObj.executeUpdate(sql);

                } catch (SQLException ex) {
                    System.out.println("Run discovery ping"+ ex.getMessage());
                }
                catch (Exception ex1){
                    System.out.println("Run Discovery exception"+ ex1.getMessage());
                }
                finally {

                    if (con != null) {
                        con.close();
                    }
                }
            }
            inputStream= new ByteArrayInputStream("Ping Discovery Successful, Device provisioned!".getBytes());
            return SUCCESS;
        }
        else if (deviceTag.equalsIgnoreCase("ssh")){
            String user="";
            String password="";



            try {
                String hostName="localhost";
                Class.forName("com.mysql.jdbc.Driver");
                populateCon = DriverManager.getConnection(
                        "jdbc:mysql://"+hostName+":3306/NMS?autoReconnect=true&useSSL=false", "root", "Mind@123");
            } catch (Exception e) {
                e.printStackTrace();

            }

            try {
                String sql = "SELECT userName,password FROM CredentialsSSH WHERE deviceIP='"+deviceIP+"' and deviceTag='"+deviceTag+"';";
                PreparedStatement preparedStatementObj = populateCon.prepareStatement(sql);
                resultsetObj = preparedStatementObj.executeQuery(sql);

            } catch (Exception e) {
                e.printStackTrace();

            }

            try {

                if (resultsetObj != null) {
                    while (resultsetObj.next()) {
                        user=resultsetObj.getString("userName");
                        password=resultsetObj.getString("password");

                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            finally {
                if (populateCon!= null) {
                    populateCon.close();
                }
            }

            String host = deviceIP;
            String command="uname";
            String responseString=null;
            int port = 22;
            Session session=null;
            ChannelExec channel=null;
            try {
                session=new JSch().getSession(user,host,port);
                session.setPassword(password);
                session.setConfig("StrictHostKeyChecking", "no");
                session.setTimeout(10000);

                session.connect(10000);

                channel = (ChannelExec) session.openChannel("exec");
                channel.setCommand(command);
                ByteArrayOutputStream responseStream = new ByteArrayOutputStream();
                channel.setOutputStream(responseStream);
                channel.connect();
                if(channel.isConnected())
                {
                    Thread.sleep(100);

                }
                responseString = new String(responseStream.toByteArray());
            }
            catch (Exception e){
                e.printStackTrace();
            }
            finally {
                if (session != null) {
                    session.disconnect();
                }
                if (channel != null) {
                    channel.disconnect();
                }
            }
            if (responseString!=null) {
                if(responseString.equals("Linux\n")) {
                    GetSqlConnection getSqlConnectionObj=new GetSqlConnection();
                    Connection con = getSqlConnectionObj.getCon();

                    try {

                        String sql="INSERT INTO Monitors(deviceName,deviceIP,deviceType,deviceTag,status) VALUES('"+deviceName+"','"+deviceIP+"','"+deviceType+"','"+deviceTag+"','"+"unknown"+"')";
                        PreparedStatement preparedStatementObj = con.prepareStatement(sql);
                        preparedStatementObj.executeUpdate(sql);

                    } catch (SQLException ex) {
                        System.out.println("SQL statement is not executed!" + ex.getMessage());
                    } catch (Exception e) {
                        System.out.println("SSH run discovery error" + e.getMessage());
                    } finally {
                        if (con != null) {
                            con.close();
                        }
                    }
                    inputStream = new ByteArrayInputStream("SSH Discovery Successful, Device Provisioned!".getBytes());
                    return SUCCESS;
                }
                else{
                    inputStream = new ByteArrayInputStream("Not a Linux Device, Discovery failed!".getBytes());
                    return SUCCESS;
                }
            }
            else if(responseString==null){
                inputStream= new ByteArrayInputStream("SSH Discovery Unsuccessful".getBytes());
                return SUCCESS;
            }
        }
        return SUCCESS;
    }

    public String refreshMonitorGrid() throws Exception{
        /*
            updating UI using DB
         */

        try {
            GetSqlConnection getSqlConnectionObj=new GetSqlConnection();
            populateCon =getSqlConnectionObj.getCon();
        } catch (Exception e) {
            e.printStackTrace();

        }

        try {
            String sql = "SELECT deviceName,deviceIP,deviceType,deviceTag,status FROM Monitors";
            PreparedStatement preparedStatementObj = populateCon.prepareStatement(sql);
            resultsetObj = preparedStatementObj.executeQuery(sql);

        } catch (Exception e) {
            e.printStackTrace();

        }

        try {
            beanList = new ArrayList<NmsBeanMonitorStatus>();

            if (resultsetObj != null) {
                while (resultsetObj.next()) {
                    bean = new NmsBeanMonitorStatus();
                    bean.setDeviceName(resultsetObj.getString("deviceName"));
                    bean.setDeviceIP(resultsetObj.getString("deviceIP"));
                    bean.setDeviceType(resultsetObj.getString("deviceType"));
                    bean.setDeviceTag(resultsetObj.getString("deviceTag"));
                    bean.setStatus(resultsetObj.getString("status"));
                    beanList.add(bean);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            if (populateCon!= null) {
                populateCon.close();
            }
        }
        return SUCCESS;
    }
}
