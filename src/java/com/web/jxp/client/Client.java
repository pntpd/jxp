package com.web.jxp.client;

import com.mysql.jdbc.Statement;
import com.web.jxp.base.Base;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import static com.web.jxp.common.Common.*;
import com.web.jxp.experiencedept.ExperienceDept;
import com.web.jxp.relation.Relation;
import com.web.jxp.state.State;
import java.io.File;
import java.util.Collection;
import java.util.LinkedList;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

public class Client extends Base {

    private static final Logger logger = Logger.getLogger(Class.class.getName());
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    public ArrayList getClientByName(String search, int next, int count, int countryIndexId, int assettypeIndexId, int allclient, String permission, String cids, String assetids) {
        ArrayList list = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("select t_client.i_clientid, t_client.s_name, t_client.s_inchargename, t_client.s_email, ");
        sb.append("t_client.s_contact, t_client.i_status, t1.ct, t_client.s_isdcode ");
        sb.append("FROM t_client left join (select i_clientid, count(1) as ct from t_clientasset where i_status = 1 group by i_clientid) as t1 on (t_client.i_clientid = t1.i_clientid) ");
        sb.append(" left join t_clientasset on (t_client.i_clientid = t_clientasset.i_clientid) where 0 = 0 ");
        if (search != null && !search.equals("")) {
            sb.append("and (t_client.s_name like ? OR t_client.s_inchargename like ? OR t_client.i_clientid = ? OR t_client.s_email = ? OR t_client.s_contact = ?) ");
        }
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
        if (countryIndexId > 0) {
            sb.append("and t_client.i_countryid = ? ");
        }
        if (assettypeIndexId > 0) {
            sb.append("and t_clientasset.i_assettypeid = ? ");
        }
        sb.append(" group by t_client.i_clientid order by t_client.i_status, t_client.s_name ");
        if (count > 0) {
            sb.append(" limit ").append(next * count).append(", ").append(count);
        }
        String query = (sb.toString()).intern();
        sb.setLength(0);

        sb.append("select count(1) from (select count(1) FROM t_client left join t_clientasset on (t_client.i_clientid = t_clientasset.i_clientid) where 0 = 0 ");
        if (search != null && !search.equals("")) {
            sb.append("and (t_client.s_name like ? OR t_client.s_inchargename like ? OR t_client.i_clientid = ? OR t_client.s_email = ? OR t_client.s_contact = ?) ");
        }
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
        if (countryIndexId > 0) {
            sb.append("and t_client.i_countryid = ? ");
        }
        if (assettypeIndexId > 0) {
            sb.append("and t_clientasset.i_assettypeid = ? ");
        }
        sb.append(" group by t_client.i_clientid) as t1");
        String countquery = (sb.toString()).intern();
        sb.setLength(0);
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            int scc = 0;
            if (search != null && !search.equals("")) {
                pstmt.setString(++scc, "%" + (search) + "%");
                pstmt.setString(++scc, "%" + (search) + "%");
                pstmt.setString(++scc, search);
                pstmt.setString(++scc, search);
                pstmt.setString(++scc, search);
            }
            if (countryIndexId > 0) {
                pstmt.setInt(++scc, countryIndexId);
            }
            if (assettypeIndexId > 0) {
                pstmt.setInt(++scc, assettypeIndexId);
            }
            logger.info("getClientByName :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String name, inchargename, email, contact, ISDCode;
            int clientId, status, assetcount;
            while (rs.next()) {
                clientId = rs.getInt(1);
                name = rs.getString(2) != null ? rs.getString(2) : "";
                inchargename = rs.getString(3) != null ? rs.getString(3) : "";
                email = rs.getString(4) != null ? rs.getString(4) : "";
                contact = rs.getString(5) != null ? rs.getString(5) : "";
                status = rs.getInt(6);
                assetcount = rs.getInt(7);
                ISDCode = rs.getString(8) != null ? rs.getString(8) : "";

                if (!ISDCode.equals("")) {
                    contact = "+" + ISDCode + " " + contact;
                }

                list.add(new ClientInfo(clientId, name, inchargename, email,
                        contact, assetcount, status, ISDCode));
            }
            rs.close();

            pstmt = conn.prepareStatement(countquery);
            scc = 0;
            if (search != null && !search.equals("")) {
                pstmt.setString(++scc, "%" + (search) + "%");
                pstmt.setString(++scc, "%" + (search) + "%");
                pstmt.setString(++scc, search);
                pstmt.setString(++scc, search);
                pstmt.setString(++scc, search);
            }
            if (countryIndexId > 0) {
                pstmt.setInt(++scc, countryIndexId);
            }
            if (assettypeIndexId > 0) {
                pstmt.setInt(++scc, assettypeIndexId);
            }
            rs = pstmt.executeQuery();
            while (rs.next()) {
                clientId = rs.getInt(1);
                list.add(new ClientInfo(clientId, "", "", "", "", 0, 0, ""));
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
        ClientInfo info;
        int total = 0;
        if (l != null) {
            total = l.size();
        }
        try {
            if (total > 0) {
                record = new HashMap();
                int i;
                for (i = 0; i < total; i++) {
                    info = (ClientInfo) l.get(i);
                    record.put(getInfoValue(info, colId), info);
                }
            }
            Map map;
            if (colId.equals("1")) {
                map = sortById(record, tp);
            } else {
                map = sortByName(record, tp);
            }
            Iterator it = map.keySet().iterator();
            list = new ArrayList();
            ClientInfo rInfo;
            while (it.hasNext()) {
                String key = (String) it.next();
                int i;
                for (i = 0; i < total; i++) {
                    rInfo = (ClientInfo) l.get(i);
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

    //for  clientasset
    public ArrayList getFinalRecordCA(ArrayList l, String colId, int tp) {
        ArrayList list = null;
        HashMap record = null;
        ClientInfo info;
        int total = 0;
        if (l != null) {
            total = l.size();
        }
        try {
            if (total > 0) {
                record = new HashMap();
                int i;
                for (i = 0; i < total; i++) {
                    info = (ClientInfo) l.get(i);
                    record.put(getInfoValueCA(info, colId), info);
                }
            }
            Map map;
            if (colId.equals("1")) {
                map = sortById(record, tp);
            } else {
                map = sortByName(record, tp);
            }
            Iterator it = map.keySet().iterator();
            list = new ArrayList();
            ClientInfo rInfo;
            while (it.hasNext()) {
                String key = (String) it.next();
                int i;
                for (i = 0; i < total; i++) {
                    rInfo = (ClientInfo) l.get(i);
                    String str = getInfoValueCA(rInfo, colId);
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

    // to sort client asset result
    public String getInfoValueCA(ClientInfo info, String i) {
        String infoval = "";
        if (i != null && i.equals("1")) {
            infoval = info.getClientassetId() + "";
        } else if (i != null && i.equals("2")) {
            infoval = info.getAssettypeName() != null ? info.getAssettypeName() : "";
        } else if (i != null && i.equals("3")) {
            infoval = info.getName() != null ? info.getName() : "";
        } else if (i != null && i.equals("4")) {
            infoval = info.getCountryName() != null ? info.getCountryName() : "";
        } else if (i != null && i.equals("5")) {
            infoval = info.getDeptcount() + "";
        } else if (i != null && i.equals("6")) {
            infoval = info.getPositionCount() + "";
        }
        return infoval;
    }

    // to sort search result
    public String getInfoValue(ClientInfo info, String i) {
        String infoval = "";
        if (i != null && i.equals("1")) {
            infoval = info.getClientId() + "";
        } else if (i != null && i.equals("2")) {
            infoval = info.getName() != null ? info.getName() : "";
        } else if (i != null && i.equals("3")) {
            infoval = info.getInchargename() != null ? info.getInchargename() : "";
        } else if (i != null && i.equals("4")) {
            infoval = info.getEmail() != null ? info.getEmail() : "";
        } else if (i != null && i.equals("5")) {
            infoval = info.getContact() != null ? info.getContact() : "";
        }
        return infoval;
    }

    public ArrayList getUsers() 
    {
        ArrayList list = new ArrayList();
        String query = "SELECT i_userid, s_name FROM t_userlogin where i_status = 1 order by s_name";
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            rs = pstmt.executeQuery();
            int id;
            String name;
            while (rs.next()) {
                id = rs.getInt(1);
                name = rs.getString(2) != null ? rs.getString(2) : "";
                list.add(new ClientInfo(id, name));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return list;
    }

    public ClientInfo getClientDetailById(int clientId) {
        ClientInfo info = null;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT s_name, s_headofficeaddress, i_countryid, s_inchargename, s_position, s_email, s_contact, ");
        sb.append("DATE_FORMAT(d_date, '%d-%b-%Y'), s_link1, s_link2, s_link3, s_link4, s_link5, s_link6, ");
        sb.append("s_ocsuserids, i_status,s_isdcode, s_shortname, s_colorcode, s_mids, s_rids FROM t_client WHERE i_clientid = ? ");
        String query = sb.toString().intern();
        sb.setLength(0);
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, clientId);
            print(this, "getClientDetailById :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String name, headofficeaddress, inchargename, position, email, contact, date, link1, link2,
                    link3, link4, link5, link6, ISDcode, ocsuserIds, shortName, colorCode, mids, rids;
            int countryId, status;
            while (rs.next()) {
                name = rs.getString(1) != null ? rs.getString(1) : "";
                headofficeaddress = rs.getString(2) != null ? rs.getString(2) : "";
                countryId = rs.getInt(3);
                inchargename = rs.getString(4) != null ? rs.getString(4) : "";
                position = rs.getString(5) != null ? rs.getString(5) : "";
                email = rs.getString(6) != null ? rs.getString(6) : "";
                contact = rs.getString(7) != null ? rs.getString(7) : "";
                date = rs.getString(8) != null ? rs.getString(8) : "";
                link1 = rs.getString(9) != null ? rs.getString(9) : "";
                link2 = rs.getString(10) != null ? rs.getString(10) : "";
                link3 = rs.getString(11) != null ? rs.getString(11) : "";
                link4 = rs.getString(12) != null ? rs.getString(12) : "";
                link5 = rs.getString(13) != null ? rs.getString(13) : "";
                link6 = rs.getString(14) != null ? rs.getString(14) : "";
                ocsuserIds = rs.getString(15) != null ? rs.getString(15) : "";
                status = rs.getInt(16);
                ISDcode = rs.getString(17) != null ? rs.getString(17) : "";
                shortName = rs.getString(18) != null ? rs.getString(18) : "";
                colorCode = rs.getString(19) != null ? rs.getString(19) : "";
                mids = rs.getString(20) != null ? rs.getString(20) : "";
                rids = rs.getString(21) != null ? rs.getString(21) : "";
                
                info = new ClientInfo(clientId, name, headofficeaddress, countryId, inchargename, position, 
                        email, contact, date, link1, link2, link3, link4, link5, link6, ocsuserIds, status, 
                        ISDcode, shortName, colorCode, mids, rids);
            }
        } catch (Exception exception) {
            print(this, "getClientDetailById :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return info;
    }

    public ClientInfo getClientDetailByIdforDetail(int clientId) {
        ClientInfo info = null;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT c.s_name, c.s_headofficeaddress, t_country.s_name, c.s_inchargename, c.s_position, c.s_email, c.s_contact, ");
        sb.append("DATE_FORMAT(c.d_date, '%d-%b-%Y'), c.s_link1, c.s_link2, c.s_link3, c.s_link4, c.s_link5, c.s_link6, ");
        sb.append("t4.names, c.i_status,c.s_isdcode, c.s_shortname, c.s_colorcode, c.s_ocsuserids, t6.names2, t7.names3 ");
        sb.append("FROM t_client AS c ");
        sb.append("LEFT JOIN t_country ON (t_country.i_countryid = c.i_countryid) ");
        sb.append("LEFT JOIN (select t_client.i_clientid, GROUP_CONCAT(DISTINCT t3.s_name ORDER BY t3.s_name SEPARATOR ', ') AS names FROM t_client ");
        sb.append("LEFT JOIN t_userlogin AS t3 ON FIND_IN_SET(t3.i_userid, t_client.s_ocsuserids) group by t_client.i_clientid) AS t4 ON (t4.i_clientid = c.i_clientid) ");
        sb.append("LEFT JOIN (select t_client.i_clientid, GROUP_CONCAT(DISTINCT t5.s_name ORDER BY t5.s_name SEPARATOR ', ') AS names2 FROM t_client ");
        sb.append("LEFT JOIN t_userlogin AS t5 ON FIND_IN_SET(t5.i_userid, t_client.s_mids) GROUP BY t_client.i_clientid) AS t6 ON (t6.i_clientid = c.i_clientid) ");        
        sb.append("LEFT JOIN (SELECT t_client.i_clientid, GROUP_CONCAT(DISTINCT t7.s_name ORDER BY t7.s_name SEPARATOR ', ') AS names3 FROM t_client ");
        sb.append("LEFT JOIN t_userlogin AS t7 ON FIND_IN_SET(t7.i_userid, t_client.s_rids) GROUP BY t_client.i_clientid) AS t7 ON (t7.i_clientid = c.i_clientid) ");        
        sb.append("WHERE c.i_clientid = ? ");
        String query = sb.toString().intern();
        sb.setLength(0);
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, clientId);
            print(this, "getClientDetailByIdforDetail :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String name, headofficeaddress, inchargename, position, email, contact, date, link1, link2, link3,
                    link4, link5, link6, countryName, userName, ISDcode, shortName, colorCode, ocsIds, mids, rids;
            int status;
            while (rs.next()) 
            {
                name = rs.getString(1) != null ? rs.getString(1) : "";
                headofficeaddress = rs.getString(2) != null ? rs.getString(2) : "";
                countryName = rs.getString(3) != null ? rs.getString(3) : "";
                inchargename = rs.getString(4) != null ? rs.getString(4) : "";
                position = rs.getString(5) != null ? rs.getString(5) : "";
                email = rs.getString(6) != null ? rs.getString(6) : "";
                contact = rs.getString(7) != null ? rs.getString(7) : "";
                date = rs.getString(8) != null ? rs.getString(8) : "";
                link1 = rs.getString(9) != null ? rs.getString(9) : "";
                link2 = rs.getString(10) != null ? rs.getString(10) : "";
                link3 = rs.getString(11) != null ? rs.getString(11) : "";
                link4 = rs.getString(12) != null ? rs.getString(12) : "";
                link5 = rs.getString(13) != null ? rs.getString(13) : "";
                link6 = rs.getString(14) != null ? rs.getString(14) : "";
                userName = rs.getString(15) != null ? rs.getString(15) : "";
                status = rs.getInt(16);
                ISDcode = rs.getString(17) != null ? rs.getString(17) : "";
                shortName = rs.getString(18) != null ? rs.getString(18) : "";
                colorCode = rs.getString(19) != null ? rs.getString(19) : "";
                ocsIds = rs.getString(20) != null ? rs.getString(20) : "";
                mids = rs.getString(21) != null ? rs.getString(21) : "";
                rids = rs.getString(22) != null ? rs.getString(22) : "";
                
                info = new ClientInfo(clientId, name, headofficeaddress, countryName, inchargename, position, email, 
                        contact, date, link1, link2, link3, link4, link5, link6, userName, status, ISDcode, 
                        shortName, colorCode, ocsIds, mids, rids);
            }
        } catch (Exception exception) {
            print(this, "getClientDetailByIdforDetail :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return info;
    }

    public int createClient(ClientInfo info) {
        int clientId = 0;
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("INSERT INTO t_client ");
            sb.append("(s_name, s_headofficeaddress, i_countryid, s_inchargename, s_position, s_email, s_isdcode, s_contact, d_date, s_ocsuserids , ");
            sb.append("s_link1, s_link2, s_link3, s_link4, s_link5, s_link6, s_shortname, s_colorcode, s_mids, s_rids, i_status, i_userid, ts_regdate, ts_moddate) ");
            sb.append("VALUES (?, ?, ?, ?, ?,   ?, ?, ?, ?, ?,   ?, ?, ?, ?, ?,   ?, ?,?,?,?,  ?,?, ?, ?)");
            String query = (sb.toString()).intern();
            sb.setLength(0);
            conn = getConnection();
            pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            int scc = 0;
            pstmt.setString(++scc, (info.getName()));
            pstmt.setString(++scc, (info.getHeadofficeaddress()));
            pstmt.setInt(++scc, (info.getCountryId()));
            pstmt.setString(++scc, (info.getInchargename()));
            pstmt.setString(++scc, (info.getPosition()));
            pstmt.setString(++scc, info.getEmail());
            pstmt.setString(++scc, info.getISDcode());
            pstmt.setString(++scc, info.getContact());
            pstmt.setString(++scc, changeDate1(info.getDate()));
            pstmt.setString(++scc, info.getOcsuserIds());
            pstmt.setString(++scc, info.getLink1());
            pstmt.setString(++scc, info.getLink2());
            pstmt.setString(++scc, info.getLink3());
            pstmt.setString(++scc, info.getLink4());
            pstmt.setString(++scc, info.getLink5());
            pstmt.setString(++scc, info.getLink6());
            pstmt.setString(++scc, info.getShortName());
            pstmt.setString(++scc, info.getColorCode());
            pstmt.setString(++scc, info.getMids());
            pstmt.setString(++scc, info.getRids());
            pstmt.setInt(++scc, info.getStatus());
            pstmt.setInt(++scc, info.getUserId());
            pstmt.setString(++scc, currDate1());
            pstmt.setString(++scc, currDate1());
            print(this, "createClient :: " + pstmt.toString());
            pstmt.executeUpdate();
            rs = pstmt.getGeneratedKeys();
            while (rs.next()) {
                clientId = rs.getInt(1);
            }
        } catch (Exception exception) {
            print(this, "createClient :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return clientId;
    }

    public int updateClient(ClientInfo info) {
        int cc = 0;
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("UPDATE t_client SET ");
            sb.append("s_name = ?, ");
            sb.append("s_headofficeaddress = ?, ");
            sb.append("i_countryid = ?, ");
            sb.append("s_inchargename = ?, ");
            sb.append("s_position = ?, ");
            sb.append("s_email = ?, ");
            sb.append("s_isdcode = ?, ");
            sb.append("s_contact = ?, ");
            sb.append("d_date = ?, ");
            sb.append("s_ocsuserids = ?, ");
            sb.append("s_link1 = ?, ");
            sb.append("s_link2 = ?, ");
            sb.append("s_link3 = ?, ");
            sb.append("s_link4 = ?, ");
            sb.append("s_link5 = ?, ");
            sb.append("s_link6 = ?, ");
            sb.append("s_shortname = ?, ");
            sb.append("s_colorcode = ?, ");
            sb.append("s_mids = ?, ");
            sb.append("s_rids = ?, ");
            sb.append("i_status = ?, ");
            sb.append("i_userid = ?, ");
            sb.append("ts_moddate = ? ");
            sb.append("WHERE i_clientid = ? ");
            String query = (sb.toString()).intern();
            sb.setLength(0);
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            int scc = 0;
            pstmt.setString(++scc, info.getName());
            pstmt.setString(++scc, info.getHeadofficeaddress());
            pstmt.setInt(++scc, info.getCountryId());
            pstmt.setString(++scc, info.getInchargename());
            pstmt.setString(++scc, info.getPosition());
            pstmt.setString(++scc, info.getEmail());
            pstmt.setString(++scc, info.getISDcode());
            pstmt.setString(++scc, info.getContact());
            pstmt.setString(++scc, changeDate1(info.getDate()));
            pstmt.setString(++scc, info.getOcsuserIds());
            pstmt.setString(++scc, info.getLink1());
            pstmt.setString(++scc, info.getLink2());
            pstmt.setString(++scc, info.getLink3());
            pstmt.setString(++scc, info.getLink4());
            pstmt.setString(++scc, info.getLink5());
            pstmt.setString(++scc, info.getLink6());
            pstmt.setString(++scc, info.getShortName());
            pstmt.setString(++scc, info.getColorCode());
            pstmt.setString(++scc, info.getMids());
            pstmt.setString(++scc, info.getRids());
            pstmt.setInt(++scc, info.getStatus());
            pstmt.setInt(++scc, info.getUserId());
            pstmt.setString(++scc, currDate1());
            pstmt.setInt(++scc, info.getClientId());
            print(this,"updateClient :: " + pstmt.toString());
            cc = pstmt.executeUpdate();
        } catch (Exception exception) {
            print(this, "updateClient :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return cc;
    }

    public int checkDuplicacy(int clientId, String name) {
        int ck = 0;
        StringBuilder sb = new StringBuilder();
        sb.append("select i_clientid FROM t_client where s_name = ?  and i_status in (1, 2)");
        if (clientId > 0) {
            sb.append(" and i_clientid != ? ");
        }
        String query = (sb.toString()).intern();
        sb.setLength(0);

        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            int scc = 0;
            pstmt.setString(++scc, name);
            if (clientId > 0) {
                pstmt.setInt(++scc, clientId);
            }
            //print(this,"checkDuplicacy :: " + pstmt.toString());
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

    public Collection getClients() {
        Collection coll = new LinkedList();
        String query = ("SELECT i_clientid, s_name FROM t_client where i_status = 1 order by s_name").intern();
        coll.add(new ClientInfo(-1, "- Select -"));
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            rs = pstmt.executeQuery();
            int refId;
            String refName;
            while (rs.next()) {
                refId = rs.getInt(1);
                refName = rs.getString(2) != null ? rs.getString(2) : "";
                coll.add(new ClientInfo(refId, refName));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return coll;
    }

    public int deleteClient(int clientId, int userId, int status, String ipAddrStr, String iplocal) {
        int cc = 0;
        try {
            conn = getConnection();
            StringBuilder sb = new StringBuilder();
            sb.append("update t_client set ");
            sb.append("i_status = ?, ");
            sb.append("i_userid = ?, ");
            sb.append("ts_moddate = ? ");
            sb.append("where i_clientid = ? ");
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
            pstmt.setInt(++scc, clientId);

            cc = pstmt.executeUpdate();
        } catch (Exception exception) {
            print(this, "updateClient :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        createHistoryAccess(null, userId, ipAddrStr, iplocal, "Data Deleted", 2, clientId);
        return cc;
    }

    public ArrayList getAssetLIst(int clientId, int allclient, String permission, String cids, String assetids) {
        ArrayList list = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("Select t_clientasset.i_clientassetid, t_clientasset.s_name, t_clientasset.s_description, ");
        sb.append("t_clientasset.i_status, t_country.s_name, t_assettype.s_name,t1.ct, t_assettype.i_assettypeid, t2.ct ");
        sb.append("FROM t_clientasset left join t_country on (t_country.i_countryid = t_clientasset.i_countryid) ");
        sb.append("left join t_assettype on (t_assettype.i_assettypeid = t_clientasset.i_assettypeid) ");
        sb.append("left join (select i_clientassetid, count(1) as ct from t_clientassetposition group by t_clientassetposition.i_clientassetid) as t1 on (t1.i_clientassetid = t_clientasset.i_clientassetid) ");
        sb.append("left join (select i_clientassetid, SUM(if(t_clientassetposition.i_pdeptid > 0, 1, 0)) as ct from t_clientassetposition left join t_pdept on (t_pdept.i_pdeptid = t_clientassetposition.i_pdeptid) where t_pdept.i_status = 1 and t_clientassetposition.i_status = 1 group by t_clientassetposition.i_clientassetid) as t2 on (t2.i_clientassetid = t_clientasset.i_clientassetid) ");
        sb.append("where t_clientasset.i_clientid = ? ");
        if (allclient == 1) {
            sb.append(" AND t_clientasset.i_clientid > 0 ");
        } else {
            if (!assetids.equals("")) {
                sb.append("AND t_clientasset.i_clientassetid IN (" + assetids + ") ");
            }
        }
        sb.append(" order by t_clientasset.i_status, t_clientasset.s_name ");
        String query = (sb.toString()).intern();
        sb.setLength(0);
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, clientId);
            logger.info("getAssetLIst :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String name, description, countryName, assettypeName;
            int clientassetId, status, positionCount, assettypeId, deptcount;
            while (rs.next()) {
                clientassetId = rs.getInt(1);
                name = rs.getString(2) != null ? rs.getString(2) : "";
                description = rs.getString(3) != null ? rs.getString(3) : "";
                status = rs.getInt(4);
                countryName = rs.getString(5) != null ? rs.getString(5) : "";
                assettypeName = rs.getString(6) != null ? rs.getString(6) : "";
                positionCount = rs.getInt(7);
                assettypeId = rs.getInt(8);
                deptcount = rs.getInt(9);
                list.add(new ClientInfo(clientassetId, name, description, countryName, assettypeName,
                        positionCount, status, 0, assettypeId, deptcount));
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return list;
    }

    public int createClientAsset(ClientInfo info, int clientId, int userId) {
        int id = 0;
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("INSERT INTO t_clientasset ");
            sb.append("(i_clientid, i_countryid, i_assettypeid, s_name, s_description, i_portid, s_deliveryyear, s_assetflag, ");
            sb.append("s_classification, s_berths, s_lifeboat, s_url, s_url_training, s_helpno, s_helpemail, i_currencyid, ");
            sb.append("i_status, i_userid, ts_regdate, ts_moddate, s_ratetype, i_rflag, i_mflag, d_allowance) ");
            sb.append("VALUES (?,?,?,?,?,   ?,?,?,?,?,  ?,?,?,?,?,  ?,?,?,?,?,  ?,?,?,  ?)");
            String query = (sb.toString()).intern();
            sb.setLength(0);
            conn = getConnection();
            pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            int scc = 0;
            pstmt.setInt(++scc, clientId);
            pstmt.setInt(++scc, info.getCountryId());
            pstmt.setInt(++scc, info.getAssettypeId());
            pstmt.setString(++scc, info.getName());
            pstmt.setString(++scc, info.getDescription());

            pstmt.setInt(++scc, info.getPortId());
            pstmt.setString(++scc, info.getDeliveryYear());
            pstmt.setString(++scc, info.getAssetFlag());
            pstmt.setString(++scc, info.getClassification());
            pstmt.setString(++scc, info.getBerths());

            pstmt.setString(++scc, info.getLifeboat());
            pstmt.setString(++scc, info.getUrl());
            pstmt.setString(++scc, info.getUrl_training());
            pstmt.setString(++scc, info.getHelpno());
            pstmt.setString(++scc, info.getHelpemail());

            pstmt.setInt(++scc, info.getCurrencyId());
            pstmt.setInt(++scc, info.getStatus());
            pstmt.setInt(++scc, userId);
            pstmt.setString(++scc, currDate1());
            pstmt.setString(++scc, currDate1());

            pstmt.setString(++scc, info.getRatetype());
            pstmt.setInt(++scc, info.getCrewrota());
            pstmt.setInt(++scc, info.getStartrota());
            pstmt.setDouble(++scc, info.getAllowance());            
            print(this, "createClientAsset :: " + pstmt.toString());
            pstmt.executeUpdate();
            rs = pstmt.getGeneratedKeys();
            while (rs.next()) {
                id = rs.getInt(1);
            }
            if (id > 0) {
                insertonadd(conn, id, info.getAssettypeId(), userId);
            }
        } catch (Exception exception) {
            print(this, "createClientAsset :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return id;
    }

    public void insertonadd(Connection conn, int clientassetId, int assettypeId, int userId) {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("INSERT INTO t_clientassetposition ");
            sb.append("(i_positionid, i_clientassetid, i_status, i_userid, ts_regdate) ");
            sb.append("VALUES (?, ?, ?, ?, ?)");
            String query1 = sb.toString();
            sb.setLength(0);

            PreparedStatement insertpstmt1 = conn.prepareStatement(query1);

            String query = "SELECT i_positionid  FROM t_position WHERE i_status = 1 and i_assettypeid = ?";
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, assettypeId);
            logger.info("insertonadd query1 :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String currdate = currDate1();
            while (rs.next()) {
                int positionId = rs.getInt(1);

                insertpstmt1.setInt(1, positionId);
                insertpstmt1.setInt(2, clientassetId);
                insertpstmt1.setInt(3, 1);
                insertpstmt1.setInt(4, userId);
                insertpstmt1.setString(5, currdate);
                insertpstmt1.executeUpdate();
            }
            rs.close();
            insertpstmt1.close();
        } catch (Exception exception) {
            print(this, "insertonadd :: " + exception.getMessage());
        } finally {
            close(null, pstmt, rs);
        }
    }

    public int updateClientAsset(ClientInfo info, int userId) {
        int cc = 0;
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("update t_clientasset set ");
            sb.append("i_countryid = ?, ");
            sb.append("i_assettypeid = ?, ");
            sb.append("s_name = ?, ");
            sb.append("s_description = ?, ");
            sb.append("i_portid = ?, ");
            sb.append("s_deliveryyear = ?, ");
            sb.append("s_assetflag = ?, ");
            sb.append("s_classification = ?, ");
            sb.append("s_berths = ?, ");
            sb.append("s_lifeboat = ?, ");
            sb.append("s_url = ?, ");
            sb.append("s_url_training = ?, ");
            sb.append("s_helpno = ?, ");
            sb.append("s_helpemail = ?, ");
            sb.append("i_currencyid = ?, ");
            sb.append("i_status = ?, ");
            sb.append("s_ratetype = ?, ");
            sb.append("i_rflag = ?, ");
            sb.append("i_mflag = ?, ");
            sb.append("i_userid = ?, ");
            sb.append("d_allowance = ?, ");
            sb.append("ts_moddate = ? ");
            sb.append("where i_clientassetid = ? ");
            String query = (sb.toString()).intern();
            sb.setLength(0);
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            int scc = 0;
            pstmt.setInt(++scc, info.getCountryId());
            pstmt.setInt(++scc, info.getAssettypeId());
            pstmt.setString(++scc, info.getName());
            pstmt.setString(++scc, info.getDescription());
            pstmt.setInt(++scc, info.getPortId());
            pstmt.setString(++scc, info.getDeliveryYear());
            pstmt.setString(++scc, info.getAssetFlag());
            pstmt.setString(++scc, info.getClassification());
            pstmt.setString(++scc, info.getBerths());
            pstmt.setString(++scc, info.getLifeboat());
            pstmt.setString(++scc, info.getUrl());
            pstmt.setString(++scc, info.getUrl_training());
            pstmt.setString(++scc, info.getHelpno());
            pstmt.setString(++scc, info.getHelpemail());
            pstmt.setInt(++scc, info.getCurrencyId());
            pstmt.setInt(++scc, info.getStatus());
            pstmt.setString(++scc, info.getRatetype());
            pstmt.setInt(++scc, info.getCrewrota());
            pstmt.setInt(++scc, info.getStartrota());
            pstmt.setInt(++scc, userId);
            pstmt.setDouble(++scc, info.getAllowance());
            pstmt.setString(++scc, currDate1());
            pstmt.setInt(++scc, info.getClientassetId());
            print(this, "updateClientAsset :: " + pstmt.toString());
            cc = pstmt.executeUpdate();
        } catch (Exception exception) {
            print(this, "updateClient :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return cc;
    }

    public int checkDuplicacyAsset(int clientassetId, int clientId, String name) {
        int ck = 0;
        StringBuilder sb = new StringBuilder();
        sb.append("select i_clientassetid FROM t_clientasset where i_clientid = ? and s_name = ? and i_status in (1, 2)");
        if (clientassetId > 0) {
            sb.append(" and i_clientassetid != ? ");
        }
        String query = (sb.toString()).intern();
        sb.setLength(0);
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            int scc = 0;
            pstmt.setInt(++scc, clientId);
            pstmt.setString(++scc, name);
            if (clientassetId > 0) {
                pstmt.setInt(++scc, clientassetId);
            }
            print(this, "checkDuplicacyAsset :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ck = 1;
            }
        } catch (Exception exception) {
            print(this, "checkDuplicacyAsset :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return ck;
    }

    public int deleteClientAsset(int clientassetId, int status, int userId) {
        int cc = 0;
        try {
            conn = getConnection();
            StringBuilder sb = new StringBuilder();
            sb.append("update t_clientasset set ");
            sb.append("i_status = ?, ");
            sb.append("i_userid = ?, ");
            sb.append("ts_moddate = ? ");
            sb.append("where i_clientassetid = ? ");
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
            pstmt.setInt(++scc, clientassetId);

            cc = pstmt.executeUpdate();
        } catch (Exception exception) {
            print(this, "updateClient :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return cc;
    }

    public Collection getAssettypes() {
        Collection coll = new LinkedList();
        String query = "SELECT i_assettypeid, s_name FROM t_assettype where i_status = 1 order by s_name";
        coll.add(new ClientInfo(-1, "- Select -"));
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            rs = pstmt.executeQuery();
            int id;
            String name;
            while (rs.next()) {
                id = rs.getInt(1);
                name = rs.getString(2) != null ? rs.getString(2) : "";
                coll.add(new ClientInfo(id, name));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return coll;
    }

    public ClientInfo getAssetForModify(int clientassetId) {
        ClientInfo info = null;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT i_clientassetid, s_name, s_description, i_portid,s_deliveryyear,s_assetflag, ");
        sb.append("s_classification,s_berths,s_lifeboat, i_status, i_countryid, i_assettypeid, s_url, ");
        sb.append("s_url_training, s_helpno, s_helpemail, i_currencyid, s_ratetype, i_rflag, i_mflag, d_allowance ");
        sb.append("FROM t_clientasset WHERE i_clientassetid = ? ");
        String query = (sb.toString()).intern();
        sb.setLength(0);
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, clientassetId);
            logger.info("getAssetForModify :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String name, description, deliveryyear, assetflag, classification, berths, lifeboat, url,
                    url_training, helpno, helpemail, ratetype;
            int status, countryId, assettypeId, portId, currencyId, crewrota, startrota;
            double allowance;
            while (rs.next()) {
                clientassetId = rs.getInt(1);
                name = rs.getString(2) != null ? rs.getString(2) : "";
                description = rs.getString(3) != null ? rs.getString(3) : "";
                portId = rs.getInt(4);
                deliveryyear = rs.getString(5) != null ? rs.getString(5) : "";
                assetflag = rs.getString(6) != null ? rs.getString(6) : "";
                classification = rs.getString(7) != null ? rs.getString(7) : "";
                berths = rs.getString(8) != null ? rs.getString(8) : "";
                lifeboat = rs.getString(9) != null ? rs.getString(9) : "";
                status = rs.getInt(10);
                countryId = rs.getInt(11);
                assettypeId = rs.getInt(12);
                url = rs.getString(13) != null ? rs.getString(13) : "";
                url_training = rs.getString(14) != null ? rs.getString(14) : "";
                helpno = rs.getString(15) != null ? rs.getString(15) : "";
                helpemail = rs.getString(16) != null ? rs.getString(16) : "";
                currencyId = rs.getInt(17);
                ratetype = rs.getString(18) != null ? rs.getString(18) : "";
                crewrota = rs.getInt(19);
                startrota = rs.getInt(20);
                allowance = rs.getInt(21);
                
                info = new ClientInfo(clientassetId, name, description, portId, deliveryyear, assetflag,
                        classification, berths, lifeboat, status, countryId, assettypeId, url, url_training,
                        currencyId, helpno, helpemail, ratetype, crewrota, startrota, allowance);
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return info;
    }

    public ArrayList getListForExcel(String search, int countryIndexId, int assettypeIndexId, int allclient, String permission, String cids, String assetids) {
        ArrayList list = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT t_client.i_clientid, t_client.s_name, t_client.s_inchargename, t_client.s_email, t_client.s_contact, t_client.i_status, t1.ct, t_client.s_isdcode ");
        sb.append("FROM t_client ");
        sb.append("LEFT JOIN (SELECT i_clientid, COUNT(1) AS ct FROM t_clientasset WHERE i_status =1 GROUP BY i_clientid) AS t1 ON (t_client.i_clientid = t1.i_clientid) ");
        sb.append("LEFT JOIN t_clientasset ON (t_client.i_clientid = t_clientasset.i_clientid) WHERE 0=0 ");
        if (search != null && !search.equals("")) {
            sb.append("AND (t_client.s_name LIKE ? OR t_client.s_inchargename LIKE ? OR t_client.i_clientid =? OR t_client.s_email =? OR t_client.s_contact =?) ");
        }
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
        if (countryIndexId > 0) {
            sb.append("AND t_client.i_countryid =? ");
        }
        if (assettypeIndexId > 0) {
            sb.append("AND t_clientasset.i_assettypeid =? ");
        }
        sb.append("GROUP BY t_client.i_clientid ORDER BY t_client.i_status, t_client.s_name ");

        String query = (sb.toString()).intern();
        sb.setLength(0);
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            int scc = 0;
            if (search != null && !search.equals("")) {
                pstmt.setString(++scc, "%" + (search) + "%");
                pstmt.setString(++scc, "%" + (search) + "%");
                pstmt.setString(++scc, search);
                pstmt.setString(++scc, search);
                pstmt.setString(++scc, search);
            }
            if (countryIndexId > 0) {
                pstmt.setInt(++scc, countryIndexId);
            }
            if (assettypeIndexId > 0) {
                pstmt.setInt(++scc, assettypeIndexId);
            }
            logger.info("getClientByName :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String name, inchargename, email, contact, ISDCode;
            int clientId, status, assetcount;
            while (rs.next()) {
                clientId = rs.getInt(1);
                name = rs.getString(2) != null ? rs.getString(2) : "";
                inchargename = rs.getString(3) != null ? rs.getString(3) : "";
                email = rs.getString(4) != null ? rs.getString(4) : "";
                contact = rs.getString(5) != null ? rs.getString(5) : "";
                status = rs.getInt(6);
                assetcount = rs.getInt(7);
                ISDCode = rs.getString(8) != null ? rs.getString(8) : "";

                if (!ISDCode.equals("")) {
                    contact = "+" + ISDCode + " " + contact;
                }

                list.add(new ClientInfo(clientId, name, inchargename, email,
                        contact, assetcount, status, ISDCode));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return list;
    }

    public String getCityAutoFill(String val, int countryId) {
        String s = "";
        StringBuilder sb = new StringBuilder();
        sb.append("select t_city.i_cityid, t_city.s_name ");
        sb.append("from t_city where t_city.i_status = 1 and i_countryid = ? and t_city.s_name like ? order by t_city.s_name ");
        String query = (sb.toString()).intern();
        sb.setLength(0);
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, countryId);
            pstmt.setString(2, (val) + "%");
            rs = pstmt.executeQuery();
            JSONArray jsonArrayParent = new JSONArray();
            while (rs.next()) {
                JSONObject jsonObjectRow = new JSONObject();
                jsonObjectRow.put("value", rs.getInt(1));
                jsonObjectRow.put("label", rs.getString(2));
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

    public String getCityAutoFillVaccination(String val) {
        String s = "";
        StringBuilder sb = new StringBuilder();
        sb.append("select t_city.i_cityid, t_city.s_name, t_country.s_name ");
        sb.append("from t_city left join t_country on (t_country.i_countryid = t_city.i_countryid) where t_city.i_status = 1 and t_city.s_name like ? order by t_city.s_name ");
        String query = (sb.toString()).intern();
        sb.setLength(0);
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, (val) + "%");
            rs = pstmt.executeQuery();
            JSONArray jsonArrayParent = new JSONArray();
            while (rs.next()) {
                JSONObject jsonObjectRow = new JSONObject();
                jsonObjectRow.put("value", rs.getInt(1));
                jsonObjectRow.put("label", rs.getString(2) + " (" + rs.getString(3) + ")");
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

    public int createMaster(int type, String name, int userId, int countryId) {
        int cc = 0;
        try {
            String query = "";
            if (type == 1) {
                query = ("insert into t_relation (s_name, i_status, i_userid, ts_regdate, ts_moddate) values (?,?,?,?,?) ");
            } else if (type == 2) {
                query = ("insert into t_experiencedept (s_name, i_status, i_userid, ts_regdate, ts_moddate) values (?,?,?,?,?) ");
            } else if (type == 3) {
                query = ("INSERT INTO t_city (s_name, i_status, i_userid, ts_regdate, ts_moddate, i_countryid) VALUES (?,?,?,?,?,?) ");
            } else if (type == 4) {
                query = ("INSERT INTO t_state (s_name, i_status, i_userid, ts_regdate, ts_moddate, i_countryid) VALUES (?,?,?,?,?,?) ");
            }
            conn = getConnection();
            String checkq = "";
            if (type == 1) {
                checkq = "select i_relationid from t_relation where i_status IN (1,2) and s_name = ? ";
            } else if (type == 2) {
                checkq = "select i_experiencedeptid from t_experiencedept where i_status IN (1,2) and s_name = ? ";
            } else if (type == 3) {
                checkq = "SELECT i_cityid FROM t_city WHERE i_status IN (1,2) AND s_name =? AND i_countryid =?";
            } else if (type == 4) {
                checkq = "SELECT i_stateid FROM t_state WHERE i_status IN (1,2) AND s_name =? AND i_countryid =?";
            }
            pstmt = conn.prepareStatement(checkq);
            pstmt.setString(1, name);
            if (type == 3 || type == 4) {
                pstmt.setInt(2, countryId);
            }
            print(this, "createMaster check:: " + pstmt.toString());
            rs = pstmt.executeQuery();
            int check = 0;
            while (rs.next()) {
                check = rs.getInt(1);
            }
            rs.close();
            if (check == 0) {
                pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                pstmt.setString(1, name);
                pstmt.setInt(2, 1);
                pstmt.setInt(3, userId);
                pstmt.setString(4, currDate1());
                pstmt.setString(5, currDate1());
                if (type == 3 || type == 4) {
                    pstmt.setInt(6, countryId);
                }
                print(this, "createMaster insert:: " + pstmt.toString());
                pstmt.executeUpdate();
                rs = pstmt.getGeneratedKeys();
                while (rs.next()) {
                    cc = rs.getInt(1);
                }
                rs.close();
                if (type == 1) {
                    Relation relation = new Relation();
                    relation.createjson(conn);
                } 
                else if (type == 2) {
                    ExperienceDept dept = new ExperienceDept();
                    dept.createjson(conn);
                }
                else if (type == 4) {
                    State state = new State();
                    state.createjson(conn);
                }
            } else {
                cc = -2;
            }
        } catch (Exception exception) {
            print(this, "createMaster :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return cc;
    }

    public int createPic(Connection conn, int clientassetId, String filename, int userId) {
        int cc = 0;
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("INSERT INTO t_clientassetpic ");
            sb.append("(i_clientassetid, s_filename, i_status, i_userid, ts_regdate, ts_moddate) ");
            sb.append("VALUES (?, ?, ?, ?, ?, ? )");
            String query = (sb.toString()).intern();
            sb.setLength(0);
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, clientassetId);
            pstmt.setString(2, filename);
            pstmt.setInt(3, 1);
            pstmt.setInt(4, userId);
            pstmt.setString(5, currDate1());
            pstmt.setString(6, currDate1());
            print(this, "createPic :: " + pstmt.toString());
            cc = pstmt.executeUpdate();
        } catch (Exception exception) {
            print(this, "createPic :: " + exception.getMessage());
        } finally {
            close(null, pstmt, rs);
        }
        return cc;
    }

    public ArrayList getPics(int clientassetId) {
        ArrayList list = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("Select t_clientassetpic.i_clientassetpicid, t_clientassetpic.s_filename, DATE_FORMAT(t_clientassetpic.ts_regdate, '%d-%b-%Y %H:%i'), ");
        sb.append("t_userlogin.s_name ");
        sb.append("FROM t_clientassetpic left join t_userlogin on (t_userlogin.i_userid = t_clientassetpic.i_userid) ");
        sb.append("where t_clientassetpic.i_clientassetid = ? ");
        sb.append(" order by t_clientassetpic.i_clientassetpicid ");
        String query = (sb.toString()).intern();
        sb.setLength(0);
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, clientassetId);
            logger.info("getPics :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String filename, date, name;
            int clientassetpicid;
            while (rs.next()) {
                clientassetpicid = rs.getInt(1);
                filename = rs.getString(2) != null ? rs.getString(2) : "";
                date = rs.getString(3) != null ? rs.getString(3) : "";
                name = rs.getString(4) != null ? rs.getString(4) : "";
                list.add(new ClientInfo(clientassetpicid, filename, date, name));
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return list;
    }

    public int delpic(int clientassetpicId) {
        int cc = 0;
        String query = "select s_filename FROM t_clientassetpic where i_clientassetpicid = ?";
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, clientassetpicId);
            logger.info("delpic :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String filename;
            while (rs.next()) {
                filename = rs.getString(1) != null ? rs.getString(1) : "";
                if (!filename.equals("")) {
                    File f = new File(getMainPath("add_client_file") + filename);
                    if (f.exists()) {
                        f.delete();
                    }
                }
            }
            rs.close();
            query = "delete from t_clientassetpic where i_clientassetpicid = ?";
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, clientassetpicId);
            logger.info("delpic :: " + pstmt.toString());
            cc = pstmt.executeUpdate();
        } catch (Exception exception) {
            print(this, "delpic :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return cc;
    }

    public int getMaxId() {
        int id = 0;
        String query = "SELECT MAX(i_clientassetid) FROM t_clientasset";
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                id = rs.getInt(1);
            }
            id = id + 1;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return id;
    }

    public int getMaxIdclient() {
        int id = 0;
        String query = "SELECT MAX(i_clientid) FROM t_client";
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                id = rs.getInt(1);
            }
            id = id + 1;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return id;
    }

    public Collection getPorts() {
        Collection coll = new LinkedList();
        String query = "SELECT i_portid, s_name FROM t_port where i_status = 1 order by s_name";
        coll.add(new ClientInfo(-1, "- Select -"));
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            rs = pstmt.executeQuery();
            int id;
            String name;
            while (rs.next()) {
                id = rs.getInt(1);
                name = rs.getString(2) != null ? rs.getString(2) : "";
                coll.add(new ClientInfo(id, name));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return coll;
    }

    public ArrayList getpositionlistformodel(int clientassetId) {
        ArrayList positionlist = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("Select t_position.i_positionid, t_position.s_name,t_grade.s_name,t_clientassetposition.i_clientassetpositionid, t_assettype.s_name ");
        sb.append("FROM t_clientassetposition left join t_position on (t_clientassetposition.i_positionid = t_position.i_positionid) ");
        sb.append("left join t_grade on (t_grade.i_gradeid = t_position.i_gradeid) ");
        sb.append("left join t_assettype on (t_assettype.i_assettypeid = t_position.i_assettypeid) ");
        sb.append("where t_clientassetposition.i_clientassetid = ? ");
        sb.append("order by t_position.s_name ");
        String query = (sb.toString()).intern();
        sb.setLength(0);
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, clientassetId);
            logger.info(" getpositionlistformodel:: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String positionname, gradename, assettypeName;
            int positionid, clientassetpositionid;
            while (rs.next()) {
                positionid = rs.getInt(1);
                positionname = rs.getString(2) != null ? rs.getString(2) : "";
                gradename = rs.getString(3) != null ? rs.getString(3) : "";
                clientassetpositionid = rs.getInt(4);
                assettypeName = rs.getString(5) != null ? rs.getString(5) : "";
                positionlist.add(new ClientInfo(positionid, positionname, gradename, clientassetpositionid, assettypeName));
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return positionlist;
    }

    public ArrayList getPositionbynotincap(int clientassetId, int assettypeId) {
        ArrayList coll = new ArrayList();
        String query = "SELECT i_positionid, t_position.s_name, t_grade.s_name, t_assettype.s_name  from t_position left join t_grade on (t_grade.i_gradeid = t_position.i_gradeid)"
                + "left join t_assettype on (t_position.i_assettypeid = t_assettype.i_assettypeid) where t_position.i_status = 1 and  t_position.i_assettypeid = ? and i_positionid  NOT IN (select i_positionid from t_clientassetposition where i_clientassetid = ?)";
        coll.add(new ClientInfo(-1, "- Select -"));
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, assettypeId);
            pstmt.setInt(2, clientassetId);
            logger.info(" getPositionbynotincap:: " + pstmt.toString());
            rs = pstmt.executeQuery();
            int id;
            String name;
            while (rs.next()) {
                id = rs.getInt(1);
                name = rs.getString(2) + " - " + rs.getString(3) + " - " + rs.getString(4);
                coll.add(new ClientInfo(id, name));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return coll;
    }

    public int insertcap(int clientassetId, int userId, String positionId[]) {
        int cc = 0;
        try {
            int len = positionId.length;
            if (len > 0) {
                StringBuilder sb = new StringBuilder();
                sb.append("insert into t_clientassetposition ");
                sb.append("(i_clientassetid, i_positionid, i_status, i_userid, ts_regdate) ");
                sb.append("values (?, ?, ?, ?, ?)");
                String query = (sb.toString()).intern();
                sb.setLength(0);
                conn = getConnection();

                pstmt = conn.prepareStatement(query);
                for (int i = 0; i < len; i++) {
                    pstmt.setInt(1, clientassetId);
                    pstmt.setInt(2, Integer.parseInt(positionId[i]));
                    pstmt.setInt(3, 1);
                    pstmt.setInt(4, userId);
                    pstmt.setString(5, currDate1());
                    cc += pstmt.executeUpdate();
                }
            }
        } catch (Exception exception) {
            print(this, "insertcap :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return cc;
    }

    public int deleteClientAssetposition(int clientassetpositionId) {
        int cc = 0;
        try {
            conn = getConnection();
            String delquery = "delete from t_clientassetposition where i_clientassetpositionid = ?";
            pstmt = conn.prepareStatement(delquery);
            pstmt.setInt(1, clientassetpositionId);
            logger.info(" deleteClientAssetposition:: " + pstmt.toString());
            cc = pstmt.executeUpdate();

        } catch (Exception exception) {
            print(this, "deleteClientAssetposition :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return cc;
    }

    public String setLink(String s) {
        String val = "";
        if (s != null && !s.equals("")) {
            String arr[] = s.split("\n");
            if (arr != null && arr.length > 0) {
                int len = arr.length;
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < len; i++) {
                    String link = arr[i] != null ? arr[i] : "";
                    if (i != 0) {
                        sb.append("<br/>");
                    }
                    sb.append("<a href='" + link + "' target = '_blank'>" + link + "</a>");
                }
                val = sb.toString();
                sb.setLength(0);
            }
        }
        return val;
    }

    public ArrayList getDeptList(int clientassetId, int assettypeId) {
        ArrayList list = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("select t_pdept.i_pdeptid, t_pdept.s_name, t1.ct FROM t_pdept  ");
        sb.append("left join (select i_pdeptid, count(1) as ct from t_clientassetposition where i_status = 1 and i_clientassetid = ? group by i_pdeptid) as t1 on (t1.i_pdeptid = t_pdept.i_pdeptid) ");
        sb.append("where t_pdept.i_assettypeid = ? and t_pdept.i_status = 1 ");
        sb.append(" order by t_pdept.s_name ");
        String query = (sb.toString()).intern();
        sb.setLength(0);
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, clientassetId);
            pstmt.setInt(2, assettypeId);
            logger.info("getDeptList :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String name;
            int pdeptId, positionCount;
            while (rs.next()) {
                pdeptId = rs.getInt(1);
                name = rs.getString(2) != null ? rs.getString(2) : "";
                positionCount = rs.getInt(3);
                list.add(new ClientInfo(pdeptId, name, positionCount));
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return list;
    }

    public int updateDept(int pdeptId, int clientassetId, int userId) {
        int cc = 0;
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("update t_clientassetposition set ");
            sb.append("i_pdeptid = 0, ");
            sb.append("i_userid = ?, ");
            sb.append("ts_moddate = ? ");
            sb.append("where i_clientassetid = ? and i_pdeptid = ? ");
            String query = (sb.toString()).intern();
            sb.setLength(0);
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, userId);
            pstmt.setString(2, currDate1());
            pstmt.setInt(3, clientassetId);
            pstmt.setInt(4, pdeptId);
            print(this, "updateDept :: " + pstmt.toString());
            cc = pstmt.executeUpdate();
        } catch (Exception exception) {
            print(this, "updateDept :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return cc;
    }

    public int updatePosition(int clientassetId, int pdeptId, int clientassetpositionId, int userId, int setval) {
        int cc = 0;
        if (clientassetpositionId > 0 && pdeptId > 0 && clientassetId > 0) {
            try {
                StringBuilder sb = new StringBuilder();
                sb.append("update t_clientassetposition set ");
                sb.append("i_pdeptid = ?, ");
                sb.append("i_userid = ?, ");
                sb.append("ts_moddate = ? ");
                sb.append("where i_clientassetid = ? and i_clientassetpositionid = ? ");
                String query = (sb.toString()).intern();
                sb.setLength(0);
                conn = getConnection();
                pstmt = conn.prepareStatement(query);
                if (setval == 2) {
                    pstmt.setInt(1, 0);
                } else {
                    pstmt.setInt(1, pdeptId);
                }
                pstmt.setInt(2, userId);
                pstmt.setString(3, currDate1());
                pstmt.setInt(4, clientassetId);
                pstmt.setInt(5, clientassetpositionId);
                print(this, "updatePosition :: " + pstmt.toString());
                cc = pstmt.executeUpdate();
            } catch (Exception exception) {
                print(this, "updatePosition :: " + exception.getMessage());
            } finally {
                close(conn, pstmt, rs);
            }
        }
        return cc;
    }

    public ArrayList getPositionlistfordept(int clientassetId, int pdeptIdparam) {
        ArrayList positionlist = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT t_clientassetposition.i_clientassetpositionid, t_position.s_name, t_grade.s_name, t_clientassetposition.i_pdeptid, t_pdept.i_status ");
        sb.append("FROM t_clientassetposition left join t_position on (t_clientassetposition.i_positionid = t_position.i_positionid) ");
        sb.append("left join t_grade on (t_grade.i_gradeid = t_position.i_gradeid) ");
        sb.append("left join t_pdept on (t_pdept.i_pdeptid = t_clientassetposition.i_pdeptid) ");
        sb.append("where t_clientassetposition.i_clientassetid = ? and (t_clientassetposition.i_pdeptid = ? OR t_clientassetposition.i_pdeptid <= 0 OR t_pdept.i_status = 2) ");
        sb.append("order by (CASE WHEN t_clientassetposition.i_pdeptid > 0 THEN 1 ELSE 0 END) desc, t_position.s_name, t_grade.s_name ");
        String query = (sb.toString()).intern();
        sb.setLength(0);
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, clientassetId);
            pstmt.setInt(2, pdeptIdparam);
            logger.info(" getPositionlistfordept:: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String positionname, gradename;
            int clientassetpositionId, pdeptId, status;
            while (rs.next()) {
                clientassetpositionId = rs.getInt(1);
                positionname = rs.getString(2) != null ? rs.getString(2) : "";
                gradename = rs.getString(3) != null ? rs.getString(3) : "";
                pdeptId = rs.getInt(4);
                status = rs.getInt(5);
                if (status != 1) {
                    pdeptId = 0;
                }
                positionlist.add(new ClientInfo(clientassetpositionId, positionname, gradename, pdeptId));
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return positionlist;
    }

    public String getDeptName(int pdeptId) {
        String name = "";
        String query = ("SELECT s_name FROM t_pdept where i_pdeptid = ? ");
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, pdeptId);
            logger.info(" getDeptName:: " + pstmt.toString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                name = rs.getString(1) != null ? rs.getString(1) : "";
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return name;
    }

    public int getDeptCount(int clientassetId) {
        int ct = 0;
        String query = "select SUM(if(t_clientassetposition.i_pdeptid > 0, 1, 0)) as ct from t_clientassetposition where i_status = 1 and i_clientassetid = ?";
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, clientassetId);
            logger.info(" getDeptCount:: " + pstmt.toString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ct = rs.getInt(1);
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return ct;
    }
    
    public String getCoordinateIds(int clientId) {
        String ids = "";
        String query = "SELECT s_ocsuserids FROM t_client WHERE i_clientid = ? AND i_status = 1";
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, clientId);
            logger.info(" getCoordinateIds:: " + pstmt.toString());
            rs = pstmt.executeQuery();
            while (rs.next()) 
            {
                ids = rs.getString(1) != null ? rs.getString(1): "";
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return ids;
    }
    
    public ArrayList getCoordinatorList(String ids) 
    {
        ArrayList list = new ArrayList();
        if(ids != null && !ids.equals(""))
        {
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT DISTINCT t_userlogin.i_userid, t_userlogin.s_name ");
            sb.append("FROM t_client ");
            sb.append("LEFT JOIN t_userlogin ON(t_userlogin.i_userid = t_client.s_ocsuserids) ");
            sb.append("WHERE t_client.s_ocsuserids IN("+ids+") AND t_userlogin.i_status = 1 ORDER BY t_userlogin.s_name");
            String query = (sb.toString().intern());
            sb.setLength(0);        
            try
            {
                conn = getConnection();
                pstmt = conn.prepareStatement(query);
                logger.info(" getCoordinatorList:: " + pstmt.toString());
                rs = pstmt.executeQuery();
                int id;
                String name;
                while (rs.next()) 
                {
                    id = rs.getInt(1);
                    name = rs.getString(2) != null ? rs.getString(2) : "";
                    list.add(new ClientInfo(id, name));
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                close(conn, pstmt, rs);
            }
        }
        return list;
    }
    
    public ArrayList getManagerIds() 
    {
        ArrayList list = new ArrayList();
        String query = "SELECT i_userid, s_name FROM t_userlogin WHERE i_status = 1 AND i_mflag = 1 ORDER BY s_name";
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            rs = pstmt.executeQuery();
            int id;
            String name;
            while (rs.next()) {
                id = rs.getInt(1);
                name = rs.getString(2) != null ? rs.getString(2) : "";
                list.add(new ClientInfo(id, name));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return list;
    }
    
    public ArrayList getRecruiterIds() 
    {
        ArrayList list = new ArrayList();
        String query = "SELECT i_userid, s_name FROM t_userlogin WHERE i_status = 1 AND i_rflag = 1 ORDER BY s_name";
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            rs = pstmt.executeQuery();
            int id;
            String name;
            while (rs.next()) {
                id = rs.getInt(1);
                name = rs.getString(2) != null ? rs.getString(2) : "";
                list.add(new ClientInfo(id, name));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return list;
    }
}
