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
            String addper = "", editper= "";
            int cassessor = 0;
            UserInfo uinfo = (UserInfo) session.getAttribute("LOGININFO");
                if(uinfo != null)
                {                    
                    addper = uinfo.getAddper() != null ? uinfo.getAddper() : "N";
                    editper = uinfo.getEditper() != null ? uinfo.getEditper() : "N";     
                    cassessor = uinfo.getCassessor();                    
                }
            if (request.getParameter("trackerId") != null)
            {
                String trackerIds = request.getParameter("trackerId") != null && !request.getParameter("trackerId").equals("") ? vobj.replaceint(request.getParameter("trackerId")) : "0";
                String typefroms = request.getParameter("typefrom") != null && !request.getParameter("typefrom").equals("") ? vobj.replaceint(request.getParameter("typefrom")) : "0";
                String positionIds = request.getParameter("positionId") != null && !request.getParameter("positionId").equals("") ? vobj.replaceint(request.getParameter("positionId")) : "0";
                int typefrom = Integer.parseInt(typefroms);   
                int trackerId = Integer.parseInt(trackerIds);
                int positionId = Integer.parseInt(positionIds);
                String view_cvfile = tracker.getMainPath("view_user_file");
                String view_path = tracker.getMainPath("view_trackerfiles");
                
                response.setContentType("text/html");
                response.setHeader("Cache-Control", "no-cache");
                Collection coll = tracker.getCompetencyResult();
                
                StringBuilder sb = new StringBuilder();
                if (trackerId > 0) 
                {
                    TrackerInfo info = tracker.TrackInfo(trackerId);
                    
                    String name = "", positionName = "", role = "", assessment = "", priority = "", username = "",  
                    completeddate ="", description = "", file1 = "", file2 = "", loginname1 ="" , loginname2 ="", submitdate1= "", submitdate2 = "", remarks = "",
                    remarks2 = "", completeddate2= "",  resultname1 = "", resultname2= "", outcomeremarks ="", courseName= "", appealremarks = "",  appeal = "", cvfile ="";
                    int status = 0,  month = 0, resultId1 = 0, resultId2 = 0, score = 0,  score2= 0, average= 0, clientassetId =0 , outcomeId = 0,  onlineflag = 0, noofdays= 0;
                    if(info != null)
                    {
                        name = info.getName() != null ? info.getName() : "";
                        role = info.getRole() != null ? info.getRole() : "";
                        status = info.getStatus();
                        month = info.getMonth();
                        completeddate = info.getCompletebydate1() != null ? info.getCompletebydate1() : "";
                        assessment = info.getPassessmenttypeName()!= null ? info.getPassessmenttypeName(): "";
                        priority = info.getPriorityName()!= null ? info.getPriorityName(): "";
                        username = info.getUserName() != null ? info.getUserName() : "";
                        loginname1 = info.getLoginname1()!= null ? info.getLoginname1() : "";
                        loginname2 = info.getLoginname2() != null ? info.getLoginname2() : "";
                        submitdate1 = info.getSubmitdate1()!= null ? info.getSubmitdate1() : "";
                        submitdate2 = info.getSubmitdate2() != null ? info.getSubmitdate2() : "";
                        description = info.getDescription()!= null ? info.getDescription(): "";
                        file1 = info.getFileName()!= null ? info.getFileName(): "";
                        file2 = info.getFileName2()!= null ? info.getFileName2(): "";
                        remarks = info.getRemarks()!= null ? info.getRemarks(): "";
                        remarks2 = info.getPracticalremarks()!= null ? info.getPracticalremarks(): "";
                        completeddate2 = info.getCompletebydate2()!= null ? info.getCompletebydate2(): "";
                        resultname1 = info.getResultname1()!= null ? info.getResultname1(): "";
                        resultname2 = info.getResultname2()!= null ? info.getResultname2(): "";
                        score = info.getScore();
                        score2 = info.getPrcaticalscore();
                        resultId1 = info.getResultId();
                        resultId2 = info.getResultId2();
                        average = info.getAverage();
                        clientassetId = info.getClientassetId();
                        outcomeId = info.getOutcomeId();
                        courseName = info.getCourseName() != null ? info.getCourseName(): "";
                        outcomeremarks = info.getOutcomeremarks()!= null ? info.getOutcomeremarks(): "";
                        appealremarks = info.getAppealremarks()!= null ? info.getAppealremarks(): "";
                        appeal = info.getAppeal()!= null ? info.getAppeal(): "";                        
                        onlineflag = info.getOnlineflag();
                        noofdays = info.getNoofdays();
                        cvfile = info.getCvfile()!= null ? info.getCvfile(): "";
                        if(positionId == info.getPositionId())
                        {
                            positionName = info.getPositionName() != null ? info.getPositionName() : "";
                        }
                        else if(positionId == info.getPositionId2())
                        {
                            positionName = info.getPosition2() != null ? info.getPosition2() : "";
                        }
                    }           
                    Collection training = null;
                    if(status ==3)
                        training = tracker.getTraining(clientassetId, positionId);
                    Collection reasons = null;
                    if(status ==6)
                        reasons = tracker.getRejectionReason();
                    if(status == 2 && (typefrom ==1 || typefrom ==2))
                    {
                            sb.append("<div class='row'>");
                            sb.append("<input type='hidden' name='trackerId' value='"+trackerId+"' />");   
                            sb.append("<input type='hidden' name='statusModal' value='"+status+"' />");
                            sb.append("<input type='hidden' name='username1Hiden' value='"+loginname1+"' />");
                            sb.append("<input type='hidden' name='username2Hiden' value='"+loginname2+"' />");
                            sb.append("<input type='hidden' name='trackerFilehidden' value='"+file1+"' />");
                            sb.append("<input type='hidden' name='trackerFilehidden2' value='"+file2+"' />");
                            sb.append("<input type='hidden' name='typefrom' value='"+typefrom+"' />"); 
                            sb.append("<input type='hidden' name='month' value='"+month+"' />"); 
                            sb.append("<div class='row'>");
                            sb.append("<div class='col-lg-12'>");
                                sb.append("<div class='row'>");
                                    sb.append("<div class='col'>");
                                        sb.append("<h2>"+name+" | "+positionName+"</h2>");
                                    sb.append("</div>");
                                    sb.append("<div class='col col-lg-5 hist_prog_unass text-right'>");
                                        sb.append("<ul>");
                                            sb.append("<li class='role_com_view'><a href='javascript:;' data-bs-toggle='modal' data-bs-target='#history_modal' onclick=\"javascript: gethistory('"+trackerId+"');\"><img src='../assets/images/reload-time-blue.png' />View History</a></li>");
                                            sb.append("<li class='rol_com_inprogress'>"+tracker.getStatus(status)+"</li>");
                                        sb.append("</ul>");
                                    sb.append("</div>");
                                sb.append("</div>");


                                sb.append("<div class='row client_position_table1'>");
                                    sb.append("<div class='col-md-12 mt_15 mb_20'>");
                                        sb.append("<div class='row d-flex align-items-end'>");
                                            sb.append("<div class='col-md-6 com_label_value'>");
                                                sb.append("<div class='row mb_0'>");
                                                    sb.append("<div class='col-md-3 pd_right_0'><label>Role Competency</label></div>");
                                                    sb.append("<div class='col-md-9'><span>"+role+"</span></div>");
                                                sb.append("</div>");
                                                sb.append("<div class='row mb_0'>");
                                                    sb.append("<div class='col-md-3 pd_right_0'><label>Assessment Type</label></div>");
                                                    sb.append("<div class='col-md-9'><span>"+assessment+"</span></div>");
                                                sb.append("</div>");
                                            sb.append("</div>");
                                            sb.append("<div class='col-md-4'>");
                                                sb.append("<div class='row'>");
                                                    sb.append("<div class='col-md-6 com_label_value'>");
                                                        sb.append("<div class='row'>");
                                                            sb.append("<div class='col-md-7 text-right'><label>Priority</label></div>");
                                                            sb.append("<div class='col-md-5'><span>"+priority+"</span></div>");	
                                                        sb.append("</div>");
                                                    sb.append("</div>");
                                                    sb.append("<div class='col-md-6 com_label_value'>");
                                                        sb.append("<div class='row'>");
                                                            sb.append("<div class='col-md-5 text-right'><label>Validity</label></div>");
                                                            sb.append("<div class='col-md-7 pd_0'><span>"+month+" Months</span></div>");	
                                                        sb.append("</div>");
                                                    sb.append("</div>");
                                                sb.append("</div>");	
                                            sb.append("</div>");
                                            sb.append("<div class='col-md-2'>");
                                                sb.append("<div class='ref_vie_ope'>");
                                                    sb.append("<ul>");
                                                        sb.append("<li class='com_view_job'><a href=\"javascript: viewCompetency();\"><img src='../assets/images/view.png'><br> View Competency</a></li>");
                                                    sb.append("</ul>");
                                                sb.append("</div>");
                                            sb.append("</div>");
                                        sb.append("</div>");
                                    sb.append("</div>");

                                    sb.append("<div class='col-md-12'>");
                                        sb.append("<div class='row'>");
                                            sb.append("<div class='col-lg-8 col-md-8 col-sm-8 col-12'>");
                                                sb.append("<div class='row'>");
                                                    sb.append("<div class='col-lg-12 col-md-12 col-sm-12 col-12 form_group'>");
                                                        sb.append("<label class='form_label'>Description</label>");
                                                        sb.append("<span class='form-control'>"+(!description.equals("") ? description : "&nbsp;") +"</span>");
                                                    sb.append("</div>");
                                                    
                                                    sb.append("<div class='col-lg-12 col-md-12 col-sm-12 col-12 form_group'>");
                                                        sb.append("<label class='form_label'>Knowledge Competency Sheet</label>");
                                                        sb.append("<div class='row d-flex align-items-center'>");
                                                        
                                                           sb.append("<div class='col-md-3'>");
                                                                sb.append("<input type='file' name='trackerFile' id='upload2' onchange=\"javascript: setClass('2');\">");
                                                            if(file1.equals(""))
                                                            {
                                                                sb.append("<a href=\"javascript:;\" id='upload_link_2' class='attache_btn attache_btn_white uploaded_img1' >");
                                                                sb.append("<i class='fas fa-paperclip'></i> Attach</a>");                                                             
                                                            }
                                                            else
                                                            {
                                                                sb.append("<a class='attache_btn attache_btn_white view_num_record' href=\"javascript:;\" data-bs-toggle='modal' data-bs-target='#view_pdf' onclick=\"javascript: setIframe('"+view_path+file1+"');\"><img src='../assets/images/view.png'/> View <span class='attach_number'></span></a>");
                                                            }
                                                            sb.append("</div>");
                                                            if(onlineflag ==2)
                                                            {
                                                                sb.append("<div class='col-md-3 pd_0'>");
                                                                        sb.append("<a href='javascript:;' data-bs-toggle='modal' data-bs-target='#online_submission_modal' class='attache_btn attache_btn_white view_num_record' onclick=\"javascript: getquestion('"+trackerId+"');\"><img src='../assets/images/view.png'/> Online Submission</a>");
                                                                sb.append("</div>");
                                                            }
                                                            if(!loginname1.equals(""))
                                                            {
                                                                sb.append("<div class='col-md-6 veri_details'>");
                                                                    sb.append("<ul>");
                                                                        sb.append("<li>"+(!loginname1.equals("") ? loginname1 : "&nbsp;")+"</li>");
                                                                        sb.append("<li>|</li>");
                                                                        sb.append("<li>"+(!submitdate1.equals("") ? submitdate1 : "&nbsp;")+"</li>");
                                                                    sb.append("</ul>");
                                                                sb.append("</div>");
                                                            }
                                                        sb.append("</div>");
                                                    sb.append("</div>");
                                                sb.append("</div>");
                                                 
                                                sb.append("<div class='row'>");

                                                    sb.append("<div class='col-lg-6 col-md-6 col-sm-6 col-12 form_group'>");
                                                        sb.append("<label class='form_label'>Knowledge Competency Score (out of 100)</label>");
                                                        if(score > 0)
                                                            sb.append("<input type='text' name='score' value='"+score+"' id='score' class='form-control' placeholder='Enter Score'   onkeypress='return allowPositiveNumber(event);' onchange=\"javascript: calculateScore();\" maxlength='3' />");
                                                        else
                                                            sb.append("<input type='text' name='score' value='' id='score' class='form-control' placeholder='Enter Score'   onkeypress='return allowPositiveNumber(event);' onchange=\"javascript: calculateScore();\" maxlength='3' />");
                                                    sb.append("</div>");

                                                    sb.append("<div class='col-lg-6 col-md-6 col-sm-6 col-12 form_group'>");
                                                         sb.append("<label class='form_label'>Knowledge Competency Result</label>");
                                                        sb.append("<select class='form-select' name='resultId' id='resultId'>");
                                                        sb.append("<option value='-1'>Select Result</option>");
                                                            if (coll.size() > 0) 
                                                            {
                                                                Iterator iter = coll.iterator();
                                                                while (iter.hasNext()) 
                                                                {
                                                                    info = (TrackerInfo) iter.next();
                                                                    if(resultId1 == info.getDdlValue())
                                                                        sb.append("<option value='" + info.getDdlValue() + "' selected>" + (info.getDdlLabel() != null ? info.getDdlLabel() : "") + "</option>");
                                                                    else
                                                                        sb.append("<option value='" + info.getDdlValue() + "'>" + (info.getDdlLabel() != null ? info.getDdlLabel() : "") + "</option>");
                                                                }
                                                            }                                                        
                                                           sb.append("</select>");
                                                    sb.append("</div>");

                                                    sb.append("<div class='col-lg-12 col-md-12 col-sm-12 col-12 form_group'>");
                                                        sb.append("<label class='form_label'>Remarks</label>");
                                                        sb.append("<textarea name='remarks' class='form-control' rows='3' maxlength='1000'>"+(!remarks.equals("") ? remarks : "")+"</textarea>");
                                                    sb.append("</div>");

                                                sb.append("</div>");

                                                sb.append("<div class='row'>");
                                           
                                               sb.append("<div class='col-lg-12 col-md-12 col-sm-12 col-12 form_group'>");
                                                   sb.append("<label class='form_label'>Practical Competency Documents</label>");
                                                   sb.append("<div class='row d-flex align-items-center'>");
                                                   
                                                       sb.append("<div class='col-md-3'>");
                                                           sb.append("<input type='file' name='trackerFile2' id='upload3' onchange=\"javascript: setClass('3');\">");
                                                           if(file2.equals(""))
                                                           {
                                                               sb.append("<a href=\"javascript:;\" id='upload_link_3' class='attache_btn attache_btn_white uploaded_img1' >");
                                                               sb.append("<i class='fas fa-paperclip'></i> Attach</a>");
                                                           }
                                                           else
                                                           {
                                                              sb.append("<a class='attache_btn attache_btn_white view_num_record' href=\"javascript:;\" data-bs-toggle='modal' data-bs-target='#view_pdf' onclick=\"javascript: setIframe('"+view_path+file2+"');\"><img src='../assets/images/view.png'/> View <span class='attach_number'></span></a>");
                                                           }
                                                       sb.append("</div>");
                                                       if(!loginname2.equals(""))
                                                        {
                                                       sb.append("<div class='col-md-6 veri_details'>");
                                                           sb.append("<ul>");
                                                               sb.append("<li>"+(!loginname2.equals("") ? loginname2: "&nbsp;")+"</li>");
                                                               sb.append("<li>|</li>");
                                                               sb.append("<li>"+(!submitdate2.equals("") ? submitdate2 : "&nbsp;")+"</li>");
                                                           sb.append("</ul>");
                                                       sb.append("</div>");
                                                        }
                                                   sb.append("</div>");
                                               sb.append("</div>");

                                                    sb.append("<div class='col-lg-6 col-md-6 col-sm-6 col-12 form_group'>");

                                                         sb.append("<label class='form_label'>Practical Competency Score (out of 100)</label>");
                                                        sb.append("<input type='text' name='practicalscore' ");
                                                        if(score2 > 0)
                                                            sb.append("<input type='text' name='score2' value='"+score2+"' id='score2' class='form-control' placeholder='Enter Score'   onkeypress='return allowPositiveNumber(event);' onchange=\"javascript: calculateScore();\" maxlength='3' />");
                                                        else
                                                            sb.append("<input type='text' name='score2' value='' id='score2' class='form-control' placeholder='Enter Score'   onkeypress='return allowPositiveNumber(event);' onchange=\"javascript: calculateScore();\" maxlength='3' />");
                                                    sb.append("</div>");

                                                    sb.append("<div class='col-lg-6 col-md-6 col-sm-6 col-12 form_group'>");
                                                        sb.append("<label class='form_label'>Practical Competency Result</label>");
                                                        sb.append("<select class='form-select' name='resultId2' id='resultId2'>");
                                                         sb.append("<option value='-1'>Select Result</option>");
                                                            if (coll.size() > 0) 
                                                            {
                                                                Iterator iter = coll.iterator();
                                                                while (iter.hasNext()) 
                                                                {
                                                                    info = (TrackerInfo) iter.next();
                                                                    if(resultId2 == info.getDdlValue())
                                                                        sb.append("<option value='" + info.getDdlValue() + "' selected>" + (info.getDdlLabel() != null ? info.getDdlLabel() : "") + "</option>");
                                                                    else
                                                                        sb.append("<option value='" + info.getDdlValue() + "'>" + (info.getDdlLabel() != null ? info.getDdlLabel() : "") + "</option>");
                                                                }
                                                            }                                                        
                                                            sb.append("</select>");
                                                    sb.append("</div>");

                                                    sb.append("<div class='col-lg-12 col-md-12 col-sm-12 col-12 form_group'>");
                                                         sb.append("<label class='form_label'>Remarks</label>");
                                                        sb.append("<textarea name='practicalremarks' class='form-control' rows='3' maxlength='1000'>"+(!remarks2.equals("") ? remarks2 : "")+"</textarea>");
                                                    sb.append("</div>");

                                                sb.append("</div>");

                                                sb.append("<div class='row'>");
                                                    sb.append("<div class='col-lg-6 col-md-6 col-sm-6 col-12 form_group'>");
                                                        sb.append("<label class='form_label'>Total Competency Score (out of 100)</label>");
                                                        sb.append("<input type='text' name='averagescore'  value="+average+" id='averagescore onkeypress='return allowPositiveNumber(event);' class='form-control' placeholder='Enter Score' maxlength='3'/>");
                                                    sb.append("</div>");
                                                sb.append("</div>");
                                            sb.append("</div>");
                                            sb.append("<div class='col-lg-4 col-md-4 col-sm-12 col-12'>");
                                                sb.append("<div class='row'>");
                                                    sb.append("<div class='col-lg-12 col-md-12 col-sm-12 col-12 form_group'>");
                                                        sb.append("<label class='form_label'>Complete By (Knowledge Assessment)</label>");
                                                        sb.append("<span class='form-control'>"+(!completeddate.equals("") ? completeddate: "&nbsp;")+"</span>");
                                                    sb.append("</div>");
                                                    sb.append("<div class='col-lg-12 col-md-12 col-sm-12 col-12 form_group'>");
                                                        sb.append("<label class='form_label'>Complete By (Practical Assessment)</label>");
                                                        sb.append("<span class='form-control'>"+(!completeddate2.equals("") ? completeddate2: "&nbsp;")+"</span>");
                                                    sb.append("</div>");
                                                    sb.append("<div class='col-lg-12 col-md-12 col-sm-12 col-12 form_group'>");
                                                        sb.append("<label class='form_label'>Competency Assessor</label>");
                                                        sb.append("<span class='form-control'>"+(!username.equals("") ? username: "&nbsp;")+"</span>");
                                                    sb.append("</div>");
                                                sb.append("</div>");
                                            sb.append("</div>");                                            
                                         
                                        sb.append("</div>");
                                    sb.append("</div>");
                                sb.append("</div>");
                                if((addper.equals("Y") || editper.equals("Y")) && (cassessor ==1 && status == 2) || (cassessor <= 0))
                                {
                                    if(typefrom ==1)
                                    {
                                        sb.append("<div class='row sticky_div ' id='updatetrack'>");
                                            sb.append("<div class='col-lg-12 col-md-12 col-sm-12 col-12 text-right'><a href=\"javascript: SubmitTrackForm("+trackerId+");\" class='save_page com_track_verify'><img src='../assets/images/save.png'/> Save</a></div>");
                                        sb.append("</div>");
                                    }
                                }
                            sb.append("</div>");
                        sb.append("</div>");
                }
                else if(status == 3 || status == 4 || status == 5 || status == 6)
                {
                       sb.append("<div class='row'>");
                       sb.append("<input type='hidden' name='trackerId' value='"+trackerId+"' />");   
                       sb.append("<input type='hidden' name='statusModal' value='"+status+"' />");
                       sb.append("<input type='hidden' name='username1Hiden' value='"+loginname1+"' />");
                       sb.append("<input type='hidden' name='username2Hiden' value='"+loginname2+"' />");
                       sb.append("<input type='hidden' name='trackerFilehidden' value='"+file1+"' />");
                       sb.append("<input type='hidden' name='trackerFilehidden2' value='"+file2+"' />");
                       sb.append("<input type='hidden' name='typefrom' value='"+typefrom+"' />"); 
                       sb.append("<input type='hidden' name='month' value='"+month+"' />"); 
                       sb.append("<div class='row'>");
                       sb.append("<div class='col-lg-12'>");
                           sb.append("<div class='row'>");
                               sb.append("<div class='col'>");
                                   sb.append("<h2>"+name+" | "+positionName+"</h2>");
                               sb.append("</div>");
                               sb.append("<div class='col col-lg-5 hist_prog_unass text-right'>");
                                   sb.append("<ul>");
                                       sb.append("<li class='role_com_view'><a href='javascript:;' data-bs-toggle='modal' data-bs-target='#history_modal' onclick=\"javascript: gethistory('"+trackerId+"');\"><img src='../assets/images/reload-time-blue.png' />View History</a></li>");
                                       sb.append("<li class='"+tracker.getStColourModal(status)+"'>"+tracker.getStatus(status)+"</li>");
                                   sb.append("</ul>");
                               sb.append("</div>");
                           sb.append("</div>");


                           sb.append("<div class='row client_position_table1'>");
                               sb.append("<div class='col-md-12 mt_15 mb_20'>");
                                   sb.append("<div class='row d-flex align-items-end'>");
                                       sb.append("<div class='col-md-6 com_label_value'>");
                                           sb.append("<div class='row mb_0'>");
                                               sb.append("<div class='col-md-3 pd_right_0'><label>Role Competency</label></div>");
                                               sb.append("<div class='col-md-9'><span>"+role+"</span></div>");
                                           sb.append("</div>");
                                           sb.append("<div class='row mb_0'>");
                                               sb.append("<div class='col-md-3 pd_right_0'><label>Assessment Type</label></div>");
                                               sb.append("<div class='col-md-9'><span>"+assessment+"</span></div>");
                                           sb.append("</div>");
                                       sb.append("</div>");
                                       sb.append("<div class='col-md-4'>");
                                           sb.append("<div class='row'>");
                                               sb.append("<div class='col-md-6 com_label_value'>");
                                                   sb.append("<div class='row'>");
                                                       sb.append("<div class='col-md-7 text-right'><label>Priority</label></div>");
                                                       sb.append("<div class='col-md-5 pd_0'><span>"+priority+"</span></div>");	
                                                   sb.append("</div>");
                                               sb.append("</div>");
                                               sb.append("<div class='col-md-6 com_label_value'>");
                                                   sb.append("<div class='row'>");
                                                       sb.append("<div class='col-md-5 text-right'><label>Validity</label></div>");
                                                       sb.append("<div class='col-md-7 pd_0'><span>"+month+" Months</span></div>");	
                                                   sb.append("</div>");
                                               sb.append("</div>");
                                           sb.append("</div>");	
                                       sb.append("</div>");
                                       sb.append("<div class='col-md-2'>");
                                           sb.append("<div class='ref_vie_ope'>");
                                               sb.append("<ul>");
                                                   sb.append("<li class='com_view_job'><a href=\"javascript: viewCompetency();\"><img src='../assets/images/view.png'><br> View Competency</a></li>");
                                               sb.append("</ul>");
                                           sb.append("</div>");
                                       sb.append("</div>");
                                   sb.append("</div>");
                               sb.append("</div>");

                               sb.append("<div class='col-md-12'>");
                                   sb.append("<div class='row'>");
                                       sb.append("<div class='col-lg-8 col-md-8 col-sm-8 col-12'>");
                                           sb.append("<div class='row'>");
                                               sb.append("<div class='col-lg-12 col-md-12 col-sm-12 col-12 form_group'>");
                                                   sb.append("<label class='form_label'>Description</label>");
                                                   sb.append("<span class='form-control'>"+(!description.equals("") ? description : "&nbsp;") +"</span>");
                                               sb.append("</div>");
                                               sb.append("<div class='col-lg-12 col-md-12 col-sm-12 col-12 form_group'>");
                                                   sb.append("<label class='form_label'>Knowledge Competency Sheet</label>");
                                                   sb.append("<div class='row d-flex align-items-center'>");
                                                      sb.append("<div class='col-md-3'>");
                                                           sb.append("<input type='file' name='trackerFile' id='upload2' onchange=\"javascript: setClass('2');\">");
                                                       if(file1.equals(""))
                                                       {
                                                           sb.append("<a href=\"javascript:;\" id='upload_link_2' class='attache_btn attache_btn_white uploaded_img1' >");
                                                           sb.append("<i class='fas fa-paperclip'></i> Attach</a>");                                                             
                                                       }
                                                       else
                                                       {
                                                               sb.append("<a class='attache_btn attache_btn_white view_num_record' href=\"javascript:;\" data-bs-toggle='modal' data-bs-target='#view_pdf' onclick=\"javascript: setIframe('"+view_path+file1+"');\"><img src='../assets/images/view.png'/> View <span class='attach_number'></span></a>");
                                                       }
                                                       sb.append("</div>");   
                                                       if(onlineflag ==2)
                                                        {
                                                            sb.append("<div class='col-md-3 pd_0'>");
                                                                    sb.append("<a href='javascript:;' data-bs-toggle='modal' data-bs-target='#online_submission_modal' class='attache_btn attache_btn_white view_num_record' onclick=\"javascript: getquestion('"+trackerId+"');\"><img src='../assets/images/view.png'/> Online Submission</a>");
                                                            sb.append("</div>");
                                                        }
                                                       if(!loginname1.equals(""))
                                                       {
                                                       sb.append("<div class='col-md-6 veri_details'>");
                                                           sb.append("<ul>");
                                                               sb.append("<li>"+(!loginname1.equals("") ? loginname1: "&nbsp;")+"</li>");
                                                               sb.append("<li>|</li>");
                                                               sb.append("<li>"+(!submitdate1.equals("") ? submitdate1 : "&nbsp;")+"</li>");
                                                           sb.append("</ul>");
                                                       sb.append("</div>");
                                                       }
                                                   sb.append("</div>");
                                               sb.append("</div>");
                                           sb.append("</div>");
                                           
                                           sb.append("<div class='row'>");

                                               sb.append("<div class='col-lg-6 col-md-6 col-sm-6 col-12 form_group'>");
                                                   sb.append("<label class='form_label'>Knowledge Competency Score (out of 100)</label>");
                                                   sb.append("<div class='col-md-12'><span class='form-control'>"+(score >0 ? score : "&nbsp;")+"</span></div>");
                                                  sb.append("</div>");

                                               sb.append("<div class='col-lg-6 col-md-6 col-sm-6 col-12 form_group'>");
                                                    sb.append("<label class='form_label'>Knowledge Competency Result</label>");
                                                    sb.append("<div class='col-md-12'><span class='form-control'>"+(!resultname1.equals("") ? resultname1 : "&nbsp;")+"</span></div>");
                                               sb.append("</div>");

                                               sb.append("<div class='col-lg-12 col-md-12 col-sm-12 col-12 form_group'>");
                                                   sb.append("<label class='form_label'>Remarks</label>");
                                                    sb.append("<div class='col-md-12'><span class='form-control'>"+(!remarks.equals("") ? remarks : "&nbsp;")+"</span></div>");
                                               sb.append("</div>");

                                           sb.append("</div>");

                                           sb.append("<div class='row'>");
                                           
                                               sb.append("<div class='col-lg-12 col-md-12 col-sm-12 col-12 form_group'>");
                                                   sb.append("<label class='form_label'>Practical Competency Documents</label>");
                                                   sb.append("<div class='row d-flex align-items-center'>");
                                                   
                                                       sb.append("<div class='col-md-3'>");
                                                           sb.append("<input type='file' name='trackerFile2' id='upload3' onchange=\"javascript: setClass('3');\">");
                                                           if(file2.equals(""))
                                                           {
                                                               sb.append("<a href=\"javascript:;\" id='upload_link_3' class='attache_btn attache_btn_white uploaded_img1' >");
                                                               sb.append("<i class='fas fa-paperclip'></i> Attach</a>");
                                                           }
                                                           else
                                                           {
                                                              sb.append("<a class='attache_btn attache_btn_white view_num_record' href=\"javascript:;\" data-bs-toggle='modal' data-bs-target='#view_pdf' onclick=\"javascript: setIframe('"+view_path+file2+"');\"><img src='../assets/images/view.png'/> View <span class='attach_number'></span></a>");
                                                           }
                                                       sb.append("</div>");
                                                       
                                                       sb.append("<div class='col-md-6 veri_details'>");
                                                           sb.append("<ul>");
                                                               sb.append("<li>"+(!loginname2.equals("") ? loginname2: "&nbsp;")+"</li>");
                                                               sb.append("<li>|</li>");
                                                               sb.append("<li>"+(!submitdate2.equals("") ? submitdate2 : "&nbsp;")+"</li>");
                                                           sb.append("</ul>");
                                                       sb.append("</div>");
                                                       
                                                   sb.append("</div>");
                                               sb.append("</div>");

                                               sb.append("<div class='col-lg-6 col-md-6 col-sm-6 col-12 form_group'>");
                                                    sb.append("<label class='form_label'>Practical Competency Score (out of 100)</label>");
                                                    sb.append("<div class='col-md-12'><span class='form-control'>"+(score2 >0 ? score2 : "&nbsp;")+"</span></div>");
                                               sb.append("</div>");

                                               sb.append("<div class='col-lg-6 col-md-6 col-sm-6 col-12 form_group'>");
                                                   sb.append("<label class='form_label'>Practical Competency Result</label>");
                                                   sb.append("<div class='col-md-12'><span class='form-control'>"+(!resultname2.equals("") ? resultname2 : "&nbsp;")+"</span></div>");
                                               sb.append("</div>");

                                               sb.append("<div class='col-lg-12 col-md-12 col-sm-12 col-12 form_group'>");
                                                    sb.append("<label class='form_label'>Remarks</label>");
                                                    sb.append("<div class='col-md-12'><span class='form-control'>"+(!remarks2.equals("") ? remarks2 : "&nbsp;")+"</span></div>");
                                               sb.append("</div>");

                                           sb.append("</div>");

                                           sb.append("<div class='row'>");
                                               sb.append("<div class='col-lg-6 col-md-6 col-sm-6 col-12 form_group'>");
                                                   sb.append("<label class='form_label'>Total Competency Score (out of 100)</label>");
                                                   sb.append("<div class='col-md-12'><span class='form-control'>"+(average >0 ? average : "&nbsp;")+"</span></div>");
                                               sb.append("</div>");
                                           sb.append("</div>");
                                       sb.append("</div>");

                                       sb.append("<div class='col-lg-4 col-md-4 col-sm-12 col-12'>");
                                           sb.append("<div class='row'>");
                                               sb.append("<div class='col-lg-12 col-md-12 col-sm-12 col-12 form_group'>");
                                                   sb.append("<label class='form_label'>Complete By (Knowledge Assessment)</label>");
                                                   sb.append("<span class='form-control'>"+(!completeddate.equals("") ? completeddate : "&nbsp;")+"</span>");
                                               sb.append("</div>");
                                               sb.append("<div class='col-lg-12 col-md-12 col-sm-12 col-12 form_group'>");
                                                   sb.append("<label class='form_label'>Complete By (Practical Assessment)</label>");
                                                   sb.append("<span class='form-control'>"+(!completeddate2.equals("") ? completeddate2: "&nbsp;")+"</span>");
                                               sb.append("</div>");
                                                    sb.append("<div class='col-lg-12 col-md-12 col-sm-12 col-12 form_group'>");
                                                            sb.append("<label class='form_label'>Competency Assessor</label>");
                                                            sb.append("<div class='row flex-end align-items-center'>");
                                                                    sb.append("<div class='col-lg-9'><span class='form-control'>"+(!username.equals("") ? username : "&nbsp;")+"</span></div>");
                                                                       
                                                                    sb.append("<div class='col-lg-3 pd_0'>");
                                                                            sb.append("<div class='ref_vie_ope'>");
                                                                                    sb.append("<ul>");
                                                                                     if(!cvfile.equals(""))
                                                                                            sb.append("<li class='com_view_job'><a href=\"javascript:;\"><a href=\"javascript:;\" data-bs-toggle='modal' data-bs-target='#view_pdf' onclick=\"javascript: setIframe('"+view_cvfile+cvfile+"');\"><img src='../assets/images/view.png'><br> View Profile</a></li>");
                                                                                    sb.append("</ul>");
                                                                            sb.append("</div>");	
                                                                    sb.append("</div>");
                                                                    
                                                            sb.append("</div>");
                                                    sb.append("</div>");
                                            sb.append("</div>");
                                            if(status == 4)
                                            {
                                             sb.append("<div class='row select_outcome'>");
                                                        sb.append("<div class='col-md-12 form_group'>");
                                                                sb.append("<label class='form_label'>Select Outcome</label>");
                                                                sb.append("<div class='col-md-12'><span class='form-control'>"+(!(tracker.getOutcomeName(outcomeId).equals("") )? tracker.getOutcomeName(outcomeId) : "&nbsp;")+"</span></div>");                                                                        
                                                        sb.append("</div>");
                                                        if(outcomeId == 2)
                                                        {
                                                            sb.append("<div class='col-lg-12 col-md-12 col-sm-12 col-4 form_group'>");
                                                                    sb.append("<label class='form_label'>Suggest Trainings</label>");
                                                                    sb.append("<div class='col-md-12'><span class='form-control'>"+(!courseName.equals("") ? courseName: "&nbsp;")+"</span></div>");
                                                            sb.append("</div>");
                                                        }
                                                        sb.append("<div class='col-lg-12 col-md-12 col-sm-12 col-4 form_group'>");
                                                                sb.append("<label class='form_label'>Enter Remarks</label>");
                                                                sb.append("<div class='col-md-12'><span class='form-control'>"+(!outcomeremarks.equals("") ? outcomeremarks: "&nbsp;")+"</span></div>");                                                                        
                                                        sb.append("</div>");
                                            }
                                            if(status == 3 && cassessor <= 0 && typefrom ==1)
                                            {
                                                    sb.append("<div class='row select_outcome'>");
                                                        sb.append("<div class='col-md-12 form_group'>");
                                                                sb.append("<label class='form_label'>Select Outcome</label>");
                                                                sb.append("<select name ='outcomeId' id ='outcomeId' class='form-select form-control' onchange = \"javascript: showhide();\">");
                                                                        sb.append("<option value='-1'>Select</option>");
                                                                        sb.append("<option value='1'>Competent</option>");
                                                                        sb.append("<option value='2'>Not Yet Competent</option>");
                                                                sb.append("</select>");
                                                        sb.append("</div>");

                                                        sb.append("<div class='col-lg-12 col-md-12 col-sm-12 col-4 form_group' id='trId' style='display: none;'>");
                                                                sb.append("<label class='form_label'>Suggest Trainings</label>");
                                                                sb.append("<select class='form-select' name='trainingId' id='trainingId'>");
                                                                 sb.append("<option value='-1'>Select Training</option>");
                                                                if (training != null && training.size() > 0 ) 
                                                                {
                                                                    Iterator iter = training.iterator();
                                                                    while (iter.hasNext()) 
                                                                    {
                                                                        info = (TrackerInfo) iter.next();                                                                    
                                                                        sb.append("<option value='" + info.getDdlValue() + "'>" + (info.getDdlLabel() != null ? info.getDdlLabel() : "") + "</option>");
                                                                    }
                                                                }                                                        
                                                               sb.append("</select>");
                                                        sb.append("</div>");
                                                        sb.append("<div class='col-lg-12 col-md-12 col-sm-12 col-4 form_group'>");
                                                                sb.append("<label class='form_label'>Enter Remarks</label>");
                                                                sb.append("<textarea name='outcomeremarks' class='form-control' rows='6' maxlength='1000'></textarea>");
                                                        sb.append("</div>");
                                            }
                                            else if(status == 5)
                                            {
                                                    sb.append("<div class='row select_outcome'>");
                                                    sb.append("<div class='col-md-12 form_group'>");
                                                            sb.append("<label class='form_label'>Select Outcome</label>");
                                                            sb.append("<div class='col-md-12'><span class='form-control'>"+(!(tracker.getOutcomeName(outcomeId).equals("")) ? tracker.getOutcomeName(outcomeId) : "&nbsp;")+"</span></div>");                                                                        
                                                    sb.append("</div>");
                                                    if(outcomeId == 2)
                                                    { 
                                                        sb.append("<div class='col-lg-12 col-md-12 col-sm-12 col-4 form_group' id='trId'>");
                                                                sb.append("<label class='form_label'>Suggest Trainings</label>");
                                                                sb.append("<div class='col-md-12'><span class='form-control'>"+(!courseName.equals("") ? courseName: "&nbsp;")+"</span></div>");
                                                        sb.append("</div>");
                                                    }
                                                    sb.append("<div class='col-lg-12 col-md-12 col-sm-12 col-4 form_group'>");
                                                            sb.append("<label class='form_label'>Enter Remarks</label>");
                                                            sb.append("<div class='col-md-12'><span class='form-control'>"+(!outcomeremarks.equals("") ? outcomeremarks: "&nbsp;")+"</span></div>");
                                                    sb.append("</div>");
                                            }
                                            else if(status == 6)
                                            {
                                                    sb.append("<div class='row select_outcome'>");
                                                    sb.append("<div class='col-md-12 form_group'>");
                                                            sb.append("<label class='form_label'>Select Outcome</label>");
                                                            sb.append("<div class='col-md-12'><span class='form-control'>"+(!(tracker.getOutcomeName(outcomeId).equals("")) ? tracker.getOutcomeName(outcomeId) : "&nbsp;")+"</span></div>");                                                                        
                                                    sb.append("</div>");
                                                    if(outcomeId == 2)
                                                    { 
                                                        sb.append("<div class='col-lg-12 col-md-12 col-sm-12 col-4 form_group' id='trId'>");
                                                                sb.append("<label class='form_label'>Suggest Trainings</label>");
                                                                sb.append("<div class='col-md-12'><span class='form-control'>"+(!courseName.equals("") ? courseName : "&nbsp;")+"</span></div>");
                                                        sb.append("</div>");
                                                    }
                                                    sb.append("<div class='col-lg-12 col-md-12 col-sm-12 col-4 form_group'>");
                                                            sb.append("<label class='form_label'>Enter Remarks</label>");
                                                            sb.append("<div class='col-md-12'><span class='form-control'>"+(!outcomeremarks.equals("") ? outcomeremarks : "&nbsp;")+"</span></div>");
                                                    sb.append("</div>");
                                                
                                                    sb.append("<div class='col-lg-12 col-md-12 col-sm-12 col-12 form_group'>");
                                                            sb.append("<label class='form_label'>Appeal Reason</label>");
                                                            sb.append("<div class='col-md-12'><span class='form-control'>"+(!appeal.equals("") ? appeal : "&nbsp;")+"</span></div>");
                                                    sb.append("</div>");
                                                    sb.append("<div class='col-lg-12 col-md-12 col-sm-12 col-12 form_group'>");
                                                            sb.append("<label class='form_label'>Appeal Statement</label>");
                                                            sb.append("<div class='col-md-12'><span class='form-control'>"+(!appealremarks.equals("") ? appealremarks : "&nbsp;")+"</span></div>");
                                                    sb.append("</div>");
                                                    sb.append("<div class='col-lg-12 col-md-12 col-sm-12 col-12 form_group'>");
                                                            sb.append("<label class='form_label'>Appeal Decision</label>");
                                                             sb.append("<select name ='rejection' id ='rejection' class='form-select form-control' onchange = \"javascript: showhide2();\">");
                                                                    sb.append("<option value=''>Select Approved/Reject</option>");
                                                                    sb.append("<option value='Approved'>Approved</option>");
                                                                    sb.append("<option value='Reject'>Reject</option>");
                                                            sb.append("</select>");
                                                    sb.append("</div>");
                                                    
                                                    sb.append("<div class='col-lg-12 col-md-12 col-sm-12 col-4 form_group' id='reasondiv' style='display: none;'>");
                                                        sb.append("<label class='form_label'>Select Reason</label>");
                                                            sb.append("<select class='form-select' name='reasonId' id='reasonId'>");
                                                             sb.append("<option value='-1'>Select Reason</option>");
                                                                    if (reasons != null && reasons.size() > 0 ) 
                                                                    {

                                                                        Iterator iter = reasons.iterator();
                                                                        while (iter.hasNext()) 
                                                                        {
                                                                            info = (TrackerInfo) iter.next();                                                                    
                                                                            sb.append("<option value='" + info.getDdlValue() + "'>" + (info.getDdlLabel() != null ? info.getDdlLabel() : "") + "</option>");
                                                                        }
                                                                    }                                                        
                                                            sb.append("</select>");
                                                         sb.append("</div>");
                                            }
                                            sb.append("</div>");
                                    sb.append("</div>");

                                   sb.append("</div>");
                               sb.append("</div>");
                           sb.append("</div>");
                        
                        if((addper.equals("y") || editper.equals("Y") && (status != 4)) && ((cassessor ==1 && status == 2) || (cassessor <= 0)))
                         {                           
                               if((typefrom ==1))
                               {
                                   if(status == 5 && noofdays >= 7)
                                   {
                                       sb.append("<div class='row sticky_div ' id='updatetrack'>");
                                        sb.append("<div class='col-lg-12 col-md-12 col-sm-12 col-12 text-right'><a href=\"javascript: SubmitTrackForm('"+trackerId+"');\" class='save_page com_track_verify'><img src='../assets/images/shield.png'/> Reassign</a></div>");
                                        sb.append("</div>");
                                   }
                                   else if(status != 5)
                                   {
                                    sb.append("<div class='row sticky_div ' id='updatetrack'>");
                                        sb.append("<div class='col-lg-12 col-md-12 col-sm-12 col-12 text-right'><a href=\"javascript: SubmitTrackForm('"+trackerId+"');\" class='save_page com_track_verify'><img src='../assets/images/shield.png'/> Verify</a></div>");
                                    sb.append("</div>");
                                   }
                               }                         
                         }
                       sb.append("</div>");
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