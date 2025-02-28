<%@page import="com.web.jxp.crewrotation.CrewrotationInfo"%>
<%@page contentType="text/html"%>
<%@page language="java" import="java.util.ArrayList"%>
<%@page pageEncoding="UTF-8"%>
<%@page import="com.web.jxp.user.UserInfo"%>
<jsp:useBean id="crewrotation" class="com.web.jxp.crewrotation.Crewrotation" scope="page"/>
<jsp:useBean id="vobj" class="com.web.jxp.base.Validate" scope="page"/>
<%
    try 
    {
        if (session.getAttribute("LOGININFO") != null)
        {
            UserInfo uinfo = ((UserInfo) request.getSession().getAttribute("LOGININFO"));
            String editper = "N";
            if (uinfo != null) 
            {
                editper = uinfo.getEditper() != null ? uinfo.getEditper() : "N";
            }
            String crewrotationIds = request.getParameter("crewrotationId") != null && !request.getParameter("crewrotationId").equals("") ? vobj.replaceint(request.getParameter("crewrotationId")) : "0";
            String cractivityIds = request.getParameter("cractivityId") != null && !request.getParameter("cractivityId").equals("") ? vobj.replaceint(request.getParameter("cractivityId")) : "0";
            String noofdayss = request.getParameter("noofdays") != null && !request.getParameter("noofdays").equals("") ? vobj.replaceint(request.getParameter("noofdays")) : "0";
            int cractivityId = Integer.parseInt(cractivityIds);
            int crewrotationId = Integer.parseInt(crewrotationIds);
            int noofdays = Integer.parseInt(noofdayss);           
            
            CrewrotationInfo crainfo = crewrotation.getActivitydetailbycractivityId(crewrotationId, cractivityId);
            CrewrotationInfo soninfo = crewrotation.getSingnonoffdetailbysignonId( crewrotationId, crainfo.getSignonId());
            CrewrotationInfo soffinfo = crewrotation.getSingnonoffdetailbysignonId( crewrotationId, crainfo.getSignoffId());
            
            String position1 = "", position2 = "";
            int positionId1 = 0, positionId2 = 0;

            CrewrotationInfo info = crewrotation.getCrewrotationBycrewrotationId(crewrotationId);
            if(info != null)
            {
               positionId1 = info.getPositionId();
               positionId2 = info.getPositionId2();
               position1 = info.getPosition() != null ? info.getPosition(): "";
               position2 = info.getPosition2() != null ? info.getPosition2(): "";
            }
            
            String sondate = "", sontime = "", onremarks = "";
            String ronlabel = "", ronremarklabel = "";
            if(soninfo != null){
            if(soninfo.getSubtype()==1){
            if(!soninfo.getFromdate().equals(""))
            {
                String d[] = soninfo.getFromdate().split(" ");
                sondate = d[0];
                sontime = d[1];
            }
              }
            if(soninfo.getSubtype()==2 || soninfo.getSubtype()==3){
            if(!soninfo.getTodate().equals(""))
            {
                String d[] = soninfo.getTodate().split(" ");
                sondate = d[0];
                sontime = d[1];
            }
             }
            
            if(soninfo.getSubtype()==1)
            {
                ronlabel = "Normal";
                ronremarklabel = "Remarks";
            } else if(soninfo.getSubtype()==2)
            {
                ronlabel = "Early Sign On";
                ronremarklabel = "Reasons";
            } else if(soninfo.getSubtype()==3)
            {
                ronlabel = "Delay Sign On";
                ronremarklabel = "Reasons";
            }
            
            onremarks = !soninfo.getRemarks().equals("") ? soninfo.getRemarks() : "no remarks";
            }
            String soffdate = "", sofftime = "";
            String rofflabel = "" ,offremarks = "", roffremarklabel = "";
            if(soffinfo != null){
            if(soffinfo.getSubtype()==1)
            {
                rofflabel = "Normal";
                roffremarklabel = "Remarks";
            } else if(soffinfo.getSubtype()==2)
            {
                rofflabel = "Early Sign Off";
                roffremarklabel = "Reasons";
            } else if(soffinfo.getSubtype()==3)
            {
                rofflabel = "Extended Sign Off";
                roffremarklabel = "Reasons";
            } 

            if(soffinfo.getSubtype()==1){
            if(!soffinfo.getFromdate().equals(""))
            {
                String d[] = soffinfo.getFromdate().split(" ");
                soffdate = d[0];
                sofftime = d[1];
            }
            }
            if(soffinfo.getSubtype()==2 || soffinfo.getSubtype()==3){
            if(!soffinfo.getTodate().equals(""))
            {
                String d[] = soffinfo.getTodate().split(" ");
                soffdate = d[0];
                sofftime = d[1];
            }
            }
            offremarks = !soffinfo.getRemarks().equals("") ? soffinfo.getRemarks() : "no remarks";
            }            
            
            int othervalsignon = 1;
            if(onremarks.equalsIgnoreCase("Reported helibase not joined") || onremarks.equalsIgnoreCase("Other medical reasons not joined") || onremarks.equalsIgnoreCase("Others compensatory off not joined") || onremarks.equalsIgnoreCase("Chopper Cancellation/Unavailability") )
                othervalsignon = 0;
            
            int othervalsignoff = 1;
            if(offremarks.equalsIgnoreCase("Reported helibase not joined") || offremarks.equalsIgnoreCase("Other medical reasons not joined") || offremarks.equalsIgnoreCase("Others compensatory off not joined") || onremarks.equalsIgnoreCase("Chopper Cancellation/Unavailability"))
                othervalsignoff = 0;
            
            String otherremarks1 = "", otherremarks2 = "";
            if(othervalsignon == 1)
            {
                otherremarks1 = onremarks;
                onremarks = "Others";
            }
            if(othervalsignoff == 1)
            {
                otherremarks2 = offremarks;
                offremarks = "Others";
            }            
            StringBuilder sb = new StringBuilder();
            
            sb.append("<div class='row client_position_table modal_tab_area'>");
            sb.append("<ul class='nav nav-pills nav-justified' role='tablist'>");
            sb.append("<li class='nav-item waves-effect waves-light'>");
            sb.append("<a class='nav-link active' ");
            if(soninfo != null){
            sb.append(" data-bs-toggle='tab' ");
            }
            sb.append(" href='#home-1' role='tab' aria-selected='true'>");
            sb.append("<span class='d-none d-md-block'>SIGN ON DETAILS</span><span class='d-block d-md-none'><i class='mdi mdi-home-variant h5'></i></span>");
            sb.append("</a>");
            sb.append("</li>");
            
            sb.append("<li class='nav-item waves-effect waves-light'>");
            sb.append("<a class='nav-link' ");
            if(soffinfo != null){
            sb.append(" data-bs-toggle='tab' ");
            }
            sb.append(" href='#profile-1' role='tab' aria-selected='false'>");
            sb.append("<span class='d-none d-md-block'>SIGN OFF DETAILS</span><span class='d-block d-md-none'><i class='mdi mdi-account h5'></i></span>");
            sb.append("</a>");
            sb.append("</li>");
            
            sb.append("</ul>");
            
            sb.append("<div class='tab-content'>");
            sb.append("<div class='tab-pane active' id='home-1' role='tabpanel'>");
            sb.append("<div class='row'>");
            sb.append("<div class='col-md-12 form_group mt_15'>");
            sb.append("<div class='form-group' id=''>");
            sb.append("<div class='mt-radio-inline'>");
            sb.append("<label class='mt-radio'>");
            sb.append("<input type='radio' name='optionsRadios' id='optionsRadios4' value='option1' checked='' readonly>");
            sb.append(ronlabel);
            sb.append("<span></span>");
            sb.append("</label>");
            sb.append("</div>");
            sb.append("</div>");
            sb.append("</div>");
            
            if(soninfo.getPositionId() == positionId1)
            {
                sb.append("<div class='col-lg-12 col-md-12 col-sm-12 col-4 form_group'>");
                    sb.append("<label class='form_label'>Position</label>");
                    sb.append("<span class='form-control'>"+position1+"</span>");
                sb.append("</div>");
            }
            if(soninfo.getPositionId() == positionId2)
            {
                sb.append("<div class='col-lg-12 col-md-12 col-sm-12 col-4 form_group'>");
                    sb.append("<label class='form_label'>Position</label>");
                    sb.append("<span class='form-control'>"+position2+"</span>");
                sb.append("</div>");
            }
            
            sb.append("<div class='col-lg-12 col-md-12 col-sm-12 col-4 form_group'>");
            sb.append("<label class='form_label'>"+ronremarklabel+"</label>");
            sb.append("<span class='form-control'>");
            sb.append(onremarks);
            sb.append("</span>");
            sb.append("</div>");
            
            if(othervalsignon == 1)
            {
                sb.append("<div class='col-lg-12 col-md-12 col-sm-12 col-4 form_group'>");
                sb.append("<label class='form_label'>"+onremarks+"</label>");
                sb.append("<span class='form-control'>");
                sb.append(otherremarks1);
                sb.append("</span>");
                sb.append("</div>");
            }
            sb.append("<div class='col-lg-12 col-md-12 col-sm-12 col-4'>");
            sb.append("<div class='row'>");
            sb.append("<div class='col-lg-6 col-md-6 col-sm-12 col-12 form_group'>");
            sb.append("<label class='form_label'>Suggested Date</label>");
            sb.append("<span class='form-control text-center'>");
            sb.append(sondate);
            sb.append("</span>");
            sb.append("</div>");
            sb.append("<div class='col-lg-6 col-md-6 col-sm-12 col-12 form_group'>");
            sb.append("<label class='form_label'>Suggested Time</label>");
            sb.append("<span class='form-control text-center'>");
            sb.append(sontime);
            sb.append("</span>");
            sb.append("</div>");
            sb.append("</div>");
            sb.append("</div>");
            sb.append("</div>");
            sb.append("</div>");
            
            sb.append("<div class='tab-pane' id='profile-1' role='tabpanel'>");
            sb.append("<div class='row'>");
            sb.append("<div class='col-md-12 form_group mt_15'>");
            sb.append("<div class='form-group' id=''>");
            sb.append("<div class='mt-radio-inline'>");
            sb.append("<label class='mt-radio'>");
            sb.append("<input type='radio' name='optionsRadios2' id='optionsRadios4' value='option1' checked='' readonly>");
            sb.append(rofflabel);
            sb.append("<span></span>");
            sb.append("</label>");
            sb.append("</div>");
            sb.append("</div>");
            sb.append("</div>");
            
            sb.append("<div class='col-lg-12 col-md-12 col-sm-12 col-4 form_group'>");
            sb.append("<label class='form_label'>"+roffremarklabel+"</label>");
            sb.append("<span class='form-control'>");
            sb.append(offremarks);                                                
            sb.append("</span>");
            sb.append("</div>");
            
            if(othervalsignoff == 1)
            {
                sb.append("<div class='col-lg-12 col-md-12 col-sm-12 col-4 form_group'>");
                sb.append("<label class='form_label'>"+offremarks+"</label>");
                sb.append("<span class='form-control'>");
                sb.append(otherremarks2);                                                
                sb.append("</span>");
                sb.append("</div>");
            }
            
            sb.append("<div class='col-lg-12 col-md-12 col-sm-12 col-4'>");
            sb.append("<div class='row'>");
            sb.append("<div class='col-lg-6 col-md-6 col-sm-12 col-12 form_group'>");
            sb.append("<label class='form_label'>Suggested Date</label>");
            sb.append("<span class='form-control text-center'>");
            sb.append(soffdate);
            sb.append("</span>");
            sb.append("</div>");
            sb.append("<div class='col-lg-6 col-md-6 col-sm-12 col-12 form_group'>");
            sb.append("<label class='form_label'>Suggested Time</label>");
            sb.append("<span class='form-control text-center'>");
            sb.append(sofftime);
            sb.append("</span>");
            sb.append("</div>");
            sb.append("</div>");
            sb.append("</div>");
            sb.append("</div>");
            sb.append("</div>");
            sb.append("</div>");
            sb.append("</div>");
            if(editper.equals("Y") && crainfo.getActiveflag()==1)
            {
                sb.append("<div class='row'>");
                sb.append("<div class='col-lg-12 col-md-12 col-sm-12 col-12 text-center'>");
                sb.append("<a href='javascript: callsignonoffedit("+crewrotationId+","+cractivityId+","+noofdays+");' class='edit_page'><img src='../assets/images/edit.png'></a>");
                sb.append("</div>");
                sb.append("</div>");
            }
            String st1 = sb.toString() + "#@#" +"";
            sb.setLength(0);
            response.getWriter().write(st1);
        } else {
            response.getWriter().write("Please check your login session....");
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
%>