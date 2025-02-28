package com.web.jxp.user;

import com.web.jxp.base.Base;
import com.web.jxp.base.Template;
import com.web.jxp.common.Common;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import static com.web.jxp.common.Common.*;
import java.util.Collection;
import java.util.LinkedList;

public class User extends Base {

    Properties user_prop = null;
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    public User() {
    }

    public UserInfo getUserAccess(String usernamepar, String password, String ip) {
        UserInfo info = null;
        try 
        {
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT t_userlogin.i_userid, t_userlogin.s_name, t_userlogin.s_email, t_userlogin.s_contact1, ");
            sb.append("t_userlogin.s_permission, s_username, s_photo , i_cflag, i_aflag , i_allclient, i_caflag ");
            sb.append("FROM t_userlogin ");
            sb.append("where t_userlogin.i_status = 1 and t_userlogin.s_username = ? and t_userlogin.s_password = ? ");
            String query = (sb.toString()).intern();
            sb.setLength(0);
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, usernamepar);
            pstmt.setString(2, cipher((password)));
            print(this, "getUserAccess :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            while (rs.next()) 
            {
                int userId = rs.getInt(1);
                String name = rs.getString(2) != null ? rs.getString(2) : "";
                String email = rs.getString(3) != null ? rs.getString(3) : "";
                String contact1 = rs.getString(4) != null ? rs.getString(4) : "";
                String permission = rs.getString(5) != null ? rs.getString(5) : "";
                String username = rs.getString(6) != null ? rs.getString(6) : "";
                String photo = rs.getString(7) != null ? rs.getString(7) : "";
                int coordinator = rs.getInt(8);
                int assessor = rs.getInt(9);
                int allclient = rs.getInt(10);
                int cassessor = rs.getInt(11);
                if (!photo.equals("")) {
                    photo = getMainPath("view_user_file") + photo;
                }
                String cids = "", assetids = "";
                int companytype = 0;
                if (allclient == 0) {
                    String innerquery = "SELECT i_clientid, s_clientassetids from t_userclient where i_userid = ?";
                    PreparedStatement innerpstmt = conn.prepareStatement(innerquery);
                    innerpstmt.setInt(1, userId);
                    ResultSet innerrs = innerpstmt.executeQuery();
                    while (innerrs.next()) {
                        int clientId = innerrs.getInt(1);
                        String clientassetids = innerrs.getString(2) != null ? innerrs.getString(2) : "";
                        if (clientId > 0) {
                            if (checkToStr(cids, "" + clientId) == false) {
                                if (cids.equals("")) {
                                    cids = "" + clientId;
                                } else {
                                    cids += "," + clientId;
                                }
                            }
                        }
                        if (!clientassetids.equals("")) {
                            if (assetids.equals("")) {
                                assetids = clientassetids;
                            } else {
                                assetids += "," + clientassetids;
                            }
                        }
                    }
                    innerrs.close();
                    innerpstmt.close();
                }
                if (cids.contains(",") || allclient == 1 || permission.equals("Y")) {
                    companytype = 1;
                } else if (!cids.equals("")) {
                    companytype = 2;
                }
                info = new UserInfo(userId, name, email, username, permission, contact1,
                        password, photo, coordinator, assessor, allclient, cassessor, cids, assetids, companytype);
            }
        } catch (Exception exception) {
            print(this, "getUserAccess :: " + exception.getMessage());
            exception.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return info;
    }

    public ArrayList getModuleListByName(int userId, boolean isFullList) {
        ArrayList list = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("select M.i_moduleid, M.s_name, M.i_type, P1.s_add, P1.s_edit, P1.s_delete, P1.s_approve, P1.s_view, M.i_subtype from t_module as M ");
        sb.append("left join (select P.i_moduleid, P.s_add, P.s_edit, P.s_delete, P.s_approve, P.s_view, P.i_userid from t_permisson AS P where i_userid = ?) as ");
        sb.append(" P1 on (M.i_moduleid = P1.i_moduleid) where M.i_status = 1 order by M.i_type, M.i_subtype, M.s_name ");
        String query = (sb.toString()).intern();
        sb.setLength(0);
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, userId);
            print(this, "getModuleListByName :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            int moduleId, type, subtype;
            String name, viewPer, addPer, editPer, deletePer, approvePer;
            while (rs.next()) 
            {
                moduleId = rs.getInt(1);
                name = rs.getString(2) != null ? rs.getString(2) : "";
                type = rs.getInt(3);
                addPer = rs.getString(4) != null ? rs.getString(4) : "";
                editPer = rs.getString(5) != null ? rs.getString(5) : "";
                deletePer = rs.getString(6) != null ? rs.getString(6) : "";
                approvePer = rs.getString(7) != null ? rs.getString(7) : "";
                viewPer = rs.getString(8) != null ? rs.getString(8) : "";
                subtype = rs.getInt(9);
                if (isFullList) {
                    list.add(new UserInfo(userId, moduleId, name, type, addPer, editPer, deletePer, approvePer, viewPer, subtype));
                } else if ((viewPer.equals("Y") || addPer.equals("Y") || editPer.equals("Y") || deletePer.equals("Y") || approvePer.equals("Y"))) {
                    list.add(new UserInfo(userId, moduleId, name, type, addPer, editPer, deletePer, approvePer, viewPer, subtype));
                }
            }
        } catch (Exception exception) {
            print(this, "getModuleListByName :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return list;
    }

    public UserInfo getLoginById(String username) {
        UserInfo info = null;
        StringBuilder sb = new StringBuilder();
        sb.append("select i_userid, s_username, s_name, s_password, s_email ");
        sb.append("from t_userlogin where t_userlogin.i_status = 1 and t_userlogin.s_username = ?");
        String query = sb.toString().intern();
        sb.setLength(0);
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, username);
            print(this, "getLoginById ::" + pstmt.toString());
            rs = pstmt.executeQuery();
            while (rs.next()) 
            {
                int userId = rs.getInt(1);
                String loginId = rs.getString(2) != null ? rs.getString(2) : "";
                String name = rs.getString(3) != null ? rs.getString(3) : "";
                String password = rs.getString(4) != null ? rs.getString(4) : "";
                String email = rs.getString(5) != null ? rs.getString(5) : "";
                password = decipher(password);
                info = new UserInfo(email, userId, name, loginId, password);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return info;
    }

    public ArrayList getUserListByName(String search, int uId, int next, int count, int clientIndex, String permissionIndex) {
        ArrayList list = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("select t_userlogin.i_userid, t_userlogin.s_name, ");
        sb.append("t_userlogin.s_contact1, t_userlogin.s_email, t_userlogin.i_status, s_code , s_permission ");
        sb.append("FROM t_userlogin ");
        sb.append(" left join t_userclient on  (t_userlogin.i_userid = t_userclient.i_userid) ");
        sb.append("where 0 = 0 ");
        if (search != null && !search.equals("")) {
            sb.append(" and (t_userlogin.s_name like ? or t_userlogin.s_code like ? OR t_userlogin.s_contact1 = ? OR t_userlogin.s_email = ? OR t_userlogin.s_username = ?) ");
        }
        if (clientIndex > 0) {
            sb.append(" and t_userclient.i_clientid = ? ");
        }
        if (permissionIndex != null && !permissionIndex.equals("")) {
            sb.append(" and  t_userlogin.s_permission = 'Y' ");
        }
        sb.append(" group by t_userlogin.i_userid order by t_userlogin.i_status, t_userlogin.s_name ");
        if (count > 0) {
            sb.append(" limit ").append(count).append(" OFFSET ").append(next * count);
        }
        String query = sb.toString();
        sb.setLength(0);

        sb.append("select count(*) from (select count(*) from t_userlogin left join t_userclient on  (t_userlogin.i_userid = t_userclient.i_userid) where 0 = 0 ");
        if (search != null && !search.equals("")) {
            sb.append(" and (t_userlogin.s_name like ? or t_userlogin.s_code like ? OR t_userlogin.s_contact1 = ? OR t_userlogin.s_email = ? OR t_userlogin.s_username = ?) ");
        }
        if (clientIndex > 0) {
            sb.append(" and (t_userclient.i_clientid = ? )");
        }
        if (permissionIndex != null && !permissionIndex.equals("")) {
            sb.append(" and  t_userlogin.s_permission = 'Y'  ");
        }
        sb.append(" group by t_userlogin.i_userid) as t1 ");
        String query_count = sb.toString();
        sb.setLength(0);
        //print(this,"getUserCount :: " + query);            
        try
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            int sno = 0;
            if (search != null && !search.equals("")) {
                pstmt.setString(++sno, "%" + (search) + "%");
                pstmt.setString(++sno, "%" + (search) + "%");
                pstmt.setString(++sno, search);
                pstmt.setString(++sno, search);
                pstmt.setString(++sno, search);
            }
            if (clientIndex > 0) {
                pstmt.setInt(++sno, clientIndex);
            }
            //print(this,"getUserListByName :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            int userId, status;
            String name, contact1, email, code, admin;
            while (rs.next()) 
            {
                userId = rs.getInt(1);
                name = rs.getString(2) != null ? rs.getString(2) : "";
                contact1 = rs.getString(3) != null ? rs.getString(3) : "";
                email = rs.getString(4) != null ? rs.getString(4) : "";
                status = rs.getInt(5);
                code = rs.getString(6) != null ? rs.getString(6) : "";
                admin = rs.getString(7) != null ? rs.getString(7) : "";
                list.add(new UserInfo(userId, name, contact1, email, status, code, admin));
            }
            rs.close();

            pstmt = conn.prepareStatement(query_count);
            sno = 0;
            if (search != null && !search.equals("")) {
                pstmt.setString(++sno, "%" + (search) + "%");
                pstmt.setString(++sno, "%" + (search) + "%");
                pstmt.setString(++sno, search);
                pstmt.setString(++sno, search);
                pstmt.setString(++sno, search);
            }
            if (clientIndex > 0) {
                pstmt.setInt(++sno, clientIndex);
            }
            rs = pstmt.executeQuery();
            while (rs.next()) {
                userId = rs.getInt(1);
                list.add(new UserInfo(userId, "", "", "", 0, ""));
            }
        } catch (Exception exception) {
            print(this, "getUserListByName :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return list;
    }

    public UserInfo getUserById(int userId) 
    {
        UserInfo info = null;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT s_name, s_contact1, t_userlogin.s_email, s_password, s_permission, t_userlogin.i_status, ");
        sb.append("s_contact2, t_userlogin.s_code, s_username, s_photo, i_managerid, i_allclient, s_address, i_cflag , i_aflag, i_caflag, s_cvfile, i_mflag, i_rflag ");
        sb.append("FROM t_userlogin where t_userlogin.i_userid = ? ");
        String query = (sb.toString()).intern();
        sb.setLength(0);
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, userId);
            print(this, "getUserById :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            while (rs.next()) 
            {
                String name = rs.getString(1) != null ? rs.getString(1) : "";
                String contact1 = rs.getString(2) != null ? rs.getString(2) : "";
                String email = rs.getString(3) != null ? rs.getString(3) : "";
                String password = rs.getString(4) != null ? rs.getString(4) : "";
                String permission = rs.getString(5) != null ? rs.getString(5) : "";
                int status = rs.getInt(6);
                String contact2 = rs.getString(7) != null ? rs.getString(7) : "";
                String code = rs.getString(8);
                String userName = rs.getString(9) != null ? rs.getString(9) : "";
                String photo = rs.getString(10) != null ? rs.getString(10) : "";
                int managerId = rs.getInt(11);
                int allclient = rs.getInt(12);
                String address = rs.getString(13) != null ? rs.getString(13) : "";
                int coordinator = rs.getInt(14);
                int assessor = rs.getInt(15);
                int cassessor = rs.getInt(16);
                String cvfile = rs.getString(17) != null ? rs.getString(17) : "";
                int ismanager = rs.getInt(18);
                int isrecruiter = rs.getInt(19);
                
                info = new UserInfo(userId, name, email, contact1, userName, decipher(password), permission, status,
                        contact2, code, photo, managerId, allclient, address, coordinator, assessor, cassessor, cvfile, ismanager, isrecruiter);
            }
        } catch (Exception exception) {
            print(this, "getUserById :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return info;
    }

    public UserInfo getUserByIdDetail(int userId) 
    {
        UserInfo info = null;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT t_userlogin.s_name, t_userlogin.s_contact1, t_userlogin.s_email, t_userlogin.s_password, ");
        sb.append("t_userlogin.i_status, t_userlogin.s_permission, t_userlogin.s_contact2, t_userlogin.s_code, ");
        sb.append("t_userlogin.s_username, t_userlogin.s_photo, t1.s_name, t_userlogin.i_allclient, t_userlogin.s_address, ");
        sb.append("t_userlogin.i_cflag, t_userlogin.i_aflag, t_userlogin.i_caflag, t_userlogin.s_cvfile, t_userlogin.i_mflag, t_userlogin.i_rflag ");
        sb.append("FROM t_userlogin ");
        sb.append("LEFT JOIN t_userlogin AS t1 ON (t_userlogin.i_managerid = t1.i_userid) ");
        sb.append("WHERE t_userlogin.i_userid = ?");
        String query = (sb.toString()).intern();
        sb.setLength(0);
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, userId);
            print(this, "getUserByIdDetail :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            while (rs.next()) 
            {
                String name = rs.getString(1) != null ? rs.getString(1) : "";
                String contact1 = rs.getString(2) != null ? rs.getString(2) : "";
                String email = rs.getString(3) != null ? rs.getString(3) : "";
                String password = rs.getString(4) != null ? rs.getString(4) : "";
                int status = rs.getInt(5);
                String permission = rs.getString(6) != null ? rs.getString(6) : "";
                String contact2 = rs.getString(7) != null ? rs.getString(7) : "";
                String code = rs.getString(8) != null ? rs.getString(8) : "";
                String userName = rs.getString(9) != null ? rs.getString(9) : "";
                String photo = rs.getString(10) != null ? rs.getString(10) : "";
                String managerName = rs.getString(11) != null ? rs.getString(11) : "";
                int allclient = rs.getInt(12);
                String address = rs.getString(13) != null ? rs.getString(13) : "";
                int coordinator = rs.getInt(14);
                int assessor = rs.getInt(15);
                int cassessor = rs.getInt(16);
                String cvfile = rs.getString(17) != null ? rs.getString(17) : "";
                int ismanager = rs.getInt(18);
                int isrecruiter = rs.getInt(19);

                info = new UserInfo(userId, name, email, contact1, userName, decipher(password),
                        status, permission, contact2, code, photo, managerName, allclient, address, 
                        coordinator, assessor, cassessor, cvfile, ismanager, isrecruiter);
            }
        } catch (Exception exception) {
            print(this, "getUserByIdDetail :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return info;
    }

    public int createUser(UserInfo info, String password, String ipAddrStr, String iplocal, int uId, ArrayList clist) {
        int userId = 0;
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("INSERT INTO t_userlogin ");
            sb.append("(s_name, s_contact1, s_email, s_permission, s_password, ");
            sb.append("s_contact2, i_status, ts_regdate, ts_moddate, s_code, s_username, s_photo, i_allclient, s_address, i_cflag, i_aflag, i_caflag, i_managerid, s_cvfile, i_mflag, i_rflag) ");
            sb.append("VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,  ?,?)");
            String query = (sb.toString()).intern();
            sb.setLength(0);
            conn = getConnection();
            pstmt = conn.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
            int scc = 0;
            pstmt.setString(++scc, info.getName());
            pstmt.setString(++scc, info.getContact1());
            pstmt.setString(++scc, info.getEmail());
            pstmt.setString(++scc, info.getPermission());
            pstmt.setString(++scc, cipher(info.getPassword()));
            pstmt.setString(++scc, info.getContact2());
            pstmt.setInt(++scc, info.getStatus());
            pstmt.setString(++scc, currDate1());
            pstmt.setString(++scc, currDate1());
            pstmt.setString(++scc, info.getCode());
            pstmt.setString(++scc, info.getUserName());
            pstmt.setString(++scc, info.getPhoto());
            pstmt.setInt(++scc, info.getAllclient());
            pstmt.setString(++scc, info.getAddress());
            pstmt.setInt(++scc, info.getCoordinator());
            pstmt.setInt(++scc, info.getAssessor());
            pstmt.setInt(++scc, info.getCassessor());
            pstmt.setInt(++scc, info.getManagerId());
            pstmt.setString(++scc, info.getCvfile());
            pstmt.setInt(++scc, info.getIsManager());
            pstmt.setInt(++scc, info.getIsRecruiter());
            print(this, "createUser :: " + pstmt.toString());
            pstmt.executeUpdate();
            rs = pstmt.getGeneratedKeys();
            while (rs.next()) {
                userId = rs.getInt(1);
            }
            rs.close();
            pstmt.close();
            if (userId > 0) {
                int size = clist.size();
                if (size > 0) {
                    query = "insert into t_userclient (i_userid, i_clientid, i_countryid, s_clientassetids, s_positionids, i_addedupdatedby, ts_regdate, ts_moddate) values (?,?,?,?,?,?,?,?) ";
                    pstmt = conn.prepareStatement(query);
                    for (int i = 0; i < size; i++) {
                        UserInfo cinfo = (UserInfo) clist.get(i);
                        if (cinfo != null) {
                            scc = 0;
                            pstmt.setInt(++scc, userId);
                            pstmt.setInt(++scc, cinfo.getClientId());
                            pstmt.setInt(++scc, cinfo.getCountryId());
                            pstmt.setString(++scc, cinfo.getAssetids());
                            pstmt.setString(++scc, cinfo.getPositionRank());
                            pstmt.setInt(++scc, uId);
                            pstmt.setString(++scc, currDate1());
                            pstmt.setString(++scc, currDate1());
                            pstmt.executeUpdate();
                        }
                    }
                    pstmt.close();
                }
            }
            createHistoryAccess(conn, uId, ipAddrStr, iplocal, "Add User", 1, userId);
        } catch (Exception exception) {
            print(this, "createUser :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }

        return userId;
    }

    public void updateUser(UserInfo info, String sids, String ipAddrStr, String iplocal, int uId, ArrayList clist) {
        try {
            int userId = info.getUserId();
            StringBuilder sb = new StringBuilder();
            sb.append("update t_userlogin set ");
            sb.append("s_name = ?, ");
            if (info.getPhoto() != null && !info.getPhoto().equals("")) {
                sb.append("s_photo = ?, ");
            }
            sb.append("s_email = ?, ");
            sb.append("s_username = ?, ");
            sb.append("s_contact1 = ?, ");
            sb.append("s_contact2 = ?, ");
            sb.append("i_managerid = ?, ");
            sb.append("i_status = ?, ");
            sb.append("s_permission = ?, ");
            sb.append("i_allclient = ?, ");
            sb.append("ts_moddate = ?, ");
            sb.append("s_address = ?, ");
            sb.append("s_code = ?, ");
            sb.append("i_cflag = ?, ");
            sb.append("i_aflag = ?, ");
            if (info.getCvfile()!= null && !info.getCvfile().equals("")) 
            {
               sb.append("s_cvfile = ?,  ");
            }
            sb.append("i_caflag = ?, ");
            sb.append("i_mflag = ?, ");
            sb.append("i_rflag = ? ");
            sb.append("where i_userid = ?");//17
            String query = (sb.toString()).intern();
            sb.setLength(0);

            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            int scc = 0;
            pstmt.setString(++scc, info.getName());
            if (info.getPhoto() != null && !info.getPhoto().equals("")) {
                pstmt.setString(++scc, info.getPhoto());
            }
            pstmt.setString(++scc, info.getEmail());
            pstmt.setString(++scc, info.getUserName());
            pstmt.setString(++scc, info.getContact1());
            pstmt.setString(++scc, info.getContact2());
            pstmt.setInt(++scc, info.getManagerId());
            pstmt.setInt(++scc, info.getStatus());
            pstmt.setString(++scc, info.getPermission());
            pstmt.setInt(++scc, info.getAllclient());
            pstmt.setString(++scc, currDate1());
            pstmt.setString(++scc, info.getAddress());
            pstmt.setString(++scc, info.getCode());
            pstmt.setInt(++scc, info.getCoordinator());
            pstmt.setInt(++scc, info.getAssessor());
            if (info.getCvfile()!= null && !info.getCvfile().equals("")) 
            {
                pstmt.setString(++scc, info.getCvfile());
            }
            pstmt.setInt(++scc, info.getCassessor());
            pstmt.setInt(++scc, info.getIsManager());
            pstmt.setInt(++scc, info.getIsRecruiter());
            pstmt.setInt(++scc, userId);

            print(this, "updateUser :: " + pstmt.toString());
            pstmt.executeUpdate();
            rs.close();
            pstmt.close();
            if (userId > 0) {
                query = " delete from t_userclient where i_userid = ?";
                pstmt = conn.prepareStatement(query);
                pstmt.setInt(1, userId);
                pstmt.executeUpdate();

                int size = clist.size();
                if (size > 0) {
                    query = "insert into t_userclient (i_userid, i_clientid, i_countryid, s_clientassetids, s_positionids, i_addedupdatedby, ts_regdate, ts_moddate) values (?,?,?,?,?,?,?,?) ";
                    pstmt = conn.prepareStatement(query);
                    for (int i = 0; i < size; i++) {
                        UserInfo cinfo = (UserInfo) clist.get(i);
                        if (cinfo != null) {
                            scc = 0;
                            pstmt.setInt(++scc, userId);
                            pstmt.setInt(++scc, cinfo.getClientId());
                            pstmt.setInt(++scc, cinfo.getCountryId());
                            pstmt.setString(++scc, cinfo.getAssetids());
                            pstmt.setString(++scc, cinfo.getPositionRank());
                            pstmt.setInt(++scc, uId);
                            pstmt.setString(++scc, currDate1());
                            pstmt.setString(++scc, currDate1());
                            pstmt.executeUpdate();
                        }
                    }
                    pstmt.close();
                }
            }
            createHistoryAccess(conn, uId, ipAddrStr, iplocal, "Update User", 1, info.getUserId());
        } catch (Exception exception) {
            print(this, "updateUser :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
    }

    public int deleteUser(int id, int status) {
        int cc = 0;
        try {
            conn = getConnection();
            String query = ("update t_userlogin set i_status = ? where i_userid = ?");
            print(this,"deleteUser :: " + query);
            try {
                pstmt = conn.prepareStatement(query);
                if (status == 1) {
                    pstmt.setInt(1, 2);
                } else {
                    pstmt.setInt(1, 1);
                }
                pstmt.setInt(2, id);
                cc = pstmt.executeUpdate();
            } catch (Exception exception) {
                print(this, "deleteUser :: " + exception.getMessage());
            }
        } catch (Exception exception) {
            print(this, "deleteUser :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return cc;
    }

    public int checkDuplicacy(int userId, String userName, String email) {
        int check = 0;
        StringBuilder sb = new StringBuilder();
        sb.append("select s_email FROM t_userlogin where (s_email = ? OR s_username = ?) and i_status in (1,2) ");
        if (userId > 0) {
            sb.append(" and i_userid != ? ");
        }
        String query = (sb.toString()).intern();
        sb.setLength(0);
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            int scc = 0;
            pstmt.setString(++scc, email);
            pstmt.setString(++scc, userName);
            if (userId > 0) {
                pstmt.setInt(++scc, userId);
            }
            //print(this,"checkDuplicacy :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                check = 1;
            }
        } catch (Exception exception) {
            print(this, "checkDuplicacy :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return check;
    }

    public String getMessage(String prop) {
        String message = user_prop.getProperty(prop);
        if (message != null) {
            return message;
        } else {
            return "";
        }
    }

    public UserInfo getPassword(int userId) {
        UserInfo info = null;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT t_userlogin.i_userid, t_userlogin.s_name, t_userlogin.s_password ");
        sb.append(" FROM t_userlogin where t_userlogin.i_userid = ?");
        String query = (sb.toString()).intern();
        sb.setLength(0);
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, userId);
            //print(this,"getPassword :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            while (rs.next())
            {
                userId = rs.getInt(1);
                String name = rs.getString(2) != null ? rs.getString(2) : "";
                String password = rs.getString(3) != null ? rs.getString(3) : "";
                info = new UserInfo(userId, name, decipher(password));
            }
        } catch (Exception exception) {
            print(this, "getPassword :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return info;
    }

    public ArrayList getSortedListByGroup(int type, ArrayList listpar) 
    {
        ArrayList list = new ArrayList();
        try 
        {
            if (listpar.size() > 0) 
            {
                int moduleId, type1, userId, subtype;
                String name, viewPer, addPer, editPer, deletePer, approvePer;

                for (int i = 0; i < listpar.size(); i++) {
                    UserInfo info = (UserInfo) listpar.get(i);
                    if (info != null) {
                        if (info.getType() == type) {
                            moduleId = info.getModuleId();
                            name = info.getName();
                            type1 = info.getType();
                            addPer = info.getAddper();
                            editPer = info.getEditper();
                            deletePer = info.getDeleteper();
                            approvePer = info.getApproveper();
                            userId = info.getUserId();
                            viewPer = info.getViewper();
                            subtype = info.getSubtype();
                            list.add(new UserInfo(userId, moduleId, name, type1, addPer, editPer, deletePer, approvePer, viewPer, subtype));
                        }
                    }
                }
            }
        } 
        catch (Exception exception) 
        {
            print(this, "getPermissionListModify :: " + exception.getMessage());
        }
        return list;
    }
    
    public ArrayList getSortedSubListByGroup(int type, int subtype, ArrayList listpar) 
    {
        ArrayList list = new ArrayList();
        try 
        {
            if (listpar.size() > 0) 
            {
                int moduleId, type1, userId, subtype1;
                String name, viewPer, addPer, editPer, deletePer, approvePer;
                for (int i = 0; i < listpar.size(); i++) 
                {
                    UserInfo info = (UserInfo) listpar.get(i);
                    if (info != null) 
                    {
                        if (info.getType() == type && info.getSubtype() == subtype) 
                        {
                            moduleId = info.getModuleId();
                            name = info.getName();
                            type1 = info.getType();
                            addPer = info.getAddper();
                            editPer = info.getEditper();
                            deletePer = info.getDeleteper();
                            approvePer = info.getApproveper();
                            userId = info.getUserId();
                            viewPer = info.getViewper();
                            subtype1 = info.getSubtype();
                            list.add(new UserInfo(userId, moduleId, name, type1, addPer, editPer, deletePer, approvePer, viewPer, subtype1));
                        }
                    }
                }
            }
        } 
        catch (Exception exception) 
        {
            print(this, "getPermissionListModify :: " + exception.getMessage());
        }
        return list;
    }

    public void deletePermisson(int userId) {
        if (userId > 0) {
            StringBuilder sb = new StringBuilder();
            sb.append("delete from t_permisson where i_userid = ?");
            String query = (sb.toString()).intern();
            sb.setLength(0);
            //print(this,"deletePermisson :: " + pstmt.toString());
            try {
                conn = getConnection();
                pstmt = conn.prepareStatement(query);
                pstmt.setInt(1, userId);
                pstmt.executeUpdate();
            } catch (Exception exception) {
                print(this, "deletePermisson :: " + exception.getMessage());
            } finally {
                close(conn, pstmt, rs);
            }
        }
    }

    public void insertPermission(UserInfo info, Connection conn) {
        StringBuilder sb = new StringBuilder();
        sb.append("insert into t_permisson ");
        sb.append("(i_userid, i_moduleid, s_add, s_edit, s_delete, s_approve, s_view, i_status, ts_regdate, ts_moddate)");
        sb.append(" values (?,?,?,?,?,?,?,?,?,?)");
        String query = (sb.toString()).intern();
        sb.setLength(0);
        try {
            pstmt = conn.prepareStatement(query);
            int scc = 0;
            pstmt.setInt(++scc, info.getUserId());
            pstmt.setInt(++scc, info.getModuleId());
            pstmt.setString(++scc, info.getAddper());
            pstmt.setString(++scc, info.getEditper());
            pstmt.setString(++scc, info.getDeleteper());
            pstmt.setString(++scc, info.getApproveper());
            pstmt.setString(++scc, info.getViewper());
            pstmt.setInt(++scc, 1);
            pstmt.setString(++scc, currDate1());
            pstmt.setString(++scc, currDate1());
            //print(this,"insertPermission :: " + pstmt.toString());
            pstmt.executeUpdate();
            pstmt.close();
        } catch (Exception exception) {
            print(this, "insertPermission :: " + exception.getMessage());
        }
    }

    public String getForgotMessage(String name, String email, String password, String htmlFile) {
        String adminpage = getMainPath("template_path") + htmlFile;
        HashMap hashmap = new HashMap();
        Template template = new Template(adminpage);
        hashmap.put("NAME", name);
        hashmap.put("EMAIL", email);
        hashmap.put("PASSWORD", password);
        return template.patch(hashmap);
    }

    public ArrayList getFinalRecord(ArrayList l, String colId, int tp) {
        ArrayList list = new ArrayList();
        HashMap record = new HashMap();
        int total = 0;
        if (l != null) {
            total = l.size();
        }
        try {
            if (total > 0) {
                for (int i = 0; i < total; i++) {
                    UserInfo info = (UserInfo) l.get(i);
                    record.put(getInfoValue(info, colId), info);
                }
            }
            Map map = null;
            map = sortByName(record, tp);
            Iterator it = map.keySet().iterator();
            while (it.hasNext()) {
                String key = (String) it.next();
                for (int i = 0; i < l.size(); i++) {
                    UserInfo rInfo = (UserInfo) l.get(i);
                    String str = getInfoValue(rInfo, colId);
                    if (str.equals(key)) {
                        list.add(rInfo);
                    }
                }
            }
        } catch (Exception e) {
            print(this, "getFinalRecord" + e.getMessage());
        }
        return list;
    }

    public String getInfoValue(UserInfo info, String i) {
        String infoval = "";
        if (i != null && i.equals("1")) {
            infoval = info.getCode() != null ? info.getCode() : "";
        } else if (i != null && i.equals("2")) {
            infoval = info.getName() != null ? info.getName() : "";
        } else if (i != null && i.equals("3")) {
            infoval = info.getContact1() != null ? info.getContact1() : "";
        } else if (i != null && i.equals("4")) {
            infoval = info.getEmail() != null ? info.getEmail() : "";
        }
        return infoval;
    }

    public String randomString(int len) {
        final String AB = "1234567890";
        Random rnd = new Random();
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            sb.append(AB.charAt(rnd.nextInt(AB.length())));
        }
        return sb.toString();
    }

    public String getRandomPassword(int length) {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz!@#$";
        int numberOfCodes = 0;//controls the length of alpha numberic string
        String code = "";
        while (numberOfCodes < length) {
            char c = chars.charAt((int) (Math.random() * chars.length()));
            code += c;
            numberOfCodes++;
        }
        return code;
    }

    public String getPhoneNo(String phoneNo) {
        if (phoneNo.length() == 10) {
            return phoneNo;
        } else if (phoneNo.length() > 10) {
            phoneNo = phoneNo.substring(phoneNo.length() - 10, phoneNo.length());
            return phoneNo;
        } else {
            return "";
        }
    }

    public int updateNewPassword(int userId, String password, int flag) {
        int cc = 0;
        String query = "update t_userlogin set s_password = ? where i_userid = ?";
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, cipher((password)));
            pstmt.setInt(2, userId);
            cc = pstmt.executeUpdate();
            //print(this,"updateNewPassword :: " + pstmt.toString());
        } catch (Exception exception) {
            print(this, "updateNewPassword :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return cc;
    }

    public Collection getManagers(int userId) {
        Collection coll = new LinkedList();
        String query = ("SELECT i_userid, s_name FROM t_userlogin where i_status = 1 ").intern();
        if (userId > 0) {
            query += " and i_userid != ? ";
        }
        query += " order by s_name";
        coll.add(new UserInfo(-1, "- Select -"));
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            if (userId > 0) {
                pstmt.setInt(1, userId);
            }
            rs = pstmt.executeQuery();
            int id;
            String name;
            while (rs.next()) 
            {
                id = rs.getInt(1);
                name = rs.getString(2) != null ? rs.getString(2) : "";
                coll.add(new UserInfo(id, name));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return coll;
    }

    public Collection getClients() {
        Collection coll = new LinkedList();
        String query = ("SELECT i_clientid, s_name FROM t_client where i_status = 1 order by s_name");
        //coll.add(new UserInfo(-1, "- Select -"));
        try
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            rs = pstmt.executeQuery();
            int id;
            String name;
            while (rs.next()) 
            {
                id = rs.getInt(1);
                name = rs.getString(2) != null ? rs.getString(2) : "";
                coll.add(new UserInfo(id, name));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return coll;
    }

    public Collection getClientLocations(int clientId) 
    {
        Collection coll = new LinkedList();
        if (clientId > 0) 
        {
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT DISTINCT(t_clientasset.i_countryid), t_country.s_name FROM t_clientasset ");
            sb.append("left join t_country on (t_country.i_countryid = t_clientasset.i_countryid) ");
            sb.append("where t_clientasset.i_clientid = ? and t_clientasset.i_status = 1 order by t_country.s_name");
            String query = sb.toString();
            sb.setLength(0);
            coll.add(new UserInfo(-1, "- Select -"));
            try 
            {
                conn = getConnection();
                pstmt = conn.prepareStatement(query);
                pstmt.setInt(1, clientId);
                print(this, "getClientLocations " + pstmt.toString());
                rs = pstmt.executeQuery();
                int id;
                String name;
                while (rs.next()) 
                {
                    id = rs.getInt(1);
                    name = rs.getString(2) != null ? rs.getString(2) : "";
                    coll.add(new UserInfo(id, name));
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                close(conn, pstmt, rs);
            }
        } else {
            coll.add(new UserInfo(-1, "- Select -"));
        }
        return coll;
    }

    public Collection getClientAssets(int clientId, int countryId) 
    {
        Collection coll = new LinkedList();
        if (clientId > 0) 
        {
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT t_clientasset.i_clientassetid, t_clientasset.s_name FROM t_clientasset ");
            sb.append("where t_clientasset.i_clientid = ? and t_clientasset.i_status = 1 ");
            if (countryId > 0) {
                sb.append(" and t_clientasset.i_countryid = ? ");
            }
            sb.append(" order by t_clientasset.s_name");
            String query = sb.toString();
            sb.setLength(0);
            //coll.add(new UserInfo(-1, "- Select -"));
            try 
            {
                conn = getConnection();
                pstmt = conn.prepareStatement(query);
                pstmt.setInt(1, clientId);
                if (countryId > 0) {
                    pstmt.setInt(2, countryId);
                }
                rs = pstmt.executeQuery();
                int id;
                String name;
                while (rs.next()) 
                {
                    id = rs.getInt(1);
                    name = rs.getString(2) != null ? rs.getString(2) : "";
                    coll.add(new UserInfo(id, name));
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                close(conn, pstmt, rs);
            }
        }
        return coll;
    }

    public ArrayList getClientList(int userId) 
    {
        ArrayList list = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT t_userclient.i_clientid, t_userclient.i_countryid, t_userclient.s_clientassetids, t_client.s_name, t_country.s_name, t_userclient.s_positionids  FROM t_userclient ");
        sb.append("left join t_country on (t_country.i_countryid = t_userclient.i_countryid) ");
        sb.append("left join t_client on (t_client.i_clientid = t_userclient.i_clientid) ");
        sb.append("where t_userclient.i_userid = ? order by t_client.s_name, t_country.s_name");
        String query = sb.toString();
        sb.setLength(0);
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, userId);
            print(this, "getClientList :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            int clientId, countryId;
            String assetids, clientName, countryName, assettext, postionids, positiontext;
            while (rs.next()) 
            {
                clientId = rs.getInt(1);
                countryId = rs.getInt(2);
                assetids = rs.getString(3) != null ? rs.getString(3) : "";
                clientName = rs.getString(4) != null ? rs.getString(4) : "";
                countryName = rs.getString(5) != null ? rs.getString(5) : "";
                postionids = rs.getString(6) != null ? rs.getString(6) : "";
                assettext = "";
                if (assetids != null && !assetids.equals("")) {
                    String query_inner = "select GROUP_CONCAT(s_name) from t_clientasset where i_clientassetid IN (" + assetids + ") ";
                    PreparedStatement pstmt_inner = conn.prepareStatement(query_inner);
                    ResultSet rs_inner = pstmt_inner.executeQuery();
                    while (rs_inner.next()) {
                        assettext = rs_inner.getString(1) != null ? rs_inner.getString(1).replaceAll(",", ", ") : "";
                    }
                    rs_inner.close();
                    pstmt_inner.close();
                }
                positiontext = "";
                if (postionids != null && !postionids.equals("")) {
                    String query_pr = "SELECT GROUP_CONCAT(CONCAT_WS(' ', t_position.s_name, ' | ', t_grade.s_name)) FROM t_position LEFT  JOIN t_grade ON (t_grade.i_gradeid = t_position.i_gradeid ) WHERE t_position.i_positionid IN (" + postionids + ") ";
                    PreparedStatement pstmt_pr = conn.prepareStatement(query_pr);
                    print(this, "pstmt_pr  ::::" + pstmt_pr ); 
                    ResultSet rs_pr = pstmt_pr.executeQuery();
                    while (rs_pr.next()) {
                        positiontext = rs_pr.getString(1) != null ? rs_pr.getString(1).replaceAll(",", ", ") : "";
                    }
                    rs_pr.close();
                    pstmt_pr.close();
                }
                list.add(new UserInfo(clientId, countryId, clientName, countryName, assetids, assettext, postionids, positiontext));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return list;
    }

    public int updatePassword(String newPassword, int userId) {
        int cc = 0;
        try {
            String query = ("update t_userlogin set s_password = ?, ts_moddate = ? where i_userid = ?");
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, cipher(newPassword));
            pstmt.setString(2, currDate1());
            pstmt.setInt(3, userId);
            //print(this,"updatePassword :: " + pstmt.toString());
            cc = pstmt.executeUpdate();
        } catch (Exception exception) {
            print(this, "updatePassword :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return cc;
    }

    public ArrayList getListForExcel(String search) {
        ArrayList list = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("select t_userlogin.s_name, ");
        sb.append("t_userlogin.s_contact1, t_userlogin.s_email, t_userlogin.s_code, t_userlogin.s_permission, t_userlogin.i_status ");
        sb.append("FROM t_userlogin ");
        sb.append("where 0 = 0 ");
        if (search != null && !search.equals("")) {
            sb.append(" and (t_userlogin.s_name like ? or t_userlogin.s_code like ? OR t_userlogin.s_contact1 = ? OR t_userlogin.s_email = ? OR t_userlogin.s_username = ?) ");
        }
        sb.append(" order by t_userlogin.i_status, t_userlogin.s_name ");
        String query = sb.toString().intern();
        sb.setLength(0);
        try
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            int sno = 0;
            if (search != null && !search.equals("")) {
                pstmt.setString(++sno, "%" + (search) + "%");
                pstmt.setString(++sno, "%" + (search) + "%");
                pstmt.setString(++sno, search);
                pstmt.setString(++sno, search);
                pstmt.setString(++sno, search);
            }
            Common.print(this, "getListForExcel :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String name, contact1, email, code, admin;
            int Status;
            while (rs.next()) 
            {
                name = rs.getString(1) != null ? rs.getString(1) : "";
                contact1 = rs.getString(2) != null ? rs.getString(2) : "";
                email = rs.getString(3) != null ? rs.getString(3) : "";
                code = rs.getString(4) != null ? rs.getString(4) : "";
                admin = rs.getString(5) != null ? rs.getString(5) : "";
                Status = rs.getInt(6);
                if (admin.equals("Y")) {
                    admin = "Yes";
                } else {
                    admin = "No";
                }
                list.add(new UserInfo(0, name, contact1, email, Status, code, admin));
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
    
    // position-rank    
    public Collection setPositionRank(String assetIds) 
    {
        Collection coll = new LinkedList();
        if (!assetIds.equals("")) 
        {
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT t_position.i_positionid, t_assettype.s_name, t_position.s_name, t_grade.s_name "); 
            sb.append("FROM t_clientassetposition "); 
            sb.append("LEFT JOIN t_position ON ( t_clientassetposition.i_positionid = t_position.i_positionid)  "); 
            sb.append("LEFT JOIN t_grade ON ( t_grade.i_gradeid = t_position.i_gradeid) "); 
            sb.append("LEFT JOIN t_assettype ON ( t_assettype.i_assettypeid = t_position.i_assettypeid)  "); 
            sb.append("WHERE 0 = 0 AND t_clientassetposition.i_clientassetid IN ("+assetIds+") ORDER BY  t_assettype.s_name, t_position.s_name, t_grade.s_name");            
            
            String query = sb.toString();
            sb.setLength(0);
            try
            {
                conn = getConnection();
                pstmt = conn.prepareStatement(query);
                Common.print(this, "setPositionRank :: " + pstmt.toString());
                rs = pstmt.executeQuery();
                String assettype, position, grade;
                int positionId;
                while (rs.next()) 
                {
                    positionId = rs.getInt(1);
                    assettype = rs.getString(2) != null ? rs.getString(2) : "";
                    position = rs.getString(3) != null ? rs.getString(3) : "";
                    grade = rs.getString(4) != null ? rs.getString(4) : "";                    
                   
                    if (!assettype.equals("")) {
                        if (!position.equals("")) {
                            if (!grade.equals("")) {
                                assettype+= " - "  + position + " | "+ grade;
                            }
                        }
                    }
                    coll.add(new UserInfo(positionId, assettype));
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                close(conn, pstmt, rs);
            }
        }
        return coll;
    }
}
