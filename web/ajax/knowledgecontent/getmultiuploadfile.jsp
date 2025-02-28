<%@page contentType="text/html"%>
<%@page language="java" import="java.util.ArrayList"%>
<%@page pageEncoding="UTF-8"%>
<%@page import="com.web.jxp.user.UserInfo" %>
<%@page import="com.web.jxp.knowledgecontent.KnowledgecontentInfo" %>
<jsp:useBean id="knowledgecontent" class="com.web.jxp.knowledgecontent.Knowledgecontent" scope="page"/>
<jsp:useBean id="vobj" class="com.web.jxp.base.Validate" scope="page"/>
<%
    try 
    {
        if (session.getAttribute("LOGININFO") != null) 
        {
            if (request.getParameter("topicId") != null) 
            {
                StringBuilder sb = new StringBuilder();
                ArrayList list = new ArrayList();
                String view_kfiles = knowledgecontent.getMainPath("view_kfiles");
                if (request.getSession().getAttribute("TOPICATTACHMENTLIST") != null) {
                    list = (ArrayList) request.getSession().getAttribute("TOPICATTACHMENTLIST");
                }
                int size = 0;
                size = list.size();
                sb.append("<div class='row flex-center align-items-center mb_10'>");
                sb.append("<div class='col'>");
                sb.append("<h2>UPLOAD TOPIC MATERIAL FILES</h2>");
                sb.append("</div>");
                sb.append("<div class='col col-md-3'>");
                sb.append("<div class='row flex-center align-items-center'>");
                sb.append("<div class='col-md-8 text-right field_label'><label>No. Of files</label></div>");
                sb.append("<div class='col-md-4'><span class='form-control text-center'>" + (size) + "</span></div>");
                sb.append("</div>");
                sb.append("</div>");
                sb.append("</div>");
                sb.append("<div class='row client_position_table'>");
                sb.append("<div class='col-md-12 table-rep-plugin sort_table'>");
                sb.append("<div class='table-responsive mb-0'>");
                sb.append("<table id='tech-companies-1' class='table table-striped'>");
                sb.append("<thead>");
                sb.append("<tr>");
                sb.append("<th width='25%'>Select Type</th>");
                sb.append("<th width='52%'>File Name</th>");
                sb.append("<th width='15%'>Browse Files</th>");
                sb.append("<th width='8%'>&nbsp;</th>");
                sb.append("</tr>");
                sb.append("</thead>");
                sb.append("<tbody>");
                sb.append("<tr>");
                sb.append("<td>");
                sb.append("<select class='form-select' id='type' name='type'>");
                sb.append("<option value='-1'>Select</option>");
                sb.append("<option value='1'>Media File</option>");
                sb.append("<option value='2'>E-learning</option>");
                sb.append("</select>");
                sb.append("</td>");
                sb.append("<td><input type='text' id='dispname' name='dispname' class='form-control'/></td>");
                sb.append("<td>");
                sb.append("<input id='upload1' name='upload1' type='file' onchange=\"setClass('1');\">");
                sb.append("<a href='javascript:;' id='upload_link_1' class='attache_btn uploaded_img1'>");
                sb.append("<i class='fas fa-paperclip'></i> Attach");
                sb.append("</a>");
                sb.append("</td>");
                sb.append("<td class=' text-center'>");
                sb.append("<a href='javascript:;' onclick=\"javascript: getAddToList();\" class='add_btn'><img src='../assets/images/add_list.png'></a>");
                sb.append("</td>");
                sb.append("</tr>");
                
                if (size > 0) 
                {
                    KnowledgecontentInfo info = null;
                    for (int i = 0; i < size; i++) 
                    {
                        info = (KnowledgecontentInfo) list.get(i);
                        if (info != null) 
                        {
                            sb.append("<tr>");
                            sb.append("<td>" + (info.getType() == 1 && info.getType() != 0 ? "Media File" : "E-learning") + "</td>");
                            sb.append("<td>" + (info.getName() != null ? info.getName() : "") + "</td>");
                            sb.append("<td class='action_column text-center'>");
                            if (info.getType() == 1) {
                                sb.append("<a href='javascript:;' data-bs-toggle='modal' data-bs-target='#view_pdf' href='javascript:;' onclick=\"javascript: setIframe('" + (view_kfiles + info.getFilename()) + "')\">");
                            } else if (info.getType() == 2 && info.getFilename() != null) {
                                sb.append("<a href='" + (view_kfiles + info.getFilename()) + "story.html' target='_blank'>");
                            }
                            sb.append("<img src='../assets/images/view.png'></a></td>");
                            sb.append("<td class='action_column text-center'>");
                            sb.append("<a href='javascript:;' onclick=\"getDeleteFromList('" + i + "')\">");
                            sb.append("<img src='../assets/images/cross.png'></a>");
                            sb.append("</td>");
                            sb.append("</tr>");
                        }
                    }
                }
                sb.append("</tbody>");
                sb.append("</table>");
                sb.append("</div>");
                sb.append("</div>");
                sb.append("</div>");
                sb.append("<div class='row'>");
                sb.append("<div class='col-lg-12 col-md-12 col-sm-12 col-12 text-center'>");
                sb.append("<a href='javascript:;' data-bs-dismiss='modal' class='save_page'> Ok</a>");
                sb.append("</div>");
                sb.append("</div>");
                String st = sb.toString();
                sb.setLength(0);
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