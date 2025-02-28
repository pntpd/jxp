package com.web.jxp.base;

import java.text.*;
import java.util.*;
import java.io.*;
import java.sql.*;
import java.net.InetAddress;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;
import javax.servlet.http.HttpServletRequest;
import com.web.jxp.user.UserInfo;
import java.util.ArrayList;
import org.apache.struts.upload.FormFile;
import static com.web.jxp.common.Common.*;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.Rectangle;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.Cookie;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.zefer.pd4ml.PD4ML;
import org.zefer.pd4ml.PD4PageMark;

public class Base {

    private static final int EARTH_RADIUS = 6371;
    Properties database = null;
    Properties globals = null;
    Connection conn = null;
    PreparedStatement pstmt = null;
    Statement stmt = null;
    ResultSet rs = null;
    Validate vobj = new Validate();

    // for the password encryption and decryption
    private final static String ALGORITHM = "DES";
    private final static String HEX = "0123456789ABCDEF";
    private final static String secretKey = "ocs@321_";

    public Base() {
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

    public Connection getConnection() {
        Connection conn1 = null;
        String DriverClassName = database.getProperty("DriverClassName");
        String dburl = database.getProperty("dburl");
        String dbusername = database.getProperty("dbusername");
        String dbpassword = database.getProperty("dbpassword");
        try {
            Class.forName(DriverClassName);
            conn1 = DriverManager.getConnection(dburl, dbusername, dbpassword);
        } catch (Exception e) {
            print(this, "getConnection ::" + e.getMessage());
            e.printStackTrace();
            return null;
        }
        return conn1;
    }

    public void close(Connection conn, PreparedStatement pstmt, ResultSet rs) {
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String replaceWith(String oldString, String pattern, String newPattern) {
        String modString = new String();
        int start = 0;
        while (true) {
            int locindex = oldString.indexOf(pattern, start);
            if (locindex < 0) {
                break;
            }
            String prestring = oldString.substring(start, locindex);
            modString += prestring.concat(newPattern);
            start = locindex + pattern.length();
        }
        modString += oldString.substring(start);
        return modString;
    }

    public String escapeApostrophes(String oldStr) {
        String ss = "";
        if (oldStr == null) {
            return ss;
        }
        String aposStr = "'";
        String doubleAposStr = "''";
        return replaceWith(oldStr, aposStr, doubleAposStr);
    }

    public Collection getStatuses() {
        Collection status = new LinkedList();
        status.add(new StatsInfo("-1", "-- Status --"));
        status.add(new StatsInfo("1", "Active"));
        status.add(new StatsInfo("2", "Inactive"));
        status.add(new StatsInfo("3", "Deleted"));
        return status;
    }

    public Collection getStatusNew() {
        Collection status = new LinkedList();
        status.add(new StatsInfo("-1", "- Status -"));
        status.add(new StatsInfo("1", "Active"));
        status.add(new StatsInfo("2", "Inactive"));
        return status;
    }

    public String getStatusById(int key) {
        String s = "";
        switch (key) {
            case 1:
                s = "Active";
                break;
            case 2:
                s = "Inactive";
                break;
            case 3:
                s = "Deleted";
                break;
            case 4:
                s = "Deceased";
                break;
            default:
                break;
        }
        return s;
    }

    public String getStatusClass(int key) {
        String s = "";
        switch (key) {
            case 1:
                s = "label label-sm label-success";
                break;
            case 2:
                s = "label label-sm label-danger";
                break;
            case 3:
                s = "label label-sm label-danger";
                break;
            default:
                break;
        }
        return s;
    }

    public String getDesicionById(int key) {
        String s = "";
        switch (key) {
            case 1:
                s = "Yes";
                break;
            default:
                s = "No";
                break;
        }
        return s;
    }

    public static String cipher(String data) throws Exception {
        if (secretKey == null || secretKey.length() != 8) {
            throw new Exception("Invalid key length - 8 bytes key needed!");
        }
        if (data != null && !data.equals("")) {
            SecretKey key = new SecretKeySpec(secretKey.getBytes(), ALGORITHM);
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            return toHex(cipher.doFinal(data.getBytes()));
        } else {
            return "";
        }
    }

    public static String decipher(String data) throws Exception {
        if (secretKey == null || secretKey.length() != 8) {
            throw new Exception("Invalid key length - 8 bytes key needed!");
        }
        if (data != null && !data.equals("")) {
            SecretKey key = new SecretKeySpec(secretKey.getBytes(), ALGORITHM);
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, key);
            return new String(cipher.doFinal(toByte(data)));
        } else {
            return "";
        }
    }

    private static byte[] toByte(String hexString) {
        int len = hexString.length() / 2;
        byte[] result = new byte[len];
        for (int i = 0; i < len; i++) {
            result[i] = Integer.valueOf(hexString.substring(2 * i, 2 * i + 2), 16).byteValue();
        }
        return result;
    }

    public static String toHex(byte[] stringBytes) {
        StringBuilder result = new StringBuilder(2 * stringBytes.length);
        for (int i = 0; i < stringBytes.length; i++) {
            result.append(HEX.charAt((stringBytes[i] >> 4) & 0x0f)).append(HEX.charAt(stringBytes[i] & 0x0f));
        }
        return result.toString();
    }

    public static String currDate0() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Asia/Calcutta"));
        java.util.Date dt = cal.getTime();
        return sdf.format(dt);
    }

    public static String currDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Asia/Calcutta"));
        java.util.Date dt = cal.getTime();
        return sdf.format(dt);
    }

    public String currDate1() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Asia/Calcutta"));
        java.util.Date dt = cal.getTime();
        return sdf.format(dt);
    }

    public int getCount() {
        int count = 0;
        String toCount = database.getProperty("recordsperpage");
        if (toCount == null || toCount.equals("") == true) {
            toCount = "10";
        }
        try {
            count = Integer.parseInt(toCount);
        } catch (NumberFormatException nfe) {
            print(this, "Please provide only numbers in database property file :: " + nfe.getMessage());
        }
        return count;
    }

    public static String makeCommaDelimString(String[] langs) {
        if (langs == null || langs.length == 0) {
            return "";
        }
        StringBuilder rv = new StringBuilder(langs[0]);
        for (int i = 1; i < langs.length; i++) {
            rv.append(",").append(langs[i]);
        }
        String s = rv.toString();
        rv.setLength(0);
        return s;
    }
    
    public static String makeCommaDelimInt(int[] langs) {
        if (langs == null || langs.length == 0) {
            return "";
        }
        StringBuilder rv = new StringBuilder();
        rv.append(langs[0]);
        for (int i = 1; i < langs.length; i++) {
            rv.append(",").append(langs[i]);
        }
        String s = rv.toString();
        rv.setLength(0);
        return s;
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

    public int[] parseCommaDelimInt(String str) {
        if (str == null || str.isEmpty()) {
            return new int[0];
        }
        String[] parts = str.split(",");
        int[] result = Arrays.stream(parts)
                .mapToInt(Integer::parseInt)
                .toArray();
        return result;
    }

    public int getCountList(String str) {
        int count = 0;
        String toCount = globals.getProperty(str);
        if (toCount == null || toCount.equals("") == true) {
            toCount = "10";
        }
        try {
            count = Integer.parseInt(toCount);
        } catch (NumberFormatException nfe) {
            print(this, "Error:: " + nfe.toString());
        }
        return count;
    }

    public String getMainPath(String prop) {
        String mainPath = globals.getProperty(prop);
        if (mainPath != null) {
            return mainPath;
        } else {
            return "";
        }
    }

    public String writeHTMLFile(String content, String filePath, String fileName) {
        try {
            File dir = new File(filePath);
            if (!dir.exists()) {
                dir.mkdir();
            }
            File file = new File(filePath + "/" + fileName);

            BufferedWriter bw = new BufferedWriter(new FileWriter(file));
            bw.write(content);
            bw.flush();
            bw.close();
        } catch (Exception e) {
            print(this, "Error:: " + e.toString());
        }
        return fileName;
    }

    public String readHTMLFile(String fileName, String filePath) {
        String content = "";
        if (fileName != null && !fileName.equals("")) {
            StringBuilder sb = new StringBuilder();
            File file = new File(filePath + fileName);
            String line;
            try {
                BufferedReader br = new BufferedReader(new FileReader(file));
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }
                br.close();
                content = sb.toString();
                sb.setLength(0);
            } catch (Exception e) {
                print(this, "Error:: " + e.toString());
            }
        }
        return content;
    }

    public boolean checkToStr(String strList, String str2) {
        boolean b = false;
        if (strList != null && !strList.equals("")) {
            StringTokenizer st = new StringTokenizer(strList, ",");
            while (st.hasMoreTokens()) {
                String s1 = st.nextToken(",");
                if (s1.equals(str2)) {
                    b = true;
                    break;
                }
            }
        }
        return b;
    }

    public StatsInfo postMailAttach(String to[], String cc[], String bcc[], String message, String sub, String filePath,
            String localFileName, int check) throws MessagingException {
        int ccval = 0;
        String fromaddress = "";
        StatsInfo info;
        try {
            String port = "", host = "", mail_server_username = "", mail_server_password = "", displayName = "";
            Connection conn_mail = getConnection();
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

//            properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
//            properties.put("mail.smtp.socketFactory.fallback", "false");
//            properties.put("mail.smtp.starttls.enable", "true");
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
            if (localFileName != null && !localFileName.equals("")) {
                File ff = new File(filePath);
                if (ff.exists()) {
                    BodyPart messageBodyPart1 = new MimeBodyPart();
                    DataSource source = new FileDataSource(filePath);
                    messageBodyPart1.setDataHandler(new DataHandler(source));
                    messageBodyPart1.setFileName(localFileName);
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

    public String changeDate1(String date) {
        String str = "0000-00-00";
        try {
            if (date != null && !date.equals("")) {
                SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MMM-yyyy");
                SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
                str = sdf2.format(sdf1.parse(date));
            }
        } catch (Exception e) {
            print(this, "Error in changeDate1 :: " + e.getMessage());
        }
        return str;
    }

    public String changeDate2(String date) {
        String str = "0000-00-00";
        try {
            if (date != null && !date.equals("") && !date.equals("0000-00-00")) {
                SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
                SimpleDateFormat sdf2 = new SimpleDateFormat("dd-MM-yyyy");
                str = sdf2.format(sdf1.parse(date));
            }
        } catch (Exception e) {
            print(this, "Error in changeDate1 :: " + e.getMessage());
        }
        return str;
    }

    public String changeDate3(String date) {
        String str = "0000-00-00 00:00";
        try {
            if (date != null && !date.equals("") && !date.equals("00-00-0000")) {
                SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy HH:mm");
                SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                str = sdf2.format(sdf1.parse(date));
            }
        } catch (Exception e) {
            print(this, "Error in changeDate3 :: " + e.getMessage());
        }
        return str;
    }

    public int getDiffTwoDateInt(String sdate, String edate, String sformatv) {
        int diff = 0;
        try {
            if (sdate != null && !sdate.equals("") && !sdate.equals("00-00-0000") && edate != null && !edate.equals("") && !edate.equals("00-00-0000")) {
                SimpleDateFormat sdf = new SimpleDateFormat(sformatv);
                long l1 = sdf.parse(sdate).getTime();
                long l2 = sdf.parse(edate).getTime();
                long d = l2 - l1;
                long div = (long) 24 * 60 * 60 * 1000;
                long f1 = d / div;
                diff = (int) f1;
                diff = diff + 1;
            }
        } catch (Exception e) {
            diff = 0;
        }
        return diff;
    }

    public Map sortByName(final HashMap record, final int tp) {
        Comparator comparator = new Comparator() {
            public int compare(Object o1, Object o2) {
                String key1 = ((String) (o1 != null ? o1 : "")).toUpperCase();//key1
                String key2 = ((String) (o2 != null ? o2 : "")).toUpperCase();//key2           
                if (tp == 1) {
                    return (key1.compareTo(key2));
                } else {
                    return (key2.compareTo(key1));
                }
            }
        };
        Map sortByName = new TreeMap(comparator);
        sortByName.putAll(record);
        return sortByName;
    }

    public Map sortById(final HashMap record, final int tp) {
        Comparator comparator = new Comparator() {
            public int compare(Object o1, Object o2) {
                int key1 = Integer.parseInt((String) o1);
                int key2 = Integer.parseInt((String) o2);
                if (tp == 1) {
                    if (key1 > key2) {
                        return 1;
                    } else if (key1 < key2) {
                        return -1;
                    } else {
                        return 0;
                    }
                } else if (key1 < key2) {
                    return 1;
                } else if (key1 > key2) {
                    return -1;
                } else {
                    return 0;
                }
            }
        };
        Map sortById = new TreeMap(comparator);
        sortById.putAll(record);
        return sortById;
    }

    public Map sortByDouble(final HashMap record, final int tp) {
        Comparator comparator = new Comparator() {
            public int compare(Object o1, Object o2) {
                double key1 = Double.parseDouble((String) o1);
                double key2 = Double.parseDouble((String) o2);
                if (tp == 1) {
                    if (key1 > key2) {
                        return 1;
                    } else if (key1 < key2) {
                        return -1;
                    } else {
                        return 0;
                    }
                } else if (key1 < key2) {
                    return 1;
                } else if (key1 > key2) {
                    return -1;
                } else {
                    return 0;
                }
            }
        };
        Map sortByDouble = new TreeMap(comparator);
        sortByDouble.putAll(record);
        return sortByDouble;
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

    public int currYear() {
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Asia/Calcutta"));
        return cal.get(Calendar.YEAR);
    }

    public String currDate3() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Asia/Calcutta"));
        java.util.Date dt = cal.getTime();
        return sdf.format(dt);
    }

    public String getLocalIp() {
        String ip = "";
        try {
            InetAddress addr = InetAddress.getLocalHost();
            byte[] ipAddr = addr.getAddress();
            for (int i = 0; i < ipAddr.length; i++) {
                if (i > 0) {
                    ip += ".";
                }
                ip += ipAddr[i] & 0xFF;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ip;
    }

    public String getCookieValue(Cookie[] cookies, String cookieName, String defaultValue) {
        if (cookies != null) {
            for (int i = 0; i < cookies.length; i++) {
                Cookie cookie = cookies[i];
                if (cookieName.equals(cookie.getName())) {
                    return (cookie.getValue());
                }
            }
        }
        return (defaultValue);
    }

    public UserInfo getModulePermission(int moduleId, ArrayList list) {
        UserInfo top_info = null;
        try {
            if (list.size() > 0) {
                for (int i = 0; i < list.size(); i++) {
                    UserInfo info = (UserInfo) list.get(i);
                    if (info != null) {
                        if (info.getModuleId() == moduleId) {
                            top_info = info;
                        }
                    }
                }
            }
        } catch (Exception exception) {
            print(this, "getModulePermission :: " + exception.getMessage());
        }
        return top_info;
    }

    public int checkUserSession(HttpServletRequest request, int moduleId, String permission) {
        int userId = 0;
        if (request.getSession().getAttribute("PERMISSION_LIST") != null) {
            if (permission.equals("Y")) {
                if (request.getSession().getAttribute("LOGININFO") != null) {
                    UserInfo uInfo = (UserInfo) request.getSession().getAttribute("LOGININFO");
                    if (uInfo != null) {
                        uInfo.setApproveper("Y");
                        uInfo.setViewper("Y");
                        uInfo.setAddper("Y");
                        uInfo.setEditper("Y");
                        uInfo.setDeleteper("Y");
                        uInfo.setApproveper("Y");
                        userId = uInfo.getUserId();
                    }
                    request.getSession().setAttribute("LOGININFO", uInfo);
                }
            } else {
                ArrayList list = (ArrayList) request.getSession().getAttribute("PERMISSION_LIST");
                UserInfo per_info = getModulePermission(moduleId, list);
                if (per_info != null && per_info.getModuleId() == moduleId) {
                    if (request.getSession().getAttribute("LOGININFO") != null) {
                        UserInfo uInfo = (UserInfo) request.getSession().getAttribute("LOGININFO");
                        if (uInfo != null) {
                            uInfo.setModuleId(per_info.getModuleId());
                            uInfo.setApproveper(per_info.getApproveper());
                            uInfo.setViewper(per_info.getViewper());
                            uInfo.setAddper(per_info.getAddper());
                            uInfo.setEditper(per_info.getEditper());
                            uInfo.setDeleteper(per_info.getDeleteper());
                            uInfo.setApproveper(per_info.getApproveper());
                            userId = uInfo.getUserId();
                        }
                        request.getSession().setAttribute("LOGININFO", uInfo);
                    } else {
                        userId = -1;
                    }
                } else {
                    userId = -2;
                }
            }
        } else {
            userId = -1;
        }
        return userId;
    }

    public Map sortByDate(final HashMap record, final int tp, final String format) {
        Comparator comparator;
        comparator = new Comparator() {
            public int compare(Object o1, Object o2) throws ClassCastException {
                java.util.Date d1 = null;
                java.util.Date d2 = null;
                try {
                    DateFormat df = new SimpleDateFormat(format);
                    String key1 = (String) o1;//key1
                    String key2 = (String) o2;//key2
                    d1 = df.parse(key1);
                    d2 = df.parse(key2);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (tp == 1) {
                    return (d1.compareTo(d2));
                } else {
                    return (d2.compareTo(d1));
                }
            }
        };
        Map sortByDateTime = new TreeMap(comparator);
        sortByDateTime.putAll(record);
        return sortByDateTime;
    }

    public String uploadFile(int id, String fileHidden, FormFile image, String fn, String add_product_file, String folderName) {
        String photoName = "", photo = "", extension = "";
        if (image != null) {
            if (image.getFileSize() > 0) {
                photoName = folderName + "/" + fn;
                photo = image.getFileName();
            }
            if ((photo != null && !photo.equals(""))) {
                try {
                    if ((photo.lastIndexOf('.') >= 0)) {
                        extension = photo.substring(photo.lastIndexOf('.'));
                    }
                    int size = image.getFileSize();
                    if (size != 0) {
                        if (id > 0) {
                            if ((fileHidden != null && !fileHidden.equals(""))) {
                                String deletePath = add_product_file + fileHidden;
                                File file = new File(deletePath);
                                if (file.exists()) {
                                    file.delete();
                                }
                            }
                        }
                        byte[] byteArr = new byte[size];
                        ByteArrayInputStream bytein = new ByteArrayInputStream(image.getFileData());
                        bytein.read(byteArr);
                        bytein.close();
                        OutputStream stream = new FileOutputStream(add_product_file + photoName + extension);
                        stream.write(byteArr);
                    }
                } catch (Exception e) {
                    print(this, "catch block of file::" + e);
                }
            }
        }
        return (photoName + extension);
    }

    public void createLoginAccess(int userId, String ipAddrStr, String localip, String desc, int moduleId) {
        StringBuilder sb = new StringBuilder();
        sb.append("insert into t_loginaccess ");
        sb.append("(i_userid,i_moduleid, s_ipaddress, s_localip, s_description, ts_regdate) ");
        sb.append("values (?, ?, ?, ?, ?, ?)");
        String query = (sb.toString()).intern();
        sb.setLength(0);
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, userId);
            pstmt.setInt(2, moduleId);
            pstmt.setString(3, ipAddrStr);
            pstmt.setString(4, localip);
            pstmt.setString(5, desc);
            pstmt.setString(6, currDate1());
            print(this, "createLoginAccess :: " + pstmt.toString());
            pstmt.executeUpdate();
        } catch (Exception exception) {
            print(this, "createLoginAccess :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
    }

    public void createHistoryAccess(Connection conn1, int userId, String ipAddrStr, String localip, String desc, int moduleId, int recordid) {
        StringBuilder sb = new StringBuilder();
        sb.append("insert into t_transaction ");
        sb.append("(i_moduleid, i_recordid, i_userid, s_ipaddress, s_localip, s_action, ts_regdate) ");
        sb.append("values (?, ?, ?, ?, ?, ?, ?)");
        String query = (sb.toString()).intern();
        sb.setLength(0);
        try {
            if (conn1 == null) {
                conn1 = getConnection();
            }
            pstmt = conn1.prepareStatement(query);
            pstmt.setInt(1, moduleId);
            pstmt.setInt(2, recordid);
            pstmt.setInt(3, userId);
            pstmt.setString(4, ipAddrStr);
            pstmt.setString(5, localip);
            pstmt.setString(6, desc);
            pstmt.setString(7, currDate1());
            print(this, "createHistoryAccess :: " + pstmt.toString());
            pstmt.executeUpdate();
        } catch (Exception exception) {
            print(this, "createHistoryAccess :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
    }

    public ArrayList getHistoryListById(int moduleId, int recordId) {
        ArrayList list = new ArrayList();
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("select DATE_FORMAT(t_transaction.ts_regdate, '%d-%m-%Y %h:%i %p') as entrydate, ");
            sb.append("REPLACE(CONCAT_WS(' ', t_userlogin.s_firstname, t_userlogin.s_lastname), \"  \", \" \") as name, t_transaction.s_action ");
            sb.append("from t_transaction left join t_userlogin on (t_userlogin.i_userid = t_transaction.i_userid) ");
            sb.append("where i_moduleid = ? and i_recordid = ? order by t_transaction.ts_regdate");
            String query = sb.toString().intern();
            sb.setLength(0);
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, moduleId);
            pstmt.setInt(2, recordId);
            print(this, "getHistoryListById :: " + pstmt.toString());

            rs = pstmt.executeQuery();
            String datetime, userName, action;
            while (rs.next()) {
                datetime = rs.getString(1) != null ? rs.getString(1) : "";
                userName = rs.getString(2) != null ? rs.getString(2) : "";
                action = rs.getString(3) != null ? rs.getString(3) : "";
                list.add(new StatsInfo(datetime, userName, action));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return list;
    }

    public int currMonth() {
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Asia/Calcutta"));
        return (cal.get(Calendar.MONTH) + 1);
    }

    public String createFolder(String path) {
        String foldername = "";
        try {
            Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Asia/Calcutta"));
            int month = (cal.get(Calendar.MONTH) + 1);
            int year = cal.get(Calendar.YEAR);
            SimpleDateFormat sdf1 = new SimpleDateFormat("M");
            SimpleDateFormat sdf2 = new SimpleDateFormat("MMM");
            String monthName = sdf2.format(sdf1.parse(month + ""));

            foldername = (monthName + "-" + year).toLowerCase();
            File dir = new File(path + foldername + "/");
            if (!dir.exists()) {
                dir.mkdir();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return foldername;
    }

    public static String dateFolder() {
        SimpleDateFormat sdf = new SimpleDateFormat("MMM-yyyy");
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Asia/Calcutta"));
        java.util.Date dt = cal.getTime();
        return sdf.format(dt).toLowerCase();
    }

    public int createMailLog(int type, String name, String to, String cc, String bcc, String from, String subject, String filename, String attachmentpath, int shortlistId, String username, int oflag) {
        int id = 0;
        try {

            StringBuilder sb = new StringBuilder();
            sb.append("insert into t_maillog ");
            sb.append("(i_type, s_name, s_to, s_cc, s_bcc, s_from, s_subject, s_filename, ts_regdate, ts_moddate,s_attachmentpath,i_shortlistid,s_sendby,i_oflag) ");
            sb.append("values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

            String query = sb.toString().intern();
            sb.setLength(0);
            print(this, "createMailLog :: " + query);
            conn = getConnection();
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
            pstmt.setString(11, attachmentpath);
            pstmt.setInt(12, shortlistId);
            pstmt.setString(13, username);
            pstmt.setInt(14, oflag);
            print(this, "createMailLog :: " + query);
            pstmt.executeUpdate();
            rs = pstmt.getGeneratedKeys();
            while (rs.next()) {
                id = rs.getInt(1);
            }
        } catch (Exception exception) {
            print(this, "createMailLog :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return id;
    }

    public static double haversin(double val) {
        return Math.pow(Math.sin(val / 2), 2);
    }

    public static double distance(double startLat, double startLong, double endLat, double endLong) {
        double d = 0;
        try {
            if (endLat > 0) {
                double dLat = Math.toRadians((endLat - startLat));
                double dLong = Math.toRadians((endLong - startLong));
                startLat = Math.toRadians(startLat);
                endLat = Math.toRadians(endLat);
                double a = haversin(dLat) + Math.cos(startLat) * Math.cos(endLat) * haversin(dLong);
                double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
                d = EARTH_RADIUS * c; // <-- d
                d = Math.round(d * 1000);
            } else {
                d = -1;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return d;
    }

    public String getFromCell(Cell cell1) {
        String s = "";
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            DecimalFormat nf = new DecimalFormat("######################.##");
            if (cell1 != null && cell1.getCellType() == Cell.CELL_TYPE_NUMERIC) {
                if (DateUtil.isCellDateFormatted(cell1)) {
                    java.util.Date d1 = cell1.getDateCellValue();
                    s = sdf.format(d1);

                } else {
                    double d = cell1.getNumericCellValue();
                    s = nf.format(d);
                }

            } else if (cell1 != null && cell1.getCellType() == Cell.CELL_TYPE_STRING) {
                cell1.setCellType(Cell.CELL_TYPE_STRING);
                s = cell1.getStringCellValue().trim();
            } else if (cell1 != null && cell1.getCellType() == Cell.CELL_TYPE_BLANK) {
                cell1.setCellType(Cell.CELL_TYPE_BLANK);
                s = "";
            } else if (cell1 != null && cell1.getCellType() == Cell.CELL_TYPE_FORMULA) {
                cell1.setCellType(Cell.CELL_TYPE_FORMULA);
                s = cell1.getCellFormula().trim();
            } else {
                s = "";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return s;
    }

    public String getDecimal(double d) {
        DecimalFormat nf = new DecimalFormat("##");
        nf.setMinimumIntegerDigits(1);
        nf.setMinimumFractionDigits(2);
        nf.setMaximumFractionDigits(2);
        if (d > 0) {
            return nf.format(d);
        } else if (d == 0) {
            return "0.00";
        } else {
            d = Math.abs(d);
            return "-" + nf.format(d);
        }
    }

    public double getDecimalDouble(double d) {
        double val = 0;
        DecimalFormat nf = new DecimalFormat("##");
        nf.setMinimumIntegerDigits(1);
        nf.setMinimumFractionDigits(2);
        nf.setMaximumFractionDigits(2);
        if (d > 0) {
            val = Double.parseDouble(nf.format(d));
        } else if (d == 0) {
            val = 0.00;
        }
        return val;
    }

    public String changeNum(int num, int digit) {
        DecimalFormat nf = new DecimalFormat("0");
        nf.setMinimumIntegerDigits(digit);
        return nf.format(num);
    }

    public String IndianFormat(double d, int n) {
        String s = "";
        try {
            if (d > 0) {
                //com.ibm.icu.text.NumberFormat nf = com.ibm.icu.text.NumberFormat.getCurrencyInstance(new Locale("en", "in"));
                NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("en", "in"));
                nf.setMinimumFractionDigits(n);
                nf.setMaximumFractionDigits(n);
                nf.setMinimumIntegerDigits(1);
                s = nf.format(d).replace("Rs.", "").replace("Rs. ", "").replace("Re. ", "");
            } else if (d < 0) {
                d = d * -1;
                NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("en", "in"));
                nf.setMinimumFractionDigits(n);
                nf.setMaximumFractionDigits(n);
                nf.setMinimumIntegerDigits(1);
                s = "-" + nf.format(d).replace("Rs.", "").replace("Rs. ", "").replace("Re. ", "");
            } else {
                s = "0";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return s;
    }

    public void generatePDFString(String inputstr, File outputPDFFile, Dimension format, String fontsDir,
            String headerBody, String footerBody, String watermarkurl, int htmlwidth) {
        try {
            java.io.FileOutputStream fos = new java.io.FileOutputStream(outputPDFFile);
            PD4ML pd4ml = new PD4ML();
            pd4ml.setPageInsets(new Insets(25, 10, 5, 10));
            if (htmlwidth > 0) {
                pd4ml.setHtmlWidth(htmlwidth);
            } else {
                pd4ml.adjustHtmlWidth();
            }
            pd4ml.setPageSize(format);
            //pd4ml.setPageSize(pd4ml.changePageOrientation(PD4Constants.A4));
            if (fontsDir != null && fontsDir.length() > 0) {
                pd4ml.useTTF(fontsDir, true);
            }
            if (headerBody != null && headerBody.length() > 0) {
                PD4PageMark header = new PD4PageMark();
                header.setAreaHeight(-1); // autocompute
                header.setWatermark(watermarkurl, new Rectangle(0, 0, 595, 810), 60);
                header.setHtmlTemplate(headerBody); // autocompute
                pd4ml.setPageHeader(header);
            } else if (headerBody.equals("") && watermarkurl != null && !watermarkurl.equals("")) {
                PD4PageMark header = new PD4PageMark();
                header.setAreaHeight(-1); // autocompute
                header.setWatermark(watermarkurl, new Rectangle(0, 0, 595, 810), 60);//x-axis, y-axis , witdth, height, opacity
                header.setHtmlTemplate(""); // autocompute
                pd4ml.setPageHeader(header);
            }
            PD4PageMark footer = new PD4PageMark();
            footer.setAreaHeight(-1);
            footer.setPagesToSkip(0);
            footer.setFontSize(12);
            footer.setHtmlTemplate(footerBody);
            pd4ml.setPageFooter(footer);
            pd4ml.render(new StringReader(inputstr), fos);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String replacefordir(String input) {
        String str = "";
        if (input != null && !input.equals("")) {
            if (input != null) {
                str = input.replaceAll("\\.\\./", "");
            }
        }
        return str;
    }

    public boolean validateCaptcha(String s, String sessioval) {
        boolean b = false;
        if (sessioval.replaceAll("\\s", "").equals(s.replaceAll("\\s", ""))) {
            b = true;
        }
        return b;
    }

    public String generateCaptcha() {
        String chars = "0123456789ABCDEFGHJKLMNPQRSTUVWXYZ0123456789abcdefghjkmnpqrstuvwxyx";
        int numberOfCodes = 0;
        String code = "";
        while (numberOfCodes < 6) {
            char c = chars.charAt((int) (Math.random() * chars.length()));
            code += " " + c;
            numberOfCodes++;
        }
        return code;
    }

    public String[] findFromToDate(int month, int year) {
        month = month - 1;
        String date[] = new String[2];
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Asia/Calcutta"));
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        String fromDate = sdf.format(cal.getTime());

        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        String toDate = sdf.format(cal.getTime());
        date[0] = fromDate;
        date[1] = toDate;
        return date;
    }

    public String changeDate5(String date) {
        String str = "0000-00-00";
        try {
            if (date != null && !date.equals("") && !date.equals("00/00/0000")) {
                SimpleDateFormat sdf1 = new SimpleDateFormat("d/M/yyyy");
                SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
                str = sdf2.format(sdf1.parse(date));
            }
        } catch (Exception e) {

        }
        return str;
    }

    public String saveImage(String datastr, String filepath, String folderName, String filename) {
        String ext = "";
        try {
            String extstr = datastr.split(",")[0];
            ext = extstr.substring(extstr.indexOf("/") + 1, extstr.indexOf(";"));
            if (ext.equalsIgnoreCase("vnd.openxmlformats-officedocument.presentationml.presentation")) {
                ext = "pptx";
            } else if (ext.equalsIgnoreCase("vnd.ms-powerpoint")) {
                ext = "ppt";
            } else if (ext.equalsIgnoreCase("msword")) {
                ext = "doc";
            }
            String base64Image = datastr.split(",")[1];
            byte[] data = org.apache.tomcat.util.codec.binary.Base64.decodeBase64(base64Image);
            try (OutputStream stream = new FileOutputStream(filepath + folderName + "/" + filename + "." + ext)) {
                stream.write(data);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        String fn = folderName + "/" + filename + "." + ext;
        return fn;
    }

    public StatsInfo postMailAttach1(String to[], String cc[], String bcc[], String message, String sub, String filePath3, String localFileName3, int check) throws MessagingException {
        int ccval = 0;
        String fromaddress = "";
        StatsInfo info;
        try {
            String port = "", host = "", mail_server_username = "", mail_server_password = "", displayName = "";
            Connection conn = getConnection();
            String query_smtp = "select s_host, s_port, s_serverusername, s_serverpassword, s_from, s_displayname from t_smtp order by ts_regdate desc limit 0, 1";
            Statement stmt_smtp = conn.createStatement();
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
            conn.close();

            Authenticator authenticator = new Authenticator(mail_server_username, mail_server_password);
            Properties properties = new Properties();
            properties.put("mail.smtp.host", host);
            if (port != null && !port.equals("")) {
                properties.put("mail.smtp.port", port);
            }
            properties.put("mail.smtp.auth", "true");

//            properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
//            properties.put("mail.smtp.socketFactory.fallback", "false");
//            properties.put("mail.smtp.starttls.enable", "true");
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

            if (localFileName3 != null && !localFileName3.equals("")) {
                File ff = new File(filePath3);
                if (ff.exists()) {
                    BodyPart messageBodyPart1 = new MimeBodyPart();
                    DataSource source = new FileDataSource(filePath3);
                    messageBodyPart1.setDataHandler(new DataHandler(source));
                    messageBodyPart1.setFileName(localFileName3);
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
                default:
                    break;
            }
            dt = endformat.format(cal.getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dt;
    }

    public StatsInfo postMailAttachMF(String to[], String cc[], String bcc[], String message, String sub, String filePath[],
            String localFileName[], int check) throws MessagingException {
        int ccval = 0;
        String fromaddress = "";
        StatsInfo info;
        int len = 0;
        if (filePath != null) {
            len = filePath.length;
        }
        try {
            String port = "", host = "", mail_server_username = "", mail_server_password = "", displayName = "";
            Connection conn = getConnection();
            String query_smtp = "select s_host, s_port, s_serverusername, s_serverpassword, s_from, s_displayname from t_smtp order by ts_regdate desc limit 0, 1";
            Statement stmt_smtp = conn.createStatement();
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
            conn.close();
            Authenticator authenticator = new Authenticator(mail_server_username, mail_server_password);
            Properties properties = new Properties();
            properties.put("mail.smtp.host", host);
            if (port != null && !port.equals("")) {
                properties.put("mail.smtp.port", port);
            }
            properties.put("mail.smtp.auth", "true");

//            properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
//            properties.put("mail.smtp.socketFactory.fallback", "false");
//            properties.put("mail.smtp.starttls.enable", "true");
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
            if (len > 0) {
                for (int i = 0; i < len; i++) {
                    if (filePath[i] != null && !filePath[i].equals("") && localFileName[i] != null) {
                        File ff = new File(filePath[i]);
                        if (ff.exists()) {
                            BodyPart messageBodyPart1 = new MimeBodyPart();
                            DataSource source = new FileDataSource(filePath[i]);
                            messageBodyPart1.setDataHandler(new DataHandler(source));
                            messageBodyPart1.setFileName(localFileName[i]);
                            multipart2.addBodyPart(messageBodyPart1);
                        }
                    }
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

    public String getfilename(String filename) {
        if (filename != null && !filename.equals("")) {
            filename = filename.replaceAll("[^a-zA-Z0-9-\\.]+", "-").trim();
            filename = filename.substring(0, filename.lastIndexOf("."));
        }
        return filename;
    }
}
