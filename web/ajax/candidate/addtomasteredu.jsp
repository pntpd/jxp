<%@page contentType="text/html"%>
<%@page language="java"%>
<%@page import="com.web.jxp.user.UserInfo" %>
<jsp:useBean id="city" class="com.web.jxp.city.City" scope="page"/>
<jsp:useBean id="vobj" class="com.web.jxp.base.Validate" scope="page"/>
<%
    try
    {
        UserInfo uInfo = (UserInfo) request.getSession().getAttribute("LOGININFO");
        if(uInfo != null)
        {
            int userId = uInfo.getUserId();
            String name = request.getParameter("name") != null ? vobj.replacename(request.getParameter("name")) : ""; 
            String countryId_s = request.getParameter("countryId") != null && !request.getParameter("countryId").equals("") ? vobj.replaceint(request.getParameter("countryId")) : "0"; 
            int countryId = Integer.parseInt(countryId_s); 
            if(countryId > 0 && !name.equals(""))
            {  
                String s = "";
                int cc = city.createCity(countryId, name, userId); 
                if(cc == -2)
                    s = "No";
                response.getWriter().write(s);
            }
        }
    }
    catch(Exception e)
    {
        e.printStackTrace();
    }
%>