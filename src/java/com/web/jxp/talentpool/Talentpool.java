package com.web.jxp.talentpool;

import com.mysql.jdbc.Statement;
import com.web.jxp.base.Base;
import static com.web.jxp.common.Common.*;
import com.web.jxp.country.CountryInfo;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

public class Talentpool extends Base {

    private static final Logger logger = Logger.getLogger(Class.class.getName());
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    public ArrayList getCandidateByName(String search, int statusIndex, int next, int count, int positionIndex,
            int clientIndex, int locationIndex, int assetIndex, int Verified, int employementIndex, int allclient,
            String permission, String cids, String assetids, int assettypeIdIndex, int positionFilter) {
        ArrayList list = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT c.i_candidateid, DATE_FORMAT(c.ts_regdate, '%d-%b-%Y'), CONCAT_WS(' ', c.s_firstname, c.s_middlename, c.s_lastname), t_country.s_name, c.i_status, ");
        sb.append("t_city.s_name, t_position.s_name, c.s_firstname, t_client.s_name, t_clientasset.s_name, t_grade.s_name, c.i_progressid, a1.ct, c.i_vflag, c.i_clientid, c.s_empno ");
        sb.append("FROM t_candidate AS c  ");
        sb.append("LEFT JOIN (SELECT i_candidateid, COUNT(1) AS ct FROM t_alert WHERE i_status = 1 GROUP BY i_candidateid) AS a1 ON (c.i_candidateid = a1.i_candidateid) ");
        sb.append("LEFT JOIN t_clientasset ON (c.i_clientassetid = t_clientasset.i_clientassetid) ");
        sb.append("LEFT JOIN t_country ON (t_country.i_countryid = t_clientasset.i_countryid) ");
        sb.append("LEFT JOIN t_city ON (c.i_cityid = t_city.i_cityid) ");
        sb.append("LEFT JOIN t_position ON ( c.i_positionid = t_position.i_positionid) ");
        sb.append("LEFT JOIN t_grade ON (t_grade.i_gradeid = t_position.i_gradeid) ");
        sb.append("LEFT JOIN t_client ON (c.i_clientid = t_client.i_clientid) ");
        sb.append("WHERE 0 =0 AND c.i_vflag IN (3,4) AND c.i_pass = 2 ");
        if (allclient == 1) {
            sb.append(" AND c.i_clientid >= 0 ");
        } else {
            if (!cids.equals("")) {
                sb.append("AND (c.i_clientid IN (" + cids + ") OR (c.i_clientid <= 0 AND c.i_progressid = 0) OR (c.i_progressid = 1 AND c.i_candidateid IN (SELECT i_candidateid FROM t_shortlist WHERE i_jobpostid IN (SELECT i_jobpostid FROM t_jobpost WHERE i_status = 1 AND i_clientid IN (" + cids + ") )))) ");
            } else {
                sb.append("AND t_client.i_clientid < 0 ");
            }
            if (!assetids.equals("")) {
                sb.append("AND (c.i_clientassetid IN (" + assetids + ") OR (c.i_clientassetid <= 0 AND c.i_progressid = 0) OR (c.i_progressid = 1 AND c.i_candidateid IN (SELECT i_candidateid FROM t_shortlist WHERE i_jobpostid IN (SELECT i_jobpostid FROM t_jobpost WHERE i_status = 1 AND i_clientassetid IN (" + assetids + ") )))) ");
            }
        }
        if (Verified > 0) {
            sb.append("AND c.i_vflag =4 ");
        }
        if (positionIndex > 0) {
            sb.append("AND c.i_positionid =? ");
        }
        if (clientIndex > 0) {
            sb.append("AND c.i_clientid = ? ");
        }
        if (locationIndex > 0) {
            sb.append("AND t_clientasset.i_countryid = ? ");
        }
        if (assetIndex > 0) {
            sb.append("AND c.i_clientassetid = ? ");
        }
        if (statusIndex > 0) {
            sb.append("AND c.i_status =? ");
        }
        if (search != null && !search.equals("")) {
            sb.append("AND (CONCAT_WS(' ', c.s_firstname, c.s_middlename, c.s_lastname) LIKE ? OR CONCAT_WS(' ', c.s_firstname, c.s_lastname) LIKE ? OR c.i_candidateid LIKE ? ");
            sb.append("OR t_country.s_name LIKE ? OR t_client.s_name LIKE ? OR t_position.s_name LIKE ? OR t_grade.s_name LIKE ? OR t_clientasset.s_name LIKE ? OR c.s_empno like ?) ");
        }
        if(employementIndex > 0)
        {
            if (employementIndex == 1) {
                sb.append("AND c.i_clientid = 0 AND c.i_status != 4 ");
            }
            if (employementIndex == 2) {
                sb.append("AND c.i_clientid >0 AND c.i_status != 4 ");
            }
            if (employementIndex == 3) {
                sb.append("AND c.i_status = 4 ");
            }
        }else if (employementIndex <= 0)
        {
            sb.append("AND c.i_status != 4 ");
        }
        if (assettypeIdIndex > 0) {
            sb.append("AND t_position.i_assettypeid = ? ");
        }
        if (positionFilter > 0) {
            sb.append("AND c.i_positionid = ? ");
        }
        sb.append("ORDER BY c.i_candidateid DESC ");
        if (count > 0) {
            sb.append(" LIMIT ").append(next * count).append(", ").append(count);
        }
        String query = (sb.toString()).intern();
        sb.setLength(0);

        sb.append("SELECT COUNT(1) FROM t_candidate AS c ");
        sb.append("LEFT JOIN t_clientasset ON ( c.i_clientassetid = t_clientasset.i_clientassetid ) ");
        sb.append("LEFT JOIN t_country ON ( t_country.i_countryid = t_clientasset.i_countryid ) ");
        sb.append("LEFT JOIN t_city ON ( c.i_cityid = t_city.i_cityid ) ");
        sb.append("LEFT JOIN t_position ON ( c.i_positionid = t_position.i_positionid ) ");
        sb.append("LEFT JOIN t_grade ON (t_grade.i_gradeid = t_position.i_gradeid) ");
        sb.append("LEFT JOIN t_client ON ( c.i_clientid = t_client.i_clientid ) ");
        sb.append("WHERE 0 =0 AND c.i_vflag IN (3,4) AND c.i_pass =2 ");
        if (allclient == 1) {
            sb.append(" AND c.i_clientid >= 0 ");
        } else {
            if (!cids.equals("")) {
                sb.append("AND (c.i_clientid IN (" + cids + ") OR (c.i_clientid <= 0 AND c.i_progressid = 0) OR (c.i_progressid = 1 AND c.i_candidateid IN (SELECT i_candidateid FROM t_shortlist WHERE i_jobpostid IN (SELECT i_jobpostid FROM t_jobpost WHERE i_status = 1 AND i_clientid IN (" + cids + ") )))) ");
            } else {
                sb.append("AND t_client.i_clientid < 0 ");
            }
            if (!assetids.equals("")) {
                sb.append("AND (c.i_clientassetid IN (" + assetids + ") OR (c.i_clientassetid <= 0 AND c.i_progressid = 0) OR (c.i_progressid = 1 AND c.i_candidateid IN (SELECT i_candidateid FROM t_shortlist WHERE i_jobpostid IN (SELECT i_jobpostid FROM t_jobpost WHERE i_status = 1 AND i_clientassetid IN (" + assetids + ") )))) ");
            }
        }
        if (Verified > 0) {
            sb.append("AND c.i_vflag =4 ");
        }
        if (positionIndex > 0) {
            sb.append("AND c.i_positionid =? ");
        }
        if (clientIndex > 0) {
            sb.append("AND c.i_clientid = ? ");
        }
        if (locationIndex > 0) {
            sb.append("AND t_clientasset.i_countryid = ? ");
        }
        if (assetIndex > 0) {
            sb.append("AND c.i_clientassetid = ? ");
        }
        if (statusIndex > 0) {
            sb.append("AND c.i_status =? ");
        }
        if (search != null && !search.equals("")) {
            sb.append("AND (CONCAT_WS(' ', c.s_firstname, c.s_middlename, c.s_lastname) LIKE ? OR CONCAT_WS(' ', c.s_firstname, c.s_lastname) LIKE ? OR c.i_candidateid LIKE ? ");
            sb.append("OR t_country.s_name LIKE ? OR t_client.s_name LIKE ? OR t_position.s_name LIKE ? OR t_grade.s_name LIKE ? OR t_clientasset.s_name LIKE ? OR c.s_empno like ?) ");
        }
        if(employementIndex > 0)
        {
            if (employementIndex == 1) {
                sb.append("AND c.i_clientid = 0 AND c.i_status != 4 ");
            }
            if (employementIndex == 2) {
                sb.append("AND c.i_clientid >0 AND c.i_status != 4 ");
            }
            if (employementIndex == 3) {
                sb.append("AND c.i_status = 4 ");
            }
        }else if (employementIndex <= 0)
        {
            sb.append("AND c.i_status != 4 ");
        }
        if (assettypeIdIndex > 0) {
            sb.append("AND t_position.i_assettypeid = ? ");
        }
        if (positionFilter > 0) {
            sb.append("AND c.i_positionid = ? ");
        }
        String countquery = (sb.toString()).intern();
        sb.setLength(0);
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            int scc = 0;
            if (positionIndex > 0) {
                pstmt.setInt(++scc, positionIndex);
            }
            if (clientIndex > 0) {
                pstmt.setInt(++scc, clientIndex);
            }
            if (locationIndex > 0) {
                pstmt.setInt(++scc, locationIndex);
            }
            if (assetIndex > 0) {
                pstmt.setInt(++scc, assetIndex);
            }
            if (statusIndex > 0) {
                pstmt.setInt(++scc, statusIndex);
            }
            if (search != null && !search.equals("")) {
                pstmt.setString(++scc, "%" + (search) + "%");
                pstmt.setString(++scc, "%" + (search) + "%");
                pstmt.setString(++scc, search.replaceFirst("^0+(?!$)", ""));
                pstmt.setString(++scc, "%" + (search) + "%");
                pstmt.setString(++scc, "%" + (search) + "%");
                pstmt.setString(++scc, "%" + (search) + "%");
                pstmt.setString(++scc, "%" + (search) + "%");
                pstmt.setString(++scc, "%" + (search) + "%");
                pstmt.setString(++scc, "%" + (search) + "%");
            }
            if (assettypeIdIndex > 0) {
                pstmt.setInt(++scc, assettypeIdIndex);
            }
            if (positionFilter > 0) {
                pstmt.setInt(++scc, positionFilter);
            }
            logger.info("getCandidateByName :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String name, countryName, date, cityName, positionName = "", firstName, gradeName, clientName, clientAsset, position, empno;
            int candidateId, status, progressId, alertCount, vflag, clientId;
            while (rs.next()) {
                candidateId = rs.getInt(1);
                date = rs.getString(2) != null ? rs.getString(2) : "";
                name = rs.getString(3) != null ? rs.getString(3) : "";
                countryName = rs.getString(4) != null ? rs.getString(4) : "";
                status = rs.getInt(5);
                cityName = rs.getString(6) != null ? rs.getString(6) : "";
                position = rs.getString(7) != null ? rs.getString(7) : "";
                firstName = rs.getString(8) != null ? rs.getString(8) : "";
                clientName = rs.getString(9) != null ? rs.getString(9) : "";
                clientAsset = rs.getString(10) != null ? rs.getString(10) : "";
                gradeName = rs.getString(11) != null ? rs.getString(11) : "";
                progressId = rs.getInt(12);
                alertCount = rs.getInt(13);
                vflag = rs.getInt(14);
                clientId = rs.getInt(15);
                empno = rs.getString(16) != null ? rs.getString(16) : "";
                if (!gradeName.equals("")) {
                    positionName = position + " | " + gradeName;
                }
                list.add(new TalentpoolInfo(candidateId, name, countryName, status, date, cityName, positionName, firstName,
                        clientName, clientAsset, progressId, alertCount, vflag, clientId, gradeName, position, empno));
            }
            rs.close();

            pstmt = conn.prepareStatement(countquery);
            scc = 0;
            if (positionIndex > 0) {
                pstmt.setInt(++scc, positionIndex);
            }
            if (clientIndex > 0) {
                pstmt.setInt(++scc, clientIndex);
            }
            if (locationIndex > 0) {
                pstmt.setInt(++scc, locationIndex);
            }
            if (assetIndex > 0) {
                pstmt.setInt(++scc, assetIndex);
            }
            if (statusIndex > 0) {
                pstmt.setInt(++scc, statusIndex);
            }
            if (search != null && !search.equals("")) {
                pstmt.setString(++scc, "%" + (search) + "%");
                pstmt.setString(++scc, "%" + (search) + "%");
                pstmt.setString(++scc, search.replaceFirst("^0+(?!$)", ""));
                pstmt.setString(++scc, "%" + (search) + "%");
                pstmt.setString(++scc, "%" + (search) + "%");
                pstmt.setString(++scc, "%" + (search) + "%");
                pstmt.setString(++scc, "%" + (search) + "%");
                pstmt.setString(++scc, "%" + (search) + "%");
                pstmt.setString(++scc, "%" + (search) + "%");
            }
            if (assettypeIdIndex > 0) {
                pstmt.setInt(++scc, assettypeIdIndex);
            }
            if (positionFilter > 0) {
                pstmt.setInt(++scc, positionFilter);
            }
            print(this, "getCandidateByName :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                candidateId = rs.getInt(1);
                list.add(new TalentpoolInfo(candidateId, "", "", 0, "", "", "", "", "", "", 0, 0, 0, 0, "", "", ""));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return list;
    }

    public ArrayList getFinalRecord(ArrayList l, String colId, int tp) {
        ArrayList list = null;
        HashMap record = null;
        TalentpoolInfo info;
        int total = 0;
        if (l != null) {
            total = l.size();
        }
        try {
            if (total > 0) {
                record = new HashMap();
                int i;
                for (i = 0; i < total; i++) {
                    info = (TalentpoolInfo) l.get(i);
                    record.put(getInfoValue(info, colId), info);
                }
            }
            Map map = sortByName(record, tp);

            Iterator it = map.keySet().iterator();
            list = new ArrayList();
            TalentpoolInfo rInfo;
            while (it.hasNext()) {
                String key = (String) it.next();
                int i;
                for (i = 0; i < total; i++) {
                    rInfo = (TalentpoolInfo) l.get(i);
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
    public String getInfoValue(TalentpoolInfo info, String i) {
        String infoval = "";
        if (i != null && i.equals("1")) {
            infoval = info.getName() != null ? info.getName() : "";
        } else if (i != null && i.equals("2")) {
            infoval = info.getPosition() != null ? info.getPosition() : "";
        } else if (i != null && i.equals("3")) {
            infoval = info.getClientName() != null ? info.getClientName() : "";
        } else if (i != null && i.equals("4")) {
            infoval = info.getCountryName() != null ? info.getCountryName() : "";
        } else if (i != null && i.equals("5")) {
            infoval = info.getClientAsset() != null ? info.getClientAsset() : "";
        } else if (i != null && i.equals("6")) {
            infoval = info.getEmployeeId() != null ? info.getEmployeeId() : "";
        }
        return infoval;
    }

    public Collection getPositions() {
        Collection coll = new LinkedList();
        String query = ("SELECT i_positionid, s_name FROM t_position WHERE i_status = 1 ORDER BY  s_name").intern();
        coll.add(new TalentpoolInfo(-1, "Select Position"));
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            rs = pstmt.executeQuery();
            int refId;
            String refName;
            while (rs.next()) {
                refId = rs.getInt(1);
                refName = rs.getString(2) != null ? rs.getString(2) : "";
                coll.add(new TalentpoolInfo(refId, refName));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return coll;
    }

    public TalentpoolInfo getCandidateDetail(int candidateId) {
        TalentpoolInfo info = null;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT CONCAT_WS(' ', c.s_firstname, c.s_middlename, c.s_lastname), DATE_FORMAT(c.d_dob, '%d-%b-%Y'), c.s_placeofbirth, c.s_email, c.s_code1, ");
        sb.append("c.s_contactno1, c.s_code2, c.s_contactno2, c.s_code3, c.s_contactno3, c.s_gender, t_country.s_name, n.s_nationality, c.s_address1line1, ");
        sb.append("c.s_address1line2, c.s_address2line1, c.s_address2line2, c.s_address2line3, c.s_nextofkin, t_relation.s_name, c.s_ecode1, c.s_econtactno1, ");
        sb.append("c.s_ecode2, c.s_econtactno2, t_maritialstatus.s_name, c.s_photofilename, t_position.s_name , c.s_address1line3, t_city.s_name,t_experiencedept.s_name, ");
        sb.append("c.i_expectedsalary,t_currency.s_name,t1.ct, c.s_firstname ,t_grade.s_name, c.i_vflag, t_assettype.s_name , t_client.i_clientid,t_clientasset.s_name, ");
        sb.append("c.i_clientassetid, t_client.s_name, c.s_empno, c.i_progressid, c.i_applytype, c.i_positionid, t_clientasset.s_ratetype, t_dayrateh.d_rate1, t_dayrateh.d_rate2, ");
        sb.append(" c.i_status, c.s_religion, p2.s_name, g2.s_name, d1.d_rate1, d1.d_rate2, c.i_positionid2, c.i_traveldays, t_state.s_name, c.s_pincode, c.i_age, ");
        sb.append("c.s_airport1, c.s_airport2, t_assettype.i_assettypeid, DATE_FORMAT(current_date(), '%d-%b-%Y'), c.s_profile, c.s_skill1, c.s_skill2 ");
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
        sb.append("LEFT JOIN (SELECT i_candidateid, COUNT(1) AS ct FROM t_candidatefiles WHERE i_candidateid = ? GROUP BY i_candidateid) AS t1 ON (c.i_candidateid = t1.i_candidateid) ");
        sb.append("LEFT JOIN t_grade ON (t_grade.i_gradeid = t_position.i_gradeid) ");
        sb.append("LEFT JOIN t_state ON ( t_state.i_stateid= c.i_stateid ) ");
        sb.append("LEFT JOIN t_dayrateh ON (t_dayrateh.i_candidateid = c.i_candidateid AND t_dayrateh.i_positionid = c.i_positionid) ");
        sb.append("LEFT JOIN t_dayrateh AS d1 ON (d1.i_candidateid = c.i_candidateid AND d1.i_positionid = c.i_positionid2) ");
        sb.append("WHERE c.i_candidateid = ? ");
        String query = sb.toString().intern();
        sb.setLength(0);
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, candidateId);
            pstmt.setInt(2, candidateId);
            print(this, "getCandidateDetail :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String name, dob, place, email, contact1_code, contact1, contact2_code, contact2, contact3_code,
            contact3, gender, countryName, nationality, address1line1, address1line2, address2line1, address2line2,
            address2line3, nextofkin, relation, econtact1_code, econtact1, econtact2_code, econtact2, maritialstatus,
            photo, positionName, address1line3, cityName, experiencedept, currency, firstname, gradeName, assetName,
            clientName, employeeId, applytypevalue, ratetype, religion, position2, grade2, state, pincode, airport1, airport2, date2, onboardingdate;
            int expectedsalary, filecount, vflag, clientId, assetId, progressId, positionId, status, positionId2, travelDays, age, assetTypeId;
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
                info = new TalentpoolInfo(candidateId, name, dob, place, email, contact1_code, contact1, contact2_code, contact2, contact3_code, contact3, gender, countryName,
                        nationality, address1line1, address1line2, address2line1, address2line2, address2line3, nextofkin, relation, econtact1_code, econtact1, econtact2_code,
                        econtact2, maritialstatus, photo, positionName, address1line3, cityName, experiencedept, expectedsalary, currency, filecount, firstname, vflag,
                        assettype, clientId, assetName, assetId, clientName, employeeId, progressId, applytypevalue, positionId, ratetype, rate1, rate2, status, religion,
                        position2, p2rate1, p2rate2, positionId2, travelDays, state, pincode, age, airport1, airport2, assetTypeId, date2, profile, skill1, skill2);
            }
        } catch (Exception exception) {
            print(this, "getCandidateDetail :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return info;
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

    public TalentpoolInfo getCandidateExpDetail(int candidateId) {
        TalentpoolInfo info = null;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT t_position.i_positionid , t_experiencedept.i_experiencedeptid, t_clientasset.s_name, ");
        sb.append("DATE_FORMAT(t_transfer.s_joiningdate, '%d-%b-%Y'),DATE_FORMAT(t_transfer.s_enddate, '%d-%b-%Y'), ");
        sb.append("t_client.s_name, p2.i_positionid,t_transfer.s_filename ");
        sb.append("FROM t_candidate AS c ");
        sb.append("LEFT JOIN t_position ON (t_position.i_positionid = c.i_positionid) ");
        sb.append("LEFT JOIN t_position AS p2 ON (p2.i_positionid = c.i_positionid2) ");
        sb.append("LEFT JOIN t_experiencedept ON (t_experiencedept.i_experiencedeptid = c.i_departmentid) ");
        sb.append("LEFT JOIN t_client ON (t_client.i_clientid = c.i_clientid) ");
        sb.append("LEFT JOIN t_clientasset ON (t_clientasset.i_clientassetid = c.i_clientassetid) ");
        sb.append("LEFT JOIN t_transfer ON ( t_transfer.i_candidateid = c.i_candidateid) ");
        sb.append("LEFT JOIN t_workexperience ON (c.i_candidateid  = t_workexperience.i_candidateid) ");
        sb.append("WHERE c.i_candidateid = ? ");
        String query = sb.toString().intern();
        sb.setLength(0);
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, candidateId);
            print(this, "getCandidateExpDetail :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String assetname, startdate, enddate, companyname, filename;
            int positionid, departmentid, positionid2;
            while (rs.next()) {
                positionid = rs.getInt(1);
                departmentid = rs.getInt(2);
                assetname = rs.getString(3) != null ? rs.getString(3) : "";
                startdate = rs.getString(4) != null ? rs.getString(4) : "";
                enddate = rs.getString(5) != null ? rs.getString(5) : "";
                companyname = rs.getString(6) != null ? rs.getString(6) : "";
                positionid2 = rs.getInt(7);
                filename = rs.getString(8) != null ? rs.getString(8) : "";                
                info = new TalentpoolInfo(candidateId, positionid, departmentid, assetname, startdate, 
                        enddate, companyname, positionid2, filename);
            }
        } catch (Exception exception) {
            print(this, "getCandidateExpDetail :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return info;
    }

    public int createTerminateExp(TalentpoolInfo info, int candidateId, int uId, TalentpoolInfo info3, int toPositionId2, int assettypeId) {
        int experiencedetailId = 0;
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("INSERT INTO t_workexperience (s_companyname, s_assetname, d_workstartdate, d_workenddate, ");
            sb.append("i_userid, i_candidateid, ts_regdate, ts_moddate, i_positionid,s_filenameexp, i_status, i_assettypeid) ");
            sb.append("VALUES ( ?,?,?,?,?,  ?,?,?,?,  ?,?,? )");
            String query = sb.toString().intern();
            sb.setLength(0);
            conn = getConnection();
            pstmt = conn.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, info3.getCompanyname());
            pstmt.setString(2, info3.getAssetName());
            pstmt.setString(3, changeDate1(info3.getJoiningDate()));
            pstmt.setString(4, changeDate1(info3.getEndDate()));
            pstmt.setInt(5, uId);
            pstmt.setInt(6, candidateId);
            pstmt.setString(7, currDate1());
            pstmt.setString(8, currDate1());
            if(toPositionId2 > 0)
                pstmt.setInt(9, toPositionId2);
            else
                pstmt.setInt(9, info3.getPositionId());
            pstmt.setString(10, info3.getFilename());
            pstmt.setInt(11, 1);
            pstmt.setInt(12, assettypeId);
            pstmt.executeUpdate();
            logger.info("createTerminateExp :: " + pstmt.toString());
            rs = pstmt.getGeneratedKeys();
            while (rs.next()) {
                experiencedetailId = rs.getInt(1);
            }
        } catch (Exception exception) {
            print(this, "createTerminateExp :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return experiencedetailId;
    }

    public TalentpoolInfo getBankDetailByCandidateId(int bankdetid) {
        TalentpoolInfo info = null;
        String query = "SELECT s_bankname,s_savingaccno,s_branch,s_ifsccode,i_bankaccounttypeid,s_filename, i_primarybankid, s_accountholder FROM t_bankdetail WHERE i_bankdetid = ? ";
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, bankdetid);
            print(this, "getBankDetailByCandidateId :" + pstmt.toString());
            rs = pstmt.executeQuery();
            String bankname, savingAccountNo, branch, ifsccode, fileName, accountholder;
            int accountTypeId, primarybankid;
            while (rs.next()) {
                bankname = rs.getString(1) != null ? rs.getString(1) : "";
                savingAccountNo = decipher(rs.getString(2) != null ? rs.getString(2) : "");
                branch = rs.getString(3) != null ? rs.getString(3) : "";
                ifsccode = decipher(rs.getString(4) != null ? rs.getString(4) : "");
                accountTypeId = rs.getInt(5);
                fileName = rs.getString(6) != null ? rs.getString(6) : "";
                primarybankid = rs.getInt(7);
                accountholder = rs.getString(8) != null ? rs.getString(8) : "";
                info = new TalentpoolInfo(bankdetid, bankname, savingAccountNo, branch, accountTypeId, 
                        ifsccode, fileName, primarybankid, accountholder);
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

    public ArrayList getCandbankList(int candidateId) {
        ArrayList list = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT t_bankdetail.i_bankdetid, t_bankdetail.s_bankname , t_bankaccounttype.s_name, ");
        sb.append("t_bankdetail.s_branch, t_bankdetail.s_ifsccode, t_bankdetail.s_filename , ");
        sb.append(" t_bankdetail.i_status , t_bankdetail.i_primarybankid  ");
        sb.append("FROM t_bankdetail ");
        sb.append("LEFT JOIN t_bankaccounttype ON (t_bankdetail.i_bankaccounttypeid = t_bankaccounttype.i_bankaccounttypeid) ");
        sb.append("WHERE t_bankdetail.i_candidateid = ? ");
        sb.append(" ORDER BY  t_bankdetail.i_status, t_bankdetail.s_bankname ");
        String query = (sb.toString()).intern();
        sb.setLength(0);
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, candidateId);
            logger.info("getCandbankList :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String bankName, bankAccounttype, branch, ifsccode, filename;
            int candidatebankId, status, primaryBankId;
            while (rs.next()) {
                candidatebankId = rs.getInt(1);
                bankName = rs.getString(2) != null ? rs.getString(2) : "";
                bankAccounttype = rs.getString(3) != null ? rs.getString(3) : "";
                branch = rs.getString(4) != null ? rs.getString(4) : "";
                ifsccode = decipher(rs.getString(5) != null ? rs.getString(5) : "");
                filename = rs.getString(6) != null ? rs.getString(6) : "";
                status = rs.getInt(7);
                primaryBankId = rs.getInt(8);
                list.add(new TalentpoolInfo(candidatebankId, bankName, bankAccounttype, branch, ifsccode, filename, status, primaryBankId));
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return list;
    }

    public int insertBankdetails(TalentpoolInfo info, int candidateId, int userId) {
        int cc = 0;
        try {
            if (info.getPrimarybankId() == 1) {
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
            sb.append(" i_primarybankid, s_accountholder, i_status , i_userid,i_candidateid, ts_regdate, ts_moddate) ");
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
            pstmt.setString(++scc, info.getAccountHolder());
            pstmt.setInt(++scc, info.getStatus());
            pstmt.setInt(++scc, userId);
            pstmt.setInt(++scc, candidateId);
            pstmt.setString(++scc, currDate1());
            pstmt.setString(++scc, currDate1());
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

    public int checkDuplicacyBank(int candidateId, int bankdetailId, String bankname, String savingaccno) {
        int ck = 0;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT i_bankdetid FROM t_bankdetail WHERE i_candidateid = ?  AND s_bankname = ? AND s_savingaccno = ? AND  i_status  IN (1, 2)");
        if (bankdetailId > 0) {
            sb.append(" AND i_bankdetid != ? ");
        }
        String query = (sb.toString()).intern();
        sb.setLength(0);
        try {
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

    public int updateBankdetails(TalentpoolInfo info, int userId, int candidateId, int bankdetailId, String username) {
        StringBuilder sb = new StringBuilder();
        int cc = 0;
        try {
            conn = getConnection();
            if (info.getPrimarybankId() == 1) {
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
            print(this, "updateBankdetailid :: " + pstmt.toString());
            cc = pstmt.executeUpdate();
            pstmt.close();
            String remarks = "Verify Bank Details tab";
            insertAlertBy(candidateId, 9, 1, remarks, userId, conn, username);
            updateverification(9, userId, candidateId, bankdetailId);
        } catch (Exception exception) {
            print(this, "updateBankdetailid :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return cc;
    }

    public int deletebank(int candidateId, int bankdetailId, int userId, int status, String ipAddrStr, String iplocal, String username) {
        int cc = 0;
        try {
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
            print(this, "deletebank :: " + pstmt.toString());
            cc = pstmt.executeUpdate();

            String remarks = "Verify Bank Details tab";
            insertAlertBy(candidateId, 9, 1, remarks, userId, conn, username);
            updateverification(9, userId, candidateId, bankdetailId);
        } catch (Exception exception) {
            print(this, "deletebank :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        createHistoryAccess(null, userId, ipAddrStr, iplocal, "Data Deleted", 7, bankdetailId);
        return cc;
    }

    public int createCandidate(TalentpoolInfo info, int userId) {
        int candidateId = 0;
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("INSERT INTO t_candidate ");
            sb.append("(s_firstname,s_middlename, s_lastname,d_dob,s_placeofbirth,s_email,s_code1,s_code2,s_contactno1,s_contactno2,s_code3,s_contactno3,");
            sb.append(" s_gender,i_countryid,i_nationalityid,s_address1line1,s_address1line2,s_address2line1,s_address2line2,s_address2line3,s_nextofkin,i_relationid ,s_ecode1,s_econtactno1,");
            sb.append("s_ecode2, s_econtactno2,i_maritialstatusid ,s_photofilename,s_address1line3,i_departmentid,i_currencyid,i_positionid,i_expectedsalary,i_cityid, i_status, ");
            sb.append("i_userid, ts_regdate, ts_moddate, i_assettypeid, s_empno, i_applytype,  s_religion, i_positionid2, i_traveldays,  s_profile, s_skill1, skill2) ");
            sb.append("VALUES (?, ?,?, ?,?,  ?,?,?,?,?   ,?,?,?,?,?   ,?,?,?,?,?  ,?,?,?,?,?,   ?,?,?,?,?,  ?,?,?,?,?,   ?,?,?,?,?,?,  ?, ?, ?,   ?, ?, ?)");
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
            pstmt.setInt(++scc, info.getApplytype());
            pstmt.setString(++scc, info.getReligion());
            pstmt.setInt(++scc, info.getPositionId2());
            pstmt.setInt(++scc, info.getTravelDays());
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

    public int checkDuplicacy(int candidateId, String emailId) {
        int ck = 0;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT i_candidateid FROM t_candidate WHERE s_email = ?  AND i_status IN (1, 2)");
        if (candidateId > 0) {
            sb.append(" AND i_candidateid != ? ");
        }
        String query = (sb.toString()).intern();
        sb.setLength(0);
        try {
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

    public int updateCandidate(TalentpoolInfo info, int candidateId, int userId, int positionIdhidden, String userName) {
        int cc = 0;
        try {
            conn = getConnection();
            if (positionIdhidden != info.getPositionId()) {
                String query1 = "UPDATE t_cassessment SET i_active = 0 WHERE i_candidateid = ? ";
                pstmt = conn.prepareStatement(query1);
                pstmt.setInt(1, candidateId);
                pstmt.executeUpdate();
            }
            StringBuilder sb = new StringBuilder();
            sb.append("UPDATE t_candidate SET ");
            sb.append("s_firstname = ?, ");
            sb.append("s_middlename = ?, ");
            sb.append("s_lastname = ?, ");
            sb.append("d_dob = ?, ");
            sb.append("s_placeofbirth = ?, ");
            sb.append("s_email = ?, ");
            sb.append("s_code1 = ?, ");
            sb.append("s_code2 = ?, ");
            sb.append("s_contactno1 = ?, ");
            sb.append("s_contactno2 = ?, ");
            sb.append("s_code3 = ?, ");
            sb.append("s_contactno3 = ?, ");
            sb.append("s_gender = ?, ");
            sb.append("i_countryid = ?, ");
            sb.append("i_stateid = ?, ");
            sb.append("i_nationalityid = ?, ");
            sb.append("s_address1line1 = ?, ");
            sb.append("s_address1line2 = ?, ");
            sb.append("s_address2line1 = ?, ");
            sb.append("s_address2line2 = ?, ");
            sb.append("s_address2line3 = ?, ");
            sb.append("s_nextofkin = ?, ");
            sb.append("i_relationid = ?, ");
            sb.append("s_ecode1 = ?, ");
            sb.append("s_econtactno1 = ?, ");
            sb.append("s_ecode2 = ?, ");
            sb.append("s_econtactno2 = ?, ");
            sb.append("i_maritialstatusid = ?, ");
            if (info.getPhotofilename() != null && !info.getPhotofilename().equals("")) {
                sb.append("s_photofilename = ?, ");
            }
            sb.append("s_address1line3 = ?, ");
            sb.append("i_departmentid = ?, ");
            sb.append("i_positionid = ?, ");
            sb.append("i_currencyid = ?, ");
            sb.append("i_expectedsalary = ?, ");
            sb.append("i_cityid = ?, ");
            sb.append("s_pincode = ?, ");
            sb.append("i_status = ?, ");
            sb.append("i_userid = ?, ");
            sb.append("ts_moddate = ?, ");
            sb.append("i_assettypeid = ?, ");
            sb.append("s_empno = ?, ");
            sb.append("i_applytype = ?, ");
            sb.append("s_religion = ?, ");
            sb.append("i_positionid2 = ?, ");
            sb.append("i_travelDays = ?, ");
            sb.append("i_age = ?, ");
            sb.append("s_airport1 = ?, ");
            sb.append("s_airport2 = ?, ");
            sb.append("s_profile = ?, ");
            sb.append("s_skill1 = ?, ");
            sb.append("s_skill2 = ? ");
            sb.append("WHERE i_candidateid = ? ");
            String query = (sb.toString()).intern();
            sb.setLength(0);
            conn = getConnection();
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
            pstmt.setInt(++scc, info.getStateId());
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
            if (info.getPhotofilename() != null && !info.getPhotofilename().equals("")) {
                pstmt.setString(++scc, info.getPhotofilename());
            }
            pstmt.setString(++scc, info.getAddress1line3());
            pstmt.setInt(++scc, info.getDepartmentId());
            pstmt.setInt(++scc, info.getPositionId());
            pstmt.setInt(++scc, info.getCurrencyId());
            pstmt.setInt(++scc, info.getExpectedsalary());
            pstmt.setInt(++scc, info.getCityId());
            pstmt.setString(++scc, info.getPinCode());
            pstmt.setInt(++scc, info.getStatus());
            pstmt.setInt(++scc, userId);
            pstmt.setString(++scc, currDate1());
            pstmt.setInt(++scc, info.getAssettypeId());
            pstmt.setString(++scc, info.getEmployeeId());
            pstmt.setInt(++scc, info.getApplytype());
            pstmt.setString(++scc, info.getReligion());
            pstmt.setInt(++scc, info.getPositionId2());
            pstmt.setInt(++scc, info.getTravelDays());
            pstmt.setInt(++scc, info.getAge());
            pstmt.setString(++scc, info.getAirport1());
            pstmt.setString(++scc, info.getAirport2());
            pstmt.setString(++scc, info.getProfile());
            pstmt.setString(++scc, info.getSkill1());
            pstmt.setString(++scc, info.getSkill2());
            pstmt.setInt(++scc, candidateId);
            print(this, "updateCandidate :: " + pstmt.toString());
            cc = pstmt.executeUpdate();

            String remarks = "Verify Personal tab";
            insertAlertBy(candidateId, 1, 1, remarks, userId, conn, userName);
            updateverification(1, userId, candidateId, 0);

        } catch (Exception exception) {
            print(this, "updateCandidate :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return cc;
    }

    //For modify
    public TalentpoolInfo getCandidateDetailById(int candidateId) {
        TalentpoolInfo info = null;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT  s_firstname, s_middlename, s_lastname, DATE_FORMAT(d_dob, '%d-%b-%Y'), s_placeofbirth, s_email,");
        sb.append("s_code1, s_code2, s_contactno1, s_contactno2, s_code3, s_contactno3, s_gender,");
        sb.append("t_candidate.i_countryid, i_nationalityid, s_address1line1, s_address1line2, s_address2line1, s_address2line2, ");
        sb.append("s_address2line3, s_nextofkin, i_relationid, s_ecode1, s_econtactno1, s_ecode2, ");
        sb.append("s_econtactno2, i_maritialstatusid,s_photofilename ,s_address1line3,i_departmentid,i_positionid, ");
        sb.append("t_candidate.i_currencyid,i_expectedsalary, t_candidate.i_status ,t_city.s_name ,t_candidate.i_cityid,t1.ct, t_candidate.i_assettypeid, ");
        sb.append("t_candidate.s_empno, t_candidate.i_applytype, t_clientasset.s_ratetype,  t_candidate.s_religion, ");
        sb.append("t_candidate.i_positionid2, t_candidate.i_travelDays, t_candidate.i_stateid, t_candidate.s_pincode, ");
        sb.append("t_candidate.i_age, t_candidate.s_airport1, t_candidate.s_airport2, t_candidate.s_profile, t_candidate.s_skill1, t_candidate.s_skill2 ");
        sb.append("FROM t_candidate ");
        sb.append("LEFT JOIN t_city  ON (t_city.i_cityid = t_candidate.i_cityid) ");
        sb.append("LEFT JOIN t_clientasset ON(t_clientasset.i_clientassetid = t_candidate.i_clientassetid) ");
        sb.append("LEFT JOIN (SELECT i_candidateid, COUNT(1) AS ct FROM t_candidatefiles WHERE t_candidatefiles.i_candidateid = ? GROUP BY i_candidateid) AS t1 ON (t_candidate.i_candidateid = t1.i_candidateid) ");
        sb.append("  WHERE t_candidate.i_candidateid = ? ");
        String query = sb.toString().intern();
        sb.setLength(0);
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, candidateId);
            pstmt.setInt(2, candidateId);
            print(this, "getCandidateDetailById :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
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
                String employeeId = rs.getString(39) != null ? rs.getString(39) : "";
                int applytype = rs.getInt(40);
                String ratetype = rs.getString(41) != null ? rs.getString(41) : "";
                String religion = rs.getString(42) != null ? rs.getString(42) : "";
                int positionId2 = rs.getInt(43);
                int travelDays = rs.getInt(44);
                int stateId = rs.getInt(45);
                String pincode = rs.getString(46) != null ? rs.getString(46) : "";
                int age = rs.getInt(47);
                String airport1 = rs.getString(48) != null ? rs.getString(48) : "";
                String airport2 = rs.getString(49) != null ? rs.getString(49) : "";
                String profile = rs.getString(50) != null ? rs.getString(50) : "";
                String skill1 = rs.getString(51) != null ? rs.getString(51) : "";
                String skill2 = rs.getString(52) != null ? rs.getString(52) : "";
                
                info = new TalentpoolInfo(firstname, middlename, lastname, dob, placeofbirth, 
                email, code1, code2, contactno1, contactno2, code3, contactno3, gender, 
                countryid, nationalityid, address1line1, address1line2, address2line1, 
                address2line2, address2line3, nextofkin, relationid, ecode1, econtactno1, 
                ecode2, econtactno2, maritialstatusid, photofilename, address1line3, departmentId,
                positionId, currencyId, expectedsalary, status, cityName, cityId, filecount, 
                assettypeId, employeeId, applytype, ratetype, religion, positionId2, 
                travelDays, stateId, pincode, age, airport1, airport2, profile, skill1, skill2);
            }
        } catch (Exception exception) {
            print(this, "getCandidateDetailById :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return info;
    }

    public int checkDuplicacyLang(int candidateId, int languageId, int candidateLangId) {
        int ck = 0;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT i_candlangid FROM t_candlang WHERE i_candidateid = ?  AND i_languageid = ? AND  i_status  IN (1, 2)");
        if (candidateLangId > 0) {
            sb.append(" AND i_candlangid != ? ");
        }
        String query = (sb.toString()).intern();
        sb.setLength(0);
        try {
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

    public ArrayList getCandlanguageList(int candidateId) {
        ArrayList list = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT t_candlang.i_candlangid, t_language.s_name , t_proficiency.s_name , t_candlang.s_filename ,t_candlang.i_status ");
        sb.append("FROM t_candlang LEFT JOIN t_language ON (t_candlang.i_languageid = t_language.i_languageid) ");
        sb.append("LEFT JOIN t_proficiency ON (t_candlang.i_proficiencyid = t_proficiency.i_proficiencyid) ");
        sb.append("WHERE t_candlang.i_candidateid = ? ");
        sb.append(" ORDER BY  t_candlang.i_status, t_language.s_name ");
        String query = (sb.toString()).intern();
        sb.setLength(0);
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, candidateId);
            logger.info("getCandlanguageList :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String langname, proficiencyname, filename;
            int candidatelangId, status;
            while (rs.next()) {
                candidatelangId = rs.getInt(1);
                langname = rs.getString(2) != null ? rs.getString(2) : "";
                proficiencyname = rs.getString(3) != null ? rs.getString(3) : "";
                filename = rs.getString(4) != null ? rs.getString(4) : "";
                status = rs.getInt(5);
                list.add(new TalentpoolInfo(candidatelangId, langname, proficiencyname, filename, status));
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return list;
    }

    public TalentpoolInfo getcandlangForModify(int candidateLangId) {
        TalentpoolInfo info = null;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT i_languageid, i_proficiencyid, s_filename ");
        sb.append("FROM t_candlang WHERE i_candlangid = ? ");
        String query = (sb.toString()).intern();
        sb.setLength(0);
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, candidateLangId);
            logger.info("getcandlangForModify :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String filename;
            int languageId, proficiencyId;
            while (rs.next()) {
                languageId = rs.getInt(1);
                proficiencyId = rs.getInt(2);
                filename = rs.getString(3) != null ? rs.getString(3) : "";
                info = new TalentpoolInfo(languageId, proficiencyId, filename);
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return info;
    }

    //For offshore update
    public TalentpoolInfo getOffshoreUpdateForModify(int offshoreId) {
        TalentpoolInfo info = null;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT i_clientid, i_clientassetid, i_remarktypeid, s_remarks, s_filename, DATE_FORMAT(d_date, '%d-%b-%Y') ");
        sb.append("FROM t_offshoreupdate WHERE i_offshoreupdateid = ? ");
        String query = (sb.toString()).intern();
        sb.setLength(0);
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, offshoreId);
            logger.info("getOffshoreUpdateForModify :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String filename, remarks, date;
            int clientId, clientassetId, remarktypeId;
            while (rs.next()) {
                clientId = rs.getInt(1);
                clientassetId = rs.getInt(2);
                remarktypeId = rs.getInt(3);
                remarks = rs.getString(4) != null ? rs.getString(4) : "";
                filename = rs.getString(5) != null ? rs.getString(5) : "";
                date = rs.getString(6) != null ? rs.getString(6) : "";
                info = new TalentpoolInfo(clientId, clientassetId, remarktypeId, remarks, filename, date);
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return info;
    }

    public int insertOffshoreDetail(TalentpoolInfo info, int candidateId, int userId) {
        int candlangId = 0;
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("INSERT INTO t_offshoreupdate  ");
            sb.append("( i_clientid, i_clientassetid, i_remarktypeid, s_remarks, s_filename, i_candidateid, i_status, d_date, i_userid, ts_regdate, ts_moddate) ");
            sb.append("VALUES (?, ?, ?, ?, ?,  ?, ?, ?, ?, ?, ?)");
            String query = (sb.toString()).intern();
            sb.setLength(0);
            conn = getConnection();
            pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            int scc = 0;
            pstmt.setInt(++scc, info.getClientId());
            pstmt.setInt(++scc, info.getClientassetId());
            pstmt.setInt(++scc, info.getRemarktypeId());
            pstmt.setString(++scc, info.getRemarks());
            pstmt.setString(++scc, info.getOffshorefile());
            pstmt.setInt(++scc, candidateId);
            pstmt.setInt(++scc, info.getStatus());
            pstmt.setString(++scc, changeDate1(info.getDate()));
            pstmt.setInt(++scc, info.getUserId());
            pstmt.setString(++scc, currDate1());
            pstmt.setString(++scc, currDate1());
            print(this, "insertOffshoreDetail :: " + pstmt.toString());
            pstmt.executeUpdate();
            rs = pstmt.getGeneratedKeys();
            while (rs.next()) {
                candlangId = rs.getInt(1);
            }
        } catch (Exception exception) {
            print(this, "insertOffshoreDetail :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return candlangId;
    }

    //for offshore listing
    public ArrayList getOffshoreList(int candidateId) {
        ArrayList list = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT t_offshoreupdate.i_offshoreupdateid, t_client.s_name, t_clientasset.s_name, ");
        sb.append("t_remarktype.s_name, t_offshoreupdate.s_remarks, t_offshoreupdate.i_status, t_offshoreupdate.s_filename,  ");
        sb.append(" DATE_FORMAT(t_offshoreupdate.d_date, '%d-%b-%Y') FROM t_offshoreupdate ");
        sb.append("LEFT JOIN t_client ON (t_offshoreupdate.i_clientid = t_client.i_clientid) ");
        sb.append("LEFT JOIN t_clientasset ON (t_offshoreupdate.i_clientassetid =t_clientasset.i_clientassetid) ");
        sb.append("LEFT JOIN t_remarktype ON (t_remarktype.i_remarktypeid = t_offshoreupdate.i_remarktypeid) ");
        sb.append("WHERE t_offshoreupdate.i_candidateid= ? ORDER BY t_offshoreupdate.i_status, t_offshoreupdate.d_date DESC");
        String query = (sb.toString()).intern();
        sb.setLength(0);
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, candidateId);
            logger.info("getOffshoreList :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String clinetname, assetname, filename, remarktype, remark, date;
            int offshoreId, status;
            while (rs.next()) {
                offshoreId = rs.getInt(1);
                clinetname = rs.getString(2) != null ? rs.getString(2) : "";
                assetname = rs.getString(3) != null ? rs.getString(3) : "";
                remarktype = rs.getString(4) != null ? rs.getString(4) : "";
                remark = rs.getString(5) != null ? rs.getString(5) : "";
                status = rs.getInt(6);
                filename = rs.getString(7) != null ? rs.getString(7) : "";
                date = rs.getString(8) != null ? rs.getString(8) : "";
                list.add(new TalentpoolInfo(offshoreId, clinetname, assetname, remarktype, remark, status, filename, date, 0, ""));
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return list;
    }

    public int updateOffshore(TalentpoolInfo info, int candidateId, int uId) {
        int cc = 0;
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("UPDATE t_offshoreupdate SET  ");
            sb.append(" i_remarktypeid = ?, ");
            sb.append(" i_clientid = ?, ");
            sb.append(" i_clientassetid = ?, ");
            sb.append(" s_remarks = ?, ");
            sb.append(" i_status = ?, ");
            sb.append(" d_date = ?, ");
            if (info.getOffshorefile() != null && !info.getOffshorefile().equals("")) {
                sb.append("s_filename = ?, ");
            }
            sb.append(" ts_moddate = ?  ");
            sb.append("WHERE i_offshoreupdateid = ? AND i_candidateid = ? ");
            String query = (sb.toString()).intern();
            sb.setLength(0);
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            int scc = 0;
            pstmt.setInt(++scc, info.getRemarktypeId());
            pstmt.setInt(++scc, info.getClientId());
            pstmt.setInt(++scc, info.getClientassetId());
            pstmt.setString(++scc, info.getRemarks());
            pstmt.setInt(++scc, info.getStatus());
            pstmt.setString(++scc, changeDate1(info.getDate()));
            if (info.getOffshorefile() != null && !info.getOffshorefile().equals("")) {
                pstmt.setString(++scc, info.getOffshorefile());
            }
            pstmt.setString(++scc, currDate1());
            pstmt.setInt(++scc, info.getOffshoreId());
            pstmt.setInt(++scc, candidateId);
            print(this, "updateOffshore :: " + pstmt.toString());
            cc = pstmt.executeUpdate();
        } catch (Exception exception) {
            print(this, "updateOffshore :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return cc;
    }

    public int deleteOffshoreDetail(int candidateId, int offshoreId, int userId, int status, String ipAddrStr, String iplocal) {
        int cc = 0;
        try {
            conn = getConnection();
            StringBuilder sb = new StringBuilder();
            sb.append("UPDATE t_offshoreupdate SET ");
            sb.append("i_status = ?, ");
            sb.append("i_userid = ?, ");
            sb.append("ts_moddate = ? ");
            sb.append("WHERE i_offshoreupdateid = ? AND i_candidateid = ? ");
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
            pstmt.setInt(++scc, offshoreId);
            pstmt.setInt(++scc, candidateId);
            print(this, "deleteOffshoreDetail :: " + pstmt.toString());
            cc = pstmt.executeUpdate();
        } catch (Exception exception) {
            print(this, "deleteOffshoreDetail :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return cc;
    }

    public int insertLanguage(TalentpoolInfo info, int candidateId, int userId, String username) {
        int candlangId = 0;
        try {
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
            String remarks = "Verify Language tab";
            insertAlertBy(candidateId, 2, 1, remarks, userId, conn, username);
            updateverification(2, userId, candidateId, candlangId);
        } catch (Exception exception) {
            print(this, "createCandLang :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return candlangId;
    }

    public int updateLanguage(TalentpoolInfo info, int candidateId, int uId, String username) {
        int cc = 0;
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("UPDATE t_candlang SET  ");
            sb.append(" i_languageid = ?, ");
            sb.append(" i_proficiencyid = ?, ");
            sb.append(" i_status = ?, ");
            if (info.getCandlangfilename() != null && !info.getCandlangfilename().equals("")) {
                sb.append("s_filename = ?, ");
            }
            sb.append(" ts_moddate = ?  ");
            sb.append("WHERE i_candlangid = ? AND i_candidateid = ? ");
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

            String remarks = "Verify Language tab";
            insertAlertBy(candidateId, 2, 1, remarks, uId, conn, username);
            updateverification(2, uId, candidateId, info.getCandlangid());

        } catch (Exception exception) {
            print(this, "updateCandLang :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return cc;
    }

    public int deleteLanguage(int candidateId, int candlangId, int userId, int status, String ipAddrStr, String iplocal, String username) {
        int cc = 0;
        try {
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
            print(this, "deleteLanguage :: " + pstmt.toString());
            cc = pstmt.executeUpdate();

            String remarks = "Verify Language tab";
            insertAlertBy(candidateId, 2, 1, remarks, userId, conn, username);
            updateverification(2, userId, candidateId, candlangId);
        } catch (Exception exception) {
            print(this, "deleteLanguage :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        createHistoryAccess(null, userId, ipAddrStr, iplocal, "Data Deleted", 7, candlangId);
        return cc;
    }

    public int insertHealthdetails(TalentpoolInfo info, int candidateId, int userId, String username) {
        int cc = 0;
        try {
            if (candidateId > 0) {
                int candidatehealthId = 0;
                String query = "SELECT i_healthid FROM t_healthdeclaration WHERE i_candidateid = ? ";
                conn = getConnection();
                pstmt = conn.prepareStatement(query);
                pstmt.setInt(1, candidateId);
                print(this, "candidatehealth :" + pstmt.toString());
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    candidatehealthId = rs.getInt(1);
                }
                rs.close();
                pstmt.close();
                if (candidatehealthId > 0) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("UPDATE t_healthdeclaration SET ");
                    sb.append("s_ssmf = ?, ");
                    sb.append("s_ogukmedicalftw = ?, ");
                    sb.append("d_ogukexp = ?, ");
                    sb.append("s_medifitcert = ?, ");
                    sb.append("d_medifitcertexp = ?, ");
                    sb.append("s_bloodgroup = ?, ");
                    sb.append("i_bloodpressureid = ?, ");
                    sb.append("s_hypertension = ?, ");
                    sb.append("s_diabetes = ?, ");
                    sb.append("s_smoking = ?, ");
                    if (info.getMfFilename() != null && !info.getMfFilename().equals("")) {
                        sb.append("s_filename = ?,");
                    }
                    sb.append("s_covid192doses = ?, ");
                    sb.append("i_status = ?, ");
                    sb.append("i_userid = ?, ");
                    sb.append("ts_moddate = ? ");
                    sb.append("WHERE i_healthid = ? AND i_candidateid = ? ");
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
                    if (info.getMfFilename() != null && !info.getMfFilename().equals("")) {
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
                    String remarks = "Verify Health tab";
                    insertAlertBy(candidateId, 3, 1, remarks, userId, conn, username);
                    if (cc > 0 && info.getMfFilename() != null && !info.getMfFilename().equals("")) {
                        String insertfile = "INSERT INTO t_healthfile (i_candidateid, s_name, i_status, i_userid, ts_regdate, ts_moddate) VALUES (?,?,?,?,?,?) ";
                        pstmt = conn.prepareStatement(insertfile);
                        pstmt.setInt(1, candidateId);
                        pstmt.setString(2, info.getMfFilename());
                        pstmt.setInt(3, 1);
                        pstmt.setInt(4, userId);
                        pstmt.setString(5, currDate1());
                        pstmt.setString(6, currDate1());
                        pstmt.executeUpdate();
                        print(this, "insertfile :" + pstmt.toString());
                    }
                    updateverification(3, userId, candidateId, candidatehealthId);
                } else {
                    StringBuilder sb = new StringBuilder();
                    sb.append("INSERT INTO t_healthdeclaration ");
                    sb.append("( s_ssmf, s_ogukmedicalftw, d_ogukexp, s_medifitcert, d_medifitcertexp, ");
                    sb.append("s_bloodgroup, i_bloodpressureid, s_hypertension, s_diabetes, s_smoking, s_filename,s_covid192doses, ");
                    sb.append(" i_status,i_vstatus, i_userid,i_candidateid, ts_regdate, ts_moddate ) ");
                    sb.append("VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?,?, ?, ?, ?, ?,?,?,?,?)");
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
                    if (candidatehealthId > 0 && info.getMfFilename() != null && !info.getMfFilename().equals("")) {
                        String insert = "INSERT INTO t_healthfile (i_candidateid, s_name, i_status, i_userid, ts_regdate, ts_moddate) VALUES (?,?,?,?,?,?) ";
                        pstmt = conn.prepareStatement(insert);
                        pstmt.setInt(1, candidateId);
                        pstmt.setString(2, info.getMfFilename());
                        pstmt.setInt(3, 1);
                        pstmt.setInt(4, userId);
                        pstmt.setString(5, currDate1());
                        pstmt.setString(6, currDate1());
                        pstmt.executeUpdate();
                        print(this, "insert :" + pstmt.toString());
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

    public TalentpoolInfo getCandidateHealthDetailBycandidateId(int candidateId) {
        TalentpoolInfo info = null;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT s_ssmf, s_ogukmedicalftw,DATE_FORMAT(d_ogukexp, '%d-%b-%Y') , s_medifitcert,DATE_FORMAT(d_medifitcertexp, '%d-%b-%Y') , ");
        sb.append("s_bloodgroup, t_healthdeclaration.i_bloodpressureid, s_hypertension, s_diabetes, s_smoking,t_healthdeclaration.i_userid,s_covid192doses, ");
        sb.append("s_filename, t_healthdeclaration.i_status ,i_healthid , t_bloodpressure.s_name ");
        sb.append("FROM t_healthdeclaration ");
        sb.append(" LEFT JOIN t_bloodpressure ON (t_bloodpressure.i_bloodpressureid = t_healthdeclaration.i_bloodpressureid) ");
        sb.append("WHERE  i_candidateid = ? ");
        String query = sb.toString().intern();
        sb.setLength(0);
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, candidateId);
            print(this, "getCandidateHealthDetailBycandidateId :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
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
                info = new TalentpoolInfo(candidatehealthId, ssmf, ogukmedicalftw, ogukexp, medifitcert, medifitcertexp,
                        bloodgroup, bloodpressureId, hypertension, diabetes, smoking, userId, mfFilename, status, 
                        covid192doses, bloodpressure, "", candidateId);
            }
        } catch (Exception exception) {
            print(this, "getCandidateHealthDetailBycandidateId :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return info;
    }

    public ArrayList getCandVaccList(int candidateId) {
        ArrayList list = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT t_vbd.i_vbdid, t_vaccine.s_name , t_vaccinetype.s_name ,t_city.s_name, t_vbd.s_filename ,");
        sb.append(" t_vbd.i_status ,DATE_FORMAT(t_vbd.d_dateofapplication, '%d-%b-%Y'),DATE_FORMAT(t_vbd.d_dateofexpiry, '%d-%b-%Y') , t_country.s_name  ");
        sb.append("FROM t_vbd LEFT JOIN t_vaccine ON (t_vbd.i_vaccinenameid = t_vaccine.i_vaccineid) ");
        sb.append("LEFT JOIN t_vaccinetype ON (t_vbd.i_vaccinetypeid = t_vaccinetype.i_vaccinetypeid) ");
        sb.append("LEFT JOIN t_city ON (t_vbd.i_placeofapplicationid = t_city.i_cityid) ");
        sb.append("LEFT JOIN t_country ON (t_country.i_countryid = t_city.i_countryid) ");
        sb.append("WHERE t_vbd.i_candidateid = ? ");
        sb.append(" ORDER BY t_vbd.i_status, t_vbd.d_dateofexpiry DESC ");
        String query = (sb.toString()).intern();
        sb.setLength(0);
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, candidateId);
            logger.info("getCandVaccList :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String vaccinename, vaccinetypename, placeofapplication, filename, dateofapplication, dateofexpiry, countryname;
            int candidatevaccId, status;
            while (rs.next()) {
                candidatevaccId = rs.getInt(1);
                vaccinename = rs.getString(2) != null ? rs.getString(2) : "";
                vaccinetypename = rs.getString(3) != null ? rs.getString(3) : "";
                placeofapplication = rs.getString(4) != null ? rs.getString(4) : "";
                filename = rs.getString(5) != null ? rs.getString(5) : "";
                status = rs.getInt(6);
                dateofapplication = rs.getString(7);
                dateofexpiry = rs.getString(8);
                countryname = rs.getString(9) != null ? rs.getString(9) : "";
                if (!placeofapplication.equals("")) {
                    if (!countryname.equals("")) {
                        placeofapplication += " (" + countryname + ")";
                    }
                }
                list.add(new TalentpoolInfo(candidatevaccId, vaccinename, vaccinetypename, placeofapplication, 
                        filename, status, dateofapplication, dateofexpiry, "", candidateId));
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return list;
    }

    public ArrayList getVerificationList(int candidateId) {
        ArrayList list = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT vs.i_vstatusid, DATE_FORMAT(vs.ts_regdate, '%d-%b-%Y'), DATE_FORMAT(vs.ts_regdate, '%H:%i'), t_userlogin.s_name, t_tab.s_name, ");
        sb.append("vs.s_authority, vs.i_status, vs.s_filename FROM t_vstatus AS vs ");
        sb.append("LEFT JOIN t_userlogin ON (t_userlogin.i_userid = vs.i_userid) ");
        sb.append("LEFT JOIN t_tab ON (t_tab.i_tab = vs.i_tabno) ");
        sb.append("WHERE vs.i_candidateid = ? ");
        sb.append("ORDER BY vs.ts_regdate DESC ");
        String query = (sb.toString()).intern();
        sb.setLength(0);
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, candidateId);
            logger.info("getVerificationList :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String filename, date, time, authority, username, tabname;
            int vstatusId, status;
            while (rs.next()) {
                vstatusId = rs.getInt(1);
                date = rs.getString(2) != null ? rs.getString(2) : "";
                time = rs.getString(3) != null ? rs.getString(3) : "";
                username = rs.getString(4) != null ? rs.getString(4) : "";
                tabname = rs.getString(5) != null ? rs.getString(5) : "";
                authority = rs.getString(6) != null ? rs.getString(6) : "";
                status = rs.getInt(7);
                filename = rs.getString(8) != null ? rs.getString(8) : "";
                list.add(new TalentpoolInfo(vstatusId, date, time, username, tabname, authority, status, filename));
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return list;
    }

    public TalentpoolInfo getVerificationStatusbyCandidateId(int candidateId) {
        TalentpoolInfo info = null;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT i_cflag1,i_cflag2,i_cflag3,i_cflag4,i_cflag5,i_cflag6,i_cflag7,i_cflag8,i_cflag9,i_cflag10 FROM t_candidate ");
        sb.append("WHERE i_candidateid = ? ");
        String query = (sb.toString()).intern();
        sb.setLength(0);
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, candidateId);
            logger.info("getVerificationStatusbyCandidateId :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            int cflag1, cflag2, cflag3, cflag4, cflag5, cflag6, cflag7, cflag8, cflag9, cflag10;
            while (rs.next()) {
                cflag1 = rs.getInt(1);
                cflag2 = rs.getInt(2);
                cflag3 = rs.getInt(3);
                cflag4 = rs.getInt(4);
                cflag5 = rs.getInt(5);
                cflag6 = rs.getInt(6);
                cflag7 = rs.getInt(7);
                cflag8 = rs.getInt(8);
                cflag9 = rs.getInt(9);
                cflag10 = rs.getInt(10);
                info = new TalentpoolInfo(candidateId, cflag1, cflag2, cflag3, cflag4, cflag5, cflag6, cflag7, cflag8, cflag9, cflag10);
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return info;
    }

    public ArrayList getCassessmenthistoryByCandidateId(int candidateId) {
        ArrayList list = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT t1.i_cassessmenthid, t1.i_cassessmentid, c1.s_name, a1.s_name, t_assessment.s_name, t1.i_status, t1.i_marks, ");
        sb.append("DATE_FORMAT(t1.ts_regdate, '%d-%b-%Y'), DATE_FORMAT(t1.ts_regdate, '%H:%i'), t1.i_attempt, t1.i_showflag, t1.i_paid ");
        sb.append("FROM t_cassessmenth AS t1 ");
        sb.append("LEFT JOIN t_positionassessment ON (t1.i_paid = t_positionassessment.i_positionassessmentid) ");
        sb.append("LEFT JOIN t_assessment ON (t_assessment.i_assessmentid = t_positionassessment.i_assessmentid) ");
        sb.append("LEFT JOIN t_userlogin  AS a1 ON (t1.i_assessorid = a1.i_userid) ");
        sb.append("LEFT JOIN t_userlogin  AS c1 ON (t1.i_coordinatorid = c1.i_userid) ");
        sb.append("WHERE t1.i_candidateid = ? ");
        sb.append("ORDER BY t1.ts_regdate DESC ");
        String query = (sb.toString()).intern();
        sb.setLength(0);
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, candidateId);
            logger.info("getCassessmenthistoryByCandidateId :: " + pstmt.toString());

            rs = pstmt.executeQuery();
            String statusValue, assessorName, date, time, coordinatorName, assessment;
            int cassessmentId, cassessmenthId, status, attempt, showflag, paId;
            double marks;
            while (rs.next()) {
                cassessmenthId = rs.getInt(1);
                cassessmentId = rs.getInt(2);
                coordinatorName = rs.getString(3) != null ? rs.getString(3) : "";
                assessorName = rs.getString(4) != null ? rs.getString(4) : "";
                assessment = rs.getString(5) != null ? rs.getString(5) : "";
                status = rs.getInt(6);
                marks = rs.getDouble(7);
                date = rs.getString(8) != null ? rs.getString(8) : "";
                time = rs.getString(9) != null ? rs.getString(9) : "";
                attempt = rs.getInt(10);
                showflag = rs.getInt(11);
                paId = rs.getInt(12);
                statusValue = getAssstatusbyId(status);
                list.add(new TalentpoolInfo(cassessmenthId, cassessmentId, candidateId, coordinatorName, assessorName, assessment, statusValue, marks, date,
                        time, status, attempt, showflag, paId));
            }
            rs.close();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return list;
    }

    public ArrayList getCompliancehistoryByCandidateId(int candidateId) {
        ArrayList list = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT t_shortlist.i_jobpostid, DATE_FORMAT(t1.ts_regdate, '%d-%b-%Y %H:%i'), t_checkpoint.s_name, ");
        sb.append("a1.s_name,t1.i_status,t_shortlist.i_shortlistid, t_shortlist.i_status ");
        sb.append("FROM t_shortlistcch AS t1 ");
        sb.append("LEFT JOIN t_shortlist ON (t1.i_shortlistid = t_shortlist.i_shortlistid) ");
        sb.append("LEFT JOIN t_checkpoint ON (t1.i_checkpointid = t_checkpoint.i_checkpointid) ");
        sb.append("LEFT JOIN t_userlogin  AS a1 ON (t1.i_userid = a1.i_userid) ");
        sb.append("WHERE t_shortlist.i_candidateid = ? ");
        sb.append("ORDER BY t1.ts_regdate DESC ");
        String query = (sb.toString()).intern();
        sb.setLength(0);
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, candidateId);
            logger.info("getCompliancehistoryByCandidateId :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String checkpointName, date, userName, statusValue;
            int jobpostId, status, shortlistId, shortlistStatus;
            while (rs.next()) {
                jobpostId = rs.getInt(1);
                date = rs.getString(2) != null ? rs.getString(2) : "";
                checkpointName = rs.getString(3) != null ? rs.getString(3) : "";
                userName = rs.getString(4) != null ? rs.getString(4) : "";
                status = rs.getInt(5);
                shortlistId = rs.getInt(6);
                shortlistStatus = rs.getInt(7);
                statusValue = getCompliancetatusbyId(status);
                list.add(new TalentpoolInfo(jobpostId, date, checkpointName, userName, statusValue, shortlistId, shortlistStatus, candidateId));
            }
            rs.close();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return list;
    }

    public ArrayList getShortlistinghistoryByCandidateId(int candidateId) {

        ArrayList list = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT s.i_jobpostid, DATE_FORMAT(j.ts_regdate, '%d-%b-%Y'), t_client.s_name, t_clientasset.s_name, t_position.s_name, t_grade.s_name, ");
        sb.append("DATE_FORMAT(s.ts_regdate, '%d-%b-%Y %H:%i'), t_userlogin.s_name, j.i_status FROM t_shortlisth AS s ");
        sb.append("LEFT JOIN t_jobpost AS j ON (s.i_jobpostid = j.i_jobpostid) ");
        sb.append("LEFT JOIN t_client ON (j.i_clientid = t_client.i_clientid) ");
        sb.append("LEFT JOIN t_clientasset ON (j.i_clientassetid =t_clientasset.i_clientassetid) ");
        sb.append("LEFT JOIN t_position ON (j.i_positionid = t_position.i_positionid) ");
        sb.append("LEFT JOIN t_grade ON (j.i_gradeid = t_grade.i_gradeid) ");
        sb.append("LEFT JOIN t_userlogin ON (s.i_userid = t_userlogin.i_userid) ");
        sb.append("WHERE FIND_IN_SET(?, s.s_candidatesid) ");
        sb.append("ORDER BY s.ts_regdate DESC ");
        String query = (sb.toString()).intern();
        sb.setLength(0);
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, candidateId);
            logger.info("getShortlistinghistoryByCandidateId :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String poston, client, clientasset, position, grade, shortliston, shortlistBy, statusvalue, code;
            int jobpostId;
            while (rs.next()) {
                jobpostId = rs.getInt(1);
                poston = rs.getString(2) != null ? rs.getString(2) : "";
                client = rs.getString(3) != null ? rs.getString(3) : "";
                clientasset = rs.getString(4) != null ? rs.getString(4) : "";
                position = rs.getString(5) != null ? rs.getString(5) : "";
                grade = rs.getString(6) != null ? rs.getString(6) : "";
                shortliston = rs.getString(7) != null ? rs.getString(7) : "";
                shortlistBy = rs.getString(8) != null ? rs.getString(8) : "";
                statusvalue = rs.getInt(9) == 1 ? "Open" : "Closed";
                code = changeNum(jobpostId, 6);
                list.add(new TalentpoolInfo(jobpostId, poston, client, clientasset, position, grade, shortliston, shortlistBy, statusvalue, code));
            }
            rs.close();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return list;
    }

    public ArrayList getClientselectionhistoryByCandidateId(int candidateId) {
        ArrayList list = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT t_shortlist.i_jobpostid, DATE_FORMAT(t1.ts_regdate, '%d-%b-%Y %H:%i'), t_client.s_name, t_clientasset.s_name, t_position.s_name,");
        sb.append(" t_grade.s_name, t1.i_sflag,t1.i_maillogid,t1.i_usertype,t1.i_shortlistid ,t1.i_oflag ");
        sb.append("FROM t_clientselectionh AS t1 ");
        sb.append("LEFT JOIN t_shortlist ON (t1.i_shortlistid = t_shortlist.i_shortlistid) ");
        sb.append("LEFT JOIN t_jobpost ON (t_shortlist.i_jobpostid = t_jobpost.i_jobpostid) ");
        sb.append("LEFT JOIN t_client ON (t_jobpost.i_clientid = t_client.i_clientid) ");
        sb.append("LEFT JOIN t_clientasset ON (t_jobpost.i_clientassetid = t_clientasset.i_clientassetid) ");
        sb.append("LEFT JOIN t_position ON (t_jobpost.i_positionid = t_position.i_positionid) ");
        sb.append("LEFT JOIN t_grade ON (t_jobpost.i_gradeid = t_grade.i_gradeid) ");
        sb.append("LEFT JOIN t_userlogin  AS a1 ON (t1.i_userid = a1.i_userid) ");
        sb.append("WHERE t_shortlist.i_candidateid = ? ");
        sb.append("ORDER BY t1.ts_regdate DESC ");
        String query = (sb.toString()).intern();
        sb.setLength(0);
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, candidateId);
            logger.info("getClientselectionhistoryByCandidateId :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String clientName, clientassetName, positionName, gradeName, date, statusValue;
            int jobpostId, sflag, maillogId, usertype, shortlistId, oflag;
            while (rs.next()) {
                jobpostId = rs.getInt(1);
                date = rs.getString(2) != null ? rs.getString(2) : "";
                clientName = rs.getString(3) != null ? rs.getString(3) : "";
                clientassetName = rs.getString(4) != null ? rs.getString(4) : "";
                positionName = rs.getString(5) != null ? rs.getString(5) : "";
                gradeName = rs.getString(6) != null ? rs.getString(6) : "";
                sflag = rs.getInt(7);
                maillogId = rs.getInt(8);
                usertype = rs.getInt(9);
                shortlistId = rs.getInt(10);
                oflag = rs.getInt(11);
                statusValue = getClientselectiontatusbyId(sflag, oflag);
                if (!positionName.equals("")) {
                    if (!gradeName.equals("")) {
                        positionName += " | " + gradeName;
                    }
                }
                if (!clientName.equals("")) {
                    if (!clientassetName.equals("")) {
                        clientName += " | " + clientassetName;
                    }
                }
                list.add(new TalentpoolInfo(jobpostId, candidateId, date, clientName, positionName, statusValue, maillogId, usertype, shortlistId, sflag, oflag));
            }
            rs.close();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return list;
    }

    public String readHTMLFile1(String filePathfileName) {
        String content = "";
        StringBuilder sb = new StringBuilder();
        File file = new File(filePathfileName);
        String line;
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            br.close();
            content = sb.toString();
            sb.setLength(0);
        } catch (Exception e) {
            print(this, "Error:: " + e.toString());
        }
        return content;
    }

    public TalentpoolInfo getselectionEmailDetail(int shortlistId, int maillogId, int sflagp, int oflagp) {
        TalentpoolInfo info = null;
        StringBuilder sb = new StringBuilder();
        String pdffilename = "";
        try {
            if (sflagp <= 2 || oflagp == 2) {
                sb.append("SELECT t_shortlist.s_pdffilename, t_shortlist.s_offerpdffilename FROM t_shortlist ");
                sb.append("WHERE t_shortlist.i_shortlistid =? AND t_shortlist.i_status > 1 ");
                String query = sb.toString().intern();
                sb.setLength(0);
                conn = getConnection();
                pstmt = conn.prepareStatement(query);
                pstmt.setInt(1, shortlistId);
                print(this, "getshortlistpdf :: " + pstmt.toString());
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    String cvpdffilename = rs.getString(1) != null ? rs.getString(1) : "";
                    String offerpdffilename = rs.getString(2) != null ? rs.getString(2) : "";
                    if (oflagp == 2) {
                        pdffilename = offerpdffilename;
                    } else if (sflagp <= 2) {
                        pdffilename = cvpdffilename;
                    }
                    info = new TalentpoolInfo("", "", "", "", "", "", pdffilename, "", "", "", "", "", shortlistId, 0, "", "", 0, "", "", 0, "", "", "", "", "", "");
                }
            } else if (sflagp > 2 || oflagp >= 3) {
                sb.append("SELECT DATE_FORMAT(m.ts_regdate, '%d-%b-%Y %H:%i'), m.s_to, m.s_from, m.s_cc, m.s_bcc, m.s_subject, m.s_filename, ");
                sb.append("m.s_attachmentpath, m.s_sendby , s.s_mailby,  DATE_FORMAT(s.ts_maildate, '%d-%b-%Y %H:%i'), s.s_srby, DATE_FORMAT( s.ts_srdate, '%d-%b-%Y %H:%i'), ");
                sb.append("s.i_usertype,s.i_sflag, s.s_remarks, s.s_reason,s.i_oflag, s.s_omailby, DATE_FORMAT( s.ts_omaildate, '%d-%b-%Y %H:%i'), s.s_oadby, ");
                sb.append("DATE_FORMAT( s.ts_oaddate, '%d-%b-%Y %H:%i'), s.s_oadreason, s.s_oadremarks  ");
                sb.append("FROM t_maillog m ");
                sb.append(" LEFT JOIN t_shortlist AS s ON (s.i_shortlistid = m.i_shortlistid) ");
                sb.append("WHERE m.i_shortlistid = ? ");
                if (maillogId > 0) {
                    sb.append("AND m.i_maillogid = ?");
                } else {
                    sb.append("ORDER BY m.ts_regdate DESC LIMIT 0,1");
                }
                String query = (sb.toString()).intern();
                sb.setLength(0);
                conn = getConnection();
                pstmt = conn.prepareStatement(query);
                pstmt.setInt(1, shortlistId);
                if (maillogId > 0) {
                    pstmt.setInt(2, maillogId);
                }
                logger.info("getselectionEmailDetail :: " + pstmt.toString());
                rs = pstmt.executeQuery();
                String date, mailto, mailfrom, mailcc, mailbcc, subject, filename, attachmentpath, sentby, mailby,
                        maildate, srby, srdate, usertypevalue, remarks, reason, omailby, omaildate, oadby, oaddate, oadreason, oadremarks;
                int sflag = 0, oflag = 0;
                while (rs.next()) {
                    date = rs.getString(1) != null ? rs.getString(1) : "";
                    mailto = rs.getString(2) != null ? rs.getString(2) : "";
                    mailfrom = rs.getString(3) != null ? rs.getString(3) : "";
                    mailcc = rs.getString(4) != null ? rs.getString(4) : "";
                    mailbcc = rs.getString(5) != null ? rs.getString(5) : "";
                    subject = rs.getString(6) != null ? rs.getString(6) : "";
                    filename = rs.getString(7) != null ? rs.getString(7) : "";
                    attachmentpath = rs.getString(8) != null ? rs.getString(8) : "";
                    sentby = rs.getString(9) != null ? rs.getString(9) : "";
                    mailby = rs.getString(10) != null ? rs.getString(10) : "";
                    maildate = rs.getString(11) != null ? rs.getString(11) : "";
                    srby = rs.getString(12) != null ? rs.getString(12) : "";
                    srdate = rs.getString(13) != null ? rs.getString(13) : "";
                    usertypevalue = getusertype(rs.getInt(14));
                    sflag = rs.getInt(15);
                    remarks = rs.getString(16) != null ? rs.getString(16) : "";
                    reason = rs.getString(17) != null ? rs.getString(17) : "";
                    oflag = rs.getInt(18);
                    omailby = rs.getString(19) != null ? rs.getString(19) : "";
                    omaildate = rs.getString(20) != null ? rs.getString(20) : "";
                    oadby = rs.getString(21) != null ? rs.getString(21) : "";
                    oaddate = rs.getString(22) != null ? rs.getString(22) : "";
                    oadreason = rs.getString(23) != null ? rs.getString(23) : "";
                    oadremarks = rs.getString(24) != null ? rs.getString(24) : "";
                    info = new TalentpoolInfo(mailto, mailfrom, mailcc, mailbcc, subject, filename, attachmentpath, sentby, mailby, maildate,
                            srby, srdate, shortlistId, maillogId, date, usertypevalue, sflag, remarks, reason, oflag, omailby, omaildate, oadby, oaddate, oadreason, oadremarks);
                }
                rs.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return info;
    }

    public String getusertype(int key) {
        String s = "";
        if (key == 1) {
            s = "company";
        } else if (key == 2) {
            s = "client";
        }
        return s;
    }

    public TalentpoolInfo getcandVaccForModify(int candvaccId) {
        TalentpoolInfo info = null;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT i_vaccinenameid, i_vaccinetypeid,i_placeofapplicationid,DATE_FORMAT(t_vbd.d_dateofapplication, '%d-%b-%Y'),  ");
        sb.append("DATE_FORMAT(t_vbd.d_dateofexpiry, '%d-%b-%Y'),t_vbd.i_status, t_vbd.s_filename, t_city.s_name, t_country.s_name ");
        sb.append("FROM t_vbd ");
        sb.append("LEFT JOIN t_city ON (t_city.i_cityid = t_vbd.i_placeofapplicationid) ");
        sb.append("LEFT JOIN t_country ON (t_country.i_countryid = t_city.i_countryid) WHERE i_vbdid = ? ");
        String query = (sb.toString()).intern();
        sb.setLength(0);
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, candvaccId);
            logger.info("getcandVaccForModify :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String dateofapplication, dateofexpiry, filename;
            int vaccinationNameId, vacinationTypeId, placeofapplication, status;
            while (rs.next()) {
                vaccinationNameId = rs.getInt(1);
                vacinationTypeId = rs.getInt(2);
                placeofapplication = rs.getInt(3);
                dateofapplication = rs.getString(4) != null ? rs.getString(4) : "";
                dateofexpiry = rs.getString(5) != null ? rs.getString(5) : "";
                status = rs.getInt(6);
                filename = rs.getString(7) != null ? rs.getString(7) : "";
                String cityName = rs.getString(8) != null ? rs.getString(8) : "";
                String countryName = rs.getString(9) != null ? rs.getString(9) : "";
                if (!countryName.equals("")) {
                    cityName += " (" + countryName + ")";
                }
                info = new TalentpoolInfo(vaccinationNameId, vacinationTypeId, placeofapplication, dateofapplication, dateofexpiry, status, filename, cityName);
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return info;
    }

    public int insertVaccinationdetail(TalentpoolInfo info, int candidateId, int userId, String username) {
        int candvaccId = 0;
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("INSERT INTO t_vbd  ");
            sb.append("( i_candidateid, i_vaccinenameid,i_vaccinetypeid,i_placeofapplicationid,d_dateofapplication, ");
            sb.append("d_dateofexpiry, i_status,s_filename,i_userid, ts_regdate, ts_moddate) ");
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
            print(this, "insertVaccinationdetail : " + pstmt.toString());
            rs = pstmt.getGeneratedKeys();
            while (rs.next()) {
                candvaccId = rs.getInt(1);
            }
            String remarks = "Verify Vaccination tab";
            insertAlertBy(candidateId, 4, 1, remarks, userId, conn, username);
            updateverification(4, userId, candidateId, candvaccId);
        } catch (Exception exception) {
            print(this, "createCandvacc :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return candvaccId;
    }

    public int updateVaccinationRecord(TalentpoolInfo info, int candidateId, int userId, String username) {
        int cc = 0;
        try {
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

            String remarks = "Verify Vaccination tab";
            insertAlertBy(candidateId, 4, 1, remarks, userId, conn, username);
            updateverification(4, userId, candidateId, info.getCandidatevaccineId());
        } catch (Exception exception) {
            print(this, "updateCandVaccination :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return cc;
    }

    public int checkDuplicacyVaccination(int candidateId, int candidatevaccineId, int vaccinationNameId, int vaccinationTypeId, String dateofapplication) {
        int ck = 0;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT i_vbdid FROM t_vbd WHERE i_candidateid = ?  AND i_vaccinenameid = ? AND i_vaccinetypeid = ? AND d_dateofapplication = ? AND i_status IN (1, 2)");
        if (candidatevaccineId > 0) {
            sb.append(" AND i_vbdid != ? ");
        }
        String query = (sb.toString()).intern();
        sb.setLength(0);
        try {
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

    public int deletevaccination(int candidateId, int candidatevaccineId, int userId, int status, 
            String ipAddrStr, String iplocal, String username) {
        int cc = 0;
        try {
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
            print(this, "deletevaccination :: " + pstmt.toString());
            cc = pstmt.executeUpdate();

            String remarks = "Verify Vaccination tab";
            insertAlertBy(candidateId, 4, 1, remarks, userId, conn, username);
            updateverification(4, userId, candidateId, candidatevaccineId);
        } catch (Exception exception) {
            print(this, "deletevaccination :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        createHistoryAccess(null, userId, ipAddrStr, iplocal, "Data Deleted", 7, candidatevaccineId);
        return cc;
    }

    public ArrayList getCandgovdocList(int candidateId) {
        ArrayList list = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT t_govdoc.i_govid, t_doctype.s_doc , t_govdoc.s_docno ,t_city.s_name,t_documentissuedby.s_name, ");
        sb.append(" t_govdoc.i_status ,DATE_FORMAT(t_govdoc.d_dateofissue, '%d-%b-%Y'),DATE_FORMAT(t_govdoc.d_expirydate, '%d-%b-%Y'), ");
        sb.append("t_country.s_name, t1.ct  ");
        sb.append("FROM t_govdoc ");
        sb.append("LEFT JOIN t_doctype ON (t_govdoc.i_doctypeid = t_doctype.i_doctypeid) ");
        sb.append("LEFT JOIN t_city ON (t_govdoc.i_placeofissueid = t_city.i_cityid) ");
        sb.append("LEFT JOIN t_documentissuedby ON (t_govdoc.i_documentissuedbyid = t_documentissuedby.i_documentissuedbyid) ");
        sb.append("LEFT JOIN t_country ON (t_city.i_countryid = t_country.i_countryid) ");
        sb.append("LEFT JOIN (SELECT i_govid, COUNT(1) AS ct FROM t_documentfiles WHERE i_status = 1 GROUP BY i_govid) AS t1 ON (t1.i_govid = t_govdoc.i_govid) ");
        sb.append("WHERE t_govdoc.i_candidateid = ? ");
        sb.append("ORDER BY t_govdoc.i_status, t_govdoc.d_expirydate ");
        String query = (sb.toString()).intern();
        sb.setLength(0);
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, candidateId);
            logger.info("getCandgovdocList :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String documentname, documentno, placeofissue, issuedby, dateofissue, dateofexpiry, countryname;
            int govdocumentId, status, filecount;
            while (rs.next()) {
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
                if (!countryname.equals("")) {
                    placeofissue += "(" + countryname + ")";
                }
                list.add(new TalentpoolInfo(govdocumentId, documentname, documentno, placeofissue, issuedby,
                        status, dateofissue, dateofexpiry, countryname, filecount, "", candidateId));
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return list;
    }

    public TalentpoolInfo getcandgovForModify(int govdocumentId) {
        TalentpoolInfo info = null;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT i_doctypeid, s_docno,i_placeofissueid,i_documentissuedbyid,DATE_FORMAT(t_govdoc.d_dateofissue, '%d-%b-%Y'),  ");
        sb.append("DATE_FORMAT(t_govdoc.d_expirydate, '%d-%b-%Y'),t_govdoc.i_status, t_city.s_name, t_country.s_name ");
        sb.append("FROM t_govdoc ");
        sb.append("LEFT JOIN t_city ON (t_city.i_cityid = t_govdoc.i_placeofissueid) ");
        sb.append("LEFT JOIN t_country ON (t_country.i_countryid = t_city.i_countryid) WHERE i_govid = ? ");
        String query = (sb.toString()).intern();
        sb.setLength(0);
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, govdocumentId);
            logger.info("getcandgovForModify :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String documentno, dateofissue, dateofexpiry, cityName, countryName;
            int placeofissueId, issuedbyId, documenttypeId, status;
            while (rs.next()) {
                documenttypeId = rs.getInt(1);
                documentno = decipher(rs.getString(2) != null ? rs.getString(2) : "");
                placeofissueId = rs.getInt(3);
                issuedbyId = rs.getInt(4);
                dateofissue = rs.getString(5) != null ? rs.getString(5) : "";
                dateofexpiry = rs.getString(6) != null ? rs.getString(6) : "";
                status = rs.getInt(7);
                cityName = rs.getString(8) != null ? rs.getString(8) : "";
                countryName = rs.getString(9) != null ? rs.getString(9) : "";
                if (!countryName.equals("")) {
                    cityName += "(" + countryName + ")";
                }
                info = new TalentpoolInfo(documenttypeId, documentno, placeofissueId, issuedbyId, dateofissue, dateofexpiry, status, cityName);
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return info;
    }

    public int checkDuplicacygovdocument(int candidateId, int govdocumentId, int documentTypeId) {
        int ck = 0;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT i_govid FROM t_govdoc WHERE i_candidateid = ?  AND i_doctypeid = ?  AND  i_status  IN (1, 2) ");
        if (govdocumentId > 0) {
            sb.append("AND i_govid != ? ");
        }
        String query = (sb.toString()).intern();
        sb.setLength(0);
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            int scc = 0;
            pstmt.setInt(++scc, candidateId);
            pstmt.setInt(++scc, documentTypeId);
            if (govdocumentId > 0) {
                pstmt.setInt(++scc, govdocumentId);
            }
            print(this, "checkDuplicacygovdocument :: " + pstmt.toString());
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

    public int insertGovdocumentdetail(TalentpoolInfo info, int candidateId, int userId, String userName) {
        int govdocumentId = 0;
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("INSERT INTO t_govdoc  ");
            sb.append("( i_candidateid, i_doctypeid,s_docno,i_placeofissueid,i_documentissuedbyid, ");
            sb.append("d_dateofissue,d_expirydate, i_status, i_userid, ts_regdate, ts_moddate) ");
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
            print(this, "insertGovdocumentdetail : " + pstmt.toString());
            rs = pstmt.getGeneratedKeys();
            while (rs.next()) {
                govdocumentId = rs.getInt(1);
            }
            String remarks = "Verify Documents tab";
            insertAlertBy(candidateId, 10, 1, remarks, userId, conn, userName);
            updateverification(10, userId, candidateId, govdocumentId);
        } catch (Exception exception) {
            print(this, "insertGovdocumentdetail :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return govdocumentId;
    }

    public int updateGovdocumentRecord(TalentpoolInfo info, int candidateId, int userId, String userName) {
        int cc = 0;
        try {
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
            String remarks = "Verify Documents tab";
            insertAlertBy(candidateId, 10, 1, remarks, userId, conn, userName);
            updateverification(10, userId, candidateId, info.getGovdocumentId());
        } catch (Exception exception) {
            print(this, "updateGovdocumentRecord :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return cc;
    }

    public int deletegovdocument(int candidateId, int govdocumentId, int userId, int status, 
            String ipAddrStr, String iplocal, String username) {
        int cc = 0;
        try {
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
            print(this, "deletegovdocument :: " + pstmt.toString());
            cc = pstmt.executeUpdate();

            String remarks = "Verify Documents tab";
            insertAlertBy(candidateId, 10, 1, remarks, userId, conn, username);
            updateverification(10, userId, candidateId, govdocumentId);
        } catch (Exception exception) {
            print(this, "deletegovdocument :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        createHistoryAccess(null, userId, ipAddrStr, iplocal, "Data Deleted", 7, govdocumentId);
        return cc;
    }

    public int inserttrainingCertificate(TalentpoolInfo info, int candidateId, int uId, String unsername) {
        int trainingandcertId = 0;
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("INSERT INTO t_trainingandcert (i_coursetypeid, i_coursenameid, s_educinst, i_locationofinstitid, s_fieldofstudy, d_coursestart, d_passingyear, ");
            sb.append("d_dateofissue, s_certificateno, d_expirydate, s_courseverification, i_approvedbyid, s_filename, i_status,i_candidateid, ts_regdate,ts_moddate) ");
            sb.append("VALUES ( ?,?,?,?,?,  ?,?,?,?,?,  ?,?,?,?,?,  ?,? )");//16
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
            print(this, "inserttrainingCertificate :: " + pstmt.toString());
            pstmt.executeUpdate();
            rs = pstmt.getGeneratedKeys();
            while (rs.next()) {
                trainingandcertId = rs.getInt(1);
            }
            String remarks = "Verify Certifications tab";
            insertAlertBy(candidateId, 7, 1, remarks, uId, conn, unsername);
            updateverification(7, uId, candidateId, trainingandcertId);
            rs.close();
            pstmt.close();
        } catch (Exception exception) {
            print(this, "inserttrainingCertificate :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return trainingandcertId;
    }

    public int updatetrainingCertificate(TalentpoolInfo info, int candidateId, int uId, String username) {
        int cc = 0;
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("UPDATE t_trainingandcert SET i_coursetypeid =?, i_coursenameid =?, s_educinst =?, i_locationofinstitid =?, s_fieldofstudy =?, ");
            sb.append("d_coursestart =?, d_passingyear =?, d_dateofissue =?, s_certificateno =?, d_expirydate =?, s_courseverification =?, i_approvedbyid =?, ");
            if (info.getCertifilename() != null && !info.getCertifilename().equals("")) {
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
            if (info.getCertifilename() != null && !info.getCertifilename().equals("")) {
                pstmt.setString(++scc, info.getCertifilename());
            }
            pstmt.setInt(++scc, info.getStatus());
            pstmt.setString(++scc, currDate1());
            pstmt.setInt(++scc, info.getTrainingandcertId());
            pstmt.setInt(++scc, candidateId);
            print(this, "updatetrainingCertificate :: " + pstmt.toString());
            cc = pstmt.executeUpdate();
            String remarks = "Verify Certifications tab";
            insertAlertBy(candidateId, 7, 1, remarks, uId, conn, username);
            updateverification(7, uId, candidateId, info.getTrainingandcertId());
        } catch (Exception exception) {
            print(this, "updatetrainingCertificate :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return cc;
    }

    public int checkDuplicacytrainingCertificate(int candidateId, int trainingcertId, int coursetypeId, int coursenameId, String educinst, String dateofissue) {
        int ck = 0;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT i_tcid FROM t_trainingandcert WHERE i_candidateid =? AND i_coursetypeid =? AND i_coursenameid =? AND s_educinst =? AND d_dateofissue =? AND i_status IN (1, 2) ");
        if (trainingcertId > 0) {
            sb.append("AND i_tcid !=? ");
        }
        String query = (sb.toString()).intern();
        sb.setLength(0);
        try {
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
            print(this, "checkDuplicacytrainingCertificate :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return ck;
    }

    public TalentpoolInfo gettrainingCertificateDetailById(int candidateId) {
        TalentpoolInfo info = null;
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
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, candidateId);
            print(this, "getCertificateDetailById :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
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
                String coursename = rs.getString(17) != null ? rs.getString(17) : "";
                if (!countryName.equals("")) {
                    cityName += " (" + countryName + ")";
                }
                info = new TalentpoolInfo(coursetypeId, coursenameId, educinst, locationofinstit, fieldofstudy, coursestart, passingyear, dateofissue, certificateno,
                        expirydate, courseverification, approvedbyId, filename, status, cityName, coursename);
            }
        } catch (Exception exception) {
            print(this, "getCertificateDetailById :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return info;
    }

    public ArrayList gettrainingCertificatelist(int candidateId, int next, int count, int courseIndex) {
        ArrayList list = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT t_coursename.s_name, t_coursetype.s_name, t_trainingandcert.s_educinst, t_city.s_name, t_approvedby.s_name, ");
        sb.append("DATE_FORMAT(t_trainingandcert.d_dateofissue, '%d-%b-%Y'), DATE_FORMAT(t_trainingandcert.d_expirydate, '%d-%b-%Y'), t_trainingandcert.i_status, ");
        sb.append("t_trainingandcert.i_tcid, t_trainingandcert.s_filename, t_country.s_name, t_trainingandcert.i_coursenameid, t_trainingandcert.s_url ");
        sb.append("FROM t_trainingandcert ");
        sb.append("LEFT JOIN t_coursetype ON (t_coursetype.i_coursetypeid = t_trainingandcert. i_coursetypeid) ");
        sb.append("LEFT JOIN t_city ON (t_city.i_cityid = t_trainingandcert.i_locationofinstitid) ");
        sb.append("LEFT JOIN t_coursename ON (t_coursename.i_coursenameid = t_trainingandcert.i_coursenameid) ");
        sb.append("LEFT JOIN t_country ON (t_city.i_countryid = t_country. i_countryid) ");
        sb.append("LEFT JOIN t_approvedby ON (t_approvedby.i_approvedbyid = t_trainingandcert.i_approvedbyid) ");
        sb.append("WHERE t_trainingandcert.i_candidateid = ? ");
        if (courseIndex > 0) {
            sb.append(" AND ( t_coursename.i_coursenameid = ? ) ");
        }
        sb.append("ORDER BY t_trainingandcert.i_status, t_trainingandcert.d_expirydate ");
        if (count > 0) {
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
        if (courseIndex > 0) {
            sb.append(" AND ( t_coursename.i_coursenameid = ? ) ");
        }
        sb.append("ORDER BY t_trainingandcert.i_status, t_trainingandcert.d_expirydate ");
        String countquery = (sb.toString()).intern();
        sb.setLength(0);
        int scc = 0;
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(++scc, candidateId);
            if (courseIndex > 0) {
                pstmt.setInt(++scc, courseIndex);
            }
            logger.info("gettrainingCertificatelist :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
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
                int coursenameId = rs.getInt(12);
                String url = rs.getString(13) != null ? rs.getString(13) : "";
                if (!educinst.equals("")) {
                    if (!countryname.equals("")) {
                        educinst += "," + locationofinstit + " (" + countryname + ")";
                    }
                }
                list.add(new TalentpoolInfo(candidateId, coursename, coursetype, educinst, approvedby, dateofissue,
                        expirydate, status, trainingandcertId, filename, coursenameId, url));
            }
            rs.close();

            pstmt = conn.prepareStatement(countquery);
            scc = 0;
            pstmt.setInt(++scc, candidateId);
            if (courseIndex > 0) {
                pstmt.setInt(++scc, courseIndex);
            }
            print(this, "gettrainingCertificatelist :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                candidateId = rs.getInt(1);
                list.add(new TalentpoolInfo(candidateId, "", "", "", "", "", "", 0, 0, "", 0, ""));
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return list;
    }

    public int deletetrainingCertificate(int candidateId, int candidatecertficateId, int userId, 
            int status, String ipAddrStr, String iplocal, String username) {
        int cc = 0;
        try {
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
            print(this, "deleteCertification :: " + pstmt.toString());
            cc = pstmt.executeUpdate();
            String remarks = "Verify Certifications tab";
            insertAlertBy(candidateId, 7, 1, remarks, userId, conn, username);
            updateverification(7, userId, candidateId, candidatecertficateId);
        } catch (Exception exception) {
            print(this, "deleteCertification :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        createHistoryAccess(null, userId, ipAddrStr, iplocal, "Data Deleted", 7, candidatecertficateId);
        return cc;
    }

    public int inserteducation(TalentpoolInfo info, int candidateId, int uId, String username) {
        int educationdetailId = 0;
        try {
            if (info.getHighestqualification() == 1) {
                String primaryquery = "UPDATE t_eduqual SET i_highestqualification = ? WHERE  i_candidateid = ? ";
                conn = getConnection();
                pstmt = conn.prepareStatement(primaryquery);
                pstmt.setInt(1, 0);
                pstmt.setInt(2, candidateId);
                print(this, "set inserteducation :" + pstmt.toString());
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
            logger.info("create education :: " + pstmt.toString());
            pstmt.executeUpdate();
            rs = pstmt.getGeneratedKeys();
            while (rs.next()) {
                educationdetailId = rs.getInt(1);
            }
            String remarks = "Verify Education tab";
            insertAlertBy(candidateId, 6, 1, remarks, uId, conn, username);
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

    public int updateeducation(TalentpoolInfo info, int candidateId, int uId, String username) {
        int cc = 0;
        try {
            if (info.getHighestqualification() == 1) {
                String primaryquery = "UPDATE t_eduqual SET i_highestqualification = ? WHERE  i_candidateid = ? ";
                conn = getConnection();
                pstmt = conn.prepareStatement(primaryquery);
                pstmt.setInt(1, 0);
                pstmt.setInt(2, candidateId);
                print(this, "set updateeducation :" + pstmt.toString());
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
            if (info.getEducationalfilename() != null && !info.getEducationalfilename().equals("")) {
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
            if (info.getEducationalfilename() != null && !info.getEducationalfilename().equals("")) {
                pstmt.setString(++scc, info.getEducationalfilename());
            }
            pstmt.setInt(++scc, info.getStatus());
            pstmt.setString(++scc, currDate1());
            pstmt.setInt(++scc, info.getEducationdetailId());
            pstmt.setInt(++scc, candidateId);
            cc = pstmt.executeUpdate();
            String remarks = "Verify Education tab";
            insertAlertBy(candidateId, 6, 1, remarks, uId, conn,  username);
            updateverification(6, uId, candidateId, info.getEducationdetailId());
        } catch (Exception exception) {
            print(this, "updateeducation :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return cc;
    }

    public int checkDuplicacyeducation(int candidateId, int educationdetailId, int kindId, int degreeId, String backgroundofstudy) {
        int ck = 0;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT i_eduqulid FROM t_eduqual WHERE i_candidateid = ?  AND i_qualificationtypeid = ? AND  i_degreeid = ? AND s_backgroundofstudy = ? AND  i_status  IN (1, 2)");
        if (educationdetailId > 0) {
            sb.append(" AND i_eduqulid != ? ");
        }
        String query = (sb.toString()).intern();
        sb.setLength(0);
        try {
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
            print(this, "checkDuplicacyeducation :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return ck;
    }

    public TalentpoolInfo geteducationDetailById(int educationdetailId) {
        TalentpoolInfo info = null;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT i_qualificationtypeid, i_degreeid,s_backgroundofstudy, s_eduinstitute, i_locationofinstituteid,  s_fieldofstudy, ");
        sb.append("DATE_FORMAT(t_eduqual.d_coursestart, '%d-%b-%Y'),  DATE_FORMAT(t_eduqual.d_passingyear, '%d-%b-%Y'), ");
        sb.append("i_highestqualification, t_eduqual.s_filename, t_eduqual.i_status, t_city.s_name, t_country.s_name ");
        sb.append("FROM t_eduqual ");
        sb.append("LEFT JOIN t_city ON (t_city.i_cityid = t_eduqual.i_locationofinstituteid) ");
        sb.append("LEFT JOIN t_country ON (t_country.i_countryid = t_city.i_countryid) ");
        sb.append("WHERE i_eduqulid = ? ");//14
        String query = sb.toString().intern();
        sb.setLength(0);
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, educationdetailId);
            print(this, "geteducationDetailById :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                int kindId = rs.getInt(1);
                int degreeId = rs.getInt(2);
                String backgroundofstudy = rs.getString(3) != null ? rs.getString(3) : "";
                String educinst = decipher(rs.getString(4) != null ? rs.getString(4) : "");
                int locationofinstit = rs.getInt(5);
                String fieldofstudy = rs.getString(6) != null ? rs.getString(6) : "";
                String coursestart = rs.getString(7) != null ? rs.getString(7) : "";
                String passingyear = rs.getString(8) != null ? rs.getString(8) : "";
                int highestqualification = rs.getInt(9);
                String filename = rs.getString(10) != null ? rs.getString(10) : "";
                int status = rs.getInt(11);
                String cityname = rs.getString(12) != null ? rs.getString(12) : "";
                String countryName = rs.getString(13) != null ? rs.getString(13) : "";
                if (cityname != null && !cityname.equals("")) {
                    cityname += " (" + countryName + ")";
                }
                info = new TalentpoolInfo(kindId, degreeId, backgroundofstudy, educinst, locationofinstit, fieldofstudy,
                        coursestart, passingyear, highestqualification, filename, status, cityname);
            }
        } catch (Exception exception) {
            print(this, "geteducationDetailById :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return info;
    }

    public ArrayList geteducationlist(int candidateId) {
        ArrayList list = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT  t_qualificationtype.s_name,  t_degree.s_name, t_eduqual.s_eduinstitute, t_city.s_name,t_eduqual.s_fieldofstudy,  ");
        sb.append("DATE_FORMAT(t_eduqual.d_coursestart, '%d-%b-%Y'), DATE_FORMAT(t_eduqual.d_passingyear, '%d-%b-%Y') ,t_eduqual.i_status, ");
        sb.append("t_eduqual.i_eduqulid , t_eduqual.s_filename,t_country.s_name ");
        sb.append("FROM t_eduqual ");
        sb.append("LEFT JOIN t_qualificationtype ON (t_qualificationtype.i_qualificationtypeid = t_eduqual.i_qualificationtypeid) ");
        sb.append("LEFT JOIN t_city ON (t_city.i_cityid = t_eduqual.i_locationofinstituteid) ");
        sb.append("LEFT JOIN t_degree ON (t_degree.i_degreeid = t_eduqual. i_degreeid) ");
        sb.append("LEFT JOIN t_country ON (t_city.i_countryid = t_country. i_countryid) ");
        sb.append("WHERE t_eduqual.i_candidateid = ? ");
        sb.append("ORDER BY t_eduqual.d_passingyear DESC ");
        String query = (sb.toString()).intern();
        sb.setLength(0);
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, candidateId);
            logger.info("geteducationlist :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
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
                if (!countryname.equals("")) {
                    locationofinstit += "(" + countryname + ")";
                }
                list.add(new TalentpoolInfo(kindname, degree, educinst, locationofinstit, filedofstudy, startdate, enddate, status, filename, educationdetailId));
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return list;
    }

    public int deleteeducation(int candidateId, int educationdetailId, int userId, int status, 
            String ipAddrStr, String iplocal, String username) {
        int cc = 0;
        try {
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

            String remarks = "Verify Education tab";
            insertAlertBy(candidateId, 6, 1, remarks, userId, conn, username);
            updateverification(6, userId, candidateId, educationdetailId);
        } catch (Exception exception) {
            print(this, "deleteeducation :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        createHistoryAccess(null, userId, ipAddrStr, iplocal, "Data Deleted", 7, educationdetailId);
        return cc;
    }

    public int insertexperiencedetail(TalentpoolInfo info, int candidateId, int uId, String username) {
        int experiencedetailId = 0;
        try {
            if (info.getCurrentworkingstatus() == 1) {
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
            sb.append("INSERT INTO t_workexperience (s_companyname, i_companyindustryid, i_assettypeid, s_assetname, i_positionid, i_departmentid, i_countryid, i_cityid, ");
            sb.append("s_clientpartyname, i_waterdepthid, i_lastdrawnsalarycurrencyid, i_currentworkingstatus, i_skillid, i_gradeid, s_ownerpool, i_crewtypeid, s_ocsemployed, ");
            sb.append("s_legalrights, i_dayratecurrencyid, i_dayrate, i_monthlysalarycurrencyid, i_monthlysalary, d_workstartdate, d_workenddate, i_lastdrawnsalary, ");
            sb.append("s_filenamework, s_filenameexp, i_status, i_userid, i_candidateid, s_role, ts_regdate, ts_moddate) ");
            sb.append("VALUES ( ?,?,?,?,?,  ?,?,?,?,?,  ?,?,?,?,?,?,?,?,?,  ?,?,?,?,?,  ?,?,?,?,?,?,  ?,?  , ? )");//33
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
            pstmt.executeUpdate();
            rs = pstmt.getGeneratedKeys();
            while (rs.next()) {
                experiencedetailId = rs.getInt(1);
            }
            String remarks = "Verify Experience tab";
            insertAlertBy(candidateId, 5, 1, remarks, uId, conn, username);
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

    public int updateexperiencedetail(TalentpoolInfo info, int candidateId, int uId, String username) {
        int cc = 0;
        try {
            if (info.getCurrentworkingstatus() == 1) {
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
            sb.append("i_status =?, i_userid =?, ts_moddate =?  ");            
            sb.append("WHERE i_experienceid =? AND i_candidateid =? ");
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

            String remarks = "Verify Experience tab";
            insertAlertBy(candidateId, 5, 1, remarks, uId, conn, username);
            updateverification(5, uId, candidateId, info.getExperiencedetailId());
        } catch (Exception exception) {
            print(this, "updateexperiencedetail :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return cc;
    }

    public int checkDuplicacyexperience(int candidateId, int experiencedetailId, String companyName, String workstartdate, String workenddate) {
        int ck = 0;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT i_experienceid FROM t_workexperience WHERE i_candidateid = ?  AND s_companyname = ? AND d_workstartdate = ? AND d_workenddate = ? AND i_status  IN (1, 2)");
        if (experiencedetailId > 0) {
            sb.append(" AND i_experienceid != ? ");
        }
        String query = (sb.toString()).intern();
        sb.setLength(0);
        try {
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

    public TalentpoolInfo getexperiencedetailById(int experiencedetailId) {
        TalentpoolInfo info = null;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT s_companyname, i_companyindustryid, i_assettypeid, s_assetname, i_positionid, i_departmentid, t_workexperience.i_countryid, ");
        sb.append("t_workexperience.i_cityid, s_clientpartyname, i_waterdepthid, i_lastdrawnsalarycurrencyid, i_currentworkingstatus, i_skillid, i_gradeid, s_ownerpool, ");
        sb.append("i_crewtypeid, s_ocsemployed, s_legalrights, i_dayratecurrencyid, i_dayrate, i_monthlysalarycurrencyid, i_monthlysalary, ");
        sb.append("DATE_FORMAT(t_workexperience.d_workstartdate, '%d-%b-%Y'), DATE_FORMAT(t_workexperience.d_workenddate, '%d-%b-%Y'), i_lastdrawnsalary, s_filenamework, ");
        sb.append("s_filenameexp, t_workexperience.i_status, t_city.s_name, t_workexperience.s_role ");
        sb.append("FROM t_workexperience ");
        sb.append("LEFT JOIN t_city ON (t_city.i_cityid = t_workexperience.i_cityid) WHERE i_experienceid =? ");//14
        String query = sb.toString().intern();
        sb.setLength(0);
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, experiencedetailId);
            print(this, "getexperiencedetailById :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                String companyName = rs.getString(1) != null ? rs.getString(1) : "";
                int companyindustryId = rs.getInt(2);
                int assettypeId = rs.getInt(3);
                String assetName = rs.getString(4) != null ? rs.getString(4) : "";
                int positionId = rs.getInt(5);
                int departmentId = rs.getInt(6);
                int countryId = rs.getInt(7);
                int cityId = rs.getInt(8);
                String clientpartyname = rs.getString(9) != null ? rs.getString(9) : "";
                int waterdepthId = rs.getInt(10);
                int lastdrawnsalarycurrencyId = rs.getInt(11);
                int currentworkingstatus = rs.getInt(12);
                int skillId = rs.getInt(13);
                int gradeId = rs.getInt(14);
                String ownerpool = rs.getString(15) != null ? rs.getString(15) : "";
                int crewtypeId = rs.getInt(16);
                String ocsemployed = rs.getString(17) != null ? rs.getString(17) : "";
                String legalrights = rs.getString(18) != null ? rs.getString(18) : "";
                int dayratecurrencyId = rs.getInt(19);
                int dayrate = rs.getInt(20);
                int monthlysalarycurrencyId = rs.getInt(21);
                int monthlysalary = rs.getInt(22);
                String workstartdate = rs.getString(23) != null ? rs.getString(23) : "";
                String workenddate = rs.getString(24) != null ? rs.getString(24) : "";
                String lastdrawnsalary = rs.getString(25) != null ? rs.getString(25) : "";
                String filenamework = rs.getString(26) != null ? rs.getString(26) : "";
                String filenameexp = rs.getString(27) != null ? rs.getString(27) : "";
                int status = rs.getInt(28);
                String cityname = rs.getString(29) != null ? rs.getString(29) : "";
                String roles = rs.getString(30) != null ? rs.getString(30) : "";
                
                info = new TalentpoolInfo(companyName, companyindustryId, assettypeId, assetName, positionId, departmentId, countryId, cityId, clientpartyname,
                        waterdepthId, lastdrawnsalarycurrencyId, currentworkingstatus, skillId, gradeId, ownerpool, crewtypeId, ocsemployed, legalrights, dayratecurrencyId,
                        dayrate, monthlysalarycurrencyId, monthlysalary, workstartdate, workenddate, lastdrawnsalary, filenamework, filenameexp, cityname, status, roles);
            }
        } catch (Exception exception) {
            print(this, "getexperiencedetailById :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return info;
    }

    public ArrayList getexperiencelist(int candidateId) {
        ArrayList list = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT  t_position.s_name,  t_experiencedept.s_name, t_workexperience.s_companyname, t_workexperience.s_assetname,  ");
        sb.append("DATE_FORMAT(t_workexperience.d_workstartdate, '%d-%b-%Y'), DATE_FORMAT(t_workexperience.d_workenddate, '%d-%b-%Y'), ");
        sb.append("t_workexperience.i_status,t_workexperience.i_experienceid , t_workexperience.s_filenameexp, ");
        sb.append("t_workexperience.s_filenamework,t_workexperience.i_currentworkingstatus, t_grade.s_name ");
        sb.append("FROM t_workexperience ");
        sb.append("LEFT JOIN t_position ON (t_position.i_positionid = t_workexperience.i_positionid) ");
        sb.append("LEFT JOIN t_grade ON(t_grade.i_gradeid = t_position.i_gradeid) ");
        sb.append("LEFT JOIN t_experiencedept ON (t_experiencedept.i_experiencedeptid = t_workexperience.i_departmentid) ");
        sb.append("WHERE t_workexperience.i_candidateid = ? ");
        sb.append("ORDER BY t_workexperience.i_status, t_workexperience.d_workstartdate DESC ");
        String query = (sb.toString()).intern();
        sb.setLength(0);
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, candidateId);
            logger.info("getexperiencelist :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
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
                if (currentworkingstatus == 1) {
                    enddate = "Present";
                }
                String grade = rs.getString(12) != null ? rs.getString(12) : "";
                if (!position.equals("")) {
                    if (!grade.equals("")) {
                        position += " | " + grade;
                    }
                }
                list.add(new TalentpoolInfo(position, department, companyname, assetname, startdate, enddate, status, workfilename, experiencefilename, experiencedetailId));
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return list;
    }

    public int deleteexperiencedetail(int candidateId, int experiencedetailId, int userId, int status, 
            String ipAddrStr, String iplocal, String username) {
        int cc = 0;
        try {
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
            String remarks = "Verify Experience tab";
            insertAlertBy(candidateId, 5, 1, remarks, userId, conn, username);
            updateverification(5, userId, candidateId, experiencedetailId);
        } catch (Exception exception) {
            print(this, "deleteeducation :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        createHistoryAccess(null, userId, ipAddrStr, iplocal, "Data Deleted", 7, experiencedetailId);
        return cc;
    }

    public ArrayList getPics(int clientassetId) {
        ArrayList list = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT t_candidatefiles.i_fileid, t_candidatefiles.s_name, DATE_FORMAT(t_candidatefiles.ts_regdate, '%d-%b-%Y %H:%i'), ");
        sb.append("t_userlogin.s_name, t_candidatefiles.s_localname ");
        sb.append("FROM t_candidatefiles LEFT JOIN t_userlogin ON (t_userlogin.i_userid = t_candidatefiles.i_userid) ");
        sb.append("WHERE t_candidatefiles.i_candidateid = ? ");
        sb.append(" ORDER BY  t_candidatefiles.i_fileid DESC");
        String query = (sb.toString()).intern();
        sb.setLength(0);
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, clientassetId);
            logger.info("getPics :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String filename, date, name, localFile;
            int fileId;
            while (rs.next()) {
                fileId = rs.getInt(1);
                filename = rs.getString(2) != null ? rs.getString(2) : "";
                date = rs.getString(3) != null ? rs.getString(3) : "";
                name = rs.getString(4) != null ? rs.getString(4) : "";
                localFile = rs.getString(5) != null ? rs.getString(5) : "";
                list.add(new TalentpoolInfo(fileId, filename, date, name, localFile, 0));
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return list;
    }

    public int createPic(Connection conn, int candidateId, String filename, int userId, String localFile) {
        int cc = 0;
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("INSERT INTO t_candidatefiles ");
            sb.append("(i_candidateid, s_name, i_userid,i_status, ts_regdate, ts_moddate, s_localname) ");
            sb.append("VALUES (?, ?, ?, ?,?,?, ?)");
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

    public int delpic(int clientassetpicId) {
        int cc = 0;
        String query = "SELECT s_name FROM t_candidatefiles WHERE i_fileid = ?";
        try {
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

    public int updatefile(int childId, int type) {
        int cc = 0;
        String query = "";
        if (type == 1) {
            query = "SELECT s_filename FROM t_candlang WHERE i_candlangid = ?";
        } else if (type == 2) {
            query = "SELECT s_filenameexp FROM t_workexperience WHERE i_experienceid = ?";
        }
        try {
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

    public int deleteCandidate(int candidateId, int userId, int status, String ipAddrStr, String iplocal) {
        int cc = 0;
        try {
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

    public String checkEmail(int candidateId, String email) {
        String output = "";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
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
            print(this, "Something went wrong.");
        } finally {
            close(conn, pstmt, rs);
        }
        return output;
    }

    public String getPartyAutoFill(String val) {
        String s = "";
        String query = ("SELECT distinct(s_clientpartyname) FROM t_workexperience WHERE s_clientpartyname LIKE ? ORDER BY  s_clientpartyname ");
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, "%" + (val) + "%");
            rs = pstmt.executeQuery();
            JSONArray jsonArrayParent = new JSONArray();
            String partyname;
            while (rs.next()) {
                partyname = rs.getString(1) != null ? rs.getString(1) : "";
                if (!partyname.equals("")) {
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
    public ArrayList getCandidateListForExcel(String search, int statusIndex, int positionIndex, int clientIndex, int locationIndex,
            int assetIndex, int Verified, int allclient, String permission, String cids, String assetids) {
        ArrayList list = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT c.i_candidateid,DATE_FORMAT(c.ts_regdate, '%d-%b-%Y'), CONCAT_WS(' ', c.s_firstname, c.s_middlename, c.s_lastname), ");
        sb.append(" t_country1.s_name, c.i_status,  t_position.s_name,  ");
        sb.append("c.s_firstname, t_client.s_name,t_clientasset.s_name, t_grade.s_name , c.i_progressid, a1.ct , c.i_vflag, c.i_clientid, ");
        sb.append("t_city.s_name, c.s_contactno1, c.s_econtactno1, c.s_contactno2,  DATE_FORMAT(c.d_dob, '%d-%b-%Y') , c.s_placeofbirth, ");
        sb.append("c.s_gender, t_maritialstatus.s_name, c.s_nextofkin, t_relation.s_name, ");
        sb.append("c.s_address1line1, c.s_address1line2, c.s_address1line3, c.s_address2line1, c.s_address2line2, c.s_address2line3, c.s_email, ");
        sb.append("c.s_code1, c.s_code2, c.s_code3, c.s_empno, t_assettype.s_name, t_country2.s_name, t_experiencedept.s_name, ");
        sb.append("t1.ct, t2.ct, t3.ct, t4.ct, t5.ct, t6.ct, t8.ct, t9.ct, t_country1.s_nationality, t_currency.s_name, t_dayrate.d_rate1, t_dayrate.d_rate2, c.i_expectedsalary, ");
        sb.append("DATE_FORMAT(c1.fromdate, '%d-%b-%Y'), tr2.remark, tr2.reason, enddate ");
        sb.append("FROM t_candidate AS c  ");
        sb.append("LEFT JOIN (SELECT i_candidateid, COUNT(1) AS ct FROM t_alert WHERE i_status = 1 GROUP BY i_candidateid) AS a1 ON ( c.i_candidateid = a1.i_candidateid ) ");
        sb.append("LEFT JOIN t_clientasset ON ( c.i_clientassetid = t_clientasset.i_clientassetid ) ");
        sb.append("LEFT JOIN t_country AS t_country1 ON ( t_country1.i_countryid = t_clientasset.i_countryid ) ");
        sb.append("LEFT JOIN t_country AS t_country2 ON ( t_country2.i_countryid = c.i_countryid ) ");
        sb.append("LEFT JOIN t_city ON ( c.i_cityid = t_city.i_cityid ) ");
        sb.append("LEFT JOIN t_position ON ( c.i_positionid = t_position.i_positionid ) ");
        sb.append("LEFT JOIN t_grade ON (t_grade.i_gradeid = t_position.i_gradeid) ");
        sb.append("LEFT JOIN t_client ON ( c.i_clientid = t_client.i_clientid) ");
        sb.append("LEFT JOIN t_maritialstatus ON (t_maritialstatus.i_maritialstatusid = c.i_maritialstatusid) ");
        sb.append("LEFT JOIN t_relation ON (t_relation.i_relationid = c.i_relationid) ");
        sb.append("LEFT JOIN t_assettype ON (t_assettype.i_assettypeid = c.i_assettypeid) ");
        sb.append("LEFT JOIN t_experiencedept ON (t_experiencedept.i_experiencedeptid = c.i_departmentid) ");
        sb.append("LEFT JOIN t_currency ON (t_currency.i_currencyid = c.i_currencyid) ");
        sb.append("LEFT JOIN t_dayrate ON (t_dayrate.i_candidateid = c.i_candidateid AND t_dayrate.i_positionid = c.i_positionid and c.i_clientassetid = t_dayrate.i_clientassetid) ");
        sb.append("LEFT JOIN (SELECT t_crewrotation.i_candidateid, MIN(ts_fromdate) AS fromdate FROM t_cractivity left join t_crewrotation on (t_crewrotation.i_crewrotationid = t_cractivity.i_crewrotationid) ");
        sb.append("WHERE t_cractivity.i_activityid = 6 and t_crewrotation.i_active = 1 GROUP BY  t_crewrotation.i_candidateid) AS c1 ON (c.i_candidateid = c1.i_candidateid) ");        
        sb.append("LEFT JOIN (SELECT tr1.i_candidateid, tr1.s_remark AS remark, tr1.s_reason AS reason, DATE_FORMAT(tr1.s_enddate, '%d-%b-%Y') AS enddate ");
        sb.append("FROM (SELECT * FROM t_transfer WHERE i_type = 2 ORDER BY s_enddate DESC, ts_regdate DESC) ");
        sb.append("AS tr1 GROUP BY i_candidateid) AS tr2 ON (c.i_candidateid = tr2.i_candidateid) ");
        sb.append("LEFT JOIN (SELECT i_candidateid, COUNT(1) AS ct FROM t_candlang GROUP BY i_candidateid) AS t1 ON (t1.i_candidateid = c.i_candidateid) ");
        sb.append("LEFT JOIN (SELECT i_candidateid, COUNT(1) AS ct FROM t_healthdeclaration GROUP BY i_candidateid) AS t2 ON (t2.i_candidateid = c.i_candidateid) ");
        sb.append("LEFT JOIN (SELECT i_candidateid, COUNT(1) AS ct FROM t_vbd GROUP BY i_candidateid) AS t3 ON (t3.i_candidateid = c.i_candidateid) ");
        sb.append("LEFT JOIN (SELECT i_candidateid, COUNT(1) AS ct FROM t_workexperience GROUP BY i_candidateid) AS t4 ON (t4.i_candidateid = c.i_candidateid) ");
        sb.append("LEFT JOIN (SELECT i_candidateid, COUNT(1) AS ct FROM t_eduqual GROUP BY i_candidateid) AS t5 ON (t5.i_candidateid = c.i_candidateid) ");
        sb.append("LEFT JOIN (SELECT i_candidateid, COUNT(1) AS ct FROM t_trainingandcert GROUP BY i_candidateid) AS t6 ON (t6.i_candidateid = c.i_candidateid) ");
        sb.append("LEFT JOIN (SELECT i_candidateid, COUNT(1) AS ct FROM t_bankdetail GROUP BY i_candidateid) AS t8 ON (t8.i_candidateid = c.i_candidateid) ");
        sb.append("LEFT JOIN (SELECT i_candidateid, COUNT(1) AS ct FROM t_govdoc GROUP BY i_candidateid) AS t9 ON (t9.i_candidateid = c.i_candidateid) ");
        sb.append("WHERE 0 = 0  AND c.i_vflag IN  (3,4) AND c.i_pass = 2  ");
        if (allclient == 1) {
            sb.append(" AND c.i_clientid >= 0 ");
        } else {
            if (!cids.equals("")) {
                sb.append("AND (c.i_clientid IN (" + cids + ") OR (c.i_clientid <= 0 AND c.i_progressid = 0) OR (c.i_progressid = 1 AND c.i_candidateid IN (SELECT i_candidateid FROM t_shortlist WHERE i_jobpostid IN (SELECT i_jobpostid FROM t_jobpost WHERE i_status = 1 AND i_clientid IN (" + cids + ") )))) ");
            } else {
                sb.append("AND t_client.i_clientid < 0 ");
            }
            if (!assetids.equals("")) {
                sb.append("AND (c.i_clientassetid IN (" + assetids + ") OR (c.i_clientassetid <= 0 AND c.i_progressid = 0) OR (c.i_progressid = 1 AND c.i_candidateid IN (SELECT i_candidateid FROM t_shortlist WHERE i_jobpostid IN (SELECT i_jobpostid FROM t_jobpost WHERE i_status = 1 AND i_clientassetid IN (" + assetids + ") )))) ");
            }
        }
        if (Verified > 0) {
            sb.append("AND c.i_vflag =4 ");
        }
        if (positionIndex > 0) {
            sb.append("AND c.i_positionid =? ");
        }
        if (clientIndex > 0) {
            sb.append("AND c.i_clientid = ? ");
        }
        if (locationIndex > 0) {
            sb.append("AND t_clientasset.i_countryid = ? ");
        }
        if (assetIndex > 0) {
            sb.append("AND c.i_clientassetid = ? ");
        }
        if (statusIndex > 0) {
            sb.append("AND c.i_status =? ");
        }
        if (search != null && !search.equals("")) {
            sb.append("AND (CONCAT_WS(' ', c.s_firstname, c.s_middlename, c.s_lastname) LIKE ? OR CONCAT_WS(' ', c.s_firstname, c.s_lastname) LIKE ? OR c.i_candidateid LIKE ? ");
            sb.append("OR t_country1.s_name LIKE ? OR t_client.s_name LIKE ? OR t_position.s_name LIKE ? OR t_grade.s_name LIKE ? OR t_clientasset.s_name LIKE ? OR c.s_empno like ?) ");
        }
        if (Verified == 1) {
            sb.append("AND c.i_progressid = 0 AND c.i_clientid = 0 ");
        }
        if (Verified == 2) {
            sb.append("AND c.i_clientid >0 ");
        }
        sb.append("ORDER BY c.i_candidateid DESC ");

        String query = (sb.toString()).intern();
        sb.setLength(0);
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            int scc = 0;
            if (positionIndex > 0) {
                pstmt.setInt(++scc, positionIndex);
            }
            if (clientIndex > 0) {
                pstmt.setInt(++scc, clientIndex);
            }
            if (locationIndex > 0) {
                pstmt.setInt(++scc, locationIndex);
            }
            if (assetIndex > 0) {
                pstmt.setInt(++scc, assetIndex);
            }
            if (statusIndex > 0) {
                pstmt.setInt(++scc, statusIndex);
            }
            if (search != null && !search.equals("")) {
                pstmt.setString(++scc, "%" + (search) + "%");
                pstmt.setString(++scc, "%" + (search) + "%");
                pstmt.setString(++scc, search.replaceFirst("^0+(?!$)", ""));
                pstmt.setString(++scc, "%" + (search) + "%");
                pstmt.setString(++scc, "%" + (search) + "%");
                pstmt.setString(++scc, "%" + (search) + "%");
                pstmt.setString(++scc, "%" + (search) + "%");
                pstmt.setString(++scc, "%" + (search) + "%");
                pstmt.setString(++scc, "%" + (search) + "%");
            }
            logger.info("getCandidateListForExcel :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String name, countryName, date, positionName = "", prefferdept,
                    firstName, gradeName, clientName, clientAsset, position, city,
                    primarycontact, econtact, seccontact, email, assettype, candidatelocation,
                    dob, placeofbirth, gender, maritalstatus, Nextofkin, Relation, address1line1, address1line2, address1line3,
                    address2line1, address2line2, address2line3, employeeId, isdcode1, isdcode2, isdcode3, nationality, currency,
                    fromDate, remarks, endDate, reason;
            int candidateId, status, progressId, alertCount, vflag, clientId, ct1, ct2, ct3, ct4, ct5, ct6, ct7, ct8, ct9, expectedsalary;
            double rate1, rate2;
            while (rs.next()) {
                candidateId = rs.getInt(1);
                date = rs.getString(2) != null ? rs.getString(2) : "";
                name = rs.getString(3) != null ? rs.getString(3) : "";
                countryName = rs.getString(4) != null ? rs.getString(4) : "";
                status = rs.getInt(5);
                position = rs.getString(6) != null ? rs.getString(6) : "";
                firstName = rs.getString(7) != null ? rs.getString(7) : "";
                clientName = rs.getString(8) != null ? rs.getString(8) : "";
                clientAsset = rs.getString(9) != null ? rs.getString(9) : "";
                gradeName = rs.getString(10) != null ? rs.getString(10) : "";
                progressId = rs.getInt(11);
                alertCount = rs.getInt(12);
                vflag = rs.getInt(13);
                clientId = rs.getInt(14);
                city = rs.getString(15) != null ? rs.getString(15) : "";
                primarycontact = decipher(rs.getString(16) != null ? rs.getString(16) : "");
                econtact = decipher(rs.getString(17) != null ? rs.getString(17) : "");
                seccontact = decipher(rs.getString(18) != null ? rs.getString(18) : "");
                dob = rs.getString(19) != null ? rs.getString(19) : "";
                placeofbirth = rs.getString(20) != null ? rs.getString(20) : "";
                gender = rs.getString(21) != null ? rs.getString(21) : "";
                maritalstatus = rs.getString(22) != null ? rs.getString(22) : "";
                Nextofkin = decipher(rs.getString(23) != null ? rs.getString(23) : "");
                Relation = rs.getString(24) != null ? rs.getString(24) : "";
                address1line1 = rs.getString(25) != null ? rs.getString(25) : "";
                address1line2 = rs.getString(26) != null ? rs.getString(26) : "";
                address1line3 = rs.getString(27) != null ? rs.getString(27) : "";
                address2line1 = rs.getString(28) != null ? rs.getString(28) : "";
                address2line2 = rs.getString(29) != null ? rs.getString(29) : "";
                address2line3 = rs.getString(30) != null ? rs.getString(30) : "";
                email = rs.getString(31) != null ? rs.getString(31) : "";
                isdcode1 = rs.getString(32) != null ? rs.getString(32) : "";
                isdcode2 = rs.getString(33) != null ? rs.getString(33) : "";
                isdcode3 = rs.getString(34) != null ? rs.getString(34) : "";
                employeeId = rs.getString(35) != null ? rs.getString(35) : "";
                assettype = rs.getString(36) != null ? rs.getString(36) : "";
                candidatelocation = rs.getString(37) != null ? rs.getString(37) : "";
                prefferdept = rs.getString(38) != null ? rs.getString(38) : "";
                ct1 = rs.getInt(39);
                ct2 = rs.getInt(40);
                ct3 = rs.getInt(41);
                ct4 = rs.getInt(42);
                ct5 = rs.getInt(43);
                ct6 = rs.getInt(44);
                ct8 = rs.getInt(45);
                ct9 = rs.getInt(46);
                nationality = rs.getString(47) != null ? rs.getString(47) : "";
                currency = rs.getString(48) != null ? rs.getString(48) : "";
                rate1 = rs.getDouble(49);
                rate2 = rs.getDouble(50);
                expectedsalary = rs.getInt(51);
                fromDate = rs.getString(52) != null ? rs.getString(52) : "";
                remarks = rs.getString(53) != null ? rs.getString(53) : "";
                reason = rs.getString(54) != null ? rs.getString(54) : "";
                endDate = rs.getString(55) != null ? rs.getString(55) : "";
                if (gradeName != null && !gradeName.equals("")) {
                    positionName = position + " | " + gradeName;
                }
                if (primarycontact != null && !primarycontact.equals("")) {
                    if (isdcode1 != null && !isdcode1.equals("")) {
                        primarycontact = "+" + isdcode1 + " " + primarycontact;
                    }
                }
                if (econtact != null && !econtact.equals("")) {
                    if (isdcode2 != null && !isdcode2.equals("")) {
                        econtact = "+" + isdcode2 + " " + econtact;
                    }
                }
                if (seccontact != null && !seccontact.equals("")) {
                    if (isdcode3 != null && !isdcode3.equals("")) {
                        seccontact = "+" + isdcode3 + " " + seccontact;
                    }
                }
                list.add(new TalentpoolInfo(candidateId, name, countryName, status, date, positionName, firstName, clientName,
                        clientAsset, progressId, alertCount, vflag, clientId, gradeName, position, city, primarycontact, econtact,
                        seccontact, dob, placeofbirth, gender, maritalstatus, Nextofkin, Relation, address1line1, address1line2,
                        address1line3, address2line1, address2line2, address2line3, email, employeeId, assettype, candidatelocation,
                        prefferdept, ct1, ct2, ct3, ct4, ct5, ct6, ct8, ct9, nationality, currency, rate1, rate2, expectedsalary, fromDate, remarks, reason, endDate));
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return list;
    }

    public int updateverification(int tabno, int userId, int candidateId, int childId) {
        int cc = 0;
        try {
            conn = getConnection();
            String currdate = currDate1();
            if (tabno == 1 || tabno == 3) {
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
                } else if (tabno == 9) {
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
                while (rs.next()) {
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
                if (childst == 1 || childst == 2) {

                    String alertquery = "DELETE FROM t_alert WHERE i_candidateid = ? AND i_tabno = ?  ";
                    pstmt = conn.prepareStatement(alertquery);
                    pstmt.setInt(1, candidateId);
                    pstmt.setInt(2, tabno);
                    pstmt.executeUpdate();
                }
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

    public Collection getClientAssets() {
        Collection coll = new LinkedList();
        String query = ("SELECT i_clientassetid, s_name FROM t_clientasset WHERE i_status = 1 ORDER BY  s_name").intern();
        coll.add(new TalentpoolInfo(-1, " Select Asset "));
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            rs = pstmt.executeQuery();
            int refId;
            String refName;
            while (rs.next()) {
                refId = rs.getInt(1);
                refName = rs.getString(2) != null ? rs.getString(2) : "";
                coll.add(new TalentpoolInfo(refId, refName));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return coll;
    }

    public Collection getCountrys() {
        Collection coll = new LinkedList();
        coll.add(new CountryInfo(-1, " Select Location", "- Select -", "", "- Select -"));
        String xml_path = getMainPath("json_path");
        String str = readHTMLFile("country.json", xml_path);
        JSONArray arr = new JSONArray(str);
        if (arr != null) {
            int len = arr.length();
            for (int i = 0; i < len; i++) {
                JSONObject jobj = arr.optJSONObject(i);
                if (jobj != null) {
                    coll.add(new CountryInfo(jobj.optInt("id"), jobj.optString("name"), jobj.optString("nationality"), jobj.optString("ISD_code"), (jobj.optString("name") + " (+" + jobj.optString("ISD_code") + ")")));
                }
            }
        }
        return coll;
    }

    public Collection getPositionsforIndex() {
        Collection coll = new LinkedList();
        String query = ("SELECT t_position.i_positionid, t_position.s_name, t_grade.s_name, t_assettype.s_name  FROM t_position LEFT JOIN t_grade ON (t_grade.i_gradeid = t_position.i_gradeid) LEFT JOIN t_assettype ON (t_assettype.i_assettypeid = t_position.i_assettypeid) WHERE t_position.i_status = 1 ORDER BY  t_assettype.s_name, t_position.s_name, t_grade.s_name").intern();
        coll.add(new TalentpoolInfo(-1, "Select Position"));
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            rs = pstmt.executeQuery();
            int refId;
            String pname, gname, assetname;
            while (rs.next()) {
                refId = rs.getInt(1);
                pname = rs.getString(2) != null ? rs.getString(2) : "";
                gname = rs.getString(3) != null ? rs.getString(3) : "";
                assetname = rs.getString(4) != null ? rs.getString(4) : "";
                coll.add(new TalentpoolInfo(refId, assetname + " | " + pname + " | " + gname));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return coll;
    }

    public int insertAlert(int candidateId, int tabno, int type, String remarks, int userId, Connection conn) {
        int alertId = 0;
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("INSERT INTO t_alert  ");
            sb.append("( i_candidateid, i_tabno, i_type, s_remarks, i_status, i_userid, ts_regdate, ts_moddate) ");
            sb.append("VALUES (?,?,?,?,?,?,?,?)");
            String query = (sb.toString()).intern();
            sb.setLength(0);
            pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            int scc = 0;
            pstmt.setInt(++scc, candidateId);
            pstmt.setInt(++scc, tabno);
            pstmt.setInt(++scc, type);
            pstmt.setString(++scc, remarks);
            pstmt.setInt(++scc, 1);
            pstmt.setInt(++scc, userId);
            pstmt.setString(++scc, currDate1());
            pstmt.setString(++scc, currDate1());
            pstmt.executeUpdate();
            print(this, "insertAlert : " + pstmt.toString());
            rs = pstmt.getGeneratedKeys();
            while (rs.next()) {
                alertId = rs.getInt(1);
            }
        } catch (Exception exception) {
            print(this, "insertAlert :: " + exception.getMessage());
        } finally {
            close(null, pstmt, rs);
        }
        return alertId;
    }

    //For Updated by  insertAlertBy(candidateId, 1, 1, remarks, userId, conn, userName);
    public int insertAlertBy(int candidateId, int tabno, int type, String remarks, int userId, Connection conn, String updatedby) {
        int alertId = 0;
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("INSERT INTO t_alert  ");
            sb.append("( i_candidateid, i_tabno, i_type, s_remarks, i_status, i_userid, s_updatedby, ts_regdate, ts_moddate) ");
            sb.append("VALUES (?,?,?,?,?,?,?,?, ?)");
            String query = (sb.toString()).intern();
            sb.setLength(0);
            pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            int scc = 0;
            pstmt.setInt(++scc, candidateId);
            pstmt.setInt(++scc, tabno);
            pstmt.setInt(++scc, type);
            pstmt.setString(++scc, remarks);
            pstmt.setInt(++scc, 1);
            pstmt.setInt(++scc, userId);
            pstmt.setString(++scc, updatedby);
            pstmt.setString(++scc, currDate1());
            pstmt.setString(++scc, currDate1());
            pstmt.executeUpdate();
            print(this, "insertAlertBy : " + pstmt.toString());
            rs = pstmt.getGeneratedKeys();
            while (rs.next()) {
                alertId = rs.getInt(1);
            }
        } catch (Exception exception) {
            print(this, "insertAlertBy :: " + exception.getMessage());
        } finally {
            close(null, pstmt, rs);
        }
        return alertId;
    }

    public ArrayList getAlertdetailByCandidateId(int candidateId, int typeId) {
        ArrayList list = new ArrayList();        
        try 
        {
            conn = getConnection();
            String remarks = "", date = "", name = "", coursename = "", document = "";
            int alertId = 0, type = 0, tabno = 0, userId = 0,documentid = 0;  
            if(typeId == -1 || typeId == 2)
            {
                StringBuilder sb = new StringBuilder();
                sb.append("SELECT i_alertid,i_type,i_tabno,s_remarks,i_userid, DATE_FORMAT(ts_regdate, '%d-%b-%Y'), s_updatedby FROM t_alert ");
                sb.append("WHERE i_candidateid = ? AND i_status = 1 ");
                sb.append(" ORDER BY  ts_regdate DESC");
                String query = (sb.toString()).intern();
                sb.setLength(0);
                pstmt = conn.prepareStatement(query);
                pstmt.setInt(1, candidateId);
                print(this, "getAlertdetailByCandidateId :" + pstmt.toString());
                rs = pstmt.executeQuery();                              
                while (rs.next()) {
                    alertId = rs.getInt(1);
                    type = rs.getInt(2);
                    tabno = rs.getInt(3);
                    remarks = rs.getString(4) != null ? rs.getString(4) : "";
                    userId = rs.getInt(5);
                    date = rs.getString(6) != null ? rs.getString(6) : "";
                    name = rs.getString(7) != null ? rs.getString(7) : "";
                    list.add(new TalentpoolInfo(alertId, type, tabno,
                            remarks, userId, date, name, 0, "", 0, "", 1));
                }
                rs.close();
                pstmt.close();
            }
            if(typeId == -1 || typeId == 1)
            {
                StringBuilder sb2 = new StringBuilder();
                sb2.append("SELECT t_govdoc.i_govid, t_doctype.s_doc, DATE_FORMAT(t_govdoc.d_expirydate, '%d-%b-%Y') ");
                sb2.append(" FROM t_govdoc ");
                sb2.append("LEFT JOIN t_doctype ON (t_govdoc.i_doctypeid = t_doctype.i_doctypeid) ");
                sb2.append("WHERE t_govdoc.i_candidateid = ? AND t_govdoc.i_status = 1 AND t_govdoc.i_doctypeid in(2,6,9) ");
                sb2.append("AND t_govdoc.d_expirydate != '0000-00-00' ");
                sb2.append("AND t_govdoc.d_expirydate < CURRENT_DATE() ");
                sb2.append("ORDER BY t_govdoc.d_expirydate DESC ");
                String docquery = (sb2.toString()).intern();
                sb2.setLength(0);

                pstmt = conn.prepareStatement(docquery);
                pstmt.setInt(1, candidateId);
                print(this, "getAlertGovdocByCandidateId :" + pstmt.toString());
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    documentid = rs.getInt(1);
                    document = rs.getString(2) != null ? rs.getString(2) : "";
                    date = rs.getString(3) != null ? rs.getString(3) : "";
                    list.add(new TalentpoolInfo(alertId, type, tabno, remarks, userId, date,
                            name, documentid, document, 0, "", 2));
                }
                rs.close();
                pstmt.close();
            }
            if(typeId == -1 || typeId == 1)
            {
                StringBuilder sb3 = new StringBuilder();
                sb3.append("SELECT  t_trainingandcert.i_tcid,  t_coursename.s_name, DATE_FORMAT(t_trainingandcert.d_expirydate,'%d-%b-%Y') ");
                sb3.append(" FROM t_trainingandcert ");
                sb3.append("LEFT JOIN  t_coursename ON (t_coursename.i_coursenameid = t_trainingandcert.i_coursenameid) ");
                sb3.append("WHERE t_trainingandcert.i_candidateid = ? AND t_trainingandcert.d_expirydate != '0000-00-00' ");
                sb3.append("AND t_trainingandcert.d_expirydate < CURRENT_DATE() AND t_trainingandcert.i_status = 1 ");
                sb3.append("ORDER BY t_trainingandcert.d_expirydate DESC ");
                String certquery = (sb3.toString()).intern();
                sb3.setLength(0);

                pstmt = conn.prepareStatement(certquery);
                pstmt.setInt(1, candidateId);                
                int certid = 0;
                print(this, "getAlertCertByCandidateId :" + pstmt.toString());
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    certid = rs.getInt(1);
                    coursename = rs.getString(2) != null ? rs.getString(2) : "";
                    date = rs.getString(3) != null ? rs.getString(3) : "";
                    list.add(new TalentpoolInfo(alertId, type, tabno, remarks, userId,
                            date, name, documentid, document, certid, coursename, 3));
                }
                rs.close();
                pstmt.close();
            }

        } catch (Exception exception) {
            print(this, "getAlertdetailByCandidateId :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return list;
    }

    public String getAssstatusbyId(int status) {
        String stval = "";
        switch (status) {
            case 0:
                stval = "Unscheduled";
                break;
            case 1:
                stval = "Scheduled";
                break;
            case 2:
                stval = "Passed";
                break;
            case 3:
                stval = "Failed";
                break;
            default:
                break;
        }
        return stval;
    }

    public String getCompliancetatusbyId(int status) {
        String stval = "";
        switch (status) {
            case 1:
                stval = "Minimum Checked";
                break;
            case 2:
                stval = "Checked";
                break;
            default:
                break;
        }
        return stval;
    }

    public String getClientselectiontatusbyId(int sflag, int oflag) {
        String stval = "";
        if (oflag <= 0) {
            switch (sflag) 
            {
                case 2:
                    stval = "CV Generated";
                    break;
                case 3:
                    stval = "CV Sent";
                    break;
                case 4:
                    stval = "Selected";
                    break;
                case 5:
                    stval = "Rejected";
                    break;
                default:
                    break;
            }
        } else if (oflag > 0) {
            switch (oflag) {
                case 2:
                    stval = "Offer Generated";
                    break;
                case 3:
                    stval = "Offer Sent";
                    break;
                case 4:
                    stval = "Accepted";
                    break;
                case 5:
                    stval = "Declined";
                    break;
                default:
                    break;
            }
        }
        return stval;
    }

    public String getStatusById(int key) {
        String s = "";
        if (key == 1) {
            s = "Minimum Verified";
        } else if (key == 2) {
            s = "Verified";
        }
        return s;
    }

    public String getReasonbyId(String rejectionreasonids) {
        String reason = "";
        if (!rejectionreasonids.equals("")) {
            String query = ("SELECT i_rejectionreasonid , s_name FROM t_rejectionreason WHERE i_rejectionreasonid in (" + rejectionreasonids + ") AND i_status = 1 ORDER BY  i_rejectionreasonid").intern();
            try {
                conn = getConnection();
                pstmt = conn.prepareStatement(query);
                logger.info("getReasonbyId :: " + pstmt.toString());
                rs = pstmt.executeQuery();
                int refId;
                String refName;
                while (rs.next()) {
                    refId = rs.getInt(1);
                    refName = rs.getString(2) != null && !rs.getString(2).equals("") ? rs.getString(2) : "";
                    reason += refName + ", ";
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                close(conn, pstmt, rs);
            }
        }
        return reason;
    }

    // Onboarding
    public ArrayList getOnboardinghistoryByCandidateId(int candidateId) 
    {
        ArrayList list = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT t_client.s_name, t_clientasset.s_name, t_position.s_name, t_grade.s_name, DATE_FORMAT(t_shortlist.d_date,'%d-%b-%Y'), ");
        sb.append("DATE_FORMAT( t_jobpost.ts_targetmobdate, '%d-%b-%Y'), t_shortlist.i_onboard, t_client.i_clientid, t_jobpost.i_clientassetid, ");
        sb.append("t_shortlistmob.i_shortlistid, t_userlogin.s_name ");
        sb.append("FROM t_shortlistmob ");
        sb.append("LEFT JOIN t_shortlist ON (t_shortlist.i_shortlistid = t_shortlistmob.i_shortlistid) ");
        sb.append("LEFT JOIN t_jobpost ON (t_shortlist.i_jobpostid = t_jobpost.i_jobpostid) ");
        sb.append("LEFT JOIN t_candidate ON (t_candidate.i_candidateid = t_shortlist.i_candidateid) ");
        sb.append("LEFT JOIN t_client ON (t_client.i_clientid = t_jobpost.i_clientid) ");
        sb.append("LEFT JOIN t_clientasset ON (t_clientasset.i_clientassetid = t_jobpost.i_clientassetid) ");
        sb.append("LEFT JOIN t_position ON (t_position.i_positionid = t_jobpost.i_positionid) ");
        sb.append("LEFT JOIN t_grade ON (t_grade.i_gradeid = t_jobpost.i_gradeid) ");
        sb.append("LEFT JOIN t_userlogin ON (t_userlogin.i_userid = t_shortlist. i_userid) ");
        sb.append("WHERE t_shortlist.i_candidateid = ? ORDER BY t_shortlist.d_date DESC");
        String query = (sb.toString()).intern();
        sb.setLength(0);
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, candidateId);
            logger.info("getOnboardinghistoryByCandidateId :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String clientName, clientassetName, positionName, gradeName, arrivalDate, mobdate, username;
            int onboardStatus, shortlistId;
            int clientId, clientassetId;
            while (rs.next()) {
                clientName = rs.getString(1) != null ? rs.getString(1) : "";
                clientassetName = rs.getString(2) != null ? rs.getString(2) : "";
                positionName = rs.getString(3) != null ? rs.getString(3) : "";
                gradeName = rs.getString(4) != null ? rs.getString(4) : "";
                arrivalDate = rs.getString(5) != null ? rs.getString(5) : "";
                mobdate = rs.getString(6) != null ? rs.getString(6) : "";
                onboardStatus = rs.getInt(7);
                clientId = rs.getInt(8);
                clientassetId = rs.getInt(9);
                shortlistId = rs.getInt(10);
                username = rs.getString(11) != null ? rs.getString(11) : "";
                if (!positionName.equals("")) {
                    if (!gradeName.equals("")) {
                        positionName += " | " + gradeName;
                    }
                }
                if (!clientName.equals("")) {
                    if (!clientassetName.equals("")) {
                        clientName += " | " + clientassetName;
                    }
                }
                list.add(new TalentpoolInfo(clientName, clientassetName, positionName, gradeName, arrivalDate, 
                        mobdate, onboardStatus, candidateId, clientId, clientassetId, shortlistId, username));
            }
            rs.close();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return list;
    }

    public ArrayList getMobilizationhistoryByCandidateId(int candidateId) {
        ArrayList list = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT t_batch.i_type, t_batch.i_crewrotationid, DATE_FORMAT(t_batch.ts_regdate, '%d %b %Y' ), ");
        sb.append("t_client.s_name, t_clientasset.s_name, t_position.s_name, t_grade.s_name ");
        sb.append("FROM t_batch ");
        sb.append("LEFT JOIN t_crewrotation ON (t_batch.i_crewrotationid = t_crewrotation.i_crewrotationid ) ");
        sb.append("LEFT JOIN  t_candidate  ON (t_crewrotation.i_candidateid = t_candidate.i_candidateid) ");
        sb.append("LEFT JOIN t_position ON (t_position.i_positionid = t_candidate.i_positionid ) ");
        sb.append("LEFT JOIN t_grade ON (t_grade.i_gradeid = t_position.i_gradeid ) ");
        sb.append("LEFT JOIN  t_client ON (t_client.i_clientid = t_crewrotation.i_clientid ) ");
        sb.append("LEFT JOIN  t_clientasset ON (t_clientasset.i_clientassetid = t_crewrotation.i_clientassetid) ");
        sb.append("WHERE t_candidate.i_candidateid = ? ORDER BY t_batch.ts_regdate DESC ");
        String query = (sb.toString()).intern();
        sb.setLength(0);
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, candidateId);
            logger.info("getMobilizationhistoryByCandidateId :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String clientName, clientassetName, positionName, gradeName, mobdate;
            int type, crewrotationId;
            while (rs.next()) {
                type = rs.getInt(1);
                crewrotationId = rs.getInt(2);
                mobdate = rs.getString(3) != null ? rs.getString(3) : "";
                clientName = rs.getString(4) != null ? rs.getString(4) : "";
                clientassetName = rs.getString(5) != null ? rs.getString(5) : "";
                positionName = rs.getString(6) != null ? rs.getString(6) : "";
                gradeName = rs.getString(7) != null ? rs.getString(7) : "";
                if (!positionName.equals("")) {
                    if (!gradeName.equals("")) {
                        positionName += " | " + gradeName;
                    }
                }
                if (!clientName.equals("")) {
                    if (!clientassetName.equals("")) {
                        clientName += " | " + clientassetName;
                    }
                }
                list.add(new TalentpoolInfo(type, crewrotationId, mobdate, clientName, positionName, candidateId));
            }
            rs.close();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return list;
    }

    // Rotation
    public ArrayList getRotationhistoryByCandidateId(int candidateId) {
        ArrayList list = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT t_client.s_name, t_clientasset.s_name, t_position.s_name, t_grade.s_name, DATE_FORMAT( t_cractivity.ts_fromdate,'%d-%b-%Y'), ");
        sb.append(" DATE_FORMAT( t_cractivity.ts_todate, '%d-%b-%Y'), t_signonoff.i_subtype,t_crewrotation.i_clientid, t_crewrotation.i_clientassetid , t_crewrotation.i_crewrotationid  FROM t_cractivity  ");
        sb.append("LEFT JOIN t_signonoff ON (t_signonoff.i_signonoffid = t_cractivity.i_signoffid  ) ");
        sb.append("LEFT JOIN t_crewrotation ON (t_crewrotation.i_crewrotationid = t_cractivity.i_crewrotationid  ) ");
        sb.append("LEFT JOIN t_client ON (t_client.i_clientid = t_crewrotation.i_clientid ) ");
        sb.append("LEFT JOIN t_clientasset ON (t_clientasset.i_clientassetid = t_crewrotation.i_clientassetid ) ");
        sb.append("LEFT JOIN t_candidate ON (t_candidate.i_candidateid = t_crewrotation.i_candidateid ) ");
        sb.append("LEFT JOIN t_jobpost ON (t_crewrotation.i_jobpostid = t_jobpost.i_jobpostid ) ");
        sb.append("LEFT JOIN t_position ON (t_position.i_positionid = t_cractivity.i_positionid ) ");
        sb.append("LEFT JOIN t_grade ON (t_grade.i_gradeid = t_position.i_gradeid ) ");
        sb.append("WHERE t_crewrotation.i_candidateid = ? AND t_cractivity.i_activityid = 6 AND t_cractivity.i_signoffid > 0  ");
        String query = (sb.toString()).intern();
        sb.setLength(0);
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, candidateId);
            logger.info("getRotationhistoryByCandidateId :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String clientName, clientassetName, positionName, gradeName, signon, signoff, rotationStatusValue;
            int rotationStatus, clientId, clientassetId, crewrotationId;
            while (rs.next()) {
                clientName = rs.getString(1) != null ? rs.getString(1) : "";
                clientassetName = rs.getString(2) != null ? rs.getString(2) : "";
                positionName = rs.getString(3) != null ? rs.getString(3) : "";
                gradeName = rs.getString(4) != null ? rs.getString(4) : "";
                signon = rs.getString(5) != null ? rs.getString(5) : "";
                signoff = rs.getString(6) != null ? rs.getString(6) : "";
                rotationStatus = rs.getInt(7);
                clientId = rs.getInt(8);
                clientassetId = rs.getInt(9);
                crewrotationId = rs.getInt(10);
                if (!positionName.equals("")) {
                    if (!gradeName.equals("")) {
                        positionName += " | " + gradeName;
                    }
                }
                if (!clientName.equals("")) {
                    if (!clientassetName.equals("")) {
                        clientName += " | " + clientassetName;
                    }
                }
                rotationStatusValue = getSignonoffStatusbyId(rotationStatus);

                list.add(new TalentpoolInfo(clientName, clientassetName, positionName, gradeName, signon, signoff, rotationStatus, candidateId, rotationStatusValue, clientId, clientassetId, crewrotationId));
            }
            rs.close();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return list;
    }

    public String getSignonoffStatusbyId(int rotationStatus) {
        String stval = "";
        if (rotationStatus == 1) {
            stval = "Signed Off";
        } else if (rotationStatus == 2) {
            stval = "Early Signed Off";
        } else if (rotationStatus == 3) {
            stval = "Extended Signed Off";
        }
        return stval;
    }

    public Collection getPostions(int clientassetId) {
        Collection coll = new LinkedList();
        if (clientassetId > 0) {
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT t_position.i_positionid, t_position.s_name, t_grade.s_name FROM t_clientassetposition ");
            sb.append(" LEFT JOIN t_position ON (t_position.i_positionid = t_clientassetposition.i_positionid) ");
            sb.append(" LEFT JOIN t_grade ON (t_grade.i_gradeid = t_position.i_gradeid) ");
            sb.append(" WHERE t_clientassetposition.i_clientassetid = ? ORDER BY t_position.s_name, t_grade.s_name");
            String query = sb.toString();
            sb.setLength(0);
            coll.add(new TalentpoolInfo(0, " Select Position | Rank "));
            try {
                conn = getConnection();
                pstmt = conn.prepareStatement(query);
                pstmt.setInt(1, clientassetId);
                logger.info("getPostions :: " + pstmt.toString());
                rs = pstmt.executeQuery();
                int ddlValue;
                String ddlLabel = "", grade, position, positionValue = "";
                while (rs.next()) {
                    ddlValue = rs.getInt(1);
                    position = rs.getString(2) != null ? rs.getString(2) : "";
                    grade = rs.getString(3) != null ? rs.getString(3) : "";
                    ddlLabel = position;
                    if (!grade.equals("")) {
                        ddlLabel += " | " + grade;
                    }
                    coll.add(new TalentpoolInfo(ddlValue, ddlLabel));
                    positionValue = "";
                    ddlLabel = "";
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                close(conn, pstmt, rs);
            }
        } else {
            coll.add(new TalentpoolInfo(0, " Select Position | Rank "));
        }
        return coll;
    }
    
    public Collection getPostions2(int clientassetId, int positionId) {
        Collection coll = new LinkedList();
        if (clientassetId > 0) {
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT t_position.i_positionid, t_position.s_name, t_grade.s_name FROM t_clientassetposition ");
            sb.append(" LEFT JOIN t_position ON (t_position.i_positionid = t_clientassetposition.i_positionid) ");
            sb.append(" LEFT JOIN t_grade ON (t_grade.i_gradeid = t_position.i_gradeid) ");
            sb.append(" WHERE t_clientassetposition.i_clientassetid = ? AND t_position.i_positionid != ? ORDER BY t_position.s_name, t_grade.s_name");
            String query = sb.toString();
            sb.setLength(0);
            coll.add(new TalentpoolInfo(0, " Select Position | Rank "));
            try {
                conn = getConnection();
                pstmt = conn.prepareStatement(query);
                pstmt.setInt(1, clientassetId);
                pstmt.setInt(2, positionId);
                logger.info("getPostions2 :: " + pstmt.toString());
                rs = pstmt.executeQuery();
                int ddlValue;
                String ddlLabel = "", grade, position, positionValue = "";
                while (rs.next()) {
                    ddlValue = rs.getInt(1);
                    position = rs.getString(2) != null ? rs.getString(2) : "";
                    grade = rs.getString(3) != null ? rs.getString(3) : "";
                    ddlLabel = position;
                    if (!grade.equals("")) {
                        ddlLabel += " | " + grade;
                    }
                    coll.add(new TalentpoolInfo(ddlValue, ddlLabel));
                    positionValue = "";
                    ddlLabel = "";
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                close(conn, pstmt, rs);
            }
        } else {
            coll.add(new TalentpoolInfo(0, " Select Position | Rank "));
        }
        return coll;
    }

    public int createTransferModal(TalentpoolInfo info) {
        int transferId = 0;
        if (info != null) {
            String torate1 = "0", torate2 = "0", torate3 = "0", top2rate1 = "0", top2rate2 = "0", top2rate3 = "0";
            torate1 = info.getTorate1() != null && !info.getTorate1().equals("") ? info.getTorate1() : "0";
            torate2 = info.getTorate2() != null && !info.getTorate2().equals("") ? info.getTorate2() : "0";
            torate3 = info.getTorate3() != null && !info.getTorate3().equals("") ? info.getTorate3() : "0";
            
            top2rate1 = info.getTop2rate1() != null && !info.getTop2rate1().equals("") ? info.getTop2rate1() : "0";
            top2rate2 = info.getTop2rate2() != null && !info.getTop2rate2().equals("") ? info.getTop2rate2() : "0";
            top2rate3 = info.getTop2rate3() != null && !info.getTop2rate3().equals("") ? info.getTop2rate3() : "0";
            try {
                StringBuilder sb = new StringBuilder();
                sb.append("INSERT INTO t_transfer ");
                sb.append("(i_fromclient, i_fromasset, i_toclientid, i_toasset, i_topositionid, s_joiningdate, s_enddate, ");//7
                sb.append("s_joiningdate2, s_enddate2, i_tocurrencyid, d_torate1, d_torate2, ");//5
                sb.append("d_torate3, s_remark, i_candidateid, i_userid, i_type, ts_regdate, ts_moddate, ");//7
                sb.append("i_topositionid2, i_tocurrencyid2, d_top2rate1, d_top2rate2, d_top2rate3) ");//5
                sb.append("VALUES (?, ?, ?, ?, ?,   ?, ?, ?, ?, ?,   ?, ?, ?, ?, ?,   ?, ?, ?, ?, ?,   ?, ?,?, ?)");//22
                String query = (sb.toString()).intern();
                sb.setLength(0);

                conn = getConnection();
                pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                int scc = 0;
                pstmt.setInt(++scc, (info.getClientId()));
                pstmt.setInt(++scc, (info.getClientassetId()));
                pstmt.setInt(++scc, (info.getToClientId()));
                pstmt.setInt(++scc, (info.getToAssetId()));
                pstmt.setInt(++scc, (info.getToPositionId()));
                pstmt.setString(++scc, changeDate1(info.getJoiningDate1()));
                pstmt.setString(++scc, changeDate1(info.getEndDate1()));
                pstmt.setString(++scc, changeDate1(info.getJoiningDate2()));
                pstmt.setString(++scc, changeDate1(info.getEndDate2()));
                pstmt.setInt(++scc, (info.getToCurrencyId()));
                pstmt.setString(++scc, (torate1));
                pstmt.setString(++scc, (torate2));
                pstmt.setString(++scc, (torate3));
                pstmt.setString(++scc, (info.getTransferRemark()));
                pstmt.setInt(++scc, info.getCandidateId());
                pstmt.setInt(++scc, info.getUserId());
                pstmt.setInt(++scc, 1);
                pstmt.setString(++scc, currDate1());
                pstmt.setString(++scc, currDate1());
                pstmt.setInt(++scc, info.getToPositionId2());
                pstmt.setInt(++scc, info.getToCurrencyId2());
                pstmt.setString(++scc, (top2rate1));
                pstmt.setString(++scc, (top2rate2));
                pstmt.setString(++scc, (top2rate3));
                print(this, "createTransferModal :: " + pstmt.toString());
                pstmt.executeUpdate();
                rs = pstmt.getGeneratedKeys();
                while (rs.next()) {
                    transferId = rs.getInt(1);
                }
            } catch (Exception exception) {
                print(this, "createTransferModal :: " + exception.getMessage());
            } finally {
                close(conn, pstmt, rs);
            }
        }
        return transferId;
    }

    public int updateTerminateModal(TalentpoolInfo info) {
        int cc = 0;
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("INSERT INTO t_transfer ");
            sb.append("(s_joiningdate, s_enddate, s_reason, s_remark, s_filename, i_candidateid, i_userid, i_type, ts_regdate, ts_moddate) ");
            sb.append("VALUES (?, ?, ?, ?, ?, ?,   ?,?,?,?)");
            String query = (sb.toString()).intern();
            sb.setLength(0);

            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, changeDate1(info.getJoiningDate()));
            pstmt.setString(2, changeDate1(info.getEndDate()));
            if(info.getReason() != null){
                pstmt.setString(3, info.getReason());
            }else{
                pstmt.setString(3, "Transfer");
            }
            pstmt.setString(4, (info.getTerminateRemark()));
            pstmt.setString(5, (info.getFilename()));
            pstmt.setInt(6, info.getCandidateId());
            pstmt.setInt(7, info.getUserId());
            pstmt.setInt(8, 2);
            pstmt.setString(9, currDate1());
            pstmt.setString(10, currDate1());
            print(this, "createTerminateModal :: " + pstmt.toString());
            pstmt.executeUpdate();

            sb.append("UPDATE t_candidate SET ");
            sb.append("i_clientid = 0, ");
            sb.append("i_clientassetid = 0, ");
            if (info.getReason() != null && info.getReason().equals("Deceased")) {
                sb.append("i_status = 4, ");
            }
            sb.append("i_userid = ?, ");
            sb.append(" ts_moddate = NOW() ");
            sb.append("WHERE i_candidateid = ? ");
            query = (sb.toString()).intern();
            sb.setLength(0);

            pstmt = conn.prepareStatement(query);
            int scc = 0;
            pstmt.setInt(++scc, info.getUserId());
            pstmt.setInt(++scc, info.getCandidateId());
            print(this, "updateTerminateModal :: " + pstmt.toString());
            cc = pstmt.executeUpdate();

            sb.append("UPDATE t_crewrotation SET ");
            sb.append("i_active = 2, ");
            sb.append("i_userid = ?, ");
            sb.append(" ts_moddate = NOW() ");
            sb.append("WHERE i_candidateid = ? ");
            sb.append("AND i_active = 1");
            query = (sb.toString()).intern();
            sb.setLength(0);

            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, info.getUserId());
            pstmt.setInt(2, info.getCandidateId());
            print(this, "updateTerminate :: " + pstmt.toString());
            cc = pstmt.executeUpdate();
        } catch (Exception exception) {
            print(this, "updateTerminateModal :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return cc;
    }

    public int deldocumentfiles(int clientassetpicId) {
        int cc = 0;
        String query = "SELECT s_name FROM t_documentfiles WHERE i_fileid = ?";
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, clientassetpicId);
            logger.info("deldocumentfiles :: " + pstmt.toString());
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
            query = "DELETE FROM t_documentfiles WHERE i_fileid = ?";
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

    public ArrayList getdocumentfiles(int clientassetId) {
        ArrayList list = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT t_documentfiles.i_fileid, t_documentfiles.s_name, DATE_FORMAT(t_documentfiles.ts_regdate, '%d-%b-%Y %H:%i'), ");
        sb.append("t_userlogin.s_name ");
        sb.append("FROM t_documentfiles LEFT JOIN t_userlogin ON (t_userlogin.i_userid = t_documentfiles.i_userid) ");
        sb.append("WHERE t_documentfiles.i_govid = ? ");
        sb.append(" ORDER BY  t_documentfiles.i_fileid DESC");
        String query = (sb.toString()).intern();
        sb.setLength(0);
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, clientassetId);
            logger.info("getPics :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String filename, date, name;
            int fileId;
            while (rs.next()) {
                fileId = rs.getInt(1);
                filename = rs.getString(2) != null ? rs.getString(2) : "";
                date = rs.getString(3) != null ? rs.getString(3) : "";
                name = rs.getString(4) != null ? rs.getString(4) : "";
                list.add(new TalentpoolInfo(fileId, filename, date, name,"",0));
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return list;
    }

    public int createdocumentfiles(Connection conn, int candidateId, String filename, int userId) {
        int cc = 0;
        try {
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
            //print(this,"createPic :: " + pstmt.toString());
            cc = pstmt.executeUpdate();
        } catch (Exception exception) {
            print(this, "createPic :: " + exception.getMessage());
        } finally {
            close(null, pstmt, rs);
        }
        return cc;
    }

    public ArrayList getNotificationhistory(int candidateId) {
        ArrayList list = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT DATE_FORMAT(ts_regdate,'%d-%b-%Y'), s_doctype, s_sendby, s_filename FROM t_maillog ");
        sb.append("WHERE i_type = 11 AND i_candidateid = ? ORDER BY ts_regdate DESC ");
        String query = (sb.toString()).intern();
        sb.setLength(0);
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, candidateId);
            logger.info("getNotificationhistory :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String date, documentname, username, filename;
            while (rs.next()) {
                date = rs.getString(1) != null ? rs.getString(1) : "";
                documentname = rs.getString(2) != null ? rs.getString(2) : "";
                username = rs.getString(3) != null ? rs.getString(3) : "";
                filename = rs.getString(4) != null ? rs.getString(4) : "";
                list.add(new TalentpoolInfo(date, documentname, username, filename, candidateId));
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return list;
    }

    public ArrayList getTransferTerminationhistory(int candidateId) {
        ArrayList list = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT t_transfer.i_type, DATE_FORMAT(t_transfer.ts_moddate, '%d %b %Y %H:%i'), t_userlogin.s_name, t_transfer.s_remark, ");
        sb.append("t_clientasset.s_name, t_transfer.s_reason, t_transfer.s_filename, t_client.s_name, t_position.s_name, t_grade.s_name, t_currency.s_name, ");
        sb.append("d_torate1, d_torate2, d_torate3, p2.s_name, g2.s_name, c2.s_name,  ");
        sb.append("d_top2rate1, d_top2rate2, d_top2rate3  ");
        sb.append("FROM t_transfer ");
        sb.append("LEFT JOIN t_userlogin ON (t_userlogin.i_userid = t_transfer.i_userid) ");
        sb.append("LEFT JOIN t_clientasset ON (t_clientasset.i_clientassetid = t_transfer.i_toasset) ");
        sb.append("LEFT JOIN t_client ON (t_client.i_clientid = t_clientasset.i_clientid) ");
        sb.append("LEFT JOIN t_position ON (t_position.i_positionid = t_transfer.i_topositionid) ");
        sb.append("LEFT JOIN t_grade ON (t_grade.i_gradeid = t_position.i_gradeid) ");
        sb.append("LEFT JOIN t_currency ON (t_currency.i_currencyid = t_transfer.i_tocurrencyid) ");
        sb.append("LEFT JOIN t_position AS p2 ON (p2.i_positionid = t_transfer.i_topositionid2) ");
        sb.append("LEFT JOIN t_grade AS g2 ON (g2.i_gradeid = p2.i_gradeid) ");
        sb.append("LEFT JOIN t_currency AS c2 ON (c2.i_currencyid = t_transfer.i_tocurrencyid2) ");
        sb.append("WHERE t_transfer.i_candidateid =? ORDER BY t_transfer.ts_moddate DESC, t_transfer.i_type ");
        String query = (sb.toString()).intern();
        sb.setLength(0);
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, candidateId);
            logger.info("getTransferTerminationhistory :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String date, assettname, username, remark, reason, filename, clientname, 
                    positionname, grade, currency, torate1, torate2, torate3,
                    position2, grade2, currency2, top2rate1, top2rate2, top2rate3;
            int type;
            int scc = 0;
            while (rs.next()) 
            {
                scc = 0;
                type = rs.getInt(++scc);
                date = rs.getString(++scc) != null ? rs.getString(scc) : "";
                username = rs.getString(++scc) != null ? rs.getString(scc) : "";
                remark = rs.getString(++scc) != null ? rs.getString(scc) : "";
                assettname = rs.getString(++scc) != null ? rs.getString(scc) : "";
                reason = rs.getString(++scc) != null ? rs.getString(scc) : "";
                filename = rs.getString(++scc) != null ? rs.getString(scc) : "";
                clientname = rs.getString(++scc) != null ? rs.getString(scc) : "";
                positionname = rs.getString(++scc) != null ? rs.getString(scc) : "";
                grade = rs.getString(++scc) != null ? rs.getString(scc) : "";
                if (!positionname.equals("")) {
                    if (!grade.equals("")) {
                        positionname += " | " + grade;
                    }
                }
                currency = rs.getString(++scc) != null ? rs.getString(scc) : "";
                torate1 = rs.getString(++scc) != null ? rs.getString(scc) : "";
                torate2 = rs.getString(++scc) != null ? rs.getString(scc) : "";
                torate3 = rs.getString(++scc) != null ? rs.getString(scc) : "";
                position2 = rs.getString(++scc) != null ? rs.getString(scc) : "";
                grade2 = rs.getString(++scc) != null ? rs.getString(scc) : "";
                if (!position2.equals("")) {
                    if (!grade2.equals("")) {
                        position2 += " | " + grade2;
                    }
                }
                currency2 = rs.getString(++scc) != null ? rs.getString(scc) : "";
                top2rate1 = rs.getString(++scc) != null ? rs.getString(scc) : "";
                top2rate2 = rs.getString(++scc) != null ? rs.getString(scc) : "";
                top2rate3 = rs.getString(++scc) != null ? rs.getString(scc) : "";
                
                if(positionname != null && !positionname.equals(""))
                    list.add(new TalentpoolInfo(type, date, username, remark, assettname, reason, filename, clientname, positionname, currency, torate1, torate2, torate3));
                
                if(position2 != null && !position2.equals(""))
                    list.add(new TalentpoolInfo(type, date, username, remark, assettname, reason, filename, clientname, position2, currency2, top2rate1, top2rate2, top2rate3));
                else  
                    list.add(new TalentpoolInfo(type, date, username, remark, assettname, reason, filename, clientname, "Not Available", currency2, top2rate1, top2rate2, top2rate3));
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return list;
    }
    
    // Wellness feedback
    public ArrayList getWellnessFeedbackhistory(int candidateId, int statusId, int next, int count) {
        ArrayList list = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT t_client.s_name, t_clientasset.s_name, t_position.s_name, t_grade.s_name, t_survey.i_status, t_subcategorywf.s_name, DATE_FORMAT(t_survey.ts_regdate, '%d-%b-%Y'), ");
        sb.append("DATE_FORMAT(t_survey.d_completedon, '%d-%b-%Y'), NOW() >  ts_expirydate, t_survey.i_surveyid, t_categorywf.s_name ");
        sb.append("FROM t_survey ");
        sb.append("LEFT JOIN t_crewrotation ON (t_crewrotation.i_crewrotationid = t_survey.i_crewrotationid) ");
        sb.append("LEFT JOIN t_clientasset ON (t_clientasset.i_clientassetid = t_survey.i_clientassetid) ");
        sb.append("LEFT JOIN t_client ON (t_client.i_clientid = t_clientasset.i_clientid) ");
        sb.append("LEFT JOIN t_position ON (t_position.i_positionid = t_survey.i_positionid) ");
        sb.append("LEFT JOIN t_grade ON (t_grade.i_gradeid = t_position.i_gradeid) ");
        sb.append("LEFT JOIN t_subcategorywf ON (t_subcategorywf.i_subcategorywfid = t_survey.i_subcategorywfid) ");
        sb.append("LEFT JOIN t_categorywf ON (t_categorywf.i_categorywfid = t_survey.i_subcategorywfid) ");
        sb.append("WHERE t_crewrotation.i_candidateid = ?  ");
        if (statusId > 0) {
            if (statusId == 1) {
                sb.append(" AND t_survey.i_status = 1 AND NOW() < t_survey.ts_expirydate ");
            } else if (statusId == 2) {
                sb.append("AND t_survey.i_status = 2 ");
            } else if (statusId == 3) {
                sb.append("AND t_survey.i_status = 1 AND NOW() > t_survey.ts_expirydate ");
            }
        }
        sb.append("ORDER BY  t_survey.ts_regdate DESC");
        if (count > 0) {
            sb.append(" LIMIT ").append(next * count).append(", ").append(count);
        }
        String query = (sb.toString()).intern();
        sb.setLength(0);
        sb.append("SELECT COUNT(1) FROM t_survey ");
        sb.append("LEFT JOIN t_crewrotation ON (t_crewrotation.i_crewrotationid = t_survey.i_crewrotationid) ");
        sb.append("LEFT JOIN t_clientasset ON (t_clientasset.i_clientassetid = t_survey.i_clientassetid) ");
        sb.append("LEFT JOIN t_client ON (t_client.i_clientid = t_clientasset.i_clientid) ");
        sb.append("LEFT JOIN t_position ON (t_position.i_positionid = t_survey.i_positionid) ");
        sb.append("LEFT JOIN t_grade ON (t_grade.i_gradeid = t_position.i_gradeid) ");
        sb.append("LEFT JOIN t_subcategorywf ON (t_subcategorywf.i_subcategorywfid = t_survey.i_subcategorywfid) ");
        sb.append("LEFT JOIN t_categorywf ON (t_categorywf.i_categorywfid = t_survey.i_subcategorywfid) ");
        sb.append("WHERE t_crewrotation.i_candidateid = ?  ");
        if (statusId > 0) {
            if (statusId == 1) {
                sb.append(" AND t_survey.i_status = 1 AND NOW() < t_survey.ts_expirydate ");
            } else if (statusId == 2) {
                sb.append("AND t_survey.i_status = 2 ");
            } else if (statusId == 3) {
                sb.append("AND t_survey.i_status = 1 AND NOW() > t_survey.ts_expirydate ");
            }
        }
        String countquery = (sb.toString()).intern();
        sb.setLength(0);
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, candidateId);
            logger.info("getWellnessFeedbackhistory :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String clientName, clientAsset, position, gradename, senton, completedon = "", status = "",
                    feedback, categoryName;
            int stval, expiryflag, surveyId;
            while (rs.next()) {
                clientName = rs.getString(1) != null ? rs.getString(1) : "";
                clientAsset = rs.getString(2) != null ? rs.getString(2) : "";
                position = rs.getString(3) != null ? rs.getString(3) : "";
                gradename = rs.getString(4) != null ? rs.getString(4) : "";
                stval = rs.getInt(5);
                feedback = rs.getString(6) != null ? rs.getString(6) : "";
                senton = rs.getString(7) != null ? rs.getString(7) : "";
                completedon = rs.getString(8) != null ? rs.getString(8) : "";
                expiryflag = rs.getInt(9);
                surveyId = rs.getInt(10);
                categoryName = rs.getString(11) != null ? rs.getString(11) : "";
                if (stval == 2) {
                    status = completedon;
                } else if (stval == 1) {
                    if (expiryflag > 0) {
                        status = "Expired";
                    } else {
                        status = "Pending";
                    }
                }
                if (!position.equals("")) {
                    if (!gradename.equals("")) {
                        position += " - " + gradename;
                    }
                }
                if (!clientName.equals("")) {
                    if (!clientAsset.equals("")) {
                        clientName += " - " + clientAsset;
                    }
                }
                if (!feedback.equals("")) {
                    if (!categoryName.equals("")) {
                        feedback += " - " + categoryName;
                    }
                }
                list.add(new TalentpoolInfo(candidateId, clientName, clientAsset, position, gradename, status, feedback, senton, categoryName,
                        expiryflag, surveyId));
            }
            rs.close();

            pstmt = conn.prepareStatement(countquery);
            pstmt.setInt(1, candidateId);
            print(this, "getWellnessFeedbackhistory :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                candidateId = rs.getInt(1);
                list.add(new TalentpoolInfo(candidateId, "", "", "", "", "", "", "", "", 0, 0));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return list;
    }

    public ArrayList getFeedbackhistoryExcel(int candidateId, int statusId) {
        ArrayList list = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT t_client.s_name, t_clientasset.s_name, t_position.s_name, t_grade.s_name, t_survey.i_status, t_subcategorywf.s_name, DATE_FORMAT(t_survey.ts_regdate, '%d-%b-%Y'), ");
        sb.append("DATE_FORMAT(t_survey.d_completedon, '%d-%b-%Y'), NOW() >  ts_expirydate, t_survey.i_surveyid, t_categorywf.s_name ");
        sb.append("FROM t_survey ");
        sb.append("LEFT JOIN t_crewrotation ON (t_crewrotation.i_crewrotationid = t_survey.i_crewrotationid) ");
        sb.append("LEFT JOIN t_clientasset ON (t_clientasset.i_clientassetid = t_survey.i_clientassetid) ");
        sb.append("LEFT JOIN t_client ON (t_client.i_clientid = t_clientasset.i_clientid) ");
        sb.append("LEFT JOIN t_position ON (t_position.i_positionid = t_survey.i_positionid) ");
        sb.append("LEFT JOIN t_grade ON (t_grade.i_gradeid = t_position.i_gradeid) ");
        sb.append("LEFT JOIN t_subcategorywf ON (t_subcategorywf.i_subcategorywfid = t_survey.i_subcategorywfid) ");
        sb.append("LEFT JOIN t_categorywf ON (t_categorywf.i_categorywfid = t_survey.i_subcategorywfid) ");
        sb.append("WHERE t_crewrotation.i_candidateid = ?  ");
        if (statusId > 0) {
            if (statusId == 1) {
                sb.append(" AND t_survey.i_status = 1 AND NOW() < t_survey.ts_expirydate ");
            } else if (statusId == 2) {
                sb.append("AND t_survey.i_status = 2 ");
            } else if (statusId == 3) {
                sb.append("AND t_survey.i_status = 1 AND NOW() > t_survey.ts_expirydate ");
            }
        }
        sb.append("ORDER BY  t_survey.ts_regdate DESC");

        String query = (sb.toString()).intern();
        sb.setLength(0);
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, candidateId);
            logger.info("getFeedbackhistoryExcel :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String clientName, clientAsset, position, gradename, senton, completedon = "", status = "",
                    feedback, categoryName;
            int stval, expiryflag, surveyId;
            while (rs.next()) {
                clientName = rs.getString(1) != null ? rs.getString(1) : "";
                clientAsset = rs.getString(2) != null ? rs.getString(2) : "";
                position = rs.getString(3) != null ? rs.getString(3) : "";
                gradename = rs.getString(4) != null ? rs.getString(4) : "";
                stval = rs.getInt(5);
                feedback = rs.getString(6) != null ? rs.getString(6) : "";
                senton = rs.getString(7) != null ? rs.getString(7) : "";
                completedon = rs.getString(8) != null ? rs.getString(8) : "";
                expiryflag = rs.getInt(9);
                surveyId = rs.getInt(10);
                categoryName = rs.getString(11) != null ? rs.getString(11) : "";
                if (stval == 2) {
                    status = completedon;
                } else if (stval == 1) {
                    if (expiryflag > 0) {
                        status = "Expired";
                    } else {
                        status = "Pending";
                    }
                }
                if (!position.equals("")) {
                    if (!gradename.equals("")) {
                        position += " - " + gradename;
                    }
                }
                if (!clientName.equals("")) {
                    if (!clientAsset.equals("")) {
                        clientName += " - " + clientAsset;
                    }
                }
                if (!feedback.equals("")) {
                    if (!categoryName.equals("")) {
                        feedback += " - " + categoryName;
                    }
                }
                list.add(new TalentpoolInfo(candidateId, clientName, clientAsset, position, gradename, status, feedback, senton, categoryName,
                        expiryflag, surveyId));
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return list;
    }

    //----------
    public TalentpoolInfo getSurveyInfo(int surveyIdTop) {
        TalentpoolInfo info = null;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT s.i_surveyid, s.i_status, DATE_FORMAT(s.ts_regdate, '%d-%b-%Y'), ");
        sb.append("DATE_FORMAT(s.d_completedon, '%d-%b-%Y'), swf.s_name, s.i_clientassetid, s.i_subcategorywfid, t_categorywf.s_name ");
        sb.append("FROM t_survey AS s ");
        sb.append("LEFT JOIN t_subcategorywf AS swf ON (swf.i_subcategorywfid = s.i_subcategorywfid) ");
        sb.append("LEFT JOIN t_categorywf ON (t_categorywf.i_categorywfid = t_categorywf.i_categorywfid) ");
        sb.append("WHERE s.i_surveyid = ?");
        String query = sb.toString();
        sb.setLength(0);
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, surveyIdTop);
            logger.info(" getSurveyInfo:: " + pstmt.toString());
            rs = pstmt.executeQuery();
            int surveyId, status, clientassetId, subcategorywfId;
            String submissionDate, filleddate, feedback, categoryName;
            while (rs.next()) {
                surveyId = rs.getInt(1);
                status = rs.getInt(2);
                submissionDate = rs.getString(3) != null ? rs.getString(3) : "";
                filleddate = rs.getString(4) != null ? rs.getString(4) : "";
                feedback = rs.getString(5) != null ? rs.getString(5) : "";
                clientassetId = rs.getInt(6);
                subcategorywfId = rs.getInt(7);
                categoryName = rs.getString(8) != null ? rs.getString(8) : "";
                info = new TalentpoolInfo(surveyId, clientassetId, status, submissionDate, filleddate, feedback, subcategorywfId, categoryName);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return info;
    }

    public ArrayList getQuestionList(int surveyId) {
        ArrayList list = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT t_question.s_question, sd.s_answer FROM t_surveydetail AS sd ");
        sb.append(" LEFT JOIN t_question ON (t_question.i_questionid = sd.i_questionid) ");
        sb.append("WHERE sd.i_surveyid = ? ORDER BY t_question.s_question");
        String query = sb.toString();
        sb.setLength(0);
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, surveyId);
            logger.info(" getQuestionList:: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String question, answer;
            while (rs.next()) {
                question = rs.getString(1) != null ? rs.getString(1) : "";
                answer = rs.getString(2) != null ? rs.getString(2) : "";

                list.add(new TalentpoolInfo(question, answer, 0));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return list;
    }
    
    public Collection getClients(String cids, int allclient, String permission) {
        Collection coll = new LinkedList();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT i_clientid, s_name FROM t_client WHERE i_status = 1 ");
        if (allclient == 1) {
            sb.append("AND i_clientid > 0 ");
        } else if (!cids.equals("")) {
            sb.append("AND i_clientid IN (" + cids + ") ");
        } else {
            sb.append("AND i_clientid < 0 ");
        }
        sb.append("ORDER BY s_name");
        String query = (sb.toString()).intern();
        sb.setLength(0);
        coll.add(new TalentpoolInfo(-1, "Select Client"));
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            System.out.println("getClients ::" + pstmt.toString());
            rs = pstmt.executeQuery();
            int refId;
            String refName;
            while (rs.next()) {
                refId = rs.getInt(1);
                refName = rs.getString(2) != null ? rs.getString(2) : "";
                coll.add(new TalentpoolInfo(refId, refName));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return coll;
    }

    public Collection getClientAsset(int clientId, String assetids, int allclient, String permission) {
        Collection coll = new LinkedList();
        if (clientId > 0) {
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT i_clientassetid, s_name FROM t_clientasset WHERE i_clientid = ? AND i_status = 1 ");
            if (allclient == 1) {
                sb.append("AND i_clientassetid > 0 ");
            } else if (!assetids.equals("")) {
                sb.append("AND i_clientassetid IN (" + assetids + ") ");
            } else {
                sb.append("AND i_clientassetid < 0 ");
            }
            sb.append("ORDER BY s_name ");
            String query = sb.toString();
            sb.setLength(0);
            coll.add(new TalentpoolInfo(-1, " Select Asset "));
            try {
                conn = getConnection();
                pstmt = conn.prepareStatement(query);
                pstmt.setInt(1, clientId);
                logger.info("getClientAsset :: " + pstmt.toString());
                rs = pstmt.executeQuery();
                int ddlValue;
                String ddlLabel;
                while (rs.next()) {
                    ddlValue = rs.getInt(1);
                    ddlLabel = rs.getString(2) != null ? rs.getString(2) : "";
                    coll.add(new TalentpoolInfo(ddlValue, ddlLabel));
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                close(conn, pstmt, rs);
            }
        } else {
            coll.add(new TalentpoolInfo(-1, " Select Asset "));
        }
        return coll;
    }

    public ArrayList getCassessmentAssessNowhistoryByCandidateId(int candidateId) {
        ArrayList list = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT t1.i_assessnowhistoryid, t1.i_assessnowid, t_position.s_name, t_grade.s_name, a1.s_name, t1.i_radio18, ");
        sb.append("DATE_FORMAT(t1.ts_regdate, '%d-%b-%Y'), DATE_FORMAT(t1.ts_regdate, '%H:%i') ");
        sb.append("FROM t_assessnowhistory AS t1 ");
        sb.append("LEFT JOIN t_position ON (t1.i_positionid = t_position.i_positionid) ");
        sb.append("LEFT JOIN t_grade ON (t_position.i_gradeid = t_grade.i_gradeid) ");
        sb.append("LEFT JOIN t_userlogin  AS a1 ON (t1.i_userid = a1.i_userid) ");
        sb.append("WHERE t1.i_candidateid = ? ");
        sb.append("ORDER BY t1.ts_regdate DESC ");
        String query = (sb.toString()).intern();
        sb.setLength(0);
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, candidateId);
            logger.info("getCassessmentAssessNowhistoryByCandidateId :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String statusValue, positionName, gradeName, date, time, grade, username;
            int cassessnowId, cassessnowhId, status;
            while (rs.next()) {
                cassessnowhId = rs.getInt(1);
                cassessnowId = rs.getInt(2);
                positionName = rs.getString(3) != null ? rs.getString(3) : "";
                gradeName = rs.getString(4) != null ? rs.getString(4) : "";
                username = rs.getString(5) != null ? rs.getString(5) : "";
                status = rs.getInt(6);
                date = rs.getString(7) != null ? rs.getString(7) : "";
                time = rs.getString(8) != null ? rs.getString(8) : "";
                if (!positionName.equals("")) {
                    if (!gradeName.equals("")) {
                        positionName += " | " + gradeName;
                    }
                }
                statusValue = getAssessnowtatusbyId(status);
                list.add(new TalentpoolInfo(cassessnowhId, cassessnowId, candidateId, positionName, username, statusValue, date,
                        time, status));
            }
            rs.close();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return list;
    }

    public String getAssessnowtatusbyId(int status) {
        String stval = "";
        switch (status) {
            case 2:
                stval = "Approved";
                break;
            case 3:
                stval = "Rejected";
                break;
            default:
                break;
        }
        return stval;
    }

    public String getAssessnowoverallratingbyId(int status) {
        String stval = "";
        switch (status) {
            case 1:
                stval = "Poor";
                break;
            case 2:
                stval = "Satisfactory";
                break;
            case 3:
                stval = "Good";
                break;
            case 4:
                stval = "Very Good";
                break;
            case 5:
                stval = "Excellent";
                break;
            default:
                break;
        }
        return stval;
    }

    public TalentpoolInfo getAssessnowinfo(int assessnowhId) {
        TalentpoolInfo info = null;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT i_radio1, i_radio2, i_radio3, i_radio4, i_radio5 , i_radio6, i_radio7, i_radio8, i_radio9, i_radio10, i_radio11, i_radio12, i_radio13, i_radio14, ");
        sb.append(" i_radio15, i_radio16, i_radio17, i_radio18, s_cdescription, i_positionid, i_status ");
        sb.append(" FROM t_assessnowhistory ");
        sb.append("WHERE i_assessnowhistoryid = ? ");
        String query = (sb.toString()).intern();
        sb.setLength(0);
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, assessnowhId);
            logger.log(Level.INFO, "getAssessnowList :: {0}", pstmt.toString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                int radio1 = rs.getInt(1);
                int radio2 = rs.getInt(2);
                int radio3 = rs.getInt(3);
                int radio4 = rs.getInt(4);
                int radio5 = rs.getInt(5);
                int radio6 = rs.getInt(6);
                int radio7 = rs.getInt(7);
                int radio8 = rs.getInt(8);
                int radio9 = rs.getInt(9);
                int radio10 = rs.getInt(10);
                int radio11 = rs.getInt(11);
                int radio12 = rs.getInt(12);
                int radio13 = rs.getInt(13);
                int radio14 = rs.getInt(14);
                int radio15 = rs.getInt(15);
                int radio16 = rs.getInt(16);
                int radio17 = rs.getInt(17);
                int radio18 = rs.getInt(18);
                String cdescription = rs.getString(19) != null ? rs.getString(19) : "";
                int positionId = rs.getInt(20);
                int status = rs.getInt(21);
                info = new TalentpoolInfo(assessnowhId, radio1, radio2, radio3, radio4, radio5, radio6, radio7, radio8, radio9, radio10, radio11, radio12, radio13, radio14,
                        radio15, radio16, radio17, radio18, cdescription, status, positionId);
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return info;
    }

    public TalentpoolInfo getFeedbackDetail(int trackerId) {
        TalentpoolInfo info = null;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT t_pcode.s_role, DATE_FORMAT(t_tracker.d_feedbackdate, '%d %b %Y'), s_feedbackremarks ");
        sb.append("FROM t_tracker ");
        sb.append("LEFT JOIN t_fcrole ON (t_fcrole.i_fcroleid = t_tracker.i_fcroleid) ");
        sb.append("LEFT JOIN t_pcode ON (t_pcode.i_pcodeid = t_fcrole.i_pcodeid) ");
        sb.append("WHERE t_tracker.i_trackerid = ?");
        String query = sb.toString().intern();
        sb.setLength(0);
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, trackerId);
            print(this, "getFeedbackDetail :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String role, date, remarks;
            while (rs.next()) {
                role = rs.getString(1) != null ? rs.getString(1) : "";
                date = rs.getString(2) != null ? rs.getString(2) : "";
                remarks = rs.getString(3) != null ? rs.getString(3) : "";
                info = new TalentpoolInfo(role, date, remarks, trackerId);
            }
            rs.close();
        } catch (Exception exception) {
            print(this, "getFeedbackDetail :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return info;
    }

    //For Health files Listing
    public ArrayList getHealthFileList(int candidateId) {
        ArrayList list = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT i_healthfileid, s_name, DATE_FORMAT(ts_regdate, '%d-%b-%Y %H:%i') FROM t_healthfile WHERE i_candidateid = ? ORDER BY ts_regdate DESC ");
        String query = (sb.toString()).intern();
        sb.setLength(0);
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, candidateId);
            logger.info("getHealthFileList :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String filename, date;
            int healthId;
            while (rs.next()) {
                healthId = rs.getInt(1);
                filename = rs.getString(2) != null ? rs.getString(2) : "";
                date = rs.getString(3) != null ? rs.getString(3) : "";
                list.add(new TalentpoolInfo(healthId, filename, date));
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return list;
    }

    public int delhelathfile(int healthfileId) {
        int cc = 0;
        String query = "SELECT s_name FROM t_healthfile WHERE i_healthfileid = ?";
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, healthfileId);
            logger.info("delhelathfile :: " + pstmt.toString());
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
            query = "DELETE FROM t_healthfile WHERE i_healthfileid = ?";
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, healthfileId);
            logger.info("delhelathfile :: " + pstmt.toString());
            cc = pstmt.executeUpdate();
        } catch (Exception exception) {
            print(this, "delhelathfile :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return cc;
    }

    //For Nominee details
    public ArrayList getNomineeList(int candidateId) {
        ArrayList list = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT t_nominee.i_nomineeid, t_nominee.s_nomineename, t_nominee.s_nomineecontactno, ");
        sb.append("t_relation.s_name, t_nominee.i_status, t_nominee.s_code, t_nominee.s_address, t_nominee.i_age, t_nominee.d_percentage ");
        sb.append("FROM t_nominee ");
        sb.append("LEFT JOIN t_relation ON (t_relation.i_relationid = t_nominee.i_relationid) ");
        sb.append("WHERE t_nominee.i_candidateid = ? ORDER BY t_nominee.i_status, t_nominee.ts_regdate DESC ");
        String query = (sb.toString()).intern();
        sb.setLength(0);
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, candidateId);
            logger.info("getNomineeList :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String nomineename, nomineecontactno, address,nomineerelation, code;
            int nomineedetailId, status, age;
            double percentage;
            while (rs.next()) {
                nomineedetailId = rs.getInt(1);
                nomineename = rs.getString(2) != null ? rs.getString(2) : "";
                nomineecontactno = rs.getString(3) != null ? rs.getString(3) : "";
                nomineerelation = rs.getString(4) != null ? rs.getString(4) : "";
                status = rs.getInt(5);
                code = rs.getString(6) != null ? rs.getString(6) : "";
                address = rs.getString(7) != null ? rs.getString(7) : "";
                age = rs.getInt(8);
                percentage = rs.getDouble(9);
                if (code != null && !code.equals("")) {
                    if (nomineecontactno != null && !nomineecontactno.equals("")) {
                        nomineecontactno = "+" + code + " " + nomineecontactno;
                    }
                }
                list.add(new TalentpoolInfo(nomineedetailId, nomineename, nomineecontactno, 
                        nomineerelation, status, code, address, age, percentage, "", candidateId));
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
    public TalentpoolInfo getNomineeDetailById(int nomineeId) {
        TalentpoolInfo info = null;
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
                info = new TalentpoolInfo(nomineeId, nomineename, nomineecontactno, relationId, codeId, address, age, percentage);
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
    public int createNomineedetails(TalentpoolInfo info, int candidateId, int userId) {
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
    public int updateNominee(TalentpoolInfo info, int userId, int candidateId, int nomineedetailId) {
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

    //For PPE details
    public ArrayList getPpeList(int candidateId) {
        ArrayList list = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT t_ppedetail.i_ppedetailid, t_ppedetail.s_remarks, t_ppedetail.i_status, t_ppetype.s_name, ");
        sb.append("DATE_FORMAT(t_ppedetail.ts_moddate, '%d-%b-%Y'), t_userlogin.s_name ");
        sb.append("FROM t_ppedetail ");
        sb.append("LEFT JOIN t_ppetype ON(t_ppetype.i_ppetypeid = t_ppedetail.i_ppetypeid) ");
        sb.append("LEFT JOIN t_userlogin ON(t_userlogin.i_userid = t_ppedetail.i_userid ) ");
        sb.append("WHERE t_ppedetail.i_candidateid = ? ORDER BY t_ppedetail.i_status, t_ppetype.s_name ");
        String query = (sb.toString()).intern();
        sb.setLength(0);
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, candidateId);
            logger.info("getPpeList :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String ppetype, remark, date, username;
            int ppedetailId, status;
            while (rs.next()) {
                ppedetailId = rs.getInt(1);
                remark = rs.getString(2) != null ? rs.getString(2) : "";
                status = rs.getInt(3);
                ppetype = rs.getString(4) != null ? rs.getString(4) : "";
                date = rs.getString(5) != null ? rs.getString(5) : "";
                username = rs.getString(6) != null ? rs.getString(6) : "";
                list.add(new TalentpoolInfo(ppedetailId, remark, status, ppetype, date, username, "", candidateId));
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return list;
    }

    //For ppe add
    public TalentpoolInfo getPpeDetailById(int ppedetailId) {
        TalentpoolInfo info = null;
        String query = "SELECT i_ppetypeid, s_remarks, i_status FROM t_ppedetail WHERE i_ppedetailid = ? ";
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, ppedetailId);
            print(this, "getPpeDetailById :" + pstmt.toString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                int ppetypeId = rs.getInt(1);
                String remarks = rs.getString(2) != null ? rs.getString(2) : "";
                int status = rs.getInt(3);
                info = new TalentpoolInfo(ppetypeId, remarks, status);
            }
            rs.close();
            pstmt.close();

        } catch (Exception exception) {
            print(this, "getPpeDetailById :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return info;
    }

    public int checkDuplicacyPpe(int candidateId, int ppedetailId, String reamrks, int ppetypeId) {
        int ck = 0;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT i_ppedetailid FROM t_ppedetail WHERE i_candidateid = ? AND s_remarks = ? AND i_ppetypeid = ? AND i_status IN (1, 2)");
        if (ppedetailId > 0) {
            sb.append(" AND i_ppedetailid != ? ");
        }
        String query = (sb.toString()).intern();
        sb.setLength(0);
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            int scc = 0;
            pstmt.setInt(++scc, candidateId);
            pstmt.setString(++scc, reamrks);
            pstmt.setInt(++scc, ppetypeId);
            if (ppedetailId > 0) {
                pstmt.setInt(++scc, ppedetailId);
            }
            print(this, "checkDuplicacyPpe :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ck = 1;
            }
        } catch (Exception exception) {
            print(this, "checkDuplicacyPpe :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return ck;
    }

    //ppe create
    public int createPpedetails(TalentpoolInfo info, int candidateId, int userId) {
        int cc = 0;
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("INSERT INTO t_ppedetail  ");
            sb.append("(i_ppetypeid, s_remarks, i_status , i_userid, i_candidateid, ts_regdate, ts_moddate) ");
            sb.append("VALUES (?, ?, ?, ?, ?, ?, ?)");
            String query1 = (sb.toString()).intern();
            sb.setLength(0);
            conn = getConnection();
            pstmt = conn.prepareStatement(query1, PreparedStatement.RETURN_GENERATED_KEYS);
            int scc = 0;
            pstmt.setInt(++scc, info.getPpetypeId());
            pstmt.setString(++scc, (info.getRemarks()));
            pstmt.setInt(++scc, info.getStatus());
            pstmt.setInt(++scc, userId);
            pstmt.setInt(++scc, candidateId);
            pstmt.setString(++scc, currDate1());
            pstmt.setString(++scc, currDate1());
            print(this, "createPpedetails :: " + pstmt.toString());
            pstmt.executeUpdate();
            rs = pstmt.getGeneratedKeys();
            while (rs.next()) {
                cc = rs.getInt(1);
            }
        } catch (Exception exception) {
            print(this, "createPpedetails :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return cc;
    }

    //upadte ppe
    public int updatePpe(TalentpoolInfo info, int userId, int candidateId, int ppedetailId) {
        StringBuilder sb = new StringBuilder();
        int cc = 0;
        try {
            conn = getConnection();
            sb.append("UPDATE t_ppedetail SET ");
            sb.append("s_remarks = ?, ");
            sb.append("i_ppetypeid = ?, ");
            sb.append("i_userid = ?, ");
            sb.append("ts_moddate = ? ");
            sb.append("WHERE  i_candidateid = ? AND i_ppedetailid = ? ");
            String query = sb.toString().intern();
            sb.setLength(0);
            int scc = 0;
            pstmt = conn.prepareStatement(query);
            pstmt.setString(++scc, info.getRemarks());
            pstmt.setInt(++scc, info.getPpetypeId());
            pstmt.setInt(++scc, userId);
            pstmt.setString(++scc, currDate1());
            pstmt.setInt(++scc, candidateId);
            pstmt.setInt(++scc, ppedetailId);
            cc = pstmt.executeUpdate();
            pstmt.close();
        } catch (Exception exception) {
            print(this, "updatePpe :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return cc;
    }

    public int deletePpe(int candidateId, int ppedetailId, int userId, int status, String ipAddrStr, String iplocal) {
        int cc = 0;
        try {
            conn = getConnection();
            StringBuilder sb = new StringBuilder();
            sb.append("UPDATE t_ppedetail SET ");
            sb.append("i_status = ?, ");
            sb.append("i_userid = ?, ");
            sb.append("ts_moddate = ? ");
            sb.append("WHERE i_ppedetailid = ? AND i_candidateid = ? ");
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
            pstmt.setInt(++scc, ppedetailId);
            pstmt.setInt(++scc, candidateId);
            print(this, "deletePpe :: " + pstmt.toString());
            cc = pstmt.executeUpdate();
        } catch (Exception exception) {
            print(this, "deletePpe :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        createHistoryAccess(null, userId, ipAddrStr, iplocal, "Data Deleted", 7, ppedetailId);
        return cc;
    }

    //Contract collection
    public Collection getContracts(int assetId, int type, int contractId, int refId) {
        Collection coll = new LinkedList();
        StringBuilder sb = new StringBuilder();
        if (assetId > 0) {
            if (type == 1) {
                sb.append("SELECT i_contractid, s_name FROM t_contract WHERE i_status = 1 AND i_type= 1 AND i_clientassetid= ? ORDER BY  s_name");
            } else {
                sb.append("SELECT i_contractid, s_name FROM t_contract WHERE i_status = 1 AND i_type= 2 AND i_clientassetid= ? ");
                if (contractId > 0) {
                    sb.append(" AND i_refid = ? ");
                }
                if (refId > 0) {
                    sb.append(" AND i_refid = ? ");
                }
                sb.append(" ORDER BY s_name");
            }
            String query = (sb.toString()).intern();
            sb.setLength(0);
            coll.add(new TalentpoolInfo(-1, "Select contract"));
            try {
                conn = getConnection();
                pstmt = conn.prepareStatement(query);
                pstmt.setInt(1, assetId);
                if (type != 1 && contractId > 0) {
                    pstmt.setInt(2, contractId);
                }
                if (type != 1 && refId > 0) {
                    pstmt.setInt(2, refId);
                }
                logger.info("getContracts :: " + pstmt.toString());
                rs = pstmt.executeQuery();
                int id;
                String name;
                while (rs.next()) {
                    id = rs.getInt(1);
                    name = rs.getString(2) != null ? rs.getString(2) : "";
                    coll.add(new TalentpoolInfo(id, name));
                }
            } catch (Exception e) {
                e.printStackTrace();

            } finally {
                close(conn, pstmt, rs);
            }
        } else {
            coll.add(new TalentpoolInfo(-1, "Select contract"));
        }
        return coll;
    }
}
