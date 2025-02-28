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
            if(request.getParameter("clientId") != null )
            {
                String str = "";
                String clientIds = request.getParameter("clientId") != null && !request.getParameter("clientId").equals("") ? vobj.replaceint(request.getParameter("clientId")) : "0";  
                int clientId = Integer.parseInt(clientIds);
                response.setContentType("text/html");
                response.setHeader("Cache-Control", "no-cache");   
                
                Collection coll = onboarding.getOboardingFormalityClientIndex(clientId);        
                StringBuffer sb = new StringBuffer();
                if(coll.size() > 0)
                {
                    Iterator iter = coll.iterator();
                    int val;
                    String name;
                    while (iter.hasNext())
                    {
                        OnboardingInfo info = (OnboardingInfo) iter.next();
                        val =  info.getDdlValue();
                        name = info.getDdlLabel() != null ? info.getDdlLabel() : "";
                        sb.append("<option value='"+val+"'>"+name+"</option>");
                    }
                }
                str = sb.toString();
                String s = str + "#@#";              
                sb.setLength(0);
                coll.clear();
                
                Collection coll2 = onboarding.getClientAsset(clientId);  
                if(coll2.size() > 0)
                {
                    Iterator iter = coll2.iterator();
                    int val;
                    String name;
                    while (iter.hasNext())
                    {
                        OnboardingInfo info = (OnboardingInfo) iter.next();
                        val =  info.getDdlValue();
                        name = info.getDdlLabel() != null ? info.getDdlLabel() : "";
                        sb.append("<option value='"+val+"'>"+name+"</option>");
                    }
                } else
                {
                    sb.append("<option value='-1'> Select Asset </option>");
                }
                str = sb.toString();
                s += str;
                sb.setLength(0);
                coll2.clear();
                response.getWriter().write(s);
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