function resetFilter()
{
    document.forms[0].search.value = "";
    document.forms[0].clientIdIndex.value = "-1";
    document.forms[0].assetIdIndex.value = "-1";
    setAssetDDL();
    searchFormAjax('s', '-1');
}

function showDetail(clientId,assetId)
{
    document.billingcycleForm.doView.value = "no";
    document.billingcycleForm.doModify.value = "no";
    document.billingcycleForm.doAdd.value = "yes";
    document.billingcycleForm.clientId.value = clientId;
    document.billingcycleForm.assetId.value = assetId;
    document.billingcycleForm.action = "../billingcycle/BillingcycleAction.do";
    document.billingcycleForm.submit();
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
        var url = "../ajax/billingcycle/getinfo.jsp";
        var httploc = getHTTPObject();
        var getstr = "";
        var next_value = escape(document.billingcycleForm.nextValue.value);
        var assetIdIndex = escape(document.billingcycleForm.assetIdIndex.value);
        var clientIdIndex = escape(document.billingcycleForm.clientIdIndex.value);
        var search = document.billingcycleForm.search.value;
        getstr += "nextValue=" + next_value;
        getstr += "&next=" + v;
        getstr += "&clientIdIndex=" + clientIdIndex;
        getstr += "&assetIdIndex=" + assetIdIndex;
        getstr += "&search=" + search;
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
    if (document.billingcycleForm.doView)
        document.billingcycleForm.doView.value = "no";
    if (document.billingcycleForm.doCancel)
        document.billingcycleForm.doCancel.value = "yes";
    if (document.billingcycleForm.doSave)
        document.billingcycleForm.doSave.value = "no";
    if (document.billingcycleForm.doAssign)
        document.billingcycleForm.doAssign.value = "no";
    if (document.billingcycleForm.doDeleteDetail)
        document.billingcycleForm.doDeleteDetail.value = "no";
    if (document.billingcycleForm.doModify)
        document.billingcycleForm.doModify.value = "no";
    document.billingcycleForm.action = "../billingcycle/BillingcycleAction.do";
    document.billingcycleForm.submit();
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
    var url_sort = "../ajax/billingcycle/sort.jsp";
    var getstr = "";
    var nextValue = 0;
    if (document.billingcycleForm.nextValue)
        nextValue = document.billingcycleForm.nextValue.value;
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
    document.billingcycleForm.doModify.value = "no";
    document.billingcycleForm.doView.value = "no";
    document.billingcycleForm.doAdd.value = "yes";
    document.billingcycleForm.action = "../billingcycle/BillingcycleAction.do";
    document.billingcycleForm.submit();
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
    if (checkBillingcycle())
    {
        document.getElementById('submitdiv').innerHTML = "<img src='../assets/images/loading.gif' align='absmiddle' />";
        document.billingcycleForm.doSave.value = "yes";
        document.billingcycleForm.doCancel.value = "no";
        if (document.billingcycleForm.doView)
            document.billingcycleForm.doView.value = "no";
        document.billingcycleForm.action = "../billingcycle/BillingcycleAction.do";
        document.billingcycleForm.submit();
    }
}

function resetForm()
{
    document.billingcycleForm.reset();
}

function exporttoexcel()
{
    document.billingcycleForm.action = "../billingcycle/BillingcycleExportAction.do";
    document.billingcycleForm.submit();
}

function checkcourse()
{
    if (document.billingcycleForm.schedulevalue.value == "" )
        {
         Swal.fire({
        title: "Please select day.",
        didClose:() => {
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
        document.billingcycleForm.doCancel.value = "no";
        document.billingcycleForm.doModify.value = "no";
        document.billingcycleForm.doView.value = "no";
        document.getElementById('submitdiv').innerHTML = "<img src='../assets/images/loading.gif' align='absmiddle' />";
        document.billingcycleForm.doSaveCourse.value = "yes";
        document.billingcycleForm.action = "../billingcycle/BillingcycleAction.do";
        document.billingcycleForm.submit();
    }
}

function deleteFormDetail(clientId, assetId, status, id)
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
            document.billingcycleForm.doDeleteDetail.value = "yes";
            if (document.billingcycleForm.doAssign)
                document.billingcycleForm.doAssign.value = "no";
            if (document.billingcycleForm.doView)
                document.billingcycleForm.doView.value = "no";
            if (document.billingcycleForm.doAdd)
                document.billingcycleForm.doAdd.value = "no";
            if (document.billingcycleForm.doModify)
                document.billingcycleForm.doModify.value = "no";
            document.billingcycleForm.clientId.value = clientId;
            document.billingcycleForm.assetId.value = assetId;
            document.billingcycleForm.status.value = status;
            document.billingcycleForm.action = "../billingcycle/BillingcycleAction.do";
            document.billingcycleForm.submit();
        } else
        {
            if (document.getElementById("flexSwitchCheckDefault_" + id).checked == true)
                document.getElementById("flexSwitchCheckDefault_" + id).checked = false;
            else
                document.getElementById("flexSwitchCheckDefault_" + id).checked = true;
        }
    })
}

function setAssetDDL()
{
    var url = "../ajax/billingcycle/getasset.jsp";
    document.getElementById("assetIdIndex").value = '-1';
    var httploc = getHTTPObject();
    var getstr = "clientIdIndex=" + document.billingcycleForm.clientIdIndex.value + "&from=asset";
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
    var url_sort = "../ajax/billingcycle/sortposition.jsp";
    var getstr = "";
    var nextValue = 0;
    if (document.billingcycleForm.nextValue)
        nextValue = document.billingcycleForm.nextValue.value;
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

function showhideschedule()
{
    document.forms[0].schedulevalue.value = "";
    setweekdays(0);
    setmonthdays(0);
    if(document.billingcycleForm.repeatId.value == "1")
    {
        document.getElementById('weekdaysId').style.display = "block";
        document.getElementById('monthdaysId').style.display = "none";
    }
    else if(document.billingcycleForm.repeatId.value == "2")
    {
        document.getElementById('weekdaysId').style.display = "none";
        document.getElementById('monthdaysId').style.display = "block";
    }
    else
    {
        document.getElementById('monthdaysId').style.display = "none";
        document.getElementById('weekdaysId').style.display = "none";
    }
}

function checkWeek(mvalue)
{
    document.forms[0].schedulevalue.value = "";
    addWeek(mvalue);
    setweekdays(mvalue);
}

function addWeek(mvalue)
{
    if (document.forms[0].schedulevalue)
    {
        document.forms[0].schedulevalue.value = mvalue;
    }
}

function setweekdays(weekId)
{
    var url = "../ajax/billingcycle/getweekdays.jsp";
    var httploc = getHTTPObject();
    var getstr = "schedulevalue=" + document.forms[0].schedulevalue.value;
    httploc.open("POST", url, true);
    httploc.onreadystatechange = function ()
    {
        if (httploc.readyState == 4)
        {
            if (httploc.status == 200)
            {
                var response = httploc.responseText;
                document.getElementById('weekviewId').innerHTML = '';
                document.getElementById('weekviewId').innerHTML = response;
            }
        }
    };
    httploc.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    httploc.setRequestHeader("Content-length", getstr.length);
    httploc.setRequestHeader("Connection", "close");
    httploc.send(getstr);
    document.getElementById('weekviewId').innerHTML = "<div><img src='../assets/images/loading.gif' align='absmiddle'/>Please wait. Loading...</div>";
}

function checkmonth(mvalue)
{
        document.forms[0].schedulevalue.value = "";
        addMonth(mvalue);
        setmonthdays(mvalue);
}

function addMonth(mvalue)
{
    if (document.forms[0].schedulevalue)
    {
        document.forms[0].schedulevalue.value = mvalue;
    }
}

function setmonthdays(monthId)
{
    var url = "../ajax/billingcycle/getmonthdays.jsp";
    var httploc = getHTTPObject();
    var getstr = "schedulevalue=" + document.forms[0].schedulevalue.value;
    httploc.open("POST", url, true);
    httploc.onreadystatechange = function ()
    {
        if (httploc.readyState == 4)
        {
            if (httploc.status == 200)
            {
                var response = httploc.responseText;
                document.getElementById('monthdaysId').innerHTML = '';
                document.getElementById('monthdaysId').innerHTML = response;
            }
        }
    };
    httploc.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    httploc.setRequestHeader("Content-length", getstr.length);
    httploc.setRequestHeader("Connection", "close");
    httploc.send(getstr);
    document.getElementById('monthdaysId').innerHTML = "<div><img src='../assets/images/loading.gif' align='absmiddle'/>Please wait. Loading...</div>";
}

function showhidescheduleonload()
{
    setweekdays(0);
    setmonthdays(0);
    if(document.billingcycleForm.repeatId.value == "1")
    {
        document.getElementById('weekdaysId').style.display = "block";
        document.getElementById('monthdaysId').style.display = "none";
    }
    else if(document.billingcycleForm.repeatId.value == "2")
    {
        document.getElementById('weekdaysId').style.display = "none";
        document.getElementById('monthdaysId').style.display = "block";
    }
    else
    {
        document.getElementById('monthdaysId').style.display = "none";
        document.getElementById('weekdaysId').style.display = "none";
    }
}
