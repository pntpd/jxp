<%@page contentType="text/html"%>
<%@page language="java" import="java.util.*"%>
<%@page import="com.web.jxp.airport.AirportInfo" %>
<jsp:useBean id="airport" class="com.web.jxp.airport.Airport" scope="page"/>
<jsp:useBean id="vobj" class="com.web.jxp.base.Validate" scope="page"/>
<%
    try
    {
        if(session.getAttribute("LOGININFO") != null)
        {
            String str = "";        
            if(request.getParameter("countryId") != null)
            {
                String clientIds = request.getParameter("countryId") != null && !request.getParameter("countryId").equals("") ? vobj.replaceint(request.getParameter("countryId")) : "";
                int countryId = Integer.parseInt(clientIds);
                response.setContentType("text/html");
                response.setHeader("Cache-Control", "no-cache");            
                if(countryId> 0)
                {
                    Collection coll = airport.getCities(countryId); 
                    StringBuffer sb = new StringBuffer();
                    if(coll.size() > 0)
                    {
                        Iterator iter = coll.iterator();
                        while (iter.hasNext())
                        {
                            AirportInfo info = (AirportInfo) iter.next();
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