<%@page contentType="text/html"%>
<%@page language="java" import="java.util.ArrayList"%>
<%@page pageEncoding="UTF-8"%>
<%@page import="com.web.jxp.user.UserInfo" %>
<%@page import="com.web.jxp.talentpool.TalentpoolInfo" %>
<jsp:useBean id="talentpool" class="com.web.jxp.talentpool.Talentpool" scope="page"/>
<jsp:useBean id="vobj" class="com.web.jxp.base.Validate" scope="page"/>
<%
    try 
    {
        if (session.getAttribute("LOGININFO") != null) 
        {
            String userName = "";
            UserInfo uInfo = (UserInfo) session.getAttribute("LOGININFO");
            if (uInfo != null)
            {
               userName = uInfo.getName();
            }
            if (request.getParameter("candidateId") != null) 
            {
                StringBuilder sb = new StringBuilder();
                String candidateId_s = request.getParameter("candidateId") != null ? vobj.replaceint(request.getParameter("candidateId")) : "0";
                String typeIds = request.getParameter("typeId") != null ? vobj.replaceint(request.getParameter("typeId")) : "0";
                int typeId = Integer.parseInt(typeIds);
                int candidateId = Integer.parseInt(candidateId_s);
                if (candidateId > 0)
                {
                    ArrayList list = talentpool.getAlertdetailByCandidateId(candidateId, typeId);   
                    int size = list.size();
                    if (size > 0) 
                    {
                        sb.append("<div class='table-responsive1 mb-0'>");                            
                        sb.append("<table class='table table-striped'>");
                        sb.append("<thead>");
                        sb.append("<tr>");
                        sb.append("<th width='25%'><span><b>Date</b> </span></th>");
                        sb.append("<th width='15%'><span><b>Type</b> </span></th>");
                        sb.append("<th width='25%'><span><b>Alert</b> </span></th>");
                        sb.append("<th width='25%'><span><b>Updated By</b> </span></th>");
                        sb.append("<th width='10%' class='text-center'><span><b>Actions</b> </span></th>");
                        sb.append("</tr>");
                        sb.append("</thead>");
                        sb.append("<tbody>");
                        
                        for (int i = 0; i < size; i++) 
                        {
                            TalentpoolInfo info = (TalentpoolInfo) list.get(i);
                            if (info != null) 
                            {
                                String updatedBy = info.getName() != null && !info.getName().equals("") ? info.getName() : "-";
                                if(info.getApplytype() == 1)
                                {
                                    sb.append("<tr>"); 
                                    sb.append("<td>" + info.getDate() + "</td>");
                                    if(info.getDate() != null && !info.getDate().equals(""))
                                    {
                                        sb.append("<td>Verify</td>");
                                    }else{
                                        sb.append("<td>Expired</td>");
                                    }
                                    sb.append("<td>" + info.getRemarks() + "</td>");
                                        sb.append("<td>" + updatedBy+ "</td>");
//                                    if(info.getUserId() > 0){
//                                    }else{
//                                        sb.append("<td>" + info.getName() + "</td>");                                    
//                                    }
                                    if (info.getType() == 1) 
                                    {
                                        sb.append("<td class='hand_cursor text-center'><a class='ver_act' href=\"../verification/VerificationAction.do?doView=yes&verificationId=" + candidateId + "&tb=" + info.getTabno() + "\"  target = '_blank'>Verify</a></td>");
                                    }
                                    sb.append("</tr>");
                                }
                                if(info.getApplytype() == 3)
                                {
                                    if(info.getDate() != null && !info.getDate().equals("") )
                                    {
                                        sb.append("<tr>"); 
                                        sb.append("<td>" + info.getDate() + "</td>");
                                        if(info.getDate() != null && !info.getDate().equals(""))
                                        {
                                            sb.append("<td>Expired</td>");
                                        }else{
                                            sb.append("<td>Verify</td>");
                                        }
                                        sb.append("<td>" + info.getCoursename()+" expired"+ "</td>");
                                        sb.append("<td>" + updatedBy+ "</td>");
//                                        if(info.getUserId() > 0){
//                                            sb.append("<td>" + userName+ "</td>");
//                                        }else{
//                                            sb.append("<td>" + info.getName() + "</td>");                                    
//                                        }
                                        sb.append("<td class='hand_cursor text-center'><a class='ver_act' href=\"../documentexpiry/DocumentexpiryAction.do?&dropdownId="+1+ "&exp="+2+"&search="+candidateId+ "\" target = '_blank'>View</a></td>");
                                        sb.append("</tr>");
                                    }
                                }
                                //For Document
                                if(info.getApplytype() == 2)
                                {
                                    if(info.getDate()!= null && !info.getDate().equals("") )
                                    {
                                        sb.append("<tr>"); 
                                        sb.append("<td>" + info.getDate() + "</td>");
                                        if(info.getDate() != null && !info.getDate().equals(""))
                                        {
                                            sb.append("<td>Expired</td>");
                                        }else{
                                            sb.append("<td>Verify</td>");
                                        }
                                        sb.append("<td>" + info.getDocumentname()+" expired"+ "</td>");
                                        sb.append("<td>" + updatedBy+ "</td>");
//                                        if(info.getUserId() > 0){
//                                            sb.append("<td>" + userName+ "</td>");
//                                        }else{
//                                            sb.append("<td>" + info.getName() + "</td>");                                    
//                                        }
                                        sb.append("<td class='hand_cursor text-center'><a class='ver_act' href=\"../documentexpiry/DocumentexpiryAction.do?&dropdownId=" + 2 + "&exp="+2+"&search="+candidateId+ "\" target = '_blank'>View</a></td>");
                                        
                                        sb.append("</tr>");
                                    }                                
                                }                                
                            }
                        }
                        sb.append("</tbody>");
                        sb.append("</table>");
                        sb.append("</div>");
                    } 
                    else {
                        sb.append("<div>No records available.</div></div>");
                    }
                    String st1 = sb.toString();
                    sb.setLength(0);
                    response.getWriter().write(st1);
                } else {
                    response.getWriter().write("Something went wrong.");
                }
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