package com.web.jxp.client;

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
import com.web.jxp.country.Country;
import com.web.jxp.currency.Currency;
import com.web.jxp.port.Port;
import com.web.jxp.user.UserInfo;
import java.sql.Connection;
import java.util.Collection;
import java.util.Date;
import java.util.Stack;

public class ClientAction extends Action {

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        ClientForm frm = (ClientForm) form;
        Client client = new Client();
        Country country = new Country();
        Assettype assettype = new Assettype();
        Validate vobj = new Validate();
        Port port = new Port();
        Currency currency = new Currency();

        int count = client.getCount();
        String search = frm.getSearch() != null ? frm.getSearch() : "";
        frm.setSearch(search);
        Collection countries = country.getCountrysclient();
        frm.setCountries(countries);
        int countryIndexId = frm.getCountryIndexId();
        frm.setCountryIndexId(countryIndexId);

        Collection assettypes = assettype.getAssettypesforclientindex();
        frm.setAssettypes(assettypes);
        int assettypeIndexId = frm.getAssettypeIndexId();
        frm.setAssettypeIndexId(assettypeIndexId);

        int uId = 0, allclient = 0;
        String permission = "N", cids = "", assetids = "";
        if (request.getSession().getAttribute("LOGININFO") != null) {
            UserInfo uInfo = (UserInfo) request.getSession().getAttribute("LOGININFO");
            if (uInfo != null) {
                permission = uInfo.getPermission();
                uId = uInfo.getUserId();
                cids = uInfo.getCids();
                allclient = uInfo.getAllclient();
                assetids = uInfo.getAssetids();
            }
        }
        int check_user = client.checkUserSession(request, 2, permission);
        if (check_user == -1) {
            return mapping.findForward("default");
        } else if (check_user == -2) {
            String authmess = client.getMainPath("user_auth");
            authmess = authmess.replaceAll("__MODULE__", "Client Module");
            request.setAttribute("AUTHMESSAGE", authmess);
            return mapping.findForward("auth");
        }
        if (frm.getDoAdd() != null && frm.getDoAdd().equals("yes")) {
            frm.setDoAdd("no");
            print(this, " doAdd block :: ");
            frm.setClientId(-1);

            frm.setCountryId(-1);
            int maxclientcode = client.getMaxIdclient();
            frm.setClientcode(maxclientcode);
            request.getSession().removeAttribute("OCSUSERS_IDs");
            saveToken(request);
            return mapping.findForward("add_client");
        } else if (frm.getDoModify() != null && frm.getDoModify().equals("yes")) {
            frm.setDoModify("no");
            int clientId = frm.getClientId();
            frm.setClientId(clientId);
            frm.setClientcode(clientId);
            ClientInfo info = client.getClientDetailById(clientId);
            if (info != null) {
                if (info.getName() != null) {
                    frm.setName(info.getName());
                }
                if (info.getHeadofficeaddress() != null) {
                    frm.setHeadofficeaddress(info.getHeadofficeaddress());
                }
                frm.setCountryId(info.getCountryId());
                if (info.getInchargename() != null) {
                    frm.setInchargename(info.getInchargename());
                }
                if (info.getPosition() != null) {
                    frm.setPosition(info.getPosition());
                }
                if (info.getEmail() != null) {
                    frm.setEmail(info.getEmail());
                }
                if (info.getContact() != null) {
                    frm.setContact(info.getContact());
                }
                if (info.getDate() != null) {
                    frm.setDate(info.getDate());
                }
                if (info.getLink1() != null) {
                    frm.setLink1(info.getLink1());
                }
                if (info.getLink2() != null) {
                    frm.setLink2(info.getLink2());
                }
                if (info.getLink3() != null) {
                    frm.setLink3(info.getLink3());
                }
                if (info.getLink4() != null) {
                    frm.setLink4(info.getLink4());
                }
                if (info.getLink5() != null) {
                    frm.setLink5(info.getLink5());
                }
                if (info.getLink6() != null) {
                    frm.setLink6(info.getLink6());
                }
                if (info.getShortName() != null) {
                    frm.setShortName(info.getShortName());
                }
                if (info.getColorCode() != null) {
                    frm.setColorCode(info.getColorCode());
                }
                request.getSession().setAttribute("OCSUSERS_IDs", info.getOcsuserIds());
                request.getSession().setAttribute("M_IDs", info.getMids());
                request.getSession().setAttribute("R_IDs", info.getRids());
                frm.setISDcode(info.getISDcode());
                if (request.getAttribute("CLIENTSAVEMODEL") != null) {
                    request.removeAttribute("CLIENTSAVEMODEL");
                }
            }
            saveToken(request);
            return mapping.findForward("add_client");
        } else if (frm.getDoView() != null && frm.getDoView().equals("yes")) {
            frm.setDoView("no");
            int clientId = frm.getClientId();
            frm.setClientId(clientId);
            ClientInfo info = client.getClientDetailByIdforDetail(clientId);
            request.getSession().setAttribute("CLIENT_DETAIL", info);
            ArrayList list = client.getAssetLIst(clientId, allclient, permission, cids, assetids);
            request.getSession().setAttribute("ASSETLIST", list);
            String tabno = frm.getTabno() != null ? frm.getTabno() : "1";
            request.getSession().setAttribute("TABNO", tabno);
            return mapping.findForward("view_client");
        } else if (frm.getDoSave() != null && frm.getDoSave().equals("yes")) {
            frm.setDoSave("no");
            print(this, "getDoSave block.");
            if (isTokenValid(request)) {
                resetToken(request);
                int clientId = frm.getClientId();
                String name = vobj.replacedesc(frm.getName());
                String headofficeaddress = vobj.replacedesc(frm.getHeadofficeaddress());
                int countryId = frm.getCountryId();
                String ISDcode = vobj.replaceint(frm.getISDcode());
                String inchargename = vobj.replacename(frm.getInchargename());
                String position = vobj.replacename(frm.getPosition());
                String email = vobj.replacedesc(frm.getEmail());
                String contact = vobj.replaceint(frm.getContact());
                String date = vobj.replacedate(frm.getDate());
                String[] ocsuserIds = frm.getOcsuserIds();
                String strocsuserIds = vobj.replacealphacomma(makeCommaDelimString(ocsuserIds));
                String[] mIds = frm.getMids();
                String strmIds = vobj.replacealphacomma(makeCommaDelimString(mIds));
                String[] rIds = frm.getRids();
                String strRids = vobj.replacealphacomma(makeCommaDelimString(rIds));
                int status = 1;
                String link1 = frm.getLink1();
                String link2 = frm.getLink2();
                String link3 = frm.getLink3();
                String link4 = frm.getLink4();
                String link5 = frm.getLink5();
                String link6 = frm.getLink6();
                String shortName = frm.getShortName();
                String colorCode = frm.getColorCode();
                String ipAddrStr = request.getRemoteAddr();
                String iplocal = client.getLocalIp();
                int ck = client.checkDuplicacy(clientId, name);
                if (ck == 1) {
                    saveToken(request);
                    frm.setCountryId(countryId);

                    request.getSession().setAttribute("OCSUSERS_IDs", strocsuserIds);
                    request.getSession().setAttribute("M_IDs", strmIds);
                    request.setAttribute("MESSAGE", "Client already exists");
                    return mapping.findForward("add_client");
                }
                ClientInfo info = new ClientInfo(clientId, name, headofficeaddress, countryId, inchargename, position,
                        email, contact, date, link1, link2, link3, link4, link5, link6, strocsuserIds, status, uId,
                        ISDcode, shortName, colorCode, strmIds, strRids);
                if (clientId <= 0) {
                    int cc = client.createClient(info);
                    if (cc > 0) {
                        client.createHistoryAccess(null, uId, ipAddrStr, iplocal, "Data Added", 2, cc);
                        request.setAttribute("MESSAGE", "Data added successfully.");
                    }
                    ClientInfo vinfo = client.getClientDetailByIdforDetail(cc);
                    request.getSession().setAttribute("CLIENT_DETAIL", vinfo);
                    request.setAttribute("CLIENTSAVEMODEL", "yes");
                    request.getSession().removeAttribute("OCSUSERS_IDs");
                    ArrayList list = client.getAssetLIst(clientId, allclient, permission, cids, assetids);
                    request.getSession().setAttribute("ASSETLIST", list);
                    String tabno = frm.getTabno() != null ? frm.getTabno() : "1";
                    request.getSession().setAttribute("TABNO", tabno);
                    return mapping.findForward("view_client");
                } else {
                    client.updateClient(info);
                    client.createHistoryAccess(null, uId, ipAddrStr, iplocal, "Data Updated", 2, clientId);
                    request.setAttribute("MESSAGE", "Data updated successfully.");
                    int next = 0;
                    if (request.getSession().getAttribute("NEXTVALUE") != null) {
                        next = Integer.parseInt((String) request.getSession().getAttribute("NEXTVALUE"));
                    }
                    next = next - 1;
                    if (next < 0) {
                        next = 0;
                    }
                    ArrayList clientList = client.getClientByName(search, next, count, countryIndexId, assettypeIndexId, allclient, permission, cids, assetids);
                    int cnt = 0;
                    if (clientList.size() > 0) {
                        ClientInfo cinfo = (ClientInfo) clientList.get(clientList.size() - 1);
                        cnt = cinfo.getClientId();
                        clientList.remove(clientList.size() - 1);
                    }
                    ClientInfo vinfo = client.getClientDetailByIdforDetail(clientId);
                    request.getSession().setAttribute("CLIENT_DETAIL", vinfo);
                    request.getSession().removeAttribute("OCSUSERS_IDs");
                    ArrayList list = client.getAssetLIst(clientId, allclient, permission, cids, assetids);
                    request.getSession().setAttribute("ASSETLIST", list);
                    String tabno = frm.getTabno() != null ? frm.getTabno() : "1";
                    request.getSession().setAttribute("TABNO", tabno);
                    return mapping.findForward("view_client");
                }
            }
        } else if (frm.getDoSaveAsset() != null && frm.getDoSaveAsset().equals("yes")) {
            if (isTokenValid(request)) {
                resetToken(request);
                frm.setDoSaveAsset("no");

                int clientId = frm.getClientId();
                frm.setClientId(clientId);
                int clientassetId = frm.getClientassetId();
                int countryId = frm.getCountryId();
                String name = vobj.replacename(frm.getName());
                int assettypeId = frm.getAssettypeId();
                int currencyId = frm.getCurrencyId();
                String description = vobj.replacedesc(frm.getDescription());
                int portId = frm.getPortId();
                String deliveryYear = frm.getDeliveryYear();
                String assetFlag = vobj.replacedesc(frm.getAssetFlag());
                String classification = vobj.replacedesc(frm.getClassification());
                String berths = vobj.replacedesc(frm.getBerths());
                String lifeboat = vobj.replacedesc(frm.getLifeboat());
                String url = vobj.replacedesc(frm.getUrl());
                String url_training = vobj.replacedesc(frm.getUrl_training());
                String helpno = vobj.replacedesc(frm.getHelpno());
                String helpemail = vobj.replacedesc(frm.getHelpemail());
                String ratetype = frm.getRatetype();
                int crewrota = frm.getCrewrota();
                int startrota = frm.getStartrota();
                double allowance = frm.getAllowance();
                int ck = client.checkDuplicacyAsset(clientassetId, clientId, name);
                if (ck == 1) {
                    saveToken(request);
                    assettypes = assettype.getAssettypesforclientindex();
                    frm.setAssettypes(assettypes);
                    Collection ports = client.getPorts();
                    frm.setPorts(ports);
                    Collection currencies = currency.getCurrencys();
                    frm.setCurrencies(currencies);

                    request.setAttribute("MESSAGE", "Asset already exists");
                    return mapping.findForward("edit_client_asset");
                }
                String add_client_file = client.getMainPath("add_client_file");
                String foldername = client.createFolder(add_client_file);
                Date now = new Date();
                String fn = String.valueOf(now.getTime());
                int status = 1;

                ClientInfo info = new ClientInfo(clientassetId, name, description, portId, deliveryYear, assetFlag, classification, berths, lifeboat,
                        status, countryId, assettypeId, url, url_training, currencyId, helpno, helpemail, ratetype, crewrota, startrota, allowance);
                if (clientassetId <= 0) {
                    int cc = client.createClientAsset(info, clientId, uId);
                    if (cc > 0) {
                        String fname = frm.getFname() != null ? frm.getFname() : "";
                        if (!"".equals(fname)) {
                            String fnameval[] = fname.split("@#@");
                            int len = fnameval.length;
                            Connection conn = null;
                            try {
                                conn = client.getConnection();
                                for (int i = 0; i < len; i++) {
                                    String fileName = client.saveImage(fnameval[i], add_client_file, foldername, fn + "_" + i);
                                    client.createPic(conn, cc, fileName, uId);
                                }
                            } finally {
                                if (conn != null) {
                                    conn.close();
                                }
                            }
                        }
                        request.setAttribute("MESSAGE", "Data added successfully.");
                    }
                    ArrayList list = client.getAssetLIst(clientId, allclient, permission, cids, assetids);
                    request.getSession().setAttribute("ASSETLIST", list);

                    request.getSession().setAttribute("TABNO", "2");
                    return mapping.findForward("view_client");
                } else {
                    client.updateClientAsset(info, uId);
                    String fname = frm.getFname() != null ? frm.getFname() : "";
                    if (!"".equals(fname)) {
                        String fnameval[] = fname.split("@#@");
                        int len = fnameval.length;
                        Connection conn = null;
                        try {
                            conn = client.getConnection();
                            for (int i = 0; i < len; i++) {
                                String fileName = client.saveImage(fnameval[i], add_client_file, foldername, fn + "_" + i);
                                client.createPic(conn, clientassetId, fileName, uId);
                            }
                        } finally {
                            if (conn != null) {
                                conn.close();
                            }
                        }
                    }
                    request.setAttribute("MESSAGE", "Data updated successfully.");
                    ArrayList list = client.getAssetLIst(clientId, allclient, permission, cids, assetids);
                    request.getSession().setAttribute("ASSETLIST", list);
                    request.getSession().setAttribute("TABNO", "2");
                    return mapping.findForward("view_client");
                }
            }
        } else if (frm.getDoDeleteAsset() != null && frm.getDoDeleteAsset().equals("yes")) {
            frm.setDoDeleteAsset("no");
            int clientId = frm.getClientId();
            frm.setClientId(clientId);
            int clientassetId = frm.getClientassetId();
            int status = frm.getStatus();
            int cc = client.deleteClientAsset(clientassetId, status, uId);
            if (cc > 0) {
                request.setAttribute("MESSAGE", "Status updated successfully.");

            }
            ArrayList list = client.getAssetLIst(clientId, allclient, permission, cids, assetids);
            request.getSession().setAttribute("ASSETLIST", list);

            request.getSession().setAttribute("TABNO", "2");
            return mapping.findForward("view_client");
        } else if (frm.getDoModifyAsset() != null && frm.getDoModifyAsset().equals("yes")) {

            frm.setDoModifyAsset("no");
            int clientId = frm.getClientId();
            frm.setClientId(clientId);
            int clientassetId = frm.getClientassetId();
            frm.setClientassetId(clientassetId);
            String coordinateIds = "";
            if(request.getSession().getAttribute("CLIENT_DETAIL") != null)
            {
                ClientInfo minfo = (ClientInfo) request.getSession().getAttribute("CLIENT_DETAIL");
                if(minfo != null)
                    coordinateIds = minfo.getOcsuserIds() != null ? minfo.getOcsuserIds() : "";
            }
            ArrayList colist = client.getCoordinatorList(coordinateIds);
            if (clientassetId <= 0) 
            {
                int maxclientassestcode = client.getMaxId();
                frm.setClientassetcode(maxclientassestcode);
                frm.setCrewrota(1);
                frm.setStartrota(1); 
            }
            assettypes = assettype.getAssettypesforclientindex();
            frm.setAssettypes(assettypes);
            Collection ports = port.getPortbycountries(-1);
            frm.setPorts(ports);
            Collection currencies = currency.getCurrencys();
            frm.setCurrencies(currencies);
            if (clientassetId > 0) 
            {
                ClientInfo info = client.getAssetForModify(clientassetId);
                if (info != null) {

                    frm.setClientassetcode(clientassetId);

                    frm.setCountryId(info.getCountryId());
                    frm.setAssettypeId(info.getAssettypeId());
                    if (info.getName() != null) {
                        frm.setName(info.getName());
                    }
                    if (info.getDescription() != null) {
                        frm.setDescription(info.getDescription());
                    }
                    ports = port.getPortbycountries(info.getCountryId());
                    frm.setPorts(ports);
                    frm.setPortId(info.getPortId());
                    frm.setCurrencyId(info.getCurrencyId());

                    if (info.getAssetFlag() != null) {
                        frm.setAssetFlag(info.getAssetFlag());
                    }
                    if (info.getBerths() != null) {
                        frm.setBerths(info.getBerths());
                    }
                    if (info.getClassification() != null) {
                        frm.setClassification(info.getClassification());
                    }
                    if (info.getDeliveryYear() != null) {
                        frm.setDeliveryYear(info.getDeliveryYear());
                    }
                    if (info.getLifeboat() != null) {
                        frm.setLifeboat(info.getLifeboat());
                    }
                    if (info.getUrl() != null) {
                        frm.setUrl(info.getUrl());
                    }
                    if (info.getUrl_training() != null) {
                        frm.setUrl_training(info.getUrl_training());
                    }
                    if (info.getHelpno() != null) {
                        frm.setHelpno(info.getHelpno());
                    }
                    if (info.getHelpemail() != null) {
                        frm.setHelpemail(info.getHelpemail());
                    }
                    if (info.getRatetype() != null) {
                        frm.setRatetype(info.getRatetype());
                    }
                    frm.setCrewrota(info.getCrewrota());
                    frm.setStartrota(info.getStartrota());
                    frm.setAllowance(info.getAllowance());
                    request.setAttribute("MINFO", info);
                }
            }     
            saveToken(request);
            request.getSession().setAttribute("CO_IDs", coordinateIds);
            request.getSession().setAttribute("COLIST", colist);
            return mapping.findForward("edit_client_asset");
        } else if (frm.getDoCancel() != null && frm.getDoCancel().equals("yes")) {
            frm.setDoCancel("no");
            print(this, "doCancel block");
            int next = 0;
            if (request.getSession().getAttribute("NEXTVALUE") != null) {
                next = Integer.parseInt((String) request.getSession().getAttribute("NEXTVALUE"));
            }
            next = next - 1;
            if (next < 0) {
                next = 0;
            }
            ArrayList clientList = client.getClientByName(search, next, count, countryIndexId, assettypeIndexId, allclient, permission, cids, assetids);
            int cnt = 0;
            if (clientList.size() > 0) {
                ClientInfo cinfo = (ClientInfo) clientList.get(clientList.size() - 1);
                cnt = cinfo.getClientId();
                clientList.remove(clientList.size() - 1);
            }
            request.getSession().setAttribute("CLIENT_LIST", clientList);
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
            ArrayList clientList = client.getClientByName(search, 0, count, countryIndexId, assettypeIndexId, allclient, permission, cids, assetids);
            int cnt = 0;
            if (clientList.size() > 0) {
                ClientInfo cinfo = (ClientInfo) clientList.get(clientList.size() - 1);
                cnt = cinfo.getClientId();
                clientList.remove(clientList.size() - 1);
            }
            request.getSession().setAttribute("CLIENT_LIST", clientList);
            request.getSession().setAttribute("COUNT_LIST", cnt + "");
            request.getSession().setAttribute("NEXT", "0");
            request.getSession().setAttribute("NEXTVALUE", "1");
        }
        return mapping.findForward("display");
    }
}
