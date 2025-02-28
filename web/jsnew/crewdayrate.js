function resetFilter()
{
    document.forms[0].search.value = "";
    document.crewdayrateForm.clientIdIndex.value = "-1"
    document.crewdayrateForm.assetIdIndex.value = "-1"
    setAssetDDL();
    searchFormAjax('s', '-1');
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
        var url = "../ajax/crewdayrate/getinfo.jsp";
        var httploc = getHTTPObject();
        var getstr = "";
        var next_value = escape(document.crewdayrateForm.nextValue.value);
        var search_value = escape(document.crewdayrateForm.search.value);
        var assetIdIndex = escape(document.crewdayrateForm.assetIdIndex.value);
        var clientIdIndex = escape(document.crewdayrateForm.clientIdIndex.value);
        getstr += "nextValue=" + next_value;
        getstr += "&next=" + v;
        getstr += "&search=" + search_value;
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
    if (document.crewdayrateForm.doView)
        document.crewdayrateForm.doView.value = "no";
    if (document.crewdayrateForm.doSave)
        document.crewdayrateForm.doSave.value = "no";
    if (document.crewdayrateForm.doCancel)
        document.crewdayrateForm.doCancel.value = "yes";
    document.crewdayrateForm.action = "../crewdayrate/CrewdayrateAction.do";
    document.crewdayrateForm.submit();
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
    var url_sort = "../ajax/crewdayrate/sort.jsp";
    var getstr = "";
    var nextValue = 0;
    if (document.crewdayrateForm.nextValue)
        nextValue = document.crewdayrateForm.nextValue.value;
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
    document.crewdayrateForm.reset();
}

function exporttoexcel()
{
    document.crewdayrateForm.action = "../crewdayrate/CrewdayrateExportAction.do";
    document.crewdayrateForm.submit();
}

function exportDetails()
{
    document.crewdayrateForm.action = "../crewdayrate/CrewdayrateViewExport.do";
    document.crewdayrateForm.submit();
}

function setAssetDDL()
{
    var url = "../ajax/crewdayrate/getasset.jsp";
    document.getElementById("assetIdIndex").value = '-1';
    var httploc = getHTTPObject();
    var getstr = "clientIdIndex=" + document.crewdayrateForm.clientIdIndex.value + "&from=asset";
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

function submitForm()
{
    if(checkrate())
    {
        if (document.crewdayrateForm.doView)
            document.crewdayrateForm.doView.value = "no";
        if (document.crewdayrateForm.doCancel)
            document.crewdayrateForm.doCancel.value = "no";
        document.getElementById('submitdiv').innerHTML = "<img src='../assets/images/loading.gif' align='absmiddle' />";
        document.crewdayrateForm.doSave.value = "yes";
        document.crewdayrateForm.action = "../crewdayrate/CrewdayrateAction.do";
        document.crewdayrateForm.submit();
    }
}


function checkrate()
{
    if (document.getElementById("fromDate").value == "")
    {
        Swal.fire({
            title: "Please select from date.",
            didClose: () => {
                document.getElementById("fromDate").focus();
            }
        })
        return false;
    }
    if (document.getElementById("toDate").value != "")
    {
        if (comparisionTest(document.getElementById("fromDate").value, document.getElementById("toDate").value) == false)
        {
            Swal.fire({
                title: "Please check end date.",
                didClose: () => {
                    document.getElementById("toDate").value = "";
                }
            })
            return false;
        }
    }
    if (Number(document.getElementById("rate1").value) <= 0)
    {
        Swal.fire({
            title: "Please enter day rate.",
            didClose: () => {
                document.getElementById("rate1").focus();
            }
        })
        return false;
    }
    return true;
}

function showDetail(assetId)
{    
    document.forms[0].target = "_self";
    document.crewdayrateForm.doChange.value = "no";
    document.crewdayrateForm.doView.value = "yes";
    document.crewdayrateForm.assetId.value = assetId;
    document.crewdayrateForm.action = "../crewdayrate/CrewdayrateAction.do";
    document.crewdayrateForm.submit();
}

function changestatus(assetId, status, id)
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
        document.forms[0].target = "_self";
        document.crewdayrateForm.doView.value = "no";
        document.crewdayrateForm.doChange.value = "yes";
        document.crewdayrateForm.assetId.value = assetId;
        document.crewdayrateForm.status.value = status;
        document.crewdayrateForm.action = "../crewdayrate/CrewdayrateAction.do";
        document.crewdayrateForm.submit();
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

function modifyClientAsset()
{
    document.forms[0].target = "_blank";
    document.forms[0].doModifyAsset.value = "yes";
    document.forms[0].action = "../client/ClientAction.do";
    document.forms[0].submit();
}

function getHistory(candidateId, positionId)
{
    var url = "../ajax/crewdayrate/getHistory.jsp";
    var httploc = getHTTPObject();
    var getstr = "";
    getstr = "candidateId=" + candidateId;
    getstr += "&positionId=" + positionId;
    getstr += "&clientassetId=" + document.crewdayrateForm.assetId.value;
    httploc.open("POST", url, true);
    httploc.onreadystatechange = function ()
    {
        if (httploc.readyState == 4)
        {
            if (httploc.status == 200)
            {
                var response = httploc.responseText;
                document.getElementById("rateHistory").innerHTML = '';
                document.getElementById("rateHistory").innerHTML = response;
            }
        }
    };
    httploc.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    httploc.setRequestHeader("Content-length", getstr.length);
    httploc.setRequestHeader("Connection", "close");
    httploc.send(getstr);
}

function editModal(candidateId, positionId)
{
    var url = "../ajax/crewdayrate/editModal.jsp";
    var httploc = getHTTPObject();
    var getstr = "";
    getstr = "candidateId=" + candidateId;
    getstr += "&positionId=" + positionId;
    httploc.open("POST", url, true);
    httploc.onreadystatechange = function ()
    {
        if (httploc.readyState == 4)
        {
            if (httploc.status == 200)
            {
                var response = httploc.responseText;
                document.getElementById("edit_div").innerHTML = '';
                document.getElementById("edit_div").innerHTML = response;
            }
        }
        jQuery(document).ready(function () {
                $(".kt-selectpicker").selectpicker();
                 $(".wesl_dt").datepicker({
                        todayHighlight: !0,
                        format: "dd-M-yyyy",
                        autoclose: "true",
                        orientation: "auto"
                });
        });
    };
    httploc.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    httploc.setRequestHeader("Content-length", getstr.length);
    httploc.setRequestHeader("Connection", "close");
    httploc.send(getstr);
}


function updateRate( positionId,srno)
{
    if(document.getElementById("prate_"+srno+"_"+positionId).value <= 0 )
    {
         Swal.fire({
            title: "Please enter position rate.",
            didClose: () => {
               document.getElementById("prate_"+srno+"_"+positionId).focus();
            }
        })
    }else
    {
        var url = "../ajax/crewdayrate/updateModal.jsp";
        var httploc = getHTTPObject();
        var getstr = "";
        getstr = "positionId=" + positionId;
        getstr += "&prate=" + document.getElementById("prate_"+srno+"_"+positionId).value;
        getstr += "&clientassetId=" + document.crewdayrateForm.assetId.value;
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
                    var v1 = arr[0];
                    var v2 = trim(arr[1]);
                    if(v2 > 0)
                    {
                        Swal.fire("Rate Updated Sucessfully."); 
                    }
                }
            }
        };
        httploc.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
        httploc.setRequestHeader("Content-length", getstr.length);
        httploc.setRequestHeader("Connection", "close");
        httploc.send(getstr);
    }
}

function searchDetails()
{
    document.forms[0].target = "_self";
    document.crewdayrateForm.doView2.value = "yes";
    document.crewdayrateForm.doView.value = "no";
    document.crewdayrateForm.action = "../crewdayrate/CrewdayrateAction.do";
    document.crewdayrateForm.submit();
}