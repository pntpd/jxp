<%@page contentType="text/html"%>
<%@page language="java" import="java.util.ArrayList"%>
<%@page pageEncoding="UTF-8"%>
<%@page import="com.web.jxp.user.UserInfo" %>
<%@page import="com.web.jxp.checkpoint.CheckPointInfo" %>
<jsp:useBean id="checkpoint" class="com.web.jxp.checkpoint.CheckPoint" scope="page"/>
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
                ArrayList checkpoint_list = new ArrayList();
                if (session.getAttribute("CHECKPOINT_LIST") != null) {
                    checkpoint_list = (ArrayList) session.getAttribute("CHECKPOINT_LIST");
                }
                int total = checkpoint_list.size();
                if (total > 0) 
                {
                    checkpoint_list = checkpoint.getFinalRecord(checkpoint_list, colid, updown);
                    session.setAttribute("CHECKPOINT_LIST", checkpoint_list);

                    int status;
                    CheckPointInfo info;
                    for (int i = 0; i < total; i++) 
                    {
                        info = (CheckPointInfo) checkpoint_list.get(i);
                        if (info != null) 
                        {
                            status = info.getStatus();
                            str.append("<tr>");
                            str.append("<td class='hand_cursor' href='javascript: void(0);' onclick='showDetail(" + info.getCheckpointId() + ");'>" + (info.getName() != null ? info.getName() : "") + "</td>");
                            str.append("<td class='hand_cursor' href='javascript: void(0);' onclick='showDetail(" + info.getCheckpointId() + ");'>" + (info.getClientname() != null ? info.getClientname() : "") + "</td>");
                            str.append("<td class='hand_cursor' href='javascript: void(0);' onclick='showDetail(" + info.getCheckpointId() + ");'>" + (info.getClientassetname() != null ? info.getClientassetname() : "") + "</td>");
                            str.append("<td class='hand_cursor' href='javascript: void(0);' onclick='showDetail(" + info.getCheckpointId() + ");'>" + (info.getPositionname() != null ? info.getPositionname() + " / " + info.getGrade() : "") + "</td>");
                            if (info.getFlag() == 1) {
                                str.append("<td class='primary_active text-center hand_cursor' href='javascript: void(0);' onclick='showDetail(" + info.getCheckpointId() + ");'><i class='fa fa-check' aria-hidden='true'></i></td>");
                            } else {
                                str.append("<td></td>");
                            }
                            str.append("<td class='action_column'>");
                            if (editper.equals("Y") && status == 1) {
                                str.append("<a href=\"javascript: modifyForm('" + (info.getCheckpointId()) + "');\" class='mr_15'><img src='../assets/images/pencil.png'></a>");
                            } else {
                                str.append("<a href='javascript:;' ><span style='width: 35px;'>&nbsp;</span></a>");
                            }
                            str.append("<span class='switch_bth'>");
                            str.append("<div class='form-check form-switch'>");
                            str.append("<input class='form-check-input' type='checkbox' role='switch' id='flexSwitchCheckDefault_" + (i) + "' ");
                            if (!editper.equals("Y")) {
                                str.append("  disabled = 'true'");
                            }
                            if (status == 1) {
                                str.append("  checked");
                            }
                            str.append(" onclick=\"javascript: deleteForm('" + info.getCheckpointId() + "', '" + status + "', '" + (i) + "');\"/>");
                            str.append("</div>");
                            str.append("</span>");
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