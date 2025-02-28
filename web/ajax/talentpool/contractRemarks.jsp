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
            
            int contractdetailId = Integer.parseInt(contractdetailIds);
            int candidateId = Integer.parseInt(candidateIds);
            String clientName = "", assetName = "", position ="", remarks1 = "", remarks2 ="";
            TalentpoolInfo info = talentpool.getCandidateDetail(candidateId);
            if(info != null)
            {  
                clientName = info.getClientName() != null ? info.getClientName() : "";
                assetName = info.getAssetName()!= null ? info.getAssetName() : "";
                position = info.getPosition()!= null ? info.getPosition() : "";
            }             
            TalentpoolInfo rinfo = ddl.getDetailFormodify(contractdetailId);
            if(rinfo != null)
            {  
                remarks1 = rinfo.getRemarks1() != null ? rinfo.getRemarks1() : "";
                remarks2 = rinfo.getRemarks2() != null ? rinfo.getRemarks2() : "";
            }             
            StringBuilder sb = new StringBuilder();
                  
            sb.append("<div class='row'>");
            sb.append("<div class='col-lg-12'>");
            sb.append("<h2>Crew Contract Information</h2>");
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
            
            if(remarks2 != null)
            {
                sb.append("<div class='col-lg-12 col-md-12 col-sm-12 col-12 form_group'>");
                    sb.append("<label class='form_label'>Company Remarks</label>");
                    sb.append("<span class='form-control'>"+(!remarks2.equals("") ? remarks2 : "&nbsp;")+"</span>");
                sb.append("</div>");
                if(remarks1 != null)
                {
                    sb.append("<div class='col-lg-12 col-md-12 col-sm-12 col-12 form_group'>");
                        sb.append("<label class='form_label'>Crew Remarks</label>");
                        sb.append("<span class='form-control'>"+(!remarks1.equals("") ? remarks1 : "&nbsp;")+"</span>");
                    sb.append("</div>");
                }
            }           
            
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