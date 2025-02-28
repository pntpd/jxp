<%@page contentType="text/html"%>
<%@page language="java" import="java.util.ArrayList"%>
<%@page import="com.web.jxp.crewlogin.CrewloginInfo" %>
<%@page import="com.web.jxp.feedback.FeedbackInfo" %>
<jsp:useBean id="feedback" class="com.web.jxp.feedback.Feedback" scope="page"/>
<jsp:useBean id="vobj" class="com.web.jxp.base.Validate" scope="page"/>
<%
    try 
    {
        if (session.getAttribute("CREWLOGIN") != null) 
        {
            StringBuffer sb = new StringBuffer();
            String interviewIds = request.getParameter("interviewId") != null && !request.getParameter("interviewId").equals("") ? vobj.replaceint(request.getParameter("interviewId")) : "0";
            int interviewId = Integer.parseInt(interviewIds);
            
            FeedbackInfo finfo = feedback.getEmailById(interviewId);
            if (finfo != null) 
            {
                sb.append("<h2>AVAILABILITY FOR INTERVIEW</h2>");
                sb.append("<input type='hidden' name='interviewId'  value='"+interviewId+"'>");
                
                sb.append("<label class='mt-radio'>");
                sb.append("<input type='radio' name='type' id='type' value='1'/>Available&nbsp;<span></span>");
                sb.append("</label>");
                sb.append("<label class='mt-radio'>");
                sb.append("<input type='radio' name='type' id='type' value='2' />Not Available&nbsp;<span></span>");
                sb.append("</label>");
                
                sb.append("<div class='col-lg-12 col-md-12 col-sm-12 col-4 form_group'>");
                sb.append("<label class='form_label'>Add Remarks</label>");
                sb.append("<textarea id='remark' name='remark' class='form-control' rows='4' maxlength='500'></textarea>");
                sb.append("</div>");
                
                sb.append("<div class='row'>");
                sb.append("<div class='col-lg-12 col-md-12 col-sm-12 col-12 text-center'  id='saveRem'>");
                sb.append("<a href=\"javascript: ;\" onclick=\"javascript: saveAvailability('" + interviewId + "');\" class='save_page'>Save &nbsp;&nbsp;<img src='../assets/images/save.png'></a>");
                sb.append("</div>");
                sb.append("</div>");
            }
            String st = sb.toString();
            sb.setLength(0);
            response.getWriter().write(st);
        
        } else {
            response.getWriter().write("Please check your login session....");
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
%>