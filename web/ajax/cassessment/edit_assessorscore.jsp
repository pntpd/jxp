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
            String editper = "N";
            if (uinfo != null) 
            {
                editper = uinfo.getEditper() != null ? uinfo.getEditper() : "N";
            }
            if (request.getParameter("cassessmentid") != null)
            {
                String cassessmentId_s = request.getParameter("cassessmentid") != null && !request.getParameter("cassessmentid").equals("") ? vobj.replaceint(request.getParameter("cassessmentid")) : "0";
                int cassessmentId = Integer.parseInt(cassessmentId_s);
                CassessmentInfo info = cassessment.getCassessmentDetailById(cassessmentId);
                if (info != null)
                {
                    StringBuilder sb = new StringBuilder();
                    sb.append("<div class='row app_ass_main'>");
                    sb.append("<div class='col-lg-12 app_scree_head'>");
                    sb.append("<h2>" + info.getAssessmentName() + "</h2>");
                    sb.append("<span>Scheduled</span>");
                    sb.append("</div>");
                    sb.append("<div class='col-lg-12 '>");
                    sb.append("<div class='view_asses'>");
                    sb.append("<div class='row'>");
                    sb.append("<div class='col-lg-3'>");
                    sb.append("<div class='row'>");
                    sb.append("<div class='col-lg-4'><label>Date</label></div>");
                    sb.append("<div class='col-lg-8'>");
                    sb.append("<span class='status_value'>" + info.getDate() + "</span>");
                    sb.append("</div>");
                    sb.append("</div>");
                    sb.append("</div>");
                    sb.append("<div class='col-lg-3'>");
                    sb.append("<div class='row'>");
                    sb.append("<div class='col-lg-4'><label>Mode</label></div>");
                    sb.append("<div class='col-lg-8'>");
                    sb.append("<span class='status_value'>" + info.getMode() + "</span>");
                    sb.append("</div>");
                    sb.append("</div>");
                    sb.append("</div>");
                    sb.append("<div class='col-lg-2'>");
                    sb.append("<div class='row'>");
                    sb.append("<div class='col-lg-4'><label>");
                    if (info.getMode().equalsIgnoreCase("online")) {
                        sb.append("Link");
                    } else if (info.getMode().equalsIgnoreCase("offline")) {
                        sb.append("Location");
                    }
                    sb.append("</label></div>");
                    sb.append("<div class='col-lg-8'>");
                    sb.append("<span class='status_value'>");
                    if (info.getMode().equalsIgnoreCase("online")) {
                        sb.append("<a href='" + info.getLinkloc() + "' target='_blank'>" + info.getLinkloc() + "</a>");
                    } else if (info.getMode().equalsIgnoreCase("offline")) {
                        sb.append(info.getLinkloc());
                    }
                    sb.append("</span>");
                    sb.append("</div>");
                    sb.append("</div>");
                    sb.append("</div>");
                    sb.append("<div class='col-lg-4'>");
                    sb.append("<div class='row'>");
                    sb.append("<div class='col-lg-4'><label>Coordinator</label></div>");
                    sb.append("<div class='col-lg-8'>");
                    sb.append("<span class='status_value'>" + info.getName() + "</span>");
                    sb.append("</div>");
                    sb.append("</div>");
                    sb.append("</div>");
                    sb.append("<div class='col-lg-3'>");
                    sb.append("<div class='row'>");
                    sb.append("<div class='col-lg-4'><label>Time</label></div>");
                    sb.append("<div class='col-lg-8'>");
                    sb.append("<span class='status_value'>" + info.getTime() + "</span>");
                    sb.append("</div>");
                    sb.append("</div>");
                    sb.append("</div>");
                    sb.append("<div class='col-lg-3'>");
                    sb.append("<div class='row'>");
                    sb.append("<div class='col-lg-4'><label>Duration</label></div>");
                    sb.append("<div class='col-lg-8'>");
                    sb.append("<span class='status_value'>" + info.getDuration() + " minutes</span>");
                    sb.append("</div>");
                    sb.append("</div>");
                    sb.append("</div>");
                    sb.append("</div>");
                    sb.append("</div>");
                    sb.append("</div>");
                    sb.append("</div>");

                    if (info.getParameter() != null) 
                    {
                        ArrayList list = new ArrayList();
                        list = cassessment.getAssessmentParameterListByParaId(info.getParameter());
                        int total = list.size();
                        sb.append("<div class='row working_area add_score'>");
                        sb.append("<div class='col-lg-12' id=''>");
                        sb.append("<div class='table-responsive table-detail'>");
                        if (total > 0) 
                        {
                            sb.append("<table class='table table-striped mb-0'>");
                            sb.append("<thead>");
                            sb.append("<tr>");
                            sb.append("<th width='59%'>Parameters</th>");
                            sb.append("<th width='27%'>Scores (out of 100)</th>");
                            sb.append("<th width='14%' class='text-center'>Guide</th>");
                            sb.append("</tr>");
                            sb.append("</thead>");
                            sb.append("<tbody>");
                            CassessmentInfo pinfo;
                            for (int i = 0; i < total; i++) 
                            {
                                pinfo = (CassessmentInfo) list.get(i);
                                if (pinfo != null) 
                                {
                                    sb.append("<tr>");
                                    sb.append("<td>" + pinfo.getParameterId() + " | " + pinfo.getName() + "");
                                    sb.append("<input type='hidden' id='hdnParameterId" + (i + 1) + "' name='hdnParameterId' value='" + pinfo.getParameterId() + "'/></td>");
                                    sb.append("<td><input type='text' class='form-control' id='marks" + (i + 1) + "' name='marks' placeholder='' onpaste='return false' maxlength='5' onkeypress='return allowPositiveNumber(event);' autocomplete='off' onkeyup='setMarks()'/></td>");
                                    sb.append("<td class='info_icon text-center'><a href='javascript: void(0);' onclick=\"javascript: getAssessmentquestions('" + info.getAssessmentId() + "', '" + pinfo.getParameterId() + "', '" + pinfo.getName() + "');\" data-bs-toggle='modal' data-bs-target='#client_position'><i class='ion ion-md-information-circle-outline'></i></a></td>");
                                    sb.append("</tr>");
                                }
                            }
                            sb.append("</tbody>");
                            sb.append("</table>");
                        }
                        sb.append("</div>");
                        sb.append("</div>");
                        sb.append("<div class='col-lg-12 rem_ave_score' id=''>");
                        sb.append("<div class='row'>");
                        sb.append("<div class='col-lg-7'>");
                        sb.append("<div class='main-heading'><h4>Remarks</h4></div>");
                        sb.append("<textarea class='remarks_value form-control' id='txtremarks' name='txtremarks' maxlength='500' rows='4'>");
                        sb.append(info.getRemarks());
                        sb.append("</textarea>");
                        sb.append("</div>");
                        sb.append("<div class='col-lg-5'>");
                        sb.append("<div class='main-heading'><h4>Average Score <span>(current assessment)</span></h4></div>");
                        sb.append("<div class='avg_score'>");
                        sb.append("<span class='avg_scrore_value passed_mark' id='smarks'>" + info.getMarks() + "</span>");
                        sb.append("<input type='hidden' id='hdnmarks' name='hdnmarks' value='" + info.getMarks() + "' />");
                        sb.append("<span class='avg_marks'>100</span>");
                        sb.append("</div>");
                        sb.append("</div>");
                        sb.append("</div>");
                        sb.append("</div>");
                        sb.append("<div class='col-lg-12 col-md-12 col-sm-12 col-12'>");
                        if (editper.equals("Y")) {
                            sb.append("<a href=\"javascript: submitAssessorForm();\" class='save_btn'><img src='../assets/images/save.png'> Save</a>");
                        }
                        sb.append("<a href=\"javascript: gobackassessor();\" class='cl_btn score_close_btn'><i class='ion ion-md-close'></i> Close</a>");
                        sb.append("<input type='hidden' id='hdnminScore' name='hdnminScore' value='" + info.getMinScore() + "' />");
                        sb.append("<input type='hidden' id='hdnpassFlag' name='hdnpassFlag' value='" + info.getPassingFlag() + "' />");
                        sb.append("<input type='hidden' id='pAssessmentId' name='pAssessmentId' value='" + info.getpAssessmentId() + "' />");
                        sb.append("<input type='hidden' id='assessorId' name='assessorId' value='" + info.getAssessorId() + "' />");
                        sb.append("<input type='hidden' id='coordinatorId' name='coordinatorId' value='" + info.getCoordinatorId() + "' />");
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