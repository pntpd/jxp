<%@page contentType="text/html"%>
<%@page language="java" import="java.util.ArrayList"%>
<%@page pageEncoding="UTF-8"%>
<%@page import="com.web.jxp.user.UserInfo" %>
<%@page import="com.web.jxp.clientinvoicing.ClientinvoicingInfo" %>
<jsp:useBean id="clientinvoicing" class="com.web.jxp.clientinvoicing.Clientinvoicing" scope="page"/>
<jsp:useBean id="vobj" class="com.web.jxp.base.Validate" scope="page"/>
<%
    try 
    {
        if (session.getAttribute("LOGININFO") != null) 
        {
            String clientassetId_s = request.getParameter("clientassetId") != null && !request.getParameter("clientassetId").equals("") ? vobj.replaceint(request.getParameter("clientassetId")) : "0";
            int clientassetId = Integer.parseInt(clientassetId_s);
            StringBuilder sb = new StringBuilder();
            sb.append("<h2>Upload Pay Slip</h2>");
            sb.append("<div class='row client_position_table'>");
            sb.append("<div class='col-lg-12 col-md-12 col-sm-12 col-4 form_group'>");
            sb.append("<label class='form_label'>Upload (Only .zip file upto 20MB)</label>");
            sb.append("<div class='row'>");
            sb.append("<div class='col-lg-7 col-md-7 col-sm-12 col-12 form_group'>");
            sb.append("<input id='upload2' name='upload2' type='file' onchange=\"setClass('2');\">");
            sb.append("<a href='javascript:;' id='upload_link_2' class='attache_btn attache_btn_white uploaded_img1'>");
            sb.append("<i class='fas fa-paperclip'></i> Attach");
            sb.append("</a>");
            sb.append("</div>");
            sb.append("</div>");
            sb.append("</div>");
            sb.append("<div class='col-lg-12 col-md-12 col-sm-12 col-12 text-center form_group'>");
            sb.append("<a href='javascript: getSavePaySlip();' class='save_page'><img src='../assets/images/save.png'> Save</a>");
            sb.append("</div>");
            
            ArrayList list = new ArrayList();
            list = clientinvoicing.getPaySlipHistoryDtls(clientassetId);
            int size = list.size();
            if (size > 0)
            {
                sb.append("<div class='col-lg-12 col-md-12 col-sm-12 col-4 form_group'>");
                sb.append("<div class='table-rep-plugin sort_table'>");
                sb.append("<div class='table-responsive mb-0'>");
                sb.append("<table class='table table-striped'>");
                sb.append("<thead>");
                sb.append("<tr>");
                sb.append("<th width='40%'><span><b>Date & Time</b> </span></th>");
                sb.append("<th width='60%'><span><b>Name</b></span></th>");
                sb.append("</tr>");
                sb.append("</thead>");
                
                ClientinvoicingInfo info = null;
                sb.append("<tbody>");
                for (int i = 0; i < size; i++) 
                {
                    info = (ClientinvoicingInfo) list.get(i);
                    if (info != null) 
                    {
                        sb.append("<tr>");
                        sb.append("<td>" + (info.getDate() != null ? info.getDate() : "") + "</td>");
                        sb.append("<td>" + (info.getUsername() != null ? info.getUsername() : "") + "</td>");
                        sb.append("</tr>");
                    }
                }
                sb.append("</tbody>");
                sb.append("</table>");
                sb.append("</div>");
                sb.append("</div>");
                sb.append("</div>");
            }
            sb.append("</div>");
            String st = sb.toString();
            sb.setLength(0);
            response.getWriter().write(st);

        } else {
            response.getWriter().write("Please check your login session....");
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
%>