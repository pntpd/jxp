<%@page import="java.util.LinkedList"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.Collection"%>
<%@page import="org.json.JSONObject"%>
<%@page import="java.io.BufferedReader"%>
<%@page contentType="text/html"%>
<%@page language="java" import="java.util.ArrayList"%>
<%@page import="com.web.jxp.user.UserInfo" %>
<%@page import="com.web.jxp.candidate.CandidateInfo" %>  
<jsp:useBean id="candidate" class="com.web.jxp.candidate.Candidate" scope="page"/>
<jsp:useBean id="vobj" class="com.web.jxp.base.Validate" scope="page"/>
<%
    try 
    {
        String homeEmailId = (String) request.getSession().getAttribute("HOME_EMAILID");
        String s = "";
        if (homeEmailId != null) {     
        
            if (request.getParameter("countryId") != null)
            {
                String countryIds = request.getParameter("countryId") != null && !request.getParameter("countryId").equals("") ? vobj.replaceint(request.getParameter("countryId")) : "0";
                int countryId = Integer.parseInt(countryIds);                
                StringBuffer sb = new StringBuffer();
                if (countryId > 0) 
                {
                    Collection coll = candidate.getCountryStates(countryId);
                    if (coll.size() > 0) 
                    {
                        Iterator iter = coll.iterator();
                        while (iter.hasNext())
                        {
                            CandidateInfo rinfo = (CandidateInfo) iter.next();
                            int val = rinfo.getDdlValue();
                            String n = rinfo.getDdlLabel() != null ? rinfo.getDdlLabel() : "";
                            sb.append("<option value='" + val + "'>" + n + "</option>");
                        }
                    }
                    coll.clear();
                } else {
                    sb.append("<option value='-1'> Select State </option>");
                }
                s = sb.toString();
                sb.setLength(0);
            }
        }
        response.getWriter().write(s);
    } catch (Exception e) {
        e.printStackTrace();
    }
%>