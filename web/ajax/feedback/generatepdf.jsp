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
                CrewloginInfo cinfo = (CrewloginInfo) session.getAttribute("CREWLOGIN");
                String name = "", position = "";
                if(cinfo != null)
                {
                    name = cinfo.getName() != null ? cinfo.getName() : "";
                    position = cinfo.getPosition() != null ? cinfo.getPosition() : "";
                }
                StringBuilder sb = new StringBuilder();
                String Ids = request.getParameter("Id") != null && !request.getParameter("Id").equals("") ? vobj.replaceint(request.getParameter("Id")) : "0";
                int trackerId = Integer.parseInt(Ids);
                FeedbackInfo info = null;
                ArrayList list = new ArrayList();
                if (trackerId > 0) 
                {
                    info = (FeedbackInfo) session.getAttribute("PCODE_INFO");
                    String role = "", date = "";
                    if (info != null) 
                    {
                        list = feedback.getQuestionListByFcroaleId(info.getFcroleId());
                        role = info.getRole() != null ? info.getRole() : "";
                        date = info.getCompleteByDate() != null ? info.getCompleteByDate() : "";
                        String assetName = info.getClientasset() != null ? info.getClientasset() : "";
                        String clientName = info.getClientName() != null ? info.getClientName() : "";
                        sb.append(feedback.getGeneratePDF(role, date, list, trackerId, clientName, assetName, name, position));
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