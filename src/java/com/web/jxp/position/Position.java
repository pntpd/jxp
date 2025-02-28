package com.web.jxp.position;

import com.mysql.jdbc.Statement;
import com.web.jxp.base.Base;
import com.web.jxp.base.Validate;
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
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

public class Position extends Base {

    private static final Logger logger = Logger.getLogger(Class.class.getName());
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    public ArrayList getPositionByName(String search, int next, int count) {
        ArrayList positions = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT t_position.i_positionid, t_position.s_name , t_grade.s_name , t_assettype.s_name, t_degree.s_name, ");
        sb.append("t_rotation.i_noofdays, t_position.i_status ");
        sb.append("FROM t_position ");
        sb.append("LEFT JOIN t_grade ON (t_position.i_gradeid = t_grade.i_gradeid) ");
        sb.append("LEFT JOIN t_assettype ON (t_position.i_assettypeid = t_assettype.i_assettypeid) ");
        sb.append("LEFT JOIN t_degree ON (t_position.i_degreeid = t_degree.i_degreeid) ");
        sb.append("LEFT JOIN t_rotation ON (t_position.i_rotationid = t_rotation.i_rotationid) ");
        sb.append("WHERE 0 = 0 ");
        if (search != null && !search.equals("")) 
        {
            sb.append("AND (t_position.s_name LIKE ? OR t_grade.s_name =? OR t_assettype.s_name =? OR ");
            sb.append(" t_rotation.i_noofdays =? OR t_degree.s_name LIKE ? ) ");
        }
        sb.append(" ORDER BY t_position.i_status, t_position.s_name ");
        if (count > 0)
        {
            sb.append(" LIMIT ").append(next * count).append(", ").append(count);
        }
        String query = (sb.toString()).intern();
        sb.setLength(0);

        sb.append("SELECT COUNT(1) FROM t_position ");
        sb.append("LEFT JOIN t_grade ON (t_position.i_gradeid = t_grade.i_gradeid) ");
        sb.append("LEFT JOIN t_assettype ON (t_position.i_assettypeid = t_assettype.i_assettypeid) ");
        sb.append("LEFT JOIN t_degree ON (t_position.i_degreeid = t_degree.i_degreeid) ");
        sb.append("LEFT JOIN t_rotation ON (t_position.i_rotationid = t_rotation.i_rotationid) ");
        sb.append("WHERE 0 = 0 ");
        if (search != null && !search.equals("")) {
            sb.append("AND (t_position.s_name LIKE ? OR t_grade.s_name =? OR t_assettype.s_name =? OR ");
            sb.append(" t_rotation.i_noofdays =? OR t_degree.s_name LIKE ? ) ");
        }
        String countquery = (sb.toString()).intern();
        sb.setLength(0);
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            int scc = 0;
            if (search != null && !search.equals(""))
            {
                pstmt.setString(++scc, "%" + (search) + "%");
                pstmt.setString(++scc, search);
                pstmt.setString(++scc, search);
                pstmt.setString(++scc, search);
                pstmt.setString(++scc, "%" + (search) + "%");
            }
            logger.info("getPositionByName :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String positiontitle, grade, asset, minqualification, rotation;
            int positionId, status;
            while (rs.next()) 
            {
                positionId = rs.getInt(1);
                positiontitle = rs.getString(2) != null ? rs.getString(2) : "";
                grade = rs.getString(3) != null ? rs.getString(3) : "";
                asset = rs.getString(4) != null ? rs.getString(4) : "";
                minqualification = rs.getString(5) != null ? rs.getString(5) : "";
                rotation = rs.getString(6) != null ? rs.getString(6) : "";
                status = rs.getInt(7);
                positions.add(new PositionInfo(positionId, positiontitle, grade, asset, minqualification, rotation, status, 0));
            }
            rs.close();

            pstmt = conn.prepareStatement(countquery);
            scc = 0;
            if (search != null && !search.equals("")) 
            {
                pstmt.setString(++scc, "%" + (search) + "%");
                pstmt.setString(++scc, search);
                pstmt.setString(++scc, search);
                pstmt.setString(++scc, search);
                pstmt.setString(++scc, "%" + (search) + "%");
            }
            print(this, "getPositionByName :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                positionId = rs.getInt(1);
                positions.add(new PositionInfo(positionId, "", "", "", "", "", 0, 0));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return positions;
    }

    public ArrayList getFinalRecord(ArrayList l, String colId, int tp) {
        ArrayList list = null;
        HashMap record = null;
        PositionInfo info;
        int total = 0;
        if (l != null) {
            total = l.size();
        }
        try {
            if (total > 0) {
                record = new HashMap();
                int i;
                for (i = 0; i < total; i++) {
                    info = (PositionInfo) l.get(i);
                    record.put(getInfoValue(info, colId), info);
                }
            }
            Map map = sortByName(record, tp);
            Iterator it = map.keySet().iterator();
            list = new ArrayList();
            PositionInfo rInfo;
            while (it.hasNext()) {
                String key = (String) it.next();
                int i;
                for (i = 0; i < total; i++) {
                    rInfo = (PositionInfo) l.get(i);
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
    public String getInfoValue(PositionInfo info, String i) {
        String infoval = "";
        if (i != null && i.equals("1")) {
            infoval = info.getPositiontitle() != null ? info.getPositiontitle() : "";
        } else if (i != null && i.equals("2")) {
            infoval = info.getGrade() != null ? info.getGrade() : "";
        } else if (i != null && i.equals("3")) {
            infoval = info.getAsset() != null ? info.getAsset() : "";
        } else if (i != null && i.equals("4")) {
            infoval = info.getMinqualification() != null ? info.getMinqualification() : "";
        }
        return infoval;
    }

    public PositionInfo getPositionDetailById(int positionId) {
        PositionInfo info = null;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT s_name, i_gradeid, i_assettypeid, i_rotationid, i_hoursofworkid, i_languageid, s_gender, i_countryid, d_renumerationmin, ");
        sb.append("d_renumerationmax, i_degreeid, i_qualificationtypeid, d_genworkexprangemin, d_genworkexprangemax, d_posworkexprangemin, d_posworkexprangemax, ");
        sb.append("s_description, s_position1, s_position2, s_position3, i_status, i_userid, i_currencyid, i_rotationoverstay FROM t_position ");
        sb.append("WHERE i_positionid = ? ");
        String query = sb.toString().intern();
        sb.setLength(0);
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, positionId);
            print(this, "getPositionDetailById :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String positiontitle, gender, description, position1, position2, position3;
            double renumerationmin, renumerationmax, genworkexprangemin, genworkexprangemax, posworkexprangemin,
                    posworkexprangemax;
            int assettypeId, gradeId, minqualificationId, qualificationtypeId, rotationId, hoursofworkId, languageId,
                    nationalityId, uId, status, currencyId, overstay;
            while (rs.next()) 
            {
                positiontitle = rs.getString(1) != null ? rs.getString(1): "";
                gradeId = rs.getInt(2);
                assettypeId = rs.getInt(3);
                rotationId = rs.getInt(4);
                hoursofworkId = rs.getInt(5);
                languageId = rs.getInt(6);
                gender = rs.getString(7) != null ? rs.getString(7): "";
                nationalityId = rs.getInt(8);
                renumerationmin = rs.getDouble(9);
                renumerationmax = rs.getDouble(10);
                minqualificationId = rs.getInt(11);
                qualificationtypeId = rs.getInt(12);
                genworkexprangemin = rs.getDouble(13);
                genworkexprangemax = rs.getDouble(14);
                posworkexprangemin = rs.getDouble(15);
                posworkexprangemax = rs.getDouble(16);
                description = rs.getString(17) != null ? rs.getString(17): "";
                position1 = rs.getString(18) != null ? rs.getString(18): "";
                position2 = rs.getString(19) != null ? rs.getString(19): "";
                position3 = rs.getString(20) != null ? rs.getString(20): "";
                status = rs.getInt(21);
                uId = rs.getInt(22);
                currencyId = rs.getInt(23);
                overstay = rs.getInt(24);

                info = new PositionInfo(positionId, positiontitle, gender, renumerationmin, renumerationmax,
                        genworkexprangemin, genworkexprangemax, posworkexprangemin, posworkexprangemax, description,
                        position1, position2, position3, assettypeId, gradeId, minqualificationId, qualificationtypeId,
                        rotationId, hoursofworkId, languageId, nationalityId, status, uId, currencyId, overstay, 0);
            }
        } catch (Exception exception) {
            print(this, "getPositionDetailById :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return info;
    }

    public PositionInfo getPositionDetailByIdforDetail(int positionId) 
    {
        PositionInfo info = null;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT ps.s_name, t_grade.s_name, t_assettype.s_name, t_rotation.i_noofdays, t_hoursofwork.i_noofhours, t_language.s_name, ps.s_gender, ");
        sb.append("t_country.s_nationality, ps.d_renumerationmin, ps.d_renumerationmax, t_degree.s_name, t_qualificationtype.s_name, ps.d_genworkexprangemin, ");
        sb.append("ps.d_genworkexprangemax, ps.d_posworkexprangemin, ps.d_posworkexprangemax, ps.s_description, ps.s_position1, ps.s_position2, ps.s_position3, ");
        sb.append("ps.i_status, t_currency.s_name, ps.i_rotationoverstay ");
        sb.append("FROM t_position AS ps ");
        sb.append("LEFT JOIN t_grade ON (ps.i_gradeid = t_grade.i_gradeid) ");
        sb.append("LEFT JOIN t_assettype ON (ps.i_assettypeid = t_assettype.i_assettypeid) ");
        sb.append("LEFT JOIN t_rotation ON (ps.i_rotationid = t_rotation.i_rotationid) ");
        sb.append("LEFT JOIN t_hoursofwork ON (ps.i_hoursofworkid = t_hoursofwork.i_hoursofworkid) ");
        sb.append("LEFT JOIN t_language ON (ps.i_languageid = t_language.i_languageid) ");
        sb.append("LEFT JOIN t_country ON (ps.i_countryid = t_country.i_countryid) ");
        sb.append("LEFT JOIN t_degree ON (ps.i_degreeid = t_degree.i_degreeid) ");
        sb.append("LEFT JOIN t_currency ON (ps.i_currencyid = t_currency.i_currencyid) ");
        sb.append("LEFT JOIN t_qualificationtype ON (ps.i_qualificationtypeid = t_qualificationtype.i_qualificationtypeid) ");
        sb.append("WHERE ps.i_positionid = ? ");
        String query = sb.toString().intern();
        sb.setLength(0);
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, positionId);
            print(this, "getPositionDetailByIdforDetail :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String positiontitle, gender, description, position1, position2, position3, assettype, grade, currency,
                    minqualification, qualificationtype, rotation, hoursofwork, language, nationality;
            double renumerationmin, renumerationmax, genworkexprangemin, genworkexprangemax, posworkexprangemin, posworkexprangemax;
            int status, rotationoverstay;
            while (rs.next())
            {
                positiontitle = rs.getString(1) != null ? rs.getString(1): "";
                grade = rs.getString(2) != null ? rs.getString(2): "";
                assettype = rs.getString(3) != null ? rs.getString(3): "";
                rotation = rs.getString(4) != null ? rs.getString(4): "";
                hoursofwork = rs.getString(5) != null ? rs.getString(5): "";
                language = rs.getString(6) != null ? rs.getString(6): "";
                gender = rs.getString(7) != null ? rs.getString(7): "";
                nationality = rs.getString(8) != null ? rs.getString(8): "";
                renumerationmin = rs.getDouble(9);
                renumerationmax = rs.getDouble(10);
                minqualification = rs.getString(11) != null ? rs.getString(11): "";
                qualificationtype = rs.getString(12) != null ? rs.getString(12): "";
                genworkexprangemin = rs.getDouble(13);
                genworkexprangemax = rs.getDouble(14);
                posworkexprangemin = rs.getDouble(15);
                posworkexprangemax = rs.getDouble(16);
                description = rs.getString(17) != null ? rs.getString(17): "";
                position1 = rs.getString(18) != null ? rs.getString(18): "";
                position2 = rs.getString(19) != null ? rs.getString(19): "";
                position3 = rs.getString(20) != null ? rs.getString(20): "";
                status = rs.getInt(21);
                currency = rs.getString(22) != null ? rs.getString(22) : "";
                rotationoverstay = rs.getInt(23);
                info = new PositionInfo(positionId, positiontitle, grade, assettype, rotation, hoursofwork, language, gender,
                        nationality, renumerationmin, renumerationmax, minqualification, qualificationtype, genworkexprangemin, genworkexprangemax,
                        posworkexprangemin, posworkexprangemax, description, position1, position2, position3, status, 0, currency, rotationoverstay);
            }
            rs.close();
        } catch (Exception exception) {
            print(this, "getPositionDetailByIdforDetail :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return info;
    }

    public int createPosition(PositionInfo info) {
        int positionId = 0;
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("INSERT INTO t_position ");
            sb.append("(s_name, i_gradeid, i_assettypeid, i_rotationid, i_hoursofworkid, i_languageid, s_gender, i_countryid,"
                    + " d_renumerationmin, d_renumerationmax, i_degreeid, i_qualificationtypeid, d_genworkexprangemin,"
                    + " d_genworkexprangemax, d_posworkexprangemin, d_posworkexprangemax, s_description, s_position1, s_position2,"
                    + " s_position3, i_status, i_userid, ts_regdate, ts_moddate, i_currencyid, i_rotationoverstay) ");
            sb.append("VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,    ?)");
            String query = (sb.toString()).intern();
            sb.setLength(0);

            conn = getConnection();
            pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            int scc = 0;
            pstmt.setString(++scc, (info.getPositiontitle()));
            pstmt.setInt(++scc, (info.getGradeId()));
            pstmt.setInt(++scc, (info.getAssettypeId()));
            pstmt.setInt(++scc, (info.getRotationId()));
            pstmt.setInt(++scc, (info.getHoursofworkId()));
            pstmt.setInt(++scc, (info.getLanguageId()));
            pstmt.setString(++scc, (info.getGender()));
            pstmt.setInt(++scc, (info.getNationalityId()));
            pstmt.setDouble(++scc, (info.getRenumerationmin()));
            pstmt.setDouble(++scc, (info.getRenumerationmax()));
            pstmt.setInt(++scc, (info.getMinqualificationId()));
            pstmt.setInt(++scc, (info.getQualificationtypeId()));
            pstmt.setDouble(++scc, (info.getGenworkexprangemin()));
            pstmt.setDouble(++scc, (info.getGenworkexprangemax()));
            pstmt.setDouble(++scc, (info.getPosworkexprangemin()));
            pstmt.setDouble(++scc, (info.getPosworkexprangemax()));
            pstmt.setString(++scc, (info.getDescription()));
            pstmt.setString(++scc, (info.getPosition1()));
            pstmt.setString(++scc, (info.getPosition2()));
            pstmt.setString(++scc, (info.getPosition3()));
            pstmt.setInt(++scc, info.getStatus());
            pstmt.setInt(++scc, info.getUserId());
            pstmt.setString(++scc, currDate1());
            pstmt.setString(++scc, currDate1());
            pstmt.setInt(++scc, (info.getCurrencyId()));
            pstmt.setInt(++scc, (info.getOverstaystart()));
            print(this, "createPosition :: " + pstmt.toString());
            pstmt.executeUpdate();
            rs = pstmt.getGeneratedKeys();
            while (rs.next()) {
                positionId = rs.getInt(1);
            }
            createjson(conn);
        } catch (Exception exception) {
            print(this, "createPosition :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return positionId;
    }

    public int updatePosition(PositionInfo info) {
        int cc = 0;
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("UPDATE t_position SET ");
            sb.append("s_name = ?, ");
            sb.append("i_gradeid = ?, ");
            sb.append("i_assettypeid = ?, ");
            sb.append("i_rotationid = ?, ");
            sb.append("i_hoursofworkid = ?, ");
            sb.append("i_languageid = ?, ");
            sb.append("s_gender = ?, ");
            sb.append("i_countryid = ?, ");
            sb.append("d_renumerationmin = ?, ");
            sb.append("d_renumerationmax = ?, ");
            sb.append("i_degreeid = ?, ");
            sb.append("i_qualificationtypeid = ?, ");
            sb.append("d_genworkexprangemin = ?, ");
            sb.append("d_genworkexprangemax = ?, ");
            sb.append("d_posworkexprangemin = ?, ");
            sb.append("d_posworkexprangemax = ?, ");
            sb.append("s_description = ?, ");
            sb.append("s_position1 = ?, ");
            sb.append("s_position2 = ?, ");
            sb.append("s_position3 = ?, ");
            sb.append("i_status = ?, ");
            sb.append("i_userid = ?, ");
            sb.append("ts_moddate = ?, ");
            sb.append("i_currencyid = ?, ");
            sb.append("i_rotationoverstay = ? ");
            sb.append("WHERE i_positionid = ? ");
            String query = (sb.toString()).intern();
            sb.setLength(0);

            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            int scc = 0;
            pstmt.setString(++scc, info.getPositiontitle());
            pstmt.setInt(++scc, info.getGradeId());
            pstmt.setInt(++scc, info.getAssettypeId());
            pstmt.setInt(++scc, info.getRotationId());
            pstmt.setInt(++scc, info.getHoursofworkId());
            pstmt.setInt(++scc, info.getLanguageId());
            pstmt.setString(++scc, info.getGender());
            pstmt.setInt(++scc, info.getNationalityId());
            pstmt.setDouble(++scc, info.getRenumerationmin());
            pstmt.setDouble(++scc, info.getRenumerationmax());
            pstmt.setInt(++scc, info.getMinqualificationId());
            pstmt.setInt(++scc, info.getQualificationtypeId());
            pstmt.setDouble(++scc, info.getGenworkexprangemin());
            pstmt.setDouble(++scc, info.getGenworkexprangemax());
            pstmt.setDouble(++scc, info.getPosworkexprangemin());
            pstmt.setDouble(++scc, info.getPosworkexprangemax());
            pstmt.setString(++scc, info.getDescription());
            pstmt.setString(++scc, info.getPosition1());
            pstmt.setString(++scc, info.getPosition2());
            pstmt.setString(++scc, info.getPosition3());
            pstmt.setInt(++scc, info.getStatus());
            pstmt.setInt(++scc, info.getUserId());
            pstmt.setString(++scc, currDate1());
            pstmt.setInt(++scc, (info.getCurrencyId()));
            pstmt.setInt(++scc, (info.getOverstaystart()));
            pstmt.setInt(++scc, info.getPositionId());
            print(this, "updatePosition :: " + pstmt.toString());
            cc = pstmt.executeUpdate();
            createjson(conn);
        } catch (Exception exception) {
            print(this, "updatePosition :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return cc;
    }

    public int checkDuplicacy(int positionId, int gradeId, String name, int assettypeId) {
        int ck = 0;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT i_positionid FROM t_position WHERE s_name = ? AND i_gradeid =? AND i_assettypeid =? AND i_status IN (1, 2)");
        if (positionId > 0) {
            sb.append(" AND i_positionid != ? ");
        }
        String query = (sb.toString()).intern();
        sb.setLength(0);
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            int scc = 0;
            pstmt.setString(++scc, name);
            pstmt.setInt(++scc, gradeId);
            pstmt.setInt(++scc, assettypeId);
            if (positionId > 0) {
                pstmt.setInt(++scc, positionId);
            }
            print(this, "checkDuplicacy :: " + pstmt.toString());
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

    public Collection getPositions() 
    {
        Collection coll = new LinkedList();
        String query = ("SELECT i_positionid, s_name FROM t_position WHERE i_status = 1 ORDER BY s_name").intern();
        coll.add(new PositionInfo(-1, "- Select -"));
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
                coll.add(new PositionInfo(refId, refName));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return coll;
    }

    public int deletePosition(int positionId, int userId, int status, String ipAddrStr, String iplocal) {
        int cc = 0;
        try {
            conn = getConnection();
            StringBuilder sb = new StringBuilder();
            sb.append("UPDATE t_position SET ");
            sb.append("i_status = ?, ");
            sb.append("i_userid = ?, ");
            sb.append("ts_moddate = ? ");
            sb.append("WHERE i_positionid = ? ");
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
            pstmt.setInt(++scc, positionId);

            cc = pstmt.executeUpdate();
            createjson(conn);
        } catch (Exception exception) {
            print(this, "deletePosition :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        createHistoryAccess(null, userId, ipAddrStr, iplocal, "Data Deleted", 12, positionId);
        return cc;
    }

    public ArrayList getPositionBenefitDetailByIdforList(int positionId) {
        ArrayList posBenefit = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT t_positionbenefit.i_benefitid, t_positionbenefit.s_info, t_benefit.s_name, t_positionbenefit.i_status, ");
        sb.append("t_positionbenefit.i_positionbenefitid ");
        sb.append("FROM t_positionbenefit ");
        sb.append("LEFT JOIN t_benefit ON t_positionbenefit.i_benefitid = t_benefit.i_benefitid ");
        sb.append("WHERE t_positionbenefit.i_positionid = ? ");
        String query = sb.toString().intern();
        sb.setLength(0);
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, positionId);
            print(this, "getPositionBenefitDetailByIdforList :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String positionbenefitinfo, benifitname, codenum;
            int benefitId, status, positionbenefitId;
            while (rs.next()) 
            {
                benefitId = rs.getInt(1);
                positionbenefitinfo = rs.getString(2) != null ? rs.getString(2): "";
                benifitname = rs.getString(3) != null ? rs.getString(3): "";
                status = rs.getInt(4);
                positionbenefitId = rs.getInt(5);
                codenum = changeNum(benefitId, 3);
                posBenefit.add(new PositionInfo(positionbenefitId, positionId, benefitId, positionbenefitinfo, benifitname, codenum, status, 0));
            }
            rs.close();
        } catch (Exception exception) {
            print(this, "getPositionBenefitDetailByIdforList :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return posBenefit;
    }

    public ArrayList getListForExcel(String search) {
        ArrayList list = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT t_position.i_positionid, t_position.s_name , t_grade.s_name , t_assettype.s_name, t_degree.s_name,"
                + " t_rotation.i_noofdays, t_position.i_status ");
        sb.append("FROM t_position ");
        sb.append("LEFT JOIN t_grade ON t_position.i_gradeid = t_grade.i_gradeid ");
        sb.append("LEFT JOIN t_assettype ON t_position.i_assettypeid = t_assettype.i_assettypeid ");
        sb.append("LEFT JOIN t_degree ON t_position.i_degreeid = t_degree.i_degreeid ");
        sb.append("LEFT JOIN t_rotation ON t_position.i_rotationid = t_rotation.i_rotationid ");
        sb.append("WHERE 0 = 0 ");
        if (search != null && !search.equals("")) {
            sb.append("AND (t_position.s_name LIKE ? OR t_grade.s_name =? OR t_assettype.s_name =? OR "
                    + " t_rotation.i_noofdays =? OR t_degree.s_name LIKE ?) ");
        }
        sb.append(" ORDER BY t_position.i_status, t_position.s_name ");
        String query = sb.toString().intern();
        sb.setLength(0);
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            int scc = 0;
            if (search != null && !search.equals("")) {
                pstmt.setString(++scc, "%" + (search) + "%");
                pstmt.setString(++scc, search);
                pstmt.setString(++scc, search);
                pstmt.setString(++scc, search);
                pstmt.setString(++scc, "%" + (search) + "%");
            }
            Common.print(this, "getListForExcel :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String name, grade, asset, qualification, rotation;
            int positionId, status;
            while (rs.next()) 
            {
                positionId = rs.getInt(1);
                name = rs.getString(2) != null ? rs.getString(2) : "";
                grade = rs.getString(3) != null ? rs.getString(3) : "";
                asset = rs.getString(4) != null ? rs.getString(4) : "";
                qualification = rs.getString(5) != null ? rs.getString(5) : "";
                rotation = rs.getString(6) != null ? rs.getString(6) : "";
                status = rs.getInt(7);
                list.add(new PositionInfo(positionId, name, grade, asset, qualification, rotation, status, 0));
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

    public void createjson(Connection conn) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT i_positionid, t_position.s_name, t_grade.s_name, t_position.i_assettypeid FROM t_position left join t_grade on (t_position.i_gradeid = t_grade.i_gradeid) WHERE t_position.i_status = 1 ");
        sb.append("ORDER BY t_position.s_name, t_grade.s_name ");
        String query = (sb.toString()).intern();
        sb.setLength(0);
        try
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            rs = pstmt.executeQuery();
            int positionId, assettypeId;
            String name, gname;
            JSONArray jarr = new JSONArray();
            while (rs.next()) 
            {
                positionId = rs.getInt(1);
                name = rs.getString(2) != null ? rs.getString(2) : "";
                gname = rs.getString(3) != null ? rs.getString(3) : "";
                assettypeId = rs.getInt(4);
                JSONObject jobj = new JSONObject();
                jobj.put("id", positionId);
                jobj.put("name", name);
                jobj.put("gname", gname);
                jobj.put("assettypeid", assettypeId);
                jarr.put(jobj);
            }
            rs.close();
            String str = jarr.toString();
            String filePath = getMainPath("json_path");
            writeHTMLFile(str, filePath, "position.json");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
    }

    public Collection getPositiontypes() {
        Collection list = new LinkedList();
        list.add(new PositionInfo(-1, "- Select -"));
        String xml_path = getMainPath("json_path");
        String str = readHTMLFile("position.json", xml_path);
        JSONArray arr = new JSONArray(str);
        if (arr != null) {
            int len = arr.length();
            for (int i = 0; i < len; i++) {
                JSONObject jobj = arr.optJSONObject(i);
                if (jobj != null) {
                    String pname = jobj.optString("name");
                    String gname = jobj.optString("gname");
                    if (gname != null && !gname.equals("")) {
                        pname += " - " + gname;
                    }
                    list.add(new PositionInfo(jobj.optInt("id"), pname));
                }
            }
        }
        return list;
    }

    public Collection getBenefitType() {
        Collection coll = new LinkedList();
        String query = ("SELECT i_benefittypeid, s_name FROM t_benefittype where i_status = 1 order by s_name").intern();
        coll.add(new PositionInfo(-1, "- Select -"));
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
                coll.add(new PositionInfo(refId, refName));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return coll;
    }

    public String getMaxBenefitQuestionId() {
        String code = "";
        String query = ("SELECT Max(i_benefitid) FROM t_benefit").intern();
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            rs = pstmt.executeQuery();
            int count = 0;
            while (rs.next()) {
                count = rs.getInt(1);
            }
            count = count + 1;
            code = changeNum(count, 3);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return code;
    }

    public int checkBenefitDuplicacy(int benefitId, String name) {
        int ck = 0;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT i_benefitid FROM t_benefit WHERE (s_name = ?) AND i_status IN (1, 2)");
        if (benefitId > 0) {
            sb.append(" AND i_benefitid != ? ");
        }
        String query = (sb.toString()).intern();
        sb.setLength(0);
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            int scc = 0;
            pstmt.setString(++scc, name);
            if (benefitId > 0) {
                pstmt.setInt(++scc, benefitId);
            }
            //print(this,"checkBenefitDuplicacy :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ck = 1;
            }
        } catch (Exception exception) {
            print(this, "checkBenefitDuplicacy :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return ck;
    }

    public int createBenefitQuestion(PositionInfo info) {
        int benefitquestionId = 0;
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("INSERT INTO t_benefit ");
            sb.append("(i_type, s_name, i_status, i_userid, ts_regdate, ts_moddate) ");
            sb.append("VALUES (?, ?, ?, ?, ?, ?)");
            String query = (sb.toString()).intern();
            sb.setLength(0);

            conn = getConnection();
            pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            int scc = 0;
            pstmt.setInt(++scc, (info.getBenefittypeId()));
            pstmt.setString(++scc, (info.getBenifitname()));
            pstmt.setInt(++scc, info.getStatus());
            pstmt.setInt(++scc, info.getUserId());
            pstmt.setString(++scc, currDate1());
            pstmt.setString(++scc, currDate1());
            print(this, "createPosition :: " + pstmt.toString());
            pstmt.executeUpdate();
            rs = pstmt.getGeneratedKeys();
            while (rs.next()) {
                benefitquestionId = rs.getInt(1);
            }
            createjson(conn);
        } catch (Exception exception) {
            print(this, "createPosition :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return benefitquestionId;
    }

    public ArrayList getEmployeePositionBenefitDetailByIdforEdit(int positionId) {
        ArrayList posBenefit = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT t_benefit.i_benefitid, t_benefit.s_name, t_benefit.i_type, t_positionbenefit.i_benefitquestionid, "
                + " t_positionbenefit.i_currencyid, t_positionbenefit.s_info,t_positionbenefit.i_status ");
        sb.append("FROM t_benefit ");
        sb.append("LEFT JOIN t_positionbenefit ON (t_benefit.i_benefitid = t_positionbenefit.i_benefitid and t_positionbenefit.i_positionid = ?) where t_benefit.i_status = 1 order by t_benefit.s_name");
        String query = sb.toString().intern();
        sb.setLength(0);
        try
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, positionId);
            print(this, "getEmployeePositionBenefitDetailByIdforEdit :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String positionbenefitinfo, benefitname, codenum;
            int benefitId, benefittype, benefitquestionId, currencyId, status;
            while (rs.next()) 
            {
                benefitId = rs.getInt(1);
                benefitname = rs.getString(2) != null ? rs.getString(2): "";
                benefittype = rs.getInt(3);
                benefitquestionId = rs.getInt(4);
                currencyId = rs.getInt(5);
                positionbenefitinfo = rs.getString(6) != null ? rs.getString(6): "";
                status = rs.getInt(7);
                if (status <= 0) {
                    status = 1;
                }
                codenum = changeNum(benefitId, 3);

                if (benefittype == 3) {
                    benefitquestionId = currencyId;
                }
                posBenefit.add(new PositionInfo(benefitId, benefitname, benefittype, benefitquestionId, positionbenefitinfo, codenum, status));
            }
            rs.close();
        } catch (Exception exception) {
            print(this, "getEmployeePositionBenefitDetailByIdforEdit :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return posBenefit;
    }

    public ArrayList getBenefitQuestionList() 
    {
        ArrayList list = new ArrayList();
        String query = ("SELECT i_benefitquestionid, i_type, s_name FROM t_benefitquestion WHERE i_status = 1 ORDER BY s_name ");
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);

            logger.info("getBenefitQuestionList :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String ddlLabel;
            int ddlValue, type;
            while (rs.next())
            {
                ddlValue = rs.getInt(1);
                type = rs.getInt(2);
                ddlLabel = rs.getString(3) != null ? rs.getString(3) : "";
                list.add(new PositionInfo(ddlValue, type, ddlLabel));
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return list;
    }

    public String getDDLFromList(ArrayList list, int type, int currency, int val) {
        String s = "";
        int size = list.size();
        StringBuilder sb = new StringBuilder();
        if (currency > 0) {
            if (size > 0) {
                for (int i = 0; i < size; i++) {
                    PositionInfo info = (PositionInfo) list.get(i);
                    if (val == info.getDdlValue()) {
                        sb.append("<option value='" + info.getDdlValue() + "' selected>" + (info.getDdlLabel()) + "</option>");
                    } else {
                        sb.append("<option value='" + info.getDdlValue() + "'>" + (info.getDdlLabel()) + "</option>");
                    }
                }
                s = sb.toString();
                sb.setLength(0);
            }
        } else if (size > 0) {
            sb.append("<option value='-1'>- Select -</option>");
            for (int i = 0; i < size; i++) {
                PositionInfo info = (PositionInfo) list.get(i);
                if (info != null && info.getBenefittypeId() == type) {
                    if (val == info.getDdlValue()) {
                        sb.append("<option value='" + info.getDdlValue() + "' selected>" + (info.getDdlLabel()) + "</option>");
                    } else {
                        sb.append("<option value='" + info.getDdlValue() + "'>" + (info.getDdlLabel()) + "</option>");
                    }
                }
            }
            s = sb.toString();
            sb.setLength(0);
        }
        return s;
    }

    public int createPositionBenefit(int positionId, int benefitId[], int benefittype[], int benefitquestionId[],
            String description[], int userId, int statusbenefit[]) 
    {
        Validate vobj = new Validate();
        int cc = 0;
        try 
        {
            StringBuilder sb = new StringBuilder();
            sb.append("INSERT INTO t_positionbenefit ");
            sb.append("(i_positionid, i_benefitid, i_benefitquestionid, i_currencyid, s_info, ");
            sb.append("i_status, i_userid, ts_regdate, ts_moddate) ");
            sb.append("VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");
            String query = (sb.toString()).intern();
            sb.setLength(0);
            conn = getConnection();
            String delqery = "DELETE FROM t_positionbenefit WHERE i_positionid = ?";
            pstmt = conn.prepareStatement(delqery);
            pstmt.setInt(1, positionId);
            pstmt.executeUpdate();

            pstmt = conn.prepareStatement(query);
            int size = benefitId.length;
            int scc = 0;
            for (int i = 0; i < size; i++) 
            {
                if (benefitquestionId[i] > 0) 
                {
                    scc = 0;
                    pstmt.setInt(++scc, positionId);
                    pstmt.setInt(++scc, benefitId[i]);
                    if (benefittype[i] == 3) {
                        pstmt.setInt(++scc, 0);
                        pstmt.setInt(++scc, benefitquestionId[i]);
                    } else {
                        pstmt.setInt(++scc, benefitquestionId[i]);
                        pstmt.setInt(++scc, 0);
                    }
                    pstmt.setString(++scc, vobj.replacedesc(description[i]));
                    pstmt.setInt(++scc, statusbenefit[i]);
                    pstmt.setInt(++scc, userId);
                    pstmt.setString(++scc, currDate1());
                    pstmt.setString(++scc, currDate1());
                    print(this, "createPositionBenefit :: " + pstmt.toString());
                    int inserted = pstmt.executeUpdate();
                    if (inserted > 0) {
                        cc++;
                    }
                }
            }
        } catch (Exception exception) {
            print(this, "createPositionBenefit :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return cc;
    }

    public int deleteBenefit(int positionbenefitId, int userId, int status, String ipAddrStr, String iplocal)
    {
        int cc = 0;
        try
        {
            conn = getConnection();
            StringBuilder sb = new StringBuilder();
            sb.append("update t_positionbenefit set ");
            sb.append("i_status = ?, ");
            sb.append("i_userid = ?, ");
            sb.append("ts_moddate = ? ");
            sb.append("where i_positionbenefitid = ? ");
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
            pstmt.setInt(++scc, positionbenefitId);

            logger.info("Query :" + pstmt.toString());
            cc = pstmt.executeUpdate();
        } catch (Exception exception) {
            print(this, "deletePositionBenefit :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        createHistoryAccess(null, userId, ipAddrStr, iplocal, "Data Deleted", 12, positionbenefitId);
        return cc;
    }

    public ArrayList getAssessmentList(int positionId) 
    {
        ArrayList list = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("select t_positionassessment.i_positionassessmentid, t_assessment.i_assessmentid, t_assessment.s_name, t_positionassessment.i_minscore, t_positionassessment.i_passingflag, t2.names, ");
        sb.append("t_positionassessment.i_status from t_positionassessment ");
        sb.append("left join t_assessment on (t_assessment.i_assessmentid = t_positionassessment.i_assessmentid) ");
        sb.append("left join (select t_assessment.i_assessmentid, GROUP_CONCAT(t1.s_name order by t1.s_name SEPARATOR ', ') as names from t_assessment ");
        sb.append("LEFT JOIN t_assessmentparameter as t1 on find_in_set(t1.i_assessmentparameterid, t_assessment.s_parameterids) ");
        sb.append("group by t_assessment.i_assessmentid) as t2 on (t2.i_assessmentid = t_assessment.i_assessmentid) ");
        sb.append("where t_positionassessment.i_positionid = ? ");
        sb.append("order by t_positionassessment.i_status, t_assessment.s_name ");
        String query = (sb.toString()).intern();
        sb.setLength(0);
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, positionId);
            logger.log(Level.INFO, "getAssessmentList :: {0}", pstmt.toString());
            rs = pstmt.executeQuery();
            String assessmentName, parameter;
            int assessmentId, status, minScore, passingFlag, assessmentDetailId;
            while (rs.next()) 
            {
                assessmentDetailId = rs.getInt(1);
                assessmentId = rs.getInt(2);
                assessmentName = rs.getString(3) != null ? rs.getString(3) : "";
                minScore = rs.getInt(4);
                passingFlag = rs.getInt(5);
                parameter = rs.getString(6) != null ? rs.getString(6) : "";
                status = rs.getInt(7);
                list.add(new PositionInfo(assessmentDetailId, assessmentId, assessmentName, minScore, passingFlag, parameter, status));
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return list;
    }

    public int deleteAssessment(int positionId, int assessmentDetailId, int userId, int status, String ipAddrStr, String iplocal) {
        int cc = 0;
        try
        {
            conn = getConnection();
            StringBuilder sb = new StringBuilder();
            sb.append("update t_positionassessment set ");
            sb.append("i_status = ?, ");
            sb.append("i_userid = ?, ");
            sb.append("ts_moddate = ? ");
            sb.append("where i_positionassessmentid = ? and i_positionid = ? ");
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
            pstmt.setInt(++scc, assessmentDetailId);
            pstmt.setInt(++scc, positionId);
            cc = pstmt.executeUpdate();
        } catch (Exception exception) {
            print(this, "deletePositionAssessment :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        createHistoryAccess(null, userId, ipAddrStr, iplocal, "Data Deleted", 12, assessmentDetailId);
        return cc;
    }

    public PositionInfo getAssessmentDetailByPositionId(int assessmentDetailId) {
        PositionInfo info = null;

        String query = "select i_assessmentid, i_minscore, i_passingflag from t_positionassessment where i_positionassessmentid = ? ";
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, assessmentDetailId);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                int assessmentId = rs.getInt(1);
                int minScore = rs.getInt(2);
                int passingFlag = rs.getInt(3);
                info = new PositionInfo(assessmentDetailId, assessmentId, minScore, passingFlag);
            }
            rs.close();
            pstmt.close();

        } catch (Exception exception) {
            print(this, "getAssessmentDetailByPositionId :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return info;
    }

    public int insertAssessmentDetail(PositionInfo info, int positionId, int userId) {
        int assessmentId = 0;
        try 
        {
            StringBuilder sb = new StringBuilder();
            sb.append("insert into t_positionassessment  ");
            sb.append("(i_positionid, i_assessmentid, i_minscore, i_passingflag ,i_status, ts_regdate, ts_moddate) ");
            sb.append("values (?, ?, ?, ?, ?, ?, ?)");
            String query = (sb.toString()).intern();
            sb.setLength(0);
            conn = getConnection();
            pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            logger.log(Level.INFO, "t_positionassessment :: {0}", pstmt.toString());
            int scc = 0;
            pstmt.setInt(++scc, positionId);
            pstmt.setInt(++scc, info.getAssessmentId());
            pstmt.setInt(++scc, info.getMinScore());
            pstmt.setInt(++scc, info.getPassingFlag());
            pstmt.setInt(++scc, info.getStatus());
            pstmt.setString(++scc, currDate1());
            pstmt.setString(++scc, currDate1());
            pstmt.executeUpdate();
            rs = pstmt.getGeneratedKeys();
            while (rs.next()) {
                assessmentId = rs.getInt(1);
            }
        } catch (Exception exception) {
            print(this, "createAssessmentDetail :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return assessmentId;
    }

    public int updateAssessmentDetail(PositionInfo info, int userId, int positionId, int assessmentDetailId) {
        StringBuilder sb = new StringBuilder();
        int cc = 0;
        try 
        {
            conn = getConnection();
            sb.append("update t_positionassessment set  ");
            sb.append(" i_assessmentid = ?, ");
            sb.append(" i_minscore = ?, ");
            sb.append(" i_passingflag = ?, ");
            sb.append(" i_userid = ?, ts_moddate = ?  where  i_positionid = ? and i_positionassessmentid = ? ");
            String query = (sb.toString()).intern();
            sb.setLength(0);
            int scc = 0;
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(++scc, info.getAssessmentId());
            pstmt.setInt(++scc, info.getMinScore());
            pstmt.setInt(++scc, info.getPassingFlag());
            pstmt.setInt(++scc, userId);
            pstmt.setString(++scc, currDate1());
            pstmt.setInt(++scc, positionId);
            pstmt.setInt(++scc, info.getAssessmentDetailId());
            cc = pstmt.executeUpdate();
            pstmt.close();

        } catch (Exception exception) {
            print(this, "updateAssessmentDetail :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return cc;
    }

    public int checkDuplicacyAssessment(int positionId, int assessmentDetailId, int assessmentId) 
    {
        int ck = 0;
        StringBuilder sb = new StringBuilder();
        sb.append("select i_positionassessmentid FROM t_positionassessment where i_positionid = ? and i_assessmentid = ? and  i_status  in (1, 2)");
        if (assessmentDetailId > 0) {
            sb.append(" and i_positionassessmentid != ? ");
        }
        String query = (sb.toString()).intern();
        sb.setLength(0);

        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            int scc = 0;
            pstmt.setInt(++scc, positionId);
            pstmt.setInt(++scc, assessmentId);
            if (assessmentDetailId > 0) {
                pstmt.setInt(++scc, assessmentDetailId);
            }
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ck = 1;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return ck;
    }

    public String getAssessmentById(int assessmentId) {
        String s = "";
        String query = ("SELECT t_assessment.i_assessmentid, GROUP_CONCAT(DISTINCT CONCAT(LPAD(t1.i_assessmentparameterid,"
                + " 3, '0' ), ' | ', t1.s_name) order by t1.s_name SEPARATOR ', ') as names from t_assessment LEFT JOIN"
                + " t_assessmentparameter as t1 on find_in_set (t1.i_assessmentparameterid, t_assessment.s_parameterids)"
                + " where t_assessment.i_assessmentid = ? group by t_assessment.i_assessmentid ").intern();
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);

            pstmt.setInt(1, assessmentId);
            rs = pstmt.executeQuery();
            int refId;
            String refName;
            while (rs.next()) 
            {
                refId = rs.getInt(1);
                refName = rs.getString(2) != null ? rs.getString(2): "";
                s = changeNum(refId, 3) + "##" + refName;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return s;
    }

    public ArrayList getCurrencys() {
        ArrayList list = new ArrayList();
        String xml_path = getMainPath("json_path");
        String str = readHTMLFile("currency.json", xml_path);
        JSONArray arr = new JSONArray(str);
        if (arr != null) {
            int len = arr.length();
            for (int i = 0; i < len; i++) {
                JSONObject jobj = arr.optJSONObject(i);
                if (jobj != null) {
                    list.add(new PositionInfo(jobj.optInt("id"), jobj.optString("name")));
                }
            }
        }
        return list;
    }
}
