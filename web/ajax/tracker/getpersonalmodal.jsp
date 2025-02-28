<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.Collection"%>
<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@page import="com.web.jxp.user.UserInfo" %>
<%@page import="com.web.jxp.tracker.TrackerInfo" %>
<jsp:useBean id="tracker" class="com.web.jxp.tracker.Tracker" scope="page"/>
<jsp:useBean id="vobj" class="com.web.jxp.base.Validate" scope="page"/>
<%
    try 
    {
        if (session.getAttribute("LOGININFO") != null) 
        {
            if (request.getParameter("typefrom") != null)
            {
                String trackerIds = request.getParameter("trackerId") != null && !request.getParameter("trackerId").equals("") ? vobj.replaceint(request.getParameter("trackerId")) : "0";
                String candidateIds = request.getParameter("candidateId") != null && !request.getParameter("candidateId").equals("") ? vobj.replaceint(request.getParameter("candidateId")) : "0";
                String typefroms = request.getParameter("typefrom") != null && !request.getParameter("typefrom").equals("") ? vobj.replaceint(request.getParameter("typefrom")) : "0";
                String pcodeIds = request.getParameter("pcodeId") != null && !request.getParameter("pcodeId").equals("") ? vobj.replaceint(request.getParameter("pcodeId")) : "0";
                String pcodeids = request.getParameter("pcodeids") != null && !request.getParameter("pcodeids").equals("") ? vobj.replacedesc(request.getParameter("pcodeids")) : "";
                String positionIds = request.getParameter("positionId") != null && !request.getParameter("positionId").equals("") ? vobj.replaceint(request.getParameter("positionId")) : "0";
                int pcodeId = Integer.parseInt(pcodeIds);
                int trackerId = Integer.parseInt(trackerIds);
                int candidateId = Integer.parseInt(candidateIds);
                int typefrom = Integer.parseInt(typefroms);
                int positionId = Integer.parseInt(positionIds);
                response.setContentType("text/html");
                response.setHeader("Cache-Control", "no-cache");
                StringBuilder sb = new StringBuilder();
                if (candidateId > 0 || pcodeId > 0) 
                {                    
                    TrackerInfo cinfo = tracker.getBasicDetail(candidateId); 
                    ArrayList alist = new ArrayList();
                    String name = "", positionName = "", role = "", description = "", assessmenttype = ""; 
                    int status = 0, clientassetId = 0;
                    if(cinfo != null)
                    {
                        name = cinfo.getName() != null ? cinfo.getName() : "";
                        positionName = cinfo.getPositionName() != null ? cinfo.getPositionName() : "";
                        clientassetId = cinfo.getClientassetId();
                        if(positionId == cinfo.getPositionId())
                        {
                            positionName = cinfo.getPositionName() != null ? cinfo.getPositionName() : "";
                        }
                        else if(positionId == cinfo.getPositionId2())
                        {
                            positionName = cinfo.getPosition2() != null ? cinfo.getPosition2() : "";
                        }
                    }
                    
                    if(status <= 1)
                    {
                        alist = tracker.getAssessorList(clientassetId, positionId);
                    }
                    if(typefrom == 2)
                    {
                        TrackerInfo sinfo = tracker.getAssessmentTypeName(positionId, pcodeId);
                        if(sinfo != null)
                        {
                            pcodeId = sinfo.getDdlValue();
                            assessmenttype = sinfo.getDdlLabel() != null ?  sinfo.getDdlLabel() : "";
                        }
                    }
                    TrackerInfo info = tracker.getTrackerInfo(pcodeId, trackerId);
                    if(info != null)
                    {
                        status = info.getStatus();
                        role = info.getRole() != null ? info.getRole() : "";
                        description = info.getDescription() != null ? info.getDescription() : "";
                    }
                    
                    int alist_size = alist.size();
                    sb.append("<div class='row'>");   
                    sb.append("<input type='hidden' name='pcodeids' value='"+pcodeids+"' />");
                    sb.append("<input type='hidden' name='pcodeIdModal' value='"+pcodeId+"' />");
                    sb.append("<input type='hidden' name='trackerId' value='"+trackerId+"' />"); 
                    sb.append("<input type='hidden' name='typefrom' value='"+typefrom+"' />");    
                    sb.append("<input type='hidden' name='candidateIdModal' value='"+candidateId+"' />");   
                        sb.append("<div class='col'>"); 
                        if(!pcodeids.equals(""))
                        {
                            sb.append("<h2 data-bs-toggle='modal' data-bs-target='#selected_role_comp_modal' href='javascript:;' onclick=\"javascript: setmultiplerole('"+pcodeids+"', '"+typefrom+"');\"><span>");
                            if(typefrom == 1)
                                sb.append("SHOW SELECTED ROLE COMPETENCIES</span>");
                            sb.append("</h2>");
                        }
                        else
                        {
                            if(typefrom == 1)
                                sb.append("<h2>"+role+"</h2>");
                            else
                                sb.append("<h2>"+name+"</h2>");
                        }
                        sb.append("</div>");
                        sb.append("<div class='col col-lg-4 hist_prog_unass text-right'>");
                            sb.append("<ul>");
                                sb.append("<li class='role_com_view'><a href='javascript:;' data-bs-toggle='modal' data-bs-target='#history_modal' onclick=\"javascript: gethistory('"+trackerId+"');\"><img src='../assets/images/reload-time-blue.png' />View History</a></li>");
                                sb.append("<li class='rol_com_unassigned'>"+tracker.getStatus(status)+"</li>");
                            sb.append("</ul>");
                        sb.append("</div>");
                    sb.append("</div>");
                    sb.append("<div class='row client_position_table1'>");
                        sb.append("<div class='col-md-12 mt_15 mb_20'>");
                            sb.append("<div class='row d-flex align-items-center'>");
                                sb.append("<div class='col-md-6 com_label_value'>");
                                if(typefrom == 1)
                                {
                                    sb.append("<div class='row mb_0'>");
                                        sb.append("<div class='col-md-3'><label>Personnel Details</label></div>");
                                        sb.append("<div class='col-md-9'><span>"+name+"</span></div>");
                                    sb.append("</div>");
                                    sb.append("<div class='row mb_0'>");
                                        sb.append("<div class='col-md-3'><label>Position-Rank</label></div>");
                                        sb.append("<div class='col-md-9'><span>"+positionName+"</span></div>");
                                    sb.append("</div>");
                                }
                                else
                                {
                                    sb.append("<div class='row mb_0'>");
                                        sb.append("<div class='col-md-3'><label>Role Competency</label></div>");
                                        sb.append("<div class='col-md-9'><span>"+role+"</span></div>");
                                    sb.append("</div>");        
                                    sb.append("<div class='row mb_0'>");
                                        sb.append("<div class='col-md-3'><label>Assessment Type</label></div>");
                                        sb.append("<div class='col-md-9'><span>"+assessmenttype+"</span></div>");
                                    sb.append("</div>");
                                }
                                sb.append("</div>");
                                sb.append("<div class='col-md-4 com_label_value'>&nbsp;</div>");
                                sb.append("<div class='col-md-2'>");
                                    sb.append("<div class='ref_vie_ope'>");
                                        sb.append("<ul>");
                                            sb.append("<li class='com_view_job'><a href=\"javascript:viewcandidate('"+candidateId+"');\"><img src='../assets/images/view.png'><br> View Personnel Profile</a></li>");
                                        sb.append("</ul>");
                                    sb.append("</div>");
                                sb.append("</div>");
                            sb.append("</div>");
                        sb.append("</div>");
                        sb.append("<div class='col-md-12'>");
                            sb.append("<div class='row'>");                                
                                sb.append("<div class='col-lg-8 col-md-8 col-sm-8 col-12'>");
                                if(pcodeids.equals(""))
                                {
                                    sb.append("<div class='row'>");
                                        sb.append("<div class='col-lg-12 col-md-12 col-sm-12 col-12 form_group'>");
                                            sb.append("<label class='form_label'>Description</label>");
                                            sb.append("<span class='form-control'>"+(!description.equals("") ? description : "&nbsp")+"</span>");
                                        sb.append("</div>");
                                    sb.append("</div>");
                                }
                                sb.append("</div>");
                                sb.append("<div class='col-lg-4 col-md-4 col-sm-12 col-12'>");
                                    sb.append("<div class='row'>");
                                        sb.append("<div class='col-lg-12 col-md-12 col-sm-12 col-12 form_group'>");
                                            sb.append("<label class='form_label'>Complete By (Knowledge Assessment)</label>");
                                            sb.append("<div class='input-daterange input-group'>");
                                                sb.append("<input type='text' name='completebydate1' value='' id='completebydate1' class='form-control add-style wesl_dt date-add' placeholder='DD-MMM-YYYY' maxlength='11' />");
                                            sb.append("</div>");
                                        sb.append("</div>");
                                    sb.append("</div>");
                                    
                                     sb.append("<div class='row'>");
                                        sb.append("<div class='col-lg-12 col-md-12 col-sm-12 col-12 form_group'>");
                                            sb.append("<label class='form_label'>Complete By (Practical Assessment)</label>");
                                            sb.append("<div class='input-daterange input-group'>");
                                                sb.append("<input type='text' name='completebydate2' value='' id='completebydate2' class='form-control add-style wesl_dt date-add' placeholder='DD-MMM-YYYY' maxlength='11' />");
                                            sb.append("</div>");
                                        sb.append("</div>");
                                    sb.append("</div>");
                                    
                                sb.append("</div>");
                                sb.append("<div class='col-lg-8 col-md-8 col-sm-8 col-4'>");
                                    sb.append("<label class='form_label'>Select Competency Assessor</label>");
                                    sb.append("<div class='full_width com_ass_table'>");
                                        sb.append("<div class='col-md-12 table-rep-plugin sort_table'>");
                                            sb.append("<div class='table-responsive mb-0'>");
                                                sb.append("<table id='tech-companies-1' class='table table-striped'>");
                                                    sb.append("<thead>");
                                                        sb.append("<tr>");
                                                            sb.append("<th width='65%'><b>Name</b></th>");
                                                            sb.append("<th width='20%' class='text-center'><b>View Profile</b></th>");
                                                            sb.append("<th width='15%' class='text-center'><b>Select</b></th>");
                                                        sb.append("</tr>");
                                                    sb.append("</thead>");
                                                    sb.append("<tbody>");
                                                    if(alist_size > 0)
                                                    {
                                                        String viewpath = tracker.getMainPath("view_user_file"); 
                                                        for(int i = 0; i < alist_size; i++)
                                                        {
                                                            TrackerInfo ainfo = (TrackerInfo) alist.get(i);
                                                            if(ainfo != null)
                                                            {
                                                                sb.append("<tr>");
                                                                    sb.append("<td>"+(ainfo.getName() != null ? ainfo.getName() : "")+"</td>");
                                                                    if(ainfo.getFileName() != null && !ainfo.getFileName().equals(""))
                                                                        sb.append("<td class='action_column text-center'><a href=\"javascript:;\" data-bs-toggle='modal' data-bs-target='#view_pdf' onclick=\"javascript: setIframe('"+viewpath+ainfo.getFileName()+"');\"><img src='../assets/images/view.png'></a></td>");
                                                                    else
                                                                        sb.append("<td class='action_column text-center'>&nbsp;</td>");
                                                                    sb.append("<td class='text-center'>");
                                                                        sb.append("<label class='mt-radio mt-radio-outline'>"); 
                                                                            sb.append("<input type='radio' value='"+ainfo.getUserId()+"' name='auserId' />");
                                                                            sb.append("<span></span>"); 
                                                                        sb.append("</label>");
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
                                sb.append("</div>");
                            sb.append("</div>");
                        sb.append("</div>");
                    sb.append("</div>");
                    if(typefrom ==1)
                    {
                        sb.append("<div class='row sticky_div'>");	
                            sb.append("<div class='col-lg-12 col-md-12 col-sm-12 col-12 text-right' id='assigndiv'><a href='javascript: savePersonalmodalassign();' class='save_page'>Assign</a></div>");
                        sb.append("</div>");
                    }
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