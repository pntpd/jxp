<%@page contentType="text/html"%>
<%@page language="java" import="java.util.*"%>
<%@page import="com.web.jxp.clientselection.ClientselectionInfo" %>
<jsp:useBean id="clientselection" class="com.web.jxp.clientselection.Clientselection" scope="page"/>
<jsp:useBean id="vobj" class="com.web.jxp.base.Validate" scope="page"/>
<%
    try
    {
        if(session.getAttribute("LOGININFO") != null)
        {                 
            if(request.getParameter("clientId") != null )
            {
                String str = "";
                String clientIds = request.getParameter("clientId") != null && !request.getParameter("clientId").equals("") ? vobj.replaceint(request.getParameter("clientId")) : "0";  
                int clientId = Integer.parseInt(clientIds);
                response.setContentType("text/html");
                response.setHeader("Cache-Control", "no-cache");   
                Collection coll = new LinkedList();
                if(clientId > 0 )
                {
                    coll = clientselection.getResumetemplatesforclientindex(clientId, 1);                 
                }
                else
                {
                    coll.add(new ClientselectionInfo(-1, "Select Template"));
                }
                StringBuffer sb = new StringBuffer();
                if(coll.size() > 0)
                {
                    Iterator iter = coll.iterator();
                    int val;
                    String name;
                    while (iter.hasNext())
                    {
                        ClientselectionInfo info = (ClientselectionInfo) iter.next();
                        val =  info.getDdlValue();
                        name = info.getDdlLabel() != null ? info.getDdlLabel() : "";
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
        e.printStackTrace();
        response.getWriter().write("Something went wrong.");
    }
%>