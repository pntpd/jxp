<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.Collection"%>
<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@page import="com.web.jxp.user.UserInfo" %>
<%@page import="com.web.jxp.managewellness.ManagewellnessInfo" %>
<jsp:useBean id="managewellness" class="com.web.jxp.managewellness.Managewellness" scope="page"/>
<jsp:useBean id="vobj" class="com.web.jxp.base.Validate" scope="page"/>
<%
    try 
    {
        if (session.getAttribute("LOGININFO") != null) 
        {
            String str = "";
            if (request.getParameter("crewrotationId") != null) 
            {
                String assetIdIndexs = request.getParameter("assetIdIndex") != null && !request.getParameter("assetIdIndex").equals("") ? vobj.replaceint(request.getParameter("assetIdIndex")) : "";
                int assetIdIndex = Integer.parseInt(assetIdIndexs);
                String subcategoryIds = request.getParameter("subcategoryId") != null && !request.getParameter("subcategoryId").equals("") ? vobj.replaceint(request.getParameter("subcategoryId")) : "";
                int subcategoryId = Integer.parseInt(subcategoryIds);
                String positionIds = request.getParameter("positionId") != null && !request.getParameter("positionId").equals("") ? vobj.replaceint(request.getParameter("positionId")) : "";
                int positionId = Integer.parseInt(positionIds);
                String crewrotationIds = request.getParameter("crewrotationId") != null && !request.getParameter("crewrotationId").equals("") ? vobj.replaceint(request.getParameter("crewrotationId")) : "";
                int crewrotationId = Integer.parseInt(crewrotationIds);
                response.setContentType("text/html");
                response.setHeader("Cache-Control", "no-cache");
                ArrayList list = new ArrayList();
                 ManagewellnessInfo info = managewellness.getSurveyInfo(crewrotationId, assetIdIndex, subcategoryId, positionId);
                 if(info != null)
                 {
                    list = managewellness.getQuestionList( assetIdIndex, subcategoryId, positionId, info.getSurveyId(), info.getStatus());
                 }
                StringBuffer sb = new StringBuffer();
                if (list.size() > 0) 
                {
                    sb.append("<div class='col-lg-12'>");
                    sb.append("<div class='row flex-center align-items-center mb_10'>");
                    sb.append("<div class='col'>");
                    sb.append("<h2>"+info.getSubcategoryName()+" | "+info.getCategoryName()+" | "+info.getDate());
                    sb.append("</h2>");
                    sb.append("</div>");
                    if(info.getStatus() == 2)
                    {
                        sb.append("<div class='col col-md-4'>");
                        sb.append("<div class='row flex-center align-items-center'>");
                        sb.append("<div class='col-md-6 text-right field_label'><label>");
                        sb.append("Filled On");
                        sb.append("</label></div>");
                        sb.append("<div class='col-md-6'><span class='form-control'>");
                        sb.append(info.getCompletedondate());
                        sb.append("</span></div>");
                        sb.append("</div>");

                        sb.append("</div>");
                    }
                    sb.append("</div>");

                    sb.append("<div class='row client_position_table'>");
                    sb.append("<div class='col-md-12 table-rep-plugin sort_table1'>");
                    sb.append("<div class='table-responsive1 mb-0'>");
                    sb.append("<table  class='table table-striped'>");
                    sb.append("<tbody>");
                    for(int i = 0; i<list.size();i++)
                    {
                        ManagewellnessInfo qinfo = (ManagewellnessInfo)list.get(i);
                        sb.append("<tr>");
                        sb.append("<td class='que_ans'>");
                        sb.append("<span class='que_bg'>Q</span>");
                        sb.append(qinfo.getQuestion());
                        sb.append("</td>");
                        sb.append("</tr>");
                        sb.append("<tr>");
                        sb.append("<td class='que_ans'>");
                        sb.append("<span class='ans_bg'>R</span>");
                        sb.append(!qinfo.getAnswer().equals("") ? qinfo.getAnswer() : "&nbsp;");
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
        } else {
            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
        }
    } catch (Exception e) {

    }
%>