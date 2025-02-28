<%@page import="org.json.JSONObject"%>
<%@page import="java.io.BufferedReader"%>
<%@page contentType="text/html"%>
<%@page language="java" import="java.util.ArrayList"%>
<%@page import="com.web.jxp.user.UserInfo" %>
<%@page import="com.web.jxp.client.ClientInfo" %>  
<jsp:useBean id="client" class="com.web.jxp.client.Client" scope="page"/>
<%
    try
    {
        String homeEmailId = (String) request.getSession().getAttribute("HOME_EMAILID");
        if(homeEmailId != null)
        {
            StringBuilder sb = new StringBuilder();
            BufferedReader reader = request.getReader();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            String data = sb.toString();
            sb.setLength(0);
            JSONObject jsonObj = new JSONObject(data);
            String cityName = jsonObj.optString("search"); 
            if(cityName != null && cityName.length() > 0)
            {            
                String s = client.getCityAutoFillVaccination(cityName);
                response.getWriter().write(s);
            }
        }
    }
    catch(Exception e)
    {
        e.printStackTrace();
    }
%>