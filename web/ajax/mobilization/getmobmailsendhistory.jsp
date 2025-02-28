<%@page import="com.web.jxp.user.UserInfo"%>
<%@page contentType="text/html"%>
<%@page language="java" import="java.util.*"%>
<%@page import="com.web.jxp.mobilization.MobilizationInfo" %>
<jsp:useBean id="mobilization" class="com.web.jxp.mobilization.Mobilization" scope="page"/>
<jsp:useBean id="vobj" class="com.web.jxp.base.Validate" scope="page"/>
<%
    try 
    {
        if (session.getAttribute("LOGININFO") != null) 
        {
            String str = "";           
            if (request.getParameter("mobids") != null) 
            {
                String mobids = request.getParameter("mobids") != null && !request.getParameter("mobids").equals("") ? vobj.replacename(request.getParameter("mobids")) : "";
                String crewroationids = request.getParameter("crewroationid") != null && !request.getParameter("crewroationid").equals("") ? vobj.replaceint(request.getParameter("crewroationid")) : "0";
                String clientids = request.getParameter("clientid") != null && !request.getParameter("clientid").equals("") ? vobj.replaceint(request.getParameter("clientid")) : "0";
                String clientassetids = request.getParameter("clientassetid") != null && !request.getParameter("clientassetid").equals("") ? vobj.replaceint(request.getParameter("clientassetid")) : "0";
                String types = request.getParameter("type") != null && !request.getParameter("type").equals("") ? vobj.replaceint(request.getParameter("type")) : "0";
                int crewroationId = Integer.parseInt(crewroationids);
                int clientId = Integer.parseInt(clientids);
                int type = Integer.parseInt(types);
                int clientassetId = Integer.parseInt(clientassetids);
                String view_mobilization = mobilization.getMainPath("view_mobilization");
                ArrayList MobDtlsList = mobilization.getMobEmailFilesByMobId(mobids, type);
                session.setAttribute("MAILHISTDOCLIST", MobDtlsList);
                MobilizationInfo einfo = mobilization.setEmailDetail(crewroationId);
                ArrayList doccheckList = new ArrayList();
                doccheckList = mobilization.getDocListId(clientId, clientassetId);
                String ReqDoc = "";
                if (doccheckList.size() > 0) 
                {
                    MobilizationInfo mbinfo = null;
                    for (int i = 0; i < doccheckList.size(); i++) 
                    {
                        mbinfo = (MobilizationInfo) doccheckList.get(i);
                        if (mbinfo != null) 
                        {
                            ReqDoc += (i + 1) + ". " + mbinfo.getDdlLabel() + "\n";
                        }
                    }
                }
                String subject = "", mailbody = "";
                mailbody = "Dear Crew member, \n \n "
                        + "Please find the Mobilization Details and Documents attached. Feel free to connect in case of any concerns. \n \n"
                        + "Please carry the documents listed below for successfull rotation: "
                        + "\n " + ReqDoc + " \n Thank you, \n Team OCS";

                subject = "Candidate Mobilization on " + einfo.getClientName() + " - " + einfo.getClientAsset();
                StringBuffer sb = new StringBuffer();
                sb.append("<h2>EMAIL DETAILS TO CREW</h2>");
                sb.append("<div class='row client_position_table'>");
                sb.append("<div class='col-lg-12 col-md-12 col-sm-12 col-4 form_group'>");
                sb.append("<label class='form_label'>From</label>");
                sb.append("<input class='form-control' id='mailfrom' name='mailfrom' value='journeyxpro@journeyxpro.com' placeholder='' readonly>");
                sb.append("</div>");
                sb.append("<div class='col-lg-12 col-md-12 col-sm-12 col-4 form_group'>");
                sb.append("<label class='form_label'>To</label>");
                sb.append("<input class='form-control' id='mailto' name='mailto' placeholder='' value='" + einfo.getCandidatemail() + "'/>");
                sb.append("</div>");
                sb.append("<div class='col-lg-12 col-md-12 col-sm-12 col-4 form_group'>");
                sb.append("<label class='form_label'>CC</label>");
                sb.append("<input class='form-control'id='mailcc' name='mailcc'  placeholder='' />");
                sb.append("</div>");
                sb.append("<div class='col-lg-12 col-md-12 col-sm-12 col-4 form_group'>");
                sb.append("<label class='form_label'>BCC</label>");
                sb.append("<input class='form-control'id='mailbcc' name='mailbcc'  placeholder='' />");
                sb.append("</div>");
                sb.append("<div class='col-lg-12 col-md-12 col-sm-12 col-4 form_group'>");
                sb.append("<label class='form_label'>Email Subject</label>");
                sb.append("<input class='form-control' placeholder='' id='mailsubject' name='mailsubject' value='" + subject + "' >");
                sb.append("</div>");
                sb.append("<div class='col-lg-12 col-md-12 col-sm-12 col-4 form_group'>");
                sb.append("<label class='form_label'>Email Body</label>");
                sb.append("<textarea id='maildescription' name='maildescription' class='form-control' maxlength='2000' style = 'height : 180px'>");
                sb.append(mailbody);
                sb.append("</textarea>");
                sb.append("</div>");
                sb.append("<div class='col-lg-12 col-md-12 col-sm-12 col-4 form_group'>");
                sb.append("<label class='form_label'>Mobilization Details</label>");
                sb.append("<div class='full_width veri_details'>");
                
                int size = MobDtlsList.size();
                if (size > 0)
                {
                    MobilizationInfo info = null;
                    for (int i = 0; i < size; i++) 
                    {
                        info = (MobilizationInfo) MobDtlsList.get(i);
                        if (info != null) 
                        {
                            if (!info.getFilename().equals("")) 
                            {
                                sb.append("<ul>");
                                if (info.getType() == 1) {
                                    sb.append("<li>" + info.getVal5() + (i + 1) + ".pdf</li>");
                                } else if (info.getType() == 2) {
                                    sb.append("<li>" + info.getVal1() + (i + 1) + ".pdf</li>");
                                }
                                sb.append("<li><a href='" + view_mobilization + info.getFilename() + "' target='_blank'>View Attachment</a></li>");
                                sb.append("</ul>");
                            }
                        }
                    }
                }
                sb.append("</div>");
                sb.append("</div>");
                sb.append("</div>");
                sb.append("<div class='row'>");
                sb.append("<div class='col-lg-12 col-md-12 col-sm-12 col-12 text-center' id='dsendhistmail'>");
                sb.append("<a href='javascript:void(0);' onclick=\"sendmobhistmail('" + crewroationId + "');\" class='save_page' >Send &nbsp;&nbsp;<img src='../assets/images/share-as-email.png'></a>");
                sb.append("</div>");
                sb.append("</div>");
                str = sb.toString();
                sb.setLength(0);
                response.getWriter().write(str);
            }
        } else {
            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
%>