<%@page import="com.web.jxp.candidate.CandidateInfo"%>
<%@page import="com.web.jxp.relation.RelationInfo"%>
<%@page import="com.web.jxp.experiencedept.ExperienceDeptInfo"%>
<%@page import="java.util.LinkedList"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.Collection"%>
<%@page import="org.json.JSONObject"%>
<%@page import="java.io.BufferedReader"%>
<%@page contentType="text/html"%>
<%@page language="java" import="java.util.ArrayList"%>
<%@page import="com.web.jxp.user.UserInfo" %>
<%@page import="com.web.jxp.client.ClientInfo" %>  
<jsp:useBean id="client" class="com.web.jxp.client.Client" scope="page"/>
<jsp:useBean id="relation" class="com.web.jxp.relation.Relation" scope="page"/>
<jsp:useBean id="experiencedept" class="com.web.jxp.experiencedept.ExperienceDept" scope="page"/>
<jsp:useBean id="candidate" class="com.web.jxp.candidate.Candidate" scope="page"/>
<jsp:useBean id="vobj" class="com.web.jxp.base.Validate" scope="page"/>
<%
    try 
    {
        UserInfo uInfo = (UserInfo) request.getSession().getAttribute("LOGININFO");
        if (uInfo != null)
        {
            int userId = uInfo.getUserId();
            String name = request.getParameter("name") != null ? vobj.replacename(request.getParameter("name")) : "";
            String type_s = request.getParameter("type") != null && !request.getParameter("type").equals("") ? vobj.replaceint(request.getParameter("type")) : "0";
            int type = Integer.parseInt(type_s);
            if (type > 0 && !name.equals(""))
            {
                int countryId = 0;
                String s = "";
                if (type == 3 || type ==4) {
                    String countryIds = request.getParameter("countryId") != null && !request.getParameter("countryId").equals("") ? vobj.replaceint(request.getParameter("countryId")) : "0";
                    countryId = Integer.parseInt(countryIds);
                }
                int cc = client.createMaster(type, name, userId, countryId);
                if (cc == -2) {
                    s = "No";
                } else {
                    if (type != 3) {
                        Collection coll = new LinkedList();
                        if (type == 1) {
                            coll = relation.getRelations();
                        } else if (type == 2) {
                            coll = experiencedept.getExperienceDepts();
                        }
                        else if (type == 4) {
                            coll = candidate.getCountryStates(countryId);
                        }
                        StringBuffer sb = new StringBuffer();
                        if (coll.size() > 0) 
                        {
                            Iterator iter = coll.iterator();
                            while (iter.hasNext()) {
                                if (type == 1) {
                                    RelationInfo rinfo = (RelationInfo) iter.next();
                                    int val = rinfo.getDdlValue();
                                    String n = rinfo.getDdlLabel() != null ? rinfo.getDdlLabel() : "";
                                    if (val == cc) {
                                        sb.append("<option value='" + val + "' selected>" + n + "</option>");
                                    } else {
                                        sb.append("<option value='" + val + "'>" + n + "</option>");
                                    }
                                } 
                                else if (type == 2) {
                                    ExperienceDeptInfo dinfo = (ExperienceDeptInfo) iter.next();
                                    int val = dinfo.getDdlValue();
                                    String n = dinfo.getDdlLabel() != null ? dinfo.getDdlLabel() : "";
                                    if (val == cc) {
                                        sb.append("<option value='" + val + "' selected>" + n + "</option>");
                                    } else {
                                        sb.append("<option value='" + val + "'>" + n + "</option>");
                                    }
                                }                                
                                else if (type == 4) 
                                {
                                    CandidateInfo dinfo = (CandidateInfo) iter.next();
                                    int val = dinfo.getDdlValue();
                                    String n = dinfo.getDdlLabel() != null ? dinfo.getDdlLabel() : "";
                                    if (val == cc) {
                                        sb.append("<option value='" + val + "' selected>" + n + "</option>");
                                    } else {
                                        sb.append("<option value='" + val + "'>" + n + "</option>");
                                    }
                                }
                            }
                        }
                        s = sb.toString();
                        sb.setLength(0);
                        coll.clear();
                    } else {
                        s = name + "#@#" + cc;
                    }
                }
                response.getWriter().write(s);
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
%>