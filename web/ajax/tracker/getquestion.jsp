<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.Collection"%>
<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@page import="com.web.jxp.user.UserInfo" %>
<%@page import="com.web.jxp.tracker.QuestionInfo" %>
<jsp:useBean id="tracker" class="com.web.jxp.tracker.Tracker" scope="page"/>
<jsp:useBean id="vobj" class="com.web.jxp.base.Validate" scope="page"/>
<%
    try 
    {
        if (session.getAttribute("LOGININFO") != null) 
        {
            if (request.getParameter("trackerId") != null)
            {
                String trackerId_s = request.getParameter("trackerId") != null && !request.getParameter("trackerId").equals("") ? vobj.replaceint(request.getParameter("trackerId")) : "0";
                int trackerId = Integer.parseInt(trackerId_s);
                response.setContentType("text/html");
                response.setHeader("Cache-Control", "no-cache");
                StringBuilder sb = new StringBuilder();                
                if (trackerId > 0) 
                {
                    ArrayList alist = tracker.getQuestionList(trackerId);
                    int alist_size = alist.size(); 
                    if(alist_size > 0)
                    {
                        String onlinedate = "";
                        QuestionInfo mainfo = (QuestionInfo) alist.get(0);
                        if(mainfo != null)
                        {
                            onlinedate = mainfo.getOnlinedate() != null ? mainfo.getOnlinedate() : "";
                        }
                        sb.append("<div class='row flex-center align-items-center mb_10'>");
                            sb.append("<div class='col'>");
                                sb.append("<h2>Online Submission</h2>");
                            sb.append("</div>");
                            sb.append("<div class='col col-md-3'>");
                                    sb.append("<div class='row flex-center align-items-center'>");
                                            sb.append("<div class='col-md-6 text-right field_label'><label>Completed On</label></div>");
                                            sb.append("<div class='col-md-6 pd_0'><span class='form-control'>"+onlinedate+"</span></div>");
                                    sb.append("</div>");
                            sb.append("</div>");
                        sb.append("</div>");
                        sb.append("<div class='row client_position_table ques_ans_table'>");
                            sb.append("<div class='col-md-12 table-rep-plugin sort_table'>");
                                sb.append("<div class='table-responsive mb-0'>");
                                    sb.append("<table id='tech-companies-1' class='table table-striped'>");
                                        sb.append("<tbody>");
                    
                        for(int i = 0; i < alist_size; i++)
                        {
                            QuestionInfo ainfo = (QuestionInfo) alist.get(i);
                            if(ainfo != null)
                            {
                                if(ainfo.getCategoryName() != null && !ainfo.getCategoryName().equals(""))
                                {
                                    sb.append("<tr><td>&nbsp;</td></tr>");
                                    sb.append("<tr>");
                                        sb.append("<td class='que_title'>");
                                                sb.append(ainfo.getCategoryName());
                                        sb.append("</td>");
                                    sb.append("</tr>");
                                }
                                sb.append("<tr>");
                                    sb.append("<td class='que_ans'>");
                                        sb.append("<span class='que_bg'>Q "+(i+1)+"</span> "+(ainfo.getQuestionName() != null ? ainfo.getQuestionName() : "")+"</td>");
                                sb.append("</tr>");
                                sb.append("<tr>");
                                    sb.append("<td class='que_ans'>");
                                        sb.append("<span class='ans_bg'>A</span> "+(ainfo.getAnswer() != null ? ainfo.getAnswer() : ""));
                                    sb.append("</td>");
                                sb.append("</tr>");
                            }
                        }
                    }
                    else
                    {
                        sb.append("<tr><td colspan='3'><b>No records available.</b></td></tr>");
                    }
                    sb.append("</tbody>");
                            sb.append("</table>");
                        sb.append("</div>");	
                    sb.append("</div>");
                sb.append("</div>");
                }
                else 
                {
                    sb.append("Something went wrong.");  
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