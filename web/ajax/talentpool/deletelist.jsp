<%@page import="java.io.File"%>
<%@page import="java.util.TreeSet"%>
<%@page import="java.util.SortedSet"%>
<%@page import="java.util.LinkedList"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.Collection"%>
<%@page import="org.json.JSONObject"%>
<%@page import="java.io.BufferedReader"%>
<%@page contentType="text/html"%>
<%@page language="java" import="java.util.ArrayList"%>
<%@page import="com.web.jxp.user.UserInfo" %>
<%@page import="com.web.jxp.onboarding.OnboardingInfo" %>
<jsp:useBean id="talentpool" class="com.web.jxp.talentpool.Talentpool" scope="page"/>
<jsp:useBean id="onboarding" class="com.web.jxp.onboarding.Onboarding" scope="page"/>
<jsp:useBean id="vobj" class="com.web.jxp.base.Validate" scope="page"/>
<%
    try 
    {
        UserInfo uInfo = (UserInfo) request.getSession().getAttribute("LOGININFO");
        if (uInfo != null) 
        {            
            int userId = uInfo.getUserId();
            
            if (userId > 0)
            {
                String onboardingfileIds = request.getParameter("onboardingfileId") != null && !request.getParameter("onboardingfileId").equals("") ? vobj.replaceint(request.getParameter("onboardingfileId")) : "0";
                int onboardingfileId = Integer.parseInt(onboardingfileIds);
                
                String targetfile_path = talentpool.getMainPath("add_onboardingtp");    
                String pdffilename;
                OnboardingInfo pinfo = onboarding.getformalitypdfFile(onboardingfileId);
                if (pinfo != null) 
                {
                    pdffilename = pinfo.getFilename()!= null ? pinfo.getFilename() : "";

                    if(!pdffilename.equals(""))
                    {
                        String deletePath = targetfile_path+pdffilename;
                        File file = new File(deletePath);
                        if(file.exists())
                        file.delete();
                    }
                    onboarding.deletePdfFile(onboardingfileId);                
                }
                response.getWriter().write("Yes");
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
%>