package com.web.jxp.cassessment;

import com.web.jxp.assettype.Assettype;
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

public class CassessmentAction extends Action 
{
    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

        CassessmentForm frm = (CassessmentForm) form;
        Cassessment cassessment = new Cassessment();
        Assettype assettype = new Assettype();
        
        int count = cassessment.getCount();
        String search = frm.getSearch() != null ? frm.getSearch() : "";
        frm.setSearch(search);
        int vstatusIndex = frm.getVstatusIndex();
        frm.setVstatusIndex(vstatusIndex);
        int astatusIndex = frm.getAstatusIndex();
        frm.setAstatusIndex(astatusIndex);
        int uId = 0, assessor = 0;
        String permission = "N",loginUsername = "";
        if (request.getSession().getAttribute("LOGININFO") != null) {
            UserInfo uInfo = (UserInfo) request.getSession().getAttribute("LOGININFO");
            if (uInfo != null) 
            {
                permission = uInfo.getPermission() != null ? uInfo.getPermission(): "";
                uId = uInfo.getUserId();
                assessor = uInfo.getAssessor();
                loginUsername = uInfo.getName();
            }
        }
        int assessorIdn = 0;
        if (assessor > 0) {
            assessorIdn = uId;
        } else if (permission.equals("Y")) {
            assessorIdn = -1;
        }
        int check_user = cassessment.checkUserSession(request, 14, permission);
        if (check_user == -1) {
            return mapping.findForward("default");
        } else if (check_user == -2) {
            String authmess = cassessment.getMainPath("user_auth");
            authmess = authmess.replaceAll("__MODULE__", "Cassessment Database Module");
            request.setAttribute("AUTHMESSAGE", authmess);
            return mapping.findForward("auth");
        }
        Collection positions = cassessment.getPositions();
        frm.setPosition(positions);
        int positionIndex = frm.getPositionIndex();
        frm.setPositionIndex(positionIndex);
        Collection assettypes = assettype.getAssettypes();
        frm.setAssettypes(assettypes);
        int assettypeIndex = frm.getAssessorIndex();
        frm.setAssettypeIndex(assettypeIndex);
        
        if (frm.getDoView() != null && frm.getDoView().equals("yes")) {
            frm.setDoView("no");

            int candidateId = frm.getCandidateId();
            frm.setCandidateId(candidateId);

            CassessmentInfo info = cassessment.getCandidatePositionDetails(candidateId);
            request.getSession().setAttribute("CANDIDATEPOSITION_INFO", info);
            if (info != null) {
                int positionId = info.getPositionId();

                ArrayList list = cassessment.getAssessmentList(candidateId, positionId);
                request.getSession().setAttribute("ASSESSMENT_LIST", list);
            }
            return mapping.findForward("view_cassessment");

        } else if (frm.getDoRetake() != null && frm.getDoRetake().equals("yes")) {
            frm.setDoRetake("no");

            int candidateId = frm.getCandidateId();
            frm.setCandidateId(candidateId);
            int caId = frm.getCassessmentId();
            int paId = frm.getpAssessmentId();
            frm.setCassessmentId(-1);
            int iattemptcnt = cassessment.getRetakeAssessmentById(caId, candidateId);
            frm.setAttemptCount(iattemptcnt);

            CassessmentInfo info = cassessment.getCandidatePositionDetails(candidateId);
            request.getSession().setAttribute("CANDIDATEPOSITION_INFO", info);
            if (info != null) {
                int positionId = info.getPositionId();
                ArrayList list = cassessment.getAssessmentList(candidateId, positionId);
                request.getSession().setAttribute("ASSESSMENT_LIST", list);
            }

            request.getSession().setAttribute("PAID", ("" + paId));
            request.setAttribute("VIEW", "onview");
            return mapping.findForward("view_cassessment");

        } else if (frm.getDoAssessorView() != null && frm.getDoAssessorView().equals("yes")) {
            frm.setDoAssessorView("no");

            int candidateId = frm.getCandidateId();
            frm.setCandidateId(candidateId);
            int cassessmentId = frm.getCassessmentId();
            frm.setCassessmentId(cassessmentId);

            CassessmentInfo info = cassessment.getCandidatePositionDetails(candidateId);
            request.getSession().setAttribute("CANDIDATEPOSITION_INFO", info);
            ArrayList list = cassessment.getAssessmentAssessorList(cassessmentId);
            request.getSession().setAttribute("ASSESSMENTASSESSOR_LIST", list);
            return mapping.findForward("view_Assessor");

        } else if (frm.getDoCancel() != null && frm.getDoCancel().equals("yes")) {
            frm.setDoCancel("no");
            print(this, "doCancel block");

            int next = 0;
            if (request.getSession().getAttribute("NEXTVALUE") != null) {
                next = Integer.parseInt((String) request.getSession().getAttribute("NEXTVALUE"));
            }
            next = next - 1;
            if (next < 0) {
                next = 0;
            }

            ArrayList cassessmentList = cassessment.getCassessmentByName(search, vstatusIndex, astatusIndex, positionIndex, assettypeIndex, 0, count);
            int cnt = 0;
            if (cassessmentList.size() > 0) {
                CassessmentInfo cinfo = (CassessmentInfo) cassessmentList.get(cassessmentList.size() - 1);
                cnt = cinfo.getCassessmentId();
                cassessmentList.remove(cassessmentList.size() - 1);
            }
            request.getSession().setAttribute("CASSESSMENT_LIST", cassessmentList);
            request.getSession().setAttribute("COUNT_LIST", cnt + "");
            request.getSession().setAttribute("NEXT", "0");
            request.getSession().setAttribute("NEXTVALUE", "1");
            return mapping.findForward("display");
        } else if (frm.getDoSave() != null && frm.getDoSave().equals("yes")) {
            frm.setDoSave("no");
            print(this, "getDoSave block." + isTokenValid(request));

            int cassessmentId = frm.getCassessmentId();

            int candidateId = frm.getCandidateId();
            frm.setCandidateId(candidateId);
            int passId = frm.getpAssessmentId();
            int attemptcount = frm.getAttemptCount();
            int assessorId = frm.getSelAssessor();
            String linkloc = frm.getTxtLocLink();
            String mode = frm.getSelMode();
            int tz1 = frm.getSelCandTimeZone();
            int tz2 = frm.getSelAssessTimeZone();
            int duration = frm.getSelDuration();
            String date = frm.getTxtstartdate();
            String time = frm.getTxttime();
            date += " " + time;
            int status = 1;
            String datec = frm.getTxtDate();

            CassessmentInfo info = new CassessmentInfo(cassessmentId, candidateId, passId, assessorId, linkloc, mode, tz1, tz2, duration, date, datec,
                    status, uId, attemptcount);
            if (cassessmentId <= 0) {
                int check = cassessment.checkDuplicacy(candidateId, passId);
                if (check > 0) {
                    
                    CassessmentInfo linfo = cassessment.getCandidatePositionDetails(candidateId);
                    request.getSession().setAttribute("CANDIDATEPOSITION_INFO", linfo);
                    if (linfo != null) {
                        int positionId = linfo.getPositionId();
                        ArrayList list = cassessment.getAssessmentList(candidateId, positionId);
                        request.getSession().setAttribute("ASSESSMENT_LIST", list);
                    }
                } else {
                    int cc = cassessment.createCassessment(info,loginUsername);
                    if (cc > 0) {
                        request.setAttribute("MESSAGE", "Data added successfully.");
                        print(this, "Insert Query");
                        frm.setCassessmentId(cc);
                        cassessment.createCassessmentHistory(cc, info, "Scheduled");
                        CassessmentInfo linfo = cassessment.getCandidatePositionDetails(candidateId);
                        request.getSession().setAttribute("CANDIDATEPOSITION_INFO", linfo);
                        if (linfo != null) {
                            int positionId = linfo.getPositionId();
                            ArrayList list = cassessment.getAssessmentList(candidateId, positionId);
                            request.getSession().setAttribute("ASSESSMENT_LIST", list);
                        }
                    }
                }
                request.getSession().setAttribute("PAID", ("" + passId));
                request.setAttribute("VIEW", "onview");
                return mapping.findForward("view_cassessment");
            } else {
                frm.setCassessmentId(cassessmentId);
                cassessment.updateCassessment(info,loginUsername);
                request.setAttribute("MESSAGE", "Data updated successfully.");
                print(this, "Update Query.");
                cassessment.createCassessmentHistory(cassessmentId, info, "Rescheduled");
                CassessmentInfo linfo = cassessment.getCandidatePositionDetails(candidateId);
                request.getSession().setAttribute("CANDIDATEPOSITION_INFO", linfo);
                if (linfo != null) {
                    int positionId = linfo.getPositionId();
                    ArrayList list = cassessment.getAssessmentList(candidateId, positionId);
                    request.getSession().setAttribute("ASSESSMENT_LIST", list);
                }
                request.getSession().setAttribute("PAID", ("" + passId));
                request.setAttribute("VIEW", "onview");
                return mapping.findForward("view_cassessment");
            }
        } else if (frm.getDoAssessorSave() != null && frm.getDoAssessorSave().equals("yes")) {
            frm.setDoAssessorSave("no");
            print(this, "getDoAssessorSave block." + isTokenValid(request));

            int cassessmentId = frm.getCassessmentId();
            frm.setCassessmentId(cassessmentId);
            int candidateId = frm.getCandidateId();
            frm.setCandidateId(candidateId);
            int parameterId[] = frm.getHdnParameterId();
            double marks[] = frm.getMarks();
            double avgMarks = frm.getHdnmarks();
            int minscore = frm.getHdnminScore();
            int passflag = frm.getHdnpassFlag();
            int paid = frm.getpAssessmentId();
            int assessorid = frm.getAssessorId();
            int coordinatorid = frm.getCoordinatorId();
            String remark = frm.getTxtremarks();
            frm.setSelectRole(2);

            Collection passessments = cassessment.getPositionAssessments(assessorIdn);
            frm.setPassessments(passessments);
            int passessmentIndex = frm.getPassessmentIndex();
            frm.setPassessmentIndex(passessmentIndex);

            Collection assessors = cassessment.getAssessors();
            frm.setAssessors(assessors);
            int assessorIndex = frm.getAssessorIndex();
            frm.setAssessorIndex(assessorIndex);

            Collection aPositions = cassessment.getPositionsforAssessor(assessorIdn);
            frm.setaPositions(aPositions);
            int aPositionIndex = frm.getaPositionIndex();
            frm.setaPositionIndex(aPositionIndex);

            cassessment.createAssessorScore(candidateId, cassessmentId, parameterId, marks, avgMarks, minscore, passflag, uId, remark, paid, assessorid, coordinatorid, permission);

            ArrayList assessorList = cassessment.getCassessmentByNameforAssessor(search, aPositionIndex, passessmentIndex, assessorIndex, assessorIdn, assettypeIndex, 0, count);
            int cnt = 0;
            if (assessorList.size() > 0) {
                CassessmentInfo cinfo = (CassessmentInfo) assessorList.get(assessorList.size() - 1);
                cnt = cinfo.getCassessmentId();
                assessorList.remove(assessorList.size() - 1);
            }
            request.getSession().setAttribute("ASSESSOR_LIST", assessorList);
            request.getSession().setAttribute("COUNT_LISTA", cnt + "");
            request.getSession().setAttribute("NEXTA", "0");
            request.getSession().setAttribute("NEXTVALUEA", "1");
            return mapping.findForward("display_assessor");

        } else if (frm.getDoSaveScore() != null && frm.getDoSaveScore().equals("yes")) {
            frm.setDoSaveScore("no");
            print(this, "getDoSaveScore block." + isTokenValid(request));

            int cassessmentId = frm.getCassessmentId();
            frm.setCassessmentId(cassessmentId);
            int candidateId = frm.getCandidateId();
            frm.setCandidateId(candidateId);
            int passId = frm.getpAssessmentId();
            int parameterId[] = frm.getHdnParameterId();
            double marks[] = frm.getMarks();
            double avgMarks = frm.getHdnmarks();
            int minscore = frm.getHdnminScore();
            int passflag = frm.getHdnpassFlag();
            int paid = frm.getpAssessmentId();
            int assessorid = frm.getAssessorId();
            int coordinatorid = frm.getCoordinatorId();
            String remark = frm.getTxtremarks();

            cassessment.createAssessorScore(candidateId, cassessmentId, parameterId, marks, avgMarks, minscore, passflag, uId, remark, paid, assessorid, coordinatorid, permission);

            CassessmentInfo linfo = cassessment.getCandidatePositionDetails(candidateId);
            request.getSession().setAttribute("CANDIDATEPOSITION_INFO", linfo);
            if (linfo != null) {
                int positionId = linfo.getPositionId();
                ArrayList list = cassessment.getAssessmentList(candidateId, positionId);
                request.getSession().setAttribute("ASSESSMENT_LIST", list);
            }
            request.getSession().setAttribute("PAID", ("" + passId));
            request.setAttribute("VIEW", "onview");
            return mapping.findForward("view_cassessment");

        } else if (frm.getDoChange() != null && frm.getDoChange().equals("yes")) {
            frm.setDoChange("no");
            int role = frm.getSelectRole();
            if (role == 1) {

                ArrayList cassessmentList = cassessment.getCassessmentByName(search, vstatusIndex, astatusIndex, positionIndex, assettypeIndex, 0, count);
                int cnt = 0;
                if (cassessmentList.size() > 0) {
                    CassessmentInfo cinfo = (CassessmentInfo) cassessmentList.get(cassessmentList.size() - 1);
                    cnt = cinfo.getCassessmentId();
                    cassessmentList.remove(cassessmentList.size() - 1);
                }
                request.getSession().setAttribute("CASSESSMENT_LIST", cassessmentList);
                request.getSession().setAttribute("COUNT_LIST", cnt + "");
                request.getSession().setAttribute("NEXT", "0");
                request.getSession().setAttribute("NEXTVALUE", "1");
                return mapping.findForward("display");
            } else if (role == 2) {

                Collection passessments = cassessment.getPositionAssessments(assessorIdn);
                frm.setPassessments(passessments);
                int passessmentIndex = frm.getPassessmentIndex();
                frm.setPassessmentIndex(passessmentIndex);

                Collection assessors = cassessment.getAssessors();
                frm.setAssessors(assessors);
                int assessorIndex = frm.getAssessorIndex();
                frm.setAssessorIndex(assessorIndex);

                Collection aPositions = cassessment.getPositionsforAssessor(assessorIdn);
                frm.setaPositions(aPositions);
                int aPositionIndex = frm.getaPositionIndex();
                frm.setaPositionIndex(aPositionIndex);

                ArrayList assessorList = cassessment.getCassessmentByNameforAssessor(search, aPositionIndex, passessmentIndex, assessorIndex, assessorIdn, assettypeIndex, 0, count);
                int cnt = 0;
                if (assessorList.size() > 0) {
                    CassessmentInfo cinfo = (CassessmentInfo) assessorList.get(assessorList.size() - 1);
                    cnt = cinfo.getCassessmentId();
                    assessorList.remove(assessorList.size() - 1);
                }
                request.getSession().setAttribute("ASSESSOR_LIST", assessorList);
                request.getSession().setAttribute("COUNT_LISTA", cnt + "");
                request.getSession().setAttribute("NEXTA", "0");
                request.getSession().setAttribute("NEXTVALUEA", "1");
                return mapping.findForward("display_assessor");
            }

        } else if (frm.getDoCancelAssessor() != null && frm.getDoCancelAssessor().equals("yes")) {
            frm.setDoCancelAssessor("no");

            frm.setSelectRole(2);
            Collection passessments = cassessment.getPositionAssessments(assessorIdn);
            frm.setPassessments(passessments);
            int passessmentIndex = frm.getPassessmentIndex();
            frm.setPassessmentIndex(passessmentIndex);

            Collection assessors = cassessment.getAssessors();
            frm.setAssessors(assessors);
            int assessorIndex = frm.getAssessorIndex();
            frm.setAssessorIndex(assessorIndex);

            Collection aPositions = cassessment.getPositionsforAssessor(assessorIdn);
            frm.setaPositions(aPositions);
            int aPositionIndex = frm.getaPositionIndex();
            frm.setaPositionIndex(aPositionIndex);

            ArrayList assessorList = cassessment.getCassessmentByNameforAssessor(search, aPositionIndex, passessmentIndex, assessorIndex, assessorIdn, assettypeIndex, 0, count);
            int cnt = 0;
            if (assessorList.size() > 0) {
                CassessmentInfo cinfo = (CassessmentInfo) assessorList.get(assessorList.size() - 1);
                cnt = cinfo.getCassessmentId();
                assessorList.remove(assessorList.size() - 1);
            }
            request.getSession().setAttribute("ASSESSOR_LIST", assessorList);
            request.getSession().setAttribute("COUNT_LISTA", cnt + "");
            request.getSession().setAttribute("NEXTA", "0");
            request.getSession().setAttribute("NEXTVALUEA", "1");
            return mapping.findForward("display_assessor");

        } if (frm.getDoViewAssessNow()!= null && frm.getDoViewAssessNow().equals("yes")) {
            frm.setDoViewAssessNow("no");

            int candidateId = frm.getCandidateId();
            frm.setCandidateId(candidateId);

            CassessmentInfo info = cassessment.getCandidatePositionDetails(candidateId);
            request.getSession().setAttribute("CANDIDATEPOSITION_INFO", info);
            
                CassessmentInfo cinfo = cassessment.getAssessnowList(candidateId);
                if (cinfo != null) {
                    
                    frm.setRadio1(cinfo.getRadio1());
                    frm.setRadio2(cinfo.getRadio2());
                    frm.setRadio3(cinfo.getRadio3());
                    frm.setRadio4(cinfo.getRadio4());
                    frm.setRadio5(cinfo.getRadio5());
                    frm.setRadio6(cinfo.getRadio6());
                    frm.setRadio7(cinfo.getRadio7());
                    frm.setRadio8(cinfo.getRadio8());
                    frm.setRadio9(cinfo.getRadio9());
                    frm.setRadio10(cinfo.getRadio10());
                    frm.setRadio11(cinfo.getRadio11());
                    frm.setRadio12(cinfo.getRadio12());
                    frm.setRadio13(cinfo.getRadio13());
                    frm.setRadio14(cinfo.getRadio14());
                    frm.setRadio15(cinfo.getRadio15());
                    frm.setRadio16(cinfo.getRadio16());
                    frm.setRadio17(cinfo.getRadio17());
                    frm.setRadio18(cinfo.getRadio18());
                    frm.setCdescription(cinfo.getCdescription());
                    request.setAttribute("PASS_STATUS", cinfo.getRadio18()+"");
            }
            return mapping.findForward("view_assessnow");

        }if (frm.getDoSaveAssessNow()!= null && frm.getDoSaveAssessNow().equals("yes")) {
            frm.setDoSaveAssessNow("no");

            int candidateId = frm.getCandidateId();
            int positionId = frm.getPositionId();
            int radio1 = frm.getRadio1();
            int radio2 = frm.getRadio2();
            int radio3 = frm.getRadio3();
            int radio4 = frm.getRadio4();
            int radio5 = frm.getRadio5();
            int radio6 = frm.getRadio6();
            int radio7 = frm.getRadio7();
            int radio8 = frm.getRadio8();
            int radio9 = frm.getRadio9();
            int radio10 = frm.getRadio10();
            int radio11 = frm.getRadio11();
            int radio12 = frm.getRadio12();
            int radio13 = frm.getRadio13();
            int radio14 = frm.getRadio14();
            int radio15 = frm.getRadio15();
            int radio16 = frm.getRadio16();
            int radio17 = frm.getRadio17();
            int radio18 = frm.getRadio18();
            String cdescription = frm.getCdescription();
            CassessmentInfo dinfo = new CassessmentInfo(candidateId,radio1,radio2, radio3, radio4, radio5, radio6, radio7, radio8, radio9, radio10, radio11, radio12, radio13, radio14
            , radio15, radio16, radio17, radio18, cdescription, 1, positionId);
            cassessment.createAssessNow(dinfo, uId);
            if(radio18 == 2)
            {
                request.setAttribute("ASSESSTPP", "yes");
            }
            ArrayList cassessmentList = cassessment.getCassessmentByName(search, vstatusIndex, astatusIndex, positionIndex, assettypeIndex, 0, count);
            int cnt = 0;
            if (cassessmentList.size() > 0) {
                CassessmentInfo cinfo = (CassessmentInfo) cassessmentList.get(cassessmentList.size() - 1);
                cnt = cinfo.getCassessmentId();
                cassessmentList.remove(cassessmentList.size() - 1);
            }
            request.getSession().setAttribute("CASSESSMENT_LIST", cassessmentList);
            request.getSession().setAttribute("COUNT_LIST", cnt + "");
            request.getSession().setAttribute("NEXT", "0");
            request.getSession().setAttribute("NEXTVALUE", "1");

        } else if (assessorIdn > 0) {

            Collection passessments = cassessment.getPositionAssessments(assessorIdn);
            frm.setPassessments(passessments);
            int passessmentIndex = frm.getPassessmentIndex();
            frm.setPassessmentIndex(passessmentIndex);

            Collection assessors = cassessment.getAssessors();
            frm.setAssessors(assessors);
            int assessorIndex = frm.getAssessorIndex();
            frm.setAssessorIndex(assessorIndex);

            Collection aPositions = cassessment.getPositionsforAssessor(assessorIdn);
            frm.setaPositions(aPositions);
            int aPositionIndex = frm.getaPositionIndex();
            frm.setaPositionIndex(aPositionIndex);
            ArrayList assessorList = cassessment.getCassessmentByNameforAssessor(search, aPositionIndex, passessmentIndex, assessorIndex, assessorIdn, assettypeIndex, 0, count);
            int cnt = 0;
            if (assessorList.size() > 0) {
                CassessmentInfo cinfo = (CassessmentInfo) assessorList.get(assessorList.size() - 1);
                cnt = cinfo.getCassessmentId();
                assessorList.remove(assessorList.size() - 1);
            }
            request.getSession().setAttribute("ASSESSOR_LIST", assessorList);
            request.getSession().setAttribute("COUNT_LISTA", cnt + "");
            request.getSession().setAttribute("NEXTA", "0");
            request.getSession().setAttribute("NEXTVALUEA", "1");
            return mapping.findForward("display_assessor");
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

            ArrayList cassessmentList = cassessment.getCassessmentByName(search, vstatusIndex, astatusIndex, positionIndex, assettypeIndex, 0, count);
            int cnt = 0;
            if (cassessmentList.size() > 0) {
                CassessmentInfo cinfo = (CassessmentInfo) cassessmentList.get(cassessmentList.size() - 1);
                cnt = cinfo.getCassessmentId();
                cassessmentList.remove(cassessmentList.size() - 1);
            }
            request.getSession().setAttribute("CASSESSMENT_LIST", cassessmentList);
            request.getSession().setAttribute("COUNT_LIST", cnt + "");
            request.getSession().setAttribute("NEXT", "0");
            request.getSession().setAttribute("NEXTVALUE", "1");
        }
        return mapping.findForward("display");
    }
}
