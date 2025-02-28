<%@page import="java.util.LinkedList"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.Collection"%>
<%@page import="org.json.JSONObject"%>
<%@page import="java.io.BufferedReader"%>
<%@page contentType="text/html"%>
<%@page language="java" import="java.util.ArrayList"%>
<%@page import="com.web.jxp.user.UserInfo" %>
<%@page import="com.web.jxp.talentpool.TalentpoolInfo" %>
<jsp:useBean id="ddl" class="com.web.jxp.talentpool.Ddl" scope="page"/>
<jsp:useBean id="vobj" class="com.web.jxp.base.Validate" scope="page"/>
<%
    try
    {
        UserInfo uInfo = (UserInfo) request.getSession().getAttribute("LOGININFO");
        String s = "";
        if(uInfo != null)
        {   
            StringBuffer sb = new StringBuffer();    
            String ppetypeIds = request.getParameter("ppetypeId") != null && !request.getParameter("ppetypeId").equals("") ? vobj.replaceint(request.getParameter("ppetypeId")) : "0";
            String ppedetailIds = request.getParameter("ppedetailId") != null && !request.getParameter("ppedetailId").equals("") ? vobj.replaceint(request.getParameter("ppedetailId")) : "0";
            int ppetypeId = Integer.parseInt(ppetypeIds);             
            int ppedetailId = Integer.parseInt(ppedetailIds);
            if (ppetypeId > 0)
            {
                s = ddl.getPpeNameAndId(ppetypeId);                
                String[] s2 = s.split(",");
                int size = s2.length;
                if(s != null && !s.equals(""))
                {  
                    if(size > 0)
                    {
                        sb.append("<label class='form_label'>Select Size<span class='required' >*</span></label>");   
                        sb.append("<select name='remark' id='remark' class='form-select form-control'>");
                        sb.append("<option value=''>- Select -</option>");
                        String s1 = ddl.getPpeNameAndId2(ppedetailId);
                        for(int i= 0; i<size; i++)
                        {
                            if(s2[i].equalsIgnoreCase(s1))                   
                                sb.append("<option selected value='"+s2[i]+"'>"+s2[i]+"</option>");
                            else
                                sb.append("<option value='"+s2[i]+"'>"+s2[i]+"</option>");                            
                        }
                        sb.append("</select>");
                    }
                }else
                {
                    
                    sb.append("<div class='col-lg-12 col-md-12 col-sm-12 col-12 form_group' id='ppeDiv2'>");
                        sb.append("<label class='form_label'>Enter Size</label>");                            
                        sb.append("<div class='input-group'>");
                        if(ppedetailId > 0)
                        {
                            String s3 = ddl.getPpeNameAndId2(ppedetailId);
                            sb.append("<input type='text' class='form-control' name='remark' value='" +s3+ "' maxlength='100' />");
                        }else{
                            sb.append("<input type='text' class='form-control' name='remark' maxlength='100' />");
                        }
                        sb.append("</div>");                            
                    sb.append("</div>");
                }
            }
            s = sb.toString();
            sb.setLength(0);                
            }
            response.getWriter().write(s);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
%>