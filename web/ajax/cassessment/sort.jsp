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

                session.setAttribute("NEXTVALUE", next_value + "");
                ArrayList cassessment_list = new ArrayList();
                if (session.getAttribute("CASSESSMENT_LIST") != null) {
                    cassessment_list = (ArrayList) session.getAttribute("CASSESSMENT_LIST");
                }
                int total = cassessment_list.size();
                if (total > 0)
                {
                    cassessment_list = cassessment.getFinalRecord(cassessment_list, colid, updown);
                    session.setAttribute("CASSESSMENT_LIST", cassessment_list);

                    CassessmentInfo info;
                    for (int i = 0; i < total; i++) 
                    {
                        info = (CassessmentInfo) cassessment_list.get(i);
                        if (info != null)
                        {
                            str.append("<tr>");
                            str.append("<td>" + (info.getIds() != null ? info.getIds() : "") + "</td>");
                            str.append("<td>" + (info.getName() != null ? info.getName() : "") + "</td>");
                            str.append("<td>" + (info.getAssettypeName() != null ? info.getAssettypeName() : "") + "</td>");
                            str.append("<td>" + (info.getPosition() != null ? info.getPosition() : "") + "</td>");
                            str.append("<td>" + (info.getVdate() != null ? info.getVdate() : "") + "</td>");
                            str.append("<td>" + (info.getVflagstatus() != null ? info.getVflagstatus() : "") + "</td>");
                            str.append("<td>" + (info.getAflagstatus() != null ? info.getAflagstatus() : "") + "</td>");
                            str.append("<td class='assets_list text-center'>");
                            if (info.getScheduled() != null && !"".equals(info.getScheduled())) {
                                str.append("<a href='javascript: void(0);'>" + info.getScheduled() + "</a>");
                            }
                            str.append("</td>");
                            str.append("<td class='text-center' data-org-colspan='1' data-columns='tech-companies-1-col-8'>");
                            str.append("<div class='main-nav ass_list'>");
                            str.append("<ul>");
                            str.append("<li class='drop_down'>");
                            str.append("<a href='javascript:;' class='toggle'><i class='fas fa-ellipsis-v'></i></a>");
                            str.append("<div class='dropdown_menu'>");
                            str.append("<div class='dropdown-wrapper'>");
                            str.append("<div class='category-menu'>");
                            str.append("<a href=\"javascript: showDetailAssessNow('"+ info.getCassessmentId()+"');\">Assess Now</a>");
                            str.append("<a href=\"javascript: showDetail('"+ info.getCassessmentId()+"');\">Schedule Assessments</a>");
                            str.append("</div>");
                            str.append("</div>");
                            str.append("</div>");
                            str.append("</li>");
                            str.append("</ul>");
                            str.append("</div>");
                            str.append("</td>");
                            str.append("</tr>");
                        }
                    }
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