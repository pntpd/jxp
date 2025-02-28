<%@page import="java.util.Iterator"%>
<%@page import="java.util.Collection"%>
<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@page import="com.web.jxp.pdept.PdeptInfo" %>
<%@page import="com.web.jxp.pcategory.PcategoryInfo" %>
<jsp:useBean id="pdept" class="com.web.jxp.pdept.Pdept" scope="page"/>
<jsp:useBean id="pcategory" class="com.web.jxp.pcategory.Pcategory" scope="page"/>
<jsp:useBean id="vobj" class="com.web.jxp.base.Validate" scope="page"/>
<%
    try 
    {
        if (session.getAttribute("LOGININFO") != null) 
        {
            if (request.getParameter("assettypeId") != null) 
            {
                String assettypeIds = request.getParameter("assettypeId") != null && !request.getParameter("assettypeId").equals("") ? vobj.replaceint(request.getParameter("assettypeId")) : "-1";
                int assettypeId = Integer.parseInt(assettypeIds);
                response.setContentType("text/html");
                response.setHeader("Cache-Control", "no-cache");                
                StringBuffer sb = new StringBuffer();
                if (assettypeId > 0) 
                {
                    Collection coll = pdept.getPdepts(assettypeId);                    
                    if (coll.size() > 0) 
                    {
                        Iterator iter = coll.iterator();
                        while (iter.hasNext()) 
                        {
                            PdeptInfo info = (PdeptInfo) iter.next();
                            int val = info.getDdlValue();
                            String name = info.getDdlLabel() != null ? info.getDdlLabel() : "";
                            sb.append("<option value='" + val + "'>" + name + "</option>");
                        }
                    }
                    coll.clear();
                } 
                else 
                {
                    sb.append("<option value='-1'>Select Department</option>");
                }
                String str = sb.toString();
                sb.setLength(0);    
                response.getWriter().write(str);
            } 
            else if (request.getParameter("assettypeIdIndex") != null) 
            {
                String assettypeIdIndexs = request.getParameter("assettypeIdIndex") != null && !request.getParameter("assettypeIdIndex").equals("") ? vobj.replaceint(request.getParameter("assettypeIdIndex")) : "-1";
                int assettypeIdIndex = Integer.parseInt(assettypeIdIndexs);
                response.setContentType("text/html");
                response.setHeader("Cache-Control", "no-cache");                
                StringBuffer sb = new StringBuffer();
                if (assettypeIdIndex > 0) 
                {
                    Collection coll = pcategory.getPdepts(assettypeIdIndex);                    
                    if (coll.size() > 0) 
                    {
                        Iterator iter = coll.iterator();
                        while (iter.hasNext()) 
                        {
                            PcategoryInfo info = (PcategoryInfo) iter.next();
                            int val = info.getDdlValue();
                            String name = info.getDdlLabel() != null ? info.getDdlLabel() : "";
                            sb.append("<option value='" + val + "'>" + name + "</option>");
                        }
                    }
                    coll.clear();
                } 
                else 
                {
                    sb.append("<option value='-1'>All</option>");
                }
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
        e.printStackTrace();
    }
%>