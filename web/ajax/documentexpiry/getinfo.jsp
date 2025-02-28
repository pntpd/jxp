<%@page contentType="text/html"%>
<%@page language="java" import="java.util.ArrayList"%>
<%@page pageEncoding="UTF-8"%>
<%@page import="com.web.jxp.documentexpiry.DocumentexpiryInfo" %>
<%@page import="com.web.jxp.user.UserInfo" %>
<jsp:useBean id="documentexpiry" class="com.web.jxp.documentexpiry.Documentexpiry" scope="page"/>
<jsp:useBean id="vobj" class="com.web.jxp.base.Validate" scope="page"/>
<%
    try 
    {
        if (session.getAttribute("LOGININFO") != null) 
        {
            int allclient = 0;
            UserInfo uinfo = ((UserInfo) request.getSession().getAttribute("LOGININFO"));
            String per = "", editper = "N", cids = "", assetids = "", addper = "N";
            if (uinfo != null) 
            {
                per = uinfo.getPermission() != null ? uinfo.getPermission() : "N";
                editper = uinfo.getEditper() != null ? uinfo.getEditper() : "N";
                cids = uinfo.getCids();
                allclient = uinfo.getAllclient();
                assetids = uinfo.getAssetids();
            }

            StringBuilder sb = new StringBuilder();
            sb.append("<thead>");
            sb.append("<tr>");
            sb.append("<th width='3%' class='select_check_box'>");
            sb.append("<label class='mt-checkbox mt-checkbox-outline'>");
            sb.append("<input type='checkbox' class='' name='govdoccball' id='govdoccball' onchange='javascrip: setall();' />");
            sb.append("<span></span>");
            sb.append("</label>");
            sb.append("</th>");
            sb.append("<th width='7%'><span><b>ID</b> </span>");
            sb.append("<a href=\"javascript: sortForm('1', '2');\" id='img_1_2' class='sort_arrow deactive_sort'><i class='ion ion-ios-arrow-up'></i></a>");
            sb.append("<a href=\"javascript: sortForm('1', '1');\" id='img_1_1' class='sort_arrow deactive_sort'><i class='ion ion-ios-arrow-down'></i></a>");
            sb.append("</th>");
            sb.append("<th width='18%'><span><b>Name</b> </span>");
            sb.append("<a href=\"javascript: sortForm('2', '2');\" id='img_2_2' class='sort_arrow deactive_sort'><i class='ion ion-ios-arrow-up'></i></a>");
            sb.append("<a href=\"javascript: sortForm('2', '1');\" id='img_2_1' class='sort_arrow deactive_sort'><i class='ion ion-ios-arrow-down'></i></a>");
            sb.append("</th>");
            sb.append("<th width='18%'><span><b>Client Name</b> </span>");
            sb.append("<a href=\"javascript: sortForm('3', '2');\" id='img_3_2' class='sort_arrow deactive_sort'><i class='ion ion-ios-arrow-up'></i></a>");
            sb.append("<a href=\"javascript: sortForm('3', '1');\" id='img_3_1' class='sort_arrow deactive_sort'><i class='ion ion-ios-arrow-down'></i></a>");
            sb.append("</th>");
            sb.append("<th width='18%'><span><b>Asset Name</b> </span>");
            sb.append("<a href=\"javascript: sortForm('4', '2');\" id='img_4_2' class='sort_arrow deactive_sort'><i class='ion ion-ios-arrow-up'></i></a>");
            sb.append("<a href=\"javascript: sortForm('4', '1');\" id='img_4_1' class='sort_arrow deactive_sort'><i class='ion ion-ios-arrow-down'></i></a>");
            sb.append("</th>");
            sb.append("<th width='17%'><span><b>Document Name</b> </span>");
            sb.append("<a href=\"javascript: sortForm('5', '2');\" id='img_5_2' class='sort_arrow deactive_sort'><i class='ion ion-ios-arrow-up'></i></a>");
            sb.append("<a href=\"javascript: sortForm('5', '1');\" id='img_5_1' class='sort_arrow deactive_sort'><i class='ion ion-ios-arrow-down'></i></a>");
            sb.append("</th>");
            sb.append("<th width='11%'><span><b>Expiry Date</b> </span>");
            sb.append("<a href=\"javascript: sortForm('6', '2');\" id='img_6_2' class='sort_arrow deactive_sort'><i class='ion ion-ios-arrow-up'></i></a>");
            sb.append("<a href=\"javascript: sortForm('6', '1');\" id='img_6_1' class='sort_arrow deactive_sort'><i class='ion ion-ios-arrow-down'></i></a>");
            sb.append("</th>");
            sb.append("<th width='6%' class='text-right'><span><b>&nbsp;</b></span></th>");
            sb.append("</tr>");
            sb.append("</thead>");
            String thead = sb.toString();
            sb.setLength(0);

            String documentIds = request.getParameter("documentId") != null && !request.getParameter("documentId").equals("") ? vobj.replaceint(request.getParameter("documentId")) : "-1";
            String types = request.getParameter("type") != null && !request.getParameter("type").equals("") ? vobj.replaceint(request.getParameter("type")) : "-1";
            String exps = request.getParameter("exp") != null && !request.getParameter("exp").equals("") ? vobj.replaceint(request.getParameter("exp")) : "-1";
            String clientIdIndexs = request.getParameter("clientIdIndex") != null && !request.getParameter("clientIdIndex").equals("") ? vobj.replaceint(request.getParameter("clientIdIndex")) : "-1";
            String assetIdIndexs = request.getParameter("assetIdIndex") != null && !request.getParameter("assetIdIndex").equals("") ? vobj.replaceint(request.getParameter("assetIdIndex")) : "-1";
            String coursenameIds = request.getParameter("coursenameId") != null && !request.getParameter("coursenameId").equals("") ? vobj.replaceint(request.getParameter("coursenameId")) : "-1";
            String dropdownIds = request.getParameter("dropdownId") != null && !request.getParameter("dropdownId").equals("") ? vobj.replaceint(request.getParameter("dropdownId")) : "-1";
            String healthIds = request.getParameter("healthId") != null && !request.getParameter("healthId").equals("") ? vobj.replaceint(request.getParameter("healthId")) : "1";
            String search = request.getParameter("search") != null && !request.getParameter("search").equals("") ? vobj.replacedesc(request.getParameter("search")) : "";
            int clientIdIndex = Integer.parseInt(clientIdIndexs);
            int assetIdIndex = Integer.parseInt(assetIdIndexs);
            int coursenameId = Integer.parseInt(coursenameIds);
            int dropdownId = Integer.parseInt(dropdownIds);
            int healthId = Integer.parseInt(healthIds);
            int type = Integer.parseInt(types);
            int exp = Integer.parseInt(exps);
            int documentId = Integer.parseInt(documentIds);
            if (request.getParameter("documentId") != null) 
            {
                StringBuilder str = new StringBuilder();
                ArrayList documentexpiry_list = new ArrayList();
                documentexpiry_list = documentexpiry.getDocumentexpiryByName(documentId, type,
                        exp, clientIdIndex, assetIdIndex, allclient, per, cids, assetids, coursenameId, dropdownId, healthId, search);
                int total = documentexpiry_list.size();
                session.setAttribute("DOCUMENTEXPIRY_LIST", documentexpiry_list);
                str.append("<div class='col-lg-12' id='printArea'>");
                str.append("<div class='table-rep-plugin sort_table'>");
                str.append("<div class='table-responsive mb-0' data-bs-pattern='priority-columns'>");
                str.append("<table id='tech-companies-1' class='table table-striped'>");
                str.append(thead);
                str.append("<tbody id = 'sort_id'>");
                DocumentexpiryInfo info;
                for (int i = 0; i < total; i++) 
                {
                    info = (DocumentexpiryInfo) documentexpiry_list.get(i);
                    if (info != null) 
                    {
                        str.append("<tr>");
                        str.append("<td class='select_check_box'>");
                        str.append("<label class='mt-checkbox mt-checkbox-outline'>");
                        str.append("<input type='checkbox' value='" + info.getGovdocId() + "' name='govdoccb' id='govdoccb' class='singlechkbox' onchange='javascript: setcb();' />");
                        str.append("<span></span>");
                        str.append("</label>");
                        str.append("</td>");
                        str.append("<td>" + (documentexpiry.changeNum(info.getCandidateId(), 6)) + "</td>");
                        str.append("<td>" + (info.getName() != null ? info.getName() : "") + "</td>");
                        str.append("<td>" + (info.getClientName() != null ? info.getClientName() : "") + "</td>");
                        str.append("<td>" + (info.getAssetName() != null ? info.getAssetName() : "") + "</td>");
                        str.append("<td>" + (info.getDocumentName() != null ? info.getDocumentName() : "") + "</td>");
                        str.append("<td>" + (info.getExpiryDate() != null ? info.getExpiryDate() : "") + "</td>");
                        if (addper.equals("Y") || editper.equals("Y")) {
                            str.append("<td class='text-center'><a class='remind_bell_btn' href=\"javascript: sendmail('" + info.getGovdocId() + "');\"><i class='far fa-bell'></i> <span>" + info.getRemind() + "</a></td>");
                        } else {
                            str.append("<td class='text-right'>&nbsp;</td>");
                        }
                        str.append("</tr>");
                    }
                }
                str.append("</tbody>");
                str.append("</table>");
                str.append("</div>");
                str.append("</div>");
                str.append("</div>");

                String s1 = str.toString();
                str.setLength(0);
                response.getWriter().write(s1);
            } else {
                response.setStatus(HttpServletResponse.SC_NO_CONTENT);
            }
        } else {
            response.getWriter().write("Please check your login session....");
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
%>