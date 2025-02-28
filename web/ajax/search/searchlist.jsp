<%@page contentType="text/html"%>
<%@page language="java" import="java.util.ArrayList"%>
<%@page import="com.web.jxp.user.UserInfo" %>
<%@page import="com.web.jxp.candidate.CandidateInfo" %>
<jsp:useBean id="client" class="com.web.jxp.candidate.Autofill" scope="page"/>
<%
    try
    {
        if(session.getAttribute("LOGININFO") != null)
        {
            int allclient = 0;
            UserInfo uinfo = ((UserInfo) request.getSession().getAttribute("LOGININFO"));
            String  str = "", cids = "", assetids = "";
            if (uinfo != null) 
            {
                cids = uinfo.getCids();
                allclient = uinfo.getAllclient();
                assetids = uinfo.getAssetids();                
            }  
            if(request.getParameter("val") != null)
            {
                String strval = request.getParameter("val") != null ? request.getParameter("val") : "";

                ArrayList list = client.getHeaderAutoFill(strval, allclient, cids, assetids);
                response.setContentType("text/html");
                response.setHeader("Cache-Control", "no-cache");
             
                StringBuffer sb = new StringBuffer();
                sb.append("<ul id='cul' tabindex='1000' style='padding:0px; margin: 0px;list-style: none;position: absolute;border:1px solid #e1e1e1;background-color:#FFF;color: black;text-align:left;font-size:11px;font-family:Arial, Helvetica, sans-serif;z-index:100;'>");                
                int listsize = list.size();
                if(listsize > 0)
                {
                    int id, tabindex = 1000;
                    String name;
                    for(int i = 0; i < listsize; i++)
                    {
                        CandidateInfo info = (CandidateInfo) list.get(i);
                        if(info != null)
                        {
                            ++tabindex;
                            id = info.getCandidateId();
                            name = info.getName() != null ? info.getName().replaceAll("'", "\\\\'") : "";
                            sb.append("<li tabindex='"+(tabindex)+"' style='padding: 3px; border-bottom: 1px solid #e4e4e4; cursor: pointer;' onkeypress=\"javascript: handleKeyHeader(event, '"+id+"', '"+name+"');\" onclick = \"javascript: setCandidateNameHeader('"+id+"', '"+name+"');\">"+name+"</li>");
                        }
                    }
                }
                sb.append("<li aligh='right' class='cancel_button'><a href='javascript:;' onclick=\"javascript: clearheader();\">Cancel</a></li>");
                sb.append("</ul>");
                //sb.append("<a class='cancel_button' href='javascript:;' onclick=\"javascript: clearheader();\">Cancel</a>");
                str = sb.toString();
                sb.setLength(0);
                response.getWriter().write(str);
            }
            else
            {
                response.setStatus(HttpServletResponse.SC_NO_CONTENT);
            }
        }
        else
        {
            response.getWriter().write("Please check your login session....");
        }
    }
    catch(Exception e)
    {
        e.printStackTrace();
    }
%>