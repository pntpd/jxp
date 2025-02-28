<%@page import="com.web.jxp.user.UserInfo"%>
<%@page contentType="text/html"%>
<%@page language="java" import="java.util.*"%>
<%@page import="com.web.jxp.talentpool.TalentpoolInfo" %>
<jsp:useBean id="talentpool" class="com.web.jxp.talentpool.Talentpool" scope="page"/>
<jsp:useBean id="vobj" class="com.web.jxp.base.Validate" scope="page"/>
<%
    try 
    {
        UserInfo uInfo = (UserInfo) request.getSession().getAttribute("LOGININFO");
        String s = "";        
        if (uInfo != null) 
        {
            String assetIds = request.getParameter("toAssetId") != null && !request.getParameter("toAssetId").equals("") ? vobj.replaceint(request.getParameter("toAssetId")) : "0";
            int toAssetId = Integer.parseInt(assetIds);
            String positionIds = request.getParameter("toPositionId") != null && !request.getParameter("toPositionId").equals("") ? vobj.replaceint(request.getParameter("toPositionId")) : "0";
            int toPositionId = Integer.parseInt(positionIds);
            StringBuffer sb = new StringBuffer();
            if (toAssetId > 0) 
            {
                Collection coll = talentpool.getPostions2(toAssetId, toPositionId);
                if (coll.size() > 0) 
                {
                    Iterator iter = coll.iterator();
                    while (iter.hasNext())
                    {
                        TalentpoolInfo rinfo = (TalentpoolInfo) iter.next();
                        int val = rinfo.getDdlValue();
                        String n = rinfo.getDdlLabel() != null ? rinfo.getDdlLabel() : "";
                        sb.append("<option value='" + val + "'>" + n + "</option>");
                    }
                }
                coll.clear();
            } else {
                sb.append("<option value='-1'>Select Position | Rank </option>");
            }
            s = sb.toString();
            sb.setLength(0);
        }
        response.getWriter().write(s);
    } catch (Exception e) {
        e.printStackTrace();
    }
%>