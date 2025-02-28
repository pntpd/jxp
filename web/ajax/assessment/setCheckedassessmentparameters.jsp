<%@page import="java.util.LinkedList"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.Collection"%>
<%@page import="org.json.JSONObject"%>
<%@page import="java.io.BufferedReader"%>
<%@page contentType="text/html"%>
<%@page language="java" import="java.util.ArrayList"%>
<%@page import="com.web.jxp.user.UserInfo" %>
<%@page import="com.web.jxp.assessment.AssessmentInfo" %>
<%@page import="com.web.jxp.assessmentparameter.AssessmentParameterInfo" %>
<jsp:useBean id="assessmentparameter" class="com.web.jxp.assessmentparameter.AssessmentParameter" scope="page"/>
<jsp:useBean id="vobj" class="com.web.jxp.base.Validate" scope="page"/>
<%
    try
    {
        UserInfo uInfo = (UserInfo) request.getSession().getAttribute("LOGININFO");
        if(uInfo != null)
        {
            int userId = uInfo.getUserId();
            if(userId > 0)
            {
                String ids = request.getParameter("ids") != null ? vobj.replacealphacomma(request.getParameter("ids")) : "";
                String s = "";
                Collection coll =  assessmentparameter.getAssessmentParametersChecked(ids);
                StringBuffer sb = new StringBuffer();
                int size = coll.size();
                if(size > 0)
                {
                  Iterator iter = coll.iterator();
                    while (iter.hasNext())
                    {
                        AssessmentParameterInfo info = (AssessmentParameterInfo) iter.next();
                        int val =  info.getDdlValue();
                        String name = info.getDdlLabel() != null ? info.getDdlLabel() : "";
                        
                        sb.append("<option value='"+val+"'>"+name+"</option>");
                    }
                }
                s = sb.toString();
                sb.setLength(0);
                coll.clear();
                response.getWriter().write(s);
            }
        }
    }
    catch(Exception e)
    {
        e.printStackTrace();
    }
%>