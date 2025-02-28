<%@page contentType="text/html"%>
<%@page language="java" import="java.util.ArrayList"%>
<%@page pageEncoding="UTF-8"%>
<%@page import="com.web.jxp.user.UserInfo" %>
<%@page import="com.web.jxp.knowledgebasematrix.KnowledgebasematrixInfo" %>
<jsp:useBean id="knowledgebasematrix" class="com.web.jxp.knowledgebasematrix.Knowledgebasematrix" scope="page"/>
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
                ArrayList wellnessmatrix_list = new ArrayList();
                if (session.getAttribute("WELLMATRIX_POS_LIST") != null) {
                    wellnessmatrix_list = (ArrayList) session.getAttribute("WELLMATRIX_POS_LIST");
                }
                int total = wellnessmatrix_list.size();
                if (total > 0) 
                {
                    wellnessmatrix_list = knowledgebasematrix.getFinalRecordposition(wellnessmatrix_list, colid, updown);
                    session.setAttribute("WELLMATRIX_POS_LIST", wellnessmatrix_list);

                    KnowledgebasematrixInfo minfo;
                    for (int i = 0; i < total; i++) 
                    {
                        minfo = (KnowledgebasematrixInfo) wellnessmatrix_list.get(i);
                        if (minfo != null)
                        {
                            int status = 0;
                            if (minfo.getStatusCount() > 0) {
                                status = 1;
                            }
                            str.append("<tr>");
                            str.append("<td>");
                            str.append(minfo.getName() != null ? minfo.getName() : "");
                            str.append("</td>");
                            str.append("<td class='assets_list text-center'>");
                            if (minfo.getCount1() > 0) {
                                str.append("<a href='javascript:;'>");
                                str.append(knowledgebasematrix.changeNum(minfo.getCount1(), 2));
                            } else {
                                str.append("&nbsp;");
                            }
                            str.append("</a></td>");
                            str.append("<td class='assets_list text-center'>");
                            if (minfo.getCount2() > 0) {
                                str.append("<a href='javascript:;'>");
                                str.append(knowledgebasematrix.changeNum(minfo.getCount2(), 2));
                            } else {
                                str.append("&nbsp;");
                            }
                            str.append("</a></td>");
                            str.append("<td class='assets_list text-center'>");
                            if (minfo.getCount3() > 0) {
                                str.append("<a href='javascript:;'>");
                                str.append(knowledgebasematrix.changeNum(minfo.getCount3(), 2));
                            } else {
                                str.append("&nbsp;");
                            }
                            str.append("</a></td>");
                            str.append("<td class='action_column'>");
                            str.append("<a class='mr_15' href=\"javascript: assignquestion('" + minfo.getPositionId() + "');\">");
                            if (minfo.getCount1() <= 0) {
                                str.append("<img src='../assets/images/link.png' />");
                            } else if (status == 1) {
                                str.append("<img src='../assets/images/pencil.png' />");
                            }
                            str.append("</a>");
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