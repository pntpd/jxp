<%@page import="java.util.Iterator"%>
<%@page import="java.util.Collection"%>
<%@page contentType="text/html"%>
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
            String str = "";
            if (request.getParameter("candidateId") != null) 
            {
                String positionIds = request.getParameter("positionId") != null && !request.getParameter("positionId").equals("") ? vobj.replaceint(request.getParameter("positionId")) : "";
                int positionId = Integer.parseInt(positionIds);
                String candidateIds = request.getParameter("candidateId") != null && !request.getParameter("candidateId").equals("") ? vobj.replaceint(request.getParameter("candidateId")) : "";
                int candidateId = Integer.parseInt(candidateIds);
                String mcrids = request.getParameter("crids") != null && !request.getParameter("crids").equals("") ? vobj.replacename(request.getParameter("crids")) : "";
                response.setContentType("text/html");
                response.setHeader("Cache-Control", "no-cache");

                ManagetrainingInfo pinfo = managetraining.getModalInfoPersonal(candidateId);
                
                String position= "";
                if(pinfo != null)
                {
                    if(positionId == pinfo.getPositionId())
                    {
                        position = pinfo.getPositionName() != null ? pinfo.getPositionName() : "";
                    }
                    else if(positionId == pinfo.getPositionId2())
                    {
                        position = pinfo.getPosition2() != null ? pinfo.getPosition2() : "";
                    }
                }

                StringBuffer sb = new StringBuffer();
                if (candidateId > 0)
                {
                    sb.append("<div class='col-lg-12'>");
                    sb.append("<div class='row m_15'>");
                    sb.append("<div class='col'>");
                    sb.append("<h2>"+pinfo.getName() +" | "+position+"</h2>");
                    sb.append("</div>");
                    sb.append("<div class='col col-lg-3 text-right'>");
                    sb.append("<span class='unassigned_mode'>Unassigned</span>");
                    sb.append("</div>");
                    sb.append("</div>");
                    sb.append("<div class='row client_position_table1'>");
                    sb.append("<div class='col-md-12'>");
                    sb.append("<div class='row'>");
                    sb.append("<div class='col-lg-4 col-md-4 col-sm-4 col-4 form_group'>");
                    sb.append("<label class='form_label'>Complete By</label>");
                    sb.append("<div class='input-daterange input-group'>");
                    sb.append("<input type='text' name='completeby' value='' id='completeby' class='form-control add-style wesl_dt date-add' placeholder='DD-MMM-YYYY'>");
                    sb.append("</div>");
                    sb.append("</div>");
                    sb.append("<div class='col-lg-8 col-md-8 col-sm-8 col-4 form_group'>");
                    sb.append("<label class='form_label'>Add Link(optional)</label>");
                    sb.append("<input type='text' name='alink' class='form-control' placeholder='Enter here'>");
                    sb.append("</div>");
                    sb.append("</div>");
                    sb.append("</div>");
                    sb.append("</div>");
                    sb.append("<div class='row'>");
                    sb.append("<input type='hidden' name ='mpositionId' value = '"+positionId+"'/>");
                    sb.append("<input type='hidden' name ='candidateId' value = '"+candidateId+"'/>");
                    sb.append("<input type='hidden' name ='mcrids' value = '"+mcrids+"'/>");
                    sb.append("<div class='col-lg-12 col-md-12 col-sm-12 col-12 text-right'><a href=\"javascript: savePersonalmodalassign();\" class='save_page'><img src='../assets/images/save.png'> Save</a></div>");
                    sb.append("</div>");
                    sb.append("</div>");
                } else {
                    sb.append("Something went wrong");
                }
                str = sb.toString();
                sb.setLength(0);
                response.getWriter().write(str);
            } else {
                response.setStatus(HttpServletResponse.SC_NO_CONTENT);
            }
        } else {
            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
        }
    } catch (Exception e) {
        e.printStackTrace();            
    }
%>