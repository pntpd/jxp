<%@page import="java.util.Iterator"%>
<%@page import="java.util.Collection"%>
<%@page contentType="text/html"%>
<%@page language="java" import="java.util.ArrayList"%>
<%@page pageEncoding="UTF-8"%>
<%@page import="com.web.jxp.user.UserInfo" %>
<%@page import="com.web.jxp.talentpool.TalentpoolInfo" %>
<jsp:useBean id="talentpool" class="com.web.jxp.talentpool.Talentpool" scope="page"/>
<%
    try {
        if (session.getAttribute("LOGININFO") != null) {

            int allclient = 0;
            UserInfo uinfo = ((UserInfo) request.getSession().getAttribute("LOGININFO"));
            String str = "", per = "", assetids = "", cids = "";
            if (uinfo != null) {
                per = uinfo.getPermission() != null ? uinfo.getPermission() : "N";
                allclient = uinfo.getAllclient();
                cids = uinfo.getCids();
                assetids = uinfo.getAssetids();
            }
            response.setContentType("text/html");
            response.setHeader("Cache-Control", "no-cache");
            Collection coll = talentpool.getClients(cids, allclient, per);
            StringBuffer sb = new StringBuffer();
            if (coll.size() > 0) {
                Iterator iter = coll.iterator();
                while (iter.hasNext()) {
                    TalentpoolInfo info = (TalentpoolInfo) iter.next();
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
    } catch (Exception e) {
        e.printStackTrace();
        response.getWriter().write("Something went wrong.");
    }
%>