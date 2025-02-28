package com.web.jxp.feedback;

import com.web.jxp.approvedby.Approvedby;
import com.web.jxp.base.Base;
import com.web.jxp.base.Validate;
import com.web.jxp.bloodpressure.Bloodpressure;
import com.web.jxp.candidate.Candidate;
import com.web.jxp.candidate.CandidateInfo;
import static com.web.jxp.common.Common.*;
import com.web.jxp.country.Country;
import com.web.jxp.coursetype.CourseType;
import com.web.jxp.crewlogin.CrewloginInfo;
import com.web.jxp.documentissuedby.Documentissuedby;
import com.web.jxp.talentpool.Talentpool;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

public class FeedbackAction extends Action {

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        FeedbackForm frm = (FeedbackForm) form;
        Feedback feedback = new Feedback();
        Candidate candidate = new Candidate();
        Validate validate = new Validate();
        Talentpool talentpool = new Talentpool();
        Documentissuedby documentissuedby = new Documentissuedby();
        Base base = new Base();
        Country country = new Country();
        Approvedby approvedby = new Approvedby();
        CourseType coursetype = new CourseType();
        Bloodpressure bloodpressure = new Bloodpressure();
        int uId = 0;
        int count = feedback.getCount();
        String search = frm.getSearch() != null && !frm.getSearch().equals("") ? frm.getSearch() : "";
        frm.setSearch(search);
        int status = frm.getStatus();
        frm.setStatus(status);
        String fromDate = "";
        String toDate = "";

        if (frm.getFromDate() != null && !frm.getFromDate().equals("")) {
            fromDate = frm.getFromDate();
            frm.setFromDate(fromDate);
        } else if (frm.getFromDate1() != null && !frm.getFromDate1().equals("")) {
            fromDate = frm.getFromDate1();
            frm.setFromDate1(fromDate);
        } else {
            frm.setFromDate(frm.getFromDate());
            frm.setFromDate1(frm.getFromDate1());
        }

        if (frm.getToDate() != null && !frm.getToDate().equals("")) {
            toDate = frm.getToDate();
            frm.setToDate(toDate);
        } else if (frm.getToDate1() != null && !frm.getToDate1().equals("")) {
            toDate = frm.getToDate1();
            frm.setToDate1(toDate);
        } else {
            frm.setToDate(frm.getToDate());
            frm.setToDate1(frm.getToDate1());
        }

        int crewrotationId = 0, positionId = 0, clientassetId = 0, candidateId = 0, clientId = 0;
        String crewName = "";
        if (request.getSession().getAttribute("CREWLOGIN") != null) 
        {
            CrewloginInfo info = (CrewloginInfo) request.getSession().getAttribute("CREWLOGIN");
            if (info != null) 
            {
                crewrotationId = info.getCrewrotationId();
                crewName = info.getName() != null ? info.getName() : "";
                positionId = info.getPositionId();
                clientassetId = info.getClientassetId();
                candidateId = info.getCandidateId();
            }
        }
//        if (crewrotationId <= 0) {
//            return mapping.findForward("crewlogindefault");
//        }
        if (candidateId <= 0) {
            return mapping.findForward("crewlogindefault");
        }
        if (frm.getDoLogout() != null && frm.getDoLogout().equals("yes")) {
            request.getSession().invalidate();
            return mapping.findForward("crewlogin");
        } else if (frm.getDoView() != null && frm.getDoView().equals("yes")) {
            frm.setDoView("no");
            print(this, "getDoView block");

            int surveyId = frm.getSurveyId();
            if (request.getSession().getAttribute("SURVEYID") != null) {
                surveyId = Integer.parseInt((String) request.getSession().getAttribute("SURVEYID"));
            }
            frm.setSurveyId(surveyId);
            FeedbackInfo info = feedback.getSurveyInfo(surveyId);
            request.setAttribute("SURVEYINFO", info);
            if (info != null) {
                ArrayList list = feedback.getQuestionList(info.getClientassetId(), info.getSubcategorywfId(), positionId,
                        surveyId, info.getStatus());
                feedback.getAnswer(info.getSrno(), info.getType(), info.getAnswer(), info.getQuestionId());
                request.getSession().setAttribute("QUEANS_LIST", list);
            }
            return mapping.findForward("view_feedback");
        }        
        
        //
       else if (frm.getDomodifygovdocumentdetail() != null && frm.getDomodifygovdocumentdetail().equals("yes")) {
            frm.setDomodifygovdocumentdetail("no");
            candidateId = frm.getCandidateId();
            int govdocumentId = frm.getGovdocumentId();
            frm.setCandidateId(candidateId);
            Collection documentTypes = feedback.getDocumentTypesForModule(1,1);
            frm.setDocumentTypes(documentTypes);
            frm.setCurrentDate(base.currDate3());
            Collection countries = country.getCountrys();
            frm.setCountries(countries);
            if (govdocumentId <= 0) {
                Collection documentissuedbys = documentissuedby.getDocumentissuedbys(-1);
                frm.setDocumentissuedbys(documentissuedbys);
            }
            frm.setFname("");
            frm.setGovdocumentId(govdocumentId);
            if (govdocumentId > 0) {
                CandidateInfo info = candidate.getcandgovForModify(govdocumentId);
                if (info != null) {
                    frm.setDocumentTypeId(info.getDocumenttypeId());
                    frm.setDocumentNo(info.getDocumentno());
                    frm.setCityName(info.getPlaceofissue());
                    frm.setPlaceofapplicationId(info.getPlaceofissueId());
                    Collection documentissuedbys = documentissuedby.getDocumentissuedbys(info.getDocumenttypeId());
                    frm.setDocumentissuedbys(documentissuedbys);
                    frm.setCountries(countries);
                    frm.setDocumentissuedbyId(info.getIssuedbyId());
                    if (info.getDateofissue() != null) {
                        frm.setDateofissue(info.getDateofissue());
                    }
                    if (info.getDateofexpiry() != null) {
                        frm.setDateofexpiry(info.getDateofexpiry());
                    }
                }
            }
            return mapping.findForward("modify_documentdetail");
       }
         else if (frm.getDoSavegovdocumentdetail() != null && frm.getDoSavegovdocumentdetail().equals("yes")) {
            frm.setDoSavegovdocumentdetail("no");
            int govdocumentId = frm.getGovdocumentId();
            frm.setGovdocumentId(govdocumentId);
            int documentTypeId = frm.getDocumentTypeId();
            String documentno = validate.replacedesc(frm.getDocumentNo());
            int cityId = frm.getPlaceofapplicationId();
            int DocumentIssuedbyId = frm.getDocumentissuedbyId();
            String dateofissue = validate.replacedate(frm.getDateofissue());
            String dateofexpiry = validate.replacedate(frm.getDateofexpiry());
            status = 1;
            if (documentTypeId <= 0 || DocumentIssuedbyId <= 0) {
                Collection documentTypes = feedback.getDocumentTypesForModule(1,1);
                Collection countries = country.getCountrys();
                frm.setDocumentTypes(documentTypes);
                frm.setCountries(countries);
                Collection documentissuedbys = documentissuedby.getDocumentissuedbys(documentTypeId);
                frm.setDocumentissuedbys(documentissuedbys);
                frm.setGovdocumentId(govdocumentId);
                request.setAttribute("MESSAGE", "Something went wrong.");
                return mapping.findForward("modify_documentdetail");
            }
            int ck = candidate.checkDuplicacygovdocument(candidateId, govdocumentId, documentTypeId);
            if (ck == 1) {
                Collection documentTypes = feedback.getDocumentTypesForModule(1,1);
                Collection countries = country.getCountrys();
                frm.setDocumentTypes(documentTypes);
                frm.setCountries(countries);
                Collection documentissuedbys = documentissuedby.getDocumentissuedbys(documentTypeId);
                frm.setDocumentissuedbys(documentissuedbys);
                frm.setGovdocumentId(govdocumentId);
                request.setAttribute("MESSAGE", "Document already exists");
                return mapping.findForward("modify_documentdetail");
            }
            String add_candidate_file = candidate.getMainPath("add_candidate_file");
            String foldername = candidate.createFolder(add_candidate_file);
            Date now = new Date();
            String fn = String.valueOf(now.getTime());
            
            CandidateInfo info = new CandidateInfo(govdocumentId, documentTypeId, documentno, DocumentIssuedbyId, cityId, dateofissue, dateofexpiry, status);
            if (govdocumentId <= 0) {
                int cc = candidate.insertGovdocumentdetail(info, candidateId, uId);
                Connection conn = null;
                conn = candidate.getConnection();
                String remarks = "Verify Documents tab";
                talentpool.insertAlertBy(candidateId, 10, 1, remarks, uId, conn, crewName);
                if (cc > 0) {
                    String fname = frm.getFname() != null ? frm.getFname() : "";
                        if (!"".equals(fname)) {
                            String fnameval[] = fname.split("@#@");
                            int len = fnameval.length;                            
                            try {
                                for (int i = 0; i < len; i++) {
                                    String fileName = candidate.saveImage(fnameval[i], add_candidate_file, foldername, fn + "_" + i);
                                    candidate.createdocumentfiles(conn, cc, fileName, uId);                                    
                                }
                            } finally {
                                if (conn != null) {
                                    conn.close();
                                }
                            }
                        }
                    request.setAttribute("MESSAGE", "Data added successfully.");
                }
                request.getSession().removeAttribute("FILENAME");
                int expiryId = frm.getExpiryId();
                String search2 = frm.getSearch2() != null && !frm.getSearch2().equals("") ? frm.getSearch2() : "";
                frm.setSearch2(search2);
                ArrayList list = candidate.getFeedbcakgovdocList(candidateId, search2, expiryId);
                request.setAttribute("CANDGOVDOCLIST", list);
                return mapping.findForward("view_documentdetail");
            } else {
                int cc = candidate.updateGovdocumentRecord(info, candidateId, uId);
                Connection conn = null;
                conn = candidate.getConnection();
                String remarks = "Verify Documents tab";
                talentpool.insertAlertBy(candidateId, 10, 1, remarks, uId, conn, crewName);
                if (cc > 0) {
                    String fname = frm.getFname() != null ? frm.getFname() : "";
                    if (!"".equals(fname)) {
                        String fnameval[] = fname.split("@#@");
                        int len = fnameval.length;
                        try {                            
                            for (int i = 0; i < len; i++) {
                                String fileName = candidate.saveImage(fnameval[i], add_candidate_file, foldername, fn + "_" + i);
                                candidate.createdocumentfiles(conn, govdocumentId, fileName, uId);                                
                            }
                        } finally {
                            if (conn != null) {
                                conn.close();
                            }
                        }
                    }
                    request.setAttribute("MESSAGE", "Data updated successfully.");
                }
                int expiryId = frm.getExpiryId();
                String search2 = frm.getSearch2() != null && !frm.getSearch2().equals("") ? frm.getSearch2() : "";
                frm.setSearch2(search2);
                ArrayList list = candidate.getFeedbcakgovdocList(candidateId, search2, expiryId);
                request.setAttribute("CANDGOVDOCLIST", list);
                return mapping.findForward("view_documentdetail");
            }
        }
         else if (frm.getDoaddtrainingcertdetail() != null && frm.getDoaddtrainingcertdetail().equals("yes")) {
            frm.setDoaddtrainingcertdetail("no");
            frm.setCandidateId(candidateId);
            Collection approvedbys = approvedby.getApprovedbys();
            Collection coursetypes = coursetype.getCourseType();
            Collection countries = country.getCountrys();
            Collection coursename = candidate.getCourseName();
            frm.setCountries(countries);
            frm.setCurrentDate(base.currDate3());
            frm.setApprovedbys(approvedbys);
            frm.setCoursetypes(coursetypes);
            frm.setCoursenames(coursename);
            int trainingandcertId = frm.getTrainingandcertId();
            frm.setTrainingandcertId(trainingandcertId);
            request.getSession().removeAttribute("FILENAME");
            if (trainingandcertId > 0) {
                CandidateInfo info = candidate.gettrainingCertificateDetailById(trainingandcertId);
                if (info != null) {
                    frm.setCoursetypeId(info.getCoursetypeId());
                    frm.setCoursenameId(info.getCoursenameId());
                    frm.setEducationInstitute(info.getEduinstitute());
                    frm.setLocationofInstituteId(info.getLocationofInstituteId());
                    frm.setDateofissue(info.getDateofissue());
                    frm.setCertificationno(info.getCertificateno());
                    frm.setDateofexpiry(info.getExpirydate());
                    frm.setCityName(info.getCity());
                    frm.setCourseverification(info.getCourseverification());
                    frm.setTrainingcerthiddenfile(info.getCertifilename());
                    frm.setCurrentDate(base.currDate3());
                    request.getSession().setAttribute("FILENAME", info.getCertifilename());
                }
            }
            return mapping.findForward("modify_trainingcertdetail");
        }
         //
         else if (frm.getDoSavetrainingcertdetail() != null && frm.getDoSavetrainingcertdetail().equals("yes")) {
            frm.setDoSavetrainingcertdetail("no");

            candidateId = frm.getCandidateId();
            int trainingandcertId = frm.getTrainingandcertId();
            frm.setTrainingandcertId(trainingandcertId);
            int coursetypeId = frm.getCoursetypeId();
            int coursenameId = frm.getCoursenameId();
            String educationInstitute = validate.replacedesc(frm.getEducationInstitute());
            int locationofInstituteId = frm.getLocationofInstituteId();
            String dateofissue = validate.replacedate(frm.getDateofissue());
            String certificationno = validate.replacedesc(frm.getCertificationno());
            String dateofexpiry = validate.replacedate(frm.getDateofexpiry());
            String courseverification = validate.replacename(frm.getCourseverification());
            status = 1;
            if (educationInstitute.equals("") || coursenameId <= 0) {
                frm.setCandidateId(candidateId);
                Collection approvedbys = approvedby.getApprovedbys();
                Collection coursetypes = coursetype.getCourseType();
                Collection countries = country.getCountrys();
                Collection coursename = candidate.getCourseName();
                frm.setCountries(countries);
                frm.setApprovedbys(approvedbys);
                frm.setCoursetypes(coursetypes);
                frm.setCoursenames(coursename);
                frm.setTrainingandcertId(trainingandcertId);
                request.setAttribute("MESSAGE", "Something went wrong.");
                return mapping.findForward("modify_trainingcertdetail");
            }
            int ck = candidate.checkDuplicacytrainingCertificate(candidateId, trainingandcertId, coursetypeId, coursenameId, educationInstitute, dateofissue);
            if (ck == 1) {
                frm.setCandidateId(candidateId);
                Collection approvedbys = approvedby.getApprovedbys();
                frm.setApprovedbys(approvedbys);
                Collection coursetypes = coursetype.getCourseType();
                frm.setCoursetypes(coursetypes);
                Collection countries = country.getCountrys();
                frm.setCountries(countries);
                Collection coursename = candidate.getCourseName();
                frm.setCoursenames(coursename);
                frm.setTrainingandcertId(trainingandcertId);
                frm.setCurrentDate(base.currDate3());
                request.setAttribute("MESSAGE", "Training and Certification already exists");
                return mapping.findForward("modify_trainingcertdetail");
            }
            String add_candidate_file = candidate.getMainPath("add_candidate_file");
            String foldername = candidate.createFolder(add_candidate_file);
            Date now = new Date();
            String fn = String.valueOf(now.getTime());
            String fileName1 = "";
            FormFile filename = frm.getTrainingcertfile();
            if (filename != null && filename.getFileSize() > 0) {
                fileName1 = candidate.uploadFile(trainingandcertId, frm.getTrainingcerthiddenfile(), filename, fn + "_1", add_candidate_file, foldername);
            }
            CandidateInfo info = new CandidateInfo(trainingandcertId, coursetypeId, coursenameId, educationInstitute, locationofInstituteId, "", "",
                    "", dateofissue, certificationno, dateofexpiry, courseverification, 0, status, fileName1);
            if (trainingandcertId <= 0) {
                int cc = candidate.inserttrainingCertificate(info, candidateId, uId,1);
                Connection conn = null;
                try
                {
                    conn = candidate.getConnection();
                    String remarks= "Verify Certification tab";
                    talentpool.insertAlertBy(candidateId, 7, 1, remarks, uId, conn, crewName);
                } finally {
                    if (conn != null) {
                        conn.close();
                    }
                }
                if (cc > 0) {
                    request.setAttribute("MESSAGE", "Data added successfully.");
                }
                request.getSession().removeAttribute("FILENAME");
                ArrayList list = candidate.gettrainingCertificatelist(candidateId,0, 0 ,0 );
                if (list.size() > 0) {
                    list.remove(list.size() - 1);
                }
                request.setAttribute("CANDTRAININGCERTLIST", list);
                return mapping.findForward("view_trainingcertdetail");
            } else {
                int cc = candidate.updatetrainingCertificate(info, candidateId, uId);
                Connection conn = null;
                try
                {
                    conn = candidate.getConnection();
                    String remarks= "Verify Certification tab";
                    talentpool.insertAlertBy(candidateId, 7, 1, remarks, uId, conn, crewName);
                } finally {
                    if (conn != null) {
                        conn.close();
                    }
                }
                if (cc > 0) {
                    request.setAttribute("MESSAGE", "Data updated successfully.");
                }
                request.getSession().removeAttribute("FILENAME");
                ArrayList list = candidate.gettrainingCertificatelist(candidateId,0,0,0);
                if (list.size() > 0) {
                    list.remove(list.size() - 1);
                }
                request.setAttribute("CANDTRAININGCERTLIST", list);
                return mapping.findForward("view_trainingcertdetail");
            }
        }
        //
        else if (frm.getDoViewProfile() != null && frm.getDoViewProfile().equals("yes")) {
            frm.setDoViewProfile("no");
            print(this, "View Profile Block");

            CandidateInfo info = candidate.getCandidateDetail(candidateId);
            request.setAttribute("CANDIDATE_DETAIL_CREW", info);
            return mapping.findForward("view_profile");
        } else if (frm.getDoViewlangdetail() != null && frm.getDoViewlangdetail().equals("yes")) {
            frm.setDoViewlangdetail("no");
            ArrayList list = candidate.getCandlanguageList(candidateId);
            request.setAttribute("CANDLANGLIST", list);
            return mapping.findForward("view_candidatelanguage");//view.page page
        } else if (frm.getDoViewhealthdetail() != null && frm.getDoViewhealthdetail().equals("yes")) {
            frm.setDoViewhealthdetail("no");
            Collection bloodpressures = bloodpressure.getBloodpressures();
            frm.setBloodpressures(bloodpressures);
            CandidateInfo info = candidate.getCandidateHealthDetailBycandidateId(candidateId);
            request.getSession().setAttribute("CANDHEALTHINFO", info);
            return mapping.findForward("view_candidatehealth");//do view.jsp
        } else if (frm.getDoViewvaccinationlist() != null && frm.getDoViewvaccinationlist().equals("yes")) {
            frm.setDoViewvaccinationlist("no");
            ArrayList list = candidate.getCandVaccList(candidateId);
            request.setAttribute("CANDVACCLIST", list);
            return mapping.findForward("view_candidatevaccination");
        } else if (frm.getDoViewtrainingcertlist() != null && frm.getDoViewtrainingcertlist().equals("yes")) {
            frm.setDoViewtrainingcertlist("no");
            ArrayList list = candidate.gettrainingCertificatelist(candidateId,0,0,0);
            if (list.size() > 0) {
                list.remove(list.size() - 1);
            }
            request.setAttribute("CANDTRAININGCERTLIST", list);
            return mapping.findForward("view_trainingcertdetail");
        } else if (frm.getDoVieweducationlist() != null && frm.getDoVieweducationlist().equals("yes")) {
            frm.setDoVieweducationlist("no");
            ArrayList list = candidate.geteducationlist(candidateId);
            request.setAttribute("CANDEDUCATIONLIST", list);
            return mapping.findForward("view_educationallist");
        } else if (frm.getDoViewexperiencelist() != null && frm.getDoViewexperiencelist().equals("yes")) {
            frm.setDoViewexperiencelist("no");
            ArrayList list = candidate.getexperiencelist(candidateId);
            request.setAttribute("CANDEXPERIENCELIST", list);
            return mapping.findForward("view_experiencelist");
        } else if (frm.getDoViewexperience() != null && frm.getDoViewexperience().equals("yes")) {
            frm.setDoViewexperience("no");
            int experiencedetailId = frm.getExperiencedetailId();
            CandidateInfo info = candidate.getexperiencedetailfeedbackById(experiencedetailId);
            request.setAttribute("CANDEXPERIENCEINFO", info);
            return mapping.findForward("view_experience");

        } else if (frm.getDoViewcertification() != null && frm.getDoViewcertification().equals("yes")) {
            frm.setDoViewcertification("no");
            int certificationdetaillId = frm.getCertificationdetaillId();
            CandidateInfo info = candidate.gettrainingCertificateDetailfeedbackById(certificationdetaillId);
            request.setAttribute("CANDTRAININGCERTINFO", info);
            return mapping.findForward("view_trainingcert");

        } else if (frm.getDoViewgovdocumentlist() != null && frm.getDoViewgovdocumentlist().equals("yes")) {
            frm.setDoViewgovdocumentlist("no");
            int expiryId = frm.getExpiryId();
            frm.setExpiryId(expiryId);
            String search2 = frm.getSearch2() != null && !frm.getSearch2().equals("") ? frm.getSearch2() : "";
            frm.setSearch2(search2);
            ArrayList list = candidate.getFeedbcakgovdocList(candidateId, search2, expiryId);
            request.setAttribute("CANDGOVDOCLIST", list);
            return mapping.findForward("view_documentdetail");
        }else if (frm.getDoViewtopic()!= null && frm.getDoViewtopic().equals("yes")) {
            frm.setDoViewtopic("no");            
            print(this, "DoViewtopic block");            
            ArrayList list = feedback.getTopicList(clientassetId, positionId);
            request.setAttribute("TOPICLIST", list);
            return mapping.findForward("view_topic");//view topic page
        }        
        else if (frm.getDoGetFeedbackList() != null && frm.getDoGetFeedbackList().equals("yes")) {
            print(this, "GetDoGetFeedbackList block.");
            frm.setDoGetFeedbackList("no");

            ArrayList feedbackList = feedback.getFeedbackByName(crewrotationId, search, status, 0, count, fromDate, toDate, positionId, clientassetId);
            int cnt = 0;
            if (feedbackList.size() > 0) {
                FeedbackInfo cinfo = (FeedbackInfo) feedbackList.get(feedbackList.size() - 1);
                cnt = cinfo.getSurveyId();
                feedbackList.remove(feedbackList.size() - 1);
            }
            int arr[] = feedback.getcountsurvey(crewrotationId);
            request.getSession().setAttribute("ARR_COUNT", arr);
            request.getSession().setAttribute("FEEDBACK_LIST", feedbackList);
            request.getSession().setAttribute("COUNT_LIST", cnt + "");
            request.getSession().setAttribute("NEXT", 0 + "");
            request.getSession().setAttribute("NEXTVALUE", (0 + 1) + "");
            return mapping.findForward("display_feedback");
        } 
        else if (frm.getDoCompetencyList() != null && frm.getDoCompetencyList().equals("yes")) 
        {
            frm.setDoCompetencyList("no");
            print(this, "GetDoCompetencyList block.");
            int compstatus = -1;
            frm.setCompstatus(compstatus);
            String compsearch = "";
            frm.setCompsearch(compsearch);
            ArrayList competencyList = feedback.getCompetencyByName(candidateId, compsearch, compstatus, 0, count);
            int cnt = 0;
            if (competencyList.size() > 0) {
                FeedbackInfo cinfo = (FeedbackInfo) competencyList.get(competencyList.size() - 1);
                cnt = cinfo.getTrackerId();
                competencyList.remove(competencyList.size() - 1);
            }
            request.getSession().setAttribute("COMPETENCY_LIST", competencyList);
            request.getSession().setAttribute("COUNT_LIST", cnt + "");
            request.getSession().setAttribute("NEXT", 0 + "");
            request.getSession().setAttribute("NEXTVALUE", (0 + 1) + "");
            return mapping.findForward("display_competency");
            
        }
        else if (frm.getDocumentList()!= null && frm.getDocumentList().equals("yes")) 
        {
            frm.setDocumentList("no");
            print(this, "GetDocumentList block.");
            String docsearch = frm.getDocSearch() != null && !frm.getDocSearch().equals("") ? frm.getDocSearch() : "";
            ArrayList list = feedback.getDocumentListing(clientId, clientassetId, docsearch);          
            request.getSession().setAttribute("DOCUMENTS_LIST", list);
            return mapping.findForward("display_documents");            
        }
        else if (frm.getTrainingList()!= null && frm.getTrainingList().equals("yes")) 
        {
            frm.setTrainingList("no");
            print(this, "GetTrainingList block.");
            String tcsearch = frm.getTcsearch() != null && !frm.getTcsearch().equals("") ? frm.getTcsearch() : "";
            ArrayList list = feedback.getTrainingListing(candidateId, tcsearch);
            request.getSession().setAttribute("TRAINING_LIST", list);
            return mapping.findForward("display_training");            
        }
        else if (frm.getDoCancelCompetency() != null && frm.getDoCancelCompetency().equals("yes")) {
            frm.setDoCancelCompetency("no");
            print(this, "getDoCancelCompetency block.");
            int compstatus = frm.getCompstatus();
            frm.setCompstatus(compstatus);
            String compsearch = frm.getCompsearch() != null ? frm.getCompsearch() : "";
            frm.setCompsearch(compsearch);

            int next = 0;
            if (request.getSession().getAttribute("NEXTVALUE") != null) {
                next = Integer.parseInt((String) request.getSession().getAttribute("NEXTVALUE"));
            }
            next = next - 1;
            if (next < 0) {
                next = 0;
            }
            ArrayList competencyList = feedback.getCompetencyByName(candidateId, compsearch, compstatus, next, count);
            int cnt = 0;
            if (competencyList.size() > 0) {
                FeedbackInfo cinfo = (FeedbackInfo) competencyList.get(competencyList.size() - 1);
                cnt = cinfo.getTrackerId();
                competencyList.remove(competencyList.size() - 1);
            }
            request.getSession().setAttribute("COMPETENCY_LIST", competencyList);
            request.getSession().setAttribute("COUNT_LIST", cnt + "");
            request.getSession().setAttribute("NEXT", next + "");
            request.getSession().setAttribute("NEXTVALUE", (next + 1) + "");
            return mapping.findForward("display_competency");
        } else if (frm.getDoSendAppeal() != null && frm.getDoSendAppeal().equals("yes")) {
            frm.setDoSendAppeal("no");
            print(this, "getDoSendAppeal block.");
            int compstatus = frm.getCompstatus();
            frm.setCompstatus(compstatus);
            String compsearch = frm.getCompsearch() != null ? frm.getCompsearch() : "";
            frm.setCompsearch(compsearch);
            int trackerId = frm.getTrackerId();
            int appealId = frm.getAppealId();
            String remark = frm.getAppealremarks();
            int cc = feedback.getAppeal(trackerId, appealId, remark, crewName);
            String msg = "";
            if (cc > 0) {
                msg = "Appeal request sent successfully.";
            } else {
                msg = "Something went wrong.";
            }

            int next = 0;
            if (request.getSession().getAttribute("NEXTVALUE") != null) {
                next = Integer.parseInt((String) request.getSession().getAttribute("NEXTVALUE"));
            }
            next = next - 1;
            if (next < 0) {
                next = 0;
            }
            ArrayList competencyList = feedback.getCompetencyByName(candidateId, compsearch, compstatus, next, count);
            int cnt = 0;
            if (competencyList.size() > 0) {
                FeedbackInfo cinfo = (FeedbackInfo) competencyList.get(competencyList.size() - 1);
                cnt = cinfo.getTrackerId();
                competencyList.remove(competencyList.size() - 1);
            }
            request.getSession().setAttribute("COMPETENCY_LIST", competencyList);
            request.getSession().setAttribute("COUNT_LIST", cnt + "");
            request.getSession().setAttribute("NEXT", next + "");
            request.getSession().setAttribute("NEXTVALUE", (next + 1) + "");
            request.setAttribute("APPEAL_MSG", msg);
            return mapping.findForward("display_competency");

        } else if (frm.getDoOnlineAssessment() != null && frm.getDoOnlineAssessment().equals("yes")) {
            frm.setDoOnlineAssessment("no");
            print(this, "getDoOnlineAssessment block.");

            int compstatus = frm.getCompstatus();
            frm.setCompstatus(compstatus);
            String compsearch = frm.getCompsearch() != null ? frm.getCompsearch() : "";
            frm.setCompsearch(compsearch);
            int trackerId = frm.getTrackerId();
            frm.setTrackerId(trackerId);
            FeedbackInfo info = null;
            if (request.getSession().getAttribute("PCODE_INFO") != null) {
                info = (FeedbackInfo) request.getSession().getAttribute("PCODE_INFO");
            }
            ArrayList qlist = new ArrayList();
            if (trackerId > 0 && info != null) {
                qlist = feedback.getQuestionListById(info);
            }
            request.setAttribute("ASSQUSTION_LIST", qlist);
            return mapping.findForward("competency_assessment");

        } else if (frm.getDoSaveAssessment() != null && frm.getDoSaveAssessment().equals("yes")) {
            frm.setDoSaveAssessment("no");
            print(this, "getDoSaveAssessment block.");

            int compstatus = frm.getCompstatus();
            frm.setCompstatus(compstatus);
            String compsearch = frm.getCompsearch() != null ? frm.getCompsearch() : "";
            frm.setCompsearch(compsearch);
            int trackerId = frm.getTrackerId();
            int[] questionId = frm.getQuestionId();
            int[] trackerDltsId = frm.getTrackerDltsId();
            String[] answer = frm.getAnswer();
            int cc = feedback.getUpdateAssessment(trackerId, questionId, trackerDltsId, answer, 1);

            int next = 0;
            if (request.getSession().getAttribute("NEXTVALUE") != null) {
                next = Integer.parseInt((String) request.getSession().getAttribute("NEXTVALUE"));
            }
            next = next - 1;
            if (next < 0) {
                next = 0;
            }
            ArrayList competencyList = feedback.getCompetencyByName(candidateId, compsearch, compstatus, next, count);
            int cnt = 0;
            if (competencyList.size() > 0) {
                FeedbackInfo cinfo = (FeedbackInfo) competencyList.get(competencyList.size() - 1);
                cnt = cinfo.getTrackerId();
                competencyList.remove(competencyList.size() - 1);
            }
            request.getSession().setAttribute("COMPETENCY_LIST", competencyList);
            request.getSession().setAttribute("COUNT_LIST", cnt + "");
            request.getSession().setAttribute("NEXT", next + "");
            request.getSession().setAttribute("NEXTVALUE", (next + 1) + "");
            return mapping.findForward("display_competency");
        } else if (frm.getDoSubmitAssessment() != null && frm.getDoSubmitAssessment().equals("yes")) {
            frm.setDoSubmitAssessment("no");
            print(this, "getDoSubmitAssessment block.");

            int compstatus = frm.getCompstatus();
            frm.setCompstatus(compstatus);
            String compsearch = frm.getCompsearch() != null ? frm.getCompsearch() : "";
            frm.setCompsearch(compsearch);
            int trackerId = frm.getTrackerId();
            int allwork = frm.getAllwork();
            int[] questionId = frm.getQuestionId();
            int[] trackerDltsId = frm.getTrackerDltsId();
            String[] answer = frm.getAnswer();
            if (allwork > 0) {
                int cc = feedback.getUpdateAssessment(trackerId, questionId, trackerDltsId, answer, 2);
            }
            int next = 0;
            if (request.getSession().getAttribute("NEXTVALUE") != null) {
                next = Integer.parseInt((String) request.getSession().getAttribute("NEXTVALUE"));
            }
            next = next - 1;
            if (next < 0) {
                next = 0;
            }
            ArrayList competencyList = feedback.getCompetencyByName(candidateId, compsearch, compstatus, next, count);
            int cnt = 0;
            if (competencyList.size() > 0) {
                FeedbackInfo cinfo = (FeedbackInfo) competencyList.get(competencyList.size() - 1);
                cnt = cinfo.getTrackerId();
                competencyList.remove(competencyList.size() - 1);
            }
            request.getSession().setAttribute("COMPETENCY_LIST", competencyList);
            request.getSession().setAttribute("COUNT_LIST", cnt + "");
            request.getSession().setAttribute("NEXT", next + "");
            request.getSession().setAttribute("NEXTVALUE", (next + 1) + "");
            return mapping.findForward("display_competency");
        } else if (frm.getDoSaveFeedback() != null && frm.getDoSaveFeedback().equals("yes")) {
            frm.setDoSaveFeedback("no");
            print(this, "getDoSaveFeedback block.");

            int compstatus = frm.getCompstatus();
            frm.setCompstatus(compstatus);
            String compsearch = frm.getCompsearch() != null ? frm.getCompsearch() : "";
            frm.setCompsearch(compsearch);
            int trackerId = frm.getTrackerId();
            String feedbackrmk = frm.getFeedbackremark() != null ? frm.getFeedbackremark() : "";

            int cc = feedback.getUpdateFeedback(trackerId, feedbackrmk, crewName);

            int next = 0;
            if (request.getSession().getAttribute("NEXTVALUE") != null) {
                next = Integer.parseInt((String) request.getSession().getAttribute("NEXTVALUE"));
            }
            next = next - 1;
            if (next < 0) {
                next = 0;
            }
            ArrayList competencyList = feedback.getCompetencyByName(candidateId, compsearch, compstatus, next, count);
            int cnt = 0;
            if (competencyList.size() > 0) {
                FeedbackInfo cinfo = (FeedbackInfo) competencyList.get(competencyList.size() - 1);
                cnt = cinfo.getTrackerId();
                competencyList.remove(competencyList.size() - 1);
            }
            request.getSession().setAttribute("COMPETENCY_LIST", competencyList);
            request.getSession().setAttribute("COUNT_LIST", cnt + "");
            request.getSession().setAttribute("NEXT", next + "");
            request.getSession().setAttribute("NEXTVALUE", (next + 1) + "");
            return mapping.findForward("display_competency");
        } else if (frm.getDoSave() != null && frm.getDoSave().equals("yes")) {
            frm.setDoSave("no");
            print(this, "getDoSave block.");
            int surveyId = frm.getSurveyId();
            String[] answer = frm.getAnswer();
            int[] questionId = frm.getQuestionId();
            feedback.createsurveydetail(surveyId, questionId, answer);
            ArrayList feedbackList = feedback.getFeedbackByName(crewrotationId, search, status, 0, count, fromDate, toDate, positionId, clientassetId);
            int cnt = 0;
            if (feedbackList.size() > 0) {
                FeedbackInfo cinfo = (FeedbackInfo) feedbackList.get(feedbackList.size() - 1);
                cnt = cinfo.getSurveyId();
                feedbackList.remove(feedbackList.size() - 1);
            }
            int arr[] = feedback.getcountsurvey(crewrotationId);
            request.getSession().setAttribute("ARR_COUNT", arr);
            request.getSession().setAttribute("FEEDBACK_LIST", feedbackList);
            request.getSession().setAttribute("COUNT_LIST", cnt + "");
            request.getSession().setAttribute("NEXT", "0");
            request.getSession().setAttribute("NEXTVALUE", "1");
            request.getSession().removeAttribute("SURVEYINFO");
            return mapping.findForward("display_feedback");
        }
        //for contratc tab
        else if (frm.getViewContractList()!= null && frm.getViewContractList().equals("yes")) 
        {
            frm.setViewContractList("no");
            frm.setCandidateId(candidateId);  
            
            int crewClientId = frm.getCrewClientId();
            int crewAssetId = frm.getCrewAssetId();
            frm.setCrewClientId(crewClientId);
            
            Collection clients = feedback.getCrewClients(candidateId);
            frm.setClients(clients);
            
            Collection assets = feedback.getCrewAssets(candidateId, -1);
            frm.setAssets(assets);
            
            frm.setCrewAssetId(crewAssetId);
            ArrayList list = feedback.getContractListing(candidateId,"","","",0,0, 0, count);
            
            int cnt = 0;
            if (list.size() > 0) {
                FeedbackInfo cinfo = (FeedbackInfo) list.get(list.size() - 1);
                cnt = cinfo.getContractdetailId();
                list.remove(list.size() - 1);
            }            
            request.getSession().setAttribute("CONTRACTLIST", list);
            request.getSession().setAttribute("COUNT_CONTRACT", cnt + "");
            request.getSession().setAttribute("NEXTCONTRACT", 0+"");
            request.getSession().setAttribute("NEXTCONTRACTVALUE", (0+1)+"");
            return mapping.findForward("view_contractlist");            
        }        
        if(frm.getContractApprove() != null && frm.getContractApprove().equals("yes"))
        {
            frm.setContractApprove("no");
            int contractdetailId = frm.getContractdetailId();
            candidateId = frm.getCandidateId();
            String remarks = frm.getRemark();
            int type = frm.getType();
            String add_file = talentpool.getMainPath("add_candidate_file");
            String foldername = talentpool.createFolder(add_file);
            Date now = new Date();
            String fn = String.valueOf(now.getTime());
            String fileName1 = "";
            FormFile filename = frm.getContractfile();
            if (filename != null && filename.getFileSize() > 0) {
                fileName1 = talentpool.uploadFile(candidateId, "", filename, fn , add_file, foldername);
            }
            feedback.contractApprove(candidateId, contractdetailId, fileName1, remarks, crewName, uId, type );
            int crewClientId = frm.getCrewClientId();
            
            Collection clients = feedback.getCrewClients(candidateId);
            frm.setClients(clients);
            
            Collection assets = feedback.getCrewAssets(candidateId, crewClientId);
            frm.setAssets(assets);
            int crewAssetId = frm.getCrewAssetId();
            frm.setCrewAssetId(crewAssetId);
            String fromDate1 = frm.getFromDate1();
            String toDate1 = frm.getToDate1();
            frm.setFromDate1(fromDate1);
            frm.setToDate1(toDate1);
            String search1 = frm.getSearch1() != null && !frm.getSearch1().equals("") ? frm.getSearch1() : "";
            frm.setSearch1(search1);
            if(fromDate1.equals(""))
            {
                fromDate1 = frm.getFromDateMob();
            }
            if(toDate1.equals(""))
            {
                toDate1 = frm.getToDateMob();
            }            
            int next = 0;
            if (request.getSession().getAttribute("NEXTCONTRACTVALUE") != null) {
                next = Integer.parseInt((String) request.getSession().getAttribute("NEXTCONTRACTVALUE"));
            }
            next = next - 1;
            if (next < 0) {
                next = 0;
            }
            ArrayList list = feedback.getContractListing(candidateId, search1,fromDate1, toDate1, crewClientId, crewAssetId, next, count);
            int cnt = 0;
            if (list.size() > 0) {
                FeedbackInfo cinfo = (FeedbackInfo) list.get(list.size() - 1);
                cnt = cinfo.getContractdetailId();
                list.remove(list.size() - 1);
            }            
            request.getSession().setAttribute("CONTRACTLIST", list);
            request.getSession().setAttribute("COUNT_CONTRACT", cnt + "");
            request.getSession().setAttribute("NEXTCONTRACT", next+"");
            request.getSession().setAttribute("NEXTCONTRACTVALUE", (next+1)+"");
            return mapping.findForward("view_contractlist");  
        }
        else if (frm.getDoViewBanklist() != null && frm.getDoViewBanklist().equals("yes")) 
        {
            frm.setDoViewBanklist("no");
            print(this, "DoViewBanklist block");
            ArrayList list = candidate.getCandbankList(candidateId);
            request.setAttribute("CANDBANKLIST", list);
            return mapping.findForward("view_banklist");
        }
        //For document search
        else if (frm.getSearchDoc()!= null && frm.getSearchDoc().equals("yes"))
        {
            frm.setSearchDoc("no");
            print(this, "getSearchDoc block");
            int expiryId = frm.getExpiryId();
            String search2 = frm.getSearch2() != null && !frm.getSearch2().equals("") ? frm.getSearch2() : "";
            frm.setSearch2(search2);
            ArrayList list = candidate.getFeedbcakgovdocList(candidateId, search2, expiryId);
            request.setAttribute("CANDGOVDOCLIST", list);
            return mapping.findForward("view_documentdetail");
        }
        //
        else if (frm.getViewInterviewList()!= null && frm.getViewInterviewList().equals("yes")) 
        {
            frm.setViewInterviewList("no");
            print(this, "getViewInterviewList block.");
            ArrayList list = feedback.getInterviewList(candidateId);
            request.getSession().setAttribute("INTERVIEW_LIST", list);
            return mapping.findForward("view_interviewlist");            
        }
        else if (frm.getDoAddAvailability()!= null && frm.getDoAddAvailability().equals("yes")) 
        {
            print(this, "getDoAddAvailability block.");
            frm.setDoAddAvailability("no");
            int interviewId = frm.getInterviewId();
            frm.setInterviewId(interviewId);
            saveToken(request);
            return mapping.findForward("edit_interview");            
        }
        else if (frm.getDoSaveAvailability()!= null && frm.getDoSaveAvailability().equals("yes")) 
        {
            frm.setDoSaveAvailability("no");
            print(this, "getDoSaveAvailability block.");
            int interviewId = frm.getInterviewId();
            frm.setInterviewId(interviewId);
            int type = frm.getType();
            String remark = frm.getRemark();
            if (isTokenValid(request)) 
            {
                resetToken(request);
                if (interviewId > 0) 
                {
                    int id = feedback.getTypeById(interviewId);
                    if (id <= 2) {
                        feedback.saveAvailability(interviewId, candidateId, type, crewName, remark);
                    }
                }
            }
            ArrayList list = feedback.getInterviewList(candidateId);
            request.getSession().setAttribute("INTERVIEW_LIST", list);
            frm.setInterviewId(0);
            return mapping.findForward("view_interviewlist");
        }
        //for offerlist
        else if (frm.getViewClientOfferwList()!= null && frm.getViewClientOfferwList().equals("yes")) 
        {
            frm.setViewClientOfferwList("no");
            print(this, "getViewClientOfferwList block.");
            ArrayList list = feedback.getClientOfferList(candidateId);
            request.getSession().setAttribute("CLIENTOFFER_LIST", list);
            return mapping.findForward("view_clientofferlist");            
        }
        else if (frm.getDoSaveOffer()!= null && frm.getDoSaveOffer().equals("yes")) 
        {
            frm.setDoSaveOffer("no");
            print(this, "getDoSaveOffer block.");
            int shortlistId = frm.getShortlistId();            
            String remark = frm.getRemark();
            int type = frm.getType();
            int cc = feedback.saveOffer(shortlistId, crewName, remark, candidateId, type); 
            if(cc > 0){
                request.getSession().setAttribute("TP_ID", ""+type);
                request.getSession().setAttribute("S_ID", ""+shortlistId);
            }
            ArrayList list = feedback.getClientOfferList(candidateId);
            request.getSession().setAttribute("CLIENTOFFER_LIST", list);
            return mapping.findForward("view_clientofferlist");
        }
        else if (frm.getDoSendmail()!= null && frm.getDoSendmail().equals("yes")) 
        {
            frm.setDoSendmail("no");
            print(this, "getDoSendmail block.");
            int shortlistId = frm.getShortlistId();
            int type = frm.getType();
            int assetId = frm.getAssetId();
            clientId = frm.getClientId();
            String fromval = frm.getFromval();
            String toval = frm.getToval();
            String ccval = frm.getCcval();
            String bccval = frm.getBccval();
            String subject = frm.getSubject();
            String description = frm.getDescription();
            FormFile attachfile = frm.getOfferFile();
            
            String add_path = feedback.getMainPath("add_resumetemplate_pdf");
            String foldername = feedback.createFolder(add_path);
            Date now = new Date();
            String fn = String.valueOf(now.getTime());
            
            String fileName1 = "";
            try {
                if (attachfile != null && attachfile.getFileSize() > 0) {
                    fileName1 = feedback.uploadFile(0, "", attachfile, fn, add_path, foldername);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            int cc = feedback.sendmailOfferReplay(fromval, toval, ccval, bccval, subject, description, fileName1,shortlistId, crewName, clientId,  assetId, type, candidateId);
            if(cc > 0)
            {
                feedback.updateSflag(shortlistId, crewName, uId, type);
            }
            ArrayList list = feedback.getClientOfferList(candidateId);
            request.getSession().setAttribute("CLIENTOFFER_LIST", list);
            return mapping.findForward("view_clientofferlist");
        }
        else if (frm.getDoCancel() != null && frm.getDoCancel().equals("yes")) {
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
            ArrayList feedbackList = feedback.getFeedbackByName(crewrotationId, search, status, next, count, fromDate, toDate, positionId, clientassetId);
            int cnt = 0;
            if (feedbackList.size() > 0) {
                FeedbackInfo cinfo = (FeedbackInfo) feedbackList.get(feedbackList.size() - 1);
                cnt = cinfo.getSurveyId();
                feedbackList.remove(feedbackList.size() - 1);
            }
            int arr[] = feedback.getcountsurvey(crewrotationId);
            request.getSession().setAttribute("ARR_COUNT", arr);
            request.getSession().setAttribute("FEEDBACK_LIST", feedbackList);
            request.getSession().setAttribute("COUNT_LIST", cnt + "");
            request.getSession().setAttribute("NEXT", next + "");
            request.getSession().setAttribute("NEXTVALUE", (next + 1) + "");
            return mapping.findForward("display_feedback");
        }
        else {
            print(this, "else block.");
            ArrayList feedbackList = feedback.getFeedbackByName(crewrotationId, search, status, 0, count, fromDate, toDate, positionId, clientassetId);
            int cnt = 0;
            if (feedbackList.size() > 0) {
                FeedbackInfo cinfo = (FeedbackInfo) feedbackList.get(feedbackList.size() - 1);
                cnt = cinfo.getSurveyId();
                feedbackList.remove(feedbackList.size() - 1);
            }
            int arr[] = feedback.getcountsurvey(crewrotationId);
            request.getSession().setAttribute("ARR_COUNT", arr);
            request.getSession().setAttribute("FEEDBACK_LIST", feedbackList);
            request.getSession().setAttribute("COUNT_LIST", cnt + "");
            request.getSession().setAttribute("NEXT", "0");
            request.getSession().setAttribute("NEXTVALUE", "1");
            return mapping.findForward("display_feedback");
        }
    }
}
