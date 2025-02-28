package com.web.jxp.rejectionreason;

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
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

public class Rejectionreason extends Base {

    private static final Logger logger = Logger.getLogger(Class.class.getName());
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    public ArrayList getRejectionreasonByName(String search, int next, int count) {
        ArrayList rejectionreasons = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("select i_rejectionreasonid, s_name, i_rejectiontype, i_status ");
        sb.append("FROM t_rejectionreason where 0 = 0 ");
        if (search != null && !search.equals("")) {
            sb.append("and (s_name like ?) ");
        }
        sb.append(" order by i_status, s_name ");
        if (count > 0) {
            sb.append(" limit ").append(next * count).append(", ").append(count);
        }
        String query = (sb.toString()).intern();
        sb.setLength(0);

        sb.append("select count(1) FROM t_rejectionreason where 0 = 0 ");
        if (search != null && !search.equals("")) {
            sb.append("and (s_name like ?) ");
        }
        String countquery = (sb.toString()).intern();
        sb.setLength(0);
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            int scc = 0;
            if (search != null && !search.equals("")) {
                pstmt.setString(++scc, "%" + (search) + "%");
            }
            logger.log(Level.INFO, "getRejectionreasonByName :: {0}", pstmt.toString());
            rs = pstmt.executeQuery();
            String name;
            int rejectionreasonId, status, type;
            while (rs.next()) 
            {
                rejectionreasonId = rs.getInt(1);
                name = rs.getString(2) != null ? rs.getString(2) : "";
                type = rs.getInt(3);
                status = rs.getInt(4);
                rejectionreasons.add(new RejectionreasonInfo(rejectionreasonId, name, type, status, 0, 0, ""));
            }
            rs.close();

            pstmt = conn.prepareStatement(countquery);
            scc = 0;
            if (search != null && !search.equals("")) {
                pstmt.setString(++scc, "%" + (search) + "%");
            }
            print(this,"getRejectionreasonByName :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                rejectionreasonId = rs.getInt(1);
                rejectionreasons.add(new RejectionreasonInfo(rejectionreasonId, "", 0, 0, 0, 0, ""));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return rejectionreasons;
    }

    public ArrayList getFinalRecord(ArrayList l, String colId, int tp) {
        ArrayList list = null;
        HashMap record = null;
        RejectionreasonInfo info;
        int total = 0;
        if (l != null) {
            total = l.size();
        }
        try {
            if (total > 0) {
                record = new HashMap();
                int i;
                for (i = 0; i < total; i++) {
                    info = (RejectionreasonInfo) l.get(i);
                    record.put(getInfoValue(info, colId), info);
                }
            }
            Map map = sortByName(record, tp);
            Iterator it = map.keySet().iterator();
            list = new ArrayList();
            RejectionreasonInfo rInfo;
            while (it.hasNext()) {
                String key = (String) it.next();
                int i;
                for (i = 0; i < total; i++) {
                    rInfo = (RejectionreasonInfo) l.get(i);
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
    public String getInfoValue(RejectionreasonInfo info, String i) {
        String infoval = "";
        if (i != null && i.equals("1")) {
            infoval = info.getName() != null ? info.getName() : "";
        }
        return infoval;
    }

    public RejectionreasonInfo getRejectionreasonDetailById(int rejectionreasonId) {
        RejectionreasonInfo info = null;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT s_name, i_rejectiontype, i_status FROM t_rejectionreason where i_rejectionreasonid = ? ");
        String query = sb.toString().intern();
        sb.setLength(0);
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, rejectionreasonId);
            //print(this,"getRejectionreasonDetailById :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String name;
            int status, rejectiontype;
            while (rs.next()) 
            {
                name = rs.getString(1) != null ? rs.getString(1) : "";
                rejectiontype = rs.getInt(2);
                status = rs.getInt(3);
                info = new RejectionreasonInfo(rejectionreasonId, name, rejectiontype, status, 0, 0);
            }
        } catch (Exception exception) {
            print(this, "getRejectionreasonDetailById :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return info;
    }

    public RejectionreasonInfo getRejectionreasonDetailByIdforDetail(int rejectionreasonId) {
        RejectionreasonInfo info = null;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT s_name, i_rejectiontype,i_status FROM t_rejectionreason where i_rejectionreasonid = ? ");
        String query = sb.toString().intern();
        sb.setLength(0);
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, rejectionreasonId);
            print(this, "getRejectionreasonDetailByIdforDetail :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String name;
            int status = 0, rejectiontype;
            while (rs.next()) 
            {
                name = rs.getString(1) != null ? rs.getString(1) : "";
                rejectiontype = rs.getInt(2);
                status = rs.getInt(3);
                info = new RejectionreasonInfo(rejectionreasonId, name, rejectiontype, status, 0, 0, 0);
            }
            rs.close();
        } catch (Exception exception) {
            print(this, "getRejectionreasonDetailByIdforDetail :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return info;
    }

    public int createRejectionreason(RejectionreasonInfo info) {
        int rejectionreasonId = 0;
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("insert into t_rejectionreason ");
            sb.append("(s_name, i_rejectiontype, i_status, i_userid, ts_regdate, ts_moddate) ");
            sb.append("values (?, ?, ?, ?, ?, ?)");
            String query = (sb.toString()).intern();
            sb.setLength(0);

            conn = getConnection();
            pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            int scc = 0;
            pstmt.setString(++scc, (info.getName()));
            pstmt.setInt(++scc, info.getRejectionType());
            pstmt.setInt(++scc, info.getStatus());
            pstmt.setInt(++scc, info.getUserId());
            pstmt.setString(++scc, currDate1());
            pstmt.setString(++scc, currDate1());
            print(this, "createRejectionreason :: " + pstmt.toString());
            pstmt.executeUpdate();
            rs = pstmt.getGeneratedKeys();
            while (rs.next()) {
                rejectionreasonId = rs.getInt(1);
            }
            createjson(conn);
        } catch (Exception exception) {
            print(this, "createRejectionreason :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return rejectionreasonId;
    }

    public int updateRejectionreason(RejectionreasonInfo info) {
        int cc = 0;
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("update t_rejectionreason set ");
            sb.append("s_name = ?, ");
            sb.append("i_rejectiontype = ?, ");
            sb.append("i_status = ?, ");
            sb.append("i_userid = ?, ");
            sb.append("ts_moddate = ? ");
            sb.append("where i_rejectionreasonid = ? ");
            String query = (sb.toString()).intern();
            sb.setLength(0);

            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            int scc = 0;
            pstmt.setString(++scc, info.getName());
            pstmt.setInt(++scc, info.getRejectionType());
            pstmt.setInt(++scc, info.getStatus());
            pstmt.setInt(++scc, info.getUserId());
            pstmt.setString(++scc, currDate1());
            pstmt.setInt(++scc, info.getRejectionreasonId());
            print(this, "updateRejectionreason :: " + pstmt.toString());
            cc = pstmt.executeUpdate();
            createjson(conn);
        } catch (Exception exception) {
            print(this, "updateRejectionreason :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return cc;
    }

    public int checkDuplicacy(int rejectionreasonId, String name) {
        int ck = 0;
        StringBuilder sb = new StringBuilder();
        sb.append("select i_rejectionreasonid FROM t_rejectionreason where s_name = ? and i_status in (1, 2)");
        if (rejectionreasonId > 0) {
            sb.append(" and i_rejectionreasonid != ? ");
        }
        String query = (sb.toString()).intern();
        sb.setLength(0);
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            int scc = 0;
            pstmt.setString(++scc, name);
            if (rejectionreasonId > 0) {
                pstmt.setInt(++scc, rejectionreasonId);
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

    public Collection getRejectionreasons() {
        Collection coll = new LinkedList();
        coll.add(new RejectionreasonInfo(-1, "- Select -"));
        String xml_path = getMainPath("json_path");
        String str = readHTMLFile("rejectionreason.json", xml_path);
        JSONArray arr = new JSONArray(str);
        if (arr != null) {
            int len = arr.length();
            for (int i = 0; i < len; i++) {
                JSONObject jobj = arr.optJSONObject(i);
                if (jobj != null) {
                    coll.add(new RejectionreasonInfo(jobj.optInt("id"), jobj.optString("name")));
                }
            }
        }
        return coll;
    }

    public int deleteRejectionreason(int rejectionreasonId, int userId, int status, String ipAddrStr, String iplocal) {
        int cc = 0;
        try {
            conn = getConnection();
            StringBuilder sb = new StringBuilder();
            sb.append("update t_rejectionreason set ");
            sb.append("i_status = ?, ");
            sb.append("i_userid = ?, ");
            sb.append("ts_moddate = ? ");
            sb.append("where i_rejectionreasonid = ? ");
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
            pstmt.setInt(++scc, rejectionreasonId);

            cc = pstmt.executeUpdate();
            createjson(conn);
        } catch (Exception exception) {
            print(this, "deleteRejectionreason :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        createHistoryAccess(null, userId, ipAddrStr, iplocal, "Data updated", 52, rejectionreasonId);
        return cc;
    }

    public ArrayList getListForExcel(String search) {
        ArrayList list = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("select s_name, i_status, i_rejectiontype ");
        sb.append("FROM t_rejectionreason where 0 = 0 ");
        if (search != null && !search.equals("")) {
            sb.append("and (s_name like ?) ");
        }
        sb.append(" order by i_status, s_name ");
        String query = sb.toString().intern();
        sb.setLength(0);
        try
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            int scc = 0;
            if (search != null && !search.equals("")) {
                pstmt.setString(++scc, "%" + (search) + "%");
            }
            Common.print(this, "getListForExcel :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String name;
            int status, rejectiontype;
            while (rs.next()) 
            {
                name = rs.getString(1) != null ? rs.getString(1) : "";
                status = rs.getInt(2);
                rejectiontype = rs.getInt(3);
                list.add(new RejectionreasonInfo(0, name, status, rejectiontype));
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
        String query = ("Select i_rejectionreasonid, s_name, i_rejectiontype FROM t_rejectionreason where i_status = 1 order by  s_name ");
        try
        {
            pstmt = conn.prepareStatement(query);
            rs = pstmt.executeQuery();
            int id, rejectiontype;
            String name;
            JSONArray jarr = new JSONArray();
            while (rs.next())
            {
                id = rs.getInt(1);
                name = rs.getString(2) != null ? rs.getString(2) : "";
                rejectiontype = rs.getInt(3);
                JSONObject jobj = new JSONObject();
                jobj.put("id", id);
                jobj.put("name", name);
                jobj.put("rejectiontype", rejectiontype);
                jarr.put(jobj);
            }
            rs.close();
            String str = jarr.toString();
            String filePath = getMainPath("json_path");
            writeHTMLFile(str, filePath, "rejectionreason.json");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(null, pstmt, rs);
        }
    }
}
