<%@page contentType="text/html"%>
<%@page language="java" import="java.util.ArrayList"%>
<%@page pageEncoding="UTF-8"%>
<%@page import="com.web.jxp.user.UserInfo" %>
<%@page import="com.web.jxp.billingcycle.BillingcycleInfo" %>
<jsp:useBean id="billingcycle" class="com.web.jxp.billingcycle.Billingcycle" scope="page"/>
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
                ArrayList billingcycle_list = new ArrayList();
                if (session.getAttribute("BILLINGCYCLE_LIST") != null) {
                    billingcycle_list = (ArrayList) session.getAttribute("BILLINGCYCLE_LIST");
                }
                int total = billingcycle_list.size();
                if (total > 0) 
                {
                    billingcycle_list = billingcycle.getFinalRecord(billingcycle_list, colid, updown);
                    session.setAttribute("BILLINGCYCLE_LIST", billingcycle_list);
                
                    int status;
                    BillingcycleInfo info;
                    for (int i = 0; i < total; i++) 
                    {
                        info = (BillingcycleInfo) billingcycle_list.get(i);
                        if (info != null)
                        {
                            status = info.getBillingstatus();
                            str.append("<tr>");
                            str.append("<td>" + (info.getClientName() != null ? info.getClientName() : "") + "</td>");
                            str.append("<td>" + (info.getClientAssetName() != null ? info.getClientAssetName() : "") + "</td>");
                            str.append("<td  class='assets_list text-center' data-org-colspan='1' data-columns='tech-companies-1-col-2'><a href='javascript: void(0);'>" + info.getPcount() + "</a></td>");
                            str.append("<td class='text-center'>" + (info.getTypevalue() != null ? info.getTypevalue() : "") + "</td>");
                            str.append("<td class='action_column'>");
                            if (editper.equals("Y")) 
                            {
                            if(info.getTypevalue() != null && !info.getTypevalue().equals(""))                     
                                str.append("<a class='mr_15' href=\"javascript: showDetail('"+ info.getClientId()+"','"+ info.getAssetId()+"')\"><img src='../assets/images/pencil.png' /></a>");
                            else                            
                                str.append("<a class='mr_15' href=\"javascript: showDetail('"+ info.getClientId()+"','"+ info.getAssetId()+"');\"><img src='../assets/images/link.png' /></a>");                            
                            }
                            str.append("<span class='switch_bth'>");
                            str.append("<div class='form-check form-switch'>");
                            str.append("<input class='form-check-input' type='checkbox' role='switch' id='flexSwitchCheckDefault_"+(i)+"'");
                            if(status == 1)
                            {
                                str.append("checked "); 
                            }
                            if(!editper.equals("Y"))
                            {
                                str.append("disabled='true'"); 
                            } 
                            str.append(" onclick=\"javascript: deleteFormDetail('"+ info.getClientId()+"','"+ info.getAssetId()+"', '"+status+"', '"+i+"');\"/>");
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