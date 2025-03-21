<%@page contentType="text/html"%>
<%@page language="java" import="java.util.ArrayList"%>
<%@page pageEncoding="UTF-8"%>
<%@page import="com.web.jxp.user.UserInfo" %>
<%@page import="com.web.jxp.trainingmatrix.TrainingmatrixInfo" %>
<jsp:useBean id="trainingmatrix" class="com.web.jxp.trainingmatrix.Trainingmatrix" scope="page"/>
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
                ArrayList trainingmatrix_list = new ArrayList();
                if (session.getAttribute("TRAININGMATRIX_LIST") != null) {
                    trainingmatrix_list = (ArrayList) session.getAttribute("TRAININGMATRIX_LIST");
                }
                int total = trainingmatrix_list.size();
                if (total > 0) 
                {
                    trainingmatrix_list = trainingmatrix.getFinalRecord(trainingmatrix_list, colid, updown);
                    session.setAttribute("TRAININGMATRIX_LIST", trainingmatrix_list);
                
                    TrainingmatrixInfo info;
                    for (int i = 0; i < total; i++)
                    {
                        info = (TrainingmatrixInfo) trainingmatrix_list.get(i);
                        if (info != null) 
                        {
                            str.append("<tr class='hand_cursor' href='javascript: void(0);'>");
                            str.append("<td class='hand_cursor' href='javascript: void(0);' onclick=\"javascript: showDetail('"+info.getClientId()+"', '"+info.getAssetId()+"');\">" + (info.getClientName()!= null ? info.getClientName(): "") + "</td>");
                            str.append("<td class='hand_cursor' href='javascript: void(0);' onclick=\"javascript: showDetail('"+info.getClientId()+"', '"+info.getAssetId()+"');\">" + (info.getClientAssetName()!= null ? info.getClientAssetName(): "") + "</td>");
                            str.append("<td class='hand_cursor assets_list text-center'><a href='javascript: void(0);' onclick=\"javascript: showDetail('"+info.getClientId()+"', '"+info.getAssetId()+"');\">" + info.getPcount() + "</a></td>");
                            str.append("<td>"); 
                            str.append("<a href=\"javascript: showDetail('"+info.getClientId()+"', '"+info.getAssetId()+"');\" class='edit_mode float-end mr_15'><img src='../assets/images/pencil.png'/></a>");
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