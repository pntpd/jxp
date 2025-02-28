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
    if(session.getAttribute("LOGININFO") != null)
    {
        UserInfo uinfo = ((UserInfo) request.getSession().getAttribute("LOGININFO"));
        String deleteper = "N";        
        if(uinfo != null)
        {
            deleteper = uinfo.getDeleteper() != null ? uinfo.getDeleteper() : "N";
        }      
        TalentpoolInfo dinfo = null;
        int dstatus = 0;
        if (session.getAttribute("CANDIDATE_DETAIL") != null) {
            dinfo = (TalentpoolInfo) session.getAttribute("CANDIDATE_DETAIL");
            dstatus = dinfo.getStatus();
        }
        if(request.getParameter("govId") != null)
        {
            StringBuilder sb = new StringBuilder();
            String clientassetId_s = request.getParameter("govId") != null ? vobj.replaceint(request.getParameter("govId")) : "0";
            int clientassetId = Integer.parseInt(clientassetId_s); 
            if(clientassetId > 0)
            {
                ArrayList list = talentpool.getdocumentfiles(clientassetId);
                int size = list.size();
                if(size > 0) 
                {
                    String view_candidate_file = talentpool.getMainPath("view_candidate_file");
                    String firsturl = "";
                    sb.append("<div class='col-lg-3'>");
                    sb.append("<div class='drop_list'>");
                    sb.append("<ul>");
                    String filename, classname = "";
                    for (int i = 0; i < size; i++)
                    {
                        TalentpoolInfo info = (TalentpoolInfo) list.get(i);
                        if(info != null)
                        {
                            filename = info.getFilename() != null ? info.getFilename() : ""; 
                            if(i == 0)
                            {                            
                                if(filename.contains(".doc") || filename.contains(".docx"))
                                {
                                    firsturl = "https://docs.google.com/gview?url="+view_candidate_file + filename+"&embedded=true";
                                    classname = "doc_mode";
                                }
                                else if(filename.contains(".pdf"))
                                {
                                    firsturl = view_candidate_file + filename; 
                                    classname = "pdf_mode";
                                }
                                else
                                {
                                    firsturl = view_candidate_file + filename;
                                    classname = "img_mode";
                                }
                            }
                            sb.append("<li>");
                            sb.append("<div class='row'>");
                            sb.append("<div class='col-lg-8 pd_right_0'>");
                            sb.append("<a href=\"javascript:setIframe('"+view_candidate_file+filename+"');\" class='list_heading'>File "+(i+1)+"</a>");
                            sb.append("<ul>");
                            sb.append("<li>"+(info.getDate() != null ? info.getDate() : "")+"</li>");
                            sb.append("<li>"+info.getName() != null ? info.getName() : ""+"</li>");
                            sb.append("</ul>");
                            sb.append("</div>");
                            sb.append("<div class='col-lg-4 text-right'>");
                            sb.append("<a href='"+view_candidate_file+filename+"' download='' class='down_trash_img'><img src='../assets/images/download.png'/></a>");
                            if(deleteper.equals("Y") && dstatus != 4)
                                sb.append("<a href=\"javascript: deldocumentfiles('"+info.getFileid()+"', '"+clientassetId+"');\" class='down_trash_img'><img src='../assets/images/trash.png'/></a>");
                            sb.append("</div>");
                            sb.append("</div>");
                            sb.append("</li>"); 
                        }
                    }
                    sb.append("</ul>");
                    sb.append("</div>");
                    sb.append("</div>");
                    sb.append("<div class='col-lg-9'>");
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