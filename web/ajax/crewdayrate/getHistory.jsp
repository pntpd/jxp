<%@page contentType="text/html"%>
<%@page language="java" import="java.util.ArrayList"%>
<%@page pageEncoding="UTF-8"%>
<%@page import="com.web.jxp.user.UserInfo" %>
<%@page import="com.web.jxp.crewdayrate.CrewdayrateInfo" %>
<jsp:useBean id="crewdayrate" class="com.web.jxp.crewdayrate.Crewdayrate" scope="page"/>
<jsp:useBean id="vobj" class="com.web.jxp.base.Validate" scope="page"/>
<%
    try 
    {
        if (session.getAttribute("LOGININFO") != null) 
        {
            if (request.getParameter("candidateId") != null) 
            {
                StringBuilder sb = new StringBuilder();
                String candidateId_s = request.getParameter("candidateId") != null ? vobj.replaceint(request.getParameter("candidateId")) : "0";
                String positionIds = request.getParameter("positionId") != null ? vobj.replaceint(request.getParameter("positionId")) : "0";
                String clientassetIds = request.getParameter("clientassetId") != null ? vobj.replaceint(request.getParameter("clientassetId")) : "0";
                int positionId = Integer.parseInt(positionIds);
                int candidateId = Integer.parseInt(candidateId_s);
                int clientassetId = Integer.parseInt(clientassetIds);
                if (candidateId > 0)
                {
                    ArrayList list = crewdayrate.getHistory(candidateId, positionId, clientassetId);
                    int size = list.size();
                    if (size > 0) 
                    {        
                        sb.append("<div class='full_width client_position_table'>");
                        sb.append("<div class='table-responsive1 mb-0'>");
                            
                        sb.append("<table class='table table-striped'>");
                        sb.append("<thead>");
                        sb.append("<tr>");
                        sb.append("<th width='10%'><span><b>Sr No.</b> </span></th>");
                        sb.append("<th width='18%'><span><b>From Date</b> </span></th>");
                        sb.append("<th width='18%'><span><b>To Date</b> </span></th>");
                        sb.append("<th width='18%'><span><b>Day Rate</b> </span></th>");
                        sb.append("<th width='18%'><span><b>Overtime / hr</b> </span></th>");
                        sb.append("<th width='18%'><span><b>Allowance</b> </span></th>");                        
                        sb.append("</tr>");
                        sb.append("</thead>");
                        sb.append("<tbody>");
                        
                        for (int i = 0; i < size; i++) 
                        {
                            CrewdayrateInfo info = (CrewdayrateInfo) list.get(i);
                            if (info != null) 
                            {
                                sb.append("<tr>"); 
                                sb.append("<td>" + (i+1)+ "</td>");
                                sb.append("<td>" + (info.getFromDate()!= null ? info.getFromDate(): "") + "</td>");                                    
                                sb.append("<td>" + (info.getToDate()!= null ? info.getToDate(): "") + "</td>");                                    
                                sb.append("<td>" + info.getRate1()+ "</td>");
                                sb.append("<td>" + info.getRate2()+ "</td>");
                                sb.append("<td>" + info.getRate3()+ "</td>");
                                sb.append("</tr>");       
                            }
                        }
                        sb.append("</tbody>");
                        sb.append("</table>");
                        sb.append("</div>");
                        sb.append("</div>");
                    } 
                    else {
                        sb.append("<div>No records available.</div></div>");
                    }
                    String st1 = sb.toString();
                    sb.setLength(0);
                    response.getWriter().write(st1);
                } else {
                    response.getWriter().write("Something went wrong.");
                }
            } else {
                response.setStatus(HttpServletResponse.SC_NO_CONTENT);
            }
        } else {
            response.getWriter().write("Please check your login session....");
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
%>