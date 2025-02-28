<%@page import="com.web.jxp.crewlogin.CrewloginInfo"%>
<%@page contentType="text/html"%>
<%@page language="java" import="java.util.ArrayList"%>
<%@page pageEncoding="UTF-8"%>
<%@page import="com.web.jxp.feedback.FeedbackInfo" %>
<jsp:useBean id="feedback" class="com.web.jxp.feedback.Feedback" scope="page"/>
<jsp:useBean id="vobj" class="com.web.jxp.base.Validate" scope="page"/>
<%
    try 
    {
        if (session.getAttribute("CREWLOGIN") != null) 
        {
            if (request.getParameter("Id") != null) 
            {
                StringBuilder sb = new StringBuilder();
                String Ids = request.getParameter("Id") != null && !request.getParameter("Id").equals("") ? vobj.replaceint(request.getParameter("Id")) : "0";
                int trackerId = Integer.parseInt(Ids);
                FeedbackInfo info = null;
                if (trackerId > 0) 
                {
                    info = (FeedbackInfo) session.getAttribute("PCODE_INFO");
                    if (info != null) 
                    {
                        sb.append("<h2>FEEDBACK</h2>");
                        sb.append("<div class='row client_position_table'>");
                        sb.append("<div class='col-xl-12 col-lg-12 col-md-12 col-sm-12 col-12'>");
                        sb.append("<p>Thank you for finishing the knowledge Assessment! Please take a moment to tell us about your experience. Your feedback assists us in improving our assessment process in the future.</p>");
                        sb.append("</div>");
                        sb.append("<div class='col-xl-12 col-lg-12 col-md-12 col-sm-12 col-12 form_group'>");
                        sb.append("<textarea class='form-control' name='feedbackremark' rows='4'>" + (info.getFeedbackremarks() != null ? info.getFeedbackremarks() : "") + "</textarea>");
                        sb.append("</div>");
                        sb.append("</div>");
                        sb.append("<div class='row'>");
                        sb.append("<div class='col-xl-12 col-lg-12 col-md-12 col-sm-12 col-12'>");
                        sb.append("<div class='row justify-content-md-center'>");
                        sb.append("<div class='col-xl-6 col-lg-6 col-md-6 col-sm-6 col-6 hand_cursor'><a href=\"javascript:;\" data-bs-toggle='modal' data-bs-target='#user_comp_ass_complete_modal' class='trans_btn'>Cancel</a></div>");
                        sb.append("<div class='col-xl-6 col-lg-6 col-md-6 col-sm-6 col-6 hand_cursor'><a onclick=\"javascript: getSaveFeedback('" + trackerId + "');\" class='termi_btn'>Send Feedback</a></div>");
                        sb.append("</div>");
                        sb.append("</div>");
                        sb.append("</div>");
                    }
                    String st1 = sb.toString();
                    sb.setLength(0);
                    response.getWriter().write(st1);
                } else {
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