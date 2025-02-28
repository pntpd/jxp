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
            String addper = "N", editper = "N";
            if (uInfo != null) 
            {
                addper = uInfo.getAddper() != null ? uInfo.getAddper() : "N";
                editper = uInfo.getEditper() != null ? uInfo.getEditper() : "N";                
            }
            if (userId > 0) 
            {
                String clientassetIds = request.getParameter("clientassetId") != null ? vobj.replaceint(request.getParameter("clientassetId")) : "0";
                String assettypeIds = request.getParameter("assettypeId") != null ? vobj.replaceint(request.getParameter("assettypeId")) : "0";
                String s = "";
                int clientassetId = Integer.parseInt(clientassetIds);
                int assettypeId = Integer.parseInt(assettypeIds);
                ArrayList list = client.getDeptList(clientassetId, assettypeId);                
                int size = list.size();
                
                StringBuilder sb = new StringBuilder();
                if (clientassetId > 0) 
                {
                    sb.append("<input type='hidden' name='assetIdModal' id='assetIdModal' value='"+clientassetId+"'/>");
                    sb.append("<h2>DEPARTMENTS</h2>");
                    sb.append("<div class='full_width client_position_table'>");
                    sb.append("<div class='table-rep-plugin sort_table'>");
                    sb.append("<div class='table-responsive mb-0'>");
                    sb.append("<table id='tech-companies-1' class='table table-striped'>");
                    sb.append("<thead>");
                    sb.append("<tr>");
                    sb.append("<th width='%'><span><b>Department</b></span></th>");
                    sb.append("<th width='%' class='text-center'><span><b>Positions</b></span></th>");
                    sb.append("<th width='%' class='text-right'><span><b>Action</b></span></th>");
                    sb.append("</tr>");
                    sb.append("</thead>");
                    sb.append("<tbody>");
                    
                    for (int i = 0; i < size; i++) 
                    {
                        ClientInfo info = (ClientInfo) list.get(i);
                        if (info != null) 
                        {
                            sb.append("<tr>");
                            sb.append("<td>" + info.getName() + "</td>");
                            sb.append("<td class='assets_list text-center'><a href='javascript:;' onclick=\"javascript: setpost('"+clientassetId+"', '"+assettypeId+"', '"+info.getPdeptId()+"');\" data-bs-toggle='modal' data-bs-target='#client_position_department'>" + client.changeNum(info.getPositionCount(), 2)+ "</a></td>");
                            sb.append("<td class='action_column'>");
                            sb.append("<span class='switch_bth'>");
                            sb.append("<div class='form-check form-switch'>");
                            sb.append("<input class='form-check-input' type='checkbox' role='switch' id='flexSwitchCheckDefaultchild_" + info.getPdeptId() + "' ");
                            if(info.getPositionCount() > 0)
                                sb.append("checked ");
                            sb.append(" value ='" + info.getPdeptId() + "' ");
                            if (!editper.equals("Y") || info.getPositionCount() <= 0) {
                                sb.append("disabled='true'");
                            }
                            sb.append("onclick=\"javascript: deletedept('" + info.getPdeptId() + "','" + clientassetId + "','" + assettypeId + "');\"/>");
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
                    sb.append("<div class='row'>");
                    sb.append("<div class='col-lg-12 col-md-12 col-sm-12 col-12 form_group'>");
                        sb.append("<div class='row flex-end align-items-end'>");
                        if(addper.equals("Y") || editper.equals("Y"))
                        {
                            sb.append("<div class='col-lg-12 col-md-12 col-sm-12 col-12'>");
                                sb.append("<label class='form_label'>Add Department</label>");
                                sb.append("<div class='input-group flex_div'>");
                                    sb.append("<a href=\"javascript: setdeptmodel('"+clientassetId+"', '"+assettypeId+"', '-1');\" class='input-group-text refresh_btn'><i class='ion ion-md-refresh'></i></a>");
                                    sb.append("<a href='/jxp/pdept/PdeptAction.do' class='add_btn' target='_blank'><i class='mdi mdi-plus'></i></a>");
                                sb.append("</div>");
                            sb.append("</div>");
                        }
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