function resetFilter()
{
    document.forms[0].search.value = "";
    document.forms[0].assettypeIdIndex.value = "-1";
    document.forms[0].pdeptIdIndex.value = "-1";
    searchFormAjax('s','-1');
    setDept('1');
}

function showDetail(id)
{
    document.pcodeForm.doView.value="yes";
    document.pcodeForm.doModify.value="no";
    document.pcodeForm.doAdd.value="no";
    document.pcodeForm.pcodeId.value=id;
    document.pcodeForm.action="../pcode/PcodeAction.do";
    document.pcodeForm.submit();
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
        var url = "../ajax/pcode/getinfo.jsp";
        var httploc = getHTTPObject();
        var getstr = "";
        var next_value = escape(document.pcodeForm.nextValue.value);
        var search_value = escape(document.pcodeForm.search.value);
        getstr += "nextValue="+next_value;
        getstr += "&next="+v;
        getstr += "&search="+search_value;
        getstr += "&pdeptIdIndex="+document.pcodeForm.pdeptIdIndex.value;
        getstr += "&assettypeIdIndex="+document.pcodeForm.assettypeIdIndex.value;
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
    if(document.pcodeForm.doView)
        document.pcodeForm.doView.value="no";
    if(document.pcodeForm.doCancel)
        document.pcodeForm.doCancel.value="yes";  
    if(document.pcodeForm.doSave)
        document.pcodeForm.doSave.value="no";
    if(document.pcodeForm.doSaveQuestion)
        document.pcodeForm.doSaveQuestion.value="no";
    document.pcodeForm.action="../pcode/PcodeAction.do";
    document.pcodeForm.submit();
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
    var url_sort = "../ajax/pcode/sort.jsp";
    var getstr = "";
    var nextValue = 0;
    if(document.pcodeForm.nextValue)
        nextValue = document.pcodeForm.nextValue.value;
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
    document.pcodeForm.doModify.value="no";
    document.pcodeForm.doView.value="no";
    document.pcodeForm.doAdd.value="yes";
    document.pcodeForm.action="../pcode/PcodeAction.do";
    document.pcodeForm.submit();
}

function modifyForm(id)
{
    document.pcodeForm.doModify.value="yes";
    document.pcodeForm.doView.value="no";
    document.pcodeForm.doAdd.value="no";
    document.pcodeForm.pcodeId.value=id;
    document.pcodeForm.action="../pcode/PcodeAction.do";
    document.pcodeForm.submit();
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
    if (checkPcode())
    {
        document.getElementById('submitdiv').innerHTML = "<img src='../assets/images/loading.gif' align='absmiddle' />";
        document.pcodeForm.doSave.value="yes";
        document.pcodeForm.doCancel.value="no";
        document.pcodeForm.action="../pcode/PcodeAction.do";
        document.pcodeForm.submit();
    }
}

function checkPcode()
{
    if(document.pcodeForm.assettypeId.value == "-1")
    {
     Swal.fire({
        title: "Please select asset type.",
        didClose:() => {
          document.pcodeForm.assettypeId.focus();
        }
        }) 
        return false;
    }
    if(document.pcodeForm.pdeptId.value == "-1")
    {
     Swal.fire({
        title: "Please select department.",
        didClose:() => {
          document.pcodeForm.pdeptId.focus();
        }
        }) 
        return false;
    }
    if (trim(document.pcodeForm.code.value) == "")
    {
        Swal.fire({
        title: "Please enter role competency code.",
        didClose:() => {
          document.pcodeForm.code.focus();
        }
        }) 
        return false;
    }
    if (validdesc(document.pcodeForm.code) == false)
    {
        return false;
    }   
    if (trim(document.pcodeForm.name.value) == "")
    {
        Swal.fire({
        title: "Please enter role competency.",
        didClose:() => {
          document.pcodeForm.name.focus();
        }
        }) 
        return false;
    }
    if (validdesc(document.pcodeForm.name) == false)
    {
        return false;
    }   
    return true;
}

function resetForm()
{
    document.pcodeForm.reset();
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
        var url = "../ajax/pcode/getinfo.jsp";
        var getstr = "";
        var httploc = getHTTPObject();
        var next_value = escape(document.pcodeForm.nextValue.value);
        var next_del = "-1";
        if(document.pcodeForm.nextDel)
            next_del = escape(document.pcodeForm.nextDel.value);
        var search_value = escape(document.pcodeForm.search.value);
        getstr += "nextValue="+next_value;
        getstr += "&nextDel="+next_del;
        getstr += "&search="+search_value;
        getstr += "&status="+status;
        getstr += "&pdeptIdIndex="+document.pcodeForm.pdeptIdIndex.value;
        getstr += "&assettypeIdIndex="+document.pcodeForm.assettypeIdIndex.value;
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
    document.pcodeForm.action = "../pcode/PcodeExportAction.do?type=1";
    document.pcodeForm.submit();
}

function exportexcel()
{   
    document.pcodeForm.action = "../pcode/PcodeExportAction.do?type=2";
    document.pcodeForm.submit();
}

function setDept(tp)
{
    var httploc = getHTTPObject();
    var url = "../ajax/pcode/getdept.jsp";
    var getstr = "";
    var assettypeId = 0;
    var assettypeIdIndex = 0;
    if(Number(tp) == 1)
        assettypeIdIndex = document.pcodeForm.assettypeIdIndex.value;
    else
        assettypeId = document.pcodeForm.assettypeId.value;
    if(Number(tp) == 1)
        getstr += "assettypeIdIndex="+assettypeIdIndex;
    else
        getstr += "assettypeId="+assettypeId;
    httploc.open("POST", url, true);
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

function changeCategory()
{
    if (document.pcodeForm.categoryId.value == "-1")
    {
        Swal.fire({
            title: "Please select category.",
            didClose: () => {
                document.pcodeForm.categoryId.focus();
            }
        })
    }
    else
    {
        document.pcodeForm.categoryName.value = document.getElementById("categorId").options[document.getElementById("categorId").selectedIndex].text;
        document.pcodeForm.doCategory.value = "yes";
        if (document.pcodeForm.doView)
            document.pcodeForm.doView.value = "no";
        if (document.pcodeForm.doCancel)
            document.pcodeForm.doCancel.value = "no";
        document.pcodeForm.doSaveQuestion.value = "no";
        document.pcodeForm.action = "../pcode/PcodeAction.do";
        document.pcodeForm.submit();
    }
}

function setQuestion(size)
{
    if (document.getElementById("catcb").checked)
    {
        for (var i = 1; i <= size; i++)
        {
            document.getElementById("questioncb_"+i).checked = true;
            document.getElementById("questionId_"+ i).value = document.getElementById("questioncb_"+ i).value;            
        }
    } 
    else
    {
        for (var i = 1; i <= size; i++)
        {
            document.getElementById("questioncb_"+i).checked = false;
            document.getElementById("questionId_"+i).value = "-1";
        }
    }
}

function setquestionhidden(tp)
{
    if(document.getElementById("questioncb_"+tp).checked)
    {
        document.getElementById("questionId_"+tp).value = document.getElementById("questioncb_"+tp).value;
    }
    else
    {
        document.getElementById("questionId_"+tp).value = "-1";
    }
}

function checkquestion()
{
    var x = 0;
    if (Number(document.pcodeForm.categoryId.value) <= 0)
    {
        Swal.fire({
            title: "Please select category.",
            didClose: () => {
                document.pcodeForm.categoryId.focus();
            }
        })
        return false;
    }
    return true;
}

function save()
{
    if (checkquestion())
    {
        document.getElementById('savediv').innerHTML = "<div><img src='../assets/images/loading.gif' align='absmiddle'/></div>";
        document.pcodeForm.doCancel.value = "no";
        document.pcodeForm.doCategory.value = "no";
        document.pcodeForm.doSaveQuestion.value = "yes";
        document.pcodeForm.action = "../pcode/PcodeAction.do";
        document.pcodeForm.submit();
    }
}
