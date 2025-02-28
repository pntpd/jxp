<%@page contentType="text/html"%>
<%@page language="java" import="java.util.ArrayList"%>
<%@page pageEncoding="UTF-8"%>
<%@page import="com.web.jxp.user.UserInfo" %>
<%@page import="com.web.jxp.pcategory.PcategoryInfo" %>
<jsp:useBean id="pcategory" class="com.web.jxp.pcategory.Pcategory" scope="page"/>
<jsp:useBean id="vobj" class="com.web.jxp.base.Validate" scope="page"/>
<%
    try 
    {
        if (session.getAttribute("LOGININFO") != null) 
        {
            UserInfo uinfo = ((UserInfo) request.getSession().getAttribute("LOGININFO"));
            String editper = "N";
            if (uinfo != null) 
            {
                editper = uinfo.getEditper() != null ? uinfo.getEditper() : "N";
            }
            if (request.getParameter("col") != null) 
            {
                StringBuilder str = new StringBuilder();
                String colid = request.getParameter("col") != null ? vobj.replaceint(request.getParameter("col")) : "";
                String updowns = request.getParameter("updown") != null && !request.getParameter("updown").equals("") ? vobj.replaceint(request.getParameter("updown")) : "0";
                String nextValue = request.getParameter("nextValue") != null && !request.getParameter("nextValue").equals("") ? vobj.replaceint(request.getParameter("nextValue")) : "0";
                int n = Integer.parseInt(nextValue);
                int updown = Integer.parseInt(updowns);
                int next_value = n;
                n = n - 1;

                session.setAttribute("NEXTVALUE", next_value + "");
                ArrayList pcategory_list = new ArrayList();
                if (session.getAttribute("PCATEGORY_LIST") != null) {
                    pcategory_list = (ArrayList) session.getAttribute("PCATEGORY_LIST");
                }
                int total = pcategory_list.size();
                if (total > 0) 
                {
                    pcategory_list = pcategory.getFinalRecord(pcategory_list, colid, updown);
                    session.setAttribute("PCATEGORY_LIST", pcategory_list);
                
                    int status;
                    PcategoryInfo info;
                    for (int i = 0; i < total; i++)
                    {
                        info = (PcategoryInfo) pcategory_list.get(i);
                        if (info != null)
                        {
                            status = info.getStatus();
                            str.append("<tr>");
                            str.append("<td>" + (info.getName() != null ? info.getName() : "") + "</td>");
                            str.append("<td>" + (info.getAssettypeName() != null ? info.getAssettypeName() : "") + "</td>");
                            str.append("<td>" + (info.getDeptName() != null ? info.getDeptName() : "") + "</td>");
                            str.append("<td class='assets_list text-center'><a href=\"javascript: showDetail('"+ info.getPcategoryId()+"');\">" + (pcategory.changeNum(info.getQcount(), 2)) + "</a></td>");
                            str.append("<td class='action_column'>");
                            str.append("<a class='mr_15' href=\"javascript: showDetail('"+info.getPcategoryId()+"');\"><img src='../assets/images/plus.png' /></a>");
                            if (editper.equals("Y") && info.getStatus() == 1) {
                                str.append("<a href=\"javascript: modifyForm('" + info.getPcategoryId() + "');\" class='mr_15'><img src='../assets/images/pencil.png'/></a>");
                            }
                            str.append("<span class='switch_bth float-end'>");
                            str.append("<div class='form-check form-switch'>");
                            str.append("<input class='form-check-input' type='checkbox' role='switch' id='flexSwitchCheckDefault_" + (i) + "'");
                            if (!editper.equals("Y")) {
                                str.append("disabled='true'");
                            }
                            if (status == 1) {
                                str.append("checked");
                            }
                            str.append(" onclick=\"javascript: deleteForm('" + info.getPcategoryId() + "', '" + status + "', '" + (i) + "');\" />");
                            str.append("</div>");
                            str.append("</span>");                            
                            str.append("</td>");
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