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
<%@page import="com.web.jxp.assessmentquestion.AssessmentQuestionInfo" %>
<jsp:useBean id="assessment" class="com.web.jxp.assessment.Assessment" scope="page"/>
<jsp:useBean id="vobj" class="com.web.jxp.base.Validate" scope="page"/>
<%
    try 
    {
        UserInfo uInfo = (UserInfo) request.getSession().getAttribute("LOGININFO");
        if (uInfo != null) 
        {
            int userId = uInfo.getUserId();
            if (userId > 0) 
            {
                String assessmentparameterids = request.getParameter("assessmentparameterid") != null ? vobj.replaceint(request.getParameter("assessmentparameterid")) : "0";
                String assessmentids = request.getParameter("assessmentid") != null ? vobj.replaceint(request.getParameter("assessmentid")) : "0";
                String s = "";
                int apid = Integer.parseInt(assessmentparameterids);
                int aid = Integer.parseInt(assessmentids);
                Collection coll = assessment.getAssessmentQuestionsparameterview(apid, aid);
                StringBuffer sb = new StringBuffer();
                int size = coll.size();
                if (size > 0) 
                {
                    Iterator iter = coll.iterator();
                    while (iter.hasNext()) 
                    {
                        AssessmentQuestionInfo info = (AssessmentQuestionInfo) iter.next();
                        int val = info.getDdlValue();
                        String name = info.getDdlLabel() != null ? info.getDdlLabel() : "";
                        sb.append("<tr>");
                        sb.append("<td>");
                        sb.append("<span class='ml_10'>" + name + "</span>");
                        sb.append("</td>");
                        sb.append("<td class='action_column'>");
                        sb.append("<a data-bs-toggle = 'modal' data-bs-target = '#par_que_modal' class='mr_15' onclick = \"javascript: setcheckedquestionparametersViewdetail('"+val+"');\"> <img src = '../assets/images/pencil.png'></a>");
                        sb.append("</td>");
                        sb.append("</tr>");
                    }
                }
                s = sb.toString();
                sb.setLength(0);
                coll.clear();
                response.getWriter().write(s);
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
%>