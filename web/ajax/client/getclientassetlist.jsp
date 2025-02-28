<%@page contentType="text/html"%>
<%@page language="java" import="java.util.ArrayList"%>
<%@page pageEncoding="UTF-8"%>
<%@page import="com.web.jxp.user.UserInfo" %>
<%@page import="com.web.jxp.client.ClientInfo" %>
<jsp:useBean id="client" class="com.web.jxp.client.Client" scope="page"/>
<jsp:useBean id="vobj" class="com.web.jxp.base.Validate" scope="page"/>
<%
    try {

        if (session.getAttribute("LOGININFO") != null)
        {
            UserInfo uinfo = ((UserInfo) request.getSession().getAttribute("LOGININFO"));
            int allclient = 0;
            String per = "", editper = "N", cids = "", assetids = "";
            if (uinfo != null)
            {
                per = uinfo.getPermission() != null ? uinfo.getPermission() : "N";
                editper = uinfo.getEditper() != null ? uinfo.getEditper() : "N";
                cids = uinfo.getCids();
                allclient = uinfo.getAllclient();
                assetids = uinfo.getAssetids();
            }
            if (request.getParameter("clientId") != null)
            {
                StringBuilder str = new StringBuilder();
                String clientIds = request.getParameter("clientId") != null && !request.getParameter("clientId").equals("") ? vobj.replaceint(request.getParameter("clientId")) : "0";
                int clientId = Integer.parseInt(clientIds);
                ArrayList asset_list = new ArrayList();
                asset_list = client.getAssetLIst(clientId,allclient,  per,  cids,  assetids);
                int total = asset_list.size();

                if (total > 0) 
                {
                    int status = 0;
                    ClientInfo info;
                    for (int i = 0; i < total; i++) 
                    {
                        info = (ClientInfo) asset_list.get(i);
                        if (info != null)
                        {
                            status = info.getStatus();
                            str.append("<tr>");
                            str.append("<td>" + (client.changeNum(info.getClientassetId(), 3)) + "</td>");
                            str.append("<td>" + (info.getAssettypeName() != null ? info.getAssettypeName() : "") + "</td>");
                            str.append("<td>" + (info.getName() != null ? info.getName() : "") + "</td>");
                            str.append("<td>" + (info.getCountryName() != null ? info.getCountryName() : "") + "</td>");
                            str.append("<td class='assets_list text-center'> <a  href='javascript: void(0);' onclick=\"setpositionmodel('" + info.getClientassetId() + "','" + info.getAssettypeId() + "');\" data-bs-toggle='modal' data-bs-target='#department_modal'>" + (client.changeNum(info.getDeptcount(), 2)) + "</a></td>");
                            str.append("<td class='assets_list text-center'> <a  href='javascript: void(0);' onclick=\"setpositionmodel('" + info.getClientassetId() + "','" + info.getAssettypeId() + "');\" data-bs-toggle='modal' data-bs-target='#client_position'>" + (client.changeNum(info.getPositionCount(), 2)) + "</a></td>");
                            str.append("<td class='action_column'>");
                            if (editper.equals("Y") && info.getStatus() == 1) {
                                str.append("<a href=\"javascript: modifyAssetForm('" + (info.getClientassetId()) + "');\" class='mr_15'><img src='../assets/images/pencil.png'/></a>");
                            } else {
                                str.append("<a href='javascript:;' ><span style='width: 35px;'>&nbsp;</span></a>");
                            }
                            str.append("<a class='mr_15' href='javascript:;' onclick=\"javascript: viewimg('" + info.getClientassetId() + "');\"  data-bs-toggle='modal' data-bs-target='#view_resume_list'><img src='../assets/images/attachment.png'></a>");
                            str.append("<span class='switch_bth'>");
                            str.append("<div class='form-check form-switch'>");
                            str.append("<input class='form-check-input' type='checkbox' role='switch' id='flexSwitchCheckDefault_" + (i) + "'");
                            if (!editper.equals("Y")) {
                                str.append("disabled='true'");
                            }
                            if (status == 1) {
                                str.append("checked");
                            }
                            str.append(" onclick=\"javascript: deleteAssetForm('" + info.getClientassetId() + "', '" + info.getStatus() + "', '" + (i) + "');\" />");
                            str.append("</div>");
                            str.append("</span>");
                            str.append("</td>");
                            str.append("</tr>");
                        }
                    }
                }
                String st = str.toString();
                str.setLength(0);
                response.getWriter().write(st);
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