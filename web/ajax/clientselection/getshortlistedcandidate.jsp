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
<%@page import="com.web.jxp.clientselection.ClientselectionInfo" %>
<jsp:useBean id="clientselection" class="com.web.jxp.clientselection.Clientselection" scope="page"/>
<jsp:useBean id="vobj" class="com.web.jxp.base.Validate" scope="page"/>
<%
    try
    {
        int companytype = 0, userId = 0 ;
        UserInfo uInfo = (UserInfo) request.getSession().getAttribute("LOGININFO");
        if (uInfo != null) 
        {
            companytype = uInfo.getCompanytype();
            userId = uInfo.getUserId();
            if (userId > 0) 
            {
                String jobpostIds = request.getParameter("jobpostid") != null && !request.getParameter("jobpostid").equals("") ? vobj.replaceint(request.getParameter("jobpostid")) : "0";
                String selstatuss = request.getParameter("status") != null && !request.getParameter("status").equals("") ? vobj.replaceint(request.getParameter("status")) : "0";
                String selsubstatuss = request.getParameter("substatus") != null && !request.getParameter("substatus").equals("") ? vobj.replaceint(request.getParameter("substatus")) : "0";
                String searchs = request.getParameter("search") != null && !request.getParameter("search").equals("") ? vobj.replacename(request.getParameter("search")) : "";
                int jobpostId = Integer.parseInt(jobpostIds);
                int selstatus = Integer.parseInt(selstatuss);
                int selsubstatus = Integer.parseInt(selsubstatuss);
                String file_path = clientselection.getMainPath("view_candidate_file");
                String cvfile_path = clientselection.getMainPath("view_resumetemplate_pdf");

                ArrayList list = clientselection.getShortlistedCandidateListByIDs(jobpostId, companytype, searchs, selstatus, selsubstatus);
                request.getSession().setAttribute("SHORTLISTEDCANDIDATE_LIST", list);
                int total = list.size();
                StringBuffer sb = new StringBuffer();
                if (total > 0) 
                {
                    ClientselectionInfo cinfo;
                    String tempImg = "";
                    String classAddone = "";
                    String tempclass = "";
                    String tempclass1 = "";
                    for (int i = 0; i < total; i++)
                    {
                        cinfo = (ClientselectionInfo) list.get(i);
                        if (cinfo != null) 
                        {
                            tempImg = "";
                            tempclass = "status_on_hold";
                            tempclass1 = "client_se_vi_re";
                            if (companytype == 2 && (cinfo.getSflag() == 1 || cinfo.getSflag() == 2)) {
                            } else {
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
                                if (cinfo.getPhoto().equals("")) {
                                    tempImg = "../assets/images/empty_user_100x100.png";
                                } else {
                                    tempImg = file_path + cinfo.getPhoto();
                                }
                                sb.append("<img src='" + tempImg + "'>");
                                sb.append("<a href=\"javascript:viewCandidate('" + cinfo.getCandidateId() + "');\"><img src='../assets/images/view.png'></a>");
                                sb.append("</div>");
                                sb.append("</div>");
                                sb.append("</div>");
                                sb.append("<div class='col-md-9'>");
                                sb.append("<div class='row'>");
                                if (cinfo.getSflag() == 4) {
                                    tempclass = "status_selected";
                                } else if (cinfo.getSflag() == 5) {
                                    tempclass = "status_rejected";
                                }
                                sb.append("<div class='portlet box " + tempclass + "'>");
                                sb.append("<div class='portlet-title'>");
                                sb.append("<div class='caption'>Status - ");
                                if (cinfo.getSflag() == 1 || cinfo.getSflag() == 2 || cinfo.getSflag() == 3) {
                                    sb.append("On Hold");
                                } else if (cinfo.getSflag() == 4) {
                                    sb.append("Selected");
                                } else if (cinfo.getSflag() == 5) {
                                    sb.append("Rejected");
                                }

                                sb.append("</div>");
                                sb.append("<div class='actions'>");
                                if ((cinfo.getSflag() == 3 || cinfo.getSflag() == 4 || cinfo.getSflag() == 5) && companytype == 1) {
                                    sb.append("<a href=\"javascript: getCandidateSummary('" + cinfo.getShortlistId() + "');\" ><i class='ion ion-md-information-circle-outline'></i> </a>");
                                }

                                sb.append("</div> ");
                                sb.append("</div>");
                                sb.append("<div class='portlet-body'>");
                                sb.append("<div class='row'>");
                                sb.append("<div class='col-md-12 com_label_value'>");
                                sb.append("<div class='row mb_0'>");
                                sb.append("<div class='col-md-2'><label>Date</label></div>");
                                sb.append("<div class='col-md-10'><span>");
                                if (cinfo.getSflag() == 3) {
                                    sb.append(cinfo.getMailon());
                                } else if (cinfo.getSflag() == 4 || cinfo.getSflag() == 5) {
                                    sb.append(cinfo.getSron());
                                } else {
                                    sb.append("-");
                                }
                                sb.append("</span></div>");
                                sb.append("</div>");
                                sb.append("<div class='row mb_0'>");
                                sb.append("<div class='col-md-2'><label>User</label></div>");
                                sb.append("<div class='col-md-10'><span>");
                                if (cinfo.getSflag() == 3) {
                                    sb.append(cinfo.getMailby());
                                } else if (cinfo.getSflag() == 4 || cinfo.getSflag() == 5) {
                                    sb.append(cinfo.getSrby());
                                } else {
                                    sb.append("-");
                                }
                                sb.append("</span></div>");
                                sb.append("</div>");
                                sb.append("</div>");
                                sb.append("</div>");
                                sb.append("</div>");
                                sb.append("</div>");
                                sb.append("</div>");
                                sb.append("</div>");
                                sb.append("</div>");
                                sb.append("</div>");

                                sb.append("<div class='full_name_ic col-md-12 mb_0'>");
                                sb.append("<a href=\"javascript:;\" class='tooltip_name' data-toggle='tooltip' data-placement='top' title='Full Name'><i class='mdi mdi-account'></i></a>");
                                sb.append("<span>" + cinfo.getName() + "</span>");
                                sb.append("</div>");

                                sb.append("<div class='posi_rank_ic col-md-12 mb_0'>");
                                sb.append("<a href=\"javascript:;\" class='tooltip_name' data-toggle='tooltip' data-placement='top' title='Position-Rank'><i class='mdi mdi-star-circle'></i></a>");
                                sb.append("<span>" + cinfo.getPosition() + "</span>");
                                sb.append("</div>");

                                sb.append("<div class='expe_ic col-md-12 mb_0'>");
                                sb.append("<a href=\"javascript:;\" class='tooltip_name' data-toggle='tooltip' data-placement='top' title='Experience'><i class='mdi mdi-lightbulb'></i></a>");
                                sb.append("<span>" + cinfo.getExperience() + " Yrs</span>");
                                sb.append("</div>");

                                sb.append("<div class='gradu_ic col-md-12 mb_0'>");
                                sb.append("<a href=\"javascript:;\" class='tooltip_name' data-toggle='tooltip' data-placement='top' title='Education'><i class='fas fa-graduation-cap'></i></a>");
                                sb.append("<span>" + cinfo.getQualification() + "</span>");
                                sb.append("</div>");

                                sb.append("<div class='brief_ic col-md-12'>");
                                sb.append("<a href=\"javascript:;\" class='tooltip_name' data-toggle='tooltip' data-placement='top' title='Last Job'><i class='ion ion-ios-briefcase'></i></a>");
                                sb.append("<span>" + cinfo.getCompany() + "</span>");
                                sb.append("</div>");

                                sb.append("</div>");
                                sb.append("</div>");

                                if ((cinfo.getSflag() == 4 && cinfo.getSubstatus() != 3) || cinfo.getSflag() == 5) {
                                    tempclass1 = "";
                                }

                                sb.append("<div class='col-md-3 add_view_area " + tempclass1 + "'>");
                                sb.append("<div class='row'>");
                                sb.append("<div class='search_add_btn'>");
                                if (cinfo.getSflag() == 1) {
                                    sb.append("<a href=\"javascript:getGenerateCV('" + cinfo.getShortlistId() + "','" + cinfo.getSflag() + "','" + cinfo.getCandidateId() + "');\">Generate CV</a>");
                                } else if (cinfo.getSflag() == 2) {
                                    sb.append("<a href=\"javascript:;\" onclick=\"javascript: getModel('" + cinfo.getShortlistId() + "', '" + cinfo.getSflag() + "');\" data-bs-toggle='modal' data-bs-target='#mail_modal'>Email Client</a>");
                                } else if (cinfo.getSflag() == 3) {
                                    sb.append("<a href=\"javascript: getSelectCandidate('" + cinfo.getName().replaceAll("'", "") + "','" + cinfo.getCountry() + "','" + clientselection.changeNum(jobpostId, 6) + "','" + cinfo.getShortlistId() + "');\">Select</a>");
                                } else if ((cinfo.getSflag() == 4 && cinfo.getSubstatus() == 1) && companytype == 1) {
                                    sb.append("<a href=\"javascript: getGenerateoffer('" + cinfo.getShortlistId() + "','" + cinfo.getSflag() + "','" + cinfo.getCandidateId() + "','" + cinfo.getSubstatus() + "');\">Generate Offer</a>");
                                } else if ((cinfo.getSflag() == 4 && cinfo.getSubstatus() == 2) && companytype == 1) {
                                    sb.append("<a href=\"javascript: ;\" onclick=\"javascript: getModelforoffer('" + cinfo.getShortlistId() + "', '" + cinfo.getSflag() + "','" + cinfo.getSubstatus() + "');\" data-bs-toggle='modal' data-bs-target='#mail_modal'>Email Candidate</a>");
                                } else if ((cinfo.getSflag() == 4 && cinfo.getSubstatus() == 3) && companytype == 1) {
                                    sb.append("<a href=\"javascript:;\" onclick=\"getsetModal('" + cinfo.getShortlistId() + "','" + cinfo.getCandidateId() + "','0','0');\" data-bs-toggle='modal' data-bs-target='#acc_offer_letter_modal'>Accept</a>");
                                } else if ((cinfo.getSflag() == 4 && cinfo.getSubstatus() == 4) && companytype == 1) {
                                    sb.append("<a class='accepted_label' href=\"javascript:;\" >Accepted</a>");
                                } else if ((cinfo.getSflag() == 4 && cinfo.getSubstatus() == 5) && companytype == 1) {
                                    sb.append("<a class='declined_label' href=\"javascript:;\" >Declined</a>");
                                }
                                sb.append("</div>");
                                if ((cinfo.getSflag() == 4 && (cinfo.getSubstatus() == 4 || cinfo.getSubstatus() == 5)) && companytype == 2) {
                                    sb.append("<div class='com_view_job'>");
                                    sb.append("<a href=\"javascript:;\" data-bs-toggle='modal' data-bs-target='#view_pdf' onclick=\"javascript: setIframe('" + (cvfile_path + cinfo.getOfferpdffile()) + "');\"><img src='../assets/images/view.png'/><br> View Offer Letter</a>");
                                    sb.append("</div>");
                                }
                                if (cinfo.getSflag() == 2 || cinfo.getSflag() == 3 || cinfo.getSflag() == 4 || cinfo.getSflag() == 5) {
                                    classAddone = "";
                                    if (cinfo.getSflag() == 2 || (cinfo.getSflag() == 4 && (cinfo.getSubstatus() == 1 || cinfo.getSubstatus() == 2 || cinfo.getSubstatus() == 4 || cinfo.getSubstatus() == 5 || companytype == 2)) || cinfo.getSflag() == 5) {
                                        classAddone = "1";
                                    }
                                    sb.append("<div class='search_view_prof com_view_job with_reject" + classAddone + " d-none1'>");
                                    if ((cinfo.getSflag() == 2 || cinfo.getSflag() == 3) && companytype == 1) {
                                        sb.append("<a href=\"javascript: getGenerateCV('" + cinfo.getShortlistId() + "','" + cinfo.getSflag() + "','" + cinfo.getCandidateId() + "');\"><img src='../assets/images/view.png'><br> View Generated CV</a>");
                                    } else if (((cinfo.getSflag() == 4 && cinfo.getSubstatus() == 1) || cinfo.getSflag() == 5) || companytype == 2) {
                                        sb.append("<a href=\"javascript:;\" data-bs-toggle='modal' data-bs-target='#view_pdf' onclick=\"javascript: setIframe('" + (cvfile_path + cinfo.getPdffileName()) + "');\"><img src='../assets/images/view.png'/><br>");
                                        if (companytype == 1) {
                                            sb.append("View Generated CV</a>");
                                        } else {
                                            sb.append("View CV</a>");
                                        }
                                    } else if ((cinfo.getSflag() == 4 && (cinfo.getSubstatus() == 1 || cinfo.getSubstatus() == 2 || cinfo.getSubstatus() == 3)) && companytype == 1) {
                                        sb.append("<a href=\"javascript: getGenerateoffer('" + cinfo.getShortlistId() + "','" + cinfo.getSflag() + "','" + cinfo.getCandidateId() + "','" + cinfo.getSubstatus() + "');\"><img src='../assets/images/view.png'/><br> View Offer Letter</a>");
                                    } else if ((cinfo.getSflag() == 4 && (cinfo.getSubstatus() == 4 || cinfo.getSubstatus() == 5)) && companytype == 1) {
                                        sb.append("<a href=\"javascript:;\" data-bs-toggle='modal' data-bs-target='#view_pdf' onclick=\"javascript: setIframe('" + (cvfile_path + cinfo.getOfferpdffile()) + "');\"><img src='../assets/images/view.png'/><br> View Offer Letter</a>");
                                    }
                                    sb.append("</div>");
                                }
                                if (cinfo.getSflag() == 3) {
                                    sb.append("<div class='search_view_prof client_reject'>");
                                    sb.append("<a href=\"javascript:;\" onclick=\"getsetModal('" + cinfo.getShortlistId() + "','" + cinfo.getCandidateId() + "','0','0');\" data-bs-toggle='modal' data-bs-target='#reject_modal'>Reject</a>");
                                    sb.append("</div>");
                                } else if ((cinfo.getSflag() == 4 && cinfo.getSubstatus() == 3) && companytype == 1) {
                                    sb.append("<div class='search_view_prof client_reject'>");
                                    sb.append("<a href=\"javascript:;\" onclick=\"getsetModal('" + cinfo.getShortlistId() + "','" + cinfo.getCandidateId() + "', '" + cinfo.getSflag() + "', '" + cinfo.getSubstatus() + "');\" data-bs-toggle='modal' data-bs-target='#dec_offer_letter_modal'>Decline</a>");
                                    sb.append("</div>");
                                }
                                sb.append("</div>");
                                sb.append("</div>");
                                sb.append("</div>");
                                sb.append("</div>");
                                sb.append("</li>");
                            }
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