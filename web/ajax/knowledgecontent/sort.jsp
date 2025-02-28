<%@page contentType="text/html"%>
<%@page language="java" import="java.util.ArrayList"%>
<%@page pageEncoding="UTF-8"%>
<%@page import="com.web.jxp.user.UserInfo" %>
<%@page import="com.web.jxp.knowledgecontent.KnowledgecontentInfo" %>
<jsp:useBean id="knowledgecontent" class="com.web.jxp.knowledgecontent.Knowledgecontent" scope="page"/>
<jsp:useBean id="vobj" class="com.web.jxp.base.Validate" scope="page"/>
<%
    try 
    {
        if (session.getAttribute("LOGININFO") != null)
        {
            UserInfo uinfo = ((UserInfo) request.getSession().getAttribute("LOGININFO"));
            String editper = "N", addper = "N";
            if (uinfo != null) 
            {
                editper = uinfo.getEditper() != null ? uinfo.getEditper() : "N";
                addper = uinfo.getAddper()!= null ? uinfo.getAddper() : "N";
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
                ArrayList createtraining_list = new ArrayList();
                if (session.getAttribute("CREATETRAINING_LIST") != null) {
                    createtraining_list = (ArrayList) session.getAttribute("CREATETRAINING_LIST");
                }
                int total = createtraining_list.size();
                if (total > 0)
                {
                    createtraining_list = knowledgecontent.getFinalRecord(createtraining_list, colid, updown);
                    session.setAttribute("CREATETRAINING_LIST", createtraining_list);
                
                    int status;
                    KnowledgecontentInfo info;
                    for (int i = 0; i < total; i++) 
                    {
                        info = (KnowledgecontentInfo) createtraining_list.get(i);
                        if (info != null) 
                        {
                            status = info.getStatus();
                            str.append("<tr class='hand_cursor' href='javascript: void(0);'>");
                            str.append("<td class='hand_cursor' href='javascript: void(0);' onclick=\"javascript: showDetail('" + info.getCategoryId()+ "');\">" + (info.getName() != null ? info.getName() : "") + "</td>");
                            str.append("<td class='assets_list text-center'><a href=\"javascript: viewSubcategory('"+ info.getCategoryId()+"');\">"+  knowledgecontent.changeNum( info.getSubcategorycount(),2)+"</a></td>");
                            str.append("<td class='assets_list text-center'><a href=\"javascript: viewCoursecat('"+info.getCategoryId()+"');\">"+  knowledgecontent.changeNum( info.getCoursecount(),2)+"</a></td>");
                            str.append("<td class='action_column'>");                            
                            if (addper.equals("Y") && info.getStatus() == 1) 
                            {                                 
                                str.append("<a class='mr_15' href=\" javascript: addsubcategoryForm('"+info.getCategoryId()+"');\"><img src='../assets/images/plus.png'></a>");
                            }
                            str.append("<span class='switch_bth float-end'>");
                            str.append("<div class='form-check form-switch'>");
                            str.append("<input class='form-check-input' type='checkbox' role='switch' id='flexSwitchCheckDefault_" + (i) + "'");
                            if (!editper.equals("Y")) {
                                str.append("disabled='true'");
                            }
                            if (status == 1) {
                                str.append("checked");
                            }
                            str.append(" onclick=\"javascript: deleteForm('" + info.getCategoryId() + "', '" + status + "', '" + (i) + "');\" />");
                            str.append("</div>");
                            str.append("</span>");
                            if (editper.equals("Y") && info.getStatus() == 1) {
                                str.append("<a href=\"javascript: modifyForm('" + info.getCategoryId() + "');\" class='edit_mode float-end mr_15'><img src='../assets/images/pencil.png'/></a>");
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