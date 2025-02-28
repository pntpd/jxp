package com.web.jxp.assessment;

import com.mysql.jdbc.Statement;
import com.web.jxp.assessmentparameter.AssessmentParameter;
import com.web.jxp.assessmentquestion.AssessmentQuestion;
import com.web.jxp.assessmentquestion.AssessmentQuestionInfo;
import com.web.jxp.base.Base;
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
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

public class Assessment extends Base 
{
    private static final Logger logger = Logger.getLogger(Class.class.getName());
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    public ArrayList getAssessmentByName(String search, int assessmentparameterIndex, int questiontypeIndex, int next, int count)
    {
        ArrayList assessments = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT t2.assessmentid, t2.aname, t2.mode, t2.pnames, t2.status FROM  (SELECT t_assessment.i_assessmentid AS assessmentid, t_assessment.s_name AS aname, ");
        sb.append("t_assessment.s_mode AS mode, GROUP_CONCAT(DISTINCT t1.s_name ORDER BY t1.s_name SEPARATOR ', ') AS pnames, t_assessment.i_status AS status ");
        sb.append("FROM t_assessment  ");
        sb.append("LEFT JOIN t_assessmentparameter AS t1 ON find_in_set(t1.i_assessmentparameterid, t_assessment.s_parameterids) ");
        sb.append("LEFT JOIN t_assessmentdetail ON (t_assessment.i_assessmentid = t_assessmentdetail.i_assessmentid ) WHERE 0 = 0 ");
        if(assessmentparameterIndex > 0)
        {
            sb.append("AND FIND_IN_SET(?, s_parameterids) ");
        }
         if(questiontypeIndex > 0)
        {
            sb.append(" AND t_assessmentdetail.i_assessmentquestionid = ? ");
        }
        sb.append("GROUP BY t_assessment.i_assessmentid ) AS t2");
        sb.append( " WHERE 0 = 0 ");
        if(search != null && !search.equals(""))
             sb.append("AND t2.aname LIKE ? OR t2.assessmentid = ? OR t2.mode = ? OR t2.pnames LIKE ? ");

        sb.append(" ORDER BY t2.status, t2.aname ");
        if(count > 0)
            sb.append(" limit ").append(next*count).append(", ").append(count);
        String query = (sb.toString()).intern();
        sb.setLength(0);
        
        sb.append("SELECT COUNT(1) FROM  (SELECT t_assessment.i_assessmentid AS assessmentid, t_assessment.s_name AS aname, ");
        sb.append("t_assessment.s_mode AS mode, GROUP_CONCAT(DISTINCT t1.s_name ORDER BY t1.s_name SEPARATOR ', ') AS pnames, t_assessment.i_status AS status ");
        sb.append("FROM t_assessment  ");
        sb.append("LEFT JOIN t_assessmentparameter AS t1 ON find_in_set(t1.i_assessmentparameterid, t_assessment.s_parameterids) ");
        sb.append("LEFT JOIN t_assessmentdetail ON (t_assessment.i_assessmentid = t_assessmentdetail.i_assessmentid ) WHERE 0 = 0 ");
        if(assessmentparameterIndex > 0)
        {
            sb.append("AND FIND_IN_SET(?, s_parameterids) ");
        }
        if(questiontypeIndex > 0)
        {
            sb.append(" AND t_assessmentdetail.i_assessmentquestionid = ? ");
        }
        sb.append("GROUP BY t_assessment.i_assessmentid ) AS t2");
        sb.append( " WHERE 0 = 0 ");
        
        if(search != null && !search.equals(""))
             sb.append("AND t2.aname LIKE ? OR t2.assessmentid = ? OR t2.mode = ? OR t2.pnames LIKE ? ");
        
        sb.append("ORDER BY t2.status, t2.aname ");
        String countquery = (sb.toString()).intern();
        sb.setLength(0);
        try
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            int scc = 0;
             if (assessmentparameterIndex > 0)
                pstmt.setInt(++scc, assessmentparameterIndex);            
            if (questiontypeIndex > 0)
                pstmt.setInt(++scc, questiontypeIndex);            
            if(search != null && !search.equals(""))
            {
                pstmt.setString(++scc, "%"+(search)+"%");
                pstmt.setString(++scc, search);
                pstmt.setString(++scc, search);
                pstmt.setString(++scc, "%"+(search)+"%");                
            }                       
            logger.log(Level.INFO, "getAssessmentByName :: {0}", pstmt.toString());
            rs = pstmt.executeQuery();
            String name,  paraName,mode;
            int assessmentId, status;
            while (rs.next())
            {
                assessmentId = rs.getInt(1);
                name = rs.getString(2) != null ? rs.getString(2) : "";
                mode = rs.getString(3) != null ? rs.getString(3) : "";
                paraName = rs.getString(4) != null ? rs.getString(4) : "";
                status = rs.getInt(5);                
                assessments.add(new AssessmentInfo(assessmentId, name, paraName, status, 0,mode));
            }
            rs.close();
            
            pstmt = conn.prepareStatement(countquery);
            scc = 0;
             if (assessmentparameterIndex > 0)
                pstmt.setInt(++scc, assessmentparameterIndex);             
            if (questiontypeIndex > 0)
                pstmt.setInt(++scc, questiontypeIndex);            
            if(search != null && !search.equals(""))
            {
                pstmt.setString(++scc, "%"+(search)+"%");
                pstmt.setString(++scc, search);
                pstmt.setString(++scc, search);
                pstmt.setString(++scc, "%"+(search)+"%");
            }           
            print(this,"getAssessmentByName :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            while (rs.next())
            {
                assessmentId = rs.getInt(1);
                assessments.add(new AssessmentInfo(assessmentId, "", "",  0, 0,""));
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
        return assessments;
    }
   
    public ArrayList getFinalRecord(ArrayList l, String colId, int tp)
    {
        ArrayList list = null;
        HashMap record = null;
        AssessmentInfo info;
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
                    info = (AssessmentInfo) l.get (i);
                    record.put(getInfoValue(info, colId), info);
                }
            }
            Map map ;
            if(colId.equals("1"))
                map = sortById(record, tp);
            else 
                map = sortByName(record,tp);
            
            Iterator it = map.keySet().iterator();
            list = new ArrayList();
            AssessmentInfo rInfo;
            while (it.hasNext())
            {
                String key = (String) it.next();
                int i;
                for(i= 0; i < total; i++)
                {
                    rInfo = (AssessmentInfo) l.get(i);
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
    public String getInfoValue(AssessmentInfo info, String i)
    {
        String infoval = "";
        if(i != null && i.equals("1"))
            infoval = info.getAssessmentId()+"";
        if(i != null && i.equals("2"))
            infoval = info.getName() != null ? info.getName() : "";
        if(i != null && i.equals("3"))
            infoval = info.getMode() != null ? info.getMode() : "";
        if(i != null && i.equals("4"))
            infoval = info.getParaName() != null ? info.getParaName() : "";
        return infoval;
    }
    
    public AssessmentInfo getAssessmentDetailById(int assessmentId)
    {
        AssessmentInfo info = null;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT s_name, s_parameterids, s_mode, i_status FROM t_assessment WHERE i_assessmentid = ? ");
        String query = sb.toString().intern();
        sb.setLength(0);
        try
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, assessmentId);
            //print(this,"getAssessmentDetailById :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String name, parameterids, mode;
            int status;
            while (rs.next())
            {
                name = rs.getString(1) != null ? rs.getString(1) : "";
                parameterids = rs.getString(2) != null ? rs.getString(2) : "";
                mode = rs.getString(3) != null ? rs.getString(3) : "";
                status = rs.getInt(4);
                info = new AssessmentInfo( name,parameterids, mode, status); 
            }
        }
        catch (Exception exception)
        {
            print(this,"getAssessmentDetailById :: " + exception.getMessage());
        }
        finally
        {
            close(conn, pstmt, rs);
        }
        return info;
    }
    
    public AssessmentInfo getAssessmentDetailByIdforDetail(int assessmentId)
    {
        AssessmentInfo info = null;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT s_name,  s_parameterids, s_mode, i_status FROM t_assessment WHERE i_assessmentid = ? ");
        String query = sb.toString().intern();
        sb.setLength(0);
        try
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, assessmentId);
            //print(this,"getAssessmentDetailByIdforDetail :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String name, parameterids, mode;
            int status = 0;
            while (rs.next())
            {
                name = rs.getString(1) != null ? rs.getString(1) : "";
                parameterids = rs.getString(2) != null ? rs.getString(2) : "";
                mode = rs.getString(3) != null ? rs.getString(3) : "";
                status = rs.getInt(4); 
                info = new AssessmentInfo( assessmentId,name, parameterids, mode,status); 
            }
            rs.close();            
        }
        catch (Exception exception)
        {
            print(this,"getAssessmentDetailByIdforDetail :: " + exception.getMessage());
        }
        finally
        {
            close(conn, pstmt, rs);
        }
        return info;
    }
    
    public int createAssessment(AssessmentInfo info)
    {
        int assessmentId = 0;
        try
        {
            StringBuilder sb = new StringBuilder();
            sb.append("INSERT INTO t_assessment ");
            sb.append("(s_name, s_parameterids,s_mode,  i_status, i_userid, ts_regdate, ts_moddate) ");
            sb.append("VALUES (?, ?, ?, ?, ?, ?, ?)");
            String query = (sb.toString()).intern();
            sb.setLength(0);

            conn = getConnection();
            pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            int scc = 0;
            pstmt.setString(++scc, (info.getName ()));
            pstmt.setString(++scc, (info.getParameterids()));
            pstmt.setString(++scc, (info.getMode()));
            pstmt.setInt(++scc, info.getStatus());
            pstmt.setInt(++scc, info.getUserId());
            pstmt.setString(++scc, currDate1());
            pstmt.setString(++scc, currDate1());  
            print(this,"createAssessment :: " + pstmt.toString());
            pstmt.executeUpdate();
            rs = pstmt.getGeneratedKeys();
            while (rs.next())
            {
                assessmentId = rs.getInt(1);
            }
            
            ArrayList list = info.getList();
            int list_size = list.size();
            
            for(int i = 0; i<= list_size; i++)
            {
            StringBuilder sb1 = new StringBuilder();
            sb1.append("INSERT INTO t_assessmentdetail ");
            sb1.append("(i_assessmentid, i_assessmentquestionid, i_status, i_userid, ts_regdate, ts_moddate) ");
            sb1.append("VALUES (?, ?, ?, ?, ?, ?)");
            String query1 = (sb1.toString()).intern();
            sb.setLength(0);
            
            pstmt = conn.prepareStatement(query1, Statement.RETURN_GENERATED_KEYS);
             scc = 0;
                int a = (int)list.get(i);
                
            pstmt.setInt(++scc,(assessmentId));
            pstmt.setInt(++scc,(int)list.get(i));
            pstmt.setInt(++scc, info.getStatus());
            pstmt.setInt(++scc, info.getUserId());
            pstmt.setString(++scc, currDate1());
            pstmt.setString(++scc, currDate1());
            print(this,"createAssessmentdetail :: " + pstmt.toString());
            pstmt.executeUpdate();
            rs = pstmt.getGeneratedKeys();
            scc = 0;
            }           
        }
        catch (Exception exception)
        {
            print(this,"createAssessment :: " + exception.getMessage());
        }
        finally
        {
            close(conn, pstmt, rs);
        }
        return assessmentId;
    }
    
    public int updateAssessment(AssessmentInfo info)
    {
        int cc = 0;
        try
        {
            StringBuilder sb = new StringBuilder();
            sb.append("UPDATE t_assessment SET ");
            sb.append("s_name = ?, ");
            sb.append("s_parameterids = ?, ");
            sb.append("s_mode = ?, ");
            sb.append("i_status = ?, ");
            sb.append("i_userid = ?, ");
            sb.append("ts_moddate = ? ");
            sb.append("WHERE i_assessmentid = ? ");
            String query = (sb.toString()).intern();
            sb.setLength(0);

            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            int scc = 0;
            pstmt.setString(++scc, info.getName());
            pstmt.setString(++scc, info.getParameterids());
            pstmt.setString(++scc, info.getMode());
            pstmt.setInt(++scc, info.getStatus());
            pstmt.setInt(++scc, info.getUserId());
            pstmt.setString(++scc, currDate1());
            pstmt.setInt(++scc, info.getAssessmentId());
            print(this,"updateAssessment :: " + pstmt.toString());
            cc = pstmt.executeUpdate();
            pstmt.close();
            
            String deletequery = "DELETE FROM t_assessmentdetail WHERE i_assessmentid = ? ";
            pstmt = conn.prepareStatement(deletequery);
            pstmt.setInt(1,info.getAssessmentId());
            pstmt.executeUpdate();
            ArrayList list = info.getList();
            int list_size = list.size();
            
            for(int i = 0; i<= list_size; i++)
            {
                StringBuilder sb1 = new StringBuilder();
                sb1.append("INSERT INTO t_assessmentdetail ");
                sb1.append("(i_assessmentid, i_assessmentquestionid, i_status, i_userid, ts_regdate, ts_moddate) ");
                sb1.append("VALUES (?, ?, ?, ?, ?, ?)");
                String query1 = (sb1.toString()).intern();
                sb.setLength(0);

                pstmt = conn.prepareStatement(query1, Statement.RETURN_GENERATED_KEYS);
                 scc = 0;
                    int a = (int)list.get(i);

                pstmt.setInt(++scc,( info.getAssessmentId()));
                pstmt.setInt(++scc,(int)list.get(i));
                pstmt.setInt(++scc, info.getStatus());
                pstmt.setInt(++scc, info.getUserId());
                pstmt.setString(++scc, currDate1());
                pstmt.setString(++scc, currDate1());
                print(this,"createAssessmentdetail :: " + pstmt.toString());
                pstmt.executeUpdate();
                rs = pstmt.getGeneratedKeys();
                scc = 0;
            }
        }
        catch (Exception exception)
        {
            print(this,"updateAssessment :: " + exception.getMessage());
        }
        finally
        {
            close(conn, pstmt, rs);
        }
        return cc;
    }
    
    public int checkDuplicacy(int assessmentId, String name)
    {
        int ck = 0;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT i_assessmentid FROM t_assessment WHERE (s_name = ? ) AND i_status IN (1, 2)");
        if(assessmentId > 0)
            sb.append(" AND i_assessmentid != ? ");
        String query = (sb.toString()).intern();
        sb.setLength(0);
        try
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            int scc = 0;           
            pstmt.setString(++scc, name);
            if(assessmentId > 0)
                pstmt.setInt(++scc, assessmentId);
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
    
    public Collection getAssessments()
    {
        Collection coll = new LinkedList();
        String query = ("SELECT i_assessmentid, s_name FROM t_assessment WHERE i_status = 1 ORDER BY s_name").intern();
        coll.add(new AssessmentInfo(-1, "- Select -"));
        try
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            rs = pstmt.executeQuery();
            int refId;
            String refName;
            while(rs.next())
            {
                refId = rs.getInt(1);
                refName = rs.getString(2) != null ? rs.getString(2) : "";
                coll.add(new AssessmentInfo(refId, refName));
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            close(conn,pstmt,rs);
        }
        return coll;
    }
    
    public int deleteAssessment(int assessmentId, int userId, int status, String ipAddrStr, String iplocal)
    {
        int cc = 0;
        try
        {
            conn = getConnection();
            StringBuilder sb = new StringBuilder();
            sb.append("UPDATE t_assessment SET ");
            sb.append("i_status = ?, ");
            sb.append("i_userid = ?, ");
            sb.append("ts_moddate = ? ");
            sb.append("WHERE i_assessmentid = ? ");
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
            pstmt.setInt(++scc, assessmentId);
            
            cc = pstmt.executeUpdate();
        }
        catch (Exception exception)
        {
            print(this,"deleteAssessment :: " + exception.getMessage());
        }
        finally
        {
            close(conn, pstmt, rs);
        }
        createHistoryAccess(null, userId, ipAddrStr, iplocal, "Data Deleted", 13, assessmentId); 
        return cc;
    }
    
    public ArrayList getListForExcel(String search, int assessmentparameterIndex,int questiontypeIndex)
    {   
        ArrayList list = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT t2.assessmentid, t2.aname, t2.mode, t2.pnames, t2.status FROM  (SELECT t_assessment.i_assessmentid AS assessmentid, t_assessment.s_name AS aname, ");
        sb.append("t_assessment.s_mode AS mode, GROUP_CONCAT(DISTINCT t1.s_name ORDER BY t1.s_name SEPARATOR ', ') AS pnames, t_assessment.i_status AS status ");
        sb.append("FROM t_assessment  ");
        sb.append("LEFT JOIN t_assessmentparameter AS t1 ON find_in_set(t1.i_assessmentparameterid, t_assessment.s_parameterids) ");
        sb.append("LEFT JOIN t_assessmentdetail ON (t_assessment.i_assessmentid = t_assessmentdetail.i_assessmentid ) WHERE 0 = 0 ");
         if(assessmentparameterIndex > 0)
        {
            sb.append("AND FIND_IN_SET(?, s_parameterids) ");
        }
         if(questiontypeIndex > 0)
        {
            sb.append(" AND t_assessmentdetail.i_assessmentquestionid = ? ");
        }
        sb.append("GROUP BY t_assessment.i_assessmentid ) AS t2");
        sb.append( " WHERE 0 = 0 ");
        if(search != null && !search.equals(""))
             sb.append("AND t2.aname LIKE ? OR t2.assessmentid = ? OR t2.mode = ? OR t2.pnames LIKE ? ");

        sb.append(" ORDER BY t2.status, t2.aname ");
        
        String query = (sb.toString()).intern();
        sb.setLength(0);       
        try
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            int scc = 0;
             if (assessmentparameterIndex > 0)
                pstmt.setInt(++scc, assessmentparameterIndex);            
            if (questiontypeIndex > 0)
                pstmt.setInt(++scc, questiontypeIndex);            
            if(search != null && !search.equals(""))
            {
                pstmt.setString(++scc, "%"+(search)+"%");
                pstmt.setString(++scc, search);
                pstmt.setString(++scc, search);
                pstmt.setString(++scc, "%"+(search)+"%");                
            }                       
            logger.log(Level.INFO, "getListForExcel :: {0}", pstmt.toString());
            rs = pstmt.executeQuery();
            String name,  paraName,mode;
            int assessmentId, status;
            while (rs.next())
            {
                assessmentId = rs.getInt(1);
                name = rs.getString(2) != null ? rs.getString(2) : "";
                mode = rs.getString(3) != null ? rs.getString(3) : "";
                paraName = rs.getString(4) != null ? rs.getString(4) : "";
                status = rs.getInt(5);                
                list.add(new AssessmentInfo(assessmentId, name, paraName, status, 0,mode));
            }
            rs.close();
                       
            print(this,"getListForExcel :: " + pstmt.toString());            
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            close(conn, pstmt, rs);
        }
        return list;
    }

     
    public int getMaxId()
    {
        int id = 0;
        String query = "SELECT MAX(i_assessmentid) FROM t_assessment";
        try
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            rs = pstmt.executeQuery();
            while(rs.next())
            {
                id = rs.getInt(1);
            }
            id = id + 1;
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            close(conn, pstmt, rs);
        }
        return id;
    }     
     
      public int createMaster( String name, String description,int userId) 
      {
        int cc = 0;
        try 
        {
            String query = "";
                query = ("INSERT INTO t_assessmentparameter (s_name, s_description, i_status, i_userid, ts_regdate, ts_moddate) VALUES (?,?,?,?,?,?) ");

            conn = getConnection();
            String checkq = "";
                checkq = "SELECT i_assessmentparameterid FROM t_assessmentparameter WHERE i_status IN (1,2) AND s_name = ? ";

            pstmt = conn.prepareStatement(checkq);
            pstmt.setString(1, name);
            rs = pstmt.executeQuery();
            int check = 0;
            while (rs.next()) {
                check = rs.getInt(1);
            }
            rs.close();
            if (check == 0) 
            {
                pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                pstmt.setString(1, name);
                pstmt.setString(2, description);
                pstmt.setInt(3, 1);
                pstmt.setInt(4, userId);
                pstmt.setString(5, currDate1());
                pstmt.setString(6, currDate1());
                pstmt.executeUpdate();
                rs = pstmt.getGeneratedKeys();
                while (rs.next())
                {
                    cc = rs.getInt(1);
                }
                rs.close();         
                
                AssessmentParameter assessmentparameter = new AssessmentParameter();
                assessmentparameter.createjson(conn);              
            } else {
                cc = -2;
            }
        } catch (Exception exception) {
            print(this, "createMaster :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }        
        return cc;
    }
      
      public int createquestionMaster(String question, int answerId, int parameterId,String externallink,int userId) 
      {
        int cc = 0;
        try 
        {
            String query = "";
                query = ("INSERT INTO t_assessmentquestion (s_name, s_link,i_assessmentanswertypeid,i_assessmentparameterid, i_status, i_userid, ts_regdate, ts_moddate) VALUES (?,?,?,?,?,?,?,?) ");

            conn = getConnection();
            String checkq = "";
                checkq = "SELECT i_assessmentquestionid FROM t_assessmentquestion WHERE i_status IN (1,2) AND s_name = ? AND i_assessmentparameterid = ? ";

            pstmt = conn.prepareStatement(checkq);
            pstmt.setString(1, question);
            pstmt.setInt(2, parameterId);
            rs = pstmt.executeQuery();
            int check = 0;
            while (rs.next()) {
                check = rs.getInt(1);
            }
            rs.close();
            if (check == 0) 
            {
                pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                pstmt.setString(1, question);
                pstmt.setString(2, externallink);
                pstmt.setInt(3, answerId);
                pstmt.setInt(4, parameterId);
                pstmt.setInt(5, 1);
                pstmt.setInt(6, userId);
                pstmt.setString(7, currDate1());
                pstmt.setString(8, currDate1());
                pstmt.executeUpdate();
                rs = pstmt.getGeneratedKeys();
                while (rs.next()) {
                    cc = rs.getInt(1);
                }
                rs.close();
               
                    AssessmentQuestion assessmentquestion = new AssessmentQuestion();
                    assessmentquestion.createjson(conn);     
            } else {
                cc = -2;
            }
        } catch (Exception exception) {
            print(this, "createquestionMaster :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }        
        return cc;
    }
      
    public SortedSet<Integer> getAssessmentDetailId(int assessmentId)
    {
        SortedSet<Integer> ss = new TreeSet<Integer>(); 
        
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT i_assessmentquestionid FROM t_assessmentdetail WHERE i_assessmentid = ? ");
        String query = sb.toString().intern();
        sb.setLength(0);
        try
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, assessmentId);
            //print(this,"getAssessmentDetailId SortedSet :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            int id;
            while (rs.next())
            {
                id = rs.getInt(1);
                ss.add(id);
            }
        }
        catch (Exception exception)
        {
            print(this,"getAssessmentDetailId SortedSet :: " + exception.getMessage());
        }
        finally
        {
            close(conn, pstmt, rs);
        }
        return ss;
    }
      
      public Collection getAssessmentQuestionsparameterview(int assessmentparameterId,int assessmentId) 
      {
        Collection coll = new LinkedList();
        StringBuilder sb1 = new StringBuilder();
        sb1.append("SELECT t_assessmentdetail.i_assessmentquestionid, t_assessmentquestion.s_name ");
        sb1.append("FROM t_assessmentdetail ");
        sb1.append("LEFT JOIN t_assessmentquestion ON (t_assessmentquestion.i_assessmentquestionid = t_assessmentdetail.i_assessmentquestionid)  ");
        sb1.append("WHERE t_assessmentquestion.i_assessmentparameterid = ? ");
        sb1.append(" AND t_assessmentdetail.i_assessmentid = ? ");
        String query1 = (sb1.toString()).intern();
        sb1.setLength(0);
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query1);
            pstmt.setInt(1, assessmentparameterId);
            pstmt.setInt(2, assessmentId);
            rs = pstmt.executeQuery();
            int refId;
            String refName;
            while (rs.next()) {
                refId = rs.getInt(1);
                refName = rs.getString(2) != null ? rs.getString(2) : "";
                coll.add(new AssessmentQuestionInfo(refId, refName));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return coll;
    }
      
      public AssessmentQuestionInfo getAssessmentQuestionviewdetail(int assessmentquestionId)
      {
        AssessmentQuestionInfo info = null;
        StringBuilder sb1 = new StringBuilder();
        sb1.append("SELECT  t_assessmentquestion.s_name ,t_assessmentquestion.s_link ,t_assessmentanswertype.s_name,t_assessmentparameter.s_name  ");
        sb1.append("FROM t_assessmentquestion ");
        sb1.append("LEFT JOIN t_assessmentanswertype ON (t_assessmentquestion.i_assessmentanswertypeid = t_assessmentanswertype.i_assessmentanswertypeid)  ");
        sb1.append("LEFT JOIN t_assessmentparameter ON (t_assessmentquestion.i_assessmentparameterid = t_assessmentparameter.i_assessmentparameterid)  ");
        sb1.append("WHERE t_assessmentquestion.i_assessmentquestionid = ? ");
        String query1 = (sb1.toString()).intern();
        sb1.setLength(0);
        try
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query1);
            pstmt.setInt(1, assessmentquestionId);
            rs = pstmt.executeQuery();
            String questionName,questionlink,answerName,assessmentParametername;
            while (rs.next()) 
            {
                questionName = rs.getString(1) != null ? rs.getString(1) : "";
                questionlink = rs.getString(2) != null ? rs.getString(2) : "";
                answerName = rs.getString(3) != null ? rs.getString(3) : "";
                assessmentParametername = rs.getString(4) != null ? rs.getString(4) : "";
                info = (new AssessmentQuestionInfo(questionName, questionlink,answerName,assessmentParametername));                
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return info;
    }
      
    //Index Dropdown 
    public Collection getAssessmentQuestionsparameterindex(int assessmentparameterId) 
    {
        Collection coll = new LinkedList();
        coll.add(new AssessmentInfo(-1, "All"));
        String query = ("SELECT i_assessmentquestionid, s_name FROM t_assessmentquestion WHERE i_status = 1 AND i_assessmentparameterid = ? ORDER BY s_name").intern();
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, assessmentparameterId);
            rs = pstmt.executeQuery();
            int refId;
            String refName;
            while (rs.next()) 
            {
                refId = rs.getInt(1);
                refName = rs.getString(2) != null ? rs.getString(2) : "";
                coll.add(new AssessmentInfo(refId, refName));
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return coll;
    }
      
    public Collection getAssessmentQuestionsIndex() 
    {
        Collection coll = new LinkedList();
        String query = ("SELECT i_assessmentquestionid, s_name FROM t_assessmentquestion WHERE i_status = 1 ORDER BY s_name").intern();
        coll.add(new AssessmentInfo(-1, "All"));
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
                refName = rs.getString(2) != null ? rs.getString(2) : "";
                coll.add(new AssessmentInfo(refId, refName));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return coll;
    }
      
    public Collection getAssessmentParametersindex() 
    {
        Collection coll = new LinkedList();
        coll.add(new AssessmentInfo(-1, "All"));
        String xml_path = getMainPath("json_path");
        String str = readHTMLFile("assessmentparameter.json", xml_path);
        JSONArray arr = new JSONArray(str);
        if (arr != null) 
        {
            int len = arr.length();
            for (int i = 0; i < len; i++) 
            {
                JSONObject jobj = arr.optJSONObject(i);
                if (jobj != null) 
                {
                    coll.add(new AssessmentInfo(jobj.optInt("id"), jobj.optString("name")));
                }
            }
        }
        return coll;
    }         
}
