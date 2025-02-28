<%@page contentType="text/html"%>
<%@page language="java" import="java.util.ArrayList"%>
<%@page pageEncoding="UTF-8"%>
<%@page import="com.web.jxp.user.UserInfo" %>
<%@page import="com.web.jxp.managetraining.ManagetrainingInfo" %>
<jsp:useBean id="managetraining" class="com.web.jxp.managetraining.Managetraining" scope="page"/>
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
                if (session.getAttribute("MANAGETRAINING_LIST") != null) {
                    managetraining_list = (ArrayList) session.getAttribute("MANAGETRAINING_LIST");
                }
                int total = managetraining_list.size();
                if (total > 0) 
                {
                    managetraining_list = managetraining.getFinalRecord2(managetraining_list, colid, updown);
                    session.setAttribute("MANAGETRAINING_LIST", managetraining_list);
                
                    ManagetrainingInfo info;
                    for (int i = 0; i < total; i++)
                    {
                        info = (ManagetrainingInfo) managetraining_list.get(i);
                        if (info != null) 
                        {
                            str.append("<tr class='hand_cursor' href='javascript: void(0);'>");
                            str.append("<td class='hand_cursor' href='javascript: void(0);' onclick=\"javascript: assign2('"+info.getCourseId()+"');\">" + (info.getCourseName()!= null ? info.getCourseName(): "") + "</td>");
                            str.append("<td class='hand_cursor' href='javascript: void(0);' onclick=\"javascript: assign2('"+info.getCourseId()+"');\">" + (info.getCategoryName() != null ? info.getCategoryName(): "")+"</td>"); 
                            str.append("<td class='hand_cursor' href='javascript: void(0);' onclick=\"javascript: assign2('"+info.getCourseId()+"');\">" + (info.getSubcategoryName() != null ? info.getSubcategoryName(): "")+"</td>");
                            str.append("<td class='assets_list text-center'><a href=\"javascript:;\">"+managetraining.changeNum(info.getTotal(),2)+"</a></td>");
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