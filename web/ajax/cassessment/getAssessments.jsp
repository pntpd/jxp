<%@page language="java"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.web.jxp.user.UserInfo"%>
<%@page import="com.web.jxp.cassessment.CassessmentInfo"%>
<%@taglib uri="/WEB-INF/struts-html-el.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/fmt.tld" prefix="fmt"%>
<%@taglib uri="/WEB-INF/c.tld" prefix="c"%>
<jsp:useBean id="cassessment" class="com.web.jxp.cassessment.Cassessment" scope="page"/>
<jsp:useBean id="vobj" class="com.web.jxp.base.Validate" scope="page"/>
<%
    try {

        if (session.getAttribute("LOGININFO") != null) 
        {
            if (request.getParameter("scheduleIndex") != null)
            {
                ArrayList list = new ArrayList();
                if (session.getAttribute("ASSESSMENT_LIST") != null) {
                    list = (ArrayList) session.getAttribute("ASSESSMENT_LIST");
                }
                int paId = 0;
                if (session.getAttribute("PAID") != null) {
                    paId = Integer.parseInt((String) session.getAttribute("PAID"));
                }
                
                StringBuilder str = new StringBuilder();
                String schedules = request.getParameter("scheduleIndex") != null && !request.getParameter("scheduleIndex").equals("") ? vobj.replaceint(request.getParameter("scheduleIndex")) : "0";
                int schedule = Integer.parseInt(schedules);
                ArrayList finallist = new ArrayList();
                finallist = cassessment.getListFromList(list, schedule);
                int total = finallist.size();
                if (total > 0) 
                {
                    str.append("<ul>");
                    CassessmentInfo info;
                    for (int i = 0; i < total; i++) 
                    {
                        info = (CassessmentInfo) finallist.get(i);
                        if (info != null) 
                        {
                            str.append("<li");
                            if (info.getAssessmentDetailId() == paId) {
                                str.append("class='active_box'");
                            }
                            str.append(" id='leftli"+(i+1)+"' >");
                            str.append("<a href='javascript: void(0);' onclick=\"getViewEditData('"+info.getAssessmentDetailId()+"', '"+info.getCassessmentId()+"', 'view'); setDivClass('"+total+"', '"+(i+1)+"');\">");
                            str.append("<div class='col-lg-12 status_div ");
                            if(info.getStatus() == 2){
                                str.append("passed_div' >");
                            }else if(info.getStatus() == 3) {
                                str.append("failed_div' >");
                            } else if(info.getCassessmentId() > 0 && info.getStatus() == 1){
                                str.append("scheduled_div' >");
                            }else if(info.getCassessmentId() <= 0){
                                str.append("unscheduled_div' >");
                            }
                            str.append("<div class='row flex-end align-items-center'>");

                            str.append("<div class='col-lg-3'><label class='mr_8'>Code</label><span class='status_value'>" + (info.getCode() != null && !"".equals(info.getCode()) ? info.getCode() : "") + "</span></div>");
                            str.append("<div class='col-lg-6'><label class='mr_8'>Minimum Score</label><span class='status_value'>" + info.getMinScore() + "</span></div>");
                            if (info.getPassingFlag() == 1) {
                                str.append("<div class='col-lg-3 text-center'><span class='passing_req'>Passing <br/> Required</span></div>");
                            }
                            str.append("</div>");
                            str.append("<div class='row'>");
                            str.append("<div class='col-lg-12 mb_10'>");
                            str.append("<label class='full_width'>Name</label>");
                            str.append("<span class='status_value'>" + (info.getAssessmentName() != null && !"".equals(info.getAssessmentName()) ? info.getAssessmentName() : "") + "</span>");
                            str.append("</div>");
                            str.append("<div class='col-lg-12'>");
                            str.append("<label class='full_width'>Status</label>");
                            str.append("<span class='status_value'>" + (info.getScheduletype() != null && !"".equals(info.getScheduletype()) ? info.getScheduletype() : "") + "</span>");
                            str.append("</div>");
                            str.append("</div>");
                            str.append("</div>");
                            str.append("</a>");
                            str.append("</li>");
                        }
                    }
                    str.append("</ul>");
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