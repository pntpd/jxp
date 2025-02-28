<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.Collection"%>
<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@page import="com.web.jxp.user.UserInfo" %>
<%@page import="com.web.jxp.managetraining.ManagetrainingInfo" %>
<jsp:useBean id="managetraining" class="com.web.jxp.managetraining.Managetraining" scope="page"/>
<jsp:useBean id="vobj" class="com.web.jxp.base.Validate" scope="page"/>
<%
    try 
    {
        if (session.getAttribute("LOGININFO") != null) 
        {
            String str = "";
            if (session.getAttribute("ASSIGNMAILINFO") != null) 
            {
                ManagetrainingInfo ainfo = null;
                if (session.getAttribute("ASSIGNMAILINFO") != null) 
                {
                    ainfo = (ManagetrainingInfo) session.getAttribute("ASSIGNMAILINFO");                            
                }
                int crewrotationId = ainfo.getCrewrotationId();
                int courseId = ainfo.getCourseId();
                response.setContentType("text/html");
                response.setHeader("Cache-Control", "no-cache");
                String typefroms = request.getParameter("typefrom") != null && !request.getParameter("typefrom").equals("") ? vobj.replaceint(request.getParameter("typefrom")) : "";
                int typefrom = Integer.parseInt(typefroms);
                ManagetrainingInfo info = null;
                 if (session.getAttribute("COURSEINFO") != null) {
                 info = (ManagetrainingInfo)session.getAttribute("COURSEINFO");
                 }
                ManagetrainingInfo pinfo = managetraining.getCandidatemailId(crewrotationId);
                String view_trainingfiles = managetraining.getMainPath("view_trainingfiles");
                ArrayList list = new ArrayList();
                list = managetraining.getcourseattachment(courseId);
                int listsize = list.size();
                String mailbody = "";
                mailbody ="Dear "+pinfo.getName()+" \n \n Please find the Course Details attached. Feel free to connect in case of any concerns.\n \n Course name :"+info.getCourseName()+"\n Description :"
                        +info.getDescription()+"\n Course type :"+info.getCoursetype()+"\n Priority :"+info.getPriority()+"\n Level :"+info.getLevel()+
                        "\n Complete by date :"+ainfo.getCompleteby()+"\n Link :"+ainfo.getAlink()+"\n \n Thank you,\n Team OCS. \n Generated from: JouneryXPro - Crew Management System";
                StringBuffer sb = new StringBuffer();
                if (crewrotationId > 0) 
                {
                    sb.append("<div class='col-lg-12'>");
                    sb.append("<h2>EMAIL COURSE DETAILS</h2>");
                    sb.append("<div class='row client_position_table'>");
                    sb.append("<div class='col-lg-12 col-md-12 col-sm-12 col-4 form_group'>");
                    sb.append("<label class='form_label'>From</label>");
                    sb.append("<input name='fromval' class='form-control' placeholder='' value = 'journeyxpro@journeyxpro.com' readonly>");
                    sb.append("</div>");
                    sb.append("<div class='col-lg-12 col-md-12 col-sm-12 col-4 form_group'>");
                    sb.append("<label class='form_label'>To</label>");
                    sb.append("<input name='toval' class='form-control' placeholder='' value = '"+pinfo.getPositionName()+"'>");
                    sb.append("</div>");
                    sb.append("<div class='col-lg-12 col-md-12 col-sm-12 col-4 form_group'>");
                    sb.append("<label class='form_label'>CC</label>");
                    sb.append("<input name='ccval' class='form-control' placeholder=''>");
                    sb.append("</div>");
                    sb.append("<div class='col-lg-12 col-md-12 col-sm-12 col-4 form_group'>");
                    sb.append("<label class='form_label'>BCC</label>");
                    sb.append("<input name='bccval' class='form-control' placeholder=''>");
                    sb.append("</div>");
                    sb.append("<div class='col-lg-12 col-md-12 col-sm-12 col-4 form_group'>");
                    sb.append("<label class='form_label'>Email Subject</label>");
                    sb.append("<input name='subject' class='form-control' placeholder='' value='Training Details for "+info.getCourseName()+"' readonly>");
                    sb.append("</div>");
                    sb.append("<div class='col-lg-12 col-md-12 col-sm-12 col-4 form_group'>");
                    sb.append("<label class='form_label'>Email Body</label>");
                    sb.append("<textarea name='description' class='form-control' maxlength='500' style = 'height : 180px'/>");
                    sb.append(mailbody);
                    sb.append("</textarea>");
                    sb.append("</div>");
                    sb.append("<div class='col-lg-12 col-md-12 col-sm-12 col-4 form_group'>");
                    sb.append("<label class='form_label'>Course Attachments</label>");
                    sb.append("<div class='full_width veri_details'>");
                    for(int i = 0; i < listsize; i++)
                    {
                        ManagetrainingInfo finfo = (ManagetrainingInfo)list.get(i);
                        sb.append("<ul>");
                        sb.append("<li>"+finfo.getName()+"</li>");
                        sb.append("<li><a href=\""+view_trainingfiles+finfo.getFilename()+"\" target = '_blank'>View Attachment</a></li>");
                        sb.append("</ul>");
                    }
                    sb.append("</div>");
                    sb.append("</div>");
                    sb.append("</div>");
                    sb.append("<input type='hidden' name ='typefrom1' value = '"+typefrom+"'/>");
                    sb.append("<div class='row' id = 'dsavesendcoursemail'>");
                    sb.append("<div class='col-lg-12 col-md-12 col-sm-12 col-12 text-center'>");
                    sb.append("<a href=\"javascript: submitcoursemailForm();\" class='save_page'>Send &nbsp;&nbsp;<img src='../assets/images/share-as-email.png'></a>");
                    sb.append("</div>");
                    sb.append("</div>");
                    sb.append("</div>");
                } else {
                    sb.append("Something went wrong");
                }
                str = sb.toString();
                sb.setLength(0);
                response.getWriter().write(str);
            } else {
                response.setStatus(HttpServletResponse.SC_NO_CONTENT);
            }
        } else {
            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
        }
    } catch (Exception e) {

    }
%>