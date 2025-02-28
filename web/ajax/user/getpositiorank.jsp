<%@page import="java.util.Iterator"%>
<%@page import="java.util.Collection"%>
<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@page import="com.web.jxp.user.UserInfo" %>
<jsp:useBean id="user" class="com.web.jxp.user.User" scope="page"/>
<jsp:useBean id="vobj" class="com.web.jxp.base.Validate" scope="page"/>
<%
    try 
    {
        if (session.getAttribute("LOGININFO") != null) 
        {
            if (request.getParameter("assetId") != null) 
            {
                String assetIds = request.getParameter("assetId") != null ? request.getParameter("assetId").replaceAll(",", ", ") : "";
                
                response.setContentType("text/html");
                response.setHeader("Cache-Control", "no-cache");                
                StringBuffer sb = new StringBuffer();
                sb.append("<select name='positionRank' id='position_rank' class='form-select form-control btn btn-default mt-multiselect' multiple='multiple' data-select-all='true' data-label='left' data-width='100%' data-filter='false'>");                                                  
                if (!assetIds.equals("")) 
                {
                    Collection coll = user.setPositionRank(assetIds);                    
                    if (coll.size() > 0) 
                    {
                        Iterator iter = coll.iterator();
                        while (iter.hasNext()) 
                        {
                            UserInfo info = (UserInfo) iter.next();
                            int val = info.getUserId();
                            String name = info.getName()!= null ? info.getName(): "";
                            sb.append("<option value='" + val + "' select>" + name + "</option>");
                        }
                    }
                    coll.clear();
                } 
                sb.append("</select>");
                String str = sb.toString();
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
    } catch (Exception e) {

    }
%>