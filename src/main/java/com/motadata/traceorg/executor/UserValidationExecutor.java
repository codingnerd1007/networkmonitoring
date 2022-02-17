package com.motadata.traceorg.executor;



import com.motadata.traceorg.dao.GetSqlConnection;
import com.motadata.traceorg.dao.LoginModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

public class UserValidationExecutor {
    private Map<String, String> credentials = new HashMap<>();
    GetSqlConnection getSqlConnectionObj = new GetSqlConnection();
//    Connection populateCon = null;
    ResultSet rs = null;

    public String checkCredentials(LoginModel loginModel, Map<String, Object> session) throws Exception {
        Connection populateCon = null;
        boolean flg=false;
        try {
//            GetSqlConnection getSqlConnectionObj = new GetSqlConnection();
            populateCon = getSqlConnectionObj.getCon();
            HashMap<String,String> result = new HashMap<>();
            result.put("validLogin","false");

            String sql = "SELECT * FROM Users where username= '"+ loginModel.getUserName()+"' and password = '"+loginModel.getPassword()+"'";
            PreparedStatement ps = populateCon.prepareStatement(sql);
            rs = ps.executeQuery(sql);


            if (rs != null) {
                while (rs.next()) {
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
                populateCon.close();
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
        return "success";
    }

}
