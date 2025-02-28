package com.web.jxp.knowledgecontent;

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

public class KnowledgecontentAction extends Action {

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        KnowledgecontentForm frm = (KnowledgecontentForm) form;
        Knowledgecontent knowledgecontent = new Knowledgecontent();
        Validate vobj = new Validate();
        int count = knowledgecontent.getCount();
        String search = frm.getSearch() != null && !frm.getSearch().equals("") ? frm.getSearch() : "";
        frm.setSearch(search);
        int uId = 0;
        String permission = "N";
        if (request.getSession().getAttribute("LOGININFO") != null) 
        {
            UserInfo uInfo = (UserInfo) request.getSession().getAttribute("LOGININFO");
            if (uInfo != null)
            {
                permission = uInfo.getPermission();
                uId = uInfo.getUserId();
            }
        }
        int check_user = knowledgecontent.checkUserSession(request, 93, permission);
        if (check_user == -1) {
            return mapping.findForward("default");
        } else if (check_user == -2) {
            String authmess = knowledgecontent.getMainPath("user_auth");
            authmess = authmess.replaceAll("__MODULE__", "Knowledgecontent Module");
            request.setAttribute("AUTHMESSAGE", authmess);
            return mapping.findForward("auth");
        }
        if (frm.getDoAdd() != null && frm.getDoAdd().equals("yes")) {
            frm.setDoAdd("no");
            print(this, " doAdd block :: ");
            frm.setStatus(1);
            frm.setCategoryId(-1);
            saveToken(request);
            frm.setCategorycode((knowledgecontent.changeNum(knowledgecontent.getcategoryMaxId(), 3)));
            return mapping.findForward("add_category");
        } else if (frm.getDoModify() != null && frm.getDoModify().equals("yes")) {
            frm.setDoModify("no");
            int categoryId = frm.getCategoryId();
            frm.setCategoryId(categoryId);
            KnowledgecontentInfo info = knowledgecontent.getKnowledgecontentDetailById(categoryId);
            if (info != null) {
                frm.setName(info.getName());
                frm.setStatus(info.getStatus());
                frm.setCdescription(info.getDescription());
                frm.setCategorycode(knowledgecontent.changeNum(categoryId, 3));
            }
            if (request.getAttribute("CATEGORYSAVEMODEL") != null) {

                request.removeAttribute("CATEGORYSAVEMODEL");
            }
            saveToken(request);
            return mapping.findForward("add_category");
        } else if (frm.getDoView() != null && frm.getDoView().equals("yes")) {
            frm.setDoView("no");
            int categoryId = frm.getCategoryId();
            frm.setCategoryId(categoryId);
            frm.setCategorycode(knowledgecontent.changeNum(categoryId, 3));
            KnowledgecontentInfo info = knowledgecontent.getKnowledgecontentDetailByIdforDetail(categoryId);
            request.setAttribute("CREATETRAINING_DETAIL", info);
            return mapping.findForward("view_category");
        } else if (frm.getDoSave() != null && frm.getDoSave().equals("yes")) {
            frm.setDoSave("no");
            print(this, "getDoSave block.");
            if (isTokenValid(request)) {
                resetToken(request);
                int categoryId = frm.getCategoryId();
                String name = vobj.replacename(frm.getName());
                String description = vobj.replacedesc(frm.getCdescription());
                int status = 1;
                String ipAddrStr = request.getRemoteAddr();
                String iplocal = knowledgecontent.getLocalIp();
                int ck = knowledgecontent.checkDuplicacy(categoryId, name);
                if (ck == 1) {
                    saveToken(request);
                    request.setAttribute("MESSAGE", "Category already exists");
                    return mapping.findForward("add_createtraining");
                }
                //add_trainingfiles

                KnowledgecontentInfo info = new KnowledgecontentInfo(categoryId, name, status, uId, description);
                if (categoryId <= 0) {
                    int cc = knowledgecontent.createKnowledgecontent(info);
                    if (cc > 0) {
                        knowledgecontent.createHistoryAccess(null, uId, ipAddrStr, iplocal, "Data Added", 60, cc);
                        request.setAttribute("MESSAGE", "Data added successfully.");

                    }
                    request.setAttribute("CATEGORYSAVEMODEL", "yes");
                    frm.setCategoryId(cc);
                    ArrayList createtrainingList = knowledgecontent.getKnowledgecontentByName(search, 0, count);
                    int cnt = 0;
                    if (createtrainingList.size() > 0) {
                        KnowledgecontentInfo cinfo = (KnowledgecontentInfo) createtrainingList.get(createtrainingList.size() - 1);
                        cnt = cinfo.getCategoryId();
                        createtrainingList.remove(createtrainingList.size() - 1);
                    }
                    request.getSession().setAttribute("CREATETRAINING_LIST", createtrainingList);
                    request.getSession().setAttribute("COUNT_LIST", cnt + "");
                    request.getSession().setAttribute("NEXT", "0");
                    request.getSession().setAttribute("NEXTVALUE", "1");

                    return mapping.findForward("display");
                } else {
                    knowledgecontent.updateKnowledgecontent(info);
                    knowledgecontent.createHistoryAccess(null, uId, ipAddrStr, iplocal, "Data Updated", 60, categoryId);
                    request.setAttribute("MESSAGE", "Data updated successfully.");
                    int next = 0;
                    if (request.getSession().getAttribute("NEXTVALUE") != null) {
                        next = Integer.parseInt((String) request.getSession().getAttribute("NEXTVALUE"));
                    }
                    next = next - 1;
                    if (next < 0) {
                        next = 0;
                    }
                    ArrayList createtrainingList = knowledgecontent.getKnowledgecontentByName(search, next, count);
                    int cnt = 0;
                    if (createtrainingList.size() > 0) {
                        KnowledgecontentInfo cinfo = (KnowledgecontentInfo) createtrainingList.get(createtrainingList.size() - 1);
                        cnt = cinfo.getCategoryId();
                        createtrainingList.remove(createtrainingList.size() - 1);
                    }
                    request.getSession().setAttribute("CREATETRAINING_LIST", createtrainingList);
                    request.getSession().setAttribute("COUNT_LIST", cnt + "");
                    request.getSession().setAttribute("NEXT", next + "");
                    request.getSession().setAttribute("NEXTVALUE", (next + 1) + "");

                    return mapping.findForward("display");
                }
            }
        } else if (frm.getDoIndexSubcategory() != null && frm.getDoIndexSubcategory().equals("yes")) {
            frm.setDoAddSubcategory("no");
            print(this, "getDoIndexSubcategory block.");
            int categoryId = frm.getCategoryId();
            frm.setCategoryId(categoryId);
            ArrayList list = knowledgecontent.getsubcategoryDetailByIdforDetail(categoryId);
            KnowledgecontentInfo info = knowledgecontent.getKnowledgecontentDetailByIdforDetail(categoryId);
            request.getSession().setAttribute("CATE_DETAIL", info);
            request.getSession().setAttribute("SUBCATEGORY_LIST", list);
            return mapping.findForward("view_subcategory");
        } else if (frm.getDoAddSubcategory() != null && frm.getDoAddSubcategory().equals("yes")) {
            frm.setDoAdd("no");
            print(this, " getDoAddSubcategory block :: ");
            frm.setStatus(1);
            frm.setSubcategoryId(-1);
            int categoryId = frm.getCategoryId();
            frm.setCategoryId(categoryId);
            saveToken(request);
            frm.setCategorycode(knowledgecontent.changeNum(categoryId, 3));
            frm.setCategoryname(knowledgecontent.getcategoryname(categoryId));
            return mapping.findForward("add_subcategory");
        } else if (frm.getDoModifysubcategory() != null && frm.getDoModifysubcategory().equals("yes")) {
            frm.setDoModifysubcategory("no");
            print(this, " getDoModifysubcategory block :: ");
            int categoryId = frm.getCategoryId();
            frm.setCategoryId(categoryId);
            int subcategoryId = frm.getSubcategoryId();
            frm.setSubcategoryId(subcategoryId);
            KnowledgecontentInfo info = knowledgecontent.getsubcategoryDetailById(categoryId, subcategoryId);
            if (info != null) {
                frm.setName(info.getName());
                frm.setStatus(info.getStatus());
                frm.setCdescription(info.getDescription());
                frm.setCategorycode(knowledgecontent.changeNum(categoryId, 3));
                frm.setCategoryname(info.getCategoryname());
            }
            if (request.getAttribute("SUBCATEGORYSAVEMODEL") != null) {

                request.removeAttribute("SUBCATEGORYSAVEMODEL");
            }
            saveToken(request);
            return mapping.findForward("add_subcategory");

        } else if (frm.getDoSaveSubcategory() != null && frm.getDoSaveSubcategory().equals("yes")) {
            frm.setDoSaveSubcategory("no");
            print(this, "getDoSaveSubcategory block.");
            if (isTokenValid(request)) {
                resetToken(request);
                int categoryId = frm.getCategoryId();
                int subcategoryId = frm.getSubcategoryId();
                String name = vobj.replacename(frm.getName());
                String description = vobj.replacedesc(frm.getCdescription());
                int status = 1;
                String ipAddrStr = request.getRemoteAddr();
                String iplocal = knowledgecontent.getLocalIp();
                int ck = knowledgecontent.checkDuplicacysubcategory(categoryId, name, subcategoryId);
                if (ck == 1) {
                    saveToken(request);
                    request.setAttribute("MESSAGE", "SubCategory already exists");
                    return mapping.findForward("add_createtraining");
                }
                //add_trainingfiles
                KnowledgecontentInfo info = new KnowledgecontentInfo(categoryId, subcategoryId, name, status, uId, description);
                if (subcategoryId <= 0) {
                    int cc = knowledgecontent.createsubCategory(info);
                    if (cc > 0) {
                        knowledgecontent.createHistoryAccess(null, uId, ipAddrStr, iplocal, "Data Added", 60, cc);
                        request.setAttribute("MESSAGE", "Data added successfully.");
                    }
                    request.setAttribute("SUBCATEGORYSAVEMODEL", "yes");
                    frm.setCategoryId(categoryId);
                    frm.setSubcategoryId(cc);
                    ArrayList list = knowledgecontent.getsubcategoryDetailByIdforDetail(categoryId);
                    request.getSession().setAttribute("SUBCATEGORY_LIST", list);
                    return mapping.findForward("view_subcategory");
                } else {
                    knowledgecontent.updatesubCategory(info);
                    knowledgecontent.createHistoryAccess(null, uId, ipAddrStr, iplocal, "Data Updated", 60, categoryId);
                    request.setAttribute("MESSAGE", "Data updated successfully.");
                }
                ArrayList list = knowledgecontent.getsubcategoryDetailByIdforDetail(categoryId);
                request.getSession().setAttribute("SUBCATEGORY_LIST", list);
                return mapping.findForward("view_subcategory");
            }
        } else if (frm.getDodeletesubcategory() != null && frm.getDodeletesubcategory().equals("yes")) {
            frm.setDodeletesubcategory("no");
            print(this, "getDoIndexSubcategory block.");
            int categoryId = frm.getCategoryId();
            frm.setCategoryId(categoryId);
            int subcategoryId = frm.getSubcategoryId();
            frm.setSubcategoryId(0);
            int substatus = frm.getSubstatus();
            frm.setSubstatus(0);
            String ipAddrStr = request.getRemoteAddr();
            String iplocal = knowledgecontent.getLocalIp();
            knowledgecontent.deletesubcategory(subcategoryId, uId, substatus, ipAddrStr, iplocal);
            ArrayList list = knowledgecontent.getsubcategoryDetailByIdforDetail(categoryId);
            request.getSession().setAttribute("SUBCATEGORY_LIST", list);
            return mapping.findForward("view_subcategory");

        } else if (frm.getDoIndexCourse() != null && frm.getDoIndexCourse().equals("yes")) {
            frm.setDoIndexCourse("no");
            print(this, "getDoIndexCourse block.");
            int categoryId = frm.getCategoryId();
            frm.setCategoryId(categoryId);
            Collection subcategorys = knowledgecontent.getSubcategory(categoryId);
            frm.setSubcategorys(subcategorys);
            int subcategoryIdIndex = frm.getSubcategoryIdIndex();
            frm.setSubcategoryIdIndex(subcategoryIdIndex);
            ArrayList list = knowledgecontent.getcourseslistByIdandIndexforDetail(categoryId, subcategoryIdIndex);
            request.getSession().setAttribute("COURSE_LIST", list);
            KnowledgecontentInfo info = knowledgecontent.getKnowledgecontentDetailByIdforDetail(categoryId);
            request.getSession().setAttribute("CATE_DETAIL", info);
             KnowledgecontentInfo subinfo = knowledgecontent.getsubcategoryDetailById(categoryId, subcategoryIdIndex);
            request.setAttribute("SUBCATE_DETAIL", subinfo);
            return mapping.findForward("view_course");

        } else if (frm.getDoAddCourse() != null && frm.getDoAddCourse().equals("yes")) {
            frm.setDoAddCourse("no");
            print(this, " getDoAddCourse block :: ");
            frm.setStatus(1);
            int categoryId = frm.getCategoryId();
            frm.setCategoryId(categoryId);
            int subcategoryId = frm.getSubcategoryId();
            frm.setSubcategoryId(subcategoryId);
            frm.setCourseId(-1);
            Collection subcategorys = knowledgecontent.getSubcategory(categoryId);
            frm.setSubcategorys(subcategorys);
            frm.setEsubcategoryId(frm.getEsubcategoryId());
            saveToken(request);
            frm.setCategoryname(knowledgecontent.getcategoryname(categoryId));
            frm.setCategorycode(knowledgecontent.changeNum(categoryId, 3));
            request.getSession().removeAttribute("ASSET_IDs");
            request.getSession().removeAttribute("TOPICATTACHMENTLIST");
            return mapping.findForward("add_course");

        } else if (frm.getDoModifycourse() != null && frm.getDoModifycourse().equals("yes")) {
            frm.setDoModifycourse("no");
            print(this, " getDoModifycourse block :: ");
            int categoryId = frm.getCategoryId();
            frm.setCategoryId(categoryId);
            int subcategoryId = frm.getSubcategoryId();
            frm.setSubcategoryId(subcategoryId);
            int courseId = frm.getCourseId();
            frm.setCourseId(courseId);
            frm.setCategorycode(knowledgecontent.changeNum(categoryId, 3));
            frm.setCategoryname(knowledgecontent.getcategoryname(categoryId));
            KnowledgecontentInfo info = knowledgecontent.getcourseDetailById(categoryId, subcategoryId, courseId);
            if (info != null) {
                frm.setStatus(info.getStatus());
                frm.setCdescription(info.getDescription());
                frm.setCoursecode(knowledgecontent.changeNum(courseId, 3));
                Collection subcategorys = knowledgecontent.getSubcategory(categoryId);
                frm.setSubcategorys(subcategorys);
                frm.setEsubcategoryId(subcategoryId);
                frm.setTopicname(info.getName());
                ArrayList list = knowledgecontent.getTopicAttachmentList(courseId);
                request.getSession().setAttribute("ASSET_IDs", info.getAssettypeids());
                request.getSession().setAttribute("TOPICATTACHMENTLIST", list);
            }
            if (request.getAttribute("COURSESAVEMODEL") != null) {

                request.removeAttribute("COURSESAVEMODEL");
            }
            saveToken(request);
            return mapping.findForward("add_course");
        } else if (frm.getDoAddFileList() != null && frm.getDoAddFileList().equals("yes")) {
            frm.setDoAddFileList("no");
            print(this, "getDoAddFileList block.");

            frm.setCourseId(frm.getCourseId());
            frm.setCategorycode(frm.getCategorycode());
            frm.setCategoryname(frm.getCategoryname());
            Collection subcategorys = knowledgecontent.getSubcategory(frm.getCategoryId());
            frm.setSubcategorys(subcategorys);
            frm.setEsubcategoryId(frm.getEsubcategoryId());
            frm.setTopicname(frm.getTopicname());
            String assettypeids = vobj.replacealphacomma(makeCommaDelimString(frm.getAssettype()));
            request.getSession().setAttribute("ASSET_IDs", assettypeids);
            frm.setCdescription(frm.getCdescription());

            int filetype = frm.getType();
            String displayName = frm.getDispname() != null ? frm.getDispname() : "";
            FormFile filename = frm.getUpload1();

            ArrayList list = new ArrayList();
            if (request.getSession().getAttribute("TOPICATTACHMENTLIST") != null) {
                list = (ArrayList) request.getSession().getAttribute("TOPICATTACHMENTLIST");
            }
            int size = list.size();
            boolean b = true;
            KnowledgecontentInfo cinfo = null;
            if (size > 0) {
                for (int i = 0; i < size; i++) {
                    cinfo = (KnowledgecontentInfo) list.get(i);
                    if (cinfo.getType() == filetype && cinfo.getName().equalsIgnoreCase(displayName)) {
                        b = false;
                        break;
                    }
                }
            }
            if (b) {
                String add_kfiles = knowledgecontent.getMainPath("add_kfiles");
                String foldername = knowledgecontent.createFolder(add_kfiles);
                Date now = new Date();
                String fn = String.valueOf(now.getTime());
                String fileName = "";

                if (filename != null && filename.getFileSize() > 0) {
                    fileName = knowledgecontent.uploadFile(0, "", filename, fn, add_kfiles, foldername);
                }
                if (filetype == 2) {
                    File dir = new File(add_kfiles + foldername + "/" + fn + "_uz/");
                    if (!dir.exists()) {
                        dir.mkdir();
                    }
                    knowledgecontent.unzip(add_kfiles + fileName, add_kfiles + foldername + "/" + fn + "_uz/");
                    fileName = foldername + "/" + fn + "_uz/";
                }
                list.add(new KnowledgecontentInfo(-1, filetype, displayName, fileName));
            }
            request.getSession().setAttribute("TOPICATTACHMENTLIST", list);
            request.setAttribute("SHOW", "yes");
            return mapping.findForward("add_course");

        } else if (frm.getDoDeleteFileList() != null && frm.getDoDeleteFileList().equals("yes")) {
            frm.setDoDeleteFileList("no");
            print(this, "getDoDeleteFileList block.");
            int tempcount = frm.getTempcount();
            frm.setCourseId(frm.getCourseId());
            frm.setCategorycode(frm.getCategorycode());
            frm.setCategoryname(frm.getCategoryname());
            Collection subcategorys = knowledgecontent.getSubcategory(frm.getCategoryId());
            frm.setSubcategorys(subcategorys);
            frm.setEsubcategoryId(frm.getEsubcategoryId());
            frm.setTopicname(frm.getTopicname());
            String assettypeids = vobj.replacealphacomma(makeCommaDelimString(frm.getAssettype()));
            request.getSession().setAttribute("ASSET_IDs", assettypeids);
            frm.setCdescription(frm.getCdescription());

            ArrayList list = new ArrayList();
            if (request.getSession().getAttribute("TOPICATTACHMENTLIST") != null) {
                list = (ArrayList) request.getSession().getAttribute("TOPICATTACHMENTLIST");
            }
            String add_kfiles = knowledgecontent.getMainPath("add_kfiles");
            if (list.size() > 0) {
                KnowledgecontentInfo delinfo = null;
                delinfo = (KnowledgecontentInfo) list.get(tempcount);
                if (delinfo != null) {
                    if (delinfo.getTopicattachmentid() > 0) {
                        knowledgecontent.delcourseattachment(delinfo.getTopicattachmentid());
                    }
                    File file = new File(add_kfiles + (delinfo.getFilename() != null ? delinfo.getFilename() : ""));
                    if (delinfo.type == 1) {
                        if (file.exists()) {
                            file.delete();
                        }
                    } else if (delinfo.type == 2) {
                        knowledgecontent.deleteDirectory(file);
                    }
                }
            }
            list.remove(tempcount);
            request.getSession().setAttribute("TOPICATTACHMENTLIST", list);
            request.setAttribute("SHOW", "yes");
            return mapping.findForward("add_course");

        } else if (frm.getDoSaveCourse() != null && frm.getDoSaveCourse().equals("yes")) {
            frm.setDoSaveCourse("no");
            print(this, "getDoSaveCourse block.");
            if (isTokenValid(request)) {
                resetToken(request);
                int categoryId = frm.getCategoryId();
                int subcategoryId = frm.getSubcategoryId();
                String description = vobj.replacedesc(frm.getCdescription());
                int esubcategoryId = frm.getEsubcategoryId();
                String topicname = vobj.replacedesc(frm.getTopicname());
                String assettype[] = frm.getAssettype();
                String assettypeids = vobj.replacealphacomma(makeCommaDelimString(assettype));
                int courseId = frm.getCourseId();
                int status = 1;
                String ipAddrStr = request.getRemoteAddr();
                String iplocal = knowledgecontent.getLocalIp();
                int ck = knowledgecontent.checkDuplicacycourse(categoryId, topicname, esubcategoryId, courseId);
                if (ck == 1) {
                    frm.setCategoryId(categoryId);
                    frm.setSubcategoryId(subcategoryId);
                    frm.setCourseId(-1);
                    Collection subcategorys = knowledgecontent.getSubcategory(categoryId);
                    frm.setSubcategorys(subcategorys);
                    frm.setEsubcategoryId(frm.getEsubcategoryId());
                    frm.setTopicname(topicname);
                    request.getSession().setAttribute("ASSET_IDs", assettypeids);
                    saveToken(request);
                    request.setAttribute("MESSAGE", "Topic already exists");
                    return mapping.findForward("add_course");
                }

                KnowledgecontentInfo info = new KnowledgecontentInfo(categoryId, esubcategoryId, topicname, status, uId, description, courseId, assettypeids);
                if (courseId <= 0) {
                    int cc = knowledgecontent.createcourse(info);
                    if (cc > 0) {
                        ArrayList flist = new ArrayList();
                        if (request.getSession().getAttribute("TOPICATTACHMENTLIST") != null) {
                            flist = (ArrayList) request.getSession().getAttribute("TOPICATTACHMENTLIST");
                            request.getSession().removeAttribute("TOPICATTACHMENTLIST");
                        }
                        int lsize = 0;
                        lsize = flist.size();
                        if (lsize > 0) {
                            Connection conn = null;
                            conn = knowledgecontent.getConnection();
                            KnowledgecontentInfo finfo = null;
                            for (int i = 0; i < lsize; i++) {
                                finfo = (KnowledgecontentInfo) flist.get(i);
                                if (finfo != null) {
                                    knowledgecontent.savecourseattachment(conn, cc, finfo.getType(), finfo.getFilename(), finfo.getName(), uId);
                                }
                            }
                            if (conn != null) {
                                conn.close();
                            }
                        }
                        knowledgecontent.createHistoryAccess(null, uId, ipAddrStr, iplocal, "Data Added", 93, cc);
                        request.setAttribute("MESSAGE", "Data added successfully.");
                    }
                    request.setAttribute("COURSESAVEMODEL", "yes");
                    Collection subcategorys = knowledgecontent.getSubcategory(categoryId);
                    frm.setSubcategorys(subcategorys);
                    frm.setSubcategoryIdIndex(-1);
                    ArrayList list = knowledgecontent.getcourseslistByIdforDetail(categoryId);
                    request.getSession().setAttribute("COURSE_LIST", list);
                    return mapping.findForward("view_course");
                } else {
                    knowledgecontent.updatecourse(info);
                    ArrayList flist = new ArrayList();
                    if (request.getSession().getAttribute("TOPICATTACHMENTLIST") != null) {
                        flist = (ArrayList) request.getSession().getAttribute("TOPICATTACHMENTLIST");
                        request.getSession().removeAttribute("TOPICATTACHMENTLIST");
                    }
                    int lsize = 0;
                    lsize = flist.size();
                    if (lsize > 0) {
                        Connection conn = null;
                        conn = knowledgecontent.getConnection();
                        KnowledgecontentInfo finfo = null;
                        for (int i = 0; i < lsize; i++) {
                            finfo = (KnowledgecontentInfo) flist.get(i);
                            if (finfo != null) {
                                if (finfo.topicattachmentid <= 0) {
                                    knowledgecontent.savecourseattachment(conn, courseId, finfo.getType(), finfo.getFilename(), finfo.getName(), uId);
                                }
                            }
                        }
                        if (conn != null) {
                            conn.close();
                        }
                    }
                    knowledgecontent.createHistoryAccess(null, uId, ipAddrStr, iplocal, "Data Added", 93, courseId);
                    request.setAttribute("MESSAGE", "Data Update successfully.");
                }
                Collection subcategorys = knowledgecontent.getSubcategory(categoryId);
                frm.setSubcategorys(subcategorys);
                frm.setSubcategoryIdIndex(-1);
                ArrayList list = knowledgecontent.getcourseslistByIdforDetail(categoryId);
                request.getSession().setAttribute("COURSE_LIST", list);
                return mapping.findForward("view_course");
            }
        } else if (frm.getDodeleteCourse() != null && frm.getDodeleteCourse().equals("yes")) {
            frm.setDodeleteCourse("no");
            print(this, "getDodeleteCourse block.");
            int categoryId = frm.getCategoryId();
            frm.setCategoryId(categoryId);
            Collection subcategorys = knowledgecontent.getSubcategory(categoryId);
            frm.setSubcategorys(subcategorys);
            int subcategoryIdIndex = frm.getSubcategoryIdIndex();
            frm.setSubcategoryIdIndex(subcategoryIdIndex);
            int courseId = frm.getCourseId();
            frm.setCourseId(0);

            int substatus = frm.getSubstatus();
            frm.setSubstatus(0);
            String ipAddrStr = request.getRemoteAddr();
            String iplocal = knowledgecontent.getLocalIp();
            knowledgecontent.deletecourse(courseId, uId, substatus, ipAddrStr, iplocal);
            ArrayList list = knowledgecontent.getcourseslistByIdandIndexforDetail(categoryId, subcategoryIdIndex);
            request.getSession().setAttribute("COURSE_LIST", list);
            return mapping.findForward("view_course");
        } else if (frm.getDoCancel() != null && frm.getDoCancel().equals("yes")) {
            frm.setDoCancel("no");
            print(this, "doCancel block");
            int next = 0;
            if (request.getSession().getAttribute("NEXTVALUE") != null) {
                next = Integer.parseInt((String) request.getSession().getAttribute("NEXTVALUE"));
            }
            next = next - 1;
            if (next < 0) {
                next = 0;
            }
            ArrayList createtrainingList = knowledgecontent.getKnowledgecontentByName(search, next, count);
            int cnt = 0;
            if (createtrainingList.size() > 0) {
                KnowledgecontentInfo cinfo = (KnowledgecontentInfo) createtrainingList.get(createtrainingList.size() - 1);
                cnt = cinfo.getCategoryId();
                createtrainingList.remove(createtrainingList.size() - 1);
            }
            request.getSession().setAttribute("CREATETRAINING_LIST", createtrainingList);
            request.getSession().setAttribute("COUNT_LIST", cnt + "");
            request.getSession().setAttribute("NEXT", next + "");
            request.getSession().setAttribute("NEXTVALUE", (next + 1) + "");
            return mapping.findForward("display");
        } else {
            print(this, "else block.");
            if (frm.getCtp() == 0) {
                Stack<String> blist = new Stack<String>();
                if (request.getSession().getAttribute("BACKURL") != null) {
                    blist = (Stack<String>) request.getSession().getAttribute("BACKURL");
                }
                blist.push(request.getRequestURI());
                request.getSession().setAttribute("BACKURL", blist);
            }
            ArrayList createtrainingList = knowledgecontent.getKnowledgecontentByName(search, 0, count);
            int cnt = 0;
            if (createtrainingList.size() > 0) {
                KnowledgecontentInfo cinfo = (KnowledgecontentInfo) createtrainingList.get(createtrainingList.size() - 1);
                cnt = cinfo.getCategoryId();
                createtrainingList.remove(createtrainingList.size() - 1);
            }
            request.getSession().setAttribute("CREATETRAINING_LIST", createtrainingList);
            request.getSession().setAttribute("COUNT_LIST", cnt + "");
            request.getSession().setAttribute("NEXT", "0");
            request.getSession().setAttribute("NEXTVALUE", "1");
        }
        return mapping.findForward("display");
    }
}
