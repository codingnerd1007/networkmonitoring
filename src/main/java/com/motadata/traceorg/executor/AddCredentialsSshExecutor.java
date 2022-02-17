package com.motadata.traceorg.executor;


import com.motadata.traceorg.dao.GetSqlConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;

import static com.opensymphony.xwork2.Action.SUCCESS;

public class AddCredentialsSshExecutor {
    GetSqlConnection getSqlConnectionObj = new GetSqlConnection();
    public String addSshCredentials(String deviceIP, String deviceTag, String userName, String password) throws Exception {
//        GetSqlConnection getSqlConnectionObj = new GetSqlConnection();
        Connection con = getSqlConnectionObj.getCon();

        Statement st = null;
        try {
            String sql="INSERT INTO CredentialsSSH(deviceIP,deviceTag,userName,password) VALUES('"+deviceIP+"','"+deviceTag+"','"+userName+"','"+password+"')";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.executeUpdate(sql);
//            st = con.createStatement();
//            int value = st
//                    .executeUpdate("INSERT INTO CredentialsSSH(deviceIP,deviceTag,userName,password)"
//                            + "VALUES('"
//                            + deviceIP
//                            + "','"
//                            + deviceTag
//                            + "','"
//                            + userName
//                            + "','"
//                            + password
//                            +
//                            "')");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (st != null) {
                st.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return SUCCESS;
    }
}
