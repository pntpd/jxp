package com.web.jxp.contract;

import com.mysql.jdbc.Statement;
import com.web.jxp.base.Base;
import com.web.jxp.common.Common;
import static com.web.jxp.common.Common.*;
import java.io.File;
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
public class Contract extends Base {
    

    private static final Logger logger = Logger.getLogger(Class.class.getName());
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    public ArrayList getContractByName(String search, int next, int count,int allclient, String permission, String cids, String assetids ) {
        ArrayList contracts = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT t_contract.i_contractid, t_contract.s_name, t_contract.i_status, t_client.s_name, ");
        sb.append("t_clientasset.s_name, t_contract.i_type, t1.s_name ");
        sb.append("FROM t_contract ");
        sb.append("LEFT JOIN t_client ON (t_client.i_clientid = t_contract.i_clientid) ");
        sb.append("LEFT JOIN t_clientasset ON (t_clientasset.i_clientassetid = t_contract.i_clientassetid) ");
        sb.append("LEFT JOIN t_contract AS t1 ON (t_contract.i_refid = t1.i_contractid) ");
        sb.append("WHERE 0 = 0 ");
        if(search != null && !search.equals(""))
            sb.append("AND (t_contract.s_name LIKE ? OR t_client.s_name LIKE ? OR t_clientasset.s_name LIKE ?) ");        
        sb.append("ORDER BY t_contract.i_status, t_contract.s_name ");
        if(count > 0)
            sb.append(" LIMIT ").append(next*count).append(", ").append(count);
        String query = (sb.toString()).intern();
        sb.setLength(0);
        
        sb.append("SELECT COUNT(1) FROM t_contract ");
        sb.append("LEFT JOIN t_client ON (t_client.i_clientid = t_contract.i_clientid) ");
        sb.append("LEFT JOIN t_clientasset ON (t_clientasset.i_clientassetid = t_contract.i_clientassetid) ");
        sb.append("LEFT JOIN t_contract AS t1 ON (t_contract.i_refid = t1.i_contractid) ");
        sb.append("WHERE 0 = 0 ");
        if(search != null && !search.equals(""))
            sb.append("AND (t_contract.s_name LIKE ? OR t_client.s_name LIKE ? OR t_clientasset.s_name LIKE ?) ");
       
        String countquery = (sb.toString()).intern();
        sb.setLength(0);
        try
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            int scc = 0;
            if(search != null && !search.equals(""))
            {
                pstmt.setString(++scc, "%"+(search)+"%");
                pstmt.setString(++scc, "%"+(search)+"%");
                pstmt.setString(++scc, "%"+(search)+"%");
            }
            logger.info("getContractByName :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String name, clientName, assetName, refName;
            int contractId, status, type;
            while (rs.next())
            {
                contractId = rs.getInt(1);
                name = rs.getString(2) != null ? rs.getString(2) : "";
                status = rs.getInt(3);
                clientName = rs.getString(4) != null ? rs.getString(4) : "";
                assetName = rs.getString(5) != null ? rs.getString(5) : "";
                type = rs.getInt(6);
                refName = rs.getString(7) != null ? rs.getString(7) : "";                
                if (!refName.equals("")) {
                    if (!name.equals("")) {
                        name += "("+refName+")";
                    }
                }
                contracts.add(new ContractInfo(contractId, name, status, clientName, assetName, type, refName));
            }
            rs.close();
            
            pstmt = conn.prepareStatement(countquery);
            scc = 0;
            if(search != null && !search.equals(""))
            {
                pstmt.setString(++scc, "%"+(search)+"%");
                pstmt.setString(++scc, "%"+(search)+"%");
                pstmt.setString(++scc, "%"+(search)+"%");
            }
            print(this,"getContractByName :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            while (rs.next())
            {
                contractId = rs.getInt(1);
                contracts.add(new ContractInfo(contractId, "", 0, "", "", 0, ""));
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
        return contracts;
    }

    public ArrayList getFinalRecord(ArrayList l, String colId, int tp) {
        ArrayList list = null;
        HashMap record = null;
        ContractInfo info;
        int total = 0;
        if (l != null) {
            total = l.size();
        }
        try {
            if (total > 0) {
                record = new HashMap();
                int i;
                for (i = 0; i < total; i++) {
                    info = (ContractInfo) l.get(i);
                    record.put(getInfoValue(info, colId), info);
                }
            }
            Map map = sortByName(record, tp);
            Iterator it = map.keySet().iterator();
            list = new ArrayList();
            ContractInfo rInfo;
            while (it.hasNext()) {
                String key = (String) it.next();
                int i;
                for (i = 0; i < total; i++) {
                    rInfo = (ContractInfo) l.get(i);
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
    public String getInfoValue(ContractInfo info, String i) {
        String infoval = "";
        if (i != null && i.equals("1")) {
            infoval = info.getClientname() != null ? info.getClientname() : "";
        }
        if (i != null && i.equals("2")) {
            infoval = info.getAssetName() != null ? info.getAssetName() : "";
        }
        if (i != null && i.equals("3")) {
            infoval = info.getName() != null ? info.getName() : "";
        }
        return infoval;
    }

    public ContractInfo getContractDetailById(int contractId) 
    {
        ContractInfo info = null;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT s_name, i_clientid, s_description, i_status, i_type, i_refid, s_wids, ");
        sb.append("s_eids, s_dids, s_tids, s_lids, s_vids, i_clientassetid, s_description2, s_description3, s_filename, s_nids ");
        sb.append("FROM t_contract WHERE i_contractid = ? ");
        String query = sb.toString().intern();
        sb.setLength(0);
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, contractId);
            print(this, "getContractDetailById :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String name, description, workexp, edudetail, docdetail, certificate, 
                    language, vaccine,description2, description3,filename, nomineedetail;
            int status, clientid, type, refId, assetId;
            while (rs.next())
            {
                name = rs.getString(1) != null ? rs.getString(1) : "";
                clientid = rs.getInt(2);
                description = rs.getString(3) != null ? rs.getString(3) : "";
                status = rs.getInt(4);
                type = rs.getInt(5);
                refId = rs.getInt(6);
                workexp = rs.getString(7) != null ? rs.getString(7) : "";
                edudetail = rs.getString(8) != null ? rs.getString(8) : "";
                docdetail = rs.getString(9) != null ? rs.getString(9) : "";
                certificate = rs.getString(10) != null ? rs.getString(10) : "";
                language = rs.getString(11) != null ? rs.getString(11) : "";
                vaccine = rs.getString(12) != null ? rs.getString(12) : "";
                assetId = rs.getInt(13);
                description2 = rs.getString(14) != null ? rs.getString(14) : "";
                description3 = rs.getString(15) != null ? rs.getString(15) : "";
                filename = rs.getString(16) != null ? rs.getString(16) : "";
                nomineedetail = rs.getString(17) != null ? rs.getString(17) : "";
                
                info = new ContractInfo(contractId, name, clientid, description, status, type, 
                        refId, workexp, edudetail, docdetail, certificate, language, vaccine, assetId,
                        description2, description3,filename, nomineedetail);
            }
        } catch (Exception exception) {
            print(this, "getContractDetailById :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return info;
    }

    public ContractInfo getContractDetailByIdforDetail(int contractId) {
        ContractInfo info = null;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT t_contract.s_name, t_contract.i_status , t_client.s_name, t_contract.s_description, ");
        sb.append("t_contract.i_type, t_clientasset.s_name, t_contract.s_wids, t_contract.s_eids, ");
        sb.append("t_contract.s_dids, t_contract.s_tids, t_contract.s_lids, t_contract.s_vids, t_contract.i_refid, t1.s_name, ");
        sb.append("t_contract.s_description2, t_contract.s_description3, t_contract.s_filename, t_contract.s_nids ");
        sb.append("FROM t_contract ");
        sb.append("LEFT JOIN t_client ON (t_client.i_clientid =  t_contract.i_clientid) ");
        sb.append("LEFT JOIN t_clientasset ON (t_clientasset.i_clientassetid = t_contract.i_clientassetid) ");
        sb.append("LEFT JOIN t_contract AS t1 ON (t_contract.i_refid = t1.i_contractid) ");
        sb.append("WHERE t_contract.i_contractid = ? ");
        String query = sb.toString().intern();
        sb.setLength(0);
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, contractId);
            print(this, "getContractDetailByIdforDetail :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String name, clientname, description, assetname, workexp, edudetail, docdetail, 
            certdetail, language, vaccine,refname, description2,description3, filename, nomineedetail;
            int status = 0, type, refId;
            while (rs.next()) 
            {
                name = rs.getString(1) != null ? rs.getString(1) : "";
                status = rs.getInt(2);
                clientname = rs.getString(3) != null ? rs.getString(3) : "";
                description = rs.getString(4) != null ? rs.getString(4) : "";
                type = rs.getInt(5);
                assetname = rs.getString(6) != null ? rs.getString(6) : "";
                workexp = rs.getString(7) != null ? rs.getString(7) : "";
                edudetail = rs.getString(8) != null ? rs.getString(8) : "";
                docdetail = rs.getString(9) != null ? rs.getString(9) : "";
                certdetail = rs.getString(10) != null ? rs.getString(10) : "";
                language = rs.getString(11) != null ? rs.getString(11) : "";
                vaccine = rs.getString(12) != null ? rs.getString(12) : "";
                refId = rs.getInt(13);
                refname = rs.getString(14) != null ? rs.getString(14) : "";
                description2 = rs.getString(15) != null ? rs.getString(15) : "";
                description3 = rs.getString(16) != null ? rs.getString(16) : "";
                filename = rs.getString(17) != null ? rs.getString(17) : "";
                nomineedetail = rs.getString(18) != null ? rs.getString(18) : "";
                if (description != null && !description.equals("")) 
                {
                    String add_path = getMainPath("add_contractfile");
                    description = readHTMLFile(description, add_path);
                }
                info = new ContractInfo(contractId, name, status, clientname, description, type, 
                        assetname, workexp, edudetail, docdetail, certdetail, language, vaccine, 
                        refId, refname, description2,description3, filename, nomineedetail);
            }
            rs.close();
        } catch (Exception exception) {
            print(this, "getContractDetailByIdforDetail :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return info;
    }

    public int createContract(ContractInfo info)
    {
        int contractId = 0;
        try
        {
            StringBuilder sb = new StringBuilder();
            sb.append("INSERT INTO t_contract ");
            sb.append("(s_name, i_status, i_userid,i_clientid, i_clientassetid, s_description, ts_regdate, ts_moddate, ");
            sb.append("i_type, i_refid, s_wids, s_eids, s_dids, s_tids, s_lids, s_vids, s_description2, s_description3, s_filename, s_nids ) ");
            sb.append("VALUES (?, ?, ?, ?, ?,   ?, ?, ?, ?, ?,   ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )");
            String query = (sb.toString()).intern();
            sb.setLength(0);

            conn = getConnection();
            pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            int scc = 0;
            pstmt.setString(++scc, (info.getName()));
            pstmt.setInt(++scc, info.getStatus());
            pstmt.setInt(++scc, info.getUserId());
            pstmt.setInt(++scc, info.getClientId());
            pstmt.setInt(++scc, info.getAssetId());
            pstmt.setString(++scc, info.getDescription());
            pstmt.setString(++scc, currDate1());
            pstmt.setString(++scc, currDate1());
            pstmt.setInt(++scc, info.getType());
            pstmt.setInt(++scc, info.getRefId());
            pstmt.setString(++scc, info.getExpColumn());
            pstmt.setString(++scc, info.getEduColumn());
            pstmt.setString(++scc, info.getDocColumn());
            pstmt.setString(++scc, info.getTrainingColumn());
            pstmt.setString(++scc, info.getLanguageColumn());
            pstmt.setString(++scc, info.getVaccineColumn());
            pstmt.setString(++scc, info.getDescription2());
            pstmt.setString(++scc, info.getDescription3());
            pstmt.setString(++scc, info.getFilename());
            pstmt.setString(++scc, info.getNomineeColumn());
            print(this, "createContract :: " + pstmt.toString());
            pstmt.executeUpdate();
            rs = pstmt.getGeneratedKeys();
            while (rs.next()) {
                contractId = rs.getInt(1);
            }
        } catch (Exception exception) {
            print(this, "createContract :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return contractId;
    }

    public int updateContract(ContractInfo info) 
    {
        int cc = 0;
        try
        {
            StringBuilder sb = new StringBuilder();
            sb.append("UPDATE t_contract SET ");
            sb.append("s_name = ?, ");
            sb.append("i_clientid = ?, ");
            sb.append("i_clientassetid = ?, ");
            sb.append("s_description = ?, ");
            sb.append("s_description2 = ?, ");
            sb.append("s_description3 = ?, ");
            if (info.getFilename() != null && !info.getFilename().equals("")) {
                sb.append("s_filename = ?, ");
            }
            sb.append("i_type = ?, ");
            sb.append("i_refid = ?, ");
            sb.append("i_status = ?, "); 
            sb.append("i_userid = ?, ");
            sb.append("s_wids = ?, ");
            sb.append("s_eids = ?, ");
            sb.append("s_dids = ?, ");
            sb.append("s_tids = ?, ");            
            sb.append("s_lids = ?, ");            
            sb.append("s_vids = ?, ");            
            sb.append("s_nids = ?, ");            
            sb.append("ts_moddate = ? ");
            sb.append("WHERE i_contractid = ? ");
            String query = (sb.toString()).intern();
            sb.setLength(0);

            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            int scc = 0;
            pstmt.setString(++scc, info.getName());
            pstmt.setInt(++scc, info.getClientId());
            pstmt.setInt(++scc, info.getAssetId());
            pstmt.setString(++scc, info.getDescription());
            pstmt.setString(++scc, info.getDescription2());
            pstmt.setString(++scc, info.getDescription3());
            if (info.getFilename() != null && !info.getFilename().equals("")) {
                pstmt.setString(++scc, info.getFilename());
            }            
            pstmt.setInt(++scc, info.getType());
            pstmt.setInt(++scc, info.getRefId());
            pstmt.setInt(++scc, info.getStatus());
            pstmt.setInt(++scc, info.getUserId());
            pstmt.setString(++scc, info.getExpColumn());
            pstmt.setString(++scc, info.getEduColumn());
            pstmt.setString(++scc, info.getDocColumn());
            pstmt.setString(++scc, info.getTrainingColumn());
            pstmt.setString(++scc, info.getLanguageColumn());
            pstmt.setString(++scc, info.getVaccineColumn());
            pstmt.setString(++scc, info.getNomineeColumn());
            pstmt.setString(++scc, currDate1());
            pstmt.setInt(++scc, info.getContractId());
            print(this, "updateContract :: " + pstmt.toString());
            cc = pstmt.executeUpdate();
        } catch (Exception exception) {
            print(this, "updateContract :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return cc;
    }

    public int checkDuplicacy(int contractId, String name, int clientId, int assetId) {
        int ck = 0;
        StringBuilder sb = new StringBuilder();
        sb.append("select i_contractid FROM t_contract where s_name = ? and i_clientid = ?  and i_clientassetid = ? and i_status in (1, 2)");
        if (contractId > 0) {
            sb.append(" and i_contractid != ? ");
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
            pstmt.setInt(++scc, assetId);
            if (contractId > 0) {
                pstmt.setInt(++scc, contractId);
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

    public int deleteContract(int contractId, int userId, int status, String ipAddrStr, String iplocal) {
        int cc = 0;
        try 
        {
            conn = getConnection();
            StringBuilder sb = new StringBuilder();
            sb.append("update t_contract set ");
            sb.append("i_status = ?, ");
            sb.append("i_userid = ?, ");
            sb.append("ts_moddate = ? ");
            sb.append("where i_contractid = ? ");
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
            pstmt.setInt(++scc, contractId);

            cc = pstmt.executeUpdate();
        } catch (Exception exception) {
            print(this, "deleteContract :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        createHistoryAccess(null, userId, ipAddrStr, iplocal, "Data Deleted", 98, contractId);
        return cc;
    }

    public ArrayList getListForExcel(String search, int allclient, String permission, String cids, String assetids) 
    {
        ArrayList list = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT t_contract.s_name, t_contract.i_status, t_client.s_name FROM t_contract ");
        sb.append("LEFT JOIN t_client on (t_contract.i_clientid = t_client.i_clientid) ");
        sb.append("WHERE 0 = 0 ");
        if (search != null && !search.equals("")) {
            sb.append("AND (t_contract.s_name LIKE ? OR t_client.s_name LIKE ?) ");
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
        sb.append(" ORDER BY t_contract.i_status, t_client.s_name, t_contract.s_name ");
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
                list.add(new ContractInfo(0, name, status, clientname));
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

        sb.append("ORDER BY  s_name");
        String query = (sb.toString()).intern();
        sb.setLength(0);
        coll.add(new ContractInfo(-1, " Select Client"));
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            rs = pstmt.executeQuery();
            int refId;
            String refName;
            while (rs.next()) {
                refId = rs.getInt(1);
                refName = rs.getString(2) != null ? rs.getString(2) : "";
                coll.add(new ContractInfo(refId, refName));
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
            coll.add(new ContractInfo(-1, " Select Asset "));
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
                    coll.add(new ContractInfo(ddlValue, ddlLabel));
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                close(conn, pstmt, rs);
            }
        } else {
            coll.add(new ContractInfo(-1, " Select Asset "));
        }
        return coll;
    }
    
    //Contract list
    public Collection getContractList(int assetId) {
        Collection coll = new LinkedList();
        StringBuilder sb = new StringBuilder();
        if(assetId > 0)
        {
            sb.append("SELECT i_contractid, s_name FROM t_contract WHERE i_status = 1 AND i_type= 1 AND i_clientassetid= ? ORDER BY  s_name");
            String query = (sb.toString()).intern();
            sb.setLength(0);
            coll.add(new ContractInfo(-1, "Select contract"));
            try 
            {
                conn = getConnection();            
                pstmt = conn.prepareStatement(query);
                pstmt.setInt(1, assetId);
                logger.info("getContractList :: " + pstmt.toString());
                rs = pstmt.executeQuery();
                int id;
                String name;
                while (rs.next()) 
                {
                    id = rs.getInt(1);
                    name = rs.getString(2) != null ? rs.getString(2) : "";
                    coll.add(new ContractInfo(id, name));
                }
           
            } catch (Exception e) {
                e.printStackTrace();

            } finally {
                close(conn, pstmt, rs);
            }
        }else{
              coll.add(new ContractInfo(-1, "Select contract")); 
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
                str += ", Approved By ";
            }
        }
        if (tids.contains("7")) {
            if (str.equals("")) {
                str = "Certification No";
            } else {
                str += ", Certification No ";
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
    
    // For Nominee
    public String getNomineeFromId(String nids)
    {
        String str = "";
        if (nids.contains("1")) {
            if (str.equals("")) {
                str = "Name";
            } else {
                str += ", Name";
            }
        }
        if (nids.contains("2")) {
            if (str.equals("")) {
                str = "Contact Number";
            } else {
                str += ", Contact Number";
            }
        }
        if (nids.contains("3")) {
            if (str.equals("")) {
                str = "Relation";
            } else {
                str += ", Relation";
            }
        }
        if (nids.contains("4")) {
            if (str.equals("")) {
                str = "Age Of Nominee";
            } else {
                str += ", Age Of Nominee";
            }
        }
        if (nids.contains("5")) {
            if (str.equals("")) {
                str = "Address";
            } else {
                str += ", Address";
            }
        }
        if (nids.contains("6")) {
            if (str.equals("")) {
                str = "Percentage Of Distribution";
            } else {
                str += ", Percentage Of Distribution";
            }
        }
        return str;
    }
    
    public int deleteFile(int contractId, int userId) {
        int cc = 0;
        try
        {
            StringBuilder sb = new StringBuilder();
            conn = getConnection();
            String query = "SELECT s_filename FROM t_contract WHERE i_contractid = ?";
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, contractId);
            logger.info("get filename :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String filename ="";
            while (rs.next())
            {
                filename = rs.getString(1) != null ? rs.getString(1): "";  
            }
            rs.close();

            String path = getMainPath("add_contractfile");   
            String deletePath = path + filename;
            File file = new File(deletePath);
            if (file.exists()) 
            {
                file.delete();
            }

            sb.append("UPDATE t_contract set ");
            sb.append("s_filename = '', ");
            sb.append("i_userid = ?, ");
            sb.append("ts_moddate = ? ");
            sb.append("where i_contractid = ? ");
            query = (sb.toString()).intern();
            sb.setLength(0);

            pstmt = conn.prepareStatement(query);
            int scc = 0;
            pstmt.setInt(++scc, userId);
            pstmt.setString(++scc, currDate1());
            pstmt.setInt(++scc, contractId);
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
