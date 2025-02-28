<%@page import="java.util.Iterator"%>
<%@page import="java.util.Collection"%>
<%@page import="com.web.jxp.crewlogin.CrewloginInfo"%>
<%@page contentType="text/html"%>
<%@page language="java" import="java.util.ArrayList"%>
<%@page pageEncoding="UTF-8"%>
<%@page import="com.web.jxp.feedback.FeedbackInfo" %>
<jsp:useBean id="feedback" class="com.web.jxp.feedback.Feedback" scope="page"/>
<jsp:useBean id="vobj" class="com.web.jxp.base.Validate" scope="page"/>
<%
    try 
    {
        if (session.getAttribute("CREWLOGIN") != null) 
        {
            if (request.getParameter("Id") != null) 
            {
                StringBuilder sb = new StringBuilder();
                String Ids = request.getParameter("Id") != null && !request.getParameter("Id").equals("") ? vobj.replaceint(request.getParameter("Id")) : "0";
                int trackerId = Integer.parseInt(Ids);
                
                if (trackerId > 0)
                {
                    sb.append("<h2>APPEAL REQUEST</h2>");
                    sb.append("<div class='row client_position_table'>");
                    sb.append("<div class='col-xl-12 col-lg-12 col-md-12 col-sm-12 col-12'>");
                    sb.append("<p>");
                    sb.append("Please utilise the appeal option to report any concerns with the assessment or to request re-evaluation.");
                    sb.append("We will carefully review your request & respond with a fair resolution as soon as possible.");
                    sb.append("</p>");
                    sb.append("</div>");
                    sb.append("<div class='col-xl-12 col-lg-12 col-md-12 col-sm-12 col-12 form_group'>");
                    sb.append("<label class='form_label'>Select Reasons</label>");
                    Collection coll = feedback.getAppealsforclientindex();
                    sb.append("<select id='appealId' name='appealId' class='form-select'>");
                    sb.append("<option value='-1'>- Select -</option>");
                    if (coll.size() > 0) 
                    {
                        Iterator iter = coll.iterator();
                        while (iter.hasNext()) 
                        {
                            FeedbackInfo colinfo = (FeedbackInfo) iter.next();
                            int val = colinfo.getDdlValue();
                            String name = colinfo.getDdlLabel() != null ? colinfo.getDdlLabel() : "";
                            sb.append("<option value='" + val + "'>" + name + "</option>");
                        }
                    }
                    coll.clear();
                    sb.append("</select>");
                    sb.append("</div>");
                    sb.append("<div class='col-xl-12 col-lg-12 col-md-12 col-sm-12 col-12 form_group'>");
                    sb.append("<label class='form_label'>Remarks</label>");
                    sb.append("<textarea id='appealremarks' name='appealremarks' class='form-control' rows='4'></textarea>");
                    sb.append("</div>");
                    sb.append("</div>");
                    sb.append("<div class='row'>");
                    sb.append("<div class='col-xl-12 col-lg-12 col-md-12 col-sm-12 col-12'>");
                    sb.append("<div class='row justify-content-md-center'>");
                    sb.append("<div class='col-xl-6 col-lg-6 col-md-6 col-sm-6 col-6'><a href='javascript:;' data-bs-dismiss='modal' class='trans_btn'>Cancel</a></div>");
                    sb.append("<div class='col-xl-6 col-lg-6 col-md-6 col-sm-6 col-6'><a href='javascript:;' onclick=\"getSendAppeal('" + trackerId + "')\" class='termi_btn'>Send Appeal</a></div>");
                    sb.append("</div>");
                    sb.append("</div>");
                    sb.append("</div>");
                    String st1 = sb.toString();
                    sb.setLength(0);
                    response.getWriter().write(st1);
                } else {
                    response.getWriter().write("Something went wrong.");
                }
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