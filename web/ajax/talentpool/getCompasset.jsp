<%@page import="java.util.Iterator"%>
<%@page import="java.util.Collection"%>
<%@page contentType="text/html"%>
<%@page language="java" import="java.util.ArrayList"%>
<%@page pageEncoding="UTF-8"%>
<%@page import="com.web.jxp.user.UserInfo" %>
<%@page import="com.web.jxp.talentpool.TalentpoolInfo" %>
<jsp:useBean id="talentpool" class="com.web.jxp.talentpool.Talentpool" scope="page"/>
<jsp:useBean id="ddl" class="com.web.jxp.talentpool.Ddl" scope="page"/>
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
            if (request.getParameter("clientId") != null)
            {
                String clientIds = request.getParameter("clientId") != null && !request.getParameter("clientId").equals("") ? vobj.replaceint(request.getParameter("clientId")) : "";
                int clientId = Integer.parseInt(clientIds);
                response.setContentType("text/html");
                response.setHeader("Cache-Control", "no-cache");
                Collection coll = talentpool.getClientAsset(clientId, assetids, allclient, per);
                StringBuffer sb = new StringBuffer();
                if (clientId > 0) 
                {
                    if (coll.size() > 0)
                    {
                        Iterator iter = coll.iterator();
                        while (iter.hasNext()) 
                        {
                            TalentpoolInfo info = (TalentpoolInfo) iter.next();
                            int val = info.getDdlValue();
                            String name = info.getDdlLabel() != null ? info.getDdlLabel() : "";
                            sb.append("<option value='" + val + "'>" + name + "</option>");
                        }
                    }
                } else {
                    sb.append("<option value='-1'> Select Asset </option>");
                }
                str = sb.toString();
                sb.setLength(0);
                coll.clear();
                response.getWriter().write(str);
            } 
            else if (request.getParameter("assetIdcom") != null) 
            {
                String clientassetIds = request.getParameter("assetIdcom") != null && !request.getParameter("assetIdcom").equals("") ? vobj.replaceint(request.getParameter("assetIdcom")) : "";
                int clientassetId = Integer.parseInt(clientassetIds);
                response.setContentType("text/html");
                response.setHeader("Cache-Control", "no-cache");
                Collection coll = ddl.getCopetencyDepartment(clientassetId);
                StringBuffer sb = new StringBuffer();
                if (clientassetId > 0)                         
                {
                    if (coll.size() > 0) 
                    {
                        Iterator iter = coll.iterator();
                        while (iter.hasNext()) 
                        {
                            TalentpoolInfo info = (TalentpoolInfo) iter.next();
                            int val = info.getDdlValue();
                            String name = info.getDdlLabel() != null ? info.getDdlLabel() : "";
                            sb.append("<option value='" + val + "'>" + name + "</option>");
                        }
                    }

                } else {
                    sb.append("<option value='-1'> Select Department </option>");
                }
                str = sb.toString();
                sb.setLength(0);
                coll.clear();
                response.getWriter().write(str);
            }
            else if (request.getParameter("pdeptId") != null) 
            {
                String pdeptIds = request.getParameter("pdeptId") != null && !request.getParameter("pdeptId").equals("") ? vobj.replaceint(request.getParameter("pdeptId")) : "";
                String clientassetIds = request.getParameter("clientassetId") != null && !request.getParameter("clientassetId").equals("") ? vobj.replaceint(request.getParameter("clientassetId")) : "";
                int pdeptId = Integer.parseInt(pdeptIds);
                int clientassetId = Integer.parseInt(clientassetIds);
                response.setContentType("text/html");
                response.setHeader("Cache-Control", "no-cache");
                Collection coll = ddl.getCopetencyPostions(clientassetId, pdeptId);
                StringBuffer sb = new StringBuffer();
                if (clientassetId > 0)                         
                {
                    if (coll.size() > 0) 
                    {
                        Iterator iter = coll.iterator();
                        while (iter.hasNext()) 
                        {
                            TalentpoolInfo info = (TalentpoolInfo) iter.next();
                            int val = info.getDdlValue();
                            String name = info.getDdlLabel() != null ? info.getDdlLabel() : "";
                            sb.append("<option value='" + val + "'>" + name + "</option>");
                        }
                    }

                } else {
                    sb.append("<option value='-1'> Select Position | Rank </option>");
                }
                str = sb.toString();
                sb.setLength(0);
                coll.clear();
                response.getWriter().write(str);
            }
            else {
                response.setStatus(HttpServletResponse.SC_NO_CONTENT);
            }            
        } else {
            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
%>