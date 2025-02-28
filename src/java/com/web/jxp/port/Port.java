package com.web.jxp.port;

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
import java.util.Collection;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

public class Port extends Base {

    private static final Logger logger = Logger.getLogger(Class.class.getName());
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    public ArrayList getPortByName(String search, int next, int count) {
        ArrayList port = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("select t_port.i_portid, t_port.s_name, t_country.s_name, t_port.i_status ");
        sb.append("FROM t_port left join t_country on t_port.i_countryid = t_country.i_countryid  where 0 = 0 ");
        if (search != null && !search.equals("")) {
            sb.append("and (t_port.s_name like ? OR t_country.s_name like ?) ");
        }
        sb.append(" order by t_port.i_status, t_port.s_name, t_country.s_name");
        if (count > 0) {
            sb.append(" limit ").append(next * count).append(", ").append(count);
        }
        String query = (sb.toString()).intern();
        sb.setLength(0);

        sb.append("select count(1) FROM t_port ");
        sb.append(" left join t_country on (t_port.i_countryid = t_country.i_countryid) where 0 = 0  ");

        if (search != null && !search.equals("")) {
            sb.append("and (t_port.s_name like ? OR t_country.s_name like ?) ");
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
            logger.log(Level.INFO, "getPortByName :: {0}", pstmt.toString());
            rs = pstmt.executeQuery();
            String name, countryName;
            int portId, status;
            while (rs.next()) {
                portId = rs.getInt(1);
                name = rs.getString(2) != null ? rs.getString(2) : "";
                countryName = rs.getString(3) != null ? rs.getString(3) : "";
                status = rs.getInt(4);
                port.add(new PortInfo(portId, name, countryName, status, 0));
            }
            rs.close();

            pstmt = conn.prepareStatement(countquery);
            scc = 0;
            if (search != null && !search.equals("")) {
                pstmt.setString(++scc, "%" + (search) + "%");
                pstmt.setString(++scc, "%" + (search) + "%");
            }
            print(this,"getPortByName :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                portId = rs.getInt(1);
                port.add(new PortInfo(portId, "", "", 0, 0));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return port;
    }

    public ArrayList getFinalRecord(ArrayList l, String colId, int tp) {
        ArrayList list = null;
        HashMap record = null;
        PortInfo info;
        int total = 0;
        if (l != null) {
            total = l.size();
        }
        try {
            if (total > 0) {
                record = new HashMap();
                int i;
                for (i = 0; i < total; i++) {
                    info = (PortInfo) l.get(i);
                    record.put(getInfoValue(info, colId), info);
                }
            }
            Map map = sortByName(record, tp);
            Iterator it = map.keySet().iterator();
            list = new ArrayList();
            PortInfo rInfo;
            while (it.hasNext()) {
                String key = (String) it.next();
                int i;
                for (i = 0; i < total; i++) {
                    rInfo = (PortInfo) l.get(i);
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
    public String getInfoValue(PortInfo info, String i) {
        String infoval = "";        
        if (i != null && i.equals("1")) {
            infoval = info.getName() != null ? info.getName() : "";
        }
         if (i != null && i.equals("2")) {
            infoval = info.getCountryName() != null ? info.getCountryName() : "";
        }
        return infoval;
    }

    public PortInfo getPortDetailById(int portId) {
        PortInfo info = null;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT s_name, i_countryid, i_status FROM t_port where i_portid = ? ");
        String query = sb.toString().intern();
        sb.setLength(0);
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, portId);
            //print(this,"getPortDetailById :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String name;
            int status, countryId;
            while (rs.next())
            {
                name = rs.getString(1) != null ? rs.getString(1): "";
                countryId = rs.getInt(2);
                status = rs.getInt(3);
                info = new PortInfo(portId, name, countryId, status, 0);
            }
        } catch (Exception exception) {
            print(this, "getPortDetailById :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return info;
    }

    public PortInfo getPortDetailByIdforDetail(int portId) {
        PortInfo info = null;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT t_port.s_name, t_country.s_name, t_port.i_status FROM t_port LEFT JOIN t_country ON t_port.i_countryid = t_country.i_countryid where i_portid = ? ");
        String query = sb.toString().intern();
        sb.setLength(0);
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, portId);
            //print(this,"getPortDetailByIdforDetail :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String name, countryName;
            int status = 0;
            while (rs.next()) 
            {
                name = rs.getString(1) != null ? rs.getString(1): "";
                countryName = rs.getString(2) != null ? rs.getString(2): "";
                status = rs.getInt(3);

                info = new PortInfo(portId, name, countryName, status, 0);
            }
            rs.close();
        } catch (Exception exception) {
            print(this, "getPortDetailByIdforDetail :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return info;
    }

    public int createPort(PortInfo info) {
        int portId = 0;
        try
        {
            StringBuilder sb = new StringBuilder();
            sb.append("insert into t_port ");
            sb.append("(s_name, i_countryid, i_status, i_userid, ts_regdate, ts_moddate) ");
            sb.append("values (?, ?, ?, ?, ?, ?)");
            String query = (sb.toString()).intern();
            sb.setLength(0);

            conn = getConnection();
            pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            int scc = 0;
            pstmt.setString(++scc, (info.getName()));
            pstmt.setInt(++scc, info.getCountryId());
            pstmt.setInt(++scc, info.getStatus());
            pstmt.setInt(++scc, info.getUserId());
            pstmt.setString(++scc, currDate1());
            pstmt.setString(++scc, currDate1());
            //print(this,"createPort :: " + pstmt.toString());
            pstmt.executeUpdate();
            rs = pstmt.getGeneratedKeys();
            while (rs.next()) {
                portId = rs.getInt(1);
            }
            createjson(conn);
        } catch (Exception exception) {
            print(this, "createPort :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return portId;
    }

    public int updatePort(PortInfo info) {
        int cc = 0;
        try 
        {
            StringBuilder sb = new StringBuilder();
            sb.append("update t_port set ");
            sb.append("s_name = ?, ");
            sb.append("i_countryid = ?, ");
            sb.append("i_status = ?, ");
            sb.append("i_userid = ?, ");
            sb.append("ts_moddate = ? ");
            sb.append("where i_portid = ? ");
            String query = (sb.toString()).intern();
            sb.setLength(0);

            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            int scc = 0;
            pstmt.setString(++scc, info.getName());
            pstmt.setInt(++scc, info.getCountryId());
            pstmt.setInt(++scc, info.getStatus());
            pstmt.setInt(++scc, info.getUserId());
            pstmt.setString(++scc, currDate1());
            pstmt.setInt(++scc, info.getPortId());
            //print(this,"updatePort :: " + pstmt.toString());
            cc = pstmt.executeUpdate();
            createjson(conn);
        } catch (Exception exception) {
            print(this, "updatePort :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return cc;
    }

    public int checkDuplicacy(int portId, String name, int countryId) {
        int ck = 0;
        StringBuilder sb = new StringBuilder();
        sb.append("select i_portid FROM t_port where s_name = ? and i_countryid = ? and i_status in (1, 2)");
        if (portId > 0) {
            sb.append(" and i_portid != ? ");
        }
        String query = (sb.toString()).intern();
        sb.setLength(0);
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            int scc = 0;
            pstmt.setString(++scc, name);
            pstmt.setInt(++scc, countryId);
            if (portId > 0) {
                pstmt.setInt(++scc, portId);
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

    public Collection getPort() {
        Collection coll = new LinkedList();
        coll.add(new PortInfo(-1, "- Select -"));
        String xml_path = getMainPath("json_path");
        String str = readHTMLFile("port.json", xml_path);
        JSONArray arr = new JSONArray(str);
        if (arr != null) {
            int len = arr.length();
            for (int i = 0; i < len; i++) {
                JSONObject jobj = arr.optJSONObject(i);
                if (jobj != null) {
                    coll.add(new PortInfo(jobj.optInt("id"), jobj.optString("name"), jobj.optInt("countryId")));
                }
            }
        }
        return coll;
    }

    public int deletePort(int portId, int userId, int status, String ipAddrStr, String iplocal) {
        int cc = 0;
        try 
        {
            conn = getConnection();
            StringBuilder sb = new StringBuilder();
            sb.append("update t_port set ");
            sb.append("i_status = ?, ");
            sb.append("i_userid = ?, ");
            sb.append("ts_moddate = ? ");
            sb.append("where i_portid = ? ");
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
            pstmt.setInt(++scc, portId);
            
            cc = pstmt.executeUpdate();
            createjson(conn);
        } catch (Exception exception) {
            print(this, "deletePort :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        createHistoryAccess(null, userId, ipAddrStr, iplocal, "Data updated", 44, portId);
        return cc;
    }

    public ArrayList getListForExcel(String search) {
        ArrayList list = new ArrayList();
        StringBuilder sb = new StringBuilder();
         sb.append("select t_port.i_portid, t_port.s_name, t_country.s_name, t_port.i_status ");
        sb.append("FROM t_port left join t_country on t_port.i_countryid = t_country.i_countryid  where 0 = 0 ");       
        if (search != null && !search.equals("")) {
            sb.append("and (t_port.s_name like ? OR t_country.s_name like ?) ");
        }
        sb.append(" order by t_port.i_status, t_port.s_name, t_country.s_name ");
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
           String name, countryName;
            int portId, status;
            while (rs.next()) 
            {
                portId = rs.getInt(1);
                name = rs.getString(2) != null ? rs.getString(2) : "";
                countryName = rs.getString(3) != null ? rs.getString(3) : "";
                status = rs.getInt(4);
                list.add(new PortInfo(portId, name, countryName, status, 0));            
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

    public void createjson(Connection conn) {
        String query = ("Select i_portid, s_name, i_countryid FROM t_port where i_status = 1 order by  s_name ");
        try 
        {
            pstmt = conn.prepareStatement(query);
            rs = pstmt.executeQuery();
            int id, countryId;
            String name;
            JSONArray jarr = new JSONArray();
            while (rs.next()) 
            {
                id = rs.getInt(1);
                name = rs.getString(2) != null ? rs.getString(2): "";
                countryId = rs.getInt(3);
                JSONObject jobj = new JSONObject();
                jobj.put("id", id);
                jobj.put("name", name);
                jobj.put("countryId", countryId);
                jarr.put(jobj);
            }
            rs.close();
            String str = jarr.toString();
            String filePath = getMainPath("json_path");
            writeHTMLFile(str, filePath, "port.json");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(null, pstmt, rs);
        }
    }

    public Collection getCountrys() {
        Collection coll = new LinkedList();
        String query = ("SELECT i_countryid, s_name FROM t_country where i_status = 1 order by s_name").intern();
        coll.add(new PortInfo(-1, "- Select -"));
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
                coll.add(new PortInfo(refId, refName));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return coll;
    }
    
    public Collection getPortbycountries(int countryId)
    {
        Collection coll = new LinkedList();
        coll.add(new PortInfo(-1, "- Select -"));
        String xml_path = getMainPath("json_path");
        String str = readHTMLFile("port.json", xml_path);
        JSONArray arr = new JSONArray(str);
        if(arr != null)
        {
            int len = arr.length();
            for(int i = 0; i < len; i++)
            {
                JSONObject jobj = arr.optJSONObject(i);
                if(jobj != null && jobj.getInt("countryId") == countryId)
                {
                    coll.add(new PortInfo(jobj.optInt("id"), jobj.optString("name")));
                }
            }
        }
        return coll;
    }
}
