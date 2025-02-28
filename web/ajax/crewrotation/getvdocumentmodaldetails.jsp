<%@page import="com.web.jxp.crewrotation.CrewrotationInfo"%>
<%@page contentType="text/html"%>
<%@page language="java" import="java.util.ArrayList"%>
<%@page pageEncoding="UTF-8"%>
<%@page import="com.web.jxp.user.UserInfo"%>
<jsp:useBean id="crewrotation" class="com.web.jxp.crewrotation.Crewrotation" scope="page"/>
<jsp:useBean id="vobj" class="com.web.jxp.base.Validate" scope="page"/>
<%
    try 
    {
        if (session.getAttribute("LOGININFO") != null)
        {
            String crewrotationIds = request.getParameter("crewrotationId") != null && !request.getParameter("crewrotationId").equals("") ? vobj.replaceint(request.getParameter("crewrotationId")) : "0";
            int crewrotationId = Integer.parseInt(crewrotationIds);
            
            CrewrotationInfo dinfo = crewrotation.getCRDocchecks(crewrotationId);
            ArrayList list = crewrotation.getList(dinfo.getYesvalues(),dinfo.getNovalues(),dinfo.getNAvalues());
            
            StringBuilder sb = new StringBuilder();
            sb.append("<h2>REQUIRED DOCUMENTS CHECKLIST</h2>");
            sb.append("<div class='row client_position_table'>");
            sb.append("<div class='col-md-12 table-rep-plugin sort_table'>");
            sb.append("<div class='table-responsive mb-0'>");
            sb.append("<table id='tech-companies-1' class='table table-striped'>");
            sb.append("<thead>");
            sb.append("<tr>");
            sb.append("<th width='65%'></th>");
            sb.append("<th width='30%'><span><b>Crew-coordinator</b> </span></th>");
            sb.append("</tr>");
            sb.append("</thead>");
            sb.append("<tbody>");
            
            if(list.size()>0)
            {
                for(int i = 0; i< list.size();i++)
                {
                    CrewrotationInfo info = (CrewrotationInfo)list.get(i);
                     sb.append("<tr>");
                     sb.append("<td>"+info.getDdlLabel()+"</td>");

                     sb.append("<td><span class='form-control'>"+info.getValues()+"</span></td>");
                     sb.append("</tr>");
                }
            }
            sb.append("</tbody>");	
            sb.append("</table>");
            sb.append("</div>");	
            sb.append("</div>");
            sb.append("</div>");
            
            String st1 = sb.toString() + "#@#" +"";
            sb.setLength(0);
            response.getWriter().write(st1);
        } else {
            response.getWriter().write("Please check your login session....");
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
%>