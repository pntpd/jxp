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
                int shortlistId = Integer.parseInt(shortlistids);
                OnboardingInfo maininfo = onboarding.getSelectedCandidatedetailsByid(shortlistId);
                OnboardingInfo reqdocinfo = onboarding.getReqDocListId(shortlistId);
                ArrayList reqdocList = onboarding.getDocumentList();
                StringBuffer sb = new StringBuffer();
                sb.append("<h2>REQUIRED DOCUMENTS - ONBOARDING</h2>");
                sb.append("<div class='row client_position_table requ_docu_list'>");
                sb.append("<ul>");
                if (reqdocinfo.getReqdocId() == null || reqdocinfo.getReqdocId().equals("")) 
                {
                    if (reqdocList.size() > 0)
                    {
                        for (int i = 0; i < reqdocList.size(); i++) 
                        {
                            OnboardingInfo info = (OnboardingInfo) reqdocList.get(i);
                            if (info != null) 
                            {
                                sb.append("<li>");
                                sb.append("<label>" + (info.getDdlLabel() != null && !info.getDdlLabel().equals("") ? info.getDdlLabel() : "&nbsp;") + "</label>");
                                sb.append("<span class='switch_bth'>");
                                sb.append("<div class='form-check form-switch'>");
                                sb.append("<input class='form-check-input' type='checkbox' role='switch' id='chkreqdocListId" + i + "' name='chkreqdocListId' value='" + info.getDdlValue() + "' checked='true' />");
                                sb.append("</div>");
                                sb.append("</span>");
                                sb.append("</li>");
                            }
                        }
                    } else {
                        sb.append("<li>");
                        sb.append("<label>No information available.</label>");
                        sb.append("</li>");
                    }
                } else {
                    if (reqdocList.size() > 0) 
                    {
                        String strSelect = "";
                        for (int i = 0; i < reqdocList.size(); i++)
                        {
                            strSelect = "";
                            OnboardingInfo info = (OnboardingInfo) reqdocList.get(i);
                            if (info != null) 
                            {
                                sb.append("<li>");
                                sb.append("<label>" + (info.getDdlLabel() != null && !info.getDdlLabel().equals("") ? info.getDdlLabel() : "&nbsp;") + "</label>");
                                sb.append("<span class='switch_bth'>");
                                sb.append("<div class='form-check form-switch'>");
                                if (reqdocinfo.getReqdocId() != null) 
                                {
                                    if (onboarding.checkToStr(reqdocinfo.getReqdocId(), "" + info.getDdlValue())) 
                                    {
                                        strSelect = "checked";
                                    }
                                }
                                sb.append("<input class='form-check-input' type='checkbox' role='switch' id='chkreqdocListId" + i + "' name='chkreqdocListId' value='" + info.getDdlValue() + "' " + strSelect + " />");
                                sb.append("</div>");
                                sb.append("</span>");
                                sb.append("</li>");
                            }
                        }
                    } else {
                        sb.append("<li>");
                        sb.append("<label>No information available.</label>");
                        sb.append("</li>");
                    }
                }
                sb.append("</ul>");
                sb.append("</div>");
                if (maininfo.getOnboardflag() < 3) 
                {
                    sb.append("<div class='row'>");
                    sb.append("<div class='col-lg-12 col-md-12 col-sm-12 col-12 text-center' id='savediv'>");
                    sb.append("<a href=\"javascript: submitReqDoc();\" class='save_page'><img src='../assets/images/save.png'> Save</a>");
                    sb.append("<a href=\"javascript: addReqDocForm();\" class='add_more_list'><img src='../assets/images/add_list.png'></a>");
                    sb.append("</div>");
                }
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