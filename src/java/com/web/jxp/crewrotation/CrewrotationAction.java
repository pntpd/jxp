package com.web.jxp.crewrotation;

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
import java.util.Stack;

public class CrewrotationAction extends Action {

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        CrewrotationForm frm = (CrewrotationForm) form;
        Crewrotation crewrotation = new Crewrotation();
        int count = crewrotation.getCount();
        String search = frm.getSearch() != null ? frm.getSearch() : "";
        frm.setSearch(search);
        int statusIndex = frm.getStatusIndex();
        frm.setStatusIndex(statusIndex);
        int countryId = frm.getCountryId();
        int uId = 0, allclient = 0;
        String permission = "N", cids = "", assetids = "", username = "";
        if (request.getSession().getAttribute("LOGININFO") != null) {
            UserInfo uInfo = (UserInfo) request.getSession().getAttribute("LOGININFO");
            if (uInfo != null) {
                permission = uInfo.getPermission();
                uId = uInfo.getUserId();
                cids = uInfo.getCids();
                allclient = uInfo.getAllclient();
                assetids = uInfo.getAssetids();
                username = uInfo.getName();
            }
        }
        int check_user = crewrotation.checkUserSession(request, 57, permission);
        if (check_user == -1) {
            return mapping.findForward("default");
        } else if (check_user == -2) {
            String authmess = crewrotation.getMainPath("user_auth");
            authmess = authmess.replaceAll("__MODULE__", "Crew Rotation Module");
            request.setAttribute("AUTHMESSAGE", authmess);
            return mapping.findForward("auth");
        }

        Collection clients = crewrotation.getClients(cids, allclient, permission);
        frm.setClients(clients);
        int clientIdIndex = frm.getClientIdIndex();
        frm.setClientIdIndex(clientIdIndex);

        Collection assets = crewrotation.getClientAsset(clientIdIndex, assetids, allclient, permission);
        frm.setAssets(assets);

        int assetIdIndex = frm.getAssetIdIndex();
        frm.setAssetIdIndex(assetIdIndex);

        String searchcr = frm.getSearchcr();
        frm.setSearchcr(searchcr);

        Collection locations = crewrotation.getCountryList();
        frm.setLocations(locations);

        if (frm.getDoView() != null && frm.getDoView().equals("yes")) {
            frm.setDoView("no");
            print(this, " getDoView block :: ");
            int clientId = frm.getClientId();
            int assetId = frm.getClientassetId();

            int positionIndex = frm.getPositionIdIndex();
            Collection positions = crewrotation.getPositionList(assetId);
            frm.setPositions(positions);
            searchcr = frm.getSearchcr();
            String crdate = frm.getCrdate();
            frm.setCrdate(crdate);
            int statusindex = frm.getStatusindex();
            frm.setStatusindex(statusindex);
            int dynamicId = frm.getDynamicId();
            frm.setDynamicId(dynamicId);
            int activityDropdown = frm.getActivityDropdown();
            frm.setActivityDropdown(activityDropdown);
            CrewrotationInfo info = crewrotation.getByclientandAssetName("", clientId, assetId, countryId);
            request.getSession().setAttribute("CREWROTATIONINFO", info);

            ArrayList list = crewrotation.getCandidatesByclientandAssetName(searchcr, clientId, assetId, countryId, crdate, statusindex, positionIndex, activityDropdown, dynamicId);
            request.getSession().setAttribute("CRCANDIDATESLIST", list);

            return mapping.findForward("crewrotation_details");
        } else if (frm.getDoSaveActivity() != null && frm.getDoSaveActivity().equals("yes")) {
            frm.setDoSaveActivity("no");
            print(this, " getDoSaveActivity block :: ");
            int clientId = frm.getClientId();
            int assetId = frm.getClientassetId();
            int activityId = frm.getActivityId();   
            int positionIdActivity = frm.getPositionIdActivity();
            int positionIdSignOff = frm.getPositionIdSignOff();
            int crewrotationId = frm.getCrewrotationId();
            int status = frm.getStatus();
            String fromdate = frm.getAstartdate();
            String enddate = frm.getAenddate();
            String remarks = frm.getRemarks();
            int cractivityId = frm.getCractivityId();
            int positionIdtemp = frm.getPositionId();
            CrewrotationInfo info2 = crewrotation.getCrewrotationBycrewrotationId(crewrotationId);
            if (cractivityId <= 0) {
                //validation
                int validationstatus = crewrotation.checkcrewActivity(crewrotationId, fromdate, enddate, activityId);
                if (validationstatus <= 0) {
                    if(activityId == 7){    
                        crewrotation.savecrewActivity(crewrotationId, fromdate, enddate, activityId, positionIdSignOff, remarks, uId, status, positionIdtemp);
                    }else
                    {
                        if(info2.getSignonId() > 0 && info2.getSignoffId() <= 0)
                        {
                            crewrotation.savecrewActivity(crewrotationId, fromdate, enddate, activityId, positionIdSignOff, remarks, uId, status, positionIdtemp);
                        }else{
                            crewrotation.savecrewActivity(crewrotationId, fromdate, enddate, activityId, positionIdActivity, remarks, uId, status, positionIdtemp);
                        }
                    }
                    crewrotation.updatecrewrotationActivity(crewrotationId, fromdate, enddate, activityId, remarks, uId, username);
                } else {
                    request.setAttribute("MESSAGE", "date already exists");
                }
                request.setAttribute("CRID", crewrotationId + "");
            } else if (cractivityId > 0) 
            {
                if(activityId == 7)
                {
                    crewrotation.updatecrActivitybyId(crewrotationId, cractivityId, uId, fromdate, enddate, remarks, activityId, positionIdSignOff, positionIdtemp);
                }else{
                    crewrotation.updatecrActivitybyId(crewrotationId, cractivityId, uId, fromdate, enddate, remarks, activityId, positionIdActivity,positionIdtemp);
                }
                int MaxId = crewrotation.getMaxActivityId(crewrotationId);
                if (MaxId == cractivityId) {
                    crewrotation.updatecrewrotationActivity(crewrotationId, fromdate, enddate, activityId, remarks, uId, username);
                }

                String todateindex = frm.getTodateindex();
                frm.setTodateindex(todateindex);
                String fromdateindex = frm.getFromdateindex();
                frm.setFromdateindex(fromdateindex);
                int activityIdindex = frm.getActivityIdindex();
                frm.setActivityIdindex(activityIdindex);
                Collection positions = crewrotation.getCrewPositionList(crewrotationId);
                frm.setPositions(positions);
                int positionId2 = frm.getPositionId2();
                CrewrotationInfo info = crewrotation.getCrewrotationBycrewrotationId(crewrotationId);
                request.getSession().setAttribute("ACTIVITYCRINFO", info);
                ArrayList list = crewrotation.getCrewActivityListBycrewrotationId(fromdateindex, todateindex, activityIdindex, crewrotationId, positionId2);
                request.getSession().setAttribute("CRACTIVITYLIST", list);
                return mapping.findForward("crewrotation_activity");
            }

            CrewrotationInfo info = crewrotation.getByclientandAssetName(search, clientId, assetId, countryId);
            request.getSession().setAttribute("CREWROTATIONINFO", info);

            searchcr = frm.getSearchcr();
            String crdate = frm.getCrdate();
            int statusindex = frm.getStatusindex();
            int activityDropdown = frm.getActivityDropdown();
            frm.setActivityDropdown(activityDropdown);
            int positionIndex = frm.getPositionIdIndex();
            frm.setStatusindex(statusindex);
            int dynamicId = frm.getDynamicId();
            frm.setDynamicId(dynamicId);
            Collection positions = crewrotation.getPositionList(assetId);
            frm.setPositions(positions);
            ArrayList list = crewrotation.getCandidatesByclientandAssetName(searchcr, clientId, assetId, countryId, crdate, statusindex, positionIndex, activityDropdown, dynamicId);
            request.getSession().setAttribute("CRCANDIDATESLIST", list);

            return mapping.findForward("crewrotation_details");
        } else if (frm.getDoSaveSignoff() != null && frm.getDoSaveSignoff().equals("yes")) {
            frm.setDoSaveSignoff("no");
            print(this, " getDoSaveSignoff block :: ");
            int clientId = frm.getClientId();
            int assetId = frm.getClientassetId();
            int crewrotationId = frm.getCrewrotationId();
            int rdateId = frm.getRdateId();
            int noofdays = frm.getNoofdays();
            int crewrota = frm.getHdnCrewRota();

            String edate = frm.getEdate();
            String etime = frm.getEtime();

            String date = "", expecteddate = "";
            String sdate = frm.getSdate();
            String stime = frm.getStime();
            String remarks = frm.getRemarks();
            String remarks1 = frm.getRemarks1();

            String reasons = frm.getReasons();
            String reasons2 = frm.getReasons2();
            String reasons3 = frm.getReasons3();

            String sdate2 = frm.getSdate2();
            String sdate3 = frm.getSdate3();
            String stime2 = frm.getStime2();
            String stime3 = frm.getStime3();

            String edate1 = frm.getEdate1();
            String etime1 = frm.getEtime1();
            int rdateId1 = frm.getRdateid1();

            String reasonsddl = frm.getReasonsDDL() != null ? frm.getReasonsDDL() : "";

            String reasonsddl2 = frm.getReasonsDDL2() != null ? frm.getReasonsDDL2() : "";
            String reasonsddl3 = frm.getReasonsDDL3() != null ? frm.getReasonsDDL3() : "";
            int positionIdSignOff = frm.getPositionIdSignOff();
            
            crewrotation.getNooffDaysForposition(positionIdSignOff);
            CrewrotationInfo infoOS = crewrotation.getCrewrotationBycrewrotationId(crewrotationId);

            if (frm.getSignonoffId() <= 0) {
                if (rdateId == 1) {
                    date = edate + " " + etime + ":00";
                    expecteddate = crewrotation.getDateAfter(date, 1, noofdays, "dd-MMM-yyyy", "yyyy-MM-dd");
                    int signoffId = crewrotation.savesignonoff(crewrotationId, crewrotation.changeDatesignonoff(date), rdateId, reasons, "0000-00-00 00:00:00", 2, uId, crewrota, positionIdSignOff);
                    crewrotation.updatecrewrotationsignoff(crewrotationId, date, (crewrota == 2 ? "0000-00-00 00:00:00" : expecteddate + " " + etime + ":00"), uId, username);
                    crewrotation.updatecractivitysignoff(crewrotationId, crewrotation.changeDate1(edate), expecteddate, signoffId, uId, 1, "", "", 0);
                } else if (rdateId == 2 || rdateId == 3) {
                    if (!reasonsddl.equals("Others")) {
                        reasons = reasonsddl;
                    }
                    date = sdate + " " + stime + ":00";
                    String date1 = "";
                    date1 = edate + " " + etime + ":00";
                    expecteddate = crewrotation.getDateAfter(date, 1, noofdays, "dd-MMM-yyyy", "yyyy-MM-dd");
                    int signoffId = crewrotation.savesignonoff(crewrotationId, crewrotation.changeDatesignonoff(date1), rdateId, reasons, crewrotation.changeDatesignonoff(date), 2, uId, crewrota, positionIdSignOff);
                    crewrotation.updatecrewrotationsignoff(crewrotationId, date, expecteddate + " " + stime + ":00", uId, username);
                    crewrotation.updatecractivitysignoff(crewrotationId, crewrotation.changeDate1(sdate), expecteddate, signoffId, uId, rdateId, sdate, edate, infoOS.getOverstaydays());
                }
                request.setAttribute("CRID", crewrotationId + "");
            } else if (frm.getSignonoffId() > 0) {
                if (rdateId1 == 1) {
                    date = edate1 + " " + etime1 + ":00";
                    expecteddate = crewrotation.getDateAfter(date, 1, noofdays, "dd-MMM-yyyy", "yyyy-MM-dd");

                    crewrotation.updatesignonoffbyId(crewrotationId, frm.getSignonoffId(), uId, crewrotation.changeDate1(edate1) + " " + etime1 + ":00", "", remarks, rdateId,frm.getPositionId());
                    crewrotation.updatecrActivitysignoffbyId(crewrotationId, frm.getCractivityId(), uId, edate1, remarks1, rdateId1, "", "", 0);
                    int MaxId = crewrotation.getMaxSignonoffId(crewrotationId);
                    if (MaxId == frm.getSignonoffId()) {
                        crewrotation.updatecrewrotationsignoff(crewrotationId, date, expecteddate + " " + etime + ":00", uId, username);
                    }
                } else if (rdateId1 == 2) {
                    if (!reasonsddl2.equals("Others")) {
                        reasons2 = reasonsddl2;
                    }
                    date = sdate2 + " " + stime2 + ":00";
                    String date1 = "";
                    date1 = edate1 + " " + etime1 + ":00";
                    expecteddate = crewrotation.getDateAfter(date, 1, noofdays, "dd-MMM-yyyy", "yyyy-MM-dd");

                    crewrotation.updatesignonoffbyId(crewrotationId, frm.getSignonoffId(), uId, crewrotation.changeDate1(edate1) + " " + etime1 + ":00", crewrotation.changeDate1(sdate2) + " " + stime2 + ":00", reasons2, rdateId1,frm.getPositionId());
                    crewrotation.updatecrActivitysignoffbyId(crewrotationId, frm.getCractivityId(), uId, sdate2, reasons2, rdateId1, "", "", 0);

                    int MaxId = crewrotation.getMaxSignonoffId(crewrotationId);
                    if (MaxId == frm.getSignonoffId()) {
                        crewrotation.updatecrewrotationsignoff(crewrotationId, date, expecteddate + " " + etime1 + ":00", uId, username);
                    }

                } else if (rdateId1 == 3) {
                    if (!reasonsddl3.equals("Others")) {
                        reasons3 = reasonsddl3;
                    }
                    date = sdate3 + " " + stime3 + ":00";
                    String date1 = "";
                    date1 = edate1 + " " + etime1 + ":00";
                    expecteddate = crewrotation.getDateAfter(date, 1, noofdays, "dd-MMM-yyyy", "yyyy-MM-dd");

                    crewrotation.updatesignonoffbyId(crewrotationId, frm.getSignonoffId(), uId, crewrotation.changeDate1(edate1) + " " + etime1 + ":00", crewrotation.changeDate1(sdate3) + " " + stime3 + ":00", reasons3, rdateId1,frm.getPositionId());///need tochange
                    crewrotation.updatecrActivitysignoffbyId(crewrotationId, frm.getCractivityId(), uId, sdate3, reasons3, rdateId1, sdate3, edate1, infoOS.getOverstaydays());

                    int MaxId = crewrotation.getMaxSignonoffId(crewrotationId);
                    if (MaxId == frm.getSignonoffId()) {
                        crewrotation.updatecrewrotationsignoff(crewrotationId, date, expecteddate + " " + etime1 + ":00", uId, username);
                    }
                }

                String todateindex = frm.getTodateindex();
                frm.setTodateindex(todateindex);
                String fromdateindex = frm.getFromdateindex();
                frm.setFromdateindex(fromdateindex);
                int activityIdindex = frm.getActivityIdindex();
                frm.setActivityIdindex(activityIdindex);
                Collection positions = crewrotation.getCrewPositionList(crewrotationId);
                frm.setPositions(positions);
                int positionId2 = frm.getPositionId2();
                CrewrotationInfo info = crewrotation.getCrewrotationBycrewrotationId(crewrotationId);
                request.getSession().setAttribute("ACTIVITYCRINFO", info);
                ArrayList list = crewrotation.getCrewActivityListBycrewrotationId(fromdateindex, todateindex, activityIdindex, crewrotationId, positionId2);
                request.getSession().setAttribute("CRACTIVITYLIST", list);
                return mapping.findForward("crewrotation_activity");
            }

            CrewrotationInfo info = crewrotation.getByclientandAssetName(search, clientId, assetId, countryId);
            request.getSession().setAttribute("CREWROTATIONINFO", info);
            searchcr = frm.getSearchcr();
            String crdate = frm.getCrdate();
            int statusindex = frm.getStatusindex();
            int activityDropdown = frm.getActivityDropdown();
            frm.setActivityDropdown(activityDropdown);
            int positionIndex = frm.getPositionIdIndex();
            frm.setStatusindex(statusindex);
            int dynamicId = frm.getDynamicId();
            frm.setDynamicId(dynamicId);
            Collection positions = crewrotation.getPositionList(assetId);
            frm.setPositions(positions);
            ArrayList list = crewrotation.getCandidatesByclientandAssetName(searchcr, clientId, assetId, countryId, crdate, statusindex, positionIndex, activityDropdown, dynamicId);

            request.getSession().setAttribute("CRCANDIDATESLIST", list);
            return mapping.findForward("crewrotation_details");

        } else if (frm.getDoSaveSignon() != null && frm.getDoSaveSignon().equals("yes")) {
            frm.setDoSaveSignon("no");
            print(this, " getDoSaveSignon block :: ");
            int clientId = frm.getClientId();
            int assetId = frm.getClientassetId();
            int crewrotationId = frm.getCrewrotationId();
            int rdateId = frm.getRdateId();
            int noofdays = frm.getNoofdays();
            int crewrota = frm.getHdnCrewRota();
            int p2positionId = frm.getP2positionId();
            int positionIdEdit = frm.getPositionIdEdit();
            int pid = frm.getPositionIdActivity();
            String edate = frm.getEdate();
            String etime = frm.getEtime();

            String date = "", expecteddate = "";
            String sdate = frm.getSdate();
            String stime = frm.getStime();
            String siremarks = frm.getSiremarks();

            String reasons = frm.getReasons() != null ? frm.getReasons() : "";
            String reasons1 = frm.getReasons1() != null ? frm.getReasons1() : "";

            String sdate1 = frm.getSdate1();
            String stime1 = frm.getStime1();

            String reasonsddl = frm.getReasonsDDL() != null ? frm.getReasonsDDL() : "";
            String reasonsddl1 = frm.getReasonsDDL1() != null ? frm.getReasonsDDL1() : "";
            
            if(p2positionId > 0)
            {
                noofdays = crewrotation.getNooffDaysForposition(p2positionId);
            }else{
                noofdays = crewrotation.getNooffDaysForposition(positionIdEdit); 
            } 
            CrewrotationInfo infoOS = crewrotation.getCrewrotationBycrewrotationId(crewrotationId);
            
            if (frm.getSignonoffId() <= 0) 
            {
                if (rdateId == 1) {
                    date = edate + " " + etime + ":00";
                    expecteddate = crewrotation.getDateAfter(date, 1, noofdays, "dd-MMM-yyyy", "yyyy-MM-dd");
                    int signonId = crewrotation.savesignonoff(crewrotationId, crewrotation.changeDatesignonoff(date), rdateId, reasons, "0000-00-00 00:00:00", 1, uId, crewrota, p2positionId);
                    crewrotation.updatecrewrotationsignon(crewrotationId, date, (crewrota == 2 ? "0000-00-00 00:00:00" : expecteddate + " " + etime + ":00"), uId, rdateId, username);
                    crewrotation.insertcrewActivitysignon(crewrotationId, edate, (crewrota == 2 ? "0000-00-00" : expecteddate), 6, reasons, uId, 2, signonId, infoOS.getNoofdays(), infoOS.getOverstaydays(), p2positionId);
                } else if (rdateId == 2 || rdateId == 3) {
                    if (!reasonsddl.equals("Others")) {
                        reasons = reasonsddl;
                    }
                    date = sdate + " " + stime + ":00";
                    String date1 = "";
                    date1 = edate + " " + etime + ":00";
                    expecteddate = crewrotation.getDateAfter(date, 1, noofdays, "dd-MMM-yyyy", "yyyy-MM-dd");
                    int signonId = crewrotation.savesignonoff(crewrotationId, crewrotation.changeDatesignonoff(date1), rdateId, reasons, crewrotation.changeDatesignonoff(date), 1, uId, crewrota, p2positionId);
                    crewrotation.updatecrewrotationsignon(crewrotationId, date, expecteddate + " " + etime + ":00", uId, rdateId, username);
                    crewrotation.insertcrewActivitysignon(crewrotationId, sdate, (crewrota == 2 ? "0000-00-00" : expecteddate), 6, reasons, uId, 2, signonId, infoOS.getNoofdays(), infoOS.getOverstaydays(), p2positionId);
                }
                if (crewrota != 2 && p2positionId == infoOS.getPositionId()) {
                    crewrotation.createplan(crewrotationId);
                }
                request.setAttribute("CRID", crewrotationId + "");
            } else if (frm.getSignonoffId() > 0) 
            {
                if (rdateId == 1) {
                    date = edate + " " + etime + ":00";
                    expecteddate = crewrotation.getDateAfter(date, 1, noofdays, "dd-MMM-yyyy", "yyyy-MM-dd");
                    crewrotation.updatesignonoffbyId(crewrotationId, frm.getSignonoffId(), uId, crewrotation.changeDate1(edate) + " " + etime + ":00", "", siremarks, rdateId, positionIdEdit);

                    CrewrotationInfo crainfo = crewrotation.getActivitydetailbycractivityId(crewrotationId, frm.getCractivityId());
                    if (crainfo.getSignoffId() > 0) {
                        crewrotation.updatecrActivitysignonbyId(crewrotationId, frm.getCractivityId(), uId, edate, expecteddate, siremarks, positionIdEdit);
                    } else {

                        crewrotation.updatecrActivitysignon1byId(crewrotationId, frm.getCractivityId(), uId, edate, siremarks, positionIdEdit);
                    }
                    int MaxId = crewrotation.getMaxSignonoffId(crewrotationId);
                    if (MaxId == frm.getSignonoffId()) {
                        crewrotation.updatecrewrotationsignon(crewrotationId, date, expecteddate + " " + etime + ":00", uId, rdateId, username);
                    }
                } else if (rdateId == 2) {
                    if (!reasonsddl1.equals("Others")) {
                        reasons = reasonsddl1;
                    }
                    date = sdate + " " + stime + ":00";
                    String date1 = "";
                    date1 = edate + " " + etime + ":00";
                    expecteddate = crewrotation.getDateAfter(date, 1, noofdays, "dd-MMM-yyyy", "yyyy-MM-dd");

                    crewrotation.updatesignonoffbyId(crewrotationId, frm.getSignonoffId(), uId, crewrotation.changeDate1(edate) + " " + etime + ":00", crewrotation.changeDate1(sdate) + " " + stime + ":00", reasons, rdateId, positionIdEdit);//date1 and date swaped

                    CrewrotationInfo crainfo = crewrotation.getActivitydetailbycractivityId(crewrotationId, frm.getCractivityId());
                    if (crainfo.getSignoffId() > 0) {
                        crewrotation.updatecrActivitysignonbyId(crewrotationId, frm.getCractivityId(), uId, sdate, expecteddate, reasons, positionIdEdit);
                        crewrotation.updatesignonoffbyIdModify(crewrotationId, crainfo.getSignoffId(), uId, expecteddate + " " + stime + ":00", "", reasons1, rdateId);//change from date in signonoff table signoff record

                    } else {
                        crewrotation.updatecrActivitysignon1byId(crewrotationId, frm.getCractivityId(), uId, sdate, reasons, positionIdEdit);
                    }

                    int MaxId = crewrotation.getMaxSignonoffId(crewrotationId);
                    if (MaxId == frm.getSignonoffId()) {
                        crewrotation.updatecrewrotationsignon(crewrotationId, date, expecteddate + " " + stime + ":00", uId, rdateId, username);
                    }
                } else if (rdateId == 3) {
                    if (!reasonsddl1.equals("Others")) {
                        reasons1 = reasonsddl1;
                    }
                    date = sdate1 + " " + stime1 + ":00";
                    String date1 = "";
                    date1 = edate + " " + etime + ":00";
                    expecteddate = crewrotation.getDateAfter(date, 1, noofdays, "dd-MMM-yyyy", "yyyy-MM-dd");

                    crewrotation.updatesignonoffbyId(crewrotationId, frm.getSignonoffId(), uId, crewrotation.changeDate1(edate) + " " + etime + ":00", crewrotation.changeDate1(sdate1) + " " + stime1 + ":00", reasons1, rdateId, frm.getPositionId());//date1 and date swaped

                    CrewrotationInfo crainfo = crewrotation.getActivitydetailbycractivityId(crewrotationId, frm.getCractivityId());
                    if (crainfo.getSignoffId() > 0) {
                        crewrotation.updatecrActivitysignonbyId(crewrotationId, frm.getCractivityId(), uId, sdate1, expecteddate, reasons1, positionIdEdit);
                        crewrotation.updatesignonoffbyIdModify(crewrotationId, crainfo.getSignoffId(), uId, expecteddate + " " + stime1 + ":00", "", reasons1, rdateId);//change from date in signonoff table signoff record
                    } else {
                        crewrotation.updatecrActivitysignon1byId(crewrotationId, frm.getCractivityId(), uId, sdate1, reasons1, positionIdEdit);
                    }

                    int MaxId = crewrotation.getMaxSignonoffId(crewrotationId);
                    if (MaxId == frm.getSignonoffId()) {
                        crewrotation.updatecrewrotationsignon(crewrotationId, date, expecteddate + " " + stime1 + ":00", uId, rdateId, username);
                    }
                }

                String todateindex = frm.getTodateindex();
                frm.setTodateindex(todateindex);
                String fromdateindex = frm.getFromdateindex();
                frm.setFromdateindex(fromdateindex);
                int activityIdindex = frm.getActivityIdindex();
                frm.setActivityIdindex(activityIdindex);
                Collection positions = crewrotation.getCrewPositionList(crewrotationId);
                frm.setPositions(positions);
                int positionId2 = frm.getPositionId2();
                CrewrotationInfo info = crewrotation.getCrewrotationBycrewrotationId(crewrotationId);
                request.getSession().setAttribute("ACTIVITYCRINFO", info);
                ArrayList list = crewrotation.getCrewActivityListBycrewrotationId(fromdateindex, todateindex, activityIdindex, crewrotationId, positionId2);
                request.getSession().setAttribute("CRACTIVITYLIST", list);
                return mapping.findForward("crewrotation_activity");

            }
            CrewrotationInfo info = crewrotation.getByclientandAssetName(search, clientId, assetId, countryId);
            request.getSession().setAttribute("CREWROTATIONINFO", info);
            searchcr = frm.getSearchcr();
            String crdate = frm.getCrdate();
            int statusindex = frm.getStatusindex();
            int activityDropdown = frm.getActivityDropdown();
            frm.setActivityDropdown(activityDropdown);
            int positionIndex = frm.getPositionIdIndex();
            Collection positions = crewrotation.getPositionList(assetId);
            frm.setPositions(positions);
            frm.setStatusindex(statusindex);
            int dynamicId = frm.getDynamicId();
            frm.setDynamicId(dynamicId);
            ArrayList list = crewrotation.getCandidatesByclientandAssetName(searchcr, clientId, assetId, countryId, crdate, statusindex, positionIndex, activityDropdown, dynamicId);

            request.getSession().setAttribute("CRCANDIDATESLIST", list);
            return mapping.findForward("crewrotation_details");

        } else if (frm.getDoSavereqdoc() != null && frm.getDoSavereqdoc().equals("yes")) {
            frm.setDoSavereqdoc("no");
            int clientId = frm.getClientId();
            int assetId = frm.getClientassetId();
            int crewrotationId = frm.getCrewrotationId();
            String docdata[] = crewrotation.getData(frm.getDocIds(), frm.getDdlhidden());

            crewrotation.insertdoc(crewrotationId, docdata, uId);
            crewrotation.updatecrewrotationcflag(crewrotationId, 2, uId);
            if (frm.getNoofdays() != -1) {
                request.setAttribute("REQUIREDDOCUMENTSAVE", "yes");
            }
            CrewrotationInfo info = crewrotation.getByclientandAssetName(search, clientId, assetId, countryId);
            request.getSession().setAttribute("CREWROTATIONINFO", info);
            searchcr = frm.getSearchcr();
            String crdate = frm.getCrdate();
            int statusindex = frm.getStatusindex();
            int activityDropdown = frm.getActivityDropdown();
            frm.setActivityDropdown(activityDropdown);
            int positionIndex = frm.getPositionIdIndex();
            Collection positions = crewrotation.getPositionList(assetId);
            frm.setPositions(positions);
            frm.setStatusindex(statusindex);
            int dynamicId = frm.getDynamicId();
            frm.setDynamicId(dynamicId);
            ArrayList list = crewrotation.getCandidatesByclientandAssetName(searchcr, clientId, assetId, countryId, crdate, statusindex, positionIndex, activityDropdown, dynamicId);
            request.getSession().setAttribute("CRCANDIDATESLIST", list);
            return mapping.findForward("crewrotation_details");
        } else if (frm.getDoDelete() != null && frm.getDoDelete().equals("yes")) {
            frm.setDoDelete("no");
            int crewrotationId = frm.getCrewrotationId();
            String todateindex = frm.getTodateindex();
            frm.setTodateindex(todateindex);
            String fromdateindex = frm.getFromdateindex();
            frm.setFromdateindex(fromdateindex);
            int activityIdindex = frm.getActivityIdindex();
            frm.setActivityIdindex(activityIdindex);
            int rota = frm.getRota();
            frm.setRota(rota);
            int activityId = frm.getCractivityId();
            crewrotation.deleteCrActivity(activityId, crewrotationId, uId, username, rota);
            Collection positions = crewrotation.getCrewPositionList(crewrotationId);
            frm.setPositions(positions);
            int positionId2 = frm.getPositionId2();
            CrewrotationInfo info = crewrotation.getCrewrotationBycrewrotationId(crewrotationId);
            request.getSession().setAttribute("ACTIVITYCRINFO", info);
            ArrayList list = crewrotation.getCrewActivityListBycrewrotationId(fromdateindex, todateindex, activityIdindex, crewrotationId, positionId2);
            request.getSession().setAttribute("CRACTIVITYLIST", list);
            return mapping.findForward("crewrotation_activity");
        } else if (frm.getDoSummary() != null && frm.getDoSummary().equals("yes")) {
            frm.setDoSummary("no");
            int crewId = frm.getCrewId();
            int crewrotationId = frm.getCrewrotationId();
            if(crewId > 0)
            {
                crewrotationId = crewId;
            }
            String todateindex = frm.getTodateindex();
            frm.setTodateindex(todateindex);
            String fromdateindex = frm.getFromdateindex();
            frm.setFromdateindex(fromdateindex);
            int activityIdindex = frm.getActivityIdindex();
            frm.setActivityIdindex(activityIdindex);
            Collection positions = crewrotation.getCrewPositionList(crewrotationId);
            frm.setPositions(positions);
            int positionId = frm.getPositionId2();
            CrewrotationInfo info = crewrotation.getCrewrotationBycrewrotationId(crewrotationId);
            request.getSession().setAttribute("ACTIVITYCRINFO", info);
            ArrayList list = crewrotation.getCrewActivityListBycrewrotationId(fromdateindex, todateindex, activityIdindex, crewrotationId, positionId);
            request.getSession().setAttribute("CRACTIVITYLIST", list);
            return mapping.findForward("crewrotation_activity");
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
            ArrayList jobpostList = crewrotation.getClientSelectByName(search, statusIndex, next, count, clientIdIndex, assetIdIndex, countryId, allclient, permission, cids, assetids);
            int cnt = 0;
            if (jobpostList.size() > 0) {
                CrewrotationInfo cinfo = (CrewrotationInfo) jobpostList.get(jobpostList.size() - 1);
                cnt = cinfo.getJobpostId();
                jobpostList.remove(jobpostList.size() - 1);
            }
            request.getSession().setAttribute("CROTATION_LIST", jobpostList);
            request.getSession().setAttribute("COUNT_LIST", cnt + "");
            request.getSession().setAttribute("NEXT", next + "");
            request.getSession().setAttribute("NEXTVALUE", (next + 1) + "");
            return mapping.findForward("display");
        } else if (frm.getDoPlanning() != null && frm.getDoPlanning().equals("yes")) {
            frm.setDoPlanning("no");
            int clientId = frm.getClientId();
            int assetId = frm.getClientassetId();
            frm.setClientId(clientId);
            frm.setClientassetId(assetId);
            String searchp = frm.getSearchp();
            String fromDate = frm.getFromDate();
            frm.setFromDate(fromDate);
            String toDate = frm.getToDate();
            frm.setToDate(toDate);
            int statusPlan = frm.getStatusPlan();
            frm.setStatusPlan(statusPlan);
            int positionIdPlan = frm.getPositionIdPlan();
            Collection positions = crewrotation.getPositionList(assetId);
            frm.setPositions(positions);
            frm.setPositionIdPlan(positionIdPlan);

            ArrayList list = crewrotation.getPlanningByclientandAssetName(clientId, assetId, searchp, fromDate, toDate, positionIdPlan, statusPlan);
            request.getSession().setAttribute("PLCANDIDATESLIST", list);
            return mapping.findForward("indexplan");
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

            ArrayList jobpostList = crewrotation.getClientSelectByName(search, statusIndex, 0, count, clientIdIndex, assetIdIndex, countryId, allclient, permission, cids, assetids);
            int cnt = 0;
            if (jobpostList.size() > 0) {
                CrewrotationInfo cinfo = (CrewrotationInfo) jobpostList.get(jobpostList.size() - 1);
                cnt = cinfo.getCrewrotationId();
                jobpostList.remove(jobpostList.size() - 1);
            }
            request.getSession().setAttribute("CROTATION_LIST", jobpostList);
            request.getSession().setAttribute("COUNT_LIST", cnt + "");
            request.getSession().setAttribute("NEXT", "0");
            request.getSession().setAttribute("NEXTVALUE", "1");
            return mapping.findForward("display");
        }
    }
}
