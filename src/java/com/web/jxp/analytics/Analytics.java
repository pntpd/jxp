package com.web.jxp.analytics;

import com.web.jxp.base.Base;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Collection;
import java.util.LinkedList;
import java.util.logging.Logger;

public class Analytics extends Base {

    private static final Logger logger = Logger.getLogger(Class.class.getName());
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    public Collection getClients(String cids, int allclient, String permission) 
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
        coll.add(new AnalyticsInfo(-1, " Select Client"));
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
                coll.add(new AnalyticsInfo(refId, refName));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return coll;
    }
    
    public Collection getClientAsset(int clientId, String assetids, int allclient, String permission)
    {
        Collection coll = new LinkedList();
        if (clientId > 0) 
        {
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
            coll.add(new AnalyticsInfo(-1, " Select Asset "));
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
                    coll.add(new AnalyticsInfo(ddlValue, ddlLabel));
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                close(conn, pstmt, rs);
            }
        } else {
            coll.add(new AnalyticsInfo(-1, " Select Asset "));
        }
        return coll;
    }
    
    public String geturl( int assetId, int type) 
    {
        String url = "", query = "";
        if(type > 0)
        {
            if(type == 1)
                query = "select s_url from t_clientasset where i_clientassetid = ?";
            else if(type == 2)
                query = "select s_url_training from t_clientasset where i_clientassetid = ?";
            try 
            {
                conn = getConnection();
                pstmt = conn.prepareStatement(query);
                pstmt.setInt(1, assetId);
                logger.info("geturl :: " + pstmt.toString());
                rs = pstmt.executeQuery();
                while (rs.next()) 
                {
                    url = rs.getString(1) != null ? rs.getString(1) : "";
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
        }
        return url;
    }
}
