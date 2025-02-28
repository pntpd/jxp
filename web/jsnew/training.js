function resetFilter()
{
    document.forms[0].search.value = "";
    document.trainingForm.clientIndex.value = "-1";
    document.trainingForm.assetIndex.value = "-1";
    searchFormAjax('s','-1');
}

function showDetail(id)
{
    document.trainingForm.doView.value="yes";
    document.trainingForm.doModify.value="no";
    document.trainingForm.doAdd.value="no";
    document.trainingForm.trainingId.value=id;
    document.trainingForm.action="../training/TrainingAction.do";
    document.trainingForm.submit();
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

function searchFormAjax(v, v1)
{
    if ( checkSearch())
    {
        var url = "../ajax/training/getinfo.jsp";
        var httploc = getHTTPObject();
        var getstr = "";
        var next_value = escape(document.trainingForm.nextValue.value);
        var search_value = escape(document.trainingForm.search.value);
        var assetIndex = escape(document.trainingForm.assetIndex.value);
        var clientIndex = escape(document.trainingForm.clientIndex.value);
        getstr += "nextValue="+next_value;
        getstr += "&next="+v;
        getstr += "&search="+search_value;
        getstr += "&clientIndex=" + document.trainingForm.clientIndex.value;
        getstr += "&assetIndex=" + document.trainingForm.assetIndex.value;
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
}

function goback()
{
    if(document.trainingForm.doView)
        document.trainingForm.doView.value="no";
    if(document.trainingForm.doCancel)
        document.trainingForm.doCancel.value="yes";  
    if(document.trainingForm.doSave)
        document.trainingForm.doSave.value="no";
    document.trainingForm.action="../training/TrainingAction.do";
    document.trainingForm.submit();
}

function sortForm(colid, updown)
{
    for(i = 1; i <= 1; i++)
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
    var url_sort = "../ajax/training/sort.jsp";
    var getstr = "";
    var nextValue = 0;
    if(document.trainingForm.nextValue)
        nextValue = document.trainingForm.nextValue.value;
    getstr += "nextValue="+nextValue;
    getstr += "&col="+colid;
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

function addForm()
{    
    document.trainingForm.doModify.value="no";
    document.trainingForm.doView.value="no";
    document.trainingForm.doAdd.value="yes";
    document.trainingForm.action="../training/TrainingAction.do";
    document.trainingForm.submit();
}

function modifyForm(id)
{
    document.trainingForm.doModify.value="yes";
    document.trainingForm.doView.value="no";
    document.trainingForm.doAdd.value="no";
    document.trainingForm.trainingId.value=id;
    document.trainingForm.action="../training/TrainingAction.do";
    document.trainingForm.submit();
}

function submitForm()
{
    if(document.forms[0].getElementsByTagName("input"))
    {
        var inputElements = document.forms[0].getElementsByTagName("input");
        for(i = 0; i < inputElements.length; i++)
        {
            if(inputElements[i].type == "text")
            {
                inputElements[i].value = trim(inputElements[i].value);
            }
        }
    }
    if (checkTraining())
    {
        document.getElementById('submitdiv').innerHTML = "<img src='../assets/images/loading.gif' align='absmiddle' />";
        document.trainingForm.doSave.value="yes";
        document.trainingForm.doCancel.value="no";
        document.trainingForm.action="../training/TrainingAction.do";
        document.trainingForm.submit();
    }
}

function checkTraining()
{
    if(document.trainingForm.clientId.value == "-1")
    {
        Swal.fire({
        title: "Please select client.",
        didClose:() => {
          document.trainingForm.clientId.focus();
        }
        }) 
        return false;
    }
    if(document.trainingForm.assetId.value == "-1")
    {
        Swal.fire({
        title: "Please select asset.",
        didClose:() => {
          document.trainingForm.assetId.focus();
        }
        }) 
        return false;
    }
    if (trim(document.trainingForm.name.value) == "")
    {
        Swal.fire({
        title: "Please enter Priority.",
        didClose:() => {
          document.trainingForm.name.focus();
        }
        }) 
        return false;
    }
    if (validdesc(document.trainingForm.name) == false)
    {
        return false;
    }
    return true;
}

function resetForm()
{
    document.trainingForm.reset();
}

function deleteForm(userId, status, id)
{
    var s = "";
    if(eval(status) == 1)
        s = "<span>Selected item will be <b>deactivated.</b></span>";
    else
        s = "<span>Selected item will be <b>activated.</b></span>";
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
        var url = "../ajax/training/getinfo.jsp";
        var getstr = "";
        var httploc = getHTTPObject();
        var next_value = escape(document.trainingForm.nextValue.value);
        var next_del = "-1";
        if(document.trainingForm.nextDel)
            next_del = escape(document.trainingForm.nextDel.value);
        var search_value = escape(document.trainingForm.search.value);
        getstr += "nextValue="+next_value;
        getstr += "&nextDel="+next_del;
        getstr += "&search="+search_value;
        getstr += "&status="+status;
        getstr += "&deleteVal="+userId;
        httploc.open("POST", url, true);
        httploc.onreadystatechange = function()
        {
            if (httploc.readyState == 4)
            {
                if(httploc.status == 200)
                {
                    var response = httploc.responseText;
                    var arr = new Array();
                    arr = response.split('##');
                    var v1 = arr[0];
                    var v2 = trim(arr[1]);
                    document.getElementById('ajax_cat').innerHTML = '';
                    document.getElementById('ajax_cat').innerHTML = v1;
                    if(trim(v2) != "")
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

function exporttoexcel()
{    
    document.trainingForm.action = "../training/TrainingExportAction.do";
    document.trainingForm.submit();
}

function getassetDDL()
{
    var url = "../ajax/training/getassetDDL.jsp";
    var httploc = getHTTPObject();
    var getstr = "clientId=" + document.trainingForm.clientId.value;
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
    var url = "../ajax/training/getassetIndex.jsp";
    var httploc = getHTTPObject();
    var getstr = "clientIndex=" + document.trainingForm.clientIndex.value;
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