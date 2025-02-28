<%@page contentType="text/html"%>
<%@page language="java" import="java.util.*"%>
<%@page import="com.web.jxp.city.CityInfo" %>
<jsp:useBean id="city" class="com.web.jxp.city.City" scope="page"/>
<jsp:useBean id="vobj" class="com.web.jxp.base.Validate" scope="page"/>
<%
    try
    {
        if(session.getAttribute("LOGININFO") != null)
        {
            String str = "";        
            if(request.getParameter("countrytId") != null )
            {
                String countryIds = request.getParameter("countrytId") != null && !request.getParameter("countrytId").equals("") ? vobj.replaceint(request.getParameter("countrytId")) : "";  
                int countryId = Integer.parseInt(countryIds);
                response.setContentType("text/html");
                response.setHeader("Cache-Control", "no-cache");   
                Collection coll = new LinkedList();
                if(countryId > 0 )
                {
                    coll = city.getCitybycountryId(countryId);                     
                }
                else
                {
                    coll.add(new CityInfo(-1, "Select city"));
                }
                StringBuffer sb = new StringBuffer();
                if(coll.size() > 0)
                {
                    Iterator iter = coll.iterator();
                    while (iter.hasNext())
                    {
                        CityInfo info = (CityInfo) iter.next();
                        int val =  info.getDdlValue();
                        String name = info.getDdlLabel() != null ? info.getDdlLabel() : "";
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