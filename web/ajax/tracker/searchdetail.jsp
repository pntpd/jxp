<%@page contentType="text/html"%>
<%@page language="java" import="java.util.ArrayList"%>
<%@page pageEncoding="UTF-8"%>
<%@page import="com.web.jxp.user.UserInfo" %>
<%@page import="com.web.jxp.tracker.TrackerInfo" %>
<jsp:useBean id="tracker" class="com.web.jxp.tracker.Tracker" scope="page"/>
<jsp:useBean id="vobj" class="com.web.jxp.base.Validate" scope="page"/>
<%
    try
    {
        if (session.getAttribute("LOGININFO") != null) 
        {
            UserInfo uinfo = ((UserInfo) request.getSession().getAttribute("LOGININFO"));
            String addper = "", editper = "N";
            int uId = 0, cassessor = 0;
            if (uinfo != null) 
            {
                    addper = uinfo.getAddper() != null ? uinfo.getAddper() : "N";
                    editper = uinfo.getEditper() != null ? uinfo.getEditper() : "N";
                    uId = uinfo.getUserId();
                    cassessor = uinfo.getCassessor();
            }            
            if (request.getParameter("assetIdIndex") != null)
            {
                StringBuilder str = new StringBuilder();
                String crewrotationId_s = request.getParameter("crewrotationId") != null ? vobj.replaceint(request.getParameter("crewrotationId")) : "0";
                String positionId_s = request.getParameter("positionId") != null ? vobj.replaceint(request.getParameter("positionId")) : "0";
                String assetIdIndex_s = request.getParameter("assetIdIndex") != null ? vobj.replaceint(request.getParameter("assetIdIndex")) : "0";
                String passessmenttypeId_s = request.getParameter("passessmenttypeId") != null ? vobj.replaceint(request.getParameter("passessmenttypeId")) : "0";
                String priorityId_s = request.getParameter("priorityId") != null && !request.getParameter("priorityId").equals("") ? vobj.replaceint(request.getParameter("priorityId")) : "0";
                String ftype_s = request.getParameter("ftype") != null && !request.getParameter("ftype").equals("") ? vobj.replaceint(request.getParameter("ftype")) : "0";
                String search = request.getParameter("search") != null && !request.getParameter("search").equals("") ? request.getParameter("search") : "";
                int passessmenttypeId = Integer.parseInt(passessmenttypeId_s);
                int priorityId = Integer.parseInt(priorityId_s);
                int ftype = Integer.parseInt(ftype_s);
                int crewrotationId = Integer.parseInt(crewrotationId_s);
                int positionId = Integer.parseInt(positionId_s);
                int assetIdIndex = Integer.parseInt(assetIdIndex_s);
                
                ArrayList list = tracker.getListAssign1(assetIdIndex, positionId, crewrotationId, passessmenttypeId, priorityId, ftype, search, cassessor, uId);
                session.setAttribute("ASSIGNLIST1", list);
                int total = list.size();
                if(total > 0)
                {
                    for(int  i = 0; i < total; i++)
                    {
                        TrackerInfo minfo = (TrackerInfo) list.get(i);
                        if(minfo != null)
                        {
                            str.append("<tr>");
                                str.append("<td class='select_check_box'>");
                                if(minfo.getStatus() <= 1)
                                {
                                    str.append("<label class='mt-checkbox mt-checkbox-outline'>"); 
                                        str.append("<input type='checkbox' value='"+minfo.getFcroleId()+"' name='fcrolecb' class='singlechkbox' onchange='javascript: setcb();' />");
                                        str.append("<span></span>");
                                    str.append("</label>");	
                                }
                                str.append("</td>");
                                str.append("<td>"+(minfo.getRole() != null ? minfo.getRole() : "")+"</td>");
                                str.append("<td>"+(minfo.getPassessmenttypeName() != null ? minfo.getPassessmenttypeName() : "")+"</td>"); 
                                str.append("<td>"+(minfo.getPriorityName() != null ? minfo.getPriorityName() : "")+"</td>");
                                str.append("<td>"+tracker.getStColour(minfo.getStatus())+" "+(minfo.getStatusval() != null ? minfo.getStatusval() : "")+"</td>");
                                str.append("<td class='text-center'>&nbsp;</td>");
                                str.append("<td>"+(minfo.getDate() != null && !minfo.getDate().equals("")  ? minfo.getDate() : "-")+"</td>");
                                if(minfo.getAverage() > 0)
                                    str.append("<td class='text-center'>"+(minfo.getAverage())+"</td>");
                                else
                                     str.append("<td class='text-center'>-</td>");
                                str.append("<td class='action_column text-center'>");
                                if(addper.equals("Y") || editper.equals("Y"))
                                {
                                    if(minfo.getTrackerId() > 0)
                                        str.append("<a class='' data-bs-toggle='modal' data-bs-target='#unassigned_details_modal_01' href='javascript:;' onclick=\"javascript: updatetracker('"+minfo.getTrackerId()+"','1');\"><img src='../assets/images/view.png' /></a>");
                                    else
                                        str.append("<a class='' data-bs-toggle='modal' data-bs-target='#unassigned_details_modal_01' href='javascript:;' onclick=\"javascript: setPersonalModal('-1','"+minfo.getTrackerId()+"','"+minfo.getFcroleId()+"', '1');\"><img src='../assets/images/view.png' /></a>");
                                }
                                else
                                    str.append("&nbsp;");
                                str.append("</td>");
                            str.append("</tr>");
                        }
                    }
                }
                 else{
                   str.append("<tr>");
                         str.append("<td colspan='10'>No information available.</td>");  
                    str.append("</tr>");
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