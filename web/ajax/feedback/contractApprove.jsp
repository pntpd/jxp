<%@page import="com.web.jxp.talentpool.TalentpoolInfo"%>
<%@page contentType="text/html"%>
<%@page language="java" import="java.util.ArrayList"%>
<%@page pageEncoding="UTF-8"%>
<%@page import="com.web.jxp.crewlogin.CrewloginInfo"%>
<jsp:useBean id="talentpool" class="com.web.jxp.talentpool.Talentpool" scope="page"/>
<jsp:useBean id="vobj" class="com.web.jxp.base.Validate" scope="page"/>
<%
    try 
    {
        if (session.getAttribute("CREWLOGIN") != null) 
        {            
            String contractdetailIds = request.getParameter("contractdetailId") != null && !request.getParameter("contractdetailId").equals("") ? vobj.replaceint(request.getParameter("contractdetailId")) : "0";
            String candidateIds = request.getParameter("candidateId") != null && !request.getParameter("candidateId").equals("") ? vobj.replaceint(request.getParameter("candidateId")) : "0";
            String typeIds = request.getParameter("type") != null && !request.getParameter("type").equals("") ? vobj.replaceint(request.getParameter("type")) : "0";
            
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
            sb.append("<h2>Crew Contract Approval</h2>");
            sb.append("<input type='hidden' name='contractdetailId' value='"+contractdetailId+"' />");
            sb.append("<div class='row client_position_table'>");
            sb.append("<div class='col-lg-12 col-md-12 col-sm-12 col-12 form_group'>");
            sb.append("<div class='row com_label_value form_group'>");
            sb.append("<div class='col-lg-3 col-md-3 col-sm-3 col-12'><label>Client Asset</label></div>");
            sb.append("<div class='col-lg-9 col-md-9 col-sm-9 col-12'><span>"+clientName+" | "+assetName+"</span></div>");
            sb.append("</div>");
            sb.append("<div class='row com_label_value form_group'>");
            sb.append("<div class='col-lg-3 col-md-3 col-sm-3 col-12'><label>Position</label></div>");
            sb.append("<div class='col-lg-9 col-md-9 col-sm-9 col-12'><span>"+position+"</span></div>");
            sb.append("</div>");
            sb.append("<div class='col-lg-12 col-md-12 col-sm-12 col-12 form_group'>");
            sb.append("<label class='form_label'>Add Remarks</label>");
            sb.append("<textarea id='remark' name='remark' class='form-control' rows='4' maxlength='500'></textarea>");
            sb.append("</div>");
            sb.append("<div class='col-lg-4 col-md-4 col-sm-4 col-12 form_group'>");
            sb.append("<label class='form_label'>Attach signed contract</label>");
            
            sb.append("<input type='file' name='contractfile' id='upload1' onchange=\"javascript: setClass('1');\"/>");
            sb.append("<a href='javascript:;' id='upload_link_1' class='attache_btn attache_btn_white uploaded_img1'><i class='fas fa-paperclip'></i> Attach</a>");
            
            sb.append("<!-- <a href='javascript:;' class='view_mode float-end trash_icon'><i class='ion ion-ios-trash'></i></a>");
            sb.append("<a href='javascript:;' class='float-end view_icon mr_8' data-bs-toggle='modal' data-bs-target='#view_pdf_modal'><i class='ion ion-ios-search'></i></a> -->");
//            sb.append("<div class='down_prev'><a href='javascript:;' data-bs-toggle='modal' data-bs-target='#view_pdf_modal'>Preview All</a></div>");
            sb.append("</div>");
            sb.append("</div>");
            sb.append("</div>");
            sb.append("<div class='row'>");
            sb.append("<div class='col-lg-12 col-md-12 col-sm-12 col-12 text-center' id='submitdiv'>");
            sb.append("<a href=\"javascript: submitApproveForm('"+contractdetailId+"', '"+type+"' );\" class='save_page'>Approve Contract</a>");
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