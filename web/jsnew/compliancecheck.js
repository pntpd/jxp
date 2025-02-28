function resetFilter()
{
    document.compliancecheckForm.search.value = "";
    document.compliancecheckForm.clientIdIndex.value = "-1";
    document.compliancecheckForm.assetIdIndex.value = "-1";
    document.compliancecheckForm.pgvalue.value = "";
    setAssetDDL();
    searchFormAjax('s', '-1');
}

function view(candidateId, jobpostId, shortlistId, status)
{
    document.compliancecheckForm.doView.value = "yes";
    document.compliancecheckForm.candidateId.value = candidateId;
    document.compliancecheckForm.shortlistId.value = shortlistId;
    document.compliancecheckForm.jobpostId.value = jobpostId;
    document.compliancecheckForm.status.value = status;
    document.compliancecheckForm.action = "../compliancecheck/CompliancecheckAction.do";
    document.compliancecheckForm.submit();
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
        if (validdescsearch(document.forms[0].search) == false)
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
        var url = "../ajax/compliancecheck/getinfo.jsp";
        var httploc = getHTTPObject();
        var getstr = "";
        var next_value = escape(document.compliancecheckForm.nextValue.value);
        var search_value = escape(document.compliancecheckForm.search.value);
        var pgvalue = escape(document.compliancecheckForm.pgvalue.value);
        var assetIdIndex = escape(document.compliancecheckForm.assetIdIndex.value);
        var clientIdIndex = escape(document.compliancecheckForm.clientIdIndex.value);
        getstr += "nextValue=" + next_value;
        getstr += "&next=" + v;
        getstr += "&search=" + search_value;
        getstr += "&pgvalue=" + pgvalue;
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
    if (document.compliancecheckForm.doView)
        document.compliancecheckForm.doView.value = "no";
    if (document.compliancecheckForm.doCancel)
        document.compliancecheckForm.doCancel.value = "yes";
    if (document.compliancecheckForm.doSave)
        document.compliancecheckForm.doSave.value = "no";
    document.compliancecheckForm.action = "../compliancecheck/CompliancecheckAction.do";
    document.compliancecheckForm.submit();
}

function sortForm(colid, updown)
{
    for (i = 1; i <= 5; i++)
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
    var url_sort = "../ajax/compliancecheck/sort.jsp";
    var getstr = "";
    var nextValue = 0;
    if (document.compliancecheckForm.nextValue)
        nextValue = document.compliancecheckForm.nextValue.value;
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


function resetForm()
{
    document.compliancecheckForm.reset();
}

function setPosition()
{
    var url = "../ajax/candidate/positions.jsp";
    var httploc = getHTTPObject();
    var getstr = "";
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
            }
        }
    };
    httploc.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    httploc.setRequestHeader("Content-length", getstr.length);
    httploc.setRequestHeader("Connection", "close");
    httploc.send(getstr);
}

function exporttoexcel()
{
    document.compliancecheckForm.action = "../compliancecheck/CompliancecheckExportAction.do";
    document.compliancecheckForm.submit();
}

function setStatuscb(tp)
{
    if (eval(tp) == 1)
    {
        if (document.getElementById("stcb1").checked)
        {
            document.getElementById("stcb2").checked = true;
            document.getElementById("stcb2").disabled = true;
            document.forms[0].rbchecked.value = "2";
        } else
        {
            document.getElementById("stcb2").checked = false;
            document.getElementById("stcb2").disabled = false;
            document.forms[0].rbchecked.value = "0";
        }
    } else
    {
        if (document.getElementById("stcb2").checked)
        {
            document.getElementById("stcb1").checked = false;
            document.forms[0].rbchecked.value = "1";
        } else
        {
            document.getElementById("stcb1").disabled = false;
            document.forms[0].rbchecked.value = "0";
        }
    }
}

function getModel(shortlistccId, checkpointId)
{
    var url = "../ajax/compliancecheck/getmodeldetails.jsp";
    var httploc = getHTTPObject();
    var getstr = "";
    getstr += "shortlistId=" + document.compliancecheckForm.shortlistId.value;
    getstr += "&shortlistccId=" + shortlistccId;
    getstr += "&checkpointId=" + checkpointId;
    getstr += "&candidateId=" + document.compliancecheckForm.candidateId.value;
    getstr += "&status=" + document.compliancecheckForm.status.value;
    httploc.open("POST", url, true);
    httploc.onreadystatechange = function ()
    {
        if (httploc.readyState == 4)
        {
            if (httploc.status == 200)
            {
                var response = httploc.responseText;
                var arr = new Array();
                arr = response.split('#@#');
                v1 = arr[0];
                v2 = arr[1];
                document.getElementById('dverify').innerHTML = '';
                document.getElementById('dverify').innerHTML = trim(v1);
            }
        }
    };
    httploc.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    httploc.setRequestHeader("Content-length", getstr.length);
    httploc.setRequestHeader("Connection", "close");
    httploc.send(getstr);
    document.getElementById('dverify').innerHTML = "<div><img src='../assets/images/loading.gif' align='absmiddle'/>Please wait. Loading...</div>";
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
    if (checkCompliance())
    {
        document.getElementById('submitdiv').innerHTML = "<img src='../assets/images/loading.gif' align='absmiddle' />";
        document.compliancecheckForm.doSave.value = "yes";
        document.compliancecheckForm.doView.value = "no";
        document.compliancecheckForm.action = "../compliancecheck/CompliancecheckAction.do";
        document.compliancecheckForm.submit();
    }
}

function checkCompliance()
{
    if (Number(document.compliancecheckForm.rbchecked.value) <= 0)
    {
        Swal.fire({
            title: "Please select compliance check status.",
            didClose: () => {
                document.compliancecheckForm.rbchecked.focus();
            }
        })
        return false;
    }
    if (trim(document.compliancecheckForm.remarks.value) != "")
    {
        if (validdesc(document.compliancecheckForm.remarks) == false)
        {
            return false;
        }
    }
    return true;
}

function setAssetDDL()
{
    var url = "../ajax/compliancecheck/getasset.jsp";
    var httploc = getHTTPObject();
    var getstr = "clientIdIndex=" + document.compliancecheckForm.clientIdIndex.value + "&from=asset";
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
                setPositionDDL();
            }
        }
    };
    httploc.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    httploc.send(getstr);
    searchFormAjax('s', '-1');
}

function setPositionDDL()
{
    var url = "../ajax/compliancecheck/getasset.jsp";
    var httploc = getHTTPObject();
    var getstr = "assetIdIndex=" + document.compliancecheckForm.assetIdIndex.value + "&from=position";
    httploc.open("POST", url, true);
    httploc.onreadystatechange = function ()
    {
        if (httploc.readyState == 4)
        {
            if (httploc.status == 200)
            {
                var response = httploc.responseText;
                document.getElementById("pgvalue").innerHTML = '';
                document.getElementById("pgvalue").innerHTML = response;
            }
        }
    };
    httploc.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    httploc.send(getstr);
    searchFormAjax('s', '-1');
}


