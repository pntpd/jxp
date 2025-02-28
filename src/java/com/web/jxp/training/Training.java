package com.web.jxp.training;

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
import java.util.Collection;
import java.util.LinkedList;
import java.util.logging.Logger;

public class Training extends Base {

    private static final Logger logger = Logger.getLogger(Class.class.getName());
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    public ArrayList getTrainingByName(String search, int next, int count,int clientIndex, 
            int assetIndex, int allclient, String permission, String cids, String assetids) {
        ArrayList trainings = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("select t_training.i_trainingid, t_training.s_name, t_training.i_status, t_client.s_name, t_clientasset.s_name FROM t_training ");
        sb.append("left join t_client on (t_client.i_clientid = t_training.i_clientid) ");
        sb.append("left join  t_clientasset on (t_clientasset.i_clientassetid = t_training.i_clientassetid) ");
        sb.append(" where 0 = 0 ");
        if (search != null && !search.equals("")) 
        {
            sb.append("and (t_training.s_name like ? OR t_client.s_name like ? OR t_clientasset.s_name like ?) ");
        }
        if (allclient == 1) 
        {
            sb.append("AND t_client.i_clientid > 0 ");
        } else 
        {
            if (!cids.equals(""))
            {
                sb.append("AND t_client.i_clientid IN (" + cids + ") ");
            } else 
            {
                sb.append("AND t_client.i_clientid < 0 ");
            }
            if (!assetids.equals(""))
            {
                sb.append("AND t_clientasset.i_clientassetid IN (" + assetids + ") ");
            }
        }
        if (clientIndex > 0) {
            sb.append("AND t_client.i_clientid =? ");
        }
        if (assetIndex > 0) {
            sb.append("AND t_clientasset.i_clientassetid =? ");
        }
        sb.append(" order by t_training.i_status, t_training.s_name ");
        if (count > 0) {
            sb.append(" limit ").append(next * count).append(", ").append(count);
        }
        String query = (sb.toString()).intern();
        sb.setLength(0);
        
        sb.append("select count(1) FROM t_training ");
        sb.append("LEFT JOIN t_client ON t_training.i_clientid = t_client.i_clientid ");
        sb.append("LEFT JOIN t_clientasset ON t_clientasset.i_clientassetid = t_training.i_clientassetid ");
        sb.append("WHERE 0=0 ");
        if (search != null && !search.equals("")) {
            sb.append("and (t_training.s_name like ? OR t_client.s_name like ? OR t_clientasset.s_name like ?) ");
        }
        if (allclient == 1) {
            sb.append("AND t_client.i_clientid > 0 ");
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
        if (clientIndex > 0) {
            sb.append("AND t_client.i_clientid =? ");
        }
        if (assetIndex > 0) {
            sb.append("AND t_clientasset.i_clientassetid =? ");
        }
        String countquery = (sb.toString()).intern();
        sb.setLength(0);
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            int scc = 0;
            if (search != null && !search.equals("")) 
            {
                pstmt.setString(++scc, "%" + (search) + "%");
                pstmt.setString(++scc, "%" + (search) + "%");
                pstmt.setString(++scc, "%" + (search) + "%");
            }
             if (clientIndex > 0) {
                pstmt.setInt(++scc, clientIndex);
            }
            if (assetIndex > 0) {
                pstmt.setInt(++scc, assetIndex);
            }
            logger.info("getTrainingByName :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String name, client, asset;
            int trainingId, status;
            while (rs.next()) 
            {
                trainingId = rs.getInt(1);
                name = rs.getString(2) != null ? rs.getString(2) : "";
                status = rs.getInt(3);
                client = rs.getString(4) != null ? rs.getString(4) : "";
                asset = rs.getString(5) != null ? rs.getString(5) : "";
                trainings.add(new TrainingInfo(trainingId, name, status, 0,client, asset));
            }
            rs.close();

            pstmt = conn.prepareStatement(countquery);
            scc = 0;
            if (search != null && !search.equals("")) {
                pstmt.setString(++scc, "%" + (search) + "%");
                pstmt.setString(++scc, "%" + (search) + "%");
                pstmt.setString(++scc, "%" + (search) + "%");
            }
            if (clientIndex > 0) {
                pstmt.setInt(++scc, clientIndex);
            }
            if (assetIndex > 0) {
                pstmt.setInt(++scc, assetIndex);
            }
            print(this,"getTrainingByName :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                trainingId = rs.getInt(1);
                trainings.add(new TrainingInfo(trainingId, "", 0, 0));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return trainings;
    }

    public ArrayList getFinalRecord(ArrayList l, String colId, int tp) {
        ArrayList list = null;
        HashMap record = null;
        TrainingInfo info;
        int total = 0;
        if (l != null) {
            total = l.size();
        }
        try {
            if (total > 0) {
                record = new HashMap();
                int i;
                for (i = 0; i < total; i++) {
                    info = (TrainingInfo) l.get(i);
                    record.put(getInfoValue(info, colId), info);
                }
            }
            Map map = sortByName(record, tp);
            Iterator it = map.keySet().iterator();
            list = new ArrayList();
            TrainingInfo rInfo;
            while (it.hasNext()) {
                String key = (String) it.next();
                int i;
                for (i = 0; i < total; i++) {
                    rInfo = (TrainingInfo) l.get(i);
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
    public String getInfoValue(TrainingInfo info, String i) {
        String infoval = "";
        if (i != null && i.equals("1")) {
            infoval = info.getClientName()!= null ? info.getClientName(): "";
        }
        if (i != null && i.equals("2")) {
            infoval = info.getAssetName()!= null ? info.getAssetName(): "";
        }
        if (i != null && i.equals("3")) {
            infoval = info.getName() != null ? info.getName() : "";
        }
        return infoval;
    }

    public TrainingInfo getTrainingDetailById(int trainingId) {
        TrainingInfo info = null;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT s_name, i_status, i_clientid, i_clientassetid FROM t_training where i_trainingid = ? ");
        String query = sb.toString().intern();
        sb.setLength(0);
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, trainingId);
            print(this,"getTrainingDetailById :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String name;
            int status, clientId, assetId;
            while (rs.next()) 
            {
                name = rs.getString(1) != null ? rs.getString(1) : "";
                status = rs.getInt(2);
                clientId = rs.getInt(3);
                assetId = rs.getInt(4);
                info = new TrainingInfo(trainingId, name, status, 0,clientId, assetId);
            }
        } catch (Exception exception) {
            print(this, "getTrainingDetailById :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return info;
    }

    public TrainingInfo getTrainingDetailByIdforDetail(int trainingId) {
        TrainingInfo info = null;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT t_training.s_name, t_training.i_status, t_client.s_name , t_clientasset.s_name FROM t_training "); 
        sb.append("left join t_client on (t_training.i_clientid = t_client.i_clientid) ");
        sb.append("left join  t_clientasset on (t_clientasset.i_clientassetid = t_training.i_clientassetid) ");
        sb.append("where t_training.i_trainingid = ? ");
        String query = sb.toString().intern();
        sb.setLength(0);
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, trainingId);
            print(this,"getTrainingDetailByIdforDetail :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String name, clientname, assetname;
            int status = 0;
            while (rs.next())
            {
                name = rs.getString(1) != null ? rs.getString(1): "";
                status = rs.getInt(2);
                clientname = rs.getString(3) != null ? rs.getString(3): "";
                assetname = rs.getString(4) != null ? rs.getString(4): "";
                info = new TrainingInfo(trainingId, name, status, clientname, assetname);
            }
            rs.close();
        } catch (Exception exception) {
            print(this, "getTrainingDetailByIdforDetail :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return info;
    }

    public int createTraining(TrainingInfo info) {
        int trainingId = 0;
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("insert into t_training ");
            sb.append("(s_name, i_status, i_userid, ts_regdate, ts_moddate, i_clientid,i_clientassetid) ");
            sb.append("values (?, ?, ?, ?, ?, ?, ?)");
            String query = (sb.toString()).intern();
            sb.setLength(0);

            conn = getConnection();
            pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            int scc = 0;
            pstmt.setString(++scc, (info.getName()));
            pstmt.setInt(++scc, info.getStatus());
            pstmt.setInt(++scc, info.getUserId());
            pstmt.setString(++scc, currDate1());
            pstmt.setString(++scc, currDate1());
            pstmt.setInt(++scc, info.getClientId());
            pstmt.setInt(++scc, info.getAssetId());
            print(this,"createTraining :: " + pstmt.toString());
            pstmt.executeUpdate();
            rs = pstmt.getGeneratedKeys();
            while (rs.next()) {
                trainingId = rs.getInt(1);
            }
        } catch (Exception exception) {
            print(this, "createTraining :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return trainingId;
    }

    public int updateTraining(TrainingInfo info) {
        int cc = 0;
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("update t_training set ");
            sb.append("s_name = ?, ");
            sb.append("i_clientid = ?, ");
            sb.append("i_clientassetid = ?, ");
            sb.append("i_status = ?, ");
            sb.append("i_userid = ?, ");
            sb.append("ts_moddate = ? ");
            sb.append("where i_trainingid = ? ");
            String query = (sb.toString()).intern();
            sb.setLength(0);

            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            int scc = 0;
            pstmt.setString(++scc, info.getName());
            pstmt.setInt(++scc, info.getClientId());
            pstmt.setInt(++scc, info.getAssetId());
            pstmt.setInt(++scc, info.getStatus());
            pstmt.setInt(++scc, info.getUserId());
            pstmt.setString(++scc, currDate1());
            pstmt.setInt(++scc, info.getTrainingId());
            print(this,"updateTraining :: " + pstmt.toString());
            cc = pstmt.executeUpdate();
        } catch (Exception exception) {
            print(this, "updateTraining :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return cc;
    }

    public int checkDuplicacy(int trainingId,int clientId, int assetId, String name) {
        int ck = 0;
        StringBuilder sb = new StringBuilder();
        sb.append("select i_trainingid FROM t_training where i_clientid = ? AND i_clientassetid = ? AND s_name = ? AND i_status in (1, 2)");
        if (trainingId > 0) {
            sb.append(" and i_trainingid != ? ");
        }
        String query = (sb.toString()).intern();
        sb.setLength(0);
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            int scc = 0;
            pstmt.setInt(++scc, clientId);
            pstmt.setInt(++scc, assetId);
            pstmt.setString(++scc, name);
            if (trainingId > 0) {
                pstmt.setInt(++scc, trainingId);
            }
            print(this,"checkDuplicacy :: " + pstmt.toString());
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

    public int deleteTraining(int trainingId, int userId, int status, String ipAddrStr, String iplocal) {
        int cc = 0;
        try {
            conn = getConnection();
            StringBuilder sb = new StringBuilder();
            sb.append("update t_training set ");
            sb.append("i_status = ?, ");
            sb.append("i_userid = ?, ");
            sb.append("ts_moddate = ? ");
            sb.append("where i_trainingid = ? ");
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
            pstmt.setInt(++scc, trainingId);

            cc = pstmt.executeUpdate();
        } catch (Exception exception) {
            print(this, "deleteTraining :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        createHistoryAccess(null, userId, ipAddrStr, iplocal, "Data Deleted", 62, trainingId);
        return cc;
    }

    public ArrayList getExcel(String search, int allclient, String permission, String cids, String assetids, int clientIndex, 
            int assetIndex) 
    {
        ArrayList trainings = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("select t_training.i_trainingid, t_training.s_name, t_training.i_status, t_client.s_name, t_clientasset.s_name FROM t_training ");
        sb.append("left join t_client on (t_client.i_clientid = t_training.i_clientid) ");
        sb.append("left join  t_clientasset on (t_clientasset.i_clientassetid = t_training.i_clientassetid) ");
        sb.append(" where 0 = 0 ");
        if (search != null && !search.equals("")) {
            sb.append("and (t_training.s_name like ? OR t_client.s_name like ? OR t_clientasset.s_name like ?) ");
        }
        if (allclient == 1) {
            sb.append("AND t_client.i_clientid > 0 ");
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
        if (clientIndex > 0) {
            sb.append("AND t_client.i_clientid =? ");
        }
        if (assetIndex > 0) {
            sb.append("AND t_clientasset.i_clientassetid =? ");
        }
        sb.append(" order by t_training.i_status, t_training.s_name ");
        String query = (sb.toString()).intern();
        sb.setLength(0);
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            int scc = 0;
            if (search != null && !search.equals("")) {
                pstmt.setString(++scc, "%" + (search) + "%");
                pstmt.setString(++scc, "%" + (search) + "%");
                pstmt.setString(++scc, "%" + (search) + "%");
            }
             if (clientIndex > 0) {
                pstmt.setInt(++scc, clientIndex);
            }
            if (assetIndex > 0) {
                pstmt.setInt(++scc, assetIndex);
            }
            logger.info("getExcel :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String name, client, asset;
            int trainingId, status;
            while (rs.next()) 
            {
                trainingId = rs.getInt(1);
                name = rs.getString(2) != null ? rs.getString(2) : "";
                status = rs.getInt(3);
                client = rs.getString(4) != null ? rs.getString(4) : "";
                asset = rs.getString(5) != null ? rs.getString(5) : "";
                trainings.add(new TrainingInfo(trainingId, name, status, 0,client, asset));
            }
            rs.close();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return trainings;
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
        coll.add(new TrainingInfo(-1, " All "));
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
                coll.add(new TrainingInfo(refId, refName));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return coll;
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
            coll.add(new TrainingInfo(-1, " All "));
            try
            {
                conn = getConnection();
                pstmt = conn.prepareStatement(query);
                pstmt.setInt(1, clientId);
                logger.info("getClientAsset :: " + pstmt.toString());
                rs = pstmt.executeQuery();
                int ddlValue;
                String ddlLabel;
                while (rs.next()) 
                {
                    ddlValue = rs.getInt(1);
                    ddlLabel = rs.getString(2) != null ? rs.getString(2) : "";
                    coll.add(new TrainingInfo(ddlValue, ddlLabel));
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                close(conn, pstmt, rs);
            }
        } else {
            coll.add(new TrainingInfo(-1, " All "));
        }
        return coll;
    }
    
    public Collection getClientsForAddEdit(String cids, int allclient, String permission) 
    {
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
        coll.add(new TrainingInfo(-1, " Select Client"));
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
                coll.add(new TrainingInfo(refId, refName));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return coll;
    }

    public Collection getClientAssetForAddEdit(int clientId, String assetids, int allclient, String permission) {
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
            coll.add(new TrainingInfo(-1, " Select Asset "));
            try
            {
                conn = getConnection();
                pstmt = conn.prepareStatement(query);
                pstmt.setInt(1, clientId);
                logger.info("getClientAsset :: " + pstmt.toString());
                rs = pstmt.executeQuery();
                int ddlValue;
                String ddlLabel;
                while (rs.next()) 
                {
                    ddlValue = rs.getInt(1);
                    ddlLabel = rs.getString(2) != null ? rs.getString(2) : "";
                    coll.add(new TrainingInfo(ddlValue, ddlLabel));
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                close(conn, pstmt, rs);
            }
        } else {
            coll.add(new TrainingInfo(-1, " Select Asset "));
        }
        return coll;
    }
}
