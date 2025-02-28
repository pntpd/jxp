<%@page import="com.web.jxp.user.UserInfo"%>
<%@page contentType="text/html"%>
<%@page language="java" import="java.util.*"%>
<%@page import="com.web.jxp.feedback.FeedbackInfo" %>
<jsp:useBean id="feedback" class="com.web.jxp.feedback.Feedback" scope="page"/>
<jsp:useBean id="vobj" class="com.web.jxp.base.Validate" scope="page"/>
<%
    try 
    {
        if (session.getAttribute("CREWLOGIN") != null) 
        {  
            String str = "";
            if (request.getParameter("crewClientId") != null) 
            {
                String crewClientIds = request.getParameter("crewClientId") != null && !request.getParameter("crewClientId").equals("") ? vobj.replaceint(request.getParameter("crewClientId")) : "";
                String candidateIds = request.getParameter("candidateId") != null && !request.getParameter("candidateId").equals("") ? vobj.replaceint(request.getParameter("candidateId")) : "";
                int candidateId = Integer.parseInt(candidateIds);
                int crewClientId = Integer.parseInt(crewClientIds);
                response.setContentType("text/html");
                response.setHeader("Cache-Control", "no-cache");
                Collection coll = feedback.getCrewAssets(candidateId, crewClientId);
                StringBuffer sb = new StringBuffer();
                if (crewClientId > 0) 
                {
                    if (coll.size() > 0) 
                    {
                        Iterator iter = coll.iterator();
                        while (iter.hasNext()) 
                        {
                            FeedbackInfo info = (FeedbackInfo) iter.next();
                            int val = info.getDdlValue();
                            String name = info.getDdlLabel() != null ? info.getDdlLabel() : "";
                            sb.append("<option value='" + val + "'>" + name + "</option>");
                        }
                    }
                } else {
                    sb.append("<option value='-1'> Select Asset </option>");
                }
                str = sb.toString();
                sb.setLength(0);
                coll.clear();
                response.getWriter().write(str);
            } else {
                response.setStatus(HttpServletResponse.SC_NO_CONTENT);
            }
        } else {
            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
%>