<%@page import="java.util.zip.ZipOutputStream"%>
<%@page import="java.io.BufferedOutputStream"%>
<%@page import="java.util.zip.ZipEntry"%>
<%@page import="java.io.FileInputStream"%>
<%@page import="java.io.FileNotFoundException"%>
<%@page import="java.io.BufferedInputStream"%>
<%@page import="java.io.File"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html"%>
<%@page language="java" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@page import="com.web.jxp.onboarding.OnboardingInfo" %>
<%@page import="com.web.jxp.user.UserInfo" %>
<jsp:useBean id="onboarding" class="com.web.jxp.onboarding.Onboarding" scope="page"/>
<jsp:useBean id="vobj" class="com.web.jxp.base.Validate" scope="page"/>
<%
        int uid = 0;
        UserInfo uinfo = ((UserInfo) request.getSession().getAttribute("LOGININFO"));
        String username = "";
        if (uinfo != null) 
        {
            uid = uinfo.getUserId();
            username = uinfo.getName();
        }
        String shortlistIds = request.getParameter("shortlistId") != null && !request.getParameter("shortlistId").equals("") ? request.getParameter("shortlistId") : "0";
        String shortlistId_s = onboarding.decipher(shortlistIds);
        int shortlistId = Integer.parseInt(shortlistId_s);
        if(shortlistId > 0)
        {
            java.util.Date now = new java.util.Date();
           String fname = String.valueOf(now.getTime());  
           ArrayList list = onboarding.getformlistByshortlist(shortlistId);
           int size = list.size();        
           if(size > 0)
           {
               String add_onboarding = onboarding.getMainPath("add_onboarding");
               response.setContentType("Content-type: text/zip");
               response.setHeader("Content-Disposition", "attachment; filename="+fname+".zip");
               ServletOutputStream outst = response.getOutputStream();
               ZipOutputStream zos = new ZipOutputStream(new BufferedOutputStream(outst));
               for(int i = 0; i < size; i++)  
               {
                   OnboardingInfo info = (OnboardingInfo) list.get(i);
                   if(info != null && info.getPdffilename() != null && !info.getPdffilename().equals(""))
                   {
                       File file = new File(add_onboarding+info.getPdffilename());
                       if(file.exists()){
                       zos.putNextEntry(new ZipEntry(file.getName()));
                       // Get the file
                       FileInputStream fis = null;
                       try 
                       {
                           fis = new FileInputStream(file);
                       }
                       catch (FileNotFoundException fnfe) 
                       {
                           zos.write(("ERRORld not find file " + file.getName()).getBytes());
                           zos.closeEntry();
                           continue;
                       }
                       BufferedInputStream fif = new BufferedInputStream(fis);
                       // Write the contents of the file
                       int data = 0;
                       while ((data = fif.read()) != -1) {
                               zos.write(data);
                       }
                       fif.close();
                       }
                       zos.closeEntry();   
                   }
               }
               zos.flush();
               zos.close();
               outst.flush();
               outst.close();

               onboarding.updateonboardflagformail(shortlistId, uid, username,5,0,"");            
            }
        }
%>
