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
                String positionIds = request.getParameter("positionId") != null ? vobj.replaceint(request.getParameter("positionId")) : "0";
                String setvals = request.getParameter("setval") != null ? vobj.replaceint(request.getParameter("setval")) : "0";
                int clientassetId = Integer.parseInt(clientassetIds);
                int deptId = Integer.parseInt(deptIds);
                int setval = Integer.parseInt(setvals);
                int cc = 0;
                int clientassetpositionId = Integer.parseInt(positionIds);
                if(setval > 0)
                    cc = client.updatePosition(clientassetId, deptId, clientassetpositionId, userId, setval); 
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