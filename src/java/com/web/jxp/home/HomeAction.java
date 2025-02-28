package com.web.jxp.home;

import com.web.jxp.approvedby.Approvedby;
import com.web.jxp.assettype.Assettype;
import com.web.jxp.base.Base;
import com.web.jxp.base.Validate;
import com.web.jxp.bloodpressure.Bloodpressure;
import com.web.jxp.candidate.Candidate;
import com.web.jxp.candidate.CandidateInfo;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import static com.web.jxp.common.Common.*;
import com.web.jxp.companyindustry.Companyindustry;
import com.web.jxp.country.Country;
import com.web.jxp.coursetype.CourseType;
import com.web.jxp.crewtype.Crewtype;
import com.web.jxp.currency.Currency;
import com.web.jxp.degree.Degree;
import com.web.jxp.documentissuedby.Documentissuedby;
import com.web.jxp.experiencedept.ExperienceDept;
import com.web.jxp.language.Language;
import com.web.jxp.maritialstatus.MaritialStatus;
import com.web.jxp.position.Position;
import com.web.jxp.proficiency.Proficiency;
import com.web.jxp.qualificationtype.QualificationType;
import com.web.jxp.relation.Relation;
import com.web.jxp.vaccine.Vaccine;
import com.web.jxp.vaccinetype.Vaccinetype;
import java.sql.Connection;
import java.util.Collection;
import java.util.Date;
import java.util.Stack;
import org.apache.struts.upload.FormFile;

public class HomeAction extends Action 
{
    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception 
    {
        HomeForm frm = (HomeForm) form;
        Home home = new Home();
        Candidate candidate = new Candidate();
        Validate validate = new Validate();
        Country country = new Country();
        Language language = new Language();
        Proficiency proficiency = new Proficiency();
        Vaccine vaccine = new Vaccine();
        Vaccinetype vaccinetype = new Vaccinetype();
        Base base = new Base();
        Relation relation = new Relation();
        MaritialStatus maritalstatus = new MaritialStatus();
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
        Crewtype crewtype = new Crewtype();
        Documentissuedby documentissuedby = new Documentissuedby();
        int uId = 0;
        String homeEmailId = "";
        int homecandidateId = 0;
        if (request.getSession().getAttribute("HOME_EMAILID") != null) 
        {
            homeEmailId = (String)request.getSession().getAttribute("HOME_EMAILID");
             if (request.getSession().getAttribute("HOME_CANDIDATEID") != null)
             {
                 String email = (String)request.getSession().getAttribute("HOME_CANDIDATEID");
                 homecandidateId = Integer.parseInt(email);
             }
        }
        else
        {
            return mapping.findForward("homedefault");
        }
        
        if (frm.getDoAdd() != null && frm.getDoAdd().equals("yes")) 
        {
            frm.setDoAdd("no");
            frm.setCandidateId(-1);
            
            Collection countries = country.getCountrys();
            frm.setCountries(countries);
            
            Collection positions = candidate.getPositionassettypes(-1);
            frm.setPositions(positions);
            
            Collection currencies = currency.getCurrencys();
            frm.setCurrencies(currencies);
            
            frm.setRelations(relation.getRelations());
            frm.setMaritalstatuses(maritalstatus.getMaritialStatus());
            frm.setAssettypes(assettype.getAssettypes());
            frm.setCurrentDate(base.currDate3());
            frm.setPositionIdhidden(0);
            frm.setEmailId(homeEmailId);
            
            Collection states = candidate.getCountryStates(-1);
            frm.setStates(states);
            
            request.removeAttribute("PHOTO");
            request.setAttribute("FILECOUNT", candidate.changeNum(0, 3) + "");
            return mapping.findForward("add_candidate");

        } else if (frm.getDoView() != null && frm.getDoView().equals("yes")) {
            frm.setDoView("no");
            print(this, "DoView block.");
            if (request.getSession().getAttribute("HOME_CANDIDATEID") != null) {
                    String email = (String)request.getSession().getAttribute("HOME_CANDIDATEID");
                    homecandidateId = Integer.parseInt(email);
             }
            frm.setCandidateId(homecandidateId);
            CandidateInfo info = candidate.getCandidateDetail(homecandidateId);
            request.getSession().setAttribute("PASS", "" + info.getPassflag());
            request.getSession().setAttribute("CANDIDATE_DETAIL", info);
            return mapping.findForward("view_candidate");

        } else if (frm.getDoModify() != null && frm.getDoModify().equals("yes")) {
            frm.setDoModify("no");
            int candidateId = frm.getCandidateId();
            frm.setCandidateId(candidateId);
            Collection countries = country.getCountrys();
            frm.setCountries(countries);            
            Collection positions = position.getPositiontypes();
            frm.setPositions(positions);
            Collection currencies = currency.getCurrencys();
            frm.setCurrencies(currencies);
            frm.setRelations(relation.getRelations());
            frm.setMaritalstatuses(maritalstatus.getMaritialStatus());
            frm.setAssettypes(assettype.getAssettypes());
            frm.setCurrentDate(base.currDate3());
            CandidateInfo info = candidate.getCandidateDetailById(candidateId);
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
                if (info.getEmailId() != null) {
                    frm.setEmailId(info.getEmailId());
                }
                if (info.getCode1Id() != null) {
                    frm.setCode1Id(info.getCode1Id());
                }
                if (info.getContactno1() != null) {
                    frm.setContactno1(info.getContactno1());
                }
                if (info.getGender() != null) {
                    frm.setGender(info.getGender());
                }
                frm.setCountryId(info.getCountryId());
                frm.setNationalityId(info.getNationalityId());
                frm.setCityId(info.getCityId());                
                if(info.getCityId() > 0){
                    frm.setCityName(info.getCity());
                } else {
                    frm.setOthercity(info.getCity());
                }                
                frm.setCurrencyId(info.getCurrencyId());
                frm.setPositionId(info.getPositionId());
                frm.setPositionIdhidden(info.getPositionId());
                frm.setExpectedsalary(info.getExpectedsalary());
                if (info.getAddress1line1() != null) {
                    frm.setAddress1line1(info.getAddress1line1());
                }
                if (info.getAddress1line2() != null) {
                    frm.setAddress1line2(info.getAddress1line2());
                }
                if (info.getAddress1line3() != null && !info.getAddress1line3().equals("")) {
                    frm.setAddress1line3(info.getAddress1line3());
                }
                frm.setMaritalstatusId(info.getMaritalstatusId());
                frm.setAssettypeId(info.getAssettypeId());
                frm.setApplytype(info.getApplytype());
                if (info.getPhotofilename() != null) {
                    frm.setPhotofilehidden(info.getPhotofilename());
                }
                if (info.getResumefilename() != null) {
                    frm.setResumefilehidden(info.getResumefilename());
                }
                Collection states = candidate.getCountryStates(info.getCountryId());
                frm.setStates(states);
                frm.setStateId(info.getStateId());
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
                request.setAttribute("PHOTO", info.getPhotofilename());
                request.setAttribute("FILECOUNT", candidate.changeNum(info.getFilecount(), 3) + "");
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
            String dob = validate.replacedate(frm.getDob());
            String emailId = validate.replacedesc(frm.getEmailId());
            String code1 = validate.replaceint(frm.getCode1Id());

            String contactno1 = validate.replaceint(frm.getContactno1());
            String gender = validate.replacename(frm.getGender());
            int countryId = frm.getCountryId();
            int nationalityId = frm.getNationalityId();
            int positionId = frm.getPositionId();
            int positionIdhidden = frm.getPositionIdhidden();
            int currencyId = frm.getCurrencyId();
            int expectedsalary = frm.getExpectedsalary();
            int AssettypeId = frm.getAssettypeId();
            int applytype = frm.getApplytype();
            String address1line1 = validate.replacedesc(frm.getAddress1line1());
            String address1line2 = validate.replacedesc(frm.getAddress1line2());
            String address1line3 = validate.replacedesc(frm.getAddress1line3());
            int maritalstatusId = frm.getMaritalstatusId();
            int cityId = frm.getCityId();
            String othercity = frm.getOthercity();
            int stateId = frm.getStateId();
            String pincode = validate.replacedesc(frm.getPinCode());            
            if(othercity != null && !othercity.equals(""))
            {
                cityId = 0;
            }
            int status = 1;
            int age =  frm.getAge();
            String airport1 = validate.replacedesc(frm.getAirport1());
            String airport2 = validate.replacedesc(frm.getAirport2());
            String ipAddrStr = request.getRemoteAddr();
            String iplocal = candidate.getLocalIp();
            String localFile = frm.getLocalFile();
            int ck = candidate.checkDuplicacy(candidateId, emailId);
            if (ck == 1) {
                Collection countries = country.getCountrys();
                frm.setCountries(countries);
                frm.setRelations(relation.getRelations());
                Collection positions = position.getPositiontypes();
                frm.setPositions(positions);
                Collection currencies = currency.getCurrencys();
                frm.setCurrencies(currencies);
                frm.setMaritalstatuses(maritalstatus.getMaritialStatus());
                frm.setAssettypes(assettype.getAssettypes());
                frm.setAssettypeId(AssettypeId);
                frm.setCurrentDate(base.currDate3());
                request.setAttribute("MESSAGE", "EmailId already exists");
                return mapping.findForward("add_candidate");
            }

            String add_candidate_file = candidate.getMainPath("add_candidate_file");
            String foldername = candidate.createFolder(add_candidate_file);
            Date now = new Date();
            String fn = String.valueOf(now.getTime());
            String fileName1 = "", fileName2 = "";
            FormFile image1 = frm.getPhotofile();

            if (image1 != null && image1.getFileSize() > 0) {
                fileName1 = candidate.uploadFile(candidateId, frm.getPhotofilehidden(), image1, fn + "_1", add_candidate_file, foldername);
            }
            FormFile image2 = frm.getResumefile();

            if (image2 != null && image2.getFileSize() > 0) {
                fileName2 = candidate.uploadFile(candidateId, frm.getResumefilehidden(), image2, fn + "_2", add_candidate_file, foldername);
            }

            CandidateInfo info = new CandidateInfo(firstname, middlename, lastname, dob, "", emailId, code1, "", "", contactno1, "",
                    "", gender, countryId, nationalityId, address1line1, address1line2, address1line3, "", "", "",
                    "", 0, "", "", "", "", maritalstatusId, fileName1, status, fileName2, positionId, 0,
                    currencyId, expectedsalary, cityId, AssettypeId, "",othercity, applytype, "",0, stateId, pincode, age, airport1, airport2, "", "", "");
            if (candidateId <= 0) 
            {
                int cc = candidate.createCandidate(info, uId,2);
                frm.setCandidateId(cc);
                request.getSession().setAttribute("HOME_CANDIDATEID",cc+"");
                if (cc > 0) 
                {
                    String fname = frm.getFname() != null ? frm.getFname() : "";
                    if (!"".equals(fname)) {
                        String fnameval[] = fname.split("@#@");
                        String localfname[] = localFile.split("@#@");
                        int len = fnameval.length;
                        Connection conn = null;
                        try {
                            conn = candidate.getConnection();
                            for (int i = 0; i < len; i++) 
                            {
                                String fileName = candidate.saveImage(fnameval[i], add_candidate_file, foldername, candidate.getfilename(localfname[i])+"-"+fn + "_" + i);
                                candidate.createPic(conn, cc, fileName, uId, localfname[i]);
                            }
                        } finally {
                            if (conn != null) {
                                conn.close();
                            }
                        }
                    }                    
                    String name = firstname+" "+lastname;
                    if(cc > 0){
                        home.sendRegistrationEmail(name, cc, 1, "New Registration", emailId);
                    }
                    request.setAttribute("MESSAGE", "Data added successfully.");
                    candidate.createHistoryAccess(null, uId, ipAddrStr, iplocal, "Data Added", 7, cc);
                }

                request.setAttribute("CANDSAVEMODEL", "yes");
                CandidateInfo cinfo = candidate.getCandidateDetail(cc);
                request.getSession().setAttribute("CANDIDATE_DETAIL", cinfo);
                return mapping.findForward("view_candidate");
            } else {
                int cc = candidate.updateCandidateHomeForm(info, candidateId, uId, positionIdhidden);
                String fname = frm.getFname() != null ? frm.getFname() : "";
                if (!"".equals(fname)) {
                    String fnameval[] = fname.split("@#@");
                    String localfname[] = localFile.split("@#@");
                    int len = fnameval.length;
                    Connection conn = null;
                    try {
                        conn = candidate.getConnection();
                        for (int i = 0; i < len; i++) {
                            String fileName = candidate.saveImage(fnameval[i], add_candidate_file, foldername, candidate.getfilename(localfname[i])+"-"+fn + "_" + i);
                            candidate.createPic(conn, candidateId, fileName, uId, localfname[i]);
                        }
                    } finally {
                        if (conn != null) {
                            conn.close();
                        }
                    }
                }
                String name = firstname+" "+lastname;
                    if(cc > 0){
                        home.sendRegistrationEmail(name, candidateId, 1, "New Registration", emailId);
                    }
                request.setAttribute("MESSAGE", "Data updated successfully.");
                candidate.createHistoryAccess(null, uId, ipAddrStr, iplocal, "Data Updated", 7, candidateId);                
                CandidateInfo cinfo = candidate.getCandidateDetail(candidateId);
                request.getSession().setAttribute("CANDIDATE_DETAIL", cinfo);
                return mapping.findForward("view_candidate");
            }

        } else if (frm.getDoCancel() != null && frm.getDoCancel().equals("yes")) {
            frm.setDoCancel("no");
            
            return mapping.findForward("display");
            
        }   else if (frm.getDoViewlangdetail() != null && frm.getDoViewlangdetail().equals("yes")) {
            frm.setDoViewlangdetail("no");
            int candidateId = frm.getCandidateId();
            ArrayList list = candidate.getCandlanguageList(candidateId);
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
                CandidateInfo info = candidate.getcandlangForModify(candidateLangId);
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
            int ck = candidate.checkDuplicacyLang(candidateId, languageId, candidateLangId);
            if (ck == 1) 
            {
                frm.setProficiencies(proficiency.getProficiencies());
                frm.setLanguages(language.getLanguages());
                frm.setLanguageId(languageId);
                frm.setProficiencyId(proficiencyId);
                ArrayList list = candidate.getCandlanguageList(candidateId);
                request.setAttribute("CANDLANGLIST", list);
                request.setAttribute("MESSAGE", "Language already exists");
                return mapping.findForward("add_candidatelanguage");
            }
            String add_candidate_file = candidate.getMainPath("add_candidate_file");
            String foldername = candidate.createFolder(add_candidate_file);
            Date now = new Date();
            String fn = String.valueOf(now.getTime());
            String fileName1 = "", localname = "";
            FormFile filename = frm.getLangfile();
            if(filename != null)
            {
                localname = filename.getFileName().replaceAll("[/'\"]+", "");
                if(localname.contains("."))
                {
                    localname = localname.substring(0, localname.lastIndexOf("."));
                }
            }
            System.out.println("localname ::: "+localname);
            if (filename != null && filename.getFileSize() > 0) {
                fileName1 = candidate.uploadFile(candidateLangId, frm.getLangfilehidden(), filename, localname+"-"+fn, add_candidate_file, foldername);
            }
            CandidateInfo info = new CandidateInfo(candidateLangId, languageId, proficiencyId, status, fileName1);
            if (candidateLangId <= 0) {
                int cc = candidate.insertLanguage(info, candidateId, uId);
                if (cc > 0) {
                    request.setAttribute("MESSAGE", "Data added successfully.");
                }
                request.getSession().removeAttribute("FILENAME");
                frm.setProficiencies(proficiency.getProficiencies());
                frm.setProficiencyId(-1);
                frm.setLanguages(language.getLanguages());
                frm.setLanguageId(-1);
                frm.setCandidateLangId(-1);
                ArrayList list = candidate.getCandlanguageList(candidateId);
                request.setAttribute("CANDLANGLIST", list);
                return mapping.findForward("view_candidatelanguage");//view.page page
            } else {
                int cc = candidate.updateLanguage(info, candidateId, uId);
                if (cc > 0) {
                    request.setAttribute("MESSAGE", "Data updated successfully.");
                }
                request.getSession().removeAttribute("FILENAME");
                frm.setProficiencies(proficiency.getProficiencies());
                frm.setLanguages(language.getLanguages());
                frm.setCandidateLangId(-1);
                ArrayList list = candidate.getCandlanguageList(candidateId);
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
            String iplocal = candidate.getLocalIp();
            int cc = candidate.deleteLanguage(candidateId, candidateLangId, uId, status, ipAddrStr, iplocal);
            if (cc > 0) {
                request.setAttribute("MESSAGE", "Status updated successfully");
            }
            ArrayList list = candidate.getCandlanguageList(candidateId);
            request.setAttribute("CANDLANGLIST", list);
            return mapping.findForward("view_candidatelanguage");//view.page page
        } else if (frm.getDoViewhealthdetail() != null && frm.getDoViewhealthdetail().equals("yes")) {
            frm.setDoViewhealthdetail("no");
            int candidateId = frm.getCandidateId();
            frm.setCandidateId(candidateId);
            Collection bloodpressures = bloodpressure.getBloodpressures();
            frm.setBloodpressures(bloodpressures);
            CandidateInfo info = candidate.getCandidateHealthDetailBycandidateId(candidateId);
            ArrayList list = candidate.getHealthFileList(candidateId);
            
            request.getSession().setAttribute("HEALTHFILELIST", list);
            request.getSession().setAttribute("CANDHEALTHINFO", info);
            return mapping.findForward("view_candidatehealth");//do view.jsp
        } else if (frm.getDoaddhealthdetail() != null && frm.getDoaddhealthdetail().equals("yes")) {
            frm.setDoaddhealthdetail("no");
            int candidateId = frm.getCandidateId();
            frm.setCandidateId(candidateId);
            Collection bloodpressures = bloodpressure.getBloodpressures();
            frm.setBloodpressures(bloodpressures);
            CandidateInfo info = candidate.getCandidateHealthDetailBycandidateId(frm.getCandidateId());
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
            int status = 1;
            String ipAddrStr = request.getRemoteAddr();
            String iplocal = candidate.getLocalIp();
            String add_candidate_file = candidate.getMainPath("add_candidate_file");
            String foldername = candidate.createFolder(add_candidate_file);
            Date now = new Date();
            String fn = String.valueOf(now.getTime());
            String fileName1 = "", localname = "";
            FormFile filename = frm.getHealthfile();            
            if(filename != null)
            {
                localname = filename.getFileName().replaceAll("[/'\"]+", "");
                if(localname.contains("."))
                {
                    localname = localname.substring(0, localname.lastIndexOf("."));
                }
            }
            System.out.println("localname ::: "+localname);
            if (filename != null && filename.getFileSize() > 0) {
                fileName1 = candidate.uploadFile(candidateId, frm.getHealthfilehidden(), filename, localname+"-"+fn, add_candidate_file, foldername);
            }
            CandidateInfo info = new CandidateInfo(ssmf, ogukmedicalftw, ogukexp, medifitcert, medifitcertexp,
                    bloodgroup, bloodpressureId, hypertension, diabetes, smoking, fileName1, status, cov192doses);
            if (candidateId > 0) {
                int cc = candidate.insertHealthdetails(info, candidateId, uId);
                if (cc > 0) {
                    candidate.createHistoryAccess(null, uId, ipAddrStr, iplocal, "Data Added", 7, cc);
                    request.setAttribute("MESSAGE", "Data added successfully.");
                    frm.setCandidatehealthId(cc);
                }
                info = candidate.getCandidateHealthDetailBycandidateId(frm.getCandidateId());
                ArrayList list = candidate.getHealthFileList(candidateId);
            
                request.getSession().setAttribute("HEALTHFILELIST", list);
                request.getSession().setAttribute("CANDHEALTHINFO", info);
                return mapping.findForward("view_candidatehealth");
            }
            request.setAttribute("MESSAGE", "Something went wrong..");
            return mapping.findForward("cancel");
        } else if (frm.getDoViewvaccinationlist() != null && frm.getDoViewvaccinationlist().equals("yes")) {
            frm.setDoViewvaccinationlist("no");
            int candidateId = frm.getCandidateId();
            frm.setCandidateId(candidateId);
            ArrayList list = candidate.getCandVaccList(candidateId);
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
                CandidateInfo info = candidate.getcandVaccForModify(candidatevaccineId);

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
            int ck = candidate.checkDuplicacyVaccination(candidateId, candidatevaccineId, vaccinationNameId, vaccinationTypeId, dateofapplication);
            if (ck == 1) 
            {
                frm.setVacinationnames(vaccine.getVaccines());
                frm.setVaccinationtypes(vaccinetype.getVaccinetypes());
                frm.setCandidatevaccineId(candidatevaccineId);
                frm.setCurrentDate(base.currDate3());
                Collection countries = country.getCountrys();
                frm.setCountries(countries);
                request.setAttribute("MESSAGE", "Vaccination already exists");
                return mapping.findForward("add_candidatevaccination");
            }
            String add_candidate_file = candidate.getMainPath("add_candidate_file");
            String foldername = candidate.createFolder(add_candidate_file);
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
            System.out.println("localname ::: "+localname);
            if (filename != null && filename.getFileSize() > 0) {
                fileName1 = candidate.uploadFile(candidatevaccineId, frm.getVaccinedetailhiddenfile(), filename, localname+"-"+fn, add_candidate_file, foldername);
            }
            CandidateInfo info = new CandidateInfo(candidatevaccineId, vaccinationNameId, vaccinationTypeId, placeofapplicationId, dateofapplication, dateofexpiry, status, fileName1);
            if (candidatevaccineId <= 0) {
                int cc = candidate.insertVaccinationdetail(info, candidateId, uId);
                if (cc > 0) {
                    request.setAttribute("MESSAGE", "Data added successfully.");
                }
                request.getSession().removeAttribute("FILENAME");
                ArrayList list = candidate.getCandVaccList(candidateId);
                request.setAttribute("CANDVACCLIST", list);
                return mapping.findForward("view_candidatevaccination");
            } else {
                int cc = candidate.updateVaccinationRecord(info, candidateId, uId);
                if (cc > 0) {
                    request.setAttribute("MESSAGE", "Data updated successfully.");
                }
                request.getSession().removeAttribute("FILENAME");
                ArrayList list = candidate.getCandVaccList(candidateId);
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
            String iplocal = candidate.getLocalIp();
            int cc = candidate.deletevaccination(candidateId, candidatevaccineId, uId, status, ipAddrStr, iplocal);
            if (cc > 0) {
                request.setAttribute("MESSAGE", "Status updated successfully");
            }
            ArrayList list = candidate.getCandVaccList(candidateId);
            request.setAttribute("CANDVACCLIST", list);
            return mapping.findForward("view_candidatevaccination");
        }else if (frm.getDoViewtrainingcertlist() != null && frm.getDoViewtrainingcertlist().equals("yes")) {
            frm.setDoViewtrainingcertlist("no");
            int candidateId = frm.getCandidateId();
            frm.setCandidateId(candidateId);
            ArrayList list = candidate.gettrainingCertificatelist(candidateId,0,0,0);
            if (list.size() > 0) {
                list.remove(list.size() - 1);
            }
            request.setAttribute("CANDTRAININGCERTLIST", list);
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
        } else if (frm.getDoSavetrainingcertdetail() != null && frm.getDoSavetrainingcertdetail().equals("yes")) {
            frm.setDoSavetrainingcertdetail("no");

            int candidateId = frm.getCandidateId();
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
            int status = 1;
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
            System.out.println("localname ::: "+localname);
            if (filename != null && filename.getFileSize() > 0) {
                fileName1 = candidate.uploadFile(trainingandcertId, frm.getTrainingcerthiddenfile(), filename, localname+"-"+fn, add_candidate_file, foldername);
            }
            CandidateInfo info = new CandidateInfo(trainingandcertId, coursetypeId, coursenameId, educationInstitute, locationofInstituteId, "", "",
                    "", dateofissue, certificationno, dateofexpiry, courseverification, 0, status, fileName1);
            if (trainingandcertId <= 0) {
                int cc = candidate.inserttrainingCertificate(info, candidateId, uId,1);
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
        } else if (frm.getDoDeletetrainingcertdetail() != null && frm.getDoDeletetrainingcertdetail().equals("yes")) {
            frm.setDoDeletetrainingcertdetail("no");
            int candidateId = frm.getCandidateId();
            frm.setCandidateId(candidateId);
            int trainingandcertId = frm.getTrainingandcertId();
            frm.setTrainingandcertId(trainingandcertId);
            int status = frm.getStatus();
            String ipAddrStr = request.getRemoteAddr();
            String iplocal = candidate.getLocalIp();
            int cc = candidate.deletetrainingCertificate(candidateId, trainingandcertId, uId, status, ipAddrStr, iplocal);
            if (cc > 0) {
                request.setAttribute("MESSAGE", "Status updated successfully");
            }
            ArrayList list = candidate.gettrainingCertificatelist(candidateId, 0, 0, 0);
            request.setAttribute("CANDTRAININGCERTLIST", list);
            return mapping.findForward("view_trainingcertdetail");
        } else if (frm.getDoVieweducationlist() != null && frm.getDoVieweducationlist().equals("yes")) {
            frm.setDoVieweducationlist("no");
            int candidateId = frm.getCandidateId();
            frm.setCandidateId(candidateId);
            ArrayList list = candidate.geteducationlist(candidateId);
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
                CandidateInfo info = candidate.geteducationDetailById(educationdetailId);

                if (info != null) {
                    frm.setKindId(info.getKindId());
                    frm.setDegreeId(info.getDegreeId());
                    frm.setBackgroundofstudy(info.getBackgroundofstudy());
                    frm.setEducationInstitute(info.getEduinstitute());
                    frm.setLocationofInstituteId(info.getLocationofInstituteId());
                    frm.setFieldofstudy(info.getFieldofstudy());
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
            String fieldofstudy = validate.replacename(frm.getFieldofstudy());
            String passingdate = validate.replacedate(frm.getPassingdate());
            int highestqualification = frm.getHighestqualification();
            int status = 1;
            if (kindId <= 0 || degreeId <= 0) {
                Collection qualificationtypes = qualificationtype.getQualificationTypes();
                Collection degrees = degree.getDegree();
                frm.setQualificationtypes(qualificationtypes);
                frm.setDegrees(degrees);
                Collection countries = country.getCountrys();
                frm.setCountries(countries);
                frm.setEducationdetailId(educationdetailId);
                request.setAttribute("MESSAGE", "Something went wrong.");
                return mapping.findForward("modify_educationaldetail");
            }
            int ck = candidate.checkDuplicacyeducation(candidateId, educationdetailId, kindId, degreeId, backgroundofstudy);
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
            String add_candidate_file = candidate.getMainPath("add_candidate_file");
            String foldername = candidate.createFolder(add_candidate_file);
            Date now = new Date();
            String fn = String.valueOf(now.getTime());
            String fileName1 = "", localname = "";
            FormFile filename = frm.getEducationfile();
            if(filename != null)
            {
                localname = filename.getFileName().replaceAll("[/'\"]+", "");
                if(localname.contains("."))
                {
                    localname = localname.substring(0, localname.lastIndexOf("."));
                }
            }
            System.out.println("localname ::: "+localname);
            if (filename != null && filename.getFileSize() > 0) {
                fileName1 = candidate.uploadFile(educationdetailId, frm.getEducationhiddenfile(), filename, localname+"-"+fn, add_candidate_file, foldername);
            }
            CandidateInfo info = new CandidateInfo(educationdetailId, kindId, degreeId, backgroundofstudy, educationInstitute,
                    0, fieldofstudy, "", passingdate, highestqualification, status, fileName1);
            if (educationdetailId <= 0) {
                int cc = candidate.inserteducation(info, candidateId, uId);
                if (cc > 0) {
                    request.setAttribute("MESSAGE", "Data added successfully.");
                }
                request.getSession().removeAttribute("FILENAME");
                ArrayList list = candidate.geteducationlist(candidateId);
                request.setAttribute("CANDEDUCATIONLIST", list);
                return mapping.findForward("view_educationallist");
            } else {
                int cc = candidate.updateeducation(info, candidateId, uId);
                if (cc > 0) {
                    request.setAttribute("MESSAGE", "Data updated successfully.");
                }
                request.getSession().removeAttribute("FILENAME");
                ArrayList list = candidate.geteducationlist(candidateId);
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
            String iplocal = candidate.getLocalIp();
            int cc = candidate.deleteeducation(candidateId, educationdetailId, uId, status, ipAddrStr, iplocal);
            if (cc > 0) {
                request.setAttribute("MESSAGE", "Status updated successfully");
            }
            ArrayList list = candidate.geteducationlist(candidateId);
            request.setAttribute("CANDEDUCATIONLIST", list);
            return mapping.findForward("view_educationallist");
        } else if (frm.getDoViewexperiencelist() != null && frm.getDoViewexperiencelist().equals("yes")) {
            frm.setDoViewexperiencelist("no");
            int candidateId = frm.getCandidateId();
            frm.setCandidateId(candidateId);
            ArrayList list = candidate.getexperiencelist(candidateId);
            request.setAttribute("CANDEXPERIENCELIST", list);
            return mapping.findForward("view_experiencelist");
        } else if (frm.getDoaddexperiencedetail() != null && frm.getDoaddexperiencedetail().equals("yes")) {
            frm.setDoaddexperiencedetail("no");
            int candidateId = frm.getCandidateId();
            frm.setCandidateId(candidateId);
            Collection currencies = currency.getCurrencys();
            Collection companyindustries = companyindustry.getcompanyindustrys();
            Collection countries = country.getCountrys();
            Collection assettypes = assettype.getAssettypes();
            Collection positions = candidate.getPositionassettypes(-1);
            Collection departments = experiencedept.getExperienceDepts();
            Collection crewtypes = crewtype.getCrewtypes();
            frm.setCurrentDate(base.currDate3());
            frm.setCurrencies(currencies);
            frm.setCompanyindustries(companyindustries);
            frm.setCountries(countries);
            frm.setAssettypes(assettypes);
            frm.setPositions(positions);
            frm.setDepartments(departments);
            frm.setCrewtypes(crewtypes);
            int experiencedetailId = frm.getExperiencedetailId();
            frm.setExperiencedetailId(experiencedetailId);
            request.getSession().removeAttribute("FILENAME");
            request.getSession().removeAttribute("FILENAME1");
            if (experiencedetailId > 0) {
                CandidateInfo info = candidate.getexperiencedetailById(experiencedetailId);
                if (info != null) {
                    positions = candidate.getPositionassettypes(info.getAssettypeId());
                    frm.setPositions(positions);
                    frm.setCompanyname(info.getCompanyname());
                    frm.setCompanyindustryId(info.getCompanyindustryId());
                    frm.setAssettypeId(info.getAssettypeId());
                    frm.setAssetname(info.getAssetName());
                    frm.setPositionId(info.getPositionId());
                    frm.setDepartmentId(info.getDepartmentId());
                    frm.setCountryId(info.getCountryId());
                    frm.setCityId(info.getCityId());
                    frm.setCityName(info.getCity());
                    frm.setLastdrawnsalarycurrencyId(info.getLastdrawnsalarycurrencyId());
                    frm.setLastdrawnsalary(info.getLastdrawnsalary());
                    frm.setWorkenddate(info.getWorkenddate());
                    frm.setWorkstartdate(info.getWorkstartdate());
                    frm.setCurrentworkingstatus(info.getCurrentworkingstatus());
                    frm.setExperiencehiddenfile(info.getExperiencefilename());
                    frm.setWorkinghiddenfile(info.getWorkfilename());
                    frm.setCurrentDate(base.currDate3());
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
            String lastdrawnsalary = validate.replacedesc(frm.getLastdrawnsalary());
            String workstartdate = validate.replacedate(frm.getWorkstartdate());
            String workenddate = validate.replacedate(frm.getWorkenddate());
            int currentworkstatus = frm.getCurrentworkingstatus();
            int lastdrawnsalarycurrencyId = frm.getLastdrawnsalarycurrencyId();
            int status = 1;
            if (currentworkstatus > 0) {
                workenddate = "";
            }

            if (assettypeId <= 0 || comnpanyindustryId <= 0 || companyName.equals("") || workstartdate.equals("")) {
                Collection currencies = currency.getCurrencys();
                Collection companyindustries = companyindustry.getcompanyindustrys();
                Collection countries = country.getCountrys();
                Collection assettypes = assettype.getAssettypes();
                Collection positions = candidate.getPositionassettypes(assettypeId);
                Collection departments = experiencedept.getExperienceDepts();
                Collection crewtypes = crewtype.getCrewtypes();
                frm.setCurrencies(currencies);
                frm.setCompanyindustries(companyindustries);
                frm.setCountries(countries);
                frm.setAssettypes(assettypes);
                frm.setPositions(positions);
                frm.setDepartments(departments);
                frm.setCrewtypes(crewtypes);
                frm.setExperiencedetailId(experiencedetailId);
                request.setAttribute("MESSAGE", "Something went wrong.");
                return mapping.findForward("modify_experiencedetail");
            }
            int ck = candidate.checkDuplicacyexperience(candidateId, experiencedetailId, companyName, workstartdate, workenddate);
            if (ck == 1) {
                Collection currencies = currency.getCurrencys();
                Collection companyindustries = companyindustry.getcompanyindustrys();
                Collection countries = country.getCountrys();
                Collection assettypes = assettype.getAssettypes();
                Collection positions = position.getPositiontypes();
                Collection departments = experiencedept.getExperienceDepts();
                Collection crewtypes = crewtype.getCrewtypes();
                frm.setCurrentDate(base.currDate3());
                frm.setCurrencies(currencies);
                frm.setCompanyindustries(companyindustries);
                frm.setCountries(countries);
                frm.setAssettypes(assettypes);
                frm.setPositions(positions);
                frm.setDepartments(departments);
                frm.setCrewtypes(crewtypes);
                frm.setExperiencedetailId(experiencedetailId);
                request.setAttribute("MESSAGE", "Experience already exists");
                return mapping.findForward("modify_experiencedetail");
            }
            String add_candidate_file = candidate.getMainPath("add_candidate_file");
            String foldername = candidate.createFolder(add_candidate_file);
            Date now = new Date();
            String fn = String.valueOf(now.getTime());
            String fileName1 = "", localname = "";
            FormFile filename = frm.getExperiencefile();
            if(filename != null)
            {
                localname = filename.getFileName().replaceAll("[/'\"]+", "");
                if(localname.contains("."))
                {
                    localname = localname.substring(0, localname.lastIndexOf("."));
                }
            }
            System.out.println("localname ::: "+localname);
            if (filename != null && filename.getFileSize() > 0) {
                fileName1 = candidate.uploadFile(experiencedetailId, frm.getExperiencehiddenfile(), filename, localname+"-"+fn, add_candidate_file, foldername);
            }

            CandidateInfo info = new CandidateInfo(experiencedetailId, companyName, comnpanyindustryId, assettypeId, assetname, positionId, departmentId, countryId,
                    cityId, "", 0, lastdrawnsalary, workstartdate, workenddate, currentworkstatus, 0, 0, "", 0,
                    "", "", 0, 0, 0, 0, lastdrawnsalarycurrencyId, status, fileName1, "", "");
            if (experiencedetailId <= 0) {
                int cc = candidate.insertexperiencedetail(info, candidateId, uId);
                if (cc > 0) {
                    request.setAttribute("MESSAGE", "Data added successfully.");
                }
                request.getSession().removeAttribute("FILENAME");
                request.getSession().removeAttribute("FILENAME1");
                ArrayList list = candidate.getexperiencelist(candidateId);
                request.setAttribute("CANDEXPERIENCELIST", list);
                return mapping.findForward("view_experiencelist");
            } else {
                int cc = candidate.updateexperiencedetail(info, candidateId, uId);
                if (cc > 0) {
                    request.setAttribute("MESSAGE", "Data updated successfully.");
                }
                request.getSession().removeAttribute("FILENAME");
                request.getSession().removeAttribute("FILENAME1");
                ArrayList list = candidate.getexperiencelist(candidateId);
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
            String iplocal = candidate.getLocalIp();
            int cc = candidate.deleteexperiencedetail(candidateId, experiencedetailId, uId, status, ipAddrStr, iplocal);
            if (cc > 0) {
                request.setAttribute("MESSAGE", "Status updated successfully");
            }
            ArrayList list = candidate.getexperiencelist(candidateId);
            request.setAttribute("CANDEXPERIENCELIST", list);
            return mapping.findForward("view_experiencelist");
        }  else if (frm.getDoViewgovdocumentlist() != null && frm.getDoViewgovdocumentlist().equals("yes")) {
            frm.setDoViewgovdocumentlist("no");
            int candidateId = frm.getCandidateId();
            frm.setCandidateId(candidateId);
            ArrayList list = candidate.getCandgovdocList(candidateId);
            request.setAttribute("CANDGOVDOCLIST", list);
            return mapping.findForward("view_documentdetail");
        } else if (frm.getDomodifygovdocumentdetail() != null && frm.getDomodifygovdocumentdetail().equals("yes")) {
            frm.setDomodifygovdocumentdetail("no");
            int candidateId = frm.getCandidateId();
            int govdocumentId = frm.getGovdocumentId();
            frm.setCandidateId(candidateId);
            Collection documentTypes = candidate.getDocumentTypesForModule(1, 1);
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
            int status = 1;
            if (documentTypeId <= 0 || DocumentIssuedbyId <= 0) {
                Collection documentTypes = candidate.getDocumentTypesForModule(1, 1);
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
                Collection documentTypes = candidate.getDocumentTypesForModule(1, 1);
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
                if (cc > 0) {
                    String fname = frm.getFname() != null ? frm.getFname() : "";
                        if (!"".equals(fname)) {
                            String fnameval[] = fname.split("@#@");
                            int len = fnameval.length;
                            Connection conn = null;
                            try {
                                conn = candidate.getConnection();
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
                ArrayList list = candidate.getCandgovdocList(candidateId);
                request.setAttribute("CANDGOVDOCLIST", list);
                return mapping.findForward("view_documentdetail");
            } else {
                int cc = candidate.updateGovdocumentRecord(info, candidateId, uId);
                if (cc > 0) {
                    String fname = frm.getFname() != null ? frm.getFname() : "";
                    if (!"".equals(fname)) {
                        String fnameval[] = fname.split("@#@");
                        int len = fnameval.length;
                        Connection conn = null;
                        try {
                            conn = candidate.getConnection();
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
                ArrayList list = candidate.getCandgovdocList(candidateId);
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
            String iplocal = candidate.getLocalIp();
            int cc = candidate.deletegovdocument(candidateId, govdocumentId, uId, status, ipAddrStr, iplocal);
            if (cc > 0) {
                request.setAttribute("MESSAGE", "Status updated successfully");
            }
            ArrayList list = candidate.getCandgovdocList(candidateId);
            request.setAttribute("CANDGOVDOCLIST", list);
            return mapping.findForward("view_documentdetail");
        }
        if(frm.getDoLogout() != null && frm.getDoLogout().equals("yes"))
        {
            request.getSession().invalidate();
            return mapping.findForward("display");
        }
        else
        {
            print(this, "else block.");
            
            if (frm.getCtp() == 0) {
                Stack<String> blist = new Stack<String>();
                if (request.getSession().getAttribute("BACKURL") != null) {
                    blist = (Stack<String>) request.getSession().getAttribute("BACKURL");
                }
                blist.push(request.getRequestURI());
                request.getSession().setAttribute("BACKURL", blist);
            }
        }
        return mapping.findForward("display");
    }    
}