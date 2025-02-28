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
                String onboardingfileIds = request.getParameter("onboardingfileId") != null && !request.getParameter("onboardingfileId").equals("") ? vobj.replacedesc(request.getParameter("onboardingfileId")) : "0"; 
                int onboardingfileId = Integer.parseInt(onboardingfileIds);
                String candidateIds = request.getParameter("candidateId") != null && !request.getParameter("candidateId").equals("") ? vobj.replacedesc(request.getParameter("candidateId")) : "0"; 
                int candidateId = Integer.parseInt(candidateIds);
                String file_path = onboarding.getMainPath("view_onboardingtp");
                StringBuilder sb = new StringBuilder();
                
                ArrayList list = onboarding.getPdfIdList(candidateId);
                int size = list.size();
                if (size > 0) 
                {
                    OnboardingInfo pinfo = null;
                    String formalityName,  pdffilename;
                    int type = 0;
                    for (int i = 0; i < size; i++) 
                    {
                        pinfo = (OnboardingInfo) list.get(i);
                        if (pinfo != null) 
                        {
                            formalityName = pinfo.getName() != null ? pinfo.getName() : "";
                            pdffilename = pinfo.getFilename()!= null ? pinfo.getFilename() : "";
                            onboardingfileId = pinfo.getOnboardingfileId();
                            type = pinfo.getType();
                            String pdffile = "";
                            if(!pdffilename.equals(""))
                            {
                                pdffile = file_path+pdffilename;
                            }
                            sb.append("<tr>");
                            sb.append("<td width='80%' class='hand_cursor' onclick=\"javascript: showformality('"+pdffile+"');\">"+formalityName+"</td>");
                            sb.append("<td width='20%' class='text-center'>");
                            if(type == 1)
                                sb.append("<a href=\"javascript: getCustomValue('" + onboardingfileId + "');\"class='edit_mode float-end mr_15'><img src='../assets/images/pencil.png'/></a>&nbsp;");
                            sb.append("</td>");  
                            sb.append("<td width='20%' class='text-center'>");
                            sb.append("<a href=\"javascript: delfromlist('" + onboardingfileId + "');\"  class='close_ic'><span id='dellistid"+(i+1)+"'><i class='ion ion-md-close'></span></i></a>&nbsp;");
                            sb.append("</td>");
                            sb.append("</tr>");
                        }
                    }
                }
                session.setAttribute("TLIST", list);
                String s1 = sb.toString()+ "#@#"+size+ "#@#"+onboardingfileId;
                sb.setLength(0);
                response.getWriter().write(s1);              
            }
            }
    } catch (Exception e) {
        e.printStackTrace();
    }
%>