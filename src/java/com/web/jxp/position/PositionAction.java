package com.web.jxp.position;

import com.web.jxp.assessment.Assessment;
import com.web.jxp.assettype.Assettype;
import com.web.jxp.base.Validate;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import static com.web.jxp.common.Common.*;
import com.web.jxp.country.Country;
import com.web.jxp.currency.Currency;
import com.web.jxp.degree.Degree;
import com.web.jxp.grade.Grade;
import com.web.jxp.hoursofwork.HoursOfWork;
import com.web.jxp.language.Language;
import com.web.jxp.qualificationtype.QualificationType;
import com.web.jxp.rotation.Rotation;
import com.web.jxp.user.UserInfo;
import java.util.Collection;
import java.util.Stack;

public class PositionAction extends Action {

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        PositionForm frm = (PositionForm) form;
        Position position = new Position();
        Validate vobj = new Validate();
        Assettype assettype = new Assettype();
        Grade grade = new Grade();
        Degree minqualification = new Degree();
        QualificationType qualificationtype = new QualificationType();
        Rotation rotation = new Rotation();
        HoursOfWork hoursofwork = new HoursOfWork();
        Language language = new Language();
        Country country = new Country();
        Assessment assessment = new Assessment();

        Collection assessments = assessment.getAssessments();
        frm.setAssessments(assessments);
        int count = position.getCount();
        String search = frm.getSearch() != null && !frm.getSearch().equals("") ? frm.getSearch() : "";
        frm.setSearch(search);
        int uId = 0;
        String permission = "N";
        if (request.getSession().getAttribute("LOGININFO") != null) 
        {
            UserInfo uInfo = (UserInfo) request.getSession().getAttribute("LOGININFO");
            if (uInfo != null) 
            {
                permission = uInfo.getPermission();
                uId = uInfo.getUserId();
            }
        }
        int check_user = position.checkUserSession(request, 12, permission);
        if (check_user == -1) {
            return mapping.findForward("default");
        } else if (check_user == -2) {
            String authmess = position.getMainPath("user_auth");
            authmess = authmess.replaceAll("__MODULE__", "Position Module");
            request.setAttribute("AUTHMESSAGE", authmess);
            return mapping.findForward("auth");
        }
        if (frm.getDoAdd() != null && frm.getDoAdd().equals("yes")) {

            frm.setDoAdd("no");
            print(this, " doAdd block :: ");
            int positionId = frm.getPositionId();
            frm.setPositionId(positionId);
            Collection assettypes = assettype.getAssettypes();
            frm.setAssettype(assettypes);
            Collection grades = grade.getGrades();
            frm.setGrades(grades);
            Collection minqualifications = minqualification.getDegree();
            frm.setMinqualification(minqualifications);
            Collection qualifications = qualificationtype.getQualificationTypes();
            frm.setQualificationtype(qualifications);
            Collection rotations = rotation.getRotations();
            frm.setCrewrotation(rotations);
            Collection hoursofworks = hoursofwork.getHoursOfWorks();
            frm.setHoursofwork(hoursofworks);
            Collection languages = language.getLanguages();
            frm.setLanguage(languages);
            Collection countrys = country.getCountrys();
            frm.setNationality(countrys);
            Collection currencys = position.getCurrencys();
            frm.setCurrency(currencys);
            frm.setStatus(1);
            frm.setPositionId(-1);
            saveToken(request);
            return mapping.findForward("add_position");

        } else if (frm.getDoModify() != null && frm.getDoModify().equals("yes")) {

            frm.setDoModify("no");
            print(this, " getDoModify block :: ");
            int positionId = frm.getPositionId();
            frm.setPositionId(positionId);

            Collection assettypes = assettype.getAssettypes();
            frm.setAssettype(assettypes);
            Collection grades = grade.getGrades();
            frm.setGrades(grades);
            Collection minqualifications = minqualification.getDegree();
            frm.setMinqualification(minqualifications);
            Collection qualifications = qualificationtype.getQualificationTypes();
            frm.setQualificationtype(qualifications);
            Collection rotations = rotation.getRotations();
            frm.setCrewrotation(rotations);
            Collection hoursofworks = hoursofwork.getHoursOfWorks();
            frm.setHoursofwork(hoursofworks);
            Collection languages = language.getLanguages();
            frm.setLanguage(languages);
            Collection countrys = country.getCountrys();
            frm.setNationality(countrys);
            Collection currencys = position.getCurrencys();
            frm.setCurrency(currencys);

            PositionInfo info = position.getPositionDetailById(positionId);
            if (info != null) {
                if (info.getPositiontitle() != null) {
                    frm.setPositiontitle(info.getPositiontitle());
                }

                frm.setAssettypeId(info.getAssettypeId());
                frm.setGradeId(info.getGradeId());
                frm.setRotationId(info.getRotationId());
                frm.setHoursofworkId(info.getHoursofworkId());
                frm.setLanguageId(info.getLanguageId());
                frm.setGender(info.getGender());
                frm.setNationalityId(info.getNationalityId());
                frm.setCurrencyId(info.getCurrencyId());
                frm.setRenumerationmin(info.getRenumerationmin());
                frm.setRenumerationmax(info.getRenumerationmax());
                frm.setMinqualificationId(info.getMinqualificationId());
                frm.setQualificationtypeId(info.getQualificationtypeId());
                frm.setGenworkexprangemin(info.getGenworkexprangemin());
                frm.setGenworkexprangemax(info.getGenworkexprangemax());
                frm.setPosworkexprangemin(info.getPosworkexprangemin());
                frm.setPosworkexprangemax(info.getPosworkexprangemax());
                frm.setDescription(info.getDescription());
                frm.setPosition1(info.getPosition1());
                frm.setPosition2(info.getPosition2());
                frm.setPosition3(info.getPosition3());
                frm.setStatus(info.getStatus());
                frm.setOverstaystart(info.getOverstaystart());
                if (request.getAttribute("POSITIONSAVEMODEL") != null) {
                    request.removeAttribute("POSITIONSAVEMODEL");
                }
            }
            saveToken(request);
            return mapping.findForward("add_position");
        } else if (frm.getDoView() != null && frm.getDoView().equals("yes")) {

            frm.setDoView("no");
            int positionId = frm.getPositionId();
            frm.setPositionId(positionId);
            PositionInfo info = position.getPositionDetailByIdforDetail(positionId);
            request.setAttribute("POSITION_DETAIL", info);
            return mapping.findForward("view_position");

        } else if (frm.getDoSave() != null && frm.getDoSave().equals("yes")) {
            frm.setDoSave("no");
            print(this, "getDoSave block.");
            if (isTokenValid(request)) 
            {
                resetToken(request);
                int positionId = frm.getPositionId();
                String positiontitle = vobj.replacedesc(frm.getPositiontitle());
                String gender = frm.getGender();
                int gradeId = frm.getGradeId();
                int assettypeId = frm.getAssettypeId();
                int rotationId = frm.getRotationId();
                int hoursofworkId = frm.getHoursofworkId();
                int languageId = frm.getLanguageId();
                int minqualificationId = frm.getMinqualificationId();
                int qualificationtypeId = frm.getQualificationtypeId();
                int nationalityId = frm.getNationalityId();
                int currencyId = frm.getCurrencyId();
                int overstaystart = frm.getOverstaystart();

                double renumerationmin = frm.getRenumerationmin();
                double renumerationmax = frm.getRenumerationmax();
                double genworkexprangemin = frm.getGenworkexprangemin();
                double genworkexprangemax = frm.getGenworkexprangemax();
                double posworkexprangemin = frm.getPosworkexprangemin();
                double posworkexprangemax = frm.getPosworkexprangemax();

                String description = vobj.replacedesc(frm.getDescription());
                String position1 = vobj.replacedesc(frm.getPosition1());
                String position2 = vobj.replacedesc(frm.getPosition2());
                String position3 = vobj.replacedesc(frm.getPosition3());

                int status = 1;
                String ipAddrStr = request.getRemoteAddr();
                String iplocal = position.getLocalIp();
                int ck = position.checkDuplicacy(positionId, gradeId, positiontitle, assettypeId);
                if (ck == 1) {
                    saveToken(request);
                    request.setAttribute("MESSAGE", "Position title, Asset type and rank already exists");
                    frm.setPositiontitle(frm.getPositiontitle());
                    Collection assettypes = assettype.getAssettypes();
                    frm.setAssettype(assettypes);
                    frm.setAssettypeId(frm.getAssettypeId());
                    Collection grades = grade.getGrades();
                    frm.setGrades(grades);
                    frm.setGradeId(frm.getGradeId());
                    Collection minqualifications = minqualification.getDegree();
                    frm.setMinqualification(minqualifications);
                    frm.setMinqualificationId(frm.getMinqualificationId());
                    Collection qualifications = qualificationtype.getQualificationTypes();
                    frm.setQualificationtype(qualifications);
                    frm.setQualificationtypeId(frm.getQualificationtypeId());
                    Collection rotations = rotation.getRotations();
                    frm.setCrewrotation(rotations);
                    frm.setRotationId(frm.getRotationId());
                    Collection hoursofworks = hoursofwork.getHoursOfWorks();
                    frm.setHoursofwork(hoursofworks);
                    frm.setHoursofworkId(frm.getHoursofworkId());
                    Collection languages = language.getLanguages();
                    frm.setLanguage(languages);
                    frm.setLanguageId(frm.getLanguageId());
                    Collection countrys = country.getCountrys();
                    frm.setNationality(countrys);
                    frm.setNationalityId(frm.getNationalityId());
                    Collection currencys = position.getCurrencys();
                    frm.setCurrency(currencys);
                    frm.setCurrencyId(frm.getCurrencyId());
                    frm.setRenumerationmin(frm.getRenumerationmin());
                    frm.setRenumerationmax(frm.getRenumerationmax());
                    frm.setPosworkexprangemin(frm.getPosworkexprangemin());
                    frm.setPosworkexprangemax(frm.getPosworkexprangemax());
                    frm.setGenworkexprangemin(frm.getGenworkexprangemin());
                    frm.setGenworkexprangemax(frm.getGenworkexprangemax());
                    frm.setDescription(frm.getDescription());
                    frm.setPosition1(frm.getPosition1());
                    frm.setPosition2(frm.getPosition2());
                    frm.setPosition3(frm.getPosition3());
                    return mapping.findForward("add_position");
                }
                PositionInfo info = new PositionInfo(positionId, positiontitle, gender, renumerationmin, renumerationmax,
                        genworkexprangemin, genworkexprangemax, posworkexprangemin, posworkexprangemax, description,
                        position1, position2, position3, assettypeId, gradeId, minqualificationId, qualificationtypeId,
                        rotationId, hoursofworkId, languageId, nationalityId, status, uId, currencyId, overstaystart);
                if (positionId <= 0) {
                    int cc = position.createPosition(info);
                    frm.setPositionId(cc);
                    if (cc > 0) {
                        position.createHistoryAccess(null, uId, ipAddrStr, iplocal, "Data Added", 12, cc);
                        request.setAttribute("MESSAGE", "Data added successfully.");

                    }
                    request.setAttribute("POSITIONSAVEMODEL", "yes");
                    PositionInfo infocc = position.getPositionDetailByIdforDetail(cc);
                    request.setAttribute("POSITION_DETAIL", infocc);
                    return mapping.findForward("view_position");

                } else {
                    position.updatePosition(info);
                    position.createHistoryAccess(null, uId, ipAddrStr, iplocal, "Data Updated", 12, positionId);
                    request.setAttribute("MESSAGE", "Data updated successfully.");

                    frm.setPositionId(positionId);
                    PositionInfo infocc = position.getPositionDetailByIdforDetail(positionId);
                    request.setAttribute("POSITION_DETAIL", infocc);
                    return mapping.findForward("view_position");
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
            ArrayList positionList = position.getPositionByName(search, next, count);
            int cnt = 0;
            if (positionList.size() > 0) {
                PositionInfo cinfo = (PositionInfo) positionList.get(positionList.size() - 1);
                cnt = cinfo.getPositionId();
                positionList.remove(positionList.size() - 1);
            }
            request.getSession().setAttribute("POSITION_LIST", positionList);
            request.getSession().setAttribute("COUNT_LIST", cnt + "");
            request.getSession().setAttribute("NEXT", next + "");
            request.getSession().setAttribute("NEXTVALUE", (next + 1) + "");
            return mapping.findForward("display");
        } else if (frm.getDoBenefitsList() != null && frm.getDoBenefitsList().equals("yes")) {

            frm.setDoBenefitsList("no");
            print(this, " getDoBenefits block :: ");
            int positionId = frm.getPositionId();
            frm.setPositionId(positionId);
            ArrayList positionbenefitList = position.getPositionBenefitDetailByIdforList(positionId);
            request.setAttribute("BENEFIT_LIST", positionbenefitList);
            return mapping.findForward("display_benefit");

        } else if (frm.getDoDeleteBenefit() != null && frm.getDoDeleteBenefit().equals("yes")) {

            frm.setDoDeleteBenefit("no");
            int positionId = frm.getPositionId();
            frm.setPositionId(positionId);
            int positionbenefitId = frm.getPositionBenefitId();
            int status = frm.getStatusBenifit();
            String ipAddrStr = request.getRemoteAddr();
            String iplocal = position.getLocalIp();
            int cc = position.deleteBenefit(positionbenefitId, uId, status, ipAddrStr, iplocal);
            if (cc > 0) {
                request.setAttribute("MESSAGE", "Status updated successfully");
            }
            ArrayList positionbenefitList = position.getPositionBenefitDetailByIdforList(positionId);
            request.setAttribute("BENEFIT_LIST", positionbenefitList);
            return mapping.findForward("display_benefit");

        } else if (frm.getDoAddBenefit() != null && frm.getDoAddBenefit().equals("yes")) {

            frm.setDoAddBenefit("no");
            print(this, " getDoAddBenefit block :: ");
            Collection benefittypes = position.getBenefitType();
            frm.setBenefittype(benefittypes);
            frm.setBenefitId(-1);
            frm.setBenefitcode(position.getMaxBenefitQuestionId());
            saveToken(request);
            return mapping.findForward("add_benefit");

        } else if (frm.getDoSaveBenefit() != null && frm.getDoSaveBenefit().equals("yes")) {

            frm.setDoSaveBenefit("no");
            print(this, " getDoSaveBenefit block :: ");

            if (isTokenValid(request)) {
                resetToken(request);
                int positionId = frm.getPositionId();
                frm.setPositionId(positionId);
                int benefitId = frm.getBenefitId();
                int benefittypeId = frm.getBenefittypeId();
                String benifitname = vobj.replacename(frm.getBenefitname());

                int status = 1;
                String ipAddrStr = request.getRemoteAddr();
                String iplocal = position.getLocalIp();
                int ck = position.checkBenefitDuplicacy(benefitId, benifitname);
                if (ck == 1) {
                    saveToken(request);
                    request.setAttribute("MESSAGE", "Benefit already exists");
                    frm.setBenefitcode(frm.getBenefitcode());
                    Collection benefittypes = position.getBenefitType();
                    frm.setBenefittype(benefittypes);
                    frm.setBenefittypeId(frm.getBenefittypeId());
                    return mapping.findForward("add_benefit");
                }
                PositionInfo info = new PositionInfo(benefitId, benifitname, benefittypeId, status, uId);
                if (benefitId <= 0) {
                    int cc = position.createBenefitQuestion(info);
                    if (cc > 0) {
                        position.createHistoryAccess(null, uId, ipAddrStr, iplocal, "Data Added", 12, cc);
                        request.setAttribute("MESSAGE", "Data added successfully.");
                    }
                    frm.setBenefitId(cc);
                    ArrayList positionbenefitEdit = position.getEmployeePositionBenefitDetailByIdforEdit(positionId);
                    request.setAttribute("BENEFIT_EDIT", positionbenefitEdit);
                    ArrayList list = position.getBenefitQuestionList();
                    request.getSession().setAttribute("BENEFITQUESTIONLIST", list);
                    Collection currencys = position.getCurrencys();
                    ArrayList list1 = new ArrayList(currencys);
                    request.getSession().setAttribute("CURRENCYLIST", list1);
                    saveToken(request);
                    return mapping.findForward("add_empposbenefit");
                }
            }

        } else if (frm.getDoModifyBenefit() != null && frm.getDoModifyBenefit().equals("yes")) {

            frm.setDoModifyBenefit("no");
            int positionId = frm.getPositionId();
            frm.setPositionId(positionId);
            ArrayList positionbenefitEdit = position.getEmployeePositionBenefitDetailByIdforEdit(positionId);
            request.setAttribute("BENEFIT_EDIT", positionbenefitEdit);
            ArrayList list = position.getBenefitQuestionList();
            request.getSession().setAttribute("BENEFITQUESTIONLIST", list);
            ArrayList list1 = position.getCurrencys();
            request.getSession().setAttribute("CURRENCYLIST", list1);
            saveToken(request);
            return mapping.findForward("add_empposbenefit");

        } else if (frm.getDoSavePosBenefit() != null && frm.getDoSavePosBenefit().equals("yes")) {

            frm.setDoSavePosBenefit("no");
            print(this, " getDoSavePosBenefit block :: ");

            int positionId = frm.getPositionId();
            int benefitId[] = frm.getPosbenefitId();
            int benefittypeId[] = frm.getHiddenbenefittype();
            int benifitquestion[] = frm.getBenefetquestionIdHidden();
            String description[] = frm.getPosdescription();
            int statusbenefit[] = frm.getStatusbenefit();
            position.createPositionBenefit(positionId, benefitId, benefittypeId, benifitquestion, description, uId, statusbenefit);

            ArrayList positionbenefitList = position.getPositionBenefitDetailByIdforList(positionId);
            request.setAttribute("BENEFIT_LIST", positionbenefitList);
            return mapping.findForward("display_benefit");

        } else if (frm.getDoViewAssessmentList() != null && frm.getDoViewAssessmentList().equals("yes")) {

            frm.setDoViewAssessmentList("no");
            frm.setAssessmentDetailId(-1);
            print(this, " getDoViewAssessmentlist block :: ");
            int positionId = frm.getPositionId();
            frm.setPositionId(positionId);
            ArrayList list = position.getAssessmentList(positionId);
            request.setAttribute("ASSESSMENTLIST", list);
            return mapping.findForward("view_assessmentdetail");

        } else if (frm.getDoDeleteAssessmentDetail() != null && frm.getDoDeleteAssessmentDetail().equals("yes")) {

            frm.setDoDeleteAssessmentDetail("no");

            int positionId = frm.getPositionId();
            frm.setPositionId(positionId);
            int assessmentDetailId = frm.getAssessmentDetailId();
            frm.setAssessmentDetailId(assessmentDetailId);
            int status = frm.getStatus();
            String ipAddrStr = request.getRemoteAddr();
            String iplocal = position.getLocalIp();
            int cc = position.deleteAssessment(positionId, assessmentDetailId, uId, status, ipAddrStr, iplocal);
            if (cc > 0) {
                request.setAttribute("MESSAGE", "Status updated successfully");
            }
            ArrayList list = position.getAssessmentList(positionId);
            request.setAttribute("ASSESSMENTLIST", list);
            return mapping.findForward("view_assessmentdetail");

        } else if (frm.getDoModifyAssessmentDetail() != null && frm.getDoModifyAssessmentDetail().equals("yes")) {

            frm.setDoModifyAssessmentDetail("no");
            print(this, " getDoModifyAssessmentDetail block :: ");
            int assessmentDetailId = frm.getAssessmentDetailId();
            frm.setAssessmentDetailId(assessmentDetailId);
            frm.setPositionId(frm.getPositionId());
            Collection assessmentss = assessment.getAssessments();
            frm.setAssessments(assessmentss);
            if (frm.getAssessmentDetailId() > 0) {
                PositionInfo info = position.getAssessmentDetailByPositionId(assessmentDetailId);
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

            int positionId = frm.getPositionId();
            int assessmentDetailId = frm.getAssessmentDetailId();
            frm.setAssessmentDetailId(assessmentDetailId);
            int assessmentId = frm.getAssessmentId();
            int minScore = frm.getMinScore();
            int passingFlag = frm.getPassingFlag();
            frm.setPositionId(positionId);
            int status = 1;
            int ck = position.checkDuplicacyAssessment(positionId, assessmentDetailId, assessmentId);
            if (ck == 1) {
                frm.setAssessments(assessments);
                frm.setAssessmentId(assessmentId);
                frm.setAssessmentDetailId(assessmentDetailId);
                request.setAttribute("MESSAGE", "Assessment already exists");
                return mapping.findForward("add_assessmentdetail");
            }
            PositionInfo info = new PositionInfo(assessmentDetailId, assessmentId, minScore, passingFlag, status);
            frm.setAssessmentDetailId(-1);
            if (assessmentDetailId <= 0) {
                int cc = position.insertAssessmentDetail(info, positionId, uId);
                if (cc > 0) {
                    request.setAttribute("MESSAGE", "Data added successfully.");
                }

                ArrayList list = position.getAssessmentList(positionId);
                request.setAttribute("ASSESSMENTLIST", list);
                return mapping.findForward("view_assessmentdetail");
            } else {
                int cc = position.updateAssessmentDetail(info, uId, positionId, assessmentDetailId);
                if (cc > 0) {
                    request.setAttribute("MESSAGE", "Data updated successfully.");
                }
                ArrayList list = position.getAssessmentList(positionId);
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
            ArrayList positionList = position.getPositionByName(search, 0, count);
            int cnt = 0;
            if (positionList.size() > 0) {
                PositionInfo cinfo = (PositionInfo) positionList.get(positionList.size() - 1);
                cnt = cinfo.getPositionId();
                positionList.remove(positionList.size() - 1);
            }
            request.getSession().setAttribute("POSITION_LIST", positionList);
            request.getSession().setAttribute("COUNT_LIST", cnt + "");
            request.getSession().setAttribute("NEXT", "0");
            request.getSession().setAttribute("NEXTVALUE", "1");
        }
        return mapping.findForward("display");
    }
}
