package com.web.jxp.pcategory;

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

public class Pcategory extends Base 
{
    private static final Logger logger = Logger.getLogger(Class.class.getName());
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    public ArrayList getPcategoryByName(String search, int pdeptIdIndex, int assettypeIdIndex, int next, int count) 
    {
        ArrayList pcategorys = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("select t_pcategory.i_pcategoryid, t_pcategory.s_name, t_assettype.s_name, t_pdept.s_name, t_pcategory.i_status, t1.ct ");
        sb.append("from t_pcategory ");
        sb.append("left join t_assettype on (t_assettype.i_assettypeid = t_pcategory.i_assettypeid) ");
        sb.append("left join t_pdept on (t_pdept.i_pdeptid = t_pcategory.i_pdeptid) ");
        sb.append("left join (select i_pcategoryid, count(1) as ct from t_pquestion where i_status = 1 group by i_pcategoryid) as t1 on (t_pcategory.i_pcategoryid = t1.i_pcategoryid) ");
        sb.append("where 0 = 0 ");
        if (search != null && !search.equals("")) {
            sb.append("and (t_pcategory.s_name like ? OR t_assettype.s_name like ? OR t_pdept.s_name like ?) ");
        }
        if (pdeptIdIndex > 0) {
            sb.append("and t_pcategory.i_pdeptid = ? ");
        }
        if(assettypeIdIndex > 0)
            sb.append(" and t_pcategory.i_assettypeid = ? ");
        sb.append(" order by t_pcategory.i_status, t_assettype.s_name, t_pdept.s_name, t_pcategory.s_name ");
        if (count > 0) {
            sb.append(" limit ").append(next * count).append(", ").append(count);
        }
        String query = (sb.toString()).intern();
        sb.setLength(0);

        sb.append("select count(1) FROM t_pcategory ");
        sb.append("left join t_assettype on (t_assettype.i_assettypeid = t_pcategory.i_assettypeid) ");
        sb.append("left join t_pdept on (t_pdept.i_pdeptid = t_pcategory.i_pdeptid) ");
        sb.append("where 0 = 0 ");
        if (search != null && !search.equals("")) {
            sb.append("and (t_pcategory.s_name like ? OR t_assettype.s_name like ? OR t_pdept.s_name like ?) ");
        }
        if (pdeptIdIndex > 0) {
            sb.append("and t_pcategory.i_pdeptid = ? ");
        }
        if(assettypeIdIndex > 0)
            sb.append(" and t_pcategory.i_assettypeid = ? ");
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
            if (pdeptIdIndex > 0) {
                pstmt.setInt(++scc, pdeptIdIndex);
            }
            if(assettypeIdIndex > 0)
                pstmt.setInt(++scc, assettypeIdIndex);
            logger.info("getPcategoryByName :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String name, assettypeName, deptName;
            int pcategoryId, status, qcount;
            while (rs.next()) 
            {
                pcategoryId = rs.getInt(1);
                name = rs.getString(2) != null ? rs.getString(2) : "";                
                assettypeName = rs.getString(3) != null ? rs.getString(3) : "";
                deptName = rs.getString(4) != null ? rs.getString(4) : "";
                status = rs.getInt(5);
                qcount = rs.getInt(6);
                pcategorys.add(new PcategoryInfo(pcategoryId, name, assettypeName, deptName, qcount, status));
            }
            rs.close();

            pstmt = conn.prepareStatement(countquery);
            scc = 0;
            if (search != null && !search.equals("")) {
                pstmt.setString(++scc, "%" + (search) + "%");
                pstmt.setString(++scc, "%" + (search) + "%");
                pstmt.setString(++scc, "%" + (search) + "%");
            }
            if (pdeptIdIndex > 0) {
                pstmt.setInt(++scc, pdeptIdIndex);
            }
            if(assettypeIdIndex > 0)
                pstmt.setInt(++scc, assettypeIdIndex);
            //print(this,"getPcategoryByName :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                pcategoryId = rs.getInt(1);
                pcategorys.add(new PcategoryInfo(pcategoryId, "", "", "", 0, 0));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return pcategorys;
    }

    public ArrayList getFinalRecord(ArrayList l, String colId, int tp) {
        ArrayList list = null;
        HashMap record = null;
        PcategoryInfo info;
        int total = 0;
        if (l != null) {
            total = l.size();
        }
        try {
            if (total > 0) {
                record = new HashMap();
                int i;
                for (i = 0; i < total; i++) {
                    info = (PcategoryInfo) l.get(i);
                    record.put(getInfoValue(info, colId), info);
                }
            }
            Map map = sortByName(record, tp);
            Iterator it = map.keySet().iterator();
            list = new ArrayList();
            PcategoryInfo rInfo;
            while (it.hasNext()) {
                String key = (String) it.next();
                int i;
                for (i = 0; i < total; i++) {
                    rInfo = (PcategoryInfo) l.get(i);
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
    public String getInfoValue(PcategoryInfo info, String i) {
        String infoval = "";
        if (i != null && i.equals("1")) {
            infoval = info.getName() != null ? info.getName() : "";
        }
        else if (i != null && i.equals("2")) {
            infoval = info.getAssettypeName() != null ? info.getAssettypeName() : "";
        }
        else if (i != null && i.equals("3")) {
            infoval = info.getDeptName() != null ? info.getDeptName() : "";
        }
        else if (i != null && i.equals("4")) {
            infoval = ""+info.getQcount();
        }
        return infoval;
    }

    public PcategoryInfo getPcategoryDetailById(int pcategoryId) {
        PcategoryInfo info = null;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT s_name, i_status, i_assettypeid, i_pdeptid, s_description FROM t_pcategory where i_pcategoryid = ? ");
        String query = sb.toString().intern();
        sb.setLength(0);
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, pcategoryId);
            print(this, "getPcategoryDetailById :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String name;
            int status;
            while (rs.next()) 
            {
                name = rs.getString(1) != null ? rs.getString(1): "";
                status = rs.getInt(2);
                int assettypeId = rs.getInt(3);
                int pdeptId = rs.getInt(4);
                String description = rs.getString(5) != null ? rs.getString(5): "";
                info = new PcategoryInfo(pcategoryId, name, assettypeId, pdeptId, description, status, 0);
            }
        } 
        catch (Exception exception) 
        {
            print(this, "getPcategoryDetailById :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return info;
    }

    public PcategoryInfo getPcategoryDetailByIdforDetail(int pcategoryId) 
    {
        PcategoryInfo info = null;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT t_pcategory.s_name, t_pcategory.i_status, t_assettype.s_name, t_pdept.s_name, ");
        sb.append("t_pcategory.s_description, t_pcategory.i_assettypeid, t1.ct ");
        sb.append("FROM t_pcategory left join t_assettype on (t_assettype.i_assettypeid = t_pcategory.i_assettypeid) ");
        sb.append("left join (select i_pcategoryid, count(1) as ct from t_pquestion where i_status = 1 group by i_pcategoryid) as t1 on (t_pcategory.i_pcategoryid = t1.i_pcategoryid) ");
        sb.append("left join t_pdept on (t_pdept.i_pdeptid = t_pcategory.i_pdeptid) ");
        sb.append("where t_pcategory.i_pcategoryid = ?");
        String query = sb.toString().intern();
        sb.setLength(0);
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, pcategoryId);
            print(this, "getPcategoryDetailByIdforDetail :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String name;
            int status = 0;
            while (rs.next()) 
            {
                name = rs.getString(1) != null ? rs.getString(1): "";
                status = rs.getInt(2);
                String assettypeName = rs.getString(3) != null ? rs.getString(3): "";
                String deptName = rs.getString(4) != null ? rs.getString(4): "";
                String description = rs.getString(5) != null ? rs.getString(5): "";
                int assettypeId = rs.getInt(6);
                int qcount = rs.getInt(7);
                info = new PcategoryInfo(pcategoryId, name, assettypeName, deptName, description, 
                    status, assettypeId, qcount);
            }
            rs.close();
        } catch (Exception exception) {
            print(this, "getPcategoryDetailByIdforDetail :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return info;
    }

    public int createPcategory(PcategoryInfo info) {
        int pcategoryId = 0;
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("insert into t_pcategory ");
            sb.append("(s_name, i_assettypeid, i_pdeptid, s_description, i_status, i_userid, ts_regdate, ts_moddate) ");
            sb.append("values (?, ?, ?, ?, ?, ?, ?, ?)");
            String query = (sb.toString()).intern();
            sb.setLength(0);

            conn = getConnection();
            pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            int scc = 0;
            pstmt.setString(++scc, (info.getName()));
            pstmt.setInt(++scc, info.getAssettypeId());
            pstmt.setInt(++scc, info.getPdeptId());
            pstmt.setString(++scc, info.getDescription());
            pstmt.setInt(++scc, info.getStatus());
            pstmt.setInt(++scc, info.getUserId());
            pstmt.setString(++scc, currDate1());
            pstmt.setString(++scc, currDate1());
            print(this, "createPcategory :: " + pstmt.toString());
            pstmt.executeUpdate();
            rs = pstmt.getGeneratedKeys();
            while (rs.next()) {
                pcategoryId = rs.getInt(1);
            }
        } catch (Exception exception) {
            print(this, "createPcategory :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return pcategoryId;
    }

    public int updatePcategory(PcategoryInfo info) {
        int cc = 0;
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("update t_pcategory set ");
            sb.append("s_name = ?, ");
            sb.append("i_assettypeid = ?, ");
            sb.append("i_pdeptid = ?, ");
            sb.append("s_description = ?, ");
            sb.append("i_status = ?, ");
            sb.append("i_userid = ?, ");
            sb.append("ts_moddate = ?, ");
            sb.append("i_assettypeid = ? ");
            sb.append("where i_pcategoryid = ? ");
            String query = (sb.toString()).intern();
            sb.setLength(0);

            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            int scc = 0;
            pstmt.setString(++scc, info.getName());
            pstmt.setInt(++scc, info.getAssettypeId());
            pstmt.setInt(++scc, info.getPdeptId());
            pstmt.setString(++scc, info.getDescription());
            pstmt.setInt(++scc, info.getStatus());
            pstmt.setInt(++scc, info.getUserId());
            pstmt.setString(++scc, currDate1());
            pstmt.setInt(++scc, info.getAssettypeId());
            pstmt.setInt(++scc, info.getPcategoryId());
            print(this, "updatePcategory :: " + pstmt.toString());
            cc = pstmt.executeUpdate();
        } catch (Exception exception) {
            print(this, "updatePcategory :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return cc;
    }

    public int checkDuplicacy(int pcategoryId, String name, int assettypeId, int pdeptId) 
    {
        int ck = 0;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT i_pcategoryid FROM t_pcategory WHERE i_assettypeid = ? AND i_pdeptid = ? AND s_name = ? AND i_status IN (1, 2) ");
        if (pcategoryId > 0) {
            sb.append("AND i_pcategoryid !=? ");
        }
        String query = (sb.toString()).intern();
        sb.setLength(0);
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            int scc = 0;
            pstmt.setInt(++scc, assettypeId);
            pstmt.setInt(++scc, pdeptId);
            pstmt.setString(++scc, name);
            if (pcategoryId > 0) {
                pstmt.setInt(++scc, pcategoryId);
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

    public int deletePcategory(int pcategoryId, int userId, int status, String ipAddrStr, String iplocal) {
        int cc = 0;
        try {
            conn = getConnection();
            StringBuilder sb = new StringBuilder();
            sb.append("update t_pcategory set ");
            sb.append("i_status = ?, ");
            sb.append("i_userid = ?, ");
            sb.append("ts_moddate = ? ");
            sb.append("where i_pcategoryid = ? ");
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
            pstmt.setInt(++scc, pcategoryId);            
            cc = pstmt.executeUpdate();
        } catch (Exception exception) {
            print(this, "deletePcategory :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        createHistoryAccess(null, userId, ipAddrStr, iplocal, "Data Updated", 83, pcategoryId);
        return cc;
    }

    public ArrayList getExcel(String search, int assettypeIdIndex, int statusIndex) 
    {
        ArrayList list = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("select t_pcategory.i_pcategoryid, t_pcategory.s_name, ");
        sb.append(" t_pcategory.i_status, t_assettype.s_name, t_pdept.s_name, t1.ct from t_pcategory ");
        sb.append("left join t_assettype on (t_assettype.i_assettypeid = t_pcategory.i_assettypeid) ");
        sb.append("left join t_pdept on (t_pdept.i_pdeptid = t_pcategory.i_pdeptid) ");
        sb.append("left join (select i_pcategoryid, count(1) as ct from t_pquestion where i_status = 1 group by i_pcategoryid) as t1 on (t_pcategory.i_pcategoryid = t1.i_pcategoryid) ");
        sb.append("where 0 = 0 ");
        if (search != null && !search.equals("")) {
            sb.append("and (t_pcategory.s_name like ? OR t_assettype.s_name like ? OR t_pdept.s_name like ?) ");
        }
        if (statusIndex > 0) {
            sb.append("and t_pcategory.i_status = ? ");
        }
        if(assettypeIdIndex > 0)
            sb.append(" and t_pcategory.i_assettypeid = ? ");
        sb.append(" order by t_pcategory.i_status, t_assettype.s_name, t_pcategory.s_name  ");

        String query = sb.toString().intern();
        sb.setLength(0);
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            Common.print(this, "getListForExcel :: " + pstmt.toString());
            int scc = 0;
            if (search != null && !search.equals("")) {
                pstmt.setString(++scc, "%" + (search) + "%");
                pstmt.setString(++scc, "%" + (search) + "%");
                pstmt.setString(++scc, "%" + (search) + "%");
            }
            if (statusIndex > 0)
                pstmt.setInt(++scc, statusIndex);
            if(assettypeIdIndex > 0)
                pstmt.setInt(++scc, assettypeIdIndex);
            rs = pstmt.executeQuery();
            int pcategoryId, status, qcount;
            String name, assettypeName, deptName;
            while (rs.next())
            {
                pcategoryId = rs.getInt(1);
                name = rs.getString(2) != null ? rs.getString(2) : "";
                status = rs.getInt(3);
                assettypeName = rs.getString(4) != null ? rs.getString(4) : "";
                deptName = rs.getString(5) != null ? rs.getString(5) : "";
                qcount = rs.getInt(6);
                list.add(new PcategoryInfo(pcategoryId, name, assettypeName, deptName, qcount, status));
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
    
    public ArrayList getQuestionList(int pcategoryId, String search) 
    {
        ArrayList pcategorys = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("select t_pquestion.i_pquestionid, t_pquestion.s_name, t_pquestion.i_status ");
        sb.append("from t_pquestion where i_pcategoryid = ? AND t_pquestion.i_status = 1 ");
        if(search != null && !search.equals(""))
            sb.append(" and t_pquestion.s_name like ? ");
        sb.append("ORDER BY t_pquestion.i_status, t_pquestion.s_name");
        String query = (sb.toString()).intern();
        sb.setLength(0);
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, pcategoryId);
            if(search != null && !search.equals(""))
                pstmt.setString(2, "%"+search+"%");
            logger.info("getQuestionList :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String name;
            int pquestionId, status;
            while (rs.next())
            {
                pquestionId = rs.getInt(1);
                name = rs.getString(2) != null ? rs.getString(2) : "";  
                status = rs.getInt(3);
                pcategorys.add(new PcategoryInfo(pquestionId, name, status));
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return pcategorys;
    }
    
    public ArrayList getSuggestedList(int pcategoryId, int assettypeId) 
    {
        ArrayList pcategorys = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT DISTINCT t_pquestion.s_name FROM t_pquestion ");
        sb.append("LEFT JOIN t_pcategory ON (t_pcategory.i_pcategoryid = t_pquestion.i_pcategoryid) ");
        sb.append("WHERE t_pquestion.i_pcategoryid != ? AND t_pcategory.i_assettypeid = ? AND t_pquestion.s_name ");
        sb.append("NOT IN (select t_pquestion.s_name FROM t_pquestion LEFT JOIN t_pcategory ON (t_pcategory.i_pcategoryid = t_pquestion.i_pcategoryid) ");
        sb.append("WHERE t_pcategory.i_pcategoryid = ? AND t_pcategory.i_assettypeid = ? ORDER BY t_pquestion.s_name) ORDER BY t_pquestion.s_name");
        String query = (sb.toString()).intern();
        sb.setLength(0);
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, pcategoryId);
            pstmt.setInt(2, assettypeId);
            pstmt.setInt(3, pcategoryId);
            pstmt.setInt(4, assettypeId);
            logger.info("getSuggestedList :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String name;
            while (rs.next())
            {
                name = rs.getString(1) != null ? rs.getString(1) : "";   
                pcategorys.add(new PcategoryInfo(0, name));
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return pcategorys;
    }
    
    public int createQuestion(int pcategoryId, String name, int userId)
    {
        int cc = 0;
        try 
        {
            StringBuilder sb = new StringBuilder();
            sb.append("insert into t_pquestion ");
            sb.append("(i_pcategoryid, s_name, i_status, i_userid, ts_regdate, ts_moddate) ");
            sb.append("values (?, ?, ?, ?, ?, ?)");
            String query = (sb.toString()).intern();
            sb.setLength(0);

            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, pcategoryId);
            pstmt.setString(2, name);
            pstmt.setInt(3, 1);
            pstmt.setInt(4, userId);
            pstmt.setString(5, currDate1());
            pstmt.setString(6, currDate1());
            print(this, "createPcategory :: " + pstmt.toString());
            cc = pstmt.executeUpdate();
        } catch (Exception exception) {
            print(this, "createPcategory :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return cc;
    }

    public int updatequestion(int pquestionId, String name, int userId) 
    {
        int cc = 0;
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("update t_pquestion set ");
            sb.append("s_name = ?, ");
            sb.append("i_userid = ?, ");
            sb.append("ts_moddate = ? ");
            sb.append("where i_pquestionid = ? ");
            String query = (sb.toString()).intern();
            sb.setLength(0);

            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, name);
            pstmt.setInt(2, userId); 
            pstmt.setString(3, currDate1());
            pstmt.setInt(4, pquestionId);
            print(this, "updatequestion :: " + pstmt.toString());
            cc = pstmt.executeUpdate();
        } catch (Exception exception) {
            print(this, "updatequestion :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return cc;
    }

    public int checkDuplicacyQuestion(int pquestionId, int pcategoryId, String name) 
    {
        int ck = 0;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT i_pquestionid FROM t_pquestion WHERE i_pcategoryid = ? AND s_name = ? AND i_status IN (1, 2) ");
        if (pquestionId > 0) {
            sb.append("AND i_pquestionid !=? ");
        }
        String query = (sb.toString()).intern();
        sb.setLength(0);
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            int scc = 0;
            pstmt.setInt(++scc, pcategoryId);
            pstmt.setString(++scc, name);
            if (pquestionId > 0) {
                pstmt.setInt(++scc, pquestionId);
            }
            print(this, "checkDuplicacyQuestion :: " + pstmt.toString());
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

    public int deleteQuestion(int pquestionId, int userId) 
    {
        int cc = 0;
        try {
            conn = getConnection();
            StringBuilder sb = new StringBuilder();
            sb.append("update t_pquestion set ");
            sb.append("i_status = ?, ");
            sb.append("i_userid = ?, ");
            sb.append("ts_moddate = ? ");
            sb.append("where i_pquestionid = ? ");
            String query = (sb.toString()).intern();
            sb.setLength(0);

            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, 3);
            pstmt.setInt(2, userId);
            pstmt.setString(3, currDate1());
            pstmt.setInt(4, pquestionId);            
            cc = pstmt.executeUpdate();
        } catch (Exception exception) {
            print(this, "deleteQuestion :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return cc;
    }
    
    public Collection getAssettypes()
    {
        Collection coll = new LinkedList();
        coll.add(new PcategoryInfo(-1, "All"));
        String xml_path = getMainPath("json_path");
        String str = readHTMLFile("assettype.json", xml_path);
        JSONArray arr = new JSONArray(str);
        if(arr != null)
        {
            int len = arr.length();
            for(int i = 0; i < len; i++)
            {
                JSONObject jobj = arr.optJSONObject(i);
                if(jobj != null)
                {
                    coll.add(new PcategoryInfo(jobj.optInt("id"), jobj.optString("name")));
                }
            }
        }
        return coll;
    }
    
    public Collection getPdepts(int assettypeId)
    {
        Collection coll = new LinkedList();
        if(assettypeId > 0)
        {
            coll.add(new PcategoryInfo(-1, "All"));
            String xml_path = getMainPath("json_path");
            String str = readHTMLFile("pdept.json", xml_path);
            JSONArray arr = new JSONArray(str);
            if(arr != null)
            {
                int len = arr.length();
                for(int i = 0; i < len; i++)
                {
                    JSONObject jobj = arr.optJSONObject(i);
                    if(jobj != null && jobj.optInt("assettypeId") == assettypeId)
                    {
                        coll.add(new PcategoryInfo(jobj.optInt("id"), jobj.optString("name")));
                    }
                }
            }
        }
        else
        {
            coll.add(new PcategoryInfo(-1, "All"));
        }
        return coll;
    }
}
