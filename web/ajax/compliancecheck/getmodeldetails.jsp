<%@page contentType="text/html"%>
<%@page language="java" import="java.util.ArrayList"%>
<%@page pageEncoding="UTF-8"%>
<%@page import="com.web.jxp.compliancecheck.CompliancecheckInfo"%>
<%@page import="com.web.jxp.user.UserInfo"%>
<jsp:useBean id="compliancecheck" class="com.web.jxp.compliancecheck.Compliancecheck" scope="page"/>
<jsp:useBean id="vobj" class="com.web.jxp.base.Validate" scope="page"/>
<%
    try 
    {
        if (session.getAttribute("LOGININFO") != null) 
        {
            UserInfo uinfo = ((UserInfo) request.getSession().getAttribute("LOGININFO"));
            String addper = "N";
            if (uinfo != null) 
            {
                addper = uinfo.getAddper() != null ? uinfo.getAddper() : "N";
            }
            String candidateIds = request.getParameter("candidateId") != null && !request.getParameter("candidateId").equals("") ? vobj.replaceint(request.getParameter("candidateId")) : "-1";
            String checkpointIds = request.getParameter("checkpointId") != null && !request.getParameter("checkpointId").equals("") ? vobj.replaceint(request.getParameter("checkpointId")) : "0";
            String shortlistccIds = request.getParameter("shortlistccId") != null && !request.getParameter("shortlistccId").equals("") ? vobj.replaceint(request.getParameter("shortlistccId")) : "0";
            String shortlistIds = request.getParameter("shortlistId") != null && !request.getParameter("shortlistId").equals("") ? vobj.replaceint(request.getParameter("shortlistId")) : "0";
            String statuss = request.getParameter("status") != null && !request.getParameter("status").equals("") ? vobj.replaceint(request.getParameter("status")) : "0";
            int status = Integer.parseInt(statuss);

            int candidateId = Integer.parseInt(candidateIds);
            int checkpointId = Integer.parseInt(checkpointIds);
            int shortlistccId = Integer.parseInt(shortlistccIds);
            int shortlistId = Integer.parseInt(shortlistIds);

            String remarks = compliancecheck.getRemarksbycheckpointId(checkpointId);
            ArrayList list = new ArrayList();
            String fieldremarks = "";
            int fieldstatus = 0;
            if (shortlistccId > 0) {
                CompliancecheckInfo info = compliancecheck.getShortlistlistcc(shortlistccId, checkpointId, shortlistId);
                fieldremarks = info.getRemarks();
                fieldstatus = info.getStatus();
                list = compliancecheck.getShortlisthistorylist(shortlistccId, checkpointId, shortlistId);
            }

            int size = list.size();
            StringBuilder sb = new StringBuilder();
            sb.append("<div class='col-lg-12'>");
            sb.append("<h2>COMPLIANCE CHECKS</h2>");
            sb.append("<div class='row ver_work_area'>");
            sb.append("<div class='col-lg-12 col-md-12 col-sm-12 col-4 form_group'>");
            sb.append("<div class='mt-radio-list'>");
            sb.append("<div class='form-check '>");
            sb.append("<input type='hidden' name='rbchecked'  ");
            if(fieldstatus == 1)
            {
            sb.append("  value='1' ");
            }
            else if(fieldstatus == 2)
            {
            sb.append("  value='2' ");
            }
            else{
            sb.append("  value='0' ");
            }
            sb.append(" />");
            sb.append("<input class='form-check-input' type='checkbox' id='stcb1' value='2' name='stcb1' onchange=\"javascript: setStatuscb('1');\" ");
            if(fieldstatus == 2)
            {
                sb.append(" checked ");
            }
            sb.append(" />");
            sb.append("&nbsp;<label class='form-check-label' for='rbcomverified'>Checked</label>");
            sb.append("</div>");
            sb.append("<div class='form-check'>");
            sb.append("<input class='form-check-input' type='checkbox' id='stcb2' value='1' name='stcb2' onchange=\"javascript: setStatuscb('2');\" ");
            if(fieldstatus == 1 || fieldstatus == 2)
            {
                sb.append(" checked ");
            }
            sb.append(" />");
            sb.append("&nbsp;<label class='form-check-label' for='rbminverified'>Minimum Checked</label>");
            sb.append("</div>");
            sb.append("</div>");
            sb.append("</div>");
            sb.append("<div class='col-lg-12 col-md-12 col-sm-12 col-4 form_group'>");
            sb.append("<label class='form_label'>Instruction Checked</label>");
            sb.append("<span class='form-control'>" + remarks + " </span>");
            sb.append("</div>");
            sb.append("<div class='col-lg-12 col-md-12 col-sm-12 col-4 form_group'>");
            sb.append("<label class='form_label'>Remarks</label>");
            sb.append("<textarea class='form-control' name='remarks' id='remarks' value=''  rows='3' maxlength='500'/>"+fieldremarks+"</textarea>");
            sb.append("</div>");
            if (size > 0)
            {
                sb.append("<div class='col-lg-12 col-md-12 col-sm-12 col-4 form_group'>");
                sb.append("<label class='form_label'>Compliance Details</label>");
                sb.append("<div class='full_width veri_details'>");

                for (int i = 0; i < size; i++)
                {
                    CompliancecheckInfo info = (CompliancecheckInfo) list.get(i);
                    if (info != null) 
                    {
                        sb.append("<ul>");
                        sb.append("<li>" + (info.getDate() != null && !info.getDate().equals("") ? info.getDate() : "") + "</li>");
                        sb.append("<li>" + (info.getName() != null && !info.getName().equals("") ? info.getName() : "") + "</li>");
                        sb.append("<li>" + (info.getRemarks() != null && !info.getRemarks().equals("") ? info.getRemarks() : "") + "</li>");
                        sb.append("</ul>");
                    }
                }
                sb.append("</div>");
                sb.append("</div>");
            }
            sb.append("</div>");
            sb.append("<div class='row'>");
                sb.append("<div class='col-lg-12 col-md-12 col-sm-12 col-12 text-center' id = 'submitdiv'>");
                sb.append("<a href=\"../talentpool/TalentpoolAction.do?doView=yes&candidateId=" + candidateId + "\" class='view_prof mr_15' target = '_blank'>View Profile</a>");
                if (addper.equals("Y") && status != 3) {
                    sb.append("<a href='javascript: submitForm();' class='save_page'><img src='../assets/images/shield.png'> Checked</a></div>");
                }
            
            sb.append("<input type='hidden' name='checkpointId' id='checkpointId' value='" + checkpointId + "'/>");
            sb.append("<input type='hidden' name='shortlistccId' id='shortlistccId' value='" + shortlistccId + "' />");

            sb.append("</div>");
            sb.append("</div>");
            sb.append("</div>");

            String st1 = sb.toString() + "#@#" + "";
            sb.setLength(0);
            response.getWriter().write(st1);
        } else {
            response.getWriter().write("Please check your login session....");
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
%>