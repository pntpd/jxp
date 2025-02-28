<%@page import="java.sql.Array"%>
<%@page import="com.web.jxp.talentpool.TalentpoolInfo" %>
<%
    
            TalentpoolInfo titleInfo = null;
            if(session.getAttribute("CANDIDATE_DETAIL") != null)
            {
                titleInfo = (TalentpoolInfo)session.getAttribute("CANDIDATE_DETAIL");   
            }
            String candidatename = "", position = "";            
            candidatename = titleInfo.getName(); 
            position = titleInfo.getPosition();
%>

<span><%= candidatename + " | " + position %> </span>
