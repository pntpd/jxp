package com.web.jxp.jobpost;

import com.web.jxp.assessment.Assessment;
import com.web.jxp.base.Validate;
import com.web.jxp.client.Client;
import static com.web.jxp.common.Common.*;
import com.web.jxp.country.Country;
import com.web.jxp.currency.Currency;
import com.web.jxp.onboarding.Onboarding;
import com.web.jxp.qualificationtype.QualificationType;
import com.web.jxp.user.UserInfo;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Stack;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class JobPostAction extends Action {

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        JobPostForm frm = (JobPostForm) form;
        JobPost jobpost = new JobPost();
        Client client = new Client();
        QualificationType qualificationtype = new QualificationType();
        Country country = new Country();
        Assessment assessment = new Assessment();
        Currency currency = new Currency();
        Validate vobj = new Validate();
        Onboarding onboarding = new Onboarding();

        Collection assessments = assessment.getAssessments();
        frm.setAssessments(assessments);
        int count = jobpost.getCount();
        String search = frm.getSearch() != null && !frm.getSearch().equals("") ? frm.getSearch() : "";
        frm.setSearch(search);
        int statusIndex = frm.getStatusIndex();
        frm.setStatusIndex(statusIndex);
        int uId = 0, allclient = 0;
        String permission = "N", cids = "", assetids = "", userName= "";
        if (request.getSession().getAttribute("LOGININFO") != null) 
        {
            UserInfo uInfo = (UserInfo) request.getSession().getAttribute("LOGININFO");
            if (uInfo != null) 
            {
                permission = uInfo.getPermission();
                userName = uInfo.getName() != null ? uInfo.getName(): "";
                uId = uInfo.getUserId();
                cids = uInfo.getCids();
                allclient = uInfo.getAllclient();
                assetids = uInfo.getAssetids();
            }
        }
        System.out.println("userName :: "+userName);
        int check_user = jobpost.checkUserSession(request, 45, permission);
        if (check_user == -1) {
            return mapping.findForward("default");
        } else if (check_user == -2) {
            String authmess = jobpost.getMainPath("user_auth");
            authmess = authmess.replaceAll("__MODULE__", "JobPost Module");
            request.setAttribute("AUTHMESSAGE", authmess);
            return mapping.findForward("auth");
        }
        if (frm.getDoAdd() != null && frm.getDoAdd().equals("yes")) {

            frm.setDoAdd("no");
            print(this, " doAdd block :: ");
            int jobpostId = frm.getJobpostId();
            frm.setJobpostId(jobpostId);
            
            Collection clients = jobpost.getClients(cids, allclient, permission);
            frm.setClient(clients);
            int clientIdIndex = frm.getClientId();
            frm.setClientId(clientIdIndex);
            Collection clinetassets = jobpost.getClientAsset(clientIdIndex, assetids, allclient, permission);
            frm.setClientasset(clinetassets);
            
            Collection positions = jobpost.getPostions(-1);
            frm.setPosition(positions);
            Collection grades = jobpost.getGrades(-1, "");
            frm.setGrade(grades);
            Collection countrys = country.getCountrys();
            frm.setCountry(countrys);
            Collection educations = qualificationtype.getQualificationTypes();
            frm.setEducation(educations);
            Collection currencys = currency.getCurrencys();
            frm.setCurrency(currencys);
            frm.setStatus(1);
            frm.setJobpostId(-1);
            request.getSession().removeAttribute("NATIONALITY_IDs");
            request.getSession().removeAttribute("LANGUAGE_IDs");
            request.getSession().removeAttribute("GENDER_IDs");
            saveToken(request);
            return mapping.findForward("add_jobpost");

        } else if (frm.getDoModify() != null && frm.getDoModify().equals("yes")) {

            frm.setDoModify("no");
            int jobpostId = frm.getJobpostId();
            frm.setJobpostId(jobpostId);

            Collection clients = jobpost.getClients(cids, allclient, permission);
            frm.setClient(clients);
            
            Collection countrys = country.getCountrys();
            frm.setCountry(countrys);
            Collection educations = qualificationtype.getQualificationTypes();
            frm.setEducation(educations);
            Collection currencys = currency.getCurrencys();
            frm.setCurrency(currencys);

            JobPostInfo info = jobpost.getJobPostDetailById(jobpostId);
            if (info != null) 
            {              
                Collection clinetassets = jobpost.getClientAsset(info.getClientId(), assetids, allclient, permission);
                frm.setClientasset(clinetassets);
                Collection positions = jobpost.getPostions(info.getClientassetId());
                frm.setPosition(positions);
                Collection grades = jobpost.getGrades(info.getClientassetId(), info.getPositionname());
                frm.setGrade(grades);

                frm.setClientId(info.getClientId());
                frm.setPositionname(info.getPositionname());
                frm.setClientassetId(info.getClientassetId());
                frm.setPositionId(info.getPositionId());
                frm.setGradeId(info.getGradeId());
                frm.setCountryId(info.getCountryId());
                frm.setCityId(info.getCityId());
                frm.setCityname(info.getCityname());
                frm.setExperiencemin(info.getExperiencemin());
                frm.setExperiencemax(info.getExperiencemax());
                request.getSession().setAttribute("NATIONALITY_IDs", info.getNationality());
                request.getSession().setAttribute("LANGUAGE_IDs", info.getLanguage());
                request.getSession().setAttribute("GENDER_IDs", info.getGender());
                frm.setEducationtypeId(info.getEducationtypeId());
                frm.setDescription(info.getDescription());
                frm.setNoofopening(info.getNoofopening());
                frm.setTargetmobdate(info.getTargetmobdate());
                frm.setTenure(info.getTenure());
                frm.setCurrencyId(info.getCurrencyId());
                frm.setDayratevalue(info.getDayratevalue());
                frm.setRemunerationmin(info.getRemunerationmin());
                frm.setRemunerationmax(info.getRemunerationmax());
                frm.setWorkhour(info.getWorkhour());
                frm.setVacancypostedby(info.getVacancypostedby());
                frm.setAdditionalnote(info.getAdditionalnote());
                frm.setStatus(info.getStatus());
                if (request.getAttribute("JOBPOSTSAVEMODEL") != null) {
                    request.removeAttribute("JOBPOSTSAVEMODEL");
                }
            }
            saveToken(request);
            return mapping.findForward("add_jobpost");
        } else if (frm.getDoView() != null && frm.getDoView().equals("yes")) {

            frm.setDoView("no");
            int jobpostId = frm.getJobpostId();
            frm.setJobpostId(jobpostId);
            JobPostInfo info = jobpost.getJobPostDetailByIdforDetail(jobpostId);
            request.getSession().setAttribute("JOBPOST_DETAIL", info);
            return mapping.findForward("view_jobpost");

        } else if (frm.getDoClose() != null && frm.getDoClose().equals("yes")) {
            frm.setDoClose("no");

            int jobpostId = frm.getJobpostId();
            onboarding.updatejobpostflagbystatus(jobpostId, uId);
            onboarding.updatecandidateflagonboarded(uId);

            ArrayList jobpostList = jobpost.getJobPostByName(search, statusIndex, 0, count, allclient, permission, cids, assetids);
            int cnt = 0;
            if (jobpostList.size() > 0) {
                JobPostInfo cinfo = (JobPostInfo) jobpostList.get(jobpostList.size() - 1);
                cnt = cinfo.getJobpostId();
                jobpostList.remove(jobpostList.size() - 1);
            }
            request.getSession().setAttribute("JOBPOST_LIST", jobpostList);
            request.getSession().setAttribute("COUNT_LIST", cnt + "");
            request.getSession().setAttribute("NEXT", "0");
            request.getSession().setAttribute("NEXTVALUE", "1");
            return mapping.findForward("display");

        } else if (frm.getDoSave() != null && frm.getDoSave().equals("yes")) {

            frm.setDoSave("no");
            print(this, "getDoSave block.");
            if (isTokenValid(request))
            {
                resetToken(request);
                int jobpostId = frm.getJobpostId();
                int clientId = frm.getClientId();
                int clientassetId = frm.getClientassetId();
                int positionId = frm.getPositionId();
                int gradeId = frm.getGradeId();
                int countryId = frm.getCountryId();
                int cityId = frm.getCityId();
                int educationtypeId = frm.getEducationtypeId();
                int noofopening = frm.getNoofopening();
                int tenure = frm.getTenure();
                int currencyId = frm.getCurrencyId();

                double experiencemin = frm.getExperiencemin();
                double experiencemax = frm.getExperiencemax();
                double dayratevalue = frm.getDayratevalue();
                double remunerationmin = frm.getRemunerationmin();
                double remunerationmax = frm.getRemunerationmax();
                double workhour = frm.getWorkhour();

                String[] nationality = frm.getNationality();
                String[] languages = frm.getLanguage();
                String[] gender = frm.getGender();

                String strnationality = vobj.replacealphacomma(makeCommaDelimString(nationality));
                String strlanguages = vobj.replacealphacomma(makeCommaDelimString(languages));
                String strgender = vobj.replacename(makeCommaDelimString(gender));

                String cityname = vobj.replacename(frm.getCityname());
                String positionname = vobj.replacename(frm.getPositionname());
                String description = vobj.replacedesc(frm.getDescription());
                String targetmobdate = vobj.replacedate(frm.getTargetmobdate());
                String vacancypostby = vobj.replacename(frm.getVacancypostedby());
                String additionalnote = vobj.replacedesc(frm.getAdditionalnote());
                int status = 1;
                String ipAddrStr = request.getRemoteAddr();
                String iplocal = jobpost.getLocalIp();
                int ck = jobpost.checkDuplicacy(jobpostId, clientId, positionId, gradeId);
                if (ck == 1) 
                {
                    saveToken(request);
                    request.setAttribute("MESSAGE", "Job Post already exists");
                    Collection clients = client.getClients();
                    frm.setClient(clients);
                    Collection clinetassets = jobpost.getClientAsset(frm.getClientId());
                    frm.setClientasset(clinetassets);
                    Collection positions = jobpost.getPostions(frm.getClientassetId());
                    frm.setPosition(positions);
                    Collection grades = jobpost.getGrades(frm.getClientassetId(), positionname);
                    frm.setGrade(grades);
                    Collection countrys = country.getCountrys();
                    frm.setCountry(countrys);
                    frm.setCityId(frm.getCityId());
                    frm.setCityname(frm.getCityname());
                    frm.setExperiencemin(frm.getExperiencemin());
                    frm.setExperiencemax(frm.getExperiencemax());
                    frm.setPositionname(frm.getPositionname());
                    request.getSession().setAttribute("NATIONALITY_IDs", strnationality);
                    request.getSession().setAttribute("LANGUAGE_IDs", strlanguages);
                    request.getSession().setAttribute("GENDER_IDs", strgender);
                    Collection educations = qualificationtype.getQualificationTypes();
                    frm.setEducation(educations);
                    frm.setDescription(frm.getDescription());
                    frm.setNoofopening(frm.getNoofopening());
                    frm.setTargetmobdate(frm.getTargetmobdate());
                    frm.setTenure(frm.getTenure());
                    frm.setCurrencyId(frm.getCurrencyId());
                    frm.setDayratevalue(frm.getDayratevalue());
                    frm.setRemunerationmin(frm.getRemunerationmin());
                    frm.setRemunerationmax(frm.getRemunerationmax());
                    frm.setWorkhour(frm.getWorkhour());
                    frm.setVacancypostedby(frm.getVacancypostedby());
                    frm.setAdditionalnote(frm.getAdditionalnote());
                    return mapping.findForward("add_jobpost");
                }
                JobPostInfo info = new JobPostInfo(jobpostId, clientId, clientassetId, positionId, gradeId, countryId, cityId,
                        experiencemin, experiencemax, strnationality, strlanguages, strgender, educationtypeId, description, noofopening,
                        targetmobdate, tenure, currencyId, dayratevalue, remunerationmin, remunerationmax, workhour, vacancypostby,
                        additionalnote, status, uId, positionname, cityname);
                if (jobpostId <= 0) {
                    int cc = jobpost.createJobPost(info);
                    frm.setJobpostId(cc);
                    if (cc > 0) 
                    {                        
                        jobpost.sendJobpostEmail(userName, uId, "New Jobpost Created", cc);
                        jobpost.createHistoryAccess(null, uId, ipAddrStr, iplocal, "Data Added", 45, cc);
                        request.setAttribute("MESSAGE", "Data added successfully.");

                    }
                    request.setAttribute("JOBPOSTSAVEMODEL", "yes");
                    JobPostInfo infocc = jobpost.getJobPostDetailByIdforDetail(cc);
                    request.getSession().removeAttribute("NATIONALITY_IDs");
                    request.getSession().removeAttribute("LANGUAGE_IDs");
                    request.getSession().removeAttribute("GENDER_IDs");
                    request.getSession().setAttribute("JOBPOST_DETAIL", infocc);
                    return mapping.findForward("view_jobpost");

                } else {
                    jobpost.updateJobPost(info);
                    jobpost.createHistoryAccess(null, uId, ipAddrStr, iplocal, "Data Updated", 45, jobpostId);
                    request.setAttribute("MESSAGE", "Data updated successfully.");

                    frm.setJobpostId(jobpostId);
                    JobPostInfo infocc = jobpost.getJobPostDetailByIdforDetail(jobpostId);
                    request.getSession().removeAttribute("NATIONALITY_IDs");
                    request.getSession().removeAttribute("LANGUAGE_IDs");
                    request.getSession().removeAttribute("GENDER_IDs");
                    request.getSession().setAttribute("JOBPOST_DETAIL", infocc);
                    return mapping.findForward("view_jobpost");
                }
            }
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
            ArrayList jobpostList = jobpost.getJobPostByName(search, statusIndex, next, count, allclient, permission, cids, assetids);
            int cnt = 0;
            if (jobpostList.size() > 0) {
                JobPostInfo cinfo = (JobPostInfo) jobpostList.get(jobpostList.size() - 1);
                cnt = cinfo.getJobpostId();
                jobpostList.remove(jobpostList.size() - 1);
            }
            request.getSession().setAttribute("JOBPOST_LIST", jobpostList);
            request.getSession().setAttribute("COUNT_LIST", cnt + "");
            request.getSession().setAttribute("NEXT", next + "");
            request.getSession().setAttribute("NEXTVALUE", (next + 1) + "");
            return mapping.findForward("display");
        } else if (frm.getDoBenefitsList() != null && frm.getDoBenefitsList().equals("yes")) {

            frm.setDoBenefitsList("no");
            print(this, " getDoBenefits block :: ");
            int jobpostId = frm.getJobpostId();
            frm.setJobpostId(jobpostId);
            ArrayList jobpostbenefitList = jobpost.getJobPostBenefitDetailByIdforList(jobpostId);
            request.setAttribute("BENEFIT_LIST", jobpostbenefitList);
            return mapping.findForward("display_benefit");

        } else if (frm.getDoDeleteJobPostBenefitDetail() != null && frm.getDoDeleteJobPostBenefitDetail().equals("yes")) {

            frm.setDoDeleteJobPostBenefitDetail("no");
            int jobpostBenefitId = frm.getJobpostBenefitId();
            int jobpostId = frm.getJobpostId();
            frm.setJobpostId(jobpostId);
            int status = frm.getStatus();
            String ipAddrStr = request.getRemoteAddr();
            String iplocal = jobpost.getLocalIp();
            jobpost.deleteBenefit(jobpostId, jobpostBenefitId, uId, status, ipAddrStr, iplocal);

            ArrayList jobpostbenefitList = jobpost.getJobPostBenefitDetailByIdforList(jobpostId);
            request.setAttribute("BENEFIT_LIST", jobpostbenefitList);
            return mapping.findForward("display_benefit");
        } else if (frm.getDoAddBenefit() != null && frm.getDoAddBenefit().equals("yes")) {

            frm.setDoAddBenefit("no");
            print(this, " getDoAddBenefit block :: ");
            Collection benefittypes = jobpost.getBenefitType();
            frm.setBenefittype(benefittypes);
            frm.setBenefitId(-1);
            frm.setBenefitcode(jobpost.getMaxBenefitQuestionId());
            saveToken(request);
            return mapping.findForward("add_benefit");
        } else if (frm.getDoSaveBenefit() != null && frm.getDoSaveBenefit().equals("yes")) {

            frm.setDoSaveBenefit("no");
            print(this, " getDoSaveBenefit block :: ");

            if (isTokenValid(request)) {
                resetToken(request);
                int jobpostId = frm.getJobpostId();
                frm.setJobpostId(jobpostId);
                int benefitId = frm.getBenefitId();
                int benefittypeId = frm.getBenefittypeId();
                String benifitname = vobj.replacedesc(frm.getBenefitname());

                int status = 1;
                String ipAddrStr = request.getRemoteAddr();
                String iplocal = jobpost.getLocalIp();
                int ck = jobpost.checkBenefitDuplicacy(benefitId, benifitname);
                if (ck == 1) {
                    saveToken(request);
                    request.setAttribute("MESSAGE", "Benefit already exists");
                    frm.setBenefitcode(frm.getBenefitcode());
                    Collection benefittypes = jobpost.getBenefitType();
                    frm.setBenefittype(benefittypes);
                    frm.setBenefittypeId(frm.getBenefittypeId());
                    return mapping.findForward("add_benefit");
                }
                JobPostInfo info = new JobPostInfo(benefitId, benifitname, benefittypeId, status, uId);
                if (benefitId <= 0) {
                    int cc = jobpost.createBenefitQuestion(info);
                    if (cc > 0) {
                        jobpost.createHistoryAccess(null, uId, ipAddrStr, iplocal, "Data Added", 45, cc);
                        request.setAttribute("MESSAGE", "Data added successfully.");
                    }
                    frm.setBenefitId(cc);
                    ArrayList jobpostbenefitEdit = jobpost.getEmployeeJobPostBenefitDetailByIdforEdit(jobpostId);
                    request.setAttribute("BENEFIT_EDIT", jobpostbenefitEdit);
                    ArrayList list = jobpost.getCurrencys();
                    request.getSession().setAttribute("CURRENCYLIST", list);
                    ArrayList list1 = jobpost.getBenefitQuestionList();
                    request.getSession().setAttribute("BENEFITQUESTIONLIST", list1);
                    saveToken(request);
                    return mapping.findForward("add_empposbenefit");
                }
            }
        } else if (frm.getDoModifyBenefit() != null && frm.getDoModifyBenefit().equals("yes")) {

            frm.setDoModifyBenefit("no");
            int jobpostId = frm.getJobpostId();
            frm.setJobpostId(jobpostId);

            ArrayList jobpostbenefitEdit = jobpost.getEmployeeJobPostBenefitDetailByIdforEdit(jobpostId);
            request.setAttribute("BENEFIT_EDIT", jobpostbenefitEdit);
            ArrayList list = jobpost.getCurrencys();
            request.getSession().setAttribute("CURRENCYLIST", list);
            ArrayList list1 = jobpost.getBenefitQuestionList();
            request.getSession().setAttribute("BENEFITQUESTIONLIST", list1);
            saveToken(request);
            return mapping.findForward("add_empposbenefit");
        } else if (frm.getDoSavePosBenefit() != null && frm.getDoSavePosBenefit().equals("yes")) {

            frm.setDoSavePosBenefit("no");
            print(this, " getDoSavePosBenefit block :: ");

            int jobpostId = frm.getJobpostId();
            int benefitId[] = frm.getPosbenefitId();
            int benefittypeId[] = frm.getHiddenbenefittype();
            int benifitquestion[] = frm.getBenefetquestionIdHidden();
            String description[] = frm.getPosdescription();
            jobpost.createJobPostBenefit(jobpostId, benefitId, benefittypeId, benifitquestion, description, uId);
            ArrayList jobpostbenefitList = jobpost.getJobPostBenefitDetailByIdforList(jobpostId);
            request.setAttribute("BENEFIT_LIST", jobpostbenefitList);
            return mapping.findForward("display_benefit");

        } else if (frm.getDoViewAssessmentList() != null && frm.getDoViewAssessmentList().equals("yes")) {

            frm.setDoViewAssessmentList("no");
            frm.setAssessmentDetailId(-1);
            print(this, " getDoViewAssessmentlist block :: ");
            int jobpostId = frm.getJobpostId();
            frm.setJobpostId(jobpostId);
            ArrayList list = jobpost.getAssessmentList(jobpostId);
            request.setAttribute("ASSESSMENTLIST", list);
            return mapping.findForward("view_assessmentdetail");

        } else if (frm.getDoDeleteAssessmentDetail() != null && frm.getDoDeleteAssessmentDetail().equals("yes")) {

            frm.setDoDeleteAssessmentDetail("no");
            int jobpostId = frm.getJobpostId();
            frm.setJobpostId(jobpostId);
            int assessmentDetailId = frm.getAssessmentDetailId();
            frm.setAssessmentDetailId(assessmentDetailId);
            int status = frm.getStatus();
            String ipAddrStr = request.getRemoteAddr();
            String iplocal = jobpost.getLocalIp();
            int cc = jobpost.deleteAssessment(jobpostId, assessmentDetailId, uId, status, ipAddrStr, iplocal);
            if (cc > 0) {
                request.setAttribute("MESSAGE", "Status updated successfully");
            }
            ArrayList list = jobpost.getAssessmentList(jobpostId);
            request.setAttribute("ASSESSMENTLIST", list);
            return mapping.findForward("view_assessmentdetail");
        } else if (frm.getDoModifyAssessmentDetail() != null && frm.getDoModifyAssessmentDetail().equals("yes")) {

            frm.setDoModifyAssessmentDetail("no");
            print(this, " getDoModifyAssessmentDetail block :: ");
            int assessmentDetailId = frm.getAssessmentDetailId();
            frm.setAssessmentDetailId(assessmentDetailId);
            frm.setJobpostId(frm.getJobpostId());
            Collection assessmentss = assessment.getAssessments();
            frm.setAssessments(assessmentss);
            if (frm.getAssessmentDetailId() > 0) {
                JobPostInfo info = jobpost.getAssessmentDetailByJobPostId(assessmentDetailId);
                if (info != null) {

                    if (info.getAssessmentId() > 0) {
                        frm.setAssessmentId(info.getAssessmentId());
                    }
                    frm.setMinScore(info.getMinScore());
                    frm.setPassingFlag(info.getPassingFlag());
                }
            }
            return mapping.findForward("add_assessmentdetail");
        } else if (frm.getDoSaveAssessmentDetail() != null && frm.getDoSaveAssessmentDetail().equals("yes")) {

            frm.setDoSaveAssessmentDetail("no");
            int jobpostId = frm.getJobpostId();
            int assessmentDetailId = frm.getAssessmentDetailId();
            frm.setAssessmentDetailId(assessmentDetailId);
            int assessmentId = frm.getAssessmentId();
            int minScore = frm.getMinScore();
            int passingFlag = frm.getPassingFlag();
            frm.setJobpostId(jobpostId);
            int status = 1;
            int ck = jobpost.checkDuplicacyAssessment(jobpostId, assessmentDetailId, assessmentId);
            if (ck == 1) {
                frm.setAssessments(assessments);
                frm.setAssessmentId(assessmentId);
                frm.setAssessmentDetailId(assessmentDetailId);
                request.setAttribute("MESSAGE", "Assessment already exists");
                return mapping.findForward("add_assessmentdetail");
            }
            JobPostInfo info = new JobPostInfo(assessmentDetailId, assessmentId, minScore, passingFlag, status);
            frm.setAssessmentDetailId(-1);
            if (assessmentDetailId <= 0) {
                int cc = jobpost.insertAssessmentDetail(info, jobpostId, uId);
                if (cc > 0) {
                    request.setAttribute("MESSAGE", "Data added successfully.");
                }

                ArrayList list = jobpost.getAssessmentList(jobpostId);
                request.setAttribute("ASSESSMENTLIST", list);
                return mapping.findForward("view_assessmentdetail");
            } else {
                int cc = jobpost.updateAssessmentDetail(info, uId, jobpostId, assessmentDetailId);
                if (cc > 0) {
                    request.setAttribute("MESSAGE", "Data updated successfully.");
                }
                ArrayList list = jobpost.getAssessmentList(jobpostId);
                request.setAttribute("ASSESSMENTLIST", list);
                return mapping.findForward("view_assessmentdetail");
            }
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
            ArrayList jobpostList = jobpost.getJobPostByName(search, statusIndex, 0, count, allclient, permission, cids, assetids);
            int cnt = 0;
            if (jobpostList.size() > 0) {
                JobPostInfo cinfo = (JobPostInfo) jobpostList.get(jobpostList.size() - 1);
                cnt = cinfo.getJobpostId();
                jobpostList.remove(jobpostList.size() - 1);
            }
            request.getSession().setAttribute("JOBPOST_LIST", jobpostList);
            request.getSession().setAttribute("COUNT_LIST", cnt + "");
            request.getSession().setAttribute("NEXT", "0");
            request.getSession().setAttribute("NEXTVALUE", "1");
        }
        return mapping.findForward("display");
    }
}
