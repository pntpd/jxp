<%@page import="java.util.Iterator"%>
<%@page import="java.util.Collection"%>
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
        String addper = "N", editper = "N";
        UserInfo uinfo = (UserInfo) session.getAttribute("LOGININFO");
        if(uinfo != null)
        {
            addper = uinfo.getAddper() != null ? uinfo.getAddper() : "N";
            editper = uinfo.getEditper() != null ? uinfo.getEditper() : "N";
        }
        if(uinfo != null)
        {
            String timesheetId_s = request.getParameter("timesheetId") != null && !request.getParameter("timesheetId").equals("") ? vobj.replaceint(request.getParameter("timesheetId")) : "0";
            int timesheetId = Integer.parseInt(timesheetId_s);
            if(timesheetId > 0)
            {
                StringBuilder sb = new StringBuilder();                
                sb.append("<div class='row client_position_table'>");
                sb.append("<div class='col-lg-12 col-md-12 col-sm-12 col-4 form_group'>");
                sb.append("<label class='form_label'>Select Reason</label>");
                sb.append("<select class='form-select' name='revisionId'>");
                Collection coll = timesheet.getRevisionReaons();   
                if (coll.size() > 0)
                {
                    Iterator iter = coll.iterator();
                    while (iter.hasNext()) 
                    {
                        TimesheetInfo info = (TimesheetInfo) iter.next(); 
                        if(info != null)
                        {
                            sb.append("<option value='"+info.getDdlValue()+"'>"+(info.getDdlLabel() != null ? info.getDdlLabel() : "")+"</option>");
                        }
                    }
                }
                sb.append("</select>");
                sb.append("</div>");
                sb.append("<div class='col-lg-12 col-md-12 col-sm-12 col-4 form_group'>");
                sb.append("<label class='form_label'>Comments</label>");
                sb.append("<textarea name='remarks' class='form-control' rows='6' maxlength='1000'></textarea>");
                sb.append("</div>");
                sb.append("</div>");
                if(addper.equals("Y") || editper.equals("Y"))
                {
                    sb.append("<div class='row'>");
                    sb.append("<div class='col-lg-12 col-md-12 col-sm-12 col-12 text-center' id='submitdiv'>");
                    sb.append("<a href=\"javascript: saveReasonForm('" + timesheetId + "');\" class='save_page'>Save &nbsp;&nbsp;<img src='../assets/images/save.png' /></a>");
                    sb.append("</div>");
                    sb.append("</div>");
                }
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