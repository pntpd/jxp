package com.web.jxp.clientselection;

import com.mysql.jdbc.Statement;
import com.web.jxp.base.Base;
import static com.web.jxp.base.Base.dateFolder;
import static com.web.jxp.base.Base.decipher;
import static com.web.jxp.base.Base.parseCommaDelimString;
import com.web.jxp.base.StatsInfo;
import com.web.jxp.base.Template;
import com.web.jxp.common.Common;
import static com.web.jxp.common.Common.*;
import com.web.jxp.documentexpiry.Documentexpiry;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.TimeZone;
import java.util.logging.Logger;
import javax.mail.MessagingException;
import org.json.JSONArray;
import org.json.JSONObject;
import org.zefer.pd4ml.PD4Constants;

public class Clientselection extends Base {

    private static final Logger logger = Logger.getLogger(Class.class.getName());
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    String view_path = getMainPath("view_candidate_file");
    String resumepdffile_path = getMainPath("add_resumetemplate_pdf");

    public ArrayList getClientSelectByName(String search, int statusIndex, int next, int count, int clientIdIndex, int assetIdIndex, String pgvalue,
            int allclient, String permission, String cids, String assetids) {

        String positionValue = "", gradeIds = "";
        int gradeId = 0;
        if (pgvalue != null && !pgvalue.equals("")) {
            String pg[] = pgvalue.split(" # ");
            positionValue = pg[0] != null ? pg[0] : "";
            gradeIds = pg[1];
            gradeId = Integer.parseInt(gradeIds);
        }

        ArrayList list = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT jp.i_jobpostid, DATE_FORMAT(jp.ts_regdate,'%d-%b-%Y'), t_client.s_name, t_clientasset.s_name, t_position.s_name, t_grade.s_name, ");
        sb.append("DATE_FORMAT(jp.ts_targetmobdate, '%d-%b-%Y'), jp.i_noofopening, t1.ct, jp.i_status, jp.i_clientid FROM t_jobpost AS jp ");
        sb.append("LEFT JOIN t_client ON ( jp.i_clientid = t_client.i_clientid ) ");
        sb.append("LEFT JOIN t_clientasset ON ( jp.i_clientassetid = t_clientasset.i_clientassetid ) ");
        sb.append("LEFT JOIN t_position ON ( jp.i_positionid = t_position.i_positionid ) ");
        sb.append("LEFT JOIN t_grade on (jp.i_gradeid = t_grade.i_gradeid) ");
        sb.append("LEFT JOIN (SELECT i_jobpostid, count(1) AS ct FROM t_shortlist WHERE i_sflag= 4 AND i_oflag =4 GROUP BY i_jobpostid) AS t1 ON (t1.i_jobpostid = jp.i_jobpostid) ");
        sb.append("WHERE jp.i_status =1 ");
        if (allclient == 1) {
            sb.append("AND jp.i_clientid > 0 ");
        } else {
            if (!cids.equals("")) {
                sb.append("AND jp.i_clientid IN (" + cids + ") ");
            } else {
                sb.append("AND jp.i_clientid < 0 ");
            }
            if (!assetids.equals("")) {
                sb.append("AND jp.i_clientassetid IN (" + assetids + ") ");
            }
        }
        if (statusIndex > 0) {
            sb.append("AND jp.i_status = ? ");
        }
        if (clientIdIndex > 0) {
            sb.append("AND jp.i_clientid = ? ");
        }
        if (assetIdIndex > 0) {
            sb.append("AND jp.i_clientassetid = ? ");
        }
        if (!positionValue.equals("") && gradeId > 0) {
            sb.append("AND t_position.s_name = ? ");
            sb.append("AND t_position.i_gradeid = ? ");
        }

        if (search != null && !search.equals("")) {
            sb.append("AND (jp.i_jobpostid LIKE ? OR DATE_FORMAT(jp.ts_regdate, '%d-%b-%Y') LIKE ? OR t_clientasset.s_name LIKE ? OR t_grade.s_name LIKE ? ");
            sb.append("OR t_position.s_name LIKE ? OR DATE_FORMAT(jp.ts_targetmobdate, '%d-%b-%Y') LIKE ?)  ");
        }
        sb.append("ORDER BY jp.i_status, jp.ts_regdate ");
        if (count > 0) {
            sb.append(" LIMIT ").append(next * count).append(", ").append(count);
        }
        String query = (sb.toString()).intern();
        sb.setLength(0);

        sb.append("SELECT count(1) FROM t_jobpost AS jp ");
        sb.append("LEFT JOIN t_client ON ( jp.i_clientid = t_client.i_clientid ) ");
        sb.append("LEFT JOIN t_clientasset ON ( jp.i_clientassetid = t_clientasset.i_clientassetid ) ");
        sb.append("LEFT JOIN t_position ON ( jp.i_positionid = t_position.i_positionid ) ");
        sb.append("LEFT JOIN t_grade on (jp.i_gradeid = t_grade.i_gradeid) ");
        sb.append("LEFT JOIN (SELECT i_jobpostid, count(1) AS ct FROM t_shortlist WHERE i_sflag= 4 AND i_oflag =4 GROUP BY i_jobpostid) AS t1 ON (t1.i_jobpostid = jp.i_jobpostid) ");
        sb.append("WHERE jp.i_status =1 ");
        if (allclient == 1) {
            sb.append("AND jp.i_clientid > 0 ");
        } else {
            if (!cids.equals("")) {
                sb.append("AND jp.i_clientid IN (" + cids + ") ");
            } else {
                sb.append("AND jp.i_clientid < 0 ");
            }
            if (!assetids.equals("")) {
                sb.append("AND jp.i_clientassetid IN (" + assetids + ") ");
            }
        }
        if (statusIndex > 0) {
            sb.append("AND jp.i_status = ? ");
        }
        if (clientIdIndex > 0) {
            sb.append("AND jp.i_clientid = ? ");
        }
        if (assetIdIndex > 0) {
            sb.append("AND jp.i_clientassetid = ? ");
        }
        if (!positionValue.equals("") && gradeId > 0) {
            sb.append("AND t_position.s_name = ? ");
            sb.append("AND t_position.i_gradeid = ? ");
        }

        if (search != null && !search.equals("")) {
            sb.append("AND (jp.i_jobpostid LIKE ? OR DATE_FORMAT(jp.ts_regdate, '%d-%b-%Y') LIKE ? OR t_clientasset.s_name LIKE ? OR t_grade.s_name LIKE ? ");
            sb.append("OR t_position.s_name LIKE ? OR DATE_FORMAT(jp.ts_targetmobdate, '%d-%b-%Y') LIKE ?) ");
        }

        String countquery = (sb.toString()).intern();
        sb.setLength(0);
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            int scc = 0;
            if (statusIndex > 0) {
                pstmt.setInt(++scc, statusIndex);
            }
            if (clientIdIndex > 0) {
                pstmt.setInt(++scc, clientIdIndex);
            }
            if (assetIdIndex > 0) {
                pstmt.setInt(++scc, assetIdIndex);
            }
            if (!positionValue.equals("") && gradeId > 0) {
                pstmt.setString(++scc, positionValue);
                pstmt.setInt(++scc, gradeId);
            }

            if (search != null && !search.equals("")) {
                pstmt.setString(++scc, search.replaceFirst("^0+(?!$)", ""));
                pstmt.setString(++scc, "%" + (search) + "%");
                pstmt.setString(++scc, "%" + (search) + "%");
                pstmt.setString(++scc, "%" + (search) + "%");
                pstmt.setString(++scc, "%" + (search) + "%");
                pstmt.setString(++scc, "%" + (search) + "%");
            }
            logger.info("getClientSelectByName :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String date, clientName, positionName, grade, clientAsset, ccStatus = "", mobdate = "";
            int jobpostId, opening, selcount, status, clientId;
            while (rs.next()) {
                jobpostId = rs.getInt(1);
                date = rs.getString(2) != null ? rs.getString(2) : "";
                clientName = rs.getString(3) != null ? rs.getString(3) : "";
                clientAsset = rs.getString(4) != null ? rs.getString(4) : "";
                positionName = rs.getString(5) != null ? rs.getString(5) : "";
                grade = rs.getString(6) != null ? rs.getString(6) : "";
                mobdate = rs.getString(7) != null ? rs.getString(7) : "";
                opening = rs.getInt(8);
                selcount = rs.getInt(9);
                status = rs.getInt(10);
                clientId = rs.getInt(11);
                ccStatus = getccStatusbyId(selcount, opening);
                if (!grade.equals("")) {
                    positionName += " - " + grade;
                }
                if (!clientAsset.equals("")) {
                    clientName += " - " + clientAsset;
                }
                list.add(new ClientselectionInfo(jobpostId, date, clientName, clientAsset, positionName, grade, mobdate, opening, selcount, ccStatus, status,
                        clientId));
            }
            rs.close();

            pstmt = conn.prepareStatement(countquery);
            scc = 0;
            if (statusIndex > 0) {
                pstmt.setInt(++scc, statusIndex);
            }

            if (clientIdIndex > 0) {
                pstmt.setInt(++scc, clientIdIndex);
            }
            if (assetIdIndex > 0) {
                pstmt.setInt(++scc, assetIdIndex);
            }
            if (!positionValue.equals("") && gradeId > 0) {
                pstmt.setString(++scc, positionValue);
                pstmt.setInt(++scc, gradeId);
            }

            if (search != null && !search.equals("")) {
                pstmt.setString(++scc, search.replaceFirst("^0+(?!$)", ""));
                pstmt.setString(++scc, "%" + (search) + "%");
                pstmt.setString(++scc, "%" + (search) + "%");
                pstmt.setString(++scc, "%" + (search) + "%");
                pstmt.setString(++scc, "%" + (search) + "%");
                pstmt.setString(++scc, "%" + (search) + "%");
            }
            print(this, "getClientSelectByName :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                jobpostId = rs.getInt(1);
                list.add(new ClientselectionInfo(jobpostId, "", "", "", "", "", "", 0, 0, "", 0, 0));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return list;
    }

    public String getccStatusbyId(int scount, int opening) {
        String stval = "";

        if (scount <= 0) {
            stval = "Pending";
        } else if (scount > 0 && scount < opening) {
            stval = "In-progress";
        } else if (scount == opening) {
            stval = "Completed";
        }
        return stval;
    }

    public ArrayList getFinalRecord(ArrayList l, String colId, int tp) {
        ArrayList list = null;
        HashMap record = null;
        ClientselectionInfo info;
        int total = 0;
        if (l != null) {
            total = l.size();
        }

        try {
            if (total > 0) {
                record = new HashMap();
                int i;
                for (i = 0; i < total; i++) {
                    info = (ClientselectionInfo) l.get(i);
                    record.put(getInfoValue(info, colId), info);
                }
            }

            Map map;
            if (colId.equals("1")) {
                map = sortById(record, tp);
            } else if (colId.equals("2") || colId.equals("5")) {
                map = sortByDate(record, tp, "dd-MMM-yyyy");
            } else {
                map = sortByName(record, tp);
            }

            Iterator it = map.keySet().iterator();
            list = new ArrayList();
            ClientselectionInfo rInfo;
            while (it.hasNext()) {
                String key = (String) it.next();
                int i;
                for (i = 0; i < total; i++) {
                    rInfo = (ClientselectionInfo) l.get(i);
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

    public ArrayList getClientFinalRecord(ArrayList l, String colId, int tp) {
        ArrayList list = null;
        HashMap record = null;
        ClientselectionInfo info;
        int total = 0;
        if (l != null) {
            total = l.size();
        }
        try {
            if (total > 0) {
                record = new HashMap();
                int i;
                for (i = 0; i < total; i++) {
                    info = (ClientselectionInfo) l.get(i);
                    record.put(getClientInfoValue(info, colId), info);
                }
            }
            Map map;
            if (colId.equals("1")) {
                map = sortById(record, tp);
            } else if (colId.equals("2") || colId.equals("5")) {
                map = sortByDate(record, tp, "dd-MMM-yyyy");
            } else {
                map = sortByName(record, tp);
            }
            Iterator it = map.keySet().iterator();
            list = new ArrayList();
            ClientselectionInfo rInfo;
            while (it.hasNext()) {
                String key = (String) it.next();
                int i;
                for (i = 0; i < total; i++) {
                    rInfo = (ClientselectionInfo) l.get(i);
                    String str = getClientInfoValue(rInfo, colId);
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

    // to company sort search result
    public String getInfoValue(ClientselectionInfo info, String i) {
        String infoval = "";
        if (i != null && i.equals("1")) {
            infoval = info.getJobpostId() + "";
        } else if (i != null && i.equals("2")) {
            infoval = info.getDate() != null ? info.getDate() : "";
        } else if (i != null && i.equals("3")) {
            infoval = info.getClientName() != null ? info.getClientName() : "";
        } else if (i != null && i.equals("4")) {
            infoval = info.getPosition() != null ? info.getPosition() : "";
        } else if (i != null && i.equals("5")) {
            infoval = info.getMobdate() != null ? info.getMobdate() : "";
        } else if (i != null && i.equals("6")) {
            infoval = info.getCcStatus() != null ? info.getCcStatus() : "";
        }
        return infoval;
    }

    // to client sort search result
    public String getClientInfoValue(ClientselectionInfo info, String i) {
        String infoval = "";
        if (i != null && i.equals("1")) {
            infoval = info.getJobpostId() + "";
        } else if (i != null && i.equals("2")) {
            infoval = info.getDate() != null ? info.getDate() : "";
        } else if (i != null && i.equals("3")) {
            infoval = info.getClientAsset() != null ? info.getClientAsset() : "";
        } else if (i != null && i.equals("4")) {
            infoval = info.getPosition() != null ? info.getPosition() : "";
        } else if (i != null && i.equals("5")) {
            infoval = info.getMobdate() != null ? info.getMobdate() : "";
        } else if (i != null && i.equals("6")) {
            infoval = info.getCcStatus() != null ? info.getCcStatus() : "";
        }
        return infoval;
    }

    // For Index Excel report
    public ArrayList getClientSelectListForExcel(String search, int statusIndex, int clientIdIndex, int assetIdIndex, String pgvalue, int allclient,
            String permission, String cids, String assetids) {
        String positionValue = "", gradeIds = "";
        int gradeId = 0;
        if (pgvalue != null && !pgvalue.equals("")) {
            String pg[] = pgvalue.split(" # ");
            positionValue = pg[0] != null ? pg[0] : "";
            gradeIds = pg[1];
            gradeId = Integer.parseInt(gradeIds);
        }

        ArrayList list = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT jp.i_jobpostid, DATE_FORMAT(jp.ts_regdate,'%d-%b-%Y'), t_client.s_name, t_clientasset.s_name, t_position.s_name, t_grade.s_name, ");
        sb.append("DATE_FORMAT(jp.ts_targetmobdate, '%d-%b-%Y'), jp.i_noofopening, t1.ct, jp.i_status, jp.i_clientid FROM t_jobpost AS jp ");
        sb.append("LEFT JOIN t_client ON ( jp.i_clientid = t_client.i_clientid ) ");
        sb.append("LEFT JOIN t_clientasset ON ( jp.i_clientassetid = t_clientasset.i_clientassetid ) ");
        sb.append("LEFT JOIN t_position ON ( jp.i_positionid = t_position.i_positionid ) ");
        sb.append("LEFT JOIN t_grade on (jp.i_gradeid = t_grade.i_gradeid) ");
        sb.append("LEFT JOIN (SELECT i_jobpostid, count(1) AS ct FROM t_shortlist WHERE i_sflag= 4 AND i_oflag =4 GROUP BY i_jobpostid) AS t1 ON (t1.i_jobpostid = jp.i_jobpostid) ");
        sb.append("WHERE jp.i_status =1 ");
        if (allclient == 1) {
            sb.append("AND jp.i_clientid > 0 ");
        } else {
            if (!cids.equals("")) {
                sb.append("AND jp.i_clientid IN (" + cids + ") ");
            } else {
                sb.append("AND jp.i_clientid < 0 ");
            }
            if (!assetids.equals("")) {
                sb.append("AND jp.i_clientassetid IN (" + assetids + ") ");
            }
        }
        if (statusIndex > 0) {
            sb.append("AND jp.i_status = ? ");
        }
        if (clientIdIndex > 0) {
            sb.append("AND jp.i_clientid = ? ");
        }
        if (assetIdIndex > 0) {
            sb.append("AND jp.i_clientassetid = ? ");
        }
        if (!positionValue.equals("") && gradeId > 0) {
            sb.append("AND t_position.s_name = ? ");
            sb.append("AND t_position.i_gradeid = ? ");
        }

        if (search != null && !search.equals("")) {
            sb.append("AND (jp.i_jobpostid LIKE ? OR DATE_FORMAT(jp.ts_regdate, '%d-%b-%Y') LIKE ? OR t_clientasset.s_name LIKE ? OR t_grade.s_name LIKE ? ");
            sb.append("OR t_position.s_name LIKE ? OR DATE_FORMAT(jp.ts_targetmobdate, '%d-%b-%Y') LIKE ?)  ");
        }
        sb.append("ORDER BY jp.i_status, jp.ts_regdate ");

        String query = (sb.toString()).intern();
        sb.setLength(0);
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            int scc = 0;
            if (statusIndex > 0) {
                pstmt.setInt(++scc, statusIndex);
            }
            if (clientIdIndex > 0) {
                pstmt.setInt(++scc, clientIdIndex);
            }
            if (assetIdIndex > 0) {
                pstmt.setInt(++scc, assetIdIndex);
            }
            if (!positionValue.equals("") && gradeId > 0) {
                pstmt.setString(++scc, positionValue);
                pstmt.setInt(++scc, gradeId);
            }

            if (search != null && !search.equals("")) {
                pstmt.setString(++scc, search.replaceFirst("^0+(?!$)", ""));
                pstmt.setString(++scc, "%" + (search) + "%");
                pstmt.setString(++scc, "%" + (search) + "%");
                pstmt.setString(++scc, "%" + (search) + "%");
                pstmt.setString(++scc, "%" + (search) + "%");
                pstmt.setString(++scc, "%" + (search) + "%");
            }
            logger.info("getClientSelectListForExcel :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String date, clientName, positionName, grade, clientAsset, ccStatus = "", mobdate = "";
            int jobpostId, opening, selcount, status, clientId;
            while (rs.next()) {
                jobpostId = rs.getInt(1);
                date = rs.getString(2) != null ? rs.getString(2) : "";
                clientName = rs.getString(3) != null ? rs.getString(3) : "";
                clientAsset = rs.getString(4) != null ? rs.getString(4) : "";
                positionName = rs.getString(5) != null ? rs.getString(5) : "";
                grade = rs.getString(6) != null ? rs.getString(6) : "";
                mobdate = rs.getString(7) != null ? rs.getString(7) : "";
                opening = rs.getInt(8);
                selcount = rs.getInt(9);
                status = rs.getInt(10);
                clientId = rs.getInt(11);
                ccStatus = getccStatusbyId(selcount, opening);
                if (!grade.equals("")) {
                    positionName += " - " + grade;
                }
                if (!clientAsset.equals("")) {
                    clientName += " - " + clientAsset;
                }
                list.add(new ClientselectionInfo(jobpostId, date, clientName, clientAsset, positionName, grade, mobdate, opening, selcount, ccStatus, status,
                        clientId));
            }
            rs.close();

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

        sb.append("order by s_name");
        String query = (sb.toString()).intern();
        sb.setLength(0);
        coll.add(new ClientselectionInfo(-1, " Select Client"));
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            rs = pstmt.executeQuery();
            int refId;
            String refName;
            while (rs.next()) {
                refId = rs.getInt(1);
                refName = rs.getString(2) != null ? rs.getString(2) : "";
                coll.add(new ClientselectionInfo(refId, refName));
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
            sb.append("SELECT i_clientassetid, s_name FROM t_clientasset WHERE i_clientid =? AND i_status =1 ");
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
            coll.add(new ClientselectionInfo(-1, " Select Client Asset "));
            try {
                conn = getConnection();
                pstmt = conn.prepareStatement(query);
                pstmt.setInt(1, clientId);
                rs = pstmt.executeQuery();
                int ddlValue;
                String ddlLabel;
                while (rs.next()) {
                    ddlValue = rs.getInt(1);
                    ddlLabel = rs.getString(2) != null ? rs.getString(2) : "";
                    coll.add(new ClientselectionInfo(ddlValue, ddlLabel));
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                close(conn, pstmt, rs);
            }
        } else {
            coll.add(new ClientselectionInfo(-1, " Select Client Asset "));
        }
        return coll;
    }

    public Collection getAsset(String assetids) {
        Collection coll = new LinkedList();
        if (!assetids.equals("")) {
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT i_clientassetid, s_name FROM t_clientasset WHERE i_status = 1 ");
            if (!assetids.equals("")) {
                sb.append("AND i_clientassetid IN (" + assetids + ") ");
            }
            sb.append("ORDER BY s_name ");
            String query = sb.toString();
            sb.setLength(0);
            coll.add(new ClientselectionInfo(-1, " Select Asset "));
            try {
                conn = getConnection();
                pstmt = conn.prepareStatement(query);
                rs = pstmt.executeQuery();
                int ddlValue;
                String ddlLabel;
                while (rs.next()) {
                    ddlValue = rs.getInt(1);
                    ddlLabel = rs.getString(2) != null ? rs.getString(2) : "";
                    coll.add(new ClientselectionInfo(ddlValue, ddlLabel));
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                close(conn, pstmt, rs);
            }
        } else {
            coll.add(new ClientselectionInfo(-1, " Select Asset "));
        }
        return coll;
    }

    public Collection getPostions(int clientassetId) {
        Collection coll = new LinkedList();
        if (clientassetId > 0) {
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT t_clientassetposition.i_positionid, t_position.s_name, t_grade.s_name, t_position.i_gradeid FROM t_clientassetposition ");
            sb.append("LEFT JOIN t_position ON (t_position.i_positionid = t_clientassetposition.i_positionid) ");
            sb.append("LEFT JOIN t_grade ON (t_grade.i_gradeid = t_position.i_gradeid) ");
            sb.append("WHERE t_clientassetposition.i_clientassetid = ? ORDER BY t_position.s_name ");
            String query = sb.toString();
            sb.setLength(0);
            coll.add(new ClientselectionInfo("", " Select Position - Rank "));
            try {
                conn = getConnection();
                pstmt = conn.prepareStatement(query);
                pstmt.setInt(1, clientassetId);
                logger.info("getPostions :: " + pstmt.toString());
                Common.print(this, "getPostions :: " + pstmt.toString());
                rs = pstmt.executeQuery();
                int ddlValue, gradeId;
                String ddlLabel = "", grade, position, positionValue = "";
                while (rs.next()) {
                    ddlValue = rs.getInt(1);
                    position = rs.getString(2) != null ? rs.getString(2) : "";
                    grade = rs.getString(3) != null ? rs.getString(3) : "";
                    gradeId = rs.getInt(4);
                    ddlLabel = position;
                    if (!grade.equals("")) {
                        ddlLabel += " - " + grade;
                    }
                    positionValue = position;
                    if (!position.equals("")) {
                        positionValue += " # " + gradeId;
                    }
                    coll.add(new ClientselectionInfo(positionValue, ddlLabel));
                    positionValue = "";
                    ddlLabel = "";
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                close(conn, pstmt, rs);
            }
        } else {
            coll.add(new ClientselectionInfo("", " Select Position - Rank "));
        }
        return coll;
    }

    public ClientselectionInfo getClientSelectionByIdforDetail(int jobpostId) {
        ClientselectionInfo info = null;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT t_client.s_name, t_clientasset.s_name, t_position.s_name, t_grade.s_name, t_qualificationtype.s_name, ");
        sb.append("DATE_FORMAT(jp.ts_regdate,'%d-%b-%Y'), jp.d_experiencemin, jp.d_experiencemax, jp.i_noofopening, jp.i_status, t2.ct2, ");
        sb.append("jp.i_clientid, t1.ct FROM t_jobpost AS jp ");
        sb.append("LEFT JOIN t_client ON jp.i_clientid = t_client.i_clientid ");
        sb.append("LEFT JOIN t_clientasset ON jp.i_clientassetid = t_clientasset.i_clientassetid ");
        sb.append("LEFT JOIN t_position ON jp.i_positionid = t_position.i_positionid ");
        sb.append("LEFT JOIN t_grade ON jp.i_gradeid = t_grade.i_gradeid ");
        sb.append("LEFT JOIN t_qualificationtype ON jp.i_qualificationtypeid = t_qualificationtype.i_qualificationtypeid ");
        sb.append("LEFT JOIN (SELECT i_jobpostid, count(1) AS ct2 FROM t_shortlist WHERE i_status IN (2,3) AND i_jobpostid =? GROUP BY i_jobpostid) AS t2 ON (t2.i_jobpostid = jp.i_jobpostid) ");
        sb.append("LEFT JOIN (SELECT i_jobpostid, count(1) AS ct FROM t_shortlist WHERE i_sflag= 4 AND i_oflag =4 GROUP BY i_jobpostid) AS t1 ON (t1.i_jobpostid = jp.i_jobpostid) ");
        sb.append("WHERE jp.i_jobpostid = ? ");
        String query = sb.toString().intern();
        sb.setLength(0);
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, jobpostId);
            pstmt.setInt(2, jobpostId);
            print(this, "getClientSelectionByIdforDetail :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String clientname, assetname, positionname, grade, education, poston;
            double experiencemin, experiencemax;
            int noofopening, shortlistcount, status, clientId, selcount;
            while (rs.next()) {
                clientname = rs.getString(1) != null ? rs.getString(1) : "";
                assetname = rs.getString(2) != null ? rs.getString(2) : "";
                positionname = rs.getString(3) != null ? rs.getString(3) : "";
                grade = rs.getString(4) != null ? rs.getString(4) : "";
                education = rs.getString(5) != null ? rs.getString(5) : "";
                poston = rs.getString(6) != null ? rs.getString(6) : "";
                experiencemin = rs.getDouble(7);
                experiencemax = rs.getDouble(8);
                noofopening = rs.getInt(9);
                status = rs.getInt(10);
                shortlistcount = rs.getInt(11);
                clientId = rs.getInt(12);
                selcount = rs.getInt(13);
                if (!grade.equals("")) {
                    positionname += " - " + grade;
                }
                if (!assetname.equals("")) {
                    clientname += " - " + assetname;
                }
                info = new ClientselectionInfo(jobpostId, clientname, assetname, positionname, grade, education, poston, experiencemin, experiencemax,
                        noofopening, status, selcount, shortlistcount, clientId);
            }
            rs.close();
        } catch (Exception exception) {
            print(this, "getClientSelectionByIdforDetail :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return info;
    }

    public ArrayList getShortlistedCandidateListByIDs(int jobpostId, int companytype, String search, int selstatus, int selsubstatus) {
        ArrayList list = new ArrayList();
        if (jobpostId > 0) 
        {
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT c.i_candidateid, CONCAT_WS(' ', c.s_firstname, c.s_middlename, c.s_lastname), t_position.s_name, t_grade.s_name, t_degree.s_name, ");
            sb.append("t_qualificationtype.s_name, tw1.s_companyname, w.exp, sl.i_shortlistid, sl.i_status, sl.i_sflag, c.s_photofilename, sl.s_mailby, ");
            sb.append("DATE_FORMAT(sl.ts_maildate, '%d-%b-%Y'), sl.s_srby, DATE_FORMAT(sl.ts_srdate, '%d-%b-%Y'), t_country.s_name, sl.s_pdffilename, sl.i_oflag, ");
            sb.append("sl.s_offerpdffilename, c.i_progressid FROM t_shortlist sl ");
            sb.append("LEFT JOIN t_candidate AS c ON (sl.i_candidateid = c.i_candidateid) ");
            sb.append("LEFT JOIN t_position ON (t_position.i_positionid = c.i_positionid) ");
            sb.append("LEFT JOIN t_grade ON (t_grade.i_gradeid = t_position.i_gradeid) ");
            sb.append("LEFT JOIN t_eduqual ON (t_eduqual.i_candidateid = c.i_candidateid AND t_eduqual.i_highestqualification = 1) ");
            sb.append("LEFT JOIN (SELECT tw.i_candidateid, tw.s_companyname FROM (SELECT i_candidateid, s_companyname FROM t_workexperience WHERE i_status = 1 ORDER BY d_workstartdate DESC) AS tw GROUP BY i_candidateid) AS tw1 ON (tw1.i_candidateid = c.i_candidateid) ");
            sb.append("LEFT JOIN t_degree ON (t_degree.i_degreeid = t_eduqual.i_degreeid) ");
            sb.append("LEFT JOIN t_country ON (c.i_countryid = t_country.i_countryid) ");
            sb.append("LEFT JOIN t_qualificationtype ON ( t_qualificationtype.i_qualificationtypeid = t_eduqual.i_qualificationtypeid ) ");
            sb.append("LEFT JOIN (SELECT i_candidateid, ROUND(SUM(DATEDIFF( IF(d_workenddate IS NULL OR d_workenddate = '0000-00-00', current_date(), d_workenddate), ");
            sb.append("d_workstartdate)) / 365, 1) AS exp FROM t_workexperience WHERE i_status = 1 GROUP BY i_candidateid ) AS w ON (c.i_candidateid = w.i_candidateid) ");
            sb.append("WHERE sl.i_jobpostid =? AND sl.i_status IN (2,3) AND c.i_progressid > 0 AND sl.i_active = 1 ");
            if (companytype == 1) {
                if (selstatus > 0) {
                    if (selstatus == 1) {
                        sb.append("AND sl.i_sflag IN (1,2,3) ");
                    } else if (selstatus == 2) {
                        sb.append("AND sl.i_sflag =4 ");
                        if (selsubstatus > 0) {
                            sb.append("AND sl.i_oflag =? ");
                        }
                    } else if (selstatus == 3) {
                        sb.append("AND sl.i_sflag =5 ");
                    }
                }
            } else if (selstatus > 0) {
                if (selstatus == 1) {
                    sb.append("AND sl.i_sflag =3 ");
                } else if (selstatus == 2) {
                    sb.append("AND sl.i_sflag =4 ");
                    if (selsubstatus > 0) {
                        sb.append("AND sl.i_oflag =? ");
                    }
                } else if (selstatus == 3) {
                    sb.append("AND sl.i_sflag =5 ");
                }
            } else {
                sb.append("AND sl.i_sflag IN (3,4,5) ");
            }
            if (!search.equals("")) {
                sb.append("AND CONCAT_WS(' ', c.s_firstname, c.s_middlename, c.s_lastname) LIKE ? ");
            }
            sb.append("GROUP BY c.i_candidateid ORDER BY sl.i_sflag, sl.i_oflag, c.s_firstname ");
            String query = (sb.toString()).intern();
            sb.setLength(0);
            try {
                conn = getConnection();
                pstmt = conn.prepareStatement(query);
                int scc = 0;
                pstmt.setInt(++scc, jobpostId);
                if (selstatus == 2) {
                    if (selsubstatus > 0) {
                        pstmt.setInt(++scc, selsubstatus);
                    }
                }
                if (!search.equals("")) {
                    pstmt.setString(++scc, "%" + search + "%");
                }
                logger.info("getShortlistedCandidateListByIDs Client Selection:: " + pstmt.toString());
                rs = pstmt.executeQuery();
                String name, position, grade, degree, qualification, company, photo, mailby, mailon, srby, sron, country, pdffilename, offerpdffile;
                int candidateId, shortlistId, status, substatus, sflag, progressId;
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
                    shortlistId = rs.getInt(9);
                    status = rs.getInt(10);
                    sflag = rs.getInt(11);
                    photo = rs.getString(12) != null ? rs.getString(12) : "";
                    mailby = rs.getString(13) != null ? rs.getString(13) : "";
                    mailon = rs.getString(14) != null ? rs.getString(14) : "";
                    srby = rs.getString(15) != null ? rs.getString(15) : "";
                    sron = rs.getString(16) != null ? rs.getString(16) : "";
                    country = rs.getString(17) != null ? rs.getString(17) : "";
                    pdffilename = rs.getString(18) != null ? rs.getString(18) : "";
                    substatus = rs.getInt(19);
                    offerpdffile = rs.getString(20) != null ? rs.getString(20) : "";
                    progressId = rs.getInt(21);
                    if (!grade.equals("")) {
                        position += " - " + grade;
                    }
                    if (!degree.equals("")) {
                        qualification += " - " + degree;
                    }
                    list.add(new ClientselectionInfo(candidateId, name, position, grade, degree, qualification, company, exp, shortlistId, status, sflag,
                            photo, mailby, mailon, srby, sron, country, pdffilename, jobpostId, substatus, offerpdffile, progressId));
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

    public ArrayList getRejectReasonList() {
        ArrayList list = new ArrayList();
        try {
            String xml_path = getMainPath("json_path");
            String str = readHTMLFile("rejectionreason.json", xml_path);
            JSONArray arr = new JSONArray(str);
            if (arr != null) {
                int len = arr.length();
                for (int i = 0; i < len; i++) {
                    JSONObject jobj = arr.optJSONObject(i);
                    if (jobj != null) {
                        list.add(new ClientselectionInfo(jobj.optInt("id"), jobj.optString("name"), jobj.optInt("rejectiontype")));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public int getClientCandidateSelect(int shortlistId, String username, int companytype, int uId) {
        int cc = 0;
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("UPDATE t_shortlist SET ");
            sb.append("i_sflag = 4, ");
            sb.append("s_srby = ?, ");
            sb.append("ts_srdate = ? ");
            sb.append("WHERE i_shortlistid = ? ");
            String query = (sb.toString()).intern();
            sb.setLength(0);

            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            int scc = 0;
            pstmt.setString(++scc, username);
            pstmt.setString(++scc, currDate1());
            pstmt.setInt(++scc, shortlistId);
            print(this, "getClientCandidateSelect :: " + pstmt.toString());
            cc = pstmt.executeUpdate();

            insertclientselectionhistory(shortlistId, 4, companytype, 0, uId, 0);
        } catch (Exception exception) {
            print(this, "getClientCandidateSelect :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return cc;
    }

    public int getClientCandidateReject(int shortlistId, String username, String reason, String reasonRemark, int companytype, int uId, int candidateId) {
        int cc = 0;
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("UPDATE t_shortlist SET ");
            sb.append("i_sflag = 5, ");
            sb.append("s_srby = ?, ");
            sb.append("ts_srdate = ?, ");
            sb.append("s_reason = ?, ");
            sb.append("s_remarks = ? ");
            sb.append("WHERE i_shortlistid = ? ");
            String query = (sb.toString()).intern();
            sb.setLength(0);

            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            int scc = 0;
            pstmt.setString(++scc, username);
            pstmt.setString(++scc, currDate1());
            pstmt.setString(++scc, reason);
            pstmt.setString(++scc, reasonRemark);
            pstmt.setInt(++scc, shortlistId);
            print(this, "getClientCandidateReject :: " + pstmt.toString());
            cc = pstmt.executeUpdate();
            insertclientselectionhistory(shortlistId, 5, companytype, 0, uId, 0);
            //unlockCandidate(candidateId);

        } catch (Exception exception) {
            print(this, "getClientCandidateReject :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return cc;
    }

    public int getClientCandidateAccept(int shortlistId, String username, String reasonRemark, int companytype, int uId, int candidateId) {
        int cc = 0;
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("UPDATE t_shortlist SET ");
            sb.append("i_oflag = 4, ");
            sb.append("s_oadby = ?, ");
            sb.append("ts_oaddate = ?, ");
            sb.append("s_oadremarks = ? ");
            sb.append("WHERE i_shortlistid = ? ");
            String query = (sb.toString()).intern();
            sb.setLength(0);

            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            int scc = 0;
            pstmt.setString(++scc, username);
            pstmt.setString(++scc, currDate1());
            pstmt.setString(++scc, reasonRemark);
            pstmt.setInt(++scc, shortlistId);
            print(this, "getClientCandidateAccept :: " + pstmt.toString());
            cc = pstmt.executeUpdate();
            insertclientselectionhistory(shortlistId, 4, companytype, 0, uId, 4);

        } catch (Exception exception) {
            print(this, "getClientCandidateAccept :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return cc;
    }

    public int getClientCandidateDecline(int shortlistId, String username, String reason, String reasonRemark, int companytype, int uId, int candidateId) {
        int cc = 0;
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("UPDATE t_shortlist SET ");
            sb.append("i_oflag = 5, ");
            sb.append("s_oadby = ?, ");
            sb.append("ts_oaddate = ?, ");
            sb.append("s_oadreason = ?, ");
            sb.append("s_oadremarks = ? ");
            sb.append("WHERE i_shortlistid = ? ");
            String query = (sb.toString()).intern();
            sb.setLength(0);

            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            int scc = 0;
            pstmt.setString(++scc, username);
            pstmt.setString(++scc, currDate1());
            pstmt.setString(++scc, reason);
            pstmt.setString(++scc, reasonRemark);
            pstmt.setInt(++scc, shortlistId);
            print(this, "getClientCandidateDecline :: " + pstmt.toString());
            cc = pstmt.executeUpdate();
            insertclientselectionhistory(shortlistId, 4, companytype, 0, uId, 5);
//            unlockCandidate(candidateId);

        } catch (Exception exception) {
            print(this, "getClientCandidateDecline :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return cc;
    }

    public ArrayList getSelectionSummaryEmaillog(int shortlistId) {
        ArrayList list = new ArrayList();
        if (shortlistId > 0) {
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT i_maillogid, DATE_FORMAT(ts_regdate, '%d-%b-%Y %H:%i'), s_sendby, s_attachmentpath FROM t_maillog WHERE i_shortlistid =? AND i_oflag =2 ORDER BY ts_regdate DESC limit 0,2");
            String query = (sb.toString()).intern();
            sb.setLength(0);
            try {
                conn = getConnection();
                pstmt = conn.prepareStatement(query);
                int scc = 0;
                pstmt.setInt(++scc, shortlistId);
                logger.info("getSelectionSummaryEmaillog :: " + pstmt.toString());
                rs = pstmt.executeQuery();
                String mailby, mailon, pdffile;
                int maillogId;
                while (rs.next()) {
                    maillogId = rs.getInt(1);
                    mailon = rs.getString(2) != null ? rs.getString(2) : "";
                    mailby = rs.getString(3) != null ? rs.getString(3) : "";
                    pdffile = rs.getString(4) != null ? rs.getString(4) : "";
                    list.add(new ClientselectionInfo(maillogId, mailon, mailby, pdffile));
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

    public ArrayList getSelectionSummaryOfferlog(int shortlistId) {
        ArrayList list = new ArrayList();
        if (shortlistId > 0) {
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT i_maillogid, DATE_FORMAT(ts_regdate, '%d-%b-%Y %H:%i'), s_sendby, s_attachmentpath FROM t_maillog WHERE i_shortlistid =? AND i_oflag =2 ORDER BY ts_regdate DESC limit 0,2");
            String query = (sb.toString()).intern();
            sb.setLength(0);
            try {
                conn = getConnection();
                pstmt = conn.prepareStatement(query);
                int scc = 0;
                pstmt.setInt(++scc, shortlistId);
                logger.info("getSelectionSummaryOfferlog :: " + pstmt.toString());
                rs = pstmt.executeQuery();
                String mailby, mailon, pdffile;
                int maillogId;
                while (rs.next()) {
                    maillogId = rs.getInt(1);
                    mailon = rs.getString(2) != null ? rs.getString(2) : "";
                    mailby = rs.getString(3) != null ? rs.getString(3) : "";
                    pdffile = rs.getString(4) != null ? rs.getString(4) : "";
                    list.add(new ClientselectionInfo(maillogId, mailon, mailby, pdffile));
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

    public void unlockCandidate(int candidateId) {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("UPDATE t_candidate SET ");
            sb.append("i_progressid = 0 ");
            sb.append("WHERE i_candidateid = ? ");
            String candidatequery = (sb.toString()).intern();
            sb.setLength(0);
            conn = getConnection();
            pstmt = conn.prepareStatement(candidatequery);
            pstmt.setInt(1, candidateId);
            print(this, "unlockCandidate :: " +pstmt.toString());
            pstmt.executeUpdate();

        } catch (Exception exception) {
            print(this, "unlockCandidate :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
    }

    public ClientselectionInfo getInfoFromList(ArrayList list, int shortlistId) {
        print(this, "list :: " + list + " shortlistId : : " + shortlistId);
        ClientselectionInfo info = null;
        int size = list.size();
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                ClientselectionInfo info1 = (ClientselectionInfo) list.get(i);
                if (info1 != null && info1.getShortlistId() == shortlistId) {
                    info = info1;
                    break;
                }
            }
        }
        return info;
    }

    public ClientselectionInfo getRejectReasonDetails(int shortlistId) {
        ClientselectionInfo info = null;
        String strReasons = "", str = "";
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT s_srby, DATE_FORMAT(ts_srdate, '%d-%b-%Y'), s_reason, s_remarks FROM t_shortlist ");
        sb.append("WHERE i_shortlistid = ? ");
        String query = sb.toString().intern();
        sb.setLength(0);
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, shortlistId);
            print(this, "getRejectReasonDetails :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String srBy = "", srDate = "", reasonIds = "", remark = "";
            while (rs.next()) {
                srBy = rs.getString(1) != null ? rs.getString(1) : "";
                srDate = rs.getString(2) != null ? rs.getString(2) : "";
                reasonIds = rs.getString(3) != null ? rs.getString(3) : "";
                remark = rs.getString(4) != null ? rs.getString(4) : "";
            }
            rs.close();

            if (reasonIds != null && !reasonIds.equals("")) {
                String query1 = "SELECT s_name FROM t_rejectionreason WHERE i_rejectionreasonid IN (" + reasonIds + ")";
                pstmt = conn.prepareStatement(query1);
                print(this, "getRejectReasonDetails query1:: " + pstmt.toString());
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    str = rs.getString(1) != null ? rs.getString(1) : "";
                    strReasons += str + ",";
                }
                rs.close();
            }
            info = new ClientselectionInfo(srBy, srDate, strReasons, remark);

        } catch (Exception exception) {
            print(this, "getRejectReasonDetails :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return info;
    }

    public ClientselectionInfo getOfferReasonDetails(int shortlistId) {
        ClientselectionInfo info = null;
        String strReasons = "", str = "";
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT s_oadreason, s_oadremarks FROM t_shortlist ");
        sb.append("WHERE i_shortlistid = ? ");
        String query = sb.toString().intern();
        sb.setLength(0);
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, shortlistId);
            print(this, "getOfferReasonDetails :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String reasonIds = "", remark = "";
            while (rs.next()) {
                reasonIds = rs.getString(1) != null ? rs.getString(1) : "";
                remark = rs.getString(2) != null ? rs.getString(2) : "";
            }
            rs.close();

            if (reasonIds != null && !reasonIds.equals("")) {
                String query1 = "SELECT s_name FROM t_rejectionreason WHERE i_rejectiontype =2 AND i_rejectionreasonid IN (" + reasonIds + ")";
                pstmt = conn.prepareStatement(query1);
                print(this, "getOfferReasonDetails query1:: " + pstmt.toString());
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    str = rs.getString(1) != null ? rs.getString(1) : "";
                    strReasons += str + ",";
                }
                rs.close();
            }
            info = new ClientselectionInfo(strReasons, remark);

        } catch (Exception exception) {
            print(this, "getOfferReasonDetails catch :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return info;
    }

    public String getSeparatedString(String val) {
        String str = "";
        if (val != null && !val.equals("")) {
            String[] arr = val.split(",");
            for (int i = 1; i <= arr.length; i++) {
                str += "<span>" + i + "." + arr[i - 1] + "</span>";
            }
        }
        return str;
    }

    public Collection getResumetemplatesforclientindex(int clientId, int temptype) {
        Collection coll = new LinkedList();
        if (clientId > 0) {
            String query = ("SELECT i_resumetemplateid, s_name FROM t_resumetemplate WHERE i_clientid =? AND i_temptype =? AND i_status =1 ORDER BY s_name").intern();
            coll.add(new ClientselectionInfo(-1, "Select Template"));
            try {
                conn = getConnection();
                pstmt = conn.prepareStatement(query);
                pstmt.setInt(1, clientId);
                pstmt.setInt(2, temptype);
                logger.info("getResumetemplatesforclientindex :: " + pstmt.toString());
                rs = pstmt.executeQuery();
                int refId;
                String refName;
                while (rs.next()) {
                    refId = rs.getInt(1);
                    refName = rs.getString(2) != null && !rs.getString(2).equals("") ? rs.getString(2) : "";
                    coll.add(new ClientselectionInfo(refId, refName));
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                close(conn, pstmt, rs);
            }
        } else {
            coll.add(new ClientselectionInfo(-1, "Select Template"));
        }
        return coll;
    }

    public String createResumeTeamplateFile(int candidateId, int resumetemplateId, String cval1s, String cval2s, String cval3s,
            String cval4s, String cval5s, String cval6s, String cval7s, String cval8s, String cval9s, String cval10s,
            String validfrom, String validto, String contractor, String edate, String dmname, String designation, String comments,
            int clientId, int jobpostId, String jobasset, double variablepay) throws SQLException {
        String pdffilename = "", templatename = "", templatepath;
        int cc = 0;
        templatepath = getMainPath("add_resumetemplate_file");
        if (resumetemplateId == -2) {
            templatename = "client-selection.html";
            templatepath = getMainPath("template_path");
        }
        String img_path = getMainPath("img_path");
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT CONCAT_WS(' ', c.s_firstname, c.s_middlename, c.s_lastname), DATE_FORMAT(c.d_dob, '%d-%b-%Y'), ");
        sb.append("c.s_placeofbirth, c.s_email, c.s_code1, c.s_contactno1, c.s_code2, c.s_contactno2, c.s_code3, c.s_contactno3, ");
        sb.append("c.s_gender, t_country.s_name, n.s_nationality, c.s_address1line1, c.s_address1line2, c.s_address2line1, ");
        sb.append("c.s_address2line2, c.s_address2line3, c.s_nextofkin, t_relation.s_name, c.s_ecode1, c.s_econtactno1, c.s_ecode2, ");
        sb.append("c.s_econtactno2, t_maritialstatus.s_name, c.s_photofilename, t_position.s_name , c.s_address1line3, t_city.s_name, ");
        sb.append("t_experiencedept.s_name,c.i_expectedsalary,t_currency.s_name, c.s_firstname, t_grade.s_name,  c.s_lastname, ");
        sb.append("c.s_middlename, t_healthdeclaration.s_bloodgroup, p2.s_name, g2.s_name, t_dayrate.d_rate1, t_dayrate.d_rate2, d2.d_rate1, d2.d_rate2 , t_assettype.s_name, ");
        sb.append("ROUND(DATEDIFF(CASE WHEN c.d_dob = '0000-00-00' THEN c.d_dob ELSE CURRENT_DATE() END, c.d_dob) / 365, 1 ) AS age, ");
        sb.append("t_position.i_positionid, p2.i_positionid, c.s_profile, c.s_skill1, c.s_skill2 ");
        sb.append("FROM t_candidate AS c ");
        sb.append("LEFT JOIN t_country ON (t_country.i_countryid = c.i_countryid ) ");
        sb.append("LEFT JOIN t_country AS n ON (n.i_countryid = c.i_nationalityid) ");
        sb.append("LEFT JOIN t_city  ON (t_city.i_cityid = c.i_cityid) ");
        sb.append("LEFT JOIN t_position ON (t_position.i_positionid = c.i_positionid) ");
        sb.append("LEFT JOIN t_grade ON (t_grade.i_gradeid = t_position.i_gradeid) ");
        sb.append("LEFT JOIN t_position AS p2 ON (p2.i_positionid = c.i_positionid2) ");
        sb.append("LEFT JOIN t_grade AS g2 ON (g2.i_gradeid = p2.i_gradeid) ");
        sb.append("LEFT JOIN t_relation ON (t_relation.i_relationid = c.i_relationid) ");
        sb.append("LEFT JOIN t_maritialstatus ON (t_maritialstatus.i_maritialstatusid = c.i_maritialstatusid) ");
        sb.append("LEFT JOIN t_experiencedept ON (t_experiencedept.i_experiencedeptid = c.i_departmentid) ");
        sb.append("LEFT JOIN t_currency ON (t_currency.i_currencyid = c.i_currencyid) ");
        sb.append("LEFT JOIN t_healthdeclaration ON (t_healthdeclaration.i_candidateid = c.i_candidateid) ");
        sb.append("LEFT JOIN t_assettype ON (t_assettype.i_assettypeid = c.i_assettypeid) ");
        sb.append("LEFT JOIN t_dayrate ON (t_dayrate.i_candidateid = c.i_candidateid AND t_dayrate.i_positionid = c.i_positionid AND t_dayrate.i_clientassetid = c.i_clientassetid) ");
        sb.append("LEFT JOIN t_dayrate AS d2 ON (d2.i_candidateid = c.i_candidateid AND d2.i_positionid = c.i_positionid2 AND d2.i_clientassetid = c.i_clientassetid) ");
        sb.append("WHERE c.i_status = 1 AND c.i_candidateid = ? ");
        String query = (sb.toString()).intern();
        sb.setLength(0);

        Connection conn = null;
        ResultSet rs = null;
        StringBuilder sbtemp = new StringBuilder();
        sbtemp.append("SELECT s_description, s_wids,s_eids,s_dids, s_tids, s_description2, s_description3, s_filename, i_exptype, ");
        sbtemp.append("s_label1, s_label2, s_label3, s_label4, s_label5, s_label6, s_label7, s_label8, s_label9, s_label10, s_label11, s_label12, "); 
        sbtemp.append("s_label13, s_label14, s_label15, s_label16, s_label17, s_label18, s_label19, s_label20, s_label21, s_label22, s_label23, ");
        sbtemp.append("s_label24, s_label25, s_label26, i_edutype, i_doctype, i_certtype ");
        sbtemp.append("FROM t_resumetemplate WHERE i_resumetemplateid = ?");
        String fle_query = (sbtemp.toString()).intern();
        sbtemp.setLength(0);
        
        StringBuilder sbinner = new StringBuilder();
        sbinner.append("SELECT  t_position.s_name,  t_experiencedept.s_name, t_workexperience.s_companyname, t_workexperience.s_assetname,  ");
        sbinner.append("DATE_FORMAT(t_workexperience.d_workstartdate, '%b-%y'), DATE_FORMAT(t_workexperience.d_workenddate, '%b-%y'), ");
        sbinner.append("t_workexperience.i_currentworkingstatus, t_assettype.s_name, t_country.s_name, t_city.s_name, ");
        sbinner.append("t_workexperience.s_role, ROUND(DATEDIFF(IF(d_workenddate IS NULL OR d_workenddate = '0000-00-00', current_date(), d_workenddate), d_workstartdate) / 365, 1) AS exp ");
        sbinner.append("FROM t_workexperience ");
        sbinner.append("LEFT JOIN t_position ON (t_position.i_positionid = t_workexperience.i_positionid) ");
        sbinner.append("LEFT JOIN t_experiencedept ON (t_experiencedept.i_experiencedeptid = t_workexperience.i_departmentid) ");
        sbinner.append("LEFT JOIN t_assettype ON (t_assettype.i_assettypeid = t_workexperience.i_assettypeid) ");
        sbinner.append("LEFT JOIN t_country ON (t_country.i_countryid = t_workexperience.i_countryid) ");
        sbinner.append("LEFT JOIN t_city ON(t_city.i_cityid = t_workexperience.i_cityid) ");
        sbinner.append("WHERE t_workexperience.i_status = 1 AND t_workexperience.i_candidateid = ? ORDER BY t_workexperience.i_status, t_workexperience.d_workstartdate DESC LIMIT 5 ");
        String sbinnerquery = sbinner.toString();
        sbinner.setLength(0);
        PreparedStatement pstmtinner = null;

        StringBuilder certsbinner = new StringBuilder();
        certsbinner.append("SELECT  t_coursename.s_name,  t_coursetype.s_name, t_trainingandcert.s_educinst, t_city.s_name,  t_approvedby.s_name, ");
        certsbinner.append("DATE_FORMAT(t_trainingandcert.d_dateofissue,  '%d/%m/%Y'), DATE_FORMAT(t_trainingandcert.d_expirydate,  '%d/%m/%Y'), ");
        certsbinner.append("t_country.s_name, DATEDIFF(t_trainingandcert.d_expirydate, CURRENT_DATE) ");
        certsbinner.append("FROM t_trainingandcert ");
        certsbinner.append("LEFT JOIN t_coursetype ON ( t_coursetype.i_coursetypeid = t_trainingandcert.i_coursetypeid ) ");
        certsbinner.append("LEFT JOIN t_coursename ON (t_coursename.i_coursenameid = t_trainingandcert.i_coursenameid) ");
        certsbinner.append("LEFT JOIN t_city ON ( t_city.i_cityid = t_trainingandcert.i_locationofinstitid ) ");
        certsbinner.append("LEFT JOIN t_country ON ( t_city.i_countryid = t_country. i_countryid ) ");
        certsbinner.append("LEFT JOIN t_approvedby ON ( t_approvedby.i_approvedbyid = t_trainingandcert.i_approvedbyid ) ");
        certsbinner.append("WHERE t_trainingandcert.i_candidateid = ? AND t_trainingandcert.i_status = 1 ");
        String certsbinnerquery = certsbinner.toString();
        certsbinner.setLength(0);
        PreparedStatement pstmtcertinner = null;

        StringBuilder bosietsb = new StringBuilder();
        bosietsb.append("SELECT i_coursenameid, DATEDIFF( d_expirydate, CURRENT_DATE)+1 ");
        bosietsb.append("FROM t_trainingandcert ");
        bosietsb.append("WHERE i_coursenameid = 57 AND i_status = 1 AND i_candidateid = ?");
        String bosietsbquery = bosietsb.toString();
        bosietsb.setLength(0);
        PreparedStatement pstmtbosiet = null;

        StringBuilder h2ssb = new StringBuilder();
        h2ssb.append("SELECT i_coursenameid, DATEDIFF( d_expirydate, CURRENT_DATE)+1 ");
        h2ssb.append("FROM t_trainingandcert ");
        h2ssb.append("WHERE i_coursenameid IN(11,176) AND i_status = 1 AND i_candidateid = ?");
        String h2ssbquery = h2ssb.toString();
        h2ssb.setLength(0);
        PreparedStatement pstmth2s = null;

        //Proficiency
        StringBuilder prosb = new StringBuilder();
        prosb.append("SELECT t_language.i_languageid, t_proficiency.s_name ");
        prosb.append("FROM t_language ");
        prosb.append("LEFT JOIN t_candlang ON( t_language.i_languageid = t_candlang.i_languageid ) ");
        prosb.append("LEFT JOIN t_proficiency ON(t_proficiency.i_proficiencyid = t_candlang.i_proficiencyid) ");
        prosb.append("WHERE t_language.i_languageid = 1 AND t_candlang.i_status = 1 AND t_candlang.i_candidateid = ? ");
        String prosbquery = prosb.toString();
        prosb.setLength(0);
        PreparedStatement pstmtpro = null;
        
        //Language list
        StringBuilder langsb = new StringBuilder();
        langsb.append("SELECT t_language.i_languageid, t_language.s_name ");
        langsb.append("FROM t_language ");
        langsb.append("LEFT JOIN t_candlang ON( t_language.i_languageid = t_candlang.i_languageid ) ");
        langsb.append("WHERE t_candlang.i_status = 1 AND t_candlang.i_candidateid = ? ");
        String langquery = langsb.toString();
        langsb.setLength(0);
        PreparedStatement pstmtlang = null;
        

        //Total work exp
        StringBuilder expsb = new StringBuilder();
        expsb.append("SELECT  ROUND(SUM(DATEDIFF(CASE WHEN i_currentworkingstatus = 1 THEN CURRENT_DATE ELSE d_workenddate END, d_workstartdate)+1)/365,1) ");
        expsb.append("FROM t_workexperience WHERE i_status = 1 AND i_candidateid = ? ");
        String expsbquery = expsb.toString();
        expsb.setLength(0);
        PreparedStatement pstmtexp = null;

        StringBuilder totalRotationexpsb = new StringBuilder();
        totalRotationexpsb.append("SELECT ROUND(SUM(DATEDIFF(t_cractivity.ts_todate, t_cractivity.ts_fromdate)) /365,1) ");
        totalRotationexpsb.append("FROM t_cractivity ");
        totalRotationexpsb.append("LEFT JOIN t_crewrotation ON (t_crewrotation.i_crewrotationid = t_cractivity.i_crewrotationid) ");
        totalRotationexpsb.append("WHERE t_crewrotation.i_candidateid = ? AND t_cractivity.i_activityid = 6 ");
        String totalRotationquery = totalRotationexpsb.toString();
        totalRotationexpsb.setLength(0);
        PreparedStatement pstmttotalRotation = null;

        StringBuilder p1expsb = new StringBuilder();
        p1expsb.append("SELECT ROUND(SUM(DATEDIFF(t_cractivity.ts_todate, t_cractivity.ts_fromdate)) /365,1) ");
        p1expsb.append("FROM t_cractivity ");
        p1expsb.append("LEFT JOIN t_crewrotation ON (t_crewrotation.i_crewrotationid = t_cractivity.i_crewrotationid) ");
        p1expsb.append("WHERE t_crewrotation.i_candidateid = ? AND t_cractivity.i_positionid = ? AND t_cractivity.i_activityid = 6 ");
        String p1expquery = p1expsb.toString();
        p1expsb.setLength(0);
        PreparedStatement pstmtp1 = null;

        String clientname = "";
        StringBuilder clientinner = new StringBuilder();
        clientinner.append(" SELECT s_name FROM t_client WHERE i_status = 1 AND i_clientid = ? ");
        String clientquery = clientinner.toString();
        clientinner.setLength(0);
        PreparedStatement pstmtclient = null;

        int vstatus = 0;
        StringBuilder vinner = new StringBuilder();
        vinner.append("SELECT i_vstatus FROM t_govdoc WHERE i_candidateid = ? AND i_doctypeid = 1 AND i_status = 1 LIMIT 1 ");
        String vquery = vinner.toString();
        vinner.setLength(0);
        PreparedStatement pstmtvstatus = null;

        double dayrate = 0;
        String currencyrate = "";
        StringBuilder dayrateinner = new StringBuilder();
        dayrateinner.append("SELECT t_currency.s_name, t_jobpost.d_dayratevalue FROM t_jobpost ");
        dayrateinner.append("LEFT JOIN t_currency ON (t_currency.i_currencyid = t_jobpost.i_currencyid) ");
        dayrateinner.append("WHERE t_jobpost.i_status = 1 AND t_jobpost.i_jobpostid = ? ");
        String dayratequery = dayrateinner.toString();
        dayrateinner.setLength(0);
        PreparedStatement pstmtdayrate = null;

        StringBuilder edusbinner = new StringBuilder();
        edusbinner.append("SELECT  t_qualificationtype.s_name,  t_degree.s_name, t_eduqual.s_eduinstitute, ");
        edusbinner.append("t_city.s_name,t_eduqual.s_fieldofstudy, DATE_FORMAT(t_eduqual.d_coursestart, '%b-%y'), ");
        edusbinner.append("DATE_FORMAT(t_eduqual.d_passingyear, '%b-%y') ,t_country.s_name, t_eduqual.i_highestqualification ");
        edusbinner.append("FROM t_eduqual ");
        edusbinner.append("LEFT JOIN t_qualificationtype ON (t_qualificationtype.i_qualificationtypeid = t_eduqual.i_qualificationtypeid) ");
        edusbinner.append("LEFT JOIN t_city ON (t_city.i_cityid = t_eduqual.i_locationofinstituteid) ");
        edusbinner.append("LEFT JOIN t_degree ON (t_degree.i_degreeid = t_eduqual. i_degreeid) ");
        edusbinner.append("LEFT JOIN t_country ON (t_city.i_countryid = t_country. i_countryid) ");
        edusbinner.append("WHERE t_eduqual.i_status = 1 AND t_eduqual.i_candidateid = ? ");
        String edusbinnerquery = edusbinner.toString();
        edusbinner.setLength(0);
        PreparedStatement pstmteduinner = null;
        sb.setLength(0);

        sb.append("SELECT t_doctype.s_doc, t_govdoc.s_docno, t_city.s_name, t_documentissuedby.s_name, ");
        sb.append("DATE_FORMAT(t_govdoc.d_dateofissue, '%b-%y'), DATE_FORMAT(t_govdoc.d_expirydate, '%b-%y'), ");
        sb.append("t_country.s_name, t_govdoc.i_doctypeid ");
        sb.append("FROM t_govdoc ");
        sb.append("LEFT JOIN t_doctype ON (t_govdoc.i_doctypeid = t_doctype.i_doctypeid) ");
        sb.append("LEFT JOIN t_city ON (t_govdoc.i_placeofissueid = t_city.i_cityid) ");
        sb.append("LEFT JOIN t_documentissuedby ON (t_govdoc.i_documentissuedbyid = t_documentissuedby.i_documentissuedbyid) ");
        sb.append("LEFT JOIN t_country ON (t_city.i_countryid = t_country.i_countryid) ");
        sb.append("WHERE t_govdoc.i_status =1 AND t_govdoc.i_candidateid =? ");
        String docsbinnerquery = (sb.toString()).intern();
        sb.setLength(0);
        PreparedStatement pstmtdocinner = null;

        sb.append("SELECT t_govdoc.i_doctypeid,  ROUND(SUM(DATEDIFF(t_govdoc.d_expirydate, CURRENT_DATE)+1 )/365, 1)");
        sb.append("FROM t_govdoc ");
        sb.append("WHERE t_govdoc.i_status =1 AND t_govdoc.i_candidateid =?  AND t_govdoc.i_doctypeid = 2");
        String docsbinnerquery1 = (sb.toString()).intern();
        sb.setLength(0);
        PreparedStatement pstmtdocinner1 = null;

        //For PCC
        sb.append("SELECT t_govdoc.i_doctypeid, ROUND(SUM(DATEDIFF(t_govdoc.d_expirydate, CURRENT_DATE)+1) /365,1) ");
        sb.append("FROM t_govdoc ");
        sb.append("WHERE t_govdoc.i_status =1 AND t_govdoc.i_candidateid =? AND t_govdoc.i_doctypeid = 6");
        String docsbinnerquery2 = (sb.toString()).intern();
        sb.setLength(0);
        PreparedStatement pstmtdocinner2 = null;
        sb.setLength(0);

        try {
            conn = getConnection();
            pstmtinner = conn.prepareStatement(sbinnerquery);
            pstmtdocinner = conn.prepareStatement(docsbinnerquery);
            pstmtdocinner1 = conn.prepareStatement(docsbinnerquery1);
            pstmtdocinner2 = conn.prepareStatement(docsbinnerquery2);
            pstmtcertinner = conn.prepareStatement(certsbinnerquery);
            pstmtbosiet = conn.prepareStatement(bosietsbquery);
            pstmth2s = conn.prepareStatement(h2ssbquery);
            pstmtpro = conn.prepareStatement(prosbquery);
            pstmtlang = conn.prepareStatement(langquery);
            pstmtexp = conn.prepareStatement(expsbquery);
            pstmttotalRotation = conn.prepareStatement(totalRotationquery);
            pstmtp1 = conn.prepareStatement(p1expquery);
            PreparedStatement pstmtp2 = null;
            PreparedStatement pstmtprimary = null;

            pstmt = conn.prepareStatement(fle_query);
            pstmt.setInt(1, resumetemplateId);
            String wids = "", eids = "", dids = "", tids = "", headerBody = "", footerBody = "", watermarkurl = "",
                    label1 = "", label2 = "", label3 = "", label4 = "", label5 = "", label6 = "", label7 = "", label8 = "", 
                    label9 = "", label10 = "", label11 = "", label12 = "", label13 = "", label14 = "", label15 = "",
                    label16 = "", label17 = "", label18 = "", label19 = "", label20 = "", label21 = "", label22 = "", label23 = "",
                    label24 = "", label25 = "", label26 = "";
            int exptype = 0, edutype =0, docutype =0, certtype =0;
            rs = pstmt.executeQuery();
            while (rs.next()) {
                templatename = rs.getString(1);
                wids = rs.getString(2) != null ? rs.getString(2) : "";
                eids = rs.getString(3) != null ? rs.getString(3) : "";
                dids = rs.getString(4) != null ? rs.getString(4) : "";
                tids = rs.getString(5) != null ? rs.getString(5) : "";
                headerBody = rs.getString(6) != null ? rs.getString(6) : "";
                footerBody = rs.getString(7) != null ? rs.getString(7) : "";
                watermarkurl = rs.getString(8) != null ? rs.getString(8) : "";
                exptype = rs.getInt(9);  
                
                label1 = rs.getString(10) != null ? rs.getString(10) : "";
                label2 = rs.getString(11) != null ? rs.getString(11) : "";
                label3 = rs.getString(12) != null ? rs.getString(12) : "";
                label4 = rs.getString(13) != null ? rs.getString(13) : "";
                label5 = rs.getString(14) != null ? rs.getString(14) : "";
                label6 = rs.getString(15) != null ? rs.getString(15) : "";
                label7 = rs.getString(16) != null ? rs.getString(16) : "";
                label8 = rs.getString(17) != null ? rs.getString(17) : "";
                label9 = rs.getString(18) != null ? rs.getString(18) : "";
                label10 = rs.getString(19) != null ? rs.getString(19) : "";
                label11 = rs.getString(20) != null ? rs.getString(20) : "";
                label12 = rs.getString(21) != null ? rs.getString(21) : "";
                label13 = rs.getString(22) != null ? rs.getString(22) : "";                
                label14 = rs.getString(23) != null ? rs.getString(23) : "";
                label15 = rs.getString(24) != null ? rs.getString(24) : "";
                label16 = rs.getString(25) != null ? rs.getString(25) : "";
                label17 = rs.getString(26) != null ? rs.getString(26) : "";
                label18 = rs.getString(27) != null ? rs.getString(27) : "";
                label19 = rs.getString(28) != null ? rs.getString(28) : "";
                label20 = rs.getString(29) != null ? rs.getString(29) : "";
                label21 = rs.getString(30) != null ? rs.getString(30) : "";
                label22 = rs.getString(31) != null ? rs.getString(31) : "";
                label23 = rs.getString(32) != null ? rs.getString(32) : "";
                label24 = rs.getString(33) != null ? rs.getString(33) : "";
                label25 = rs.getString(34) != null ? rs.getString(34) : "";
                label26 = rs.getString(35) != null ? rs.getString(35) : "";
                edutype = rs.getInt(36);
                docutype = rs.getInt(37);
                certtype = rs.getInt(38);
            }
            rs.close();

            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, candidateId);
            //print(this, "Personnel_Data :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            boolean bcoursenameId2 = false;
            String name, dob, birthplace, email, contact1_code, contact1, contact2_code, contact2,
                    contact3_code, contact3, gender, countryName, nationality, address1line1, address1line2, address2line1,
                    address2line2, address2line3, nextofkin, relation, econtact1_code, econtact1, econtact2_code, econtact2,
                    maritialstatus, photo, positionName, address1line3, city, experiencedept, currency, firstname, gradeName,
                    lastname, middlename, bloodgroup, position2, grade2, assettype;
            double rate1, rate2, p2rate1, p2rate2;
            while (rs.next()) {
                name = rs.getString(1) != null ? rs.getString(1) : "";
                dob = rs.getString(2) != null ? rs.getString(2) : "";
                birthplace = rs.getString(3) != null ? rs.getString(3) : "";
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
                city = rs.getString(29) != null ? rs.getString(29) : "";
                experiencedept = rs.getString(30) != null ? rs.getString(30) : "";
                int expectedsalary = rs.getInt(31);
                currency = rs.getString(32) != null ? rs.getString(32) : "";
                firstname = rs.getString(33) != null ? rs.getString(33) : "";
                gradeName = rs.getString(34) != null ? rs.getString(34) : "";
                lastname = rs.getString(35) != null ? rs.getString(35) : "";
                middlename = rs.getString(36) != null ? rs.getString(36) : "";
                bloodgroup = decipher(rs.getString(37) != null ? rs.getString(37) : "");
                position2 = rs.getString(38) != null ? rs.getString(38) : "";
                grade2 = rs.getString(39) != null ? rs.getString(39) : "";
                rate1 = rs.getDouble(40);
                rate2 = rs.getDouble(41);
                p2rate1 = rs.getDouble(42);
                p2rate2 = rs.getDouble(43);
                assettype = rs.getString(44) != null ? rs.getString(44) : "";
                int age = rs.getInt(45);
                int positionId1 = rs.getInt(46);
                int positionId2 = rs.getInt(47);
                String profile = rs.getString(48) != null ? rs.getString(48) : "";
                String skill1 = rs.getString(49) != null ? rs.getString(49) : "";
                String skill2 = rs.getString(50) != null ? rs.getString(50) : "";
                
                String p1 = "", pstr ="", s01 = "", s1str ="", s02= "", s2str="";
                p1 = profile.replaceAll("\n", "</li><li>");
                pstr = "<ul><li>"+p1+"</li></ul>";
                
                s01 = skill1.replaceAll("\n", "</li><li>");
                s1str = "<ul><li>"+s01+"</li></ul>";
                
                s02 = skill2.replaceAll("\n", "</li><li>");
                s2str = "<ul><li>"+s02+"</li></ul>";
                
                if (!positionName.equals("")) {
                    if (!gradeName.equals("")) {
                        positionName += " - " + gradeName;
                    }
                }
                if (!position2.equals("")) {
                    if (!grade2.equals("")) {
                        position2 += " - " + grade2;
                    }
                }
                if (contact1_code != null && !contact1_code.equals("")) {
                    contact1 = "+" + contact1_code + " " + contact1;
                }
                if (contact2_code != null && !contact2_code.equals("")) {
                    contact2 = "+" + contact2_code + " " + contact2;
                }
                if (contact3_code != null && !contact3_code.equals("")) {
                    contact3 = "+" + contact3_code + " " + contact3;
                }
                if (econtact1_code != null && !econtact1_code.equals("")) {
                    econtact1 = "+" + econtact1_code + " " + econtact1;
                }
                if (econtact2_code != null && !econtact2_code.equals("")) {
                    econtact2 = "+" + econtact2_code + " " + econtact2;
                }

                StringBuilder p2expsb = new StringBuilder();
                double p2RotationDays = 0;
                if (positionId2 > 0) {
                    p2expsb.append("SELECT ROUND(SUM(DATEDIFF(t_cractivity.ts_todate, t_cractivity.ts_fromdate))  /365,1) ");
                    p2expsb.append("FROM t_cractivity ");
                    p2expsb.append("LEFT JOIN t_crewrotation ON (t_crewrotation.i_crewrotationid = t_cractivity.i_crewrotationid) ");
                    p2expsb.append("WHERE t_crewrotation.i_candidateid = ? AND t_cractivity.i_positionid = ? AND t_cractivity.i_activityid = 6 ");
                    String p2expquery = p2expsb.toString();
                    p2expsb.setLength(0);

                    pstmtp2 = conn.prepareStatement(p2expquery);
                    pstmtp2.setInt(1, candidateId);
                    pstmtp2.setInt(2, positionId2);
                    //print(this, "Total P2 Expeience :: " + pstmtp2.toString());
                    ResultSet rsp2 = pstmtp2.executeQuery();
                    while (rsp2.next()) {
                        p2RotationDays = rsp2.getDouble(1);
                    }
                    rsp2.close();
                }

                StringBuilder primaryPositionsb = new StringBuilder();
                double p1DateDiff = 0;
                primaryPositionsb.append("SELECT ROUND(SUM(DATEDIFF(CASE  WHEN t_workexperience.i_currentworkingstatus = 1 THEN CURRENT_DATE  ELSE t_workexperience.d_workenddate  END, t_workexperience.d_workstartdate)+1)/ 365 ,1) ");
                primaryPositionsb.append("FROM t_workexperience ");
                primaryPositionsb.append("LEFT JOIN t_candidate ON(t_candidate.i_candidateid = t_workexperience.i_candidateid  AND t_candidate.i_positionid = t_workexperience.i_positionid ) ");
                primaryPositionsb.append("WHERE t_workexperience.i_status = 1 AND t_workexperience.i_candidateid = ?  AND t_candidate.i_positionid = ? ");

                String primaryPositionquery = primaryPositionsb.toString();
                primaryPositionsb.setLength(0);
                pstmtprimary = conn.prepareStatement(primaryPositionquery);

                pstmtprimary.setInt(1, candidateId);
                pstmtprimary.setInt(2, positionId1);
                //print(this, "Total Primary Expeience :: " + pstmtprimary.toString());
                ResultSet rsprimary = pstmtprimary.executeQuery();
                while (rsprimary.next()) {
                    p1DateDiff = rsprimary.getDouble(1);
                }
                rsprimary.close();

                pstmtinner.setInt(1, candidateId);
                print(this, "Experience_Data :: " + pstmtinner.toString());
                ResultSet rsinner = pstmtinner.executeQuery();
                StringBuilder we = new StringBuilder();
                String position, department, companyname, assetname, startdate, enddate, 
                        assetType,  country, roles="", rolestr, cityname;
                int currentworkingstatus;
                double experience;
                String add_path = getMainPath("add_candidate_file");
                while (rsinner.next()) 
                {
                    cc++;
                    position = rsinner.getString(1) != null ? rsinner.getString(1) : "";
                    department = rsinner.getString(2) != null ? rsinner.getString(2) : "";
                    companyname = rsinner.getString(3) != null ? rsinner.getString(3) : "";
                    assetname = rsinner.getString(4) != null ? rsinner.getString(4) : "";
                    startdate = rsinner.getString(5) != null ? rsinner.getString(5) : "";
                    enddate = rsinner.getString(6) != null ? rsinner.getString(6) : "";
                    currentworkingstatus = rsinner.getInt(7);
                    assetType = rsinner.getString(8) != null ? rsinner.getString(8) : "";
                    country = rsinner.getString(9) != null ? rsinner.getString(9) : "";
                    cityname = rsinner.getString(10) != null ? rsinner.getString(10) : "";
                    roles = rsinner.getString(11) != null ? rsinner.getString(11) : "";
                    experience = rsinner.getDouble(12);
                    if (currentworkingstatus == 1) {
                        enddate = "Present";
                    }
                    
                    if(exptype== 1)
                    {
                        we.append("<tr style='border: 1px solid black;text-align:center;'>");
                        if (wids.contains("")) {
                            we.append("<td style='border: 1px solid black;text-align:center;'>" + cc + "</td>");
                        }
                        if (wids.contains("3")) {
                            we.append("<td style='border: 1px solid black;text-align:center;'>" + companyname + "</td>");
                        }
                        if (wids.contains("4")) {
                            we.append("<td style='border: 1px solid black;text-align:center;'>" + assetname + "</td>");
                        }
                        if (wids.contains("5")) {
                            we.append("<td style='border: 1px solid black;text-align:center;'>" + assetType + "</td>");
                        }
                        if (wids.contains("1")) {
                            we.append("<td style='border: 1px solid black;text-align:center;'>" + position + "</td>");
                        }
                        if (wids.contains("2")) {
                            we.append("<td style='border: 1px solid black;text-align:center;'>" + department + "</td>");
                        }
                        if (wids.contains("6")) {
                            we.append("<td style='border: 1px solid black;text-align:center;'>" + startdate + "</td>");
                        }
                        if (wids.contains("7")) {
                            we.append("<td style='border: 1px solid black;text-align:center;'>" + enddate + "</td>");
                        }
                        we.append("</tr>");
                    }else{
                        
                        we.append("<tr><td colspan='2' style='height:10px;'></td></tr>");
                        //we.append("<tr><td colspan='2' style='text-decoration:underline;text-align:left;font-weight:bold;'>PROFESSIONAL EXPERIENCE</td></tr>");
                        we.append("<tr>");
                        we.append("<td colspan='2'>");
                        we.append("<ul style='list-style-type: none;padding-left:0px;'>");
                        we.append("<li style='margin-bottom:30px;'>");
                        we.append("<div style='float:left; font-size: 16pt;'><img src='"+img_path+"dot.png' style='width: 6px; margin-right:10px; position:relative; top:-2px;'>"+ position + ", " + country + "</div>");
                        we.append("<div style='float:right; font-size: 16pt;'>("+startdate+" to  "+  enddate+")</div><br/><br/>");

                        we.append("<b style='font-size: 16pt;'>Project</b>:<br/><span style='font-size: 16pt;'>" + assetname + "-" + cityname + "(" + country + ").</span><br/>");
                        we.append("<b style='font-size: 16pt;'>Client </b>-<span style='font-size: 16pt;'>" + companyname + "</span><br/><br/><br/>");

                        we.append("<b style='font-size: 16pt;'>Roles & Responsibilities:</b><br/>");
                        we.append("<ul style='list-style-type: disc;margin-top:10px;'>");
                        if(roles != null && !roles.equals(""))
                        {                            
                            rolestr = readHTMLFile(roles, add_path).replaceAll("<li>", "<li style='font-size: 16pt;'>");
                            we.append(rolestr);
                        }
                        we.append("</ul>");
                        we.append("</li>");
                        we.append("</ul>");
                        we.append("</td>");
                        we.append("</tr>");
                    }
                }
                rsinner.close();

                String westr = we.toString();
                we.setLength(0);
                String wevalue = "";
                if (!westr.equals("")) 
                {
                    if(exptype== 1)
                    {
                        wevalue += "<tr style='text-align:center;'><td style='height:10px;'></td></tr>";
                        wevalue += "<tr style='text-align:center;'><td style='text-decoration:underline;text-align:right; font-weight:bold; font-family: georgia, palatino; font-size: 20px;'></td></tr>";
                        wevalue += "<tr style='text-align:center;'>";
                        wevalue += "<td style='text-align:center;' colspan='2'>";
                        wevalue += "<table cellspacing='5' cellpadding='5' align='center' style='width:100%;border-collapse: collapse;'>";
                        wevalue += ("<tr style='background-color: #b0d8e6'>");
                        if (wids.contains("")) {
                            wevalue += ("<td width='5%' style='border: 1px solid black; font-family: georgia, palatino; font-size: 20px;text-align:center; text-align:center;'>Sr No.</td>");
                        }
                        if (wids.contains("3")) {
                            if(label1.equals("") || label1 == null)
                                wevalue += ("<td width='20%' style='border: 1px solid black; font-family: georgia, palatino; font-size: 20px;text-align:center; text-align:center;'>Company Name</td>");
                            else
                                wevalue += ("<td width='20%' style='border: 1px solid black; font-family: georgia, palatino; font-size: 20px;text-align:center; text-align:center;'>"+label1+"</td>");
                        }
                        if (wids.contains("4")) {
                            if(label2.equals("") || label2 == null)
                                wevalue += ("<td width='10%' style='border: 1px solid black; font-family: georgia, palatino; font-size: 20px;text-align:center;text-align:center;'>Rig Name</td>");
                            else 
                                wevalue += ("<td width='10%' style='border: 1px solid black; font-family: georgia, palatino; font-size: 20px;text-align:center;text-align:center;'>"+label2+"</td>");
                        }
                        if (wids.contains("5")) {
                            if(label3.equals("") || label3 == null)
                                wevalue += ("<td width='10%' style='border: 1px solid black; font-family: georgia, palatino; font-size: 20px;text-align:center;text-align:center;'>Rig Type</td>");
                            else
                                wevalue += ("<td width='10%' style='border: 1px solid black; font-family: georgia, palatino; font-size: 20px;text-align:center;text-align:center;'>"+label3+"</td>");
                        }
                        if (wids.contains("1")) {
                            if(label4.equals("") || label4 == null)
                                wevalue += ("<td width='15%' style='border: 1px solid black; font-family: georgia, palatino; font-size: 20px;text-align:center;text-align:center;'>Designation</td>");
                            else 
                                wevalue += ("<td width='15%' style='border: 1px solid black; font-family: georgia, palatino; font-size: 20px;text-align:center;text-align:center;'>"+label4+"</td>");
                        }
                        if (wids.contains("2")) {
                            if(label5.equals("") || label5 == null)
                                wevalue += ("<td width='15%' style='border: 1px solid black; font-family: georgia, palatino; font-size: 20px;text-align:center;text-align:center;'>Department</td>");
                            else     
                                wevalue += ("<td width='15%' style='border: 1px solid black; font-family: georgia, palatino; font-size: 20px;text-align:center;text-align:center;'>"+label5+"</td>");
                        }
                        if (wids.contains("6")) {
                            if(label6.equals("") || label6 == null)
                                wevalue += ("<td width='15%' style='border: 1px solid black; font-family: georgia, palatino; font-size: 20px;text-align:center;text-align:center;'>Start Date</td>");
                            else 
                                wevalue += ("<td width='15%' style='border: 1px solid black; font-family: georgia, palatino; font-size: 20px;text-align:center;text-align:center;'>"+label6+"</td>");
                        }
                        if (wids.contains("7")) {
                            if(label7.equals("") || label7 == null)
                                wevalue += ("<td width='10%' style='border: 1px solid black; font-family: georgia, palatino; font-size: 20px;text-align:center;text-align:center;'>End Date</td>");
                            else
                                wevalue += ("<td width='10%' style='border: 1px solid black; font-family: georgia, palatino; font-size: 20px;text-align:center;text-align:center;'>"+label7+"</td>");
                        }
                        wevalue += ("</tr>");
                        wevalue += westr;
                        wevalue += "</table>";
                        wevalue += "</td>";
                        wevalue += "</tr>";
                    }
                    else
                    {
                       wevalue += westr;
                    }
                }

                //For passport validity
                pstmtdocinner1.setInt(1, candidateId);
                //print(this, "Document_Details1 :: " + pstmtdocinner1.toString());
                ResultSet rsdoc1 = pstmtdocinner1.executeQuery();
                int doctype1 = 0;
                double dateDiff1 = 0;
                while (rsdoc1.next()) {
                    doctype1 = rsdoc1.getInt(1);
                    dateDiff1 = rsdoc1.getDouble(2);
                }
                rsdoc1.close();

                pstmtdocinner2.setInt(1, candidateId);
                //print(this, "Document_Details2 :: " + pstmtdocinner2.toString());
                ResultSet rsdoc2 = pstmtdocinner2.executeQuery();
                int doctype2 = 0;
                double dateDiff2 = 0;
                while (rsdoc2.next()) {
                    doctype2 = rsdoc2.getInt(1);
                    dateDiff2 = rsdoc2.getDouble(2);
                }
                rsdoc2.close();

                pstmtbosiet.setInt(1, candidateId);
                //print(this, "Bosiet_Details :: " + pstmtbosiet.toString());
                ResultSet rsbosiet = pstmtbosiet.executeQuery();
                int coursenameId1 = 0, cdateDiff1 = 0;
                String cdateValue1 = "";
                while (rsbosiet.next()) {
                    coursenameId1 = rsbosiet.getInt(1);
                    cdateDiff1 = rsbosiet.getInt(2);
                    cdateValue1 = getM(cdateDiff1);
                }
                rsbosiet.close();

                pstmth2s.setInt(1, candidateId);
                //print(this, "H2S_Details :: " + pstmth2s.toString());
                ResultSet rsh2s = pstmth2s.executeQuery();
                int coursenameId2 = 0, cdateDiff2 = 0;
                String cdateValue2 = "";
                while (rsh2s.next()) {
                    coursenameId2 = rsh2s.getInt(1);
                    cdateDiff2 = rsh2s.getInt(2);
                    cdateValue2 = getM(cdateDiff2);
                }
                rsh2s.close();

                pstmtpro.setInt(1, candidateId);
                //print(this, "Proficiency :: " + pstmtpro.toString());
                ResultSet rspro = pstmtpro.executeQuery();
                String proficiency = "";
                int languageId;
                while (rspro.next()) {
                    languageId = rspro.getInt(1);
                    proficiency = rspro.getString(2) != null ? rspro.getString(2) : "";
                }
                rspro.close();
                
                pstmtlang.setInt(1, candidateId);
                //print(this, "Language :: " + pstmtlang.toString());
                StringBuilder lsb = new StringBuilder();
                ResultSet rslang = pstmtlang.executeQuery();
                String lang = "";
                int langId;
                while (rslang.next()) {
                    languageId = rslang.getInt(1);
                    lang = rslang.getString(2) != null ? rslang.getString(2) : "";
                    if(!lang.equals(""))
                    {
                        lsb.append(lang);
                    }
                }
                rslang.close();
                
                String langstr = lsb.toString().replaceAll("([a-z])([A-Z])","$1,$2");;
                lsb.setLength(0);
                String langvalue = "";
                if(!langstr.equals(""))
                {
                    langvalue = langstr;
                }
                if(!langvalue.equals(""))
                {
                    String[] s2 = langvalue.split(",");
                    int size = s2.length;                    
                    if (size > 1) {
                        langvalue = String.join(", ", java.util.Arrays.copyOfRange(s2, 0, s2.length - 1)) + " and " + s2[s2.length - 1];
                    }
                }

                //Total work exp
                pstmtexp.setInt(1, candidateId);
                //print(this, "Total Expeience :: " + pstmtexp.toString());
                ResultSet rsexp = pstmtexp.executeQuery();
                double totalDays = 0;
                while (rsexp.next()) {
                    totalDays = rsexp.getDouble(1);
                }
                rsexp.close();

                pstmttotalRotation.setInt(1, candidateId);
                //print(this, "Total Rotation Expeience :: " + pstmttotalRotation.toString());
                ResultSet rstr = pstmttotalRotation.executeQuery();
                double totalRotationDays = 0;
                while (rstr.next()) {
                    totalRotationDays = rstr.getDouble(1);
                }
                rstr.close();

                pstmtp1.setInt(1, candidateId);
                pstmtp1.setInt(2, positionId1);
                //print(this, "Total P1 Expeience :: " + pstmtp1.toString());
                ResultSet rsp1 = pstmtp1.executeQuery();
                double p1RotationDays = 0;
                while (rsp1.next()) {
                    p1RotationDays = rsp1.getDouble(1);
                }
                rsp1.close();

                double primarySecondaryDays = p1RotationDays + p2RotationDays;

                pstmtclient = conn.prepareStatement(clientquery);
                pstmtclient.setInt(1, clientId);
                //print(this, "clientquery :: " + pstmtclient.toString());
                ResultSet clientrs = pstmtclient.executeQuery();
                while (clientrs.next()) {
                    clientname = clientrs.getString(1) != null ? clientrs.getString(1) : "";
                }
                clientrs.close();

                pstmtvstatus = conn.prepareStatement(vquery);
                pstmtvstatus.setInt(1, candidateId);
                //print(this, "vstaus query :: " + pstmtvstatus.toString());
                ResultSet vrs = pstmtvstatus.executeQuery();
                while (vrs.next()) {
                    vstatus = vrs.getInt(1);
                }
                vrs.close();

                pstmtdayrate = conn.prepareStatement(dayratequery);
                pstmtdayrate.setInt(1, jobpostId);
                //print(this, "dayratequery :: " + pstmtdayrate.toString());
                ResultSet dayraters = pstmtdayrate.executeQuery();
                while (dayraters.next()) {
                    currencyrate = dayraters.getString(1) != null ? dayraters.getString(1) : "";
                    dayrate = dayraters.getDouble(2);
                }
                dayraters.close();

                String highestq = "";
                pstmteduinner = conn.prepareStatement(edusbinnerquery);
                pstmteduinner.setInt(1, candidateId);
                //print(this, "Education_Detalis :: " + pstmteduinner.toString());
                ResultSet edurs = pstmteduinner.executeQuery();
                StringBuilder ed = new StringBuilder();
                cc = 0;
                String kindname, degree, educinst, locationofinstit, filedofstudy;
                while (edurs.next()) {
                    cc++;
                    kindname = edurs.getString(1) != null ? edurs.getString(1) : "";
                    degree = edurs.getString(2) != null ? edurs.getString(2) : "";
                    educinst = decipher(edurs.getString(3) != null ? edurs.getString(3) : "");
                    locationofinstit = edurs.getString(4) != null ? edurs.getString(4) : "";
                    filedofstudy = edurs.getString(5) != null ? edurs.getString(5) : "";
                    startdate = edurs.getString(6) != null ? edurs.getString(6) : "";
                    enddate = edurs.getString(7) != null ? edurs.getString(7) : "";
                    String countryname = edurs.getString(8) != null ? edurs.getString(8) : "";
                    int hflag = edurs.getInt(9);
                    if (hflag == 1) {
                        highestq = kindname + " - " + degree;
                    }
                    if (!countryname.equals("")) {
                        locationofinstit += "(" + countryname + ")";
                    }
                    
                    if(edutype == 1)
                    {
                        ed.append("<tr style='font-family: georgia, palatino; font-size: 20px;text-align:center;text-align:center;'>");
                        if (eids.contains("")) {
                            ed.append("<td style='border: 1px solid black; font-family: georgia, palatino; font-size: 20px;text-align:center;'>" + cc + "</td>");
                        }
                        if (eids.contains("1")) {
                            ed.append("<td style='border: 1px solid black; font-family: georgia, palatino; font-size: 20px;text-align:center;'>" + kindname + "</td>");
                        }
                        if (eids.contains("2")) {
                            ed.append("<td style='border: 1px solid black; font-family: georgia, palatino; font-size: 20px;text-align:center;'>" + degree + "</td>");
                        }
                        if (eids.contains("3")) {
                            ed.append("<td style='border: 1px solid black; font-family: georgia, palatino; font-size: 20px;text-align:center;'>" + educinst + "," + locationofinstit + "</td>");
                        }
                        if (eids.contains("4")) {
                            ed.append("<td style='border: 1px solid black; font-family: georgia, palatino; font-size: 20px;text-align:center;'>" + filedofstudy + "</td>");
                        }
                        if (eids.contains("5")) {
                            ed.append("<td style='border: 1px solid black; font-family: georgia, palatino; font-size: 20px;text-align:center;'>" + startdate + "</td>");
                        }
                        if (eids.contains("6")) {
                            ed.append("<td style='border: 1px solid black; font-family: georgia, palatino; font-size: 20px;text-align:center;'>" + enddate + "</td>");
                        }
                        ed.append("</tr>");
                    }else{                        
                        ed.append("<tr>");
                        ed.append("<td>");
                        //ed.append("<b style='text-decoration:underline;text-align:left;font-weight:bold;'>QUALIFICATIONS</b><br/><br/>");
                        //ed.append("<b style='text-align:left;'>Education</b>");
                        ed.append("<table style='padding-left:25px;' width='100%'>");
                        ed.append("<tr>");
                        ed.append("<td style='font-size: 16pt;'><img src='"+img_path+"dot.png' style='width: 6px; margin-right:10px; position:relative; top:-2px;'>"+kindname+" ("+educinst+")</td>");
                        ed.append("<td style='font-size: 16pt;' align='right'>" + enddate + "</td>");
                        ed.append("</tr>");
                        ed.append("</table>");
                        ed.append("</td>");
                        ed.append("</tr>");
                    }
                }
                edurs.close();

                String edstr = ed.toString();
                ed.setLength(0);
                String eduvalue = "";
                if (!edstr.equals("")) 
                {
                    if(edutype == 1)
                    {
                        eduvalue += "<tr style='text-align:center;'><td style='height:10px;'></td></tr>";
                        eduvalue += "<tr style='text-align:center;'><td style='text-decoration:underline;text-align:center;font-weight:bold; font-family: georgia, palatino; font-size: 20px;'></td></tr>";
                        eduvalue += "<tr style='text-align:center;'>";
                        eduvalue += "<td style='text-align:center;' colspan='2'>";
                        eduvalue += "<table cellspacing='5' cellpadding='5' align='center' style='width:100%;border-collapse: collapse;'>";
                        eduvalue += ("<tr style='background-color: #b0d8e6'>");
                        if (eids.contains("")) {
                            eduvalue += ("<td width='10%' style='border: 1px solid black; font-family: georgia, palatino; font-size: 20px;text-align:center;'>Sr No.</td>");
                        }
                        if (eids.contains("1")) {
                            if(label8.equals("") || label8 == null)
                                eduvalue += ("<td width='15%' style='border: 1px solid black; font-family: georgia, palatino; font-size: 20px;text-align:center;'>Kind</td>");
                            else
                                eduvalue += ("<td width='15%' style='border: 1px solid black; font-family: georgia, palatino; font-size: 20px;text-align:center;'>"+label8+"</td>");
                        }
                        if (eids.contains("2")) {
                            if(label9.equals("") || label9 == null)
                                eduvalue += ("<td width='15%' style='border: 1px solid black; font-family: georgia, palatino; font-size: 20px;text-align:center;'>Degree</td>");
                            else
                                eduvalue += ("<td width='15%' style='border: 1px solid black; font-family: georgia, palatino; font-size: 20px;text-align:center;'>"+label9+"</td>");
                        }
                        if (eids.contains("3")) {
                            if(label10.equals("") || label10 == null)
                                eduvalue += ("<td width='15%' style='border: 1px solid black; font-family: georgia, palatino; font-size: 20px;text-align:center;'>Institution, Location</td>");
                            else
                                eduvalue += ("<td width='15%' style='border: 1px solid black; font-family: georgia, palatino; font-size: 20px;text-align:center;'>"+label10+"</td>");
                        }
                        if (eids.contains("4")) {
                            if(label11.equals("") || label11 == null)
                                eduvalue += ("<td width='15%' style='border: 1px solid black; font-family: georgia, palatino; font-size: 20px;text-align:center;'>Field of Study</td>");
                            else
                                eduvalue += ("<td width='15%' style='border: 1px solid black; font-family: georgia, palatino; font-size: 20px;text-align:center;'>"+label11+"</td>");
                        }
                        if (eids.contains("5")) {
                            if(label12.equals("") || label12 == null)
                                eduvalue += ("<td width='15%' style='border: 1px solid black; font-family: georgia, palatino; font-size: 20px;text-align:center;'>Start Date</td>");
                            else
                                eduvalue += ("<td width='15%' style='border: 1px solid black; font-family: georgia, palatino; font-size: 20px;text-align:center;'>"+label12+"</td>");
                        }
                        if (eids.contains("6")) {
                            if(label13.equals("") || label13 == null)
                                eduvalue += ("<td width='15%' style='border: 1px solid black; font-family: georgia, palatino; font-size: 20px;text-align:center;'>End Date</td>");
                            else
                                eduvalue += ("<td width='15%' style='border: 1px solid black; font-family: georgia, palatino; font-size: 20px;text-align:center;'>"+label13+"</td>");
                        }
                        eduvalue += ("</tr>");
                        eduvalue += edstr;
                        eduvalue += "</table>";
                        eduvalue += "</td>";
                        eduvalue += "</tr>";
                    }
                    else{
                        eduvalue += edstr;
                    }
                }

                pstmtcertinner.setInt(1, candidateId);
                //print(this,"Certificate_Deatils :: " + pstmtcertinner.toString());
                ResultSet certrs = pstmtcertinner.executeQuery();
                StringBuilder cert = new StringBuilder();
                cc = 0;
                String coursename, coursetype, approvedby, dateofissue,
                        expirydate, countryname, certValidity;
                int datediff;
                while (certrs.next()) {
                    cc++;
                    coursename = certrs.getString(1) != null ? certrs.getString(1) : "";
                    coursetype = certrs.getString(2) != null ? certrs.getString(2) : "";
                    educinst = certrs.getString(3) != null ? certrs.getString(3) : "";
                    locationofinstit = certrs.getString(4) != null ? certrs.getString(4) : "";
                    approvedby = certrs.getString(5) != null ? certrs.getString(5) : "";
                    dateofissue = certrs.getString(6) != null ? certrs.getString(6) : "";
                    expirydate = certrs.getString(7) != null ? certrs.getString(7) : "";
                    countryname = certrs.getString(8) != null ? certrs.getString(8) : "";
                    datediff = certrs.getInt(9);
                    certValidity = getYMD(datediff);
                    if (!locationofinstit.equals("")) {
                        if (!countryname.equals("")) {
                            locationofinstit += ", (" + countryname + ")";
                        }
                    }
                    if(certtype == 1)
                    {
                        cert.append("<tr style='text-align:center;'>");

                        if (tids.contains("")) {
                            cert.append("<td style='border: 1px solid black; font-family: georgia, palatino; font-size: 20px;text-align:center;'>" + cc + "</td>");
                        }
                        if (tids.contains("1")) {
                            cert.append("<td style='border: 1px solid black; font-family: georgia, palatino; font-size: 20px;text-align:center;'>" + coursename + "</td>");
                        }
                        if (tids.contains("2")) {
                            cert.append("<td style='border: 1px solid black; font-family: georgia, palatino; font-size: 20px;text-align:center;'>" + coursetype + "</td>");
                        }
                        if (tids.contains("3")) {
                            cert.append("<td style='border: 1px solid black; font-family: georgia, palatino; font-size: 20px;text-align:center;'>" + educinst + locationofinstit + "</td>");
                        }
                        if (tids.contains("4")) {
                            cert.append("<td style='border: 1px solid black;text-align:center;'>" + approvedby + "</td>");
                        }
                        if (tids.contains("5")) {
                            cert.append("<td style='border: 1px solid black; font-family: georgia, palatino; font-size: 20px;text-align:center;'>" + (dateofissue != null && !dateofissue.equals("00/00/0000") ? dateofissue : "-") + "</td>");
                        }
                        if (tids.contains("6")) {
                            cert.append("<td style='border: 1px solid black; font-family: georgia, palatino; font-size: 20px;text-align:center;'>" + (expirydate != null && !expirydate.equals("00/00/0000") ? expirydate : "-") + "</td>");
                        }
                        if (tids.contains("7")) {
                            cert.append("<td style='border: 1px solid black; font-family: georgia, palatino; font-size: 20px;text-align:center;'>" + (datediff > 0 ? certValidity : "Expired") + "</td>");
                        }
                        cert.append("</tr>");
                    }else
                    {
                        cert.append("<tr>");
                        cert.append("<td>");
//                        cert.append("<b style='text-align:left;'>Trainings</b>");
                        cert.append("<table style='padding-left:25px;' width='100%'>");
                        cert.append("<tr>");//<img src='" + view_path + photo + "' style = 'weight :150px; height : 150px'/>
                        cert.append("<td style='font-size: 16pt;'><img src='"+img_path+"dot.png' style='width: 6px; margin-right:10px; position:relative; top:-2px;'>"+coursename+"</td>");
                        cert.append("<td align='right' style='font-size: 16pt;'>Valid until  -" + expirydate + "</td>");
                        cert.append("</tr>");
                        cert.append("</table>");
                        cert.append("</td>");
                        cert.append("</tr>");
                    }
                }
                certrs.close();

                String certstr = cert.toString();
                cert.setLength(0);
                String certvalue = "";
                if (!certstr.equals("")) 
                {
                    if(certtype == 1)
                    {
                        certvalue += "<tr style='text-align:center;'><td style='height:10px;'></td></tr>";
                        certvalue += "<tr style='text-align:center;'><td style='text-decoration:underline;text-align:center;font-weight:bold; font-family: georgia, palatino; font-size: 20px;'></td></tr>";
                        certvalue += "<tr style='text-align:center;'>";
                        certvalue += "<td style='text-align:center;' colspan='2'>";
                        certvalue += "<table cellspacing='5' cellpadding='5' align='center' style='width:100%;border-collapse: collapse;'>";
                        certvalue += ("<tr style='background-color: #b0d8e6'>");
                        if (tids.contains("")) {
                            certvalue += ("<td width='10%' style='border: 1px solid black; font-family: georgia, palatino; font-size: 20px;text-align:center;'>Sr No.</td>");
                        }
                        if (tids.contains("1")) {
                            if(label23.equals("") || label23 == null)
                                certvalue += ("<td width='15%' style='border: 1px solid black; font-family: georgia, palatino; font-size: 20px;text-align:center;'>Name of Certificate </td>");
                            else
                                certvalue += ("<td width='15%' style='border: 1px solid black; font-family: georgia, palatino; font-size: 20px;text-align:center;'>"+label23+"</td>");
                        }
                        if (tids.contains("2")) {
                            if(label24.equals("") || label24 == null)
                                certvalue += ("<td width='15%' style='border: 1px solid black; font-family: georgia, palatino; font-size: 20px;text-align:center;'>Type</td>");
                            else
                                certvalue += ("<td width='15%' style='border: 1px solid black; font-family: georgia, palatino; font-size: 20px;text-align:center;'>"+label24+"</td>");
                        }
                        if (tids.contains("3")) {
                            if(label25.equals("") || label25 == null)
                                certvalue += ("<td width='15%' style='border: 1px solid black; font-family: georgia, palatino; font-size: 20px;text-align:center;'>Place of Issue</td>");
                            else
                                certvalue += ("<td width='15%' style='border: 1px solid black; font-family: georgia, palatino; font-size: 20px;text-align:center;'>"+label25+"</td>");
                        }
                        if (tids.contains("4")) {
                            if(label26.equals("") || label26 == null)
                                certvalue += ("<td width='15%' style='border: 1px solid black; font-family: georgia, palatino; font-size: 20px;text-align:center;'>Approved By</td>");
                            else
                                certvalue += ("<td width='15%' style='border: 1px solid black; font-family: georgia, palatino; font-size: 20px;text-align:center;'>"+label26+"</td>");
                        }
                        if (tids.contains("5")) {
                            if(label20.equals("") || label20 == null)
                                certvalue += ("<td width='10%' style='border: 1px solid black; font-family: georgia, palatino; font-size: 20px;text-align:center;'>Date of Issue</td>");
                            else
                                certvalue += ("<td width='10%' style='border: 1px solid black; font-family: georgia, palatino; font-size: 20px;text-align:center;'>"+label20+"</td>");
                        }
                        if (tids.contains("6")) {
                            if(label21.equals("") || label21 == null)
                                certvalue += ("<td width='10%' style='border: 1px solid black; font-family: georgia, palatino; font-size: 20px;text-align:center;'>Expiry Date</td>");
                            else
                                certvalue += ("<td width='10%' style='border: 1px solid black; font-family: georgia, palatino; font-size: 20px;text-align:center;'>"+label21+"</td>");
                        }
                        if (tids.contains("7")) {
                            if(label22.equals("") || label22 == null)
                                certvalue += ("<td width='10%' style='border: 1px solid black; font-family: georgia, palatino; font-size: 20px;text-align:center;'>Validity</td>");
                            else
                                certvalue += ("<td width='10%' style='border: 1px solid black; font-family: georgia, palatino; font-size: 20px;text-align:center;'>"+label22+"</td>");
                        }
                        certvalue += ("</tr>");
                        certvalue += certstr;
                        certvalue += "</table>";
                        certvalue += "</td>";
                        certvalue += "</tr>";
                    }else{
                        certvalue += certstr;
                    }
                }

                HashMap hashmap = new HashMap();

                pstmtdocinner.setInt(1, candidateId);
                //print(this, "Document_Details :: " + pstmtdocinner.toString());
                ResultSet rsdoc = pstmtdocinner.executeQuery();
                StringBuilder govdoc = new StringBuilder();
                String documentname, documentno, placeofissue, issuedby, dateofexpiry;
                int doctype;
                cc = 0;
                while (rsdoc.next()) {
                    cc++;
                    documentname = rsdoc.getString(1) != null ? rsdoc.getString(1) : "";
                    documentno = decipher(rsdoc.getString(2) != null ? rsdoc.getString(2) : "");
                    placeofissue = rsdoc.getString(3) != null ? rsdoc.getString(3) : "";
                    issuedby = rsdoc.getString(4) != null ? rsdoc.getString(4) : "";
                    dateofissue = rsdoc.getString(5) != null ? rsdoc.getString(5) : "";
                    dateofexpiry = rsdoc.getString(6) != null ? rsdoc.getString(6) : "";
                    countryname = rsdoc.getString(7) != null ? rsdoc.getString(7) : "";
                    doctype = rsdoc.getInt(8);
                    if (!countryname.equals("")) {
                        placeofissue += "(" + countryname + ")";
                    }
                    govdoc.append("<tr style='font-family: georgia, palatino; font-size: 20px;text-align:center;'>");
                    if (dids.contains("")) {
                        govdoc.append("<td style='border: 1px solid black; font-family: georgia, palatino; font-size: 20px;text-align:center;'>" + cc + "</td>");
                    }
                    if (dids.contains("1")) {
                        govdoc.append("<td style='border: 1px solid black; font-family: georgia, palatino; font-size: 20px;text-align:center;'>" + documentname + "</td>");
                    }
                    if (dids.contains("2")) {
                        govdoc.append("<td style='border: 1px solid black; font-family: georgia, palatino; font-size: 20px;text-align:center;'>" + documentno + "</td>");
                    }
                    if (dids.contains("3")) {
                        govdoc.append("<td style='border: 1px solid black; font-family: georgia, palatino; font-size: 20px;text-align:center;'>" + placeofissue + "</td>");
                    }
                    if (dids.contains("4")) {
                        govdoc.append("<td style='border: 1px solid black; font-family: georgia, palatino; font-size: 20px;text-align:center;'>" + issuedby + "</td>");
                    }
                    if (dids.contains("5")) {
                        govdoc.append("<td style='border: 1px solid black; font-family: georgia, palatino; font-size: 20px;text-align:center;'>" + dateofissue + "</td>");
                    }
                    if (dids.contains("6")) {
                        govdoc.append("<td style='border: 1px solid black; font-family: georgia, palatino; font-size: 20px;text-align:center;'>" + dateofexpiry + "</td>");
                    }
                    govdoc.append("</tr>");

                    if (doctype == 1) {
                        hashmap.put("AADHARNUMBER", documentno);
                        hashmap.put("AADHARISSUEDATE", dateofissue);
                    }
                    if (doctype == 3) {
                        hashmap.put("BIRTHCERTIFICATENUMBER", documentno);
                        hashmap.put("BIRTHCERTIFICATEISSUEDATE", dateofissue);
                    }
                    if (doctype == 4) {
                        hashmap.put("MEDICALCERTIFICATENUMBER", documentno);
                        hashmap.put("MEDICALCERTIFICATEISSUEDATE", dateofissue);
                        hashmap.put("MEDICALCERTIFICATEEXPIRYDATE", dateofexpiry);
                    }
                    if (doctype == 2) {
                        hashmap.put("PASSPORTNUMBER", documentno);
                        hashmap.put("PASSPORTISSUEDATE", dateofissue);
                        hashmap.put("PASSPORTEXPIRYDATE", dateofexpiry);
                    }
                    if (doctype == 5) {
                        hashmap.put("PANCARDNUMBER", documentno);
                        hashmap.put("PANCARDISSUEDATE", dateofissue);
                    }
                    if (doctype == 6) {
                        hashmap.put("PCCNUMBER", documentno);
                        hashmap.put("PCCISSUEDATE", dateofissue);
                        hashmap.put("PCCEXPIRYDATE", dateofexpiry);
                    }
                    if (doctype == 8) {
                        hashmap.put("ONGCNEDPASSNUMBER", documentno);
                        hashmap.put("ONGCNEDPASSNUMBERISSUEDATE", dateofissue);
                        hashmap.put("ONGCNEDPASSNUMBEREXPIRYDATE", dateofexpiry);
                    }
                    if (doctype == 9) {
                        hashmap.put("CDCNUMBER", documentno);
                        hashmap.put("CDCISSUEDATE", dateofissue);
                        hashmap.put("CDCEXPIRYDATE", dateofexpiry);
                    }
                }
                rsdoc.close();

                String docstr = govdoc.toString();
                govdoc.setLength(0);
                String docvalue = "";
                if (!docstr.equals("")) {
                    docvalue += "<tr style='text-align:center;'><td style='height:10px;'></td></tr>";
                    docvalue += "<tr style='text-align:center;'><td style='text-decoration:underline;text-align:center;font-weight:bold; font-family: georgia, palatino; font-size: 20px;'></td></tr>";
                    docvalue += "<tr style='text-align:center;'>";
                    docvalue += "<td style='text-align:center;' colspan='2'>";
                    docvalue += "<table cellspacing='5' cellpadding='5' align='center' style='width:100%;border-collapse: collapse;'>";
                    docvalue += ("<tr style='background-color: #b0d8e6'>");
                    if (dids.contains("")) {
                        docvalue += ("<td width='10%' style='border: 1px solid black; font-family: georgia, palatino; font-size: 20px;text-align:center;'>Sr No.</td>");
                    }
                    if (dids.contains("1")) {
                        if(label14.equals("") || label14 == null)
                            docvalue += ("<td width='15%' style='border: 1px solid black; font-family: georgia, palatino; font-size: 20px;text-align:center;'>Document Name</td>");
                        else    
                            docvalue += ("<td width='15%' style='border: 1px solid black; font-family: georgia, palatino; font-size: 20px;text-align:center;'>"+label14+"</td>");
                    }
                    if (dids.contains("2")) {
                        if(label15.equals("") || label15 == null)
                            docvalue += ("<td width='15%' style='border: 1px solid black; font-family: georgia, palatino; font-size: 20px;text-align:center;'>Number</td>");
                        else
                            docvalue += ("<td width='15%' style='border: 1px solid black; font-family: georgia, palatino; font-size: 20px;text-align:center;'>"+label15+"</td>");
                    }
                    if (dids.contains("3")) {
                        if(label16.equals("") || label16 == null)
                            docvalue += ("<td width='15%' style='border: 1px solid black; font-family: georgia, palatino; font-size: 20px;text-align:center;'>Place of Issue</td>");
                        else
                            docvalue += ("<td width='15%' style='border: 1px solid black; font-family: georgia, palatino; font-size: 20px;text-align:center;'>"+label16+"</td>");
                    }
                    if (dids.contains("4")) {
                        if(label17.equals("") || label17 == null)
                            docvalue += ("<td width='15%' style='border: 1px solid black; font-family: georgia, palatino; font-size: 20px;text-align:center;'>Issued By</td>");
                        else
                            docvalue += ("<td width='15%' style='border: 1px solid black; font-family: georgia, palatino; font-size: 20px;text-align:center;'>"+label17+"</td>");
                    }
                    if (dids.contains("5")) {
                        if(label18.equals("") || label18 == null)
                            docvalue += ("<td width='15%' style='border: 1px solid black; font-family: georgia, palatino; font-size: 20px;text-align:center;'>Issue Date</td>");
                        else
                            docvalue += ("<td width='15%' style='border: 1px solid black; font-family: georgia, palatino; font-size: 20px;text-align:center;'>"+label18+"</td>");
                    }
                    if (dids.contains("6")) {
                        if(label19.equals("") || label19 == null)
                            docvalue += ("<td width='15%' style='border: 1px solid black; font-family: georgia, palatino; font-size: 20px;text-align:center;'>Expiry Date</td>");
                        else
                            docvalue += ("<td width='15%' style='border: 1px solid black; font-family: georgia, palatino; font-size: 20px;text-align:center;'>"+label19+"</td>");
                    }
                    docvalue += ("</tr>");
                    docvalue += docstr;
                    docvalue += "</table>";
                    docvalue += "</td>";
                    docvalue += "</tr>";
                }

                double totalrate = dayrate + dayrate * variablepay / 100;

                Template template = new Template(templatepath + templatename);
                hashmap.put("NAME", name);
                hashmap.put("DOB", dob);
                hashmap.put("EMAIL", email);
                hashmap.put("PLACEOFBIRTH", birthplace);
                hashmap.put("CONTACT1", contact1);
                hashmap.put("CONTACT2", contact2);
                hashmap.put("CONATCT3", contact3);
                hashmap.put("GENDER", gender);
                hashmap.put("CITY", city);
                hashmap.put("COUNTRYNAME", countryName);
                hashmap.put("NATIONALITY", nationality);
                hashmap.put("ADDRESS1LINE1", address1line1);
                hashmap.put("ADDRESS1LINE2", address1line2);
                hashmap.put("ADDRESS2LINE1", address2line1);
                hashmap.put("ADDRESS2LINE2", address2line2);
                hashmap.put("ADDRESS2LINE3", address2line3);
                hashmap.put("NEXTOFKIN", nextofkin);
                hashmap.put("RELATION", relation);
                hashmap.put("ECONTACT1", econtact1);
                hashmap.put("ECONTACT2", econtact2);
                hashmap.put("MARITALSTATUS", maritialstatus);
                if (!photo.equals("")) {
                    hashmap.put("PHOTO", "<img src='" + view_path + photo + "' style = 'weight :150px; height : 150px'/>");
                }
                hashmap.put("ADDRESS1LINE3", address1line3);
                hashmap.put("EXPERIENCEDEPT", experiencedept);
                hashmap.put("EXPECTEDSALARY", expectedsalary);
                hashmap.put("CURRENCY", currency);
                hashmap.put("PRIMARYPOSITIONNAME", positionName);
                hashmap.put("PRIMARYPOSITIONRATE", rate1);
                hashmap.put("PRIMARYPOSITIONOVERTIMERATE", rate2);
                hashmap.put("SECONDARYPOSITIONNAME", position2);
                hashmap.put("SECONDARYPOSITIONRATE", p2rate1);
                hashmap.put("SECONDARYPOSITIONOVERTIMERATE", p2rate2);
                hashmap.put("FIRSTNAME", firstname);
                hashmap.put("MIDDLENAME", middlename);
                hashmap.put("LASTNAME", lastname);
                hashmap.put("BLOODGROUP", bloodgroup);
                hashmap.put("PROFESSIONALPROFILE", pstr);
                hashmap.put("STRENGTHSANDSKILLS", s1str);
                hashmap.put("ADDITIONALSKILLS", s2str);
                hashmap.put("WORKEXPERIENCE", wevalue);
                hashmap.put("ROLESANDRESPONSIBILITIES", roles);
                hashmap.put("EDUCATION", eduvalue);
                hashmap.put("GOVDOC", docvalue);
                hashmap.put("CUSTOMVALUE1", (cval1s != null && !cval1s.equals("") ? cval1s : "Custom Value 1"));
                hashmap.put("CUSTOMVALUE2", (cval2s != null && !cval2s.equals("") ? cval2s : "Custom Value 2"));
                hashmap.put("CUSTOMVALUE3", (cval3s != null && !cval3s.equals("") ? cval3s : "Custom Value 3"));
                hashmap.put("CUSTOMVALUE4", (cval4s != null && !cval4s.equals("") ? cval4s : "Custom Value 4"));
                hashmap.put("CUSTOMVALUE5", (cval5s != null && !cval5s.equals("") ? cval5s : "Custom Value 5"));
                hashmap.put("CUSTOMVALUE6", (cval6s != null && !cval6s.equals("") ? cval6s : "Custom Value 6"));
                hashmap.put("CUSTOMVALUE7", (cval7s != null && !cval7s.equals("") ? cval7s : "Custom Value 7"));
                hashmap.put("CUSTOMVALUE8", (cval8s != null && !cval8s.equals("") ? cval8s : "Custom Value 8"));
                hashmap.put("CUSTOMVALUE9", (cval9s != null && !cval9s.equals("") ? cval9s : "Custom Value 9"));
                hashmap.put("CUSTOMVALUE10", (cval10s != null && !cval10s.equals("") ? cval10s : "Custom Value 10"));
                hashmap.put("CERTIFICATIONS", certvalue);
                hashmap.put("HIGHESTQUALIFICATION", highestq);
                hashmap.put("CONTRACTOR", contractor);
                hashmap.put("CALLOFFORDER", edate);
                hashmap.put("VALIDFROM", validfrom);
                hashmap.put("VALIDTO", validto);
                hashmap.put("MANAGERNAME", dmname);
                hashmap.put("DESIGNATION", designation);
                hashmap.put("COMMENTS", comments);
                hashmap.put("COMPANY", clientname);
                hashmap.put("DAYRATE", dayrate);
                hashmap.put("DAYRATECURRENCY", currencyrate);
                hashmap.put("ASSETNAME", jobasset);
                hashmap.put("TOTALRATE", totalrate);
                hashmap.put("VPE", variablepay);
                hashmap.put("CURRENTDATE", currDate01());
                hashmap.put("CURRENTDATETIME", currDate02());
                hashmap.put("ASSETTYPE", assettype);
                hashmap.put("LANGUAGEPROFICIENCY", proficiency);
                hashmap.put("LANGUAGELIST", langvalue);
                hashmap.put("AGE", age);
                hashmap.put("TOTALWORKEXPERIENCE", totalDays);
                hashmap.put("TOTALPRIMARYPOSITIONWORKEXPERIENCE", p1DateDiff);
                hashmap.put("TOTALROTATIONEXPERIENCE", totalRotationDays);
                hashmap.put("PRIMARYPOSITIONEXPERIENCE", p1RotationDays);
                hashmap.put("SECONDARYPOSITIONEXPERIENCE", p2RotationDays);
                hashmap.put("PRIMARYSECONDARYPOSITIONEXPERIENCE", primarySecondaryDays);
                if (vstatus > 0) {
                    hashmap.put("AADHARVERIFICATION", "Yes");
                } else {
                    hashmap.put("AADHARVERIFICATION", "No");
                }
                if (doctype1 == 2 && dateDiff1 > 0) {
                    hashmap.put("PASSPORTAVAILABILITY", "Yes");
                    hashmap.put("PASSPORTVALIDITY", dateDiff1);
                } else {
                    hashmap.put("PASSPORTAVAILABILITY", "No");
                    hashmap.put("PASSPORTVALIDITY", "-");
                }
                if (doctype2 == 6 && dateDiff2 > 0) {
                    hashmap.put("PCCAVAILABILITY", "Yes");
                    hashmap.put("PCCVALIDITY", dateDiff2);
                } else {
                    hashmap.put("PCCAVAILABILITY", "No");
                    hashmap.put("PCCVALIDITY", "-");
                }
                if (coursenameId1 == 6) {
                    hashmap.put("BOSIETAVAILABILITY", "Yes");
                    hashmap.put("BOSIETVALIDITY", cdateValue1);
                } else {
                    hashmap.put("BOSIETAVAILABILITY", "No");
                    hashmap.put("BOSIETVALIDITY", "-");
                }
                if (!bcoursenameId2)
                {
                    if (coursenameId2 == 11)
                    {
                        bcoursenameId2 = true;
                        hashmap.put("H2SAVAILABILITY", "Yes");
                        hashmap.put("H2SVALIDITY", cdateValue2);
                    } else if (coursenameId2 == 176) 
                    {
                        hashmap.put("H2SAVAILABILITY", "Yes");
                        hashmap.put("H2SVALIDITY", cdateValue2);
                    } else {
                        hashmap.put("H2SAVAILABILITY", "No");
                        hashmap.put("H2SVALIDITY", "-");
                    }
                }
                
                String content = template.patch(hashmap);
                StringBuilder sb2 = new StringBuilder();
                sb2.append("<html>");
                sb2.append("<head>");
                sb2.append("<style type='text/css'>");
                sb2.append("table {page-break-inside: avoid}");
                sb2.append("tr {page-break-inside: avoid;}");
                sb2.append("td {font-size:14px;line-height:28px}");
                sb2.append("img {page-break-inside: avoid}");
                sb2.append("</style>");
                sb2.append("</head>");
                sb2.append("<body>" + content + "</body>");
                sb2.append("</html>");
                String st1 = sb2.toString();
                sb2.setLength(0);
                st1 = content.replaceAll("<strong", "<b style='text-weight: bold' ").replaceAll("</strong>", "</b>");

                hashmap.clear();
                String filePath = getMainPath("add_resumetemplate_pdf");
                String viewPath = getMainPath("view_resumetemplate_pdf");
                java.util.Date now = new java.util.Date();
                String fname = String.valueOf(now.getTime()) + ".pdf";
                String htmlFolderName = dateFolder();
                File dir = new File(filePath + htmlFolderName);
                if (!dir.exists()) {
                    dir.mkdir();
                }
                pdffilename = htmlFolderName + "/" + fname;
                File pdfFile = new File(filePath + htmlFolderName + "/" + fname);
                int pdfwidth = 1050;
                if (watermarkurl != null && !watermarkurl.equals("")) {
                    watermarkurl = viewPath + watermarkurl;
                }
                try {
                    generatePDFString(st1, pdfFile, PD4Constants.A4, "", headerBody, footerBody, watermarkurl, pdfwidth);
                    String st2 = headerBody + st1 + footerBody;
                    writeStringToFile(st2, filePath + htmlFolderName + "/" + fname.replace(".pdf", ".doc"));
                    pdfFile = null;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            rs.close();
            pstmt.close();
            pstmtinner.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return pdffilename;
    }

    private static void writeStringToFile(String content, String filePath) {
        try (FileWriter fileWriter = new FileWriter(filePath)) {
            fileWriter.write(content);
        } catch (IOException e) {
            e.printStackTrace(System.out);
        }
    }

    public int updateShortlistPdffile(int shortlistId, String pdffilename, int uId) {
        int cc = 0;
        try {
            String checkquery = "SELECT s_pdffilename, i_sflag from t_shortlist WHERE i_shortlistid = ? ";
            conn = getConnection();
            pstmt = conn.prepareStatement(checkquery);
            pstmt.setInt(1, shortlistId);
            rs = pstmt.executeQuery();
            String oldfile = "";
            int sflag = 0;
            while (rs.next()) {
                oldfile = rs.getString(1) != null ? rs.getString(1) : "";
                sflag = rs.getInt(2);
            }
            rs.close();
            pstmt.close();

            StringBuilder sb = new StringBuilder();
            sb.append("UPDATE t_shortlist SET  ");
            sb.append(" s_pdffilename = ?, ");
            if (sflag < 3) {
                sb.append(" i_sflag = ?, ");
            }
            sb.append(" i_userid = ?, ");
            sb.append(" ts_moddate = ?  ");
            sb.append("WHERE i_shortlistid = ? ");
            String query = (sb.toString()).intern();
            sb.setLength(0);
            pstmt = conn.prepareStatement(query);
            int scc = 0;
            pstmt.setString(++scc, pdffilename);
            if (sflag < 3) {
                pstmt.setInt(++scc, 2);
            }
            pstmt.setInt(++scc, uId);
            pstmt.setString(++scc, currDate1());
            pstmt.setInt(++scc, shortlistId);
            print(this, "updateShortlistPdffile :: " + pstmt.toString());
            cc = pstmt.executeUpdate();

        } catch (Exception exception) {
            print(this, "updateShortlistPdffile :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return cc;
    }

    public int updatePdffiletalentpool(int candidateId, String pdffilename, int uId) {
        int cc = 0;
        try {
            String checkquery = "SELECT s_pdffilename FROM t_candidate WHERE i_candidateid =? ";
            conn = getConnection();
            pstmt = conn.prepareStatement(checkquery);
            pstmt.setInt(1, candidateId);
            rs = pstmt.executeQuery();
            String oldfile = "", oldfile2 = "";
            while (rs.next()) {
                oldfile = rs.getString(1) != null ? rs.getString(1) : "";
                oldfile2 = oldfile.replace(".pdf", ".doc");
            }
            rs.close();
            pstmt.close();

            if (oldfile != null && !oldfile.equals("")) {
                File file = new File(getMainPath("add_resumetemplate_pdf") + oldfile);
                if (file.exists()) {
                    file.delete();
                }
            }
            if (oldfile2 != null && !oldfile2.equals("")) {
                File file = new File(getMainPath("add_resumetemplate_pdf") + oldfile2);
                if (file.exists()) {
                    file.delete();
                }
            }
            String query = "UPDATE t_candidate SET s_pdffilename = ?, i_userid = ?, ts_moddate = ? WHERE i_candidateid = ? ";
            pstmt = conn.prepareStatement(query);
            int scc = 0;
            pstmt.setString(++scc, pdffilename);
            pstmt.setInt(++scc, uId);
            pstmt.setString(++scc, currDate1());
            pstmt.setInt(++scc, candidateId);
            print(this, "updatePdffiletalentpool :: " + pstmt.toString());
            cc = pstmt.executeUpdate();

        } catch (Exception exception) {
            print(this, "updatePdffiletalentpool :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return cc;
    }

    public int updateShortlistofferPdffile(int shortlistId, String offerpdffilename, int uId, String fromDate, String toDate) {
        int cc = 0;
        try {
            String checkquery = "SELECT s_offerpdffilename, i_oflag from t_shortlist WHERE i_shortlistid = ? ";
            conn = getConnection();
            pstmt = conn.prepareStatement(checkquery);
            pstmt.setInt(1, shortlistId);
            rs = pstmt.executeQuery();
            String oldfile = "";
            int sflag = 0;
            while (rs.next()) {
                oldfile = rs.getString(1) != null ? rs.getString(1) : "";
                sflag = rs.getInt(2);
            }
            rs.close();
            pstmt.close();

            StringBuilder sb = new StringBuilder();
            sb.append("UPDATE t_shortlist SET  ");
            sb.append(" s_offerpdffilename = ?, ");
            if (sflag < 3) {
                sb.append(" i_oflag = ?, ");
            }
            sb.append(" i_userid = ?, ");
            sb.append(" d_fromdate = ?, ");
            sb.append(" d_todate = ?, ");
            sb.append(" ts_moddate = ?  ");
            sb.append("WHERE i_shortlistid = ? ");
            String query = (sb.toString()).intern();
            sb.setLength(0);
            pstmt = conn.prepareStatement(query);
            int scc = 0;
            pstmt.setString(++scc, offerpdffilename);
            if (sflag < 3) {
                pstmt.setInt(++scc, 2);
            }
            pstmt.setInt(++scc, uId);
            pstmt.setString(++scc, changeDate1(fromDate));
            pstmt.setString(++scc, changeDate1(toDate));
            pstmt.setString(++scc, currDate1());
            pstmt.setInt(++scc, shortlistId);
            print(this, "updateShortlistPdffile :: " + pstmt.toString());
            cc = pstmt.executeUpdate();

        } catch (Exception exception) {
            print(this, "updateShortlistPdffile :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return cc;
    }

    public String getshortlistpdf(int shortlistId) {
        String pdffilename = "";
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT t_shortlist.s_pdffilename FROM t_shortlist WHERE t_shortlist.i_shortlistid =? AND t_shortlist.i_status >1 ");
        String query = sb.toString().intern();
        sb.setLength(0);
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, shortlistId);
            print(this, "getshortlistpdf :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                pdffilename = rs.getString(1) != null ? rs.getString(1) : "";
            }
        } catch (Exception exception) {
            print(this, "getshortlistpdf :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return pdffilename;
    }

    public String getshortlistofferpdf(int shortlistId) {

        String offerpdffilename = "";
        StringBuilder sb = new StringBuilder();
        sb.append(" SELECT  t_shortlist.s_offerpdffilename FROM t_shortlist  ");
        sb.append(" WHERE t_shortlist.i_shortlistid = ? AND t_shortlist.i_status > 1 ");
        String query = sb.toString().intern();
        sb.setLength(0);
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, shortlistId);
            print(this, "getshortlistofferpdf :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                offerpdffilename = rs.getString(1) != null ? rs.getString(1) : "";
            }
        } catch (Exception exception) {
            print(this, "getshortlistofferpdf :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return offerpdffilename;
    }

    public ClientselectionInfo setEmailDetailoffer(int shortlistId, int sflag, int oflag) {
        ClientselectionInfo info = null;
        StringBuilder sb = new StringBuilder();
        try {
            if (oflag < 3) {
                sb.append("SELECT t_shortlist.i_candidateid, t_client.s_name, t_client.s_email, t4.emails, t_shortlist.s_offerpdffilename, ");
                sb.append("CONCAT_WS(' ', t_candidate.s_firstname, t_candidate.s_lastname), t_jobpost.i_jobpostid, t_position.s_name, t_grade.s_name, ");
                sb.append("DATE_FORMAT(t_shortlist.ts_regdate,'%d-%b-%Y'), t_candidate.s_email, DATE_FORMAT(t_shortlist.d_fromdate,'%d-%b-%Y'), ");
                sb.append("DATE_FORMAT(t_shortlist.d_todate,'%d-%b-%Y'), t_jobpost.i_clientid, t_jobpost.i_clientassetid, t6.emails ");
                sb.append("FROM t_shortlist ");
                sb.append("LEFT JOIN t_jobpost on (t_jobpost.i_jobpostid = t_shortlist.i_jobpostid) ");
                sb.append("LEFT JOIN t_position on (t_jobpost.i_positionid = t_position.i_positionid) ");
                sb.append("LEFT JOIN t_grade on (t_jobpost.i_gradeid = t_grade.i_gradeid) ");
                sb.append("LEFT JOIN t_candidate on (t_candidate.i_candidateid = t_shortlist.i_candidateid) ");
                sb.append("LEFT JOIN t_client on (t_client.i_clientid = t_jobpost.i_clientid) ");
                sb.append("LEFT JOIN (SELECT t_client.i_clientid, GROUP_CONCAT(DISTINCT t3.s_email order by t3.s_email SEPARATOR ', ') as emails from t_client ");
                sb.append("LEFT JOIN t_userlogin as t3 on find_in_set(t3.i_userid, t_client.s_ocsuserids) group by t_client.i_clientid) as t4 on (t4.i_clientid = t_client.i_clientid) ");
                sb.append("LEFT JOIN (SELECT t_client.i_clientid, GROUP_CONCAT(DISTINCT t5.s_email order by t5.s_email SEPARATOR ', ') as emails from t_client "); 
                sb.append("LEFT JOIN t_userlogin as t5 on find_in_set(t5.i_userid, t_client.s_rids) group by t_client.i_clientid) as t6 on (t6.i_clientid = t_client.i_clientid) "); 
                sb.append("WHERE t_shortlist.i_shortlistid = ? ");
                String query = sb.toString().intern();
                sb.setLength(0);
                conn = getConnection();
                pstmt = conn.prepareStatement(query);
                pstmt.setInt(1, shortlistId);
                print(this, "setEmailDetailoffer sflag < 3 :: " + pstmt.toString());
                rs = pstmt.executeQuery();
                while (rs.next()) 
                {
                    int candidateId = rs.getInt(1);
                    String clientname = rs.getString(2) != null ? rs.getString(2) : "";
                    String clientmailId = rs.getString(3) != null ? rs.getString(3) : "";
                    String comailId = rs.getString(4) != null ? rs.getString(4) : "";
                    String pdffileName = rs.getString(5) != null ? rs.getString(5) : "";
                    String candidateName = rs.getString(6) != null ? rs.getString(6) : "";
                    int jobpostId = rs.getInt(7);
                    String position = rs.getString(8) != null ? rs.getString(8) : "";
                    String grade = rs.getString(9) != null ? rs.getString(9) : "";
                    String date = rs.getString(10) != null ? rs.getString(10) : "";
                    String candidatemail = rs.getString(11) != null ? rs.getString(11) : "";
                    String fromDate = rs.getString(12) != null ? rs.getString(12) : "";
                    String toDate = rs.getString(13) != null ? rs.getString(13) : "";
                    int clientId = rs.getInt(14);
                    int assetId = rs.getInt(15);
                    String re_email = rs.getString(13) != null ? rs.getString(13) : "";
                    if (!grade.equals("")) {
                        position += " - " + grade;
                    }
                    
                    info = new ClientselectionInfo(shortlistId, candidateId, clientname, clientmailId, "", pdffileName, candidateName.trim(),
                            jobpostId, position, "", "", "", "", date, candidatemail, fromDate, toDate, clientId, assetId, re_email, "");
                }

            } else if (oflag >= 3) {

                sb.append("SELECT s.i_candidateid, m.s_name, m.s_to, m.s_cc, m.s_bcc, m.s_subject, m.s_filename, s.s_offerpdffilename, t_candidate.s_firstname, ");
                sb.append("t_jobpost.i_jobpostid, t_position.s_name, t_grade.s_name, m.s_sendby, DATE_FORMAT(m.ts_regdate,'%d-%b-%Y'), t_candidate.s_email, t6.emails ");
                sb.append("FROM t_maillog m ");
                sb.append("LEFT JOIN t_shortlist as s on (s.i_shortlistid = m.i_shortlistid) ");
                sb.append("LEFT JOIN t_candidate on (t_candidate.i_candidateid = s.i_candidateid) ");
                sb.append("LEFT JOIN t_jobpost on (t_jobpost.i_jobpostid = s.i_jobpostid) ");
                sb.append("LEFT JOIN t_position on (t_jobpost.i_positionid = t_position.i_positionid) ");
                sb.append("LEFT JOIN t_grade on (t_jobpost.i_gradeid = t_grade.i_gradeid) ");
                sb.append("LEFT JOIN t_client on (t_client.i_clientid = t_jobpost.i_clientid) ");                
                sb.append("LEFT JOIN (SELECT t_client.i_clientid, GROUP_CONCAT(DISTINCT t5.s_email order by t5.s_email SEPARATOR ', ') as emails from t_client "); 
                sb.append("LEFT JOIN t_userlogin as t5 on find_in_set(t5.i_userid, t_client.s_rids) group by t_client.i_clientid) as t6 on (t6.i_clientid = t_client.i_clientid) ");
                sb.append("WHERE m.i_shortlistid = ? order by m.ts_regdate desc limit 0,1");

                String query = (sb.toString()).intern();
                sb.setLength(0);
                conn = getConnection();
                pstmt = conn.prepareStatement(query);
                pstmt.setInt(1, shortlistId);
                logger.info("setEmailDetailoffer  sflag >= 3 :: " + pstmt.toString());

                rs = pstmt.executeQuery();

                while (rs.next()) {
                    int candidateId = rs.getInt(1);
                    String clientname = rs.getString(2) != null ? rs.getString(2) : "";
                    String clientmailId = rs.getString(3) != null ? rs.getString(3) : "";
                    String comailId = rs.getString(4) != null ? rs.getString(4) : "";
                    String bccmailId = rs.getString(5) != null ? rs.getString(5) : "";
                    String subject = rs.getString(6) != null ? rs.getString(6) : "";
                    String mailfileName = rs.getString(7) != null ? rs.getString(7) : "";
                    String pdffileName = rs.getString(8) != null ? rs.getString(8) : "";
                    String candidateName = rs.getString(9) != null ? rs.getString(9) : "";
                    int jobpostId = rs.getInt(10);
                    String position = rs.getString(8) != null ? rs.getString(11) : "";
                    String grade = rs.getString(12) != null ? rs.getString(12) : "";
                    String sendby = rs.getString(13) != null ? rs.getString(13) : "";
                    String date = rs.getString(14) != null ? rs.getString(14) : "";
                    String candidatemail = rs.getString(15) != null ? rs.getString(15) : "";
                    String re_email = rs.getString(16) != null ? rs.getString(16) : "";
                    if (!grade.equals("")) {
                        position += " - " + grade;
                    }

                    info = new ClientselectionInfo(shortlistId, candidateId, clientname, clientmailId, comailId, pdffileName, candidateName, jobpostId,
                            position, bccmailId, subject, mailfileName, sendby, date, candidatemail, "", "", 0, 0, re_email, "");
                }
                rs.close();

            }

        } catch (Exception exception) {
            print(this, "setEmailDetailoffer :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return info;
    }

    public ClientselectionInfo setEmailDetail(int shortlistId, int sflag, int maillogId) {
        ClientselectionInfo info = null;
        StringBuilder sb = new StringBuilder();
        try {
            if (sflag < 3) {
                sb.append("SELECT t_shortlist.i_candidateid, t_client.s_name, t_client.s_email, t4.emails, t_shortlist.s_pdffilename, ");
                sb.append("t_candidate.s_firstname, t_jobpost.i_jobpostid, t_position.s_name, t_grade.s_name, DATE_FORMAT(t_shortlist.ts_regdate,'%d-%b-%Y'), ");
                sb.append("t_candidate.s_email, t_jobpost.i_clientid, t_jobpost.i_clientassetid, t6.emails ");
                sb.append("FROM t_shortlist ");
                sb.append("LEFT JOIN t_jobpost on (t_jobpost.i_jobpostid = t_shortlist.i_jobpostid) ");
                sb.append("LEFT JOIN t_position on (t_jobpost.i_positionid = t_position.i_positionid) ");
                sb.append("LEFT JOIN t_grade on (t_jobpost.i_gradeid = t_grade.i_gradeid) ");
                sb.append("LEFT JOIN t_candidate on (t_candidate.i_candidateid = t_shortlist.i_candidateid) ");
                sb.append("LEFT JOIN t_client on (t_client.i_clientid = t_jobpost.i_clientid) ");
                sb.append("LEFT JOIN (SELECT t_client.i_clientid, GROUP_CONCAT(DISTINCT t3.s_email order by t3.s_email SEPARATOR ', ') as emails from t_client ");
                sb.append("LEFT JOIN t_userlogin as t3 on find_in_set(t3.i_userid, t_client.s_ocsuserids) group by t_client.i_clientid) as t4 on (t4.i_clientid = t_client.i_clientid) ");
                sb.append("LEFT JOIN (SELECT t_client.i_clientid, GROUP_CONCAT(DISTINCT t5.s_email order by t5.s_email SEPARATOR ', ') as emails from t_client ");
                sb.append("LEFT JOIN t_userlogin as t5 on find_in_set(t5.i_userid, t_client.s_mids) group by t_client.i_clientid) as t6 on (t6.i_clientid = t_client.i_clientid) ");
                sb.append("WHERE t_shortlist.i_shortlistid = ? ");
                String query = sb.toString().intern();
                sb.setLength(0);
                conn = getConnection();
                pstmt = conn.prepareStatement(query);
                pstmt.setInt(1, shortlistId);
                print(this, "setEmailDetail sflag < 3 :: " + pstmt.toString());
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    int candidateId = rs.getInt(1);
                    String clientname = rs.getString(2) != null ? rs.getString(2) : "";
                    String clientmailId = rs.getString(3) != null ? rs.getString(3) : "";
                    String comailId = rs.getString(4) != null ? rs.getString(4) : "";
                    String pdffileName = rs.getString(5) != null ? rs.getString(5) : "";
                    String candidateName = rs.getString(6) != null ? rs.getString(6) : "";
                    int jobpostId = rs.getInt(7);
                    String position = rs.getString(8) != null ? rs.getString(8) : "";
                    String grade = rs.getString(9) != null ? rs.getString(9) : "";
                    String date = rs.getString(10) != null ? rs.getString(10) : "";
                    String candidatemail = rs.getString(11) != null ? rs.getString(11) : "";
                    int clientId = rs.getInt(12);
                    int assetId = rs.getInt(13);
                    String ma_email = rs.getString(14) != null ? rs.getString(14) : "";

                    if (!grade.equals("")) {
                        position += " - " + grade;
                    }
                    info = new ClientselectionInfo(shortlistId, candidateId, clientname, clientmailId, comailId, pdffileName, candidateName.trim(),
                            jobpostId, position, "", "", "", "", date, candidatemail, "", "", clientId, assetId, "", ma_email);
                }

            } else if (sflag >= 3) {

                sb.append("SELECT s.i_candidateid, m.s_name, m.s_to, m.s_cc, m.s_bcc, m.s_subject, m.s_filename, s.s_pdffilename, t_candidate.s_firstname, ");
                sb.append("t_jobpost.i_jobpostid, t_position.s_name, t_grade.s_name, m.s_sendby, DATE_FORMAT(m.ts_regdate,'%d-%b-%Y'), t_candidate.s_email ");
                sb.append("FROM t_maillog m ");
                sb.append("LEFT JOIN t_shortlist as s on (s.i_shortlistid = m.i_shortlistid) ");
                sb.append("LEFT JOIN t_candidate on (t_candidate.i_candidateid = s.i_candidateid) ");
                sb.append("LEFT JOIN t_jobpost on (t_jobpost.i_jobpostid = s.i_jobpostid) ");
                sb.append("LEFT JOIN t_position on (t_jobpost.i_positionid = t_position.i_positionid) ");
                sb.append("LEFT JOIN t_grade on (t_jobpost.i_gradeid = t_grade.i_gradeid) ");
                sb.append("WHERE m.i_shortlistid =? ");
                if (maillogId > 0) {
                    sb.append("AND m.i_maillogid =? ");
                }
                sb.append("order by m.ts_regdate desc limit 0,1");
                String query = (sb.toString()).intern();
                sb.setLength(0);
                conn = getConnection();
                pstmt = conn.prepareStatement(query);
                pstmt.setInt(1, shortlistId);
                if (maillogId > 0) {
                    pstmt.setInt(2, maillogId);
                }
                logger.info("setEmailDetail  sflag >= 3 :: " + pstmt.toString());

                rs = pstmt.executeQuery();

                while (rs.next()) {
                    int candidateId = rs.getInt(1);
                    String clientname = rs.getString(2) != null ? rs.getString(2) : "";
                    String clientmailId = rs.getString(3) != null ? rs.getString(3) : "";
                    String comailId = rs.getString(4) != null ? rs.getString(4) : "";
                    String bccmailId = rs.getString(5) != null ? rs.getString(5) : "";
                    String subject = rs.getString(6) != null ? rs.getString(6) : "";
                    String mailfileName = rs.getString(7) != null ? rs.getString(7) : "";
                    String pdffileName = rs.getString(8) != null ? rs.getString(8) : "";
                    String candidateName = rs.getString(9) != null ? rs.getString(9) : "";
                    int jobpostId = rs.getInt(10);
                    String position = rs.getString(8) != null ? rs.getString(11) : "";
                    String grade = rs.getString(12) != null ? rs.getString(12) : "";
                    String sendby = rs.getString(13) != null ? rs.getString(13) : "";
                    String date = rs.getString(14) != null ? rs.getString(14) : "";
                    String candidatemail = rs.getString(15) != null ? rs.getString(15) : "";
                    if (!grade.equals("")) {
                        position += " - " + grade;
                    }

                    info = new ClientselectionInfo(shortlistId, candidateId, clientname, clientmailId, comailId, pdffileName, candidateName, jobpostId,
                            position, bccmailId, subject, mailfileName, sendby, date, candidatemail, "", "", 0, 0, "", "");
                }
                rs.close();
            }

        } catch (Exception exception) {
            print(this, "getJobpostDetail :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return info;
    }

    public ClientselectionInfo setTalentpoolEmailDetail(int candidateId) {
        ClientselectionInfo info = null;
        StringBuilder sb = new StringBuilder();
        try {
            sb.append("SELECT t_candidate.s_firstname, t_candidate.s_pdffilename, t_position.s_name, t_grade.s_name FROM t_candidate ");
            sb.append("LEFT JOIN t_position ON (t_candidate.i_positionid = t_position.i_positionid) ");
            sb.append("LEFT JOIN t_grade ON (t_position.i_gradeid = t_grade.i_gradeid) ");
            sb.append("WHERE t_candidate.i_candidateid =? ");
            String query = sb.toString().intern();
            sb.setLength(0);
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, candidateId);
            print(this, "setTalentpoolEmailDetail :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                String candidatename = rs.getString(1) != null ? rs.getString(1) : "";
                String pdffilename = rs.getString(2) != null ? rs.getString(2) : "";
                String position = rs.getString(3) != null ? rs.getString(3) : "";
                String grade = rs.getString(4) != null ? rs.getString(4) : "";
                if (!grade.equals("")) {
                    position += " - " + grade;
                }
                info = new ClientselectionInfo(candidatename, candidateId, pdffilename, position);
            }

        } catch (Exception exception) {
            print(this, "getJobpostDetail :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return info;
    }

    public String getpatchbody(String description, String htmlFile) {
        String adminpage = getMainPath("template_path") + htmlFile;
        HashMap hashmap = new HashMap();
        Template template = new Template(adminpage);
        hashmap.put("MAILBODY", description);
        return template.patch(hashmap);
    }

    public int sendofferMail(String fromval, String toval, String ccval, String bccval, String subject, String description,
            String candidatename, String filename, String clientname, int shortlistId, String username, int userId, int clientId, int assetId) throws MessagingException {
        String toaddress[] = parseCommaDelimString(toval);
        String ccaddress[] = parseCommaDelimString(ccval);
        String bccaddress[] = parseCommaDelimString(bccval);
        String filePath = getMainPath("add_resumetemplate_pdf");
        String filePath1 = "";
        if (!filename.equals("")) {
            filePath1 = filePath + filename;
        }
        String vfilePath = getMainPath("view_resumetemplate_pdf");
        String attachmentpath = "";
        if (!filename.equals("")) {
            attachmentpath = vfilePath + filename;
        }
        String sdescription = description.replaceAll("\n", "<br/>");
        String mailbody = getpatchbody(sdescription, "mail_selection.html");

        String file_maillog = getMainPath("file_maillog");
        java.util.Date nowmail = new java.util.Date();
        String fn_mail = "ofma-" + String.valueOf(nowmail.getTime());
        String filePath_html = createFolder(file_maillog);
        String fname = writeHTMLFile(mailbody, file_maillog + "/" + filePath_html, fn_mail + ".html");
        StatsInfo sinfo = postMailAttach(toaddress, ccaddress, bccaddress, mailbody, subject, filePath1, candidatename + ".pdf", -1);
        String from = "";
        if (sinfo != null) {
            from = sinfo.getDdlLabel();
        }
        int maillogId = 0;
        Documentexpiry doc = new Documentexpiry();
        int notificationId = doc.createNotification(userId, 5, 8, userId, clientId, assetId, "Offer Letter Send", username);
        if(notificationId > 0){
            maillogId = createMailLogForCVOFFER(13, clientname, toval, ccval, bccval, from, subject, filePath_html + "/" + fname, attachmentpath, shortlistId, username, notificationId);
        }
        return maillogId;
    }
    
    public int createMailLogForCVOFFER(int type, String name, String to, String cc, String bcc, String from, String subject, String filename, String attachmentpath, int shortlistId, String username, int notificationId) {
        int id = 0;
        try {

            StringBuilder sb = new StringBuilder();
            sb.append("insert into t_maillog ");
            sb.append("(i_type, s_name, s_to, s_cc, s_bcc, s_from, s_subject, s_filename, ts_regdate, ts_moddate,s_attachmentpath,i_shortlistid,s_sendby,i_notificationid) ");
            sb.append("values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

            String query = sb.toString().intern();
            sb.setLength(0);
            print(this, "createMailLogForCVOFFER :: " + query);
            conn = getConnection();
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
            pstmt.setString(11, attachmentpath);
            pstmt.setInt(12, shortlistId);
            pstmt.setString(13, username);
            pstmt.setInt(14, notificationId);
            print(this, "createMailLogForCVOFFER :: " + query);
            pstmt.executeUpdate();
            rs = pstmt.getGeneratedKeys();
            while (rs.next()) {
                id = rs.getInt(1);
            }
        } catch (Exception exception) {
            print(this, "createMailLogForCVOFFER :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return id;
    }

    public int updateoflagformail(int shortlistId, int uId, String mailby, int type) {
        int cc = 0;
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("UPDATE t_shortlist SET  ");
            sb.append(" i_oflag = ?, ");
            if(type == 2){
                sb.append(" i_sflag = 4, ");
            }
            sb.append(" s_omailby = ?, ");
            sb.append(" i_userid = ?, ");
            sb.append(" ts_omaildate = ?,  ");
            sb.append(" ts_moddate = ?  ");
            sb.append("WHERE i_shortlistid = ? ");
            String query = (sb.toString()).intern();
            sb.setLength(0);
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            int scc = 0;
            pstmt.setInt(++scc, 3);
            pstmt.setString(++scc, mailby);
            pstmt.setInt(++scc, uId);
            pstmt.setString(++scc, currDate1());
            pstmt.setString(++scc, currDate1());
            pstmt.setInt(++scc, shortlistId);
            print(this, "updateoflagformail :: " + pstmt.toString());
            cc = pstmt.executeUpdate();
        } catch (Exception exception) {
            print(this, "updateoflagformail :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return cc;
    }

    public int sendCVMail(String fromval, String toval, String ccval, String bccval, String subject, String description,
            String candidatename, String filename, String clientname, int shortlistId, String username, int userId, 
            int clientId, int assetId, int jobpostId) throws MessagingException {
        String toaddress[] = parseCommaDelimString(toval);
        String ccaddress[] = parseCommaDelimString(ccval);
        String bccaddress[] = parseCommaDelimString(bccval);
        String filePath = getMainPath("add_resumetemplate_pdf");
        String vfilePath = getMainPath("view_resumetemplate_pdf");
        String attachmentpath = vfilePath + filename;
        String sdescription = description.replaceAll("\n", "<br/>");
        String mailbody = getpatchbody(sdescription, "mail_selection.html");

        String file_maillog = getMainPath("file_maillog");
        java.util.Date nowmail = new java.util.Date();
        String fn_mail = "cvma-" + String.valueOf(nowmail.getTime());
        String filePath_html = createFolder(file_maillog);
        String fname = writeHTMLFile(mailbody, file_maillog + "/" + filePath_html, fn_mail + ".html");
        String from = "";
        StatsInfo sinfo = postMailAttach(toaddress, ccaddress, bccaddress, mailbody, subject, filePath + filename, candidatename + ".pdf", -1);
        if (sinfo != null) {
            from = sinfo.getDdlLabel();
        }
        int maillogId = 0;
        Documentexpiry doc = new Documentexpiry();
        int notificationId = doc.createNotification(userId, 4, 8, jobpostId, clientId, assetId, "Resume Send to client", username);
        if (notificationId > 0) {
            maillogId = createMailLogForCVOFFER(9, clientname, toval, ccval, bccval, from, subject, filePath_html + "/" + fname, attachmentpath, shortlistId, username, notificationId);
        }
        return maillogId;
    }

    public int updatesflagformail(int shortlistId, int uId, String mailby) {
        int cc = 0;
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("UPDATE t_shortlist SET  ");
            sb.append(" i_sflag = ?, ");
            sb.append(" s_mailby = ?, ");
            sb.append(" i_userid = ?, ");
            sb.append(" ts_maildate = ?,  ");
            sb.append(" ts_moddate = ?  ");
            sb.append("WHERE i_shortlistid = ? ");
            String query = (sb.toString()).intern();
            sb.setLength(0);
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            int scc = 0;
            pstmt.setInt(++scc, 3);
            pstmt.setString(++scc, mailby);
            pstmt.setInt(++scc, uId);
            pstmt.setString(++scc, currDate1());
            pstmt.setString(++scc, currDate1());
            pstmt.setInt(++scc, shortlistId);
            print(this, "updatesflagformail :: " + pstmt.toString());
            cc = pstmt.executeUpdate();
        } catch (Exception exception) {
            print(this, "updatesflagformail :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return cc;
    }

    public int insertclientselectionhistory(int shortlistId, int sflag, int companytype, int maillogId, int userId, int oflag) {
        int candlangId = 0;
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("insert into t_clientselectionh  ");
            sb.append("( i_shortlistid, i_sflag, i_oflag, i_usertype, i_maillogid,i_userid, ts_regdate) ");
            sb.append("values (?, ?, ?, ?, ?, ?, ?)");
            String query = (sb.toString()).intern();
            sb.setLength(0);
            conn = getConnection();
            pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            int scc = 0;
            pstmt.setInt(++scc, shortlistId);
            pstmt.setInt(++scc, sflag);
            pstmt.setInt(++scc, oflag);
            pstmt.setInt(++scc, companytype);
            pstmt.setInt(++scc, maillogId);
            pstmt.setInt(++scc, userId);
            pstmt.setString(++scc, currDate1());
            pstmt.executeUpdate();
            rs = pstmt.getGeneratedKeys();
            while (rs.next()) {
                candlangId = rs.getInt(1);
            }
        } catch (Exception exception) {
            print(this, "insertclientselectionhistory :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return candlangId;
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

    public static String currDate01() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Asia/Calcutta"));
        java.util.Date dt = cal.getTime();
        return sdf.format(dt);
    }

    public static String currDate02() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Asia/Calcutta"));
        java.util.Date dt = cal.getTime();
        return sdf.format(dt);
    }

    public String getYMD(int days) {
        String s = "";
        int y = 0, m = 0, d = 0;
        if (days > 0) {
            y = days / 365;
            int yper = days % 365;
            m = yper / 30;
            d = (int) (yper % 30);
        }
        if (y > 0) {
            s = y + " Year(s), ";
        }
        if (m > 0) {
            if (s.equals("")) {
                if (d > 0) {
                    s = m + " Month(s), ";
                } else {
                    s = m + " Month(s) ";
                }
            } else if (d > 0) {
                s += m + " Month(s), ";
            } else {
                s += m + " Month(s) ";
            }
        }
        if (d > 0) {
            if (s.equals("")) {
                s = d + " Day(s)";
            } else {
                s += d + " Day(s)";
            }
        }
        return s;
    }

    public String getYM(int days) {
        String s = "";
        int y = 0, m = 0;
        if (days > 0) {
            y = days / 365;
            int yper = days % 365;
            m = yper / 30;
        }
        if (y > 0) {
            s = y + " Year(s), ";
        }
        if (m > 0) {
            if (s.equals("")) {
                s = m + " Month(s) ";
            } else {
                s += m + " Month(s) ";
            }
        }
        return s;
    }

    public String getM(int days) {
        String s = "";
        int y = 0, m = 0, d = 0;
        if (days > 0) {
            m = days / 30;
        }
        if (m > 0) {
            if (s.equals("")) {
                if (d > 0) {
                    s = m + "";
                } else {
                    s = m + "";
                }
            } else if (d > 0) {
                s += m + "";
            } else {
                s += m + "";
            }
        }
        return s;
    }
    
    public int updateStatus(int shortlistId) 
    {
        int id= 0;
        try 
        {
            StringBuilder sb = new StringBuilder();
            sb.append("UPDATE t_shortlist SET ");
            sb.append("i_active = 2 ");
            sb.append("WHERE i_shortlistid = ? ");
            String candidatequery = (sb.toString()).intern();
            sb.setLength(0);
            conn = getConnection();
            pstmt = conn.prepareStatement(candidatequery);
            pstmt.setInt(1, shortlistId);
            print(this, "updateStatus :: " +pstmt.toString());
            id = pstmt.executeUpdate();

        } catch (Exception exception) {
            print(this, "updateStatus :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return id;
    }
}
