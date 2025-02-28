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
        int userId = 0;
        UserInfo uInfo = (UserInfo) request.getSession().getAttribute("LOGININFO");
        if (uInfo != null) 
        {
            userId = uInfo.getUserId();
            if (userId > 0) 
            {
                String shortlistIds = request.getParameter("shortlistId") != null && !request.getParameter("shortlistId").equals("") ? vobj.replaceint(request.getParameter("shortlistId")) : "0";
                int shortlistId = Integer.parseInt(shortlistIds);
                String file_path = onboarding.getMainPath("view_onboarding");
                ArrayList list = new ArrayList();
                StringBuilder sb = new StringBuilder();
                list = onboarding.getformlistByshortlist(shortlistId);
                int list_size = list.size();
                if (list_size > 0) 
                {
                    OnboardingInfo pinfo = null;
                    String formalityName,  pdffilename;
                    int onboarddocId =0;
                    for (int i = 0; i < list_size; i++) {
                        pinfo = (OnboardingInfo) list.get(i);
                        if (pinfo != null) 
                        {
                            formalityName = pinfo.getFormalityname() != null ? pinfo.getFormalityname() : "";
                            pdffilename = pinfo.getPdffilename() != null ? pinfo.getPdffilename() : "";
                            onboarddocId = pinfo.getOnboarddocId();
                            String pdffile = "";
                            if(!pdffilename.equals("")){
                                pdffile = file_path+pinfo.getPdffilename();
                            }
                            sb.append("<tr>");
                            sb.append("<td width='80%' class='hand_cursor' onclick=\"javascript: showformality('"+pdffile+"');\">"+formalityName+"</td>");
                            sb.append("<td width='20%' class='text-center'>");
                            sb.append("<a href=\"javascript: delfromlist('" + onboarddocId + "');\"  class='close_ic'><span id='dellistid"+(i+1)+"'><i class='ion ion-md-close'></span></i></a>&nbsp;");
                            sb.append("</td>");
                            sb.append("</tr>");
                        }
                    }
                }
                String s1 = sb.toString()+ "#@#"+list_size;
                sb.setLength(0);
                response.getWriter().write(s1);              
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
%>