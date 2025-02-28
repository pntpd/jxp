<%@page contentType="text/html"%>
<%@page language="java" import="java.util.ArrayList"%>
<%@page pageEncoding="UTF-8"%>
<%@page import="com.web.jxp.user.UserInfo" %>
<%@page import="com.web.jxp.wellnessfb.WellnessfbInfo" %>
<jsp:useBean id="wellnessfb" class="com.web.jxp.wellnessfb.Wellnessfb" scope="page"/>
<jsp:useBean id="dashboard" class="com.web.jxp.dashboard.Dashboard" scope="page"/>
<jsp:useBean id="vobj" class="com.web.jxp.base.Validate" scope="page"/>
<%
    try 
    {
        if (session.getAttribute("LOGININFO") != null) 
        {
            String month_s = request.getParameter("month") != null && !request.getParameter("month").equals("") ? vobj.replaceint(request.getParameter("month")) : "0";
            String day_s = request.getParameter("day") != null && !request.getParameter("day").equals("") ? vobj.replaceint(request.getParameter("day")) : "0";
            String action = request.getParameter("action") != null && !request.getParameter("action").equals("") ? vobj.replacename(request.getParameter("action")) : "";
            int month = Integer.parseInt(month_s);
            int day = Integer.parseInt(day_s);
            ArrayList list = new ArrayList();
            if(session.getAttribute("MDLIST") != null)
                list = (ArrayList) session.getAttribute("MDLIST");
            int list_size = list.size();
            if(action.equals("add"))
            {
                int exist = 0;
                if(list_size > 0)
                {
                    for(int i = 0; i < list_size; i++)
                    {
                        WellnessfbInfo info = (WellnessfbInfo) list.get(i);
                        if(info != null && info.getMonth() == month && info.getDay() == day)
                        {
                            exist = 1;
                            break;
                        }
                    }
                }
                if(exist == 0)
                {
                    list.add(new WellnessfbInfo(month, day, day+"-"+month+"-"+wellnessfb.currYear()));
                    session.setAttribute("MDLIST", list);
                }
            }
            else if(action.equals("delete"))
            {
                ArrayList listnew = new ArrayList();
                if(list_size > 0)
                {
                    for(int i = 0; i < list_size; i++)
                    {
                        WellnessfbInfo info = (WellnessfbInfo) list.get(i);
                        if(info != null && info.getMonth() == month && info.getDay() == day)
                        {
                        }
                        else
                        {
                            listnew.add(info);
                        }
                    }
                }
                list = listnew;
                if(listnew.size() > 0)
                    session.setAttribute("MDLIST", listnew);
                else
                    session.removeAttribute("MDLIST");
            }
            list_size = list.size();
            StringBuilder sb = new StringBuilder();
            sb.append("<div class='col-md-4'>");
            sb.append("<label class='form_label'>Select Month</label>");
            sb.append("<select name = 'monthdp' class='form-select'  onchange=\" javascript: setyeardays('-1','a')\">");
            sb.append("<option value='-1'>Select here</option>");
            sb.append("<option value='1' ");
            if(month == 1)
            {
                sb.append(" selected ");
            }
            sb.append(">Jan</option>");
            sb.append("<option value='2' ");
            if(month == 2)
            {
                sb.append(" selected ");
            }
            sb.append(">Feb</option>");
            sb.append("<option value='3' ");
            if(month == 3)
            {
                sb.append(" selected ");
            }
            sb.append(">Mar</option>");
            sb.append("<option value='4' ");
            if(month == 4)
            {
                sb.append(" selected ");
            }
            sb.append(">Apr</option>");
            sb.append("<option value='5' ");
            if(month == 5)
            {
                sb.append(" selected ");
            }
            sb.append(">May</option>");
            sb.append("<option value='6' ");
            if(month == 6)
            {
                sb.append(" selected ");
            }
            sb.append(">Jun</option>");
            sb.append("<option value='7' ");
            if(month == 7)
            {
                sb.append(" selected ");
            }
            sb.append(">Jul</option>");
            sb.append("<option value='8' ");
            if(month == 8)
            {
                sb.append(" selected ");
            }
            sb.append(">Aug</option>");
            sb.append("<option value='9' ");
            if(month == 9)
            {
                sb.append(" selected ");
            }
            sb.append(">Sep</option>");
            sb.append("<option value='10' ");
            if(month == 10)
            {
                sb.append(" selected ");
            }
            sb.append(">Oct</option>");
            sb.append("<option value='11' ");
            if(month == 11)
            {
                sb.append(" selected ");
            }
            sb.append(">Nov</option>");
            sb.append("<option value='12' ");
            if(month == 12)
            {
                sb.append(" selected ");
            }
            sb.append(">Dec</option>");
            sb.append("</select>");
            sb.append("</div>");
            sb.append("<div class='col-md-8'>");
            sb.append("<label class='form_label'>Select Days</label>");
            sb.append("<div class='full_width weekly_div'>");
            sb.append("<ul>");

            sb.append("<li><a href=\"javascript: setyeardays('01','add');\"");
            if(wellnessfb.checklistforday(list, 1, month))
            {
                sb.append("class='week_selected'");
            }
            sb.append(">1</a></li>");
            sb.append("<li><a href=\"javascript: setyeardays('02','add');\"");
            if(wellnessfb.checklistforday(list, 2, month))
            {
                sb.append("class='week_selected'");
            }
            sb.append(">2</a></li>");
            sb.append("<li><a href=\"javascript: setyeardays('03','add');\"");
            if(wellnessfb.checklistforday(list, 3, month))
            {
                sb.append("class='week_selected'");
            }
            sb.append(">3</a></li>");
            sb.append("<li><a href=\"javascript: setyeardays('04','add');\"");
            if(wellnessfb.checklistforday(list, 4, month))
            {
                sb.append("class='week_selected'");
            }
            sb.append(">4</a></li>");
            sb.append("<li><a href=\"javascript: setyeardays('05','add');\"");
            if(wellnessfb.checklistforday(list, 5, month))
            {
                sb.append("class='week_selected'");
            }
            sb.append(">5</a></li>");
            sb.append("<li><a href=\"javascript: setyeardays('06','add');\"");
            if(wellnessfb.checklistforday(list, 6, month))
            {
                sb.append("class='week_selected'");
            }
            sb.append(">6</a></li>");
            sb.append("<li><a href=\"javascript: setyeardays('07','add');\"");
            if(wellnessfb.checklistforday(list, 7, month))
            {
                sb.append("class='week_selected'");
            }
            sb.append(">7</a></li>");
            sb.append("<li><a href=\"javascript: setyeardays('08','add');\"");
            if(wellnessfb.checklistforday(list, 8, month))
            {
                sb.append("class='week_selected'");
            }
            sb.append(">8</a></li>");
            sb.append("<li><a href=\"javascript: setyeardays('09','add');\"");
            if(wellnessfb.checklistforday(list, 9, month))
            {
                sb.append("class='week_selected'");
            }
            sb.append(">9</a></li>");
            sb.append("<li><a href=\"javascript: setyeardays('10','add');\"");
            if(wellnessfb.checklistforday(list, 10, month))
            {
                sb.append("class='week_selected'");
            }
            sb.append(">10</a></li>");
            sb.append("<li><a href=\"javascript: setyeardays('11','add');\"");
            if(wellnessfb.checklistforday(list, 11, month))
            {
                sb.append("class='week_selected'");
            }
            sb.append(">11</a></li>");
            sb.append("<li><a href=\"javascript: setyeardays('12','add');\"");
            if(wellnessfb.checklistforday(list, 12, month))
            {
                sb.append("class='week_selected'");
            }
            sb.append(">12</a></li>");
            sb.append("<li><a href=\"javascript: setyeardays('13','add');\"");
            if(wellnessfb.checklistforday(list, 13, month))
            {
                sb.append("class='week_selected'");
            }
            sb.append(">13</a></li>");
            sb.append("<li><a href=\"javascript: setyeardays('14','add');\"");
            if(wellnessfb.checklistforday(list, 14, month))
            {
                sb.append("class='week_selected'");
            }
            sb.append(">14</a></li>");
            sb.append("<li><a href=\"javascript: setyeardays('15','add');\"");
            if(wellnessfb.checklistforday(list, 15, month))
            {
                sb.append("class='week_selected'");
            }
            sb.append(">15</a></li>");
            sb.append("<li><a href=\"javascript: setyeardays('16','add');\"");
            if(wellnessfb.checklistforday(list, 16, month))
            {
                sb.append("class='week_selected'");
            }
            sb.append(">16</a></li>");
            sb.append("<li><a href=\"javascript: setyeardays('17','add');\"");
            if(wellnessfb.checklistforday(list, 17, month))
            {
                sb.append("class='week_selected'");
            }
            sb.append(">17</a></li>");
            sb.append("<li><a href=\"javascript: setyeardays('18','add');\"");
            if(wellnessfb.checklistforday(list, 18, month))
            {
                sb.append("class='week_selected'");
            }
            sb.append(">18</a></li>");
            sb.append("<li><a href=\"javascript: setyeardays('19','add');\"");
            if(wellnessfb.checklistforday(list, 19, month))
            {
                sb.append("class='week_selected'");
            }
            sb.append(">19</a></li>");
            sb.append("<li><a href=\"javascript: setyeardays('20','add');\"");
            if(wellnessfb.checklistforday(list, 20, month))
            {
                sb.append("class='week_selected'");
            }
            sb.append(">20</a></li>");
            sb.append("<li><a href=\"javascript: setyeardays('21','add');\"");
            if(wellnessfb.checklistforday(list, 21, month))
            {
                sb.append("class='week_selected'");
            }
            sb.append(">21</a></li>");
            sb.append("<li><a href=\"javascript: setyeardays('22','add');\"");
            if(wellnessfb.checklistforday(list, 22, month))
            {
                sb.append("class='week_selected'");
            }
            sb.append(">22</a></li>");
            sb.append("<li><a href=\"javascript: setyeardays('23','add');\"");
            if(wellnessfb.checklistforday(list, 23, month))
            {
                sb.append("class='week_selected'");
            }
            sb.append(">23</a></li>");
            sb.append("<li><a href=\"javascript: setyeardays('24','add');\"");
            if(wellnessfb.checklistforday(list, 24, month))
            {
                sb.append("class='week_selected'");
            }
            sb.append(">24</a></li>");
            sb.append("<li><a href=\"javascript: setyeardays('25','add');\"");
            if(wellnessfb.checklistforday(list, 25, month))
            {
                sb.append("class='week_selected'");
            }
            sb.append(">25</a></li>");
            sb.append("<li><a href=\"javascript: setyeardays('26','add');\"");
            if(wellnessfb.checklistforday(list, 26, month))
            {
                sb.append("class='week_selected'");
            }
            sb.append(">26</a></li>");
            sb.append("<li><a href=\"javascript: setyeardays('27','add');\"");
            if(wellnessfb.checklistforday(list, 27, month))
            {
                sb.append("class='week_selected'");
            }
            sb.append(">27</a></li>");
            sb.append("<li><a href=\"javascript: setyeardays('28','add');\"");
            if(wellnessfb.checklistforday(list, 28, month))
            {
                sb.append("class='week_selected'");
            }
            sb.append(">28</a></li>");
            
            if(month != 2){
            sb.append("<li><a href=\"javascript: setyeardays('29','add');\"");
            if(wellnessfb.checklistforday(list, 29, month))
            {
                sb.append("class='week_selected'");
            }
            
            sb.append(">29</a></li>");
            
            sb.append("<li><a href=\"javascript: setyeardays('30','add');\"");
            if(wellnessfb.checklistforday(list, 30, month))
            {
                sb.append("class='week_selected'");
            }
            sb.append(">30</a></li>");
             if(month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12){
            sb.append("<li><a href=\"javascript: setyeardays('31','add');\"");
            if(wellnessfb.checklistforday(list, 31, month))
            {
                sb.append("class='week_selected'");
            }
            sb.append(">31</a></li>");
            }
            }
            sb.append("</ul>");
            sb.append("</div>");
            sb.append("<div class='full_width pill_box_area month_pill'>");
            sb.append("<ul>");
            
            if(list_size > 0)
            {
            for(int i = 0; i < list_size; i++)
            {
                WellnessfbInfo info = (WellnessfbInfo) list.get(i); 
                sb.append("<li><span>"+info.getDay()+ "-"+dashboard.getMonthName(info.getMonth())+"</span> <a href=\"javascript: deleteyeardays('"+info.getDay()+"','"+info.getMonth()+"','delete');\"><i class='ion ion-md-close'></i></a></li>");
            }
            sb.append("</ul>");
            sb.append("</div>");
            sb.append("</div>");
            }
            String s1 = sb.toString();
            sb.setLength(0);
            response.getWriter().write(s1);
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