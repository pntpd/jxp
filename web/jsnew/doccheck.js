function resetFilter()
{
    document.forms[0].search.value = "";
    document.doccheckForm.clientIndex.value = "-1";
    document.doccheckForm.assetIndex.value = "-1";
    searchFormAjax('s', '-1');
    getassetIndex();
}

function showDetail(id)
{
    document.doccheckForm.doView.value = "yes";
    document.doccheckForm.doModify.value = "no";
    document.doccheckForm.doAdd.value = "no";
    document.doccheckForm.doccheckId.value = id;
    document.doccheckForm.action = "../doccheck/DoccheckAction.do";
    document.doccheckForm.submit();
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
        var url = "../ajax/doccheck/getinfo.jsp";
        var httploc = getHTTPObject();
        var getstr = "";
        var next_value = escape(document.doccheckForm.nextValue.value);
        var search_value = escape(document.doccheckForm.search.value);
        var assetIndex = escape(document.doccheckForm.assetIndex.value);
        var clientIndex = escape(document.doccheckForm.clientIndex.value);
        getstr += "nextValue=" + next_value;
        getstr += "&next=" + v;
        getstr += "&search=" + search_value;
        getstr += "&clientIndex=" + document.doccheckForm.clientIndex.value;
        getstr += "&assetIndex=" + document.doccheckForm.assetIndex.value;
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
    if (document.doccheckForm.doView)
        document.doccheckForm.doView.value = "no";
    if (document.doccheckForm.doCancel)
        document.doccheckForm.doCancel.value = "yes";
    if (document.doccheckForm.doSave)
        document.doccheckForm.doSave.value = "no";
    document.doccheckForm.action = "../doccheck/DoccheckAction.do";
    document.doccheckForm.submit();
}

function sortForm(colid, updown)
{
    for (i = 1; i <= 3; i++)
    {
        document.getElementById("img_" + i + "_2").className = "sort_arrow deactive_sort";
        document.getElementById("img_" + i + "_1").className = "sort_arrow deactive_sort";
    }
    if (updown == 3)
    {
        document.getElementById("img_" + colid + "_2").className = "sort_arrow active_sort";

    } else
    {
        document.getElementById("img_" + colid + "_" + updown).className = "sort_arrow active_sort";
    }
    var httploc = getHTTPObject();
    var url_sort = "../ajax/doccheck/sort.jsp";
    var getstr = "";
    var nextValue = 0;
    if (document.doccheckForm.nextValue)
        nextValue = document.doccheckForm.nextValue.value;
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
    document.doccheckForm.doModify.value = "no";
    document.doccheckForm.doView.value = "no";
    document.doccheckForm.doAdd.value = "yes";
    document.doccheckForm.action = "../doccheck/DoccheckAction.do";
    document.doccheckForm.submit();
}

function modifyForm(id)
{
    document.doccheckForm.doModify.value = "yes";
    document.doccheckForm.doView.value = "no";
    document.doccheckForm.doAdd.value = "no";
    document.doccheckForm.doccheckId.value = id;
    document.doccheckForm.action = "../doccheck/DoccheckAction.do";
    document.doccheckForm.submit();
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
    if (checkDoccheck())
    {
        document.getElementById('submitdiv').innerHTML = "<img src='../assets/images/loading.gif' align='absmiddle' />";
        document.doccheckForm.doSave.value = "yes";
        document.doccheckForm.doCancel.value = "no";
        document.doccheckForm.action = "../doccheck/DoccheckAction.do";
        document.doccheckForm.submit();
    }
}

function checkDoccheck()
{
    if (document.doccheckForm.clientId.value == "-1")
    {
        Swal.fire({
            title: "Please select client.",
            didClose: () => {
                document.doccheckForm.clientId.focus();
            }
        })
        return false;
    }
    if (document.doccheckForm.assetId.value == "-1")
    {
        Swal.fire({
            title: "Please select asset.",
            didClose: () => {
                document.doccheckForm.assetId.focus();
            }
        })
        return false;
    }
    if (trim(document.doccheckForm.doccheckName.value) == "")
    {
        Swal.fire({
            title: "Please enter name.",
            didClose: () => {
                document.doccheckForm.doccheckName.focus();
            }
        })
        return false;
    }
    if (validname(document.doccheckForm.doccheckName) == false)
    {
        return false;
    }

    return true;
}

function resetForm()
{
    document.doccheckForm.reset();
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
            var url = "../ajax/doccheck/getinfo.jsp";
            var getstr = "";
            var httploc = getHTTPObject();
            var next_value = escape(document.doccheckForm.nextValue.value);
            var next_del = "-1";
            if (document.doccheckForm.nextDel)
                next_del = escape(document.doccheckForm.nextDel.value);
            var search_value = escape(document.doccheckForm.search.value);
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
    document.doccheckForm.action = "../doccheck/DoccheckExportAction.do";
    document.doccheckForm.submit();
}

function getassetDDL()
{
    var url = "../ajax/doccheck/getassetDDL.jsp";
    var httploc = getHTTPObject();
    var getstr = "clientId=" + document.doccheckForm.clientId.value;
    httploc.open("POST", url, true);
    httploc.onreadystatechange = function ()
    {
        if (httploc.readyState == 4)
        {
            if (httploc.status == 200)
            {
                var response = httploc.responseText;
                document.getElementById("assetdiv").innerHTML = '';
                document.getElementById("assetdiv").innerHTML = response;
            }
        }
    };
    httploc.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    httploc.setRequestHeader("Content-length", getstr.length);
    httploc.setRequestHeader("Connection", "close");
    httploc.send(getstr);
}

function getassetIndex()
{
    var url = "../ajax/doccheck/getassetIndex.jsp";
    var httploc = getHTTPObject();
    var getstr = "clientIndex=" + document.doccheckForm.clientIndex.value;
    httploc.open("POST", url, true);
    httploc.onreadystatechange = function ()
    {
        if (httploc.readyState == 4)
        {
            if (httploc.status == 200)
            {
                var response = httploc.responseText;
                document.getElementById("assetdivId").innerHTML = '';
                document.getElementById("assetdivId").innerHTML = response;
            }
        }
    };
    httploc.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    httploc.setRequestHeader("Content-length", getstr.length);
    httploc.setRequestHeader("Connection", "close");
    httploc.send(getstr);
}