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
    public InputStream addDevice(String deviceName, String deviceIP, String deviceType, String deviceTag) {

        Connection con = null;

        String response = null;
        try {
            con = getSqlConnectionObj.getCon();
                String sql="INSERT INTO AddedDevices(deviceName,deviceIP,deviceType,deviceTag) VALUES('"+deviceName+"','"+deviceIP+"','"+deviceType+"','"+deviceTag+"')";
                PreparedStatement preparedStatementObj = con.prepareStatement(sql);
                preparedStatementObj.executeUpdate(sql);

        } catch (SQLException ex) {
            ex.printStackTrace();
            response = ex.getMessage();

        } catch (Exception ex1) {
            ex1.printStackTrace();
        } finally {
            try
            {
                if (con != null) {
                    con.close();
                }
            }
            catch (Exception exception){
                exception.printStackTrace();
            }

        }
        if (response != null) {
            if (response.contains("Duplicate entry")) {
                return new ByteArrayInputStream("Device already Added".getBytes());
            } else {
                return new ByteArrayInputStream("Device Added".getBytes());
            }
        }
        return new ByteArrayInputStream("Device Added".getBytes());
    }
}
