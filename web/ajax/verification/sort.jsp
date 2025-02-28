<%@page contentType="text/html"%>
<%@page language="java" import="java.util.ArrayList"%>
<%@page pageEncoding="UTF-8"%>
<%@page import="com.web.jxp.user.UserInfo" %>
<%@page import="com.web.jxp.verification.VerificationInfo" %>
<jsp:useBean id="verification" class="com.web.jxp.verification.Verification" scope="page"/>
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
                ArrayList verification_list = new ArrayList();
                if (session.getAttribute("VERIFICATION_LIST") != null) {
                    verification_list = (ArrayList) session.getAttribute("VERIFICATION_LIST");
                }
                int total = verification_list.size();
                if (total > 0) 
                {
                    verification_list = verification.getFinalRecord(verification_list, colid, updown);
                    session.setAttribute("VERIFICATION_LIST", verification_list);
                    
                    VerificationInfo info;
                    for (int i = 0; i < total; i++)
                    {
                        info = (VerificationInfo) verification_list.get(i);
                        if (info != null)
                        {
                            str.append("<tr class='hand_cursor' href='javascript: void(0);' >");
                            str.append("<td class='hand_cursor' href='javascript: void(0);' onclick=\"javascript: view('" + info.getVerificationId() + "');\">" + (info.getIds() != null ? info.getIds() : "") + "</td>");
                            str.append("<td class='hand_cursor' href='javascript: void(0);' onclick=\"javascript: view('" + info.getVerificationId() + "');\">" + (info.getEnrollon() != null ? info.getEnrollon() : "") + "</td>");
                            str.append("<td class='hand_cursor' href='javascript: void(0);' onclick=\"javascript: view('" + info.getVerificationId() + "');\">" + (info.getName() != null ? info.getName() : "") + "</td>");
                            str.append("<td class='hand_cursor' href='javascript: void(0);' onclick=\"javascript: view('" + info.getVerificationId() + "');\">" + (info.getPosition() != null ? info.getPosition() : "") + "</td>");
                            str.append("<td class='hand_cursor' href='javascript: void(0);' onclick=\"javascript: view('" + info.getVerificationId() + "');\"><span ");
                            if ((info.getFlagstatusId() ==4 || info.getFlagstatusId() ==3) && info.getFlagav() > 0) {
                                str.append(" class='com_verified'");
                            }
                            str.append(">" + (info.getFlagstatus() != null ? info.getFlagstatus() : "") + "</span></td>");
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