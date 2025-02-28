<%@page import="java.util.Iterator"%>
<%@page import="java.util.Collection"%>
<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@page import="com.web.jxp.user.UserInfo" %>
<%@page import="com.web.jxp.managewellness.ManagewellnessInfo" %>
<jsp:useBean id="managewellness" class="com.web.jxp.managewellness.Managewellness" scope="page"/>
<jsp:useBean id="vobj" class="com.web.jxp.base.Validate" scope="page"/>
<%
    try 
    {
        if (session.getAttribute("LOGININFO") != null)
        {
            String str = "";
            if (request.getParameter("categoryIdIndex") != null) 
            {
                String categoryIdIndexs = request.getParameter("categoryIdIndex") != null && !request.getParameter("categoryIdIndex").equals("") ? vobj.replaceint(request.getParameter("categoryIdIndex")) : "";
                String assetIdIndexs = request.getParameter("assetIdIndex") != null && !request.getParameter("assetIdIndex").equals("") ? vobj.replaceint(request.getParameter("assetIdIndex")) : "";
                int categoryIdIndex = Integer.parseInt(categoryIdIndexs);
                int assetIdIndex = Integer.parseInt(assetIdIndexs);
                response.setContentType("text/html");
                response.setHeader("Cache-Control", "no-cache");
                Collection coll = managewellness.getSubCategories(assetIdIndex, categoryIdIndex);
                StringBuffer sb = new StringBuffer();
                if (assetIdIndex > 0) 
                {
                    if (coll.size() > 0)
                    {
                        Iterator iter = coll.iterator();
                        while (iter.hasNext())
                        {
                            ManagewellnessInfo info = (ManagewellnessInfo) iter.next();
                            int val = info.getDdlValue();
                            String name = info.getDdlLabel() != null ? info.getDdlLabel() : "";
                            sb.append("<option value='" + val + "'>" + name + "</option>");
                        }
                    }
                } else {
                    sb.append("<option value='-1'>Select Sub Category</option>");
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
    } catch (Exception e) {
        e.printStackTrace();
    }
%>