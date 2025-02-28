<%@page contentType="text/html"%>
<%@page language="java" %>
<%@page import="javax.servlet.http.*" %>
<%@page import="java.util.ArrayList" %>
<%@page import="java.io.File"%>
<%@page import="java.net.InetAddress" %>
<%@page import="com.web.jxp.user.UserInfo" %>
<jsp:useBean id="user" class="com.web.jxp.user.User" scope="page"/>
<jsp:useBean id="document" class="com.web.jxp.documentexpiry.Documentexpiry" scope="page"/>
<%
    session.removeAttribute("BACKURL");
    response.setContentType("text/plain");
    response.setHeader("Cache-Control", "no-cache");    
    if (request.getParameter("Username") != null)
    {
        String loginId = request.getParameter("Username"); 
        String password = request.getParameter("Password");
        String cap = request.getParameter("cap") != null ? request.getParameter("cap") : "";
        String cval = "";
        if (request.getSession().getAttribute("CVAL1") != null) 
        {
            cval = (String) request.getSession().getAttribute("CVAL1");
            session.removeAttribute("CVAL1");
        }
        String str = "";
        if (!cval.equals("") && !cap.equals("") && user.validateCaptcha(cap, cval))  
        {
            String ipAddrStr = "";
            String iplocal = user.getLocalIp();
            ipAddrStr = request.getRemoteAddr();
            if(ipAddrStr.equals("0:0:0:0:0:0:0:1"))
                ipAddrStr  = iplocal;
            int cc = 1;
            if(cc > 0)
            {
                UserInfo info = user.getUserAccess(loginId, password, ipAddrStr);
                if(info != null && info.getUserId() > 0)
                {                         
                    str = "S";
                    String permission = info.getPermission() != null ? info.getPermission() : "";
                    int userId = info.getUserId();
                    if(userId > 0)
                    {
                        String photo = info.getPhoto() != null ? info.getPhoto() : "";
                        user.createHistoryAccess(null, userId, ipAddrStr, iplocal, "Login", -1, userId);
                        
                        int uId = 0, allclient = 0;
                        String permissionr = "N", cids = "", assetids = "", username = "";                       
                        if (info != null) 
                        {
                            permissionr = info.getPermission();
                            cids = info.getCids();
                            allclient = info.getAllclient();
                            assetids = info.getAssetids();
                        }
                        int totalReminder = document.totalCounts(allclient, permissionr, cids, assetids);
                        
                        session.setAttribute("LOGININFO", info);
                        session.setAttribute("WELCOME", info.getName());
                        session.setAttribute("USERPHOTO", photo);
                        session.setAttribute("TOTALRCOUNT", ""+totalReminder);
                        boolean b = false;
                        if(permission.equals("Y"))
                            b = true;
                        ArrayList vec = user.getModuleListByName(userId, b);
                        request.getSession().setAttribute("PERMISSION_LIST", vec);                            
                        int smarr[] = new int[vec.size()];
                        int total = vec.size();
                        for(int i = 0; i < total; i ++)
                        {
                            UserInfo minfo = (UserInfo) vec.get(i);
                            if(minfo != null)
                                smarr[i] = minfo.getModuleId();
                        }
                        session.setAttribute("MARR", smarr);
                    }
                }
                else
                {
                    str = "N";
                    session.removeAttribute("LOGININFO");
                    session.removeAttribute("USERPHOTO");
                    session.removeAttribute("WELCOME");
                    session.removeAttribute("PERMISSION_LIST");
                    session.removeAttribute("MARR");
                }
            }
        }
        else
        {
            str = "INV";
            session.removeAttribute("LOGININFO");
            session.removeAttribute("USERPHOTO");
            session.removeAttribute("WELCOME");
            session.removeAttribute("PERMISSION_LIST");
            session.removeAttribute("MARR");
        }
        response.getWriter().write(str);
   }
   else
   {
        response.setStatus(HttpServletResponse.SC_NO_CONTENT);
   }
%>