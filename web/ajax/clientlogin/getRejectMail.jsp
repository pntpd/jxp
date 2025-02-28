<%@page contentType="text/html"%>
<%@page language="java" import="java.util.ArrayList"%>
<%@page import="com.web.jxp.clientlogin.ClientloginInfo" %>
<jsp:useBean id="clientlogin" class="com.web.jxp.clientlogin.Clientlogin" scope="page"/>
<jsp:useBean id="vobj" class="com.web.jxp.base.Validate" scope="page"/>
<%
    try 
    {
        ClientloginInfo uInfo = (ClientloginInfo) request.getSession().getAttribute("MLOGININFO");
        if (uInfo != null)
        {
            String shortlistIds = request.getParameter("shortlistId") != null && !request.getParameter("shortlistId").equals("") ? vobj.replaceint(request.getParameter("shortlistId")) : "0";
            int shortlistId = Integer.parseInt(shortlistIds);
            ClientloginInfo info2 = clientlogin.getDetails(shortlistId);
            //ClientloginInfo info2 = clientlogin.getDetailsById(interviewId);
            String web_path = clientlogin.getMainPath("web_path");
            String name = "", co_email="", position=""; 
            int jobpostId = 0;
            if(info2 != null)
            {
                name = info2.getName() != null ? info2.getName(): "";           
                co_email = info2.getComailId()!= null ? info2.getComailId(): "";                
                position = info2.getPositionname()!= null ? info2.getPositionname(): "";
                jobpostId = info2.getJobpostId();
            }
            
            StringBuffer sb = new StringBuffer();
            
            String message = "Dear Coordinator,"+

            "\n\nThe candidate  "+name+" has been rejected.\n\n"+

            "Details:\n"+
            "Position: "+position+"\n"+
                    
            "Please proceed with the appropriate next steps as per the recruitment process.";
            
            String subject = "Manager Response for "+name;
            
            if (info2 != null)
            {
                sb.append("<h2>SEND EMAIL</h2>");
                sb.append("<div class='row client_position_table'>");
                sb.append("<div class='col-lg-12 col-md-12 col-sm-12 col-12 form_group'>");
                sb.append("<label class='form_label'>From</label>");
                sb.append("<input name='fromval' class='form-control' placeholder='' value = 'journeyxpro@journeyxpro.com' readonly>");
                sb.append("</div>");
                sb.append("<div class='col-lg-12 col-md-12 col-sm-12 col-12 form_group'>");
                sb.append("<label class='form_label'>To</label>");
                sb.append("<input name='toval' class='form-control' placeholder='' value = '" + co_email+ "'>");
                sb.append("</div>");
                sb.append("<div class='col-lg-12 col-md-12 col-sm-12 col-12 form_group'>");
                sb.append("<label class='form_label'>CC</label>");
                sb.append("<input name='ccval' class='form-control' placeholder='' value = ''>");
                sb.append("</div>");
                sb.append("<div class='col-lg-12 col-md-12 col-sm-12 col-12 form_group'>");
                sb.append("<label class='form_label'>BCC</label>");
                sb.append("<input  name='bccval' class='form-control' placeholder='' value = ''>");
                sb.append("</div>");
                sb.append("<div class='col-lg-12 col-md-12 col-sm-12 col-12 form_group'>");
                sb.append("<label class='form_label'>Email Subject</label>");
                sb.append("<input name='subject' class='form-control' placeholder='' value='" + subject + "' readonly>");
                sb.append("</div>");
                sb.append("<div class='col-lg-12 col-md-12 col-sm-12 col-12 form_group'>");
                sb.append("<label class='form_label'>Email Body</label>");
                sb.append("<textarea name='description'  class='form-control' maxlength='2000' style = 'height : 180px'/>");
                sb.append(message);
                sb.append("</textarea>");
                sb.append("</div>");
                
                sb.append("</div>");
                sb.append("<div class='row'>");
                sb.append("<div class='col-lg-12 col-md-12 col-sm-12 col-12 text-center'  id='sendmaildiv'>");
                sb.append("<a href=\"javascript: ;\" onclick=\"javascript: mailToCoordinator('"+shortlistId+"');\" class='save_page'>Send &nbsp;&nbsp;<img src='../assets/images/share-as-email.png'></a>");
                sb.append("</div>");
                sb.append("</div>");
            }
            String st = sb.toString();
            sb.setLength(0);
            response.getWriter().write(st);
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
%>