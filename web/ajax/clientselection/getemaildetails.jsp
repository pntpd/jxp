<%@page import="java.util.TreeSet"%>
<%@page import="java.util.SortedSet"%>
<%@page import="java.util.LinkedList"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.Collection"%>
<%@page import="org.json.JSONObject"%>
<%@page import="java.io.BufferedReader"%>
<%@page contentType="text/html"%>
<%@page language="java" import="java.util.ArrayList"%>
<%@page import="com.web.jxp.user.UserInfo" %>
<%@page import="com.web.jxp.clientselection.ClientselectionInfo" %>
<jsp:useBean id="clientselection" class="com.web.jxp.clientselection.Clientselection" scope="page"/>
<jsp:useBean id="vobj" class="com.web.jxp.base.Validate" scope="page"/>
<%
    try 
    {
        int  companytype = 0;
        UserInfo uInfo = (UserInfo) request.getSession().getAttribute("LOGININFO");
        if (uInfo != null)
        {
            companytype = uInfo.getCompanytype();
            int userId = uInfo.getUserId();
            if (userId > 0) 
            {
                String shortlistIds = request.getParameter("shortlistid") != null && !request.getParameter("shortlistid").equals("") ? vobj.replaceint(request.getParameter("shortlistid")) : "0";
                String sflags = request.getParameter("sflag") != null && !request.getParameter("sflag").equals("") ? vobj.replaceint(request.getParameter("sflag")) : "0";
                String maillogIds = request.getParameter("maillogid") != null && !request.getParameter("maillogid").equals("") ? vobj.replaceint(request.getParameter("maillogid")) : "0";
                int shortlistId = Integer.parseInt(shortlistIds);
                int sflag = Integer.parseInt(sflags);
                int maillogId = Integer.parseInt(maillogIds);

                String mailfile_path = clientselection.getMainPath("file_maillog");
                ClientselectionInfo einfo = clientselection.setEmailDetail(shortlistId, sflag, maillogId);

                StringBuffer sb = new StringBuffer();
                if (einfo != null) 
                {
                    sb.append("<ul>");
                    sb.append("<li>");
                    sb.append("<div class='row'>");
                    sb.append("<div class='col-md-3'><label>User Type</label></div>");
                    sb.append("<div class='col-md-9'><span>");
                    sb.append(companytype == 1 ? "Company" : "Client");
                    sb.append("</span></div>");
                    sb.append("</div>");
                    sb.append("</li>");
                    sb.append("<li>");
                    sb.append("<div class='row'>");
                    sb.append("<div class='col-md-3'><label>Sent On</label></div>");
                    sb.append("<div class='col-md-9'><span> " + einfo.getDate() + "</span></div>");
                    sb.append("</div>");
                    sb.append("</li>");
                    sb.append("<li>");
                    sb.append("<div class='row'>");
                    sb.append("<div class='col-md-3'><label>Sent By</label></div>");
                    sb.append("<div class='col-md-9'><span>" + einfo.getSendby() + "</span></div>");
                    sb.append("</div>");
                    sb.append("</li>");
                    String mbody = clientselection.readHTMLFile(einfo.getMailfileName(), mailfile_path);
                    int v1 = 0;
                    int v2 = mbody.length();
                    if (mbody.indexOf("<br/><br/>") != -1) {
                        v1 = mbody.indexOf("<br/><br/>");
                    }
                    if (mbody.indexOf("<br/><br/><br/></table>") != -1) {
                        v2 = mbody.indexOf("<br/><br/><br/></table>");
                    }
                    mbody = mbody.substring(v1 + 10, v2).trim();
                    String mailbody = mbody.replaceAll("<br/>", "\n");

                    sb.append("<li>");
                    sb.append("<div class='row'>");
                    sb.append("<div class='col-md-3'><label>Email Overview</label></div>");
                    sb.append("<div class='col-md-9'>");
                    sb.append("<span>" + einfo.getSubject() + "<br/><br/> " + mailbody + "</span>");
                    sb.append("</div>");
                    sb.append("</div>");
                    sb.append("</li>");
                    sb.append("<li>");
                    sb.append("<div class='row'>");
                    sb.append("<div class='col-md-3'><label>Sent To</label></div>");
                    sb.append("<div class='col-md-9'><span>" + einfo.getClientmailId() + "</span></div>");
                    sb.append("</div>");
                    sb.append("</li>");
                    sb.append("<li>");
                    sb.append("<div class='row'>");
                    sb.append("<div class='col-md-3'><label>Sent CC</label></div>");
                    sb.append("<div class='col-md-9'><span>" + einfo.getComailId() + "</span></div>");
                    sb.append("</div>");
                    sb.append("</li>");
                    sb.append("<li>");
                    sb.append("<div class='row'>");
                    sb.append("<div class='col-md-3'><label>Sent BCC</label></div>");
                    sb.append("<div class='col-md-9'><span>" + einfo.getBccmailId() + "</span></div>");
                    sb.append("</div>");
                    sb.append("</li>");
                    sb.append("</ul>");
                }
                String st = sb.toString();
                sb.setLength(0);
                response.getWriter().write(st);
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
%>