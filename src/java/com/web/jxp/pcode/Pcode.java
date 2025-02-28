package com.web.jxp.pcode;

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

public class Pcode extends Base {

    private static final Logger logger = Logger.getLogger(Class.class.getName());
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    public ArrayList getPcodeByName(String search, int pdeptIdIndex, int assettypeIdIndex, int next, int count) 
    {
        ArrayList pcodes = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("select t_pcode.i_pcodeid, t_pcode.s_code, t_pcode.s_role, t_assettype.s_name, t_pdept.s_name, t_pcode.i_status, t1.ct ");
        sb.append("from t_pcode ");
        sb.append("left join (select t_pcodequestion.i_pcodeid, count(DISTINCT(t_pcodequestion.i_pcategoryid)) as ct from t_pcodequestion ");
        sb.append("left join t_pcategory on (t_pcategory.i_pcategoryid = t_pcodequestion.i_pcategoryid) ");
        sb.append("where t_pcategory.i_status = 1 group by t_pcodequestion.i_pcodeid) as t1 on (t1.i_pcodeid = t_pcode.i_pcodeid ) ");
        sb.append("left join t_assettype on (t_assettype.i_assettypeid = t_pcode.i_assettypeid) ");
        sb.append("left join t_pdept on (t_pdept.i_pdeptid = t_pcode.i_pdeptid) ");
        sb.append("where 0 = 0 ");
        if (search != null && !search.equals("")) {
            sb.append("and (t_pcode.s_code like ? OR t_pcode.s_role like ? OR t_assettype.s_name like ? OR t_pdept.s_name like ?) ");
        }
        if (pdeptIdIndex > 0) {
            sb.append("and t_pcode.i_pdeptid = ? ");
        }
        if(assettypeIdIndex > 0)
            sb.append(" and t_pcode.i_assettypeid = ? ");
        sb.append(" order by t_pcode.i_status, t_assettype.s_name, t_pdept.s_name, t_pcode.s_role ");
        if (count > 0) {
            sb.append(" limit ").append(next * count).append(", ").append(count);
        }
        String query = (sb.toString()).intern();
        sb.setLength(0);

        sb.append("select count(1) FROM t_pcode ");
        sb.append("left join t_assettype on (t_assettype.i_assettypeid = t_pcode.i_assettypeid) ");
        sb.append("left join t_pdept on (t_pdept.i_pdeptid = t_pcode.i_pdeptid) ");
        sb.append("where 0 = 0 ");
        if (search != null && !search.equals("")) {
            sb.append("and (t_pcode.s_code like ? OR t_pcode.s_role like ? OR t_assettype.s_name like ? OR t_pdept.s_name like ?) ");
        }
        if (pdeptIdIndex > 0) {
            sb.append("and t_pcode.i_pdeptid = ? ");
        }
        if(assettypeIdIndex > 0)
            sb.append(" and t_pcode.i_assettypeid = ? ");
        String countquery = (sb.toString()).intern();
        sb.setLength(0);
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            int scc = 0;
            if (search != null && !search.equals("")) 
            {
                pstmt.setString(++scc, "%" + (search) + "%");
                pstmt.setString(++scc, "%" + (search) + "%");
                pstmt.setString(++scc, "%" + (search) + "%");
                pstmt.setString(++scc, "%" + (search) + "%");
            }
            if (pdeptIdIndex > 0) {
                pstmt.setInt(++scc, pdeptIdIndex);
            }
            if(assettypeIdIndex > 0)
                pstmt.setInt(++scc, assettypeIdIndex);
            logger.info("getPcodeByName :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String name, code, assettypeName, deptName;
            int pcodeId, status, qcount;
            while (rs.next())
            {
                pcodeId = rs.getInt(1);
                code = rs.getString(2) != null ? rs.getString(2) : "";  
                name = rs.getString(3) != null ? rs.getString(3) : "";                
                assettypeName = rs.getString(4) != null ? rs.getString(4) : "";
                deptName = rs.getString(5) != null ? rs.getString(5) : "";
                status = rs.getInt(6);
                qcount = rs.getInt(7);
                pcodes.add(new PcodeInfo(pcodeId, code, name, assettypeName, deptName, status, qcount));
            }
            rs.close();

            pstmt = conn.prepareStatement(countquery);
            scc = 0;
            if (search != null && !search.equals("")) {
                pstmt.setString(++scc, "%" + (search) + "%");
                pstmt.setString(++scc, "%" + (search) + "%");
                pstmt.setString(++scc, "%" + (search) + "%");
                pstmt.setString(++scc, "%" + (search) + "%");
            }
            if (pdeptIdIndex > 0) {
                pstmt.setInt(++scc, pdeptIdIndex);
            }
            if(assettypeIdIndex > 0)
                pstmt.setInt(++scc, assettypeIdIndex);
            //print(this,"getPcodeByName :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                pcodeId = rs.getInt(1);
                pcodes.add(new PcodeInfo(pcodeId, "", "", "", "", 0, 0));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return pcodes;
    }

    public ArrayList getFinalRecord(ArrayList l, String colId, int tp) {
        ArrayList list = null;
        HashMap record = null;
        PcodeInfo info;
        int total = 0;
        if (l != null) {
            total = l.size();
        }
        try {
            if (total > 0) {
                record = new HashMap();
                int i;
                for (i = 0; i < total; i++) {
                    info = (PcodeInfo) l.get(i);
                    record.put(getInfoValue(info, colId), info);
                }
            }
            Map map = sortByName(record, tp);
            Iterator it = map.keySet().iterator();
            list = new ArrayList();
            PcodeInfo rInfo;
            while (it.hasNext()) {
                String key = (String) it.next();
                int i;
                for (i = 0; i < total; i++) {
                    rInfo = (PcodeInfo) l.get(i);
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
    public String getInfoValue(PcodeInfo info, String i) {
        String infoval = "";
        if (i != null && i.equals("1")) {
            infoval = info.getName() != null ? info.getName() : "";
        }
        else if (i != null && i.equals("2")) {
            infoval = info.getCode() != null ? info.getCode() : "";
        }
        else if (i != null && i.equals("3")) {
            infoval = info.getAssettypeName() != null ? info.getAssettypeName() : "";
        }
        else if (i != null && i.equals("4")) {
            infoval = info.getDeptName() != null ? info.getDeptName() : "";
        }
        return infoval;
    }

    public PcodeInfo getPcodeDetailById(int pcodeId) {
        PcodeInfo info = null;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT s_code, s_role, i_status, i_assettypeid, i_pdeptid, s_description FROM t_pcode where i_pcodeid = ? ");
        String query = sb.toString().intern();
        sb.setLength(0);
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, pcodeId);
            print(this, "getPcodeDetailById :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String name;
            int status;
            while (rs.next()) 
            {
                String code = rs.getString(1) != null ? rs.getString(1): "";
                name = rs.getString(2) != null ? rs.getString(2): "";
                status = rs.getInt(3);
                int assettypeId = rs.getInt(4);
                int pdeptId = rs.getInt(5);
                String description = rs.getString(6) != null ? rs.getString(6): "";
                info = new PcodeInfo(pcodeId, code, name, assettypeId, pdeptId, description, status, 0);
            }
        } 
        catch (Exception exception) 
        {
            print(this, "getPcodeDetailById :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return info;
    }

    public PcodeInfo getPcodeDetailByIdforDetail(int pcodeId) 
    {
        PcodeInfo info = null;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT t_pcode.s_code, t_pcode.s_role, t_pcode.i_status, t_assettype.s_name, t_pdept.s_name, ");
        sb.append("t_pcode.s_description, t_pcode.i_assettypeid, t_pcode.i_pdeptid ");
        sb.append("FROM t_pcode left join t_assettype on (t_assettype.i_assettypeid = t_pcode.i_assettypeid) ");
        sb.append("left join t_pdept on (t_pdept.i_pdeptid = t_pcode.i_pdeptid) ");
        sb.append("where i_pcodeid = ?");
        String query = sb.toString().intern();
        sb.setLength(0);
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, pcodeId);
            print(this, "getPcodeDetailByIdforDetail :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String name;
            int status = 0;
            while (rs.next())
            {
                String code = rs.getString(1) != null ? rs.getString(1): "";
                name = rs.getString(2) != null ? rs.getString(2): "";
                status = rs.getInt(3);
                String assettypeName = rs.getString(4) != null ? rs.getString(4): "";
                String deptName = rs.getString(5) != null ? rs.getString(5): "";
                String description = rs.getString(6) != null ? rs.getString(6): "";
                int assettypeId = rs.getInt(7);
                int pdeptId = rs.getInt(8);
                info = new PcodeInfo(pcodeId, code, name, assettypeName, deptName, description, 
                    status, assettypeId, pdeptId);
            }
            rs.close();
        } catch (Exception exception) {
            print(this, "getPcodeDetailByIdforDetail :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return info;
    }

    public int createPcode(PcodeInfo info) {
        int pcodeId = 0;
        try 
        {
            StringBuilder sb = new StringBuilder();
            sb.append("insert into t_pcode ");
            sb.append("(s_code, s_role, i_assettypeid, i_pdeptid, s_description, i_status, i_userid, ts_regdate, ts_moddate) ");
            sb.append("values (?, ?, ?, ?, ?, ?, ?, ?, ?)");
            String query = (sb.toString()).intern();
            sb.setLength(0);

            conn = getConnection();
            pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            int scc = 0;
            pstmt.setString(++scc, (info.getCode()));
            pstmt.setString(++scc, (info.getName()));
            pstmt.setInt(++scc, info.getAssettypeId());
            pstmt.setInt(++scc, info.getPdeptId());
            pstmt.setString(++scc, info.getDescription());
            pstmt.setInt(++scc, info.getStatus());
            pstmt.setInt(++scc, info.getUserId());
            pstmt.setString(++scc, currDate1());
            pstmt.setString(++scc, currDate1());
            print(this, "createPcode :: " + pstmt.toString());
            pstmt.executeUpdate();
            rs = pstmt.getGeneratedKeys();
            while (rs.next()) {
                pcodeId = rs.getInt(1);
            }
            createjson(conn);
        } catch (Exception exception) {
            print(this, "createPcode :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return pcodeId;
    }

    public int updatePcode(PcodeInfo info) {
        int cc = 0;
        try
        {
            StringBuilder sb = new StringBuilder();
            sb.append("update t_pcode set ");
            sb.append("s_code = ?, ");
            sb.append("s_role = ?, ");
            sb.append("i_assettypeid = ?, ");
            sb.append("i_pdeptid = ?, ");
            sb.append("s_description = ?, ");
            sb.append("i_status = ?, ");
            sb.append("i_userid = ?, ");
            sb.append("ts_moddate = ?, ");
            sb.append("i_assettypeid = ? ");
            sb.append("where i_pcodeid = ? ");
            String query = (sb.toString()).intern();
            sb.setLength(0);

            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            int scc = 0;
            pstmt.setString(++scc, info.getCode());
            pstmt.setString(++scc, info.getName());
            pstmt.setInt(++scc, info.getAssettypeId());
            pstmt.setInt(++scc, info.getPdeptId());
            pstmt.setString(++scc, info.getDescription());
            pstmt.setInt(++scc, info.getStatus());
            pstmt.setInt(++scc, info.getUserId());
            pstmt.setString(++scc, currDate1());
            pstmt.setInt(++scc, info.getAssettypeId());
            pstmt.setInt(++scc, info.getPcodeId());
            print(this, "updatePcode :: " + pstmt.toString());
            cc = pstmt.executeUpdate();
            createjson(conn);
        } catch (Exception exception) {
            print(this, "updatePcode :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return cc;
    }

    public int checkDuplicacy(int pcodeId, String code) 
    {
        int ck = 0;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT i_pcodeid FROM t_pcode WHERE s_code = ? AND i_status IN (1, 2) ");
        if (pcodeId > 0) {
            sb.append("AND i_pcodeid !=? ");
        }
        String query = (sb.toString()).intern();
        sb.setLength(0);
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            int scc = 0;
            pstmt.setString(++scc, code);
            if (pcodeId > 0) {
                pstmt.setInt(++scc, pcodeId);
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

    public int deletePcode(int pcodeId, int userId, int status, String ipAddrStr, String iplocal) {
        int cc = 0;
        try 
        {
            conn = getConnection();
            StringBuilder sb = new StringBuilder();
            sb.append("update t_pcode set ");
            sb.append("i_status = ?, ");
            sb.append("i_userid = ?, ");
            sb.append("ts_moddate = ? ");
            sb.append("where i_pcodeid = ? ");
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
            pstmt.setInt(++scc, pcodeId);            
            cc = pstmt.executeUpdate();
            
            createjson(conn);
        } catch (Exception exception) {
            print(this, "deletePcode :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        createHistoryAccess(null, userId, ipAddrStr, iplocal, "Data Updated", 86, pcodeId);
        return cc;
    }

    public ArrayList getExcel(String search, int assettypeIdIndex, int pdeptIdIndex) 
    {
        ArrayList list = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("select t_pcode.i_pcodeid, t_pcode.s_code, t_pcode.s_role, ");
        sb.append(" t_pcode.i_status, t_assettype.s_name, t_pdept.s_name from t_pcode ");
        sb.append("left join (select t_pcodequestion.i_pcodeid, count(DISTINCT(t_pcodequestion.i_pcategoryid)) as ct from t_pcodequestion ");
        sb.append("left join t_pcategory on (t_pcategory.i_pcategoryid = t_pcodequestion.i_pcategoryid) ");
        sb.append("where t_pcategory.i_status = 1 group by t_pcodequestion.i_pcodeid) as t1 on (t1.i_pcodeid = t_pcode.i_pcodeid ) ");
        sb.append("left join t_assettype on (t_assettype.i_assettypeid = t_pcode.i_assettypeid) ");
        sb.append("left join t_pdept on (t_pdept.i_pdeptid = t_pcode.i_pdeptid) ");
        sb.append("where 0 = 0 ");
        if (search != null && !search.equals("")) {
            sb.append("and (t_pcode.s_code like ? OR t_pcode.s_role like ? OR t_assettype.s_name like ? OR t_pdept.s_name like ?) ");
        }
        if (pdeptIdIndex > 0) {
            sb.append("and t_pcode.i_pdeptid = ? ");
        }
        if(assettypeIdIndex > 0)
            sb.append(" and t_pcode.i_assettypeid = ? ");
        sb.append(" order by t_pcode.i_status, t_pcode.s_code  ");

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
                pstmt.setString(++scc, "%" + (search) + "%");
            }
            if (pdeptIdIndex > 0)
                pstmt.setInt(++scc, pdeptIdIndex);
            if(assettypeIdIndex > 0)
                pstmt.setInt(++scc, assettypeIdIndex);
            rs = pstmt.executeQuery();
            int pcodeId, status, qcount;
            String code, name, assettypeName, deptName;
            while (rs.next())
            {
                pcodeId = rs.getInt(1);
                code = rs.getString(2) != null ? rs.getString(2) : "";
                name = rs.getString(3) != null ? rs.getString(3) : "";
                status = rs.getInt(4);                
                assettypeName = rs.getString(5) != null ? rs.getString(5) : "";
                deptName = rs.getString(6) != null ? rs.getString(6) : "";
                qcount = rs.getInt(7);
                list.add(new PcodeInfo(pcodeId, code, name, assettypeName, deptName, status, qcount));
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
    
    public Collection getCategories(int pdeptId) 
    {
        Collection coll = new LinkedList();
        coll.add(new PcodeInfo(-1, "Select Category"));
        String query = ("select i_pcategoryid, s_name from t_pcategory where i_pdeptid = ? and i_status = 1 order by s_name");
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, pdeptId);
            logger.info("getCategories :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            int id;
            String name;
            while (rs.next()) 
            {
                id = rs.getInt(1);
                name = rs.getString(2) != null ? rs.getString(2) : "";
                coll.add(new PcodeInfo(id, name));
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return coll;
    }
    
    public ArrayList getQuestionList(int pcodeId, int pcategoryId) 
    {
        ArrayList list = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("select p.i_pquestionid, p.s_name, pc.i_pcodequestionid ");
        sb.append("from t_pquestion as p left join t_pcodequestion as pc on (p.i_pquestionid = pc.i_pquestionid and pc.i_pcodeid = ? and pc.i_pcategoryid = ?) ");
        sb.append("where p.i_pcategoryid = ? and p.i_status = 1");
        String query = sb.toString();
        sb.setLength(0);
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, pcodeId);
            pstmt.setInt(2, pcategoryId);
            pstmt.setInt(3, pcategoryId);
            logger.info("getQuestionList :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String name;
            int pquestionId, pcodequestionId;
            while (rs.next()) 
            {
                pquestionId = rs.getInt(1);
                name = rs.getString(2) != null ? rs.getString(2) : "";
                pcodequestionId = rs.getInt(3);
                list.add(new PcodeInfo(name, pquestionId, pcodequestionId, pcategoryId));
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return list;
    }
    
    public boolean checkinlistcat(ArrayList list, int categoryId) 
    {
        boolean b = false;
        int size = list.size();
        for (int i = 0; i < size; i++) 
        {
            PcodeInfo info = (PcodeInfo) list.get(i);
            if (info != null && info.getPcodequestionId() > 0 && info.getPcategoryId() == categoryId) {
                b = true;
                break;
            }
        }
        return b;
    }
    
    public int createQuestionDetail(int pcodeId, int categoryId, int questionId[], int userId) 
    {
        int cc = 0;
        try 
        {
            StringBuilder sb = new StringBuilder();
            sb.append("insert into t_pcodequestion ");
            sb.append("(i_pcodeid, i_pcategoryid, i_pquestionid, i_status, i_userid, ts_regdate, ts_moddate) ");
            sb.append("values (?, ?, ?, ?, ?, ?, ?)");
            String query = (sb.toString()).intern();
            sb.setLength(0);
            conn = getConnection();

            String delquery = "delete from t_pcodequestion where i_pcodeid = ? and i_pcategoryid = ? ";
            pstmt = conn.prepareStatement(delquery);
            pstmt.setInt(1, pcodeId);
            pstmt.setInt(2, categoryId);
            print(this, "createQuestionDetail delete all :: " + pstmt.toString());
            pstmt.executeUpdate();

            int len = questionId.length;
            pstmt = conn.prepareStatement(query);
            for (int i = 0; i < len; i++) 
            {
                if (questionId[i] > 0) 
                {
                    pstmt.setInt(1, pcodeId);
                    pstmt.setInt(2, categoryId);
                    pstmt.setInt(3, questionId[i]);
                    pstmt.setInt(4, 1);
                    pstmt.setInt(5, userId);
                    pstmt.setString(6, currDate1());
                    pstmt.setString(7, currDate1());
                    print(this, "createQuestionDetail :: " + pstmt.toString());
                    cc += pstmt.executeUpdate();
                }
            }
        } 
        catch (Exception exception) 
        {
            print(this, "createQuestionDetail :: " + exception.getMessage());
        } 
        finally 
        {
            close(conn, pstmt, rs);
        }
        return cc;
    }
    
    public ArrayList getExcelQuestion(int pcodeId) 
    {
        ArrayList list = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("select t_pquestion.s_name, t_pcategory.s_name, t_pcode.s_code, t_pcode.s_role, t_assettype.s_name, t_pdept.s_name ");
        sb.append("from t_pcodequestion left join t_pquestion on (t_pquestion.i_pquestionid = t_pcodequestion.i_pquestionid) ");
        sb.append("left join t_pcategory on (t_pcategory.i_pcategoryid = t_pcodequestion.i_pcategoryid) ");
        sb.append("left join t_pcode on (t_pcode.i_pcodeid = t_pcodequestion.i_pcodeid) ");
        sb.append("left join t_assettype on (t_assettype.i_assettypeid = t_pcode.i_assettypeid) ");
        sb.append("left join t_pdept on (t_pdept.i_pdeptid = t_pcode.i_pdeptid) ");
        sb.append("where t_pcodequestion.i_pcodeid = ? order by t_assettype.s_name, t_pdept.s_name, t_pcode.s_role, t_pcategory.s_name, t_pquestion.s_name");
        String query = sb.toString().intern();
        sb.setLength(0);
        try
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, pcodeId);
            rs = pstmt.executeQuery();
            String questionName, categoryName, code, role, assettypeName, deptName;            
            while (rs.next())
            {
                questionName = rs.getString(1) != null ? rs.getString(1) : "";
                categoryName = rs.getString(2) != null ? rs.getString(2) : "";
                code = rs.getString(3) != null ? rs.getString(3) : "";
                role = rs.getString(4) != null ? rs.getString(4) : "";
                assettypeName = rs.getString(5) != null ? rs.getString(5) : "";
                deptName = rs.getString(6) != null ? rs.getString(6) : "";
                if(!role.equals(""))
                    code += " | " + role;
                list.add(new PcodeInfo(questionName, categoryName, code, assettypeName, deptName));
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
       String query = ("Select i_pcodeid, i_assettypeid, i_pdeptid, s_code, s_role FROM t_pcode where i_status = 1 order by s_code, s_role ");
        try
        {
            pstmt = conn.prepareStatement(query);
            rs = pstmt.executeQuery();
            int id, assettypeId, pdeptId;
            String code, role;
            JSONArray jarr = new JSONArray();
            while (rs.next())
            {
                id = rs.getInt(1);
                assettypeId = rs.getInt(2);
                pdeptId = rs.getInt(3);
                code = rs.getString(4) != null ? rs.getString(4): "";
                role = rs.getString(5) != null ? rs.getString(5): "";
                JSONObject jobj = new JSONObject();
                jobj.put("id", id);
                jobj.put("assettypeId", assettypeId);
                jobj.put("pdeptId", pdeptId);
                jobj.put("code", code);
                jobj.put("role", role);
                jarr.put(jobj);
            }
            rs.close();
            String str = jarr.toString();
            String filePath = getMainPath("json_path");
            writeHTMLFile(str, filePath, "pcode.json");
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
    
    
    public Collection getPdepts(int assettypeId)
    {
        Collection coll = new LinkedList();
        if(assettypeId > 0)
        {
            coll.add(new PcodeInfo(-1, "All"));
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
                        coll.add(new PcodeInfo(jobj.optInt("id"), jobj.optString("name")));
                    }
                }
            }
        }
        else
        {
            coll.add(new PcodeInfo(-1, "All"));
        }
        return coll;
    }
    
    public Collection getAssettypes()
    {
        Collection coll = new LinkedList();
        coll.add(new PcodeInfo(-1, "All"));
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
                    coll.add(new PcodeInfo(jobj.optInt("id"), jobj.optString("name")));
                }
            }
        }
        return coll;
    }
}
