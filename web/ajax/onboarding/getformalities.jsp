<%@page import="java.util.Iterator"%>
<%@page import="java.util.Collection"%>
<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@page import="com.web.jxp.user.UserInfo"%>
<%@page contentType="text/html"%>
<%@page language="java" import="java.util.*"%>
<%@page import="com.web.jxp.onboarding.OnboardingInfo" %>
<jsp:useBean id="onboarding" class="com.web.jxp.onboarding.Onboarding" scope="page"/>
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
                String clientassetIds = request.getParameter("clientassetId") != null && !request.getParameter("clientassetId").equals("") ? vobj.replaceint(request.getParameter("clientassetId")) : "";
                int clientassetId = Integer.parseInt(clientassetIds);
                response.setContentType("text/html");
                response.setHeader("Cache-Control", "no-cache");            
                if(clientId> 0)
                {
                    Collection coll = onboarding.getformalitiesById(clientId, clientassetId);
                    StringBuffer sb = new StringBuffer();
                    if(coll.size() > 0)
                    {
                        Iterator iter = coll.iterator();
                        while (iter.hasNext())
                        {
                            OnboardingInfo info = (OnboardingInfo) iter.next();
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