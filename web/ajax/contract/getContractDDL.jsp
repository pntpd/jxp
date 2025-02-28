<%@page import="com.web.jxp.user.UserInfo"%>
<%@page contentType="text/html"%>
<%@page language="java" import="java.util.*"%>
<%@page import="com.web.jxp.contract.ContractInfo" %>
<jsp:useBean id="contract" class="com.web.jxp.contract.Contract" scope="page"/>
<jsp:useBean id="vobj" class="com.web.jxp.base.Validate" scope="page"/>
<%
    try 
    {
        if (session.getAttribute("LOGININFO") != null) 
        { 
            UserInfo uinfo = ((UserInfo) request.getSession().getAttribute("LOGININFO"));
            String str = "";
            if (request.getParameter("assetId") != null) 
            {
                String assetIds = request.getParameter("assetId") != null && !request.getParameter("assetId").equals("") ? vobj.replaceint(request.getParameter("assetId")) : "";
                int assetId = Integer.parseInt(assetIds);
                response.setContentType("text/html");
                response.setHeader("Cache-Control", "no-cache");
                Collection coll = contract.getContractList(assetId);
                StringBuffer sb = new StringBuffer();
                if (assetId > 0) 
                {
                    if (coll.size() > 0) 
                    {
                        Iterator iter = coll.iterator();
                        while (iter.hasNext()) 
                        {
                            ContractInfo info = (ContractInfo) iter.next();
                            int val = info.getDdlValue();
                            String name = info.getDdlLabel() != null ? info.getDdlLabel() : "";
                            sb.append("<option value='" + val + "'>" + name + "</option>");
                        }
                    }
                } else {
                    sb.append("<option value='-1'> Select Main contract </option>");
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