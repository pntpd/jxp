function resetFilter()
{
    document.forms[0].search.value = "";
    searchFormAjax('s','-1');
}

function showDetail(id)
{
    document.bankAccountTypeForm.doView.value="yes";
    document.bankAccountTypeForm.doModify.value="no";
    document.bankAccountTypeForm.doAdd.value="no";
    document.bankAccountTypeForm.bankAccountTypeId.value=id;
    document.bankAccountTypeForm.action="../bankaccounttype/BankAccountTypeAction.do";
    document.bankAccountTypeForm.submit();
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
        var url = "../ajax/bankaccounttype/getinfo.jsp";
        var httploc = getHTTPObject();
        var getstr = "";
        var next_value = escape(document.bankAccountTypeForm.nextValue.value);
        var search_value = escape(document.bankAccountTypeForm.search.value);
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
    if(document.bankAccountTypeForm.doView)
        document.bankAccountTypeForm.doView.value="no";
    if(document.bankAccountTypeForm.doCancel)
        document.bankAccountTypeForm.doCancel.value="yes";  
    if(document.bankAccountTypeForm.doSave)
        document.bankAccountTypeForm.doSave.value="no";
    document.bankAccountTypeForm.action="../bankaccounttype/BankAccountTypeAction.do";
    document.bankAccountTypeForm.submit();
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
    var url_sort = "../ajax/bankaccounttype/sort.jsp";
    var getstr = "";
    var nextValue = 0;
    if(document.bankAccountTypeForm.nextValue)
        nextValue = document.bankAccountTypeForm.nextValue.value;
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
    document.bankAccountTypeForm.doModify.value="no";
    document.bankAccountTypeForm.doView.value="no";
    document.bankAccountTypeForm.doAdd.value="yes";
    document.bankAccountTypeForm.action="../bankaccounttype/BankAccountTypeAction.do";
    document.bankAccountTypeForm.submit();
}

function modifyForm(id)
{
    document.bankAccountTypeForm.doModify.value="yes";
    document.bankAccountTypeForm.doView.value="no";
    document.bankAccountTypeForm.doAdd.value="no";
    document.bankAccountTypeForm.bankAccountTypeId.value=id;
    document.bankAccountTypeForm.action="../bankaccounttype/BankAccountTypeAction.do";
    document.bankAccountTypeForm.submit();
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
    if (checkBankAccountType())
    {
        document.getElementById('submitdiv').innerHTML = "<img src='../assets/images/loading.gif' align='absmiddle' />";
        document.bankAccountTypeForm.doSave.value="yes";
        document.bankAccountTypeForm.doCancel.value="no";
        document.bankAccountTypeForm.action="../bankaccounttype/BankAccountTypeAction.do";
        document.bankAccountTypeForm.submit();
    }
}

function checkBankAccountType()
{
    if (trim(document.bankAccountTypeForm.name.value) == "")
    {
         Swal.fire({
        title: "Please enter name.",
        didClose:() => {
          document.bankAccountTypeForm.name.focus();
        }
        }) 
        return false;
    }
    if (validname(document.bankAccountTypeForm.name) == false)
    {
        return false;
    }
    return true;
}

function resetForm()
{
    document.bankAccountTypeForm.reset();
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
        var url = "../ajax/bankaccounttype/getinfo.jsp";
        var getstr = "";
        var httploc = getHTTPObject();
        var next_value = escape(document.bankAccountTypeForm.nextValue.value);
        var next_del = "-1";
        if(document.bankAccountTypeForm.nextDel)
            next_del = escape(document.bankAccountTypeForm.nextDel.value);
        var search_value = escape(document.bankAccountTypeForm.search.value);
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
    document.bankAccountTypeForm.action = "../bankaccounttype/BankAccountTypeExportAction.do";
    document.bankAccountTypeForm.submit();
}