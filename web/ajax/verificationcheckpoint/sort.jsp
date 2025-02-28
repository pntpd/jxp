<%@page contentType="text/html"%>
<%@page language="java" import="java.util.ArrayList"%>
<%@page pageEncoding="UTF-8"%>
<%@page import="com.web.jxp.user.UserInfo" %>
<%@page import="com.web.jxp.verificationcheckpoint.VerificationcheckpointInfo" %>
<jsp:useBean id="verificationcheckpoint" class="com.web.jxp.verificationcheckpoint.Verificationcheckpoint" scope="page"/>
<jsp:useBean id="vobj" class="com.web.jxp.base.Validate" scope="page"/>
<%
    try 
    {
        if (session.getAttribute("LOGININFO") != null) 
        {
            UserInfo uinfo = ((UserInfo) request.getSession().getAttribute("LOGININFO"));
            String editper = "N";
            if (uinfo != null) 
            {
                editper = uinfo.getEditper() != null ? uinfo.getEditper() : "N";
            }
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
                ArrayList verificationcheckpoint_list = new ArrayList();
                if (session.getAttribute("VERIFICATIONSUBTYPE_LIST") != null) {
                    verificationcheckpoint_list = (ArrayList) session.getAttribute("VERIFICATIONSUBTYPE_LIST");
                }
                int total = verificationcheckpoint_list.size();
                if (total > 0) 
                {
                    verificationcheckpoint_list = verificationcheckpoint.getFinalRecord(verificationcheckpoint_list, colid, updown);
                    session.setAttribute("VERIFICATIONSUBTYPE_LIST", verificationcheckpoint_list);
                    
                    int status;
                    VerificationcheckpointInfo info;
                    for (int i = 0; i < total; i++)
                    {
                        info = (VerificationcheckpointInfo) verificationcheckpoint_list.get(i);
                        if (info != null) 
                        {
                            status = info.getStatus();
                            str.append("<tr>");
                            str.append("<td class='hand_cursor' href='javascript: void(0);' onclick=\"javascript: showDetail('"+ info.getVerificationcheckpointId()+"');\">" + (info.getVerificationcheckpointName() != null ? info.getVerificationcheckpointName() : "") + "</td>");
                             str.append("<td class='hand_cursor' href='javascript: void(0);' onclick=\"javascript: showDetail('"+ info.getVerificationcheckpointId()+"');\">" + (info.getTabname() != null ? info.getTabname() : "") + "</td>");
                            str.append("<td class='hand_cursor' href='javascript: void(0);' onclick=\"javascript: showDetail('"+ info.getVerificationcheckpointId()+"');\">" + (info.getDisplaynote() != null ? info.getDisplaynote() : "") + "</td>");
                           if( info.getMinverification() == 1) {
                            str.append("<td class='primary_active text-center'><i class='fa fa-check' aria-hidden='true'></i></td>"); 
                            } else { 
                            str.append("<td></td>"); 
                            }
                            str.append("<td>");
                            str.append("<span class='switch_bth float-end'>");
                            str.append("<div class='form-check form-switch'>");
                            str.append("<input class='form-check-input' type='checkbox' role='switch' id='flexSwitchCheckDefault_" + (i) + "'");
                            if (!editper.equals("Y")) {
                                str.append("disabled='true'");
                            }
                            if (status == 1) {
                                str.append("checked");
                            }
                            str.append(" onclick=\"javascript: deleteForm('" + info.getVerificationcheckpointId() + "', '" + status + "', '" + (i) + "');\" />");
                            str.append("</div>");
                            str.append("</span>");
                            if (editper.equals("Y") && info.getStatus() == 1) {
                                str.append("<a href=\"javascript: modifyForm('" + info.getVerificationcheckpointId() + "');\" class='edit_mode float-end mr_15'><img src='../assets/images/pencil.png'/></a>");
                            }
                            str.append("</td>");
                            str.append("</tr>");
                        }
                    }
                }
                else
                {
                    str.append("<tr><td colspan='6'>No verification checkpoint available.</td></tr>");
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