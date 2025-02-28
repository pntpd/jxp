function resetFilter()
{
    document.forms[0].clientIdIndex.value = "-1";
    document.forms[0].assetIdIndex.value = "-1";
    setAssetDDL();
    searchFormAjax('s', '-1');
}

function showDetail(clientId,assetId)
{
    document.wellnessmatrixForm.doView.value = "yes";
    document.wellnessmatrixForm.doAdd.value = "no";
    document.wellnessmatrixForm.clientId.value = clientId;
    document.wellnessmatrixForm.assetId.value = assetId;
    document.wellnessmatrixForm.action = "../wellnessmatrix/WellnessmatrixAction.do";
    document.wellnessmatrixForm.submit();
}

function showDetailBack()
{
    document.wellnessmatrixForm.doView.value = "yes";
    document.wellnessmatrixForm.doCancel.value = "no";
    document.wellnessmatrixForm.doCategory.value = "no";
    document.wellnessmatrixForm.doSaveCourse.value = "no";
    document.wellnessmatrixForm.action = "../wellnessmatrix/WellnessmatrixAction.do";
    document.wellnessmatrixForm.submit();
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
    return true;
}

function searchFormAjax(v, v1)
{
    if (checkSearch())
    {
        var url = "../ajax/wellnessmatrix/getinfo.jsp";
        var httploc = getHTTPObject();
        var getstr = "";
        var next_value = escape(document.wellnessmatrixForm.nextValue.value);
        var assetIdIndex = escape(document.wellnessmatrixForm.assetIdIndex.value);
        var clientIdIndex = escape(document.wellnessmatrixForm.clientIdIndex.value);
        getstr += "nextValue=" + next_value;
        getstr += "&next=" + v;
        getstr += "&clientIdIndex=" + clientIdIndex;
        getstr += "&assetIdIndex=" + assetIdIndex;
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
    document.forms[0].target = "_self";
    if (document.wellnessmatrixForm.doView)
        document.wellnessmatrixForm.doView.value = "no";
    if (document.wellnessmatrixForm.doCancel)
        document.wellnessmatrixForm.doCancel.value = "yes";
    if (document.wellnessmatrixForm.doSave)
        document.wellnessmatrixForm.doSave.value = "no";
    if (document.wellnessmatrixForm.doAssign)
        document.wellnessmatrixForm.doAssign.value = "no";
    if (document.wellnessmatrixForm.doDeleteDetail)
        document.wellnessmatrixForm.doDeleteDetail.value = "no";
    document.wellnessmatrixForm.action = "../wellnessmatrix/WellnessmatrixAction.do";
    document.wellnessmatrixForm.submit();
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
    } 
    else
    {
        document.getElementById("img_" + colid + "_" + updown).className = "sort_arrow active_sort";
    }
    var httploc = getHTTPObject();
    var url_sort = "../ajax/wellnessmatrix/sort.jsp";
    var getstr = "";
    var nextValue = 0;
    if (document.wellnessmatrixForm.nextValue)
        nextValue = document.wellnessmatrixForm.nextValue.value;
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
    document.wellnessmatrixForm.doView.value = "no";
    document.wellnessmatrixForm.doAdd.value = "yes";
    document.wellnessmatrixForm.action = "../wellnessmatrix/WellnessmatrixAction.do";
    document.wellnessmatrixForm.submit();
}

function assignquestion(positionId)
{
    document.forms[0].target = "_self";
    document.wellnessmatrixForm.doAssign.value = "yes";
    if (document.wellnessmatrixForm.doDeleteDetail)
        document.wellnessmatrixForm.doDeleteDetail.value = "no";
    if (document.wellnessmatrixForm.doView)
        document.wellnessmatrixForm.doView.value = "no";
    if (document.wellnessmatrixForm.doAdd)
        document.wellnessmatrixForm.doAdd.value = "no";
    document.wellnessmatrixForm.positionId.value = positionId;
    document.wellnessmatrixForm.action = "../wellnessmatrix/WellnessmatrixAction.do";
    document.wellnessmatrixForm.submit();
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
    if (checkWellnessmatrix())
    {
        document.getElementById('submitdiv').innerHTML = "<img src='../assets/images/loading.gif' align='absmiddle' />";
        document.wellnessmatrixForm.doSave.value = "yes";
        document.wellnessmatrixForm.doCancel.value = "no";
        if (document.wellnessmatrixForm.doView)
            document.wellnessmatrixForm.doView.value = "no";
        document.wellnessmatrixForm.action = "../wellnessmatrix/WellnessmatrixAction.do";
        document.wellnessmatrixForm.submit();
    }
}

function checkWellnessmatrix()
{
    if (trim(document.wellnessmatrixForm.name.value) == "")
    {
        Swal.fire({
            title: "Please enter wellnessmatrix name.",
            didClose: () => {
                document.wellnessmatrixForm.name.focus();
            }
        })
        return false;
    }
    if (validname(document.wellnessmatrixForm.name) == false)
    {
        return false;
    }
    if (trim(document.wellnessmatrixForm.assettypeId.value) == "-1")
    {
        Swal.fire({
            title: "Please select asset type.",
            didClose: () => {
                document.wellnessmatrixForm.assettypeId.focus();
            }
        })
        return false;
    }
    return true;
}

function resetForm()
{
    document.wellnessmatrixForm.reset();
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
            var url = "../ajax/wellnessmatrix/getinfo.jsp";
            var getstr = "";
            var httploc = getHTTPObject();
            var next_value = escape(document.wellnessmatrixForm.nextValue.value);
            var next_del = "-1";
            if (document.wellnessmatrixForm.nextDel)
                next_del = escape(document.wellnessmatrixForm.nextDel.value);
            var search_value = escape(document.wellnessmatrixForm.search.value);
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
    document.wellnessmatrixForm.type.value = id;
    document.wellnessmatrixForm.action = "../wellnessmatrix/WellnessmatrixExportAction.do";
    document.wellnessmatrixForm.submit();
}

function changeCategory()
{
    if (document.wellnessmatrixForm.categoryId.value == "-1")
    {
        Swal.fire({
            title: "Please select category.",
            didClose: () => {
                document.wellnessmatrixForm.categoryId.focus();
            }
        })
    } else
    {
        document.wellnessmatrixForm.categoryName.value = document.getElementById("categorId").options[document.getElementById("categorId").selectedIndex].text;
        document.wellnessmatrixForm.doCategory.value = "yes";
        if (document.wellnessmatrixForm.doView)
            document.wellnessmatrixForm.doView.value = "no";
        if (document.wellnessmatrixForm.doAdd)
            document.wellnessmatrixForm.doAdd.value = "no";
        if (document.wellnessmatrixForm.doAssign)
            document.wellnessmatrixForm.doAssign.value = "no";
        if (document.wellnessmatrixForm.doDeleteDetail)
            document.wellnessmatrixForm.doDeleteDetail.value = "no";
        if (document.wellnessmatrixForm.doSaveCourse)
            document.wellnessmatrixForm.doSaveCourse.value = "no";
        document.wellnessmatrixForm.action = "../wellnessmatrix/WellnessmatrixAction.do";
        document.wellnessmatrixForm.submit();
    }
}

function gobackassign()
{
    if (document.wellnessmatrixForm.doView)
        document.wellnessmatrixForm.doView.value = "yes";
    if (document.wellnessmatrixForm.doCancel)
        document.wellnessmatrixForm.doCancel.value = "no";
    if (document.wellnessmatrixForm.doSave)
        document.wellnessmatrixForm.doSave.value = "no";
    if (document.wellnessmatrixForm.doAssign)
        document.wellnessmatrixForm.doAssign.value = "no";
    if (document.wellnessmatrixForm.doDeleteDetail)
        document.wellnessmatrixForm.doDeleteDetail.value = "no";
    if (document.wellnessmatrixForm.doCategory)
        document.wellnessmatrixForm.doCategory.value = "no";
    if (document.wellnessmatrixForm.doSaveCourse)
        document.wellnessmatrixForm.doSaveCourse.value = "no";
    document.wellnessmatrixForm.action = "../wellnessmatrix/WellnessmatrixAction.do";
    document.wellnessmatrixForm.submit();
}

function setSubcat(size)
{
    if (document.getElementById("catcb").checked)
    {
        for (var i = 1; i <= size; i++)
        {
            document.getElementById("subcategorycb_" + i).checked = true;
            var subcategoryId = document.getElementById("subcategorycb_" + i).value;
            setcourse(subcategoryId, i, document.getElementById("questionsize_" + subcategoryId).value);
        }
    } else
    {
        for (var i = 1; i <= size; i++)
        {
            document.getElementById("subcategorycb_" + i).checked = false;
            var subcategoryId = document.getElementById("subcategorycb_" + i).value;
            setcourse(subcategoryId, i, document.getElementById("questionsize_" + subcategoryId).value);
        }
    }
}

function setcourse(subcategoryId, tp, size)
{
    if (document.getElementById("subcategorycb_" + tp).checked)
    {
        for (var i = 1; i <= size; i++)
        {
            document.getElementById("questioncb_" + subcategoryId + "_" + i).checked = true;
            document.getElementById("questionId_" + subcategoryId + "_" + i).value = document.getElementById("questioncb_" + subcategoryId + "_" + i).value;
            document.getElementById("subcategoryId_" + subcategoryId + "_" + i).value = subcategoryId;
        }
    } 
    else
    {
        for (var i = 1; i <= size; i++)
        {
            document.getElementById("questioncb_" + subcategoryId + "_" + i).checked = false;
            document.getElementById("questionId_" + subcategoryId + "_" + i).value = "-1";
            document.getElementById("subcategoryId_" + subcategoryId + "_" + i).value = "-1";
        }
    }
}

function checkcourse()
{
    var x = 0;
    if (Number(document.wellnessmatrixForm.categoryId.value) <= 0)
    {
        Swal.fire({
            title: "Please select category.",
            didClose: () => {
                document.wellnessmatrixForm.categoryId.focus();
            }
        })
        return false;
    }
    if (document.wellnessmatrixForm.questionId)
    {
        if (document.wellnessmatrixForm.questionId.length)
        {
            for (var i = 0; i < document.wellnessmatrixForm.questionId.length; i++)
            {                
                if (eval(document.wellnessmatrixForm.questionId[i].value) > 0)
                {
                    if (Number(document.wellnessmatrixForm.subcategoryId[i].value) <= 0)
                    {
                        Swal.fire({                            
                            title: "Please select sub category.",
                            didClose: () => {
                                document.wellnessmatrixForm.subcategoryId[i].focus();
                            }
                        })
                        return false;
                    }
                    x++;
                }
            }
        } else
        {
            if (eval(document.wellnessmatrixForm.questionId.value) > 0)
            {
                if (Number(document.wellnessmatrixForm.subcategoryId.value) <= 0)
                    {
                        Swal.fire({                            
                            title: "Please select sub category.",
                            didClose: () => {
                                document.wellnessmatrixForm.subcategoryId.focus();
                            }
                        })
                        return false;
                    }
                x++;
            }
        }
    }
    return true;
}

function save()
{
    if (checkcourse())
    {
        document.wellnessmatrixForm.doCancel.value = "no";
        document.wellnessmatrixForm.doCategory.value = "no";
        document.wellnessmatrixForm.doView.value = "no";
        document.wellnessmatrixForm.doSaveCourse.value = "yes";
        document.wellnessmatrixForm.action = "../wellnessmatrix/WellnessmatrixAction.do";
        document.wellnessmatrixForm.submit();
    }
}

function deleteFormDetail(clientId, assetId, positionId, status, id)
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
            document.wellnessmatrixForm.doDeleteDetail.value = "yes";
            if (document.wellnessmatrixForm.doAssign)
                document.wellnessmatrixForm.doAssign.value = "no";
            if (document.wellnessmatrixForm.doView)
                document.wellnessmatrixForm.doView.value = "no";
            if (document.wellnessmatrixForm.doAdd)
                document.wellnessmatrixForm.doAdd.value = "no";
            document.wellnessmatrixForm.positionId.value = positionId;
            document.wellnessmatrixForm.clientId.value = clientId;
            document.wellnessmatrixForm.assetId.value = assetId;
            document.wellnessmatrixForm.status.value = status;
            document.wellnessmatrixForm.action = "../wellnessmatrix/WellnessmatrixAction.do";
            document.wellnessmatrixForm.submit();
        } else
        {
            if (document.getElementById("flexSwitchCheckDefault_" + id).checked == true)
                document.getElementById("flexSwitchCheckDefault_" + id).checked = false;
            else
                document.getElementById("flexSwitchCheckDefault_" + id).checked = true;
        }
    })
}

function setquestionhidden(subcategoryId, tp)
{
    if(document.getElementById("questioncb_"+subcategoryId+"_"+tp).checked)
    {
        document.getElementById("questionId_"+subcategoryId+"_"+tp).value = document.getElementById("questioncb_"+subcategoryId+"_"+tp).value;
    }
    else
    {
        document.getElementById("questionId_"+subcategoryId+"_"+tp).value = "-1";
    }
}

function setAssetDDL()
{
    var url = "../ajax/wellnessmatrix/getasset.jsp";
    document.getElementById("assetIdIndex").value = '-1';
    var httploc = getHTTPObject();
    var getstr = "clientIdIndex=" + document.wellnessmatrixForm.clientIdIndex.value + "&from=asset";
    httploc.open("POST", url, true);
    httploc.onreadystatechange = function ()
    {
        if (httploc.readyState == 4)
        {
            if (httploc.status == 200)
            {
                var response = httploc.responseText;
                document.getElementById("assetIdIndex").innerHTML = '';
                document.getElementById("assetIdIndex").innerHTML = response;
            }
        }
    };
    httploc.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    httploc.send(getstr);
    searchFormAjax('s', '-1');
}

function sortFormPosition(colid, updown)
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
    var url_sort = "../ajax/wellnessmatrix/sortposition.jsp";
    var getstr = "";
    var nextValue = 0;
    if (document.wellnessmatrixForm.nextValue)
        nextValue = document.wellnessmatrixForm.nextValue.value;
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
                document.getElementById('sort_idp').innerHTML = '';
                document.getElementById('sort_idp').innerHTML = response;
            }
        }
    };
    httploc.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    httploc.setRequestHeader("Content-length", getstr.length);
    httploc.setRequestHeader("Connection", "close");
    httploc.send(getstr);
    document.getElementById('sort_idp').innerHTML = "<div><img src='../assets/images/loading.gif' align='absmiddle'/>Please wait. Loading...</div>";
}

function viewasset(clientId)
{
    document.forms[0].clientId.value = clientId;
    document.forms[0].doView.value = "yes";
    document.forms[0].target = "_blank";
    document.forms[0].action = "../client/ClientAction.do?tabno=2";
    document.forms[0].submit();
}