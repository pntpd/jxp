<%@page import="java.io.File"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.Collection"%>
<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@page import="com.web.jxp.user.UserInfo" %>
<%@page import="com.web.jxp.mobilization.MobilizationInfo" %>
<jsp:useBean id="mobilization" class="com.web.jxp.mobilization.Mobilization" scope="page"/>
<jsp:useBean id="vobj" class="com.web.jxp.base.Validate" scope="page"/>
<%
    try 
    {
        if (session.getAttribute("LOGININFO") != null) 
        {
            UserInfo uinfo = ((UserInfo) request.getSession().getAttribute("LOGININFO"));
            String str = "", deleteper = "N";
            if (uinfo != null)
            {
                deleteper = uinfo.getDeleteper() != null ? uinfo.getDeleteper() : "N";
            }
            if (request.getParameter("mobid") != null) 
            {
                String mobids = request.getParameter("mobid") != null && !request.getParameter("mobid").equals("") ? vobj.replaceint(request.getParameter("mobid")) : "0";
                String types = request.getParameter("type") != null && !request.getParameter("type").equals("") ? vobj.replaceint(request.getParameter("type")) : "0";
                String batchs = request.getParameter("batchid") != null && !request.getParameter("batchid").equals("") ? vobj.replaceint(request.getParameter("batchid")) : "0";
                int mobId = Integer.parseInt(mobids);
                int type = Integer.parseInt(types);
                int batch = Integer.parseInt(batchs);
                response.setContentType("text/html");
                response.setHeader("Cache-Control", "no-cache");

                if (mobId > 0) 
                {
                    StringBuffer sb = new StringBuffer();
                    String add_mobilization = mobilization.getMainPath("add_mobilization");
                    String view_mobilization = mobilization.getMainPath("view_mobilization");
                    MobilizationInfo delinfo = mobilization.getDetailsByMobId(mobId);
                    if (delinfo.getFilename() != null || !delinfo.getFilename().equals(""))
                    {
                        File file = new File(add_mobilization + delinfo.getFilename());
                        if (file.exists())
                        {
                            file.delete();
                        }
                    }
                    mobilization.deleteMobilizationDtls(mobId);
                    ArrayList MobDetls_list = mobilization.getMobilizationListByBatch(batch, type);
                    int isize = MobDetls_list.size();
                    if (isize > 0) 
                    {
                        MobilizationInfo info = null;
                        if (type == 1) 
                        {
                            for (int i = 0; i < isize; i++) 
                            {
                                info = (MobilizationInfo) MobDetls_list.get(i);
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
                                        sb.append("<a href='" + (view_mobilization + info.getFilename()) + "' class='view_document' target='_blank'><img src='../assets/images/view.png'></a>");
                                    } else {
                                        sb.append("&nbsp;");
                                    }
                                    sb.append("</td>");
                                    sb.append("<td><b>&nbsp;</b></br>");
                                    if (deleteper.equals("Y")) {
                                        sb.append("<a href='javascript:;' onclick=\"getDeleteMobDtls('" + info.getMobilizationId() + "','" + info.getBatchId() + "','" + info.getType() + "')\" class='close_row'><i class='ion ion-md-close'></i></a>");
                                    }
                                    sb.append("</td>");
                                    sb.append("</tr>");
                                }
                            }
                        } else if (type == 2) 
                        {
                            for (int i = 0; i < isize; i++)
                            {
                                info = (MobilizationInfo) MobDetls_list.get(i);
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
                                        sb.append("<a href='" + (view_mobilization + info.getFilename()) + "' id='' class='view_document' target='_blank'><img src='../assets/images/view.png'></a>");
                                    } else {
                                        sb.append("&nbsp;");
                                    }
                                    sb.append("</td>");
                                    sb.append("<td><b>&nbsp;</b></br>");
                                    if (deleteper.equals("Y")) {
                                        sb.append("<a href='javascript:;' onclick=\"getDeleteMobDtls('" + info.getMobilizationId() + "','" + info.getBatchId() + "','" + info.getType() + "')\" class='close_row'><i class='ion ion-md-close'></i></a>");
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