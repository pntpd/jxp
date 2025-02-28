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
            String str = "", editper = "N", deleteper = "N";
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
                sb.append("<h2>ADD TRAVEL DETAILS</h2>");
                sb.append("<div class='row client_position_table" + (oinfo != null && oinfo.getChkNotReqTravel() == 1 ? "" : "1") + "' id='dnotereqsize'>");
                sb.append("<div class='col-lg-12 not_req'>");
                sb.append("<label class='mt-checkbox mt-checkbox-outline'> Not Required");
                sb.append("<input type='checkbox' id='chkNotReqTravel' name='chkNotReqTravel' " + (oinfo != null && oinfo.getChkNotReqTravel() == 1 ? "checked" : "") + " value='1' onchange=\"javascript : getNotReqTravel('1')\">");
                sb.append("<span></span>");
                sb.append("</label>");
                sb.append("</div>");
                sb.append("</div>");

                sb.append("<div class='row client_position_table1' id='dMobAccNotReq' " + (oinfo != null && oinfo.getChkNotReqTravel() == 1 ? "style='display:none'" : "") + ">");
                sb.append("<div class='wrapper1'><div class='div1-tasks'></div></div>");
                sb.append("<div class='wrapper2'>");
                sb.append("<div class='table-scrollable table-detail div02-tasks'>");
                sb.append("<table class='table table-bordered table-striped mb-0'>");
                sb.append("<tbody>");
                sb.append("<tr>");
                sb.append("<td width='8%'>");
                sb.append("<b>Mode</b></br>");
                sb.append("<select class='form-select' id='val1' name='val1'>");
                sb.append("<option value=''>Select</option>");
                sb.append("<option value='Flight'>Flight</option>");
                sb.append("<option value='Train'>Train</option>");
                sb.append("<option value='Bus'>Bus</option>");
                sb.append("<option value='Car'>Car</option>");
                sb.append("</select>");
                sb.append("</td>");
                sb.append("<td width='10%'><b>Departure location</b></br><input type='text' class='form-control' id='val2' name='val2' autocomplete='off' /></td>");
                sb.append("<td width='10%'><b>Departure Airport/Train/Bus station</b></br><input type='text' class='form-control' id='val3' name='val3' autocomplete='off' /></td>");
                sb.append("<td width='8%'><b>Departure Terminal/Platform No.</b></br><input type='text' class='form-control' id='val4' name='val4' autocomplete='off' /></td>");
                sb.append("<td width='8%'><b>Flight/train/Bus/Car/number</b></br><input type='text' class='form-control' id='val5' name='val5' autocomplete='off'/></td>");
                sb.append("<td width='4%'><b>ETD - Date <span class='dt_label'>&nbsp;</span></b></br><div class='input-daterange input-group'><input type='text' class='form-control add-style wesl_dt date-add' placeholder='DD-MMM-YYYY' id='vald9' name='vald9' autocomplete='off' /></div></td>");
                sb.append("<td width='4%'><b>ETD - Time <span class='time_label'>&nbsp;</span></b></br><div class='input-group'><input type='text' id='valt9' name='valt9' class='form-control timepicker timepicker-24' autocomplete='off' /></div></td>");
                sb.append("<td width='4%'><b>ETA - Date <span class='dt_label'>&nbsp;</span></b></br><div class='input-daterange input-group'><input type='text' class='form-control add-style wesl_dt date-add' placeholder='DD-MMM-YYYY' id='vald10' name='vald10' autocomplete='off' /></div></td>");
                sb.append("<td width='4%'><b>ETA - Time <span class='time_label'>&nbsp;</span></b></br><div class='input-group'><input type='text' id='valt10' name='valt10' class='form-control timepicker timepicker-24' autocomplete='off' /></div></td>");
                sb.append("<td width='9%'><b>Arrival location</b></br><input type='text' class='form-control' id='val6' name='val6' autocomplete='off' /></td>");
                sb.append("<td width='9%'><b>Arrival Airport/Train/Bus station</b></br><input type='text' class='form-control' id='val7' name='val7' autocomplete='off' /></td>");
                sb.append("<td width='9%'><b>Arrival Terminal/Platform No.</b></br><input type='text' class='form-control' id='val8' name='val8' autocomplete='off' /></td>");
                sb.append("<td width='9%'>");
                sb.append("<b>Document / Ticket</b></br>");
                sb.append("<input type='file' id='upload1' name='upload1' onchange=\"javascript: setofferfilebase();\" /><input type='hidden' id='hdnfilename' name='hdnfilename'/>");
                sb.append("<a href='javascript:void(0);' id='upload_link_1' class='attache_btn attache_btn_white uploaded_img1'>");
                sb.append("<i class='fas fa-paperclip'></i> Attach");
                sb.append("</a>");
                sb.append("</td>");
                sb.append("<td width='4%'><b>&nbsp;</b></br>");
                if (editper.equals("Y")) {
                    sb.append("<a href='javascript:;' onclick=\"getSaveMobi('" + shortlistId + "','1')\" class='save_row'>Save</a>");
                }
                sb.append("</td>");

                sb.append("</tr>");
                sb.append("</tbody>");
                sb.append("<tbody id='tbodytravel'>");
                
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
                }
                sb.append("<input type='hidden' id='mobcount' name='mobcount' value='" + isize + "' />");
                sb.append("</tbody>");
                sb.append("</table>");
                sb.append("</div>");
                sb.append("</div>");
                sb.append("</div>");
                sb.append("<div class='row'>");
                sb.append("<div class='col-lg-12 col-md-12 col-sm-12 col-12 mt_15'>");
                sb.append("<a href='javascript:void(0);' onclick=\"getSaveTravel();\" class='save_page float-end'><img src='../assets/images/save.png'> Save</a>");
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