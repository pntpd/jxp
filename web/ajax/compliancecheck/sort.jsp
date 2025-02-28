<%@page contentType="text/html"%>
<%@page language="java" import="java.util.ArrayList"%>
<%@page pageEncoding="UTF-8"%>
<%@page import="com.web.jxp.user.UserInfo" %>
<%@page import="com.web.jxp.compliancecheck.CompliancecheckInfo" %>
<jsp:useBean id="compliancecheck" class="com.web.jxp.compliancecheck.Compliancecheck" scope="page"/>
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
                ArrayList candidate_list = new ArrayList();
                if (session.getAttribute("COMPLANCE_LIST") != null) {
                    candidate_list = (ArrayList) session.getAttribute("COMPLANCE_LIST");
                }
                int total = candidate_list.size();
                if (total > 0) 
                {
                    candidate_list = compliancecheck.getFinalRecord(candidate_list, colid, updown);
                    session.setAttribute("COMPLANCE_LIST", candidate_list);

                    CompliancecheckInfo info;
                    for (int i = 0; i < total; i++) 
                    {
                        info = (CompliancecheckInfo) candidate_list.get(i);
                        if (info != null) 
                        {
                            str.append("<tr class='hand_cursor' href='javascript: void(0);' \">");
                            str.append("<td class='hand_cursor' href='javascript: void(0);' onclick=\"javascript: view('" + info.getCandidateId() +"','"+ info.getJobpostId()+"','"+info.getShortlistId()+"','"+ info.getStatus()+ "');\">" + (compliancecheck.changeNum(info.getJobpostId(), 6)) + "</td>");
                            str.append("<td class='hand_cursor' href='javascript: void(0);' onclick=\"javascript: view('" + info.getCandidateId() +"','"+ info.getJobpostId()+"','"+info.getShortlistId()+"','"+ info.getStatus()+ "');\">" + (info.getDate() != null ? info.getDate() : "") + "</td>");
                            str.append("<td class='hand_cursor' href='javascript: void(0);' onclick=\"javascript: view('" + info.getCandidateId() +"','"+ info.getJobpostId()+"','"+info.getShortlistId()+"','"+ info.getStatus()+ "');\">" + (info.getName() != null ? info.getName() : "") + "</td>");
                            str.append("<td class='hand_cursor' href='javascript: void(0);' onclick=\"javascript: view('" + info.getCandidateId() +"','"+ info.getJobpostId()+"','"+info.getShortlistId()+"','"+ info.getStatus()+ "');\">" + (info.getPosition() != null ? info.getPosition() : "") + "</td>");
                            str.append("<td class='hand_cursor' href='javascript: void(0);' onclick=\"javascript: view('" + info.getCandidateId() +"','"+ info.getJobpostId()+"','"+info.getShortlistId()+"','"+ info.getStatus()+ "');\">" + (info.getClientName() != null ? info.getClientName() : "") + "</td>");
                            str.append("<td class='hand_cursor' href='javascript: void(0);' onclick=\"javascript: view('" + info.getCandidateId() +"','"+ info.getJobpostId()+"','"+info.getShortlistId()+"','"+ info.getStatus()+ "');\">" + (info.getPosition() != null ? info.getCcStatus() : "") + "</td>");
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