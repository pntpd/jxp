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
                String assessmentids = request.getParameter("assessmentquestionid") != null ? vobj.replaceint(request.getParameter("assessmentquestionid")) : "0";
                String s = "";
                int aid = Integer.parseInt(assessmentids);
                AssessmentQuestionInfo info = assessment.getAssessmentQuestionviewdetail(aid);
                String questionName = info.getName() != null && !info.getName().equals("") ? info.getName() : "&nbsp;";
                String questionlink = info.getLink() != null && !info.getLink().equals("") ? info.getLink() : "&nbsp;";
                String answerName = info.getAssessmentAnswerTypeName() != null && !info.getAssessmentAnswerTypeName().equals("") ? info.getAssessmentAnswerTypeName() : "&nbsp;";
                String assessmentParametername = info.getAssessmentParameterName() != null && !info.getAssessmentParameterName().equals("") ? info.getAssessmentParameterName() : "&nbsp;";
                StringBuffer sb = new StringBuffer();
                sb.append("<div class='col-lg-12 col-md-12 col-sm-12 col-4 form_group'>");
                sb.append("<label class='form_label'>Question Text</label>");
                sb.append("<span class='form-control'>"+questionName+"</span>");
                sb.append("</div>");
                sb.append("<div class='col-lg-12 col-md-12 col-sm-12 col-4 form_group'>");
                sb.append("<label class='form_label'>Answer Type</label>");
                sb.append("<span class='form-control'>"+answerName+"</span>");
                sb.append("</div>");
                sb.append("<div class='col-lg-12 col-md-12 col-sm-12 col-4 form_group'>");
                sb.append("<label class='form_label'>Parameter</label>");
                sb.append("<span class='form-control'>"+assessmentParametername+"</span>");
                sb.append("</div>");
                sb.append("<div class='col-lg-12 col-md-12 col-sm-12 col-4 form_group'>");
                sb.append("<label class='form_label'>Attach External Link</label>");
                sb.append("<span class='form-control'>"+questionlink+"</span>");
                sb.append("</div>");
                s = sb.toString();
                sb.setLength(0);
                response.getWriter().write(s);
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
%>