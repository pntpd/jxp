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
            UserInfo uinfo = ((UserInfo) request.getSession().getAttribute("LOGININFO"));
            String editper = "N";
            if (uinfo != null)
            {
                editper = uinfo.getEditper() != null ? uinfo.getEditper() : "N";
            }
            String crewrotationIds = request.getParameter("crewrotationId") != null && !request.getParameter("crewrotationId").equals("") ? vobj.replaceint(request.getParameter("crewrotationId")) : "0";
            String cractivityIds = request.getParameter("cractivityId") != null && !request.getParameter("cractivityId").equals("") ? vobj.replaceint(request.getParameter("cractivityId")) : "0";
            String statuss = request.getParameter("status") != null && !request.getParameter("status").equals("") ? vobj.replaceint(request.getParameter("status")) : "0";
            int crewrotationId = Integer.parseInt(crewrotationIds);
            int cractivityId = Integer.parseInt(cractivityIds);
            int status = Integer.parseInt(statuss);
            if(cractivityId > 0)
            {
                CrewrotationInfo info = crewrotation.getActivitydetailbycractivityId(crewrotationId, cractivityId);
                StringBuilder sb = new StringBuilder();
                sb.append("<h2>SCHEDULE ACTIVITY</h2>");

                sb.append("<div class='row client_position_table'>");
                sb.append("<div class='col-md-12 form_group'>");
                sb.append("<label class='form_label'>Activity</label>");
                sb.append("<td><span class='form-control'>"+info.getActivitystatus()+"</span></td>");

                if(info.getActivityId()== 7 && info.getPositionId2()>0)
                {
                    sb.append("<label class='form_label'>Position</label>");
                    sb.append("<td><span class='form-control'>"+info.getPosition2()+"</span></td>");
                }   else{
                    sb.append("<label class='form_label'>Position</label>");
                    sb.append("<td><span class='form-control'>"+info.getPosition()+"</span></td>");
                }                     
                sb.append("</select>");
                sb.append("</div>");
                sb.append("<div class='col-md-6 form_group'>");
                sb.append("<label class='form_label'>From</label>");
                sb.append("<td><span class='form-control'>"+info.getFromdate()+"</span></td>");
                sb.append("</div>");
                sb.append("<div class='col-md-6 form_group'>");
                sb.append("<label class='form_label'>To</label>");
                sb.append("<td><span class='form-control'>"+info.getTodate()+"</span></td>");
                sb.append("</div>");

                sb.append("<div class='col-lg-12 col-md-12 col-sm-12 col-4 form_group'>");
                sb.append("<label class='form_label'>Remarks</label>");
                sb.append("<textarea name='remarks' class='form-control' rows='6' readonly>"+info.getRemarks()+"</textarea>");
                sb.append("</div>");
                sb.append("</div>");
                if(editper.equals("Y") && info.getActiveflag()==1){
                sb.append("<div class='row'>");
                sb.append("<div class='col-lg-12 col-md-12 col-sm-12 col-12 text-center'>");
                sb.append("<a href='javascript: callactivityedit("+crewrotationId+","+status+","+cractivityId+");'  class='edit_page'><img src='../assets/images/edit.png'></a>");
                sb.append("</div>");
                sb.append("</div>");
                }
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