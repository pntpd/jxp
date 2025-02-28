<%@page contentType="text/html"%>
<%@page language="java" import="java.util.ArrayList"%>
<%@page pageEncoding="UTF-8"%>
<%@page import="com.web.jxp.documentexpiry.DocumentexpiryInfo" %>
<%@page import="com.web.jxp.user.UserInfo" %>
<jsp:useBean id="documentexpiry" class="com.web.jxp.documentexpiry.Documentexpiry" scope="page"/>
<jsp:useBean id="vobj" class="com.web.jxp.base.Validate" scope="page"/>
<%
    try 
    {
        if (session.getAttribute("LOGININFO") != null) 
        {
            int allclient = 0;
            UserInfo uinfo = ((UserInfo) request.getSession().getAttribute("LOGININFO"));
            String per = "",assetids = "", cids ="";
            if (uinfo != null) 
            {
                per = uinfo.getPermission() != null ? uinfo.getPermission() : "N";
                allclient = uinfo.getAllclient();
                assetids = uinfo.getAssetids();
                cids = uinfo.getCids();
            }
            StringBuilder sb = new StringBuilder();
            sb.append("<thead>");            
            sb.append("<tr>");
                sb.append("<th width='20%'>");
                    sb.append("<span><b>Action By</b></span>");
                    sb.append("<a href=\"javascript: sortForm2('1', '2');\" id='img_1_2' class='sort_arrow deactive_sort'><i class='ion ion-ios-arrow-up'></i></a>");
                    sb.append("<a href=\"javascript: sortForm2('1', '1');\" id='img_1_1' class='sort_arrow deactive_sort'><i class='ion ion-ios-arrow-down'></i></a>");
                sb.append("</th>");
                sb.append("<th width='15%'>");
                    sb.append("<span><b>Module</b></span>");
                    sb.append("<a href=\"javascript: sortForm2('2', '2');\" id='img_2_2' class='sort_arrow deactive_sort'><i class='ion ion-ios-arrow-up'></i></a>");
                    sb.append("<a href=\"javascript: sortForm2('2', '1');\" id='img_2_1' class='sort_arrow deactive_sort'><i class='ion ion-ios-arrow-down'></i></a>");
                sb.append("</th>");
                sb.append("<th width='15%'>");
                    sb.append("<span><b>Action Type</b></span>");
                    sb.append("<a href=\"javascript: sortForm2('3', '2');\" id='img_3_2' class='sort_arrow deactive_sort'><i class='ion ion-ios-arrow-up'></i></a>");
                    sb.append("<a href=\"javascript: sortForm2('3', '1');\" id='img_3_1' class='sort_arrow deactive_sort'><i class='ion ion-ios-arrow-down'></i></a>");
                sb.append("</th>");
                sb.append("<th width='15%'>");
                    sb.append("<span><b>Client</b></span>");
                    sb.append("<a href=\"javascript: sortForm2('4', '2');\" id='img_4_2' class='sort_arrow deactive_sort'><i class='ion ion-ios-arrow-up'></i></a>");
                    sb.append("<a href=\"javascript: sortForm2('4', '1');\" id='img_4_1' class='sort_arrow deactive_sort'><i class='ion ion-ios-arrow-down'></i></a>");
                sb.append("</th>");
                sb.append("<th width='15%'>");
                    sb.append("<span><b>Asset</b></span>");
                    sb.append("<a href=\"javascript: sortForm2('5', '2');\" id='img_5_2' class='sort_arrow deactive_sort'><i class='ion ion-ios-arrow-up'></i></a>");
                    sb.append("<a href=\"javascript: sortForm2('5', '1');\" id='img_5_1' class='sort_arrow deactive_sort'><i class='ion ion-ios-arrow-down'></i></a>");
                sb.append("</th>");
                sb.append("<th width='10%'>");
                    sb.append("<span><b>Date-Time</b></span>");
                    sb.append("<a href=\"javascript: sortForm2('6', '2');\" id='img_6_2' class='sort_arrow deactive_sort'><i class='ion ion-ios-arrow-up'></i></a>");
                    sb.append("<a href=\"javascript: sortForm2('6', '1');\" id='img_6_1' class='sort_arrow deactive_sort'><i class='ion ion-ios-arrow-down'></i></a>");
                sb.append("</th>");
                sb.append("<th width='10%' class='text-right'>");
                    sb.append("<span><b>Action</b></span>");
                sb.append("</th>");
            sb.append("</tr>");
            sb.append("</thead>");
            String thead = sb.toString();
            sb.setLength(0);
            String search = request.getParameter("search") != null && !request.getParameter("search").equals("") ? vobj.replacedesc(request.getParameter("search")) : "";
            String fromDate = request.getParameter("fromDate") != null && !request.getParameter("fromDate").equals("") ? vobj.replacedate(request.getParameter("fromDate")) : "";
            String toDate = request.getParameter("toDate") != null && !request.getParameter("toDate").equals("") ? vobj.replacedate(request.getParameter("toDate")) : "";
            String clientIdIndexs = request.getParameter("clientIdAlert") != null && !request.getParameter("clientIdAlert").equals("") ? vobj.replaceint(request.getParameter("clientIdAlert")) : "-1";
            String assetIdIndexs = request.getParameter("assetIdAlert") != null && !request.getParameter("assetIdAlert").equals("") ? vobj.replaceint(request.getParameter("assetIdAlert")) : "-1";
            String moduleIds = request.getParameter("moduleId") != null && !request.getParameter("moduleId").equals("") ? vobj.replaceint(request.getParameter("moduleId")) : "-1";
            
            int clientIdIndex = Integer.parseInt(clientIdIndexs);
            int assetIdIndex = Integer.parseInt(assetIdIndexs);
            int moduleId = Integer.parseInt(moduleIds);
            String viewpath = documentexpiry.getMainPath("view_maillog_file");
            StringBuilder str = new StringBuilder();
            ArrayList list = new ArrayList();
            list = documentexpiry.getAlertByName(search, clientIdIndex, assetIdIndex, fromDate, toDate, moduleId, allclient, per, cids, assetids);
            int total = list.size();
            session.setAttribute("ALERT_LIST", list);
            str.append("<div class='col-lg-12' id='printArea'>");
            str.append("<div class='table-rep-plugin sort_table'>");
            str.append("<div class='table-responsive mb-0' data-bs-pattern='priority-columns'>");
            str.append("<table id='tech-companies-1' class='table table-striped'>");
            str.append(thead);
            str.append("<tbody id ='sort_id2'>");
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
                        str.append("<td class='hand_cursor' onclick=\"javascript: showDetail('"+ mainId+"');\">" + (info.getName() != null ? info.getName() : "") + "</td>");
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
                        str.append("<td class='hand_cursor' onclick=\"javascript: showDetail('"+ mainId+"');\">" + (info.getModuleName() != null ? info.getModuleName() : "") + "</td>");
                    } else if(mId == 2){
                        str.append("<td class='hand_cursor' onclick=\"goToClientSelectionByAssetId('"+ mainId +"');\">" + (info.getModuleName() != null ? info.getModuleName() : "") + "</td>");
                    } else if(mId == 3){
                        str.append("<td class='hand_cursor' onclick=\"showJobpostDetail('"+ mainId +"');\">" + (info.getModuleName() != null ? info.getModuleName() : "") + "</td>");
                    } else if(mId == 4){
                        str.append("<td class='hand_cursor' onclick=\"goToClientSelectionById('"+ mainId +"');\">" + (info.getModuleName() != null ? info.getModuleName() : "") + "</td>");
                    } else if(mId == 5){
                        str.append("<td class='hand_cursor' onclick=\"goToCrewrotation('"+ clientId +"', '"+ clientassetId +"');\">" + (info.getModuleName() != null ? info.getModuleName() : "") + "</td>");
                    }
                    
                    if(mId == 1){
                        str.append("<td class='hand_cursor' onclick=\"javascript: showDetail('"+ mainId+"');\">" + (info.getStVal() != null ? info.getStVal() : "") + "</td>");
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
                        str.append("<td class='hand_cursor' onclick=\"javascript: showDetail('"+ mainId+"');\">" + (info.getClientName() != null ? info.getClientName() : "") + "</td>");
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
                        str.append("<td class='hand_cursor' onclick=\"javascript: showDetail('"+ mainId+"');\">" + (info.getAssetName() != null ? info.getAssetName() : "") + "</td>");
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
                        str.append("<td class='hand_cursor' onclick=\"javascript: showDetail('"+ mainId+"');\">" + (info.getDate() != null ? info.getDate() : "") + "</td>");
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
//                    str.append("<a class='coll_form_ic' href=\"javascript: deleteAlert('"+notificationId+ "');\"><img src='../assets/images/cross.png' /></a>");
                    str.append("</td>");
                    str.append("</tr>");
                }
            }
            str.append("</tbody>");
            str.append("</table>");
            str.append("</div>");
            str.append("</div>");
            str.append("</div>");

            String s1 = str.toString();
            str.setLength(0);
            response.getWriter().write(s1);
            
        } else {
            response.getWriter().write("Please check your login session....");
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
%>