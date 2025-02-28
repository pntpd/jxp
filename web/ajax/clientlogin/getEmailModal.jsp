<%@page contentType="text/html"%>
<%@page language="java" import="java.util.ArrayList"%>
<%@page import="com.web.jxp.user.UserInfo" %>
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
            String interviewIds = request.getParameter("interviewId") != null && !request.getParameter("interviewId").equals("") ? vobj.replaceint(request.getParameter("interviewId")) : "0";
            int shortlistId = Integer.parseInt(shortlistIds);
            int interviewId = Integer.parseInt(interviewIds);
            //ClientloginInfo info = clientlogin.getDetails(shortlistId);
            ClientloginInfo info2 = clientlogin.getDetailsById(interviewId);
            String web_path = clientlogin.getMainPath("web_path");
            String name = "", date= "", time= "", linkLoc ="", email = "", co_email="", re_email ="", ma_email ="", ccVal ="", position=""; 
            int jobpostId = 0;
            if(info2 != null)
            {
                name = info2.getName() != null ? info2.getName(): "";
                date = info2.getDate()!= null ? info2.getDate(): "";
                time = info2.getTime()!= null ? info2.getTime(): "";
                linkLoc = info2.getLinkloc()!= null ? info2.getLinkloc(): "";
                email = info2.getEmail()!= null ? info2.getEmail(): "";
                ma_email = info2.getMa_email()!= null ? info2.getMa_email(): "";
                co_email = info2.getCo_email()!= null ? info2.getCo_email(): "";
                re_email = info2.getRe_email()!= null ? info2.getRe_email(): "";
                position = info2.getPositionname()!= null ? info2.getPositionname(): "";
                jobpostId = info2.getJobpostId();
            }
            if(!ma_email.equals(""))
            {
                ccVal = ma_email;
            }
            if(!re_email.equals(""))
            {
                ccVal += ","+re_email;
            }
            if(!co_email.equals(""))
            {
                ccVal += ","+co_email;
            }

            StringBuffer sb = new StringBuffer();
                        
            String message = "Dear "+name+
                    
            "\n\nYour interview has been scheduled. Please find the details below:\n\n"+
                    
            "Details: \n"+
            "Position: "+position+"\n"+
            "Date: "+date+"\n"+
            "Time: "+time+"\n"+
            "Location/Mode: "+linkLoc+"\n\n"+

            "Kindly confirm your availability through JourneyXPro portal using the link below. Log in with your registered email address to complete the process:\n"+
            web_path+"/jxp/crewlogin";
            
            String subject = "Interview Schedule Invitation";
            if (info2 != null)
            {
                sb.append("<h2>EMAIL TO CANDIADTE</h2>");
                sb.append("<div class='row client_position_table'>");
                sb.append("<div class='col-lg-12 col-md-12 col-sm-12 col-12 form_group'>");
                sb.append("<label class='form_label'>From</label>");
                sb.append("<input name='fromval' class='form-control' placeholder='' value = 'journeyxpro@journeyxpro.com' readonly>");
                sb.append("</div>");
                sb.append("<div class='col-lg-12 col-md-12 col-sm-12 col-12 form_group'>");
                sb.append("<label class='form_label'>To</label>");
                sb.append("<input name='toval' class='form-control' placeholder='' value = '" + email+ "'>");
                sb.append("</div>");
                sb.append("<div class='col-lg-12 col-md-12 col-sm-12 col-12 form_group'>");
                sb.append("<label class='form_label'>CC</label>");
                sb.append("<input name='ccval' class='form-control' placeholder='' value = '" + ccVal + "'>");
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
                sb.append("<a href=\"javascript: ;\" onclick=\"javascript: mailToCandidate('" + interviewId + "', '"+jobpostId+"');\" class='save_page'>Send &nbsp;&nbsp;<img src='../assets/images/share-as-email.png'></a>");
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