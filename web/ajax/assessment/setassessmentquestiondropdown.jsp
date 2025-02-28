<%@page import="java.util.LinkedList"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.Collection"%>
<%@page import="org.json.JSONObject"%>
<%@page import="java.io.BufferedReader"%>
<%@page contentType="text/html"%>
<%@page language="java" import="java.util.ArrayList"%>
<%@page import="com.web.jxp.user.UserInfo" %>
<%@page import="com.web.jxp.assessment.AssessmentInfo" %>
<%@page import="com.web.jxp.assessmentquestion.AssessmentQuestionInfo" %>
<jsp:useBean id="assessmentquestion" class="com.web.jxp.assessmentquestion.AssessmentQuestion" scope="page"/>
<jsp:useBean id="assessment" class="com.web.jxp.assessment.Assessment" scope="page"/>
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
                String ids = request.getParameter("assessmentparameterid") != null ? vobj.replaceint(request.getParameter("assessmentparameterid")) : "0"; 
                String s = "";
                int id = Integer.parseInt(ids);
                Collection coll = null ;
                if(id>0)
                {
                    coll =  assessment.getAssessmentQuestionsparameterindex(id);
                }
                else if(id <= 0)
                {
                    coll =  assessment.getAssessmentQuestionsIndex();
                }
                StringBuffer sb = new StringBuffer();
                int size = coll.size();
                if(size > 0)
                { 
                  Iterator iter = coll.iterator();
                    while (iter.hasNext())
                    {
                        AssessmentInfo info = (AssessmentInfo) iter.next();
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