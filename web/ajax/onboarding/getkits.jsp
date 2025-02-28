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
            if (request.getParameter("ids") != null) 
            {
                String Ids = request.getParameter("ids") != null && !request.getParameter("ids").equals("") ? vobj.replacename(request.getParameter("ids")) : "";
                ArrayList kitList = onboarding.getOnboardingkitById(Ids);
                int size = kitList.size();
                StringBuffer sb = new StringBuffer();
                sb.append("<h2>ONBOARD CANDIDATE</h2>");
                sb.append("<div class='row client_position_table req_doc_check'>");
                sb.append("<ul>");
                if (size > 0) 
                {
                    for (int i = 0; i < size; i++) 
                    {
                        OnboardingInfo info = (OnboardingInfo) kitList.get(i);
                        if (info != null) 
                        {
                            sb.append("<li>");
                            if (info.getDdlLabel() != null) 
                            {
                                sb.append("<span>" + info.getDdlLabel() + "</span>");
                            }
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
                sb.append("<div class='col-lg-12 col-md-12 col-sm-12 col-12 text-center'>");
                sb.append("<a href=\"javascript:;\" class='save_page' data-bs-dismiss='modal'>OK</a>");
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