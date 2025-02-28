<%@page contentType="text/html"%>
<%@page language="java" import="java.util.ArrayList"%>
<%@page pageEncoding="UTF-8"%>
<%@page import="com.web.jxp.user.UserInfo" %>
<%@page import="com.web.jxp.managewellness.ManagewellnessInfo" %>
<jsp:useBean id="managewellness" class="com.web.jxp.managewellness.Managewellness" scope="page"/>
<jsp:useBean id="vobj" class="com.web.jxp.base.Validate" scope="page"/>
<%
    try 
    {
        if (session.getAttribute("LOGININFO") != null) 
        {
            UserInfo uinfo = ((UserInfo) request.getSession().getAttribute("LOGININFO"));
            String addper = "", editper = "N";
            if (uinfo != null) 
            {
                    addper = uinfo.getAddper() != null ? uinfo.getAddper() : "N";
                    editper = uinfo.getEditper() != null ? uinfo.getEditper() : "N";
            }            
            if (request.getParameter("assetIdIndex") != null)
            {
                StringBuilder str = new StringBuilder();
                String crewrotationId_s = request.getParameter("crewrotationId") != null ? vobj.replaceint(request.getParameter("crewrotationId")) : "0";
                String positionId_s = request.getParameter("positionId") != null ? vobj.replaceint(request.getParameter("positionId")) : "0";
                String clientIdIndex_s = request.getParameter("clientIdIndex") != null ? vobj.replaceint(request.getParameter("clientIdIndex")) : "0";
                String assetIdIndex_s = request.getParameter("assetIdIndex") != null ? vobj.replaceint(request.getParameter("assetIdIndex")) : "0";
                String search = request.getParameter("search") != null && !request.getParameter("search").equals("") ? request.getParameter("search") : "";
                String statusIndex_s = request.getParameter("statusIndex") != null && !request.getParameter("statusIndex").equals("") ? vobj.replaceint(request.getParameter("statusIndex")) : "-1";
                String categoryIdDetail_s = request.getParameter("categoryIdDetail") != null && !request.getParameter("categoryIdDetail").equals("") ? vobj.replaceint(request.getParameter("categoryIdDetail")) : "-1";
                String subcategoryIdDetail_s = request.getParameter("subcategoryIdDetail") != null && !request.getParameter("subcategoryIdDetail").equals("") ? vobj.replaceint(request.getParameter("subcategoryIdDetail")) : "-1";
                int categoryIdDetail = Integer.parseInt(categoryIdDetail_s);
                int subcategoryIdDetail = Integer.parseInt(subcategoryIdDetail_s);
                int crewrotationId = Integer.parseInt(crewrotationId_s);
                int positionId = Integer.parseInt(positionId_s);
                int clientIdIndex = Integer.parseInt(clientIdIndex_s);
                int assetIdIndex = Integer.parseInt(assetIdIndex_s);
                int statusIndex = Integer.parseInt(statusIndex_s);
                
                ArrayList list = managewellness.getListAssign1(clientIdIndex, assetIdIndex, positionId, 
                    crewrotationId, search, statusIndex, categoryIdDetail, subcategoryIdDetail);
                int total = list.size();
                if(total > 0)
                {
                    for(int  i = 0; i < total; i++)
                    {
                        ManagewellnessInfo minfo = (ManagewellnessInfo) list.get(i);
                        if(minfo != null)
                        {
                            str.append("<tr>");
                                str.append("<td>"+(minfo.getSubcategoryName() != null ? minfo.getSubcategoryName() : "")+"</td>");
                                str.append("<td>"+(minfo.getCategoryName() != null ? minfo.getCategoryName() : "")+"</td>"); 
                                str.append("<td class='assets_list text-center' data-org-colspan='1' data-columns='tech-companies-1-col-3'>");
                                str.append("<a href=\"javascript:;\" class='off_total_value'>"+(minfo.getQuestCount())+"</a></td>");
                                str.append("<td>"+(minfo.getRepeatvalue() != null ? minfo.getRepeatvalue() : "")+"</td>");
                                str.append("<td>"+(minfo.getFromdate() != null ? minfo.getFromdate() : "")+"</td>");
                                str.append("<td>"+(minfo.getTodate() != null ? minfo.getTodate() : "")+"</td>"); 
                                str.append("<td class='action_column text-center'>");
                                if(addper.equals("Y") || editper.equals("Y"))
                                    str.append("<a class='' data-bs-toggle='modal' data-bs-target='#medical_emer_details_modal_01' href='javascript:;' onclick=\"javascript: setQuestionModal('"+minfo.getSubcategoryId()+"','"+minfo.getPositionId()+"');\"><img src='../assets/images/view.png' /></a>");
                                else
                                    str.append("&nbsp;");
                                str.append("</td>");
                            str.append("</tr>");
                        }
                    }
                }
                else{
                    str.append("<tr>");
                        str.append("<td colspan='8'>No Information available</td>");
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