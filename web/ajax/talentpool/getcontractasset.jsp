<%@page import="com.web.jxp.user.UserInfo"%>
<%@page contentType="text/html"%>
<%@page language="java" import="java.util.*"%>
<%@page import="com.web.jxp.talentpool.TalentpoolInfo" %>
<jsp:useBean id="talentpool" class="com.web.jxp.talentpool.Talentpool" scope="page"/>
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
            if (request.getParameter("clientIdContract") != null) 
            {
                String clientIdContracts = request.getParameter("clientIdContract") != null && !request.getParameter("clientIdContract").equals("") ? vobj.replaceint(request.getParameter("clientIdContract")) : "";
                int clientIdContract = Integer.parseInt(clientIdContracts);
                response.setContentType("text/html");
                response.setHeader("Cache-Control", "no-cache");
                Collection coll = talentpool.getClientAsset(clientIdContract, assetids, allclient, per);
                StringBuffer sb = new StringBuffer();
                if (clientIdContract > 0) 
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
            } else {
                response.setStatus(HttpServletResponse.SC_NO_CONTENT);
            }
        } else {
            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
%>