<%@page import="com.web.jxp.talentpool.TalentpoolInfo"%>
<%@page contentType="text/html"%>
<%@page language="java" import="java.util.ArrayList"%>
<%@page pageEncoding="UTF-8"%>
<%@page import="com.web.jxp.user.UserInfo"%>
<jsp:useBean id="talentpool" class="com.web.jxp.talentpool.Talentpool" scope="page"/>
<jsp:useBean id="ddl" class="com.web.jxp.talentpool.Ddl" scope="page"/>
<jsp:useBean id="vobj" class="com.web.jxp.base.Validate" scope="page"/>
<%
    try 
    {
        if (session.getAttribute("LOGININFO") != null) 
        {            
            String contractdetailIds = request.getParameter("contractdetailId") != null && !request.getParameter("contractdetailId").equals("") ? vobj.replaceint(request.getParameter("contractdetailId")) : "0";
            String candidateIds = request.getParameter("candidateId") != null && !request.getParameter("candidateId").equals("") ? vobj.replaceint(request.getParameter("candidateId")) : "0";
            String typeIds = request.getParameter("type") != null && !request.getParameter("type").equals("") ? vobj.replaceint(request.getParameter("type")) : "0";
            String filename = request.getParameter("filename") != null && !request.getParameter("filename").equals("") ? vobj.replacedesc(request.getParameter("filename")) : "";
            
            int contractdetailId = Integer.parseInt(contractdetailIds);
            int candidateId = Integer.parseInt(candidateIds);
            int type = Integer.parseInt(typeIds);
            TalentpoolInfo info = talentpool.getCandidateDetail(candidateId);
            String clientName = "", assetName = "", position ="";
            if(info != null)
            {  
                clientName = info.getClientName() != null ? info.getClientName() : "";
                assetName = info.getAssetName()!= null ? info.getAssetName() : "";
                position = info.getPosition()!= null ? info.getPosition() : "";
            }
            StringBuilder sb = new StringBuilder();
                  
            sb.append("<div class='row'>");   
            
            sb.append("<div class='col-lg-12'>");
            sb.append("<input type='hidden' name='typeId' value='"+type+"' />");
            sb.append("<input type='hidden' name='file1' value='"+filename+"' />");
            
            sb.append("<h2>Crew Contract Approval</h2>");
            
            sb.append("<div class='row client_position_table'>");
            sb.append("<div class='col-lg-12 col-md-12 col-sm-12 col-4 form_group'>");
            sb.append("<div class='row com_label_value'>");
            sb.append("<div class='col-md-3'><label>Client Asset</label></div>");
            sb.append("<div class='col-md-9'><span>"+clientName+" | "+assetName+"</span></div>");
            sb.append("</div>");
            
            sb.append("<div class='row com_label_value form_group'>");
            sb.append("<div class='col-md-3'><label>Position</label></div>");
            sb.append("<div class='col-md-9'><span>"+position+"</span></div>");
            sb.append("</div>");
            
            sb.append("<div class='col-lg-12 col-md-12 col-sm-12 col-4 form_group'>");
            sb.append("<label class='form_label'>Add Remarks</label>");
            sb.append("<textarea id='remark' name='remark' class='form-control' rows='4' maxlength='500'></textarea>");
            sb.append("</div>");
            
            sb.append("<div class='col-lg-4 col-md-4 col-sm-4 col-4 form_group'>");
            sb.append("<label class='form_label'>Attach signed contract</label>");            
            sb.append("<input type='file' name='contractfile' id='upload1' onchange=\"javascript: setClass('1');\"/>");
            sb.append("<a href='javascript:;' id='upload_link_1' class='attache_btn attache_btn_white uploaded_img1'><i class='fas fa-paperclip'></i> Attach</a>");
            sb.append("</div>");           
            
            sb.append("<div class='col-lg-7 col-md-7 col-sm-12 col-12 form_group'>");
            if(type == 2)
            {
                sb.append("<div class='mt-checkbox-inline'>");
                sb.append("<label class='mt-checkbox mt-checkbox-outline'> No Additional Attachment<input type='checkbox' value='1' name='checktype' id='checktype' />");
                sb.append("<span></span>");
                sb.append("</label>");
                sb.append("</div>");
            }
            sb.append("</div>");
            sb.append("</div>");
            
            if(type == 2)
            {
                TalentpoolInfo info1 = ddl.setContractEmailDetail(candidateId);
                String subject = "", mailbody = "";
                mailbody = "Dear Candidate, \n \n Please find your employment contract for the " + info1.getPosition() + " position at " + info1.getClientName() +"-"+ info.getAssetName()+". \n Please review the document carefully, sign where indicated, and return a scanned copy to us at your earliest convenience. \n Alternatively, you can also upload the signed copy directly through your Crew application.";
                subject = "Employment Contract - Action Required";

                sb.append("<div class='col-lg-12 col-md-12 col-sm-12 col-4 form_group'>");
                sb.append("<label class='form_label'>From</label>");
                sb.append("<input name='fromval' class='form-control' placeholder='' value = 'journeyxpro@journeyxpro.com' readonly>");
                sb.append("</div>");
                sb.append("<div class='col-lg-12 col-md-12 col-sm-12 col-4 form_group'>");
                sb.append("<label class='form_label'>To</label>");
                sb.append("<input name='toval' class='form-control' placeholder='' value = '"+info1.getEmailId()+"' maxlength='200'/>");
                sb.append("</div>");
                sb.append("<div class='col-lg-12 col-md-12 col-sm-12 col-4 form_group'>");
                sb.append("<label class='form_label'>CC</label>");
                sb.append("<input name='ccval' class='form-control' placeholder='' value = '"+info1.getCcaddress()+"' maxlength='200'/>");
                sb.append("</div>");
                sb.append("<div class='col-lg-12 col-md-12 col-sm-12 col-4 form_group'>");
                sb.append("<label class='form_label'>BCC</label>");
                sb.append("<input  name='bccval' class='form-control' placeholder='' value = '' maxlength='200'/>");
                sb.append("</div>");
                sb.append("<div class='col-lg-12 col-md-12 col-sm-12 col-4 form_group'>");
                sb.append("<label class='form_label'>Email Subject</label>");
                sb.append("<input name='subject' class='form-control' placeholder='' value='" + subject + "' readonly>");
                sb.append("</div>");
                sb.append("<div class='col-lg-12 col-md-12 col-sm-12 col-4 form_group'>");
                sb.append("<label class='form_label'>Email Body</label>");
                sb.append("<textarea name='description'  class='form-control' maxlength='2000' style = 'height : 180px'/>");
                sb.append(mailbody);
                sb.append("</textarea>");
                sb.append("</div>");
            }
            
            sb.append("<div class='row'>");
            sb.append("<div class='col-lg-12 col-md-12 col-sm-12 col-12 text-center' id='submitdiv'>");
            sb.append("<a href=\"javascript: submitApproveForm('"+contractdetailId+"', '"+type+"' );\" class='save_page'>Approve Contract</a>");
            sb.append("</div>");
            sb.append("</div>");
            sb.append("</div>");
            sb.append("</div>");
            sb.append("</div>");
            
            String st1 = sb.toString();
            sb.setLength(0);
            response.getWriter().write(st1);
        } else {
            response.getWriter().write("Please check your login session....");
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
%>