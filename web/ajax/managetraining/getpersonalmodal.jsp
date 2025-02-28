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
                String clientmatrixdetailids = request.getParameter("clientmatrixdetailId") != null && !request.getParameter("clientmatrixdetailId").equals("") ? vobj.replaceint(request.getParameter("clientmatrixdetailId")) : "";
                int clientmatrixdetailId = Integer.parseInt(clientmatrixdetailids);
                String candidateIds = request.getParameter("candidateId") != null && !request.getParameter("candidateId").equals("") ? vobj.replaceint(request.getParameter("candidateId")) : "";
                int candidateId = Integer.parseInt(candidateIds);
                String typefroms = request.getParameter("typefrom") != null && !request.getParameter("typefrom").equals("") ? vobj.replaceint(request.getParameter("typefrom")) : "";
                int typefrom = Integer.parseInt(typefroms);
                response.setContentType("text/html");
                response.setHeader("Cache-Control", "no-cache");

                 ManagetrainingInfo info = managetraining.getModalInfo(clientmatrixdetailId);
                 session.setAttribute("COURSEINFO", info);
                 String position= "";
                 ManagetrainingInfo pinfo = managetraining.getModalInfoPersonal(candidateId);
                 if(pinfo != null)
                 {
                     if(info.getPositionId() == pinfo.getPositionId())
                     {
                         position = pinfo.getPositionName() != null ? pinfo.getPositionName() : "";
                     }
                     else if(info.getPositionId() == pinfo.getPositionId2())
                     {
                         position = pinfo.getPosition2() != null ? pinfo.getPosition2() : "";
                     }
                 }

                StringBuffer sb = new StringBuffer();
                if (candidateId > 0) 
                {
                    sb.append("<div class='col-lg-12'>");
                    sb.append("<div class='row'>");
                    sb.append("<div class='col'>");
                    sb.append("<h2>"+pinfo.getName()+" | "+position+"</h2>");
                    sb.append("</div>");
                    sb.append("<div class='col col-lg-3'>");
                    sb.append("<select name = 'stype' class='form-select' id='stype' onchange = 'javascript: changestype()'>");
                    sb.append("<option selected class = 'select_label' value = '-1'>- Select -</option>");
                    sb.append("<option class='sel_assign_bg' value='2' >Assign</option>");
                    sb.append("<option class='sel_complete_bg' value='3'>Complete</option>");
                    sb.append("</select>");
                    sb.append("</div>");
                    sb.append("</div>");

                    sb.append("<div class='row client_position_table1'>");
                    sb.append("<div class='col-md-12 mt_15'>");
                    sb.append("<div class='row d-flex align-items-center'>");
                    sb.append("<div class='col-md-5 com_label_value'>");
                    sb.append("<div class='row mb_0'>");
                    sb.append("<div class='col-md-3'><label>Category</label></div>");
                    sb.append("<div class='col-md-9'><span>"+(info.getCategoryName() != null ? info.getCategoryName(): "&nbsp;")+"</span></div>");
                    sb.append("</div>");
                    sb.append("<div class='row mb_0'>");
                    sb.append("<div class='col-md-3'><label>Course</label></div>");
                    sb.append("<div class='col-md-9'><span>"+(info.getCourseName() != null ? info.getCourseName(): "&nbsp;")+"</span></div>");
                    sb.append("</div>");
                    sb.append("</div>");
                    sb.append("<div class='col-md-5 com_label_value'>");
                    sb.append("<div class='row mb_0'>");
                    sb.append("<div class='col-md-3'><label>Sub-category</label></div>");
                    sb.append("<div class='col-md-9'><span>"+(info.getSubcategoryName() != null ? info.getSubcategoryName(): "&nbsp;")+"</span></div>");
                    sb.append("</div>");
                    sb.append("<div class='row mb_0'>");
                    sb.append("<div class='col-md-3'><label>&nbsp;</label></div>");
                    sb.append("<div class='col-md-9'><span>&nbsp;</span></div>");
                    sb.append("</div>");
                    sb.append("</div>");
                    if(info.getPcount() > 0)
                    {
                        sb.append("<div class='col-md-1 pd_0'>");
                        sb.append("<div class='ref_vie_ope'>");
                        sb.append("<ul>");
                        sb.append("<li class='com_view_job'><a href=\"javascript:openfiles('"+info.getCourseId()+"');\"><img src='../assets/images/attachment.png'><br> View Attachment</a></li>");
                        sb.append("</ul>");
                        sb.append("</div>");
                        sb.append("</div>");
                    }
                    sb.append("<div class='col-md-1'>");
                    sb.append("<div class='ref_vie_ope'>");
                    sb.append("<ul>");
                    sb.append("<li class='com_view_job'><a href='javascript:;' onclick=\"javascript: viewCoursecat('"+info.getCategoryId()+"','"+info.getSubcategoryId()+"');\"><img src='../assets/images/view.png'><br> View Course</a></li>");
                    sb.append("</ul>");
                    sb.append("</div>");
                    sb.append("</div>");
                    sb.append("</div>");
                    sb.append("</div>");

                    sb.append("<div class='col-md-12 cour_min_lev'>");
                    sb.append("<div class='row d-flex align-items-center'>");
                    sb.append("<div class='col-md-4 com_label_value'>");
                    sb.append("<div class='row mb_0'>");
                    sb.append("<div class='col-md-6 text-right'><label>Course Type</label></div>");
                    sb.append("<div class='col-md-6'><span>"+(info.getCoursetype() != null ? info.getCoursetype() : "&nbsp;")+"</span></div>");
                    sb.append("</div>");
                    sb.append("</div>");
                    sb.append("<div class='col-md-4 com_label_value'>");
                    sb.append("<div class='row mb_0'>");
                    sb.append("<div class='col-md-6 text-right'><label>Priority</label></div>");
                    sb.append("<div class='col-md-6'><span>"+(info.getPriority() != null ? info.getPriority(): "&nbsp;")+"</span></div>");
                    sb.append("</div>");
                    sb.append("</div>");
                    sb.append("<div class='col-md-4 com_label_value'>");
                    sb.append("<div class='row mb_0'>");
                    sb.append("<div class='col-md-6 text-right'><label>Level</label></div>");
                    sb.append("<div class='col-md-6'><span>"+(info.getLevel() != null && !info.getLevel().equals("") ? info.getLevel() : "NA")+"</span></div>");
                    sb.append("</div>");
                    sb.append("</div>");
                    sb.append("</div>");
                    sb.append("</div>");
                    //assign
                    sb.append("<div class='col-md-12' id = 'completeId'>");
                    sb.append("<div class='row'>");
                    sb.append("<div class='col-lg-4 col-md-4 col-sm-4 col-4 form_group'>");
                    sb.append("<label class='form_label'>From</label>");
                    sb.append("<div class='input-daterange input-group'>");
                    sb.append("<input type='text' name='fromdate' value='' id='fromdate' class='form-control add-style wesl_dt date-add' placeholder='DD-MMM-YYYY'>");
                    sb.append("</div>");
                    sb.append("</div>");
                    sb.append("<div class='col-lg-4 col-md-4 col-sm-4 col-4 form_group'>");
                    sb.append("<label class='form_label'>To</label>");
                    sb.append("<div class='row'>");
                    sb.append("<div class='col-lg-6 col-md-6 col-sm-6 col-12'>");
                    sb.append("<div class='mt-checkbox-inline not_applicable'>");
                    sb.append("<label class='mt-checkbox mt-checkbox-outline'> Not Applicable");
                    sb.append("<input type='checkbox' value='1' name='cbtodate'>");
                    sb.append("<span></span>");
                    sb.append("</label>");
                    sb.append("</div>");
                    sb.append("</div>");
                    sb.append("<div class='col-lg-6 col-md-6 col-sm-6 col-12'>");
                    sb.append("<div class='input-daterange input-group'>");
                    sb.append("<input type='text' name='todate' value='' id='todate' class='form-control add-style wesl_dt date-add' placeholder='DD-MMM-YYYY'>");
                    sb.append("</div>");
                    sb.append("</div>");
                    sb.append("</div>");
                    sb.append("</div>");
                    sb.append("<div class='col-lg-4 col-md-4 col-sm-4 col-4 form_group'>");
                    sb.append("<label class='form_label'>Upload Certificate (.pdf/.jpeg/.png)( max size 5 mb)</label>");
                    sb.append("<input id='upload1' type='file' name = 'attachment' onchange=\"javascript: setClass('1');\">");
                    sb.append("<a href='javascript:;' id='upload_link_1' class='attache_btn attache_btn_white uploaded_img1'>");
                    sb.append("<i class='fas fa-paperclip'></i> Attachment");
                    sb.append("</a>");
                    sb.append("</div>");
                    sb.append("</div>");
                    sb.append("</div>");

                    sb.append("<div class='col-md-12'>");
                    sb.append("<div class='row'>");
                    sb.append("<div class='col-lg-8 col-md-8 col-sm-8 col-12 form_group'  id = 'remarksId'>");
                    sb.append("<label class='form_label'>Remarks</label>");
                    sb.append("<textarea name='remarks' class='form-control' maxlength='2000' style = 'height : 180px'/>");
                    sb.append("</textarea>");
                    sb.append("</div>");
                    sb.append("<div class='col-lg-8 col-md-8 col-sm-8 col-12 form_group' id = 'descriId'>");
                    sb.append("<label class='form_label'>Description</label>");
                    sb.append("<span class='form-control'>");
                    String description = !info.getDescription().equals("") ? info.getDescription() : "&nbsp;";
                    sb.append(description);	
                    sb.append("</span>");
                    sb.append("</div>");	
                    sb.append("<div class='col-lg-4 col-md-4 col-sm-4 col-12 form_group' id = 'reltrainId'>");
                    sb.append("<label class='form_label'>Relative Training</label>");
                    sb.append("<span class='form-control'>");
                    String relativetraining = info.getCourseNamerel() != null && !info.getCourseNamerel().equals("") ? info.getCourseNamerel() : "NA";
                    sb.append(relativetraining);
                    sb.append("</span>");
                    sb.append("</div>");
                    sb.append("</div>");
                    sb.append("<div class='row' id = 'assignId'>");
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
                    sb.append("<input type='hidden' name ='mcourseId' value = '"+info.getCourseId()+"'/>");
                    sb.append("<input type='hidden' name ='mpositionId' value = '"+info.getPositionId()+"'/>");
                    sb.append("<input type='hidden' name ='mcrewrotationId' value = '"+candidateId+"'/>");
                    sb.append("<input type='hidden' name ='coursenameId' value = '"+info.getCoursenameId()+"'/>");
//                    sb.append("<input type='hidden' name ='candidateId' value = '"+candidateId+"'/>");
                    sb.append("<input type='hidden' name ='typefrom' value = '"+typefrom+"'/>");
                    sb.append("<div class='col-lg-12 col-md-12 col-sm-12 col-12 text-right'><a href=\"javascript: savePersonalmodal();\" class='save_page'><img src='../assets/images/save.png'> Save</a></div>");
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