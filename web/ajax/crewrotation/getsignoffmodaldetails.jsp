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
            String noofdayss = request.getParameter("noofdays") != null && !request.getParameter("noofdays").equals("") ? vobj.replaceint(request.getParameter("noofdays")) : "0";
            String crewrotas = request.getParameter("crewrota") != null && !request.getParameter("crewrota").equals("") ? vobj.replaceint(request.getParameter("crewrota")) : "0";
            int crewrotationId = Integer.parseInt(crewrotationIds);
            int noofdays = Integer.parseInt(noofdayss);
            int crewrota = Integer.parseInt(crewrotas);
            String date = "", time = "";
            int pid = 0;
            CrewrotationInfo info = crewrotation.getCrewrotationBycrewrotationId(crewrotationId);
            CrewrotationInfo info2 = crewrotation.getPositionIdForSignoff(crewrotationId);
            if(info2 != null)
            {
                pid = info2.getPositionId();
            }
            int status = info.getStatus();
            String currentdate = crewrotation.currDatef();
            int tempdate = 0;
            if (status == 1) {
                date = currentdate;
                String currenttime = crewrotation.currtime();
                time = currenttime;
                if (!info.getExpecteddate().equals("")) {
                    String d[] = info.getExpecteddate().split(" ");
                    date = d[0];
                    time = d[1];
                } else if (!info.getSignoff().equals("")) {
                    String d[] = info.getSignoff().split(" ");
                    date = d[0];
                    time = d[1];
                }
            } else if (status == 2) {
                if (!info.getExpecteddate().equals("")) {
                    String d[] = info.getExpecteddate().split(" ");
                    date = d[0];
                    time = d[1];
                } else if (!info.getSignoff().equals("")) {

                    String d[] = info.getSignoff().split(" ");
                    date = d[0];
                    time = d[1];
                } else if (info.getExpecteddate().equals("") && crewrota != 2) {
                    tempdate = 1;
                }
            }

            StringBuilder sb = new StringBuilder();
            sb.append("<h2>SIGN OFF DETAILS</h2>");
            sb.append("<input type='hidden' name='positionIdSignOff' id='positionIdSignOff' value='"+ pid + "' />");
            sb.append("<div class='row client_position_table'>");
            sb.append("<div class='col-md-6 form_group'>");
            sb.append("<div class='input-group'>");
            sb.append("<input type='text' name='edate' value='" + (crewrota == 2 ? currentdate : date) + "' id='startdate' class='form-control add-style date-add text-center " + (crewrota == 2 || tempdate == 1 ? "wesl_dt" : "") + "' placeholder='DD-MMM-YYYY' readonly/>");
            sb.append("</div>");
            sb.append("</div>");
            sb.append("<div class='col-md-6 form_group'>");
            sb.append("<div class='input-group'>");
            sb.append("<input type='text' name='etime'  id='starttime' class='form-control timepicker timepicker-24  text-center' value='" + time + "'/>");
            sb.append("</div>");
            sb.append("</div>");

            sb.append("<div class='form-group' id='' style=''>");
            sb.append("<div class='mt-radio-inline'>");
            if (crewrota == 2) {
                sb.append("<input type='hidden' name='rdateId' value='1'/>");
            } else {
                sb.append("<label class='mt-radio'>");
                sb.append("<input type='radio' name='rdateId' id='ondate' value='1' onChange = ' javascript: setSuggestedDate();' checked/>Normal<span></span>");
                sb.append("</label>");
                sb.append("<label class='mt-radio'>");
                sb.append("<input type='radio' name='rdateId' id='ersignoff' value='2' onChange = ' javascript: setSuggestedDate();'/> Early Sign Off<span></span>");
                sb.append("</label>");
                sb.append("<label class='mt-radio'>");
                sb.append("<input type='radio' name='rdateId' id='exsignoff' value='3' onChange = ' javascript: setSuggestedDate();'/> Extend Sign Off<span></span>");
                sb.append("</label>");
            }
            sb.append("</div>");
            sb.append("</div>");
            
            sb.append("<div class='col-lg-12 col-md-12 col-sm-12 col-4 form_group' style ='display: none' id='reasonDiv'>");
            sb.append("<select class='form-select' name='reasonsDDL' id='reasonsDDL' onchange='javascript: showTextArea();'>");
            sb.append("<option value=''>- Select Reason-</option>");
            sb.append("<option value='Reported helibase not joined'>Reported helibase not joined</option>");
            sb.append("<option value='Other medical reasons not joined'>Other medical reasons not joined</option>");
            sb.append("<option value='Others compensatory off not joined'>Others compensatory off not joined</option>");
            sb.append("<option value='Chopper Cancellation/Unavailability'>Chopper Cancellation/Unavailability</option>");
            sb.append("<option value='Others'>Others</option>");
            sb.append("</select>");
            sb.append("</div>");

            sb.append("<div class='col-lg-12 col-md-12 col-sm-12 col-4 form_group' id='dreasons'>");
            sb.append("<label class='form_label' id = 'reasonId'>Remarks</label>");
            sb.append("<textarea name='reasons' class='form-control' rows='6' maxlength='500'></textarea>");
            sb.append("</div>");

            sb.append("<div class='col-md-12' id='dvPinNo' style='display: none'>");
            sb.append("<div class='row'>");
            sb.append("<div class='col-md-6 form_group'>");
            sb.append("<label class='form_label'>Suggested Date</label>");
            sb.append("<div class='input-daterange input-group'>");
            sb.append("<input type='text' name='sdate' value='" + date + "' id='startdate' class='form-control add-style wesl_dt date-add text-center' placeholder='DD-MMM-YYYY'>");
            sb.append("</div>");
            sb.append("</div>");
            sb.append("<div class='col-md-6 form_group'>");
            sb.append("<label class='form_label'>Suggested Time</label>");
            sb.append("<div class='input-group'>");
            sb.append("<input type='text' name='stime'  class='form-control timepicker timepicker-24 text-center' value='" + time + "'>");
            sb.append("</div>");
            sb.append("</div>");
            sb.append("</div>");
            sb.append("</div>");
            sb.append("</div>");
            sb.append("<div class='row'>");
            sb.append("<div class='col-lg-12 col-md-12 col-sm-12 col-12 text-center' id = 'savesignoffdiv'>");
            sb.append("<a href=\"javascript: submitsignoffForm(" + crewrotationId + "," + noofdays + ");\" class='save_page'><img src='../assets/images/save.png'> Save</a>");
            sb.append("</div>");
            sb.append("</div>");

            String st1 = sb.toString() + "#@#" + "";
            sb.setLength(0);
            response.getWriter().write(st1);
        } else {
            response.getWriter().write("Please check your login session....");
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
%>