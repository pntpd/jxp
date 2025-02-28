<%@page contentType="text/html"%>
<%@page language="java" import="java.util.ArrayList"%>
<%@page pageEncoding="UTF-8"%>
<%@page import="com.web.jxp.user.UserInfo" %>
<%
    try 
    {
        if (session.getAttribute("LOGININFO") != null)
        {
            ArrayList list = new ArrayList();
            if (session.getAttribute("LIST") != null) 
            {
                list = (ArrayList) session.getAttribute("LIST");
            }
            String action = request.getParameter("action") != null ? request.getParameter("action") : "";
            String cassessor_s = request.getParameter("cassessor") != null && !request.getParameter("cassessor").equals("") ? request.getParameter("cassessor") : "0";
            int cassessor = Integer.parseInt(cassessor_s);
            if (action.equals("add"))
            {
                String clientId_s = request.getParameter("clientId") != null && !request.getParameter("clientId").equals("") ? request.getParameter("clientId") : "0";
                String clientName = request.getParameter("clientName") != null ? request.getParameter("clientName") : "";
                String countryId_s = request.getParameter("countryId") != null && !request.getParameter("countryId").equals("") ? request.getParameter("countryId") : "0";
                String countryName = request.getParameter("countryName") != null ? request.getParameter("countryName") : "";
                String assetids = request.getParameter("assets") != null ? request.getParameter("assets") : "";
                String assettext = request.getParameter("assets_text") != null ? request.getParameter("assets_text").replaceAll(",", ", ") : "";
                String positionids = request.getParameter("positions") != null ? request.getParameter("positions") : "";
                String positiontext = request.getParameter("positions_text") != null ? request.getParameter("positions_text").replaceAll(",", ", ") : "";
                
                int clientId = Integer.parseInt(clientId_s);
                int countryId = Integer.parseInt(countryId_s);       
                if(cassessor <= 0)
                {
                    positionids = "";
                    positiontext = "";
                }
                UserInfo info = new UserInfo(clientId, countryId, clientName, countryName, assetids, assettext, positionids, positiontext);
                boolean b = true;
                int list_size = list.size();
                if (list_size > 0) {
                    UserInfo pinfo = null;
                    for (int i = 0; i < list_size; i++) {
                        pinfo = (UserInfo) list.get(i);
                        if (pinfo != null && pinfo.getClientId() == clientId && pinfo.getCountryId() == countryId) {
                            b = false;
                            break;
                        }
                    }
                }
                if (b == true) {
                    list.add(info);
                    session.setAttribute("LIST", list);
                }
            } else if (action.equals("delete")) {
                String id_s = request.getParameter("id") != null && !request.getParameter("id").equals("") ? request.getParameter("id") : "-1";
                int id = Integer.parseInt(id_s);
                if (id >= 0) {
                    list.remove(id);
                    session.setAttribute("LIST", list);
                }
            } else if (action.equals("deleteall")) {
                list.clear();
                session.removeAttribute("LIST");
            } else if (action.equals("onload")) {
            }
            StringBuilder sb = new StringBuilder();
            int list_size = list.size();
            if (list_size > 0) 
            {
                UserInfo pinfo = null;
                String clientName, countryName, assettext, positiontext;
                for (int i = 0; i < list_size; i++) 
                {
                    pinfo = (UserInfo) list.get(i);
                    if (pinfo != null) 
                    {
                        clientName = pinfo.getClientName() != null ? pinfo.getClientName() : "";
                        countryName = pinfo.getCountryName() != null && !pinfo.getCountryName().equals("") && !pinfo.getCountryName().equals("- Select -") ? pinfo.getCountryName() : "All";
                        assettext = pinfo.getAssettext() != null ? pinfo.getAssettext() : "";
                        positiontext = pinfo.getPositionRankId()!= null ? pinfo.getPositionRankId() : "";
                        sb.append("<tr>");
                        sb.append("<td>" + clientName + "</td>");
                        sb.append("<td>" + countryName + "</td>");
                        sb.append("<td>" + assettext + "</td>");
                        if(cassessor > 0)
                            sb.append("<td>" + positiontext + "</td>");
                        sb.append("<td class='action-btn'><a class='btn-delete' href=\"javascript: delfromlist('" + i + "');\"><i class='dripicons-trash'></i></a></td>");
                        sb.append("</tr>");
                    }
                }
            }
            String s1 = sb.toString();
            sb.setLength(0);
            response.getWriter().write(s1);
        } else {
            response.getWriter().write("Please check your login session....");
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
%>