<%@page import="com.web.jxp.crewrotation.CrewrotationInfo"%>
<%@page contentType="text/html"%>
<%@page language="java" import="java.util.ArrayList"%>
<%@page pageEncoding="UTF-8"%>
<%@page import="com.web.jxp.user.UserInfo"%>
<jsp:useBean id="crewrotation" class="com.web.jxp.crewrotation.Crewrotation" scope="page"/>
<jsp:useBean id="vobj" class="com.web.jxp.base.Validate" scope="page"/>
<%
    try {
        if (session.getAttribute("LOGININFO") != null) {
            String crewrotationIds = request.getParameter("crewrotationId") != null && !request.getParameter("crewrotationId").equals("") ? vobj.replaceint(request.getParameter("crewrotationId")) : "0";
            String cractivityIds = request.getParameter("cractivityId") != null && !request.getParameter("cractivityId").equals("") ? vobj.replaceint(request.getParameter("cractivityId")) : "0";
            String noofdayss = request.getParameter("noofdays") != null && !request.getParameter("noofdays").equals("") ? vobj.replaceint(request.getParameter("noofdays")) : "0";
            int noofdays = Integer.parseInt(noofdayss);
            int cractivityId = Integer.parseInt(cractivityIds);
            int crewrotationId = Integer.parseInt(crewrotationIds);

            CrewrotationInfo crainfo = crewrotation.getActivitydetailbycractivityId(crewrotationId, cractivityId);
            CrewrotationInfo soninfo = crewrotation.getSingnonoffdetailbysignonId(crewrotationId, crainfo.getSignonId());
            CrewrotationInfo soffinfo = crewrotation.getSingnonoffdetailbysignonId(crewrotationId, crainfo.getSignoffId());
            
            String  position1 = "", position2 = "";
            int positionId1 = 0, positionId2 = 0;
            CrewrotationInfo info = crewrotation.getCrewrotationBycrewrotationId(crewrotationId);
            if(info != null)
            {
               positionId1 = info.getPositionId();
               positionId2 = info.getPositionId2();
               position1 = info.getPosition() != null ? info.getPosition(): "";
               position2 = info.getPosition2() != null ? info.getPosition2(): "";
            }
            
            String sondatefrom = "", sontimefrom = "", sondateto = "", sontimeto = "", signonremarks = "";

            if (soninfo.getSubtype() == 1) {
                if (!soninfo.getFromdate().equals("")) {
                    String d[] = soninfo.getFromdate().split(" ");
                    sondatefrom = d[0];
                    sontimefrom = d[1];
                }
            }
            if (soninfo.getSubtype() == 2 || soninfo.getSubtype() == 3) {
                if (!soninfo.getFromdate().equals("")) {
                    String d[] = soninfo.getFromdate().split(" ");
                    sondatefrom = d[0];
                    sontimefrom = d[1];
                }
                if (!soninfo.getTodate().equals("")) {
                    String d1[] = soninfo.getTodate().split(" ");
                    sondateto = d1[0];
                    sontimeto = d1[1];
                }
            }
            signonremarks = soninfo.getRemarks();

            int onnorota = 0, offnorota = 0;
            if (soninfo.getNorota() > 0) {
                onnorota = soninfo.getNorota();
            }

            String soffdatefrom = "", sofftimefrom = "", soffdateto = "", sofftimeto = "", signoffremarks = "";
            int offsubtype = 0;
            if (soffinfo != null) {
                if (soffinfo.getNorota() > 0) {
                    offnorota = soffinfo.getNorota();
                }

                if (soffinfo.getSubtype() == 1) {
                    if (!soffinfo.getFromdate().equals("")) {
                        String d[] = soffinfo.getFromdate().split(" ");
                        soffdatefrom = d[0];
                        sofftimefrom = d[1];
                    }
                }
                if (soffinfo.getSubtype() == 2 || soffinfo.getSubtype() == 3) {
                    if (!soffinfo.getFromdate().equals("")) {
                        String d[] = soffinfo.getFromdate().split(" ");
                        soffdatefrom = d[0];
                        sofftimefrom = d[1];
                    }
                    if (!soffinfo.getTodate().equals("")) {
                        String d1[] = soffinfo.getTodate().split(" ");
                        soffdateto = d1[0];
                        sofftimeto = d1[1];
                    }
                }

                signoffremarks = soffinfo.getRemarks();
                offsubtype = soffinfo.getSubtype();
            }
            int othervalsignon = 1;
            if (signonremarks.equalsIgnoreCase("Reported helibase not joined") || signonremarks.equalsIgnoreCase("Other medical reasons not joined") || signonremarks.equalsIgnoreCase("Others compensatory off not joined") || signonremarks.equalsIgnoreCase("Chopper Cancellation/Unavailability")) {
                othervalsignon = 0;
            }

            int othervalsignoff = 1;
            if (soffinfo != null) {
                if (signoffremarks.equalsIgnoreCase("Reported helibase not joined") || signoffremarks.equalsIgnoreCase("Other medical reasons not joined") || signoffremarks.equalsIgnoreCase("Others compensatory off not joined") || signonremarks.equalsIgnoreCase("Chopper Cancellation/Unavailability")) {
                    othervalsignoff = 0;
                }
            }
            StringBuilder sb = new StringBuilder();

            sb.append("<div class='row modal_tab_area'>");
            sb.append("<ul class='nav nav-pills nav-justified' role='tablist'>");

            sb.append("<li class='nav-item waves-effect waves-light'>");
            sb.append("<a class='nav-link active' data-bs-toggle='tab' href='#tab_01' role='tab' aria-selected='true'>");
            sb.append("<span class='d-none d-md-block'>SIGN ON DETAILS</span><span class='d-block d-md-none'><i class='mdi mdi-home-variant h5'></i></span>");
            sb.append("</a>");
            sb.append("</li>");

            sb.append("<li class='nav-item waves-effect waves-light'>");
            sb.append("<a class='nav-link' ");
            if (soffinfo != null) {
                sb.append(" data-bs-toggle='tab' ");
            }
            sb.append(" href='#tab_02' role='tab' aria-selected='false'>");
            sb.append("<span class='d-none d-md-block'>SIGN OFF DETAILS</span><span class='d-block d-md-none'><i class='mdi mdi-account h5'></i></span>");
            sb.append("</a>");
            sb.append("</li>");

            sb.append("</ul>");
            sb.append("<div class='tab-content'>");

            sb.append("<div class='tab-pane active' id='tab_01' role='tabpanel'>");
            sb.append("<div class='row client_position_table'>");

            sb.append("<div class='col-md-6 form_group mt_15'>");
            sb.append("<div class='input-daterange input-group'>");
            sb.append("<input type='text' name='edate' value='" + sondatefrom + "' id='startdate' class='form-control add-style wesl_dt date-add text-center' placeholder='DD-MMM-YYYY'>");
            sb.append("</div>");
            sb.append("</div>");

            sb.append("<div class='col-md-6 form_group mt_15'>");
            sb.append("<div class='input-group'>");
            sb.append("<input type='text' name = 'etime' class='form-control timepicker timepicker-24 text-center' value='" + sontimefrom + "'>");
            sb.append("</div>");
            sb.append("</div>");

            String shownorotadata = "style ='display: none'";
            if (onnorota == 2) {
                shownorotadata = "";
                sb.append("<input type='hidden' name='rdateId' id='ondate' value='1' />");
                sb.append("<input type='hidden' name='reasonsDDL' id='reasonsDDL' value='' />");
            } else {
                sb.append("<div class='form-group' id='' style=''>");
                sb.append("<div class='mt-radio-inline'>");
                sb.append("<label class='mt-radio'>");
                sb.append("<input type='radio' name='rdateId' id='ondate' value='1' ");
                if (soninfo.getSubtype() == 1) {
                    sb.append(" checked ");
                }
                sb.append("> Normal");
                sb.append("<span></span>");
                sb.append("</label>");

                sb.append("<label class='mt-radio'>");
                sb.append("<input type='radio' name='rdateId' id='ersignoff' value='2' ");
                if (soninfo.getSubtype() == 2) {
                    sb.append(" checked ");
                }
                sb.append("> Early Sign On");
                sb.append("<span></span>");
                sb.append("</label>");

                sb.append("<label class='mt-radio'>");
                sb.append("<input type='radio' name='rdateId' id='exsignoff' value='3' ");
                if (soninfo.getSubtype() == 3) {
                    sb.append(" checked ");
                }
                sb.append("> Delay Sign On");
                sb.append("<span></span>");
                sb.append("</label>");

                sb.append("</div>");
                sb.append("</div>");

                sb.append("<div class='col-lg-12 col-md-12 col-sm-12 col-4 form_group' style ='display: none' id='reasonDiv'>");
                sb.append("<select styleClass='form-select' name='reasonsDDL' id='reasonsDDL' onchange='javascript: showTextAreasignon();'>");
                sb.append("<option value=''>- Select Reason-</option>");
                sb.append("<option value='Reported helibase not joined' ");
                if (signonremarks.equalsIgnoreCase("Reported helibase not joined")) {
                    sb.append(" selected ");
                }
                sb.append(">Reported helibase not joined</option>");
                sb.append("<option value='Other medical reasons not joined'  ");
                if (signonremarks.equalsIgnoreCase("Other medical reasons not joined")) {
                    sb.append(" selected ");
                }
                sb.append(">Other medical reasons not joined</option>");
                sb.append("<option value='Others compensatory off not joined'  ");
                if (signonremarks.equalsIgnoreCase("Others compensatory off not joined")) {
                    sb.append(" selected ");
                }
                sb.append(">Others compensatory off not joined</option>");  
                
                sb.append("<option value='Chopper Cancellation/Unavailability'  ");
                if (signonremarks.equalsIgnoreCase("Chopper Cancellation/Unavailability")) {
                    sb.append(" selected ");
                }
                sb.append(">Chopper Cancellation/Unavailability</option>");  
                
                sb.append("<option value='Others'  ");
                if (!signonremarks.equalsIgnoreCase("Others compensatory off not joined") && !signonremarks.equalsIgnoreCase("Reported helibase not joined") && !signonremarks.equalsIgnoreCase("Other medical reasons not joined") && !signonremarks.equalsIgnoreCase("Chopper Cancellation/Unavailability") && !signonremarks.equalsIgnoreCase("Others")) {
                    sb.append(" selected ");
                }
                sb.append(">Others</option>");
                sb.append("</select>");
                sb.append("</div>");
            }
            
            sb.append("<div class='col-lg-12 col-md-12 col-sm-12 col-4 form_group' >");
            sb.append("<select class='form-select' name='positionIdEdit' id='positionIdEdit' >");            
            if(soninfo.getPositionId() == positionId1)
            {
                sb.append("<option value='"+positionId1+"' ");
                    sb.append(" selected "); 
                sb.append(" >"+position1+"</option>");
                if(positionId2 > 0)
                    sb.append("<option value='"+positionId2+"' ");
                        sb.append(" select "); 
                    sb.append(" >"+position2+"</option>");
            }
            else if(soninfo.getPositionId() == positionId2)
            {
                sb.append("<option value='"+positionId2+"' ");
                    sb.append(" selected "); 
                sb.append(" >"+position2+"</option>");
                if(positionId1 > 0)
                    sb.append("<option value='"+positionId1+"' ");
                        sb.append(" select "); 
                    sb.append(" >"+position1+"</option>");
            }
                
            sb.append("</select>");
            sb.append("</div>");  
            

            sb.append("<div class='col-lg-12 col-md-12 col-sm-12 col-4 form_group' id='div02_1' " + (shownorotadata) + ">");
            sb.append("<label class='form_label'>Remarks</label>");
            sb.append("<textarea name='siremarks' class='form-control' rows='6'>" + signonremarks + "</textarea>");
            sb.append("</div>");

            sb.append("<div class='col-lg-12 col-md-12 col-sm-12 col-4 form_group' style ='display: none' id='reasonDiv1'>");
            sb.append("<select styleClass='form-select' name='reasonsDDL1' id='reasonsDDL1' onchange='javascript: showTextAreasignon();'>");
            sb.append("<option value=''>- Select Reason-</option>");
            sb.append("<option value='Reported helibase not joined' ");
            if (signonremarks.equalsIgnoreCase("Reported helibase not joined")) {
                sb.append(" selected ");
            }
            sb.append(">Reported helibase not joined</option>");
            sb.append("<option value='Other medical reasons not joined'  ");
            if (signonremarks.equalsIgnoreCase("Other medical reasons not joined")) {
                sb.append(" selected ");
            }
            sb.append(">Other medical reasons not joined</option>");
            
            sb.append("<option value='Others compensatory off not joined'  ");
            if (signonremarks.equalsIgnoreCase("Others compensatory off not joined")) {
                sb.append(" selected ");
            }
            sb.append(">Others compensatory off not joined</option>");
            
            sb.append("<option value='Chopper Cancellation/Unavailability'  ");
            if (signonremarks.equalsIgnoreCase("Chopper Cancellation/Unavailability")) {
                sb.append(" selected ");
            }
            sb.append(">Chopper Cancellation/Unavailability</option>");
            
            
            sb.append("<option value='Others'  ");
            if (!signonremarks.equalsIgnoreCase("Others compensatory off not joined") && !signonremarks.equalsIgnoreCase("Reported helibase not joined") && !signonremarks.equalsIgnoreCase("Other medical reasons not joined") && !signonremarks.equalsIgnoreCase("Chopper Cancellation/Unavailability") && !signonremarks.equalsIgnoreCase("Others")) {
                sb.append(" selected ");
            }
            sb.append(">Others</option>");
            sb.append("</select>");
            sb.append("</div>");

            sb.append("<div class='col-md-12' id='div02_2' style='display: none'>");
            sb.append("<div class='row'>");

            sb.append("<div class='col-lg-12 col-md-12 col-sm-12 col-4 form_group' id='div02_11' ");
            if (soninfo.getSubtype() == 2 && othervalsignon == 1) {
                sb.append(" style ='display: ' ");
            } else {
                sb.append(" style ='display: none' ");
            }
            sb.append(">");
            sb.append("<label class='form_label'>Reason</label>");
            sb.append("<textarea name='reasons' class='form-control' rows='6'>" + signonremarks + "</textarea>");
            sb.append("</div>");

            sb.append("<div class='col-md-6 form_group'>");
            sb.append("<label class='form_label'>Suggested Date</label>");
            sb.append("<div class='input-daterange input-group'>");
            sb.append("<input type='text' name='sdate' value='" + sondateto + "' id='startdate' class='form-control add-style wesl_dt date-add text-center' placeholder='DD-MMM-YYYY'>");
            sb.append("</div>");
            sb.append("</div>");

            sb.append("<div class='col-md-6 form_group'>");
            sb.append("<label class='form_label'>Suggested Time</label>");
            sb.append("<div class='input-group'>");
            sb.append("<input type='text' name='stime' class='form-control timepicker timepicker-24 text-center' value='" + sontimeto + "'>");
            sb.append("</div>");
            sb.append("</div>");
            sb.append("</div>");
            sb.append("</div>");

            sb.append("<div class='col-md-12' id='div02_3' style='display: none'>");
            sb.append("<div class='row'>");

            sb.append("<div class='col-lg-12 col-md-12 col-sm-12 col-4 form_group'  id='div02_12' ");
            if (soninfo.getSubtype() == 3 && othervalsignon == 1) {
                sb.append(" style ='display: ' ");
            } else {
                sb.append(" style ='display: none' ");
            }

            sb.append(">");
            sb.append("<label class='form_label'>Reason</label>");
            sb.append("<textarea name='reasons1' class='form-control' rows='6'>" + signonremarks + "</textarea>");
            sb.append("</div>");

            sb.append("<div class='col-md-6 form_group'>");
            sb.append("<label class='form_label'>Suggested Date</label>");
            sb.append("<div class='input-daterange input-group'>");
            sb.append("<input type='text' name='sdate1' value='" + sondateto + "' id='startdate' class='form-control add-style wesl_dt date-add text-center' placeholder='DD-MMM-YYYY'>");
            sb.append("</div>");

            sb.append("</div>");
            sb.append("<div class='col-md-6 form_group'>");

            sb.append("<label class='form_label'>Suggested Time</label>");
            sb.append("<div class='input-group'>");
            sb.append("<input type='text' name='stime1' class='form-control timepicker timepicker-24 text-center' value='" + sontimeto + "'>");
            sb.append("</div>");
            sb.append("</div>");
            sb.append("</div>");

            sb.append("</div>");
            sb.append("</div>");

            sb.append("<div class='row'>");
            sb.append("<div class='col-lg-12 col-md-12 col-sm-12 col-12 text-center' id = 'savediv1signon'>");
            sb.append("<a href=\"javascript: submitsignoneditForm(" + crewrotationId + "," + noofdays + "," + crainfo.getSignonId() + "," + cractivityId + ");\" class='save_page'><img src='../assets/images/save.png'> Save</a>");
            sb.append("</div>");
            sb.append("</div>");

            sb.append("</div>");

            sb.append("<div class='tab-pane' id='tab_02' role='tabpanel'>");
            sb.append("<div class='row client_position_table'>");

            sb.append("<div class='col-md-6 form_group mt_15'>");
            sb.append("<div class='input-daterange input-group'>");
            sb.append("<input type='text' name='edate1' value='" + soffdatefrom + "' id='startdate' class='form-control add-style wesl_dt date-add text-center' placeholder='DD-MMM-YYYY'>");
            sb.append("</div>");
            sb.append("</div>");

            sb.append("<div class='col-md-6 form_group mt_15'>");

            sb.append("<div class='input-group'>");
            sb.append("<input type='text' name='etime1' class='form-control timepicker timepicker-24 text-center' value='" + sofftimefrom + "'>");
            sb.append("</div>");
            sb.append("</div>");

            if (offnorota == 2) {
                sb.append("<input type='hidden' name='rdateid1' value='1' />");
                sb.append("<input type='hidden' name='reasonsDDL2' id='reasonsDDL2' value='' />");
                sb.append("<input type='hidden' name='reasonsDDL3' id='reasonsDDL3' value='' />");
            } else {
                sb.append("<div class='form-group' id='' style=''>");
                sb.append("<div class='mt-radio-inline'>");

                sb.append("<label class='mt-radio'>");
                sb.append("<input type='radio' name='rdateid1' id='ondate1' value='1' ");
                if (offsubtype == 1) {
                    sb.append(" checked  ");
                }
                sb.append("> Normal");
                sb.append("<span></span>");
                sb.append("</label>");

                sb.append("<label class='mt-radio'>");
                sb.append("<input type='radio' name='rdateid1' id='ersignoff1' value='2' ");
                if (offsubtype == 2) {
                    sb.append(" checked  ");
                }
                sb.append("> Early Sign Off");
                sb.append("<span></span>");
                sb.append("</label>");

                sb.append("<label class='mt-radio'>");
                sb.append("<input type='radio' name='rdateid1' id='exsignoff1' value='3'");
                if (offsubtype == 3) {
                    sb.append(" checked  ");
                }
                sb.append("> Extended Sign Off");
                sb.append("<span></span>");
                sb.append("</label>");
                sb.append("</div>");
                sb.append("</div>");

                sb.append("<div class='col-lg-12 col-md-12 col-sm-12 col-4 form_group' style ='display: none' id='reasonDiv2'>");
                sb.append("<select styleClass='form-select' name='reasonsDDL2' id='reasonsDDL2' onchange='javascript: showTextAreasignoff();'>");
                sb.append("<option value=''>- Select Reason-</option>");
                sb.append("<option value='Reported helibase not joined' ");
                if (signoffremarks.equalsIgnoreCase("Reported helibase not joined")) {
                    sb.append(" selected ");
                }
                sb.append(">Reported helibase not joined</option>");
                sb.append("<option value='Other medical reasons not joined'  ");
                if (signoffremarks.equalsIgnoreCase("Other medical reasons not joined")) {
                    sb.append(" selected ");
                }
                sb.append(">Other medical reasons not joined</option>");
                
                sb.append("<option value='Others compensatory off not joined'  ");
                if (signoffremarks.equalsIgnoreCase("Others compensatory off not joined")) {
                    sb.append(" selected ");
                }
                sb.append(">Others compensatory off not joined</option>");
                
                sb.append("<option value='Chopper Cancellation/Unavailability'  ");
                if (signoffremarks.equalsIgnoreCase("Chopper Cancellation/Unavailability")) {
                    sb.append(" selected ");
                }
                sb.append(">Chopper Cancellation/Unavailability</option>");
                
                sb.append("<option value='Others'  ");
                if (!signoffremarks.equalsIgnoreCase("Others compensatory off not joined") && !signoffremarks.equalsIgnoreCase("Reported helibase not joined") && !signoffremarks.equalsIgnoreCase("Other medical reasons not joined") && !signoffremarks.equalsIgnoreCase("Chopper Cancellation/Unavailability") && !signoffremarks.equalsIgnoreCase("Others")) {
                    sb.append(" selected ");
                }
                sb.append(">Others</option>");
                sb.append("</select>");
                sb.append("</div>");

                sb.append("<div class='col-lg-12 col-md-12 col-sm-12 col-4 form_group' style ='display: none' id='reasonDiv3'>");
                sb.append("<select styleClass='form-select' name='reasonsDDL3' id='reasonsDDL3' onchange='javascript: showTextAreasignoff();'>");
                sb.append("<option value=''>- Select Reason-</option>");
                sb.append("<option value='Reported helibase not joined' ");
                if (signoffremarks.equalsIgnoreCase("Reported helibase not joined")) {
                    sb.append(" selected ");
                }
                sb.append(">Reported helibase not joined</option>");
                sb.append("<option value='Other medical reasons not joined'  ");
                if (signoffremarks.equalsIgnoreCase("Other medical reasons not joined")) {
                    sb.append(" selected ");
                }
                sb.append(">Other medical reasons not joined</option>");
                
                sb.append("<option value='Others compensatory off not joined'  ");
                if (signoffremarks.equalsIgnoreCase("Others compensatory off not joined")) {
                    sb.append(" selected ");
                }
                sb.append(">Others compensatory off not joined</option>");
                
                sb.append("<option value='Chopper Cancellation/Unavailability'  ");
                if (signoffremarks.equalsIgnoreCase("Chopper Cancellation/Unavailability")) {
                    sb.append(" selected ");
                }
                sb.append(">Chopper Cancellation/Unavailability</option>");
                
                sb.append("<option value='Others'  ");
                if (!signoffremarks.equalsIgnoreCase("Others compensatory off not joined") && !signoffremarks.equalsIgnoreCase("Reported helibase not joined") && !signoffremarks.equalsIgnoreCase("Other medical reasons not joined") && !signoffremarks.equalsIgnoreCase("Chopper Cancellation/Unavailability") && !signoffremarks.equalsIgnoreCase("Others")) {
                    sb.append(" selected ");
                }
                sb.append(">Others</option>");
                sb.append("</select>");
                sb.append("</div>");
            }

            sb.append("<div class='col-lg-12 col-md-12 col-sm-12 col-4 form_group' id='div02_01'>");
            sb.append("<label class='form_label'>Remarks</label>");
            sb.append("<textarea name = 'remarks1' class='form-control' rows='6'>" + signoffremarks + "</textarea>");
            sb.append("</div>");

            sb.append("<div class='col-md-12' id='div02_02' style='display: none'>");
            sb.append("<div class='row'>");

            sb.append("<div class='col-lg-12 col-md-12 col-sm-12 col-4 form_group' id='div02_011' ");
            if (soffinfo != null && soffinfo.getSubtype() == 2 && othervalsignoff == 1) {
                sb.append(" style ='display: ' ");
            } else {
                sb.append(" style ='display: none' ");
            }
            sb.append(">");
            sb.append("<label class='form_label'>Reason</label>");
            sb.append("<textarea name = 'reasons2' class='form-control' rows='6'>" + signoffremarks + "</textarea>");
            sb.append("</div>");

            sb.append("<div class='col-md-6 form_group'>");

            sb.append("<label class='form_label'>Suggested Date</label>");
            sb.append("<div class='input-daterange input-group'>");
            sb.append("<input type='text' name='sdate2' value='" + soffdateto + "' id='startdate' class='form-control add-style wesl_dt date-add text-center' placeholder='DD-MMM-YYYY'>");
            sb.append("</div>");

            sb.append("</div>");
            sb.append("<div class='col-md-6 form_group'>");

            sb.append("<label class='form_label'>Suggested Time</label>");
            sb.append("<div class='input-group'>");
            sb.append("<input type='text' name = 'stime2' class='form-control timepicker timepicker-24 text-center' value='" + sofftimeto + "'>");
            sb.append("</div>");

            sb.append("</div>");
            sb.append("</div>");
            sb.append("</div>");

            sb.append("<div class='col-md-12' id='div02_03' style='display: none'>");
            sb.append("<div class='row'>");

            sb.append("<div class='col-lg-12 col-md-12 col-sm-12 col-4 form_group' id='div02_012' ");
            if (soffinfo != null && soffinfo.getSubtype() == 3 && othervalsignoff == 1) {
                sb.append(" style ='display: ' ");
            } else {
                sb.append(" style ='display: none' ");
            }
            sb.append(" >");
            sb.append("<label class='form_label'>Reason</label>");
            sb.append("<textarea name = 'reasons3' class='form-control' rows='6'>" + signoffremarks + "</textarea>");
            sb.append("</div>");

            sb.append("<div class='col-md-6 form_group'>");

            sb.append("<label class='form_label'>Suggested Date</label>");
            sb.append("<div class='input-daterange input-group'>");
            sb.append("<input type='text' name='sdate3' value='" + soffdateto + "' id='startdate' class='form-control add-style wesl_dt date-add text-center' placeholder='DD-MMM-YYYY'>");
            sb.append("</div>");

            sb.append("</div>");
            sb.append("<div class='col-md-6 form_group'>");

            sb.append("<label class='form_label'>Suggested Time</label>");
            sb.append("<div class='input-group'>");
            sb.append("<input type='text' name = 'stime3' class='form-control timepicker timepicker-24 text-center' value='" + sofftimeto + "'>");
            sb.append("</div>");
            sb.append("</div>");

            sb.append("</div>");
            sb.append("</div>");
            sb.append("</div>");

            sb.append("<div class='row'>");
            sb.append("<div class='col-lg-12 col-md-12 col-sm-12 col-12 text-center' id = 'savediv1signoff'>");
            sb.append("<a href=\"javascript: submitsignoffeditForm(" + crewrotationId + "," + noofdays + "," + crainfo.getSignoffId() + "," + cractivityId + ");\" class='save_page'><img src='../assets/images/save.png'> Save</a>");
            sb.append("</div>");
            sb.append("</div>");

            sb.append("</div>");
            sb.append("</div>");
            sb.append("</div>");

            String st1 = sb.toString() + "#@#" + "";
            sb.setLength(0);
            response.getWriter().write(st1);
        } else {
            response.getWriter().write("Please check your login session....");
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
%>