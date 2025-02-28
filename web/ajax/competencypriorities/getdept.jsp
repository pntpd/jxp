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
                String ids = request.getParameter("ids") != null && !request.getParameter("ids").equals("") ? vobj.replacedesc(request.getParameter("ids")) : "";
                int assetId = Integer.parseInt(assetIds);
                response.setContentType("text/html");
                response.setHeader("Cache-Control", "no-cache");                
                StringBuffer sb = new StringBuffer();
                sb.append("<select name='deptColum' id='deptmultiselect_dd' class='form-select form-control btn btn-default mt-multiselect' multiple='multiple' data-select-all='true' data-label='left' data-width='100%' data-filter='false'>");                                                  
                if (assetId > 0) 
                {
                    Collection coll = competencypriorities.getPdepts(assetId, 0);                    
                    if (coll.size() > 0) 
                    {
                        Iterator iter = coll.iterator();
                        while (iter.hasNext()) 
                        {
                            CompetencyprioritiesInfo info = (CompetencyprioritiesInfo) iter.next();
                            int val = info.getDdlValue();
                            String name = info.getDdlLabel() != null ? info.getDdlLabel() : "";
                            if(competencypriorities.checkToStr(ids, ""+val))
                                sb.append("<option value='" + val + "' selected>" + name + "</option>");
                            else
                                sb.append("<option value='" + val + "'>" + name + "</option>");
                        }
                    }
                    coll.clear();
                } 
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