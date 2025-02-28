package com.web.jxp.benefitinformationtype;

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

public class Benefitinformationtype extends Base 
{
    private static final Logger logger = Logger.getLogger(Class.class.getName());
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    public ArrayList getBenefitinformationtypeByName(String search, int next, int count)
    {
        ArrayList benefitinformationtypes = new ArrayList();
        StringBuilder sb = new StringBuilder();
       sb.append("select t_benefitquestion.i_benefitquestionid,  t_benefittype.s_name , t_benefitquestion.i_status,  t_benefitquestion.s_name   ");
        sb.append(" from t_benefitquestion  left join t_benefittype on (t_benefitquestion.i_type = t_benefittype.i_benefittypeid) ");
        sb.append("where 0 = 0 ");
        if(search != null && !search.equals(""))
            sb.append("and (t_benefittype.s_name like ? OR t_benefitquestion.s_name like ?) ");
        
        sb.append(" order by t_benefitquestion.i_status, t_benefittype.s_name, t_benefitquestion.s_name ");
        if(count > 0)
            sb.append(" limit ").append(next*count).append(", ").append(count);
        String query = (sb.toString()).intern();
        sb.setLength(0);
        
        sb.append("select count(1) FROM t_benefitquestion  ");
        sb.append("left join t_benefittype on (t_benefitquestion.i_type = t_benefittype.i_benefittypeid) ");
        sb.append("where 0 = 0 ");
        if(search != null && !search.equals(""))
           sb.append("and (t_benefittype.s_name like ? OR t_benefitquestion.s_name like ?) ");
        
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
                pstmt.setString(++scc, "%"+(search)+"%");
            }
            logger.info("getBenefitinformationtypeByName :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String name, benefittypeName;
            int benefitinformationtypeId, status;
            while (rs.next())
            {
                benefitinformationtypeId = rs.getInt(1);
                name = rs.getString(2) != null ? rs.getString(2) : "";
                status = rs.getInt(3);
                benefittypeName = rs.getString(4) != null ? rs.getString(4) : "";
                benefitinformationtypes.add(new BenefitinformationtypeInfo(benefitinformationtypeId, name, status, benefittypeName,  0));
            }
            rs.close();
            
            pstmt = conn.prepareStatement(countquery);
            scc = 0;
            if(search != null && !search.equals(""))
            {
                pstmt.setString(++scc, "%"+(search)+"%");
                pstmt.setString(++scc, "%"+(search)+"%");
            }
            //print(this,"getBenefitinformationtypeByName :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            while (rs.next())
            {
                benefitinformationtypeId = rs.getInt(1);
                benefitinformationtypes.add(new BenefitinformationtypeInfo(benefitinformationtypeId, "", 0, 0));
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
        return benefitinformationtypes;
    }

    public ArrayList getFinalRecord(ArrayList l, String colId, int tp)
    {
        ArrayList list = null;
        HashMap record = null;
        BenefitinformationtypeInfo info;
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
                    info = (BenefitinformationtypeInfo) l.get (i);
                    record.put(getInfoValue(info, colId), info);
                }
            }
            Map map = sortByName(record, tp);
            Iterator it = map.keySet().iterator();
            list = new ArrayList();
            BenefitinformationtypeInfo rInfo;
            while (it.hasNext())
            {
                String key = (String) it.next();
                int i;
                for(i= 0; i < total; i++)
                {
                    rInfo = (BenefitinformationtypeInfo) l.get(i);
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
    public String getInfoValue(BenefitinformationtypeInfo info, String i)
    {
        String infoval = "";
        if(i != null && i.equals("1"))
            infoval = info.getBenefitinformationtypeName()!= null ? info.getBenefitinformationtypeName(): "";
        if(i !=null && i.equals("2"))
            infoval = info.getBenefittypeName()!= null ? info.getBenefittypeName() : "";        
        return infoval;
    }
    
    public BenefitinformationtypeInfo getBenefitinformationtypeDetailById(int benefitinformationtypeId)
    {
        BenefitinformationtypeInfo info = null;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT s_name, i_type, i_status FROM t_benefitquestion where i_benefitquestionid = ? ");
        String query = sb.toString().intern();
        sb.setLength(0);
        try
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, benefitinformationtypeId);
            print(this,"getBenefitinformationtypeDetailById :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String name;
            int status;
            while (rs.next())
            {
                name = rs.getString(1) != null ? rs.getString(1) : "";
                status = rs.getInt(2);
                int benefittypeId = rs.getInt(3);
                
                info = new BenefitinformationtypeInfo(benefitinformationtypeId, name, status, benefittypeId, 0);
            }
        }
        catch (Exception exception)
        {
            print(this,"getBenefitinformationtypeDetailById :: " + exception.getMessage());
        }
        finally
        {
            close(conn, pstmt, rs);
        }
        return info;
    }
    
    public BenefitinformationtypeInfo getBenefitinformationtypeDetailByIdforDetail(int benefitinformationtypeId)
    {
        BenefitinformationtypeInfo info = null;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT t_benefitquestion.s_name, t_benefitquestion.i_status, t_benefittype.s_name from t_benefitquestion ");
        sb.append("left join t_benefittype on (t_benefittype.i_benefittypeid = t_benefitquestion.i_type) where i_benefitquestionid = ?");
        String query = sb.toString().intern();
        sb.setLength(0);
        try
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, benefitinformationtypeId);
            print(this,"getBenefitinformationtypeDetailByIdforDetail :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String name;
            int status = 0;
            while (rs.next())
            {
                name = rs.getString(1) != null ? rs.getString(1) : "";
                status = rs.getInt(2); 
                String benefittypeName = rs.getString(3) != null ? rs.getString(3) : "";
                
                info = new BenefitinformationtypeInfo(benefitinformationtypeId, name, status, benefittypeName, 0);
            }
            rs.close();            
        }
        catch (Exception exception)
        {
            print(this,"getBenefitinformationtypeDetailByIdforDetail :: " + exception.getMessage());
        }
        finally
        {
            close(conn, pstmt, rs);
        }
        return info;
    }
    
    public int createBenefitinformationtype(BenefitinformationtypeInfo info)
    {
        int benefitinformationtypeId = 0;
        try
        {
            StringBuilder sb = new StringBuilder();
            sb.append("insert into t_benefitquestion  ");
            sb.append("(s_name, i_type, i_status,  i_userid, ts_regdate, ts_moddate) ");
            sb.append("values (?, ?, ?, ?, ?, ?)");
            String query = (sb.toString()).intern();
            sb.setLength(0);

            conn = getConnection();
            pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            int scc = 0;
            pstmt.setString(++scc, (info.getBenefitinformationtypeName()));
            pstmt.setInt(++scc, info.getBenefittypeId());
            pstmt.setInt(++scc, info.getStatus());
            pstmt.setInt(++scc, info.getUserId());
            pstmt.setString(++scc, currDate1());
            pstmt.setString(++scc, currDate1());  
            print(this,"createBenefitinformationtype :: " + pstmt.toString());
            pstmt.executeUpdate();
            rs = pstmt.getGeneratedKeys();
            while (rs.next())
            {
                benefitinformationtypeId = rs.getInt(1);
            }
            createjson(conn);
        }
        catch (Exception exception)
        {
            print(this,"createBenefitinformationtype :: " + exception.getMessage());
        }
        finally
        {
            close(conn, pstmt, rs);
        }
        return benefitinformationtypeId;
    }
    
    public int updateBenefitinformationtype(BenefitinformationtypeInfo info)
    {
        int cc = 0;
        try
        {
            StringBuilder sb = new StringBuilder();
            sb.append("update t_benefitquestion  set ");
            sb.append("s_name = ?, ");
            sb.append("i_status = ?, ");
            sb.append("i_userid = ?, ");
            sb.append("ts_moddate = ?, ");
            sb.append("i_type = ? ");
            sb.append("where i_benefitquestionid = ? ");
            String query = (sb.toString()).intern();
            sb.setLength(0);

            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            int scc = 0;
            pstmt.setString(++scc, info.getBenefitinformationtypeName());
            pstmt.setInt(++scc, info.getStatus());
            pstmt.setInt(++scc, info.getUserId());
            pstmt.setString(++scc, currDate1());
            pstmt.setInt(++scc, info.getBenefittypeId());
            pstmt.setInt(++scc, info.getBenefitinformationtypeId());
            print(this,"updateBenefitinformationtype :: " + pstmt.toString());
            cc = pstmt.executeUpdate();
            createjson(conn);
        }
        catch (Exception exception)
        {
            print(this,"updateBenefitinformationtype :: " + exception.getMessage());
        }
        finally
        {
            close(conn, pstmt, rs);
        }
        return cc;
    }
    
    public int checkDuplicacy(int benefitinformationtypeId, String name, int benefittypeId)
    {
        int ck = 0;
        StringBuilder sb = new StringBuilder();
        sb.append("select i_benefitquestionid FROM t_benefitquestion  where i_type = ? and s_name = ? and i_status in (1, 2)");
        if(benefitinformationtypeId > 0)
            sb.append(" and i_benefitquestionid != ? ");
        String query = (sb.toString()).intern();
        sb.setLength(0);
        try
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            int scc = 0;        
            pstmt.setInt(++scc, benefittypeId);
            pstmt.setString(++scc, name);           
            if(benefitinformationtypeId > 0){
                pstmt.setInt(++scc, benefitinformationtypeId);
            }
            print(this,"checkDuplicacy :: " + pstmt.toString());
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
    
    public Collection getBenefittype()
    {
        Collection coll = new LinkedList();
        String query = ("SELECT i_benefittypeid, s_name FROM t_benefittype where  i_benefittypeid != 3 order by i_benefittypeid").intern();
        coll.add(new BenefitinformationtypeInfo(-1, "- Select -"));
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
                coll.add(new BenefitinformationtypeInfo(refId, refName));
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
    
    public int deleteBenefitinformationtype(int benefitinformationtypeId, int userId, int status, String ipAddrStr, String iplocal)
    {
        int cc = 0;
        try
        {
            conn = getConnection();
            StringBuilder sb = new StringBuilder();
            sb.append("update t_benefitquestion  set ");
            sb.append("i_status = ?, ");
            sb.append("i_userid = ?, ");
            sb.append("ts_moddate = ? ");
            sb.append("where i_benefitquestionid = ? ");
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
            pstmt.setInt(++scc, benefitinformationtypeId);
            
            cc = pstmt.executeUpdate();
            createjson(conn);
        }
        catch (Exception exception)
        {
            print(this,"deleteBenefitinformationtype :: " + exception.getMessage());
        }
        finally
        {
            close(conn, pstmt, rs);
        }
        createHistoryAccess(null, userId, ipAddrStr, iplocal, "Data Updated", 33, benefitinformationtypeId); 
        return cc;
    } 
    
    public ArrayList getExcel(String search )
    {
        ArrayList list = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("select t_benefitquestion.i_benefitquestionid,  t_benefittype.s_name , t_benefitquestion.i_status,  t_benefitquestion.s_name   ");
        sb.append(" from t_benefitquestion  left join t_benefittype on (t_benefitquestion.i_type = t_benefittype.i_benefittypeid) ");
        sb.append("where 0 = 0 ");
        if(search != null && !search.equals(""))
            sb.append("and (t_benefittype.s_name like ? OR t_benefitquestion.s_name like ?) ");
       
        sb.append(" order by t_benefitquestion.i_status, t_benefittype.s_name, t_benefitquestion.s_name ");
       
        String query = sb.toString().intern();
        sb.setLength(0);        
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            Common.print(this, "getListForExcel :: " + pstmt.toString());
           
            int scc = 0;
            if(search != null && !search.equals(""))
            {
                pstmt.setString(++scc, "%"+(search)+"%");
                 pstmt.setString(++scc, "%"+(search)+"%");
            }
            rs = pstmt.executeQuery();
            while (rs.next())
            {
                 int benefitinformationtypeId = rs.getInt(1);
                 String benefitinformationtypename = rs.getString(2) != null ? rs.getString(2) : "";
                 int Status = rs.getInt(3);
                 String benefittypename = rs.getString(4) != null ? rs.getString(4) : "";
                 
                list.add(new BenefitinformationtypeInfo(benefitinformationtypeId, benefitinformationtypename, Status, benefittypename));
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
        String query = ("Select i_type,i_benefitquestionid, s_name FROM t_benefitquestion where i_status = 1 order by  s_name ");      
        try
        {
            pstmt = conn.prepareStatement(query);
            rs = pstmt.executeQuery();
            int benefittypeid;
            int benefitinformationtypeid;
            String name;
            JSONArray jarr = new JSONArray();
            while (rs.next())
            {
                benefittypeid = rs.getInt(1);
                benefitinformationtypeid = rs.getInt(2);
                name = rs.getString(3) != null ? rs.getString(3) : "";
                JSONObject jobj = new JSONObject();
                jobj.put("benefittypeid", benefittypeid);
                jobj.put("benefitinformationtypeid", benefitinformationtypeid);
                jobj.put("name", name);
                jarr.put(jobj);
            }
            rs.close();
            String str = jarr.toString();
            String filePath = getMainPath("json_path");
            writeHTMLFile(str, filePath, "benefitinformationtype.json");
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

    public Collection getbenefittype()
    {
        Collection coll = new LinkedList();
        coll.add(new BenefitinformationtypeInfo(-1, "Select Benefitinformationtype"));
        String xml_path = getMainPath("json_path");
        String str = readHTMLFile("benefitinformationtype.json", xml_path);
        JSONArray arr = new JSONArray(str);
        if(arr != null)
        {
            int len = arr.length();
            for(int i = 0; i < len; i++)
            {
                JSONObject jobj = arr.optJSONObject(i);
                if(jobj != null)
                {
                    coll.add(new BenefitinformationtypeInfo(jobj.optInt("benefitinformationtypeId"), jobj.optString("name")));
                }
            }
        }
        return coll;
    }

    public Collection getCities()
    {
        Collection coll = new LinkedList();
        String query = ("SELECT i_benefittypeid, s_name FROM t_benefittype  where i_status = 1 order by s_name").intern();
        coll.add(new BenefitinformationtypeInfo(-1, "--Select Benefitinformationtype --"));
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
                coll.add(new BenefitinformationtypeInfo(refId, refName));
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
    
    public Collection getBenefitinformationtypebybenefittypeId(int benefittypeId)
    {
         Collection coll = new LinkedList();
        String query = ("SELECT i_benefittypeid, s_name FROM t_benefittype  where i_status = 1 and i_benefittypeid = ? order by s_name").intern();
        coll.add(new BenefitinformationtypeInfo(-1, "- Select -"));
        try
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1,benefittypeId);
            rs = pstmt.executeQuery();
            int refId;
            String refName;
            while(rs.next())
            {
                refId = rs.getInt(1);
                refName = rs.getString(2) != null ? rs.getString(2) : "";
                coll.add(new BenefitinformationtypeInfo(refId, refName));
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
}



