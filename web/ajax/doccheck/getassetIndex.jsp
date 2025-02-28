<%@page import="java.util.Iterator"%>
<%@page import="java.util.Collection"%>
<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@page import="com.web.jxp.user.UserInfo" %>
<%@page import="com.web.jxp.doccheck.DoccheckInfo" %>
<jsp:useBean id="doccheck" class="com.web.jxp.doccheck.Doccheck" scope="page"/>
<jsp:useBean id="vobj" class="com.web.jxp.base.Validate" scope="page"/>
<%
    try 
    {
        if (session.getAttribute("LOGININFO") != null) 
        {
            int allclient = 0;
            UserInfo uinfo = ((UserInfo) request.getSession().getAttribute("LOGININFO"));
            String str = "", per = "", assetids = "";
            if (uinfo != null)
            {
                per = uinfo.getPermission() != null ? uinfo.getPermission() : "N";
                allclient = uinfo.getAllclient();
                assetids = uinfo.getAssetids();
            }
            if (request.getParameter("clientIndex") != null) 
            {
                String assetIds = request.getParameter("clientIndex") != null && !request.getParameter("clientIndex").equals("") ? vobj.replaceint(request.getParameter("clientIndex")) : "0";
                int clientIndex = Integer.parseInt(assetIds);
                response.setContentType("text/html");
                response.setHeader("Cache-Control", "no-cache");
                Collection coll = doccheck.getClientAsset(clientIndex, assetids, allclient, per);
                StringBuffer sb = new StringBuffer();
                if (coll.size() > 0) 
                {
                    Iterator iter = coll.iterator();
                    while (iter.hasNext()) 
                    {
                        DoccheckInfo info = (DoccheckInfo) iter.next();
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