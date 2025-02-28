<%@page contentType="text/html"%>
<%@page language="java" import="java.util.ArrayList"%>
<%@page pageEncoding="UTF-8"%>
<%@page import="com.web.jxp.user.UserInfo" %>
<%@page import="com.web.jxp.mobilization.MobilizationInfo" %>
<jsp:useBean id="mobilization" class="com.web.jxp.mobilization.Mobilization" scope="page"/>
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
                if (session.getAttribute("MOBILIZATION_LIST") != null) {
                    candidate_list = (ArrayList) session.getAttribute("MOBILIZATION_LIST");
                }
                
                int total = candidate_list.size();
                if (total > 0) 
                {
                    candidate_list = mobilization.getFinalRecord(candidate_list, colid, updown);
                    session.setAttribute("MOBILIZATION_LIST", candidate_list);

                    MobilizationInfo info;
                    for (int i = 0; i < total; i++) 
                    {
                        info = (MobilizationInfo) candidate_list.get(i);
                        if (info != null)
                        {
                            str.append("<tr class='hand_cursor' href='javascript: void(0);' \">");
                            str.append("<td class='hand_cursor' href='javascript: void(0);' onclick=\"javascript: view('" + info.getClientId() + "','" + info.getClientassetId() + "');\">" + (info.getClientName() != null ? info.getClientName() : "") + "</td>");
                            str.append("<td class='hand_cursor' href='javascript: void(0);' onclick=\"javascript: view('" + info.getClientId() + "','" + info.getClientassetId() + "');\">" + (info.getClientAsset() != null ? info.getClientAsset() : "") + "</td>");
                            str.append("<td class='hand_cursor' href='javascript: void(0);' onclick=\"javascript: view('" + info.getClientId() + "','" + info.getClientassetId() + "');\">" + (info.getCountry() != null ? info.getCountry() : "") + "</td>");
                            str.append("<td class='assets_list text-center'><a href='javascript:;' class='total_value'>" + (info.getStatus1()) + "</a></td>");
                            str.append("<td class='text-center'>");
                            str.append("<div class='main-nav'>");
                            str.append("<ul>");
                            str.append("<li class='drop_down'>");
                            str.append("<a href = \"javascript:;\" class='toggle'><i class='fas fa-ellipsis-v'></i></a>");
                            str.append("<div class='dropdown_menu'>");
                            str.append("<div class='dropdown-wrapper'>");
                            str.append("<div class='category-menu'>");
                            str.append("<a href=\"javascript: ViewClient('" + info.getClientId() + "');\">View Client</a>");
                            str.append("</div> </div> </div>");
                            str.append("</li> </ul>");
                            str.append("</div> </td>");
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