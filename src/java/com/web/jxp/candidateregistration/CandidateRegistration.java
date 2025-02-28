package com.web.jxp.candidateregistration;

import com.web.jxp.base.Base;
import com.web.jxp.base.Template;
import static com.web.jxp.common.Common.print;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.logging.Logger;

public class CandidateRegistration extends Base {

    private static final Logger logger = Logger.getLogger(Class.class.getName());
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    public String generateotp() {
        String chars = "0123456789";
        int numberOfCodes = 0;
        String code = "";
        while (numberOfCodes < 6) {
            char c = chars.charAt((int) (Math.random() * chars.length()));
            code += " " + c;
            numberOfCodes++;
        }
        return code;
    }

    public static String getDateAfter(String date, int type, int no, String sformatv, String endformatv) {
        String dt = "";
        try {
            Calendar cal = Calendar.getInstance();
            SimpleDateFormat sformat = new SimpleDateFormat(sformatv);
            SimpleDateFormat endformat = new SimpleDateFormat(endformatv);
            long l1 = sformat.parse(date).getTime();
            cal.setTimeInMillis(l1);
            switch (type) {
                case 1:
                    cal.add(Calendar.DATE, no);
                    break;
                case 2:
                    cal.add(Calendar.MONTH, no);
                    break;
                case 3:
                    cal.add(Calendar.YEAR, no);
                    break;
                case 4:
                    cal.add(Calendar.HOUR, no);
                    break;
                case 5:
                    cal.add(Calendar.MINUTE, no);
                    break;
                default:
                    break;
            }
            dt = endformat.format(cal.getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dt;
    }

    public int insertOTP(String emailId, String otp, String date) {
        int homeId = 0;
        try {
            String query = "INSERT INTO t_otp (s_emailid, s_otpvalue, d_expirydate, ts_regdate) VALUES (?, ?, ?, ?)";

            conn = getConnection();
            pstmt = conn.prepareStatement(query, com.mysql.jdbc.Statement.RETURN_GENERATED_KEYS);
            int scc = 0;
            pstmt.setString(++scc, (emailId));
            pstmt.setString(++scc, otp);
            pstmt.setString(++scc, date);
            pstmt.setString(++scc, currDate1());
            //print(this,"createHome :: " + pstmt.toString());
            pstmt.executeUpdate();
            rs = pstmt.getGeneratedKeys();
            while (rs.next()) {
                homeId = rs.getInt(1);
            }
        } catch (Exception exception) {
            print(this, "createHome :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return homeId;
    }

    public String getOTPMessage(String otp, String htmlFile) {
        String adminpage = getMainPath("template_path") + htmlFile;
        HashMap hashmap = new HashMap();
        Template template = new Template(adminpage);
        hashmap.put("OTP", otp);
        return template.patch(hashmap);
    }

    public int checkOTP(String emailId, String otp) {
        int ck = 0;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT i_otpid FROM t_otp WHERE s_emailid =? AND s_otpvalue =? AND d_expirydate >now() ");

        String query = (sb.toString()).intern();
        sb.setLength(0);
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            int scc = 0;
            pstmt.setString(++scc, emailId);
            pstmt.setString(++scc, otp);
            print(this, "checkOTP :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ck = 1;
            }
        } catch (Exception exception) {
            print(this, "checkOTP :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return ck;
    }

    public CandidateRegistrationInfo getCandidateId(String emailId) {
        CandidateRegistrationInfo info = null;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT i_candidateid, i_vflag, i_pass, i_draftflag FROM t_candidate WHERE s_email =?");

        String query = (sb.toString()).intern();
        sb.setLength(0);
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            int scc = 0;
            pstmt.setString(++scc, emailId);
            print(this, "getCandidateId :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            int candidateId = 0, onlineflag = 0, vflag = 0, pass = 0, draftflag = 0;
            while (rs.next()) {
                candidateId = rs.getInt(1);
                vflag = rs.getInt(2);
                pass = rs.getInt(3);
                draftflag = rs.getInt(4);
                if ((vflag == 3 || vflag == 4) && pass == 2) {
                    onlineflag = 1;
                } else {
                    onlineflag = 2;
                }
                info = new CandidateRegistrationInfo(candidateId, onlineflag, emailId, draftflag);
            }
        } catch (Exception exception) {
            print(this, "getCandidateId :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return info;
    }

    public int createCandidate(CandidateRegistrationInfo info, int userId, int type, int draft) {
        int candidateId = 0;
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("INSERT INTO t_candidate (s_firstname, s_middlename, s_lastname, d_dob, s_placeofbirth, s_gender, i_maritialstatusid, ");
            sb.append("i_nationalityid, s_religion, s_code1, s_contactno1, s_email, s_address1line1, s_address1line2, s_address1line3, ");
            sb.append("i_countryid, i_stateid, i_cityid, s_pincode, i_sameasprimaryadd, s_address2line1, s_address2line2, s_address2line3, ");
            sb.append("i_countryid2, i_stateid2, i_cityid2, s_pincode2, i_assettypeid, i_positionid2, i_currencyid, i_expectedsalary, ");
            sb.append("i_drawncurrencyid, i_drawnsalary, i_status, i_userid, ts_regdate, ts_moddate, i_online, i_fresher, i_draftflag, ");
            sb.append("i_onshore) ");
            sb.append("VALUES (?, ?, ?, ?, ?,  ?, ?, ?, ?, ?,  ?, ?, ?, ?, ?,  ?, ?, ?, ?, ?,  ?, ?, ?, ?, ?,  ?, ?, ?, ?, ?, ");
            sb.append("?, ?, ?, ?, ?,  ?, ?, ?, ?, ?,  ?)");
            String candidatequery = sb.toString().intern();
            sb.setLength(0);
            int scc = 0;
            conn = getConnection();
            pstmt = conn.prepareStatement(candidatequery, PreparedStatement.RETURN_GENERATED_KEYS);
            pstmt.setString(++scc, info.getFirstname());
            pstmt.setString(++scc, info.getMiddlename());
            pstmt.setString(++scc, info.getLastname());
            pstmt.setString(++scc, changeDate1(info.getDob()));
            pstmt.setString(++scc, info.getPlaceofbirth());

            pstmt.setString(++scc, info.getGender());
            pstmt.setInt(++scc, info.getMaritalstatusId());
            pstmt.setInt(++scc, info.getNationalityId());
            pstmt.setString(++scc, info.getReligion());

            pstmt.setString(++scc, info.getCode1());
            pstmt.setString(++scc, cipher(info.getContactno1()));
            pstmt.setString(++scc, info.getEmailId());

            pstmt.setString(++scc, info.getAddressline1_1());
            pstmt.setString(++scc, info.getAddressline1_2());
            pstmt.setString(++scc, info.getAddressline1_3());
            pstmt.setInt(++scc, info.getCountryId());
            pstmt.setInt(++scc, info.getStateId());
            pstmt.setInt(++scc, info.getCityId());
            pstmt.setString(++scc, info.getPincode());

            pstmt.setInt(++scc, info.getSameAsPermanent());

            pstmt.setString(++scc, info.getAddressline2_1());
            pstmt.setString(++scc, info.getAddressline2_2());
            pstmt.setString(++scc, info.getAddressline2_3());
            pstmt.setInt(++scc, info.getCountryId2());
            pstmt.setInt(++scc, info.getStateId2());
            pstmt.setInt(++scc, info.getCityId2());
            pstmt.setString(++scc, info.getPincode2());

            pstmt.setInt(++scc, info.getAssettypeId());
            pstmt.setInt(++scc, info.getPositionId());

            pstmt.setInt(++scc, info.getEcurrencyId());
            pstmt.setInt(++scc, info.getExpectedsalary());
            pstmt.setInt(++scc, info.getLcurrencyId());
            pstmt.setInt(++scc, info.getLastdrawnsalary());

            pstmt.setInt(++scc, info.getStatus());
            pstmt.setInt(++scc, userId);
            pstmt.setString(++scc, currDate1());
            pstmt.setString(++scc, currDate1());
            pstmt.setInt(++scc, type);
            pstmt.setInt(++scc, info.getIsFresher());

            pstmt.setInt(++scc, draft);
            pstmt.setInt(++scc, info.getOnshore());
            print(this, "createCandidate :: " + pstmt.toString());
            pstmt.executeUpdate();
            rs = pstmt.getGeneratedKeys();
            while (rs.next()) {
                candidateId = rs.getInt(1);
            }
            if (candidateId > 0) {
                insertEducation(conn, info.getKindId(), info.getDegreeId(), candidateId);
            }
            rs.close();
            pstmt.close();
        } catch (Exception exception) {
            print(this, "createCandidate :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return candidateId;
    }

    public int updateCandidate(CandidateRegistrationInfo info, int userId, int type, int draft, int Id) {
        int cc = 0;
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("UPDATE t_candidate SET s_firstname =?, s_middlename =?, s_lastname =?, d_dob =?, s_placeofbirth =?, s_gender =?, i_maritialstatusid =?, ");
            sb.append("i_nationalityid =?, s_religion =?, s_code1 =?, s_contactno1 =?, s_email =?, s_address1line1 =?, s_address1line2 =?, s_address1line3 =?, ");
            sb.append("i_countryid =?, i_stateid =?, i_cityid =?, s_pincode =?, i_sameasprimaryadd =?, s_address2line1 =?, s_address2line2 =?, s_address2line3 =?, ");
            sb.append("i_countryid2 =?, i_stateid2 =?, i_cityid2 =?, s_pincode2 =?, i_assettypeid =?, i_positionid2 =?, i_currencyid =?, i_expectedsalary =?, ");
            sb.append("i_drawncurrencyid =?, i_drawnsalary =?, i_status =?, i_userid =?, ts_regdate =?, ts_moddate =?, i_online =?, i_fresher =?, i_draftflag =?, ");
            sb.append("i_onshore =? WHERE i_candidateid = ?");
            String candidatequery = sb.toString().intern();
            sb.setLength(0);
            int scc = 0;
            conn = getConnection();
            pstmt = conn.prepareStatement(candidatequery, PreparedStatement.RETURN_GENERATED_KEYS);
            pstmt.setString(++scc, info.getFirstname());
            pstmt.setString(++scc, info.getMiddlename());
            pstmt.setString(++scc, info.getLastname());
            pstmt.setString(++scc, changeDate1(info.getDob()));
            pstmt.setString(++scc, info.getPlaceofbirth());

            pstmt.setString(++scc, info.getGender());
            pstmt.setInt(++scc, info.getMaritalstatusId());
            pstmt.setInt(++scc, info.getNationalityId());
            pstmt.setString(++scc, info.getReligion());

            pstmt.setString(++scc, info.getCode1());
            pstmt.setString(++scc, cipher(info.getContactno1()));
            pstmt.setString(++scc, info.getEmailId());

            pstmt.setString(++scc, info.getAddressline1_1());
            pstmt.setString(++scc, info.getAddressline1_2());
            pstmt.setString(++scc, info.getAddressline1_3());
            pstmt.setInt(++scc, info.getCountryId());
            pstmt.setInt(++scc, info.getStateId());
            pstmt.setInt(++scc, info.getCityId());
            pstmt.setString(++scc, info.getPincode());

            pstmt.setInt(++scc, info.getSameAsPermanent());

            pstmt.setString(++scc, info.getAddressline2_1());
            pstmt.setString(++scc, info.getAddressline2_2());
            pstmt.setString(++scc, info.getAddressline2_3());
            pstmt.setInt(++scc, info.getCountryId2());
            pstmt.setInt(++scc, info.getStateId2());
            pstmt.setInt(++scc, info.getCityId2());
            pstmt.setString(++scc, info.getPincode2());

            pstmt.setInt(++scc, info.getAssettypeId());
            pstmt.setInt(++scc, info.getPositionId());

            pstmt.setInt(++scc, info.getEcurrencyId());
            pstmt.setInt(++scc, info.getExpectedsalary());
            pstmt.setInt(++scc, info.getLcurrencyId());
            pstmt.setInt(++scc, info.getLastdrawnsalary());

            pstmt.setInt(++scc, info.getStatus());
            pstmt.setInt(++scc, userId);
            pstmt.setString(++scc, currDate1());
            pstmt.setString(++scc, currDate1());
            pstmt.setInt(++scc, type);
            pstmt.setInt(++scc, info.getIsFresher());

            pstmt.setInt(++scc, draft);
            pstmt.setInt(++scc, info.getOnshore());
            pstmt.setInt(++scc, Id);
            print(this, "updateCandidate :: " + pstmt.toString());
            cc = pstmt.executeUpdate();
            if (cc > 0) {
                insertEducation(conn, info.getKindId(), info.getDegreeId(), Id);
            }
            rs.close();
            pstmt.close();
        } catch (Exception exception) {
            print(this, "updateCandidate :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return cc;
    }

    public int createResume(Connection conn, int candidateId, String filename, int userId, String localFile) {
        int cc = 0;
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("INSERT INTO t_candidatefiles ");
            sb.append("(i_candidateid, s_name, i_userid,i_status, ts_regdate, ts_moddate, s_localname) ");
            sb.append("VALUES (?, ?, ?, ?, ?, ?, ?)");
            String query = (sb.toString()).intern();
            sb.setLength(0);
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, candidateId);
            pstmt.setString(2, filename);
            pstmt.setInt(3, userId);
            pstmt.setInt(4, 1);
            pstmt.setString(5, currDate1());
            pstmt.setString(6, currDate1());
            pstmt.setString(7, localFile);
            print(this, "createPic :: " + pstmt.toString());
            cc = pstmt.executeUpdate();
        } catch (Exception exception) {
            print(this, "createPic :: " + exception.getMessage());
        } finally {
            close(null, pstmt, rs);
        }
        return cc;
    }

    public int insertEducation(Connection conn, int kindId, int degreeId, int candidateId) {
        int educationDetailId = 0;
        String deleteQuery = "DELETE FROM t_eduqual WHERE i_candidateid = ?";
        String insertQuery = "INSERT INTO t_eduqual "
                + "(i_qualificationtypeid, i_degreeid, i_status, i_candidateid, ts_regdate, ts_moddate) "
                + "VALUES (?, ?, ?, ?, ?, ?)";

        try {
            pstmt = conn.prepareStatement(deleteQuery);
            pstmt.setInt(1, candidateId);
            pstmt.executeUpdate();
            pstmt.close();

            pstmt = conn.prepareStatement(insertQuery);
            pstmt.setInt(1, kindId);
            pstmt.setInt(2, degreeId);
            pstmt.setInt(3, 1);
            pstmt.setInt(4, candidateId);
            pstmt.setString(5, currDate1());
            pstmt.setString(6, currDate1());

            pstmt.executeUpdate();

        } catch (Exception exception) {
            print(this, "inserteducation :: " + exception.getMessage());
        } finally {
            close(null, pstmt, null);
        }
        return educationDetailId;
    }

    public ArrayList getLanguage(int Id) {
        ArrayList list = new ArrayList();
        if (Id > 0) {
            String query = "SELECT i_languageid, i_proficiencytypeid FROM t_candlang WHERE i_candidateid =? AND i_languageid !=1 ORDER BY i_candlangid";
            try {
                conn = getConnection();
                pstmt = conn.prepareStatement(query);
                pstmt.setInt(1, Id);
                logger.info("getLanguage :: " + pstmt.toString());
                rs = pstmt.executeQuery();
                String proficiencytypeid;
                int languageid;
                while (rs.next()) {
                    languageid = rs.getInt(1);
                    proficiencytypeid = rs.getString(2) != null ? rs.getString(2) : "";
                    list.add(new CandidateRegistrationInfo(languageid, proficiencytypeid));
                }
                rs.close();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                close(conn, pstmt, rs);
            }
        }
        return list;
    }

    public int insertLanguage(CandidateRegistrationInfo info, int candidateId) {
        int candLangId = 0;
        String deleteQuery = "DELETE FROM t_candlang WHERE i_candidateid = ?";
        String insertQuery = "INSERT INTO t_candlang "
                + "(i_candidateid, i_languageid, i_proficiencytypeid, i_status, ts_regdate, ts_moddate, i_proficiencyid) "
                + "VALUES (?, ?, ?, ?, ?,  ?, ?)";
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(deleteQuery);
            pstmt.setInt(1, candidateId);
            pstmt.executeUpdate();
            pstmt.close();
            pstmt = conn.prepareStatement(insertQuery);
            insertLanguageRecord(pstmt, candidateId, info.getLanguageId1(), info.getLanguageProf1(), info.getStatus());
            int arr[] = new int[0];
            insertLanguageRecord(pstmt, candidateId, info.getLanguageId2(), arr, info.getStatus());
            insertLanguageRecord(pstmt, candidateId, info.getLanguageId3(), arr, info.getStatus());
            insertLanguageRecord(pstmt, candidateId, info.getLanguageId4(), arr, info.getStatus());
        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            close(conn, pstmt, rs);
        }
        return candLangId;
    }

    private void insertLanguageRecord(PreparedStatement pstmt, int candidateId, int languageId, int[] proficiency, int status) throws SQLException {
        if (languageId > 0) {
            pstmt.setInt(1, candidateId);
            pstmt.setInt(2, languageId);
            pstmt.setString(3, makeCommaDelimInt(proficiency));
            pstmt.setInt(4, status);
            pstmt.setString(5, currDate1());
            pstmt.setString(6, currDate1());
            pstmt.setInt(7, 3);
            pstmt.executeUpdate();
            print(this, pstmt.toString());
        }
    }

    public int insertexperiencedetail(ArrayList<CandidateRegistrationInfo> list, int candidateId, int uId) {
        int experiencedetailId = 0;

        if (list == null || list.isEmpty()) {
            return experiencedetailId;
        }

        String deleteQuery = "DELETE FROM t_workexperience WHERE i_candidateid = ?";
        String insertQuery = "INSERT INTO t_workexperience "
                + "(s_companyname, i_assettypeid, s_assetname, i_positionid, d_workstartdate, d_workenddate, "
                + "i_currentworkingstatus, i_status, i_userid, i_candidateid, ts_regdate, ts_moddate, i_pastocsemp) "
                + "VALUES (?, ?, ?, ?, ?,  ?, ?, ?, ?, ?,  ?, ?, ?)";

        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(deleteQuery);
            pstmt.setInt(1, candidateId);
            pstmt.executeUpdate();
            pstmt.close();

            pstmt = conn.prepareStatement(insertQuery);
            for (CandidateRegistrationInfo info : list) {
                if (info != null) {
                    int scc = 0;
                    pstmt.setString(++scc, info.getCompanyname());
                    pstmt.setInt(++scc, info.getExpassettypeId());
                    pstmt.setString(++scc, info.getAssetname());
                    pstmt.setInt(++scc, info.getExppositionId());
                    pstmt.setString(++scc, changeDate1(info.getWorkstartdate()));
                    pstmt.setString(++scc, changeDate1(info.getWorkenddate()));
                    pstmt.setInt(++scc, info.getCurrentworkingstatus());
                    pstmt.setInt(++scc, info.getStatus());
                    pstmt.setInt(++scc, uId);
                    pstmt.setInt(++scc, candidateId);
                    pstmt.setString(++scc, currDate1());
                    pstmt.setString(++scc, currDate1());
                    pstmt.setInt(++scc, info.getPastOCSemp());
                    pstmt.executeUpdate();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return experiencedetailId;
    }

    public ArrayList getExperiencDetailsById(int Id) {
        ArrayList list = new ArrayList();
        if (Id > 0) {
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT s_companyname, t_workexperience.i_assettypeid, s_assetname, t_workexperience.i_positionid, DATE_FORMAT(d_workstartdate, '%d-%b-%Y'), ");
            sb.append("DATE_FORMAT(d_workenddate, '%d-%b-%Y'), i_currentworkingstatus, i_pastocsemp, t_assettype.s_name, t_position.s_name FROM t_workexperience ");
            sb.append("LEFT JOIN t_assettype ON (t_assettype.i_assettypeid = t_workexperience.i_assettypeid)");
            sb.append("LEFT JOIN t_position  ON (t_position .i_positionid = t_workexperience.i_positionid)");
            sb.append("WHERE i_candidateid =? ");
            String query = sb.toString().intern();
            try {
                conn = getConnection();
                pstmt = conn.prepareStatement(query);
                pstmt.setInt(1, Id);
                logger.info("getExperiencDetail :: " + pstmt.toString());
                rs = pstmt.executeQuery();
                String companyname, assetname, workstartdate, workenddate, assettype, position;
                int assettypeid, positionid, currentworkingstatus, pastocsemp, scc = 0;
                while (rs.next()) {
                    scc = 0;
                    companyname = rs.getString(++scc) != null ? rs.getString(scc) : "";
                    assettypeid = rs.getInt(++scc);
                    assetname = rs.getString(++scc) != null ? rs.getString(scc) : "";
                    positionid = rs.getInt(++scc);
                    workstartdate = rs.getString(++scc) != null ? rs.getString(scc) : "";
                    workenddate = rs.getString(++scc) != null ? rs.getString(scc) : "";
                    currentworkingstatus = rs.getInt(++scc);
                    pastocsemp = rs.getInt(++scc);
                    assettype = rs.getString(++scc) != null ? rs.getString(scc) : "";
                    position = rs.getString(++scc) != null ? rs.getString(scc) : "";
                    list.add(new CandidateRegistrationInfo(companyname, assetname, workstartdate, currentworkingstatus, pastocsemp,
                            assettypeid, assettype, positionid, position, workenddate));
                }
                rs.close();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                close(conn, pstmt, rs);
            }
        }
        return list;
    }

    public int inserttrainingCertificate(ArrayList<CandidateRegistrationInfo> list, int candidateId, int uId) {
        int trainingandcertId = 0;

        if (list == null || list.isEmpty()) {
            return trainingandcertId;
        }
        String selQuery = "SELECT s_filename FROM t_trainingandcert WHERE i_candidateid = ?";
        String deleteQuery = "DELETE FROM t_trainingandcert WHERE i_candidateid = ?";
        String insertQuery = "INSERT INTO t_trainingandcert "
                + "(i_coursenameid, d_dateofissue, d_expirydate, i_approvedbyid, s_filename, "
                + "i_status, i_candidateid, ts_regdate, ts_moddate, i_noexpiry) "
                + "VALUES (?, ?, ?, ?, ?,  ?, ?, ?, ?, ?)";

        try {
            String add_candidate_file = getMainPath("add_candidate_file");
            conn = getConnection();
            pstmt = conn.prepareStatement(selQuery);
            pstmt.setInt(1, candidateId);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                String filename = rs.getString(1) != null ? rs.getString(1) : "";
                if (!filename.equals("")) {
                    File f = new File(add_candidate_file + filename);
                    if (f.exists()) {
                        f.delete();
                    }
                }
            }
            pstmt.close();

            pstmt = conn.prepareStatement(deleteQuery);
            pstmt.setInt(1, candidateId);
            pstmt.executeUpdate();
            pstmt.close();

            String foldername = createFolder(add_candidate_file);
            Date now = new Date();
            String fn = String.valueOf(now.getTime());
            String fileName = "";

            pstmt = conn.prepareStatement(insertQuery);
            for (int i = 0; i < list.size(); i++) {
                CandidateRegistrationInfo info = list.get(i);
                if (info != null) {
                    fileName = "";
                    fileName = saveImage(info.getTempfname(), add_candidate_file, foldername, getfilename(info.getTemplocalFile()) + "-" + fn + "_" + i);
                    int scc = 0;
                    pstmt.setInt(++scc, info.getCoursenameId());
                    pstmt.setString(++scc, changeDate1(info.getDateofissue()));
                    pstmt.setString(++scc, changeDate1(info.getDateofexpiry()));
                    pstmt.setInt(++scc, info.getApprovedbyId());
                    pstmt.setString(++scc, fileName);
                    pstmt.setInt(++scc, info.getStatus());
                    pstmt.setInt(++scc, candidateId);
                    pstmt.setString(++scc, currDate1());
                    pstmt.setString(++scc, currDate1());
                    pstmt.setInt(++scc, info.getIsexpiry());

                    pstmt.executeUpdate();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return trainingandcertId;
    }

    public ArrayList getCertificatesById(int Id) {
        ArrayList list = new ArrayList();
        if (Id > 0) {
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT t_trainingandcert.i_coursenameid, DATE_FORMAT(d_dateofissue, '%d-%b-%Y'), DATE_FORMAT(d_expirydate, '%d-%b-%Y'), ");
            sb.append("t_trainingandcert.i_approvedbyid, s_filename, i_noexpiry, t_coursename.s_name, t_approvedby.s_name FROM t_trainingandcert ");
            sb.append("LEFT JOIN t_coursename ON (t_coursename.i_coursenameid = t_trainingandcert. i_coursenameid) ");
            sb.append("LEFT JOIN t_approvedby ON (t_approvedby.i_approvedbyid = t_trainingandcert.i_approvedbyid) ");
            sb.append("WHERE i_candidateid =? ");
            String query = sb.toString().intern();
            sb.setLength(0);
            try {
                conn = getConnection();
                pstmt = conn.prepareStatement(query);
                pstmt.setInt(1, Id);
                logger.info("getCertificatesById :: " + pstmt.toString());
                rs = pstmt.executeQuery();
                String dateofissue, expirydate, filename, courseName, approvedby;
                int coursenameid, approvedbyid, noexpiry, scc = 0;
                while (rs.next()) {
                    scc = 0;
                    coursenameid = rs.getInt(++scc);
                    dateofissue = rs.getString(++scc) != null ? rs.getString(scc) : "";
                    expirydate = rs.getString(++scc) != null ? rs.getString(scc) : "";
                    approvedbyid = rs.getInt(++scc);
                    filename = rs.getString(++scc) != null ? rs.getString(scc) : "";
                    noexpiry = rs.getInt(++scc);
                    courseName = rs.getString(++scc) != null ? rs.getString(scc) : "";
                    approvedby = rs.getString(++scc) != null ? rs.getString(scc) : "";
                    list.add(new CandidateRegistrationInfo(noexpiry, coursenameid, courseName, dateofissue, expirydate, approvedbyid, approvedby, "", filename));
                }
                rs.close();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                close(conn, pstmt, rs);
            }
        }
        return list;
    }

    public int insertGovdocumentdetail(int candidateId, int userId, CandidateRegistrationInfo info, String add_candidate_file, boolean aflag,
            boolean pflag) {
        int govdocumentId = 0;
        String insertQuery = "INSERT INTO t_govdoc "
                + "(i_candidateid, i_doctypeid, s_docno, s_filename, i_status, i_userid, ts_regdate, ts_moddate) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            conn = getConnection();
            if (aflag) {
                String selQuery = "SELECT s_filename FROM t_govdoc WHERE i_candidateid = ? AND i_doctypeid =1";
                pstmt = conn.prepareStatement(selQuery);
                pstmt.setInt(1, candidateId);
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    String filename = rs.getString(1) != null ? rs.getString(1) : "";
                    if (!filename.isEmpty()) {
                        File f = new File(add_candidate_file + filename);
                        if (f.exists()) {
                            f.delete();
                        }
                    }
                }
                pstmt.close();

                String deleteQuery = "DELETE FROM t_govdoc WHERE i_candidateid = ? AND i_doctypeid =1";
                pstmt = conn.prepareStatement(deleteQuery);
                pstmt.setInt(1, candidateId);
                pstmt.executeUpdate();
                pstmt.close();

                if (!info.getAdhaar().isEmpty()) {
                    pstmt = conn.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS);
                    pstmt.setInt(1, candidateId);
                    pstmt.setInt(2, 1);
                    pstmt.setString(3, cipher(info.getAdhaar()));
                    pstmt.setString(4, info.getAdhaarFile());
                    pstmt.setInt(5, 1);
                    pstmt.setInt(6, userId);
                    pstmt.setString(7, currDate1());
                    pstmt.setString(8, currDate1());
                    govdocumentId += pstmt.executeUpdate();
                    pstmt.close();
                }
            }

            if (pflag) {
                String selQuery = "SELECT s_filename FROM t_govdoc WHERE i_candidateid = ? AND i_doctypeid =5";
                pstmt = conn.prepareStatement(selQuery);
                pstmt.setInt(1, candidateId);
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    String filename = rs.getString(1) != null ? rs.getString(1) : "";
                    if (!filename.isEmpty()) {
                        File f = new File(add_candidate_file + filename);
                        if (f.exists()) {
                            f.delete();
                        }
                    }
                }
                pstmt.close();

                String deleteQuery = "DELETE FROM t_govdoc WHERE i_candidateid = ? AND i_doctypeid =5";
                pstmt = conn.prepareStatement(deleteQuery);
                pstmt.setInt(1, candidateId);
                pstmt.executeUpdate();
                pstmt.close();

                if (!info.getPancard().isEmpty()) {
                    pstmt = conn.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS);
                    pstmt.setInt(1, candidateId);
                    pstmt.setInt(2, 5);
                    pstmt.setString(3, cipher(info.getPancard()));
                    pstmt.setString(4, info.getPanFile());
                    pstmt.setInt(5, 1);
                    pstmt.setInt(6, userId);
                    pstmt.setString(7, currDate1());
                    pstmt.setString(8, currDate1());
                    govdocumentId += pstmt.executeUpdate();
                    pstmt.close();
                }
            }

            if (info.getPassport() > 0) {
                String deleteQuery = "DELETE FROM t_govdoc WHERE i_candidateid = ? AND i_doctypeid =2";
                pstmt = conn.prepareStatement(deleteQuery);
                pstmt.setInt(1, candidateId);
                pstmt.executeUpdate();
                pstmt.close();
                if (info.getPassport() == 1) {
                    String passportQuery = "INSERT INTO t_govdoc "
                            + "(i_candidateid, i_doctypeid, i_status, i_userid, ts_regdate, ts_moddate) "
                            + "VALUES (?, ?, ?, ?, ?, ?)";
                    pstmt = conn.prepareStatement(passportQuery, Statement.RETURN_GENERATED_KEYS);
                    pstmt.setInt(1, candidateId);
                    pstmt.setInt(2, 2);
                    pstmt.setInt(3, 1);
                    pstmt.setInt(4, userId);
                    pstmt.setString(5, currDate1());
                    pstmt.setString(6, currDate1());
                    govdocumentId += pstmt.executeUpdate();
                    pstmt.close();
                }
            }
        } catch (Exception exception) {
            print(this, "insertGovdocumentdetail :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return govdocumentId;
    }

    public CandidateRegistrationInfo getCandidateData(int Id) {
        CandidateRegistrationInfo info = null;
        if (Id > 0) {
            try {
                StringBuilder sb = new StringBuilder();
                sb.append("SELECT c.s_firstname, c.s_middlename, c.s_lastname, DATE_FORMAT(c.d_dob, '%d-%b-%Y') AS formatted_dob, c.s_placeofbirth, ");
                sb.append("c.s_gender, c.i_maritialstatusid, c.i_nationalityid, c.s_religion, c.s_code1, c.s_contactno1, c.s_email, c.s_address1line1, ");
                sb.append("c.s_address1line2, c.s_address1line3, c.i_countryid, c.i_stateid, c.i_cityid, city1.s_name AS city1_name, c.s_pincode, ");
                sb.append("c.i_sameasprimaryadd, c.s_address2line1, c.s_address2line2, c.s_address2line3, c.i_countryid2, c.i_stateid2, c.i_cityid2, ");
                sb.append("city2.s_name AS city2_name, c.s_pincode2, c.i_assettypeid, c.i_positionid2, c.i_currencyid, c.i_expectedsalary, ");
                sb.append("c.i_drawncurrencyid, c.i_drawnsalary, c.i_online, c.i_fresher, c.i_onshore, t_eduqual.i_qualificationtypeid, ");
                sb.append("t_eduqual.i_degreeid, passdoc.i_govid, adoc.s_docno, adoc.s_filename, pandoc.s_docno, pandoc.s_filename, cfile.s_name, ");
                sb.append("lang.i_proficiencytypeid FROM t_candidate AS c ");
                sb.append("LEFT JOIN t_city AS city1 ON (c.i_cityid = city1.i_cityid) ");
                sb.append("LEFT JOIN t_city AS city2 ON (c.i_cityid2 = city2.i_cityid) ");
                sb.append("LEFT JOIN t_eduqual ON (c.i_candidateid = t_eduqual.i_candidateid) ");
                sb.append("LEFT JOIN t_govdoc AS adoc ON (c.i_candidateid = adoc.i_candidateid AND adoc.i_doctypeid =1) ");
                sb.append("LEFT JOIN t_govdoc AS passdoc ON (c.i_candidateid = passdoc.i_candidateid AND passdoc.i_doctypeid =2) ");
                sb.append("LEFT JOIN t_govdoc AS pandoc ON (c.i_candidateid = pandoc.i_candidateid AND pandoc.i_doctypeid =5) ");
                sb.append("LEFT JOIN t_candidatefiles AS cfile ON (c.i_candidateid = cfile.i_candidateid) ");
                sb.append("LEFT JOIN t_candlang AS lang ON (c.i_candidateid = lang.i_candidateid AND lang.i_languageid =1) ");
                sb.append("WHERE c.i_candidateid =? AND c.i_draftflag >0");
                String candidatequery = sb.toString().intern();
                sb.setLength(0);
                int scc = 0;
                conn = getConnection();
                pstmt = conn.prepareStatement(candidatequery);
                pstmt.setInt(1, Id);
                print(this, "CandidateRegistrationInfo :: " + pstmt.toString());
                rs = pstmt.executeQuery();
                String firstname, middlename, lastname, dob, placeofbirth, gender, religion, code1, contactno1, email, address1line1,
                        address1line2, address1line3, city1_name, pincode, address2line1, address2line2, address2line3, city2_name,
                        pincode2, adhaarNo, adhaarFile, panNo, panFile, resumeFile, proficiencytypes;
                int maritalstatusid, nationalityid, countryid, stateid, cityid, sameasprimaryadd, countryid2, stateid2, cityid2,
                        assettypeid, positionid2, currencyid, expectedsalary, drawncurrencyid, drawnsalary, online, fresher, onshore,
                        kindId, degreeId, ispassport;

                if (rs.next()) {
                    firstname = rs.getString(++scc) != null ? rs.getString(scc) : "";
                    middlename = rs.getString(++scc) != null ? rs.getString(scc) : "";
                    lastname = rs.getString(++scc) != null ? rs.getString(scc) : "";
                    dob = rs.getString(++scc) != null ? rs.getString(scc) : "";
                    placeofbirth = rs.getString(++scc) != null ? rs.getString(scc) : "";

                    gender = rs.getString(++scc) != null ? rs.getString(scc) : "";
                    maritalstatusid = rs.getInt(++scc);
                    nationalityid = rs.getInt(++scc);
                    religion = rs.getString(++scc) != null ? rs.getString(scc) : "";
                    code1 = rs.getString(++scc) != null ? rs.getString(scc) : "";

                    contactno1 = rs.getString(++scc) != null ? rs.getString(scc) : "";
                    email = rs.getString(++scc) != null ? rs.getString(scc) : "";
                    address1line1 = rs.getString(++scc) != null ? rs.getString(scc) : "";
                    address1line2 = rs.getString(++scc) != null ? rs.getString(scc) : "";
                    address1line3 = rs.getString(++scc) != null ? rs.getString(scc) : "";

                    countryid = rs.getInt(++scc);
                    stateid = rs.getInt(++scc);
                    cityid = rs.getInt(++scc);
                    city1_name = rs.getString(++scc) != null ? rs.getString(scc) : "";
                    pincode = rs.getString(++scc) != null ? rs.getString(scc) : "";

                    sameasprimaryadd = rs.getInt(++scc);
                    address2line1 = rs.getString(++scc) != null ? rs.getString(scc) : "";
                    address2line2 = rs.getString(++scc) != null ? rs.getString(scc) : "";
                    address2line3 = rs.getString(++scc) != null ? rs.getString(scc) : "";
                    countryid2 = rs.getInt(++scc);

                    stateid2 = rs.getInt(++scc);
                    cityid2 = rs.getInt(++scc);
                    city2_name = rs.getString(++scc) != null ? rs.getString(scc) : "";
                    pincode2 = rs.getString(++scc) != null ? rs.getString(scc) : "";
                    assettypeid = rs.getInt(++scc);

                    positionid2 = rs.getInt(++scc);
                    currencyid = rs.getInt(++scc);
                    expectedsalary = rs.getInt(++scc);
                    drawncurrencyid = rs.getInt(++scc);
                    drawnsalary = rs.getInt(++scc);

                    online = rs.getInt(++scc);
                    fresher = rs.getInt(++scc);
                    onshore = rs.getInt(++scc);
                    kindId = rs.getInt(++scc);
                    degreeId = rs.getInt(++scc);

                    ispassport = rs.getInt(++scc);
                    adhaarNo = rs.getString(++scc) != null ? rs.getString(scc) : "";
                    adhaarFile = rs.getString(++scc) != null ? rs.getString(scc) : "";
                    panNo = rs.getString(++scc) != null ? rs.getString(scc) : "";
                    panFile = rs.getString(++scc) != null ? rs.getString(scc) : "";
                    resumeFile = rs.getString(++scc) != null ? rs.getString(scc) : "";

                    proficiencytypes = rs.getString(++scc) != null ? rs.getString(scc) : "";
//                System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@  " + proficiencytypes);
                    info = new CandidateRegistrationInfo(onshore, firstname, middlename, lastname, dob, placeofbirth, gender, maritalstatusid,
                            nationalityid, religion, code1, contactno1, email, address1line1, address1line2, address1line3, countryid,
                            stateid, cityid, city1_name, pincode, sameasprimaryadd, address2line1, address2line2, address2line3, countryid2,
                            stateid2, cityid2, city2_name, pincode2, assettypeid, positionid2, currencyid, expectedsalary, drawncurrencyid,
                            drawnsalary, online, fresher, kindId, degreeId, ispassport, adhaarNo, adhaarFile, panNo, panFile, resumeFile,
                            proficiencytypes);
                }
                rs.close();
            } catch (Exception exception) {
                print(this, "Exception createCandidate :: " + exception.getMessage());
            } finally {
                close(conn, pstmt, rs);
            }
        }
        return info;
    }

    public int calculateAge(String dob) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy");
        LocalDate birthDate = LocalDate.parse(dob, formatter);
        LocalDate currentDate = LocalDate.now();
        return Period.between(birthDate, currentDate).getYears();
    }

    public Collection getCountryStates(int countryId) {
        Collection coll = new LinkedList();
        coll.add(new CandidateRegistrationInfo(-1, " Select State "));
        if (countryId > 0) {
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT i_stateid, s_name FROM t_state WHERE i_countryid = ? AND i_status = 1 ORDER BY s_name ");
            String query = sb.toString();
            sb.setLength(0);
            try {
                conn = getConnection();
                pstmt = conn.prepareStatement(query);
                pstmt.setInt(1, countryId);
                print(this, "getCountryStates :: " + pstmt.toString());
                rs = pstmt.executeQuery();
                int id;
                String name;
                while (rs.next()) {
                    id = rs.getInt(1);
                    name = rs.getString(2) != null ? rs.getString(2) : "";
                    coll.add(new CandidateRegistrationInfo(id, name));
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                close(conn, pstmt, rs);
            }
        }
        return coll;
    }
}
