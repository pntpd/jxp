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
<jsp:useBean id="assessment" class="com.web.jxp.assessment.Assessment" scope="page"/>
<jsp:useBean id="vobj" class="com.web.jxp.base.Validate" scope="page"/>
<%
    try
    {
        UserInfo uInfo = (UserInfo) request.getSession().getAttribute("LOGININFO");
        if(uInfo != null)
        {    
            int userId = uInfo.getUserId();
            String question = request.getParameter("question") != null ? vobj.replacedesc(request.getParameter("question")) : "";
            String answerIds = request.getParameter("answerId") != null ? vobj.replaceint(request.getParameter("answerId")) : ""; 
            String parameterIds = request.getParameter("parameterId") != null ? vobj.replaceint(request.getParameter("parameterId")) : ""; 
            String externallink = request.getParameter("externallink") != null && !request.getParameter("externallink").equals("") ? vobj.replacedesc(request.getParameter("externallink")) : ""; 
            int answerId = Integer.parseInt(answerIds);
            int parameterId = Integer.parseInt(parameterIds);
            String s = "";
            int cc = assessment.createquestionMaster(question, answerId,parameterId,externallink,userId); 
            if(cc== -2)
                s = "Data already exist.";
            else if(cc == 0)
                s = "Something went wrong.";
            else
                s = "Yes";
            response.getWriter().write(s);
        }
    }
    catch(Exception e)
    {
        e.printStackTrace();
    }
%>