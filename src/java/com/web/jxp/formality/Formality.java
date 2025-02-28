package com.web.jxp.formality;

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
public class Formality extends Base {

    private static final Logger logger = Logger.getLogger(Class.class.getName());
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    public ArrayList getFormalityByName(String search, int next, int count,int allclient, String permission, String cids, String assetids ) {
        ArrayList formalitys = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT t_formality.i_formalityid, t_formality.s_name, t_formality.i_status, t_client.s_name FROM t_formality ");
        sb.append("LEFT JOIN t_client ON (t_formality.i_clientid = t_client.i_clientid) ");
        sb.append("WHERE 0=0 ");
        if (search != null && !search.equals("")) {
            sb.append("AND (t_formality.s_name LIKE ? OR t_client.s_name LIKE ? ) ");
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
        sb.append(" ORDER BY t_formality.i_status,t_client.s_name, t_formality.s_name ");
        if (count > 0) {
            sb.append(" LIMIT ").append(next * count).append(", ").append(count);
        }
        String query = (sb.toString()).intern();
        sb.setLength(0);

        sb.append("SELECT COUNT(1) FROM t_formality ");
        sb.append("LEFT JOIN t_client ON (t_formality.i_clientid = t_client.i_clientid)");
        sb.append("WHERE 0=0 ");
        if (search != null && !search.equals("")) {
            sb.append("AND (t_formality.s_name LIKE ? OR t_client.s_name LIKE ?) ");
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
        String countquery = (sb.toString()).intern();
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
            logger.info("getFormalityByName :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String name, clientname;
            int formalityId, status;
            while (rs.next()) 
            {
                formalityId = rs.getInt(1);
                name = rs.getString(2) != null ? rs.getString(2) : "";
                status = rs.getInt(3);
                clientname = rs.getString(4) != null ? rs.getString(4) : "";
                formalitys.add(new FormalityInfo(formalityId, name, status, 0, clientname, 0));
            }
            rs.close();

            pstmt = conn.prepareStatement(countquery);
            scc = 0;
            if (search != null && !search.equals("")) {
                pstmt.setString(++scc, "%" + (search) + "%");
                pstmt.setString(++scc, "%" + (search) + "%");
            }
            print(this, "getFormalityByName :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                formalityId = rs.getInt(1);
                formalitys.add(new FormalityInfo(formalityId, "", 0, 0));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return formalitys;
    }

    public ArrayList getFinalRecord(ArrayList l, String colId, int tp) {
        ArrayList list = null;
        HashMap record = null;
        FormalityInfo info;
        int total = 0;
        if (l != null) {
            total = l.size();
        }
        try {
            if (total > 0) {
                record = new HashMap();
                int i;
                for (i = 0; i < total; i++) {
                    info = (FormalityInfo) l.get(i);
                    record.put(getInfoValue(info, colId), info);
                }
            }
            Map map = sortByName(record, tp);
            Iterator it = map.keySet().iterator();
            list = new ArrayList();
            FormalityInfo rInfo;
            while (it.hasNext()) {
                String key = (String) it.next();
                int i;
                for (i = 0; i < total; i++) {
                    rInfo = (FormalityInfo) l.get(i);
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
    public String getInfoValue(FormalityInfo info, String i) {
        String infoval = "";
        if (i != null && i.equals("1")) {
            infoval = info.getCandidatename() != null ? info.getCandidatename() : "";
        }
        if (i != null && i.equals("2")) {
            infoval = info.getName() != null ? info.getName() : "";
        }
        return infoval;
    }

    public FormalityInfo getFormalityDetailById(int formalityId) 
    {
        FormalityInfo info = null;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT s_name, i_clientid, s_description, i_status, i_temptype, s_formalityfile, ");
        sb.append("s_wids, s_eids, s_dids, s_tids, s_lids, s_vids, s_description2, s_description3, s_filename ");
        sb.append("FROM t_formality WHERE i_formalityid = ? ");
        String query = sb.toString().intern();
        sb.setLength(0);
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, formalityId);
            print(this, "getFormalityDetailById :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String name, description, formalityfile, workexp, edudetail,
                    docdetail, certificate, language, vaccine, description2, description3, filename;
            int status, candidateid, temptype;
            while (rs.next())
            {
                name = rs.getString(1) != null ? rs.getString(1) : "";
                candidateid = rs.getInt(2);
                description = rs.getString(3) != null ? rs.getString(3) : "";
                status = rs.getInt(4);
                temptype = rs.getInt(5);
                formalityfile = rs.getString(6) != null ? rs.getString(6) : "";
                workexp = rs.getString(7) != null ? rs.getString(7) : "";
                edudetail = rs.getString(8) != null ? rs.getString(8) : "";
                docdetail = rs.getString(9) != null ? rs.getString(9) : "";
                certificate = rs.getString(10) != null ? rs.getString(10) : "";
                language = rs.getString(11) != null ? rs.getString(11) : "";
                vaccine = rs.getString(12) != null ? rs.getString(12) : "";
                description2 = rs.getString(13) != null ? rs.getString(13) : "";
                description3 = rs.getString(14) != null ? rs.getString(14) : "";
                filename = rs.getString(15) != null ? rs.getString(15) : "";
                
                info = new FormalityInfo(formalityId, name, candidateid, description, status, temptype, 
                        formalityfile, workexp, edudetail, docdetail, certificate, language, vaccine,description2, description3, filename);
            }
        } catch (Exception exception) {
            print(this, "getFormalityDetailById :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return info;
    }

    public FormalityInfo getFormalityDetailByIdforDetail(int formalityId) {
        FormalityInfo info = null;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT t_formality.s_name, t_formality.i_status , t_client.s_name, t_formality.s_description, ");
        sb.append("t_formality.i_temptype, t_formality.s_formalityfile, t_formality.s_wids, t_formality.s_eids, ");
        sb.append("t_formality.s_dids, t_formality.s_tids, t_formality.s_lids, t_formality.s_vids, ");
        sb.append("t_formality.s_description2, t_formality.s_description3, t_formality.s_filename ");
        sb.append("FROM t_formality ");
        sb.append("LEFT JOIN t_client ON (t_client.i_clientid =  t_formality.i_clientid) ");
        sb.append("WHERE t_formality.i_formalityid = ? ");
        String query = sb.toString().intern();
        sb.setLength(0);
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, formalityId);
            print(this, "getFormalityDetailByIdforDetail :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String name, candidatename, description, filename, workexp, edudetail, docdetail, 
            certdetail, language, vaccine, description2, description3, filename2;
            int status = 0, templatetype;
            while (rs.next()) 
            {
                name = rs.getString(1) != null ? rs.getString(1) : "";
                status = rs.getInt(2);
                candidatename = rs.getString(3) != null ? rs.getString(3) : "";
                description = rs.getString(4) != null ? rs.getString(4) : "";
                templatetype = rs.getInt(5);
                filename = rs.getString(6) != null ? rs.getString(6) : "";
                workexp = rs.getString(7) != null ? rs.getString(7) : "";
                edudetail = rs.getString(8) != null ? rs.getString(8) : "";
                docdetail = rs.getString(9) != null ? rs.getString(9) : "";
                certdetail = rs.getString(10) != null ? rs.getString(10) : "";
                language = rs.getString(11) != null ? rs.getString(11) : "";
                vaccine = rs.getString(12) != null ? rs.getString(12) : "";
                description2 = rs.getString(13) != null ? rs.getString(13) : "";
                description3 = rs.getString(14) != null ? rs.getString(14) : "";
                filename2 = rs.getString(15) != null ? rs.getString(15) : "";
                
                if (description != null && !description.equals("")) 
                {
                    String add_path = getMainPath("add_formality_file");
                    description = readHTMLFile(description, add_path);
                }
                
                info = new FormalityInfo(formalityId, name, status, candidatename, description, templatetype, 
                        filename, workexp, edudetail, docdetail, certdetail, language, vaccine, description2, description3, filename2);
            }
            rs.close();
        } catch (Exception exception) {
            print(this, "getFormalityDetailByIdforDetail :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return info;
    }

    public int createFormality(FormalityInfo info)
    {
        int formalityId = 0;
        try
        {
            StringBuilder sb = new StringBuilder();
            sb.append("INSERT INTO t_formality ");
            sb.append("(s_name, i_status, i_userid,i_clientid, s_description, ts_regdate, ts_moddate, ");
            sb.append("s_formalityfile, i_temptype, s_wids, s_eids, s_dids, s_tids, s_lids, s_vids,  ");
            sb.append("s_description2, s_description3, s_filename ) ");
            sb.append("VALUES (?, ?, ?, ?, ?,   ?, ?, ?, ?, ?,   ?, ?, ?, ?, ?,   ?, ?, ? )");
            String query = (sb.toString()).intern();
            sb.setLength(0);

            conn = getConnection();
            pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            int scc = 0;
            pstmt.setString(++scc, (info.getName()));
            pstmt.setInt(++scc, info.getStatus());
            pstmt.setInt(++scc, info.getUserId());
            pstmt.setInt(++scc, info.getCandidateId());
            pstmt.setString(++scc, info.getDescription());
            pstmt.setString(++scc, currDate1());
            pstmt.setString(++scc, currDate1());
            pstmt.setString(++scc, info.getFormalityfilename());
            pstmt.setInt(++scc, info.getTemptype());
            pstmt.setString(++scc, info.getExpColumn());
            pstmt.setString(++scc, info.getEduColumn());
            pstmt.setString(++scc, info.getDocColumn());
            pstmt.setString(++scc, info.getTrainingColumn());
            pstmt.setString(++scc, info.getLanguageColumn());
            pstmt.setString(++scc, info.getVaccineColumn());
            pstmt.setString(++scc, info.getDescription2());
            pstmt.setString(++scc, info.getDescription3());
            pstmt.setString(++scc, info.getFilename());
            print(this, "createFormality :: " + pstmt.toString());
            pstmt.executeUpdate();
            rs = pstmt.getGeneratedKeys();
            while (rs.next()) {
                formalityId = rs.getInt(1);
            }
        } catch (Exception exception) {
            print(this, "createFormality :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return formalityId;
    }

    public int updateFormality(FormalityInfo info) 
    {
        int cc = 0;
        try
        {
            StringBuilder sb = new StringBuilder();
            sb.append("UPDATE t_formality SET ");
            sb.append("s_name = ?, ");
            sb.append("i_clientid = ?, ");
            sb.append("s_description = ?, ");
            if(info.getFormalityfilename()!= null && !info.getFormalityfilename().equals(""))
                sb.append("s_formalityfile = ?, ");
            if(info.getTemptype() == 1)
                sb.append("s_formalityfile = '', ");
            sb.append("s_description2 = ?, ");
            sb.append("s_description3 = ?, ");
            if (info.getFilename() != null && !info.getFilename().equals("")) 
            {
                sb.append("s_filename = ?, ");
            }
            if(info.getTemptype() == 2)
                sb.append("s_filename = '', ");
            sb.append("i_temptype = ?, ");
            sb.append("i_status = ?, "); 
            sb.append("i_userid = ?, ");
            sb.append("s_wids = ?, ");
            sb.append("s_eids = ?, ");
            sb.append("s_dids = ?, ");
            sb.append("s_tids = ?, ");            
            sb.append("s_lids = ?, ");            
            sb.append("s_vids = ?, ");            
            sb.append("ts_moddate = ? ");
            sb.append("WHERE i_formalityid = ? ");
            String query = (sb.toString()).intern();
            sb.setLength(0);

            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            int scc = 0;
            pstmt.setString(++scc, info.getName());
            pstmt.setInt(++scc, info.getCandidateId());
            pstmt.setString(++scc, info.getDescription());
            if(info.getFormalityfilename()!= null && !info.getFormalityfilename().equals(""))
                 pstmt.setString(++scc, info.getFormalityfilename());
            pstmt.setString(++scc, info.getDescription2());
            pstmt.setString(++scc, info.getDescription3());
            if (info.getFilename() != null && !info.getFilename().equals("")) {
                pstmt.setString(++scc, info.getFilename());
            }
            pstmt.setInt(++scc, info.getTemptype());
            pstmt.setInt(++scc, info.getStatus());
            pstmt.setInt(++scc, info.getUserId());
            pstmt.setString(++scc, info.getExpColumn());
            pstmt.setString(++scc, info.getEduColumn());
            pstmt.setString(++scc, info.getDocColumn());
            pstmt.setString(++scc, info.getTrainingColumn());
            pstmt.setString(++scc, info.getLanguageColumn());
            pstmt.setString(++scc, info.getVaccineColumn());
            pstmt.setString(++scc, currDate1());
            pstmt.setInt(++scc, info.getFormalityId());
            print(this, "updateFormality :: " + pstmt.toString());
            cc = pstmt.executeUpdate();
        } catch (Exception exception) {
            print(this, "updateFormality :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return cc;
    }

    public int checkDuplicacy(int formalityId, String name, int clientId) {
        int ck = 0;
        StringBuilder sb = new StringBuilder();
        sb.append("select i_formalityid FROM t_formality where s_name = ? and i_clientid = ? and i_status in (1, 2)");
        if (formalityId > 0) {
            sb.append(" and i_formalityid != ? ");
        }
        String query = (sb.toString()).intern();
        sb.setLength(0);
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            int scc = 0;
            pstmt.setString(++scc, name);
            pstmt.setInt(++scc, clientId);
            if (formalityId > 0) {
                pstmt.setInt(++scc, formalityId);
            }
            print(this, "checkDuplicacy :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            while (rs.next()) 
            {
                ck = 1;
            }
        } catch (Exception exception) {
            print(this, "checkDuplicacy :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return ck;
    }

    public int deleteFormality(int formalityId, int userId, int status, String ipAddrStr, String iplocal) {
        int cc = 0;
        try 
        {
            conn = getConnection();
            StringBuilder sb = new StringBuilder();
            sb.append("update t_formality set ");
            sb.append("i_status = ?, ");
            sb.append("i_userid = ?, ");
            sb.append("ts_moddate = ? ");
            sb.append("where i_formalityid = ? ");
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
            pstmt.setInt(++scc, formalityId);

            cc = pstmt.executeUpdate();
        } catch (Exception exception) {
            print(this, "deleteFormality :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        createHistoryAccess(null, userId, ipAddrStr, iplocal, "Data Deleted", 55, formalityId);
        return cc;
    }

    public ArrayList getListForExcel(String search, int allclient, String permission, String cids, String assetids) 
    {
        ArrayList list = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT t_formality.s_name, t_formality.i_status, t_client.s_name FROM t_formality ");
        sb.append("LEFT JOIN t_client on (t_formality.i_clientid = t_client.i_clientid) ");
        sb.append("WHERE 0 = 0 ");
        if (search != null && !search.equals("")) {
            sb.append("AND (t_formality.s_name LIKE ? OR t_client.s_name LIKE ?) ");
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
        sb.append(" ORDER BY t_formality.i_status, t_client.s_name, t_formality.s_name ");
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
                list.add(new FormalityInfo(0, name, status, clientname));
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
        coll.add(new FormalityInfo(-1, " Select Client"));
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
                refName = rs.getString(2) != null ? rs.getString(2): "";
                coll.add(new FormalityInfo(refId, refName));
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

    //Work exp
    public String getNameFromId(String wids) {
        String str = "";
        if (wids != null && !wids.equals("")) {
            if (wids.contains("1")) {
                if (str.equals("")) {
                    str = "Position";
                } else {
                    str += ", Position";
                }
            }
            if (wids.contains("2")) {
                if (str.equals("")) {
                    str = "Department";
                } else {
                    str += ", Department";
                }
            }
            if (wids.contains("3")) {
                if (str.equals("")) {
                    str = "Company Name";
                } else {
                    str += ", Company Name";
                }
            }
            if (wids.contains("4")) {
                if (str.equals("")) {
                    str = "Asset Name";
                } else {
                    str += ", Asset Name";
                }
            }
            if (wids.contains("5")) {
                if (str.equals("")) {
                    str = "Start Date";
                } else {
                    str += ", Start Date";
                }
            }
            if (wids.contains("6")) {
                if (str.equals("")) {
                    str = "End Date";
                } else {
                    str += ", End Date";
                }
            }
            if (wids.contains("7")) {
                if (str.equals("")) {
                    str = "Experience";
                } else {
                    str += ", Experience";
                }
            }
        }
        return str;
    }

    //for education
    public String getEduFromId(String eids) {
        String str = "";
        
        if (eids.contains("1")) {
            if (str.equals("")) {
                str = "Degree";
            } else {
                str += ", Degree";
            }
        }
         if (eids.contains("2")) {
            if (str.equals("")) {
                str = "End Date";
            } else {
                str += ", End Date";
            }
        }
        if (eids.contains("3")) {
            if (str.equals("")) {
                str = "Institution/Location";
            } else {
                str += ", Institution/Location";
            }
        }
        return str;
    }
    
    //for language
    public String getLanguageFromId(String lids) {
        String str = "";        
        if (lids.contains("1")) {
            if (str.equals("")) {
                str = "Name";
            } else {
                str += ", Name";
            }
        }
         if (lids.contains("2")) {
            if (str.equals("")) {
                str = "Proficiency";
            } else {
                str += ", Proficiency";
            }
        }
        return str;
    }

    //For documnet    
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
        if (tids.contains("1")) {
            if (str.equals("")) {
                str = "Course Name";
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
        if (tids.contains("7")) {
            if (str.equals("")) {
                str = "Certification No";
            } else {
                str += ", Certification No";
            }
        }
        return str;
    }
    
    // For vaccine
    public String getVaccineFromId(String vids)
    {
        String str = "";
        if (vids.contains("1")) {
            if (str.equals("")) {
                str = "Name";
            } else {
                str += ", Name";
            }
        }
        if (vids.contains("2")) {
            if (str.equals("")) {
                str = "Issue Date";
            } else {
                str += ", Issue Date";
            }
        }
        if (vids.contains("3")) {
            if (str.equals("")) {
                str = "Expiry Date";
            } else {
                str += ", Expiry Date";
            }
        }
        if (vids.contains("4")) {
            if (str.equals("")) {
                str = "Place Of Issue";
            } else {
                str += ", Place Of Issue";
            }
        }
        return str;
    }
    
    //Delete file
    public int deleteFile(int formalityId, int userId) {
        int cc = 0;
        try
        {
            StringBuilder sb = new StringBuilder();
            conn = getConnection();
            String query = "SELECT s_filename FROM t_formality WHERE i_formalityid = ?";
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, formalityId);
            logger.info("get filename :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String filename ="";
            while (rs.next())
            {
                filename = rs.getString(1) != null ? rs.getString(1): "";  
            }
            rs.close();

            String path = getMainPath("add_formality_file");   
            String deletePath = path + filename;
            File file = new File(deletePath);
            if (file.exists()) 
            {
                file.delete();
            }

            sb.append("UPDATE t_formality set ");
            sb.append("s_filename = '', ");
            sb.append("i_userid = ?, ");
            sb.append("ts_moddate = ? ");
            sb.append("where i_formalityid = ? ");
            query = (sb.toString()).intern();
            sb.setLength(0);

            pstmt = conn.prepareStatement(query);
            int scc = 0;
            pstmt.setInt(++scc, userId);
            pstmt.setString(++scc, currDate1());
            pstmt.setInt(++scc, formalityId);
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
