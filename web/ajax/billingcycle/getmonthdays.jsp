<%@page contentType="text/html"%>
<%@page language="java" import="java.util.ArrayList"%>
<%@page pageEncoding="UTF-8"%>
<%@page import="com.web.jxp.user.UserInfo" %>
<%@page import="com.web.jxp.billingcycle.BillingcycleInfo" %>
<jsp:useBean id="wellnessfb" class="com.web.jxp.billingcycle.Billingcycle" scope="page"/>
<jsp:useBean id="vobj" class="com.web.jxp.base.Validate" scope="page"/>
<%
    try 
    {
        if (session.getAttribute("LOGININFO") != null) 
        {            
            if (request.getParameter("schedulevalue") != null) 
            {
                StringBuilder str = new StringBuilder();
                String schedulevalues = request.getParameter("schedulevalue") != null && !request.getParameter("schedulevalue").equals("") ? vobj.replacedesc(request.getParameter("schedulevalue")) : "0";
                
                str.append("<label class='form_label'>Select Days</label>");
                str.append("<div class='full_width weekly_div'>");
                str.append("<ul>");
                 
                str.append("<li><a href=\"javascript: checkmonth('1');\" ");
                str.append("class='");
                if(wellnessfb.checkToStr(schedulevalues,"1"))
                {
                  str.append("week_selected ");
                }
                str.append("'>1</a></li>");
                str.append("<li><a href=\"javascript: checkmonth('2');\" ");
                str.append("class='");
                if(wellnessfb.checkToStr(schedulevalues,"2"))
                {
                    str.append("week_selected ");
                }
                str.append("'>2</a></li>");
                str.append("<li><a href=\"javascript: checkmonth('3');\" ");
                str.append("class='");
                if(wellnessfb.checkToStr(schedulevalues,"3"))
                {
                    str.append("week_selected ");
                }
                str.append("'>3</a></li>");
                str.append("<li><a href=\"javascript: checkmonth('4');\" ");
                str.append("class='");
                if(wellnessfb.checkToStr(schedulevalues,"4"))
                {
                    str.append("week_selected ");
                }
                str.append("'>4</a></li>");
                str.append("<li><a href=\"javascript: checkmonth('5');\" ");
                str.append("class='");
                if(wellnessfb.checkToStr(schedulevalues,"5"))
                {
                    str.append("week_selected ");
                }
                str.append("'>5</a></li>");
                str.append("<li><a href=\"javascript: checkmonth('6');\" ");
                str.append("class='");
                if(wellnessfb.checkToStr(schedulevalues,"6"))
                {
                    str.append("week_selected ");
                }
                str.append("'>6</a></li>");
                str.append("<li><a href=\"javascript: checkmonth('7');\" ");
                str.append("class='");
                if(wellnessfb.checkToStr(schedulevalues,"7"))
                {
                    str.append("week_selected ");
                }
                str.append("'>7</a></li>");
                str.append("<li><a href=\"javascript: checkmonth('8');\" ");
                str.append("class='");
                if(wellnessfb.checkToStr(schedulevalues,"8"))
                {
                    str.append("week_selected ");
                }
                str.append("'>8</a></li>");
                str.append("<li><a href=\"javascript: checkmonth('9');\" ");
                str.append("class='");
                if(wellnessfb.checkToStr(schedulevalues,"9"))
                {
                    str.append("week_selected ");
                }
                str.append("'>9</a></li>");
                str.append("<li><a href=\"javascript: checkmonth('10');\" ");
                str.append("class='");
                if(wellnessfb.checkToStr(schedulevalues,"10"))
                {
                    str.append("week_selected ");
                }
                str.append("'>10</a></li>");
                str.append("<li><a href=\"javascript: checkmonth('11');\" ");
                str.append("class='");
                if(wellnessfb.checkToStr(schedulevalues,"11"))
                {
                    str.append("week_selected ");
                }
                str.append("'>11</a></li>");
                str.append("<li><a href=\"javascript: checkmonth('12');\" ");
                str.append("class='");
                if(wellnessfb.checkToStr(schedulevalues,"12"))
                {
                    str.append("week_selected ");
                }
                str.append("'>12</a></li>");
                str.append("<li><a href=\"javascript: checkmonth('13');\" ");
                str.append("class='");
                if(wellnessfb.checkToStr(schedulevalues,"13"))
                {
                    str.append("week_selected ");
                }
                str.append("'>13</a></li>");
                str.append("<li><a href=\"javascript: checkmonth('14');\" ");
                str.append("class='");
                if(wellnessfb.checkToStr(schedulevalues,"14"))
                {
                    str.append("week_selected ");
                }
                str.append("'>14</a></li>");
                str.append("<li><a href=\"javascript: checkmonth('15');\" ");
                str.append("class='");
                if(wellnessfb.checkToStr(schedulevalues,"15"))
                {
                    str.append("week_selected ");
                }
                str.append("'>15</a></li>");
                str.append("<li><a href=\"javascript: checkmonth('16');\" ");
                str.append("class='");
                if(wellnessfb.checkToStr(schedulevalues,"16"))
                {
                    str.append("week_selected ");
                }
                str.append("'>16</a></li>");
                str.append("<li><a href=\"javascript: checkmonth('17');\" ");
                str.append("class='");
                if(wellnessfb.checkToStr(schedulevalues,"17"))
                {
                    str.append("week_selected ");
                }
                str.append("'>17</a></li>");
                str.append("<li><a href=\"javascript: checkmonth('18');\" ");
                str.append("class='");
                if(wellnessfb.checkToStr(schedulevalues,"18"))
                {
                    str.append("week_selected ");
                }
                str.append("'>18</a></li>");
                str.append("<li><a href=\"javascript: checkmonth('19');\" ");
                str.append("class='");
                if(wellnessfb.checkToStr(schedulevalues,"19"))
                {
                    str.append("week_selected ");
                }
                str.append("'>19</a></li>");
                str.append("<li><a href=\"javascript: checkmonth('20');\" ");
                str.append("class='");
                if(wellnessfb.checkToStr(schedulevalues,"20"))
                {
                    str.append("week_selected ");
                }
                str.append("'>20</a></li>");
                str.append("<li><a href=\"javascript: checkmonth('21');\" ");
                str.append("class='");
                if(wellnessfb.checkToStr(schedulevalues,"21"))
                {
                    str.append("week_selected ");
                }
                str.append("'>21</a></li>");
                str.append("<li><a href=\"javascript: checkmonth('22');\" ");
                str.append("class='");
                if(wellnessfb.checkToStr(schedulevalues,"22"))
                {
                    str.append("week_selected ");
                }
                str.append("'>22</a></li>");
                str.append("<li><a href=\"javascript: checkmonth('23');\" ");
                str.append("class='");
                if(wellnessfb.checkToStr(schedulevalues,"23"))
                {
                    str.append("week_selected ");
                }
                str.append("'>23</a></li>");
                str.append("<li><a href=\"javascript: checkmonth('24');\" ");
                str.append("class='");
                if(wellnessfb.checkToStr(schedulevalues,"24"))
                {
                    str.append("week_selected ");
                }
                str.append("'>24</a></li>");
                str.append("<li><a href=\"javascript: checkmonth('25');\" ");
                str.append("class='");
                if(wellnessfb.checkToStr(schedulevalues,"25"))
                {
                    str.append("week_selected ");
                }
                str.append("'>25</a></li>");
                str.append("<li><a href=\"javascript: checkmonth('26');\" ");
                str.append("class='");
                if(wellnessfb.checkToStr(schedulevalues,"26"))
                {
                    str.append("week_selected ");
                }
                str.append("'>26</a></li>");
                str.append("<li><a href=\"javascript: checkmonth('27');\" ");
                str.append("class='");
                if(wellnessfb.checkToStr(schedulevalues,"27"))
                {
                    str.append("week_selected ");
                }
                str.append("'>27</a></li>");
                str.append("<li><a href=\"javascript: checkmonth('28');\" ");
                str.append("class='");
                if(wellnessfb.checkToStr(schedulevalues,"28"))
                {
                    str.append("week_selected ");
                }
                str.append("'>28</a></li>");
                
                str.append("</ul>");
                str.append("</div>");
                str.append("<div class='full_width'>");
                str.append("<div class='form-check permission-check'>");
                str.append("<input class='form-check-input' type='checkbox' name='' id='' value='-2' onchange=\"javascript: checkmonth('-2');\" ");
                if(wellnessfb.checkToStr(schedulevalues,"-2"))
                {
                    str.append("checked");
                }
                str.append(">");
                str.append("<span class='last_day'>Last Day</span>");
                str.append("</div>");
                str.append("</div>");
                                                            
                String st = str.toString();
                str.setLength(0);
                response.getWriter().write(st);
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