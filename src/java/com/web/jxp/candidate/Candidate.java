package com.web.jxp.candidate;

import com.mysql.jdbc.Statement;
import com.web.jxp.base.Base;
import static com.web.jxp.base.Base.cipher;
import static com.web.jxp.base.Base.decipher;
import com.web.jxp.common.Common;
import static com.web.jxp.common.Common.*;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

public class Candidate extends Base {

    private static final Logger logger = Logger.getLogger(Class.class.getName());
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    public ArrayList getCandidateByName(String search, int statusIndex, int next, int count, int onlineflag, 
            int statustype, int assettypeIdIndex, int positionIdIndex)
    {
        ArrayList list = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT c.i_candidateid, DATE_FORMAT(c.ts_regdate, '%d-%b-%Y'), CONCAT_WS(' ', c.s_firstname, c.s_middlename, c.s_lastname), "); 
        sb.append("t_country.s_name, c.i_status, t_city.s_name, t_position.s_name, c.s_firstname, c.i_pass, c.i_online, t_grade.s_name ");
        sb.append("FROM t_candidate AS c ");
        sb.append("LEFT JOIN t_country ON (c.i_countryid = t_country.i_countryid) ");
        sb.append("LEFT JOIN t_city ON (c.i_cityid = t_city.i_cityid) ");
        sb.append("LEFT JOIN t_position ON (c.i_positionid = t_position.i_positionid) ");
        sb.append("LEFT JOIN t_grade ON (t_grade.i_gradeid = t_position.i_gradeid) ");
        sb.append("WHERE c.i_draftflag =0 ");
        if (statusIndex > 0) {
            sb.append("AND c.i_status =? ");
        }
        if (search != null && !search.equals("")) {
            sb.append("AND (CONCAT_WS(' ', c.s_firstname, c.s_middlename, c.s_lastname) LIKE ? OR CONCAT_WS(' ', c.s_firstname, c.s_lastname) LIKE ? OR c.i_candidateid LIKE ? ");
            sb.append("OR DATE_FORMAT(c.ts_regdate, '%d-%b-%Y') LIKE ? OR t_country.s_name LIKE ?  OR t_city.s_name LIKE ? OR t_position.s_name LIKE ?) ");
        }
        if (onlineflag > 0) {
            sb.append(" AND c.i_online = ? ");
        }
        if (assettypeIdIndex > 0) {
            sb.append("AND c.i_assettypeid = ? ");
        }
        if (positionIdIndex > 0) {
            sb.append("AND c.i_positionid = ? ");
        }
        if (statustype > 0) 
        {
            if(statustype == 1)
            {
               sb.append(" AND (c.i_vflag NOT IN (3,4) OR c.i_pass != 2) ");
            }
            else if(statustype == 2)
            {
                sb.append(" AND (c.i_vflag IN (3,4) AND c.i_pass = 2) ");
            }
        }        
        sb.append(" ORDER BY c.i_candidateid DESC ");
        if (count > 0) {
            sb.append("LIMIT ").append(next * count).append(", ").append(count);
        }
        String query = (sb.toString()).intern();
        sb.setLength(0);

        sb.append("SELECT COUNT(1) FROM t_candidate AS c ");
        sb.append("LEFT JOIN t_country ON (c.i_countryid = t_country.i_countryid) ");
        sb.append("LEFT JOIN t_city ON (c.i_cityid = t_city.i_cityid) ");
        sb.append("LEFT JOIN t_position ON (c.i_positionid = t_position.i_positionid) ");
        sb.append("WHERE c.i_draftflag =0 ");
        if (statusIndex > 0) {
            sb.append("AND c.i_status =? ");
        }
        if (search != null && !search.equals("")) {
            sb.append("AND (CONCAT_WS(' ', c.s_firstname, c.s_middlename, c.s_lastname) LIKE ? OR CONCAT_WS(' ', c.s_firstname, c.s_lastname) LIKE ? OR c.i_candidateid LIKE ? ");
            sb.append("OR DATE_FORMAT(c.ts_regdate, '%d-%b-%Y') LIKE ? OR t_country.s_name LIKE ?  OR t_city.s_name LIKE ? OR t_position.s_name LIKE ?) ");
        }
        if (onlineflag > 0) {
            sb.append("AND c.i_online = ? ");
        }
        if (assettypeIdIndex > 0) {
            sb.append("AND c.i_assettypeid = ? ");
        }
        if (positionIdIndex > 0) {
            sb.append("AND c.i_positionid = ? ");
        }
        if (statustype > 0) 
        {
            if(statustype == 1)
            {
               sb.append("AND (c.i_vflag NOT IN (3,4) OR c.i_pass != 2) ");
            }
            else if(statustype == 2)
            {
                sb.append("AND (c.i_vflag IN (3,4) AND c.i_pass = 2) ");
            }
        }
        String countquery = (sb.toString()).intern();
        sb.setLength(0);
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            int scc = 0;
            if (statusIndex > 0) {
                pstmt.setInt(++scc, statusIndex);
            }
            if (search != null && !search.equals("")) 
            {
                pstmt.setString(++scc, "%" + (search) + "%");
                pstmt.setString(++scc, "%" + (search) + "%");
                pstmt.setString(++scc, search.replaceFirst("^0+(?!$)", ""));
                pstmt.setString(++scc, "%" + (search) + "%");
                pstmt.setString(++scc, "%" + (search) + "%");
                pstmt.setString(++scc, "%" + (search) + "%");
                pstmt.setString(++scc, "%" + (search) + "%");
            }
            if (onlineflag > 0) {
                pstmt.setInt(++scc, onlineflag);
            }
            if (assettypeIdIndex > 0) {
                pstmt.setInt(++scc, assettypeIdIndex);
            }
            if (positionIdIndex > 0) {
                pstmt.setInt(++scc, positionIdIndex);
            }
            logger.info("getCandidateByName :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String name, countryName, date, cityName, positionName, firstName, onlineStatus, gradeName;
            int candidateId, status, pass;
            while (rs.next()) 
            {
                candidateId = rs.getInt(1);
                date = rs.getString(2) != null ? rs.getString(2) : "";
                name = rs.getString(3) != null ? rs.getString(3) : "";
                countryName = rs.getString(4) != null ? rs.getString(4) : "";
                status = rs.getInt(5);
                cityName = rs.getString(6)  != null ? rs.getString(6): "";
                positionName = rs.getString(7) != null ? rs.getString(7): "";
                firstName = rs.getString(8) != null ? rs.getString(8) : "";
                pass = rs.getInt(9);
                onlineStatus =  getonlineStatusbyId(rs.getInt(10));
                gradeName = rs.getString(11) != null ? rs.getString(11) : "";
                if (!positionName.equals("")) 
                {
                    if (!gradeName.equals("")) 
                    {
                        positionName += " | " + gradeName;
                    }
                }
                list.add(new CandidateInfo(candidateId, name, countryName, status, date, cityName, positionName, firstName, pass, onlineStatus));
            }
            rs.close();

            pstmt = conn.prepareStatement(countquery);
            scc = 0;
            if (statusIndex > 0) {
                pstmt.setInt(++scc, statusIndex);
            }
            if (search != null && !search.equals("")) 
            {
                pstmt.setString(++scc, "%" + (search) + "%");
                pstmt.setString(++scc, "%" + (search) + "%");
                pstmt.setString(++scc, search.replaceFirst("^0+(?!$)", ""));
                pstmt.setString(++scc, "%" + (search) + "%");
                pstmt.setString(++scc, "%" + (search) + "%");
                pstmt.setString(++scc, "%" + (search) + "%");
                pstmt.setString(++scc, "%" + (search) + "%");
            }
            if (onlineflag > 0) {
                pstmt.setInt(++scc, onlineflag);
            }
            if (assettypeIdIndex > 0) {
                pstmt.setInt(++scc, assettypeIdIndex);
            }
            if (positionIdIndex > 0) {
                pstmt.setInt(++scc, positionIdIndex);
            }
            print(this, "getCandidateByName :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                candidateId = rs.getInt(1);
                list.add(new CandidateInfo(candidateId, "", "", 0, "", "", "", "", 0,""));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return list;
    }

    public ArrayList getFinalRecord(ArrayList l, String colId, int tp) 
    {
        ArrayList list = null;
        HashMap record = null;
        CandidateInfo info;
        int total = 0;
        if (l != null) {
            total = l.size();
        }
        try {
            if (total > 0) {
                record = new HashMap();
                int i;
                for (i = 0; i < total; i++) {
                    info = (CandidateInfo) l.get(i);
                    record.put(getInfoValue(info, colId), info);
                }
            }
            Map map;
            if (colId.equals("1")) {
                map = sortById(record, tp);
            } else if (colId.equals("2")) {
                map = sortByDate(record, tp, "dd-MMM-yyyy");
            } else {
                map = sortByName(record, tp);
            }

            Iterator it = map.keySet().iterator();
            list = new ArrayList();
            CandidateInfo rInfo;
            while (it.hasNext()) {
                String key = (String) it.next();
                int i;
                for (i = 0; i < total; i++) {
                    rInfo = (CandidateInfo) l.get(i);
                    String str = getInfoValue(rInfo, colId);
                    if (str.equals(key)) {
                        list.add(rInfo);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // to sort search result
    public String getInfoValue(CandidateInfo info, String i)
    {
        String infoval = "";
        if (i != null && i.equals("1")) {
            infoval = info.getCandidateId() + "";
        } else if (i != null && i.equals("2")) {
            infoval = info.getDate() != null ? info.getDate() : "";
        } else if (i != null && i.equals("3")) {
            infoval = info.getName() != null ? info.getName() : "";
        } else if (i != null && i.equals("4")) {
            infoval = info.getCountryName() != null ? info.getCountryName() : "";
        } else if (i != null && i.equals("5")) {
            infoval = info.getCity() != null ? info.getCity() : "";
        } else if (i != null && i.equals("6")) {
            infoval = info.getPosition() != null ? info.getPosition() : "";
        } else if (i != null && i.equals("7")) {
            infoval = info.getOnlineStatus() != null ? info.getOnlineStatus() : "";
        }
        return infoval;
    }

    public Collection getPositions() 
    {
        Collection coll = new LinkedList();
        String query = ("SELECT i_positionid, s_name FROM t_position WHERE i_status = 1 ORDER BY s_name").intern();
        coll.add(new CandidateInfo(-1, "Select Position"));
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            rs = pstmt.executeQuery();
            int refId;
            String refName;
            while (rs.next()) 
            {
                refId = rs.getInt(1);
                refName = rs.getString(2) != null ? rs.getString(2): "";
                coll.add(new CandidateInfo(refId, refName));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return coll;
    }

    //for view
    public CandidateInfo getCandidateDetail(int candidateId) 
    {
        CandidateInfo info = null;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT CONCAT_WS(' ', c.s_firstname, c.s_middlename, c.s_lastname), DATE_FORMAT(c.d_dob, '%d-%b-%Y'), c.s_placeofbirth, c.s_email, c.s_code1, ");
        sb.append("c.s_contactno1, c.s_code2, c.s_contactno2, c.s_code3, c.s_contactno3, c.s_gender, t_country.s_name, n.s_nationality, c.s_address1line1, ");
        sb.append("c.s_address1line2, c.s_address2line1, c.s_address2line2, c.s_address2line3, c.s_nextofkin, t_relation.s_name, c.s_ecode1, c.s_econtactno1, c.s_ecode2, c.s_econtactno2, ");
        sb.append("t_maritialstatus.s_name, c.s_photofilename, t_position.s_name , c.s_address1line3, t_city.s_name,t_experiencedept.s_name,c.i_expectedsalary,t_currency.s_name,t1.ct, ");
        sb.append("c.s_firstname ,t_grade.s_name, c.i_pass, t_assettype.s_name, c.s_empno, c.i_online, c.s_othercity, c.i_applytype, c.s_middlename, ");
        sb.append("c.s_lastname, c.s_religion, p2.s_name , g2.s_name, t_state.s_name, c.s_pincode, ");
        sb.append("c.i_age, c.s_airport1, c.s_airport2, c.s_profile, c.s_skill1, c.s_skill2 ");
        sb.append("FROM t_candidate AS c ");
        sb.append("LEFT JOIN t_country ON (t_country.i_countryid = c.i_countryid) ");
        sb.append("LEFT JOIN t_country AS n ON (n.i_countryid = c.i_nationalityid) ");
        sb.append("LEFT JOIN t_city ON (t_city.i_cityid = c.i_cityid) ");
        sb.append("LEFT JOIN t_position ON (t_position.i_positionid = c.i_positionid) ");
        sb.append("LEFT JOIN t_relation ON (t_relation.i_relationid = c.i_relationid) ");
        sb.append("LEFT JOIN t_assettype ON (t_assettype.i_assettypeid = c.i_assettypeid) ");
        sb.append("LEFT JOIN t_maritialstatus ON (t_maritialstatus.i_maritialstatusid = c.i_maritialstatusid) ");
        sb.append("LEFT JOIN t_experiencedept ON (t_experiencedept.i_experiencedeptid = c.i_departmentid) ");
        sb.append("LEFT JOIN t_currency ON (t_currency.i_currencyid = c.i_currencyid) ");
        sb.append("LEFT JOIN t_position AS p2 ON (p2.i_positionid = c.i_positionid2) ");
        sb.append("LEFT JOIN t_grade AS g2 ON (g2.i_gradeid = p2.i_gradeid) ");
        sb.append("LEFT JOIN (SELECT i_candidateid, COUNT(1) AS ct FROM t_candidatefiles WHERE i_candidateid = ? GROUP BY i_candidateid) AS t1 ON (c.i_candidateid = t1.i_candidateid) ");
        sb.append("LEFT JOIN t_grade ON (t_grade.i_gradeid = t_position.i_gradeid) ");
        sb.append("LEFT JOIN t_state ON ( t_state.i_stateid= c.i_stateid ) ");
        sb.append("WHERE c.i_candidateid = ? ");
        String query = sb.toString().intern();
        sb.setLength(0);
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, candidateId);
            pstmt.setInt(2, candidateId);
            print(this, "getCandidateDetail :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            while (rs.next())
            {
                String name = rs.getString(1)  != null ? rs.getString(1) : "";
                String dob = rs.getString(2) != null ? rs.getString(2) : "";
                String place = rs.getString(3) != null ? rs.getString(3) : "";
                String email = rs.getString(4) != null ? rs.getString(4) : "";
                String contact1_code = rs.getString(5) != null ? rs.getString(5) : "";
                String contact1 = decipher(rs.getString(6) != null ? rs.getString(6) : "");
                String contact2_code = rs.getString(7) != null ? rs.getString(7) : "";
                String contact2 = decipher(rs.getString(8) != null ? rs.getString(8) : "");
                String contact3_code = rs.getString(9) != null ? rs.getString(9) : "";
                String contact3 = decipher(rs.getString(10) != null ? rs.getString(10) : "");
                String gender = rs.getString(11) != null ? rs.getString(11) : "";
                String countryName = rs.getString(12) != null ? rs.getString(12) : "";
                String nationality = rs.getString(13) != null ? rs.getString(13) : "";
                String address1line1 = rs.getString(14) != null ? rs.getString(14) : "";
                String address1line2 = rs.getString(15) != null ? rs.getString(15) : "";
                String address2line1 = rs.getString(16) != null ? rs.getString(16) : "";
                String address2line2 = rs.getString(17) != null ? rs.getString(17) : "";
                String address2line3 = rs.getString(18) != null ? rs.getString(18) : "";
                String nextofkin = decipher(rs.getString(19) != null ? rs.getString(19) : "");
                String relation = rs.getString(20) != null ? rs.getString(20) : "";
                String econtact1_code = rs.getString(21) != null ? rs.getString(21) : "";
                String econtact1 = decipher(rs.getString(22) != null ? rs.getString(22) : "");
                String econtact2_code = rs.getString(23) != null ? rs.getString(23) : "";
                String econtact2 = decipher(rs.getString(24) != null ? rs.getString(24) : "");
                String maritialstatus = rs.getString(25) != null ? rs.getString(25) : "";
                String photo = rs.getString(26) != null ? rs.getString(26) : "";
                String positionName = rs.getString(27) != null ? rs.getString(27) : "";
                String address1line3 = rs.getString(28) != null ? rs.getString(28) : "";
                String cityName = rs.getString(29) != null ? rs.getString(29) : "";
                String experiencedept = rs.getString(30) != null ? rs.getString(30) : "";
                int expectedsalary = rs.getInt(31);
                String currency = rs.getString(32) != null ? rs.getString(32) : "";
                int filecount = rs.getInt(33);
                String firstname = rs.getString(34) != null ? rs.getString(34) : "";
                String gradeName = rs.getString(35) != null ? rs.getString(35) : "";
                int pass = rs.getInt(36);
                String assettype = rs.getString(37) != null ? rs.getString(37) : "";
                String employeeId = rs.getString(38) != null ? rs.getString(38) : "";
                String  onlineStatus = getonlineStatusbyId(rs.getInt(39));
                String othercityName = rs.getString(40) != null ? rs.getString(40) : "";
                String applytypevalue = getApplytypeStatusbyId(rs.getInt(41));
                String middlename = rs.getString(42) != null ? rs.getString(42) : "";
                String lastname = rs.getString(43) != null ? rs.getString(43) : "";
                String religion = rs.getString(44) != null ? rs.getString(44) : "";
                String position2 = rs.getString(45) != null ? rs.getString(45) : "";
                String grade2 = rs.getString(46) != null ? rs.getString(46) : "";
                String state = rs.getString(47) != null ? rs.getString(47): "";
                String pincode = rs.getString(48) != null ? rs.getString(48): "";
                int age = rs.getInt(49);
                String airport1 = rs.getString(50) != null ? rs.getString(50) : "";
                String airport2 = rs.getString(51) != null ? rs.getString(51) : "";
                String profile = rs.getString(52) != null ? rs.getString(52) : "";
                String skill1 = rs.getString(53) != null ? rs.getString(53) : "";
                String skill2 = rs.getString(54) != null ? rs.getString(54) : "";
                if(cityName.equals(""))
                    cityName = othercityName;
                if (!positionName.equals("")) 
                {
                    if (!gradeName.equals("")) 
                    {
                        positionName += " | " + gradeName;
                    }
                }
                if (!position2.equals("")) 
                {
                    if (!grade2.equals("")) 
                    {
                        position2 += " | " + grade2;
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
                info = new CandidateInfo(candidateId, name, dob, place, email, contact1_code, contact1, 
                        contact2_code, contact2, contact3_code, contact3, gender, countryName,
                        nationality, address1line1, address1line2, address2line1, address2line2,
                        address2line3, nextofkin, relation, econtact1_code, econtact1, econtact2_code,
                        econtact2, maritialstatus, photo, positionName, address1line3, cityName, experiencedept, 
                        expectedsalary, currency, filecount, firstname, pass, assettype, employeeId, onlineStatus,
                        applytypevalue, middlename, lastname, religion, position2, state, pincode, age, airport1, 
                        airport2, profile, skill1, skill2);
            }
        } catch (Exception exception) {
            print(this, "getCandidateDetails :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return info;
    }
    
    public String getApplytypeStatusbyId(int onlineflag) 
    {
        String stval = "";
        
        if (onlineflag == 1) {
            stval = "Marine";
        } else if (onlineflag == 2) {
            stval = "Oil and Gas";
        }
        return stval;
    }
    
    public String getonlineStatusbyId(int onlineflag) 
    {
        String stval = "";

        if (onlineflag == 1) {
            stval = "Company";
        } else if (onlineflag == 2) {
            stval = "Self";
        }
        return stval;
    }

    public CandidateInfo getBankDetailByCandidateId(int bankdetid)
    {
        CandidateInfo info = null;
        String query = "SELECT s_bankname,s_savingaccno,s_branch,s_ifsccode,i_bankaccounttypeid,s_filename,i_primarybankid, s_accountholder FROM t_bankdetail WHERE i_bankdetid = ? ";
        try
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, bankdetid);
            print(this,"getBankDetailByCandidateId :" + pstmt.toString());
            rs = pstmt.executeQuery();
            while (rs.next()) 
            {
                String bankname = rs.getString(1) != null ? rs.getString(1) : "";
                String savingAccountNo = decipher(rs.getString(2) != null ? rs.getString(2) : "");
                String branch = rs.getString(3) != null ? rs.getString(3) : "";
                String ifsccode = decipher(rs.getString(4) != null ? rs.getString(4) : "");
                int accountTypeId = rs.getInt(5);
                String fileName = rs.getString(6) != null ? rs.getString(6) : "";
                int primarybankid = rs.getInt(7);
                String accountholder = rs.getString(8) != null ? rs.getString(8) : "";
                info = new CandidateInfo(bankdetid, bankname, savingAccountNo, branch, accountTypeId, ifsccode, fileName, primarybankid, accountholder);
            }
            rs.close();
            pstmt.close();

        } catch (Exception exception) {
            print(this, "getBankDetailByCandidateId :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return info;
    }

    public ArrayList getCandbankList(int candidateId) 
    {
        ArrayList list = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT t_bankdetail.i_bankdetid, t_bankdetail.s_bankname , t_bankaccounttype.s_name ,t_bankdetail.s_branch, t_bankdetail.s_ifsccode, t_bankdetail.s_filename , ");
        sb.append(" t_bankdetail.i_status , t_bankdetail.i_primarybankid, t_candidate.i_pass ");
        sb.append("FROM t_bankdetail LEFT JOIN t_bankaccounttype ON (t_bankdetail.i_bankaccounttypeid = t_bankaccounttype.i_bankaccounttypeid) ");
        sb.append("LEFT JOIN t_candidate ON (t_candidate.i_candidateid = t_bankdetail.i_candidateid) ");
        sb.append("WHERE t_bankdetail.i_candidateid = ? ");
        sb.append(" ORDER BY t_bankdetail.i_status, t_bankdetail.s_bankname ");
        String query = (sb.toString()).intern();
        sb.setLength(0);
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, candidateId);
            logger.info("getCandbankList :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String bankName, bankAccounttype, branch, ifsccode, filename;
            int candidatebankId, status, primaryBankId, pass;
            while (rs.next())
            {
                candidatebankId = rs.getInt(1);
                bankName = rs.getString(2) != null ? rs.getString(2) : "";
                bankAccounttype = rs.getString(3) != null ? rs.getString(3) : "";
                branch = rs.getString(4) != null ? rs.getString(4) : "";
                ifsccode = decipher(rs.getString(5) != null ? rs.getString(5) : "");
                filename = rs.getString(6) != null ? rs.getString(6) : "";
                status = rs.getInt(7);
                primaryBankId = rs.getInt(8);
                pass = rs.getInt(9);
                list.add(new CandidateInfo(candidatebankId, bankName, bankAccounttype, branch, ifsccode, filename, status, primaryBankId, pass));
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return list;
    }

    public int insertBankdetails(CandidateInfo info, int candidateId, int userId) 
    {
        int cc = 0;
        try 
        {
            if (info.getPrimarybankId() == 1) 
            {
                String primaryquery = "UPDATE t_bankdetail SET i_primarybankid = ? WHERE  i_candidateid = ? ";
                conn = getConnection();
                pstmt = conn.prepareStatement(primaryquery);
                pstmt.setInt(1, 0);
                pstmt.setInt(2, candidateId);
                pstmt.executeUpdate();
                pstmt.close();
                conn.close();
            }
            StringBuilder sb = new StringBuilder();
            sb.append("INSERT INTO t_bankdetail  ");
            sb.append("( s_bankname, s_savingaccno, s_branch, s_ifsccode,i_bankaccounttypeid,s_filename,  ");
            sb.append(" i_primarybankid, i_status , i_userid,i_candidateid, ts_regdate, ts_moddate, s_accountholder) ");
            sb.append("VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            String query1 = (sb.toString()).intern();
            sb.setLength(0);
            conn = getConnection();
            pstmt = conn.prepareStatement(query1, PreparedStatement.RETURN_GENERATED_KEYS);
            int scc = 0;
            pstmt.setString(++scc, (info.getBankName()));
            pstmt.setString(++scc, cipher(info.getSavingAccountNo()));
            pstmt.setString(++scc, (info.getBranch()));
            pstmt.setString(++scc, cipher(info.getIFSCCode()));
            pstmt.setInt(++scc, info.getAccountTypeId());
            pstmt.setString(++scc, info.getBkFilename());
            pstmt.setInt(++scc, info.getPrimarybankId());
            pstmt.setInt(++scc, info.getStatus());
            pstmt.setInt(++scc, userId);
            pstmt.setInt(++scc, candidateId);
            pstmt.setString(++scc, currDate1());
            pstmt.setString(++scc, currDate1());
            pstmt.setString(++scc, info.getAccountHolder());
            print(this, "createbank :: " + pstmt.toString());
            pstmt.executeUpdate();
            rs = pstmt.getGeneratedKeys();
            while (rs.next()) {
                cc = rs.getInt(1);
            }
            updateverification(9, userId, candidateId, cc);
        } catch (Exception exception) {
            print(this, "createBankdetail :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return cc;
    }

    public int checkDuplicacyBank(int candidateId, int bankdetailId, String bankname, String savingaccno) 
    {
        int ck = 0;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT i_bankdetid FROM t_bankdetail WHERE i_candidateid = ?  AND s_bankname = ? AND s_savingaccno = ? AND  i_status  in (1, 2)");
        if (bankdetailId > 0) {
            sb.append(" AND i_bankdetid != ? ");
        }
        String query = (sb.toString()).intern();
        sb.setLength(0);
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            int scc = 0;
            pstmt.setInt(++scc, candidateId);
            pstmt.setString(++scc, bankname);
            pstmt.setString(++scc, cipher(savingaccno));
            if (bankdetailId > 0) {
                pstmt.setInt(++scc, bankdetailId);
            }
            print(this, "checkDuplicacy :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ck = 1;
            }
        } catch (Exception exception) {
            print(this, "checkcandbankDuplicacy :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return ck;
    }

    public int updateBankdetails(CandidateInfo info, int userId, int candidateId, int bankdetailId)
    {
        StringBuilder sb = new StringBuilder();
        int cc = 0;
        try 
        {
            conn = getConnection();
            if (info.getPrimarybankId() == 1)
            {
                String primaryquery = "UPDATE t_bankdetail SET i_primarybankid = ? WHERE  i_candidateid = ? AND i_bankdetid != ? ";
                pstmt = conn.prepareStatement(primaryquery);
                pstmt.setInt(1, 0);
                pstmt.setInt(2, candidateId);
                pstmt.setInt(3, bankdetailId);
                pstmt.executeUpdate();
                pstmt.close();
            }
            sb.append("UPDATE t_bankdetail SET s_bankname = ?,s_savingaccno = ? ,s_branch = ?,s_ifsccode = ?, ");
            sb.append(" i_bankaccounttypeid = ?,i_primarybankid = ?, s_accountholder = ?, ");
            if (info.getBkFilename() != null && !info.getBkFilename().equals("")) {
                sb.append("s_filename = ? ,");
            }
            sb.append(" i_userid = ?,ts_moddate = ?  WHERE  i_candidateid = ? AND i_bankdetid = ? ");
            String doctypequery = sb.toString().intern();
            sb.setLength(0);
            int scc = 0;
            pstmt = conn.prepareStatement(doctypequery);
            pstmt.setString(++scc, info.getBankName());
            pstmt.setString(++scc, cipher(info.getSavingAccountNo()));
            pstmt.setString(++scc, info.getBranch());
            pstmt.setString(++scc, cipher(info.getIFSCCode()));
            pstmt.setInt(++scc, info.getAccountTypeId());
            pstmt.setInt(++scc, info.getPrimarybankId());
            pstmt.setString(++scc, info.getAccountHolder());
            if (info.getBkFilename() != null && !info.getBkFilename().equals("")) {
                pstmt.setString(++scc, info.getBkFilename());
            }
            pstmt.setInt(++scc, userId);
            pstmt.setString(++scc, currDate1());
            pstmt.setInt(++scc, candidateId);
            pstmt.setInt(++scc, bankdetailId);
            cc = pstmt.executeUpdate();
            pstmt.close();
            updateverification(9, userId, candidateId, bankdetailId);
        } catch (Exception exception) {
            print(this, "updateBankdetailid :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return cc;
    }

    public int deletebank(int candidateId, int bankdetailId, int userId, int status, String ipAddrStr, String iplocal) 
    {
        int cc = 0;
        try 
        {
            conn = getConnection();
            StringBuilder sb = new StringBuilder();
            sb.append("UPDATE t_bankdetail SET ");
            sb.append("i_status = ?, ");
            sb.append("i_userid = ?, ");
            sb.append("ts_moddate = ? ");
            sb.append("WHERE i_bankdetid = ? AND i_candidateid = ? ");
            String query = (sb.toString()).intern();
            sb.setLength(0);
            pstmt = conn.prepareStatement(query);
            int scc = 0;
            if (status == 1) {
                pstmt.setInt(++scc, 2);
            } else {
                pstmt.setInt(++scc, 1);
            }
            pstmt.setInt(++scc, userId);
            pstmt.setString(++scc, currDate1());
            pstmt.setInt(++scc, bankdetailId);
            pstmt.setInt(++scc, candidateId);
            print(this,"deletebank :: " + pstmt.toString());
            cc = pstmt.executeUpdate();

            updateverification(9, userId, candidateId, bankdetailId);
        } catch (Exception exception) {
            print(this, "deletebank :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        createHistoryAccess(null, userId, ipAddrStr, iplocal, "Data Deleted", 7, bankdetailId);
        return cc;
    }

    public int createCandidate(CandidateInfo info, int userId, int type) 
    {
        int candidateId = 0;
        try 
        {
            StringBuilder sb = new StringBuilder();
            sb.append("INSERT INTO t_candidate (s_firstname, s_middlename, s_lastname, d_dob, s_placeofbirth, s_email, s_code1, s_code2, s_contactno1, s_contactno2, s_code3, ");
            sb.append("s_contactno3, s_gender, i_countryid, i_nationalityid, s_address1line1, s_address1line2, s_address2line1, s_address2line2, s_address2line3, s_nextofkin, ");
            sb.append("i_relationid, s_ecode1, s_econtactno1, s_ecode2, s_econtactno2, i_maritialstatusid, s_photofilename, s_address1line3, i_departmentid, i_currencyid, ");
            sb.append("i_positionid, i_expectedsalary, i_cityid, i_status, i_userid, ts_regdate, ts_moddate, i_assettypeid, s_empno, i_online,s_othercity, i_applytype, s_religion, ");
            sb.append("i_positionid2, i_stateid, s_pincode, i_age, s_airport1, s_airport2, s_profile, s_skill1, s_skill2 ) ");
            sb.append("VALUES (?, ?, ?, ?, ?,   ?, ?, ?, ?, ?,   ?, ?, ?, ?, ?,   ?, ?, ?, ?, ?,   ?, ?, ?, ?, ?, ");
            sb.append(" ?, ?, ?, ?, ?,   ?, ?, ?, ?, ?,   ?, ?, ?, ?, ?,   ?, ?, ?, ?, ?,   ?, ?,?, ?, ?,   ?, ?, ? )");
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
            pstmt.setString(++scc, info.getEmailId());
            pstmt.setString(++scc, info.getCode1Id());
            pstmt.setString(++scc, info.getCode2Id());
            pstmt.setString(++scc, cipher(info.getContactno1()));
            pstmt.setString(++scc, cipher(info.getContactno2()));
            pstmt.setString(++scc, info.getCode3Id());
            pstmt.setString(++scc, cipher(info.getContactno3()));
            pstmt.setString(++scc, info.getGender());
            pstmt.setInt(++scc, info.getCountryId());
            pstmt.setInt(++scc, info.getNationalityId());
            pstmt.setString(++scc, info.getAddress1line1());
            pstmt.setString(++scc, info.getAddress1line2());
            pstmt.setString(++scc, info.getAddress2line1());
            pstmt.setString(++scc, info.getAddress2line2());
            pstmt.setString(++scc, info.getAddress2line3());
            pstmt.setString(++scc, cipher(info.getNextofkin()));
            pstmt.setInt(++scc, info.getRelationId());
            pstmt.setString(++scc, info.getEcode1Id());
            pstmt.setString(++scc, cipher(info.getEcontactno1()));
            pstmt.setString(++scc, info.getEcode2Id());
            pstmt.setString(++scc, cipher(info.getEcontactno2()));
            pstmt.setInt(++scc, info.getMaritalstatusId());
            pstmt.setString(++scc, info.getPhotofilename());
            pstmt.setString(++scc, info.getAddress1line3());
            pstmt.setInt(++scc, info.getDepartmentId());
            pstmt.setInt(++scc, info.getCurrencyId());
            pstmt.setInt(++scc, info.getPositionId());
            pstmt.setInt(++scc, info.getExpectedsalary());
            pstmt.setInt(++scc, info.getCityId());
            pstmt.setInt(++scc, info.getStatus());
            pstmt.setInt(++scc, userId);
            pstmt.setString(++scc, currDate1());
            pstmt.setString(++scc, currDate1());
            pstmt.setInt(++scc, info.getAssettypeId());
            pstmt.setString(++scc, info.getEmployeeId());
            pstmt.setInt(++scc, type);
            pstmt.setString(++scc, info.getOthercity());
            pstmt.setInt(++scc, info.getApplytype());
            pstmt.setString(++scc, info.getReligion());
            pstmt.setInt(++scc, info.getPositionId2());
            pstmt.setInt(++scc, info.getStateId());
            pstmt.setString(++scc, info.getPinCode());
            pstmt.setInt(++scc, info.getAge());
            pstmt.setString(++scc, info.getAirport1());
            pstmt.setString(++scc, info.getAirport2());
            pstmt.setString(++scc, info.getProfile());
            pstmt.setString(++scc, info.getSkill1());
            pstmt.setString(++scc, info.getSkill2());
            print(this, "createCandidate :: " + pstmt.toString());
            pstmt.executeUpdate();
            rs = pstmt.getGeneratedKeys();
            while (rs.next()) {
                candidateId = rs.getInt(1);
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

    public int checkDuplicacy(int candidateId, String emailId) 
    {
        int ck = 0;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT i_candidateid FROM t_candidate WHERE s_email = ?  AND i_status in (1, 2)");
        if (candidateId > 0) {
            sb.append(" AND i_candidateid != ? ");
        }
        String query = (sb.toString()).intern();
        sb.setLength(0);
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            int scc = 0;
            pstmt.setString(++scc, emailId);
            if (candidateId > 0) {
                pstmt.setInt(++scc, candidateId);
            }
            //print(this,"checkDuplicacy :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ck = 1;
            }
        } catch (Exception exception) {
            print(this, "checkDuplicacy :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return ck;
    }

    public int updateCandidate(CandidateInfo info, int candidateId, int userId, int positionIdhidden) 
    {
        int cc = 0;
        try 
        {
            conn = getConnection();
            if (positionIdhidden != info.getPositionId()) 
            {
                String query1 = "UPDATE t_cassessment SET i_active = 0 WHERE i_candidateid =? ";
                pstmt = conn.prepareStatement(query1);
                pstmt.setInt(1, candidateId);
                pstmt.executeUpdate();
            }
            StringBuilder sb = new StringBuilder();
            sb.append("UPDATE t_candidate SET ");
            sb.append("s_firstname =?, ");
            sb.append("s_middlename =?, ");
            sb.append("s_lastname =?, ");
            sb.append("d_dob =?, ");
            sb.append("s_placeofbirth =?, ");
            sb.append("s_email =?, ");
            sb.append("s_code1 =?, ");
            sb.append("s_code2 =?, ");
            sb.append("s_contactno1 =?, ");
            sb.append("s_contactno2 =?, ");
            sb.append("s_code3 =?, ");
            sb.append("s_contactno3 =?, ");
            sb.append("s_gender =?, ");
            sb.append("i_countryid =?, ");
            sb.append("i_nationalityid =?, ");
            sb.append("s_address1line1 =?, ");
            sb.append("s_address1line2 =?, ");
            sb.append("s_address2line1 =?, ");
            sb.append("s_address2line2 =?, ");
            sb.append("s_address2line3 =?, ");
            sb.append("s_nextofkin =?, ");
            sb.append("i_relationid =?, ");
            sb.append("s_ecode1 =?, ");
            sb.append("s_econtactno1 =?, ");
            sb.append("s_ecode2 =?, ");
            sb.append("s_econtactno2 =?, ");
            sb.append("i_maritialstatusid =?, ");
            if (info.getPhotofilename() != null && !info.getPhotofilename().equals("")) {
                sb.append("s_photofilename =?, ");
            }
            sb.append("s_address1line3 =?, ");
            sb.append("i_departmentid =?, ");
            sb.append("i_positionid =?, ");
            sb.append("i_currencyid =?, ");
            sb.append("i_expectedsalary =?, ");
            sb.append("i_cityid =?, ");
            sb.append("i_status =?, ");
            sb.append("i_userid =?, ");
            if (positionIdhidden != info.getPositionId()) {
                sb.append("i_pass =1, ");
                sb.append("i_aflag =0, ");
            }
            sb.append("ts_moddate =?, ");
            sb.append("i_assettypeid =?, ");
            sb.append("s_empno =?, ");
            sb.append("s_othercity = ?, ");
            sb.append("i_applytype = ?, ");
            sb.append("s_religion = ?, ");
            sb.append("i_positionid2 =?, ");
            sb.append("i_stateid =?, ");
            sb.append("s_pincode =?, ");
            sb.append("i_age = ?, ");
            sb.append("s_airport1 = ?, ");
            sb.append("s_airport2 = ? ");
            sb.append("s_profile = ?, ");
            sb.append("s_skill1 = ?, ");
            sb.append("s_skill2 = ? ");
            sb.append("WHERE i_candidateid =? ");
            String query = (sb.toString()).intern();
            sb.setLength(0);
            pstmt = conn.prepareStatement(query);
            int scc = 0;
            pstmt.setString(++scc, info.getFirstname());
            pstmt.setString(++scc, info.getMiddlename());
            pstmt.setString(++scc, info.getLastname());
            pstmt.setString(++scc, changeDate1(info.getDob()));
            pstmt.setString(++scc, info.getPlaceofbirth());
            pstmt.setString(++scc, info.getEmailId());
            pstmt.setString(++scc, info.getCode1Id());
            pstmt.setString(++scc, info.getCode2Id());
            pstmt.setString(++scc, cipher(info.getContactno1()));
            pstmt.setString(++scc, cipher(info.getContactno2()));
            pstmt.setString(++scc, info.getCode3Id());
            pstmt.setString(++scc, cipher(info.getContactno3()));
            pstmt.setString(++scc, info.getGender());
            pstmt.setInt(++scc, info.getCountryId());
            pstmt.setInt(++scc, info.getNationalityId());
            pstmt.setString(++scc, info.getAddress1line1());
            pstmt.setString(++scc, info.getAddress1line2());
            pstmt.setString(++scc, info.getAddress2line1());
            pstmt.setString(++scc, info.getAddress2line2());
            pstmt.setString(++scc, info.getAddress2line3());
            pstmt.setString(++scc, cipher(info.getNextofkin()));
            pstmt.setInt(++scc, info.getRelationId());
            pstmt.setString(++scc, info.getEcode1Id());
            pstmt.setString(++scc, cipher(info.getEcontactno1()));
            pstmt.setString(++scc, info.getEcode2Id());
            pstmt.setString(++scc, cipher(info.getEcontactno2()));
            pstmt.setInt(++scc, info.getMaritalstatusId());
            if (info.getPhotofilename() != null && !info.getPhotofilename().equals("")) 
            {
                pstmt.setString(++scc, info.getPhotofilename());
            }
            pstmt.setString(++scc, info.getAddress1line3());
            pstmt.setInt(++scc, info.getDepartmentId());
            pstmt.setInt(++scc, info.getPositionId());
            pstmt.setInt(++scc, info.getCurrencyId());
            pstmt.setInt(++scc, info.getExpectedsalary());
            pstmt.setInt(++scc, info.getCityId());
            pstmt.setInt(++scc, info.getStatus());
            pstmt.setInt(++scc, userId);
            pstmt.setString(++scc, currDate1());
            pstmt.setInt(++scc, info.getAssettypeId());
            pstmt.setString(++scc, info.getEmployeeId());
            pstmt.setString(++scc, info.getOthercity());
            pstmt.setInt(++scc, info.getApplytype());
            pstmt.setString(++scc, info.getReligion());
            pstmt.setInt(++scc, info.getPositionId2());
            pstmt.setInt(++scc, info.getStateId());
            pstmt.setString(++scc, info.getPinCode());
            pstmt.setInt(++scc, info.getAge());
            pstmt.setString(++scc, info.getAirport1());
            pstmt.setString(++scc, info.getAirport2());
            pstmt.setString(++scc, info.getProfile());
            pstmt.setString(++scc, info.getSkill1());
            pstmt.setString(++scc, info.getSkill2());
            pstmt.setInt(++scc, candidateId);
            print(this, "updateCandidate :: " + pstmt.toString());
            cc = pstmt.executeUpdate();

            updateverification(1, userId, candidateId, 0);
        } catch (Exception exception) {
            print(this, "updateCandidate :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return cc;
    }

    public int updateCandidateHomeForm(CandidateInfo info, int candidateId, int userId, int positionIdhidden) 
    {
        int cc = 0;
        try 
        {
            conn = getConnection();
            if (positionIdhidden != info.getPositionId())
            {
                String query1 = "UPDATE t_cassessment SET i_active = 0 WHERE i_candidateid =? ";
                pstmt = conn.prepareStatement(query1);
                pstmt.setInt(1, candidateId);
                print(this, "UPDATE t_cassessment :: " + pstmt.toString());
                pstmt.executeUpdate();
            }
            StringBuilder sb = new StringBuilder();
            sb.append("UPDATE t_candidate SET ");
            sb.append("s_firstname =?, ");
            sb.append("s_middlename =?, ");
            sb.append("s_lastname =?, ");
            sb.append("d_dob =?, ");
            sb.append("s_email =?, ");
            sb.append("s_code1 =?, ");
            sb.append("s_contactno1 =?, ");
            sb.append("s_gender =?, ");
            sb.append("i_countryid =?, ");
            sb.append("i_nationalityid =?, ");
            sb.append("s_address1line1 =?, ");
            sb.append("s_address1line2 =?, ");
            sb.append("i_maritialstatusid =?, ");
            if (info.getPhotofilename() != null && !info.getPhotofilename().equals("")) 
            {
                sb.append("s_photofilename =?, ");
            }
            sb.append("s_address1line3 =?, ");
            sb.append("i_positionid =?, ");
            sb.append("i_currencyid =?, ");
            sb.append("i_expectedsalary =?, ");
            sb.append("i_cityid =?, ");
            sb.append("i_status =?, ");
            sb.append("i_userid =?, ");
            if (positionIdhidden != info.getPositionId())
            {
                sb.append("i_pass =1, ");
                sb.append("i_aflag =0, ");
            }
            sb.append("ts_moddate =?, ");
            sb.append("i_assettypeid =?, ");
            sb.append("s_othercity = ?, ");
            sb.append("i_applytype = ?, ");
            sb.append("i_stateid = ?, ");
            sb.append("s_pincode = ?, ");
            sb.append("i_age = ?, ");
            sb.append("s_airport1 = ?, ");
            sb.append("s_airport2 = ? ");
            sb.append("WHERE i_candidateid =? ");
            String query = (sb.toString()).intern();
            sb.setLength(0);
            pstmt = conn.prepareStatement(query);
            int scc = 0;
            pstmt.setString(++scc, info.getFirstname());
            pstmt.setString(++scc, info.getMiddlename());
            pstmt.setString(++scc, info.getLastname());
            pstmt.setString(++scc, changeDate1(info.getDob()));
            pstmt.setString(++scc, info.getEmailId());
            pstmt.setString(++scc, info.getCode1Id());
            pstmt.setString(++scc, cipher(info.getContactno1()));
            pstmt.setString(++scc, info.getGender());
            pstmt.setInt(++scc, info.getCountryId());
            pstmt.setInt(++scc, info.getNationalityId());
            pstmt.setString(++scc, info.getAddress1line1());
            pstmt.setString(++scc, info.getAddress1line2());
            pstmt.setInt(++scc, info.getMaritalstatusId());
            if (info.getPhotofilename() != null && !info.getPhotofilename().equals("")) 
            {
                pstmt.setString(++scc, info.getPhotofilename());
            }
            pstmt.setString(++scc, info.getAddress1line3());
            pstmt.setInt(++scc, info.getPositionId());
            pstmt.setInt(++scc, info.getCurrencyId());
            pstmt.setInt(++scc, info.getExpectedsalary());
            pstmt.setInt(++scc, info.getCityId());
            pstmt.setInt(++scc, info.getStatus());
            pstmt.setInt(++scc, userId);
            pstmt.setString(++scc, currDate1());
            pstmt.setInt(++scc, info.getAssettypeId());
            pstmt.setString(++scc, info.getOthercity());
            pstmt.setInt(++scc, info.getApplytype());
            pstmt.setInt(++scc, info.getStateId());
            pstmt.setString(++scc, info.getPinCode());
            pstmt.setInt(++scc, info.getAge());
            pstmt.setString(++scc, info.getAirport1());
            pstmt.setString(++scc, info.getAirport2());
            pstmt.setInt(++scc, candidateId);
            print(this, "updateCandidateHomeForm :: " + pstmt.toString());
            cc = pstmt.executeUpdate();
            updateverification(1, userId, candidateId, 0);
        } catch (Exception exception) {
            print(this, "updateCandidateHomeForm :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return cc;
    }

    public CandidateInfo getCandidateDetailById(int candidateId) 
    {
        CandidateInfo info = null;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT s_firstname, s_middlename, s_lastname, DATE_FORMAT(d_dob, '%d-%b-%Y'), s_placeofbirth, s_email, s_code1, s_code2, s_contactno1, ");
        sb.append("s_contactno2, s_code3, s_contactno3, s_gender, t_candidate.i_countryid, i_nationalityid, s_address1line1, s_address1line2, s_address2line1, ");
        sb.append("s_address2line2, s_address2line3, s_nextofkin, i_relationid, s_ecode1, s_econtactno1, s_ecode2, s_econtactno2, i_maritialstatusid, ");
        sb.append("s_photofilename ,s_address1line3, i_departmentid, i_positionid, i_currencyid, i_expectedsalary, t_candidate.i_status ,t_city.s_name, ");
        sb.append("t_candidate.i_cityid, t1.ct, t_candidate.i_assettypeid, t_candidate.s_empno,t_candidate.s_othercity,t_candidate.i_applytype, ");
        sb.append("t_candidate.s_religion, t_candidate.i_positionid2, t_candidate.i_stateid, t_candidate.s_pincode, ");
        sb.append("t_candidate.i_age, t_candidate.s_airport1, t_candidate.s_airport2, t_candidate.s_profile, t_candidate.s_skill1, t_candidate.s_skill2 ");
        sb.append("FROM t_candidate ");
        sb.append("LEFT JOIN t_city  ON (t_city.i_cityid = t_candidate.i_cityid) ");
        sb.append("LEFT JOIN (SELECT i_candidateid, COUNT(1) AS ct FROM t_candidatefiles WHERE t_candidatefiles.i_candidateid = ? GROUP BY i_candidateid) AS t1 ON (t_candidate.i_candidateid = t1.i_candidateid) ");
        sb.append("WHERE t_candidate.i_candidateid = ? ");
        String query = sb.toString().intern();
        sb.setLength(0);
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, candidateId);
            pstmt.setInt(2, candidateId);
            print(this, "getCandidateDetailById ::::::::: " + pstmt.toString());
            rs = pstmt.executeQuery();
            while (rs.next()) 
            {
                String firstname = rs.getString(1) != null ? rs.getString(1) : "";
                String middlename = rs.getString(2) != null ? rs.getString(2) : "";
                String lastname = rs.getString(3) != null ? rs.getString(3) : "";
                String dob = rs.getString(4) != null ? rs.getString(4) : "";
                String placeofbirth = rs.getString(5) != null ? rs.getString(5) : "";
                String email = rs.getString(6) != null ? rs.getString(6) : "";
                String code1 = rs.getString(7) != null ? rs.getString(7) : "";
                String code2 = rs.getString(8) != null ? rs.getString(8) : "";
                String contactno1 = decipher(rs.getString(9) != null ? rs.getString(9) : "");
                String contactno2 = decipher(rs.getString(10) != null ? rs.getString(10) : "");
                String code3 = rs.getString(11) != null ? rs.getString(11) : "";
                String contactno3 = decipher(rs.getString(12) != null ? rs.getString(12) : "");
                String gender = rs.getString(13) != null ? rs.getString(13) : "";
                int countryid = rs.getInt(14);
                int nationalityid = rs.getInt(15);
                String address1line1 = rs.getString(16) != null ? rs.getString(16) : "";
                String address1line2 = rs.getString(17) != null ? rs.getString(17) : "";
                String address2line1 = rs.getString(18) != null ? rs.getString(18) : "";
                String address2line2 = rs.getString(19) != null ? rs.getString(19) : "";
                String address2line3 = rs.getString(20) != null ? rs.getString(20) : "";
                String nextofkin = decipher(rs.getString(21) != null ? rs.getString(21) : "");
                int relationid = rs.getInt(22);
                String ecode1 = rs.getString(23) != null ? rs.getString(23) : "";
                String econtactno1 = decipher(rs.getString(24) != null ? rs.getString(24) : "");
                String ecode2 = rs.getString(25) != null ? rs.getString(25) : "";
                String econtactno2 = decipher(rs.getString(26) != null ? rs.getString(26) : "");
                int maritialstatusid = rs.getInt(27);
                String photofilename = rs.getString(28) != null ? rs.getString(28) : "";
                String address1line3 = rs.getString(29) != null ? rs.getString(29) : "";
                int departmentId = rs.getInt(30);
                int positionId = rs.getInt(31);
                int currencyId = rs.getInt(32);
                int expectedsalary = rs.getInt(33);
                int status = rs.getInt(34);
                String cityName = rs.getString(35) != null ? rs.getString(35) : "";
                int cityId = rs.getInt(36);
                int filecount = rs.getInt(37);
                int assettypeId = rs.getInt(38);
                String employeeId = rs.getString(39) != null ? rs.getString(36) : "";
                String othercityName = rs.getString(40) != null ? rs.getString(40) : "";
                int applytype = rs.getInt(41);
                String religion = rs.getString(42) != null ? rs.getString(42) : "";
                int positionId2 = rs.getInt(43);
                int stateId = rs.getInt(44);
                String pincode = rs.getString(45) != null ? rs.getString(45) : "";
                int age = rs.getInt(46);
                String airport1 = rs.getString(47) != null ? rs.getString(47) : "";
                String airport2 = rs.getString(48) != null ? rs.getString(48) : "";
                String profile = rs.getString(49) != null ? rs.getString(49) : "";
                String skill1 = rs.getString(50) != null ? rs.getString(50) : "";
                String skill2 = rs.getString(51) != null ? rs.getString(51) : "";
                
                if(cityName.equals(""))
                    cityName = othercityName;
                info = new CandidateInfo(firstname, middlename, lastname, dob, placeofbirth, email, 
                        code1, code2, contactno1, contactno2, code3, contactno3, gender, countryid, 
                        nationalityid, address1line1, address1line2, address2line1, address2line2, address2line3,
                        nextofkin, relationid, ecode1, econtactno1, ecode2, econtactno2, maritialstatusid, 
                        photofilename, address1line3, departmentId, positionId, currencyId, expectedsalary, 
                        status, cityName, cityId, filecount, assettypeId, employeeId, applytype, religion,
                        positionId2, stateId, pincode, age, airport1, airport2, profile, skill1, skill2);
            }
        } catch (Exception exception) {
            print(this, "getCandidateDetailById :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return info;
    }
    
    public Collection getPositionassettypes(int assettypeId)
    {
        Collection coll = new LinkedList();
        if (assettypeId > 0)
        {
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT t_position.i_positionid, t_position.s_name, t_grade.s_name FROM t_position ");
            sb.append(" LEFT JOIN t_grade ON (t_grade.i_gradeid = t_position.i_gradeid) ");
            sb.append(" WHERE t_position.i_assettypeid = ? ORDER BY t_position.s_name, t_grade.s_name ");
            String query = sb.toString();
            sb.setLength(0);
            coll.add(new CandidateInfo(-1, " Select Position | Rank "));
            try 
            {
                conn = getConnection();
                pstmt = conn.prepareStatement(query);
                pstmt.setInt(1, assettypeId);
                logger.info("getPositionassettypes :: " + pstmt.toString());
                rs = pstmt.executeQuery();
                int ddlValue;
                String ddlLabel = "", grade, position;
                while (rs.next()) 
                {
                    ddlValue = rs.getInt(1);
                    position = rs.getString(2) != null ? rs.getString(2) : "";
                    grade = rs.getString(3) != null ? rs.getString(3) : "";
                    ddlLabel = position;
                    if (!grade.equals("")) 
                    {
                        ddlLabel += " | " + grade;
                    }
                    coll.add(new CandidateInfo(ddlValue, ddlLabel));
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                close(conn, pstmt, rs);
            }
        } else {
            coll.add(new CandidateInfo(-1, " Select Position | Rank "));
        }
        return coll;
    }
    
    public Collection getPosition2assettypes(int assettypeId, int positionId)
    {
        Collection coll = new LinkedList();
        if (assettypeId > 0)
        {
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT t_position.i_positionid, t_position.s_name, t_grade.s_name FROM t_position ");
            sb.append(" LEFT JOIN t_grade ON (t_grade.i_gradeid = t_position.i_gradeid) ");
            sb.append(" WHERE t_position.i_assettypeid = ? AND t_position.i_positionid != ? ORDER BY t_position.s_name, t_grade.s_name ");
            String query = sb.toString();
            sb.setLength(0);
            coll.add(new CandidateInfo(-1, " Select Position | Rank "));
            try 
            {
                conn = getConnection();
                pstmt = conn.prepareStatement(query);
                pstmt.setInt(1, assettypeId);
                pstmt.setInt(2, positionId);
                Common.print(this, "getPosition2assettypes :: " + pstmt.toString());
                rs = pstmt.executeQuery();
                int ddlValue;
                String ddlLabel = "", grade, position;
                while (rs.next()) 
                {
                    ddlValue = rs.getInt(1);
                    position = rs.getString(2) != null ? rs.getString(2) : "";
                    grade = rs.getString(3) != null ? rs.getString(3) : "";
                    ddlLabel = position;
                    if (!grade.equals("")) 
                    {
                        ddlLabel += " | " + grade;
                    }
                    coll.add(new CandidateInfo(ddlValue, ddlLabel));
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                close(conn, pstmt, rs);
            }
        } else {
            coll.add(new CandidateInfo(-1, " Select Position | Rank "));
        }
        return coll;
    }

    public int checkDuplicacyLang(int candidateId, int languageId, int candidateLangId) 
    {
        int ck = 0;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT i_candlangid FROM t_candlang WHERE i_candidateid = ?  AND i_languageid = ? AND  i_status  in (1, 2)");
        if (candidateLangId > 0) {
            sb.append(" AND i_candlangid != ? ");
        }
        String query = (sb.toString()).intern();
        sb.setLength(0);
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            int scc = 0;
            pstmt.setInt(++scc, candidateId);
            pstmt.setInt(++scc, languageId);
            if (candidateLangId > 0) {
                pstmt.setInt(++scc, candidateLangId);
            }
            //print(this,"checkDuplicacy :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ck = 1;
            }
        } catch (Exception exception) {
            print(this, "checkcandlangDuplicacy :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return ck;
    }

    public ArrayList getCandlanguageList(int candidateId) 
    {
        ArrayList list = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT t_candlang.i_candlangid, t_language.s_name , t_proficiency.s_name , t_candlang.s_filename ,t_candlang.i_status, t_candidate.i_pass ");
        sb.append("FROM t_candlang ");
        sb.append("LEFT JOIN t_language ON (t_candlang.i_languageid = t_language.i_languageid) ");
        sb.append("LEFT JOIN t_candidate ON (t_candidate.i_candidateid = t_candlang.i_candidateid) ");
        sb.append("LEFT JOIN t_proficiency ON (t_candlang.i_proficiencyid = t_proficiency.i_proficiencyid) ");
        sb.append("WHERE t_candlang.i_candidateid = ? ");
        sb.append("ORDER BY t_candlang.i_status, t_language.s_name ");
        String query = (sb.toString()).intern();
        sb.setLength(0);
        try
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, candidateId);
            logger.info("getCandlanguageList :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String langname, proficiencyname, filename;
            int candidatelangId, status, pass;
            while (rs.next())
            {
                candidatelangId = rs.getInt(1);
                langname = rs.getString(2) != null ? rs.getString(2) : "";
                proficiencyname = rs.getString(3) != null ? rs.getString(3) : "";
                filename = rs.getString(4) != null ? rs.getString(4) : "";
                status = rs.getInt(5);
                pass = rs.getInt(6);
                list.add(new CandidateInfo(candidatelangId, langname, proficiencyname, filename, status, pass));
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return list;
    }

    public CandidateInfo getcandlangForModify(int candidateLangId) 
    {
        CandidateInfo info = null;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT i_languageid, i_proficiencyid, s_filename ");
        sb.append("FROM t_candlang WHERE i_candlangid = ? ");
        String query = (sb.toString()).intern();
        sb.setLength(0);
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, candidateLangId);
            logger.info("getcandlangForModify :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String filename;
            int languageId, proficiencyId;
            while (rs.next()) 
            {
                languageId = rs.getInt(1);
                proficiencyId = rs.getInt(2);
                filename = rs.getString(3) != null ? rs.getString(3) : "";
                info = new CandidateInfo(languageId, proficiencyId, filename);
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return info;
    }

    public int insertLanguage(CandidateInfo info, int candidateId, int userId)
    {
        int candlangId = 0;
        try 
        {
            StringBuilder sb = new StringBuilder();
            sb.append("INSERT INTO t_candlang  ");
            sb.append("( i_candidateid, i_languageid, i_proficiencyid, i_status,s_filename, ts_regdate, ts_moddate) ");
            sb.append("VALUES (?, ?, ?, ?, ?, ?,?)");
            String query = (sb.toString()).intern();
            sb.setLength(0);
            conn = getConnection();
            pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            int scc = 0;
            pstmt.setInt(++scc, candidateId);
            pstmt.setInt(++scc, info.getLanguageId());
            pstmt.setInt(++scc, info.getProficiencyId());
            pstmt.setInt(++scc, info.getStatus());
            pstmt.setString(++scc, info.getCandlangfilename());
            pstmt.setString(++scc, currDate1());
            pstmt.setString(++scc, currDate1());
            pstmt.executeUpdate();
            rs = pstmt.getGeneratedKeys();
            while (rs.next()) {
                candlangId = rs.getInt(1);
            }
            updateverification(2, userId, candidateId, candlangId);
        } catch (Exception exception) {
            print(this, "createCandLang :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return candlangId;
    }

    public int updateLanguage(CandidateInfo info, int candidateId, int uId) 
    {
        int cc = 0;
        try 
        {
            StringBuilder sb = new StringBuilder();
            sb.append("UPDATE t_candlang SET  ");
            sb.append(" i_languageid = ?, ");
            sb.append(" i_proficiencyid = ?, ");
            sb.append(" i_status = ?, ");
            if (info.getCandlangfilename() != null && !info.getCandlangfilename().equals("")) {
                sb.append("s_filename = ?, ");
            }
            sb.append(" ts_moddate = ?  ");
            sb.append("WHERE i_candlangid = ? AND  i_candidateid = ? ");
            String query = (sb.toString()).intern();
            sb.setLength(0);
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            int scc = 0;
            pstmt.setInt(++scc, info.getLanguageId());
            pstmt.setInt(++scc, info.getProficiencyId());
            pstmt.setInt(++scc, info.getStatus());
            if (info.getCandlangfilename() != null && !info.getCandlangfilename().equals("")) {
                pstmt.setString(++scc, info.getCandlangfilename());
            }
            pstmt.setString(++scc, currDate1());
            pstmt.setInt(++scc, info.getCandlangid());
            pstmt.setInt(++scc, candidateId);
            cc = pstmt.executeUpdate();
            updateverification(2, uId, candidateId, info.getCandlangid());
        } catch (Exception exception) {
            print(this, "updateCandLang :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return cc;
    }

    public int deleteLanguage(int candidateId, int candlangId, int userId, int status, String ipAddrStr, String iplocal) 
    {
        int cc = 0;
        try 
        {
            conn = getConnection();
            StringBuilder sb = new StringBuilder();
            sb.append("UPDATE t_candlang SET ");
            sb.append("i_status = ?, ");
            sb.append("i_userid = ?, ");
            sb.append("ts_moddate = ? ");
            sb.append("WHERE i_candlangid = ? AND i_candidateid = ? ");
            String query = (sb.toString()).intern();
            sb.setLength(0);
            pstmt = conn.prepareStatement(query);
            int scc = 0;
            if (status == 1) {
                pstmt.setInt(++scc, 2);
            } else {
                pstmt.setInt(++scc, 1);
            }
            pstmt.setInt(++scc, userId);
            pstmt.setString(++scc, currDate1());
            pstmt.setInt(++scc, candlangId);
            pstmt.setInt(++scc, candidateId);
            print(this,"deleteLanguage :: " + pstmt.toString());
            cc = pstmt.executeUpdate();

            updateverification(2, userId, candidateId, candlangId);
        } catch (Exception exception) {
            print(this, "deleteLanguage :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        createHistoryAccess(null, userId, ipAddrStr, iplocal, "Data Deleted", 7, candlangId);
        return cc;
    }

    public int insertHealthdetails(CandidateInfo info, int candidateId, int userId)
    {
        int cc = 0;
        try 
        {
            if (candidateId > 0) 
            {
                int candidatehealthId = 0;
                String query = "SELECT i_healthid FROM t_healthdeclaration WHERE i_candidateid =? ";
                conn = getConnection();
                pstmt = conn.prepareStatement(query);
                pstmt.setInt(1, candidateId);
                print(this,"candidatehealth :" + pstmt.toString());
                rs = pstmt.executeQuery();
                while (rs.next()) 
                {
                    candidatehealthId = rs.getInt(1);
                }
                rs.close();
                pstmt.close();
                if (candidatehealthId > 0) 
                {
                    StringBuilder sb = new StringBuilder();
                    sb.append("UPDATE t_healthdeclaration SET s_ssmf =?, s_ogukmedicalftw =?, d_ogukexp =?, s_medifitcert =?, d_medifitcertexp =?, s_bloodgroup =?, ");
                    sb.append("i_bloodpressureid =?, s_hypertension =?, s_diabetes =?, s_smoking =?, ");
                    if (info.getMfFilename() != null && !info.getMfFilename().equals("")) {
                        sb.append("s_filename =?, ");
                    }
                    sb.append("s_covid192doses =?, i_status =?, i_userid =?, ts_moddate =? WHERE i_healthid =? AND i_candidateid =? ");
                    String query1 = (sb.toString()).intern();
                    sb.setLength(0);
                    pstmt = conn.prepareStatement(query1);
                    int scc = 0;
                    pstmt.setString(++scc, cipher(info.getSsmf()));
                    pstmt.setString(++scc, cipher(info.getOgukmedicalftw()));
                    pstmt.setString(++scc, changeDate1(info.getOgukexp()));
                    pstmt.setString(++scc, cipher(info.getMedifitcert()));
                    pstmt.setString(++scc, changeDate1(info.getMedifitcertexp()));
                    pstmt.setString(++scc, cipher(info.getBloodgroup()));
                    pstmt.setInt(++scc, info.getBloodpressureId());
                    pstmt.setString(++scc, cipher(info.getHypertension()));
                    pstmt.setString(++scc, cipher(info.getDiabetes()));
                    pstmt.setString(++scc, cipher(info.getSmoking()));
                    if (info.getMfFilename() != null && !info.getMfFilename().equals("")) 
                    {
                        pstmt.setString(++scc, info.getMfFilename());
                    }
                    pstmt.setString(++scc, cipher(info.getCov192doses()));
                    pstmt.setInt(++scc, info.getStatus());
                    pstmt.setInt(++scc, userId);
                    pstmt.setString(++scc, currDate1());
                    pstmt.setInt(++scc, candidatehealthId);
                    pstmt.setInt(++scc, candidateId);
                    print(this, "updateHealth :: " + pstmt.toString());
                    cc = pstmt.executeUpdate();
                    if (cc > 0 && info.getMfFilename() != null && !info.getMfFilename().equals("")) 
                    {
                        String insertfile = "INSERT INTO t_healthfile (i_candidateid, s_name, i_status, i_userid, ts_regdate, ts_moddate) VALUES (?,?,?,?,?,?) ";
                        pstmt = conn.prepareStatement(insertfile);
                        pstmt.setInt(1, candidateId);
                        pstmt.setString(2, info.getMfFilename());
                        pstmt.setInt(3, 1);
                        pstmt.setInt(4, userId);
                        pstmt.setString(5, currDate1());
                        pstmt.setString(6, currDate1());
                        print(this,"insertfile :" + pstmt.toString());
                        pstmt.executeUpdate();
                    }
                    updateverification(3, userId, candidateId, candidatehealthId);
                } 
                else 
                {
                    StringBuilder sb = new StringBuilder();
                    sb.append("INSERT INTO t_healthdeclaration (s_ssmf, s_ogukmedicalftw, d_ogukexp, s_medifitcert, d_medifitcertexp, s_bloodgroup, i_bloodpressureid, ");
                    sb.append("s_hypertension, s_diabetes, s_smoking, s_filename, s_covid192doses, i_status,i_vstatus, i_userid,i_candidateid, ts_regdate, ts_moddate) ");
                    sb.append("VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
                    String query2 = (sb.toString()).intern();
                    sb.setLength(0);
                    pstmt = conn.prepareStatement(query2, Statement.RETURN_GENERATED_KEYS);
                    int scc = 0;
                    pstmt.setString(++scc, cipher(info.getSsmf()));
                    pstmt.setString(++scc, cipher(info.getOgukmedicalftw()));
                    pstmt.setString(++scc, changeDate1(info.getOgukexp()));
                    pstmt.setString(++scc, cipher(info.getMedifitcert()));
                    pstmt.setString(++scc, changeDate1(info.getMedifitcertexp()));
                    pstmt.setString(++scc, cipher(info.getBloodgroup()));
                    pstmt.setInt(++scc, info.getBloodpressureId());
                    pstmt.setString(++scc, cipher(info.getHypertension()));
                    pstmt.setString(++scc, cipher(info.getDiabetes()));
                    pstmt.setString(++scc, cipher(info.getSmoking()));
                    pstmt.setString(++scc, info.getMfFilename());
                    pstmt.setString(++scc, cipher(info.getCov192doses()));
                    pstmt.setInt(++scc, info.getStatus());
                    pstmt.setInt(++scc, 0);
                    pstmt.setInt(++scc, userId);
                    pstmt.setInt(++scc, candidateId);
                    pstmt.setString(++scc, currDate1());
                    pstmt.setString(++scc, currDate1());
                    print(this, "createHealth :: " + pstmt.toString());
                    pstmt.executeUpdate();
                    rs = pstmt.getGeneratedKeys();
                    while (rs.next()) {
                        candidatehealthId = rs.getInt(1);
                    }
                    if (candidatehealthId > 0 && info.getMfFilename() != null && !info.getMfFilename().equals("")) 
                    {
                        String insert = "INSERT INTO t_healthfile (i_candidateid, s_name, i_status, i_userid, ts_regdate, ts_moddate) VALUES (?,?,?,?,?,?) ";
                        pstmt = conn.prepareStatement(insert);
                        pstmt.setInt(1, candidateId);
                        pstmt.setString(2, info.getMfFilename());
                        pstmt.setInt(3, 1);
                        pstmt.setInt(4, userId);
                        pstmt.setString(5, currDate1());
                        pstmt.setString(6, currDate1());
                        print(this,"insert :" + pstmt.toString());
                        pstmt.executeUpdate();
                    }
                }
            }
        } catch (Exception exception) {
            print(this, "createhealthdetail :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return cc;
    }

    public CandidateInfo getCandidateHealthDetailBycandidateId(int candidateId) 
    {
        CandidateInfo info = null;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT s_ssmf, s_ogukmedicalftw,DATE_FORMAT(d_ogukexp, '%d-%b-%Y'), s_medifitcert,DATE_FORMAT(d_medifitcertexp, '%d-%b-%Y'), s_bloodgroup, ");
        sb.append("t_healthdeclaration.i_bloodpressureid, s_hypertension, s_diabetes, s_smoking, t_healthdeclaration.i_userid, s_covid192doses, s_filename, ");
        sb.append("t_healthdeclaration.i_status ,i_healthid , t_bloodpressure.s_name, t_candidate.i_pass ");
        sb.append("FROM t_healthdeclaration ");
        sb.append("LEFT JOIN t_candidate ON (t_candidate.i_candidateid = t_healthdeclaration.i_candidateid) ");
        sb.append("LEFT JOIN t_bloodpressure ON (t_bloodpressure.i_bloodpressureid = t_healthdeclaration.i_bloodpressureid) WHERE t_healthdeclaration.i_candidateid =? ");
        String query = sb.toString().intern();
        sb.setLength(0);
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, candidateId);
            print(this, "getCandidateHealthDetailBycandidateId :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            while (rs.next())
            {
                String ssmf = decipher(rs.getString(1) != null ? rs.getString(1): "") ;
                String ogukmedicalftw = decipher(rs.getString(2) != null ? rs.getString(2): "") ;
                String ogukexp = rs.getString(3) != null ? rs.getString(3): "";
                String medifitcert = decipher(rs.getString(4) != null ? rs.getString(4): "") ;
                String medifitcertexp = rs.getString(5) != null ? rs.getString(5): "";
                String bloodgroup = decipher(rs.getString(6) != null ? rs.getString(6): "") ;
                int bloodpressureId = rs.getInt(7);
                String hypertension = decipher(rs.getString(8) != null ? rs.getString(8): "") ;
                String diabetes = decipher(rs.getString(9) != null ? rs.getString(9): "") ;
                String smoking = decipher(rs.getString(10) != null ? rs.getString(10): "") ;
                int userId = rs.getInt(11);
                String covid192doses = decipher(rs.getString(12) != null ? rs.getString(12): "") ;
                String mfFilename = rs.getString(13) != null ? rs.getString(13): "";
                int status = rs.getInt(14);
                int candidatehealthId = rs.getInt(15);
                String bloodpressure = rs.getString(16) != null ? rs.getString(16): "";
                int pass = rs.getInt(17);
                info = new CandidateInfo(candidatehealthId, ssmf, ogukmedicalftw, ogukexp, medifitcert, medifitcertexp,
                        bloodgroup, bloodpressureId, hypertension, diabetes, smoking, userId, mfFilename, status, covid192doses, bloodpressure, pass);
            }
        } catch (Exception exception) {
            print(this, "getCandidateHealthDetailBycandidateId :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return info;
    }

    public ArrayList getCandVaccList(int candidateId) 
    {
        ArrayList list = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT t_vbd.i_vbdid, t_vaccine.s_name, t_vaccinetype.s_name ,t_city.s_name, t_vbd.s_filename, t_vbd.i_status, ");
        sb.append("DATE_FORMAT(t_vbd.d_dateofapplication, '%d-%b-%Y'),DATE_FORMAT(t_vbd.d_dateofexpiry, '%d-%b-%Y') , t_country.s_name, t_candidate.i_pass ");
        sb.append("FROM t_vbd ");
        sb.append("LEFT JOIN t_vaccine ON (t_vbd.i_vaccinenameid = t_vaccine.i_vaccineid) ");
        sb.append("LEFT JOIN t_vaccinetype ON (t_vbd.i_vaccinetypeid = t_vaccinetype.i_vaccinetypeid) ");
        sb.append("LEFT JOIN t_city ON (t_vbd.i_placeofapplicationid = t_city.i_cityid) ");
        sb.append("LEFT JOIN t_country ON (t_country.i_countryid = t_city.i_countryid) ");
        sb.append("LEFT JOIN t_candidate ON (t_candidate.i_candidateid = t_vbd.i_candidateid) ");
        sb.append("WHERE t_vbd.i_candidateid = ? ");
        sb.append("ORDER BY t_vbd.i_status, t_vaccine.s_name ");
        String query = (sb.toString()).intern();
        sb.setLength(0);
        try
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, candidateId);
            logger.info("getCandVaccList :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String vaccinename, vaccinetypename, placeofapplication, filename, dateofapplication, dateofexpiry, countryname;
            int candidatevaccId, status, pass;
            while (rs.next()) 
            {
                candidatevaccId = rs.getInt(1);
                vaccinename = rs.getString(2) != null ? rs.getString(2) : "";
                vaccinetypename = rs.getString(3) != null ? rs.getString(3) : "";
                placeofapplication = rs.getString(4) != null ? rs.getString(4) : "";
                filename = rs.getString(5) != null ? rs.getString(5) : "";
                status = rs.getInt(6);
                dateofapplication = rs.getString(7) != null ? rs.getString(7): "";
                dateofexpiry = rs.getString(8) != null ? rs.getString(8): "";
                countryname = rs.getString(9) != null ? rs.getString(9) : "";
                pass = rs.getInt(10);
                if (!placeofapplication.equals("")) 
                {
                    if (!countryname.equals("")) 
                    {
                        placeofapplication += " (" + countryname + ")";
                    }
                }
                list.add(new CandidateInfo(candidatevaccId, vaccinename, vaccinetypename, placeofapplication, filename, status, dateofapplication, dateofexpiry, pass));
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return list;
    }

    public CandidateInfo getcandVaccForModify(int candvaccId) 
    {
        CandidateInfo info = null;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT i_vaccinenameid, i_vaccinetypeid,i_placeofapplicationid,DATE_FORMAT(t_vbd.d_dateofapplication, '%d-%b-%Y'),  ");
        sb.append("DATE_FORMAT(t_vbd.d_dateofexpiry, '%d-%b-%Y'),t_vbd.i_status, t_vbd.s_filename, t_city.s_name, t_country.s_name ");
        sb.append("FROM t_vbd LEFT JOIN t_city ON (t_city.i_cityid = t_vbd.i_placeofapplicationid) LEFT JOIN t_country ON (t_country.i_countryid = t_city.i_countryid) WHERE i_vbdid = ? ");
        String query = (sb.toString()).intern();
        sb.setLength(0);
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, candvaccId);
            logger.info("getcandVaccForModify :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String dateofapplication, dateofexpiry, filename;
            int vaccinationNameId, vacinationTypeId, placeofapplication, status;
            while (rs.next()) 
            {
                vaccinationNameId = rs.getInt(1);
                vacinationTypeId = rs.getInt(2);
                placeofapplication = rs.getInt(3);
                dateofapplication = rs.getString(4) != null ? rs.getString(4): "";
                dateofexpiry = rs.getString(5) != null ? rs.getString(5): "";
                status = rs.getInt(6);
                filename = rs.getString(7) != null ? rs.getString(7) : "";
                String cityName = rs.getString(8) != null ? rs.getString(8) : "";
                String countryName = rs.getString(9) != null ? rs.getString(9) : "";
                if (!countryName.equals("")) 
                {
                    cityName += " (" + countryName + ")";
                }
                info = new CandidateInfo(vaccinationNameId, vacinationTypeId, placeofapplication, dateofapplication, dateofexpiry, status, filename, cityName);
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return info;
    }

    public int insertVaccinationdetail(CandidateInfo info, int candidateId, int userId) 
    {
        int candvaccId = 0;
        try 
        {
            StringBuilder sb = new StringBuilder();
            sb.append("INSERT INTO t_vbd  ");
            sb.append("( i_candidateid, i_vaccinenameid,i_vaccinetypeid,i_placeofapplicationid,d_dateofapplication,d_dateofexpiry, i_status,s_filename,i_userid, ts_regdate, ts_moddate) ");
            sb.append("VALUES (?,?,?,?,?,?,?,?,?,?,?)");
            String query = (sb.toString()).intern();
            sb.setLength(0);
            conn = getConnection();
            pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            int scc = 0;
            pstmt.setInt(++scc, candidateId);
            pstmt.setInt(++scc, info.getVaccinationNameId());
            pstmt.setInt(++scc, info.getVaccinationTypeId());
            pstmt.setInt(++scc, info.getPlaceofapplicationId());
            pstmt.setString(++scc, changeDate1(info.getDateofapplication()));
            pstmt.setString(++scc, changeDate1(info.getDateofexpiry()));
            pstmt.setInt(++scc, info.getStatus());
            pstmt.setString(++scc, info.getVaccinecertfile());
            pstmt.setInt(++scc, userId);
            pstmt.setString(++scc, currDate1());
            pstmt.setString(++scc, currDate1());
            pstmt.executeUpdate();
            print(this,"insertVaccinationdetail : " + pstmt.toString());
            rs = pstmt.getGeneratedKeys();
            while (rs.next()) {
                candvaccId = rs.getInt(1);
            }
            updateverification(4, userId, candidateId, candvaccId);
        } catch (Exception exception) {
            print(this, "createCandvacc :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return candvaccId;
    }

    public int updateVaccinationRecord(CandidateInfo info, int candidateId, int userId) 
    {
        int cc = 0;
        try
        {
            StringBuilder sb = new StringBuilder();
            sb.append("UPDATE t_vbd SET  ");
            sb.append(" i_vaccinenameid = ?, ");
            sb.append(" i_vaccinetypeid = ?, ");
            sb.append(" i_placeofapplicationid = ?, ");
            sb.append(" d_dateofapplication = ?, ");
            sb.append(" d_dateofexpiry = ?, ");
            sb.append(" i_status = ?, ");
            if (info.getVaccinecertfile() != null && !info.getVaccinecertfile().equals("")) {
                sb.append("s_filename = ?, ");
            }
            sb.append(" ts_moddate = ?  ");
            sb.append("WHERE i_vbdid = ? AND  i_candidateid = ? ");
            String query = (sb.toString()).intern();
            sb.setLength(0);
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            int scc = 0;
            pstmt.setInt(++scc, info.getVaccinationNameId());
            pstmt.setInt(++scc, info.getVaccinationTypeId());
            pstmt.setInt(++scc, info.getPlaceofapplicationId());
            pstmt.setString(++scc, changeDate1(info.getDateofapplication()));
            pstmt.setString(++scc, changeDate1(info.getDateofexpiry()));
            pstmt.setInt(++scc, info.getStatus());
            if (info.getVaccinecertfile() != null && !info.getVaccinecertfile().equals("")) {
                pstmt.setString(++scc, info.getVaccinecertfile());
            }
            pstmt.setString(++scc, currDate1());
            pstmt.setInt(++scc, info.getCandidatevaccineId());
            pstmt.setInt(++scc, candidateId);
            cc = pstmt.executeUpdate();

            updateverification(4, userId, candidateId, info.getCandidatevaccineId());
        } catch (Exception exception) {
            print(this, "updateCandVaccination :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return cc;
    }
    
    //dateof appli dupl

    public int checkDuplicacyVaccination(int candidateId, int candidatevaccineId, int vaccinationNameId, int vaccinationTypeId, String dateofapplication) 
    {
        int ck = 0;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT i_vbdid FROM t_vbd WHERE i_candidateid = ?  AND i_vaccinenameid = ? AND i_vaccinetypeid = ? AND d_dateofapplication = ? AND  i_status  IN (1, 2)");
        if (candidatevaccineId > 0) {
            sb.append(" AND i_vbdid != ? ");
        }
        String query = (sb.toString()).intern();
        sb.setLength(0);
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            int scc = 0;
            pstmt.setInt(++scc, candidateId);
            pstmt.setInt(++scc, vaccinationNameId);
            pstmt.setInt(++scc, vaccinationTypeId);
            pstmt.setString(++scc, changeDate1(dateofapplication));
            if (candidatevaccineId > 0) {
                pstmt.setInt(++scc, candidatevaccineId);
            }
            print(this, "checkDuplicacyVaccination :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ck = 1;
            }
        } catch (Exception exception) {
            print(this, "checkDuplicacyVaccination :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return ck;
    }

    public int deletevaccination(int candidateId, int candidatevaccineId, int userId, int status, String ipAddrStr, String iplocal)
    {
        int cc = 0;
        try
        {
            conn = getConnection();
            StringBuilder sb = new StringBuilder();
            sb.append("UPDATE t_vbd SET ");
            sb.append("i_status = ?, ");
            sb.append("i_userid = ?, ");
            sb.append("ts_moddate = ? ");
            sb.append("WHERE i_vbdid = ? AND i_candidateid = ? ");
            String query = (sb.toString()).intern();
            sb.setLength(0);
            pstmt = conn.prepareStatement(query);
            int scc = 0;
            if (status == 1) {
                pstmt.setInt(++scc, 2);
            } else {
                pstmt.setInt(++scc, 1);
            }
            pstmt.setInt(++scc, userId);
            pstmt.setString(++scc, currDate1());
            pstmt.setInt(++scc, candidatevaccineId);
            pstmt.setInt(++scc, candidateId);
            print(this,"deletevaccination :: " + pstmt.toString());
            cc = pstmt.executeUpdate();

            updateverification(4, userId, candidateId, candidatevaccineId);
        } catch (Exception exception) {
            print(this, "deletevaccination :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        createHistoryAccess(null, userId, ipAddrStr, iplocal, "Data Deleted", 7, candidatevaccineId);
        return cc;
    }

    public ArrayList getCandgovdocList(int candidateId) 
    {
        ArrayList list = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT t_govdoc.i_govid, t_doctype.s_doc , t_govdoc.s_docno ,t_city.s_name,t_documentissuedby.s_name, t_govdoc.i_status , ");
        sb.append("DATE_FORMAT(t_govdoc.d_dateofissue, '%d-%b-%Y'),DATE_FORMAT(t_govdoc.d_expirydate, '%d-%b-%Y') , t_country.s_name, t_candidate.i_pass, t1.c1 ");
        sb.append("FROM t_govdoc ");
        sb.append("LEFT JOIN t_doctype ON (t_govdoc.i_doctypeid = t_doctype.i_doctypeid) ");
        sb.append("LEFT JOIN t_city ON (t_govdoc.i_placeofissueid = t_city.i_cityid) ");
        sb.append("LEFT JOIN t_documentissuedby ON (t_govdoc.i_documentissuedbyid = t_documentissuedby.i_documentissuedbyid) ");
        sb.append("LEFT JOIN t_candidate ON (t_candidate.i_candidateid = t_govdoc.i_candidateid) ");
        sb.append("LEFT JOIN t_country ON (t_city.i_countryid = t_country.i_countryid) ");
        sb.append("LEFT JOIN (SELECT i_govid, COUNT(1) AS c1 FROM t_documentfiles WHERE i_status = 1 GROUP BY i_govid) AS t1 ON (t1.i_govid = t_govdoc.i_govid) ");
        sb.append("WHERE t_govdoc.i_candidateid = ? ");
        sb.append("ORDER BY t_govdoc.i_status, t_doctype.s_doc ");
        String query = (sb.toString()).intern();
        sb.setLength(0);
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, candidateId);
            logger.info("getCandgovdocList :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String documentname, documentno, placeofissue, issuedby, dateofissue, dateofexpiry, countryname;
            int govdocumentId, status, pass, filecount;
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
                pass = rs.getInt(10);
                filecount = rs.getInt(11);
                if (!countryname.equals(""))
                {
                    placeofissue += "(" + countryname + ")";
                }
                list.add(new CandidateInfo(govdocumentId, documentname, documentno, placeofissue, issuedby, 
                    status, dateofissue, dateofexpiry, countryname, pass, filecount));
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return list;
    }

    public CandidateInfo getcandgovForModify(int govdocumentId) 
    {
        CandidateInfo info = null;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT i_doctypeid, s_docno,i_placeofissueid,i_documentissuedbyid,DATE_FORMAT(t_govdoc.d_dateofissue, '%d-%b-%Y'),  ");
        sb.append("DATE_FORMAT(t_govdoc.d_expirydate, '%d-%b-%Y'),t_govdoc.i_status, t_city.s_name, t_country.s_name ");
        sb.append("FROM t_govdoc ");
        sb.append("LEFT JOIN t_city ON (t_city.i_cityid = t_govdoc.i_placeofissueid) ");
        sb.append("LEFT JOIN t_country ON (t_country.i_countryid = t_city.i_countryid) WHERE i_govid = ? ");
        String query = (sb.toString()).intern();
        sb.setLength(0);
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, govdocumentId);
            logger.info("getcandgovForModify :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String documentno,  dateofissue, dateofexpiry, cityName, countryName;
            int placeofissueId, issuedbyId, documenttypeId, status;
            while (rs.next()) 
            {
                documenttypeId = rs.getInt(1);
                documentno = decipher(rs.getString(2) != null ? rs.getString(2) : "");
                placeofissueId = rs.getInt(3);
                issuedbyId = rs.getInt(4);
                dateofissue = rs.getString(5) != null ? rs.getString(5) : "";
                dateofexpiry = rs.getString(6) != null ? rs.getString(6) : "";
                status = rs.getInt(7);
                cityName = rs.getString(8) != null ? rs.getString(8) : "";
                countryName = rs.getString(9) != null ? rs.getString(9) : "";
                if (!countryName.equals("")) 
                {
                    cityName += "(" + countryName + ")";
                }
                info = new CandidateInfo(documenttypeId, documentno, placeofissueId, issuedbyId, dateofissue, dateofexpiry, status, cityName);
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return info;
    }

    public int checkDuplicacygovdocument(int candidateId, int govdocumentId, int documentTypeId)
    {
        int ck = 0;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT i_govid FROM t_govdoc WHERE i_candidateid = ?  AND i_doctypeid = ?  AND  i_status  IN (1, 2)");
        if (govdocumentId > 0) {
            sb.append(" AND i_govid != ? ");
        }
        String query = (sb.toString()).intern();
        sb.setLength(0);
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            int scc = 0;
            pstmt.setInt(++scc, candidateId);
            pstmt.setInt(++scc, documentTypeId);
            if (govdocumentId > 0) {
                pstmt.setInt(++scc, govdocumentId);
            }
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ck = 1;
            }
        } catch (Exception exception) {
            print(this, "checkDuplicacygovdocument :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return ck;
    }

    public int insertGovdocumentdetail(CandidateInfo info, int candidateId, int userId) 
    {
        int govdocumentId = 0;
        try 
        {
            StringBuilder sb = new StringBuilder();
            sb.append("INSERT INTO t_govdoc  ");
            sb.append("( i_candidateid, i_doctypeid,s_docno,i_placeofissueid,i_documentissuedbyid,d_dateofissue,d_expirydate, i_status,i_userid, ts_regdate, ts_moddate) ");
            sb.append("VALUES (?,?,?,?,?,?,?,?,?,?,?)");
            String query = (sb.toString()).intern();
            sb.setLength(0);
            conn = getConnection();
            pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            int scc = 0;
            pstmt.setInt(++scc, candidateId);
            pstmt.setInt(++scc, info.getDocumenttypeId());
            pstmt.setString(++scc, cipher(info.getDocumentno()));
            pstmt.setInt(++scc, info.getPlaceofissueId());
            pstmt.setInt(++scc, info.getIssuedbyId());
            pstmt.setString(++scc, changeDate1(info.getDateofissue()));
            pstmt.setString(++scc, changeDate1(info.getDateofexpiry()));
            pstmt.setInt(++scc, info.getStatus());
            pstmt.setInt(++scc, userId);
            pstmt.setString(++scc, currDate1());
            pstmt.setString(++scc, currDate1());
            pstmt.executeUpdate();
            print(this,"insertGovdocumentdetail : " + pstmt.toString());
            rs = pstmt.getGeneratedKeys();
            while (rs.next()) {
                govdocumentId = rs.getInt(1);
            }

            updateverification(10, userId, candidateId, govdocumentId);
        } catch (Exception exception) {
            print(this, "insertGovdocumentdetail :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return govdocumentId;
    }

    public int updateGovdocumentRecord(CandidateInfo info, int candidateId, int userId)
    {
        int cc = 0;
        try 
        {
            StringBuilder sb = new StringBuilder();
            sb.append("UPDATE t_govdoc SET i_remind = 0, ");
            sb.append(" i_doctypeid = ?, ");
            sb.append(" s_docno = ?, ");
            sb.append(" i_placeofissueid = ?, ");
            sb.append(" i_documentissuedbyid = ?, ");
            sb.append(" d_dateofissue = ?, ");
            sb.append(" d_expirydate = ?, ");
            sb.append(" i_status = ?, ");
            sb.append(" i_userid = ?, ");
            sb.append(" ts_moddate = ?  ");
            sb.append("WHERE i_govid = ? AND  i_candidateid = ? ");
            String query = (sb.toString()).intern();
            sb.setLength(0);
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            int scc = 0;
            pstmt.setInt(++scc, info.getDocumenttypeId());
            pstmt.setString(++scc, cipher(info.getDocumentno()));
            pstmt.setInt(++scc, info.getPlaceofissueId());
            pstmt.setInt(++scc, info.getIssuedbyId());
            pstmt.setString(++scc, changeDate1(info.getDateofissue()));
            pstmt.setString(++scc, changeDate1(info.getDateofexpiry()));
            pstmt.setInt(++scc, info.getStatus());
            pstmt.setInt(++scc, info.getUserId());
            pstmt.setString(++scc, currDate1());
            pstmt.setInt(++scc, info.getGovdocumentId());
            pstmt.setInt(++scc, candidateId);
            cc = pstmt.executeUpdate();
            updateverification(10, userId, candidateId, info.getGovdocumentId());
        } catch (Exception exception) {
            print(this, "updateGovdocumentRecord :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return cc;
    }
    
    public int deletegovdocument(int candidateId, int govdocumentId, int userId, int status, String ipAddrStr, String iplocal)
    {
        int cc = 0;
        try 
        {
            conn = getConnection();
            StringBuilder sb = new StringBuilder();
            sb.append("UPDATE t_govdoc SET ");
            sb.append("i_status = ?, ");
            sb.append("i_userid = ?, ");
            sb.append("ts_moddate = ? ");
            sb.append("WHERE i_govid = ? AND i_candidateid = ? ");
            String query = (sb.toString()).intern();
            sb.setLength(0);
            pstmt = conn.prepareStatement(query);
            int scc = 0;
            if (status == 1) {
                pstmt.setInt(++scc, 2);
            } else {
                pstmt.setInt(++scc, 1);
            }
            pstmt.setInt(++scc, userId);
            pstmt.setString(++scc, currDate1());
            pstmt.setInt(++scc, govdocumentId);
            pstmt.setInt(++scc, candidateId);
            print(this,"deletegovdocument :: " + pstmt.toString());
            cc = pstmt.executeUpdate();

            updateverification(10, userId, candidateId, govdocumentId);
        } catch (Exception exception) {
            print(this, "deletegovdocument :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        createHistoryAccess(null, userId, ipAddrStr, iplocal, "Data Deleted", 7, govdocumentId);
        return cc;
    }
    
    public int inserttrainingCertificate(CandidateInfo info, int candidateId, int uId, int type) 
    {
        int trainingandcertId = 0;
        try 
        {
            StringBuilder sb = new StringBuilder();
            sb.append("INSERT INTO t_trainingandcert (i_coursetypeid, i_coursenameid, s_educinst, i_locationofinstitid, s_fieldofstudy, d_coursestart, d_passingyear, ");
            sb.append("d_dateofissue, s_certificateno, d_expirydate, s_courseverification, i_approvedbyid, s_filename, i_status,i_candidateid, ts_regdate,ts_moddate) ");
            sb.append("VALUES ( ?,?,?,?,?,  ?,?,?,?,?,  ?,?,?,?,?,  ?,? )");//17
            String candidatequery = sb.toString().intern();
            sb.setLength(0);
            conn = getConnection();
            pstmt = conn.prepareStatement(candidatequery, PreparedStatement.RETURN_GENERATED_KEYS);
            pstmt.setInt(1, info.getCoursetypeId());
            pstmt.setInt(2, info.getCoursenameId());
            pstmt.setString(3, info.getEduinstitute());
            pstmt.setInt(4, info.getLocationofInstituteId());
            pstmt.setString(5, info.getFieldofstudy());
            pstmt.setString(6, changeDate1(info.getCoursestart()));
            pstmt.setString(7, changeDate1(info.getPassingyear()));
            pstmt.setString(8, changeDate1(info.getDateofissue()));
            pstmt.setString(9, cipher(info.getCertificateno()));
            pstmt.setString(10, changeDate1(info.getDateofexpiry()));
            pstmt.setString(11, info.getCourseverification());
            pstmt.setInt(12, info.getApprovedbyid());
            pstmt.setString(13, info.getCertifilename());
            pstmt.setInt(14, info.getStatus());
            pstmt.setInt(15, candidateId);
            pstmt.setString(16, currDate1());
            pstmt.setString(17, currDate1());
            print(this, "inserttrainingCertificate ::::" + pstmt.toString());
            pstmt.executeUpdate();
            rs = pstmt.getGeneratedKeys();
            while (rs.next()) {
                trainingandcertId = rs.getInt(1);
            }
            if(type == 1){
                updateverification(7, uId, candidateId, trainingandcertId);
            }
            rs.close();
            pstmt.close();
        } catch (Exception exception) {
            print(this, "inserttrainingCertificate :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return trainingandcertId;
    }

    public int updatetrainingCertificate(CandidateInfo info, int candidateId, int uId) 
    {
        int cc = 0;
        try 
        {
            StringBuilder sb = new StringBuilder();
            sb.append("UPDATE t_trainingandcert SET i_coursetypeid =?, i_coursenameid =?, s_educinst =?, i_locationofinstitid =?, s_fieldofstudy =?, ");
            sb.append("d_coursestart =?, d_passingyear =?, d_dateofissue =?, s_certificateno =?, d_expirydate =?, s_courseverification =?, i_approvedbyid =?, ");
            if (info.getCertifilename() != null && !info.getCertifilename().equals("")) 
            {
                sb.append("s_filename =?, ");
            }
            sb.append("i_status =?, ts_moddate =? WHERE i_tcid =? AND i_candidateid =? ");
            String query = (sb.toString()).intern();
            sb.setLength(0);
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            int scc = 0;
            pstmt.setInt(++scc, info.getCoursetypeId());
            pstmt.setInt(++scc, info.getCoursenameId());
            pstmt.setString(++scc, info.getEduinstitute());
            pstmt.setInt(++scc, info.getLocationofInstituteId());
            pstmt.setString(++scc, info.getFieldofstudy());
            pstmt.setString(++scc, changeDate1(info.getCoursestart()));
            pstmt.setString(++scc, changeDate1(info.getPassingyear()));
            pstmt.setString(++scc, changeDate1(info.getDateofissue()));
            pstmt.setString(++scc, cipher(info.getCertificateno()));
            pstmt.setString(++scc, changeDate1(info.getDateofexpiry()));
            pstmt.setString(++scc, info.getCourseverification());
            pstmt.setInt(++scc, info.getApprovedbyid());
            if (info.getCertifilename() != null && !info.getCertifilename().equals(""))
            {
                pstmt.setString(++scc, info.getCertifilename());
            }
            pstmt.setInt(++scc, info.getStatus());
            pstmt.setString(++scc, currDate1());
            pstmt.setInt(++scc, info.getTrainingandcertId());
            pstmt.setInt(++scc, candidateId);
            print(this, "updatetrainingCertificate :: " + pstmt.toString());
            cc = pstmt.executeUpdate();
            updateverification(7, uId, candidateId, info.getTrainingandcertId());
        } catch (Exception exception) {
            print(this, "updatetrainingCertificate :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return cc;
    }

    public int checkDuplicacytrainingCertificate(int candidateId, int trainingcertId, int coursetypeId, int coursenameId, String educinst, String dateofissue) 
    {
        int ck = 0;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT i_tcid FROM t_trainingandcert WHERE i_candidateid =? AND i_coursetypeid =? AND i_coursenameid =? AND s_educinst =? AND d_dateofissue= ? AND i_status IN (1, 2) ");
        if (trainingcertId > 0) {
            sb.append("AND i_tcid !=? ");
        }
        String query = (sb.toString()).intern();
        sb.setLength(0);
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            int scc = 0;
            pstmt.setInt(++scc, candidateId);
            pstmt.setInt(++scc, coursetypeId);
            pstmt.setInt(++scc, coursenameId);
            pstmt.setString(++scc, educinst);
            pstmt.setString(++scc, changeDate1(dateofissue));
            if (trainingcertId > 0) {
                pstmt.setInt(++scc, trainingcertId);
            }
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ck = 1;
            }
        } catch (Exception exception) {
            print(this, "checkDuplicacyhitch :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return ck;
    }

    public CandidateInfo gettrainingCertificateDetailById(int candidateId) 
    {
        CandidateInfo info = null;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT i_coursetypeid, t_trainingandcert.i_coursenameid, s_educinst, i_locationofinstitid, s_fieldofstudy, DATE_FORMAT(t_trainingandcert.d_coursestart, '%d-%b-%Y'), ");
        sb.append("DATE_FORMAT(t_trainingandcert.d_passingyear, '%d-%b-%Y'), DATE_FORMAT(t_trainingandcert.d_dateofissue, '%d-%b-%Y'), t_trainingandcert.s_certificateno, ");
        sb.append("DATE_FORMAT(t_trainingandcert.d_expirydate, '%d-%b-%Y'), s_courseverification, t_trainingandcert.i_approvedbyid, t_trainingandcert.s_filename, ");
        sb.append("t_trainingandcert.i_status, t_city.s_name, t_country.s_name, t_coursename.s_name ");
        sb.append("FROM t_trainingandcert ");
        sb.append("LEFT JOIN t_city ON (t_city.i_cityid = t_trainingandcert.i_locationofinstitid) ");
        sb.append("LEFT JOIN t_coursename ON (t_coursename.i_coursenameid = t_trainingandcert.i_coursenameid) ");
        sb.append("LEFT JOIN t_country ON (t_country.i_countryid = t_city.i_countryid) WHERE i_tcid = ? ");//14
        String query = sb.toString().intern();
        sb.setLength(0);
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, candidateId);
            print(this, "gettrainingCertificateDetailById :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            while (rs.next()) 
            {
                int coursetypeId = rs.getInt(1);
                int coursenameId = rs.getInt(2);
                String educinst = rs.getString(3) != null ? rs.getString(3) : "";
                int locationofinstit = rs.getInt(4);
                String fieldofstudy = rs.getString(5) != null ? rs.getString(5) : "";
                String coursestart = rs.getString(6) != null ? rs.getString(6) : "";
                String passingyear = rs.getString(7) != null ? rs.getString(7) : "";
                String dateofissue = rs.getString(8) != null ? rs.getString(8) : "";
                String certificateno = decipher(rs.getString(9) != null ? rs.getString(9) : "");
                String expirydate = rs.getString(10) != null ? rs.getString(10) : "";
                String courseverification = rs.getString(11) != null ? rs.getString(11) : "";
                int approvedbyId = rs.getInt(12);
                String filename = rs.getString(13) != null ? rs.getString(13) : "";
                int status = rs.getInt(14);
                String cityName = rs.getString(15) != null ? rs.getString(15) : "";
                String countryName = rs.getString(16) != null ? rs.getString(16) : "";
                String courseName = rs.getString(17) != null ? rs.getString(17) : "";
                if (!countryName.equals("")) 
                {
                    cityName += " (" + countryName + ")";
                }
                info = new CandidateInfo(coursetypeId, coursenameId, educinst, locationofinstit, fieldofstudy,
                        coursestart, passingyear, dateofissue, certificateno, expirydate, courseverification,
                        approvedbyId, filename, status, cityName, courseName);
            }
        } catch (Exception exception) {
            print(this, "gettrainingCertificateDetailById :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return info;
    }

    public ArrayList gettrainingCertificatelist(int candidateId, int next, int count, int courseIndex) 
    {
        ArrayList list = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT t_coursename.s_name, t_coursetype.s_name, t_trainingandcert.s_educinst, t_city.s_name, t_approvedby.s_name, ");
        sb.append("DATE_FORMAT(t_trainingandcert.d_dateofissue, '%d-%b-%Y'), DATE_FORMAT(t_trainingandcert.d_expirydate, '%d-%b-%Y'), t_trainingandcert.i_status, ");
        sb.append("t_trainingandcert.i_tcid, t_trainingandcert.s_filename, t_country.s_name, t_candidate.i_pass, t_trainingandcert.i_coursenameid, t_trainingandcert.s_url ");
        sb.append("FROM t_trainingandcert ");
        sb.append("LEFT JOIN t_coursetype ON (t_coursetype.i_coursetypeid = t_trainingandcert. i_coursetypeid) ");
        sb.append("LEFT JOIN t_city ON (t_city.i_cityid = t_trainingandcert.i_locationofinstitid) ");
        sb.append("LEFT JOIN t_coursename ON (t_coursename.i_coursenameid = t_trainingandcert.i_coursenameid) ");
        sb.append("LEFT JOIN t_country ON (t_city.i_countryid = t_country. i_countryid) ");
        sb.append("LEFT JOIN t_candidate ON (t_candidate.i_candidateid = t_trainingandcert.i_candidateid) ");
        sb.append("LEFT JOIN t_approvedby ON (t_approvedby.i_approvedbyid = t_trainingandcert.i_approvedbyid) WHERE t_trainingandcert.i_candidateid =? AND t_trainingandcert.i_status = 1 ");
        if(courseIndex > 0)
        {
            sb.append(" AND t_coursename.i_coursenameid = ? ");
        }
        sb.append("ORDER BY t_coursename.s_name ");
        if (count > 0)
        {
            sb.append(" LIMIT ").append(next * count).append(", ").append(count);
        }        
        String query = (sb.toString()).intern();
        sb.setLength(0);
        
        sb.append("SELECT COUNT(1) FROM t_trainingandcert ");
        sb.append("LEFT JOIN t_coursetype ON (t_coursetype.i_coursetypeid = t_trainingandcert. i_coursetypeid) ");
        sb.append("LEFT JOIN t_city ON (t_city.i_cityid = t_trainingandcert.i_locationofinstitid) ");
        sb.append("LEFT JOIN t_coursename ON (t_coursename.i_coursenameid = t_trainingandcert.i_coursenameid) ");
        sb.append("LEFT JOIN t_country ON (t_city.i_countryid = t_country. i_countryid) ");
        sb.append("LEFT JOIN t_approvedby ON (t_approvedby.i_approvedbyid = t_trainingandcert.i_approvedbyid) ");
        sb.append("WHERE t_trainingandcert.i_candidateid = ? ");
        if (courseIndex > 0) 
        {
            sb.append(" AND ( t_coursename.i_coursenameid = ? ) ");
        }
        sb.append("ORDER BY t_coursename.s_name ");
        
        String countquery = (sb.toString()).intern();
        sb.setLength(0);
        int scc =0 ;
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(++scc, candidateId);
            if(courseIndex > 0)
            {
                pstmt.setInt(++scc, courseIndex);
            }
            logger.info("gettrainingCertificatelist :: " + pstmt.toString());
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
                int pass = rs.getInt(12);
                int coursenameId = rs.getInt(13);
                String url = rs.getString(14) != null ? rs.getString(14) : "";
               if(!educinst.equals(""))
                {
                   if (!countryname.equals("")) 
                   {
                      educinst += ","+ locationofinstit+  " (" + countryname + ")";
                   }
                }
                list.add(new CandidateInfo(candidateId, coursename, coursetype, educinst, approvedby, 
                        dateofissue, expirydate, status, trainingandcertId, filename, pass, coursenameId, url));
            }
            rs.close();
            
            pstmt = conn.prepareStatement(countquery);
            scc =0;
            pstmt.setInt(++scc, candidateId);
            if (courseIndex > 0) 
            {
                pstmt.setInt(++scc, courseIndex);
            }
            print(this,"gettrainingCertificatelist :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            while (rs.next()) 
            {
                candidateId = rs.getInt(1);
                list.add(new CandidateInfo(candidateId, "", "", "", "", "", "", 0, 0,  "", 0, 0,""));
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return list;
    }

    public int deletetrainingCertificate(int candidateId, int candidatecertficateId, int userId, int status, String ipAddrStr, String iplocal) 
    {
        int cc = 0;
        try 
        {
            conn = getConnection();
            StringBuilder sb = new StringBuilder();
            sb.append("UPDATE t_trainingandcert SET ");
            sb.append("i_status = ?, ");
            sb.append("i_userid = ?, ");
            sb.append("ts_moddate = ? ");
            sb.append("WHERE i_tcid = ? AND i_candidateid = ? ");
            String query = (sb.toString()).intern();
            sb.setLength(0);
            pstmt = conn.prepareStatement(query);
            int scc = 0;
            if (status == 1) {
                pstmt.setInt(++scc, 2);
            } else {
                pstmt.setInt(++scc, 1);
            }
            pstmt.setInt(++scc, userId);
            pstmt.setString(++scc, currDate1());
            pstmt.setInt(++scc, candidatecertficateId);
            pstmt.setInt(++scc, candidateId);
            print(this,"deleteCertification :: " + pstmt.toString());
            cc = pstmt.executeUpdate();
            updateverification(7, userId, candidateId, candidatecertficateId);
        } catch (Exception exception) {
            print(this, "deleteCertification :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        createHistoryAccess(null, userId, ipAddrStr, iplocal, "Data Deleted", 7, candidatecertficateId);
        return cc;
    }

    public int inserteducation(CandidateInfo info, int candidateId, int uId) 
    {
        int educationdetailId = 0;
        try 
        {
            if (info.getHighestqualification() == 1) 
            {
                String primaryquery = "UPDATE t_eduqual SET i_highestqualification = ? WHERE  i_candidateid = ? ";
                conn = getConnection();
                pstmt = conn.prepareStatement(primaryquery);
                pstmt.setInt(1, 0);
                pstmt.setInt(2, candidateId);
                print(this, "SET inserteducation :" + pstmt.toString());
                pstmt.executeUpdate();
                pstmt.close();
                conn.close();
            }
            StringBuilder sb = new StringBuilder();
            sb.append("INSERT INTO t_eduqual  ");
            sb.append("( i_qualificationtypeid, i_degreeid, s_backgroundofstudy, s_eduinstitute,  i_locationofinstituteid, s_fieldofstudy, d_coursestart, ");
            sb.append("d_passingyear,i_highestqualification, s_filename, i_status,i_candidateid, ts_regdate,ts_moddate) ");
            sb.append(" VALUES ( ?,?,?,?,?,  ?,?,?,?,?,  ?,?,?,? )");//14
            String candidatequery = sb.toString().intern();
            sb.setLength(0);
            conn = getConnection();
            pstmt = conn.prepareStatement(candidatequery, PreparedStatement.RETURN_GENERATED_KEYS);
            pstmt.setInt(1, info.getKindId());
            pstmt.setInt(2, info.getDegreeId());
            pstmt.setString(3, info.getBackgroundofstudy());
            pstmt.setString(4, cipher(info.getEduinstitute()));
            pstmt.setInt(5, info.getLocationofInstituteId());
            pstmt.setString(6, info.getFieldofstudy());
            pstmt.setString(7, changeDate1(info.getCoursestart()));
            pstmt.setString(8, changeDate1(info.getPassingyear()));
            pstmt.setInt(9, info.getHighestqualification());
            pstmt.setString(10, info.getEducationalfilename());
            pstmt.setInt(11, info.getStatus());
            pstmt.setInt(12, candidateId);
            pstmt.setString(13, currDate1());
            pstmt.setString(14, currDate1());
            pstmt.executeUpdate();
            rs = pstmt.getGeneratedKeys();
            while (rs.next()) {
                educationdetailId = rs.getInt(1);
            }
            updateverification(6, uId, candidateId, educationdetailId);
            rs.close();
            pstmt.close();
        } catch (Exception exception) {
            print(this, "inserteducation :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return educationdetailId;
    }

    public int updateeducation(CandidateInfo info, int candidateId, int uId)
    {
        int cc = 0;
        try 
        {
            if (info.getHighestqualification() == 1) 
            {
                String primaryquery = "UPDATE t_eduqual SET i_highestqualification = ? WHERE  i_candidateid = ? ";
                conn = getConnection();
                pstmt = conn.prepareStatement(primaryquery);
                pstmt.setInt(1, 0);
                pstmt.setInt(2, candidateId);
                print(this, "SET updateeducation :" + pstmt.toString());
                pstmt.executeUpdate();
                pstmt.close();
                conn.close();
            }
            StringBuilder sb = new StringBuilder();
            sb.append("UPDATE t_eduqual SET  ");
            sb.append(" i_qualificationtypeid = ?, ");
            sb.append(" i_degreeid = ?, ");
            sb.append(" s_backgroundofstudy = ?, ");
            sb.append(" s_eduinstitute = ?, ");
            sb.append(" i_locationofinstituteid = ?, ");
            sb.append("  s_fieldofstudy = ?, ");
            sb.append("  d_coursestart = ?, ");
            sb.append("  d_passingyear = ?, ");
            sb.append(" i_highestqualification = ?, ");
            if (info.getEducationalfilename() != null && !info.getEducationalfilename().equals(""))
            {
                sb.append(" s_filename = ?, ");
            }
            sb.append(" i_status = ?, ");
            sb.append(" ts_moddate = ?  ");
            sb.append("WHERE i_eduqulid = ? AND  i_candidateid = ? ");
            String query = (sb.toString()).intern();
            sb.setLength(0);
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            int scc = 0;
            pstmt.setInt(++scc, info.getKindId());
            pstmt.setInt(++scc, info.getDegreeId());
            pstmt.setString(++scc, info.getBackgroundofstudy());
            pstmt.setString(++scc, cipher(info.getEduinstitute()));
            pstmt.setInt(++scc, info.getLocationofInstituteId());
            pstmt.setString(++scc, info.getFieldofstudy());
            pstmt.setString(++scc, changeDate1(info.getCoursestart()));
            pstmt.setString(++scc, changeDate1(info.getPassingyear()));
            pstmt.setInt(++scc, info.getHighestqualification());
            if (info.getEducationalfilename() != null && !info.getEducationalfilename().equals("")) 
            {
                pstmt.setString(++scc, info.getEducationalfilename());
            }
            pstmt.setInt(++scc, info.getStatus());
            pstmt.setString(++scc, currDate1());
            pstmt.setInt(++scc, info.getEducationdetailId());
            pstmt.setInt(++scc, candidateId);
            cc = pstmt.executeUpdate();
            updateverification(6, uId, candidateId, info.getEducationdetailId());
        } catch (Exception exception) {
            print(this, "updateeducation :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return cc;
    }

    public int checkDuplicacyeducation(int candidateId, int educationdetailId, int kindId, int degreeId, String backgroundofstudy) 
    {
        int ck = 0;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT i_eduqulid FROM t_eduqual WHERE i_candidateid = ?  AND i_qualificationtypeid = ? AND  i_degreeid = ? AND s_backgroundofstudy = ? AND  i_status  IN (1, 2)");
        if (educationdetailId > 0) {
            sb.append(" AND i_eduqulid != ? ");
        }
        String query = (sb.toString()).intern();
        sb.setLength(0);
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            int scc = 0;
            pstmt.setInt(++scc, candidateId);
            pstmt.setInt(++scc, kindId);
            pstmt.setInt(++scc, degreeId);
            pstmt.setString(++scc, backgroundofstudy);
            if (educationdetailId > 0) {
                pstmt.setInt(++scc, educationdetailId);
            }
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ck = 1;
            }
        } catch (Exception exception) {
            print(this, "checkDuplicacyhitch :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return ck;
    }

    public CandidateInfo geteducationDetailById(int educationdetailId)
    {
        CandidateInfo info = null;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT i_qualificationtypeid, i_degreeid,s_backgroundofstudy, s_eduinstitute, i_locationofinstituteid,  s_fieldofstudy, ");
        sb.append("DATE_FORMAT(t_eduqual.d_coursestart, '%d-%b-%Y'),  DATE_FORMAT(t_eduqual.d_passingyear, '%d-%b-%Y'), ");
        sb.append("i_highestqualification, t_eduqual.s_filename, t_eduqual.i_status, t_city.s_name, t_country.s_name FROM t_eduqual ");
        sb.append(" LEFT JOIN t_city ON (t_city.i_cityid = t_eduqual.i_locationofinstituteid) ");
        sb.append(" LEFT JOIN t_country ON (t_country.i_countryid = t_city.i_countryid) ");
        sb.append("WHERE i_eduqulid = ? ");//13
        String query = sb.toString().intern();
        sb.setLength(0);
        try
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, educationdetailId);
            print(this, "geteducationDetailById :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            while (rs.next()) 
            {
                int kindId = rs.getInt(1);
                int degreeId = rs.getInt(2);
                String backgroundofstudy = rs.getString(3) != null ? rs.getString(3): "";
                String educinst = decipher(rs.getString(4) != null ? rs.getString(4): "");
                int locationofinstit = rs.getInt(5);
                String fieldofstudy = rs.getString(6) != null ? rs.getString(6): "";
                String coursestart = rs.getString(7) != null ? rs.getString(7): "";
                String passingyear = rs.getString(8) != null ? rs.getString(8): "";
                int highestqualification = rs.getInt(9);
                String filename = rs.getString(10) != null ? rs.getString(10): "";
                int status = rs.getInt(11);
                String cityname = rs.getString(12) != null ? rs.getString(12): "";
                String countryName = rs.getString(13) != null ? rs.getString(13) : "";
                if (cityname != null && !cityname.equals("")) 
                {
                    cityname += " (" + countryName + ")";
                }
                info = new CandidateInfo(kindId, degreeId, backgroundofstudy, educinst, locationofinstit, fieldofstudy,
                        coursestart, passingyear, highestqualification, filename, status, cityname);
            }
        } catch (Exception exception) {
            print(this, "geteducationDetailById :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return info;
    }

    public ArrayList geteducationlist(int candidateId) 
    {
        ArrayList list = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT  t_qualificationtype.s_name,  t_degree.s_name, t_eduqual.s_eduinstitute, t_city.s_name,t_eduqual.s_fieldofstudy, ");
        sb.append(" DATE_FORMAT(t_eduqual.d_coursestart, '%d-%b-%Y'), DATE_FORMAT(t_eduqual.d_passingyear, '%d-%b-%Y') ,t_eduqual.i_status, ");
        sb.append("t_eduqual.i_eduqulid , t_eduqual.s_filename,t_country.s_name, t_candidate.i_pass ");
        sb.append("FROM t_eduqual ");
        sb.append("LEFT JOIN t_qualificationtype ON t_qualificationtype.i_qualificationtypeid = t_eduqual.i_qualificationtypeid ");
        sb.append("LEFT JOIN t_city ON t_city.i_cityid = t_eduqual.i_locationofinstituteid ");
        sb.append("LEFT JOIN t_degree ON t_degree.i_degreeid = t_eduqual. i_degreeid ");
        sb.append("LEFT JOIN t_country ON t_city.i_countryid = t_country. i_countryid ");
        sb.append("LEFT JOIN t_candidate ON (t_candidate.i_candidateid = t_eduqual.i_candidateid) ");
        sb.append("WHERE t_eduqual.i_candidateid = ? ");
        sb.append("ORDER BY t_eduqual.d_passingyear desc ");
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
                int pass = rs.getInt(12);
                if (!countryname.equals("")) 
                {
                    if (!educinst.equals("")) 
                    {
                        educinst += ", "+locationofinstit+"(" + countryname + ")";
                    }
                }
                list.add(new CandidateInfo(kindname, degree, educinst, locationofinstit, filedofstudy, 
                        startdate, enddate, status, filename, educationdetailId, pass));
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return list;
    }

    public int deleteeducation(int candidateId, int educationdetailId, int userId, int status, String ipAddrStr, String iplocal)
    {
        int cc = 0;
        try 
        {
            conn = getConnection();
            StringBuilder sb = new StringBuilder();
            sb.append("UPDATE t_eduqual SET ");
            sb.append("i_status = ?, ");
            sb.append("i_userid = ?, ");
            sb.append("ts_moddate = ? ");
            sb.append("WHERE i_eduqulid = ? AND i_candidateid = ? ");
            String query = (sb.toString()).intern();
            sb.setLength(0);
            pstmt = conn.prepareStatement(query);
            int scc = 0;
            if (status == 1) {
                pstmt.setInt(++scc, 2);
            } else {
                pstmt.setInt(++scc, 1);
            }
            pstmt.setInt(++scc, userId);
            pstmt.setString(++scc, currDate1());
            pstmt.setInt(++scc, educationdetailId);
            pstmt.setInt(++scc, candidateId);
            print(this, "deleteeducation :: " + pstmt.toString());
            cc = pstmt.executeUpdate();

            updateverification(6, userId, candidateId, educationdetailId);
        } catch (Exception exception) {
            print(this, "deleteeducation :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        createHistoryAccess(null, userId, ipAddrStr, iplocal, "Data Deleted", 7, educationdetailId);
        return cc;
    }

    public int insertexperiencedetail(CandidateInfo info, int candidateId, int uId)
    {
        int experiencedetailId = 0;
        try 
        {
            if (info.getCurrentworkingstatus() == 1) 
            {
                String primaryquery = "UPDATE t_workexperience SET i_currentworkingstatus = ? WHERE  i_candidateid = ? ";
                conn = getConnection();
                pstmt = conn.prepareStatement(primaryquery);
                pstmt.setInt(1, 0);
                pstmt.setInt(2, candidateId);
                pstmt.executeUpdate();
                pstmt.close();
                conn.close();
            }
            StringBuilder sb = new StringBuilder();
            sb.append("INSERT INTO t_workexperience  ");
            sb.append("( s_companyname, i_companyindustryid,i_assettypeid,s_assetname,i_positionid,i_departmentid,i_countryid,i_cityid,s_clientpartyname,i_waterdepthid,  ");
            sb.append(" i_lastdrawnsalarycurrencyid,i_currentworkingstatus,i_skillid,i_gradeid,s_ownerpool,i_crewtypeid,s_ocsemployed,s_legalrights, ");
            sb.append(" i_dayratecurrencyid,i_dayrate,i_monthlysalarycurrencyid,i_monthlysalary,d_workstartdate");
            sb.append(", d_workenddate,i_lastdrawnsalary, s_filenamework,s_filenameexp, i_status,i_userid,i_candidateid, s_role, ts_regdate,ts_moddate) ");
            sb.append(" VALUES ( ?,?,?,?,?,  ?,?,?,?,?,  ?,?,?,?,?, ?,?,?,?,?,  ?,?,?,?,?,  ?,?,?,?,?,  ?,?,    ? )");
            String candidatequery = sb.toString().intern();
            sb.setLength(0);
            conn = getConnection();
            pstmt = conn.prepareStatement(candidatequery, PreparedStatement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, info.getCompanyname());
            pstmt.setInt(2, info.getCompanyindustryId());
            pstmt.setInt(3, info.getAssettypeId());
            pstmt.setString(4, info.getAssetName());
            pstmt.setInt(5, info.getPositionId());
            pstmt.setInt(6, info.getDepartmentId());
            pstmt.setInt(7, info.getCountryId());
            pstmt.setInt(8, info.getCityId());
            pstmt.setString(9, info.getClientpartyname());
            pstmt.setInt(10, info.getWaterdepthId());
            pstmt.setInt(11, info.getLastdrawnsalarycurrencyId());
            pstmt.setInt(12, info.getCurrentworkingstatus());
            pstmt.setInt(13, info.getSkillsId());
            pstmt.setInt(14, info.getGradeId());
            pstmt.setString(15, info.getOwnerpool());
            pstmt.setInt(16, info.getCrewtypeid());
            pstmt.setString(17, info.getOcsemployed());
            pstmt.setString(18, info.getLegalrights());
            pstmt.setInt(19, info.getDayratecurrencyid());
            pstmt.setInt(20, info.getDayrate());
            pstmt.setInt(21, info.getMonthlysalarycurrencyId());
            pstmt.setInt(22, info.getMonthlysalary());
            pstmt.setString(23, changeDate1(info.getWorkstartdate()));
            pstmt.setString(24, changeDate1(info.getWorkenddate()));
            pstmt.setString(25, info.getLastdrawnsalary());
            pstmt.setString(26, info.getWorkfilename());
            pstmt.setString(27, info.getExperiencefilename());
            pstmt.setInt(28, info.getStatus());
            pstmt.setInt(29, uId);
            pstmt.setInt(30, candidateId);
            pstmt.setString(31, info.getRole());
            pstmt.setString(32, currDate1());
            pstmt.setString(33, currDate1());
            print(this, "insertexperiencedetail :: " + pstmt.toString());
            pstmt.executeUpdate();
            rs = pstmt.getGeneratedKeys();
            while (rs.next()) {
                experiencedetailId = rs.getInt(1);
            }
            updateverification(5, uId, candidateId, experiencedetailId);
            rs.close();
            pstmt.close();
        } catch (Exception exception) {
            print(this, "insertexperiencedetail :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return experiencedetailId;
    }

    public int updateexperiencedetail(CandidateInfo info, int candidateId, int uId) 
    {
        int cc = 0;
        try 
        {
            if (info.getCurrentworkingstatus() == 1) 
            {
                String primaryquery = "UPDATE t_workexperience SET i_currentworkingstatus = ? WHERE  i_candidateid = ? ";
                conn = getConnection();
                pstmt = conn.prepareStatement(primaryquery);
                pstmt.setInt(1, 0);
                pstmt.setInt(2, candidateId);
                pstmt.executeUpdate();
                pstmt.close();
                conn.close();
            }
            StringBuilder sb = new StringBuilder();
            sb.append("UPDATE t_workexperience SET s_companyname =?, i_companyindustryid =?, i_assettypeid =?, s_assetname =?, i_positionid =?, i_departmentid =?, i_countryid =?, ");
            sb.append("i_cityid =?, s_clientpartyname =?, i_waterdepthid =?, i_lastdrawnsalarycurrencyid =?, i_currentworkingstatus =?, i_skillid =?, i_gradeid =?, ");
            sb.append("s_ownerpool =?, i_crewtypeid =?, s_ocsemployed =?, s_legalrights =?, i_dayratecurrencyid =?, i_dayrate =?, i_monthlysalarycurrencyid =?, ");
            sb.append("i_monthlysalary =?, d_workstartdate =?, d_workenddate =?, i_lastdrawnsalary =?, ");
            if (info.getWorkfilename() != null && !info.getWorkfilename().equals("")) {
                sb.append("s_filenamework =?, ");
            }
            if (info.getExperiencefilename() != null && !info.getExperiencefilename().equals("")) {
                sb.append("s_filenameexp =?, ");
            }
            if (info.getRole() != null && !info.getRole().equals("")) {
                sb.append("s_role = ?, ");
            }
            sb.append("i_status =?, i_userid =?, ts_moddate =? WHERE i_experienceid =? AND i_candidateid =? ");
            String query = (sb.toString()).intern();
            sb.setLength(0);
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            int scc = 0;
            pstmt.setString(++scc, info.getCompanyname());
            pstmt.setInt(++scc, info.getCompanyindustryId());
            pstmt.setInt(++scc, info.getAssettypeId());
            pstmt.setString(++scc, info.getAssetName());
            pstmt.setInt(++scc, info.getPositionId());
            pstmt.setInt(++scc, info.getDepartmentId());
            pstmt.setInt(++scc, info.getCountryId());
            pstmt.setInt(++scc, info.getCityId());
            pstmt.setString(++scc, info.getClientpartyname());
            pstmt.setInt(++scc, info.getWaterdepthId());
            pstmt.setInt(++scc, info.getLastdrawnsalarycurrencyId());
            pstmt.setInt(++scc, info.getCurrentworkingstatus());
            pstmt.setInt(++scc, info.getSkillsId());
            pstmt.setInt(++scc, info.getGradeId());
            pstmt.setString(++scc, info.getOwnerpool());
            pstmt.setInt(++scc, info.getCrewtypeid());
            pstmt.setString(++scc, info.getOcsemployed());
            pstmt.setString(++scc, info.getLegalrights());
            pstmt.setInt(++scc, info.getDayratecurrencyid());
            pstmt.setInt(++scc, info.getDayrate());
            pstmt.setInt(++scc, info.getMonthlysalarycurrencyId());
            pstmt.setInt(++scc, info.getMonthlysalary());
            pstmt.setString(++scc, changeDate1(info.getWorkstartdate()));
            pstmt.setString(++scc, changeDate1(info.getWorkenddate()));
            pstmt.setString(++scc, info.getLastdrawnsalary());
            if (info.getWorkfilename() != null && !info.getWorkfilename().equals("")) {
                pstmt.setString(++scc, info.getWorkfilename());
            }
            if (info.getExperiencefilename() != null && !info.getExperiencefilename().equals("")) {
                pstmt.setString(++scc, info.getExperiencefilename());
            }
            if (info.getRole() != null && !info.getRole().equals("")) {
                pstmt.setString(++scc, info.getRole());
            }
            pstmt.setInt(++scc, info.getStatus());
            pstmt.setInt(++scc, uId);
            pstmt.setString(++scc, currDate1());
            pstmt.setInt(++scc, info.getExperiencedetailId());
            pstmt.setInt(++scc, candidateId);
            cc = pstmt.executeUpdate();

            updateverification(5, uId, candidateId, info.getExperiencedetailId());
        } catch (Exception exception) {
            print(this, "updateexperiencedetail :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return cc;
    }

    public int checkDuplicacyexperience(int candidateId, int experiencedetailId, String companyName, String workstartdate, String workenddate) 
    {
        int ck = 0;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT i_experienceid FROM t_workexperience WHERE i_candidateid =? AND s_companyname =? AND d_workstartdate =? AND d_workenddate =? AND i_status  IN (1, 2) ");
        if (experiencedetailId > 0) {
            sb.append("AND i_experienceid !=? ");
        }
        String query = (sb.toString()).intern();
        sb.setLength(0);
        try
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            int scc = 0;
            pstmt.setInt(++scc, candidateId);
            pstmt.setString(++scc, companyName);
            pstmt.setString(++scc, changeDate1(workstartdate));
            pstmt.setString(++scc, changeDate1(workenddate));
            if (experiencedetailId > 0) {
                pstmt.setInt(++scc, experiencedetailId);
            }
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ck = 1;
            }
        } catch (Exception exception) {
            print(this, "checkDuplicacyexperience :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return ck;
    }

    public CandidateInfo getexperiencedetailById(int experiencedetailId)
    {
        CandidateInfo info = null;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT s_companyname, i_companyindustryid, i_assettypeid, s_assetname, i_positionid, i_departmentid, t_workexperience.i_countryid, ");
        sb.append("t_workexperience.i_cityid, s_clientpartyname, i_waterdepthid, i_lastdrawnsalarycurrencyid, i_currentworkingstatus, i_skillid, i_gradeid, s_ownerpool, ");
        sb.append("i_crewtypeid, s_ocsemployed, s_legalrights, i_dayratecurrencyid, i_dayrate, i_monthlysalarycurrencyid, i_monthlysalary, ");
        sb.append("DATE_FORMAT(t_workexperience.d_workstartdate, '%d-%b-%Y') ,DATE_FORMAT(t_workexperience.d_workenddate, '%d-%b-%Y'), i_lastdrawnsalary, s_filenamework, ");
        sb.append("s_filenameexp, t_workexperience.i_status, t_city.s_name, t_workexperience.s_role FROM t_workexperience ");
        sb.append("LEFT JOIN t_city ON (t_city.i_cityid = t_workexperience.i_cityid) WHERE i_experienceid =? ");//14
        String query = sb.toString().intern();
        sb.setLength(0);
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, experiencedetailId);
            print(this, "getexperiencedetailById :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            while (rs.next())
            {
                String companyName = rs.getString(1) != null ? rs.getString(1): "";
                int companyindustryId = rs.getInt(2);
                int assettypeId = rs.getInt(3);
                String assetName = rs.getString(4) != null ? rs.getString(4): "";
                int positionId = rs.getInt(5);
                int departmentId = rs.getInt(6);
                int countryId = rs.getInt(7);
                int cityId = rs.getInt(8);
                String clientpartyname = rs.getString(9) != null ? rs.getString(9): "";
                int waterdepthId = rs.getInt(10);
                int lastdrawnsalarycurrencyId = rs.getInt(11);
                int currentworkingstatus = rs.getInt(12);
                int skillId = rs.getInt(13);
                int gradeId = rs.getInt(14);
                String ownerpool = rs.getString(15) != null ? rs.getString(15): "";
                int crewtypeId = rs.getInt(16);
                String ocsemployed = rs.getString(17) != null ? rs.getString(17): "";
                String legalrights = rs.getString(18) != null ? rs.getString(18): "";
                int dayratecurrencyId = rs.getInt(19);
                int dayrate = rs.getInt(20);
                int monthlysalarycurrencyId = rs.getInt(21);
                int monthlysalary = rs.getInt(22);
                String workstartdate = rs.getString(23) != null ? rs.getString(23): "";
                String workenddate = rs.getString(24) != null ? rs.getString(24): "";
                String lastdrawnsalary = rs.getString(25) != null ? rs.getString(25): "";
                String filenamework = rs.getString(26) != null ? rs.getString(26): "";
                String filenameexp = rs.getString(27) != null ? rs.getString(27): "";
                int status = rs.getInt(28);
                String cityname = rs.getString(29) != null ? rs.getString(29): "";
                String role = rs.getString(30) != null ? rs.getString(30): "";
                
                info = new CandidateInfo(companyName, companyindustryId, assettypeId, assetName, positionId, departmentId, countryId, cityId, clientpartyname,
                        waterdepthId, lastdrawnsalarycurrencyId, currentworkingstatus, skillId, gradeId, ownerpool, crewtypeId, ocsemployed, legalrights, dayratecurrencyId,
                        dayrate, monthlysalarycurrencyId, monthlysalary, workstartdate, workenddate, lastdrawnsalary, filenamework, filenameexp, cityname, status, role);
            }
        } catch (Exception exception) {
            print(this, "getexperiencedetailById :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return info;
    }

    public ArrayList getexperiencelist(int candidateId)
    {
        ArrayList list = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT  t_position.s_name, t_experiencedept.s_name, t_workexperience.s_companyname, t_workexperience.s_assetname, ");
        sb.append("DATE_FORMAT(t_workexperience.d_workstartdate, '%d-%b-%Y'), DATE_FORMAT(t_workexperience.d_workenddate, '%d-%b-%Y'),t_workexperience.i_status, ");
        sb.append("t_workexperience.i_experienceid, t_workexperience.s_filenameexp,t_workexperience.s_filenamework,t_workexperience.i_currentworkingstatus, ");
        sb.append("t_candidate.i_pass, t_grade.s_name ");
        sb.append("FROM t_workexperience ");
        sb.append("LEFT JOIN t_position ON t_position.i_positionid = t_workexperience.i_positionid ");
        sb.append("LEFT JOIN t_candidate ON (t_candidate.i_candidateid = t_workexperience.i_candidateid) ");
        sb.append("LEFT JOIN t_experiencedept ON (t_experiencedept.i_experiencedeptid = t_workexperience.i_departmentid) ");
        sb.append("LEFT JOIN t_grade ON(t_grade.i_gradeid = t_position.i_gradeid) ");
        sb.append(" WHERE t_workexperience.i_candidateid = ? ");
        sb.append(" ORDER BY t_workexperience.i_status , t_workexperience.d_workstartdate  ");
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
                int pass = rs.getInt(12);
                String grade = rs.getString(13) != null ? rs.getString(13) : "";
                if(!grade.equals(""))
                {
                    position += " | "+grade;
                }
                if (currentworkingstatus == 1) {
                    enddate = "Present";
                }
                list.add(new CandidateInfo(position, department, companyname, assetname, startdate, enddate, 
                        status, workfilename, experiencefilename, experiencedetailId, pass));
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return list;
    }

    public int deleteexperiencedetail(int candidateId, int experiencedetailId, int userId, int status, String ipAddrStr, String iplocal) 
    {
        int cc = 0;
        try 
        {
            conn = getConnection();
            StringBuilder sb = new StringBuilder();
            sb.append("UPDATE t_workexperience SET ");
            sb.append("i_status = ?, ");
            sb.append("i_userid = ?, ");
            sb.append("ts_moddate = ? ");
            sb.append("WHERE i_experienceid = ? AND i_candidateid = ? ");
            String query = (sb.toString()).intern();
            sb.setLength(0);
            pstmt = conn.prepareStatement(query);
            int scc = 0;
            if (status == 1) {
                pstmt.setInt(++scc, 2);
            } else {
                pstmt.setInt(++scc, 1);
            }
            pstmt.setInt(++scc, userId);
            pstmt.setString(++scc, currDate1());
            pstmt.setInt(++scc, experiencedetailId);
            pstmt.setInt(++scc, candidateId);
            print(this, "deleteexperiencedetail :: " + pstmt.toString());
            cc = pstmt.executeUpdate();
            updateverification(5, userId, candidateId, experiencedetailId);
        } catch (Exception exception) {
            print(this, "deleteeducation :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        createHistoryAccess(null, userId, ipAddrStr, iplocal, "Data Deleted", 7, experiencedetailId);
        return cc;
    }

    public ArrayList getPics(int clientassetId) 
    {
        ArrayList list = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT t_candidatefiles.i_fileid, t_candidatefiles.s_name, DATE_FORMAT(t_candidatefiles.ts_regdate, '%d-%b-%Y %H:%i'), ");
        sb.append("t_userlogin.s_name, t_candidatefiles.s_localname ");
        sb.append("FROM t_candidatefiles LEFT JOIN t_userlogin ON (t_userlogin.i_userid = t_candidatefiles.i_userid) ");
        sb.append("WHERE t_candidatefiles.i_candidateid = ? ");
        sb.append(" ORDER BY t_candidatefiles.i_fileid desc");
        String query = (sb.toString()).intern();
        sb.setLength(0);
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, clientassetId);
            logger.info("getPics :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String filename, date, name, localFile;
            int fileId;
            while (rs.next()) 
            {
                fileId = rs.getInt(1);
                filename = rs.getString(2) != null ? rs.getString(2) : "";
                date = rs.getString(3) != null ? rs.getString(3) : "";
                name = rs.getString(4) != null ? rs.getString(4) : "";
                localFile = rs.getString(5) != null ? rs.getString(5) : "";
                list.add(new CandidateInfo(fileId, filename, date, name, localFile));
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return list;
    }
    
    public int deldocumentfiles(int clientassetpicId) 
    {
        int cc = 0;
        String query = "SELECT s_name FROM t_documentfiles WHERE i_fileid = ?";
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, clientassetpicId);
            logger.info("deldocumentfiles :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String filename;
            while (rs.next()) 
            {
                filename = rs.getString(1) != null ? rs.getString(1) : "";
                if (!filename.equals("")) 
                {
                    File f = new File(getMainPath("add_candidate_file") + filename);
                    if (f.exists()) 
                    {
                        f.delete();
                    }
                }
            }
            rs.close();
            query = "delete FROM t_documentfiles WHERE i_fileid = ?";
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, clientassetpicId);
            logger.info("deldocumentfiles :: " + pstmt.toString());
            cc = pstmt.executeUpdate();
        } catch (Exception exception) {
            print(this, "deldocumentfiles :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return cc;
    }
    
    public ArrayList getdocumentfiles(int clientassetId) 
    {
        ArrayList list = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT t_documentfiles.i_fileid, t_documentfiles.s_name, DATE_FORMAT(t_documentfiles.ts_regdate, '%d-%b-%Y %H:%i'), ");
        sb.append("t_userlogin.s_name ");
        sb.append("FROM t_documentfiles LEFT JOIN t_userlogin ON (t_userlogin.i_userid = t_documentfiles.i_userid) ");
        sb.append("WHERE t_documentfiles.i_govid = ? ");
        sb.append(" ORDER BY t_documentfiles.i_fileid desc");
        String query = (sb.toString()).intern();
        sb.setLength(0);
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, clientassetId);
            logger.info("getPics :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String filename, date, name;
            int fileId;
            while (rs.next()) 
            {
                fileId = rs.getInt(1);
                filename = rs.getString(2) != null ? rs.getString(2) : "";
                date = rs.getString(3) != null ? rs.getString(3) : "";
                name = rs.getString(4) != null ? rs.getString(4) : "";
                list.add(new CandidateInfo(fileId, filename, date, name, ""));
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return list;
    }
    
    public int createdocumentfiles(Connection conn, int candidateId, String filename, int userId) 
    {
        int cc = 0;
        try 
        {
            StringBuilder sb = new StringBuilder();
            sb.append("INSERT INTO t_documentfiles ");
            sb.append("(i_govid, s_name, i_userid,i_status, ts_regdate, ts_moddate) ");
            sb.append("VALUES (?, ?, ?, ?,?,?)");
            String query = (sb.toString()).intern();
            sb.setLength(0);
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, candidateId);
            pstmt.setString(2, filename);
            pstmt.setInt(3, userId);
            pstmt.setInt(4, 1);
            pstmt.setString(5, currDate1());
            pstmt.setString(6, currDate1());
            print(this,"createPic :: " + pstmt.toString());
            cc = pstmt.executeUpdate();
        } catch (Exception exception) {
            print(this, "createPic :: " + exception.getMessage());
        } finally {
            close(null, pstmt, rs);
        }
        return cc;
    }

    public int createPic(Connection conn, int candidateId, String filename, int userId, String localFile) 
    {
        int cc = 0;
        try 
        {
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
            print(this,"createPic :: " + pstmt.toString());
            cc = pstmt.executeUpdate();
        } catch (Exception exception) {
            print(this, "createPic :: " + exception.getMessage());
        } finally {
            close(null, pstmt, rs);
        }
        return cc;
    }

    public int delpic(int clientassetpicId)
    {
        int cc = 0;
        String query = "SELECT s_name FROM t_candidatefiles WHERE i_fileid = ?";
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, clientassetpicId);
            logger.info("delpic :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String filename;
            while (rs.next()) {
                filename = rs.getString(1) != null ? rs.getString(1) : "";
                if (!filename.equals("")) {
                    File f = new File(getMainPath("add_candidate_file") + filename);
                    if (f.exists()) {
                        f.delete();
                    }
                }
            }
            rs.close();
            query = "DELETE FROM t_candidatefiles WHERE i_fileid = ?";
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, clientassetpicId);
            logger.info("delpic :: " + pstmt.toString());
            cc = pstmt.executeUpdate();
        } catch (Exception exception) {
            print(this, "delpic :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return cc;
    }

    public int updatefile(int childId, int type) 
    {
        int cc = 0;
        String query = "";
        if (type == 1) {
            query = "SELECT s_filename FROM t_candlang WHERE i_candlangid = ?";
        } else if (type == 2) {
            query = "SELECT s_filenameexp FROM t_workexperience WHERE i_experienceid = ?";
        }

        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, childId);
            logger.info("updatefile :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String filename;
            while (rs.next()) {
                filename = rs.getString(1) != null ? rs.getString(1) : "";
                if (!filename.equals("")) {
                    File f = new File(getMainPath("add_candidate_file") + filename);
                    if (f.exists()) {
                        f.delete();
                    }
                }
            }
            rs.close();
            if (type == 1) {
                query = "UPDATE t_candlang SET s_filename = '' WHERE i_candlangid = ?";
            } else if (type == 2) {
                query = "UPDATE t_workexperience SET s_filenameexp = '' WHERE i_experienceid = ?";
            }

            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, childId);
            logger.info("updatefile :: " + pstmt.toString());
            cc = pstmt.executeUpdate();
        } catch (Exception exception) {
            print(this, "updatefile :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return cc;
    }

    public int deleteCandidate(int candidateId, int userId, int status, String ipAddrStr, String iplocal) 
    {
        int cc = 0;
        try 
        {
            conn = getConnection();
            StringBuilder sb = new StringBuilder();
            sb.append("UPDATE t_candidate SET ");
            sb.append("i_status = ?, ");
            sb.append("i_userid = ?, ");
            sb.append("ts_moddate = ? ");
            sb.append("WHERE i_candidateid = ? ");
            String query = (sb.toString()).intern();
            sb.setLength(0);
            pstmt = conn.prepareStatement(query);
            int scc = 0;
            if (status == 1) {
                pstmt.setInt(++scc, 2);
            } else {
                pstmt.setInt(++scc, 1);
            }
            pstmt.setInt(++scc, userId);
            pstmt.setString(++scc, currDate1());
            pstmt.setInt(++scc, candidateId);
            print(this, "deleteCandidate :: " + pstmt.toString());
            cc = pstmt.executeUpdate();
        } catch (Exception exception) {
            print(this, "deleteCandidate :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        createHistoryAccess(null, userId, ipAddrStr, iplocal, "Status changed successfully", 7, candidateId);
        return cc;
    }

    public String checkEmail(int candidateId, String email) 
    {
        String output = "";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try 
        {
            conn = getConnection();
            String query = ("SELECT i_candidateid FROM t_candidate WHERE s_email = ? ").intern();
            if (candidateId > 0) {
                query += " AND i_candidateid != ?";
            }
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, email);
            if (candidateId > 0) {
                pstmt.setInt(2, candidateId);
            }
            logger.info("checkEmailExistance ::" + pstmt.toString());
            rs = pstmt.executeQuery();
            if (rs.next()) {
                output = "YES";
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return output;
    }

    public String getPartyAutoFill(String val) 
    {
        String s = "";
        String query = ("SELECT distinct(s_clientpartyname) FROM t_workexperience WHERE s_clientpartyname LIKE ? ORDER BY s_clientpartyname ");
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, "%" + (val) + "%");
            rs = pstmt.executeQuery();
            JSONArray jsonArrayParent = new JSONArray();
            String partyname;
            while (rs.next()) 
            {
                partyname = rs.getString(1) != null ? rs.getString(1) : "";
                if (!partyname.equals(""))
                {
                    JSONObject jsonObjectRow = new JSONObject();
                    jsonObjectRow.put("value", partyname);
                    jsonObjectRow.put("label", partyname);
                    jsonArrayParent.put(jsonObjectRow);
                }
            }
            s = jsonArrayParent.toString();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return s;
    }

    // For Index Excel report
    public ArrayList getCandidateListForExcel(String search, int statusIndex,int onlineflag, 
            int statustype, int assettypeIdIndex, int positionIdIndex) 
    {
        ArrayList list = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT c.i_candidateid, DATE_FORMAT(c.ts_regdate, '%d-%b-%Y'), CONCAT_WS(' ', c.s_firstname, c.s_middlename, c.s_lastname), t_country.s_name, c.i_status, ");
        sb.append("t_city.s_name, t_position.s_name, t_candidate.s_contactno1, c.s_econtactno1, t_candidate.s_email, DATE_FORMAT(t_candidate.d_dob, '%d-%b-%Y'), ");
        sb.append("t_candidate.s_placeofbirth, t_candidate.s_gender, t_maritialstatus.s_name, n.s_nationality, t_experiencedept.s_name, c.i_expectedsalary, t_currency.s_name, ");
        sb.append("t_candidate.s_nextofkin, t_relation.s_name, c.s_address1line1, c.s_address1line2, c.s_address1line3, c.s_address2line1, c.s_address2line2, c.s_address2line3, ");
        sb.append("t_grade.s_name, c.s_code1, c.s_code2, c.s_code3, c.s_contactno3, t_assettype.s_name, c.s_empno ");
        sb.append("FROM t_candidate AS c ");
        sb.append("LEFT JOIN t_country ON ( c.i_countryid = t_country.i_countryid ) ");
        sb.append("LEFT JOIN t_city ON ( c.i_cityid = t_city.i_cityid ) ");
        sb.append("LEFT JOIN t_position ON ( c.i_positionid = t_position.i_positionid ) ");
        sb.append("LEFT JOIN t_candidate ON (t_candidate.i_candidateid = c.i_candidateid) ");
        sb.append("LEFT JOIN t_maritialstatus ON (t_maritialstatus.i_maritialstatusid = c.i_maritialstatusid) ");
        sb.append("LEFT JOIN t_experiencedept ON (t_experiencedept.i_experiencedeptid = c.i_departmentid) ");
        sb.append("LEFT JOIN t_country AS n ON (n.i_countryid = c.i_nationalityid) ");
        sb.append("LEFT JOIN t_currency ON (t_currency.i_currencyid = c.i_currencyid) ");
        sb.append("LEFT JOIN t_relation ON (t_relation.i_relationid = c.i_relationid) ");
        sb.append("LEFT JOIN t_grade ON (t_grade.i_gradeid = t_position.i_gradeid) ");
        sb.append("LEFT JOIN t_assettype ON (t_assettype.i_assettypeid = c.i_assettypeid) ");
        sb.append("WHERE 0 = 0 ");        
        if (statusIndex > 0) {
            sb.append("AND c.i_status =? ");
        }
        if (search != null && !search.equals("")) {
            sb.append("AND (CONCAT_WS(' ', c.s_firstname, c.s_middlename, c.s_lastname) LIKE ? OR CONCAT_WS(' ', c.s_firstname, c.s_lastname) LIKE ? OR c.i_candidateid LIKE ? ");
            sb.append("OR DATE_FORMAT(c.ts_regdate, '%d-%b-%Y') LIKE ? OR t_country.s_name LIKE ?  OR t_city.s_name LIKE ? OR t_position.s_name LIKE ?) ");
        }
        if (onlineflag > 0) {
            sb.append(" AND c.i_online = ? ");
        }
        if (assettypeIdIndex > 0) {
            sb.append("AND c.i_assettypeid = ? ");
        }
        if (positionIdIndex > 0) {
            sb.append("AND c.i_positionid = ? ");
        }
        if (statustype > 0) 
        {
            if(statustype == 1)
            {
               sb.append(" AND (c.i_vflag NOT IN (3,4) OR c.i_pass != 2) ");
            }
            else if(statustype == 2)
            {
                sb.append(" AND (c.i_vflag IN (3,4) AND c.i_pass = 2) ");
            }
        }
        String query = (sb.toString()).intern();
        sb.setLength(0);
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            int scc = 0;
            if (statusIndex > 0) {
                pstmt.setInt(++scc, statusIndex);
            }
            if (search != null && !search.equals("")) 
            {
                pstmt.setString(++scc, "%" + (search) + "%");
                pstmt.setString(++scc, "%" + (search) + "%");
                pstmt.setString(++scc, search.replaceFirst("^0+(?!$)", ""));
                pstmt.setString(++scc, "%" + (search) + "%");
                pstmt.setString(++scc, "%" + (search) + "%");
                pstmt.setString(++scc, "%" + (search) + "%");
                pstmt.setString(++scc, "%" + (search) + "%");
            }
            if (onlineflag > 0) {
                pstmt.setInt(++scc, onlineflag);
            }
            if (assettypeIdIndex > 0) {
                pstmt.setInt(++scc, assettypeIdIndex);
            }
            if (positionIdIndex > 0) {
                pstmt.setInt(++scc, positionIdIndex);
            }
            print(this, "getCandidateListForExcel :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String name, countryName, date, cityName, positionName;
            int candidateId, status;
            while (rs.next()) 
            {
                candidateId = rs.getInt(1);
                date = rs.getString(2) != null ? rs.getString(2) : "";
                name = rs.getString(3) != null ? rs.getString(3) : "";
                countryName = rs.getString(4) != null ? rs.getString(4) : "";
                status = rs.getInt(5);
                cityName = rs.getString(6) != null ? rs.getString(6) : "";
                positionName = rs.getString(7) != null ? rs.getString(7) : "";
                String contactno1 = decipher(rs.getString(8) != null ? rs.getString(8) : "");
                String econactno = decipher(rs.getString(9) != null ? rs.getString(9) : "");
                String email = rs.getString(10) != null ? rs.getString(10) : "";
                String dob = rs.getString(11) != null ? rs.getString(11) : "";
                String placeofbirth = rs.getString(12) != null ? rs.getString(12) : "";
                String gender = rs.getString(13) != null ? rs.getString(13) : "";
                String maritalstatus = rs.getString(14) != null ? rs.getString(14) : "";
                String nationality = rs.getString(15) != null ? rs.getString(15) : "";
                String prefferddept = rs.getString(16) != null ? rs.getString(16) : "";
                int expectedsalary = rs.getInt(17);
                String currency = rs.getString(18) != null ? rs.getString(18) : "";
                String nextofkin = decipher(rs.getString(19) != null ? rs.getString(19) : "");
                String relation = rs.getString(20) != null ? rs.getString(20) : "";
                String address1line1 = rs.getString(21) != null ? rs.getString(21) : "";
                String address1line2 = rs.getString(22) != null ? rs.getString(22) : "";
                String address1line3 = rs.getString(23) != null ? rs.getString(23) : "";
                String address2line1 = rs.getString(24) != null ? rs.getString(24) : "";
                String address2line2 = rs.getString(25) != null ? rs.getString(25) : "";
                String address2line3 = rs.getString(26) != null ? rs.getString(26) : "";
                String gradeName = rs.getString(27) != null ? rs.getString(27) : "";
                String isdcode1 = rs.getString(28) != null ? rs.getString(28) : "";
                String isdcode2 = rs.getString(29) != null ? rs.getString(29) : "";
                String isdcode3 = rs.getString(30) != null ? rs.getString(30) : "";
                String seccontact = decipher(rs.getString(31) != null ? rs.getString(31) : "");
                String assettype = rs.getString(32) != null ? rs.getString(32) : "";
                String employeeId = rs.getString(33) != null ? rs.getString(33) : "";                
                if (gradeName != null && !gradeName.equals("")) {
                    positionName = positionName + " | " + gradeName;
                }
                if (contactno1 != null && !contactno1.equals("")) {
                    if (isdcode1 != null && !isdcode1.equals("")) {
                        contactno1 = "+" + isdcode1 + " " + contactno1;
                    }
                }
                if (econactno != null && !econactno.equals("")) {
                    if (isdcode2 != null && !isdcode2.equals("")) {
                        econactno = "+" + isdcode2 + " " + econactno;
                    }
                }
                if (seccontact != null && !seccontact.equals("")) {
                    if (isdcode3 != null && !isdcode3.equals("")) {
                        seccontact = "+" + isdcode3 + " " + seccontact;
                    }
                }
                list.add(new CandidateInfo(candidateId, name, countryName, status, date, cityName, positionName, contactno1, econactno, email, dob, placeofbirth, gender,
                        maritalstatus, nationality, prefferddept, expectedsalary, currency, nextofkin, relation, address1line1, address1line2, address1line3, address2line1,
                        address2line2, address2line3, gradeName, seccontact, assettype, employeeId));
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return list;
    }

    public int updateverification(int tabno, int userId, int candidateId, int childId) 
    {
        int cc = 0;
        try 
        {
            conn = getConnection();
            String currdate = currDate1();
            if (tabno == 1 || tabno == 3) 
            {
                String query = "";
                if (tabno == 1) {
                    query = "UPDATE t_candidate SET i_cflag1 = ? WHERE i_candidateid = ?";
                } else {
                    query = "UPDATE t_candidate SET i_cflag3 = ? WHERE i_candidateid = ?";
                }
                pstmt = conn.prepareStatement(query);
                pstmt.setInt(1, 0);
                pstmt.setInt(2, candidateId);
                pstmt.executeUpdate();
            } else {
                String tablaname = "", child_id = "", cflagname = "";
                if (tabno == 2) {
                    tablaname = "t_candlang";
                    child_id = "i_candlangid";
                    cflagname = "i_cflag2";
                } else if (tabno == 4) {
                    tablaname = "t_vbd";
                    child_id = "i_vbdid";
                    cflagname = "i_cflag4";
                } else if (tabno == 5) {
                    tablaname = "t_workexperience";
                    child_id = "i_experienceid";
                    cflagname = "i_cflag5";
                } else if (tabno == 6) {
                    tablaname = "t_eduqual";
                    child_id = "i_eduqulid";
                    cflagname = "i_cflag6";
                } else if (tabno == 7) {
                    tablaname = "t_trainingandcert";
                    child_id = "i_tcid";
                    cflagname = "i_cflag7";
                }else if (tabno == 9) {
                    tablaname = "t_bankdetail";
                    child_id = "i_bankdetid";
                    cflagname = "i_cflag9";
                } else if (tabno == 10) {
                    tablaname = "t_govdoc";
                    child_id = "i_govid";
                    cflagname = "i_cflag10";
                }

                String updatechild = "UPDATE " + tablaname + " SET i_vstatus = ?, i_userid = ?, ts_moddate = ? WHERE " + child_id + " = ?";
                pstmt = conn.prepareStatement(updatechild);
                pstmt.setInt(1, 0);
                pstmt.setInt(2, userId);
                pstmt.setString(3, currdate);
                pstmt.setInt(4, childId);
                pstmt.executeUpdate();

                String countquery = "SELECT t1.ct, t2.ct, t3.ct FROM (SELECT COUNT(1) AS ct FROM " + tablaname + " WHERE i_candidateid = ? AND i_status = 1) AS t1, "
                        + "(SELECT COUNT(1) AS ct FROM " + tablaname + " WHERE i_candidateid = ? AND i_status = 1 AND i_vstatus = 2) AS t2, "
                        + "(SELECT COUNT(1) AS ct FROM " + tablaname + " WHERE i_candidateid = ? AND i_status = 1 AND i_vstatus = 1) AS t3";
                pstmt = conn.prepareStatement(countquery);
                pstmt.setInt(1, candidateId);
                pstmt.setInt(2, candidateId);
                pstmt.setInt(3, candidateId);
                rs = pstmt.executeQuery();
                int totalcount = 0, countst2 = 0, countst1 = 0;
                while (rs.next()) 
                {
                    totalcount = rs.getInt(1);
                    countst2 = rs.getInt(2);
                    countst1 = rs.getInt(3);
                }
                rs.close();

                int childst = 0;
                if (totalcount == countst2) {
                    childst = 2;
                } else if (countst2 + countst1 == 0) {
                    childst = 0;
                } else if (totalcount > (countst2 + countst1)) {
                    childst = 3;
                } else {
                    childst = 1;
                }

                String query = "UPDATE t_candidate SET " + cflagname + " = ? WHERE i_candidateid = ?";
                pstmt = conn.prepareStatement(query);
                pstmt.setInt(1, childst);
                pstmt.setInt(2, candidateId);
                pstmt.executeUpdate();
            }
            String query = "SELECT i_cflag1, i_cflag2, i_cflag3, i_cflag4, i_cflag5, i_cflag6, i_cflag7, i_cflag8, i_cflag9, i_cflag10, i_vflag FROM t_candidate WHERE i_candidateid = ?";
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, candidateId);
            rs = pstmt.executeQuery();
            int f1 = 0, f2 = 0, f3 = 0, f4 = 0, f5 = 0, f6 = 0, f7 = 0, f8 = 0, f9 = 0, f10 = 0, vf = 0;
            while (rs.next()) {
                f1 = rs.getInt(1);
                f2 = rs.getInt(2);
                f3 = rs.getInt(3);
                f4 = rs.getInt(4);
                f5 = rs.getInt(5);
                f6 = rs.getInt(6);
                f7 = rs.getInt(7);
                f8 = rs.getInt(8);
                f9 = rs.getInt(9);
                f10 = rs.getInt(10);
                vf = rs.getInt(11);
            }
            rs.close();
            if (vf == 3 || vf == 4) {
                int vflag = getStVal(f1, f2, f3, f4, f5, f6, f7, f8, f9, f10);
                if (vflag == 4) {
                    query = "UPDATE t_candidate SET i_vflag = ?, i_vflagav = 0, i_userid = ?, ts_moddate = ? WHERE i_candidateid = ?";
                } else {
                    query = "UPDATE t_candidate SET i_vflagav = 1, i_userid = ?, ts_moddate = ? WHERE i_candidateid = ?";
                }
                pstmt = conn.prepareStatement(query);
                int scc = 0;
                if (vflag == 4) {
                    pstmt.setInt(++scc, vflag);
                }
                pstmt.setInt(++scc, userId);
                pstmt.setString(++scc, currdate);
                pstmt.setInt(++scc, candidateId);
                pstmt.executeUpdate();
            } else {
                int vflag = getStVal(f1, f2, f3, f4, f5, f6, f7, f8, f9, f10);
                if (vflag == 4 || vflag == 3) {
                    query = "UPDATE t_candidate SET i_vflag = ?, i_vflagav = 0, i_userid = ?, ts_moddate = ? WHERE i_candidateid = ?";
                } else {
                    query = "UPDATE t_candidate SET i_vflag = ?, i_userid = ?, ts_moddate = ? WHERE i_candidateid = ?";
                }
                pstmt = conn.prepareStatement(query);
                pstmt.setInt(1, vflag);
                pstmt.setInt(2, userId);
                pstmt.setString(3, currdate);
                pstmt.setInt(4, candidateId);
                pstmt.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return cc;
    }

    public int getStVal(int f1, int f2, int f3, int f4, int f5, int f6, int f7, int f8, int f9, int f10) {
        int st = 1;
        if (f1 == 2 && f2 == 2 && f3 == 2 && f4 == 2 && f5 == 2 && f6 == 2 && f7 == 2 && f9 == 2 && f10 == 2) {
            st = 4;
        } else if (f1 == 0 && f2 == 0 && f3 == 0 && f4 == 0 && f5 == 0 && f6 == 0 && f7 == 0 && f9 == 0 && f10 == 0) {
            st = 1;
        } else if ((f1 == 0 || f1 == 3) || (f2 == 0 || f2 == 3) || (f3 == 0 || f3 == 3) || (f4 == 0 || f4 == 3) || (f5 == 0 || f5 == 3) || (f6 == 0 || f6 == 3) || (f7 == 0 || f7 == 3) || (f9 == 0 || f9 == 3) || (f10 == 0 || f10 == 3)) {
            st = 2;
        } else {
            st = 3;
        }
        return st;
    }

    public int getCountryCode(int countryId)
    {
        int code = 0;
        StringBuilder sb = new StringBuilder();
        if (countryId > 0) 
        {
            sb.append("SELECT s_stdcode FROM t_country WHERE i_countryid =? AND i_status =1");
            String query = (sb.toString()).intern();
            sb.setLength(0);
            try 
            {
                conn = getConnection();
                pstmt = conn.prepareStatement(query);
                int scc = 0;
                pstmt.setInt(++scc, countryId);
                rs = pstmt.executeQuery();
                while (rs.next()) 
                {
                    code = rs.getInt(1);
                }
                rs.close();
            } catch (Exception exception) {
                print(this, "getCountryCode :: " + exception.getMessage());
            } finally {
                close(conn, pstmt, rs);
            }
        }
        return code;
    }

    public Collection getCourseName() 
    {
        Collection coll = new LinkedList();
        coll.add(new CandidateInfo(-1, "- Select -"));
        String xml_path = getMainPath("json_path");
        String str = readHTMLFile("coursename.json", xml_path);
        JSONArray arr = new JSONArray(str);
        if (arr != null) {
            int len = arr.length();
            for (int i = 0; i < len; i++)
            {
                JSONObject jobj = arr.optJSONObject(i);
                if (jobj != null) {
                    coll.add(new CandidateInfo(jobj.optInt("id"), jobj.optString("name")));
                }
            }
        }
        return coll;
    }
    
        public CandidateInfo getexperiencedetailfeedbackById(int experiencedetailId) 
        {
        CandidateInfo info = null;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT s_companyname, t_companyindustry.s_name, t_assettype.s_name, s_assetname, t_position.s_name, t_experiencedept.s_name, t_country.s_name, ");
        sb.append("t_workexperience.i_cityid, s_clientpartyname, t_experiencewaterdepth.i_depth, t_currency.s_name, i_currentworkingstatus, t_skills.s_name, t_grade.s_name, s_ownerpool, ");
        sb.append("t_crewtype.s_name, s_ocsemployed, s_legalrights, a.s_name, i_dayrate, b.s_name, i_monthlysalary, ");
        sb.append("DATE_FORMAT(t_workexperience.d_workstartdate, '%d-%b-%Y') ,DATE_FORMAT(t_workexperience.d_workenddate, '%d-%b-%Y'), i_lastdrawnsalary, s_filenamework, ");
        sb.append("s_filenameexp, t_workexperience.i_status, t_city.s_name, t_experiencewaterdepth.s_unitmeasurement  FROM t_workexperience ");
        sb.append("LEFT JOIN t_companyindustry ON (t_companyindustry.i_companyindustryid = t_workexperience.i_companyindustryid)");
        sb.append("LEFT JOIN t_experiencedept ON (t_experiencedept.i_experiencedeptid = t_workexperience.i_departmentid)");
        sb.append("LEFT JOIN t_assettype ON (t_assettype.i_assettypeid = t_workexperience.i_assettypeid)");
        sb.append("LEFT JOIN t_position ON (t_position.i_positionid = t_workexperience.i_positionid)");
        sb.append("LEFT JOIN t_country ON (t_country.i_countryid = t_workexperience.i_countryid)");
        sb.append("LEFT JOIN t_currency ON (t_currency.i_currencyid = t_workexperience.i_lastdrawnsalarycurrencyid)");
        sb.append("LEFT JOIN t_currency AS a ON (a.i_currencyid = t_workexperience.i_dayratecurrencyid)");
        sb.append("LEFT JOIN t_currency AS b ON (b.i_currencyid = t_workexperience.i_monthlysalarycurrencyid)");
        sb.append("LEFT JOIN t_skills ON (t_skills.i_skillsid = t_workexperience.i_skillid)");
        sb.append("LEFT JOIN t_grade ON (t_grade.i_gradeid = t_workexperience.i_gradeid)");
        sb.append("LEFT JOIN t_crewtype ON (t_crewtype.i_crewtypeid = t_workexperience.i_crewtypeid)");
        sb.append("LEFT JOIN t_experiencewaterdepth ON (t_experiencewaterdepth.i_experiencewaterdepthid = t_workexperience.i_waterdepthid)");
        sb.append("LEFT JOIN t_city ON (t_city.i_cityid = t_workexperience.i_cityid) WHERE i_experienceid =? ");//14
        String query = sb.toString().intern();
        sb.setLength(0);
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, experiencedetailId);
            print(this, "getexperiencedetailById :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            while (rs.next())
            {
                String companyName = rs.getString(1) != null ?  rs.getString(1) : "";
                String companyindustry = rs.getString(2) != null ?  rs.getString(2) : "";
                String assettype = rs.getString(3) != null ?  rs.getString(3) : "";
                String assetName = rs.getString(4) != null ?  rs.getString(4) : "";
                String position = rs.getString(5) != null ?  rs.getString(5) : "";
                String department = rs.getString(6) != null ?  rs.getString(6) : "";
                String country = rs.getString(7) != null ?  rs.getString(7) : "";
                int cityId = rs.getInt(8);
                String clientpartyname = rs.getString(9) != null ?  rs.getString(9) : "";
                String waterdepth = rs.getString(10) != null ?  rs.getString(10) : "";
                String lastdrawnsalarycurrency = rs.getString(11) != null ?  rs.getString(11) : "";
                int currentworkingstatus = rs.getInt(12);
                String skill = rs.getString(13) != null ?  rs.getString(13) : "";
                String grade = rs.getString(14) != null ?  rs.getString(14) : "";
                String ownerpool = rs.getString(15) != null ?  rs.getString(15) : "";
                String crewtype = rs.getString(16) != null ?  rs.getString(16) : "";
                String ocsemployed = rs.getString(17) != null ?  rs.getString(17) : "";
                String legalrights = rs.getString(18) != null ?  rs.getString(18) : "";
                String dayratecurrency = rs.getString(19) != null ?  rs.getString(19) : "";
                int dayrate = rs.getInt(20);
                String monthlysalarycurrency = rs.getString(21) != null ?  rs.getString(21) : "";
                int monthlysalary = rs.getInt(22);
                String workstartdate = rs.getString(23) != null ?  rs.getString(23) : "";
                String workenddate = rs.getString(24) != null ?  rs.getString(24) : "";
                String lastdrawnsalary = rs.getString(25) != null ?  rs.getString(25) : "";
                String filenamework = rs.getString(26) != null ?  rs.getString(26) : "";
                String filenameexp = rs.getString(27) != null ?  rs.getString(27) : "";
                int status = rs.getInt(28);
                String cityname = rs.getString(29) != null ?  rs.getString(29) : "";
                String waterdepthunit = rs.getString(30) != null ?  rs.getString(30) : "";
                info = new CandidateInfo(companyName, companyindustry, assettype, assetName, position, department, country, cityId, clientpartyname,
                        waterdepth, lastdrawnsalarycurrency, currentworkingstatus, skill, grade, ownerpool, crewtype, ocsemployed, legalrights, dayratecurrency,
                        dayrate, monthlysalarycurrency, monthlysalary, workstartdate, workenddate, lastdrawnsalary, filenamework, filenameexp, cityname, status, waterdepthunit);
            }
        } catch (Exception exception) {
            print(this, "getexperiencedetailById :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return info;
    }
        
    public CandidateInfo gettrainingCertificateDetailfeedbackById(int candidateId)
    {
        CandidateInfo info = null;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT t_coursetype.s_name, t_coursename.s_name, s_educinst, i_locationofinstitid, s_fieldofstudy, DATE_FORMAT(t_trainingandcert.d_coursestart, '%d-%b-%Y'), ");
        sb.append("DATE_FORMAT(t_trainingandcert.d_passingyear, '%d-%b-%Y'), DATE_FORMAT(t_trainingandcert.d_dateofissue, '%d-%b-%Y'), t_trainingandcert.s_certificateno, ");
        sb.append("DATE_FORMAT(t_trainingandcert.d_expirydate, '%d-%b-%Y'), s_courseverification, t_approvedby.s_name, t_trainingandcert.s_filename, ");
        sb.append("t_trainingandcert.i_status, t_city.s_name, t_country.s_name, t_trainingandcert.s_url ");
        sb.append("FROM t_trainingandcert ");
        sb.append("LEFT JOIN t_city ON (t_city.i_cityid = t_trainingandcert.i_locationofinstitid) ");
        sb.append("LEFT JOIN t_approvedby ON (t_approvedby.i_approvedbyid = t_trainingandcert.i_approvedbyid) ");
        sb.append("LEFT JOIN t_coursetype ON (t_coursetype.i_coursetypeid = t_trainingandcert.i_coursetypeid) ");
        sb.append("LEFT JOIN t_coursename ON (t_coursename.i_coursenameid = t_trainingandcert.i_coursenameid) ");
        sb.append("LEFT JOIN t_country ON (t_country.i_countryid = t_city.i_countryid) WHERE i_tcid = ? ");//14
        String query = sb.toString().intern();
        sb.setLength(0);
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, candidateId);
            print(this, "gettrainingCertificateDetailfeedbackById :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            while (rs.next()) 
            {
                String coursetype = rs.getString(1) != null ?  rs.getString(1) : "";
                String coursename = rs.getString(2) != null ?  rs.getString(2) : "";
                String educinst = rs.getString(3) != null ? rs.getString(3) : "";
                int locationofinstit = rs.getInt(4);
                String fieldofstudy = rs.getString(5) != null ? rs.getString(5) : "";
                String coursestart = rs.getString(6) != null ? rs.getString(6) : "";
                String passingyear = rs.getString(7) != null ? rs.getString(7) : "";
                String dateofissue = rs.getString(8) != null ? rs.getString(8) : "";
                String certificateno = decipher(rs.getString(9) != null ? rs.getString(9) : "");
                String expirydate = rs.getString(10) != null ? rs.getString(10) : "";
                String courseverification = rs.getString(11) != null ? rs.getString(11) : "";
                String approvedby = rs.getString(12) != null ?  rs.getString(12) : "";
                String filename = rs.getString(13) != null ? rs.getString(13) : "";
                int status = rs.getInt(14);
                String cityName = rs.getString(15) != null ? rs.getString(15) : "";
                String countryName = rs.getString(16) != null ? rs.getString(16) : "";
                String url = rs.getString(17) != null ? rs.getString(17) : "";
                if (!countryName.equals("")) 
                {
                    cityName += " (" + countryName + ")";
                }
                info = new CandidateInfo(coursetype, coursename, educinst, locationofinstit, fieldofstudy,
                        coursestart, passingyear, dateofissue, certificateno, expirydate, courseverification,
                        approvedby, filename, status, cityName, url);
            }
        } catch (Exception exception) {
            print(this, "gettrainingCertificateDetailfeedbackById :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return info;
    }
    
    //For Health files Listing
    public ArrayList getHealthFileList(int candidateId) 
    {
        ArrayList list = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT s_name, DATE_FORMAT(ts_regdate, '%d-%b-%Y %H:%i'), i_healthfileid FROM t_healthfile WHERE i_candidateid = ? ORDER BY ts_regdate DESC ");
        String query = (sb.toString()).intern();
        sb.setLength(0);
        try
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, candidateId);
            logger.info("getHealthFileList :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String filename, date;
            int healthId;
            while (rs.next())
            {
                filename = rs.getString(1) != null ? rs.getString(1) : "";
                date = rs.getString(2) != null ? rs.getString(2) : "";
                healthId = rs.getInt(3);
                list.add(new CandidateInfo(filename, date, healthId));
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return list;
    }    
    
    public int delhelathfile(int healthfileId)
    {
        int cc = 0;
        String query = "SELECT s_name FROM t_healthfile WHERE i_healthfileid = ?";
        try
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, healthfileId);
            logger.info("delhelathfile :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String filename;
            while (rs.next())
            {
                filename = rs.getString(1) != null ? rs.getString(1) : "";
                if (!filename.equals(""))
                {
                    File f = new File(getMainPath("add_candidate_file") + filename);
                    if (f.exists()) {
                        f.delete();
                    }
                }
            }
            rs.close();
            query = "DELETE FROM t_healthfile WHERE i_healthfileid = ?";
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, healthfileId);
            logger.info("delhelathfile :: " + pstmt.toString());
            cc = pstmt.executeUpdate();
        }
        catch (Exception exception)
        {
            print(this, "delhelathfile :: " + exception.getMessage());
        }
        finally
        {
            close(conn, pstmt, rs);
        }
        return cc;
    }
    
    public ArrayList getFeedbcakgovdocList(int candidateId, String search, int exp) 
    {
        ArrayList list = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT t_govdoc.i_govid, t_doctype.s_doc , t_govdoc.s_docno ,t_city.s_name,t_documentissuedby.s_name, t_govdoc.i_status , ");
        sb.append("DATE_FORMAT(t_govdoc.d_dateofissue, '%d-%b-%Y'),DATE_FORMAT(t_govdoc.d_expirydate, '%d-%b-%Y') , t_country.s_name, t1.c1 ");
        sb.append("FROM t_govdoc ");
        sb.append("LEFT JOIN t_doctype ON (t_govdoc.i_doctypeid = t_doctype.i_doctypeid) ");
        sb.append("LEFT JOIN t_city ON (t_govdoc.i_placeofissueid = t_city.i_cityid) ");
        sb.append("LEFT JOIN t_documentissuedby ON (t_govdoc.i_documentissuedbyid = t_documentissuedby.i_documentissuedbyid) ");
        sb.append("LEFT JOIN t_candidate ON (t_candidate.i_candidateid = t_govdoc.i_candidateid) ");
        sb.append("LEFT JOIN t_country ON (t_city.i_countryid = t_country.i_countryid) ");
        sb.append("LEFT JOIN (SELECT i_govid, COUNT(1) AS c1 FROM t_documentfiles WHERE i_status = 1 GROUP BY i_govid) AS t1 ON (t1.i_govid = t_govdoc.i_govid) ");
        sb.append("WHERE t_govdoc.i_candidateid = ? AND t_govdoc.i_status = 1 ");        
        if(exp > 0)
        {
            if(exp == 1)
                sb.append(" AND t_govdoc.d_expirydate <= DATE_ADD(CURRENT_DATE(), INTERVAL 10 DAY) AND t_govdoc.d_expirydate >= CURRENT_DATE() ");
            else if(exp == 3)
                sb.append(" AND t_govdoc.d_expirydate <= DATE_ADD(CURRENT_DATE(), INTERVAL 90 DAY) AND t_govdoc.d_expirydate >= CURRENT_DATE() ");
            else if(exp == 4)
                sb.append(" AND t_govdoc.d_expirydate <= DATE_ADD(CURRENT_DATE(), INTERVAL 180 DAY) AND t_govdoc.d_expirydate >= CURRENT_DATE() ");
            else if(exp == 5)
                sb.append(" AND t_govdoc.d_expirydate <= DATE_ADD(CURRENT_DATE(), INTERVAL 365 DAY) AND t_govdoc.d_expirydate >= CURRENT_DATE() ");
            else if(exp == 2)
                sb.append(" AND t_govdoc.d_expirydate < CURRENT_DATE() ");
        }
        if (search != null && !search.equals("")) 
        {
            sb.append("AND (t_doctype.s_doc LIKE ?) ");
        }
        sb.append("ORDER BY t_govdoc.i_status, t_doctype.s_doc ");
        String query = (sb.toString()).intern();
        sb.setLength(0);
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            int scc = 0;
            pstmt.setInt(++scc, candidateId);
            if (search != null && !search.equals(""))
            {
                pstmt.setString(++scc, "%" + (search) + "%");
            }
            logger.info("getFeedbcakgovdocList :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String documentname, documentno, placeofissue, issuedby, dateofissue, dateofexpiry, countryname;
            int govdocumentId, status, filecount;
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
                if (!countryname.equals(""))
                {
                    placeofissue += "(" + countryname + ")";
                }
                list.add(new CandidateInfo(govdocumentId, documentname, documentno, placeofissue, issuedby, 
                    status, dateofissue, dateofexpiry, countryname, 0, filecount));
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return list;
    }
    
    public ArrayList getNomineeList(int candidateId) {
        ArrayList list = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT t_nominee.i_nomineeid, t_nominee.s_nomineename, t_nominee.s_nomineecontactno, ");
        sb.append("t_relation.s_name, t_nominee.i_status, t_nominee.s_code, t_candidate.i_pass ");
        sb.append("FROM t_nominee ");
        sb.append("LEFT JOIN t_relation ON (t_relation.i_relationid = t_nominee.i_relationid) ");
        sb.append("LEFT JOIN t_candidate ON (t_candidate.i_candidateid = t_nominee.i_candidateid) ");
        sb.append("WHERE t_nominee.i_candidateid = ? ORDER BY t_nominee.i_status, t_nominee.ts_regdate DESC ");
        String query = (sb.toString()).intern();
        sb.setLength(0);
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, candidateId);
            logger.info("getNomineeList :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String nomineename, nomineecontactno, nomineerelation, code;
            int nomineedetailId, status, pass;
            while (rs.next()) {
                nomineedetailId = rs.getInt(1);
                nomineename = rs.getString(2) != null ? rs.getString(2) : "";
                nomineecontactno = rs.getString(3) != null ? rs.getString(3) : "";
                nomineerelation = rs.getString(4) != null ? rs.getString(4) : "";
                status = rs.getInt(5);
                code = rs.getString(6) != null ? rs.getString(6) : "";
                pass = rs.getInt(7);
                if (code != null && !code.equals("")) {
                    if (nomineecontactno != null && !nomineecontactno.equals("")) {
                        nomineecontactno = "+" + code + " " + nomineecontactno;
                    }
                }
                list.add(new CandidateInfo(nomineedetailId, nomineename, nomineecontactno, nomineerelation, status, code, pass));
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return list;
    }

    //For Nominee add
    public CandidateInfo getNomineeDetailById(int nomineeId) {
        CandidateInfo info = null;
        String query = "SELECT s_nomineename, s_nomineecontactno, i_relationid, s_code, s_address, i_age, d_percentage FROM t_nominee WHERE i_nomineeid = ? ";
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, nomineeId);
            print(this, "getNomineeDetailById :" + pstmt.toString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                String nomineename = rs.getString(1) != null ? rs.getString(1) : "";
                String nomineecontactno = rs.getString(2) != null ? rs.getString(2) : "";
                int relationId = rs.getInt(3);
                String codeId = rs.getString(4) != null ? rs.getString(4) : "";
                String address = rs.getString(5) != null ? rs.getString(5) : "";
                int age = rs.getInt(6);
                double percentage = rs.getDouble(7);
                info = new CandidateInfo(nomineeId, nomineename, nomineecontactno, relationId, codeId, address, age, percentage);
            }
            rs.close();
            pstmt.close();
        } catch (Exception exception) {
            print(this, "getNomineeDetailById :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return info;
    }

    public int checkDuplicacyNominee(int candidateId, int nomineedetailId, String nomineename) {
        int ck = 0;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT i_nomineeid FROM t_nominee WHERE i_candidateid = ? AND s_nomineename = ? AND i_status IN (1, 2)");
        if (nomineedetailId > 0) {
            sb.append(" AND i_nomineeid != ? ");
        }
        String query = (sb.toString()).intern();
        sb.setLength(0);
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            int scc = 0;
            pstmt.setInt(++scc, candidateId);
            pstmt.setString(++scc, nomineename);
            if (nomineedetailId > 0) {
                pstmt.setInt(++scc, nomineedetailId);
            }
            print(this, "checkDuplicacyNominee :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ck = 1;
            }
        } catch (Exception exception) {
            print(this, "checkDuplicacyNominee :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return ck;
    }

    //Nominee create
    public int createNomineedetails(CandidateInfo info, int candidateId, int userId) {
        int cc = 0;
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("INSERT INTO t_nominee  ");
            sb.append("( s_nomineename, s_nomineecontactno, i_relationid, s_code, s_address, i_age, d_percentage, ");
            sb.append(" i_status , i_userid, i_candidateid, ts_regdate, ts_moddate) ");
            sb.append("VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            String query1 = (sb.toString()).intern();
            sb.setLength(0);
            conn = getConnection();
            pstmt = conn.prepareStatement(query1, PreparedStatement.RETURN_GENERATED_KEYS);
            int scc = 0;
            pstmt.setString(++scc, (info.getNomineeName()));
            pstmt.setString(++scc, (info.getNomineeContactno()));
            pstmt.setInt(++scc, info.getRelationId());
            pstmt.setString(++scc, (info.getCode1Id()));
            pstmt.setString(++scc, (info.getAddress()));
            pstmt.setInt(++scc, info.getAge());
            pstmt.setDouble(++scc, info.getPercentage());
            pstmt.setInt(++scc, info.getStatus());
            pstmt.setInt(++scc, userId);
            pstmt.setInt(++scc, candidateId);
            pstmt.setString(++scc, currDate1());
            pstmt.setString(++scc, currDate1());
            print(this, "createNomineedetails :: " + pstmt.toString());
            pstmt.executeUpdate();
            rs = pstmt.getGeneratedKeys();
            while (rs.next()) {
                cc = rs.getInt(1);
            }
        } catch (Exception exception) {
            print(this, "createNomineedetails :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return cc;
    }

    //upadte nominee
    public int updateNominee(CandidateInfo info, int userId, int candidateId, int nomineedetailId) {
        StringBuilder sb = new StringBuilder();
        int cc = 0;
        try {
            conn = getConnection();
            sb.append("UPDATE t_nominee SET ");
            sb.append("s_nomineename = ?, ");
            sb.append("s_nomineecontactno = ?, ");
            sb.append("i_relationid = ?, ");
            sb.append("s_code = ?, ");
            sb.append("s_address = ?, ");
            sb.append("i_age = ?, ");
            sb.append("d_percentage = ?, ");
            sb.append("i_userid = ?, ");
            sb.append("ts_moddate = ? ");
            sb.append("WHERE  i_candidateid = ? AND i_nomineeid = ? ");
            String doctypequery = sb.toString().intern();
            sb.setLength(0);
            int scc = 0;
            pstmt = conn.prepareStatement(doctypequery);
            pstmt.setString(++scc, info.getNomineeName());
            pstmt.setString(++scc, info.getNomineeContactno());
            pstmt.setInt(++scc, info.getRelationId());
            pstmt.setString(++scc, info.getCode1Id());
            pstmt.setString(++scc, (info.getAddress()));
            pstmt.setInt(++scc, info.getAge());
            pstmt.setDouble(++scc, info.getPercentage());
            pstmt.setInt(++scc, userId);
            pstmt.setString(++scc, currDate1());
            pstmt.setInt(++scc, candidateId);
            pstmt.setInt(++scc, nomineedetailId);
            cc = pstmt.executeUpdate();
            pstmt.close();
        } catch (Exception exception) {
            print(this, "updateNominee :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return cc;
    }

    public int deleteNominee(int candidateId, int nomineedetailId, int userId, int status, String ipAddrStr, String iplocal) {
        int cc = 0;
        try {
            conn = getConnection();
            StringBuilder sb = new StringBuilder();
            sb.append("UPDATE t_nominee SET ");
            sb.append("i_status = ?, ");
            sb.append("i_userid = ?, ");
            sb.append("ts_moddate = ? ");
            sb.append("WHERE i_nomineeid = ? AND i_candidateid = ? ");
            String query = (sb.toString()).intern();
            sb.setLength(0);
            pstmt = conn.prepareStatement(query);
            int scc = 0;
            if (status == 1) {
                pstmt.setInt(++scc, 2);
            } else {
                pstmt.setInt(++scc, 1);
            }
            pstmt.setInt(++scc, userId);
            pstmt.setString(++scc, currDate1());
            pstmt.setInt(++scc, nomineedetailId);
            pstmt.setInt(++scc, candidateId);
            print(this, "deleteNominee :: " + pstmt.toString());
            cc = pstmt.executeUpdate();
        } catch (Exception exception) {
            print(this, "deleteNominee :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        createHistoryAccess(null, userId, ipAddrStr, iplocal, "Data Deleted", 7, nomineedetailId);
        return cc;
    }
    
    public Collection getCountryStates(int countryId)
    {
        Collection coll = new LinkedList();
        if (countryId > 0)
        {
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT i_stateid, s_name FROM t_state WHERE i_countryid = ? AND i_status = 1 ORDER BY s_name ");
            String query = sb.toString();
            sb.setLength(0);
            coll.add(new CandidateInfo(-1, " Select State "));
            try 
            {
                conn = getConnection();
                pstmt = conn.prepareStatement(query);
                pstmt.setInt(1, countryId);
                Common.print(this, "getCountryStates :: " + pstmt.toString());
                rs = pstmt.executeQuery();
                int id;
                String name ;
                while (rs.next()) 
                {
                    id = rs.getInt(1);
                    name = rs.getString(2) != null ? rs.getString(2) : "";
                    coll.add(new CandidateInfo(id, name));
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                close(conn, pstmt, rs);
            }
        } else {
            coll.add(new CandidateInfo(-1, " Select State "));
        }
        return coll;
    }
    
    public Collection getDocumentTypesForModule(int mid, int smid)
    {
        Collection coll = new LinkedList();
        coll.add(new CandidateInfo(-1, "- Select -"));
        String xml_path = getMainPath("json_path");
        String str = readHTMLFile("documenttype.json", xml_path);
        JSONArray arr = new JSONArray(str);
        if(arr != null)
        {
            int len = arr.length();
            for(int i = 0; i < len; i++)
            {
                JSONObject jobj = arr.optJSONObject(i);
                if(jobj != null)
                {
                    String mids = jobj.optString("docmodule");
                    String msids = jobj.optString("docsubmodule");
                    if(mids.contains(""+mid) && msids.contains(""+smid)) 
                        coll.add(new CandidateInfo(jobj.optInt("id"), jobj.optString("name")));
                }
            }
        }
        return coll;
    }
}
