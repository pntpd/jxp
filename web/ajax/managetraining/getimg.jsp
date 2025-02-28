<%@page contentType="text/html"%>
<%@page language="java" import="java.util.ArrayList"%>
<%@page pageEncoding="UTF-8"%>
<%@page import="com.web.jxp.user.UserInfo" %>
<%@page import="com.web.jxp.createtraining.CreatetrainingInfo" %>
<jsp:useBean id="createtraining" class="com.web.jxp.createtraining.Createtraining" scope="page"/>
<jsp:useBean id="vobj" class="com.web.jxp.base.Validate" scope="page"/>
<%
try
{
    if(session.getAttribute("LOGININFO") != null)
    {
        if(request.getParameter("courseId") != null)
        {
            StringBuilder sb = new StringBuilder();
            String courseIds = request.getParameter("courseId") != null ? vobj.replaceint(request.getParameter("courseId")) : "0";
            int courseId = Integer.parseInt(courseIds); 
            if(courseId > 0)
            {
                ArrayList list = createtraining.getcourseattachment(courseId);
                int size = list.size();
                if(size > 0) 
                {
                    String view_trainingfiles = createtraining.getMainPath("view_trainingfiles");
                    String firsturl = "";
                    sb.append("<div class='col-lg-5'>");
                    sb.append("<div class='drop_list'>");
                    sb.append("<ul>");
                    String filename, classname = "";
                    for (int i = 0; i < size; i++)
                    {
                        CreatetrainingInfo info = (CreatetrainingInfo) list.get(i);
                        if(info != null)
                        {
                            filename = info.getFilename() != null ? info.getFilename() : ""; 
                            if(i == 0)
                            {                            
                                if(filename.contains(".doc") || filename.contains(".docx"))
                                {
                                    firsturl = "https://docs.google.com/gview?url="+view_trainingfiles + filename+"&embedded=true";
                                    classname = "doc_mode";
                                }
                                else if(filename.contains(".pdf"))
                                {
                                    firsturl = view_trainingfiles + filename; 
                                    classname = "pdf_mode";
                                }
                                else
                                {
                                    firsturl = view_trainingfiles + filename;
                                    classname = "img_mode";
                                }
                            }
                            sb.append("<li>");
                            sb.append("<div class='row'>");
                            sb.append("<div class='col-lg-9'>");
                            sb.append("<a href=\"javascript:setIframe('"+view_trainingfiles+filename+"');\" class='list_heading'>File "+(i+1)+"</a>");
                            sb.append("<ul>");
                            sb.append("<li>"+(info.getDate() != null ? info.getDate() : "")+"</li>");
                            sb.append("<li>"+info.getName() != null ? info.getName() : ""+"</li>");
                            sb.append("</ul>");
                            sb.append("</div>");
                            sb.append("<div class='col-lg-3'>");
                            sb.append("<a href='"+view_trainingfiles+filename+"' download='' class='down_trash_img'><img src='../assets/images/download.png'/></a>");
                            sb.append("</div>");
                            sb.append("</div>");
                            sb.append("</li>"); 
                        }
                    }
                    sb.append("</ul>");
                    sb.append("</div>");
                    sb.append("</div>");
                    sb.append("<div class='col-lg-7'>");
                    sb.append("<iframe id='iframe' class='"+classname+"' src='"+firsturl+"'></iframe>");
                    sb.append("</div>");
                }
                else
                {
                    sb.append("<div class='col-lg-12'><div class='alert deleted-msg alert-dismissible fade show'>No files available.</div></div>");
                }
                String st1 = sb.toString();
                sb.setLength(0);
                response.getWriter().write(st1);
            }
            else
            {
                response.getWriter().write("Something went wrong.");
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