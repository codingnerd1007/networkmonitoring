package com.motadata.traceorg.action;

import com.motadata.traceorg.dao.LoginModel;
import com.motadata.traceorg.executor.UserValidationExecutor;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import org.apache.struts2.interceptor.SessionAware;

import java.util.Map;

public class UserValidation extends ActionSupport implements SessionAware, ModelDriven<LoginModel> {

    private Map session;

    private static LoginModel loginModel = new LoginModel();

    public String toLogin() throws Exception{

        UserValidationExecutor userValidationExecutorObj=new UserValidationExecutor();

        return userValidationExecutorObj.checkCredentials(loginModel,session);
    }

    public String toLogout() throws Exception {
        UserValidationExecutor userValidationExecutorObj1=new UserValidationExecutor();
        return userValidationExecutorObj1.removeSession(loginModel,session);
    }

    @Override
    public void setSession(Map<String, Object> map) {
        this.session=map;
    }


    @Override
    public LoginModel getModel() {
        return loginModel;
    }
}
