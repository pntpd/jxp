<%@page import="java.io.File"%>
<%@page import="java.util.ArrayList"%>
<%@page language="java" contentType="text/html; charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<%@page import="com.web.jxp.onboarding.OnboardingInfo"%>
<%@page import="com.web.jxp.user.UserInfo"%>
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
                String onboardingfileIds = request.getParameter("onboardingfileId") != null && !request.getParameter("onboardingfileId").equals("") ? vobj.replaceint(request.getParameter("onboardingfileId")) : "0"; 
                int onboardingfileId = Integer.parseInt(onboardingfileIds);
                
                OnboardingInfo tinfo  = onboarding.getformalityFileById(onboardingfileId);    
                String  pdffile = "", cval1 = "",  cval2 = "",  cval3 = "", cval4 = "",  cval5 = "",  cval6 = "",  cval7 = "",  cval8 = "",  cval9 = "",  cval10 = "";
                int clientId =0, clientassetId=0, formalityId =0;
                if(tinfo != null)
                {
                    onboardingfileId = tinfo.getOnboardingfileId();
                    clientId = tinfo.getClientId();
                    clientassetId = tinfo.getClientassetId();
                    formalityId = tinfo.getFormalityId();
                    cval1 = tinfo.getVal1() != null ? tinfo.getVal1(): "";
                    cval2 = tinfo.getVal2() != null ? tinfo.getVal2(): "";
                    cval3 = tinfo.getVal3() != null ? tinfo.getVal3(): "";
                    cval4 = tinfo.getVal4() != null ? tinfo.getVal4(): "";
                    cval5 = tinfo.getVal5() != null ? tinfo.getVal5(): "";
                    cval6 = tinfo.getVal6() != null ? tinfo.getVal6(): "";
                    cval7 = tinfo.getVal7() != null ? tinfo.getVal7(): "";
                    cval8 = tinfo.getVal8() != null ? tinfo.getVal8(): "";
                    cval9 = tinfo.getVal9() != null ? tinfo.getVal9(): "";
                    cval10 = tinfo.getVal10() != null ? tinfo.getVal10(): "";
                    pdffile = tinfo.getFilename() != null ? tinfo.getFilename(): "";
                }
                String pdffilename = "";
                  
                String vfile_path = onboarding.getMainPath("view_onboardingtp");       
                if(!pdffilename.equals(""))
                {
                    pdffile = vfile_path+pdffilename; 
                }
                response.getWriter().write(pdffile+ "#@#"+cval1+"#@#"+cval2+"#@#"+cval3+"#@#"+cval4+"#@#"+
                        cval5+"#@#"+cval6+"#@#"+cval7+"#@#"+cval8+"#@#"+cval9+"#@#"+cval10+"#@#"+
                        pdffilename+ "#@#"+onboardingfileId+ "#@#"+clientId+ "#@#"+clientassetId+ "#@#"+formalityId);
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
%>