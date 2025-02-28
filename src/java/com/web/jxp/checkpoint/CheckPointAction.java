package com.web.jxp.checkpoint;

import com.web.jxp.base.Validate;
import com.web.jxp.client.Client;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import static com.web.jxp.common.Common.*;
import com.web.jxp.user.UserInfo;
import java.util.Collection;
import java.util.Stack;

public class CheckPointAction extends Action {
    
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        CheckPointForm frm = (CheckPointForm) form;
        CheckPoint checkpoint = new CheckPoint();
        Client client = new Client();
        Validate vobj = new Validate();
        int count = checkpoint.getCount();
        String search = frm.getSearch() != null && !frm.getSearch().equals("") ? frm.getSearch() : "";
        frm.setSearch(search);
        int uId = 0;
        String permission = "N";
        if (request.getSession().getAttribute("LOGININFO") != null) {
            UserInfo uInfo = (UserInfo) request.getSession().getAttribute("LOGININFO");
            if (uInfo != null) {
                permission = uInfo.getPermission();
                uId = uInfo.getUserId();
            }
        }
        int check_user = checkpoint.checkUserSession(request, 48, permission);
        if (check_user == -1) {
            return mapping.findForward("default");
        } else if (check_user == -2) {
            String authmess = checkpoint.getMainPath("user_auth");
            authmess = authmess.replaceAll("__MODULE__", "Compliance Checkpoints");
            request.setAttribute("AUTHMESSAGE", authmess);
            return mapping.findForward("auth");
        }
        if (frm.getDoAdd() != null && frm.getDoAdd().equals("yes")) {
            
            frm.setDoAdd("no");
            print(this, " doAdd block :: ");
            frm.setCode(checkpoint.getCode());
            Collection clients = client.getClients();
            frm.setClient(clients);
            Collection clinetassets = checkpoint.getClientAsset(-1);
            frm.setClientasset(clinetassets);
            Collection positions = checkpoint.getPostions(-1);
            frm.setPosition(positions);
            Collection grades = checkpoint.getGrades(-1, "");
            frm.setGrade(grades);
            frm.setStatus(1);
            frm.setCheckpointId(-1);
            saveToken(request);
            return mapping.findForward("add_checkpoint");
            
        } else if (frm.getDoModify() != null && frm.getDoModify().equals("yes")) {
            frm.setDoModify("no");
            
            int checkpointId = frm.getCheckpointId();
            frm.setCheckpointId(checkpointId);
            
            Collection clients = client.getClients();
            frm.setClient(clients);
            
            CheckPointInfo info = checkpoint.getCheckPointDetailById(checkpointId);
            if (info != null) 
            {
                Collection clinetassets = checkpoint.getClientAsset(info.getClientId());
                frm.setClientasset(clinetassets);
                Collection positions = checkpoint.getPostions(info.getClientassetId());
                frm.setPosition(positions);
                Collection grades = checkpoint.getGrades(info.getClientassetId(), info.getPositionname());
                frm.setGrade(grades);
                frm.setClientId(info.getClientId());
                frm.setPositionname(info.getPositionname());
                frm.setClientassetId(info.getClientassetId());
                frm.setPositionId(info.getPositionId());
                frm.setGradeId(info.getGradeId());
                frm.setCode(info.getCode());
                frm.setName(info.getName());
                frm.setDisplaynote(info.getDesc());
                frm.setCheckFlag(info.getFlag());
                frm.setStatus(info.getStatus());
            }
            saveToken(request);
            return mapping.findForward("add_checkpoint");
        } else if (frm.getDoView() != null && frm.getDoView().equals("yes")) {
            
            frm.setDoView("no");
            int checkpointId = frm.getCheckpointId();
            frm.setCheckpointId(checkpointId);
            CheckPointInfo info = checkpoint.getCheckPointDetailByIdforDetail(checkpointId);
            request.setAttribute("CHECKPOINT_DETAIL", info);
            return mapping.findForward("view_checkpoint");
            
        } else if (frm.getDoSave() != null && frm.getDoSave().equals("yes")) {
            
            frm.setDoSave("no");
            print(this, "getDoSave block.");
            if (isTokenValid(request)) {
                resetToken(request);
                int checkpointId = frm.getCheckpointId();
                int clientId = frm.getClientId();
                int clientassetId = frm.getClientassetId();
                int positionId = frm.getPositionId();
                int gradeId = frm.getGradeId();
                int flag = frm.getCheckFlag();
                
                String name = vobj.replacename(frm.getName());
                String description = vobj.replacedesc(frm.getDisplaynote());
                String positionname = frm.getPositionname();
                String code = frm.getCode();
                
                int status = 1;
                String ipAddrStr = request.getRemoteAddr();
                String iplocal = checkpoint.getLocalIp();
                int ck = checkpoint.checkDuplicacy(checkpointId, clientId, clientassetId, positionId, gradeId, name);
                if (ck == 1) 
                {
                    saveToken(request);
                    request.setAttribute("MESSAGE", "Compliance Checkpoint already exists");
                    frm.setName(name);
                    frm.setCode(code);
                    Collection clients = client.getClients();
                    frm.setClient(clients);
                    Collection clinetassets = checkpoint.getClientAsset(frm.getClientId());
                    frm.setClientasset(clinetassets);
                    Collection positions = checkpoint.getPostions(frm.getClientassetId());
                    frm.setPosition(positions);
                    Collection grades = checkpoint.getGrades(frm.getClientassetId(), positionname);
                    frm.setGrade(grades);
                    frm.setPositionname(frm.getPositionname());
                    frm.setDisplaynote(description);
                    frm.setCheckFlag(flag);
                    return mapping.findForward("add_checkpoint");
                }
                CheckPointInfo info = new CheckPointInfo(checkpointId, name, clientId, clientassetId, positionId, gradeId, description, flag, status, uId);
                if (checkpointId <= 0) {
                    int cc = checkpoint.createCheckPoint(info);
                    frm.setCheckpointId(cc);
                    if (cc > 0) {
                        checkpoint.createHistoryAccess(null, uId, ipAddrStr, iplocal, "Data Added", 48, cc);
                        request.setAttribute("MESSAGE", "Data added successfully.");                        
                    }
                    CheckPointInfo infocc = checkpoint.getCheckPointDetailByIdforDetail(cc);
                    request.setAttribute("CHECKPOINT_DETAIL", infocc);
                    return mapping.findForward("view_checkpoint");
                    
                } else {
                    checkpoint.updateCheckPoint(info);
                    checkpoint.createHistoryAccess(null, uId, ipAddrStr, iplocal, "Data Updated", 48, checkpointId);
                    request.setAttribute("MESSAGE", "Data updated successfully.");
                    
                    frm.setCheckpointId(checkpointId);
                    CheckPointInfo infocc = checkpoint.getCheckPointDetailByIdforDetail(checkpointId);
                    request.setAttribute("CHECKPOINT_DETAIL", infocc);
                    return mapping.findForward("view_checkpoint");
                }
            }
        } else if (frm.getDoAddMore() != null && frm.getDoAddMore().equals("yes")) {
            
            frm.setDoAddMore("no");
            print(this, "getDoAddMore block.");
            if (isTokenValid(request)) {
                resetToken(request);
                int checkpointId = frm.getCheckpointId();
                int clientId = frm.getClientId();
                int clientassetId = frm.getClientassetId();
                int positionId = frm.getPositionId();
                int gradeId = frm.getGradeId();
                int flag = frm.getCheckFlag();
                
                String name = vobj.replacename(frm.getName());
                String description = vobj.replacedesc(frm.getDisplaynote());
                String positionname = frm.getPositionname();
                String code = frm.getCode();
                
                int status = 1;
                String ipAddrStr = request.getRemoteAddr();
                String iplocal = checkpoint.getLocalIp();
                int ck = checkpoint.checkDuplicacy(checkpointId, clientId, clientassetId, positionId, gradeId, name);
                if (ck == 1) 
                {
                    saveToken(request);
                    request.setAttribute("MESSAGE", "Compliance Checkpoint already exists");
                    frm.setName(name);
                    frm.setCode(code);
                    Collection clients = client.getClients();
                    frm.setClient(clients);
                    Collection clinetassets = checkpoint.getClientAsset(frm.getClientId());
                    frm.setClientasset(clinetassets);
                    Collection positions = checkpoint.getPostions(frm.getClientassetId());
                    frm.setPosition(positions);
                    Collection grades = checkpoint.getGrades(frm.getClientassetId(), positionname);
                    frm.setGrade(grades);
                    frm.setPositionname(frm.getPositionname());
                    frm.setDisplaynote(description);
                    frm.setCheckFlag(flag);
                    return mapping.findForward("add_checkpoint");
                }
                CheckPointInfo info = new CheckPointInfo(checkpointId, name, clientId, clientassetId, positionId, gradeId, description, flag, status, uId);
                if (checkpointId <= 0) {
                    int cc = checkpoint.createCheckPoint(info);
                    frm.setCheckpointId(cc);
                    if (cc > 0) 
                    {
                        checkpoint.createHistoryAccess(null, uId, ipAddrStr, iplocal, "Data Added", 48, cc);
                        request.setAttribute("MESSAGE", "Data added successfully.");                        
                    }
                    frm.setName("");
                    frm.setCode(checkpoint.getCode());
                    Collection clients = client.getClients();
                    frm.setClient(clients);
                    Collection clinetassets = checkpoint.getClientAsset(frm.getClientId());
                    frm.setClientasset(clinetassets);
                    Collection positions = checkpoint.getPostions(frm.getClientassetId());
                    frm.setPosition(positions);
                    Collection grades = checkpoint.getGrades(frm.getClientassetId(), positionname);
                    frm.setPositionname(frm.getPositionname());
                    frm.setDisplaynote("");
                    frm.setGrade(grades);
                    frm.setStatus(1);
                    frm.setCheckpointId(-1);
                    saveToken(request);
                    return mapping.findForward("add_checkpoint");
                }
            }
        } else if (frm.getDoCancel() != null && frm.getDoCancel().equals("yes")) {
            
            frm.setDoCancel("no");
            print(this, "doCancel block");
            int next = 0;
            if (request.getSession().getAttribute("NEXTVALUE") != null) {
                next = Integer.parseInt((String) request.getSession().getAttribute("NEXTVALUE"));
            }
            next = next - 1;
            if (next < 0) {
                next = 0;
            }
            ArrayList checkpointList = checkpoint.getCheckPointByName(search, next, count);
            int cnt = 0;
            if (checkpointList.size() > 0) {
                CheckPointInfo cinfo = (CheckPointInfo) checkpointList.get(checkpointList.size() - 1);
                cnt = cinfo.getCheckpointId();
                checkpointList.remove(checkpointList.size() - 1);
            }
            request.getSession().setAttribute("CHECKPOINT_LIST", checkpointList);
            request.getSession().setAttribute("COUNT_LIST", cnt + "");
            request.getSession().setAttribute("NEXT", next + "");
            request.getSession().setAttribute("NEXTVALUE", (next + 1) + "");
            return mapping.findForward("display");
        } else {
            print(this, "else block.");
            if (frm.getCtp() == 0) {
                Stack<String> blist = new Stack<String>();
                if (request.getSession().getAttribute("BACKURL") != null) {
                    blist = (Stack<String>) request.getSession().getAttribute("BACKURL");
                }
                blist.push(request.getRequestURI());
                request.getSession().setAttribute("BACKURL", blist);
            }
            ArrayList checkpointList = checkpoint.getCheckPointByName(search, 0, count);
            int cnt = 0;
            if (checkpointList.size() > 0) {
                CheckPointInfo cinfo = (CheckPointInfo) checkpointList.get(checkpointList.size() - 1);
                cnt = cinfo.getCheckpointId();
                checkpointList.remove(checkpointList.size() - 1);
            }
            request.getSession().setAttribute("CHECKPOINT_LIST", checkpointList);
            request.getSession().setAttribute("COUNT_LIST", cnt + "");
            request.getSession().setAttribute("NEXT", "0");
            request.getSession().setAttribute("NEXTVALUE", "1");
        }
        return mapping.findForward("display");
    }
}
