<%@page contentType="text/html"%>
<%@page language="java" import="java.util.ArrayList"%>
<%@page import="com.web.jxp.user.UserInfo" %>
<%@page import="com.web.jxp.client.ClientInfo" %>
<jsp:useBean id="client" class="com.web.jxp.client.Client" scope="page"/>
<jsp:useBean id="vobj" class="com.web.jxp.base.Validate" scope="page"/>
<%
    try
    {
        UserInfo uInfo = (UserInfo) request.getSession().getAttribute("LOGININFO");
        if (uInfo != null) 
        {
            int userId = uInfo.getUserId();
            String editper = "N";
            if (uInfo != null) 
            {
                editper = uInfo.getEditper() != null ? uInfo.getEditper() : "N";
            }
            if (userId > 0) 
            {
                String clientassetIds = request.getParameter("clientassetId") != null ? vobj.replaceint(request.getParameter("clientassetId")) : "0";
                String assettypeIds = request.getParameter("assettypeId") != null ? vobj.replaceint(request.getParameter("assettypeId")) : "0";
                String deptIds = request.getParameter("deptId") != null ? vobj.replaceint(request.getParameter("deptId")) : "0";
                String s = "";
                int clientassetId = Integer.parseInt(clientassetIds);
                int assettypeId = Integer.parseInt(assettypeIds);
                int deptId = Integer.parseInt(deptIds);
                ArrayList list = client.getPositionlistfordept(clientassetId, deptId); 
                String deptName = client.getDeptName(deptId);
                int size = list.size();
                StringBuilder sb = new StringBuilder();
                if (clientassetId > 0) 
                {
                    sb.append("<div class='modal-header'>");
                    sb.append("<button onclick=\"javascript: setdeptmodel('"+clientassetId+"', '"+assettypeId+"', '-1');\" type='button' data-bs-toggle='modal' data-bs-target='#department_modal' class='close close_modal_btn pull-right' aria-hidden='true'><i class='ion ion-md-close'></i></button>");
                    sb.append("</div>");
                    sb.append("<div class='modal-body'>");
                    sb.append("<div class='row'>");
                    sb.append("<div class='col-lg-12'>");
                    sb.append("<h2>"+deptName+" - POSITIONS</h2>");
                    sb.append("<div class='full_width client_position_table'>");
                    sb.append("<div class='table-rep-plugin sort_table'>");
                    sb.append("<div class='table-responsive mb-0'>");
                    sb.append("<table id='techcompanies-1' class='table table-striped'>");
                    sb.append("<thead>");
                    sb.append("<tr>");
                    sb.append("<th width='%'><span><b>Position</b> </span></th>");
                    sb.append("<th width='%'><span><b>Rank</b></span></th>");
                    sb.append("<th width='%' class='text-right'><span><b>Action</b></span></th>"); 
                    sb.append("</tr>");
                    sb.append("</thead>");
                    sb.append("<tbody>");
                    for (int i = 0; i < size; i++) 
                    {
                        ClientInfo info = (ClientInfo) list.get(i);
                        int setval = 1;
                        if(info.getPdeptId() > 0)
                            setval = 2;
                        if (info != null)
                        {
                            sb.append("<tr>");
                            sb.append("<td>" + (info.getPosition() != null ? info.getPosition() : "") + "</td>");
                            sb.append("<td>" + (info.getGradename() != null ? info.getGradename() : "") + "</td>");
                            sb.append("<td class='action_column'>");
                            sb.append("<span class='switch_bth'>");
                            sb.append("<div class='form-check form-switch'>");
                            sb.append("<input class='form-check-input' type='checkbox' role='switch' id='flexSwitchCheckDefaultchild_dept_" + info.getClientassetpositionId() + "' ");
                            if(info.getPdeptId() > 0)
                                sb.append("checked ");
                            sb.append(" value ='" + info.getClientassetpositionId() + "' ");
                            if (!editper.equals("Y")) {
                                sb.append("disabled='true'");
                            }
                            sb.append("onclick=\"javascript: setdepttopos('" + info.getClientassetpositionId() + "','" + clientassetId + "','" + assettypeId + "', '"+deptId+"', '"+setval+"');\"/>");
                            sb.append("</div>");
                            sb.append("</span>");
                            sb.append("</td>");
                            sb.append("</tr>");
                        }
                    }
                    sb.append("</tbody>");
                    sb.append("</table>");
                    sb.append("</div>");		
                    sb.append("</div>");
                    sb.append("</div>");
                    sb.append("</div>");
                    sb.append("</div>");
                    sb.append("</div>");
                }
                s = sb.toString();
                sb.setLength(0);
                list.clear();
                response.getWriter().write(s);
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
%>