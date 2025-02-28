<%@page language="java" contentType="text/html; charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<%@page import="com.web.jxp.clientselection.ClientselectionInfo"%>
<%@page import="com.web.jxp.user.UserInfo"%>
<jsp:useBean id="clientselection" class="com.web.jxp.clientselection.Clientselection" scope="page"/>
<jsp:useBean id="vobj" class="com.web.jxp.base.Validate" scope="page"/>
<%
    request.setCharacterEncoding("UTF-8");
    response.setContentType("text/html; charset=UTF-8");
    try 
    {
        if (session.getAttribute("LOGININFO") != null) 
        {
            int uid = 0;
            UserInfo uinfo = ((UserInfo) request.getSession().getAttribute("LOGININFO"));
            if (uinfo != null) 
            {
                uid = uinfo.getUserId();
            }
            String candidateIds = request.getParameter("candidateId") != null && !request.getParameter("candidateId").equals("") ? vobj.replaceint(request.getParameter("candidateId")) : "-1";
            String resumetempIds = request.getParameter("resumetempId") != null && !request.getParameter("resumetempId").equals("") ? vobj.replaceint(request.getParameter("resumetempId")) : "0";
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
            int candidateId = Integer.parseInt(candidateIds);
            int resumetempId = Integer.parseInt(resumetempIds);
            String pdffilename = clientselection.createResumeTeamplateFile(candidateId, resumetempId, cval1s, cval2s, cval3s,
                    cval4s, cval5s, cval6s, cval7s, cval8s, cval9s, cval10s,  "",  "", "", "", "", "", "", 0, 0, "", 0.0);

            clientselection.updatePdffiletalentpool(candidateId, pdffilename, uid);
            String filepath = clientselection.getMainPath("view_resumetemplate_pdf");

            response.getWriter().write(filepath + pdffilename);
        } else {
            response.getWriter().write("Please check your login session....");
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
%>