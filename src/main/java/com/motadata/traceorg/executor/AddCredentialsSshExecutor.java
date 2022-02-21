package com.motadata.traceorg.executor;


import com.motadata.traceorg.dao.GetSqlConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;

import static com.opensymphony.xwork2.Action.SUCCESS;

public class AddCredentialsSshExecutor {
    GetSqlConnection getSqlConnectionObj = new GetSqlConnection();
    public String addSshCredentials(String deviceIP, String deviceTag, String userName, String password) {

        Connection con=null;

        try {
            con = getSqlConnectionObj.getCon();
            String sql="INSERT INTO CredentialsSSH(deviceIP,deviceTag,userName,password) VALUES('"+deviceIP+"','"+deviceTag+"','"+userName+"','"+password+"')";
            PreparedStatement preparedStatementObj = con.prepareStatement(sql);
            preparedStatementObj.executeUpdate(sql);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            if (con != null) {
                try
                {
                    con.close();
                }
                catch (Exception exception){
                    exception.printStackTrace();
                }
            }
        }
        return SUCCESS;
    }
}
