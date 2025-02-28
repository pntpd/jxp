<%@page contentType="text/html"%>
<%@page language="java" import="java.util.ArrayList"%>
<%@page pageEncoding="UTF-8"%>
<%@page import="com.web.jxp.user.UserInfo" %>
<%@page import="com.web.jxp.timesheet.TimesheetInfo" %>
<jsp:useBean id="timesheet" class="com.web.jxp.timesheet.Timesheet" scope="page"/>
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
                ArrayList timesheet_list = new ArrayList();
                if (session.getAttribute("TIMESHEET_LIST") != null) {
                    timesheet_list = (ArrayList) session.getAttribute("TIMESHEET_LIST");
                }
                int total = timesheet_list.size();    
                if (total > 0) 
                {
                    timesheet_list = timesheet.getFinalRecord(timesheet_list, colid, updown);
                    total = timesheet_list.size(); 
                    session.setAttribute("TIMESHEET_LIST", timesheet_list);
                
                    TimesheetInfo info;
                    for (int i = 0; i < total; i++)
                    {
                        info = (TimesheetInfo) timesheet_list.get(i);                        
                        if (info != null) 
                        {
                            str.append("<tr class='hand_cursor' href='javascript: void(0);'>");
                            str.append("<td class='hand_cursor' href='javascript: void(0);' onclick=\"javascript: showDetail('"+info.getAssetId()+"');\">" + (info.getClientName()!= null ? info.getClientName(): "") + "</td>");
                            str.append("<td class='hand_cursor' href='javascript: void(0);' onclick=\"javascript: showDetail('"+info.getAssetId()+"');\">" + (info.getClientAssetName()!= null ? info.getClientAssetName(): "") + "</td>");
                            str.append("<td class='hand_cursor text-center' href='javascript: void(0);' onclick=\"javascript: showDetail('"+info.getAssetId()+"');\">" + (info.getTypevalue()!= null ? info.getTypevalue(): "") + "</td>");
                            str.append("<td class='hand_cursor assets_list text-center'><a href='javascript: void(0);' onclick=\"javascript: showDetail('"+info.getAssetId()+"');\">" + info.getTcount()+ "</a></td>");                            
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