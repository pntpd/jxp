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
                ArrayList coll =  assessmentparameter.getAssessmentParameterArraylist(); 
                StringBuffer sb = new StringBuffer();
                int size = coll.size();
                if(size > 0)
                {
                    for (int i = 0;i <size;i++)
                    {
                        AssessmentParameterInfo info = (AssessmentParameterInfo) coll.get(i);
                        int val =  info.getDdlValue();
                        String name = info.getDdlLabel() != null ? info.getDdlLabel() : "";
                        sb.append("<li>");
                        sb.append("<div class='form-check permission-check'>"); 
                        if(assessmentparameter.checkToStr(ids, ""+val))
                            sb.append("<input class='form-check-input' type='checkbox' name='assparaameter' id='assparaameter' value='"+val+"' checked />");
                        else
                            sb.append("<input class='form-check-input' type='checkbox' name='assparaameter' id='assparaameter' value='"+val+"' />");
                        sb.append("<span class='ml_10'>"+assessmentparameter.changeNum(val, 2)+" | "+name+"</span>");
                        sb.append("</div>");
                        sb.append("</li>");
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