<%@page import="java.util.TreeSet"%>
<%@page import="java.util.SortedSet"%>
<%@page import="java.util.LinkedList"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.Collection"%>
<%@page import="org.json.JSONObject"%>
<%@page import="java.io.BufferedReader"%>
<%@page contentType="text/html"%>
<%@page language="java" import="java.util.ArrayList"%>
<%@page import="com.web.jxp.user.UserInfo" %>
<%@page import="com.web.jxp.onboarding.OnboardingInfo" %>
<jsp:useBean id="onboarding" class="com.web.jxp.onboarding.Onboarding" scope="page"/>
<jsp:useBean id="vobj" class="com.web.jxp.base.Validate" scope="page"/>
<%
    try
    {
        int userId = 0;
        UserInfo uInfo = (UserInfo) request.getSession().getAttribute("LOGININFO");
        if (uInfo != null) 
        {
            userId = uInfo.getUserId();
            if (userId > 0) 
            {
                String onstatuss = request.getParameter("onstatus") != null && !request.getParameter("onstatus").equals("") ? vobj.replaceint(request.getParameter("onstatus")) : "0";
                String clientIds = request.getParameter("clientId") != null && !request.getParameter("clientId").equals("") ? vobj.replaceint(request.getParameter("clientId")) : "0";
                String clientassetIds = request.getParameter("clientassetId") != null && !request.getParameter("clientassetId").equals("") ? vobj.replaceint(request.getParameter("clientassetId")) : "0";
                String searchons = request.getParameter("searchon") != null && !request.getParameter("searchon").equals("") ? vobj.replacename(request.getParameter("searchon")) : "";
                int onstatus = Integer.parseInt(onstatuss);
                int clientassetId = Integer.parseInt(clientassetIds);
                int clientId = Integer.parseInt(clientIds);
                String file_path = onboarding.getMainPath("view_candidate_file");

                String cphoto = "../assets/images/empty_user.png";

                ArrayList list = onboarding.getSelectedCandidateListByIDs(clientId, clientassetId, searchons, onstatus);
                request.getSession().setAttribute("SELECTEDCANDIDATE_LIST", list);
                int total = list.size();
                StringBuffer sb = new StringBuffer();
                if (total > 0) 
                {
                    OnboardingInfo oinfo;
                    for (int i = 0; i < total; i++) 
                    {
                        oinfo = (OnboardingInfo) list.get(i);
                        int onboardflag = 0, onflag = 0;
                        onboardflag = oinfo.getOnboardflag();
                        onflag = oinfo.getOnflag();

                        if (!oinfo.getPhoto().equals("")) 
                        {
                            cphoto = file_path + oinfo.getPhoto();
                        }
                        if (oinfo != null) 
                        {
                            sb.append("<li class='odd_list_1'>");
                            sb.append("<div class='search_box'>");
                            sb.append("<div class='row'>");
                            sb.append("<div class='col-md-9 comp_view'>");
                            sb.append("<div class='row'>");
                            sb.append("<div class='col-md-12 client_prof_status'>");
                            sb.append("<div class='row d-flex align-items-start'>");
                            sb.append("<div class='col-md-3 com_view_prof cand_box_img'>");
                            sb.append("<div class='user_photo pic_photo'>");
                            sb.append("<div class='upload_file'>");
                            sb.append("<img src='");
                            sb.append(cphoto);
                            sb.append("'>");
                            sb.append("<a href='javascript:;' onclick=\"viewCandidate('" + oinfo.getCandidateId() + "')\"><img src='../assets/images/view.png'></a>");
                            sb.append("</div>");
                            sb.append("</div>");
                            sb.append("</div>");
                            sb.append("<div class='col-md-6'>");
                            sb.append("<div class='row'>");
                            sb.append("<div class='portlet box status_on_hold'>");
                            sb.append("<div class='portlet-title'>");
                            sb.append("<div class='caption'>Status - ");
                            sb.append(onboarding.getccStatusbyId(oinfo.getOnboardflag()));
                            sb.append("</div>");
                            if (onboardflag > 2) 
                            {
                                sb.append("<div class='actions'>");
                                sb.append("<a href=\"javascript: getSummaryDtls('" + oinfo.getShortlistId() + "');\"><i class='ion ion-md-information-circle-outline'></i> </a>");
                                sb.append("</div>");
                            }
                            sb.append("</div>");
                            sb.append("<div class='portlet-body'>");
                            sb.append("<div class='row'>");
                            sb.append("<div class='col-md-12 status_ic_checkbox'>");
                            sb.append("<ul>");
                            sb.append("<li>");
                            sb.append("<div class='status_ic'><span><i class='ion ion-ios-briefcase'></i></span></div>");
                            sb.append("<div class='form-check permission-check'>");
                            sb.append("<input class='form-check-input' type='checkbox' id='chkMobandAcc" + oinfo.getShortlistId() + "' ");
                            if (onflag == 1) {
                                sb.append("checked disabled ");
                            }
                            sb.append("onclick=\"javascript: getTraveldetails('" + oinfo.getShortlistId() + "','1');\" />");
                            sb.append("<input type='hidden' id='hdnMobandAcc" + oinfo.getShortlistId() + "' value='" + oinfo.getShortlistId() + "' />");
                            sb.append("<span>Mobilize (Travel & Accommodation)</span>");
                            sb.append("</div>");
                            sb.append("</li>");

                            sb.append("<li>");
                            sb.append("<div class='status_ic'><span><i class='fas fa-bed'></i></span></div>");
                            sb.append("<div class='form-check permission-check'>");
                            sb.append("<input class='form-check-input' type='checkbox' id='chkreqdoc" + oinfo.getShortlistId() + "' ");
                            if (onboardflag >= 2) {
                                sb.append("checked ");
                            }
                            sb.append("onclick=\"javascript:getReqdoc('" + oinfo.getShortlistId() + "');\" " + (onboardflag >= 2 ? "return false;" : "") + "/> ");
                            sb.append("<span>Required Documents</span>");
                            sb.append("</div>");
                            sb.append("</li>");
                            sb.append("</ul>");
                            sb.append("</div>");
                            sb.append("</div>");
                            sb.append("</div>");
                            sb.append("</div>");
                            sb.append("</div>");
                            sb.append("</div>");

                            sb.append("<div class='col-md-3 bag_bed'>");
                            sb.append("<ul>");
                            String tempclass1 = "not_required_btn", tempclass2 = "not_required_btn";
                            if (onboardflag == 3) {
                                tempclass1 = "";
                            }
                            if (onboardflag > 3) {
                                tempclass1 = "required_btn";
                            }
                            if (onboardflag == 4) {
                                tempclass2 = "";
                            }
                            if (onboardflag > 4) {
                                tempclass2 = "required_btn";
                            }
                            sb.append("<li class='" + tempclass1 + "'>");
                            sb.append("<a href='javascript:;' data-toggle='tooltip' data-placement='top' title='Docs Checks' ");
                            if (onboardflag == 3) {
                                sb.append("onclick=\"javascript:getDocCheckList('" + oinfo.getShortlistId() + "','');\" ");
                            }
                            sb.append("/><i class='far fa-file'></i></a></li>");
                            sb.append("<li class='" + tempclass2 + "'><a href='javascript:;' data-toggle='tooltip' data-placement='top' title='Joining Formalities' ");
                            if (onboardflag == 4) {
                                sb.append("onclick=\"javascript : viewgenerateformalities('" + oinfo.getShortlistId() + "');\" ");
                            }
                            sb.append("/><i class='far fa-copy'></i></a></li>");
                            sb.append("</ul>");
                            sb.append("</div>");
                            sb.append("</div>");
                            sb.append("</div>");
                            sb.append("</div>");
                            sb.append("</div>");
                            sb.append("<div class='col-md-3 add_view_area client_se_vi_re'>");
                            sb.append("<div class='row'>");
                            if (onboardflag <= 3) {
                                sb.append("<div class='search_add_btn'><a ");
                                if (onboardflag > 1) {
                                    sb.append("data-bs-toggle='modal' data-bs-target='#email_det_candidate_modal' ");
                                }
                                sb.append("href=\"javascript:;\" class='");
                                if (onboardflag < 2) {
                                    sb.append("inactive_btn");
                                } else {
                                    sb.append("active_btn");
                                }
                                sb.append("' ");
                                if (onboardflag > 1) {
                                    sb.append("onclick=\"javascript : gettravelModel('" + oinfo.getOnboardflag() + "', '" + oinfo.getShortlistId() + "')\" ");
                                }
                                sb.append(">");
                                if (onboardflag == 3) {
                                    sb.append("Re-send Email");
                                } else {
                                    sb.append("Send Email");
                                }
                                sb.append("</a></div>");
                            } else if (onboardflag == 4) {
                                sb.append("<div class='search_add_btn'><a href=\"javascript:;\" data-bs-toggle='modal' data-bs-target='#email_candidate_modal' class='active_btn' onclick=\"javscript : getModelexternal('" + oinfo.getOnboardflag() + "', '" + oinfo.getShortlistId() + "')\">Send Email</a></div>");
                            } else if (onboardflag >= 5 && onboardflag < 7) {
                                sb.append("<div class='search_add_btn'><a href=\"javascript:;\" class='active_btn' onclick=\" javscript : viewuploadedformalities('" + oinfo.getShortlistId() + "')\">Onboard</a></div>");
                            } else if (onboardflag == 7 || onboardflag == 8) {
                                sb.append("<div class='search_add_btn'><a href=\"javascript:;\" class='active_btn' onclick=\" javscript : viewuploadedformalities('" + oinfo.getShortlistId() + "')\">Onboard</a></div>");
                            } else if (onboardflag == 9) {
                                sb.append("<div class='search_add_btn'><a class='onboarded_label'>Onboarded</a></div>");
                            }
                            sb.append("</div>");
                            sb.append("</div>");

                            sb.append("<div class='col-md-12 comp_view'>");
                            sb.append("<div class='row'>");
                            sb.append("<div class='full_name_ic col-md-12 mb_0'>");
                            sb.append("<div class='row'>");
                            sb.append("<div class='col-md-5'>");
                            sb.append("<a href='javascript:;' class='tooltip_name' data-toggle='tooltip' data-placement='top' title='Full Name'><i class='mdi mdi-account'></i></a>");
                            sb.append("<span>");
                            sb.append(oinfo.getName());
                            sb.append("</span>");
                            sb.append("</div>");
                            sb.append("<div class='col-md-7 posi_rank'>");
                            sb.append("<span>");
                            sb.append(oinfo.getPosition());
                            sb.append("</span>");
                            sb.append("</div>");
                            sb.append("</div>");
                            sb.append("</div>");
                            sb.append("<div class='col-md-12'>");
                            sb.append("<div class='row'>");
                            sb.append("<div class='col-md-5 dep_location'><label><i class='ion ion-ios-briefcase'></i></label> <span>Travel - ");
                            if (onflag == 1) {
                                if (onboardflag < 4) {
                                    sb.append("<a href=\"javascript:;\" onclick=\"javascript: getTraveldetails('" + oinfo.getShortlistId() + "', '1');\" class='trav_his_view_details'>View Details</a>");
                                } else {
                                    sb.append("<a href=\"javascript:;\" onclick=\"javascript: getViewTravel('" + oinfo.getShortlistId() + "', '1');\" class='trav_his_view_details'>View Details</a>");

                                }
                            }
                            sb.append("</span>");
                            sb.append("</div>");
                            sb.append("<div class='col-md-7 dep_location'><label><i class='fas fa-bed'></i></label> <span>Accommodation - ");
                            if (onflag == 1) {
                                if (onboardflag < 4) {
                                    sb.append("<a href=\"javascript:;\" onclick=\"javascript: getAccommDetails('" + oinfo.getShortlistId() + "', '2');\" class='trav_his_view_details'>View Details</a>");

                                } else {

                                    sb.append("<a href=\"javascript:;\" onclick=\"javascript: getViewAccomm('" + oinfo.getShortlistId() + "', '2');\" class='trav_his_view_details'>View Details</a>");

                                }
                            }
                            sb.append("</span>");
                            sb.append("</div>");
                            sb.append("</div>");
                            sb.append("</div>");
                            sb.append("</div>");
                            sb.append("</div>");
                            sb.append("</div>");
                            sb.append("</div>");
                            sb.append("</li>");
                        }
                    }
                }
                String st = sb.toString();
                sb.setLength(0);
                response.getWriter().write(st);
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
%>