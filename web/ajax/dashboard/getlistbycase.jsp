<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.Collection"%>
<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@page import="com.web.jxp.user.UserInfo" %>
<%@page import="com.web.jxp.dashboard.DashboardInfo" %>
<jsp:useBean id="dashboard" class="com.web.jxp.dashboard.Dashboard" scope="page"/>
<jsp:useBean id="vobj" class="com.web.jxp.base.Validate" scope="page"/>
<%
    try
    {
        if (session.getAttribute("LOGININFO") != null) 
        {
            String str = "";            
            if (request.getParameter("clientIdIndex") != null && request.getParameter("assetIdIndex") != null) 
            {
            String types = request.getParameter("type") != null && !request.getParameter("type").equals("") ? vobj.replaceint(request.getParameter("type")) : "";
            int type = Integer.parseInt(types);
            response.setContentType("text/html");
            response.setHeader("Cache-Control", "no-cache");   
            DashboardInfo listinfo = null;
            if (session.getAttribute("DASH_CREWROTATIONULIST") != null) {
                listinfo = (DashboardInfo) session.getAttribute("DASH_CREWROTATIONULIST");
            }
            // datelist
            ArrayList datelist = new ArrayList();
            if (session.getAttribute("DASH_DATELIST") != null) {
                datelist = (ArrayList) session.getAttribute("DASH_DATELIST");
            }
            int datetotal = datelist.size();
            int datetotal1 = datelist.size();
            
            ArrayList candlist = new ArrayList();
            if (session.getAttribute("DASH_CREWROTATIONCLIST") != null) {
                candlist = (ArrayList) session.getAttribute("DASH_CREWROTATIONCLIST");
            }
            int candlisttotal = candlist.size();                        
                        
                        StringBuffer sb = new StringBuffer();
                            if(candlisttotal > 0){
                                        
                                
                                        String lastdate = "",datestatus = "";
                                        if(datetotal1 > 0){
                                        lastdate = (String) datelist.get(datetotal1 - 1);
                                        String currentdate = dashboard.currDate();
                                        if(dashboard.getDiffTwoDateInt(currentdate, lastdate, "yyyy-MM-dd") > 0)
                                            datestatus = currentdate;
                                        else
                                            datestatus = lastdate;
                                        }
                                        for(int i = 0 ;i < candlisttotal;i++){
                                        DashboardInfo caninfo = (DashboardInfo)candlist.get(i);
                                        if (caninfo != null) {
                                        int countstatus  = dashboard.checkwork((ArrayList)listinfo.getList4(), (ArrayList)listinfo.getList3(),(ArrayList)listinfo.getList2(), (ArrayList)listinfo.getList1(), datestatus, (ArrayList)listinfo.getList5(), (ArrayList)listinfo.getList6(), (ArrayList)listinfo.getList7(), (ArrayList)listinfo.getList8(), (ArrayList)listinfo.getList9(), caninfo.getCandidateId(), caninfo.getPositionId()) ;
                                                                        
                                        if((type == 4 && (countstatus == 0 || countstatus == 4)) ||(type == 3 && countstatus == 3) || (type == 2 && countstatus == 2) || (type == 1 && countstatus == 1) || (type == -1))
                                        {
                                         sb.append("<tr>");
                                        sb.append("<td class='td_bg_white sticky-col first_col'>"+caninfo.getPosition()+"</td>");
                                        sb.append("<td class='td_bg_white sticky-col second_col'>"+caninfo.getCandidateName()+"</td>");
                                        sb.append("<td class='td_bg_white sticky-col third_col text-center'>"+ caninfo.getRotations()+"</td>");
                                        sb.append("<td class='td_bg_white sticky-col fourth_col text-center'><span class='round_circle ");
                                        if(caninfo.getStatus() == 1){
                                            sb.append("signoff_circle");
                                            }else{
                                            sb.append("normal_circle");}
                                        sb.append("'></span></td>");

                                        if(datetotal > 0){
                                        for(int j = 0 ;j < datetotal;j++){
                                        String date = (String)datelist.get(j);

                                        if (date != null && listinfo != null) {
                                        int daystatus = dashboard.checkwork((ArrayList)listinfo.getList4(), (ArrayList)listinfo.getList3(),(ArrayList)listinfo.getList2(), (ArrayList)listinfo.getList1(), date, (ArrayList)listinfo.getList5(), (ArrayList)listinfo.getList6(), (ArrayList)listinfo.getList7(), (ArrayList)listinfo.getList8(), (ArrayList)listinfo.getList9(), caninfo.getCandidateId(), caninfo.getPositionId()) ;
                                        
                                        sb.append("<td class='");
                                        if( daystatus == 10) 
                                            sb.append("standby_label");
                                        else if( daystatus == 8) 
                                            sb.append("training_circle");
                                        else if( daystatus == 9) 
                                            sb.append("temp_pro_circle");                                        
                                        else if(daystatus == 1) 
                                            sb.append("w_label");
                                        else if(daystatus == 2)
                                            sb.append("yellow_sf_label");
                                        else if(daystatus == 3)
                                            sb.append("orange_sf_label");
                                        else if(daystatus == 4)
                                            sb.append("sf_label hand_cursor");
                                        else if(daystatus == 5)
                                            sb.append("w_early_label");
                                        else if(daystatus == 7)
                                            sb.append("w_delayed_label");
                                        else if(daystatus == 6)
                                            sb.append("planning_label");                                                    
                                        sb.append("'");
                                        if(daystatus == 4){
                                            sb.append(" data-bs-toggle=\"modal\" data-bs-target='#sf_modal' onclick = \"javascript: setsfmodal('"+caninfo.getCrewrotationId()+"','"+ date+"');\" ");
                                        }
                                        else if(daystatus == 5){
                                            sb.append(" data-bs-toggle=\"modal\" data-bs-target=\"#sf_modal\" onclick = \"javascript: setearlymodal('"+caninfo.getCrewrotationId()+"','"+ date+"');\" ");
                                        }
                                        else if(daystatus == 7){
                                            sb.append(" data-bs-toggle=\"modal\" data-bs-target=\"#sf_modal\" onclick = \"javascript: setearlymodal('"+caninfo.getCrewrotationId()+"','"+date+"');\" ");
                                        }else if(daystatus == 8){
                                            sb.append(" data-bs-toggle=\"modal\" data-bs-target=\"#sf_modal\" onclick =\"javascript: settrainingmodal('"+caninfo.getCrewrotationId()+"','"+date+"');\" ");
                                        }else if(daystatus == 9){
                                        sb.append(" data-bs-toggle=\"modal\" data-bs-target=\"#sf_modal\" onclick =\"javascript: setTpmodal('"+caninfo.getCrewrotationId()+"','"+date+"');\" ");
                                        }else if(daystatus == 10){
                                        sb.append(" data-bs-toggle=\"modal\" data-bs-target=\"#sf_modal\" onclick =\"javascript: setSbymodal('"+caninfo.getCrewrotationId()+"','"+date+"');\" ");
                                        }
                                        sb.append(">");
                                        if(daystatus == 1 || daystatus == 2)
                                            sb.append("W");
                                        else if (daystatus == 5)
                                            sb.append("R");
                                        else if (daystatus == 7)
                                            sb.append("D");
                                        else if(daystatus == 4)
                                            sb.append("SF");
                                        else if(daystatus == 6)
                                            sb.append("P");
                                        else if(daystatus == 8)
                                            sb.append("TR");
                                        else if(daystatus == 9)
                                            sb.append("TP"); 
                                        else if(daystatus == 10)
                                            sb.append("SB"); 
                                        else
                                            sb.append("&nbsp;");
                                        sb.append("</td>");
                                    }

                                }
                            }
                        sb.append("</tr>");
                    }
                }                              
            }
        }
        else 
        {
            sb.append("<tr><td colspan='4'>No data available</td></tr>");
        }
        str = sb.toString();
        sb.setLength(0);
        response.getWriter().write(str);
    } 
    else 
    {
        response.setStatus(HttpServletResponse.SC_NO_CONTENT);
    }           
    }
    else 
    {
        response.setStatus(HttpServletResponse.SC_NO_CONTENT);
    }
} 
catch (Exception e) 
{
}
%>