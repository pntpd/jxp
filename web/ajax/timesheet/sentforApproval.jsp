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
            String type_s = request.getParameter("type") != null && !request.getParameter("type").equals("") ? vobj.replaceint(request.getParameter("type")) : "0";
            String repeatIds = request.getParameter("repeatId") != null && !request.getParameter("repeatId").equals("") ? vobj.replaceint(request.getParameter("repeatId")) : "0";
            int repeatId = Integer.parseInt(repeatIds);
            int type = Integer.parseInt(type_s);
            
            if(timesheetId > 0)
            {               
                String email = "", froDate ="" , toDate = "", ccaddress = "", mailbody = "", excelfile ="";               
                
                TimesheetInfo info = timesheet.getClientEmailById(timesheetId);
                if(info != null)
                {                    
                    email = info.getEmail()!= null && !info.getEmail().equals("") ? info.getEmail() : "&nbsp;";
                    froDate = info.getFromDate()!= null && !info.getFromDate().equals("") ? info.getFromDate(): "&nbsp;";
                    toDate = info.getToDate()!= null && !info.getToDate().equals("") ? info.getToDate(): "&nbsp;";
                    ccaddress = info.getCcaddress()!= null && !info.getCcaddress().equals("") ? info.getCcaddress(): "&nbsp;";
                    excelfile = info.getExcelFile() != null ? info.getExcelFile(): "";
                }
                mailbody = "Dear Client,"
                        + "\n"
                        + "\nPlease find the timesheet generated from "+froDate+" to "+toDate+". "
                        + "Kindly go through the timesheet and send us a signed copy of acceptance."
                        +"\n"
                        + "\n Thank you, \n Team OCS";
                StringBuilder sb = new StringBuilder();
                sb.append("<input type='hidden' name='excelfile' value='"+excelfile+"'/>");
                sb.append("<input type='hidden' name='repeatId' value='"+repeatId+"'/>");
                sb.append("<div class='col-lg-12 col-md-12 col-sm-12 col-4 form_group'>");
                sb.append("<h2>EMAIL TIMESHEET TO CLIENT</h2>");
                sb.append("<label class='form_label'>To</label>");
                sb.append("<input class='form-control' name='email' value='"+email+"' placeholder=''>");
                sb.append("</div>");
                sb.append("<div class='col-lg-12 col-md-12 col-sm-12 col-4 form_group'>");
                sb.append("<label class='form_label'>CC</label>");
                sb.append("<input class='form-control' name='ccaddress' value='"+ccaddress+"'>");
                sb.append("</div>");
                sb.append("<div class='col-lg-12 col-md-12 col-sm-12 col-4 form_group'>");
                sb.append("<label class='form_label'>BCC</label>");
                sb.append("<input class='form-control' property='bccaddress' name='bccaddress' value='' placeholder=''>");
                sb.append("</div>");
                sb.append("<div class='col-lg-12 col-md-12 col-sm-12 col-4 form_group'>");
                sb.append("<label class='form_label'>Email Subject</label>");
                sb.append("<input class='form-control' placeholder='' name='subject' maxlength='100' value='Timesheet for Approval - "+froDate+" to "+toDate+"' />");
                sb.append("</div>");
                sb.append("<div class='col-lg-12 col-md-12 col-sm-12 col-4 form_group'>");
                sb.append("<label class='form_label'>Email Body</label>");
                sb.append("<textarea name='message' class='form-control' rows='6' maxlength='1000'>"+mailbody+"</textarea>");
                sb.append("</div>");
                sb.append("<div class='col-lg-12 col-md-12 col-sm-12 col-4 form_group'>");
                sb.append("<label class='form_label'>Additional Docs upload(5Mb) (Only .pdf)</label>");
                sb.append("<div class='row'>");
                sb.append("<div class='col-lg-5 col-md-5 col-sm-12 col-12 form_group'>");
                sb.append("<input type='file' name='sendApproveFile' id='upload2' onchange=\"javascript: setClass('2');\">");
                sb.append("<a href=\"javascript:;\" id='upload_link_2' class='attache_btn attache_btn_white uploaded_img1' >");
                sb.append("<i class='fas fa-paperclip'></i> Attach</a>");
                sb.append("</div>");
                
                sb.append("<div class='col-lg-7 col-md-7 col-sm-12 col-12 form_group'>");
                sb.append("<div class='mt-checkbox-inline'>");
                sb.append("<label class='mt-checkbox mt-checkbox-outline'> No Additional Attachment<input type='checkbox' value='1' name='checktype' id='checktype' />");
                sb.append("<span></span>");
                sb.append("</label>");
                sb.append("</div>");
                sb.append("</div>");
                
                sb.append("</div>");
                sb.append("</div>");
                if(addper.equals("Y") || editper.equals("Y"))
                {
                    sb.append("<div class='row'>");                
                    sb.append("<div class='col-lg-12 col-md-12 col-sm-12 col-12 text-center' id='submitdiv'>");
                    if(type == 1)
                        sb.append("<a href=\"javascript: sentApproval('" + timesheetId + "');\" class='save_page mr_15'>Send &nbsp;&nbsp;<img src='../assets/images/share-as-email.png'></a>");
                    else
                        sb.append("<a href=\"javascript: sentApprovalMail('" + timesheetId + "');\" class='save_page'>Send &nbsp;&nbsp;<img src='../assets/images/share-as-email.png'></a>");
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