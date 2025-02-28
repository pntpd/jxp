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
                String candidateIds = request.getParameter("candidateId") != null && !request.getParameter("candidateId").equals("") ? vobj.replaceint(request.getParameter("candidateId")) : "0";
                int candidateId = Integer.parseInt(candidateIds);

                response.setContentType("text/html");
                response.setHeader("Cache-Control", "no-cache");
                TalentpoolInfo info = talentpool.getAssessnowinfo(candidateId) ;
              
                StringBuffer sb = new StringBuffer();
                if (info != null) 
                {
                    sb.append("<div class='table-responsive table-detail'>");
                    sb.append("<table class='table table-striped1 table-bordered'>");
                    sb.append("<thead>");
                    sb.append("<tr>");
                    sb.append("<th width='%' class='head_bg'>Observations:</th>");
                    sb.append("<th width='%' class='text-center head_bg'>Rating</th>");	
                    sb.append("</tr>");
                    sb.append("</thead>");
                    sb.append("<tbody>");
                    sb.append("<tr class='title_head_bg'>");
                    sb.append("<td colspan='2'>Appearance:</td>");
                    sb.append("</tr>");
                    sb.append("<tr>");
                    sb.append("<td width='85%'>Dress up / presentation</td>");
                    sb.append("<td width='15%' class='text-center'><b>"+info.getRadio1()+"</b></td>");
                    sb.append("</tr>");
                    sb.append("<tr>");
                    sb.append("<td>Composure</td>");
                    sb.append("<td class='text-center'><b>"+info.getRadio2()+"</b></td>");
                    sb.append("</tr>");
                    sb.append("</tbody>");

                    sb.append("<tbody>");
                    sb.append("<tr class='title_head_bg'>");
                    sb.append("<td colspan='2'>Observable Traits:</td>");
                    sb.append("</tr>");
                    sb.append("<tr>");
                    sb.append("<td>Attitude</td>");
                    sb.append("<td class='text-center'><b>"+info.getRadio3()+"</b></td>");
                    sb.append("</tr>");
                    sb.append("<tr>");
                    sb.append("<td>Motivation</td>");
                    sb.append("<td class='text-center'><b>"+info.getRadio4()+"</b></td>");
                    sb.append("</tr>");
                    sb.append("<tr>");
                    sb.append("<td>Communication</td>");
                    sb.append("<td class='text-center'><b>"+info.getRadio5()+"</b></td>");
                    sb.append("</tr>");
                    sb.append("<tr>");
                    sb.append("<td>Assertiveness</td>");
                    sb.append("<td class='text-center'><b>"+info.getRadio6()+"</b></td>");
                    sb.append("</tr>");
                    sb.append("<tr>");
                    sb.append("<td>Verbal / Persuasiveness</td>");
                    sb.append("<td class='text-center'><b>"+info.getRadio7()+"</b></td>");
                    sb.append("</tr>");
                    sb.append("</tbody>");

                    sb.append("<tbody>");
                    sb.append("<tr class='title_head_bg'>");
                    sb.append("<td colspan='2'>Skills:</td>");
                    sb.append("</tr>");
                    sb.append("<tr>");
                    sb.append("<td>Education/Professional</td>");
                    sb.append("<td class='text-center'><b>"+info.getRadio8()+"</b></td>");
                    sb.append("</tr>");
                    sb.append("<tr>");
                    sb.append("<td>Relevant Experience</td>");
                    sb.append("<td class='text-center'><b>"+info.getRadio9()+"</b></td>");
                    sb.append("</tr>");
                    sb.append("<tr>");
                    sb.append("<td>Digital Proficiency and the ability to work with multiple technologies</td>");
                    sb.append("<td class='text-center'><b>"+info.getRadio10()+"</b></td>");
                    sb.append("</tr>");
                    sb.append("<tr>");
                    sb.append("<td>Technical Skills (if required)</td>");
                    sb.append("<td class='text-center'><b>"+info.getRadio11()+"</b></td>");
                    sb.append("</tr>");
                    sb.append("</tbody>");

                    sb.append("<tbody>");
                    sb.append("<tr class='title_head_bg'>");
                    sb.append("<td colspan='2'>Competencies:</td>");
                    sb.append("</tr>");
                    sb.append("<tr>");
                    sb.append("<td>Business Knowledge / knowledge of field</td>");
                    sb.append("<td class='text-center'><b>"+info.getRadio12()+"</b></td>");
                    sb.append("</tr>");
                    sb.append("<tr>");
                    sb.append("<td>Job Knowledge</td>");
                    sb.append("<td class='text-center'><b>"+info.getRadio13()+"</b></td>");
                    sb.append("</tr>");
                    sb.append("<tr>");
                    sb.append("<td>Clarity of thought</td>");
                    sb.append("<td class='text-center'><b>"+info.getRadio14()+"</b></td>");
                    sb.append("</tr>");
                    sb.append("<tr>");
                    sb.append("<td>Understands questions and answers to the point</td>");
                    sb.append("<td class='text-center'><b>"+info.getRadio15()+"</b></td>");
                    sb.append("</tr>");
                    sb.append("<tr>");
                    sb.append("<td>Logic</td>");
                    sb.append("<td class='text-center'><b>"+info.getRadio6()+"</b></td>");
                    sb.append("</tr>");
                    sb.append("</tbody>");

                    sb.append("<tbody>");
                    sb.append("<tr>");
                    sb.append("<td colspan='2' class='title_head_bg'>Overall Rating:</td>");
                    sb.append("</tr>");
                    sb.append("<tr>");
                    sb.append("<td colspan='2' class='pd_0 over_final'>");
                    sb.append("<table width='100%'>");
                    sb.append("<tbody>");
                    sb.append("<tr>");
                    sb.append("<td><label>"+talentpool.getAssessnowoverallratingbyId(info.getRadio17())+"</label></td>");
                    sb.append("</tr>");
                    sb.append("</tbody>");
                    sb.append("</table>");
                    sb.append("</td>");
                    sb.append("</tr>");
                    sb.append("</tbody>");
                    sb.append("</table>");		
                    sb.append("</div>");

                    sb.append("<div class='table-responsive table-detail'>");
                    sb.append("<table class='table table-striped1 table-bordered'>");
                    sb.append("<thead>");
                    sb.append("<tr><th width='%' colspan='2' class='head_bg'>Additional Comments:</th></tr>");
                    sb.append("</thead>");
                    sb.append("<tbody>");
                    sb.append("<tr>");
                    sb.append("<td>");
                    sb.append(info.getCdescription());
                    sb.append("</td>");
                    sb.append("</tr>");
                    sb.append("</tbody>");
                    sb.append("</table>");
                    sb.append("</div>");

                    sb.append("<div class='table-responsive table-detail'>");
                    sb.append("<table class='table table-striped1 table-bordered'>");
                    sb.append("<thead>");
                    sb.append("<tr><th width='%' class='head_bg'>Final Action:</th></tr>");
                    sb.append("</thead>");
                    sb.append("<tbody>");
                    sb.append("<tr>");
                    sb.append("<td><label>"+talentpool.getAssessnowtatusbyId(info.getRadio18())+"</label></td>");
                    sb.append("</tr>");
                    sb.append("</tbody>");
                    sb.append("</table>");
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