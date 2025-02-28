<%@page contentType="text/html"%>
<%@page language="java" import="java.util.ArrayList"%>
<%@page pageEncoding="UTF-8"%>
<%@page import="com.web.jxp.user.UserInfo" %>
<%@page import="com.web.jxp.crewdayrate.CrewdayrateInfo" %>
<jsp:useBean id="crewdayrate" class="com.web.jxp.crewdayrate.Crewdayrate" scope="page"/>
<jsp:useBean id="vobj" class="com.web.jxp.base.Validate" scope="page"/>
<%
    try 
    {
        if (session.getAttribute("LOGININFO") != null) 
        {
            if (request.getParameter("candidateId") != null) 
            {
                StringBuilder sb = new StringBuilder();
                String candidateId_s = request.getParameter("candidateId") != null ? vobj.replaceint(request.getParameter("candidateId")) : "0";
                String positionIds = request.getParameter("positionId") != null ? vobj.replaceint(request.getParameter("positionId")) : "0";
                int candidateId = Integer.parseInt(candidateId_s);
                int positionId = Integer.parseInt(positionIds);
                
                if (candidateId > 0)
                {
                    sb.append("<input type='hidden' name='positionIdCandidate' id='positionIdCandidate' value='"+ positionId + "' />");
                    sb.append("<input type='hidden' name='candidateId' id='candidateId' value='"+ candidateId + "' />");
                    sb.append("<div class='row' id='dup_div'>");
                    sb.append("<div class='col-lg-12'>");
                        sb.append("<h2>Crew Rate</h2>");
                        sb.append("<div class='row client_position_table'>");
                            sb.append("<div class='col-lg-6 col-md-6 col-sm-12 col-4 form_group'>");
                                sb.append("<label class='form_label'>From Date</label>");
                                sb.append("<div class='input-daterange input-group'>");
                                    sb.append("<input type='text' name='fromDate' value='' id='fromDate' class='form-control add-style wesl_dt date-add text-center' placeholder='DD-MMM-YYYY'>");
                                sb.append("</div>");
                            sb.append("</div>");
                                            
                            sb.append("<div class='col-lg-6 col-md-6 col-sm-12 col-4 form_group'>");
                                sb.append("<label class='form_label'>To Date</label>");
                                sb.append("<div class='input-daterange input-group'>");
                                    sb.append("<input type='text' name='toDate' value='' id='toDate' class='form-control add-style wesl_dt date-add text-center' placeholder='DD-MMM-YYYY' />");
                                sb.append("</div>");
                            sb.append("</div>");                            
                            
                            sb.append("<div class='col-lg-6 col-md-6 col-sm-6 col-4 form_group'>");
                                sb.append("<label class='form_label'>Day Rate</label>");
                                sb.append("<input type='text' name='rate1' id='rate1' value='' class='form-control' placeholder='' onkeypress='return allowPositiveNumber(event);'/>");
                            sb.append("</div>");
                            sb.append("<div class='col-lg-6 col-md-6 col-sm-6 col-4 form_group'>");
                                sb.append("<label class='form_label'>Overtime / Hr</label>");
                                sb.append("<input type='text' name='rate2' id='rate2' value='' class='form-control' placeholder='' onkeypress='return allowPositiveNumber(event);'/>");
                            sb.append("</div>");
                            sb.append("<div class='col-lg-6 col-md-6 col-sm-6 col-4 form_group'>");
                                sb.append("<label class='form_label'>Allowances</label>");
                                sb.append("<input type='text' name='rate3' id='rate3' value='' class='form-control' placeholder='' onkeypress='return allowPositiveNumber(event);'/>");
                            sb.append("</div>");
                        sb.append("</div>");
                        sb.append("<div class='row'>");	
                            sb.append("<div class='col-lg-12 col-md-12 col-sm-12 col-12 text-center' id='submitdiv'>");
                                sb.append("<div class='row'>");
                                    sb.append("<div class='col-lg-6 col-md-6'><a class='trans_btn hand_cursor' data-bs-dismiss='modal'>Cancel</a></div>");
                                    sb.append("<div class='col-lg-6 col-md-6'><a href=\"javascript: submitForm();\" class='termi_btn'>Save</a></div>");
                                sb.append("</div>");
                            sb.append("</div>");
                        sb.append("</div>");
                    sb.append("</div>");
                sb.append("</div>");
                String st1 = sb.toString();
                sb.setLength(0);
                response.getWriter().write(st1);
                }
                else {
                    response.getWriter().write("Something went wrong.");
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