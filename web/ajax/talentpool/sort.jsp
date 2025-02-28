<%@page contentType="text/html"%>
<%@page language="java" import="java.util.ArrayList"%>
<%@page pageEncoding="UTF-8"%>
<%@page import="com.web.jxp.user.UserInfo" %>
<%@page import="com.web.jxp.talentpool.TalentpoolInfo" %>
<jsp:useBean id="talentpool" class="com.web.jxp.talentpool.Talentpool" scope="page"/>
<jsp:useBean id="vobj" class="com.web.jxp.base.Validate" scope="page"/>
<%
    try 
    {
        if (session.getAttribute("LOGININFO") != null) 
        {
            if (request.getParameter("col") != null) 
            {
                StringBuilder str = new StringBuilder();
                String colid = request.getParameter("col") != null ? vobj.replaceint(request.getParameter("col")) : "";
                String updowns = request.getParameter("updown") != null && !request.getParameter("updown").equals("") ? vobj.replaceint(request.getParameter("updown")) : "0";
                String nextValue = request.getParameter("nextValue") != null && !request.getParameter("nextValue").equals("") ? vobj.replaceint(request.getParameter("nextValue")) : "0";
                int n = Integer.parseInt(nextValue);
                int updown = Integer.parseInt(updowns);
                int next_value = n;
                n = n - 1;
                session.setAttribute("NEXTVALUE", next_value + "");
                ArrayList candidate_list = new ArrayList();
                if (session.getAttribute("CANDIDATE_LIST") != null) {
                    candidate_list = (ArrayList) session.getAttribute("CANDIDATE_LIST");
                }
                int total = candidate_list.size();
                if (total > 0) 
                {
                    candidate_list = talentpool.getFinalRecord(candidate_list, colid, updown);
                    session.setAttribute("CANDIDATE_LIST", candidate_list);
                
                    TalentpoolInfo info;
                    for (int i = 0; i < total; i++) 
                    {
                        info = (TalentpoolInfo) candidate_list.get(i);
                        int vflag = 0, progress = 0, clientId = 0;
                        if (info != null) 
                        {
                            vflag = info.getVflag();
                            progress = info.getProgressId();
                            clientId = info.getClientId();
                            str.append("<tr class='hand_cursor' href='javascript: void(0);' \">");
                            str.append("<td class='ocs_cer_index text-center' data-org-colspan='1' data-columns='tech-companies-1-col-0'>");
                            if(vflag == 4) {
                                str.append("<img src='../assets/images/ocs_certified_index.png'>");
                            } else {
                                str.append("<a href='javascript:;' ><span style='width: 35px;'>&nbsp;</span></a>");
                            }
                            str.append("</td>");
                            str.append("<td class='hand_cursor' href='javascript: void(0);' onclick=\"javascript: view('" + info.getCandidateId() + "');\">" + (info.getEmployeeId() != null ? info.getEmployeeId() : "") + "</td>");
                            str.append("<td class='hand_cursor' href='javascript: void(0);' onclick=\"javascript: view('" + info.getCandidateId() + "');\">" + (info.getName() != null ? info.getName() : "") + "</td>");
                            str.append("<td class='hand_cursor' href='javascript: void(0);' onclick=\"javascript: view('" + info.getCandidateId() + "');\">" + (info.getPosition() != null ? info.getPosition() : "") + "</td>");
                            str.append("<td class='hand_cursor' href='javascript: void(0);' onclick=\"javascript: view('" + info.getCandidateId() + "');\">" + (info.getClientName() != null ? info.getClientName() : "") + "</td>");
                            str.append("<td class='hand_cursor' href='javascript: void(0);' onclick=\"javascript: view('" + info.getCandidateId() + "');\">" + (info.getCountryName() != null ? info.getCountryName() : "") + "</td>");
                            str.append("<td class='hand_cursor' href='javascript: void(0);' onclick=\"javascript: view('" + info.getCandidateId() + "');\">" + (info.getClientAsset() != null ? info.getClientAsset() : "") + "</td>");
                            str.append("<td><a href='javascript:;' data-bs-toggle='modal' data-bs-target='#client_position' onclick=\"javascript: viewAlertmodal('"+ info.getCandidateId()+"','"+ info.getName()+"','"+ info.getPositionname()+"','"+ info.getGradename()+"');\"><span class='alert_icon'><i class='ion ion-md-information-circle-outline'></i></span></a> "+ talentpool.changeNum( info.getAlertCount(),2) +"</td>");
                            str.append("<td class='hand_cursor text-center' data-org-colspan='1' data-columns='tech-companies-1-col-7'>");
                            if(clientId > 0) 
                            {
                                str.append("<img class='cer_img' src='../assets/images/active_status.png'>");
                            }
                            else if(clientId <= 0 && info.getStatus() == 4) 
                            {
                                str.append("<img class='cer_img' src='../assets/images/deceased_status.png'>"); 
                            }else 
                            {
                                str.append("<img class='cer_img' src='../assets/images/inactive_status.png'>");                                        
                            }
                            str.append("&nbsp;");
                            if(progress == 1) {
                            str.append("<img class='cer_img lock_icon' src='../assets/images/lock.png'>");
                            } 
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