<%@page import="java.util.LinkedList"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.Collection"%>
<%@page import="org.json.JSONObject"%>
<%@page import="java.io.BufferedReader"%>
<%@page contentType="text/html"%>
<%@page language="java" import="java.util.ArrayList"%>
<%@page import="com.web.jxp.user.UserInfo" %>
<%@page import="com.web.jxp.port.PortInfo" %>  
<jsp:useBean id="port" class="com.web.jxp.port.Port" scope="page"/>
<jsp:useBean id="vobj" class="com.web.jxp.base.Validate" scope="page"/>

<%
    try
    {
        UserInfo uInfo = (UserInfo) request.getSession().getAttribute("LOGININFO");
        String s = "";
        if(uInfo != null)
        {    
            String countryId_s = request.getParameter("countryId") != null && !request.getParameter("countryId").equals("") ? vobj.replaceint(request.getParameter("countryId")) : "0"; 
            int countryId = Integer.parseInt(countryId_s); 
            Collection coll = port.getPortbycountries(countryId);
            StringBuffer sb = new StringBuffer();    
            if(coll.size() > 0)
            {
                Iterator iter = coll.iterator();
                while (iter.hasNext())
                {
                        PortInfo  rinfo = (PortInfo) iter.next();
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