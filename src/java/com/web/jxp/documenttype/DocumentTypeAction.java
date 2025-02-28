package com.web.jxp.documenttype;

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
import java.util.Stack;

public class DocumentTypeAction extends Action 
{
    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
       DocumentTypeForm frm = (DocumentTypeForm) form;
       DocumentType documentType = new DocumentType();
       Validate vobj = new Validate();
        int count = documentType.getCount();
        String search = frm.getSearch() != null && !frm.getSearch().equals("") ? frm.getSearch() : "";
        frm.setSearch(search);
        int uId = 0;
        String permission = "N";
        if (request.getSession().getAttribute("LOGININFO") != null) {
            UserInfo uInfo = (UserInfo) request.getSession().getAttribute("LOGININFO");
            if (uInfo != null) 
            {
                permission = uInfo.getPermission();
                uId = uInfo.getUserId();
            }
        }
        int check_user = documentType.checkUserSession(request, 11, permission);
        if (check_user == -1)
            return mapping.findForward("default");
        else if (check_user == -2) 
        {
            String authmess = documentType.getMainPath("user_auth");
            authmess = authmess.replaceAll("__MODULE__", "Document Type Module");
            request.setAttribute("AUTHMESSAGE", authmess);
            return mapping.findForward("auth");
        }
        if (frm.getDoAdd() != null && frm.getDoAdd().equals("yes"))
        {
            print(this, " doAdd block :: ");
            frm.setStatus(1);
            frm.setDocumentTypeId(-1);
            saveToken(request);
            request.getSession().removeAttribute("FORMAT_IDs");
            request.getSession().removeAttribute("DOCMODULE_IDs");
            request.getSession().removeAttribute("DOCSUBMODULE_IDs");
            return mapping.findForward("add_documentType");
        }
        else if(frm.getDoModify() != null && frm.getDoModify().equals("yes"))
        {
            int documentTypeId = frm.getDocumentTypeId();
            frm.setDocumentTypeId(documentTypeId);
            DocumentTypeInfo info = documentType.getDocumentTypeDetailById(documentTypeId);
            if(info != null)
            {
                frm.setName(info.getName());
                frm.setStatus(info.getStatus());
                frm.setMflag(info.getMflag());
                request.getSession().setAttribute("FORMAT_IDs", info.getDocFormat());
                request.getSession().setAttribute("DOCMODULE_IDs", info.getDocModule());
                request.getSession().setAttribute("DOCSUBMODULE_IDs", info.getDocsubModule());
            }
            saveToken(request);
            return mapping.findForward("add_documentType");
        }
        else if(frm.getDoView() != null && frm.getDoView().equals("yes"))
        {
            int documentTypeId = frm.getDocumentTypeId();
            frm.setDocumentTypeId(documentTypeId);
            DocumentTypeInfo info = documentType.getDocumentTypeDetailByIdforDetail(documentTypeId);
            request.setAttribute("DOCUMENTTYPE_DETAIL", info);
            return mapping.findForward("view_documentType");
        }        
        else if(frm.getDoSave() != null && frm.getDoSave().equals("yes"))
        {
            print(this,"getDoSave block.");
            if(isTokenValid(request))
            {
                resetToken(request);
                int documentTypeId = frm.getDocumentTypeId();
                String name = vobj.replacename(frm.getName());
                int mflag = frm.getMflag();
                
                String[] format = frm.getDocFormat();
                String[] module = frm.getDocModule();
                String[] submodule = frm.getDocsubModule();
                
                String docformat = vobj.replacealphacomma(makeCommaDelimString(format));
                String docmodule = vobj.replacealphacomma(makeCommaDelimString(module));
                String docsubmodule = vobj.replacealphacomma(makeCommaDelimString(submodule));
                int status = 1;
                String ipAddrStr = request.getRemoteAddr();
                String iplocal = documentType.getLocalIp();
                int ck = documentType.checkDuplicacy(documentTypeId, name);
                if(ck == 1)
                {
                    saveToken(request);
                    request.setAttribute("MESSAGE", "Document already exists");
                    return mapping.findForward("add_documentType");
                }
                DocumentTypeInfo info = new DocumentTypeInfo(documentTypeId, name,docformat,docmodule, docsubmodule, status, uId, mflag);
                if(documentTypeId <= 0)
                {
                    int cc = documentType.createDocumentType(info);
                    if(cc > 0)
                    {
                       documentType.createHistoryAccess(null, uId, ipAddrStr, iplocal, "Data Added", 11, cc);
                       request.setAttribute("MESSAGE", "Data added successfully.");
                       
                    }
                    ArrayList documentTypeList = documentType.getDocumentTypeByName(search, 0, count);
                    int cnt = 0;
                    if(documentTypeList.size() > 0)
                    {
                        DocumentTypeInfo cinfo = (DocumentTypeInfo) documentTypeList.get(documentTypeList.size() - 1);
                        cnt = cinfo.getDocumentTypeId();
                        documentTypeList.remove(documentTypeList.size() - 1);
                    }
                    request.getSession().setAttribute("DOCUMENTTYPE_LIST", documentTypeList);
                    request.getSession().setAttribute("COUNT_LIST", cnt+"");
                    request.getSession().setAttribute("NEXT", "0");
                    request.getSession().setAttribute("NEXTVALUE", "1");
                    
                    return mapping.findForward("display");
                }
                else
                {
                    documentType.updateDocumentType(info);
                    documentType.createHistoryAccess(null, uId, ipAddrStr, iplocal, "Data Updated", 11, documentTypeId);
                    request.setAttribute("MESSAGE", "Data updated successfully.");
                    int next = 0;
                    if(request.getSession().getAttribute("NEXTVALUE") != null)
                    {
                        next = Integer.parseInt((String) request.getSession().getAttribute("NEXTVALUE"));
                    }
                    next = next - 1;
                    if(next < 0)
                        next = 0;
                    ArrayList documentTypeList = documentType.getDocumentTypeByName(search, next, count);
                    int cnt = 0;
                    if(documentTypeList.size() > 0)
                    {
                        DocumentTypeInfo cinfo = (DocumentTypeInfo) documentTypeList.get(documentTypeList.size() - 1);
                        cnt = cinfo.getDocumentTypeId();
                        documentTypeList.remove(documentTypeList.size() - 1);
                    }
                    request.getSession().setAttribute("DOCUMENTTYPE_LIST", documentTypeList);
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
            ArrayList documentTypeList = documentType.getDocumentTypeByName(search, next, count);
            int cnt = 0;
            if(documentTypeList.size() > 0)
            {
                DocumentTypeInfo cinfo = (DocumentTypeInfo) documentTypeList.get(documentTypeList.size() - 1);
                cnt = cinfo.getDocumentTypeId();
                documentTypeList.remove(documentTypeList.size() - 1);
            }
            request.getSession().setAttribute("DOCUMENTTYPE_LIST", documentTypeList);
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
            
            ArrayList documentTypeList = documentType.getDocumentTypeByName(search, 0, count);
            int cnt = 0;
            if(documentTypeList.size() > 0)
            {
                DocumentTypeInfo cinfo = (DocumentTypeInfo) documentTypeList.get(documentTypeList.size() - 1);
                cnt = cinfo.getDocumentTypeId();
                documentTypeList.remove(documentTypeList.size() - 1);
            }
            request.getSession().setAttribute("DOCUMENTTYPE_LIST", documentTypeList);
            request.getSession().setAttribute("COUNT_LIST", cnt+"");
            request.getSession().setAttribute("NEXT", "0");
            request.getSession().setAttribute("NEXTVALUE", "1");
        }
        return mapping.findForward("display");
    }    
}