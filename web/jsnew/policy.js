function resetFilter()
{
    document.forms[0].search.value = "";
    document.policyForm.clientIndex.value = "-1";
    document.policyForm.assetIndex.value = "-1";
    searchFormAjax('s', '-1');
    getassetIndex();
}

function showDetail(id)
{
    document.policyForm.doView.value = "yes";
    document.policyForm.doModify.value = "no";
    document.policyForm.doAdd.value = "no";
    document.policyForm.policyId.value = id;
    document.policyForm.action = "../policy/PolicyAction.do";
    document.policyForm.submit();
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
        var url = "../ajax/policy/getinfo.jsp";
        var httploc = getHTTPObject();
        var getstr = "";
        var next_value = escape(document.policyForm.nextValue.value);
        var search_value = escape(document.policyForm.search.value);
        var assetIndex = escape(document.policyForm.assetIndex.value);
        var clientIndex = escape(document.policyForm.clientIndex.value);
        getstr += "nextValue=" + next_value;
        getstr += "&next=" + v;
        getstr += "&search=" + search_value;
        getstr += "&clientIndex=" + document.policyForm.clientIndex.value;
        getstr += "&assetIndex=" + document.policyForm.assetIndex.value;
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
    if (document.policyForm.doView)
        document.policyForm.doView.value = "no";
    if (document.policyForm.doCancel)
        document.policyForm.doCancel.value = "yes";
    if (document.policyForm.doSave)
        document.policyForm.doSave.value = "no";
    document.policyForm.action = "../policy/PolicyAction.do";
    document.policyForm.submit();
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
    var url_sort = "../ajax/policy/sort.jsp";
    var getstr = "";
    var nextValue = 0;
    if (document.policyForm.nextValue)
        nextValue = document.policyForm.nextValue.value;
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
    document.policyForm.doModify.value = "no";
    document.policyForm.doView.value = "no";
    document.policyForm.doAdd.value = "yes";
    document.policyForm.action = "../policy/PolicyAction.do";
    document.policyForm.submit();
}

function modifyForm(id)
{
    document.policyForm.doModify.value = "yes";
    document.policyForm.doView.value = "no";
    document.policyForm.doAdd.value = "no";
    document.policyForm.policyId.value = id;
    document.policyForm.action = "../policy/PolicyAction.do";
    document.policyForm.submit();
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
    if (checkPolicy())
    {
        document.getElementById('submitdiv').innerHTML = "<img src='../assets/images/loading.gif' align='absmiddle' />";
        document.policyForm.doSave.value = "yes";
        document.policyForm.doCancel.value = "no";
        document.policyForm.action = "../policy/PolicyAction.do";
        document.policyForm.submit();
    }
}

function checkPolicy()
{
    if (document.policyForm.clientId.value == "-1")
    {
        Swal.fire({
            title: "Please select client.",
            didClose: () => {
                document.policyForm.clientId.focus();
            }
        })
        return false;
    }
    if (trim(document.policyForm.policyName.value) == "")
    {
        Swal.fire({
            title: "Please enter document name.",
            didClose: () => {
                document.policyForm.policyName.focus();
            }
        })
        return false;
    }
    if (validname(document.policyForm.policyName) == false)
    {
        return false;
    }    
    if (document.policyForm.filehidden.value == "")
    {
        if (document.policyForm.policyfile.value == "")
        {
            Swal.fire({
                    title: "Please upload document.",
                    didClose: () => {
                        document.policyForm.policyfile.focus();
                    }
                })
                return false;
        }
    }
    if (document.policyForm.filehidden.value == "")
    {
        if (!(document.policyForm.policyfile.value).match(/(\.(pdf)|(doc)|(docx))$/i))
        {
            Swal.fire({
                title: "Only .pdf, .doc, .docx are allowed.",
                didClose: () => {
                    document.policyForm.policyfile.focus();
                }
            })
            return false;
        }
        var input = document.policyForm.policyfile;
        if (input.files)
        {
            var file = input.files[0];
            if (file.size > 1024 * 1024 * 5)
            {
                Swal.fire({
                    title: "File size should not exceed 5 MB.",
                    didClose: () => {
                        document.policyForm.policyfile.focus();
                    }
                })
                return false;
            }
        }    
    }
    return true;
}

function resetForm()
{
    document.policyForm.reset();
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
            var url = "../ajax/policy/getinfo.jsp";
            var getstr = "";
            var httploc = getHTTPObject();
            var next_value = escape(document.policyForm.nextValue.value);
            var next_del = "-1";
            if (document.policyForm.nextDel)
                next_del = escape(document.policyForm.nextDel.value);
            var search_value = escape(document.policyForm.search.value);
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
    document.policyForm.action = "../policy/PolicyExportAction.do";
    document.policyForm.submit();
}

function getassetDDL()
{
    var url = "../ajax/policy/getassetDDL.jsp";
    var httploc = getHTTPObject();
    var getstr = "clientId=" + document.policyForm.clientId.value;
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
    var url = "../ajax/policy/getassetIndex.jsp";
    var httploc = getHTTPObject();
    var getstr = "clientIndex=" + document.policyForm.clientIndex.value;
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

function setClass(tp)
{
    document.getElementById("upload_link_" + tp).className = "attache_btn uploaded_img";
}