package com.web.jxp.pcategory;

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

public class PcategoryAction extends Action 
{
    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        PcategoryForm frm = (PcategoryForm) form;
        Pcategory pcategory = new Pcategory();
        Validate vobj = new Validate();
        Assettype assettype = new Assettype();
        Pdept pdept = new Pdept();
        int count = pcategory.getCount();
        
        Collection assettypes = pcategory.getAssettypes();
        frm.setAssettypes(assettypes);   
        int assettypeIdIndex = frm.getAssettypeIdIndex();
        frm.setAssettypeIdIndex(assettypeIdIndex);
        
        Collection pdepts = pcategory.getPdepts(assettypeIdIndex);
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
        int check_user = pcategory.checkUserSession(request, 83, permission);
        if (check_user == -1)
            return mapping.findForward("default");
        else if (check_user == -2) 
        {
            String authmess = pcategory.getMainPath("user_auth");
            authmess = authmess.replaceAll("__MODULE__", "Competency Assessments");
            request.setAttribute("AUTHMESSAGE", authmess);
            return mapping.findForward("auth");
        }
        if (frm.getDoAdd() != null && frm.getDoAdd().equals("yes"))
        {
            print(this, " doAdd block :: ");
            frm.setDoAdd("no"); 
            frm.setStatus(1);
            frm.setPcategoryId(-1);
            assettypes = assettype.getAssettypes();
            frm.setAssettypes(assettypes);   
            pdepts = pdept.getPdepts(frm.getAssettypeId());
            frm.setPdepts(pdepts);
            saveToken(request);
            return mapping.findForward("add_pcategory");
        }
        else if(frm.getDoModify() != null && frm.getDoModify().equals("yes"))
        {
            frm.setDoModify("no"); 
            int pcategoryId = frm.getPcategoryId();
            frm.setPcategoryId(pcategoryId);
            PcategoryInfo info = pcategory.getPcategoryDetailById(pcategoryId);
            if(info != null)
            {   
                assettypes = assettype.getAssettypes();
                frm.setAssettypes(assettypes); 
                pdepts = pdept.getPdepts(info.getAssettypeId());
                frm.setPdepts(pdepts);
                if(info.getName() != null)
                    frm.setName(info.getName());
                if(info.getDescription()!= null)
                    frm.setDescription(info.getDescription());
                frm.setAssettypeId(info.getAssettypeId());
                frm.setPdeptId(info.getPdeptId());
                frm.setStatus(info.getStatus());                
            }
            saveToken(request);
            return mapping.findForward("add_pcategory");
        }
        else if(frm.getDoView() != null && frm.getDoView().equals("yes"))
        {
            frm.setDoView("no"); 
            int pcategoryId = frm.getPcategoryId();
            frm.setPcategoryId(pcategoryId);
            frm.setSearchq(""); 
            frm.setQnamesugg("");
            PcategoryInfo info = pcategory.getPcategoryDetailByIdforDetail(pcategoryId);
            request.getSession().setAttribute("PCATEGORY_DETAIL", info);
            int assettypeId = info.getAssettypeId();
            ArrayList list1 = pcategory.getQuestionList(pcategoryId, "");
            ArrayList list2 = pcategory.getSuggestedList(pcategoryId, assettypeId); 
            request.setAttribute("QLIST1", list1);
            request.setAttribute("QLIST2", list2);
            return mapping.findForward("view_pcategory");
        }   
        else if(frm.getDoSearchQuestion() != null && frm.getDoSearchQuestion().equals("yes"))
        {
            frm.setDoSaveQuestion("no");
            int pcategoryId = frm.getPcategoryId();
            frm.setPcategoryId(pcategoryId);
            String searchq = frm.getSearchq() != null ? frm.getSearchq() : "";
            frm.setSearchq(searchq); 
            PcategoryInfo info = null;
            if(request.getSession().getAttribute("PCATEGORY_DETAIL") != null)
                info = (PcategoryInfo)request.getSession().getAttribute("PCATEGORY_DETAIL");
            int assettypeId = 0;
            if(info != null)
                assettypeId = info.getAssettypeId();
            ArrayList list1 = pcategory.getQuestionList(pcategoryId, searchq);
            ArrayList list2 = pcategory.getSuggestedList(pcategoryId, assettypeId); 
            request.setAttribute("QLIST1", list1);
            request.setAttribute("QLIST2", list2);
            return mapping.findForward("view_pcategory");
        }  
        else if(frm.getDoSaveQuestion() != null && frm.getDoSaveQuestion().equals("yes"))
        {
            frm.setDoSaveQuestion("no");
            int pcategoryId = frm.getPcategoryId();
            frm.setPcategoryId(pcategoryId);
            String searchq = "";
            frm.setSearchq(searchq); 
            int pquestionId = frm.getPquestionId();
            String qname = frm.getQname() != null ? frm.getQname() : ""; 
            if(qname.equals(""))
                qname = frm.getQnamesugg() != null ? frm.getQnamesugg() : "";
            frm.setQnamesugg("");
            int ck = pcategory.checkDuplicacyQuestion(pquestionId, pcategoryId, qname);
            if(ck == 1)
            {
                saveToken(request);
                request.setAttribute("MESSAGE", "Question already exist");
            }
            else
            {
                if(pquestionId <= 0)
                {
                    pcategory.createQuestion(pcategoryId, qname, uId);
                }
                else
                {
                    pcategory.updatequestion(pquestionId, qname, uId);
                }   
            }
            PcategoryInfo info = pcategory.getPcategoryDetailByIdforDetail(pcategoryId);
            request.getSession().setAttribute("PCATEGORY_DETAIL", info);
            int assettypeId = 0;
            if(info != null)
                assettypeId = info.getAssettypeId();
            ArrayList list1 = pcategory.getQuestionList(pcategoryId, searchq);
            ArrayList list2 = pcategory.getSuggestedList(pcategoryId, assettypeId); 
            request.setAttribute("QLIST1", list1);
            request.setAttribute("QLIST2", list2);
            return mapping.findForward("view_pcategory");
        }  
        else if(frm.getDoDeleteQuestion() != null && frm.getDoDeleteQuestion().equals("yes"))
        {
            frm.setDoDeleteQuestion("no");
            int pcategoryId = frm.getPcategoryId();
            frm.setPcategoryId(pcategoryId);
            String searchq = "";
            frm.setSearchq(searchq); 
            int delId = frm.getDelId();
            pcategory.deleteQuestion(delId, uId); 
            PcategoryInfo info = pcategory.getPcategoryDetailByIdforDetail(pcategoryId);
            request.getSession().setAttribute("PCATEGORY_DETAIL", info);
            int assettypeId = 0;
            if(info != null)
                assettypeId = info.getAssettypeId();
            ArrayList list1 = pcategory.getQuestionList(pcategoryId, searchq);
            ArrayList list2 = pcategory.getSuggestedList(pcategoryId, assettypeId); 
            request.setAttribute("QLIST1", list1);
            request.setAttribute("QLIST2", list2);
            return mapping.findForward("view_pcategory");
        } 
        else if(frm.getDoSave() != null && frm.getDoSave().equals("yes"))
        {
            print(this,"getDoSave block.");
            if(isTokenValid(request))
            {
                resetToken(request);
                int pcategoryId = frm.getPcategoryId();
                String name = vobj.replacedesc(frm.getName());
                int pdeptId = frm.getPdeptId();
                String description = frm.getDescription();
                int status = 1;
                int assettypeId = frm.getAssettypeId();                
                String ipAddrStr = request.getRemoteAddr();
                String iplocal = pcategory.getLocalIp();
                int ck = pcategory.checkDuplicacy(pcategoryId, name, assettypeId, pdeptId);
                if(ck == 1)
                {
                    saveToken(request);
                    pdepts = pdept.getPdepts(assettypeId);
                    frm.setPdepts(pdepts);
                    request.setAttribute("MESSAGE", "Data already exists");
                    return mapping.findForward("add_pcategory");
                }
                PcategoryInfo info = new PcategoryInfo(pcategoryId, name, assettypeId, pdeptId, description, status, uId);
                if(pcategoryId <= 0)
                {
                    int cc = pcategory.createPcategory(info);
                    if(cc > 0)
                    {
                       pcategory.createHistoryAccess(null, uId, ipAddrStr, iplocal, "Data Added", 83, cc);
                       request.setAttribute("MESSAGE", "Data added successfully.");
                       
                    }
                    ArrayList pcategoryList = pcategory.getPcategoryByName(search, pdeptIdIndex, assettypeIdIndex, 0, count);
                    int cnt = 0;
                    if(pcategoryList.size() > 0)
                    {
                        PcategoryInfo cinfo = (PcategoryInfo) pcategoryList.get(pcategoryList.size() - 1);
                        cnt = cinfo.getPcategoryId();
                        pcategoryList.remove(pcategoryList.size() - 1);
                    }
                    request.getSession().setAttribute("PCATEGORY_LIST", pcategoryList);
                    request.getSession().setAttribute("COUNT_LIST", cnt+"");
                    request.getSession().setAttribute("NEXT", "0");
                    request.getSession().setAttribute("NEXTVALUE", "1");
                    
                    return mapping.findForward("display");
                }
                else
                {
                    pcategory.updatePcategory(info);
                    pcategory.createHistoryAccess(null, uId, ipAddrStr, iplocal, "Data Updated", 83, pcategoryId);
                    request.setAttribute("MESSAGE", "Data updated successfully.");
                    int next = 0;
                    if(request.getSession().getAttribute("NEXTVALUE") != null)
                    {
                        next = Integer.parseInt((String) request.getSession().getAttribute("NEXTVALUE"));
                    }
                    next = next - 1;
                    if(next < 0)
                        next = 0;
                    ArrayList pcategoryList = pcategory.getPcategoryByName(search, pdeptIdIndex, assettypeIdIndex, next, count);
                    int cnt = 0;
                    if(pcategoryList.size() > 0)
                    {
                        PcategoryInfo cinfo = (PcategoryInfo) pcategoryList.get(pcategoryList.size() - 1);
                        cnt = cinfo.getPcategoryId();
                        pcategoryList.remove(pcategoryList.size() - 1);
                    }
                    request.getSession().setAttribute("PCATEGORY_LIST", pcategoryList);
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
            ArrayList pcategoryList = pcategory.getPcategoryByName(search, pdeptIdIndex, assettypeIdIndex, next, count);
            int cnt = 0;
            if(pcategoryList.size() > 0)
            {
                PcategoryInfo cinfo = (PcategoryInfo) pcategoryList.get(pcategoryList.size() - 1);
                cnt = cinfo.getPcategoryId();
                pcategoryList.remove(pcategoryList.size() - 1);
            }
            request.getSession().setAttribute("PCATEGORY_LIST", pcategoryList);
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
            ArrayList pcategoryList = pcategory.getPcategoryByName(search, pdeptIdIndex, assettypeIdIndex, 0, count);
            int cnt = 0;
            if(pcategoryList.size() > 0)
            {
                PcategoryInfo cinfo = (PcategoryInfo) pcategoryList.get(pcategoryList.size() - 1);
                cnt = cinfo.getPcategoryId();
                pcategoryList.remove(pcategoryList.size() - 1);
            }
            request.getSession().setAttribute("PCATEGORY_LIST", pcategoryList);
            request.getSession().setAttribute("COUNT_LIST", cnt+"");
            request.getSession().setAttribute("NEXT", "0");
            request.getSession().setAttribute("NEXTVALUE", "1");
        }
        return mapping.findForward("display");
    }    
}