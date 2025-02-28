<%@page contentType="text/html"%>
<%@page language="java" import="java.util.ArrayList"%>
<%@page import="com.web.jxp.crewlogin.CrewloginInfo" %>
<%@page import="com.web.jxp.feedback.FeedbackInfo" %>
<%@page import="com.web.jxp.clientselection.ClientselectionInfo"%>
<jsp:useBean id="feedback" class="com.web.jxp.feedback.Feedback" scope="page"/>
<jsp:useBean id="clientselection" class="com.web.jxp.clientselection.Clientselection" scope="page"/>
<jsp:useBean id="vobj" class="com.web.jxp.base.Validate" scope="page"/>
<%
    try 
    {
        if (session.getAttribute("CREWLOGIN") != null) 
        {
            String shortlistIds = request.getParameter("shortlistId") != null && !request.getParameter("shortlistId").equals("") ? vobj.replaceint(request.getParameter("shortlistId")) : "0";
            String types = request.getParameter("type") != null && !request.getParameter("type").equals("") ? vobj.replaceint(request.getParameter("type")) : "0";
            int shortlistId = Integer.parseInt(shortlistIds);
            int type = Integer.parseInt(types);
            
            FeedbackInfo info2 = feedback.getDetailsById(shortlistId, type);
           
            String name = "", date= "",str="", co_email="", re_email ="", position="", remarks =""; 
            int assetId = 0, clientId = 0;
            if(info2 != null)
            {
                name = info2.getName() != null ? info2.getName(): "";
                date = info2.getDate()!= null ? info2.getDate(): "";
                co_email = info2.getCo_email()!= null ? info2.getCo_email(): "";
                re_email = info2.getRe_email()!= null ? info2.getRe_email(): "";
                position = info2.getPositionname()!= null ? info2.getPositionname(): "";
                remarks = info2.getRemarks()!= null ? info2.getRemarks(): "";
                assetId = info2.getClientassetId();
                clientId = info2.getClientId();
            }
            if(type == 1)
                str = "Offer Accepted";
            else
                str = "Offer Rejected";
            StringBuffer sb = new StringBuffer();
                        
            String message = "Dear Coordinator,"+

            "\n\nThe candidate  "+name+" has responded to the job offer."+

            "Details:\n"+
            "Position: "+position+"\n"+
            "Candidate's Response: "+str+"\n"+
            "Date: "+date+"\n\n"+

            "Please proceed with the appropriate next steps as per the recruitment process.";
            String subject = "Candidate Job Offer Response Notification";
            
            if (info2 != null) 
            {
                sb.append("<h2>SEND EMAIL</h2>");
                sb.append("<input type='hidden' name='type' value='"+type+"'>");
                sb.append("<input type='hidden' name='assetId' value='"+assetId+"'>");
                sb.append("<input type='hidden' name='clientId' value='"+clientId+"'>");
                sb.append("<input type='hidden' name='remark' value='"+remarks+"'>");
                sb.append("<input type='hidden' name='shortlistId' value='"+shortlistId+"'>");
                sb.append("<div class='row client_position_table'>");
                sb.append("<div class='col-lg-12 col-md-12 col-sm-12 col-12 form_group'>");
                sb.append("<label class='form_label'>From</label>");
                sb.append("<input name='fromval' class='form-control' placeholder='' value = 'journeyxpro@journeyxpro.com' readonly>");
                sb.append("</div>");
                sb.append("<div class='col-lg-12 col-md-12 col-sm-12 col-4 form_group'>");
                sb.append("<label class='form_label'>To</label>");
                sb.append("<input name='toval' class='form-control' placeholder='' value = '" + co_email+ "' readonly>");
                sb.append("</div>");
                sb.append("<div class='col-lg-12 col-md-12 col-sm-12 col-4 form_group'>");
                sb.append("<label class='form_label'>CC</label>");
                sb.append("<input name='ccval' class='form-control' placeholder='' value = '" + re_email + "' readonly>");
                sb.append("</div>");
                sb.append("<div class='col-lg-12 col-md-12 col-sm-12 col-4 form_group'>");
                sb.append("<label class='form_label'>BCC</label>");
                sb.append("<input  name='bccval' class='form-control' placeholder='' value = '' readonly>");
                sb.append("</div>");
                sb.append("<div class='col-lg-12 col-md-12 col-sm-12 col-4 form_group'>");
                sb.append("<label class='form_label'>Email Subject</label>");
                sb.append("<input name='subject' class='form-control' placeholder='' value='" + subject + "' readonly>");
                sb.append("</div>");
                sb.append("<div class='col-lg-12 col-md-12 col-sm-12 col-4 form_group'>");
                sb.append("<label class='form_label'>Email Body</label>");
                sb.append("<textarea name='description'  class='form-control' maxlength='2000' style = 'height : 180px' readonly />");
                sb.append(message);
                sb.append("</textarea>");
                sb.append("</div>");
//                if(type == 1)
//                {
                    sb.append("<div class='col-lg-12 col-md-12 col-sm-12 col-4 form_group'>");
                    sb.append("<label class='form_label'>Attach File(.pdf Only)</label>");
                    sb.append("<div class='row'>");
                    sb.append("<div class='col-lg-5 col-md-5 col-sm-12 col-12 form_group'>");
                    sb.append("<input id='upload1' name='offerFile' type='file' onchange=\"javascript: setClass('1');\">");
                    sb.append("<a href=\"javascript:;\" id='upload_link_1' class='attache_btn attache_btn_white uploaded_img1'>");
                    sb.append("<i class='fas fa-paperclip'></i> Attach");
                    sb.append("</a>");
                    sb.append("</div>");
//                }
                
                sb.append("</div>");
                sb.append("<div class='row'>");
                sb.append("<div class='col-lg-12 col-md-12 col-sm-12 col-12 text-center'  id='sendmaildiv'>");
                sb.append("<a href=\"javascript: ;\" onclick=\"javascript: sendmail();\" class='save_page'>Send &nbsp;&nbsp;<img src='../assets/images/share-as-email.png'></a>");
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