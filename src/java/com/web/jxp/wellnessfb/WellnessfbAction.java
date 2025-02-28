package com.web.jxp.wellnessfb;

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

public class WellnessfbAction extends Action 
{
    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        WellnessfbForm frm = (WellnessfbForm) form;
        Wellnessfb wellnessfb = new Wellnessfb();
        Validate vobj = new Validate();
        int count = wellnessfb.getCount();
        String search = frm.getSearch() != null && !frm.getSearch().equals("") ? frm.getSearch() : "";
        frm.setSearch(search);
        Collection statuses = wellnessfb.getStatuses();
        frm.setStatuses(statuses);
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
        int check_user = wellnessfb.checkUserSession(request, 69, permission);
        if (check_user == -1)
            return mapping.findForward("default");
        else if (check_user == -2) 
        {
            String authmess = wellnessfb.getMainPath("user_auth");
            authmess = authmess.replaceAll("__MODULE__", "Wellnessfb Module");
            request.setAttribute("AUTHMESSAGE", authmess);
            return mapping.findForward("auth");
        }
        if (frm.getDoAdd() != null && frm.getDoAdd().equals("yes"))
        {
            frm.setDoAdd("no");
            print(this, " doAdd block :: ");
            frm.setStatus(1);
            frm.setCategoryId(-1);
            saveToken(request);
            frm.setCategorycode((wellnessfb.changeNum(wellnessfb.getcategoryMaxId(), 3)));
            return mapping.findForward("add_category");
        }
        else if(frm.getDoModify() != null && frm.getDoModify().equals("yes"))
        {
             frm.setDoModify("no");
            int categoryId = frm.getCategoryId();
            frm.setCategoryId(categoryId);
            WellnessfbInfo info = wellnessfb.getWellnessfbDetailById(categoryId);
            if(info != null)
            {                
                frm.setName(info.getName());
                frm.setStatus(info.getStatus());
                frm.setCdescription(info.getDescription());
                frm.setCategorycode(wellnessfb.changeNum(categoryId, 3));
            }
            if (request.getAttribute("CATEGORYWFSAVEMODEL") != null) {

                    request.removeAttribute("CATEGORYWFSAVEMODEL");
                }
            saveToken(request);
            return mapping.findForward("add_category");
        }
        else if(frm.getDoView() != null && frm.getDoView().equals("yes"))
        {
            frm.setDoView("no");
            int categoryId = frm.getCategoryId();
            frm.setCategoryId(categoryId);
            frm.setCategorycode(wellnessfb.changeNum(categoryId, 3));
            WellnessfbInfo info = wellnessfb.getWellnessfbDetailByIdforDetail(categoryId);
            request.setAttribute("CATEGORYWF_DETAIL", info);
            return mapping.findForward("view_category");
        }        
        else if(frm.getDoSave() != null && frm.getDoSave().equals("yes"))
        {
             frm.setDoSave("no");
            print(this,"getDoSave block.");
            if(isTokenValid(request))
            {
                resetToken(request);
                int categoryId = frm.getCategoryId();
                String name = vobj.replacedesc(frm.getName());
                String description = vobj.replacedesc(frm.getCdescription());
                int status = 1;
                String ipAddrStr = request.getRemoteAddr();
                String iplocal = wellnessfb.getLocalIp();
                int ck = wellnessfb.checkDuplicacy(categoryId, name);
                if(ck == 1)
                {
                    saveToken(request);
                    frm.setName(name);
                    frm.setCdescription(description);
                    request.setAttribute("MESSAGE", "Category already exists");
                    return mapping.findForward("add_category");
                }
                //add_trainingfiles                
                
                WellnessfbInfo info = new WellnessfbInfo(categoryId, name, status, uId, description);
                if(categoryId <= 0)
                {
                    int cc = wellnessfb.createWellnessfb(info);
                    if(cc > 0)
                    {
                       wellnessfb.createHistoryAccess(null, uId, ipAddrStr, iplocal, "Data Added", 69, cc);
                       request.setAttribute("MESSAGE", "Data added successfully.");
                       
                    }
                    request.setAttribute("CATEGORYWFSAVEMODEL", "yes");
                    frm.setCategoryId(cc);
                    ArrayList wellnessfbList = wellnessfb.getWellnessfbByName(search, 0, count);
                    int cnt = 0;
                    if(wellnessfbList.size() > 0)
                    {
                        WellnessfbInfo cinfo = (WellnessfbInfo) wellnessfbList.get(wellnessfbList.size() - 1);
                        cnt = cinfo.getCategoryId();
                        wellnessfbList.remove(wellnessfbList.size() - 1);
                    }
                    request.getSession().setAttribute("CATEGORYWF_LIST", wellnessfbList);
                    request.getSession().setAttribute("COUNT_LIST", cnt+"");
                    request.getSession().setAttribute("NEXT", "0");
                    request.getSession().setAttribute("NEXTVALUE", "1");
                    
                    return mapping.findForward("display");
                }
                else
                {
                    wellnessfb.updateWellnessfb(info);
                    wellnessfb.createHistoryAccess(null, uId, ipAddrStr, iplocal, "Data Updated", 69, categoryId);
                    request.setAttribute("MESSAGE", "Data updated successfully.");
                    int next = 0;
                    if(request.getSession().getAttribute("NEXTVALUE") != null)
                    {
                        next = Integer.parseInt((String) request.getSession().getAttribute("NEXTVALUE"));
                    }
                    next = next - 1;
                    if(next < 0)
                        next = 0;
                    ArrayList wellnessfbList = wellnessfb.getWellnessfbByName(search, next, count);
                    int cnt = 0;
                    if(wellnessfbList.size() > 0)
                    {
                        WellnessfbInfo cinfo = (WellnessfbInfo) wellnessfbList.get(wellnessfbList.size() - 1);
                        cnt = cinfo.getCategoryId();
                        wellnessfbList.remove(wellnessfbList.size() - 1);
                    }
                    request.getSession().setAttribute("CATEGORYWF_LIST", wellnessfbList);
                    request.getSession().setAttribute("COUNT_LIST", cnt+"");
                    request.getSession().setAttribute("NEXT", next+"");
                    request.getSession().setAttribute("NEXTVALUE", (next+1)+"");
                    
                    return mapping.findForward("display");
                }                
            }
        }
        else if(frm.getDoIndexSubcategory()!= null && frm.getDoIndexSubcategory().equals("yes"))
        {
            frm.setDoAddSubcategory("no");
            print(this,"getDoIndexSubcategory block.");
            int categoryId = frm.getCategoryId();
            frm.setCategoryId(categoryId);
            ArrayList list  = wellnessfb.getsubcategoryDetailByIdforDetail(categoryId);
            request.setAttribute("SUBCATEGORYWF_LIST", list);

            WellnessfbInfo info = wellnessfb.getcategorystatus(categoryId);
            request.setAttribute("CATEGORY_STATUS", info);
            request.getSession().removeAttribute("MDLIST");
            return mapping.findForward("view_subcategory");
        }
        else if (frm.getDoAddSubcategory()!= null && frm.getDoAddSubcategory().equals("yes"))
        {
            frm.setDoAdd("no");
            print(this, " getDoAddSubcategory block :: ");
            frm.setStatus(1);
            frm.setSubcategoryId(-1);
            int categoryId = frm.getCategoryId();
            frm.setCategoryId(categoryId);
            saveToken(request);
            frm.setCategorycode(wellnessfb.changeNum(categoryId, 3));
            frm.setCategoryname(wellnessfb.getcategoryname(categoryId));
            request.getSession().removeAttribute("MDLIST");
            return mapping.findForward("add_subcategory");
        }
        else if(frm.getDoModifysubcategory()!= null && frm.getDoModifysubcategory().equals("yes"))
        {
             frm.setDoModifysubcategory("no");
             print(this, " getDoModifysubcategory block :: ");
            int categoryId = frm.getCategoryId();
            frm.setCategoryId(categoryId);
            int subcategoryId = frm.getSubcategoryId();
            frm.setSubcategoryId(subcategoryId);
            WellnessfbInfo info = wellnessfb.getsubcategoryDetailById(categoryId,subcategoryId);
            if(info != null)
            {                
                frm.setName(info.getName());
                frm.setStatus(info.getStatus());
                frm.setCdescription(info.getDescription());
                frm.setCategorycode(wellnessfb.changeNum(categoryId, 3));
                frm.setCategoryname(info.getCategoryname());
                frm.setRepeatdp(info.getRepeatdp());
                
                frm.setSchedulecb(info.getSchedulecb());
                if(info.getRepeatdp() == 4)
                {
                    ArrayList list = wellnessfb.getListfromVal(info.getSchedulevalue());
                    request.getSession().setAttribute("MDLIST",list );
                }
                else
                {
                    frm.setSchedulevalue(info.getSchedulevalue());
                }
                request.getSession().setAttribute("NOTIFICATIONDETAIL_IDs", info.getNotification());
            }
             if (request.getAttribute("SUBCATEGORYWFSAVEMODEL") != null) {

                    request.removeAttribute("SUBCATEGORYWFSAVEMODEL");
                }
            saveToken(request);
            return mapping.findForward("add_subcategory");
        }
        else if(frm.getDoSaveSubcategory()!= null && frm.getDoSaveSubcategory().equals("yes"))
        {
             frm.setDoSaveSubcategory("no");
            print(this,"getDoSaveSubcategory block.");
            if(isTokenValid(request))
            {
                resetToken(request);
                int categoryId = frm.getCategoryId();
                int subcategoryId = frm.getSubcategoryId();
                String name = vobj.replacedesc(frm.getName());
                String description = vobj.replacedesc(frm.getCdescription());
                int repeatdp = frm.getRepeatdp();
                int schedulecb = frm.getSchedulecb();
                String[] notificationdp = frm.getNotificationdp();
                String notification = vobj.replacename(makeCommaDelimString(notificationdp));
                String schedulevalue = "";
                if(repeatdp == 4)
                {
                      if (request.getSession().getAttribute("MDLIST") != null) {
                       ArrayList list = (ArrayList)request.getSession().getAttribute("MDLIST");
                       schedulevalue = wellnessfb.getValFromList(list);
                       request.getSession().removeAttribute("MDLIST");
                      }
                }
                else  if(repeatdp == 1)
                {
                    schedulevalue = frm.getSchedulevalue();
                }
                else
                {
                    schedulevalue = frm.getSchedulevalue();
                }
                int status = 1;
                String ipAddrStr = request.getRemoteAddr();
                String iplocal = wellnessfb.getLocalIp();
                int ck = wellnessfb.checkDuplicacysubcategory(categoryId, name,subcategoryId);
                if(ck == 1)
                {
                    saveToken(request);
                    frm.setName(name);
                    frm.setCdescription(description);
                    frm.setCategorycode(wellnessfb.changeNum(categoryId, 3));
                    frm.setCategoryname(wellnessfb.getcategoryname(categoryId));
                    request.setAttribute("MESSAGE", "SubCategory already exists");
                    return mapping.findForward("add_subcategory");
                }

                WellnessfbInfo info = new WellnessfbInfo(categoryId, subcategoryId, name, status, uId, description, repeatdp, notification, schedulevalue, schedulecb);
                if(subcategoryId <= 0)
                {
                    int cc = wellnessfb.createsubCategory(info);
                    if(cc > 0)
                    {
                       wellnessfb.createHistoryAccess(null, uId, ipAddrStr, iplocal, "Data Added", 69, cc);
                       request.setAttribute("MESSAGE", "Data added successfully.");
                    }
                    request.setAttribute("SUBCATEGORYWFSAVEMODEL", "yes");
                    frm.setCategoryId(categoryId);
                    frm.setSubcategoryId(cc);
                    ArrayList list  = wellnessfb.getsubcategoryDetailByIdforDetail(categoryId);
                    request.setAttribute("SUBCATEGORYWF_LIST", list);
                    WellnessfbInfo infosc = wellnessfb.getcategorystatus(categoryId);
                    request.setAttribute("CATEGORY_STATUS", infosc);
                    return mapping.findForward("view_subcategory");
                }
                else
                {
                    wellnessfb.updatesubCategory(info);
                    wellnessfb.createHistoryAccess(null, uId, ipAddrStr, iplocal, "Data Updated", 69, categoryId);
                    request.setAttribute("MESSAGE", "Data updated successfully.");
                }          
                    ArrayList list  = wellnessfb.getsubcategoryDetailByIdforDetail(categoryId);
                    request.setAttribute("SUBCATEGORYWF_LIST", list);
                    WellnessfbInfo infosf = wellnessfb.getcategorystatus(categoryId);
                    request.setAttribute("CATEGORY_STATUS", infosf);
                    return mapping.findForward("view_subcategory");
            }
        }
        else if(frm.getDodeletesubcategory()!= null && frm.getDodeletesubcategory().equals("yes"))
        {
            frm.setDodeletesubcategory("no");
            print(this,"getDodeletesubcategory block.");
            int categoryId = frm.getCategoryId();
            frm.setCategoryId(categoryId);
            int subcategoryId = frm.getSubcategoryId();
            frm.setSubcategoryId(0);
            int substatus = frm.getSubstatus();
            frm.setSubstatus(0);
            String ipAddrStr = request.getRemoteAddr();
            String iplocal = wellnessfb.getLocalIp();
            wellnessfb.deletesubcategory(subcategoryId, uId, substatus, ipAddrStr, iplocal);
            ArrayList list  = wellnessfb.getsubcategoryDetailByIdforDetail(categoryId);
            request.setAttribute("SUBCATEGORYWF_LIST", list);
            WellnessfbInfo info = wellnessfb.getcategorystatus(categoryId);
            request.setAttribute("CATEGORY_STATUS", info);
            return mapping.findForward("view_subcategory");
        }
        else if(frm.getDoIndexQuestion()!= null && frm.getDoIndexQuestion().equals("yes"))
        {
            frm.setDoIndexQuestion("no");
            print(this,"getDoIndexQuestion block.");
            int categoryId = frm.getCategoryId();
            frm.setCategoryId(categoryId);
            Collection subcategorys = wellnessfb.getSubcategory(categoryId);
            frm.setSubcategorys(subcategorys);
            int subcategoryIdIndex = frm.getSubcategoryIdIndex();
            frm.setSubcategoryIdIndex(subcategoryIdIndex);
            
            ArrayList list  = wellnessfb.getquestionslistByIdandIndexforDetail(categoryId,subcategoryIdIndex);
            request.setAttribute("QUESTION_LIST", list);

            WellnessfbInfo info = wellnessfb.getcategorystatus(categoryId);
            request.setAttribute("CATEGORY_STATUS", info);

            return mapping.findForward("view_question");
        }
        else if (frm.getDoAddQuestion()!= null && frm.getDoAddQuestion().equals("yes"))
        {
            frm.setDoAddQuestion("no");
            print(this, " getDoAddQuestion block :: ");
            frm.setStatus(1);
            int categoryId = frm.getCategoryId();
            frm.setCategoryId(categoryId);
            int subcategoryId = frm.getSubcategoryId();
            frm.setSubcategoryId(subcategoryId);
            frm.setQuestionId(-1);
            Collection subcategorys = wellnessfb.getSubcategory(categoryId);
            frm.setSubcategorys(subcategorys);
            frm.setEsubcategoryId(frm.getEsubcategoryId());
            ArrayList assettypelist = wellnessfb.getAssettype();
            request.setAttribute("ASSETTYPELIST", assettypelist);
            saveToken(request);
            frm.setCategoryname(wellnessfb.getcategoryname(categoryId));
            frm.setCategorycode(wellnessfb.changeNum(categoryId, 3));
            return mapping.findForward("add_question");
        }
        else if(frm.getDoModifyquestion()!= null && frm.getDoModifyquestion().equals("yes"))
        {
             frm.setDoModifyquestion("no");
             print(this, " getDoModifyquestion block :: ");
            int categoryId = frm.getCategoryId();
            frm.setCategoryId(categoryId);
            int subcategoryId = frm.getSubcategoryId();
            frm.setSubcategoryId(subcategoryId);
            int questionId = frm.getQuestionId();
            frm.setQuestionId(questionId);
            frm.setCategorycode(wellnessfb.changeNum(categoryId, 3));
            frm.setCategoryname(wellnessfb.getcategoryname(categoryId));
            WellnessfbInfo info = wellnessfb.getquestionDetailById(questionId);
            if(info != null)
            {                
                frm.setQuestioncode(wellnessfb.changeNum(questionId, 3));
                Collection subcategorys = wellnessfb.getSubcategory(categoryId);
                frm.setSubcategorys(subcategorys);
                frm.setEsubcategoryId(info.getSubcategoryId());
                ArrayList assettypelist = wellnessfb.getAssettype();
                request.setAttribute("ASSETTYPELIST", assettypelist);
                frm.setQuestion(info.getQuestion());

                frm.setAnswertypeId(info.getAnswertypeId());
                if(info.getAnswertypeId() == 2)
                {
                    frm.setAddvalue(info.getAddvalues());
                }
                frm.setRecipientId(info.getRecipientId());
                frm.setStatus(info.getStatus());
                frm.setCdescription(info.getDescription());
                request.setAttribute("ASSETTYPECB", info.getAssettypeids());
                request.setAttribute("RESPONDERCB", info.getResponderids());

            }
            if (request.getAttribute("QUESTIONSAVEMODEL") != null) {

                    request.removeAttribute("QUESTIONSAVEMODEL");
                }
            saveToken(request);
            return mapping.findForward("add_question");
        }
        else if(frm.getDoSaveQuestion()!= null && frm.getDoSaveQuestion().equals("yes"))
        {
             frm.setDoSaveQuestion("no");
            print(this,"getDoSaveQuestion block.");
            if(isTokenValid(request))
            {
                resetToken(request);
                int categoryId = frm.getCategoryId();
                int subcategoryId = frm.getSubcategoryId();
                String description = vobj.replacedesc(frm.getCdescription());
                int esubcategoryId = frm.getEsubcategoryId();
                String question = frm.getQuestion();
                String addvalues = frm.getAddvalue();
                int answertypeId = frm.getAnswertypeId();
                int recipientId = frm.getRecipientId();
                String [] assettype = frm.getAssettype();
                String [] responder = frm.getResponder();
                String assettypes = vobj.replacename(makeCommaDelimString(assettype));
                String responders = vobj.replacename(makeCommaDelimString(responder));
                int questionId = frm.getQuestionId();
                
                int status = 1;
                String ipAddrStr = request.getRemoteAddr();
                String iplocal = wellnessfb.getLocalIp();
                int ck = wellnessfb.checkDuplicacyquestion(categoryId, question,esubcategoryId,questionId);
                if(ck == 1)
                {
                    ArrayList assettypelist = wellnessfb.getAssettype();
                    request.setAttribute("ASSETTYPELIST", assettypelist);
                    frm.setCategoryId(categoryId);
                    frm.setSubcategoryId(subcategoryId);
                    frm.setQuestionId(-1);
                    Collection subcategorys = wellnessfb.getSubcategory(categoryId);
                    frm.setSubcategorys(subcategorys);
                    frm.setEsubcategoryId(frm.getEsubcategoryId());

                    saveToken(request);
                    request.setAttribute("MESSAGE", "Question already exists");
                    return mapping.findForward("add_question");
                }
                WellnessfbInfo info = new WellnessfbInfo(categoryId, esubcategoryId, question, addvalues, answertypeId, assettypes, responders,
                        status, uId, description,questionId,recipientId);
                if(questionId <= 0)
                {
                    int cc = wellnessfb.createquestion(info);
                    if(cc > 0)
                    {                        
                       wellnessfb.createHistoryAccess(null, uId, ipAddrStr, iplocal, "Data Added", 69, cc);
                       request.setAttribute("MESSAGE", "Data added successfully.");
                    }
                    request.setAttribute("QUESTIONSAVEMODEL", "yes");
                    Collection subcategorys = wellnessfb.getSubcategory(categoryId);
                    frm.setSubcategorys(subcategorys);
                    frm.setSubcategoryIdIndex(esubcategoryId);
                    ArrayList list  = wellnessfb.getquestionslistByIdandIndexforDetail(categoryId,esubcategoryId);
                    request.setAttribute("QUESTION_LIST", list);
                    WellnessfbInfo infoq = wellnessfb.getcategorystatus(categoryId);
                    request.setAttribute("CATEGORY_STATUS", infoq);
                    return mapping.findForward("view_question");
                }
                else
                {
                    wellnessfb.updatequestion(info);
                    wellnessfb.createHistoryAccess(null, uId, ipAddrStr, iplocal, "Data Updated", 69, categoryId);
                    request.setAttribute("MESSAGE", "Data updated successfully.");
              
                    Collection subcategorys = wellnessfb.getSubcategory(categoryId);
                    frm.setSubcategorys(subcategorys);
                    frm.setSubcategoryIdIndex(esubcategoryId);
                    ArrayList list  = wellnessfb.getquestionslistByIdandIndexforDetail(categoryId,esubcategoryId);
                    request.setAttribute("QUESTION_LIST", list);
                    WellnessfbInfo infoq = wellnessfb.getcategorystatus(categoryId);
                    request.setAttribute("CATEGORY_STATUS", infoq);
                    return mapping.findForward("view_question");
                }  
            }
        }
        else if(frm.getDodeleteQuestion()!= null && frm.getDodeleteQuestion().equals("yes"))
        {
            frm.setDodeleteQuestion("no");
            print(this,"getDodeleteQuestion block.");
            int categoryId = frm.getCategoryId();
            frm.setCategoryId(categoryId);
            Collection subcategorys = wellnessfb.getSubcategory(categoryId);
            frm.setSubcategorys(subcategorys);
            int subcategoryIdIndex = frm.getSubcategoryIdIndex();
            frm.setSubcategoryIdIndex(subcategoryIdIndex);
            int questionId = frm.getQuestionId();
            frm.setQuestionId(0);
            
            int substatus = frm.getSubstatus();
            frm.setSubstatus(0);
            String ipAddrStr = request.getRemoteAddr();
            String iplocal = wellnessfb.getLocalIp();
            wellnessfb.deletequestion(questionId, uId, substatus, ipAddrStr, iplocal);
            ArrayList list  = wellnessfb.getquestionslistByIdandIndexforDetail(categoryId,subcategoryIdIndex);
            request.setAttribute("QUESTION_LIST", list);
            WellnessfbInfo info = wellnessfb.getcategorystatus(categoryId);
            request.setAttribute("CATEGORY_STATUS", info);
            return mapping.findForward("view_question");
        }
        else if(frm.getDoCancel() != null && frm.getDoCancel().equals("yes"))
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
            ArrayList wellnessfbList = wellnessfb.getWellnessfbByName(search, next, count);
            int cnt = 0;
            if(wellnessfbList.size() > 0)
            {
                WellnessfbInfo cinfo = (WellnessfbInfo) wellnessfbList.get(wellnessfbList.size() - 1);
                cnt = cinfo.getCategoryId();
                wellnessfbList.remove(wellnessfbList.size() - 1);
            }
            request.getSession().setAttribute("CATEGORYWF_LIST", wellnessfbList);
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
            ArrayList wellnessfbList = wellnessfb.getWellnessfbByName(search, 0, count);
            int cnt = 0;
            if(wellnessfbList.size() > 0)
            {
                WellnessfbInfo cinfo = (WellnessfbInfo) wellnessfbList.get(wellnessfbList.size() - 1);
                cnt = cinfo.getCategoryId();
                wellnessfbList.remove(wellnessfbList.size() - 1);
            }
            request.getSession().setAttribute("CATEGORYWF_LIST", wellnessfbList);
            request.getSession().setAttribute("COUNT_LIST", cnt+"");
            request.getSession().setAttribute("NEXT", "0");
            request.getSession().setAttribute("NEXTVALUE", "1");
        }
        return mapping.findForward("display");
    }    
}