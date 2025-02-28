<%@page contentType="text/html"%>
<%@page language="java" import="java.util.ArrayList"%>
<%@page pageEncoding="UTF-8"%>
<%@page import="com.web.jxp.user.UserInfo" %>
<%@page import="com.web.jxp.jobpost.JobPostInfo" %>
<jsp:useBean id="jobpost" class="com.web.jxp.jobpost.JobPost" scope="page"/>
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
                ArrayList jobpost_list = new ArrayList();
                if (session.getAttribute("JOBPOST_LIST") != null) {
                    jobpost_list = (ArrayList) session.getAttribute("JOBPOST_LIST");
                }
                int total = jobpost_list.size();
                if (total > 0) 
                {
                    jobpost_list = jobpost.getFinalRecord(jobpost_list, colid, updown);
                    session.setAttribute("JOBPOST_LIST", jobpost_list);
                
                    int status;
                    JobPostInfo info;
                    for (int i = 0; i < total; i++) 
                    {
                        info = (JobPostInfo) jobpost_list.get(i);
                        if (info != null) 
                        {
                            status = info.getStatus();
                            str.append("<tr>");
                            str.append("<td class='hand_cursor' href='javascript: void(0);' onclick='showDetail(" + info.getJobpostId() + ");'>" + (info.getRefno() != null ? info.getRefno() : "") + "</td>");
                            str.append("<td class='hand_cursor' href='javascript: void(0);' onclick='showDetail(" + info.getJobpostId() + ");'>" + (info.getPoston() != null ? info.getPoston() : "") + "</td>");
                            str.append("<td class='hand_cursor' href='javascript: void(0);' onclick='showDetail(" + info.getJobpostId() + ");'>" + (info.getClientname() != null ? info.getClientname() : "") + "</td>");
                            str.append("<td class='hand_cursor' href='javascript: void(0);' onclick='showDetail(" + info.getJobpostId() + ");'>" + (info.getPositionname() != null ? info.getPositionname() : "") + "</td>");
                            str.append("<td class='hand_cursor' href='javascript: void(0);' onclick='showDetail(" + info.getJobpostId() + ");'>" + (info.getGrade() != null ? info.getGrade() : "") + "</td>");
                            str.append("<td class='hand_cursor' href='javascript: void(0);' onclick='showDetail(" + info.getJobpostId() + ");'>" + (info.getTargetmobdate() != null ? info.getTargetmobdate() : "") + "</td>");
                            str.append("<td class='assets_list text-center hand_cursor'><a href='javascript: void(0); onclick='showDetail('" + info.getJobpostId() + "');'>" + (info.getOpening() != null ? info.getOpening() : "&nbsp;") + "</a></td>");
                            str.append("<td class='action_column'>");
                             if (status == 1) {
                            str.append("<a href=\"javascript: searchFormShortlist('" + (info.getJobpostId()) + "');\" class='mr_15'><img src='../assets/images/view_icon.png'></a>");
                             }
                            if (editper.equals("Y") && status == 1) {
                                str.append("<a href=\"javascript: modifyForm('" + (info.getJobpostId()) + "');\" class='mr_15'><img src='../assets/images/pencil.png'></a>");
                            }
                            else
                            { 
                                str.append("<a href='javascript:;' ><span style='width: 35px;'>&nbsp;</span></a>");
                            }
                            if(status == 3)
                                str.append("<span class='switch_bth close_switch'>");
                            else
                                str.append("<span class='switch_bth'>");
                            str.append("<div class='form-check form-switch'>");
                            str.append("<input class='form-check-input' type='checkbox' role='switch' id='flexSwitchCheckDefault_" + (i) + "' ");
                            if (!editper.equals("Y") || status == 3) {
                                str.append("  disabled = 'true'");
                            }
                            if (status == 1 || status == 3) {
                                str.append("  checked");
                            }
                            str.append(" onclick=\"javascript: deleteForm('" + info.getJobpostId() + "', '" + status + "', '" + (i) + "');\"/>");
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