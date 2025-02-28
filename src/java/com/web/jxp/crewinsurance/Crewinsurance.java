package com.web.jxp.crewinsurance;

import com.mysql.jdbc.Statement;
import com.web.jxp.base.Base;
import com.web.jxp.base.StatsInfo;
import com.web.jxp.base.Template;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import static com.web.jxp.common.Common.*;
import com.web.jxp.documentexpiry.Documentexpiry;
import java.util.Collection;
import java.util.LinkedList;
import java.util.logging.Logger;

public class Crewinsurance extends Base {

    private static final Logger logger = Logger.getLogger(Class.class.getName());
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    public ArrayList getCrewinsuranceByName(String search, int statusIndex, int positionIdIndex,
            int clientIdIndex, int assetIdIndex, int allclient, String permission,
            String cids, String assetids) {
        ArrayList crewinsurances = new ArrayList();
        StringBuilder sb = new StringBuilder();
        if (clientIdIndex > 0 && assetIdIndex > 0) {
            sb.append("SELECT cr.i_crewrotationid, CONCAT_WS(' ', c.s_firstname, c.s_middlename, c.s_lastname), t_position.s_name, ");
            sb.append("t_grade.s_name, t1.i_status, DATEDIFF(t1.d_todate, CURRENT_DATE()), t1.i_insuranceid, ");
            sb.append("DATE_FORMAT(t1.d_fromdate, '%d-%b-%Y'), DATE_FORMAT(t1.d_todate, '%d-%b-%Y'), t1.i_dependent, t1.s_filename ");
            sb.append("FROM t_crewrotation AS cr ");
            sb.append("LEFT JOIN t_candidate AS c ON (c.i_candidateid = cr.i_candidateid ) ");
            sb.append("LEFT JOIN t_position ON (t_position.i_positionid = c.i_positionid) ");
            sb.append("LEFT JOIN t_grade ON (t_grade.i_gradeid = t_position.i_gradeid) ");
            sb.append("LEFT JOIN t_insurance AS t1 ON (cr.i_crewrotationid = t1.i_crewrotationid) ");
            sb.append("WHERE 0 = 0 AND cr.i_active = 1 ");

            if (allclient == 1) {
                sb.append(" AND cr.i_clientid > 0 ");
            } else {
                if (!cids.equals("")) {
                    sb.append("AND cr.i_clientid IN (" + cids + ") ");
                } else {
                    sb.append("AND cr.i_clientid < 0 ");
                }
                if (!assetids.equals("")) {
                    sb.append("AND cr.i_clientassetid IN (" + assetids + ") ");
                }
            }
            if ((positionIdIndex > 0)) {
                sb.append(" AND c.i_positionid = ? ");
            }
            if (clientIdIndex > 0) {
                sb.append("AND cr.i_clientid = ? ");
            }
            if ((assetIdIndex > 0)) {
                sb.append("AND cr.i_clientassetid = ? ");
            }

            if (statusIndex > 0) {
                if (statusIndex == 1) {
                    sb.append(" AND (t1.i_status = 0 OR t1.i_status is NULL) ");
                } else if (statusIndex == 2) {
                    sb.append(" AND (t1.i_status = 1 AND current_date() <= t1.d_todate )");
                } else if (statusIndex == 3) {
                    sb.append(" AND (t1.i_status > 0 AND t1.d_todate >= current_date() AND t1.d_todate <= current_date() + INTERVAL '45' DAY) ");
                } else if (statusIndex == 4) {
                    sb.append(" AND (t1.i_status > 0 AND t1.d_todate > current_date() + INTERVAL '45' DAY AND t1.d_todate <= current_date() + INTERVAL '65' DAY)  ");
                } else if (statusIndex == 5) {
                    sb.append(" AND (t1.i_status > 0 AND t1.d_todate > current_date() + INTERVAL '65' DAY AND t1.d_todate <= current_date() + INTERVAL '90' DAY)  ");
                } else if (statusIndex == 6) {
                    sb.append(" AND (t1.i_status > 0 AND current_date() > t1.d_todate ) ");
                }
            }

            if (search != null && !search.equals("")) {
                sb.append("AND (CONCAT_WS(' ', c.s_firstname, c.s_middlename, c.s_lastname) LIKE ? OR CONCAT_WS(' ', c.s_firstname, c.s_lastname) LIKE ? ");
                sb.append("OR t_position.s_name LIKE ? OR t_grade.s_name LIKE ?) ");
            }
            sb.append(" ORDER BY c.s_firstname, c.s_middlename, c.s_lastname ");

            String query = (sb.toString()).intern();

            sb.setLength(0);
            try {
                conn = getConnection();
                pstmt = conn.prepareStatement(query);
                int scc = 0;
                if (positionIdIndex > 0) {
                    pstmt.setInt(++scc, positionIdIndex);
                }
                if ((clientIdIndex > 0)) {
                    pstmt.setInt(++scc, clientIdIndex);
                }
                if ((assetIdIndex > 0)) {
                    pstmt.setInt(++scc, assetIdIndex);
                }
                if (search != null && !search.equals("")) {
                    pstmt.setString(++scc, "%" + (search) + "%");
                    pstmt.setString(++scc, "%" + (search) + "%");
                    pstmt.setString(++scc, "%" + (search) + "%");
                    pstmt.setString(++scc, "%" + (search) + "%");
                }
                logger.info("getCrewinsuranceByName :: " + pstmt.toString());
                rs = pstmt.executeQuery();
                String name, position, grade, fromdate, todate, filename;
                int crewrotationId, status, datedifference, crewinsuranceId, dependent;
                while (rs.next()) {
                    crewrotationId = rs.getInt(1);
                    name = rs.getString(2) != null ? rs.getString(2) : "";
                    position = rs.getString(3) != null ? rs.getString(3) : "";
                    grade = rs.getString(4) != null ? rs.getString(4) : "";
                    status = rs.getInt(5);
                    datedifference = rs.getInt(6);
                    crewinsuranceId = rs.getInt(7);
                    fromdate = rs.getString(8) != null ? rs.getString(8) : "";
                    todate = rs.getString(9) != null ? rs.getString(9) : "";
                    dependent = rs.getInt(10);
                    filename = rs.getString(11) != null ? rs.getString(11) : "";

                    String stval = "";
                    if (status == 0) {
                        stval = "Pending";
                    } else if (datedifference < 0) {
                        stval = "Expired";
                    } else if (datedifference > 90) {
                        stval = "Uploaded";
                    } else {
                        stval = "Expiry in " + datedifference + " days";
                    }

                    if (!position.equals("")) {
                        if (!grade.equals("")) {
                            position += " - " + grade;
                        }
                    }
                    crewinsurances.add(new CrewinsuranceInfo(crewrotationId, name, position, grade, status, datedifference, 0,
                            clientIdIndex, assetIdIndex, stval, crewinsuranceId, fromdate, todate, dependent, filename));
                }
                rs.close();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                close(conn, pstmt, rs);
            }
        }
        return crewinsurances;
    }

    public ArrayList getFinalRecord(ArrayList l, String colId, int tp) {
        ArrayList list = null;
        HashMap record = null;
        CrewinsuranceInfo info;
        int total = 0;
        if (l != null) {
            total = l.size();
        }
        try {
            if (total > 0) {
                record = new HashMap();
                int i;
                for (i = 0; i < total; i++) {
                    info = (CrewinsuranceInfo) l.get(i);
                    record.put(getInfoValue(info, colId), info);
                }
            }
            Map map = null;
            if (colId.equals("1") || colId.equals("2")) {
                map = sortByName(record, tp);
            } else {
                map = sortById(record, tp);
            }
            Iterator it = map.keySet().iterator();
            list = new ArrayList();
            CrewinsuranceInfo rInfo;
            while (it.hasNext()) {
                String key = (String) it.next();
                int i;
                for (i = 0; i < total; i++) {
                    rInfo = (CrewinsuranceInfo) l.get(i);
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
    public String getInfoValue(CrewinsuranceInfo info, String i) {
        String infoval = "";
        if (i != null && i.equals("1")) {
            infoval = info.getName() != null ? info.getName() : "";
        } else if (i != null && i.equals("2")) {
            infoval = info.getPosition() != null ? info.getPosition() : "";
        } else if (i != null && i.equals("3")) {
            infoval = "" + info.getStatus();
        }
        return infoval;
    }

    public ArrayList getListForExcel(String search, int positionIdIndex, int clientIdIndex,
            int assetIdIndex, int allclient, String permission, String cids, String assetids, int statusIndex) {
        ArrayList crewinsurances = new ArrayList();
        StringBuilder sb = new StringBuilder();
        if (clientIdIndex > 0 && assetIdIndex > 0) {
            sb.append("SELECT cr.i_crewrotationid, CONCAT_WS(' ', c.s_firstname, c.s_middlename, c.s_lastname), t_position.s_name, ");
            sb.append("t_grade.s_name, t1.i_status, DATEDIFF(t1.d_todate, CURRENT_DATE()), t1.i_insuranceid, ");
            sb.append("DATE_FORMAT(t1.d_fromdate, '%d-%b-%Y'), DATE_FORMAT(t1.d_todate, '%d-%b-%Y'), t1.i_dependent, t1.s_filename ");
            sb.append("FROM t_crewrotation AS cr ");
            sb.append("LEFT JOIN t_candidate AS c ON (c.i_candidateid = cr.i_candidateid ) ");
            sb.append("LEFT JOIN t_position ON (t_position.i_positionid = c.i_positionid) ");
            sb.append("LEFT JOIN t_grade ON (t_grade.i_gradeid = t_position.i_gradeid) ");
            sb.append("LEFT JOIN t_insurance AS t1 ON (cr.i_crewrotationid = t1.i_crewrotationid) ");
            sb.append("WHERE 0 = 0 AND cr.i_active = 1 ");

            if (allclient == 1) {
                sb.append(" AND cr.i_clientid > 0 ");
            } else {
                if (!cids.equals("")) {
                    sb.append("AND cr.i_clientid IN (" + cids + ") ");
                } else {
                    sb.append("AND cr.i_clientid < 0 ");
                }
                if (!assetids.equals("")) {
                    sb.append("AND cr.i_clientassetid IN (" + assetids + ") ");
                }
            }
            if ((positionIdIndex > 0)) {
                sb.append(" AND c.i_positionid = ? ");
            }
            if (clientIdIndex > 0) {
                sb.append("AND cr.i_clientid = ? ");
            }
            if ((assetIdIndex > 0)) {
                sb.append("AND cr.i_clientassetid = ? ");
            }

            if (statusIndex > 0) {
                if (statusIndex == 1) {
                    sb.append(" AND (t1.i_status = 0 OR t1.i_status is NULL) ");
                } else if (statusIndex == 2) {
                    sb.append(" AND (t1.i_status = 1 AND current_date() <= t1.d_todate )");
                } else if (statusIndex == 3) {
                    sb.append(" AND (t1.i_status > 0 AND t1.d_todate >= current_date() AND t1.d_todate <= current_date() + INTERVAL '45' DAY) ");
                } else if (statusIndex == 4) {
                    sb.append(" AND (t1.i_status > 0 AND t1.d_todate > current_date() + INTERVAL '45' DAY AND t1.d_todate <= current_date() + INTERVAL '65' DAY)  ");
                } else if (statusIndex == 5) {
                    sb.append(" AND (t1.i_status > 0 AND t1.d_todate > current_date() + INTERVAL '65' DAY AND t1.d_todate <= current_date() + INTERVAL '90' DAY)  ");
                } else if (statusIndex == 6) {
                    sb.append(" AND (t1.i_status > 0 AND current_date() > t1.d_todate ) ");
                }
            }

            if (search != null && !search.equals("")) {
                sb.append("AND (CONCAT_WS(' ', c.s_firstname, c.s_middlename, c.s_lastname) LIKE ? OR CONCAT_WS(' ', c.s_firstname, c.s_lastname) LIKE ? ");
                sb.append("OR t_position.s_name LIKE ? OR t_grade.s_name LIKE ?) ");
            }
            sb.append(" ORDER BY c.s_firstname, c.s_middlename, c.s_lastname ");

            String query = (sb.toString()).intern();

            sb.setLength(0);
            try {
                conn = getConnection();
                pstmt = conn.prepareStatement(query);
                int scc = 0;
                if (positionIdIndex > 0) {
                    pstmt.setInt(++scc, positionIdIndex);
                }
                if ((clientIdIndex > 0)) {
                    pstmt.setInt(++scc, clientIdIndex);
                }
                if ((assetIdIndex > 0)) {
                    pstmt.setInt(++scc, assetIdIndex);
                }
                if (search != null && !search.equals("")) {
                    pstmt.setString(++scc, "%" + (search) + "%");
                    pstmt.setString(++scc, "%" + (search) + "%");
                    pstmt.setString(++scc, "%" + (search) + "%");
                    pstmt.setString(++scc, "%" + (search) + "%");
                }
                logger.info("getCrewinsuranceByName :: " + pstmt.toString());
                rs = pstmt.executeQuery();
                String name, position, grade, fromdate, todate, filename;
                int crewrotationId, status, datedifference, crewinsuranceId, dependent;
                while (rs.next()) {
                    crewrotationId = rs.getInt(1);
                    name = rs.getString(2) != null ? rs.getString(2) : "";
                    position = rs.getString(3) != null ? rs.getString(3) : "";
                    grade = rs.getString(4) != null ? rs.getString(4) : "";
                    status = rs.getInt(5);
                    datedifference = rs.getInt(6);
                    crewinsuranceId = rs.getInt(7);
                    fromdate = rs.getString(8) != null ? rs.getString(8) : "";
                    todate = rs.getString(9) != null ? rs.getString(9) : "";
                    dependent = rs.getInt(10);
                    filename = rs.getString(11) != null ? rs.getString(11) : "";

                    String stval = "";
                    if (status == 0) {
                        stval = "Pending";
                    } else if (datedifference < 0) {
                        stval = "Expired";
                    } else if (datedifference > 90) {
                        stval = "Uploaded";
                    } else {
                        stval = "Expiry in " + datedifference + " days";
                    }

                    if (!position.equals("")) {
                        if (!grade.equals("")) {
                            position += " - " + grade;
                        }
                    }
                    crewinsurances.add(new CrewinsuranceInfo(crewrotationId, name, position, grade, status, datedifference, 0,
                            clientIdIndex, assetIdIndex, stval, crewinsuranceId, fromdate, todate, dependent, filename));
                }
                rs.close();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                close(conn, pstmt, rs);
            }
        }
        return crewinsurances;
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
            coll.add(new CrewinsuranceInfo(-1, " Select Asset "));
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
                    coll.add(new CrewinsuranceInfo(ddlValue, ddlLabel));
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                close(conn, pstmt, rs);
            }
        } else {
            coll.add(new CrewinsuranceInfo(-1, " Select Asset "));
        }
        return coll;
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
        coll.add(new CrewinsuranceInfo(-1, " Select Client"));
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            rs = pstmt.executeQuery();
            int refId;
            String refName;
            while (rs.next()) {
                refId = rs.getInt(1);
                refName = rs.getString(2) != null ? rs.getString(2) : "";
                coll.add(new CrewinsuranceInfo(refId, refName));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return coll;
    }

    public Collection getPostions(int clientassetId) {
        Collection coll = new LinkedList();
        coll.add(new CrewinsuranceInfo(-1, " All"));
        if (clientassetId > 0) {
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT t_position.i_positionid, t_position.s_name, t_grade.s_name ");
            sb.append("FROM t_clientassetposition LEFT JOIN t_position ON (t_position.i_positionid = t_clientassetposition.i_positionid) ");
            sb.append("LEFT JOIN t_grade ON (t_grade.i_gradeid = t_position.i_gradeid) ");
            sb.append("WHERE t_position.i_status = 1 AND t_clientassetposition.i_clientassetid = ? ORDER BY t_position.s_name, t_grade.s_name ");
            String query = sb.toString();
            sb.setLength(0);
            try {
                conn = getConnection();
                pstmt = conn.prepareStatement(query);
                pstmt.setInt(1, clientassetId);
                logger.info("getPositions :: " + pstmt.toString());
                rs = pstmt.executeQuery();
                int id;
                String name, gradeName;
                while (rs.next()) {
                    id = rs.getInt(1);
                    name = rs.getString(2) != null ? rs.getString(2) : "";
                    gradeName = rs.getString(3) != null ? rs.getString(3) : "";
                    if (!gradeName.equals("")) {
                        name += " - " + gradeName;
                    }
                    coll.add(new CrewinsuranceInfo(id, name));
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                close(conn, pstmt, rs);
            }
        }
        return coll;
    }

    public int createCrewinsurance(CrewinsuranceInfo info) {
        int exist = 0;
        try {
            conn = getConnection();
            StringBuilder sb1 = new StringBuilder();
            sb1.append(" SELECT i_insuranceid  FROM t_insurance WHERE i_crewrotationid  = ? ");
            String query1 = (sb1.toString()).intern();
            pstmt = conn.prepareStatement(query1);
            pstmt.setInt(1, info.getCrewrotationId());
            logger.info("getCrewDetalis :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            //int exist = 0;
            while (rs.next()) {
                exist = rs.getInt(1);
            }
            rs.close();

            if (exist <= 0) {
                StringBuilder sb = new StringBuilder();
                sb.append("INSERT INTO t_insurance ");
                sb.append("(i_crewrotationid, d_fromdate, d_todate, i_status, i_dependent, s_filename, i_userid, ts_regdate, ts_moddate, s_nomineeids) ");
                sb.append("VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
                String query = (sb.toString()).intern();
                sb.setLength(0);

                pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                int scc = 0;
                pstmt.setInt(++scc, info.getCrewrotationId());
                pstmt.setString(++scc, changeDate1(info.getFromDate()));
                pstmt.setString(++scc, changeDate1(info.getToDate()));
                pstmt.setInt(++scc, info.getStatus());
                pstmt.setInt(++scc, info.getDependent());
                pstmt.setString(++scc, (info.getFilename()));
                pstmt.setInt(++scc, info.getUserId());
                pstmt.setString(++scc, currDate1());
                pstmt.setString(++scc, currDate1());
                pstmt.setString(++scc, info.getSelectedNominee());
                print(this, "createCrewinsurance :: " + pstmt.toString());
                pstmt.executeUpdate();
                rs = pstmt.getGeneratedKeys();
                while (rs.next()) {
                    exist = rs.getInt(1);
                }
                rs.close();
            } else if (exist > 0) {
                StringBuilder sb = new StringBuilder();
                sb.append("UPDATE t_insurance SET ");
                sb.append("d_fromdate = ?, ");
                sb.append("d_todate = ?, ");
                sb.append("i_status = ?, ");
                sb.append("i_dependent = ?, ");
                if (info.getFilename() != null && !info.getFilename().equals("")) {
                    sb.append("s_filename = ?, ");
                }
                sb.append("i_userid = ?, ");
                sb.append("ts_moddate = ?, ");
                sb.append("s_nomineeids = ? ");
                sb.append("WHERE i_insuranceid = ? ");
                String query = (sb.toString()).intern();
                sb.setLength(0);

                conn = getConnection();
                pstmt = conn.prepareStatement(query);
                int scc = 0;
                pstmt.setString(++scc, changeDate1(info.getFromDate()));
                pstmt.setString(++scc, changeDate1(info.getToDate()));
                pstmt.setInt(++scc, info.getStatus());
                pstmt.setInt(++scc, info.getDependent());
                if (info.getFilename() != null && !info.getFilename().equals("")) {
                    pstmt.setString(++scc, info.getFilename());
                }
                pstmt.setInt(++scc, info.getUserId());
                pstmt.setString(++scc, currDate1());
                pstmt.setString(++scc, info.getSelectedNominee());
                pstmt.setInt(++scc, exist);
                print(this, "updateCrewinsurance :: " + pstmt.toString());
                scc = pstmt.executeUpdate();
            }
        } catch (Exception exception) {
            print(this, "createCrewinsurance :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return exist;

    }

    public int[] getTopCounts(int assetIdIndex) {
        int arr[] = new int[7];
        int total_candidate = 0, pending_count = 0, uploaded_count = 0, expired1 = 0, expired2 = 0, expired3 = 0, expired = 0;
        if (assetIdIndex > 0) {
            String query = ("SELECT COUNT(1) FROM t_crewrotation WHERE i_active = 1 AND i_clientassetid = ? "); //total_candidate
            try {
                conn = getConnection();
                pstmt = conn.prepareStatement(query);
                pstmt.setInt(1, assetIdIndex);
                logger.info("getTopCounts :: " + pstmt.toString());
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    total_candidate = rs.getInt(1);
                }
                rs.close();

                query = ("SELECT COUNT(1) AS ct FROM t_insurance "
                        + " LEFT JOIN t_crewrotation ON (t_crewrotation.i_crewrotationid = t_insurance.i_crewrotationid) "
                        + " WHERE t_insurance.i_status = 1 AND t_crewrotation.i_clientassetid = ? AND t_crewrotation.i_active = 1 AND CURRENT_DATE() <= t_insurance.d_todate "); //uploaded_count
                pstmt = conn.prepareStatement(query);
                pstmt.setInt(1, assetIdIndex);
                logger.info("getCounts :: " + pstmt.toString());
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    uploaded_count = rs.getInt(1);
                }
                rs.close();

                query = ("SELECT COUNT(1) AS ct FROM t_insurance "
                        + " LEFT JOIN t_crewrotation ON (t_crewrotation.i_crewrotationid = t_insurance.i_crewrotationid) "
                        + " WHERE t_crewrotation.i_clientassetid = ? AND t_crewrotation.i_active = 1 AND (t_insurance.d_todate >= current_date() AND t_insurance.d_todate <= current_date() + INTERVAL '45' DAY) "); //expire in 45 days

                pstmt = conn.prepareStatement(query);
                pstmt.setInt(1, assetIdIndex);
                logger.info("getCounts :: " + pstmt.toString());
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    expired1 = rs.getInt(1);
                }
                rs.close();

                query = ("SELECT COUNT(1) AS ct FROM t_insurance "
                        + "LEFT JOIN t_crewrotation ON (t_crewrotation.i_crewrotationid = t_insurance.i_crewrotationid) "
                        + "WHERE t_crewrotation.i_clientassetid = ? AND t_crewrotation.i_active = 1 AND (t_insurance.d_todate > current_date() + INTERVAL '45' DAY AND t_insurance.d_todate <= current_date() + INTERVAL '65' DAY) "); //expire in 45 - 65 days
                pstmt = conn.prepareStatement(query);
                pstmt.setInt(1, assetIdIndex);
                logger.info("getCounts :: " + pstmt.toString());
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    expired2 = rs.getInt(1);
                }
                rs.close();
                query = ("SELECT COUNT(1) AS ct FROM t_insurance "
                        + "LEFT JOIN t_crewrotation ON (t_crewrotation.i_crewrotationid = t_insurance.i_crewrotationid) "
                        + " WHERE t_crewrotation.i_clientassetid = ? AND t_crewrotation.i_active = 1 AND (t_insurance.d_todate > current_date() + INTERVAL '65' DAY AND t_insurance.d_todate <= current_date() + INTERVAL '90' DAY) "); //expire in 65 - 90 days
                pstmt = conn.prepareStatement(query);
                pstmt.setInt(1, assetIdIndex);
                logger.info("getCounts :: " + pstmt.toString());
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    expired3 = rs.getInt(1);
                }
                rs.close();

                query = ("SELECT COUNT(1) AS ct FROM t_insurance "
                        + " LEFT JOIN t_crewrotation ON (t_crewrotation.i_crewrotationid = t_insurance.i_crewrotationid) "
                        + " WHERE t_crewrotation.i_clientassetid = ? AND t_crewrotation.i_active = 1 AND current_date() > t_insurance.d_todate  "); //expired
                pstmt = conn.prepareStatement(query);
                pstmt.setInt(1, assetIdIndex);
                logger.info("getCounts :: " + pstmt.toString());
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    expired = rs.getInt(1);
                }
                rs.close();
                pending_count = total_candidate - uploaded_count;
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                close(conn, pstmt, rs);
            }
        }
        arr[0] = total_candidate;
        arr[1] = pending_count;
        arr[2] = uploaded_count;
        arr[3] = expired1;
        arr[4] = expired2;
        arr[5] = expired3;
        arr[6] = expired;
        return arr;
    }

    public CrewinsuranceInfo getCrewinsuranceDetailById(int crewrotationid, int crewinsuranceId) {
        CrewinsuranceInfo info = null;
        if (crewrotationid > 0) {
            StringBuilder sb = new StringBuilder();
            if (crewinsuranceId > 0) {
                sb.append("SELECT cr.i_crewrotationid, t_insurance.i_status, DATE_FORMAT(t_insurance.d_fromdate, '%d-%b-%Y'), ");
                sb.append("DATE_FORMAT(t_insurance.d_todate, '%d-%b-%Y'), t_insurance.i_dependent, t_insurance.s_filename, ");
                sb.append("CONCAT_WS(' ', c.s_firstname, c.s_middlename, c.s_lastname), t_position.s_name, t_grade.s_name, ");
                sb.append("DATEDIFF(t_insurance.d_todate, CURRENT_DATE()), c.i_candidateid, GROUP_CONCAT(DISTINCT t_insurance.s_nomineeids SEPARATOR ', ') ");
                sb.append("FROM t_insurance  ");
                sb.append("LEFT JOIN t_crewrotation AS cr ON (cr.i_crewrotationid = t_insurance.i_crewrotationid) ");
                sb.append("LEFT JOIN t_candidate AS c ON (c.i_candidateid = cr.i_candidateid ) ");
                sb.append("LEFT JOIN t_position ON (t_position.i_positionid = c.i_positionid) ");
                sb.append("LEFT JOIN t_grade ON (t_grade.i_gradeid = t_position.i_gradeid) ");
                sb.append("WHERE t_insurance.i_insuranceid = ? ");
            } else {
                sb.append("SELECT cr.i_crewrotationid, 0, '', '', 0, '', ");
                sb.append("CONCAT_WS(' ', c.s_firstname, c.s_middlename, c.s_lastname), t_position.s_name, t_grade.s_name, 0, c.i_candidateid, '' ");
                sb.append("FROM t_crewrotation AS cr  ");
                sb.append("LEFT JOIN t_candidate AS c ON (c.i_candidateid = cr.i_candidateid ) ");
                sb.append("LEFT JOIN t_position ON (t_position.i_positionid = c.i_positionid) ");
                sb.append("LEFT JOIN t_grade ON (t_grade.i_gradeid = t_position.i_gradeid) ");
                sb.append("WHERE cr.i_crewrotationid = ? ");
            }
            String query = sb.toString().intern();
            sb.setLength(0);
            try {
                conn = getConnection();
                pstmt = conn.prepareStatement(query);
                if (crewinsuranceId > 0) {
                    pstmt.setInt(1, crewinsuranceId);
                } else {
                    pstmt.setInt(1, crewrotationid);
                }
                print(this, "getCrewinsuranceDetailById :: " + pstmt.toString());
                rs = pstmt.executeQuery();
                String fromdate, todate, filename, name, position, grade, selectedNominee;
                int status, dependent, datedifference, candidateId;
                while (rs.next()) {
                    crewrotationid = rs.getInt(1);
                    status = rs.getInt(2);
                    fromdate = rs.getString(3) != null ? rs.getString(3) : "";
                    todate = rs.getString(4) != null ? rs.getString(4) : "";
                    dependent = rs.getInt(5);
                    filename = rs.getString(6) != null ? rs.getString(6) : "";
                    name = rs.getString(7) != null ? rs.getString(7) : "";
                    position = rs.getString(8) != null ? rs.getString(8) : "";
                    grade = rs.getString(9) != null ? rs.getString(9) : "";
                    datedifference = rs.getInt(10);
                    candidateId = rs.getInt(11);
                    selectedNominee = rs.getString(12) != null ? rs.getString(12) : "";
                    String stval = "";
                    if (status == 0) {
                        stval = "Pending";
                    } else if (datedifference < 0) {
                        stval = "Expired";
                    } else if (datedifference > 90) {
                        stval = "Uploaded";
                    } else {
                        stval = "Expiry in " + datedifference + " days";
                    }
                    if (!position.equals("")) {
                        if (!grade.equals("")) {
                            position += " | " + grade;
                        }
                    }
                    info = new CrewinsuranceInfo(crewrotationid, crewinsuranceId, status, fromdate, todate,
                            dependent, filename, name, position, grade, datedifference, stval, candidateId, selectedNominee);
                }
            } catch (Exception exception) {
                print(this, "getCrewinsuranceDetailById :: " + exception.getMessage());
            } finally {
                close(conn, pstmt, rs);
            }
        }
        return info;
    }

    public CrewinsuranceInfo getCrewinsuranceDetailById(int crewinsuranceId) {
        CrewinsuranceInfo info = null;
        if (crewinsuranceId > 0) {
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT i_crewrotationid, i_status, d_fromdate, d_todate, i_dependent, s_filename, GROUP_CONCAT(DISTINCT s_nomineeids SEPARATOR ', ') FROM t_insurance WHERE i_insuranceid = ? ");
            String query = sb.toString().intern();
            sb.setLength(0);
            try {
                conn = getConnection();
                pstmt = conn.prepareStatement(query);
                pstmt.setInt(1, crewinsuranceId);
                print(this, "getCrewinsuranceDetailById :: " + pstmt.toString());
                rs = pstmt.executeQuery();
                String fromdate, todate, filename, selectedNominee;
                int status, crewrotationid, dependent;
                while (rs.next()) {
                    crewrotationid = rs.getInt(1);
                    status = rs.getInt(2);
                    fromdate = rs.getString(3) != null ? rs.getString(3) : "";
                    todate = rs.getString(4) != null ? rs.getString(4) : "";
                    dependent = rs.getInt(5);
                    filename = rs.getString(6) != null ? rs.getString(6) : "";
                    selectedNominee = rs.getString(7) != null ? rs.getString(7) : "";

                    info = new CrewinsuranceInfo(crewinsuranceId, crewrotationid, status, fromdate, todate, dependent, filename, selectedNominee);
                }
            } catch (Exception exception) {
                print(this, "getCrewinsuranceDetailById :: " + exception.getMessage());
            } finally {
                close(conn, pstmt, rs);
            }
        }
        return info;
    }

    public int sendmailinsurance(int insuranceId, String username, int userId) {
        int sent = 0;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT CONCAT_WS(' ', c.s_firstname, c.s_middlename, c.s_lastname), c.s_email, ");
        sb.append("t_client.s_name, t_client.s_ocsuserids, cr.i_candidateid, t_insurance.s_filename ");
        sb.append("FROM t_insurance ");
        sb.append("LEFT JOIN t_crewrotation AS cr ON (cr.i_crewrotationid = t_insurance.i_crewrotationid) ");
        sb.append("LEFT JOIN t_candidate AS c ON (c.i_candidateid = cr.i_candidateid) ");
        sb.append("LEFT JOIN t_client ON (t_client.i_clientid = c.i_clientid) ");
        sb.append("WHERE t_insurance.i_insuranceid = ? ");
        String query = sb.toString();
        sb.setLength(0);
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, insuranceId);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                String name = rs.getString(1) != null ? rs.getString(1) : "";
                String emailId = rs.getString(2) != null ? rs.getString(2) : "";
                String clientName = rs.getString(3) != null ? rs.getString(3) : "";
                String ocsuserids = rs.getString(4) != null ? rs.getString(4) : "";
                int candidateId = rs.getInt(5);
                String insu_file = rs.getString(6) != null ? rs.getString(6) : "";
                if (!emailId.equals("")) {
                    String message = "Hi,<br/>Your insurance has been updated by " + clientName + ".<br/>Please find updated insurance attached.";
                    String subject = "Crew Insurance";
                    String ccval = "";
                    if (!ocsuserids.equals("")) {
                        String query_cc = "SELECT GROUP_CONCAT(s_email) FROM t_userlogin WHERE i_userid IN (" + ocsuserids + ")";
                        PreparedStatement pstmt_cc = conn.prepareStatement(query_cc);
                        ResultSet rs_cc = pstmt_cc.executeQuery();
                        while (rs_cc.next()) {
                            ccval = rs_cc.getString(1) != null ? rs_cc.getString(1) : "";
                        }
                    }
                    String messageBody = getInsuranceMessage(name, message, "insurance.html");
                    String file_maillog = getMainPath("file_maillog");
                    java.util.Date nowmail = new java.util.Date();
                    String fn_mail = "insurance-" + String.valueOf(nowmail.getTime()) + ".html";
                    String filePath = createFolder(file_maillog);
                    String fname = writeHTMLFile(messageBody, file_maillog + "/" + filePath, fn_mail);
                    String attachfile = getMainPath("add_candidate_file");
                    String attachfile_fn = "", attachfile_ext = "";
                    if (!insu_file.equals("")) {
                        attachfile_fn = attachfile + insu_file;
                        if (insu_file.indexOf(".") != -1) {
                            attachfile_ext = "Insurance" + insu_file.substring(insu_file.lastIndexOf("."));
                        }
                    }
                    String receipent[] = new String[1];
                    receipent[0] = emailId;
                    String from = "";

                    String cc[] = parseCommaDelimString(ccval);
                    String bcc[] = new String[0];
                    try {
                        StatsInfo sinfo = postMailAttach(receipent, cc, bcc, messageBody, subject, attachfile_fn, attachfile_ext, -1);
                        int flag = 0;
                        if (sinfo != null) {
                            flag = sinfo.getDdlValue();
                            from = sinfo.getDdlLabel();
                        }
                        Documentexpiry documentexpiry = new Documentexpiry();
                        documentexpiry.createMailLog(conn, 11, name, emailId, ccval, "", from, subject, candidateId, username, "", "/" + filePath + "/" + fname);
                        if (flag > 0) {
                            sent = 1;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return sent;
    }

    public String getInsuranceMessage(String name, String message, String htmlFile) {
        String adminpage = getMainPath("template_path") + htmlFile;
        HashMap hashmap = new HashMap();
        Template template = new Template(adminpage);
        hashmap.put("NAME", name);
        hashmap.put("MESSAGE", message);
        return template.patch(hashmap);
    }

    public ArrayList getNomineeList(int candidateId) {
        ArrayList list = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT t_nominee.i_nomineeid, t_nominee.s_nomineename, t_nominee.s_nomineecontactno, ");
        sb.append("t_relation.s_name, t_nominee.i_status, t_nominee.s_code ");
        sb.append("FROM t_nominee ");
        sb.append("LEFT JOIN t_relation ON (t_relation.i_relationid = t_nominee.i_relationid) ");
        sb.append("WHERE t_nominee.i_candidateid = ? ORDER BY t_nominee.i_status ");
        String query = (sb.toString()).intern();
        sb.setLength(0);
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, candidateId);
            logger.info("getNomineeList :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String nomineename, nomineecontactno, nomineerelation, code;
            int nomineedetailId, status;
            while (rs.next())
            {
                nomineedetailId = rs.getInt(1);
                nomineename = rs.getString(2) != null ? rs.getString(2) : "";
                nomineecontactno = rs.getString(3) != null ? rs.getString(3) : "";
                nomineerelation = rs.getString(4) != null ? rs.getString(4) : "";
                status = rs.getInt(5);
                code = rs.getString(6) != null ? rs.getString(6) : "";
                if (code != null && !code.equals("")) {
                    if (nomineecontactno != null && !nomineecontactno.equals("")) {
                        nomineecontactno = "+" + code + " " + nomineecontactno;
                    }
                }
                list.add(new CrewinsuranceInfo(nomineedetailId, nomineename, nomineecontactno, nomineerelation, status, code));
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
