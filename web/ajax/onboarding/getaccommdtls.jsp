<%@page import="com.web.jxp.user.UserInfo"%>
<%@page contentType="text/html"%>
<%@page language="java" import="java.util.*"%>
<%@page import="com.web.jxp.onboarding.OnboardingInfo" %>
<jsp:useBean id="onboarding" class="com.web.jxp.onboarding.Onboarding" scope="page"/>
<jsp:useBean id="vobj" class="com.web.jxp.base.Validate" scope="page"/>
<%
    try 
    {
        if (session.getAttribute("LOGININFO") != null) 
        {
            UserInfo uinfo = ((UserInfo) request.getSession().getAttribute("LOGININFO"));
            String str = "",  editper = "N", deleteper = "N";
            if (uinfo != null)
            {
                editper = uinfo.getEditper() != null ? uinfo.getEditper() : "N";
                deleteper = uinfo.getDeleteper() != null ? uinfo.getDeleteper() : "N";
            }
            if (request.getParameter("shortlistid") != null)
            {
                String shortlistids = request.getParameter("shortlistid") != null && !request.getParameter("shortlistid").equals("") ? vobj.replaceint(request.getParameter("shortlistid")) : "0";
                String types = request.getParameter("type") != null && !request.getParameter("type").equals("") ? vobj.replaceint(request.getParameter("type")) : "0";
                int shortlistId = Integer.parseInt(shortlistids);
                int type = Integer.parseInt(types);
                String view_onboarding = onboarding.getMainPath("view_onboarding");
                ArrayList MobDtlsList = new ArrayList();
                MobDtlsList = onboarding.getOnboadingListByshortlistId(shortlistId, type);
                OnboardingInfo oinfo = onboarding.getReqDocListId(shortlistId);
                
                StringBuffer sb = new StringBuffer();
                sb.append("<h2>ADD ACCOMMODATION DETAILS</h2>");
                sb.append("<div class='row client_position_table" + (oinfo != null && oinfo.getChkAccommodation()== 1 ? "" : "1") + "' id='dnotereqsize'>");
                sb.append("<div class='col-lg-12 not_req'>");
                sb.append("<label class='mt-checkbox mt-checkbox-outline'> Not Required");
                sb.append("<input type='checkbox' id='chkAccommodation' name='chkAccommodation' value='1' " + (oinfo != null && oinfo.getChkAccommodation() == 1 ? "checked" : "") + " onchange=\"javascript : getNotReqTravel('2')\">");
                sb.append("<span></span>");
                sb.append("</label>");
                sb.append("</div>");
                sb.append("</div>");

                sb.append("<div class='row client_position_table1' id='dAccommodationNotReq' " + (oinfo != null && oinfo.getChkAccommodation() == 1 ? "style='display:none'" : "") + ">");
                sb.append("<div class='wrapper01'><div class='div01-tasks'></div></div>");
                sb.append("<div class='wrapper02'>");
                sb.append("<div class='table-scrollable table-detail div02-tasks'>");
                sb.append("<table class='table table-bordered table-striped mb-0'>");
                sb.append("<tbody>");
                sb.append("<td width='8%'><b>Hotel Name</b></br><input type='text' class='form-control' id='val1' name='val1' autocomplete='off'/></td>");
                sb.append("<td width='10%'><b>Address</b></br><input type='text' class='form-control' id='val2' name='val2' autocomplete='off'/></td>");
                sb.append("<td width='10%'><b>Contact Number</b></br><input type='text' class='form-control' id='val3' name='val3' autocomplete='off'/></td>");
                sb.append("<td width='8%'><b>Room No.</b></br><input type='text' class='form-control' id='val4' name='val4' autocomplete='off'/></td>");
                sb.append("<td width='8%'><b>Check-in Date</b></br>");
                sb.append("<div class='input-daterange input-group'>");
                sb.append("<input type='text' id='vald9' name='vald9' value='' class='form-control add-style wesl_dt date-add' placeholder='DD-MMM-YYYY' autocomplete='off' />");
                sb.append("</div>");
                sb.append("</td>");
                sb.append("<td width='8%'><b>Check-in Time</b></br>");
                sb.append("<div class='input-group'>");
                sb.append("<input type='text' id='valt9' name='valt9' class='form-control timepicker timepicker-24' autocomplete='off'>");
                sb.append("</div>");
                sb.append("</td>");
                sb.append("<td width='8%'><b>Check-out Date</b></br>");
                sb.append("<div class='input-daterange input-group'>");
                sb.append("<input type='text' id='vald10' name='vald10' value='' class='form-control add-style wesl_dt date-add' placeholder='DD-MMM-YYYY' autocomplete='off' />");
                sb.append("</div>");
                sb.append("</td>");
                sb.append("<td width='9%'><b>Check-out Time</b></br>");
                sb.append("<div class='input-group'>");
                sb.append("<input type='text' id='valt10' name='valt10' class='form-control timepicker timepicker-24' autocomplete='off'>");
                sb.append("</div>");
                sb.append("</td>");
                sb.append("<td width='9%'><b>Booking No.(optional)</b></br><input type='text' class='form-control' id='val5' name='val5' autocomplete='off'/></td>");
                sb.append("<td width='9%'><b>Remarks</b></br><input type='text' class='form-control' id='val6' name='val6' autocomplete='off'/></td>");
                sb.append("<td width='9%'>");
                sb.append("<b>Documents</b></br>");
                sb.append("<input type='file' id='upload1' name='upload1' onchange=\"javascript: setofferfilebase();\" /><input type='hidden' id='hdnfilename' name='hdnfilename'/>");
                sb.append("<a href='javascript:void(0);' id='upload_link_1' class='attache_btn attache_btn_white uploaded_img1'>");
                sb.append("<i class='fas fa-paperclip'></i> Attach");
                sb.append("</a>");
                sb.append("</td>");
                sb.append("<td width='4%'><b>&nbsp;</b></br>");
                if (editper.equals("Y")) {
                    sb.append("<a href='javascript:;' onclick=\"getSaveMobi('" + shortlistId + "','2')\" class='save_row'>Save</a>");
                }
                sb.append("</td>");
                sb.append("</tr>");
                sb.append("</tbody>");
                sb.append("<tbody id='tbodyaccomm'>");
                
                int isize = MobDtlsList.size();
                if (isize > 0) 
                {
                    OnboardingInfo info = null;
                    for (int i = 0; i < isize; i++)
                    {
                        info = (OnboardingInfo) MobDtlsList.get(i);
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
                sb.append("</tbody>");
                sb.append("</table>");
                sb.append("</div>");
                sb.append("</div>");
                sb.append("</div>");
                sb.append("<div class='row'>");
                sb.append("<div class='col-lg-12 col-md-12 col-sm-12 col-12 mt_15'>");
                sb.append("<a href='javascript:;' class='save_page float-end' onclick=\"getSaveAccomm()\" ><img src='../assets/images/save.png'> Save</a>");
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