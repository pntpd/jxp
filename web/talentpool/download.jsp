<%@page import="java.util.zip.ZipOutputStream"%>
<%@page import="java.io.BufferedOutputStream"%>
<%@page import="java.util.zip.ZipEntry"%>
<%@page import="java.io.FileInputStream"%>
<%@page import="java.io.FileNotFoundException"%>
<%@page import="java.io.BufferedInputStream"%>
<%@page import="java.io.File"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html"%>
<%@page language="java" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@page import="com.web.jxp.talentpool.TalentpoolInfo" %>
<jsp:useBean id="talentpool" class="com.web.jxp.talentpool.Talentpool" scope="page"/>
<jsp:useBean id="vobj" class="com.web.jxp.base.Validate" scope="page"/>
<%
    String candidateId_s = request.getParameter("candidateId") != null && !request.getParameter("candidateId").equals("") ? request.getParameter("candidateId") : "0";
    String fn = request.getParameter("fn") != null && !request.getParameter("fn").equals("") ? vobj.replacename(request.getParameter("fn")) : "";
    candidateId_s = talentpool.decipher(candidateId_s);
    int candidateId = Integer.parseInt(candidateId_s);
    if(candidateId > 0)
    {
        String fname = fn+"-"+talentpool.changeNum(candidateId, 6);        
        ArrayList list = talentpool.getPics(candidateId);
        int size = list.size();        
        if(size > 0)
        {
            String add_candidate_file = talentpool.getMainPath("add_candidate_file");
            response.setContentType("Content-type: text/zip");
            response.setHeader("Content-Disposition", "attachment; filename="+fname+".zip");
            ServletOutputStream outst = response.getOutputStream();
            ZipOutputStream zos = new ZipOutputStream(new BufferedOutputStream(outst));
            for(int i = 0; i < size; i++)  
            {
                TalentpoolInfo info = (TalentpoolInfo) list.get(i);
                if(info != null && info.getFilename() != null && !info.getFilename().equals(""))
                {
                    String localFile = info.getLocalFile() != null ? info.getLocalFile() : "";
                    if(localFile.equals(""))
                    {
                        if(info.getFilename().indexOf("/") != -1)
                            localFile = info.getFilename().substring(info.getFilename().lastIndexOf("/")+1);
                        else
                            localFile = info.getFilename();
                    }
                    File file = new File(add_candidate_file+info.getFilename());
                    zos.putNextEntry(new ZipEntry(localFile));
                    // Get the file
                    FileInputStream fis = null;
                    try 
                    {
                        fis = new FileInputStream(file);
                    }
                    catch (FileNotFoundException fnfe) 
                    {
                        zos.write(("ERRORld not find file " + file.getName()).getBytes());
                        zos.closeEntry();
                        continue;
                    }
                    BufferedInputStream fif = new BufferedInputStream(fis);
                    // Write the contents of the file
                    int data = 0;
                    while ((data = fif.read()) != -1) {
                            zos.write(data);
                    }
                    fif.close();
                    zos.closeEntry();   
                }
            }
            zos.flush();
            zos.close();
            outst.flush();
            outst.close();   
        }
    }
%>
