<%@page import="java.util.Iterator"%>
<%@page import="java.util.Collection"%>
<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@page import="com.web.jxp.competencypriorities.CompetencyprioritiesInfo" %>
<jsp:useBean id="competencypriorities" class="com.web.jxp.competencypriorities.Competencypriorities" scope="page"/>
<jsp:useBean id="vobj" class="com.web.jxp.base.Validate" scope="page"/>
<%
    try 
    {
        if (session.getAttribute("LOGININFO") != null) 
        {
            if (request.getParameter("assetId") != null) 
            {
                String assetIds = request.getParameter("assetId") != null && !request.getParameter("assetId").equals("") ? vobj.replaceint(request.getParameter("assetId")) : "-1";
                int assetId = Integer.parseInt(assetIds);
                response.setContentType("text/html");
                response.setHeader("Cache-Control", "no-cache");                
                StringBuffer sb = new StringBuffer();
                Collection coll = competencypriorities.getPdepts(assetId, 1);                    
                if (coll.size() > 0) 
                {
                    Iterator iter = coll.iterator();
                    while (iter.hasNext()) 
                    {
                        CompetencyprioritiesInfo info = (CompetencyprioritiesInfo) iter.next();
                        int val = info.getDdlValue();
                        String name = info.getDdlLabel() != null ? info.getDdlLabel() : "";
                        sb.append("<option value='" + val + "'>" + name + "</option>");
                    }
                }
                coll.clear();
                sb.append("</select>");
                String str = sb.toString();
                sb.setLength(0);    
                response.getWriter().write(str);
            } 
            else 
            {
                response.setStatus(HttpServletResponse.SC_NO_CONTENT);
            }
        } 
        else 
        {
            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
        }
    } catch (Exception e) {

    }
%>