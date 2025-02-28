<%@page import="java.util.LinkedList"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.Collection"%>
<%@page import="org.json.JSONObject"%>
<%@page import="java.io.BufferedReader"%>
<%@page contentType="text/html"%>
<%@page language="java" import="java.util.ArrayList"%>
<%@page import="com.web.jxp.user.UserInfo" %>
<%@page import="com.web.jxp.candidate.CandidateInfo" %> 
<jsp:useBean id="candidate" class="com.web.jxp.candidate.Candidate" scope="page"/>
<jsp:useBean id="vobj" class="com.web.jxp.base.Validate" scope="page"/>
<%
    try
    {
        UserInfo uInfo = (UserInfo) request.getSession().getAttribute("LOGININFO");
        String s = "";
        if(uInfo != null)
        {   
                StringBuffer sb = new StringBuffer();    
                String assettypeIds = request.getParameter("assettypeId") != null && !request.getParameter("assettypeId").equals("") ? vobj.replaceint(request.getParameter("assettypeId")) : "0";
                int assettypeId = Integer.parseInt(assettypeIds); 
                if (assettypeId > 0)
                {
                    Collection coll = candidate.getPositionassettypes(assettypeId);                    
                    if(coll.size() > 0)
                    {
                        Iterator iter = coll.iterator();
                        while (iter.hasNext())
                        {
                            CandidateInfo  rinfo = (CandidateInfo) iter.next();
                            int val =  rinfo.getDdlValue();
                            String n = rinfo.getDdlLabel()!= null ? rinfo.getDdlLabel(): "";
                            sb.append("<option value='"+val+"'>"+n+"</option>");
                        }
                    }
                    coll.clear();
                }
                else
                {
                    sb.append("<option value='-1'>- Select -</option>");
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