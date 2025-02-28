<%@page contentType="text/html"%>
<%@page language="java" import="java.util.ArrayList"%>
<%@page pageEncoding="UTF-8"%>
<%@page import="com.web.jxp.user.UserInfo" %>
<%@page import="com.web.jxp.talentpool.TalentpoolInfo" %>
<jsp:useBean id="ddl" class="com.web.jxp.talentpool.Ddl" scope="page"/>
<jsp:useBean id="vobj" class="com.web.jxp.base.Validate" scope="page"/>
<%
    try 
    {
        if (session.getAttribute("LOGININFO") != null) 
        {
            if (request.getParameter("dayrateId") != null) 
            {
                StringBuilder sb = new StringBuilder();
                String dayrateId_s = request.getParameter("dayrateId") != null ? vobj.replaceint(request.getParameter("dayrateId")) : "0";
                String dayrateId2s = request.getParameter("dayrateId2") != null ? vobj.replaceint(request.getParameter("dayrateId2")) : "0";
                String type_s = request.getParameter("type") != null ? vobj.replaceint(request.getParameter("type")) : "0";
                int type = Integer.parseInt(type_s);
                int dayrateId = Integer.parseInt(dayrateId_s);
                int dayrateId2 = Integer.parseInt(dayrateId2s);
                TalentpoolInfo info = ddl.getDayRateForEdit(dayrateId);
                double rate1 = 0.0, rate2 = 0.0, rate3 =0.0;
                String fromDate = "", toDate = "";
                int positionId = 0, pid1 = 0, pid2 = 0;
                if(info != null)
                {
                    rate1 = info.getRate1();
                    rate2 = info.getRate2();
                    rate3 = info.getRate3();
                    positionId = info.getPositionId();
                    System.out.println("positionId :: "+positionId);
                    fromDate = info.getFromDate() != null ? info.getFromDate(): "";
                    toDate = info.getToDate() != null ? info.getToDate(): "";
                }
                TalentpoolInfo pinfo = null;
                String pname1 = "", pname2 = "";
                if (request.getSession().getAttribute("PINFO") != null) 
                {
                    pinfo = (TalentpoolInfo) request.getSession().getAttribute("PINFO");
                    if(pinfo != null)
                    {
                        pid1 = pinfo.getPositionId();
                        pid2 = pinfo.getPositionId2();
                        pname1 = pinfo.getPosition() != null ? pinfo.getPosition(): "";
                        pname2 = pinfo.getPosition2() != null ? pinfo.getPosition2(): "";
                    }
                }
                if (dayrateId > 0)
                {
                    sb.append("<div class='row' id='dup_div'>");
                    sb.append("<div class='col-lg-12'>");
                    sb.append("<h2>Crew Rate</h2>");
                    sb.append("<div class='row client_position_table'>");
                    sb.append("<input type='hidden' name='dayrateId2' id='dayrateId2' value='" + dayrateId + "' />");
                    sb.append("<input type='hidden' name='dayratehId' id='dayratehId' value='" + dayrateId2 + "' />");
                    if(type == 1)
                    {
                        sb.append("<div class='col-lg-12 col-md-12 col-sm-12 col-4 form_group' >");
                            sb.append("<select class='form-select' name='positionId' id='positionId' >");
                            if (pid1 == positionId) 
                            {
                                sb.append("<option value='" + pid1 + "' ");
                                sb.append(" selected ");
                                sb.append(" >" + pname1 + "</option>");
                                if (pid2 > 0) {
                                    sb.append("<option value='" + pid2 + "' ");
                                }
                                sb.append(" select ");
                                sb.append(" >" + pname2 + "</option>");
                            }
                            else if (pid2 == positionId) 
                            {
                                sb.append("<option value='" + pid2 + "' ");
                                sb.append(" selected ");
                                sb.append(" >" + pname2 + "</option>");
                                sb.append("<option value='" + pid1 + "' select>" + pname1 + "</option>");
                            }
                            sb.append("</select>");
                        sb.append("</div>");

                        sb.append("<div class='col-lg-6 col-md-6 col-sm-12 col-4 form_group'>");
                        sb.append("<label class='form_label'>From Date<span class='required'>*</span></label>");
                        sb.append("<div class='input-daterange input-group'>");
                        sb.append("<input type='text' name='fromDate' value='" + fromDate + "' id='fromDate' class='form-control add-style wesl_dt date-add text-center' placeholder='DD-MMM-YYYY'>");
                        sb.append("</div>");
                        sb.append("</div>");

                        sb.append("<div class='col-lg-6 col-md-6 col-sm-12 col-4 form_group'>");
                        sb.append("<label class='form_label'>To Date</label>");
                        sb.append("<div class='input-daterange input-group'>");
                        sb.append("<input type='text' name='toDate' value='" + toDate + "' id='toDate' class='form-control add-style wesl_dt date-add text-center' placeholder='DD-MMM-YYYY' />");
                        sb.append("</div>");
                        sb.append("</div>");
                    }else
                    {
                        sb.append("<input type='hidden' name='positionId' id='positionId' value='" + positionId + "' />");
                        sb.append("<div class='col-lg-12 col-md-12 col-sm-12 col-4 form_group' >");
                        if (pid1 == positionId)
                        {
                            sb.append("<label class='form_label'>Position</label>");
                            sb.append("<span class='form-control'>"+pname1+"</span>");
                        }
                        else if (pid2 == positionId)
                        {
                            sb.append("<label class='form_label'>Position</label>");
                            sb.append("<span class='form-control'>"+pname2+"</span>");
                        }
                        sb.append("</div>");

                        sb.append("<div class='col-lg-6 col-md-6 col-sm-12 col-4 form_group'>");
                        sb.append("<label class='form_label'>From Date<span class='required'>*</span></label>");
                        sb.append("<div class='input-daterange input-group'>");
                        sb.append("<input type='text' name='fromDate' value='" + fromDate + "' id='fromDate' class='form-control add-style wesl_dt date-add text-center' placeholder='DD-MMM-YYYY' readonly>");
                        sb.append("</div>");
                        sb.append("</div>");

                        sb.append("<div class='col-lg-6 col-md-6 col-sm-12 col-4 form_group'>");
                        sb.append("<label class='form_label'>To Date</label>");
                        sb.append("<div class='input-daterange input-group'>");
                        sb.append("<input type='text' name='toDate' value='" + toDate + "' id='toDate' class='form-control add-style wesl_dt date-add text-center' placeholder='DD-MMM-YYYY' readonly>");
                        sb.append("</div>");
                        sb.append("</div>");                        
                    }
                    
                    sb.append("<div class='col-lg-6 col-md-6 col-sm-6 col-4 form_group'>");
                    sb.append("<label class='form_label'>Day Rate<span class='required'>*</span></label>");
                    sb.append("<input type='text' name='dayrate1' id='dayrate1' value='" + rate1 + "' class='form-control' placeholder='' onkeypress='return allowPositiveNumber(event);'/>");
                    sb.append("</div>");
                    
                    sb.append("<div class='col-lg-6 col-md-6 col-sm-6 col-4 form_group'>");
                    sb.append("<label class='form_label'>Overtime / Hr</label>");
                    sb.append("<input type='text' name='dayrate2' id='dayrate2' value='" + rate2 + "' class='form-control' placeholder='' onkeypress='return allowPositiveNumber(event);'/>");
                    sb.append("</div>");
                    
                    sb.append("<div class='col-lg-6 col-md-6 col-sm-6 col-4 form_group'>");
                    sb.append("<label class='form_label'>Allowances</label>");
                    sb.append("<input type='text' name='dayrate3' id='dayrate3' value='" + rate3 + "' class='form-control' placeholder='' onkeypress='return allowPositiveNumber(event);'/>");
                    sb.append("</div>");
                    sb.append("</div>");

                    sb.append("<div class='row'>");
                    sb.append("<div class='col-lg-12 col-md-12 col-sm-12 col-12 text-center' id='submitdiv'>");
                    sb.append("<div class='row'>");
                    sb.append("<div class='col-lg-6 col-md-6'><a class='trans_btn hand_cursor' data-bs-dismiss='modal'>Cancel</a></div>");
                    sb.append("<div class='col-lg-6 col-md-6'><a href=\"javascript: updateDayRate();\" class='termi_btn'>Save</a></div>");
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