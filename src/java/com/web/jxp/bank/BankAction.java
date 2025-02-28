package com.web.jxp.bank;

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

public class BankAction extends Action {

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        BankForm frm = (BankForm) form;
        Bank bank = new Bank();
        int count = bank.getCount();
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
        int check_user = bank.checkUserSession(request, 81, permission);
        if (check_user == -1) {
            return mapping.findForward("default");
        } else if (check_user == -2) {
            String authmess = bank.getMainPath("user_auth");
            authmess = authmess.replaceAll("__MODULE__", "Bank Module");
            request.setAttribute("AUTHMESSAGE", authmess);
            return mapping.findForward("auth");
        }
        if (frm.getDoAdd() != null && frm.getDoAdd().equals("yes")) {
            print(this, " doAdd block :: ");
            frm.setStatus(1);
            frm.setBankId(-1);
            saveToken(request);
            return mapping.findForward("add_bank");
        } else if (frm.getDoModify() != null && frm.getDoModify().equals("yes")) {
            int bankId = frm.getBankId();
            frm.setBankId(bankId);
            BankInfo info = bank.getBankDetailById(bankId);
            if (info != null) 
            {
                frm.setName(info.getName() != null ? info.getName() : "");
                frm.setBranch(info.getBranch()!= null ? info.getBranch() : "");
                frm.setAccno(info.getAccno() != null ? info.getAccno() : "");
                frm.setIfsc(info.getIfsc() != null ? info.getIfsc() : "");
                frm.setStatus(info.getStatus());
            }
            saveToken(request);
            return mapping.findForward("add_bank");
        } else if (frm.getDoView() != null && frm.getDoView().equals("yes")) {
            int bankId = frm.getBankId();
            frm.setBankId(bankId);
            BankInfo info = bank.getBankDetailByIdforDetail(bankId);
            request.setAttribute("PAYMENTMODE_DETAIL", info);
            return mapping.findForward("view_bank");
        } else if (frm.getDoSave() != null && frm.getDoSave().equals("yes")) {
            print(this, "getDoSave block.");
            if (isTokenValid(request)) {
                resetToken(request);
                int bankId = frm.getBankId();
                String name = frm.getName();
                String branch = frm.getBranch();
                String accno = frm.getAccno();
                String ifsc = frm.getIfsc();
                int status = 1;
                String ipAddrStr = request.getRemoteAddr();
                String iplocal = bank.getLocalIp();
                int ck = bank.checkDuplicacy(bankId, name);
                if (ck == 1) {
                    saveToken(request);
                    request.setAttribute("MESSAGE", "Bank name already exists");
                    return mapping.findForward("add_bank");
                }
                BankInfo info = new BankInfo(bankId, name, branch, accno, ifsc, status, uId);
                if (bankId <= 0) {
                    int cc = bank.createBank(info);
                    if (cc > 0) {
                        bank.createHistoryAccess(null, uId, ipAddrStr, iplocal, "Data Added", 81, cc);
                        request.setAttribute("MESSAGE", "Data added successfully.");

                    }
                    ArrayList bankList = bank.getBankByName(search, 0, count);
                    int cnt = 0;
                    if (bankList.size() > 0) {
                        BankInfo cinfo = (BankInfo) bankList.get(bankList.size() - 1);
                        cnt = cinfo.getBankId();
                        bankList.remove(bankList.size() - 1);
                    }
                    request.getSession().setAttribute("PAYMENTMODE_LIST", bankList);
                    request.getSession().setAttribute("COUNT_LIST", cnt + "");
                    request.getSession().setAttribute("NEXT", "0");
                    request.getSession().setAttribute("NEXTVALUE", "1");

                    return mapping.findForward("display");
                } else {
                    bank.updateBank(info);
                    bank.createHistoryAccess(null, uId, ipAddrStr, iplocal, "Data Updated", 81, bankId);
                    request.setAttribute("MESSAGE", "Data updated successfully.");
                    int next = 0;
                    if (request.getSession().getAttribute("NEXTVALUE") != null) {
                        next = Integer.parseInt((String) request.getSession().getAttribute("NEXTVALUE"));
                    }
                    next = next - 1;
                    if (next < 0) {
                        next = 0;
                    }
                    ArrayList bankList = bank.getBankByName(search, next, count);
                    int cnt = 0;
                    if (bankList.size() > 0) {
                        BankInfo cinfo = (BankInfo) bankList.get(bankList.size() - 1);
                        cnt = cinfo.getBankId();
                        bankList.remove(bankList.size() - 1);
                    }
                    request.getSession().setAttribute("PAYMENTMODE_LIST", bankList);
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
            ArrayList bankList = bank.getBankByName(search, next, count);
            int cnt = 0;
            if (bankList.size() > 0) {
                BankInfo cinfo = (BankInfo) bankList.get(bankList.size() - 1);
                cnt = cinfo.getBankId();
                bankList.remove(bankList.size() - 1);
            }
            request.getSession().setAttribute("PAYMENTMODE_LIST", bankList);
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

            ArrayList bankList = bank.getBankByName(search, 0, count);
            int cnt = 0;
            if (bankList.size() > 0) {
                BankInfo cinfo = (BankInfo) bankList.get(bankList.size() - 1);
                cnt = cinfo.getBankId();
                bankList.remove(bankList.size() - 1);
            }
            request.getSession().setAttribute("PAYMENTMODE_LIST", bankList);
            request.getSession().setAttribute("COUNT_LIST", cnt + "");
            request.getSession().setAttribute("NEXT", "0");
            request.getSession().setAttribute("NEXTVALUE", "1");
        }
        return mapping.findForward("display");
    }
}
