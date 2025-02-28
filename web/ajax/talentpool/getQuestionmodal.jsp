<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.Collection"%>
<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@page import="com.web.jxp.user.UserInfo" %>
<%@page import="com.web.jxp.talentpool.TalentpoolInfo" %>
<jsp:useBean id="talentpool" class="com.web.jxp.talentpool.Talentpool" scope="page"/>
<jsp:useBean id="vobj" class="com.web.jxp.base.Validate" scope="page"/>
<%
    try 
    {
        if (session.getAttribute("LOGININFO") != null)
        {
            String str = "";
            String surveyIds = request.getParameter("surveyId") != null && !request.getParameter("surveyId").equals("") ? vobj.replaceint(request.getParameter("surveyId")) : "0";
            int surveyId = Integer.parseInt(surveyIds);

            response.setContentType("text/html");
            response.setHeader("Cache-Control", "no-cache");
            ArrayList list = new ArrayList();
            TalentpoolInfo info = talentpool.getSurveyInfo(surveyId);
            String question ="", answer = "", completedon = "", filleddate ="", feedback ="", category ="";
             if(info != null)
             {
                completedon = info.getSubmissionDate()!= null ? info.getSubmissionDate(): "";
                feedback = info.getFeedback()!= null ? info.getFeedback(): "";
                category = info.getCategoryName()!= null ? info.getCategoryName(): "";
                filleddate = info.getFilleddate()!= null ? info.getFilleddate(): "";
                list = talentpool.getQuestionList(surveyId);
             }
            StringBuffer sb = new StringBuffer();
            if (list.size() > 0) 
            {
                sb.append("<div class='col-lg-12'>");
                sb.append("<div class='row flex-center align-items-center mb_10'>");
                sb.append("<div class='col'>");
                sb.append("<h2>"+feedback+" | "+category);
                sb.append(" | "+completedon);
                sb.append("</h2>");
                sb.append("</div>");
                if(info.getStatus() == 2)
                {
                    sb.append("<div class='col col-md-3'>");
                    sb.append("<div class='row flex-center align-items-center'>");
                    sb.append("<div class='col-md-6 text-right field_label'><label>");
                    sb.append("Filled On");
                    sb.append("</label></div>");
                    sb.append("<div class='col-md-6'><span class='form-control'>");
                    sb.append(filleddate);
                    sb.append("</span></div>");
                    sb.append("</div>");

                    sb.append("</div>");
                    sb.append("</div>");
                }
                sb.append("<div class='row client_position_table'>");
                sb.append("<div class='col-md-12 table-rep-plugin sort_table'>");
                sb.append("<div class='table-responsive mb-0'>");
                sb.append("<table id='tech-companies-1' class='table table-striped'>");
                sb.append("<tbody>");
                for(int i = 0; i<list.size();i++)
                {
                    TalentpoolInfo qinfo = (TalentpoolInfo)list.get(i);
                    if(qinfo != null)
                    {
                        question = qinfo.getQuestion()!= null ? qinfo.getQuestion() : "";
                        answer = qinfo.getAnswer()!= null ? qinfo.getAnswer(): "";
                    }
                    sb.append("<tr>");
                    sb.append("<td class='que_ans'>");
                    sb.append("<span class='que_bg'>Q</span>");
                    sb.append(question);
                    sb.append("</td>");
                    sb.append("</tr>");
                    sb.append("<tr>");
                    sb.append("<td class='que_ans'>");
                    sb.append("<span class='ans_bg'>A</span>");
                    sb.append(answer);
                    sb.append("</td>");
                    sb.append("</tr>");
                }                                                   
                sb.append("</tbody>");
                sb.append("</table>");
                sb.append("</div>");	
                sb.append("</div>");
                sb.append("</div>");

                sb.append("</div>");

            } else {
                sb.append("No feedback available.");
            }
            str = sb.toString();
            sb.setLength(0);
            response.getWriter().write(str);
            
        } else {
            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
%>