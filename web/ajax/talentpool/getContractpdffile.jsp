<%@page import="java.io.File"%>
<%@page language="java" contentType="text/html; charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<%@page import="com.web.jxp.talentpool.TalentpoolInfo"%>
<%@page import="com.web.jxp.user.UserInfo"%>
<jsp:useBean id="ddl" class="com.web.jxp.talentpool.Ddl" scope="page"/>
<jsp:useBean id="vobj" class="com.web.jxp.base.Validate" scope="page"/>
<%
    request.setCharacterEncoding("UTF-8");
    response.setContentType("text/html; charset=UTF-8");
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
            int positionId = 0, clientId=0;
            String asset = "";
            if(session.getAttribute("CANDIDATE_DETAIL") != null)
            {
                TalentpoolInfo pinfo = (TalentpoolInfo) session.getAttribute("CANDIDATE_DETAIL");
                if(pinfo != null)
                {
                    positionId = pinfo.getPositionId();
                    clientId = pinfo.getClientId();
                    asset = pinfo.getAssetName() != null ? pinfo.getAssetName(): "";
                }
            }
            String candidateIds = request.getParameter("candidateId") != null && !request.getParameter("candidateId").equals("") ? vobj.replaceint(request.getParameter("candidateId")) : "-1";
            String contractIds = request.getParameter("contractId") != null && !request.getParameter("contractId").equals("") ? vobj.replaceint(request.getParameter("contractId")) : "0";
            String fromdate = request.getParameter("fromdate") != null && !request.getParameter("fromdate").equals("") ? vobj.replacedate(request.getParameter("fromdate")) : "";
            String todate = request.getParameter("todate") != null && !request.getParameter("todate").equals("") ? vobj.replacedate(request.getParameter("todate")) : "";
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
            String contractdetailIds = request.getParameter("contractdetailId") != null && !request.getParameter("contractdetailId").equals("") ? vobj.replacedesc(request.getParameter("contractdetailId")) : "";
            String clientassetIds = request.getParameter("clientassetId") != null && !request.getParameter("clientassetId").equals("") ? vobj.replaceint(request.getParameter("clientassetId")) : "0";
            String types = request.getParameter("type") != null && !request.getParameter("type").equals("") ? vobj.replaceint(request.getParameter("type")) : "0";
            String filename = request.getParameter("filename") != null && !request.getParameter("filename").equals("") ? vobj.replacedesc(request.getParameter("filename")) : "";
            int candidateId = Integer.parseInt(candidateIds);
            int contractId = Integer.parseInt(contractIds);
            int type = Integer.parseInt(types);
            int contractdetailId = Integer.parseInt(contractdetailIds);
            int clientassetId = Integer.parseInt(clientassetIds);
            String filepath = ddl.getMainPath("view_candidate_file");
            String path = ddl.getMainPath("add_candidate_file");
            if(filename != null && !filename.equals(""))
            {
                String deletePath = path + filename;
                File file = new File(deletePath);
                if (file.exists()) 
                {
                    file.delete();
                }
            }
            int ck = ddl.checkContractDuplicacy(contractdetailId, contractId, fromdate, todate);            
            if (ck == 1)
            {
                response.getWriter().write("DUP");
            }else
            {
                String pdffilename = ddl.createContractFile(candidateId, contractId, cval1s, cval2s, cval3s,
                        cval4s, cval5s, cval6s, cval7s, cval8s, cval9s, cval10s, fromdate, todate, "", "", "", "", "", clientId, 0, asset, 0.0);
                if(contractdetailId > 0)
                {
                   ddl.updateContractPdffile(candidateId, pdffilename, uid, contractId, fromdate, todate, clientassetId, positionId, username, contractdetailId, cval1s, cval2s, cval3s,
                        cval4s, cval5s, cval6s, cval7s, cval8s, cval9s, cval10s);
                }
                response.getWriter().write(filepath + pdffilename+ "#@#"+pdffilename+ "#@#"+ck);
            }
        } else {
            response.getWriter().write("Please check your login session....");
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
%>