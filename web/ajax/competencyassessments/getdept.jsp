<%@page import="java.util.Iterator"%>
<%@page import="java.util.Collection"%>
<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@page import="com.web.jxp.competencyassessments.CompetencyassessmentsInfo" %>
<jsp:useBean id="competencyassessments" class="com.web.jxp.competencyassessments.Competencyassessments" scope="page"/>
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
                String type_s = request.getParameter("type") != null && !request.getParameter("type").equals("") ? vobj.replaceint(request.getParameter("type")) : "0";
                int type = Integer.parseInt(type_s);
                response.setContentType("text/html");
                response.setHeader("Cache-Control", "no-cache");                
                StringBuffer sb = new StringBuffer();
                if (assettypeId > 0) 
                {
                    Collection coll = competencyassessments.getCompetencyDept(assettypeId, type);                    
                    if (coll.size() > 0) 
                    {
                        Iterator iter = coll.iterator();
                        while (iter.hasNext()) 
                        {
                            CompetencyassessmentsInfo info = (CompetencyassessmentsInfo) iter.next();
                            int val = info.getDdlValue();
                            String name = info.getDdlLabel() != null ? info.getDdlLabel() : "";
                            sb.append("<option value='" + val + "'>" + name + "</option>");
                        }
                    }
                    coll.clear();
                } 
                else 
                {
                    if(type == 1){
                        sb.append("<option value='-1'>All</option>");
                    }
                    if(type == 2){
                        sb.append("<option value='-1'>Select Department</option>");
                    }
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

    }
%>