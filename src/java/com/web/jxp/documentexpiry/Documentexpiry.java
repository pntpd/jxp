package com.web.jxp.documentexpiry;

import com.mysql.jdbc.Statement;
import com.web.jxp.base.Base;
import com.web.jxp.base.StatsInfo;
import com.web.jxp.base.Template;
import static com.web.jxp.common.Common.*;
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

public class Documentexpiry extends Base 
{
    private static final Logger logger = Logger.getLogger(Class.class.getName());
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    public ArrayList getDocumentexpiryByName( int documentId, int type, int exp,int clientIdIndex, int assetIdIndex, 
            int allclient, String permission, String cids, String assetids, int coursenameId,int dropdownId, int healthId, String search)
    {
        ArrayList list = new ArrayList();
        StringBuilder sb = new StringBuilder();
        if(dropdownId ==2)
        {
            sb.append("SELECT t_govdoc.i_govid, t_govdoc.i_candidateid, CONCAT_WS(' ', c.s_firstname, c.s_middlename, c.s_lastname), t_doctype.s_doc, ");
            sb.append("DATE_FORMAT(t_govdoc.d_expirydate, '%d-%b-%Y'), t_govdoc.i_remind, t_client.s_name, t_clientasset.s_name ");
            sb.append("FROM t_govdoc ");
            sb.append("LEFT JOIN t_candidate AS c ON (c.i_candidateid = t_govdoc.i_candidateid) ");
            sb.append("LEFT JOIN t_client ON (c.i_clientid = t_client.i_clientid) ");
            sb.append("LEFT JOIN t_clientasset ON (c.i_clientassetid = t_clientasset.i_clientassetid) ");
            sb.append("LEFT JOIN t_doctype ON (t_doctype.i_doctypeid = t_govdoc.i_doctypeid) ");
            sb.append("WHERE t_govdoc.i_doctypeid in(2,6,9) AND t_govdoc.i_status = 1 AND d_expirydate != '0000-00-00' ");            
            if (allclient == 1) 
            {
                sb.append(" AND c.i_clientid >= -1 ");
            } 
            else 
            {
                if (!cids.equals("")) {
                    sb.append(" AND (c.i_clientid IN (" + cids + ") OR c.i_clientid <= 0)  ");
                } 
                else 
                {
                    sb.append(" AND c.i_clientid <= 0 ");
                }
                if (!assetids.equals("")) {
                    sb.append(" AND (c.i_clientassetid IN (" + assetids + ") OR c.i_clientassetid <= 0) ");
                }
            }
            if (clientIdIndex > 0) {
                sb.append("AND c.i_clientid = ? ");
            }
            if (assetIdIndex > 0) {
                sb.append("AND c.i_clientassetid = ? ");
            }
            if(documentId > 0){
                sb.append("AND t_govdoc.i_doctypeid = ? ");
            }
            if (search != null && !search.equals("")) {
                sb.append("AND  t_govdoc.i_candidateid LIKE ? ");                
            }
            if(type > 0)
            {
                if(type == 1)
                    sb.append(" AND t_govdoc.i_remind <= 0 ");
                else if(type == 2)
                    sb.append(" AND t_govdoc.i_remind > 0 ");
            }
            if(exp > 0)
            {
                if(exp == 1)
                    sb.append(" AND d_expirydate <= DATE_ADD(CURRENT_DATE(), INTERVAL 10 DAY) AND d_expirydate >= CURRENT_DATE() ");
                else if(exp == 3)
                    sb.append(" AND d_expirydate <= DATE_ADD(CURRENT_DATE(), INTERVAL 90 DAY) AND d_expirydate >= CURRENT_DATE() ");
                else if(exp == 4)
                    sb.append(" AND d_expirydate <= DATE_ADD(CURRENT_DATE(), INTERVAL 180 DAY) AND d_expirydate >= CURRENT_DATE() ");
                else if(exp == 5)
                    sb.append(" AND d_expirydate <= DATE_ADD(CURRENT_DATE(), INTERVAL 365 DAY) AND d_expirydate >= CURRENT_DATE() ");
                else if(exp == 2)
                    sb.append(" AND d_expirydate < CURRENT_DATE() ");
            }
            sb.append("ORDER BY t_doctype.s_doc, d_expirydate ");       
        }
        else if( dropdownId ==1)
        {
            sb.append("SELECT  t_trainingandcert.i_tcid, t_candidate.i_candidateid,  CONCAT_WS(' ', t_candidate.s_firstname, t_candidate.s_middlename, t_candidate.s_lastname), "); 
            sb.append("t_coursename.s_name, DATE_FORMAT(t_trainingandcert.d_expirydate, '%d-%b-%Y'), t_trainingandcert.i_remind, t_client.s_name, t_clientasset.s_name ");
            sb.append("FROM t_trainingandcert  ");
            sb.append("LEFT JOIN  t_coursename ON (t_coursename.i_coursenameid = t_trainingandcert.i_coursenameid) ");
            sb.append("LEFT JOIN t_candidate ON(t_candidate.i_candidateid = t_trainingandcert.i_candidateid) ");
            sb.append("LEFT JOIN t_client ON (t_candidate.i_clientid = t_client.i_clientid) ");
            sb.append("LEFT JOIN t_clientasset ON (t_candidate.i_clientassetid = t_clientasset.i_clientassetid) ");
            sb.append("WHERE t_trainingandcert.i_status = 1 AND t_trainingandcert.d_expirydate != '0000-00-00' ");
            
            if (allclient == 1) 
            {
                sb.append(" AND t_candidate.i_clientid >= -1 ");
            } 
            else 
            {
                if (!cids.equals("")) {
                    sb.append(" AND (t_candidate.i_clientid IN (" + cids + ") OR t_candidate.i_clientid <= 0)  ");
                } 
                else 
                {
                    sb.append(" AND t_candidate.i_clientid <= 0 ");
                }
                if (!assetids.equals("")) {
                    sb.append(" AND (t_candidate.i_clientassetid IN (" + assetids + ") OR t_candidate.i_clientassetid <= 0) ");
                }
            }
            if (clientIdIndex > 0) {
                sb.append("AND t_candidate.i_clientid = ? ");
            }
            if (assetIdIndex > 0) {
                sb.append("AND t_candidate.i_clientassetid = ? ");
            }
            if(coursenameId > 0){
                sb.append("AND t_coursename.i_coursenameid = ? ");
            }
            if (search != null && !search.equals("")) {
                sb.append("AND  t_candidate.i_candidateid LIKE ? ");                
            }
            if(type > 0)
            {
                if(type == 1)
                    sb.append(" AND t_trainingandcert.i_remind <= 0 ");
                else if(type == 2)
                    sb.append(" AND t_trainingandcert.i_remind > 0 ");
            }
            if(exp > 0)
            {
                if(exp == 1)
                    sb.append(" AND t_trainingandcert.d_expirydate <= DATE_ADD(CURRENT_DATE(), INTERVAL 10 DAY) AND t_trainingandcert.d_expirydate >= CURRENT_DATE() ");
                else if(exp == 3)
                    sb.append(" AND t_trainingandcert.d_expirydate <= DATE_ADD(CURRENT_DATE(), INTERVAL 90 DAY) AND t_trainingandcert.d_expirydate >= CURRENT_DATE() ");
                else if(exp == 4)
                    sb.append(" AND t_trainingandcert.d_expirydate <= DATE_ADD(CURRENT_DATE(), INTERVAL 180 DAY) AND t_trainingandcert.d_expirydate >= CURRENT_DATE() ");
                else if(exp == 5)
                    sb.append(" AND t_trainingandcert.d_expirydate <= DATE_ADD(CURRENT_DATE(), INTERVAL 365 DAY) AND t_trainingandcert.d_expirydate >= CURRENT_DATE() ");
                else if(exp == 2)
                    sb.append(" AND t_trainingandcert.d_expirydate < CURRENT_DATE() ");
            }
            sb.append("ORDER BY t_trainingandcert.d_expirydate ");
        }
        else if( dropdownId ==3)
        {
            if(healthId ==1)
            {
                sb.append("SELECT t_healthdeclaration.i_healthid, t_candidate.i_candidateid, ");
                sb.append("CONCAT_WS(' ', t_candidate.s_firstname, t_candidate.s_middlename, t_candidate.s_lastname), t_healthdeclaration.s_ogukmedicalftw ,  ");
                sb.append("DATE_FORMAT(t_healthdeclaration.d_ogukexp, '%d-%b-%Y'), t_healthdeclaration.i_remind, ");
                sb.append("t_client.s_name, t_clientasset.s_name ");
                sb.append("FROM t_healthdeclaration ");
                sb.append("LEFT JOIN t_candidate ON(t_candidate.i_candidateid = t_healthdeclaration.i_candidateid) ");
                sb.append("LEFT JOIN t_client ON (t_candidate.i_clientid = t_client.i_clientid) ");
                sb.append("LEFT JOIN t_clientasset ON (t_candidate.i_clientassetid = t_clientasset.i_clientassetid) ");
                sb.append("WHERE t_healthdeclaration.i_status = 1 AND t_healthdeclaration.d_ogukexp != '0000-00-00' ");            
                if (allclient == 1) 
                {
                    sb.append(" AND t_candidate.i_clientid >= -1 ");
                } 
                else 
                {
                    if (!cids.equals("")) {
                        sb.append(" AND (t_candidate.i_clientid IN (" + cids + ") OR t_candidate.i_clientid <= 0)  ");
                    } 
                    else 
                    {
                        sb.append(" AND t_candidate.i_clientid <= 0 ");
                    }
                    if (!assetids.equals("")) {
                        sb.append(" AND (t_candidate.i_clientassetid IN (" + assetids + ") OR t_candidate.i_clientassetid <= 0) ");
                    }
                }
                if (clientIdIndex > 0) {
                    sb.append("AND t_candidate.i_clientid = ? ");
                }
                if (assetIdIndex > 0) {
                    sb.append("AND t_candidate.i_clientassetid = ? ");
                }
                if (search != null && !search.equals("")) {
                    sb.append("AND  t_candidate.i_candidateid LIKE ? ");                
                }
                if(type > 0)
                {
                    if(type == 1)
                        sb.append(" AND t_healthdeclaration.i_remind <= 0 ");
                    else if(type == 2)
                        sb.append(" AND t_healthdeclaration.i_remind > 0 ");
                }
                if(exp > 0)
                {
                    if(healthId == 1)
                    {
                        if(exp == 1)
                            sb.append(" AND t_healthdeclaration.d_ogukexp <= DATE_ADD(CURRENT_DATE(), INTERVAL 10 DAY) AND t_healthdeclaration.d_ogukexp >= CURRENT_DATE() ");
                        else if(exp == 3)
                            sb.append(" AND t_healthdeclaration.d_ogukexp <= DATE_ADD(CURRENT_DATE(), INTERVAL 90 DAY) AND t_healthdeclaration.d_ogukexp >= CURRENT_DATE() ");
                        else if(exp == 4)
                            sb.append(" AND t_healthdeclaration.d_ogukexp <= DATE_ADD(CURRENT_DATE(), INTERVAL 180 DAY) AND t_healthdeclaration.d_ogukexp >= CURRENT_DATE() ");
                        else if(exp == 5)
                            sb.append(" AND t_healthdeclaration.d_ogukexp <= DATE_ADD(CURRENT_DATE(), INTERVAL 365 DAY) AND t_healthdeclaration.d_ogukexp >= CURRENT_DATE() ");
                        else if(exp == 2)
                            sb.append(" AND t_healthdeclaration.d_ogukexp < CURRENT_DATE() ");
                    }
                }
                sb.append("ORDER BY t_healthdeclaration.d_ogukexp ");
                }
                else if(healthId ==2)
                {                    
                    sb.append("SELECT t_healthdeclaration.i_healthid, t_candidate.i_candidateid, ");
                    sb.append("CONCAT_WS(' ', t_candidate.s_firstname, t_candidate.s_middlename, t_candidate.s_lastname), t_healthdeclaration.s_medifitcert ,  ");
                    sb.append("DATE_FORMAT(t_healthdeclaration.d_medifitcertexp, '%d-%b-%Y'), t_healthdeclaration.i_remind2, ");
                    sb.append("t_client.s_name, t_clientasset.s_name ");
                    sb.append("FROM t_healthdeclaration ");
                    sb.append("LEFT JOIN t_candidate ON(t_candidate.i_candidateid = t_healthdeclaration.i_candidateid) ");
                    sb.append("LEFT JOIN t_client ON (t_candidate.i_clientid = t_client.i_clientid) ");
                    sb.append("LEFT JOIN t_clientasset ON (t_candidate.i_clientassetid = t_clientasset.i_clientassetid) ");
                    sb.append("WHERE t_healthdeclaration.i_status = 1 AND t_healthdeclaration.d_medifitcertexp != '0000-00-00' ");            
                    if (allclient == 1) 
                    {
                        sb.append(" AND t_candidate.i_clientid >= -1 ");
                    } 
                    else 
                    {
                        if (!cids.equals("")) {
                            sb.append(" AND (t_candidate.i_clientid IN (" + cids + ") OR t_candidate.i_clientid <= 0)  ");
                        } 
                        else 
                        {
                            sb.append(" AND t_candidate.i_clientid <= 0 ");
                        }
                        if (!assetids.equals("")) {
                            sb.append(" AND (t_candidate.i_clientassetid IN (" + assetids + ") OR t_candidate.i_clientassetid <= 0) ");
                        }
                    }
                    if (clientIdIndex > 0) {
                        sb.append("AND t_candidate.i_clientid = ? ");
                    }
                    if (assetIdIndex > 0) {
                        sb.append("AND t_candidate.i_clientassetid = ? ");
                    }
                    if(type > 0)
                    {
                        if(type == 1)
                            sb.append(" AND t_healthdeclaration.i_remind <= 0 ");
                        else if(type == 2)
                            sb.append(" AND t_healthdeclaration.i_remind > 0 ");
                    }
                    if(exp > 0)
                    {                      
                        if(exp == 1)
                            sb.append(" AND t_healthdeclaration.d_medifitcertexp <= DATE_ADD(CURRENT_DATE(), INTERVAL 10 DAY) AND t_healthdeclaration.d_medifitcertexp >= CURRENT_DATE() ");
                        else if(exp == 3)
                            sb.append(" AND t_healthdeclaration.d_medifitcertexp <= DATE_ADD(CURRENT_DATE(), INTERVAL 90 DAY) AND t_healthdeclaration.d_medifitcertexp >= CURRENT_DATE() ");
                        else if(exp == 4)
                            sb.append(" AND t_healthdeclaration.d_medifitcertexp <= DATE_ADD(CURRENT_DATE(), INTERVAL 180 DAY) AND t_healthdeclaration.d_medifitcertexp >= CURRENT_DATE() ");
                        else if(exp == 5)
                            sb.append(" AND t_healthdeclaration.d_medifitcertexp <= DATE_ADD(CURRENT_DATE(), INTERVAL 365 DAY) AND t_healthdeclaration.d_medifitcertexp >= CURRENT_DATE() ");
                        else if(exp == 2)
                            sb.append(" AND t_healthdeclaration.d_medifitcertexp < CURRENT_DATE() ");
                    }
                sb.append("ORDER BY t_healthdeclaration.d_medifitcertexp ");
            }
            
            }
            String query = (sb.toString()).intern();
            sb.setLength(0);

            try
            {
                conn = getConnection();
                pstmt = conn.prepareStatement(query);
                int scc = 0; 
                String name, document, expirydate, clientName, assetName;
                int govdocId, candidateId, remind;
                if(dropdownId == 2)
                {                          
                    if (clientIdIndex > 0) {
                        pstmt.setInt(++scc, clientIdIndex);
                    }
                    if (assetIdIndex > 0) {
                        pstmt.setInt(++scc, assetIdIndex);
                    }
                    if (documentId > 0) {
                        pstmt.setInt(++scc, documentId);
                    }
                    if (search != null && !search.equals("")) {
                        pstmt.setString(++scc, search.replaceFirst("^0+(?!$)", ""));
                    }
                    logger.info("getDocumentexpiryByName :: " + pstmt.toString());
                    rs = pstmt.executeQuery();

                    while (rs.next())
                    {
                        govdocId = rs.getInt(1);
                        candidateId = rs.getInt(2);
                        name = rs.getString(3) != null ? rs.getString(3) : "";
                        document = rs.getString(4) != null ? rs.getString(4) : "";
                        expirydate = rs.getString(5) != null ? rs.getString(5) : "";
                        remind = rs.getInt(6);
                        clientName = rs.getString(7) != null ? rs.getString(7) : "";
                        assetName = rs.getString(8) != null ? rs.getString(8) : "";
                        
                        list.add(new DocumentexpiryInfo(candidateId, govdocId, name, document, expirydate, remind, clientName, assetName));
                    }
                    rs.close();
                }
                if(dropdownId == 1)
                {   
                if (clientIdIndex > 0) {
                    pstmt.setInt(++scc, clientIdIndex);
                }                
                if (assetIdIndex > 0) {
                    pstmt.setInt(++scc, assetIdIndex);
                }                
                if (coursenameId > 0) {
                    pstmt.setInt(++scc, coursenameId);
                }
                if (search != null && !search.equals("")) {
                    pstmt.setString(++scc, search.replaceFirst("^0+(?!$)", ""));
                }
                logger.info("getDocumentexpiryByName Certificate :: " + pstmt.toString());
                rs = pstmt.executeQuery();
                while (rs.next())
                {
                    govdocId = rs.getInt(1);
                    candidateId = rs.getInt(2);
                    name = rs.getString(3) != null ? rs.getString(3) : "";
                    document = rs.getString(4) != null ? rs.getString(4) : "";
                    expirydate = rs.getString(5) != null ? rs.getString(5) : "";
                    remind = rs.getInt(6);
                    clientName = rs.getString(7) != null ? rs.getString(7) : "";
                    assetName = rs.getString(8) != null ? rs.getString(8) : "";

                    list.add(new DocumentexpiryInfo(candidateId, govdocId, name, document, expirydate, remind, clientName, assetName));
                }
                rs.close();
            }            
            if(dropdownId == 3)
            {   
                if (clientIdIndex > 0) {
                    pstmt.setInt(++scc, clientIdIndex);
                }                
                if (assetIdIndex > 0) {
                    pstmt.setInt(++scc, assetIdIndex);
                }
                if (search != null && !search.equals("")) {
                    pstmt.setString(++scc, search.replaceFirst("^0+(?!$)", ""));
                }
                logger.info("getDocumentexpiryByName Medical :: " + pstmt.toString());
                rs = pstmt.executeQuery();
                while (rs.next())
                {
                    
                    govdocId = rs.getInt(1);
                    candidateId = rs.getInt(2);
                    name = rs.getString(3) != null ? rs.getString(3) : "";
                    document = rs.getString(4) != null ? rs.getString(4) : "";
                    expirydate = rs.getString(5) != null ? rs.getString(5) : "";
                    remind = rs.getInt(6);
                    clientName = rs.getString(7) != null ? rs.getString(7) : "";
                    assetName = rs.getString(8) != null ? rs.getString(8) : "";
                    document = decipher(document);
                    if(healthId ==2)
                    {
                        if(!document.equals(""))
                        {
                            document = "Medical Fitness Certificate";
                        }
                    }                        
                    list.add(new DocumentexpiryInfo(candidateId, govdocId, name, document, expirydate, remind, clientName, assetName));
                        
                }
                rs.close();
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
        return list;
    }

    public ArrayList getFinalRecord(ArrayList l, String colId, int tp)
    {
        ArrayList list = null;
        HashMap record = null;
        DocumentexpiryInfo info;
        int total = 0;
        if(l != null)
            total = l.size();
        try
        {
            if(total > 0)
            {
                record = new HashMap();
                int i;
                for (i = 0; i < total; i++)
                {
                    info = (DocumentexpiryInfo) l.get (i);
                    record.put(getInfoValue(info, colId), info);
                }
            }
            Map map = null;
            if(colId.equals("1")) 
                map = sortById(record, tp);
            else if(colId.equals("6")) 
                map = sortByDate(record, tp, "dd-MMM-yyyy");
            else
                map = sortByName(record, tp);
            Iterator it = map.keySet().iterator();
            list = new ArrayList();
            DocumentexpiryInfo rInfo;
            while (it.hasNext())
            {
                String key = (String) it.next();
                int i;
                for(i= 0; i < total; i++)
                {
                    rInfo = (DocumentexpiryInfo) l.get(i);
                    String str = getInfoValue(rInfo, colId);
                    if(str.equals(key))
                    {
                        list.add(rInfo);
                    }
                }
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return list;
    }
    // to sort search result
    public String getInfoValue(DocumentexpiryInfo info, String i)
    {
        String infoval = "";
        if (i != null && i.equals("1"))
            infoval = info.getCandidateId() + "";      
        else if(i != null && i.equals("2"))
            infoval = info.getName() != null ? info.getName() : "";        
        else if(i != null && i.equals("3"))
            infoval = info.getClientName()!= null ? info.getClientName(): "";   
        else if(i != null && i.equals("4"))
            infoval = info.getAssetName()!= null ? info.getAssetName(): ""; 
        else if(i != null && i.equals("5"))
            infoval = info.getDocumentName()!= null ? info.getDocumentName(): "";        
        else if(i != null && i.equals("6"))
            infoval = info.getExpiryDate()!= null ? info.getExpiryDate(): "";        
        return infoval;
    }
        
    public ArrayList getListForExcel(int documentId, int type, int exp, int clientIdIndex, int assetIdIndex, 
            int allclient, String permission, String cids, String assetids, int coursenameId,int dropdownId, int healthId) 
    {
        ArrayList list = new ArrayList();
        StringBuilder sb = new StringBuilder();
        if(dropdownId ==2)
        {
            sb.append("SELECT t_govdoc.i_govid, t_govdoc.i_candidateid, CONCAT_WS(' ', c.s_firstname, c.s_middlename, c.s_lastname), t_doctype.s_doc, ");
            sb.append("DATE_FORMAT(t_govdoc.d_expirydate, '%d-%b-%Y'), t_govdoc.i_remind, t_client.s_name, t_clientasset.s_name ");
            sb.append("FROM t_govdoc ");
            sb.append("LEFT JOIN t_candidate AS c ON (c.i_candidateid = t_govdoc.i_candidateid) ");
            sb.append("LEFT JOIN t_client ON (c.i_clientid = t_client.i_clientid) ");
            sb.append("LEFT JOIN t_clientasset ON (c.i_clientassetid = t_clientasset.i_clientassetid) ");
            sb.append("LEFT JOIN t_doctype ON (t_doctype.i_doctypeid = t_govdoc.i_doctypeid) ");
            sb.append("WHERE t_govdoc.i_doctypeid in(2,6,9) AND t_govdoc.i_status = 1 AND d_expirydate != '0000-00-00' ");                
            if (allclient == 1) 
            {
                sb.append(" AND c.i_clientid >= -1 ");
            } 
            else 
            {
                if (!cids.equals("")) {
                    sb.append(" AND (c.i_clientid IN (" + cids + ") OR c.i_clientid <= 0)  ");
                } 
                else 
                {
                    sb.append(" AND c.i_clientid <= 0 ");
                }
                if (!assetids.equals("")) {
                    sb.append(" AND (c.i_clientassetid IN (" + assetids + ") OR c.i_clientassetid <= 0) ");
                }
            }
            if (clientIdIndex > 0) {
                sb.append("AND c.i_clientid = ? ");
            }
            if (assetIdIndex > 0) {
                sb.append("AND c.i_clientassetid = ? ");
            }
            if(documentId > 0){
                sb.append("AND t_govdoc.i_doctypeid = ? ");
            }
            if(type > 0)
            {
                if(type == 1)
                    sb.append(" AND t_govdoc.i_remind <= 0 ");
                else if(type == 2)
                    sb.append(" AND t_govdoc.i_remind > 0 ");
            }
            if(exp > 0)
            {
                if(exp == 1)
                    sb.append(" AND d_expirydate <= DATE_ADD(CURRENT_DATE(), INTERVAL 10 DAY) AND d_expirydate >= CURRENT_DATE() ");
                else if(exp == 3)
                    sb.append(" AND d_expirydate <= DATE_ADD(CURRENT_DATE(), INTERVAL 90 DAY) AND d_expirydate >= CURRENT_DATE() ");
                else if(exp == 2)
                    sb.append(" AND d_expirydate < CURRENT_DATE() ");
            }
            sb.append("ORDER BY t_doctype.s_doc, d_expirydate ");       
        }
        else if( dropdownId ==1)
        {
            sb.append("SELECT t_trainingandcert.i_tcid, t_candidate.i_candidateid,  CONCAT_WS(' ', t_candidate.s_firstname, t_candidate.s_middlename, t_candidate.s_lastname), "); 
            sb.append("t_coursename.s_name, DATE_FORMAT(t_trainingandcert.d_expirydate, '%d-%b-%Y'), t_trainingandcert.i_remind, t_client.s_name, t_clientasset.s_name ");
            sb.append("FROM t_trainingandcert  ");
            sb.append("LEFT JOIN  t_coursename ON (t_coursename.i_coursenameid = t_trainingandcert.i_coursenameid) ");
            sb.append("LEFT JOIN t_candidate ON(t_candidate.i_candidateid = t_trainingandcert.i_candidateid) ");
            sb.append("LEFT JOIN t_client ON (t_candidate.i_clientid = t_client.i_clientid) ");
            sb.append("LEFT JOIN t_clientasset ON (t_candidate.i_clientassetid = t_clientasset.i_clientassetid) ");
            sb.append("WHERE t_trainingandcert.i_status = 1 AND t_trainingandcert.d_expirydate != '0000-00-00' ");            
            if (allclient == 1) 
            {
                sb.append(" AND t_candidate.i_clientid >= -1 ");
            } 
            else 
            {
                if (!cids.equals("")) {
                    sb.append(" AND (t_candidate.i_clientid IN (" + cids + ") OR t_candidate.i_clientid <= 0)  ");
                } 
                else 
                {
                    sb.append(" AND t_candidate.i_clientid <= 0 ");
                }
                if (!assetids.equals("")) {
                    sb.append(" AND (t_candidate.i_clientassetid IN (" + assetids + ") OR t_candidate.i_clientassetid <= 0) ");
                }
            }
            if (clientIdIndex > 0) {
                sb.append("AND t_candidate.i_clientid = ? ");
            }
            if (assetIdIndex > 0) {
                sb.append("AND t_candidate.i_clientassetid = ? ");
            }
            if(coursenameId > 0){
                sb.append("AND t_coursename.i_coursenameid = ? ");
            }
            if(type > 0)
            {
                if(type == 1)
                    sb.append(" AND t_trainingandcert.i_remind <= 0 ");
                else if(type == 2)
                    sb.append(" AND t_trainingandcert.i_remind > 0 ");
            }
            if(exp > 0)
            {
                if(exp == 1)
                    sb.append(" AND t_trainingandcert.d_expirydate <= DATE_ADD(CURRENT_DATE(), INTERVAL 10 DAY) AND t_trainingandcert.d_expirydate >= CURRENT_DATE() ");
                else if(exp == 3)
                    sb.append(" AND t_trainingandcert.d_expirydate <= DATE_ADD(CURRENT_DATE(), INTERVAL 90 DAY) AND t_trainingandcert.d_expirydate >= CURRENT_DATE() ");
                else if(exp == 2)
                    sb.append(" AND t_trainingandcert.d_expirydate < CURRENT_DATE() ");
            }
            sb.append("ORDER BY t_trainingandcert.d_expirydate ");
        }
        else if( dropdownId ==3)
        {
            if(healthId ==1)
            {
                sb.append("SELECT t_healthdeclaration.i_healthid, t_candidate.i_candidateid, ");
                sb.append("CONCAT_WS(' ', t_candidate.s_firstname, t_candidate.s_middlename, t_candidate.s_lastname), t_healthdeclaration.s_ogukmedicalftw ,  ");
                sb.append("DATE_FORMAT(t_healthdeclaration.d_ogukexp, '%d-%b-%Y'), t_healthdeclaration.i_remind, ");
                sb.append("t_client.s_name, t_clientasset.s_name ");
                sb.append("FROM t_healthdeclaration ");
                sb.append("LEFT JOIN t_candidate ON(t_candidate.i_candidateid = t_healthdeclaration.i_candidateid) ");
                sb.append("LEFT JOIN t_client ON (t_candidate.i_clientid = t_client.i_clientid) ");
                sb.append("LEFT JOIN t_clientasset ON (t_candidate.i_clientassetid = t_clientasset.i_clientassetid) ");
                sb.append("WHERE t_healthdeclaration.i_status = 1 AND t_healthdeclaration.d_ogukexp != '0000-00-00' ");            
                if (allclient == 1) 
                {
                    sb.append(" AND t_candidate.i_clientid >= -1 ");
                } 
                else 
                {
                    if (!cids.equals("")) {
                        sb.append(" AND (t_candidate.i_clientid IN (" + cids + ") OR t_candidate.i_clientid <= 0)  ");
                    } 
                    else 
                    {
                        sb.append(" AND t_candidate.i_clientid <= 0 ");
                    }
                    if (!assetids.equals("")) {
                        sb.append(" AND (t_candidate.i_clientassetid IN (" + assetids + ") OR t_candidate.i_clientassetid <= 0) ");
                    }
                }
                if (clientIdIndex > 0) {
                    sb.append("AND t_candidate.i_clientid = ? ");
                }
                if (assetIdIndex > 0) {
                    sb.append("AND t_candidate.i_clientassetid = ? ");
                }
                if(type > 0)
                {
                    if(type == 1)
                        sb.append(" AND t_healthdeclaration.i_remind <= 0 ");
                    else if(type == 2)
                        sb.append(" AND t_healthdeclaration.i_remind > 0 ");
                }
                if(exp > 0)
                {
                    if(healthId == 1)
                    {
                        if(exp == 1)
                            sb.append(" AND t_healthdeclaration.d_ogukexp <= DATE_ADD(CURRENT_DATE(), INTERVAL 10 DAY) AND t_healthdeclaration.d_ogukexp >= CURRENT_DATE() ");
                        else if(exp == 3)
                            sb.append(" AND t_healthdeclaration.d_ogukexp <= DATE_ADD(CURRENT_DATE(), INTERVAL 90 DAY) AND t_healthdeclaration.d_ogukexp >= CURRENT_DATE() ");
                        else if(exp == 2)
                            sb.append(" AND t_healthdeclaration.d_ogukexp < CURRENT_DATE() ");
                    }
                }
                sb.append("ORDER BY t_healthdeclaration.d_ogukexp ");
                }
                else if(healthId ==2)
                {                    
                    sb.append("SELECT t_healthdeclaration.i_healthid, t_candidate.i_candidateid, ");
                    sb.append("CONCAT_WS(' ', t_candidate.s_firstname, t_candidate.s_middlename, t_candidate.s_lastname), t_healthdeclaration.s_medifitcert ,  ");
                    sb.append("DATE_FORMAT(t_healthdeclaration.d_medifitcertexp, '%d-%b-%Y'), t_healthdeclaration.i_remind2, ");
                    sb.append("t_client.s_name, t_clientasset.s_name ");
                    sb.append("FROM t_healthdeclaration ");
                    sb.append("LEFT JOIN t_candidate ON(t_candidate.i_candidateid = t_healthdeclaration.i_candidateid) ");
                    sb.append("LEFT JOIN t_client ON (t_candidate.i_clientid = t_client.i_clientid) ");
                    sb.append("LEFT JOIN t_clientasset ON (t_candidate.i_clientassetid = t_clientasset.i_clientassetid) ");
                    sb.append("WHERE t_healthdeclaration.i_status = 1 AND t_healthdeclaration.d_medifitcertexp != '0000-00-00' ");            
                    if (allclient == 1) 
                    {
                        sb.append(" AND t_candidate.i_clientid >= -1 ");
                    } 
                    else 
                    {
                        if (!cids.equals("")) {
                            sb.append(" AND (t_candidate.i_clientid IN (" + cids + ") OR t_candidate.i_clientid <= 0)  ");
                        } 
                        else 
                        {
                            sb.append(" AND t_candidate.i_clientid <= 0 ");
                        }
                        if (!assetids.equals("")) {
                            sb.append(" AND (t_candidate.i_clientassetid IN (" + assetids + ") OR t_candidate.i_clientassetid <= 0) ");
                        }
                    }
                    if (clientIdIndex > 0) {
                        sb.append("AND t_candidate.i_clientid = ? ");
                    }
                    if (assetIdIndex > 0) {
                        sb.append("AND t_candidate.i_clientassetid = ? ");
                    }
                    if(type > 0)
                    {
                        if(type == 1)
                            sb.append(" AND t_healthdeclaration.i_remind <= 0 ");
                        else if(type == 2)
                            sb.append(" AND t_healthdeclaration.i_remind > 0 ");
                    }
                    if(exp > 0)
                    {                      
                        if(exp == 1)
                            sb.append(" AND t_healthdeclaration.d_medifitcertexp <= DATE_ADD(CURRENT_DATE(), INTERVAL 10 DAY) AND t_healthdeclaration.d_medifitcertexp >= CURRENT_DATE() ");
                        else if(exp == 3)
                            sb.append(" AND t_healthdeclaration.d_medifitcertexp <= DATE_ADD(CURRENT_DATE(), INTERVAL 90 DAY) AND t_healthdeclaration.d_medifitcertexp >= CURRENT_DATE() ");
                        else if(exp == 2)
                            sb.append(" AND t_healthdeclaration.d_medifitcertexp < CURRENT_DATE() ");
                    }
                sb.append("ORDER BY t_healthdeclaration.d_medifitcertexp ");
            }
            
            }
            String query = (sb.toString()).intern();
            sb.setLength(0);

            try
            {
                conn = getConnection();
                pstmt = conn.prepareStatement(query);
                int scc = 0; 
                String name, document, expirydate, clientName, assetName;
                int govdocId, candidateId, remind;
                if(dropdownId == 2)
                {                          
                    if (clientIdIndex > 0) {
                        pstmt.setInt(++scc, clientIdIndex);
                    }
                    if (assetIdIndex > 0) {
                        pstmt.setInt(++scc, assetIdIndex);
                    }
                    if (documentId > 0) {
                        pstmt.setInt(++scc, documentId);
                    }
                    logger.info("getDocumentexpiryByName :: " + pstmt.toString());
                    rs = pstmt.executeQuery();

                    while (rs.next())
                    {
                        govdocId = rs.getInt(1);
                        candidateId = rs.getInt(2);
                        name = rs.getString(3) != null ? rs.getString(3) : "";
                        document = rs.getString(4) != null ? rs.getString(4) : "";
                        expirydate = rs.getString(5) != null ? rs.getString(5) : "";
                        remind = rs.getInt(6);
                        clientName = rs.getString(7) != null ? rs.getString(7) : "";
                        assetName = rs.getString(8) != null ? rs.getString(8) : "";
                        
                        list.add(new DocumentexpiryInfo(candidateId, govdocId, name, document, expirydate, remind, clientName, assetName));
                    }
                    rs.close();
                }
                if(dropdownId == 1)
                {   
                if (clientIdIndex > 0) {
                    pstmt.setInt(++scc, clientIdIndex);
                }                
                if (assetIdIndex > 0) {
                    pstmt.setInt(++scc, assetIdIndex);
                }                
                if (coursenameId > 0) {
                    pstmt.setInt(++scc, coursenameId);
                }
                logger.info("getDocumentexpiryByName Certificate :: " + pstmt.toString());
                rs = pstmt.executeQuery();
                while (rs.next())
                {
                    govdocId = rs.getInt(1);
                    candidateId = rs.getInt(2);
                    name = rs.getString(3) != null ? rs.getString(3) : "";
                    document = rs.getString(4) != null ? rs.getString(4) : "";
                    expirydate = rs.getString(5) != null ? rs.getString(5) : "";
                    remind = rs.getInt(6);
                    clientName = rs.getString(7) != null ? rs.getString(7) : "";
                    assetName = rs.getString(8) != null ? rs.getString(8) : "";

                    list.add(new DocumentexpiryInfo(candidateId, govdocId, name, document, expirydate, remind, clientName, assetName));
                }
                rs.close();
            }            
            if(dropdownId == 3)
            {   
                if (clientIdIndex > 0) {
                    pstmt.setInt(++scc, clientIdIndex);
                }                
                if (assetIdIndex > 0) {
                    pstmt.setInt(++scc, assetIdIndex);
                }   
                logger.info("getDocumentexpiryByName Medical :: " + pstmt.toString());
                rs = pstmt.executeQuery();
                while (rs.next())
                {
                    
                    govdocId = rs.getInt(1);
                    candidateId = rs.getInt(2);
                    name = rs.getString(3) != null ? rs.getString(3) : "";
                    document = rs.getString(4) != null ? rs.getString(4) : "";
                    expirydate = rs.getString(5) != null ? rs.getString(5) : "";
                    remind = rs.getInt(6);
                    clientName = rs.getString(7) != null ? rs.getString(7) : "";
                    assetName = rs.getString(8) != null ? rs.getString(8) : "";
                    document = decipher(document);
                    if(healthId ==2)
                    {
                        if(!document.equals("") &&  !expirydate.equals(""))
                        {
                            document = "Medical Fitness Certificate";
                        }
                    }                        
                    list.add(new DocumentexpiryInfo(candidateId, govdocId, name, document, expirydate, remind, clientName, assetName));
                        
                }
                rs.close();
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
        return list;
    }
    
    public Collection getDocumentList() 
    {
        Collection coll = new LinkedList();
        String query = ("SELECT i_doctypeid, s_doc FROM t_doctype WHERE i_status = 1 AND i_doctypeid in(2,6,9) ORDER BY s_doc").intern();
        coll.add(new DocumentexpiryInfo(-1, "Document Name"));
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            rs = pstmt.executeQuery();
            int refId;
            String refName;
            while (rs.next()) {
                refId = rs.getInt(1);
                refName = rs.getString(2) != null ? rs.getString(2) : "";
                coll.add(new DocumentexpiryInfo(refId, refName));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return coll;
    }
    
    public String getRemindMessage(String name, String message, String htmlFile) 
    {
        String adminpage = getMainPath("template_path") + htmlFile;
        HashMap hashmap = new HashMap();
        Template template = new Template(adminpage);
        hashmap.put("NAME", name);
        hashmap.put("MESSAGE", message);  
        return template.patch(hashmap);
    }
    
    public int createMailLog(Connection conn, int type, String name, String to, String cc, String bcc, String from, String subject, 
        int candidateId, String username, String doctype, String filename) 
    {
        int count = 0;
        try 
        {
            StringBuilder sb = new StringBuilder();
            sb.append("INSERT INTO t_maillog ");
            sb.append("(i_type, s_name, s_to, s_cc, s_bcc, s_from, s_subject, s_doctype, s_filename, ts_regdate, ts_moddate, i_candidateid, s_sendby) ");
            sb.append("VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            String query = sb.toString().intern();
            sb.setLength(0);       
            pstmt = conn.prepareStatement(query, java.sql.Statement.RETURN_GENERATED_KEYS);
            pstmt.setInt(1, type);
            pstmt.setString(2, name);
            pstmt.setString(3, to);
            pstmt.setString(4, cc);
            pstmt.setString(5, bcc);
            pstmt.setString(6, from);
            pstmt.setString(7, subject);
            pstmt.setString(8, doctype);
            pstmt.setString(9, filename);
            pstmt.setString(10, currDate1());
            pstmt.setString(11, currDate1());
            pstmt.setInt(12, candidateId);
            pstmt.setString(13, username);            
            print(this, "createMailLog :: " + pstmt.toString());
            pstmt.executeUpdate();
            rs = pstmt.getGeneratedKeys();
            while (rs.next()) {
                count = rs.getInt(1);
            }
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
            print(this, "createMailLog :: " + e.getMessage());
        } 
        finally 
        {
            close(null, pstmt, null);
        }
        return count;
    }
    
    public int sendmail(int govdocIdMain, String gids, String username, int userId, int dropdownId, int healthId) 
    {
        int sent = 0;
        StringBuilder sb = new StringBuilder();
        if(dropdownId ==2)
        {
            sb.append("SELECT CONCAT_WS(' ', c.s_firstname, c.s_middlename, c.s_lastname), c.s_email, DATE_FORMAT(g.d_expirydate, '%b %D, %Y'), ");
            sb.append("t_doctype.s_doc, g.i_candidateid, g.d_expirydate >= CURRENT_DATE(), g.i_govid, t_client.s_ocsuserids ");
            sb.append("FROM t_govdoc AS g ");
             sb.append("LEFT JOIN t_candidate AS c ON (c.i_candidateid = g.i_candidateid) ");
            sb.append("LEFT JOIN t_client ON (t_client.i_clientid = c.i_clientid) ");
            sb.append("LEFT JOIN t_doctype ON (t_doctype.i_doctypeid = g.i_doctypeid) WHERE 0 = 0 ");
            if(govdocIdMain > 0)
                sb.append(" AND  g.i_govid = ? ");
            else if(gids != null && !gids.equals(""))
                sb.append(" AND  g.i_govid IN ("+gids+") ");
        }
        else if(dropdownId == 1)
        {
            sb.append("SELECT CONCAT_WS(' ', c.s_firstname, c.s_middlename, c.s_lastname), c.s_email, DATE_FORMAT(t_trainingandcert.d_expirydate, '%b %D, %Y'), ");            
            sb.append("t_coursename.s_name, t_trainingandcert.i_candidateid, t_trainingandcert.d_expirydate >= CURRENT_DATE(), t_trainingandcert.i_coursenameid, t_client.s_ocsuserids ");
            sb.append("FROM t_trainingandcert  ");
            sb.append("LEFT JOIN  t_coursename ON (t_coursename.i_coursenameid = t_trainingandcert.i_coursenameid)  ");
            sb.append("LEFT JOIN t_candidate AS c ON(c.i_candidateid = t_trainingandcert.i_candidateid) ");            
            sb.append("LEFT JOIN t_client ON (t_client.i_clientid = c.i_clientid) WHERE 0 = 0 ");
            if(govdocIdMain > 0)
                sb.append(" AND  t_trainingandcert.i_tcid = ?  ");
            else if(gids != null && !gids.equals(""))
                sb.append(" AND  t_trainingandcert.i_tcid IN ("+gids+") ");
        }
        else if(dropdownId == 3)
        {
            if(healthId ==1)
            {
                sb.append("SELECT CONCAT_WS(' ', c.s_firstname, c.s_middlename, c.s_lastname), c.s_email, DATE_FORMAT(t_healthdeclaration.d_ogukexp, '%b %D, %Y'), ");
                sb.append("t_healthdeclaration.s_ogukmedicalftw, t_healthdeclaration.i_candidateid, t_healthdeclaration.d_ogukexp >= CURRENT_DATE(), t_healthdeclaration.i_healthid,  t_client.s_ocsuserids ");
                sb.append("FROM t_healthdeclaration ");
                sb.append("LEFT JOIN t_candidate AS c ON(c.i_candidateid = t_healthdeclaration.i_candidateid) ");
                sb.append("LEFT JOIN t_client ON (t_client.i_clientid = c.i_clientid)  WHERE 0 = 0 ");
            }
            else if(healthId ==2)
            {
                sb.append("SELECT CONCAT_WS(' ', c.s_firstname, c.s_middlename, c.s_lastname), c.s_email, DATE_FORMAT(t_healthdeclaration.d_medifitcertexp, '%b %D, %Y'), ");
                sb.append("t_healthdeclaration.s_medifitcert, t_healthdeclaration.i_candidateid, t_healthdeclaration.d_medifitcertexp >= CURRENT_DATE(), t_healthdeclaration.i_healthid,  t_client.s_ocsuserids ");
                sb.append("FROM t_healthdeclaration ");
                sb.append("LEFT JOIN t_candidate AS c ON(c.i_candidateid = t_healthdeclaration.i_candidateid) ");
                sb.append("LEFT JOIN t_client ON (t_client.i_clientid = c.i_clientid)  WHERE 0 = 0 ");
            }
            if(govdocIdMain > 0)
                sb.append(" AND  t_healthdeclaration.i_healthid = ?  ");
            else if(gids != null && !gids.equals(""))
                sb.append(" AND  t_healthdeclaration.i_healthid IN ("+gids+") ");
        }
        String query = sb.toString();
        sb.setLength(0);
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            if(govdocIdMain > 0)
                pstmt.setInt(1, govdocIdMain);
            String name = "", emailId = "", expiry = "", doctype = "",  ocsuserids = "", ccval = "";
            int candidateId=0 , type = 0, govdocId = 0;
            print(this, "sendmail :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            while (rs.next()) 
            {
                name = rs.getString(1) != null ? rs.getString(1) : "";
                emailId = rs.getString(2) != null ? rs.getString(2) : "";
                expiry = rs.getString(3) != null ? rs.getString(3) : "";
                doctype = rs.getString(4) != null ? rs.getString(4) : "";
                candidateId = rs.getInt(5);
                type = rs.getInt(6); 
                govdocId = rs.getInt(7);
                ocsuserids = rs.getString(8) != null ? rs.getString(8) : "";
                if(dropdownId == 3 )
                {
                    doctype = decipher(doctype);
                }
                if(dropdownId ==3)
                {
                    if(healthId == 2)
                    {
                        doctype = "Medical Fitness Certificate";
                    }
                    else if(healthId ==1)
                    {
                         doctype = "OGUK Medical FTW ("+doctype+")";
                    }
                }
                if(!emailId.equals(""))
                {
                    String message = "", subject = "";                    
                    if(type == 1)
                    {
                        message = "Your <b>"+doctype+"</b> is expiring on <b>"+expiry+"</b>. Please renew it soon.";
                        subject = "JourneyXPro: " + doctype + " Expiring on " + expiry;
                    }
                    else
                    {
                        message = "Your <b>"+doctype+"</b> validity is expired on <b>"+expiry+"</b>. Please renew it soon.";
                        subject = "JourneyXPro: " + doctype + " Expired on " + expiry;
                    }                        
                    if(!ocsuserids.equals(""))
                    {
                        String query_cc = "SELECT GROUP_CONCAT(s_email) FROM t_userlogin WHERE i_userid IN ("+ocsuserids+")";
                        PreparedStatement pstmt_cc = conn.prepareStatement(query_cc);
                        ResultSet rs_cc = pstmt_cc.executeQuery();
                        while (rs_cc.next()) 
                        { 
                            ccval = rs_cc.getString(1) != null ? rs_cc.getString(1) : "";
                        }
                    }
                    String messageBody = getRemindMessage(name, message, "remind.html");
                    String file_maillog = getMainPath("file_maillog");
                    java.util.Date nowmail = new java.util.Date();
                    String fn_mail = "remind-"+String.valueOf(nowmail.getTime())+".html";
                    String filePath = createFolder(file_maillog);
                    String fname = writeHTMLFile(messageBody, file_maillog+"/"+filePath, fn_mail);
                    String receipent[] = new String[1];
                    receipent[0] = emailId;
                    String from = "";                    
                        
                    String cc[] = parseCommaDelimString(ccval); 
                    String bcc[] = new String[0];
                    try
                    {
                        StatsInfo sinfo = postMailAttach(receipent, cc, bcc, messageBody, subject, "", "", -1);
                        int flag = 0;
                        if(sinfo != null)
                        {
                            flag = sinfo.getDdlValue();
                            from = sinfo.getDdlLabel();
                            if(flag > 0)
                            {
                                String updateq = "";
                                if(dropdownId ==1)
                                {
                                    updateq = "UPDATE t_trainingandcert SET i_remind = i_remind+1 WHERE i_tcid = ?";
                                }
                                if(dropdownId ==2)
                                {
                                    updateq = "UPDATE t_govdoc SET i_remind = i_remind+1 WHERE i_govid = ?";
                                }                                
                                if(dropdownId ==3)
                                {
                                    if(healthId == 1)
                                        updateq = "UPDATE t_healthdeclaration SET i_remind = i_remind+1 WHERE i_healthid = ?";
                                    else if(healthId == 2)
                                        updateq = "UPDATE t_healthdeclaration SET i_remind2 = i_remind2+1 WHERE i_healthid = ?";
                                }
                                PreparedStatement update_stmt = conn.prepareStatement(updateq);                                 
                                update_stmt.setInt(1, govdocIdMain);
                                update_stmt.executeUpdate();
                            }
                        }
                        createMailLog(conn, 6, name, emailId, ccval, "", from, subject, candidateId, username, doctype, "/"+filePath+ "/" +fname);
                        if(flag > 0)
                        {
                            sent = 1;
                        }
                    }
                    catch(Exception e)
                    {
                       print(this, "sendmail :: " + e.getMessage());
                    }
                }
            }
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return sent;
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
        coll.add(new DocumentexpiryInfo(-1, " Select Client"));
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
                refName = rs.getString(2) != null ? rs.getString(2): "" ;
                coll.add(new DocumentexpiryInfo(refId, refName));
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
            coll.add(new DocumentexpiryInfo(-1, " Select Asset "));
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
                    ddlLabel = rs.getString(2) != null ? rs.getString(2): "" ;
                    coll.add(new DocumentexpiryInfo(ddlValue, ddlLabel));
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                close(conn, pstmt, rs);
            }
        } else {
            coll.add(new DocumentexpiryInfo(-1, " Select Asset "));
        }
        return coll;
    }
    
     public Collection getCourseName() {
        Collection coll = new LinkedList();
        coll.add(new DocumentexpiryInfo(-1, "- Select Course -"));
        String xml_path = getMainPath("json_path");
        String str = readHTMLFile("coursename.json", xml_path);
        JSONArray arr = new JSONArray(str);
        if (arr != null) {
            int len = arr.length();
            for (int i = 0; i < len; i++) {
                JSONObject jobj = arr.optJSONObject(i);
                if (jobj != null) {
                    coll.add(new DocumentexpiryInfo(jobj.optInt("id"), jobj.optString("name")));
                }
            }
        }
        return coll;
    }
     
    public int totalCounts( int allclient, String permission, String cids, String assetids)
    {
        int count = 0;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT count(1)  FROM t_govdoc ");
        sb.append("LEFT JOIN t_candidate as c on (c.i_candidateid = t_govdoc.i_candidateid) ");
        sb.append("WHERE t_govdoc.i_doctypeid IN(2,6,9) AND d_expirydate != '0000-00-00'  AND d_expirydate <= DATE_ADD(CURRENT_DATE(), INTERVAL 10 DAY) AND d_expirydate >= CURRENT_DATE()");                
        if (allclient == 1) 
        {
            sb.append(" AND c.i_clientid >= -1 ");
        } 
        else 
        {
            if (!cids.equals("")) {
                sb.append(" AND (c.i_clientid IN (" + cids + ") OR c.i_clientid <= 0)  ");
            } 
            else 
            {
                sb.append(" AND c.i_clientid <= 0 ");
            }
            if (!assetids.equals("")) {
                sb.append(" AND (c.i_clientassetid IN (" + assetids + ") OR c.i_clientassetid <= 0) ");
            }
        }
        String query1 = sb.toString();
        sb.setLength(0);
        
        sb.append("SELECT COUNT(1) FROM t_trainingandcert ");
        sb.append("LEFT JOIN t_candidate ON(t_candidate.i_candidateid = t_trainingandcert.i_candidateid) ");
        sb.append("WHERE t_trainingandcert.d_expirydate != '0000-00-00' ");  
        sb.append("AND t_trainingandcert.d_expirydate <= DATE_ADD(CURRENT_DATE(), INTERVAL 10 DAY) AND t_trainingandcert.d_expirydate >= CURRENT_DATE() ");
        if (allclient == 1) 
        {
            sb.append("AND t_candidate.i_clientid >= -1 ");
        } 
        else 
        {
            if (!cids.equals("")) {
                sb.append("AND (t_candidate.i_clientid IN (" + cids + ") OR t_candidate.i_clientid <= 0)  ");
            } 
            else 
            {
                sb.append("AND t_candidate.i_clientid <= 0 ");
            }
            if (!assetids.equals("")) {
                sb.append("AND (t_candidate.i_clientassetid IN (" + assetids + ") OR t_candidate.i_clientassetid <= 0) ");
            }
        }
        String query2 = sb.toString();
        sb.setLength(0);
        
        sb.append("SELECT COUNT(1) FROM t_healthdeclaration ");
        sb.append("LEFT JOIN t_candidate ON(t_candidate.i_candidateid = t_healthdeclaration.i_candidateid) ");
        sb.append("WHERE t_healthdeclaration.d_ogukexp != '0000-00-00' ");            
        sb.append(" AND t_healthdeclaration.d_ogukexp <= DATE_ADD(CURRENT_DATE(), INTERVAL 10 DAY) AND t_healthdeclaration.d_ogukexp >= CURRENT_DATE() ");
        if (allclient == 1) 
        {
            sb.append(" AND t_candidate.i_clientid >= -1 ");
        } 
        else 
        {
            if (!cids.equals("")) {
                sb.append(" AND (t_candidate.i_clientid IN (" + cids + ") OR t_candidate.i_clientid <= 0)  ");
            } 
            else 
            {
                sb.append(" AND t_candidate.i_clientid <= 0 ");
            }
            if (!assetids.equals("")) {
                sb.append(" AND (t_candidate.i_clientassetid IN (" + assetids + ") OR t_candidate.i_clientassetid <= 0) ");
            }
        }
        String query3 = sb.toString();
        sb.setLength(0); 
        
        sb.append("SELECT COUNT(1) FROM t_healthdeclaration ");
        sb.append("LEFT JOIN t_candidate ON(t_candidate.i_candidateid = t_healthdeclaration.i_candidateid) ");
        sb.append("WHERE t_healthdeclaration.d_medifitcertexp != '0000-00-00' ");      
        sb.append("AND t_healthdeclaration.d_medifitcertexp <= DATE_ADD(CURRENT_DATE(), INTERVAL 10 DAY) AND t_healthdeclaration.d_medifitcertexp >= CURRENT_DATE() ");
        if (allclient == 1) 
        {
            sb.append(" AND t_candidate.i_clientid >= -1 ");
        } 
        else 
        {
            if (!cids.equals("")) {
                sb.append(" AND (t_candidate.i_clientid IN (" + cids + ") OR t_candidate.i_clientid <= 0)  ");
            } 
            else 
            {
                sb.append(" AND t_candidate.i_clientid <= 0 ");
            }
            if (!assetids.equals("")) {
                sb.append(" AND (t_candidate.i_clientassetid IN (" + assetids + ") OR t_candidate.i_clientassetid <= 0) ");
            }
        }
        String query4 = sb.toString();
        sb.setLength(0); 
        try
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query1);     
            logger.info("query1 :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            while (rs.next())
            {
                count += rs.getInt(1);
            }
            rs.close();
            pstmt = conn.prepareStatement(query2);      
            logger.info("query2 :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            while (rs.next())
             {
                 count += rs.getInt(1);
             }
             rs.close();

            pstmt = conn.prepareStatement(query3);      
            logger.info("query3 :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            while (rs.next())
             {
                 count += rs.getInt(1);
             }
             rs.close();

            pstmt = conn.prepareStatement(query4);
            logger.info("query4 :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            while (rs.next())
             {
                 count += rs.getInt(1);
             }
             rs.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            close(conn, pstmt, rs);
        }
        return count;
    }
    
    public ArrayList getAlertByName(String search, int clientId, int assetId, String fromDate, String toDate, 
            int moduleIndex, int allclient, String permission, String cids, String assetids)
    {
        ArrayList list = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT t_notification.i_notificationid, t_notification.s_updatedby, t_client.s_name, t_clientasset.s_name, ");
        sb.append("t_notification.i_type, DATE_FORMAT(t_notification.ts_regdate, '%d-%b-%Y %H:%i'), ");
        sb.append("t_notification.i_moduleid, t_notification.s_remarks, t_maillog.s_filename, t_notification.i_mainid, ");
        sb.append("t_notification.i_clientid, t_notification.i_clientassetid ");
        sb.append("FROM t_notification ");
        sb.append("LEFT JOIN t_client ON (t_notification.i_clientid = t_client.i_clientid) ");
        sb.append("LEFT JOIN t_clientasset ON (t_notification.i_clientassetid = t_clientasset.i_clientassetid) ");        
        sb.append("LEFT JOIN t_maillog ON (t_maillog.i_notificationid = t_notification.i_notificationid) ");        
        sb.append("WHERE 0 = 0 ");
        if (allclient == 1) {
            sb.append("AND (t_notification.i_clientid > 0 OR t_notification.i_clientid <= 0) ");
        } 
        else 
        {
            if (!cids.equals("")) {
                sb.append("AND (t_notification.i_clientid IN (" + cids + ") OR t_notification.i_clientid <= 0) ");
            } else {
                sb.append("AND (t_notification.i_clientid > 0 OR t_notification.i_clientid <= 0) ");
            }
            if (!assetids.equals("")) {
                sb.append("AND (t_notification.i_clientassetid IN (" + assetids + ") OR t_notification.i_clientid <= 0) ");
            }
        }
        if (search != null && !search.equals("")) 
        {
            sb.append("AND t_notification.s_updatedby LIKE ? ");
        }
        if(fromDate != null && !fromDate.equals("") && !fromDate.equals("DD-MMM-YYYY") && toDate != null && !toDate.equals("") && !toDate.equals("DD-MMM-YYYY")){
            sb.append(" AND (DATE_FORMAT(t_notification.ts_regdate, '%Y-%m-%d') >= ? AND DATE_FORMAT(t_notification.ts_regdate, '%Y-%m-%d') <= ?) ");
        }
        if (clientId > 0) {
            sb.append("AND t_notification.i_clientid = ? ");
        }
        if (assetId > 0) {
            sb.append("AND t_notification.i_clientassetid = ? ");
        }
        if (moduleIndex > 0) {
            sb.append("AND t_notification.i_moduleid = ? ");
        }
        sb.append("ORDER BY t_notification.ts_regdate DESC ");
        
        String query = (sb.toString()).intern();
        sb.setLength(0);
        try
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            int scc = 0;
            if(search != null && !search.equals(""))
            {
                pstmt.setString(++scc, "%"+(search)+"%");
            }
            if (fromDate != null && !fromDate.equals("") && !fromDate.equals("DD-MMM-YYYY") && toDate != null && !toDate.equals("") && !toDate.equals("DD-MMM-YYYY")) {
                pstmt.setString(++scc, changeDate1(fromDate));
                pstmt.setString(++scc, changeDate1(toDate));
            }
            if (clientId > 0) {
                pstmt.setInt(++scc, clientId);
            }
            if (assetId > 0) {
                pstmt.setInt(++scc, assetId);
            }
            if (moduleIndex > 0) {
                pstmt.setInt(++scc, moduleIndex);
            }
            logger.info("getAlertByName :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String name, client, asset, date, remark, filename;
            int id, type, moduleId, mainId, cid, aid;
            while (rs.next())
            {
                id = rs.getInt(1);
                name = rs.getString(2) != null ? rs.getString(2) : "";
                client = rs.getString(3) != null ? rs.getString(3) : "";
                asset = rs.getString(4) != null ? rs.getString(4) : "";
                type = rs.getInt(5);
                date = rs.getString(6) != null ? rs.getString(6) : "";
                moduleId = rs.getInt(7);
                remark = rs.getString(8) != null ? rs.getString(8) : "";
                filename = rs.getString(9) != null ? rs.getString(9) : "";
                mainId = rs.getInt(10);
                cid = rs.getInt(11);
                aid = rs.getInt(12);
                
                String stVal = getTypeVal(type);
                String module = getModulename(moduleId);
                
                list.add(new DocumentexpiryInfo(id, name, client, asset,stVal, date, module, remark, filename, mainId, moduleId, cid, aid));
            }
            rs.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            logger.info("getAlertByName :: " + pstmt.toString());
        }
        finally
        {
            close(conn, pstmt, rs);
        }
        return list;
    }
    
    public String getTypeVal(int id) {
        String s = "";
        if (id == 1) {
            s = "New Registration";            
        }
        if (id == 2) {
            s = "Invitation Sent";            
        }
        if (id == 3) {
            s = "Available";            
        }
        if (id == 4) {
            s = "Unavailable";            
        }
        if (id == 5) {
            s = "Selected by Client";            
        }
        if (id == 6) {
            s = "Rejected by Client";            
        }
        if (id == 7) {
            s = "New Job Post Created";            
        }
        if (id == 8) {
            s = "Resume sent to Client";            
        }
        if (id == 9) {
            s = "Offer sent to Candidate";            
        }
        if (id == 10) {
            s = "Offer Accepted";            
        }
        if (id == 11) {
            s = "Offer Rejected";            
        }
        if (id == 12) {
            s = "Crew Sign-Off Notification – T-2 Days";            
        }
        if (id == 14) {
            s = "Rejected by Client";            
        }
        return s;
    }
    
    public String getModulename(int id) {
        String s = "";
        if (id == 1) {
            s = "Crew Login";            
        }
        if (id == 2) {
            s = "Client Login Selection";            
        }
        if (id == 3) {
            s = "Job Posting";            
        }
        if (id == 4) {
            s = "Client Selection";            
        }
        if (id == 5) {
            s = "Crew Rotation";            
        }
        return s;
    }
    
    public int createNotification(int userId, int moduleId, int type, int mainId, 
            int clientId, int assetId, String remarks, String username)
    {
        int cc = 0;
        try 
        {
            StringBuilder sb = new StringBuilder();
            sb.append("INSERT INTO t_notification  ");
            sb.append("(i_moduleid, i_type, s_remarks, s_updatedby, i_clientid, i_clientassetid, i_mainid, i_userid, ts_regdate, ts_moddate) ");
            sb.append("VALUES (?, ?, ?, ?, ?,  ?, ?, ?, ?, ?)");
            String query = (sb.toString()).intern();
            sb.setLength(0);
            conn = getConnection();
            pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            int scc = 0;
            pstmt.setInt(++scc, moduleId);
            pstmt.setInt(++scc, type);
            pstmt.setString(++scc, remarks);
            pstmt.setString(++scc, username);
            pstmt.setInt(++scc, clientId);
            pstmt.setInt(++scc, assetId);
            pstmt.setInt(++scc, mainId);
            pstmt.setInt(++scc, userId);
            pstmt.setString(++scc, currDate1());
            pstmt.setString(++scc, currDate1());
            pstmt.executeUpdate();
            rs = pstmt.getGeneratedKeys();
            while (rs.next()) 
            {
                cc = rs.getInt(1);
            }
        } catch (Exception exception) {
            print(this, "createNotification :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return cc;
    }
    
    public ArrayList getFinalRecord2(ArrayList l, String colId, int tp)
    {
        ArrayList list = null;
        HashMap record = null;
        DocumentexpiryInfo info;
        int total = 0;
        if(l != null)
            total = l.size();
        try
        {
            if(total > 0)
            {
                record = new HashMap();
                int i;
                for (i = 0; i < total; i++)
                {
                    info = (DocumentexpiryInfo) l.get (i);
                    record.put(getInfoValue2(info, colId), info);
                }
            }
            Map map;
            if(colId.equals("6"))
            {
                map = sortByDate(record, tp, "dd-MMM-yyyy HH:mm");
            }else{
                map = sortByName(record, tp);
            }
            Iterator it = map.keySet().iterator();
            list = new ArrayList();
            DocumentexpiryInfo rInfo;
            while (it.hasNext())
            {
                String key = (String) it.next();
                int i;
                for(i= 0; i < total; i++)
                {
                    rInfo = (DocumentexpiryInfo) l.get(i);
                    String str = getInfoValue2(rInfo, colId);
                    if(str.equals(key))
                    {
                        list.add(rInfo);
                    }
                }
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return list;
    }
    
    public String getInfoValue2(DocumentexpiryInfo info, String i)
    {
        String infoval = "";   
        if(i != null && i.equals("1"))
            infoval = info.getName() != null ? info.getName() : "";        
        else if(i != null && i.equals("2"))
            infoval = info.getModuleName() != null ? info.getModuleName(): "";   
        else if(i != null && i.equals("3"))
            infoval = info.getStVal() != null ? info.getStVal(): "";   
        else if(i != null && i.equals("4"))
            infoval = info.getClientName() != null ? info.getClientName(): "";   
        else if(i != null && i.equals("5"))
            infoval = info.getAssetName() != null ? info.getAssetName(): "";    
        else if(i != null && i.equals("6"))
            infoval = info.getDate() != null ? info.getDate(): "";    
        return infoval;
    }
    
    public int deleteAlert(int notificationId, int userId, String ipAddrStr, String iplocal) 
    {
        int cc = 0;
        try 
        {
            String query = "DELETE FROM t_notification WHERE i_notificationid = ? ";
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, notificationId);
            print(this,"deleteAlert :: " + pstmt.toString());
            cc = pstmt.executeUpdate();
        } catch (Exception exception) {
            print(this, "deleteAlert :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        createHistoryAccess(null, userId, ipAddrStr, iplocal, "Data Deleted", 70, notificationId);
        return cc;
    }
}