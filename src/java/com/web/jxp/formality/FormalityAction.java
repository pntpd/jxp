package com.web.jxp.formality;

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
import java.io.File;
import java.util.Collection;
import java.util.Stack;
import org.apache.struts.upload.FormFile;

public class FormalityAction extends Action {

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        FormalityForm frm = (FormalityForm) form;
        Formality formality = new Formality();
        Validate vobj = new Validate();
        int count = formality.getCount();
        String search = frm.getSearch() != null && !frm.getSearch().equals("") ? frm.getSearch() : "";
        frm.setSearch(search);
        int uId = 0, allclient = 0;
        String permission = "N", cids = "", assetids = "";
        if (request.getSession().getAttribute("LOGININFO") != null) {
            UserInfo uInfo = (UserInfo) request.getSession().getAttribute("LOGININFO");
            if (uInfo != null) {
                permission = uInfo.getPermission() != null ? uInfo.getPermission() : "";
                uId = uInfo.getUserId();
                cids = uInfo.getCids() != null ? uInfo.getCids() : "";
                allclient = uInfo.getAllclient();
                assetids = uInfo.getAssetids() != null ? uInfo.getAssetids() : "";
            }
        }
        int check_user = formality.checkUserSession(request, 55, permission);
        if (check_user == -1) {
            return mapping.findForward("default");
        } else if (check_user == -2) {
            String authmess = formality.getMainPath("user_auth");
            authmess = authmess.replaceAll("__MODULE__", "Onboarding Formality Module");
            request.setAttribute("AUTHMESSAGE", authmess);
            return mapping.findForward("auth");
        }
        Collection clients = formality.getClients(cids, allclient, permission);
        frm.setCandiateIdList(clients);
        if (frm.getDoAdd() != null && frm.getDoAdd().equals("yes")) {
            print(this, " doAdd block :: ");
            frm.setStatus(1);
            frm.setFormalityId(-1);
            frm.setTemptype(1);

            request.getSession().removeAttribute("WORKEXP_IDs");
            request.getSession().removeAttribute("EDUDETAIL_IDs");
            request.getSession().removeAttribute("DOCDETAIL_IDs");
            request.getSession().removeAttribute("TRAININGDETAIL_IDs");
            request.getSession().removeAttribute("LANGUAGEDETAIL_IDs");
            request.getSession().removeAttribute("VACCINEDETAIL_IDs");

            saveToken(request);
            return mapping.findForward("add_formality");
        } else if (frm.getDoModify() != null && frm.getDoModify().equals("yes")) {
            int formalityId = frm.getFormalityId();
            frm.setFormalityId(formalityId);
            FormalityInfo info = formality.getFormalityDetailById(formalityId);
            if (info != null) {
                frm.setName(info.getName());
                frm.setStatus(info.getStatus());

                Collection candidate = formality.getClients(cids, allclient, permission);
                frm.setCandiateIdList(candidate);
                frm.setCandidateId(info.getCandidateId());

                frm.setDescription(info.getDescription());
                frm.setDescription2(info.getDescription2());
                frm.setDescription3(info.getDescription3());
                frm.setTemptype(info.getTemptype());

                request.getSession().setAttribute("WORKEXP_IDs", info.getExpColumn());
                request.getSession().setAttribute("EDUDETAIL_IDs", info.getEduColumn());
                request.getSession().setAttribute("DOCDETAIL_IDs", info.getDocColumn());
                request.getSession().setAttribute("TRAININGDETAIL_IDs", info.getTrainingColumn());
                request.getSession().setAttribute("LANGUAGEDETAIL_IDs", info.getLanguageColumn());
                request.getSession().setAttribute("VACCINEDETAIL_IDs", info.getVaccineColumn());

                if (info.getFormalityfilename() != null) {
                    frm.setFormalityfilehidden(info.getFormalityfilename());
                    String add_path = formality.getMainPath("add_formality_file");
                    frm.setFormalityfilehidden(info.getFormalityfilename());
                    frm.setDescription(formality.readHTMLFile(info.getDescription(), add_path));
                }
                if (info.getDescription() != null && !info.getDescription().equals("")) {
                    String add_path = formality.getMainPath("add_formality_file");
                    frm.setDeschidden(info.getDescription());
                    frm.setDescription(formality.readHTMLFile(info.getDescription(), add_path));
                }
                if (info.getFilename()!= null) {
                    frm.setFormalityfilehidden2(info.getFilename());
                }
                request.getSession().setAttribute("FILENAME", info.getFilename());
            }
            saveToken(request);
            return mapping.findForward("add_formality");
        } else if (frm.getDoView() != null && frm.getDoView().equals("yes")) 
        {
            int formalityId = frm.getFormalityId();
            frm.setFormalityId(formalityId);
            FormalityInfo info = formality.getFormalityDetailByIdforDetail(formalityId);
            request.setAttribute("FORMALITY_DETAIL", info);
            return mapping.findForward("view_formality");
        }
        else if (frm.getDoDeleteFile()!= null && frm.getDoDeleteFile().equals("yes")) 
        {
            print(this, "getDoDeleteFile block.");
            frm.setDoDeleteFile("no");            
            int formalityId = frm.getFormalityId();
            frm.setFormalityId(formalityId);
            int cc = formality.deleteFile(formalityId, uId);
            if(cc > 0)
            {
                FormalityInfo info = formality.getFormalityDetailByIdforDetail(formalityId);
                request.setAttribute("FORMALITY_DETAIL", info);
                return mapping.findForward("view_formality");
            }
        }
        else if (frm.getDoSave() != null && frm.getDoSave().equals("yes")) {
            print(this, "getDoSave block.");
            if (isTokenValid(request)) {
                resetToken(request);
                int formalityId = frm.getFormalityId();
                String name = vobj.replacename(frm.getName());
                int status = 1;
                int candidateId = frm.getCandidateId();
                String deschidden = frm.getDeschidden();
                String description = frm.getDescription();
                String description2 = frm.getDescription2();
                String description3 = frm.getDescription3();

                String formalityfilehidden = frm.getFormalityfilehidden();
                FormFile formalityfile = frm.getFormalityfile();
                
                String formalityfilehidden2 = frm.getFormalityfilehidden2();
                FormFile formalityfile2 = frm.getFormalityfile2();

                int clientId = frm.getCandidateId();
                clients = formality.getClients(cids, allclient, permission);
                frm.setCandiateIdList(clients);
                int temptype = frm.getTemptype();
                String ipAddrStr = request.getRemoteAddr();
                String iplocal = formality.getLocalIp();
                String[] expcolumn = frm.getExpColumn();
                String[] educolumn = frm.getEduColumn();
                String[] doccolumn = frm.getDocColumn();
                String[] trainingcolumn = frm.getTrainingColumn();
                String[] languagecolumn = frm.getLanguageColumn();
                String[] vaccinecolumn = frm.getVaccineColumn();

                String wexpcolumn = vobj.replacename(makeCommaDelimString(expcolumn));
                String candeducolumn = vobj.replacename(makeCommaDelimString(educolumn));
                String canddoccolumn = vobj.replacename(makeCommaDelimString(doccolumn));
                String ctrainingcolumn = vobj.replacename(makeCommaDelimString(trainingcolumn));
                String clangcolumn = vobj.replacename(makeCommaDelimString(languagecolumn));
                String cvaccinecolumn = vobj.replacename(makeCommaDelimString(vaccinecolumn));

                int ck = formality.checkDuplicacy(formalityId, name, clientId);
                if (ck == 1) {
                    saveToken(request);
                    request.setAttribute("MESSAGE", "Onboarding Formality already exists");
                    return mapping.findForward("add_formality");
                }
                String add_path = formality.getMainPath("add_formality_file");
                java.util.Date now = new java.util.Date();
                String fname = String.valueOf(now.getTime());
                String htmlFolderName = formality.dateFolder();
                if (formalityId > 0) 
                {
                    if (deschidden != null && !deschidden.equals("")) {
                        File f = new File(add_path + deschidden);
                        if (f.exists()) {
                            f.delete();
                        }
                    }
                    if (temptype == 1 && description != null && !description.equals("")) {
                        description = formality.writeHTMLFile(description, add_path + htmlFolderName, fname + ".html");
                        description = htmlFolderName + "/" + description;
                    }
                }
                else 
                {
                    if (temptype == 1) {
                        if (description != null && !description.equals("")) {
                            description = formality.writeHTMLFile(description, add_path + htmlFolderName, fname + ".html");
                            description = htmlFolderName + "/" + description;
                        }
                    }
                }

                request.getSession().removeAttribute("WORKEXP_IDs");
                request.getSession().removeAttribute("EDUDETAIL_IDs");
                request.getSession().removeAttribute("DOCDETAIL_IDs");
                request.getSession().removeAttribute("TRAININGDETAIL_IDs");
                request.getSession().removeAttribute("LANGUAGEDETAIL_IDs");
                request.getSession().removeAttribute("VACCINEDETAIL_IDs");

                if (temptype == 2) 
                {
                    description = "";
                    description2 = "";
                    description3 = "";
                }
                String fileName = "", fileName2 = "", folderName = "";
                if (formalityfile != null && formalityfile.getFileSize() > 0 && temptype == 2) {
                    folderName  = formality.createFolder(add_path);
                    String fnval = formality.uploadFile(formalityId, formalityfilehidden, formalityfile, fname, add_path, folderName);
                    fileName = fnval;
                }
                if (formalityfile2 != null && formalityfile2.getFileSize() > 0) 
                {
                    folderName  = formality.createFolder(add_path);
                    String fnval = formality.uploadFile(formalityId, formalityfilehidden2, formalityfile2, fname+"_1", add_path, folderName);
                    fileName2 = fnval;
                }
                FormalityInfo info = new FormalityInfo(formalityId, name, status, uId, candidateId, description, fileName,
                        temptype, wexpcolumn, candeducolumn, canddoccolumn, ctrainingcolumn, clangcolumn, cvaccinecolumn,
                        description2, description3, fileName2);
                if (formalityId <= 0) {
                    int cc = formality.createFormality(info);
                    if (cc > 0) {
                        formality.createHistoryAccess(null, uId, ipAddrStr, iplocal, "Data Added", 55, cc);
                        request.setAttribute("MESSAGE", "Data added successfully.");
                    }
                    ArrayList formalityList = formality.getFormalityByName(search, 0, count, allclient, permission, cids, assetids);
                    int cnt = 0;
                    if (formalityList.size() > 0) {
                        FormalityInfo cinfo = (FormalityInfo) formalityList.get(formalityList.size() - 1);
                        cnt = cinfo.getFormalityId();
                        formalityList.remove(formalityList.size() - 1);
                    }
                    request.getSession().setAttribute("FORMALITY_LIST", formalityList);
                    request.getSession().setAttribute("COUNT_LIST", cnt + "");
                    request.getSession().setAttribute("NEXT", "0");
                    request.getSession().setAttribute("NEXTVALUE", "1");

                    return mapping.findForward("display");
                } else {
                    formality.updateFormality(info);
                    formality.createHistoryAccess(null, uId, ipAddrStr, iplocal, "Data Updated", 55, formalityId);
                    request.setAttribute("MESSAGE", "Data updated successfully.");
                    int next = 0;
                    if (request.getSession().getAttribute("NEXTVALUE") != null) {
                        next = Integer.parseInt((String) request.getSession().getAttribute("NEXTVALUE"));
                    }
                    next = next - 1;
                    if (next < 0) {
                        next = 0;
                    }
                    ArrayList formalityList = formality.getFormalityByName(search, next, count, allclient, permission, cids, assetids);
                    int cnt = 0;
                    if (formalityList.size() > 0) {
                        FormalityInfo cinfo = (FormalityInfo) formalityList.get(formalityList.size() - 1);
                        cnt = cinfo.getFormalityId();
                        formalityList.remove(formalityList.size() - 1);
                    }
                    request.getSession().setAttribute("FORMALITY_LIST", formalityList);
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
            ArrayList formalityList = formality.getFormalityByName(search, next, count, allclient, permission, cids, assetids);
            int cnt = 0;
            if (formalityList.size() > 0) {
                FormalityInfo cinfo = (FormalityInfo) formalityList.get(formalityList.size() - 1);
                cnt = cinfo.getFormalityId();
                formalityList.remove(formalityList.size() - 1);
            }
            request.getSession().setAttribute("FORMALITY_LIST", formalityList);
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

            ArrayList formalityList = formality.getFormalityByName(search, 0, count, allclient, permission, cids, assetids);
            int cnt = 0;
            if (formalityList.size() > 0) {
                FormalityInfo cinfo = (FormalityInfo) formalityList.get(formalityList.size() - 1);
                cnt = cinfo.getFormalityId();
                formalityList.remove(formalityList.size() - 1);
            }
            request.getSession().setAttribute("FORMALITY_LIST", formalityList);
            request.getSession().setAttribute("COUNT_LIST", cnt + "");
            request.getSession().setAttribute("NEXT", "0");
            request.getSession().setAttribute("NEXTVALUE", "1");
        }
        return mapping.findForward("display");
    }
}
