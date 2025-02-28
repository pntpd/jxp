package com.web.jxp.resumetemplate;

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

public class ResumetemplateAction extends Action {

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        ResumetemplateForm frm = (ResumetemplateForm) form;
        Resumetemplate resumetemplate = new Resumetemplate();
        Validate vobj = new Validate();
        int count = resumetemplate.getCount();
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
        int check_user = resumetemplate.checkUserSession(request, 50, permission);
        if (check_user == -1) {
            return mapping.findForward("default");
        } else if (check_user == -2) {
            String authmess = resumetemplate.getMainPath("user_auth");
            authmess = authmess.replaceAll("__MODULE__", "Resume Template Module");
            request.setAttribute("AUTHMESSAGE", authmess);
            return mapping.findForward("auth");
        }            
        if (frm.getDoAdd() != null && frm.getDoAdd().equals("yes")) {
            print(this, " doAdd block :: ");
            frm.setStatus(1);
            frm.setResumetemplateId(-1);
            request.getSession().removeAttribute("WORKEXP_IDs");
            request.getSession().removeAttribute("EDUDETAIL_IDs");
            request.getSession().removeAttribute("DOCDETAIL_IDs");
            request.getSession().removeAttribute("TRAININGDETAIL_IDs");
            Collection clients = resumetemplate.getClients(cids, allclient, permission);
            frm.setClients(clients);
            saveToken(request);
            return mapping.findForward("add_resumetemplate");
        } else if (frm.getDoModify() != null && frm.getDoModify().equals("yes")) {
            int resumetemplateId = frm.getResumetemplateId();
            frm.setResumetemplateId(resumetemplateId);
            Collection clients = resumetemplate.getClients(cids, allclient, permission);
            frm.setClients(clients);
            ResumetemplateInfo info = resumetemplate.getResumetemplateDetailById(resumetemplateId);
            if (info != null)
            {
                frm.setName(info.getName());
                frm.setStatus(info.getStatus());                
                frm.setClientId(info.getClientId());
                frm.setDescription(info.getDescription());
                frm.setDescription2(info.getDescription2());
                frm.setDescription3(info.getDescription3());
                frm.setTemptype(info.getTemptype());
                frm.setExptype(info.getExptype());
                frm.setEdutype(info.getEdutype());
                frm.setDoctype(info.getDoctype());
                frm.setCerttype(info.getCerttype());
                frm.setLabel1(info.getLabel1());
                frm.setLabel2(info.getLabel2());
                frm.setLabel3(info.getLabel3());
                frm.setLabel4(info.getLabel4());
                frm.setLabel5(info.getLabel5());
                frm.setLabel6(info.getLabel6());
                frm.setLabel7(info.getLabel7());
                frm.setLabel8(info.getLabel8());
                frm.setLabel9(info.getLabel9());
                frm.setLabel10(info.getLabel10());
                frm.setLabel11(info.getLabel11());
                frm.setLabel12(info.getLabel12());
                frm.setLabel13(info.getLabel13());
                frm.setLabel14(info.getLabel14());
                frm.setLabel15(info.getLabel15());
                frm.setLabel16(info.getLabel16());
                frm.setLabel17(info.getLabel17());
                frm.setLabel18(info.getLabel18());
                frm.setLabel19(info.getLabel19());
                frm.setLabel20(info.getLabel20());
                frm.setLabel21(info.getLabel21());
                frm.setLabel22(info.getLabel22());
                frm.setLabel23(info.getLabel23());
                frm.setLabel24(info.getLabel24());
                frm.setLabel25(info.getLabel25());
                frm.setLabel26(info.getLabel26());
                request.getSession().setAttribute("WORKEXP_IDs", info.getExpColumn());
                request.getSession().setAttribute("EDUDETAIL_IDs", info.getEduColumn());
                request.getSession().setAttribute("DOCDETAIL_IDs", info.getDocColumn());
                request.getSession().setAttribute("TRAININGDETAIL_IDs", info.getTrainingColumn());
                if(info.getDescription() != null && !info.getDescription().equals(""))
                {
                    String add_path = resumetemplate.getMainPath("add_resumetemplate_file");
                    frm.setDeschidden(info.getDescription());
                    frm.setDescription(resumetemplate.readHTMLFile(info.getDescription(), add_path));
                }
                if (info.getFilename()!= null) {
                    frm.setResumefilehidden(info.getFilename());
                }
                request.getSession().setAttribute("FILENAME", info.getFilename());
            }
            saveToken(request);
            return mapping.findForward("add_resumetemplate");
        }
        else if (frm.getDoView() != null && frm.getDoView().equals("yes")) 
        {
            int resumetemplateId = frm.getResumetemplateId();
            frm.setResumetemplateId(resumetemplateId);
            ResumetemplateInfo info = resumetemplate.getResumetemplateDetailByIdforDetail(resumetemplateId);
            request.setAttribute("RESUMETEMPLATE_DETAIL", info);
            return mapping.findForward("view_resumetemplate");
        }
        else if (frm.getDoDeleteFile()!= null && frm.getDoDeleteFile().equals("yes")) 
        {
            print(this, "getDoDeleteFile block.");
            frm.setDoDeleteFile("no");            
            int resumetemplateId = frm.getResumetemplateId();
            frm.setResumetemplateId(resumetemplateId);
            int cc = resumetemplate.deleteFile(resumetemplateId, uId);
            if(cc > 0)
            {
                ResumetemplateInfo info = resumetemplate.getResumetemplateDetailByIdforDetail(resumetemplateId);
                request.setAttribute("RESUMETEMPLATE_DETAIL", info);
                return mapping.findForward("view_resumetemplate");
            }
        }
        else if (frm.getDoSave() != null && frm.getDoSave().equals("yes")) {
            print(this, "getDoSave block.");
            if (isTokenValid(request)) {
                resetToken(request);
                int resumetemplateId = frm.getResumetemplateId();
                String name = vobj.replacename(frm.getName());
                int status = 1;
                int clientId = frm.getClientId();
                String deschidden = frm.getDeschidden();
                String description = frm.getDescription();
                String description2 = frm.getDescription2();
                String description3 = frm.getDescription3();                
                int templateType = frm.getTemptype();
                int exptype = frm.getExptype();
                int edutype = frm.getEdutype();
                int doctype = frm.getDoctype();
                int certtype = frm.getCerttype();
                String label1 = frm.getLabel1();                
                String label2 = frm.getLabel2();                
                String label3 = frm.getLabel3();                
                String label4 = frm.getLabel4();                
                String label5 = frm.getLabel5();                
                String label6 = frm.getLabel6();                
                String label7 = frm.getLabel7();                
                String label8 = frm.getLabel8();                
                String label9 = frm.getLabel9();                
                String label10 = frm.getLabel10();                
                String label11 = frm.getLabel11();                
                String label12 = frm.getLabel12();                
                String label13 = frm.getLabel13();                
                String label14 = frm.getLabel14();                
                String label15 = frm.getLabel15();                
                String label16 = frm.getLabel16();                
                String label17 = frm.getLabel17();                
                String label18 = frm.getLabel18();                
                String label19 = frm.getLabel19();                
                String label20 = frm.getLabel20();                
                String label21 = frm.getLabel21();                
                String label22 = frm.getLabel22();                
                String label23 = frm.getLabel23();                
                String label24 = frm.getLabel24();                
                String label25 = frm.getLabel25();                
                String label26 = frm.getLabel26();                
                String ipAddrStr = request.getRemoteAddr();
                String iplocal = resumetemplate.getLocalIp();
                String[] expcolumn = frm.getExpColumn();
                String[] educolumn = frm.getEduColumn();
                String[] doccolumn = frm.getDocColumn();
                String[] trainingcolumn = frm.getTrainingColumn();
                
                String wexpcolumn = vobj.replacename(makeCommaDelimString(expcolumn));
                String candeducolumn = vobj.replacename(makeCommaDelimString(educolumn));
                String canddoccolumn = vobj.replacename(makeCommaDelimString(doccolumn));
                String ctrainingcolumn = vobj.replacename(makeCommaDelimString(trainingcolumn));
                int ck = resumetemplate.checkDuplicacy(resumetemplateId, name, clientId);
                if (ck == 1)
                {
                    saveToken(request);
                    Collection clients = resumetemplate.getClients(cids, allclient, permission);
                    frm.setClients(clients);
                    request.setAttribute("MESSAGE", "Resume template already exists");
                    return mapping.findForward("add_resumetemplate");
                }
                String add_path = resumetemplate.getMainPath("add_resumetemplate_file");
                java.util.Date now = new java.util.Date();
                String fname = String.valueOf(now.getTime());
                String htmlFolderName = resumetemplate.dateFolder();
                
                String add_path2 = resumetemplate.getMainPath("add_resumetemplate_pdf");
                String foldername = resumetemplate.createFolder(add_path2);
                String fn = String.valueOf(now.getTime());
                String fileName1 = "";
                FormFile filename = frm.getResumefile();
                if(filename != null && filename.getFileSize() > 0) {
                    fileName1 = resumetemplate.uploadFile(resumetemplateId, frm.getResumefilehidden(), filename, fn + "_1", add_path2, foldername);
                }                
                if (resumetemplateId > 0) {
                    if (description != null && !description.equals("")) {
                        if (deschidden != null && !deschidden.equals("")) {
                            File f = new File(add_path + deschidden);
                            if (f.exists()) {
                                f.delete();
                            }
                        }
                        description = resumetemplate.writeHTMLFile(description, add_path + htmlFolderName, fname + ".html");
                        description = htmlFolderName + "/" + description;
                    }
                } else if (description != null && !description.equals("")) {
                    description = resumetemplate.writeHTMLFile(description, add_path + htmlFolderName, fname + ".html");
                    description = htmlFolderName + "/" + description;
                }              
                request.getSession().removeAttribute("WORKEXP_IDs");
                request.getSession().removeAttribute("EDUDETAIL_IDs");
                request.getSession().removeAttribute("DOCDETAIL_IDs");
                request.getSession().removeAttribute("TRAININGDETAIL_IDs");
                ResumetemplateInfo info = new ResumetemplateInfo(resumetemplateId, name, status, uId, clientId, description, 
                        wexpcolumn, candeducolumn, canddoccolumn, ctrainingcolumn, templateType, description2, description3, fileName1, exptype,
                        label1, label2, label3, label4, label5, label6, label7, label8, label9, label10, label11, label12, label13, label14, label15,
                        label16, label17, label18, label19, label20, label21, label22, label23, label24, label25, label26, edutype, doctype, certtype);
                if (resumetemplateId <= 0) {
                    int cc = resumetemplate.createResumetemplate(info);
                    if (cc > 0) {
                        resumetemplate.createHistoryAccess(null, uId, ipAddrStr, iplocal, "Data Added", 50, cc);
                        request.setAttribute("MESSAGE", "Data added successfully.");

                    }
                    ArrayList resumetemplateList = resumetemplate.getResumetemplateByName(search, 0, count,allclient, permission, cids, assetids);
                    int cnt = 0;
                    if (resumetemplateList.size() > 0) {
                        ResumetemplateInfo cinfo = (ResumetemplateInfo) resumetemplateList.get(resumetemplateList.size() - 1);
                        cnt = cinfo.getResumetemplateId();
                        resumetemplateList.remove(resumetemplateList.size() - 1);
                    }
                    request.getSession().setAttribute("RESUMETEMPLATE_LIST", resumetemplateList);
                    request.getSession().setAttribute("COUNT_LIST", cnt + "");
                    request.getSession().setAttribute("NEXT", "0");
                    request.getSession().setAttribute("NEXTVALUE", "1");

                    return mapping.findForward("display");
                } else {
                    resumetemplate.updateResumetemplate(info);
                    resumetemplate.createHistoryAccess(null, uId, ipAddrStr, iplocal, "Data Updated", 50, resumetemplateId);
                    request.setAttribute("MESSAGE", "Data updated successfully.");
                    int next = 0;
                    if (request.getSession().getAttribute("NEXTVALUE") != null) {
                        next = Integer.parseInt((String) request.getSession().getAttribute("NEXTVALUE"));
                    }
                    next = next - 1;
                    if (next < 0) {
                        next = 0;
                    }
                    ArrayList resumetemplateList = resumetemplate.getResumetemplateByName(search, next, count,allclient, permission, cids, assetids);
                    int cnt = 0;
                    if (resumetemplateList.size() > 0) {
                        ResumetemplateInfo cinfo = (ResumetemplateInfo) resumetemplateList.get(resumetemplateList.size() - 1);
                        cnt = cinfo.getResumetemplateId();
                        resumetemplateList.remove(resumetemplateList.size() - 1);
                    }
                    request.getSession().setAttribute("RESUMETEMPLATE_LIST", resumetemplateList);
                    request.getSession().setAttribute("COUNT_LIST", cnt + "");
                    request.getSession().setAttribute("NEXT", next + "");
                    request.getSession().setAttribute("NEXTVALUE", (next + 1) + "");

                    return mapping.findForward("display");
                }
            }        
        }else if (frm.getDoCancel() != null && frm.getDoCancel().equals("yes")) {
            print(this, "doCancel block");
            int next = 0;
            if (request.getSession().getAttribute("NEXTVALUE") != null) {
                next = Integer.parseInt((String) request.getSession().getAttribute("NEXTVALUE"));
            }
            next = next - 1;
            if (next < 0) {
                next = 0;
            }
            ArrayList resumetemplateList = resumetemplate.getResumetemplateByName(search, next, count,allclient, permission, cids, assetids);
            int cnt = 0;
            if (resumetemplateList.size() > 0) {
                ResumetemplateInfo cinfo = (ResumetemplateInfo) resumetemplateList.get(resumetemplateList.size() - 1);
                cnt = cinfo.getResumetemplateId();
                resumetemplateList.remove(resumetemplateList.size() - 1);
            }
            request.getSession().setAttribute("RESUMETEMPLATE_LIST", resumetemplateList);
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

            ArrayList resumetemplateList = resumetemplate.getResumetemplateByName(search, 0, count,allclient, permission, cids, assetids);
            int cnt = 0;
            if (resumetemplateList.size() > 0) {
                ResumetemplateInfo cinfo = (ResumetemplateInfo) resumetemplateList.get(resumetemplateList.size() - 1);
                cnt = cinfo.getResumetemplateId();
                resumetemplateList.remove(resumetemplateList.size() - 1);
            }
            request.getSession().setAttribute("RESUMETEMPLATE_LIST", resumetemplateList);
            request.getSession().setAttribute("COUNT_LIST", cnt + "");
            request.getSession().setAttribute("NEXT", "0");
            request.getSession().setAttribute("NEXTVALUE", "1");
        }
        return mapping.findForward("display");
    }
}
