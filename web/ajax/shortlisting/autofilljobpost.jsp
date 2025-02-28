<%@page import="org.json.JSONObject"%>
<%@page import="java.io.BufferedReader"%>
<%@page contentType="text/html"%>
<%@page language="java" import="java.util.ArrayList"%>
<%@page import="com.web.jxp.user.UserInfo" %>
<%@page import="com.web.jxp.shortlisting.ShortlistingInfo" %>  
<jsp:useBean id="shortlisting" class="com.web.jxp.shortlisting.Shortlisting" scope="page"/>
<%
    try
    {
        UserInfo uInfo = (UserInfo) request.getSession().getAttribute("LOGININFO");
        int allclient = 0;
        String permission = "N", cids = "", assetids = "";
        if (uInfo != null) 
        {
            permission = uInfo.getPermission();
            cids = uInfo.getCids();
            allclient = uInfo.getAllclient();
            assetids = uInfo.getAssetids();

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
            if (name != null && name.length() > 0) 
            {
                String s = shortlisting.getShortlistingJobPost(name,allclient, permission, cids, assetids);
                response.getWriter().write(s);
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
%>