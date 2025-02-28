package com.web.jxp.documenttype;

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

public class DocumentType extends Base 
{
    private static final Logger logger = Logger.getLogger(Class.class.getName());
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    public ArrayList getDocumentTypeByName(String search, int next, int count)
    {
        ArrayList documentType = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("select i_doctypeid, s_doc, s_docformatids, s_docmoduleids, i_status, s_docsubmoduleids  ");
        sb.append("FROM t_doctype where 0 = 0 ");
        if(search != null && !search.equals(""))
            sb.append("and (s_doc like ? ) ");
        sb.append(" order by i_status, s_doc ");
        if(count > 0)
            sb.append(" limit ").append(next*count).append(", ").append(count);
        String query = (sb.toString()).intern();
        sb.setLength(0);
        
        sb.append("select count(1) FROM t_doctype where 0 = 0 ");
        if(search != null && !search.equals(""))
            sb.append("and (s_doc like ? ) ");
        String countquery = (sb.toString()).intern();
        sb.setLength(0);
        try
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            int scc = 0;
            if(search != null && !search.equals(""))
            {
                pstmt.setString(++scc, "%"+(search)+"%");
               
            }
            logger.info("getDocumentTypeByName :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String name, docformat, moduletype, submodule;
            int documentTypeId, status;
            while (rs.next())
            {
                documentTypeId = rs.getInt(1);
                name = rs.getString(2) != null ? rs.getString(2) : "";
                docformat = rs.getString(3) != null ? rs.getString(3) : "";
                moduletype = rs.getString(4) != null ? rs.getString(4) : "";
                status = rs.getInt(5);
                submodule = rs.getString(6) != null ? rs.getString(6) : "";
                documentType.add(new DocumentTypeInfo(documentTypeId, name,docformat, moduletype, status, 0,submodule));
            }
            rs.close();
            
            pstmt = conn.prepareStatement(countquery);
            scc = 0;
            if(search != null && !search.equals(""))
            {
                pstmt.setString(++scc, "%"+(search)+"%");
            }
            print(this,"getDocumentTypeByName :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            while (rs.next())
            {
                documentTypeId = rs.getInt(1);
                documentType.add(new DocumentTypeInfo(documentTypeId, "", 0, 0));
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            close(conn, pstmt, rs);
        }
        return documentType;
    }

    public ArrayList getFinalRecord(ArrayList l, String colId, int tp)
    {
        ArrayList list = null;
        HashMap record = null;
        DocumentTypeInfo info;
        int total = 0;
        if(l != null)
            total = l.size();
        try
        {
            if(total > 0)
            {
                record = new HashMap();
                int i;
                for (i = 0; i < total; i++)
                {
                    info = (DocumentTypeInfo) l.get (i);
                    record.put(getInfoValue(info, colId), info);
                }
            }
            Map map = sortByName(record, tp);
            Iterator it = map.keySet().iterator();
            list = new ArrayList();
            DocumentTypeInfo rInfo;
            while (it.hasNext())
            {
                String key = (String) it.next();
                int i;
                for(i= 0; i < total; i++)
                {
                    rInfo = (DocumentTypeInfo) l.get(i);
                    String str = getInfoValue(rInfo, colId);
                    if(str.equals(key))
                    {
                        list.add(rInfo);
                    }
                }
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return list;
    }
    // to sort search result
    public String getInfoValue(DocumentTypeInfo info, String i)
    {
        String infoval = "";
        if(i != null && i.equals("1"))
            infoval = info.getName() != null ? info.getName() : "";  
        if(i != null && i.equals("2"))
            infoval = info.getDocFormat()!= null ? info.getDocFormat(): "";
        return infoval;
    }
    public DocumentTypeInfo getDocumentTypeDetailById(int documentTypeId)
    {
        DocumentTypeInfo info = null;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT s_doc, i_status,s_docformatids, s_docmoduleids, s_docsubmoduleids, i_mflag FROM t_doctype WHERE i_doctypeid = ? ");
        String query = sb.toString().intern();
        sb.setLength(0);
        try
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, documentTypeId);
            print(this,"getDocumentTypeDetailById :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String name, docformat, docmodule,docsubmodule;
            int status, mflag;
            while (rs.next())
            {
                name = rs.getString(1) != null ? rs.getString(1) : "";
                status = rs.getInt(2);
                docformat = rs.getString(3) != null ? rs.getString(3) : "";
                docmodule = rs.getString(4) != null ? rs.getString(4) : "";
                docsubmodule = rs.getString(5) != null ? rs.getString(5) : "";
                mflag = rs.getInt(6);
                info = new DocumentTypeInfo(documentTypeId, name, status, docformat, docmodule, docsubmodule, mflag ); 
            }
        }
        catch (Exception exception)
        {
            print(this,"getDocumentTypeDetailById :: " + exception.getMessage());
        }
        finally
        {
            close(conn, pstmt, rs);
        }
        return info;
    }
    
    public DocumentTypeInfo getDocumentTypeDetailByIdforDetail(int documentTypeId)
    {
        DocumentTypeInfo info = null;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT s_doc, i_status, s_docformatids, s_docmoduleids, s_docsubmoduleids, i_mflag FROM t_doctype WHERE i_doctypeid = ? ");
        String query = sb.toString().intern();
        sb.setLength(0);
        try
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, documentTypeId);
            print(this,"getDocumentTypeDetailByIdforDetail :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String name, docformat ,docmodule, docsubmodule;
            int status = 0, mflag = 0;
            while (rs.next())
            {
                name = rs.getString(1) != null ? rs.getString(1): "";
                status = rs.getInt(2); 
                docformat = rs.getString(3) != null ? rs.getString(3): "";
                docmodule = rs.getString(4) != null ? rs.getString(4): "";
                docsubmodule = rs.getString(5) != null ? rs.getString(5): "";
                mflag = rs.getInt(6);
                info = new DocumentTypeInfo(documentTypeId, name, status, 0, docformat, docmodule, docsubmodule, mflag); 
            }
            rs.close();            
        }
        catch (Exception exception)
        {
            print(this,"getDocumentTypeDetailByIdforDetail :: " + exception.getMessage());
        }
        finally
        {
            close(conn, pstmt, rs);
        }
        return info;
    }
    
    public int createDocumentType(DocumentTypeInfo info)
    {
        int documentTypeId = 0;
        try
        {
            StringBuilder sb = new StringBuilder();
            sb.append("INSERT INTO t_doctype ");
            sb.append("(s_doc, s_docformatids, s_docmoduleids, s_docsubmoduleids, i_mflag, i_status, i_userid, ts_regdate, ts_moddate) ");
            sb.append("VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");
            String query = (sb.toString()).intern();
            sb.setLength(0);

            conn = getConnection();
            pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            int scc = 0;
            pstmt.setString(++scc, (info.getName ()));
            pstmt.setString(++scc, info.getDocFormat());
            pstmt.setString(++scc, info.getDocModule());
            pstmt.setString(++scc, info.getDocsubModule());
            pstmt.setInt(++scc, info.getMflag());
            pstmt.setInt(++scc, info.getStatus());
            pstmt.setInt(++scc, info.getUserId());
            pstmt.setString(++scc, currDate1());
            pstmt.setString(++scc, currDate1());  
            print(this,"createDocumentType :: " + pstmt.toString());
            pstmt.executeUpdate();
            rs = pstmt.getGeneratedKeys();
            while (rs.next())
            {
                documentTypeId = rs.getInt(1);
            }
            createjson(conn);
        }
        catch (Exception exception)
        {
            print(this,"createDocumentType :: " + exception.getMessage());
        }
        finally
        {
            close(conn, pstmt, rs);
        }
        return documentTypeId;
    }
    public int updateDocumentType(DocumentTypeInfo info)
    {
        int cc = 0;
        try
        {
            StringBuilder sb = new StringBuilder();
            sb.append("UPDATE t_doctype SET ");
            sb.append("s_doc = ?, ");
            sb.append("s_docformatids = ?, ");
            sb.append("s_docmoduleids = ?, ");
            sb.append("s_docsubmoduleids = ?, ");
            sb.append("i_mflag = ?, ");
            sb.append("i_status = ?, ");
            sb.append("i_userid = ?, ");
            sb.append("ts_moddate = ? ");
            sb.append("WHERE i_doctypeid = ? ");
            String query = (sb.toString()).intern();
            sb.setLength(0);

            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            int scc = 0;
            pstmt.setString(++scc, info.getName());
            pstmt.setString(++scc, info.getDocFormat());
            pstmt.setString(++scc, info.getDocModule());
            pstmt.setString(++scc, info.getDocsubModule());
            pstmt.setInt(++scc, info.getMflag());
            pstmt.setInt(++scc, info.getStatus());
            pstmt.setInt(++scc, info.getUserId());
            pstmt.setString(++scc, currDate1());
            pstmt.setInt(++scc, info.getDocumentTypeId());
            print(this,"updateDocumentType :: " + pstmt.toString());
            cc = pstmt.executeUpdate();
            createjson(conn);
        }
        catch (Exception exception)
        {
            print(this,"updateDocumentType :: " + exception.getMessage());
        }
        finally
        {
            close(conn, pstmt, rs);
        }
        return cc;
    }
    public int checkDuplicacy(int documentTypeId, String name)
    {
        int ck = 0;
        StringBuilder sb = new StringBuilder();
        sb.append("select i_doctypeid FROM t_doctype where s_doc = ? and i_status in (1, 2)");
        if(documentTypeId > 0)
            sb.append(" and i_doctypeid != ? ");
        String query = (sb.toString()).intern();
        sb.setLength(0);
        try
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            int scc = 0;           
            pstmt.setString(++scc, name);
            if(documentTypeId > 0)
                pstmt.setInt(++scc, documentTypeId);
            //print(this,"checkDuplicacy :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            while (rs.next())
            {
                ck = 1;
            }
        }
        catch (Exception exception)
        {
            print(this,"checkDuplicacy :: " + exception.getMessage());
        }
        finally
        {
            close(conn, pstmt, rs);
        }
        return ck;
    }
    
    public Collection getDocumentTypes()
    {
        Collection coll = new LinkedList();
        coll.add(new DocumentTypeInfo(-1, "- Select -"));
        String xml_path = getMainPath("json_path");
        String str = readHTMLFile("documenttype.json", xml_path);
        JSONArray arr = new JSONArray(str);
        if(arr != null)
        {
            int len = arr.length();
            for(int i = 0; i < len; i++)
            {
                JSONObject jobj = arr.optJSONObject(i);
                if(jobj != null)
                {
                    coll.add(new DocumentTypeInfo(jobj.optInt("id"), jobj.optString("name")));
                }
            }
        }
        return coll;
    }
    
    public int deleteDocumentType(int documentTypeId, int userId, int status, String ipAddrStr, String iplocal)
    {
        int cc = 0;
        try
        {
            conn = getConnection();
            StringBuilder sb = new StringBuilder();
            sb.append("update t_doctype set ");
            sb.append("i_status = ?, ");
            sb.append("i_userid = ?, ");
            sb.append("ts_moddate = ? ");
            sb.append("where i_doctypeid = ? ");
            String query = (sb.toString()).intern();
            sb.setLength(0);
            
            pstmt = conn.prepareStatement(query);
            int scc = 0;
            if(status == 1)
                pstmt.setInt(++scc, 2);
            else
                pstmt.setInt(++scc, 1);
            pstmt.setInt(++scc, userId);
            pstmt.setString(++scc, currDate1());
            pstmt.setInt(++scc, documentTypeId);
            
            cc = pstmt.executeUpdate();
            createjson(conn);
        }
        catch (Exception exception)
        {
            print(this,"deleteDocumentType :: " + exception.getMessage());
        }
        finally
        {
            close(conn, pstmt, rs);
        }
        createHistoryAccess(null, userId, ipAddrStr, iplocal, "Data Deleted", 11, documentTypeId); 
        return cc;
    } 
    
    public ArrayList getListForExcel(String search)
    {
        ArrayList list = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT s_doc,  i_status,s_docformatids, s_docmoduleids, s_docsubmoduleids, i_mflag ");
        sb.append("FROM t_doctype WHERE 0 = 0 ");
        if(search != null && !search.equals(""))
            sb.append("AND (s_doc LIKE ?) ");
        sb.append(" ORDER BY i_status, s_doc ");      
        String query = sb.toString().intern();
        sb.setLength(0);        
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            int scc = 0;
           if(search != null && !search.equals(""))
            {
                pstmt.setString(++scc, "%"+(search)+"%");
            }
            Common.print(this, "getListForExcel :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String name, docformat,  docmodule,  docsubmodule;
            int status, mflag;
            while (rs.next())
            {
                name = rs.getString(1) != null ? rs.getString(1) : "";
                status = rs.getInt(2);
                docformat = rs.getString(3) != null ? rs.getString(3) : "";
                docmodule = rs.getString(4) != null ? rs.getString(4) : "";
                docsubmodule = rs.getString(5) != null ? rs.getString(5) : "";
                mflag = rs.getInt(6);
                list.add(new DocumentTypeInfo(0, name, status, 0, docformat,  docmodule,  docsubmodule, mflag));
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
       String query = ("Select i_doctypeid, s_doc, s_docformatids, s_docmoduleids,s_docsubmoduleids FROM t_doctype where i_status = 1 order by  s_doc ");
        try
        {
            pstmt = conn.prepareStatement(query);
            rs = pstmt.executeQuery();
            int id;
            String name, docformat, docmodule, docsubmodule;
            JSONArray jarr = new JSONArray();
            while (rs.next())
            {
                id = rs.getInt(1);
                name = rs.getString(2) != null ? rs.getString(2): "";
                docformat = rs.getString(3) != null ? rs.getString(3): "";
                docmodule = rs.getString(4) != null ? rs.getString(4): "";
                docsubmodule = rs.getString(5) != null ? rs.getString(5): "";
                JSONObject jobj = new JSONObject();
                jobj.put("id", id);
                jobj.put("name", name);
                jobj.put("docformat", docformat);
                jobj.put("docmodule", docmodule);
                jobj.put("docsubmodule", docsubmodule);
                jarr.put(jobj);
            }
            rs.close();
            String str = jarr.toString();
            String filePath = getMainPath("json_path");
            writeHTMLFile(str, filePath, "documenttype.json");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            close(null, pstmt, rs);
        }
    }
     public String getDocFormatId(String formatIds) {
        String str = "";
        if (formatIds.contains("1")) {
            if (str.equals("")) {
                str = "PDF";
            } else {
                str += ", PDF";
            }
        }
        if (formatIds.contains("2")) {
            if (str.equals("")) {
                str = "DOCX";
            } else {
                str += ", DOCX";
            }
        }
        if (formatIds.contains("3")) {
            if (str.equals("")) {
                str = "JPEG";
            } else {
                str += ", JPEG";
            }
        }
        return str;
    }
    public String getDocModuleId(String docModuleIds) {
        String str = "";
        if (docModuleIds.contains("1")) {
            if (str.equals("")) {
                str = "Crew Management";
            } else {
                str += ", Crew Management";
            }
        }
        if (docModuleIds.contains("2")) {
            if (str.equals("")) {
                str = "Training & Development";
            } else {
                str += ", Training & Development";
            }
        }
        if (docModuleIds.contains("3")) {
            if (str.equals("")) {
                str = "Configurations";
            } else {
                str += ", Configurations";
            }
        }
        return str;
    }
    public String getDocSubModuleId(String docSubModuleIds) {
        String str = "";
        if (docSubModuleIds.contains("1")) {
            if (str.equals("")) {
                str = "Enroll Candidate";
            } else {
                str += ", Enroll Candidate";
            }
        }
        if (docSubModuleIds.contains("2")) {
            if (str.equals("")) {
                str = "Talent Pool";
            } else {
                str += ", Talent Pool";
            }
        }
        if (docSubModuleIds.contains("3")) {
            if (str.equals("")) {
                str = "Mobilization";
            } else {
                str += ", Mobilization";
            }
        }
        if (docSubModuleIds.contains("4")) {
            if (str.equals("")) {
                str = "Onboarding";
            } else {
                str += ", Onboarding";
            }
        }
        if (docSubModuleIds.contains("5")) {
            if (str.equals("")) {
                str = "Training Matrix";
            } else {
                str += ", Training Matrix";
            }
        }
        if (docSubModuleIds.contains("6")) {
            if (str.equals("")) {
                str = "Document Issued by";
            } else {
                str += ", Document Issued by";
            }
        }
        return str;
    }
}
