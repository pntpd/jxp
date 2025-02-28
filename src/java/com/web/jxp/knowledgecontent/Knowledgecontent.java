package com.web.jxp.knowledgecontent;

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
import java.io.File;
import java.util.Collection;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

public class Knowledgecontent extends Base {

    private static final Logger logger = Logger.getLogger(Class.class.getName());
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    public ArrayList getKnowledgecontentByName(String search, int next, int count)
    {
        ArrayList createtraining = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT t_kcategory.i_kcategoryid, t_kcategory.s_name, t_kcategory.i_status , t1.ct , t2.ct ");
        sb.append("FROM t_kcategory ");
        sb.append("LEFT JOIN (SELECT i_kcategoryid, COUNT(1) AS ct FROM t_ksubcategory WHERE i_status= 1 GROUP BY i_kcategoryid) AS t1 ON (t_kcategory.i_kcategoryid = t1.i_kcategoryid) ");
        sb.append("LEFT JOIN (SELECT t_topic.i_kcategoryid, COUNT(1) AS ct FROM t_topic LEFT JOIN t_ksubcategory ON (t_ksubcategory.i_ksubcategoryid = t_topic.i_ksubcategoryid) WHERE t_topic.i_status= 1 AND t_ksubcategory.i_status = 1 GROUP BY t_topic.i_kcategoryid) AS t2 ON (t_kcategory.i_kcategoryid = t2.i_kcategoryid)  ");
        sb.append("WHERE 0=0 ");
        if (search != null && !search.equals("")) {
            sb.append("AND (t_kcategory.s_name LIKE ?) ");
        }
        sb.append("ORDER BY t_kcategory.i_status, t_kcategory.s_name ");
        if (count > 0) {
            sb.append("LIMIT ").append(next * count).append(", ").append(count);
        }
        String query = (sb.toString()).intern();
        sb.setLength(0);

        sb.append("SELECT COUNT(1) FROM t_kcategory WHERE 0 = 0 ");
        if (search != null && !search.equals("")) {
            sb.append("AND (t_kcategory.s_name LIKE ?) ");
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
            logger.log(Level.INFO, "getKnowledgecontentByName :: {0}", pstmt.toString());
            rs = pstmt.executeQuery();
            String name;
            int categoryId, status, subcategorycount, coursecount;
            while (rs.next()) 
            {
                categoryId = rs.getInt(1);
                name = rs.getString(2) != null ? rs.getString(2) : "";
                status = rs.getInt(3);
                subcategorycount = rs.getInt(4);
                coursecount = rs.getInt(5);
                createtraining.add(new KnowledgecontentInfo(categoryId, name, status, subcategorycount, coursecount));
            }
            rs.close();

            pstmt = conn.prepareStatement(countquery);
            scc = 0;
            if (search != null && !search.equals("")) {
                pstmt.setString(++scc, "%" + (search) + "%");
            }
            print(this,"getKnowledgecontentByName :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                categoryId = rs.getInt(1);
                createtraining.add(new KnowledgecontentInfo(categoryId, "", 0, 0, 0));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return createtraining;
    }

    public ArrayList getFinalRecord(ArrayList l, String colId, int tp) {
        ArrayList list = null;
        HashMap record = null;
        KnowledgecontentInfo info;
        int total = 0;
        if (l != null) {
            total = l.size();
        }
        try {
            if (total > 0) {
                record = new HashMap();
                int i;
                for (i = 0; i < total; i++) {
                    info = (KnowledgecontentInfo) l.get(i);
                    record.put(getInfoValue(info, colId), info);
                }
            }
            Map map;
            if (colId.equals("1")) {
                map = sortByName(record, tp);
            } else {
                map = sortById(record, tp);
            }
            Iterator it = map.keySet().iterator();
            list = new ArrayList();
            KnowledgecontentInfo rInfo;
            while (it.hasNext()) {
                String key = (String) it.next();
                int i;
                for (i = 0; i < total; i++) {
                    rInfo = (KnowledgecontentInfo) l.get(i);
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
    public String getInfoValue(KnowledgecontentInfo info, String i) {
        String infoval = "";
        if (i != null && i.equals("1")) {
            infoval = info.getName() != null ? info.getName() : "";
        }
        if (i != null && i.equals("2")) {
            infoval = info.getSubcategorycount() + "";
        }
        if (i != null && i.equals("3")) {
            infoval = info.getCoursecount() + "";
        }
        return infoval;
    }

    public KnowledgecontentInfo getKnowledgecontentDetailById(int categoryId) 
    {
        KnowledgecontentInfo info = null;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT s_name, i_status, s_description FROM t_kcategory WHERE i_kcategoryid =? ");
        String query = sb.toString().intern();
        sb.setLength(0);
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, categoryId);
            //print(this,"getKnowledgecontentDetailById :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String name, description;
            int status;
            while (rs.next()) 
            {
                name = rs.getString(1) != null ? rs.getString(1) : "";
                status = rs.getInt(2);
                description = rs.getString(3) != null ? rs.getString(3) : "";
                info = new KnowledgecontentInfo(categoryId, name, status, description, 0, "", 0);
            }
        } catch (Exception exception) {
            print(this, "getKnowledgecontentDetailById :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return info;
    }

    public KnowledgecontentInfo getKnowledgecontentDetailByIdforDetail(int categoryId) {
        KnowledgecontentInfo info = null;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT s_name, i_status, s_description FROM t_kcategory WHERE i_kcategoryid =? ");
        String query = sb.toString().intern();
        sb.setLength(0);
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, categoryId);
            //print(this,"getKnowledgecontentDetailByIdforDetail :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String name, description;
            int status = 0;
            while (rs.next()) 
            {
                name = rs.getString(1) != null ? rs.getString(1) : "";
                status = rs.getInt(2);
                description = rs.getString(3) != null ? rs.getString(3) : "";
                info = new KnowledgecontentInfo(categoryId, name, status, description, 0, "", 0);
            }
            rs.close();
        } catch (Exception exception) {
            print(this, "getKnowledgecontentDetailByIdforDetail :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return info;
    }

    public int createKnowledgecontent(KnowledgecontentInfo info) {
        int createtrainingId = 0;
        try
        {
            StringBuilder sb = new StringBuilder();
            sb.append("INSERT INTO t_kcategory ");
            sb.append("(s_name, s_description, i_status, i_userid, ts_regdate, ts_moddate) ");
            sb.append("VALUES (?, ?, ?, ?, ?, ?)");
            String query = (sb.toString()).intern();
            sb.setLength(0);

            conn = getConnection();
            pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            int scc = 0;
            pstmt.setString(++scc, (info.getName()));
            pstmt.setString(++scc, (info.getDescription()));
            pstmt.setInt(++scc, info.getStatus());
            pstmt.setInt(++scc, info.getUserId());
            pstmt.setString(++scc, currDate1());
            pstmt.setString(++scc, currDate1());
            //print(this,"createKnowledgecontent :: " + pstmt.toString());
            pstmt.executeUpdate();
            rs = pstmt.getGeneratedKeys();
            while (rs.next()) 
            {
                createtrainingId = rs.getInt(1);
            }
        } catch (Exception exception) {
            print(this, "createKnowledgecontent :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return createtrainingId;
    }

    public int updateKnowledgecontent(KnowledgecontentInfo info) {
        int cc = 0;
        try 
        {
            StringBuilder sb = new StringBuilder();
            sb.append("UPDATE t_kcategory SET ");
            sb.append("s_name =?, ");
            sb.append("s_description =?, ");
            sb.append("i_status =?, ");
            sb.append("i_userid =?, ");
            sb.append("ts_moddate =? ");
            sb.append("WHERE i_kcategoryid =? ");
            String query = (sb.toString()).intern();
            sb.setLength(0);

            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            int scc = 0;
            pstmt.setString(++scc, info.getName());
            pstmt.setString(++scc, (info.getDescription()));
            pstmt.setInt(++scc, info.getStatus());
            pstmt.setInt(++scc, info.getUserId());
            pstmt.setString(++scc, currDate1());
            pstmt.setInt(++scc, info.getCategoryId());
            //print(this,"updateKnowledgecontent :: " + pstmt.toString());
            cc = pstmt.executeUpdate();
        } catch (Exception exception) {
            print(this, "updateKnowledgecontent :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return cc;
    }

    public int checkDuplicacy(int categoryId, String name) {
        int ck = 0;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT i_kcategoryid FROM t_kcategory WHERE s_name =? AND i_status IN (1, 2) ");
        if (categoryId > 0) {
            sb.append("AND i_kcategoryid !=? ");
        }
        String query = (sb.toString()).intern();
        sb.setLength(0);
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            int scc = 0;
            pstmt.setString(++scc, name);
            if (categoryId > 0) {
                pstmt.setInt(++scc, categoryId);
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

    public Collection getKnowledgecontent()
    {
        Collection coll = new LinkedList();
        coll.add(new KnowledgecontentInfo(-1, "- Select -"));
        String xml_path = getMainPath("json_path");
        String str = readHTMLFile("knowledgecontent.json", xml_path);
        JSONArray arr = new JSONArray(str);
        if (arr != null) 
        {
            int len = arr.length();
            for (int i = 0; i < len; i++) 
            {
                JSONObject jobj = arr.optJSONObject(i);
                if (jobj != null) 
                {
                    coll.add(new KnowledgecontentInfo(jobj.optInt("id"), jobj.optString("name")));
                }
            }
        }
        return coll;
    }

    public int deleteKnowledgecontent(int createtrainingId, int userId, int status, String ipAddrStr, String iplocal) {
        int cc = 0;
        try 
        {
            conn = getConnection();
            StringBuilder sb = new StringBuilder();
            sb.append("UPDATE t_kcategory SET ");
            sb.append("i_status =?, ");
            sb.append("i_userid =?, ");
            sb.append("ts_moddate =? ");
            sb.append("WHERE i_kcategoryid =? ");
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
            pstmt.setInt(++scc, createtrainingId);

            cc = pstmt.executeUpdate();
        } catch (Exception exception) {
            print(this, "deleteKnowledgecontent :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        createHistoryAccess(null, userId, ipAddrStr, iplocal, "Data updated", 93, createtrainingId);
        return cc;
    }

    public ArrayList getListForExcel()
    {
        ArrayList list = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT t_kcategory.s_name, t_ksubcategory.s_name, t_topic.s_name ");
        sb.append("FROM t_topic ");
        sb.append("LEFT JOIN t_ksubcategory ON (t_topic.i_ksubcategoryid = t_ksubcategory.i_ksubcategoryid) ");
        sb.append("LEFT JOIN t_kcategory ON (t_ksubcategory.i_kcategoryid = t_kcategory.i_kcategoryid) ");
        sb.append("WHERE t_topic.i_status =1 ");
        sb.append("ORDER BY t_kcategory.s_name, t_ksubcategory.s_name, t_topic.s_name ");
        String query = sb.toString().intern();
        sb.setLength(0);
        try
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            int scc = 0;
            Common.print(this, "getListForExcel :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String categoryname, subcategoryname, coursename;
            while (rs.next()) 
            {
                categoryname = rs.getString(1) != null ? rs.getString(1) : "";
                subcategoryname = rs.getString(2) != null ? rs.getString(2) : "";
                coursename = rs.getString(3) != null ? rs.getString(3) : "";
                list.add(new KnowledgecontentInfo(categoryname, subcategoryname, coursename));
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
        String query = ("SELECT i_kcategoryid, s_name FROM t_kcategory WHERE i_status =1 ORDER BY s_name ");
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
            writeHTMLFile(str, filePath, "knowledgecontent.json");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(null, pstmt, rs);
        }
    }

    public int getcategoryMaxId() {
        int id = 0;
        String query = "SELECT MAX(i_kcategoryid) FROM t_kcategory";
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                id = rs.getInt(1);
            }
            id = id + 1;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return id;
    }

    public ArrayList getsubcategoryDetailByIdforDetail(int categoryId) 
    {
        ArrayList list = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT t_ksubcategory.s_name, t_ksubcategory.i_status, t_ksubcategory.s_description, t1.ct, t_ksubcategory.i_ksubcategoryid FROM t_ksubcategory ");
        sb.append("LEFT JOIN (SELECT i_ksubcategoryid, COUNT(1) AS ct FROM t_topic WHERE i_status = 1 GROUP BY i_ksubcategoryid) AS t1 ON (t_ksubcategory.i_ksubcategoryid = t1.i_ksubcategoryid) ");
        sb.append("WHERE i_kcategoryid =? ORDER BY t_ksubcategory.i_status, t_ksubcategory.s_name ");
        String query = sb.toString().intern();
        sb.setLength(0);
        try
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, categoryId);
            print(this,"getsubcategoryDetailByIdforDetail :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String name, description;
            int status = 0, coursecount = 0, subcategoryId = 0;
            while (rs.next()) 
            {
                name = rs.getString(1) != null ? rs.getString(1) : "";
                status = rs.getInt(2);
                description = rs.getString(3) != null ? rs.getString(3) : "";
                coursecount = rs.getInt(4);
                subcategoryId = rs.getInt(5);
                list.add(new KnowledgecontentInfo(categoryId, name, status, description, coursecount, "", subcategoryId));
            }
            rs.close();
        } catch (Exception exception) {
            print(this, "getsubcategoryDetailByIdforDetail :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return list;
    }

    public int getsubcategoryMaxId() {
        int id = 0;
        String query = "SELECT MAX(i_ksubcategoryid) FROM t_ksubcategory";
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                id = rs.getInt(1);
            }
            id = id + 1;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return id;
    }

    public int checkDuplicacysubcategory(int categoryId, String name, int subcategoryId) {
        int ck = 0;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT i_ksubcategoryid FROM t_ksubcategory WHERE s_name =? AND i_kcategoryid =? AND i_status IN (1, 2)");
        if (subcategoryId > 0) {
            sb.append("AND i_ksubcategoryid !=? ");
        }
        String query = (sb.toString()).intern();
        sb.setLength(0);
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            int scc = 0;
            pstmt.setString(++scc, name);
            pstmt.setInt(++scc, categoryId);
            if (subcategoryId > 0) {
                pstmt.setInt(++scc, subcategoryId);
            }
            //print(this,"checkDuplicacysubcategory :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ck = 1;
            }
        } catch (Exception exception) {
            print(this, "checkDuplicacysubcategory :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return ck;
    }

    public int createsubCategory(KnowledgecontentInfo info) {
        int categoryId = 0;
        try
        {
            StringBuilder sb = new StringBuilder();
            sb.append("INSERT INTO t_ksubcategory ");
            sb.append("(s_name, s_description, i_kcategoryid, i_status, i_userid, ts_regdate, ts_moddate) ");
            sb.append("VALUES (?, ?, ?, ?, ?, ?, ?)");
            String query = (sb.toString()).intern();
            sb.setLength(0);

            conn = getConnection();
            pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            int scc = 0;
            pstmt.setString(++scc, (info.getName()));
            pstmt.setString(++scc, (info.getDescription()));
            pstmt.setInt(++scc, info.getCategoryId());
            pstmt.setInt(++scc, info.getStatus());
            pstmt.setInt(++scc, info.getUserId());
            pstmt.setString(++scc, currDate1());
            pstmt.setString(++scc, currDate1());
            //print(this,"createKnowledgecontent :: " + pstmt.toString());
            pstmt.executeUpdate();
            rs = pstmt.getGeneratedKeys();
            while (rs.next())
            {
                categoryId = rs.getInt(1);
            }
        } catch (Exception exception) {
            print(this, "createKnowledgecontent :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return categoryId;
    }

    public int updatesubCategory(KnowledgecontentInfo info) {
        int cc = 0;
        try 
        {
            StringBuilder sb = new StringBuilder();
            sb.append("UPDATE t_ksubcategory SET ");
            sb.append("s_name =?, ");
            sb.append("s_description =?, ");
            sb.append("i_status =?, ");
            sb.append("i_userid =?, ");
            sb.append("ts_moddate =? ");
            sb.append("WHERE i_kcategoryid =? AND i_ksubcategoryid =? ");
            String query = (sb.toString()).intern();
            sb.setLength(0);

            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            int scc = 0;
            pstmt.setString(++scc, info.getName());
            pstmt.setString(++scc, (info.getDescription()));
            pstmt.setInt(++scc, info.getStatus());
            pstmt.setInt(++scc, info.getUserId());
            pstmt.setString(++scc, currDate1());
            pstmt.setInt(++scc, info.getCategoryId());
            pstmt.setInt(++scc, info.getSubcategoryId());
            //print(this,"updatesubCategory :: " + pstmt.toString());
            cc = pstmt.executeUpdate();
        } catch (Exception exception) {
            print(this, "updatesubCategory :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return cc;
    }

    public ArrayList getcourseslistByIdforDetail(int categoryId)
    {
        ArrayList list = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT t_topic.s_name, t_topic.i_status, t_topic.s_description, t_ksubcategory.i_ksubcategoryid, t_ksubcategory.s_name, ");
        sb.append("t_topic.i_topicid, t1.ct, t2.names FROM t_topic ");
        sb.append("LEFT JOIN t_ksubcategory ON (t_ksubcategory.i_ksubcategoryid = t_topic.i_ksubcategoryid) ");
        sb.append("LEFT JOIN (SELECT i_topicid, COUNT(1) AS ct FROM t_topicattachment GROUP BY i_topicid) AS t1 ON (t1.i_topicid = t_topic.i_topicid) ");
        sb.append("LEFT JOIN (SELECT t_topic.i_topicid, GROUP_CONCAT(DISTINCT t_assettype.s_name ORDER BY t_assettype.s_name SEPARATOR ', ') AS names FROM t_topic ");
        sb.append("LEFT JOIN t_assettype ON find_in_set(t_assettype.i_assettypeid, t_topic.s_assettypeids) GROUP BY t_topic.i_topicid) AS t2 ON (t2.i_topicid = t_topic.i_topicid) ");
        sb.append("WHERE t_topic.i_kcategoryid =? ORDER BY t_ksubcategory.s_name, t_topic.s_name ");
        String query = sb.toString().intern();
        sb.setLength(0);
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, categoryId);
            //print(this,"getcourseslistByIdforDetail :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String coursename, description, subcategoryname, assettypeNames;
            int status = 0, subcategoryId = 0, courseId = 0, count = 0;
            while (rs.next()) 
            {
                coursename = rs.getString(1) != null ? rs.getString(1) : "";
                status = rs.getInt(2);
                description = rs.getString(3) != null ? rs.getString(3) : "";
                subcategoryId = rs.getInt(4);
                subcategoryname = rs.getString(5) != null ? rs.getString(5) : "";
                courseId = rs.getInt(6);
                count = rs.getInt(7);
                assettypeNames = rs.getString(8) != null ? rs.getString(8) : "";
                list.add(new KnowledgecontentInfo(categoryId, coursename, status, description, subcategoryname, subcategoryId, courseId, count, assettypeNames, 0));
            }
            rs.close();
        } catch (Exception exception) {
            print(this, "getcourseslistByIdforDetail :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return list;
    }

    public Collection getSubcategory(int categoryId) 
    {
        Collection coll = new LinkedList();
        if (categoryId > 0) 
        {
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT i_ksubcategoryid, s_name FROM t_ksubcategory WHERE i_kcategoryid =? AND i_status =1 ");
            sb.append("ORDER BY s_name ");
            String query = sb.toString();
            sb.setLength(0);
            coll.add(new KnowledgecontentInfo(-1, " Select Module "));
            try
            {
                conn = getConnection();
                pstmt = conn.prepareStatement(query);
                pstmt.setInt(1, categoryId);
                logger.info("getSubcategory :: " + pstmt.toString());
                rs = pstmt.executeQuery();
                int ddlValue;
                String ddlLabel;
                while (rs.next()) 
                {
                    ddlValue = rs.getInt(1);
                    ddlLabel = rs.getString(2) != null ? rs.getString(2) : "";
                    coll.add(new KnowledgecontentInfo(ddlValue, ddlLabel));
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                close(conn, pstmt, rs);
            }
        } else {
            coll.add(new KnowledgecontentInfo(-1, " Select Subcategory "));
        }
        return coll;
    }

    public int getcourseMaxId() {
        int id = 0;
        String query = "SELECT MAX(i_topicid) FROM t_topic";
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                id = rs.getInt(1);
            }
            id = id + 1;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return id;
    }

    public int createcourse(KnowledgecontentInfo info) 
    {
        int categoryId = 0;
        try 
        {
            StringBuilder sb = new StringBuilder();
            sb.append("INSERT INTO t_topic ");
            sb.append("(s_name, s_description, i_kcategoryid, i_ksubcategoryid, s_assettypeids, i_status, i_userid, ts_regdate, ts_moddate) ");
            sb.append("VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");
            String query = (sb.toString()).intern();
            sb.setLength(0);

            conn = getConnection();
            pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            int scc = 0;
            pstmt.setString(++scc, (info.getTopicname()));
            pstmt.setString(++scc, (info.getDescription()));
            pstmt.setInt(++scc, info.getCategoryId());
            pstmt.setInt(++scc, info.getSubcategoryId());
            pstmt.setString(++scc, info.getAssettypeids());
            pstmt.setInt(++scc, info.getStatus());
            pstmt.setInt(++scc, info.getUserId());
            pstmt.setString(++scc, currDate1());
            pstmt.setString(++scc, currDate1());
            //print(this,"createcourse :: " + pstmt.toString());
            pstmt.executeUpdate();
            rs = pstmt.getGeneratedKeys();
            while (rs.next()) {
                categoryId = rs.getInt(1);
            }
        } catch (Exception exception) {
            print(this, "createcourse :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return categoryId;
    }

    public int updatecourse(KnowledgecontentInfo info) 
    {
        int cc = 0;
        try 
        {
            StringBuilder sb = new StringBuilder();
            sb.append("UPDATE t_topic SET ");
            sb.append("s_name =?, ");
            sb.append("s_description =?, ");
            sb.append("i_ksubcategoryid =?, ");
            sb.append("s_assettypeids =?, ");
            sb.append("i_status =?, ");
            sb.append("i_userid =?, ");
            sb.append("ts_moddate =? ");
            sb.append("WHERE i_kcategoryid =? AND i_topicid =? ");
            String query = (sb.toString()).intern();
            sb.setLength(0);

            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            int scc = 0;
            pstmt.setString(++scc, (info.getTopicname()));
            pstmt.setString(++scc, (info.getDescription()));
            pstmt.setInt(++scc, info.getSubcategoryId());
            pstmt.setString(++scc, info.getAssettypeids());
            pstmt.setInt(++scc, info.getStatus());
            pstmt.setInt(++scc, info.getUserId());
            pstmt.setString(++scc, currDate1());
            pstmt.setInt(++scc, info.getCategoryId());
            pstmt.setInt(++scc, info.getCourseId());
            //print(this,"updatecourse :: " + pstmt.toString());
            cc = pstmt.executeUpdate();
        } catch (Exception exception) {
            print(this, "updatecourse :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return cc;
    }

    public int savecourseattachment(Connection conn, int courseId, int type, String filename, String dispname, int userId)
    {
        int cc = 0;
        try 
        {
            StringBuilder sb = new StringBuilder();
            sb.append("INSERT INTO t_topicattachment ");
            sb.append("(i_topicid, i_type, s_filename, s_displayname, i_status, i_userid, ts_regdate, ts_moddate) ");
            sb.append("VALUES (?, ?, ?, ?, ?, ?, ?, ?) ");
            String query = (sb.toString()).intern();
            sb.setLength(0);
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, courseId);
            pstmt.setInt(2, type);
            pstmt.setString(3, filename);
            pstmt.setString(4, dispname);
            pstmt.setInt(5, 1);
            pstmt.setInt(6, userId);
            pstmt.setString(7, currDate1());
            pstmt.setString(8, currDate1());
            print(this, "createPic :: " + pstmt.toString());
            cc = pstmt.executeUpdate();
        } catch (Exception exception) {
            print(this, "createPic :: " + exception.getMessage());
        } finally {
            close(null, pstmt, rs);
        }
        return cc;
    }

    public ArrayList getcourseattachment(int courseId)
    {
        ArrayList list = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT t_topicattachment.i_topicattachmentid, t_topicattachment.s_filename, DATE_FORMAT(t_topicattachment.ts_regdate, '%d-%b-%Y %H:%i'), ");
        sb.append("t_userlogin.s_name FROM t_topicattachment ");
        sb.append("LEFT JOIN t_userlogin ON (t_userlogin.i_userid = t_topicattachment.i_userid) ");
        sb.append("WHERE t_topicattachment.i_topicid =? ");
        sb.append("ORDER BY t_topicattachment.i_topicattachmentid ");
        String query = (sb.toString()).intern();
        sb.setLength(0);
        try
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, courseId);
            logger.info("getcourseattachment :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String filename, date, name;
            int courseattachmentId;
            while (rs.next()) 
            {
                courseattachmentId = rs.getInt(1);
                filename = rs.getString(2) != null ? rs.getString(2) : "";
                date = rs.getString(3) != null ? rs.getString(3) : "";
                name = rs.getString(4) != null ? rs.getString(4) : "";
                list.add(new KnowledgecontentInfo(courseattachmentId, filename, date, name));
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return list;
    }

    public int delcourseattachment(int courseattachmentId) {
        int cc = 0;
        String query = "";
        try 
        {
            conn = getConnection();
            query = "UPDATE t_topicattachment SET i_status =2 WHERE i_topicattachmentid =?";
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, courseattachmentId);
            logger.info("delcourseattachment :: " + pstmt.toString());
            cc = pstmt.executeUpdate();
        } catch (Exception exception) {
            print(this, "delcourseattachment :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return cc;
    }

    public KnowledgecontentInfo getsubcategoryDetailById(int categoryId, int subcategoryId) 
    {
        KnowledgecontentInfo info = null;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT t_ksubcategory.s_name, t_ksubcategory.i_status, t_ksubcategory.s_description, t_kcategory.s_name FROM t_ksubcategory ");
        sb.append("LEFT JOIN t_kcategory ON (t_kcategory.i_kcategoryid = t_ksubcategory.i_kcategoryid ) ");
        sb.append("WHERE t_ksubcategory.i_kcategoryid =? AND t_ksubcategory.i_ksubcategoryid =? ");
        String query = sb.toString().intern();
        sb.setLength(0);
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, categoryId);
            pstmt.setInt(2, subcategoryId);
            //print(this,"getKnowledgecontentDetailById :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String name, description, categoryname;
            int status;
            while (rs.next()) 
            {
                name = rs.getString(1) != null ? rs.getString(1) : "";
                status = rs.getInt(2);
                description = rs.getString(3) != null ? rs.getString(3) : "";
                categoryname = rs.getString(4) != null ? rs.getString(4) : "";

                info = new KnowledgecontentInfo(categoryId, name, status, description, 0, subcategoryId, categoryname);
            }
        } catch (Exception exception) {
            print(this, "getKnowledgecontentDetailById :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return info;
    }

    public KnowledgecontentInfo getcourseDetailById(int categoryId, int subcategoryId, int courseId) {
        KnowledgecontentInfo info = null;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT s_name, i_status, s_description, s_assettypeids FROM t_topic WHERE i_kcategoryid =? AND i_ksubcategoryid =? AND i_topicid =? ");
        String query = sb.toString().intern();
        sb.setLength(0);
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, categoryId);
            pstmt.setInt(2, subcategoryId);
            pstmt.setInt(3, courseId);
            print(this, "getcourseDetailById :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String topicname, description, assettypeids;
            int status;
            while (rs.next()) 
            {
                topicname = rs.getString(1) != null ? rs.getString(1) : "";
                status = rs.getInt(2);
                description = rs.getString(3) != null ? rs.getString(3) : "";
                assettypeids = rs.getString(4) != null ? rs.getString(4) : "";
                info = new KnowledgecontentInfo(categoryId, topicname, status, description, courseId, assettypeids, subcategoryId);
            }
        } catch (Exception exception) {
            print(this, "getcourseDetailById :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return info;
    }

    public Collection getCourseType() {
        Collection coll = new LinkedList();
        coll.add(new KnowledgecontentInfo(-1, "- Select -"));
        String xml_path = getMainPath("json_path");
        String str = readHTMLFile("coursetype.json", xml_path);
        JSONArray arr = new JSONArray(str);
        if (arr != null) {
            int len = arr.length();
            for (int i = 0; i < len; i++) {
                JSONObject jobj = arr.optJSONObject(i);
                if (jobj != null) {
                    coll.add(new KnowledgecontentInfo(jobj.optInt("id"), jobj.optString("name")));
                }
            }
        }
        return coll;
    }

    public Collection getCoursename() {
        Collection coll = new LinkedList();
        coll.add(new KnowledgecontentInfo(-1, "- Select -"));
        String xml_path = getMainPath("json_path");
        String str = readHTMLFile("coursename.json", xml_path);
        JSONArray arr = new JSONArray(str);
        if (arr != null) {
            int len = arr.length();
            for (int i = 0; i < len; i++) {
                JSONObject jobj = arr.optJSONObject(i);
                if (jobj != null) {
                    coll.add(new KnowledgecontentInfo(jobj.optInt("id"), jobj.optString("name")));
                }
            }
        }
        return coll;
    }

    public ArrayList getcourseslistByIdandIndexforDetail(int categoryId, int subcategoryIdIndex) {
        ArrayList list = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT t_topic.s_name, t_topic.i_status, t_topic.s_description, t_ksubcategory.i_ksubcategoryid, t_ksubcategory.s_name, ");
        sb.append("t_topic.i_topicid, t1.ct, t2.names, t_ksubcategory.i_status  FROM t_topic ");
        sb.append("LEFT JOIN  t_ksubcategory ON (t_ksubcategory.i_ksubcategoryid = t_topic.i_ksubcategoryid) ");
        sb.append("LEFT JOIN (SELECT i_topicid, COUNT(1) AS ct FROM t_topicattachment GROUP BY i_topicid) AS t1 ON (t1.i_topicid = t_topic.i_topicid) ");
        sb.append("LEFT JOIN (SELECT t_topic.i_topicid, GROUP_CONCAT(DISTINCT t_assettype.s_name ORDER BY t_assettype.s_name SEPARATOR ', ') AS names FROM t_topic ");
        sb.append("LEFT JOIN t_assettype ON find_in_set(t_assettype.i_assettypeid, t_topic.s_assettypeids) GROUP BY t_topic.i_topicid) AS t2 ON (t2.i_topicid = t_topic.i_topicid) ");
        sb.append("WHERE t_topic.i_kcategoryid =? ");
        if (subcategoryIdIndex > 0) {
            sb.append("AND t_topic.i_ksubcategoryid =? ");
        }
        sb.append("ORDER BY t_topic.i_status, t_ksubcategory.s_name, t_topic.s_name ");
        String query = sb.toString().intern();
        sb.setLength(0);
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, categoryId);
            if (subcategoryIdIndex > 0) {
                pstmt.setInt(2, subcategoryIdIndex);
            }
            print(this,"getcourseslistByIdandIndexforDetail :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String coursename, description, subcategoryname, assettypeNames;
            int status = 0, subcategoryId = 0, courseId = 0, count = 0, substatus = 0;
            while (rs.next())
            {
                coursename = rs.getString(1) != null ? rs.getString(1) : "";
                status = rs.getInt(2);
                description = rs.getString(3) != null ? rs.getString(3) : "";
                subcategoryId = rs.getInt(4);
                subcategoryname = rs.getString(5) != null ? rs.getString(5) : "";
                courseId = rs.getInt(6);
                count = rs.getInt(7);
                assettypeNames = rs.getString(8) != null ? rs.getString(8) : "";
                substatus = rs.getInt(9);
                list.add(new KnowledgecontentInfo(categoryId, coursename, status, description, 
                        subcategoryname, subcategoryId, courseId, count, assettypeNames, substatus));
            }
            rs.close();
        } catch (Exception exception) {
            print(this, "getcourseslistByIdandIndexforDetail :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return list;
    }

    public String getcategoryname(int categoryId) 
    {
        String categoryname = "";
        String query = "SELECT s_name FROM t_kcategory WHERE i_kcategoryid =?";
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, categoryId);
            rs = pstmt.executeQuery();
            while (rs.next()) 
            {
                categoryname = rs.getString(1) != null ? rs.getString(1) : "";
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return categoryname;
    }

    public int checkDuplicacycourse(int categoryId, String topicname, int subcategoryId, int couresId) 
    {
        int ck = 0;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT i_topicid FROM t_topic WHERE s_name =? AND i_kcategoryid =? AND i_ksubcategoryid =? AND i_status IN (1, 2) ");
        if (couresId > 0) {
            sb.append("AND i_topicid !=? ");
        }
        String query = (sb.toString()).intern();
        sb.setLength(0);
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            int scc = 0;
            pstmt.setString(++scc, topicname);
            pstmt.setInt(++scc, categoryId);
            pstmt.setInt(++scc, subcategoryId);
            if (couresId > 0) {
                pstmt.setInt(++scc, couresId);
            }
            print(this, "checkDuplicacycourse :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ck = 1;
            }
        } catch (Exception exception) {
            print(this, "checkDuplicacycourse :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return ck;
    }

    public int deletesubcategory(int subcategoryId, int userId, int status, String ipAddrStr, String iplocal) {
        int cc = 0;
        try 
        {
            conn = getConnection();
            StringBuilder sb = new StringBuilder();
            sb.append("UPDATE t_ksubcategory SET ");
            sb.append("i_status =?, ");
            sb.append("i_userid =?, ");
            sb.append("ts_moddate =? ");
            sb.append("WHERE i_ksubcategoryid =? ");
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
            pstmt.setInt(++scc, subcategoryId);

            cc = pstmt.executeUpdate();
        } catch (Exception exception) {
            print(this, "deletesubcategory :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        createHistoryAccess(null, userId, ipAddrStr, iplocal, "Data updated", 93, subcategoryId);
        return cc;
    }

    public int deletecourse(int courseId, int userId, int status, String ipAddrStr, String iplocal) {
        int cc = 0;
        try 
        {
            conn = getConnection();
            StringBuilder sb = new StringBuilder();
            sb.append("UPDATE t_topic SET ");
            sb.append("i_status =?, ");
            sb.append("i_userid =?, ");
            sb.append("ts_moddate =? ");
            sb.append("WHERE i_topicid =? ");
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
            pstmt.setInt(++scc, courseId);

            cc = pstmt.executeUpdate();
        } catch (Exception exception) {
            print(this, "deletecourse :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        createHistoryAccess(null, userId, ipAddrStr, iplocal, "Data updated", 93, courseId);
        return cc;
    }

    public ArrayList getAssetList()
    {
        ArrayList list = new ArrayList();
        String query = ("SELECT i_assettypeid, s_name FROM t_assettype WHERE i_status = 1 ORDER BY s_name ");
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);

            logger.info("getAssetList :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String ddlLabel;
            int ddlValue;
            while (rs.next())
            {
                ddlValue = rs.getInt(1);
                ddlLabel = rs.getString(2) != null ? rs.getString(2) : "";
                list.add(new KnowledgecontentInfo(ddlValue, ddlLabel));
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return list;
    }

    public ArrayList getTopicAttachmentList(int Id) 
    {
        ArrayList list = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT i_topicattachmentid, i_type, s_displayname, s_filename FROM t_topicattachment WHERE i_status =1 ");
        if (Id > 0)
        {
            sb.append("AND i_topicid =? ");
        }
        sb.append("ORDER BY ts_regdate ");
        String query = sb.toString().intern();
        sb.setLength(0);
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            if (Id > 0) {
                pstmt.setInt(1, Id);
            }
            logger.info("getTopicAttachmentList :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String displayname, filename;
            int topicattachmentid, type;
            while (rs.next()) 
            {
                topicattachmentid = rs.getInt(1);
                type = rs.getInt(2);
                displayname = rs.getString(3) != null ? rs.getString(3) : "";
                filename = rs.getString(4) != null ? rs.getString(4) : "";
                list.add(new KnowledgecontentInfo(topicattachmentid, type, displayname, filename));
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return list;
    }

    public void unzip(String zippath, String destinationpath) {
        String command = "";
        if (getMainPath("run_place").equals("local")) {
            command = "powershell Expand-Archive -Path " + zippath + " -DestinationPath " + destinationpath;
        } else {
            command = "unzip " + zippath + " -d " + destinationpath;
        }
        logger.info("## " + command);
        try {
            Runtime rt = Runtime.getRuntime();
            Process p = rt.exec(command);
            p.waitFor();
            File file = new File(zippath);
            if (file.exists()) {
                file.delete();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteDirectory(File path) {
        try {
            if (path.exists()) {
                File[] files = path.listFiles();
                for (int i = 0; i < files.length; i++) {
                    if (files[i].isDirectory()) {
                        deleteDirectory(files[i]);
                    } else {
                        files[i].delete();
                    }
                }
            }
            path.delete();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
