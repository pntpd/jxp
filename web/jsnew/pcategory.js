function resetFilter()
{
    document.forms[0].search.value = "";
    document.forms[0].assettypeIdIndex.value = "-1";
    document.forms[0].pdeptIdIndex.value = "-1";
    setDept('1');
    searchFormAjax('s','-1');
}

function showDetail(id)
{
    document.pcategoryForm.doView.value="yes";
    document.pcategoryForm.doModify.value="no";
    document.pcategoryForm.doAdd.value="no";
    document.pcategoryForm.pcategoryId.value=id;
    document.pcategoryForm.action="../pcategory/PcategoryAction.do";
    document.pcategoryForm.submit();
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
        var url = "../ajax/pcategory/getinfo.jsp";
        var httploc = getHTTPObject();
        var getstr = "";
        var next_value = escape(document.pcategoryForm.nextValue.value);
        var search_value = escape(document.pcategoryForm.search.value);
        getstr += "nextValue="+next_value;
        getstr += "&next="+v;
        getstr += "&search="+search_value;
        getstr += "&pdeptIdIndex="+document.pcategoryForm.pdeptIdIndex.value;
        getstr += "&assettypeIdIndex="+document.pcategoryForm.assettypeIdIndex.value;
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
    if(document.pcategoryForm.doView)
        document.pcategoryForm.doView.value="no";
    if(document.pcategoryForm.doCancel)
        document.pcategoryForm.doCancel.value="yes";  
    if(document.pcategoryForm.doSave)
        document.pcategoryForm.doSave.value="no";
    if(document.pcategoryForm.doSearchQuestion)
        document.pcategoryForm.doSearchQuestion.value="no";
    if(document.pcategoryForm.doSaveQuestion)
        document.pcategoryForm.doSaveQuestion.value="no";
    document.pcategoryForm.action="../pcategory/PcategoryAction.do";
    document.pcategoryForm.submit();
}

function sortForm(colid, updown)
{
    for(i = 1; i <= 4; i++)
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
    var url_sort = "../ajax/pcategory/sort.jsp";
    var getstr = "";
    var nextValue = 0;
    if(document.pcategoryForm.nextValue)
        nextValue = document.pcategoryForm.nextValue.value;
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
    document.pcategoryForm.doModify.value="no";
    document.pcategoryForm.doView.value="no";
    document.pcategoryForm.doAdd.value="yes";
    document.pcategoryForm.action="../pcategory/PcategoryAction.do";
    document.pcategoryForm.submit();
}

function modifyForm(id)
{
    document.pcategoryForm.doModify.value="yes";
    document.pcategoryForm.doView.value="no";
    document.pcategoryForm.doAdd.value="no";
    document.pcategoryForm.pcategoryId.value=id;
    document.pcategoryForm.action="../pcategory/PcategoryAction.do";
    document.pcategoryForm.submit();
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
    if (checkPcategory())
    {
        document.getElementById('submitdiv').innerHTML = "<img src='../assets/images/loading.gif' align='absmiddle' />";
        document.pcategoryForm.doSave.value="yes";
        document.pcategoryForm.doCancel.value="no";
        document.pcategoryForm.action="../pcategory/PcategoryAction.do";
        document.pcategoryForm.submit();
    }
}

function checkPcategory()
{
    if(document.pcategoryForm.assettypeId.value == "-1")
    {
     Swal.fire({
        title: "Please select asset type.",
        didClose:() => {
          document.pcategoryForm.assettypeId.focus();
        }
        }) 
        return false;
    }
    if(document.pcategoryForm.pdeptId.value == "-1")
    {
     Swal.fire({
        title: "Please select department.",
        didClose:() => {
          document.pcategoryForm.pdeptId.focus();
        }
        }) 
        return false;
    }
    if (trim(document.pcategoryForm.name.value) == "")
    {
        Swal.fire({
        title: "Please enter category.",
        didClose:() => {
          document.pcategoryForm.name.focus();
        }
        }) 
        return false;
    }
    if (validdesc(document.pcategoryForm.name) == false)
    {
        return false;
    }   
    return true;
}

function resetForm()
{
    document.pcategoryForm.reset();
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
        var url = "../ajax/pcategory/getinfo.jsp";
        var getstr = "";
        var httploc = getHTTPObject();
        var next_value = escape(document.pcategoryForm.nextValue.value);
        var next_del = "-1";
        if(document.pcategoryForm.nextDel)
            next_del = escape(document.pcategoryForm.nextDel.value);
        var search_value = escape(document.pcategoryForm.search.value);
        getstr += "nextValue="+next_value;
        getstr += "&nextDel="+next_del;
        getstr += "&search="+search_value;
        getstr += "&status="+status;
        getstr += "&pdeptIdIndex="+document.pcategoryForm.pdeptIdIndex.value;
        getstr += "&assettypeIdIndex="+document.pcategoryForm.assettypeIdIndex.value;
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
    document.pcategoryForm.action = "../pcategory/PcategoryExportAction.do?type=1";
    document.pcategoryForm.submit();
}

function setDept(tp)
{
    var httploc = getHTTPObject();
    var url_sort = "../ajax/pcategory/getdept.jsp";
    var getstr = "";
    var assettypeIdIndex = 0;
    var assettypeId = 0;
    if(Number(tp) == 1)
        assettypeIdIndex = document.pcategoryForm.assettypeIdIndex.value;
    else
        assettypeId = document.pcategoryForm.assettypeId.value;
    if(Number(tp) == 1)
        getstr += "assettypeIdIndex="+assettypeIdIndex;
    else
        getstr += "assettypeId="+assettypeId;
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
                    document.getElementById('pdeptIdIndex').innerHTML = '';
                    document.getElementById('pdeptIdIndex').innerHTML = response;
                    searchFormAjax('s','-1');
                }
                else
                {
                    document.getElementById('pdeptId').innerHTML = '';
                    document.getElementById('pdeptId').innerHTML = response;
                }
            }
        }
    };
    httploc.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    httploc.setRequestHeader("Content-length", getstr.length);
    httploc.setRequestHeader("Connection", "close");
    httploc.send(getstr);
}

function searchq()
{
    document.pcategoryForm.doSearchQuestion.value = "yes";
    document.pcategoryForm.doSaveQuestion.value = "no";
    document.pcategoryForm.doDeleteQuestion.value = "no";
    document.pcategoryForm.action="../pcategory/PcategoryAction.do";
    document.pcategoryForm.submit();
}

function checkQuestion()
{
    if (trim(document.pcategoryForm.qname.value) == "")
    {
        Swal.fire({
        title: "Please enter question.",
        didClose:() => {
          document.pcategoryForm.qname.focus();
        }
        }) 
        return false;
    }
    if (validdesc(document.pcategoryForm.qname) == false)
    {
        return false;
    }   
    return true;
}

function savequestion()
{
    if(checkQuestion())
    {
        document.getElementById('saveid').innerHTML = "<div><img src='../assets/images/loading.gif' align='absmiddle'/></div>";
        document.pcategoryForm.doSearchQuestion.value = "no";
        document.pcategoryForm.doDeleteQuestion.value = "no";        
        document.pcategoryForm.doSaveQuestion.value = "yes";
        document.pcategoryForm.action="../pcategory/PcategoryAction.do";
        document.pcategoryForm.submit();
    }
}

function editquestion(id, name)
{
    document.pcategoryForm.pquestionId.value = id;
    document.pcategoryForm.qname.value = name;
}

function deletequestion(id)
{
    var s = "<span>The selected question will be <b>deleted.</b></span>";
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
        document.pcategoryForm.delId.value = id;
        document.pcategoryForm.doSearchQuestion.value = "no";
        document.pcategoryForm.doDeleteQuestion.value = "yes";        
        document.pcategoryForm.doSaveQuestion.value = "no";
        document.pcategoryForm.action="../pcategory/PcategoryAction.do";
        document.pcategoryForm.submit();
    }
    })
}

function addquestion(name)
{
    document.pcategoryForm.qnamesugg.value = name;
    document.pcategoryForm.pquestionId.value = "-1";
    document.pcategoryForm.doSearchQuestion.value = "no";
    document.pcategoryForm.doDeleteQuestion.value = "no";        
    document.pcategoryForm.doSaveQuestion.value = "yes";
    document.pcategoryForm.action="../pcategory/PcategoryAction.do";
    document.pcategoryForm.submit();
}

function exporttoexcelq()
{   
    document.pcategoryForm.action = "../pcategory/PcategoryExportAction.do?type=2";
    document.pcategoryForm.submit();
}