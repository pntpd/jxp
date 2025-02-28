function resetFilter()
{
    document.forms[0].clientIdIndex.value = "-1";
    document.forms[0].assetIdIndex.value = "-1";
    setAssetDDL();
    searchFormAjax('s', '-1');
}

function showDetail(clientId, assetId)
{
    document.knowledgebasematrixForm.doView.value = "yes";
    document.knowledgebasematrixForm.doAdd.value = "no";
    document.knowledgebasematrixForm.clientId.value = clientId;
    document.knowledgebasematrixForm.assetId.value = assetId;
    document.knowledgebasematrixForm.action = "../knowledgebasematrix/KnowledgebasematrixAction.do";
    document.knowledgebasematrixForm.submit();
}

function showDetailBack()
{
    document.knowledgebasematrixForm.doView.value = "yes";
    document.knowledgebasematrixForm.doCancel.value = "no";
    document.knowledgebasematrixForm.doCategory.value = "no";
    document.knowledgebasematrixForm.doSaveCourse.value = "no";
    document.knowledgebasematrixForm.action = "../knowledgebasematrix/KnowledgebasematrixAction.do";
    document.knowledgebasematrixForm.submit();
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
        var url = "../ajax/knowledgebasematrix/getinfo.jsp";
        var httploc = getHTTPObject();
        var getstr = "";
        var next_value = escape(document.knowledgebasematrixForm.nextValue.value);
        var assetIdIndex = escape(document.knowledgebasematrixForm.assetIdIndex.value);
        var clientIdIndex = escape(document.knowledgebasematrixForm.clientIdIndex.value);
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
    if (document.knowledgebasematrixForm.doView)
        document.knowledgebasematrixForm.doView.value = "no";
    if (document.knowledgebasematrixForm.doCancel)
        document.knowledgebasematrixForm.doCancel.value = "yes";
    if (document.knowledgebasematrixForm.doSave)
        document.knowledgebasematrixForm.doSave.value = "no";
    if (document.knowledgebasematrixForm.doAssign)
        document.knowledgebasematrixForm.doAssign.value = "no";
    if (document.knowledgebasematrixForm.doDeleteDetail)
        document.knowledgebasematrixForm.doDeleteDetail.value = "no";
    document.knowledgebasematrixForm.action = "../knowledgebasematrix/KnowledgebasematrixAction.do";
    document.knowledgebasematrixForm.submit();
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
    var url_sort = "../ajax/knowledgebasematrix/sort.jsp";
    var getstr = "";
    var nextValue = 0;
    if (document.knowledgebasematrixForm.nextValue)
        nextValue = document.knowledgebasematrixForm.nextValue.value;
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
    document.knowledgebasematrixForm.doView.value = "no";
    document.knowledgebasematrixForm.doAdd.value = "yes";
    document.knowledgebasematrixForm.action = "../knowledgebasematrix/KnowledgebasematrixAction.do";
    document.knowledgebasematrixForm.submit();
}

function assignquestion(positionId)
{
    document.forms[0].target = "_self";
    document.knowledgebasematrixForm.doAssign.value = "yes";
    if (document.knowledgebasematrixForm.doDeleteDetail)
        document.knowledgebasematrixForm.doDeleteDetail.value = "no";
    if (document.knowledgebasematrixForm.doView)
        document.knowledgebasematrixForm.doView.value = "no";
    if (document.knowledgebasematrixForm.doAdd)
        document.knowledgebasematrixForm.doAdd.value = "no";
    document.knowledgebasematrixForm.positionId.value = positionId;
    document.knowledgebasematrixForm.action = "../knowledgebasematrix/KnowledgebasematrixAction.do";
    document.knowledgebasematrixForm.submit();
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
        document.knowledgebasematrixForm.doSave.value = "yes";
        document.knowledgebasematrixForm.doCancel.value = "no";
        if (document.knowledgebasematrixForm.doView)
            document.knowledgebasematrixForm.doView.value = "no";
        document.knowledgebasematrixForm.action = "../knowledgebasematrix/KnowledgebasematrixAction.do";
        document.knowledgebasematrixForm.submit();
    }
}

function checkWellnessmatrix()
{
    if (trim(document.knowledgebasematrixForm.name.value) == "")
    {
        Swal.fire({
            title: "Please enter knowledge base matrix name.",
            didClose: () => {
                document.knowledgebasematrixForm.name.focus();
            }
        })
        return false;
    }
    if (validname(document.knowledgebasematrixForm.name) == false)
    {
        return false;
    }
    if (trim(document.knowledgebasematrixForm.assettypeId.value) == "-1")
    {
        Swal.fire({
            title: "Please select asset type.",
            didClose: () => {
                document.knowledgebasematrixForm.assettypeId.focus();
            }
        })
        return false;
    }
    return true;
}

function resetForm()
{
    document.knowledgebasematrixForm.reset();
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
            var url = "../ajax/knowledgebasematrix/getinfo.jsp";
            var getstr = "";
            var httploc = getHTTPObject();
            var next_value = escape(document.knowledgebasematrixForm.nextValue.value);
            var next_del = "-1";
            if (document.knowledgebasematrixForm.nextDel)
                next_del = escape(document.knowledgebasematrixForm.nextDel.value);
            var search_value = escape(document.knowledgebasematrixForm.search.value);
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

function exporttoexcel(type)
{
    document.forms[0].target = "_blank";
    document.knowledgebasematrixForm.type.value = type;
    document.knowledgebasematrixForm.action = "../knowledgebasematrix/KnowledgebasematrixExportAction.do";
    document.knowledgebasematrixForm.submit();
}

function changeCategory()
{
    if (document.knowledgebasematrixForm.categoryId.value == "-1")
    {
        Swal.fire({
            title: "Please select category.",
            didClose: () => {
                document.knowledgebasematrixForm.categoryId.focus();
            }
        })
    } else
    {
        document.knowledgebasematrixForm.categoryName.value = document.getElementById("categorId").options[document.getElementById("categorId").selectedIndex].text;
        document.knowledgebasematrixForm.doCategory.value = "yes";
        if (document.knowledgebasematrixForm.doView)
            document.knowledgebasematrixForm.doView.value = "no";
        if (document.knowledgebasematrixForm.doAdd)
            document.knowledgebasematrixForm.doAdd.value = "no";
        if (document.knowledgebasematrixForm.doAssign)
            document.knowledgebasematrixForm.doAssign.value = "no";
        if (document.knowledgebasematrixForm.doDeleteDetail)
            document.knowledgebasematrixForm.doDeleteDetail.value = "no";
        if (document.knowledgebasematrixForm.doSaveCourse)
            document.knowledgebasematrixForm.doSaveCourse.value = "no";
        document.knowledgebasematrixForm.action = "../knowledgebasematrix/KnowledgebasematrixAction.do";
        document.knowledgebasematrixForm.submit();
    }
}

function gobackassign()
{
    if (document.knowledgebasematrixForm.doView)
        document.knowledgebasematrixForm.doView.value = "yes";
    if (document.knowledgebasematrixForm.doCancel)
        document.knowledgebasematrixForm.doCancel.value = "no";
    if (document.knowledgebasematrixForm.doSave)
        document.knowledgebasematrixForm.doSave.value = "no";
    if (document.knowledgebasematrixForm.doAssign)
        document.knowledgebasematrixForm.doAssign.value = "no";
    if (document.knowledgebasematrixForm.doDeleteDetail)
        document.knowledgebasematrixForm.doDeleteDetail.value = "no";
    if (document.knowledgebasematrixForm.doCategory)
        document.knowledgebasematrixForm.doCategory.value = "no";
    if (document.knowledgebasematrixForm.doSaveCourse)
        document.knowledgebasematrixForm.doSaveCourse.value = "no";
    document.knowledgebasematrixForm.action = "../knowledgebasematrix/KnowledgebasematrixAction.do";
    document.knowledgebasematrixForm.submit();
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
    } else
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
    if (Number(document.knowledgebasematrixForm.categoryId.value) <= 0)
    {
        Swal.fire({
            title: "Please select category.",
            didClose: () => {
                document.knowledgebasematrixForm.categoryId.focus();
            }
        })
        return false;
    }
    if (document.knowledgebasematrixForm.questionId)
    {
        if (document.knowledgebasematrixForm.questionId.length)
        {
            for (var i = 0; i < document.knowledgebasematrixForm.questionId.length; i++)
            {
                if (eval(document.knowledgebasematrixForm.questionId[i].value) > 0)
                {
                    if (Number(document.knowledgebasematrixForm.subcategoryId[i].value) <= 0)
                    {
                        Swal.fire({
                            title: "Please select module.",
                            didClose: () => {
                                document.knowledgebasematrixForm.subcategoryId[i].focus();
                            }
                        })
                        return false;
                    }
                    x++;
                }
            }
        } else
        {
            if (eval(document.knowledgebasematrixForm.questionId.value) > 0)
            {
                if (Number(document.knowledgebasematrixForm.subcategoryId.value) <= 0)
                {
                    Swal.fire({
                        title: "Please select module.",
                        didClose: () => {
                            document.knowledgebasematrixForm.subcategoryId.focus();
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
        document.knowledgebasematrixForm.doCancel.value = "no";
        document.knowledgebasematrixForm.doCategory.value = "no";
        document.knowledgebasematrixForm.doView.value = "no";
        document.knowledgebasematrixForm.doSaveCourse.value = "yes";
        document.knowledgebasematrixForm.action = "../knowledgebasematrix/KnowledgebasematrixAction.do";
        document.knowledgebasematrixForm.submit();
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
            document.knowledgebasematrixForm.doDeleteDetail.value = "yes";
            if (document.knowledgebasematrixForm.doAssign)
                document.knowledgebasematrixForm.doAssign.value = "no";
            if (document.knowledgebasematrixForm.doView)
                document.knowledgebasematrixForm.doView.value = "no";
            if (document.knowledgebasematrixForm.doAdd)
                document.knowledgebasematrixForm.doAdd.value = "no";
            document.knowledgebasematrixForm.positionId.value = positionId;
            document.knowledgebasematrixForm.clientId.value = clientId;
            document.knowledgebasematrixForm.assetId.value = assetId;
            document.knowledgebasematrixForm.status.value = status;
            document.knowledgebasematrixForm.action = "../knowledgebasematrix/KnowledgebasematrixAction.do";
            document.knowledgebasematrixForm.submit();
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
    if (document.getElementById("questioncb_" + subcategoryId + "_" + tp).checked)
    {
        document.getElementById("questionId_" + subcategoryId + "_" + tp).value = document.getElementById("questioncb_" + subcategoryId + "_" + tp).value;
    } else
    {
        document.getElementById("questionId_" + subcategoryId + "_" + tp).value = "-1";
    }
}

function setAssetDDL()
{
    var url = "../ajax/knowledgebasematrix/getasset.jsp";
    document.getElementById("assetIdIndex").value = '-1';
    var httploc = getHTTPObject();
    var getstr = "clientIdIndex=" + document.knowledgebasematrixForm.clientIdIndex.value + "&from=asset";
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
    var url_sort = "../ajax/knowledgebasematrix/sortposition.jsp";
    var getstr = "";
    var nextValue = 0;
    if (document.knowledgebasematrixForm.nextValue)
        nextValue = document.knowledgebasematrixForm.nextValue.value;
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