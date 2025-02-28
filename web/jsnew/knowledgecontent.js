function resetFilter()
{
    document.forms[0].search.value = "";
    searchFormAjax('s', '-1');
}

function showDetail(id)
{
    document.knowledgecontentForm.doView.value = "yes";
    document.knowledgecontentForm.doModify.value = "no";
    document.knowledgecontentForm.doAdd.value = "no";
    document.knowledgecontentForm.categoryId.value = id;
    document.knowledgecontentForm.action = "../knowledgecontent/KnowledgecontentAction.do";
    document.knowledgecontentForm.submit();
}

function handleKeySearch(e)
{
    var key = e.keyCode || e.which;
    if (key === 13)
    {
        e.preventDefault();
        searchFormAjax('s', '-1');
    }
}

function checkSearch()
{
    if (trim(document.forms[0].search.value) != "")
    {
        if (validdesc(document.forms[0].search) == false)
        {
            document.forms[0].search.focus();
            return false;
        }
    }
    return true;
}

function searchFormAjax(v, v1)
{
    if (checkSearch())
    {
        var url = "../ajax/knowledgecontent/getinfo.jsp";
        var httploc = getHTTPObject();
        var getstr = "";
        var next_value = escape(document.knowledgecontentForm.nextValue.value);
        var search_value = escape(document.knowledgecontentForm.search.value);
        getstr += "nextValue=" + next_value;
        getstr += "&next=" + v;
        getstr += "&search=" + search_value;
        getstr += "&doDirect=" + v1;
        httploc.open("POST", url, true);
        httploc.onreadystatechange = function ()
        {
            if (httploc.readyState == 4)
            {
                if (httploc.status == 200)
                {
                    var response = httploc.responseText;
                    document.getElementById('ajax_cat').innerHTML = '';
                    document.getElementById('ajax_cat').innerHTML = response;
                }
            }
        };
        httploc.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
        httploc.setRequestHeader("Content-length", getstr.length);
        httploc.setRequestHeader("Connection", "close");
        httploc.send(getstr);
        document.getElementById('ajax_cat').innerHTML = "<div><img src='../assets/images/loading.gif' align='absmiddle'/>Please wait. Loading...</div>";
    }
}

function goback()
{
    if (document.knowledgecontentForm.doView)
        document.knowledgecontentForm.doView.value = "no";
    if (document.knowledgecontentForm.doCancel)
        document.knowledgecontentForm.doCancel.value = "yes";
    if (document.knowledgecontentForm.doSave)
        document.knowledgecontentForm.doSave.value = "no";
    document.knowledgecontentForm.action = "../knowledgecontent/KnowledgecontentAction.do";
    document.knowledgecontentForm.submit();
}

function sortForm(colid, updown)
{
    for (i = 1; i <= 3; i++)
    {
        document.getElementById("img_" + i + "_2").className = "sort_arrow deactive_sort";
        document.getElementById("img_" + i + "_1").className = "sort_arrow deactive_sort";
    }
    if (updown == 2)
    {
        document.getElementById("img_" + colid + "_2").className = "sort_arrow active_sort";

    } else
    {
        document.getElementById("img_" + colid + "_" + updown).className = "sort_arrow active_sort";
    }
    var httploc = getHTTPObject();
    var url_sort = "../ajax/knowledgecontent/sort.jsp";
    var getstr = "";
    var nextValue = 0;
    if (document.knowledgecontentForm.nextValue)
        nextValue = document.knowledgecontentForm.nextValue.value;
    getstr += "nextValue=" + nextValue;
    getstr += "&col=" + colid;
    getstr += "&updown=" + updown;
    httploc.open("POST", url_sort, true);
    httploc.onreadystatechange = function ()
    {
        if (httploc.readyState == 4)
        {
            if (httploc.status == 200)
            {
                var response = httploc.responseText;
                document.getElementById('sort_id').innerHTML = '';
                document.getElementById('sort_id').innerHTML = response;
            }
        }
    };
    httploc.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    httploc.setRequestHeader("Content-length", getstr.length);
    httploc.setRequestHeader("Connection", "close");
    httploc.send(getstr);
    document.getElementById('sort_id').innerHTML = "<div><img src='../assets/images/loading.gif' align='absmiddle'/>Please wait. Loading...</div>";
}

function addForm()
{
    document.knowledgecontentForm.doModify.value = "no";
    document.knowledgecontentForm.doView.value = "no";
    document.knowledgecontentForm.doAdd.value = "yes";
    document.knowledgecontentForm.action = "../knowledgecontent/KnowledgecontentAction.do";
    document.knowledgecontentForm.submit();
}

function modifyForm(id)
{
    document.knowledgecontentForm.doModify.value = "yes";
    document.knowledgecontentForm.doView.value = "no";
    document.knowledgecontentForm.doAdd.value = "no";
    document.knowledgecontentForm.categoryId.value = id;
    document.knowledgecontentForm.action = "../knowledgecontent/KnowledgecontentAction.do";
    document.knowledgecontentForm.submit();
}

function submitForm()
{
    if (document.forms[0].getElementsByTagName("input"))
    {
        var inputElements = document.forms[0].getElementsByTagName("input");
        for (i = 0; i < inputElements.length; i++)
        {
            if (inputElements[i].type == "text")
            {
                inputElements[i].value = trim(inputElements[i].value);
            }
        }
    }
    if (checkcategory())
    {
        document.getElementById('submitdiv').innerHTML = "<img src='../assets/images/loading.gif' align='absmiddle' />";
        document.knowledgecontentForm.doSave.value = "yes";
        document.knowledgecontentForm.doCancel.value = "no";
        document.knowledgecontentForm.action = "../knowledgecontent/KnowledgecontentAction.do";
        document.knowledgecontentForm.submit();
    }
}

function checkcategory()
{
    if (trim(document.knowledgecontentForm.name.value) == "")
    {
        Swal.fire({
            title: "Please enter category name.",
            didClose: () => {
                document.knowledgecontentForm.name.focus();
            }
        })
        return false;
    }
    if (validnamenumwithspace(document.knowledgecontentForm.name) == false)
    {
        return false;
    }
    if (trim(document.knowledgecontentForm.cdescription.value) == "")
    {
        Swal.fire({
            title: "Please enter description.",
            didClose: () => {
                document.knowledgecontentForm.cdescription.focus();
            }
        })
        return false;
    }
    return true;
}

function checksubcategory()
{
    if (trim(document.knowledgecontentForm.name.value) == "")
    {
        Swal.fire({
            title: "Please enter module name.",
            didClose: () => {
                document.knowledgecontentForm.name.focus();
            }
        })
        return false;
    }
    if (validnamenumwithspace(document.knowledgecontentForm.name) == false)
    {
        return false;
    }
    if (trim(document.knowledgecontentForm.cdescription.value) == "")
    {
        Swal.fire({
            title: "Please enter description.",
            didClose: () => {
                document.knowledgecontentForm.cdescription.focus();
            }
        })
        return false;
    }
    return true;
}

function resetForm()
{
    document.knowledgecontentForm.reset();
}

function deleteForm(userId, status, id)
{
    var s = "";
    if (eval(status) == 1)
        s = "<span>The selected item will be <b>deactivated.</b></span>";
    else
        s = "<span>The selected item will be <b>activated.</b></span>";
    Swal.fire({
        title: s + 'Are you sure?',
        confirmButtonColor: '#3085d6',
        cancelButtonColor: '#d33',
        confirmButtonText: 'Confirm',
        showCloseButton: true,
        allowOutsideClick: false,
        allowEscapeKey: false
    }).then((result) => {
        if (result.isConfirmed)
        {
            var url = "../ajax/knowledgecontent/getinfo.jsp";
            var getstr = "";
            var httploc = getHTTPObject();
            var next_value = escape(document.knowledgecontentForm.nextValue.value);
            var next_del = "-1";
            if (document.knowledgecontentForm.nextDel)
                next_del = escape(document.knowledgecontentForm.nextDel.value);
            var search_value = escape(document.knowledgecontentForm.search.value);
            getstr += "nextValue=" + next_value;
            getstr += "&nextDel=" + next_del;
            getstr += "&search=" + search_value;
            getstr += "&status=" + status;
            getstr += "&deleteVal=" + userId;
            httploc.open("POST", url, true);
            httploc.onreadystatechange = function ()
            {
                if (httploc.readyState == 4)
                {
                    if (httploc.status == 200)
                    {
                        var response = httploc.responseText;
                        var arr = new Array();
                        arr = response.split('##');
                        var v1 = arr[0];
                        var v2 = trim(arr[1]);
                        document.getElementById('ajax_cat').innerHTML = '';
                        document.getElementById('ajax_cat').innerHTML = v1;
                        if (trim(v2) != "")
                        {
                           Swal.fire(v2)
                        }
                    }
                }
            };
            httploc.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
            httploc.setRequestHeader("Content-length", getstr.length);
            httploc.setRequestHeader("Connection", "close");
            httploc.send(getstr);
        } else
        {
            if (document.getElementById("flexSwitchCheckDefault_" + id).checked == true)
                document.getElementById("flexSwitchCheckDefault_" + id).checked = false;
            else
                document.getElementById("flexSwitchCheckDefault_" + id).checked = true;
        }
    })
}

function exporttoexcel(id)
{
    document.knowledgecontentForm.type.value = id;
    document.knowledgecontentForm.action = "../knowledgecontent/KnowledgecontentExportAction.do";
    document.knowledgecontentForm.submit();
}

function viewSubcategory(categoryId)
{
    if (categoryId > 0) {
        document.knowledgecontentForm.categoryId.value = categoryId;
    }
    document.knowledgecontentForm.doIndexSubcategory.value = "yes";
    document.knowledgecontentForm.action = "../knowledgecontent/KnowledgecontentAction.do";
    document.knowledgecontentForm.submit();
}

function addsubcategoryForm(categoryId)
{
    if (categoryId > 0) 
    {
        document.knowledgecontentForm.categoryId.value = categoryId;
    }
    document.knowledgecontentForm.doIndexSubcategory.value = "no";
    document.knowledgecontentForm.doAddSubcategory.value = "yes";
    document.knowledgecontentForm.action = "../knowledgecontent/KnowledgecontentAction.do";
    document.knowledgecontentForm.submit();
}

function modifySubcategoryForm(id)
{
    document.knowledgecontentForm.subcategoryId.value = id;
    document.knowledgecontentForm.doIndexSubcategory.value = "no";
    document.knowledgecontentForm.doAddSubcategory.value = "no";
    document.knowledgecontentForm.doModifysubcategory.value = "yes";
    document.knowledgecontentForm.action = "../knowledgecontent/KnowledgecontentAction.do";
    document.knowledgecontentForm.submit();
}

function submitsubcategoryForm()
{
    if (document.forms[0].getElementsByTagName("input"))
    {
        var inputElements = document.forms[0].getElementsByTagName("input");
        for (i = 0; i < inputElements.length; i++)
        {
            if (inputElements[i].type == "text")
            {
                inputElements[i].value = trim(inputElements[i].value);
            }
        }
    }
    if (checksubcategory())
    {
        document.getElementById('submitdiv').innerHTML = "<img src='../assets/images/loading.gif' align='absmiddle' />";
        document.knowledgecontentForm.doSaveSubcategory.value = "yes";
        document.knowledgecontentForm.doCancel.value = "no";
        document.knowledgecontentForm.action = "../knowledgecontent/KnowledgecontentAction.do";
        document.knowledgecontentForm.submit();
    }
}

function showDetailcategory(id)
{
    document.knowledgecontentForm.doView.value = "yes";
    document.knowledgecontentForm.action = "../knowledgecontent/KnowledgecontentAction.do";
    document.knowledgecontentForm.submit();
}

function viewCourse(id)
{
    if (id <= 0)
    {
        id = document.knowledgecontentForm.subcategoryIdIndex.value
    }
    document.knowledgecontentForm.doIndexCourse.value = "yes";
    document.knowledgecontentForm.doIndexSubcategory.value = "no";
    document.knowledgecontentForm.subcategoryIdIndex.value = id;
    document.knowledgecontentForm.doView.value = "no";
    document.knowledgecontentForm.action = "../knowledgecontent/KnowledgecontentAction.do";
    document.knowledgecontentForm.submit();
}

function viewCoursecat(id, subid)
{
    if (id > 0)
    {
        document.knowledgecontentForm.categoryId.value = id
    }
    if (subid > 0)
        document.knowledgecontentForm.subcategoryIdIndex.value = subid
    document.knowledgecontentForm.doIndexCourse.value = "yes";
    document.knowledgecontentForm.doIndexSubcategory.value = "no";
    document.knowledgecontentForm.doView.value = "no";
    document.knowledgecontentForm.action = "../knowledgecontent/KnowledgecontentAction.do";
    document.knowledgecontentForm.submit();
}

function addcourseForm()
{
    document.knowledgecontentForm.doIndexCourse.value = "no";
    document.knowledgecontentForm.doAddCourse.value = "yes";
    document.knowledgecontentForm.action = "../knowledgecontent/KnowledgecontentAction.do";
    document.knowledgecontentForm.submit();
}

function addcoursesubForm(subcategoryId)
{
    if (subcategoryId > 0)
    {
        document.knowledgecontentForm.esubcategoryId.value = subcategoryId;
    }
    document.knowledgecontentForm.doIndexCourse.value = "no";
    document.knowledgecontentForm.doIndexSubcategory.value = "no";
    document.knowledgecontentForm.doAddCourse.value = "yes";
    document.knowledgecontentForm.action = "../knowledgecontent/KnowledgecontentAction.do";
    document.knowledgecontentForm.submit();
}

function modifycourseForm(courseId, subcategoryId)
{
    document.knowledgecontentForm.courseId.value = courseId;
    document.knowledgecontentForm.subcategoryId.value = subcategoryId;
    document.knowledgecontentForm.doIndexCourse.value = "no";
    document.knowledgecontentForm.doAddCourse.value = "no";
    document.knowledgecontentForm.doModifycourse.value = "yes";
    document.knowledgecontentForm.action = "../knowledgecontent/KnowledgecontentAction.do";
    document.knowledgecontentForm.submit();
}

function viewimg(courseId)
{
    var url = "../ajax/knowledgecontent/gettopicfiles.jsp";
    var httploc = getHTTPObject();
    var getstr = "topicId=" + courseId;
    httploc.open("POST", url, true);
    httploc.onreadystatechange = function ()
    {
        if (httploc.readyState == 4)
        {
            if (httploc.status == 200)
            {
                var response = httploc.responseText;
                document.getElementById('viewfilesdiv').innerHTML = '';
                document.getElementById('viewfilesdiv').innerHTML = response;

                $("#iframe").on("load", function () {
                    let head = $("#iframe").contents().find("head");
                    let css = '<style>img{margin: 0px auto; max-width:-webkit-fill-available;}</style>';
                    $(head).append(css);
                });

            }
        }
    };
    httploc.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    httploc.setRequestHeader("Content-length", getstr.length);
    httploc.setRequestHeader("Connection", "close");
    httploc.send(getstr);
    document.getElementById('viewfilesdiv').innerHTML = "<div><img src='../assets/images/loading.gif' align='absmiddle'/>Please wait. Loading...</div>";
}

function setIframeresume(uval)
{
    var url_v = "", classname = "";
    if (uval.includes(".doc") || uval.includes(".docx") || uval.includes("wordprocessingml.document"))
    {
        url_v = "https://docs.google.com/gview?url=" + uval + "&embedded=true";
        classname = "doc_mode";
    }  else if (uval.includes(".ppt") || uval.includes(".pptx") || uval.includes(".presentation"))
    {
        url_v = "https://view.officeapps.live.com/op/embed.aspx?src=" + uval + "&embedded=true";
        classname = "doc_mode";
    } else if (uval.includes(".pdf"))
    {
        url_v = uval + "#toolbar=0&page=1&view=fitH,100";
        classname = "pdf_mode";
    } else
    {
        url_v = uval;
        classname = "img_mode";
    }
    window.top.$('#iframe').attr('src', 'about:blank');
    setTimeout(function () {
        window.top.$('#iframe').attr('src', url_v);
        document.getElementById("iframe").className = classname;
    }, 1000);
}

function delpic(courseattachmentId, courseId)
{
    var s = "<span>File will be <b>deleted.<b></span>";
    Swal.fire({
        title: s + 'Are you sure?',
        confirmButtonColor: '#3085d6',
        cancelButtonColor: '#d33',
        confirmButtonText: 'Confirm',
        showCloseButton: true,
        allowOutsideClick: false,
        allowEscapeKey: false
    }).then((result) => {
        if (result.isConfirmed)
        {
            var url = "../ajax/knowledgecontent/delimg.jsp";
            var httploc = getHTTPObject();
            var getstr = "courseattachmentId=" + courseattachmentId;
            httploc.open("POST", url, true);
            httploc.onreadystatechange = function ()
            {
                if (httploc.readyState == 4)
                {
                    if (httploc.status == 200)
                    {
                        var response = trim(httploc.responseText);
                        if (response == "Yes")
                        {
                            viewimg(courseId);
                        }
                    }
                }
            };
            httploc.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
            httploc.setRequestHeader("Content-length", getstr.length);
            httploc.setRequestHeader("Connection", "close");
            httploc.send(getstr);
            document.getElementById('viewfilesdiv').innerHTML = "<div><img src='../assets/images/loading.gif' align='absmiddle'/>Please wait. Loading...</div>";
        }
    })
}

function submitcourseForm()
{
    if (document.forms[0].getElementsByTagName("input"))
    {
        var inputElements = document.forms[0].getElementsByTagName("input");
        for (i = 0; i < inputElements.length; i++)
        {
            if (inputElements[i].type == "text")
            {
                inputElements[i].value = trim(inputElements[i].value);
            }
        }
    }
    if (checkcourse())
    {
        document.getElementById('submitdiv').innerHTML = "<img src='../assets/images/loading.gif' align='absmiddle' />";
        document.knowledgecontentForm.doSaveCourse.value = "yes";
        document.knowledgecontentForm.doIndexCourse.value = "no";
        document.knowledgecontentForm.doCancel.value = "no";
        document.knowledgecontentForm.action = "../knowledgecontent/KnowledgecontentAction.do";
        document.knowledgecontentForm.submit();
    }
}

function checkcourse()
{
    if (document.knowledgecontentForm.esubcategoryId.value <= "0")
    {
        Swal.fire({
            title: "Please select module.",
            didClose: () => {
                document.knowledgecontentForm.esubcategoryId.focus();
            }
        })
        return false;
    }

    if (document.knowledgecontentForm.topicname.value == "")
    {
        Swal.fire({
            title: "Please enter topic name.",
            didClose: () => {
                document.knowledgecontentForm.esubcategoryId.focus();
            }
        })
        return false;
    }

    if (document.knowledgecontentForm.topicname.value == "")
    {
        Swal.fire({
            title: "Please enter topic name.",
            didClose: () => {
                document.knowledgecontentForm.esubcategoryId.focus();
            }
        })
        return false;
    }

    if (trim(document.knowledgecontentForm.assettype.value) == "")
    {
        Swal.fire({
            title: "Please select atleast one asset type.",
            didClose: () => {
                document.knowledgecontentForm.assettype.focus();
            }
        })
        return false;
    }

    if (Number(document.getElementById("filecount").value) <= 0)
    {
        Swal.fire({
            title: "Please upload atleast one file.",
        })
        return false;
    }

    return true;
}

function deletesubcategoryForm(subcategoryId, status, id)
{
    var s = "";
    if (eval(status) == 1)
        s = "<span>The selected item will be <b>deactivated.</b></span>";
    else
        s = "<span>The selected item will be <b>activated.</b></span>";
    Swal.fire({
        title: s + 'Are you sure?',
        confirmButtonColor: '#3085d6',
        cancelButtonColor: '#d33',
        confirmButtonText: 'Confirm',
        showCloseButton: true,
        allowOutsideClick: false,
        allowEscapeKey: false
    }).then((result) => {
        if (result.isConfirmed)
        {
            document.knowledgecontentForm.dodeletesubcategory.value = "yes";
            document.knowledgecontentForm.doIndexSubcategory.value = "no";
            document.knowledgecontentForm.subcategoryId.value = subcategoryId;
            document.knowledgecontentForm.substatus.value = status;
            document.knowledgecontentForm.action = "../knowledgecontent/KnowledgecontentAction.do";
            document.knowledgecontentForm.submit();
        } else
        {
            if (document.getElementById("flexSwitchCheckDefault_" + id).checked == true)
                document.getElementById("flexSwitchCheckDefault_" + id).checked = false;
            else
                document.getElementById("flexSwitchCheckDefault_" + id).checked = true;
        }
    })
}

function deletecourseForm(courseId, status, id)
{
    var s = "";
    if (eval(status) == 1)
        s = "<span>The selected item will be <b>deactivated.</b></span>";
    else
        s = "<span>The selected item will be <b>activated.</b></span>";
    Swal.fire({
        title: s + 'Are you sure?',
        confirmButtonColor: '#3085d6',
        cancelButtonColor: '#d33',
        confirmButtonText: 'Confirm',
        showCloseButton: true,
        allowOutsideClick: false,
        allowEscapeKey: false
    }).then((result) => {
        if (result.isConfirmed)
        {
            document.knowledgecontentForm.dodeleteCourse.value = "yes";
            document.knowledgecontentForm.doIndexCourse.value = "no";
            document.knowledgecontentForm.courseId.value = courseId;
            document.knowledgecontentForm.substatus.value = status;
            document.knowledgecontentForm.action = "../knowledgecontent/KnowledgecontentAction.do";
            document.knowledgecontentForm.submit();
        } else
        {
            if (document.getElementById("flexSwitchCheckDefault_" + id).checked == true)
                document.getElementById("flexSwitchCheckDefault_" + id).checked = false;
            else
                document.getElementById("flexSwitchCheckDefault_" + id).checked = true;
        }
    })
}

function showcategory()
{
    document.knowledgecontentForm.doView.value = "no";
    document.knowledgecontentForm.doIndexSubcategory.value = "no";
    document.knowledgecontentForm.action = "../knowledgecontent/KnowledgecontentAction.do";
    document.knowledgecontentForm.submit();
}

function setIframe(uval)
{
    var url_v = "", classname = "";
    if (uval.includes(".doc") || uval.includes(".docx"))
    {
        url_v = "https://docs.google.com/gview?url=" + uval + "&embedded=true";
        classname = "doc_mode";
    } else if (uval.includes(".ppt") || uval.includes(".pptx") || uval.includes(".presentation"))
    {
        url_v = "https://view.officeapps.live.com/op/embed.aspx?src=" + uval + "&embedded=true";
        classname = "doc_mode";
    } else if (uval.includes(".pdf"))
    {
        url_v = uval + "#toolbar=0&page=1&view=fitH,100";
        classname = "pdf_mode";
    } else
    {
        url_v = uval;
        classname = "img_mode";
    }
    window.top.$('#iframe').attr('src', 'about:blank');
    setTimeout(function () {
        window.top.$('#iframe').attr('src', url_v);
        document.getElementById("iframe").className = classname;
    }, 1000);
}

function getMultiFile()
{
    var topicId = document.forms[0].courseId.value;
    var httploc = getHTTPObject();
    var url_sort = "../ajax/knowledgecontent/getmultiuploadfile.jsp";
    var getstr = "";
    getstr += "topicId=" + topicId;
    httploc.open("POST", url_sort, true);
    httploc.onreadystatechange = function ()
    {
        if (httploc.readyState == 4)
        {
            if (httploc.status == 200)
            {
                var response = httploc.responseText;
                document.getElementById('dMultiUpload').innerHTML = '';
                document.getElementById('dMultiUpload').innerHTML = response;
                $("#topic_material_files_modal").modal("show");
                $("#upload_link_1").on('click', function (e) {
                    e.preventDefault();
                    $("#upload1:hidden").trigger('click');
                });
            }
        }
    };
    httploc.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    httploc.setRequestHeader("Content-length", getstr.length);
    httploc.setRequestHeader("Connection", "close");
    httploc.send(getstr);
    document.getElementById('dMultiUpload').innerHTML = "<div><img src='../assets/images/loading.gif' align='absmiddle'/>Please wait. Loading...</div>";
}

function checkFile() 
{
    var type = document.knowledgecontentForm.type.value;
    if (type <= "0")
    {
        Swal.fire({
            title: "Please select type.",
            didClose: () => {
                document.knowledgecontentForm.type.focus();
            }
        })
        return false;
    }
    if (document.knowledgecontentForm.dispname.value <= "0")
    {
        Swal.fire({
            title: "Please enter display name.",
            didClose: () => {
                document.knowledgecontentForm.dispname.focus();
            }
        })
        return false;
    }
    if (document.knowledgecontentForm.upload1.value == "")
    {
        Swal.fire({
            title: "Please upload file.",
            didClose: () => {
                document.knowledgecontentForm.upload1.focus();
            }
        })
        return false;
    }
    if (type == 1) {
        if (!(document.knowledgecontentForm.upload1.value).match(/(\.(png)|(jpg)|(jpeg)|(ppt)|(pptx)|(pdf)|(doc)|(docx))$/i))
        {
            Swal.fire({
                title: "Only .jpg, .jpeg, .png, .ppt, .pptx, .pdf, .doc, .docx are allowed.",
                didClose: () => {
                    document.knowledgecontentForm.upload1.focus();
                }
            })
            return false;
        }
        if (document.knowledgecontentForm.upload1.value != "")
        {
            var input = document.knowledgecontentForm.upload1;
            if (input.files)
            {
                var file = input.files[0];
                if (file.size > (1024 * 1024 * 20))
                {
                    Swal.fire({
                        title: "File size should not exceed 20 MB.",
                        didClose: () => {
                            document.knowledgecontentForm.upload1.focus();
                        }
                    })
                    return false;
                }
            }
        }
    } else if (type == 2) {
        if (!(document.knowledgecontentForm.upload1.value).match(/(\.(zip))$/i))
        {
            Swal.fire({
                title: "Only .zip are allowed.",
                didClose: () => {
                    document.knowledgecontentForm.upload1.focus();
                }
            })
            return false;
        }
        if (document.knowledgecontentForm.upload1.value != "")
        {
            var input = document.knowledgecontentForm.upload1;
            if (input.files)
            {
                var file = input.files[0];
                if (file.size > (1024 * 1024 * 200))
                {
                    Swal.fire({
                        title: "File size should not exceed 200 MB.",
                        didClose: () => {
                            document.knowledgecontentForm.upload1.focus();
                        }
                    })
                    return false;
                }
            }
        }
    }
    return true;
}

function getAddToList()
{
    if (checkFile()) 
    {
        document.forms[0].doAddFileList.value = "yes";
        document.forms[0].action = "../knowledgecontent/KnowledgecontentAction.do";
        document.forms[0].submit();
    }
}

function getDeleteFromList(tempcount) 
{
    document.forms[0].tempcount.value = tempcount;
    document.forms[0].doDeleteFileList.value = "yes";
    document.forms[0].action = "../knowledgecontent/KnowledgecontentAction.do";
    document.forms[0].submit();
}

function setClass(tp)
{
    document.getElementById("upload_link_" + tp).className = "attache_btn uploaded_img";
}
