package com.web.jxp.benefit;

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

public class BenefitAction extends Action {

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        BenefitForm frm = (BenefitForm) form;
        Benefit benefit = new Benefit();

        Collection benefittypes = benefit.getBenefittype();
        frm.setBenefittypes(benefittypes);
        Validate vobj = new Validate();

        int count = benefit.getCount();
        String search = frm.getSearch() != null && !frm.getSearch().equals("") ? frm.getSearch() : "";
        frm.setSearch(search);
        int uId = 0;
        String permission = "N";
        if (request.getSession().getAttribute("LOGININFO") != null) {
            UserInfo uInfo = (UserInfo) request.getSession().getAttribute("LOGININFO");
            if (uInfo != null) {
                permission = uInfo.getPermission();
                uId = uInfo.getUserId();
            }
        }
        int check_user = benefit.checkUserSession(request, 43, permission);
        if (check_user == -1) {
            return mapping.findForward("default");
        } else if (check_user == -2) {
            String authmess = benefit.getMainPath("user_auth");
            authmess = authmess.replaceAll("__MODULE__", "Benefit Module");
            request.setAttribute("AUTHMESSAGE", authmess);
            return mapping.findForward("auth");
        }
        if (frm.getDoAdd() != null && frm.getDoAdd().equals("yes")) {
            print(this, " doAdd block :: ");
            frm.setStatus(1);
            frm.setBenefitId(-1);
            saveToken(request);
            return mapping.findForward("add_benefit");
        } else if (frm.getDoModify() != null && frm.getDoModify().equals("yes")) {
            int benefitId = frm.getBenefitId();
            frm.setBenefitId(benefitId);
            BenefitInfo info = benefit.getBenefitDetailById(benefitId);
            if (info != null) {
                frm.setBenefitName(info.getBenefitName());
                frm.setStatus(info.getStatus());

                Collection benefittypess = benefit.getBenefittype();
                frm.setBenefittypes(benefittypess);
                frm.setBenefittypeId(info.getBenefittypeId());
            }
            saveToken(request);
            return mapping.findForward("add_benefit");
        } else if (frm.getDoView() != null && frm.getDoView().equals("yes")) {
            int benefitId = frm.getBenefitId();
            frm.setBenefitId(benefitId);
            BenefitInfo info = benefit.getBenefitDetailByIdforDetail(benefitId);
            request.setAttribute("BENEFIT_DETAIL", info);
            return mapping.findForward("view_benefit");
        } else if (frm.getDoSave() != null && frm.getDoSave().equals("yes")) {
            print(this, "getDoSave block.");
            if (isTokenValid(request)) {
                resetToken(request);
                int benefitId = frm.getBenefitId();
                String name = vobj.replacedesc(frm.getBenefitName());
                int status = 1;
                int benefittypeId = frm.getBenefittypeId();

                String ipAddrStr = request.getRemoteAddr();
                String iplocal = benefit.getLocalIp();
                int ck = benefit.checkDuplicacy(benefitId, name, benefittypeId);
                if (ck == 1) {
                    saveToken(request);
                    request.setAttribute("MESSAGE", "Benefit already exists");
                    return mapping.findForward("add_benefit");
                }
                BenefitInfo info = new BenefitInfo(benefitId, name, benefittypeId, status, uId);
                if (benefitId <= 0) {
                    int cc = benefit.createBenefit(info);
                    if (cc > 0) {
                        benefit.createHistoryAccess(null, uId, ipAddrStr, iplocal, "Data Added", 43, cc);
                        request.setAttribute("MESSAGE", "Data added successfully.");

                    }
                    ArrayList benefitList = benefit.getBenefitByName(search, 0, count);
                    int cnt = 0;
                    if (benefitList.size() > 0) {
                        BenefitInfo cinfo = (BenefitInfo) benefitList.get(benefitList.size() - 1);
                        cnt = cinfo.getBenefitId();
                        benefitList.remove(benefitList.size() - 1);
                    }
                    request.getSession().setAttribute("BENEFIT_LIST", benefitList);
                    request.getSession().setAttribute("COUNT_LIST", cnt + "");
                    request.getSession().setAttribute("NEXT", "0");
                    request.getSession().setAttribute("NEXTVALUE", "1");

                    return mapping.findForward("display");
                } else {
                    benefit.updateBenefit(info);
                    benefit.createHistoryAccess(null, uId, ipAddrStr, iplocal, "Data Updated", 43, benefitId);
                    request.setAttribute("MESSAGE", "Data updated successfully.");
                    int next = 0;
                    if (request.getSession().getAttribute("NEXTVALUE") != null) {
                        next = Integer.parseInt((String) request.getSession().getAttribute("NEXTVALUE"));
                    }
                    next = next - 1;
                    if (next < 0) {
                        next = 0;
                    }
                    ArrayList benefitList = benefit.getBenefitByName(search, next, count);
                    int cnt = 0;
                    if (benefitList.size() > 0) {
                        BenefitInfo cinfo = (BenefitInfo) benefitList.get(benefitList.size() - 1);
                        cnt = cinfo.getBenefitId();
                        benefitList.remove(benefitList.size() - 1);
                    }
                    request.getSession().setAttribute("BENEFIT_LIST", benefitList);
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
            ArrayList benefitList = benefit.getBenefitByName(search, next, count);
            int cnt = 0;
            if (benefitList.size() > 0) {
                BenefitInfo cinfo = (BenefitInfo) benefitList.get(benefitList.size() - 1);
                cnt = cinfo.getBenefitId();
                benefitList.remove(benefitList.size() - 1);
            }
            request.getSession().setAttribute("BENEFIT_LIST", benefitList);
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

            ArrayList benefitList = benefit.getBenefitByName(search, 0, count);
            int cnt = 0;
            if (benefitList.size() > 0) {
                BenefitInfo cinfo = (BenefitInfo) benefitList.get(benefitList.size() - 1);
                cnt = cinfo.getBenefitId();
                benefitList.remove(benefitList.size() - 1);
            }
            request.getSession().setAttribute("BENEFIT_LIST", benefitList);
            request.getSession().setAttribute("COUNT_LIST", cnt + "");
            request.getSession().setAttribute("NEXT", "0");
            request.getSession().setAttribute("NEXTVALUE", "1");
        }
        return mapping.findForward("display");
    }
}
