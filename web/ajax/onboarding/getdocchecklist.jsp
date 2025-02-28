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
            String str = "";
            if (request.getParameter("shortlistid") != null) 
            {
                String shortlistids = request.getParameter("shortlistid") != null && !request.getParameter("shortlistid").equals("") ? vobj.replaceint(request.getParameter("shortlistid")) : "0";
                String from = request.getParameter("from") != null && !request.getParameter("from").equals("") ? vobj.replacename(request.getParameter("from")) : "";
                int shortlistId = Integer.parseInt(shortlistids);
                OnboardingInfo reqdocinfo = onboarding.getReqDocListId(shortlistId);
                ArrayList doccheckList = new ArrayList();
                StringBuffer sb = new StringBuffer();
                if (reqdocinfo != null) 
                {
                    if (reqdocinfo.getReqdocId() != null) 
                    {
                        doccheckList = onboarding.getDocumentCheckList(reqdocinfo.getReqdocId());
                    }
                    sb.append("<h2>REQUIRED DOCUMENTS CHECKLIST</h2>");
                    sb.append("<div class='row client_position_table req_doc_check'>");
                    sb.append("<ul>");
                    if (doccheckList.size() > 0) 
                    {
                        String strSelect = "";
                        for (int i = 0; i < doccheckList.size(); i++) 
                        {
                            OnboardingInfo info = (OnboardingInfo) doccheckList.get(i);
                            if (info != null) 
                            {
                                strSelect = "";
                                if (reqdocinfo.getReqchecklistId() != null) 
                                {
                                    if (onboarding.checkToStr(reqdocinfo.getReqchecklistId(), "" + info.getDdlValue()))
                                    {
                                        strSelect = "checked";
                                    }
                                }
                                sb.append("<li>");
                                sb.append("<div class='mt-checkbox-list'>");
                                sb.append("<label class='mt-checkbox mt-checkbox-outline'>");
                                sb.append("<input type='checkbox' value='" + info.getDdlValue() + "' id='chkdoccheckListId" + i + "' name='chkdoccheckListId' " + strSelect + ">");
                                sb.append("<span></span>");
                                sb.append("</label>");
                                sb.append("</div>");
                                sb.append("<span class='check_list_text'>" + (info.getDdlLabel() != null && !info.getDdlLabel().equals("") ? info.getDdlLabel() : "&nbsp;") + "</span>");
                                sb.append("</li>");
                            }
                        }
                    } else {
                        sb.append("<li>");
                        sb.append("<div class='mt-checkbox-list'>");
                        sb.append("<label>No information available.</label>");
                        sb.append("</div>");
                        sb.append("</li>");
                    }
                    sb.append("</ul>");
                    sb.append("</div>");
                    sb.append("<div class='row'>");
                    if (from.equals("view")) {
                    } else {
                        sb.append("<div class='col-lg-12 col-md-12 col-sm-12 col-12 text-center'>");
                        sb.append("<a href=\"javascript: submitDocCheckList();\" class='save_page'><img src='../assets/images/save.png'> Save</a>");
                        sb.append("</div>");
                    }
                        sb.append("<input type='hidden' id='reqcheckstatus' name='reqcheckstatus' value=''/>");
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