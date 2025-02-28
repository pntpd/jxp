package com.web.jxp.tracker;
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

public class TrackerAction extends Action {

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        TrackerForm frm = (TrackerForm) form;
        Tracker tracker = new Tracker();
        String search = frm.getSearch() != null && !frm.getSearch().equals("") ? frm.getSearch() : "";
        frm.setSearch(search);
        int uId = 0, allclient = 0, cassessor = 0;
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
                cassessor = uInfo.getCassessor();
            }
        }         
        Collection clients = tracker.getClients(cids, allclient, permission);
        frm.setClients(clients);
        int clientIdIndex = frm.getClientIdIndex();
        frm.setClientIdIndex(clientIdIndex);

        Collection assets = tracker.getClientAsset(clientIdIndex, assetids, allclient, permission);
        frm.setAssets(assets);
        int assetIdIndex = frm.getAssetIdIndex();
        frm.setAssetIdIndex(assetIdIndex);
        
        Collection positions = tracker.getPositions(assetIdIndex);
        frm.setPositions(positions);
        int positionIdIndex = frm.getPositionIdIndex();
        frm.setPositionIdIndex(positionIdIndex);
        
        int mode = frm.getMode() > 0 ? frm.getMode() : 1;
        frm.setMode(mode);
        Collection pcodes = tracker.getPcodes(assetIdIndex);
        frm.setPcodes(pcodes);
        int pcodeIdIndex = frm.getPcodeIdIndex();
        frm.setPcodeIdIndex(pcodeIdIndex);
        
        int check_user = tracker.checkUserSession(request, 90, permission);
        if (check_user == -1) {
            return mapping.findForward("default");
        } else if (check_user == -2) {
            String authmess = tracker.getMainPath("user_auth");
            authmess = authmess.replaceAll("__MODULE__", "Competency Tracker");
            request.setAttribute("AUTHMESSAGE", authmess);
            return mapping.findForward("auth");
        } 
        if (frm.getDoSearchAsset() != null && frm.getDoSearchAsset().equals("yes")) 
        {
            frm.setDoSearchAsset("no");
            ArrayList trackerList = tracker.getTrackerByName(assetIdIndex, positionIdIndex, mode, search, pcodeIdIndex);            
            request.getSession().setAttribute("TRACKER_LIST", trackerList);
            int arr[] = tracker.getCounts(assetIdIndex, -1, -1, 1); 
            request.getSession().setAttribute("ARR_COUNT", arr);
            if(mode == 1)
                return mapping.findForward("list1");
            else
                return mapping.findForward("list2");
        }
        else if (frm.getDoSearch() != null && frm.getDoSearch().equals("yes")) 
        {
            frm.setDoSearch("no");
            ArrayList trackerList = tracker.getTrackerByName(assetIdIndex, positionIdIndex, mode, search, pcodeIdIndex);            
            request.getSession().setAttribute("TRACKER_LIST", trackerList);
            int arr[] = tracker.getCounts(assetIdIndex, -1, -1, 1); 
            request.getSession().setAttribute("ARR_COUNT", arr);
            if(mode == 1)
                return mapping.findForward("list1");
            else
                return mapping.findForward("list2");
        }
        else if(frm.getDoAssign1() != null && frm.getDoAssign1().equals("yes"))
        {
            frm.setDoAssign1("no");
            int candidateId = frm.getCandidateId();
            frm.setCandidateId(candidateId);
            int crewrotationId = frm.getCrewrotationId();
            frm.setCrewrotationId(crewrotationId);
            int positionId2 = frm.getPositionId();  
            TrackerInfo info = tracker.getBasicDetail(candidateId);
            int positionId = 0;
            if(info != null)
                positionId = info.getPositionId();            
            frm.setPositionId(positionId);             
            Collection passessmenttypes = tracker.getAssessmenttypes(assetIdIndex, positionId2);
            frm.setPassessmenttypes(passessmenttypes); 
            int passessmenttypeId = frm.getPassessmenttypeId();
            frm.setPassessmenttypeId(passessmenttypeId);
            Collection priorities = tracker.getPriorities(assetIdIndex, positionId2);
            frm.setPriorities(priorities); 
            int priorityId = frm.getPriorityId();
            frm.setPriorityId(priorityId);
            int ftype = frm.getFtype(); 
            frm.setFtype(ftype);
            String searchd = frm.getSearchdetail() != null ? frm.getSearchdetail() : "";
            frm.setSearchdetail(searchd);
            
            ArrayList assignlist1 = tracker.getListAssign1(assetIdIndex, positionId2, candidateId, passessmenttypeId, priorityId, ftype, searchd, cassessor, uId);
            request.getSession().setAttribute("ASSIGNLIST1", assignlist1);
            request.setAttribute("TRACKER_INFO1", info);         
            int arr[] = tracker.getCounts(assetIdIndex, candidateId, -1, 1); 
            request.getSession().setAttribute("ARR_COUNT", arr);
            request.setAttribute("TRACKER_PID", ""+positionId2);
            return mapping.findForward("assign1");
        }
        else if (frm.getDoCancel() != null && frm.getDoCancel().equals("yes")) 
        {
            frm.setDoCancel("no");
            print(this, "doCancel block");
            ArrayList trackerList = tracker.getTrackerByName(assetIdIndex, positionIdIndex, mode, search, pcodeIdIndex);            
            request.getSession().setAttribute("TRACKER_LIST", trackerList);            
            int crewrotationId = frm.getCrewrotationId();
            frm.setCrewrotationId(crewrotationId);
            TrackerInfo info = tracker.getBasicDetail(crewrotationId);
            int positionId = 0;
            if(info != null)
                positionId = info.getPositionId();                        
            frm.setPositionId(positionId);             
            Collection passessmenttypes = tracker.getAssessmenttypes(assetIdIndex, positionId);
            frm.setPassessmenttypes(passessmenttypes); 
            int passessmenttypeId = frm.getPassessmenttypeId();
            frm.setPassessmenttypeId(passessmenttypeId);
            Collection priorities = tracker.getPriorities(assetIdIndex, positionId);
            frm.setPriorities(priorities); 
            int priorityId = frm.getPriorityId();
            frm.setPriorityId(priorityId);
            int ftype = frm.getFtype(); 
            frm.setFtype(ftype);
            String searchd = frm.getSearchdetail() != null ? frm.getSearchdetail() : "";
            frm.setSearchdetail(searchd);
            ArrayList assignlist1 = tracker.getListAssign1(assetIdIndex, positionId, crewrotationId, passessmenttypeId, priorityId, ftype, searchd, cassessor, uId);
            if(mode == 1)   
            {
                request.getSession().setAttribute("ASSIGNLIST1", assignlist1);
                request.setAttribute("TRACKER_INFO1", info);         
                int arr[] = tracker.getCounts(assetIdIndex, 0, -1, 1); 
                request.getSession().setAttribute("ARR_COUNT", arr);
                return mapping.findForward("list1");
            }
            else 
            {
                int arr[] = tracker.getCounts(assetIdIndex, -1, -1, 1); 
                request.getSession().setAttribute("ARR_COUNT", arr);
                return mapping.findForward("list2");
            }
        }
        else if(frm.getDoSavepersonalassign() != null && frm.getDoSavepersonalassign().equals("yes"))
        {
            frm.setDoSavepersonalassign("no");
            print(this, "getDoSavepersonalassign block");
            int crewrotationId = frm.getCrewrotationId();
            frm.setCrewrotationId(crewrotationId);
            int typefrom = frm.getTypefrom(); 
            int ftype = frm.getFtype(); 
            frm.setFtype(ftype);
            int positionId2 = frm.getPositionId();
            int candidateId = frm.getCandidateId();
            frm.setCandidateId(candidateId);
            String searchd = frm.getSearchdetail() != null ? frm.getSearchdetail() : "";
            frm.setSearchdetail(searchd);
            
            String pcodeids = frm.getPcodeids() != null ? frm.getPcodeids() : "";
            int pcodeIdModal = frm.getPcodeIdModal();
            int crewrotationIdModal = frm.getCrewrotationIdModal();
            int candidateIdModal = frm.getCandidateIdModal();
            String completebydate1 = frm.getCompletebydate1() != null ? frm.getCompletebydate1() : "";  
            String completebydate2 = frm.getCompletebydate2() != null ? frm.getCompletebydate2() : ""; 
            int auserId = frm.getAuserId();
            if(typefrom == 1)
            {
                TrackerInfo info = tracker.getBasicDetail(candidateId);
                int positionId = 0;
                if(info != null)
                    positionId = info.getPositionId();            
                frm.setPositionId(positionId); 
                Collection passessmenttypes = tracker.getAssessmenttypes(assetIdIndex, positionId2);
                frm.setPassessmenttypes(passessmenttypes); 
                int passessmenttypeId = frm.getPassessmenttypeId();
                frm.setPassessmenttypeId(passessmenttypeId);
                Collection priorities = tracker.getPriorities(assetIdIndex, positionId2);
                frm.setPriorities(priorities); 
                int priorityId = frm.getPriorityId();
                frm.setPriorityId(priorityId);
                
                if(pcodeids.equals(""))
                    pcodeids = ""+pcodeIdModal;
                tracker.assignassessment(assetIdIndex, pcodeids, 2, completebydate1, completebydate2, auserId, uId, candidateId, username, positionId2);
                ArrayList assignlist1 = tracker.getListAssign1(assetIdIndex, positionId2, candidateId, passessmenttypeId, priorityId, ftype, searchd, cassessor, uId);            
                request.getSession().setAttribute("ASSIGNLIST1", assignlist1);
                request.setAttribute("TRACKER_INFO1", info);  
                int arr[] = tracker.getCounts(assetIdIndex,candidateId, -1, 1); 
                request.getSession().setAttribute("ARR_COUNT", arr);
                request.setAttribute("TRACKER_PID", ""+positionId2);
                return mapping.findForward("assign1");  
            }
            else
            {
                int pcodeId = frm.getPcodeId();
                frm.setPcodeId(pcodeId);
                Collection positionsassign2 = tracker.getPositionsAssign2(pcodeId, assetIdIndex); 
                frm.setPositions(positionsassign2); 
                int positionId2Index = frm.getPositionId2Index();
                frm.setPositionId2Index(positionId2Index);
                if(pcodeids.equals(""))
                    pcodeids = ""+pcodeIdModal;
                tracker.assignassessment2(assetIdIndex, positionId2, pcodeids, 2, completebydate1, completebydate2, auserId, uId, candidateId, username);
                
                TrackerInfo info = tracker.getBasicDetailAssign2(pcodeId, assetIdIndex);
                ArrayList assignlist2 = tracker.getListAssign2(assetIdIndex, pcodeId, ftype, searchd, positionId2Index, cassessor, uId);           
                request.getSession().setAttribute("ASSIGNLIST2", assignlist2);
                request.setAttribute("TRACKER_INFO2", info);   
                int arr[] = tracker.getCounts(assetIdIndex, -1, pcodeId, -1); 
                request.getSession().setAttribute("ARR_COUNT", arr);
                return mapping.findForward("assign2"); 
            }            
        }
        else if(frm.getDoAssign2() != null && frm.getDoAssign2().equals("yes"))
        {
            frm.setDoAssign2("no");
            int pcodeId = frm.getPcodeId();
            frm.setPcodeId(pcodeId);
            TrackerInfo info = tracker.getBasicDetailAssign2(pcodeId, assetIdIndex);
                        
            Collection positionsassign2 = tracker.getPositionsAssign2(pcodeId, assetIdIndex); 
            frm.setPositions(positionsassign2); 
            int positionId2Index = frm.getPositionId2Index();
            frm.setPositionId2Index(positionId2Index);
            int ftype = frm.getFtype(); 
            frm.setFtype(ftype);
            String searchd = frm.getSearchdetail() != null ? frm.getSearchdetail() : "";
            frm.setSearchdetail(searchd);            
            ArrayList assignlist2 = tracker.getListAssign2(assetIdIndex, pcodeId, ftype, searchd, positionId2Index, cassessor, uId);           
            request.getSession().setAttribute("ASSIGNLIST2", assignlist2);
            request.setAttribute("TRACKER_INFO2", info);       
            int arr[] = tracker.getCounts(assetIdIndex, -1, pcodeId, -1); 
            request.getSession().setAttribute("ARR_COUNT", arr);
            return mapping.findForward("assign2");
        }
        else if(frm.getDoUpdateTrack()!= null && frm.getDoUpdateTrack().equals("yes"))
        {
            print(this, "DoUpdateTrack block");
            frm.setDoUpdateTrack("no");
            int trackerId = frm.getTrackerId();
            int crewrotationId = frm.getCrewrotationId();
            frm.setCrewrotationId(crewrotationId);          
             int ftype = frm.getFtype(); 
            frm.setFtype(ftype);
            int positionId2 = frm.getPositionId();
            int candidateId = frm.getCandidateId();
            frm.setCandidateId(candidateId);
            String searchd = frm.getSearchdetail() != null ? frm.getSearchdetail() : "";
            frm.setSearchdetail(searchd);
            int status = frm.getStatusModal();
            if(status == 2)
            {
                String remarks = frm.getRemarks();
                int score = frm.getScore();
                int prcaticalscore = frm.getPracticalscore();
                int resultId = frm.getResultId();
                String practicalremarks = frm.getPracticalremarks();
                int average  = frm.getAveragescore();            
                int resultId2 = frm.getResultId2();
                if(average >0)
                {
                    status = 3;
                }else{
                    status = 2;
                }
            
                String loginname1 = frm.getUsername1Hiden();
                String loginname2 = frm.getUsername2Hiden();

                String add_tracker_file = tracker.getMainPath("add_tracker_file");
                String foldername = tracker.createFolder(add_tracker_file);
                Date now = new Date();
                String fn = String.valueOf(now.getTime());
                String fileName1 = "";
                FormFile trackerFile = frm.getTrackerFile();
                if(trackerFile != null && trackerFile.getFileSize() > 0)
                {
                    fileName1 = tracker.uploadFile(trackerId, frm.getTrackerFilehidden(), trackerFile, fn, add_tracker_file, foldername);
                }

                String fn1 = String.valueOf(now.getTime());
                String fileName2 = "";
                FormFile trackerFile2 = frm.getTrackerFile2();

                if(trackerFile2 != null && trackerFile2.getFileSize() > 0)
                {
                    fileName2 = tracker.uploadFile(trackerId, frm.getTrackerFilehidden2(), trackerFile2, fn1, add_tracker_file, foldername);
                }

                TrackerInfo info = new TrackerInfo(trackerId, remarks, score,resultId, fileName1, status, fileName2, 
                        practicalremarks, prcaticalscore,  resultId2, average, loginname1, loginname2 );
                tracker.updatetracker(info, username);
                
            }
            else if(status == 3)
            {   
                int trainingId = frm.getTrainingId();
                int outcomeId = frm.getOutcomeId();
                String outcomeremarks = frm.getOutcomeremarks();     
                int month = frm.getMonth();
                tracker.updateTrackerStatus(trackerId, trainingId, outcomeId, outcomeremarks, username, month);
            }        
            else if(status == 5)
            {   
                tracker.reasign(trackerId, username, uId);
            }    
            else if(status == 6)
            {   
                String rejection = frm.getRejection();
                int reasonId = frm.getReasonId();                
                tracker.updateAppeal(trackerId, rejection, reasonId, username);
            }
            
            int typefrom = frm.getTypefrom();
            if(typefrom == 1)
            {
                TrackerInfo info1 = tracker.getBasicDetail(candidateId);
                int positionId = 0;
                if(info1 != null)
                    positionId = info1.getPositionId();            
                frm.setPositionId(positionId); 
                Collection passessmenttypes = tracker.getAssessmenttypes(assetIdIndex, positionId2);
                frm.setPassessmenttypes(passessmenttypes); 
                int passessmenttypeId = frm.getPassessmenttypeId();
                frm.setPassessmenttypeId(passessmenttypeId);
                Collection priorities = tracker.getPriorities(assetIdIndex, positionId2);
                frm.setPriorities(priorities); 
                int priorityId = frm.getPriorityId();
                frm.setPriorityId(priorityId);

                ArrayList assignlist1 = tracker.getListAssign1(assetIdIndex, positionId2, candidateId, passessmenttypeId, priorityId, ftype, searchd, cassessor, uId);    
                request.getSession().setAttribute("ASSIGNLIST1", assignlist1);
                request.setAttribute("TRACKER_INFO1", info1);         
                 int arr[] = tracker.getCounts(assetIdIndex, candidateId, -1, 1); 
                request.getSession().setAttribute("ARR_COUNT", arr);
                request.setAttribute("TRACKER_PID", ""+positionId2);
                return mapping.findForward("assign1");
            }
            else
            {
                int pcodeId = frm.getPcodeId();
                frm.setPcodeId(pcodeId);
                Collection positionsassign2 = tracker.getPositionsAssign2(pcodeId, assetIdIndex); 
                frm.setPositions(positionsassign2); 
                int positionId2Index = frm.getPositionId2Index();
                frm.setPositionId2Index(positionId2Index);
                
                TrackerInfo minfo = tracker.getBasicDetailAssign2(pcodeId, assetIdIndex);
                ArrayList assignlist2 = tracker.getListAssign2(assetIdIndex, pcodeId, ftype, searchd, positionId2Index, cassessor, uId);           
                request.getSession().setAttribute("ASSIGNLIST2", assignlist2);
                request.setAttribute("TRACKER_INFO2", minfo);   
                int arr[] = tracker.getCounts(assetIdIndex, -1, pcodeId, -1); 
                request.getSession().setAttribute("ARR_COUNT", arr);
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
            request.getSession().removeAttribute("TRACKER_LIST");
            request.getSession().removeAttribute("ARR_COUNT");
            return mapping.findForward("list1");
        }        
    }
}
