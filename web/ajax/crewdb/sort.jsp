<%@page contentType="text/html"%>
<%@page language="java" import="java.util.ArrayList"%>
<%@page pageEncoding="UTF-8"%>
<%@page import="com.web.jxp.user.UserInfo" %>
<%@page import="com.web.jxp.crewdb.CrewdbInfo" %>
<jsp:useBean id="crewdb" class="com.web.jxp.crewdb.Crewdb" scope="page"/>
<jsp:useBean id="vobj" class="com.web.jxp.base.Validate" scope="page"/>
<%
try
{
    if(session.getAttribute("LOGININFO") != null)
    {
        if(request.getParameter("col") != null)
        {
            StringBuilder str = new StringBuilder();
            String colid = request.getParameter("col") != null ? vobj.replaceint(request.getParameter("col")) : "";
            String updowns = request.getParameter("updown") != null && !request.getParameter("updown").equals("") ? vobj.replaceint(request.getParameter("updown")) : "0";
            String nextValue = request.getParameter("nextValue") != null && !request.getParameter("nextValue").equals("") ? vobj.replaceint(request.getParameter("nextValue")) : "0";
            int n = Integer.parseInt(nextValue);
            int updown = Integer.parseInt(updowns);
            int next_value = n;
            n = n - 1;
            
            session.setAttribute("NEXTVALUE", next_value+"");
            ArrayList crewdb_list = new ArrayList();
            if(session.getAttribute("CREWDB_LIST") != null)
                crewdb_list = (ArrayList) session.getAttribute("CREWDB_LIST");
            int total = crewdb_list.size();
            if(total > 0)
            {
                crewdb_list = crewdb.getFinalRecord(crewdb_list, colid, updown);
                session.setAttribute("CREWDB_LIST", crewdb_list);
                
                int status;
                CrewdbInfo info;
                for (int i = 0; i < total; i++)
                {
                    info = (CrewdbInfo) crewdb_list.get(i);
                    if (info != null)
                    {
                        status = info.getStatus();
                        str.append("<tr class='hand_cursor' href='javascript: void(0);' \">");
                        str.append("<td class='hand_cursor' href='javascript: void(0);' onclick=\"javascript: view('" + info.getCrewdbId() + "');\">" + (crewdb.changeNum(info.getCrewdbId(), 6)) + "</td>");
                        str.append("<td class='hand_cursor' href='javascript: void(0);' onclick=\"javascript: view('" + info.getCrewdbId() + "');\">" + (info.getName() != null ? info.getName() : "") + "</td>");
                        str.append("<td class='hand_cursor' href='javascript: void(0);' onclick=\"javascript: view('" + info.getCrewdbId() + "');\">" + (info.getPosition() != null ? info.getPosition() : "") + "</td>");
                        str.append("<td class='hand_cursor' href='javascript: void(0);' onclick=\"javascript: view('" + info.getCrewdbId() + "');\">" + (info.getClientName() != null ? info.getClientName() : "") + "</td>");
                        str.append("<td class='hand_cursor' href='javascript: void(0);' onclick=\"javascript: view('" + info.getCrewdbId() + "');\">" + (info.getCountryName() != null ? info.getCountryName() : "") + "</td>");
                        str.append("<td class='hand_cursor' href='javascript: void(0);' onclick=\"javascript: view('" + info.getCrewdbId() + "');\">" + (info.getAssetName() != null ? info.getAssetName() : "") + "</td>");
                        str.append("<td class='action_column'>");
                        str.append("<a href='javascript:;' onclick=\"javascript: viewimg('" + (info.getCrewdbId()) + "');\" class='mr_15' data-bs-toggle='modal' data-bs-target='#view_resume_list'><img src='../assets/images/attachment.png'></a>");
                        if(status == 2)
                            str.append("<td class='text-center'><img class='cer_img' src='../assets/images/active_status.png' /></td>");
                        else
                            str.append("<td class='text-center'><img class='cer_img' src='../assets/images/inactive_status.png' /></td>");
                        str.append("</tr>");
                    }
                }
            }
            String st = str.toString();
            str.setLength(0);
            response.getWriter().write(st);
        }
        else
        {
            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
        }
    }
    else
    {
        response.getWriter().write("Please check your login session....");
    }
}
catch(Exception e)
{    
    e.printStackTrace();
}
%>