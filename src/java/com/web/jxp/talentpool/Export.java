package com.web.jxp.talentpool;

import com.web.jxp.base.Base;
import static com.web.jxp.base.Base.decipher;
import static com.web.jxp.common.Common.print;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.logging.Logger;


public class Export extends Base{
    
    private static final Logger logger = Logger.getLogger(Class.class.getName());
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    
    public ArrayList getexperiencelistdetails(int candidateId) {
        ArrayList list = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT  t_position.s_name,  t_experiencedept.s_name, t_workexperience.s_companyname, t_workexperience.s_assetname,  ");
        sb.append(" DATE_FORMAT(t_workexperience.d_workstartdate, '%d-%b-%Y'), DATE_FORMAT(t_workexperience.d_workenddate, '%d-%b-%Y') ,t_workexperience.i_status,t_workexperience.i_experienceid , ");
        sb.append(" t_workexperience.s_filenameexp,t_workexperience.s_filenamework,t_workexperience.i_currentworkingstatus, t_companyindustry.s_name, ");
        sb.append(" t_assettype.s_name, t_country.s_name,t_city.s_name, t_workexperience.s_clientpartyname, t_experiencewaterdepth.i_depth,t_currency.s_name ,t_workexperience.i_lastdrawnsalary, ");
        sb.append(" t_skills.s_name, t_grade.s_name, t_workexperience.s_ownerpool, t_crewtype.s_name, t_workexperience.s_ocsemployed, t_workexperience.s_legalrights,  ");
        sb.append(" dr.s_name, t_workexperience.i_dayrate, mr.s_name, t_workexperience.i_monthlysalary  from t_workexperience ");
        sb.append("LEFT JOIN t_position on t_position.i_positionid = t_workexperience.i_positionid ");
        sb.append("LEFT JOIN t_experiencedept on t_experiencedept.i_experiencedeptid = t_workexperience.i_departmentid ");
        sb.append("LEFT JOIN t_companyindustry on t_companyindustry.i_companyindustryid = t_workexperience.i_companyindustryid ");
        sb.append("LEFT JOIN t_assettype on t_assettype.i_assettypeid = t_workexperience.i_assettypeid ");
        sb.append("LEFT JOIN t_country on t_country.i_countryid = t_workexperience.i_countryid ");
        sb.append("LEFT JOIN t_city on t_city.i_cityid = t_workexperience.i_cityid ");
        sb.append("LEFT JOIN t_experiencewaterdepth on t_experiencewaterdepth.i_experiencewaterdepthid = t_workexperience.i_waterdepthid ");
        sb.append("LEFT JOIN t_currency on t_currency.i_currencyid = t_workexperience.i_lastdrawnsalarycurrencyid ");
        sb.append("LEFT JOIN t_skills on t_skills.i_skillsid = t_workexperience.i_skillid ");
        sb.append("LEFT JOIN t_grade on t_grade.i_gradeid = t_workexperience.i_gradeid ");
        sb.append("LEFT JOIN t_crewtype on t_crewtype.i_crewtypeid = t_workexperience.i_crewtypeid ");
        sb.append("LEFT JOIN t_currency as dr on dr.i_currencyid = t_workexperience.i_dayratecurrencyid ");
        sb.append("LEFT JOIN t_currency as mr on mr.i_currencyid = t_workexperience.i_monthlysalarycurrencyid ");
        sb.append(" where t_workexperience.i_candidateid = ? ");
        sb.append(" order by t_workexperience.i_status , t_workexperience.d_workstartdate  ");
        String query = (sb.toString()).intern();
        sb.setLength(0);
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, candidateId);
            logger.info("getexperiencelist :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            while (rs.next()) 
            {
                String position = rs.getString(1) != null ? rs.getString(1) : "";
                String department = rs.getString(2) != null ? rs.getString(2) : "";
                String companyname = rs.getString(3) != null ? rs.getString(3) : "";
                String assetname = rs.getString(4) != null ? rs.getString(4) : "";
                String startdate = rs.getString(5) != null ? rs.getString(5) : "";
                String enddate = rs.getString(6) != null ? rs.getString(6) : "";
                int status = rs.getInt(7);
                int experiencedetailId = rs.getInt(8);
                String experiencefilename = rs.getString(9) != null ? rs.getString(9) : "";
                String workfilename = rs.getString(10) != null ? rs.getString(10) : "";
                int currentworkingstatus = rs.getInt(11);
                String compayIndname = rs.getString(12) != null ? rs.getString(12) : "";
                String assettypename = rs.getString(13) != null ? rs.getString(13) : "";
                String countryname = rs.getString(14) != null ? rs.getString(14) : "";
                String cityname = rs.getString(15) != null ? rs.getString(15) : "";
                String operator = rs.getString(16) != null ? rs.getString(16) : "";
                String waterdepth = rs.getString(17) != null ? rs.getString(17) : "";
                String lastdrawncurrrency = rs.getString(18) != null ? rs.getString(18) : "";
                int lastdrawncurrrencyvalue = rs.getInt(19);
                String skills = rs.getString(20) != null ? rs.getString(20) : "";
                String rank = rs.getString(21) != null ? rs.getString(21) : "";
                String ownerpoll = rs.getString(22) != null ? rs.getString(22) : "";
                String crewtypename = rs.getString(23) != null ? rs.getString(23) : "";
                String ocsemployed = rs.getString(24) != null ? rs.getString(24) : "";
                String legalrights = rs.getString(25) != null ? rs.getString(25) : "";
                String daterate = rs.getString(26) != null ? rs.getString(26) : "";
                int dayvalue = rs.getInt(27);
                String monthrate = rs.getString(28) != null ? rs.getString(28) : "";
                int monthvalue = rs.getInt(29);               
                
                if (currentworkingstatus == 1) {
                    enddate = "Present";
                }
                list.add(new ExportInfo(position, department, companyname, assetname, startdate, enddate, status, workfilename, experiencefilename, experiencedetailId,
                compayIndname,assettypename,countryname,cityname,operator,waterdepth,lastdrawncurrrency,lastdrawncurrrencyvalue,skills,rank,ownerpoll,crewtypename,
                ocsemployed,legalrights,daterate,dayvalue,monthrate,monthvalue,currentworkingstatus, "", candidateId));
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return list;
    }
    
    public ArrayList gettrainingCertificatelistdetails(int candidateId) {
        ArrayList list = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT  t_coursename.s_name,  t_coursetype.s_name, t_trainingandcert.s_educinst, t_city.s_name,  t_approvedby.s_name, ");
        sb.append(" DATE_FORMAT(t_trainingandcert.d_dateofissue, '%d-%b-%Y'), DATE_FORMAT(t_trainingandcert.d_expirydate, '%d-%b-%Y') ,t_trainingandcert.i_status, ");
        sb.append(" t_trainingandcert.i_tcid , t_trainingandcert.s_filename,t_country.s_name,  DATE_FORMAT(t_trainingandcert.d_coursestart, '%d-%b-%Y'), DATE_FORMAT(t_trainingandcert.d_passingyear, '%d-%b-%Y'), ");
        sb.append(" t_trainingandcert.s_certificateno, t_trainingandcert.s_fieldofstudy from t_trainingandcert ");
        sb.append("LEFT JOIN t_coursetype on t_coursetype.i_coursetypeid = t_trainingandcert. i_coursetypeid ");
        sb.append("LEFT JOIN t_city on t_city.i_cityid = t_trainingandcert. i_locationofinstitid ");
        sb.append("LEFT JOIN t_country on t_city.i_countryid = t_country. i_countryid ");
        sb.append("LEFT JOIN t_coursename on t_coursename.i_coursenameid = t_trainingandcert.i_coursenameid ");
        sb.append("LEFT JOIN t_approvedby on t_approvedby.i_approvedbyid = t_trainingandcert. i_approvedbyid where t_trainingandcert.i_candidateid = ? ");
        sb.append(" order by t_trainingandcert.i_status, t_coursename.s_name ");
        String query = (sb.toString()).intern();
        sb.setLength(0);
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, candidateId);
            logger.info("gettrainingCertificatelistdetails :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            while (rs.next()) 
            {
                String coursename = rs.getString(1) != null ? rs.getString(1) : "";
                String coursetype = rs.getString(2) != null ? rs.getString(2) : "";
                String educinst = rs.getString(3) != null ? rs.getString(3) : "";
                String locationofinstit = rs.getString(4) != null ? rs.getString(4) : "";
                String approvedby = rs.getString(5) != null ? rs.getString(5) : "";
                String dateofissue = rs.getString(6) != null ? rs.getString(6) : "";
                String expirydate = rs.getString(7) != null ? rs.getString(7) : "";
                int status = rs.getInt(8);
                int trainingandcertId = rs.getInt(9);
                String filename = rs.getString(10) != null ? rs.getString(10) : "";
                String countryname = rs.getString(11) != null ? rs.getString(11) : "";
                String coursestart = rs.getString(12) != null ? rs.getString(12) : "";
                String passingyear = rs.getString(13) != null ? rs.getString(13) : "";
                String certificateno = decipher(rs.getString(14) != null ? rs.getString(14) : "");
                String fieldofstudy = rs.getString(15) != null ? rs.getString(15) : "";               
                
                list.add(new ExportInfo(coursename, coursetype, educinst, locationofinstit, approvedby, dateofissue, expirydate, status,
                        trainingandcertId, filename,countryname,coursestart,passingyear,certificateno,fieldofstudy, "", candidateId));
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return list;
    }
    
    public ArrayList getCandbankListdetails(int candidateId) {
        ArrayList list = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("select t_bankdetail.i_bankdetid, t_bankdetail.s_bankname , t_bankaccounttype.s_name ,t_bankdetail.s_branch, t_bankdetail.s_ifsccode, t_bankdetail.s_filename , ");
        sb.append(" t_bankdetail.i_status , t_bankdetail.i_primarybankid, t_bankdetail.s_savingaccno  ");
        sb.append("FROM t_bankdetail LEFT JOIN t_bankaccounttype on (t_bankdetail.i_bankaccounttypeid = t_bankaccounttype.i_bankaccounttypeid) ");
        sb.append("where t_bankdetail.i_candidateid = ? ");
        sb.append(" order by t_bankdetail.i_status, t_bankdetail.s_bankname ");
        String query = (sb.toString()).intern();
        sb.setLength(0);
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, candidateId);
            logger.info("getCandbankListdetails :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String bankName, bankAccounttype, branch, ifsccode, filename, accountno;
            int candidatebankId, status, primaryBankId;
            while (rs.next()) 
            {
                candidatebankId = rs.getInt(1);
                bankName = rs.getString(2) != null ? rs.getString(2) : "";
                bankAccounttype = (rs.getString(3) != null ? rs.getString(3) : "");
                branch = rs.getString(4) != null ? rs.getString(4) : "";
                ifsccode = decipher(rs.getString(5) != null ? rs.getString(5) : "");
                filename = rs.getString(6) != null ? rs.getString(6) : "";
                status = rs.getInt(7);
                primaryBankId = rs.getInt(8);
                accountno = decipher(rs.getString(9) != null ? rs.getString(9) : "");
                list.add(new ExportInfo(candidatebankId, bankName, bankAccounttype, branch, ifsccode, 
                        filename, status, primaryBankId,accountno, "",candidateId));
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return list;
    }
    
    public ArrayList geteducationlistdetails(int candidateId) {
        ArrayList list = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT  t_qualificationtype.s_name,  t_degree.s_name, t_eduqual.s_eduinstitute, t_city.s_name,t_eduqual.s_fieldofstudy,  ");
        sb.append("DATE_FORMAT(t_eduqual.d_coursestart, '%d-%b-%Y'), DATE_FORMAT(t_eduqual.d_passingyear, '%d-%b-%Y') ,t_eduqual.i_status, ");
        sb.append("t_eduqual.i_eduqulid , t_eduqual.s_filename,t_country.s_name, t_eduqual.i_highestqualification ");
        sb.append("FROM t_eduqual ");
        sb.append("LEFT JOIN t_qualificationtype ON (t_qualificationtype.i_qualificationtypeid = t_eduqual.i_qualificationtypeid) ");
        sb.append("LEFT JOIN t_city ON (t_city.i_cityid = t_eduqual.i_locationofinstituteid) ");
        sb.append("LEFT JOIN t_degree ON (t_degree.i_degreeid = t_eduqual. i_degreeid) ");
        sb.append("LEFT JOIN t_country ON (t_city.i_countryid = t_country. i_countryid) ");
        sb.append("WHERE t_eduqual.i_candidateid = ? ");
        sb.append("ORDER BY t_eduqual.i_status,t_eduqual.d_passingyear desc ");
        String query = (sb.toString()).intern();
        sb.setLength(0);
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, candidateId);
            logger.info("geteducationlist :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            while (rs.next()) 
            {
                String kindname = rs.getString(1) != null ? rs.getString(1) : "";
                String degree = rs.getString(2) != null ? rs.getString(2) : "";
                String educinst = decipher(rs.getString(3) != null ? rs.getString(3) : "");
                String locationofinstit = rs.getString(4) != null ? rs.getString(4) : "";
                String filedofstudy = rs.getString(5) != null ? rs.getString(5) : "";
                String startdate = rs.getString(6) != null ? rs.getString(6) : "";
                String enddate = rs.getString(7) != null ? rs.getString(7) : "";
                int status = rs.getInt(8);
                int educationdetailId = rs.getInt(9);
                String filename = rs.getString(10) != null ? rs.getString(10) : "";
                String countryname = rs.getString(11) != null ? rs.getString(11) : "";
                int highestqualification = rs.getInt(12);
                if (!countryname.equals("")) {
                    locationofinstit += "(" + countryname + ")";
                }
                list.add(new ExportInfo(kindname, degree, educinst, locationofinstit, filedofstudy, 
                        startdate, enddate, status, filename, educationdetailId,highestqualification, "", candidateId));
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return list;
    }
    
    public ArrayList getPersonalDataDump(Connection conn,  String empno) 
    {
        ArrayList list = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT CONCAT_WS(' ', c.s_firstname, c.s_middlename, c.s_lastname), DATE_FORMAT(c.d_dob, '%d-%b-%Y'), c.s_placeofbirth, c.s_email, c.s_code1, ");
        sb.append("c.s_contactno1, c.s_code2, c.s_contactno2, c.s_code3, c.s_contactno3, c.s_gender, t_country.s_name, n.s_nationality, c.s_address1line1, ");
        sb.append("c.s_address1line2, c.s_address2line1, c.s_address2line2, c.s_address2line3, c.s_nextofkin, t_relation.s_name, c.s_ecode1, c.s_econtactno1, ");
        sb.append("c.s_ecode2, c.s_econtactno2, t_maritialstatus.s_name, c.s_photofilename, t_position.s_name , c.s_address1line3, t_city.s_name,t_experiencedept.s_name, ");
        sb.append("c.i_expectedsalary,t_currency.s_name,t1.ct, c.s_firstname ,t_grade.s_name, c.i_vflag, t_assettype.s_name , t_client.i_clientid,t_clientasset.s_name, ");
        sb.append("c.i_clientassetid, t_client.s_name, c.s_empno, c.i_progressid, c.i_applytype, c.i_positionid, t_clientasset.s_ratetype, t_dayrateh.d_rate1, t_dayrateh.d_rate2, ");
        sb.append(" c.i_status, c.s_religion, p2.s_name, g2.s_name, d1.d_rate1, d1.d_rate2, c.i_positionid2, c.i_traveldays, t_state.s_name, c.s_pincode, c.i_age, ");
        sb.append("c.s_airport1, c.s_airport2, t_assettype.i_assettypeid, DATE_FORMAT(current_date(), '%d-%b-%Y'), c.s_profile, c.s_skill1, c.s_skill2, c.i_candidateid ");
        sb.append("FROM t_candidate AS c ");
        sb.append("LEFT JOIN t_country ON (t_country.i_countryid = c.i_countryid) ");
        sb.append("LEFT JOIN t_country AS n ON (n.i_countryid = c.i_nationalityid) ");
        sb.append("LEFT JOIN t_city  ON (t_city.i_cityid = c.i_cityid) ");
        sb.append("LEFT JOIN t_position ON (t_position.i_positionid = c.i_positionid) ");
        sb.append("LEFT JOIN t_relation ON (t_relation.i_relationid = c.i_relationid) ");
        sb.append("LEFT JOIN t_assettype ON (t_assettype.i_assettypeid = c.i_assettypeid) ");
        sb.append("LEFT JOIN t_maritialstatus ON (t_maritialstatus.i_maritialstatusid = c.i_maritialstatusid) ");
        sb.append("LEFT JOIN t_experiencedept ON (t_experiencedept.i_experiencedeptid = c.i_departmentid) ");
        sb.append("LEFT JOIN t_currency ON (t_currency.i_currencyid = c.i_currencyid) ");
        sb.append("LEFT JOIN t_client ON (t_client.i_clientid = c.i_clientid) ");
        sb.append("LEFT JOIN t_clientasset ON (t_clientasset.i_clientassetid = c.i_clientassetid) ");
        sb.append("LEFT JOIN t_position AS p2 ON (p2.i_positionid = c.i_positionid2) ");
        sb.append("LEFT JOIN t_grade AS g2 ON (g2.i_gradeid = p2.i_gradeid) ");
        sb.append("LEFT JOIN (SELECT i_candidateid, COUNT(1) AS ct FROM t_candidatefiles GROUP BY i_candidateid) AS t1 ON (c.i_candidateid = t1.i_candidateid) ");
        sb.append("LEFT JOIN t_grade ON (t_grade.i_gradeid = t_position.i_gradeid) ");
        sb.append("LEFT JOIN t_state ON ( t_state.i_stateid= c.i_stateid ) ");
        sb.append("LEFT JOIN t_dayrateh ON (t_dayrateh.i_candidateid = c.i_candidateid AND t_dayrateh.i_positionid = c.i_positionid) ");
        sb.append("LEFT JOIN t_dayrateh AS d1 ON (d1.i_candidateid = c.i_candidateid AND d1.i_positionid = c.i_positionid2) ");
        sb.append("WHERE c.s_empno LIKE 'AIMS%' ORDER BY c.s_empno, c.s_firstname "); 
        String query = sb.toString().intern();
        sb.setLength(0);
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            print(this, "getPersonalDataDump :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String name, dob, place, email, contact1_code, contact1, contact2_code, contact2, contact3_code,
            contact3, gender, countryName, nationality, address1line1, address1line2, address2line1, address2line2,
            address2line3, nextofkin, relation, econtact1_code, econtact1, econtact2_code, econtact2, maritialstatus,
            photo, positionName, address1line3, cityName, experiencedept, currency, firstname, gradeName, assetName,
            clientName, employeeId, applytypevalue, ratetype, religion, position2, grade2, state, pincode, airport1, airport2, date2, onboardingdate;
            int candidateId,expectedsalary, filecount, vflag, clientId, assetId, progressId, positionId, status, positionId2, travelDays, age, assetTypeId;
            double rate1, rate2, p2rate1, p2rate2;
            while (rs.next()) 
            {
                name = rs.getString(1) != null ? rs.getString(1) : "";
                dob = rs.getString(2) != null ? rs.getString(2) : "";
                place = rs.getString(3) != null ? rs.getString(3) : "";
                email = rs.getString(4) != null ? rs.getString(4) : "";
                contact1_code = rs.getString(5) != null ? rs.getString(5) : "";
                contact1 = decipher(rs.getString(6) != null ? rs.getString(6) : "");
                contact2_code = rs.getString(7) != null ? rs.getString(7) : "";
                contact2 = decipher(rs.getString(8) != null ? rs.getString(8) : "");
                contact3_code = rs.getString(9) != null ? rs.getString(9) : "";
                contact3 = decipher(rs.getString(10) != null ? rs.getString(10) : "");
                gender = rs.getString(11) != null ? rs.getString(11) : "";
                countryName = rs.getString(12) != null ? rs.getString(12) : "";
                nationality = rs.getString(13) != null ? rs.getString(13) : "";
                address1line1 = rs.getString(14) != null ? rs.getString(14) : "";
                address1line2 = rs.getString(15) != null ? rs.getString(15) : "";
                address2line1 = rs.getString(16) != null ? rs.getString(16) : "";
                address2line2 = rs.getString(17) != null ? rs.getString(17) : "";
                address2line3 = rs.getString(18) != null ? rs.getString(18) : "";
                nextofkin = decipher(rs.getString(19) != null ? rs.getString(19) : "");
                relation = rs.getString(20) != null ? rs.getString(20) : "";
                econtact1_code = rs.getString(21) != null ? rs.getString(21) : "";
                econtact1 = decipher(rs.getString(22) != null ? rs.getString(22) : "");
                econtact2_code = rs.getString(23) != null ? rs.getString(23) : "";
                econtact2 = decipher(rs.getString(24) != null ? rs.getString(24) : "");
                maritialstatus = rs.getString(25) != null ? rs.getString(25) : "";
                photo = rs.getString(26) != null ? rs.getString(26) : "";
                positionName = rs.getString(27) != null ? rs.getString(27) : "";
                address1line3 = rs.getString(28) != null ? rs.getString(28) : "";
                cityName = rs.getString(29) != null ? rs.getString(29) : "";
                experiencedept = rs.getString(30) != null ? rs.getString(30) : "";
                expectedsalary = rs.getInt(31);
                currency = rs.getString(32) != null ? rs.getString(32) : "";
                filecount = rs.getInt(33);
                firstname = rs.getString(34) != null ? rs.getString(34) : "";
                gradeName = rs.getString(35) != null ? rs.getString(35) : "";
                vflag = rs.getInt(36);
                String assettype = rs.getString(37) != null ? rs.getString(37) : "";
                clientId = rs.getInt(38);
                assetName = rs.getString(39) != null ? rs.getString(39) : "";
                assetId = rs.getInt(40);
                clientName = rs.getString(41) != null ? rs.getString(41) : "";
                employeeId = rs.getString(42) != null ? rs.getString(42) : "";
                progressId = rs.getInt(43);
                applytypevalue = getApplytypeStatusbyId(rs.getInt(44));
                positionId = rs.getInt(45);
                ratetype = rs.getString(46) != null ? rs.getString(46) : "";
                rate1 = rs.getDouble(47);
                rate2 = rs.getDouble(48);
                status = rs.getInt(49);
                religion = rs.getString(50) != null ? rs.getString(50) : "";
                position2 = rs.getString(51) != null ? rs.getString(51) : "";
                grade2 = rs.getString(52) != null ? rs.getString(52) : "";
                p2rate1 = rs.getDouble(53);
                p2rate2 = rs.getDouble(54);
                positionId2 = rs.getInt(55);
                travelDays = rs.getInt(56);
                state = rs.getString(57) != null ? rs.getString(57): "";
                pincode = rs.getString(58) != null ? rs.getString(58): "";
                age = rs.getInt(59);
                airport1 = rs.getString(60) != null ? rs.getString(60) : "";
                airport2 = rs.getString(61) != null ? rs.getString(61) : "";
                assetTypeId = rs.getInt(62);
                date2 = rs.getString(63) != null ? rs.getString(63) : "";
                String profile = rs.getString(64) != null ? rs.getString(64) : "";
                String skill1 = rs.getString(65) != null ? rs.getString(65) : "";
                String skill2 = rs.getString(66) != null ? rs.getString(66) : "";
                candidateId = rs.getInt(67);
                if (!position2.equals("")) {
                    if (!grade2.equals("")){
                        position2 += " | " + grade2;
                    }
                }
                if (!positionName.equals("")) {
                    if (!gradeName.equals("")) {
                        positionName += " | " + gradeName;
                    }
                }
                if (contact1_code != null && !contact1_code.equals("")) {
                    contact1_code = "+" + contact1_code;
                }
                if (contact2_code != null && !contact2_code.equals("")) {
                    contact2_code = "+" + contact2_code;
                }
                if (contact3_code != null && !contact3_code.equals("")) {
                    contact3_code = "+" + contact3_code;
                }
                if (econtact1_code != null && !econtact1_code.equals("")) {
                    econtact1_code = "+" + econtact1_code;
                }
                if (econtact2_code != null && !econtact2_code.equals("")) {
                    econtact2_code = "+" + econtact2_code;
                }
                list.add( new TalentpoolInfo(candidateId, name, dob, place, email, contact1_code, contact1, contact2_code, contact2, contact3_code, contact3, gender, countryName,
                        nationality, address1line1, address1line2, address2line1, address2line2, address2line3, nextofkin, relation, econtact1_code, econtact1, econtact2_code,
                        econtact2, maritialstatus, photo, positionName, address1line3, cityName, experiencedept, expectedsalary, currency, filecount, firstname, vflag,
                        assettype, clientId, assetName, assetId, clientName, employeeId, progressId, applytypevalue, positionId, ratetype, rate1, rate2, status, religion,
                        position2, p2rate1, p2rate2, positionId2, travelDays, state, pincode, age, airport1, airport2, assetTypeId, date2, profile, skill1, skill2));
            }
        } catch (Exception exception) {
            print(this, "getPersonalDataDump :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return list;
    }

    public ArrayList getLanguageDump(Connection conn, String empcode) {
        ArrayList list = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT t_candlang.i_candlangid, t_language.s_name, t_proficiency.s_name, ");
        sb.append("t_candidate.s_empno, t_candlang.i_status, t_candlang.i_candidateid "); 
        sb.append("FROM t_candlang ");
        sb.append("LEFT JOIN t_candidate ON (t_candidate.i_candidateid = t_candlang.i_candidateid ) ");
        sb.append("LEFT JOIN t_language ON (t_candlang.i_languageid = t_language.i_languageid) ");  
        sb.append("LEFT JOIN t_proficiency ON (t_candlang.i_proficiencyid = t_proficiency.i_proficiencyid) ");  
        sb.append("WHERE t_candidate.s_empno LIKE 'AIMS%' "); 
        sb.append("ORDER BY t_candidate.s_empno, t_language.s_name ");
        String query = (sb.toString()).intern();
        sb.setLength(0);
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);            
            logger.info("getLanguageDump :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String langname, proficiencyname, empno;
            int candidatelangId, candidateId, status;
            while (rs.next()) 
            {
                candidatelangId = rs.getInt(1);
                langname = rs.getString(2) != null ? rs.getString(2) : "";
                proficiencyname = rs.getString(3) != null ? rs.getString(3) : "";
                empno = rs.getString(4) != null ? rs.getString(4) : "";
                status = rs.getInt(5);
                candidateId = rs.getInt(6);
                list.add(new ExportInfo(candidateId, candidatelangId, langname, proficiencyname, empno, status));
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return list;
    }
    
    public ArrayList getEducationDump(Connection conn,  String empcode) {
        ArrayList list = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT t_qualificationtype.s_name,  t_degree.s_name, ");
        sb.append("t_eduqual.s_eduinstitute, t_city.s_name, t_eduqual.s_fieldofstudy, ");   
        sb.append("DATE_FORMAT(t_eduqual.d_coursestart, '%d-%b-%Y'), DATE_FORMAT(t_eduqual.d_passingyear, '%d-%b-%Y') ,t_eduqual.i_status, ");  
        sb.append("t_eduqual.i_eduqulid, t_eduqual.s_filename, t_country.s_name, t_eduqual.i_highestqualification, t_candidate.s_empno, t_candidate.i_candidateid ");  
        sb.append("FROM t_eduqual ");  
        sb.append("LEFT JOIN t_candidate ON (t_candidate.i_candidateid = t_eduqual.i_candidateid) ");
        sb.append("LEFT JOIN t_qualificationtype ON (t_qualificationtype.i_qualificationtypeid = t_eduqual.i_qualificationtypeid) ");  
        sb.append("LEFT JOIN t_city ON (t_city.i_cityid = t_eduqual.i_locationofinstituteid) ");  
        sb.append("LEFT JOIN t_degree ON (t_degree.i_degreeid = t_eduqual. i_degreeid) ");  
        sb.append("LEFT JOIN t_country ON (t_city.i_countryid = t_country. i_countryid) ");  
        sb.append("WHERE t_candidate.s_empno LIKE 'AIMS%' ");   
        sb.append("ORDER BY t_candidate.s_empno, t_qualificationtype.s_name ");
        String query = (sb.toString()).intern();
        sb.setLength(0);
        try 
        {            
            conn = getConnection();
            pstmt = conn.prepareStatement(query);            
            logger.info("getEducationDump :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            while (rs.next()) 
            {
                String kindname = rs.getString(1) != null ? rs.getString(1) : "";
                String degree = rs.getString(2) != null ? rs.getString(2) : "";
                String educinst = decipher(rs.getString(3) != null ? rs.getString(3) : "");
                String locationofinstit = rs.getString(4) != null ? rs.getString(4) : "";
                String filedofstudy = rs.getString(5) != null ? rs.getString(5) : "";
                String startdate = rs.getString(6) != null ? rs.getString(6) : "";
                String enddate = rs.getString(7) != null ? rs.getString(7) : "";
                int status = rs.getInt(8);
                int educationdetailId = rs.getInt(9);
                String filename = rs.getString(10) != null ? rs.getString(10) : "";
                String countryname = rs.getString(11) != null ? rs.getString(11) : "";
                int highestqualification = rs.getInt(12);
                String empno = rs.getString(13) != null ? rs.getString(13) : "";
                int candidateId = rs.getInt(14);
                if (!countryname.equals("")) {
                    locationofinstit += "(" + countryname + ")";
                }
                list.add(new ExportInfo(kindname, degree, educinst, locationofinstit, filedofstudy,
                        startdate, enddate, status, filename, educationdetailId,highestqualification, empno, candidateId));
            }
            rs.close();
        } catch (Exception e) 
        {
            logger.info("getEducationDump :: " + pstmt.toString());
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return list;
    }
    
    public ArrayList getExperienceDataDump(Connection conn,  String empno) {
        ArrayList list = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT t_position.s_name, t_experiencedept.s_name, t_workexperience.s_companyname, t_workexperience.s_assetname,  ");
        sb.append(" DATE_FORMAT(t_workexperience.d_workstartdate, '%d-%b-%Y'), DATE_FORMAT(t_workexperience.d_workenddate, '%d-%b-%Y') ,t_workexperience.i_status,t_workexperience.i_experienceid , ");
        sb.append(" t_workexperience.s_filenameexp,t_workexperience.s_filenamework,t_workexperience.i_currentworkingstatus, t_companyindustry.s_name, ");
        sb.append(" t_assettype.s_name, t_country.s_name,t_city.s_name, t_workexperience.s_clientpartyname, t_experiencewaterdepth.i_depth,t_currency.s_name ,t_workexperience.i_lastdrawnsalary, ");
        sb.append(" t_skills.s_name, t_grade.s_name, t_workexperience.s_ownerpool, t_crewtype.s_name, t_workexperience.s_ocsemployed, t_workexperience.s_legalrights,  ");
        sb.append(" dr.s_name, t_workexperience.i_dayrate, mr.s_name, t_workexperience.i_monthlysalary, t_candidate.s_empno, t_candidate.i_candidateid ");
        sb.append("FROM t_workexperience ");
        sb.append("LEFT JOIN t_candidate ON (t_candidate.i_candidateid = t_workexperience.i_candidateid) ");
        sb.append("LEFT JOIN t_position ON (t_position.i_positionid = t_workexperience.i_positionid) ");
        sb.append("LEFT JOIN t_experiencedept ON (t_experiencedept.i_experiencedeptid = t_workexperience.i_departmentid) ");
        sb.append("LEFT JOIN t_companyindustry ON (t_companyindustry.i_companyindustryid = t_workexperience.i_companyindustryid) ");
        sb.append("LEFT JOIN t_assettype ON (t_assettype.i_assettypeid = t_workexperience.i_assettypeid) ");
        sb.append("LEFT JOIN t_country ON (t_country.i_countryid = t_workexperience.i_countryid) ");
        sb.append("LEFT JOIN t_city ON (t_city.i_cityid = t_workexperience.i_cityid) ");
        sb.append("LEFT JOIN t_experiencewaterdepth ON (t_experiencewaterdepth.i_experiencewaterdepthid = t_workexperience.i_waterdepthid) ");
        sb.append("LEFT JOIN t_currency ON (t_currency.i_currencyid = t_workexperience.i_lastdrawnsalarycurrencyid) ");
        sb.append("LEFT JOIN t_skills ON (t_skills.i_skillsid = t_workexperience.i_skillid) ");
        sb.append("LEFT JOIN t_grade ON (t_grade.i_gradeid = t_workexperience.i_gradeid) ");
        sb.append("LEFT JOIN t_crewtype ON (t_crewtype.i_crewtypeid = t_workexperience.i_crewtypeid) ");
        sb.append("LEFT JOIN t_currency AS dr ON (dr.i_currencyid = t_workexperience.i_dayratecurrencyid) ");
        sb.append("LEFT JOIN t_currency AS mr ON (mr.i_currencyid = t_workexperience.i_monthlysalarycurrencyid) ");
        sb.append("WHERE t_candidate.s_empno LIKE 'AIMS%' ");
        sb.append("ORDER BY t_candidate.s_empno, t_workexperience.i_status  ");
        String query = (sb.toString()).intern();
        sb.setLength(0);
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);            
            logger.info("getExperienceDataDump :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            while (rs.next()) 
            {
                String position = rs.getString(1) != null ? rs.getString(1) : "";
                String department = rs.getString(2) != null ? rs.getString(2) : "";
                String companyname = rs.getString(3) != null ? rs.getString(3) : "";
                String assetname = rs.getString(4) != null ? rs.getString(4) : "";
                String startdate = rs.getString(5) != null ? rs.getString(5) : "";
                String enddate = rs.getString(6) != null ? rs.getString(6) : "";
                int status = rs.getInt(7);
                int experiencedetailId = rs.getInt(8);
                String experiencefilename = rs.getString(9) != null ? rs.getString(9) : "";
                String workfilename = rs.getString(10) != null ? rs.getString(10) : "";
                int currentworkingstatus = rs.getInt(11);
                String compayIndname = rs.getString(12) != null ? rs.getString(12) : "";
                String assettypename = rs.getString(13) != null ? rs.getString(13) : "";
                String countryname = rs.getString(14) != null ? rs.getString(14) : "";
                String cityname = rs.getString(15) != null ? rs.getString(15) : "";
                String operator = rs.getString(16) != null ? rs.getString(16) : "";
                String waterdepth = rs.getString(17) != null ? rs.getString(17) : "";
                String lastdrawncurrrency = rs.getString(18) != null ? rs.getString(18) : "";
                int lastdrawncurrrencyvalue = rs.getInt(19);
                String skills = rs.getString(20) != null ? rs.getString(20) : "";
                String rank = rs.getString(21) != null ? rs.getString(21) : "";
                String ownerpoll = rs.getString(22) != null ? rs.getString(22) : "";
                String crewtypename = rs.getString(23) != null ? rs.getString(23) : "";
                String ocsemployed = rs.getString(24) != null ? rs.getString(24) : "";
                String legalrights = rs.getString(25) != null ? rs.getString(25) : "";
                String daterate = rs.getString(26) != null ? rs.getString(26) : "";
                int dayvalue = rs.getInt(27);
                String monthrate = rs.getString(28) != null ? rs.getString(28) : "";
                int monthvalue = rs.getInt(29);               
                String empId = rs.getString(30) != null ? rs.getString(30) : "";
                int candidateId = rs.getInt(31);               
                if (currentworkingstatus == 1) {
                    enddate = "Present";
                }
                list.add(new ExportInfo(position, department, companyname, assetname, startdate, enddate, status, workfilename, experiencefilename, experiencedetailId,
                compayIndname,assettypename,countryname,cityname,operator,waterdepth,lastdrawncurrrency,lastdrawncurrrencyvalue,skills,rank,ownerpoll,crewtypename,
                ocsemployed,legalrights,daterate,dayvalue,monthrate,monthvalue,currentworkingstatus, empId, candidateId));
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return list;
    }
    
    public ArrayList getVaccinationDump(Connection conn, String empno) 
    {
        ArrayList list = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT t_vbd.i_vbdid, t_vaccine.s_name , t_vaccinetype.s_name ,t_city.s_name, t_vbd.s_filename, ");
        sb.append("t_vbd.i_status ,DATE_FORMAT(t_vbd.d_dateofapplication, '%d-%b-%Y'),DATE_FORMAT(t_vbd.d_dateofexpiry, '%d-%b-%Y'), ");
        sb.append("t_country.s_name, t_candidate.s_empno, t_candidate.i_candidateid ");
        sb.append("FROM t_vbd ");
        sb.append("LEFT JOIN t_candidate ON (t_candidate.i_candidateid = t_vbd.i_candidateid) ");
        sb.append("LEFT JOIN t_vaccine ON (t_vbd.i_vaccinenameid = t_vaccine.i_vaccineid) ");
        sb.append("LEFT JOIN t_vaccinetype ON (t_vbd.i_vaccinetypeid = t_vaccinetype.i_vaccinetypeid) ");
        sb.append("LEFT JOIN t_city ON (t_vbd.i_placeofapplicationid = t_city.i_cityid) ");
        sb.append("LEFT JOIN t_country ON (t_country.i_countryid = t_city.i_countryid) ");
        sb.append("WHERE t_candidate.s_empno LIKE 'AIMS%' ");
        sb.append("ORDER BY t_candidate.s_empno, t_vaccine.s_name ");
        String query = (sb.toString()).intern();
        sb.setLength(0);
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);            
            logger.info("getVaccinationDump :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String vaccinename, vaccinetypename, placeofapplication, filename, 
            dateofapplication, dateofexpiry, countryname, empId;
            int candidatevaccId, status, candidateId;
            while (rs.next()) 
            {
                candidatevaccId = rs.getInt(1);
                vaccinename = rs.getString(2) != null ? rs.getString(2) : "";
                vaccinetypename = rs.getString(3) != null ? rs.getString(3) : "";
                placeofapplication = rs.getString(4) != null ? rs.getString(4) : "";
                filename = rs.getString(5) != null ? rs.getString(5) : "";
                status = rs.getInt(6);
                dateofapplication = rs.getString(7);
                dateofexpiry = rs.getString(8);
                countryname = rs.getString(9) != null ? rs.getString(9) : "";
                empId = rs.getString(10) != null ? rs.getString(10) : "";
                candidateId = rs.getInt(11);
                if (!placeofapplication.equals("")) {
                    if (!countryname.equals("")) {
                        placeofapplication += " (" + countryname + ")";
                    }
                }
                list.add(new TalentpoolInfo(candidatevaccId, vaccinename, vaccinetypename, placeofapplication, 
                        filename, status, dateofapplication, dateofexpiry, empId, candidateId));
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return list;
    }
    
    public ArrayList getDocumentDump(Connection conn, String empno) 
    {
        ArrayList list = new ArrayList();
        StringBuilder sb = new StringBuilder();        
        sb.append("SELECT t_govdoc.i_govid, t_doctype.s_doc , t_govdoc.s_docno ,t_city.s_name, t_documentissuedby.s_name, ");  
        sb.append("t_govdoc.i_status ,DATE_FORMAT(t_govdoc.d_dateofissue, '%d-%b-%Y'),DATE_FORMAT(t_govdoc.d_expirydate, '%d-%b-%Y'), ");  
        sb.append("t_country.s_name, t1.ct, t_candidate.s_empno, t_candidate.i_candidateid ");
        sb.append("FROM t_govdoc ");  
        sb.append("LEFT JOIN t_candidate ON (t_candidate.i_candidateid = t_govdoc.i_candidateid ) ");
        sb.append("LEFT JOIN t_doctype ON (t_govdoc.i_doctypeid = t_doctype.i_doctypeid) ");  
        sb.append("LEFT JOIN t_city ON (t_govdoc.i_placeofissueid = t_city.i_cityid) ");  
        sb.append("LEFT JOIN t_documentissuedby ON (t_govdoc.i_documentissuedbyid = t_documentissuedby.i_documentissuedbyid) ");  
        sb.append("LEFT JOIN t_country ON (t_city.i_countryid = t_country.i_countryid) ");  
        sb.append("LEFT JOIN (SELECT i_govid, COUNT(1) AS ct FROM t_documentfiles WHERE i_status = 1 GROUP BY i_govid) AS t1 ON (t1.i_govid = t_govdoc.i_govid) "); 
        sb.append("WHERE t_candidate.s_empno LIKE 'AIMS%' ");
        sb.append("ORDER BY t_candidate.s_empno, t_doctype.s_doc ");
        
        String query = (sb.toString()).intern();
        sb.setLength(0);
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);            
            logger.info("getDocumentDump :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String documentname, documentno, placeofissue, issuedby, dateofissue, dateofexpiry, countryname, empId;
            int govdocumentId, status, filecount, candidateId;
            while (rs.next()) 
            {
                govdocumentId = rs.getInt(1);
                documentname = rs.getString(2) != null ? rs.getString(2) : "";
                documentno = decipher(rs.getString(3) != null ? rs.getString(3) : "");
                placeofissue = rs.getString(4) != null ? rs.getString(4) : "";
                issuedby = rs.getString(5) != null ? rs.getString(5) : "";
                status = rs.getInt(6);
                dateofissue = rs.getString(7) != null ? rs.getString(7) : "";
                dateofexpiry = rs.getString(8) != null ? rs.getString(8) : "";
                countryname = rs.getString(9) != null ? rs.getString(9) : "";
                filecount = rs.getInt(10);
                empId = rs.getString(11) != null ? rs.getString(11) : "";
                candidateId = rs.getInt(12);
                if (!countryname.equals("")) {
                    placeofissue += "(" + countryname + ")";
                }
                list.add(new TalentpoolInfo(govdocumentId, documentname, documentno, placeofissue, issuedby,
                        status, dateofissue, dateofexpiry, countryname, filecount, empId, candidateId));
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return list;
    }
    
    public ArrayList getCertificationDump(Connection conn, String empno) {
        ArrayList list = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT t_coursename.s_name, t_coursetype.s_name, t_trainingandcert.s_educinst, t_city.s_name, t_approvedby.s_name, "); 
        sb.append("DATE_FORMAT(t_trainingandcert.d_dateofissue, '%d-%b-%Y'), DATE_FORMAT(t_trainingandcert.d_expirydate, '%d-%b-%Y'), "); 
        sb.append("t_trainingandcert.i_status, t_trainingandcert.i_tcid, t_trainingandcert.s_filename, t_country.s_name, "); 
        sb.append("DATE_FORMAT(t_trainingandcert.d_coursestart, '%d-%b-%Y'), DATE_FORMAT(t_trainingandcert.d_passingyear, '%d-%b-%Y'), ");  
        sb.append("t_trainingandcert.s_certificateno, t_trainingandcert.s_fieldofstudy, t_candidate.s_empno, t_candidate.i_candidateid ");
        sb.append("FROM t_trainingandcert ");  
        sb.append("LEFT JOIN t_candidate ON (t_candidate.i_candidateid = t_trainingandcert.i_candidateid) ");
        sb.append("LEFT JOIN t_coursetype ON (t_coursetype.i_coursetypeid = t_trainingandcert. i_coursetypeid) ");
        sb.append("LEFT JOIN t_city ON (t_city.i_cityid = t_trainingandcert. i_locationofinstitid) ");
        sb.append("LEFT JOIN t_country ON (t_city.i_countryid = t_country. i_countryid) ");
        sb.append("LEFT JOIN t_coursename ON (t_coursename.i_coursenameid = t_trainingandcert.i_coursenameid) ");  
        sb.append("LEFT JOIN t_approvedby ON (t_approvedby.i_approvedbyid = t_trainingandcert. i_approvedbyid) ");
        sb.append("WHERE t_candidate.s_empno LIKE 'AIMS%' ");
        sb.append("ORDER BY t_candidate.s_empno, t_coursename.s_name ");
        String query = (sb.toString()).intern();
        sb.setLength(0);
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            logger.info("getCertificationDump :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            while (rs.next()) 
            {
                String coursename = rs.getString(1) != null ? rs.getString(1) : "";
                String coursetype = rs.getString(2) != null ? rs.getString(2) : "";
                String educinst = rs.getString(3) != null ? rs.getString(3) : "";
                String locationofinstit = rs.getString(4) != null ? rs.getString(4) : "";
                String approvedby = rs.getString(5) != null ? rs.getString(5) : "";
                String dateofissue = rs.getString(6) != null ? rs.getString(6) : "";
                String expirydate = rs.getString(7) != null ? rs.getString(7) : "";
                int status = rs.getInt(8);
                int trainingandcertId = rs.getInt(9);
                String filename = rs.getString(10) != null ? rs.getString(10) : "";
                String countryname = rs.getString(11) != null ? rs.getString(11) : "";
                String coursestart = rs.getString(12) != null ? rs.getString(12) : "";
                String passingyear = rs.getString(13) != null ? rs.getString(13) : "";
                String certificateno = decipher(rs.getString(14) != null ? rs.getString(14) : "");
                String fieldofstudy = rs.getString(15) != null ? rs.getString(15) : "";               
                String empId = rs.getString(16) != null ? rs.getString(16) : "";   
                int candidateId = rs.getInt(17);
                
                list.add(new ExportInfo(coursename, coursetype, educinst, locationofinstit, approvedby, dateofissue, 
                        expirydate, status, trainingandcertId, filename,countryname,coursestart,passingyear,
                        certificateno,fieldofstudy, empId, candidateId));
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return list;
    }
    
    public ArrayList getHealthDetailDump(Connection conn, String empno) {
        ArrayList list = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT s_ssmf, s_ogukmedicalftw, DATE_FORMAT(d_ogukexp, '%d-%b-%Y'), s_medifitcert, DATE_FORMAT(d_medifitcertexp, '%d-%b-%Y'), ");  
        sb.append("s_bloodgroup, t_healthdeclaration.i_bloodpressureid, s_hypertension, s_diabetes, s_smoking, t_healthdeclaration.i_userid, s_covid192doses, ");  
        sb.append("s_filename, t_healthdeclaration.i_status, i_healthid, t_bloodpressure.s_name, t_candidate.s_empno, t_candidate.i_candidateid, "); 
        sb.append("d_height, d_weight FROM t_healthdeclaration ");
        sb.append("LEFT JOIN t_candidate ON (t_candidate.i_candidateid = t_healthdeclaration.i_candidateid) ");  
        sb.append("LEFT JOIN t_bloodpressure ON (t_bloodpressure.i_bloodpressureid = t_healthdeclaration.i_bloodpressureid) ");  
        sb.append("WHERE t_candidate.s_empno LIKE 'AIMS%' ");
        sb.append("ORDER BY t_candidate.s_empno ");
        
        String query = sb.toString().intern();
        sb.setLength(0);
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);            
            print(this, "getHealthDetailDump :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            while (rs.next()) 
            {
                String ssmf = decipher(rs.getString(1) != null ? rs.getString(1) : "");
                String ogukmedicalftw = decipher(rs.getString(2) != null ? rs.getString(2) : "");
                String ogukexp = rs.getString(3) != null ? rs.getString(3) : "";
                String medifitcert = decipher(rs.getString(4) != null ? rs.getString(4) : "");
                String medifitcertexp = rs.getString(5) != null ? rs.getString(5) : "";
                String bloodgroup = decipher(rs.getString(6) != null ? rs.getString(6) : "");
                int bloodpressureId = rs.getInt(7);
                String hypertension = decipher(rs.getString(8) != null ? rs.getString(8) : "");
                String diabetes = decipher(rs.getString(9) != null ? rs.getString(9) : "");
                String smoking = decipher(rs.getString(10) != null ? rs.getString(10) : "");
                int userId = rs.getInt(11);
                String covid192doses = decipher(rs.getString(12) != null ? rs.getString(12) : "");
                String mfFilename = rs.getString(13) != null ? rs.getString(13) : "";
                int status = rs.getInt(14);
                int candidatehealthId = rs.getInt(15);
                String bloodpressure = rs.getString(16) != null ? rs.getString(16) : "";
                String empId = rs.getString(17) != null ? rs.getString(17) : "";
                int candidateId = rs.getInt(18);
                double height = rs.getDouble(19);
                double weight = rs.getDouble(20);
                
                list.add(new TalentpoolInfo(candidatehealthId, ssmf, ogukmedicalftw, ogukexp, medifitcert, medifitcertexp,
                        bloodgroup, bloodpressureId, hypertension, diabetes, smoking, userId, mfFilename, status, 
                        covid192doses, bloodpressure, empId, candidateId, height, weight));
            }
        } catch (Exception e) {
            print(this, "getHealthDetailDump :: " + e.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return list;
    }
    
    public ArrayList getBankdetailsDump(Connection conn, String empno) 
    {
        ArrayList list = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT t_bankdetail.i_bankdetid, t_bankdetail.s_bankname, t_bankaccounttype.s_name ,t_bankdetail.s_branch, "); 
        sb.append("t_bankdetail.s_ifsccode, t_bankdetail.s_filename ,  t_bankdetail.i_status , t_bankdetail.i_primarybankid, "); 
        sb.append("t_bankdetail.s_savingaccno, t_candidate.s_empno, t_candidate.i_candidateid ");   
        sb.append("FROM t_bankdetail "); 
        sb.append("LEFT JOIN t_candidate ON (t_candidate.i_candidateid = t_bankdetail.i_candidateid) ");
        sb.append("LEFT JOIN t_bankaccounttype ON (t_bankdetail.i_bankaccounttypeid = t_bankaccounttype.i_bankaccounttypeid) ");  
        sb.append("WHERE t_candidate.s_empno LIKE 'AIMS%' ");
        sb.append("ORDER BY t_candidate.s_empno, t_bankdetail.s_bankname ");
        String query = (sb.toString()).intern();
        sb.setLength(0);
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            logger.info("getBankdetailsDump :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String bankName, bankAccounttype, branch, ifsccode, filename, accountno;
            int candidatebankId, status, primaryBankId, candidateId;
            while (rs.next()) 
            {
                candidatebankId = rs.getInt(1);
                bankName = rs.getString(2) != null ? rs.getString(2) : "";
                bankAccounttype = (rs.getString(3) != null ? rs.getString(3) : "");
                branch = rs.getString(4) != null ? rs.getString(4) : "";
                ifsccode = decipher(rs.getString(5) != null ? rs.getString(5) : "");
                filename = rs.getString(6) != null ? rs.getString(6) : "";
                status = rs.getInt(7);
                primaryBankId = rs.getInt(8);
                accountno = decipher(rs.getString(9) != null ? rs.getString(9) : "");
                String empId = rs.getString(10) != null ? rs.getString(10) : "";
                candidateId = rs.getInt(11);
                
                list.add(new ExportInfo(candidatebankId, bankName, bankAccounttype, branch, ifsccode, filename, 
                        status, primaryBankId, accountno, empId, candidateId));
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return list;
    }
    
    public ArrayList getRemarksDump(Connection conn, String empno) {
        ArrayList list = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT t_offshoreupdate.i_offshoreupdateid, t_client.s_name, t_clientasset.s_name, ");  
        sb.append("t_remarktype.s_name, t_offshoreupdate.s_remarks, t_offshoreupdate.i_status, t_offshoreupdate.s_filename, ");   
        sb.append("DATE_FORMAT(t_offshoreupdate.d_date, '%d-%b-%Y'), t_candidate.s_empno, t_candidate.i_candidateid ");
        sb.append("FROM t_offshoreupdate ");  
        sb.append("LEFT JOIN t_candidate ON (t_candidate.i_candidateid = t_offshoreupdate.i_candidateid) ");
        sb.append("LEFT JOIN t_client ON (t_offshoreupdate.i_clientid = t_client.i_clientid) ");  
        sb.append("LEFT JOIN t_clientasset ON (t_offshoreupdate.i_clientassetid =t_clientasset.i_clientassetid) ");  
        sb.append("LEFT JOIN t_remarktype ON (t_remarktype.i_remarktypeid = t_offshoreupdate.i_remarktypeid) ");  
        sb.append("WHERE t_candidate.s_empno LIKE 'AIMS%' ");  
        sb.append("ORDER BY t_candidate.s_empno, t_client.s_name ");
        String query = (sb.toString()).intern();
        sb.setLength(0);
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            logger.info("getRemarksDump :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String clinetname, assetname, filename, remarktype, remark, date, empId;
            int offshoreId, status, candidateId;
            while (rs.next()) 
            {
                offshoreId = rs.getInt(1);
                clinetname = rs.getString(2) != null ? rs.getString(2) : "";
                assetname = rs.getString(3) != null ? rs.getString(3) : "";
                remarktype = rs.getString(4) != null ? rs.getString(4) : "";
                remark = rs.getString(5) != null ? rs.getString(5) : "";
                status = rs.getInt(6);
                filename = rs.getString(7) != null ? rs.getString(7) : "";
                date = rs.getString(8) != null ? rs.getString(8) : "";
                empId = rs.getString(9) != null ? rs.getString(9) : "";
                candidateId = rs.getInt(10);
                
                list.add(new TalentpoolInfo(offshoreId, clinetname, assetname, remarktype, remark, status, filename, date, candidateId, empId));
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return list;
    }
    
    public ArrayList getNomineeDetailDump(Connection conn, String empno) {
        ArrayList list = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT t_nominee.i_nomineeid, t_nominee.s_nomineename, t_nominee.s_nomineecontactno, t_relation.s_name, "); 
        sb.append("t_nominee.i_status, t_nominee.s_code, t_nominee.s_address, t_nominee.i_age, t_nominee.d_percentage, t_candidate.s_empno, t_candidate.i_candidateid "); 
        sb.append("FROM t_nominee ");
        sb.append("LEFT JOIN t_candidate ON (t_candidate.i_candidateid = t_nominee.i_candidateid) ");
        sb.append("LEFT JOIN t_relation ON (t_relation.i_relationid = t_nominee.i_relationid) ");  
        sb.append("WHERE t_candidate.s_empno LIKE 'AIMS%' "); 
        sb.append("ORDER BY t_candidate.s_empno, t_nominee.s_nomineename ");
        String query = (sb.toString()).intern();
        sb.setLength(0);
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            logger.info("getNomineeDetailDump :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String nomineename, nomineecontactno, address,nomineerelation, code, empId;
            int nomineedetailId, status, age, candidateId;
            double percentage;
            while (rs.next()) 
            {
                nomineedetailId = rs.getInt(1);
                nomineename = rs.getString(2) != null ? rs.getString(2) : "";
                nomineecontactno = rs.getString(3) != null ? rs.getString(3) : "";
                nomineerelation = rs.getString(4) != null ? rs.getString(4) : "";
                status = rs.getInt(5);
                code = rs.getString(6) != null ? rs.getString(6) : "";
                address = rs.getString(7) != null ? rs.getString(7) : "";
                age = rs.getInt(8);
                percentage = rs.getDouble(9);
                empId = rs.getString(10) != null ? rs.getString(10) : "";
                candidateId = rs.getInt(11);
                if (code != null && !code.equals("")) {
                    if (nomineecontactno != null && !nomineecontactno.equals("")) {
                        nomineecontactno = "+" + code + " " + nomineecontactno;
                    }
                }
                list.add(new TalentpoolInfo(nomineedetailId, nomineename, nomineecontactno, 
                        nomineerelation, status, code, address, age, percentage, empId, candidateId));
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return list;
    }
    
    public ArrayList getContractDump(Connection conn, String empno) {
        ArrayList list = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT t_contractdetail.i_contractdetailid, t_client.s_name, t_clientasset.s_name, t_position.s_name, t_grade.s_name, t_contract.i_type, "); 
        sb.append("DATE_FORMAT(t_contractdetail.d_fromdate, '%d-%b-%Y '), DATE_FORMAT(t_contractdetail.d_todate, '%d-%b-%Y'), ");  
        sb.append("t_contractdetail.s_file1, t_contractdetail.s_file2, t_contractdetail.s_file3, t_contractdetail.i_status, ");  
        sb.append("t_contractdetail.i_approval1, t_contractdetail.i_approval2, t_candidate.s_empno, t_candidate.i_candidateid ");
        sb.append("FROM t_contractdetail ");  
        sb.append("LEFT JOIN t_candidate ON (t_candidate.i_candidateid = t_contractdetail.i_candidateid) ");
        sb.append("LEFT JOIN t_clientasset ON (t_clientasset.i_clientassetid = t_contractdetail.i_clientassetid) ");  
        sb.append("LEFT JOIN t_client ON ( t_client.i_clientid = t_clientasset.i_clientid) ");  
        sb.append("LEFT JOIN t_position ON (t_position.i_positionid = t_contractdetail.i_positionid) ");  
        sb.append("LEFT JOIN t_grade ON (t_grade.i_gradeid = t_position.i_gradeid) ");  
        sb.append("LEFT JOIN t_contract ON(t_contract.i_contractid = t_contractdetail.i_contractid) ");
        sb.append("WHERE t_candidate.s_empno LIKE 'AIMS%' "); 
        sb.append("ORDER BY t_candidate.s_empno,t_client.s_name ");
        String query = (sb.toString()).intern();
        sb.setLength(0);
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            logger.info("getContractDump :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            while (rs.next()) 
            {
                int contractdetailId = rs.getInt(1);
                String clientName = rs.getString(2) != null ? rs.getString(2) : "";
                String assetName = rs.getString(3) != null ? rs.getString(3) : "";
                String positionName = rs.getString(4) != null ? rs.getString(4) : "";
                String gradeName = rs.getString(5) != null ? rs.getString(5) : "";
                int type = rs.getInt(6);
                String fromDate = rs.getString(7) != null ? rs.getString(7) : "";
                String toDate = rs.getString(8) != null ? rs.getString(8) : "";
                String file1 = rs.getString(9) != null ? rs.getString(9) : "";
                String file2 = rs.getString(10) != null ? rs.getString(10) : "";
                String file3 = rs.getString(11) != null ? rs.getString(11) : "";
                int status = rs.getInt(12);
                int approval1 = rs.getInt(13);
                int approval2 = rs.getInt(14);
                String empId = rs.getString(15) != null ? rs.getString(15) : "";
                int candidateId = rs.getInt(16);
                
                list.add(new TalentpoolInfo(contractdetailId, clientName, assetName, positionName,
                        gradeName, type, fromDate, toDate, file1, file2, file3, status, approval1, 
                        approval2, "", 0, 0, empId, candidateId));
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return list;
    }
    
    public ArrayList getPpeDump(Connection conn, String empno) 
    {
        ArrayList list = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT t_ppedetail.i_ppedetailid, t_ppedetail.s_remarks, t_ppedetail.i_status, t_ppetype.s_name, ");  
        sb.append("DATE_FORMAT(t_ppedetail.ts_moddate, '%d-%b-%Y'), t_userlogin.s_name, t_candidate.s_empno, t_candidate.i_candidateid "); 
        sb.append("FROM t_ppedetail ");
        sb.append("LEFT JOIN t_candidate ON(t_candidate.i_candidateid = t_ppedetail.i_candidateid) ");
        sb.append("LEFT JOIN t_ppetype ON(t_ppetype.i_ppetypeid = t_ppedetail.i_ppetypeid) ");  
        sb.append("LEFT JOIN t_userlogin ON(t_userlogin.i_userid = t_ppedetail.i_userid) ");  
        sb.append("WHERE t_candidate.s_empno LIKE 'AIMS%' ");  
        sb.append("ORDER BY t_candidate.s_empno, t_ppetype.s_name ");        
        String query = (sb.toString()).intern();
        sb.setLength(0);
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);            
            logger.info("getPpeDump :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String ppetype, remark,date, username, empId;
            int ppedetailId, status, candidateId;
            while (rs.next()) 
            {
                ppedetailId = rs.getInt(1);
                remark = rs.getString(2) != null ? rs.getString(2) : "";
                status = rs.getInt(3);
                ppetype = rs.getString(4) != null ? rs.getString(4) : "";
                date = rs.getString(5) != null ? rs.getString(5) : "";
                username = rs.getString(6) != null ? rs.getString(6) : "";
                empId = rs.getString(7) != null ? rs.getString(7) : "";
                candidateId = rs.getInt(8);
                
                list.add(new TalentpoolInfo(ppedetailId, remark, status, ppetype, date, username, empId, candidateId));
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return list;
    }
    
    
    public ArrayList getAppraisalDump(Connection conn, String empno) 
    {
        ArrayList list = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT t_appraisal.i_appraisalid, t_position.i_positionid, t_position.s_name, t_grade.s_name, p2.i_positionid, ");  
        sb.append("p2.s_name, g2.s_name, t_appraisal.s_remarks, DATE_FORMAT(t_appraisal.d_date, '%d-%b-%Y'), ");  
        sb.append("t_appraisal.i_status, t_appraisal.s_filename, t_appraisal.d_rate1, t_appraisal.d_rate2, t_appraisal.d_rate3, ");  
        sb.append("DATE_FORMAT(t_appraisal.d_date2, '%d-%b-%Y'), t_candidate.s_empno, t_candidate.i_candidateid  ");  
        sb.append("FROM t_appraisal "); 
        sb.append("LEFT JOIN t_candidate ON (t_candidate.i_candidateid = t_appraisal.i_candidateid) ");
        sb.append("LEFT JOIN t_position ON(t_position.i_positionid = t_appraisal.i_positionid1) ");  
        sb.append("LEFT JOIN t_grade ON (t_grade.i_gradeid = t_position.i_gradeid) ");  
        sb.append("LEFT JOIN t_position AS p2 ON(p2.i_positionid = t_appraisal.i_positionid2) ");  
        sb.append("LEFT JOIN t_grade AS g2 ON (g2.i_gradeid = p2.i_gradeid) ");  
        sb.append("WHERE t_candidate.s_empno LIKE 'AIMS%' ");  
        sb.append("ORDER BY t_candidate.s_empno, t_appraisal.d_date DESC ");
        String query = (sb.toString()).intern();
        sb.setLength(0);
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            logger.info("getAppraisalDump :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String position, position2, grade1, grade2,filename, remark, date, date2, empId;
            int appraisalId, positionId, positionId2, status, candidateId;
            double newRate1,newRate2, newRate3;
            while (rs.next()) 
            {
                appraisalId = rs.getInt(1);
                positionId = rs.getInt(2);
                position = rs.getString(3) != null ? rs.getString(3) : "";
                grade1 = rs.getString(4) != null ? rs.getString(4) : "";
                positionId2 = rs.getInt(5);
                position2 = rs.getString(6) != null ? rs.getString(6) : "";
                grade2 = rs.getString(7) != null ? rs.getString(7) : "";
                remark = rs.getString(8) != null ? rs.getString(8) : "";
                date = rs.getString(9) != null ? rs.getString(9) : "";
                status = rs.getInt(10);
                filename = rs.getString(11) != null ? rs.getString(11) : "";                
                newRate1 = rs.getDouble(12);
                newRate2 = rs.getDouble(13);
                newRate3 = rs.getDouble(14);
                date2 = rs.getString(15) != null ? rs.getString(15) : "";
                empId = rs.getString(16) != null ? rs.getString(16) : "";
                candidateId = rs.getInt(17);
                if(!grade1.equals(""))
                {
                    position += " | "+grade1;
                }
                if(!grade2.equals(""))
                {
                    position2 += " | "+grade2;
                }
                
                list.add(new TalentpoolInfo(appraisalId, positionId, position, positionId2, position2,
                        remark, date,status, filename, newRate1,newRate2, newRate3, date2, empId, candidateId));
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return list;
    }
    
    public ArrayList getDayrateDump(Connection conn, String empno)
    {
        ArrayList list = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT t_dayrateh.i_dayratehid, t_position.s_name, DATE_FORMAT(t_dayrateh.d_fromdate, '%d-%b-%Y'), ");  
        sb.append("DATE_FORMAT(t_dayrateh.d_todate, '%d-%b-%Y'), t_dayrateh.d_rate1, t_dayrateh.d_rate2, t_dayrateh.d_rate3, ");  
        sb.append("t_grade.s_name, t_dayrateh.i_positionid, t_candidate.s_empno, t_candidate.i_candidateid ");
        sb.append("FROM t_dayrateh ");
        sb.append("LEFT JOIN t_candidate ON (t_candidate.i_candidateid = t_dayrateh.i_candidateid) ");
        sb.append("LEFT JOIN t_position ON(t_position.i_positionid = t_dayrateh.i_positionid) ");  
        sb.append("LEFT JOIN t_grade ON (t_grade.i_gradeid = t_position.i_gradeid) ");  
        sb.append("WHERE t_candidate.s_empno LIKE 'AIMS%' ");  
        sb.append("ORDER BY t_candidate.s_empno, t_dayrateh.d_fromdate DESC ");
        String query = (sb.toString()).intern();
        sb.setLength(0);
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            logger.info("getDayrateDump :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String fromDate, toDate, position, grade, empId;
            int dayrateId, positionId, candidateId;
            double rate1, rate2, rate3;
            while (rs.next())
            {
                dayrateId = rs.getInt(1);
                position = rs.getString(2) != null ? rs.getString(2) : "";
                fromDate = rs.getString(3) != null ? rs.getString(3) : "";
                toDate = rs.getString(4) != null ? rs.getString(4) : "";
                rate1 = rs.getDouble(5);
                rate2 = rs.getDouble(6);
                rate3 = rs.getDouble(7);
                grade = rs.getString(8) != null ? rs.getString(8) : "";
                positionId = rs.getInt(9);
                empId = rs.getString(10) != null ? rs.getString(10) : "";
                candidateId = rs.getInt(11);
                if (!grade.equals("")) {
                    position += " | " + grade;
                }
                list.add(new TalentpoolInfo(dayrateId, position, fromDate, toDate, rate1, rate2, rate3, positionId, empId, candidateId));
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return list;
    }
    
     public String getApplytypeStatusbyId(int onlineflag) {
        String stval = "";
        if (onlineflag == 1) {
            stval = "Marine";
        } else if (onlineflag == 2) {
            stval = "Oil and Gas";
        }
        return stval;
    }
}
