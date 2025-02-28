function resetFilter()
{
    document.forms[0].search.value = "";
    document.forms[0].assettypeIdIndex.value = "-1";
    document.forms[0].departmentIdIndex.value = "-1";
    searchFormAjax('s','-1');
    setDept('1','1');
}

function showDetail(id)
{
    document.competencyassessmentsForm.doView.value="yes";
    document.competencyassessmentsForm.doModify.value="no";
    document.competencyassessmentsForm.doAdd.value="no";
    document.competencyassessmentsForm.competencyassessmentsId.value=id;
    document.competencyassessmentsForm.action="../competencyassessments/CompetencyassessmentsAction.do";
    document.competencyassessmentsForm.submit();
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
        var url = "../ajax/competencyassessments/getinfo.jsp";
        var httploc = getHTTPObject();
        var getstr = "";
        var next_value = escape(document.competencyassessmentsForm.nextValue.value);
        var search_value = escape(document.competencyassessmentsForm.search.value);
        getstr += "nextValue="+next_value;
        getstr += "&next="+v;
        getstr += "&search="+search_value;
        getstr += "&departmentIdIndex="+document.competencyassessmentsForm.departmentIdIndex.value;
        getstr += "&assettypeIdIndex="+document.competencyassessmentsForm.assettypeIdIndex.value;
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
    if(document.competencyassessmentsForm.doView)
        document.competencyassessmentsForm.doView.value="no";
    if(document.competencyassessmentsForm.doCancel)
        document.competencyassessmentsForm.doCancel.value="yes";  
    if(document.competencyassessmentsForm.doSave)
        document.competencyassessmentsForm.doSave.value="no";
    document.competencyassessmentsForm.action="../competencyassessments/CompetencyassessmentsAction.do";
    document.competencyassessmentsForm.submit();
}

function sortForm(colid, updown)
{
    for(i = 1; i <= 3; i++)
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
    var url_sort = "../ajax/competencyassessments/sort.jsp";
    var getstr = "";
    var nextValue = 0;
    if(document.competencyassessmentsForm.nextValue)
        nextValue = document.competencyassessmentsForm.nextValue.value;
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
    document.competencyassessmentsForm.doModify.value="no";
    document.competencyassessmentsForm.doView.value="no";
    document.competencyassessmentsForm.doAdd.value="yes";
    document.competencyassessmentsForm.action="../competencyassessments/CompetencyassessmentsAction.do";
    document.competencyassessmentsForm.submit();
}

function modifyForm(id)
{
    document.competencyassessmentsForm.doModify.value="yes";
    document.competencyassessmentsForm.doView.value="no";
    document.competencyassessmentsForm.doAdd.value="no";
    document.competencyassessmentsForm.competencyassessmentsId.value=id;
    document.competencyassessmentsForm.action="../competencyassessments/CompetencyassessmentsAction.do";
    document.competencyassessmentsForm.submit();
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
    if (checkCompetencyassessments())
    {
        document.getElementById('submitdiv').innerHTML = "<img src='../assets/images/loading.gif' align='absmiddle' />";
        document.competencyassessmentsForm.doSave.value="yes";
        document.competencyassessmentsForm.doCancel.value="no";
        document.competencyassessmentsForm.action="../competencyassessments/CompetencyassessmentsAction.do";
        document.competencyassessmentsForm.submit();
    }
}

function checkCompetencyassessments()
{
    if(document.competencyassessmentsForm.assettypeId.value == "-1")
    {
     Swal.fire({
        title: "Please select asset type.",
        didClose:() => {
          document.competencyassessmentsForm.assettypeId.focus();
        }
        })
        return false;
    }
    if(document.competencyassessmentsForm.departmentId.value == "-1")
    {
     Swal.fire({
        title: "Please select department.",
        didClose:() => {
          document.competencyassessmentsForm.departmentId.focus();
        }
        })
        return false;
    }
    if (trim(document.competencyassessmentsForm.name.value) == "")
    {
        Swal.fire({
        title: "Please enter title.",
        didClose:() => {
          document.competencyassessmentsForm.name.focus();
        }
        }) 
        return false;
    }
    if (validdesc(document.competencyassessmentsForm.name) == false)
    {
        return false;
    }
    if (trim(document.competencyassessmentsForm.description.value) != "")
    {       
        if (validdesc(document.competencyassessmentsForm.description) == false)
        {
            document.competencyassessmentsForm.description.focus();
            return false;
        }
    }
    return true;
}

function resetForm()
{
    document.competencyassessmentsForm.reset();
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
        var url = "../ajax/competencyassessments/getinfo.jsp";
        var getstr = "";
        var httploc = getHTTPObject();
        var next_value = escape(document.competencyassessmentsForm.nextValue.value);
        var next_del = "-1";
        if(document.competencyassessmentsForm.nextDel)
            next_del = escape(document.competencyassessmentsForm.nextDel.value);
        var search_value = escape(document.competencyassessmentsForm.search.value);
        getstr += "nextValue="+next_value;
        getstr += "&nextDel="+next_del;
        getstr += "&search="+search_value;
        getstr += "&status="+status;
        getstr += "&departmentIdIndex="+document.competencyassessmentsForm.departmentIdIndex.value;
        getstr += "&assettypeIdIndex="+document.competencyassessmentsForm.assettypeIdIndex.value;
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
    document.competencyassessmentsForm.action = "../competencyassessments/CompetencyassessmentsExportAction.do";
    document.competencyassessmentsForm.submit();
}

function setDept(tp, type)
{
    var httploc = getHTTPObject();
    var url_sort = "../ajax/competencyassessments/getdept.jsp";
    var getstr = "";
    var assettypeId = 0;
    var assettypeIdIndex = 0;
    if(Number(tp) == 1){
        assettypeIdIndex = document.competencyassessmentsForm.assettypeIdIndex.value;
        getstr += "assettypeId="+assettypeIdIndex;
    }
    else if(Number(tp) == 2){
        assettypeId = document.competencyassessmentsForm.assettypeId.value;
        getstr += "assettypeId="+assettypeId;
    }
    getstr += "&type="+type;
    httploc.open("POST", url_sort, true);
    httploc.onreadystatechange = function()
    {
        if (httploc.readyState == 4)
        {
            if(httploc.status == 200)
            {
                var response = httploc.responseText;
                if(Number(tp) == 1)
                {
                    document.getElementById('departmentIdIndex').innerHTML = '';
                    document.getElementById('departmentIdIndex').innerHTML = response;
                    searchFormAjax('s','-1');
                }
                else
                {
                    document.getElementById('departmentId').innerHTML = '';
                    document.getElementById('departmentId').innerHTML = response;
                }
            }
        }
    };
    httploc.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    httploc.setRequestHeader("Content-length", getstr.length);
    httploc.setRequestHeader("Connection", "close");
    httploc.send(getstr);
}