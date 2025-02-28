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
            if (request.getParameter("clientid") != null && request.getParameter("assetid") != null) 
            {
                String search = request.getParameter("search") != null && !request.getParameter("search").equals("") ? vobj.replacename(request.getParameter("search")) : "";
                String clientids = request.getParameter("clientid") != null && !request.getParameter("clientid").equals("") ? vobj.replaceint(request.getParameter("clientid")) : "0";
                String clientassetids = request.getParameter("assetid") != null && !request.getParameter("assetid").equals("") ? vobj.replaceint(request.getParameter("assetid")) : "0";
                String positionids = request.getParameter("positionid") != null && !request.getParameter("positionid").equals("") ? vobj.replaceint(request.getParameter("positionid")) : "0";
                int clientId = Integer.parseInt(clientids);
                int assetId = Integer.parseInt(clientassetids);
                int positionId = Integer.parseInt(positionids);

                ArrayList crcand_list = mobilization.getCandidatesByclientandAssetName(search, clientId, assetId, positionId);
                int total = crcand_list.size();

                StringBuffer sb = new StringBuffer();
                String file_path = mobilization.getMainPath("view_candidate_file");
                String cphoto = "";
                if (total > 0) 
                {
                    MobilizationInfo minfo = null;
                    for (int i = 0; i < total; i++)
                    {
                        cphoto = "../assets/images/empty_user.png";
                        minfo = (MobilizationInfo) crcand_list.get(i);
                        if (minfo != null)
                        {
                            if (!minfo.getPhoto().equals("")) 
                            {
                                cphoto = file_path + minfo.getPhoto();
                            }
                            sb.append("<li class='odd_list_1'>");
                            sb.append("<div class='search_box'>");
                            sb.append("<div class='row flex-end align-items-center'>");
                            sb.append("<div class='col-md-11 comp_view'>");
                            sb.append("<div class='row'>");
                            sb.append("<div class='col-md-12 client_prof_status'>");
                            sb.append("<div class='row d-flex align-items-start'>");
                            sb.append("<div class='col-md-2 com_view_prof cand_box_img'>");
                            sb.append("<div class='user_photo pic_photo'>");
                            sb.append("<div class='upload_file'>");
                            sb.append("<img src='" + cphoto + "'>");
                            sb.append("<a href='javascript:;' onclick=\"viewCandidate('" + minfo.getCandidateId() + "');\"><img src='../assets/images/view.png'></a>");
                            sb.append("</div>");
                            sb.append("</div>	");
                            sb.append("</div>");
                            sb.append("<div class='col-md-10'>");
                            sb.append("<div class='row'>");
                            sb.append("<div class='portlet box status_on_hold'>");
                            sb.append("<div class='portlet-title'>");
                            sb.append("<div class='caption'>" + minfo.getName());
                            if (!minfo.getName().equals("") && (!minfo.getPosition().equals("") || !minfo.getGrade().equals(""))) {
                                sb.append("|");
                            }
                            sb.append(minfo.getPosition() + "</div>");
                            sb.append("</div>");
                            sb.append("<div class='portlet-body'>");
                            sb.append("<div class='row'>");
                            sb.append("<div class='col-md-12 status_ic_checkbox'>");
                            sb.append("<ul>");
                            sb.append("<li class='fill_trav_accom'>");
                            sb.append("<a href='javascript:;' onclick=\"getTraveldetails('" + minfo.getCrewrotationId() + "', '1');\">");
                            sb.append("<div class='status_ic " + (minfo.getFlag1() == 1 ? "req_information" : "") + "'><span><i class='ion ion-ios-briefcase'></i></span></div>");
                            sb.append("<div class='form-check permission-check'>");
                            sb.append("<span>Fill Travel Details</span>");
                            sb.append("</div>");
                            sb.append("</a>");
                            sb.append("<a href='javascript:void(0);' onclick=\"getmobtraveldtls('" + minfo.getCrewrotationId() + "', '1');\" class='fill_tr_acc_det'>View Details</a>");
                            sb.append("</li>");
                            sb.append("<li class='fill_trav_accom'>");
                            sb.append("<a href='javascript:;' onclick=\"getAccommDetails('" + minfo.getCrewrotationId() + "', '2');\">");
                            sb.append("<div class='status_ic " + (minfo.getFlag2() == 1 ? "req_information" : "") + "'><span><i class='fas fa-bed'></i></span></div>");
                            sb.append("<div class='form-check permission-check'>");
                            sb.append("<span>Fill Accommodation Details</span>");
                            sb.append("</div>");
                            sb.append("</a>");
                            sb.append("<a href='javascript:void(0);' onclick=\"getmobaccommdtls('" + minfo.getCrewrotationId() + "', '2');\" class='fill_tr_acc_det'>View Details</a>");
                            sb.append("</li>");
                            sb.append("</ul>");
                            sb.append("</div>");
                            sb.append("</div>");
                            sb.append("</div>");
                            sb.append("</div>");
                            sb.append("</div>");
                            sb.append("</div>");
                            sb.append("</div>");
                            sb.append("</div>");
                            sb.append("</div>");
                            sb.append("</div>");
                            sb.append("<div class='col-md-1 add_view_area client_se_vi_re'>");
                            sb.append("<div class='row'>");
                            sb.append("<div class='search_add_btn circle_btn'><a href='javascript:void(0);' onclick=\"getMailDetails('" + minfo.getCrewrotationId() + "');\" class=''><i class='mdi mdi-email-outline'></i></a></div>");
                            sb.append("</div>");
                            sb.append("</div>");
                            sb.append("</div>");
                            sb.append("</div>");
                            sb.append("</li>");
                        }
                    }
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