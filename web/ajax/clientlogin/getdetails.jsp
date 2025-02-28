<%@page import="com.web.jxp.clientlogin.ClientloginInfo"%>
<%@page contentType="text/html"%>
<%@page language="java" import="java.util.ArrayList"%>
<%@page pageEncoding="UTF-8"%>
<%@page import="com.web.jxp.compliancecheck.CompliancecheckInfo"%>
<%@page import="com.web.jxp.user.UserInfo"%>
<jsp:useBean id="clientlogin" class="com.web.jxp.clientlogin.Clientlogin" scope="page"/>
<jsp:useBean id="vobj" class="com.web.jxp.base.Validate" scope="page"/>
<%
    try 
    {
        if (session.getAttribute("MLOGININFO") != null) 
        {
            StringBuilder sb = new StringBuilder();
            if (request.getParameter("shortlistId") != null)
            {
                String shortlistIds = request.getParameter("shortlistId") != null && !request.getParameter("shortlistId").equals("") ? vobj.replaceint(request.getParameter("shortlistId")) : "0";
                int shortlistId = Integer.parseInt(shortlistIds);
                ClientloginInfo info = clientlogin.getDetails(shortlistId);
                int candidateId = 0, assetId = 0, positionId= 0, clientId = 0;
                if(info != null)
                {
                    candidateId = info.getCandidateId();
                    assetId = info.getAssetId();
                    clientId = info.getClientId();
                    positionId = info.getPositionId();
                }
                ArrayList tzList = clientlogin.getTimezone();
                sb.append("<h2>SCHEDULE INTERVIEW</h2>");
                sb.append("<input type='hidden' name='candidateId' value='"+candidateId+"'>");
                sb.append("<input type='hidden' name='assetId' value='"+assetId+"'>");
                sb.append("<input type='hidden' name='shortlistId' value='"+shortlistId+"'>");
                sb.append("<input type='hidden' name='positionId' value='"+positionId+"'>");
                sb.append("<input type='hidden' name='clientId' value='"+clientId+"'>");
                sb.append("<div class='col-lg-12'>");
                sb.append("<div class='row'>");
                sb.append("<div class='col-lg-6 col-md-6 col-sm-6 col-6 form_group'>");
                sb.append("<label class='form_label'>Date</label>");
                sb.append("<div class='input-daterange input-group'>");
                sb.append("<input type='text' name='txtstartdate' value='' id='txtstartdate' class='form-control add-style wesl_dt date-add' placeholder='DD-MMM-YYYY' maxlength='11' onchange='getChgedatetime();' onfocus='getChgedatetime();' onblur='getChgedatetime();' autocomplete='off'/>");
                sb.append("</div>");
                sb.append("</div>");                

                sb.append("<div class='col-lg-6 col-md-6 col-sm-6 col-6 form_group'>");
                sb.append("<label class='form_label'>Mode</label>");
                sb.append("<select class='form-select' onchange='getonoffvalue();' id='selMode' name='selMode'>");
                sb.append("<option value='mode'>- Select -</option>");
                sb.append("<option value='Online'");
                if ("Online".equalsIgnoreCase(info.getMode())) {
                    sb.append("selected");
                }
                sb.append(">Online</option>");
                sb.append("<option value='Offline'");
                if ("Offline".equalsIgnoreCase(info.getMode())) {
                    sb.append("selected");
                }
                sb.append(">Offline</option>");
                sb.append("</select>");
                sb.append("</div>");
                
                sb.append("<div class='col-lg-6 col-md-6 col-sm-6 col-6 form_group'>");
                sb.append("<label class='form_label'>Duration (Minutes)</label>");
                sb.append("<select class='form-select' id='selDuration' name='selDuration'>");
                sb.append("<option value='-1'>- Select -</option>");
                sb.append("<option value='15'>15</option>");
                sb.append("<option value='30'>30</option>");
                sb.append("<option value='45'>45</option>");
                sb.append("<option value='60'>60</option>");
                sb.append("</select>");
                sb.append("</div>");
                sb.append("<div class='col-lg-6 col-md-6 col-sm-6 col-6 form_group'>");
                sb.append("<label class='form_label'>Time</label>");
                sb.append("<div class='input-group'>");
                sb.append("<input type='text' class='form-control timepicker timepicker-24' name='txttime' value='' id='txttime' maxlength='5' onblur='getChgedatetime();' onchange='getChgedatetime();'/>");
                sb.append("</div>");
                sb.append("</div>");
                sb.append("<div class='col-lg-6 col-md-6 col-sm-6 col-12 form_group'>");
                sb.append("<label class='form_label'>Interviewer</label>");
                sb.append("<select class='form-select' id='selInterviewer' name='selInterviewer'>");
                sb.append("<option value='-1'>- Select -</option>");
                sb.append(clientlogin.getManagerList(0));
                sb.append("</select>");
                sb.append("</div>");
                sb.append("<div class='col-lg-6 col-md-6 col-sm-6 col-12 form_group'>");
                sb.append("<label class='form_label'>Interviewer Country - Time Zone</label>");
                sb.append("<select class='form-select' id='selInterviewerTimeZone' name='selInterviewerTimeZone' onchange='getChgedatetime();'>");
                sb.append(clientlogin.getStringFromList(tzList, 0));
                sb.append("</select>");
                sb.append("</div>");
                sb.append("<div class='col-lg-6 col-md-6 col-sm-6 col-12 form_group'>");
                sb.append("<label class='form_label'>Candidate Country - Time Zone</label>");
                sb.append("<select class='form-select' id='selCandTimeZone' name='selCandTimeZone' onchange='getChgedatetime();'>");
                sb.append(clientlogin.getStringFromList(tzList, 0));
                sb.append("</select>");
                sb.append("<label class='form_label date_time_label'>Candidate Date & Time</label>");
                sb.append("<span class='date_time' id='sDate'></span>");
                sb.append("<input type='hidden' class='form-control' id='txtDate' name='txtDate' value=''/>");
                sb.append("</div>");
                
                
                sb.append("<div class='col-lg-6 col-md-6 col-sm-6 col-12 form_group'>");
                sb.append("<label class='form_label' id='onofflbl'>");
                if ("Online".equalsIgnoreCase(info.getMode())) {
                    sb.append("Link");
                } else if ("Offline".equalsIgnoreCase(info.getMode())) {
                    sb.append("Location");
                }
                sb.append("</label>");
                sb.append("<textarea id='txtLocLink' name='txtLocLink' class='form-control' rows='3' maxlength='500'></textarea>");
                sb.append("</div>");
                sb.append("</div>");
                sb.append("</div>");
                sb.append("</div>");
                sb.append("</div>");

                sb.append("</div>");
                sb.append("<div class='row'>");
                sb.append("<div class='col-lg-12 col-md-12 col-sm-12 col-12 text-center'  id='submitdiv'>");
                sb.append("<a href=\"javascript: ;\" onclick=\"javascript: saveInterview();\" class='save_page'>Save &nbsp;&nbsp;<img src='../assets/images/save.png'></a>");
                sb.append("</div>");
                sb.append("</div>");
                
            }else if (request.getParameter("interviewId") != null)
            {
                String interviewIds = request.getParameter("interviewId") != null && !request.getParameter("interviewId").equals("") ? vobj.replaceint(request.getParameter("interviewId")) : "0";
                int interviewId = Integer.parseInt(interviewIds);
                ClientloginInfo einfo = clientlogin.editInterview(interviewId);
                ArrayList tzList = clientlogin.getTimezone();
                sb.append("<h2>RE-SCHEDULE INTERVIEW</h2>");
                sb.append("<input type='hidden' name='interviewId' value='"+interviewId+"'>");
                sb.append("<input type='hidden' name='shortlistId' value='"+einfo.getShortlistId()+"'>");
                sb.append("<input type='hidden' name='assetId' value='"+einfo.getClientassetId()+"'>");
                sb.append("<input type='hidden' name='positionId' value='"+einfo.getPositionId()+"'>");
                
                sb.append("<div class='col-lg-12'>");
                sb.append("<div class='row'>");
                sb.append("<div class='col-lg-6 col-md-6 col-sm-6 col-6 form_group'>");
                sb.append("<label class='form_label'>Date</label>");
                sb.append("<div class='input-daterange input-group'>");
                sb.append("<input type='text' name='txtstartdate' value='" + einfo.getDate() + "' id='txtstartdate' class='form-control add-style wesl_dt date-add' placeholder='DD-MMM-YYYY' maxlength='11'  onchange='getChgedatetime();' onfocus='getChgedatetime();' onblur='getChgedatetime();' autocomplete='off' />");
                sb.append("</div>");
                sb.append("</div>");

                sb.append("<div class='col-lg-6 col-md-6 col-sm-6 col-6 form_group'>");
                sb.append("<label class='form_label'>Mode</label>");
                sb.append("<select class='form-select' onchange='getonoffvalue();' id='selMode' name='selMode'>");
                sb.append("<option value='mode'>- Select -</option>");
                sb.append("<option value='Online'");
                if ("Online".equalsIgnoreCase(einfo.getMode())) {
                    sb.append("selected");
                }
                sb.append(">Online</option>");
                sb.append("<option value='Offline'");
                if ("Offline".equalsIgnoreCase(einfo.getMode())) {
                    sb.append("selected");
                }
                sb.append(">Offline</option>");
                sb.append("</select>");
                sb.append("</div>");
                
                sb.append("<div class='col-lg-6 col-md-6 col-sm-6 col-6 form_group'>");
                sb.append("<label class='form_label'>Duration (Minutes)</label>");
                sb.append("<select class='form-select' id='selDuration' name='selDuration'>");
                sb.append("<option value='-1'>- Select -</option>");
                sb.append("<option value='15'");
                if (einfo.getDuration() == 15) {
                    sb.append("selected");
                }
                sb.append(">15</option>");
                sb.append("<option value='30'");
                if (einfo.getDuration() == 30) {
                    sb.append("selected");
                }
                sb.append(">30</option>");
                sb.append("<option value='45'");
                if (einfo.getDuration() == 45) {
                    sb.append("selected");
                }
                sb.append(">45</option>");
                sb.append("<option value='60'");
                if (einfo.getDuration() == 60) {
                    sb.append("selected");
                }
                sb.append(">60</option>");
                sb.append("</select>");
                sb.append("</div>");
                sb.append("<div class='col-lg-6 col-md-6 col-sm-6 col-6 form_group'>");
                sb.append("<label class='form_label'>Time</label>");
                sb.append("<div class='input-group'>");
                sb.append("<input type='text' class='form-control timepicker timepicker-24' name='txttime' value='" + einfo.getTime() + "' id='txttime' maxlength='5' onblur='getChgedatetime();' onchange='getChgedatetime();'/>");
                sb.append("</div>");
                sb.append("</div>");
                sb.append("<div class='col-lg-6 col-md-6 col-sm-6 col-12 form_group'>");
                sb.append("<label class='form_label'>Interviewer</label>");
                sb.append("<select class='form-select' id='selInterviewer' name='selInterviewer'>");
                sb.append("<option value='-1'>- Select -</option>");
                sb.append(clientlogin.getManagerList(einfo.getInterviewerId()));
                sb.append("</select>");
                sb.append("</div>");
                
                sb.append("<div class='col-lg-6 col-md-6 col-sm-6 col-12 form_group'>");
                sb.append("<label class='form_label'>Interviewer Country - Time Zone</label>");
                sb.append("<select class='form-select' id='selInterviewerTimeZone' name='selInterviewerTimeZone' onchange='getChgedatetime();' />");
                sb.append(clientlogin.getStringFromList(tzList, einfo.getTz1()));
                sb.append("</select>");
                sb.append("</div>");
                
                sb.append("<div class='col-lg-6 col-md-6 col-sm-6 col-12 form_group'>");
                sb.append("<label class='form_label'>Candidate Country - Time Zone</label>");
                sb.append("<select class='form-select' id='selCandTimeZone' name='selCandTimeZone' onchange='getChgedatetime();'>");
                sb.append(clientlogin.getStringFromList(tzList, einfo.getTz2()));
                sb.append("</select>");                
                sb.append("<label class='form_label date_time_label'>Candidate Date & Time</label>");
                sb.append("<span class='date_time' id='sDate'>" + einfo.getDatec() + "</span>");
                sb.append("<input type='hidden' class='form-control' id='txtDate' name='txtDate' value='" + einfo.getDatec() + "'/>");
                sb.append("</div>");                
                
                sb.append("<div class='col-lg-6 col-md-6 col-sm-6 col-12 form_group'>");
                sb.append("<label class='form_label' id='onofflbl'>");
                if ("Online".equalsIgnoreCase(einfo.getMode())) {
                    sb.append("Link");
                } else if ("Offline".equalsIgnoreCase(einfo.getMode())) {
                    sb.append("Location");
                }
                sb.append("</label>");                            
                sb.append("<textarea id='txtLocLink' name='txtLocLink' class='form-control' rows='3' maxlength='500'>" + einfo.getLinkloc() + "</textarea>");
                sb.append("</div>");                
                sb.append("</div>");
                sb.append("</div>");
                sb.append("</div>");
                sb.append("</div>");
                sb.append("</div>");
                sb.append("<div class='row'>");
                sb.append("<div class='col-lg-12 col-md-12 col-sm-12 col-12 text-center'  id='submitdiv'>");
                sb.append("<a href=\"javascript: ;\" onclick=\"javascript: saveInterview();\" class='save_page'>Save &nbsp;&nbsp;<img src='../assets/images/save.png'></a>");
                sb.append("</div>");
                sb.append("</div>");
            }
            String st1 = sb.toString();
            sb.setLength(0);
            response.getWriter().write(st1);
        } else {
            response.getWriter().write("Please check your login session....");
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
%>