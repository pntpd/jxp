package com.web.jxp.createtraining;

import com.web.jxp.base.Validate;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import static com.web.jxp.common.Common.*;
import com.web.jxp.user.UserInfo;
import java.io.File;
import java.sql.Connection;
import java.util.Collection;
import java.util.Date;
import java.util.Stack;
import org.apache.struts.upload.FormFile;

public class CreatetrainingAction extends Action 
{
    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        CreatetrainingForm frm = (CreatetrainingForm) form;
        Createtraining createtraining = new Createtraining();
        Validate vobj = new Validate();
        int count = createtraining.getCount();
        String search = frm.getSearch() != null && !frm.getSearch().equals("") ? frm.getSearch() : "";
        frm.setSearch(search);
        int uId = 0;
        String permission = "N";
        if (request.getSession().getAttribute("LOGININFO") != null) {
            UserInfo uInfo = (UserInfo) request.getSession().getAttribute("LOGININFO");
            if (uInfo != null) 
            {
                permission = uInfo.getPermission();
                uId = uInfo.getUserId();
            }
        }
        int check_user = createtraining.checkUserSession(request, 60, permission);
        if (check_user == -1)
            return mapping.findForward("default");
        else if (check_user == -2) 
        {
            String authmess = createtraining.getMainPath("user_auth");
            authmess = authmess.replaceAll("__MODULE__", "Createtraining Module");
            request.setAttribute("AUTHMESSAGE", authmess);
            return mapping.findForward("auth");
        }
        if (frm.getDoAdd() != null && frm.getDoAdd().equals("yes"))
        {
            frm.setDoAdd("no");
            print(this, " doAdd block :: ");
            frm.setStatus(1);
            frm.setCategoryId(-1);
            saveToken(request);
            frm.setCategorycode((createtraining.changeNum(createtraining.getcategoryMaxId(), 3)));
            return mapping.findForward("add_category");
        }
        else if(frm.getDoModify() != null && frm.getDoModify().equals("yes"))
        {
             frm.setDoModify("no");
            int categoryId = frm.getCategoryId();
            frm.setCategoryId(categoryId);
            CreatetrainingInfo info = createtraining.getCreatetrainingDetailById(categoryId);
            if(info != null)
            {                
                frm.setName(info.getName());
                frm.setStatus(info.getStatus());
                frm.setCdescription(info.getDescription());
                frm.setCategorycode(createtraining.changeNum(categoryId, 3));
            }
            if (request.getAttribute("CATEGORYSAVEMODEL") != null) {

                    request.removeAttribute("CATEGORYSAVEMODEL");
                }
            saveToken(request);
            return mapping.findForward("add_category");
        }
        else if(frm.getDoView() != null && frm.getDoView().equals("yes"))
        {
            frm.setDoView("no");
            int categoryId = frm.getCategoryId();
            frm.setCategoryId(categoryId);
            frm.setCategorycode(createtraining.changeNum(categoryId, 3));
            CreatetrainingInfo info = createtraining.getCreatetrainingDetailByIdforDetail(categoryId);
            request.setAttribute("CREATETRAINING_DETAIL", info);
            return mapping.findForward("view_category");
        }        
        else if(frm.getDoSave() != null && frm.getDoSave().equals("yes"))
        {
             frm.setDoSave("no");
            print(this,"getDoSave block.");
            if(isTokenValid(request))
            {
                resetToken(request);
                int categoryId = frm.getCategoryId();
                String name = vobj.replacename(frm.getName());
                String description = vobj.replacedesc(frm.getCdescription());
                int status = 1;
                String ipAddrStr = request.getRemoteAddr();
                String iplocal = createtraining.getLocalIp();
                int ck = createtraining.checkDuplicacy(categoryId, name);
                if(ck == 1)
                {
                    saveToken(request);
                    request.setAttribute("MESSAGE", "Category already exists");
                    return mapping.findForward("add_createtraining");
                }
                //add_trainingfiles               
                
                CreatetrainingInfo info = new CreatetrainingInfo(categoryId, name, status, uId, description);
                if(categoryId <= 0)
                {
                    int cc = createtraining.createCreatetraining(info);
                    if(cc > 0)
                    {
                       createtraining.createHistoryAccess(null, uId, ipAddrStr, iplocal, "Data Added", 60, cc);
                       request.setAttribute("MESSAGE", "Data added successfully.");
                       
                    }
                    request.setAttribute("CATEGORYSAVEMODEL", "yes");
                    frm.setCategoryId(cc);
                    ArrayList createtrainingList = createtraining.getCreatetrainingByName(search, 0, count);
                    int cnt = 0;
                    if(createtrainingList.size() > 0)
                    {
                        CreatetrainingInfo cinfo = (CreatetrainingInfo) createtrainingList.get(createtrainingList.size() - 1);
                        cnt = cinfo.getCategoryId();
                        createtrainingList.remove(createtrainingList.size() - 1);
                    }
                    request.getSession().setAttribute("CREATETRAINING_LIST", createtrainingList);
                    request.getSession().setAttribute("COUNT_LIST", cnt+"");
                    request.getSession().setAttribute("NEXT", "0");
                    request.getSession().setAttribute("NEXTVALUE", "1");
                    
                    return mapping.findForward("display");
                }
                else
                {
                    createtraining.updateCreatetraining(info);
                    createtraining.createHistoryAccess(null, uId, ipAddrStr, iplocal, "Data Updated", 60, categoryId);
                    request.setAttribute("MESSAGE", "Data updated successfully.");
                    int next = 0;
                    if(request.getSession().getAttribute("NEXTVALUE") != null)
                    {
                        next = Integer.parseInt((String) request.getSession().getAttribute("NEXTVALUE"));
                    }
                    next = next - 1;
                    if(next < 0)
                        next = 0;
                    ArrayList createtrainingList = createtraining.getCreatetrainingByName(search, next, count);
                    int cnt = 0;
                    if(createtrainingList.size() > 0)
                    {
                        CreatetrainingInfo cinfo = (CreatetrainingInfo) createtrainingList.get(createtrainingList.size() - 1);
                        cnt = cinfo.getCategoryId();
                        createtrainingList.remove(createtrainingList.size() - 1);
                    }
                    request.getSession().setAttribute("CREATETRAINING_LIST", createtrainingList);
                    request.getSession().setAttribute("COUNT_LIST", cnt+"");
                    request.getSession().setAttribute("NEXT", next+"");
                    request.getSession().setAttribute("NEXTVALUE", (next+1)+"");
                    
                    return mapping.findForward("display");
                }                
            }
        }
        else if(frm.getDoIndexSubcategory()!= null && frm.getDoIndexSubcategory().equals("yes"))
        {
            frm.setDoAddSubcategory("no");
            print(this,"getDoIndexSubcategory block.");
            int categoryId = frm.getCategoryId();
            frm.setCategoryId(categoryId);
            ArrayList list  = createtraining.getsubcategoryDetailByIdforDetail(categoryId);
            request.getSession().setAttribute("SUBCATEGORY_LIST", list);
            return mapping.findForward("view_subcategory");
        }
        else if (frm.getDoAddSubcategory()!= null && frm.getDoAddSubcategory().equals("yes"))
        {
            frm.setDoAdd("no");
            print(this, " getDoAddSubcategory block :: ");
            frm.setStatus(1);
            frm.setSubcategoryId(-1);
            int categoryId = frm.getCategoryId();
            frm.setCategoryId(categoryId);
            saveToken(request);
            frm.setCategorycode(createtraining.changeNum(categoryId, 3));
            frm.setCategoryname(createtraining.getcategoryname(categoryId));
            return mapping.findForward("add_subcategory");
        }
        else if(frm.getDoModifysubcategory()!= null && frm.getDoModifysubcategory().equals("yes"))
        {
             frm.setDoModifysubcategory("no");
             print(this, " getDoModifysubcategory block :: ");
            int categoryId = frm.getCategoryId();
            frm.setCategoryId(categoryId);
            int subcategoryId = frm.getSubcategoryId();
            frm.setSubcategoryId(subcategoryId);
            CreatetrainingInfo info = createtraining.getsubcategoryDetailById(categoryId,subcategoryId);
            if(info != null)
            {                
                frm.setName(info.getName());
                frm.setStatus(info.getStatus());
                frm.setCdescription(info.getDescription());
                frm.setCategorycode(createtraining.changeNum(categoryId, 3));
                frm.setCategoryname(info.getCategoryname());
            }
             if (request.getAttribute("SUBCATEGORYSAVEMODEL") != null) {

                    request.removeAttribute("SUBCATEGORYSAVEMODEL");
                }
            saveToken(request);
            return mapping.findForward("add_subcategory");
        }
        else if(frm.getDoSaveSubcategory()!= null && frm.getDoSaveSubcategory().equals("yes"))
        {
             frm.setDoSaveSubcategory("no");
            print(this,"getDoSaveSubcategory block.");
            if(isTokenValid(request))
            {
                resetToken(request);
                int categoryId = frm.getCategoryId();
                int subcategoryId = frm.getSubcategoryId();
                String name = vobj.replacename(frm.getName());
                String description = vobj.replacedesc(frm.getCdescription());
                int status = 1;
                String ipAddrStr = request.getRemoteAddr();
                String iplocal = createtraining.getLocalIp();
                int ck = createtraining.checkDuplicacysubcategory(categoryId, name,subcategoryId);
                if(ck == 1)
                {
                    saveToken(request);
                    request.setAttribute("MESSAGE", "SubCategory already exists");
                    return mapping.findForward("add_createtraining");
                }
                //add_trainingfiles
                
                
                CreatetrainingInfo info = new CreatetrainingInfo(categoryId, subcategoryId, name, status, uId, description);
                if(subcategoryId <= 0)
                {
                    int cc = createtraining.createsubCategory(info);
                    if(cc > 0)
                    {
                       createtraining.createHistoryAccess(null, uId, ipAddrStr, iplocal, "Data Added", 60, cc);
                       request.setAttribute("MESSAGE", "Data added successfully.");
                    }
                    request.setAttribute("SUBCATEGORYSAVEMODEL", "yes");
                    frm.setCategoryId(categoryId);
                    frm.setSubcategoryId(cc);
                    ArrayList list  = createtraining.getsubcategoryDetailByIdforDetail(categoryId);
                    request.getSession().setAttribute("SUBCATEGORY_LIST", list);
                    return mapping.findForward("view_subcategory");
                }
                else
                {
                    createtraining.updatesubCategory(info);
                    createtraining.createHistoryAccess(null, uId, ipAddrStr, iplocal, "Data Updated", 60, categoryId);
                    request.setAttribute("MESSAGE", "Data updated successfully.");
                }          
                    ArrayList list  = createtraining.getsubcategoryDetailByIdforDetail(categoryId);
                    request.getSession().setAttribute("SUBCATEGORY_LIST", list);
                    return mapping.findForward("view_subcategory");
            }
        }
        else if(frm.getDodeletesubcategory()!= null && frm.getDodeletesubcategory().equals("yes"))
        {
            frm.setDodeletesubcategory("no");
            print(this,"getDoIndexSubcategory block.");
            int categoryId = frm.getCategoryId();
            frm.setCategoryId(categoryId);
            int subcategoryId = frm.getSubcategoryId();
            frm.setSubcategoryId(0);
            int substatus = frm.getSubstatus();
            frm.setSubstatus(0);
            String ipAddrStr = request.getRemoteAddr();
            String iplocal = createtraining.getLocalIp();
            createtraining.deletesubcategory(subcategoryId, uId, substatus, ipAddrStr, iplocal);
            ArrayList list  = createtraining.getsubcategoryDetailByIdforDetail(categoryId);
            request.getSession().setAttribute("SUBCATEGORY_LIST", list);
            return mapping.findForward("view_subcategory");
        }
        else if(frm.getDoIndexCourse()!= null && frm.getDoIndexCourse().equals("yes"))
        {
            frm.setDoIndexCourse("no");
            print(this,"getDoIndexCourse block.");
            int categoryId = frm.getCategoryId();
            frm.setCategoryId(categoryId);
            Collection subcategorys = createtraining.getSubcategory(categoryId);
            frm.setSubcategorys(subcategorys);
            int subcategoryIdIndex = frm.getSubcategoryIdIndex();
            frm.setSubcategoryIdIndex(subcategoryIdIndex);
            ArrayList list  = createtraining.getcourseslistByIdandIndexforDetail(categoryId,subcategoryIdIndex);
            request.getSession().setAttribute("COURSE_LIST", list);
            return mapping.findForward("view_course");
        }
        else if (frm.getDoAddCourse()!= null && frm.getDoAddCourse().equals("yes"))
        {
            frm.setDoAddCourse("no");
            print(this, " getDoAddCourse block :: ");
            frm.setStatus(1);
            int categoryId = frm.getCategoryId();
            frm.setCategoryId(categoryId);
            int subcategoryId = frm.getSubcategoryId();
            frm.setSubcategoryId(subcategoryId);
            frm.setCourseId(-1);
            Collection subcategorys = createtraining.getSubcategory(categoryId);
            frm.setSubcategorys(subcategorys);
            frm.setEsubcategoryId(frm.getEsubcategoryId());
            Collection coursetypes = createtraining.getCourseType();
            frm.setCoursetypes(coursetypes);
            frm.setCoursetypeId(-1);
            
            Collection coursenames = createtraining.getCoursename();
            frm.setCoursenames(coursenames);
            frm.setCoursenameId(-1);
            
            saveToken(request);
            frm.setCategoryname(createtraining.getcategoryname(categoryId));
            frm.setCategorycode(createtraining.changeNum(categoryId, 3));
            return mapping.findForward("add_course");
        }
        else if(frm.getDoModifycourse()!= null && frm.getDoModifycourse().equals("yes"))
        {
             frm.setDoModifycourse("no");
             print(this, " getDoModifycourse block :: ");
            int categoryId = frm.getCategoryId();
            frm.setCategoryId(categoryId);
            int subcategoryId = frm.getSubcategoryId();
            frm.setSubcategoryId(subcategoryId);
            int courseId = frm.getCourseId();
            frm.setCourseId(courseId);
            frm.setCategorycode(createtraining.changeNum(categoryId, 3));
            frm.setCategoryname(createtraining.getcategoryname(categoryId));
            CreatetrainingInfo info = createtraining.getcourseDetailById(categoryId,subcategoryId,courseId);
            if(info != null)
            {                
                frm.setName(info.getName());
                frm.setStatus(info.getStatus());
                frm.setCdescription(info.getDescription());
                frm.setCoursecode(createtraining.changeNum(courseId, 3));
                Collection subcategorys = createtraining.getSubcategory(categoryId);
                frm.setSubcategorys(subcategorys);
                frm.setEsubcategoryId(subcategoryId);

                Collection coursetypes = createtraining.getCourseType();
                frm.setCoursetypes(coursetypes);
                frm.setCoursetypeId(info.getCoursetypeId());

                Collection coursenames = createtraining.getCoursename();
                frm.setCoursenames(coursenames);
                frm.setCoursenameId(info.getCoursenameId());
                
                if (info.getElearningfile()!= null) {
                    frm.setEfilehidden(info.getElearningfile());
                }                
            }
            if (request.getAttribute("COURSESAVEMODEL") != null) {

                    request.removeAttribute("COURSESAVEMODEL");
                }
            saveToken(request);
            return mapping.findForward("add_course");
        }
        else if(frm.getDoSaveCourse()!= null && frm.getDoSaveCourse().equals("yes"))
        {
            frm.setDoSaveCourse("no");
            print(this,"getDoSaveCourse block.");
            if(isTokenValid(request))
            {
                resetToken(request);
                int categoryId = frm.getCategoryId();
                int subcategoryId = frm.getSubcategoryId();
                String description = vobj.replacedesc(frm.getCdescription());
                int esubcategoryId = frm.getEsubcategoryId();
                int coursetypeId = frm.getCoursetypeId();
                int coursenameId = frm.getCoursenameId();
                
                int courseId = frm.getCourseId();
                int status = 1;
                String ipAddrStr = request.getRemoteAddr();
                String iplocal = createtraining.getLocalIp();
                int ck = createtraining.checkDuplicacycourse(categoryId, coursenameId,esubcategoryId,courseId);
                if(ck == 1)
                {
                    frm.setCategoryId(categoryId);
                    frm.setSubcategoryId(subcategoryId);
                    frm.setCourseId(-1);
                    Collection subcategorys = createtraining.getSubcategory(categoryId);
                    frm.setSubcategorys(subcategorys);
                    frm.setEsubcategoryId(frm.getEsubcategoryId());
                    Collection coursetypes = createtraining.getCourseType();
                    frm.setCoursetypes(coursetypes);
                    frm.setCoursetypeId(coursetypeId);

                    Collection coursenames = createtraining.getCoursename();
                    frm.setCoursenames(coursenames);
                    frm.setCoursenameId(coursenameId);
                    
                    saveToken(request);
                    request.setAttribute("MESSAGE", "Course already exists");
                    return mapping.findForward("add_course");
                }
                //add_trainingfiles
                
                String add_trainingfiles = createtraining.getMainPath("add_trainingfiles");
                String foldername = createtraining.createFolder(add_trainingfiles);
                Date now = new Date();
                String fn = String.valueOf(now.getTime());
                
                String fileName1 = "";
                FormFile filename = frm.getElearningfile();
                if (filename != null && filename.getFileSize() > 0) 
                {
                    fileName1 = createtraining.uploadFile(categoryId, frm.getEfilehidden(), filename, fn + "_1", add_trainingfiles, foldername);
                }
                File dir = new File(add_trainingfiles + foldername + "/" + fn + "_uz/");
                if (!dir.exists()) {
                    dir.mkdir();
                }
                if (filename != null && filename.getFileSize() > 0) 
                {
                    createtraining.unzip(add_trainingfiles + fileName1, add_trainingfiles + foldername + "/" + fn + "_uz/");
                    fileName1 = foldername + "/" + fn + "_uz/";
                }
                CreatetrainingInfo info = new CreatetrainingInfo(categoryId, esubcategoryId, coursenameId, status, uId, description,courseId, coursetypeId, fileName1);                
               
                if(courseId <= 0)
                {
                    int cc = createtraining.createcourse(info);
                    if(cc > 0)
                    {
                        String fname = frm.getFname() != null ? frm.getFname() : "";
                        if (!"".equals(fname)) {
                            String fnameval[] = fname.split("@#@");
                            int len = fnameval.length;
                            Connection conn = null;
                            try {
                                conn = createtraining.getConnection();
                                for (int i = 0; i < len; i++) {
                                    String fileName = createtraining.saveImage(fnameval[i], add_trainingfiles, foldername, fn + "_" + i);
                                    createtraining.savecourseattachment(conn, cc, fileName, uId);
                                }
                            } finally {
                                if (conn != null) {
                                    conn.close();
                                }
                            }
                        }
                        
                       createtraining.createHistoryAccess(null, uId, ipAddrStr, iplocal, "Data Added", 60, cc);
                       request.setAttribute("MESSAGE", "Data added successfully.");
                    }
                    request.setAttribute("COURSESAVEMODEL", "yes");
                    Collection subcategorys = createtraining.getSubcategory(categoryId);
                    frm.setSubcategorys(subcategorys);
                    frm.setSubcategoryIdIndex(-1);
                    ArrayList list  = createtraining.getcourseslistByIdforDetail(categoryId);
                    request.getSession().setAttribute("COURSE_LIST", list);
                    return mapping.findForward("view_course");
                }
                else
                {
                    createtraining.updatecourse(info);
                    String fname = frm.getFname() != null ? frm.getFname() : "";
                    if (!"".equals(fname)) {
                        String fnameval[] = fname.split("@#@");
                        int len = fnameval.length;
                        Connection conn = null;
                        try {
                            conn = createtraining.getConnection();
                            for (int i = 0; i < len; i++) {
                                String fileName = createtraining.saveImage(fnameval[i], add_trainingfiles, foldername, fn + "_" + i);
                                createtraining.savecourseattachment(conn, courseId, fileName, uId);
                            }
                        } finally {
                            if (conn != null) {
                                conn.close();
                            }
                        }
                    }
                    createtraining.createHistoryAccess(null, uId, ipAddrStr, iplocal, "Data Updated", 60, categoryId);
                    request.setAttribute("MESSAGE", "Data updated successfully.");
                }  
                    Collection subcategorys = createtraining.getSubcategory(categoryId);
                    frm.setSubcategorys(subcategorys);
                    frm.setSubcategoryIdIndex(-1);
                    ArrayList list = createtraining.getcourseslistByIdforDetail(categoryId);
                    request.getSession().setAttribute("COURSE_LIST", list);
                    return mapping.findForward("view_course");
            }
        }
        else if(frm.getDodeleteCourse()!= null && frm.getDodeleteCourse().equals("yes"))
        {
            frm.setDodeleteCourse("no");
            print(this,"getDodeleteCourse block.");
            int categoryId = frm.getCategoryId();
            frm.setCategoryId(categoryId);
            Collection subcategorys = createtraining.getSubcategory(categoryId);
            frm.setSubcategorys(subcategorys);
            int subcategoryIdIndex = frm.getSubcategoryIdIndex();
            frm.setSubcategoryIdIndex(subcategoryIdIndex);
            int courseId = frm.getCourseId();
            frm.setCourseId(0);
            
            int substatus = frm.getSubstatus();
            frm.setSubstatus(0);
            String ipAddrStr = request.getRemoteAddr();
            String iplocal = createtraining.getLocalIp();
            createtraining.deletecourse(courseId, uId, substatus, ipAddrStr, iplocal);
            ArrayList list  = createtraining.getcourseslistByIdandIndexforDetail(categoryId,subcategoryIdIndex);
            request.getSession().setAttribute("COURSE_LIST", list);
            return mapping.findForward("view_course");
        }
        else if(frm.getDoCancel() != null && frm.getDoCancel().equals("yes"))
        {
            frm.setDoCancel("no");
            print(this,"doCancel block");
            int next = 0;
            if(request.getSession().getAttribute("NEXTVALUE") != null)
            {
                next = Integer.parseInt((String) request.getSession().getAttribute("NEXTVALUE"));
            }
            next = next - 1;
            if(next < 0)
                next = 0;
            ArrayList createtrainingList = createtraining.getCreatetrainingByName(search, next, count);
            int cnt = 0;
            if(createtrainingList.size() > 0)
            {
                CreatetrainingInfo cinfo = (CreatetrainingInfo) createtrainingList.get(createtrainingList.size() - 1);
                cnt = cinfo.getCategoryId();
                createtrainingList.remove(createtrainingList.size() - 1);
            }
            request.getSession().setAttribute("CREATETRAINING_LIST", createtrainingList);
            request.getSession().setAttribute("COUNT_LIST", cnt+"");
            request.getSession().setAttribute("NEXT", next + "");
            request.getSession().setAttribute("NEXTVALUE", (next+1) +"");
            return mapping.findForward("display");
        }
        else
        {
            print(this, "else block.");
            if (frm.getCtp() == 0) {
                Stack<String> blist = new Stack<String>();
                if (request.getSession().getAttribute("BACKURL") != null) {
                    blist = (Stack<String>) request.getSession().getAttribute("BACKURL");
                }
                blist.push(request.getRequestURI());
                request.getSession().setAttribute("BACKURL", blist);
            }
            ArrayList createtrainingList = createtraining.getCreatetrainingByName(search, 0, count);
            int cnt = 0;
            if(createtrainingList.size() > 0)
            {
                CreatetrainingInfo cinfo = (CreatetrainingInfo) createtrainingList.get(createtrainingList.size() - 1);
                cnt = cinfo.getCategoryId();
                createtrainingList.remove(createtrainingList.size() - 1);
            }
            request.getSession().setAttribute("CREATETRAINING_LIST", createtrainingList);
            request.getSession().setAttribute("COUNT_LIST", cnt+"");
            request.getSession().setAttribute("NEXT", "0");
            request.getSession().setAttribute("NEXTVALUE", "1");
        }
        return mapping.findForward("display");
    }    
}