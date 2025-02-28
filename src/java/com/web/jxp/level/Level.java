package com.web.jxp.level;

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
import org.json.JSONArray;
import org.json.JSONObject;

public class Level extends Base {

    private static final Logger logger = Logger.getLogger(Class.class.getName());
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    public ArrayList getLevelByName(String search, int next, int count) {
        ArrayList levels = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("select i_levelid, s_name, i_status ");
        sb.append("FROM t_level where 0 = 0 ");
        if (search != null && !search.equals("")) {
            sb.append("and (s_name like ?) ");
        }
        sb.append(" order by i_status, s_name ");
        if (count > 0) {
            sb.append(" limit ").append(next * count).append(", ").append(count);
        }
        String query = (sb.toString()).intern();
        sb.setLength(0);

        sb.append("select count(1) FROM t_level where 0 = 0 ");
        if (search != null && !search.equals("")) {
            sb.append("and (s_name like ?) ");
        }
        String countquery = (sb.toString()).intern();
        sb.setLength(0);
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            int scc = 0;
            if (search != null && !search.equals("")) {
                pstmt.setString(++scc, "%" + (search) + "%");
            }
            logger.info("getLevelByName :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String name;
            int levelId, status;
            while (rs.next()) 
            {
                levelId = rs.getInt(1);
                name = rs.getString(2) != null ? rs.getString(2) : "";
                status = rs.getInt(3);
                levels.add(new LevelInfo(levelId, name, status, 0));
            }
            rs.close();

            pstmt = conn.prepareStatement(countquery);
            scc = 0;
            if (search != null && !search.equals("")) {
                pstmt.setString(++scc, "%" + (search) + "%");
            }
            print(this,"getLevelByName :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                levelId = rs.getInt(1);
                levels.add(new LevelInfo(levelId, "", 0, 0));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return levels;
    }

    public ArrayList getFinalRecord(ArrayList l, String colId, int tp) {
        ArrayList list = null;
        HashMap record = null;
        LevelInfo info;
        int total = 0;
        if (l != null) {
            total = l.size();
        }
        try {
            if (total > 0) {
                record = new HashMap();
                int i;
                for (i = 0; i < total; i++) {
                    info = (LevelInfo) l.get(i);
                    record.put(getInfoValue(info, colId), info);
                }
            }
            Map map = sortByName(record, tp);
            Iterator it = map.keySet().iterator();
            list = new ArrayList();
            LevelInfo rInfo;
            while (it.hasNext()) {
                String key = (String) it.next();
                int i;
                for (i = 0; i < total; i++) {
                    rInfo = (LevelInfo) l.get(i);
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
    public String getInfoValue(LevelInfo info, String i) {
        String infoval = "";
        if (i != null && i.equals("1")) {
            infoval = info.getName() != null ? info.getName() : "";
        }
        return infoval;
    }

    public LevelInfo getLevelDetailById(int levelId) {
        LevelInfo info = null;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT s_name, i_status FROM t_level where i_levelid = ? ");
        String query = sb.toString().intern();
        sb.setLength(0);
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, levelId);
            //print(this,"getLevelDetailById :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String name;
            int status;
            while (rs.next()) 
            {
                name = rs.getString(1) != null ? rs.getString(1) : "";
                status = rs.getInt(2);
                info = new LevelInfo(levelId, name, status, 0);
            }
        } catch (Exception exception) {
            print(this, "getLevelDetailById :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return info;
    }

    public LevelInfo getLevelDetailByIdforDetail(int levelId) 
    {
        LevelInfo info = null;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT s_name, i_status FROM t_level where i_levelid = ? ");
        String query = sb.toString().intern();
        sb.setLength(0);
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, levelId);
            //print(this,"getLevelDetailByIdforDetail :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String name;
            int status = 0;
            while (rs.next()) 
            {
                name = rs.getString(1) != null ? rs.getString(1) : "";
                status = rs.getInt(2);
                info = new LevelInfo(levelId, name, status, 0);
            }
            rs.close();
        } catch (Exception exception) {
            print(this, "getLevelDetailByIdforDetail :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return info;
    }

    public int createLevel(LevelInfo info) 
    {
        int levelId = 0;
        try 
        {
            StringBuilder sb = new StringBuilder();
            sb.append("insert into t_level ");
            sb.append("(s_name, i_status, i_userid, ts_regdate, ts_moddate) ");
            sb.append("values (?, ?, ?, ?, ?)");
            String query = (sb.toString()).intern();
            sb.setLength(0);

            conn = getConnection();
            pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            int scc = 0;
            pstmt.setString(++scc, (info.getName()));
            pstmt.setInt(++scc, info.getStatus());
            pstmt.setInt(++scc, info.getUserId());
            pstmt.setString(++scc, currDate1());
            pstmt.setString(++scc, currDate1());
            //print(this,"createLevel :: " + pstmt.toString());
            pstmt.executeUpdate();
            rs = pstmt.getGeneratedKeys();
            while (rs.next()) {
                levelId = rs.getInt(1);
            }
            createjson(conn);
        } catch (Exception exception) {
            print(this, "createLevel :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return levelId;
    }

    public int updateLevel(LevelInfo info) 
    {
        int cc = 0;
        try 
        {
            StringBuilder sb = new StringBuilder();
            sb.append("update t_level set ");
            sb.append("s_name = ?, ");
            sb.append("i_status = ?, ");
            sb.append("i_userid = ?, ");
            sb.append("ts_moddate = ? ");
            sb.append("where i_levelid = ? ");
            String query = (sb.toString()).intern();
            sb.setLength(0);

            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            int scc = 0;
            pstmt.setString(++scc, info.getName());
            pstmt.setInt(++scc, info.getStatus());
            pstmt.setInt(++scc, info.getUserId());
            pstmt.setString(++scc, currDate1());
            pstmt.setInt(++scc, info.getLevelId());
            //print(this,"updateLevel :: " + pstmt.toString());
            cc = pstmt.executeUpdate();
            createjson(conn);
        } catch (Exception exception) {
            print(this, "updateLevel :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return cc;
    }

    public int checkDuplicacy(int levelId, String name) {
        int ck = 0;
        StringBuilder sb = new StringBuilder();
        sb.append("select i_levelid FROM t_level where s_name = ? and i_status in (1, 2)");
        if (levelId > 0) {
            sb.append(" and i_levelid != ? ");
        }
        String query = (sb.toString()).intern();
        sb.setLength(0);
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            int scc = 0;
            pstmt.setString(++scc, name);
            if (levelId > 0) {
                pstmt.setInt(++scc, levelId);
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

    public int deleteLevel(int levelId, int userId, int status, String ipAddrStr, String iplocal) {
        int cc = 0;
        try 
        {
            conn = getConnection();
            StringBuilder sb = new StringBuilder();
            sb.append("update t_level set ");
            sb.append("i_status = ?, ");
            sb.append("i_userid = ?, ");
            sb.append("ts_moddate = ? ");
            sb.append("where i_levelid = ? ");
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
            pstmt.setInt(++scc, levelId);

            cc = pstmt.executeUpdate();
            createjson(conn);
        } catch (Exception exception) {
            print(this, "deleteLevel :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        createHistoryAccess(null, userId, ipAddrStr, iplocal, "Data Deleted", 63, levelId);
        return cc;
    }

    public ArrayList getListForExcel(String search) 
    {
        ArrayList list = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("select s_name, i_status ");
        sb.append("FROM t_level where 0 = 0 ");
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
            int status;
            while (rs.next()) 
            {
                name = rs.getString(1) != null ? rs.getString(1) : "";
                status = rs.getInt(2);
                list.add(new LevelInfo(0, name, status, 0));
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

    public void createjson(Connection conn) 
    {
        String query = ("Select i_levelid, s_name FROM t_level where i_status = 1 order by  s_name ");
        try 
        {
            pstmt = conn.prepareStatement(query);
            rs = pstmt.executeQuery();
            int id;
            String name;
            JSONArray jarr = new JSONArray();
            while (rs.next()) 
            {
                id = rs.getInt(1);
                name = rs.getString(2) != null ? rs.getString(2) : "";
                JSONObject jobj = new JSONObject();
                jobj.put("id", id);
                jobj.put("name", name);
                jarr.put(jobj);
            }
            rs.close();
            String str = jarr.toString();
            String filePath = getMainPath("json_path");
            writeHTMLFile(str, filePath, "level.json");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(null, pstmt, rs);
        }
    }

    public Collection getLevels() 
    {
        Collection coll = new LinkedList();
        coll.add(new LevelInfo(-1, "- Select -"));
        String xml_path = getMainPath("json_path");
        String str = readHTMLFile("level.json", xml_path);
        JSONArray arr = new JSONArray(str);
        if (arr != null)
        {
            int len = arr.length();
            for (int i = 0; i < len; i++) 
            {
                JSONObject jobj = arr.optJSONObject(i);
                if (jobj != null) 
                {
                    coll.add(new LevelInfo(jobj.optInt("id"), jobj.optString("name")));
                }
            }
        }
        return coll;
    }
}
