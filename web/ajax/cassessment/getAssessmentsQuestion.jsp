<%@page language="java"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.web.jxp.user.UserInfo"%>
<%@page import="com.web.jxp.cassessment.CassessmentInfo"%>
<%@taglib uri="/WEB-INF/struts-html-el.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/fmt.tld" prefix="fmt"%>
<%@taglib uri="/WEB-INF/c.tld" prefix="c"%>
<jsp:useBean id="cassessment" class="com.web.jxp.cassessment.Cassessment" scope="page"/>
<jsp:useBean id="vobj" class="com.web.jxp.base.Validate" scope="page"/>
<%
    try 
    {
        if (session.getAttribute("LOGININFO") != null) 
        {
            if (request.getParameter("assessmentId") != null)
            {
                StringBuilder str = new StringBuilder();
                String assessmentIds = request.getParameter("assessmentId") != null && !request.getParameter("assessmentId").equals("") ? vobj.replaceint(request.getParameter("assessmentId")) : "0";
                String parameterIds = request.getParameter("parameterId") != null && !request.getParameter("parameterId").equals("") ? vobj.replaceint(request.getParameter("parameterId")) : "0";
                String title = request.getParameter("title") != null && !request.getParameter("title").equals("") ? vobj.replacename(request.getParameter("title")) : "";
                int assessmentId = Integer.parseInt(assessmentIds);
                int parameterId = Integer.parseInt(parameterIds);
                ArrayList finallist = new ArrayList();
                finallist = cassessment.getAssessmentquestionlistbyparameterid(assessmentId, parameterId);
                int total = finallist.size();                
                if (total > 0) 
                {
                    str.append("<div class='row'>");
                    str.append("<div class='col-lg-12'>");
                    str.append("<h2>" + title + " - QUESTIONS</h2>");
                    str.append("<div class='full_width client_position_table'>");
                    str.append("<div class='table-responsive mb-0'>");
                    str.append("<table id='tech-companies-1' class='table table-striped'>");
                    str.append("<thead>");
                    str.append("<tr>");
                    str.append("<th width='%'><span><b>Text</b> </span></th>");
                    str.append("</tr>");
                    str.append("</thead>");
                    str.append("<tbody>");
                    for (int i = 0; i < total; i++) {
                        String questions = (String) finallist.get(i);
                        str.append("<tr><td>" + questions + "</td></tr>");
                    }
                    str.append("</tbody>");
                    str.append("</table>");
                    str.append("</div>");
                    str.append("</div>");
                    str.append("</div>");
                    str.append("</div>");

                }
                String st = str.toString();
                str.setLength(0);
                response.getWriter().write(st);
            } else {
                response.setStatus(HttpServletResponse.SC_NO_CONTENT);
            }
        } else {
            response.getWriter().write("Please check your login session....");
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
%>