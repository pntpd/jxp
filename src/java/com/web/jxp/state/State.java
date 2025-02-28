package com.web.jxp.state;

import com.mysql.jdbc.Statement;
import com.web.jxp.assettype.AssettypeInfo;
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
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

public class State extends Base {

    private static final Logger logger = Logger.getLogger(Class.class.getName());
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    public ArrayList getStateByName(String search, int next, int count) 
    {
        ArrayList states = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("select t_state.i_stateid,  t_state.s_name , t_state.i_status,  t_country.s_name from t_state ");
        sb.append("left join t_country on t_state.i_countryid = t_country.i_countryid ");
        sb.append("where 0 = 0 ");
        if (search != null && !search.equals("")) {
            sb.append("and (t_state.s_name like ? OR t_country.s_name like ?) ");
        }
        sb.append(" order by   t_state.i_status,t_country.s_name, t_state.s_name ");
        if (count > 0) {
            sb.append(" limit ").append(next * count).append(", ").append(count);
        }
        String query = (sb.toString()).intern();
        sb.setLength(0);

        sb.append("select count(1) FROM t_state ");
        sb.append("left join t_country on t_state.i_countryid = t_country.i_countryid ");
        sb.append("where 0 = 0 ");
        if (search != null && !search.equals("")) {
            sb.append("and (t_state.s_name like ? OR t_country.s_name like ?) ");
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
            logger.info("getStateByName :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String name, countryName;
            int stateId, status;
            while (rs.next())
            {
                stateId = rs.getInt(1);
                name = rs.getString(2) != null ? rs.getString(2) : "";
                status = rs.getInt(3);
                countryName = rs.getString(4) != null ? rs.getString(4) : "";
                states.add(new StateInfo(stateId, name, status, countryName, 0));
            }
            rs.close();

            pstmt = conn.prepareStatement(countquery);
            scc = 0;
            if (search != null && !search.equals("")) {
                pstmt.setString(++scc, "%" + (search) + "%");
                pstmt.setString(++scc, "%" + (search) + "%");
            }
            //print(this,"getStateByName :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                stateId = rs.getInt(1);
                states.add(new StateInfo(stateId, "", 0, 0));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return states;
    }

    public ArrayList getFinalRecord(ArrayList l, String colId, int tp) {
        ArrayList list = null;
        HashMap record = null;
        StateInfo info;
        int total = 0;
        if (l != null) {
            total = l.size();
        }
        try {
            if (total > 0) {
                record = new HashMap();
                int i;
                for (i = 0; i < total; i++) {
                    info = (StateInfo) l.get(i);
                    record.put(getInfoValue(info, colId), info);
                }
            }
            Map map = sortByName(record, tp);
            Iterator it = map.keySet().iterator();
            list = new ArrayList();
            StateInfo rInfo;
            while (it.hasNext()) {
                String key = (String) it.next();
                int i;
                for (i = 0; i < total; i++) {
                    rInfo = (StateInfo) l.get(i);
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
    public String getInfoValue(StateInfo info, String i) {
        String infoval = "";
        if (i != null && i.equals("1")) {
            infoval = info.getStateName() != null ? info.getStateName() : "";
        }
        if (i != null && i.equals("2")) {
            infoval = info.getCountryName() != null ? info.getCountryName() : "";
        }
        return infoval;
    }

    public StateInfo getStateDetailById(int stateId) 
    {
        StateInfo info = null;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT s_name, i_countryid, i_status FROM t_state where i_stateid = ? ");
        String query = sb.toString().intern();
        sb.setLength(0);
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, stateId);
            print(this, "getStateDetailById :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String name;
            int status;
            while (rs.next()) 
            {
                name = rs.getString(1) != null ? rs.getString(1): "";
                status = rs.getInt(2);
                int countryId = rs.getInt(3);
                info = new StateInfo(stateId, name, status, countryId, 0);
            }
        } catch (Exception exception) {
            print(this, "getStateDetailById :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return info;
    }

    public StateInfo getStateDetailByIdforDetail(int stateId) 
    {
        StateInfo info = null;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT t_state.s_name,t_state.i_status, t_country.s_name  FROM t_state left join t_country on t_state.i_countryid = t_country.i_countryid where i_stateid = ?");
        String query = sb.toString().intern();
        sb.setLength(0);
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, stateId);
            print(this, "getStateDetailByIdforDetail :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String name;
            int status = 0;
            while (rs.next()) 
            {
                name = rs.getString(1) != null ? rs.getString(1): "";
                status = rs.getInt(2);
                String countryName = rs.getString(3) != null ? rs.getString(3): "";
                
                info = new StateInfo(stateId, name, status, countryName, 0);
            }
            rs.close();
        } catch (Exception exception) {
            print(this, "getStateDetailByIdforDetail :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return info;
    }

    public int createState(StateInfo info)
    {
        int stateId = 0;
        try 
        {
            StringBuilder sb = new StringBuilder();
            sb.append("insert into t_state ");
            sb.append("(s_name, i_countryid, i_status,  i_userid, ts_regdate, ts_moddate) ");
            sb.append("values (?, ?, ?, ?, ?, ?)");
            String query = (sb.toString()).intern();
            sb.setLength(0);

            conn = getConnection();
            pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            int scc = 0;
            pstmt.setString(++scc, (info.getStateName()));
            pstmt.setInt(++scc, info.getCountryId());
            pstmt.setInt(++scc, info.getStatus());
            pstmt.setInt(++scc, info.getUserId());
            pstmt.setString(++scc, currDate1());
            pstmt.setString(++scc, currDate1());
            print(this, "createState :: " + pstmt.toString());
            pstmt.executeUpdate();
            rs = pstmt.getGeneratedKeys();
            while (rs.next()) {
                stateId = rs.getInt(1);
            }
            createjson(conn);
        } catch (Exception exception) {
            print(this, "createState :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return stateId;
    }

    public int updateState(StateInfo info) 
    {
        int cc = 0;
        try 
        {
            StringBuilder sb = new StringBuilder();
            sb.append("update t_state set ");
            sb.append("s_name = ?, ");
            sb.append("i_status = ?, ");
            sb.append("i_userid = ?, ");
            sb.append("ts_moddate = ?, ");
            sb.append("i_countryid = ? ");
            sb.append("where i_stateid = ? ");
            String query = (sb.toString()).intern();
            sb.setLength(0);

            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            int scc = 0;
            pstmt.setString(++scc, info.getStateName());
            pstmt.setInt(++scc, info.getStatus());
            pstmt.setInt(++scc, info.getUserId());
            pstmt.setString(++scc, currDate1());
            pstmt.setInt(++scc, info.getCountryId());
            pstmt.setInt(++scc, info.getStateId());
            print(this, "updateState :: " + pstmt.toString());
            cc = pstmt.executeUpdate();
            createjson(conn);
        } catch (Exception exception) {
            print(this, "updateState :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return cc;
    }

    public int checkDuplicacy(int stateId, String name, int countryId) 
    {
        int ck = 0;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT i_stateid FROM t_state WHERE i_countryid =? AND s_name =? AND i_status IN (1, 2) ");
        if (stateId > 0) {
            sb.append("AND i_stateid !=? ");
        }
        String query = (sb.toString()).intern();
        sb.setLength(0);
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            int scc = 0;
            pstmt.setInt(++scc, countryId);
            pstmt.setString(++scc, name);
            if (stateId > 0) {
                pstmt.setInt(++scc, stateId);
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

    public Collection getCountry() 
    {
        Collection coll = new LinkedList();
        String query = ("SELECT i_countryid, s_name FROM t_country where i_status = 1 order by s_name").intern();
        coll.add(new StateInfo(-1, "- Select -"));
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
                coll.add(new StateInfo(refId, refName));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return coll;
    }

    public int deleteState(int stateId, int userId, int status, String ipAddrStr, String iplocal) 
    {
        int cc = 0;
        try
        {
            conn = getConnection();
            StringBuilder sb = new StringBuilder();
            sb.append("update t_state set ");
            sb.append("i_status = ?, ");
            sb.append("i_userid = ?, ");
            sb.append("ts_moddate = ? ");
            sb.append("where i_stateid = ? ");
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
            pstmt.setInt(++scc, stateId);

            cc = pstmt.executeUpdate();
            createjson(conn);
        } catch (Exception exception) {
            print(this, "deleteState :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        createHistoryAccess(null, userId, ipAddrStr, iplocal, "Data Updated", 99, stateId);
        return cc;
    }

    public ArrayList getExcel(String search) 
    {
        ArrayList list = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("select t_state.i_stateid,  t_state.s_name, ");
        sb.append(" t_state.i_status,  t_country.s_name from t_state ");
        sb.append("left join t_country on t_state.i_countryid = t_country.i_countryid ");
        sb.append("where 0 = 0 ");
        if (search != null && !search.equals("")) {
            sb.append("and (t_state.s_name like ? OR t_country.s_name like ? ) ");
        }
        sb.append(" order by t_state.i_status, t_country.s_name, t_state.s_name  ");
        String query = sb.toString().intern();
        sb.setLength(0);
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            Common.print(this, "getListForExcel :: " + pstmt.toString());
            int scc = 0;
            if (search != null && !search.equals("")) 
            {
                pstmt.setString(++scc, "%" + (search) + "%");
                pstmt.setString(++scc, "%" + (search) + "%");
            }
            rs = pstmt.executeQuery();
            while (rs.next()) 
            {
                int stateId = rs.getInt(1);
                String statename = rs.getString(2) != null ? rs.getString(2) : "";
                int Status = rs.getInt(3);
                String countryname = rs.getString(4) != null ? rs.getString(4) : "";

                list.add(new StateInfo(stateId, statename, Status, countryname));
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

   public void createjson(Connection conn)
    {
       String query = ("SELECT i_countryid, i_stateid, s_name FROM t_state WHERE i_status = 1 ORDER BY s_name ");
        try
        {
            pstmt = conn.prepareStatement(query);
            rs = pstmt.executeQuery();
            int countryId, stateId;
            String name;
            JSONArray jarr = new JSONArray();
            while (rs.next())
            {
                countryId = rs.getInt(1);
                stateId = rs.getInt(2);
                name = rs.getString(3) != null ? rs.getString(3) : "";
                JSONObject jobj = new JSONObject();
                jobj.put("countryid", countryId);
                jobj.put("stateid", stateId);
                jobj.put("name", name);
                jarr.put(jobj);
            }
            rs.close();
            String str = jarr.toString();
            String filePath = getMainPath("json_path");
            writeHTMLFile(str, filePath, "state.json");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            close(null, pstmt, rs);
        }
    }
   
    public Collection getStates()
    {
        Collection coll = new LinkedList();
        coll.add(new AssettypeInfo(-1, "Select State"));
        String xml_path = getMainPath("json_path");
        String str = readHTMLFile("state.json", xml_path);
        JSONArray arr = new JSONArray(str);
        if(arr != null)
        {
            int len = arr.length();
            for(int i = 0; i < len; i++)
            {
                JSONObject jobj = arr.optJSONObject(i);
                if(jobj != null)
                {
                    coll.add(new StateInfo(jobj.optInt("countryid"), jobj.optInt("stateid"), jobj.optString("name")));
                }
            }
        }
        return coll;
    }
}
