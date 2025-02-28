<%@page contentType="text/html"%>
<%@page language="java" import="java.util.ArrayList"%>
<%@page import="com.web.jxp.user.UserInfo" %>
<%@page import="com.web.jxp.client.ClientInfo" %>
<jsp:useBean id="client" class="com.web.jxp.client.Client" scope="page"/>
<jsp:useBean id="vobj" class="com.web.jxp.base.Validate" scope="page"/>
<%
    try 
    {
        UserInfo uInfo = (UserInfo) request.getSession().getAttribute("LOGININFO");
        if (uInfo != null) 
        {
            int userId = uInfo.getUserId();
            if (userId > 0) 
            {
                String clientassetIds = request.getParameter("clientassetId") != null ? vobj.replaceint(request.getParameter("clientassetId")) : "0";
                String deptIds = request.getParameter("deptId") != null ? vobj.replaceint(request.getParameter("deptId")) : "0";   
                int clientassetId = Integer.parseInt(clientassetIds);
                int deptId = Integer.parseInt(deptIds);
                int cc = client.updateDept(deptId, clientassetId, userId); 
                if(cc > 0)
                    response.getWriter().write("Yes");
                else
                    response.getWriter().write("No");
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
%>