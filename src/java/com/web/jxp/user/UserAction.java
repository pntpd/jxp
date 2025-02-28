package com.web.jxp.user;

import com.web.jxp.base.StatsInfo;
import com.web.jxp.base.Validate;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Collection;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import static com.web.jxp.common.Common.*;
import java.util.Date;
import java.util.Stack;
import org.apache.struts.upload.FormFile;

public class UserAction extends Action
{
    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        UserForm frm = (UserForm) form;
        User user = new User();
        Validate vobj = new Validate();

        int count = user.getCount();
        String search = frm.getSearch() != null ? frm.getSearch() : "";
        frm.setSearch(search);
        Collection clients = user.getClients();
        frm.setClients(clients);
        int clientIndex = frm.getClientIndex();
        frm.setClientIndex(clientIndex);
        String permissionIndex = frm.getPermissionIndex() != null ? frm.getPermissionIndex() : "";
        frm.setPermissionIndex(permissionIndex);
        int uId = 0;
        String permission = "N",loginUsername="";
        if (request.getSession().getAttribute("LOGININFO") != null)
        {
            UserInfo uInfo = (UserInfo) request.getSession().getAttribute("LOGININFO");
            if(uInfo != null)
            {
                permission = uInfo.getPermission();
                uId = uInfo.getUserId();
                loginUsername = uInfo.getName();
            }
        }
        int check_user = user.checkUserSession(request, 1, permission);
        if (check_user == -1)
            return mapping.findForward("default");        
        else if (check_user == -2)
        {
            String authmess = user.getMainPath("user_auth");
            authmess = authmess.replaceAll("__MODULE__", "User");
            request.setAttribute("AUTHMESSAGE", authmess);
            return mapping.findForward("auth");
        }            
        if (frm.getDoAdd () != null && frm.getDoAdd().equals("yes"))
        {
            frm.setDoAdd("no");
            print(this,"doAdd block.");
            request.getSession().removeAttribute("MODIFYUSER");
            ArrayList moduleperList = user.getModuleListByName(-1, true);
            request.getSession().setAttribute("MODULEPER_LIST", moduleperList);
            request.getSession().removeAttribute("LIST");
            frm.setUserNameOld("");
            frm.setUserId (-1);
            Collection managers = user.getManagers(-1);
            frm.setManagers(managers);
            frm.setClients(clients);
            frm.setClientId(-1);
            Collection countries = user.getClientLocations(-1);
            frm.setCountries(countries);
            Collection positions = user.setPositionRank("-1");
            frm.setPositions(positions);
            frm.setCountryId(-1);
            request.removeAttribute("PHOTO");
            request.getSession().removeAttribute("POSITION_IDS");
            saveToken(request);
            return mapping.findForward("add_user");
        }
        else if (frm.getDoModify () != null && frm.getDoModify ().equals("yes"))
        {
            frm.setDoModify("no");
            print(this,"doModify block.");
            int userId = frm.getUserId();
            UserInfo info = user.getUserById (userId);
            request.getSession().setAttribute("MODIFYUSER", info);
            ArrayList moduleperList = user.getModuleListByName(userId, true);
            request.getSession().setAttribute("MODULEPER_LIST", moduleperList);
            ArrayList list = user.getClientList(userId);
            request.getSession().setAttribute("LIST", list);
            Collection managers = user.getManagers(userId);
            frm.setManagers(managers);
            frm.setUserId (frm.getUserId ());
            frm.setClients(clients);
            frm.setClientId(-1);
            Collection countries = user.getClientLocations(-1);
            frm.setCountries(countries);
            frm.setCountryId(-1);
            int coordinator = frm.getCoordinator();
            frm.setCoordinator(coordinator);
            int assessor = frm.getAssessor();
            frm.setAssessor(assessor);
            int cassessor = frm.getCassessor();
            frm.setCassessor(cassessor);
            int ismanager = frm.getIsManager();
            frm.setIsManager(ismanager);
            int isRecruiter = frm.getIsRecruiter();
            frm.setIsRecruiter(isRecruiter);
            
            if(info != null)
            {
                if (info.getName() != null)
                    frm.setName (info.getName ());
                if (info.getContact1() != null)
                    frm.setContact1(info.getContact1());
                frm.setStatus(info.getStatus());
                if (info.getUserName () != null)
                    frm.setUserName (info.getUserName ());
                if (info.getUserName () != null)
                    frm.setUserNameOld(info.getUserName());
                if(info.getPermission() != null)
                    frm.setPermission(info.getPermission());
                if(info.getContact2() != null)
                    frm.setContact2(info.getContact2());
                if(info.getEmail() != null)
                    frm.setEmail(info.getEmail());
                if(info.getPhoto() != null)
                    frm.setPhotoHidden(info.getPhoto()); 
                frm.setCode(info.getCode());
                frm.setManagerId(info.getManagerId());
                frm.setAllclient(info.getAllclient());
                if(info.getAddress() != null)
                    frm.setAddress(info.getAddress());
                frm.setCoordinator(info.getCoordinator());
                frm.setAssessor(info.getAssessor());
                frm.setCassessor(info.getCassessor());
                frm.setIsManager(info.getIsManager());
                frm.setIsRecruiter(info.getIsRecruiter());
                if (info.getCvfile() != null) {
                    frm.setCvHidden(info.getCvfile());
                }
                request.setAttribute("PHOTO", info.getPhoto());
                request.setAttribute("CVFILE", info.getCvfile());
                request.getSession().setAttribute("POSITION_IDS", info.getPositionRank());
            }            
            saveToken(request);
            return mapping.findForward("add_user");
        }
        else if(frm.getDoSaveUser () != null && frm.getDoSaveUser().equals("yes"))
        {
            frm.setDoSaveUser("no");
            print(this,"doSave block.");
            if(isTokenValid(request))
            {
                print(this,"doSave block. token ");
                resetToken(request);
                int uid = frm.getUserId ();
                String name =  vobj.replacename(frm.getName());
                String mobile = vobj.replaceint(frm.getContact1());
                String userName = vobj.replacedesc(frm.getUserName ());
                String email = vobj.replacedesc(frm.getEmail());
                String per = frm.getPermission() != null ? frm.getPermission() : "N";
                int status = 1;
                String contact2 = frm.getContact2();
                String code = frm.getCode();
                int managerId = frm.getManagerId();
                int allclient = frm.getAllclient();
                String address = frm.getAddress();
                int coordinator = frm.getCoordinator();
                int assessor = frm.getAssessor();
                int cassessor = frm.getCassessor();
                int ismanager = frm.getIsManager();
                int isRecruiter = frm.getIsRecruiter();
                String positionRank[] = frm.getPositionRank();                
                String postionstr = vobj.replacename(makeCommaDelimString(positionRank));
                
                int chkdup = user.checkDuplicacy(uid, userName, email);
                if(chkdup > 0)
                {
                    print(this,"checkDuplicacy.");
                    Collection managers = user.getManagers(uid);
                    frm.setManagers(managers);
                    frm.setManagerId(managerId);
                    frm.setName(name);
                    frm.setContact1(mobile);
                    frm.setUserName(userName);
                    frm.setPermission(per);
                    frm.setCode(code);
                    if(chkdup == 1)                        
                        request.getSession().setAttribute("MESSAGE", "User already exists");
                        request.getSession().setAttribute("POSITION_IDS", postionstr);
                    saveToken(request);
                    return mapping.findForward("add_user");
                }
                String password = user.getRandomPassword(8);
                String add_user_file = user.getMainPath("add_user_file");
                String foldername = user.createFolder(add_user_file);
                Date now = new Date();
                String fn = String.valueOf(now.getTime());
                String fileName1 = "";
                FormFile image = frm.getPhoto();
                if(image != null && image.getFileSize() > 0)
                {
                    fileName1 = user.uploadFile(uid, frm.getPhotoHidden(), image, fn, add_user_file, foldername);
                }
                
                String fn1 = String.valueOf(now.getTime());
                String fileName2 = "";
                FormFile filename2 = frm.getCvfile();

                if (filename2 != null && filename2.getFileSize() > 0)
                {
                    fileName2 = user.uploadFile(uid, frm.getCvHidden(), filename2, fn1 + "_1", add_user_file, foldername);
                }
                
                request.getSession().removeAttribute("POSITION_IDS");
                UserInfo info = new UserInfo(uid, name, email, mobile,userName, password, per, status, contact2, code, fileName1, 
                        managerId, allclient, address, coordinator, assessor,cassessor, fileName2, postionstr, ismanager, isRecruiter);
                String ipAddrStr = request.getRemoteAddr();
                String iplocal = user.getLocalIp();
                ArrayList clist = new ArrayList();
                if(request.getSession().getAttribute("LIST") != null)
                {
                    clist = (ArrayList) request.getSession().getAttribute("LIST");
                    request.getSession().removeAttribute("LIST");
                }
                if (frm.getUserId () <= 0)
                {
                    uid = user.createUser (info, password, ipAddrStr, iplocal, uId, clist);                    
                    String cc[] = new String[0];
                    String bcc[] = new String[0];
                    String to[] = new String[1];
                    to[0] = email;
                    
                    String mailBody = user.getForgotMessage(name, userName, password, "newpassword.html");
                    String file_maillog = user.getMainPath("file_maillog");
                    java.util.Date nowmail = new java.util.Date();
                    String fn_mail = "np-"+String.valueOf(nowmail.getTime());
                    String filePath = user.createFolder(file_maillog);
                    String fname = user.writeHTMLFile(mailBody, file_maillog+"/"+filePath, fn_mail);
                    if(uid > 0)
                    {
                        StatsInfo sinfo = user.postMailAttach(to, cc, bcc, mailBody, "JourneyXPro: Login Credentials", "", "", -1);
                        String from = "";
                        if(sinfo != null)
                        {
                            from = sinfo.getDdlLabel();
                        }
                        user.createMailLog(1, name, email, "", "", from, "JourneyXPro: Login Credentials", filePath+"/"+fname,"",0,loginUsername,0);                        
                        //request.getSession().setAttribute("MESSAGE", "Data saved successfully.");
                    }
                    else
                    {
                        request.getSession().setAttribute("MESSAGE", "Data not saved.");
                    }                    
                    //request.getSession().setAttribute("MESSAGE", "Data saved successfully.");
                    ArrayList list = user.getUserListByName (search, uId, 0, count, clientIndex, permissionIndex);                    
                    int cnt = 0;
                    if(list.size() > 0)
                    {
                        UserInfo cinfo = (UserInfo) list.get(list.size() - 1);
                        cnt = cinfo.getUserId();
                        list.remove(list.size() - 1);
                    }
                    request.getSession().setAttribute("USER_LIST", list);
                    request.getSession().setAttribute("COUNT_LIST", cnt+"");
                    request.getSession().setAttribute("NEXT", "0");
                    request.getSession().setAttribute("NEXTVALUE", "1");
                }
                else
                {
                    print(this,"updateUser.");
                    user.updateUser(info, "", ipAddrStr, iplocal, uid, clist);                    
                    if(uid > 0)
                    {
                        request.setAttribute("MESSAGE", "Data updated successfully.");
                    }
                    else
                    {
                        request.setAttribute("MESSAGE", "Data not updated.");
                    }
                    int next = 0;
                    if(request.getSession().getAttribute("NEXTVALUE") != null)
                    {
                        next = Integer.parseInt((String) request.getSession().getAttribute("NEXTVALUE"));
                    }
                    next = next - 1;
                    if(next < 0)
                        next = 0;
                    ArrayList list = user.getUserListByName( search, uId, next, count, clientIndex,permissionIndex);                    
                    int cnt = 0;
                    if(list.size() > 0)
                    {
                        UserInfo cinfo = (UserInfo) list.get(list.size() - 1);
                        cnt = cinfo.getUserId();
                        list.remove(list.size() - 1);
                    }
                    request.getSession().setAttribute("USER_LIST", list);
                    request.getSession().setAttribute("COUNT_LIST", cnt+"");
                    request.getSession().setAttribute("NEXT", next + "");
                    request.getSession().setAttribute("NEXTVALUE", (next+1) +"");
                }
                user.deletePermisson(uid);
                if(!per.equals("Y") && uid > 0)
                {
                    String permissionId[] = frm.getModuleIdArr();
                    String approve[] = frm.getApproveRight();
                    String view[] = frm.getViewRight();
                    String add[] = frm.getAddRight();
                    String edit[] = frm.getEditRight();
                    String delete[] = frm.getDeleteRight();
                    Connection conn = null;
                    try
                    {
                        conn = user.getConnection();
                        if (permissionId.length > 0)
                        { 
                            for (int i = 0; i < permissionId.length; i++)
                            {
                                String addId = "N";
                                String editId = "N";
                                String deleteId = "N";
                                String approveId = "N";
                                String viewId = "N";
                                try
                                {
                                    if (add != null && add.length > 0)
                                    {
                                        for (int j = 0; j < add.length; j++)
                                        {
                                            if (add[j].equals(permissionId[i]))
                                            {
                                                addId = "Y";
                                                break;
                                            }
                                        }
                                    }
                                    if (edit != null && edit.length > 0)
                                    {
                                        for (int j = 0; j < edit.length; j++)
                                        {
                                            if (edit[j].equals(permissionId[i]))
                                            {
                                                editId = "Y";
                                                break;
                                            }
                                        }
                                    }
                                    if (delete != null && delete.length > 0)
                                    {
                                        for (int j = 0; j < delete.length; j++)
                                        {
                                            if (delete[j].equals(permissionId[i]))
                                            {
                                                deleteId = "Y";
                                                break;
                                            }
                                        }
                                    }
                                    if (approve != null && approve.length > 0)
                                    {
                                        for (int j = 0; j < approve.length; j++)
                                        {
                                            if (approve[j].equals(permissionId[i]))
                                            {
                                                approveId = "Y";
                                                break;
                                            }
                                        }
                                    }
                                    if (view != null && view.length > 0)
                                    {
                                        for (int j = 0; j < view.length; j++)
                                        {
                                            if (view[j].equals(permissionId[i]))
                                            {
                                                viewId = "Y";
                                                break;
                                            }
                                        }
                                    }

                                }
                                catch (Exception e)
                                {
                                    print(this,"Error :: " + e.getMessage());
                                }
                                int moduleId = Integer.parseInt(permissionId[i]);
                                UserInfo inffo = new UserInfo(uid, moduleId, "", 0, addId, editId, deleteId, approveId, viewId, 0);
                                if (viewId.equals("Y") || addId.equals("Y") || editId.equals("Y") || deleteId.equals("Y") || approveId.equals("Y"))
                                {
                                    user.insertPermission(inffo, conn);
                                }
                            }
                        }
                    }
                    catch(Exception e)
                    {
                        e.printStackTrace();
                    }
                    finally
                    {
                        user.close(conn,null,null);
                    }
                }
            }
            return mapping.findForward("display");
        }
        else if(frm.getDoDetail() != null && frm.getDoDetail().equals("yes"))
        {
            frm.setDoDetail("no");
            print(this,"user detail block.");
            int userId = frm.getUserId();
            UserInfo info = user.getUserByIdDetail(userId);
            request.removeAttribute("USER_DETAIL");
            request.setAttribute("USER_DETAIL", info);            
            ArrayList moduleperList = user.getModuleListByName(userId, false);
            request.getSession().setAttribute("MODULEPER_LIST", moduleperList);
            ArrayList clist = user.getClientList(userId);
            request.setAttribute("CLIST", clist);
            return mapping.findForward("detail_user");
        }
        else if(frm.getDoCancel() !=null && frm.getDoCancel().equals("yes"))
        {
            frm.setDoCancel("no");
            print(this,"doCancel block");
            int next = 0;
            if(request.getSession().getAttribute("NEXTVALUE") != null)
            {
                next = Integer.parseInt((String) request.getSession().getAttribute("NEXTVALUE"));
            }
            next = next - 1;
            if(next < 0)
                next = 0;
            ArrayList list = user.getUserListByName( search, uId, next, count, clientIndex,permissionIndex);            
            int cnt = 0;
            if(list.size() > 0)
            {
                UserInfo cinfo = (UserInfo) list.get(list.size() - 1);
                cnt = cinfo.getUserId();
                list.remove(list.size() - 1);
            }
            request.getSession().setAttribute("USER_LIST", list);
            request.getSession().setAttribute("COUNT_LIST", cnt+"");
            request.getSession().setAttribute("NEXT", next + "");
            request.getSession().setAttribute("NEXTVALUE", (next+1) +"");
            return mapping.findForward("display");
        }
        else
        {
            print(this,"else block.");
            if (frm.getCtp() == 0) {
                Stack<String> blist = new Stack<String>();
                if (request.getSession().getAttribute("BACKURL") != null) {
                    blist = (Stack<String>) request.getSession().getAttribute("BACKURL");
                }
                blist.push(request.getRequestURI());
                request.getSession().setAttribute("BACKURL", blist);
            }
            ArrayList list = user.getUserListByName (search, uId, 0, count,clientIndex,permissionIndex);
            int cnt = 0;
            if(list.size() > 0)
            {
                UserInfo cinfo = (UserInfo) list.get(list.size() - 1);
                cnt = cinfo.getUserId();
                list.remove(list.size() - 1);
            }
            request.getSession().setAttribute("COUNT_LIST", cnt+"");
            request.getSession().setAttribute("USER_LIST", list);            
            request.getSession().setAttribute("NEXT", "0");
            request.getSession().setAttribute("NEXTVALUE", "1");
        }
        return mapping.findForward("display");
    }
}