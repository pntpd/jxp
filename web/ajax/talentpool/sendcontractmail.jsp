<%@page contentType="text/html"%>
<%@page language="java" import="java.util.ArrayList"%>
<%@page pageEncoding="UTF-8"%>
<%@page import="com.web.jxp.talentpool.TalentpoolInfo"%>
<%@page import="com.web.jxp.user.UserInfo"%>
<jsp:useBean id="mail" class="com.web.jxp.talentpool.Mail" scope="page"/>
<jsp:useBean id="ddl" class="com.web.jxp.talentpool.Ddl" scope="page"/>
<jsp:useBean id="vobj" class="com.web.jxp.base.Validate" scope="page"/>
<%
    try 
    {
        if (session.getAttribute("LOGININFO") != null) 
        {
            int uid = 0;
            String username = "";
            UserInfo uinfo = ((UserInfo) request.getSession().getAttribute("LOGININFO"));
            if (uinfo != null) 
            {
                uid = uinfo.getUserId();
                username = uinfo.getName()!= null ? uinfo.getName(): "";
            }
            int positionId = 0;
            if(session.getAttribute("CANDIDATE_DETAIL") != null)
            {
                TalentpoolInfo pinfo = (TalentpoolInfo) session.getAttribute("CANDIDATE_DETAIL");
                if(pinfo != null)
                {
                    positionId = pinfo.getPositionId();
                }
            }
            String fromval = request.getParameter("fromval") != null && !request.getParameter("fromval").equals("") ? vobj.replacedesc(request.getParameter("fromval")) : "";
            String toval = request.getParameter("toval") != null && !request.getParameter("toval").equals("") ? vobj.replacedesc(request.getParameter("toval")) : "";
            String ccval = request.getParameter("ccval") != null && !request.getParameter("ccval").equals("") ? vobj.replacedesc(request.getParameter("ccval")) : "";
            String bccval = request.getParameter("bccval") != null && !request.getParameter("bccval").equals("") ? vobj.replacedesc(request.getParameter("bccval")) : "";
            String subject = request.getParameter("subject") != null && !request.getParameter("subject").equals("") ? vobj.replacedesc(request.getParameter("subject")) : "";
            String description = request.getParameter("description") != null && !request.getParameter("description").equals("") ? vobj.replacedesc(request.getParameter("description")) : "";
            String candidatename = request.getParameter("candidatename") != null && !request.getParameter("candidatename").equals("") ? vobj.replacedesc(request.getParameter("candidatename")) : "";
            String filename = request.getParameter("filename") != null && !request.getParameter("filename").equals("") ? vobj.replacedesc(request.getParameter("filename")) : "";
            String clientname = request.getParameter("clientname") != null && !request.getParameter("clientname").equals("") ? vobj.replacedesc(request.getParameter("clientname")) : "";
            String candidateIds = request.getParameter("candidateId") != null && !request.getParameter("candidateId").equals("") ? vobj.replaceint(request.getParameter("candidateId")) : "0";
            String clientassetIds = request.getParameter("clientassetId") != null && !request.getParameter("clientassetId").equals("") ? vobj.replaceint(request.getParameter("clientassetId")) : "0";
            String contractIds = request.getParameter("contractId") != null && !request.getParameter("contractId").equals("") ? vobj.replaceint(request.getParameter("contractId")) : "0";
            String fromdate = request.getParameter("fromdate") != null && !request.getParameter("fromdate").equals("") ? vobj.replacedate(request.getParameter("fromdate")) : "";
            String todate = request.getParameter("todate") != null && !request.getParameter("todate").equals("") ? vobj.replacedate(request.getParameter("todate")) : "";
            String contractdetailIds = request.getParameter("contractdetailId") != null && !request.getParameter("contractdetailId").equals("") ? vobj.replaceint(request.getParameter("contractdetailId")) : "0";
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
            int contractdetailId = Integer.parseInt(contractdetailIds);
            int contractId = Integer.parseInt(contractIds);
            int clientassetId = Integer.parseInt(clientassetIds);   
            
            int candidateId = Integer.parseInt(candidateIds);

            if(contractdetailId <= 0)
            {        
                ddl.createContractPdffile(candidateId, filename, uid, contractId, fromdate, todate, clientassetId, positionId, username, cval1s, cval2s, cval3s, cval4s, cval5s, cval6s, cval7s, cval8s, cval9s, cval10s);
            }            
            //int maillogId = mail.sendContractMail(fromval, toval, ccval, bccval, subject, description, candidatename, filename, clientname, candidateId, username);
            
        } else {
            response.getWriter().write("Please check your login session....");
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
%>