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
            if (request.getParameter("searchPersonnel") != null) 
            {
                String searchPersonnel = request.getParameter("searchPersonnel") != null && !request.getParameter("searchPersonnel").equals("") ? vobj.replacedesc(request.getParameter("searchPersonnel")) : "";
                String clientIdIndexs = request.getParameter("clientIdIndex") != null && !request.getParameter("clientIdIndex").equals("") ? vobj.replaceint(request.getParameter("clientIdIndex")) : "";
                int clientIdIndex = Integer.parseInt(clientIdIndexs);
                String assetIdIndexs = request.getParameter("assetIdIndex") != null && !request.getParameter("assetIdIndex").equals("") ? vobj.replaceint(request.getParameter("assetIdIndex")) : "";
                int assetIdIndex = Integer.parseInt(assetIdIndexs);
                String crewrotaioncb = "";
                if (session.getAttribute("CREWROTATIONCB") != null) {
                    crewrotaioncb = (String) session.getAttribute("CREWROTATIONCB");
                }
                response.setContentType("text/html");
                response.setHeader("Cache-Control", "no-cache");
                ArrayList personnellist  = dashboard.getPersonnellistfromcrewrotation(clientIdIndex, assetIdIndex,"","","","",searchPersonnel);
                StringBuffer sb = new StringBuffer();
                int personlisttotal = personnellist.size();
                if (personlisttotal > 0)
                {
                    if(personlisttotal > 0)
                    {
                        for(int i = 0 ;i < personlisttotal; i++)
                        {
                            DashboardInfo personinfo = (DashboardInfo)personnellist.get(i);
                            if (personinfo != null) 
                            {
                                String candidatename = personinfo.getCandidateName() != null && !personinfo.getCandidateName().equals("") ? personinfo.getCandidateName() : "";
                                sb.append("<ul>");
                                sb.append("<li>");
                                sb.append("<label class='mt-checkbox mt-checkbox-outline'>");
                                sb.append(candidatename);
                                sb.append("<input type='checkbox' value='");
                                sb.append(personinfo.getCrewrotationId()+"'");
                                sb.append("name='crewcb' onchange =\" javascript :setValperson();\" ");
                                //check logic
                                if(dashboard.checkToStr(crewrotaioncb,""+personinfo.getCrewrotationId()))
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