<%@page contentType="text/html"%>
<%@page language="java" import="java.util.ArrayList"%>
<%@page pageEncoding="UTF-8"%>
<%@page import="com.web.jxp.user.UserInfo" %>
<%@page import="com.web.jxp.crewinsurance.CrewinsuranceInfo" %>
<jsp:useBean id="crewinsurance" class="com.web.jxp.crewinsurance.Crewinsurance" scope="page"/>
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
                int updown = Integer.parseInt(updowns);
                ArrayList crewinsurance_list = new ArrayList();
                if (session.getAttribute("CREWINSURANCE_LIST") != null) {
                    crewinsurance_list = (ArrayList) session.getAttribute("CREWINSURANCE_LIST");
                }
                int total = crewinsurance_list.size();
                if (total > 0) 
                {
                    crewinsurance_list = crewinsurance.getFinalRecord(crewinsurance_list, colid, updown);
                    session.setAttribute("CREWINSURANCE_LIST", crewinsurance_list);
                    CrewinsuranceInfo info;
                    for (int i = 0; i < total; i++) 
                    {
                        info = (CrewinsuranceInfo) crewinsurance_list.get(i);
                        if (info != null) 
                        {
                            str.append("<tr>");
                            str.append("<td>" + (info.getName() != null ? info.getName() : "") + "</td>");
                            str.append("<td>" + (info.getPosition()!= null ? info.getPosition() : "") + "</td>");
                            str.append("<td>" + (info.getStval() != null ? info.getStval() : "") + "</td>");
                            str.append("<td class='text-right action_column'>");
                            if(info.getStatus() > 0){
                                str.append("<a data-bs-toggle='modal' data-bs-target='#upload_insurance_certificate' onclick=\"javascript: getcertdetails('" + info.getCrewrotationId()+ "', '" + info.getCrewinsuranceId() + "', '2');\" class='edit_mode mr_15'><img src='../assets/images/view.png'/></a>");
                            }else{
                                str.append("&nbsp;");
                            }
                            str.append("&nbsp;");
                            str.append("<a data-bs-toggle='modal' data-bs-target='#upload_insurance_certificate' onclick=\"javascript: getcertdetails('" + info.getCrewrotationId()+ "', '" + info.getCrewinsuranceId() + "', '1');\" class='edit_mode mr_15'><img src='../assets/images/pencil.png'/></a>");
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