import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.TimeZone;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class SignOffReminder
{
    private static final String ALGORITHM = "DES";
    private static final String secretKey = "ocs@321_";
    private static final String HEX = "0123456789ABCDEF";
    Properties database;
    Properties globals;
    
    public SignOffReminder() {
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
        
        this.globals = null;
        this.globals = new Properties();
        InputStream in2 = null;
        try {
            in2 = this.getClass().getClassLoader().getResourceAsStream("globals.properties");
            this.globals.load(in2);
            in2.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            try 
            {
                if (in != null) {
                    in.close();
                }
                if (in2 != null) {
                    in2.close();
                }
            }
            catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
    
    public Connection getConnection() 
    {
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
    
    public static void close(final Connection conn, final PreparedStatement pstmt, final ResultSet rs) {
        try 
        {
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
            Transport.send((Message)mimemessage);
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
    
    public String getMessage(final String mainstr, final String mailbody) 
    {
        String s = mainstr.replaceAll("__MAILBODY__", mailbody);
        return s;
    }
    
    public String createFolder(final String path) {
        String foldername = "";
        try 
        {
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
    
    public static void main(final String[] args) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        SignOffReminder obj = new SignOffReminder();
        try 
        {
            String template_path = obj.globals.getProperty("template_path");
            final String mainstr = obj.readHTMLFile("notification_mail2.html", template_path);
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
                        
            final StringBuilder sb = new StringBuilder();
            sb.append("SELECT CONCAT_WS(' ', c.s_firstname, c.s_middlename, c.s_lastname), t4.emails, t_position.s_name, "); 
            sb.append("t_grade.s_name, DATE_FORMAT (cr.ts_expecteddate, '%Y-%m-%d'), DATE_FORMAT (cr.ts_signon, '%Y-%m-%d'), ");
            sb.append("c.i_clientid, c.i_clientassetid, c.i_candidateid ");
            sb.append("FROM t_crewrotation AS cr ");
            sb.append("LEFT JOIN t_candidate AS c ON (c.i_candidateid = cr.i_candidateid) ");
            sb.append("LEFT JOIN t_clientasset ON (t_clientasset.i_clientassetid = cr.i_clientassetid) ");
            sb.append("LEFT JOIN t_client ON(t_client.i_clientid = t_clientasset.i_clientid) ");
            sb.append("LEFT JOIN (SELECT t_client.i_clientid, GROUP_CONCAT(DISTINCT t3.s_email SEPARATOR ', ' ) AS emails FROM t_client ");
            sb.append("LEFT JOIN t_userlogin AS t3 ON FIND_IN_SET(t3.i_userid, t_client.s_ocsuserids) GROUP BY t_client.i_clientid) AS t4 ON (t_clientasset.i_clientid = t4.i_clientid) ");
            sb.append("LEFT JOIN t_cractivity crc ON crc.i_cractivityid  = (SELECT c2.i_cractivityid FROM t_cractivity c2 WHERE i_activityid = 6 AND (c2.i_crewrotationid = cr.i_crewrotationid) ORDER BY c2.ts_fromdate DESC LIMIT 1 ) ");
            sb.append("LEFT JOIN t_position ON (t_position.i_positionid = crc.i_positionid) ");
            sb.append("LEFT JOIN t_grade ON (t_grade.i_gradeid = t_position.i_gradeid) ");
            sb.append("WHERE cr.i_status2 > 0 AND cr.i_active = 1 AND t_clientasset.i_status = 1 AND t_client.i_status = 1 AND ");
            sb.append("DATE_FORMAT(DATE_ADD(cr.ts_expecteddate, INTERVAL -2 DAY), '%Y-%m-%d') = CURRENT_DATE() ");
            
            final String query = sb.toString();
            sb.setLength(0);
            System.out.println("get query :: " + query);
            pstmt = conn.prepareStatement(query);
            rs = pstmt.executeQuery();
            final String[] cc = new String[0];
            final String[] bcc = new String[0];
            final String add_path = obj.globals.getProperty("file_maillog");
            final String filePath = obj.createFolder(add_path);
            while (rs.next())
            {
                final String name = (rs.getString(1) != null) ? rs.getString(1) : "";                
                final String email = (rs.getString(2) != null) ? rs.getString(2) : "";
                final String position = (rs.getString(3) != null) ? rs.getString(3) : "";
                final String grade = (rs.getString(4) != null) ? rs.getString(4) : "";
                final String signoffDate = (rs.getString(5) != null) ? rs.getString(5) : "";
                final String signonDate = (rs.getString(6) != null) ? rs.getString(6) : "";
                final int clientId = rs.getInt(7);
                final int assetId = rs.getInt(8);
                final int candidateId = rs.getInt(9);
                final String[] to = parseCommaDelimString(email);
                if(!name.equals(""))
                {
                    String mailbody = "Dear Coordinator,\n\n"+
                    "This is to inform you that the crew member "+ name+" will sign off in next 2 days.\n\n"+

                    "Details:\n"+
                    "Crew Member: "+name+"\n"+
                    "Position: "+position+" - "+grade+"\n"+
                    "Sign-Off Date: "+signoffDate+"\n"+
                     
                    "Upcoming Sign On date: "+signonDate+"\n\n"+

                    "Please ensure all preparations are in place for the crew member's.";
                    
                    final String description = mailbody.replaceAll("\n", "<br/>");
                    final String message = obj.getMessage(mainstr, description);
                    final Date nowmail = new Date();
                    final String fn_mail = "Sign_Off-" + String.valueOf(nowmail.getTime()) + ".html";
                    final String file_maillog = obj.globals.getProperty("file_maillog");
                    final String fname = obj.writeHTMLFile(message, file_maillog + "/" + filePath, fn_mail);
                    final String sub = "Crew Sign-Off Notification – T-2 Days";
                    String attachmentpath = file_maillog + fname;
                    
                    final int sentmail = obj.postMailAttach(port, host, mail_server_username, mail_server_password, displayName, fromaddress, to, cc, bcc, message, sub);
                    if(sentmail > 0)
                    {
                        int notificationId = createNotification(candidateId, 5, 12, candidateId, clientId, assetId, "Crew Sign-Off Notification – T-2 Days", "COORDOINATOR");
                        if(notificationId > 0){
                            obj.createMailLog(conn, 40, name, email, "", "", fromaddress, sub, filePath+ "/" +fname, attachmentpath, 0, "COORDOINATOR" , 0, notificationId);
                        }
                    }
                }
            }
            rs.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            obj.close(conn, pstmt, rs);
        }
    }
    
    public int createMailLog(Connection conn, int type, String name, String to, String cc, String bcc, 
        String from, String subject, String filename, String attachmentpath, int shortlistId, 
        String username, int oflag, int notificationId)
    {
        int id = 0;
        SignOffReminder obj = new SignOffReminder();
        try 
        {
            StringBuilder sb = new StringBuilder();
            sb.append("INSERT INTO t_maillog ");
            sb.append("(i_type, s_name, s_to, s_cc, s_bcc, s_from, s_subject, s_filename, ts_regdate, ts_moddate, s_attachmentpath, i_shortlistid, s_sendby, i_oflag, i_notificationid) ");
            sb.append("VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            String query = sb.toString().intern();
            sb.setLength(0);
            if (conn == null) {
                conn = obj.getConnection();
            }
            PreparedStatement pstmt = null;
            ResultSet rs = null;
            pstmt = conn.prepareStatement(query, com.mysql.jdbc.Statement.RETURN_GENERATED_KEYS);
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
            pstmt.setString(11, attachmentpath);
            pstmt.setInt(12, shortlistId);
            pstmt.setString(13, username);
            pstmt.setInt(14, oflag);
            pstmt.setInt(15, notificationId);
            System.out.println("createMailLog :: "+ pstmt.toString());
            pstmt.executeUpdate();
            rs = pstmt.getGeneratedKeys();
            while (rs.next()) {
                id = rs.getInt(1);
            }
        } catch (Exception e)
        {
            e.printStackTrace();
            System.out.println("createMailLog :: "+ e.getMessage());
        } finally {
             close(null, null, null);
        }
        return id;
    }
    
    public static int createNotification(int userId, int moduleId, int type, int mainId, int clientId,
            int assetId, String remarks, String username)
    {
        int cc = 0;
        SignOffReminder obj = new SignOffReminder();
        try 
        {
            StringBuilder sb = new StringBuilder();
            sb.append("INSERT INTO t_notification  ");
            sb.append("(i_moduleid, i_type, s_remarks, s_updatedby, i_clientid, i_clientassetid, i_mainid, i_userid, ts_regdate, ts_moddate) ");
            sb.append("VALUES (?, ?, ?, ?, ?,  ?, ?, ?, ?, ?)");
            String query = (sb.toString()).intern();
            sb.setLength(0);
            Connection conn = null;
            conn = obj.getConnection();
            PreparedStatement pstmt = null;
            ResultSet rs = null;
            pstmt = conn.prepareStatement(query, com.mysql.jdbc.Statement.RETURN_GENERATED_KEYS);
            int scc = 0;
            pstmt.setInt(++scc, moduleId);
            pstmt.setInt(++scc, type);
            pstmt.setString(++scc, remarks);
            pstmt.setString(++scc, username);
            pstmt.setInt(++scc, clientId);
            pstmt.setInt(++scc, assetId);
            pstmt.setInt(++scc, mainId);
            pstmt.setInt(++scc, userId);
            pstmt.setString(++scc, currDate1());
            pstmt.setString(++scc, currDate1());
            pstmt.executeUpdate();
            rs = pstmt.getGeneratedKeys();
            while (rs.next()) {
                cc = rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("createNotification :: " + e.getMessage());
        } finally {
            close(null, null, null);
        }
        return cc;
    }
    
    public static String currDate1() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Asia/Calcutta"));
        java.util.Date dt = cal.getTime();
        return sdf.format(dt);
    }
    
    public static String[] parseCommaDelimString(String langString) {
        if (langString == null || langString.trim().length() == 0) {
            return new String[]{};
        }
        java.util.List l = new ArrayList();
        StringTokenizer st = new StringTokenizer(langString, ",");
        while (st.hasMoreTokens()) {
            l.add(st.nextToken());
        }
        return (String[]) l.toArray(new String[]{});
    }
}