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
            if (request.getParameter("categoryIdDetail") != null)
            {
                StringBuilder str = new StringBuilder();
                String crewrotationId_s = request.getParameter("crewrotationId") != null ? vobj.replaceint(request.getParameter("crewrotationId")) : "0";
                String positionId_s = request.getParameter("positionId") != null ? vobj.replaceint(request.getParameter("positionId")) : "0";
                String clientIdIndex_s = request.getParameter("clientIdIndex") != null ? vobj.replaceint(request.getParameter("clientIdIndex")) : "0";
                String assetIdIndex_s = request.getParameter("assetIdIndex") != null ? vobj.replaceint(request.getParameter("assetIdIndex")) : "0";
                String categoryIdDetail_s = request.getParameter("categoryIdDetail") != null ? vobj.replaceint(request.getParameter("categoryIdDetail")) : "0";
                String subcategoryIdDetail_s = request.getParameter("subcategoryIdDetail") != null && !request.getParameter("subcategoryIdDetail").equals("") ? vobj.replaceint(request.getParameter("subcategoryIdDetail")) : "0";
                String ftype_s = request.getParameter("ftype") != null && !request.getParameter("ftype").equals("") ? vobj.replaceint(request.getParameter("ftype")) : "0";
                String search = request.getParameter("search") != null && !request.getParameter("search").equals("") ? request.getParameter("search") : "";
                String statusIndex_s = request.getParameter("statusIndex") != null && !request.getParameter("statusIndex").equals("") ? vobj.replaceint(request.getParameter("statusIndex")) : "-1";
                int categoryIdDetail = Integer.parseInt(categoryIdDetail_s);
                int subcategoryIdDetail = Integer.parseInt(subcategoryIdDetail_s);
                int ftype = Integer.parseInt(ftype_s);
                int crewrotationId = Integer.parseInt(crewrotationId_s);
                int positionId = Integer.parseInt(positionId_s);
                int clientIdIndex = Integer.parseInt(clientIdIndex_s);
                int assetIdIndex = Integer.parseInt(assetIdIndex_s);
                int statusIndex = Integer.parseInt(statusIndex_s);
                String file_path = managetraining.getMainPath("view_candidate_file");
                ArrayList list = managetraining.getListAssign1(clientIdIndex, assetIdIndex, positionId, 
                    crewrotationId, categoryIdDetail, subcategoryIdDetail, ftype, search, statusIndex);
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
                                str.append("<td>"+(minfo.getCourseName() != null ? minfo.getCourseName() : "")+"</td>");
                                str.append("<td>"+(minfo.getCategoryName() != null ? minfo.getCategoryName() : "")+"</td>"); 
                                str.append("<td>"+(minfo.getSubcategoryName() != null ? minfo.getSubcategoryName() : "")+"</td>");
                                str.append("<td>"+(minfo.getCoursetype() != null ? minfo.getCoursetype() : "")+"</td>");
                                str.append("<td>"+(minfo.getLevel() != null ? minfo.getLevel() : "")+"</td>");
                                str.append("<td>"+(minfo.getStatusval() != null ? minfo.getStatusval() : "")+"</td>");
                                str.append("<td>"+(minfo.getDate() != null ? minfo.getDate() : "")+"</td>"); 
                                str.append("<td class='action_column text-center'>");
                                   if(!filep.equals("")) {
                                        str.append("<a href=\"javascript:;\" class='mr_15' data-bs-toggle='modal' data-bs-target='#view_pdf' onclick=\"javascript:setIframe('"+filep+"');\"><img src='../assets/images/view.png'/> </a>");
                                   } else if(!url.equals(""))
                                   { 
                                       str.append("<a href= "+url+" class='mr_15' target='_blank'><img src='../assets/images/view.png'/></a>");
                                    } else {
                                         str.append("<a href='javascript:;' ><span style='width: 35px;'>&nbsp;</span></a>");
                                     }
                                    if(addper.equals("Y") || editper.equals("Y"))
                                    {
                                        str.append("<a data-bs-toggle='modal' data-bs-target='#personal_course_details_modal_01' href=\"javascript:;\" onclick=\"javascript: setPersonalModal('-1',' "+minfo.getClientmatrixdetailid()+" ','1');\"><img src='../assets/images/pencil.png'></a>");
                                    } else {
                                        str.append("&nbsp;");
                                    }
                                 str.append("</td>");                                
                            str.append("</tr>");
                        }
                    }
                }else{
                   str.append("<tr>");
                         str.append("<td colspan='11'>No information available.</td>");  
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