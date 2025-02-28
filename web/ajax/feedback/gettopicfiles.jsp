<%@page import="com.web.jxp.crewlogin.CrewloginInfo"%>
<%@page contentType="text/html"%>
<%@page language="java" import="java.util.ArrayList"%>
<%@page pageEncoding="UTF-8"%>
<%@page import="com.web.jxp.feedback.FeedbackInfo" %>
<jsp:useBean id="candidate" class="com.web.jxp.candidate.Candidate" scope="page"/>
<jsp:useBean id="feedback" class="com.web.jxp.feedback.Feedback" scope="page"/>
<jsp:useBean id="vobj" class="com.web.jxp.base.Validate" scope="page"/>
<%
    try 
    {
        if (session.getAttribute("CREWLOGIN") != null) 
        {
                StringBuilder sb = new StringBuilder();
                String topicIds = request.getParameter("topicId") != null && !request.getParameter("topicId").equals("") ? vobj.replacename(request.getParameter("topicId")) : "";
                int topicId = Integer.parseInt(topicIds);
                if (topicId > 0) 
                {
                    ArrayList list = feedback.getTopicFiles(topicId);
                    int size = list.size();
                    if (size > 0) 
                    {
                        String view_client_file = feedback.getMainPath("view_kfiles");
                        String firsturl = "";
                        sb.append("<div class='col-lg-3'>");
                        sb.append("<div class='drop_list'>");
                        sb.append("<ul>");
                        String filename, classname = "pdf_mode";
                        for (int i = 0; i < size; i++) 
                        {
                            FeedbackInfo info = (FeedbackInfo) list.get(i);
                            if (info != null) 
                            {
                                if(info.getType() == 1)
                                {
                                    filename = info.getFilename() != null ? info.getFilename() : "";
                                    String ext = filename.substring(filename.lastIndexOf("."));
                                    String fn_download =   info.getDisplayname() + ext;
                                    if (i == 0) {
                                        if (filename.contains(".doc") || filename.contains(".docx")) 
                                        {
                                            firsturl = "https://docs.google.com/gview?url=" + view_client_file + filename + "&embedded=true";
                                            classname = "doc_mode";
                                        }
                                        else if(filename.contains(".ppt") || filename.contains(".pptx") || filename.contains(".presentation"))
                                        {
                                            firsturl = "https://view.officeapps.live.com/op/embed.aspx?src=" + view_client_file + filename + "&embedded=true";
                                            classname = "doc_mode";
                                        }
                                        else if (filename.contains(".pdf")) {
                                            firsturl = view_client_file + filename;
                                            classname = "pdf_mode";
                                        } else {
                                            firsturl = view_client_file + filename;
                                            classname = "img_mode";
                                        }
                                    }
                                    sb.append("<li>");
                                    sb.append("<div class='row'>");
                                    sb.append("<div class='col-lg-8 pd_right_0'>");
                                    sb.append("<a href=\"javascript:setIframeresume('" + view_client_file + filename + "');\" class='list_heading'>"+info.getDisplayname()+"</a>");
                                    sb.append("</div>");
                                    sb.append("<div class='col-lg-4 text-right'>");
                                    sb.append("<a href='" + view_client_file + filename + "' download='" + fn_download + "' class='down_trash_img topic_list_del'><img src='../assets/images/download.png'/></a>");
                                    sb.append("</div>");
                                    sb.append("</div>");
                                    sb.append("</li>");
                                }
                                else
                                {
                                    filename = info.getFilename() != null ? info.getFilename() : "";
                                    sb.append("<li>");
                                    sb.append("<div class='row'>");
                                    sb.append("<div class='col-lg-8 pd_right_0'>");
                                    sb.append("<a href=\""+view_client_file + filename+"story.html" + "\" class='list_heading' target='_blank'>"+info.getDisplayname()+"</a>");
                                    sb.append("</div>");
                                    sb.append("<div class='col-lg-4 text-right'>");
                                    sb.append("<a href='"+ view_client_file + filename+"story.html" + "'  class='down_trash_img topic_list_del' target='_blank'><img src='../assets/images/view.png'/></a>");
                                    sb.append("</div>");
                                    sb.append("</div>");
                                    sb.append("</li>");
                                }
                            }
                        }
                        sb.append("</ul>");
                        sb.append("</div>");
                        sb.append("</div>");
                        sb.append("<div class='col-lg-9'>");
                        sb.append("<iframe id='iframe' class='" + classname + "' src='" + firsturl + "'></iframe>");
                        sb.append("</div>");
                    } else {
                        sb.append("<div class='col-lg-12'><div class='alert deleted-msg alert-dismissible fade show'>No files available.</div></div>");
                    }
                    String st1 = sb.toString();
                    sb.setLength(0);
                    response.getWriter().write(st1);
                } else {
                    response.getWriter().write("Something went wrong.");
                }
           
        } else {
            response.getWriter().write("Please check your login session....");
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
%>