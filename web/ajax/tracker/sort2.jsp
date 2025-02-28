<%@page contentType="text/html"%>
<%@page language="java" import="java.util.ArrayList"%>
<%@page pageEncoding="UTF-8"%>
<%@page import="com.web.jxp.user.UserInfo" %>
<%@page import="com.web.jxp.tracker.TrackerInfo" %>
<jsp:useBean id="tracker" class="com.web.jxp.tracker.Tracker" scope="page"/>
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
                ArrayList tracker_list = new ArrayList();
                if (session.getAttribute("TRACKER_LIST") != null) {
                    tracker_list = (ArrayList) session.getAttribute("TRACKER_LIST");
                }
                int total = tracker_list.size();
                if (total > 0) 
                {
                    tracker_list = tracker.getFinalRecord2(tracker_list, colid, updown);
                    session.setAttribute("TRACKER_LIST", tracker_list);
                
                    TrackerInfo info;
                    for (int i = 0; i < total; i++)
                    {
                        info = (TrackerInfo) tracker_list.get(i);
                        if (info != null) 
                        {
                            str.append("<tr class='hand_cursor' href='javascript: void(0);'>");
                            str.append("<td class='hand_cursor' href='javascript: void(0);' onclick=\"javascript: assign2('"+info.getPcodeId()+"');\">" + (info.getRole()!= null ? info.getRole(): "") + "</td>");
                            str.append("<td class='assets_list text-center'><a href=\"javascript:;\" onclick=\"javascript: assign2('"+info.getPcodeId()+"');\">"+tracker.changeNum(info.getTotal(),2)+"</a></td>");
                            str.append("<td class='text-center'>");
                                str.append("<div class='main-nav'>");
                                    str.append("<ul>");
                                        str.append("<li class='drop_down'>");
                                            str.append("<a href='javascript:;' class='toggle'><i class='fas fa-ellipsis-v'></i></a>");
                                            str.append("<div class='dropdown_menu'>");
                                                str.append("<div class='dropdown-wrapper'>");
                                                    str.append("<div class='category-menu'>");
                                                        str.append("<a href=\"javascript: assign2('"+info.getPcodeId()+"');\">View Personnel</a>");
                                                    str.append("</div>");
                                                str.append("</div>");
                                            str.append("</div>");
                                        str.append("</li>");
                                    str.append("</ul>");
                                str.append("</div>");
                            str.append("</td>");
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