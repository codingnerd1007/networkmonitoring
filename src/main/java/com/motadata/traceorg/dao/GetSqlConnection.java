package com.motadata.traceorg.dao;

import java.sql.Connection;
import java.sql.DriverManager;



public class GetSqlConnection {
    static
    {
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public Connection getCon() throws Exception {
        String hostName = "localhost";
        return DriverManager.getConnection(
                "jdbc:mysql://" + hostName + ":3306/NMS?autoReconnect=true&useSSL=false", "root", "Mind@123");
    }
}
