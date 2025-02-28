package com.web.jxp.training;

import com.web.jxp.base.Validate;
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

public class TrainingAction extends Action 
{
    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        TrainingForm frm = (TrainingForm) form;
        Training training = new Training();
        Validate vobj = new Validate();
        int count = training.getCount();
        String search = frm.getSearch() != null && !frm.getSearch().equals("") ? frm.getSearch() : "";
        frm.setSearch(search);
        int uId = 0, allclient = 0;
        String permission = "N", cids = "", assetids = "";
        if (request.getSession().getAttribute("LOGININFO") != null) 
        {
            UserInfo uInfo = (UserInfo) request.getSession().getAttribute("LOGININFO");
            if (uInfo != null) 
            {
                permission = uInfo.getPermission();
                uId = uInfo.getUserId();
                cids = uInfo.getCids();
                allclient = uInfo.getAllclient();
                assetids = uInfo.getAssetids();
            }
        }
        int check_user = training.checkUserSession(request, 62, permission);
        if (check_user == -1)
            return mapping.findForward("default");
        else if (check_user == -2) 
        {
            String authmess = training.getMainPath("user_auth");
            authmess = authmess.replaceAll("__MODULE__", "Training Module");
            request.setAttribute("AUTHMESSAGE", authmess);
            return mapping.findForward("auth");
        }
        Collection clients = training.getClients(cids, allclient, permission);
        frm.setClients(clients);
        int clientIndex = frm.getClientIndex();
        frm.setClientIndex(clientIndex);

        Collection assets = training.getClientAsset(clientIndex, assetids, allclient, permission);
        frm.setAssets(assets);
        int assetIndex = frm.getAssetIndex();
        frm.setAssetIndex(assetIndex);
        if (frm.getDoAdd() != null && frm.getDoAdd().equals("yes"))
        {
            print(this, " doAdd block :: ");
            frm.setStatus(1);
            frm.setTrainingId(-1);
            int clientId = frm.getClientId();
            clients = training.getClientsForAddEdit(cids, allclient, permission);
            frm.setClients(clients);
            assets = training.getClientAssetForAddEdit(clientId, assetids, allclient, permission);
            frm.setAssets(assets);
            saveToken(request);
            return mapping.findForward("add_training");
        }
        else if(frm.getDoModify() != null && frm.getDoModify().equals("yes"))
        {
            int trainingId = frm.getTrainingId();
            frm.setTrainingId(trainingId);
            TrainingInfo info = training.getTrainingDetailById(trainingId);
            if(info != null)
            {                
                frm.setName(info.getName());
                frm.setStatus(info.getStatus());
                frm.setClientId(info.getClientId());
                frm.setAssetId(info.getAssetId());
                
                clients = training.getClientsForAddEdit(cids, allclient, permission);
                frm.setClients(clients);
                clientIndex = frm.getClientIndex();
                frm.setClientIndex(clientIndex);
                assets = training.getClientAssetForAddEdit(info.getClientId(), assetids, allclient, permission);
                frm.setAssets(assets);
            }
            saveToken(request);
            return mapping.findForward("add_training");
        }
        else if(frm.getDoView() != null && frm.getDoView().equals("yes"))
        {
            int trainingId = frm.getTrainingId();
            frm.setTrainingId(trainingId);
            TrainingInfo info = training.getTrainingDetailByIdforDetail(trainingId);
            request.setAttribute("TRAINING_DETAIL", info);
            return mapping.findForward("view_training");
        }        
        else if(frm.getDoSave() != null && frm.getDoSave().equals("yes"))
        {
            print(this,"getDoSave block.");
            if(isTokenValid(request))
            {
                resetToken(request);
                int trainingId = frm.getTrainingId();
                String name = vobj.replacedesc(frm.getName());
                int status = 1;
                int clientId = frm.getClientId();
                int assetId = frm.getAssetId();
                
                String ipAddrStr = request.getRemoteAddr();
                String iplocal = training.getLocalIp();
                int ck = training.checkDuplicacy(trainingId, clientId, assetId, name);
                if(ck == 1)
                {
                    saveToken(request);
                    request.setAttribute("MESSAGE", "Training Type already exists");
                    return mapping.findForward("add_training");
                }
                TrainingInfo info = new TrainingInfo(trainingId, name, status, uId, clientId, assetId);
                if(trainingId <= 0)
                {
                    int cc = training.createTraining(info);
                    if(cc > 0)
                    {
                       training.createHistoryAccess(null, uId, ipAddrStr, iplocal, "Data Added", 62, cc);
                       request.setAttribute("MESSAGE", "Data added successfully.");
                      
                    }
                    ArrayList trainingList = training.getTrainingByName(search, 0, count,clientIndex, assetIndex, allclient, permission, cids, assetids);
                    int cnt = 0;
                    if(trainingList.size() > 0)
                    {
                        TrainingInfo cinfo = (TrainingInfo) trainingList.get(trainingList.size() - 1);
                        cnt = cinfo.getTrainingId();
                        trainingList.remove(trainingList.size() - 1);
                    }
                    request.getSession().setAttribute("TRAINING_LIST", trainingList);
                    request.getSession().setAttribute("COUNT_LIST", cnt+"");
                    request.getSession().setAttribute("NEXT", "0");
                    request.getSession().setAttribute("NEXTVALUE", "1");
                    
                    return mapping.findForward("display");
                }
                else
                {
                    training.updateTraining(info);
                    training.createHistoryAccess(null, uId, ipAddrStr, iplocal, "Data Updated", 62, trainingId);
                    request.setAttribute("MESSAGE", "Data updated successfully.");
                    int next = 0;
                    if(request.getSession().getAttribute("NEXTVALUE") != null)
                    {
                        next = Integer.parseInt((String) request.getSession().getAttribute("NEXTVALUE"));
                    }
                    next = next - 1;
                    if(next < 0)
                        next = 0;
                    ArrayList trainingList = training.getTrainingByName(search, next, count,clientIndex, assetIndex, allclient, permission, cids, assetids);
                    int cnt = 0;
                    if(trainingList.size() > 0)
                    {
                        TrainingInfo cinfo = (TrainingInfo) trainingList.get(trainingList.size() - 1);
                        cnt = cinfo.getTrainingId();
                        trainingList.remove(trainingList.size() - 1);
                    }
                    request.getSession().setAttribute("TRAINING_LIST", trainingList);
                    request.getSession().setAttribute("COUNT_LIST", cnt+"");
                    request.getSession().setAttribute("NEXT", next+"");
                    request.getSession().setAttribute("NEXTVALUE", (next+1)+"");
                    
                    return mapping.findForward("display");
                }                
            }
        }
        else if(frm.getDoCancel() != null && frm.getDoCancel().equals("yes"))
        {
            print(this,"doCancel block");
            int next = 0;
            if(request.getSession().getAttribute("NEXTVALUE") != null)
            {
                next = Integer.parseInt((String) request.getSession().getAttribute("NEXTVALUE"));
            }
            next = next - 1;
            if(next < 0)
                next = 0;
            ArrayList trainingList = training.getTrainingByName(search, next, count,clientIndex, assetIndex, allclient, permission, cids, assetids);
            int cnt = 0;
            if(trainingList.size() > 0)
            {
                TrainingInfo cinfo = (TrainingInfo) trainingList.get(trainingList.size() - 1);
                cnt = cinfo.getTrainingId();
                trainingList.remove(trainingList.size() - 1);
            }
            request.getSession().setAttribute("TRAINING_LIST", trainingList);
            request.getSession().setAttribute("COUNT_LIST", cnt+"");
            request.getSession().setAttribute("NEXT", next + "");
            request.getSession().setAttribute("NEXTVALUE", (next+1) +"");
            return mapping.findForward("display");
        }
        else
        {
            print(this, "else block.");
            
            if (frm.getCtp() == 0) {
                Stack<String> blist = new Stack<String>();
                if (request.getSession().getAttribute("BACKURL") != null) {
                    blist = (Stack<String>) request.getSession().getAttribute("BACKURL");
                }
                blist.push(request.getRequestURI());
                request.getSession().setAttribute("BACKURL", blist);
            }
            
            ArrayList trainingList = training.getTrainingByName(search, 0, count, clientIndex, assetIndex, allclient, permission, cids, assetids);
            int cnt = 0;
            if(trainingList.size() > 0)
            {
                TrainingInfo cinfo = (TrainingInfo) trainingList.get(trainingList.size() - 1);
                cnt = cinfo.getTrainingId();
                trainingList.remove(trainingList.size() - 1);
            }
            request.getSession().setAttribute("TRAINING_LIST", trainingList);
            request.getSession().setAttribute("COUNT_LIST", cnt+"");
            request.getSession().setAttribute("NEXT", "0");
            request.getSession().setAttribute("NEXTVALUE", "1");
        }
        return mapping.findForward("display");
    }    
}