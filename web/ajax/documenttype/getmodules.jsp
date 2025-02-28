<%@page import="java.util.Collection"%>
<%@page contentType="text/html"%>
<%@page language="java" import="java.util.ArrayList"%>
<%@page pageEncoding="UTF-8"%>
<%@page import="com.web.jxp.documenttype.DocumentTypeInfo" %>
<%@page import="com.web.jxp.user.UserInfo" %>
<jsp:useBean id="documentType" class="com.web.jxp.documenttype.DocumentType" scope="page"/>
<jsp:useBean id="vobj" class="com.web.jxp.base.Validate" scope="page"/>
<%
    try 
    {
        if (session.getAttribute("LOGININFO") != null) 
        {
            String str = "", docModuleIds = "", docSubModuleIds = "";
            if (request.getParameter("docModule") != null) 
            {
                if (session.getAttribute("DOCMODULE_IDs") != null) {
                    docModuleIds = (String) session.getAttribute("DOCMODULE_IDs");
                    request.removeAttribute("DOCMODULE_IDs");
                }
                if (session.getAttribute("DOCSUBMODULE_IDs") != null) {
                    docSubModuleIds = (String) session.getAttribute("DOCSUBMODULE_IDs");
                    request.removeAttribute("DOCSUBMODULE_IDs");
                }
                response.setContentType("text/html");
                response.setHeader("Cache-Control", "no-cache");
                docModuleIds = request.getParameter("docModule") != null && !request.getParameter("docModule").equals("") ? vobj.replacedesc(request.getParameter("docModule")) : "";
                StringBuilder sb = new StringBuilder();
                sb.append("<label class='form_label'>Applicable-Sub-Module(Optional)</label>");
                sb.append("<select name='docsubModule' id='docsubModulemultiselect_dd' class='form-select form-control btn btn-default mt-multiselect' multiple='multiple' data-select-all='true' data-label='left' data-width='100%' data-filter='false'>");

                if (docModuleIds.contains("1")) 
                {
                    sb.append("<option value='1' ");
                    if (documentType.checkToStr(docSubModuleIds, "1")) 
                    {
                        sb.append("selected ");
                    }
                    sb.append(">Enroll Candidate</option>");

                    sb.append("<option value='2' ");
                    if (documentType.checkToStr(docSubModuleIds, "2")) 
                    {
                        sb.append("selected ");
                    }
                    sb.append(">Talent Pool</option>");

                    sb.append("<option value='3' ");
                    if (documentType.checkToStr(docSubModuleIds, "3")) 
                    {
                        sb.append("selected ");
                    }
                    sb.append(">Mobilization</option>");
                    
                    sb.append("<option value='4' ");
                    if (documentType.checkToStr(docSubModuleIds, "4"))
                    {
                        sb.append("selected ");
                    }
                    sb.append(">Onboarding</option>");
                    if (docModuleIds.contains("2")) 
                    {
                        sb.append("<option value='5' ");
                        if (documentType.checkToStr(docSubModuleIds, "5"))
                        {
                            sb.append("selected ");
                        }
                        sb.append(">Training Matrix</option>");
                    }
                    if (docModuleIds.contains("3")) 
                    {
                        sb.append("<option value='6' ");
                        if (documentType.checkToStr(docSubModuleIds, "6"))
                        {
                            sb.append("selected ");
                        }
                        sb.append(">Document Issued by</option>");
                    }
                }                
                else if (docModuleIds.contains("2")) 
                {
                    sb.append("<option value='5' ");
                    if (documentType.checkToStr(docSubModuleIds, "5"))
                    {
                        sb.append("selected ");
                    }
                    sb.append(">Training Matrix</option>");
                    if (docModuleIds.contains("3")) 
                    {
                        sb.append("<option value='6' ");
                        if (documentType.checkToStr(docSubModuleIds, "6"))
                        {
                            sb.append("selected ");
                        }
                        sb.append(">Document Issued by</option>");
                    }
                }                
                else if (docModuleIds.contains("3")) 
                {
                    sb.append("<option value='6' ");
                    if (documentType.checkToStr(docSubModuleIds, "6"))
                    {
                        sb.append("selected ");
                    }
                    sb.append(">Document Issued by</option>");
                }
                sb.append("</select>");
                str = sb.toString();
                sb.setLength(0);
                response.getWriter().write(str);
            } else {
                response.setStatus(HttpServletResponse.SC_NO_CONTENT);
            }
        } else {
            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
        }
    } catch (Exception e) {
        e.printStackTrace();
        response.getWriter().write("Something went wrong.");
    }
%>