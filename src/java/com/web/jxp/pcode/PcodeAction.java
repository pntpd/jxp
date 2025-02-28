package com.web.jxp.pcode;

import com.web.jxp.assettype.Assettype;
import com.web.jxp.base.Validate;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import static com.web.jxp.common.Common.*;
import com.web.jxp.pdept.Pdept;
import com.web.jxp.user.UserInfo;
import java.util.Collection;
import java.util.Stack;

public class PcodeAction extends Action 
{
    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        PcodeForm frm = (PcodeForm) form;
        Pcode pcode = new Pcode();
        Validate vobj = new Validate();
        Assettype assettype = new Assettype();
        Pdept pdept = new Pdept();
        int count = pcode.getCount();
        
        Collection assettypes = pcode.getAssettypes();
        frm.setAssettypes(assettypes);   
        int assettypeIdIndex = frm.getAssettypeIdIndex();
        frm.setAssettypeIdIndex(assettypeIdIndex);
        
        Collection pdepts = pcode.getPdepts(assettypeIdIndex);
        frm.setPdepts(pdepts);
        int pdeptIdIndex = frm.getPdeptIdIndex();
        frm.setPdeptIdIndex(pdeptIdIndex);
        
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
        int check_user = pcode.checkUserSession(request, 86, permission);
        if (check_user == -1)
            return mapping.findForward("default");
        else if (check_user == -2) 
        {
            String authmess = pcode.getMainPath("user_auth");
            authmess = authmess.replaceAll("__MODULE__", "Competency Framework Module");
            request.setAttribute("AUTHMESSAGE", authmess);
            return mapping.findForward("auth");
        }
        if (frm.getDoAdd() != null && frm.getDoAdd().equals("yes"))
        {
            print(this, " doAdd block :: ");
            frm.setDoAdd("no"); 
            frm.setStatus(1);
            frm.setPcodeId(-1);
            assettypes = assettype.getAssettypes();
            frm.setAssettypes(assettypes);   
            pdepts = pdept.getPdepts(frm.getAssettypeId());
            frm.setPdepts(pdepts);
            saveToken(request);
            return mapping.findForward("add_pcode");
        }
        else if(frm.getDoModify() != null && frm.getDoModify().equals("yes"))
        {
            frm.setDoModify("no"); 
            int pcodeId = frm.getPcodeId();
            frm.setPcodeId(pcodeId);
            PcodeInfo info = pcode.getPcodeDetailById(pcodeId);
            if(info != null)
            {   
                assettypes = assettype.getAssettypes();
                frm.setAssettypes(assettypes); 
                pdepts = pdept.getPdepts(info.getAssettypeId());
                frm.setPdepts(pdepts);
                if(info.getCode() != null)
                    frm.setCode(info.getCode());
                if(info.getName() != null)
                    frm.setName(info.getName());
                if(info.getDescription()!= null)
                    frm.setDescription(info.getDescription());
                frm.setAssettypeId(info.getAssettypeId());
                frm.setPdeptId(info.getPdeptId());
                frm.setStatus(info.getStatus());                
            }
            saveToken(request);
            return mapping.findForward("add_pcode");
        }
        else if(frm.getDoView() != null && frm.getDoView().equals("yes"))
        {
            frm.setDoView("no"); 
            int pcodeId = frm.getPcodeId();
            frm.setPcodeId(pcodeId);
            PcodeInfo info = pcode.getPcodeDetailByIdforDetail(pcodeId);
            int pdeptId = 0;
            if(info != null)
                pdeptId = info.getPdeptId();
            Collection categories = pcode.getCategories(pdeptId);
            frm.setCategories(categories); 
            frm.setCategoryId(-1);
            request.removeAttribute("CATNAME");
            frm.setCategoryIdHidden(-1);
            request.removeAttribute("SAVED");
            request.getSession().setAttribute("PCODE_DETAIL", info);
            return mapping.findForward("view_pcode");
        }
        else if(frm.getDoCategory() != null && frm.getDoCategory().equals("yes"))
        {
            frm.setDoCategory("no"); 
            int pcodeId = frm.getPcodeId();
            frm.setPcodeId(pcodeId);
            PcodeInfo info = pcode.getPcodeDetailByIdforDetail(pcodeId);
            int pdeptId = 0;
            if(info != null)
                pdeptId = info.getPdeptId();
            Collection categories = pcode.getCategories(pdeptId);
            frm.setCategories(categories); 
            
            int categoryId = frm.getCategoryId();
            frm.setCategoryIdHidden(categoryId);
            String categoryName = frm.getCategoryName() != null ? frm.getCategoryName() : "";
            frm.setCategoryId(categoryId);
            PcodeInfo cidinfo = new PcodeInfo(categoryId, categoryName);            
            ArrayList list = pcode.getQuestionList(pcodeId, categoryId); 
            
            request.setAttribute("CATNAME", cidinfo);
            request.setAttribute("LIST", list);
            request.removeAttribute("SAVED");
            request.getSession().setAttribute("PCODE_DETAIL", info);
            return mapping.findForward("view_pcode");
        }
        else if (frm.getDoSaveQuestion() != null && frm.getDoSaveQuestion().equals("yes"))
        {
            frm.setDoSaveQuestion("no");
            int pcodeId = frm.getPcodeId();
            frm.setPcodeId(pcodeId);
            
            PcodeInfo info = pcode.getPcodeDetailByIdforDetail(pcodeId);
            int pdeptId = 0;
            if(info != null)
                pdeptId = info.getPdeptId();
            Collection categories = pcode.getCategories(pdeptId);
            frm.setCategories(categories); 
            int categoryId = frm.getCategoryId();
            frm.setCategoryIdHidden(categoryId);
            int questionId[] = frm.getQuestionId();
            pcode.createQuestionDetail(pcodeId, categoryId, questionId, uId);

            request.removeAttribute("CATNAME");
            request.removeAttribute("LIST");
            request.setAttribute("SAVED", "yes");
            frm.setCategoryId(-1);
            frm.setCategoryIdHidden(-1);
            return mapping.findForward("view_pcode");
        } 
        else if(frm.getDoSave() != null && frm.getDoSave().equals("yes"))
        {
            print(this,"getDoSave block.");
            if(isTokenValid(request))
            {
                resetToken(request);
                int pcodeId = frm.getPcodeId();
                String code = frm.getCode();
                String name = vobj.replacedesc(frm.getName());
                int pdeptId = frm.getPdeptId();
                String description = frm.getDescription();
                int status = 1;
                int assettypeId = frm.getAssettypeId();                
                String ipAddrStr = request.getRemoteAddr();
                String iplocal = pcode.getLocalIp();
                int ck = pcode.checkDuplicacy(pcodeId, code);
                if(ck == 1)
                {
                    saveToken(request);
                    pdepts = pdept.getPdepts(assettypeId);
                    frm.setPdepts(pdepts);
                    request.setAttribute("MESSAGE", "Data already exists");
                    return mapping.findForward("add_pcode");
                }
                PcodeInfo info = new PcodeInfo(pcodeId, code, name, assettypeId, pdeptId, description, status, uId);
                if(pcodeId <= 0)
                {
                    int cc = pcode.createPcode(info);
                    if(cc > 0)
                    {
                       pcode.createHistoryAccess(null, uId, ipAddrStr, iplocal, "Data Added", 86, cc);
                       request.setAttribute("MESSAGE", "Data added successfully.");
                       
                    }
                    ArrayList pcodeList = pcode.getPcodeByName(search, pdeptIdIndex, assettypeIdIndex, 0, count);
                    int cnt = 0;
                    if(pcodeList.size() > 0)
                    {
                        PcodeInfo cinfo = (PcodeInfo) pcodeList.get(pcodeList.size() - 1);
                        cnt = cinfo.getPcodeId();
                        pcodeList.remove(pcodeList.size() - 1);
                    }
                    request.getSession().setAttribute("PCODE_LIST", pcodeList);
                    request.getSession().setAttribute("COUNT_LIST", cnt+"");
                    request.getSession().setAttribute("NEXT", "0");
                    request.getSession().setAttribute("NEXTVALUE", "1");
                    
                    return mapping.findForward("display");
                }
                else
                {
                    pcode.updatePcode(info);
                    pcode.createHistoryAccess(null, uId, ipAddrStr, iplocal, "Data Updated", 86, pcodeId);
                    request.setAttribute("MESSAGE", "Data updated successfully.");
                    int next = 0;
                    if(request.getSession().getAttribute("NEXTVALUE") != null)
                    {
                        next = Integer.parseInt((String) request.getSession().getAttribute("NEXTVALUE"));
                    }
                    next = next - 1;
                    if(next < 0)
                        next = 0;
                    ArrayList pcodeList = pcode.getPcodeByName(search, pdeptIdIndex, assettypeIdIndex, next, count);
                    int cnt = 0;
                    if(pcodeList.size() > 0)
                    {
                        PcodeInfo cinfo = (PcodeInfo) pcodeList.get(pcodeList.size() - 1);
                        cnt = cinfo.getPcodeId();
                        pcodeList.remove(pcodeList.size() - 1);
                    }
                    request.getSession().setAttribute("PCODE_LIST", pcodeList);
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
            ArrayList pcodeList = pcode.getPcodeByName(search, pdeptIdIndex, assettypeIdIndex, next, count);
            int cnt = 0;
            if(pcodeList.size() > 0)
            {
                PcodeInfo cinfo = (PcodeInfo) pcodeList.get(pcodeList.size() - 1);
                cnt = cinfo.getPcodeId();
                pcodeList.remove(pcodeList.size() - 1);
            }
            request.getSession().setAttribute("PCODE_LIST", pcodeList);
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
            ArrayList pcodeList = pcode.getPcodeByName(search, pdeptIdIndex, assettypeIdIndex, 0, count);
            int cnt = 0;
            if(pcodeList.size() > 0)
            {
                PcodeInfo cinfo = (PcodeInfo) pcodeList.get(pcodeList.size() - 1);
                cnt = cinfo.getPcodeId();
                pcodeList.remove(pcodeList.size() - 1);
            }
            request.getSession().setAttribute("PCODE_LIST", pcodeList);
            request.getSession().setAttribute("COUNT_LIST", cnt+"");
            request.getSession().setAttribute("NEXT", "0");
            request.getSession().setAttribute("NEXTVALUE", "1");
        }
        return mapping.findForward("display");
    }    
}