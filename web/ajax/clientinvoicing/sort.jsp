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
                ArrayList clientinvoicing_list = new ArrayList();
                if (session.getAttribute("CLIENTINVOICING_LIST") != null) {
                    clientinvoicing_list = (ArrayList) session.getAttribute("CLIENTINVOICING_LIST");
                }
                int total = clientinvoicing_list.size();
                if (total > 0)
                {
                    clientinvoicing_list = clientinvoicing.getFinalRecord(clientinvoicing_list, colid, updown);
                    session.setAttribute("CLIENTINVOICING_LIST", clientinvoicing_list);

                    ClientinvoicingInfo info;
                    for (int i = 0; i < total; i++)
                    {
                        info = (ClientinvoicingInfo) clientinvoicing_list.get(i);
                        if (info != null)
                        {
                            str.append("<tr class='hand_cursor' href='javascript: void(0);'>");
                             str.append("<td class='hand_cursor' href='javascript: void(0);' onclick=\"javascript: showDetail('" + info.getClientassetId() + "');\">" + (info.getClientName() != null ? info.getClientName() : "") + "</td>");
                            str.append("<td class='hand_cursor' href='javascript: void(0);' onclick=\"javascript: showDetail('" + info.getClientassetId() + "');\">" + (info.getClientassetName() != null ? info.getClientassetName() : "") + "</td>");
                            str.append("<td class='hand_cursor' href='javascript: void(0);' onclick=\"javascript: showDetail('" + info.getClientassetId() + "');\">" + (clientinvoicing.getschedulevalue(info.getType())) + "</td>");
                            str.append("<td class='hand_cursor assets_list text-center' href='javascript: void(0);' onclick=\"javascript: showDetail('" + info.getClientassetId() + "');\"> <a href=\"javascript:;\">" + (info.getInvoiceCount()) + "</a></td>");
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