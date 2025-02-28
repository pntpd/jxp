package com.web.jxp.maillog;
import com.web.jxp.base.Base;
import com.web.jxp.common.Common;
import static com.web.jxp.common.Common.*;
import java.sql.*;
import java.util.*;

public class Maillog extends Base
{    
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    public Maillog ()
    {
       
    }
   
    public ArrayList getMaillogListByName(String search, String fromDate, String toDate, int next, int count)
    {
        ArrayList list = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT i_maillogid, s_name, s_to, s_cc, s_subject, s_filename, ");
        sb.append("DATE_FORMAT(ts_regdate, '%d %b %Y %H:%i') as regdate ");
        sb.append("FROM t_maillog WHERE 0 = 0 ");
        if(search != null && !search.equals(""))
            sb.append(" AND (s_name LIKE ? OR s_to LIKE ? OR s_subject LIKE ? ) ");
        if(fromDate != null && !fromDate.equals("") && !fromDate.equals("DD-MMM-YYYY") && toDate != null && !toDate.equals("") && !toDate.equals("DD-MMM-YYYY"))
            sb.append(" AND (DATE_FORMAT(ts_regdate, '%Y-%m-%d') >= ? AND DATE_FORMAT(ts_regdate, '%Y-%m-%d') <= ?) ");
        sb.append(" ORDER BY ts_regdate DESC  ");
        if(count > 0)
            sb.append(" limit ").append(next * count).append(", ").append(count);
        String query = sb.toString().intern();
        sb.setLength(0);
        
        sb.append("SELECT COUNT(1) FROM t_maillog WHERE 0 = 0 ");
        if(search != null && !search.equals(""))
            sb.append(" AND (s_name LIKE ? OR s_to LIKE ? OR s_subject LIKE ? ) ");
        if(fromDate != null && !fromDate.equals("") && !fromDate.equals("DD-MMM-YYYY") && toDate != null && !toDate.equals("") && !toDate.equals("DD-MMM-YYYY"))
            sb.append(" AND (DATE_FORMAT(ts_regdate, '%Y-%m-%d') >= ? AND DATE_FORMAT(ts_regdate, '%Y-%m-%d') <= ?) ");
        String countquery = sb.toString().intern();
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
                pstmt.setString(++scc, "%"+(search)+"%");
            }
            if(fromDate != null && !fromDate.equals("") && !fromDate.equals("DD-MMM-YYYY"))
            {
                pstmt.setString(++scc, changeDate1(fromDate));
                pstmt.setString(++scc, changeDate1(toDate));
            }
            print(this,"getMaillogListByName :: " +pstmt.toString());
            rs = pstmt.executeQuery();
            int maillogId;
            String name, cc, regDate, email, subject, filename;
            while (rs.next())
            {
                maillogId = rs.getInt(1);
                name = rs.getString(2) != null ? rs.getString(2) : "";
                email = rs.getString(3) != null ? rs.getString(3) : "";
                cc = rs.getString(4) != null ? rs.getString(4) : "";
                subject = rs.getString(5) != null ? rs.getString(5) : "";
                filename = rs.getString(6) != null ? rs.getString(6) : "";
                regDate = rs.getString(7) != null ? rs.getString(7) : "";
                if(cc != null && !cc.equals(""))
                {
                    cc = cc.replaceAll(",", ", ");
                }
                if(email != null && !email.equals(""))
                {
                    email = email.replaceAll(",", ", ");
                }
                list.add(new MaillogInfo(maillogId, 0, name, email, cc, subject, filename, regDate));
            }
            rs.close();
            pstmt = conn.prepareStatement(countquery);
            scc = 0;
            if(search != null && !search.equals(""))
            {
                pstmt.setString(++scc, "%"+(search)+"%");
                pstmt.setString(++scc, "%"+(search)+"%");
                pstmt.setString(++scc, "%"+(search)+"%");
            }
            if(fromDate != null && !fromDate.equals("") && !fromDate.equals("DD-MMM-YYYY"))
            {
                pstmt.setString(++scc, changeDate1(fromDate));
                pstmt.setString(++scc, changeDate1(toDate));
            }
            print(this,"getMaillogListByName :: " +pstmt.toString());
            rs = pstmt.executeQuery();
            while (rs.next())
            {
                maillogId = rs.getInt(1);
                list.add(new MaillogInfo(maillogId, 0, "", "", "", "", "", ""));
            }
        }
        catch (Exception exception)
        {
            print(this,"getMaillogListByName :: " + exception.getMessage());
        }
        finally
        {
            close(conn, pstmt, rs);
        }
        return list;
    }
    
    public ArrayList getListForExcel(String search, String fromDate, String toDate) 
    {
        ArrayList list = new ArrayList();
        StringBuilder sb = new StringBuilder();
       sb.append("SELECT s_name, s_to, s_cc, s_subject, s_filename, ");
        sb.append("DATE_FORMAT(ts_regdate, '%d %b %Y %H:%i') as regdate ");
        sb.append("FROM t_maillog WHERE 0 = 0 ");
        if(search != null && !search.equals(""))
            sb.append(" AND (s_name LIKE ? OR s_to LIKE ? OR s_subject LIKE ? ) ");
        if(fromDate != null && !fromDate.equals("") && !fromDate.equals("DD-MMM-YYYY") && toDate != null && !toDate.equals("") && !toDate.equals("DD-MMM-YYYY"))
            sb.append(" AND (DATE_FORMAT(ts_regdate, '%Y-%m-%d') >= ? AND DATE_FORMAT(ts_regdate, '%Y-%m-%d') <= ?) ");
        sb.append(" ORDER BY ts_regdate DESC  ");
        String query = sb.toString().intern();
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
                pstmt.setString(++scc, "%"+(search)+"%");
            }
            if(fromDate != null && !fromDate.equals("") && !fromDate.equals("DD-MMM-YYYY"))
            {
                pstmt.setString(++scc, changeDate1(fromDate));
                pstmt.setString(++scc, changeDate1(toDate));
            }
            Common.print(this, "getListForExcel :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String name, cc, regDate, email, subject, filename;
            while (rs.next())
            {
                name = rs.getString(1) != null ? rs.getString(1) : "";
                email = rs.getString(2) != null ? rs.getString(2) : "";
                cc = rs.getString(3) != null ? rs.getString(3) : "";
                subject = rs.getString(4) != null ? rs.getString(4) : "";
                filename = rs.getString(5) != null ? rs.getString(5) : "";
                regDate = rs.getString(6) != null ? rs.getString(6) : "";
                
                list.add(new MaillogInfo(0, 0, name, email, cc, subject, filename, regDate));
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
    
    public MaillogInfo getMaillogDetailByIdforDetail(int maillogId)
    {
        MaillogInfo info = null;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT s_name, s_to, s_cc, s_subject, ts_regdate, s_filename,s_attachmentpath, ");
        sb.append("s_attachmentpath1, s_attachmentpath2, s_attachmentpath3 ");
        sb.append("FROM t_maillog where i_maillogid = ? ");
        String query = sb.toString().intern();
        sb.setLength(0);
        try
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, maillogId);
            print(this,"getMaillogDetailByIdforDetail :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String name, toaddress, ccaddress,subject,date, filename,attachment1,attachment2,attachment3, attachment4;
            while (rs.next())
            {
                name = rs.getString(1) != null ? rs.getString(1): "";
                toaddress = rs.getString(2) !=null ? rs.getString(2): "";
                ccaddress = rs.getString(3) !=null ? rs.getString(3): "";
                subject = rs.getString(4) !=null ? rs.getString(4): "";
                date = rs.getString(5) !=null ? rs.getString(5): "";
                filename = rs.getString(6) !=null ? rs.getString(6): "";
                attachment1 = rs.getString(7) !=null ? rs.getString(7): "";
                attachment2 = rs.getString(8) !=null ? rs.getString(8): "";
                attachment3 = rs.getString(9) !=null ? rs.getString(9): "";
                attachment4 = rs.getString(10) !=null ? rs.getString(10): "";
                info = new MaillogInfo( maillogId, name, toaddress, ccaddress, subject, date, filename, attachment1, attachment2, attachment3, attachment4);
            }
            rs.close();            
        }
        catch (Exception exception)
        {
            print(this,"getMaillogDetailByIdforDetail :: " + exception.getMessage());
        }
        finally
        {
            close(conn, pstmt, rs);
        }
        return info;
    }
}