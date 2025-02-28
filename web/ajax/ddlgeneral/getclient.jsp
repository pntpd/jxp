<%@page contentType="text/html"%>
<%@page language="java" import="java.util.*"%>
<%@page import="com.web.jxp.talentpool.TalentpoolInfo" %>
<jsp:useBean id="ddl" class="com.web.jxp.talentpool.Ddl" scope="page"/>
<%
    try
    {
        if(session.getAttribute("LOGININFO") != null)
        {
            String str = "";  
            response.setContentType("text/html");
            response.setHeader("Cache-Control", "no-cache"); 
            StringBuilder sb = new StringBuilder();
            Collection coll = ddl.getClients();  
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
            str = sb.toString();
            sb.setLength(0);                
            response.getWriter().write(str);
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