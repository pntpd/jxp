package com.web.jxp.access;
import com.web.jxp.base.Base;
import java.util.*;
import java.sql.*;
import java.sql.PreparedStatement;
import static com.web.jxp.common.Common.*;



public class Access extends Base
{    
    Properties access_prop = null;
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    
    public ArrayList getAccessListByName(String search, int uId, String fromDate, String toDate, int next, int count,int moduleId)
    {
        ArrayList vec = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT t_transaction.i_transactionid, t_userlogin.s_name, ");
        sb.append("t_transaction.s_ipaddress, t_transaction.s_action, ");
        sb.append("DATE_FORMAT(t_transaction.ts_regdate, '%d %b %Y %H:%i:%s') as regdate, s_localip ");
        sb.append("FROM t_transaction left join t_userlogin on (t_transaction.i_userid = t_userlogin.i_userid) ");
        sb.append("where 0 = 0 and t_transaction.s_action like '%Log%' ");
        if(search != null && !search.equals(""))
            sb.append(" and (UPPER(t_userlogin.s_name) like UPPER(?)) ");
        if(uId > 0)
            sb.append(" and t_transaction.i_userid = ?");
        if(moduleId > 0)
            sb.append(" and t_transaction.i_moduleid = ?");
        if(fromDate != null && !fromDate.equals("") && !fromDate.equals("DD-MM-YYYY") && toDate != null && !toDate.equals("") && !toDate.equals("DD-MM-YYYY"))
        {
            sb.append(" and (DATE_FORMAT(t_transaction.ts_regdate, '%Y-%m-%d') >= ? and DATE_FORMAT(t_transaction.ts_regdate, '%Y-%m-%d') <= ?) ");
        }
        sb.append(" order by t_transaction.ts_regdate desc ");
        if(count > 0)
            sb.append(" limit " + next * count + ", " + count);
        String query = (sb.toString()).intern();
        sb.setLength(0);
        
        sb.append("SELECT count(1) FROM t_transaction left join t_userlogin on (t_transaction.i_userid = t_userlogin.i_userid) ");
        sb.append("where 0 = 0 and t_transaction.s_action like '%Log%' ");
        if(search != null && !search.equals(""))
            sb.append(" and (UPPER(t_userlogin.s_name) like UPPER(?)) ");
        if(uId > 0)
            sb.append(" and t_transaction.i_userid = ?");
        if(moduleId > 0)
            sb.append(" and t_transaction.i_moduleid = ?");
        if(fromDate != null && !fromDate.equals("") && !fromDate.equals("DD-MM-YYYY") && toDate != null && !toDate.equals("") && !toDate.equals("DD-MM-YYYY"))
        {
            sb.append(" and (DATE_FORMAT(t_transaction.ts_regdate, '%Y-%m-%d') >= ? and DATE_FORMAT(t_transaction.ts_regdate, '%Y-%m-%d') <= ?) ");
        }
        String countquery = (sb.toString()).intern();
        sb.setLength(0);
        try
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            int scc=0;
            if(search != null && !search.equals(""))
                pstmt.setString(++scc, "%"+(search)+"%");            
            if(uId > 0)
                pstmt.setInt(++scc, uId);
            if(moduleId > 0)
                pstmt.setInt(++scc, moduleId);
            if(fromDate != null && !fromDate.equals("") && !fromDate.equals("DD-MM-YYYY") && toDate != null && !toDate.equals("") && !toDate.equals("DD-MM-YYYY"))
            {
                pstmt.setString(++scc, changeDate1(fromDate));
                pstmt.setString(++scc, changeDate1(toDate));
            }
            print(this,pstmt.toString());
            rs = pstmt.executeQuery();
            int accessId;
            String name, ipaddress, description, regDate, localip;
            while (rs.next())
            {
                accessId = rs.getInt(1);
                name = rs.getString(2);
                ipaddress = rs.getString(3);
                description = rs.getString(4);
                regDate = rs.getString(5);
                localip = rs.getString(6);
                vec.add(new AccessInfo(accessId, name, ipaddress, description, regDate, localip));
            }
            rs.close();
            pstmt = conn.prepareStatement(countquery);
            scc=0;
            if(search != null && !search.equals("") && !search.equals("Search By User Name"))
                pstmt.setString(++scc, "%"+(search)+"%");            
            if(uId > 0)
                pstmt.setInt(++scc, uId);
            if(moduleId > 0)
                pstmt.setInt(++scc, moduleId);
            if(fromDate != null && !fromDate.equals("") && !fromDate.equals("DD-MM-YYYY") && toDate != null && !toDate.equals("") && !toDate.equals("DD-MM-YYYY"))
            {
                pstmt.setString(++scc, changeDate1(fromDate));
                pstmt.setString(++scc, changeDate1(toDate));
            }
            print(this,pstmt.toString());
            rs = pstmt.executeQuery();
            while (rs.next())
            {
                accessId = rs.getInt(1);
                vec.add(new AccessInfo(accessId, "", "", "", "", ""));
            }
        }
        catch (Exception exception)
        {
            print(this,"getAccessListByName :: " + exception.getMessage());
        }
        finally
        {
            close(conn,pstmt,rs);
        }
        return vec;
    }

    public Collection getModulesById()
    {
        Collection coll =  new LinkedList();
        String query = ("select i_moduleid,s_name FROM t_module where i_status = 1 order by s_name").intern();
        coll.add(new AccessInfo(-1, "-- Select Module --"));
        try
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            rs = pstmt.executeQuery();
            int moduleId;
            String moduleName;
            while(rs.next())
            {
                moduleId = rs.getInt("i_moduleid");
                moduleName = rs.getString("s_name");
                coll.add(new AccessInfo(moduleId, moduleName));
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
    
    public String getMessage(String prop)
    {
        String message = access_prop.getProperty(prop);
        if(message != null)
            return message;
        else
            return "";
    }
	
	public ArrayList getListForExcel(String search) 
    {
        ArrayList list = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT t_userlogin.s_name, ");
        sb.append("t_transaction.s_ipaddress, t_transaction.s_action, ");
        sb.append("DATE_FORMAT(t_transaction.ts_regdate, '%d %b %Y %H:%i:%s') as regdate ");
        sb.append("FROM t_transaction left join t_userlogin on (t_transaction.i_userid = t_userlogin.i_userid)");
        sb.append("where 0 = 0 and t_transaction.s_action like '%Log%' ");
        if(search != null && !search.equals(""))
            sb.append(" and (UPPER(t_userlogin.s_name) like UPPER(?)) ");         
        sb.append(" order by t_transaction.ts_regdate desc ");  
        String query = sb.toString().intern();
        sb.setLength(0);        
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            int scc = 0;
           if(search != null && !search.equals(""))
                pstmt.setString(++scc, "%"+(search)+"%");    
        //    Common.print(this, "getListForExcel :: " + pstmt.toString());
            rs = pstmt.executeQuery();
           String name, ipaddress, description, regDate;
            while (rs.next())
            {
                name = rs.getString(1);
                ipaddress = rs.getString(2);
                description = rs.getString(3);
                regDate = rs.getString(4);
                list.add(new AccessInfo(0, name, ipaddress, description, regDate, ""));
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
}