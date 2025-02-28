<%@page import="java.util.TreeSet"%>
<%@page import="java.util.SortedSet"%>
<%@page import="java.util.LinkedList"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.Collection"%>
<%@page import="org.json.JSONObject"%>
<%@page import="java.io.BufferedReader"%>
<%@page contentType="text/html"%>
<%@page language="java" import="java.util.ArrayList"%>
<%@page import="com.web.jxp.user.UserInfo" %>
<%@page import="com.web.jxp.assessment.AssessmentInfo" %>
<%@page import="com.web.jxp.assessmentparameter.AssessmentParameterInfo" %>
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
                String clientassetids = request.getParameter("clientassetid") != null ? vobj.replaceint(request.getParameter("clientassetid")) : "0";
                String assettypeIds = request.getParameter("assettypeId") != null ? vobj.replaceint(request.getParameter("assettypeId")) : "0";
                String s = "";
                int clientassetid = Integer.parseInt(clientassetids);
                int assettypeId = Integer.parseInt(assettypeIds);
                ArrayList list = client.getpositionlistformodel(clientassetid);
                ArrayList coll = client.getPositionbynotincap(clientassetid, assettypeId);
                StringBuffer sb = new StringBuffer();
                int size = list.size();
                if (clientassetid > 0)
                {
                    sb.append("<h2>POSITIONS</h2>");
                    sb.append("<div class='full_width client_position_table'>");
                    sb.append("<div class='table-responsive mb-0'>");
                    sb.append("<table id='tech-companies-1' class='table table-striped'>");
                    sb.append("<thead>");
                    sb.append("<tr>");
                    sb.append("<th width='%'>");
                    sb.append("<span><b>Position</b> </span>");
                    sb.append("</th>");
                    sb.append("<th width='%'>");
                    sb.append("<span><b>Rank</b></span>");
                    sb.append("</th>");
                    sb.append("<th width='%'>");
                    sb.append("<span><b>Asset type</b></span>");
                    sb.append("</th>");
                    sb.append("<th width='%' class='text-right'>");
                    sb.append("<span><b>Actions</b></span>");
                    sb.append("</th>");
                    sb.append("</tr>");
                    sb.append("</thead>");
                    sb.append("<tbody>");
                    
                    for (int i = 0; i < size; i++)
                    {
                        ClientInfo info = (ClientInfo) list.get(i);
                        if (info != null) 
                        {
                            sb.append("<tr>");
                            sb.append("<td>" + info.getPosition() + "</td>");
                            sb.append("<td>" + info.getGradename() + "</td>");
                            sb.append("<td>" + info.getAssettypeName() + "</td>");
                            sb.append("<td class='action_column'>");
                            sb.append("<span class='switch_bth'>");
                            sb.append("<div class='form-check form-switch'>");
                            sb.append("<input class='form-check-input' type='checkbox' role='switch' id='flexSwitchCheckDefaultchild_" + info.getPositionId() + "' checked value ='" + info.getPositionId() + "' ");
                            if (!editper.equals("Y")) {
                                sb.append("disabled='true'");
                            }
                            sb.append("onclick=\"javascript: deleteclientassetposition('" + info.getClientassetpositionId() + "','" + clientassetid + "','" + info.getPositionId() + "','" + assettypeId + "');\"/>");
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
                    if (addper.equals("Y")) 
                    {
                        sb.append("<div class='row'>");
                        sb.append("<div class='col-lg-8 col-md-8 col-sm-8 col-12 form_group'>");
                        sb.append("<label class='form_label'>Add Position</label>");
                        sb.append("<div class='row'>");
                        sb.append("<div class='col-lg-10 col-md-12 col-sm-12 col-12'>");
                        sb.append("<div class='input-group flex_div'> ");

                        sb.append("<a href=\"javascript: setpositionmodel('" + clientassetid + "','" + assettypeId + "');\" class='input-group-text refresh_btn'><i class='ion ion-md-refresh'></i></a>");

                        sb.append("<select name='positionIds' id='positionIds' class='form-select form-control btn btn-default mt-multiselect' multiple='multiple' data-select-all='true' data-label='left' data-width='100%' data-filter='false>");
                        int sizeco = coll.size();
                        if (sizeco > 0)
                        {
                            for (int i = 0; i < sizeco; i++)
                            {
                                ClientInfo rinfo = (ClientInfo) coll.get(i);
                                int val = rinfo.getDdlValue();
                                String n = rinfo.getDdlLabel() != null ? rinfo.getDdlLabel() : "";
                                sb.append("<option value='" + val + "'>" + n + "</option>");
                            }
                        }

                        sb.append("</select>");
                        sb.append("</div>");
                        sb.append("</div>");
                        sb.append("<div class='col-lg-2 col-md-12 col-sm-12 col-12 pd_left_null'>");

                        sb.append("<a href='/jxp/position/PositionAction.do?doAdd=yes' target='_blank' class='add_btn'><i class='mdi mdi-plus'></i></a>");

                        sb.append("</div>");
                        sb.append("</div>");
                        sb.append("</div>");

                        sb.append("<div class='col-lg-4'  id = 'godiv'><a href=\"javascript: addtoclientassetposition('" + clientassetid + "','" + assettypeId + "');\" class='save_page mt_10 pull_right'><img src=\"../assets/images/save.png\"> Save</a></div>");
                    }
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