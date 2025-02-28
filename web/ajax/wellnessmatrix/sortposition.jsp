<%@page contentType="text/html"%>
<%@page language="java" import="java.util.ArrayList"%>
<%@page pageEncoding="UTF-8"%>
<%@page import="com.web.jxp.user.UserInfo" %>
<%@page import="com.web.jxp.wellnessmatrix.WellnessmatrixInfo" %>
<jsp:useBean id="wellnessmatrix" class="com.web.jxp.wellnessmatrix.Wellnessmatrix" scope="page"/>
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
                ArrayList wellnessmatrix_list = new ArrayList();
                if (session.getAttribute("WELLMATRIX_POS_LIST") != null) {
                    wellnessmatrix_list = (ArrayList) session.getAttribute("WELLMATRIX_POS_LIST");
                }
                int total = wellnessmatrix_list.size();
                if (total > 0) 
                {
                    wellnessmatrix_list = wellnessmatrix.getFinalRecordposition(wellnessmatrix_list, colid, updown);
                    session.setAttribute("WELLMATRIX_POS_LIST", wellnessmatrix_list);
                
                    WellnessmatrixInfo minfo;
                    for (int i = 0; i < total; i++) 
                    {
                        minfo = (WellnessmatrixInfo) wellnessmatrix_list.get(i);
                        if (minfo != null) 
                        {
                             int status = 0;
                            if(minfo.getStatusCount() > 0)
                            {
                                status = 1;
                            }
                            str.append("<tr>");
                            str.append("<td>");
                             str.append(minfo.getName() != null ? minfo.getName() : "");
                             str.append("</td>");
                            str.append("<td class='assets_list text-center'>");
                            if(minfo.getCount1() > 0) {
                                str.append("<a href='javascript:;'>");
                                str.append(wellnessmatrix.changeNum(minfo.getCount1(), 2));
                                  } else
                            {str.append("&nbsp;"); } str.append("</a></td>");
                            str.append("<td class='assets_list text-center'>"); if(minfo.getCount2() > 0) { str.append("<a href='javascript:;'>"); str.append(wellnessmatrix.changeNum(minfo.getCount2(), 2)); } else {str.append("&nbsp;"); } str.append("</a></td>");
                            str.append("<td class='assets_list text-center'>"); 
                            if(minfo.getCount3() > 0)
                            { str.append("<a href='javascript:;'>");
                             str.append(wellnessmatrix.changeNum(minfo.getCount3(), 2)); 
                            } else {
                                str.append("&nbsp;");
                           } str.append("</a></td>");
                            str.append("<td class='action_column'>");
                        str.append("<a class='mr_15' href=\"javascript: assignquestion('"+ minfo.getPositionId()+"');\">"); if(minfo.getCount1() <= 0) { str.append("<img src='../assets/images/link.png' />"); } else if(status == 1) { str.append("<img src='../assets/images/pencil.png' />"); } str.append("<a/>");
                         if(minfo.getCount1() > 0) {
                        str.append("<span class='switch_bth float-end'>");
                        str.append("<div class='form-check form-switch'>");
                        str.append("<input class='form-check-input' type='checkbox' role='switch' id='flexSwitchCheckDefault_"+(i)+"'");
                        if(status == 1)
                        {str.append("checked"); } 
                        if(!editper.equals("Y")) {str.append("disabled='true'");
                        }   str.append("onclick=\"javascript: deleteFormDetail('"+minfo.getClientId()+"','"+minfo.getAssetId()+"','"+minfo.getPositionId()+"', '"+status+"', '"+i+"');\"/>");
                        str.append("</div>");
                        str.append("</span>");
                         } else {str.append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");} 
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