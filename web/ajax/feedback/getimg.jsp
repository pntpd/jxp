<%@page import="com.web.jxp.crewlogin.CrewloginInfo"%>
<%@page contentType="text/html"%>
<%@page language="java" import="java.util.ArrayList"%>
<%@page pageEncoding="UTF-8"%>
<%@page import="com.web.jxp.candidate.CandidateInfo" %>
<jsp:useBean id="candidate" class="com.web.jxp.candidate.Candidate" scope="page"/>
<jsp:useBean id="vobj" class="com.web.jxp.base.Validate" scope="page"/>
<%
    try 
    {
        if (session.getAttribute("CREWLOGIN") != null) 
        {
            int candidateId = 0;
            if (request.getSession().getAttribute("CREWLOGIN") != null) 
            {
                CrewloginInfo info = (CrewloginInfo) request.getSession().getAttribute("CREWLOGIN");
                if (info != null)
                {
                    candidateId = info.getCandidateId();
                }    
            }
            if (request.getParameter("fn") != null)
            {
                StringBuilder sb = new StringBuilder();
                String fn = request.getParameter("fn") != null && !request.getParameter("fn").equals("") ? vobj.replacename(request.getParameter("fn")) : "";
                if (candidateId > 0) 
                {
                    ArrayList list = candidate.getPics(candidateId);
                    int size = list.size();
                    if (size > 0) 
                    {
                        String view_client_file = candidate.getMainPath("view_candidate_file");
                        String firsturl = "";
                        sb.append("<div class='col-lg-3'>");
                        sb.append("<div class='drop_list'>");
                        sb.append("<ul>");
                        String filename, classname = "pdf_mode";
                        for (int i = 0; i < size; i++)
                        {
                            CandidateInfo info = (CandidateInfo) list.get(i);
                            if (info != null) 
                            {
                                filename = info.getFilename() != null ? info.getFilename() : "";
                                String ext = filename.substring(filename.lastIndexOf("."));
                                String fn_download = fn + "-" + candidate.changeNum(candidateId, 6) + "-Resume" + (i + 1) + ext;
                                if (i == 0) {
                                    if (filename.contains(".doc") || filename.contains(".docx")) {
                                        firsturl = "https://docs.google.com/gview?url=" + view_client_file + filename + "&embedded=true";
                                        classname = "doc_mode";
                                    } else if (filename.contains(".pdf")) {
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
                                sb.append("<a href=\"javascript:setIframeresume('" + view_client_file + filename + "');\" class='list_heading'>Resume " + (i + 1) + "</a>");
                                sb.append("<ul>");
                                sb.append("<li>" + (info.getDate() != null ? info.getDate() : "") + "</li>");
                                sb.append("</ul>");
                                sb.append("</div>");
                                sb.append("<div class='col-lg-4 text-right'>");
                                sb.append("<a href='" + view_client_file + filename + "' download='" + fn_download + "' class='down_trash_img'><img src='../assets/images/download.png'/></a>");
                                sb.append("</div>");
                                sb.append("</div>");
                                sb.append("</li>");
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
                response.setStatus(HttpServletResponse.SC_NO_CONTENT);
            }
        } else {
            response.getWriter().write("Please check your login session....");
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
%>