package com.web.jxp.managetraining;

import com.web.jxp.candidate.Candidate;
import com.web.jxp.candidate.CandidateInfo;
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
import java.util.Date;
import java.util.Stack;
import org.apache.struts.upload.FormFile;

public class ManagetrainingAction extends Action {

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        ManagetrainingForm frm = (ManagetrainingForm) form;
        Managetraining managetraining = new Managetraining();
        Candidate candidate = new Candidate();
        String search = frm.getSearch() != null && !frm.getSearch().equals("") ? frm.getSearch() : "";
        frm.setSearch(search);
        int uId = 0, allclient = 0;
        String permission = "N", cids = "", assetids = "", username = "";
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
                username = uInfo.getName();
            }
        }
        Collection clients = managetraining.getClients(cids, allclient, permission);
        frm.setClients(clients);
        int clientIdIndex = frm.getClientIdIndex();
        frm.setClientIdIndex(clientIdIndex);

        Collection assets = managetraining.getClientAsset(clientIdIndex, assetids, allclient, permission);
        frm.setAssets(assets);
        int assetIdIndex = frm.getAssetIdIndex();
        frm.setAssetIdIndex(assetIdIndex);
        
        Collection positions = managetraining.getPositions(assetIdIndex);
        frm.setPositions(positions);
        int positionIdIndex = frm.getPositionIdIndex();
        frm.setPositionIdIndex(positionIdIndex);
        
        int mode = frm.getMode() > 0 ? frm.getMode() : 1;
        frm.setMode(mode);
        Collection categories = managetraining.getCategories(assetIdIndex);
        frm.setCategories(categories); 
        int categoryIdIndex = frm.getCategoryIdIndex();
        frm.setCategoryIdIndex(categoryIdIndex);
        Collection subcategories = managetraining.getSubCategories(assetIdIndex, categoryIdIndex);
        frm.setSubcategories(subcategories); 
        int subcategoryIdIndex = frm.getSubcategoryIdIndex();
        frm.setSubcategoryIdIndex(subcategoryIdIndex);
        
        int check_user = managetraining.checkUserSession(request, 67, permission);
        if (check_user == -1) {
            return mapping.findForward("default");
        } else if (check_user == -2) {
            String authmess = managetraining.getMainPath("user_auth");
            authmess = authmess.replaceAll("__MODULE__", "Manage Training Module");
            request.setAttribute("AUTHMESSAGE", authmess);
            return mapping.findForward("auth");
        } 
        if (frm.getDoSearchAsset() != null && frm.getDoSearchAsset().equals("yes")) 
        {
            request.removeAttribute("SENDCOURSEEMAIL");
            frm.setDoSearchAsset("no");
            ArrayList managetrainingList = managetraining.getManagetrainingByName(clientIdIndex, assetIdIndex, positionIdIndex, mode, search, categoryIdIndex, subcategoryIdIndex);            
            request.getSession().setAttribute("MANAGETRAINING_LIST", managetrainingList);
            int arr[] = managetraining.getCounts(assetIdIndex, 1); 
            request.getSession().setAttribute("ARR_COUNT", arr);
            if(mode == 1)
                return mapping.findForward("list1");
            else
                return mapping.findForward("list2");
        }
        else if (frm.getDoSearch() != null && frm.getDoSearch().equals("yes")) 
        {
            request.removeAttribute("SENDCOURSEEMAIL");
            frm.setDoSearch("no");
            ArrayList managetrainingList = managetraining.getManagetrainingByName(clientIdIndex, assetIdIndex, positionIdIndex, mode, search, categoryIdIndex, subcategoryIdIndex);            
            request.getSession().setAttribute("MANAGETRAINING_LIST", managetrainingList);
            int arr[] = managetraining.getCounts(assetIdIndex, 1); 
            if(mode == 1)
                return mapping.findForward("list1");
            else
                return mapping.findForward("list2");
        }
        else if(frm.getDoAssign1() != null && frm.getDoAssign1().equals("yes"))
        {
            request.removeAttribute("SENDCOURSEEMAIL");
            frm.setDoAssign1("no");
            int crewrotationId = frm.getCrewrotationId();
            frm.setCrewrotationId(crewrotationId);
            int candidateId = frm.getCandidateId();
            frm.setCandidateId(candidateId);
            int positionId2 = frm.getPositionId();            
            ManagetrainingInfo info = managetraining.getBasicDetail(candidateId);
            int positionId = 0;
            if(info != null)
                positionId = info.getPositionId();
            
            frm.setPositionId(positionId);
            int cid = frm.getCategoryIdDetail();
            int scid = frm.getSubcategoryIdDetail();
            int ftype = frm.getFtype(); 
            String searchd = frm.getSearchdetail() != null ? frm.getSearchdetail() : "";
            frm.setCategoryIdDetail(cid);
            frm.setSubcategoryIdDetail(scid);
            frm.setFtype(ftype);
            frm.setSearchdetail(searchd);
            int statusIndex = frm.getStatusIndex();
            frm.setStatusIndex(statusIndex);
            
            ArrayList assignlist1 = managetraining.getListAssign1(clientIdIndex, assetIdIndex, positionId2, 
                candidateId, cid, scid, ftype, searchd, statusIndex);            
            request.setAttribute("ASSIGNLIST1", assignlist1);
            request.setAttribute("MANAGETRAINING_INFO1", info);   
            request.setAttribute("MANAGETRAINING_PID", ""+positionId2);
            return mapping.findForward("assign1");
        }
        else if(frm.getDoAssign2() != null && frm.getDoAssign2().equals("yes"))
        {
            request.removeAttribute("SENDCOURSEEMAIL");
            frm.setDoAssign2("no");
            int courseId = frm.getCourseId();  
            frm.setCourseId(courseId);
            int ftype = frm.getFtype(); 
            String searchd = frm.getSearchdetail() != null ? frm.getSearchdetail() : "";
            frm.setFtype(ftype);
            frm.setSearchdetail(searchd);
            int statusIndex = frm.getStatusIndex();
            frm.setStatusIndex(statusIndex);
            int positionId2Index = frm.getPositionId2Index();
            frm.setPositionId2Index(positionId2Index);
            
            ManagetrainingInfo info = managetraining.getCourseDetail(courseId, assetIdIndex);
            ArrayList list = managetraining.getListAssign2(clientIdIndex, assetIdIndex, courseId, ftype, search, statusIndex, positionId2Index);
            request.setAttribute("MANAGETRAINING_INFO2", info);
            request.setAttribute("ASSIGNLIST1", list);
            return mapping.findForward("assign2");
        }
        else if (frm.getDoCancel() != null && frm.getDoCancel().equals("yes")) 
        {
            request.removeAttribute("SENDCOURSEEMAIL");
            frm.setDoCancel("no");
            print(this, "doCancel block");
            ArrayList managetrainingList = managetraining.getManagetrainingByName(clientIdIndex, assetIdIndex, positionIdIndex, mode, search, categoryIdIndex, subcategoryIdIndex);            
            request.getSession().setAttribute("MANAGETRAINING_LIST", managetrainingList);            
            if(mode == 1)
                return mapping.findForward("list1");
            else
                return mapping.findForward("list2");
        }
        else if(frm.getDoSavepersonal() != null && frm.getDoSavepersonal().equals("yes"))
        {
            request.removeAttribute("SENDCOURSEEMAIL");
            frm.setDoSavepersonal("no");
            print(this, "getDoSavepersonal block");
            int typefrom = frm.getTypefrom();
            int clientassetId = frm.getAssetIdIndex();
            int candidateId = frm.getMcrewrotationId();
            int mcourseId = frm.getMcourseId();
            int mpositionId = frm.getMpositionId();
            String completeby = frm.getCompleteby();
            String alink = frm.getAlink();
            int cbtodate = frm.getCbtodate();
            int stype = frm.getStype();
            String add_candidate_file = managetraining.getMainPath("add_candidate_file");
            String foldername = managetraining.createFolder(add_candidate_file);
            Date now = new Date();
            String fn = String.valueOf(now.getTime());
            String fileName1 = "";
            FormFile filename = frm.getAttachment();
            if (filename != null && filename.getFileSize() > 0) {
                fileName1 = managetraining.uploadFile(1, "", filename, fn + "_1", add_candidate_file, foldername);
            }
            String startdate = "";
            String enddate = "";
            String remarks = "";
            if(stype == 3)
            {
                startdate = frm.getFromdate();
                enddate = frm.getTodate();
                if(cbtodate == 1)
                {
                    enddate = "";
                }
                remarks = frm.getRemarks() != null ? frm.getRemarks() : "";
            }
            managetraining.createCourse(clientassetId, candidateId, mcourseId, mpositionId, stype, completeby, 
                alink, startdate, enddate, cbtodate, fileName1, uId, remarks);
            if(stype == 2)
            {
                ManagetrainingInfo cinfo = new ManagetrainingInfo( candidateId, mcourseId, alink, completeby);
                request.getSession().setAttribute("ASSIGNMAILINFO", cinfo);
                request.setAttribute("SENDCOURSEEMAILASSIGN", "yes");
            }
            if(stype == 3)
            {
                CandidateInfo cinfo = new CandidateInfo(0, 0, frm.getCoursenameId(), "", 0, "", "",
                    "", startdate, "", enddate, "", 0, 1, fileName1);            
                candidate.inserttrainingCertificate(cinfo, frm.getCandidateId(), uId, 2);
            }
            int arr[] = managetraining.getCounts(assetIdIndex, 1); 
            request.getSession().setAttribute("ARR_COUNT", arr);
            if(typefrom == 1)
            {
                request.setAttribute("SENDCOURSEEMAIL", "yes");
                int crewrotationId = frm.getCrewrotationId();
                frm.setCrewrotationId(crewrotationId);
                int positionId2 = frm.getMpositionId();
                ManagetrainingInfo info = managetraining.getBasicDetail(candidateId);
                if(info != null)
                {
                    if(positionId2== info.getPositionId())
                        positionId2 = info.getPositionId();
                    else if(positionId2 == info.getPositionId2())
                        positionId2 = info.getPositionId2();
                }

                frm.setPositionId(positionId2); 
                int cid = frm.getCategoryIdDetail();
                int scid = frm.getSubcategoryIdDetail();
                int ftype = frm.getFtype(); 
                String searchd = frm.getSearchdetail() != null ? frm.getSearchdetail() : "";
                frm.setCategoryIdDetail(cid);
                frm.setSubcategoryIdDetail(scid);
                frm.setFtype(ftype);
                frm.setSearchdetail(searchd);
                int statusIndex = frm.getStatusIndex();
                frm.setStatusIndex(statusIndex);
                
                ArrayList assignlist1 = managetraining.getListAssign1(clientIdIndex, assetIdIndex, positionId2, 
                    candidateId, cid, scid, ftype, searchd, statusIndex);            
                request.setAttribute("ASSIGNLIST1", assignlist1);
                request.setAttribute("MANAGETRAINING_INFO1", info); 
                request.setAttribute("MANAGETRAINING_PID", ""+positionId2);
                return mapping.findForward("assign1");
            }
            else 
            {
                request.setAttribute("SENDCOURSEEMAIL", "yes");
                int courseId = frm.getCourseId();  
                frm.setCourseId(courseId);
                int ftype = frm.getFtype(); 
                String searchd = frm.getSearchdetail() != null ? frm.getSearchdetail() : "";
                frm.setFtype(ftype);
                frm.setSearchdetail(searchd);
                int statusIndex = frm.getStatusIndex();
                frm.setStatusIndex(statusIndex);
                int positionId2Index = frm.getPositionId2Index();
                frm.setPositionId2Index(positionId2Index);
                ManagetrainingInfo info = managetraining.getCourseDetail(courseId, assetIdIndex);
                ArrayList list = managetraining.getListAssign2(clientIdIndex, assetIdIndex, courseId, ftype, search, statusIndex, positionId2Index);
                request.setAttribute("MANAGETRAINING_INFO2", info);
                request.setAttribute("ASSIGNLIST1", list);
                return mapping.findForward("assign2");
            }
        }
        else if(frm.getDoSavepersonalassign()!= null && frm.getDoSavepersonalassign().equals("yes"))
        {
            request.removeAttribute("SENDCOURSEEMAIL");
            frm.setDoSavepersonalassign("no");
            print(this, "getDoSavepersonalassign block");
            
            int mpositionId = frm.getMpositionId();
            String mcrids = frm.getMcrids();
            int clientassetId = frm.getAssetIdIndex();
            int candidateId = frm.getCandidateId();
            String completeby = frm.getCompleteby();
            String alink = frm.getAlink();
            
            managetraining.createCourseMultiple(clientassetId, candidateId, mcrids, mpositionId,completeby, alink, uId);
            
            int arr[] = managetraining.getCounts(assetIdIndex, 1); 
            request.getSession().setAttribute("ARR_COUNT", arr);
                frm.setCandidateId(candidateId);
                ManagetrainingInfo info = managetraining.getBasicDetail(candidateId);
                int positionId = 0;
                if(info != null)
                    positionId = info.getPositionId();

                frm.setPositionId(positionId); 
                int cid = frm.getCategoryIdDetail();
                int scid = frm.getSubcategoryIdDetail();
                int ftype = frm.getFtype(); 
                String searchd = frm.getSearchdetail() != null ? frm.getSearchdetail() : "";
                frm.setCategoryIdDetail(cid);
                frm.setSubcategoryIdDetail(scid);
                frm.setFtype(ftype);
                frm.setSearchdetail(searchd);
                int statusIndex = frm.getStatusIndex();
                frm.setStatusIndex(statusIndex);

                ArrayList assignlist1 = managetraining.getListAssign1(clientIdIndex, assetIdIndex, positionId, 
                    candidateId, cid, scid, ftype, searchd, statusIndex);            
                request.setAttribute("ASSIGNLIST1", assignlist1);
                request.setAttribute("MANAGETRAINING_INFO1", info);            
                return mapping.findForward("assign1");        
        }
        else if(frm.getDoSavecourseassign()!= null && frm.getDoSavecourseassign().equals("yes"))
        {
            request.removeAttribute("SENDCOURSEEMAIL");
            frm.setDoSavecourseassign("no");
            print(this, "getDoSavepersonalassign block");
            
            String candidateIds = frm.getMcrids();
            int clientassetId = frm.getAssetIdIndex();
            int mcourseId = frm.getMcourseId();
            String completeby = frm.getCompleteby();
            String alink = frm.getAlink();
            
            managetraining.createPersonalMultiple(clientassetId, candidateIds, mcourseId, completeby, alink, uId);
            
            int arr[] = managetraining.getCounts(assetIdIndex, 1); 
            request.getSession().setAttribute("ARR_COUNT", arr);
            int courseId = frm.getCourseId();  
            frm.setCourseId(courseId);
            int ftype = frm.getFtype(); 
            String searchd = frm.getSearchdetail() != null ? frm.getSearchdetail() : "";
            frm.setFtype(ftype);
            frm.setSearchdetail(searchd);
            int statusIndex = frm.getStatusIndex();
            frm.setStatusIndex(statusIndex);
            int positionId2Index = frm.getPositionId2Index();
            frm.setPositionId2Index(positionId2Index);
            ManagetrainingInfo info = managetraining.getCourseDetail(courseId, assetIdIndex);
            ArrayList list = managetraining.getListAssign2(clientIdIndex, assetIdIndex, courseId, ftype, search, statusIndex, positionId2Index);
            request.setAttribute("MANAGETRAINING_INFO2", info);
            request.setAttribute("ASSIGNLIST1", list);
            return mapping.findForward("assign2");        
        }
        else if(frm.getDoMail()!= null && frm.getDoMail().equals("yes"))
        {
            request.removeAttribute("SENDCOURSEEMAIL");
            frm.setDoMail("no");
            print(this, "getDoMail block");
            int typefrom = frm.getTypefrom1();
            ManagetrainingInfo ainfo = null;
            if (request.getSession().getAttribute("ASSIGNMAILINFO") != null) {
                ainfo = (ManagetrainingInfo) request.getSession().getAttribute("ASSIGNMAILINFO");
            }
            String fromval = frm.getFromval();
            String toval = frm.getToval();
            String ccval = frm.getCcval();
            String bccval = frm.getBccval();
            String subject = frm.getSubject();
            String description = frm.getDescription(); 
            ArrayList list = new ArrayList();
            list = managetraining.getcourseattachment(ainfo.getCourseId());
            int size = list.size();
            String view_trainingfiles = managetraining.getMainPath("view_trainingfiles");
            String add_trainingfiles = managetraining.getMainPath("add_trainingfiles");
            String arr1[] = new String[size];
            String arr2[] = new String[size];
            String fn_mail = "";
            for (int i = 0; i < size; i++) {
                ManagetrainingInfo fninfo = (ManagetrainingInfo) list.get(i);
                String fn = fninfo.getFilename();
                if (fn != null && !fn.equals("")) {
                    String ext = "";
                    if (fn.contains(".")) {
                        ext = fn.substring(fn.lastIndexOf("."));
                    }
                    arr1[i] = add_trainingfiles + fn;
                    arr2[i] = "File " + (i + 1)+ext;
                    if (fn_mail.equals("")) {
                        fn_mail = view_trainingfiles + fn;
                    } else {
                        fn_mail += ", " + view_trainingfiles + fn;
                    }
                }
            }
            
            ManagetrainingInfo pinfo = managetraining.getCandidatemailId(ainfo.getCrewrotationId());
            
            managetraining.sendMail(fromval, toval, ccval, bccval, subject, description, arr1,arr2,fn_mail,pinfo.getName(),username);
            
            if(typefrom == 1)
            {
                int crewrotationId = frm.getCrewrotationId();
                frm.setCrewrotationId(crewrotationId);
                ManagetrainingInfo info = managetraining.getBasicDetail(crewrotationId);
                int positionId = 0;
                if(info != null)
                    positionId = info.getPositionId();

                frm.setPositionId(positionId); 
                int cid = frm.getCategoryIdDetail();
                int scid = frm.getSubcategoryIdDetail();
                int ftype = frm.getFtype(); 
                String searchd = frm.getSearchdetail() != null ? frm.getSearchdetail() : "";
                frm.setCategoryIdDetail(cid);
                frm.setSubcategoryIdDetail(scid);
                frm.setFtype(ftype);
                frm.setSearchdetail(searchd);
                int statusIndex = frm.getStatusIndex();
                frm.setStatusIndex(statusIndex);

                ArrayList assignlist1 = managetraining.getListAssign1(clientIdIndex, assetIdIndex, positionId, 
                    crewrotationId, cid, scid, ftype, searchd, statusIndex);            
                request.setAttribute("ASSIGNLIST1", assignlist1);
                request.setAttribute("MANAGETRAINING_INFO1", info);            
                return mapping.findForward("assign1");
            }
            else 
            {
                int courseId = frm.getCourseId();  
                frm.setCourseId(courseId);
                int ftype = frm.getFtype(); 
                String searchd = frm.getSearchdetail() != null ? frm.getSearchdetail() : "";
                frm.setFtype(ftype);
                frm.setSearchdetail(searchd);
                int statusIndex = frm.getStatusIndex();
                frm.setStatusIndex(statusIndex);
                int positionId2Index = frm.getPositionId2Index();
                frm.setPositionId2Index(positionId2Index);
                ManagetrainingInfo info = managetraining.getCourseDetail(courseId, assetIdIndex);
                ArrayList list1 = managetraining.getListAssign2(clientIdIndex, assetIdIndex, courseId, ftype, search, statusIndex, positionId2Index);
                request.setAttribute("MANAGETRAINING_INFO2", info);
                request.setAttribute("ASSIGNLIST1", list1);
                return mapping.findForward("assign2");
            }
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
            request.getSession().removeAttribute("MANAGETRAINING_LIST");
            request.getSession().removeAttribute("ARR_COUNT");
            return mapping.findForward("list1");
        }        
    }
}
