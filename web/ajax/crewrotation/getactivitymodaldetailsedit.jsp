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
            String statuss = request.getParameter("status") != null && !request.getParameter("status").equals("") ? vobj.replaceint(request.getParameter("status")) : "0";
            String cractivityIds = request.getParameter("cractivityId") != null && !request.getParameter("cractivityId").equals("") ? vobj.replaceint(request.getParameter("cractivityId")) : "0";
            int cractivityId = Integer.parseInt(cractivityIds);
            int crewrotationId = Integer.parseInt(crewrotationIds);
            int status = Integer.parseInt(statuss);
            
            int positionId2 = 0, positionId1=0 ;
            String position1 = "", position2 = "";
            CrewrotationInfo info1 = crewrotation.getCrewrotationBycrewrotationId(crewrotationId);
            if(info1 != null)
            {
               positionId1 = info1.getPositionId();
               positionId2 = info1.getPositionId2();
               position1 = info1.getPosition() != null ? info1.getPosition(): "";
               position2 = info1.getPosition2() != null ? info1.getPosition2(): "";
            }
            
            if(cractivityId > 0)
            {
                CrewrotationInfo info = crewrotation.getActivitydetailbycractivityId(crewrotationId, cractivityId);
                Collection positions = crewrotation.getPositionList(info.getClientassetId());
                StringBuilder sb = new StringBuilder();
                sb.append("<h2>SCHEDULE ACTIVITY</h2>");            
                sb.append("<div class='row client_position_table'>");
                sb.append("<div class='col-md-12 form_group'>");
                sb.append("<label class='form_label'>Activity</label>");
                sb.append("<select name ='activityId' id = 'activityId' class='form-select form-control' onchange = 'javascript: showhidetp();'>");
                sb.append("<option value=''>--Select here --</option>");
                if(status == 1)
                {
                    sb.append("<option value='1' ");
                    if(info.getActivityId() == 1)
                        sb.append(" selected "); 
                    sb.append(" >Office Work</option>");
                    sb.append("<option value='2'  ");
                    if(info.getActivityId() == 2)
                        sb.append(" selected "); 
                    sb.append(" >Training</option>");            
                }
                else
                {
                    sb.append("<option value='2'  ");
                    if(info.getActivityId() == 2)
                        sb.append(" selected "); 
                    sb.append(" >Training</option>");
                    sb.append("<option value='7'  ");
                    if(info.getActivityId() == 7)
                        sb.append(" selected "); 
                    sb.append(" >Temporary Promotion</option>");
                    sb.append("<option value='8'  ");
                    if(info.getActivityId() == 8)
                        sb.append(" selected "); 
                    sb.append(" >Standby</option>");
                }                                           
                sb.append("</select>");
                sb.append("</div>");
                
                if(info.getActivityId() == 7)
                {
                    sb.append("<div class='col-md-12 form_group' id='prom_position' style=''>");
                    sb.append("<label class='form_label'>Promoted to Position</label>");
                    sb.append("<select name = 'positionId' class='form-select form-control'>");
                    if (positions.size() > 0)
                    {
                        Iterator iter = positions.iterator();
                        while (iter.hasNext()) 
                        {
                            CrewrotationInfo infop = (CrewrotationInfo) iter.next();
                            int val = infop.getDdlValue();
                            String name = infop.getDdlLabel() != null ? infop.getDdlLabel() : "";
                            sb.append("<option value='" + val + "'");
                            if (info.getPositionId2()== val) {
                                sb.append(" selected ");
                            }
                            sb.append(">" + name + "</option>");
                        }
                    }
                    sb.append("</select>");
                    sb.append("</div>");
                }
                else if(info.getActivityId() != 7)
                {                
                    sb.append("<div class='col-lg-12 col-md-12 col-sm-12 col-4 form_group' >");
                    sb.append("<select class='form-select' name='positionIdActivity' id='positionIdActivity' >");            
                    if(info.getPositionId() == positionId1)
                    {
                        sb.append("<option value='"+positionId1+"' ");
                            sb.append(" selected "); 
                        sb.append(" >"+position1+"</option>");
                        if(positionId2 > 0)
                            sb.append("<option value='"+positionId2+"' ");
                                sb.append(" select "); 
                            sb.append(" >"+position2+"</option>");
                    }
                    else if(info.getPositionId() == positionId2)
                    {
                        sb.append("<option value='"+positionId2+"' ");
                            sb.append(" selected "); 
                        sb.append(" >"+position2+"</option>");
                        if(positionId1 > 0)
                            sb.append("<option value='"+positionId1+"' ");
                                sb.append(" select "); 
                            sb.append(" >"+position1+"</option>");
                    }
                    sb.append("</select>");
                    sb.append("</div>");  
                }

                sb.append("<div class='col-md-6 form_group'>");
                sb.append("<label class='form_label'>From</label>");
                sb.append("<div class='input-daterange input-group'>");
                sb.append("<input type='text' name='astartdate' value='"+info.getFromdate()+"' id='startdate' class='form-control add-style wesl_dt date-add text-center' placeholder='DD-MMM-YYYY'>");
                sb.append("</div>");
                sb.append("</div>");
                sb.append("<div class='col-md-6 form_group'>");
                sb.append("<label class='form_label'>To</label>");
                sb.append("<div class='input-daterange input-group'>");
                sb.append("<input type='text' name='aenddate' value='"+info.getTodate()+"' id='startdate' class='form-control add-style wesl_dt date-add text-center' placeholder='DD-MMM-YYYY'>");
                sb.append("</div>");
                sb.append("</div>");

                sb.append("<div class='col-lg-12 col-md-12 col-sm-12 col-4 form_group'>");
                sb.append("<label class='form_label'>Remarks</label>");
                sb.append("<textarea name='remarks' class='form-control' rows='6'>"+info.getRemarks()+"</textarea>");
                sb.append("</div>");

                sb.append("</div>");
                sb.append("<div class='row'>");
                sb.append("<div class='col-lg-12 col-md-12 col-sm-12 col-12 text-center' id = 'saveditactivitydiv'>");
                sb.append("<a href=\"javascript: submitupdateactivityForm("+crewrotationId+","+cractivityId+");\" class='save_page'><img src='../assets/images/save.png'> Save</a>");
                sb.append("</div>");
                sb.append("</div>");

                String st1 = sb.toString() + "#@#" +"";
                sb.setLength(0);
                response.getWriter().write(st1);
            }
        } else {
            response.getWriter().write("Please check your login session....");
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
%>