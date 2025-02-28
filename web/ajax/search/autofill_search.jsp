<%@page import="org.json.JSONObject"%>
<%@page import="java.io.BufferedReader"%>
<%@page contentType="text/html"%>
<%@page language="java" import="java.util.ArrayList"%>
<jsp:useBean id="obj" class="com.web.jxp.candidate.Autofill" scope="page"/>
<%
    try
    {
        if(request.getSession().getAttribute("LOGININFO") != null)
        {
            StringBuilder sb = new StringBuilder();
            BufferedReader reader = request.getReader();
            String line;
            while ((line = reader.readLine()) != null)
            {
                sb.append(line);
            }
            String data = sb.toString();
            sb.setLength(0);
            JSONObject jsonObj = new JSONObject(data);
            String name = jsonObj.optString("search");   
            if(name != null && name.length() > 0)
            {            
                String s = obj.getSearchAutoFill(name);
                response.getWriter().write(s);
            }
        }
    }
    catch(Exception e)
    {
        e.printStackTrace();
    }
%>