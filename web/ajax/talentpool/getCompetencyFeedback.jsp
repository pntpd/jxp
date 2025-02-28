<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.Collection"%>
<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@page import="com.web.jxp.user.UserInfo" %>
<%@page import="com.web.jxp.talentpool.TalentpoolInfo"%>
<jsp:useBean id="talentpool" class="com.web.jxp.talentpool.Talentpool" scope="page"/>
<jsp:useBean id="vobj" class="com.web.jxp.base.Validate" scope="page"/>
<%
    try 
    {
        if (session.getAttribute("LOGININFO") != null) 
        {
            if (request.getParameter("trackerId") != null)
            {
                String trackerIds = request.getParameter("trackerId") != null && !request.getParameter("trackerId").equals("") ? vobj.replaceint(request.getParameter("trackerId")) : "0";
                int trackerId = Integer.parseInt(trackerIds);
                String role = "", date = "", remarks = ""; 
                TalentpoolInfo info = talentpool.getFeedbackDetail(trackerId);
                if(info != null)
                {
                    role = info.getRole() != null ? info.getRole(): "";
                    date = info.getDate() != null ? info.getDate(): "";
                    remarks = info.getRemarks()!= null ? info.getRemarks(): "";
                }
                response.setContentType("text/html");
                response.setHeader("Cache-Control", "no-cache");
                
                StringBuilder sb = new StringBuilder();
                if (trackerId > 0) 
                {
                    sb.append("<div>");
                         sb.append("<h2>Competency Assessment Feedback (Crew)</h2>");
                    sb.append("<div class='row client_position_table'>");
                                    sb.append("<div class='col-md-12 com_label_value form_group'>");
                                            sb.append("<div class='row mb_0'>");
                                                    sb.append("<div class='col-md-4'><label>Role Competency</label></div>");
                                                    sb.append("<div class='col-md-8'><span>"+role+"</span></div>");
                                            sb.append("</div>");
                                            sb.append("<div class='row mb_0'>");
                                                    sb.append("<div class='col-md-4'><label>Provided On</label></div>");
                                                    sb.append("<div class='col-md-8'><span>"+date+"</span></div>");
                                            sb.append("</div>");
                                   sb.append(" </div>");
                            sb.append("<div class='col-lg-12 col-md-12 col-sm-12 col-4 form_group'><span class='form-control'>"+(!remarks.equals("") ? remarks : "&nbsp;")+"</span></div>");                                                                     
                    sb.append("</div>");
                    sb.append("</div>");
                }
                else 
                {
                    sb.append("Something went wrong");  
                }
                String str = sb.toString();
                sb.setLength(0);
                response.getWriter().write(str);
            }
            else 
            {
                response.setStatus(HttpServletResponse.SC_NO_CONTENT);
            }
        } 
        else 
        {
            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
        }
    } 
    catch (Exception e) 
    {
        e.printStackTrace();
    }
%>