function resetFilter()
{
    document.forms[0].search.value = "";
    document.frameworkForm.clientIdIndex.value = "-1"
    document.frameworkForm.assetIdIndex.value = "-1"
    setAssetDDL();
    searchFormAjax('s', '-1');
}

function showDetail(clientId, assetId)
{
    document.forms[0].target = "_self";
    document.frameworkForm.doView.value = "yes";
    document.frameworkForm.clientId.value = clientId;
    document.frameworkForm.assetId.value = assetId;
    document.frameworkForm.action = "../framework/FrameworkAction.do";
    document.frameworkForm.submit();
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
        var url = "../ajax/framework/getinfo.jsp";
        var httploc = getHTTPObject();
        var getstr = "";
        var next_value = escape(document.frameworkForm.nextValue.value);
        var search_value = escape(document.frameworkForm.search.value);
        var assetIdIndex = escape(document.frameworkForm.assetIdIndex.value);
        var clientIdIndex = escape(document.frameworkForm.clientIdIndex.value);
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
    if (document.frameworkForm.doView)
        document.frameworkForm.doView.value = "no";
    if (document.frameworkForm.doCancel)
        document.frameworkForm.doCancel.value = "yes";
    if (document.frameworkForm.doSave)
        document.frameworkForm.doSave.value = "no";
    if (document.frameworkForm.doDeptChange)
        document.frameworkForm.doDeptChange.value = "no";
    if (document.frameworkForm.doDeleteRole)
        document.frameworkForm.doDeleteRole.value = "no";
    if (document.frameworkForm.doAssign)
        document.frameworkForm.doAssign.value = "no";
    if (document.frameworkForm.doAssignAll)
        document.frameworkForm.doAssignAll.value = "no";
    document.frameworkForm.action = "../framework/FrameworkAction.do";
    document.frameworkForm.submit();
}

function gobackassign()
{
    if (document.frameworkForm.doView)
        document.frameworkForm.doView.value = "yes";
    if (document.frameworkForm.doCancel)
        document.frameworkForm.doCancel.value = "no";
    if (document.frameworkForm.doSave)
        document.frameworkForm.doSave.value = "no";
    if (document.frameworkForm.doDeptChange)
        document.frameworkForm.doDeptChange.value = "no";
    if (document.frameworkForm.doDeleteRole)
        document.frameworkForm.doDeleteRole.value = "no";
    if (document.frameworkForm.doAssign)
        document.frameworkForm.doAssign.value = "no";
    if (document.frameworkForm.doAssignAll)
        document.frameworkForm.doAssignAll.value = "no";
    document.frameworkForm.doSave.value = "no";  
    document.frameworkForm.doChangePcode.value = "no";
    document.frameworkForm.action = "../framework/FrameworkAction.do";
    document.frameworkForm.submit();
}

function sortForm(colid, updown)
{
    for (i = 1; i <= 4; i++)
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
    var url_sort = "../ajax/framework/sort.jsp";
    var getstr = "";
    var nextValue = 0;
    if (document.frameworkForm.nextValue)
        nextValue = document.frameworkForm.nextValue.value;
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
    document.frameworkForm.reset();
}

function exporttoexcel()
{
    document.frameworkForm.action = "../framework/FrameworkExportAction.do?exceltype=1";
    document.frameworkForm.submit();
}

function exportexcel(type)
{
    document.frameworkForm.action = "../framework/FrameworkExportAction.do?exceltype="+type;
    document.frameworkForm.submit();
}

function changeDept()
{ 
    document.forms[0].target = "_self";
    document.frameworkForm.doDeptChange.value = "yes";
    if (document.frameworkForm.doView)
        document.frameworkForm.doView.value = "no";
    if (document.frameworkForm.doCancel)
        document.frameworkForm.doCancel.value = "no";
    document.frameworkForm.doAssign.value = "no";   
    document.frameworkForm.doAssignAll.value = "no"; 
    document.frameworkForm.doDeleteRole.value = "no";
    document.frameworkForm.action = "../framework/FrameworkAction.do";
    document.frameworkForm.submit();
}

function setAssetDDL()
{
    var url = "../ajax/framework/getasset.jsp";
    document.getElementById("assetIdIndex").value = '-1';
    var httploc = getHTTPObject();
    var getstr = "clientIdIndex=" + document.frameworkForm.clientIdIndex.value + "&from=asset";
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

function setallcb()
{   
    if(Number(document.frameworkForm.pdeptId.value) > 0)
    {
        var ct = 0;
        if(document.getElementById("cball").checked == true)
        {
            if(document.frameworkForm.positioncb)
            {
                if(document.frameworkForm.positioncb.length)
                {
                    for(var i = 0; i < document.frameworkForm.positioncb.length; i++)
                    {
                        document.frameworkForm.positioncb[i].checked = true;
                        ct++;
                    }
                }
                else
                {
                    document.frameworkForm.positioncb.checked = true;
                    ct++;
                }
            }
        }
        else
        {
            if(document.frameworkForm.positioncb)
            {
                if(document.frameworkForm.positioncb.length)
                {
                    for(var i = 0; i < document.frameworkForm.positioncb.length; i++)
                        document.frameworkForm.positioncb[i].checked = false;
                }
                else
                {
                    document.frameworkForm.positioncb.checked = false;
                }
            }
        }
        if(document.getElementById("assignhref"))
        {
            if(Number(ct) > 0)
            {
                document.getElementById("assignhref").href="javascript: assignall();";
                document.getElementById("assignhref").className = "add_new_ques active_btn";
            }
            else
            {
                document.getElementById("assignhref").href="javascript:;";
                document.getElementById("assignhref").className = "add_new_ques inactive_btn";
            }
        }
    }
    else
    {        
        Swal.fire({
            title: "Please select department.",
            didClose: () => {
                document.getElementById("cball").checked = false;
                document.frameworkForm.pdeptId.focus();
            }
        })
    }
}

function setcb(tp)
{   
    if(Number(document.frameworkForm.pdeptId.value) > 0)
    {
        var ct = 0;
        if(document.frameworkForm.positioncb)
        {
            if(document.frameworkForm.positioncb.length)
            {
                for(var i = 0; i < document.frameworkForm.positioncb.length; i++)
                {
                    if(document.frameworkForm.positioncb[i].checked)
                        ct++;
                }
            }
            else
            {
                if(document.frameworkForm.positioncb.checked);
                    ct++;
            }
        }
        if(document.getElementById("assignhref"))
        {
            if(Number(ct) > 0)
            {
                document.getElementById("assignhref").href="javascript: assignall();";
                document.getElementById("assignhref").className = "assign_training active_btn";
            }
            else
            {
                document.getElementById("assignhref").href="javascript:;";
                document.getElementById("assignhref").className = "assign_training inactive_btn";
            }
        }
    }
    else
    {
        Swal.fire({
            title: "Please select department.",
            didClose: () => {
                document.getElementById("positioncb_"+tp).checked = false;
                document.frameworkForm.pdeptId.focus();
            }
        })
    }
}

function assign(positionId, pdeptId)
{ 
    document.forms[0].target = "_self";
    document.frameworkForm.positionIdHidden.value = positionId;
    document.frameworkForm.pdeptIdHidden.value = pdeptId;
    document.frameworkForm.doAssign.value = "yes";
    document.frameworkForm.doDeptChange.value = "no";
    document.frameworkForm.doView.value = "no";
    document.frameworkForm.doCancel.value = "no";  
    document.frameworkForm.doDeleteRole.value = "no";
    document.frameworkForm.action = "../framework/FrameworkAction.do";
    document.frameworkForm.submit();
}

function assignall()
{ 
    document.forms[0].target = "_self";
    document.frameworkForm.pdeptIdHidden.value = document.frameworkForm.pdeptId.value;
    document.frameworkForm.doAssignAll.value = "yes";
    document.frameworkForm.doAssign.value = "no";
    document.frameworkForm.doDeptChange.value = "no";
    document.frameworkForm.doView.value = "no";
    document.frameworkForm.doCancel.value = "no";  
    document.frameworkForm.doDeleteRole.value = "no";
    document.frameworkForm.action = "../framework/FrameworkAction.do";
    document.frameworkForm.submit();
}

function changepcode()
{
    document.forms[0].target = "_self";
    document.frameworkForm.pcodeName.value = document.getElementById("pcodeId").options[document.getElementById("pcodeId").selectedIndex].text;
    document.frameworkForm.doView.value = "no";
    document.frameworkForm.doCancel.value = "no";  
    document.frameworkForm.doSave.value = "no";  
    document.frameworkForm.doChangePcode.value = "yes"; 
    document.frameworkForm.action = "../framework/FrameworkAction.do";
    document.frameworkForm.submit();
}

function setquestion(categoryId, tp, size)
{
    if (document.getElementById("categorycb_" + tp).checked)
    {
        for (var i = 1; i <= size; i++)
        {
            document.getElementById("questioncb_" + categoryId + "_" + i).checked = true;
            document.getElementById("questionId_" + categoryId + "_" + i).value = document.getElementById("questioncb_" + categoryId + "_" + i).value;
            document.getElementById("categoryId_" + categoryId + "_" + i).value = categoryId;
        }
    } 
    else
    {
        for (var i = 1; i <= size; i++)
        {
            document.getElementById("questioncb_" + categoryId + "_" + i).checked = false;
            document.getElementById("questionId_" + categoryId + "_" + i).value = "-1";
            document.getElementById("categoryId_" + categoryId + "_" + i).value = "-1";
        }
    }
}

function setquestionhidden(categoryId, tp)
{
    if(document.getElementById("questioncb_"+categoryId+"_"+tp).checked)
    {
        document.getElementById("questionId_"+categoryId+"_"+tp).value = document.getElementById("questioncb_"+categoryId+"_"+tp).value;
    }
    else
    {
        document.getElementById("questionId_"+categoryId+"_"+tp).value = "-1";
    }
}

function checkquestion()
{
    var ct = 0;
    if(document.forms[0].questioncb)
    {
        if(document.forms[0].questioncb.length)
        {
            for(var i = 0; i < document.forms[0].questioncb.length; i++)
            {
                if(document.forms[0].questioncb[i].checked)
                {
                    ct++;
                }
            }
        }
        else
        {
            if(document.forms[0].questioncb.checked)
                ct++;
        }
    }
    if (Number(document.frameworkForm.pcodeId.value) <= 0)
    {
        Swal.fire({
            title: "Please select competency code.",
            didClose: () => {
                document.frameworkForm.pcodeId.focus();
            }
        })
        return false;
    }
    if(Number(ct) > 0)
    {
        if (Number(document.frameworkForm.monthId.value) <= 0)
        {
            Swal.fire({
                title: "Please set validity.",
                didClose: () => {
                    document.frameworkForm.monthId.focus();
                }
            })
            return false;
        }
    }
    if(Number(ct) > 0)
    {
        if (Number(document.frameworkForm.passessmenttypeId.value) <= 0)
        {
            Swal.fire({
                title: "Please select assessment type.",
                didClose: () => {
                    document.frameworkForm.passessmenttypeId.focus();
                }
            })
            return false;
        }
        if (Number(document.frameworkForm.priorityId.value) <= 0)
        {
            Swal.fire({
                title: "Please select priority.",
                didClose: () => {
                    document.frameworkForm.priorityId.focus();
                }
            })
            return false;
        }
    }
    return true;
}

function save()
{
    if (checkquestion())
    {
        document.forms[0].target = "_self";
        document.getElementById("savediv").innerHTML = "<div><img src='../assets/images/loading.gif' align='absmiddle'/>Please wait. Loading...</div>";
        document.frameworkForm.doView.value = "no";
        document.frameworkForm.doCancel.value = "no"; 
        document.frameworkForm.doChangePcode.value = "no"; 
        document.frameworkForm.doSave.value = "yes";
        document.frameworkForm.action = "../framework/FrameworkAction.do";
        document.frameworkForm.submit();
    }
}

function deleterole(positionId, count, id)
{
    var s = "";
    if (eval(count) > 0)
    {
        document.frameworkForm.status.value = 2;
        s = "<span>The selected item will be <b>deactivated.</b></span>";
    }
    else
    {
        document.frameworkForm.status.value = 1;
        s = "<span>The selected item will be <b>activated.</b></span>";
    }
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
            document.frameworkForm.positionIdHidden.value = positionId;
            document.frameworkForm.doCancel.value = "no";
            document.frameworkForm.doView.value = "no";            
            document.frameworkForm.doDeptChange.value = "no";            
            document.frameworkForm.doAssign.value = "no"; 
            document.frameworkForm.doAssignAll.value = "no";
            document.frameworkForm.doDeleteRole.value = "yes"; 
            document.frameworkForm.action = "../framework/FrameworkAction.do";
            document.frameworkForm.submit();
        } 
        else
        {
            if (document.getElementById("flexSwitchCheckDefault_" + id).checked == true)
                document.getElementById("flexSwitchCheckDefault_" + id).checked = false;
            else
                document.getElementById("flexSwitchCheckDefault_" + id).checked = true;
        }
    })
}

function setposition()
{
    var url = "../ajax/framework/setposmodal.jsp";
    var httploc = getHTTPObject();
    var getstr = "pids=" + document.forms[0].pids.value;
    httploc.open("POST", url, true);
    httploc.onreadystatechange = function ()
    {
        if (httploc.readyState == 4)
        {
            if (httploc.status == 200)
            {
                var response = trim(httploc.responseText);
                document.getElementById("pdivlist").innerHTML = response;
            }
        }
    };
    httploc.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    httploc.setRequestHeader("Content-length", getstr.length);
    httploc.setRequestHeader("Connection", "close");
    httploc.send(getstr);
}