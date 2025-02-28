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
            UserInfo uinfo = ((UserInfo) request.getSession().getAttribute("LOGININFO"));
            String str = "", editper = "N";
            if (uinfo != null) 
            {
                editper = uinfo.getEditper() != null ? uinfo.getEditper() : "N";
            }
            if (request.getParameter("mobilizationid") != null) 
            {
                String mobilizationids = request.getParameter("mobilizationid") != null && !request.getParameter("mobilizationid").equals("") ? vobj.replaceint(request.getParameter("mobilizationid")) : "0";
                int mobilizationId = Integer.parseInt(mobilizationids);

                MobilizationInfo info = mobilization.getDetailsByMobId(mobilizationId);
                StringBuffer sb = new StringBuffer();

                if (info != null) 
                {
                    sb.append("<h2>ACCOMMODATION DETAILS</h2>");
                    sb.append("<div class='row client_position_table1 view_trav_accom'>");
                    sb.append("<div class='wrapper1'><div class='div1-tasks'></div></div>");
                    sb.append("<div class='wrapper2'>");
                    sb.append("<div class='table-scrollable table-detail div2-tasks'>");
                    sb.append("<table class='table table-bordered table-striped mb-0'>");
                    sb.append("<tbody>");
                    sb.append("<tr>");
                    sb.append("<td width='8%'><b>Hotel Name</b></br><input type='text' class='form-control' id='val1' name='val1' value='" + (info.getVal1() != null ? info.getVal1() : "") + "'/></td>");
                    sb.append("<td width='10%'><b>Address</b></br><input type='text' class='form-control' id='val2' name='val2' value='" + (info.getVal2() != null ? info.getVal2() : "") + "'/></td>");
                    sb.append("<td width='10%'><b>Contact Number</b></br><input type='text' class='form-control' id='val3' name='val3' value='" + (info.getVal3() != null ? info.getVal3() : "") + "'/></td>");
                    sb.append("<td width='8%'><b>Room No.</b></br><input type='text' class='form-control' id='val4' name='val4'value='" + (info.getVal4() != null ? info.getVal4() : "") + "'/></td>");
                    sb.append("<td width='8%'><b>Check-in Date</b></br>");
                    sb.append("<div class='input-daterange input-group'>");
                    sb.append("<input type='text' id='vald9' name='vald9' value='" + (info.getVald9() != null ? info.getVald9() : "") + "' class='form-control add-style wesl_dt date-add' placeholder='DD-MMM-YYYY'>");
                    sb.append("</div>");
                    sb.append("</td>");
                    sb.append("<td width='8%'><b>Check-in Time</b></br>");
                    sb.append("<div class='input-group'>");
                    sb.append("<input type='text' id='valt9' name='valt9' value='" + (info.getValt9() != null ? info.getValt9() : "") + "' class='form-control timepicker timepicker-24'>");
                    sb.append("</div>");
                    sb.append("</td>");
                    sb.append("<td width='8%'><b>Check-out Date</b></br>");
                    sb.append("<div class='input-daterange input-group'>");
                    sb.append("<input type='text' id='vald10' name='vald10' value='" + (info.getVald10() != null ? info.getVald10() : "") + "' class='form-control add-style wesl_dt date-add' placeholder='DD-MMM-YYYY'>");
                    sb.append("</div>");
                    sb.append("</td>");
                    sb.append("<td width='9%'><b>Check-out Time</b></br>");
                    sb.append("<div class='input-group'>");
                    sb.append("<input type='text' id='valt10' name='valt10' value='" + (info.getValt10() != null ? info.getValt10() : "") + "' class='form-control timepicker timepicker-24'>");
                    sb.append("</div>");
                    sb.append("</td>");
                    sb.append("<td width='9%'><b>Booking No.(optional)</b></br><input type='text' class='form-control' id='val5' name='val5' value='" + (info.getVal5() != null ? info.getVal5() : "") + "'/></td>");
                    sb.append("<td width='9%'><b>Remarks</b></br><input type='text' class='form-control' id='val6' name='val6' value='" + (info.getVal6() != null ? info.getVal6() : "") + "'/></td>");
                    sb.append("<td width='9%'>");
                    sb.append("<b>Documents</b></br>");
                    sb.append("<input type='file' id='upload1' name='upload1' onchange=\"javascript: setClass('1');\" />"
                            + "<input type='hidden' id='hdnfilename' name='hdnfilename' value='" + (info.getFilename() != null ? info.getFilename() : "") + "'/>");
                    sb.append("<a href='javascript:void(0);' id='upload_link_1' class='attache_btn attache_btn_white uploaded_img");
                    if (info.getFilename().equals("") || info.getFilename() == null) {
                        sb.append("1");
                    }
                    sb.append("'>");
                    sb.append("<i class='fas fa-paperclip'></i> Attach");
                    sb.append("</a>");
                    sb.append("</td>");
                    sb.append("<td width='4%'><b>&nbsp;</b></br>");
                    if (editper.equals("Y")) {
                        sb.append("<a href='javascript:;' onclick=\"getSaveMobibyId('" + mobilizationId + "','2')\" class='save_row'>Save</a>");
                    }
                    sb.append("</td>");
                    sb.append("</tr>");
                    sb.append("</tbody>");
                    sb.append("</table>");
                    sb.append("</div>");
                    sb.append("</div>");
                    sb.append("</div>");
                }

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