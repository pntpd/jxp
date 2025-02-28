<%@page import="com.web.jxp.candidateregistration.CandidateRegistrationInfo"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html"%>
<%@page language="java" import="java.util.Base64"%>
<%@page import="com.web.jxp.candidate.*"%>
<jsp:useBean id="vobj" class="com.web.jxp.base.Validate" scope="page"/>
<jsp:useBean id="cr" class="com.web.jxp.candidateregistration.CandidateRegistration" scope="page"/>
<%
    try {
        String regEmailId = (String) request.getSession().getAttribute("REG_EMAILID");
        if (regEmailId != null) {
            StringBuilder sb1 = new StringBuilder();
            StringBuilder sb2 = new StringBuilder();
            StringBuilder sb3 = new StringBuilder();
            int size = 0;
            if (request.getParameter("editexp") != null) {
                String companyname = (String) request.getParameter("companyname") != null ? vobj.replacedesc(request.getParameter("companyname")) : "";
                String exppositionIds = (String) request.getParameter("exppositionId") != null ? vobj.replaceint(request.getParameter("exppositionId")) : "0";
                int exppositionId = Integer.parseInt(exppositionIds);

                ArrayList list = new ArrayList();
                if (session.getAttribute("CANDREGWORKEXP") != null) {
                    list = (ArrayList) request.getSession().getAttribute("CANDREGWORKEXP");
                    request.getSession().removeAttribute("CANDREGWORKEXP");
                }
                if (list.size() > 0) {
                    CandidateRegistrationInfo info = null;
                    for (int i = 0; i < list.size(); i++) {
                        info = (CandidateRegistrationInfo) list.get(i);
                        if (info != null && info.getCompanyname().equals(companyname) && info.getExppositionId() == exppositionId) {
                            sb3.append((info.getCompanyname() != null ? info.getCompanyname() : "") + "#@#");
                            sb3.append((info.getAssetname() != null ? info.getAssetname() : "") + "#@#");
                            sb3.append((info.getWorkstartdate() != null ? info.getWorkstartdate() : "") + "#@#");
                            sb3.append((info.getCurrentworkingstatus()) + "#@#");
                            sb3.append((info.getPastOCSemp()) + "#@#");
                            sb3.append((info.getExpassettypeId()) + "#@#");
                            sb3.append((info.getExpassettype() != null ? info.getExpassettype() : "") + "#@#");
                            sb3.append((info.getExppositionId()) + "#@#");
                            sb3.append((info.getExpposition() != null ? info.getExpposition() : "") + "#@#");
                            sb3.append((info.getWorkenddate() != null ? info.getWorkenddate() : ""));
                            list.remove(info);
                        }
                    }
                }
                size = list.size();
                request.getSession().setAttribute("CANDREGWORKEXP", list);
                if (list.size() > 0) {
                    CandidateRegistrationInfo info = null;
                    String compName = "";
                    for (int i = 0; i < list.size(); i++) {
                        info = (CandidateRegistrationInfo) list.get(i);
                        if (info != null && info.getExppositionId() > 0) {
                            compName = info.getCompanyname() != null ? info.getCompanyname() : "";
                            sb1.append("<tr>");
                            sb1.append("<td>" + compName + "</td>");
                            sb1.append("<td>" + (info.getExpassettype() != null ? info.getExpassettype() : "") + "</td>");
                            sb1.append("<td>" + (info.getAssetname() != null ? info.getAssetname() : "") + "</td>");
                            sb1.append("<td>" + (info.getExpposition() != null ? info.getExpposition() : "") + "</td>");
                            sb1.append("<td>" + (info.getWorkstartdate() != null ? info.getWorkstartdate() : "") + "</td>");
                            sb1.append("<td>" + (info.getWorkenddate() != null ? info.getWorkenddate() : "") + "</td>");
                            sb1.append("<td>" + (info.getPastOCSemp() == 1 ? "Yes" : "No") + "</td>");
                            sb1.append("<td>" + (info.getCurrentworkingstatus() == 1 ? "Yes" : "No") + "</td>");
                            sb1.append("<td>");
                            sb1.append("<div class='d-flex mr-15'>");
                            sb1.append("<a href=\"javascript:;\" onclick=\"getEditExp('" + compName + "', '" + info.getExppositionId() + "');\" ><img src='../assets/pnt-images/edit-icon.svg'/></a>&nbsp;");
                            sb1.append("<a href=\"javascript:;\" onclick=\"getRemoveExp('" + compName + "', '" + info.getExppositionId() + "');\" ><img src='../assets/pnt-images/cancel-icon.svg'/></a>");
                            sb1.append("</div>");
                            sb1.append("</td>");
                            sb1.append("</tr>");
//                            ---------------------------------- Mob -------------------------------------------------------
                            sb2.append("<div class='mob-Experience mb-2'>");
                            sb2.append("<div class='row'>");
                            sb2.append("<div class='d-flex justify-content-end'>");
                            sb2.append("<div class='d-flex gap-2'>");
                            sb2.append("<div><a href=\"javascript:;\" onclick=\"getEditExp('" + compName + "', '" + info.getExppositionId() + "');\" class='mr_15'><img src='../assets/pnt-images/edit-icon.svg'/></a></div>");
                            sb2.append("<div><a href=\"javascript:;\" onclick=\"getRemoveExp('" + compName + "', '" + info.getExppositionId() + "');\" class='mr_15'><img src='../assets/pnt-images/cancel-icon.svg'/></a>");
                            sb2.append("</div>");
                            sb2.append("</div>");
                            sb2.append("</div>");
                            sb2.append("<div class='col-12 mb-2'>");
                            sb2.append("<div class='d-flex flex-column'>");
                            sb2.append("<div>");
                            sb2.append("<span class='gray'> Company Name</span>");
                            sb2.append("</div>");
                            sb2.append("<div>");
                            sb2.append("<span class='black'>" + compName + "</span>");
                            sb2.append("</div>");
                            sb2.append("</div>");
                            sb2.append("</div>");
                            sb2.append("<div class='col-12 mb-2'>");
                            sb2.append("<div class='d-flex gap-2'>");
                            sb2.append("<div class='col-6'>");
                            sb2.append("<div class='d-flex flex-column'>");
                            sb2.append("<div>");
                            sb2.append("<span class='gray'> Asset Type</span>");
                            sb2.append("</div>");
                            sb2.append("<div>");
                            sb2.append("<span class='black'>" + (info.getExpassettype() != null ? info.getExpassettype() : "") + "</span>");
                            sb2.append("</div>");
                            sb2.append("</div>");
                            sb2.append("</div>");
                            sb2.append("<div class='col-6'>");
                            sb2.append("<div class='d-flex flex-column'>");
                            sb2.append("<div>");
                            sb2.append("<span class='gray'> Asset Name</span>");
                            sb2.append("</div>");
                            sb2.append("<div>");
                            sb2.append("<span class='black'>" + (info.getAssetname() != null ? info.getAssetname() : "") + "</span>");
                            sb2.append("</div>");
                            sb2.append("</div>");
                            sb2.append("</div>");
                            sb2.append("</div>");
                            sb2.append("</div>");
                            sb2.append("<div class='col-12 mb-2'>");
                            sb2.append("<div class='d-flex flex-column'>");
                            sb2.append("<div>");
                            sb2.append("<span class='gray'> Position</span>");
                            sb2.append("</div>");
                            sb2.append("<div>");
                            sb2.append("<span class='black'>" + info.getExpposition() != null ? info.getExpposition() : "" + "</span>");
                            sb2.append("</div>");
                            sb2.append("</div>");
                            sb2.append("</div>");
                            sb2.append("<div class='col-12 mb-2'>");
                            sb2.append("<div class='d-flex gap-2'>");
                            sb2.append("<div class='col-6'>");
                            sb2.append("<div class='d-flex flex-column'>");
                            sb2.append("<div>");
                            sb2.append("<span class='gray'> Start Date</span>");
                            sb2.append("</div>");
                            sb2.append("<div>");
                            sb2.append("<span class='black'>" + (info.getWorkstartdate() != null ? info.getWorkstartdate() : "") + "</span>");
                            sb2.append("</div>");
                            sb2.append("</div>");
                            sb2.append("</div>");
                            sb2.append("<div class='col-6'>");
                            sb2.append("<div class='d-flex flex-column'>");
                            sb2.append("<div>");
                            sb2.append("<span class='gray'> End Date</span>");
                            sb2.append("</div>");
                            sb2.append("<div>");
                            sb2.append("<span class='black'>" + (info.getWorkenddate() != null ? info.getWorkenddate() : "") + "</span>");
                            sb2.append("</div>");
                            sb2.append("</div>");
                            sb2.append("</div>");
                            sb2.append("</div>");
                            sb2.append("</div>");
                            sb2.append("<div class='col-12 mb-2'>");
                            sb2.append("<div class='d-flex gap-2'>");
                            sb2.append("<div class='col-6'>");
                            sb2.append("<div class='d-flex flex-column'>");
                            sb2.append("<div>");
                            sb2.append("<span class='gray'> Past OCS</span>");
                            sb2.append("</div>");
                            sb2.append("<div>");
                            sb2.append("<span class='black'>" + (info.getPastOCSemp() == 1 ? "Yes" : "No") + "</span>");
                            sb2.append("</div>");
                            sb2.append("</div>");
                            sb2.append("</div>");
                            sb2.append("<div class='col-6'>");
                            sb2.append("<div class='d-flex flex-column'>");
                            sb2.append("<div>");
                            sb2.append("<span class='gray'> Is Current</span>");
                            sb2.append("</div>");
                            sb2.append("<div>");
                            sb2.append("<span class='black'>" + (info.getCurrentworkingstatus() == 1 ? "Yes" : "No") + "</span>");
                            sb2.append("</div>");
                            sb2.append("</div>");
                            sb2.append("</div>");
                            sb2.append("</div>");
                            sb2.append("</div>");
                            sb2.append("</div>");
                            sb2.append("</div>");
                        }
                    }
                } else {
                    sb1.append("<tr>");
                    sb1.append("<td colspan='9'>" + cr.getMainPath("record_not_found") + "</td>");
                    sb1.append("</tr>");
//                            ---------------------------------- Mob -------------------------------------------------------
                    sb2.append("<div class='mob-Experience mb-2'>");
                    sb2.append("<div class='row'>");
                    sb2.append("<div class='col-12 mb-2'>");
                    sb2.append("<div class='d-flex flex-column'>");
                    sb2.append("<div>");
                    sb2.append("<span class='black'>" + cr.getMainPath("record_not_found") + "</span>");
                    sb2.append("</div>");
                    sb2.append("</div>");
                    sb2.append("</div>");
                    sb2.append("</div>");
                    sb2.append("</div>");

                }
            } else if (request.getParameter("editcert") != null) {
                String coursenameIds = (String) request.getParameter("coursenameId") != null ? vobj.replaceint(request.getParameter("coursenameId")) : "0";
                int coursenameId = Integer.parseInt(coursenameIds);

                ArrayList list = new ArrayList();
                if (session.getAttribute("CANDREGCERT") != null) {
                    list = (ArrayList) request.getSession().getAttribute("CANDREGCERT");
                    request.getSession().removeAttribute("CANDREGCERT");
                }
                if (list.size() > 0) {
                    CandidateRegistrationInfo info = null;
                    for (int i = 0; i < list.size(); i++) {
                        info = (CandidateRegistrationInfo) list.get(i);
                        if (info != null && info.getCoursenameId() == coursenameId) {
                            sb3.append((info.getIsexpiry()) + "#@#");
                            sb3.append((info.getCoursenameId()) + "#@#");
                            sb3.append((info.getCoursename() != null ? info.getCoursename() : "") + "#@#");
                            sb3.append((info.getDateofissue() != null ? info.getDateofissue() : "") + "#@#");
                            sb3.append((info.getDateofexpiry() != null ? info.getDateofexpiry() : "") + "#@#");
                            sb3.append((info.getApprovedbyId()) + "#@#");
                            sb3.append((info.getApprovedby() != null ? info.getApprovedby() : "") + "#@#");
                            sb3.append((info.getTempfname() != null ? info.getTempfname() : ""));
                            sb3.append((info.getTemplocalFile() != null ? info.getTemplocalFile() : "") + "#@#");
                            list.remove(info);
                        }
                    }
                }
                size = list.size();
                request.getSession().setAttribute("CANDREGCERT", list);
                if (list.size() > 0) {
                    CandidateRegistrationInfo info = null;
                    for (int i = 0; i < list.size(); i++) {
                        info = (CandidateRegistrationInfo) list.get(i);
                        if (info != null) {
                            sb1.append("<tr>");
                            sb1.append("<td>" + (info.getCoursename() != null ? info.getCoursename() : "") + "</td>");
                            sb1.append("<td>" + (info.getDateofissue() != null ? info.getDateofissue() : "") + "</td>");
                            sb1.append("<td>" + (info.getDateofexpiry() != null && info.getIsexpiry() == 0 ? info.getDateofexpiry() : "") + "</td>");
                            sb1.append("<td>" + (info.getIsexpiry() == 1 ? "Yes" : "No") + "</td>");
                            sb1.append("<td>" + (info.getApprovedby() != null && !info.getApprovedby().equals("- Select -") ? info.getApprovedby() : "") + "</td>");
                            sb1.append("<td>");
                            sb1.append("<div class='d-flex  mr-15'>");
                            sb1.append("<a href=\"javascript:;\" onclick=\"getEditCert('" + info.getCoursenameId() + "');\" ><img src='../assets/pnt-images/edit-icon.svg'/></a>&nbsp;");
                            sb1.append("<a href=\"javascript:;\" onclick=\"getRemoveCert('" + info.getCoursenameId() + "');\" ><img src='../assets/pnt-images/cancel-icon.svg'/></a>");
                            sb1.append("</div>");
                            sb1.append("</td>");
                            sb1.append("</tr>");
//                            ---------------------------------- Mob -------------------------------------------------------
                            sb2.append("<div class='mob-Experience mb-2'>");
                            sb2.append("<div class='row'>");
                            sb2.append("<div class='d-flex justify-content-end'>");
                            sb2.append("<div class='d-flex gap-2'>");
                            sb2.append("<div><a href=\"javascript:;\" onclick=\"getEditCert('" + info.getCoursenameId() + "');\" class='mr_15'><img src='../assets/pnt-images/edit-icon.svg'/></a></div>");
                            sb2.append("<div> <a href=\"javascript:;\" onclick=\"getRemoveCert('" + info.getCoursenameId() + "');\" class='mr_15'><img src='../assets/pnt-images/cancel-icon.svg'/></a></div>");
                            sb2.append("</div>");
                            sb2.append("</div>");
                            sb2.append("<div class='col-12 mb-2'>");
                            sb2.append("<div class='d-flex flex-column'>");
                            sb2.append("<div>");
                            sb2.append("<span class='gray'> Name Of certificate</span>");
                            sb2.append("</div>");
                            sb2.append("<div>");
                            sb2.append("<span class='black'>" + info.getCoursename() != null ? info.getCoursename() : "" + "</span>");
                            sb2.append("</div>");
                            sb2.append("</div>");
                            sb2.append("</div>");
                            sb2.append("<div class='col-12 mb-2'>");
                            sb2.append("<div class='d-flex gap-2'>");
                            sb2.append("<div class='col-6'>");
                            sb2.append("<div class='d-flex flex-column'>");
                            sb2.append("<div>");
                            sb2.append("<span class='gray'>Date Of Issue</span>");
                            sb2.append("</div>");
                            sb2.append("<div>");
                            sb2.append("<span class='black'>" + (info.getDateofissue() != null ? info.getDateofissue() : "") + "</span>");
                            sb2.append("</div>");
                            sb2.append("</div>");
                            sb2.append("</div>");
                            sb2.append("<div class='col-6'>");
                            sb2.append("<div class='d-flex flex-column'>");
                            sb2.append("<div>");
                            sb2.append("<span class='gray'>Date of Expiry</span>");
                            sb2.append("</div>");
                            sb2.append("<div>");
                            sb2.append("<span class='black'>" + (info.getDateofexpiry() != null && info.getIsexpiry() == 0 ? info.getDateofexpiry() : "") + "</span>");
                            sb2.append("</div>");
                            sb2.append("</div>");
                            sb2.append("</div>");
                            sb2.append("</div>");
                            sb2.append("</div>");
                            sb2.append("<div class='col-12 mb-2'>");
                            sb2.append("<div class='d-flex gap-2'>");
                            sb2.append("<div class='col-6'>");
                            sb2.append("<div class='d-flex flex-column'>");
                            sb2.append("<div>");
                            sb2.append("<span class='gray'>No Expiry</span>");
                            sb2.append("</div>");
                            sb2.append("<div>");
                            sb2.append("<span class='black'>" + (info.getIsexpiry() == 1 ? "Yes" : "No") + "</span>");
                            sb2.append("</div>");
                            sb2.append("</div>");
                            sb2.append("</div>");
                            sb2.append("<div class='col-6'>");
                            sb2.append("<div class='d-flex flex-column'>");
                            sb2.append("<div>");
                            sb2.append("<span class='gray'>Approved By</span>");
                            sb2.append("</div>");
                            sb2.append("<div>");
                            sb2.append("<span class='black'>" + (info.getApprovedby() != null && !info.getApprovedby().equals("- Select -") ? info.getApprovedby() : "") + "</span>");
                            sb2.append("</div>");
                            sb2.append("</div>");
                            sb2.append("</div>");
                            sb2.append("</div>");
                            sb2.append("</div>");
                            sb2.append("</div>");
                            sb2.append("</div>");
                        }
                    }
                } else {
                    sb1.append("<tr>");
                    sb1.append("<td colspan=6'>" + cr.getMainPath("record_not_found") + "</td>");
                    sb1.append("</tr>");
//                            ---------------------------------- Mob -------------------------------------------------------
                    sb2.append("<div class='mob-Experience mb-2'>");
                    sb2.append("<div class='row'>");
                    sb2.append("<div class='col-12 mb-2'>");
                    sb2.append("<div class='d-flex flex-column'>");
                    sb2.append("<div>");
                    sb2.append("<span class='black'>" + cr.getMainPath("record_not_found") + "</span>");
                    sb2.append("</div>");
                    sb2.append("</div>");
                    sb2.append("</div>");
                    sb2.append("</div>");
                    sb2.append("</div>");
                }
            }
            String str = sb1.toString().intern() + "#@#@#" + sb2.toString().intern() + "#@#@#" + sb3.toString().intern() + "#@#@#" + size;
            sb1.setLength(0);
            sb2.setLength(0);
            sb3.setLength(0);
            response.getWriter().write(str);
        } else {
            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
        }
    } catch (Exception e) {

    }
%>