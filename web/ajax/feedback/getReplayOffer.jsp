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
            String shortlistIds = request.getParameter("shortlistId") != null && !request.getParameter("shortlistId").equals("") ? vobj.replaceint(request.getParameter("shortlistId")) : "0";
            int shortlistId = Integer.parseInt(shortlistIds);
           
            sb.append("<h2>REPLY TO OFFER</h2>");
            sb.append("<input type='hidden' name='shortlistId' value='"+shortlistId+"'>");
            sb.append("<input type='hidden' name='type' value=''>");
            
            sb.append("<div class='col-lg-12 col-md-12 col-sm-12 col-12 text-center'>");
            sb.append("<div class='row'>");
            sb.append("<div class='col-lg-12 col-md-12 col-sm-12 col-12 mb_15'>");
            sb.append("<div class='main-heading'><h4>Remarks</h4></div>");
            sb.append("<textarea class='remark form-control' id='remark' name='remark' maxlength='500' rows='5'>");
            sb.append("</textarea>");
            sb.append("</div>");

            sb.append("<div class='col-lg-12 col-md-12 col-sm-12 col-12 text-center' id='saveEV'>");
                sb.append("<a href=\"javascript: saveOffer('1');\" class='save_page mr_15'> Accept</a>");
                sb.append("<a href=\"javascript: saveOffer('2');\" class='save_page'> Reject</a>");
            sb.append("</div>");
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