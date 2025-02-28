<%@page contentType="text/html"%>
<%@page language="java" import="java.util.ArrayList"%>
<%@page pageEncoding="UTF-8"%>
<%@page import="com.web.jxp.user.UserInfo" %>
<%@page import="com.web.jxp.talentpool.TalentpoolInfo" %>
<jsp:useBean id="ddl" class="com.web.jxp.talentpool.Ddl" scope="page"/>
<jsp:useBean id="vobj" class="com.web.jxp.base.Validate" scope="page"/>
<%
    try 
    {
        if (session.getAttribute("LOGININFO") != null) 
        {
            if (request.getParameter("appraisalId") != null) 
            {
                StringBuilder sb = new StringBuilder();
                String appraisalId_s = request.getParameter("appraisalId") != null ? vobj.replaceint(request.getParameter("appraisalId")) : "0";
                int appraisalId = Integer.parseInt(appraisalId_s);
                if (appraisalId > 0)
                {
                    ArrayList list = ddl.getAppraisalHistory(appraisalId);   
                    int size = list.size();
                    if (size > 0) 
                    {
                        sb.append("<div class='full_width client_position_table' id='tds'>");
                        sb.append("<div class='table-responsive1 mb-0'>");
                            
                        sb.append("<table class='table table-striped'>");
                        sb.append("<thead>");
                        sb.append("<tr>");
                        sb.append("<th width='%'><span><b>Old <br/>Position</b> </span></th>");
                        sb.append("<th width='%'><span><b>New <br/>Position</b> </span></th>");
                        sb.append("<th width='%'><span><b>New Day <br/>Rate</b> </span></th>");
                        sb.append("<th width='%'><span><b>New Overtime <br/>Rate</b> </span></th>");
                        sb.append("<th width='%'><span><b>New <br/>Allowance</b> </span></th>");
                        sb.append("<th width='%'><span><b>Effective <br/>Date</b> </span></th>");
                        sb.append("<th width='%'><span><b>Effective <br/>To Date</b> </span></th>");
                        sb.append("</tr>");
                        sb.append("</thead>");
                        sb.append("<tbody>");
                        
                        for (int i = 0; i < size; i++) 
                        {
                            TalentpoolInfo info = (TalentpoolInfo) list.get(i);
                            if (info != null) 
                            {
                                sb.append("<tr>"); 
                                    sb.append("<td>" + (info.getPosition() != null ? info.getPosition(): "")+ "</td>");
                                    sb.append("<td>" + (info.getPosition2() != null ? info.getPosition2(): "")+ "</td>");
                                    sb.append("<td>" + info.getRate1()+ "</td>");
                                    sb.append("<td>" + info.getRate2()+ "</td>");
                                    sb.append("<td>" + info.getRate3()+ "</td>");
                                    sb.append("<td>" + (info.getFromDate() != null ? info.getFromDate(): "") + "</td>");
                                    sb.append("<td>" + (info.getToDate() != null && !info.getToDate().equals("") ? info.getToDate(): "Active") + "</td>");
                                sb.append("</tr>");       
                            }
                        }
                        sb.append("</tbody>");
                        sb.append("</table>");
                        sb.append("</div>");
                        sb.append("</div>");
                    } 
                    else {
                        sb.append("<div>No records available.</div></div>");
                    }
                    String st1 = sb.toString();
                    sb.setLength(0);
                    response.getWriter().write(st1);
                } else {
                    response.getWriter().write("Something went wrong.");
                }
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