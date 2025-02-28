<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.Collection"%>
<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@page import="com.web.jxp.user.UserInfo" %>
<%@page import="com.web.jxp.dashboard.DashboardInfo" %>
<jsp:useBean id="dashboard" class="com.web.jxp.dashboard.Dashboard" scope="page"/>
<jsp:useBean id="vobj" class="com.web.jxp.base.Validate" scope="page"/>
<%
    try 
    {
        if (session.getAttribute("LOGININFO") != null) 
        {
            String str = "";
            if (request.getParameter("searchPosition") != null) 
            {
                String searchPosition = request.getParameter("searchPosition") != null && !request.getParameter("searchPosition").equals("") ? vobj.replacedesc(request.getParameter("searchPosition")) : "";
                String assetIdIndexs = request.getParameter("assetIdIndex") != null && !request.getParameter("assetIdIndex").equals("") ? vobj.replaceint(request.getParameter("assetIdIndex")) : "";
                int assetIdIndex = Integer.parseInt(assetIdIndexs);
                String positioncb = "";
                if (session.getAttribute("POSITIONCB") != null) {
                    positioncb = (String) session.getAttribute("POSITIONCB");
                }
                response.setContentType("text/html");
                response.setHeader("Cache-Control", "no-cache");
                ArrayList positionlist  = dashboard.getPositionlist( assetIdIndex, searchPosition);
                StringBuffer sb = new StringBuffer();
                int positionlisttotal = positionlist.size();
                if (positionlisttotal > 0) 
                {
                    if(positionlisttotal > 0)
                    {
                        for(int i = 0 ;i < positionlisttotal; i++)
                        {
                            DashboardInfo positioninfo = (DashboardInfo)positionlist.get(i);
                            if (positioninfo != null) 
                            {
                                String positionname = positioninfo.getPosition() != null && !positioninfo.getPosition().equals("") ? positioninfo.getPosition() : "";
                                sb.append("<ul>");
                                sb.append("<li>");
                                sb.append("<label class='mt-checkbox mt-checkbox-outline'>");
                                sb.append(positionname);
                                sb.append("<input type='checkbox' value='");
                                sb.append(positioninfo.getPositionId()+"'");
                                sb.append("name='pcb' onchange =\" javascript :setValposition();\" ");
                                //checked logic
                                if(dashboard.checkToStr(positioncb,""+positioninfo.getPositionId()))
                                {
                                    sb.append(" checked ");
                                }
                                sb.append("/>");
                                sb.append("<span></span>");
                                sb.append("</label>");	
                                sb.append("</li>");
                            }
                        }
                    }
                    sb.append("</ul>");
                }
                else {
                    sb.append("no data available");
                }
                str = sb.toString();
                sb.setLength(0);
                response.getWriter().write(str);
            } else {
                response.setStatus(HttpServletResponse.SC_NO_CONTENT);
            }
        }
         else {
            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
        }
    } catch (Exception e) {

    }
%>