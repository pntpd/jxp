function resetFilter()
{
    document.forms[0].search.value = "";
    searchFormAjax('s','-1');
}

function showDetail(id)
{
    document.cityForm.doView.value="yes";
    document.cityForm.doModify.value="no";
    document.cityForm.doAdd.value="no";
    document.cityForm.cityId.value=id;
    document.cityForm.action="../city/CityAction.do";
    document.cityForm.submit();
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
        var url = "../ajax/city/getinfo.jsp";
        var httploc = getHTTPObject();
        var getstr = "";
        var next_value = escape(document.cityForm.nextValue.value);
        var search_value = escape(document.cityForm.search.value);
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
    if(document.cityForm.doView)
        document.cityForm.doView.value="no";
    if(document.cityForm.doCancel)
        document.cityForm.doCancel.value="yes";  
    if(document.cityForm.doSave)
        document.cityForm.doSave.value="no";
    document.cityForm.action="../city/CityAction.do";
    document.cityForm.submit();
}

function sortForm(colid, updown)
{
    for(i = 1; i <= 2; i++)
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
    var url_sort = "../ajax/city/sort.jsp";
    var getstr = "";
    var nextValue = 0;
    if(document.cityForm.nextValue)
        nextValue = document.cityForm.nextValue.value;
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
    document.cityForm.doModify.value="no";
    document.cityForm.doView.value="no";
    document.cityForm.doAdd.value="yes";
    document.cityForm.action="../city/CityAction.do";
    document.cityForm.submit();
}

function modifyForm(id)
{
    document.cityForm.doModify.value="yes";
    document.cityForm.doView.value="no";
    document.cityForm.doAdd.value="no";
    document.cityForm.cityId.value=id;
    document.cityForm.action="../city/CityAction.do";
    document.cityForm.submit();
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
    if (checkCity())
    {
        document.getElementById('submitdiv').innerHTML = "<img src='../assets/images/loading.gif' align='absmiddle' />";
        document.cityForm.doSave.value="yes";
        document.cityForm.doCancel.value="no";
        document.cityForm.action="../city/CityAction.do";
        document.cityForm.submit();
    }
}

function checkCity()
{
    if(document.cityForm.countryId.value == "-1")
    {
     Swal.fire({
        title: "Please select country.",
        didClose:() => {
          document.cityForm.countryId.focus();
        }
        }) 
        return false;
    }
    if (trim(document.cityForm.cityName.value) == "")
    {
        Swal.fire({
        title: "Please enter name.",
        didClose:() => {
          document.cityForm.cityName.focus();
        }
        }) 
        return false;
    }
    if (validname(document.cityForm.cityName) == false)
    {
        return false;
    }
   
    return true;
}

function resetForm()
{
    document.cityForm.reset();
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
        var url = "../ajax/city/getinfo.jsp";
        var getstr = "";
        var httploc = getHTTPObject();
        var next_value = escape(document.cityForm.nextValue.value);
        var next_del = "-1";
        if(document.cityForm.nextDel)
            next_del = escape(document.cityForm.nextDel.value);
        var search_value = escape(document.cityForm.search.value);
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
    document.cityForm.action = "../city/CityExportAction.do";
    document.cityForm.submit();
}