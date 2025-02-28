<%@page contentType="text/html"%>
<%@page language="java" import="java.util.ArrayList"%>
<%@page pageEncoding="UTF-8"%>
<%@page import="com.web.jxp.crewinsurance.CrewinsuranceInfo" %>
<%@page import="com.web.jxp.user.UserInfo" %>
<jsp:useBean id="crewinsurance" class="com.web.jxp.crewinsurance.Crewinsurance" scope="page"/>
<jsp:useBean id="vobj" class="com.web.jxp.base.Validate" scope="page"/>
<%
    try 
    {
        if (session.getAttribute("LOGININFO") != null) 
        {
            int allclient = 0;
            UserInfo uinfo = ((UserInfo) request.getSession().getAttribute("LOGININFO"));
            String per = "", cids = "", assetids = "";
            if (uinfo != null) 
            {
                per = uinfo.getPermission() != null ? uinfo.getPermission() : "N";
                cids = uinfo.getCids();
                allclient = uinfo.getAllclient();
                assetids = uinfo.getAssetids();
            }
            StringBuilder sb = new StringBuilder();
            sb.append("<thead>");
            sb.append("<tr>");

            sb.append("<th width='40%'><span><b>Personnel Name</b> </span>");
            sb.append("<a href=\"javascript: sortForm('1', '2');\" id='img_1_2' class='sort_arrow deactive_sort'><i class='ion ion-ios-arrow-up'></i></a>");
            sb.append("<a href=\"javascript: sortForm('1', '1');\" id='img_1_1' class='sort_arrow deactive_sort'><i class='ion ion-ios-arrow-down'></i></a>");
            sb.append("</th>");

            sb.append("<th width='38%'><span><b>Position-Rank</b> </span>");
            sb.append("<a href=\"javascript: sortForm('2', '2');\" id='img_2_2' class='sort_arrow deactive_sort'><i class='ion ion-ios-arrow-up'></i></a>");
            sb.append("<a href=\"javascript: sortForm('2', '1');\" id='img_2_1' class='sort_arrow deactive_sort'><i class='ion ion-ios-arrow-down'></i></a>");
            sb.append("</th>");

            sb.append("<th width='14%'><span><b>Status</b> </span>");
            sb.append("<a href=\"javascript: sortForm('3', '2');\" id='img_3_2' class='sort_arrow deactive_sort'><i class='ion ion-ios-arrow-up'></i></a>");
            sb.append("<a href=\"javascript: sortForm('3', '1');\" id='img_3_1' class='sort_arrow deactive_sort'><i class='ion ion-ios-arrow-down'></i></a>");
            sb.append("</th>");

            sb.append("<th class='text-right' width='8%'><span><b>Actions</b></span></th>");
            sb.append("</tr>");
            sb.append("</thead>");
            String thead = sb.toString();
            sb.setLength(0);
            
            String search = request.getParameter("search") != null ? vobj.replacedesc(request.getParameter("search")) : "";
            String positionIdIndexs = request.getParameter("positionIdIndex") != null && !request.getParameter("positionIdIndex").equals("") ? vobj.replaceint(request.getParameter("positionIdIndex")) : "-1";
            String clientIdIndexs = request.getParameter("clientIdIndex") != null && !request.getParameter("clientIdIndex").equals("") ? vobj.replaceint(request.getParameter("clientIdIndex")) : "-1";
            String assetIdIndexs = request.getParameter("assetIdIndex") != null && !request.getParameter("assetIdIndex").equals("") ? vobj.replaceint(request.getParameter("assetIdIndex")) : "-1";
            String statusIndexs = request.getParameter("statusIndex") != null && !request.getParameter("statusIndex").equals("") ? vobj.replaceint(request.getParameter("statusIndex")) : "-1";
            int positionIdIndex = Integer.parseInt(positionIdIndexs);
            int clientIdIndex = Integer.parseInt(clientIdIndexs);
            int assetIdIndex = Integer.parseInt(assetIdIndexs);
            int statusIndex = Integer.parseInt(statusIndexs);            
            if (request.getParameter("deleteVal") == null)
            {
                StringBuilder str = new StringBuilder();
                ArrayList crewinsurance_list = crewinsurance.getCrewinsuranceByName(search, statusIndex,  positionIdIndex, clientIdIndex, assetIdIndex, allclient, per, cids, assetids);
                session.setAttribute("CREWINSURANCE_LIST", crewinsurance_list);
                int total = crewinsurance_list.size();
                if (total > 0)
                {
                    str.append("<div class='col-lg-12' id='printArea'>");
                    str.append("<div class='table-rep-plugin sort_table'>");
                    str.append("<div class='table-responsive mb-0' data-bs-pattern='priority-columns'>");
                    str.append("<table id='tech-companies-1' class='table table-striped'>");
                    str.append(thead);
                    str.append("<tbody id = 'sort_id'>");
                    CrewinsuranceInfo info;
                    for (int i = 0; i < total; i++) 
                    {
                        info = (CrewinsuranceInfo) crewinsurance_list.get(i);
                        if (info != null) 
                        {
                            str.append("<tr>");
                            str.append("<td>" + (info.getName() != null ? info.getName() : "") + "</td>");
                            str.append("<td>" + (info.getPosition() != null ? info.getPosition() : "") + "</td>");
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
                    str.append("</tbody>");
                    str.append("</table>");
                    str.append("</div>");
                    str.append("</div>");
                    str.append("</div>");
                } 
                else 
                {
                    str.append("<div class='panel-body'>");
                    str.append("<div class='table-responsive'>");
                    str.append("<table class='table table-bordered table-hover' id='dataTables-example'>");
                    str.append("<tbody>");
                    str.append("<tr class='grid1'>");
                    str.append("<td>" + crewinsurance.getMainPath("record_not_found") + "</td>");
                    str.append("</tr>");
                    str.append("</tbody>");
                    str.append("</table>");
                    str.append("</div>");
                    str.append("</div>");
                }
                String s1 = str.toString();
                str.setLength(0);
                response.getWriter().write(s1);
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