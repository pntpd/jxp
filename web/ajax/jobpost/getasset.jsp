<%@page import="com.web.jxp.user.UserInfo"%>
<%@page contentType="text/html"%>
<%@page language="java" import="java.util.*"%>
<%@page import="com.web.jxp.jobpost.JobPostInfo" %>
<jsp:useBean id="jobpost" class="com.web.jxp.jobpost.JobPost" scope="page"/>
<jsp:useBean id="vobj" class="com.web.jxp.base.Validate" scope="page"/>
<%
    try 
    {
        if (session.getAttribute("LOGININFO") != null) 
        {
           int allclient = 0;
            UserInfo uinfo = ((UserInfo) request.getSession().getAttribute("LOGININFO"));
            String str = "", per = "", assetids = "";
            if (uinfo != null) 
            {
                per = uinfo.getPermission() != null ? uinfo.getPermission() : "N";
                allclient = uinfo.getAllclient();
                assetids = uinfo.getAssetids();
            }
            if (request.getParameter("from") != null)
            {
                if ("asset".equalsIgnoreCase(request.getParameter("from"))) 
                {
                    if (request.getParameter("clientId") != null) 
                    {
                        String clientIds = request.getParameter("clientId") != null && !request.getParameter("clientId").equals("") ? vobj.replaceint(request.getParameter("clientId")) : "";
                        int clientId = Integer.parseInt(clientIds);
                        response.setContentType("text/html");
                        response.setHeader("Cache-Control", "no-cache");
                        Collection coll = jobpost.getClientAsset(clientId, assetids, allclient, per);
                        StringBuffer sb = new StringBuffer();
                        if (clientId > 0) 
                        {
                            if (coll.size() > 0) 
                            {
                                Iterator iter = coll.iterator();
                                while (iter.hasNext()) 
                                {
                                    JobPostInfo info = (JobPostInfo) iter.next();
                                    int val = info.getDdlValue();
                                    String name = info.getDdlLabel() != null ? info.getDdlLabel() : "";
                                    sb.append("<option value='" + val + "'>" + name + "</option>");
                                }
                            }
                        } else {
                            sb.append("<option value='-1'>- Select -</option>");
                        }
                        str = sb.toString();
                        sb.setLength(0);
                        coll.clear();
                        response.getWriter().write(str);
                    } else {
                        response.setStatus(HttpServletResponse.SC_NO_CONTENT);
                    }
                } else if ("position".equalsIgnoreCase(request.getParameter("from"))) 
                {
                    if (request.getParameter("clientassetId") != null) 
                    {
                        String clientassetIds = request.getParameter("clientassetId") != null && !request.getParameter("clientassetId").equals("") ? vobj.replaceint(request.getParameter("clientassetId")) : "";
                        int clientassetddl = Integer.parseInt(clientassetIds);
                        response.setContentType("text/html");
                        response.setHeader("Cache-Control", "no-cache");
                        Collection coll = jobpost.getPostions(clientassetddl);
                        StringBuffer sb = new StringBuffer();
                        if (clientassetddl > 0) 
                        {
                            if (coll.size() > 0) 
                            {
                                Iterator iter = coll.iterator();
                                while (iter.hasNext()) 
                                {
                                    JobPostInfo info = (JobPostInfo) iter.next();
                                    int val = info.getDdlValue();
                                    String name = info.getDdlLabel() != null ? info.getDdlLabel() : "";
                                    sb.append("<option value='" + val + "'>" + name + "</option>");
                                }
                            }
                        } else {
                            sb.append("<option value='-1'>- Select -</option>");
                        }
                        str = sb.toString();
                        sb.setLength(0);
                        coll.clear();
                        response.getWriter().write(str);
                    } else {
                        response.setStatus(HttpServletResponse.SC_NO_CONTENT);
                    }
                } else if ("grade".equalsIgnoreCase(request.getParameter("from"))) 
                {
                    if (request.getParameter("clientassetId") != null) 
                    {
                        String clientIds = request.getParameter("clientassetId") != null && !request.getParameter("clientassetId").equals("") ? vobj.replaceint(request.getParameter("clientassetId")) : "";
                        String positionName = request.getParameter("positionnm") != null && !request.getParameter("positionnm").equals("") ? vobj.replacedesc(request.getParameter("positionnm")) : "";
                        int clientassetddl = Integer.parseInt(clientIds);
                        response.setContentType("text/html");
                        response.setHeader("Cache-Control", "no-cache");
                        Collection coll = jobpost.getGrades(clientassetddl, positionName);
                        StringBuffer sb = new StringBuffer();
                        if (clientassetddl > 0) 
                        {
                            if (coll.size() > 0) 
                            {
                                Iterator iter = coll.iterator();
                                while (iter.hasNext()) 
                                {
                                    JobPostInfo info = (JobPostInfo) iter.next();
                                    int val = info.getDdlValue();
                                    String name = info.getDdlLabel() != null ? info.getDdlLabel() : "";
                                    sb.append("<option value='" + val + "'>" + name + "</option>");
                                }
                            }
                        } else {
                            sb.append("<option value='-1'>- Select -</option>");
                        }
                        str = sb.toString();
                        sb.setLength(0);
                        coll.clear();
                        response.getWriter().write(str);
                    } else {
                        response.setStatus(HttpServletResponse.SC_NO_CONTENT);
                    }
                }
            }
        } else {
            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
        }
    } catch (Exception e) {
        
    }
%>