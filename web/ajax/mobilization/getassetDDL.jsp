<%@page import="java.util.Iterator"%>
<%@page import="java.util.Collection"%>
<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@page import="com.web.jxp.user.UserInfo" %>
<%@page import="com.web.jxp.mobilization.MobilizationInfo" %>
<jsp:useBean id="mobilization" class="com.web.jxp.mobilization.Mobilization" scope="page"/>
<jsp:useBean id="vobj" class="com.web.jxp.base.Validate" scope="page"/>
<%
    try 
    {
        if (session.getAttribute("LOGININFO") != null) 
        {
            int  allclient = 0;
            String permission = "N", assetids = "";
            UserInfo uInfo = (UserInfo) request.getSession().getAttribute("LOGININFO");
            if (uInfo != null) 
            {
                permission = uInfo.getPermission();
                allclient = uInfo.getAllclient();
                assetids = uInfo.getAssetids();
            }
            String str = "";
            if (request.getParameter("clientIdIndex") != null) 
            {
                String clientIds = request.getParameter("clientIdIndex") != null && !request.getParameter("clientIdIndex").equals("") ? vobj.replaceint(request.getParameter("clientIdIndex")) : "0";
                int clientId = Integer.parseInt(clientIds);
                response.setContentType("text/html");
                response.setHeader("Cache-Control", "no-cache");
                Collection coll = mobilization.getClientAsset(clientId, assetids, allclient, permission);
                StringBuffer sb = new StringBuffer();
                if (coll.size() > 0) 
                {
                    Iterator iter = coll.iterator();
                    while (iter.hasNext()) 
                    {
                        MobilizationInfo info = (MobilizationInfo) iter.next();
                        int val = info.getDdlValue();
                        String name = info.getDdlLabel() != null ? info.getDdlLabel() : "";
                        sb.append("<option value='" + val + "'>" + name + "</option>");
                    }
                }
                str = sb.toString();
                sb.setLength(0);
                coll.clear();
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