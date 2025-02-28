package com.web.jxp.jobpost;

import com.mysql.jdbc.Statement;
import com.web.jxp.base.Base;
import static com.web.jxp.base.Base.parseCommaDelimString;
import com.web.jxp.base.StatsInfo;
import com.web.jxp.base.Template;
import com.web.jxp.base.Validate;
import com.web.jxp.common.Common;
import static com.web.jxp.common.Common.*;
import com.web.jxp.documentexpiry.Documentexpiry;
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

public class JobPost extends Base {

    private static final Logger logger = Logger.getLogger(Class.class.getName());
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    public ArrayList getJobPostByName(String search, int statusIndex, int next, int count, int allclient, String permission, String cids, String assetids) {
        ArrayList jobpost = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT t_jobpost.i_jobpostid, t_client.s_name, t_position.s_name, t_grade.s_name, ");
        sb.append("DATE_FORMAT(t_jobpost.ts_targetmobdate, '%d-%b-%Y'), t_jobpost.i_noofopening, ");
        sb.append("DATE_FORMAT(t_jobpost.ts_regdate,'%d-%b-%Y'), t_jobpost.i_status ");
        sb.append("FROM t_jobpost ");
        sb.append("LEFT JOIN t_client ON t_jobpost.i_clientid = t_client.i_clientid ");
        sb.append("LEFT JOIN t_position ON t_jobpost.i_positionid = t_position.i_positionid ");
        sb.append("LEFT JOIN t_grade ON t_jobpost.i_gradeid = t_grade.i_gradeid ");
        sb.append("LEFT JOIN t_clientasset ON (t_clientasset.i_clientassetid = t_jobpost.i_clientassetid) ");
        sb.append("WHERE 0 = 0 AND t_client.i_status = 1 AND t_clientasset.i_status = 1 ");
        if (allclient == 1) {
            sb.append(" AND t_client.i_clientid > 0 ");
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
        if (search != null && !search.equals("")) {
            sb.append("AND (t_client.s_name =? OR t_position.s_name =? OR t_grade.s_name =? OR t_jobpost.i_noofopening =? ");
            sb.append(" OR DATE_FORMAT(t_jobpost.ts_regdate, '%d-%b-%Y') like ?) ");
        }
        if (statusIndex > 0) {
            sb.append("AND t_jobpost.i_status = ? ");
        }
        sb.append(" ORDER BY t_jobpost.i_status, t_jobpost.ts_regdate DESC");
        if (count > 0) {
            sb.append(" limit ").append(next * count).append(", ").append(count);
        }
        String query = (sb.toString()).intern();
        sb.setLength(0);

        sb.append("SELECT COUNT(1) FROM t_jobpost ");
        sb.append("LEFT JOIN t_client ON t_jobpost.i_clientid = t_client.i_clientid ");
        sb.append("LEFT JOIN t_position ON t_jobpost.i_positionid = t_position.i_positionid ");
        sb.append("LEFT JOIN t_grade ON t_jobpost.i_gradeid = t_grade.i_gradeid ");
        sb.append("LEFT JOIN t_clientasset ON (t_clientasset.i_clientassetid = t_jobpost.i_clientassetid) ");
        sb.append("WHERE 0 = 0 AND t_client.i_status = 1 AND t_clientasset.i_status = 1 ");
        if (allclient == 1) {
            sb.append(" AND t_client.i_clientid > 0 ");
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
        if (search != null && !search.equals("")) {
            sb.append("AND (t_client.s_name =? OR t_position.s_name =? OR t_grade.s_name =? OR t_jobpost.i_noofopening =? ");
            sb.append(" OR DATE_FORMAT(t_jobpost.ts_regdate, '%d-%b-%Y') like ?) ");
        }
        if (statusIndex > 0) {
            sb.append("AND t_jobpost.i_status = ? ");
        }
        String countquery = (sb.toString()).intern();
        sb.setLength(0);
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            int scc = 0;
            if (search != null && !search.equals("")) {
                pstmt.setString(++scc, search);
                pstmt.setString(++scc, search);
                pstmt.setString(++scc, search);
                pstmt.setString(++scc, search);
                pstmt.setString(++scc, "%" + (search) + "%");

            }
            if (statusIndex > 0) {
                pstmt.setInt(++scc, statusIndex);
            }
            logger.info("getJobPostByName :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String refno, poston, clientname, positionname, grade, mobilizeby, opening;
            int jobpostId, status;
            while (rs.next())
            {
                jobpostId = rs.getInt(1);
                clientname = rs.getString(2) != null ? rs.getString(2) : "";
                positionname = rs.getString(3) != null ? rs.getString(3) : "";
                grade = rs.getString(4) != null ? rs.getString(4) : "";
                mobilizeby = rs.getString(5) != null ? rs.getString(5) : "";
                opening = rs.getString(6) != null ? rs.getString(6) : "";
                poston = rs.getString(7) != null ? rs.getString(7) : "";
                status = rs.getInt(8);
                refno = changeNum(jobpostId, 6);
                jobpost.add(new JobPostInfo(jobpostId, refno, clientname, positionname, grade, mobilizeby, opening, poston, status, 0));
            }
            rs.close();

            pstmt = conn.prepareStatement(countquery);
            scc = 0;
            if (search != null && !search.equals("")) {
                pstmt.setString(++scc, search);
                pstmt.setString(++scc, search);
                pstmt.setString(++scc, search);
                pstmt.setString(++scc, search);
                pstmt.setString(++scc, "%" + (search) + "%");
            }
            if (statusIndex > 0) {
                pstmt.setInt(++scc, statusIndex);
            }
            print(this, "getJobPostByName :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                jobpostId = rs.getInt(1);
                jobpost.add(new JobPostInfo(jobpostId, "", "", "", "", "", "", "", 0, 0));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return jobpost;
    }

    public ArrayList getFinalRecord(ArrayList l, String colId, int tp) {
        ArrayList list = null;
        HashMap record = null;
        JobPostInfo info;
        int total = 0;
        if (l != null) {
            total = l.size();
        }
        try {
            if (total > 0) {
                record = new HashMap();
                int i;
                for (i = 0; i < total; i++) {
                    info = (JobPostInfo) l.get(i);
                    record.put(getInfoValue(info, colId), info);
                }
            }
            Map map = sortByName(record, tp);
            Iterator it = map.keySet().iterator();
            list = new ArrayList();
            JobPostInfo rInfo;
            while (it.hasNext()) {
                String key = (String) it.next();
                int i;
                for (i = 0; i < total; i++) {
                    rInfo = (JobPostInfo) l.get(i);
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
    public String getInfoValue(JobPostInfo info, String i) {
        String infoval = "";
        if (i != null && i.equals("1")) {
            infoval = info.getRefno() != null ? info.getRefno() : "";
        } else if (i != null && i.equals("2")) {
            infoval = info.getPoston() != null ? info.getPoston() : "";
        } else if (i != null && i.equals("3")) {
            infoval = info.getClientname() != null ? info.getClientname() : "";
        } else if (i != null && i.equals("4")) {
            infoval = info.getPositionname() != null ? info.getPositionname() : "";
        } else if (i != null && i.equals("5")) {
            infoval = info.getTargetmobdate() != null ? info.getTargetmobdate() : "";
        }
        return infoval;
    }

    public JobPostInfo getJobPostDetailById(int jobpostId) {
        JobPostInfo info = null;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT t_jobpost.i_clientid, t_jobpost.i_clientassetid, t_jobpost.i_positionid, t_jobpost.i_gradeid,"
                + " t_jobpost.i_countryid, t_jobpost.i_cityid, t_jobpost.d_experiencemin, t_jobpost.d_experiencemax,"
                + " t_jobpost.s_nationality, t_jobpost.s_language, t_jobpost.s_gender, t_jobpost.i_qualificationtypeid,"
                + " t_jobpost.s_description, t_jobpost.i_noofopening, DATE_FORMAT(t_jobpost.ts_targetmobdate,'%d-%b-%Y'),"
                + " t_jobpost.i_tenure, t_jobpost.i_currencyid, t_jobpost.d_dayratevalue, t_jobpost.d_remunerationmin,"
                + " t_jobpost.d_remunerationmax, t_jobpost.d_workhour, t_jobpost.s_vacancypostedby, t_jobpost.s_additionalnote,"
                + " t_jobpost.i_status, t_jobpost.i_userid, t_position.s_name, t_city.s_name ");
        sb.append("FROM t_jobpost ");
        sb.append("LEFT JOIN t_position ON t_jobpost.i_positionid = t_position.i_positionid ");
        sb.append("LEFT JOIN t_city ON t_jobpost.i_cityid = t_city.i_cityid ");
        sb.append("WHERE i_jobpostid = ? ");
        String query = sb.toString().intern();
        sb.setLength(0);
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, jobpostId);
            print(this, "getJobPostDetailById :: " + pstmt.toString());
            logger.info("getJobPostDetailById :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String nationality, language, gender, description, targetmobdate, vacancypostedby, additionalnote, positionname, cityname;
            double experiencemin, experiencemax, dayratevalue, remunerationmin, remunerationmax, workhour;
            int clientId, clientassetId, positionId, gradeId, countryId, cityId, qualificationtypeId, currencyId,
                    noofopening, tenure, uId, status;
            while (rs.next()) 
            {
                clientId = rs.getInt(1);
                clientassetId = rs.getInt(2);
                positionId = rs.getInt(3);
                gradeId = rs.getInt(4);
                countryId = rs.getInt(5);
                cityId = rs.getInt(6);
                experiencemin = rs.getDouble(7);
                experiencemax = rs.getDouble(8);
                nationality = rs.getString(9) != null ? rs.getString(9): "";
                language = rs.getString(10) != null ? rs.getString(10): "";
                gender = rs.getString(11) != null ? rs.getString(11): "";
                qualificationtypeId = rs.getInt(12);
                description = rs.getString(13) != null ? rs.getString(13): "";
                noofopening = rs.getInt(14);
                targetmobdate = rs.getString(15) != null ? rs.getString(15): "";
                tenure = rs.getInt(16);
                currencyId = rs.getInt(17);
                dayratevalue = rs.getDouble(18);
                remunerationmin = rs.getDouble(19);
                remunerationmax = rs.getDouble(20);
                workhour = rs.getDouble(21);
                vacancypostedby = rs.getString(22) != null ? rs.getString(22): "";
                additionalnote = rs.getString(23) != null ? rs.getString(23): "";
                status = rs.getInt(24);
                uId = rs.getInt(25);
                positionname = rs.getString(26) != null ? rs.getString(26): "";
                cityname = rs.getString(27) != null ? rs.getString(27): "";

                info = new JobPostInfo(jobpostId, clientId, clientassetId, positionId, gradeId, countryId, cityId,
                        experiencemin, experiencemax, nationality, language, gender, qualificationtypeId, description,
                        noofopening, targetmobdate, tenure, currencyId, dayratevalue, remunerationmin, remunerationmax,
                        workhour, vacancypostedby, additionalnote, status, uId, positionname, cityname);
            }
        } catch (Exception exception) {
            print(this, "getJobPostDetailById :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return info;
    }

    public JobPostInfo getJobPostDetailByIdforDetail(int jobpostId) {
        JobPostInfo info = null;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT t_client.s_name, t_clientasset.s_name, t_position.s_name, t_grade.s_name, t_country.s_name, t_city.s_name, t_jobpost.d_experiencemin, ");
        sb.append("t_jobpost.d_experiencemax, t2.names, t4.names, t_jobpost.s_gender, t_qualificationtype.s_name, t_jobpost.s_description, t_jobpost.i_noofopening, ");
        sb.append("DATE_FORMAT(t_jobpost.ts_targetmobdate,'%d-%b-%Y'), t_jobpost.i_tenure, t_currency.s_name, t_jobpost.d_dayratevalue, t_jobpost.d_remunerationmin, ");
        sb.append("t_jobpost.d_remunerationmax, t_jobpost.d_workhour, t_jobpost.s_vacancypostedby, t_jobpost.s_additionalnote, t_jobpost.i_status FROM t_jobpost ");
        sb.append("LEFT JOIN t_client ON t_jobpost.i_clientid = t_client.i_clientid ");
        sb.append("LEFT JOIN t_clientasset ON t_jobpost.i_clientassetid = t_clientasset.i_clientassetid ");
        sb.append("LEFT JOIN t_position ON t_jobpost.i_positionid = t_position.i_positionid ");
        sb.append("LEFT JOIN t_grade ON t_jobpost.i_gradeid = t_grade.i_gradeid ");
        sb.append("LEFT JOIN t_country ON t_jobpost.i_countryid = t_country.i_countryid ");
        sb.append("LEFT JOIN t_city ON t_jobpost.i_cityid = t_city.i_cityid ");
        sb.append("LEFT JOIN t_currency ON t_jobpost.i_currencyid = t_currency.i_currencyid ");
        sb.append("LEFT JOIN t_qualificationtype ON t_jobpost.i_qualificationtypeid = t_qualificationtype.i_qualificationtypeid ");
        sb.append("LEFT JOIN (select t_jobpost.i_jobpostid, GROUP_CONCAT(DISTINCT t1.s_name order by t1.s_name SEPARATOR ', ') as names from t_jobpost LEFT JOIN t_country as t1 on find_in_set(t1.i_countryid, t_jobpost.s_nationality) group by t_jobpost.i_jobpostid) as t2 on (t2.i_jobpostid = t_jobpost.i_jobpostid) ");
        sb.append("LEFT JOIN (select t_jobpost.i_jobpostid, GROUP_CONCAT(DISTINCT t3.s_name order by t3.s_name SEPARATOR ', ') as names from t_jobpost LEFT JOIN t_language as t3 on find_in_set(t3.i_languageid, t_jobpost.s_language) group by t_jobpost.i_jobpostid) as t4 on (t4.i_jobpostid = t_jobpost.i_jobpostid) ");
        sb.append("WHERE t_jobpost.i_jobpostid = ? ");
        String query = sb.toString().intern();
        sb.setLength(0);
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, jobpostId);
            print(this, "getJobPostDetailByIdforDetail :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String clientname, assetname, positionname, grade, countryname, cityname, nationality, language, currency,
                    gender, educationlevel, description, targetmobdate, vacancypostedby, additionalnote;
            double experiencemin, experiencemax, dayratevalue, remunerationmin, remunerationmax, workhour;
            int noofopening, tenure, status;
            while (rs.next()) 
            {
                clientname = rs.getString(1) != null ? rs.getString(1) : "";
                assetname = rs.getString(2) != null ? rs.getString(2) : "";
                positionname = rs.getString(3) != null ? rs.getString(3) : "";
                grade = rs.getString(4) != null ? rs.getString(4) : "";
                countryname = rs.getString(5) != null ? rs.getString(5) : "";
                cityname = rs.getString(6) != null ? rs.getString(6) : "";
                experiencemin = rs.getDouble(7);
                experiencemax = rs.getDouble(8);
                nationality = rs.getString(9) != null ? rs.getString(9) : "";
                language = rs.getString(10) != null ? rs.getString(10) : "";
                gender = rs.getString(11) != null ? rs.getString(11) : "";
                educationlevel = rs.getString(12) != null ? rs.getString(12) : "";
                description = rs.getString(13) != null ? rs.getString(13) : "";
                noofopening = rs.getInt(14);
                targetmobdate = rs.getString(15) != null ? rs.getString(15) : "";
                tenure = rs.getInt(16);
                currency = rs.getString(17) != null ? rs.getString(17) : "";
                dayratevalue = rs.getDouble(18);
                remunerationmin = rs.getDouble(19);
                remunerationmax = rs.getDouble(20);
                workhour = rs.getDouble(21);
                vacancypostedby = rs.getString(22) != null ? rs.getString(22) : "";
                additionalnote = rs.getString(23) != null ? rs.getString(23) : "";
                status = rs.getInt(24);
                info = new JobPostInfo(jobpostId, clientname, assetname, positionname, grade, countryname, cityname,
                        experiencemin, experiencemax, nationality, language, gender, educationlevel, description,
                        noofopening, targetmobdate, tenure, currency, dayratevalue, remunerationmin, remunerationmax,
                        workhour, vacancypostedby, additionalnote, status, 0);
            }
            rs.close();
        } catch (Exception exception) {
            print(this, "getJobPostDetailByIdforDetail :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return info;
    }

    public int createJobPost(JobPostInfo info) 
    {
        int jobpostId = 0;
        try
        {
            StringBuilder sb = new StringBuilder();
            sb.append("INSERT INTO t_jobpost ");
            sb.append("(i_clientid, i_clientassetid, i_positionid, i_gradeid, i_countryid, i_cityid, d_experiencemin, d_experiencemax, ");
            sb.append("s_nationality, s_language, s_gender, i_qualificationtypeid, s_description, i_noofopening, ts_targetmobdate, ");
            sb.append("i_tenure, i_currencyid, d_dayratevalue, d_remunerationmin, d_remunerationmax, d_workhour, s_vacancypostedby, ");
            sb.append("s_additionalnote, i_status, i_userid, ts_regdate, ts_moddate) ");
            sb.append("VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            String query = (sb.toString()).intern();
            sb.setLength(0);

            conn = getConnection();
            pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            int scc = 0;
            pstmt.setInt(++scc, (info.getClientId()));
            pstmt.setInt(++scc, (info.getClientassetId()));
            pstmt.setInt(++scc, (info.getPositionId()));
            pstmt.setInt(++scc, (info.getGradeId()));
            pstmt.setInt(++scc, (info.getCountryId()));
            pstmt.setInt(++scc, (info.getCityId()));
            pstmt.setDouble(++scc, (info.getExperiencemin()));
            pstmt.setDouble(++scc, (info.getExperiencemax()));
            pstmt.setString(++scc, (info.getNationality()));
            pstmt.setString(++scc, (info.getLanguage()));
            pstmt.setString(++scc, (info.getGender()));
            pstmt.setInt(++scc, (info.getEducationtypeId()));
            pstmt.setString(++scc, (info.getDescription()));
            pstmt.setInt(++scc, (info.getNoofopening()));
            pstmt.setString(++scc, changeDate1(info.getTargetmobdate()));
            pstmt.setInt(++scc, (info.getTenure()));
            pstmt.setInt(++scc, (info.getCurrencyId()));
            pstmt.setDouble(++scc, (info.getDayratevalue()));
            pstmt.setDouble(++scc, (info.getRemunerationmin()));
            pstmt.setDouble(++scc, (info.getRemunerationmax()));
            pstmt.setDouble(++scc, (info.getWorkhour()));
            pstmt.setString(++scc, (info.getVacancypostedby()));
            pstmt.setString(++scc, (info.getAdditionalnote()));
            pstmt.setInt(++scc, info.getStatus());
            pstmt.setInt(++scc, info.getUserId());
            pstmt.setString(++scc, currDate1());
            pstmt.setString(++scc, currDate1());
            print(this, "createJobPost :: " + pstmt.toString());
            pstmt.executeUpdate();
            rs = pstmt.getGeneratedKeys();
            while (rs.next()) {
                jobpostId = rs.getInt(1);
            }
            if (jobpostId > 0) {
                insertonadd(conn, jobpostId, info.getPositionname(), info.getUserId(), info.getGradeId());
            }
        } catch (Exception exception) {
            print(this, "createJobPost :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return jobpostId;
    }

    public int updateJobPost(JobPostInfo info) 
    {
        int cc = 0;
        try 
        {
            StringBuilder sb = new StringBuilder();
            sb.append("UPDATE t_jobpost SET ");
            sb.append("i_clientid = ?, ");
            sb.append("i_clientassetid = ?, ");
            sb.append("i_positionid = ?, ");
            sb.append("i_gradeid = ?, ");
            sb.append("i_countryid = ?, ");
            sb.append("i_cityid = ?, ");
            sb.append("d_experiencemin = ?, ");
            sb.append("d_experiencemax = ?, ");
            sb.append("s_nationality = ?, ");
            sb.append("s_language = ?, ");
            sb.append("s_gender = ?, ");
            sb.append("i_qualificationtypeid = ?, ");
            sb.append("s_description = ?, ");
            sb.append("i_noofopening = ?, ");
            sb.append("ts_targetmobdate = ?, ");
            sb.append("i_tenure = ?, ");
            sb.append("i_currencyid = ?, ");
            sb.append("d_dayratevalue = ?, ");
            sb.append("d_remunerationmin = ?, ");
            sb.append("d_remunerationmax = ?, ");
            sb.append("d_workhour = ?, ");
            sb.append("s_vacancypostedby = ?, ");
            sb.append("s_additionalnote = ?, ");
            sb.append("i_status = ?, ");
            sb.append("i_userid = ?, ");
            sb.append("ts_moddate = ? ");
            sb.append("WHERE i_jobpostid = ? ");
            String query = (sb.toString()).intern();
            sb.setLength(0);

            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            int scc = 0;
            pstmt.setInt(++scc, (info.getClientId()));
            pstmt.setInt(++scc, (info.getClientassetId()));
            pstmt.setInt(++scc, (info.getPositionId()));
            pstmt.setInt(++scc, (info.getGradeId()));
            pstmt.setInt(++scc, (info.getCountryId()));
            pstmt.setInt(++scc, (info.getCityId()));
            pstmt.setDouble(++scc, (info.getExperiencemin()));
            pstmt.setDouble(++scc, (info.getExperiencemax()));
            pstmt.setString(++scc, (info.getNationality()));
            pstmt.setString(++scc, (info.getLanguage()));
            pstmt.setString(++scc, (info.getGender()));
            pstmt.setInt(++scc, (info.getEducationtypeId()));
            pstmt.setString(++scc, (info.getDescription()));
            pstmt.setInt(++scc, (info.getNoofopening()));
            pstmt.setString(++scc, changeDate1(info.getTargetmobdate()));
            pstmt.setInt(++scc, (info.getTenure()));
            pstmt.setInt(++scc, (info.getCurrencyId()));
            pstmt.setDouble(++scc, (info.getDayratevalue()));
            pstmt.setDouble(++scc, (info.getRemunerationmin()));
            pstmt.setDouble(++scc, (info.getRemunerationmax()));
            pstmt.setDouble(++scc, (info.getWorkhour()));
            pstmt.setString(++scc, (info.getVacancypostedby()));
            pstmt.setString(++scc, (info.getAdditionalnote()));
            pstmt.setInt(++scc, info.getStatus());
            pstmt.setInt(++scc, info.getUserId());
            pstmt.setString(++scc, currDate1());
            pstmt.setInt(++scc, (info.getJobpostId()));
            print(this, "updateJobPost :: " + pstmt.toString());
            logger.info("updateJobPost :: " + pstmt.toString());

            cc = pstmt.executeUpdate();
        } catch (Exception exception) {
            print(this, "updateJobPost :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return cc;
    }

    public int checkDuplicacy(int jobpostId, int clientId, int positionId, int gradeId)
    {
        int ck = 0;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT i_jobpostid FROM t_jobpost WHERE (i_clientid =? AND i_positionid =? AND i_gradeid =? AND DATE_FORMAT(ts_regdate,'%Y-%m-%d') =current_date) AND i_status IN (1, 2)");
        if (jobpostId > 0) {
            sb.append(" AND i_jobpostid != ? ");
        }
        String query = (sb.toString()).intern();
        sb.setLength(0);
        try
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            int scc = 0;
            pstmt.setInt(++scc, clientId);
            pstmt.setInt(++scc, positionId);
            pstmt.setInt(++scc, gradeId);
            if (jobpostId > 0) {
                pstmt.setInt(++scc, jobpostId);
            }
            print(this, "checkDuplicacy :: " + pstmt.toString());
            logger.info("checkDuplicacy :: " + pstmt.toString());
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

    public int deleteJobPost(int jobpostId, int userId, int status, String ipAddrStr, String iplocal) 
    {
        int cc = 0;
        try 
        {
            conn = getConnection();
            StringBuilder sb = new StringBuilder();
            sb.append("UPDATE t_jobpost SET ");
            sb.append("i_status = ?, ");
            sb.append("i_userid = ?, ");
            sb.append("ts_moddate = ? ");
            sb.append("WHERE i_jobpostid = ? ");
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
            pstmt.setInt(++scc, jobpostId);

            cc = pstmt.executeUpdate();
        } catch (Exception exception) {
            print(this, "deleteJobPost :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        createHistoryAccess(null, userId, ipAddrStr, iplocal, "Data Deleted", 45, jobpostId);
        return cc;
    }

    public void insertonadd(Connection conn, int jobpostId, String positionname, int userId, int gradeId)
    {
        try 
        {
            String pquery = "SELECT i_positionid FROM t_position WHERE s_name=? AND i_gradeid=?";
            pstmt = conn.prepareStatement(pquery);
            pstmt.setString(1, positionname);
            pstmt.setInt(2, gradeId);
            rs = pstmt.executeQuery();
            int positionId = 0;
            while (rs.next()) 
            {
                positionId = rs.getInt(1);
            }
            rs.close();
            pstmt.close();
            
            StringBuilder sb = new StringBuilder();
            sb.append("INSERT INTO t_jobpostbenefit ");
            sb.append("(i_jobpostid, i_benefitid, i_benefitquestionid, i_currencyid, s_info, i_status, i_userid, ts_regdate, ts_moddate) ");
            sb.append("VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");
            String query1 = sb.toString();
            sb.setLength(0);

            sb.append("INSERT INTO t_jobpostassessment ");
            sb.append("(i_jobpostid, i_assessmentid, i_minscore, i_passingflag, i_status, i_userid, ts_regdate, ts_moddate) ");
            sb.append("VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
            String query2 = sb.toString();
            sb.setLength(0);

            PreparedStatement insertpstmt1 = conn.prepareStatement(query1);
            PreparedStatement insertpstmt2 = conn.prepareStatement(query2);

            String query = "SELECT i_benefitid, i_benefitquestionid, i_currencyid, s_info FROM t_positionbenefit WHERE i_positionid = ? AND i_status =1";
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, positionId);
            logger.info("insertonadd query1 :: " + pstmt.toString());

            rs = pstmt.executeQuery();
            String currdate = currDate1();
            while (rs.next()) 
            {
                int benfitId = rs.getInt(1);
                int benefitquestionId = rs.getInt(2);
                int currencyId = rs.getInt(3);
                String desc = rs.getString(4) != null ? rs.getString(4) : "";

                insertpstmt1.setInt(1, jobpostId);
                insertpstmt1.setInt(2, benfitId);
                insertpstmt1.setInt(3, benefitquestionId);
                insertpstmt1.setInt(4, currencyId);
                insertpstmt1.setString(5, desc);
                insertpstmt1.setInt(6, 1);
                insertpstmt1.setInt(7, userId);
                insertpstmt1.setString(8, currdate);
                insertpstmt1.setString(9, currdate);
                insertpstmt1.executeUpdate();
            }
            rs.close();
            insertpstmt1.close();

            query = "SELECT i_assessmentid, i_minscore, i_passingflag FROM t_positionassessment WHERE i_positionid = ?  AND i_status =1";
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, positionId);
            logger.info("insertonadd query2 :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            while (rs.next()) 
            {
                int assessmentId = rs.getInt(1);
                int minscore = rs.getInt(2);
                int passingflag = rs.getInt(3);

                insertpstmt2.setInt(1, jobpostId);
                insertpstmt2.setInt(2, assessmentId);
                insertpstmt2.setInt(3, minscore);
                insertpstmt2.setInt(4, passingflag);
                insertpstmt2.setInt(5, 1);
                insertpstmt2.setInt(6, userId);
                insertpstmt2.setString(7, currdate);
                insertpstmt2.setString(8, currdate);
                insertpstmt2.executeUpdate();
            }
            rs.close();
            insertpstmt2.close();
        } catch (Exception exception) {
            print(this, "insertonadd :: " + exception.getMessage());
        } finally {
            close(null, pstmt, rs);
        }
    }

    public ArrayList getJobPostBenefitDetailByIdforList(int jobpostId) 
    {
        ArrayList jobBenefit = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT t_jobpostbenefit.i_benefitid, t_jobpostbenefit.s_info, t_benefit.s_name, t_jobpostbenefit.i_status,"
                + " t_jobpostbenefit.i_jobpostbenefitid ");
        sb.append("FROM t_jobpostbenefit ");
        sb.append("LEFT JOIN t_benefit ON t_jobpostbenefit.i_benefitid = t_benefit.i_benefitid ");
        sb.append("WHERE t_jobpostbenefit.i_jobpostid = ? ");
        String query = sb.toString().intern();
        sb.setLength(0);
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, jobpostId);
            print(this, "getJobPostBenefitDetailByIdforList :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String positionbenefitinfo, benifitname, codenum;
            int benefitId, status, jobpostbenefitId;
            while (rs.next()) 
            {
                benefitId = rs.getInt(1);
                positionbenefitinfo = rs.getString(2) != null ? rs.getString(2): "";
                benifitname = rs.getString(3) != null ? rs.getString(3): "";
                status = rs.getInt(4);
                jobpostbenefitId = rs.getInt(5);
                codenum = changeNum(benefitId, 3);
                jobBenefit.add(new JobPostInfo(jobpostId, benefitId, positionbenefitinfo, benifitname, codenum, status, 0, jobpostbenefitId));
            }
            rs.close();
        } catch (Exception exception) {
            print(this, "getJobPostBenefitDetailByIdforList :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return jobBenefit;
    }

    public ArrayList getListForExcel(String search, int statusIndex, int allclient, String permission, String cids, String assetids) {
        ArrayList list = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT t_jobpost.i_jobpostid, t_client.s_name, t_position.s_name, t_grade.s_name, DATE_FORMAT(t_jobpost.ts_targetmobdate,"
                + " '%d-%b-%Y'), t_jobpost.i_noofopening, DATE_FORMAT(t_jobpost.ts_regdate,'%d-%b-%Y'), t_jobpost.i_status  ");
        sb.append("FROM t_jobpost ");
        sb.append("LEFT JOIN t_client ON t_jobpost.i_clientid = t_client.i_clientid ");
        sb.append("LEFT JOIN t_position ON t_jobpost.i_positionid = t_position.i_positionid ");
        sb.append("LEFT JOIN t_grade ON t_jobpost.i_gradeid = t_grade.i_gradeid ");
        sb.append("LEFT JOIN t_clientasset ON (t_clientasset.i_clientassetid = t_jobpost.i_clientassetid) ");
        sb.append("WHERE 0 = 0 ");
        if (allclient == 1) {
            sb.append(" AND t_client.i_clientid > 0 ");
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
        if (search != null && !search.equals("")) {
            sb.append("AND (t_client.s_name =? OR t_position.s_name =? OR t_grade.s_name =? OR t_jobpost.i_noofopening =? "
                    + " OR DATE_FORMAT(t_jobpost.ts_regdate, '%d-%b-%Y') like ?) ");
        }
        if (statusIndex > 0) {
            sb.append("AND t_jobpost.i_status = ? ");
        }
        sb.append(" ORDER BY t_jobpost.i_status, t_jobpost.ts_regdate DESC");
        String query = sb.toString().intern();
        sb.setLength(0);
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            int scc = 0;
            if (search != null && !search.equals("")) 
            {
                pstmt.setString(++scc, search);
                pstmt.setString(++scc, search);
                pstmt.setString(++scc, search);
                pstmt.setString(++scc, search);
                pstmt.setString(++scc, "%" + (search) + "%");
            }
            if (statusIndex > 0) {
                pstmt.setInt(++scc, statusIndex);
            }
            Common.print(this, "getListForExcel :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String refno, poston, clientname, positionname, grade, mobilizeby, opening;
            int jobpostId, status;
            while (rs.next()) 
            {
                jobpostId = rs.getInt(1);
                clientname = rs.getString(2) != null ? rs.getString(2) : "";
                positionname = rs.getString(3) != null ? rs.getString(3) : "";
                grade = rs.getString(4) != null ? rs.getString(4) : "";
                mobilizeby = rs.getString(5) != null ? rs.getString(5) : "";
                opening = rs.getString(6) != null ? rs.getString(6) : "";
                poston = rs.getString(7) != null ? rs.getString(7) : "";
                status = rs.getInt(8);
                refno = changeNum(jobpostId, 0);
                list.add(new JobPostInfo(jobpostId, refno, clientname, positionname, grade, mobilizeby, opening, poston, status, 0));
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

    public Collection getClientAsset(int clientId) 
    {
        Collection coll = new LinkedList();
        if (clientId > 0) 
        {
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT i_clientassetid, s_name FROM t_clientasset WHERE i_clientid = ? AND i_status = 1 ORDER BY s_name");
            String query = sb.toString();
            sb.setLength(0);
            coll.add(new JobPostInfo(-1, "- Select -"));
            try 
            {
                conn = getConnection();
                pstmt = conn.prepareStatement(query);
                pstmt.setInt(1, clientId);
                rs = pstmt.executeQuery();
                int ddlValue;
                String ddlLabel;
                while (rs.next())
                {
                    ddlValue = rs.getInt(1);
                    ddlLabel = rs.getString(2) != null ? rs.getString(2): "";
                    coll.add(new JobPostInfo(ddlValue, ddlLabel));
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                close(conn, pstmt, rs);
            }
        } else {
            coll.add(new JobPostInfo(-1, "- Select -"));
        }
        return coll;
    }
    
   public Collection getClientAsset(int clientId, String assetids, int allclient, String permission)
   {
        Collection coll = new LinkedList();
        if (clientId > 0) 
        {
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT i_clientassetid, s_name FROM t_clientasset WHERE i_clientid = ? AND i_status = 1 ");
            if (allclient == 1 ) {
                sb.append("AND i_clientassetid > 0 ");
            } else {
                if (!assetids.equals("")) {
                    sb.append("AND i_clientassetid IN (" + assetids + ") ");
                } else {
                    sb.append("AND i_clientassetid < 0 ");
                }
            }
            sb.append("ORDER BY s_name ");
            String query = sb.toString();
            sb.setLength(0);
            coll.add(new JobPostInfo(-1, " Select Asset "));
            try 
            {
                conn = getConnection();
                pstmt = conn.prepareStatement(query);
                pstmt.setInt(1, clientId);
                logger.info("getClientAsset :: " + pstmt.toString());
                rs = pstmt.executeQuery();
                int ddlValue;
                String ddlLabel;
                while (rs.next()) 
                {
                    ddlValue = rs.getInt(1);
                    ddlLabel = rs.getString(2) != null ? rs.getString(2): "";
                    coll.add(new JobPostInfo(ddlValue, ddlLabel));
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                close(conn, pstmt, rs);
            }
        } else {
            coll.add(new JobPostInfo(-1, " Select Asset "));
        }
        return coll;
    }

    public Collection getPostions(int clientassetId) 
    {
        Collection coll = new LinkedList();
        if (clientassetId > 0) 
        {
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT t_clientassetposition.i_positionid, t_position.s_name FROM t_clientassetposition ");
            sb.append("LEFT JOIN t_position ON (t_position.i_positionid = t_clientassetposition.i_positionid) ");
            sb.append("WHERE t_clientassetposition.i_clientassetid = ? GROUP BY t_position.s_name ORDER BY t_position.s_name ");
            String query = sb.toString();
            sb.setLength(0);
            coll.add(new JobPostInfo(-1, "- Select -"));
            try
            {
                conn = getConnection();
                pstmt = conn.prepareStatement(query);
                pstmt.setInt(1, clientassetId);
                logger.info("getPostions :: " + pstmt.toString());
                Common.print(this, "getPostions :: " + pstmt.toString());
                rs = pstmt.executeQuery();
                int ddlValue;
                String ddlLabel;
                while (rs.next()) 
                {
                    ddlValue = rs.getInt(1);
                    ddlLabel = rs.getString(2) != null ? rs.getString(2): "";
                    coll.add(new JobPostInfo(ddlValue, ddlLabel));
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                close(conn, pstmt, rs);
            }
        } else {
            coll.add(new JobPostInfo(-1, "- Select -"));
        }
        return coll;
    }

    public Collection getGrades(int clientassetId, String positionname) {
        Collection coll = new LinkedList();
        if (clientassetId > 0) {
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT t_grade.i_gradeid, t_grade.s_name FROM t_clientassetposition LEFT JOIN t_position ON"
                    + " (t_position.i_positionid = t_clientassetposition.i_positionid) LEFT JOIN t_grade ON"
                    + " (t_grade.i_gradeid = t_position.i_gradeid) WHERE t_clientassetposition.i_clientassetid = ? "
                    + " AND t_position.s_name = ? ORDER BY t_grade.s_name");
            String query = sb.toString();
            sb.setLength(0);
            coll.add(new JobPostInfo(-1, "- Select -"));
            try 
            {
                conn = getConnection();
                pstmt = conn.prepareStatement(query);
                pstmt.setInt(1, clientassetId);
                pstmt.setString(2, positionname);
                rs = pstmt.executeQuery();
                Common.print(this, "getGrades :: " + pstmt.toString());
                logger.info("getGrades :: " + pstmt.toString());
                int ddlValue;
                String ddlLabel;
                while (rs.next()) 
                {
                    ddlValue = rs.getInt(1);
                    ddlLabel = rs.getString(2) != null ? rs.getString(2): "";
                    coll.add(new JobPostInfo(ddlValue, ddlLabel));
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                close(conn, pstmt, rs);
            }
        } else {
            coll.add(new JobPostInfo(-1, "- Select -"));
        }
        return coll;
    }

    public ArrayList getNationalityList() {
        ArrayList list = new ArrayList();
        try {
            String xml_path = getMainPath("json_path");
            String str = readHTMLFile("country.json", xml_path);
            JSONArray arr = new JSONArray(str);
            if (arr != null) {
                int len = arr.length();
                for (int i = 0; i < len; i++) {
                    JSONObject jobj = arr.optJSONObject(i);
                    if (jobj != null) {
                        list.add(new JobPostInfo(jobj.optInt("id"), jobj.optString("nationality")));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public ArrayList getLanguageList() {
        ArrayList list = new ArrayList();
        try {
            String xml_path = getMainPath("json_path");
            String str = readHTMLFile("language.json", xml_path);
            JSONArray arr = new JSONArray(str);
            if (arr != null) {
                int len = arr.length();
                for (int i = 0; i < len; i++) {
                    JSONObject jobj = arr.optJSONObject(i);
                    if (jobj != null) {
                        list.add(new JobPostInfo(jobj.optInt("id"), jobj.optString("name")));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public Collection getBenefitType() 
    {
        Collection coll = new LinkedList();
        String query = ("SELECT i_benefittypeid, s_name FROM t_benefittype where i_status = 1 order by s_name").intern();
        coll.add(new JobPostInfo(-1, "- Select -"));
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
                coll.add(new JobPostInfo(refId, refName));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return coll;
    }

    public String getMaxBenefitQuestionId()
    {
        String code = "";
        String query = ("SELECT Max(i_benefitid) FROM t_benefit").intern();
        try 
        {
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

    public int checkBenefitDuplicacy(int benefitId, String name)
    {
        int ck = 0;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT i_benefitid FROM t_benefit WHERE (s_name = ?) AND i_status IN (1, 2)");
        if (benefitId > 0) 
        {
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
            print(this, "checkBenefitDuplicacy :: " + pstmt.toString());
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

    public int createBenefitQuestion(JobPostInfo info) 
    {
        int benefitquestionId = 0;
        try
        {
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
        } catch (Exception exception) {
            print(this, "createBenefitQuestion :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return benefitquestionId;
    }

    public ArrayList getEmployeeJobPostBenefitDetailByIdforEdit(int jobpostId) 
    {
        ArrayList jobBenefit = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT t_benefit.i_benefitid, t_benefit.s_name, t_benefit.i_type, t_jobpostbenefit.i_benefitquestionid, "
                + " t_jobpostbenefit.i_currencyid, t_jobpostbenefit.s_info ");
        sb.append("FROM t_benefit ");
        sb.append("LEFT JOIN t_jobpostbenefit ON (t_benefit.i_benefitid = t_jobpostbenefit.i_benefitid and t_jobpostbenefit.i_jobpostid = ?) where t_benefit.i_status = 1 order by t_benefit.s_name");
        String query = sb.toString().intern();
        sb.setLength(0);
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, jobpostId);
            print(this, "getEmployeeJobPostBenefitDetailByIdforEdit :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String positionbenefitinfo, benefitname, codenum;
            int benefitId, benefittype, benefitquestionId, currencyId;
            while (rs.next())
            {
                benefitId = rs.getInt(1);
                benefitname = rs.getString(2) != null ? rs.getString(2): "";
                benefittype = rs.getInt(3);
                benefitquestionId = rs.getInt(4);
                currencyId = rs.getInt(5);
                positionbenefitinfo = rs.getString(6) != null ? rs.getString(6): "";
                codenum = changeNum(benefitId, 3);

                if (benefittype == 3) {
                    benefitquestionId = currencyId;
                }
                jobBenefit.add(new JobPostInfo(benefitId, benefitname, benefittype, benefitquestionId, positionbenefitinfo, codenum));
            }
            rs.close();
        } catch (Exception exception) {
            print(this, "getEmployeeJobPostBenefitDetailByIdforEdit :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return jobBenefit;
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
                list.add(new JobPostInfo(ddlValue, type, ddlLabel));
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
        sb.append("<option value='-1'>- Select -</option>");
        if (currency > 0) {
            if (size > 0) {
                for (int i = 0; i < size; i++) {
                    JobPostInfo info = (JobPostInfo) list.get(i);
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
            for (int i = 0; i < size; i++) {
                JobPostInfo info = (JobPostInfo) list.get(i);
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

    public int createJobPostBenefit(int jobpostId, int benefitId[], int benefittype[], int benefitquestionId[],
            String description[], int userId)
    {
        int cc = 0;
        Validate vobj = new Validate();
        try 
        {
            StringBuilder sb = new StringBuilder();
            sb.append("INSERT INTO t_jobpostbenefit ");
            sb.append("(i_jobpostid, i_benefitid, i_benefitquestionid, i_currencyid, s_info, ");
            sb.append("i_status, i_userid, ts_regdate, ts_moddate) ");
            sb.append("VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");
            String query = (sb.toString()).intern();
            sb.setLength(0);
            conn = getConnection();
            String delqery = "DELETE FROM t_jobpostbenefit WHERE i_jobpostid = ?";
            pstmt = conn.prepareStatement(delqery);
            pstmt.setInt(1, jobpostId);
            pstmt.executeUpdate();

            pstmt = conn.prepareStatement(query);
            int size = benefitId.length;
            int scc = 0;
            for (int i = 0; i < size; i++) {
                if (benefitquestionId[i] > 0) {
                    scc = 0;
                    pstmt.setInt(++scc, jobpostId);
                    pstmt.setInt(++scc, benefitId[i]);
                    if (benefittype[i] == 3) {
                        pstmt.setInt(++scc, 0);
                        pstmt.setInt(++scc, benefitquestionId[i]);
                    } else {
                        pstmt.setInt(++scc, benefitquestionId[i]);
                        pstmt.setInt(++scc, 0);
                    }
                    pstmt.setString(++scc, vobj.replacedesc(description[i]));
                    pstmt.setInt(++scc, 1);
                    pstmt.setInt(++scc, userId);
                    pstmt.setString(++scc, currDate1());
                    pstmt.setString(++scc, currDate1());
                    print(this, "createJobpostBenefit :: " + pstmt.toString());
                    int inserted = pstmt.executeUpdate();
                    if (inserted > 0) {
                        cc++;
                    }
                }
            }
        } catch (Exception exception) {
            print(this, "createJobpostBenefit :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return cc;
    }

    public int deleteBenefit(int jobpostId, int jobpostBenefitId, int userId, int status, String ipAddrStr, String iplocal) {
        int cc = 0;
        try {
            conn = getConnection();
            StringBuilder sb = new StringBuilder();
            sb.append("DELETE FROM t_jobpostbenefit WHERE i_jobpostbenefitid = ? ");
            String query = (sb.toString()).intern();
            sb.setLength(0);

            pstmt = conn.prepareStatement(query);
            int scc = 0;
            pstmt.setInt(++scc, jobpostBenefitId);
            print(this, "deleteBenefit :: " + pstmt.toString());
            pstmt.execute();
        } catch (Exception exception) {
            print(this, "deleteBenefit :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        createHistoryAccess(null, userId, ipAddrStr, iplocal, "Data Deleted", 45, jobpostBenefitId);
        return cc;
    }

    public ArrayList getAssessmentList(int jobpostId) 
    {
        ArrayList list = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT t_jobpostassessment.i_jobpostassessmentid, t_assessment.i_assessmentid, t_assessment.s_name,"
                + " t_jobpostassessment.i_minscore, t_jobpostassessment.i_passingflag, t2.names, t_jobpostassessment.i_status"
                + " from t_jobpostassessment ");
        sb.append("LEFT JOIN t_assessment on (t_assessment.i_assessmentid = t_jobpostassessment.i_assessmentid) ");
        sb.append("LEFT JOIN (select t_assessment.i_assessmentid, GROUP_CONCAT(t1.s_name order by t1.s_name SEPARATOR ', ') as names from t_assessment ");
        sb.append("LEFT JOIN t_assessmentparameter as t1 on find_in_set(t1.i_assessmentparameterid, t_assessment.s_parameterids) ");
        sb.append("group by t_assessment.i_assessmentid) as t2 on (t2.i_assessmentid = t_assessment.i_assessmentid) ");
        sb.append("where t_jobpostassessment.i_jobpostid = ? ");
        sb.append("order by t_jobpostassessment.i_status, t_assessment.s_name ");
        String query = (sb.toString()).intern();
        sb.setLength(0);
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, jobpostId);
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
                list.add(new JobPostInfo(assessmentDetailId, assessmentId, assessmentName, minScore, passingFlag, parameter, status));
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return list;
    }

    public int deleteAssessment(int jobpostId, int assessmentDetailId, int userId, int status, String ipAddrStr, String iplocal) {
        int cc = 0;
        try 
        {
            conn = getConnection();
            StringBuilder sb = new StringBuilder();
            sb.append("update t_jobpostassessment set ");
            sb.append("i_status = ?, ");
            sb.append("i_userid = ?, ");
            sb.append("ts_moddate = ? ");
            sb.append("where i_jobpostassessmentid = ? and i_jobpostid = ? ");
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
            pstmt.setInt(++scc, jobpostId);
            cc = pstmt.executeUpdate();
        } catch (Exception exception) {
            print(this, "deletePositionAssessment :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        createHistoryAccess(null, userId, ipAddrStr, iplocal, "Data Deleted", 45, assessmentDetailId);
        return cc;
    }

    public JobPostInfo getAssessmentDetailByJobPostId(int assessmentDetailId) 
    {
        JobPostInfo info = null;
        String query = "select i_assessmentid, i_minscore, i_passingflag from t_jobpostassessment where i_jobpostassessmentid = ? ";
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, assessmentDetailId);
            rs = pstmt.executeQuery();
            while (rs.next())
            {
                int assessmentId = rs.getInt(1);
                int minScore = rs.getInt(2);
                int passingFlag = rs.getInt(3);
                info = new JobPostInfo(assessmentDetailId, assessmentId, minScore, passingFlag);
            }
            rs.close();
            pstmt.close();

        } catch (Exception exception) {
            print(this, "getAssessmentDetailByJobPostId :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return info;
    }

    public int insertAssessmentDetail(JobPostInfo info, int jobpostId, int userId) 
    {
        int assessmentId = 0;
        try
        {
            StringBuilder sb = new StringBuilder();
            sb.append("insert into t_jobpostassessment  ");
            sb.append("(i_jobpostid, i_assessmentid, i_minscore, i_passingflag ,i_status, ts_regdate, ts_moddate) ");
            sb.append("values (?, ?, ?, ?, ?, ?, ?)");
            String query = (sb.toString()).intern();
            sb.setLength(0);
            conn = getConnection();
            pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            logger.log(Level.INFO, "t_jobpostassessment :: {0}", pstmt.toString());
            int scc = 0;
            pstmt.setInt(++scc, jobpostId);
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

    public int updateAssessmentDetail(JobPostInfo info, int userId, int jobpostId, int assessmentDetailId)
    {
        StringBuilder sb = new StringBuilder();
        int cc = 0;
        try 
        {
            conn = getConnection();
            sb.append("update t_jobpostassessment set  ");
            sb.append(" i_assessmentid = ?, ");
            sb.append(" i_minscore = ?, ");
            sb.append(" i_passingflag = ?, ");
            sb.append(" i_userid = ?, ts_moddate = ?  where  i_jobpostid = ? and i_jobpostassessmentid = ? ");
            String query = (sb.toString()).intern();
            sb.setLength(0);
            int scc = 0;
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(++scc, info.getAssessmentId());
            pstmt.setInt(++scc, info.getMinScore());
            pstmt.setInt(++scc, info.getPassingFlag());
            pstmt.setInt(++scc, userId);
            pstmt.setString(++scc, currDate1());
            pstmt.setInt(++scc, jobpostId);
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

    public int checkDuplicacyAssessment(int jobpostId, int assessmentDetailId, int assessmentId)
    {
        int ck = 0;
        StringBuilder sb = new StringBuilder();
        sb.append("select i_jobpostassessmentid FROM t_jobpostassessment where i_jobpostid = ? and i_assessmentid = ? and  i_status  in (1, 2)");
        if (assessmentDetailId > 0) {
            sb.append(" and i_jobpostassessmentid != ? ");
        }
        String query = (sb.toString()).intern();
        sb.setLength(0);
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            int scc = 0;
            pstmt.setInt(++scc, jobpostId);
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
        try {
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
                    list.add(new JobPostInfo(jobj.optInt("id"), jobj.optString("name")));
                }
            }
        }
        return list;
    }

    public Collection getClients(String cids, int allclient, String permission) {
        Collection coll = new LinkedList();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT i_clientid, s_name FROM t_client where i_status = 1 ");
        if (allclient == 1 || permission.equals("Y")) {
            sb.append("AND i_clientid > 0 ");
        } else {
            if (!cids.equals("")) {
                sb.append("AND i_clientid IN (" + cids + ") ");
            } else {
                sb.append("AND i_clientid < 0 ");
            }
        }
        sb.append("order by s_name");
        String query = (sb.toString()).intern();
        sb.setLength(0);
        coll.add(new JobPostInfo(-1, " Select Client"));
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
                coll.add(new JobPostInfo(refId, refName));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return coll;
    }
    
    public int createMailLogForJobpost(Connection conn, int type, String name, String to, String cc, 
            String bcc, String from, String subject, int candidateId, String username, String filename, int notificationId) 
    {
        int count = 0;
        try 
        {
            StringBuilder sb = new StringBuilder();
            sb.append("INSERT INTO t_maillog ");
            sb.append("(i_type, s_name, s_to, s_cc, s_bcc, s_from, s_subject, s_filename, ts_regdate, ts_moddate, i_candidateid, s_sendby, i_notificationid) ");
            sb.append("VALUES (?, ?, ?, ?, ?,   ?, ?, ?, ?, ?,   ?, ?, ?)");
            String query = sb.toString().intern();
            sb.setLength(0);
            pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            pstmt.setInt(1, type);
            pstmt.setString(2, name);
            pstmt.setString(3, to);
            pstmt.setString(4, cc);
            pstmt.setString(5, bcc);
            pstmt.setString(6, from);
            pstmt.setString(7, subject);
            pstmt.setString(8, filename);
            pstmt.setString(9, currDate1());
            pstmt.setString(10, currDate1());
            pstmt.setInt(11, candidateId);
            pstmt.setString(12, username);
            pstmt.setInt(13, notificationId);
            print(this, "createMailLogForJobpost :: " + pstmt.toString());
            pstmt.executeUpdate();
            rs = pstmt.getGeneratedKeys();
            while (rs.next()) {
                count = rs.getInt(1);
            }
        }
        catch (Exception exception)
        {
            print(this, "createMailLogForJobpost :: " + exception.getMessage());
        } 
        finally 
        {
            close(null, pstmt, null);
        }
        return count;
    }
    
    public String getpatchbody(String description, String htmlFile) {
        String adminpage = getMainPath("template_path") + htmlFile;
        HashMap hashmap = new HashMap();
        Template template = new Template(adminpage);
        hashmap.put("MAILBODY", description);
        return template.patch(hashmap);
    }
    
    public int sendJobpostEmail(String username, int userId, String remarks, int jobpostId)
    {
        int arr = 0, clientId = 0, assetId= 0;
        String client= "", asset= "", position ="", grade="", date ="", email="", email2="", toVal ="",
                ccVal = "",  toVal2 =""; 
        if(jobpostId > 0)
        { 
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT t_client.s_name, t_clientasset.s_name, t_position.s_name, t_grade.s_name, ");
            sb.append("DATE_FORMAT(t_jobpost.ts_regdate,'%D-%b-%Y'), t_jobpost.i_clientid, t_jobpost.i_clientassetid ");
            sb.append("FROM t_jobpost ");
            sb.append("LEFT JOIN t_client ON (t_jobpost.i_clientid = t_client.i_clientid) "); 
            sb.append("LEFT JOIN t_clientasset ON (t_jobpost.i_clientassetid = t_clientasset.i_clientassetid) "); 
            sb.append("LEFT JOIN t_position ON (t_jobpost.i_positionid = t_position.i_positionid) ");
            sb.append("LEFT JOIN t_grade ON (t_jobpost.i_gradeid = t_grade.i_gradeid) ");
            sb.append("WHERE t_jobpost.i_jobpostid = ?");
            String query = sb.toString();
            sb.setLength(0);
            try
            {
                conn = getConnection();
                pstmt = conn.prepareStatement(query);
                pstmt.setInt(1, jobpostId);
                logger.info("Jobpost deatils :: " + pstmt.toString());
                ResultSet rs = pstmt.executeQuery();
                while (rs.next())
                {
                    client = rs.getString(1) != null ? rs.getString(1): "";
                    asset = rs.getString(2) != null ? rs.getString(2): "";
                    position = rs.getString(3) != null ? rs.getString(3): "";
                    grade = rs.getString(4) != null ? rs.getString(4): "";
                    date = rs.getString(5) != null ? rs.getString(5): "";
                    clientId = rs.getInt(6);
                    assetId = rs.getInt(7);
               
                    String query2 = ("SELECT GROUP_CONCAT(DISTINCT i_userid ORDER BY s_email SEPARATOR ', ') FROM t_userlogin WHERE i_cflag = 1"); //coordinator emails for to field
                    pstmt = conn.prepareStatement(query2);
                    logger.info("Coordinator ids :: " + pstmt.toString());
                    rs = pstmt.executeQuery();
                    while (rs.next())
                    {
                        email = rs.getString(1) != null ? rs.getString(1): "";     
                        if (!email.equals("")) 
                        {
                            String query1 = "SELECT GROUP_CONCAT(s_email) FROM t_userlogin WHERE i_userid IN (" + email + ")";
                            PreparedStatement pstmt1 = conn.prepareStatement(query1);
                            logger.info("email cids :: " + pstmt.toString());
                            ResultSet rs1 = pstmt1.executeQuery();
                            while (rs1.next()) {
                                toVal = rs1.getString(1) != null ? rs1.getString(1) : "";
                            }
                        }
                    }
                    query2 = ("SELECT GROUP_CONCAT(DISTINCT i_userid ORDER BY s_email SEPARATOR ', ') FROM t_userlogin WHERE i_rflag = 1"); //recruiter emails for to field
                    pstmt = conn.prepareStatement(query2);
                    logger.info("recruiter ids :: " + pstmt.toString());
                    rs = pstmt.executeQuery();
                    while (rs.next())
                    {
                        email2 = rs.getString(1) != null ? rs.getString(1): "";     
                        if (!email2.equals("")) 
                        {
                            String query1 = "SELECT GROUP_CONCAT(s_email) FROM t_userlogin WHERE i_userid IN (" + email2 + ")";
                            PreparedStatement pstmt1 = conn.prepareStatement(query1);
                            logger.info("email rids :: " + pstmt.toString());
                            ResultSet rs1 = pstmt1.executeQuery();
                            while (rs1.next()) {
                                toVal2 = rs1.getString(1) != null ? rs1.getString(1) : "";
                            }
                        }
                    }
                    if(!toVal2.equals(""))
                    {
                        toVal += ","+ toVal2;
                    }
                    
                    if(!email.equals(""))
                    {
                        String message = "", subject = "";
                        message = "Dear Team, "+"\n\nA new job post has been successfully created in the system.\n\n"+
                        "Details: \n"+
                        "Job Title: "+ position+"-"+grade+"\n"+
                        "Client: "+client+"\n"+
                        "Asset: "+asset+"\n"+
                        "Posted On: "+date+"\n\n"+

                        "You can review the job post and make any necessary updates through JourneyXPro";
                        subject = "New Job Post Created";

                        String sdescription = message.replaceAll("\n", "<br/>");
                        String mailbody = getpatchbody(sdescription, "notification_mail.html");

                        String file_maillog = getMainPath("file_maillog");
                        java.util.Date nowmail = new java.util.Date();
                        String fn_mail = "new_jobpost-" + String.valueOf(nowmail.getTime());
                        String filePath_html = createFolder(file_maillog);
                        String fname = writeHTMLFile(mailbody, file_maillog + "/" + filePath_html, fn_mail + ".html");
                        String receipent[] = parseCommaDelimString(toVal);
                        
                        String from = "";
                        String cc[] = parseCommaDelimString(ccVal);
                        String bcc[] = new String[0];
                        try
                        {
                            StatsInfo sinfo = postMailAttach(receipent, cc, bcc, mailbody, subject, "", "", -1);
                            int flag = 0;
                            if(sinfo != null)
                            {
                                flag = sinfo.getDdlValue();
                                from = sinfo.getDdlLabel();                            
                            }
                            if(flag > 0)
                            {
                                Documentexpiry doc = new Documentexpiry();
                                int notificationId = doc.createNotification(userId, 3, 7, jobpostId, clientId, assetId, remarks, username);
                                if(notificationId > 0)
                                   createMailLogForJobpost(conn, 21, username, toVal, ccVal, "", from, subject, userId, username, filePath_html+ "/" +fname, notificationId);
                            }
                        }
                        catch(Exception e)
                        {
                           print(this, "sendJobpostEmail :: " + e.getMessage());
                        }
                    }
                }
                rs.close();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            finally
            {
                close(conn, pstmt, rs);
            }
        }
        return arr;
    }

}
