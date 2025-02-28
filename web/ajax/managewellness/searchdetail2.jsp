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
            String editper = "N";
            if (uinfo != null) 
            {
                editper = uinfo.getEditper() != null ? uinfo.getEditper() : "N";
            }
            if (request.getParameter("clientIdIndex") != null)
            {
                StringBuilder str = new StringBuilder();
                
                String clientIdIndex_s = request.getParameter("clientIdIndex") != null ? vobj.replaceint(request.getParameter("clientIdIndex")) : "0";
                String assetIdIndex_s = request.getParameter("assetIdIndex") != null ? vobj.replaceint(request.getParameter("assetIdIndex")) : "0";
                String search = request.getParameter("search") != null && !request.getParameter("search").equals("") ? request.getParameter("search") : "";
                String categoryIdIndex_s = request.getParameter("categoryIdIndex") != null && !request.getParameter("categoryIdIndex").equals("") ? vobj.replaceint(request.getParameter("categoryIdIndex")) : "-1";
                String subcategoryIdIndex_s = request.getParameter("subcategoryIdIndex") != null && !request.getParameter("subcategoryIdIndex").equals("") ? vobj.replaceint(request.getParameter("subcategoryIdIndex")) : "-1";
                int subcategoryIdIndex = Integer.parseInt(subcategoryIdIndex_s);
                
                int clientIdIndex = Integer.parseInt(clientIdIndex_s);
                int assetIdIndex = Integer.parseInt(assetIdIndex_s);
                int categoryIdIndex = Integer.parseInt(categoryIdIndex_s);
                
                ArrayList list = managewellness.getManagewellnessByName(clientIdIndex, assetIdIndex, 0, 2, search, categoryIdIndex, subcategoryIdIndex);    
                int total = list.size();
                if(total > 0)
                {
                    for(int  i = 0; i < total; i++)
                    {
                        ManagewellnessInfo minfo = (ManagewellnessInfo) list.get(i);
                        if(minfo != null)
                        {
                            str.append("<tr>");
                            if(minfo.getFromdate().equals("") && minfo.getTodate().equals("") ) 
                            {
                                str.append("<td class='select_check_box'>");
                                    str.append("<label class='mt-checkbox mt-checkbox-outline'>"); 
                                        str.append("<input type='checkbox' value='"+minfo.getCourseId()+"' name='categorycb' class='singlechkbox' onchange='javascript: setcb();' />");
                                        str.append("<span></span>");
                                    str.append("</label>");	
                                str.append("</td>");
                            }else
                            {
                                str.append("&nbsp;");
                            }
                            
                                str.append("<td>"+(minfo.getSubcategoryName() != null ? minfo.getSubcategoryName() : "")+"</td>");
                                str.append("<td>"+(minfo.getCategoryName() != null ? minfo.getCategoryName() : "")+"</td>");
                                str.append("<td class='assets_list text-center'><a href=\"javascript:;\">"+managewellness.changeNum(minfo.getQuestCount(),2)+"</a></td>");
                                str.append("<td class='assets_list text-center'><a href=\"javascript:;\">"+managewellness.changeNum(minfo.getPcount(),2)+"</a></td>");
                                str.append("<td>"+(minfo.getRepeatvalue() != null ? minfo.getRepeatvalue() : "")+"</td>");
                                str.append("<td>"+(minfo.getFromdate() != null ? minfo.getFromdate() : "")+"</td>");
                                str.append("<td>"+(minfo.getTodate() != null ? minfo.getTodate() : "")+"</td>");
                                str.append("<td>"+(minfo.getDate() != null ? minfo.getDate() : "")+"</td>"); 
                                str.append("<td class='text-center' data-org-colspan='1' data-columns='tech-companies-1-col-8'>");
                                str.append("<div class='main-nav float-start'>");
                                str.append("<ul>");
                                str.append("<li class='drop_down'>");
                                str.append("<a href=\"javascript:;\" class='toggle'><i class='fas fa-ellipsis-v'></i></a>");
                                str.append("<div class='dropdown_menu'>");
                                str.append("<div class='dropdown-wrapper'>");

                                str.append("<div class='category-menu'>");
                                str.append("<a href='' onclick = \"javascript: saveQuestionmodal('"+minfo.getSubcategoryId()+"','"+minfo.getSchedulecb()+"');\" data-bs-toggle='modal' data-bs-target='#define_schedule'>Define Schedule</a>");
                                str.append("<a href=\"javascript:;\">View Sub-category</a>");
                                str.append("</div>");
                                str.append("</div>");
                                str.append("</div>");
                                str.append("</li>");
                                str.append("</ul>");
                                str.append("</div>");
                                str.append("<span class='switch_bth float-end'>");
                                str.append("<div class='form-check form-switch'>");
                                str.append("<input class='form-check-input' type='checkbox' role='switch' id=\"flexSwitchCheckDefault_"+(i)+"\"" );
                                if(!minfo.getFromdate().equals("") && !minfo.getTodate().equals(""))
                                {
                                    str.append("checked"); }
                                if(!editper.equals("Y")) {
                                    str.append("disabled='true' ");
                                }  str.append("onclick=\"javascript: deletedateForm('"+ minfo.getSubcategoryId()+"');\"/>");
                                   str.append("</div>");
                                   str.append("</span>");
                                   str.append("</td>");
                                   str.append("</tr>");
                        }
                    }
                }
                 else{
                    str.append("<tr>");
                        str.append("<td colspan='10'>No Information available</td>");
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