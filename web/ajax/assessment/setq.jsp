<%@page import="java.util.TreeSet"%>
<%@page import="java.util.SortedSet"%>
<%@page import="java.util.LinkedList"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.Collection"%>
<%@page import="org.json.JSONObject"%>
<%@page import="java.io.BufferedReader"%>
<%@page contentType="text/html"%>
<%@page language="java" import="java.util.ArrayList"%>
<%@page import="com.web.jxp.user.UserInfo" %>
<%@page import="com.web.jxp.assessment.AssessmentInfo" %>
<%@page import="com.web.jxp.assessmentparameter.AssessmentParameterInfo" %>
<jsp:useBean id="assessmentparameter" class="com.web.jxp.assessmentparameter.AssessmentParameter" scope="page"/>
<jsp:useBean id="vobj" class="com.web.jxp.base.Validate" scope="page"/>
<%
    try
    {
        UserInfo uInfo = (UserInfo) request.getSession().getAttribute("LOGININFO");
        if(uInfo != null)
        {    
            int userId = uInfo.getUserId();
            if(userId > 0)
            {
                String type_s = request.getParameter("type") != null && !request.getParameter("type").equals("") ? vobj.replaceint(request.getParameter("type")) : "0";
                String qid_s = request.getParameter("qid") != null && !request.getParameter("qid").equals("") ? vobj.replaceint(request.getParameter("qid")) : "0"; 
                int type = Integer.parseInt(type_s);
                int qid = Integer.parseInt(qid_s);
                SortedSet<Integer> ss = new TreeSet<Integer>(); 
                if(session.getAttribute("QIDLIST") != null)
                    ss = (SortedSet<Integer>) session.getAttribute("QIDLIST");
                if(type == 1)
                    ss.add(qid);
                else if(type == 2)
                    ss.remove(qid);
              
                session.setAttribute("QIDLIST", ss);
                response.getWriter().write("Yes");
            }
        }
    }
    catch(Exception e)
    {
        e.printStackTrace();
    }
%>