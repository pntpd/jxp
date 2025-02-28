<%@page contentType="text/html"%>
<%@page language="java" import="java.util.ArrayList"%>
<%@page pageEncoding="UTF-8"%>
<%@page import="com.web.jxp.user.UserInfo" %>
<%@page import="com.web.jxp.candidate.CandidateInfo" %>
<jsp:useBean id="candidate" class="com.web.jxp.candidate.Candidate" scope="page"/>
<jsp:useBean id="vobj" class="com.web.jxp.base.Validate" scope="page"/>
<%
    try 
    {
        if (session.getAttribute("LOGININFO") != null) 
        {
            UserInfo uinfo = ((UserInfo) request.getSession().getAttribute("LOGININFO"));
            String editper = "N";
            if (uinfo != null) 
            {
                editper = uinfo.getEditper() != null ? uinfo.getEditper() : "N";
            }
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
                    candidate_list = candidate.getFinalRecord(candidate_list, colid, updown);
                    session.setAttribute("CANDIDATE_LIST", candidate_list);

                    int status;
                    CandidateInfo info;
                    for (int i = 0; i < total; i++)
                    {
                        info = (CandidateInfo) candidate_list.get(i);
                        if (info != null) 
                        {
                            status = info.getStatus();
                            str.append("<tr class='hand_cursor' href='javascript: void(0);' \">");
                            str.append("<td class='hand_cursor' href='javascript: void(0);' onclick=\"javascript: view('" + info.getCandidateId() + "');\">" + (candidate.changeNum(info.getCandidateId(), 6)) + "</td>");
                            str.append("<td class='hand_cursor' href='javascript: void(0);' onclick=\"javascript: view('" + info.getCandidateId() + "');\">" + (info.getDate() != null ? info.getDate() : "") + "</td>");
                            str.append("<td class='hand_cursor' href='javascript: void(0);' onclick=\"javascript: view('" + info.getCandidateId() + "');\">" + (info.getName() != null ? info.getName() : "") + "</td>");
                            str.append("<td class='hand_cursor' href='javascript: void(0);' onclick=\"javascript: view('" + info.getCandidateId() + "');\">" + (info.getCountryName() != null ? info.getCountryName() : "") + "</td>");
                            str.append("<td class='hand_cursor' href='javascript: void(0);' onclick=\"javascript: view('" + info.getCandidateId() + "');\">" + (info.getCity() != null ? info.getCity() : "") + "</td>");
                            str.append("<td class='hand_cursor' href='javascript: void(0);' onclick=\"javascript: view('" + info.getCandidateId() + "');\">" + (info.getPosition() != null ? info.getPosition() : "") + "</td>");
                            str.append("<td class='hand_cursor' href='javascript: void(0);' onclick=\"javascript: view('" + info.getCandidateId() + "');\">" + (info.getOnlineStatus() != null ? info.getOnlineStatus() : "") + "</td>");
                            str.append("<td class='action_column'>");
                            if (status == 1) {
                                str.append("<a href='javascript:;' onclick=\"javascript: viewimg('" + (info.getCandidateId()) + "', '" + (info.getFirstname().replaceAll("['\"&\\s]", "")) + "');\" class='mr_15' data-bs-toggle='modal' data-bs-target='#view_resume_list'><img src='../assets/images/attachment.png'></a>");
                            }
                            if (info.getPassflag() == 2 || info.getPassflag() == 3) {
                            } else {
                                if (editper.equals("Y") && status == 1) {
                                    str.append("<a href=\"javascript: modifyForm('" + (info.getCandidateId()) + "');\" class=' mr_15'><img src='../assets/images/pencil.png'/></a>");
                                }
                            }
                            str.append("<span class='switch_bth'>");
                            str.append("<div class='form-check form-switch'>");
                            str.append("<input class='form-check-input' type='checkbox' role='switch' id='flexSwitchCheckDefault_" + (i) + "' ");
                            if (!editper.equals("Y") || info.getPassflag() == 2 || info.getPassflag() == 3) {
                                str.append(" disabled = 'true'");
                            }
                            if (status == 1) {
                                str.append(" checked");
                            }

                            str.append(" onclick=\"javascript: deleteForm('" + info.getCandidateId() + "', '" + status + "','" + i + "');\"/>");
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