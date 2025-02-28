<%@page contentType="text/html"%>
<%@page language="java" import="java.util.ArrayList"%>
<%@page pageEncoding="UTF-8"%>
<%@page import="com.web.jxp.user.UserInfo" %>
<%@page import="com.web.jxp.documentexpiry.DocumentexpiryInfo" %>
<jsp:useBean id="documentexpiry" class="com.web.jxp.documentexpiry.Documentexpiry" scope="page"/>
<jsp:useBean id="vobj" class="com.web.jxp.base.Validate" scope="page"/>
<%
    try 
    {
        if (session.getAttribute("LOGININFO") != null) 
        {
            String viewpath = documentexpiry.getMainPath("view_maillog_file");
            if (request.getParameter("col") != null) 
            {
                StringBuilder str = new StringBuilder();
                String colid = request.getParameter("col") != null ? vobj.replaceint(request.getParameter("col")) : "";
                String updowns = request.getParameter("updown") != null && !request.getParameter("updown").equals("") ? vobj.replaceint(request.getParameter("updown")) : "0";
                int updown = Integer.parseInt(updowns);                
                ArrayList list = new ArrayList();
                if (session.getAttribute("ALERT_LIST") != null) {
                    list = (ArrayList) session.getAttribute("ALERT_LIST");
                }
                int total = list.size();
                if (total > 0) 
                {
                    list = documentexpiry.getFinalRecord2(list, colid, updown);
                    session.setAttribute("ALERT_LIST", list);
                    DocumentexpiryInfo info;
                    for (int i = 0; i < total; i++) 
                    {
                        info = (DocumentexpiryInfo) list.get(i);
                        if (info != null) 
                        {
                            int mainId = info.getMainId();
                            int mId = info.getModuleId();
                            int clientId = info.getClientId();
                            int clientassetId = info.getClientassetId();
                            int notificationId = info.getNotificationId();
                            
                            str.append("<tr>"); 
                            if(mId == 1){
                                str.append("<td class='hand_cursor' onclick=\"javascript: showDetail('"+ mainId +"');\">" + (info.getName() != null ? info.getName() : "") + "</td>");
                            } else if(mId == 2){
                                str.append("<td class='hand_cursor' onclick=\"goToClientSelectionByAssetId('"+ mainId +"');\">" + (info.getName() != null ? info.getName() : "") + "</td>");
                            } else if(mId == 3){
                                str.append("<td class='hand_cursor' onclick=\"showJobpostDetail('"+ mainId +"');\">" + (info.getName() != null ? info.getName() : "") + "</td>");
                            } else if(mId == 4){
                                str.append("<td class='hand_cursor' onclick=\"goToClientSelectionById('"+ mainId +"');\">" + (info.getName() != null ? info.getName() : "") + "</td>");
                            } else if(mId == 5){
                                str.append("<td class='hand_cursor' onclick=\"goToCrewrotation('"+ clientId +"', '"+ clientassetId +"');\">" + (info.getName() != null ? info.getName() : "") + "</td>");
                            }

                            if(mId == 1){
                                str.append("<td class='hand_cursor' onclick=\"javascript: showDetail('"+ mainId +"');\">" + (info.getModuleName() != null ? info.getModuleName() : "") + "</td>");
                            } else if(mId == 2){
                                str.append("<td class='hand_cursor' onclick=\"goToClientSelectionByAssetId('"+ mainId +"');\">" + (info.getModuleName() != null ? info.getModuleName(): "") + "</td>");
                            } else if(mId == 3){
                                str.append("<td class='hand_cursor' onclick=\"showJobpostDetail('"+ mainId +"');\">" + (info.getModuleName() != null ? info.getModuleName() : "") + "</td>");
                            } else if(mId == 4){
                                str.append("<td class='hand_cursor' onclick=\"goToClientSelectionById('"+ mainId +"');\">" + (info.getModuleName() != null ? info.getModuleName() : "") + "</td>");
                            } else if(mId == 5){
                                str.append("<td class='hand_cursor' onclick=\"goToCrewrotation('"+ clientId +"', '"+ clientassetId +"');\">" + (info.getModuleName() != null ? info.getModuleName() : "") + "</td>");
                            }

                            if(mId == 1){
                                str.append("<td class='hand_cursor' onclick=\"javascript: showDetail('"+ mainId +"');\">" + (info.getStVal() != null ? info.getStVal() : "") + "</td>");
                            } else if(mId == 2){
                                str.append("<td class='hand_cursor' onclick=\"goToClientSelectionByAssetId('"+ mainId +"');\">" + (info.getStVal() != null ? info.getStVal() : "") + "</td>");
                            } else if(mId == 3){
                                str.append("<td class='hand_cursor' onclick=\"showJobpostDetail('"+ mainId +"');\">" + (info.getStVal() != null ? info.getStVal() : "") + "</td>");
                            } else if(mId == 4){
                                str.append("<td class='hand_cursor' onclick=\"goToClientSelectionById('"+ mainId +"');\">" + (info.getStVal() != null ? info.getStVal() : "") + "</td>");
                            } else if(mId == 5){
                                str.append("<td class='hand_cursor' onclick=\"goToCrewrotation('"+ clientId +"', '"+ clientassetId +"');\">" + (info.getStVal() != null ? info.getStVal() : "") + "</td>");
                            }

                            if(mId == 1){
                                str.append("<td class='hand_cursor' onclick=\"javascript: showDetail('"+ mainId +"');\">" + (info.getClientName() != null ? info.getClientName() : "") + "</td>");
                            } else if(mId == 2){
                                str.append("<td class='hand_cursor' onclick=\"goToClientSelectionByAssetId('"+ mainId +"');\">" + (info.getClientName() != null ? info.getClientName() : "") + "</td>");
                            } else if(mId == 3){
                                str.append("<td class='hand_cursor' onclick=\"showJobpostDetail('"+ mainId +"');\">" + (info.getClientName() != null ? info.getClientName() : "") + "</td>");
                            } else if(mId == 4){
                                str.append("<td class='hand_cursor' onclick=\"goToClientSelectionById('"+ mainId +"');\">" + (info.getClientName() != null ? info.getClientName() : "") + "</td>");
                            } else if(mId == 5){
                                str.append("<td class='hand_cursor' onclick=\"goToCrewrotation('"+ clientId +"', '"+ clientassetId +"');\">" + (info.getClientName() != null ? info.getClientName() : "") + "</td>");
                            }

                            if(mId == 1){
                                str.append("<td class='hand_cursor' onclick=\"javascript: showDetail('"+ mainId  +"');\">" + (info.getAssetName() != null ? info.getAssetName() : "") + "</td>");
                            } else if(mId == 2){
                                str.append("<td class='hand_cursor' onclick=\"goToClientSelectionByAssetId('"+ mainId +"');\">" + (info.getAssetName() != null ? info.getAssetName() : "") + "</td>");
                            } else if(mId == 3){
                                str.append("<td class='hand_cursor' onclick=\"showJobpostDetail('"+ mainId +"');\">" + (info.getAssetName() != null ? info.getAssetName() : "") + "</td>");
                            } else if(mId == 4){
                                str.append("<td class='hand_cursor' onclick=\"goToClientSelectionById('"+ mainId +"');\">" + (info.getAssetName() != null ? info.getAssetName() : "") + "</td>");
                            } else if(mId == 5){
                                str.append("<td class='hand_cursor' onclick=\"goToCrewrotation('"+ clientId +"', '"+ clientassetId +"');\">" + (info.getAssetName() != null ? info.getAssetName() : "") + "</td>");
                            }

                            if(mId == 1){
                                str.append("<td class='hand_cursor' onclick=\"javascript: showDetail('"+ mainId +"');\">" + (info.getDate() != null ? info.getDate() : "") + "</td>");
                            } else if(mId == 2){
                                str.append("<td class='hand_cursor' onclick=\"goToClientSelectionByAssetId('"+ mainId +"');\">" + (info.getDate() != null ? info.getDate() : "") + "</td>");
                            } else if(mId == 3){
                                str.append("<td class='hand_cursor' onclick=\"showJobpostDetail('"+ mainId +"');\">" + (info.getDate() != null ? info.getDate() : "") + "</td>");
                            } else if(mId == 4){
                                str.append("<td class='hand_cursor' onclick=\"goToClientSelectionById('"+ mainId +"');\">" + (info.getDate() != null ? info.getDate() : "") + "</td>");
                            } else if(mId == 5){
                                str.append("<td class='hand_cursor' onclick=\"goToCrewrotation('"+ clientId +"', '"+ clientassetId +"');\">" + (info.getDate() != null ? info.getDate() : "") + "</td>");
                            }
                            str.append("<td class='action_column text-right'>");                    
                            if(info.getFilename() != null && !info.getFilename().equals("")) {
                                str.append("<a href=\"" + viewpath+info.getFilename()+ "\" class='view_mode mr_15' target='_blank'>");
                                    str.append("<img src='../assets/images/view.png'/></a>");
                            }  
                            //str.append("<a class='coll_form_ic' href=\"javascript: deleteAlert('"+notificationId+ "');\"><img src='../assets/images/cross.png' /></a>");
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