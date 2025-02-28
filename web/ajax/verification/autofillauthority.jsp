<%@page import="org.json.JSONObject"%>
<%@page import="java.io.BufferedReader"%>
<%@page contentType="text/html"%>
<%@page language="java" import="java.util.ArrayList"%>
<%@page import="com.web.jxp.user.UserInfo" %>
<jsp:useBean id="verification" class="com.web.jxp.verification.Verification" scope="page"/>
<%
    try {
        UserInfo uInfo = (UserInfo) request.getSession().getAttribute("LOGININFO");
        if (uInfo != null) {
            StringBuilder sb = new StringBuilder();
            BufferedReader reader = request.getReader();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            String data = sb.toString();
            sb.setLength(0);
            JSONObject jsonObj = new JSONObject(data);
            String verificationauthority = jsonObj.optString("search");
           
            if (verificationauthority != null && verificationauthority.length() > 0) {
                String s = verification.getAuthority(verificationauthority);
                response.getWriter().write(s);
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
%>