<%@page contentType="text/html"%>
<%@page language="java" import="java.util.*"%>
<%@page import="com.web.jxp.talentpool.TalentpoolInfo" %>
<jsp:useBean id="ddl" class="com.web.jxp.talentpool.Ddl" scope="page"/>
<jsp:useBean id="vobj" class="com.web.jxp.base.Validate" scope="page"/>
<%
    try
    {
        if(session.getAttribute("LOGININFO") != null)
        {
            String str = "";        
            if(request.getParameter("assetId") != null)
            {
                String assetIds = request.getParameter("assetId") != null && !request.getParameter("assetId").equals("") ? vobj.replaceint(request.getParameter("assetId")) : "-1";
                int assetId = Integer.parseInt(assetIds);
                response.setContentType("text/html");
                response.setHeader("Cache-Control", "no-cache"); 
                StringBuilder sb = new StringBuilder();
                if(assetId > 0)
                {
                    Collection coll = ddl.getPostions(assetId);  
                    if(coll.size() > 0)
                    {
                        Iterator iter = coll.iterator();
                        while (iter.hasNext())
                        {
                            TalentpoolInfo info = (TalentpoolInfo) iter.next();
                            int val =  info.getDdlValue();
                            String name = info.getDdlLabel() != null ? info.getDdlLabel() : "";
                            sb.append("<option value='"+val+"'>"+name+"</option>");
                        }
                    }
                    coll.clear();                    
                }
                str = sb.toString();
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
    }
    catch(Exception e)
    {        
        
    }
%>