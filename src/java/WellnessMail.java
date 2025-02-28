import java.sql.Statement;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;
import javax.crypto.SecretKey;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.BodyPart;
import javax.mail.Transport;
import java.util.Date;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeBodyPart;
import javax.mail.Message;
import javax.mail.Address;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.Session;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.DriverManager;
import java.sql.Connection;
import java.io.InputStream;
import java.util.Properties;
import javax.mail.PasswordAuthentication;
public class WellnessMail
{
    private static final String ALGORITHM = "DES";
    private static final String secretKey = "ocs@321_";
    private static final String HEX = "0123456789ABCDEF";
    Properties database;
    
    public WellnessMail() {
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
        WellnessMail obj = new WellnessMail();
        PreparedStatement update_status = null;
        try {
            final String mainstr = obj.readHTMLFile("survey_reminder.html", "/opt/content/jxp_data/templates/");
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
            final String update_status_query = "UPDATE t_surveymail set i_status = 2, s_filename = ? where i_surveymailid = ?";
            update_status = conn.prepareStatement(update_status_query);
            final StringBuilder sb = new StringBuilder();
            sb.append("SELECT t_surveymail.i_surveymailid, t_surveymail.i_type, t_surveymail.i_surveyid, ");
            sb.append("swf.s_name, DATE_FORMAT(t_survey.ts_expirydate, '%d-%b-%Y'), ");
            sb.append("CONCAT_WS(' ', c.s_firstname, c.s_middlename, c.s_lastname), t_survey.i_crewrotationid, c.s_email ");
            sb.append("FROM t_surveymail LEFT JOIN t_survey ON (t_survey.i_surveyid = t_surveymail.i_surveyid) ");
            sb.append("left join t_crewrotation as cr ON (cr.i_crewrotationid = t_survey.i_crewrotationid) ");
            sb.append("left join t_candidate as c ON (c.i_candidateid = cr.i_candidateid) ");
            sb.append("left join t_subcategorywf as swf ON (swf.i_subcategorywfid = t_survey.i_subcategorywfid)  ");
            sb.append("where t_survey.i_status = 1 and t_surveymail.i_status = 1 AND CURRENT_DATE() = DATE_FORMAT(t_surveymail.d_date, '%Y-%m-%d') and t_surveymail.i_type IN (2,3,4) ");
            final String query = sb.toString();
            sb.setLength(0);
            System.out.println("get query :: " + query);
            pstmt = conn.prepareStatement(query);
            rs = pstmt.executeQuery();
            final String[] cc = new String[0];
            final String[] bcc = new String[0];
            final String filePath = obj.createFolder("/opt/content/jxp_data/maillog/");
            while (rs.next()) {
                final int surveymailId = rs.getInt(1);
                final int type = rs.getInt(2);
                final int surveyId = rs.getInt(3);
                final String subcategoryname = (rs.getString(4) != null) ? rs.getString(4) : "";
                final String expiry = (rs.getString(5) != null) ? rs.getString(5) : "";
                final String candidatename = (rs.getString(6) != null) ? rs.getString(6) : "";
                final int crewrotationId = rs.getInt(7);
                final String email = (rs.getString(8) != null) ? rs.getString(8) : "";
                final String link = "http://sandbox.journeyxpro.com/jxp/crewlogin/index.jsp?surveyId=" + obj.cipher("" + surveyId) + "&crId=" + obj.cipher("" + crewrotationId);
                final String[] to = { email };
                final String message = obj.getMessage(mainstr, candidatename, subcategoryname, link, expiry);
                final Date nowmail = new Date();
                final String fn_mail = "surremind-" + String.valueOf(nowmail.getTime()) + ".html";
                final String file_maillog = "/opt/content/jxp_data/maillog/";
                final String fname = obj.writeHTMLFile(message, file_maillog + "/" + filePath, fn_mail);
                final String sub = "Survey Expiring - " + subcategoryname;
                final int sentmail = obj.postMailAttach(port, host, mail_server_username, mail_server_password, displayName, fromaddress, to, cc, bcc, message, sub);
                if (sentmail > 0) {
                    update_status.setString(1, "/" + filePath + "/" + fname);
                    update_status.setInt(2, surveymailId);
                    update_status.executeUpdate();
                }
            }
            rs.close();
        }
        catch (Exception e) {
            e.printStackTrace();
            obj.close(conn, pstmt, rs);
            try {
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