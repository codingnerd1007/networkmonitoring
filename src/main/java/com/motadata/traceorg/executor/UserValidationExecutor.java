package com.motadata.traceorg.executor;



import com.motadata.traceorg.dao.GetSqlConnection;
import com.motadata.traceorg.dao.LoginModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class UserValidationExecutor {
    private Map<String, String> credentials = new HashMap<>();
    GetSqlConnection getSqlConnectionObj = new GetSqlConnection();
//    Connection populateCon = null;
    ResultSet resultsetObj = null;

    public String checkCredentials(LoginModel loginModel, Map<String, Object> session) {
        Connection populateCon = null;
        boolean flg=false;
        try {

            populateCon = getSqlConnectionObj.getCon();
            HashMap<String,String> result = new HashMap<>();
            result.put("validLogin","false");

            String sql = "SELECT * FROM Users where username= '"+ loginModel.getUserName()+"' and password = '"+loginModel.getPassword()+"'";
            PreparedStatement preparedStatementObj = populateCon.prepareStatement(sql);
            resultsetObj = preparedStatementObj.executeQuery(sql);

            if (resultsetObj != null) {
                while (resultsetObj.next()) {
                    session.put("user-session",loginModel);
                    result.put("validLogin","true");
                    flg=true;
                }
            }
            loginModel.setResult(result);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (populateCon != null) {
                try
                {
                    populateCon.close();
                } catch (SQLException e)
                {
                    e.printStackTrace();
                }
            }
        }
        if(flg){
            return "success";
        }
        else {
            return "error";
        }
    }
    public String removeSession(LoginModel loginModel, Map<String, Object> session){
        session.remove("user-session");
        loginModel.setUserName(null);
        loginModel.setPassword(null);
        return "success";
    }

}
