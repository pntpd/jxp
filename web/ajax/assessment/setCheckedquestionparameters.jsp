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
<jsp:useBean id="assessmentquestion" class="com.web.jxp.assessmentquestion.AssessmentQuestion" scope="page"/>
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
                SortedSet<Integer> ss = new TreeSet<Integer>();
                if (session.getAttribute("QIDLIST") != null) 
                {
                    ss = (SortedSet<Integer>) session.getAttribute("QIDLIST");
                }
                String ids = request.getParameter("assessmentparameterid") != null ? vobj.replaceint(request.getParameter("assessmentparameterid")) : "0";
                String s = "";
                int id = Integer.parseInt(ids);
                Collection coll = assessmentquestion.getAssessmentQuestionsparameter(id);
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
                        sb.append("<div class='form-check permission-check'>");
                        if (ss.contains(val)) {
                            sb.append("<input class='form-check-input' type='checkbox' name='assquestionparameter' id='assquestionparameter_" + val + "' value='" + val + "' checked onchange = \"javascript: setqid('assquestionparameter_" + val + "');\"/>");
                        } else {
                            sb.append("<input class='form-check-input' type='checkbox' name='assquestionparameter' id='assquestionparameter_" + val + "' value='" + val + "' onchange = \"javascript: setqid('assquestionparameter_" + val + "');\"/>");
                        }
                        sb.append("<span class='ml_10'>" + name + "</span>");
                        sb.append("</div>");
                        sb.append("</td>");
                        sb.append("<td class='action_column'>");
                        sb.append("<a data-bs-toggle = 'modal' data-bs-target = '#view_par_que_modal' class='mr_15' onclick = \"javascript: setcheckedquestionparametersViewdetail('"+val+"');\">");
                        sb.append("<img src = '../assets/images/pencil.png' > </a>");
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