function resetFilter()
{
    document.forms[0].search.value = "";
    searchFormAjax('s','-1');
}

function showDetail(id)
{
    document.approvedbyForm.doView.value="yes";
    document.approvedbyForm.doModify.value="no";
    document.approvedbyForm.doAdd.value="no";
    document.approvedbyForm.approvedbyId.value=id;
    document.approvedbyForm.action="../approvedby/ApprovedbyAction.do";
    document.approvedbyForm.submit();
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
        var url = "../ajax/approvedby/getinfo.jsp";
        var httploc = getHTTPObject();
        var getstr = "";
        var next_value = escape(document.approvedbyForm.nextValue.value);
        var search_value = escape(document.approvedbyForm.search.value);
        getstr += "nextValue="+next_value;
        getstr += "&next="+v;
        getstr += "&search="+search_value;
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
    if(document.approvedbyForm.doView)
        document.approvedbyForm.doView.value="no";
    if(document.approvedbyForm.doCancel)
        document.approvedbyForm.doCancel.value="yes";  
    if(document.approvedbyForm.doSave)
        document.approvedbyForm.doSave.value="no";
    document.approvedbyForm.action="../approvedby/ApprovedbyAction.do";
    document.approvedbyForm.submit();
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
    var url_sort = "../ajax/approvedby/sort.jsp";
    var getstr = "";
    var nextValue = 0;
    if(document.approvedbyForm.nextValue)
        nextValue = document.approvedbyForm.nextValue.value;
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
    document.approvedbyForm.doModify.value="no";
    document.approvedbyForm.doView.value="no";
    document.approvedbyForm.doAdd.value="yes";
    document.approvedbyForm.action="../approvedby/ApprovedbyAction.do";
    document.approvedbyForm.submit();
}

function modifyForm(id)
{
    document.approvedbyForm.doModify.value="yes";
    document.approvedbyForm.doView.value="no";
    document.approvedbyForm.doAdd.value="no";
    document.approvedbyForm.approvedbyId.value=id;
    document.approvedbyForm.action="../approvedby/ApprovedbyAction.do";
    document.approvedbyForm.submit();
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
    if (checkApprovedby())
    {
        document.getElementById('submitdiv').innerHTML = "<img src='../assets/images/loading.gif' align='absmiddle' />";
        document.approvedbyForm.doSave.value="yes";
        document.approvedbyForm.doCancel.value="no";
        document.approvedbyForm.action="../approvedby/ApprovedbyAction.do";
        document.approvedbyForm.submit();
    }
}

function checkApprovedby()
{
    if (trim(document.approvedbyForm.name.value) == "")
    {
       Swal.fire({
        title: "Please enter name.",
        didClose:() => {
          document.approvedbyForm.name.focus();
        }
        }) 
        return false;
    }
    if (validname(document.approvedbyForm.name) == false)
    {
        return false;
    }
    return true;
}

function resetForm()
{
    document.approvedbyForm.reset();
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
        var url = "../ajax/approvedby/getinfo.jsp";
        var getstr = "";
        var httploc = getHTTPObject();
        var next_value = escape(document.approvedbyForm.nextValue.value);
        var next_del = "-1";
        if(document.approvedbyForm.nextDel)
            next_del = escape(document.approvedbyForm.nextDel.value);
        var search_value = escape(document.approvedbyForm.search.value);
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
    document.approvedbyForm.action = "../approvedby/ApprovedbyExportAction.do";
    document.approvedbyForm.submit();
}