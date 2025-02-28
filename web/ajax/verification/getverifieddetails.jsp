<%@page contentType="text/html"%>
<%@page language="java" import="java.util.ArrayList"%>
<%@page pageEncoding="UTF-8"%>
<%@page import="com.web.jxp.verification.VerificationInfo"%>
<%@page import="com.web.jxp.user.UserInfo"%>
<jsp:useBean id="verification" class="com.web.jxp.verification.Verification" scope="page"/>
<jsp:useBean id="vobj" class="com.web.jxp.base.Validate" scope="page"/>
<%
    try 
    {
        if (session.getAttribute("LOGININFO") != null) 
        {
            UserInfo uinfo = ((UserInfo) request.getSession().getAttribute("LOGININFO"));
            String addper = "N";
            if (uinfo != null) 
            {
                addper = uinfo.getAddper() != null ? uinfo.getAddper() : "N";
            }
            String file_path = verification.getMainPath("view_candidate_file");
            String candidateIds = request.getParameter("candidateId") != null && !request.getParameter("candidateId").equals("") ? vobj.replaceint(request.getParameter("candidateId")) : "-1";
            String tabNos = request.getParameter("tabNo") != null && !request.getParameter("tabNo").equals("") ? vobj.replaceint(request.getParameter("tabNo")) : "1";
            String childIds = request.getParameter("childId") != null && !request.getParameter("childId").equals("") ? vobj.replaceint(request.getParameter("childId")) : "-1";
            String documentName = request.getParameter("documentName") != null && !request.getParameter("documentName").equals("") ? vobj.replacename(request.getParameter("documentName")) : "";
            String coursename = request.getParameter("coursename") != null && !request.getParameter("coursename").equals("") ? (request.getParameter("coursename")) : "";
            String alertIds = request.getParameter("alertId") != null && !request.getParameter("alertId").equals("") ? vobj.replaceint(request.getParameter("alertId")) : "0";
            int alertId = Integer.parseInt(alertIds);
            int can = Integer.parseInt(candidateIds);
            int tab = Integer.parseInt(tabNos);
            int chil = Integer.parseInt(childIds);

            ArrayList list = verification.getAddedList(can, tab, chil);
            String strfile = "";
            if ( tab != 8) {
                strfile = verification.getFilename(can, tab, chil);
            }
            int size = list.size();
            String exist = "No";
            if (strfile != null && !strfile.equals("")) {
                exist = "Yes";
            }

            StringBuilder sb = new StringBuilder();
            sb.append("<div class='col-lg-" + (exist == "Yes" ? "6" : "12") + "'>");
            sb.append("<h2>VERIFICATION</h2>");
            sb.append("<input type='hidden' name='alertId' value='"+alertId+"'/>");
            sb.append("<div class='row ver_work_area'>");
            sb.append("<div class='col-lg-12 col-md-12 col-sm-12 col-4 form_group'>");
            sb.append("<div class='mt-radio-list'>");
            sb.append("<div class='form-check '>");
            sb.append("<input type='hidden' name='rbverification' vaule='0'/>");
            sb.append("<input class='form-check-input' type='checkbox' id='stcb1' value='2' name='stcb1' onchange=\"javascript: setStatuscb('1');\" />");
            sb.append("&nbsp;<label class='form-check-label' for='rbcomverified'>Verified</label>");
            sb.append("</div>");
            sb.append("<div class='form-check'>");
            sb.append("<input class='form-check-input' type='checkbox' id='stcb2' value='1' name='stcb2' onchange=\"javascript: setStatuscb('2');\"/>");
            sb.append("&nbsp;<label class='form-check-label' for='rbminverified'>Minimum Verified</label>");
            sb.append("</div>");
            sb.append("</div>");
            sb.append("</div>");
            sb.append("<div class='col-lg-12 col-md-12 col-sm-12 col-4 form_group'>");
            sb.append("<label class='form_label'>Verification Authority</label>");
            sb.append("<input class='form-control' name='verificatonauthority' id='verificatonauthority' value=''/>");
            sb.append("</div>");
            sb.append("<div class='col-lg-12 col-md-12 col-sm-12 col-4 form_group'>");
            sb.append("<label class='form_label'>Verified Document Upload (optional) (5MB) (.pdf/.docx)</label>");
            sb.append("<input type='file' name='verifiedfile' id='upload1' onchange=\"javascript: setClass('1');\" />");
            sb.append("<a href='javascript:;' id='upload_link_1' class='attache_btn uploaded_img1 fix_width'><i class='fas fa-paperclip'></i> Attach</a>");
            sb.append("</div>");
            sb.append("<div class='col-lg-12 col-md-12 col-sm-12 col-4 form_group'>");
            sb.append("<label class='form_label'>Instruction Note</label>");
            sb.append("<span class='form-control'>" + verification.getChecklist(tab) + " </span>");
            sb.append("</div>");
            if (size > 0) 
            {
                sb.append("<div class='col-lg-12 col-md-12 col-sm-12 col-4 form_group'>");
                sb.append("<label class='form_label'>Verification Details</label>");
                sb.append("<div class='full_width veri_details'>");

                for (int i = 0; i < size; i++) 
                {
                    VerificationInfo info = (VerificationInfo) list.get(i);
                    if (info != null) 
                    {
                        sb.append("<ul>");
                        sb.append("<li>" + (info.getDate() != null && !info.getDate().equals("") ? info.getDate() : "") + "</li>");
                        sb.append("<li>" + (info.getName() != null && !info.getName().equals("") ? info.getName() : "") + "</li>");
                        sb.append("<li>" + (info.getStatus() > 0 ? verification.getStatusById(info.getStatus()) : "") + "</li>");
                        sb.append("<li>" + (info.getFilename() != null && !info.getFilename().equals("") ? "<a href ='javascript: void(0);' class='mr_15'  onclick=\"javascript: openm('" + file_path + info.getFilename() + "');\"><img src ='../assets/images/attachment.png'/></a>" : "") + "</li>");
                        sb.append("</ul>");
                    }
                }

                sb.append("</div>");
                sb.append("</div>");
            }
            sb.append("</div>");
            sb.append("<div class='row'>");
            if(addper.equals("Y")){
            sb.append("<div class='col-lg-12 col-md-12 col-sm-12 col-12 text-center'><a href='javascript:submitForm();' class='save_page verify_btn'><img src='../assets/images/shield.png'> Verify</a></div>");
            }
            sb.append("<input type='hidden' name='tabId' id='tabId' value='" + tabNos + "'/>");
            sb.append("<input type='hidden' name='childId' id='childId' value='" + childIds + "' />");
            sb.append("<input type='hidden' name='documentName' id='documentName' value='" + documentName + "' />");
            sb.append("<input type='hidden' name='coursename' id='coursename' value='" + coursename + "' />");
            sb.append("</div>");
            sb.append("</div>");
            sb.append("</div>");
            if (exist == "Yes") {
                String classname = "";
                if (strfile.contains(".doc") || strfile.contains(".docx")) {
                    strfile = "https://docs.google.com/gview?url=" + file_path + strfile + "&embedded=true";
                    classname = "doc_mode";
                } else if (strfile.contains(".pdf")) {
                    strfile = file_path + strfile;
                    classname = "pdf_mode";
                } else {
                    strfile = file_path + strfile;
                    classname = "img_mode";
                }
                sb.append("<div class='col-lg-6 iframe_div text-center'>");
                sb.append("<iframe id='iframe' class='" + classname + "' src='" + strfile + "'></iframe>");
                sb.append("</div>");
            }
            String st1 = sb.toString() + "#@#" + exist;
            sb.setLength(0);
            response.getWriter().write(st1);
        } else {
            response.getWriter().write("Please check your login session....");
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
%>