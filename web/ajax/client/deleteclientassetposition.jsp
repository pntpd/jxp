<%@page import="com.web.jxp.relation.RelationInfo"%>
<%@page import="com.web.jxp.experiencedept.ExperienceDeptInfo"%>
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
<jsp:useBean id="relation" class="com.web.jxp.relation.Relation" scope="page"/>
<jsp:useBean id="experiencedept" class="com.web.jxp.experiencedept.ExperienceDept" scope="page"/>
<jsp:useBean id="vobj" class="com.web.jxp.base.Validate" scope="page"/>
<%
    try
    {
        UserInfo uInfo = (UserInfo) request.getSession().getAttribute("LOGININFO");
        if(uInfo != null)
        {    
            int userId = uInfo.getUserId();
            String clientassetpositionIds = request.getParameter("clientassetpositionId") != null && !request.getParameter("clientassetpositionId").equals("") ? vobj.replaceint(request.getParameter("clientassetpositionId")) : "0"; 
            int clientassetpositionId = Integer.parseInt(clientassetpositionIds);             
            String s = "";
            if(clientassetpositionId > 0 )
            {  
                int cc = client.deleteClientAssetposition(clientassetpositionId);
               
                if(cc>0)
                {
                    s = "yes";
                }
                else
                {
                    s= "no";
                }
                   
                }
                response.getWriter().write(s);
        }
    }
    catch(Exception e)
    {
        e.printStackTrace();
    }
%>