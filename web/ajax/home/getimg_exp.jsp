<%@page contentType="text/html"%>
<%@page language="java" import="java.util.ArrayList"%>
<%@page pageEncoding="UTF-8"%>
<%@page import="com.web.jxp.user.UserInfo" %>
<%@page import="com.web.jxp.candidate.CandidateInfo" %>
<jsp:useBean id="candidate" class="com.web.jxp.candidate.Candidate" scope="page"/>
<jsp:useBean id="vobj" class="com.web.jxp.base.Validate" scope="page"/>
<%
try
{
    if(session.getAttribute("HOME_EMAILID") != null)
    {
        if(request.getParameter("filename1") != null)
        {
            StringBuilder sb = new StringBuilder();
            String filename1 = request.getParameter("filename1") != null ? vobj.replacedesc(request.getParameter("filename1")) : "";
            String filename2 = request.getParameter("filename2") != null ? vobj.replacedesc(request.getParameter("filename2")) : "";
            if(!filename1.equals("") || !filename2.equals(""))
            {
                String view_client_file = candidate.getMainPath("view_candidate_file");
                String firsturl = "", classname = "";
                sb.append("<div class='col-lg-3'>");
                sb.append("<div class='drop_list'>");
                sb.append("<ul>");  
                if(!filename1.equals(""))
                {
                    if(filename1.contains(".doc") || filename1.contains(".docx"))
                    {
                        firsturl = "https://docs.google.com/gview?url="+view_client_file + filename1+"&embedded=true";
                        classname = "doc_mode";
                    }
                    else if(filename1.contains(".pdf"))
                    {
                        firsturl = view_client_file + filename1; 
                        classname = "pdf_mode";
                    }
                    else
                    {
                        firsturl = view_client_file + filename1;
                        classname = "img_mode";
                    }
                }
                else if(!filename2.equals(""))
                {
                    if(filename2.contains(".doc") || filename2.contains(".docx"))
                    {
                        firsturl = "https://docs.google.com/gview?url="+view_client_file + filename2+"&embedded=true";
                        classname = "doc_mode";
                    }
                    else if(filename2.contains(".pdf"))
                    {
                        firsturl = view_client_file + filename2; 
                        classname = "pdf_mode";
                    }
                    else
                    {
                        firsturl = view_client_file + filename2;
                        classname = "img_mode";
                    }
                }
            if(!filename1.equals(""))
            {
                sb.append("<li>");
                sb.append("<div class='row'>");
                sb.append("<div class='col-lg-8 pd_right_0'>");
                sb.append("<a href=\"javascript:setIframe('"+view_client_file+filename1+"');\" class='list_heading'>Experience Certificate</a>");                            
                sb.append("</div>");
                sb.append("<div class='col-lg-4 text-right'>");
                sb.append("<a href='"+view_client_file+filename1+"' download='' class='down_trash_img'><img src='../assets/images/download.png'/></a>");
                sb.append("</div>");
                sb.append("</div>");
                sb.append("</li>"); 
            }
                if(!filename2.equals(""))
                {
                sb.append("<li>");
                sb.append("<div class='row'>");
                sb.append("<div class='col-lg-8 pd_right_0'>");
                sb.append("<a href=\"javascript:setIframe('"+view_client_file+filename2+"');\" class='list_heading'>Document</a>");                            
                sb.append("</div>");
                sb.append("<div class='col-lg-4 text-right'>");
                sb.append("<a href='"+view_client_file+filename2+"' download='' class='down_trash_img'><img src='../assets/images/download.png'/></a>");
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