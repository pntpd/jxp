package com.web.jxp.matrix;

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

public class Matrix extends Base {

    private static final Logger logger = Logger.getLogger(Class.class.getName());
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    public ArrayList getMatrixByName(String search, int next, int count) {
        ArrayList list = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("select t_matrix.i_matrixid, t_matrix.s_name, t_matrix.i_status, t_assettype.s_name, t1.ct, t2.ct ");
        sb.append("FROM t_matrix left join t_assettype on (t_assettype.i_assettypeid = t_matrix.i_assettypeid) ");
        sb.append("left join (select i_assettypeid, count(1) as ct from t_position where i_status = 1 group by i_assettypeid) as t1 on (t1.i_assettypeid = t_assettype.i_assettypeid) ");
        sb.append("left join (select i_matrixid, count(1) as ct from t_matrixposition where i_status = 1 group by i_matrixid) as t2 on (t2.i_matrixid = t_matrix.i_matrixid) where 0 = 0 ");
        if (search != null && !search.equals("")) {
            sb.append("and (t_matrix.s_name like ? OR t_assettype.s_name like ?) ");
        }
        sb.append(" order by t_matrix.i_status, t_matrix.s_name ");
        if (count > 0) {
            sb.append(" limit ").append(next * count).append(", ").append(count);
        }
        String query = (sb.toString()).intern();
        sb.setLength(0);

        sb.append("select count(1) FROM t_matrix left join t_assettype on (t_assettype.i_assettypeid = t_matrix.i_assettypeid) where 0 = 0 ");
        if (search != null && !search.equals("")) {
            sb.append("and (t_matrix.s_name like ? OR t_assettype.s_name like ?) ");
        }
        String countquery = (sb.toString()).intern();
        sb.setLength(0);
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            int scc = 0;
            if (search != null && !search.equals("")) {
                pstmt.setString(++scc, "%" + (search) + "%");
                pstmt.setString(++scc, "%" + (search) + "%");
            }
            logger.info("getMatrixByName :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String name, assettypeName;
            int matrixId, status, pcount, editcount;
            while (rs.next()) 
            {
                matrixId = rs.getInt(1);
                name = rs.getString(2) != null ? rs.getString(2) : "";
                status = rs.getInt(3);
                assettypeName = rs.getString(4) != null ? rs.getString(4) : "";
                pcount = rs.getInt(5);
                editcount = rs.getInt(6);
                list.add(new MatrixInfo(matrixId, name, assettypeName, pcount, editcount, status));
            }
            rs.close();

            pstmt = conn.prepareStatement(countquery);
            scc = 0;
            if (search != null && !search.equals("")) {
                pstmt.setString(++scc, "%" + (search) + "%");
                pstmt.setString(++scc, "%" + (search) + "%");
            }
            //print(this,"getMatrixByName :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                matrixId = rs.getInt(1);
                list.add(new MatrixInfo(matrixId, "", "", 0, 0, 0));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return list;
    }

    public ArrayList getFinalRecord(ArrayList l, String colId, int tp) {
        ArrayList list = null;
        HashMap record = null;
        MatrixInfo info;
        int total = 0;
        if (l != null) {
            total = l.size();
        }
        try {
            if (total > 0) {
                record = new HashMap();
                int i;
                for (i = 0; i < total; i++) {
                    info = (MatrixInfo) l.get(i);
                    record.put(getInfoValue(info, colId), info);
                }
            }
            Map map = null;
            if (colId.equals("1") || colId.equals("4")) {
                map = sortById(record, tp);
            } else {
                map = sortByName(record, tp);
            }
            Iterator it = map.keySet().iterator();
            list = new ArrayList();
            MatrixInfo rInfo;
            while (it.hasNext()) {
                String key = (String) it.next();
                int i;
                for (i = 0; i < total; i++) {
                    rInfo = (MatrixInfo) l.get(i);
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
    public String getInfoValue(MatrixInfo info, String i) {
        String infoval = "";
        if (i != null && i.equals("1")) {
            infoval = "" + info.getMatrixId();
        }
        if (i != null && i.equals("2")) {
            infoval = info.getName() != null ? info.getName() : "";
        } else if (i != null && i.equals("3")) {
            infoval = info.getAssettypeName() != null ? info.getAssettypeName() : "";
        } else if (i != null && i.equals("4")) {
            infoval = "" + info.getPcount();
        }
        return infoval;
    }

    public MatrixInfo getMatrixDetailById(int matrixId) {
        MatrixInfo info = null;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT s_name, i_assettypeid, i_status, s_description FROM t_matrix where i_matrixid = ? ");
        String query = sb.toString().intern();
        sb.setLength(0);
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, matrixId);
            print(this, "getMatrixDetailById :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String name, description;
            int assettypeId, status;
            while (rs.next()) 
            {
                name = rs.getString(1) != null ? rs.getString(1) : "";
                assettypeId = rs.getInt(2);
                status = rs.getInt(3);
                description = rs.getString(4) != null ? rs.getString(4) : "";
                info = new MatrixInfo(matrixId, name, assettypeId, description, status, 0);
            }
        } catch (Exception exception) {
            print(this, "getMatrixDetailById :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return info;
    }

    public MatrixInfo getMatrixDetailByIdforDetail(int matrixId) {
        MatrixInfo info = null;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT t_matrix.s_name, t_matrix.i_status, t_assettype.s_name, t_matrix.s_description, t_matrix.i_assettypeid FROM t_matrix ");
        sb.append("left join t_assettype on (t_assettype.i_assettypeid = t_matrix.i_assettypeid) ");
        sb.append("where i_matrixid = ? ");
        String query = sb.toString().intern();
        sb.setLength(0);
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, matrixId);
            print(this, "getMatrixDetailByIdforDetail :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String name, assettypeName;
            int status;
            while (rs.next()) 
            {
                name = rs.getString(1) != null ? rs.getString(1) : "";
                status = rs.getInt(2);
                assettypeName = rs.getString(3) != null ? rs.getString(3) : "";
                String description = rs.getString(4) != null ? rs.getString(4) : "";
                int assettypeId = rs.getInt(5);
                info = new MatrixInfo(matrixId, name, assettypeName, description, status, assettypeId);
            }
            rs.close();
        } catch (Exception exception) {
            print(this, "getMatrixDetailByIdforDetail :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return info;
    }

    public int createMatrix(MatrixInfo info) {
        int matrixId = 0;
        try 
        {
            StringBuilder sb = new StringBuilder();
            sb.append("insert into t_matrix ");
            sb.append("(s_name, i_assettypeid, s_description, i_status, i_userid, ts_regdate, ts_moddate) ");
            sb.append("values (?, ?, ?, ?, ?, ?, ?)");
            String query = (sb.toString()).intern();
            sb.setLength(0);

            conn = getConnection();
            pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            int scc = 0;
            pstmt.setString(++scc, info.getName());
            pstmt.setInt(++scc, info.getAssettypeId());
            pstmt.setString(++scc, info.getDescription());
            pstmt.setInt(++scc, info.getStatus());
            pstmt.setInt(++scc, info.getUserId());
            pstmt.setString(++scc, currDate1());
            pstmt.setString(++scc, currDate1());
            //print(this,"createMatrix :: " + pstmt.toString());
            pstmt.executeUpdate();
            rs = pstmt.getGeneratedKeys();
            while (rs.next()) {
                matrixId = rs.getInt(1);
            }
        } catch (Exception exception) {
            print(this, "createMatrix :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return matrixId;
    }

    public int updateMatrix(MatrixInfo info) {
        int cc = 0;
        try 
        {
            StringBuilder sb = new StringBuilder();
            sb.append("update t_matrix set ");
            sb.append("s_name = ?, ");
            sb.append("s_description = ?, ");
            sb.append("i_assettypeid = ?, ");
            sb.append("i_status = ?, ");
            sb.append("i_userid = ?, ");
            sb.append("ts_moddate = ? ");
            sb.append("where i_matrixid = ? ");
            String query = (sb.toString()).intern();
            sb.setLength(0);

            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            int scc = 0;
            pstmt.setString(++scc, info.getName());
            pstmt.setString(++scc, info.getDescription());
            pstmt.setInt(++scc, info.getAssettypeId());
            pstmt.setInt(++scc, info.getStatus());
            pstmt.setInt(++scc, info.getUserId());
            pstmt.setString(++scc, currDate1());
            pstmt.setInt(++scc, info.getMatrixId());
            //print(this,"updateMatrix :: " + pstmt.toString());
            cc = pstmt.executeUpdate();
        } catch (Exception exception) {
            print(this, "updateMatrix :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return cc;
    }

    public int checkDuplicacy(int matrixId, String name, int assettypeId) {
        int ck = 0;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT i_matrixid FROM t_matrix WHERE (s_name = ? OR i_assettypeid =?) AND i_status IN (1, 2) ");
        if (matrixId > 0) {
            sb.append("AND i_matrixid !=? ");
        }
        String query = (sb.toString()).intern();
        sb.setLength(0);
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            int scc = 0;
            pstmt.setString(++scc, name);
            pstmt.setInt(++scc, assettypeId);
            if (matrixId > 0) {
                pstmt.setInt(++scc, matrixId);
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

    public int deleteMatrix(int matrixId, int userId, int status, String ipAddrStr, String iplocal) 
    {
        int cc = 0;
        try 
        {
            conn = getConnection();
            StringBuilder sb = new StringBuilder();
            sb.append("update t_matrix set ");
            sb.append("i_status = ?, ");
            sb.append("i_userid = ?, ");
            sb.append("ts_moddate = ? ");
            sb.append("where i_matrixid = ? ");
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
            pstmt.setInt(++scc, matrixId);

            cc = pstmt.executeUpdate();
        } catch (Exception exception) {
            print(this, "deleteMatrix :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        createHistoryAccess(null, userId, ipAddrStr, iplocal, "Data Deleted", 64, matrixId);
        return cc;
    }

    public ArrayList getListForExcel(String search) {
        ArrayList list = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("select t_matrix.s_name, t_matrix.i_status, t_assettype.s_name, t1.ct ");
        sb.append("FROM t_matrix left join t_assettype on (t_assettype.i_assettypeid = t_matrix.i_assettypeid) ");
        sb.append("left join (select i_assettypeid, count(1) as ct from t_position where i_status = 1 group by i_assettypeid) as t1 on (t1.i_assettypeid = t_assettype.i_assettypeid) where 0 = 0 ");
        if (search != null && !search.equals("")) {
            sb.append("and (t_matrix.s_name like ? OR t_assettype.s_name like ?) ");
        }
        sb.append(" order by t_matrix.i_status, t_matrix.s_name ");
        String query = sb.toString().intern();
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
            }
            Common.print(this, "getListForExcel :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String name, assettypeName;
            int pcount, status;
            while (rs.next()) 
            {
                name = rs.getString(1) != null ? rs.getString(1) : "";
                status = rs.getInt(2);
                assettypeName = rs.getString(3) != null ? rs.getString(3) : "";
                pcount = rs.getInt(4);
                list.add(new MatrixInfo(0, name, assettypeName, status, pcount, 0));
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

    public int getMaxId() {
        int maxId = 0;
        String query = ("SELECT MAX(i_matrixid) FROM t_matrix");
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            while (rs.next()) {
                maxId = rs.getInt(1);
            }
        } catch (Exception exception) {
            print(this, "getMaxId :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return maxId + 1;
    }

    public ArrayList getPositionList(int matrixId, int assettypeId) {
        ArrayList list = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("select t_matrixposition.i_matrixpositionid, t_position.s_name, t1.ct1, t1.ct2, t1.ct3, t_grade.s_name, t_position.i_positionid, t_matrixposition.i_status ");
        sb.append("from t_position left join t_grade on (t_grade.i_gradeid = t_position.i_gradeid) ");
        sb.append("left join t_matrixposition on (t_position.i_positionid = t_matrixposition.i_positionid and t_matrixposition.i_matrixid = ?) ");
        sb.append("left join (select  t_matrixdetail.i_matrixpositionid, count(DISTINCT(t_matrixdetail.i_categoryid)) as ct1, count(DISTINCT(i_subcategoryid)) as ct2, count(DISTINCT(i_courseid)) as ct3 from t_matrixdetail left join t_matrixposition ");
        sb.append("on (t_matrixdetail.i_matrixpositionid = t_matrixposition.i_matrixpositionid) left join t_matrix on (t_matrix.i_matrixid = t_matrixposition.i_matrixid) left join t_category on (t_category.i_categoryid = t_matrixdetail.i_categoryid) "); 
        sb.append("where t_matrix.i_status = 1 and t_category.i_status = 1 group by  t_matrixdetail.i_matrixpositionid) as t1 on (t1.i_matrixpositionid = t_matrixposition.i_matrixpositionid ) ");
        sb.append("where t_position.i_status = 1 and t_position.i_assettypeid = ? ");
        String query = (sb.toString()).intern();
        sb.setLength(0);
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, matrixId);
            pstmt.setInt(2, assettypeId);
            logger.info("getPositionList :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String name, gradeName;
            int matrixpositionId, count1, count2, count3, positionId, status;
            while (rs.next()) 
            {
                matrixpositionId = rs.getInt(1);
                name = rs.getString(2) != null ? rs.getString(2) : "";
                count1 = rs.getInt(3);
                count2 = rs.getInt(4);
                count3 = rs.getInt(5);
                gradeName = rs.getString(6) != null ? rs.getString(6) : "";
                positionId = rs.getInt(7);
                status = rs.getInt(8);
                if (!gradeName.equals("")) {
                    name += " - " + gradeName;
                }
                list.add(new MatrixInfo(matrixpositionId, name, count1, count2, count3, positionId, status));
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return list;
    }

    public MatrixInfo getPositionInfo(int matrixpositionId) {
        MatrixInfo info = null;
        StringBuilder sb = new StringBuilder();
        sb.append("select t_position.s_name, t_grade.s_name ");
        sb.append("from t_matrixposition left join t_position on (t_position.i_positionid = t_matrixposition.i_positionid) ");
        sb.append("left join t_grade on (t_grade.i_gradeid = t_position.i_gradeid) ");
        sb.append("where t_matrixposition.i_matrixpositionid = ?");
        String query = (sb.toString()).intern();
        sb.setLength(0);
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, matrixpositionId);
            logger.info("getPositionInfo :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String name, gradeName;
            while (rs.next()) {
                name = rs.getString(1) != null ? rs.getString(1) : "";
                gradeName = rs.getString(2) != null ? rs.getString(2) : "";
                if (!gradeName.equals("")) {
                    name += " - " + gradeName;
                }
                info = new MatrixInfo(0, name);
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return info;
    }

    public Collection getCategories() 
    {
        Collection coll = new LinkedList();
        coll.add(new MatrixInfo(-1, "Select Category"));
        String query = ("select i_categoryid, s_name from t_category where i_status = 1 order by s_name");
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            logger.info("getCategories :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            int id;
            String name;
            while (rs.next())
            {
                id = rs.getInt(1);
                name = rs.getString(2) != null ? rs.getString(2) : "";
                coll.add(new MatrixInfo(id, name));
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return coll;
    }

    public ArrayList getSubCategoryList(int categoryId) {
        ArrayList list = new ArrayList();
        String query = ("select i_subcategoryid, s_name from t_subcategory where i_status = 1 and i_categoryid = ? order by s_name");
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, categoryId);
            logger.info("getSubCategoryList :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            int id;
            String name;
            while (rs.next()) 
            {
                id = rs.getInt(1);
                name = rs.getString(2) != null ? rs.getString(2) : "";
                list.add(new MatrixInfo(id, name));
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return list;
    }

    public ArrayList getCourseList(int matrixpositionId, int categoryId) {
        ArrayList list = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("select d.i_matrixdetailid, t_coursename.s_name, t_course.i_subcategoryid, t_course.i_courseid ");
        sb.append("from t_course left join t_coursename on (t_coursename.i_coursenameid = t_course.i_coursenameid) ");
        sb.append("left join t_matrixdetail as d on (t_course.i_courseid = d.i_courseid and d.i_matrixpositionid = ? and d.i_categoryid = ?) ");
        sb.append("where t_course.i_status = 1");
        String query = sb.toString();
        sb.setLength(0);
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, matrixpositionId);
            pstmt.setInt(2, categoryId);
            logger.info("getCourseList :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String name;
            int matrixdetailId, subcategoryid, courseId;
            while (rs.next()) 
            {
                matrixdetailId = rs.getInt(1);
                name = rs.getString(2) != null ? rs.getString(2) : "";
                subcategoryid = rs.getInt(3);
                courseId = rs.getInt(4);
                list.add(new MatrixInfo(name, matrixdetailId, categoryId, subcategoryid, courseId));
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return list;
    }

    public ArrayList getListFromList(ArrayList list, int subcategoryId) {
        ArrayList l = new ArrayList();
        int size = list.size();
        for (int i = 0; i < size; i++) {
            MatrixInfo info = (MatrixInfo) list.get(i);
            if (info != null && info.getSubcategoryId() == subcategoryId) {
                l.add(info);
            }
        }
        return l;
    }

    public boolean checkinlist(ArrayList list, int subcategoryId) {
        boolean b = false;
        int size = list.size();
        for (int i = 0; i < size; i++) {
            MatrixInfo info = (MatrixInfo) list.get(i);
            if (info != null && info.getMatrixdetailId() > 0 && info.getSubcategoryId() == subcategoryId) {
                b = true;
                break;
            }
        }
        return b;
    }

    public boolean checkinlistcat(ArrayList list, int categoryId) {
        boolean b = false;
        int size = list.size();
        for (int i = 0; i < size; i++) {
            MatrixInfo info = (MatrixInfo) list.get(i);
            if (info != null && info.getMatrixdetailId() > 0 && info.getCategoryId() == categoryId) {
                b = true;
                break;
            }
        }
        return b;
    }

    public String getCollection1(ArrayList list, int id) {
        int size = list.size();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < size; i++) {
            MatrixInfo info = (MatrixInfo) list.get(i);
            if (info != null && info.getDdlValue() == id) {
                sb.append("<option selected value='" + info.getDdlValue() + "'>" + (info.getDdlLabel() != null ? info.getDdlLabel() : "") + "</option>");
            } else {
                sb.append("<option value='" + info.getDdlValue() + "'>" + (info.getDdlLabel() != null ? info.getDdlLabel() : "") + "</option>");
            }
        }
        String str = sb.toString();
        sb.setLength(0);
        return str;
    }

    public int createMatrixDetail(int categoryId, int matrixpositionId, int subcategoryId[], int courseId[], int userId) {
        int matrixId = 0;
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("insert into t_matrixdetail ");
            sb.append("(i_matrixpositionid, i_categoryid, i_subcategoryid, i_courseid, i_status, i_userid, ts_regdate, ts_moddate) ");
            sb.append("values (?, ?, ?, ?, ?, ?, ?, ?)");
            String query = (sb.toString()).intern();
            sb.setLength(0);
            conn = getConnection();

            String delquery = "delete from t_matrixdetail where i_matrixpositionid = ? and i_categoryid = ? ";
            pstmt = conn.prepareStatement(delquery);
            pstmt.setInt(1, matrixpositionId);
            pstmt.setInt(2, categoryId);
            print(this, "createMatrixDetail delete all :: " + pstmt.toString());
            pstmt.executeUpdate();

            int len = courseId.length;
            pstmt = conn.prepareStatement(query);
            for (int i = 0; i < len; i++) {
                if (subcategoryId[i] > 0 && courseId[i] > 0) {
                    int scc = 0;
                    pstmt.setInt(++scc, matrixpositionId);
                    pstmt.setInt(++scc, categoryId);
                    pstmt.setInt(++scc, subcategoryId[i]);
                    pstmt.setInt(++scc, courseId[i]);
                    pstmt.setInt(++scc, 1);
                    pstmt.setInt(++scc, userId);
                    pstmt.setString(++scc, currDate1());
                    pstmt.setString(++scc, currDate1());
                    print(this, "createMatrixDetail :: " + pstmt.toString());
                    pstmt.executeUpdate();
                }
            }
        } catch (Exception exception) {
            print(this, "createMatrixDetail :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return matrixId;
    }

    public int createMatrixPosition(int matrixId, int positionId, int userId) {
        int matrixpositionId = 0;
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("insert into t_matrixposition ");
            sb.append("(i_matrixid, i_positionid, i_status, i_userid, ts_regdate, ts_moddate) ");
            sb.append("values (?, ?, ?, ?, ?, ?)");
            String query = (sb.toString()).intern();
            sb.setLength(0);

            conn = getConnection();
            pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            pstmt.setInt(1, matrixId);
            pstmt.setInt(2, positionId);
            pstmt.setInt(3, 1);
            pstmt.setInt(4, userId);
            pstmt.setString(5, currDate1());
            pstmt.setString(6, currDate1());
            //print(this,"createMatrixPosition :: " + pstmt.toString());
            pstmt.executeUpdate();
            rs = pstmt.getGeneratedKeys();
            while (rs.next()) {
                matrixpositionId = rs.getInt(1);
            }
        } catch (Exception exception) {
            print(this, "matrixpositionId :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return matrixpositionId;
    }

    public int deleteMatrixDetail(int matrixpositionId, int userId, int status) {
        int cc = 0;
        try {
            conn = getConnection();
            StringBuilder sb = new StringBuilder();
            sb.append("update t_matrixposition set ");
            sb.append("i_status = ?, ");
            sb.append("i_userid = ?, ");
            sb.append("ts_moddate = ? ");
            sb.append("where i_matrixpositionid = ? ");
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
            pstmt.setInt(++scc, matrixpositionId);
            print(this, "deleteMatrixDetail :: " + pstmt.toString());
            cc = pstmt.executeUpdate();
        } catch (Exception exception) {
            print(this, "deleteMatrixDetail :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return cc;
    }
}
