<%@page import="com.web.jxp.crewinsurance.CrewinsuranceInfo"%>
<%@page contentType="text/html"%>
<%@page language="java" import="java.util.ArrayList"%>
<%@page pageEncoding="UTF-8"%>
<%@page import="com.web.jxp.user.UserInfo"%>
<jsp:useBean id="crewinsurance" class="com.web.jxp.crewinsurance.Crewinsurance" scope="page"/>
<jsp:useBean id="vobj" class="com.web.jxp.base.Validate" scope="page"/>
<%
    try 
    {
        if (session.getAttribute("LOGININFO") != null) 
        {
            UserInfo uinfo = ((UserInfo) request.getSession().getAttribute("LOGININFO"));
            String editper = "N", addper ="";
            if (uinfo != null) 
            {
                editper = uinfo.getEditper() != null ? uinfo.getEditper() : "N";
                addper = uinfo.getAddper() != null ? uinfo.getAddper() : "N";
            }
            String crewinsuranceIds = request.getParameter("crewinsuranceId") != null && !request.getParameter("crewinsuranceId").equals("") ? vobj.replaceint(request.getParameter("crewinsuranceId")) : "0";
            String crewrotationIds = request.getParameter("crewrotationId") != null && !request.getParameter("crewrotationId").equals("") ? vobj.replaceint(request.getParameter("crewrotationId")) : "0";
            String typeids = request.getParameter("type") != null && !request.getParameter("type").equals("") ? vobj.replaceint(request.getParameter("type")) : "0";
            
            int type = Integer.parseInt(typeids);
            int crewinsuranceId = Integer.parseInt(crewinsuranceIds);
            int crewrotationId = Integer.parseInt(crewrotationIds);
            String name = "", fromdate = "", todate = "", statusValue = "", filename = "", position = "", stcolor = "",
            nominee= "", relation = "", contactno = "", selectedNominee = "";
            int dependent = 0, candidateId = 0, nomineeId = 0;    
            String viewpath = crewinsurance.getMainPath("view_candidate_file");
            CrewinsuranceInfo info = crewinsurance.getCrewinsuranceDetailById(crewrotationId, crewinsuranceId);
            StringBuilder sb = new StringBuilder();
            if(type == 1)
            {
                ArrayList list = new ArrayList();
                if(info != null)
                {
                    candidateId = info.getCandidateId();
                    list = crewinsurance.getNomineeList(candidateId);

                    statusValue = info.getStval();
                    name = info.getName() != null ? info.getName() : "";
                    selectedNominee = info.getSelectedNominee()!= null ? info.getSelectedNominee() : "";
                    fromdate = info.getFromDate();
                    todate = info.getToDate();
                    filename = info.getFilename();
                    dependent = info.getDependent();

                    position = info.getPosition();
                    if(statusValue.equals("Pending"))
                        stcolor ="modal_status pen_bg";
                    else if(statusValue.equals("Uploaded"))
                        stcolor = "modal_status uploaded_bg";
                    else if(statusValue.equals("Expired"))
                        stcolor = "modal_status upload_expired_bg";
                    else        
                        stcolor="modal_status expiry_bg";        
                    if(dependent <= 0)
                    {
                        dependent = list.size();
                    }
                }             
                
                       sb.append("<div class='row m_15'>");                   
                            sb.append("<div class='col'>");
                                sb.append("<h2 class='text-start'>UPLOAD INSURANCE CERTIFICATE</h2>");
                            sb.append("</div>");

                        sb.append("<div class='col col-lg-3 text-right pd_0' >");                                             
                            sb.append("<span class='"+stcolor+"' id='statusvalue'>"+statusValue+"</span>");                                              
                        sb.append("</div>");

                    sb.append("<div class='row client_position_tsble1'>");
                sb.append("<h3><span>"+name+"|</span> <span name='position'>"+position+"</span></h3>");
                sb.append("<div class='col-md-12' id='' style=''>");
                    sb.append("<div class='row'>");

                        sb.append("<div class='col-md-12'><h4>Certificate Validity</h4></div>");
                        sb.append("<div class='col-lg-6 col-md-6 col-sm-6 col-12 form_group'>");
                            sb.append("<label class='form_label'>From</label>");
                                    sb.append("<div class='input-daterange input-group'>");
                                sb.append("<input type='text' name='fromDate' value='"+fromdate+"' id='statrtDate'  class='form-control add-style wesl_dt date-add text-center' placeholder='DD-MMM-YYYY'>");
                            sb.append("</div>");
                        sb.append("</div>");

                        sb.append("<div class='col-lg-6 col-md-6 col-sm-6 col-12 form_group'>");
                            sb.append("<label class='form_lsbel'>To</label>");
                            sb.append("<div class='input-daterange input-group'>");                                                        
                                sb.append("<input type='text' name='toDate' value='"+todate+"' id='endDate'  class='form-control add-style wesl_dt date-add text-center' placeholder='DD-MMM-YYYY'>");
                            sb.append("</div>");
                        sb.append("</div>");

                        sb.append("<div class='col-lg-6 col-md-6 col-sm-6 col-12 form_group'>");
                            sb.append("<label class='form_lsbel'>No. Of Dependents</label>");
                            sb.append("<input type ='text' name='dependent' value='"+dependent+"' id='onofdependent' class='form-control text-right' maxlength='2' onkeypress='return allowPositiveNumber1(event);'/>");
                        sb.append("</div>");

                        sb.append("<div class='col-lg-6 col-md-6 col-sm-12 col-12 form_group'>");
                            sb.append("<label class='form_lsbel'>Upload Certificate (.pdf/.jpeg/.png/.jpg)</label>");                                                 
                            sb.append("<input type='file' name='certificatefile' id='upload1' onchange=\"javascript: setClass('1');\"/>");
                            sb.append("<a href='javascript:;' id='upload_link_1' class='attache_btn uploaded_img1'><i class='fas fa-paperclip'></i> Attach</a>");
                            if(!filename.equals(""))
                                sb.append("<div class='down_prev' id='preview_1'><a href='"+viewpath + filename+"' id='cretfile' target='_blank'>Preview</a></div>");
                        sb.append("</div></div></div></div>");

                sb.append("<div class='col-lg-12 col-md-12 col-sm-12 col-12'>");
                sb.append("<label class='form_label'>Select Insurance Nominee</label>");
                sb.append("<div class='full_width com_ass_table'>");
                sb.append("<div class='col-md-12 table-rep-plugin sort_table'>");
                sb.append("<div class='table-responsive mb-0'>");
                sb.append("<table id='tech-companies-1' class='table table-striped'>");
                sb.append("<thead>");
                sb.append("<tr>");
                sb.append("<th width='%' class='text-center'>");
                       sb.append("<input type='checkbox' id='cball' name ='cball' class='singlechkbox' onchange='javascript: checkAll();' />");
                sb.append("</th>");
                sb.append("<th width='%'><b>Name</b></th>");
                sb.append("<th width='%'><b>Relation</b></th>");
                sb.append("<th width='%'><b>Contact</b></th>");
                sb.append("</tr>");
                sb.append("</thead>");
                sb.append("<tbody>");


                CrewinsuranceInfo ninfo;
                for(int i = 0; i < list.size(); i++)
                {
                    ninfo = (CrewinsuranceInfo) list.get(i);
                    if(ninfo != null)
                    {
                        nominee = ninfo.getNominee()!= null ? ninfo.getNominee(): "";
                        relation = ninfo.getRelation()!= null ? ninfo.getRelation(): "";
                        contactno = ninfo.getContactno()!= null ? ninfo.getContactno(): "";
                        nomineeId = ninfo.getNomineeId();                        
                    }
                    sb.append("<tr>");
                    sb.append("<td class='text-center'>");

                    sb.append("<label class='mt-checkbox mt-checkbox-outline'>");
                    sb.append("<input type='checkbox' value='");
                    sb.append(nomineeId+"'");
                    sb.append("name='cb1' id='cb1' onchange =\" javascript : checkcb();\" ");
                    if(crewinsurance.checkToStr(selectedNominee,""+nomineeId))
                    {
                        sb.append(" checked ");
                    }
                    sb.append("/>");
                    sb.append("<span></span>");
                    sb.append("</label>");

                    sb.append("</td>");

                    sb.append("<td>"+nominee+"</td>");
                    sb.append("<span></span>");  
                    sb.append("</label>");
                    sb.append("</td>");
                    sb.append("<td>"+relation+"</td>");
                    sb.append("<span></span>");  
                    sb.append("</label>");
                    sb.append("</td>");
                    sb.append("<td>"+contactno+"</td>");
                    sb.append("<span></span>");  
                    sb.append("</label>");
                    sb.append("</td>");
                    sb.append("</tr>");
                    sb.append("</tbody>");
                }
                sb.append("</table>");
                sb.append("</div>");
                sb.append("</div>");
                sb.append("</div>");
                sb.append("</div>");
                sb.append("</div>");
                sb.append("</div>");
                sb.append("</div>");

                if(addper.equals("Y") || editper.equals("Y"))
                {
                    sb.append("<div class='row'>");
                       sb.append("<div class='col-lg-12 col-md-12 col-sm-12 col-12 text-center' id='submitdiv'>");
                       sb.append("<a href=\"javascript: submitForm();\" class='save_page'><img src='../assets/images/save.png'> Save</a>");
                   sb.append("</div>");                                              
                }
             }else{
                ArrayList list = new ArrayList();
                if(info != null)
                {
                    candidateId = info.getCandidateId();
                    list = crewinsurance.getNomineeList(candidateId);

                    statusValue = info.getStval();
                    name = info.getName() != null ? info.getName() : "";
                    selectedNominee = info.getSelectedNominee()!= null ? info.getSelectedNominee() : "";
                    fromdate = info.getFromDate();
                    todate = info.getToDate();
                    filename = info.getFilename();
                    dependent = info.getDependent();

                    position = info.getPosition();
                    if(statusValue.equals("Pending"))
                        stcolor ="modal_status pen_bg";
                    else if(statusValue.equals("Uploaded"))
                        stcolor = "modal_status uploaded_bg";
                    else if(statusValue.equals("Expired"))
                        stcolor = "modal_status upload_expired_bg";
                    else        
                        stcolor="modal_status expiry_bg"; 
                }             
                
                       sb.append("<div class='row m_15'>");                   
                            sb.append("<div class='col'>");
                                sb.append("<h2 class='text-start'>UPLOAD INSURANCE CERTIFICATE</h2>");
                            sb.append("</div>");

                        sb.append("<div class='col col-lg-3 text-right pd_0' >");                                             
                            sb.append("<span class='"+stcolor+"' id='statusvalue'>"+statusValue+"</span>");                                              
                        sb.append("</div>");

                    sb.append("<div class='row client_position_tsble1'>");
                sb.append("<h3><span>"+name+"|</span> <span name='position'>"+position+"</span></h3>");
                sb.append("<div class='col-md-12' id='' style=''>");
                    sb.append("<div class='row'>");

                        sb.append("<div class='col-md-12'><h4>Certificate Validity</h4></div>");
                        sb.append("<div class='col-lg-6 col-md-6 col-sm-6 col-12 form_group'>");
                            sb.append("<label class='form_label'>From</label>");
                                    sb.append("<div class='input-daterange input-group'>");
                                    sb.append("<span class='form-control'>"+fromdate+"</span>");
                            sb.append("</div>");
                        sb.append("</div>");

                        sb.append("<div class='col-lg-6 col-md-6 col-sm-6 col-12 form_group'>");
                            sb.append("<label class='form_lsbel'>To</label>");
                            sb.append("<div class='input-daterange input-group'>");   
                            sb.append("<span class='form-control'>"+todate+"</span>");
                            sb.append("</div>");
                        sb.append("</div>");

                        sb.append("<div class='col-lg-6 col-md-6 col-sm-6 col-12 form_group'>");
                            sb.append("<label class='form_lsbel'>No. Of Dependents</label>");
                             sb.append("<span class='form-control text-right'>"+dependent+"</span>");
                        sb.append("</div>");

                        sb.append("<div class='col-lg-6 col-md-6 col-sm-12 col-12 form_group'>");
                            sb.append("<label class='form_lsbel'>Attachment</label>"); 
                            if(!filename.equals(""))
                                sb.append("<div class='down_prev' id='preview_1'><a href='"+viewpath + filename+"' id='cretfile' target='_blank'>Preview</a></div>");
                        sb.append("</div></div></div></div>");

                sb.append("<div class='col-lg-12 col-md-12 col-sm-12 col-12'>");
                sb.append("<div class='full_width com_ass_table'>");
                sb.append("<div class='col-md-12 table-rep-plugin sort_table'>");
                sb.append("<div class='table-responsive mb-0'>");
                sb.append("<table id='tech-companies-1' class='table table-striped'>");
                sb.append("<thead>");
                sb.append("<tr>");
                sb.append("<th width='%'><b>Name</b></th>");
                sb.append("<th width='%'><b>Relation</b></th>");
                sb.append("<th width='%'><b>Contact</b></th>");
                sb.append("</tr>");
                sb.append("</thead>");
                sb.append("<tbody>");


                CrewinsuranceInfo ninfo;
                for(int i = 0; i < list.size(); i++)
                {
                    ninfo = (CrewinsuranceInfo) list.get(i);
                    if(ninfo != null)
                    {
                        nominee = ninfo.getNominee()!= null ? ninfo.getNominee(): "";
                        relation = ninfo.getRelation()!= null ? ninfo.getRelation(): "";
                        contactno = ninfo.getContactno()!= null ? ninfo.getContactno(): "";
                        nomineeId = ninfo.getNomineeId();                        
                    }
                    sb.append("<tr>");
                    if(crewinsurance.checkToStr(selectedNominee,""+nomineeId))
                    {
                        sb.append("<td>"+nominee+"</td>");
                        sb.append("<span></span>");  
                        sb.append("</label>");
                        sb.append("</td>");
                        sb.append("<td>"+relation+"</td>");
                        sb.append("<span></span>");  
                        sb.append("</label>");
                        sb.append("</td>");
                        sb.append("<td>"+contactno+"</td>");
                        sb.append("<span></span>");  
                        sb.append("</label>");
                        sb.append("</td>");
                    }
                    sb.append("</tr>");
                    sb.append("</tbody>");
                }
                sb.append("</table>");
                sb.append("</div>");
                sb.append("</div>");
                sb.append("</div>");
                sb.append("</div>");
                sb.append("</div>");
                sb.append("</div>");
                sb.append("</div>");
            }
            
            
            
            String st1 = sb.toString();
            sb.setLength(0);
            response.getWriter().write(st1);
        } else {
            response.getWriter().write("Please check your login session....");
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
%>