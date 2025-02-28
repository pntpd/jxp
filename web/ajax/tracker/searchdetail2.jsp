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
            if (request.getParameter("pcodeId") != null)
            {
                StringBuilder str = new StringBuilder();
                String pcodeId_s = request.getParameter("pcodeId") != null ? vobj.replaceint(request.getParameter("pcodeId")) : "0";
                String assetIdIndex_s = request.getParameter("assetIdIndex") != null ? vobj.replaceint(request.getParameter("assetIdIndex")) : "0";
                String ftype_s = request.getParameter("ftype") != null && !request.getParameter("ftype").equals("") ? vobj.replaceint(request.getParameter("ftype")) : "0";
                String search = request.getParameter("search") != null && !request.getParameter("search").equals("") ? request.getParameter("search") : "";
               String positionId2Index_s = request.getParameter("positionId2Index") != null && !request.getParameter("positionId2Index").equals("") ? vobj.replaceint(request.getParameter("positionId2Index")) : "-1";
                int positionId2Index = Integer.parseInt(positionId2Index_s);
                int pcodeId = Integer.parseInt(pcodeId_s);
                int ftype = Integer.parseInt(ftype_s);
                int assetIdIndex = Integer.parseInt(assetIdIndex_s);
                
                ArrayList list = tracker.getListAssign2(assetIdIndex, pcodeId, ftype, search, positionId2Index, cassessor, uId);
                session.setAttribute("ASSIGNLIST2", list);
                int total = list.size();
                if(total > 0)
                {
                    for(int  i = 0; i < total; i++)
                    {
                        TrackerInfo minfo = (TrackerInfo) list.get(i);
                        if(minfo != null)
                        {
                            str.append("<tr>");
                                str.append("<td>"+(minfo.getName() != null ? minfo.getName() : "")+"</td>");
                                str.append("<td>"+(minfo.getPositionName() != null ? minfo.getPositionName() : "")+"</td>"); 
                                str.append("<td>"+tracker.getStColour(minfo.getStatus()) + " " + (minfo.getStatusval() != null ? minfo.getStatusval() : "")+"</td>");
                                str.append("<td class='text-center'>&nbsp;</td>");
                                str.append("<td>"+(minfo.getDate() != null && !minfo.getDate().equals("") ? minfo.getDate() : "-")+"</td>"); 
                                str.append("<td class='text-center'>");
                                if(minfo.getAverage() > 0) 
                                    str.append(minfo.getAverage());
                                else 
                                    str.append("-");
                                str.append("</td>");
                                str.append("<td class='action_column text-center'>");
                                if(addper.equals("Y") || editper.equals("Y"))
                                {
                                    if(minfo.getTrackerId() > 0)
                                        str.append("<a class='' data-bs-toggle='modal' data-bs-target='#unassigned_details_modal_01' href='javascript:;' onclick=\"javascript: updatetracker('"+minfo.getTrackerId()+"','2');\"><img src='../assets/images/view.png' /></a>");
                                    else
                                        str.append("<a class='' data-bs-toggle='modal' data-bs-target='#unassigned_details_modal_01' href='javascript:;' onclick=\"javascript: setPersonalModal('"+minfo.getCrewrotationId()+"','"+minfo.getTrackerId()+"' '"+pcodeId+"', ,'2');\"><img src='../assets/images/view.png' /></a>");
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
                         str.append("<td colspan='8'>No information available.</td>");  
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