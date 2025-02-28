<%@page contentType="text/html"%>
<%@page language="java" import="java.util.*"%>
<%@page import="com.web.jxp.user.UserInfo" %>
<jsp:useBean id="user" class="com.web.jxp.user.User" scope="page"/>
<jsp:useBean id="vobj" class="com.web.jxp.base.Validate" scope="page"/>
<%
    try 
    {
        if (session.getAttribute("LOGININFO") != null) 
        {
            String str = "";
            if (request.getParameter("countryId") != null)
            {
                String clientIds = request.getParameter("clientId") != null && !request.getParameter("clientId").equals("") ? vobj.replaceint(request.getParameter("clientId")) : "";
                String countryIds = request.getParameter("countryId") != null && !request.getParameter("countryId").equals("") ? vobj.replaceint(request.getParameter("countryId")) : "";
                int clientId = Integer.parseInt(clientIds);
                int countryId = Integer.parseInt(countryIds);
                response.setContentType("text/html");
                response.setHeader("Cache-Control", "no-cache");
                StringBuilder sb = new StringBuilder();
                sb.append("<select name='assetId' id='multiselect_dd' class='form-select form-control btn btn-default mt-multiselect' multiple='multiple' data-select-all='true' data-label='left' data-width='100%' data-filter='false'  onchange=\"javascript: setPositionRank();\">");
                if (clientId > 0) 
                {
                    Collection coll = user.getClientAssets(clientId, countryId);
                    if (coll.size() > 0) 
                    {
                        Iterator iter = coll.iterator();
                        while (iter.hasNext()) 
                        {
                            UserInfo info = (UserInfo) iter.next();
                            int val = info.getUserId();
                            String name = info.getName() != null ? info.getName() : "";
                            sb.append("<option value='" + val + "'>" + name + "</option>");
                        }
                    }
                    coll.clear();
                }
                sb.append("<select>");
                str = sb.toString();
                sb.setLength(0);
                response.getWriter().write(str);
            } else {
                response.setStatus(HttpServletResponse.SC_NO_CONTENT);
            }
        } else {
            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
        }
    } catch (Exception e) {
        e.printStackTrace();
        response.getWriter().write("Something went wrong.");
    }
%>