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
            int userId = uInfo.getUserId();
            if (userId > 0) 
            {
                ArrayList search_list = new ArrayList();
                if (session.getAttribute("SEARCHPARAMETER") != null) 
                {
                    search_list = (ArrayList) session.getAttribute("SEARCHPARAMETER");
                }
                int total = search_list.size();
                if (total > 0) 
                {
                    String strid = request.getParameter("id") != null && !request.getParameter("id").equals("") ? vobj.replaceint(request.getParameter("id")) : "-1";
                    String strflag = request.getParameter("flag") != null && !request.getParameter("flag").equals("") ? vobj.replaceint(request.getParameter("flag")) : "-1";
                    int id = Integer.parseInt(strid);
                    int flag = Integer.parseInt(strflag);
                    ShortlistingInfo pillsinfo = (ShortlistingInfo) search_list.get(id);
                    if (flag == 1) {
                        pillsinfo.setShowflag(0);
                    } else if (flag == 0) {
                        pillsinfo.setShowflag(1);
                    }
                    search_list.remove(id);
                    search_list.add(id, pillsinfo);

                    request.getSession().setAttribute("SEARCHPARAMETER", search_list);
                    request.getSession().removeAttribute("CANDIDATELIST");
                    String strSearch = shortlisting.getStrFromStr(search_list);
                    ArrayList candidate_list = new ArrayList();
                    candidate_list = shortlisting.getCandidateListByFields(strSearch);
                    request.getSession().setAttribute("CANDIDATELIST", candidate_list);

                    StringBuffer sb = new StringBuffer();
                    int total1 = search_list.size();
                    if (total1 > 0) 
                    {
                        ShortlistingInfo sinfo;
                        for (int i = 0; i < total1; i++) 
                        {
                            sinfo = (ShortlistingInfo) search_list.get(i);
                            if (sinfo != null) 
                            {
                                if (i == 0 || i == (total - 1)) {
                                } else {
                                    if (sinfo.getShowflag() != 0) {
                                        sb.append("<li><span>" + sinfo.getVal2() + "</span><a href=\"javascript:getpills('" + i + "','" + sinfo.getShowflag() + "');\"><i class='ion ion-md-close'></i></a></li>");
                                    } else {
                                        sb.append("<li class='disable_pill'><span>" + sinfo.getVal2() + "</span><a href=\"javascript:getpills('" + i + "','" + sinfo.getShowflag() + "');\"><i class='ion ion-md-checkmark'></i></a></li>");
                                    }
                                }
                            }
                        }
                    }
                    String str = sb.toString();
                    sb.setLength(0);

                    SortedSet<String> ss = new TreeSet<String>();
                    if (session.getAttribute("CANDSORTEDLIST") != null) {
                        ss = (SortedSet) session.getAttribute("CANDSORTEDLIST");
                    }
                    int total2 = candidate_list.size();
                    if (total2 > 0) 
                    {
                        ShortlistingInfo info;
                        String strclass = "", strtemp = "";
                        for (int i = 0; i < total2; i++) {
                            info = (ShortlistingInfo) candidate_list.get(i);
                            if (info != null) {
                                if ((i + 1) % 2 == 0) {
                                    strclass = "odd_list_2";
                                } else {
                                    strclass = "odd_list_1";
                                }
                                sb.append("<li class='" + strclass + "'>");
                                sb.append("<div class='search_box'>");
                                sb.append("<div class='row'>");

                                sb.append("<div class='col-md-10 comp_view'>");
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
                                sb.append("<div class='col-md-2 add_view_area'>");
                                sb.append("<div class='row'>");
                                sb.append("<div class='search_add_btn'>");
                                if (ss.contains("" + info.getCandidateId())) {
                                    strtemp = "style='display: none'";
                                } else {
                                    strtemp = "";
                                }
                                sb.append("<a href='javascript:;' id='btnadd_" + info.getCandidateId() + "' onclick=\"addtoSortedSet('" + info.getCandidateId() + "', 'ADD');\" " + strtemp + ">Add <i class='ion ion-ios-arrow-round-forward'></i></a>");
                                sb.append("</div>");
                                sb.append("<div class='search_view_prof com_view_job'>");
                                sb.append("<a href=\"javascript: viewTalentPool('" + info.getCandidateId() + "');\"><img src='../assets/images/view.png'/><br/> View Profile</a>");
                                sb.append("</div>");
                                sb.append("</div>");
                                sb.append("</div>");

                                sb.append("</div>");
                                sb.append("</div>");
                                sb.append("</li>");
                            }
                        }
                    }
                    String str1 = sb.toString();
                    sb.setLength(0);
                    response.getWriter().write(str + "#@#" + str1);
                }
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
%>