function resetFilter()
{
    document.competencyprioritiesForm.search.value = "";
    document.competencyprioritiesForm.clientIdIndex.value = "-1";
    document.competencyprioritiesForm.assetIdIndex.value = "-1";
    document.competencyprioritiesForm.departmentIdIndex.value = "-1";
    searchFormAjax('s','-1');
    setAssetDDL('1', '1');
    setList();
}

function showDetail(id)
{
    document.competencyprioritiesForm.doView.value="yes";
    document.competencyprioritiesForm.doModify.value="no";
    document.competencyprioritiesForm.doAdd.value="no";
    document.competencyprioritiesForm.competencyprioritiesId.value=id;
    document.competencyprioritiesForm.action="../competencypriorities/CompetencyprioritiesAction.do";
    document.competencyprioritiesForm.submit();
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
    if (checkSearch())
    {
        var url = "../ajax/competencypriorities/getinfo.jsp";
        var httploc = getHTTPObject();
        var getstr = "";
        var next_value = escape(document.competencyprioritiesForm.nextValue.value);
        var search_value = escape(document.competencyprioritiesForm.search.value);
        getstr += "nextValue="+next_value;
        getstr += "&next="+v;
        getstr += "&search="+search_value;
        getstr += "&departmentIdIndex="+document.competencyprioritiesForm.departmentIdIndex.value;
        getstr += "&assetIdIndex="+document.competencyprioritiesForm.assetIdIndex.value;
        getstr += "&clientIdIndex="+document.competencyprioritiesForm.clientIdIndex.value;
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
    if(document.competencyprioritiesForm.doView)
        document.competencyprioritiesForm.doView.value="no";
    if(document.competencyprioritiesForm.doCancel)
        document.competencyprioritiesForm.doCancel.value="yes";  
    if(document.competencyprioritiesForm.doSave)
        document.competencyprioritiesForm.doSave.value="no";
    document.competencyprioritiesForm.action="../competencypriorities/CompetencyprioritiesAction.do";
    document.competencyprioritiesForm.submit();
}

function sortForm(colid, updown)
{
    for(i = 1; i <= 5; i++)
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
    var url_sort = "../ajax/competencypriorities/sort.jsp";
    var getstr = "";
    var nextValue = 0;
    if(document.competencyprioritiesForm.nextValue)
        nextValue = document.competencyprioritiesForm.nextValue.value;
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
    document.competencyprioritiesForm.doModify.value="no";
    document.competencyprioritiesForm.doView.value="no";
    document.competencyprioritiesForm.doAdd.value="yes";
    document.competencyprioritiesForm.action="../competencypriorities/CompetencyprioritiesAction.do";
    document.competencyprioritiesForm.submit();
}

function modifyForm(id)
{
    document.competencyprioritiesForm.doModify.value="yes";
    document.competencyprioritiesForm.doView.value="no";
    document.competencyprioritiesForm.doAdd.value="no";
    document.competencyprioritiesForm.competencyprioritiesId.value=id;
    document.competencyprioritiesForm.action="../competencypriorities/CompetencyprioritiesAction.do";
    document.competencyprioritiesForm.submit();
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
    if (checkCompetencypriorities())
    {
        document.getElementById('submitdiv').innerHTML = "<img src='../assets/images/loading.gif' align='absmiddle' />";
        document.competencyprioritiesForm.doSave.value="yes";
        document.competencyprioritiesForm.doCancel.value="no";
        document.competencyprioritiesForm.action="../competencypriorities/CompetencyprioritiesAction.do";
        document.competencyprioritiesForm.submit();
    }
}

function checkCompetencypriorities()
{
    if (trim(document.competencyprioritiesForm.clientId.value) == "-1")
    {
        Swal.fire({
        title: "Please select client.",
        didClose:() => {
          document.competencyprioritiesForm.clientId.focus();
        }
        }) 
        return false;
    }
    if (trim(document.competencyprioritiesForm.assetId.value) == "-1")
    {
        Swal.fire({
        title: "Please select asset.",
        didClose:() => {
          document.competencyprioritiesForm.assetId.focus();
        }
        }) 
        return false;
    }
    if (trim(document.competencyprioritiesForm.deptColum.value) == "")
    {
        Swal.fire({
        title: "Please select department.",
        didClose:() => {
          document.competencyprioritiesForm.deptColum.focus();
        }
        }) 
        return false;
    }    
    if (trim(document.competencyprioritiesForm.name.value) == "")
    {
        Swal.fire({
        title: "Please enter competency priority title.",
        didClose:() => {
          document.competencyprioritiesForm.name.focus();
        }
        }) 
        return false;
    } 
    if (validdesc(document.competencyprioritiesForm.name) == false)
    {
        return false;
    }
    if (trim(document.competencyprioritiesForm.degreeId.value) == "-1")
    {
        Swal.fire({
        title: "Please select Priority degree.",
        didClose:() => {
          document.competencyprioritiesForm.degreeId.focus();
        }
        }) 
        return false;
    }
    if (trim(document.competencyprioritiesForm.description.value) != "")
    {       
        if (validdesc(document.competencyprioritiesForm.description) == false)
        {
            document.competencyprioritiesForm.description.focus();
            return false;
        }
    }
    return true;
}

function resetForm()
{
    document.competencyprioritiesForm.reset();
}

function deleteForm(userId, status, id)
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
        var url = "../ajax/competencypriorities/getinfo.jsp";
        var getstr = "";
        var httploc = getHTTPObject();
        var next_value = escape(document.competencyprioritiesForm.nextValue.value);
        var next_del = "-1";
        if(document.competencyprioritiesForm.nextDel)
            next_del = escape(document.competencyprioritiesForm.nextDel.value);
        var search_value = escape(document.competencyprioritiesForm.search.value);
        getstr += "nextValue="+next_value;
        getstr += "&nextDel="+next_del;
        getstr += "&search="+search_value;
        getstr += "&status="+status;
        getstr += "&departmentIdIndex="+document.competencyprioritiesForm.departmentIdIndex.value;
        getstr += "&assetIdIndex="+document.competencyprioritiesForm.assetIdIndex.value;
        getstr += "&clientIdIndex="+document.competencyprioritiesForm.clientIdIndex.value;
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
    document.competencyprioritiesForm.action = "../competencypriorities/CompetencyprioritiesExportAction.do";
    document.competencyprioritiesForm.submit();
}

function setAssetDDL(tp, type)
{
    var url = "../ajax/competencypriorities/getasset.jsp";
    var getstr = "";
    var clientId = 0;    
    if(Number(tp) == 1)
    {
       getstr = "clientId=" + document.competencyprioritiesForm.clientIdIndex.value ;
    }
    else
    {
       getstr = "clientId=" + document.competencyprioritiesForm.clientId.value;
    }
    getstr += "&type=" + type;
    var httploc = getHTTPObject();
    httploc.open("POST", url, true);
    httploc.onreadystatechange = function ()
    {
        if (httploc.readyState == 4)
        {
            if (httploc.status == 200)
            {
                var response = httploc.responseText;
                if(Number(tp) == 1)
                {
                    document.getElementById('assetIdIndex').innerHTML = '';
                    document.getElementById('assetIdIndex').innerHTML = response;
                    searchFormAjax('s','-1');
                }
                else
                {
                    document.getElementById('assetId').innerHTML = '';
                    document.getElementById('assetId').innerHTML = response;
                }
            }
        }
    };
    httploc.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    httploc.send(getstr);
}

function setdept()
{
    var url = "../ajax/competencypriorities/getdept.jsp";
    var getstr = "assetId=" + document.competencyprioritiesForm.assetId.value;
    getstr += "&ids="+document.competencyprioritiesForm.ids.value;
    var httploc = getHTTPObject();
    httploc.open("POST", url, true);
    httploc.onreadystatechange = function ()
    {
        if (httploc.readyState == 4)
        {
            if (httploc.status == 200)
            {
                var response = httploc.responseText;
                document.getElementById('deptdiv').innerHTML = '';
                document.getElementById('deptdiv').innerHTML = response;
                $('#deptmultiselect_dd').multiselect({
                    includeSelectAllOption: true,
                    dropUp: true,
                    nonSelectedText: 'Select Department',
                    maxHeight: 200,
                    enableFiltering: false,
                    enableCaseInsensitiveFiltering: false,
                    buttonWidth: '100%'
                });
            }
        }
    };
    httploc.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    httploc.send(getstr);
}

function setList()
{
    var url = "../ajax/competencypriorities/getdeptList.jsp";
    var getstr = "assetId=" + document.competencyprioritiesForm.assetIdIndex.value;
    var httploc = getHTTPObject();
    httploc.open("POST", url, true);
    httploc.onreadystatechange = function ()
    {
        if (httploc.readyState == 4)
        {
            if (httploc.status == 200)
            {
                var response = httploc.responseText;
                document.getElementById('departmentIdIndex').innerHTML = '';
                document.getElementById('departmentIdIndex').innerHTML = response;
                searchFormAjax('s','-1');
            }
        }
    };
    httploc.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    httploc.send(getstr);
}