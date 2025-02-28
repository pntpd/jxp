<%@page contentType="text/html"%>
<%@page language="java" import="java.util.ArrayList"%>
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
            UserInfo uinfo = ((UserInfo) request.getSession().getAttribute("LOGININFO"));
            String addper = "", editper = "N";
            if (uinfo != null) 
            {
                addper = uinfo.getAddper() != null ? uinfo.getAddper() : "N";
                editper = uinfo.getEditper() != null ? uinfo.getEditper() : "N";
            }
            if (request.getParameter("courseId") != null)
            {
                StringBuilder str = new StringBuilder();
                String courseId_s = request.getParameter("courseId") != null ? vobj.replaceint(request.getParameter("courseId")) : "0";
                String clientIdIndex_s = request.getParameter("clientIdIndex") != null ? vobj.replaceint(request.getParameter("clientIdIndex")) : "0";
                String assetIdIndex_s = request.getParameter("assetIdIndex") != null ? vobj.replaceint(request.getParameter("assetIdIndex")) : "0";
                String ftype_s = request.getParameter("ftype") != null && !request.getParameter("ftype").equals("") ? vobj.replaceint(request.getParameter("ftype")) : "0";
                String search = request.getParameter("search") != null && !request.getParameter("search").equals("") ? request.getParameter("search") : "";
                String statusIndex_s = request.getParameter("statusIndex") != null && !request.getParameter("statusIndex").equals("") ? vobj.replaceint(request.getParameter("statusIndex")) : "-1";
                String positionId2Index_s = request.getParameter("positionId2Index") != null && !request.getParameter("positionId2Index").equals("") ? vobj.replaceint(request.getParameter("positionId2Index")) : "-1";
                int positionId2Index = Integer.parseInt(positionId2Index_s);
                int courseId = Integer.parseInt(courseId_s);
                int ftype = Integer.parseInt(ftype_s);
                int clientIdIndex = Integer.parseInt(clientIdIndex_s);
                int assetIdIndex = Integer.parseInt(assetIdIndex_s);
                int statusIndex = Integer.parseInt(statusIndex_s);
                
                String file_path = managetraining.getMainPath("view_candidate_file");
                
                ArrayList list = managetraining.getListAssign2(clientIdIndex, assetIdIndex, courseId, ftype, search, statusIndex, positionId2Index);
                int total = list.size();
                if(total > 0)
                {
                    String filep, url;
                    for(int  i = 0; i < total; i++)
                    {
                        ManagetrainingInfo minfo = (ManagetrainingInfo) list.get(i);
                        if(minfo != null)
                        {
                            filep = ""; url = "";
                            if(minfo.getFilename() != null && !minfo.getFilename().equals(""))
                                filep = file_path+minfo.getFilename();
                            else if(minfo.getUrl() != null && !minfo.getUrl().equals(""))
                                url = minfo.getUrl(); 
                            
                            str.append("<tr>");
                                str.append("<td class='select_check_box'>");
                                    str.append("<label class='mt-checkbox mt-checkbox-outline'>"); 
                                        str.append("<input type='checkbox' value='"+minfo.getCourseId()+"' name='coursecb' class='singlechkbox' onchange='javascript: setcb();' />");
                                        str.append("<span></span>");
                                    str.append("</label>");	
                                str.append("</td>");
                                str.append("<td class='text-center'>"+managetraining.getStColour(minfo.getStatus())+"</td>"); 
                                str.append("<td>"+(minfo.getName() != null ? minfo.getName() : "")+"</td>");
                                str.append("<td>"+(minfo.getPositionName() != null ? minfo.getPositionName() : "")+"</td>"); 
                                str.append("<td>"+(minfo.getCoursetype() != null ? minfo.getCoursetype() : "")+"</td>");
                                str.append("<td>"+(minfo.getLevel() != null ? minfo.getLevel() : "")+"</td>");
                                str.append("<td>"+(minfo.getStatusval() != null ? minfo.getStatusval() : "")+"</td>");
                                str.append("<td>"+(minfo.getDate() != null ? minfo.getDate() : "")+"</td>");                                 
                                str.append("<td class='action_column text-center'>");
                                   if(!filep.equals("")) 
                                   {
                                        str.append("<a href=\"javascript:;\" class='mr_15' data-bs-toggle='modal' data-bs-target='#view_pdf' onclick=\"javascript:setIframe('"+filep+"');\"><img src='../assets/images/view.png'/> </a>");
                                   } else if(!url.equals(""))
                                   { 
                                       str.append("<a href= "+url+" class='mr_15' target='_blank'><img src='../assets/images/view.png'/></a>");
                                    } else {
                                         str.append("<a href='javascript:;' ><span style='width: 35px;'>&nbsp;</span></a>");
                                     }
                                    if(addper.equals("Y") || editper.equals("Y"))
                                    {
                                       str.append("<a class='' data-bs-toggle='modal' data-bs-target='#personal_course_details_modal_01' href='javascript:;' onclick=\"javascript: setPersonalModal('"+minfo.getCrewrotationId()+"','"+minfo.getClientmatrixdetailid()+"','2');\"><img src='../assets/images/pencil.png' /></a>");
                                    } else {
                                        str.append("&nbsp;");
                                    }                                     
                                 str.append("</td>");
                            str.append("</tr>");
                        }
                    }
                }
                else{
                   str.append("<tr>");
                         str.append("<td colspan='9'>No information available.</td>");  
                    str.append("</tr>");
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