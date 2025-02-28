package com.web.jxp.contract;

import com.web.jxp.base.Validate;
import static com.web.jxp.common.Common.*;
import com.web.jxp.user.UserInfo;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Stack;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

public class ContractAction extends Action {

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        ContractForm frm = (ContractForm) form;
        Contract contract = new Contract();
        Validate vobj = new Validate();
        int count = contract.getCount();
        String search = frm.getSearch() != null && !frm.getSearch().equals("") ? frm.getSearch() : "";
        frm.setSearch(search);
        int uId = 0, allclient = 0;
        String permission = "N", cids = "", assetids = "";
        if (request.getSession().getAttribute("LOGININFO") != null) 
        {
            UserInfo uInfo = (UserInfo) request.getSession().getAttribute("LOGININFO");
            if (uInfo != null) 
            {
                permission = uInfo.getPermission() != null ? uInfo.getPermission(): "";
                uId = uInfo.getUserId();
                cids = uInfo.getCids() != null ? uInfo.getCids(): "";
                allclient = uInfo.getAllclient();
                assetids = uInfo.getAssetids() != null ? uInfo.getAssetids(): "";
            }
        }
        int check_user = contract.checkUserSession(request, 55, permission);
        if (check_user == -1) {
            return mapping.findForward("default");
        } else if (check_user == -2) {
            String authmess = contract.getMainPath("user_auth");
            authmess = authmess.replaceAll("__MODULE__", "Contract Module");
            request.setAttribute("AUTHMESSAGE", authmess);
            return mapping.findForward("auth");
        }
            Collection clients = contract.getClients(cids, allclient, permission);
            frm.setClients(clients);
        if (frm.getDoAdd() != null && frm.getDoAdd().equals("yes")) {
            print(this, " doAdd block :: ");
            frm.setStatus(1);
            frm.setContractId(-1);
            int clientId = frm.getClientId();
            int assetId = frm.getAssetId();
            frm.setType(1);
            frm.setClientId(clientId);            
            Collection contracts = contract.getContractList(-1);
            frm.setContracts(contracts);
            clients = contract.getClients(cids, allclient, permission);
            frm.setClients(clients);
            Collection assets = contract.getClientAsset(clientId, assetids, allclient, permission);
            frm.setAssets(assets);
            frm.setAssetId(assetId);
            
            request.getSession().removeAttribute("WORKEXP_IDs");
            request.getSession().removeAttribute("EDUDETAIL_IDs");
            request.getSession().removeAttribute("DOCDETAIL_IDs");
            request.getSession().removeAttribute("TRAININGDETAIL_IDs");
            request.getSession().removeAttribute("LANGUAGEDETAIL_IDs");
            request.getSession().removeAttribute("VACCINEDETAIL_IDs");
            request.getSession().removeAttribute("NOMINEEDETAIL_IDs");
            
            saveToken(request);
            return mapping.findForward("add_contract");
        } else if (frm.getDoModify() != null && frm.getDoModify().equals("yes")) 
        {
            int contractId = frm.getContractId();
            frm.setContractId(contractId);
            ContractInfo info = contract.getContractDetailById(contractId);
            if (info != null) 
            {
                frm.setName(info.getName());
                frm.setStatus(info.getStatus());  
                frm.setDescription(info.getDescription());
                frm.setDescription2(info.getDescription2());
                frm.setDescription3(info.getDescription3());
                frm.setType(info.getType());
                frm.setRefId(info.getRefId()); 
                frm.setClientId(info.getClientId());        
                frm.setAssetId(info.getAssetId());                
                clients = contract.getClients(cids, allclient, permission);
                frm.setClients(clients);
                Collection assets = contract.getClientAsset(info.getClientId(), assetids, allclient, permission);
                frm.setAssets(assets);
                Collection contracts = contract.getContractList(info.getAssetId());
                frm.setContracts(contracts);
                if(info.getDescription() != null && !info.getDescription().equals(""))
                {
                    String add_path = contract.getMainPath("add_contractfile");
                    frm.setDeschidden(info.getDescription());
                    frm.setDescription(contract.readHTMLFile(info.getDescription(), add_path));
                }
                if (info.getFilename()!= null) {
                    frm.setContractfilehidden(info.getFilename());
                }
                request.getSession().setAttribute("FILENAME", info.getFilename());
                request.getSession().setAttribute("WORKEXP_IDs", info.getExpColumn());
                request.getSession().setAttribute("EDUDETAIL_IDs", info.getEduColumn());
                request.getSession().setAttribute("DOCDETAIL_IDs", info.getDocColumn());
                request.getSession().setAttribute("TRAININGDETAIL_IDs", info.getTrainingColumn());
                request.getSession().setAttribute("LANGUAGEDETAIL_IDs", info.getLanguageColumn());
                request.getSession().setAttribute("VACCINEDETAIL_IDs", info.getVaccineColumn());
                request.getSession().setAttribute("NOMINEEDETAIL_IDs", info.getNomineeColumn());
            }
            saveToken(request);
            return mapping.findForward("add_contract");
        } else if (frm.getDoView() != null && frm.getDoView().equals("yes")) {
            int contractId = frm.getContractId();
            frm.setContractId(contractId);
            ContractInfo info = contract.getContractDetailByIdforDetail(contractId);
            request.setAttribute("CONTRACT_DETAIL", info);
            return mapping.findForward("view_contract");
        }
        else if (frm.getDoDeleteFile()!= null && frm.getDoDeleteFile().equals("yes")) 
        {
            print(this, "getDoDeleteFile block.");
            frm.setDoDeleteFile("no");            
            int contractId = frm.getContractId();
            frm.setContractId(contractId);
            int cc = contract.deleteFile(contractId, uId);
            if(cc > 0)
            {
                ContractInfo info = contract.getContractDetailByIdforDetail(contractId);
                request.setAttribute("CONTRACT_DETAIL", info);
                return mapping.findForward("view_contract");
            }
        }
        else if (frm.getDoSave() != null && frm.getDoSave().equals("yes")) {
            print(this, "getDoSave block.");
            if (isTokenValid(request)) {
                resetToken(request);
                int contractId = frm.getContractId();
                String name = vobj.replacename(frm.getName());
                int status = 1;
                int clientId = frm.getClientId();
                String deschidden = frm.getDeschidden();
                String description = frm.getDescription();
                clientId = frm.getClientId();
                clients = contract.getClients(cids, allclient, permission);
                frm.setClients(clients);
                int assetId = frm.getAssetId();
                Collection assets = contract.getClientAsset(clientId, assetids, allclient, permission);
                frm.setAssets(assets);
                frm.setAssetId(assetId);
                
                int type = frm.getType();
                int refId = frm.getRefId();
                String description2 = frm.getDescription2();
                String description3 = frm.getDescription3();
                String ipAddrStr = request.getRemoteAddr();
                String iplocal = contract.getLocalIp();
                String[] expcolumn = frm.getExpColumn();
                String[] educolumn = frm.getEduColumn();
                String[] doccolumn = frm.getDocColumn();
                String[] trainingcolumn = frm.getTrainingColumn();
                String[] languagecolumn = frm.getLanguageColumn();
                String[] vaccinecolumn = frm.getVaccineColumn();
                String[] nomineecolumn = frm.getNomineeColumn();
                
                String wexpcolumn = vobj.replacename(makeCommaDelimString(expcolumn));
                String candeducolumn = vobj.replacename(makeCommaDelimString(educolumn));
                String canddoccolumn = vobj.replacename(makeCommaDelimString(doccolumn));
                String ctrainingcolumn = vobj.replacename(makeCommaDelimString(trainingcolumn));
                String clangcolumn = vobj.replacename(makeCommaDelimString(languagecolumn));
                String cvaccinecolumn = vobj.replacename(makeCommaDelimString(vaccinecolumn));
                String nomcolumn = vobj.replacename(makeCommaDelimString(nomineecolumn));
                
                int ck = contract.checkDuplicacy(contractId, name, clientId, assetId);
                if (ck == 1) 
                {
                    saveToken(request);
                    request.setAttribute("MESSAGE", "Contract already exists");
                    return mapping.findForward("add_contract");
                }
                String add_path = contract.getMainPath("add_contractfile");
                java.util.Date now = new java.util.Date();
                String fname = String.valueOf(now.getTime());
                String htmlFolderName = contract.dateFolder(); 
                
                String fileName1 = "";
                FormFile filename = frm.getContractfile();
                if(filename != null && filename.getFileSize() > 0) {
                    fileName1 = contract.uploadFile(contractId, frm.getContractfilehidden(), filename, fname + "_1", add_path, htmlFolderName);
                }
                
                if(contractId > 0)
                {
                    if(description != null && !description.equals(""))
                    {
                        if(deschidden != null && !deschidden.equals(""))
                        {
                            File f = new File(add_path + deschidden);
                            if(f.exists())
                                f.delete();
                        }
                        description = contract.writeHTMLFile(description, add_path+htmlFolderName, fname+".html");
                        description = htmlFolderName+"/"+description;
                    }
                }
                else
                {
                    if(description != null && !description.equals(""))
                    {
                        description = contract.writeHTMLFile(description, add_path+htmlFolderName, fname+".html");
                        description = htmlFolderName+"/"+description;
                    }
                }
                
                request.getSession().removeAttribute("WORKEXP_IDs");
                request.getSession().removeAttribute("EDUDETAIL_IDs");
                request.getSession().removeAttribute("DOCDETAIL_IDs");
                request.getSession().removeAttribute("TRAININGDETAIL_IDs");
                request.getSession().removeAttribute("LANGUAGEDETAIL_IDs");
                request.getSession().removeAttribute("VACCINEDETAIL_IDs");
                request.getSession().removeAttribute("NOMINEEDETAIL_IDs");
                
                ContractInfo info = new ContractInfo(contractId, name, status, uId, clientId, description,assetId, 
                        type, refId, wexpcolumn, candeducolumn, canddoccolumn, ctrainingcolumn, clangcolumn, 
                        cvaccinecolumn, description2, description3, fileName1, nomcolumn);
                if (contractId <= 0) {
                    int cc = contract.createContract(info);
                    if (cc > 0) {
                        contract.createHistoryAccess(null, uId, ipAddrStr, iplocal, "Data Added", 98, cc);
                        request.setAttribute("MESSAGE", "Data added successfully.");

                    }
                    ArrayList contractList = contract.getContractByName(search, 0, count,allclient, permission, cids, assetids);
                    int cnt = 0;
                    if (contractList.size() > 0) {
                        ContractInfo cinfo = (ContractInfo) contractList.get(contractList.size() - 1);
                        cnt = cinfo.getContractId();
                        contractList.remove(contractList.size() - 1);
                    }
                    request.getSession().setAttribute("CONTRACT_LIST", contractList);
                    request.getSession().setAttribute("COUNT_LIST", cnt + "");
                    request.getSession().setAttribute("NEXT", "0");
                    request.getSession().setAttribute("NEXTVALUE", "1");

                    return mapping.findForward("display");
                } else {
                    contract.updateContract(info);
                    contract.createHistoryAccess(null, uId, ipAddrStr, iplocal, "Data Updated", 98, contractId);
                    request.setAttribute("MESSAGE", "Data updated successfully.");
                    int next = 0;
                    if (request.getSession().getAttribute("NEXTVALUE") != null) {
                        next = Integer.parseInt((String) request.getSession().getAttribute("NEXTVALUE"));
                    }
                    next = next - 1;
                    if (next < 0) {
                        next = 0;
                    }
                    ArrayList contractList = contract.getContractByName(search, next, count,allclient, permission, cids, assetids);
                    int cnt = 0;
                    if (contractList.size() > 0) {
                        ContractInfo cinfo = (ContractInfo) contractList.get(contractList.size() - 1);
                        cnt = cinfo.getContractId();
                        contractList.remove(contractList.size() - 1);
                    }
                    request.getSession().setAttribute("CONTRACT_LIST", contractList);
                    request.getSession().setAttribute("COUNT_LIST", cnt + "");
                    request.getSession().setAttribute("NEXT", next + "");
                    request.getSession().setAttribute("NEXTVALUE", (next + 1) + "");

                    return mapping.findForward("display");
                }
            }
        } else if (frm.getDoCancel() != null && frm.getDoCancel().equals("yes")) {
            print(this, "doCancel block");
            int next = 0;
            if (request.getSession().getAttribute("NEXTVALUE") != null) {
                next = Integer.parseInt((String) request.getSession().getAttribute("NEXTVALUE"));
            }
            next = next - 1;
            if (next < 0) {
                next = 0;
            }
            ArrayList contractList = contract.getContractByName(search, next, count,allclient, permission, cids, assetids);
            int cnt = 0;
            if (contractList.size() > 0) {
                ContractInfo cinfo = (ContractInfo) contractList.get(contractList.size() - 1);
                cnt = cinfo.getContractId();
                contractList.remove(contractList.size() - 1);
            }
            request.getSession().setAttribute("CONTRACT_LIST", contractList);
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

            ArrayList contractList = contract.getContractByName(search, 0, count,allclient, permission, cids, assetids);
            int cnt = 0;
            if (contractList.size() > 0) {
                ContractInfo cinfo = (ContractInfo) contractList.get(contractList.size() - 1);
                cnt = cinfo.getContractId();
                contractList.remove(contractList.size() - 1);
            }
            request.getSession().setAttribute("CONTRACT_LIST", contractList);
            request.getSession().setAttribute("COUNT_LIST", cnt + "");
            request.getSession().setAttribute("NEXT", "0");
            request.getSession().setAttribute("NEXTVALUE", "1");
        }
        return mapping.findForward("display");
    }
}
