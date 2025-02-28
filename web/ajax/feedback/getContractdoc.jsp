<%@page import="com.web.jxp.talentpool.Talentpool"%>
<%@page contentType="text/html"%>
<%@page language="java" import="java.util.ArrayList"%>
<%@page pageEncoding="UTF-8"%>
<%@page import="com.web.jxp.crewlogin.CrewloginInfo"%>
<%@page import="com.web.jxp.feedback.FeedbackInfo" %>
<jsp:useBean id="feedback" class="com.web.jxp.feedback.Feedback" scope="page"/>
<jsp:useBean id="vobj" class="com.web.jxp.base.Validate" scope="page"/>
<%
try
{
    if(session.getAttribute("CREWLOGIN") != null)
    {
        if(request.getParameter("filename1") != null)
        {
            StringBuilder sb = new StringBuilder();
            String filename1 = request.getParameter("filename1") != null ? vobj.replacedesc(request.getParameter("filename1")) : "";
            String filename2 = request.getParameter("filename2") != null ? vobj.replacedesc(request.getParameter("filename2")) : "";
            String filename3 = request.getParameter("filename3") != null ? vobj.replacedesc(request.getParameter("filename3")) : "";
            String contractdetailIds = request.getParameter("contractdetailId") != null && !request.getParameter("contractdetailId").equals("") ? vobj.replacedesc(request.getParameter("contractdetailId")) : "";
            int contractdetailId = Integer.parseInt(contractdetailIds);
            FeedbackInfo info = feedback.getDetailsForModal(contractdetailId);
            String date1 ="", date2 ="", date3 ="", contractName = "";;
            if(info != null )
            {
                date1 = info.getDate1()!= null ? info.getDate1(): "";
                date2 = info.getDate2()!= null ? info.getDate2(): "";
                date3 = info.getDate3()!= null ? info.getDate3(): "";
                contractName = info.getContract()!= null ? info.getContract(): "";
            }
            if(!filename1.equals("") || !filename2.equals("") || !filename3.equals(""))
            {
                String view_file = feedback.getMainPath("view_candidate_file");
                String firsturl = "", classname = "";
                sb.append("<div class='col-lg-3'>");
                sb.append("<div class='drop_list'>");
                sb.append("<ul>");  
                if(!filename1.equals(""))
                {
                    if(filename1.contains(".pdf"))
                    {
                        firsturl = feedback.getMainPath("pdfviewer")+view_file + filename1; 
                        classname = "pdf_mode";
                    }
                }
                else if(!filename2.equals(""))
                {
                    if(filename2.contains(".pdf"))
                    {
                        firsturl = feedback.getMainPath("pdfviewer")+view_file + filename2; 
                        classname = "pdf_mode";
                    }
                }
                else if(!filename3.equals(""))
                {
                    if(filename3.contains(".pdf"))
                    {
                        firsturl = feedback.getMainPath("pdfviewer")+view_file + filename3; 
                        classname = "pdf_mode";
                    }
                }
                if(!filename1.equals(""))
                {
                    sb.append("<li>");
                    sb.append("<div class='row'>");
                    sb.append("<div class='col-lg-8 col-md-8 col-sm-8 col-10 pd_right_0'>");
                    sb.append("<a href=\"javascript:setIframe('"+feedback.getMainPath("pdfviewer")+view_file+filename1+"');\" class='list_heading'>"+contractName+"</a>"); 
                    sb.append("<ul>");
                    sb.append("<li>" +date1 + "</li>");
                    //sb.append("<li>" + username1+ "</li>");
                    sb.append("</ul>");
                    sb.append("</div>");
                    sb.append("<div class='col-lg-4 col-md-4 col-sm-4 col-2 text-right'>");
                    sb.append("<a href='"+view_file+filename1+"' download='' class='down_trash_img'><img src='../assets/images/download.png'/></a>");                
                    sb.append("</div>");
                    sb.append("</div>");
                    sb.append("</li>"); 
                }
                if(!filename2.equals(""))
                {
                    sb.append("<li>");
                    sb.append("<div class='row'>");
                    sb.append("<div class='col-lg-8 col-md-8 col-sm-8 col-10 pd_right_0'>");
                    sb.append("<a href=\"javascript:setIframe('"+feedback.getMainPath("pdfviewer")+view_file+filename2+"');\" class='list_heading'>Crew Attested</a>");  
                    sb.append("<ul>");
                    sb.append("<li>" +date2 + "</li>");
                    //sb.append("<li>" + username2+ "</li>");
                    sb.append("</ul>");
                    sb.append("</div>");
                    sb.append("<div class='col-lg-4 col-md-4 col-sm-4 col-2 text-right'>");                
                    sb.append("<a href='"+view_file+filename2+"' download='' class='down_trash_img'><img src='../assets/images/download.png'/></a>");
                    //sb.append("<a href=\"javascript: deleteFile('"+contractdetailId+"','2');\" class='down_trash_img'><img src='../assets/images/trash.png'/></a>");
                    sb.append("</div>");
                    sb.append("</div>");
                    sb.append("</li>"); 
                }
                if(!filename3.equals(""))
                {
                    sb.append("<li>");
                    sb.append("<div class='row'>");
                    sb.append("<div class='col-lg-8 col-md-8 col-sm-8 col-10 pd_right_0'>");
                    sb.append("<a href=\"javascript:setIframe('"+feedback.getMainPath("pdfviewer")+view_file+filename3+"');\" class='list_heading'>Company Approved</a>"); 
                    sb.append("<ul>");
                    sb.append("<li>" +date3 + "</li>");
                    //sb.append("<li>" + username3+ "</li>");
                    sb.append("</ul>");
                    sb.append("</div>");
                    sb.append("<div class='col-lg-4 col-md-4 col-sm-4 col-2 text-right'>");
                    sb.append("<a href='"+view_file+filename3+"' download='' class='down_trash_img'><img src='../assets/images/download.png'/></a>");
                    //sb.append("<a href=\"javascript: deleteFile('"+contractdetailId+"','3');\" class='down_trash_img'><img src='../assets/images/trash.png'/></a>");
                    sb.append("</div>");
                    sb.append("</div>");
                    sb.append("</li>"); 
                }
                    sb.append("</ul>");
                    sb.append("</div>");
                    sb.append("</div>");
                    sb.append("<div class='col-lg-9'>");
                    sb.append("<iframe id='iframe' class='"+classname+"' src='"+firsturl+"'></iframe>");
                    sb.append("</div>");

                    String st1 = sb.toString();
                    sb.setLength(0);
                    response.getWriter().write(st1);
            }
            else
            {
                response.getWriter().write("No Files Uploaded.");
            }
        }
        else
        {
            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
        }
    }
    else
    {
        response.getWriter().write("Please check your login session....");
    }
}
catch(Exception e)
{    
    e.printStackTrace();
}
%>