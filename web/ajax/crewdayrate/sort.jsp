<%@page contentType="text/html"%>
<%@page language="java" import="java.util.ArrayList"%>
<%@page pageEncoding="UTF-8"%>
<%@page import="com.web.jxp.user.UserInfo" %>
<%@page import="com.web.jxp.crewdayrate.CrewdayrateInfo" %>
<jsp:useBean id="crewdayrate" class="com.web.jxp.crewdayrate.Crewdayrate" scope="page"/>
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
            if (request.getParameter("col") != null) {
                StringBuilder str = new StringBuilder();
                String colid = request.getParameter("col") != null ? vobj.replaceint(request.getParameter("col")) : "";
                String updowns = request.getParameter("updown") != null && !request.getParameter("updown").equals("") ? vobj.replaceint(request.getParameter("updown")) : "0";
                String nextValue = request.getParameter("nextValue") != null && !request.getParameter("nextValue").equals("") ? vobj.replaceint(request.getParameter("nextValue")) : "0";
                int n = Integer.parseInt(nextValue);
                int updown = Integer.parseInt(updowns);
                int next_value = n;
                n = n - 1;

                session.setAttribute("NEXTVALUE", next_value + "");
                ArrayList crewdayrate_list = new ArrayList();
                if (session.getAttribute("CREWDAYRATE_LIST") != null) {
                    crewdayrate_list = (ArrayList) session.getAttribute("CREWDAYRATE_LIST");
                }
                int total = crewdayrate_list.size();    
                if (total > 0) 
                {
                    crewdayrate_list = crewdayrate.getFinalRecord(crewdayrate_list, colid, updown);
                    total = crewdayrate_list.size(); 
                    session.setAttribute("CREWDAYRATE_LIST", crewdayrate_list);
                
                    CrewdayrateInfo info;
                    int status;
                    for (int i = 0; i < total; i++)
                    {
                        info = (CrewdayrateInfo) crewdayrate_list.get(i);                        
                        if (info != null) 
                        {
                            status = info.getStatus();
                            str.append("<tr class='hand_cursor' href='javascript: void(0);'>");
                            str.append("<td class='hand_cursor' href='javascript: void(0);' onclick=\"javascript: showDetail('"+info.getAssetId()+"');\">" + (info.getClientName()!= null ? info.getClientName(): "") + "</td>");
                            str.append("<td class='hand_cursor' href='javascript: void(0);' onclick=\"javascript: showDetail('"+info.getAssetId()+"');\">" + (info.getClientAssetName()!= null ? info.getClientAssetName(): "") + "</td>");
                            str.append("<td class='hand_cursor assets_list text-center'><a href='javascript: void(0);' onclick=\"javascript: showDetail('"+info.getAssetId()+"');\">" + info.getPcount() + "</a></td>");
                            str.append("<td class='hand_cursor assets_list text-center'><a href='javascript: void(0);' onclick=\"javascript: showDetail('"+info.getAssetId()+"');\">" + info.getPendingcount() + "</a></td>");
                            str.append("<td class='action_column' >");
                            if(info.getScount() > 0)
                            {
                                str.append("<a class='mr_15' href=\"javascript: showDetail('"+info.getAssetId()+"');\"><img src='../assets/images/pencil.png'></a>");
                            }else
                            {
                                str.append("<a class='mr_15' href=\"javascript: showDetail('"+info.getAssetId()+"');\"><img src='../assets/images/link.png'></a>");
                            }
                            str.append("<span class='switch_bth'>");
                            str.append("<div class='form-check form-switch'>");
                            str.append("<input class='form-check-input' type='checkbox' role='switch' id='flexSwitchCheckDefault"+(i)+"' onclick=\"javascript: changestatus('"+info.getAssetId()+"', '"+status+"','"+i+"');\" ");
                            if(status == 1)
                                str.append(" checked ");
                            if(!editper.equals("Y") || info.getScount() <= 0)
                                str.append(" disabled='true' ");
                            str.append("/>");
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