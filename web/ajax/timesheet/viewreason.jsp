<%@page import="com.web.jxp.timesheet.TimesheetInfo"%>
<%@page contentType="text/html"%>
<%@page language="java" import="java.util.ArrayList"%>
<%@page pageEncoding="UTF-8"%>
<%@page import="com.web.jxp.user.UserInfo"%>
<jsp:useBean id="timesheet" class="com.web.jxp.timesheet.Timesheet" scope="page"/>
<jsp:useBean id="vobj" class="com.web.jxp.base.Validate" scope="page"/>
<%
    try
    {
        UserInfo uInfo = (UserInfo) request.getSession().getAttribute("LOGININFO");
        if(uInfo != null)
        {
            String timesheetId_s = request.getParameter("timesheetId") != null && !request.getParameter("timesheetId").equals("") ? vobj.replaceint(request.getParameter("timesheetId")) : "0";
            int timesheetId = Integer.parseInt(timesheetId_s);
            if(timesheetId > 0)
            {
                String revision = "", remarks = "";
                TimesheetInfo info = timesheet.getRevisionById(timesheetId);
                if(info != null)
                {
                    revision = info.getRevision() != null && !info.getRevision().equals("") ? info.getRevision() : "&nbsp;";
                    remarks = info.getRemarks() != null && !info.getRemarks().equals("") ? info.getRemarks() : "&nbsp;";
                }
                StringBuilder sb = new StringBuilder();
                sb.append("<div class='col-lg-12 col-md-12 col-sm-12 col-4 form_group'>");
                sb.append("<label class='form_label'>Reason</label>");
                sb.append("<span class='form-control'>"+revision+"</span>");
                sb.append("</div>");
                sb.append("<div class='col-lg-12 col-md-12 col-sm-12 col-4 form_group'>");
                sb.append("<label class='form_label'>Comments</label>");
                sb.append("<span class='form-control'>"+remarks+"</span>");
                sb.append("</div>");
                String s = sb.toString();
                sb.setLength(0); 
                response.getWriter().write(s);
            }
            else
            {
                response.getWriter().write("Something went wrong!");
            }
        }
    }
    catch(Exception e)
    {
        e.printStackTrace();
    }
%>