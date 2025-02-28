<%@page import="java.util.Date"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.Collection"%>
<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@page import="com.web.jxp.user.UserInfo" %>
<%@page import="com.web.jxp.onboarding.OnboardingInfo" %>
<jsp:useBean id="onboarding" class="com.web.jxp.onboarding.Onboarding" scope="page"/>
<jsp:useBean id="vobj" class="com.web.jxp.base.Validate" scope="page"/>
<%
    try 
    {
        if (session.getAttribute("LOGININFO") != null) 
        {
            int uid = 0;
            UserInfo uinfo = ((UserInfo) request.getSession().getAttribute("LOGININFO"));
            String str = "", deleteper = "N";
            if (uinfo != null)
            {
                uid = uinfo.getUserId();
                deleteper = uinfo.getDeleteper() != null ? uinfo.getDeleteper() : "N";
            }
            if (request.getParameter("shortlistid") != null) 
            {
                String shortlistids = request.getParameter("shortlistid") != null && !request.getParameter("shortlistid").equals("") ? vobj.replaceint(request.getParameter("shortlistid")) : "0";
                String types = request.getParameter("type") != null && !request.getParameter("type").equals("") ? vobj.replaceint(request.getParameter("type")) : "0";
                String val1 = request.getParameter("val1") != null && !request.getParameter("val1").equals("") ? vobj.replacedesc(request.getParameter("val1")) : "";
                String val2 = request.getParameter("val2") != null && !request.getParameter("val2").equals("") ? vobj.replacedesc(request.getParameter("val2")) : "";
                String val3 = request.getParameter("val3") != null && !request.getParameter("val3").equals("") ? vobj.replacedesc(request.getParameter("val3")) : "";
                String val4 = request.getParameter("val4") != null && !request.getParameter("val4").equals("") ? vobj.replacedesc(request.getParameter("val4")) : "";
                String val5 = request.getParameter("val5") != null && !request.getParameter("val5").equals("") ? vobj.replacedesc(request.getParameter("val5")) : "";
                String val6 = request.getParameter("val6") != null && !request.getParameter("val6").equals("") ? vobj.replacedesc(request.getParameter("val6")) : "";
                String val7 = request.getParameter("val7") != null && !request.getParameter("val7").equals("") ? vobj.replacedesc(request.getParameter("val7")) : "";
                String val8 = request.getParameter("val8") != null && !request.getParameter("val8").equals("") ? vobj.replacedesc(request.getParameter("val8")) : "";
                String vald9 = request.getParameter("vald9") != null && !request.getParameter("vald9").equals("") ? vobj.replacedate(request.getParameter("vald9")) : "";
                String valt9 = request.getParameter("valt9") != null && !request.getParameter("valt9").equals("") ? vobj.replacetime(request.getParameter("valt9")) : "";
                String vald10 = request.getParameter("vald10") != null && !request.getParameter("vald10").equals("") ? vobj.replacedate(request.getParameter("vald10")) : "";
                String valt10 = request.getParameter("valt10") != null && !request.getParameter("valt10").equals("") ? vobj.replacetime(request.getParameter("valt10")) : "";
                String filename = request.getParameter("filename") != null && !request.getParameter("filename").equals("") ? vobj.replacedesc(request.getParameter("filename")) : "";
                String attachfile = request.getParameter("attachfile") != null && !request.getParameter("attachfile").equals("") ? vobj.replacedesc(request.getParameter("attachfile")) : "";
                int shortlistId = Integer.parseInt(shortlistids);
                int type = Integer.parseInt(types);
                filename = "";
                response.setContentType("text/html");
                response.setHeader("Cache-Control", "no-cache");
                String add_onboarding = onboarding.getMainPath("add_onboarding");
                String view_onboarding = onboarding.getMainPath("view_onboarding");

                if (shortlistId > 0) {
                    StringBuffer sb = new StringBuffer();
                    if (val7.equals("")) {
                        val7 = "";
                    }
                    if (val8.equals("")) {
                        val8 = "";
                    }
                    if (attachfile.equals("")) {
                        filename = "";
                    } else {
                        String foldername = onboarding.createFolder(add_onboarding);
                        Date now = new Date();
                        String fn = String.valueOf(now.getTime());
                        filename = onboarding.saveImage(attachfile, add_onboarding, foldername, fn);
                    }

                    shortlistId = onboarding.insertOnboardingDtls(shortlistId, type, val1, val2, val3, val4, val5, val6, val7, val8, vald9, valt9, vald10, valt10, filename,
                            uid, 1);
                    ArrayList OnDetls_list = onboarding.getOnboadingListByshortlistId(shortlistId, type);
                    int isize = OnDetls_list.size();
                    if (isize > 0)
                    {
                        OnboardingInfo info = null;
                        if (type == 1) 
                        {
                            for (int i = 0; i < isize; i++) 
                            {
                                info = (OnboardingInfo) OnDetls_list.get(i);
                                if (info != null)
                                {
                                    sb.append("<tr class='d-none1'>");
                                    sb.append("<td>");
                                    sb.append("<b>Mode</b></br><span class='form-control'>" + (info.getVal1().equals("") ? "&nbsp;" : info.getVal1()) + "</span>");
                                    sb.append("</td>");
                                    sb.append("<td><b>Departure location</b></br><span class='form-control'>" + (info.getVal2().equals("") ? "&nbsp;" : info.getVal2()) + "</span></td>");
                                    sb.append("<td><b>Departure Airport/Train/Bus station</b></br><span class='form-control'>" + (info.getVal3().equals("") ? "&nbsp;" : info.getVal3()) + "</span></td>");
                                    sb.append("<td><b>Departure Terminal/Platform No.</b></br><span class='form-control'>" + (info.getVal4().equals("") ? "&nbsp;" : info.getVal4()) + "</span></td>");
                                    sb.append("<td><b>Flight/train/Bus/Car/number</b></br><span class='form-control'>" + (info.getVal5().equals("") ? "&nbsp;" : info.getVal5()) + "</span></td>");
                                    sb.append("<td colspan='2'><b>ETD</b></br><span class='form-control'>" + (info.getVal9().equals("") ? "&nbsp;" : info.getVal9()) + "</span></td>");
                                    sb.append("<td colspan='2'><b>ETA</b></br><span class='form-control'>" + (info.getVal10().equals("") ? "&nbsp;" : info.getVal10()) + "</span></td>");
                                    sb.append("<td><b>Arrival location</b></br><span class='form-control'>" + (info.getVal6().equals("") ? "&nbsp;" : info.getVal6()) + "</span></td>");
                                    sb.append("<td><b>Arrival Airport/Train/Bus station</b></br><span class='form-control'>" + (info.getVal7().equals("") ? "&nbsp;" : info.getVal7()) + "</span></td>");
                                    sb.append("<td><b>Arrival Terminal/Platform No.</b></br><span class='form-control'>" + (info.getVal8().equals("") ? "&nbsp;" : info.getVal8()) + "</span></td>");
                                    sb.append("<td>");
                                    sb.append("<b>Document / Ticket</b></br>");
                                    if (!info.getFilename().equals("")) {
                                        sb.append("<a href='" + (view_onboarding + info.getFilename()) + "' class='view_document' target='_blank'><img src='../assets/images/view.png'></a>");
                                    } else {
                                        sb.append("&nbsp;");
                                    }
                                    sb.append("</td>");
                                    sb.append("<td><b>&nbsp;</b></br>");
                                    if (deleteper.equals("Y")) {
                                        sb.append("<a href='javascript:;' onclick=\"getDeleteMobDtls('" + info.getOnboardingdtlsid() + "','" + info.getType() + "')\" class='close_row'><i class='ion ion-md-close'></i></a>");
                                    }
                                    sb.append("</td>");
                                    sb.append("</tr>");
                                }
                            }
                        } else if (type == 2) {
                            for (int i = 0; i < isize; i++) 
                            {
                                info = (OnboardingInfo) OnDetls_list.get(i);
                                if (info != null) 
                                {
                                    sb.append("<tr class='d-none1'>");
                                    sb.append("<td>");
                                    sb.append("<b>Hotel Name</b></br><span class='form-control'>" + (info.getVal1().equals("") ? "&nbsp;" : info.getVal1()) + "</span>");
                                    sb.append("</td>");
                                    sb.append("<td><b>Address</b></br><span class='form-control'>" + (info.getVal2().equals("") ? "&nbsp;" : info.getVal2()) + "</span></td>");
                                    sb.append("<td><b>Contact Number</b></br><span class='form-control'>" + (info.getVal3().equals("") ? "&nbsp;" : info.getVal3()) + "</span></td>");
                                    sb.append("<td><b>Room No.</b></br><span class='form-control'>" + (info.getVal4().equals("") ? "&nbsp;" : info.getVal4()) + "</span></td>");
                                    sb.append("<td colspan='2'><b>Check-in Date</b></br><span class='form-control'>" + (info.getVal9().equals("") ? "&nbsp;" : info.getVal9()) + "</span></td>");
                                    sb.append("<td colspan='2'><b>Check-out Date</b></br><span class='form-control'>" + (info.getVal10().equals("") ? "&nbsp;" : info.getVal10()) + "</span></td>");
                                    sb.append("<td><b>Booking No.</b></br><span class='form-control'>" + (info.getVal5().equals("") ? "&nbsp;" : info.getVal5()) + "</span></td>");
                                    sb.append("<td><b>Remarks</b></br><span class='form-control'>" + (info.getVal6().equals("") ? "&nbsp;" : info.getVal6()) + "</span></td>");
                                    sb.append("<td>");
                                    sb.append("<b>Documents</b></br>");
                                    if (!info.getFilename().equals("")) {
                                        sb.append("<a href='" + (view_onboarding + info.getFilename()) + "' id='' class='view_document' target='_blank'><img src='../assets/images/view.png'></a>");
                                    } else {
                                        sb.append("&nbsp;");
                                    }
                                    sb.append("</td>");
                                    sb.append("<td><b>&nbsp;</b></br>");
                                    if (deleteper.equals("Y")) {
                                        sb.append("<a href='javascript:;' onclick=\"getDeleteMobDtls('" + info.getOnboardingdtlsid() + "','" + info.getType() + "')\" class='close_row'><i class='ion ion-md-close'></i></a>");
                                    }
                                    sb.append("</td>");
                                    sb.append("</tr>");
                                }
                            }
                        }
                        sb.append("<input type='hidden' id='mobcount' name='mobcount' value='" + isize + "' />");
                    }
                    str = sb.toString();
                    sb.setLength(0);
                    response.getWriter().write(str);
                }
            } else {
                response.setStatus(HttpServletResponse.SC_NO_CONTENT);
            }
        } else {
            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
        }
    } catch (Exception e) {
        e.printStackTrace();
        response.getWriter().write("Something went wrong.");
    }
%>