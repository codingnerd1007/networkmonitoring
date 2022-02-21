package com.motadata.traceorg.action;


import com.motadata.traceorg.dao.LoginModel;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.interceptor.Interceptor;
import org.apache.struts2.StrutsStatics;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class LoginInterceptor implements Interceptor
{
//    private final String[] _allowedURLs = {"checkPlugin","selectcategory","fetchConstraintName","addConstraintName","selectSubTiles","getAllDetail","getPluginType","getAllDteailsOFPlugins","showPluginVersionDetails","getAllUserPluginDetails","login.jsp","login","logout"};
    private final String[] _allowedURLs={"/loginValidation"};
    public void destroy() {

    }

    public void init() {

    }

    public String intercept(ActionInvocation invocation) throws Exception {

        final ActionContext context = invocation.getInvocationContext();

        HttpServletRequest request = (HttpServletRequest) context.get(StrutsStatics.HTTP_REQUEST);

        Map<String, Object> session = ActionContext.getContext().getSession();

        LoginModel user = (LoginModel) session.get("user-session");

        boolean userActionQualified = false;

        if (user != null)
        {
            invocation.invoke();

            userActionQualified = true;
        }
        else
        {
            String requestURL = request.getRequestURI();

            for (String url: _allowedURLs)
            {
                if(requestURL.equals(url))
                {
                    invocation.invoke();

                    userActionQualified = true;
                }
            }


        }

        if(!userActionQualified)
        {
            return ActionSupport.LOGIN;
        }

        return ActionSupport.SUCCESS;
    }
}
