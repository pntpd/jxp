package com.web.jxp.home;

import com.mysql.jdbc.Statement;
import com.web.jxp.base.Base;
import com.web.jxp.base.StatsInfo;
import com.web.jxp.base.Template;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import static com.web.jxp.common.Common.*;
import com.web.jxp.documentexpiry.Documentexpiry;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.Logger;

public class Home extends Base
{
    private static final Logger logger = Logger.getLogger(Class.class.getName());
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    
    public String generateotp() {
        String chars = "0123456789";
        int numberOfCodes = 0;
        String code = "";
        while (numberOfCodes < 6) {
            char c = chars.charAt((int) (Math.random() * chars.length()));
            code += " " + c;
            numberOfCodes++;
        }
        return code;
    }
    public static String getDateAfter(String date, int type, int no, String sformatv, String endformatv) {
        String dt = "";
        try {
            Calendar cal = Calendar.getInstance();
            SimpleDateFormat sformat = new SimpleDateFormat(sformatv);
            SimpleDateFormat endformat = new SimpleDateFormat(endformatv);
            long l1 = sformat.parse(date).getTime();
            cal.setTimeInMillis(l1);
            switch (type) {
                case 1:
                    cal.add(Calendar.DATE, no);
                    break;
                case 2:
                    cal.add(Calendar.MONTH, no);
                    break;
                case 3:
                    cal.add(Calendar.YEAR, no);
                    break;
                case 4:
                    cal.add(Calendar.HOUR, no);
                    break;
                case 5:
                    cal.add(Calendar.MINUTE, no);
                    break;
                default:
                    break;
            }
            dt = endformat.format(cal.getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dt;
    }
    public int insertOTP(String emailId,String otp,String date)
    {
        int homeId = 0;
        try
        {
            StringBuilder sb = new StringBuilder();
            sb.append("insert into t_otp ");
            sb.append("(s_emailid, s_otpvalue, d_expirydate, ts_regdate) ");
            sb.append("values (?, ?, ?, ?)");
            String query = (sb.toString()).intern();
            sb.setLength(0);

            conn = getConnection();
            pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            int scc = 0;
            pstmt.setString(++scc, (emailId));
            pstmt.setString(++scc, otp);
            pstmt.setString(++scc, date);
            pstmt.setString(++scc, currDate1());  
            //print(this,"createHome :: " + pstmt.toString());
            pstmt.executeUpdate();
            rs = pstmt.getGeneratedKeys();
            while (rs.next())
            {
                homeId = rs.getInt(1);
            }
        }
        catch (Exception exception)
        {
            print(this,"createHome :: " + exception.getMessage());
        }
        finally
        {
            close(conn, pstmt, rs);
        }
        return homeId;
    }
    
    public String getOTPMessage(String otp, String htmlFile) 
    {
        String adminpage = getMainPath("template_path") + htmlFile;
        HashMap hashmap = new HashMap();
        Template template = new Template(adminpage);
        hashmap.put("OTP", otp);
        return template.patch(hashmap);
    }
    
    public int checkOTP(String emailId, String otp)
    {
        int ck = 0;
        StringBuilder sb = new StringBuilder();
        sb.append("select i_otpid FROM t_otp where s_emailid = ? and s_otpvalue = ? and d_expirydate > now() ");
        
        String query = (sb.toString()).intern();
        sb.setLength(0);
        try
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            int scc = 0;           
            pstmt.setString(++scc, emailId);
            pstmt.setString(++scc, otp);
            print(this,"checkOTP :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            while (rs.next())
            {
                ck = 1;
            }
        }
        catch (Exception exception)
        {
            print(this,"checkOTP :: " + exception.getMessage());
        }
        finally
        {
            close(conn, pstmt, rs);
        }
        return ck;
    }
    
    public HomeInfo getCandidateId(String emailId)
    {
        HomeInfo info = null;
        StringBuilder sb = new StringBuilder();
        sb.append("select i_candidateid, i_vflag, i_pass FROM t_candidate where s_email = ? ");
        
        String query = (sb.toString()).intern();
        sb.setLength(0);
        try
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            int scc = 0;           
            pstmt.setString(++scc, emailId);
            print(this,"getCandidateId :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            int candidateId = 0, onlineflag = 0, vflag = 0, pass = 0;
            while (rs.next())
            {
                candidateId = rs.getInt(1);
                vflag = rs.getInt(2);
                pass = rs.getInt(3);
                if((vflag == 3 || vflag == 4) && pass == 2)
                    onlineflag = 1;
                else
                    onlineflag = 2;
                info = new HomeInfo(candidateId, onlineflag, emailId);
            }
        }
        catch (Exception exception)
        {
            print(this,"getCandidateId :: " + exception.getMessage());
        }
        finally
        {
            close(conn, pstmt, rs);
        }
        return info;
    }
    
    
    public int sendRegistrationEmail(String username, int userId, int type, String remarks, String emailid) 
    {
        int sent = 0;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT GROUP_CONCAT(DISTINCT i_userid ORDER BY s_email SEPARATOR ', '), DATE_FORMAT(CURRENT_DATE(), '%D-%b-%Y') FROM t_userlogin WHERE i_rflag = 1 ");
        String query = sb.toString();
        sb.setLength(0);
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            String email = "",  ccval = "", toval = "", currDate ="";
            print(this, "sendRegistrationEmail :: " + pstmt.toString());
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) 
            {
                email = rs.getString(1) != null ? rs.getString(1) : "";
                currDate = rs.getString(2) != null ? rs.getString(2) : "";
                if (!email.equals("")) 
                {
                    String query_cc = "SELECT GROUP_CONCAT(s_email) FROM t_userlogin WHERE i_userid IN (" + email + ")";
                    PreparedStatement pstmt_cc = conn.prepareStatement(query_cc);
                    ResultSet rs_cc = pstmt_cc.executeQuery();
                    while (rs_cc.next()) {
                        toval = rs_cc.getString(1) != null ? rs_cc.getString(1) : "";
                    }
                }
                if(!email.equals(""))
                {
                    String message = "Dear Recruiter, "+"\n\nNew candidate registration has been successfully completed.\n\n"+
                    "Details:\n" +
                    "Name: " +username+"\n"+
                    "Registration Email: "+emailid +"\n"+
                    "Date: "+currDate;
                    
                    String subject = "Candidate Registration Confirmation";
                    
                    String sdescription = message.replaceAll("\n", "<br/>");
                    String mailbody = getpatchbody(sdescription, "notification_crew.html");

                    String file_maillog = getMainPath("file_maillog");
                    java.util.Date nowmail = new java.util.Date();
                    String fn_mail = "new_registration-" + String.valueOf(nowmail.getTime());
                    String filePath_html = createFolder(file_maillog);
                    String fname = writeHTMLFile(mailbody, file_maillog + "/" + filePath_html, fn_mail + ".html");
                    String receipent[] = parseCommaDelimString(toval);
                    String from = "";
                    String cc[] = parseCommaDelimString(ccval);
                    String bcc[] = new String[0];
                    try
                    {
                        StatsInfo sinfo = postMailAttach(receipent, cc, bcc, mailbody, subject, "", "", -1);
                        int flag = 0;
                        if(sinfo != null)
                        {
                            flag = sinfo.getDdlValue();
                            from = sinfo.getDdlLabel();                            
                        }
                        if(flag > 0)
                        {
                            sent = 1;
                            Documentexpiry doc = new Documentexpiry();
                            int notificationId = doc.createNotification(userId, 1, type, userId, 0, 0, remarks, username);
                            if(notificationId > 0)
                               createMailLogForRegistration(conn, 22, username, toval, ccval, "", from, subject, userId, username, filePath_html+ "/" +fname, notificationId);
                        }
                    }
                    catch(Exception e)
                    {
                       print(this, "sendRegistrationEmail :: " + e.getMessage());
                    }
                }
            }
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return sent;
    }
    
    public int createMailLogForRegistration(Connection conn, int type, String name, String to, String cc, 
            String bcc, String from, String subject, int candidateId, String username, String filename, int notificationId) 
    {
        int count = 0;
        try 
        {
            StringBuilder sb = new StringBuilder();
            sb.append("INSERT INTO t_maillog ");
            sb.append("(i_type, s_name, s_to, s_cc, s_bcc, s_from, s_subject, s_filename, ts_regdate, ts_moddate, i_candidateid, s_sendby, i_notificationid) ");
            sb.append("VALUES (?, ?, ?, ?, ?,   ?, ?, ?, ?, ?,   ?, ?, ?)");
            String query = sb.toString().intern();
            sb.setLength(0);
            pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            pstmt.setInt(1, type);
            pstmt.setString(2, name);
            pstmt.setString(3, to);
            pstmt.setString(4, cc);
            pstmt.setString(5, bcc);
            pstmt.setString(6, from);
            pstmt.setString(7, subject);
            pstmt.setString(8, filename);
            pstmt.setString(9, currDate1());
            pstmt.setString(10, currDate1());
            pstmt.setInt(11, candidateId);
            pstmt.setString(12, username);
            pstmt.setInt(13, notificationId);
            print(this, "createMailLogForRegistration :: " + pstmt.toString());
            pstmt.executeUpdate();
            rs = pstmt.getGeneratedKeys();
            while (rs.next()) {
                count = rs.getInt(1);
            }
        } 
        catch (Exception exception) 
        {
            print(this, "createMailLogForRegistration :: " + exception.getMessage());
        } 
        finally 
        {
            close(null, pstmt, null);
        }
        return count;
    }
    
    public String getpatchbody(String description, String htmlFile) {
        String adminpage = getMainPath("template_path") + htmlFile;
        HashMap hashmap = new HashMap();
        Template template = new Template(adminpage);
        hashmap.put("MAILBODY", description);
        return template.patch(hashmap);
    }
}