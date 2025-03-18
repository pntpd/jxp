package com.web.jxp.talentpool;

import com.web.jxp.approvedby.Approvedby;
import com.web.jxp.assettype.Assettype;
import com.web.jxp.bankaccounttype.BankAccountType;
import com.web.jxp.base.Base;
import com.web.jxp.base.Validate;
import com.web.jxp.bloodpressure.Bloodpressure;
import com.web.jxp.candidate.Candidate;
import com.web.jxp.clientselection.Clientselection;
import static com.web.jxp.common.Common.*;
import com.web.jxp.companyindustry.Companyindustry;
import com.web.jxp.contract.Contract;
import com.web.jxp.country.Country;
import com.web.jxp.coursetype.CourseType;
import com.web.jxp.crewdayrate.Crewdayrate;
import com.web.jxp.crewtype.Crewtype;
import com.web.jxp.currency.Currency;
import com.web.jxp.degree.Degree;
import com.web.jxp.documentissuedby.Documentissuedby;
import com.web.jxp.experiencedept.ExperienceDept;
import com.web.jxp.experiencewaterdepth.ExperienceWaterDepth;
import com.web.jxp.grade.Grade;
import com.web.jxp.language.Language;
import com.web.jxp.maritialstatus.MaritialStatus;
import com.web.jxp.onboarding.Onboarding;
import com.web.jxp.position.Position;
import com.web.jxp.ppetype.Ppetype;
import com.web.jxp.proficiency.Proficiency;
import com.web.jxp.qualificationtype.QualificationType;
import com.web.jxp.relation.Relation;
import com.web.jxp.remark.Remark;
import com.web.jxp.skills.Skills;
import com.web.jxp.user.UserInfo;
import com.web.jxp.vaccine.Vaccine;
import com.web.jxp.vaccinetype.Vaccinetype;
import java.io.File;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Stack;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

public class TalentpoolAction extends Action {

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        TalentpoolForm frm = (TalentpoolForm) form;
        Talentpool talentpool = new Talentpool();
        Candidate candidate = new Candidate();
        Validate validate = new Validate();
        Country country = new Country();
        Ddl ddl = new Ddl();
        Crewdayrate crewdayrate = new Crewdayrate();
        Contract contract = new Contract();
        Language language = new Language();
        Proficiency proficiency = new Proficiency();
        Remark remarktype = new Remark();
        Vaccine vaccine = new Vaccine();
        Vaccinetype vaccinetype = new Vaccinetype();
        Base base = new Base();
        Relation relation = new Relation();
        Ppetype ppe = new Ppetype();
        MaritialStatus maritalstatus = new MaritialStatus();
        BankAccountType bankaccounttype = new BankAccountType();
        Documentissuedby documentissuedby = new Documentissuedby();
        Position position = new Position();
        Approvedby approvedby = new Approvedby();
        CourseType coursetype = new CourseType();
        Bloodpressure bloodpressure = new Bloodpressure();
        ExperienceDept experiencedept = new ExperienceDept();
        Currency currency = new Currency();
        QualificationType qualificationtype = new QualificationType();
        Degree degree = new Degree();
        Companyindustry companyindustry = new Companyindustry();
        Assettype assettype = new Assettype();
        ExperienceWaterDepth waterdepth = new ExperienceWaterDepth();
        Skills skill = new Skills();
        Grade grade = new Grade();
        Crewtype crewtype = new Crewtype();
        int count = talentpool.getCount();
        String search = frm.getSearch() != null ? frm.getSearch() : "";
        frm.setSearch(search);

        Collection courses = candidate.getCourseName();
        frm.setCoursenames(courses);
        int courseIndex = frm.getCourseIndex();

        int statusIndex = frm.getStatusIndex();
        frm.setStatusIndex(statusIndex);
        int uId = 0, allclient = 0;
        String permission = "N", cids = "", assetids = "", userName = "";
        if (request.getSession().getAttribute("LOGININFO") != null) {
            UserInfo uInfo = (UserInfo) request.getSession().getAttribute("LOGININFO");
            if (uInfo != null) {
                permission = uInfo.getPermission();
                uId = uInfo.getUserId();
                cids = uInfo.getCids();
                allclient = uInfo.getAllclient();
                assetids = uInfo.getAssetids();
                userName = uInfo.getName();
            }
        }
        int check_user = talentpool.checkUserSession(request, 4, permission);
        if (check_user == -1) {
            return mapping.findForward("default");
        } else if (check_user == -2) {
            String authmess = talentpool.getMainPath("user_auth");
            authmess = authmess.replaceAll("__MODULE__", "Candidate Talentpool");
            request.setAttribute("AUTHMESSAGE", authmess);
            return mapping.findForward("auth");
        }
        Collection clients = talentpool.getClients(cids, allclient, permission);
        frm.setClients(clients);
        int clientIndex = frm.getClientIndex();
        frm.setClientIndex(clientIndex);

        Collection clientassets = talentpool.getClientAsset(clientIndex, assetids, allclient, permission);
        frm.setClientassets(clientassets);
        int assetIndex = frm.getAssetIndex();
        frm.setAssetIndex(assetIndex);

        Collection Ipositions = talentpool.getPostions(assetIndex);
        frm.setIpositions(Ipositions);
        int positionIndexId = frm.getPositionIndexId();
        frm.setPositionIndexId(positionIndexId);

        Collection locations = talentpool.getCountrys();
        frm.setLocations(locations);
        int locationIndex = frm.getLocationIndex();
        frm.setLocationIndex(locationIndex);

        int employementstatus = frm.getEmployementstatus();
        frm.setEmployementstatus(employementstatus);

        Collection assettypes = assettype.getAssettypes();
        frm.setAssettypes(assettypes);
        int assettypeIdIndex = frm.getAssettypeIdIndex();
        frm.setAssettypeIdIndex(assettypeIdIndex);

        Collection positionss = ddl.getAssetypePositions(assettypeIdIndex);
        frm.setPositions(positionss);
        int positionFilterId = frm.getPositionFilterId();
        frm.setPositionFilterId(positionFilterId);

        int verified = 0;
        verified = frm.getVerified();
        frm.setVerified(verified);

        if (frm.getDoAdd() != null && frm.getDoAdd().equals("yes")) {
            frm.setDoAdd("no");
            frm.setCandidateId(-1);
            Collection countries = country.getCountrys();
            frm.setCountries(countries);
            Collection experiencedepts = experiencedept.getExperienceDepts();
            frm.setDepartments(experiencedepts);
            Collection positions = candidate.getPositionassettypes(-1);
            frm.setPositions(positions);
            Collection positions2 = candidate.getPosition2assettypes(-1, -1);
            frm.setPositions2(positions2);
            Collection currencies = currency.getCurrencys();
            frm.setCurrencies(currencies);
            frm.setRelations(relation.getRelations());
            frm.setMaritalstatuses(maritalstatus.getMaritialStatus());
            frm.setAssettypes(assettype.getAssettypes());
            frm.setCurrentDate(base.currDate3());
            frm.setPositionIdhidden(0);
            Collection states = candidate.getCountryStates(-1);
            frm.setStates(states);
            request.removeAttribute("PHOTO");
            request.setAttribute("FILECOUNT", talentpool.changeNum(0, 3) + "");
            return mapping.findForward("add_candidate");
        } else if (frm.getDoSaveOnboardModal() != null && frm.getDoSaveOnboardModal().equals("yes")) {
            frm.setDoSaveOnboardModal("no");
            int candidateId = frm.getCandidateId();
            frm.setCandidateId(candidateId);
            int clientId = frm.getClientIdModal();
            int clientassetId = frm.getAssetIdModal();
            int positionId = frm.getPositionIdModal();
            String remarks = frm.getOnboardRemark();
            String date = frm.getDate();
            ddl.addtocrew(clientId, clientassetId, candidateId, positionId, uId, remarks, date);
            TalentpoolInfo info = talentpool.getCandidateDetail(candidateId);
            request.getSession().setAttribute("CANDIDATE_DETAIL", info);
            return mapping.findForward("view_candidate");
        } else if (frm.getDoSaveTransferModal() != null && frm.getDoSaveTransferModal().equals("yes")) {
            frm.setDoSaveTransferModal("no");
            int candidateId = frm.getCandidateId();
            frm.setCandidateId(candidateId);
            int assetId = frm.getClientassetId();
            int type = frm.getTransferType();
            int clientId = frm.getClientId();
            int toAssetId = frm.getToAssetId();
            String joiningdate1 = frm.getJoiningDate1();
            String joiningdate2 = frm.getJoiningDate2();
            String endDate1 = frm.getEndDate1();
            String endDate2 = frm.getEndDate2();
            String remark = frm.getTransferRemark();
            int toClientId = frm.getToClientId();
            int toPositionId = frm.getToPositionId();
            int toCurrencyId = frm.getToCurrencyId();
            String torate1 = frm.getTorate1();
            String torate2 = frm.getTorate2();
            String torate3 = frm.getTorate3();
            
            int toPositionId2 = frm.getToPositionId2();
            int toCurrencyId2 = frm.getToCurrencyId2();
            String top2rate1 = frm.getTop2rate1();
            String top2rate2 = frm.getTop2rate2();
            String top2rate3 = frm.getTop2rate3();
            
            TalentpoolInfo info = new TalentpoolInfo(assetId, toAssetId, joiningdate1, remark, uId, 
                    candidateId, type, clientId, toClientId, toPositionId, toCurrencyId, torate1, torate2, torate3,
                    toPositionId2,toCurrencyId2,top2rate1,top2rate2,top2rate3, endDate1, joiningdate2, endDate2);
            TalentpoolInfo info2 = talentpool.getCandidateExpDetail(candidateId);
            int ck = crewdayrate.checkDuplicacy(candidateId, joiningdate1, endDate1, toAssetId, toPositionId);
            int ck2 = 0;
            if(toPositionId2 > 0)
            {
                 ck2 = crewdayrate.checkDuplicacy(candidateId, joiningdate2, endDate2, toAssetId, toPositionId2);
            }
            if(ck == 1)
            {
                request.setAttribute("MESSAGE", "Day rate for the selected date already exists, Please select a later date");
                return mapping.findForward("view_candidate");
            }
            else if(ck2 == 1)
            {
                request.setAttribute("MESSAGE", "Day rate for the selected date already exists, Please select a later date");
                return mapping.findForward("view_candidate");
            }
            else
            {
                talentpool.updateTerminateModal(info);
                talentpool.createTransferModal(info);
                ddl.updateTransModal(candidateId, uId, info);
                TalentpoolInfo info3 = talentpool.getCandidateExpDetail(candidateId);
                TalentpoolInfo info1 = talentpool.getCandidateDetail(candidateId);
                talentpool.createTerminateExp(info2, candidateId, uId, info3,0, info1.getAssettypeId());
                if(toPositionId2 > 0)
                    talentpool.createTerminateExp(info2, candidateId, uId, info3, toPositionId2, info1.getAssettypeId() );
                request.setAttribute("TRANSAVEMODEL", "yes");
                TalentpoolInfo p1info = ddl.getDayRateForTransfer(candidateId, info.getToAssetId(), info1.getPositionId());
                TalentpoolInfo p2info = ddl.getDayRateForTransfer(candidateId, info.getToAssetId(), info1.getPositionId2());
                request.getSession().setAttribute("P1_RATE", p1info);
                request.getSession().setAttribute("P2_RATE", p2info);
                request.getSession().setAttribute("CANDIDATE_DETAIL", info1);
                return mapping.findForward("view_candidate");
            }
        } else if (frm.getDoSaveTerminateModal() != null && frm.getDoSaveTerminateModal().equals("yes")) {
            frm.setDoSaveTerminateModal("no");
            int candidateId = frm.getCandidateId();
            frm.setCandidateId(candidateId);
            String reason = frm.getReason();
            String joiningdate = frm.getJoiningDate();
            String enddate = frm.getEndDate();
            String terminateremark = frm.getTerminateRemark();
            String add_candidate_file = candidate.getMainPath("add_candidate_file");
            String foldername = candidate.createFolder(add_candidate_file);
            Date now = new Date();
            String fn = String.valueOf(now.getTime());
            String fileName1 = "";
            FormFile filename = frm.getTerminatefile();
            if (filename != null && filename.getFileSize() > 0) {
                fileName1 = candidate.uploadFile(candidateId, frm.getTerminatefilehidden(), filename, fn + "_1", add_candidate_file, foldername);
            }
            TalentpoolInfo info = new TalentpoolInfo(joiningdate, enddate, reason, terminateremark, uId, candidateId, fileName1);
            TalentpoolInfo info2 = talentpool.getCandidateExpDetail(candidateId);
            talentpool.updateTerminateModal(info);
            TalentpoolInfo info3 = talentpool.getCandidateExpDetail(candidateId);
            TalentpoolInfo info1 = talentpool.getCandidateDetail(candidateId);
            talentpool.createTerminateExp(info2, candidateId, uId, info3, 0, info1.getAssettypeId());
            request.setAttribute("TERMISAVEMODEL", "yes");
            request.getSession().setAttribute("CANDIDATE_DETAIL", info1);
            return mapping.findForward("view_candidate");
        } else if (frm.getDoViewHeader() != null && frm.getDoViewHeader().equals("yes")) {
            frm.setDoViewHeader("no");
            int candidateId = frm.getCandidateIdHeader();
            frm.setCandidateId(candidateId);
            TalentpoolInfo info = talentpool.getCandidateDetail(candidateId);
            request.getSession().setAttribute("CANDIDATE_DETAIL", info);
            return mapping.findForward("view_candidate");
        } else if (frm.getDoView() != null && frm.getDoView().equals("yes")) {
            frm.setDoView("no");
            int candidateId = frm.getCandidateId();
            frm.setCandidateId(candidateId);
            TalentpoolInfo info = talentpool.getCandidateDetail(candidateId);
            TalentpoolInfo p1info = ddl.getDayRateForTransfer(candidateId, info.getToAssetId(), info.getPositionId());
            TalentpoolInfo p2info = ddl.getDayRateForTransfer(candidateId, info.getToAssetId(), info.getPositionId2());
            request.getSession().setAttribute("P1_RATE", p1info);
            request.getSession().setAttribute("P2_RATE", p2info);
           
            ArrayList list = talentpool.getOffshoreList(candidateId);
            request.setAttribute("OFFSHORELIST", list);
            request.getSession().setAttribute("CANDIDATE_DETAIL", info);
            return mapping.findForward("view_candidate");
        } else if (frm.getDoModify() != null && frm.getDoModify().equals("yes")) {
            frm.setDoModify("no");
            int candidateId = frm.getCandidateId();
            frm.setCandidateId(candidateId);
            Collection countries = country.getCountrys();
            frm.setCountries(countries);
            Collection experiencedepts = experiencedept.getExperienceDepts();
            frm.setDepartments(experiencedepts);
            Collection currencies = currency.getCurrencys();
            frm.setCurrencies(currencies);
            frm.setRelations(relation.getRelations());
            frm.setMaritalstatuses(maritalstatus.getMaritialStatus());
            frm.setAssettypes(assettype.getAssettypes());
            frm.setCurrentDate(base.currDate3());
            TalentpoolInfo info = talentpool.getCandidateDetailById(candidateId);
            if (info != null) {
                frm.setCandidateId(candidateId);
                if (info.getFirstname() != null) {
                    frm.setFirstname(info.getFirstname());
                }
                if (info.getMiddlename() != null) {
                    frm.setMiddlename(info.getMiddlename());
                }
                if (info.getLastname() != null) {
                    frm.setLastname(info.getLastname());
                }
                if (info.getDob() != null) {
                    frm.setDob(info.getDob());
                }
                if (info.getPlaceofbirth() != null) {
                    frm.setPlaceofbirth(info.getPlaceofbirth());
                }
                if (info.getEmailId() != null) {
                    frm.setEmailId(info.getEmailId());
                }
                if (info.getCode1Id() != null) {
                    frm.setCode1Id(info.getCode1Id());
                }
                if (info.getCode2Id() != null) {
                    frm.setCode2Id(info.getCode2Id());
                }
                if (info.getCode3Id() != null) {
                    frm.setCode3Id(info.getCode3Id());
                }
                if (info.getContactno1() != null) {
                    frm.setContactno1(info.getContactno1());
                }
                if (info.getContactno2() != null) {
                    frm.setContactno2(info.getContactno2());
                }
                if (info.getContactno3() != null) {
                    frm.setContactno3(info.getContactno3());
                }
                if (info.getGender() != null) {
                    frm.setGender(info.getGender());
                }
                frm.setCountryId(info.getCountryId());
                frm.setNationalityId(info.getNationalityId());
                frm.setCityId(info.getCityId());
                frm.setCityName(info.getCity());
                frm.setCurrencyId(info.getCurrencyId());
                frm.setAssettypeId(info.getAssettypeId());
                
                Collection positions2 = candidate.getPositionassettypes(info.getAssettypeId());
                frm.setPositions(positions2);
                frm.setPositionIdhidden(info.getPositionId());
                frm.setPositionId(info.getPositionId());
                
                Collection positions3 = candidate.getPosition2assettypes(info.getAssettypeId(), info.getPositionId());
                frm.setPositions2(positions3);
                frm.setPositionId2(info.getPositionId2());
                
                Collection states = candidate.getCountryStates(info.getCountryId());
                frm.setStates(states);
                frm.setStateId(info.getStateId());
                
                frm.setDepartmentId(info.getDepartmentId());
                frm.setExpectedsalary(info.getExpectedsalary());
                frm.setApplytype(info.getApplytype());
                if (info.getAddress1line1() != null) {
                    frm.setAddress1line1(info.getAddress1line1());
                }
                if (info.getAddress1line2() != null) {
                    frm.setAddress1line2(info.getAddress1line2());
                }
                if (info.getAddress1line3() != null && !info.getAddress1line3().equals("")) {
                    frm.setAddress1line3(info.getAddress1line3());
                }
                if (info.getAddress2line1() != null) {
                    frm.setAddress2line1(info.getAddress2line1());
                }
                if (info.getAddress2line2() != null) {
                    frm.setAddress2line2(info.getAddress2line2());
                }
                if (info.getAddress2line3() != null) {
                    frm.setAddress2line3(info.getAddress2line3());
                }
                if (info.getEmployeeId() != null) {
                    frm.setEmployeeid(info.getEmployeeId());
                }
                if (info.getNextofkin() != null) {
                    frm.setNextofkin(info.getNextofkin());
                }
                frm.setRelationId(info.getRelationId());
                if (info.getEcode1Id() != null) {
                    frm.setEcode1Id(info.getEcode1Id());
                }
                if (info.getEcode2Id() != null) {
                    frm.setEcode2Id(info.getEcode2Id());
                }
                if (info.getEcontactno1() != null) {
                    frm.setEcontactno1(info.getEcontactno1());
                }
                if (info.getEcontactno2() != null) {
                    frm.setEcontactno2(info.getEcontactno2());
                }
                frm.setMaritalstatusId(info.getMaritalstatusId());                
                if (info.getPhotofilename() != null) {
                    frm.setPhotofilehidden(info.getPhotofilename());
                }
                if (info.getResumefilename() != null) {
                    frm.setResumefilehidden(info.getResumefilename());
                }
                if (info.getReligion() != null) {
                    frm.setReligion(info.getReligion());
                }
                frm.setRate1(info.getRate1());
                frm.setRate2(info.getRate2());
                frm.setP2rate1(info.getP2rate1());
                frm.setP2rate2(info.getP2rate2());
                frm.setTravelDays(info.getTravelDays());
                if(info.getPinCode() != null){
                    frm.setPinCode(info.getPinCode());
                } 
                frm.setAge(info.getAge());
                if(info.getAirport1()!= null){
                    frm.setAirport1(info.getAirport1());
                }
                if(info.getAirport2()!= null){
                    frm.setAirport2(info.getAirport2());
                }
                if(info.getProfile()!= null){
                    frm.setProfile(info.getProfile());
                }
                if(info.getSkill1()!= null){
                    frm.setSkill1(info.getSkill1());
                }
                if(info.getSkill2()!= null){
                    frm.setSkill2(info.getSkill2());
                }
                request.getSession().setAttribute("RATETYPE", (info.getRatetype() != null ? info.getRatetype() : ""));
                request.setAttribute("PHOTO", info.getPhotofilename());
                request.setAttribute("FILECOUNT", talentpool.changeNum(info.getFilecount(), 3) + "");
                if (request.getAttribute("CANDSAVEMODEL") != null) {
                    request.removeAttribute("CANDSAVEMODEL");
                }
            }
            return mapping.findForward("add_candidate");
        } else if (frm.getDoSave() != null && frm.getDoSave().equals("yes")) {
            frm.setDoSave("no");
            int candidateId = frm.getCandidateId();
            String firstname = validate.replacename(frm.getFirstname());
            String middlename = validate.replacename(frm.getMiddlename());
            String lastname = validate.replacename(frm.getLastname());
            String religion = validate.replacedesc(frm.getReligion());
            String dob = validate.replacedate(frm.getDob());
            String placeofbirth = validate.replacename(frm.getPlaceofbirth());
            String emailId = validate.replacedesc(frm.getEmailId());
            String code1 = validate.replaceint(frm.getCode1Id());
            String contactno1 = validate.replaceint(frm.getContactno1());
            String contactno2 = validate.replaceint(frm.getContactno2());
            String contactno3 = validate.replaceint(frm.getContactno3());
            String code2 = validate.replaceint(frm.getCode2Id());
            if (contactno2 == "" && contactno2.equals("")) {
                code2 = "";
            }
            String code3 = validate.replaceint(frm.getCode3Id());
            if (contactno3 == "" && contactno3.equals("")) {
                code3 = "";
            }
            String gender = validate.replacename(frm.getGender());
            int countryId = frm.getCountryId();
            int nationalityId = frm.getNationalityId();
            int positionId = frm.getPositionId();
            int positionIdhidden = frm.getPositionIdhidden();
            int departmentId = frm.getDepartmentId();
            int currencyId = frm.getCurrencyId();
            int expectedsalary = frm.getExpectedsalary();
            int assettypeId = frm.getAssettypeId();
            String address1line1 = validate.replacedesc(frm.getAddress1line1());
            String address1line2 = validate.replacedesc(frm.getAddress1line2());
            String address1line3 = validate.replacedesc(frm.getAddress1line3());
            String address2line1 = validate.replacedesc(frm.getAddress2line1());
            String address2line2 = validate.replacedesc(frm.getAddress2line2());
            String address2line3 = validate.replacedesc(frm.getAddress2line3());
            String employeeId = validate.replacedesc(frm.getEmployeeid());
            String nextofkin = validate.replacename(frm.getNextofkin());
            int relationId = frm.getRelationId();
            String ecode1 = validate.replaceint(frm.getEcode1Id());
            String ecode2 = validate.replaceint(frm.getEcode2Id());
            String econtactno1 = validate.replaceint(frm.getEcontactno1());
            String econtactno2 = validate.replaceint(frm.getEcontactno2());
            int maritalstatusId = frm.getMaritalstatusId();
            int cityId = frm.getCityId();
            int applytype = frm.getApplytype();
            double rate1 = frm.getRate1();
            double rate2 = frm.getRate2();
            double p2rate1 = frm.getP2rate1();
            double p2rate2 = frm.getP2rate2();
            int positionId2 = frm.getPositionId2();
            int stateId = frm.getStateId();
            String pincode = validate.replacedesc(frm.getPinCode());
            int status = 1;
            int travelDays =  frm.getTravelDays();
            int age =  frm.getAge();
            String airport1 = validate.replacedesc(frm.getAirport1());
            String airport2 = validate.replacedesc(frm.getAirport2());
            String profile = frm.getProfile();
            String skill1 = frm.getSkill1();
            String skill2 = frm.getSkill2();
            String ipAddrStr = request.getRemoteAddr();
            String iplocal = talentpool.getLocalIp();
            String localFile = frm.getLocalFile();
            int ck = talentpool.checkDuplicacy(candidateId, emailId);
            if (ck == 1) {
                Collection countries = country.getCountrys();
                frm.setCountries(countries);
                frm.setRelations(relation.getRelations());
                Collection experiencedepts = experiencedept.getExperienceDepts();
                frm.setDepartments(experiencedepts);
                Collection positions = position.getPositiontypes();
                frm.setPositions(positions);
                Collection currencies = currency.getCurrencys();
                frm.setCurrencies(currencies);
                frm.setMaritalstatuses(maritalstatus.getMaritialStatus());
                frm.setAssettypes(assettype.getAssettypes());
                frm.setAssettypeId(assettypeId);
                frm.setCurrentDate(base.currDate3());
                request.setAttribute("MESSAGE", "EmailId already exists");
                return mapping.findForward("add_candidate");
            }
            String add_candidate_file = talentpool.getMainPath("add_candidate_file");
            String foldername = talentpool.createFolder(add_candidate_file);
            Date now = new Date();
            String fn = String.valueOf(now.getTime());
            String fileName1 = "", fileName2 = "";
            FormFile image1 = frm.getPhotofile();
            if (image1 != null && image1.getFileSize() > 0) {
                fileName1 = talentpool.uploadFile(candidateId, frm.getPhotofilehidden(), image1, fn + "_1", add_candidate_file, foldername);
            }
            FormFile image2 = frm.getResumefile();

            if (image2 != null && image2.getFileSize() > 0) {
                fileName2 = talentpool.uploadFile(candidateId, frm.getResumefilehidden(), image2, fn + "_2", add_candidate_file, foldername);
            }
            TalentpoolInfo info = new TalentpoolInfo(firstname, middlename, lastname, dob, placeofbirth, emailId, code1, code2, code3, contactno1, contactno2, contactno3,
                    gender, countryId, nationalityId, address1line1, address1line2, address1line3, address2line1, address2line2, address2line3, nextofkin, relationId, ecode1,
                    ecode2, econtactno1, econtactno2, maritalstatusId, fileName1, status, fileName2, positionId, departmentId, currencyId, expectedsalary, cityId, assettypeId,
                    employeeId, applytype, rate1, rate2, religion, positionId2, p2rate1, p2rate2,travelDays, stateId, pincode, age, airport1, airport2, profile, skill1, skill2);
            if (candidateId <= 0) {
                int cc = talentpool.createCandidate(info, uId);
                frm.setCandidateId(cc);
                if (cc > 0) {
                    String fname = frm.getFname() != null ? frm.getFname() : "";
                    if (!"".equals(fname)) {
                        String fnameval[] = fname.split("@#@");
                        String localname[] = localFile.split("@#@");
                        int len = fnameval.length;
                        Connection conn = null;
                        try {
                            conn = talentpool.getConnection();
                            for (int i = 0; i < len; i++) 
                            {
                                String fileName = talentpool.saveImage(fnameval[i], add_candidate_file, foldername, talentpool.getfilename(localname[i])+"-"+fn + "_" + i);
                                talentpool.createPic(conn, cc, fileName, uId, localname[i]);
                            }
                        } finally {
                            if (conn != null) {
                                conn.close();
                            }
                        }
                    }
                    request.setAttribute("MESSAGE", "Data added successfully.");
                    talentpool.createHistoryAccess(null, uId, ipAddrStr, iplocal, "Data Added", 4, cc);
                }
                request.getSession().removeAttribute("RATETYPE");
                request.setAttribute("CANDSAVEMODEL", "yes");
                TalentpoolInfo cinfo = talentpool.getCandidateDetail(cc);
                request.getSession().setAttribute("CANDIDATE_DETAIL", cinfo);
                return mapping.findForward("view_candidate");
            } else {
                talentpool.updateCandidate(info, candidateId, uId, positionIdhidden, userName);
                String fname = frm.getFname() != null ? frm.getFname() : "";
                if (!"".equals(fname)) {
                    String fnameval[] = fname.split("@#@");
                    String localname[] = localFile.split("@#@");
                    int len = fnameval.length;
                    Connection conn = null;
                    try {
                        conn = talentpool.getConnection();
                        for (int i = 0; i < len; i++) {
                            String fileName = talentpool.saveImage(fnameval[i], add_candidate_file, foldername, talentpool.getfilename(localname[i])+"-"+fn + "_" + i);
                            if(fileName != null && !fileName.equals(""))
                            {
                                talentpool.createPic(conn, candidateId, fileName, uId, localname[i]);
                            }
                        }
                    } finally {
                        if (conn != null) {
                            conn.close();
                        }
                    }
                }
                request.setAttribute("MESSAGE", "Data updated successfully.");
                talentpool.createHistoryAccess(null, uId, ipAddrStr, iplocal, "Data Updated", 4, candidateId);
                TalentpoolInfo cinfo = talentpool.getCandidateDetail(candidateId);
                request.getSession().setAttribute("CANDIDATE_DETAIL", cinfo);
                return mapping.findForward("view_candidate");
            }
        } else if (frm.getDoCancel() != null && frm.getDoCancel().equals("yes")) {
            frm.setDoCancel("no");
            int next = 0;
            if (request.getSession().getAttribute("NEXTVALUE") != null) {
                next = Integer.parseInt((String) request.getSession().getAttribute("NEXTVALUE"));
            }
            next = next - 1;
            if (next < 0) {
                next = 0;
            }
            ArrayList candidateList = talentpool.getCandidateByName(search, statusIndex, next, count,
                    positionIndexId, clientIndex, locationIndex, assetIndex, verified, employementstatus,
                    allclient, permission, cids, assetids, assettypeIdIndex, positionFilterId);
            int cnt = 0;
            if (candidateList.size() > 0) {
                TalentpoolInfo cinfo = (TalentpoolInfo) candidateList.get(candidateList.size() - 1);
                cnt = cinfo.getCandidateId();
                candidateList.remove(candidateList.size() - 1);
            }
            request.getSession().setAttribute("CANDIDATE_LIST", candidateList);
            request.getSession().setAttribute("COUNT_LIST", cnt + "");
            request.getSession().setAttribute("NEXT", next + "");
            request.getSession().setAttribute("NEXTVALUE", (next + 1) + "");
            return mapping.findForward("display");
        } else if (frm.getDoViewBanklist() != null && frm.getDoViewBanklist().equals("yes")) {
            frm.setDoViewBanklist("no");
            frm.setBankdetailId(-1);
            int candidateId = frm.getCandidateId();
            frm.setCandidateId(candidateId);
            ArrayList list = talentpool.getCandbankList(candidateId);
            request.setAttribute("CANDBANKLIST", list);
            return mapping.findForward("view_candidatebanklist");
        } else if (frm.getDoManageBankdetail() != null && frm.getDoManageBankdetail().equals("yes")) {
            frm.setDoManageBankdetail("no");
            int bankDetailId = frm.getBankdetailId();
            frm.setBankdetailId(bankDetailId);
            frm.setCandidateId(frm.getCandidateId());
            Collection bankaccounttypes = bankaccounttype.getBankAccountType();
            frm.setBankAccountTypes(bankaccounttypes);
            request.getSession().removeAttribute("FILENAME");
            if (frm.getBankdetailId() > 0) {
                TalentpoolInfo info = talentpool.getBankDetailByCandidateId(bankDetailId);
                if (info != null) {
                    if (info.getBankName() != "" && !info.getBankName().equals("")) {
                        frm.setBankName(info.getBankName());
                    }
                    if (info.getSavingAccountNo() != null) {
                        frm.setSavingAccountNo(info.getSavingAccountNo());
                    }
                    if (info.getBranch() != null) {
                        frm.setBranch(info.getBranch());
                    }
                    if (info.getIFSCCode() != null) {
                        frm.setIFSCCode(info.getIFSCCode());
                    }
                    if (info.getAccountTypeId() > 0) {
                        frm.setAccountTypeId(info.getAccountTypeId());
                    }
                    if (info.getBankdetid() > 0) {
                        frm.setBankdetailId(info.getBankdetid());
                    }
                    if (info.getBkFilename() != null) {
                        frm.setBankfilehidden(info.getBkFilename());
                    }
                    if (info.getAccountHolder()!= null) {
                        frm.setAccountHolder(info.getAccountHolder());
                    }
                    frm.setPrimarybankId(info.getPrimarybankId());
                    request.getSession().setAttribute("FILENAME", info.getBkFilename());
                }
            }
            return mapping.findForward("add_bankdetail");
        } else if (frm.getDoSaveBankdetail() != null && frm.getDoSaveBankdetail().equals("yes")) {
            frm.setDoSaveBankdetail("no");
            int bankdetailId = frm.getBankdetailId();
            String bankName = validate.replacename(frm.getBankName());
            String savingAccountNo = validate.replaceint(frm.getSavingAccountNo());
            String accountHolder = validate.replacename(frm.getAccountHolder());
            String branch = validate.replacename(frm.getBranch());
            String IFSCCode = validate.replacename(frm.getIFSCCode());
            int accountTypeId = frm.getAccountTypeId();
            int primarybankId = frm.getPrimarybankId();
            int candidateId = frm.getCandidateId();
            frm.setCandidateId(candidateId);
            int status = 1;
            int ck = talentpool.checkDuplicacyBank(candidateId, bankdetailId, bankName, savingAccountNo);
            if (ck == 1) {
                Collection bankaccounttypes = bankaccounttype.getBankAccountType();
                frm.setBankAccountTypes(bankaccounttypes);
                frm.setBankName(bankName);
                frm.setSavingAccountNo(savingAccountNo);
                frm.setBranch(branch);
                frm.setIFSCCode(IFSCCode);
                frm.setAccountTypeId(accountTypeId);
                frm.setPrimarybankId(primarybankId);
                request.setAttribute("MESSAGE", "Bank already exists");
                return mapping.findForward("add_bankdetail");
            }
            String add_candidate_file = talentpool.getMainPath("add_candidate_file");
            String foldername = talentpool.createFolder(add_candidate_file);
            Date now = new Date();
            String fn = String.valueOf(now.getTime());
            String fileName1 = "", localname = "";
            FormFile image1 = frm.getBankfile();
            if(image1 != null)
            {
                localname = image1.getFileName().replaceAll("[/'\"]+", "");
                if(localname.contains("."))
                    localname = localname.substring(0, localname.lastIndexOf("."));
            }
            if (image1 != null && image1.getFileSize() > 0) {
                fileName1 = talentpool.uploadFile(candidateId, frm.getBankfilehidden(), image1, localname+"-"+fn, add_candidate_file, foldername);
            }
            TalentpoolInfo info = new TalentpoolInfo(candidateId, bankName, savingAccountNo, branch, 
                    IFSCCode, accountTypeId, fileName1, status, primarybankId, accountHolder);
            frm.setBankdetailId(-1);
            if (bankdetailId <= 0) {
                int cc = talentpool.insertBankdetails(info, candidateId, uId);
                if (cc > 0) {
                    request.setAttribute("MESSAGE", "Data added successfully.");
                }
                request.getSession().removeAttribute("FILENAME");
                ArrayList list = talentpool.getCandbankList(candidateId);
                request.setAttribute("CANDBANKLIST", list);
                return mapping.findForward("view_candidatebanklist");
            } else {
                int cc = talentpool.updateBankdetails(info, uId, candidateId, bankdetailId, userName);
                if (cc > 0) {
                    request.setAttribute("MESSAGE", "Data updated successfully.");
                }
                request.getSession().removeAttribute("FILENAME");
                ArrayList list = talentpool.getCandbankList(candidateId);
                request.setAttribute("CANDBANKLIST", list);
                return mapping.findForward("view_candidatebanklist");
            }
        } else if (frm.getDoDeleteBankdetail() != null && frm.getDoDeleteBankdetail().equals("yes")) {
            frm.setDoDeleteBankdetail("no");
            int candidateId = frm.getCandidateId();
            frm.setCandidateId(candidateId);
            int bankdetailId = frm.getBankdetailId();
            frm.setBankdetailId(bankdetailId);
            int status = frm.getStatus();
            String ipAddrStr = request.getRemoteAddr();
            String iplocal = talentpool.getLocalIp();
            int cc = talentpool.deletebank(candidateId, bankdetailId, uId, status, ipAddrStr, iplocal, userName);
            if (cc > 0) {
                request.setAttribute("MESSAGE", "Status updated successfully");
            }
            ArrayList list = talentpool.getCandbankList(candidateId);
            request.setAttribute("CANDBANKLIST", list);
            return mapping.findForward("view_candidatebanklist");

        } else if (frm.getDoViewlangdetail() != null && frm.getDoViewlangdetail().equals("yes")) {
            frm.setDoViewlangdetail("no");
            int candidateId = frm.getCandidateId();
            ArrayList list = talentpool.getCandlanguageList(candidateId);
            request.setAttribute("CANDLANGLIST", list);
            return mapping.findForward("view_candidatelanguage");//view.page page
        } else if (frm.getDoaddlangdetail() != null && frm.getDoaddlangdetail().equals("yes")) {
            frm.setDoaddlangdetail("no");
            int candidateId = frm.getCandidateId();
            frm.setCandidateId(candidateId);
            frm.setProficiencies(proficiency.getProficiencies());
            frm.setLanguages(language.getLanguages());
            int candidateLangId = frm.getCandidateLangId();
            frm.setCandidateLangId(candidateLangId);
            request.getSession().removeAttribute("FILENAME");
            if (candidateLangId > 0) {
                TalentpoolInfo info = talentpool.getcandlangForModify(candidateLangId);
                if (info != null) {
                    frm.setProficiencies(proficiency.getProficiencies());
                    frm.setLanguages(language.getLanguages());
                    frm.setProficiencyId(info.getProficiencyId());
                    frm.setLanguageId(info.getLanguageId());
                    if (info.getCandlangfilename() != null) {
                        frm.setLangfilehidden(info.getCandlangfilename());
                    }
                    request.getSession().setAttribute("FILENAME", info.getCandlangfilename());
                }
            }
            return mapping.findForward("add_candidatelanguage");
        } else if (frm.getDoSavelangdetail() != null && frm.getDoSavelangdetail().equals("yes")) {
            frm.setDoSavelangdetail("no");
            int candidateId = frm.getCandidateId();
            int candidateLangId = frm.getCandidateLangId();
            frm.setCandidateId(candidateId);
            int proficiencyId = frm.getProficiencyId();
            int languageId = frm.getLanguageId();
            int status = 1;
            int ck = talentpool.checkDuplicacyLang(candidateId, languageId, candidateLangId);
            if (ck == 1) {
                frm.setProficiencies(proficiency.getProficiencies());
                frm.setLanguages(language.getLanguages());
                frm.setLanguageId(languageId);
                frm.setProficiencyId(proficiencyId);
                ArrayList list = talentpool.getCandlanguageList(candidateId);
                request.setAttribute("CANDLANGLIST", list);
                request.setAttribute("MESSAGE", "Language already exists");
                return mapping.findForward("add_candidatelanguage");
            }
            String add_candidate_file = talentpool.getMainPath("add_candidate_file");
            String foldername = talentpool.createFolder(add_candidate_file);
            Date now = new Date();
            String fn = String.valueOf(now.getTime());
            String fileName1 = "",localname = "";
            FormFile filename = frm.getLangfile();
            if(filename != null)
            {
                localname = filename.getFileName().replaceAll("[/'\"]+", "");
                if(localname.contains("."))
                    localname = localname.substring(0, localname.lastIndexOf("."));
            }
            if (filename != null && filename.getFileSize() > 0) {
                fileName1 = talentpool.uploadFile(candidateLangId, frm.getLangfilehidden(), filename, localname + "-" + fn, add_candidate_file, foldername);
            }
            TalentpoolInfo info = new TalentpoolInfo(candidateLangId, languageId, proficiencyId, status, fileName1);
            if (candidateLangId <= 0) {
                int cc = talentpool.insertLanguage(info, candidateId, uId, userName);
                if (cc > 0) {
                    request.setAttribute("MESSAGE", "Data added successfully.");
                }
                request.getSession().removeAttribute("FILENAME");
                frm.setProficiencies(proficiency.getProficiencies());
                frm.setProficiencyId(-1);
                frm.setLanguages(language.getLanguages());
                frm.setLanguageId(-1);
                frm.setCandidateLangId(-1);
                ArrayList list = talentpool.getCandlanguageList(candidateId);
                request.setAttribute("CANDLANGLIST", list);
                return mapping.findForward("view_candidatelanguage");//view.page page
            } else {
                int cc = talentpool.updateLanguage(info, candidateId, uId, userName);
                if (cc > 0) {
                    request.setAttribute("MESSAGE", "Data updated successfully.");
                }
                request.getSession().removeAttribute("FILENAME");
                frm.setProficiencies(proficiency.getProficiencies());
                frm.setLanguages(language.getLanguages());
                frm.setCandidateLangId(-1);
                ArrayList list = talentpool.getCandlanguageList(candidateId);
                request.setAttribute("CANDLANGLIST", list);
                return mapping.findForward("view_candidatelanguage");//view.page page
            }
        } else if (frm.getDoDeletelangdetail() != null && frm.getDoDeletelangdetail().equals("yes")) {
            frm.setDoDeletelangdetail("no");
            int candidateId = frm.getCandidateId();
            int candidateLangId = frm.getCandidateLangId();
            frm.setCandidateId(candidateId);
            frm.setCandidateLangId(candidateLangId);
            int status = frm.getStatus();
            String ipAddrStr = request.getRemoteAddr();
            String iplocal = talentpool.getLocalIp();
            int cc = talentpool.deleteLanguage(candidateId, candidateLangId, uId, status, ipAddrStr, iplocal, userName);
            if (cc > 0) {
                request.setAttribute("MESSAGE", "Status updated successfully");
            }
            ArrayList list = talentpool.getCandlanguageList(candidateId);
            request.setAttribute("CANDLANGLIST", list);
            return mapping.findForward("view_candidatelanguage");//view.page page
        } else if (frm.getDoViewhealthdetail() != null && frm.getDoViewhealthdetail().equals("yes")) {
            frm.setDoViewhealthdetail("no");
            int candidateId = frm.getCandidateId();
            frm.setCandidateId(candidateId);
            Collection bloodpressures = bloodpressure.getBloodpressures();
            frm.setBloodpressures(bloodpressures);
            TalentpoolInfo info = talentpool.getCandidateHealthDetailBycandidateId(candidateId);
            ArrayList list = talentpool.getHealthFileList(candidateId);
            request.getSession().setAttribute("HEALTHFILELIST", list);
            request.getSession().setAttribute("CANDHEALTHINFO", info);
            return mapping.findForward("view_candidatehealth");//do view.jsp
        } else if (frm.getDoaddhealthdetail() != null && frm.getDoaddhealthdetail().equals("yes")) {
            frm.setDoaddhealthdetail("no");
            int candidateId = frm.getCandidateId();
            frm.setCandidateId(candidateId);
            Collection bloodpressures = bloodpressure.getBloodpressures();
            frm.setBloodpressures(bloodpressures);
            TalentpoolInfo info = talentpool.getCandidateHealthDetailBycandidateId(frm.getCandidateId());
            if (info != null) {
                frm.setSsmf(info.getSsmf());
                if (info.getOgukmedicalftw() != null) {
                    frm.setOgukmedicalftw(info.getOgukmedicalftw());
                }
                if (info.getOgukexp() != null) {
                    frm.setOgukexp(info.getOgukexp());
                }
                frm.setMedifitcert(info.getMedifitcert());
                if (info.getMedifitcertexp() != null) {
                    frm.setMedifitcertexp(info.getMedifitcertexp());
                }
                if (info.getBloodgroup() != null) {
                    frm.setBloodgroup(info.getBloodgroup());
                }
                frm.setBloodpressureid(info.getBloodpressureId());
                frm.setHypertension(info.getHypertension());
                frm.setDiabetes(info.getDiabetes());
                frm.setSmoking(info.getSmoking());
                frm.setCov192doses(info.getCov192doses());
                if (info.getMfFilename() != null) {
                    frm.setHealthfilehidden(info.getMfFilename());
                }
                frm.setHeight(info.getHeight());
                frm.setWeight(info.getWeight());
            }
            return mapping.findForward("add_candidatehealth");
        } else if (frm.getDoSavehealthdetail() != null && frm.getDoSavehealthdetail().equals("yes")) {
            frm.setDoSavehealthdetail("no");
            int candidateId = frm.getCandidateId();
            int candidatehealthId = frm.getCandidatehealthId();
            frm.setCandidateId(candidateId);
            frm.setCandidatehealthId(candidatehealthId);
            Collection bloodpressures = bloodpressure.getBloodpressures();
            frm.setBloodpressures(bloodpressures);
            String ssmf = validate.replacename(frm.getSsmf());
            String ogukmedicalftw = validate.replacedesc(frm.getOgukmedicalftw());
            String ogukexp = validate.replacedate(frm.getOgukexp());
            String medifitcert = frm.getMedifitcert();
            String medifitcertexp = validate.replacedate(frm.getMedifitcertexp());
            String bloodgroup = validate.replacedesc(frm.getBloodgroup());
            int bloodpressureId = frm.getBloodpressureid();
            String hypertension = validate.replacename(frm.getHypertension());
            String diabetes = validate.replacename(frm.getDiabetes());
            String smoking = validate.replacename(frm.getSmoking());
            String cov192doses = validate.replacename(frm.getCov192doses());
            double height = frm.getHeight();
            double weight = frm.getWeight();
            int status = 1;
            String ipAddrStr = request.getRemoteAddr();
            String iplocal = talentpool.getLocalIp();
            String add_candidate_file = talentpool.getMainPath("add_candidate_file");
            String foldername = talentpool.createFolder(add_candidate_file);
            Date now = new Date();
            String fn = String.valueOf(now.getTime());
            String fileName1 = "", localname ="";
            FormFile image1 = frm.getHealthfile();
            if(image1 != null)
            {
                localname = image1.getFileName().replaceAll("[/'\"]+", "");
                if(localname.contains("."))
                {
                    localname = localname.substring(0, localname.lastIndexOf("."));
                }
            }
            if (image1 != null && image1.getFileSize() > 0) {
                fileName1 = talentpool.uploadFile(candidateId, "", image1, localname+"-"+fn, add_candidate_file, foldername);
            }
            TalentpoolInfo info = new TalentpoolInfo(ssmf, ogukmedicalftw, ogukexp, medifitcert, medifitcertexp,
                    bloodgroup, bloodpressureId, hypertension, diabetes, smoking, fileName1, status, cov192doses, height, weight);
            if (candidateId > 0) {
                int cc = talentpool.insertHealthdetails(info, candidateId, uId, userName);
                if (cc > 0) {
                    talentpool.createHistoryAccess(null, uId, ipAddrStr, iplocal, "Data Added", 4, cc);
                    request.setAttribute("MESSAGE", "Data added successfully.");
                    frm.setCandidatehealthId(cc);
                }
                info = talentpool.getCandidateHealthDetailBycandidateId(frm.getCandidateId());
                ArrayList list = talentpool.getHealthFileList(candidateId);
                request.getSession().setAttribute("HEALTHFILELIST", list);
                request.getSession().setAttribute("CANDHEALTHINFO", info);
                return mapping.findForward("view_candidatehealth");
            }
            request.setAttribute("MESSAGE", "Something went wrong..");
            return mapping.findForward("cancel");
        } else if (frm.getDoDeleteHealthFile() != null && frm.getDoDeleteHealthFile().equals("yes")) {
            frm.setDoDeleteHealthFile("no");
            int candidateId = frm.getCandidateId();
            int healthfileId = frm.getHealthfileId();
            frm.setHealthfileId(healthfileId);
            talentpool.delhelathfile(healthfileId);
            ArrayList list = talentpool.getHealthFileList(candidateId);
            request.getSession().setAttribute("HEALTHFILELIST", list);
            return mapping.findForward("view_candidatehealth");//view.page page
        } else if (frm.getDoViewvaccinationlist() != null && frm.getDoViewvaccinationlist().equals("yes")) {
            frm.setDoViewvaccinationlist("no");
            int candidateId = frm.getCandidateId();
            frm.setCandidateId(candidateId);
            ArrayList list = talentpool.getCandVaccList(candidateId);
            request.setAttribute("CANDVACCLIST", list);
            return mapping.findForward("view_candidatevaccination");

        } else if (frm.getDoaddvaccinationdetail() != null && frm.getDoaddvaccinationdetail().equals("yes")) {
            frm.setDoaddvaccinationdetail("no");
            int candidateId = frm.getCandidateId();
            frm.setCandidateId(candidateId);
            frm.setCurrentDate(base.currDate3());
            frm.setVacinationnames(vaccine.getVaccines());
            frm.setVaccinationtypes(vaccinetype.getVaccinetypes());
            int candidatevaccineId = frm.getCandidatevaccineId();
            frm.setCandidatevaccineId(candidatevaccineId);
            Collection countries = country.getCountrys();
            frm.setCountries(countries);
            request.getSession().removeAttribute("FILENAME");
            if (candidatevaccineId > 0) {
                TalentpoolInfo info = talentpool.getcandVaccForModify(candidatevaccineId);
                if (info != null) {
                    frm.setVacinationnames(vaccine.getVaccines());
                    frm.setVaccinationNameId(info.getVaccinationNameId());
                    frm.setCurrentDate(base.currDate3());
                    frm.setVaccinationtypes(vaccinetype.getVaccinetypes());
                    frm.setVacinationTypeId(info.getVaccinationTypeId());
                    frm.setPlaceofapplicationId(info.getPlaceofapplicationId());
                    if (info.getPlaceofapplication() != null) {
                        frm.setCityName(info.getPlaceofapplication());
                    }
                    if (info.getDateofapplication() != null) {
                        frm.setDateofapplication(info.getDateofapplication());
                    }
                    if (info.getDateofexpiry() != null) {
                        frm.setDateofexpiry(info.getDateofexpiry());
                    }
                    if (info.getVaccinecertfile() != null) {
                        frm.setVaccinedetailhiddenfile(info.getVaccinecertfile());
                    }
                    request.getSession().setAttribute("FILENAME", info.getVaccinecertfile());
                }
            }
            return mapping.findForward("add_candidatevaccination");

        } else if (frm.getDoSavevaccinationdetail() != null && frm.getDoSavevaccinationdetail().equals("yes")) {
            frm.setDoSavevaccinationdetail("no");
            int candidateId = frm.getCandidateId();
            int candidatevaccineId = frm.getCandidatevaccineId();
            frm.setCandidatevaccineId(candidatevaccineId);
            int vaccinationNameId = frm.getVaccinationNameId();
            int vaccinationTypeId = frm.getVacinationTypeId();
            int placeofapplicationId = frm.getPlaceofapplicationId();
            String dateofapplication = validate.replacedate(frm.getDateofapplication());
            String dateofexpiry = validate.replacedate(frm.getDateofexpiry());
            int status = 1;
            int ck = talentpool.checkDuplicacyVaccination(candidateId, candidatevaccineId, vaccinationNameId, vaccinationTypeId, dateofapplication);
            if (ck == 1) {
                frm.setVacinationnames(vaccine.getVaccines());
                frm.setVaccinationtypes(vaccinetype.getVaccinetypes());
                frm.setCandidatevaccineId(candidatevaccineId);
                frm.setCurrentDate(base.currDate3());
                Collection countries = country.getCountrys();
                frm.setCountries(countries);
                request.setAttribute("MESSAGE", "Vaccination already exists");
                return mapping.findForward("add_candidatevaccination");
            }
            String add_candidate_file = talentpool.getMainPath("add_candidate_file");
            String foldername = talentpool.createFolder(add_candidate_file);
            Date now = new Date();
            String fn = String.valueOf(now.getTime());
            String fileName1 = "", localname = "";
            FormFile filename = frm.getVaccinedetailfile();
            if(filename != null)
            {
                localname = filename.getFileName().replaceAll("[/'\"]+", "");
                if(localname.contains("."))
                    localname = localname.substring(0, localname.lastIndexOf("."));
            }
            if (filename != null && filename.getFileSize() > 0) {
                fileName1 = talentpool.uploadFile(candidatevaccineId, frm.getVaccinedetailhiddenfile(), filename, localname+"-"+fn, add_candidate_file, foldername);
            }
            TalentpoolInfo info = new TalentpoolInfo(candidatevaccineId, vaccinationNameId, vaccinationTypeId, placeofapplicationId, dateofapplication, dateofexpiry, status, fileName1);
            if (candidatevaccineId <= 0) {
                int cc = talentpool.insertVaccinationdetail(info, candidateId, uId, userName);
                if (cc > 0) {
                    request.setAttribute("MESSAGE", "Data added successfully.");
                }
                request.getSession().removeAttribute("FILENAME");
                ArrayList list = talentpool.getCandVaccList(candidateId);
                request.setAttribute("CANDVACCLIST", list);
                return mapping.findForward("view_candidatevaccination");

            } else {
                int cc = talentpool.updateVaccinationRecord(info, candidateId, uId, userName);
                if (cc > 0) {
                    request.setAttribute("MESSAGE", "Data updated successfully.");
                }
                request.getSession().removeAttribute("FILENAME");
                ArrayList list = talentpool.getCandVaccList(candidateId);
                request.setAttribute("CANDVACCLIST", list);
                return mapping.findForward("view_candidatevaccination");
            }

        } else if (frm.getDoDeletevaccinationdetail() != null && frm.getDoDeletevaccinationdetail().equals("yes")) {
            frm.setDoDeletevaccinationdetail("no");
            int candidateId = frm.getCandidateId();
            frm.setCandidateId(candidateId);
            int candidatevaccineId = frm.getCandidatevaccineId();
            frm.setCandidatevaccineId(candidatevaccineId);
            int status = frm.getStatus();
            String ipAddrStr = request.getRemoteAddr();
            String iplocal = talentpool.getLocalIp();
            int cc = talentpool.deletevaccination(candidateId, candidatevaccineId, uId, status, ipAddrStr, iplocal, userName);
            if (cc > 0) {
                request.setAttribute("MESSAGE", "Status updated successfully");
            }
            ArrayList list = talentpool.getCandVaccList(candidateId);
            request.setAttribute("CANDVACCLIST", list);
            return mapping.findForward("view_candidatevaccination");

        } else if (frm.getDoViewgovdocumentlist() != null && frm.getDoViewgovdocumentlist().equals("yes")) {
            frm.setDoViewgovdocumentlist("no");
            int candidateId = frm.getCandidateId();
            frm.setCandidateId(candidateId);
            ArrayList list = talentpool.getCandgovdocList(candidateId);
            request.setAttribute("CANDGOVDOCLIST", list);
            return mapping.findForward("view_documentdetail");

        } else if (frm.getDomodifygovdocumentdetail() != null && frm.getDomodifygovdocumentdetail().equals("yes")) {
            frm.setDomodifygovdocumentdetail("no");
            int candidateId = frm.getCandidateId();
            int govdocumentId = frm.getGovdocumentId();
            frm.setCandidateId(candidateId);
            Collection documentTypes = ddl.getDocumentTypesForModule(1, 2);
            frm.setDocumentTypes(documentTypes);
            Collection countries = country.getCountrys();
            frm.setCountries(countries);
            frm.setCurrentDate(base.currDate3());
            if (govdocumentId <= 0) {
                Collection documentissuedbys = documentissuedby.getDocumentissuedbys(-1);
                frm.setDocumentissuedbys(documentissuedbys);
            }
            frm.setFname("");
            frm.setGovdocumentId(govdocumentId);
            if (govdocumentId > 0) {
                TalentpoolInfo info = talentpool.getcandgovForModify(govdocumentId);
                if (info != null) {
                    frm.setDocumentTypeId(info.getDocumenttypeId());
                    frm.setDocumentNo(info.getDocumentno());
                    frm.setCityName(info.getPlaceofissue());
                    frm.setPlaceofapplicationId(info.getPlaceofissueId());
                    Collection documentissuedbys = documentissuedby.getDocumentissuedbys(info.getDocumenttypeId());
                    frm.setDocumentissuedbys(documentissuedbys);
                    countries = country.getCountrys();
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

        } else if (frm.getDoSavegovdocumentdetail() != null && frm.getDoSavegovdocumentdetail().equals("yes")) {
            frm.setDoSavegovdocumentdetail("no");
            int candidateId = frm.getCandidateId();
            int govdocumentId = frm.getGovdocumentId();
            frm.setGovdocumentId(govdocumentId);
            int documentTypeId = frm.getDocumentTypeId();
            String documentno = validate.replacedesc(frm.getDocumentNo());
            int cityId = frm.getPlaceofapplicationId();
            int DocumentIssuedbyId = frm.getDocumentissuedbyId();
            String dateofissue = validate.replacedate(frm.getDateofissue());
            String dateofexpiry = validate.replacedate(frm.getDateofexpiry());
            String localFile = frm.getLocalFile();
            int status = 1;
            if (documentTypeId <= 0 || DocumentIssuedbyId <= 0) {
                Collection documentTypes = ddl.getDocumentTypesForModule(1, 2);
                Collection countries = country.getCountrys();
                frm.setCountries(countries);
                frm.setDocumentTypes(documentTypes);
                frm.setCountries(countries);
                Collection documentissuedbys = documentissuedby.getDocumentissuedbys(documentTypeId);
                frm.setDocumentissuedbys(documentissuedbys);
                frm.setGovdocumentId(govdocumentId);
                request.setAttribute("MESSAGE", "Something went wrong.");
                return mapping.findForward("modify_documentdetail");
            }
            int ck = talentpool.checkDuplicacygovdocument(candidateId, govdocumentId, documentTypeId);
            if (ck == 1) {
                Collection documentTypes = ddl.getDocumentTypesForModule(1, 2);
                Collection countries = country.getCountrys();
                frm.setDocumentTypes(documentTypes);
                frm.setCountries(countries);
                Collection documentissuedbys = documentissuedby.getDocumentissuedbys(documentTypeId);
                frm.setDocumentissuedbys(documentissuedbys);
                frm.setGovdocumentId(govdocumentId);
                request.setAttribute("MESSAGE", "Document already exists");
                return mapping.findForward("modify_documentdetail");
            }
            String add_candidate_file = talentpool.getMainPath("add_candidate_file");
            String foldername = talentpool.createFolder(add_candidate_file);
            Date now = new Date();
            String fn = String.valueOf(now.getTime());

            TalentpoolInfo info = new TalentpoolInfo(govdocumentId, documentTypeId, documentno, DocumentIssuedbyId, cityId, dateofissue, dateofexpiry, status);
            if (govdocumentId <= 0) {
                int cc = talentpool.insertGovdocumentdetail(info, candidateId, uId, userName);
                if (cc > 0) {
                    String fname = frm.getFname() != null ? frm.getFname() : "";
                    if (!"".equals(fname)) 
                    {
                        String fnameval[] = fname.split("@#@");
                        String localname[] = localFile.split("@#@");
                        int len = fnameval.length;
                        Connection conn = null;
                        try {
                            conn = candidate.getConnection();
                            for (int i = 0; i < len; i++) 
                            {
                                String fileName = candidate.saveImage(fnameval[i], add_candidate_file, foldername, talentpool.getfilename(localname[i])+"-"+fn);
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
                ArrayList list = talentpool.getCandgovdocList(candidateId);
                request.setAttribute("CANDGOVDOCLIST", list);
                return mapping.findForward("view_documentdetail");
            } else {
                int cc = talentpool.updateGovdocumentRecord(info, candidateId, uId, userName);
                if (cc > 0) {

                    String fname = frm.getFname() != null ? frm.getFname() : "";
                    if (!"".equals(fname)) {
                        String fnameval[] = fname.split("@#@");
                        String localname[] = localFile.split("@#@");
                        int len = fnameval.length;
                        Connection conn = null;
                        try {
                            conn = candidate.getConnection();
                            for (int i = 0; i < len; i++) {
                                String fileName = candidate.saveImage(fnameval[i], add_candidate_file, foldername, talentpool.getfilename(localname[i])+"-"+fn);
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
                ArrayList list = talentpool.getCandgovdocList(candidateId);
                request.setAttribute("CANDGOVDOCLIST", list);
                return mapping.findForward("view_documentdetail");
            }
        } else if (frm.getDoDeletegovdocumentdetail() != null && frm.getDoDeletegovdocumentdetail().equals("yes")) {
            frm.setDoDeletegovdocumentdetail("no");
            int candidateId = frm.getCandidateId();
            frm.setCandidateId(candidateId);
            int govdocumentId = frm.getGovdocumentId();
            frm.setGovdocumentId(govdocumentId);
            int status = frm.getStatus();
            String ipAddrStr = request.getRemoteAddr();
            String iplocal = talentpool.getLocalIp();
            int cc = talentpool.deletegovdocument(candidateId, govdocumentId, uId, status, ipAddrStr, iplocal, userName);
            if (cc > 0) {
                request.setAttribute("MESSAGE", "Status updated successfully");
            }
            ArrayList list = talentpool.getCandgovdocList(candidateId);
            request.setAttribute("CANDGOVDOCLIST", list);
            return mapping.findForward("view_documentdetail");
        } 
        else if (frm.getDoViewtrainingcertlist() != null && frm.getDoViewtrainingcertlist().equals("yes")) {
            frm.setDoViewtrainingcertlist("no");
            int candidateId = frm.getCandidateId();
            frm.setCandidateId(candidateId);
            ArrayList list = talentpool.gettrainingCertificatelist(candidateId, 0, count, courseIndex);
            int cnt = 0;
            if (list.size() > 0) {
                TalentpoolInfo cinfo = (TalentpoolInfo) list.get(list.size() - 1);
                cnt = cinfo.getCandidateId();
                list.remove(list.size() - 1);
            }
            request.getSession().setAttribute("CANDTRAININGCERTLIST", list);
            request.getSession().setAttribute("COUNT_CERTLIST", cnt + "");
            request.getSession().setAttribute("NEXTCERT", "0");
            request.getSession().setAttribute("NEXTCERTVALUE", "1");
            return mapping.findForward("view_trainingcertdetail");
        } else if (frm.getDoaddtrainingcertdetail() != null && frm.getDoaddtrainingcertdetail().equals("yes")) {
            frm.setDoaddtrainingcertdetail("no");
            int candidateId = frm.getCandidateId();
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
                TalentpoolInfo info = talentpool.gettrainingCertificateDetailById(trainingandcertId);
                if (info != null) {
                    frm.setCoursetypeId(info.getCoursetypeId());
                    frm.setCoursenameId(info.getCoursenameId());
                    frm.setEducationInstitute(info.getEduinstitute());
                    frm.setLocationofInstituteId(info.getLocationofInstituteId());
                    frm.setFieldofstudy(info.getFieldofstudy());
                    frm.setCoursestarted(info.getCoursestart());
                    frm.setPassingdate(info.getPassingyear());
                    frm.setDateofissue(info.getDateofissue());
                    frm.setCertificationno(info.getCertificateno());
                    frm.setDateofexpiry(info.getExpirydate());
                    frm.setCityName(info.getCity());
                    frm.setCourseverification(info.getCourseverification());
                    frm.setApprovedbyId(info.getApprovedbyid());
                    frm.setTrainingcerthiddenfile(info.getCertifilename());
                    frm.setCurrentDate(base.currDate3());
                    request.getSession().setAttribute("FILENAME", info.getCertifilename());
                }
            }
            return mapping.findForward("modify_trainingcertdetail");

        } else if (frm.getDoSavetrainingcertdetail() != null && frm.getDoSavetrainingcertdetail().equals("yes")) {
            frm.setDoSavetrainingcertdetail("no");
            int candidateId = frm.getCandidateId();
            int trainingandcertId = frm.getTrainingandcertId();
            frm.setTrainingandcertId(trainingandcertId);
            int coursetypeId = frm.getCoursetypeId();
            int coursenameId = frm.getCoursenameId();
            String educationInstitute = validate.replacename(frm.getEducationInstitute());
            int locationofInstituteId = frm.getLocationofInstituteId();
            String fieldofstudy = validate.replacedesc(frm.getFieldofstudy());
            String coursestarted = validate.replacedate(frm.getCoursestarted());
            String passingdate = validate.replacedate(frm.getPassingdate());
            String dateofissue = validate.replacedate(frm.getDateofissue());
            String certificationno = validate.replacedesc(frm.getCertificationno());
            String dateofexpiry = validate.replacedate(frm.getDateofexpiry());
            String courseverification = validate.replacename(frm.getCourseverification());
            int approvedbyId = frm.getApprovedbyId();
            int status = 1;
            int ck = talentpool.checkDuplicacytrainingCertificate(candidateId, trainingandcertId, coursetypeId, coursenameId, educationInstitute, dateofissue);
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
            String add_candidate_file = talentpool.getMainPath("add_candidate_file");
            String foldername = talentpool.createFolder(add_candidate_file);
            Date now = new Date();
            String fn = String.valueOf(now.getTime());
            String fileName1 = "", localname = "";
            FormFile filename = frm.getTrainingcertfile();
            if(filename != null)
            {
                localname = filename.getFileName().replaceAll("[/'\"]+", "");
                if(localname.contains("."))
                {
                    localname = localname.substring(0, localname.lastIndexOf("."));
                }
            }
            if (filename != null && filename.getFileSize() > 0) 
            {
                fileName1 = talentpool.uploadFile(trainingandcertId, frm.getTrainingcerthiddenfile(), filename, localname+"-"+fn, add_candidate_file, foldername);
            }
            TalentpoolInfo info = new TalentpoolInfo(trainingandcertId, coursetypeId, coursenameId, educationInstitute, locationofInstituteId, fieldofstudy, coursestarted,
                    passingdate, dateofissue, certificationno, dateofexpiry, courseverification, approvedbyId, status, fileName1);
            if (trainingandcertId <= 0) {
                int cc = talentpool.inserttrainingCertificate(info, candidateId, uId, userName);
                if (cc > 0) {
                    request.setAttribute("MESSAGE", "Data added successfully.");
                }
                request.getSession().removeAttribute("FILENAME");
                ArrayList list = talentpool.gettrainingCertificatelist(candidateId, 0, count, courseIndex);
                int cnt = 0;
                if (list.size() > 0) {
                    TalentpoolInfo cinfo = (TalentpoolInfo) list.get(list.size() - 1);
                    cnt = cinfo.getCandidateId();
                    list.remove(list.size() - 1);
                }
                request.getSession().setAttribute("CANDTRAININGCERTLIST", list);
                request.getSession().setAttribute("COUNT_CERTLIST", cnt + "");
                request.getSession().setAttribute("NEXTCERT", "0");
                request.getSession().setAttribute("NEXTCERTVALUE", "1");
                return mapping.findForward("view_trainingcertdetail");
            } else {
                int cc = talentpool.updatetrainingCertificate(info, candidateId, uId, userName);
                if (cc > 0) {
                    request.setAttribute("MESSAGE", "Data updated successfully.");
                }
                request.getSession().removeAttribute("FILENAME");
                ArrayList list = talentpool.gettrainingCertificatelist(candidateId, 0, count, courseIndex);
                int cnt = 0;
                if (list.size() > 0) {
                    TalentpoolInfo cinfo = (TalentpoolInfo) list.get(list.size() - 1);
                    cnt = cinfo.getCandidateId();
                    list.remove(list.size() - 1);
                }
                request.getSession().setAttribute("CANDTRAININGCERTLIST", list);
                request.getSession().setAttribute("COUNT_CERTLIST", cnt + "");
                request.getSession().setAttribute("NEXTCERT", "0");
                request.getSession().setAttribute("NEXTCERTVALUE", "1");
                return mapping.findForward("view_trainingcertdetail");
            }
        } else if (frm.getDoDeletetrainingcertdetail() != null && frm.getDoDeletetrainingcertdetail().equals("yes")) {
            frm.setDoDeletetrainingcertdetail("no");
            int candidateId = frm.getCandidateId();
            frm.setCandidateId(candidateId);
            int trainingandcertId = frm.getTrainingandcertId();
            frm.setTrainingandcertId(trainingandcertId);
            int status = frm.getStatus();
            String ipAddrStr = request.getRemoteAddr();
            String iplocal = talentpool.getLocalIp();
            int cc = talentpool.deletetrainingCertificate(candidateId, trainingandcertId, uId, status, ipAddrStr, iplocal, userName);
            if (cc > 0) {
                request.setAttribute("MESSAGE", "Status updated successfully");
            }
            ArrayList list = talentpool.gettrainingCertificatelist(candidateId, 0, count, courseIndex);
            int cnt = 0;
            if (list.size() > 0) {
                TalentpoolInfo cinfo = (TalentpoolInfo) list.get(list.size() - 1);
                cnt = cinfo.getCandidateId();
                list.remove(list.size() - 1);
            }
            request.getSession().setAttribute("CANDTRAININGCERTLIST", list);
            request.getSession().setAttribute("COUNT_CERTLIST", cnt + "");
            request.getSession().setAttribute("NEXTCERT", "0");
            request.getSession().setAttribute("NEXTCERTVALUE", "1");
            request.setAttribute("CANDTRAININGCERTLIST", list);
            return mapping.findForward("view_trainingcertdetail");
        } else if (frm.getDoVieweducationlist() != null && frm.getDoVieweducationlist().equals("yes")) {
            frm.setDoVieweducationlist("no");
            int candidateId = frm.getCandidateId();
            frm.setCandidateId(candidateId);
            ArrayList list = talentpool.geteducationlist(candidateId);
            request.setAttribute("CANDEDUCATIONLIST", list);
            return mapping.findForward("view_educationallist");
        } else if (frm.getDoaddeducationdetail() != null && frm.getDoaddeducationdetail().equals("yes")) {
            frm.setDoaddeducationdetail("no");
            int candidateId = frm.getCandidateId();
            frm.setCandidateId(candidateId);
            Collection qualificationtypes = qualificationtype.getQualificationTypes();
            Collection degrees = degree.getDegree();
            Collection countries = country.getCountrys();
            frm.setCountries(countries);
            frm.setCurrentDate(base.currDate3());
            frm.setQualificationtypes(qualificationtypes);
            frm.setDegrees(degrees);
            int educationdetailId = frm.getEducationdetailId();
            frm.setEducationdetailId(educationdetailId);
            request.getSession().removeAttribute("FILENAME");
            if (educationdetailId > 0) {
                TalentpoolInfo info = talentpool.geteducationDetailById(educationdetailId);
                if (info != null) {
                    frm.setKindId(info.getKindId());
                    frm.setDegreeId(info.getDegreeId());
                    frm.setBackgroundofstudy(info.getBackgroundofstudy());
                    frm.setEducationInstitute(info.getEduinstitute());
                    frm.setLocationofInstituteId(info.getLocationofInstituteId());
                    frm.setFieldofstudy(info.getFieldofstudy());
                    frm.setCoursestarted(info.getCoursestart());
                    frm.setPassingdate(info.getPassingyear());
                    frm.setHighestqualification(info.getHighestqualification());
                    frm.setEducationhiddenfile(info.getEducationalfilename());
                    frm.setCityName(info.getCity());
                    frm.setCurrentDate(base.currDate3());
                    request.getSession().setAttribute("FILENAME", info.getEducationalfilename());
                }
            }
            return mapping.findForward("modify_educationaldetail");

        } else if (frm.getDoSaveeducationdetail() != null && frm.getDoSaveeducationdetail().equals("yes")) {
            frm.setDoSaveeducationdetail("no");
            int candidateId = frm.getCandidateId();
            int educationdetailId = frm.getEducationdetailId();
            frm.setEducationdetailId(educationdetailId);
            int kindId = frm.getKindId();
            int degreeId = frm.getDegreeId();
            String backgroundofstudy = validate.replacename(frm.getBackgroundofstudy());
            String educationInstitute = validate.replacedesc(frm.getEducationInstitute());
            int locationofInstituteId = frm.getLocationofInstituteId();
            String fieldofstudy = validate.replacename(frm.getFieldofstudy());
            String coursestarted = validate.replacedate(frm.getCoursestarted());
            String passingdate = validate.replacedate(frm.getPassingdate());
            int highestqualification = frm.getHighestqualification();
            int status = 1;
            if (kindId <= 0 || degreeId <= 0) {
                Collection qualificationtypes = qualificationtype.getQualificationTypes();
                Collection degrees = degree.getDegree();
                Collection countries = country.getCountrys();
                frm.setCountries(countries);
                frm.setQualificationtypes(qualificationtypes);
                frm.setDegrees(degrees);
                frm.setEducationdetailId(educationdetailId);
                request.setAttribute("MESSAGE", "Something went wrong.");
                return mapping.findForward("modify_educationaldetail");
            }
            int ck = talentpool.checkDuplicacyeducation(candidateId, educationdetailId, kindId, degreeId, backgroundofstudy);
            if (ck == 1) {
                Collection qualificationtypes = qualificationtype.getQualificationTypes();
                Collection degrees = degree.getDegree();
                Collection countries = country.getCountrys();
                frm.setCountries(countries);
                frm.setCurrentDate(base.currDate3());
                frm.setQualificationtypes(qualificationtypes);
                frm.setDegrees(degrees);
                frm.setEducationdetailId(educationdetailId);
                request.setAttribute("MESSAGE", "Education already exists");
                return mapping.findForward("modify_educationaldetail");
            }
            String add_candidate_file = talentpool.getMainPath("add_candidate_file");
            String foldername = talentpool.createFolder(add_candidate_file);
            Date now = new Date();
            String fn = String.valueOf(now.getTime());
            String fileName1 = "", localname= "";
            FormFile filename = frm.getEducationfile();
            if(filename != null)
            {
                localname = filename.getFileName().replaceAll("[/'\"]+", "");
                if(localname.contains("."))
                    localname = localname.substring(0, localname.lastIndexOf("."));
            }
            if (filename != null && filename.getFileSize() > 0) {
                fileName1 = talentpool.uploadFile(educationdetailId, frm.getEducationhiddenfile(), filename, localname+"-"+fn, add_candidate_file, foldername);
            }
            TalentpoolInfo info = new TalentpoolInfo(educationdetailId, kindId, degreeId, backgroundofstudy, educationInstitute,
                    locationofInstituteId, fieldofstudy, coursestarted, passingdate, highestqualification, status, fileName1);
            if (educationdetailId <= 0) {
                int cc = talentpool.inserteducation(info, candidateId, uId, userName);
                if (cc > 0) {
                    request.setAttribute("MESSAGE", "Data added successfully.");
                }
                request.getSession().removeAttribute("FILENAME");
                ArrayList list = talentpool.geteducationlist(candidateId);
                request.setAttribute("CANDEDUCATIONLIST", list);
                return mapping.findForward("view_educationallist");
            } else {
                int cc = talentpool.updateeducation(info, candidateId, uId, userName);
                if (cc > 0) {
                    request.setAttribute("MESSAGE", "Data updated successfully.");
                }
                request.getSession().removeAttribute("FILENAME");
                ArrayList list = talentpool.geteducationlist(candidateId);
                request.setAttribute("CANDEDUCATIONLIST", list);
                return mapping.findForward("view_educationallist");
            }
        } else if (frm.getDoDeleteeducationdetail() != null && frm.getDoDeleteeducationdetail().equals("yes")) {
            frm.setDoDeleteeducationdetail("no");
            int candidateId = frm.getCandidateId();
            frm.setCandidateId(candidateId);
            int educationdetailId = frm.getEducationdetailId();
            frm.setEducationdetailId(educationdetailId);
            int status = frm.getStatus();
            String ipAddrStr = request.getRemoteAddr();
            String iplocal = talentpool.getLocalIp();
            int cc = talentpool.deleteeducation(candidateId, educationdetailId, uId, status, ipAddrStr, iplocal, userName);
            if (cc > 0) {
                request.setAttribute("MESSAGE", "Status updated successfully");
            }
            ArrayList list = talentpool.geteducationlist(candidateId);
            request.setAttribute("CANDEDUCATIONLIST", list);
            return mapping.findForward("view_educationallist");
        } else if (frm.getDoViewexperiencelist() != null && frm.getDoViewexperiencelist().equals("yes")) {
            frm.setDoViewexperiencelist("no");
            int candidateId = frm.getCandidateId();
            frm.setCandidateId(candidateId);
            ArrayList list = talentpool.getexperiencelist(candidateId);
            request.setAttribute("CANDEXPERIENCELIST", list);
            return mapping.findForward("view_experiencelist");
        } else if (frm.getDoaddexperiencedetail() != null && frm.getDoaddexperiencedetail().equals("yes")) {
            frm.setDoaddexperiencedetail("no");
            int candidateId = frm.getCandidateId();
            frm.setCandidateId(candidateId);
            Collection currencies = currency.getCurrencys();
            Collection companyindustries = companyindustry.getcompanyindustrys();
            Collection countries = country.getCountrys();
            assettypes = assettype.getAssettypes();
            Collection positions = candidate.getPositionassettypes(-1);
            Collection departments = experiencedept.getExperienceDepts();
            Collection waterdepths = waterdepth.getExperienceWaterDepths();
            Collection skills = skill.getSkills();
            Collection grades = grade.getGrades();
            Collection crewtypes = crewtype.getCrewtypes();
            frm.setCurrentDate(base.currDate3());
            frm.setCurrencies(currencies);
            frm.setCompanyindustries(companyindustries);
            frm.setCountries(countries);
            frm.setSkills(skills);
            frm.setGrades(grades);
            frm.setAssettypes(assettypes);
            frm.setPositions(positions);
            frm.setDepartments(departments);
            frm.setWaterdepths(waterdepths);
            frm.setCrewtypes(crewtypes);
            int experiencedetailId = frm.getExperiencedetailId();
            frm.setExperiencedetailId(experiencedetailId);
            request.getSession().removeAttribute("FILENAME");
            request.getSession().removeAttribute("FILENAME1");
            if (experiencedetailId > 0) {
                TalentpoolInfo info = talentpool.getexperiencedetailById(experiencedetailId);
                if (info != null) {
                    frm.setCompanyname(info.getCompanyname());
                    frm.setCompanyindustryId(info.getCompanyindustryId());
                    frm.setAssettypeId(info.getAssettypeId());
                    frm.setAssetname(info.getAssetName());
                    frm.setPositionId(info.getPositionId());
                    positions = candidate.getPositionassettypes(info.getAssettypeId());
                    frm.setPositions(positions);
                    frm.setDepartmentId(info.getDepartmentId());
                    frm.setCountryId(info.getCountryId());
                    frm.setCityId(info.getCityId());
                    frm.setCityName(info.getCity());
                    frm.setClientpartyname(info.getClientpartyname());
                    frm.setWaterdepthId(info.getWaterdepthId());
                    frm.setLastdrawnsalarycurrencyId(info.getLastdrawnsalarycurrencyId());
                    frm.setLastdrawnsalary(info.getLastdrawnsalary());
                    frm.setWorkenddate(info.getWorkenddate());
                    frm.setWorkstartdate(info.getWorkstartdate());
                    frm.setCurrentworkingstatus(info.getCurrentworkingstatus());
                    frm.setExperiencehiddenfile(info.getExperiencefilename());
                    frm.setWorkinghiddenfile(info.getWorkfilename());
                    frm.setCurrentDate(base.currDate3());
                    frm.setRole(info.getRole());
                    if (info.getCurrentworkingstatus() > 0) {
                        frm.setSkillsId(info.getSkillsId());
                        frm.setGradeid(info.getGradeId());
                        frm.setOwnerpool(info.getOwnerpool());
                        frm.setCrewtypeid(info.getCrewtypeid());
                        frm.setOcsemployed(info.getOcsemployed());
                        frm.setLegalrights(info.getLegalrights());
                        frm.setDayrate(info.getDayrate());
                        frm.setDayratecurrencyid(info.getDayratecurrencyid());
                        frm.setMonthlysalary(info.getMonthlysalary());
                        frm.setMonthlysalarycurrencyId(info.getMonthlysalarycurrencyId());
                    }
                    if(info.getRole()!= null && !info.getRole().equals(""))
                    {
                        String add_path = talentpool.getMainPath("add_candidate_file");
                        frm.setRolehiddenfile(info.getRole());
                        String str = talentpool.readHTMLFile(info.getRole(), add_path);
                        String rolestr = "";
                        rolestr = str.replaceAll("<ul>", "").replaceAll("</ul>", "").replaceAll("<li>", "").replaceAll("</li>", "\n");
                        frm.setRole(rolestr);
                    }
                    if (info.getRole() != null) {
                        frm.setRolehiddenfile(info.getRole());
                    }
                    request.getSession().setAttribute("FILENAME", info.getExperiencefilename());
                    request.getSession().setAttribute("FILENAME1", info.getWorkfilename());
                }
            }
            return mapping.findForward("modify_experiencedetail");
        } else if (frm.getDoSaveexperiencedetail() != null && frm.getDoSaveexperiencedetail().equals("yes")) {
            frm.setDoSaveexperiencedetail("no");
            int candidateId = frm.getCandidateId();
            int experiencedetailId = frm.getExperiencedetailId();
            frm.setExperiencedetailId(experiencedetailId);
            String companyName = validate.replacename(frm.getCompanyname());
            int comnpanyindustryId = frm.getCompanyindustryId();
            int assettypeId = frm.getAssettypeId();
            String assetname = validate.replacedesc(frm.getAssetname());
            int positionId = frm.getPositionId();
            int departmentId = frm.getDepartmentId();
            int countryId = frm.getCountryId();
            int cityId = frm.getCityId();
            String clientpartyname = validate.replacedesc(frm.getClientpartyname());
            int waterdepthId = frm.getWaterdepthId();
            String lastdrawnsalary = frm.getLastdrawnsalary();
            String workstartdate = validate.replacedate(frm.getWorkstartdate());
            String workenddate = validate.replacedate(frm.getWorkenddate());
            int currentworkstatus = frm.getCurrentworkingstatus();
            int skillId = frm.getSkillsId();
            int gradeId = frm.getGradeid();
            String ownerpool = validate.replacedesc(frm.getOwnerpool());
            int crewtypeId = frm.getCrewtypeid();
            String ocsemployed = validate.replacename(frm.getOcsemployed());
            String legalrights = validate.replacename(frm.getLegalrights());
            int dayrate = frm.getDayrate();
            int monthlysalary = frm.getMonthlysalary();
            int dayratecurrencyId = frm.getDayratecurrencyid();
            int monthlysalarycurrencyId = frm.getMonthlysalarycurrencyId();
            int lastdrawnsalarycurrencyId = frm.getLastdrawnsalarycurrencyId();
            String role = frm.getRole();
            String p1 = "", pstr ="";
            p1 = role.replaceAll("\n", "</li><li>");
            pstr = "<ul><li>"+p1+"</li></ul>";
            
            String rolehidden = frm.getRolehiddenfile();
            int status = 1;
            if (currentworkstatus > 0) {
                workenddate = "";
            }
            if (currentworkstatus <= 0) {
                skillId = 0;
                gradeId = 0;
                ownerpool = "";
                crewtypeId = 0;
                ocsemployed = "";
                legalrights = "";
                dayrate = 0;
                monthlysalary = 0;
                dayratecurrencyId = 0;
                monthlysalarycurrencyId = 0;
            }
            if (assettypeId <= 0 || comnpanyindustryId <= 0 || companyName.equals("") || workstartdate.equals("")) {
                Collection currencies = currency.getCurrencys();
                Collection companyindustries = companyindustry.getcompanyindustrys();
                Collection countries = country.getCountrys();
                assettypes = assettype.getAssettypes();
                Collection positions = position.getPositiontypes();
                Collection departments = experiencedept.getExperienceDepts();
                Collection waterdepths = waterdepth.getExperienceWaterDepths();
                Collection skills = skill.getSkills();
                Collection grades = grade.getGrades();
                Collection crewtypes = crewtype.getCrewtypes();
                frm.setCurrencies(currencies);
                frm.setCompanyindustries(companyindustries);
                frm.setCountries(countries);
                frm.setSkills(skills);
                frm.setGrades(grades);
                frm.setAssettypes(assettypes);
                frm.setPositions(positions);
                frm.setDepartments(departments);
                frm.setWaterdepths(waterdepths);
                frm.setCrewtypes(crewtypes);
                frm.setExperiencedetailId(experiencedetailId);
                request.setAttribute("MESSAGE", "Something went wrong.");
                return mapping.findForward("modify_experiencedetail");
            }
            int ck = talentpool.checkDuplicacyexperience(candidateId, experiencedetailId, companyName, workstartdate, workenddate);
            if (ck == 1) {
                Collection currencies = currency.getCurrencys();
                Collection companyindustries = companyindustry.getcompanyindustrys();
                Collection countries = country.getCountrys();
                assettypes = assettype.getAssettypes();
                Collection positions = position.getPositiontypes();
                Collection departments = experiencedept.getExperienceDepts();
                Collection waterdepths = waterdepth.getExperienceWaterDepths();
                Collection skills = skill.getSkills();
                Collection grades = grade.getGrades();
                Collection crewtypes = crewtype.getCrewtypes();
                frm.setCurrentDate(base.currDate3());
                frm.setCurrencies(currencies);
                frm.setCompanyindustries(companyindustries);
                frm.setCountries(countries);
                frm.setSkills(skills);
                frm.setGrades(grades);
                frm.setAssettypes(assettypes);
                frm.setPositions(positions);
                frm.setDepartments(departments);
                frm.setWaterdepths(waterdepths);
                frm.setCrewtypes(crewtypes);
                frm.setExperiencedetailId(experiencedetailId);
                request.setAttribute("MESSAGE", "Experience already exists");
                return mapping.findForward("modify_experiencedetail");
            }
            
            String add_path2 = talentpool.getMainPath("add_candidate_file");
            java.util.Date now2 = new java.util.Date();
            String fname2 = String.valueOf(now2.getTime());
            String htmlFolderName2 = talentpool.dateFolder();
            
            if (experiencedetailId > 0) {
                if (pstr != null && !pstr.equals("")) {
                    if (rolehidden != null && !rolehidden.equals("")) {
                        File f = new File(add_path2 + rolehidden);
                        if (f.exists()) {
                            f.delete();
                        }
                    }
                    pstr = talentpool.writeHTMLFile(pstr, add_path2 + htmlFolderName2, fname2 + ".html");
                    pstr = htmlFolderName2 + "/" + pstr;
                }
            } else if (pstr != null && !pstr.equals("")) {
                pstr = talentpool.writeHTMLFile(pstr, add_path2 + htmlFolderName2, fname2 + ".html");
                pstr = htmlFolderName2 + "/" + pstr;
            }
            String add_candidate_file = talentpool.getMainPath("add_candidate_file");
            String foldername = talentpool.createFolder(add_candidate_file);
            Date now = new Date();
            String fn = String.valueOf(now.getTime());
            String fileName1 = "", localname= "";
            FormFile filename = frm.getExperiencefile();
            if(filename != null)
            {
                localname = filename.getFileName().replaceAll("[/'\"]+", "");
                if(localname.contains("."))
                    localname = localname.substring(0, localname.lastIndexOf("."));
            }
            if (filename != null && filename.getFileSize() > 0) {
                fileName1 = talentpool.uploadFile(experiencedetailId, frm.getExperiencehiddenfile(), filename, localname+"-"+fn, add_candidate_file, foldername);
            }
            String fn1 = String.valueOf(now.getTime());
            String fileName2 = "", localname2= "";
            FormFile filename2 = frm.getWorkingfile();
            if(filename2 != null)
            {
                localname2 = filename2.getFileName().replaceAll("[/'\"]+", "");
                if(localname2.contains("."))
                    localname2 = localname2.substring(0, localname2.lastIndexOf("."));
            }
            if (filename2 != null && filename2.getFileSize() > 0) {
                fileName2 = talentpool.uploadFile(experiencedetailId, frm.getWorkinghiddenfile(), filename2, localname2+"-"+fn1, add_candidate_file, foldername);
            }
            
            TalentpoolInfo info = new TalentpoolInfo(experiencedetailId, companyName, comnpanyindustryId, assettypeId, assetname, positionId, departmentId, countryId,
                    cityId, clientpartyname, waterdepthId, lastdrawnsalary, workstartdate, workenddate, currentworkstatus, skillId, gradeId, ownerpool, crewtypeId,
                    ocsemployed, legalrights, dayrate, monthlysalary, dayratecurrencyId, monthlysalarycurrencyId, lastdrawnsalarycurrencyId, status, fileName1, fileName2, pstr);
            if (experiencedetailId <= 0) {
                int cc = talentpool.insertexperiencedetail(info, candidateId, uId, userName);
                if (cc > 0) {
                    request.setAttribute("MESSAGE", "Data added successfully.");
                }
                request.getSession().removeAttribute("FILENAME");
                request.getSession().removeAttribute("FILENAME1");
                ArrayList list = talentpool.getexperiencelist(candidateId);
                request.setAttribute("CANDEXPERIENCELIST", list);
                return mapping.findForward("view_experiencelist");
            } else {
                int cc = talentpool.updateexperiencedetail(info, candidateId, uId, userName);
                if (cc > 0) {
                    request.setAttribute("MESSAGE", "Data updated successfully.");
                }
                request.getSession().removeAttribute("FILENAME");
                request.getSession().removeAttribute("FILENAME1");
                ArrayList list = talentpool.getexperiencelist(candidateId);
                request.setAttribute("CANDEXPERIENCELIST", list);
                return mapping.findForward("view_experiencelist");
            }
        } else if (frm.getDoDeleteexperiencedetail() != null && frm.getDoDeleteexperiencedetail().equals("yes")) {
            frm.setDoDeleteexperiencedetail("no");
            int candidateId = frm.getCandidateId();
            frm.setCandidateId(candidateId);
            int experiencedetailId = frm.getExperiencedetailId();
            frm.setExperiencedetailId(experiencedetailId);
            int status = frm.getStatus();
            String ipAddrStr = request.getRemoteAddr();
            String iplocal = talentpool.getLocalIp();
            int cc = talentpool.deleteexperiencedetail(candidateId, experiencedetailId, uId, status, ipAddrStr, iplocal, userName);
            if (cc > 0) {
                request.setAttribute("MESSAGE", "Status updated successfully");
            }
            ArrayList list = talentpool.getexperiencelist(candidateId);
            request.setAttribute("CANDEXPERIENCELIST", list);
            return mapping.findForward("view_experiencelist");
        } else if (frm.getDoViewVerificationlist() != null && frm.getDoViewVerificationlist().equals("yes")) {
            frm.setDoViewVerificationlist("no");
            int candidateId = frm.getCandidateId();
            frm.setCandidateId(candidateId);
            TalentpoolInfo info = talentpool.getVerificationStatusbyCandidateId(candidateId);
            request.setAttribute("VERIFICATIONINFO", info);
            ArrayList list = talentpool.getVerificationList(candidateId);
            request.setAttribute("VERIFICATIONLIST", list);
            return mapping.findForward("view_verificationlist");
        } else if (frm.getDoViewAssessmentlist() != null && frm.getDoViewAssessmentlist().equals("yes")) {
            frm.setDoViewAssessmentlist("no");
            int candidateId = frm.getCandidateId();
            frm.setCandidateId(candidateId);
            ArrayList list = talentpool.getCassessmenthistoryByCandidateId(candidateId);
            request.setAttribute("ASSESSMENTLIST", list);
            ArrayList alist = talentpool.getCassessmentAssessNowhistoryByCandidateId(candidateId);
            request.setAttribute("ASSESSNOWLIST", alist);
            return mapping.findForward("view_assessmentlist");
        } else if (frm.getDoViewCompliancelist() != null && frm.getDoViewCompliancelist().equals("yes")) {
            frm.setDoViewCompliancelist("no");
            int candidateId = frm.getCandidateId();
            frm.setCandidateId(candidateId);
            ArrayList list = talentpool.getCompliancehistoryByCandidateId(candidateId);
            request.setAttribute("COMPLIANCELIST", list);
            return mapping.findForward("view_compliancelist");
        } else if (frm.getDoViewShortlistinglist() != null && frm.getDoViewShortlistinglist().equals("yes")) {
            frm.setDoViewShortlistinglist("no");
            int candidateId = frm.getCandidateId();
            frm.setCandidateId(candidateId);
            ArrayList list = talentpool.getShortlistinghistoryByCandidateId(candidateId);
            request.setAttribute("SHORTLISTINGLIST", list);
            return mapping.findForward("view_shortlisting");
        } else if (frm.getDoViewClientselectionlist() != null && frm.getDoViewClientselectionlist().equals("yes")) {
            frm.setDoViewClientselectionlist("no");
            int candidateId = frm.getCandidateId();
            frm.setCandidateId(candidateId);
            ArrayList list = talentpool.getClientselectionhistoryByCandidateId(candidateId);
            request.setAttribute("CLIENTSELHISTLIST", list);
            return mapping.findForward("view_clientselectionlist");
        } else if (frm.getDoViewOnboardinglist() != null && frm.getDoViewOnboardinglist().equals("yes")) {
            frm.setDoViewOnboardinglist("no");
            int candidateId = frm.getCandidateId();
            frm.setCandidateId(candidateId);
            ArrayList list = talentpool.getOnboardinghistoryByCandidateId(candidateId);
            request.setAttribute("ONBOARDINGHISTLIST", list);
            ArrayList onboard_list = ddl.getDirectOnboardHistory(candidateId);
            request.setAttribute("ONBOARD_HISTLIST", onboard_list);
            return mapping.findForward("view_onbording");
        } else if (frm.getDoViewMobilizationlist() != null && frm.getDoViewMobilizationlist().equals("yes")) {
            frm.setDoViewMobilizationlist("no");
            int candidateId = frm.getCandidateId();
            frm.setCandidateId(candidateId);
            ArrayList list = talentpool.getMobilizationhistoryByCandidateId(candidateId);
            request.setAttribute("MOBILIZATIONHISTLIST", list);
            return mapping.findForward("view_mobilization");
        } else if (frm.getDoViewRotationlist() != null && frm.getDoViewRotationlist().equals("yes")) {
            frm.setDoViewRotationlist("no");
            int candidateId = frm.getCandidateId();
            frm.setCandidateId(candidateId);
            ArrayList list = talentpool.getRotationhistoryByCandidateId(candidateId);
            request.setAttribute("ROTATIONHISTLIST", list);
            return mapping.findForward("view_rotation");
        } else if (frm.getDoViewNotificationlist() != null && frm.getDoViewNotificationlist().equals("yes")) {
            frm.setDoViewNotificationlist("no");
            int candidateId = frm.getCandidateId();
            frm.setCandidateId(candidateId);
            ArrayList list = talentpool.getNotificationhistory(candidateId);
            request.setAttribute("NOTIFICATIONLIST", list);
            return mapping.findForward("view_notificationlist");
        } else if (frm.getDoViewTransferterminationlist() != null && frm.getDoViewTransferterminationlist().equals("yes")) {
            frm.setDoViewTransferterminationlist("no");
            int candidateId = frm.getCandidateId();
            frm.setCandidateId(candidateId);
            ArrayList list = talentpool.getTransferTerminationhistory(candidateId);
            request.setAttribute("TRANSFERTERMINATIONLIST", list);
            return mapping.findForward("view_transferterminationlist");

        } else if (frm.getDoViewWellnessfeedbacklist() != null && frm.getDoViewWellnessfeedbacklist().equals("yes")) {
            frm.setDoViewWellnessfeedbacklist("no");
            int candidateId = frm.getCandidateId();
            frm.setCandidateId(candidateId);
            int statusId = frm.getStatusIndex();
            frm.setStatusIndex(statusId);
            ArrayList list = talentpool.getWellnessFeedbackhistory(candidateId, statusId, 0, count);
            int cnt = 0;
            if (list.size() > 0) {
                TalentpoolInfo cinfo = (TalentpoolInfo) list.get(list.size() - 1);
                cnt = cinfo.getCandidateId();
                list.remove(list.size() - 1);
            }
            request.getSession().setAttribute("WELLNESSFEEDBACKHISTLIST", list);
            request.getSession().setAttribute("COUNT_FEEDBACKLIST", cnt + "");
            request.getSession().setAttribute("NEXTFEEDBACK", "0");
            request.getSession().setAttribute("NEXTFEEDBACKVALUE", "1");
            return mapping.findForward("view_wellnessfeedbacklist");
        } else if (frm.getDoViewCompetencylist() != null && frm.getDoViewCompetencylist().equals("yes")) {
            frm.setDoViewCompetencylist("no");
            int candidateId = frm.getCandidateId();
            frm.setCandidateId(candidateId);
            TalentpoolInfo tinfo = null;
            int clientIdcom = 0, assetIdcom = 0, positionIdcom = 0;
            if (request.getSession().getAttribute("CANDIDATE_DETAIL") != null) {
                tinfo = (TalentpoolInfo) request.getSession().getAttribute("CANDIDATE_DETAIL");
                if (tinfo != null) {
                    clientIdcom = tinfo.getClientId();
                    assetIdcom = tinfo.getToAssetId();
                    positionIdcom = tinfo.getPositionId();
                }
            }
            int pdeptId = ddl.getPdeptId(assetIdcom, positionIdcom);
            clientassets = talentpool.getClientAsset(clientIdcom, assetids, allclient, permission);
            frm.setClientassets(clientassets);
            Collection positionscom = ddl.getCopetencyPositions(assetIdcom, pdeptId);
            frm.setPositions(positionscom);
            Collection depts = ddl.getCopetencyDepartment(assetIdcom);
            frm.setDepts(depts);
            frm.setClientIdcom(clientIdcom);
            frm.setAssetIdcom(assetIdcom);
            frm.setPdeptId(pdeptId);
            frm.setPositionIdcom(positionIdcom);
            ArrayList list = ddl.getCompetencyTrackerList(candidateId, 0, count, clientIdcom, assetIdcom, pdeptId, positionIdcom);
            int cnt = 0;
            if (list.size() > 0) {
                TalentpoolInfo cinfo = (TalentpoolInfo) list.get(list.size() - 1);
                cnt = cinfo.getCandidateId();
                list.remove(list.size() - 1);
            }
            request.getSession().setAttribute("COMPETENCYLISTHISTLIST", list);
            request.getSession().setAttribute("COUNT_COMPETENCYLIST", cnt + "");
            request.getSession().setAttribute("NEXTCOMPETENCY", "0");
            request.getSession().setAttribute("NEXTCOMPETENCYVALUE", "1");
            return mapping.findForward("view_competencylist");
        } //For offshore update
        else if (frm.getDoViewoffshoredetail() != null && frm.getDoViewoffshoredetail().equals("yes")) {
            frm.setDoViewoffshoredetail("no");
            int candidateId = frm.getCandidateId();
            ArrayList list = talentpool.getOffshoreList(candidateId);
            request.setAttribute("OFFSHORELIST", list);
            return mapping.findForward("view_offshorelist");//view.page page
        } else if (frm.getDoaddoffshoredetail() != null && frm.getDoaddoffshoredetail().equals("yes")) {
            frm.setDoaddoffshoredetail("no");
            int candidateId = frm.getCandidateId();
            frm.setCandidateId(candidateId);
            int offshoreId = frm.getOffshoreId();
            frm.setOffshoreId(offshoreId);
            int clientId = frm.getClientId();
            int clientAssetId = frm.getClientassetId();
            frm.setClientId(clientId);
            clients = talentpool.getClients(cids, allclient, permission);
            frm.setClients(clients);
            Collection assets = talentpool.getClientAsset(clientId, assetids, allclient, permission);
            frm.setAssets(assets);
            frm.setClientassetId(clientAssetId);
            frm.setRemarktypes(remarktype.getRemarks());
            frm.setCurrentDate(base.currDate3());
            request.getSession().removeAttribute("OFFSHOREFILE");
            if (offshoreId > 0) {
                TalentpoolInfo info = talentpool.getOffshoreUpdateForModify(offshoreId);
                if (info != null) {
                    frm.setRemarktypes(remarktype.getRemarks());
                    frm.setRemarktypeId(info.getRemarktypeId());
                    frm.setClientId(info.getClientId());
                    frm.setClientassetId(info.getClientassetId());
                    frm.setRemark(info.getRemarks());
                    frm.setDate(info.getDate());
                    clientId = frm.getClientId();
                    clientAssetId = frm.getClientassetId();
                    frm.setClientId(clientId);
                    clients = talentpool.getClients(cids, allclient, permission);
                    frm.setClients(clients);
                    assets = talentpool.getClientAsset(clientId, assetids, allclient, permission);
                    frm.setAssets(assets);
                    frm.setClientassetId(clientAssetId);
                    if (info.getOffshorefile() != null) {
                        frm.setOffshorefilehidden(info.getOffshorefile());
                    }
                    frm.setCurrentDate(base.currDate3());
                    request.getSession().setAttribute("OFFSHOREFILE", info.getOffshorefile());
                }
            }
            return mapping.findForward("edit_offshore");
        } else if (frm.getDoSaveoffshoredetail() != null && frm.getDoSaveoffshoredetail().equals("yes")) {
            frm.setDoSaveoffshoredetail("no");
            int candidateId = frm.getCandidateId();
            int offshoreId = frm.getOffshoreId();
            frm.setCandidateId(candidateId);
            int status = 1;
            int clientId = frm.getClientId();
            int clientassetId = frm.getClientassetId();
            int remarktypeId = frm.getRemarktypeId();
            String remark = frm.getRemark();
            String date = frm.getDate();
            String add_offshore_file = talentpool.getMainPath("add_offshore_file");
            String foldername = talentpool.createFolder(add_offshore_file);
            Date now = new Date();
            String fn = String.valueOf(now.getTime());
            String fileName1 = "", localname = "";
            FormFile filename = frm.getOffshorefile();
            if(filename  != null)
            {
                localname = filename.getFileName().replaceAll("[/'\"]+", "");
                if(localname.contains(".")){
                    localname = localname.substring(0, localname.lastIndexOf("."));
                }
            }
            if (filename != null && filename.getFileSize() > 0) {
                fileName1 = talentpool.uploadFile(offshoreId, frm.getOffshorefilehidden(), filename, localname+"-"+fn, add_offshore_file, foldername);
            }
            TalentpoolInfo info = new TalentpoolInfo(candidateId, offshoreId, clientId, clientassetId, remarktypeId, remark, status, fileName1, date);
            if (offshoreId <= 0) {
                int cc = talentpool.insertOffshoreDetail(info, candidateId, uId);
                if (cc > 0) {
                    request.setAttribute("MESSAGE", "Data added successfully.");
                }
                request.getSession().removeAttribute("OFFSHOREFILE");
                ArrayList list = talentpool.getOffshoreList(candidateId);
                request.setAttribute("OFFSHORELIST", list);
                return mapping.findForward("view_offshorelist");//view.page page
            } else {
                int cc = talentpool.updateOffshore(info, candidateId, uId);
                if (cc > 0) {
                    request.setAttribute("MESSAGE", "Data updated successfully.");
                }
                request.getSession().removeAttribute("FILENAME");
                ArrayList list = talentpool.getOffshoreList(candidateId);
                request.setAttribute("OFFSHORELIST", list);
                return mapping.findForward("view_offshorelist");
            }
        } else if (frm.getDoDeleteoffshoredetail() != null && frm.getDoDeleteoffshoredetail().equals("yes")) {
            frm.setDoDeleteoffshoredetail("no");
            int candidateId = frm.getCandidateId();
            int offshoreId = frm.getOffshoreId();
            frm.setCandidateId(candidateId);
            frm.setOffshoreId(offshoreId);
            int status = frm.getStatus();
            String ipAddrStr = request.getRemoteAddr();
            String iplocal = talentpool.getLocalIp();
            int cc = talentpool.deleteOffshoreDetail(candidateId, offshoreId, uId, status, ipAddrStr, iplocal);
            if (cc > 0) {
                request.setAttribute("MESSAGE", "Status updated successfully");
            }
            ArrayList list = talentpool.getOffshoreList(candidateId);
            request.setAttribute("OFFSHORELIST", list);
            return mapping.findForward("view_offshorelist");
        } //-------for nominee tab
        else if (frm.getDoViewNomineelist() != null && frm.getDoViewNomineelist().equals("yes")) 
        {
            frm.setDoViewNomineelist("no");
            frm.setNomineedetailId(-1);
            int candidateId = frm.getCandidateId();
            frm.setCandidateId(candidateId);
            ArrayList list = talentpool.getNomineeList(candidateId);
            request.setAttribute("CANDNOMINEELIST", list);
            return mapping.findForward("view_candidatenomineelist");
        } else if (frm.getDoManageNomineedetail() != null && frm.getDoManageNomineedetail().equals("yes")) {
            frm.setDoManageNomineedetail("no");
            int nomineeDetailId = frm.getNomineedetailId();
            frm.setNomineedetailId(nomineeDetailId);
            frm.setCandidateId(frm.getCandidateId());
            Collection relations = relation.getRelations();
            frm.setRelations(relations);
            int relationId = frm.getRelationId();
            frm.setRelationId(relationId);
            Collection countries = country.getCountrys();
            frm.setCountries(countries);
            if (frm.getNomineedetailId() > 0) 
            {
                TalentpoolInfo info = talentpool.getNomineeDetailById(nomineeDetailId);
                if (info != null) 
                {
                    if (info.getNomineeName() != null) {
                        frm.setNomineeName(info.getNomineeName());
                    }
                    if (info.getNomineeRelation() != null) {
                        frm.setNomineeRelation(info.getNomineeRelation());
                    }
                    if (info.getNomineeContactno() != null) {
                        frm.setNomineeContactno(info.getNomineeContactno());
                    }
                    if (info.getRelationId() > 0) {
                        frm.setRelationId(info.getRelationId());
                    }
                    if (info.getCode1Id() != null) {
                        frm.setCode1Id(info.getCode1Id());
                    }
                    if (info.getAddress()!= null) {
                        frm.setAddress(info.getAddress());
                    }
                    frm.setAge(info.getAge());
                    frm.setPercentage(info.getPercentage());
                }
            }
            return mapping.findForward("add_nomineedetail");
        } else if (frm.getDoSaveNomineedetail() != null && frm.getDoSaveNomineedetail().equals("yes")) {
            frm.setDoSaveNomineedetail("no");
            int nomineedetailId = frm.getNomineedetailId();
            String nomineeName = validate.replacename(frm.getNomineeName());
            String nomineeContactno = validate.replacename(frm.getNomineeContactno());
            int candidateId = frm.getCandidateId();
            frm.setCandidateId(candidateId);
            int relationId = frm.getRelationId();
            frm.setRelationId(relationId);
            String address = validate.replacedesc(frm.getAddress());
            int age = frm.getAge();
            double percentage = frm.getPercentage();
            int status = 1;
            String code1 = validate.replaceint(frm.getCode1Id());
            int ck = talentpool.checkDuplicacyNominee(candidateId, nomineedetailId, nomineeName);
            if (ck == 1) 
            {
                Collection relations = relation.getRelations();
                frm.setRelations(relations);
                relationId = frm.getRelationId();
                frm.setRelationId(relationId);
                Collection countries = country.getCountrys();
                frm.setCountries(countries);
                frm.setCode1Id(frm.getCode1Id());
                request.setAttribute("MESSAGE", "Nominee already exists");
                return mapping.findForward("add_nomineedetail");
            }
            TalentpoolInfo info = new TalentpoolInfo(candidateId, nomineedetailId, nomineeName, nomineeContactno, 
                    relationId, status, code1, address, age, percentage);
            frm.setNomineedetailId(-1);
            if (nomineedetailId <= 0) {
                int cc = talentpool.createNomineedetails(info, candidateId, uId);
                if (cc > 0) {
                    request.setAttribute("MESSAGE", "Data added successfully.");
                }
                ArrayList list = talentpool.getNomineeList(candidateId);
                request.setAttribute("CANDNOMINEELIST", list);
                return mapping.findForward("view_candidatenomineelist");
            } else {
                int cc = talentpool.updateNominee(info, uId, candidateId, nomineedetailId);
                if (cc > 0) {
                    request.setAttribute("MESSAGE", "Data updated successfully.");
                }
                ArrayList list = talentpool.getNomineeList(candidateId);
                request.setAttribute("CANDNOMINEELIST", list);
                return mapping.findForward("view_candidatenomineelist");
            }
        } else if (frm.getDoDeleteNomineedetail() != null && frm.getDoDeleteNomineedetail().equals("yes")) {
            frm.setDoDeleteNomineedetail("no");
            int candidateId = frm.getCandidateId();
            frm.setCandidateId(candidateId);
            int nomineedetailId = frm.getNomineedetailId();
            frm.setNomineedetailId(nomineedetailId);
            int status = frm.getStatus();
            String ipAddrStr = request.getRemoteAddr();
            String iplocal = talentpool.getLocalIp();
            int cc = talentpool.deleteNominee(candidateId, nomineedetailId, uId, status, ipAddrStr, iplocal);
            if (cc > 0) {
                request.setAttribute("MESSAGE", "Status updated successfully");
            }
            ArrayList list = talentpool.getNomineeList(candidateId);
            request.setAttribute("CANDNOMINEELIST", list);
            return mapping.findForward("view_candidatenomineelist");

        } //-------for PPE tab
        else if (frm.getDoViewPpelist() != null && frm.getDoViewPpelist().equals("yes")) {
            frm.setDoViewPpelist("no");
            frm.setPpedetailId(-1);
            int candidateId = frm.getCandidateId();
            frm.setCandidateId(candidateId);
            ArrayList list = talentpool.getPpeList(candidateId);
            request.setAttribute("CANDPPELIST", list);
            return mapping.findForward("view_ppelist");
        } else if (frm.getDoManagePpedetail() != null && frm.getDoManagePpedetail().equals("yes")) {
            frm.setDoManagePpedetail("no");
            int ppeDetailId = frm.getPpedetailId();
            frm.setPpedetailId(ppeDetailId);
            frm.setCandidateId(frm.getCandidateId());
            Collection ppetypes = ppe.getPpetypes();
            frm.setPpetypes(ppetypes);
            int ppetypeId = frm.getPpetypeId();
            frm.setPpetypeId(ppetypeId);
            String remark = frm.getRemark();
            frm.setRemark(remark);
            if (frm.getPpedetailId() > 0) {
                TalentpoolInfo info = talentpool.getPpeDetailById(ppeDetailId);
                if (info != null) {
                    if (info.getPpetypeId() > 0) {
                        frm.setPpetypeId(info.getPpetypeId());
                    }
                    if (info.getRemarks() != null) {
                        frm.setRemark(info.getRemarks());
                    }
                }
            }
            return mapping.findForward("add_ppedetail");
        } else if (frm.getDoSavePpedetail() != null && frm.getDoSavePpedetail().equals("yes")) {
            frm.setDoSavePpedetail("no");
            int ppedetailId = frm.getPpedetailId();
            String remarks = frm.getRemark();
            int candidateId = frm.getCandidateId();
            int ppetypeId = frm.getPpetypeId();
            int status = 1;
            int ck = talentpool.checkDuplicacyPpe(candidateId, ppedetailId, remarks, ppetypeId);
            if (ck == 1) {
                Collection ppetypes = ppe.getPpetypes();
                frm.setPpetypes(ppetypes);
                ppetypeId = frm.getPpetypeId();
                frm.setPpetypeId(ppetypeId);
                remarks = frm.getRemark();
                frm.setRemark(remarks);
                request.setAttribute("MESSAGE", "PPE already exists");
                return mapping.findForward("add_ppedetail");
            }
            TalentpoolInfo info = new TalentpoolInfo(candidateId, ppetypeId, remarks, status);
            frm.setPpedetailId(-1);
            if (ppedetailId <= 0) {
                int cc = talentpool.createPpedetails(info, candidateId, uId);
                if (cc > 0) {
                    request.setAttribute("MESSAGE", "Data added successfully.");
                }
                ArrayList list = talentpool.getPpeList(candidateId);
                request.setAttribute("CANDPPELIST", list);
                return mapping.findForward("view_ppelist");
            } else {
                int cc = talentpool.updatePpe(info, uId, candidateId, ppedetailId);
                if (cc > 0) {
                    request.setAttribute("MESSAGE", "Data updated successfully.");
                }
                ArrayList list = talentpool.getPpeList(candidateId);
                request.setAttribute("CANDPPELIST", list);
                return mapping.findForward("view_ppelist");
            }
        } else if (frm.getDoDeletePpedetail() != null && frm.getDoDeletePpedetail().equals("yes")) {
            frm.setDoDeletePpedetail("no");
            int candidateId = frm.getCandidateId();
            frm.setCandidateId(candidateId);
            int ppedetailId = frm.getPpedetailId();
            frm.setPpedetailId(ppedetailId);
            int status = frm.getStatus();
            String ipAddrStr = request.getRemoteAddr();
            String iplocal = talentpool.getLocalIp();
            int cc = talentpool.deletePpe(candidateId, ppedetailId, uId, status, ipAddrStr, iplocal);
            if (cc > 0) {
                request.setAttribute("MESSAGE", "Status updated successfully");
            }
            ArrayList list = talentpool.getPpeList(candidateId);
            request.setAttribute("CANDPPELIST", list);
            return mapping.findForward("view_ppelist");
        } //-------for Contract tab
        else if (frm.getDoViewContractlist() != null && frm.getDoViewContractlist().equals("yes")) {
            frm.setDoViewContractlist("no");
            frm.setContractdetailId(-1);
            int candidateId = frm.getCandidateId();
            frm.setCandidateId(candidateId);
            clients = talentpool.getClients(cids, allclient, permission);
            frm.setClients(clients);
            int clientIdContract = frm.getClientIdContract();
            frm.setClientIdContract(clientIdContract);
            clientassets = talentpool.getClientAsset(clientIdContract, assetids, allclient, permission);
            frm.setClientassets(clientassets);
            int assetIdContract = frm.getAssetIdContract();
            frm.setAssetIdContract(assetIdContract);
            ArrayList list = ddl.getContractListing(candidateId, -1, -1, -1, "", "", 0, count);
            int cnt = 0;
            if (list.size() > 0) {
                TalentpoolInfo cinfo = (TalentpoolInfo) list.get(list.size() - 1);
                cnt = cinfo.getContractdetailId();
                list.remove(list.size() - 1);
            }
            request.getSession().setAttribute("CONTRACTLIST", list);
            request.getSession().setAttribute("COUNT_CONTRACT", cnt + "");
            request.getSession().setAttribute("NEXTCONTRACT", "0");
            request.getSession().setAttribute("NEXTCONTRACTVALUE", "1");            
            return mapping.findForward("view_contractlist");
        } else if (frm.getDoManageContractdetail() != null && frm.getDoManageContractdetail().equals("yes")) {
            frm.setDoManageContractdetail("no");
            int contractDetailId = frm.getContractdetailId();
            frm.setContractdetailId(contractDetailId);
            frm.setCandidateId(frm.getCandidateId());
            int positionId = frm.getPositionId();
            frm.setPositionId(positionId);
            int assetId = frm.getClientassetId();
            frm.setClientassetId(assetId);            
            frm.setContractId(-1);
            int refId = frm.getRefId();
            frm.setRefId(refId);
            int type = frm.getType();
            frm.setType(type);
            Collection contracts = talentpool.getContracts(assetId, 1, -1, -1);
            frm.setContracts(contracts);
            frm.setCurrentDate(base.currDate3());
            return mapping.findForward("add_contractdetail");
        } //for repeat contract
        else if (frm.getRepeatContract() != null && frm.getRepeatContract().equals("yes")) {
            frm.setRepeatContract("no");
            int contractDetailId = frm.getContractdetailId();
            frm.setContractdetailId(contractDetailId);
            frm.setCandidateId(frm.getCandidateId());
            int positionId = frm.getPositionId();
            frm.setPositionId(positionId);
            int assetId = frm.getClientassetId();
            frm.setClientassetId(assetId);
            int contractId = frm.getContractId();
            frm.setContractId(contractId);
            int refId = frm.getRefId();
            frm.setRefId(refId);
            int type = frm.getType();
            frm.setType(type);
            Collection contracts = talentpool.getContracts(assetId, type, contractId, refId);
            frm.setContracts(contracts);
            frm.setCurrentDate(base.currDate3());
            return mapping.findForward("add_contractdetail");
        } //-------for contract
        else if (frm.getDoGenerateContarct() != null && frm.getDoGenerateContarct().equals("yes")) {
            frm.setDoGenerate("no");
            print(this, " getDoGenerateContarct block :: ");
            frm.setClientId(-1);
            int candidateId = frm.getCandidateId();
            frm.setCandidateId(candidateId);
            int assetId = frm.getClientassetId();
            Collection contracts = contract.getContractList(assetId);
            frm.setContracts(contracts);
            frm.setCurrentDate(base.currDate3());
            return mapping.findForward("add_contractdetail");
        } //for modify contract
        else if (frm.getDoGenerateContarct() != null && frm.getDoGenerateContarct().equals("yes")) {
            frm.setDoGenerateContarct("no");
            print(this, " getDoGenerateContarct block :: ");
            int contractdetailId = frm.getContractdetailId();
            frm.setContractdetailId(contractdetailId);
            int contractId = frm.getContractId();
            frm.setContractId(contractId);
            int assetId = frm.getClientassetId();
            int candidateId = frm.getCandidateId();
            frm.setCandidateId(candidateId);
            Collection contracts = contract.getContractList(assetId);
            frm.setContracts(contracts);
            String pdffilename = "";
            pdffilename = ddl.getGeneratedContractpdf(contractdetailId);
            request.setAttribute("PDFFILENAME", pdffilename);
            return mapping.findForward("add_contractdetail");
        } //For modfiy contract
        else if (frm.getDoGeneratedContract() != null && frm.getDoGeneratedContract().equals("yes")) {
            frm.setDoGeneratedContract("no");
            int contractDetailId = frm.getContractdetailId();
            frm.setContractdetailId(contractDetailId);
            frm.setCandidateId(frm.getCandidateId());
            int assetId = frm.getClientassetId();
            frm.setClientassetId(assetId);
            int type = frm.getType();
            frm.setType(type);
            frm.setDateofissue(frm.getDateofissue());
            frm.setDateofexpiry(frm.getDateofexpiry());
            frm.setCurrentDate(base.currDate3());
            frm.setCfilehidden(frm.getCfilehidden());            
            TalentpoolInfo info = ddl.getDetailFormodify(contractDetailId);
            if (info != null) {
                Collection contracts = talentpool.getContracts(assetId, type, -1, info.getRefId());
                frm.setContracts(contracts);
                frm.setContractId(info.getContractId());
                if (info.getFromDate() != null) {
                    frm.setDateofissue(info.getFromDate());
                }
                if (info.getToDate() != null) {
                    frm.setDateofexpiry(info.getToDate());
                }
                if(info.getFile1() != null) {
                    frm.setCfilehidden(info.getFile1());
                }
                if(info.getCval1()!= null) {
                    frm.setCval1(info.getCval1());
                }
                if(info.getCval2()!= null) {
                    frm.setCval2(info.getCval2());
                }
                if(info.getCval3()!= null) {
                    frm.setCval3(info.getCval3());
                }
                if(info.getCval4()!= null) {
                    frm.setCval4(info.getCval4());
                }
                if(info.getCval5()!= null) {
                    frm.setCval5(info.getCval5());
                }
                if(info.getCval6()!= null) {
                    frm.setCval6(info.getCval6());
                }
                if(info.getCval7()!= null) {
                    frm.setCval7(info.getCval7());
                }
                if(info.getCval8()!= null){
                    frm.setCval8(info.getCval8());
                }
                if(info.getCval9()!= null){
                    frm.setCval9(info.getCval9());
                }
                if(info.getCval10()!= null){
                    frm.setCval10(info.getCval10());
                }
                frm.setRefId(info.getRefId());
            }
            String pdffilename = "";
            pdffilename = ddl.getGeneratedContractpdf(contractDetailId);
            request.setAttribute("PDFFILENAME", pdffilename);
            request.getSession().setAttribute("CONTRACT_EDIT", info);
            return mapping.findForward("add_contractdetail");
        }
        //for approval
        if (frm.getContractApprove() != null && frm.getContractApprove().equals("yes")) {
            frm.setContractApprove("no");
            int contractdetailId = frm.getContractdetailId();
            int candidateId = frm.getCandidateId();
            String remarks = frm.getRemark();
            int type = frm.getTypeId();
            String add_file = talentpool.getMainPath("add_candidate_file");
            String foldername = talentpool.createFolder(add_file);
            Date now = new Date();
            String fn = String.valueOf(now.getTime());
            String fileName1 = "";
            String file1 = frm.getFile1();
            int checktype = frm.getChecktype();
            frm.setChecktype(checktype);
            FormFile filename = frm.getContractfile();
            if(checktype <= 0)
            {
                if (filename != null && filename.getFileSize() > 0) {
                    fileName1 = talentpool.uploadFile(candidateId, "", filename, fn, add_file, foldername);
                }
                ddl.contractApprove(candidateId, contractdetailId, fileName1, remarks, userName, uId, type);
            }else{
                ddl.contractApprove(candidateId, contractdetailId, file1, remarks, userName, uId, type);
            }        
            
            frm.setContractdetailId(contractdetailId);
            int clientId = frm.getClientIdContract();
            int assetId = frm.getAssetIdContract();
            int status = frm.getContractStatus();
            String toval = frm.getToval();
            String fromVal = frm.getFromVal();
            String fromDate = frm.getFromDate();
            String ccVal = frm.getCcval();
            String bccVal = frm.getBccval();
            String subject = frm.getSubject();
            String decription = frm.getDescription();
            String toDate = frm.getToDate();
            
            TalentpoolInfo info1 = ddl.setContractEmailDetail(candidateId);
            
            if(type == 2 )
            {
                if(checktype == 1)
                    ddl.sendContractMail(fromVal, toval, ccVal, bccVal, subject, decription, info1.getName(), file1, info1.getClientName(), candidateId, userName);
                else
                    ddl.sendContractMail(fromVal, toval, ccVal, bccVal, subject, decription, info1.getName(), fileName1, info1.getClientName(), candidateId, userName);
            }
            int next = 0;
            if (request.getSession().getAttribute("NEXTCONTRACTVALUE") != null) {
                next = Integer.parseInt((String) request.getSession().getAttribute("NEXTCONTRACTVALUE"));
            }
            next = next - 1;
            if (next < 0) {
                next = 0;
            }
            ArrayList list = ddl.getContractListing(candidateId, clientId, assetId, status, fromDate, toDate, next, count);
            int cnt = 0;
            if (list.size() > 0) {
                TalentpoolInfo cinfo = (TalentpoolInfo) list.get(list.size() - 1);
                cnt = cinfo.getContractdetailId();
                list.remove(list.size() - 1);
            }
            request.getSession().setAttribute("CONTRACTLIST", list);
            request.getSession().setAttribute("COUNT_CONTRACT", cnt + "");
            request.getSession().setAttribute("NEXTCONTRACT", next+"");
            request.getSession().setAttribute("NEXTCONTRACTVALUE", (next+1)+"");
            return mapping.findForward("view_contractlist");
        } //delete contract
        else if (frm.getDeleteContract() != null && frm.getDeleteContract().equals("yes")) {
            frm.setDeleteContract("no");
            int candidateId = frm.getCandidateId();
            frm.setCandidateId(candidateId);
            int contractdetailId = frm.getContractdetailId();
            frm.setContractdetailId(contractdetailId);
            int status = frm.getStatus();
            String ipAddrStr = request.getRemoteAddr();
            String iplocal = talentpool.getLocalIp();
            int next = 0;
            if (request.getSession().getAttribute("NEXTCONTRACTVALUE") != null) {
                next = Integer.parseInt((String) request.getSession().getAttribute("NEXTCONTRACTVALUE"));
            }
            next = next - 1;
            if (next < 0) {
                next = 0;
            }
            int cc = ddl.deleteContract(candidateId, contractdetailId, uId, status, ipAddrStr, iplocal);
            if (cc > 0) {
                request.setAttribute("MESSAGE", "Status updated successfully");
            }
            frm.setContractdetailId(contractdetailId);
            int clientId = frm.getClientIdContract();
            int assetId = frm.getAssetIdContract();
            String fromDate = frm.getFromDate();
            String toDate = frm.getToDate();
            int contractstatus = frm.getContractStatus();
            ArrayList list = ddl.getContractListing(candidateId, clientId, assetId, contractstatus, fromDate, toDate, next, count);
            int cnt = 0;
            if (list.size() > 0) {
                TalentpoolInfo cinfo = (TalentpoolInfo) list.get(list.size() - 1);
                cnt = cinfo.getContractdetailId();
                list.remove(list.size() - 1);
            }
            request.getSession().setAttribute("CONTRACTLIST", list);
            request.getSession().setAttribute("COUNT_CONTRACT", cnt + "");
            request.getSession().setAttribute("NEXTCONTRACT", next+"");
            request.getSession().setAttribute("NEXTCONTRACTVALUE", (next+1)+"");
            return mapping.findForward("view_contractlist");
        } else if (frm.getDeleteContractFile() != null && frm.getDeleteContractFile().equals("yes")) {
            frm.setDeleteContractFile("no");
            print(this, "deleteFile block");
            int candidateId = frm.getCandidateId();
            int fileId = frm.getFileId();
            frm.setFileId(fileId);
            int contractdetailId = frm.getContractdetailId();
            frm.setContractdetailId(contractdetailId);
            String file1 = frm.getFile1();
            frm.setFile1(file1);
            String file2 = frm.getFile2();
            frm.setFile2(file2);
            String file3 = frm.getFile3();
            frm.setFile3(file3);
            TalentpoolInfo info = ddl.getDetailsForModal(contractdetailId);
            if(info != null )
            {
                file1 = info.getFile1();
                file2 = info.getFile2();
                file3 = info.getFile3();
            }    
            int clientId = frm.getClientIdContract();
            int assetId = frm.getAssetIdContract();
            int status = frm.getContractStatus();
            String fromDate = frm.getFromDate();
            String toDate = frm.getToDate();  
            int next = 0;
            if (request.getSession().getAttribute("NEXTCONTRACTVALUE") != null) {
                next = Integer.parseInt((String) request.getSession().getAttribute("NEXTCONTRACTVALUE"));
            }
            next = next - 1;
            if (next < 0) {
                next = 0;
            }
            int cc = ddl.deleteContractFile(contractdetailId, fileId, uId, file1, file2, file3);
            ArrayList list = ddl.getContractListing(candidateId, clientId, assetId, status, fromDate, toDate, next, count);
            int cnt = 0;
            if (list.size() > 0) {
                TalentpoolInfo cinfo = (TalentpoolInfo) list.get(list.size() - 1);
                cnt = cinfo.getContractdetailId();
                list.remove(list.size() - 1);
            }
            request.getSession().setAttribute("CONTRACTLIST", list);
            request.getSession().setAttribute("COUNT_CONTRACT", cnt + "");
            request.getSession().setAttribute("NEXTCONTRACT", next+"");
            request.getSession().setAttribute("NEXTCONTRACTVALUE", (next+1)+"");
            return mapping.findForward("view_contractlist");
        }
        else if (frm.getCancelContract()!= null && frm.getCancelContract().equals("yes")) 
        {
            print(this, "CancelContract block");            
            int candidateId = frm.getCandidateId();
            frm.setCandidateId(candidateId);
            
            clients = talentpool.getClients(cids, allclient, permission);
            frm.setClients(clients);
            int clientIdContract = frm.getClientIdContract();
            frm.setClientIdContract(clientIdContract);
            
            clientassets = talentpool.getClientAsset(clientIdContract, assetids, allclient, permission);
            frm.setClientassets(clientassets);
            int assetIdContract = frm.getAssetIdContract();
            frm.setAssetIdContract(assetIdContract);
            
            String fromDate = frm.getFromDate();
            String toDate = frm.getToDate();
            int contractstatus = frm.getContractStatus();
            int next = 0;
            if (request.getSession().getAttribute("NEXTCONTRACTVALUE") != null) {
                next = Integer.parseInt((String) request.getSession().getAttribute("NEXTCONTRACTVALUE"));
            }
            next = next - 1;
            if (next < 0) {
                next = 0;
            }
            ArrayList list = ddl.getContractListing(candidateId, clientIdContract, assetIdContract, contractstatus, fromDate, toDate, next, count);
            int cnt = 0;
            if (list.size() > 0) {
                TalentpoolInfo cinfo = (TalentpoolInfo) list.get(list.size() - 1);
                cnt = cinfo.getContractdetailId();
                list.remove(list.size() - 1);
            }
            request.getSession().setAttribute("CONTRACTLIST", list);
            request.getSession().setAttribute("COUNT_CONTRACT", cnt + "");
            request.getSession().setAttribute("NEXTCONTRACT", next+"");
            request.getSession().setAttribute("NEXTCONTRACTVALUE", (next+1)+"");            
            return mapping.findForward("view_contractlist");
        }
        else if (frm.getDoViewAppraisallist()!= null && frm.getDoViewAppraisallist().equals("yes"))
        {
            frm.setDoViewAppraisallist("no");
            int candidateId = frm.getCandidateId();
            ArrayList list = ddl.getAppraisalListing(candidateId);
            request.setAttribute("APPRAISALLIST", list);
            return mapping.findForward("view_appraisallist");//view.page page
        }
        else if (frm.getDoAddAppraisaldetail()!= null && frm.getDoAddAppraisaldetail().equals("yes")) 
        {
            frm.setDoAddAppraisaldetail("no");
            int appraisalId = frm.getAppraisalId();
            frm.setAppraisalId(appraisalId);
            int candidateId = frm.getCandidateId();   
            TalentpoolInfo info1 = ddl.getCandidatePositions(candidateId);
            Collection positions = talentpool.getPostions(info1.getClientassetId());
            frm.setPositions(positions);
            request.getSession().removeAttribute("APPRAISALFILE");
            request.getSession().setAttribute("PINFO", info1);
            return mapping.findForward("add_appraisaldetail");
        }        
        else if (frm.getDoSaveAppraisaldetail() != null && frm.getDoSaveAppraisaldetail().equals("yes")) 
        {
            frm.setDoSaveAppraisaldetail("no");            
            int candidateId = frm.getCandidateId();
            frm.setCandidateId(candidateId);
            int appraisalId = frm.getAppraisalId();
            frm.setAppraisalId(appraisalId);
            int positionId = frm.getPositionId();
            int positionId2 = frm.getPositionId2();
            String fromDate = frm.getFromDate();
            String toDate = frm.getToDate();
            double rate1 = frm.getRate1();
            double rate2 = frm.getRate2();
            double rate3 = frm.getRate3();
            int status = 1;
            int checktype = frm.getChecktype();
            String remark = frm.getRemark();            
            if(checktype == 1)
            {
                positionId2 = positionId;
            }
            TalentpoolInfo info1 = ddl.getCandidatePositions(candidateId);
            int ck = ddl.checkDuplicacyAppraisal(candidateId, appraisalId, positionId, positionId2, fromDate);
            if (ck == 1)
            {
                Collection positions = talentpool.getPostions(info1.getClientassetId());
                frm.setPositions(positions);
                frm.setPositionId(positionId);
                frm.setPositionId2(positionId2);
                frm.setAppraisalId(appraisalId);
                request.setAttribute("MESSAGE", "Appraisal already exists");
                return mapping.findForward("add_appraisaldetail");
            }
            else
            {
                String add_candidate_file = talentpool.getMainPath("add_candidate_file");
                String foldername = talentpool.createFolder(add_candidate_file);
                Date now = new Date();
                String fn = String.valueOf(now.getTime());
                String fileName1 = "", localname = "";
                FormFile filename = frm.getAppraisalFile();
                if(filename != null)
                {
                    localname = filename.getFileName().replaceAll("[/'\"]+", "");
                    if(localname.contains("."))
                    {
                        localname = localname.substring(0, localname.lastIndexOf("."));
                    }
                }
                if (filename != null && filename.getFileSize() > 0) {
                    fileName1 = talentpool.uploadFile(appraisalId, frm.getAppraisalfilehidden(), filename, localname+"-"+fn, add_candidate_file, foldername);
                }
                TalentpoolInfo info = new TalentpoolInfo(appraisalId, positionId, positionId2, rate1, rate2, rate3, fromDate, toDate, status, fileName1, remark, checktype);

                ddl.deleteRate(candidateId, positionId2, info1.getClientassetId(), fromDate, toDate);
                int cc = ddl.insertAppraisal(info, candidateId, uId, positionId2);  
                if (cc > 0)
                {
                    {
                        ddl.updateAppraisalPosition(positionId, candidateId, uId, info1.getPositionId(), info1.getPositionId2(), positionId2, checktype);
                        crewdayrate.createdatacandidate(info1.getClientassetId(), candidateId, positionId2, rate1, rate2, rate3, fromDate, toDate, uId);
                        TalentpoolInfo dinfo = talentpool.getCandidateDetail(candidateId);
                        ddl.createAppraisalExp(dinfo, candidateId, uId, info, positionId2);
                        request.setAttribute("MESSAGE", "Data added successfully.");
                    }
                }
                request.getSession().removeAttribute("APPRAISALFILE");
                frm.setAppraisalId(-1);
                ArrayList list = ddl.getAppraisalListing(candidateId);
                request.setAttribute("APPRAISALLIST", list);
                return mapping.findForward("view_appraisallist");//view.page page  
            }
        }
        //for dayrate tab
        else if (frm.getDoViewDayratelist()!= null && frm.getDoViewDayratelist().equals("yes"))
        {
            frm.setDoViewDayratelist("no");
            int candidateId = frm.getCandidateId();
            ArrayList list = ddl.getDayrateListing(candidateId);
            int pid = 0;
            if(list.size() > 0)
            {
                TalentpoolInfo info;
                for (int i = 0; i < list.size(); i++) 
                {
                    info = (TalentpoolInfo) list.get(i);
                    if (info != null)
                    {
                        if(i== 0)
                        {
                            pid = info.getPositionId();
                        }
                    }
                }
            }
            TalentpoolInfo info = talentpool.getCandidateDetail(candidateId);
            int dayrateId = ddl.checkDayRateId(candidateId, pid, info.getToAssetId());
            TalentpoolInfo info1 = ddl.getCandidatePositions(candidateId);
            request.getSession().setAttribute("PINFO", info1);
            request.setAttribute("DAYRATE_ID", ""+dayrateId);
            request.setAttribute("DAYRATELIST", list);
            return mapping.findForward("view_dayratelist");//view.page page
        }
        else if (frm.getDoAddDayratedetail()!= null && frm.getDoAddDayratedetail().equals("yes")) 
        {
            frm.setDoAddDayratedetail("no");
            frm.setDayrateId(-1);
            int candidateId = frm.getCandidateId();
            TalentpoolInfo info1 = ddl.getCandidatePositions(candidateId);
            request.getSession().setAttribute("PINFO", info1);
            return mapping.findForward("add_dayratedetail");
        }        
        else if (frm.getDoSaveDayratedetail()!= null && frm.getDoSaveDayratedetail().equals("yes")) 
        {
            frm.setDoSaveDayratedetail("no");
            int candidateId = frm.getCandidateId();
            frm.setCandidateId(candidateId);
            int dayaretId = frm.getDayrateId();
            frm.setDayrateId(dayaretId);
            int dayarethId = frm.getDayratehId();
            frm.setDayratehId(dayarethId);
            int positionId = frm.getPositionId();
            String fromdate = frm.getFromDate();
            String todate = frm.getToDate();
            double rate1 = frm.getRate1();
            double rate2 = frm.getRate2();
            double rate3 = frm.getRate3();
            
            TalentpoolInfo info1 = ddl.getCandidatePositions(candidateId);
            
            int ck = crewdayrate.checkDuplicacy(candidateId, fromdate, todate, info1.getClientassetId(), positionId);
            if (ck == 1)
            {
                frm.setPositionId(positionId);
                frm.setDayrateId(dayaretId);
                request.setAttribute("MESSAGE", "Day rate for the selected date already exists, Please select a later date.");
                return mapping.findForward("add_dayratedetail");
            }
            if (dayaretId <= 0)
            {
                int cc = crewdayrate.createdatacandidate(info1.getClientassetId(), candidateId, positionId, rate1, rate2, rate3, fromdate, todate, uId);
                if (cc > 0)
                    request.setAttribute("MESSAGE", "Data added successfully.");
                frm.setDayrateId(-1);
                ArrayList list = ddl.getDayrateListing(candidateId);
                int pid = 0;
                if (list.size() > 0) {
                    TalentpoolInfo info;
                    for (int i = 0; i < list.size(); i++) {
                        info = (TalentpoolInfo) list.get(i);
                        if (info != null) {
                            if (i == 0) {
                                pid = info.getPositionId();
                            }
                        }
                    }
                }
                TalentpoolInfo info = talentpool.getCandidateDetail(candidateId);
                int dayrateId = ddl.checkDayRateId(candidateId, pid, info.getToAssetId());
                request.setAttribute("DAYRATE_ID", "" + dayrateId);
                request.setAttribute("DAYRATELIST", list);
                return mapping.findForward("view_dayratelist");//view.page page
            }
        }
        else if (frm.getDoSaveDayrate()!= null && frm.getDoSaveDayrate().equals("yes")) 
        {
            frm.setDoSaveDayrate("no");
            print(this, " DoSaveDayrate block :: ");
            int candidateId = frm.getCandidateId();
            frm.setCandidateId(candidateId);
            int dayaretId = frm.getDayrateId2();
            frm.setDayrateId2(dayaretId);
            int dayarethId = frm.getDayratehId();
            frm.setDayratehId(dayarethId);
            double rate1 = frm.getDayrate1();
            double rate2 = frm.getDayrate2();
            double rate3 = frm.getDayrate3();
            String fromdate = frm.getFromDate();
            String todate = frm.getToDate();
            int positionId = frm.getPositionId();
            TalentpoolInfo info1 = ddl.getCandidatePositions(candidateId);
            ArrayList list = ddl.getDayrateListing(candidateId);
            
            int ck = ddl.checkDuplicacyDayrate(candidateId, fromdate, todate, info1.getClientassetId(), positionId, dayaretId);
            if (ck == 1)
            {
                frm.setPositionId(positionId);
                frm.setDayrateId(dayaretId);
                request.setAttribute("MESSAGE", "Day rate for the selected date already exists, Please select a later date.");
                TalentpoolInfo info = talentpool.getCandidateDetail(candidateId);
                int pid = 0;
                if(list.size() > 0)
                {
                    TalentpoolInfo info2;
                    for (int i = 0; i < list.size(); i++) 
                    {
                        info2 = (TalentpoolInfo) list.get(i);
                        if (info2 != null) 
                        {
                            if(i== 0)
                            {
                                pid = info2.getPositionId();
                            }
                        }
                    }
                }
                int dayrateId = ddl.checkDayRateId(candidateId, pid, info.getToAssetId());
                request.setAttribute("DAYRATE_ID", ""+dayrateId);
                request.setAttribute("DAYRATELIST", list);
                return mapping.findForward("view_dayratelist");
            }else
            {
                ddl.updateCandidateDayrate(rate1, rate2, rate3, uId, dayaretId, dayarethId, fromdate, todate, positionId);
                list = ddl.getDayrateListing(candidateId);
                int pid = 0;
                if(list.size() > 0)
                {
                    TalentpoolInfo info;
                    for (int i = 0; i < list.size(); i++) 
                    {
                        info = (TalentpoolInfo) list.get(i);
                        if (info != null) 
                        {
                            if(i== 0)
                            {
                                pid = info.getPositionId();
                            }
                        }
                    }
                }
                TalentpoolInfo info = talentpool.getCandidateDetail(candidateId);
                int dayrateId = ddl.checkDayRateId(candidateId, pid, info.getToAssetId());
                request.setAttribute("DAYRATE_ID", ""+dayrateId);
                request.setAttribute("DAYRATELIST", list);
                return mapping.findForward("view_dayratelist");
            }
        } 
        else if (frm.getDoGenerate() != null && frm.getDoGenerate().equals("yes")) 
        {
            frm.setDoGenerate("no");
            print(this, " getDoGenerate block :: ");
            frm.setClientId(-1);
            Clientselection clientselection = new Clientselection();
            int candidateId = frm.getCandidateId();
            frm.setCandidateId(candidateId);
            Collection resumetemplates = clientselection.getResumetemplatesforclientindex(-1, 1);
            frm.setResumetemplates(resumetemplates);
            return mapping.findForward("generate_cv");
        } 
        else if (frm.getDoGenerateOF() != null && frm.getDoGenerateOF().equals("yes")) {
            frm.setDoGenerateOF("no");
            print(this, " getDoGenerate OF block :: ");
            int clientId = frm.getClientId();
            frm.setClientId(clientId);
            Onboarding of = new Onboarding();
            int candidateId = frm.getCandidateId();
            frm.setCandidateId(candidateId);
            int clientassetId = frm.getClientassetId();
            frm.setClientassetId(clientassetId);
            Collection templates = of.getOboardingFormalityClientIndex(clientId);
            frm.setFormalitytemplates(templates);
            Collection assets = of.getClientAsset(clientId);
            frm.setAssets(assets);       
            return mapping.findForward("generate_formality");
        }
        else {
            print(this, "else block.");
            if (frm.getCtp() == 0) {
                Stack<String> blist = new Stack<String>();
                if (request.getSession().getAttribute("BACKURL") != null) {
                    blist = (Stack<String>) request.getSession().getAttribute("BACKURL");
                }
                blist.push(request.getRequestURI());
                request.getSession().setAttribute("BACKURL", blist);
            }
            ArrayList candidateList = talentpool.getCandidateByName(search, statusIndex, 0, count, positionIndexId,
                    clientIndex, locationIndex, assetIndex, verified, employementstatus, allclient, permission, cids, assetids, assettypeIdIndex, positionFilterId);
            int cnt = 0;
            if (candidateList.size() > 0) {
                TalentpoolInfo cinfo = (TalentpoolInfo) candidateList.get(candidateList.size() - 1);
                cnt = cinfo.getCandidateId();
                candidateList.remove(candidateList.size() - 1);
            }
            request.getSession().setAttribute("CANDIDATE_LIST", candidateList);
            request.getSession().setAttribute("COUNT_LIST", cnt + "");
            request.getSession().setAttribute("NEXT", "0");
            request.getSession().setAttribute("NEXTVALUE", "1");
        }
        return mapping.findForward("display");
    }
}
