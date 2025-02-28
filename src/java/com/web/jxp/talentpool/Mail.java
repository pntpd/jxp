/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.jxp.talentpool;

import com.web.jxp.base.Base;
import static com.web.jxp.base.Base.parseCommaDelimString;
import com.web.jxp.base.StatsInfo;
import com.web.jxp.base.Template;
import static com.web.jxp.common.Common.print;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import javax.mail.MessagingException;

/**
 *
 * @author wesld
 */
public class Mail extends Base {

    public String getpatchbody(String description, String htmlFile) {
        String adminpage = getMainPath("template_path") + htmlFile;
        HashMap hashmap = new HashMap();
        Template template = new Template(adminpage);
        hashmap.put("MAILBODY", description);
        return template.patch(hashmap);
    }

    public int sendCVMail(String fromval, String toval, String ccval, String bccval, String subject, String description,
            String candidatename, String filename, String clientname, int candidateId, String username) throws MessagingException {
        String toaddress[] = parseCommaDelimString(toval);
        String ccaddress[] = parseCommaDelimString(ccval);
        String bccaddress[] = parseCommaDelimString(bccval);
        String filePath = getMainPath("add_resumetemplate_pdf");
        String vfilePath = getMainPath("view_resumetemplate_pdf");
        String attachmentpath = vfilePath + filename;
        String sdescription = description.replaceAll("\n", "<br/>");
        String mailbody = getpatchbody(sdescription, "mail_selection.html");

        String file_maillog = getMainPath("file_maillog");
        java.util.Date nowmail = new java.util.Date();
        String fn_mail = "cvma-" + String.valueOf(nowmail.getTime());
        String filePath_html = createFolder(file_maillog);
        String fname = writeHTMLFile(mailbody, file_maillog + "/" + filePath_html, fn_mail + ".html");

        StatsInfo sinfo = postMailAttach(toaddress, ccaddress, bccaddress, mailbody, subject, filePath + filename, candidatename + "_CV.pdf", -1);
        String from = "";
        if (sinfo != null) {
            from = sinfo.getDdlLabel();
        }

        int maillogId = createMailLog(10, clientname, toval, ccval, bccval, from, subject, "/" + filePath_html + "/" + fname, attachmentpath, candidateId, username, 1);

        return maillogId;
    }

    public int createMailLog(int type, String name, String to, String cc, String bcc, String from, String subject, String filename, String attachmentpath, int candidateId,
            String username, int oflag) {
        int id = 0;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {

            StringBuilder sb = new StringBuilder();
            sb.append("insert into t_maillog ");
            sb.append("(i_type, s_name, s_to, s_cc, s_bcc, s_from, s_subject, s_filename, ts_regdate, ts_moddate,s_attachmentpath,i_candidateid,s_sendby,i_oflag) ");
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
            pstmt.setInt(12, candidateId);
            pstmt.setString(13, username);
            pstmt.setInt(14, oflag);
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
    
    public String getusertype(int key) {
        String s = "";
        if (key == 1) {
            s = "company";
        } else if (key == 2) {
            s = "client";
        }
        return s;
    }
    
    //For contract mail
    public int sendContractMail(String fromval, String toval, String ccval, String bccval, String subject, String description,
            String candidatename, String filename, String clientname, int candidateId, String username) throws MessagingException {
        String toaddress[] = parseCommaDelimString(toval);
        String ccaddress[] = parseCommaDelimString(ccval);
        String bccaddress[] = parseCommaDelimString(bccval);
        String filePath = "";//getMainPath("add_candidate_file");
        String vfilePath = "";//getMainPath("view_candidate_file");
        String attachmentpath = vfilePath + filename;
        String sdescription = description.replaceAll("\n", "<br/>");
        String mailbody = getpatchbody(sdescription, "mail_selection.html");

        String file_maillog = getMainPath("file_maillog");
        java.util.Date nowmail = new java.util.Date();
        String fn_mail = "Employment Contract-" + String.valueOf(nowmail.getTime());
        String filePath_html = createFolder(file_maillog);
        String fname = writeHTMLFile(mailbody, file_maillog + "/" + filePath_html, fn_mail + ".html");

        StatsInfo sinfo = postMailAttach(toaddress, ccaddress, bccaddress, mailbody, subject, filePath + filename, candidatename + "_Contract.pdf", -1);
        String from = "";
        if (sinfo != null) {
            from = sinfo.getDdlLabel();
        }

        int maillogId = createMailLog(18, clientname, toval, ccval, bccval, from, subject, filePath_html + "/" + fname, attachmentpath, candidateId, username, 1);

        return maillogId;
    }
}
