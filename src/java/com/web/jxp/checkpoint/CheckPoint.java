package com.web.jxp.checkpoint;

import com.mysql.jdbc.Statement;
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
import java.util.Collection;
import java.util.LinkedList;
import java.util.logging.Logger;

public class CheckPoint extends Base {

    private static final Logger logger = Logger.getLogger(Class.class.getName());
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    public ArrayList getCheckPointByName(String search, int next, int count) 
    {
        ArrayList checkpoint = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT cp.i_checkpointid, cp.s_name, cp.s_desc, cp.i_flag, cp.i_status, t_client.s_name, t_clientasset.s_name, t_position.s_name, ");
        sb.append("t_grade.s_name FROM t_checkpoint AS cp ");
        sb.append("LEFT JOIN t_client ON cp.i_clientid = t_client.i_clientid ");
        sb.append("LEFT JOIN t_clientasset ON cp.i_clientassetid = t_clientasset.i_clientassetid ");
        sb.append("LEFT JOIN t_position ON cp.i_positionid = t_position.i_positionid ");
        sb.append("LEFT JOIN t_grade ON cp.i_gradeid = t_grade.i_gradeid ");
        sb.append("WHERE 0 = 0 ");
        if (search != null && !search.equals("")) {
            sb.append("AND (cp.s_name LIKE ? OR t_client.s_name LIKE ? OR t_clientasset.s_name LIKE ? OR t_position.s_name LIKE ? OR t_grade.s_name LIKE ?) ");
        }
        sb.append("ORDER BY t_client.s_name, cp.s_name ");
        if (count > 0) {
            sb.append(" limit ").append(next * count).append(", ").append(count);
        }
        String query = (sb.toString()).intern();
        sb.setLength(0);

        sb.append("SELECT COUNT(1) FROM t_checkpoint AS cp ");
        sb.append("LEFT JOIN t_client ON cp.i_clientid = t_client.i_clientid ");
        sb.append("LEFT JOIN t_clientasset ON cp.i_clientassetid = t_clientasset.i_clientassetid ");
        sb.append("LEFT JOIN t_position ON cp.i_positionid = t_position.i_positionid ");
        sb.append("LEFT JOIN t_grade ON cp.i_gradeid = t_grade.i_gradeid ");
        sb.append("WHERE 0 = 0 ");
        if (search != null && !search.equals("")) {
            sb.append("AND (cp.s_name LIKE ? OR t_client.s_name LIKE ? OR t_clientasset.s_name LIKE ? OR t_position.s_name LIKE ? OR t_grade.s_name LIKE ?) ");
        }
        sb.append("ORDER BY cp.s_name");
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
                pstmt.setString(++scc, "%" + (search) + "%");
                pstmt.setString(++scc, "%" + (search) + "%");
                pstmt.setString(++scc, "%" + (search) + "%");
                pstmt.setString(++scc, "%" + (search) + "%");
            }
            logger.info("getCheckPointByName :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String name, desc, clientname, clientassetname, positionname, grade;
            int checkpointId, status, flag;
            while (rs.next())
            {
                checkpointId = rs.getInt(1);
                name = rs.getString(2) != null ? rs.getString(2) : "";
                desc = rs.getString(3) != null ? rs.getString(3) : "";
                flag = rs.getInt(4);
                status = rs.getInt(5);
                clientname = rs.getString(6) != null ? rs.getString(6) : "";
                clientassetname = rs.getString(7) != null ? rs.getString(7) : "";
                positionname = rs.getString(8) != null ? rs.getString(8) : "";
                grade = rs.getString(9) != null ? rs.getString(9) : "";
                checkpoint.add(new CheckPointInfo(checkpointId, name, desc, clientname, clientassetname, positionname, grade, flag, status));
            }
            rs.close();

            pstmt = conn.prepareStatement(countquery);
            scc = 0;
            if (search != null && !search.equals("")) 
            {
                pstmt.setString(++scc, "%" + (search) + "%");
                pstmt.setString(++scc, "%" + (search) + "%");
                pstmt.setString(++scc, "%" + (search) + "%");
                pstmt.setString(++scc, "%" + (search) + "%");
                pstmt.setString(++scc, "%" + (search) + "%");
            }
            print(this, "getCheckPointByName Count:: " + pstmt.toString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                checkpointId = rs.getInt(1);
                checkpoint.add(new CheckPointInfo(checkpointId, "", "", "", "", "", "", 0, 0));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return checkpoint;
    }

    public ArrayList getFinalRecord(ArrayList l, String colId, int tp) {
        ArrayList list = null;
        HashMap record = null;
        CheckPointInfo info;
        int total = 0;
        if (l != null) {
            total = l.size();
        }
        try {
            if (total > 0) {
                record = new HashMap();
                int i;
                for (i = 0; i < total; i++) {
                    info = (CheckPointInfo) l.get(i);
                    record.put(getInfoValue(info, colId), info);
                }
            }
            Map map = sortByName(record, tp);
            Iterator it = map.keySet().iterator();
            list = new ArrayList();
            CheckPointInfo rInfo;
            while (it.hasNext()) {
                String key = (String) it.next();
                int i;
                for (i = 0; i < total; i++) {
                    rInfo = (CheckPointInfo) l.get(i);
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
    public String getInfoValue(CheckPointInfo info, String i) 
    {
        String infoval = "";
        if (i != null && i.equals("1")) {
            infoval = info.getName() != null ? info.getName() : "";
        } else if (i != null && i.equals("2")) {
            infoval = info.getClientname() != null ? info.getClientname() : "";
        } else if (i != null && i.equals("3")) {
            infoval = info.getClientassetname() != null ? info.getClientassetname() : "";
        } else if (i != null && i.equals("4")) {
            infoval = info.getPositionname() != null ? info.getPositionname() : "";
        }
        return infoval;
    }

    public String getCode() 
    {
        String code = "";
        String selquery = "Select MAX(i_checkpointid) FROM t_checkpoint ";
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(selquery);
            logger.info("getCode :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            int id = 0;
            while (rs.next()) {
                id = rs.getInt(1);
                code = changeNum((id + 1), 3);
            }
        } catch (Exception exception) {
            print(this, "getCode :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return code;
    }

    public CheckPointInfo getCheckPointDetailById(int checkpointId) 
    {
        CheckPointInfo info = null;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT t_checkpoint.s_name, t_checkpoint.s_desc, t_checkpoint.i_flag, t_checkpoint.i_status, t_checkpoint.i_clientid, t_checkpoint.i_clientassetid, ");
        sb.append("t_checkpoint.i_positionid, t_checkpoint.i_gradeid, t_checkpoint.i_userid, t_position.s_name FROM t_checkpoint ");
        sb.append("LEFT JOIN t_position ON t_checkpoint.i_positionid = t_position.i_positionid ");
        sb.append("WHERE t_checkpoint.i_checkpointid =? ");
        String query = sb.toString().intern();
        sb.setLength(0);
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, checkpointId);
            logger.info("getCheckPointDetailById :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String name, desc, code, positionname;
            int flag, uId, status, clientId, clientassetId, positionId, gradeId;
            while (rs.next()) 
            {
                name = rs.getString(1) != null ? rs.getString(1) : "";
                desc = rs.getString(2) != null ? rs.getString(2) : "";
                flag = rs.getInt(3);
                status = rs.getInt(4);
                clientId = rs.getInt(5);
                clientassetId = rs.getInt(6);
                positionId = rs.getInt(7);
                gradeId = rs.getInt(8);
                uId = rs.getInt(9);
                positionname = rs.getString(10) != null ? rs.getString(10) : "";
                code = changeNum(checkpointId, 3);
                info = new CheckPointInfo(name, desc, flag, status, clientId, clientassetId, positionId, gradeId, uId, code, positionname);
            }
        } catch (Exception exception) {
            print(this, "getCheckPointDetailById :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return info;
    }

    public CheckPointInfo getCheckPointDetailByIdforDetail(int checkpointId) 
    {
        CheckPointInfo info = null;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT cp.s_name, cp.s_desc, cp.i_flag, cp.i_status, t_client.s_name, t_clientasset.s_name, t_position.s_name, ");
        sb.append("t_grade.s_name, cp.i_userid FROM t_checkpoint AS cp ");
        sb.append("LEFT JOIN t_client ON cp.i_clientid = t_client.i_clientid ");
        sb.append("LEFT JOIN t_clientasset ON cp.i_clientassetid = t_clientasset.i_clientassetid ");
        sb.append("LEFT JOIN t_position ON cp.i_positionid = t_position.i_positionid ");
        sb.append("LEFT JOIN t_grade ON cp.i_gradeid = t_grade.i_gradeid ");
        sb.append("WHERE cp.i_checkpointid =? ");
        String query = sb.toString().intern();
        sb.setLength(0);
        try
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, checkpointId);
            logger.info("getCheckPointDetailById :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String name, desc, clientname, clientassetname, positionname, grade, code;
            int flag, uId, status;
            while (rs.next()) 
            {
                name = rs.getString(1) != null ? rs.getString(1) : "";
                desc = rs.getString(2) != null ? rs.getString(2) : "";
                flag = rs.getInt(3);
                status = rs.getInt(4);
                clientname = rs.getString(5) != null ? rs.getString(5) : "";
                clientassetname = rs.getString(6) != null ? rs.getString(6) : "";
                positionname = rs.getString(7) != null ? rs.getString(7) : "";
                grade = rs.getString(8) != null ? rs.getString(8) : "";
                uId = rs.getInt(9);
                code = changeNum(checkpointId, 3);
                info = new CheckPointInfo(checkpointId, name, desc, flag, status, clientname, clientassetname, positionname,
                        grade, uId, code);
            }
        } catch (Exception exception) {
            print(this, "getCheckPointDetailByIdforDetail :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return info;
    }

    public int createCheckPoint(CheckPointInfo info) 
    {
        int checkpointId = 0;
        try 
        {
            StringBuilder sb = new StringBuilder();
            sb.append("INSERT INTO t_checkpoint ");
            sb.append("(s_name, s_desc, i_flag, i_status, i_userid, ts_regdate, ts_moddate, i_clientid, i_clientassetid, i_positionid, i_gradeid) ");
            sb.append("VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ");
            String query = (sb.toString()).intern();
            sb.setLength(0);

            conn = getConnection();
            pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            int scc = 0;
            pstmt.setString(++scc, (info.getName()));
            pstmt.setString(++scc, (info.getDesc()));
            pstmt.setInt(++scc, (info.getFlag()));
            pstmt.setInt(++scc, (info.getStatus()));
            pstmt.setInt(++scc, (info.getuId()));
            pstmt.setString(++scc, currDate1());
            pstmt.setString(++scc, currDate1());
            pstmt.setInt(++scc, (info.getClientId()));
            pstmt.setInt(++scc, (info.getClientassetId()));
            pstmt.setInt(++scc, (info.getPositionId()));
            pstmt.setInt(++scc, (info.getGradeId()));

            print(this, "createCheckPoint :: " + pstmt.toString());
            pstmt.executeUpdate();
            rs = pstmt.getGeneratedKeys();
            while (rs.next()) {
                checkpointId = rs.getInt(1);
            }
        } catch (Exception exception) {
            print(this, "createCheckPoint :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return checkpointId;
    }

    public int updateCheckPoint(CheckPointInfo info) 
    {
        int cc = 0;
        try 
        {
            StringBuilder sb = new StringBuilder();
            sb.append("UPDATE t_checkpoint SET ");
            sb.append("s_name = ?, ");
            sb.append("s_desc = ?, ");
            sb.append("i_flag = ?, ");
            sb.append("i_status = ?, ");
            sb.append("i_userid = ?, ");
            sb.append("ts_regdate = ?, ");
            sb.append("ts_moddate = ?, ");
            sb.append("i_clientid = ?, ");
            sb.append("i_clientassetid = ?, ");
            sb.append("i_positionid = ?, ");
            sb.append("i_gradeid = ? ");
            sb.append("WHERE i_checkpointid = ? ");
            String query = (sb.toString()).intern();
            sb.setLength(0);

            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            int scc = 0;
            pstmt.setString(++scc, (info.getName()));
            pstmt.setString(++scc, (info.getDesc()));
            pstmt.setInt(++scc, (info.getFlag()));
            pstmt.setInt(++scc, (info.getStatus()));
            pstmt.setInt(++scc, (info.getuId()));
            pstmt.setString(++scc, currDate1());
            pstmt.setString(++scc, currDate1());
            pstmt.setInt(++scc, (info.getClientId()));
            pstmt.setInt(++scc, (info.getClientassetId()));
            pstmt.setInt(++scc, (info.getPositionId()));
            pstmt.setInt(++scc, (info.getGradeId()));
            pstmt.setInt(++scc, (info.getCheckpointId()));
            logger.info("updateCheckPoint :: " + pstmt.toString());

            cc = pstmt.executeUpdate();
        } catch (Exception exception) {
            print(this, "updateCheckPoint :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return cc;
    }

    public int checkDuplicacy(int checkpointId, int clientId, int clientassetId, int positionId, int gradeId, String name)
    {
        int ck = 0;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT i_checkpointid FROM t_checkpoint WHERE (i_clientid =? AND i_clientassetid =? AND i_positionid =? AND i_gradeid =? AND s_name =?) AND i_status =1 ");
        if (checkpointId > 0) {
            sb.append(" AND i_checkpointid != ? ");
        }
        String query = (sb.toString()).intern();
        sb.setLength(0);
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            int scc = 0;
            pstmt.setInt(++scc, clientId);
            pstmt.setInt(++scc, clientassetId);
            pstmt.setInt(++scc, positionId);
            pstmt.setInt(++scc, gradeId);
            pstmt.setString(++scc, name);
            if (checkpointId > 0) {
                pstmt.setInt(++scc, checkpointId);
            }
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

    public int deleteCheckPoint(int checkpointId, int userId, int status, String ipAddrStr, String iplocal) 
    {
        int cc = 0;
        try 
        {
            conn = getConnection();
            StringBuilder sb = new StringBuilder();
            sb.append("UPDATE t_checkpoint SET ");
            sb.append("i_status = ?, ");
            sb.append("i_userid = ?, ");
            sb.append("ts_moddate = ? ");
            sb.append("WHERE i_checkpointid = ? ");
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
            pstmt.setInt(++scc, checkpointId);

            cc = pstmt.executeUpdate();
        } catch (Exception exception) {
            print(this, "deleteCheckPoint :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        createHistoryAccess(null, userId, ipAddrStr, iplocal, "Data Deleted", 48, checkpointId);
        return cc;
    }

    public ArrayList getListForExcel(String search) 
    {
        ArrayList list = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT cp.i_checkpointid, cp.s_name, cp.s_desc, cp.i_flag, cp.i_status, t_client.s_name, t_clientasset.s_name, t_position.s_name, ");
        sb.append("t_grade.s_name FROM t_checkpoint AS cp ");
        sb.append("LEFT JOIN t_client ON cp.i_clientid = t_client.i_clientid ");
        sb.append("LEFT JOIN t_clientasset ON cp.i_clientassetid = t_clientasset.i_clientassetid ");
        sb.append("LEFT JOIN t_position ON cp.i_positionid = t_position.i_positionid ");
        sb.append("LEFT JOIN t_grade ON cp.i_gradeid = t_grade.i_gradeid ");
        sb.append("WHERE 0 = 0 ");
        if (search != null && !search.equals("")) {
            sb.append("AND (cp.s_name LIKE ? OR t_client.s_name LIKE ? OR t_clientasset.s_name LIKE ? OR t_position.s_name LIKE ? OR t_grade.s_name LIKE ?) ");
        }
        sb.append("ORDER BY cp.s_name ");
        String query = sb.toString().intern();
        sb.setLength(0);
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            int scc = 0;
            if (search != null && !search.equals("")) {
                pstmt.setString(++scc, "%" + (search) + "%");
                pstmt.setString(++scc, "%" + (search) + "%");
                pstmt.setString(++scc, "%" + (search) + "%");
                pstmt.setString(++scc, "%" + (search) + "%");
                pstmt.setString(++scc, "%" + (search) + "%");
            }
            Common.print(this, "getListForExcel :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String name, desc, clientname, clientassetname, positionname, grade;
            int checkpointId, status, flag;
            while (rs.next())
            {
                checkpointId = rs.getInt(1);
                name = rs.getString(2) != null ? rs.getString(2) : "";
                desc = rs.getString(3) != null ? rs.getString(3) : "";
                flag = rs.getInt(4);
                status = rs.getInt(5);
                clientname = rs.getString(6) != null ? rs.getString(6) : "";
                clientassetname = rs.getString(7) != null ? rs.getString(7) : "";
                positionname = rs.getString(8) != null ? rs.getString(8) : "";
                grade = rs.getString(9) != null ? rs.getString(9) : "";
                list.add(new CheckPointInfo(checkpointId, name, desc, clientname, clientassetname, positionname, grade, flag, status));
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
            coll.add(new CheckPointInfo(-1, "- Select -"));
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
                    ddlLabel = rs.getString(2) != null ? rs.getString(2) : "";
                    coll.add(new CheckPointInfo(ddlValue, ddlLabel));
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                close(conn, pstmt, rs);
            }
        } else {
            coll.add(new CheckPointInfo(-1, "- Select -"));
        }
        return coll;
    }

    public Collection getPostions(int clientassetId) 
    {
        Collection coll = new LinkedList();
        if (clientassetId > 0) 
        {
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT t_clientassetposition.i_positionid, t_position.s_name ");
            sb.append("FROM t_clientassetposition ");
            sb.append("LEFT JOIN t_position ON (t_position.i_positionid = t_clientassetposition.i_positionid) ");
            sb.append("WHERE t_clientassetposition.i_clientassetid = ? GROUP BY t_position.s_name ORDER BY t_position.s_name");
            String query = sb.toString();
            sb.setLength(0);
            coll.add(new CheckPointInfo(-1, "- Select -"));
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
                    ddlLabel = rs.getString(2) != null ? rs.getString(2) : "";
                    coll.add(new CheckPointInfo(ddlValue, ddlLabel));
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                close(conn, pstmt, rs);
            }
        } else {
            coll.add(new CheckPointInfo(-1, "- Select -"));
        }
        return coll;
    }

    public Collection getGrades(int clientassetId, String positionname) 
    {
        Collection coll = new LinkedList();
        if (clientassetId > 0) 
        {
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT t_grade.i_gradeid, t_grade.s_name ");
            sb.append("FROM t_clientassetposition ");
            sb.append("LEFT JOIN t_position ON (t_position.i_positionid = t_clientassetposition.i_positionid) ");
            sb.append("LEFT JOIN t_grade ON (t_grade.i_gradeid = t_position.i_gradeid) ");
            sb.append("WHERE t_clientassetposition.i_clientassetid = ? AND t_position.s_name = ? ORDER BY t_grade.s_name");
            String query = sb.toString();
            sb.setLength(0);
            coll.add(new CheckPointInfo(-1, "- Select -"));
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
                    coll.add(new CheckPointInfo(ddlValue, ddlLabel));
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                close(conn, pstmt, rs);
            }
        } else {
            coll.add(new CheckPointInfo(-1, "- Select -"));
        }
        return coll;
    }
}
