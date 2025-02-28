package com.web.jxp.documentissuedby;

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

public class DocumentissuedbyAction extends Action 
{
    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        DocumentissuedbyForm frm = (DocumentissuedbyForm) form;
        Documentissuedby documentissuedby = new Documentissuedby();
        Validate vobj = new Validate();
        
                
        int count = documentissuedby.getCount();
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
        int check_user = documentissuedby.checkUserSession(request, 37, permission);
        if (check_user == -1)
            return mapping.findForward("default");
        else if (check_user == -2) 
        {
            String authmess = documentissuedby.getMainPath("user_auth");
            authmess = authmess.replaceAll("__MODULE__", "Documentissuedby Module");
            request.setAttribute("AUTHMESSAGE", authmess);
            return mapping.findForward("auth");
        }
        if (frm.getDoAdd() != null && frm.getDoAdd().equals("yes"))
        {
            print(this, " doAdd block :: ");
            frm.setStatus(1);
            frm.setDocumentissuedbyId(-1);
            Collection countrys = documentissuedby.getCountrys();
            frm.setCountrys(countrys);

            Collection documents = documentissuedby.getDocuments();
            frm.setDocuments(documents);
            saveToken(request);
            return mapping.findForward("add_documentissuedby");
        }
        else if(frm.getDoModify() != null && frm.getDoModify().equals("yes"))
        {
            int documentissuedbyId = frm.getDocumentissuedbyId();
            frm.setDocumentissuedbyId(documentissuedbyId);
            DocumentissuedbyInfo info = documentissuedby.getDocumentissuedbyDetailById(documentissuedbyId);
            if(info != null)
            {                
                frm.setDocumentissuedbyName(info.getDocumentissuedbyName());
                frm.setStatus(info.getStatus());
                
                Collection countryss = documentissuedby.getCountrys();
                frm.setCountrys(countryss);
                frm.setCountryId(info.getCountryId());
                
                Collection documentss = documentissuedby.getDocuments();
                frm.setDocuments(documentss);
                frm.setDocumentId(info.getDocumentId());
            }
            saveToken(request);
            return mapping.findForward("add_documentissuedby");
        }
        else if(frm.getDoView() != null && frm.getDoView().equals("yes"))
        {
            int documentissuedbyId = frm.getDocumentissuedbyId();
            frm.setDocumentissuedbyId(documentissuedbyId);
            DocumentissuedbyInfo info = documentissuedby.getDocumentissuedbyDetailByIdforDetail(documentissuedbyId);
            request.setAttribute("DOCUMENTISSUEDBY_DETAIL", info);
            return mapping.findForward("view_documentissuedby");
        }        
        else if(frm.getDoSave() != null && frm.getDoSave().equals("yes"))
        {
            print(this,"getDoSave block.");
            if(isTokenValid(request))
            {
                resetToken(request);
                int documentissuedbyId = frm.getDocumentissuedbyId();
                String name = vobj.replacename(frm.getDocumentissuedbyName());
                int status = 1;
                int countryId = frm.getCountryId();
                int documentId = frm.getDocumentId();
                
                String ipAddrStr = request.getRemoteAddr();
                String iplocal = documentissuedby.getLocalIp();
                int ck = documentissuedby.checkDuplicacy(documentissuedbyId, name, countryId, documentId);
                if(ck == 1)
                {
                    saveToken(request);
                    request.setAttribute("MESSAGE", "Document Issued by already exists");
                    return mapping.findForward("add_documentissuedby");
                }
                DocumentissuedbyInfo info = new DocumentissuedbyInfo(documentissuedbyId, name, countryId, documentId, status, uId);
                if(documentissuedbyId <= 0)
                {
                    int cc = documentissuedby.createDocumentissuedby(info);
                    if(cc > 0)
                    {
                       documentissuedby.createHistoryAccess(null, uId, ipAddrStr, iplocal, "Data Added", 37, cc);
                       request.setAttribute("MESSAGE", "Data added successfully.");
                       
                    }
                    ArrayList documentissuedbyList = documentissuedby.getDocumentissuedbyByName(search, 0, count);
                    int cnt = 0;
                    if(documentissuedbyList.size() > 0)
                    {
                        DocumentissuedbyInfo cinfo = (DocumentissuedbyInfo) documentissuedbyList.get(documentissuedbyList.size() - 1);
                        cnt = cinfo.getDocumentissuedbyId();
                        documentissuedbyList.remove(documentissuedbyList.size() - 1);
                    }
                    request.getSession().setAttribute("DOCUMENTISSUEDBY_LIST", documentissuedbyList);
                    request.getSession().setAttribute("COUNT_LIST", cnt+"");
                    request.getSession().setAttribute("NEXT", "0");
                    request.getSession().setAttribute("NEXTVALUE", "1");
                    
                    return mapping.findForward("display");
                }
                else
                {
                    documentissuedby.updateDocumentissuedby(info);
                    documentissuedby.createHistoryAccess(null, uId, ipAddrStr, iplocal, "Data Updated", 37, documentissuedbyId);
                    request.setAttribute("MESSAGE", "Data updated successfully.");
                    int next = 0;
                    if(request.getSession().getAttribute("NEXTVALUE") != null)
                    {
                        next = Integer.parseInt((String) request.getSession().getAttribute("NEXTVALUE"));
                    }
                    next = next - 1;
                    if(next < 0)
                        next = 0;
                    ArrayList documentissuedbyList = documentissuedby.getDocumentissuedbyByName(search, next, count);
                    int cnt = 0;
                    if(documentissuedbyList.size() > 0)
                    {
                        DocumentissuedbyInfo cinfo = (DocumentissuedbyInfo) documentissuedbyList.get(documentissuedbyList.size() - 1);
                        cnt = cinfo.getDocumentissuedbyId();
                        documentissuedbyList.remove(documentissuedbyList.size() - 1);
                    }
                    request.getSession().setAttribute("DOCUMENTISSUEDBY_LIST", documentissuedbyList);
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
            ArrayList documentissuedbyList = documentissuedby.getDocumentissuedbyByName(search, next, count);
            int cnt = 0;
            if(documentissuedbyList.size() > 0)
            {
                DocumentissuedbyInfo cinfo = (DocumentissuedbyInfo) documentissuedbyList.get(documentissuedbyList.size() - 1);
                cnt = cinfo.getDocumentissuedbyId();
                documentissuedbyList.remove(documentissuedbyList.size() - 1);
            }
            request.getSession().setAttribute("DOCUMENTISSUEDBY_LIST", documentissuedbyList);
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
            
            ArrayList documentissuedbyList = documentissuedby.getDocumentissuedbyByName(search, 0, count);
            int cnt = 0;
            if(documentissuedbyList.size() > 0)
            {
                DocumentissuedbyInfo cinfo = (DocumentissuedbyInfo) documentissuedbyList.get(documentissuedbyList.size() - 1);
                cnt = cinfo.getDocumentissuedbyId();
                documentissuedbyList.remove(documentissuedbyList.size() - 1);
            }
            request.getSession().setAttribute("DOCUMENTISSUEDBY_LIST", documentissuedbyList);
            request.getSession().setAttribute("COUNT_LIST", cnt+"");
            request.getSession().setAttribute("NEXT", "0");
            request.getSession().setAttribute("NEXTVALUE", "1");
        }
        return mapping.findForward("display");
    }    
}