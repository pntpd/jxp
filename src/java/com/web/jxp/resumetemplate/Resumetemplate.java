package com.web.jxp.resumetemplate;

import com.mysql.jdbc.Statement;
import com.web.jxp.base.Base;
import com.web.jxp.common.Common;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import static com.web.jxp.common.Common.*;
import java.io.File;
import java.util.Collection;
import java.util.LinkedList;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

public class Resumetemplate extends Base {

    private static final Logger logger = Logger.getLogger(Class.class.getName());
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    public ArrayList getResumetemplateByName(String search, int next, int count, int allclient, String permission, String cids, String assetids) {
        ArrayList resumetemplates = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT t_resumetemplate.i_resumetemplateid, t_resumetemplate.s_name, t_resumetemplate.i_status, t_client.s_name FROM t_resumetemplate ");
        sb.append("LEFT JOIN t_client ON (t_resumetemplate.i_clientid = t_client.i_clientid) ");
        sb.append("WHERE 0 = 0 ");
        if (search != null && !search.equals("")) {
            sb.append("AND (t_resumetemplate.s_name LIKE ? OR t_client.s_name LIKE ? ) ");
        }
        if (allclient == 1) {
            sb.append(" AND t_client.i_clientid > 0 ");
        } else {
            if (!cids.equals("")) {
                sb.append("AND t_client.i_clientid IN (" + cids + ") ");
            } else {
                sb.append("AND t_client.i_clientid < 0 ");
            }
        }
        sb.append("ORDER BY t_resumetemplate.i_status,t_client.s_name, t_resumetemplate.s_name ");
        if (count > 0) {
            sb.append(" LIMIT ").append(next * count).append(", ").append(count);
        }
        String query = (sb.toString()).intern();
        sb.setLength(0);

        sb.append("SELECT count(1) FROM t_resumetemplate ");
        sb.append("LEFT JOIN t_client ON (t_resumetemplate.i_clientid = t_client.i_clientid)");
        sb.append("WHERE 0=0 ");
        if (search != null && !search.equals("")) {
            sb.append("AND (t_resumetemplate.s_name LIKE ? OR t_client.s_name LIKE ?) ");
        }
        if (allclient == 1) {
            sb.append(" AND t_client.i_clientid > 0 ");
        } else {
            if (!cids.equals("")) {
                sb.append(" AND t_client.i_clientid IN (" + cids + ") ");
            } else {
                sb.append(" AND t_client.i_clientid < 0 ");
            }
        }
        String countquery = (sb.toString()).intern();
        sb.setLength(0);
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            int scc = 0;
            if (search != null && !search.equals("")) {
                pstmt.setString(++scc, "%" + (search) + "%");
                pstmt.setString(++scc, "%" + (search) + "%");
            }
            logger.info("getResumetemplateByName :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String name, clientname;
            int resumetemplateId, status;
            while (rs.next()) {
                resumetemplateId = rs.getInt(1);
                name = rs.getString(2) != null ? rs.getString(2) : "";
                status = rs.getInt(3);
                clientname = rs.getString(4) != null ? rs.getString(4) : "";
                resumetemplates.add(new ResumetemplateInfo(resumetemplateId, name, status, 0, clientname, 0));
            }
            rs.close();

            pstmt = conn.prepareStatement(countquery);
            scc = 0;
            if (search != null && !search.equals("")) {
                pstmt.setString(++scc, "%" + (search) + "%");
                pstmt.setString(++scc, "%" + (search) + "%");
            }
            print(this, "getResumetemplateByName :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                resumetemplateId = rs.getInt(1);
                resumetemplates.add(new ResumetemplateInfo(resumetemplateId, "", 0, 0));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return resumetemplates;
    }

    public ArrayList getFinalRecord(ArrayList l, String colId, int tp) {
        ArrayList list = null;
        HashMap record = null;
        ResumetemplateInfo info;
        int total = 0;
        if (l != null) {
            total = l.size();
        }
        try {
            if (total > 0) {
                record = new HashMap();
                int i;
                for (i = 0; i < total; i++) {
                    info = (ResumetemplateInfo) l.get(i);
                    record.put(getInfoValue(info, colId), info);
                }
            }
            Map map = sortByName(record, tp);
            Iterator it = map.keySet().iterator();
            list = new ArrayList();
            ResumetemplateInfo rInfo;
            while (it.hasNext()) {
                String key = (String) it.next();
                int i;
                for (i = 0; i < total; i++) {
                    rInfo = (ResumetemplateInfo) l.get(i);
                    String str = getInfoValue(rInfo, colId);
                    if (str.equals(key)) {
                        list.add(rInfo);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // to sort search result
    public String getInfoValue(ResumetemplateInfo info, String i) {
        String infoval = "";
        if (i != null && i.equals("1")) {
            infoval = info.getClientname()!= null ? info.getClientname() : "";
        }
        if (i != null && i.equals("2")) {
            infoval = info.getName() != null ? info.getName() : "";
        }
        return infoval;
    }

    public ResumetemplateInfo getResumetemplateDetailById(int resumetemplateId) {
        ResumetemplateInfo info = null;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT s_name, i_clientid, s_description, i_status, s_wids, s_eids, s_dids, s_tids, i_temptype, ");
        sb.append("s_description2, s_description3, s_filename, i_exptype, ");
        sb.append("s_label1, s_label2, s_label3, s_label4, s_label5, s_label6, s_label7, s_label8, s_label9, s_label10, s_label11, s_label12, ");
        sb.append("s_label13, s_label14, s_label15, s_label16, s_label17, s_label18, s_label19, s_label20, s_label21, s_label22, s_label23, s_label24, s_label25, s_label26, ");
        sb.append("i_edutype, i_doctype, i_certtype ");
        sb.append("FROM t_resumetemplate where i_resumetemplateid = ? ");
        String query = sb.toString().intern();
        sb.setLength(0);
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, resumetemplateId);
            print(this, "getResumetemplateDetailById :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String name, description, description2, description3, workexp, edudetail, docdetail, certificate, filename,
                    label1, label2, label3, label4, label5, label6, label7, label8, label9, label10, label11, label12,
                    label13, label14, label15, label16, label17, label18, label19, label20, label21, label22, label23,
                    label24, label25, label26;
            int status, clientid, temptype, exptype, edutype, doctype, certtype;
            while (rs.next()) 
            {
                name = rs.getString(1) != null ? rs.getString(1) : "";
                clientid = rs.getInt(2);
                description = rs.getString(3) != null ? rs.getString(3) : "";
                status = rs.getInt(4);
                workexp = rs.getString(5) != null ? rs.getString(5) : "";
                edudetail = rs.getString(6) != null ? rs.getString(6) : "";
                docdetail = rs.getString(7) != null ? rs.getString(7) : "";
                certificate = rs.getString(8) != null ? rs.getString(8) : "";
                temptype = rs.getInt(9);
                description2 = rs.getString(10) != null ? rs.getString(10) : "";
                description3 = rs.getString(11) != null ? rs.getString(11) : "";
                filename = rs.getString(12) != null ? rs.getString(12) : "";
                exptype = rs.getInt(13);
                label1 = rs.getString(14) != null ? rs.getString(14) : "";
                label2 = rs.getString(15) != null ? rs.getString(15) : "";
                label3 = rs.getString(16) != null ? rs.getString(16) : "";
                label4 = rs.getString(17) != null ? rs.getString(17) : "";
                label5 = rs.getString(18) != null ? rs.getString(18) : "";
                label6 = rs.getString(19) != null ? rs.getString(19) : "";
                label7 = rs.getString(20) != null ? rs.getString(20) : "";
                label8 = rs.getString(21) != null ? rs.getString(21) : "";
                label9 = rs.getString(22) != null ? rs.getString(22) : "";
                label10 = rs.getString(23) != null ? rs.getString(23) : "";
                label11 = rs.getString(24) != null ? rs.getString(24) : "";
                label12 = rs.getString(25) != null ? rs.getString(25) : "";
                label13 = rs.getString(26) != null ? rs.getString(26) : "";                
                label14 = rs.getString(27) != null ? rs.getString(27) : "";
                label15 = rs.getString(28) != null ? rs.getString(28) : "";
                label16 = rs.getString(29) != null ? rs.getString(29) : "";
                label17 = rs.getString(30) != null ? rs.getString(30) : "";
                label18 = rs.getString(31) != null ? rs.getString(31) : "";
                label19 = rs.getString(32) != null ? rs.getString(32) : "";
                label20 = rs.getString(33) != null ? rs.getString(33) : "";
                label21 = rs.getString(34) != null ? rs.getString(34) : "";
                label22 = rs.getString(35) != null ? rs.getString(35) : "";
                label23 = rs.getString(36) != null ? rs.getString(36) : "";
                label24 = rs.getString(37) != null ? rs.getString(37) : "";
                label25 = rs.getString(38) != null ? rs.getString(38) : "";
                label26 = rs.getString(39) != null ? rs.getString(39) : "";
                edutype = rs.getInt(40);
                doctype = rs.getInt(41);
                certtype = rs.getInt(42);
                
                info = new ResumetemplateInfo(resumetemplateId, name, clientid, description, status, workexp, 
                        edudetail, docdetail, certificate, temptype, description2, description3, filename, exptype,
                        label1, label2, label3, label4, label5, label6, label7, label8, label9, label10, label11, label12,
                        label13, label14, label15, label16, label17, label18, label19, label20, label21, label22, label23,
                        label24, label25, label26, edutype, doctype, certtype);
            }
        } catch (Exception exception) {
            print(this, "getResumetemplateDetailById :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return info;
    }

    public ResumetemplateInfo getResumetemplateDetailByIdforDetail(int resumetemplateId) 
    {
        ResumetemplateInfo info = null;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT t_resumetemplate.s_name, t_resumetemplate.i_status , t_client.s_name, ");
        sb.append("t_resumetemplate.s_description, t_resumetemplate.s_wids, t_resumetemplate.s_eids, ");
        sb.append("t_resumetemplate.s_dids, t_resumetemplate.s_tids, t_resumetemplate.i_temptype, ");
        sb.append("t_resumetemplate.s_description2, t_resumetemplate.s_description3, t_resumetemplate.s_filename, ");
        sb.append("t_resumetemplate.s_label1, t_resumetemplate.s_label2, t_resumetemplate.s_label3, t_resumetemplate.s_label4, ");
        sb.append("t_resumetemplate.s_label5, t_resumetemplate.s_label6, t_resumetemplate.s_label7, t_resumetemplate.s_label8, ");
        sb.append("t_resumetemplate.s_label9, t_resumetemplate.s_label10, t_resumetemplate.s_label11, t_resumetemplate.s_label12, ");
        sb.append("t_resumetemplate.s_label13, t_resumetemplate.s_label14, t_resumetemplate.s_label15, t_resumetemplate.s_label16, ");
        sb.append("t_resumetemplate.s_label17, t_resumetemplate.s_label18, t_resumetemplate.s_label19, t_resumetemplate.s_label20, ");
        sb.append("t_resumetemplate.s_label21, t_resumetemplate.s_label22, t_resumetemplate.s_label23, t_resumetemplate.s_label24, ");
        sb.append("t_resumetemplate.s_label25, t_resumetemplate.s_label26, t_resumetemplate.i_edutype, t_resumetemplate.i_doctype, t_resumetemplate.i_certtype ");
        sb.append("FROM t_resumetemplate ");
        sb.append("LEFT JOIN t_client ON (t_client.i_clientid =  t_resumetemplate.i_clientid) ");
        sb.append("WHERE t_resumetemplate.i_resumetemplateid = ? ");
        String query = sb.toString().intern();
        sb.setLength(0);
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, resumetemplateId);
            print(this, "getResumetemplateDetailByIdforDetail :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String name, clientname, description,description2, description3, workexp, edudetail, docdetail, certdetail, filename,
                    label1, label2, label3, label4, label5, label6, label7, label8, label9, label10, label11, label12,
                    label13, label14, label15, label16, label17, label18, label19, label20, label21, label22, label23,
                    label24, label25, label26;
            int status = 0, templatetype, edutype, doctype, certtype;
            while (rs.next()) {
                name = rs.getString(1) != null ? rs.getString(1) : "";
                status = rs.getInt(2);
                clientname = rs.getString(3) != null ? rs.getString(3) : "";
                description = rs.getString(4) != null ? rs.getString(4) : "";
                workexp = rs.getString(5) != null ? rs.getString(5) : "";
                edudetail = rs.getString(6) != null ? rs.getString(6) : "";
                docdetail = rs.getString(7) != null ? rs.getString(7) : "";
                certdetail = rs.getString(8) != null ? rs.getString(8) : "";
                templatetype = rs.getInt(9);
                description2 = rs.getString(10) != null ? rs.getString(10) : "";
                description3 = rs.getString(11) != null ? rs.getString(11) : "";
                filename = rs.getString(12) != null ? rs.getString(12) : "";
                label1 = rs.getString(13) != null ? rs.getString(13) : "";
                label2 = rs.getString(14) != null ? rs.getString(14) : "";
                label3 = rs.getString(15) != null ? rs.getString(15) : "";
                label4 = rs.getString(16) != null ? rs.getString(16) : "";
                label5 = rs.getString(17) != null ? rs.getString(17) : "";
                label6 = rs.getString(18) != null ? rs.getString(18) : "";
                label7 = rs.getString(19) != null ? rs.getString(19) : "";
                label8 = rs.getString(20) != null ? rs.getString(20) : "";
                label9 = rs.getString(21) != null ? rs.getString(21) : "";
                label10= rs.getString(22) != null ? rs.getString(22) : "";
                label11 = rs.getString(23) != null ? rs.getString(23) : "";
                label12 = rs.getString(24) != null ? rs.getString(24) : "";
                label13 = rs.getString(25) != null ? rs.getString(25) : "";
                label14 = rs.getString(26) != null ? rs.getString(26) : "";                
                label15 = rs.getString(27) != null ? rs.getString(27) : "";
                label16 = rs.getString(28) != null ? rs.getString(28) : "";
                label17 = rs.getString(29) != null ? rs.getString(29) : "";
                label18 = rs.getString(30) != null ? rs.getString(30) : "";
                label19 = rs.getString(31) != null ? rs.getString(31) : "";
                label20 = rs.getString(32) != null ? rs.getString(32) : "";
                label21 = rs.getString(33) != null ? rs.getString(33) : "";
                label22 = rs.getString(34) != null ? rs.getString(34) : "";
                label23 = rs.getString(35) != null ? rs.getString(35) : "";
                label24 = rs.getString(36) != null ? rs.getString(36) : "";
                label25 = rs.getString(37) != null ? rs.getString(37) : "";
                label26 = rs.getString(38) != null ? rs.getString(38) : "";
                edutype = rs.getInt(39);
                doctype = rs.getInt(40);
                certtype = rs.getInt(41);
                
                if (description != null && !description.equals("")) {
                    String add_path = getMainPath("add_resumetemplate_file");
                    description = readHTMLFile(description, add_path);
                }
                info = new ResumetemplateInfo(resumetemplateId, name, status, 0, clientname,description, 
                        workexp, edudetail, docdetail, certdetail, templatetype, description2, description3, filename,
                label1, label2, label3, label4, label5, label6, label7, label8, label9, label10, label11, label12,
                    label13, label14, label15, label16, label17, label18, label19, label20, label21, label22, label23,
                    label24, label25, label26, edutype, doctype, certtype);
            }
            rs.close();
        } catch (Exception exception) {
            print(this, "getResumetemplateDetailByIdforDetail :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return info;
    }

    public int createResumetemplate(ResumetemplateInfo info) {
        int resumetemplateId = 0;
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("insert into t_resumetemplate ");
            sb.append("(s_name, i_status, i_userid,i_clientid, s_description,s_wids, s_eids, s_dids, s_tids, i_temptype, i_exptype, i_edutype, i_doctype, i_certtype, ts_regdate, ts_moddate, s_description2, s_description3, s_filename, ");
            sb.append(" s_label1, s_label2, s_label3, s_label4, s_label5, s_label6, s_label7, s_label8, s_label9, s_label10, s_label11, s_label12, ");
            sb.append(" s_label13, s_label14, s_label15, s_label16, s_label17, s_label18, s_label19, s_label20, s_label21, s_label22, s_label23, s_label24, s_label25, s_label26 ) ");
            sb.append("values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?   , ?, ?, ?, ?, ?  , ?, ?, ?, ?, ?  , ?, ?, ?, ?, ?  , ?, ?, ?, ?, ?  , ?, ?, ?, ?, ?,   ?, ?, ?)");
            String query = (sb.toString()).intern();
            sb.setLength(0);

            conn = getConnection();
            pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            int scc = 0;
            pstmt.setString(++scc, (info.getName()));
            pstmt.setInt(++scc, info.getStatus());
            pstmt.setInt(++scc, info.getUserId());
            pstmt.setInt(++scc, info.getClientId());
            pstmt.setString(++scc, info.getDescription());
            pstmt.setString(++scc, info.getExpColumn());
            pstmt.setString(++scc, info.getEduColumn());
            pstmt.setString(++scc, info.getDocColumn());
            pstmt.setString(++scc, info.getTrainingColumn());
            pstmt.setInt(++scc, info.getTemptype());
            pstmt.setInt(++scc, info.getExptype());
            pstmt.setInt(++scc, info.getEdutype());
            pstmt.setInt(++scc, info.getDoctype());
            pstmt.setInt(++scc, info.getCerttype());
            pstmt.setString(++scc, currDate1());
            pstmt.setString(++scc, currDate1());
            pstmt.setString(++scc, info.getDescription2());
            pstmt.setString(++scc, info.getDescription3());
            pstmt.setString(++scc, info.getFilename());
            pstmt.setString(++scc, info.getLabel1());
            pstmt.setString(++scc, info.getLabel2());
            pstmt.setString(++scc, info.getLabel3());
            pstmt.setString(++scc, info.getLabel4());
            pstmt.setString(++scc, info.getLabel5());
            pstmt.setString(++scc, info.getLabel6());
            pstmt.setString(++scc, info.getLabel7());
            pstmt.setString(++scc, info.getLabel8());
            pstmt.setString(++scc, info.getLabel9());
            pstmt.setString(++scc, info.getLabel10());
            pstmt.setString(++scc, info.getLabel11());
            pstmt.setString(++scc, info.getLabel12());
            pstmt.setString(++scc, info.getLabel13());
            pstmt.setString(++scc, info.getLabel14());
            pstmt.setString(++scc, info.getLabel15());
            pstmt.setString(++scc, info.getLabel16());
            pstmt.setString(++scc, info.getLabel17());
            pstmt.setString(++scc, info.getLabel18());
            pstmt.setString(++scc, info.getLabel19());
            pstmt.setString(++scc, info.getLabel20());
            pstmt.setString(++scc, info.getLabel21());
            pstmt.setString(++scc, info.getLabel22());
            pstmt.setString(++scc, info.getLabel23());
            pstmt.setString(++scc, info.getLabel24());
            pstmt.setString(++scc, info.getLabel25());
            pstmt.setString(++scc, info.getLabel26());
            print(this, "createResumetemplate :: " + pstmt.toString());
            pstmt.executeUpdate();
            rs = pstmt.getGeneratedKeys();
            while (rs.next()) {
                resumetemplateId = rs.getInt(1);
            }
        } catch (Exception exception) {
            print(this, "createResumetemplate :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return resumetemplateId;
    }

    public int updateResumetemplate(ResumetemplateInfo info) {
        int cc = 0;
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("update t_resumetemplate set ");
            sb.append("s_name = ?, ");
            sb.append("i_clientid = ?, ");
            sb.append("s_description = ?, ");
            sb.append("s_description2 = ?, ");
            sb.append("s_description3 = ?, ");
            if (info.getFilename() != null && !info.getFilename().equals("")) {
                sb.append("s_filename = ?, ");
            }
            sb.append("s_wids = ?, ");
            sb.append("s_eids = ?, ");
            sb.append("s_dids = ?, ");
            sb.append("s_tids = ?, ");
            sb.append("i_temptype = ?, ");
            sb.append("i_exptype = ?, ");
            sb.append("i_edutype = ?, ");
            sb.append("i_doctype = ?, ");
            sb.append("i_certtype = ?, ");
            sb.append("s_label1 = ?, ");
            sb.append("s_label2 = ?, ");
            sb.append("s_label3 = ?, ");
            sb.append("s_label4 = ?, ");
            sb.append("s_label5 = ?, ");            
            sb.append("s_label6 = ?, ");
            sb.append("s_label7 = ?, ");
            sb.append("s_label8 = ?, ");
            sb.append("s_label9 = ?, ");
            sb.append("s_label10 = ?, ");            
            sb.append("s_label11 = ?, ");
            sb.append("s_label12 = ?, ");
            sb.append("s_label13 = ?, ");
            sb.append("s_label14 = ?, ");
            sb.append("s_label15 = ?, ");            
            sb.append("s_label16 = ?, ");
            sb.append("s_label17 = ?, ");
            sb.append("s_label18 = ?, ");
            sb.append("s_label19 = ?, ");
            sb.append("s_label20 = ?, ");            
            sb.append("s_label21 = ?, ");
            sb.append("s_label22 = ?, ");
            sb.append("s_label23 = ?, ");
            sb.append("s_label24 = ?, ");
            sb.append("s_label25 = ?, ");
            sb.append("s_label26 = ?, ");
            sb.append("i_status = ?, ");
            sb.append("i_userid = ?, ");
            sb.append("ts_moddate = ? ");
            sb.append("where i_resumetemplateid = ? ");
            String query = (sb.toString()).intern();
            sb.setLength(0);

            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            int scc = 0;
            pstmt.setString(++scc, info.getName());
            pstmt.setInt(++scc, info.getClientId());
            pstmt.setString(++scc, info.getDescription());
            pstmt.setString(++scc, info.getDescription2());
            pstmt.setString(++scc, info.getDescription3());
            if (info.getFilename() != null && !info.getFilename().equals("")) {
                pstmt.setString(++scc, info.getFilename());
            }
            pstmt.setString(++scc, info.getExpColumn());
            pstmt.setString(++scc, info.getEduColumn());
            pstmt.setString(++scc, info.getDocColumn());
            pstmt.setString(++scc, info.getTrainingColumn());
            pstmt.setInt(++scc, info.getTemptype());
            pstmt.setInt(++scc, info.getExptype());
            pstmt.setInt(++scc, info.getEdutype());
            pstmt.setInt(++scc, info.getDoctype());
            pstmt.setInt(++scc, info.getCerttype());
            pstmt.setString(++scc, info.getLabel1());
            pstmt.setString(++scc, info.getLabel2());
            pstmt.setString(++scc, info.getLabel3());
            pstmt.setString(++scc, info.getLabel4());
            pstmt.setString(++scc, info.getLabel5());
            pstmt.setString(++scc, info.getLabel6());
            pstmt.setString(++scc, info.getLabel7());
            pstmt.setString(++scc, info.getLabel8());
            pstmt.setString(++scc, info.getLabel9());
            pstmt.setString(++scc, info.getLabel10());
            pstmt.setString(++scc, info.getLabel11());
            pstmt.setString(++scc, info.getLabel12());
            pstmt.setString(++scc, info.getLabel13());
            pstmt.setString(++scc, info.getLabel14());
            pstmt.setString(++scc, info.getLabel15());
            pstmt.setString(++scc, info.getLabel16());
            pstmt.setString(++scc, info.getLabel17());
            pstmt.setString(++scc, info.getLabel18());
            pstmt.setString(++scc, info.getLabel19());
            pstmt.setString(++scc, info.getLabel20());
            pstmt.setString(++scc, info.getLabel21());
            pstmt.setString(++scc, info.getLabel22());
            pstmt.setString(++scc, info.getLabel23());
            pstmt.setString(++scc, info.getLabel24());
            pstmt.setString(++scc, info.getLabel25());
            pstmt.setString(++scc, info.getLabel26());
            pstmt.setInt(++scc, info.getStatus());
            pstmt.setInt(++scc, info.getUserId());
            pstmt.setString(++scc, currDate1());
            pstmt.setInt(++scc, info.getResumetemplateId());
            print(this, "updateResumetemplate :: " + pstmt.toString());
            cc = pstmt.executeUpdate();
        } catch (Exception exception) {
            print(this, "updateResumetemplate :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return cc;
    }

    public int checkDuplicacy(int resumetemplateId, String name, int clientId) {
        int ck = 0;
        StringBuilder sb = new StringBuilder();
        sb.append("select i_resumetemplateid FROM t_resumetemplate where s_name = ? and i_clientid = ? and i_status in (1, 2)");
        if (resumetemplateId > 0) {
            sb.append(" and i_resumetemplateid != ? ");
        }
        String query = (sb.toString()).intern();
        sb.setLength(0);
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            int scc = 0;
            pstmt.setString(++scc, name);
            pstmt.setInt(++scc, clientId);
            if (resumetemplateId > 0) {
                pstmt.setInt(++scc, resumetemplateId);
            }
            print(this, "checkDuplicacy :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ck = 1;
            }
        } catch (Exception exception) {
            print(this, "checkDuplicacy :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return ck;
    }

    public Collection getResumetemplates() {
        Collection coll = new LinkedList();
        coll.add(new ResumetemplateInfo(-1, "- Select -"));
        String xml_path = getMainPath("json_path");
        String str = readHTMLFile("resumetemplate.json", xml_path);
        JSONArray arr = new JSONArray(str);
        if (arr != null) {
            int len = arr.length();
            for (int i = 0; i < len; i++) {
                JSONObject jobj = arr.optJSONObject(i);
                if (jobj != null) {
                    coll.add(new ResumetemplateInfo(jobj.optInt("id"), jobj.optString("name")));
                }
            }
        }
        return coll;
    }

    public Collection getResumetemplatesforclientindex() {
        Collection coll = new LinkedList();
        String xml_path = getMainPath("json_path");
        String str = readHTMLFile("resumetemplate.json", xml_path);
        JSONArray arr = new JSONArray(str);
        if (arr != null) {
            int len = arr.length();
            for (int i = 0; i < len; i++) {
                JSONObject jobj = arr.optJSONObject(i);
                if (jobj != null) {
                    coll.add(new ResumetemplateInfo(jobj.optInt("id"), jobj.optString("name")));
                }
            }
        }
        return coll;
    }

    public int deleteResumetemplate(int resumetemplateId, int userId, int status, String ipAddrStr, String iplocal) {
        int cc = 0;
        try {
            conn = getConnection();
            StringBuilder sb = new StringBuilder();
            sb.append("update t_resumetemplate set ");
            sb.append("i_status = ?, ");
            sb.append("i_userid = ?, ");
            sb.append("ts_moddate = ? ");
            sb.append("where i_resumetemplateid = ? ");
            String query = (sb.toString()).intern();
            sb.setLength(0);

            pstmt = conn.prepareStatement(query);
            int scc = 0;
            if (status == 1) {
                pstmt.setInt(++scc, 2);
            } else {
                pstmt.setInt(++scc, 1);
            }
            pstmt.setInt(++scc, userId);
            pstmt.setString(++scc, currDate1());
            pstmt.setInt(++scc, resumetemplateId);

            cc = pstmt.executeUpdate();
        } catch (Exception exception) {
            print(this, "deleteResumetemplate :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        createHistoryAccess(null, userId, ipAddrStr, iplocal, "Data Deleted", 50, resumetemplateId);
        return cc;
    }

    public ArrayList getListForExcel(String search, int allclient, String permission, String cids, String assetids) {
        ArrayList list = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT t_resumetemplate.s_name, t_resumetemplate.i_status, t_client.s_name FROM t_resumetemplate ");
        sb.append("LEFT JOIN t_client ON (t_resumetemplate.i_clientid = t_client.i_clientid) ");
        sb.append("WHERE 0=0 ");
        if (search != null && !search.equals("")) {
            sb.append("AND (t_resumetemplate.s_name LIKE ? OR t_client.s_name LIKE ?) ");
        }
        if (allclient == 1) {
            sb.append(" AND t_client.i_clientid > 0 ");
        } else {
            if (!cids.equals("")) {
                sb.append("AND t_client.i_clientid IN (" + cids + ") ");
            } else {
                sb.append("AND t_client.i_clientid < 0 ");
            }
        }
        sb.append(" ORDER BY t_resumetemplate.i_status, t_client.s_name, t_resumetemplate.s_name ");
        String query = sb.toString().intern();
        sb.setLength(0);
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            int scc = 0;
            if (search != null && !search.equals("")) {
                pstmt.setString(++scc, "%" + (search) + "%");
                pstmt.setString(++scc, "%" + (search) + "%");
            }
            Common.print(this, "getListForExcel :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String name, clientname;
            int status;
            while (rs.next()) 
            {
                name = rs.getString(1) != null ? rs.getString(1) : "";
                status = rs.getInt(2);
                clientname = rs.getString(3) != null ? rs.getString(3) : "";
                list.add(new ResumetemplateInfo(0, name, status, clientname));
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

    public Collection getClients(String cids, int allclient, String permission) {
        Collection coll = new LinkedList();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT i_clientid, s_name FROM t_client where i_status = 1 ");
        if (allclient == 1) {
            sb.append("AND i_clientid > 0 ");
        } else {
            if (!cids.equals("")) {
                sb.append("AND i_clientid IN (" + cids + ") ");
            } else {
                sb.append("AND i_clientid < 0 ");
            }
        }

        sb.append("order by s_name");
        String query = (sb.toString()).intern();
        sb.setLength(0);
        coll.add(new ResumetemplateInfo(-1, " Select Client"));
        try
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            rs = pstmt.executeQuery();
            int refId;
            String refName;
            while (rs.next()) 
            {
                refId = rs.getInt(1);
                refName = rs.getString(2) != null ? rs.getString(2) : "";
                coll.add(new ResumetemplateInfo(refId, refName));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return coll;
    }
    public String checkToStr(String ids) {
        String str = "";
        if (ids.contains("1")) {
            if (str.equals("")) {
                str = "Position";
            } else {
                str += ", Position";
            }
        }
        if (ids.contains("2")) {
            if (str.equals("")) {
                str = "Department";
            } else {
                str += ", Department";
            }
        }
        if (ids.contains("3")) {
            if (str.equals("")) {
                str = "CompanyName";
            } else {
                str += ", CompanyName";
            }
        }
        if (ids.contains("4")) {
            if (str.equals("")) {
                str = "Asset Name";
            } else {
                str += ", Asset Name";
            }
        }
        if (ids.contains("5")) {
            if (str.equals("")) {
                str = "Asset Type";
            } else {
                str += ", Asset Type";
            }
        }
        if (ids.contains("6")) {
            if (str.equals("")) {
                str = "Start Date";
            } else {
                str += ", Start Date";
            }
        }
        if (ids.contains("7")) {
            if (str.equals("")) {
                str = "End Date";
            } else {
                str += ", End Date";
            }
        }
        if (ids.contains("1")) {
            if (str.equals("")) {
                str = "Kind";
            } else {
                str += ", Kind";
            }
        }
        
        if (ids.contains("2")) {
            if (str.equals("")) {
                str = "Degree";
            } else {
                str += ", Degree";
            }
        }
        if (ids.contains("3")) {
            if (str.equals("")) {
                str = "Institution/Location";
            } else {
                str += ", Institution/Location";
            }
        }
        if (ids.contains("4")) {
            if (str.equals("")) {
                str = "Field of Study";
            } else {
                str += ", Field of Study";
            }
        }
        if (ids.contains("5")) {
            if (str.equals("")) {
                str = "Start Date";
            } else {
                str += ", Start Date";
            }
        }
        if (ids.contains("6")) {
            if (str.equals("")) {
                str = "End Date";
            } else {
                str += ", End Date";
            }
        }
        if (ids.contains("1")) {
            if (str.equals("")) {
                str = "Document Name";
            } else {
                str += ", Document Name";
            }
        }
        if (ids.contains("2")) {
            if (str.equals("")) {
                str = "Number";
            } else {
                str += ", Number";
            }
        }
        if (ids.contains("3")) {
            if (str.equals("")) {
                str = "Place of Issue";
            } else {
                str += ", Place of Issue";
            }
        }
        if (ids.contains("4")) {
            if (str.equals("")) {
                str = "Issued By";
            } else {
                str += ", Issued By";
            }
        }
        if (ids.contains("5")) {
            if (str.equals("")) {
                str = "Issue Date";
            } else {
                str += ", Issue Date";
            }
        }
        if (ids.contains("6")) {
            if (str.equals("")) {
                str = "Expiry Date";
            } else {
                str += ", Expiry Date";
            }
        }
        return str;
    }

    public String getNameFromId(String ids) {
        String str = "";
        if (ids != null && !ids.equals("")) {
            if (ids.contains("1")) {
                if (str.equals("")) {
                    str = "Position";
                } else {
                    str += ", Position";
                }
            }
            if (ids.contains("2")) {
                if (str.equals("")) {
                    str = "Department";
                } else {
                    str += ", Department";
                }
            }
            if (ids.contains("3")) {
                if (str.equals("")) {
                    str = "Company Name";
                } else {
                    str += ", Company Name";
                }
            }
            if (ids.contains("4")) {
                if (str.equals("")) {
                    str = "Asset Name";
                } else {
                    str += ", Asset Name";
                }
            }
            if (ids.contains("5")) {
                if (str.equals("")) {
                    str = "Start Date";
                } else {
                    str += ", Start Date";
                }
            }
            if (ids.contains("6")) {
                if (str.equals("")) {
                    str = "End Date";
                } else {
                    str += ", End Date";
                }
            }
        }
        return str;
    }

    public String getEduFromId(String eids) {
        String str = "";
        if (eids.contains("1")) {
            if (str.equals("")) {
                str = "Kind";
            } else {
                str += ", Kind";
            }
        }
        if (eids.contains("2")) {
            if (str.equals("")) {
                str = "Degree";
            } else {
                str += ", Degree";
            }
        }
        if (eids.contains("3")) {
            if (str.equals("")) {
                str = "Institution/Location";
            } else {
                str += ", Institution/Location";
            }
        }
        if (eids.contains("4")) {
            if (str.equals("")) {
                str = "Field of Study";
            } else {
                str += ", Field of Study";
            }
        }
        if (eids.contains("5")) {
            if (str.equals("")) {
                str = "Start Date";
            } else {
                str += ", Start Date";
            }
        }
        if (eids.contains("6")) {
            if (str.equals("")) {
                str = "End Date";
            } else {
                str += ", End Date";
            }
        }
        return str;
    }

    public String getDocFromId(String dids) {
        String str = "";
        if (dids.contains("1")) {
            if (str.equals("")) {
                str = "Document Name";
            } else {
                str += ", Document Name";
            }
        }
        if (dids.contains("2")) {
            if (str.equals("")) {
                str = "Number";
            } else {
                str += ", Number";
            }
        }
        if (dids.contains("3")) {
            if (str.equals("")) {
                str = "Place of Issue";
            } else {
                str += ", Place of Issue";
            }
        }
        if (dids.contains("4")) {
            if (str.equals("")) {
                str = "Issued By";
            } else {
                str += ", Issued By";
            }
        }
        if (dids.contains("5")) {
            if (str.equals("")) {
                str = "Issue Date";
            } else {
                str += ", Issue Date";
            }
        }
        if (dids.contains("6")) {
            if (str.equals("")) {
                str = "Expiry Date";
            } else {
                str += ", Expiry Date";
            }
        }
        return str;
    }
    
    //Training cert
    public String getTrainingFromId(String tids) {
        String str = "";
        if (tids.contains("5")) {
            if (str.equals("")) {
                str = "Issue Date";
            } else {
                str += ", Issue Date";
            }
        }
        if (tids.contains("6")) {
            if (str.equals("")) {
                str = "Expiry Date";
            } else {
                str += ", Expiry Date";
            }
        }
        if (tids.contains("7")) {
            if (str.equals("")) {
                str = "Validity";
            } else {
                str += ", Validity";
            }
        }
        if (tids.contains("1")) {
            if (str.equals("")) {
                str = "CourseName";
            } else {
                str += ", Course Name";
            }
        }
        if (tids.contains("2")) {
            if (str.equals("")) {
                str = "Type";
            } else {
                str += ", Type";
            }
        }
        if (tids.contains("3")) {
            if (str.equals("")) {
                str = "Institution/Location";
            } else {
                str += ", Institution/Location";
            }
        }
        if (tids.contains("4")) {
            if (str.equals("")) {
                str = "Approved By";
            } else {
                str += ", Approved By";
            }
        }
        return str;
    }
    
    //Delete file
    public int deleteFile(int resumetemplateId, int userId) {
        int cc = 0;
        try
        {
            StringBuilder sb = new StringBuilder();
            conn = getConnection();
            String query = "SELECT s_filename FROM t_resumetemplate WHERE i_resumetemplateid = ?";
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, resumetemplateId);
            logger.info("get filename :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String filename ="";
            while (rs.next())
            {
                filename = rs.getString(1) != null ? rs.getString(1): "";  
            }
            rs.close();

            String path = getMainPath("add_resumetemplate_pdf");   
            String deletePath = path + filename;
            File file = new File(deletePath);
            if (file.exists()) 
            {
                file.delete();
            }

            sb.append("UPDATE t_resumetemplate set ");
            sb.append("s_filename = '', ");
            sb.append("i_userid = ?, ");
            sb.append("ts_moddate = ? ");
            sb.append("where i_resumetemplateid = ? ");
            query = (sb.toString()).intern();
            sb.setLength(0);

            pstmt = conn.prepareStatement(query);
            int scc = 0;
            pstmt.setInt(++scc, userId);
            pstmt.setString(++scc, currDate1());
            pstmt.setInt(++scc, resumetemplateId);
            print(this, "deleteFile :: " + pstmt.toString());
            cc = pstmt.executeUpdate();                    
        }
        catch (Exception exception) {
            print(this, "deleteFile :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return cc;
    }    
}
