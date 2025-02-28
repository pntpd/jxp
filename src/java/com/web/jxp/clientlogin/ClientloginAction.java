package com.web.jxp.clientlogin;

import static com.web.jxp.common.Common.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Stack;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ClientloginAction extends Action {

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
            HttpServletResponse response) throws Exception 
    {
        ClientloginForm frm = (ClientloginForm) form;
        Clientlogin clientlogin = new Clientlogin();
        
        String search = frm.getSearch() != null ? frm.getSearch() : "";
        frm.setSearch(search);
        
        int count = clientlogin.getCount();
        int uId = 0;
        String username = "";
        if (request.getSession().getAttribute("MLOGININFO") != null) 
        {
            ClientloginInfo uInfo = (ClientloginInfo) request.getSession().getAttribute("MLOGININFO");
            uId = uInfo.getUserId();
            username = uInfo.getName() != null ? uInfo.getName(): "";
        }
        Collection clients = clientlogin.getClients(uId);
        frm.setClients(clients);
        int clientIndex = frm.getClientIdIndex();
        frm.setClientIdIndex(clientIndex);
        
        Collection assets = clientlogin.getClientAsset(-1);
        frm.setAssets(assets);
        int assetIndex = frm.getAssetIdIndex();
        frm.setAssetIdIndex(assetIndex);

        Collection positions = clientlogin.getPostions(-1);
        frm.setPositions(positions);
        int positionIndex = frm.getPositionIndex();
        frm.setPositionIndex(positionIndex);
        
        if (uId <=0) {
            return mapping.findForward("clientlogindefault");            
        } 
        if (frm.getDoLogout() != null && frm.getDoLogout().equals("yes")) {
            request.getSession().invalidate();
            return mapping.findForward("display");
        }
        else if (frm.getDoView() != null && frm.getDoView().equals("yes")) 
        {
            frm.setDoView("no");
            print(this, " getDoView block :: ");
            int jobpostId = frm.getJobpostId();
            
            ClientloginInfo info = clientlogin.getClientSelectionByIdforDetail(jobpostId);            
            ArrayList shortList = clientlogin.getShortlistedCandidateListByIDs(jobpostId, "");
            frm.setClientId(info.getClientId());
            request.getSession().setAttribute("CLIENTSELECTION_DETAIL", info);
            request.getSession().setAttribute("SHORTLIST_LIST", shortList);
            return mapping.findForward("view_details");
        }
        
        else if (frm.getDoSaveInterview()!= null && frm.getDoSaveInterview().equals("yes")) 
        {
            frm.setDoSaveInterview("no");
            print(this, "getDoSave block :::: ");

            int jobpostId = frm.getJobpostId();
            int candidateId = frm.getCandidateId();
            int interviewId = frm.getInterviewId();
            int assetId = frm.getAssetId();  
            int interviewerId = frm.getSelInterviewer();
            String linkloc = frm.getTxtLocLink();
            String mode = frm.getSelMode();
            int tz1 = frm.getSelCandTimeZone();
            int tz2 = frm.getSelInterviewerTimeZone();
            int duration = frm.getSelDuration();
            String date = frm.getTxtstartdate();
            String time = frm.getTxttime();
            date += " " + time;
            String datec = frm.getTxtDate();
            frm.setAssetId(assetId);
            int shortlistId = frm.getShortlistId();
            int positionId = frm.getPositionId();
            int clientId = frm.getClientId();
            
            ClientloginInfo info = new ClientloginInfo(jobpostId, candidateId, assetId, interviewerId,  linkloc, mode,
                    tz1, tz2, duration, date, datec, shortlistId, uId, username, positionId, clientId);
            int cc =0;
            if(interviewId <= 0)
            {
                cc = clientlogin.createInterview(info);
                request.getSession().setAttribute("INTV_ID", ""+cc);
            }else{
                cc = clientlogin.rescheduleInterview(info, interviewId);
                request.getSession().setAttribute("INTV_ID", ""+interviewId);
            }
            if(cc > 0){
                request.getSession().setAttribute("S_ID", ""+shortlistId);                
                request.getSession().setAttribute("THANKYOU_1", "yes");
            }
            return mapping.findForward("view_details");
        }
        //doEvaluation block
        else if (frm.getDoEvaluation()!= null && frm.getDoEvaluation().equals("yes")) 
        {
            frm.setDoEvaluation("no");
            print(this, "getDoEvaluation block :::: ");
            
            int interviewId = frm.getInterviewId();
            int score = frm.getScore();
            int type = frm.getType(); 
            String remarks = frm.getRemark();
            int candidateId = frm.getCandidateId();
            int cc = clientlogin.saveEvaluation(interviewId, score, type, remarks, username, uId, candidateId);
            frm.setType(0);
            if(cc > 0)
            {
                request.getSession().setAttribute("THANKYOU_2", "yes");
            }
            return mapping.findForward("view_details");
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
            ArrayList list = clientlogin.getClientLoginByName(search, 0, count, positionIndex, clientIndex, assetIndex, uId);
            int cnt = 0;
            if (list.size() > 0) {
                ClientloginInfo cinfo = (ClientloginInfo) list.get(list.size() - 1);
                cnt = cinfo.getJobpostId();
                list.remove(list.size() - 1);
            }
            request.getSession().setAttribute("CLIENTLOGIN_LIST", list);
            request.getSession().setAttribute("COUNT_LIST", cnt + "");
            request.getSession().setAttribute("NEXT", "0");
            request.getSession().setAttribute("NEXTVALUE", "1");
            return mapping.findForward("view_clientlogin");
        }
    }
}
