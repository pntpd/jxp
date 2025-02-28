package com.web.jxp.crewrotation;

import com.web.jxp.base.Base;
import static com.web.jxp.common.Common.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.TimeZone;
import java.util.logging.Logger;

public class Crewrotation extends Base {

    private static final Logger logger = Logger.getLogger(Class.class.getName());
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    public ArrayList getClientSelectByName(String search, int statusIndex, int next, int count,
            int clientIdIndex, int assetIdIndex, int countryId, int allclient, String permission, String cids, String assetids) {

        ArrayList list = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT t_client.s_name, t_clientasset.s_name, t_country.s_name, SUM(IF(i_status1 = 1 && i_active = 1, 1, 0)), SUM(IF(i_status2 = 1 && i_active = 1, 1, 0)) ");
        sb.append(" AS ct1,   SUM(IF(i_status2 = 2 && i_active = 1, 1, 0)),   SUM(IF(i_status2 = 3 && i_active = 1, 1, 0)) AS ct2  ,t_crewrotation.i_clientid , t_crewrotation.i_clientassetid ");
        sb.append("FROM t_crewrotation ");
        sb.append("LEFT JOIN t_client ON (t_client.i_clientid = t_crewrotation.i_clientid) ");
        sb.append("LEFT JOIN t_clientasset ON (t_clientasset.i_clientassetid = t_crewrotation.i_clientassetid) ");
        sb.append("LEFT JOIN t_country ON (t_country.i_countryid = t_clientasset.i_countryid) WHERE 0 = 0  AND t_crewrotation.i_active = 1 AND t_client.i_status = 1 AND t_clientasset.i_status = 1 ");
        if (allclient == 1) {
            sb.append(" AND t_client.i_clientid > 0 ");
        } else {
            if (!cids.equals("")) {
                sb.append("AND t_client.i_clientid IN (" + cids + ") ");
            } else {
                sb.append("AND t_client.i_clientid < 0 ");
            }
            if (!assetids.equals("")) {
                sb.append("AND t_clientasset.i_clientassetid IN (" + assetids + ") ");
            }
        }
        if (statusIndex > 0) {
            sb.append("AND t_client.i_status = ? ");
        }
        if (clientIdIndex > 0) {
            sb.append("AND t_crewrotation.i_clientid = ? ");
        }
        if (assetIdIndex > 0) {
            sb.append("AND t_crewrotation.i_clientassetid = ? ");
        }
        if (countryId > 0) {
            sb.append("AND t_country.i_countryid = ? ");
        }

        if (search != null && !search.equals("")) {
            sb.append(" AND (t_client.s_name LIKE ? OR t_clientasset.s_name LIKE ? OR t_country.s_name LIKE ?) ");
        }
        sb.append(" GROUP BY t_crewrotation.i_clientid, t_crewrotation.i_clientassetid ");
        if (count > 0) {
            sb.append(" LIMIT ").append(next * count).append(", ").append(count);
        }
        String query = (sb.toString()).intern();
        sb.setLength(0);

        sb.append("SELECT COUNT(1) FROM (SELECT COUNT(1) FROM t_crewrotation ");
        sb.append("LEFT JOIN t_client ON (t_client.i_clientid = t_crewrotation.i_clientid) ");
        sb.append("LEFT JOIN t_clientasset ON (t_clientasset.i_clientassetid = t_crewrotation.i_clientassetid)");
        sb.append("LEFT JOIN t_country ON (t_country.i_countryid = t_clientasset.i_countryid) ");
        sb.append("WHERE 0 = 0  AND t_crewrotation.i_active = 1 AND t_client.i_status = 1 AND t_clientasset.i_status = 1 ");

        if (allclient == 1) {
            sb.append(" AND t_client.i_clientid > 0 ");
        } else {
            if (!cids.equals("")) {
                sb.append("AND t_client.i_clientid IN (" + cids + ") ");
            } else {
                sb.append("AND t_client.i_clientid < 0 ");
            }
            if (!assetids.equals("")) {
                sb.append("AND t_clientasset.i_clientassetid IN (" + assetids + ") ");
            }
        }
        if (statusIndex > 0) {
            sb.append("AND t_client.i_status = ? ");
        }
        if (clientIdIndex > 0) {
            sb.append("AND t_crewrotation.i_clientid = ? ");
        }
        if (assetIdIndex > 0) {
            sb.append("AND t_crewrotation.i_clientassetid = ? ");
        }
        if (countryId > 0) {
            sb.append("AND t_country.i_countryid = ? ");
        }

        if (search != null && !search.equals("")) {
            sb.append(" AND (t_client.s_name LIKE ? OR t_clientasset.s_name LIKE ? OR t_country.s_name LIKE ? ) ");
        }
        sb.append(" GROUP BY t_crewrotation.i_clientid, t_crewrotation.i_clientassetid) AS t ");
        String countquery = (sb.toString()).intern();
        sb.setLength(0);
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            int scc = 0;
            if (statusIndex > 0) {
                pstmt.setInt(++scc, statusIndex);
            }
            if (clientIdIndex > 0) {
                pstmt.setInt(++scc, clientIdIndex);
            }
            if (assetIdIndex > 0) {
                pstmt.setInt(++scc, assetIdIndex);
            }
            if (countryId > 0) {
                pstmt.setInt(++scc, countryId);
            }

            if (search != null && !search.equals("")) {
                pstmt.setString(++scc, search.replaceFirst("^0+(?!$)", ""));
                pstmt.setString(++scc, "%" + (search) + "%");
                pstmt.setString(++scc, "%" + (search) + "%");
            }
            logger.info("getClientSelectByName:: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String clientName, Country, clientAsset;
            int crewrotationId, status1, normal, delayed, extended, clientId, clientassetId;
            while (rs.next()) {
                clientName = rs.getString(1) != null ? rs.getString(1) : "";
                clientAsset = rs.getString(2) != null ? rs.getString(2) : "";
                Country = rs.getString(3) != null ? rs.getString(3) : "";
                status1 = rs.getInt(4);
                normal = rs.getInt(5);
                delayed = rs.getInt(6);
                extended = rs.getInt(7);
                clientId = rs.getInt(8);
                clientassetId = rs.getInt(9);
                list.add(new CrewrotationInfo(clientName, clientAsset, Country, status1, normal, delayed, extended, clientId, clientassetId, 0, 0));
            }
            rs.close();

            pstmt = conn.prepareStatement(countquery);
            scc = 0;
            if (statusIndex > 0) {
                pstmt.setInt(++scc, statusIndex);
            }

            if (clientIdIndex > 0) {
                pstmt.setInt(++scc, clientIdIndex);
            }
            if (assetIdIndex > 0) {
                pstmt.setInt(++scc, assetIdIndex);
            }
            if (countryId > 0) {
                pstmt.setInt(++scc, countryId);
            }
            if (search != null && !search.equals("")) {
                pstmt.setString(++scc, search.replaceFirst("^0+(?!$)", ""));
                pstmt.setString(++scc, "%" + (search) + "%");
                pstmt.setString(++scc, "%" + (search) + "%");
            }
            print(this, "getClientSelectByName :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                crewrotationId = rs.getInt(1);
                list.add(new CrewrotationInfo(crewrotationId, 0, 0, "", "", "", "", "",
                        "", "", "", 0, 0, 0, "", 0, "", "", "", "", 0, 0, 0, 0, 0, 0, "", 0, 0,"",0));
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
        CrewrotationInfo info;
        int total = 0;
        if (l != null) {
            total = l.size();
        }

        try {
            if (total > 0) {
                record = new HashMap();
                int i;
                for (i = 0; i < total; i++) {
                    info = (CrewrotationInfo) l.get(i);
                    record.put(getInfoValue(info, colId), info);
                }
            }

            Map map = sortByName(record, tp);

            Iterator it = map.keySet().iterator();
            list = new ArrayList();
            CrewrotationInfo rInfo;
            while (it.hasNext()) {
                String key = (String) it.next();
                int i;
                for (i = 0; i < total; i++) {
                    rInfo = (CrewrotationInfo) l.get(i);
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

    public String getInfoValue(CrewrotationInfo info, String i) {
        String infoval = "";
        if (i != null && i.equals("1")) {
            infoval = info.getClientName() != null ? info.getClientName() : "";
        } else if (i != null && i.equals("2")) {
            infoval = info.getClientAsset() != null ? info.getClientAsset() : "";
        } else if (i != null && i.equals("3")) {
            infoval = info.getCountry() != null ? info.getCountry() : "";
        }
        return infoval;
    }

    public CrewrotationInfo getByclientandAssetName(String search, int clientIdIndex, int assetIdIndex, int countryId) {

        CrewrotationInfo info = null;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT t_client.s_name, t_clientasset.s_name, t_country.s_name, SUM(IF(i_status1 = 1 && i_active = 1, 1, 0)), SUM(IF(i_status2 = 1 && i_active = 1, 1, 0)) ");
        sb.append("AS ct1, SUM(IF(i_status2 = 2 && i_active = 1, 1, 0)), SUM(IF(i_status2 = 3 && i_active = 1, 1, 0)) AS ct2, t_crewrotation.i_clientid, ");
        sb.append("t_crewrotation.i_clientassetid, p.ct1, t_clientasset.i_rflag FROM t_crewrotation ");
        sb.append("LEFT JOIN t_client ON (t_client.i_clientid = t_crewrotation.i_clientid) ");
        sb.append("LEFT JOIN t_clientasset ON (t_clientasset.i_clientassetid = t_crewrotation.i_clientassetid) ");
        sb.append("LEFT JOIN (SELECT COUNT(DISTINCT(t_candidate.i_positionid)) AS ct1, t_crewrotation.i_clientid, t_crewrotation.i_clientassetid FROM t_crewrotation LEFT JOIN t_candidate ON (t_candidate.i_candidateid = t_crewrotation.i_candidateid) ");
        sb.append("WHERE t_crewrotation.i_active = 1 AND t_crewrotation.i_clientid = ? AND t_crewrotation.i_clientassetid = ?) AS p ON (p.i_clientid = t_crewrotation.i_clientid AND p.i_clientassetid = t_crewrotation.i_clientassetid) ");
        sb.append("LEFT JOIN t_country ON (t_country.i_countryid = t_clientasset.i_countryid) WHERE  t_crewrotation.i_active = 1 AND  t_crewrotation.i_clientid = ? AND t_crewrotation.i_clientassetid = ? ");
        if (search != null && !search.equals("")) {
            sb.append(" AND (t_client.s_name LIKE ? OR t_clientasset.s_name LIKE ? OR t_country.s_name LIKE ?) ");
        }
        sb.append(" GROUP BY t_crewrotation.i_clientid, t_crewrotation.i_clientassetid ");
        String query = (sb.toString()).intern();
        sb.setLength(0);

        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            int scc = 0;
            pstmt.setInt(++scc, clientIdIndex);
            pstmt.setInt(++scc, assetIdIndex);
            pstmt.setInt(++scc, clientIdIndex);
            pstmt.setInt(++scc, assetIdIndex);

            if (search != null && !search.equals("")) {
                pstmt.setString(++scc, search.replaceFirst("^0+(?!$)", ""));
                pstmt.setString(++scc, "%" + (search) + "%");
                pstmt.setString(++scc, "%" + (search) + "%");
            }
            logger.info("getByclientandAssetName :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String clientName, Country, clientAsset;
            int status1, normal, delayed, extended, clientId, clientassetId, positionCount, crewrota;
            while (rs.next()) {
                clientName = rs.getString(1) != null ? rs.getString(1) : "";
                clientAsset = rs.getString(2) != null ? rs.getString(2) : "";
                Country = rs.getString(3) != null ? rs.getString(3) : "";
                status1 = rs.getInt(4);
                normal = rs.getInt(5);
                delayed = rs.getInt(6);
                extended = rs.getInt(7);
                clientId = rs.getInt(8);
                clientassetId = rs.getInt(9);
                positionCount = rs.getInt(10);
                crewrota = rs.getInt(11);
                info = new CrewrotationInfo(clientName, clientAsset, Country, status1, normal, delayed, extended, clientId, clientassetId, positionCount,
                        crewrota);
            }
            rs.close();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return info;
    }

    public ArrayList getCandidatesByclientandAssetName(String search, int clientIdIndex,
            int assetIdIndex, int countryId, String crdate, int statusindex, int positionIndex, int activityDropdown, int dynamicId) {

        ArrayList list = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT c.i_candidateid, CONCAT_WS(' ', c.s_firstname, c.s_middlename, c.s_lastname), t_position.s_name, t_grade.s_name, c.s_photofilename, ");
        sb.append("t_client.s_name, t_clientasset.s_name, t_country.s_name, t_crewrotation.i_crewrotationid, t_crewrotation.i_jobpostid, ");
        sb.append("DATE_FORMAT( t_crewrotation.ts_signon, '%d-%b-%Y | %H:%i'), DATE_FORMAT( t_crewrotation.ts_signoff, '%d-%b-%Y | %H:%i'), ");
        sb.append("t_crewrotation.i_docflag, t_rotation.i_noofdays, t_crewrotation.i_status1, t_crewrotation.i_status2, ");
        sb.append("DATE_FORMAT( t_crewrotation.ts_expecteddate, '%d-%b-%Y | %H:%i'), t_crewrotation.i_activityid, DATE_FORMAT( t_crewrotation.d_fromdate, '%d-%b-%Y'), ");
        sb.append("DATE_FORMAT( t_crewrotation.d_todate, '%d-%b-%Y'), t_crewrotation.s_remarks, t_crewrotation.i_active, t_position.i_rotationoverstay, ");
        sb.append("p2.s_name, g2.s_name, t_position.i_positionid, t_clientasset.i_rflag, t1.i_signonid, t1.inflag  ");
        sb.append("FROM t_crewrotation ");
        sb.append("LEFT JOIN t_candidate AS c  ON (c.i_candidateid = t_crewrotation.i_candidateid) ");
        sb.append("LEFT JOIN t_client ON (t_client.i_clientid = t_crewrotation.i_clientid) ");
        sb.append("LEFT JOIN t_clientasset ON (t_clientasset.i_clientassetid = t_crewrotation.i_clientassetid) ");
        sb.append("LEFT JOIN t_position ON (t_position.i_positionid = c.i_positionid) ");
        sb.append("LEFT JOIN t_grade ON (t_grade.i_gradeid = t_position.i_gradeid) ");
        sb.append("LEFT JOIN t_position AS p2 ON (p2.i_positionid = c.i_positionid2) ");
        sb.append("LEFT JOIN t_grade AS g2 ON (g2.i_gradeid = p2.i_gradeid) ");
        sb.append("LEFT JOIN t_country ON (t_country.i_countryid = t_clientasset.i_countryid) ");
        sb.append("LEFT JOIN t_rotation ON (t_position.i_rotationid = t_rotation.i_rotationid) ");
        sb.append("LEFT JOIN (select i_crewrotationid, i_signonid, i_signoffid, (CURRENT_DATE() < ts_todate) as inflag from t_cractivity where i_activityid = 6 AND ts_fromdate <= CURRENT_DATE() group by i_crewrotationid) as t1 on (t_crewrotation.i_crewrotationid = t1.i_crewrotationid) ");
        sb.append("WHERE t_crewrotation.i_active = 1 AND t_crewrotation.i_clientid = ? AND t_crewrotation.i_clientassetid = ? ");
        if (search != null && !search.equals("")) {
            sb.append("AND (CONCAT_WS(' ', c.s_firstname, c.s_middlename, c.s_lastname) LIKE ? OR CONCAT_WS(' ', c.s_firstname, c.s_lastname) LIKE ? OR  c.s_firstname LIKE ?) ");
        }
        if (dynamicId == 1)//signon
        {
            if (crdate != null && !crdate.equals("")) {
                sb.append("AND ( t_crewrotation.ts_signon LIKE ?  OR ( t_crewrotation.ts_expecteddate LIKE ?  AND t_crewrotation.i_status1 > 0)) ");
            }
        }
        if (dynamicId == 2)//signoff
        {
            if (crdate != null && !crdate.equals("")) {
                sb.append("AND ( t_crewrotation.ts_signoff LIKE ? OR (t_crewrotation.ts_expecteddate LIKE ?  AND  t_crewrotation.i_status2 > 0)) ");
            }
        }
        if (statusindex == 1) {
            sb.append(" AND ( t_crewrotation.ts_signon = '0000-00-00 00:00:00' AND i_status1 = 1) ");
        } else if (statusindex == 2) {
            sb.append(" AND (t_crewrotation.i_status2 >= 1 && t_crewrotation.i_sotype = 1) ");
        } else if (statusindex == 3) {
            sb.append(" AND (t_crewrotation.i_status2 >= 1 && t_crewrotation.i_sotype = 2) ");
        } else if (statusindex == 4) {
            sb.append(" AND (t_crewrotation.i_status2 >= 1 && t_crewrotation.i_sotype = 3) ");
        } else if (statusindex == 5) {
            sb.append(" AND (t_crewrotation.i_status1 > 0) ");
        } else if (statusindex == 6) {
            sb.append(" AND (t_crewrotation.i_status2 > 0) ");
        }
        if (positionIndex > 0) {
            sb.append(" AND (t_position.i_positionid = ? OR p2.i_positionid = ?) ");
        }
        if (activityDropdown > 0) {
            sb.append(" AND (t_crewrotation.i_activityid = ?) ");
        }

        String query = (sb.toString()).intern();
        sb.setLength(0);
        print(this, "query :: " + query);
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            int scc = 0;
            pstmt.setInt(++scc, clientIdIndex);
            pstmt.setInt(++scc, assetIdIndex);
            if (search != null && !search.equals("")) {
                pstmt.setString(++scc, "%" + (search) + "%");
                pstmt.setString(++scc, "%" + (search) + "%");
                pstmt.setString(++scc, "%" + (search) + "%");
            }
            if (dynamicId == 1)//signon
            {
                if (crdate != null && !crdate.equals("")) {
                    pstmt.setString(++scc, "%" + changeDate1(crdate) + "%");
                    pstmt.setString(++scc, "%" + changeDate1(crdate) + "%");
                }
            }
            if (dynamicId == 2)//signoff
            {
                if (crdate != null && !crdate.equals("")) {
                    pstmt.setString(++scc, "%" + changeDate1(crdate) + "%");
                    pstmt.setString(++scc, "%" + changeDate1(crdate) + "%");
                }
            }
            if (positionIndex > 0) {
                pstmt.setInt(++scc, positionIndex);
                pstmt.setInt(++scc, positionIndex);
            }
            if (activityDropdown > 0) {
                pstmt.setInt(++scc, activityDropdown);
            }
            logger.info("getCandidatesByclientandAssetName :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String clientName, Country, clientAsset, name, position, grade,position2, grade2, photo, signon, signoff, expecteddate, fromdate, todate, remarks, activitystatus = "", offshorestatus = "";
            int crewrotationId, status1, positionId,status2, status = 0, candidateId, jobpostId, docflagId, noofdays, 
                activityId, activeflag, overstaydays, rflag, inflag;
            while (rs.next()) {
                candidateId = rs.getInt(1);
                name = rs.getString(2) != null ? rs.getString(2) : "";
                position = rs.getString(3) != null ? rs.getString(3) : "";
                grade = rs.getString(4) != null ? rs.getString(4) : "";
                photo = rs.getString(5) != null ? rs.getString(5) : "";
                clientName = rs.getString(6) != null ? rs.getString(6) : "";
                clientAsset = rs.getString(7) != null ? rs.getString(7) : "";
                Country = rs.getString(8) != null ? rs.getString(8) : "";
                crewrotationId = rs.getInt(9);
                jobpostId = rs.getInt(10);
                signon = rs.getString(11) != null ? rs.getString(11) : "";
                signoff = rs.getString(12) != null ? rs.getString(12) : "";
                docflagId = rs.getInt(13);
                noofdays = rs.getInt(14);
                status1 = rs.getInt(15);
                status2 = rs.getInt(16);
                expecteddate = rs.getString(17) != null ? rs.getString(17) : "";
                activityId = rs.getInt(18);
                fromdate = rs.getString(19) != null ? rs.getString(19) : "";
                todate = rs.getString(20) != null ? rs.getString(20) : "";
                remarks = rs.getString(21) != null ? rs.getString(21) : "";
                activeflag = rs.getInt(22);
                overstaydays = rs.getInt(23);
                position2 = rs.getString(24) != null ? rs.getString(24) : "";
                grade2 = rs.getString(25) != null ? rs.getString(25) : "";
                activitystatus = getactivityStatusbyId(activityId);
                positionId = rs.getInt(26);
                rflag = rs.getInt(27);
                inflag = rs.getInt(28);
                if(rflag == 1)
                    offshorestatus = getCurrStVal(status1, status2, signon);
                else
                {
                    if(inflag > 0)
                        offshorestatus = "Offshore";
                    else
                        offshorestatus = "Onshore";
                }
                if (status1 > 0) {
                    status = 1;
                } else if (status2 > 0) {
                    status = 2;
                }
                if (!grade.equals("")) {
                    position += " - " + grade;
                }
                if (!grade2.equals("")) {
                    position2 += " - " + grade2;
                }

                list.add(new CrewrotationInfo(crewrotationId, candidateId, jobpostId, name, clientName, position, photo, clientAsset,
                        Country, signon, signoff, docflagId, noofdays, status, expecteddate, activityId, fromdate, todate, remarks, activitystatus, 0, 0,
                        clientIdIndex, assetIdIndex, status1, status2, offshorestatus, activeflag, overstaydays, position2, positionId));
            }
            rs.close();
        } catch (Exception e) {
            System.out.println(" getMsg :: " + e.getMessage());
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return list;
    }

    public String getactivityStatusbyId(int activityId) {
        String stval = "";
        if (activityId == 1) {
            stval = "Office Work";
        } else if (activityId == 2) {
            stval = "Training";
        } else if (activityId == 3) {
            stval = "Absent";
        } else if (activityId == 4) {
            stval = "Available";
        } else if (activityId == 5) {
            stval = "Not Available";
        } else if (activityId == 6) {
            stval = "OffShore work";
        } else if (activityId == 7) {
            stval = "Temporary Promotion";
        } else if (activityId == 8) {
            stval = "Standby";
        }
        return stval;
    }

    public String getCurrStVal(int status1, int status2, String signondate) {
        String s = "";
        if (signondate != null && signondate.equals("") && status1 == 1) {
            s = "Pending";
        } else if (status1 > 0) {
            s = "Onshore";
        } else if (status2 == 1) {
            s = "Offshore-Normal";
        } else if (status2 == 2) {
            s = "Offshore-Extended";
        } else if (status2 == 3) {
            s = "Offshore-Overstay";
        }
        return s;
    }

    public String getStatusbyId(int activityId) {
        String stval = "";

        if (activityId == 1) {
            stval = "OnShore";
        } else if (activityId == 2) {
            stval = "OffShore";
        }
        return stval;
    }

    public Collection getAsset(String assetids) {
        Collection coll = new LinkedList();
        if (!assetids.equals("")) {
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT i_clientassetid, s_name FROM t_clientasset WHERE i_status = 1 ");
            if (!assetids.equals("")) {
                sb.append("AND i_clientassetid IN (" + assetids + ") ");
            }
            sb.append("ORDER BY s_name ");
            String query = sb.toString();
            sb.setLength(0);
            coll.add(new CrewrotationInfo(-1, " Select Asset "));
            try {
                conn = getConnection();
                pstmt = conn.prepareStatement(query);
                rs = pstmt.executeQuery();
                int ddlValue;
                String ddlLabel;
                while (rs.next()) {
                    ddlValue = rs.getInt(1);
                    ddlLabel = rs.getString(2) != null ? rs.getString(2) : "";
                    coll.add(new CrewrotationInfo(ddlValue, ddlLabel));
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                close(conn, pstmt, rs);
            }
        } else {
            coll.add(new CrewrotationInfo(-1, " Select Asset "));
        }
        return coll;
    }

    public Collection getClients(String cids, int allclient, String permission) {
        Collection coll = new LinkedList();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT i_clientid, s_name FROM t_client WHERE i_status = 1 ");
        if (allclient == 1) {
            sb.append("AND i_clientid > 0 ");
        } else {
            if (!cids.equals("")) {
                sb.append("AND i_clientid IN (" + cids + ") ");
            } else {
                sb.append("AND i_clientid < 0 ");
            }
        }

        sb.append("ORDER BY s_name");
        String query = (sb.toString()).intern();
        sb.setLength(0);
        coll.add(new CrewrotationInfo(-1, " Select Client"));
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            rs = pstmt.executeQuery();
            int refId;
            String refName;
            while (rs.next()) {
                refId = rs.getInt(1);
                refName = rs.getString(2) != null ? rs.getString(2) : "";
                coll.add(new CrewrotationInfo(refId, refName));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return coll;
    }

    public Collection getClientAsset(int clientId) {
        Collection coll = new LinkedList();
        if (clientId > 0) {
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT i_clientassetid, s_name FROM t_clientasset WHERE i_clientid = ? AND i_status = 1 ");
            sb.append("ORDER BY s_name ");
            String query = sb.toString();
            sb.setLength(0);
            coll.add(new CrewrotationInfo(-1, " Select Asset "));
            try {
                conn = getConnection();
                pstmt = conn.prepareStatement(query);
                pstmt.setInt(1, clientId);
                logger.info("getClientAsset :: " + pstmt.toString());
                rs = pstmt.executeQuery();
                int ddlValue;
                String ddlLabel;
                while (rs.next()) {
                    ddlValue = rs.getInt(1);
                    ddlLabel = rs.getString(2) != null ? rs.getString(2) : "";
                    coll.add(new CrewrotationInfo(ddlValue, ddlLabel));
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                close(conn, pstmt, rs);
            }
        } else {
            coll.add(new CrewrotationInfo(-1, " Select Asset "));
        }
        return coll;
    }

    public Collection getCountryList() {
        Collection coll = new LinkedList();
        String query = ("SELECT i_countryid, s_name FROM t_country WHERE i_status = 1 ORDER BY s_name").intern();
        coll.add(new CrewrotationInfo(-1, "Select Location"));
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            rs = pstmt.executeQuery();
            int refId;
            String refName;
            while (rs.next()) {
                refId = rs.getInt(1);
                refName = rs.getString(2) != null ? rs.getString(2) : "";
                coll.add(new CrewrotationInfo(refId, refName));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return coll;
    }

    public int checkcrewActivity(int crewrotationId, String fromdate, String todate, int activityId) {
        int cractivityId = 0;
        try {
            StringBuilder sb1 = new StringBuilder();
            if (activityId == 3) {
                sb1.append("SELECT i_cractivityid  FROM t_cractivity WHERE  i_crewrotationid = ? AND i_activityid IN (3,5)  AND ((ts_fromdate <= ? AND ts_todate >= ?) OR (ts_fromdate <= ? AND ts_todate >= ?) OR (ts_fromdate >=  ? AND ts_fromdate <= ?))");
            } else if (activityId == 4) {
                sb1.append("SELECT i_cractivityid  FROM t_cractivity WHERE  i_crewrotationid = ? AND i_activityid NOT IN (6)  AND ((ts_fromdate <= ? AND ts_todate >= ?) OR (ts_fromdate <= ? AND ts_todate >= ?) OR (ts_fromdate >=  ? AND ts_fromdate <= ?))");
            } else {
                sb1.append("SELECT i_cractivityid  FROM t_cractivity WHERE  i_crewrotationid = ? AND i_activityid NOT IN (4,6)  AND ((ts_fromdate <= ? AND ts_todate >= ?) OR (ts_fromdate <= ? AND ts_todate >= ?) OR (ts_fromdate >=  ? AND ts_fromdate <= ?))");
            }
            String cquery = (sb1.toString()).intern();
            sb1.setLength(0);
            conn = getConnection();
            pstmt = conn.prepareStatement(cquery);
            int scc = 0;
            pstmt.setInt(++scc, crewrotationId);
            pstmt.setString(++scc, changeDate1(fromdate));
            pstmt.setString(++scc, changeDate1(fromdate));
            pstmt.setString(++scc, changeDate1(todate));
            pstmt.setString(++scc, changeDate1(todate));
            pstmt.setString(++scc, changeDate1(fromdate));
            pstmt.setString(++scc, changeDate1(todate));
            print(this, "checkcrewActivity :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                cractivityId = rs.getInt(1);
                break;
            }
        } catch (Exception exception) {
            print(this, "checkcrewActivity :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return cractivityId;
    }

    public void savecrewActivity(int crewrotationId, String fromdate, String enddate, 
            int activityId, int positionId, String remarks, int userId, int status, int positionIdtemp) {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("INSERT INTO t_cractivity ");
            sb.append("(i_crewrotationid, ts_fromdate , ts_todate, s_remarks, i_activityid, i_positionid, i_positionid_temp, i_status, i_userid, ts_regdate, ts_moddate) ");
            sb.append("VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            String query = (sb.toString()).intern();
            sb.setLength(0);

            conn = getConnection();
            pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            int scc = 0;
            pstmt.setInt(++scc, crewrotationId);
            pstmt.setString(++scc, changeDate1(fromdate));
            pstmt.setString(++scc, changeDate1(enddate));
            pstmt.setString(++scc, remarks);
            pstmt.setInt(++scc, activityId);
            pstmt.setInt(++scc, positionId);
            pstmt.setInt(++scc, positionIdtemp);
            pstmt.setInt(++scc, status);
            pstmt.setInt(++scc, userId);
            pstmt.setString(++scc, currDate1());
            pstmt.setString(++scc, currDate1());
            print(this, "savecrewActivity :: " + pstmt.toString());
            pstmt.executeUpdate();
            rs = pstmt.getGeneratedKeys();
        } catch (Exception exception) {
            print(this, "savecrewActivity :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
    }

    public void updatecrewrotationActivity(int crewrotationId, String fromdate, String enddate, int activityId, String remarks, int userId, String username) {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("UPDATE t_crewrotation SET ");
            sb.append("d_fromdate = ? ,");
            sb.append("d_todate = ? ,");
            sb.append("s_remarks = ? ,");
            sb.append("i_activityid = ?,");
            sb.append("i_userid = ?,");
            sb.append("s_activityby = ?,");
            sb.append("ts_moddate = ? ");
            sb.append("WHERE i_crewrotationid = ? ");
            String candidatequery = (sb.toString()).intern();
            sb.setLength(0);
            conn = getConnection();
            pstmt = conn.prepareStatement(candidatequery);
            int scc = 0;
            pstmt.setString(++scc, changeDate1(fromdate));
            pstmt.setString(++scc, changeDate1(enddate));
            pstmt.setString(++scc, remarks);
            pstmt.setInt(++scc, activityId);
            pstmt.setInt(++scc, userId);
            pstmt.setString(++scc, username);
            pstmt.setString(++scc, currDate1());
            pstmt.setInt(++scc, crewrotationId);
            print(this, "updatecrewrotationActivity :: " + pstmt.toString());
            pstmt.executeUpdate();

        } catch (Exception exception) {
            print(this, "updatecrewrotationActivity :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
    }

    public int savesignonoff(int crewrotationId, String edate, int rdateId, String remarks, String sdate, int type, int userId, int crewrota, int positionId) {
        int id = 0;
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("INSERT INTO t_signonoff ");
            sb.append("(i_crewrotationid, ts_date1, s_remarks, ts_date2, i_type, i_subtype, i_userid, ts_regdate, i_norota, i_positionid) ");
            sb.append("VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            String query = (sb.toString()).intern();
            sb.setLength(0);

            conn = getConnection();
            pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            int scc = 0;
            pstmt.setInt(++scc, crewrotationId);
            pstmt.setString(++scc, (edate));
            pstmt.setString(++scc, remarks);
            pstmt.setString(++scc, (sdate));
            pstmt.setInt(++scc, type);
            pstmt.setInt(++scc, rdateId);
            pstmt.setInt(++scc, userId);
            pstmt.setString(++scc, currDate1());
            pstmt.setInt(++scc, crewrota);
            pstmt.setInt(++scc, positionId);
            print(this, "savesignonoff :: " + pstmt.toString());
            pstmt.executeUpdate();
            rs = pstmt.getGeneratedKeys();
            while (rs.next()) {
                id = rs.getInt(1);
            }
        } catch (Exception exception) {
            print(this, "savesignonoff :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return id;
    }

    public void updatecrewrotationsignoff(int crewrotationId, String date, String expecteddate, int userId, String username) {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("UPDATE t_crewrotation SET ");
            sb.append("ts_signoff = ? ,");
            sb.append("s_signofflby = ? ,");
            sb.append("i_status1 = 1 ,");
            sb.append("i_status2 = 0 ,");
            sb.append("i_docflag = 1 ,");
            sb.append("ts_expecteddate = ? ,");
            sb.append("ts_moddate = ? ");
            sb.append("WHERE i_crewrotationid = ? ");
            String candidatequery = (sb.toString()).intern();
            sb.setLength(0);
            conn = getConnection();
            pstmt = conn.prepareStatement(candidatequery);
            int scc = 0;
            pstmt.setString(++scc, changeDate3a(date));
            pstmt.setString(++scc, username);
            pstmt.setString(++scc, expecteddate);
            pstmt.setString(++scc, currDate1());
            pstmt.setInt(++scc, crewrotationId);
            print(this, "updatecrewrotationsignoff :: " + pstmt.toString());
            pstmt.executeUpdate();

        } catch (Exception exception) {
            print(this, "updatecrewrotationsignoff :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
    }

    public void updatecractivitysignoff(int crewrotationId, String date, String expecteddate, 
            int signoffId, int userId, int rdateId, String sdate, String edate, int overstaydays) {
        try {
            StringBuilder sb1 = new StringBuilder();
            sb1.append("SELECT i_cractivityid, DATE_FORMAT( ts_fromdate, '%d-%b-%Y') FROM t_cractivity WHERE i_activityid = 6 AND i_crewrotationid = ?  ");
            sb1.append("ORDER BY ts_regdate DESC LIMIT 0,1");
            String cquery = (sb1.toString()).intern();
            sb1.setLength(0);
            conn = getConnection();
            pstmt = conn.prepareStatement(cquery);
            pstmt.setInt(1, crewrotationId);

            rs = pstmt.executeQuery();
            int cractivityId = 0;
            String fromdate = "";
            while (rs.next()) {
                cractivityId = rs.getInt(1);
                fromdate = rs.getString(2) != null ? rs.getString(2) : "";
            }
            rs.close();
            pstmt.close();
            String overstaydate = "";///0000-00-00
            if (rdateId == 3 && overstaydays > 0) {
                int daysdiff = getDiffTwoDateInt(fromdate, sdate, "dd-MMM-yyyy") - 1;
                if (daysdiff > overstaydays) {
                    overstaydate = getDateAfter(fromdate, 1, overstaydays, "dd-MMM-yyyy", "yyyy-MM-dd");
                } else {
                    overstaydate = "0000-00-00";
                }
            }
            StringBuilder sb = new StringBuilder();
            sb.append("UPDATE t_cractivity SET ");
            sb.append("i_signoffid = ?, ");
            sb.append("ts_todate = ?, ");
            if (rdateId == 3) {
                sb.append("ts_overstaydate = ?, ");
            }
            sb.append("ts_moddate = ?,");
            sb.append("i_status = 2 ");
            sb.append("WHERE i_cractivityid = ? ");
            String candidatequery = (sb.toString()).intern();
            sb.setLength(0);
            pstmt = conn.prepareStatement(candidatequery);
            int scc = 0;
            pstmt.setInt(++scc, signoffId);
            pstmt.setString(++scc, date);
            if (rdateId == 3) {
                pstmt.setString(++scc, overstaydate);
            }
            pstmt.setString(++scc, currDate1());
            pstmt.setInt(++scc, cractivityId);
            print(this, "updatecractivitysignoff :: " + pstmt.toString());
            pstmt.executeUpdate();

        } catch (Exception exception) {
            print(this, "updatecractivitysignoff :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
    }

    public CrewrotationInfo getCrewrotationBycrewrotationId(int crewrotationId) {
        CrewrotationInfo info = null;
        StringBuilder sb = new StringBuilder();
        sb.append(" SELECT c.i_candidateid , CONCAT_WS(' ', c.s_firstname, c.s_middlename, c.s_lastname),  t_position.s_name, t_grade.s_name ,c.s_photofilename, ");
        sb.append(" t_client.s_name, t_clientasset.s_name, t_country.s_name, t_crewrotation.i_crewrotationid,   ");
        sb.append("t_crewrotation.i_jobpostid , DATE_FORMAT( t_crewrotation.ts_signon, '%d-%b-%Y %H:%i'),");
        sb.append(" DATE_FORMAT( t_crewrotation.ts_signoff, '%d-%b-%Y %H:%i'), t_crewrotation.i_docflag ,t_rotation.i_noofdays ,  ");
        sb.append(" t_crewrotation.i_status1, t_crewrotation.i_status2 ,DATE_FORMAT( t_crewrotation.ts_expecteddate, '%d-%b-%Y %H:%i') ,");
        sb.append(" t_crewrotation.i_activityid, DATE_FORMAT( t_crewrotation.d_fromdate, '%d-%b-%Y %H:%i'),DATE_FORMAT( t_crewrotation.d_todate, '%d-%b-%Y %H:%i'), ");
        sb.append(" t_crewrotation.s_remarks , cr.ct1, cr2.ct2 ,t_crewrotation.i_active, t_position.i_rotationoverstay, ");
        sb.append(" t_position.i_positionid, p2.i_positionid, p2.s_name, g2.s_name, t_cractivity.i_signonid, t_cractivity.i_signoffid ");
        sb.append("FROM t_crewrotation ");
        sb.append("LEFT JOIN t_candidate AS c  ON (c.i_candidateid = t_crewrotation.i_candidateid) ");
        sb.append("LEFT JOIN t_client ON (t_client.i_clientid = t_crewrotation.i_clientid) ");
        sb.append("LEFT JOIN t_clientasset ON (t_clientasset.i_clientassetid = t_crewrotation.i_clientassetid) ");
        sb.append("LEFT JOIN t_position ON (t_position.i_positionid = c.i_positionid) ");
        sb.append("LEFT JOIN t_grade ON (t_grade.i_gradeid = t_position.i_gradeid) ");
        sb.append("LEFT JOIN t_position AS p2 ON (p2.i_positionid = c.i_positionid2) ");
        sb.append("LEFT JOIN t_grade AS g2 ON (g2.i_gradeid = p2.i_gradeid) ");
        sb.append("LEFT JOIN t_country ON (t_country.i_countryid = t_clientasset.i_countryid) ");
        sb.append("LEFT JOIN t_rotation ON (t_position.i_rotationid = t_rotation.i_rotationid) ");
        sb.append("LEFT JOIN t_cractivity ON (t_cractivity.i_crewrotationid =  t_crewrotation.i_crewrotationid) ");
        sb.append("LEFT JOIN (SELECT i_crewrotationid, COUNT(1) AS ct1 FROM t_cractivity WHERE i_activityid = 2 AND i_crewrotationid = ?) AS cr  ON (cr.i_crewrotationid = t_crewrotation.i_crewrotationid) ");
        sb.append("LEFT JOIN (SELECT i_crewrotationid, COUNT(1) AS ct2 FROM t_cractivity WHERE i_activityid = 6 AND i_crewrotationid = ?) AS cr2  ON (cr2.i_crewrotationid = t_crewrotation.i_crewrotationid) ");
        sb.append("WHERE t_crewrotation.i_crewrotationid = ?   ");
        String query = (sb.toString()).intern();
        sb.setLength(0);
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            int scc = 0;
            pstmt.setInt(++scc, crewrotationId);
            pstmt.setInt(++scc, crewrotationId);
            pstmt.setInt(++scc, crewrotationId);
            //logger.info("getCrewrotationBycrewrotationId :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String clientName, Country, clientAsset, name, position, grade, photo,
                    signon, signoff, expecteddate, fromdate, todate, remarks, activitystatus = "", position2, grade2;
            int status1, status2, status = 0, candidateId, jobpostId, docflagId, noofdays, activityId,
                    trainingCount, rotationCount, activeflag, overstaydays,positionId, positionId2, signonId, signoffId;
            while (rs.next()) {
                candidateId = rs.getInt(1);
                name = rs.getString(2) != null ? rs.getString(2) : "";
                position = rs.getString(3) != null ? rs.getString(3) : "";
                grade = rs.getString(4) != null ? rs.getString(4) : "";
                photo = rs.getString(5) != null ? rs.getString(5) : "";
                clientName = rs.getString(6) != null ? rs.getString(6) : "";
                clientAsset = rs.getString(7) != null ? rs.getString(7) : "";
                Country = rs.getString(8) != null ? rs.getString(8) : "";
                crewrotationId = rs.getInt(9);
                jobpostId = rs.getInt(10);
                signon = rs.getString(11) != null ? rs.getString(11) : "";
                signoff = rs.getString(12) != null ? rs.getString(12) : "";
                docflagId = rs.getInt(13);
                noofdays = rs.getInt(14);
                status1 = rs.getInt(15);
                status2 = rs.getInt(16);
                expecteddate = rs.getString(17) != null ? rs.getString(17) : "";
                activityId = rs.getInt(18);
                fromdate = rs.getString(19) != null ? rs.getString(19) : "";
                todate = rs.getString(20) != null ? rs.getString(20) : "";
                remarks = rs.getString(21) != null ? rs.getString(21) : "";
                trainingCount = rs.getInt(22);
                rotationCount = rs.getInt(23);
                activeflag = rs.getInt(24);
                overstaydays = rs.getInt(25);
                positionId = rs.getInt(26);
                positionId2 = rs.getInt(27);
                position2 = rs.getString(28) != null ? rs.getString(28) : "";
                grade2 = rs.getString(29) != null ? rs.getString(29) : "";
                signonId = rs.getInt(30);
                signoffId = rs.getInt(31);
                activitystatus = getactivityStatusbyId(activityId);
                if (status1 > 0) {
                    status = 1;
                } else if (status2 > 0) {
                    status = 2;
                }
                if (!grade.equals("")) {
                    position += " - " + grade;
                }
                if (!grade2.equals("")) {
                    position2 += " - " + grade2;
                }
                info = new CrewrotationInfo(crewrotationId, candidateId, jobpostId, name, clientName, position, photo, clientAsset,
                        Country, signon, signoff, docflagId, noofdays, status, expecteddate, activityId, fromdate, todate, remarks, activitystatus,
                        trainingCount, rotationCount, 0, 0, status1, status2, "", activeflag, overstaydays, positionId,positionId2, position2, signonId, signoffId);
            }
            rs.close();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return info;
    }

    public void updatecrewrotationsignon(int crewrotationId, String date, String expecteddate, int userId, int rdateId, String username) {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("UPDATE t_crewrotation SET ");
            sb.append("ts_signon = ? ,");
            sb.append("s_signonlby = ? ,");
            sb.append("i_status1 = 0 ,");
            sb.append("i_status2 = 1 ,");
            sb.append("i_sotype = ? ,");
            sb.append("ts_expecteddate = ? ,");
            sb.append("ts_moddate = ? ");
            sb.append("WHERE i_crewrotationid = ? ");
            String candidatequery = (sb.toString()).intern();
            sb.setLength(0);
            conn = getConnection();
            pstmt = conn.prepareStatement(candidatequery);
            int scc = 0;
            pstmt.setString(++scc, changeDate3a(date));
            pstmt.setString(++scc, username);
            pstmt.setInt(++scc, rdateId);
            pstmt.setString(++scc, expecteddate);
            pstmt.setString(++scc, currDate1());
            pstmt.setInt(++scc, crewrotationId);
            print(this, "updatecrewrotationsignon :: " + pstmt.toString());
            pstmt.executeUpdate();

        } catch (Exception exception) {
            print(this, "updatecrewrotationsignon :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
    }

    public void insertcrewActivitysignon(int crewrotationId, String fromdate, String enddate, int activityId,
            String remarks, int userId, int status, int signonId, int noofdays, int overstaydays, int p2positionId) {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("INSERT INTO t_cractivity ");
            sb.append("(i_crewrotationid, ts_fromdate ,ts_todate,s_remarks, i_activityid, i_extendeddays, i_overstaydays, i_status,i_signonid, i_userid, ts_regdate, ts_moddate, i_positionid) ");
            sb.append("VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            String query = (sb.toString()).intern();
            sb.setLength(0);
            
            conn = getConnection();
            pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            int scc = 0;
            pstmt.setInt(++scc, crewrotationId);
            pstmt.setString(++scc, changeDate1(fromdate));
            pstmt.setString(++scc, (enddate));
            pstmt.setString(++scc, remarks);
            pstmt.setInt(++scc, activityId);
            pstmt.setInt(++scc, noofdays);
            pstmt.setInt(++scc, overstaydays);
            pstmt.setInt(++scc, status);
            pstmt.setInt(++scc, signonId);
            pstmt.setInt(++scc, userId);
            pstmt.setString(++scc, currDate1());
            pstmt.setString(++scc, currDate1());
            pstmt.setInt(++scc, p2positionId);
            print(this, "insertcrewActivitysignon :: " + pstmt.toString());
            pstmt.executeUpdate();
            rs = pstmt.getGeneratedKeys();
        } catch (Exception exception) {
            print(this, "insertcrewActivitysignon :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
    }

    public ArrayList getDocchecks(int clientId, int clientassetId) {
        ArrayList list = new ArrayList();
        if (clientId > 0) {
            String query = ("SELECT i_doccheckid, s_name FROM t_doccheck WHERE i_status = 1 AND i_clientid = ? AND i_clientassetid = ? ORDER BY s_name").intern();
            //    list.add(new CrewrotationInfo(-1, "- Select -"));
            try {
                conn = getConnection();
                pstmt = conn.prepareStatement(query);
                pstmt.setInt(1, clientId);
                pstmt.setInt(2, clientassetId);
                print(this, "getDocchecks :: " + pstmt.toString());
                rs = pstmt.executeQuery();
                int refId;
                String refName;
                while (rs.next()) {
                    refId = rs.getInt(1);
                    refName = rs.getString(2) != null ? rs.getString(2) : "";
                    list.add(new CrewrotationInfo(refId, refName));
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                close(conn, pstmt, rs);
            }
        } else {
            //  list.add(new CrewrotationInfo(-1, "- Select -"));
        }
        return list;
    }

    public CrewrotationInfo getCRDocchecks(int crewrotationId) {
        CrewrotationInfo info = null;
        if (crewrotationId > 0) {
            String query = ("SELECT s_yes, s_no, s_na FROM t_crdoc WHERE  i_crewrotationid = ? ORDER BY ts_regdate desc limit 0,1").intern();
            //    list.add(new CrewrotationInfo(-1, "- Select -"));
            try {
                conn = getConnection();
                pstmt = conn.prepareStatement(query);
                pstmt.setInt(1, crewrotationId);
                print(this, "getCRDocchecks :: " + pstmt.toString());
                rs = pstmt.executeQuery();
                String Yes, No, NA;
                while (rs.next()) {
                    Yes = rs.getString(1) != null ? rs.getString(1) : "";
                    No = rs.getString(2) != null ? rs.getString(2) : "";
                    NA = rs.getString(3) != null ? rs.getString(3) : "";
                    info = new CrewrotationInfo(crewrotationId, Yes, No, NA);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                close(conn, pstmt, rs);
            }
        } else {
            //  list.add(new CrewrotationInfo(-1, "- Select -"));
        }
        return info;
    }

    public ArrayList getList(String ids1, String ids2, String ids3) {
        ArrayList list = new ArrayList();
        String ids = "";
        if (ids1 != null && !ids1.equals("")) {
            if (ids.equals("")) {
                ids = ids1;
            } else {
                ids += "," + ids1;
            }
        }
        if (ids2 != null && !ids2.equals("")) {
            if (ids.equals("")) {
                ids = ids2;
            } else {
                ids += "," + ids2;
            }
        }
        if (ids3 != null && !ids3.equals("")) {
            if (ids.equals("")) {
                ids = ids3;
            } else {
                ids += "," + ids3;
            }
        }
        String query = "SELECT i_doccheckid, s_name FROM t_doccheck WHERE i_doccheckid IN (" + ids + ") ";
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            rs = pstmt.executeQuery();
            int id;
            String name;
            while (rs.next()) {
                id = rs.getInt(1);
                name = rs.getString(2) != null ? rs.getString(2) : "";
                String type = "";
                if (checkToStr(ids1, "" + id)) {
                    type = "Yes";
                } else if (checkToStr(ids2, "" + id)) {
                    type = "No";
                } else if (checkToStr(ids3, "" + id)) {
                    type = "NA";
                }
                list.add(new CrewrotationInfo(id, name, type));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return list;
    }

    public void insertdoc(int crewrotationId, String[] docdata, int userId) {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("INSERT INTO t_crdoc ");
            sb.append("(i_crewrotationid, s_yes, s_no, s_na, i_userid, ts_regdate) ");
            sb.append("VALUES (?, ?, ?, ?, ?, ?)");
            String query = (sb.toString()).intern();
            sb.setLength(0);

            conn = getConnection();
            pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            int scc = 0;
            pstmt.setInt(++scc, crewrotationId);
            pstmt.setString(++scc, docdata[0]);
            pstmt.setString(++scc, docdata[1]);
            pstmt.setString(++scc, docdata[2]);
            pstmt.setInt(++scc, userId);
            pstmt.setString(++scc, currDate1());
            print(this, "insertdoc :: " + pstmt.toString());
            pstmt.executeUpdate();
            rs = pstmt.getGeneratedKeys();
        } catch (Exception exception) {
            print(this, "insertdoc :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
    }

    public String[] getData(int arr1[], int arr2[]) {
        String s1 = "", s2 = "", s3 = "";
        int len = arr1.length;
        if (len > 0) {
            for (int i = 0; i < len; i++) {
                if (arr2[i] == 1) {
                    if (s1.equals("")) {
                        s1 = "" + arr1[i];
                    } else {
                        s1 += "," + arr1[i];
                    }
                } else if (arr2[i] == 2) {
                    if (s2.equals("")) {
                        s2 = "" + arr1[i];
                    } else {
                        s2 += "," + arr1[i];
                    }
                } else if (arr2[i] == 3) {
                    if (s3.equals("")) {
                        s3 = "" + arr1[i];
                    } else {
                        s3 += "," + arr1[i];
                    }
                }
            }
        }
        String darr[] = new String[3];
        darr[0] = s1;
        darr[1] = s2;
        darr[2] = s3;
        return darr;
    }

    public void updatecrewrotationcflag(int crewrotationId, int docflag, int userId) {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("UPDATE t_crewrotation SET ");
            sb.append("i_docflag = ? ,");
            sb.append("i_userid = ? ,");
            sb.append("ts_moddate = ? ");
            sb.append("WHERE i_crewrotationid = ? ");
            String candidatequery = (sb.toString()).intern();
            sb.setLength(0);
            conn = getConnection();
            pstmt = conn.prepareStatement(candidatequery);
            int scc = 0;
            pstmt.setInt(++scc, docflag);
            pstmt.setInt(++scc, userId);
            pstmt.setString(++scc, currDate1());
            pstmt.setInt(++scc, crewrotationId);
            print(this, "updatecrewrotationcflag :: " + pstmt.toString());
            pstmt.executeUpdate();

        } catch (Exception exception) {
            print(this, "updatecrewrotationcflag :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
    }

    public void updatecrActivitybyId(int crewrotationId, int cractivityId, int userId, String fromdate,
            String enddate, String remarks, int activityId, int positionId, int positionIdtemp) {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("UPDATE t_cractivity SET ");
            sb.append("ts_fromdate = ? ,");
            sb.append("ts_todate = ? ,");
            sb.append("s_remarks = ? ,");
            sb.append("i_activityid = ? ,");
            sb.append("i_positionid = ? ,");
            sb.append("i_positionid_temp = ? ,");
            sb.append("i_userid = ? ,");
            sb.append("ts_moddate = ? ");
            sb.append(" WHERE i_crewrotationid = ? AND i_cractivityid = ?");
            String candidatequery = (sb.toString()).intern();
            sb.setLength(0);
            conn = getConnection();
            pstmt = conn.prepareStatement(candidatequery);
            int scc = 0;
            pstmt.setString(++scc, changeDate1(fromdate));
            pstmt.setString(++scc, changeDate1(enddate));
            pstmt.setString(++scc, remarks);
            pstmt.setInt(++scc, activityId);
            pstmt.setInt(++scc, positionId);
            pstmt.setInt(++scc, positionIdtemp);
            pstmt.setInt(++scc, userId);
            pstmt.setString(++scc, currDate1());
            pstmt.setInt(++scc, crewrotationId);
            pstmt.setInt(++scc, cractivityId);
            print(this, "updatecrActivitybyId :: " + pstmt.toString());
            pstmt.executeUpdate();

        } catch (Exception exception) {
            print(this, "updatecrActivitybyId :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
    }

    public ArrayList getCrewActivityListBycrewrotationId(String fromdateindex, String todateindex,
            int activityIdindex, int crewrotationId, int positionId2) {
        ArrayList list = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append(" SELECT t_cractivity.i_cractivityid,DATE_FORMAT( t_cractivity.ts_fromdate, '%d-%b-%Y'), DATE_FORMAT( t_cractivity.ts_todate, '%d-%b-%Y'), ");
        sb.append(" t_cractivity.i_status, t_cractivity.i_activityid, DATEDIFF(ts_todate, ts_fromdate), t_rotation.i_noofdays, t_crewrotation.i_active, ");
        sb.append(" CONCAT_WS(' ', c.s_firstname, c.s_middlename, c.s_lastname), ");
        sb.append("t_client.s_name, t_clientasset.s_name, ");
        sb.append("t_position.s_name, t_position.i_positionid, t_grade.s_name, p3.i_positionid,  p3.s_name,  g3.s_name ");
        sb.append("from t_cractivity ");
        sb.append("LEFT JOIN t_crewrotation ON (t_crewrotation.i_crewrotationId = t_cractivity.i_crewrotationId) ");
        sb.append("LEFT JOIN t_candidate as c  on (c.i_candidateid = t_crewrotation.i_candidateid) ");
        sb.append("LEFT JOIN t_position ON (t_position.i_positionid = t_cractivity.i_positionid) ");
        sb.append("LEFT JOIN t_grade ON (t_grade.i_gradeid = t_position.i_gradeid) ");
        sb.append("LEFT JOIN t_position AS p3 ON (p3.i_positionid = t_cractivity.i_positionid_temp) ");
        sb.append("LEFT JOIN t_grade AS g3 ON (g3.i_gradeid = p3.i_gradeid) ");
        sb.append("LEFT JOIN t_rotation ON (t_position.i_rotationid = t_rotation.i_rotationid) ");
        sb.append("LEFT JOIN t_client ON (t_client.i_clientid = t_crewrotation.i_clientid) ");
        sb.append("LEFT JOIN t_clientasset ON (t_clientasset.i_clientassetid = t_crewrotation.i_clientassetid) ");
        sb.append(" WHERE t_cractivity.i_crewrotationId = ?  ");
        if (fromdateindex != null && !fromdateindex.equals("") && todateindex != null && !todateindex.equals("")) {
            sb.append(" AND (( t_cractivity.ts_fromdate  >= ?  AND  t_cractivity.ts_fromdate  <= ? ) OR ( t_cractivity.ts_todate  >= ?  AND  t_cractivity.ts_todate  <= ? ) )");
        }
        if (activityIdindex == 7) {
            sb.append(" AND t_cractivity.i_activityid = 2 AND t_cractivity.i_status = 2  ");
        } else if (activityIdindex == 8) {
            sb.append(" AND t_cractivity.i_activityid = 7 AND t_cractivity.i_status = 2  ");
        } else if (activityIdindex == 9) {
            sb.append(" AND t_cractivity.i_activityid = 8 AND t_cractivity.i_status = 2  ");
        } else if (activityIdindex > 0 && activityIdindex != 7 && activityIdindex != 8 && activityIdindex != 9) {
            sb.append(" AND t_cractivity.i_activityid = ?  ");
        }
        if(positionId2 > 0)
        {
            sb.append(" AND t_cractivity.i_positionid = ?  OR t_cractivity.i_positionid_temp = ? ");
        }
        sb.append(" ORDER BY t_cractivity.ts_fromdate desc ");
        String query = (sb.toString()).intern();
        sb.setLength(0);

        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            int scc = 0;
            pstmt.setInt(++scc, crewrotationId);

            if (fromdateindex != null && !fromdateindex.equals("") && todateindex != null && !todateindex.equals("")) {
                pstmt.setString(++scc, changeDate1(fromdateindex));
                pstmt.setString(++scc, changeDate1(todateindex));
                pstmt.setString(++scc, changeDate1(fromdateindex));
                pstmt.setString(++scc, changeDate1(todateindex));
            }
            if (activityIdindex > 0 && activityIdindex != 7 && activityIdindex != 8) {
                pstmt.setInt(++scc, activityIdindex);
            }
            if (positionId2 > 0) {
                pstmt.setInt(++scc, positionId2);
                pstmt.setInt(++scc, positionId2);
            }
            //logger.info("getCrewActivityListBycrewrotationId :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String fromdate, todate, activitystatus = "", statusvalue = "", 
                    candidatename, client, asset, position, grade, position3, grade3;
            int cractivityId, status, datedifference, activityId = 0, noofdays = 0, activeflag = 0, positionId=0, temppositionId=0;
            while (rs.next()) {
                cractivityId = rs.getInt(1);
                fromdate = rs.getString(2) != null ? rs.getString(2) : "";
                todate = rs.getString(3) != null ? rs.getString(3) : "";
                status = rs.getInt(4);
                activityId = rs.getInt(5);
                datedifference = rs.getInt(6);
                noofdays = rs.getInt(7);
                activeflag = rs.getInt(8);
                candidatename = rs.getString(9) != null ? rs.getString(9) : "";
                client = rs.getString(10) != null ? rs.getString(10) : "";
                asset = rs.getString(11) != null ? rs.getString(11) : "";
                position = rs.getString(12) != null ? rs.getString(12) : "";
                positionId = rs.getInt(13);
                grade = rs.getString(14) != null ? rs.getString(14) : "";
                temppositionId = rs.getInt(15);
                position3 = rs.getString(16) != null ? rs.getString(16) : "";
                grade3 = rs.getString(17) != null ? rs.getString(17) : "";                
                if(!grade.equals(""))
                {
                    if(!position.equals(""))
                    {
                        position += " | "+grade;
                    }
                }
                if(!grade3.equals(""))
                {
                    if(!position3.equals(""))
                    {
                        position3 += " | "+grade3;
                    }
                }
                if(activityId == 7)
                {
                    position = position3;
                }
                activitystatus = getactivityStatusbyId(activityId);
                statusvalue = getStatusbyId(status);
                if (activityId != 6) {
                    datedifference = datedifference + 1;
                }
                list.add(new CrewrotationInfo(crewrotationId, cractivityId, fromdate, todate, status, activityId,
                        datedifference, activitystatus, statusvalue, noofdays, activeflag, candidatename, client,
                        asset, position, positionId));
            }
            rs.close();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return list;
    }

    public CrewrotationInfo getActivitydetailbycractivityId(int crewrotationId, int cractivityId) {
        CrewrotationInfo info = null;
        if (cractivityId > 0) {
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT t_cractivity.i_activityid ,DATE_FORMAT( t_cractivity.ts_fromdate, '%d-%b-%Y'), DATE_FORMAT( t_cractivity.ts_todate, '%d-%b-%Y'), ");
            sb.append("t_cractivity.s_remarks,t_cractivity.i_status,t_cractivity.i_userid,t_cractivity.i_signonid,t_cractivity.i_signoffid, t_crewrotation.i_active, ");
            sb.append("t_cractivity.i_positionid, t_crewrotation.i_clientassetid, CONCAT(t_position.s_name,' | ',t_grade.s_name), ");
            sb.append("t_cractivity.i_positionid_temp, CONCAT(p2.s_name,' | ',g2.s_name) ");
            sb.append("FROM t_cractivity ");
            sb.append("LEFT JOIN t_crewrotation ON (t_cractivity.i_crewrotationid = t_crewrotation.i_crewrotationid)");
            sb.append("LEFT JOIN t_position ON (t_position.i_positionid = t_cractivity.i_positionid) ");
            sb.append("LEFT JOIN t_grade ON (t_grade.i_gradeid = t_position.i_gradeid) ");
            sb.append("LEFT JOIN t_position AS p2 ON (p2.i_positionid = t_cractivity.i_positionid_temp) "); 
            sb.append("LEFT JOIN t_grade AS g2 ON (g2.i_gradeid = p2.i_gradeid) ");
            sb.append(" WHERE t_cractivity.i_crewrotationid = ? AND t_cractivity.i_cractivityid = ? ");
            String query = sb.toString();
            try {
                conn = getConnection();
                pstmt = conn.prepareStatement(query);
                pstmt.setInt(1, crewrotationId);
                pstmt.setInt(2, cractivityId);
                print(this, "getActivitydetailbyactivityId :: " + pstmt.toString());
                rs = pstmt.executeQuery();
                int activityId, userId, status, signonId, signoffId, activeflag, positionId,positionId2, clientassetId;
                String fromdate, todate, remarks, activitystatus = "", position = "", position2="";
                while (rs.next()) {
                    activityId = rs.getInt(1);
                    fromdate = rs.getString(2) != null ? rs.getString(2) : "";
                    todate = rs.getString(3) != null ? rs.getString(3) : "";
                    remarks = rs.getString(4) != null ? rs.getString(4) : "";
                    status = rs.getInt(5);
                    userId = rs.getInt(6);
                    signonId = rs.getInt(7);
                    signoffId = rs.getInt(8);
                    activeflag = rs.getInt(9);
                    positionId = rs.getInt(10);
                    clientassetId = rs.getInt(11);
                    position = rs.getString(12) != null ? rs.getString(12) : "";
                    positionId2 = rs.getInt(13);
                    position2 = rs.getString(14) != null ? rs.getString(14) : "";
                    
                    activitystatus = getactivityStatusbyId(activityId);
                    info = new CrewrotationInfo(activityId, fromdate, todate, remarks, status,
                            userId, signonId, signoffId, crewrotationId, cractivityId, activitystatus,
                            activeflag, positionId, clientassetId, position, positionId2, position2);
                }
                rs.close();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                close(conn, pstmt, rs);
            }
        } else {
            //  list.add(new CrewrotationInfo(-1, "- Select -"));
        }
        return info;
    }

    public CrewrotationInfo getSingnonoffdetailbysignonId(int crewrotationId, int signonId) {
        CrewrotationInfo info = null;
        if (signonId > 0) {
            String query = ("SELECT DATE_FORMAT( t_signonoff.ts_date1, '%d-%b-%Y %H:%i'), DATE_FORMAT( t_signonoff.ts_date2, '%d-%b-%Y %H:%i'), s_remarks, "
                    + "i_type, i_subtype, i_userid, i_norota, i_positionid FROM t_signonoff WHERE i_crewrotationid =? AND i_signonoffid =? ").intern();
            try {
                conn = getConnection();
                pstmt = conn.prepareStatement(query);
                pstmt.setInt(1, crewrotationId);
                pstmt.setInt(2, signonId);
                print(this, "getSingnonoffdetailbysignonId :: " + pstmt.toString());
                rs = pstmt.executeQuery();
                int userId, type, subtype, norota, positionId;
                String remarks, date1, date2;
                while (rs.next()) {
                    date1 = rs.getString(1) != null ? rs.getString(1) : "";
                    date2 = rs.getString(2) != null ? rs.getString(2) : "";
                    remarks = rs.getString(3) != null ? rs.getString(3) : "";
                    type = rs.getInt(4);
                    subtype = rs.getInt(5);
                    userId = rs.getInt(6);
                    norota = rs.getInt(7);
                    positionId = rs.getInt(8);
                    info = new CrewrotationInfo(signonId, date1, date2, remarks, type, subtype, userId, crewrotationId, norota, positionId);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                close(conn, pstmt, rs);
            }
        }
        return info;
    }
    

    public int getMaxActivityId(int crewrotationId) {
        int maxId = 0;
        if (crewrotationId > 0) {
            String query = ("SELECT MAX(i_cractivityid) FROM t_cractivity WHERE i_crewrotationid = ? AND i_activityid != 6 ").intern();
            try {
                conn = getConnection();
                pstmt = conn.prepareStatement(query);
                pstmt.setInt(1, crewrotationId);
                print(this, "getMaxActivityId :: " + pstmt.toString());
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    maxId = rs.getInt(1);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                close(conn, pstmt, rs);
            }
        }
        return maxId;
    }

    public void updatesignonoffbyId(int crewrotationId, int signonoffId, int userId, String fromdate, String enddate, String remarks, int subtype, int positionId) {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("UPDATE t_signonoff SET ");
            if (!fromdate.equals("")) {
                sb.append("ts_date1 = ? ,");
            }
            if (!enddate.equals("")) {
                sb.append("ts_date2 = ? ,");
            }
            sb.append("s_remarks = ? ,");
            sb.append("i_subtype = ? ,");
            sb.append("i_userid = ? ,");
            sb.append("i_positionid = ? ,");
            sb.append("ts_moddate = ? ");
            sb.append(" WHERE i_crewrotationid = ? AND i_signonoffid = ?");
            String candidatequery = (sb.toString()).intern();
            sb.setLength(0);
            conn = getConnection();
            pstmt = conn.prepareStatement(candidatequery);
            int scc = 0;
            if (!fromdate.equals("")) {
                pstmt.setString(++scc, (fromdate));//changeDate1
            }
            if (!enddate.equals("")) {
                pstmt.setString(++scc, (enddate));//changeDate1
            }

            pstmt.setString(++scc, remarks);
            pstmt.setInt(++scc, subtype);
            pstmt.setInt(++scc, userId);
            pstmt.setInt(++scc, positionId);
            pstmt.setString(++scc, currDate1());
            pstmt.setInt(++scc, crewrotationId);
            pstmt.setInt(++scc, signonoffId);
            print(this, "updatesignonoffbyId :: " + pstmt.toString());
            pstmt.executeUpdate();

        } catch (Exception exception) {
            print(this, "updatesignonoffbyId :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
    }

    public void updatesignonoffbyIdModify(int crewrotationId, int signonoffId, int userId,
            String fromdate, String enddate, String remarks, int subtype) {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("UPDATE t_signonoff SET ");
            if (!fromdate.equals("")) {
                sb.append("ts_date1 = ? ,");
            }
            sb.append("i_userid = ? ,");
            sb.append("ts_moddate = ? ");
            sb.append(" WHERE i_crewrotationid = ? AND i_signonoffid = ?");
            String candidatequery = (sb.toString()).intern();
            sb.setLength(0);
            conn = getConnection();
            pstmt = conn.prepareStatement(candidatequery);
            int scc = 0;
            if (!fromdate.equals("")) {
                pstmt.setString(++scc, (fromdate));
            }
            pstmt.setInt(++scc, userId);
            pstmt.setString(++scc, currDate1());
            pstmt.setInt(++scc, crewrotationId);
            pstmt.setInt(++scc, signonoffId);
            print(this, "updatesignonoffbyIdModify :: " + pstmt.toString());
            pstmt.executeUpdate();

        } catch (Exception exception) {
            print(this, "updatesignonoffbyIdModify :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
    }

    public void updatecrActivitysignonbyId(int crewrotationId, int cractivityId, int userId, 
            String fromdate, String todate, String remarks, int positionId) {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("UPDATE t_cractivity SET ");
            sb.append("ts_fromdate = ? ,");
            sb.append("s_remarks = ? ,");
            sb.append("i_userid = ? ,");
            sb.append("i_positionid = ? ,");
            sb.append("ts_moddate = ? ");
            sb.append(" WHERE i_crewrotationid = ? AND i_cractivityid = ?");
            String candidatequery = (sb.toString()).intern();
            sb.setLength(0);
            conn = getConnection();
            pstmt = conn.prepareStatement(candidatequery);
            int scc = 0;
            pstmt.setString(++scc, changeDate1(fromdate));
            pstmt.setString(++scc, remarks);
            pstmt.setInt(++scc, userId);
            pstmt.setInt(++scc, positionId);
            pstmt.setString(++scc, currDate1());
            pstmt.setInt(++scc, crewrotationId);
            pstmt.setInt(++scc, cractivityId);
            print(this, "updatecrActivitysignonbyId :: " + pstmt.toString());
            pstmt.executeUpdate();

        } catch (Exception exception) {
            print(this, "updatecrActivitysignonbyId :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
    }

    public void updatecrActivitysignon1byId(int crewrotationId, int cractivityId, int userId, String fromdate, String remarks, int positionId) {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("UPDATE t_cractivity SET ");
            sb.append("ts_fromdate = ? ,");
            sb.append("s_remarks = ? ,");
            sb.append("i_userid = ? ,");
            sb.append("i_positionid = ? ,");
            sb.append("ts_moddate = ? ");
            sb.append(" WHERE i_crewrotationid = ? AND i_cractivityid = ?");
            String candidatequery = (sb.toString()).intern();
            sb.setLength(0);
            conn = getConnection();
            pstmt = conn.prepareStatement(candidatequery);
            int scc = 0;
            pstmt.setString(++scc, changeDate1(fromdate));
            pstmt.setString(++scc, remarks);
            pstmt.setInt(++scc, userId);
            pstmt.setInt(++scc, positionId);
            pstmt.setString(++scc, currDate1());
            pstmt.setInt(++scc, crewrotationId);
            pstmt.setInt(++scc, cractivityId);
            print(this, "updatecrActivitysignon1byId :: " + pstmt.toString());
            pstmt.executeUpdate();

        } catch (Exception exception) {
            print(this, "updatecrActivitysignon1byId :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
    }

    public void updatecrActivitysignoffbyId(int crewrotationId, int cractivityId, int userId,
            String enddate, String remarks, int rdateId, String sdate, String edate, int overstaydays) {
        try {
            StringBuilder sb1 = new StringBuilder();
            sb1.append("SELECT DATE_FORMAT( ts_fromdate, '%d-%b-%Y') FROM t_cractivity WHERE  i_cractivityid = ?  ");
            String cquery = (sb1.toString()).intern();
            sb1.setLength(0);
            conn = getConnection();
            pstmt = conn.prepareStatement(cquery);
            pstmt.setInt(1, cractivityId);
            rs = pstmt.executeQuery();
            String fromdate = "";
            while (rs.next()) {
                fromdate = rs.getString(1);
            }
            rs.close();
            pstmt.close();
            String overstaydate = "0000-00-00";//
            if (rdateId == 3 && overstaydays > 0) {
                int daysdiff = getDiffTwoDateInt(fromdate, sdate, "dd-MMM-yyyy") - 1;//change -1
                if (daysdiff > overstaydays) {
                    overstaydate = getDateAfter(fromdate, 1, overstaydays, "dd-MMM-yyyy", "yyyy-MM-dd");
                } else {
                    overstaydate = "0000-00-00";//
                }
            }

            StringBuilder sb = new StringBuilder();
            sb.append("UPDATE t_cractivity SET ");
            sb.append("ts_todate = ? ,");
            if (rdateId == 3) {
                sb.append("ts_overstaydate = ? ,");
            }
            sb.append("i_userid = ? ,");
            sb.append("ts_moddate = ? ");
            sb.append(" WHERE i_crewrotationid = ? AND i_cractivityid = ?");
            String candidatequery = (sb.toString()).intern();
            sb.setLength(0);
            conn = getConnection();
            pstmt = conn.prepareStatement(candidatequery);
            int scc = 0;
            pstmt.setString(++scc, changeDate1(enddate));
            if (rdateId == 3) {
                pstmt.setString(++scc, overstaydate);
            }
            pstmt.setInt(++scc, userId);
            pstmt.setString(++scc, currDate1());
            pstmt.setInt(++scc, crewrotationId);
            pstmt.setInt(++scc, cractivityId);
            print(this, "updatecrActivitysignoffbyId :: " + pstmt.toString());
            pstmt.executeUpdate();

        } catch (Exception exception) {
            print(this, "updatecrActivitysignoffbyId :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
    }

    public int getMaxSignonoffId(int crewrotationId) {
        int maxId = 0;
        if (crewrotationId > 0) {
            String query = ("select MAX(i_signonoffid) from t_signonoff WHERE i_crewrotationid = ? ").intern();
            try {
                conn = getConnection();
                pstmt = conn.prepareStatement(query);
                pstmt.setInt(1, crewrotationId);
                print(this, "getMaxSignonoffId :: " + pstmt.toString());
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    maxId = rs.getInt(1);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                close(conn, pstmt, rs);
            }
        }
        return maxId;
    }

    public static String currDatef() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Asia/Calcutta"));
        java.util.Date dt = cal.getTime();
        return sdf.format(dt);
    }

    public static String currtime() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Asia/Calcutta"));
        java.util.Date dt = cal.getTime();
        return sdf.format(dt);
    }

    public String changeDate3a(String date) {
        String str = "0000-00-00 00:00";
        try {
            if (date != null && !date.equals("") && !date.equals("00-00-0000")) {
                SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
                SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                str = sdf2.format(sdf1.parse(date));
            }
        } catch (Exception e) {
            print(this, "Error in changeDate3 :: " + e.getMessage());
        }
        return str;
    }

    public Collection getClientAsset(int clientId, String assetids, int allclient, String permission) {
        Collection coll = new LinkedList();
        if (clientId > 0) {
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT i_clientassetid, s_name FROM t_clientasset WHERE i_clientid = ? AND i_status = 1 ");
            if (allclient == 1) {
                sb.append("AND i_clientassetid > 0 ");
            } else {
                if (!assetids.equals("")) {
                    sb.append("AND i_clientassetid IN (" + assetids + ") ");
                } else {
                    sb.append("AND i_clientassetid < 0 ");
                }
            }
            sb.append("ORDER BY s_name ");
            String query = sb.toString();
            sb.setLength(0);
            coll.add(new CrewrotationInfo(-1, " Select Asset "));
            try {
                conn = getConnection();
                pstmt = conn.prepareStatement(query);
                pstmt.setInt(1, clientId);
                logger.info("getClientAsset :: " + pstmt.toString());
                rs = pstmt.executeQuery();
                int ddlValue;
                String ddlLabel;
                while (rs.next()) {
                    ddlValue = rs.getInt(1);
                    ddlLabel = rs.getString(2) != null ? rs.getString(2) : "";
                    coll.add(new CrewrotationInfo(ddlValue, ddlLabel));
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                close(conn, pstmt, rs);
            }
        } else {
            coll.add(new CrewrotationInfo(-1, " Select Asset "));
        }
        return coll;
    }

    public Collection getPositionList(int clientassetId) {
        Collection coll = new LinkedList();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT  t_position.i_positionid, CONCAT(t_position.s_name,' | ',t_grade.s_name) ");
        sb.append("FROM t_clientassetposition ");
        sb.append("LEFT JOIN t_position ON (t_position.i_positionid = t_clientassetposition.i_positionid)  ");
        sb.append("LEFT JOIN t_grade ON (t_grade.i_gradeid = t_position.i_gradeid) ");
        sb.append("WHERE t_clientassetposition.i_clientassetid = ? AND t_clientassetposition.i_status = 1 ");
        sb.append("ORDER BY t_position.s_name ");
        String query = sb.toString();
        sb.setLength(0);
        coll.add(new CrewrotationInfo(-1, " Select Position "));
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, clientassetId);
            logger.info("getPositionList :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            int ddlValue;
            String ddlLabel;
            while (rs.next()) {
                ddlValue = rs.getInt(1);
                ddlLabel = rs.getString(2) != null ? rs.getString(2) : "";
                coll.add(new CrewrotationInfo(ddlValue, ddlLabel));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }

        return coll;
    }

    //Excel Report
    public ArrayList getListForExcel(String search, int statusIndex, int clientIdIndex, int assetIdIndex, int countryId, int allclient, String permission, String cids, String assetids) {
        ArrayList list = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT t_client.s_name, t_clientasset.s_name, t_country.s_name, SUM(IF(i_status1 = 1 && i_active = 1, 1, 0)), SUM(IF(i_status2 = 1 && i_active = 1, 1, 0)) ");
        sb.append("AS ct1,   SUM(IF(i_status2 = 2 && i_active = 1, 1, 0)),   SUM(IF(i_status2 = 3 && i_active = 1, 1, 0)) AS ct2  ,t_crewrotation.i_clientid , t_crewrotation.i_clientassetid ");
        sb.append("FROM t_crewrotation ");
        sb.append("LEFT JOIN t_client ON (t_client.i_clientid = t_crewrotation.i_clientid) ");
        sb.append("LEFT JOIN t_clientasset ON (t_clientasset.i_clientassetid = t_crewrotation.i_clientassetid) ");
        sb.append("LEFT JOIN t_country ON (t_country.i_countryid = t_clientasset.i_countryid) ");
        sb.append("WHERE 0 = 0 AND  t_crewrotation.i_active = 1 ");
        if (allclient == 1) {
            sb.append(" AND t_client.i_clientid > 0 ");
        } else {
            if (!cids.equals("")) {
                sb.append("AND t_client.i_clientid IN (" + cids + ") ");
            } else {
                sb.append("AND t_client.i_clientid < 0 ");
            }
            if (!assetids.equals("")) {
                sb.append("AND t_clientasset.i_clientassetid IN (" + assetids + ") ");
            }
        }
        if (statusIndex > 0) {
            sb.append(" AND t_client.i_status = ? ");
        }
        if (clientIdIndex > 0) {
            sb.append("AND t_client.i_clientid = ? ");
        }
        if (assetIdIndex > 0) {
            sb.append("AND t_clientasset.i_clientassetid = ? ");
        }
        if (countryId > 0) {
            sb.append("AND t_country.i_countryid = ? ");
        }
        if (search != null && !search.equals("")) {
            sb.append(" AND (t_client.s_name LIKE ? OR t_clientasset.s_name LIKE ? OR t_country.s_name LIKE ?) ");
        }
        sb.append(" GROUP BY t_crewrotation.i_clientid, t_crewrotation.i_clientassetid ");

        String query = (sb.toString()).intern();
        sb.setLength(0);
        if (statusIndex > 0) {
            sb.append("AND t_client.i_status = ? ");
        }
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            int scc = 0;
            if (statusIndex > 0) {
                pstmt.setInt(++scc, statusIndex);
            }
            if (clientIdIndex > 0) {
                pstmt.setInt(++scc, clientIdIndex);
            }
            if (assetIdIndex > 0) {
                pstmt.setInt(++scc, assetIdIndex);
            }
            if (countryId > 0) {
                pstmt.setInt(++scc, countryId);
            }
            if (search != null && !search.equals("")) {
                pstmt.setString(++scc, search.replaceFirst("^0+(?!$)", ""));
                pstmt.setString(++scc, "%" + (search) + "%");
                pstmt.setString(++scc, "%" + (search) + "%");
            }
            logger.info("getListForExcel:: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String clientName, Country, clientAsset;
            int status1, normal, delayed, extended, clientId, clientassetId;
            while (rs.next()) {
                clientName = rs.getString(1) != null ? rs.getString(1) : "";
                clientAsset = rs.getString(2) != null ? rs.getString(2) : "";
                Country = rs.getString(3) != null ? rs.getString(3) : "";
                status1 = rs.getInt(4);
                normal = rs.getInt(5);
                delayed = rs.getInt(6);
                extended = rs.getInt(7);
                clientId = rs.getInt(8);
                clientassetId = rs.getInt(9);
                list.add(new CrewrotationInfo(clientName, clientAsset, Country, status1, normal, delayed, extended, clientId, clientassetId, 0, 0));
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return list;
    }

    public String changeDatesignonoff(String date) {
        String str = "0000-00-00 00:00";
        try {
            if (date != null && !date.equals("") && !date.equals("00-00-0000")) {
                SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MMM-yyyy HH:mm");
                SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                str = sdf2.format(sdf1.parse(date));
            }
        } catch (Exception e) {
            print(this, "Error in changeDate3 :: " + e.getMessage());
        }
        return str;
    }

    public int deleteCrActivity(int cractivityId, int crewrotationId, int uId, String username, int rota) {
        int cc = 0, count = 0;
        String query = "";
        try {
            conn = getConnection();
            query = "SELECT i_activityid, i_signonid, i_signoffid, i_status FROM t_cractivity WHERE i_cractivityid = ?";
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, cractivityId);
            logger.info("CrActivity :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            int activityId = 0, signonId = 0, signoffId = 0, status = 0;
            while (rs.next()) 
            {
                activityId = rs.getInt(1);
                signonId = rs.getInt(2);
                signoffId = rs.getInt(3);
                status = rs.getInt(4);
            }
            rs.close();
            
            String date = "";
            
            if (activityId != 6) 
            {
                query = "DELETE FROM t_cractivity WHERE i_cractivityid = ?";
                pstmt = conn.prepareStatement(query);
                pstmt.setInt(1, cractivityId);
                logger.info("deleteCrActivity1 :: " + pstmt.toString());
                cc = pstmt.executeUpdate();
                pstmt.close();
                if (cc > 0) {
                    query = "SELECT i_crewrotationid, COUNT(1) FROM t_cractivity WHERE i_activityid != 6 AND i_crewrotationid = ?";
                    pstmt = conn.prepareStatement(query);
                    pstmt.setInt(1, crewrotationId);
                    logger.info("CrActivity2 :: " + pstmt.toString());
                    rs = pstmt.executeQuery();
                    while (rs.next()) {
                        count = rs.getInt(2);
                    }
                    rs.close();                   
                    
                    if (count == 0) {
                        updatecrewrotationActivitydelete(crewrotationId, conn, 1, date, rota);
                    }
                }
                cc = 0;
            }
            else 
            {                
                if (activityId== 6 && signonId > 0 && signoffId <= 0) //only sign on
                {
                    query = "SELECT ts_date1 FROM t_signonoff WHERE i_signonoffid = ?";
                    pstmt = conn.prepareStatement(query);
                    pstmt.setInt(1, signonId);
                    logger.info("Activity Date 1 :: " + pstmt.toString());
                    rs = pstmt.executeQuery();
                    while (rs.next()) 
                    {
                        date = rs.getString(1) != null ? rs.getString(1): "";
                    }
                    rs.close();
                    
                    query = "DELETE FROM t_signonoff WHERE i_signonoffid = ?";
                    pstmt = conn.prepareStatement(query);
                    pstmt.setInt(1, signonId);
                    logger.info("deleteCrActivity2 :: " + pstmt.toString());
                    cc =pstmt.executeUpdate();
                    pstmt.close();
                    
                    query = "DELETE FROM t_cractivity WHERE i_cractivityid = ?";
                    pstmt = conn.prepareStatement(query);
                    pstmt.setInt(1, cractivityId);
                    logger.info("deleteCrActivity1 :: " + pstmt.toString());
                    cc = pstmt.executeUpdate();
                    pstmt.close();
                    
                    if(cc > 0){
                        updatecrewrotationActivitydelete(crewrotationId, conn, 1, date, rota);
                    }
                }                
                else if (signoffId > 0) //sign off
                { 
                    query = "SELECT ts_date1 FROM t_signonoff WHERE i_signonoffid = ?";
                    pstmt = conn.prepareStatement(query);
                    pstmt.setInt(1, signoffId);
                    logger.info("Activity Date 2:: " + pstmt.toString());
                    rs = pstmt.executeQuery();
                    while (rs.next()) 
                    {
                        date = rs.getString(1) != null ? rs.getString(1): "";
                    }
                    rs.close(); 
                    
                    query = "DELETE FROM t_signonoff WHERE i_signonoffid = ?";
                    pstmt = conn.prepareStatement(query);
                    pstmt.setInt(1, signoffId);
                    logger.info("deleteCrActivity3 :: " + pstmt.toString());
                    pstmt.executeUpdate();
                    pstmt.close();
                    
                    query = "UPDATE t_cractivity SET i_signoffid = 0, ts_moddate = ? WHERE i_cractivityid = ?";
                    pstmt = conn.prepareStatement(query);
                    pstmt.setString(1, currDate1());
                    pstmt.setInt(2, cractivityId);
                    logger.info("update cractivity when sign off :: " + pstmt.toString());
                    pstmt.executeUpdate();
                    pstmt.close();
                }
                if (cc > 0 && activityId == 6) 
                {
                    query = "SELECT i_crewrotationid, COUNT(1) FROM t_cractivity WHERE i_activityid = 6 AND i_crewrotationid = ?";
                    pstmt = conn.prepareStatement(query);
                    pstmt.setInt(1, crewrotationId);
                    print(this, "count query :: " + pstmt.toString());
                    rs = pstmt.executeQuery();
                    while (rs.next()) {
                        count = rs.getInt(2);
                    }
                    rs.close();

                    if (count == 0) {
                        updatecrewrotationActivitydelete(crewrotationId, conn, 2, date, rota);
                    }else{
                        updatecrewrotationActivitydelete(crewrotationId, conn, 1, date, rota);
                    }
                }
            }
        } catch (Exception exception) {
            print(this, "deleteCrActivity :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return count;
    }
 
    public void updatecrewrotationActivitydelete(int crewrotationId, Connection conn, int tp, String date, int rota) {
        try 
        {            
            StringBuilder sb = new StringBuilder();            
            sb.append("UPDATE t_crewrotation SET ");
            if (tp == 2)
            {
                sb.append("i_docflag = 1,");
                sb.append("ts_expecteddate = ? ,");
                sb.append("d_fromdate = ? ,");
                sb.append("d_todate = ? ,");
                sb.append("ts_signon = ? ,");
                sb.append("ts_signoff = ? ,");
                sb.append("s_signonlby = ? ,");
                sb.append("s_signofflby = ? ,");
                sb.append("s_activityby = ? ,");
                sb.append("s_remarks = ?,");
                sb.append("i_activityid = 0,");
                sb.append("i_status1 = 1,");
                sb.append("i_status2 = 0,");
                sb.append("i_sotype = 0,");
                sb.append("i_flag1 = 0,");
                sb.append("i_flag2 = 0,");
                sb.append("ts_moddate = ? ");
                sb.append("WHERE i_crewrotationid = ? ");
                String query = (sb.toString()).intern();
                sb.setLength(0);
                pstmt = conn.prepareStatement(query);
                int scc = 0;
                pstmt.setString(++scc, "0000-00-00 00:00:00");
                pstmt.setString(++scc, "0000-00-00");
                pstmt.setString(++scc, "0000-00-00");
                pstmt.setString(++scc, "0000-00-00 00:00:00");
                pstmt.setString(++scc, "0000-00-00 00:00:00");
                pstmt.setString(++scc, "");
                pstmt.setString(++scc, "");
                pstmt.setString(++scc, "");
                pstmt.setString(++scc, "");
                pstmt.setString(++scc, currDate1());
                pstmt.setInt(++scc, crewrotationId);
                print(this, "updatecrewrotationActivitydelete :: " + pstmt.toString());
                pstmt.executeUpdate();
            } else 
            {
                sb.append("d_fromdate = '0000-00-00',");
                sb.append("d_todate = '0000-00-00',");
                sb.append("s_remarks = '',");
                sb.append("i_activityid = 0,");
                sb.append("i_status1 = 1,");
                sb.append("i_status2 = 0,");
                sb.append("ts_signon = ? ,");
                sb.append("ts_signoff = ? ,");
                sb.append("ts_expecteddate = ? ,");
                sb.append("ts_moddate = ? ");
                sb.append("WHERE i_crewrotationid = ? ");
                String query = (sb.toString()).intern();
                sb.setLength(0);
                pstmt = conn.prepareStatement(query);
                pstmt.setString(1, "0000-00-00 00:00:00");
                pstmt.setString(2, "0000-00-00 00:00:00");
                if(rota == 1)
                    pstmt.setString(3, date);
                else
                    pstmt.setString(3, "0000-00-00 00:00:00");
                pstmt.setString(4, currDate1());
                pstmt.setInt(5, crewrotationId);
                print(this, "updatecrewrotation :: " + pstmt.toString());
                pstmt.executeUpdate();
            }
        } catch (Exception exception) {
            print(this, "updatecrewrotationActivitydelete :: " + exception.getMessage());
        } finally {
            close(null, pstmt, rs);
        }
    }

    public ArrayList getDetailForExcel(String search, int clientIdIndex, int assetIdIndex, int countryId,
            String crdate, int statusindex, int positionIndex, int activityDropdown, int dynamicId) {

        ArrayList list = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append(" SELECT c.i_candidateid , CONCAT_WS(' ', c.s_firstname, c.s_middlename, c.s_lastname),  t_position.s_name, t_grade.s_name ,c.s_photofilename, ");
        sb.append("t_client.s_name, t_clientasset.s_name, t_country.s_name, t_crewrotation.i_crewrotationid, ");
        sb.append("t_crewrotation.i_jobpostid , DATE_FORMAT( t_crewrotation.ts_signon, '%d-%b-%Y | %H:%i'),");
        sb.append("DATE_FORMAT( t_crewrotation.ts_expecteddate, '%d-%b-%Y | %H:%i'), t_crewrotation.i_docflag ,t_rotation.i_noofdays, ");
        sb.append("t_crewrotation.i_status1, t_crewrotation.i_status2 ,DATE_FORMAT( t_crewrotation.ts_expecteddate, '%d-%b-%Y | %H:%i'), ");
        sb.append("t_crewrotation.i_activityid, DATE_FORMAT( t_crewrotation.d_fromdate, '%d-%b-%Y'),DATE_FORMAT( t_crewrotation.d_todate, '%d-%b-%Y'), ");
        sb.append("t_crewrotation.s_remarks, t_crewrotation.i_active, t_position.i_rotationoverstay ");
        sb.append("FROM t_crewrotation ");
        sb.append("LEFT JOIN t_candidate AS c  ON (c.i_candidateid = t_crewrotation.i_candidateid) ");
        sb.append("LEFT JOIN t_client ON (t_client.i_clientid = t_crewrotation.i_clientid) ");
        sb.append("LEFT JOIN t_clientasset ON (t_clientasset.i_clientassetid = t_crewrotation.i_clientassetid) ");
        sb.append("LEFT JOIN t_position ON (t_position.i_positionid = c.i_positionid) ");
        sb.append("LEFT JOIN t_grade ON (t_grade.i_gradeid = t_position.i_gradeid) ");
        sb.append("LEFT JOIN t_country ON (t_country.i_countryid = t_clientasset.i_countryid) ");
        sb.append("LEFT JOIN t_rotation ON (t_position.i_rotationid = t_rotation.i_rotationid) ");
        sb.append(" WHERE t_crewrotation.i_active = 1 AND t_crewrotation.i_clientid = ? AND t_crewrotation.i_clientassetid = ? ");

        if (search != null && !search.equals("")) {
            sb.append(" AND (CONCAT_WS(' ', c.s_firstname, c.s_middlename, c.s_lastname) LIKE ? OR CONCAT_WS(' ', c.s_firstname, c.s_lastname) LIKE ? OR  c.s_firstname LIKE ?) ");
        }
        if (dynamicId == 1)//signon
        {
            if (crdate != null && !crdate.equals("")) {
                sb.append(" AND ( t_crewrotation.ts_signon LIKE ?  OR ( t_crewrotation.ts_expecteddate LIKE ?  AND t_crewrotation.i_status1 > 0)) ");
            }
        }
        if (dynamicId == 2)//signoff
        {
            if (crdate != null && !crdate.equals("")) {
                sb.append(" AND ( t_crewrotation.ts_signoff LIKE ? OR (t_crewrotation.ts_expecteddate LIKE ?  AND  t_crewrotation.i_status2 > 0)) ");
            }
        }
        if (statusindex == 1) {
            sb.append(" AND ( t_crewrotation.ts_signon = '0000-00-00 00:00:00' AND i_status1 = 1) ");
        } else if (statusindex == 2) {
            sb.append(" AND (t_crewrotation.i_status2 >= 1 && t_crewrotation.i_sotype = 1) ");
        } else if (statusindex == 3) {
            sb.append(" AND (t_crewrotation.i_status2 >= 1 && t_crewrotation.i_sotype = 2)  ");
        } else if (statusindex == 4) {
            sb.append(" AND (t_crewrotation.i_status2 >= 1 && t_crewrotation.i_sotype = 3)  ");
        } else if (statusindex == 5) {
            sb.append(" AND (t_crewrotation.i_status1 > 0)  ");
        } else if (statusindex == 6) {
            sb.append(" AND (t_crewrotation.i_status2 > 0)  ");
        }
        if (positionIndex > 0) {
            sb.append(" AND ( t_position.i_positionid = ? ) ");
        }
        if (activityDropdown > 0) {
            sb.append(" AND ( t_crewrotation.i_activityid = ? ) ");
        }

        String query = (sb.toString()).intern();
        sb.setLength(0);
        print(this, "query :: " + query);

        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            int scc = 0;
            pstmt.setInt(++scc, clientIdIndex);
            pstmt.setInt(++scc, assetIdIndex);
            if (search != null && !search.equals("")) {
                pstmt.setString(++scc, "%" + (search) + "%");
                pstmt.setString(++scc, "%" + (search) + "%");
                pstmt.setString(++scc, "%" + (search) + "%");
            }
            if (dynamicId == 1)//signon
            {
                if (crdate != null && !crdate.equals("")) {
                    pstmt.setString(++scc, "%" + changeDate1(crdate) + "%");
                    pstmt.setString(++scc, "%" + changeDate1(crdate) + "%");
                }
            }
            if (dynamicId == 2)//signoff
            {
                if (crdate != null && !crdate.equals("")) {
                    pstmt.setString(++scc, "%" + changeDate1(crdate) + "%");
                    pstmt.setString(++scc, "%" + changeDate1(crdate) + "%");
                }
            }
            if (positionIndex > 0) {
                pstmt.setInt(++scc, positionIndex);
            }
            if (activityDropdown > 0) {
                pstmt.setInt(++scc, activityDropdown);
            }
            logger.info("getDetailForExcel :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String clientName, Country, clientAsset, name, position, grade, photo, signon, signoff, expecteddate, fromdate, todate, remarks, activitystatus = "", offshorestatus = "";
            int crewrotationId, status1, status2, status = 0, candidateId, jobpostId, docflagId, noofdays, activityId, activeflag, overstaydays;
            while (rs.next()) {
                candidateId = rs.getInt(1);
                name = rs.getString(2) != null ? rs.getString(2) : "";
                position = rs.getString(3) != null ? rs.getString(3) : "";
                grade = rs.getString(4) != null ? rs.getString(4) : "";
                photo = rs.getString(5) != null ? rs.getString(5) : "";
                clientName = rs.getString(6) != null ? rs.getString(6) : "";
                clientAsset = rs.getString(7) != null ? rs.getString(7) : "";
                Country = rs.getString(8) != null ? rs.getString(8) : "";
                crewrotationId = rs.getInt(9);
                jobpostId = rs.getInt(10);
                signon = rs.getString(11) != null ? rs.getString(11) : "";
                signoff = rs.getString(12) != null ? rs.getString(12) : "";
                docflagId = rs.getInt(13);
                noofdays = rs.getInt(14);
                status1 = rs.getInt(15);
                status2 = rs.getInt(16);
                expecteddate = rs.getString(17) != null ? rs.getString(17) : "";
                activityId = rs.getInt(18);
                fromdate = rs.getString(19) != null ? rs.getString(19) : "";
                todate = rs.getString(20) != null ? rs.getString(20) : "";
                remarks = rs.getString(21) != null ? rs.getString(21) : "";
                activeflag = rs.getInt(22);
                overstaydays = rs.getInt(23);
                activitystatus = getactivityStatusbyId(activityId);
                offshorestatus = getCurrStVal(status1, status2, signon);
                if (status1 > 0) {
                    status = 1;
                } else if (status2 > 0) {
                    status = 2;
                    activitystatus = "";
                }
                if (!grade.equals("")) {
                    position += " - " + grade;
                }
                list.add(new CrewrotationInfo(crewrotationId, candidateId, jobpostId, name, clientName, position, photo, clientAsset,
                        Country, signon, signoff, docflagId, noofdays, status, expecteddate, activityId, fromdate, todate, remarks, activitystatus, 0, 0,
                        clientIdIndex, assetIdIndex, status1, status2, offshorestatus, activeflag, overstaydays,"",0));
            }
            rs.close();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return list;
    }

    public ArrayList getPlanningByclientandAssetName(int clientIdIndex, int assetIdIndex, String search, String fromDate, String toDate, int positionIdPlan, int statusPlan) {
        ArrayList list = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT CONCAT_WS(' ', c.s_firstname, c.s_middlename,c.s_lastname), t_position.s_name, t_grade.s_name, DATE_FORMAT(p.d_fromdate, '%d %b %Y'), ");
        sb.append("DATE_FORMAT(DATE_ADD(p.d_todate, INTERVAL -1 DAY), '%d %b %Y'), CASE WHEN p.i_type = 1 THEN 'OffShore' ELSE 'OnShore' END, DATEDIFF(p.d_todate, p.d_fromdate) ");
        sb.append("FROM t_planning AS p ");
        sb.append("LEFT JOIN t_crewrotation AS cr ON (cr.i_crewrotationid = p.i_crewrotationid) ");
        sb.append("LEFT JOIN t_candidate AS c ON (c.i_candidateid = cr.i_candidateid) ");
        sb.append("LEFT JOIN t_position ON (t_position.i_positionid = c.i_positionid) ");
        sb.append("LEFT JOIN t_grade ON (t_grade.i_gradeid = t_position.i_gradeid) ");
        sb.append("WHERE cr.i_active = 1 AND (p.d_fromdate > current_date() OR p.d_todate >= current_date()) AND cr.i_clientid = ? AND cr.i_clientassetid = ? ");
        if (statusPlan > 0) {
            sb.append(" AND p.i_type = ? ");
        }
        if (positionIdPlan > 0) {
            sb.append(" AND c.i_positionid = ? ");
        }
        if (fromDate != null && !fromDate.equals("") && toDate != null && !toDate.equals("")) {
            sb.append(" AND ((p.d_fromdate >= ? AND p.d_fromdate <= ?) OR (p.d_todate >= ? AND p.d_todate <= ?)) ");
        }
        if (search != null && !search.equals("")) {
            sb.append(" AND (CONCAT_WS(' ', c.s_firstname, c.s_middlename, c.s_lastname) LIKE ? OR CONCAT_WS(' ', c.s_firstname, c.s_lastname) LIKE ?) ");
        }

        sb.append(" ORDER BY t_position.s_name, t_grade.s_name, CONCAT_WS(' ', c.s_firstname, c.s_middlename,c.s_lastname), p.d_fromdate ");

        String query = (sb.toString()).intern();
        sb.setLength(0);
        print(this, "query :: " + query);

        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            int scc = 0;
            pstmt.setInt(++scc, clientIdIndex);
            pstmt.setInt(++scc, assetIdIndex);
            if (statusPlan > 0) {
                pstmt.setInt(++scc, statusPlan);
            }
            if (positionIdPlan > 0) {
                pstmt.setInt(++scc, positionIdPlan);
            }

            if (fromDate != null && !fromDate.equals("") && toDate != null && !toDate.equals("")) {
                pstmt.setString(++scc, changeDate1(fromDate));
                pstmt.setString(++scc, changeDate1(toDate));
                pstmt.setString(++scc, changeDate1(fromDate));
                pstmt.setString(++scc, changeDate1(toDate));
            }
            if (search != null && !search.equals("")) {
                pstmt.setString(++scc, "%" + (search) + "%");
                pstmt.setString(++scc, "%" + (search) + "%");
            }
            logger.info("getCandidatesByclientandAssetName :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String name, position, grade, fromdate, todate, typestatus;
            int dateDifference;
            String temp = "";
            while (rs.next()) {
                name = rs.getString(1) != null ? rs.getString(1) : "";
                position = rs.getString(2) != null ? rs.getString(2) : "";
                grade = rs.getString(3) != null ? rs.getString(3) : "";
                fromdate = rs.getString(4) != null ? rs.getString(4) : "";
                todate = rs.getString(5) != null ? rs.getString(5) : "";
                typestatus = rs.getString(6) != null ? rs.getString(6) : "";
                dateDifference = rs.getInt(7);
                if (!grade.equals("")) {
                    position += " - " + grade;
                }
                if (position.equals(temp)) {
                    list.add(new CrewrotationInfo(name, "", fromdate, todate, typestatus, dateDifference));
                } else {
                    list.add(new CrewrotationInfo(name, position, fromdate, todate, typestatus, dateDifference));
                    temp = position;
                }
            }
            rs.close();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return list;
    }

    public void createplan(int crewrotationIdParam) {
        PreparedStatement insert_pstmt = null;
        try {
            conn = getConnection();
            String checkq = "SELECT COUNT(1) FROM t_signonoff WHERE i_crewrotationid = ? AND i_type = 1 ";
            PreparedStatement pstmt_check = conn.prepareStatement(checkq);
            pstmt_check.setInt(1, crewrotationIdParam);
            rs = pstmt_check.executeQuery();
            logger.info("pstmt_check :: " + pstmt_check.toString());
            int count = 0;
            while (rs.next()) {
                count = rs.getInt(1);
            }
            rs.close();
            pstmt_check.close();
            if (count == 1) {
                String currdate = currDate1();
                int curryear = currYear();

                String insertq = "INSERT INTO t_planning (i_crewrotationid, d_fromdate, d_todate, i_type, i_status, ts_regdate, ts_moddate) VALUES (?,?,?,?,?,?,?) ";
                insert_pstmt = conn.prepareStatement(insertq);

                String delq = "DELETE FROM t_planning WHERE i_crewrotationid = ? ";
                PreparedStatement del_pstmt = conn.prepareStatement(delq);
                del_pstmt.setInt(1, crewrotationIdParam);
                del_pstmt.executeUpdate();
                del_pstmt.close();

                StringBuilder sb = new StringBuilder();
                sb.append("SELECT t_crewrotation.i_crewrotationid, i_status1, i_status2, DATE_FORMAT(ts_expecteddate, '%Y-%m-%d'), ");
                sb.append("t_rotation.i_noofdays ");
                sb.append("FROM t_crewrotation ");
                sb.append("LEFT JOIN t_candidate ON (t_candidate.i_candidateid = t_crewrotation.i_candidateid) ");
                sb.append("LEFT JOIN t_position ON (t_position.i_positionid = t_candidate.i_positionid) ");
                sb.append("LEFT JOIN t_rotation ON (t_rotation.i_rotationid = t_position.i_rotationid) ");
                sb.append("WHERE t_rotation.i_noofdays > 0 AND t_crewrotation.i_crewrotationid = ? ");
                String query = sb.toString();
                sb.setLength(0);
                PreparedStatement pstmtquery = conn.prepareStatement(query);
                pstmtquery.setInt(1, crewrotationIdParam);
                logger.info("pstmt_check :: " + pstmtquery.toString());
                rs = pstmtquery.executeQuery();
                while (rs.next()) {
                    int crewrotationId = rs.getInt(1);
                    int status1 = rs.getInt(2);
                    int status2 = rs.getInt(3);
                    String date = rs.getString(4) != null ? rs.getString(4) : "";
                    int noofdays = rs.getInt(5);
                    String startdate = date;
                    if (status2 > 0) {
                        date = getDateAfter(date, 1, noofdays, "yyyy-MM-dd", "yyyy-MM-dd");
                        insert_pstmt.setInt(1, crewrotationId);
                        insert_pstmt.setString(2, (startdate));
                        insert_pstmt.setString(3, (date));
                        insert_pstmt.setInt(4, 2);
                        insert_pstmt.setInt(5, 1);
                        insert_pstmt.setString(6, currdate);
                        insert_pstmt.setString(7, currdate);
                        print(this, "insert_pstmt status2 :: " + insert_pstmt.toString());
                        insert_pstmt.executeUpdate();
                    }
                    while (getDiffTwoDateInt(date, curryear + "-12-31", "yyyy-MM-dd") > 0) {
                        String todate = getDateAfter(date, 1, noofdays, "yyyy-MM-dd", "yyyy-MM-dd");
                        insert_pstmt.setInt(1, crewrotationId);
                        insert_pstmt.setString(2, (date));
                        insert_pstmt.setString(3, (todate));
                        insert_pstmt.setInt(4, 1);
                        insert_pstmt.setInt(5, 1);
                        insert_pstmt.setString(6, currdate);
                        insert_pstmt.setString(7, currdate);
                        print(this, "insert_pstmt :: " + insert_pstmt.toString());
                        insert_pstmt.executeUpdate();

                        date = getDateAfter(todate, 1, noofdays, "yyyy-MM-dd", "yyyy-MM-dd");

                        insert_pstmt.setInt(1, crewrotationId);
                        insert_pstmt.setString(2, (todate));
                        insert_pstmt.setString(3, (date));
                        insert_pstmt.setInt(4, 2);
                        insert_pstmt.setInt(5, 1);
                        insert_pstmt.setString(6, currdate);
                        insert_pstmt.setString(7, currdate);
                        print(this, "insert_pstmt +1 :: " + insert_pstmt.toString());
                        insert_pstmt.executeUpdate();
                    }
                }
                rs.close();
                insert_pstmt.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, null);
        }
    }

    public String getLastSignOffDate(int CrewrotationId) {
        String date = "";
        try {
            conn = getConnection();
            String checkq = "SELECT DATE_FORMAT(MAX(ts_date1), '%d-%b-%Y') FROM t_signonoff WHERE i_crewrotationid =? ";
            pstmt = conn.prepareStatement(checkq);
            pstmt.setInt(1, CrewrotationId);
            rs = pstmt.executeQuery();
            logger.info("getLastSignOffDate :: " + pstmt.toString());
            while (rs.next()) {
                date = rs.getString(1) != null ? rs.getString(1) : "";
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return date;
    }
    
    //
    public CrewrotationInfo getPositionsDetail(int crewrotationId) {
        CrewrotationInfo info = null;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT t_candidate.i_positionid, t_position.s_name,  t_grade.s_name,t_candidate.i_positionid2,p2.s_name, g2.s_name ");
        sb.append("FROM t_crewrotation ");
        sb.append("LEFT JOIN t_candidate  ON (t_candidate.i_candidateid = t_crewrotation.i_candidateid) ");
        sb.append("LEFT JOIN t_position ON (t_position.i_positionid = t_candidate.i_positionid) ");
        sb.append("LEFT JOIN t_grade ON (t_grade.i_gradeid = t_position.i_gradeid) ");
        sb.append("LEFT JOIN t_position AS p2 ON (p2.i_positionid = t_candidate.i_positionid2) ");
        sb.append("LEFT JOIN t_grade AS g2 ON (g2.i_gradeid = p2.i_gradeid) ");
        sb.append("WHERE t_crewrotation.i_crewrotationid = ? ");
        String query = sb.toString().intern();
        sb.setLength(0);
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, crewrotationId);
            print(this,"getPositionsDetail :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String position1, position2, grade1, grade2;
            int positionid, positionid2;
            while (rs.next()) 
            {
                positionid = rs.getInt(1);
                position1 = rs.getString(2) != null ? rs.getString(2): "";
                grade1 = rs.getString(3) != null ? rs.getString(3): "";
                positionid2 = rs.getInt(4);
                position2 = rs.getString(5) != null ? rs.getString(5): "";
                grade2 = rs.getString(6) != null ? rs.getString(6): "";
                if (!position1.equals("")) {
                    if (!grade1.equals("")) {
                        position1 += " | " + grade1;
                    }
                }
                if (!position2.equals("")) {
                    if (!grade2.equals("")) {
                        position2 += " | " + grade2;
                    }
                }
                info = new CrewrotationInfo(positionid, position1, positionid2, position2);
            }
            rs.close();
        } catch (Exception exception) {
            print(this, "getPositionsDetail :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return info;
    }
    
    public Collection getCrewPositionList(int crewrotionId) 
    {
        Collection coll = new LinkedList();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT DISTINCT t_position.i_positionid, CONCAT(t_position.s_name,' | ',t_grade.s_name) ");
        sb.append("FROM t_cractivity ");
        sb.append("LEFT JOIN t_position ON (t_position.i_positionid = t_cractivity.i_positionid) "); 
        sb.append("LEFT JOIN t_grade ON (t_grade.i_gradeid = t_position.i_gradeid) ");
        sb.append("WHERE t_cractivity.i_crewrotationId = ? AND t_cractivity.i_positionid > 0 ORDER BY t_position.s_name, t_grade.s_name ");
        String query = sb.toString();
        sb.setLength(0);
        coll.add(new CrewrotationInfo(-1, " Select Position "));
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, crewrotionId);
            //logger.info("getCrewPositionList :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            int ddlValue;
            String ddlLabel, pids = "";
            while (rs.next()) 
            {
                ddlValue = rs.getInt(1);
                ddlLabel = rs.getString(2) != null ? rs.getString(2) : "";
                if(pids.equals(""))
                    pids = ""+ddlValue;
                else
                    pids += ","+ddlValue;
                coll.add(new CrewrotationInfo(ddlValue, ddlLabel));
            }
            rs.close();
            
            sb.append("SELECT DISTINCT t_position.i_positionid, CONCAT(t_position.s_name,' | ',t_grade.s_name) ");
            sb.append("FROM t_cractivity ");
            sb.append("LEFT JOIN t_position ON (t_position.i_positionid = t_cractivity.i_positionid_temp) "); 
            sb.append("LEFT JOIN t_grade ON (t_grade.i_gradeid = t_position.i_gradeid) ");
            sb.append("WHERE t_cractivity.i_crewrotationId = ? ");
            if(!pids.equals(""))
                sb.append(" AND t_position.i_positionid NOT IN ("+pids+") ");
            sb.append("ORDER BY t_position.s_name, t_grade.s_name ");
            query = sb.toString();
            sb.setLength(0);
            
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, crewrotionId);
            //logger.info("getCrewPositionList2 :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            while (rs.next()) 
            {
                ddlValue = rs.getInt(1);
                ddlLabel = rs.getString(2) != null ? rs.getString(2) : "";
                coll.add(new CrewrotationInfo(ddlValue, ddlLabel));
            }
            rs.close();
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }

        return coll;
    }    
    
    public CrewrotationInfo getPositionIdForSignoff(int crewrotationId) {
        CrewrotationInfo info = null;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT t_cractivity.i_cractivityid, t_cractivity.i_positionid, t_rotation.i_noofdays ");
        sb.append("FROM t_cractivity ");
        sb.append("LEFT JOIN t_rotation ON (t_rotation.i_rotationid = t_rotation.i_rotationid) ");
        sb.append("WHERE t_cractivity.i_crewrotationId = ? AND t_cractivity.i_activityid =6 AND t_cractivity.i_signoffid <= 0 ORDER BY t_cractivity.ts_regdate DESC LIMIT 0,1 ");
        String query = sb.toString();
        sb.setLength(0);
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, crewrotationId);
            print(this, "getPositionIdForSignoff :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            int activityId, positionId, nooffdays;
            while (rs.next()) 
            {
                activityId = rs.getInt(1);
                positionId = rs.getInt(2);
                nooffdays = rs.getInt(3);
                info = new CrewrotationInfo(activityId, positionId, nooffdays);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }    
        return info;
    }
    
    public CrewrotationInfo getPositionIdActivity(int crewrotationId) {
        CrewrotationInfo info = null;
        String query = ("SELECT i_cractivityid, i_positionid, i_signonid , i_signoffid FROM t_cractivity WHERE i_crewrotationId = ? AND i_activityid= 6 AND i_positionid >0 ORDER BY ts_regdate DESC LIMIT 0,1").intern();
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, crewrotationId);
            print(this, "getPositionIdActivity :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            int cractivityId, positionId, signonId, signoffId;
            while (rs.next()) 
            {
                cractivityId = rs.getInt(1);
                positionId = rs.getInt(2);
                signonId = rs.getInt(3);
                signoffId = rs.getInt(4);
                info = new CrewrotationInfo(cractivityId, positionId, signonId, signoffId);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }    
        return info;
    }
    
    public int getNooffDaysForposition(int positionId) {
        int id = 0;        
        String query = ("SELECT t_rotation.i_noofdays FROM t_rotation LEFT JOIN t_position ON (t_rotation.i_rotationid = t_position.i_rotationid) WHERE t_position.i_positionid = ? ").intern();
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, positionId);
            print(this, "getNooffDaysForposition :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            while (rs.next()) 
            {
                id = rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }    
        return id;
    }
}
