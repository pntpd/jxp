package com.web.jxp.crewlogin;

import com.web.jxp.base.Base;
import com.web.jxp.base.Template;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import static com.web.jxp.common.Common.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.Logger;

public class Crewlogin extends Base {

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

    public int insertOTP(String emailId, String otp, String date) {
        int cc = 0;
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("insert into t_otp ");
            sb.append("(s_emailid, s_otpvalue, d_expirydate, ts_regdate) ");
            sb.append("values (?, ?, ?, ?)");
            String query = (sb.toString()).intern();
            sb.setLength(0);

            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            int scc = 0;
            pstmt.setString(++scc, (emailId));
            pstmt.setString(++scc, otp);
            pstmt.setString(++scc, date);
            pstmt.setString(++scc, currDate1());
            //print(this,"createCrewlogin :: " + pstmt.toString());
            cc = pstmt.executeUpdate();
        } catch (Exception exception) {
            print(this, "createCrewlogin :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return cc;
    }

    public String getOTPMessage(String otp, String htmlFile) {
        String adminpage = getMainPath("template_path") + htmlFile;
        HashMap hashmap = new HashMap();
        Template template = new Template(adminpage);
        hashmap.put("OTP", otp);
        return template.patch(hashmap);
    }

    public int checkOTP(String emailId, String otp) {
        int ck = 0;
        StringBuilder sb = new StringBuilder();
        sb.append("select i_otpid FROM t_otp where s_emailid = ? and s_otpvalue = ? and d_expirydate > now() ");

        String query = (sb.toString()).intern();
        sb.setLength(0);
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            int scc = 0;
            pstmt.setString(++scc, emailId);
            pstmt.setString(++scc, otp);
            print(this, "checkOTP :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ck = 1;
            }
        } catch (Exception exception) {
            print(this, "checkOTP :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return ck;
    }
    
   public CrewloginInfo getCandidateInfo(String emailId) 
   {
        CrewloginInfo info = null;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT cr.i_crewrotationid, c.i_candidateid, c.i_positionid, CONCAT_WS(' ', c.s_firstname, c.s_middlename, c.s_lastname), ");
        sb.append("cr.i_clientid, cr.i_clientassetid, t_client.s_name, t_clientasset.s_name, t_position.s_name, t_grade.s_name ");
        sb.append("FROM t_candidate AS c ");
        sb.append("LEFT JOIN t_client ON (t_client.i_clientid = c.i_clientid) ");
        sb.append("LEFT JOIN t_clientasset ON (t_clientasset.i_clientassetid = c.i_clientassetid) ");
        sb.append("LEFT JOIN t_crewrotation AS cr ON (c.i_candidateid = cr.i_candidateid AND cr.i_active =1)  ");
        sb.append("LEFT JOIN t_position ON (t_position.i_positionid = c.i_positionid)  ");
        sb.append("LEFT JOIN t_grade ON (t_grade.i_gradeid = t_position.i_gradeid)  ");
        sb.append("WHERE c.s_email = ? AND c.i_status =1 ");
        String query = (sb.toString()).intern();
        sb.setLength(0);
        try
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            int scc = 0;
            pstmt.setString(++scc, emailId);
            print(this, "getCandidateInfo :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            int crewrotationId = 0, candidateId = 0, positionId = 0, clientId = 0, clientassetId = 0;
            String name = "", clientname = "", assetname = "", position = "", grade = "";
            while (rs.next()) 
            {
                crewrotationId = rs.getInt(1);
                candidateId = rs.getInt(2);
                positionId = rs.getInt(3);
                name = rs.getString(4) != null ? rs.getString(4) : "";
                clientId = rs.getInt(5);
                clientassetId = rs.getInt(6);
                clientname = rs.getString(7) != null ? rs.getString(7) : "";
                assetname = rs.getString(8) != null ? rs.getString(8) : "";
                position = rs.getString(9) != null ? rs.getString(9) : "";
                grade = rs.getString(10) != null ? rs.getString(10) : "";
                if(!position.equals(""))
                {
                    if(!grade.equals(""))
                    {
                        position += " | " + grade;
                    }
                }
                info = new CrewloginInfo(crewrotationId, candidateId, positionId, name, emailId, clientId, clientassetId, clientname, assetname, position);
            }
        } catch (Exception exception) {
            print(this, "getCandidateId :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return info;
    }
}
