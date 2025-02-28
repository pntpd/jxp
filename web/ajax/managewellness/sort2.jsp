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
                ArrayList managetraining_list = new ArrayList();
                if (session.getAttribute("MANAGEWELLNESS_LIST") != null) {
                    managetraining_list = (ArrayList) session.getAttribute("MANAGEWELLNESS_LIST");
                }
                int total = managetraining_list.size();
                if (total > 0) 
                {
                    managetraining_list = managewellness.getFinalRecord2(managetraining_list, colid, updown);
                    session.setAttribute("MANAGETRAINING_LIST", managetraining_list);
                
                    ManagewellnessInfo info;
                    for (int i = 0; i < total; i++)
                    {
                        info = (ManagewellnessInfo) managetraining_list.get(i);
                        if (info != null) 
                        {
                            str.append("<tr class='hand_cursor' href='javascript: void(0);'>");
                            str.append("<td class='select_check_box'>");
                            if(info.getFromdate().equals("") && info.getTodate().equals("") ){
                            str.append("<label class='mt-checkbox mt-checkbox-outline'>"); 
                            str.append("<input type='checkbox' value='"+info.getSubcategoryId()+"' name='subcategorycb' class='singlechkbox' onchange=\"javascript: setcb();\" />");
                            str.append("<span></span>");
                            str.append("</label>");
                            } else {
                            str.append("&nbsp;");}
                            str.append("</td>");
                            str.append("<td class='hand_cursor' href='javascript: void(0);' onclick=\"javascript: ;\">" + (info.getSubcategoryName() != null ? info.getSubcategoryName(): "")+"</td>");
                            str.append("<td class='hand_cursor' href='javascript: void(0);' onclick=\"javascript: ;\">" + (info.getCategoryName() != null ? info.getCategoryName(): "")+"</td>"); 
                            str.append("<td class='assets_list text-center'><a href='javascript:;' data-bs-toggle='modal' data-bs-target='#medical_emer_details_modal_01' onclick=\"javascript: getqlist('"+info.getSubcategoryId()+"');\">"+managewellness.changeNum(info.getQuestCount(),2)+"</a></td>");
                            str.append("<td class='assets_list text-center'><a href=\"javascript: assign2('"+info.getSubcategoryId()+"');\">"+managewellness.changeNum(info.getPcount(),2)+"</a></td>");
                            str.append("<td class='hand_cursor' href='javascript: void(0);' onclick=\"javascript: ;\">" + (info.getRepeatvalue()!= null ? info.getRepeatvalue(): "") + "</td>");
                            str.append("<td class='hand_cursor' href='javascript: void(0);' onclick=\"javascript: ;\">" + (info.getFromdate()!= null ? info.getFromdate(): "") + "</td>");
                            str.append("<td class='hand_cursor' href='javascript: void(0);' onclick=\"javascript: ;\">" + (info.getTodate()!= null ? info.getTodate(): "") + "</td>");
                            str.append("<td class='text-center' data-org-colspan='1' data-columns='tech-companies-1-col-8'>");
                            str.append("<div class='main-nav'>");
                            str.append("<ul>");
                            str.append("<li class='drop_down'>");
                            str.append("<a href='javascript:;' class='toggle'><i class='fas fa-ellipsis-v'></i></a>");
                            str.append("<div class='dropdown_menu'>");
                            str.append("<div class='dropdown-wrapper'>");
                            str.append("<div class='category-menu'>");
                            str.append("<a href=\"javascript:saveQuestionmodal('"+info.getSubcategoryId()+"','"+info.getSchedulecb()+"');\">View Personnel</a>");
                            str.append("<a href=\"javascript:viewtraing();\">View Sub-category</a>");
                            str.append("</div>");
                            str.append("</div>");
                            str.append("</div>");
                            str.append("</li>");
                            str.append("</ul>");
                            str.append("</div>");
                            str.append("</td>");
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