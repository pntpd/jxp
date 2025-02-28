package com.web.jxp.shortlisting;

import com.web.jxp.base.Base;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Iterator;
import static com.web.jxp.common.Common.*;
import java.util.SortedSet;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

public class Shortlisting extends Base {

    private static final Logger logger = Logger.getLogger(Class.class.getName());
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    public String getShortlistingJobPost(String val, int allclient, String permission, String cids, String assetids) {
        String s = "";
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT jp.i_jobpostid, t_client.s_name, t_clientasset.s_name, t_position.s_name, t_grade.s_name, DATE_FORMAT(jp.ts_regdate, '%d-%b-%Y') ");
        sb.append("FROM t_jobpost AS jp ");
        sb.append("LEFT JOIN t_client ON (t_client.i_clientid = jp.i_clientid) ");
        sb.append("LEFT JOIN t_clientasset ON (t_clientasset.i_clientassetid = jp.i_clientassetid) ");
        sb.append("LEFT JOIN t_position ON (t_position.i_positionid = jp.i_positionid) ");
        sb.append("LEFT JOIN t_grade ON (t_grade.i_gradeid = jp.i_gradeid) ");
        sb.append("WHERE jp.i_status = 1 AND  (jp.i_jobpostid LIKE ? OR t_client.s_name LIKE ? OR t_clientasset.s_name LIKE ? OR t_position.s_name LIKE ? OR t_grade.s_name LIKE ? ) ");
        if (allclient == 1) {
            sb.append("AND t_client.i_clientid > 0 ");
        } else {
            if (!cids.equals("")) {
                sb.append("AND t_client.i_clientid IN (" + cids + ") ");
            } else {
                sb.append("AND t_client.i_clientid < 0 ");
            }
            if (!assetids.equals("")) {
                sb.append("AND t_clientasset.i_clientassetid IN (" + assetids + ") ");
            }
        }
        sb.append("ORDER BY jp.i_jobpostid DESC");
        String query = (sb.toString()).intern();
        sb.setLength(0);
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, val.replaceFirst("^0+(?!$)", ""));
            pstmt.setString(2, "%" + (val) + "%");
            pstmt.setString(3, "%" + (val) + "%");
            pstmt.setString(4, "%" + (val) + "%");
            pstmt.setString(5, "%" + (val) + "%");
            logger.info("getShortlistingJobPost :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            JSONArray jsonArrayParent = new JSONArray();
            String refno = "", data = "";
            while (rs.next()) {
                refno = "";
                data = "";
                int jobpostId = rs.getInt(1);
                String clientname = !rs.getString(2).equals(null) ? rs.getString(2) : "";
                String assetname = !rs.getString(3).equals(null) ? rs.getString(3) : "";
                String positionname = !rs.getString(4).equals(null) ? rs.getString(4) : "";
                String gradename = !rs.getString(5).equals(null) ? rs.getString(5) : "";
                String poston = !rs.getString(6).equals(null) ? rs.getString(6) : "";
                refno = changeNum(jobpostId, 6);
                data = refno + " | " + clientname + "-" + assetname + " | " + positionname + "-" + gradename + " |" + poston;
                JSONObject jsonObjectRow = new JSONObject();
                jsonObjectRow.put("value", jobpostId);
                jsonObjectRow.put("label", data);
                jsonArrayParent.put(jsonObjectRow);
            }
            s = jsonArrayParent.toString();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return s;
    }

    public ShortlistingInfo getShortlistingDetailById(int jobpostId, int allclient, String permission, String cids, String assetids) {
        ShortlistingInfo info = null;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT jp.i_clientid, t_client.s_name, jp.i_clientassetid, t_clientasset.s_name, jp.i_positionid, t_position.s_name, ");
        sb.append("jp.i_gradeid, t_grade.s_name, jp.i_countryid, t_country.s_name, jp.i_cityid, t_city.s_name, jp.d_experiencemin, jp.d_experiencemax, ");
        sb.append("jp.s_nationality, jp.s_language, jp.s_gender, jp.i_qualificationtypeid, t_qualificationtype.s_name, jp.i_noofopening, ");
        sb.append("DATE_FORMAT(jp.ts_regdate, '%d-%b-%Y'), jp.i_status, t_position.i_assettypeid FROM t_jobpost AS jp ");
        sb.append("LEFT JOIN t_client ON (t_client.i_clientid = jp.i_clientid) ");
        sb.append("LEFT JOIN t_clientasset ON (t_clientasset.i_clientassetid = jp.i_clientassetid) ");
        sb.append("LEFT JOIN t_position ON (t_position.i_positionid = jp.i_positionid) ");
        sb.append("LEFT JOIN t_grade ON (t_grade.i_gradeid = jp.i_gradeid) ");
        sb.append("LEFT JOIN t_country ON (t_country.i_countryid = jp.i_countryid) ");
        sb.append("LEFT JOIN t_city ON (t_city.i_cityid = jp.i_cityid) ");
        sb.append("LEFT JOIN t_qualificationtype ON (t_qualificationtype.i_qualificationtypeid = jp.i_qualificationtypeid) ");
        sb.append("WHERE jp.i_jobpostid =? ");
        if (allclient == 1) {
            sb.append("AND t_client.i_clientid > 0 ");
        } else {
            if (!cids.equals("")) {
                sb.append("AND t_client.i_clientid IN (" + cids + ") ");
            } else {
                sb.append("AND t_client.i_clientid < 0 ");
            }
            if (!assetids.equals("")) {
                sb.append("AND t_clientasset.i_clientassetid IN (" + assetids + ") ");
            }
        }
        String query = (sb.toString()).intern();
        sb.setLength(0);
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, jobpostId);
            logger.info("getShortlistingDetailById :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String client, clientasset, position, grade, country, city, nationality, language, gender, qualification, poston = "", code, statusvalue;
            int clientId, clientassetId, positionId, gradeId, countryId, cityId, qualificationId, noofopening, assettypeId;
            double minexp, maxexp;
            while (rs.next()) 
            {
                clientId = rs.getInt(1);
                client = rs.getString(2) != null ? rs.getString(2) : "";
                clientassetId = rs.getInt(3);
                clientasset = rs.getString(4) != null ? rs.getString(4) : "";
                positionId = rs.getInt(5);
                position = rs.getString(6) != null ? rs.getString(6) : "";
                gradeId = rs.getInt(7);
                grade = rs.getString(8) != null ? rs.getString(8) : "";
                countryId = rs.getInt(9);
                country = rs.getString(10) != null ? rs.getString(10) : "";
                cityId = rs.getInt(11);
                city = rs.getString(12) != null ? rs.getString(12) : "";
                minexp = rs.getDouble(13);
                maxexp = rs.getDouble(14);
                nationality = rs.getString(15) != null ? rs.getString(15) : "";
                language = rs.getString(16) != null ? rs.getString(16) : "";
                gender = rs.getString(17) != null ? rs.getString(17) : "";
                qualificationId = rs.getInt(18);
                qualification = rs.getString(19) != null ? rs.getString(19) : "";
                noofopening = rs.getInt(20);
                poston = rs.getString(21) != null ? rs.getString(21) : "";
                statusvalue = rs.getInt(22) == 1 ? "Open" : "Closed";
                assettypeId = rs.getInt(23);
                code = changeNum(jobpostId, 6);
                info = new ShortlistingInfo(clientId, client, clientassetId, clientasset, positionId, position, gradeId, grade, countryId, country, cityId,
                        city, minexp, maxexp, nationality, language, gender, qualificationId, qualification, noofopening, poston, code, statusvalue, assettypeId);
            }
        } catch (Exception exception) {
            print(this, "getCheckPointDetailById :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return info;
    }

    public ArrayList getShortlistingJobpostData(ShortlistingInfo info) {
        ArrayList list = new ArrayList();
        int positionId = 0, assettypeId = 0;
        String query = "";
        try
        {
            conn = getConnection();

            query = "SELECT i_positionid, i_assettypeid FROM t_position WHERE s_name =? AND i_gradeid =? AND i_assettypeid =? AND i_status =1 ";
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, (info.getPosition()));
            pstmt.setInt(2, (info.getGradeId()));
            pstmt.setInt(3, (info.getAssettypeId()));
            logger.info("getShortlistingJobpostData pos :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                positionId = rs.getInt(1);
                assettypeId = rs.getInt(2);
            }
            rs.close();
            list.add(new ShortlistingInfo(1, "" + positionId, info.getPosition() + " - " + info.getGrade(), 1));

            list.add(new ShortlistingInfo(2, "" + info.getMinexp(), "" + info.getMinexp(), 1));
            list.add(new ShortlistingInfo(3, "" + info.getMaxexp(), "" + info.getMaxexp(), 1));

            String gender = info.getGender() != null ? info.getGender().replaceAll("\\s", "") : "";
            if (gender.equals("Male,Female") || gender.equals("Female,Male")) {
                list.add(new ShortlistingInfo(4, "Male", "Male", 1));
                list.add(new ShortlistingInfo(4, "Female", "Female", 1));
            } else if (gender.equals("Female")) {
                list.add(new ShortlistingInfo(4, "Female", "Female", 1));
            } else if (gender.equals("Male")) {
                list.add(new ShortlistingInfo(4, "Male", "Male", 1));
            }

            if (info.getNationality() != null && !"".equals(info.getNationality())) {
                query = "";
                query = "SELECT i_countryid, s_nationality FROM t_country WHERE i_countryid IN (" + info.getNationality() + ") AND i_status =1 ";
                pstmt = conn.prepareStatement(query);
                logger.info("getShortlistingJobpostData nati:: " + pstmt.toString());
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    list.add(new ShortlistingInfo(5, rs.getString(1), rs.getString(2), 1));
                }
                rs.close();
            }

            if (info.getLanguage() != null && !"".equals(info.getLanguage())) {
                query = "";
                query = "SELECT i_languageid, s_name FROM t_language WHERE i_languageid IN (" + info.getLanguage() + ") AND i_status =1 ";
                pstmt = conn.prepareStatement(query);
                logger.info("getShortlistingJobpostData lang:: " + pstmt.toString());
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    list.add(new ShortlistingInfo(6, rs.getString(1), rs.getString(2), 1));
                }
                rs.close();
            }
            list.add(new ShortlistingInfo(7, "" + assettypeId, "" + assettypeId, 1));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return list;
    }

    public String getStrFromList(ArrayList mainlist, int type) {
        String str = "";
        int size = mainlist.size();
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                ShortlistingInfo info = (ShortlistingInfo) mainlist.get(i);
                if (info != null && info.getType() == type && info.getShowflag() == 1) {
                    if (str.equals("")) {
                        str = info.getVal1();
                    } else {
                        str += "," + info.getVal1();
                    }
                }
            }
        }
        if (str.equals("") && (type == 2 || type == 3 || type == 7)) {
            str = "-1";
        } else if (str.equals("") && (type == 4 || type == 5 || type == 6)) {
            str = " ";
        }
        return str;
    }

    public String getStrFromStr(ArrayList list) {
        String str = "";
        str = getStrFromList(list, 1) + "#" + getStrFromList(list, 2) + "#" + getStrFromList(list, 3) + "#" + getStrFromList(list, 4) + "#" + getStrFromList(list, 5)
                + "#" + getStrFromList(list, 6) + "#" + getStrFromList(list, 7) + "#";
        return str;
    }

    public ArrayList getCandidateListByFields(String search) {
        ArrayList list = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT c.i_candidateid, CONCAT_WS(' ', c.s_firstname, c.s_middlename, c.s_lastname), t_position.s_name, t_grade.s_name, ");
        sb.append("t_degree.s_name, t_qualificationtype.s_name, tw1.s_companyname, w.exp FROM t_candidate AS c  ");
        sb.append("LEFT JOIN t_position ON (t_position.i_positionid = c.i_positionid) ");
        sb.append("LEFT JOIN t_grade ON (t_grade.i_gradeid = t_position.i_gradeid) ");
        sb.append("LEFT JOIN t_candlang AS cl ON (cl.i_candidateid = c.i_candidateid) ");
        sb.append("LEFT JOIN t_eduqual ON (t_eduqual.i_candidateid = c.i_candidateid AND t_eduqual.i_highestqualification = 1) ");
        sb.append("LEFT JOIN (SELECT tw.i_candidateid, tw.s_companyname FROM (SELECT i_candidateid, s_companyname FROM t_workexperience WHERE i_status = 1 ORDER BY d_workstartdate DESC) AS tw GROUP BY i_candidateid) AS tw1 ON (tw1.i_candidateid = c.i_candidateid) ");
        sb.append("LEFT JOIN t_degree ON (t_degree.i_degreeid = t_eduqual.i_degreeid) ");
        sb.append("LEFT JOIN t_qualificationtype ON ( t_qualificationtype.i_qualificationtypeid = t_eduqual.i_qualificationtypeid ) ");
        sb.append("LEFT JOIN (SELECT i_candidateid, ROUND(SUM(DATEDIFF( IF(d_workenddate IS NULL OR d_workenddate = '0000-00-00', current_date(), d_workenddate), ");
        sb.append("d_workstartdate)) / 365, 1) AS exp FROM t_workexperience WHERE i_status = 1 GROUP BY i_candidateid ) AS w ON (c.i_candidateid = w.i_candidateid) ");
        sb.append("WHERE c.i_progressid <=0 AND c.i_clientid <=0 AND c.i_pass =2 AND c.i_status = 1 ");

        String arr[] = search.split("#");
        if (arr.length == 7) {
            if (arr[0] != null && !arr[0].equals("")) {
                sb.append(" AND c.i_positionid = " + arr[0]);
            }
            if (arr[1] != null && !arr[1].equals("-1")) {
                sb.append(" AND w.exp >= " + arr[1]);
            }
            if (arr[2] != null && !arr[2].equals("-1")) {
                sb.append(" AND w.exp <= " + arr[2]);
            }
            if (arr[3] != null && !(arr[3].trim()).equals("")) {
                if (arr[3].equals("Male,Female") || arr[3].equals("Female,Male")) {
                    sb.append(" AND (c.s_gender = 'Male' OR c.s_gender = 'Female') ");
                } else if (arr[3].equals("Female")) {
                    sb.append(" AND (c.s_gender = 'Female') ");
                }
                if (arr[3].equals("Male")) {
                    sb.append(" AND (c.s_gender = 'Male') ");
                }
            }
            if (arr[4] != null && !(arr[4].trim()).equals("")) {
                sb.append(" AND c.i_nationalityid IN (" + arr[4] + ") ");
            }
            if (arr[5] != null && !(arr[5].trim()).equals("")) {
                sb.append(" AND cl.i_languageid IN (" + arr[5] + ") ");
            }
            if (arr[6] != null && !arr[6].equals("-1")) {
                sb.append(" AND c.i_assettypeid = " + arr[6]);
            }
        }
        sb.append(" GROUP BY c.i_candidateid ORDER BY c.s_firstname ");
        String query = (sb.toString()).intern();
        sb.setLength(0);

        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            logger.info("getCandidateListByFields :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String name, position, grade, degree, qualification, company;
            int candidateId;
            double exp;
            while (rs.next()) {
                candidateId = rs.getInt(1);
                name = rs.getString(2) != null ? rs.getString(2) : "";
                position = rs.getString(3) != null ? rs.getString(3) : "";
                grade = rs.getString(4) != null ? rs.getString(4) : "";
                degree = rs.getString(5) != null ? rs.getString(5) : "";
                qualification = rs.getString(6) != null ? rs.getString(6) : "";
                company = rs.getString(7) != null ? rs.getString(7) : "";
                exp = rs.getDouble(8);
                list.add(new ShortlistingInfo(candidateId, name, position, grade, degree, qualification, company, exp));
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return list;
    }

    public ArrayList getCandidateListByIDs(String candidateIds) {
        ArrayList list = new ArrayList();
        if (candidateIds != null && !candidateIds.equals("")) {
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT c.i_candidateid, CONCAT_WS(' ', c.s_firstname, c.s_middlename, c.s_lastname), t_position.s_name, t_grade.s_name, ");
            sb.append("t_degree.s_name, t_qualificationtype.s_name, tw1.s_companyname, w.exp FROM t_candidate AS c  ");
            sb.append("LEFT JOIN t_position ON (t_position.i_positionid = c.i_positionid) ");
            sb.append("LEFT JOIN t_grade ON (t_grade.i_gradeid = t_position.i_gradeid) ");
            sb.append("LEFT JOIN t_candlang AS cl ON (cl.i_candidateid = c.i_candidateid) ");
            sb.append("LEFT JOIN t_eduqual ON (t_eduqual.i_candidateid = c.i_candidateid AND t_eduqual.i_highestqualification = 1) ");
            sb.append("LEFT JOIN (SELECT tw.i_candidateid, tw.s_companyname FROM (SELECT i_candidateid, s_companyname FROM t_workexperience WHERE i_status = 1 ORDER BY d_workstartdate DESC) AS tw GROUP BY i_candidateid) AS tw1 ON (tw1.i_candidateid = c.i_candidateid) ");
            sb.append("LEFT JOIN t_degree ON (t_degree.i_degreeid = t_eduqual.i_degreeid) ");
            sb.append("LEFT JOIN t_qualificationtype ON ( t_qualificationtype.i_qualificationtypeid = t_eduqual.i_qualificationtypeid ) ");
            sb.append("LEFT JOIN (SELECT i_candidateid, ROUND(SUM(DATEDIFF( IF(d_workenddate IS NULL OR d_workenddate = '0000-00-00', current_date(), d_workenddate), ");
            sb.append("d_workstartdate)) / 365, 1) AS exp FROM t_workexperience WHERE i_status = 1 GROUP BY i_candidateid ) AS w ON (c.i_candidateid = w.i_candidateid) ");
            sb.append("WHERE c.i_candidateid IN (" + candidateIds + ") ");
            sb.append("GROUP BY c.i_candidateid ORDER BY c.s_firstname ");
            String query = (sb.toString()).intern();
            sb.setLength(0);
            try {
                conn = getConnection();
                pstmt = conn.prepareStatement(query);
                logger.info("getCandidateListByIDs :: " + pstmt.toString());
                rs = pstmt.executeQuery();
                String name, position, grade, degree, qualification, company;
                int candidateId;
                double exp;
                while (rs.next()) {
                    candidateId = rs.getInt(1);
                    name = rs.getString(2) != null ? rs.getString(2) : "";
                    position = rs.getString(3) != null ? rs.getString(3) : "";
                    grade = rs.getString(4) != null ? rs.getString(4) : "";
                    degree = rs.getString(5) != null ? rs.getString(5) : "";
                    qualification = rs.getString(6) != null ? rs.getString(6) : "";
                    company = rs.getString(7) != null ? rs.getString(7) : "";
                    exp = rs.getDouble(8);
                    list.add(new ShortlistingInfo(candidateId, name, position, grade, degree, qualification, company, exp));
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

    public int createShortlisting(SortedSet ss, ShortlistingInfo info) {
        String candidateIds = String.join(",", ss);
        int count = 0, candidateId;
        try {
            StringBuilder sb = new StringBuilder();
            Iterator i = ss.iterator();

            conn = getConnection();
            while (i.hasNext()) {
                count++;
                String ids = (String) i.next();
                if (ids != null && !ids.equals("")) {
                    candidateId = Integer.parseInt(ids);
                    sb.append("INSERT INTO t_shortlist ");
                    sb.append("(i_candidateid, i_jobpostid, i_status, i_userid, ts_regdate, ts_moddate) ");
                    sb.append("VALUES (?, ?, ?, ?, ?, ?)");
                    String query = (sb.toString()).intern();
                    sb.setLength(0);

                    pstmt = conn.prepareStatement(query);
                    int scc = 0;
                    pstmt.setInt(++scc, candidateId);
                    pstmt.setInt(++scc, (info.getJobpostId()));
                    pstmt.setInt(++scc, info.getStatus());
                    pstmt.setInt(++scc, info.getUserId());
                    pstmt.setString(++scc, currDate1());
                    pstmt.setString(++scc, currDate1());
                    logger.info("createShortlisting :: " + pstmt.toString());
                    pstmt.executeUpdate();
                }
            }

            String query1 = "UPDATE t_candidate SET i_userid =?, ts_moddate =?, i_progressid =1 WHERE i_candidateid IN (" + candidateIds + ")";
            pstmt = conn.prepareStatement(query1);
            int scc = 0;
            pstmt.setInt(++scc, (info.getUserId()));
            pstmt.setString(++scc, currDate1());
            logger.info("UPDATE createShortlisting :: " + pstmt.toString());
            pstmt.executeUpdate();

        } catch (Exception exception) {
            logger.info("createShortlisting :: " + pstmt.toString());
        } finally {
            close(conn, pstmt, rs);
        }
        return count;
    }

    public void createShortlistHistory(SortedSet ss, ShortlistingInfo info, int count) {
        String candidateIds = String.join(",", ss);
        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO t_shortlisth ");
        sb.append("(i_jobpostid, i_count, s_candidatesid, i_userid, ts_regdate, ts_moddate) ");
        sb.append("VALUES (?, ?, ?, ?, ?, ?)");
        String query = (sb.toString()).intern();
        sb.setLength(0);
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, (info.getJobpostId()));
            pstmt.setInt(2, count);
            pstmt.setString(3, candidateIds);
            pstmt.setInt(4, (info.getUserId()));
            pstmt.setString(5, currDate1());
            pstmt.setString(6, currDate1());
            logger.info("createShortlistHistory :: " + pstmt.toString());
            pstmt.executeUpdate();
        } catch (Exception exception) {
            logger.info("createShortlistHistory :: " + pstmt.toString());
        } finally {
            close(conn, pstmt, rs);
        }
    }
}
