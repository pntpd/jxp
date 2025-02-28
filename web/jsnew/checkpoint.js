function resetFilter()
{
    document.forms[0].search.value = "";
    searchFormAjax('s', '-1');
}

function showDetail(id)
{
    document.checkpointForm.doView.value = "yes";
    document.checkpointForm.doModify.value = "no";
    document.checkpointForm.doAdd.value = "no";
    document.checkpointForm.checkpointId.value = id;
    document.checkpointForm.action = "../checkpoint/CheckPointAction.do";
    document.checkpointForm.submit();
}

function getView()
{
    document.checkpointForm.doView.value = "yes";
    document.checkpointForm.doModify.value = "no";
    if(document.checkpointForm.doAdd)
        document.checkpointForm.doAdd.value = "no";
    document.checkpointForm.action = "../checkpoint/CheckPointAction.do";
    document.checkpointForm.submit();
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

function searchFormAjax(v, v1)
{
    var url = "../ajax/checkpoint/getinfo.jsp";
    var httploc = getHTTPObject();
    var getstr = "";
    var next_value = escape(document.checkpointForm.nextValue.value);
    var search_value = escape(document.checkpointForm.search.value);
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

function goback()
{

    document.checkpointForm.doView.value = "no";
    document.checkpointForm.doCancel.value = "yes";
    document.checkpointForm.doSave.value = "no";
    document.checkpointForm.action = "../checkpoint/CheckPointAction.do";
    document.checkpointForm.submit();
}

function sortForm(colid, updown)
{
    for (i = 1; i <= 4; i++)
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
    var url_sort = "../ajax/checkpoint/sort.jsp";
    var getstr = "";
    var nextValue = 0;
    if (document.checkpointForm.nextValue)
        nextValue = document.checkpointForm.nextValue.value;
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
    document.checkpointForm.doModify.value = "no";
    document.checkpointForm.doView.value = "no";
    document.checkpointForm.doAdd.value = "yes";
    document.checkpointForm.action = "../checkpoint/CheckPointAction.do";
    document.checkpointForm.submit();
}

function modifyForm(id)
{
    document.checkpointForm.doModify.value = "yes";
    document.checkpointForm.doView.value = "no";
    document.checkpointForm.checkpointId.value = id;
    document.checkpointForm.action = "../checkpoint/CheckPointAction.do";
    document.checkpointForm.submit();
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

    if (checkCheckPoint())
    {
        document.getElementById('submitdiv').innerHTML = "<img src='../assets/images/loading.gif' align='absmiddle' />";
        document.checkpointForm.doSave.value = "yes";
        document.checkpointForm.doCancel.value = "no";
        document.checkpointForm.action = "../checkpoint/CheckPointAction.do";
        document.checkpointForm.submit();
    }
}

function submitAddMore()
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
    if (checkCheckPoint())
    {
        document.checkpointForm.doAddMore.value = "yes";
        document.checkpointForm.doSave.value = "no";
        document.checkpointForm.doCancel.value = "no";
        document.checkpointForm.action = "../checkpoint/CheckPointAction.do";
        document.checkpointForm.submit();
    }
}

function checkCheckPoint()
{
    if (trim(document.checkpointForm.name.value) == "")
    {
        Swal.fire({
            title: "Please enter checkpoint name.",
            didClose: () => {
                document.checkpointForm.name.focus();
            }
        })
        return false;
    }
    if (validname(document.checkpointForm.name) == false)
    {
        return false;
    }

    if (trim(document.checkpointForm.clientId.value) == "-1")
    {
        Swal.fire({
            title: "Please select client.",
            didClose: () => {
                document.checkpointForm.clientId.focus();
            }
        })
        return false;
    }
    if (document.checkpointForm.clientassetddl.value == "-1")
    {
        Swal.fire({
            title: "Please select asset.",
            didClose: () => {
                document.checkpointForm.clientassetddl.focus();
            }
        })
        return false;
    }
    if (document.checkpointForm.positionddl.value == "-1")
    {
        Swal.fire({
            title: "Please select position.",
            didClose: () => {
                document.checkpointForm.positionddl.focus();
            }
        })
        return false;
    }
    if (document.checkpointForm.Gradeddl.value == "-1")
    {
        Swal.fire({
            title: "Please select rank.",
            didClose: () => {
                document.checkpointForm.Gradeddl.focus();
            }
        })
        return false;
    }

    if (trim(document.checkpointForm.displaynote.value) == "")
    {
        Swal.fire({
            title: "Please enter display note.",
            didClose: () => {
                document.checkpointForm.displaynote.focus();
            }
        })
        return false;
    }
    if (validdesc(document.checkpointForm.displaynote) == false)
    {
        return false;
    }
    return true;
}

function resetForm()
{
    document.checkpointForm.reset();
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
            var url = "../ajax/checkpoint/getinfo.jsp";
            var getstr = "";
            var httploc = getHTTPObject();
            var next_value = escape(document.checkpointForm.nextValue.value);
            var next_del = "-1";
            if (document.checkpointForm.nextDel)
                next_del = escape(document.checkpointForm.nextDel.value);
            var search_value = escape(document.checkpointForm.search.value);
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
                            swal.fire(v2);
                        }
                    }
                }
            };
            httploc.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
            httploc.setRequestHeader("Content-length", getstr.length);
            httploc.setRequestHeader("Connection", "close");
            httploc.send(getstr);
            document.getElementById('ajax_cat').innerHTML = "<div><img src='../assets/images/loading.gif' align='absmiddle'/>Please wait. Loading...</div>";
        } else
        {
            if (document.getElementById("flexSwitchCheckDefault_" + id).checked == true)
                document.getElementById("flexSwitchCheckDefault_" + id).checked = false;
            else
                document.getElementById("flexSwitchCheckDefault_" + id).checked = true;
        }
    });
}

function exporttoexcel()
{
    document.checkpointForm.action = "../checkpoint/CheckPointExportAction.do";
    document.checkpointForm.submit();
}

function setAssetDDL()
{
    var url = "../ajax/jobpost/getasset.jsp";
    var httploc = getHTTPObject();
    var getstr = "clientId=" + document.checkpointForm.clientId.value + "&from=asset";
    httploc.open("POST", url, true);
    httploc.onreadystatechange = function ()
    {
        if (httploc.readyState == 4)
        {
            if (httploc.status == 200)
            {
                var response = httploc.responseText;
                document.getElementById("clientassetddl").innerHTML = '';
                document.getElementById("clientassetddl").innerHTML = response;
                document.checkpointForm.positionname.value = "";
                setPositionDDL();
            }
        }
    };
    httploc.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    httploc.send(getstr);
}

function setPositionDDL()
{
    var url = "../ajax/jobpost/getasset.jsp";
    var httploc = getHTTPObject();
    var getstr = "clientassetId=" + document.checkpointForm.clientassetddl.value + "&from=position";
    httploc.open("POST", url, true);
    httploc.onreadystatechange = function ()
    {
        if (httploc.readyState == 4)
        {
            if (httploc.status == 200)
            {
                var response = httploc.responseText;
                document.getElementById("positionddl").innerHTML = '';
                document.getElementById("positionddl").innerHTML = response;
                document.checkpointForm.positionname.value = "";
                setGradeDDL();
            }
        }
    };
    httploc.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    httploc.send(getstr);
}

function setGradeDDL()
{
    var url = "../ajax/jobpost/getasset.jsp";
    var httploc = getHTTPObject();
    var positionId = document.getElementById("positionddl");
    var pname = positionId.options[positionId.selectedIndex].text;
    if (pname == "- Select -") {
        document.checkpointForm.positionname.value = "";
    } else {
        document.checkpointForm.positionname.value = pname;
    }
    var getstr = "clientassetId=" + document.checkpointForm.clientassetddl.value + "&positionnm=" + document.checkpointForm.positionname.value + "&from=grade";
    httploc.open("POST", url, true);
    httploc.onreadystatechange = function ()
    {
        if (httploc.readyState == 4)
        {
            if (httploc.status == 200)
            {
                var response = httploc.responseText;
                document.getElementById("Gradeddl").innerHTML = '';
                document.getElementById("Gradeddl").innerHTML = response;
            }
        }
    };
    httploc.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    httploc.send(getstr);
}
