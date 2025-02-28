<%@page language="java"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.web.jxp.user.UserInfo"%>
<%@page import="com.web.jxp.cassessment.CassessmentInfo"%>
<%@taglib uri="/WEB-INF/struts-html-el.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/fmt.tld" prefix="fmt"%>
<%@taglib uri="/WEB-INF/c.tld" prefix="c"%>
<jsp:useBean id="cassessment" class="com.web.jxp.cassessment.Cassessment" scope="page"/>
<jsp:useBean id="vobj" class="com.web.jxp.base.Validate" scope="page"/>
<%
    try 
    {
        if (session.getAttribute("LOGININFO") != null) 
        {
            if (request.getParameter("inputdate") != null && request.getParameter("intz") != null && request.getParameter("outtz") != null) 
            {
                StringBuilder str = new StringBuilder();
                String inputdate = request.getParameter("inputdate") != null && !request.getParameter("inputdate").equals("") ? vobj.replacedate(request.getParameter("inputdate")) : "";
                String intz = request.getParameter("intz") != null && !request.getParameter("intz").equals("") ? vobj.replacedesc(request.getParameter("intz")) : "";
                String outtz = request.getParameter("outtz") != null && !request.getParameter("outtz").equals("") ? vobj.replacedesc(request.getParameter("outtz")) : "";
                str.append(cassessment.changedatetz(inputdate, intz, outtz));
                String st = str.toString();
                str.setLength(0);
                response.getWriter().write(st);
            } else {
                response.setStatus(HttpServletResponse.SC_NO_CONTENT);
            }
        } else {
            response.getWriter().write("Please check your login session....");
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
%>