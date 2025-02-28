<%@page import="java.util.LinkedList"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.Collection"%>
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
        UserInfo uInfo = (UserInfo) request.getSession().getAttribute("LOGININFO");
        String s = "";
        if(uInfo != null)
        {    
            Collection coll = client.getUsers();
            StringBuffer sb = new StringBuffer();    
            if(coll.size() > 0)
            {
                Iterator iter = coll.iterator();
                while (iter.hasNext())
                {
                    ClientInfo  rinfo = (ClientInfo) iter.next();
                    int val =  rinfo.getDdlValue();
                    String n = rinfo.getDdlLabel()!= null ? rinfo.getDdlLabel(): "";
                    sb.append("<option value='"+val+"'>"+n+"</option>");
                }
            }
            s = sb.toString();
            sb.setLength(0);
            coll.clear();
        }
            response.getWriter().write(s);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
%>