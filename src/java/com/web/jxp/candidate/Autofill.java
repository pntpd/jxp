package com.web.jxp.candidate;
import com.web.jxp.base.Base;
import static com.web.jxp.common.Common.print;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

public class Autofill extends Base
{    
    private static final Logger logger = Logger.getLogger(Class.class.getName());
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    
    public String getSearchAutoFill(String val) 
    {
        String s = "";
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT t_candidate.i_candidateid, CONCAT_WS(' ', t_candidate.s_firstname, t_candidate.s_middlename, t_candidate.s_lastname), t_position.s_name, t_grade.s_name ");
        sb.append("FROM t_candidate ");
        sb.append("LEFT JOIN t_position ON (t_candidate.i_positionid = t_position.i_positionid) ");
        sb.append("LEFT JOIN t_grade ON (t_grade.i_gradeid = t_position.i_gradeid) ");
        sb.append("WHERE (CONCAT_WS(' ', t_candidate.s_firstname, t_candidate.s_middlename, t_candidate.s_lastname) LIKE ? OR CONCAT_WS(' ', t_candidate.s_firstname, t_candidate.s_lastname) LIKE ?)  ");
        String query = sb.toString();
        sb.setLength(0);
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, "%" + (val) + "%");
            pstmt.setString(2, "%" + (val) + "%");
            print(this, "getSearchAutoFill ::" + pstmt.toString());
            rs = pstmt.executeQuery();
            JSONArray jsonArrayParent = new JSONArray();
            int candidateId;
            String name, positionName, gradeName;
            while (rs.next())
            {
                candidateId = rs.getInt(1);
                name = rs.getString(2) != null ? rs.getString(2) : "";
                positionName = rs.getString(3) != null ? rs.getString(3) : "";
                gradeName = rs.getString(4) != null ? rs.getString(4) : "";
                if(!gradeName.equals(""))
                    positionName += " | "+ gradeName;
                if(!positionName.equals(""))
                    name += " - " + positionName;
                if (!name.equals("")) 
                {
                    JSONObject jsonObjectRow = new JSONObject();
                    jsonObjectRow.put("value", candidateId);
                    jsonObjectRow.put("label", name);
                    jsonArrayParent.put(jsonObjectRow);
                }
            }
            s = jsonArrayParent.toString();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return s;
    }        
    
    public String getFlag(int candiadteId)
    {
        String s = "No";
        int vflag = 0, pass = 0;
        String query = ("SELECT i_vflag, i_pass FROM t_candidate  WHERE i_candidateid = ? ");
        try
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, candiadteId);
            rs = pstmt.executeQuery();        
            while (rs.next())
            {
                vflag = rs.getInt(1);
                pass = rs.getInt(2);
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        if((vflag == 3 || vflag == 4) && pass == 2)
            s = "Yes";
        return s;
    }
    
    public ArrayList getHeaderAutoFill(String val, int allclient, String cids, String assetids) 
    {
        ArrayList list = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT t_candidate.i_candidateid, CONCAT_WS(' ', t_candidate.s_firstname, t_candidate.s_middlename, t_candidate.s_lastname), t_position.s_name, t_grade.s_name ");
        sb.append("FROM t_candidate ");
        sb.append("LEFT JOIN t_position ON (t_candidate.i_positionid = t_position.i_positionid) ");
        sb.append("LEFT JOIN t_grade ON (t_grade.i_gradeid = t_position.i_gradeid) ");
        sb.append("WHERE (CONCAT_WS(' ', t_candidate.s_firstname, t_candidate.s_middlename, t_candidate.s_lastname) LIKE ? OR CONCAT_WS(' ', t_candidate.s_firstname, t_candidate.s_lastname) LIKE ?)  ");
        if (allclient == 1)
        {
            sb.append(" AND t_candidate.i_clientid >= 0 ");
        }
        else
        {
            if (!cids.equals("")) {
                sb.append("AND (t_candidate.i_clientid IN (" + cids + ") OR (t_candidate.i_clientid <= 0)) ");
            } else {
            sb.append("AND t_client.i_clientid < 0 ");
            }
            if (!assetids.equals("")) {
                sb.append("AND (t_candidate.i_clientassetid IN (" + assetids + ") OR (t_candidate.i_clientassetid <= 0)) ");
            }
        }
        String query = sb.toString();
        sb.setLength(0);
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, "%" + (val) + "%");
            pstmt.setString(2, "%" + (val) + "%");
            print(this, "getHeaderAutoFill ::" + pstmt.toString());
            rs = pstmt.executeQuery();
            int candidateId;
            String name, positionName, gradeName;
            while (rs.next())
            {
                candidateId = rs.getInt(1);
                name = rs.getString(2) != null ? rs.getString(2) : "";
                positionName = rs.getString(3) != null ? rs.getString(3) : "";
                gradeName = rs.getString(4) != null ? rs.getString(4) : "";
                if(!gradeName.equals(""))
                    positionName += " | "+ gradeName;
                if(!positionName.equals(""))
                    name += " - " + positionName;
               list.add(new CandidateInfo (candidateId, name, positionName));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return list;
    }        
}
