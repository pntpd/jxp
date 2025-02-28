<%@page import="java.util.TreeSet"%>
<%@page import="java.util.SortedSet"%>
<%@page import="java.util.LinkedList"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.Collection"%>
<%@page import="org.json.JSONObject"%>
<%@page import="java.io.BufferedReader"%>
<%@page contentType="text/html"%>
<%@page language="java" import="java.util.ArrayList"%>
<%@page import="com.web.jxp.user.UserInfo" %>
<%@page import="com.web.jxp.shortlisting.ShortlistingInfo" %>
<%@page import="com.web.jxp.client.ClientInfo" %>  
<jsp:useBean id="shortlisting" class="com.web.jxp.shortlisting.Shortlisting" scope="page"/>
<jsp:useBean id="vobj" class="com.web.jxp.base.Validate" scope="page"/>
<%
    try
    {
        UserInfo uInfo = (UserInfo) request.getSession().getAttribute("LOGININFO");
        if (uInfo != null) 
        {
            SortedSet<String> ss = new TreeSet<String>();
            if (session.getAttribute("CANDSORTEDLIST") != null) {
                ss = (SortedSet) session.getAttribute("CANDSORTEDLIST");
            }
            int userId = uInfo.getUserId();
            if (userId > 0) 
            {
                String candidateIds = request.getParameter("candidateid") != null && !request.getParameter("candidateid").equals("") ? vobj.replaceint(request.getParameter("candidateid")) : "0";
                String from = request.getParameter("from") != null && !request.getParameter("from").equals("") ? vobj.replacename(request.getParameter("from")) : "";
                if (from.equalsIgnoreCase("ADD")) {
                    ss.add(candidateIds);
                } else if (from.equalsIgnoreCase("REMOVE")) {
                    ss.remove(candidateIds);
                }
                request.getSession().setAttribute("CANDSORTEDLIST", ss);

                String strCandidatelist = String.join(",", ss);
                ArrayList list = shortlisting.getCandidateListByIDs(strCandidatelist);
                int total = list.size();
                StringBuffer sb = new StringBuffer();
                int tempVal = 0;
                if (total > 0) 
                {
                    ShortlistingInfo info;
                    String strclass = "";
                    for (int i = 0; i < total; i++) 
                    {
                        tempVal++;
                        info = (ShortlistingInfo) list.get(i);
                        if (info != null) 
                        {
                            if ((i + 1) % 2 == 0) {
                                strclass = "odd_list_2";
                            } else {
                                strclass = "odd_list_1";
                            }
                            sb.append("<li class='" + strclass + "'>");
                            sb.append("<div class='search_box'>");
                            sb.append("<div class='row'>");
                            sb.append("<div class='col-md-9 comp_view'>");
                            sb.append("<div class='row'>");
                            sb.append("<div class='full_name_ic col-md-12 mb_0'>");
                            sb.append("<a href='javascript:;' class='tooltip_name' data-toggle='tooltip' data-placement='top' title='Full Name'><i class='mdi mdi-account'></i></a>");
                            sb.append("<span>" + (info.getName() != null || info.getName().equals("") ? info.getName() : "&nbsp;") + "</span>");
                            sb.append("</div>");

                            sb.append("<div class='posi_rank_ic col-md-12 mb_0'>");
                            sb.append("<a href='javascript:;' class='tooltip_name' data-toggle='tooltip' data-placement='top' title='Position-Rank'><i class='mdi mdi-star-circle'></i></a>");
                            sb.append("<span>" + (info.getPosition() != null || info.getPosition().equals("") ? info.getPosition() : "&nbsp;") + (info.getGrade() != null || info.getGrade().equals("") ? " - " + info.getGrade() : "&nbsp;") + "</span>");
                            sb.append("</div>");
                            sb.append("<div class='expe_ic col-md-12 mb_0'>");
                            sb.append("<a href='javascript:;' class='tooltip_name' data-toggle='tooltip' data-placement='top' title='Experience'><i class='mdi mdi-lightbulb'></i></a>");
                            sb.append("<span>" + info.getExp() + " Yrs</span>");
                            sb.append("</div>");

                            sb.append("<div class='gradu_ic col-md-12 mb_0'>");
                            sb.append("<a href='javascript:;' class='tooltip_name' data-toggle='tooltip' data-placement='top' title='Education'><i class='fas fa-graduation-cap'></i></a>");
                            sb.append("<span>" + (info.getDegree() != null || info.getDegree().equals("") ? info.getDegree() : "&nbsp;") + (info.getQualification() != null || info.getQualification().equals("") ? " - " + info.getQualification() : "&nbsp;") + "</span>");
                            sb.append("</div>");

                            sb.append("<div class='brief_ic col-md-12'>");
                            sb.append("<a href='javascript:;' class='tooltip_name' data-toggle='tooltip' data-placement='top' title='Last Job'><i class='ion ion-ios-briefcase'></i></a>");
                            sb.append("<span>" + (info.getCompany() != null || info.getCompany().equals("") ? info.getCompany() : "&nbsp;") + "</span>");
                            sb.append("</div>");
                            sb.append("</div>");
                            sb.append("</div>");
                            sb.append("<div class='col-md-3 add_view_area'>");
                            sb.append("<div class='row'>");
                            sb.append("<div class='search_rem_btn'><a href='javascript:;' id='btnRmv_'" + info.getCandidateId() + "' onclick=\"javascript: addtoSortedSet('" + info.getCandidateId() + "','REMOVE');\"><i class='ion ion-ios-arrow-round-back'></i> Remove </a></div>");
                            sb.append("<div class='search_view_prof com_view_job'>");
                            sb.append("<a href=\"javascript: viewTalentPool('" + info.getCandidateId() + "'); \"><img src='../assets/images/view.png'/><br/> View Profile</a>");
                            sb.append("</div>");
                            sb.append("</div>");
                            sb.append("</div>");
                            sb.append("</div>");
                            sb.append("</div>");
                            sb.append("</li>");
                        }
                    }
                }
                sb.append("<input type='hidden' name='sortedcount' id='sortedcount' value='" + tempVal + "'/>");
                String st = sb.toString();
                sb.setLength(0);
                response.getWriter().write(st);
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
%>