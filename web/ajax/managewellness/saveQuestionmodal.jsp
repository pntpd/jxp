<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.Collection"%>
<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@page import="com.web.jxp.user.UserInfo" %>
<%@page import="com.web.jxp.managewellness.ManagewellnessInfo" %>
<jsp:useBean id="managewellness" class="com.web.jxp.managewellness.Managewellness" scope="page"/>
<jsp:useBean id="vobj" class="com.web.jxp.base.Validate" scope="page"/>
<%
    try 
    {
        if (session.getAttribute("LOGININFO") != null) 
        {
            String str = "";
            if (request.getParameter("clientIdIndex") != null) 
            {
                String clientIdIndexs = request.getParameter("clientIdIndex") != null && !request.getParameter("clientIdIndex").equals("") ? vobj.replaceint(request.getParameter("clientIdIndex")) : "";
                int clientIdIndex = Integer.parseInt(clientIdIndexs);
                String assetIdIndexs = request.getParameter("assetIdIndex") != null && !request.getParameter("assetIdIndex").equals("") ? vobj.replaceint(request.getParameter("assetIdIndex")) : "";
                int assetIdIndex = Integer.parseInt(assetIdIndexs);
                String schedulecbs = request.getParameter("schedulecb") != null && !request.getParameter("schedulecb").equals("") ? vobj.replaceint(request.getParameter("schedulecb")) : "";
                int schedulecb = Integer.parseInt(schedulecbs);
                String mcrids = request.getParameter("crids") != null && !request.getParameter("crids").equals("") ? vobj.replacename(request.getParameter("crids")) : "";
                String [] ba = mcrids.split(",");
                int mcrid = 0;
                if(ba.length<=1)
                {
                    mcrid = Integer.parseInt(mcrids);
                }
                response.setContentType("text/html");
                response.setHeader("Cache-Control", "no-cache");
                ManagewellnessInfo sinfo = new ManagewellnessInfo("","");
                 if(ba.length<=1)
                 {
                    sinfo = (ManagewellnessInfo)managewellness.getScheduleInfo( clientIdIndex, assetIdIndex, mcrid);
                 }
                ArrayList list =  (ArrayList)managewellness.getQuestionList( clientIdIndex, assetIdIndex, mcrids);
                String currentDate = managewellness.currDateNew();
                StringBuffer sb = new StringBuffer();
                if (clientIdIndex > 0) 
                {
                    sb.append("<div class='row flex-center align-items-center mb_10'>");
                    sb.append("<div class='col'>");
                    sb.append("<h2>DEFINE SCHEDULE</h2>");
                    sb.append("<input type='hidden' name='mcrids' id='mcrids' value='"+mcrids+"'/>");
                    sb.append("<input type='hidden' name='currentDate' id='mcrids' value='"+currentDate+"'/>");
                    sb.append("</div>");
                    sb.append("<div class='col col-lg-1'>");
                    sb.append("<div class='ref_vie_ope'>");
                    sb.append("<ul>");
                    sb.append("<li class='com_view_job m_0'><a href=\"../wellnessmatrix/WellnessmatrixAction.do\" target='_blank'><img src='../assets/images/view.png'><br> View Matrix</a></li>");
                    sb.append("</ul>");
                    sb.append("</div>");
                    sb.append("</div>");
                    sb.append("</div>");

                    sb.append("<div class='row client_position_table1'>");
                    sb.append("<div class='col-md-3 form_group'>");
                    sb.append("<label class='form_label'>Start Date</label>");
                    sb.append("<div class='input-daterange input-group'>");
                    sb.append("<input type='text' name='startdate' id='startdate' value='"+sinfo.getQuestion()+"' class='form-control add-style wesl_dt date-add text-center' placeholder='DD-MMM-YYYY'>");
                    sb.append("</div>");
                    sb.append("</div>");
                    if(schedulecb == 1)
                    {
                        sb.append("<div class='col-md-3 form_group'>");
                        sb.append("<label class='form_label'>End Date</label>");
                        sb.append("<div class='input-daterange input-group'>");
                        sb.append("<input type='text' name='enddate'  id='enddate' value='"+sinfo.getAnswer()+"' class='form-control add-style wesl_dt date-add text-center' placeholder='DD-MMM-YYYY'>");
                        sb.append("</div>");
                        sb.append("</div>");
                    }
                    sb.append("<div class='col-md-12 table-rep-plugin sort_table client_position_table'>");
                    sb.append("<div class='table-responsive mb-0'>");
                    sb.append("<table id='tech-companies-1' class='table table-striped'>");
                    sb.append("<thead>");
                    sb.append("<tr>");
                    sb.append("<th width='43%'><b>Sub Category</b></th>");
                    sb.append("<th width='43%'><b>Category</b></th>");
                    sb.append("<th width='14%'><b>Repeats</b></th>");
                    sb.append("</tr>");
                    sb.append("</thead>");
                    sb.append("<tbody>");
                    for(int i = 0;i<list.size();i++)
                    {
                        ManagewellnessInfo info = (ManagewellnessInfo)list.get(i);

                        sb.append("<tr>");
                        sb.append("<td>"+info.getSubcategoryName()+"</td>");
                        sb.append("<td>"+info.getCategoryName()+"</td>");
                        sb.append("<td>"+info.getRepeatvalue()+"</td>");
                        sb.append("</tr>");
                    }
                    sb.append("</tbody>");
                    sb.append("</table>");	
                    sb.append("</div>");
                    sb.append("</div>");
                    sb.append("</div>");
                    sb.append("<div class='row'>");
                    sb.append("<div class='col-lg-12 col-md-12 col-sm-12 col-12 text-center'>");
                    sb.append("<a href=\"javascript: savedateForm('"+schedulecb+"');\" class='save_page'><img src='../assets/images/save.png'> Save</a>");
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