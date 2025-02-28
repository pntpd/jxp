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
            UserInfo uinfo = ((UserInfo) request.getSession().getAttribute("LOGININFO"));
            String str = "", editper = "N";
            if (uinfo != null) 
            {
                editper = uinfo.getEditper() != null ? uinfo.getEditper() : "N";
            }

            if (request.getParameter("clientid") != null && request.getParameter("clientassetId") != null) 
            {
                String clientids = request.getParameter("clientid") != null && !request.getParameter("clientid").equals("") ? vobj.replaceint(request.getParameter("clientid")) : "0";
                String clientassetids = request.getParameter("clientassetId") != null && !request.getParameter("clientassetId").equals("") ? vobj.replaceint(request.getParameter("clientassetId")) : "0";
                int clientId = Integer.parseInt(clientids);
                int clientassetId = Integer.parseInt(clientassetids);
                ArrayList doccheckList = new ArrayList();
                doccheckList = mobilization.getDocListId(clientId, clientassetId);
                StringBuffer sb = new StringBuffer();
                sb.append("<h2>REQUIRED DOCUMENTS - MOBILIZATION</h2>");
                if (doccheckList.size() > 0) 
                {
                    sb.append("<div class='row client_position_table'>");
                    sb.append("<div class='col-lg-12 col-md-12 col-sm-12 col-12'>");
                    sb.append("<div class='table-responsive mb-0'>");
                    sb.append("<table class='table table-striped'>");
                    
                    MobilizationInfo info = null;
                    for (int i = 0; i < doccheckList.size(); i++) 
                    {
                        info = (MobilizationInfo) doccheckList.get(i);
                        if (info != null)
                        {
                            sb.append("<tr><td>" + info.getDdlLabel() + "</td></tr>");
                        }
                    }
                    sb.append("</table>");
                    sb.append("</div>");
                    sb.append("</div>");
                    sb.append("</div>");
                }

                sb.append("<div class='row'>");
                sb.append("<div class='col-lg-12 col-md-12 col-sm-12 col-12 text-center'>");
                sb.append("<a href='javascript:;' class='save_page' data-bs-dismiss='modal' aria-hidden='true'>OK</a>");
                if (editper.equals("Y")) {
                    sb.append("<a href='javascript:;' onclick=\"viewDocCheck('" + clientId + "','" + clientassetId + "')\" class='edit_page'><img src='../assets/images/edit.png'></a>");
                }
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