<%@page contentType="text/html"%>
<%@page language="java" import="java.util.*"%>
<%@page import="com.web.jxp.documentissuedby.DocumentissuedbyInfo" %>
<jsp:useBean id="documentissuedby" class="com.web.jxp.documentissuedby.Documentissuedby" scope="page"/>
<jsp:useBean id="vobj" class="com.web.jxp.base.Validate" scope="page"/>
<%
    try
    {
        if(session.getAttribute("LOGININFO") != null)
        {
            String str = "";        
            if(request.getParameter("documentTypeId") != null && request.getParameter("documentTypeId") != null)
            {
                String documentTypeIds = request.getParameter("documentTypeId") != null && !request.getParameter("documentTypeId").equals("") ? vobj.replaceint(request.getParameter("documentTypeId")) : "";
                int documentTypeId = Integer.parseInt(documentTypeIds);
                response.setContentType("text/html");
                response.setHeader("Cache-Control", "no-cache");   
                Collection coll = new LinkedList();
                if( documentTypeId>0)
                {
                    coll = documentissuedby.getDocumentissuedbys(documentTypeId);                     
                }
                else
                {
                    coll.add(new DocumentissuedbyInfo(-1, "Select Documentissusedby"));
                }
                StringBuffer sb = new StringBuffer();
                if(coll.size() > 0)
                {
                    Iterator iter = coll.iterator();
                    while (iter.hasNext())
                    {
                        DocumentissuedbyInfo info = (DocumentissuedbyInfo) iter.next();
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