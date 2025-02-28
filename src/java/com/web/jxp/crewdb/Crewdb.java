package com.web.jxp.crewdb;

import com.web.jxp.base.Base;
import com.web.jxp.base.Template;
import com.web.jxp.common.Common;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import static com.web.jxp.common.Common.*;
import java.util.Collection;
import java.util.LinkedList;
import java.util.logging.Logger;

public class Crewdb extends Base {

    private static final Logger logger = Logger.getLogger(Class.class.getName());
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    public ArrayList getCrewdbByName(String search, int statusIndex, int positionIndex, int clientIdIndex, int countryIdIndex, int assetIdIndex, int next, int count) {
        ArrayList list = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("select c.i_candidateid, CONCAT_WS(' ', c.s_firstname, c.s_middlename, c.s_lastname), ");
        sb.append("t_position.s_name, t_client.s_name, t_country.s_name, t_clientasset.s_name, c.i_status, c.s_firstname ");
        sb.append("FROM t_candidate as c ");
        sb.append("left join t_client on (t_client.i_clientid = c.i_clientid) ");
        sb.append("left join t_position on (t_position.i_positionid = c.i_positionid) ");
        sb.append("left join t_clientasset on (t_clientasset.i_clientassetid = c.i_clientassetid) ");
        sb.append("left join t_country on (t_country.i_countryid = t_clientasset.i_countryid) ");
        sb.append("where 0 = 0 and  c.i_status = 1 and c.i_pass in (1,2,3) ");
        if (statusIndex > 0) {
            sb.append(" and c.i_status = ? ");
        }
        if (positionIndex > 0) {
            sb.append(" and c.i_positionid = ? ");
        }
        if (clientIdIndex > 0) {
            sb.append("and c.i_clientid = ? ");
        }
        if (countryIdIndex > 0) {
            sb.append("and t_clientasset.i_countryid = ? ");
        }
        if (assetIdIndex > 0) {
            sb.append("and c.i_clientassetid = ? ");
        }
        if (search != null && !search.equals("")) {
            sb.append("and (CONCAT_WS(' ', c.s_firstname, c.s_middlename, c.s_lastname) like ? OR CONCAT_WS(' ', c.s_firstname, c.s_lastname) like ? OR c.i_candidateid like ? ");
            sb.append(" OR t_country.s_name like ? OR t_client.s_name like ? OR t_clientasset.s_name like ? OR t_position.s_name like ?) ");
        }
        sb.append(" order by c.i_status, c.s_firstname ");
        if (count > 0) {
            sb.append(" limit ").append(next * count).append(", ").append(count);
        }
        String query = (sb.toString()).intern();
        sb.setLength(0);

        sb.append("select count(1) FROM t_candidate as c ");
        sb.append("left join t_client on (t_client.i_clientid = c.i_clientid) ");
        sb.append("left join t_position on (t_position.i_positionid = c.i_positionid) ");
        sb.append("left join t_clientasset on (t_clientasset.i_clientassetid = c.i_clientassetid) ");
        sb.append("left join t_country on (t_country.i_countryid = t_clientasset.i_countryid) ");
        sb.append("where 0 = 0  and  c.i_status = 1 and c.i_pass in (1,2,3)  ");
        if (statusIndex > 0) {
            sb.append(" and c.i_status = ? ");
        }
        if (positionIndex > 0) {
            sb.append(" and c.i_positionid = ? ");
        }
        if (clientIdIndex > 0) {
            sb.append("and c.i_clientid = ? ");
        }
        if (countryIdIndex > 0) {
            sb.append("and t_clientasset.i_countryid = ? ");
        }
        if (assetIdIndex > 0) {
            sb.append("and c.i_clientassetid = ? ");
        }
        if (search != null && !search.equals("")) {
            sb.append("and (CONCAT_WS(' ', c.s_firstname, c.s_middlename, c.s_lastname) like ? OR CONCAT_WS(' ', c.s_firstname, c.s_lastname) like ? OR c.i_candidateid like ? ");
            sb.append(" OR t_country.s_name like ? OR t_client.s_name like ? OR t_clientasset.s_name like ? OR t_position.s_name like ?) ");
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
            if (positionIndex > 0) {
                pstmt.setInt(++scc, positionIndex);
            }
            if (clientIdIndex > 0) {
                pstmt.setInt(++scc, clientIdIndex);
            }
            if (countryIdIndex > 0) {
                pstmt.setInt(++scc, countryIdIndex);
            }
            if (assetIdIndex > 0) {
                pstmt.setInt(++scc, assetIdIndex);
            }
            if (search != null && !search.equals("")) {
                pstmt.setString(++scc, "%" + (search) + "%");
                pstmt.setString(++scc, "%" + (search) + "%");
                pstmt.setString(++scc, search.replaceFirst("^0+(?!$)", ""));

                pstmt.setString(++scc, "%" + (search) + "%");
                pstmt.setString(++scc, "%" + (search) + "%");
                pstmt.setString(++scc, "%" + (search) + "%");
                pstmt.setString(++scc, "%" + (search) + "%");
            }
            logger.info("getCrewdbByName :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String name, position, clientName, countryName, assetName, firstname;
            int crewdbId, status;
            while (rs.next()) 
            {
                crewdbId = rs.getInt(1);
                name = rs.getString(2) != null ? rs.getString(2) : "";
                position = rs.getString(3) != null ? rs.getString(3) : "";
                clientName = rs.getString(4) != null ? rs.getString(4) : "";
                countryName = rs.getString(5) != null ? rs.getString(5) : "";
                assetName = rs.getString(6) != null ? rs.getString(6) : "";
                status = rs.getInt(7);
                firstname = rs.getString(8) != null ? rs.getString(8) : "";
                list.add(new CrewdbInfo(crewdbId, name, position, clientName,
                        countryName, assetName, status, firstname));
            }
            rs.close();

            pstmt = conn.prepareStatement(countquery);
            scc = 0;
            if (statusIndex > 0) {
                pstmt.setInt(++scc, statusIndex);
            }
            if (positionIndex > 0) {
                pstmt.setInt(++scc, positionIndex);
            }
            if (clientIdIndex > 0) {
                pstmt.setInt(++scc, clientIdIndex);
            }
            if (countryIdIndex > 0) {
                pstmt.setInt(++scc, countryIdIndex);
            }
            if (assetIdIndex > 0) {
                pstmt.setInt(++scc, assetIdIndex);
            }
            if (search != null && !search.equals("")) {
                pstmt.setString(++scc, "%" + (search) + "%");
                pstmt.setString(++scc, "%" + (search) + "%");
                pstmt.setString(++scc, search.replaceFirst("^0+(?!$)", ""));

                pstmt.setString(++scc, "%" + (search) + "%");
                pstmt.setString(++scc, "%" + (search) + "%");
                pstmt.setString(++scc, "%" + (search) + "%");
                pstmt.setString(++scc, "%" + (search) + "%");
            }
            //print(this,"getCrewdbByName :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                crewdbId = rs.getInt(1);
                list.add(new CrewdbInfo(crewdbId, "", "", "", "", "", 0, ""));
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
        CrewdbInfo info;
        int total = 0;
        if (l != null) {
            total = l.size();
        }
        try {
            if (total > 0) {
                record = new HashMap();
                int i;
                for (i = 0; i < total; i++) {
                    info = (CrewdbInfo) l.get(i);
                    record.put(getInfoValue(info, colId), info);
                }
            }
            Map map;
            if (colId.equals("1")) {
                map = sortById(record, tp);
            } else {
                map = sortByName(record, tp);
            }
            Iterator it = map.keySet().iterator();
            list = new ArrayList();
            CrewdbInfo rInfo;
            while (it.hasNext()) {
                String key = (String) it.next();
                int i;
                for (i = 0; i < total; i++) {
                    rInfo = (CrewdbInfo) l.get(i);
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
    public String getInfoValue(CrewdbInfo info, String i) {
        String infoval = "";
        if (i != null && i.equals("1")) {
            infoval = info.getCrewdbId() + "";
        } else if (i != null && i.equals("2")) {
            infoval = info.getName() != null ? info.getName() : "";
        } else if (i != null && i.equals("3")) {
            infoval = info.getPosition() != null ? info.getPosition() : "";
        } else if (i != null && i.equals("4")) {
            infoval = info.getClientName() != null ? info.getClientName() : "";
        } else if (i != null && i.equals("5")) {
            infoval = info.getCountryName() != null ? info.getCountryName() : "";
        } else if (i != null && i.equals("6")) {
            infoval = info.getAssetName() != null ? info.getAssetName() : "";
        }
        return infoval;
    }

    public Collection getPositions() {
        Collection coll = new LinkedList();
        String query = ("SELECT i_positionid, s_name FROM t_position where i_status = 1 order by s_name").intern();
        coll.add(new CrewdbInfo(-1, "Select Position"));
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
                coll.add(new CrewdbInfo(refId, refName));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return coll;
    }

    public ArrayList getList1(int candidateId) {
        ArrayList list = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT t_candlang.i_candlangid, t_language.s_name, t_proficiency.s_name, t_candlang.s_filename, t_candlang.i_vstatus ");
        sb.append("FROM t_candlang ");
        sb.append("LEFT JOIN t_language ON (t_language.i_languageid = t_candlang.i_languageid) ");
        sb.append("LEFT JOIN t_proficiency ON (t_proficiency.i_proficiencyid = t_candlang.i_proficiencyid) ");
        sb.append("WHERE t_candlang.i_candidateid = ? AND t_candlang.i_status = 1 ");
        sb.append("ORDER BY t_language.s_name ");
        String query = (sb.toString()).intern();
        sb.setLength(0);
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, candidateId);
            rs = pstmt.executeQuery();
            String langaugeName, proficiencyName, candlangfilename;
            int vstatus, languageId;
            while (rs.next()) 
            {
                languageId = rs.getInt(1);
                langaugeName = rs.getString(2) != null ? rs.getString(2) : "";
                proficiencyName = rs.getString(3) != null ? rs.getString(3) : "";
                candlangfilename = rs.getString(4) != null ? rs.getString(4) : "";
                vstatus = rs.getInt(5);
                list.add(new CrewdbInfo(languageId, langaugeName, proficiencyName, candlangfilename, vstatus));
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return list;
    }
    //---- Workexp

    public ArrayList getworkexpList(int candidateId) 
    {
        ArrayList list = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT t_workexperience.i_experienceid, t_position.s_name, t_experiencedept.s_name, t_workexperience.s_companyname, t_assettype.s_name, ");
        sb.append("t_workexperience.s_assetname, DATE_FORMAT(t_workexperience.d_workstartdate, '%d-%b-%Y'), DATE_FORMAT (t_workexperience.d_workenddate , '%d-%b-%Y'), ");
        sb.append("t_workexperience.s_filenameexp, t_workexperience.i_currentworkingstatus, t_workexperience.i_vstatus FROM t_workexperience ");
        sb.append("LEFT JOIN t_experiencedept ON  t_experiencedept.i_experiencedeptid = t_workexperience.i_departmentid ");
        sb.append("LEFT JOIN t_position ON t_position.i_positionid = t_workexperience.i_positionid ");
        sb.append("LEFT JOIN t_assettype ON (t_assettype.i_assettypeid=  t_workexperience. i_assettypeid) ");
        sb.append("WHERE t_workexperience.i_candidateid = ? AND t_workexperience.i_status = 1 ");
        sb.append("ORDER BY t_workexperience.d_workstartdate ");
        String query = (sb.toString()).intern();
        sb.setLength(0);
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, candidateId);
            logger.info("getworkexpList :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String jobtitle, department, companyname, rigname, workstartdate, workenddate, workexpfilename, assettype;
            int currentwork, vstatus, experienceId;
            while (rs.next()) 
            {
                experienceId = rs.getInt(1);
                jobtitle = rs.getString(2) != null ? rs.getString(2) : "";
                department = rs.getString(3) != null ? rs.getString(3) : "";
                companyname = rs.getString(4) != null ? rs.getString(4) : "";
                assettype = rs.getString(5) != null ? rs.getString(5) : "";
                rigname = rs.getString(6) != null ? rs.getString(6) : "";
                workstartdate = rs.getString(7) != null ? rs.getString(7) : "";
                workenddate = rs.getString(8) != null ? rs.getString(8) : "";
                workexpfilename = rs.getString(9) != null ? rs.getString(9) : "";
                currentwork = rs.getInt(10);
                vstatus = rs.getInt(11);

                if (currentwork == 1) {
                    workenddate = "Present";
                }

                list.add(new CrewdbInfo(experienceId, jobtitle, department, companyname, assettype, rigname, workstartdate, workenddate, workexpfilename,
                        vstatus));
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return list;
    }

    public ArrayList getListbank(int candidateId) 
    {
        ArrayList list = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT t_bankdetail.i_bankdetid, s_bankname, t_bankaccounttype.s_name, s_branch, s_ifsccode, i_primarybankid, s_filename, ");
        sb.append("t_bankdetail.i_vstatus FROM t_bankdetail ");
        sb.append("LEFT JOIN t_bankaccounttype on (t_bankdetail.i_bankaccounttypeid = t_bankaccounttype.i_bankaccounttypeid) ");
        sb.append("WHERE t_bankdetail.i_candidateid = ? AND t_bankdetail.i_status = 1 ");
        sb.append("ORDER BY t_bankdetail.s_bankname ");
        String query = (sb.toString()).intern();
        sb.setLength(0);
        try
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, candidateId);
            logger.info("getListbk :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String bankName, savingAccountNo, branch, IFSCCode, bkFilename;
            int primarybankid, vstatus, bankId;
            while (rs.next()) 
            {
                bankId = rs.getInt(1);
                bankName = rs.getString(2) != null ? rs.getString(2) : "";
                savingAccountNo = rs.getString(3) != null ? rs.getString(3) : "";
                branch = rs.getString(4) != null ? rs.getString(4) : "";
                IFSCCode = decipher(rs.getString(5) != null ? rs.getString(5) : "");
                primarybankid = rs.getInt(6);
                bkFilename = rs.getString(7) != null ? rs.getString(7) : "";
                vstatus = rs.getInt(8);
                list.add(new CrewdbInfo(bankId, bankName, savingAccountNo, branch, IFSCCode, primarybankid, bkFilename, vstatus));
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return list;
    }

    public CrewdbInfo gethealthdetail(int candidateId) {
        CrewdbInfo info = null;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT t_healthdeclaration.s_ssmf, t_healthdeclaration.s_ogukmedicalftw, DATE_FORMAT(d_ogukexp,'%d-%b-%Y'), s_medifitcert, ");
        sb.append("DATE_FORMAT(d_medifitcertexp, '%d-%b-%Y'), s_bloodgroup, t_bloodpressure.s_name, s_hypertension, s_diabetes, s_smoking, s_covid192doses, ");
        sb.append("t_healthdeclaration.i_vstatus FROM t_healthdeclaration ");
        sb.append("LEFT JOIN t_bloodpressure ON (t_bloodpressure.i_bloodpressureid = t_healthdeclaration.i_bloodpressureid) ");
        sb.append("WHERE t_healthdeclaration.i_candidateid = ? and t_healthdeclaration.i_status = 1 ");
        String query = (sb.toString()).intern();
        sb.setLength(0);
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, candidateId);
            // logger.info("gethealthdetail :: " + pstmt.toString());
            rs = pstmt.executeQuery();

            while (rs.next()) 
            {
                String ssmf = decipher(rs.getString(1) != null ? rs.getString(1): "") ;
                String ogukmedicalftw = decipher(rs.getString(2) != null ? rs.getString(2): "") ;
                String ogukexp = rs.getString(3);
                String medifitcert = decipher(rs.getString(4) != null ? rs.getString(4): "") ;
                String medifitcertexp = rs.getString(5);
                String bloodgroup = decipher(rs.getString(6) != null ? rs.getString(6): "") ;
                String bloodpressure = rs.getString(7);
                String hypertension = decipher(rs.getString(8) != null ? rs.getString(8): "") ;
                String diabetes = decipher(rs.getString(9) != null ? rs.getString(9): "") ;
                String smoking = decipher(rs.getString(10) != null ? rs.getString(10): "") ;
                String covid192doses = decipher(rs.getString(11) != null ? rs.getString(11): "") ;
                int vstatus = rs.getInt(12);
                info = new CrewdbInfo(ssmf, ogukmedicalftw, ogukexp, medifitcert, medifitcertexp, bloodgroup, bloodpressure, hypertension, diabetes, smoking,
                        covid192doses, vstatus);
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return info;
    }
    // Vaccination

    public ArrayList getvaccinationList(int candidateId) {
        ArrayList list = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT t_vbd.i_vbdid, t_vaccine.s_name, t_vaccinetype.s_name, t_city.s_name, DATE_FORMAT(t_vbd.d_dateofapplication, '%d-%b-%Y'), ");
        sb.append("DATE_FORMAT(t_vbd.d_dateofexpiry, '%d-%b-%Y'), t_vbd.s_filename, t_vbd.i_vstatus, t_country.s_name FROM t_vbd ");
        sb.append("LEFT JOIN t_vaccine ON (t_vbd.i_vaccinenameid = t_vaccine.i_vaccineid) ");
        sb.append("LEFT JOIN t_vaccinetype ON (t_vbd.i_vaccinetypeid = t_vaccinetype.i_vaccinetypeid) ");
        sb.append("LEFT JOIN t_city ON (t_vbd.i_placeofapplicationid = t_city.i_cityid) ");
        sb.append("LEFT JOIN t_country ON (t_country.i_countryid = t_city.i_countryid) ");
        sb.append("where t_vbd.i_candidateid = ? AND t_vbd.i_status = 1 ");
        sb.append("ORDER BY t_vbd.d_dateofapplication DESC ");
        String query = (sb.toString()).intern();
        sb.setLength(0);
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, candidateId);
            logger.info("getvaccinationList :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String vaccinename, vaccinetype, placeofapplication, dateofapplication, dateofexpiry, filename, countryname;
            int i = 1, vstatus, vaccinationId;
            while (rs.next()) 
            {
                vaccinationId = rs.getInt(1);
                vaccinename = rs.getString(2) != null ? rs.getString(2) : "";
                vaccinetype = rs.getString(3) != null ? rs.getString(3) : "";
                placeofapplication = rs.getString(4) != null ? rs.getString(4) : "";
                dateofapplication = rs.getString(5) != null ? rs.getString(5) : "";
                dateofexpiry = rs.getString(6) != null ? rs.getString(6) : "";
                filename = rs.getString(7) != null ? rs.getString(7) : "";
                vstatus = rs.getInt(8);
                countryname = rs.getString(9) != null ? rs.getString(9) : "";
                if (!placeofapplication.equals("")) {
                    if (!countryname.equals("")) {
                        placeofapplication += " (" + countryname + ")";
                    }
                }

                list.add(new CrewdbInfo(vaccinationId, vaccinename, vaccinetype, placeofapplication, dateofapplication, dateofexpiry, filename, i, vstatus));
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return list;
    }

    public ArrayList getListGovdoc(int candidateId) 
    {
        ArrayList list = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT t_govdoc.i_govid, t_doctype.s_doc, t_govdoc.s_docno, DATE_FORMAT(t_govdoc.d_dateofissue, '%d-%b-%Y'), DATE_FORMAT(t_govdoc.d_expirydate, '%d-%b-%Y'), ");
        sb.append("t_city.s_name, t_documentissuedby.s_name, t_govdoc.i_vstatus, t_country.s_name ");
        sb.append("FROM t_govdoc ");
        sb.append("LEFT JOIN t_doctype ON (t_govdoc.i_doctypeid = t_doctype.i_doctypeid) ");
        sb.append("LEFT JOIN t_city ON ( t_city.i_cityid = t_govdoc.i_placeofissueid ) ");
        sb.append("LEFT JOIN t_documentissuedby ON ( t_documentissuedby.i_documentissuedbyid = t_govdoc.i_documentissuedbyid ) ");
        sb.append("LEFT JOIN t_country ON (t_city.i_countryid = t_country.i_countryid) ");
        sb.append("WHERE t_govdoc.i_candidateid = ? AND t_govdoc.i_status = 1 ");
        sb.append("ORDER BY t_doctype.s_doc ");
        String query = (sb.toString()).intern();
        sb.setLength(0);
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, candidateId);
            logger.info("getgovdocdetail :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String documentName, documentNo, dateOfissue, dateOfexpiry, placeOfissue, docIssuedby, docFilename = "", countryname;
            int i = 1, j = 0, vstatus, govId;
            while (rs.next()) 
            {
                govId = rs.getInt(1);
                documentName = rs.getString(2) != null ? rs.getString(2) : "";
                documentNo = decipher(rs.getString(3) != null ? rs.getString(3) : "");
                dateOfissue = rs.getString(4) != null ? rs.getString(4) : "";
                dateOfexpiry = rs.getString(5) != null ? rs.getString(5) : "";
                placeOfissue = rs.getString(6) != null ? rs.getString(6) : "";
                docIssuedby = rs.getString(7) != null ? rs.getString(7) : "";
                vstatus = rs.getInt(8);
                countryname = rs.getString(9) != null ? rs.getString(9) : "";
                if (!countryname.equals("")) {
                    placeOfissue += "(" + countryname + ")";
                }
                list.add(new CrewdbInfo(govId, documentName, documentNo, dateOfissue, dateOfexpiry, placeOfissue, docIssuedby, docFilename, i, j, vstatus,
                        countryname));
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return list;
    }

    public ArrayList getListeduc(int candidateId) 
    {
        ArrayList list = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT t_eduqual.i_eduqulid, t_degree.s_name, t_qualificationtype.s_name, t_eduqual.s_eduinstitute, t_city.s_name, t_eduqual.s_fieldofstudy, ");
        sb.append("DATE_FORMAT(t_eduqual.d_coursestart, '%d-%b-%Y'), DATE_FORMAT(t_eduqual.d_passingyear, '%d-%b-%Y'), t_eduqual.s_filename, t_eduqual.i_vstatus, ");
        sb.append("t_country.s_name FROM t_eduqual ");
        sb.append("LEFT JOIN t_qualificationtype ON t_qualificationtype.i_qualificationtypeid = t_eduqual.i_qualificationtypeid ");
        sb.append("LEFT JOIN t_city ON t_city.i_cityid = t_eduqual.i_locationofinstituteid ");
        sb.append("LEFT JOIN t_degree ON t_degree.i_degreeid = t_eduqual. i_degreeid ");
        sb.append("LEFT JOIN t_country ON t_city.i_countryid = t_country. i_countryid ");
        sb.append("WHERE t_eduqual.i_candidateid = ? AND t_eduqual.i_status = 1 ");
        sb.append("ORDER BY t_eduqual.d_passingyear DESC ");
        String query = (sb.toString()).intern();
        sb.setLength(0);
        try
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, candidateId);
            logger.info("getListeduc :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String degree, backgroundofstudy, eduinstitute, locationofinstitute, fieldofstudy, coursestart, passingyear, educfilename;
            int i = 1, vstatus, eduqulId;
            while (rs.next())
            {
                eduqulId = rs.getInt(1);
                degree = rs.getString(2) != null ? rs.getString(2) : "";
                backgroundofstudy = rs.getString(3) != null ? rs.getString(3) : "";
                eduinstitute = decipher(rs.getString(4) != null ? rs.getString(4) : "");
                locationofinstitute = rs.getString(5) != null ? rs.getString(5) : "";
                fieldofstudy = rs.getString(6) != null ? rs.getString(6) : "";
                coursestart = rs.getString(7) != null ? rs.getString(7) : "";
                passingyear = rs.getString(8) != null ? rs.getString(8) : "";
                educfilename = rs.getString(9) != null ? rs.getString(9) : "";
                vstatus = rs.getInt(10);
                String countryname = rs.getString(11) != null ? rs.getString(11) : "";
                if (!countryname.equals("")) {
                    locationofinstitute += "(" + countryname + ")";
                }
                list.add(new CrewdbInfo(eduqulId, degree, backgroundofstudy, eduinstitute, fieldofstudy, coursestart, passingyear, educfilename, i, vstatus,
                        locationofinstitute));
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return list;
    }

    //------------------ Certificates ----
    public ArrayList getListcerti(int candidateId) 
    {
        ArrayList list = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT t_trainingandcert.i_tcid, t_coursename.s_name, t_coursetype.s_name, t_trainingandcert.s_educinst, t_city.s_name, ");
        sb.append("t_approvedby.s_name, DATE_FORMAT(t_trainingandcert.d_dateofissue, '%d-%b-%Y'), DATE_FORMAT(t_trainingandcert.d_expirydate, '%d-%b-%Y'), ");
        sb.append("t_trainingandcert.s_filename, t_trainingandcert.i_vstatus, t_country.s_name FROM t_trainingandcert ");
        sb.append("LEFT JOIN t_coursetype ON t_coursetype.i_coursetypeid = t_trainingandcert. i_coursetypeid ");
        sb.append("LEFT JOIN t_coursename ON (t_coursename.i_coursenameid = t_trainingandcert.i_coursenameid) ");
        sb.append("LEFT JOIN t_city ON t_city.i_cityid = t_trainingandcert. i_locationofinstitid ");
        sb.append("LEFT JOIN t_approvedby ON t_approvedby.i_approvedbyid = t_trainingandcert.i_approvedbyid  ");
        sb.append("LEFT JOIN t_country ON t_country.i_countryid = t_city.i_countryid ");
        sb.append("WHERE t_trainingandcert.i_candidateid = ? AND t_trainingandcert.i_status = 1 ");
        sb.append("ORDER BY t_trainingandcert.d_passingyear DESC");
        String query = (sb.toString()).intern();
        sb.setLength(0);
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, candidateId);
            logger.info("getListcerti :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String coursename, coursetype, educinst, locationofinstit, approvedby, issuedate, expirydate, certifilename, countryname;
            int vstatus, tcId;
            while (rs.next()) 
            {
                tcId = rs.getInt(1);
                coursename = rs.getString(2) != null ? rs.getString(2) : "";
                coursetype = rs.getString(3) != null ? rs.getString(3) : "";
                educinst = rs.getString(4) != null ? rs.getString(4) : "";
                locationofinstit = rs.getString(5) != null ? rs.getString(5) : "";
                approvedby = rs.getString(6) != null ? rs.getString(6) : "";
                issuedate = rs.getString(7) != null ? rs.getString(7) : "";
                expirydate = rs.getString(8) != null ? rs.getString(8) : "";
                certifilename = rs.getString(9) != null ? rs.getString(9) : "";
                vstatus = rs.getInt(10);
                countryname = rs.getString(11) != null ? rs.getString(11) : "";
               if(!educinst.equals(""))
                {
                   if (!countryname.equals("")) 
                   {
                      educinst += ","+ locationofinstit+  " (" + countryname + ")";
                   }
                }
                list.add(new CrewdbInfo(tcId, coursename, coursetype, educinst, approvedby, issuedate, expirydate, certifilename, vstatus));
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return list;
    }

    public CrewdbInfo getCrewdbDetail(int crewdbId) 
    {
        CrewdbInfo info = null;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT CONCAT_WS(' ', c.s_firstname, c.s_middlename, c.s_lastname), DATE_FORMAT(c.d_dob, '%d-%b-%Y'), c.s_placeofbirth, ");
        sb.append("c.s_email, c.s_code1, c.s_contactno1, c.s_code2, c.s_contactno2, c.s_code3, c.s_contactno3, c.s_gender, t_country.s_name, t_city.s_name, ");
        sb.append("n.s_nationality, c.s_address1line1, c.s_address1line2, c.s_address1line3, c.s_address2line1, c.s_address2line2, c.s_address2line3, ");
        sb.append("t_experiencedept.s_name,c.i_expectedsalary,t_currency.s_name, c.s_nextofkin, r.s_name, c.s_ecode1, c.s_econtactno1, c.s_ecode2, c.s_econtactno2, ");
        sb.append("m.s_name, c.s_photofilename, t_position.s_name, c.s_firstname, t1.ct, t_grade.s_name, c.i_cflag1, c.i_cflag3, ");
        sb.append("c.i_cflag4, c.i_cflag9, c.i_cflag10, t_client.s_ocsuserids, p2.s_name, g2.s_name, c.i_applytype, c.i_cflag11, t_state.s_name, c.s_pincode, c.i_age, c.s_airport1, c.s_airport2 ");
        sb.append("FROM t_candidate as c LEFT JOIN t_country on (t_country.i_countryid = c.i_countryid) ");
        sb.append("LEFT JOIN t_relation as r ON (r.i_relationid = c.i_relationid) ");
        sb.append("LEFT JOIN t_maritialstatus as m on (m.i_maritialstatusid = c.i_maritialstatusid) ");
        sb.append("LEFT JOIN t_country as n ON (n.i_countryid = c.i_nationalityid) ");
        sb.append("LEFT JOIN t_city  ON (t_city.i_cityid = c.i_cityid) ");
        sb.append("LEFT JOIN t_position ON (t_position.i_positionid = c.i_positionid) ");
        sb.append("LEFT JOIN t_experiencedept on (t_experiencedept.i_experiencedeptid = c.i_departmentid) ");
        sb.append("LEFT JOIN t_currency ON (t_currency.i_currencyid = c.i_currencyid) ");
        sb.append("LEFT JOIN t_position AS p2 ON (p2.i_positionid = c.i_positionid2) ");
        sb.append(" LEFT JOIN t_grade AS g2 ON (g2.i_gradeid = p2.i_gradeid) ");        
        sb.append("LEFT JOIN (SELECT i_candidateid, COUNT(1) as ct from t_candidatefiles WHERE i_candidateid = ? GROUP BY i_candidateid) as t1 on (c.i_candidateid = t1.i_candidateid) ");
        sb.append("LEFT JOIN t_grade ON (t_grade.i_gradeid = t_position.i_gradeid) ");        
        sb.append("LEFT JOIN t_client ON (t_client.i_clientid = c.i_clientid) ");
        sb.append("LEFT JOIN t_state ON (t_state.i_stateid = c.i_stateid) ");
        sb.append("WHERE c.i_candidateid = ? ");
        String query = sb.toString().intern();
        sb.setLength(0);
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, crewdbId);
            pstmt.setInt(2, crewdbId);
            print(this, "getCrewdbDetailById :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            while (rs.next())
            {
                String name = rs.getString(1) != null ? rs.getString(1) : "";
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
                String city = rs.getString(13) != null ? rs.getString(13) : "";
                String nationality = rs.getString(14) != null ? rs.getString(14) : "";
                String address1line1 = rs.getString(15) != null ? rs.getString(15) : "";
                String address1line2 = rs.getString(16) != null ? rs.getString(16) : "";
                String address1line3 = rs.getString(17) != null ? rs.getString(17) : "";
                String address2line1 = rs.getString(18) != null ? rs.getString(18) : "";
                String address2line2 = rs.getString(19) != null ? rs.getString(19) : "";
                String address2line3 = rs.getString(20) != null ? rs.getString(20) : "";
                String preferredDept = rs.getString(21) != null ? rs.getString(21) : "";
                int expectedsalary = rs.getInt(22);
                String currency = rs.getString(23) != null ? rs.getString(23) : "";
                String nextofkin = decipher(rs.getString(24) != null ? rs.getString(24) : "");
                String relation = rs.getString(25) != null ? rs.getString(25) : "";
                String econtact1_code = rs.getString(26) != null ? rs.getString(26) : "";
                String econtact1 = decipher(rs.getString(27) != null ? rs.getString(27) : "");
                String econtact2_code = rs.getString(28) != null ? rs.getString(28) : "";
                String econtact2 = decipher(rs.getString(29) != null ? rs.getString(29) : "");
                String maritialstatus = rs.getString(30) != null ? rs.getString(30) : "";
                String photo = rs.getString(31) != null ? rs.getString(31) : "";
                String positionName = rs.getString(32) != null ? rs.getString(32) : "";
                String firstname = rs.getString(33) != null ? rs.getString(33) : "";
                int filecount = rs.getInt(34);
                String gradeName = rs.getString(35) != null ? rs.getString(35) : "";
                int cflag1 = rs.getInt(36);
                int cflag3 = rs.getInt(37);
                int cflag4 = rs.getInt(38);
                int cflag9 = rs.getInt(39);
                int cflag10 = rs.getInt(40);
                String ocsuserids = rs.getString(41) != null ? rs.getString(41) : "";
                String position2 = rs.getString(42) != null ? rs.getString(42) : "";
                String grade2 = rs.getString(43) != null ? rs.getString(43) : "";
                String applytypevalue = getApplytypeStatusbyId(rs.getInt(44));
                int cflag11 = rs.getInt(45);
                String state = rs.getString(46) != null ? rs.getString(46) : "";
                String pincode = rs.getString(47) != null ? rs.getString(47) : "";
                int age = rs.getInt(48);
                String airport1 = rs.getString(49) != null ? rs.getString(49) : "";
                String airport2 = rs.getString(50) != null ? rs.getString(50) : "";
                if (!position2.equals("")) 
                {
                    if (!grade2.equals("")) 
                    {
                        position2 += " | " + grade2;
                    }
                }
                String ccval = "";
                if(!ocsuserids.equals(""))
                {
                    String query_cc = "SELECT GROUP_CONCAT(s_email) FROM t_userlogin WHERE i_userid IN ("+ocsuserids+")";
                    PreparedStatement pstmt_cc = conn.prepareStatement(query_cc);
                    ResultSet rs_cc = pstmt_cc.executeQuery();
                    while (rs_cc.next()) 
                    { 
                        ccval = rs_cc.getString(1) != null ? rs_cc.getString(1) : "";
                    }
                }
                String resumefilename = "";
                if (!positionName.equals("")) {
                    if (!gradeName.equals("")) {
                        positionName += " - " + gradeName;
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
                info = new CrewdbInfo(crewdbId, name, dob, place, email, contact1_code, contact1,
                        contact2_code, contact2, contact3_code, contact3, gender, countryName, city,
                        nationality, address1line1, address1line2, address1line3, address2line1, address2line2,
                        address2line3, preferredDept, expectedsalary, currency, nextofkin, relation, econtact1_code, econtact1, econtact2_code,
                        econtact2, maritialstatus, resumefilename, photo, positionName, firstname, filecount, 
                        cflag1, cflag3, cflag4, cflag9, cflag10, ccval, position2, applytypevalue, cflag11, 
                        state, pincode, age, airport1, airport2);
            }
        } catch (Exception exception) {
            print(this, "getCrewdbDetailById :: " + exception.getMessage());
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
    
    public CrewdbInfo getPassDetail(int crewdbId) {
        CrewdbInfo info = null;
        String selQuery="SELECT i_pass FROM t_candidate WHERE i_candidateid = ? ";
        try
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(selQuery);
            pstmt.setInt(1, crewdbId);
            print(this, "getPassDetail :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            while (rs.next()) 
            {
                int ipass = rs.getInt(1);
                info = new CrewdbInfo(ipass);
            }
        } catch (Exception exception) {
            print(this, "getPassDetail :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return info;
    }

    public ArrayList getListForExcel(String search, int positionIndex, int clientIdIndex, int countryIdIndex, int assetIdIndex) 
    {
        ArrayList list = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("select c.i_candidateid, CONCAT_WS(' ', c.s_firstname, c.s_middlename, c.s_lastname), ");
        sb.append("t_position.s_name, t_client.s_name, t_country.s_name, t_clientasset.s_name ");
        sb.append("FROM t_candidate as c left join t_client on (t_client.i_clientid = c.i_clientid) ");
        sb.append("left join t_position on (t_position.i_positionid = c.i_positionid) ");
        sb.append("left join t_clientasset on (t_clientasset.i_clientassetid = c.i_clientassetid) ");
        sb.append("left join t_country on (t_country.i_countryid = t_clientasset.i_countryid) ");
        sb.append("where 0 = 0 ");

        if (positionIndex > 0) {
            sb.append(" and c.i_positionid = ? ");
        }
        if (clientIdIndex > 0) {
            sb.append("and c.i_clientid = ? ");
        }
        if (countryIdIndex > 0) {
            sb.append("and t_clientasset.i_countryid = ? ");
        }
        if (assetIdIndex > 0) {
            sb.append("and c.i_clientassetid = ? ");
        }
        if (search != null && !search.equals("")) {
            sb.append("and (CONCAT_WS(' ', c.s_firstname, c.s_middlename, c.s_lastname) like ? OR CONCAT_WS(' ', c.s_firstname, c.s_lastname) like ? OR c.i_candidateid like ? ");
            sb.append(" OR t_country.s_name like ? OR t_client.s_name like ? OR t_clientasset.s_name like ? OR t_position.s_name like ?) ");
        }
        sb.append(" order by c.s_firstname ");
        String query = sb.toString().intern();
        sb.setLength(0);
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            int scc = 0;

            if (positionIndex > 0) {
                pstmt.setInt(++scc, positionIndex);
            }
            if (clientIdIndex > 0) {
                pstmt.setInt(++scc, clientIdIndex);
            }
            if (countryIdIndex > 0) {
                pstmt.setInt(++scc, countryIdIndex);
            }
            if (assetIdIndex > 0) {
                pstmt.setInt(++scc, assetIdIndex);
            }
            if (search != null && !search.equals("")) {
                pstmt.setString(++scc, "%" + (search) + "%");
                pstmt.setString(++scc, "%" + (search) + "%");
                pstmt.setString(++scc, search.replaceFirst("^0+(?!$)", ""));
                pstmt.setString(++scc, "%" + (search) + "%");
                pstmt.setString(++scc, "%" + (search) + "%");
                pstmt.setString(++scc, "%" + (search) + "%");
                pstmt.setString(++scc, "%" + (search) + "%");
            }
            Common.print(this, "getListForExcel :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String name, position, clientName, countryName, assetName;
            int crewdbId;
            while (rs.next()) 
            {
                crewdbId = rs.getInt(1);
                name = rs.getString(2) != null ? rs.getString(2) : "";
                position = rs.getString(3) != null ? rs.getString(3) : "";
                clientName = rs.getString(4) != null ? rs.getString(4) : "";
                countryName = rs.getString(5) != null ? rs.getString(5) : "";
                assetName = rs.getString(6) != null ? rs.getString(6) : "";
                list.add(new CrewdbInfo(crewdbId, name, position, clientName,
                        countryName, assetName, 0, ""));
            }
            rs.close();
            pstmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return list;
    }

    public Collection getClients() {
        Collection coll = new LinkedList();
        String query = ("SELECT i_clientid, s_name FROM t_client where i_status = 1 order by s_name");
        coll.add(new CrewdbInfo(-1, "Select Client"));
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            rs = pstmt.executeQuery();
            int id;
            String name;
            while (rs.next()) 
            {
                id = rs.getInt(1);
                name = rs.getString(2) != null ? rs.getString(2): "";
                coll.add(new CrewdbInfo(id, name));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return coll;
    }

    public Collection getClientLocations(int clientId) 
    {
        Collection coll = new LinkedList();
        if (clientId > 0) 
        {
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT DISTINCT(t_clientasset.i_countryid), t_country.s_name FROM t_clientasset ");
            sb.append("left join t_country on (t_country.i_countryid = t_clientasset.i_countryid) ");
            sb.append("where t_clientasset.i_clientid = ? and t_clientasset.i_status = 1 order by t_country.s_name");
            String query = sb.toString();
            sb.setLength(0);
            coll.add(new CrewdbInfo(-1, "Select Location"));
            try
            {
                conn = getConnection();
                pstmt = conn.prepareStatement(query);
                pstmt.setInt(1, clientId);
                rs = pstmt.executeQuery();
                int id;
                String name;
                while (rs.next()) 
                {
                    id = rs.getInt(1);
                    name = rs.getString(2);
                    coll.add(new CrewdbInfo(id, name));
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                close(conn, pstmt, rs);
            }
        } else {
            coll.add(new CrewdbInfo(-1, "Select Location"));
        }
        return coll;
    }

    public Collection getClientAssetsList(int clientId, int countryId) 
    {
        Collection coll = new LinkedList();
        if (clientId > 0 && countryId > 0) 
        {
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT t_clientasset.i_clientassetid, t_clientasset.s_name FROM t_clientasset ");
            sb.append("where t_clientasset.i_clientid = ? and t_clientasset.i_countryid = ? and t_clientasset.i_status = 1 order by t_clientasset.s_name");
            String query = sb.toString();
            sb.setLength(0);
            try 
            {
                conn = getConnection();
                pstmt = conn.prepareStatement(query);
                pstmt.setInt(1, clientId);
                pstmt.setInt(2, countryId);
                rs = pstmt.executeQuery();
                int id;
                String name;
                while (rs.next())
                {
                    id = rs.getInt(1);
                    name = rs.getString(2);
                    coll.add(new CrewdbInfo(id, name));
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                close(conn, pstmt, rs);
            }
        }
        return coll;
    }

    public String getAssstatusbyId(int status) 
    {
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

    public ArrayList getCassessmenthistoryByCandidateId(int candidateId) 
    {
        ArrayList list = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT t1.i_cassessmenthid,t1.i_cassessmentid, c1.s_name, a1.s_name,t_assessment.s_name, ");
        sb.append("t1.i_status,t1.i_marks, DATE_FORMAT(t1.ts_regdate, '%d-%b-%Y'), DATE_FORMAT(t1.ts_regdate, '%H:%i') ");
        sb.append("from t_cassessmenth as t1 ");
        sb.append("LEFT JOIN t_positionassessment on (t1.i_paid = t_positionassessment.i_positionassessmentid) ");
        sb.append("LEFT JOIN t_assessment on (t_assessment.i_assessmentid = t_positionassessment.i_assessmentid) ");
        sb.append("LEFT JOIN t_userlogin  as a1 on (t1.i_assessorid = a1.i_userid) ");
        sb.append("LEFT JOIN t_userlogin  as c1 on (t1.i_coordinatorid = c1.i_userid) ");
        sb.append("WHERE t1.i_candidateid = ? ");
        sb.append(" ORDER BY t1.ts_regdate desc ");

        String query = (sb.toString()).intern();
        sb.setLength(0);
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, candidateId);
            logger.info("getCassessmenthistoryByCandidateId :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String statusValue, assessorName, date, time, coordinatorName, assessment;
            int cassessmentId, cassessmenthId, marks, status;
            while (rs.next()) 
            {
                cassessmenthId = rs.getInt(1);
                cassessmentId = rs.getInt(2);
                coordinatorName = rs.getString(3) != null ? rs.getString(3) : "";
                assessorName = rs.getString(4) != null ? rs.getString(4) : "";
                assessment = rs.getString(5) != null ? rs.getString(5) : "";
                status = rs.getInt(6);
                marks = rs.getInt(7);
                date = rs.getString(8) != null ? rs.getString(8) : "";
                time = rs.getString(9) != null ? rs.getString(9) : "";
                
                statusValue = getAssstatusbyId(status);
                list.add(new CrewdbInfo(cassessmenthId, cassessmentId, coordinatorName, assessorName, assessment, statusValue, marks, date, time, status));
            }
            rs.close();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return list;

    }
    
    public String getVerifyMessage(String name, String documentname, String message,  String htmlFile) 
    {
        String adminpage = getMainPath("template_path") + htmlFile;
        HashMap hashmap = new HashMap();
        Template template = new Template(adminpage);
        hashmap.put("NAME", name);
        hashmap.put("DOCUMENTNAME", documentname);
        hashmap.put("MESSAGE", message);
        return template.patch(hashmap);
    }
    
    public ArrayList getNomineeList(int candidateId) {
        ArrayList list = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT t_nominee.i_nomineeid, t_nominee.s_nomineename, t_nominee.s_nomineecontactno, ");
        sb.append("t_relation.s_name, t_nominee.i_status, t_nominee.s_code, t_nominee.i_vstatus ");
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
            String nomineename, nomineecontactno, nomineerelation, code;
            int nomineedetailId, status, vstatus;
            while (rs.next()) {
                nomineedetailId = rs.getInt(1);
                nomineename = rs.getString(2) != null ? rs.getString(2) : "";
                nomineecontactno = rs.getString(3) != null ? rs.getString(3) : "";
                nomineerelation = rs.getString(4) != null ? rs.getString(4) : "";
                status = rs.getInt(5);
                code = rs.getString(6) != null ? rs.getString(6) : "";
                vstatus = rs.getInt(7);
                if (code != null && !code.equals("")) {
                    if (nomineecontactno != null && !nomineecontactno.equals("")) {
                        nomineecontactno = "+" + code + " " + nomineecontactno;
                    }
                }
                list.add(new CrewdbInfo(nomineedetailId, nomineename, nomineecontactno, nomineerelation, status, code, vstatus));
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return list;
    }
}
