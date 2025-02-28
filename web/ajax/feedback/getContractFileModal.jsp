<%@page import="java.util.Iterator"%>
<%@page import="java.util.Collection"%>
<%@page import="com.web.jxp.crewlogin.CrewloginInfo"%>
<%@page contentType="text/html"%>
<%@page language="java" import="java.util.ArrayList"%>
<%@page pageEncoding="UTF-8"%>
<%@page import="com.web.jxp.feedback.FeedbackInfo" %>
<jsp:useBean id="feedback" class="com.web.jxp.feedback.Feedback" scope="page"/>
<jsp:useBean id="vobj" class="com.web.jxp.base.Validate" scope="page"/>
<%
    try 
    {
        if (session.getAttribute("CREWLOGIN") != null) 
        {
            if (request.getParameter("contractdetailId") != null) 
            {
                StringBuilder sb = new StringBuilder();
                String contractdetailIds = request.getParameter("contractdetailId") != null && !request.getParameter("contractdetailId").equals("") ? vobj.replaceint(request.getParameter("contractdetailId")) : "0";
                String candidateIds = request.getParameter("candidateId") != null && !request.getParameter("candidateId").equals("") ? vobj.replaceint(request.getParameter("candidateId")) : "0";
                int candidateId = Integer.parseInt(candidateIds);
                int contractdetailId = Integer.parseInt(contractdetailIds);
                FeedbackInfo info = feedback.getDetailsForModal(contractdetailId);
               
                String file1 = "", file2 ="", file3 = "", date1 ="", date2 ="", date3 ="", username1 ="", username2 ="", username3 ="",
                contractName = "", statusValue = "", acceptanceDate= "", updateVal = "", stcolor = "";
                int approval1 = 0, flag4 = 0, approval2=0;
                FeedbackInfo cinfo = null;
                ArrayList list = feedback.getContractListing(candidateId, "", "", "", 0, 0, 0,0);
                for(int i = 0; i< list.size(); i++) 
                {
                    cinfo = (FeedbackInfo) list.get(i);
                    if(cinfo != null)
                    {
                        statusValue = cinfo.getStatusValue() != null ? cinfo.getStatusValue() : "";
                        acceptanceDate =  cinfo.getAcceptanceDate() != null ? cinfo.getAcceptanceDate() : "";                        
                        
                    }
                }                
                if(info != null )
                {
                    file1 = info.getFile1() != null ? info.getFile1(): "";
                    file2 = info.getFile2() != null ? info.getFile2(): "";
                    file3 = info.getFile3() != null ? info.getFile3(): "";
                    date1 = info.getDate1()!= null ? info.getDate1(): "";
                    date2 = info.getDate2()!= null ? info.getDate2(): "";
                    date3 = info.getDate3()!= null ? info.getDate3(): "";
                    contractName = info.getContract()!= null ? info.getContract(): "";
                    approval1 = info.getApproval1();
                    approval2 = info.getApproval2();
                    flag4 = info.getFlag4();
                    updateVal = info.getUpdateValue()!= null ? info.getUpdateValue() : "";
                }
                if(updateVal.equals("Pending"))
                        stcolor ="modal_status pen_bg";
                    else if(updateVal.equals("Completed"))
                        stcolor = "modal_status uploaded_bg";
                    else if(updateVal.equals("Expired"))
                        stcolor = "modal_status upload_expired_bg";
                sb.append("<div class='row'>");
                sb.append("<div class='col-lg-12'>");
                sb.append("<div class='row'>");
                sb.append("<div class='col'>");
                sb.append("<h2>PRODUCTION: GAS COMPRESSION</h2>");
                sb.append("</div>");
                sb.append("<div class='col col-xl-2 col-lg-2 col-md-2 col-sm-3 col-3 hist_prog_unass text-right'>");
                sb.append("<ul><li class='"+stcolor+"'> "+ (updateVal) +"</li></ul>");
                sb.append("</div>");
                sb.append("</div>");
                
                sb.append("<div class='row client_position_table fix_modal_body1'>");
               
                sb.append("<div class='col-lg-12 col-md-12 col-sm-12 col-12'>");
                sb.append("<div class='row'>");
                
                sb.append("<div class='col-lg-12 col-md-12 col-sm-12 col-12 form_group'>");
                sb.append("<label class='form_label'>"+(acceptanceDate)+"</label>");
                sb.append("</div>");
                
                sb.append("<div class='col-lg-12 col-md-12 col-sm-12 col-12 form_group'>");
                sb.append("<label class='form_label'>Description</label>");
                sb.append("<span class='full_width des_content'>Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eost accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy</span>");
                sb.append("</div>");
                
                sb.append("<div class='col-lg-12 col-md-12 col-sm-12 col-12 form_group'>");
                sb.append("<label class='form_label'>Instructions</label>");
                sb.append("<span class='full_width offline_online_content mb_10'>");
                sb.append("<h4>Offline Submission</h4>");
                sb.append("<ol type='1'>");
                sb.append("<li>Download printed copy of the assessment. </li>");
                sb.append("<li>Fill and Submit the printed copy of your assessment directly to the Crew Coordinator.</li>");
                sb.append("</ol>");
                sb.append("</span><span class='full_width offline_online_content'>");
                sb.append("<h4>Online Submission</h4>");
                sb.append("<ol type='1'>");
                sb.append("<li>Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore </li>");
                sb.append("<li>Et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum.</li>");
                sb.append("</ol>");
                sb.append("</span>");
                sb.append("</div>");
                
                sb.append("<div class='col-lg-12 col-md-12 col-sm-12 col-12 form_group'>");
                sb.append("<label class='form_label'>Contact</label>");
                sb.append("<span class='full_width des_content'>If you have any questions or run into any technical issues while taking the assessment, please get in touch with our support staff at email@id.com or by calling XXXXXXXX.</span>");
                sb.append("</div>");               
                
                sb.append("</div>");
                
                
                sb.append("</div>");
                sb.append("</div>");
                
                sb.append("<div class='row'>");
                sb.append("<div class='col-xl-12 col-lg-12 col-md-12 col-sm-12 col-12'>");
                sb.append("<div class='row justify-content-md-center align-items-center'>");
                sb.append("<div class='col-xl-3 col-lg-3 col-md-3 col-sm-4 col-4 text-center'>");
                sb.append("<a href='javascript:;' data-bs-toggle='modal' data-bs-target='#view_resume_list' onclick=\"javascript: viewContractDoc('"+contractdetailId+"','"+file1+"', '"+file2+"','"+file3+"');\" class='termi_btn'>View Contract Document </a>");
                sb.append("</div>");
                if( approval2 == 1 && flag4 <= 0){
                    sb.append("<div class='col-xl-3 col-lg-3 col-md-3 col-sm-4 col-4'>");  
                    sb.append("<a href='javascript:;' data-bs-toggle='modal' data-bs-target='#crew_contract_modal' onclick=\"javascript: getCrewDetails('"+(contractdetailId)+"', '1');\" class='termi_btn'>Accept Contract </a>");
                sb.append("</div>");
                }
                sb.append("</div>");
                sb.append("</div>");
                sb.append("</div>");
                
                sb.append("</div>");
                sb.append("</div>");
                String st1 = sb.toString();
                sb.setLength(0);
                response.getWriter().write(st1);
                    
                
            } else {
                response.setStatus(HttpServletResponse.SC_NO_CONTENT);
            }
        } else {
            response.getWriter().write("Please check your login session....");
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
%>