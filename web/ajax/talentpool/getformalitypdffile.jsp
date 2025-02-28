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
                String clientIds = request.getParameter("clientId") != null && !request.getParameter("clientId").equals("") ? vobj.replaceint(request.getParameter("clientId")) : "0";
                String clientassetIds = request.getParameter("clientassetId") != null && !request.getParameter("clientassetId").equals("") ? vobj.replaceint(request.getParameter("clientassetId")) : "0";
                String formalityIds = request.getParameter("formalityId") != null && !request.getParameter("formalityId").equals("") ? vobj.replaceint(request.getParameter("formalityId")) : "0";
                String candidateIds = request.getParameter("candidateId") != null && !request.getParameter("candidateId").equals("") ? vobj.replaceint(request.getParameter("candidateId")) : "0";
                String cval1s = request.getParameter("cval1") != null && !request.getParameter("cval1").equals("") ? vobj.replacedesc(request.getParameter("cval1")) : "";
                String cval2s = request.getParameter("cval2") != null && !request.getParameter("cval2").equals("") ? vobj.replacedesc(request.getParameter("cval2")) : "";
                String cval3s = request.getParameter("cval3") != null && !request.getParameter("cval3").equals("") ? vobj.replacedesc(request.getParameter("cval3")) : "";
                String cval4s = request.getParameter("cval4") != null && !request.getParameter("cval4").equals("") ? vobj.replacedesc(request.getParameter("cval4")) : "";
                String cval5s = request.getParameter("cval5") != null && !request.getParameter("cval5").equals("") ? vobj.replacedesc(request.getParameter("cval5")) : "";
                String cval6s = request.getParameter("cval6") != null && !request.getParameter("cval6").equals("") ? vobj.replacedesc(request.getParameter("cval6")) : "";
                String cval7s = request.getParameter("cval7") != null && !request.getParameter("cval7").equals("") ? vobj.replacedesc(request.getParameter("cval7")) : "";
                String cval8s = request.getParameter("cval8") != null && !request.getParameter("cval8").equals("") ? vobj.replacedesc(request.getParameter("cval8")) : "";
                String cval9s = request.getParameter("cval9") != null && !request.getParameter("cval9").equals("") ? vobj.replacedesc(request.getParameter("cval9")) : "";
                String cval10s = request.getParameter("cval10") != null && !request.getParameter("cval10").equals("") ? vobj.replacedesc(request.getParameter("cval10")) : "";
                
                int formalityId = Integer.parseInt(formalityIds);
                int clientassetId = Integer.parseInt(clientassetIds);
                int clientId = Integer.parseInt(clientIds);
                int candidateId = Integer.parseInt(candidateIds);
                String pdffile = "";
                OnboardingInfo  info = onboarding.getformalityflagByformality(formalityId);
                String targetfile_path = onboarding.getMainPath("add_onboardingtp");
                String sourcefile_path = onboarding.getMainPath("add_formality_file");
                java.util.Date now = new java.util.Date();
                String fname = String.valueOf(now.getTime());  
                String foldername = onboarding.createFolder(targetfile_path);
                int onboardingfileId =0;
                String pdffilename = "";
                if(info.getDdlValue() == 2)
                {
                    String ext = "";
                    if(info.getDdlLabel().lastIndexOf(".") != -1)
                    {
                        ext = info.getDdlLabel().substring(info.getDdlLabel().lastIndexOf("."));
                    }
                    onboarding.copyfile(sourcefile_path + info.getDdlLabel(), targetfile_path+foldername+"/"+fname+ext);
                    pdffilename = foldername+"/"+fname+ext;
                    String vfile_path = onboarding.getMainPath("view_onboardingtp");
                    if(!pdffilename.equals(""))
                    {
                        pdffile = vfile_path+pdffilename;
                    }
                    OnboardingInfo tinfo = onboarding.getformalityDetail(candidateId, pdffilename, userId, formalityId, clientId, clientassetId, cval1s,
                        cval2s,  cval3s,  cval4s,  cval5s,  cval6s,  cval7s, cval8s,  cval9s,  cval10s );
                    tinfo = onboarding.getformalityFile(candidateId, formalityId);
                    if(tinfo != null)
                    {
                        onboardingfileId = tinfo.getOnboardingfileId();
                    } 
                }
                else
                {
                    pdffilename = onboarding.createOnboardingFormFile(0,formalityId, clientId,clientassetId,
                             candidateId,cval1s,cval2s,cval3s, cval4s, cval5s, cval6s, cval7s, cval8s, cval9s, cval10s);
                    
                    OnboardingInfo tinfo = onboarding.getformalityDetail(candidateId, pdffilename, userId, formalityId, clientId, clientassetId, cval1s,
                        cval2s,  cval3s,  cval4s,  cval5s,  cval6s,  cval7s, cval8s,  cval9s,  cval10s);
                    tinfo = onboarding.getformalityFile(candidateId, formalityId);     
                    if(tinfo != null)
                    {
                        onboardingfileId = tinfo.getOnboardingfileId();
                    }  
                    String vfile_path = onboarding.getMainPath("view_onboardingtp");       
                    if(!pdffilename.equals(""))
                    {
                        pdffile = vfile_path+pdffilename; 
                    }
                }   
                response.getWriter().write(pdffile+ "#@#"+cval1s+"#@#"+cval2s+"#@#"+cval3s+"#@#"+cval4s+"#@#"+
                        cval5s+"#@#"+cval6s+"#@#"+cval7s+"#@#"+cval8s+"#@#"+cval9s+"#@#"+cval10s+"#@#"+
                        pdffilename+ "#@#"+onboardingfileId+ "#@#"+clientId+ "#@#"+clientassetId+ "#@#"+formalityId);
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
%>