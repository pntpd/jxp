<%@page contentType="text/html"%>
<%@page import="com.web.jxp.clientlogin.ClientloginInfo" %>
<jsp:useBean id="clientlogin" class="com.web.jxp.clientlogin.Clientlogin" scope="page"/>
<jsp:useBean id="vobj" class="com.web.jxp.base.Validate" scope="page"/>
<%
    try 
    {
        if (session.getAttribute("MLOGININFO") != null) 
        {
            if (request.getParameter("interviewId") != null) 
            {
                String st1 = "";
                StringBuilder sb = new StringBuilder();
                String interviewIds = request.getParameter("interviewId") != null ? vobj.replaceint(request.getParameter("interviewId")) : "0";
                String types = request.getParameter("type") != null ? vobj.replaceint(request.getParameter("type")) : "0";
                int interviewId = Integer.parseInt(interviewIds);
                int type = Integer.parseInt(types);
                ClientloginInfo info = clientlogin.getSchedulDetail(interviewId);
                if (interviewId > 0 && type == 1)
                {
                    if (info != null) 
                    {
                        sb.append("<h2>INTERVIEW DETAILS</h2>");
                        sb.append("<div class='full_width client_position_table'>");
                        sb.append("<div class='table-responsive1 mb-0'>");
                        sb.append("<table class='table table-striped'>");
                        sb.append("<thead>");
                        sb.append("<tr>");
                        sb.append("<th width='%'><span><b>Interviewer Name</b> </span></th>");
                        sb.append("<th width='%'><span><b>Interview Date</b> </span></th>");
                        sb.append("<th width='%'><span><b>Mode</b> </span></th>");
                        sb.append("<th width='%'><span><b>Interview Details</b> </span></th>");
                        sb.append("</tr>");
                        sb.append("</thead>");
                        sb.append("<tbody>");
                        sb.append("<tr>");
                        sb.append("<td>" + (info.getUsername() != null ? info.getUsername() : "") + "</td>");
                        sb.append("<td>" + (info.getDate() != null ? info.getDate() : "") + "</td>");
                        sb.append("<td>" + (info.getMode() != null ? info.getMode() : "") + "</td>");
                        sb.append("<td>" + (info.getLinkloc() != null ? info.getLinkloc() : "") + "</td>");
                        sb.append("</tr>");
                        sb.append("</tbody>");
                        sb.append("</table>");
                        sb.append("</div>");
                        sb.append("</div>");
                    } 
                    else {
                        sb.append("<div>No records available.</div></div>");
                    }
                    st1 = sb.toString();
                    sb.setLength(0);
                    response.getWriter().write(st1);
                }
                else if (interviewId > 0 && type == 2)
                {
                    sb.append("<h2>EVALUATE CANDIDATE</h2>");
                    sb.append("<input type='hidden' name='interviewId' value='"+interviewId+"'>");
                    sb.append("<input type='hidden' name='candidateId' value='"+info.getCandidateId()+"'>");
                                        
                    sb.append("<div class='col-lg-12 rem_ave_score'>");
                    sb.append("<div class='row mb_20'>");
                    sb.append("<div class='col-lg-7'>");
                    
                    sb.append("<div class='main-heading'><h4>Remarks</h4></div>");
                    sb.append("<textarea class='remark form-control remarks_score_bx' id='remark' name='remark' maxlength='500' rows='5'>");
                    sb.append("</textarea>");
                    sb.append("</div>");
                    
                    sb.append("<div class='col-lg-5'>");
                    sb.append("<div class='main-heading'><h4>Score</h4></div>");
                    sb.append("<div class='avg_score remarks_score_bx'>");
                    sb.append("<input class='form-control text-center passed_mark avg_scrore_value bg_transparent' type='text' name='score' id='score' style='border:none;' onkeypress='return allowPositiveNumber1(event);' maxlength='3' />");
                    sb.append("<span class='avg_marks'>100</span>");
                    sb.append("</div>");
                    sb.append("</div>");
                    sb.append("</div>");
                    sb.append("</div>");

                    sb.append("<div class='col-lg-12 col-md-12 col-sm-12 col-12 text-center' id='saveEV'>");
                            sb.append("<a href=\"javascript: evaluateCandidate('1');\" class='save_page mr_15'> Accept</a>");
                            sb.append("<a href=\"javascript: evaluateCandidate('2');\" class='save_page'> Reject</a>");
                    sb.append("</div>");
                        
                    st1 = sb.toString();
                    sb.setLength(0);
                    response.getWriter().write(st1);
                }
                else if (interviewId > 0 && type == 4)
                {
                    if (info != null) 
                    {
                        sb.append("<h2>UNAVAILABLE DETAILS</h2>");
                        sb.append("<div class='full_width client_position_table'>");
                        sb.append("<div class='table-responsive1 mb-0'>");
                        sb.append("<table class='table table-striped'>");
                        sb.append("<thead>");
                        sb.append("<tr>");
                        sb.append("<th width='%'><span><b>Remarks</b> </span></th>");
                        sb.append("<th width='%'><span><b>Upadated On</b> </span></th>");
                        sb.append("</tr>");
                        sb.append("</thead>");
                        sb.append("<tbody>");
                        sb.append("<tr>");
                        sb.append("<td>" + (info.getRemarks2()!= null ? info.getRemarks2() : "") + "</td>");
                        sb.append("<td>" + (info.getDate2() != null ? info.getDate2() : "") + "</td>");
                        sb.append("</tr>");
                        sb.append("</tbody>");
                        sb.append("</table>");
                        sb.append("</div>");
                        sb.append("</div>");
                    } 
                    else {
                        sb.append("<div>No records available.</div></div>");
                    }
                    st1 = sb.toString();
                    sb.setLength(0);
                    response.getWriter().write(st1);
                }
                else
                {
                    sb.append("<div class='full_width client_position_table'>");
                    sb.append("<div class='table-responsive1 mb-0'>");
                    sb.append("<table class='table table-striped'>");
                    sb.append("<thead>");
                    sb.append("<tr>");
                    sb.append("<th width='%'><span><b>Interviewer Name</b> </span></th>");
                    sb.append("<th width='%'><span><b>Interview Date</b> </span></th>");
                    sb.append("<th width='%'><span><b>Mode</b> </span></th>");
                    sb.append("<th width='%'><span><b>Interview Details</b> </span></th>");
                    sb.append("<th width='%'><span><b>Remarks</b> </span></th>");
                    sb.append("</tr>");
                    sb.append("</thead>");
                    sb.append("<tbody>");
                    sb.append("<tr>");
                    sb.append("<td>" + (info.getUsername() != null ? info.getUsername() : "") + "</td>");
                    sb.append("<td>" + (info.getDate() != null ? info.getDate() : "") + "</td>");
                    sb.append("<td>" + (info.getMode() != null ? info.getMode() : "") + "</td>");
                    sb.append("<td>" + (info.getLinkloc() != null ? info.getLinkloc() : "") + "</td>");
                    sb.append("<td>" + (info.getRemarks()!= null ? info.getRemarks() : "") + "</td>");
                    sb.append("</tr>");
                    sb.append("</tbody>");
                    sb.append("</table>");
                    sb.append("</div>");
                    sb.append("</div>");
                
                    st1 = sb.toString();
                    sb.setLength(0);
                    response.getWriter().write(st1);
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