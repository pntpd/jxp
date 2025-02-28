package com.web.jxp.mobilization;

import com.web.jxp.base.Validate;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import static com.web.jxp.common.Common.*;
import com.web.jxp.user.UserInfo;
import java.util.Collection;
import java.util.Date;
import java.util.Stack;
import org.apache.struts.upload.FormFile;

public class MobilizationAction extends Action {

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        MobilizationForm frm = (MobilizationForm) form;
        Mobilization mobilization = new Mobilization();
        Validate vobj = new Validate();
        int count = mobilization.getCount();
        String search = frm.getSearch() != null ? frm.getSearch() : "";
        frm.setSearch(search);
        int statusIndex = frm.getStatusIndex();
        frm.setStatusIndex(statusIndex);
        int countryId = frm.getCountryId();
        frm.setCountryId(countryId);
        int uId = 0, allclient = 0;
        String permission = "N", cids = "", assetids = "", username = "";
        if (request.getSession().getAttribute("LOGININFO") != null) 
        {
            UserInfo uInfo = (UserInfo) request.getSession().getAttribute("LOGININFO");
            if (uInfo != null) 
            {
                permission = uInfo.getPermission() != null ? uInfo.getPermission(): "";
                uId = uInfo.getUserId();
                cids = uInfo.getCids() != null ? uInfo.getCids(): "";
                allclient = uInfo.getAllclient();
                assetids = uInfo.getAssetids() != null ? uInfo.getAssetids(): "";
                username = uInfo.getName() != null ? uInfo.getUserName(): "";
            }
        }
        int check_user = mobilization.checkUserSession(request, 59, permission);
        if (check_user == -1) {
            return mapping.findForward("default");
        } else if (check_user == -2) {
            String authmess = mobilization.getMainPath("user_auth");
            authmess = authmess.replaceAll("__MODULE__", "Mobilization Module");
            request.setAttribute("AUTHMESSAGE", authmess);
            return mapping.findForward("auth");
        }

        Collection clients = mobilization.getClients(cids, allclient, permission);
        frm.setClients(clients);
        int clientIdIndex = frm.getClientIdIndex();
        frm.setClientIdIndex(clientIdIndex);

        Collection assets = mobilization.getClientAsset(clientIdIndex, assetids, allclient, permission);
        frm.setAssets(assets);
        int assetIdIndex = frm.getAssetIdIndex();
        frm.setAssetIdIndex(assetIdIndex);

        Collection locations = mobilization.getCountryList();
        frm.setLocations(locations);

        if (frm.getDoView() != null && frm.getDoView().equals("yes")) {
            frm.setDoView("no");
            print(this, " getDoView block :: ");
            int clientId = frm.getClientId();
            frm.setClientId(clientId);
            int assetId = frm.getClientassetId();
            frm.setClientassetId(assetId);

            MobilizationInfo info = mobilization.getMobiClientDetails(clientId, assetId);
            Collection positions = mobilization.getPostions(assetId);
            frm.setPositions(positions);
            ArrayList list = mobilization.getCandidatesByclientandAssetName("", clientId, assetId, 0);
            request.getSession().setAttribute("MOBILIZATIONINFO", info);
            request.getSession().setAttribute("CRCANDIDATESLIST", list);

            return mapping.findForward("details_mobilization");
        } else if (frm.getDoViewCancel() != null && frm.getDoViewCancel().equals("yes")) {
            frm.setDoViewCancel("no");
            print(this, " getDoViewCancel block :: ");
            int clientId = frm.getClientId();
            frm.setClientId(clientId);
            String txtsearch = frm.getTxtsearch();
            frm.setTxtsearch(txtsearch);
            int assetId = frm.getClientassetId();
            frm.setClientassetId(assetId);

            MobilizationInfo info = mobilization.getMobiClientDetails(clientId, assetId);
            Collection positions = mobilization.getPostions(assetId);
            frm.setPositions(positions);
            int positionId = frm.getPositionId();
            frm.setPositionId(positionId);
            ArrayList list = mobilization.getCandidatesByclientandAssetName(txtsearch, clientId, assetId, 0);
            request.getSession().setAttribute("MOBILIZATIONINFO", info);
            request.getSession().setAttribute("CRCANDIDATESLIST", list);

            return mapping.findForward("details_mobilization");
        } else if (frm.getDoMobTravel() != null && frm.getDoMobTravel().equals("yes")) {
            frm.setDoMobTravel("no");
            print(this, " getDoMobTravel block :: ");
            int clientId = frm.getClientId();
            frm.setClientId(clientId);
            int assetId = frm.getClientassetId();
            frm.setClientassetId(assetId);
            String txtsearch = frm.getTxtsearch();
            frm.setTxtsearch(txtsearch);
            int crewrotationId = frm.getCrewrotationId();
            frm.setCrewrotationId(crewrotationId);
            int positionId = frm.getPositionId();
            frm.setPositionId(positionId);
            int type = frm.getType();
            frm.setType(type);

            MobilizationInfo info = mobilization.getCandDtlsByCrewId(crewrotationId);
            ArrayList list = mobilization.getMobDataByCrewId(crewrotationId, type);
            request.getSession().setAttribute("CANDIDATEINFO", info);
            request.getSession().setAttribute("MOBILIZATIONLIST", list);

            return mapping.findForward("dtls_mobtravel");
        } else if (frm.getDoMobAccomm() != null && frm.getDoMobAccomm().equals("yes")) {
            frm.setDoMobAccomm("no");
            print(this, " getDoMobAccomm block :: ");
            int clientId = frm.getClientId();
            frm.setClientId(clientId);
            int assetId = frm.getClientassetId();
            frm.setClientassetId(assetId);
            String txtsearch = frm.getTxtsearch();
            frm.setTxtsearch(txtsearch);
            int crewrotationId = frm.getCrewrotationId();
            frm.setCrewrotationId(crewrotationId);
            int positionId = frm.getPositionId();
            frm.setPositionId(positionId);
            int type = frm.getType();
            frm.setType(type);

            MobilizationInfo info = mobilization.getCandDtlsByCrewId(crewrotationId);
            ArrayList list = mobilization.getMobDataByCrewId(crewrotationId, type);
            request.getSession().setAttribute("CANDIDATEINFO", info);
            request.getSession().setAttribute("MOBILIZATIONLIST", list);

            return mapping.findForward("dtls_mobaccomm");
        } else if (frm.getDoSaveMob() != null && frm.getDoSaveMob().equals("yes")) {
            frm.setDoSaveMob("no");
            print(this, " getDoSaveMob block :: ");
            int clientId = frm.getClientId();
            frm.setClientId(clientId);
            int assetId = frm.getClientassetId();
            frm.setClientassetId(assetId);
            String txtsearch = frm.getTxtsearch();
            frm.setTxtsearch(txtsearch);
            int positionId = frm.getPositionId();
            frm.setPositionId(positionId);
            int crewrotationId = frm.getCrewrotationId();
            frm.setCrewrotationId(crewrotationId);
            int type = frm.getType();
            frm.setType(type);

            int mobId = frm.getMobilizationId();
            String val1 = frm.getVal1();
            String val2 = frm.getVal2();
            String val3 = frm.getVal3();
            String val4 = frm.getVal4();
            String val5 = frm.getVal5();
            String val6 = frm.getVal6();
            String val7 = frm.getVal7();
            String val8 = frm.getVal8();
            String vald9 = frm.getVald9();
            String valt9 = frm.getValt9();
            String vald10 = frm.getVald10();
            String valt10 = frm.getValt10();
            FormFile upload1 = frm.getUpload1();
            String filename = frm.getHdnfilename();

            String filePath = mobilization.getMainPath("add_mobilization");

            String foldername = mobilization.createFolder(filePath);
            Date now = new Date();
            String fn = String.valueOf(now.getTime());
            String updatefile = "";
            if (upload1 != null && upload1.getFileSize() > 0) {
                updatefile = mobilization.uploadFile(mobId, filename, upload1, fn + "_1", filePath, foldername);
            }

            mobilization.updateMobilizationDtls(mobId, type, val1, val2, val3, val4, val5, val6, val7, val8, vald9, valt9, vald10, valt10, updatefile, uId);

            MobilizationInfo info = mobilization.getCandDtlsByCrewId(crewrotationId);
            ArrayList list = mobilization.getMobDataByCrewId(crewrotationId, type);
            request.getSession().setAttribute("CANDIDATEINFO", info);
            request.getSession().setAttribute("MOBILIZATIONLIST", list);
            if (type == 1) {
                return mapping.findForward("dtls_mobtravel");
            } else {
                return mapping.findForward("dtls_mobaccomm");
            }
        } else if (frm.getDoSaveTravel() != null && frm.getDoSaveTravel().equals("yes")) {
            frm.setDoSaveTravel("no");
            print(this, " getDoSaveTravel block :: ");

            int clientId = frm.getClientId();
            frm.setClientId(clientId);
            int assetId = frm.getClientassetId();
            frm.setClientassetId(assetId);
            int crewId = frm.getCrewrotationId();
            frm.setCrewrotationId(crewId);
            String txtsearch = frm.getTxtsearch();
            frm.setTxtsearch(txtsearch);
            mobilization.updateCrewFlag(crewId, 3, uId);

            MobilizationInfo info = mobilization.getMobiClientDetails(clientId, assetId);
            Collection positions = mobilization.getPostions(assetId);
            frm.setPositions(positions);
            int positionId = frm.getPositionId();
            frm.setPositionId(positionId);
            ArrayList list = mobilization.getCandidatesByclientandAssetName(txtsearch, clientId, assetId, 0);
            request.getSession().setAttribute("MOBILIZATIONINFO", info);
            request.getSession().setAttribute("CRCANDIDATESLIST", list);
            request.setAttribute("MAILMODAL", "yes");
            request.setAttribute("MODALTYPE", "1");
            return mapping.findForward("details_mobilization");
        } else if (frm.getDoSaveAccomm() != null && frm.getDoSaveAccomm().equals("yes")) {
            frm.setDoSaveAccomm("no");
            print(this, " getDoView block :: ");

            int clientId = frm.getClientId();
            frm.setClientId(clientId);
            int assetId = frm.getClientassetId();
            frm.setClientassetId(assetId);
            int crewId = frm.getCrewrotationId();
            frm.setCrewrotationId(crewId);
            String txtsearch = frm.getTxtsearch();
            frm.setTxtsearch(txtsearch);
            mobilization.updateCrewFlag(crewId, 4, uId);

            MobilizationInfo info = mobilization.getMobiClientDetails(clientId, assetId);
            Collection positions = mobilization.getPostions(assetId);
            frm.setPositions(positions);
            int positionId = frm.getPositionId();
            frm.setPositionId(positionId);
            ArrayList list = mobilization.getCandidatesByclientandAssetName(txtsearch, clientId, assetId, 0);
            request.getSession().setAttribute("MOBILIZATIONINFO", info);
            request.getSession().setAttribute("CRCANDIDATESLIST", list);
            request.setAttribute("MAILMODAL", "yes");
            request.setAttribute("MODALTYPE", "2");
            return mapping.findForward("details_mobilization");
        } else if (frm.getDoMobMail() != null && frm.getDoMobMail().equals("yes")) {
            frm.setDoMobMail("no");
            print(this, " getDoMobMail block :: ");
            int clientId = frm.getClientId();
            frm.setClientId(clientId);
            int assetId = frm.getClientassetId();
            frm.setClientassetId(assetId);
            String txtsearch = frm.getTxtsearch();
            frm.setTxtsearch(txtsearch);
            int crewId = frm.getCrewrotationId();

            String from = frm.getMailfrom();
            String to = frm.getMailto();
            String cc = frm.getMailcc();
            String bcc = frm.getMailbcc();
            String subject = frm.getMailsubject();
            String body = frm.getMaildescription();
            int travelnotreq = frm.getChktravelnotreq();
            int accommnotereq = frm.getChkaccommnotreq();
            ArrayList doclist = new ArrayList();
            if (request.getSession().getAttribute("MAILDOCLIST") != null) {
                doclist = (ArrayList) request.getSession().getAttribute("MAILDOCLIST");
            }
            if (travelnotreq > 0 && accommnotereq <= 0) {
                doclist = mobilization.getListFromList(doclist, 2);
            } else if (accommnotereq > 0 && travelnotreq <= 0) {
                doclist = mobilization.getListFromList(doclist, 1);
            } else if (accommnotereq > 0 && travelnotreq > 0) {
                doclist.removeAll(doclist);
            }
            int doclist_size = doclist.size();
            String filePath = mobilization.getMainPath("add_mobilization");
            String vfilePath = mobilization.getMainPath("view_mobilization");
            String filenames[] = new String[doclist_size];
            String vfilenames[] = new String[doclist_size];
            String candidatename[] = new String[doclist_size];
            if (doclist_size > 0) {
                MobilizationInfo info = null;
                for (int i = 0; i < doclist_size; i++) {
                    info = (MobilizationInfo) doclist.get(i);
                    if (info != null) 
                    {
                        if (!info.getFilename().equals("")) 
                        {
                            filenames[i] = filePath + info.getFilename();
                            vfilenames[i] = vfilePath + info.getFilename();
                            if (info.getType() == 1) 
                            {
                                candidatename[i] = info.getVal5() + ".pdf";
                            } else if (info.getType() == 2) 
                            {
                                candidatename[i] = info.getVal1() + ".pdf";
                            }
                        }
                    }
                }
            }

            String vfilename = "";
            if (vfilenames.length > 0) {
                vfilename = vobj.replacealphacomma(makeCommaDelimString(vfilenames));
            }
            MobilizationInfo minfo = mobilization.getMobiClientDetails(clientId, assetId);
            int val = mobilization.sendMobilizationMail(from, to, cc, bcc, subject, body, candidatename, filenames, minfo.getClientName(), vfilename, username);
            request.getSession().removeAttribute("MAILDOCLIST");
            if (val == 1) {
                mobilization.updateCrewFlagAfterMail(crewId, uId);
            }
            Collection positions = mobilization.getPostions(assetId);
            frm.setPositions(positions);
            int positionId = frm.getPositionId();
            frm.setPositionId(positionId);
            ArrayList list = mobilization.getCandidatesByclientandAssetName("", clientId, assetId, 0);
            request.getSession().setAttribute("MOBILIZATIONINFO", minfo);
            request.getSession().setAttribute("CRCANDIDATESLIST", list);

            return mapping.findForward("details_mobilization");
        } else if (frm.getDoMobhistMail() != null && frm.getDoMobhistMail().equals("yes")) {
            frm.setDoMobhistMail("no");
            print(this, " getDoMobhistMail block :: ");

            int clientId = frm.getClientId();
            frm.setClientId(clientId);
            int assetId = frm.getClientassetId();
            frm.setClientassetId(assetId);
            String txtsearch = frm.getTxtsearch();
            frm.setTxtsearch(txtsearch);
            int crewrotationId = frm.getCrewrotationId();
            frm.setCrewrotationId(crewrotationId);
            int positionId = frm.getPositionId();
            frm.setPositionId(positionId);
            int type = frm.getType();
            frm.setType(type);

            String from = frm.getMailfrom();
            String to = frm.getMailto();
            String cc = frm.getMailcc();
            String bcc = frm.getMailbcc();
            String subject = frm.getMailsubject();
            String body = frm.getMaildescription();
            ArrayList doclist = new ArrayList();
            if (request.getSession().getAttribute("MAILHISTDOCLIST") != null) {
                doclist = (ArrayList) request.getSession().getAttribute("MAILHISTDOCLIST");
            }

            int doclist_size = doclist.size();
            String filePath = mobilization.getMainPath("add_mobilization");
            String vfilePath = mobilization.getMainPath("view_mobilization");
            String filenames[] = new String[doclist_size];
            String vfilenames[] = new String[doclist_size];
            String candidatename[] = new String[doclist_size];

            if (doclist_size > 0) {
                MobilizationInfo info = null;
                for (int i = 0; i < doclist_size; i++)
                {
                    info = (MobilizationInfo) doclist.get(i);
                    if (info != null) 
                    {
                        if (!info.getFilename().equals("")) 
                        {
                            filenames[i] = filePath + info.getFilename();
                            vfilenames[i] = vfilePath + info.getFilename();
                            if (info.getType() == 1) 
                            {
                                candidatename[i] = info.getVal5() + ".pdf";
                            } else if (info.getType() == 2) {
                                candidatename[i] = info.getVal1() + ".pdf";
                            }
                        }
                    }
                }
            }
            String vfilename = "";
            if (vfilenames.length > 0) {
                vfilename = vobj.replacealphacomma(makeCommaDelimString(vfilenames));
            }
            MobilizationInfo minfo = mobilization.getMobiClientDetails(clientId, assetId);
            int val = mobilization.sendMobilizationMail(from, to, cc, bcc, subject, body, candidatename, filenames, minfo.getClientName(), vfilename, username);
            request.getSession().removeAttribute("MAILHISTDOCLIST");

            MobilizationInfo info = mobilization.getCandDtlsByCrewId(crewrotationId);
            ArrayList list = mobilization.getMobDataByCrewId(crewrotationId, type);
            request.getSession().setAttribute("CANDIDATEINFO", info);
            request.getSession().setAttribute("MOBILIZATIONLIST", list);

            return mapping.findForward("dtls_mobtravel");
        } else if (frm.getDoCancel() != null && frm.getDoCancel().equals("yes")) {
            frm.setDoCancel("no");
            print(this, " getDoCancel block :: ");

            int next = 0;
            if (request.getSession().getAttribute("NEXTVALUE") != null) {
                next = Integer.parseInt((String) request.getSession().getAttribute("NEXTVALUE"));
            }
            next = next - 1;
            if (next < 0) {
                next = 0;
            }
            ArrayList mobilizationList = mobilization.getClientSelectByName(search, statusIndex, next, count, clientIdIndex, assetIdIndex, countryId, allclient, permission, cids, assetids);
            int cnt = 0;
            if (mobilizationList.size() > 0) {
                MobilizationInfo cinfo = (MobilizationInfo) mobilizationList.get(mobilizationList.size() - 1);
                cnt = cinfo.getCrewrotationId();
                mobilizationList.remove(mobilizationList.size() - 1);
            }
            request.getSession().setAttribute("MOBILIZATION_LIST", mobilizationList);
            request.getSession().setAttribute("COUNT_LIST", cnt + "");
            request.getSession().setAttribute("NEXT", next + "");
            request.getSession().setAttribute("NEXTVALUE", (next + 1) + "");
            return mapping.findForward("display");
        } else {
            print(this, "else block.");

            if (frm.getCtp() == 0) {
                Stack<String> blist = new Stack<String>();
                if (request.getSession().getAttribute("BACKURL") != null) {
                    blist = (Stack<String>) request.getSession().getAttribute("BACKURL");
                }
                blist.push(request.getRequestURI());
                request.getSession().setAttribute("BACKURL", blist);
            }
            ArrayList mobilizationList = mobilization.getClientSelectByName(search, statusIndex, 0, count, clientIdIndex, assetIdIndex, countryId, allclient, permission, cids, assetids);
            int cnt = 0;
            if (mobilizationList.size() > 0) {
                MobilizationInfo cinfo = (MobilizationInfo) mobilizationList.get(mobilizationList.size() - 1);
                cnt = cinfo.getCrewrotationId();
                mobilizationList.remove(mobilizationList.size() - 1);
            }
            request.getSession().setAttribute("MOBILIZATION_LIST", mobilizationList);
            request.getSession().setAttribute("COUNT_LIST", cnt + "");
            request.getSession().setAttribute("NEXT", "0");
            request.getSession().setAttribute("NEXTVALUE", "1");
            return mapping.findForward("display");
        }
    }
}
