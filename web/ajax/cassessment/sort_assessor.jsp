<%@page contentType="text/html"%>
<%@page language="java" import="java.util.ArrayList"%>
<%@page pageEncoding="UTF-8"%>
<%@page import="com.web.jxp.user.UserInfo" %>
<%@page import="com.web.jxp.cassessment.CassessmentInfo" %>
<jsp:useBean id="cassessment" class="com.web.jxp.cassessment.Cassessment" scope="page"/>
<jsp:useBean id="vobj" class="com.web.jxp.base.Validate" scope="page"/>
<%
    try 
    {
        if (session.getAttribute("LOGININFO") != null) 
        {
            if (request.getParameter("col") != null) 
            {
                StringBuilder str = new StringBuilder();
                String colid = request.getParameter("col") != null ? vobj.replaceint(request.getParameter("col")) : "";
                String updowns = request.getParameter("updown") != null && !request.getParameter("updown").equals("") ? vobj.replaceint(request.getParameter("updown")) : "0";
                String nextValue = request.getParameter("nextValue") != null && !request.getParameter("nextValue").equals("") ? vobj.replaceint(request.getParameter("nextValue")) : "0";
                int n = Integer.parseInt(nextValue);
                int updown = Integer.parseInt(updowns);
                int next_value = n;
                n = n - 1;
                session.setAttribute("NEXTVALUEA", next_value + "");

                ArrayList cassessment_list = new ArrayList();
                if (session.getAttribute("ASSESSOR_LIST") != null) {
                    cassessment_list = (ArrayList) session.getAttribute("ASSESSOR_LIST");
                }
                int total = cassessment_list.size();
                if (total > 0) 
                {
                    cassessment_list = cassessment.getFinalRecordAss(cassessment_list, colid, updown);
                    session.setAttribute("ASSESSOR_LIST", cassessment_list);

                    CassessmentInfo info;
                    for (int i = 0; i < total; i++) 
                    {
                        info = (CassessmentInfo) cassessment_list.get(i);
                        if (info != null) 
                        {
                            int status = info.getStatus();
                            String link = ";";
                            if (status == 1) {
                                link = "showAssessorDetail('" + info.getCandidateId() + "','" + info.getCassessmentId() + "');";
                            }
                            str.append("<tr href='javascript: void(0);' >");
                            str.append("<td class='hand_cursor' href='javascript: void(0);' onclick=\"javascript: " + link + ";\">" + (info.getDate() != null && !"".equals(info.getDate()) ? info.getDate() : "") + "</td>");
                            str.append("<td class='hand_cursor' href='javascript: void(0);' onclick=\"javascript: " + link + ";\">" + (info.getTime() != null && !"".equals(info.getTime()) ? info.getTime() : "") + "</td>");
                            str.append("<td class='hand_cursor' href='javascript: void(0);' onclick=\"javascript: " + link + ";\">" + (info.getName() != null && !"".equals(info.getName()) ? info.getName() : "") + "</td>");
                            str.append("<td class='hand_cursor' href='javascript: void(0);' onclick=\"javascript: " + link + ";\">" + (info.getAssessmentName() != null && !"".equals(info.getAssessmentName()) ? info.getAssessmentName() : "") + "</td>");
                            str.append("<td class='hand_cursor' href='javascript: void(0);' onclick=\"javascript: " + link + ";\">" + (info.getAssettypeName() != null && !"".equals(info.getAssettypeName()) ? info.getAssettypeName() : "") + "</td>");
                            str.append("<td class='hand_cursor' href='javascript: void(0);' onclick=\"javascript: " + link + ";\">" + (info.getPosition() != null && !"".equals(info.getPosition()) ? info.getPosition() : "") + "</td>");
                            str.append("<td class='hand_cursor' href='javascript: void(0);' onclick=\"javascript: " + link + ";\">" + (info.getAssStatus() != null && !"".equals(info.getAssStatus()) ? info.getAssStatus() : "") + "</td>");
                            str.append("<td class='hand_cursor text-center' href='javascript: void(0);' onclick=\"javascript: " + link + ";\">" + (info.getMinScore()) + "</td>");
                            str.append("<td class='hand_cursor' href='javascript: void(0);' onclick=\"javascript: " + link + ";\">" + (info.getAssessorName() != null && !"".equals(info.getAssessorName()) ? info.getAssessorName() : "") + "</td>");
                            str.append("<td class='hand_cursor text-center' href='javascript: void(0);' onclick=\"javascript: " + link + ";\">");
                            if (status == 1) {
                                str.append(" - ");
                            } else {
                                str.append("<span ");
                                if (status == 3) {
                                    str.append("class='red_mark' ");
                                }
                                str.append(">" + info.getMarks() + "</span> ");
                            }
                            str.append("</td>");
                            str.append("</tr>");
                        }
                    }
                } else {
                    str.append("<div class='col-lg-12' id='printArea'>");
                    str.append("<div class='table-rep-plugin sort_table'>");
                    str.append("<div class='table-responsive mb-0' data-bs-pattern='priority-columns'>");
                    str.append("<table id='tech-companies-1' class='table table-striped'>");
                    str.append("<tbody>");
                    str.append("<tr>");
                    str.append("<td><b>" + cassessment.getMainPath("record_not_found") + "</b></td>");
                    str.append("</tr>");
                    str.append("</tbody>");
                    str.append("</table>");
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