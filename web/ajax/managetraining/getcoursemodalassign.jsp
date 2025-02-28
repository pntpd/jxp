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
            if (request.getParameter("courseId") != null) 
            {
                String courseIds = request.getParameter("courseId") != null && !request.getParameter("courseId").equals("") ? vobj.replaceint(request.getParameter("courseId")) : "";
                int courseId = Integer.parseInt(courseIds);
                String mcrids = request.getParameter("crids") != null && !request.getParameter("crids").equals("") ? vobj.replacedesc(request.getParameter("crids")) : "";
                response.setContentType("text/html");
                response.setHeader("Cache-Control", "no-cache");

                ManagetrainingInfo info = managetraining.getcourseModalInfo(courseId);

                StringBuffer sb = new StringBuffer();
                if (courseId > 0) 
                {
                    sb.append("<div class='col-lg-12'>");
                    sb.append("<div class='row m_15'>");
                    sb.append("<div class='col'>");
                    sb.append("<h2>"+info.getCourseName()+"</h2>");
                    sb.append("</div>");
                    sb.append("<div class='col col-lg-3 text-right'>");
                    sb.append("<span class='unassigned_mode'>Unassigned</span>");
                    sb.append("</div>");

                    sb.append("<div class='col-md-12 mt_15'>");
                    sb.append("<div class='row d-flex align-items-center'>");
                    sb.append("<div class='col-md-5 com_label_value'>");
                    sb.append("<div class='row mb_0'>");
                    sb.append("<div class=col-md-3'><label>Category</label></div>");
                    sb.append("<div class='col-md-9'><span>"+info.getCategoryName()+"</span></div>");
                    sb.append("</div>");
                    sb.append("</div>");
                    sb.append("<div class='col-md-5 com_label_value'>");
                    sb.append("<div class='row mb_0'>");
                    sb.append("<div class='col-md-3'><label>Sub-Category</label></div>");
                    sb.append("<div class='col-md-9'><span>"+info.getSubcategoryName()+"</span></div>");
                    sb.append("</div>");    									
                    sb.append("</div>");									
                    sb.append("</div>");
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
                    sb.append("<input type='hidden' name ='mcourseId' value = '"+courseId+"'/>");
                    sb.append("<input type='hidden' name ='mcrids' value = '"+mcrids+"'/>");
                    sb.append("<div class='col-lg-12 col-md-12 col-sm-12 col-12 text-right'><a href=\" javascript: saveCoursemodalassign();\" class='save_page'><img src='../assets/images/save.png'> Save</a></div>");
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