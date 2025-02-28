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
            UserInfo uinfo = ((UserInfo) request.getSession().getAttribute("LOGININFO"));
            String editper = "N", addper = "N";
            if (uinfo != null)
            {
                editper = uinfo.getEditper() != null ? uinfo.getEditper() : "N";
                addper = uinfo.getAddper()!= null ? uinfo.getAddper() : "N";
            }
            String view_path = clientinvoicing.getMainPath("view_timesheet_file");
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

                session.setAttribute("NEXTVALUETS", next_value + "");
                ArrayList clientinvoicing_list = new ArrayList();
                if (session.getAttribute("CLIENTTIMESHEET_LIST") != null) {
                    clientinvoicing_list = (ArrayList) session.getAttribute("CLIENTTIMESHEET_LIST");
                }
                int total = clientinvoicing_list.size();
                if (total > 0) 
                {
                    clientinvoicing_list = clientinvoicing.getFinalRecordTS(clientinvoicing_list, colid, updown);
                    session.setAttribute("CLIENTTIMESHEET_LIST", clientinvoicing_list);
                
                    ClientinvoicingInfo info;
                    for (int i = 0; i < total; i++) 
                    {
                        info = (ClientinvoicingInfo) clientinvoicing_list.get(i);
                        if (info != null) 
                        {
                            str.append("<tr>");
                             str.append("<td>" + (clientinvoicing.changeNum( info.getInvoiceId(),6)) + "</td>");
                             str.append("<td>" + (clientinvoicing.getInvoicestatusvalue(info.getInvoicestatus())) + "</td>");
                            str.append("<td>" + (info.getGeneratedate() != null ? info.getGeneratedate() : "") + "</td>");
                            str.append("<td>" + (info.getSentdate() != null ? info.getSentdate() : "") + "</td>");
                            str.append("<td>" + (info.getFromdate() != null ? info.getFromdate() : "") + "</td>");
                            str.append("<td>" + (info.getTodate() != null ? info.getTodate() : "") + "</td>");
                             if (editper.equals("Y") || addper.equals("Y")){ 
                            str.append("<td class='action_column text-right'>");
                                if(info.getInvoicestatus() <= 2){
                            str.append("<a href=\"javascript:;\" onclick=\" javascript: doGenerateinvoice('"+ info.getTimesheetId()+"');\" class='mr_15 generate_action'>Generate </a>");
                                } else if(info.getInvoicestatus() == 3){
                            str.append("<a href=\"javascript:;\" onclick=\" javascript: doPayment('"+ info.getTimesheetId()+"');\" class='mr_15'><img src='../assets/images/view.png'> </a>");
                                } else if(info.getInvoicestatus() == 4){
                            str.append("<a href=\"javascript:;\" onclick=\" javascript: doPaymentView('"+ info.getTimesheetId()+"');\" class='mr_15'><img src='../assets/images/view.png'> </a>");
                                }
                                if(!info.getSendappfile().equals("")) {                                                                                
                            str.append("<a  href=\"javascript: seturl('"+(view_path+info.getSendappfile())+"');\"><img src='../assets/images/attachment.png'></a>");
                                }
                            str.append("</td>");
                             }
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