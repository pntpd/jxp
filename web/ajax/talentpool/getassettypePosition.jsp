<%@page import="java.util.Iterator"%>
<%@page import="java.util.Collection"%>
<%@page contentType="text/html"%>
<%@page language="java" import="java.util.ArrayList"%>
<%@page pageEncoding="UTF-8"%>
<%@page import="com.web.jxp.user.UserInfo" %>
<%@page import="com.web.jxp.talentpool.TalentpoolInfo" %>
<jsp:useBean id="ddl" class="com.web.jxp.talentpool.Ddl" scope="page"/>
<jsp:useBean id="vobj" class="com.web.jxp.base.Validate" scope="page"/>
<%
    try
    {
        if(session.getAttribute("LOGININFO") != null)
        {
            String str = "";        
            if(request.getParameter("assettypeIdIndex") != null)
            {
                String assettypeIdIndexs = request.getParameter("assettypeIdIndex") != null && !request.getParameter("assettypeIdIndex").equals("") ? vobj.replaceint(request.getParameter("assettypeIdIndex")) : "";
                int assettypeIdIndex = Integer.parseInt(assettypeIdIndexs);
                response.setContentType("text/html");
                response.setHeader("Cache-Control", "no-cache");  
                Collection coll = ddl.getAssetypePositions(assettypeIdIndex);
                StringBuffer sb = new StringBuffer();
                if(assettypeIdIndex > 0)
                {
                    if(coll.size() > 0)
                    {
                        Iterator iter = coll.iterator();
                        while (iter.hasNext())
                        {
                            TalentpoolInfo info = (TalentpoolInfo) iter.next();
                            int val =  info.getDdlValue();
                            String name = info.getDdlLabel()!= null ? info.getDdlLabel() : "";
                            sb.append("<option value='"+val+"'>"+name+"</option>");
                        }
                    }
                }else
                {
                    sb.append("<option value='-1'>Select Position | Rank</option>");
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