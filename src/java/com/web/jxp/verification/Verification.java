package com.web.jxp.verification;

import com.web.jxp.base.Base;
import com.web.jxp.common.Common;
import static com.web.jxp.common.Common.print;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

public class Verification extends Base {

    private static final Logger logger = Logger.getLogger(Class.class.getName());
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    public ArrayList getVerificationByName(String search, int statusIndex, int positionIndex, int tabIndex, int next, int count) {
        ArrayList list = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT c.i_candidateid, CONCAT_WS(' ', c.s_firstname, c.s_middlename, c.s_lastname), ");
        sb.append("t_position.s_name, c.i_vflag, DATE_FORMAT(c.ts_regdate,'%d-%b-%Y'), c.i_status, c.i_vflagav ");
        sb.append("FROM t_candidate AS c ");
        sb.append("LEFT JOIN t_position on (t_position.i_positionid = c.i_positionid) ");
        sb.append("where c.i_status = 1 AND c.i_draftflag =0 ");
        if (statusIndex > 0) {
            if (statusIndex == 1) {
                sb.append(" and c.i_vflag IN (1,2) ");
            } else {
                sb.append(" and c.i_vflag = ? ");
            }
        }
        if (positionIndex > 0) {
            sb.append(" and c.i_positionid = ? ");
        }
        if (search != null && !search.equals("")) {
            sb.append("and (CONCAT_WS(' ', c.s_firstname, c.s_middlename, c.s_lastname) like ? OR CONCAT_WS(' ', c.s_firstname, c.s_lastname) like ? OR c.i_candidateid like ? ");
            sb.append(" OR t_position.s_name like ? OR DATE_FORMAT(c.ts_regdate,'%d-%b-%Y') like ?) ");
        }
        if (tabIndex > 0) {
            sb.append(" and c.i_cflag" + tabIndex + " IN (0,3) ");
        }
        sb.append(" ORDER BY (CASE WHEN c.i_vflag =2 THEN 1 ELSE c.i_vflag END), c.ts_regdate ");
        if (count > 0) {
            sb.append(" limit ").append(next * count).append(", ").append(count);
        }
        String query = (sb.toString()).intern();
        sb.setLength(0);

        sb.append("SELECT count(1) FROM t_candidate as c ");
        sb.append("LEFT JOIN t_position on (t_position.i_positionid = c.i_positionid) ");
        sb.append("where c.i_status = 1 AND c.i_draftflag =0 ");
        if (statusIndex > 0) {
            if (statusIndex == 1) {
                sb.append(" and c.i_vflag IN (1,2) ");
            } else {
                sb.append(" and c.i_vflag = ? ");
            }
        }
        if (positionIndex > 0) {
            sb.append(" and c.i_positionid = ? ");
        }
        if (search != null && !search.equals("")) {
            sb.append("and (CONCAT_WS(' ', c.s_firstname, c.s_middlename, c.s_lastname) like ? OR CONCAT_WS(' ', c.s_firstname, c.s_lastname) like ? OR c.i_candidateid like ? ");
            sb.append(" OR t_position.s_name like ? OR DATE_FORMAT(c.ts_regdate,'%d-%b-%Y') like ?) ");
        }
        if (tabIndex > 0) {
            sb.append(" and c.i_cflag" + tabIndex + " IN (0,3) ");
        }
        String countquery = (sb.toString()).intern();
        sb.setLength(0);
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            int scc = 0;
            if (statusIndex > 2) {
                pstmt.setInt(++scc, statusIndex);
            }
            if (positionIndex > 0) {
                pstmt.setInt(++scc, positionIndex);
            }
            if (search != null && !search.equals("")) {
                pstmt.setString(++scc, "%" + (search) + "%");
                pstmt.setString(++scc, "%" + (search) + "%");
                pstmt.setString(++scc, search.replaceFirst("^0+(?!$)", ""));
                pstmt.setString(++scc, "%" + (search) + "%");
                pstmt.setString(++scc, "%" + (search) + "%");
            }
            logger.info("getVerificationByName :: " + pstmt.toString());

            rs = pstmt.executeQuery();
            String enrollon, name, position, Ids, flagstatus;
            int verificationId, status, flagav, flagstatusId;
            while (rs.next()) {
                verificationId = rs.getInt(1);
                name = rs.getString(2) != null ? rs.getString(2) : "";
                position = rs.getString(3) != null ? rs.getString(3) : "";
                flagstatusId = rs.getInt(4);
                flagstatus = getStbyId(flagstatusId);
                enrollon = rs.getString(5) != null ? rs.getString(5) : "";
                status = rs.getInt(6);
                flagav = rs.getInt(7);
                Ids = changeNum(verificationId, 6);
                list.add(new VerificationInfo(verificationId, Ids, enrollon, name, position, flagstatus, status, 0, flagav, flagstatusId));
            }
            rs.close();

            pstmt = conn.prepareStatement(countquery);
            scc = 0;
            if (statusIndex > 2) {
                pstmt.setInt(++scc, statusIndex);
            }
            if (positionIndex > 0) {
                pstmt.setInt(++scc, positionIndex);
            }
            if (search != null && !search.equals("")) {
                pstmt.setString(++scc, "%" + (search) + "%");
                pstmt.setString(++scc, "%" + (search) + "%");
                pstmt.setString(++scc, search.replaceFirst("^0+(?!$)", ""));
                pstmt.setString(++scc, "%" + (search) + "%");
                pstmt.setString(++scc, "%" + (search) + "%");
            }
            rs = pstmt.executeQuery();
            while (rs.next()) {
                verificationId = rs.getInt(1);
                list.add(new VerificationInfo(verificationId, "", "", "", "", "", 0, 0, 0, 0));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return list;
    }

    public ArrayList getFinalRecord(ArrayList l, String colId, int tp) {
        ArrayList list = null;
        HashMap record = null;
        VerificationInfo info;
        int total = 0;
        if (l != null) {
            total = l.size();
        }
        try {
            if (total > 0) {
                record = new HashMap();
                int i;
                for (i = 0; i < total; i++) {
                    info = (VerificationInfo) l.get(i);
                    record.put(getInfoValue(info, colId), info);
                }
            }
            Map map;
            if (colId.equals("1")) {
                map = sortById(record, tp);
            } 
            else if (colId.equals("2")) {
                map = sortByDate(record, tp, "dd-MMM-yyyy");
            } 
            else 
            {
                map = sortByName(record, tp);
            }
            Iterator it = map.keySet().iterator();
            list = new ArrayList();
            VerificationInfo rInfo;
            while (it.hasNext()) {
                String key = (String) it.next();
                int i;
                for (i = 0; i < total; i++) {
                    rInfo = (VerificationInfo) l.get(i);
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
    public String getInfoValue(VerificationInfo info, String i) {
        String infoval = "";
        if (i != null && i.equals("1")) {
            infoval = info.getVerificationId() + "";
        } else if (i != null && i.equals("2")) {
            infoval = info.getEnrollon() != null ? info.getEnrollon() : "";
        } else if (i != null && i.equals("3")) {
            infoval = info.getName() != null ? info.getName() : "";
        } else if (i != null && i.equals("4")) {
            infoval = info.getPosition() != null ? info.getPosition() : "";
        }
        return infoval;
    }

    public ArrayList getListForExcel(String search, int statusIndex, int positionIndex, int tabIndex) {
        ArrayList list = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT c.i_candidateid, CONCAT_WS(' ', c.s_firstname, c.s_middlename, c.s_lastname), ");
        sb.append("t_position.s_name, c.i_vflag, DATE_FORMAT(c.ts_regdate,'%d-%b-%Y'), c.i_status, c.i_vflagav ");
        sb.append("FROM t_candidate AS c ");
        sb.append("LEFT JOIN t_position on (t_position.i_positionid = c.i_positionid) ");
        sb.append("where c.i_status = 1 ");
        if (statusIndex > 0) {
            if (statusIndex == 1) {
                sb.append(" and c.i_vflag IN (1,2) ");
            } else {
                sb.append(" and c.i_vflag = ? ");
            }
        }
        if (positionIndex > 0) {
            sb.append(" and c.i_positionid = ? ");
        }
        if (search != null && !search.equals("")) {
            sb.append("and (CONCAT_WS(' ', c.s_firstname, c.s_middlename, c.s_lastname) like ? OR CONCAT_WS(' ', c.s_firstname, c.s_lastname) like ? OR c.i_candidateid like ? ");
            sb.append(" OR t_position.s_name like ? OR DATE_FORMAT(c.ts_regdate,'%d-%b-%Y') like ?) ");
        }
        if (tabIndex > 0) {
            sb.append(" and c.i_cflag" + tabIndex + " IN (0,3) ");
        }
        sb.append(" ORDER BY (CASE WHEN c.i_vflag =2 THEN 1 ELSE c.i_vflag END), c.ts_regdate ");

        String query = (sb.toString()).intern();
        sb.setLength(0);
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            int scc = 0;

            if (statusIndex > 2) {
                pstmt.setInt(++scc, statusIndex);
            }
            if (positionIndex > 0) {
                pstmt.setInt(++scc, positionIndex);
            }
            if (search != null && !search.equals("")) {
                pstmt.setString(++scc, "%" + (search) + "%");
                pstmt.setString(++scc, "%" + (search) + "%");
                pstmt.setString(++scc, search.replaceFirst("^0+(?!$)", ""));
                pstmt.setString(++scc, "%" + (search) + "%");
                pstmt.setString(++scc, "%" + (search) + "%");
            }
            Common.print(this, "getListForExcel :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String enrollon, name, position, Ids, flagstatus;
            int verificationId, status, flagav, flagstatusId;
            while (rs.next()) {
                verificationId = rs.getInt(1);
                name = rs.getString(2) != null ? rs.getString(2) : "";
                position = rs.getString(3) != null ? rs.getString(3) : "";
                flagstatusId = rs.getInt(4);
                flagstatus = getStbyId(flagstatusId);
                enrollon = rs.getString(5) != null ? rs.getString(5) : "";
                status = rs.getInt(6);
                flagav = rs.getInt(7);
                Ids = changeNum(verificationId, 6);
                list.add(new VerificationInfo(verificationId, Ids, enrollon, name, position, flagstatus, status, 0, flagav, flagstatusId));
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

    public int getStVal(int f1, int f2, int f3, int f4, int f5, int f6, int f7, int f8, int f9, int f10, int f11) {
        int st = 1;
        if (f1 == 2 && f2 == 2 && f3 == 2 && f4 == 2 && f5 == 2 && f6 == 2 && f7 == 2 && f9 == 2 && f10 == 2 && f11 == 2) {
            st = 4;
        } else if (f1 == 0 && f2 == 0 && f3 == 0 && f4 == 0 && f5 == 0 && f6 == 0 && f7 == 0 && f9 == 0 && f10 == 0 && f11 == 0) {
            st = 1;
        } else if ((f1 == 0 || f1 == 3) || (f2 == 0 || f2 == 3) || (f3 == 0 || f3 == 3) || (f4 == 0 || f4 == 3) || (f5 == 0 || f5 == 3) || (f6 == 0 || f6 == 3) || (f7 == 0 || f7 == 3) || (f9 == 0 || f9 == 3) || (f10 == 0 || f10 == 3) || (f11 == 0 || f11 == 3)) {
            st = 2;
        } else {
            st = 3;
        }
        return st;
    }

    public String getStbyId(int status) {
        String stval = "";
        switch (status) {
            case 1:
                stval = "Unverified";
                break;
            case 2:
                stval = "Unverified";
                break;
            case 3:
                stval = "Minimum Verified";
                break;
            case 4:
                stval = "Verified";
                break;
            default:
                break;
        }
        return stval;
    }

    public Collection getPositions() 
    {
        Collection list = new LinkedList();
        list.add(new VerificationInfo(-1, "Select Position-Rank"));
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT t_position.i_positionid, t_position.s_name, t_grade.s_name, t_assettype.s_name FROM t_position ");
        sb.append("LEFT JOIN t_grade ON (t_position.i_gradeid = t_grade.i_gradeid) ");
        sb.append("LEFT JOIN t_assettype ON (t_assettype.i_assettypeid = t_position.i_assettypeid) ");
        sb.append("WHERE t_position.i_status = 1 ");
        sb.append("ORDER BY t_assettype.s_name, t_position.s_name, t_grade.s_name ");
        String query = (sb.toString()).intern();
        sb.setLength(0);
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            rs = pstmt.executeQuery();
            int positionId;
            String pname, gname, aname;
            while (rs.next()) {
                positionId = rs.getInt(1);
                pname = rs.getString(2) != null ? rs.getString(2) : "";
                gname = rs.getString(3) != null ? rs.getString(3) : "";
                aname = rs.getString(4) != null ? rs.getString(4) : "";
                if (gname != null && !gname.equals(""))
                    pname += " | " + gname;
                if (aname != null && !aname.equals(""))
                    pname += " | " + aname;
                list.add(new VerificationInfo(positionId, pname));
            }
            rs.close();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return list;
    }

    public int updateverification(int tabno, int userId, int candidateId, int childId, int status, String filename, String authorityName) {
        int cc = 0;
        try {
            conn = getConnection();
            String currdate = currDate1(), query = "";
            int scc = 0;
            if (tabno == 1 || tabno == 3) {
                query = "INSERT INTO t_vstatus (i_candidateid, i_tabno, i_status, s_filename, s_authority, i_userid, ts_regdate, ts_moddate) VALUES (?,?,?,?,?,?,?,?) ";
                pstmt = conn.prepareStatement(query);
                scc = 0;
                pstmt.setInt(++scc, candidateId);
                pstmt.setInt(++scc, tabno);
                pstmt.setInt(++scc, status);
                pstmt.setString(++scc, filename);
                pstmt.setString(++scc, authorityName);
                pstmt.setInt(++scc, userId);
                pstmt.setString(++scc, currdate);
                pstmt.setString(++scc, currdate);
                logger.info("INSERT INTO t_vstatus tab 1  3 :: " + pstmt.toString());
                cc = pstmt.executeUpdate();
                if (cc > 0) {
                    if (tabno == 1) {
                        query = "UPDATE t_candidate SET i_cflag1 = ? WHERE i_candidateid = ?";
                    } else {
                        query = "UPDATE t_candidate SET i_cflag3 = ? WHERE i_candidateid = ?";
                    }
                    pstmt = conn.prepareStatement(query);
                    pstmt.setInt(1, status);
                    pstmt.setInt(2, candidateId);

                    pstmt.executeUpdate();
                }
                pstmt.close();

                String alertquery = "delete from t_alert where i_candidateid = ? and i_tabno = ?  ";
                pstmt = conn.prepareStatement(alertquery);
                pstmt.setInt(1, candidateId);
                pstmt.setInt(2, tabno);
                pstmt.executeUpdate();
            } else {
                query = "INSERT INTO t_vstatus (i_candidateid, i_tabno, i_childid, i_status, s_filename, s_authority, i_userid, ts_regdate, ts_moddate) VALUES (?,?,?,?,?,?,?,?,?) ";
                pstmt = conn.prepareStatement(query);
                scc = 0;
                pstmt.setInt(++scc, candidateId);
                pstmt.setInt(++scc, tabno);
                pstmt.setInt(++scc, childId);
                pstmt.setInt(++scc, status);
                pstmt.setString(++scc, filename);
                pstmt.setString(++scc, authorityName);
                pstmt.setInt(++scc, userId);
                pstmt.setString(++scc, currdate);
                pstmt.setString(++scc, currdate);
                cc = pstmt.executeUpdate();
                logger.info("INSERT INTO t_vstatus Others:: " + pstmt.toString());
                if (cc > 0) {
                    String tablaname = "", child_id = "", cflagname = "";
                    if (tabno == 2) {
                        tablaname = "t_candlang";
                        child_id = "i_candlangid";
                        cflagname = "i_cflag2";
                    } else if (tabno == 4) {
                        tablaname = "t_vbd";
                        child_id = "i_vbdid";
                        cflagname = "i_cflag4";
                    } else if (tabno == 5) {
                        tablaname = "t_workexperience";
                        child_id = "i_experienceid";
                        cflagname = "i_cflag5";
                    } else if (tabno == 6) {
                        tablaname = "t_eduqual";
                        child_id = "i_eduqulid";
                        cflagname = "i_cflag6";
                    } else if (tabno == 7) {
                        tablaname = "t_trainingandcert";
                        child_id = "i_tcid";
                        cflagname = "i_cflag7";
                    } else if (tabno == 9) {
                        tablaname = "t_bankdetail";
                        child_id = "i_bankdetid";
                        cflagname = "i_cflag9";
                    } else if (tabno == 10) {
                        tablaname = "t_govdoc";
                        child_id = "i_govid";
                        cflagname = "i_cflag10";
                    } else if (tabno == 11) {
                        tablaname = "t_nominee";
                        child_id = "i_nomineeid";
                        cflagname = "i_cflag11";
                    }
                    String updatechild = "UPDATE " + tablaname + " SET i_vstatus = ? WHERE " + child_id + " = ?";
                    pstmt = conn.prepareStatement(updatechild);
                    pstmt.setInt(1, status);
                    pstmt.setInt(2, childId);
                    pstmt.executeUpdate();

                    logger.info("UPDATE " + pstmt.toString());

                    String countquery = "SELECT t1.ct, t2.ct, t3.ct FROM (SELECT count(1) as ct FROM " + tablaname + " WHERE i_candidateid = ? AND i_status = 1) AS t1, "
                            + "(SELECT count(1) AS ct FROM " + tablaname + " WHERE i_candidateid = ? AND i_status = 1 AND i_vstatus = 2) AS t2, "
                            + "(SELECT count(1) AS ct FROM " + tablaname + " WHERE i_candidateid = ? AND i_status = 1 AND i_vstatus = 1) AS t3";
                    pstmt = conn.prepareStatement(countquery);
                    pstmt.setInt(1, candidateId);
                    pstmt.setInt(2, candidateId);
                    pstmt.setInt(3, candidateId);
                    rs = pstmt.executeQuery();
                    logger.info("SELECT t1.ct, t2.ct :: " + pstmt.toString());
                    int totalcount = 0, countst2 = 0, countst1 = 0;
                    while (rs.next()) {
                        totalcount = rs.getInt(1);
                        countst2 = rs.getInt(2);
                        countst1 = rs.getInt(3);
                    }
                    rs.close();

                    int childst = 0;
                    if (totalcount == countst2) {
                        childst = 2;
                    } else if (countst2 + countst1 == 0) {
                        childst = 0;
                    } else if (totalcount > (countst2 + countst1)) {
                        childst = 3;
                    } else {
                        childst = 1;
                    }

                    query = "UPDATE t_candidate SET " + cflagname + " = ? WHERE i_candidateid = ?";
                    pstmt = conn.prepareStatement(query);
                    pstmt.setInt(1, childst);
                    pstmt.setInt(2, candidateId);
                    logger.info("update t_candidate :: " + pstmt.toString());
                    pstmt.executeUpdate();

                    if (childst == 1 || childst == 2) {

                        String alertquery = "delete from t_alert where i_candidateid = ? and i_tabno = ?  ";
                        pstmt = conn.prepareStatement(alertquery);
                        pstmt.setInt(1, candidateId);
                        pstmt.setInt(2, tabno);
                        pstmt.executeUpdate();
                    }

                }
            }
            query = "SELECT i_cflag1, i_cflag2, i_cflag3, i_cflag4, i_cflag5, i_cflag6, i_cflag7, i_cflag8, i_cflag9, i_cflag10, i_cflag11, i_vflag from t_candidate where i_candidateid = ?";
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, candidateId);
            rs = pstmt.executeQuery();
            int f1 = 0, f2 = 0, f3 = 0, f4 = 0, f5 = 0, f6 = 0, f7 = 0, f8 = 0, f9 = 0, f10 = 0, f11 = 0 ,vf = 0;
            while (rs.next()) {
                f1 = rs.getInt(1);
                f2 = rs.getInt(2);
                f3 = rs.getInt(3);
                f4 = rs.getInt(4);
                f5 = rs.getInt(5);
                f6 = rs.getInt(6);
                f7 = rs.getInt(7);
                f8 = rs.getInt(8);
                f9 = rs.getInt(9);
                f10 = rs.getInt(10);
                f11 = rs.getInt(11);
                vf = rs.getInt(12);
            }
            rs.close();
            int vflag = getStVal(f1, f2, f3, f4, f5, f6, f7, f8, f9, f10, f11);
            if (vf == 3 || vf == 4) {

                if (vflag == 4) {
                    query = "update t_candidate set i_vflag = ?, i_vflagav = 0, i_userid = ?, ts_moddate = ?, d_vdate = ?  where i_candidateid = ?";
                } else {
                    query = "update t_candidate set i_vflagav = 0, i_userid = ?, ts_moddate = ?, d_vdate = ? where i_candidateid = ?";
                }
                pstmt = conn.prepareStatement(query);
                scc = 0;
                if (vflag == 4) {
                    pstmt.setInt(++scc, vflag);
                }
                pstmt.setInt(++scc, userId);
                pstmt.setString(++scc, currdate);
                pstmt.setString(++scc, currDate());
                pstmt.setInt(++scc, candidateId);
                pstmt.executeUpdate();
            } else {
                if (vflag == 4 || vflag == 3) {
                    query = "update t_candidate set i_vflag = ?, i_vflagav = 0, i_userid = ?, ts_moddate = ?, d_vdate = ? where i_candidateid = ?";
                } else {
                    query = "update t_candidate set i_vflag = ?, i_userid = ?, ts_moddate = ?, d_vdate = ? where i_candidateid = ?";
                }
                pstmt = conn.prepareStatement(query);
                pstmt.setInt(1, vflag);
                pstmt.setInt(2, userId);
                pstmt.setString(3, currdate);
                pstmt.setString(4, currDate());
                pstmt.setInt(5, candidateId);

                pstmt.executeUpdate();
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return cc;
    }

    public String getChecklist(int tabno) {
        String str = "";
        String query = ("SELECT s_displaynotes, i_minverification FROM t_verificationcheckpoint where i_tab = ? order by i_tab, s_displaynotes");
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, tabno);
            logger.info("getChecklist :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            StringBuilder sb1 = new StringBuilder();
            StringBuilder sb2 = new StringBuilder();
            while (rs.next()) {
                String remarks = rs.getString(1) != null ? rs.getString(1).replaceAll("\n", "<br/>") : "";
                int minflag = rs.getInt(2);
                if (minflag == 1) {
                    sb1.append("<br/>" + remarks != null ? remarks : "");
                } else {
                    sb2.append("<br/>" + remarks != null ? remarks : "");
                }
            }
            rs.close();
            String s1 = sb1.toString();
            sb1.setLength(0);
            String s2 = sb2.toString();
            sb2.setLength(0);
            if (!s1.equals("")) {
                str = "<b>Minimum Verification:</b>" + s1;
            }
            if (!s2.equals("")) {
                str += "<br/><br/><b>Complete Verification:</b>" + s2;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return str;
    }

    public ArrayList getAddedList(int candidateId, int tabno, int childId) {
        ArrayList list = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("select t_vstatus.i_vstatusid, t_vstatus.s_filename, DATE_FORMAT(t_vstatus.ts_regdate, '%d-%b-%Y %H:%i'), ");
        sb.append("t_vstatus.s_authority, t_vstatus.i_status, t_userlogin.s_name ");
        sb.append("FROM t_vstatus left join t_userlogin on (t_userlogin.i_userid = t_vstatus.i_userid) ");
        sb.append("where t_vstatus.i_candidateid = ? and i_tabno = ? ");
        if (childId > 0) {
            sb.append(" and t_vstatus.i_childid = ? ");
        }
        sb.append(" order by t_vstatus.ts_regdate desc ");
        String query = (sb.toString()).intern();
        sb.setLength(0);
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, candidateId);
            pstmt.setInt(2, tabno);
            if (childId > 0) {
                pstmt.setInt(3, childId);
            }
            logger.info("getAddedList :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String filename, date, authority, name;
            int vstatusId, status;
            while (rs.next()) 
            {
                vstatusId = rs.getInt(1);
                filename = rs.getString(2) != null ? rs.getString(2) : "";
                date = rs.getString(3) != null ? rs.getString(3) : "";
                authority = rs.getString(4) != null ? rs.getString(4) : "";
                status = rs.getInt(5);
                name = rs.getString(6) != null ? rs.getString(6) : "";
                list.add(new VerificationInfo(vstatusId, filename, date, authority, status, name));
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return list;
    }

    public String getAuthority(String val) {
        String s = "";
        String query = ("Select distinct s_authority from t_vstatus where s_authority like ? order by s_authority");
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, (val) + "%");
            logger.info("getAuthority :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            JSONArray jsonArrayParent = new JSONArray();
            while (rs.next()) {
                JSONObject jsonObjectRow = new JSONObject();
                jsonObjectRow.put("value", rs.getString(1));
                jsonObjectRow.put("label", rs.getString(1));
                jsonArrayParent.put(jsonObjectRow);
            }
            s = jsonArrayParent.toString();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return s;
    }

    public String getFilename(int candidateId, int tabNo, int childNo) {
        String s = "", filename = "", tablaname = "", childid = "";
        if (tabNo == 1) {
            filename = "s_name";
            tablaname = "t_candidatefiles";
            childid = " AND i_candidateid =" + candidateId;
        } else if (tabNo == 2) {
            filename = "s_filename";
            tablaname = "t_candlang";
            childid = " AND i_candlangid =" + childNo;
        } 
        else if (tabNo == 3) {
            filename = "s_filename";
            tablaname = "t_healthdeclaration";
            childid = " AND i_candidateid =" + candidateId;
        }
        else if (tabNo == 4) {
            filename = "s_filename";
            tablaname = "t_vbd";
            childid = "AND i_vbdid =" + childNo;
        } else if (tabNo == 5) {
            filename = "s_filenamework, s_filenameexp";
            tablaname = "t_workexperience";
            childid = "AND i_experienceid =" + childNo;
        } else if (tabNo == 6) {
            filename = "s_filename";
            tablaname = "t_eduqual";
            childid = "AND i_eduqulid =" + childNo;
        } else if (tabNo == 7) {
            filename = "s_filename";
            tablaname = "t_trainingandcert";
            childid = "AND i_tcid =" + childNo;
        } else if (tabNo == 9) {
            filename = "s_filename";
            tablaname = "t_bankdetail";
            childid = "AND i_bankdetid =" + childNo;
        } else if (tabNo == 10) {
            filename = "s_name";
            tablaname = "t_documentfiles";
            childid = "AND i_govid =" + childNo;
        }
        if (!tablaname.equals("")) {
            String query = ("SELECT " + filename + " FROM " + tablaname + " WHERE i_status =1 " + childid);
            if (tabNo == 1) {
                query += " ORDER BY i_fileid DESC LIMIT 0, 1";
            } else {
                query += " ORDER BY ts_regdate DESC LIMIT 0, 1";
            }
            try {
                conn = getConnection();
                pstmt = conn.prepareStatement(query);
                logger.info("getFilename :: " + pstmt.toString());
                rs = pstmt.executeQuery();
                while (rs.next()) 
                {
                    s = rs.getString(1) != null ? rs.getString(1) : "";
                    if (tabNo == 5 && s != null && !s.equals("")) {
                        s = rs.getString(2) != null ? rs.getString(2) : "";
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                close(conn, pstmt, rs);
            }
        }
        return s;
    }

    public String getStatusById(int key) {
        String s = "";
        if (key == 1) {
            s = "Minimum Verified";
        } else if (key == 2) {
            s = "Verified";
        }
        return s;
    }

    public String getVerifiedImage(int key) {
        String s = "";
        if (key == 0) {
            s = "inactive_verified.png";
        } else if (key == 1) {
            s = "pending_verified.png";
        } else if (key == 2) {
            s = "active_verified.png";
        }
        return s;
    }
    public int updateverification8910(int tabno, int userId, int candidateId)
    {
        int cc = 0;
        try {
            conn = getConnection();
            String currdate = currDate1(), query = "";
            int scc = 0;
            if (tabno == 4 || tabno == 9 || tabno == 10 || tabno == 11) 
            {
                query = "UPDATE t_candidate SET i_cflag"+tabno+" = ? WHERE i_candidateid = ?";
                pstmt = conn.prepareStatement(query);
                pstmt.setInt(1, 2);
                pstmt.setInt(2, candidateId);
                print(this,"UPDATE t_candidate :: " + pstmt.toString());
                pstmt.executeUpdate();
                pstmt.close();
                
                query = "SELECT i_cflag1, i_cflag2, i_cflag3, i_cflag4, i_cflag5, i_cflag6, i_cflag7, i_cflag8, i_cflag9, i_cflag10, i_cflag11, i_vflag from t_candidate where i_candidateid = ?";
                pstmt = conn.prepareStatement(query);
                pstmt.setInt(1, candidateId);
                rs = pstmt.executeQuery();
                int f1 = 0, f2 = 0, f3 = 0, f4 = 0, f5 = 0, f6 = 0, f7 = 0, f8 = 0, f9 = 0, f10 = 0, f11 = 0, vf = 0;
                while (rs.next()) {
                    f1 = rs.getInt(1);
                    f2 = rs.getInt(2);
                    f3 = rs.getInt(3);
                    f4 = rs.getInt(4);
                    f5 = rs.getInt(5);
                    f6 = rs.getInt(6);
                    f7 = rs.getInt(7);
                    f8 = rs.getInt(8);
                    f9 = rs.getInt(9);
                    f10 = rs.getInt(10);
                    f11 = rs.getInt(11);
                    vf = rs.getInt(12);
                }
                rs.close();
                int vflag = getStVal(f1, f2, f3, f4, f5, f6, f7, f8, f9, f10, f11);
                if (vf == 3 || vf == 4) 
                {
                    if (vflag == 4) {
                        query = "update t_candidate set i_vflag = ?, i_vflagav = 0, i_userid = ?, ts_moddate = ?, d_vdate = ?  where i_candidateid = ?";
                    } else {
                        query = "update t_candidate set i_vflagav = 0, i_userid = ?, ts_moddate = ?, d_vdate = ? where i_candidateid = ?";
                    }
                    pstmt = conn.prepareStatement(query);
                    scc = 0;
                    if (vflag == 4) {
                        pstmt.setInt(++scc, vflag);
                    }
                    pstmt.setInt(++scc, userId);
                    pstmt.setString(++scc, currdate);
                    pstmt.setString(++scc, currDate());
                    pstmt.setInt(++scc, candidateId);
                    print(this,"UPDATE t_candidate2 :: " + pstmt.toString());
                    pstmt.executeUpdate();
                } 
                else 
                {
                    if (vflag == 4 || vflag == 3) {
                        query = "update t_candidate set i_vflag = ?, i_vflagav = 0, i_userid = ?, ts_moddate = ?, d_vdate = ? where i_candidateid = ?";
                    } else {
                        query = "update t_candidate set i_vflag = ?, i_userid = ?, ts_moddate = ?, d_vdate = ? where i_candidateid = ?";
                    }
                    pstmt = conn.prepareStatement(query);
                    pstmt.setInt(1, vflag);
                    pstmt.setInt(2, userId);
                    pstmt.setString(3, currdate);
                    pstmt.setString(4, currDate());
                    pstmt.setInt(5, candidateId);
                    print(this,"UPDATE t_candidate3 :: " + pstmt.toString());
                    pstmt.executeUpdate();
                }
            }
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        } 
        finally 
        {
            close(conn, pstmt, rs);
        }
        return cc;
    }    
    
    //For alertdelete
    public int deleteAlert(int alertId)
    {
        int ck = 0;
        StringBuilder sb = new StringBuilder();
        sb.append("DELETE FROM t_alert WHERE i_alertid = ? ");        
        String query = (sb.toString()).intern();
        sb.setLength(0);
        try
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            if(alertId > 0){
                pstmt.setInt(1, alertId);
            }
            print(this,"deleteAlert :: " + pstmt.toString());
            ck = pstmt.executeUpdate();
        }
        catch (Exception exception)
        {
            print(this,"deleteAlert :: " + exception.getMessage());
        }
        finally
        {
            close(conn, pstmt, rs);
        }
        return ck;
    }
}
