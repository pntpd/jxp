<%@page import="com.web.jxp.cassessment.Cassessment"%>
<%@page contentType="text/html"%>
<%@page language="java" import="java.util.ArrayList"%>
<%@page pageEncoding="UTF-8"%>
<%@page import="com.web.jxp.user.UserInfo" %>
<%@page import="com.web.jxp.cassessment.CassessmentInfo"%>
<jsp:useBean id="cassessment" class="com.web.jxp.cassessment.Cassessment" scope="page"/>
<jsp:useBean id="vobj" class="com.web.jxp.base.Validate" scope="page"/>
<%
    try 
    {
        if (session.getAttribute("LOGININFO") != null) 
        {
            UserInfo uinfo = ((UserInfo) request.getSession().getAttribute("LOGININFO"));
            String per = "", editper = "N";
            if (uinfo != null)
            {
                per = uinfo.getPermission() != null ? uinfo.getPermission() : "N";
                editper = uinfo.getEditper() != null ? uinfo.getEditper() : "N";
            }
            if (request.getParameter("cid") != null) 
            {
                String cassessmentId_s = request.getParameter("cid") != null && !request.getParameter("cid").equals("") ? vobj.replaceint(request.getParameter("cid")) : "0";
                String passId_s = request.getParameter("passid") != null && !request.getParameter("passid").equals("") ? vobj.replaceint(request.getParameter("passid")) : "0";
                String candidateId_s = request.getParameter("candidateid") != null && !request.getParameter("candidateid").equals("") ? vobj.replaceint(request.getParameter("candidateid")) : "0";
                String para_s = request.getParameter("para") != null ? vobj.replacedesc(request.getParameter("para")) : "";
                int cassessmentId = Integer.parseInt(cassessmentId_s);
                int candidateId = Integer.parseInt(candidateId_s);
                int passId = Integer.parseInt(passId_s);
                CassessmentInfo info = cassessment.getAssessmentDetailByIdforDetail(passId, candidateId);

                if (info != null) 
                {
                    StringBuilder sb = new StringBuilder();
                    sb.append("<div class='row app_ass_main'>");
                    sb.append("<div class='col-lg-12 app_scree_head'>");
                    sb.append("<h2>" + info.getAssessmentName() + "</h2>");
                    sb.append("<span>" + info.getScheduletype() + "</span>");
                    sb.append("</div>");
                    sb.append("<div class='col-lg-12 '>");
                    sb.append("<div class='view_asses'>");
                    sb.append("<div class='row'>");
                    sb.append("<div class='col-lg-10'>");
                    sb.append("<div class='row'>");
                    sb.append("<div class='col-lg-4'>");
                    sb.append("<div class='row'>");
                    sb.append("<div class='col-lg-7'><label>Minimum Score</label></div>");
                    if (!"".equals(info.getMinScore())) {
                        sb.append("<div class='col-lg-5'><span class='status_value'>" + info.getMinScore() + "</span></div>");
                    } else {
                        sb.append("<div class='col-lg-5'><span class='status_value'>&nbsp;</span></div>");
                    }
                    sb.append("</div>");
                    sb.append("</div>");

                    sb.append("<div class='col-lg-3'>");
                    sb.append("<div class='row'>");
                    sb.append("<div class='col-lg-12'>");
                    sb.append("<div class='row'>");
                    sb.append("<div class='col-lg-9'><label>Passing Required</label></div>");
                    if (info.getPassing() != null && !"".equals(info.getPassing())) {
                        sb.append("<div class='col-lg-3'><span class='status_value'>" + info.getPassing() + "</span></div>");
                    } else {
                        sb.append("<div class='col-lg-3'><span class='status_value'>&nbsp;</span></div>");
                    }
                    sb.append("</div>");
                    sb.append("</div>");
                    sb.append("</div>");
                    sb.append("</div>");
                    sb.append("<div class='col-lg-5'>");
                    sb.append("<div class='row'>");
                    sb.append("<div class='col-lg-4'><label>Coordinator</label></div>");
                    if (info.getName() != null && !info.getName().equals("")) {
                        sb.append("<div class='col-lg-8'><span class='status_value'>" + info.getName() + "</span></div>");
                    } else {
                        sb.append("<div class='col-lg-8'><span class='status_value'>&nbsp;</span></div>");
                    }
                    sb.append("</div>");
                    sb.append("</div>");
                    sb.append("<div class='col-lg-12'>");
                    sb.append("<div class='row'>");
                    sb.append("<div class='col-lg-2'>");
                    sb.append("<label>Parameters</label>");
                    sb.append("</div>");
                    sb.append("<div class='col-lg-10'>");
                    if (info.getParameter() != null && !"".equals(info.getParameter())) {
                        sb.append("<span class='status_value'>" + info.getParameter() + "</span>");
                    } else {
                        sb.append("<span class='status_value'>&nbsp;</span>");
                    }
                    sb.append("</div></div>");
                    sb.append("</div>");
                    sb.append("</div>");
                    sb.append("</div>");

                    sb.append("<div class='col-lg-2'>");
                    sb.append("<div class='row'>");
                    sb.append("<div class='col-lg-12 candi_prof_div'>");
                    sb.append("<input type='hidden' name='assessmentId' id='assessmentId' value='" + info.getAssessmentId() + "'/>");
                    sb.append("<a href=\"javascript: viewAssessment('" + info.getAssessmentId() + "');\">");
                    sb.append("<img src='../assets/images/view.png'/><br/>");
                    sb.append("View Assessment");
                    sb.append("</a>");
                    sb.append("</div>");
                    sb.append("</div>");
                    sb.append("</div>");
                    sb.append("</div>");
                    sb.append("</div>");
                    sb.append("</div>");
                    sb.append("</div>");
                    if (cassessmentId > 0)
                    {
                        if (para_s.equalsIgnoreCase("view")) 
                        {
                            CassessmentInfo vinfo = cassessment.viewCassessment(cassessmentId);

                            sb.append("<div class='row working_area'>");

                            sb.append("<div class='full_width'>");
                            sb.append("<div class='col-lg-8'>");
                            sb.append("<div class='row'>");
                            sb.append("<div class='col-lg-6 col-md-6 col-sm-6 col-6 form_group'>");
                            sb.append("<label class='form_label'>Datesss</label>");
                            sb.append("<span class='form-control'>" + vinfo.getDate() + "</span>");
                            sb.append("</div>");
                            sb.append("<div class='col-lg-6 col-md-6 col-sm-6 col-6 form_group'>");
                            sb.append("<label class='form_label'>Mode</label>");
                            sb.append("<span class='form-control'>" + vinfo.getMode() + "</span>");
                            sb.append("</div>");
                            sb.append("<div class='col-lg-6 col-md-6 col-sm-6 col-6 form_group'>");
                            sb.append("<label class='form_label'>Duration (Minutes)</label>");
                            sb.append("<span class='form-control'>" + vinfo.getDuration() + "</span>");
                            sb.append("</div>");
                            sb.append("<div class='col-lg-6 col-md-6 col-sm-6 col-6 form_group'>");
                            sb.append("<label class='form_label'>Time</label>");
                            sb.append("<span class='form-control'>" + vinfo.getTime() + "</span>");
                            sb.append("</div>");
                            sb.append("<div class='col-lg-6 col-md-6 col-sm-6 col-6 form_group'>");
                            sb.append("<label class='form_label'>Assessor</label>");
                            sb.append("<span class='form-control'>" + vinfo.getName() + "</span>");
                            sb.append("</div>");
                            sb.append("<div class='col-lg-6 col-md-6 col-sm-6 col-6 form_group'>");
                            sb.append("<label class='form_label'>Assesor Country - Time Zone</label>");
                            sb.append("<span class='form-control'>" + vinfo.getTimezone1() + "</span>");
                            sb.append("</div>");
                            sb.append("<div class='col-lg-6 col-md-6 col-sm-6 col-6 form_group'>");
                            sb.append("<label class='form_label'>Candidate Country - Time Zone</label>");
                            sb.append("<span class='form-control mb_10'>" + vinfo.getTimezone2() + "</span>");
                            sb.append("<label class='form_label date_time_label'>Candidate Date & Time</label>");
                            sb.append("<span class='date_time'>" + vinfo.getDatec() + "</span>");
                            sb.append("</div>");
                            sb.append("<div class='col-lg-6 col-md-6 col-sm-6 col-6 form_group'>");
                            sb.append("<label class='form_label'>");
                            if ("Online".equalsIgnoreCase(vinfo.getMode())) {
                                sb.append("Link");
                            } else if ("Offline".equalsIgnoreCase(vinfo.getMode())) {
                                sb.append("Location");
                            }
                            sb.append("</label>");
                            sb.append("<span class='form-control'>" + vinfo.getLinkloc() + "</span>");
                            sb.append("</div>");
                            sb.append("</div>");
                            sb.append("</div>");
                            sb.append("</div>");
                            sb.append("<div class='col-lg-12 col-md-12 col-sm-12 col-12 '>");
                            if (vinfo.getStatus() == 2 || vinfo.getStatus() == 3) {
                                sb.append("<a href=\"javascript: getCassessmentScore('" + cassessmentId + "');\" class='cl_btn score_btn'><i class='ion ion-md-star-outline'></i> Score</a> ");
                            }

                            if ((editper.equals("Y") || (per.equals("Y"))) && vinfo.getStatus() == 1) {
                                sb.append("<a href=\"javascript: getViewEditData('" + passId + "', '" + cassessmentId + "','edit');\" class='ass_edit'><img src='../assets/images/edit.png'></a>");
                            }
                            if (vinfo.getStatus() == 3) {
                                sb.append("<a href=\"javascript: retakeDetail('" + cassessmentId + "','" + passId + "');\" class='ass_edit'><img src='../assets/images/retake.png'></a>");
                            }
                            sb.append("</div>");
                            sb.append("</div>");

                        } else if (para_s.equalsIgnoreCase("edit")) 
                        {
                            ArrayList tzList = cassessment.getTimezone();
                            CassessmentInfo einfo = cassessment.editCassessment(cassessmentId);

                            sb.append("<div class='row working_area'>");
                            sb.append("<div class='full_width'>");
                            sb.append("<div class='col-lg-8'>");
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
                            sb.append("<div class='col-lg-6 col-md-6 col-sm-6 col-6 form_group'>");
                            sb.append("<label class='form_label'>Assessor</label>");
                            sb.append("<select class='form-select' id='selAssessor' name='selAssessor'>");
                            sb.append("<option value='-1'>- Select -</option>");
                            sb.append(cassessment.getAssessorList(einfo.getAssessorId()));
                            sb.append("</select>");
                            sb.append("</div>");
                            sb.append("<div class='col-lg-6 col-md-6 col-sm-6 col-6 form_group'>");
                            sb.append("<label class='form_label'>Assesor Country - Time Zone</label>");
                            sb.append("<select class='form-select' id='selCandTimeZone' name='selCandTimeZone'  onchange='getChgedatetime();' />");
                            sb.append(cassessment.getStringFromList(tzList, einfo.getTz1()));
                            sb.append("</select>");
                            sb.append("</div>");
                            sb.append("<div class='col-lg-6 col-md-6 col-sm-6 col-6 form_group'>");
                            sb.append("<label class='form_label'>Candidate Country - Time Zone</label>");
                            sb.append("<select class='form-select' id='selAssessTimeZone' name='selAssessTimeZone' onchange='getChgedatetime();'>");
                            sb.append(cassessment.getStringFromList(tzList, einfo.getTz2()));
                            sb.append("</select>");
                            sb.append("<label class='form_label date_time_label'>Candidate Date & Time</label>");
                            sb.append("<span class='date_time' id='sDate'>" + einfo.getDatec() + "</span>");
                            sb.append("<input type='hidden' class='form-control' id='txtDate' name='txtDate' value='" + einfo.getDatec() + "'/>");
                            sb.append("</div>");
                            sb.append("<div class='col-lg-6 col-md-6 col-sm-6 col-6 form_group'>");
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
                            sb.append("<div class='col-lg-12 col-md-12 col-sm-12 col-12' id='submitdiv'>");
                            if (per.equals("Y") || editper.equals("Y")) {
                                sb.append("<a href=\"javascript: submitForm();\"  class='save_btn'><img src='../assets/images/save.png'> Save</a>");
                            }
                            sb.append("<a href=\"javascript: getViewEditData('" + passId + "', '" + cassessmentId + "','view');\" class='cl_btn'><i class='ion ion-md-close'></i> Close</a>");
                            sb.append("</div>");
                            sb.append("</div>");
                        }
                    } else
                    {
                        ArrayList tzList = cassessment.getTimezone();
                        sb.append("<div class='row working_area'>");
                        sb.append("<div class='full_width'>");
                        sb.append("<div class='col-lg-8'>");
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
                        sb.append("<div class='col-lg-6 col-md-6 col-sm-6 col-6 form_group'>");
                        sb.append("<label class='form_label'>Assessor</label>");
                        sb.append("<select class='form-select' id='selAssessor' name='selAssessor'>");
                        sb.append("<option value='-1'>- Select -</option>");
                        sb.append(cassessment.getAssessorList(0));
                        sb.append("</select>");
                        sb.append("</div>");
                        sb.append("<div class='col-lg-6 col-md-6 col-sm-6 col-6 form_group'>");
                        sb.append("<label class='form_label'>Assesor Country - Time Zone</label>");
                        sb.append("<select class='form-select' id='selCandTimeZone' name='selCandTimeZone' onchange='getChgedatetime();'>");
                        sb.append(cassessment.getStringFromList(tzList, 0));
                        sb.append("</select>");
                        sb.append("</div>");
                        sb.append("<div class='col-lg-6 col-md-6 col-sm-6 col-6 form_group'>");
                        sb.append("<label class='form_label'>Candidate Country - Time Zone</label>");
                        sb.append("<select class='form-select' id='selAssessTimeZone' name='selAssessTimeZone' onchange='getChgedatetime();'>");
                        sb.append(cassessment.getStringFromList(tzList, 0));
                        sb.append("</select>");
                        sb.append("<label class='form_label date_time_label'>Candidate Date & Time</label>");
                        sb.append("<span class='date_time' id='sDate'></span>");
                        sb.append("<input type='hidden' class='form-control' id='txtDate' name='txtDate' value=''/>");
                        sb.append("</div>");
                        sb.append("<div class='col-lg-6 col-md-6 col-sm-6 col-6 form_group'>");
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
                        sb.append("<div class='col-lg-12 col-md-12 col-sm-12 col-12' id='submitdiv'>");
                        if (per.equals("Y") || editper.equals("Y")) {
                            sb.append("<a href=\"javascript: submitForm();\" class='save_btn'><img src='../assets/images/save.png'> Save</a>");
                        }
                        sb.append("<a href=\"javascript: goback();\" class='cl_btn'><i class='ion ion-md-close'></i> Close</a>");
                        sb.append("</div>");
                        sb.append("</div>");
                    }
                    String s1 = sb.toString();
                    sb.setLength(0);
                    response.getWriter().write(s1);
                }
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