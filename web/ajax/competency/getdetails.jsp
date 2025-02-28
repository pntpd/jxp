<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.Collection"%>
<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@page import="com.web.jxp.user.UserInfo" %>
<%@page import="com.web.jxp.competency.CompetencyInfo" %>
<jsp:useBean id="competency" class="com.web.jxp.competency.Competency" scope="page"/>
<jsp:useBean id="vobj" class="com.web.jxp.base.Validate" scope="page"/>
<%
    try 
    {
        if (session.getAttribute("LOGININFO") != null) 
        {
            String str = "";                    
            String assetIdIndexs = request.getParameter("assetIdIndex") != null && !request.getParameter("assetIdIndex").equals("") ? vobj.replaceint(request.getParameter("assetIdIndex")) : "";
            int assetIdIndex = Integer.parseInt(assetIdIndexs);
            if(assetIdIndex > 0)
            {
                if (request.getParameter("searchDept") != null) 
                {
                    String searchDept = request.getParameter("searchDept") != null && !request.getParameter("searchDept").equals("") ? vobj.replacedesc(request.getParameter("searchDept")) : "";

                    String deptcb = "";
                    if (session.getAttribute("DEPTCB") != null) 
                    {
                        deptcb = (String) session.getAttribute("DEPTCB");
                    }
                    response.setContentType("text/html");
                    response.setHeader("Cache-Control", "no-cache");
                    ArrayList deptlist  = competency.getDepartmentList(assetIdIndex, searchDept );
                    StringBuffer sb = new StringBuffer();
                    int deptlisttotal = deptlist.size();
                    if (deptlisttotal > 0) 
                    {
                        for(int i = 0 ;i < deptlisttotal; i++)
                        {
                            CompetencyInfo positioninfo = (CompetencyInfo)deptlist.get(i);
                            if (positioninfo != null) 
                            {
                                String positionname = positioninfo.getDdlLabel()!= null && !positioninfo.getDdlLabel().equals("") ? positioninfo.getDdlLabel(): "";
                                sb.append("<ul>");
                                sb.append("<li>");
                                sb.append("<label class='mt-checkbox mt-checkbox-outline'>");
                                sb.append(positionname);
                                sb.append("<input type='checkbox' value='");
                                sb.append(positioninfo.getDdlValue()+"'");
                                sb.append("name='pcb' onchange =\" javascript : setVal('1');\" ");
                                //checked logic
                                if(competency.checkToStr(deptcb,""+positioninfo.getDdlValue()))
                                {
                                    sb.append(" checked ");
                                }
                                sb.append("/>");
                                sb.append("<span></span>");
                                sb.append("</label>");	
                                sb.append("</li>");
                            }
                        }                        
                        sb.append("</ul>");
                    }
                    else {
                        sb.append("no data available");
                    }
                    str = sb.toString();
                    sb.setLength(0);
                    response.getWriter().write(str);
                }
                else if (request.getParameter("searchPosition") != null) 
                {
                    String searchPosition = request.getParameter("searchPosition") != null && !request.getParameter("searchPosition").equals("") ? vobj.replacedesc(request.getParameter("searchPosition")) : "";

                    String positioncb = "";
                    if (session.getAttribute("POSITIONCB") != null) 
                    {
                        positioncb = (String) session.getAttribute("POSITIONCB");
                    }
                    response.setContentType("text/html");
                    response.setHeader("Cache-Control", "no-cache");
                    ArrayList positionlist  = competency.getPositionList(assetIdIndex, searchPosition);
                    StringBuffer sb = new StringBuffer();
                    int positionlisttotal = positionlist.size();
                    if (positionlisttotal > 0) 
                    {
                        for(int i = 0 ;i < positionlisttotal; i++)
                        {
                            CompetencyInfo positioninfo = (CompetencyInfo)positionlist.get(i);
                            if (positioninfo != null) 
                            {
                                String positionname = positioninfo.getDdlLabel()!= null && !positioninfo.getDdlLabel().equals("") ? positioninfo.getDdlLabel(): "";
                                sb.append("<ul>");
                                sb.append("<li>");
                                sb.append("<label class='mt-checkbox mt-checkbox-outline'>");
                                sb.append(positionname);
                                sb.append("<input type='checkbox' value='");
                                sb.append(positioninfo.getDdlValue()+"'");
                                sb.append("name='pcb' onchange =\" javascript :setVal('2');\" ");
                                //checked logic
                                if(competency.checkToStr(positioncb,""+positioninfo.getDdlValue()))
                                {
                                    sb.append(" checked ");
                                }
                                sb.append("/>");
                                sb.append("<span></span>");
                                sb.append("</label>");	
                                sb.append("</li>");
                            }
                        }
                        sb.append("</ul>");
                    }
                    else {
                        sb.append("no data available");
                    }
                    str = sb.toString();
                    sb.setLength(0);
                    response.getWriter().write(str);
                }
                else if (request.getParameter("searchName") != null) 
                {
                    String searchName = request.getParameter("searchName") != null && !request.getParameter("searchName").equals("") ? vobj.replacedesc(request.getParameter("searchName")) : "";
                    String crewrotationcb = "";
                    if (session.getAttribute("CREWROTATIONCB") != null) 
                    {
                        crewrotationcb = (String) session.getAttribute("CREWROTATIONCB");
                    }
                    response.setContentType("text/html");
                    response.setHeader("Cache-Control", "no-cache");
                    ArrayList crewlist  = competency.getCandidateList(assetIdIndex, searchName);
                    StringBuffer sb = new StringBuffer();
                    int crewlisttotal = crewlist.size();
                    if (crewlisttotal > 0) 
                    {
                        for(int i = 0 ;i < crewlisttotal; i++)
                        {
                            CompetencyInfo crewinfo = (CompetencyInfo)crewlist.get(i);
                            if (crewinfo != null) 
                            {
                                String positionname = crewinfo.getDdlLabel()!= null && !crewinfo.getDdlLabel().equals("") ? crewinfo.getDdlLabel(): "";
                                sb.append("<ul>");
                                sb.append("<li>");
                                sb.append("<label class='mt-checkbox mt-checkbox-outline'>");
                                sb.append(positionname);
                                sb.append("<input type='checkbox' value='");
                                sb.append(crewinfo.getDdlValue()+"'");
                                sb.append("name='pcb' onchange =\" javascript : setVal('3');\" ");
                                //checked logic
                                if(competency.checkToStr(crewrotationcb,""+crewinfo.getDdlValue()))
                                {
                                    sb.append(" checked ");
                                }
                                sb.append("/>");
                                sb.append("<span></span>");
                                sb.append("</label>");	
                                sb.append("</li>");
                            }
                        }
                        sb.append("</ul>");
                    }
                    else {
                        sb.append("no data available");
                    }
                    str = sb.toString();
                    sb.setLength(0);
                    response.getWriter().write(str);
                }
                else if (request.getParameter("searchRole") != null) 
                {
                    String searchRole = request.getParameter("searchRole") != null && !request.getParameter("searchRole").equals("") ? vobj.replacedesc(request.getParameter("searchRole")) : "";
                    String rolecb = "";
                    if (session.getAttribute("ROLECB") != null) 
                    {
                        rolecb = (String) session.getAttribute("ROLECB");
                    }
                    response.setContentType("text/html");
                    response.setHeader("Cache-Control", "no-cache");
                    ArrayList list  = competency.getCompetencyRoleList(assetIdIndex, searchRole);
                    StringBuffer sb = new StringBuffer();
                    int listtotal = list.size();
                    if (listtotal > 0) 
                    {
                        for(int i = 0 ;i < listtotal; i++)
                        {
                            CompetencyInfo roleinfo = (CompetencyInfo)list.get(i);
                            if (roleinfo != null) 
                            {
                                String positionname = roleinfo.getDdlLabel()!= null && !roleinfo.getDdlLabel().equals("") ? roleinfo.getDdlLabel(): "";
                                sb.append("<ul>");
                                sb.append("<li>");
                                sb.append("<label class='mt-checkbox mt-checkbox-outline'>");
                                sb.append(positionname);
                                sb.append("<input type='checkbox' value='");
                                sb.append(roleinfo.getDdlValue()+"'");
                                sb.append("name='pcb' onchange =\" javascript :setVal('4');\" ");
                                //checked logic
                                if(competency.checkToStr(rolecb,""+roleinfo.getDdlValue()))
                                {
                                    sb.append(" checked ");
                                }
                                sb.append("/>");
                                sb.append("<span></span>");
                                sb.append("</label>");	
                                sb.append("</li>");
                            }
                        }
                        sb.append("</ul>");
                    }
                    else {
                        sb.append("no data available");
                    }
                    str = sb.toString();
                    sb.setLength(0);
                    response.getWriter().write(str);
                }
             }
            else {
                response.setStatus(HttpServletResponse.SC_NO_CONTENT);
            }
        }
         else {
            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
        }
    } catch (Exception e) {

    }
%>