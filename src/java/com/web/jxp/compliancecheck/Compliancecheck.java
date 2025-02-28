package com.web.jxp.compliancecheck;

import com.web.jxp.base.Base;
import com.web.jxp.common.Common;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import static com.web.jxp.common.Common.*;
import java.io.File;
import java.util.Collection;
import java.util.LinkedList;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

public class Compliancecheck extends Base {

    private static final Logger logger = Logger.getLogger(Class.class.getName());
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    public ArrayList getJobpostByName(String search, int next, int count, int clientIdIndex, int assetIdIndex, String pgvalue, int allclient, String permission, String cids, String assetids) {

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
        sb.append("select t_jobpost.i_jobpostid, DATE_FORMAT(t_jobpost.ts_regdate,'%d-%b-%Y'), CONCAT_WS(' ', c.s_firstname, c.s_middlename, c.s_lastname), ");
        sb.append(" t_client.s_name, s.i_status,t_position.s_name, c.s_firstname, t_grade.s_name, t_clientasset.s_name, s.i_shortlistid,s.i_candidateid FROM t_shortlist as s  ");
        sb.append("left join t_candidate as c on ( s.i_candidateid = c.i_candidateid ) ");
        sb.append("left join t_jobpost on ( s.i_jobpostid = t_jobpost.i_jobpostid ) ");
        sb.append("left join t_clientasset on ( t_jobpost.i_clientassetid = t_clientasset.i_clientassetid ) ");
        sb.append("left join t_position on ( t_jobpost.i_positionid = t_position.i_positionid ) ");
        sb.append("LEFT JOIN t_grade on (t_grade.i_gradeid = t_jobpost.i_gradeid) ");
        sb.append("left join t_client on ( t_jobpost.i_clientid = t_client.i_clientid ) ");
        sb.append("where 0 = 0 ");
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
        if (clientIdIndex > 0) {
            sb.append(" and t_jobpost.i_clientid = ? ");
        }
        if (assetIdIndex > 0) {
            sb.append(" and t_jobpost.i_clientassetid = ? ");
        }
        if (!positionValue.equals("") && gradeId > 0) {
            sb.append(" and t_position.s_name = ? ");
            sb.append(" and t_position.i_gradeid = ? ");
        }

        if (search != null && !search.equals("")) {
            sb.append("and (CONCAT_WS(' ', c.s_firstname, c.s_middlename, c.s_lastname) like ? OR CONCAT_WS(' ', c.s_firstname, c.s_lastname) like ? OR t_jobpost.i_jobpostid like ? ");
            sb.append(" OR DATE_FORMAT(t_jobpost.ts_regdate, '%d-%b-%Y') like ? OR t_clientasset.s_name like ?  OR t_grade.s_name like ? OR t_position.s_name like ? )  ");
        }
        sb.append(" order by  s.i_status , t_jobpost.ts_regdate");
        if (count > 0) {
            sb.append(" LIMIT ").append(next * count).append(", ").append(count);
        }
        String query = (sb.toString()).intern();
        sb.setLength(0);

        sb.append("select count(1) FROM t_shortlist as s ");
        sb.append("left join t_candidate as c on ( s.i_candidateid = c.i_candidateid ) ");
        sb.append("left join t_jobpost on ( s.i_jobpostid = t_jobpost.i_jobpostid ) ");
        sb.append("left join t_clientasset on ( t_jobpost.i_clientassetid = t_clientasset.i_clientassetid ) ");
        sb.append("left join t_position on ( t_jobpost.i_positionid = t_position.i_positionid ) ");
        sb.append("LEFT JOIN t_grade on (t_grade.i_gradeid = t_jobpost.i_gradeid) ");
        sb.append("left join t_client on ( t_jobpost.i_clientid = t_client.i_clientid ) ");
        sb.append("where 0 = 0  ");
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
        if (clientIdIndex > 0) {
            sb.append(" and t_jobpost.i_clientid = ? ");
        }

        if (assetIdIndex > 0) {
            sb.append(" and t_jobpost.i_clientassetid = ? ");
        }

        if (!positionValue.equals("") && gradeId > 0) {
            sb.append(" and t_position.s_name = ? ");
            sb.append(" and t_position.i_gradeid = ? ");
        }

        if (search != null && !search.equals("")) {
            sb.append("and (CONCAT_WS(' ', c.s_firstname, c.s_middlename, c.s_lastname) like ? OR CONCAT_WS(' ', c.s_firstname, c.s_lastname) like ? OR t_jobpost.i_jobpostid like ? ");
            sb.append(" OR DATE_FORMAT(t_jobpost.ts_regdate, '%d-%b-%Y') like ? OR t_clientasset.s_name like ?  OR t_grade.s_name like ? OR t_position.s_name like ? )  ");
        }

        String countquery = (sb.toString()).intern();
        sb.setLength(0);
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            int scc = 0;
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
                pstmt.setString(++scc, "%" + (search) + "%");
                pstmt.setString(++scc, "%" + (search) + "%");
                pstmt.setString(++scc, search.replaceFirst("^0+(?!$)", ""));
                pstmt.setString(++scc, "%" + (search) + "%");
                pstmt.setString(++scc, "%" + (search) + "%");
                pstmt.setString(++scc, "%" + (search) + "%");
                pstmt.setString(++scc, "%" + (search) + "%");
            }
            logger.info("getJobpostByName :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String name, date, clientName, positionName, firstName, grade, clientAsset, ccStatus = "";
            int jobpostId, status, shortlistId, candidateId;
            while (rs.next()) 
            {
                jobpostId = rs.getInt(1);
                date = rs.getString(2) != null ? rs.getString(2) : "";
                name = rs.getString(3) != null ? rs.getString(3) : "";
                clientName = rs.getString(4) != null ? rs.getString(4) : "";
                status = rs.getInt(5);
                positionName = rs.getString(6);
                firstName = rs.getString(7) != null ? rs.getString(7) : "";
                grade = rs.getString(8) != null ? rs.getString(8) : "";
                clientAsset = rs.getString(9) != null ? rs.getString(9) : "";
                shortlistId = rs.getInt(10);
                candidateId = rs.getInt(11);
                ccStatus = getccStatusbyId(status);
                if (!grade.equals("")) {
                    positionName += " - " + grade;
                }
                if (!clientAsset.equals("")) {
                    clientName += " - " + clientAsset;
                }
                list.add(new CompliancecheckInfo(jobpostId, name, status, date, positionName, firstName, clientAsset, ccStatus, clientName, shortlistId, candidateId));
            }
            rs.close();

            pstmt = conn.prepareStatement(countquery);
            scc = 0;
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
                pstmt.setString(++scc, "%" + (search) + "%");
                pstmt.setString(++scc, "%" + (search) + "%");
                pstmt.setString(++scc, search.replaceFirst("^0+(?!$)", ""));
                pstmt.setString(++scc, "%" + (search) + "%");
                pstmt.setString(++scc, "%" + (search) + "%");
                pstmt.setString(++scc, "%" + (search) + "%");
                pstmt.setString(++scc, "%" + (search) + "%");
            }
            print(this, "getJobpostByName :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                jobpostId = rs.getInt(1);
                list.add(new CompliancecheckInfo(jobpostId, "", 0, "", "", "", "", "", "", 0, 0));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return list;
    }

    public String getccStatusbyId(int status) {
        String stval = "";

        if (status == 1) {
            stval = "Unchecked";

        } else if (status == 2) {
            stval = "Minimum Checked";
        } else if (status == 3) {
            stval = "Checked";
        }
        return stval;
    }

    public ArrayList getFinalRecord(ArrayList l, String colId, int tp) {
        ArrayList list = null;
        HashMap record = null;
        CompliancecheckInfo info;
        int total = 0;
        if (l != null) {
            total = l.size();
        }
        try {
            if (total > 0) {
                record = new HashMap();
                int i;
                for (i = 0; i < total; i++) {
                    info = (CompliancecheckInfo) l.get(i);
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
            CompliancecheckInfo rInfo;
            while (it.hasNext()) {
                String key = (String) it.next();
                int i;
                for (i = 0; i < total; i++) {
                    rInfo = (CompliancecheckInfo) l.get(i);
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
    public String getInfoValue(CompliancecheckInfo info, String i) {
        String infoval = "";
        if (i != null && i.equals("1")) {
            infoval = info.getJobpostId() + "";
        } else if (i != null && i.equals("2")) {
            infoval = info.getDate() != null ? info.getDate() : "";
        } else if (i != null && i.equals("3")) {
            infoval = info.getName() != null ? info.getName() : "";
        } else if (i != null && i.equals("4")) {
            infoval = info.getPosition() != null ? info.getPosition() : "";
        } else if (i != null && i.equals("5")) {
            infoval = info.getClientName() != null ? info.getClientName() : "";
        }
        return infoval;
    }

    public Collection getPositions() {
        Collection coll = new LinkedList();
        String query = ("SELECT i_positionid, s_name FROM t_position where i_status = 1 order by s_name").intern();
        coll.add(new CompliancecheckInfo(-1, "Select Position"));
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
                coll.add(new CompliancecheckInfo(refId, refName));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return coll;
    }

    public ArrayList getPics(int clientassetId) 
    {
        ArrayList list = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("select t_candidatefiles.i_fileid, t_candidatefiles.s_name, DATE_FORMAT(t_candidatefiles.ts_regdate, '%d-%b-%Y %H:%i'), ");
        sb.append("t_userlogin.s_name ");
        sb.append("FROM t_candidatefiles left join t_userlogin on (t_userlogin.i_userid = t_candidatefiles.i_userid) ");
        sb.append("where t_candidatefiles.i_candidateid = ? ");
        sb.append(" order by t_candidatefiles.i_fileid desc");
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
                list.add(new CompliancecheckInfo(fileId, filename, date, name));
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return list;
    }

    public int createPic(Connection conn, int candidateId, String filename, int userId) {
        int cc = 0;
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("insert into t_candidatefiles ");
            sb.append("(i_candidateid, s_name, i_userid,i_status, ts_regdate, ts_moddate) ");
            sb.append("values (?, ?, ?, ?,?,?)");
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

    public int delpic(int clientassetpicId) 
    {
        int cc = 0;
        String query = "select s_name FROM t_candidatefiles where i_fileid = ?";
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, clientassetpicId);
            logger.info("delpic :: " + pstmt.toString());
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
            query = "delete from t_candidatefiles where i_fileid = ?";
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
            query = "select s_filename FROM t_candlang where i_candlangid = ?";
        } else if (type == 2) {
            query = "select s_filenameexp FROM t_workexperience where i_experienceid = ?";
        }

        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, childId);
            logger.info("updatefile :: " + pstmt.toString());
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
            if (type == 1) {
                query = "update t_candlang set s_filename = '' where i_candlangid = ?";
            } else if (type == 2) {
                query = "update t_workexperience set s_filenameexp = '' where i_experienceid = ?";
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

    public String checkEmail(int candidateId, String email) {
        String output = "";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            String query = ("select i_candidateid from t_candidate where s_email = ? ").intern();
            if (candidateId > 0) {
                query += " and i_candidateid != ?";
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
            print(this, "Something went wrong." + e.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return output;
    }

    public String getPartyAutoFill(String val) {
        String s = "";
        String query = ("select distinct(s_clientpartyname) from t_workexperience where s_clientpartyname like ? order by s_clientpartyname ");
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
    public ArrayList getJobpostListForExcel(String search, int clientIdIndex, int assetIdIndex, String pgvalue, int allclient, String permission, String cids, String assetids) {
        ArrayList list = new ArrayList();
        String positionValue = "", gradeIds = "";
        int gradeId = 0;
        if (pgvalue != null && !pgvalue.equals("")) {
            String pg[] = pgvalue.split(" # ");
            positionValue = pg[0] != null ? pg[0] : "";
            gradeIds = pg[1];
            gradeId = Integer.parseInt(gradeIds);
        }

        StringBuilder sb = new StringBuilder();
        sb.append("select t_jobpost.i_jobpostid, DATE_FORMAT(t_jobpost.ts_regdate,'%d-%b-%Y'), CONCAT_WS(' ', c.s_firstname, c.s_middlename, c.s_lastname), ");
        sb.append(" t_client.s_name, s.i_status,t_position.s_name, c.s_firstname, t_grade.s_name, t_clientasset.s_name, s.i_shortlistid,s.i_candidateid FROM t_shortlist as s  ");
        sb.append("left join t_candidate as c on ( s.i_candidateid = c.i_candidateid ) ");
        sb.append("left join t_jobpost on ( s.i_jobpostid = t_jobpost.i_jobpostid ) ");
        sb.append("left join t_clientasset on ( t_jobpost.i_clientassetid = t_clientasset.i_clientassetid ) ");
        sb.append("left join t_position on ( t_jobpost.i_positionid = t_position.i_positionid ) ");
        sb.append("LEFT JOIN t_grade on (t_grade.i_gradeid = t_jobpost.i_gradeid) ");
        sb.append("left join t_client on ( t_jobpost.i_clientid = t_client.i_clientid ) ");
        sb.append("where 0 = 0 ");
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
        if (clientIdIndex > 0) {
            sb.append(" and t_jobpost.i_clientid = ? ");
        }
        if (assetIdIndex > 0) {
            sb.append(" and t_jobpost.i_clientassetid = ? ");
        }
        if (!positionValue.equals("") && gradeId > 0) {
            sb.append(" and t_position.s_name = ? ");
            sb.append(" and t_position.i_gradeid = ? ");
        }
        if (search != null && !search.equals("")) {
            sb.append("and (CONCAT_WS(' ', c.s_firstname, c.s_middlename, c.s_lastname) like ? OR CONCAT_WS(' ', c.s_firstname, c.s_lastname) like ? OR t_jobpost.i_jobpostid like ? ");
            sb.append(" OR DATE_FORMAT(t_jobpost.ts_regdate, '%d-%b-%Y') like ? OR t_clientasset.s_name like ?  OR t_grade.s_name like ? OR t_position.s_name like ? )  ");
        }
        sb.append(" order by  s.i_status , t_jobpost.ts_regdate");

        String query = (sb.toString()).intern();
        sb.setLength(0);
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            int scc = 0;
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
                pstmt.setString(++scc, "%" + (search) + "%");
                pstmt.setString(++scc, "%" + (search) + "%");
                pstmt.setString(++scc, search.replaceFirst("^0+(?!$)", ""));
                pstmt.setString(++scc, "%" + (search) + "%");
                pstmt.setString(++scc, "%" + (search) + "%");
                pstmt.setString(++scc, "%" + (search) + "%");
                pstmt.setString(++scc, "%" + (search) + "%");
            }
            logger.info("getJobpostByName :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String name, date, clientName, positionName, firstName, grade, clientAsset, ccStatus = "";
            int jobpostId, status, shortlistId, candidateId;
            while (rs.next()) 
            {
                jobpostId = rs.getInt(1);
                date = rs.getString(2) != null ? rs.getString(2) : "";
                name = rs.getString(3) != null ? rs.getString(3) : "";
                clientName = rs.getString(4) != null ? rs.getString(4) : "";
                status = rs.getInt(5);
                positionName = rs.getString(6);
                firstName = rs.getString(7) != null ? rs.getString(7) : "";
                grade = rs.getString(8) != null ? rs.getString(8) : "";
                clientAsset = rs.getString(9) != null ? rs.getString(9) : "";
                shortlistId = rs.getInt(10);
                candidateId = rs.getInt(11);
                ccStatus = getccStatusbyId(status);
                if (!grade.equals("")) {
                    positionName += " - " + grade;
                }
                if (!clientAsset.equals("")) {
                    clientName += " - " + clientAsset;
                }
                list.add(new CompliancecheckInfo(jobpostId, name, status, date, positionName, firstName, clientAsset, ccStatus, clientName, shortlistId, candidateId));
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
        sb.append("SELECT i_clientid, s_name FROM t_client where i_status = 1 ");
        if (allclient == 1) {
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
        coll.add(new CompliancecheckInfo(-1, " Select Client"));
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
                coll.add(new CompliancecheckInfo(refId, refName));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return coll;
    }

    public Collection getPositionsforIndex() {
        Collection coll = new LinkedList();
        String query = ("SELECT t_position.i_positionid, t_position.s_name,t_grade.s_name  FROM t_position LEFT JOIN t_grade on (t_grade.i_gradeid = t_position.i_gradeid) where t_position.i_status = 1 order by t_position.s_name").intern();
        coll.add(new CompliancecheckInfo(-1, "Select Position - Rank"));
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            rs = pstmt.executeQuery();
            int refId;
            String positionName, grade;
            while (rs.next()) 
            {
                refId = rs.getInt(1);
                positionName = rs.getString(2) != null ? rs.getString(2): "";
                grade = rs.getString(3) != null ? rs.getString(3) : "";
                if (!grade.equals("")) {
                    positionName += " - " + grade;
                }
                coll.add(new CompliancecheckInfo(refId, positionName));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return coll;
    }

    public Collection getJobposts() 
    {
        Collection coll = new LinkedList();
        String query = ("SELECT i_jobpostid FROM t_jobpost where i_status = 1 order by i_jobpostid").intern();
        coll.add(new CompliancecheckInfo(-1, " Select Job Posting"));
        try
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            rs = pstmt.executeQuery();
            int refId;
            while (rs.next()) 
            {
                refId = rs.getInt(1);
                coll.add(new CompliancecheckInfo(refId, refId + ""));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return coll;
    }

    public CompliancecheckInfo getCandidateDetail(int candidateId) {
        CompliancecheckInfo info = null;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT CONCAT_WS(' ', c.s_firstname, c.s_middlename, c.s_lastname), t_position.s_name ,c.i_expectedsalary,t_currency.s_name, ");
        sb.append("t_grade.s_name, t_degree.s_name, t_qualificationtype.s_name, tw1.s_companyname, c.s_photofilename, w.exp FROM t_candidate AS c ");
        sb.append("LEFT JOIN t_position ON (t_position.i_positionid = c.i_positionid) ");
        sb.append("LEFT JOIN t_eduqual ON (t_eduqual.i_candidateid = c.i_candidateid  and t_eduqual.i_highestqualification = 1 ) ");
        sb.append("LEFT JOIN (SELECT tw.i_candidateid, tw.s_companyname FROM (SELECT i_candidateid, s_companyname FROM t_workexperience WHERE i_status = 1 ORDER BY d_workstartdate DESC) AS tw GROUP BY i_candidateid) AS tw1 ON (tw1.i_candidateid = c.i_candidateid) ");
        sb.append("LEFT JOIN t_degree ON (t_degree.i_degreeid = t_eduqual.i_degreeid) ");
        sb.append("LEFT JOIN t_qualificationtype ON ( t_qualificationtype.i_qualificationtypeid = t_eduqual.i_qualificationtypeid ) ");
        sb.append("LEFT JOIN t_currency ON (t_currency.i_currencyid = c.i_currencyid) ");
        sb.append("LEFT JOIN t_grade ON (t_grade.i_gradeid = t_position.i_gradeid) ");
        sb.append("LEFT JOIN (SELECT i_candidateid, ROUND(SUM(DATEDIFF( IF(d_workenddate IS NULL OR d_workenddate = '0000-00-00', current_date(), d_workenddate), d_workstartdate)) / 365, 1) AS exp ");
        sb.append("FROM t_workexperience where i_status = 1 and i_candidateid = ?) AS w ON (c.i_candidateid = w.i_candidateid) ");
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
                String name = rs.getString(1);
                String positionName = rs.getString(2) != null ? rs.getString(2) : "";
                int expectedsalary = rs.getInt(3);
                String currency = rs.getString(4) != null ? rs.getString(4) : "";
                String gradeName = rs.getString(5) != null ? rs.getString(5) : "";
                String degree = rs.getString(6) != null ? rs.getString(6) : "";
                String qualificationType = rs.getString(7) != null ? rs.getString(7) : "";
                String companyName = rs.getString(8) != null ? rs.getString(8) : "";
                String photoName = rs.getString(9) != null ? rs.getString(9) : "";
                double experience = rs.getDouble(10);
                if (!positionName.equals("")) {
                    if (!gradeName.equals("")) {
                        positionName += " - " + gradeName;
                    }
                }
                info = new CompliancecheckInfo(candidateId, name, positionName, expectedsalary, currency, degree, qualificationType, gradeName, companyName, photoName, experience);
            }
        } catch (Exception exception) {
            print(this, "getCandidateDetailById :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return info;
    }

    public CompliancecheckInfo getJobpostDetail(int jobpostId) {
        CompliancecheckInfo info = null;
        StringBuilder sb = new StringBuilder();
        sb.append("select  DATE_FORMAT(t_jobpost.ts_regdate,'%d-%b-%Y') , t_client.s_name, t_position.s_name, t_clientasset.s_name,  ");
        sb.append("t_grade.s_name , t_qualificationtype.s_name, t_jobpost.d_experiencemin, t_jobpost.d_experiencemax, ");
        sb.append("t_jobpost.i_noofopening, t_jobpost.i_gradeid, t_jobpost.i_clientid, t_jobpost.i_clientassetid  ");
        sb.append("FROM t_jobpost  ");
        sb.append("left join t_clientasset on ( t_jobpost.i_clientassetid = t_clientasset.i_clientassetid ) ");
        sb.append("left join t_position on ( t_jobpost.i_positionid = t_position.i_positionid ) ");
        sb.append("LEFT JOIN t_grade on (t_grade.i_gradeid = t_jobpost.i_gradeid) ");
        sb.append("left join t_client on ( t_jobpost.i_clientid = t_client.i_clientid ) ");
        sb.append("left join t_qualificationtype on ( t_qualificationtype.i_qualificationtypeid = t_jobpost.i_qualificationtypeid ) ");
        sb.append("where t_jobpost.i_jobpostid = ?  ");
        String query = sb.toString().intern();
        sb.setLength(0);
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, jobpostId);
            print(this, "getJobpostDetail :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            while (rs.next()) 
            {
                String date = rs.getString(1) != null ? rs.getString(1) : "";
                String clientName = rs.getString(2) != null ? rs.getString(2) : "";
                String position = rs.getString(3) != null ? rs.getString(3) : "";
                String assetName = rs.getString(4) != null ? rs.getString(4) : "";
                String gradeName = rs.getString(5) != null ? rs.getString(5) : "";
                String qualificationType = rs.getString(6) != null ? rs.getString(6) : "";
                double expMin = rs.getDouble(7);
                double expMax = rs.getDouble(8);
                int openings = rs.getInt(9);
                int gradeId = rs.getInt(10);
                int clientId = rs.getInt(11);
                int clientassetId = rs.getInt(12);
                String positionName = position;
                if (!positionName.equals("")) {
                    if (!gradeName.equals("")) {
                        positionName += " - " + gradeName;
                    }
                }
                info = new CompliancecheckInfo(jobpostId, date, clientName, positionName, assetName, qualificationType, expMin, expMax, openings, gradeId, clientId, clientassetId, position);
            }
        } catch (Exception exception) {
            print(this, "getJobpostDetail :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return info;
    }

    public ArrayList getcompliancechecklist(int shortlistId, int gradeId, int clientId, int clientassetId, String position, int status, int shortlistccId) 
    {
        ArrayList list = new ArrayList();
        if (status > 1)
        {
            StringBuilder sb = new StringBuilder();
            sb.append("select t_checkpoint.i_checkpointid, t_checkpoint.s_name, t_shortlistcc.i_shortlistccid, t_shortlistcc.i_status, t_userlogin.s_name ");
            sb.append("from t_shortlistcc left join  t_checkpoint on (t_checkpoint.i_checkpointid = t_shortlistcc.i_checkpointid and t_shortlistcc.i_shortlistid = ?) ");
            sb.append("left join t_userlogin on (t_userlogin.i_userid = t_shortlistcc.i_userid) ");
            sb.append("where  t_shortlistcc.i_shortlistid = ? ");
            sb.append("order by t_checkpoint.s_name ");
            String query = (sb.toString()).intern();
            sb.setLength(0);
            try
            {
                conn = getConnection();
                pstmt = conn.prepareStatement(query);
                pstmt.setInt(1, shortlistId);
                pstmt.setInt(2, shortlistId);
                logger.info("getcompliancechecklist status > 1:: " + pstmt.toString());
                rs = pstmt.executeQuery();
                String checkpointName, userName;
                int checkpointId, nshortlistccId, ccstatus;
                while (rs.next()) 
                {
                    checkpointId = rs.getInt(1);
                    checkpointName = rs.getString(2) != null ? rs.getString(2) : "";
                    nshortlistccId = rs.getInt(3);
                    ccstatus = rs.getInt(4);
                    userName = rs.getString(5) != null ? rs.getString(5) : "";
                    list.add(new CompliancecheckInfo(checkpointId, checkpointName, nshortlistccId, ccstatus, userName));
                }
                rs.close();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                close(conn, pstmt, rs);
            }

        } else {

            StringBuilder sb = new StringBuilder();
            sb.append("select t_checkpoint.i_checkpointid, t_checkpoint.s_name, t_shortlistcc.i_shortlistccid, t_shortlistcc.i_status, t_userlogin.s_name ");
            sb.append("from t_checkpoint left join t_shortlistcc on (t_checkpoint.i_checkpointid = t_shortlistcc.i_checkpointid and t_shortlistcc.i_shortlistid = ?) ");
            sb.append("left join t_userlogin on (t_userlogin.i_userid = t_shortlistcc.i_userid) ");
            sb.append("left join t_position on (t_position.i_positionid = t_checkpoint.i_positionid) ");
            sb.append("where t_checkpoint.i_clientid = ? and t_checkpoint.i_clientassetid = ? and t_position.s_name = ? and t_checkpoint.i_gradeid = ? and t_checkpoint.i_status = 1 ");
            sb.append("order by t_checkpoint.s_name ");
            String query = (sb.toString()).intern();
            sb.setLength(0);
            try 
            {
                conn = getConnection();
                pstmt = conn.prepareStatement(query);
                pstmt.setInt(1, shortlistId);
                pstmt.setInt(2, clientId);
                pstmt.setInt(3, clientassetId);
                pstmt.setString(4, position);
                pstmt.setInt(5, gradeId);
                logger.info("getcompliancechecklist status < 1:: " + pstmt.toString());
                rs = pstmt.executeQuery();
                String checkpointName, userName;
                int checkpointId, nshortlistccId, ccstatus;
                while (rs.next()) 
                {
                    checkpointId = rs.getInt(1);
                    checkpointName = rs.getString(2) != null ? rs.getString(2) : "";
                    nshortlistccId = rs.getInt(3);
                    ccstatus = rs.getInt(4);
                    userName = rs.getString(5) != null ? rs.getString(5) : "";
                    list.add(new CompliancecheckInfo(checkpointId, checkpointName, nshortlistccId, ccstatus, userName));
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

    public void updatemainst(int shortlistId, int listsize) {
        try {
            conn = getConnection();
            String countquery = "select t1.ct, t2.ct from (select count(1) as ct from t_shortlistcc where i_shortlistid = ? and i_status = 1) as t1, "
                    + "(select count(1) as ct from t_shortlistcc where i_shortlistid = ? and i_status = 2) as t2";
            pstmt = conn.prepareStatement(countquery);
            pstmt.setInt(1, shortlistId);
            pstmt.setInt(2, shortlistId);
            logger.info("updatemainst :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            int countst1 = 0, countst2 = 0;
            while (rs.next()) {
                countst1 = rs.getInt(1);
                countst2 = rs.getInt(2);
            }
            rs.close();
            int stval = 0;
            if (listsize == countst1 + countst2) {
                if (listsize == countst2) {
                    stval = 3;
                } else {
                    stval = 2;
                }
                String query = "update t_shortlist set i_status = ?, ts_moddate = ? where i_shortlistid = ?";
                pstmt = conn.prepareStatement(query);
                pstmt.setInt(1, stval);
                pstmt.setString(2, currDate1());
                pstmt.setInt(3, shortlistId);
                pstmt.executeUpdate();
            }
        } catch (Exception exception) {
            print(this, "updatemainst :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
    }

    public int insertShortlistccdetails(CompliancecheckInfo info, int candidateId, int userId) {
        int cc = 0;
        try {

            StringBuilder sb = new StringBuilder();
            sb.append("insert into t_shortlistcc  ");
            sb.append("( i_shortlistid, i_checkpointid, s_remarks, ");
            sb.append("  i_status , i_userid, ts_regdate, ts_moddate) ");
            sb.append("values (?, ?, ?, ?, ?, ?, ?)");
            String query1 = (sb.toString()).intern();
            sb.setLength(0);
            conn = getConnection();
            pstmt = conn.prepareStatement(query1, PreparedStatement.RETURN_GENERATED_KEYS);
            int scc = 0;
            pstmt.setInt(++scc, info.getShortlistId());
            pstmt.setInt(++scc, info.getCheckpointId());
            pstmt.setString(++scc, info.getRemarks());
            pstmt.setInt(++scc, info.getStatus());
            pstmt.setInt(++scc, userId);
            pstmt.setString(++scc, currDate1());
            pstmt.setString(++scc, currDate1());
            print(this, "insertShortlistccdetails :: " + pstmt.toString());
            pstmt.executeUpdate();
            rs = pstmt.getGeneratedKeys();
            while (rs.next()) {
                cc = rs.getInt(1);
            }

            insertShortlistcchdetails(info, candidateId, userId, cc);

        } catch (Exception exception) {
            print(this, "insertShortlistccdetails :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return cc;
    }

    public int updateShortlistcc(CompliancecheckInfo info, int candidateId, int uId) {
        int cc = 0;
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("update t_shortlistcc set  ");
            sb.append(" i_shortlistid = ?, ");
            sb.append(" i_checkpointid = ?, ");
            sb.append(" s_remarks = ?, ");
            sb.append(" i_status = ?, ");
            sb.append(" ts_moddate = ?  ");
            sb.append("where i_shortlistccid = ? ");
            String query = (sb.toString()).intern();
            sb.setLength(0);
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            int scc = 0;
            pstmt.setInt(++scc, info.getShortlistId());
            pstmt.setInt(++scc, info.getCheckpointId());
            pstmt.setString(++scc, info.getRemarks());
            pstmt.setInt(++scc, info.getStatus());
            pstmt.setString(++scc, currDate1());
            pstmt.setInt(++scc, info.getShortlistccId());
            print(this, "updateShortlistcc :: " + pstmt.toString());
            cc = pstmt.executeUpdate();
            insertShortlistcchdetails(info, candidateId, uId, info.getShortlistccId());
        } catch (Exception exception) {
            print(this, "updateShortlistcc :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return cc;
    }

    public int insertShortlistcchdetails(CompliancecheckInfo info, int candidateId, int userId, int shortlistccId) {
        int cc = 0;
        try {

            StringBuilder sb = new StringBuilder();
            sb.append("insert into t_shortlistcch  ");
            sb.append("( i_shortlistid,i_shortlistccid, i_checkpointid, s_remarks, ");
            sb.append("  i_status , i_userid, ts_regdate, ts_moddate) ");
            sb.append("values (?, ?, ?, ?, ?, ?, ?, ?)");
            String query1 = (sb.toString()).intern();
            sb.setLength(0);
            conn = getConnection();
            pstmt = conn.prepareStatement(query1, PreparedStatement.RETURN_GENERATED_KEYS);
            int scc = 0;
            pstmt.setInt(++scc, info.getShortlistId());
            pstmt.setInt(++scc, shortlistccId);
            pstmt.setInt(++scc, info.getCheckpointId());
            pstmt.setString(++scc, info.getRemarks());
            pstmt.setInt(++scc, info.getStatus());
            pstmt.setInt(++scc, userId);
            pstmt.setString(++scc, currDate1());
            pstmt.setString(++scc, currDate1());
            print(this, "insertShortlistcchdetails :: " + pstmt.toString());
            pstmt.executeUpdate();
            rs = pstmt.getGeneratedKeys();
            while (rs.next()) {
                cc = rs.getInt(1);
            }
        } catch (Exception exception) {
            print(this, "insertShortlistcchdetails :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return cc;
    }

    public String getRemarksbycheckpointId(int checkpointId) {
        String remarks = "";
        String data = "";
        int flag = 0;
        String query = ("SELECT s_desc, i_flag FROM t_checkpoint where i_checkpointid = ? and i_status = 1").intern();
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, checkpointId);
            logger.info("getRemarksbycheckpointId :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            while (rs.next()) 
            {
                remarks = rs.getString(1) != null ? rs.getString(1): "";
                flag = rs.getInt(2);
            }
            if (flag == 1) {
                data = "<b>Minimum Checked:</b><br/>" + remarks + "<br/>";
            } else if (flag == 0) {
                data = "<b>Checked:</b><br/>" + remarks + "<br/>";
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return data;
    }

    public ArrayList getShortlisthistorylist(int shortlistccId, int checkpointId, int shortlistId) {
        ArrayList list = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("select DATE_FORMAT(s.ts_regdate,'%d-%b-%Y %H %i') , s.s_remarks,  t_userlogin.s_name  from t_shortlistcch as s ");
        sb.append(" left join  t_userlogin on (t_userlogin.i_userid = s.i_userid)");
        sb.append("where s.i_shortlistccid = ? and s.i_checkpointid = ? and s.i_shortlistid = ? ");
        sb.append("order by s.ts_regdate desc ");
        String query = (sb.toString()).intern();
        sb.setLength(0);
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, shortlistccId);
            pstmt.setInt(2, checkpointId);
            pstmt.setInt(3, shortlistId);
            logger.info("getcompliancechecklist :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String date, name, remarks;
            while (rs.next()) 
            {
                date = rs.getString(1) != null ? rs.getString(1) : "";
                remarks = rs.getString(2) != null ? rs.getString(2) : "";
                name = rs.getString(3) != null ? rs.getString(3) : "";
                list.add(new CompliancecheckInfo(date, remarks, name));
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return list;
    }

    public String getVerifiedImage(int key) {
        String s = "";
        if (key == 0) {
            s = "inactive_verified.png";
        } else if (key == 1) {
            s = "pending_verified.png";
        } else if (key == 2) {
            s = "active_verified.png";
        }
        return s;
    }

    public Collection getClientAsset(int clientId, String assetids, int allclient, String permission) {
        Collection coll = new LinkedList();
        if (clientId > 0) {
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT i_clientassetid, s_name FROM t_clientasset WHERE i_clientid = ? AND i_status = 1 ");
            if (allclient == 1) {
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
            coll.add(new CompliancecheckInfo(-1, " Select Asset "));
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
                    coll.add(new CompliancecheckInfo(ddlValue, ddlLabel));
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                close(conn, pstmt, rs);
            }
        } else {
            coll.add(new CompliancecheckInfo(-1, " Select Asset "));
        }
        return coll;
    }

    public Collection getPostions(int clientassetId) {
        Collection coll = new LinkedList();
        if (clientassetId > 0) {
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT t_clientassetposition.i_positionid, t_position.s_name, t_grade.s_name, t_position.i_gradeid FROM t_clientassetposition"
                    + " LEFT JOIN t_position ON (t_position.i_positionid = t_clientassetposition.i_positionid)"
                    + " LEFT JOIN t_grade ON (t_grade.i_gradeid = t_position.i_gradeid) "
                    + " WHERE t_clientassetposition.i_clientassetid = ? ORDER BY t_position.s_name");
            String query = sb.toString();
            sb.setLength(0);
            coll.add(new CompliancecheckInfo("", " Select Position - Rank "));
            try {
                conn = getConnection();
                pstmt = conn.prepareStatement(query);
                pstmt.setInt(1, clientassetId);
                logger.info("getPostions :: " + pstmt.toString());
                Common.print(this, "getPostions :: " + pstmt.toString());
                rs = pstmt.executeQuery();
                int ddlValue, gradeId;
                String ddlLabel = "", grade, position, positionValue = "";
                while (rs.next())
                {
                    ddlValue = rs.getInt(1);
                    position = rs.getString(2) != null ? rs.getString(2): "";
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
                    coll.add(new CompliancecheckInfo(positionValue, ddlLabel));
                    positionValue = "";
                    ddlLabel = "";
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                close(conn, pstmt, rs);
            }
        } else {
            coll.add(new CompliancecheckInfo("", " Select Position - Rank "));
        }
        return coll;
    }

    public int saveShortlistccdata(CompliancecheckInfo info, int candidateId, int uId, int listsize) {
        int cc = 0;
        try {

            if (info.getShortlistccId() <= 0) {
                String checkquery = "select i_shortlistccid from t_shortlistcc where i_shortlistid = ? and i_checkpointid = ?";
                conn = getConnection();
                pstmt = conn.prepareStatement(checkquery);
                pstmt.setInt(1, info.getShortlistId());
                pstmt.setInt(2, info.getCheckpointId());
                rs = pstmt.executeQuery();
                int id = 0;
                while (rs.next()) {
                    id = rs.getInt(1);
                }
                // rs.close();
                close(conn, pstmt, rs);
                if (id <= 0) {
                    cc = insertShortlistccdetails(info, candidateId, uId);
                    updatemainst(info.getShortlistId(), listsize);
                }
            } else {
                cc = updateShortlistcc(info, candidateId, uId);
                updatemainst(info.getShortlistId(), listsize);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return cc;
    }

    public CompliancecheckInfo getShortlistlistcc(int shortlistccId, int checkpointId, int shortlistId) {
        CompliancecheckInfo info = null;
        StringBuilder sb = new StringBuilder();
        sb.append("select DATE_FORMAT(s.ts_regdate,'%d-%b-%Y %H %i') , s.s_remarks,  t_userlogin.s_name, s.i_status  from t_shortlistcc as s ");
        sb.append(" left join  t_userlogin on (t_userlogin.i_userid = s.i_userid)");
        sb.append("where s.i_shortlistccid = ? and s.i_checkpointid = ? and s.i_shortlistid = ? ");
        String query = (sb.toString()).intern();
        sb.setLength(0);
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, shortlistccId);
            pstmt.setInt(2, checkpointId);
            pstmt.setInt(3, shortlistId);
            logger.info("getShortlistlistcc :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String date, name, remarks;
            int status = 0;
            while (rs.next()) {
                date = rs.getString(1) != null ? rs.getString(1) : "";
                remarks = rs.getString(2) != null ? rs.getString(2) : "";
                name = rs.getString(3) != null ? rs.getString(3) : "";
                status = rs.getInt(4);
                info = new CompliancecheckInfo(date, remarks, name, status);
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return info;
    }
}
