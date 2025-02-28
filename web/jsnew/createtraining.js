function resetFilter()
{
    document.forms[0].search.value = "";
    searchFormAjax('s','-1');
}

function showDetail(id)
{
    document.createtrainingForm.doView.value="yes";
    document.createtrainingForm.doModify.value="no";
    document.createtrainingForm.doAdd.value="no";
    document.createtrainingForm.categoryId.value=id;
    document.createtrainingForm.action="../createtraining/CreatetrainingAction.do";
    document.createtrainingForm.submit();
}

function handleKeySearch(e)
{
    var key=e.keyCode || e.which;
    if (key===13)
    {
        e.preventDefault();
        searchFormAjax('s','-1');
    }
}    

function checkSearch()
{
    if(trim(document.forms[0].search.value) != "")
    {
        if(validdesc(document.forms[0].search) == false)
        {
            document.forms[0].search.focus();
            return false;
        }
    }
    return true;
}

function searchFormAjax(v, v1)
{
    if ( checkSearch())
    {
        var url = "../ajax/createtraining/getinfo.jsp";
        var httploc = getHTTPObject();
        var getstr = "";
        var next_value = escape(document.createtrainingForm.nextValue.value);
        var search_value = escape(document.createtrainingForm.search.value);
        getstr += "nextValue="+next_value;
        getstr += "&next="+v;
        getstr += "&search="+search_value;
        getstr += "&doDirect="+v1;
        httploc.open("POST", url, true);
        httploc.onreadystatechange = function()
        {
            if (httploc.readyState == 4)
            {
                if(httploc.status == 200)
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
    if(document.createtrainingForm.doView)
        document.createtrainingForm.doView.value="no";
    if(document.createtrainingForm.doCancel)
        document.createtrainingForm.doCancel.value="yes";  
    if(document.createtrainingForm.doSave)
        document.createtrainingForm.doSave.value="no";
    document.createtrainingForm.action="../createtraining/CreatetrainingAction.do";
    document.createtrainingForm.submit();
}

function sortForm(colid, updown)
{
    for(i = 1; i <= 3; i++)
    {
        document.getElementById("img_"+i+"_2").className = "sort_arrow deactive_sort";
        document.getElementById("img_"+i+"_1").className = "sort_arrow deactive_sort";
    }
    if(updown == 2)
    {
        document.getElementById("img_"+colid+"_2").className = "sort_arrow active_sort";

    }
    else
    {
        document.getElementById("img_"+colid+"_"+updown).className = "sort_arrow active_sort";
    }
    var httploc = getHTTPObject();
    var url_sort = "../ajax/createtraining/sort.jsp";
    var getstr = "";
    var nextValue = 0;
    if(document.createtrainingForm.nextValue)
        nextValue = document.createtrainingForm.nextValue.value;
    getstr += "nextValue="+nextValue;
    getstr += "&col="+colid;
    getstr += "&updown="+updown;
    httploc.open("POST", url_sort, true);
    httploc.onreadystatechange = function()
    {
        if (httploc.readyState == 4)
        {
            if(httploc.status == 200)
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
    document.createtrainingForm.doModify.value="no";
    document.createtrainingForm.doView.value="no";
    document.createtrainingForm.doAdd.value="yes";
    document.createtrainingForm.action="../createtraining/CreatetrainingAction.do";
    document.createtrainingForm.submit();
}

function modifyForm(id)
{
    document.createtrainingForm.doModify.value="yes";
    document.createtrainingForm.doView.value="no";
    document.createtrainingForm.doAdd.value="no";
    document.createtrainingForm.categoryId.value=id;
    document.createtrainingForm.action="../createtraining/CreatetrainingAction.do";
    document.createtrainingForm.submit();
}

function submitForm()
{
    if(document.forms[0].getElementsByTagName("input"))
    {
        var inputElements = document.forms[0].getElementsByTagName("input");
        for(i = 0; i < inputElements.length; i++)
        {
            if(inputElements[i].type == "text")
            {
                inputElements[i].value = trim(inputElements[i].value);
            }
        }
    }
    if (checkcategory())
    {
        document.getElementById('submitdiv').innerHTML = "<img src='../assets/images/loading.gif' align='absmiddle' />";
        document.createtrainingForm.doSave.value="yes";
        document.createtrainingForm.doCancel.value="no";
        document.createtrainingForm.action="../createtraining/CreatetrainingAction.do";
        document.createtrainingForm.submit();
    }
}

function checkcategory()
{
    if (trim(document.createtrainingForm.name.value) == "")
    {
            Swal.fire({
        title: "Please enter category name.",
        didClose:() => {
          document.createtrainingForm.name.focus();
        }
        }) 
        return false;
    }
    if (validnamenumwithspace(document.createtrainingForm.name) == false)
    {
        return false;
    }
    if (trim(document.createtrainingForm.cdescription.value) == "")
    {
            Swal.fire({
        title: "Please enter description.",
        didClose:() => {
          document.createtrainingForm.cdescription.focus();
        }
        }) 
        return false;
    }
    return true;
}

function checksubcategory()
{
    if (trim(document.createtrainingForm.name.value) == "")
    {
            Swal.fire({
        title: "Please enter subcategory name.",
        didClose:() => {
          document.createtrainingForm.name.focus();
        }
        }) 
        return false;
    }
    if (validnamenumwithspace(document.createtrainingForm.name) == false)
    {
        return false;
    }
    if (trim(document.createtrainingForm.cdescription.value) == "")
    {
            Swal.fire({
        title: "Please enter description.",
        didClose:() => {
          document.createtrainingForm.cdescription.focus();
        }
        }) 
        return false;
    }
    return true;
}

function resetForm()
{
    document.createtrainingForm.reset();
}

function deleteForm(userId, status, id)
{
    var s = "";
    if(eval(status) == 1)
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
        var url = "../ajax/createtraining/getinfo.jsp";
        var getstr = "";
        var httploc = getHTTPObject();
        var next_value = escape(document.createtrainingForm.nextValue.value);
        var next_del = "-1";
        if(document.createtrainingForm.nextDel)
            next_del = escape(document.createtrainingForm.nextDel.value);
        var search_value = escape(document.createtrainingForm.search.value);
        getstr += "nextValue="+next_value;
        getstr += "&nextDel="+next_del;
        getstr += "&search="+search_value;
        getstr += "&status="+status;
        getstr += "&deleteVal="+userId;
        httploc.open("POST", url, true);
        httploc.onreadystatechange = function()
        {
            if (httploc.readyState == 4)
            {
                if(httploc.status == 200)
                {
                    var response = httploc.responseText;
                    var arr = new Array();
                    arr = response.split('##');
                    var v1 = arr[0];
                    var v2 = trim(arr[1]);
                    document.getElementById('ajax_cat').innerHTML = '';
                    document.getElementById('ajax_cat').innerHTML = v1;
                    if(trim(v2) != "")
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
    }
    else
    {
        if(document.getElementById("flexSwitchCheckDefault_"+id).checked == true)
            document.getElementById("flexSwitchCheckDefault_"+id).checked = false;
        else
            document.getElementById("flexSwitchCheckDefault_"+id).checked = true;
    }
    })
}

function exporttoexcel(id)
{    
    document.createtrainingForm.type.value = id;
    document.createtrainingForm.action = "../createtraining/CreatetrainingExportAction.do";
    document.createtrainingForm.submit();
}

function viewSubcategory(categoryId)
{    if(categoryId > 0){
        document.createtrainingForm.categoryId.value=categoryId;
        }
    document.createtrainingForm.doIndexSubcategory.value="yes";
    document.createtrainingForm.action="../createtraining/CreatetrainingAction.do";
    document.createtrainingForm.submit();
}

function addsubcategoryForm(categoryId)
{    if(categoryId > 0){
        document.createtrainingForm.categoryId.value=categoryId;
        }
    document.createtrainingForm.doIndexSubcategory.value="no";
    document.createtrainingForm.doAddSubcategory.value="yes";
    document.createtrainingForm.action="../createtraining/CreatetrainingAction.do";
    document.createtrainingForm.submit();
}

function modifySubcategoryForm(id)
{    
    document.createtrainingForm.subcategoryId.value=id;
    document.createtrainingForm.doIndexSubcategory.value="no";
    document.createtrainingForm.doAddSubcategory.value="no";
    document.createtrainingForm.doModifysubcategory.value="yes";
    document.createtrainingForm.action="../createtraining/CreatetrainingAction.do";
    document.createtrainingForm.submit();
}

function submitsubcategoryForm()
{
    if(document.forms[0].getElementsByTagName("input"))
    {
        var inputElements = document.forms[0].getElementsByTagName("input");
        for(i = 0; i < inputElements.length; i++)
        {
            if(inputElements[i].type == "text")
            {
                inputElements[i].value = trim(inputElements[i].value);
            }
        }
    }
    if (checksubcategory())
    {
        document.getElementById('submitdiv').innerHTML = "<img src='../assets/images/loading.gif' align='absmiddle' />";
        document.createtrainingForm.doSaveSubcategory.value="yes";
        document.createtrainingForm.doCancel.value="no";
        document.createtrainingForm.action="../createtraining/CreatetrainingAction.do";
        document.createtrainingForm.submit();
    }
}

function showDetailcategory(id)
{
    document.createtrainingForm.doView.value="yes";
    document.createtrainingForm.action="../createtraining/CreatetrainingAction.do";
    document.createtrainingForm.submit();
}

function viewCourse(id)
{    
    if(id <= 0)
    {
        id = document.createtrainingForm.subcategoryIdIndex.value
    }
    document.createtrainingForm.doIndexCourse.value="yes";
    document.createtrainingForm.doIndexSubcategory.value="no";
    document.createtrainingForm.subcategoryIdIndex.value=id;
    document.createtrainingForm.doView.value="no";
    document.createtrainingForm.action="../createtraining/CreatetrainingAction.do";
    document.createtrainingForm.submit();
}

function viewCoursecat(id)
{    
    if(id > 0)
    {
        document.createtrainingForm.categoryId.value=id
    }
    document.createtrainingForm.doIndexCourse.value="yes";
    document.createtrainingForm.doIndexSubcategory.value="no";
    document.createtrainingForm.doView.value="no";
    document.createtrainingForm.action="../createtraining/CreatetrainingAction.do";
    document.createtrainingForm.submit();
}

function addcourseForm()
{    
    document.createtrainingForm.doIndexCourse.value="no";
    document.createtrainingForm.doAddCourse.value="yes";
    document.createtrainingForm.action="../createtraining/CreatetrainingAction.do";
    document.createtrainingForm.submit();
}

function addcoursesubForm(subcategoryId)
{    
    if(subcategoryId > 0)
    {
        document.createtrainingForm.esubcategoryId.value=subcategoryId;
    }
    document.createtrainingForm.doIndexCourse.value="no";
    document.createtrainingForm.doIndexSubcategory.value="no";
    document.createtrainingForm.doAddCourse.value="yes";
    document.createtrainingForm.action="../createtraining/CreatetrainingAction.do";
    document.createtrainingForm.submit();
}

function modifycourseForm(courseId,subcategoryId)
{
    
    document.createtrainingForm.courseId.value = courseId;
    document.createtrainingForm.subcategoryId.value = subcategoryId;
    document.createtrainingForm.doIndexCourse.value="no";
    document.createtrainingForm.doAddCourse.value="no";
    document.createtrainingForm.doModifycourse.value="yes";
    document.createtrainingForm.action="../createtraining/CreatetrainingAction.do";
    document.createtrainingForm.submit();
}

function viewimg(courseId)
{
    var url = "../ajax/createtraining/getimg.jsp";
    var httploc = getHTTPObject();
    var getstr = "courseId=" + courseId;
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
            var url = "../ajax/createtraining/delimg.jsp";
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
    if(document.forms[0].getElementsByTagName("input"))
    {
        var inputElements = document.forms[0].getElementsByTagName("input");
        for(i = 0; i < inputElements.length; i++)
        {
            if(inputElements[i].type == "text")
            {
                inputElements[i].value = trim(inputElements[i].value);
            }
        }
    }
    if (checkcourse())
    {
        document.getElementById('submitdiv').innerHTML = "<img src='../assets/images/loading.gif' align='absmiddle' />";
        document.createtrainingForm.doSaveCourse.value="yes";
        document.createtrainingForm.doIndexCourse.value="no";
        document.createtrainingForm.doCancel.value="no";
        document.createtrainingForm.action="../createtraining/CreatetrainingAction.do";
        document.createtrainingForm.submit();
    }
}

function checkcourse()
{
    if (document.createtrainingForm.esubcategoryId.value <= "0")
    {
            Swal.fire({
        title: "Please select subcategory.",
        didClose:() => {
          document.createtrainingForm.esubcategoryId.focus();
        }
        }) 
        return false;
    }
    if (document.createtrainingForm.coursenameId.value <= "0")
    {
            Swal.fire({
        title: "Please select course name.",
        didClose:() => {
          document.createtrainingForm.coursenameId.focus();
        }
        }) 
        return false;
    }
    if (document.createtrainingForm.elearningfile.value != "")
    {    
        if (!(document.createtrainingForm.elearningfile.value).match(/(\.(zip))$/i))
        {
            Swal.fire({
                title: "Only .zip are allowed.",
                didClose: () => {
                    document.createtrainingForm.elearningfile.focus();
                }
            })
            return false;
        }
        if (document.createtrainingForm.elearningfile.value != "")
        {
            var input = document.createtrainingForm.elearningfile;
            if (input.files)
            {
                var file = input.files[0];
                if (file.size > (1024 * 1024 * 200))
                {
                    Swal.fire({
                        title: "File size should not exceed 200 MB.",
                        didClose: () => {
                            document.createtrainingForm.elearningfile.focus();
                        }
                    })
                    return false;
                }
            }
        }
    }
    if (trim(document.createtrainingForm.cdescription.value) == "")
    {
        Swal.fire({
        title: "Please enter description.",
        didClose:() => {
          document.createtrainingForm.cdescription.focus();
        }
        }) 
        return false;
    }    
    return true;
}

function deletesubcategoryForm(subcategoryId, status, id)
{
    var s = "";
    if(eval(status) == 1)
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
        document.createtrainingForm.dodeletesubcategory.value="yes";
        document.createtrainingForm.doIndexSubcategory.value="no";
        document.createtrainingForm.subcategoryId.value=subcategoryId;
        document.createtrainingForm.substatus.value=status;
        document.createtrainingForm.action="../createtraining/CreatetrainingAction.do";
        document.createtrainingForm.submit();
    }
    else
    {
        if(document.getElementById("flexSwitchCheckDefault_"+id).checked == true)
            document.getElementById("flexSwitchCheckDefault_"+id).checked = false;
        else
            document.getElementById("flexSwitchCheckDefault_"+id).checked = true;
    }
    })
}

function deletecourseForm(courseId, status, id)
{
    var s = "";
    if(eval(status) == 1)
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
        document.createtrainingForm.dodeleteCourse.value="yes";
        document.createtrainingForm.doIndexCourse.value="no";
        document.createtrainingForm.courseId.value=courseId;
        document.createtrainingForm.substatus.value=status;
        document.createtrainingForm.action="../createtraining/CreatetrainingAction.do";
        document.createtrainingForm.submit();
    }
    else
    {
        if(document.getElementById("flexSwitchCheckDefault_"+id).checked == true)
            document.getElementById("flexSwitchCheckDefault_"+id).checked = false;
        else
            document.getElementById("flexSwitchCheckDefault_"+id).checked = true;
    }
    })
}

function showcategory()
{
    document.createtrainingForm.doView.value="no";
    document.createtrainingForm.doIndexSubcategory.value="no";
    document.createtrainingForm.action="../createtraining/CreatetrainingAction.do";
    document.createtrainingForm.submit();
}

function setIframe(uval)
{
    var url_v = "", classname = "";
    if (uval.includes(".doc") || uval.includes(".docx"))
    {
        url_v = "https://docs.google.com/gview?url=" + uval + "&embedded=true";
        classname = "doc_mode";
    } 
    else if(uval.includes(".ppt") || uval.includes(".pptx") || uval.includes(".presentation"))
    {
        url_v = "https://view.officeapps.live.com/op/embed.aspx?src=" + uval + "&embedded=true";
        classname = "doc_mode";
    }
    else if (uval.includes(".pdf"))
    {
        url_v = uval+"#toolbar=0&page=1&view=fitH,100";
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

function setClass(tp)
{
    document.getElementById("upload_link_" + tp).className = "attache_btn uploaded_img";
}