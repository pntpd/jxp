function resetFilter()
{
    document.forms[0].search.value = "";
    searchFormAjax('s', '-1');
}

function showDetail(id)
{
    document.matrixForm.doView.value = "yes";
    document.matrixForm.doModify.value = "no";
    document.matrixForm.doAdd.value = "no";
    document.matrixForm.matrixId.value = id;
    document.matrixForm.action = "../matrix/MatrixAction.do";
    document.matrixForm.submit();
}

function showDetailBack()
{
    document.matrixForm.doView.value = "yes";
    document.matrixForm.doCancel.value = "no";
    document.matrixForm.doModify.value = "no";
    document.matrixForm.doCategory.value = "no";
    document.matrixForm.doSaveCourse.value = "no";
    document.matrixForm.action = "../matrix/MatrixAction.do";
    document.matrixForm.submit();
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
        var url = "../ajax/matrix/getinfo.jsp";
        var httploc = getHTTPObject();
        var getstr = "";
        var next_value = escape(document.matrixForm.nextValue.value);
        var search_value = escape(document.matrixForm.search.value);
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
    if (document.matrixForm.doView)
        document.matrixForm.doView.value = "no";
    if (document.matrixForm.doCancel)
        document.matrixForm.doCancel.value = "yes";
    if (document.matrixForm.doSave)
        document.matrixForm.doSave.value = "no";
    if (document.matrixForm.doAssign)
        document.matrixForm.doAssign.value = "no";
    if (document.matrixForm.doDeleteDetail)
        document.matrixForm.doDeleteDetail.value = "no";
    if (document.matrixForm.doModify)
        document.matrixForm.doModify.value = "no";
    document.matrixForm.action = "../matrix/MatrixAction.do";
    document.matrixForm.submit();
}

function sortForm(colid, updown)
{
    for (i = 1; i <= 1; i++)
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
    var url_sort = "../ajax/matrix/sort.jsp";
    var getstr = "";
    var nextValue = 0;
    if (document.matrixForm.nextValue)
        nextValue = document.matrixForm.nextValue.value;
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
    document.matrixForm.doModify.value = "no";
    document.matrixForm.doView.value = "no";
    document.matrixForm.doAdd.value = "yes";
    document.matrixForm.action = "../matrix/MatrixAction.do";
    document.matrixForm.submit();
}

function modifyForm(id)
{
    document.matrixForm.doModify.value = "yes";
    if (document.matrixForm.doView)
        document.matrixForm.doView.value = "no";
    if (document.matrixForm.doAdd)
        document.matrixForm.doAdd.value = "no";
    if (document.matrixForm.doAssign)
        document.matrixForm.doAssign.value = "no";
    if (document.matrixForm.doDeleteDetail)
        document.matrixForm.doDeleteDetail.value = "no";
    if (document.matrixForm.doCategory)
        document.matrixForm.doCategory.value = "no";
    if (document.matrixForm.doSaveCourse)
        document.matrixForm.doSaveCourse.value = "no";
    document.matrixForm.matrixId.value = id;
    document.matrixForm.action = "../matrix/MatrixAction.do";
    document.matrixForm.submit();
}

function assigncourse(id, positionId)
{
    document.matrixForm.doAssign.value = "yes";
    if (document.matrixForm.doDeleteDetail)
        document.matrixForm.doDeleteDetail.value = "no";
    if (document.matrixForm.doView)
        document.matrixForm.doView.value = "no";
    if (document.matrixForm.doAdd)
        document.matrixForm.doAdd.value = "no";
    if (document.matrixForm.doModify)
        document.matrixForm.doModify.value = "no";
    document.matrixForm.matrixpositionId.value = id;
    document.matrixForm.positionId.value = positionId;
    document.matrixForm.action = "../matrix/MatrixAction.do";
    document.matrixForm.submit();
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
    if (checkMatrix())
    {
        document.getElementById('submitdiv').innerHTML = "<img src='../assets/images/loading.gif' align='absmiddle' />";
        document.matrixForm.doSave.value = "yes";
        document.matrixForm.doCancel.value = "no";
        if (document.matrixForm.doView)
            document.matrixForm.doView.value = "no";
        document.matrixForm.action = "../matrix/MatrixAction.do";
        document.matrixForm.submit();
    }
}

function checkMatrix()
{
    if (trim(document.matrixForm.name.value) == "")
    {
        Swal.fire({
            title: "Please enter matrix name.",
            didClose: () => {
                document.matrixForm.name.focus();
            }
        })
        return false;
    }
    if (validname(document.matrixForm.name) == false)
    {
        return false;
    }
    if (trim(document.matrixForm.assettypeId.value) == "-1")
    {
        Swal.fire({
            title: "Please select asset type.",
            didClose: () => {
                document.matrixForm.assettypeId.focus();
            }
        })
        return false;
    }
    return true;
}

function resetForm()
{
    document.matrixForm.reset();
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
            var url = "../ajax/matrix/getinfo.jsp";
            var getstr = "";
            var httploc = getHTTPObject();
            var next_value = escape(document.matrixForm.nextValue.value);
            var next_del = "-1";
            if (document.matrixForm.nextDel)
                next_del = escape(document.matrixForm.nextDel.value);
            var search_value = escape(document.matrixForm.search.value);
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

function exporttoexcel()
{
    document.matrixForm.action = "../matrix/MatrixExportAction.do";
    document.matrixForm.submit();
}

function changeCategory()
{
    if (document.matrixForm.categoryId.value == "-1")
    {
        Swal.fire({
            title: "Please select category.",
            didClose: () => {
                document.matrixForm.categoryId.focus();
            }
        })
    } else
    {
        document.matrixForm.categoryName.value = document.getElementById("categorId").options[document.getElementById("categorId").selectedIndex].text;
        document.matrixForm.doCategory.value = "yes";
        if (document.matrixForm.doView)
            document.matrixForm.doView.value = "no";
        if (document.matrixForm.doModify)
            document.matrixForm.doModify.value = "no";
        if (document.matrixForm.doAdd)
            document.matrixForm.doAdd.value = "no";
        if (document.matrixForm.doAssign)
            document.matrixForm.doAssign.value = "no";
        if (document.matrixForm.doDeleteDetail)
            document.matrixForm.doDeleteDetail.value = "no";
        if (document.matrixForm.doSaveCourse)
            document.matrixForm.doSaveCourse.value = "no";
        document.matrixForm.action = "../matrix/MatrixAction.do";
        document.matrixForm.submit();
    }
}

function gobackassign()
{
    if (document.matrixForm.doView)
        document.matrixForm.doView.value = "yes";
    if (document.matrixForm.doCancel)
        document.matrixForm.doCancel.value = "no";
    if (document.matrixForm.doSave)
        document.matrixForm.doSave.value = "no";
    if (document.matrixForm.doAssign)
        document.matrixForm.doAssign.value = "no";
    if (document.matrixForm.doDeleteDetail)
        document.matrixForm.doDeleteDetail.value = "no";
    if (document.matrixForm.doModify)
        document.matrixForm.doModify.value = "no";
    if (document.matrixForm.doCategory)
        document.matrixForm.doCategory.value = "no";
    if (document.matrixForm.doSaveCourse)
        document.matrixForm.doSaveCourse.value = "no";
    document.matrixForm.action = "../matrix/MatrixAction.do";
    document.matrixForm.submit();
}

function setSubcat(size)
{
    if (document.getElementById("catcb").checked)
    {
        for (var i = 1; i <= size; i++)
        {
            document.getElementById("subcategorycb_" + i).checked = true;
            var subcategoryId = document.getElementById("subcategorycb_" + i).value;
            setcourse(subcategoryId, i, document.getElementById("coursesize_" + subcategoryId).value);
        }
    } else
    {
        for (var i = 1; i <= size; i++)
        {
            document.getElementById("subcategorycb_" + i).checked = false;
            var subcategoryId = document.getElementById("subcategorycb_" + i).value;
            setcourse(subcategoryId, i, document.getElementById("coursesize_" + subcategoryId).value);
        }
    }
}

function setcourse(subcategoryId, tp, size)
{
    if (document.getElementById("subcategorycb_" + tp).checked)
    {
        for (var i = 1; i <= size; i++)
        {
            document.getElementById("coursecb_" + subcategoryId + "_" + i).checked = true;
            document.getElementById("courseId_" + subcategoryId + "_" + i).value = document.getElementById("coursecb_" + subcategoryId + "_" + i).value;
            document.getElementById("subcategoryId_" + subcategoryId + "_" + i).value = subcategoryId;
        }
    } 
    else
    {
        for (var i = 1; i <= size; i++)
        {
            document.getElementById("coursecb_" + subcategoryId + "_" + i).checked = false;
            document.getElementById("courseId_" + subcategoryId + "_" + i).value = "-1";
            document.getElementById("subcategoryId_" + subcategoryId + "_" + i).value = "-1";
        }
    }
}

function checkcourse()
{
    var x = 0;
    if (Number(document.matrixForm.categoryId.value) <= 0)
    {
        Swal.fire({
            title: "Please select category.",
            didClose: () => {
                document.matrixForm.categoryId.focus();
            }
        })
        return false;
    }
    return true;
}

function save()
{
    if (checkcourse())
    {
        document.matrixForm.doCancel.value = "no";
        document.matrixForm.doModify.value = "no";
        document.matrixForm.doCategory.value = "no";
        document.matrixForm.doView.value = "no";
        document.matrixForm.doSaveCourse.value = "yes";
        document.matrixForm.action = "../matrix/MatrixAction.do";
        document.matrixForm.submit();
    }
}

function deleteFormDetail(id, status)
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
            document.matrixForm.doDeleteDetail.value = "yes";
            if (document.matrixForm.doAssign)
                document.matrixForm.doAssign.value = "no";
            if (document.matrixForm.doView)
                document.matrixForm.doView.value = "no";
            if (document.matrixForm.doAdd)
                document.matrixForm.doAdd.value = "no";
            if (document.matrixForm.doModify)
                document.matrixForm.doModify.value = "no";
            document.matrixForm.matrixpositionId.value = id;
            document.matrixForm.status.value = status;
            document.matrixForm.action = "../matrix/MatrixAction.do";
            document.matrixForm.submit();
        } else
        {
            if (document.getElementById("flexSwitchCheckDefault_" + id).checked == true)
                document.getElementById("flexSwitchCheckDefault_" + id).checked = false;
            else
                document.getElementById("flexSwitchCheckDefault_" + id).checked = true;
        }
    })
}

function setcoursehidden(subcategoryId, tp)
{
    if(document.getElementById("coursecb_"+subcategoryId+"_"+tp).checked)
    {
        document.getElementById("courseId_"+subcategoryId+"_"+tp).value = document.getElementById("coursecb_"+subcategoryId+"_"+tp).value;
    }
    else
    {
        document.getElementById("courseId_"+subcategoryId+"_"+tp).value = "-1";
    }
}