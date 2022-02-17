package com.motadata.traceorg.executor;

import com.motadata.traceorg.dao.GetSqlConnection;
import com.opensymphony.xwork2.ActionSupport;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class AddDeviceExecutor extends ActionSupport {
    GetSqlConnection getSqlConnectionObj = new GetSqlConnection();
    public InputStream addDevice(String deviceName, String deviceIP, String deviceType, String deviceTag) throws Exception {
//        GetSqlConnection getSqlConnectionObj = new GetSqlConnection();
        Connection con = getSqlConnectionObj.getCon();

        Statement st = null;
        String response = null;
        try {
                String sql="INSERT INTO AddedDevices(deviceName,deviceIP,deviceType,deviceTag) VALUES('"+deviceName+"','"+deviceIP+"','"+deviceType+"','"+deviceTag+"')";
                PreparedStatement ps = con.prepareStatement(sql);
                ps.executeUpdate(sql);
//            st = con.createStatement();
//            int value = st
//                    .executeUpdate("INSERT INTO AddedDevices(deviceName,deviceIP,deviceType,deviceTag)"
//                            + "VALUES('"
//                            + deviceName
//                            + "','"
//                            + deviceIP
//                            + "','"
//                            + deviceType
//                            + "','"
//                            + deviceTag
//                            +
//                            "')");
        } catch (SQLException ex) {
            ex.printStackTrace();
            response = ex.getMessage();

        } catch (Exception ex1) {
            ex1.printStackTrace();
        } finally {
            if (st != null) {
                st.close();
            }
            if (con != null) {
                con.close();
            }
        }
        if (response != null) {
            if (response.contains("Duplicate entry")) {
//                return "Device already added";
                return new ByteArrayInputStream("Device already Added".getBytes());
            } else {
                return new ByteArrayInputStream("Device Added".getBytes());
//        return "Device Added";
            }
        }
        return new ByteArrayInputStream("Device Added".getBytes());
    }
}
