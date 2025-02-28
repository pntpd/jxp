<%@page import="com.web.jxp.crewdb.CrewdbInfo"%>
<%@page contentType="text/html"%>
<%@page language="java" import="java.util.*"%>
<%@page import="com.web.jxp.user.UserInfo" %>
<jsp:useBean id="crewdb" class="com.web.jxp.crewdb.Crewdb" scope="page"/>
<jsp:useBean id="vobj" class="com.web.jxp.base.Validate" scope="page"/>
<%
    try
    {
        if(session.getAttribute("LOGININFO") != null)
        {
            String str = "";        
            if(request.getParameter("clientId") != null)
            {
                String clientIds = request.getParameter("clientId") != null && !request.getParameter("clientId").equals("") ? vobj.replaceint(request.getParameter("clientId")) : "";
                int clientId = Integer.parseInt(clientIds);
                response.setContentType("text/html");
                response.setHeader("Cache-Control", "no-cache");   
                Collection coll = new LinkedList();
                if(clientId > 0)
                {
                    coll = crewdb.getClientLocations(clientId);                     
                }
                else
                {
                    coll.add(new CrewdbInfo(-1, "Select Location"));
                }
                StringBuffer sb = new StringBuffer();
                if(coll.size() > 0)
                {
                    Iterator iter = coll.iterator();
                    while (iter.hasNext())
                    {
                        CrewdbInfo info = (CrewdbInfo) iter.next();
                        int val =  info.getDdlValue();
                        String name = info.getDdlLabel()!= null ? info.getDdlLabel(): "";
                        sb.append("<option value='"+val+"'>"+name+"</option>");
                    }
                }
                str = sb.toString();
                sb.setLength(0);
                coll.clear();
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