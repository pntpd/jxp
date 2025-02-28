function resetFilter()
{
    document.documentexpiryForm.documentId.value = "-1";
    document.documentexpiryForm.type.value = "-1";
    document.documentexpiryForm.clientIdIndex.value = "-1";
    document.documentexpiryForm.assetIdIndex.value = "-1";    
    document.documentexpiryForm.dropdownId.value =  "2";
    document.documentexpiryForm.search.value =  "";
    showhide();
    searchFormAjax('s', '-1');
}

function searchFormAjax(v, v1)
{
    var url = "../ajax/documentexpiry/getinfo.jsp";
    var httploc = getHTTPObject();
    var getstr = "";    
    var documentId = (document.documentexpiryForm.documentId.value);
    var assetIdIndex = (document.documentexpiryForm.assetIdIndex.value);
    var clientIdIndex = (document.documentexpiryForm.clientIdIndex.value);
    var type = document.documentexpiryForm.type.value;
    var exp = document.documentexpiryForm.exp.value;
    var dropdownId = document.documentexpiryForm.dropdownId.value;
    var coursenameId = document.documentexpiryForm.coursenameId.value;    
    var healthId = document.documentexpiryForm.healthId.value;    
    var search = document.documentexpiryForm.search.value;    
    getstr += "documentId=" + documentId;
    getstr += "&clientIdIndex=" + clientIdIndex;
    getstr += "&assetIdIndex=" + assetIdIndex;
    getstr += "&type=" + type;
    getstr += "&exp=" + exp;
    getstr += "&dropdownId=" + dropdownId;
    getstr += "&coursenameId=" + coursenameId;
    getstr += "&healthId=" + healthId;
    getstr += "&search=" + search;
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

function sortForm(colid, updown)
{
    for(i = 1; i <= 6; i++)
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
    var url_sort = "../ajax/documentexpiry/sort.jsp";
    var getstr = "";
    getstr += "col="+colid;
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

function exporttoexcel()
{    
    document.documentexpiryForm.action = "../documentexpiry/DocumentexpiryExportAction.do";
    document.documentexpiryForm.submit();
}

function exportAlert()
{    
    document.documentexpiryForm.action = "../documentexpiry/DocumentexpiryAlertExport.do";
    document.documentexpiryForm.submit();
}

function sendmail(govdocId)
{
    Swal.fire({
        title: '<b>Send reminder to selected candidate(s)?</b>',
        confirmButtonColor: '#3085d6',
        cancelButtonColor: '#d33',
        confirmButtonText: 'Confirm',
        showCloseButton: true,
        allowOutsideClick: false,
        allowEscapeKey: false
    }).then((result) => {
        if (result.isConfirmed)
        {
            document.documentexpiryForm.govdocId.value = govdocId;
            document.documentexpiryForm.doRemind.value = "yes";
            document.documentexpiryForm.doRemindAll.value = "no";
            document.documentexpiryForm.action = "../documentexpiry/DocumentexpiryAction.do";
            document.documentexpiryForm.submit();
        } 
        else
        {
        }
    })   
}

function setAssetDDL()
{
    var url = "../ajax/documentexpiry/getasset.jsp";
    document.getElementById("assetIdIndex").value = '-1';
    var httploc = getHTTPObject();
    var getstr = "clientIdIndex=" + document.documentexpiryForm.clientIdIndex.value + "&from=asset";
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

function setall()
{
    var ct = 0;
    if(document.getElementById("govdoccball").checked == true)
    {
        if(document.documentexpiryForm.govdoccb)
        {
            if(document.documentexpiryForm.govdoccb.length)
            {
                for(var i = 0; i < document.documentexpiryForm.govdoccb.length; i++)
                {
                    document.documentexpiryForm.govdoccb[i].checked = true;
                    ct++;
                }
            }
            else
            {
                document.documentexpiryForm.govdoccb.checked = true;
                ct++;
            }
        }
    }
    else
    {
        if(document.documentexpiryForm.govdoccb)
        {
            if(document.documentexpiryForm.govdoccb.length)
            {
                for(var i = 0; i < document.documentexpiryForm.govdoccb.length; i++)
                    document.documentexpiryForm.govdoccb[i].checked = false;
            }
            else
            {
                document.documentexpiryForm.govdoccb.checked = false;
            }
        }
    }
    if(document.getElementById("assignhref"))
    {
        if(Number(ct) > 0)
        {
            document.getElementById("assignhref").href="javascript: remindall();";
            document.getElementById("assignhref").style.display = "";
        }
        else
        {
            document.getElementById("assignhref").href="javascript:;";
            document.getElementById("assignhref").style.display = "none";
        }
    }
}

function setcb()
{    
    var ct = 0;
    if(document.documentexpiryForm.govdoccb)
    {
        if(document.documentexpiryForm.govdoccb.length)
        {
            for(var i = 0; i < document.documentexpiryForm.govdoccb.length; i++)
            {
                if(document.documentexpiryForm.govdoccb[i].checked == true)
                    ct++;
            }
        }
        else
        {
            if(document.documentexpiryForm.govdoccb.checked == true)
                ct++;
        }
    }
    if(document.getElementById("assignhref"))
    {
        if(Number(ct) > 0)
        {
            document.getElementById("assignhref").href="javascript: remindall();";
            document.getElementById("assignhref").style.display = "";
        }
        else
        {
            document.getElementById("assignhref").href="javascript:;";
            document.getElementById("assignhref").style.display = "none";
        }
    }
}

function remindall()
{
    Swal.fire({
        title: '<b>Send reminder to selected candidate(s)?</b>',
        confirmButtonColor: '#3085d6',
        cancelButtonColor: '#d33',
        confirmButtonText: 'Confirm',
        showCloseButton: true,
        allowOutsideClick: false,
        allowEscapeKey: false
    }).then((result) => {
        if (result.isConfirmed)
        {
            document.documentexpiryForm.doRemindAll.value = "yes";
            document.documentexpiryForm.doRemind.value = "no";
            document.getElementById('remindall_div').innerHTML = "<img src='../assets/images/loading.gif' align='absmiddle'/>";
            document.documentexpiryForm.action = "../documentexpiry/DocumentexpiryAction.do";
            document.documentexpiryForm.submit();
        } 
        else
        {
        }
    })   
}

function showhide()
{
    if(document.documentexpiryForm.dropdownId.value == "1")
    {
        document.getElementById("coursediv").style.display = "";
        document.getElementById("healthdiv").style.display = "none";
        document.getElementById("documentdiv").style.display = "none";
        searchFormAjax('s','-1');
    }
    else if(document.documentexpiryForm.dropdownId.value == "2")
    {
        document.getElementById("coursediv").style.display = "none";
        document.getElementById("healthdiv").style.display = "none";
        document.getElementById("documentdiv").style.display = "";
        searchFormAjax('s','-1');
    }
    else if(document.documentexpiryForm.dropdownId.value == "3")
    {
        document.getElementById("coursediv").style.display = "none";
        document.getElementById("healthdiv").style.display = "";
        document.getElementById("documentdiv").style.display = "none";
        searchFormAjax('s','-1');
    }
}

function viewAlerts()
{ 
    document.documentexpiryForm.viewAlerts.value="yes";
    document.documentexpiryForm.action="../documentexpiry/DocumentexpiryAction.do";
    document.documentexpiryForm.submit();
}

function viewExpiry()
{ 
    document.documentexpiryForm.viewExpiry.value="yes";
    document.documentexpiryForm.action="../documentexpiry/DocumentexpiryAction.do";
    document.documentexpiryForm.submit();
}

function setAssetAlert()
{
    var url = "../ajax/documentexpiry/getassetAlert.jsp";
    document.getElementById("assetIdAlert").value = '-1';
    var httploc = getHTTPObject();
    var getstr = "clientIdIndex=" + document.documentexpiryForm.clientIdAlert.value + "&from=asset";
    httploc.open("POST", url, true);
    httploc.onreadystatechange = function ()
    {
        if (httploc.readyState == 4)
        {
            if (httploc.status == 200)
            {
                var response = httploc.responseText;
                document.getElementById("assetIdAlert").innerHTML = '';
                document.getElementById("assetIdAlert").innerHTML = response;
            }
        }
    };
    httploc.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    httploc.send(getstr);
    searchFormAjax2('s', '-1');
}

function searchFormAjax2(v, v1)
{
    var url = "../ajax/documentexpiry/getinfo2.jsp";
    var httploc = getHTTPObject();
    var getstr = "";  
    
    var search = document.documentexpiryForm.search2.value;   
    var fromDate = document.documentexpiryForm.fromDate.value;   
    var toDate = document.documentexpiryForm.toDate.value;  
    
    var clientIdAlert = (document.documentexpiryForm.clientIdAlert.value);
    var assetIdAlert = (document.documentexpiryForm.assetIdAlert.value);
    var moduleId = (document.documentexpiryForm.moduleId.value);
    
    getstr += "moduleId=" + moduleId;
    getstr += "&clientIdAlert=" + clientIdAlert;
    getstr += "&assetIdAlert=" + assetIdAlert;
    getstr += "&fromDate=" + fromDate;
    getstr += "&toDate=" + toDate;
    getstr += "&search=" + search;
    getstr += "&doDirect="+v1;
    httploc.open("POST", url, true);
    
    httploc.onreadystatechange = function()
    {
        if (httploc.readyState == 4)
        {
            if(httploc.status == 200)
            {
                var response = httploc.responseText;
                document.getElementById('ajax_cat2').innerHTML = '';
                document.getElementById('ajax_cat2').innerHTML = response;
            }
        }
    };
    httploc.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    httploc.setRequestHeader("Content-length", getstr.length);
    httploc.setRequestHeader("Connection", "close");
    httploc.send(getstr);
    document.getElementById('ajax_cat2').innerHTML = "<div><img src='../assets/images/loading.gif' align='absmiddle'/>Please wait. Loading...</div>";
}

function resetFilter2()
{
    document.documentexpiryForm.search2.value =  "";
    document.documentexpiryForm.fromDate.value = "";
    document.documentexpiryForm.toDate.value =  "";
    document.documentexpiryForm.clientIdAlert.value = "-1";
    document.documentexpiryForm.assetIdAlert.value = "-1";    
    document.documentexpiryForm.moduleId.value = "-1";
    setAssetAlert();
    searchFormAjax2('s', '-1');
}

function goToCrew(id)//1
{
    document.forms[0].target = "_blank";
    document.forms[0].candidateId.value = id;
    document.forms[0].doView.value = "yes";
    document.forms[0].action = "../candidate/CandidateAction.do";
    document.forms[0].submit();
}

function showJobpostDetail(id)//3
{
    document.forms[0].target = "_blank";
    document.forms[0].jobpostId.value = id;
    document.forms[0].doView.value = "yes";
    document.forms[0].action = "../jobpost/JobPostAction.do";
    document.forms[0].submit();
}

function goToClientSelectionById(id)//4
{
    document.forms[0].target = "_blank";
    document.forms[0].jobpostId.value = id;
    document.forms[0].doView.value = "yes";
    document.forms[0].action = "../clientselection/ClientselectionAction.do";
    document.forms[0].submit();
}

function goToClientSelectionByAssetId(id)//5
{
    document.forms[0].target = "_blank";
    document.forms[0].jobpostId.value = id;
    document.forms[0].doView.value = "yes";
    document.forms[0].action = "../clientselection/ClientselectionAction.do";
    document.forms[0].submit();
}

function goToCrewrotation(clientId, clientassetId)//6
{
    document.forms[0].target = "_blank";
    document.forms[0].clientId.value = clientId;
    document.forms[0].clientassetId.value = clientassetId;
    document.forms[0].doView.value = "yes";
    document.forms[0].action = "../crewrotation/CrewrotationAction.do";
    document.forms[0].submit();
}

function sortForm2(colid, updown)
{
    for(i = 1; i <= 6; i++)
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
    var url_sort = "../ajax/documentexpiry/sort2.jsp";
    var getstr = "";
    getstr += "col="+colid;
    getstr += "&updown="+updown;
    httploc.open("POST", url_sort, true);
    httploc.onreadystatechange = function()
    {
        if (httploc.readyState == 4)
        {
            if(httploc.status == 200)
            {
                var response = httploc.responseText;
                document.getElementById('sort_id2').innerHTML = '';
                document.getElementById('sort_id2').innerHTML = response;
            }
        }
    };
    httploc.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    httploc.setRequestHeader("Content-length", getstr.length);
    httploc.setRequestHeader("Connection", "close");
    httploc.send(getstr);
    document.getElementById('sort_id2').innerHTML = "<div><img src='../assets/images/loading.gif' align='absmiddle'/>Please wait. Loading...</div>";
}

    function deleteAlert(id)
    {
        var s = "<span>Selected notification will be <b>deleted.</b></span>";
        Swal.fire({
            title: s + 'Are you sure?',
            confirmButtonColor: '#3085d6',
            cancelButtonColor: '#d33',
            confirmButtonText: 'Confirmed',
            showCloseButton: true,
            allowOutsideClick: false,
            allowEscapeKey: false
        }).then((result) => {
        if (result.isConfirmed)
        {
            document.documentexpiryForm.viewExpiry.value="no";
            document.documentexpiryForm.deleteAlert.value="yes";
            document.documentexpiryForm.notificationId.value= id;
            document.documentexpiryForm.action="../documentexpiry/DocumentexpiryAction.do";
            document.documentexpiryForm.submit();
        }
        })
    }