package com.web.jxp.bankaccounttype;

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

public class BankAccountTypeAction extends Action 
{
    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        BankAccountTypeForm frm = (BankAccountTypeForm) form;
        BankAccountType bankAccountType = new BankAccountType();
        Validate vobj = new Validate();
        int count = bankAccountType.getCount();
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
        int check_user = bankAccountType.checkUserSession(request, 39, permission);
        if (check_user == -1) {
            return mapping.findForward("default");
        } else if (check_user == -2) {
            String authmess = bankAccountType.getMainPath("user_auth");
            authmess = authmess.replaceAll("__MODULE__", "Bank Account Type Module");
            request.setAttribute("AUTHMESSAGE", authmess);
            return mapping.findForward("auth");
        }
        if (frm.getDoAdd() != null && frm.getDoAdd().equals("yes")) {
            print(this, " doAdd block :: ");
            frm.setStatus(1);
            frm.setBankAccountTypeId(-1);
            saveToken(request);
            return mapping.findForward("add_bankaccounttype");
        } else if (frm.getDoModify() != null && frm.getDoModify().equals("yes")) {
            int bankAccountTypeId = frm.getBankAccountTypeId();
            frm.setBankAccountTypeId(bankAccountTypeId);
            BankAccountTypeInfo info = bankAccountType.getBankAccountTypeDetailById(bankAccountTypeId);
            if (info != null) {
                frm.setName(info.getName());
                frm.setStatus(info.getStatus());
            }
            saveToken(request);
            return mapping.findForward("add_bankaccounttype");
        } else if (frm.getDoView() != null && frm.getDoView().equals("yes")) {
            int bankAccountTypeId = frm.getBankAccountTypeId();
            frm.setBankAccountTypeId(bankAccountTypeId);
            BankAccountTypeInfo info = bankAccountType.getBankAccountTypeDetailByIdforDetail(bankAccountTypeId);
            request.setAttribute("BANKACCOUNTTYPE_DETAIL", info);
            return mapping.findForward("view_bankaccounttype");
        } else if (frm.getDoSave() != null && frm.getDoSave().equals("yes")) {
            print(this, "getDoSave block.");
            if (isTokenValid(request)) {
                resetToken(request);
                int bankAccountTypeId = frm.getBankAccountTypeId();
                String name = vobj.replacename(frm.getName());
                int status = 1;
                String ipAddrStr = request.getRemoteAddr();
                String iplocal = bankAccountType.getLocalIp();
                int ck = bankAccountType.checkDuplicacy(bankAccountTypeId, name);
                if (ck == 1) {
                    saveToken(request);
                    request.setAttribute("MESSAGE", "Bank Account Type already exists");
                    return mapping.findForward("add_bankaccounttype");
                }
                BankAccountTypeInfo info = new BankAccountTypeInfo(bankAccountTypeId, name, status, uId);
                if (bankAccountTypeId <= 0) {
                    int cc = bankAccountType.createBankAccountType(info);
                    if (cc > 0) {
                        bankAccountType.createHistoryAccess(null, uId, ipAddrStr, iplocal, "Data Added", 39, cc);
                        request.setAttribute("MESSAGE", "Data added successfully.");

                    }
                    ArrayList bankAccountTypeList = bankAccountType.getBankAccountTypeByName(search, 0, count);
                    int cnt = 0;
                    if (bankAccountTypeList.size() > 0) {
                        BankAccountTypeInfo cinfo = (BankAccountTypeInfo) bankAccountTypeList.get(bankAccountTypeList.size() - 1);
                        cnt = cinfo.getBankAccountTypeId();
                        bankAccountTypeList.remove(bankAccountTypeList.size() - 1);
                    }
                    request.getSession().setAttribute("BANKACCOUNTTYPE_LIST", bankAccountTypeList);
                    request.getSession().setAttribute("COUNT_LIST", cnt + "");
                    request.getSession().setAttribute("NEXT", "0");
                    request.getSession().setAttribute("NEXTVALUE", "1");

                    return mapping.findForward("display");
                } else {
                    bankAccountType.updateBankAccountType(info);
                    bankAccountType.createHistoryAccess(null, uId, ipAddrStr, iplocal, "Data Updated", 39, bankAccountTypeId);
                    request.setAttribute("MESSAGE", "Data updated successfully.");
                    int next = 0;
                    if (request.getSession().getAttribute("NEXTVALUE") != null) {
                        next = Integer.parseInt((String) request.getSession().getAttribute("NEXTVALUE"));
                    }
                    next = next - 1;
                    if (next < 0) {
                        next = 0;
                    }
                    ArrayList bankAccountTypeList = bankAccountType.getBankAccountTypeByName(search, next, count);
                    int cnt = 0;
                    if (bankAccountTypeList.size() > 0) {
                        BankAccountTypeInfo cinfo = (BankAccountTypeInfo) bankAccountTypeList.get(bankAccountTypeList.size() - 1);
                        cnt = cinfo.getBankAccountTypeId();
                        bankAccountTypeList.remove(bankAccountTypeList.size() - 1);
                    }
                    request.getSession().setAttribute("BANKACCOUNTTYPE_LIST", bankAccountTypeList);
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
            ArrayList bankAccountTypeList = bankAccountType.getBankAccountTypeByName(search, next, count);
            int cnt = 0;
            if (bankAccountTypeList.size() > 0) {
                BankAccountTypeInfo cinfo = (BankAccountTypeInfo) bankAccountTypeList.get(bankAccountTypeList.size() - 1);
                cnt = cinfo.getBankAccountTypeId();
                bankAccountTypeList.remove(bankAccountTypeList.size() - 1);
            }
            request.getSession().setAttribute("BANKACCOUNTTYPE_LIST", bankAccountTypeList);
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

            ArrayList bankAccountTypeList = bankAccountType.getBankAccountTypeByName(search, 0, count);
            int cnt = 0;
            if (bankAccountTypeList.size() > 0) {
                BankAccountTypeInfo cinfo = (BankAccountTypeInfo) bankAccountTypeList.get(bankAccountTypeList.size() - 1);
                cnt = cinfo.getBankAccountTypeId();
                bankAccountTypeList.remove(bankAccountTypeList.size() - 1);
            }
            request.getSession().setAttribute("BANKACCOUNTTYPE_LIST", bankAccountTypeList);
            request.getSession().setAttribute("COUNT_LIST", cnt + "");
            request.getSession().setAttribute("NEXT", "0");
            request.getSession().setAttribute("NEXTVALUE", "1");
        }
        return mapping.findForward("display");
    }
}
