package com.web.jxp.base;
import java.util.*;
import java.io.*;
import java.sql.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;
import static com.web.jxp.common.Common.*;

public class Mail 
{
    Properties database = null;
    Properties globals = null;
    Connection conn = null;
    PreparedStatement pstmt = null;
    Statement stmt = null;
    ResultSet rs = null;
    public Mail() 
    {
        database = new Properties();
        globals = new Properties();
        InputStream in = null;
        try {
            in = getClass().getClassLoader().getResourceAsStream("database.properties");
            database.load(in);
            in.close();

            in = getClass().getClassLoader().getResourceAsStream("globals.properties");
            globals.load(in);
        } catch (Exception e) {
            print(this, "Base ::" + e.getMessage());
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e) {
            }
        }
    }

    public StatsInfo postMailAttach(String to[], String cc[], String bcc[], String message, String sub, String filePath,
            String localFileName, String filePath2, String localFileName2, int check) throws MessagingException 
    {
        int ccval = 0;
        String fromaddress = "";
        StatsInfo info;
        try
        {
            Base base = new Base();
            String port = "", host = "", mail_server_username = "", mail_server_password = "", displayName = "";
            Connection conn_mail = base.getConnection();
            String query_smtp = "select s_host, s_port, s_serverusername, s_serverpassword, s_from, s_displayname from t_smtp order by ts_regdate desc limit 0, 1";
            Statement stmt_smtp = conn_mail.createStatement();
            ResultSet rs1 = stmt_smtp.executeQuery(query_smtp);
            while (rs1.next()) {
                host = rs1.getString("s_host");
                port = rs1.getString("s_port");
                mail_server_username = rs1.getString("s_serverusername");
                mail_server_password = rs1.getString("s_serverpassword");
                fromaddress = rs1.getString("s_from");
                displayName = rs1.getString("s_displayname");
            }
            rs1.close();
            stmt_smtp.close();
            conn_mail.close();

            Authenticator authenticator = new Authenticator(mail_server_username, mail_server_password);
            Properties properties = new Properties();
            properties.put("mail.smtp.host", host);
            if (port != null && !port.equals("")) {
                properties.put("mail.smtp.port", port);
            }
            properties.put("mail.smtp.auth", "true");
            Session session = Session.getInstance(properties, authenticator);
            session.setDebug(false);
            MimeMessage mimemessage = new MimeMessage(session);
            InternetAddress from_address = new InternetAddress(fromaddress, displayName);
            mimemessage.setFrom(from_address);
            mimemessage.setSubject(sub);
            mimemessage.setHeader("Content-Type", "text/plain; charset=UTF-8");
            if (check > 0) {
                mimemessage.addHeader("Disposition-Notification-To", fromaddress);
            }
            if (to != null && to.length > 0) {
                InternetAddress to_address[] = new InternetAddress[to.length];
                for (int i = 0; i < to.length; i++) {
                    to_address[i] = new InternetAddress(to[i]);
                }
                mimemessage.setRecipients(javax.mail.Message.RecipientType.TO, to_address);
            }
            if (cc != null && cc.length > 0) {
                InternetAddress cc_address[] = new InternetAddress[cc.length];
                for (int i = 0; i < cc.length; i++) {
                    cc_address[i] = new InternetAddress(cc[i]);
                }
                mimemessage.setRecipients(javax.mail.Message.RecipientType.CC, cc_address);
            }
            if (bcc != null && bcc.length > 0) {
                InternetAddress bcc_address[] = new InternetAddress[bcc.length];
                for (int i = 0; i < bcc.length; i++) {
                    bcc_address[i] = new InternetAddress(bcc[i]);
                }
                mimemessage.setRecipients(javax.mail.Message.RecipientType.BCC, bcc_address);
            }

            BodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setText(message);

            Multipart multipart2 = new MimeMultipart();
            messageBodyPart.setContent(message, "text/html");
            multipart2.addBodyPart(messageBodyPart);
            if (localFileName != null && !localFileName.equals(""))
            {
                File ff = new File(filePath);
                if (ff.exists()) {
                    BodyPart messageBodyPart1 = new MimeBodyPart();
                    DataSource source = new FileDataSource(filePath);
                    messageBodyPart1.setDataHandler(new DataHandler(source));
                    messageBodyPart1.setFileName(localFileName);
                    multipart2.addBodyPart(messageBodyPart1);
                }
            }
            if (localFileName2 != null && !localFileName2.equals(""))
            {
                File ff = new File(filePath2);
                if (ff.exists()) 
                {
                    BodyPart messageBodyPart1 = new MimeBodyPart();
                    DataSource source = new FileDataSource(filePath2);
                    messageBodyPart1.setDataHandler(new DataHandler(source));
                    messageBodyPart1.setFileName(localFileName2);
                    multipart2.addBodyPart(messageBodyPart1);
                }
            }
            mimemessage.setContent(multipart2);
            mimemessage.setSentDate(new java.util.Date());
            Transport.send(mimemessage);
            ccval = 1;
        } catch (Exception e) {
            ccval = 0;
            print(this, "Error:: " + e.toString());
        }
        info = new StatsInfo(ccval, fromaddress);
        return info;
    }
    
    class Authenticator extends javax.mail.Authenticator {
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
}
