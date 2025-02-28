<%@page contentType="text/html"%>
<%@page language="java" import="java.util.ArrayList"%>
<%@page pageEncoding="UTF-8"%>
<%@page import="com.web.jxp.user.UserInfo" %>
<%@page import="com.web.jxp.documentexpiry.DocumentexpiryInfo" %>
<jsp:useBean id="documentexpiry" class="com.web.jxp.documentexpiry.Documentexpiry" scope="page"/>
<jsp:useBean id="vobj" class="com.web.jxp.base.Validate" scope="page"/>
<%
    try 
    {
        if (session.getAttribute("LOGININFO") != null) 
        {
            UserInfo uinfo = ((UserInfo) request.getSession().getAttribute("LOGININFO"));
            String addper = "", editper = "N";
            if (uinfo != null) 
            {
                addper = uinfo.getAddper() != null ? uinfo.getAddper() : "N";
                editper = uinfo.getEditper() != null ? uinfo.getEditper() : "N";
            }
            if (request.getParameter("col") != null) 
            {
                StringBuilder str = new StringBuilder();
                String colid = request.getParameter("col") != null ? vobj.replaceint(request.getParameter("col")) : "0";
                String updowns = request.getParameter("updown") != null && !request.getParameter("updown").equals("") ? vobj.replaceint(request.getParameter("updown")) : "0";
                int updown = Integer.parseInt(updowns);                
                ArrayList documentexpiry_list = new ArrayList();
                if (session.getAttribute("DOCUMENTEXPIRY_LIST") != null) {
                    documentexpiry_list = (ArrayList) session.getAttribute("DOCUMENTEXPIRY_LIST");
                }
                int total = documentexpiry_list.size();
                if (total > 0) 
                {
                    documentexpiry_list = documentexpiry.getFinalRecord(documentexpiry_list, colid, updown);
                    session.setAttribute("DOCUMENTEXPIRY_LIST", documentexpiry_list);
                    DocumentexpiryInfo info;
                    for (int i = 0; i < total; i++) 
                    {
                        info = (DocumentexpiryInfo) documentexpiry_list.get(i);
                        if (info != null) 
                        {
                            str.append("<tr>");
                            str.append("<td class='select_check_box'>");
                            str.append("<label class='mt-checkbox mt-checkbox-outline'>"); 
                                str.append("<input type='checkbox' value='"+info.getGovdocId()+"' name='govdoccb' id='govdoccb' class='singlechkbox' onchange='javascript: setcb();' />");
                                str.append("<span></span>");
                            str.append("</label>");	
                            str.append("</td>");
                            str.append("<td>" + (documentexpiry.changeNum(info.getCandidateId(), 6)) + "</td>"); 
                            str.append("<td>" + (info.getName() != null ? info.getName() : "") + "</td>");
                            str.append("<td>" + (info.getClientName()!= null ? info.getClientName(): "") + "</td>");
                            str.append("<td>" + (info.getAssetName()!= null ? info.getAssetName(): "") + "</td>");
                            str.append("<td>" + (info.getDocumentName()!= null ? info.getDocumentName(): "") + "</td>");
                            str.append("<td>" + (info.getExpiryDate()!= null ? info.getExpiryDate(): "") + "</td>");
                            if(addper.equals("Y") || editper.equals("Y"))
                                str.append("<td class='text-center'><a class='remind_bell_btn' href=\"javascript: sendmail('"+info.getGovdocId()+"');\"><i class='far fa-bell'></i> <span>"+info.getRemind()+"</a></td>");
                            else
                                str.append("<td class='text-right'>&nbsp;</td>");
                            str.append("</tr>");
                        }
                    }
                }
                String st = str.toString();
                str.setLength(0);
                response.getWriter().write(st);
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