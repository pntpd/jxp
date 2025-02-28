<%@page import="java.util.Iterator"%>
<%@page import="java.util.Collection"%>
<%@page import="com.web.jxp.crewrotation.CrewrotationInfo"%>
<%@page contentType="text/html"%>
<%@page language="java" import="java.util.ArrayList"%>
<%@page pageEncoding="UTF-8"%>
<%@page import="com.web.jxp.user.UserInfo"%>
<jsp:useBean id="crewrotation" class="com.web.jxp.crewrotation.Crewrotation" scope="page"/>
<jsp:useBean id="vobj" class="com.web.jxp.base.Validate" scope="page"/>
<%
    try 
    {
        if (session.getAttribute("LOGININFO") != null) 
        {
            String crewrotationIds = request.getParameter("crewrotationId") != null && !request.getParameter("crewrotationId").equals("") ? vobj.replaceint(request.getParameter("crewrotationId")) : "0";
            String clientassetIds = request.getParameter("clientassetId") != null && !request.getParameter("clientassetId").equals("") ? vobj.replaceint(request.getParameter("clientassetId")) : "0";
            String statuss = request.getParameter("status") != null && !request.getParameter("status").equals("") ? vobj.replaceint(request.getParameter("status")) : "0";
            int crewrotationId = Integer.parseInt(crewrotationIds);
            int clientassetId = Integer.parseInt(clientassetIds);
            int status = Integer.parseInt(statuss);
            int positionId2 = 0, positionId1=0, pid = 0;
            String position1 = "", position2 = "";
            
            CrewrotationInfo info1 = crewrotation.getCrewrotationBycrewrotationId(crewrotationId);
            CrewrotationInfo info2 = crewrotation.getPositionIdForSignoff(crewrotationId);
            if(info2 != null)
            {
                pid = info2.getPositionId();
            }
            if(info1 != null)
            {
               positionId1 = info1.getPositionId();
               positionId2 = info1.getPositionId2();
               position1 = info1.getPosition() != null ? info1.getPosition(): "";
               position2 = info1.getPosition2() != null ? info1.getPosition2(): "";
            }
            Collection positions = crewrotation.getPositionList(clientassetId);            
            StringBuilder sb = new StringBuilder();
            sb.append("<h2>SCHEDULE ACTIVITY</h2>"); 
            sb.append("<div class='row client_position_table'>");
            sb.append("<div class='col-md-12 form_group'>");
            sb.append("<label class='form_label'>Activity</label>");
            sb.append("<select name ='activityId' id = 'activityId' class='form-select form-control' onchange = 'javascript: showhidetp();'>");
            sb.append("<option value=''>- Select here -</option>");
            if(status == 1)
            {
                sb.append("<option value='1'>Office Work</option>");
                sb.append("<option value='2'>Training</option>");
            }
            else
            {
                sb.append("<option value='2'>Training</option>");
                sb.append("<option value='7'>Temporary Promotion</option>");
                sb.append("<option value='8'>Standby</option>");
            }
            sb.append("</select>");
            sb.append("</div>");
            if(pid <= 0)
            {
                if(positionId1> 0)
                {
                    if(pid <= 0 )
                    {
                        sb.append("<div class='col-lg-12 col-md-12 col-sm-12 col-4 form_group' id='positionIdActivityDiv'>");
                        sb.append("<select class='form-select' name='positionIdActivity' id='positionIdActivity' >");
                        sb.append("<option value='"+positionId1+"'>"+position1+"</option>");
                        if(positionId2 > 0)
                            sb.append("<option value='"+positionId2+"'>"+position2+"</option>");
                        sb.append("</select>");
                        sb.append("</div>");
                    }
                }
            }
            if(info1.getSignoffId() <= 0 && info1.getSignonId() > 0)
            {
                sb.append("<input type='hidden' name='positionIdSignOff' id='positionIdSignOff' value='"+ info2.getPositionId()+ "' />");
            }else if(pid > 0)
            {
                sb.append("<input type='hidden' name='positionIdActivity' id='positionIdActivity' value='"+ pid+ "' />");
            }
            sb.append("<div class='col-md-12 form_group' id='prom_position' style=''>");
            sb.append("<label class='form_label'>Promoted to Position</label>");
            sb.append("<select name = 'positionId' class='form-select form-control'>");
            if (positions.size() > 0)
            {
                Iterator iter = positions.iterator();
                while (iter.hasNext())
                {
                    CrewrotationInfo info = (CrewrotationInfo) iter.next();
                    if(info != null)
                    {
                        int val = info.getDdlValue();
                        String name = info.getDdlLabel() != null ? info.getDdlLabel() : "";
                        sb.append("<option value='" + val + "'>" + name + "</option>");
                    }
                }
            }
            sb.append("</select>");
            sb.append("</div>");
            sb.append("<div class='col-md-6 form_group'>");
            sb.append("<label class='form_label'>From</label>");
            sb.append("<div class='input-daterange input-group'>");
            sb.append("<input type='text' name='astartdate' value='' id='startdate' class='form-control add-style wesl_dt date-add text-center' placeholder='DD-MMM-YYYY'>");
            sb.append("</div>");
            sb.append("</div>");
            sb.append("<div class='col-md-6 form_group'>");
            sb.append("<label class='form_label'>To</label>");
            sb.append("<div class='input-daterange input-group'>");
            sb.append("<input type='text' name='aenddate' value='' id='startdate' class='form-control add-style wesl_dt date-add text-center' placeholder='DD-MMM-YYYY'>");
            sb.append("</div>");
            sb.append("</div>");            
            
            sb.append("<div class='col-lg-12 col-md-12 col-sm-12 col-4 form_group'>");
            sb.append("<label class='form_label'>Remarks</label>");
            sb.append("<textarea name='remarks' class='form-control' rows='6'></textarea>");
            sb.append("</div>");

            sb.append("</div>");
            sb.append("<div class='row'>");
            sb.append("<div class='col-lg-12 col-md-12 col-sm-12 col-12 text-center' id = 'saveactivitydiv'>");
            sb.append("<a href=\"javascript: submitactivityForm("+crewrotationId+","+status+" );\" class='save_page'><img src='../assets/images/save.png'> Save</a>");
            sb.append("</div>");
            sb.append("</div>");
            
            String st1 = sb.toString() + "#@#" +"";
            sb.setLength(0);
            response.getWriter().write(st1);
        } else {
            response.getWriter().write("Please check your login session....");
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
%>