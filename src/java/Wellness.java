import java.sql.Statement;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.BodyPart;
import javax.mail.Transport;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeBodyPart;
import javax.mail.Message;
import javax.mail.Address;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.Session;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;
import javax.crypto.SecretKey;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.StringTokenizer;
import java.util.Date;
import java.util.TimeZone;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.DriverManager;
import java.sql.Connection;
import java.io.InputStream;
import java.util.Properties;
import javax.mail.PasswordAuthentication;

public class Wellness
{
    private static final String ALGORITHM = "DES";
    private static final String secretKey = "ocs@321_";
    private static final String HEX = "0123456789ABCDEF";
    Properties database;
    
    public Wellness() {
        this.database = null;
        this.database = new Properties();
        InputStream in = null;
        try {
            in = this.getClass().getClassLoader().getResourceAsStream("database.properties");
            this.database.load(in);
            in.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            }
            catch (Exception ex) {}
        }
    }
    
    public Connection getConnection() {
        Connection conn1 = null;
        final String DriverClassName = this.database.getProperty("DriverClassName");
        final String dburl = this.database.getProperty("dburl");
        final String dbusername = this.database.getProperty("dbusername");
        final String dbpassword = this.database.getProperty("dbpassword");
        try {
            Class.forName(DriverClassName);
            conn1 = DriverManager.getConnection(dburl, dbusername, dbpassword);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return conn1;
    }
    
    public void close(final Connection conn, final PreparedStatement pstmt, final ResultSet rs) {
        try {
            if (conn != null) {
                conn.close();
            }
            if (pstmt != null) {
                pstmt.close();
            }
            if (rs != null) {
                rs.close();
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public static String getDateAfter(final String date, final int type, final int no, final String sformatv, final String endformatv) {
        String dt = "";
        try {
            final Calendar cal = Calendar.getInstance();
            final SimpleDateFormat sformat = new SimpleDateFormat(sformatv);
            final SimpleDateFormat endformat = new SimpleDateFormat(endformatv);
            final long l1 = sformat.parse(date).getTime();
            cal.setTimeInMillis(l1);
            switch (type) {
                case 1: {
                    cal.add(5, no);
                    break;
                }
                case 2: {
                    cal.add(2, no);
                    break;
                }
                case 3: {
                    cal.add(1, no);
                    break;
                }
                case 4: {
                    cal.add(10, no);
                    break;
                }
            }
            dt = endformat.format(cal.getTime());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return dt;
    }
    
    public String currDate1() {
        final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        final Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Asia/Calcutta"));
        final Date dt = cal.getTime();
        return sdf.format(dt);
    }
    
    public String currDate() {
        final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        final Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Asia/Calcutta"));
        final Date dt = cal.getTime();
        return sdf.format(dt);
    }
    
    public int getDiffTwoDateInt(final String sdate, final String edate, final String sformatv) {
        int diff = 0;
        try {
            if (sdate != null && !sdate.equals("") && !sdate.equals("00-00-0000") && edate != null && !edate.equals("") && !edate.equals("00-00-0000")) {
                final SimpleDateFormat sdf = new SimpleDateFormat(sformatv);
                final long l1 = sdf.parse(sdate).getTime();
                final long l2 = sdf.parse(edate).getTime();
                final long d = l2 - l1;
                final long div = 86400000L;
                final long f1 = d / div;
                diff = (int)f1;
                ++diff;
            }
        }
        catch (Exception e) {
            diff = 0;
        }
        return diff;
    }
    
    public boolean checkToStr(final String strList, final String str2) {
        boolean b = false;
        if (strList != null && !strList.equals("")) {
            final StringTokenizer st = new StringTokenizer(strList, ",");
            while (st.hasMoreTokens()) {
                final String s1 = st.nextToken(",");
                if (s1.equals(str2)) {
                    b = true;
                    break;
                }
            }
        }
        return b;
    }
    
    public boolean checkinschedule(final int type, final String scheduleval, final String checkval, final int lastday, final int currday) {
        boolean b = false;
        if (scheduleval != null && !scheduleval.equals("")) {
            if (type == 2) {
                if (scheduleval.contains(checkval)) {
                    b = true;
                }
            }
            else if (type == 3) {
                if (this.checkToStr(scheduleval, checkval)) {
                    b = true;
                }
                if (!b && scheduleval.contains("-2") && lastday == currday) {
                    b = true;
                }
            }
            else if (type == 4 && this.checkToStr(scheduleval, checkval)) {
                b = true;
            }
        }
        return b;
    }
    
    public String readHTMLFile(final String fileName, final String filePath) {
        String content = "";
        if (fileName != null && !fileName.equals("")) {
            final StringBuilder sb = new StringBuilder();
            final File file = new File(filePath + fileName);
            try {
                final BufferedReader br = new BufferedReader(new FileReader(file));
                String line;
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }
                br.close();
                content = sb.toString();
                sb.setLength(0);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        return content;
    }
    
    public String getMessage(final String mainstr, final String name, final String subcategoryname, final String link, final String expiry) {
        String s = mainstr.replaceAll("__NAME__", name);
        s = s.replaceAll("__SUBCATEGORY__", subcategoryname);
        s = s.replaceAll("__LINK__", link);
        s = s.replaceAll("__EXPIRY__", expiry);
        return s;
    }
    
    public String createFolder(final String path) {
        String foldername = "";
        try {
            final Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Asia/Calcutta"));
            final int month = cal.get(2) + 1;
            final int year = cal.get(1);
            final SimpleDateFormat sdf1 = new SimpleDateFormat("M");
            final SimpleDateFormat sdf2 = new SimpleDateFormat("MMM");
            final String monthName = sdf2.format(sdf1.parse(month + ""));
            foldername = (monthName + "-" + year).toLowerCase();
            final File dir = new File(path + foldername + "/");
            if (!dir.exists()) {
                dir.mkdir();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return foldername;
    }
    
    public String writeHTMLFile(final String content, final String filePath, final String fileName) {
        try {
            final File dir = new File(filePath);
            if (!dir.exists()) {
                dir.mkdir();
            }
            final File file = new File(filePath + "/" + fileName);
            final BufferedWriter bw = new BufferedWriter(new FileWriter(file));
            bw.write(content);
            bw.flush();
            bw.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return fileName;
    }
    
    public int postMailAttach(final String port, final String host, final String mail_server_username, final String mail_server_password, final String displayName, final String fromaddress, final String[] to, final String[] cc, final String[] bcc, final String message, final String sub) throws MessagingException {
        int ccval = 0;
        try 
        {
            Authenticator authenticator = new Authenticator(mail_server_username, mail_server_password);
            final Properties properties = new Properties();
            properties.put("mail.smtp.host", host);
            if (port != null && !port.equals("")) {
                properties.put("mail.smtp.port", port);
            }
            properties.put("mail.smtp.auth", "true");
            final Session session = Session.getInstance(properties, (Authenticator)authenticator);
            session.setDebug(false);
            final MimeMessage mimemessage = new MimeMessage(session);
            final InternetAddress from_address = new InternetAddress(fromaddress, displayName);
            mimemessage.setFrom((Address)from_address);
            mimemessage.setSubject(sub);
            mimemessage.setHeader("Content-Type", "text/plain; charset=UTF-8");
            if (to != null && to.length > 0) {
                final InternetAddress[] to_address = new InternetAddress[to.length];
                for (int i = 0; i < to.length; ++i) {
                    to_address[i] = new InternetAddress(to[i]);
                }
                mimemessage.setRecipients(Message.RecipientType.TO, (Address[])to_address);
            }
            if (cc != null && cc.length > 0) {
                final InternetAddress[] cc_address = new InternetAddress[cc.length];
                for (int i = 0; i < cc.length; ++i) {
                    cc_address[i] = new InternetAddress(cc[i]);
                }
                mimemessage.setRecipients(Message.RecipientType.CC, (Address[])cc_address);
            }
            if (bcc != null && bcc.length > 0) {
                final InternetAddress[] bcc_address = new InternetAddress[bcc.length];
                for (int i = 0; i < bcc.length; ++i) {
                    bcc_address[i] = new InternetAddress(bcc[i]);
                }
                mimemessage.setRecipients(Message.RecipientType.BCC, (Address[])bcc_address);
            }
            final BodyPart messageBodyPart = (BodyPart)new MimeBodyPart();
            messageBodyPart.setText(message);
            final Multipart multipart2 = (Multipart)new MimeMultipart();
            messageBodyPart.setContent((Object)message, "text/html");
            multipart2.addBodyPart(messageBodyPart);
            mimemessage.setContent(multipart2);
            mimemessage.setSentDate(new Date());
            //Transport.send((Message)mimemessage);
            ccval = 1;
        }
        catch (Exception e) {
            ccval = 0;
            e.printStackTrace();
        }
        return ccval;
    }
    
    class Authenticator extends javax.mail.Authenticator 
    {
        String username;
        String password;
        public Authenticator(String username, String password) {
            this.username = username;
            this.password = password;
        }
        @Override
        public PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(username, password);
        }
    }
    
    public static String cipher(String data) throws Exception {
        if (secretKey == null || secretKey.length() != 8) {
            throw new Exception("Invalid key length - 8 bytes key needed!");
        }
        SecretKey key = new SecretKeySpec(secretKey.getBytes(), ALGORITHM);
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, key);
        return toHex(cipher.doFinal(data.getBytes()));
    }
    
    public static String toHex(byte[] stringBytes) {
        StringBuilder result = new StringBuilder(2 * stringBytes.length);
        for (int i = 0; i < stringBytes.length; i++) {
            result.append(HEX.charAt((stringBytes[i] >> 4) & 0x0f)).append(HEX.charAt(stringBytes[i] & 0x0f));
        }
        return result.toString();
    }
    
    public String changeDate2(final String date) {
        String str = "";
        try {
            if (date != null && !date.equals("")) {
                final SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                final SimpleDateFormat sdf2 = new SimpleDateFormat("dd-MMM-yyyy");
                str = sdf2.format(sdf1.parse(date));
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }
    
    public static void main(final String[] args) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        final Wellness obj = new Wellness();
        PreparedStatement insert_pstmt = null;
        PreparedStatement pstmt_crew = null;
        PreparedStatement pstmt_sutvey_mail = null;
        PreparedStatement update_status = null;
        ResultSet rs_crew = null;
        try {
            final Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Asia/Calcutta"));
            final int curryear = cal.get(1);
            final int currmonth = cal.get(2) + 1;
            final int currday = cal.get(5);
            final int dayofweek = cal.get(7);
            cal.set(5, cal.getActualMaximum(5));
            final int lastday = cal.get(5);
            final String mainstr = obj.readHTMLFile("survey.html", "/opt/content/jxp_data/templates/");
            final String currdate = obj.currDate1();
            final String filePath = obj.createFolder("/opt/content/jxp_data/maillog/");
            final String[] cc = new String[0];
            final String[] bcc = new String[0];
            conn = obj.getConnection();
            String port = "";
            String host = "";
            String mail_server_username = "";
            String mail_server_password = "";
            String displayName = "";
            String fromaddress = "";
            final String query_smtp = "select s_host, s_port, s_serverusername, s_serverpassword, s_from, s_displayname from t_smtp order by ts_regdate desc limit 0, 1";
            final Statement stmt_smtp = conn.createStatement();
            final ResultSet rs2 = stmt_smtp.executeQuery(query_smtp);
            while (rs2.next()) {
                host = rs2.getString("s_host");
                port = rs2.getString("s_port");
                mail_server_username = rs2.getString("s_serverusername");
                mail_server_password = rs2.getString("s_serverpassword");
                fromaddress = rs2.getString("s_from");
                displayName = rs2.getString("s_displayname");
            }
            rs2.close();
            stmt_smtp.close();
            final String update_status_query = "UPDATE t_survey set s_filename = ? where i_surveyid = ?";
            update_status = conn.prepareStatement(update_status_query);
            final String insertq = "INSERT INTO t_survey (i_clientassetid, i_crewrotationid, i_subcategorywfid, i_positionid, i_status, ts_expirydate, ts_regdate, ts_moddate) values (?,?,?,?,?,?,?,?) ";
            insert_pstmt = conn.prepareStatement(insertq, 1);
            final String insert_survey_mail = "INSERT INTO t_surveymail (i_surveyid, d_date, i_status, i_type, ts_regdate) values (?,?,?,?,?) ";
            pstmt_sutvey_mail = conn.prepareStatement(insert_survey_mail);
            final StringBuilder sb = new StringBuilder();
            sb.append("SELECT t_schedule.i_clientassetid, t_schedule.i_subcategorywfid, swf.i_repeatdp, swf.s_schedulevalue, ");
            sb.append("swf.s_notificationdp, swf.s_name ");
            sb.append("FROM t_schedule LEFT JOIN t_subcategorywf as swf ON (t_schedule.i_subcategorywfid = swf.i_subcategorywfid) ");
            sb.append(" LEFT JOIN t_categorywf on (t_categorywf.i_categorywfid = swf.i_categorywfid) ");
            sb.append("where t_schedule.i_status = 1 AND swf.i_status = 1 AND t_categorywf.i_status = 1 ");
            sb.append(" AND ((i_schedulecb > 0 AND d_fromdate <= CURRENT_DATE() AND d_todate >= CURRENT_DATE()) OR ((i_schedulecb <= 0 AND d_fromdate = CURRENT_DATE())))  ");
            final String query = sb.toString();
            sb.setLength(0);
            System.out.println("schedule query :: " + query);
            pstmt = conn.prepareStatement(query);
            sb.append("select cr.i_crewrotationid, c.i_positionid, CONCAT_WS(' ', c.s_firstname, c.s_middlename, c.s_lastname), c.s_email ");
            sb.append("FROM t_crewrotation as cr LEFT JOIN t_candidate as c ON (c.i_candidateid = cr.i_candidateid) ");
            sb.append(" where cr.i_clientassetid = ? and cr.i_active = 1 ");
            sb.append(" and c.i_positionid IN (select DISTINCT(i_positionid) from t_wellnessmatrix where i_clientassetid = ? AND i_subcategorywfid = ?) ");
            final String crew_query = sb.toString();
            sb.setLength(0);
            pstmt_crew = conn.prepareStatement(crew_query);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                final int clientassetId = rs.getInt(1);
                final int subcategorywfId = rs.getInt(2);
                final int repeatdp = rs.getInt(3);
                final String scheduleval = (rs.getString(4) != null) ? rs.getString(4) : "";
                final String notificationdp = (rs.getString(5) != null) ? rs.getString(5) : "";
                final String subcategoryname = (rs.getString(6) != null) ? rs.getString(6) : "";
                String checkval = "";
                String expirydate = "";
                if (repeatdp == 0 || repeatdp == 1) {
                    expirydate = getDateAfter(currdate, 4, 24, "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm:ss");
                }
                else if (repeatdp == 2) {
                    checkval = "" + dayofweek;
                    expirydate = getDateAfter(currdate, 1, 7, "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd") + " 23:59:00";
                }
                else if (repeatdp == 3) {
                    checkval = "" + currday;
                    expirydate = getDateAfter(currdate, 2, 1, "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd") + " 23:59:00";
                }
                else if (repeatdp == 4) {
                    checkval = currday + "-" + currmonth;
                    expirydate = getDateAfter(currdate, 2, 1, "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd") + " 23:59:00";
                }
                final String expiry_mail = obj.changeDate2(expirydate);
                if (repeatdp == 1 || obj.checkinschedule(repeatdp, scheduleval, checkval, lastday, currday) || repeatdp == 0) {
                    pstmt_crew.setInt(1, clientassetId);
                    pstmt_crew.setInt(2, clientassetId);
                    pstmt_crew.setInt(3, subcategorywfId);
                    System.out.println("pstmt_crew :: " + pstmt_crew.toString());
                    rs_crew = pstmt_crew.executeQuery();
                    System.out.println("pstmt_crew :: " + pstmt_crew.toString());
                    while (rs_crew.next()) {
                        final int crewrotationId = rs_crew.getInt(1);
                        final int positionId = rs_crew.getInt(2);
                        final String candidatename = (rs_crew.getString(3) != null) ? rs_crew.getString(3) : "";
                        final String email = (rs_crew.getString(4) != null) ? rs_crew.getString(4) : "";
                        insert_pstmt.setInt(1, clientassetId);
                        insert_pstmt.setInt(2, crewrotationId);
                        insert_pstmt.setInt(3, subcategorywfId);
                        insert_pstmt.setInt(4, positionId);
                        insert_pstmt.setInt(5, 1);
                        insert_pstmt.setString(6, expirydate);
                        insert_pstmt.setString(7, currdate);
                        insert_pstmt.setString(8, currdate);
                        System.out.println("insert_survey :: " + insert_pstmt.toString());
                        insert_pstmt.executeUpdate();
                        final ResultSet rs_maxid = insert_pstmt.getGeneratedKeys();
                        int surveyId = 0;
                        while (rs_maxid.next()) {
                            surveyId = rs_maxid.getInt(1);
                        }
                        if (surveyId > 0) {
                            if ((repeatdp == 2 || repeatdp == 3 || repeatdp == 4) && !notificationdp.equals("")) {
                                final String[] notarr = notificationdp.split(",");
                                if (notarr != null && notarr.length > 0) {
                                    for (int i = 0; i < notarr.length; ++i) {
                                        if (notarr[i] != null && !notarr[i].equals("")) {
                                            final int notarr_val = Integer.parseInt(notarr[i]);
                                            if (notarr_val > 0) {
                                                pstmt_sutvey_mail.setInt(1, surveyId);
                                                pstmt_sutvey_mail.setString(2, getDateAfter(expirydate, 1, notarr_val * -1, "yyyy-MM-dd", "yyyy-MM-dd"));
                                                pstmt_sutvey_mail.setInt(3, 1);
                                                pstmt_sutvey_mail.setInt(4, repeatdp);
                                                pstmt_sutvey_mail.setString(5, currdate);
                                                pstmt_sutvey_mail.executeUpdate();
                                            }
                                        }
                                    }
                                }
                            }
                            final String link = "http://sandbox.journeyxpro.com/jxp/crewlogin/index.jsp?surveyId=" + cipher("" + surveyId) + "&crId=" + cipher("" + crewrotationId);
                            final String[] to = { email };
                            final String message = obj.getMessage(mainstr, candidatename, subcategoryname, link, expiry_mail);
                            final String file_maillog = "/opt/content/jxp_data/maillog/";
                            final Date nowmail = new Date();
                            final String fn_mail = "surremind-" + String.valueOf(nowmail.getTime()) + ".html";
                            final String fname = obj.writeHTMLFile(message, file_maillog + "/" + filePath, fn_mail);
                            final String sub = "Survey Expiring - " + subcategoryname;
                            final int sentmail = obj.postMailAttach(port, host, mail_server_username, mail_server_password, displayName, fromaddress, to, cc, bcc, message, sub);
                            if (sentmail <= 0) {
                                continue;
                            }
                            update_status.setString(1, "/" + filePath + "/" + fname);
                            update_status.setInt(2, surveyId);
                            update_status.executeUpdate();
                        }
                    }
                    rs_crew.close();
                }
            }
            rs.close();
        }
        catch (Exception e) {
            e.printStackTrace();
            obj.close(conn, pstmt, rs);
            try {
                if (insert_pstmt != null) {
                    insert_pstmt.close();
                }
                if (pstmt_crew != null) {
                    pstmt_crew.close();
                }
                if (pstmt_sutvey_mail != null) {
                    pstmt_sutvey_mail.close();
                }
                if (update_status != null) {
                    update_status.close();
                }
            }
            catch (SQLException se) {
                se.printStackTrace();
            }
        }
        finally {
            obj.close(conn, pstmt, rs);
            try {
                if (insert_pstmt != null) {
                    insert_pstmt.close();
                }
                if (pstmt_crew != null) {
                    pstmt_crew.close();
                }
                if (pstmt_sutvey_mail != null) {
                    pstmt_sutvey_mail.close();
                }
                if (update_status != null) {
                    update_status.close();
                }
            }
            catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }
}