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
                String shortlistIds = request.getParameter("shortlistId") != null && !request.getParameter("shortlistId").equals("") ? vobj.replaceint(request.getParameter("shortlistId")) : "0";
                String onboarddocIds = request.getParameter("onboarddocId") != null && !request.getParameter("onboarddocId").equals("") ? vobj.replaceint(request.getParameter("onboarddocId")) : "0";
                int shortlistId = Integer.parseInt(shortlistIds);
                int onboarddocId = Integer.parseInt(onboarddocIds);
                String targetfile_path = onboarding.getMainPath("add_onboarding");
                String uploadeddoc = onboarding.getFormalitygeneratedFormfile(onboarddocId);
                if((uploadeddoc != null && !uploadeddoc.equals("")))
                {
                    String deletePath = targetfile_path+uploadeddoc;
                    File file = new File(deletePath);
                    if(file.exists())
                        file.delete();
                }
                int deletestatus = onboarding.deleteformalitypdf(shortlistId,onboarddocId,userId);
              
                response.getWriter().write(deletestatus);
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
%>