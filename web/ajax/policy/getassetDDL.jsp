<%@page import="java.util.Iterator"%>
<%@page import="java.util.Collection"%>
<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@page import="com.web.jxp.user.UserInfo" %>
<%@page import="com.web.jxp.policy.PolicyInfo" %>
<jsp:useBean id="policy" class="com.web.jxp.policy.Policy" scope="page"/>
<jsp:useBean id="vobj" class="com.web.jxp.base.Validate" scope="page"/>
<%
    try
    {
        if(session.getAttribute("LOGININFO") != null)
        {
            int allclient = 0;
            UserInfo uinfo = ((UserInfo) request.getSession().getAttribute("LOGININFO"));
            String str = "", per = "", assetids = "";
            if (uinfo != null) 
            {
                per = uinfo.getPermission() != null ? uinfo.getPermission() : "N";
                allclient = uinfo.getAllclient();
                assetids = uinfo.getAssetids();
            }      
            if(request.getParameter("clientId") != null)
            {
                String assetIds = request.getParameter("clientId") != null && !request.getParameter("clientId").equals("") ? vobj.replaceint(request.getParameter("clientId")) : "0";
                int clientId = Integer.parseInt(assetIds);
                response.setContentType("text/html");
                response.setHeader("Cache-Control", "no-cache");            
                StringBuffer sb = new StringBuffer();
                Collection coll = policy.getClientAssetForAddEdit(clientId, assetids, allclient, per); 
                if(clientId> 0)
                {
                    if(coll.size() > 0)
                    {
                        Iterator iter = coll.iterator();
                        while (iter.hasNext())
                        {
                            PolicyInfo info = (PolicyInfo) iter.next();
                            int val =  info.getDdlValue();
                            String name = info.getDdlLabel()!= null ? info.getDdlLabel(): "";
                            sb.append("<option value='"+val+"'>"+name+"</option>");
                        }
                    }      
                }
                 else{
                        sb.append("<option value='-1'>Select Asset</option>");
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