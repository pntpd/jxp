<%@page contentType="text/html"%>
<%@page language="java" import="java.util.ArrayList"%>
<%@page pageEncoding="UTF-8"%>
<%@page import="com.web.jxp.user.UserInfo" %>
<%@page import="com.web.jxp.talentpool.TalentpoolInfo" %>
<jsp:useBean id="ddl" class="com.web.jxp.talentpool.Ddl" scope="page"/>
<jsp:useBean id="vobj" class="com.web.jxp.base.Validate" scope="page"/>
<%
try
{
    if(session.getAttribute("LOGININFO") != null)
    {
        UserInfo uinfo = ((UserInfo) request.getSession().getAttribute("LOGININFO"));
        String editper = "N", deleteper= "N";
        if (uinfo != null) 
        {
            editper = uinfo.getEditper() != null ? uinfo.getEditper() : "N";
            deleteper = uinfo.getDeleteper() != null ? uinfo.getDeleteper() : "N";
        }
        TalentpoolInfo dinfo = null;
        int dstatus = 0;
        if (session.getAttribute("CANDIDATE_DETAIL") != null) {
            dinfo = (TalentpoolInfo) session.getAttribute("CANDIDATE_DETAIL");
            dstatus = dinfo.getStatus();
        }
        if(request.getParameter("filename1") != null)
        {
            StringBuilder sb = new StringBuilder();
            String filename1 = request.getParameter("filename1") != null ? vobj.replacedesc(request.getParameter("filename1")) : "";
            String filename2 = request.getParameter("filename2") != null ? vobj.replacedesc(request.getParameter("filename2")) : "";
            String filename3 = request.getParameter("filename3") != null ? vobj.replacedesc(request.getParameter("filename3")) : "";
            String contractdetailIds = request.getParameter("contractdetailId") != null && !request.getParameter("contractdetailId").equals("") ? vobj.replacedesc(request.getParameter("contractdetailId")) : "";
            int contractdetailId = Integer.parseInt(contractdetailIds);
            TalentpoolInfo info = ddl.getDetailsForModal(contractdetailId);
            String date1 ="", date2 ="", date3 ="", username1 ="", username2 ="", username3 ="",
            contractName = "";
            if(info != null )
            {
                date1 = info.getDate1()!= null ? info.getDate1(): "";
                date2 = info.getDate2()!= null ? info.getDate2(): "";
                date3 = info.getDate3()!= null ? info.getDate3(): "";
                username1 = info.getUsername1()!= null ? info.getUsername1(): "";
                username2 = info.getUsername2()!= null ? info.getUsername2(): "";
                username3 = info.getUsername3()!= null ? info.getUsername3(): "";
                contractName = info.getContractName()!= null ? info.getContractName(): "";
            }
            if(!filename1.equals("") || !filename2.equals("") || !filename3.equals(""))
            {
                String view_file = ddl.getMainPath("view_candidate_file");
                String firsturl = "", classname = "";
                sb.append("<div class='col-lg-3'>");
                sb.append("<div class='drop_list'>");
                sb.append("<ul>");  
                if(!filename1.equals(""))
                {
                    if(filename1.contains(".pdf"))
                    {
                        firsturl = view_file + filename1; 
                        classname = "pdf_mode";
                    }
                }
                else if(!filename2.equals(""))
                {
                    if(filename2.contains(".pdf"))
                    {
                        firsturl = view_file + filename2; 
                        classname = "pdf_mode";
                    }
                }
                else if(!filename3.equals(""))
                {
                    if(filename3.contains(".pdf"))
                    {
                        firsturl = view_file + filename3; 
                        classname = "pdf_mode";
                    }
                }
            if(!filename1.equals(""))
            {
                sb.append("<li>");
                sb.append("<div class='row'>");
                sb.append("<div class='col-lg-8 pd_right_0'>");
                sb.append("<a href=\"javascript:setIframe('"+view_file+filename1+"');\" class='list_heading'>"+contractName+"</a>"); 
                sb.append("<ul>");
                sb.append("<li>" +date1 + "</li>");
                sb.append("<li>" + username1+ "</li>");
                sb.append("</ul>");
                sb.append("</div>");
                sb.append("<div class='col-lg-4 text-right'>");
                sb.append("<a href='"+view_file+filename1+"' download='' class='down_trash_img'><img src='../assets/images/download.png'/></a>");                
                sb.append("</div>");
                sb.append("</div>");
                sb.append("</li>"); 
            }
            if(!filename2.equals(""))
            {
                sb.append("<li>");
                sb.append("<div class='row'>");
                sb.append("<div class='col-lg-8 pd_right_0'>");
                sb.append("<a href=\"javascript:setIframe('"+view_file+filename2+"');\" class='list_heading'> Company Approved </a>");  
                sb.append("<ul>");
                sb.append("<li>" +date2 + "</li>");
                sb.append("<li>" + username2+ "</li>");
                sb.append("</ul>");
                sb.append("</div>");
                sb.append("<div class='col-lg-4 text-right'>");                
                sb.append("<a href='"+view_file+filename2+"' download='' class='down_trash_img'><img src='../assets/images/download.png'/></a>");
                if(deleteper.equals("Y") && dstatus != 4){
                    sb.append("<a href=\"javascript: deleteFile('"+contractdetailId+"','2', '"+filename1+"', '"+filename2+"', '"+filename3+"');\" class='down_trash_img'><img src='../assets/images/trash.png'/></a>");
                }
                sb.append("</div>");
                sb.append("</div>");
                sb.append("</li>"); 
            }
            if(!filename3.equals(""))
            {
                sb.append("<li>");
                sb.append("<div class='row'>");
                sb.append("<div class='col-lg-8 pd_right_0'>");
                sb.append("<a href=\"javascript:setIframe('"+view_file+filename3+"');\" class='list_heading'>Crew Attested</a>"); 
                sb.append("<ul>");
                sb.append("<li>" +date3 + "</li>");
                sb.append("<li>" + username3+ "</li>");
                sb.append("</ul>");
                sb.append("</div>");
                sb.append("<div class='col-lg-4 text-right'>");
                sb.append("<a href='"+view_file+filename3+"' download='' class='down_trash_img'><img src='../assets/images/download.png'/></a>");
                if(deleteper.equals("Y") && dstatus != 4){
                    sb.append("<a href=\"javascript: deleteFile('"+contractdetailId+"','3', '"+filename1+"', '"+filename2+"', '"+filename3+"');\" class='down_trash_img'><img src='../assets/images/trash.png'/></a>");
                }
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