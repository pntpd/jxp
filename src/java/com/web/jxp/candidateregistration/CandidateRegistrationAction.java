package com.web.jxp.candidateregistration;

import com.web.jxp.approvedby.Approvedby;
import com.web.jxp.assettype.Assettype;
import com.web.jxp.base.Validate;
import com.web.jxp.candidate.Candidate;
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
import com.web.jxp.home.Home;
import com.web.jxp.language.Language;
import com.web.jxp.maritialstatus.MaritialStatus;
import com.web.jxp.proficiency.Proficiency;
import com.web.jxp.qualificationtype.QualificationType;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import org.apache.struts.upload.FormFile;

public class CandidateRegistrationAction extends Action {

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        CandidateRegistrationForm frm = (CandidateRegistrationForm) form;
        CandidateRegistration cr = new CandidateRegistration();
        Candidate candidate = new Candidate();
        Home home = new Home();
        Validate validate = new Validate();
        Country country = new Country();
        Language language = new Language();
        Proficiency proficiency = new Proficiency();
        MaritialStatus maritalstatus = new MaritialStatus();
        Approvedby approvedby = new Approvedby();
        Currency currency = new Currency();
        QualificationType qualificationtype = new QualificationType();
        Degree degree = new Degree();
        Assettype assettype = new Assettype();
        int uId = 0;
        String emailId = "";
        int regcandidateId = 0;
        frm.setCurrentDate(cr.currDate3());

        if (request.getSession().getAttribute("REG_EMAILID") != null) {
            emailId = (String) request.getSession().getAttribute("REG_EMAILID");
            if (request.getSession().getAttribute("REG_CANDIDATEID") != null) {
                String regcandidateIds = (String) request.getSession().getAttribute("REG_CANDIDATEID");
                regcandidateId = Integer.parseInt(regcandidateIds);
            }
        } else {
            return mapping.findForward("regdefault");
        }
        if (frm.getDoModify() != null && frm.getDoModify().equals("yes")) {
            print(this, "Modify Block");
            CandidateRegistrationInfo info = cr.getCandidateData(regcandidateId);
            frm.setEmailId(emailId);
            frm.setMaritalstatuses(maritalstatus.getMaritialStatus());
            frm.setCountries(country.getCountrys());
            frm.setCurrencies(currency.getCurrencys());
            frm.setAssettypes(assettype.getAssettypes());
            frm.setLanguages(language.getLanguages());
            frm.setQualificationtypes(qualificationtype.getQualificationTypes());
            frm.setDegrees(degree.getDegree());
            frm.setProficiencies(proficiency.getProficiencies());
            frm.setCoursenames(candidate.getCourseName());
            frm.setApprovedbys(approvedby.getApprovedbys());
            if (info != null) {
                frm.setOnshore(info.getOnshore());
                request.setAttribute("RESUMEFILE", info.getResumeFile());
                frm.setLocalFile(info.getResumeFile());
                frm.setFirstname(info.getFirstname());
                frm.setMiddlename(info.getMiddlename());
                frm.setLastname(info.getLastname());
                frm.setDob(info.getDob());
                int age = 0;
                if (!info.getDob().equals("") && info.getDob() != null) {
                    age = cr.calculateAge(info.getDob());
                }
                frm.setAge(age);
                if (info.getPassport() > 0) {
                    frm.setPassport(1);
                }
                frm.setPancard(cr.decipher(info.getPancard()));
                frm.setAdhaar(cr.decipher(info.getAdhaar()));
                frm.setHdnadhaarfile(info.getAdhaarFile());
                frm.setHdnpanfile(info.getPanFile());
                request.setAttribute("ADHAARFILE", info.getAdhaarFile());
                request.setAttribute("PANFILE", info.getPanFile());

                frm.setPlaceofbirth(info.getPlaceofbirth());
                frm.setGender(info.getGender());
                frm.setMaritalstatusId(info.getMaritalstatusId());
                frm.setNationalityId(info.getNationalityId());
                frm.setReligion(info.getReligion());

                frm.setCode1Id(info.getCode1());
                frm.setContactno1(decipher(info.getContactno1()));
                frm.setAddress1line1(info.getAddressline1_1());
                frm.setAddress1line2(info.getAddressline1_2());

                frm.setAddress1line3(info.getAddressline1_3());
                frm.setCountryId(info.getCountryId());
                frm.setStates(cr.getCountryStates(info.getCountryId()));
                frm.setStateId(info.getStateId());
                frm.setCityId(info.getCityId());
                frm.setCityName(info.getCity1_name());
                frm.setPinCode(info.getPincode());

                frm.setSameAsPermanent(info.getSameAsPermanent());
                if (info.getSameAsPermanent() > 0) {
                    request.setAttribute("SAMEASADDRESS", info.getSameAsPermanent());
                    frm.setStates2(cr.getCountryStates(-1));
                } else {
                    frm.setAddress2line1(info.getAddressline2_1());
                    frm.setAddress2line2(info.getAddressline2_2());
                    frm.setAddress2line3(info.getAddressline2_3());
                    frm.setCountryId2(info.getCountryId2());
                    frm.setStates2(cr.getCountryStates(info.getCountryId2()));
                    frm.setStateId2(info.getStateId2());
                    frm.setCityId2(info.getCityId2());
                    frm.setCityName2(info.getCity2_name());
                    frm.setPinCode2(info.getPincode2());
                }
                frm.setAssettypeId(info.getAssettypeId());
                frm.setPositions(candidate.getPositionassettypes(info.getAssettypeId()));
                frm.setPositionId(info.getPositionId());
                frm.setEcurrencyId(info.getEcurrencyId());
                frm.setExpectedsalary(info.getExpectedsalary());
                frm.setLcurrencyId(info.getLcurrencyId());
                frm.setLastdrawnsalary(info.getLastdrawnsalary());

                frm.setKindId(info.getKindId());
                frm.setDegreeId(info.getDegreeId());
//                frm.setLanguageProf1(cr.parseCommaDelimInt(info.getProficiencytypes()));
                request.setAttribute("LANGPROFTYPE", info.getProficiencytypes());
                ArrayList langlist = cr.getLanguage(regcandidateId);
                int lc = 0;
                if (langlist.size() > 0) {
                    CandidateRegistrationInfo lang_info = null;
                    for (int i = 0; i < langlist.size(); i++) {
                        lang_info = (CandidateRegistrationInfo) langlist.get(i);
                        if (lang_info != null) {
                            if (lc == 0) {
                                frm.setLanguageId2(lang_info.getDdlValue());
                                lc++;
                            } else if (lc == 1) {
                                frm.setLanguageId3(lang_info.getDdlValue());
                                lc++;
                            } else if (lc == 2) {
                                frm.setLanguageId4(lang_info.getDdlValue());
                                lc++;
                            }
                        }
                    }
                }
                frm.setIsFresher(info.getIsFresher());
                request.setAttribute("ISFRESHER", info.getIsFresher());
                if (info.getIsFresher() <= 0) {
                    ArrayList explist = cr.getExperiencDetailsById(regcandidateId);
                    request.getSession().setAttribute("CANDREGWORKEXP", explist);
                    frm.setExpCount(explist.size());
                }
                ArrayList certlist = cr.getCertificatesById(regcandidateId);
                request.getSession().setAttribute("CANDREGCERT", certlist);
                frm.setCertCount(certlist.size());
            }
            return mapping.findForward("display");
        } else if (frm.getDoSaveDraft() != null && frm.getDoSaveDraft().equals("yes")) {
            print(this, "Draft Block");
            frm.setDoSaveDraft("no");

            int candidateId = frm.getCandidateId();
            int onshore = frm.getOnshore();

            String firstname = validate.replacename(frm.getFirstname());
            String middlename = validate.replacename(frm.getMiddlename());
            String lastname = validate.replacename(frm.getLastname());
            String dob = validate.replacedate(frm.getDob());
            String placeofbirth = validate.replacename(frm.getPlaceofbirth());

            int passport = frm.getPassport();
            String gender = frm.getGender();
            int maritalstatusId = frm.getMaritalstatusId();
            int nationalityId = frm.getNationalityId();
            String religion = validate.replacename(frm.getReligion());

            String adhaar = validate.replacedesc(frm.getAdhaar());
            String panno = validate.replacedesc(frm.getPancard());
            String code1 = validate.replaceint(frm.getCode1Id());
            String contactno1 = validate.replaceint(frm.getContactno1());

            String addressline1_1 = validate.replacedesc(frm.getAddress1line1());
            String addressline1_2 = validate.replacedesc(frm.getAddress1line2());
            String addressline1_3 = validate.replacedesc(frm.getAddress1line3());
            String pincode = validate.replaceint(frm.getPinCode());
            int countryId = frm.getCountryId();
            int stateId = frm.getStateId();
            int cityId = frm.getCityId();

            int sameAsPermanent = frm.getSameAsPermanent();
            String addressline2_1 = "";
            String addressline2_2 = "";
            String addressline2_3 = "";
            String pincode2 = "";
            int countryId2 = 0;
            int stateId2 = 0;
            int cityId2 = 0;

            if (sameAsPermanent == 0) {
                addressline2_1 = validate.replacedesc(frm.getAddress2line1());
                addressline2_2 = validate.replacedesc(frm.getAddress2line2());
                addressline2_3 = validate.replacedesc(frm.getAddress2line3());
                pincode2 = validate.replaceint(frm.getPinCode2());
                countryId2 = frm.getCountryId2();
                stateId2 = frm.getStateId2();
                cityId2 = frm.getCityId2();
            }

            int assettypeId = frm.getAssettypeId();
            int positionId = frm.getPositionId();
            int ecurrencyId = frm.getEcurrencyId();
            int expectedsalary = frm.getExpectedsalary();
            int lcurrencyId = frm.getLcurrencyId();

            int lastdrawnsalary = frm.getLastdrawnsalary();
            int kindId = frm.getKindId();
            int degreeId = frm.getDegreeId();
            int isFresher = frm.getIsFresher();

            int languageId1 = frm.getLanguageId1();
            int languageId2 = frm.getLanguageId2();
            int languageId3 = frm.getLanguageId3();
            int languageId4 = frm.getLanguageId4();

            int languageProf1[] = frm.getLanguageProf1();

            String ipAddrStr = request.getRemoteAddr();
            String iplocal = cr.getLocalIp();

            FormFile adhaarfile = frm.getAdhaarfile();
            String hdnadhaarfile = frm.getHdnadhaarfile();
            FormFile panfile = frm.getPanfile();
            String hdnpanfile = frm.getHdnpanfile();

            String add_candidate_file = cr.getMainPath("add_candidate_file");
            String foldername = cr.createFolder(add_candidate_file);
            Date now = new Date();
            String fn = String.valueOf(now.getTime());
            String adhaarfileName = "", panfileName = "";
            boolean adharflag = false, panflag = false;
            if (adhaarfile != null && adhaarfile.getFileSize() > 0) {
                adharflag = true;
                adhaarfileName = cr.uploadFile(0, hdnadhaarfile, adhaarfile, fn + "_2", add_candidate_file, foldername);
            }
            if (panfile != null && panfile.getFileSize() > 0) {
                panflag = true;
                panfileName = cr.uploadFile(0, hdnpanfile, panfile, fn + "_3", add_candidate_file, foldername);
            }

            CandidateRegistrationInfo info = new CandidateRegistrationInfo(onshore, firstname, middlename, lastname, dob, placeofbirth,
                    passport, gender, maritalstatusId, nationalityId, religion, adhaar,
                    panno, code1, contactno1, emailId, addressline1_1, addressline1_2, addressline1_3, pincode, countryId, stateId,
                    cityId, sameAsPermanent, addressline2_1, addressline2_2, addressline2_3, pincode2, countryId2, stateId2, cityId2,
                    assettypeId, positionId, ecurrencyId, expectedsalary, lcurrencyId, lastdrawnsalary, kindId, degreeId, isFresher,
                    languageId1, languageProf1, languageId2, languageId3, languageId4, 1, adhaarfileName, panfileName);

            int cc = 0;
            if (regcandidateId > 0) {
                cc = cr.updateCandidate(info, uId, 2, 1, regcandidateId);
            } else {
                cc = cr.createCandidate(info, uId, 2, 1);
            }
            if (cc > 0) {
                String fname = frm.getFname() != null ? frm.getFname() : "";
                String localFile = frm.getLocalFile();
                if (!"".equals(fname)) {
                    String fnameval[] = fname.split("@#@");
                    String localfname[] = localFile.split("@#@");
                    int len = fnameval.length;
                    Connection conn = null;
                    try {
                        conn = cr.getConnection();
                        for (int i = 0; i < len; i++) {
                            String fileName = cr.saveImage(fnameval[i], add_candidate_file, foldername, cr.getfilename(localfname[i]) + "-" + fn + "_" + i);
                            cr.createResume(conn, cc, fileName, uId, localfname[i]);
                        }
                    } finally {
                        if (conn != null) {
                            conn.close();
                        }
                    }
                }
//                    --------------------------------------- Document--------------------------------------------------------
                cr.insertGovdocumentdetail(cc, uId, info, add_candidate_file, adharflag, panflag);
//                    --------------------------------------- Work Experience --------------------------------------------------------
                ArrayList worklist = new ArrayList();
                if (request.getSession().getAttribute("CANDREGWORKEXP") != null) {
                    worklist = (ArrayList) request.getSession().getAttribute("CANDREGWORKEXP");
                }
                request.getSession().removeAttribute("CANDREGWORKEXP");
                if (worklist.size() > 0) {
                    cr.insertexperiencedetail(worklist, cc, uId);
                }
//                    --------------------------------------- Certification --------------------------------------------------------
                ArrayList certlist = new ArrayList();
                if (request.getSession().getAttribute("CANDREGCERT") != null) {
                    certlist = (ArrayList) request.getSession().getAttribute("CANDREGCERT");
                }
                request.getSession().removeAttribute("CANDREGCERT");
                if (certlist.size() > 0) {
                    cr.inserttrainingCertificate(certlist, cc, uId);
                }
//                    --------------------------------------- Language --------------------------------------------------------
                cr.insertLanguage(info, cc);
//                    -------------------------------------------------------------------------------------------------------------------
                String name = firstname + " " + lastname;
                home.sendRegistrationEmail(name, candidateId, 1, "New Registration", emailId);

                cr.createHistoryAccess(null, uId, ipAddrStr, iplocal, "Data Added", 7, cc);
            }
//            request.setAttribute("CANDSAVEMODEL", "yes");************************************************************

            CandidateRegistrationInfo info1 = cr.getCandidateData(regcandidateId);
            frm.setEmailId(emailId);
            frm.setMaritalstatuses(maritalstatus.getMaritialStatus());
            frm.setCountries(country.getCountrys());
            frm.setCurrencies(currency.getCurrencys());
            frm.setAssettypes(assettype.getAssettypes());
            frm.setLanguages(language.getLanguages());
            frm.setQualificationtypes(qualificationtype.getQualificationTypes());
            frm.setDegrees(degree.getDegree());
            frm.setProficiencies(proficiency.getProficiencies());
            frm.setCoursenames(candidate.getCourseName());
            frm.setApprovedbys(approvedby.getApprovedbys());

            if (info1 != null) {
                frm.setOnshore(info1.getOnshore());
                request.setAttribute("RESUMEFILE", info1.getResumeFile());
                frm.setLocalFile(info1.getResumeFile());
                frm.setFirstname(info1.getFirstname());
                frm.setMiddlename(info1.getMiddlename());
                frm.setLastname(info1.getLastname());
                frm.setDob(info1.getDob());
                int age = 0;
                if (!info.getDob().equals("") && info.getDob() != null) {
                    age = cr.calculateAge(info1.getDob());
                }
                frm.setAge(age);

                if (info1.getPassport() > 0) {
                    frm.setPassport(1);
                }
                frm.setPancard(cr.decipher(info1.getPancard()));
                frm.setHdnpanfile(info1.getPanFile());
                frm.setAdhaar(cr.decipher(info1.getAdhaar()));
                frm.setHdnadhaarfile(info1.getAdhaarFile());
                request.setAttribute("ADHAARFILE", info1.getAdhaarFile());
                request.setAttribute("PANFILE", info1.getPanFile());

                frm.setPlaceofbirth(info1.getPlaceofbirth());
                frm.setGender(info1.getGender());
                frm.setMaritalstatusId(info1.getMaritalstatusId());
                frm.setNationalityId(info1.getNationalityId());
                frm.setReligion(info1.getReligion());

                frm.setCode1Id(info1.getCode1());
                frm.setContactno1(decipher(info1.getContactno1()));
                frm.setAddress1line1(info1.getAddressline1_1());
                frm.setAddress1line2(info1.getAddressline1_2());

                frm.setAddress1line3(info1.getAddressline1_3());
                frm.setCountryId(info1.getCountryId());
                if (info1.getCountryId() > 0) {
                    frm.setStates(cr.getCountryStates(info1.getCountryId()));
                } else {
                    frm.setStates(cr.getCountryStates(-1));
                }
                frm.setStateId(info1.getStateId());
                frm.setCityId(info1.getCityId());
                frm.setCityName(info1.getCity1_name());
                frm.setPinCode(info1.getPincode());

                frm.setSameAsPermanent(info1.getSameAsPermanent());
                if (info1.getSameAsPermanent() > 0) {
                    request.setAttribute("SAMEASADDRESS", info1.getSameAsPermanent());
                    frm.setStates2(cr.getCountryStates(-1));
                } else {
                    frm.setAddress2line1(info1.getAddressline2_1());
                    frm.setAddress2line2(info1.getAddressline2_2());
                    frm.setAddress2line3(info1.getAddressline2_3());
                    frm.setCountryId2(info1.getCountryId2());
                    frm.setStates2(cr.getCountryStates(info1.getCountryId2()));
                    frm.setStateId2(info1.getStateId2());
                    frm.setCityId2(info1.getCityId2());
                    frm.setCityName2(info1.getCity2_name());
                    frm.setPinCode2(info1.getPincode2());
                }
                frm.setAssettypeId(info1.getAssettypeId());
                frm.setPositions(candidate.getPositionassettypes(info1.getAssettypeId()));
                frm.setPositionId(info1.getPositionId());
                frm.setEcurrencyId(info1.getEcurrencyId());
                frm.setExpectedsalary(info1.getExpectedsalary());
                frm.setLcurrencyId(info1.getLcurrencyId());
                frm.setLastdrawnsalary(info1.getLastdrawnsalary());

                frm.setKindId(info1.getKindId());
                frm.setDegreeId(info1.getDegreeId());
                frm.setIsFresher(info1.getIsFresher());

//                frm.setLanguageProf1(cr.parseCommaDelimInt(info.getProficiencytypes()));
                request.setAttribute("LANGPROFTYPE", info.getProficiencytypes());
                ArrayList langlist = cr.getLanguage(regcandidateId);
                int lc = 0;
                if (langlist.size() > 0) {
                    CandidateRegistrationInfo lang_info = null;
                    for (int i = 0; i < langlist.size(); i++) {
                        lang_info = (CandidateRegistrationInfo) langlist.get(i);
                        if (lang_info != null) {
                            if (lc == 0) {
                                frm.setLanguageId2(lang_info.getDdlValue());
                                lc++;
                            } else if (lc == 1) {
                                frm.setLanguageId3(lang_info.getDdlValue());
                                lc++;
                            } else if (lc == 2) {
                                frm.setLanguageId4(lang_info.getDdlValue());
                                lc++;
                            }
                        }
                    }
                }

                request.setAttribute("ISFRESHER", info1.getIsFresher());
                if (info1.getIsFresher() <= 0) {
                    ArrayList explist = cr.getExperiencDetailsById(regcandidateId);
                    request.getSession().setAttribute("CANDREGWORKEXP", explist);
                    frm.setExpCount(explist.size());
                }
                ArrayList certlist = cr.getCertificatesById(regcandidateId);
                request.getSession().setAttribute("CANDREGCERT", certlist);
                frm.setCertCount(certlist.size());
            }

            return mapping.findForward("display");
        } else if (frm.getDoSave() != null && frm.getDoSave().equals("yes")) {
            print(this, "Save Block");
            frm.setDoSave("no");

            int candidateId = frm.getCandidateId();
            int onshore = frm.getOnshore();

            String firstname = validate.replacename(frm.getFirstname());
            String middlename = validate.replacename(frm.getMiddlename());
            String lastname = validate.replacename(frm.getLastname());
            String dob = validate.replacedate(frm.getDob());
            String placeofbirth = validate.replacename(frm.getPlaceofbirth());

            int passport = frm.getPassport();
            String gender = frm.getGender();
            int maritalstatusId = frm.getMaritalstatusId();
            int nationalityId = frm.getNationalityId();
            String religion = validate.replacename(frm.getReligion());

            String adhaar = validate.replacedesc(frm.getAdhaar());
            String panno = validate.replacedesc(frm.getPancard());
            String code1 = validate.replaceint(frm.getCode1Id());
            String contactno1 = validate.replaceint(frm.getContactno1());

            String addressline1_1 = validate.replacedesc(frm.getAddress1line1());
            String addressline1_2 = validate.replacedesc(frm.getAddress1line2());
            String addressline1_3 = validate.replacedesc(frm.getAddress1line3());
            String pincode = validate.replaceint(frm.getPinCode());
            int countryId = frm.getCountryId();
            int stateId = frm.getStateId();
            int cityId = frm.getCityId();

            int sameAsPermanent = frm.getSameAsPermanent();
            String addressline2_1 = "";
            String addressline2_2 = "";
            String addressline2_3 = "";
            String pincode2 = "";
            int countryId2 = 0;
            int stateId2 = 0;
            int cityId2 = 0;

            if (sameAsPermanent == 0) {
                addressline2_1 = validate.replacedesc(frm.getAddress2line1());
                addressline2_2 = validate.replacedesc(frm.getAddress2line2());
                addressline2_3 = validate.replacedesc(frm.getAddress2line3());
                pincode2 = validate.replaceint(frm.getPinCode2());
                countryId2 = frm.getCountryId2();
                stateId2 = frm.getStateId2();
                cityId2 = frm.getCityId2();
            }

            int assettypeId = frm.getAssettypeId();
            int positionId = frm.getPositionId();
            int ecurrencyId = frm.getEcurrencyId();
            int expectedsalary = frm.getExpectedsalary();
            int lcurrencyId = frm.getLcurrencyId();

            int lastdrawnsalary = frm.getLastdrawnsalary();
            int kindId = frm.getKindId();
            int degreeId = frm.getDegreeId();
            int isFresher = frm.getIsFresher();

            int languageId1 = frm.getLanguageId1();
            int languageId2 = frm.getLanguageId2();
            int languageId3 = frm.getLanguageId3();
            int languageId4 = frm.getLanguageId4();

            int languageProf1[] = frm.getLanguageProf1();

            String ipAddrStr = request.getRemoteAddr();
            String iplocal = cr.getLocalIp();

            FormFile adhaarfile = frm.getAdhaarfile();
            String hdnadhaarfile = frm.getHdnadhaarfile();
            FormFile panfile = frm.getPanfile();
            String hdnpanfile = frm.getHdnpanfile();

            String add_candidate_file = cr.getMainPath("add_candidate_file");
            String foldername = cr.createFolder(add_candidate_file);
            Date now = new Date();
            String fn = String.valueOf(now.getTime());
            String adhaarfileName = "", panfileName = "";
            boolean adharflag = false, panflag = false;
            if (adhaarfile != null && adhaarfile.getFileSize() > 0) {
                adharflag = true;
                adhaarfileName = cr.uploadFile(0, hdnadhaarfile, adhaarfile, fn + "_2", add_candidate_file, foldername);
            }
            if (panfile != null && panfile.getFileSize() > 0) {
                panflag = true;
                panfileName = cr.uploadFile(0, hdnpanfile, panfile, fn + "_3", add_candidate_file, foldername);
            }

            CandidateRegistrationInfo info = new CandidateRegistrationInfo(onshore, firstname, middlename, lastname, dob, placeofbirth,
                    passport, gender, maritalstatusId, nationalityId, religion, adhaar,
                    panno, code1, contactno1, emailId, addressline1_1, addressline1_2, addressline1_3, pincode, countryId, stateId,
                    cityId, sameAsPermanent, addressline2_1, addressline2_2, addressline2_3, pincode2, countryId2, stateId2, cityId2,
                    assettypeId, positionId, ecurrencyId, expectedsalary, lcurrencyId, lastdrawnsalary, kindId, degreeId, isFresher,
                    languageId1, languageProf1, languageId2, languageId3, languageId4, 1, adhaarfileName, panfileName);

            int cc = 0;
            if (regcandidateId > 0) {
                cc = cr.updateCandidate(info, uId, 2, 0, regcandidateId);
            } else {
                cc = cr.createCandidate(info, uId, 2, 0);
            }
            if (cc > 0) {
                String fname = frm.getFname() != null ? frm.getFname() : "";
                String localFile = frm.getLocalFile();
                if (!"".equals(fname)) {
                    String fnameval[] = fname.split("@#@");
                    String localfname[] = localFile.split("@#@");
                    int len = fnameval.length;
                    Connection conn = null;
                    try {
                        conn = cr.getConnection();
                        for (int i = 0; i < len; i++) {
                            String fileName = cr.saveImage(fnameval[i], add_candidate_file, foldername, cr.getfilename(localfname[i]) + "-" + fn + "_" + i);
                            cr.createResume(conn, cc, fileName, uId, localfname[i]);
                        }
                    } finally {
                        if (conn != null) {
                            conn.close();
                        }
                    }
                }
//                    --------------------------------------- Document--------------------------------------------------------
                cr.insertGovdocumentdetail(cc, uId, info, add_candidate_file, adharflag, panflag);
//                    --------------------------------------- Work Experience --------------------------------------------------------
                ArrayList worklist = new ArrayList();
                if (request.getSession().getAttribute("CANDREGWORKEXP") != null) {
                    worklist = (ArrayList) request.getSession().getAttribute("CANDREGWORKEXP");
                }
                request.getSession().removeAttribute("CANDREGWORKEXP");
                if (worklist.size() > 0) {
                    cr.insertexperiencedetail(worklist, cc, uId);
                }
//                    --------------------------------------- Certification --------------------------------------------------------
                ArrayList certlist = new ArrayList();
                if (request.getSession().getAttribute("CANDREGCERT") != null) {
                    certlist = (ArrayList) request.getSession().getAttribute("CANDREGCERT");
                }
                request.getSession().removeAttribute("CANDREGCERT");
                if (certlist.size() > 0) {
                    cr.inserttrainingCertificate(certlist, cc, uId);
                }
//                    --------------------------------------- Language --------------------------------------------------------
                cr.insertLanguage(info, cc);
//                    -------------------------------------------------------------------------------------------------------------------
                String name = firstname + " " + lastname;
                home.sendRegistrationEmail(name, candidateId, 1, "New Registration", emailId);

                cr.createHistoryAccess(null, uId, ipAddrStr, iplocal, "Data Added", 7, cc);
            }
//            request.setAttribute("CANDSAVEMODEL", "yes");************************************************************
            CandidateRegistrationInfo info1 = cr.getCandidateData(regcandidateId);
            frm.setEmailId(emailId);
            frm.setMaritalstatuses(maritalstatus.getMaritialStatus());
            frm.setCountries(country.getCountrys());
            frm.setCurrencies(currency.getCurrencys());
            frm.setAssettypes(assettype.getAssettypes());
            frm.setLanguages(language.getLanguages());
            frm.setQualificationtypes(qualificationtype.getQualificationTypes());
            frm.setDegrees(degree.getDegree());
            frm.setProficiencies(proficiency.getProficiencies());
            frm.setCoursenames(candidate.getCourseName());
            frm.setApprovedbys(approvedby.getApprovedbys());
            frm.setStates(cr.getCountryStates(-1));
            frm.setStates2(cr.getCountryStates(-1));
            frm.setPositions(candidate.getPositionassettypes(-1));

            if (info1 != null) {
                frm.setOnshore(info1.getOnshore());
                request.setAttribute("RESUMEFILE", info1.getResumeFile());
                frm.setLocalFile(info1.getResumeFile());
                frm.setFirstname(info1.getFirstname());
                frm.setMiddlename(info1.getMiddlename());
                frm.setLastname(info1.getLastname());
                frm.setDob(info1.getDob());
                int age = 0;
                if (!info.getDob().equals("") && info.getDob() != null) {
                    age = cr.calculateAge(info1.getDob());
                }
                frm.setAge(age);

                if (info1.getPassport() > 0) {
                    frm.setPassport(1);
                }
                frm.setPancard(cr.decipher(info1.getPancard()));
                frm.setHdnpanfile(info1.getPanFile());
                frm.setAdhaar(cr.decipher(info1.getAdhaar()));
                frm.setHdnadhaarfile(info1.getAdhaarFile());
                request.setAttribute("ADHAARFILE", info1.getAdhaarFile());
                request.setAttribute("PANFILE", info1.getPanFile());

                frm.setPlaceofbirth(info1.getPlaceofbirth());
                frm.setGender(info1.getGender());
                frm.setMaritalstatusId(info1.getMaritalstatusId());
                frm.setNationalityId(info1.getNationalityId());
                frm.setReligion(info1.getReligion());

                frm.setCode1Id(info1.getCode1());
                frm.setContactno1(decipher(info1.getContactno1()));
                frm.setAddress1line1(info1.getAddressline1_1());
                frm.setAddress1line2(info1.getAddressline1_2());

                frm.setAddress1line3(info1.getAddressline1_3());
                frm.setCountryId(info1.getCountryId());
                if (info1.getCountryId() > 0) {
                    frm.setStates(cr.getCountryStates(info1.getCountryId()));
                } else {
                }
                frm.setStateId(info1.getStateId());
                frm.setCityId(info1.getCityId());
                frm.setCityName(info1.getCity1_name());
                frm.setPinCode(info1.getPincode());

                frm.setSameAsPermanent(info1.getSameAsPermanent());
                if (info1.getSameAsPermanent() > 0) {
                    request.setAttribute("SAMEASADDRESS", info1.getSameAsPermanent());
                } else {
                    frm.setAddress2line1(info1.getAddressline2_1());
                    frm.setAddress2line2(info1.getAddressline2_2());
                    frm.setAddress2line3(info1.getAddressline2_3());
                    frm.setCountryId2(info1.getCountryId2());
                    frm.setStates2(cr.getCountryStates(info1.getCountryId2()));
                    frm.setStateId2(info1.getStateId2());
                    frm.setCityId2(info1.getCityId2());
                    frm.setCityName2(info1.getCity2_name());
                    frm.setPinCode2(info1.getPincode2());
                }
                frm.setAssettypeId(info1.getAssettypeId());
                frm.setPositions(candidate.getPositionassettypes(info1.getAssettypeId()));
                frm.setPositionId(info1.getPositionId());
                frm.setEcurrencyId(info1.getEcurrencyId());
                frm.setExpectedsalary(info1.getExpectedsalary());
                frm.setLcurrencyId(info1.getLcurrencyId());
                frm.setLastdrawnsalary(info1.getLastdrawnsalary());

                frm.setKindId(info1.getKindId());
                frm.setDegreeId(info1.getDegreeId());
                frm.setIsFresher(info1.getIsFresher());
                //                frm.setLanguageProf1(cr.parseCommaDelimInt(info.getProficiencytypes()));
                request.setAttribute("LANGPROFTYPE", info.getProficiencytypes());
                ArrayList langlist = cr.getLanguage(regcandidateId);
                int lc = 0;
                if (langlist.size() > 0) {
                    CandidateRegistrationInfo lang_info = null;
                    for (int i = 0; i < langlist.size(); i++) {
                        lang_info = (CandidateRegistrationInfo) langlist.get(i);
                        if (lang_info != null) {
                            if (lc == 0) {
                                frm.setLanguageId2(lang_info.getDdlValue());
                                lc++;
                            } else if (lc == 1) {
                                frm.setLanguageId3(lang_info.getDdlValue());
                                lc++;
                            } else if (lc == 2) {
                                frm.setLanguageId4(lang_info.getDdlValue());
                                lc++;
                            }
                        }
                    }
                }
                request.setAttribute("ISFRESHER", info1.getIsFresher());
                if (info1.getIsFresher() <= 0) {
                    ArrayList explist = cr.getExperiencDetailsById(regcandidateId);
                    request.getSession().setAttribute("CANDREGWORKEXP", explist);
                    frm.setExpCount(explist.size());
                }
                ArrayList certlist = cr.getCertificatesById(regcandidateId);
                request.getSession().setAttribute("CANDREGCERT", certlist);
                frm.setCertCount(certlist.size());
            }

            request.setAttribute("CANDSAVEMODEL", "yes");
//            }
            return mapping.findForward("display");
        } else {
            print(this, "else block.");
            frm.setEmailId(emailId);
            request.getSession().removeAttribute("CANDREGCERT");
            request.getSession().removeAttribute("CANDREGWORKEXP");
            frm.setMaritalstatuses(maritalstatus.getMaritialStatus());
            frm.setCountries(country.getCountrys());
            frm.setStates(cr.getCountryStates(-1));
            frm.setStates2(cr.getCountryStates(-1));
            frm.setCurrencies(currency.getCurrencys());
            frm.setAssettypes(assettype.getAssettypes());
            frm.setPositions(candidate.getPositionassettypes(-1));
            frm.setLanguages(language.getLanguages());
            frm.setQualificationtypes(qualificationtype.getQualificationTypes());
            frm.setDegrees(degree.getDegree());
            frm.setProficiencies(proficiency.getProficiencies());
            frm.setCoursenames(candidate.getCourseName());
            frm.setApprovedbys(approvedby.getApprovedbys());
            frm.setOnshore(1);
        }
        return mapping.findForward("display");
    }
}
