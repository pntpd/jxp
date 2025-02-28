function resetFilter()
{
    document.forms[0].search.value = "";
    searchFormAjax('s', '-1');
}

function showDetail(id)
{
    document.formalityForm.doView.value = "yes";
    document.formalityForm.doModify.value = "no";
    document.formalityForm.doAdd.value = "no";
    document.formalityForm.formalityId.value = id;
    document.formalityForm.action = "../formality/FormalityAction.do";
    document.formalityForm.submit();
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
        var url = "../ajax/formality/getinfo.jsp";
        var httploc = getHTTPObject();
        var getstr = "";
        var next_value = escape(document.formalityForm.nextValue.value);
        var search_value = escape(document.formalityForm.search.value);
        getstr += "nextValue=" + next_value;
        getstr += "&next=" + v;
        getstr += "&search=" + search_value;
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
    if (document.formalityForm.doView)
        document.formalityForm.doView.value = "no";
    if (document.formalityForm.doCancel)
        document.formalityForm.doCancel.value = "yes";
    if (document.formalityForm.doSave)
        document.formalityForm.doSave.value = "no";
    document.formalityForm.action = "../formality/FormalityAction.do";
    document.formalityForm.submit();
}

function sortForm(colid, updown)
{
    for (i = 1; i <= 1; i++)
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
    var url_sort = "../ajax/formality/sort.jsp";
    var getstr = "";
    var nextValue = 0;
    if (document.formalityForm.nextValue)
        nextValue = document.formalityForm.nextValue.value;
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

function addForm()
{
    document.formalityForm.doModify.value = "no";
    document.formalityForm.doView.value = "no";
    document.formalityForm.doAdd.value = "yes";
    document.formalityForm.action = "../formality/FormalityAction.do";
    document.formalityForm.submit();
}

function modifyForm(id)
{
    document.formalityForm.doModify.value = "yes";
    document.formalityForm.doView.value = "no";
    document.formalityForm.doAdd.value = "no";
    document.formalityForm.formalityId.value = id;
    document.formalityForm.action = "../formality/FormalityAction.do";
    document.formalityForm.submit();
}

function submitForm()
{
    if (document.forms[0].getElementsByTagName("input"))
    {
        var inputElements = document.forms[0].getElementsByTagName("input");
        for (i = 0; i < inputElements.length; i++)
        {
            if (inputElements[i].type == "text")
            {
                inputElements[i].value = trim(inputElements[i].value);
            }
        }
    }
    if (checkFormality())
    {
        document.getElementById('submitdiv').innerHTML = "<img src='../assets/images/loading.gif' align='absmiddle' />";
        document.formalityForm.doSave.value = "yes";
        document.formalityForm.doCancel.value = "no";
        document.formalityForm.action = "../formality/FormalityAction.do";
        document.formalityForm.submit();
    }
}

function checkFormality()
{
    if (document.formalityForm.candidateId.value == "-1")
    {
        Swal.fire({
            title: "Please select client.",
            didClose: () => {
                document.formalityForm.candidateId.focus();
            }
        })
        return false;
    }
    if (trim(document.formalityForm.name.value) == "")
    {
        Swal.fire({
            title: "Please enter name.",
            didClose: () => {
                document.formalityForm.name.focus();
            }
        })
        return false;
    }
    if (validname(document.formalityForm.name) == false)
    {
        return false;
    }
    if(document.formalityForm.temptype.value <=0)
    {
        Swal.fire({
            title: "please select template.",
            didClose: () => {
            document.formalityForm.temptype.focus();
            }
        })
        return false;
    }
    if(document.formalityForm.temptype.value == 2)
    {
        if (document.formalityForm.formalityfilehidden.value == "")
        {
            if (document.formalityForm.formalityfile.value == "")
            {
                Swal.fire({
                    title: "please upload document.",
                    didClose: () => {
                        document.formalityForm.formalityfile.focus();
                    }
                })
                return false;
            }
         }
    }
    if (document.formalityForm.formalityfile.value != "")
    {
        if (!(document.formalityForm.formalityfile.value).match(/(\.(docx)|(pdf)|(doc))$/i))
        {
            Swal.fire({
                title: "Only .docx, .pdf, .doc are allowed.",
                didClose: () => {
                    document.formalityForm.formalityfile.focus();
                }
            })
            return false;
        }
        var input = document.formalityForm.formalityfile;
        if (input.files)
        {
            var file = input.files[0];
            if (file.size > 1024 * 1024 * 5)
            {
                Swal.fire({
                    title: "File size should not exceed 5 MB.",
                    didClose: () => {
                        document.formalityForm.formalityfile.focus();
                    }
                })
                return false;
            }
        }
    }  
    if(document.formalityForm.temptype.value == 1)
    {
        var content = tinyMCE.editors["elm1"].getContent();
        if (content == "")
        {
            Swal.fire({
                title: "Please enter description.",
                didClose: () => {
                    tinyMCE.get("elm1").focus();
                }
            })
            return false;
        }
    }
    if (document.formalityForm.formalityfile2.value != "")
    {
        if (!(document.formalityForm.formalityfile2.value).match(/(\.(png)|(jpg)|(jpeg))$/i))
        {
            Swal.fire({
                title: "Only .png, .jpg, .jpeg are allowed.",
                didClose: () => {
                    document.formalityForm.formalityfile2.focus();
                }
            })
            return false;
        }
        var input = document.formalityForm.formalityfile2;
        if (input.files)
        {
            var file = input.files[0];
            if (file.size > 1024 * 1024 * 5)
            {
                Swal.fire({
                    title: "File size should not exceed 5 MB.",
                    didClose: () => {
                        document.formalityForm.formalityfile2.focus();
                    }
                })
                return false;
            }
        }
    }
    return true;
}

function resetForm()
{
    document.formalityForm.reset();
}

function deleteForm(userId, status, id)
{
    var s = "";
    if (eval(status) == 1)
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
            var url = "../ajax/formality/getinfo.jsp";
            var getstr = "";
            var httploc = getHTTPObject();
            var next_value = escape(document.formalityForm.nextValue.value);
            var next_del = "-1";
            if (document.formalityForm.nextDel)
                next_del = escape(document.formalityForm.nextDel.value);
            var search_value = escape(document.formalityForm.search.value);
            getstr += "nextValue=" + next_value;
            getstr += "&nextDel=" + next_del;
            getstr += "&search=" + search_value;
            getstr += "&status=" + status;
            getstr += "&deleteVal=" + userId;
            httploc.open("POST", url, true);
            httploc.onreadystatechange = function ()
            {
                if (httploc.readyState == 4)
                {
                    if (httploc.status == 200)
                    {
                        var response = httploc.responseText;
                        var arr = new Array();
                        arr = response.split('##');
                        var v1 = arr[0];
                        var v2 = trim(arr[1]);
                        document.getElementById('ajax_cat').innerHTML = '';
                        document.getElementById('ajax_cat').innerHTML = v1;
                        if (trim(v2) != "")
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
        } else
        {
            if (document.getElementById("flexSwitchCheckDefault_" + id).checked == true)
                document.getElementById("flexSwitchCheckDefault_" + id).checked = false;
            else
                document.getElementById("flexSwitchCheckDefault_" + id).checked = true;
        }
    })
}

function exporttoexcel()
{
    document.formalityForm.action = "../formality/FormalityExportAction.do";
    document.formalityForm.submit();
}

function copyTemplate(id)
{
    var copyText = trim(document.getElementById(id).innerHTML);
    navigator.clipboard.writeText(copyText);
}

function showhide()
{
    if(document.forms[0].temptype[0].checked)
    {
        document.getElementById("div2").style.display = "";
        document.getElementById("div1").style.display = "none";
    }
    else
    {
        document.getElementById("div2").style.display = "none";
        document.getElementById("div1").style.display = "";
    }
}

function setClass(tp)
{
    document.getElementById("upload_link_" + tp).className = "attache_btn uploaded_img";
}