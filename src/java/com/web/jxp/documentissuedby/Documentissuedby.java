package com.web.jxp.documentissuedby;

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

public class Documentissuedby extends Base {

    private static final Logger logger = Logger.getLogger(Class.class.getName());
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    public ArrayList getDocumentissuedbyByName(String search,  int next, int count) 
    {
        ArrayList documentissuedbys = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("select t_documentissuedby.i_documentissuedbyid,  t_country.s_name,  t_doctype.s_doc, t_documentissuedby.s_name , t_documentissuedby.i_status from t_documentissuedby left join t_country on t_documentissuedby.i_countryid = t_country.i_countryid  left join  t_doctype on t_doctype.i_doctypeid = t_documentissuedby.i_doctypeid ");
        sb.append("where 0 = 0 ");
        if (search != null && !search.equals("")) {
            sb.append("and (t_documentissuedby.s_name like ? OR  t_doctype.s_doc like ? OR t_country.s_name like ?) ");
        }
        sb.append(" order by   t_documentissuedby.i_status, t_country.s_name, t_documentissuedby.s_name ");
        if (count > 0) {
            sb.append(" limit ").append(next * count).append(", ").append(count);
        }
        String query = (sb.toString()).intern();
        sb.setLength(0);

        sb.append("select count(1) FROM t_documentissuedby ");
        sb.append("left join t_country on t_documentissuedby.i_countryid = t_country.i_countryid ");
        sb.append("left join  t_doctype on t_doctype.i_doctypeid = t_documentissuedby.i_doctypeid ");
        sb.append("where 0 = 0 ");
        if (search != null && !search.equals("")) {
            sb.append("and (t_documentissuedby.s_name like ? OR  t_doctype.s_doc like ? OR t_country.s_name like ?) ");
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
                pstmt.setString(++scc, "%" + (search) + "%");
                pstmt.setString(++scc, "%" + (search) + "%");
            }
            logger.info("getDocumentissuedbyByName :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String name, countryName, documentName;
            int documentissuedbyId, status;
            while (rs.next()) 
            {
                documentissuedbyId = rs.getInt(1);
                countryName = rs.getString(2) != null ? rs.getString(2) : "";
                documentName = rs.getString(3) != null ? rs.getString(3) : "";
                name = rs.getString(4) != null ? rs.getString(4) : "";
                status = rs.getInt(5);
                documentissuedbys.add(new DocumentissuedbyInfo(documentissuedbyId, countryName, documentName, name, status, 0));
            }
            rs.close();

            pstmt = conn.prepareStatement(countquery);
            scc = 0;
            if (search != null && !search.equals("")) {
                pstmt.setString(++scc, "%" + (search) + "%");
                pstmt.setString(++scc, "%" + (search) + "%");
                pstmt.setString(++scc, "%" + (search) + "%");
            }
            print(this,"getDocumentissuedbyByName :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                documentissuedbyId = rs.getInt(1);
                documentissuedbys.add(new DocumentissuedbyInfo(documentissuedbyId, "", 0, 0));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return documentissuedbys;
    }

    public ArrayList getFinalRecord(ArrayList l, String colId, int tp) {
        ArrayList list = null;
        HashMap record = null;
        DocumentissuedbyInfo info;
        int total = 0;
        if (l != null) {
            total = l.size();
        }
        try {
            if (total > 0) {
                record = new HashMap();
                int i;
                for (i = 0; i < total; i++) {
                    info = (DocumentissuedbyInfo) l.get(i);
                    record.put(getInfoValue(info, colId), info);
                }
            }
            Map map = sortByName(record, tp);
            Iterator it = map.keySet().iterator();
            list = new ArrayList();
            DocumentissuedbyInfo rInfo;
            while (it.hasNext()) {
                String key = (String) it.next();
                int i;
                for (i = 0; i < total; i++) {
                    rInfo = (DocumentissuedbyInfo) l.get(i);
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
    public String getInfoValue(DocumentissuedbyInfo info, String i) {
        String infoval = "";

        if (i != null && i.equals("1")) {
            infoval = info.getCountryName() != null ? info.getCountryName() : "";
        }
        if (i != null && i.equals("2")) {
            infoval = info.getDocumentName() != null ? info.getDocumentName() : "";
        }
        if (i != null && i.equals("3")) {
            infoval = info.getDocumentissuedbyName() != null ? info.getDocumentissuedbyName() : "";
        }

        return infoval;
    }

    public DocumentissuedbyInfo getDocumentissuedbyDetailById(int documentissuedbyId) {
        DocumentissuedbyInfo info = null;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT s_name, i_countryid, i_doctypeid, i_status FROM t_documentissuedby where i_documentissuedbyid = ? ");
        String query = sb.toString().intern();
        sb.setLength(0);
        try
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, documentissuedbyId);
            print(this, "getDocumentissuedbyDetailById :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String name;
            int status;
            while (rs.next())
            {
                name = rs.getString(1) != null ? rs.getString(1): "" ;
                status = rs.getInt(2);
                int countryId = rs.getInt(3);
                int documentId = rs.getInt(4);
                info = new DocumentissuedbyInfo(documentissuedbyId, name, status, countryId, documentId, 0);
            }
        } catch (Exception exception) {
            print(this, "getDocumentissuedbyDetailById :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return info;
    }

    public DocumentissuedbyInfo getDocumentissuedbyDetailByIdforDetail(int documentissuedbyId) {
        DocumentissuedbyInfo info = null;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT t_documentissuedby.s_name,t_documentissuedby.i_status, t_country.s_name , t_doctype.s_doc FROM t_documentissuedby "
                + "left join t_country on t_documentissuedby.i_countryid = t_country.i_countryid "
                + "left join  t_doctype on t_doctype.i_doctypeid = t_documentissuedby.i_doctypeid where i_documentissuedbyid = ?");
        String query = sb.toString().intern();
        sb.setLength(0);
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, documentissuedbyId);
            print(this, "getDocumentissuedbyDetailByIdforDetail :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String name;
            int status = 0;
            while (rs.next()) 
            {
                name = rs.getString(1) != null ? rs.getString(1): "";
                status = rs.getInt(2);
                String countryName = rs.getString(3) != null ? rs.getString(3): "";
                String documentName = rs.getString(4) != null ? rs.getString(4): "";
                info = new DocumentissuedbyInfo(documentissuedbyId, name, status, countryName, documentName, 0);
            }
            rs.close();
        } catch (Exception exception) {
            print(this, "getDocumentissuedbyDetailByIdforDetail :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return info;
    }

    public int createDocumentissuedby(DocumentissuedbyInfo info) {
        int documentissuedbyId = 0;
        try 
        {
            StringBuilder sb = new StringBuilder();
            sb.append("insert into t_documentissuedby ");
            sb.append("(s_name, i_countryid, i_doctypeid, i_status,  i_userid, ts_regdate, ts_moddate) ");
            sb.append("values (?, ?, ?, ?, ?, ?, ? )");
            String query = (sb.toString()).intern();
            sb.setLength(0);

            conn = getConnection();
            pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            int scc = 0;
            pstmt.setString(++scc, (info.getDocumentissuedbyName()));
            pstmt.setInt(++scc, info.getCountryId());
            pstmt.setInt(++scc, info.getDocumentId());
            pstmt.setInt(++scc, info.getStatus());
            pstmt.setInt(++scc, info.getUserId());
            pstmt.setString(++scc, currDate1());
            pstmt.setString(++scc, currDate1());
            print(this, "createDocumentissuedby :: " + pstmt.toString());
            pstmt.executeUpdate();
            rs = pstmt.getGeneratedKeys();
            while (rs.next()) {
                documentissuedbyId = rs.getInt(1);
            }
            createjson(conn);
        } catch (Exception exception) {
            print(this, "createDocumentissuedby :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return documentissuedbyId;
    }

    public int updateDocumentissuedby(DocumentissuedbyInfo info) {
        int cc = 0;
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("update t_documentissuedby set ");
            sb.append("s_name = ?, ");
            sb.append("i_status = ?, ");
            sb.append("i_userid = ?, ");
            sb.append("ts_moddate = ?, ");
            sb.append("i_countryid = ?, ");
            sb.append("i_doctypeid = ? ");
            sb.append("where i_documentissuedbyid = ? ");
            String query = (sb.toString()).intern();
            sb.setLength(0);

            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            int scc = 0;
            pstmt.setString(++scc, info.getDocumentissuedbyName());
            pstmt.setInt(++scc, info.getStatus());
            pstmt.setInt(++scc, info.getUserId());
            pstmt.setString(++scc, currDate1());
            pstmt.setInt(++scc, info.getCountryId());
            pstmt.setInt(++scc, info.getDocumentId());
            pstmt.setInt(++scc, info.getDocumentissuedbyId());
            print(this, "updateDocumentissuedby :: " + pstmt.toString());
            cc = pstmt.executeUpdate();
            createjson(conn);
        } catch (Exception exception) {
            print(this, "updateDocumentissuedby :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return cc;
    }

    public int checkDuplicacy(int documentissuedbyId, String name, int countryId, int documentId) {
        int ck = 0;
        StringBuilder sb = new StringBuilder();
        sb.append("select i_documentissuedbyid FROM t_documentissuedby where i_countryid = ? and i_doctypeid = ? and s_name = ? and i_status in (1, 2)");
        if (documentissuedbyId > 0) {
            sb.append(" and i_documentissuedbyid != ? ");
        }
        String query = (sb.toString()).intern();
        sb.setLength(0);
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            int scc = 0;
            pstmt.setInt(++scc, countryId);
            pstmt.setInt(++scc, documentId);
            pstmt.setString(++scc, name);
            if (documentissuedbyId > 0) {
                pstmt.setInt(++scc, documentissuedbyId);
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

    public Collection getCountrys() 
    {
        Collection coll = new LinkedList();
        String query = ("SELECT i_countryid, s_name FROM t_country where i_status = 1 order by s_name").intern();
        coll.add(new DocumentissuedbyInfo(-1, "- Select -"));
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            print(this, "getCountrys :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            int refId;
            String refName;
            while (rs.next()) 
            {
                refId = rs.getInt(1);
                refName = rs.getString(2) != null ? rs.getString(2): "";
                coll.add(new DocumentissuedbyInfo(refId, refName));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return coll;
    }

    public Collection getDocuments()
    {
        Collection coll = new LinkedList();
        String query = ("SELECT i_doctypeid, s_doc  FROM t_doctype WHERE i_status = 1 AND FIND_IN_SET(3,s_docmoduleids) AND FIND_IN_SET(6, s_docsubmoduleids) ORDER BY s_doc ").intern();
        coll.add(new DocumentissuedbyInfo(-1, "- Select -"));
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            print(this, "getDocuments :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            int refId;
            String refName;
            while (rs.next())
            {
                refId = rs.getInt(1);
                refName = rs.getString(2) != null ? rs.getString(2): "";
                coll.add(new DocumentissuedbyInfo(refId, refName));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return coll;
    }

    public int deleteDocumentissuedby(int documentissuedbyId, int userId, int status, String ipAddrStr, String iplocal) {
        int cc = 0;
        try {
            conn = getConnection();
            StringBuilder sb = new StringBuilder();
            sb.append("update t_documentissuedby set ");
            sb.append("i_status = ?, ");
            sb.append("i_userid = ?, ");
            sb.append("ts_moddate = ? ");
            sb.append("where i_documentissuedbyid = ? ");
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
            pstmt.setInt(++scc, documentissuedbyId);
            
            cc = pstmt.executeUpdate();
            createjson(conn);
        } catch (Exception exception) {
            print(this, "deleteDocumentissuedby :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        createHistoryAccess(null, userId, ipAddrStr, iplocal, "Data Updated", 37, documentissuedbyId);
        return cc;
    }

    public ArrayList getExcel(String search)
    {
        ArrayList list = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("select t_documentissuedby.i_documentissuedbyid,  t_country.s_name,  ");
        sb.append("t_doctype.s_doc, t_documentissuedby.s_name , t_documentissuedby.i_status FROM t_documentissuedby ");
        sb.append("LEFT JOIN t_country ON t_documentissuedby.i_countryid = t_country.i_countryid  ");
        sb.append("LEFT JOIN  t_doctype ON t_doctype.i_doctypeid = t_documentissuedby.i_doctypeid ");
        sb.append(" WHERE 0 = 0 ");
        if (search != null && !search.equals("")) {
            sb.append("and (t_documentissuedby.s_name like ? OR t_country.s_name like ? OR  t_doctype.s_doc like ?) ");
        }
        sb.append(" order by t_documentissuedby.i_status,t_country.s_name, t_documentissuedby.s_name  ");

        String query = sb.toString().intern();
        sb.setLength(0);
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            Common.print(this, "getListForExcel :: " + pstmt.toString());
            int scc = 0;
            if (search != null && !search.equals("")) 
            {
                pstmt.setString(++scc, "%" + (search) + "%");
                pstmt.setString(++scc, "%" + (search) + "%");
                pstmt.setString(++scc, "%" + (search) + "%");
            }
            rs = pstmt.executeQuery();
            while (rs.next()) 
            {
                int documentissuedbyId = rs.getInt(1);
                String countryName = rs.getString(2) != null ? rs.getString(2) : "";
                String documentName = rs.getString(3) != null ? rs.getString(3) : "";
                String issuesAuthority = rs.getString(4) != null ? rs.getString(4) : "";
                int status = rs.getInt(5);

                list.add(new DocumentissuedbyInfo(documentissuedbyId, countryName, documentName, issuesAuthority, status));
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
        String query = ("Select i_countryid, i_doctypeid, i_documentissuedbyid, s_name FROM t_documentissuedby where i_status = 1 order by  s_name ");

        try {
            pstmt = conn.prepareStatement(query);
            rs = pstmt.executeQuery();
            int countryid;
            int documentid;
            int documentissuedbyid;
            String name;
            JSONArray jarr = new JSONArray();
            while (rs.next()) {
                countryid = rs.getInt(1);
                documentissuedbyid = rs.getInt(2);
                documentid = rs.getInt(3);
                name = rs.getString(4) != null ? rs.getString(4): "";
                JSONObject jobj = new JSONObject();
                jobj.put("countryid", countryid);
                jobj.put("documentissuedbyid", documentissuedbyid);
                jobj.put("documentid", documentid);
                jobj.put("name", name);
                jarr.put(jobj);
            }
            rs.close();
            String str = jarr.toString();
            String filePath = getMainPath("json_path");
            writeHTMLFile(str, filePath, "documentissuedby.json");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(null, pstmt, rs);
        }
    }

    public Collection getcountry() {
        Collection coll = new LinkedList();
        coll.add(new DocumentissuedbyInfo(-1, "- Select -"));
        String xml_path = getMainPath("json_path");
        String str = readHTMLFile("documentissuedby.json", xml_path);
        JSONArray arr = new JSONArray(str);
        if (arr != null) {
            int len = arr.length();
            for (int i = 0; i < len; i++) {
                JSONObject jobj = arr.optJSONObject(i);
                if (jobj != null) {
                    coll.add(new DocumentissuedbyInfo(jobj.optInt("countryid"), jobj.optString("name")));
                }
            }
        }
        return coll;
    }

    public Collection getDocumentissuedbys(int doctypeId) 
    {
        Collection coll = new LinkedList();
        if (doctypeId > 0)
        {
            String query = ("SELECT i_documentissuedbyid, s_name FROM t_documentissuedby where i_status = 1 and i_doctypeid = ? order by s_name").intern();
            coll.add(new DocumentissuedbyInfo(-1, "- Select -"));
            try 
            {
                conn = getConnection();
                pstmt = conn.prepareStatement(query);

                pstmt.setInt(1, doctypeId);
                rs = pstmt.executeQuery();
                int refId;
                String refName;
                while (rs.next()) 
                {
                    refId = rs.getInt(1);
                    refName = rs.getString(2) != null ? rs.getString(2): "";
                    coll.add(new DocumentissuedbyInfo(refId, refName));
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                close(conn, pstmt, rs);
            }
        } else {
            coll.add(new DocumentissuedbyInfo(-1, "- Select -"));
        }
        return coll;
    }
}
