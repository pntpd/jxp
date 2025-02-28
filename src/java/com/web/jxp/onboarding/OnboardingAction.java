package com.web.jxp.onboarding;

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

public class OnboardingAction extends Action {

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        OnboardingForm frm = (OnboardingForm) form;
        Onboarding onboarding = new Onboarding();
        int count = onboarding.getCount();
        String search = frm.getSearch() != null ? frm.getSearch() : "";
        frm.setSearch(search);
        int statusIndex = frm.getStatusIndex();
        frm.setStatusIndex(statusIndex);
        int uId = 0, allclient = 0;
        String permission = "N", cids = "", assetids = "", username = "";
        if (request.getSession().getAttribute("LOGININFO") != null) {
            UserInfo uInfo = (UserInfo) request.getSession().getAttribute("LOGININFO");
            if (uInfo != null)
            {
                permission = uInfo.getPermission();
                uId = uInfo.getUserId();
                cids = uInfo.getCids();
                allclient = uInfo.getAllclient();
                assetids = uInfo.getAssetids();
                username = uInfo.getName();
            }
        }
        int check_user = onboarding.checkUserSession(request, 56, permission);
        if (check_user == -1) {
            return mapping.findForward("default");
        } else if (check_user == -2) {
            String authmess = onboarding.getMainPath("user_auth");
            authmess = authmess.replaceAll("__MODULE__", "Onboarding Enrollment Module");
            request.setAttribute("AUTHMESSAGE", authmess);
            return mapping.findForward("auth");
        }

        Collection clients = onboarding.getClients(cids, allclient, permission);
        frm.setClients(clients);
        int clientIdIndex = frm.getClientIdIndex();
        frm.setClientIdIndex(clientIdIndex);

        Collection assets = onboarding.getClientAsset(clientIdIndex, assetids, allclient, permission);
        frm.setAssets(assets);
        int assetIdIndex = frm.getAssetIdIndex();
        frm.setAssetIdIndex(assetIdIndex);

        if (frm.getDoView() != null && frm.getDoView().equals("yes")) {
            frm.setDoView("no");
            print(this, " getDoView block :: ");

            int clientId = frm.getClientId();
            frm.setClientId(clientId);
            int clientassetId = frm.getClientassetId();
            frm.setClientassetId(clientassetId);
            int assetcountryId = frm.getAssetcountryId();
            frm.setAssetcountryId(assetcountryId);

            int jobpostId = frm.getJobpostId();
            frm.setJobpostId(jobpostId);

            frm.setShortlistId(0);
            frm.setCandidateId(0);

            OnboardingInfo info = onboarding.getonboardingByIdforDetail(clientId, clientassetId);
            ArrayList onboardcandList = onboarding.getSelectedCandidateListByIDs(clientId, clientassetId, "", 0);
            request.getSession().setAttribute("ONBOARDING_DETAIL", info);
            request.getSession().setAttribute("SELECTEDCANDIDATE_LIST", onboardcandList);

            return mapping.findForward("onboarding_details");
        } else if (frm.getDoViewCancel() != null && frm.getDoViewCancel().equals("yes")) {
            frm.setDoViewCancel("no");
            print(this, " getDoViewCancel block :: ");
            int clientId = frm.getClientId();
            frm.setClientId(clientId);
            int clientassetId = frm.getClientassetId();
            frm.setClientassetId(clientassetId);
            int jobpostId = frm.getJobpostId();
            frm.setJobpostId(jobpostId);

            frm.setShortlistId(0);
            frm.setCandidateId(0);

            OnboardingInfo dltsinfo = onboarding.getonboardingByIdforDetail(clientId, clientassetId);
            ArrayList onboardcandList = onboarding.getSelectedCandidateListByIDs(clientId, clientassetId, "", 0);
            request.getSession().setAttribute("ONBOARDING_DETAIL", dltsinfo);
            request.getSession().setAttribute("SELECTEDCANDIDATE_LIST", onboardcandList);
            return mapping.findForward("onboarding_details");
        } else if (frm.getDoSaveTravel() != null && frm.getDoSaveTravel().equals("yes")) {
            frm.setDoSaveTravel("no");
            print(this, " getDoSaveTravel block :: ");

            int clientId = frm.getClientId();
            frm.setClientId(clientId);
            int clientassetId = frm.getClientassetId();
            frm.setClientassetId(clientassetId);
            int assetcountryId = frm.getAssetcountryId();
            frm.setAssetcountryId(assetcountryId);
            int shortlistId = frm.getShortlistId();
            frm.setShortlistId(shortlistId);

            int travelnotreq = frm.getChkNotReqTravel();
            int status = 1;

            int cc = onboarding.insertTravelDtls(shortlistId, travelnotreq, uId, status);

            if (cc > 0) {
                request.setAttribute("ACCOMMODATIONMODAL", "yes");
            }
            OnboardingInfo dltsinfo = onboarding.getonboardingByIdforDetail(clientId, clientassetId);
            ArrayList onboardcandList = onboarding.getSelectedCandidateListByIDs(clientId, clientassetId, "", 0);
            request.getSession().setAttribute("ONBOARDING_DETAIL", dltsinfo);
            request.getSession().setAttribute("SELECTEDCANDIDATE_LIST", onboardcandList);
            return mapping.findForward("onboarding_details");
        } else if (frm.getDoSaveAccomm() != null && frm.getDoSaveAccomm().equals("yes")) {
            frm.setDoSaveAccomm("no");
            print(this, " getDoSaveAccomm block :: ");

            int clientId = frm.getClientId();
            frm.setClientId(clientId);
            int clientassetId = frm.getClientassetId();
            frm.setClientassetId(clientassetId);
            int assetcountryId = frm.getAssetcountryId();
            frm.setAssetcountryId(assetcountryId);
            int shortlistId = frm.getShortlistId();

            int chkAccommodation = frm.getChkAccommodation();
            int status = 1;

            int cc = onboarding.insertAccomodationDtls(shortlistId, chkAccommodation, uId, status);

            OnboardingInfo dltsinfo = onboarding.getonboardingByIdforDetail(clientId, clientassetId);
            ArrayList onboardcandList = onboarding.getSelectedCandidateListByIDs(clientId, clientassetId, "", 0);
            request.getSession().setAttribute("ONBOARDING_DETAIL", dltsinfo);
            request.getSession().setAttribute("SELECTEDCANDIDATE_LIST", onboardcandList);

            return mapping.findForward("onboarding_details");
        } else if (frm.getDoSaveReqDoc() != null && frm.getDoSaveReqDoc().equals("yes")) {
            frm.setDoSaveReqDoc("no");
            print(this, " getDoSaveReqDoc block :: ");

            int clientId = frm.getClientId();
            frm.setClientId(clientId);
            int clientassetId = frm.getClientassetId();
            frm.setClientassetId(clientassetId);
            int assetcountryId = frm.getAssetcountryId();
            frm.setAssetcountryId(assetcountryId);
            int shortlistId = frm.getShortlistId();

            String reqdocIds = makeCommaDelimString(frm.getChkreqdocListId());
            int cc = onboarding.insertReqDoclist(reqdocIds, shortlistId, uId);

            OnboardingInfo dltsinfo = onboarding.getonboardingByIdforDetail(clientId, clientassetId);
            ArrayList onboardcandList = onboarding.getSelectedCandidateListByIDs(clientId, clientassetId, "", 0);
            request.getSession().setAttribute("ONBOARDING_DETAIL", dltsinfo);
            request.getSession().setAttribute("SELECTEDCANDIDATE_LIST", onboardcandList);

            return mapping.findForward("onboarding_details");
        } else if (frm.getDoSaveDocCheck() != null && frm.getDoSaveDocCheck().equals("yes")) {
            frm.setDoSaveDocCheck("no");
            print(this, " getDoSaveDocCheck block :: ");

            int clientId = frm.getClientId();
            frm.setClientId(clientId);
            int clientassetId = frm.getClientassetId();
            frm.setClientassetId(clientassetId);
            int assetcountryId = frm.getAssetcountryId();
            frm.setAssetcountryId(assetcountryId);
            int reqcheckstatus = frm.getReqcheckstatus();
            int shortlistId = frm.getShortlistId();

            String docchecklistIds = makeCommaDelimString(frm.getChkdoccheckListId());
            int cc = onboarding.insertDocChecklist(docchecklistIds, shortlistId, uId, reqcheckstatus, username);

            OnboardingInfo dltsinfo = onboarding.getonboardingByIdforDetail(clientId, clientassetId);
            ArrayList onboardcandList = onboarding.getSelectedCandidateListByIDs(clientId, clientassetId, "", 0);
            request.getSession().setAttribute("ONBOARDING_DETAIL", dltsinfo);
            request.getSession().setAttribute("SELECTEDCANDIDATE_LIST", onboardcandList);

            return mapping.findForward("onboarding_details");
        } else if (frm.getDoMailtravel() != null && frm.getDoMailtravel().equals("yes")) {
            frm.setDoMailtravel("no");
            print(this, " getDoMailtravel block :: ");

            int clientId = frm.getClientId();
            frm.setClientId(clientId);
            int clientassetId = frm.getClientassetId();
            frm.setClientassetId(clientassetId);
            int assetcountryId = frm.getAssetcountryId();
            frm.setAssetcountryId(assetcountryId);

            int candidateId = frm.getCandidateId();
            frm.setCandidateId(candidateId);

            int shortlistId = frm.getShortlistId();
            frm.setShortlistId(shortlistId);

            String fromval = frm.getFromval();
            String toval = frm.getToval();
            String ccval = frm.getCcval();
            String bccval = frm.getBccval();
            String subject = frm.getSubject();
            String description = frm.getDescription();
            FormFile attachfile = frm.getAttachfile();

            String add_onboarding = onboarding.getMainPath("add_onboarding");
            String view_onboarding = onboarding.getMainPath("view_onboarding");

            OnboardingInfo cinfo = onboarding.getSelectedCandidatedetailsByid(shortlistId);

            String foldername = onboarding.createFolder(add_onboarding);
            Date now = new Date();
            String fn = String.valueOf(now.getTime());

            try {
                String fileName1 = "";
                if (attachfile != null && attachfile.getFileSize() > 0) {
                    fileName1 = onboarding.uploadFile(0, "", attachfile, fn + "_1", add_onboarding, foldername);
                    onboarding.updateexternalpdfmobfile(shortlistId, fileName1, uId);
                }
                OnboardingInfo info = onboarding.getonboardingByIdforDetail(clientId, clientassetId);

                ArrayList doclist = new ArrayList();
                if (request.getSession().getAttribute("MAILDOCLIST") != null) {
                    doclist = (ArrayList) request.getSession().getAttribute("MAILDOCLIST");
                }
                int doclist_size = doclist.size();
                if (!fileName1.equals("") && fileName1 != null) {
                    doclist_size = doclist_size + 1;
                }
                String filenames[] = new String[doclist_size];
                String vfilenames[] = new String[doclist_size];
                String candidatename[] = new String[doclist_size];

                int lastindex = 0;
                if (doclist_size > 0) {
                    OnboardingInfo oinfo = null;
                    int tempcount = 0;
                    if (!fileName1.equals("") && fileName1 != null) {
                        tempcount = doclist_size - 1;
                    } else {
                        tempcount = doclist_size;
                    }
                    for (int i = 0; i < tempcount; i++) {
                        lastindex++;
                        oinfo = (OnboardingInfo) doclist.get(i);
                        if (oinfo != null) {
//                        print(this, " getDoMobMail Mail after info :: travelnotreq " + travelnotreq + " accommnotereq " + accommnotereq + " doclist.size() " + doclist.size());
                            if (!oinfo.getFilename().equals("")) {
                                filenames[i] = add_onboarding + oinfo.getFilename();
                                vfilenames[i] = view_onboarding + oinfo.getFilename();
                                if (oinfo.getType() == 1) {
                                    candidatename[i] = oinfo.getVal5() + ".pdf";
                                } else if (oinfo.getType() == 2) {
                                    candidatename[i] = oinfo.getVal1() + ".pdf";
                                }
                            }
                        }
                    }
                }
                if (!fileName1.equals("") && fileName1 != null) {
                    filenames[lastindex] = add_onboarding + fileName1;
                    vfilenames[lastindex] = view_onboarding + fileName1;
                    candidatename[lastindex] = "External.pdf";
                }

                String vfilename = "";
                if (vfilenames.length > 0) {
                    vfilename = makeCommaDelimString(vfilenames);
                }
                int val = onboarding.sendMobilizationMail(fromval, toval, ccval, bccval, subject, description, candidatename, filenames, info.getClientName(), vfilename, username, shortlistId);
            } catch (Exception e) {
                e.printStackTrace();
            }

            request.getSession().removeAttribute("MAILDOCLIST");
            onboarding.updateonboardflagfortravelmail(shortlistId, uId, username, 3);

            OnboardingInfo dltsinfo = onboarding.getonboardingByIdforDetail(clientId, clientassetId);
            ArrayList onboardcandList = onboarding.getSelectedCandidateListByIDs(clientId, clientassetId, "", 0);
            request.getSession().setAttribute("ONBOARDING_DETAIL", dltsinfo);
            request.getSession().setAttribute("SELECTEDCANDIDATE_LIST", onboardcandList);

            return mapping.findForward("onboarding_details");

        } else if (frm.getDoGenerate() != null && frm.getDoGenerate().equals("yes")) {
            frm.setDoGenerate("no");
            print(this, " getDoView block :: ");

            int clientId = frm.getClientId();
            frm.setClientId(clientId);
            int clientassetId = frm.getClientassetId();
            frm.setClientassetId(clientassetId);
            int assetcountryId = frm.getAssetcountryId();
            frm.setAssetcountryId(assetcountryId);

            int candidateId = frm.getCandidateId();
            frm.setCandidateId(candidateId);

            int shortlistId = frm.getShortlistId();
            frm.setShortlistId(shortlistId);

            Collection formalitiestemplate = onboarding.getformalitiesById(clientId, shortlistId);
            frm.setFormalitiestemplate(formalitiestemplate);
            frm.setFormalityId(-1);

            OnboardingInfo info = onboarding.getonboardingByIdforDetail(clientId, clientassetId);
            request.setAttribute("ONBOARDING_DETAIL", info);

            OnboardingInfo cinfo = onboarding.getSelectedCandidatedetailsByid(shortlistId);
            request.setAttribute("SHORTLISTEDCANDIDATE_DETAILS", cinfo);
            frm.setCandidateId(cinfo.getCandidateId());
            ArrayList list = onboarding.getformlistByshortlist(shortlistId);
            request.setAttribute("FORMALITYLIST_DETAILS", list);
            frm.setGeneratedlistsize(list.size());
            return mapping.findForward("generate_formalities");

        } else if (frm.getDoMail() != null && frm.getDoMail().equals("yes")) {
            frm.setDoMail("no");
            print(this, " getDoMail block :: ");

            int clientId = frm.getClientId();
            frm.setClientId(clientId);
            int clientassetId = frm.getClientassetId();
            frm.setClientassetId(clientassetId);
            int assetcountryId = frm.getAssetcountryId();
            frm.setAssetcountryId(assetcountryId);

            int candidateId = frm.getCandidateId();
            frm.setCandidateId(candidateId);

            int shortlistId = frm.getShortlistId();
            frm.setShortlistId(shortlistId);

            String fromval = frm.getFromval();
            String toval = frm.getToval();
            String ccval = frm.getCcval();
            String bccval = frm.getBccval();
            String subject = frm.getSubject();
            String description = frm.getDescription();

            String add_archivefiles = onboarding.getMainPath("add_archivefiles");
            FormFile attachfile = frm.getAttachfile();
            OnboardingInfo cinfo = onboarding.getSelectedCandidatedetailsByid(shortlistId);
            String foldername = onboarding.createFolder(add_archivefiles);
            Date now = new Date();
            String fn = String.valueOf(now.getTime());
            String fileName1 = "";

            fileName1 = frm.getZipfilename();
            if (attachfile != null && attachfile.getFileSize() > 0) {
                fileName1 = onboarding.uploadFile(0, "", attachfile, fn + "_1", add_archivefiles, foldername);
            }

            OnboardingInfo info = onboarding.getonboardingByIdforDetail(clientId, clientassetId);

            int maillogId = onboarding.sendformalityMail(fromval, toval, ccval, bccval, subject, description, cinfo.getName(), fileName1, info.getClientName(), shortlistId, username);

            if (attachfile != null && attachfile.getFileSize() > 0) {
                onboarding.updateonboardflagformail(shortlistId, uId, username, 7, 1,"");
            } else {
                onboarding.updateonboardflagformail(shortlistId, uId, username, 6, 0,"");
            }

            OnboardingInfo dltsinfo = onboarding.getonboardingByIdforDetail(clientId, clientassetId);
            ArrayList onboardcandList = onboarding.getSelectedCandidateListByIDs(clientId, clientassetId, "", 0);
            request.getSession().setAttribute("ONBOARDING_DETAIL", dltsinfo);
            request.getSession().setAttribute("SELECTEDCANDIDATE_LIST", onboardcandList);

            return mapping.findForward("onboarding_details");
        } else if (frm.getDoUpload() != null && frm.getDoUpload().equals("yes")) {
            frm.setDoUpload("no");
            print(this, " getDoUpload block :: ");

            int clientId = frm.getClientId();
            frm.setClientId(clientId);
            int clientassetId = frm.getClientassetId();
            frm.setClientassetId(clientassetId);
            int assetcountryId = frm.getAssetcountryId();
            frm.setAssetcountryId(assetcountryId);

            int candidateId = frm.getCandidateId();
            frm.setCandidateId(candidateId);
            int onboarddocId = frm.getOnboarddocId();
            int shortlistId = frm.getShortlistId();
            frm.setShortlistId(shortlistId);            

            String add_onboarding = onboarding.getMainPath("add_onboarding");
            FormFile attachfile = frm.getAttachfile();
            String foldername = onboarding.createFolder(add_onboarding);
            Date now = new Date();
            String fn = String.valueOf(now.getTime());
            String fileName1 = "";

            OnboardingInfo cinfo = onboarding.getSelectedCandidatedetailsByid(shortlistId);
            if (attachfile != null && attachfile.getFileSize() > 0) {
                fileName1 = onboarding.uploadFile(0, "", attachfile, fn + "_1", add_onboarding, foldername);
                if (cinfo.getOnboardflag() == 7) {
                    onboarding.updateshortlistforextupload(shortlistId, fileName1, uId);
                    onboarding.updateonboardflagformail(shortlistId, uId, "", 8, 0,"");
                } else {
                    onboarding.uploadformalitypdf(shortlistId, fileName1, onboarddocId, uId);
                }
                int countflag = onboarding.getfileexitsByIds(shortlistId);
                if (countflag == 0) {
                    onboarding.updateonboardflagformail(shortlistId, uId, "", 8, 0,"");
                }
            }
            frm.setDate2(frm.getDate2());
            OnboardingInfo binfo = onboarding.getSelectedCandidatedetailsByid(shortlistId);
            request.setAttribute("SHORTLISTEDCANDIDATE_DETAILS", binfo);
            OnboardingInfo info = onboarding.getonboardingByIdforDetail(clientId, clientassetId);
            request.setAttribute("ONBOARDING_DETAIL", info);
            ArrayList list = onboarding.getuploadformlistByshortlist(shortlistId);
            request.setAttribute("UPLOADFORMALITYLIST_DETAILS", list);
            ArrayList onboardingkitlist = onboarding.getOnboardingkitByName();
            request.setAttribute("KITLIST_DETAILS", onboardingkitlist);

            return mapping.findForward("upload_formalities");
        } else if (frm.getDoMobTravel() != null && frm.getDoMobTravel().equals("yes")) {
            frm.setDoMobTravel("no");
            print(this, " getDoMobTravel block :: ");
            int clientId = frm.getClientId();
            frm.setClientId(clientId);
            int assetId = frm.getClientassetId();
            frm.setClientassetId(assetId);
            int shortlistId = frm.getShortlistId();
            frm.setShortlistId(shortlistId);
            String txtsearch = frm.getTxtsearch();
            frm.setTxtsearch(txtsearch);
            String adate = frm.getAdate();
            frm.setAdate(adate);
            int onstatus = frm.getOnstatus();
            frm.setOnstatus(onstatus);
            int type = frm.getType();
            frm.setType(type);

            OnboardingInfo info = onboarding.getSelectedCandidatedetailsByid(shortlistId);
            ArrayList list = onboarding.getOnboadingListByshortlistId(shortlistId, type);
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
            int shortlistId = frm.getShortlistId();
            frm.setShortlistId(shortlistId);
            String txtsearch = frm.getTxtsearch();
            frm.setTxtsearch(txtsearch);
            String adate = frm.getAdate();
            frm.setAdate(adate);
            int onstatus = frm.getOnstatus();
            frm.setOnstatus(onstatus);
            int type = frm.getType();
            frm.setType(type);

            OnboardingInfo info = onboarding.getSelectedCandidatedetailsByid(shortlistId);
            ArrayList list = onboarding.getOnboadingListByshortlistId(shortlistId, type);
            request.getSession().setAttribute("CANDIDATEINFO", info);
            request.getSession().setAttribute("MOBILIZATIONLIST", list);

            return mapping.findForward("dtls_mobaccomm");
        } else if (frm.getDoDeleteExt() != null && frm.getDoDeleteExt().equals("yes")) {
            frm.setDoDeleteExt("no");
            print(this, " getDoDeleteExt block :: ");

            int clientId = frm.getClientId();
            frm.setClientId(clientId);
            int clientassetId = frm.getClientassetId();
            frm.setClientassetId(clientassetId);
            int assetcountryId = frm.getAssetcountryId();
            frm.setAssetcountryId(assetcountryId);

            int candidateId = frm.getCandidateId();
            frm.setCandidateId(candidateId);
            int shortlistId = frm.getShortlistId();
            String extFilename = frm.getHdnExternalfile();
            frm.setShortlistId(shortlistId);

            String add_onboarding = onboarding.getMainPath("add_onboarding");
            String strResponse = onboarding.deleteFiles(extFilename, add_onboarding);
            if (strResponse.equals("true")) {
                onboarding.updateshortlistforextupload(shortlistId, "", uId);
                onboarding.updateonboardflagformail(shortlistId, uId, "", 7, 0,"");
            }

            OnboardingInfo cinfo = onboarding.getSelectedCandidatedetailsByid(shortlistId);
            request.setAttribute("SHORTLISTEDCANDIDATE_DETAILS", cinfo);
            OnboardingInfo info = onboarding.getonboardingByIdforDetail(clientId, clientassetId);
            request.setAttribute("ONBOARDING_DETAIL", info);
            ArrayList list = onboarding.getuploadformlistByshortlist(shortlistId);
            request.setAttribute("UPLOADFORMALITYLIST_DETAILS", list);
            ArrayList onboardingkitlist = onboarding.getOnboardingkitByName();
            request.setAttribute("KITLIST_DETAILS", onboardingkitlist);

            return mapping.findForward("upload_formalities");
        } else if (frm.getDoSave() != null && frm.getDoSave().equals("yes")) {
            frm.setDoSave("no");

            print(this, " getDoSave block :: ");

            int clientId = frm.getClientId();
            frm.setClientId(clientId);
            int clientassetId = frm.getClientassetId();
            frm.setClientassetId(clientassetId);
            int assetcountryId = frm.getAssetcountryId();
            frm.setAssetcountryId(assetcountryId);

            int candidateId = frm.getCandidateId();
            frm.setCandidateId(candidateId);

            int shortlistId = frm.getShortlistId();
            frm.setShortlistId(shortlistId);

            String onboardingkitIds[] = frm.getOnboardingkitIds();
            String onboardingkitids = makeCommaDelimString(onboardingkitIds);
            String date = frm.getDate2();
            OnboardingInfo cinfo = onboarding.getSelectedCandidatedetailsByid(shortlistId);

            if (cinfo.getOnboardflag() == 8) {
                int updatestatus = onboarding.updateshortlistforkit(shortlistId, onboardingkitids, uId);

                if (updatestatus == 1) {
                    int jobpostId = 0;
                    onboarding.updateonboardflagformail(shortlistId, uId, "", 9, 0, date);
                    jobpostId = onboarding.getJobpostIdByShortlistId(shortlistId, uId, cinfo.getCandidateId(), clientId, clientassetId);
                    onboarding.insertCrewRotation(clientId, clientassetId, cinfo.getCandidateId(), jobpostId, uId);
                }
            }

            OnboardingInfo dltsinfo = onboarding.getonboardingByIdforDetail(clientId, clientassetId);
            ArrayList onboardcandList = onboarding.getSelectedCandidateListByIDs(clientId, clientassetId, "", 0);
            request.getSession().setAttribute("ONBOARDING_DETAIL", dltsinfo);
            request.getSession().setAttribute("SELECTEDCANDIDATE_LIST", onboardcandList);

            return mapping.findForward("onboarding_details");

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
            ArrayList jobpostList = onboarding.getClientSelectByName(search, statusIndex, next, count, clientIdIndex, assetIdIndex, allclient, permission, cids, assetids);
            int cnt = 0;
            if (jobpostList.size() > 0) {
                OnboardingInfo cinfo = (OnboardingInfo) jobpostList.get(jobpostList.size() - 1);
                cnt = cinfo.getClientId();
                jobpostList.remove(jobpostList.size() - 1);
            }
            request.getSession().setAttribute("ONBOARDING_LIST", jobpostList);
            request.getSession().setAttribute("COUNT_LIST", cnt + "");
            request.getSession().setAttribute("NEXT", next + "");
            request.getSession().setAttribute("NEXTVALUE", (next + 1) + "");
            return mapping.findForward("display");
        } else if (frm.getDoSummary() != null && frm.getDoSummary().equals("yes")) {
            frm.setDoSummary("no");
            print(this, " getDoSummary block :: ");

            int clientId = frm.getClientId();
            frm.setClientId(clientId);
            int clientassetId = frm.getClientassetId();
            frm.setClientassetId(clientassetId);
            int assetcountryId = frm.getAssetcountryId();
            frm.setAssetcountryId(assetcountryId);

            int jobpostId = frm.getJobpostId();
            frm.setJobpostId(jobpostId);
            int shortlistId = frm.getShortlistId();

            OnboardingInfo info = onboarding.getonboardingByIdforDetail(clientId, clientassetId);
            OnboardingInfo candinfo = onboarding.getSelectedCandidateByIDs(shortlistId);
            OnboardingInfo reqdocinfo = onboarding.getReqDocListId(shortlistId);
            request.getSession().setAttribute("ONBOARDING_DETAIL", info);
            request.getSession().setAttribute("SELECTEDCANDIDATE", candinfo);
            request.getSession().setAttribute("CANDIDATEINFO", reqdocinfo);

            return mapping.findForward("summary_details");
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

            ArrayList jobpostList = onboarding.getClientSelectByName(search, statusIndex, 0, count, clientIdIndex, assetIdIndex, allclient, permission, cids, assetids);
            int cnt = 0;
            if (jobpostList.size() > 0) {
                OnboardingInfo cinfo = (OnboardingInfo) jobpostList.get(jobpostList.size() - 1);
                cnt = cinfo.getClientId();
                jobpostList.remove(jobpostList.size() - 1);
            }
            request.getSession().setAttribute("ONBOARDING_LIST", jobpostList);
            request.getSession().setAttribute("COUNT_LIST", cnt + "");
            request.getSession().setAttribute("NEXT", "0");
            request.getSession().setAttribute("NEXTVALUE", "1");

        }
        return mapping.findForward("display");
    }
}
