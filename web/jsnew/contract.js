function resetFilter()
{
    document.forms[0].search.value = "";
    searchFormAjax('s', '-1');
}

function showDetail(id)
{
    document.contractForm.doView.value = "yes";
    document.contractForm.doModify.value = "no";
    document.contractForm.doAdd.value = "no";
    document.contractForm.contractId.value = id;
    document.contractForm.action = "../contract/ContractAction.do";
    document.contractForm.submit();
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
        var url = "../ajax/contract/getinfo.jsp";
        var httploc = getHTTPObject();
        var getstr = "";
        var next_value = escape(document.contractForm.nextValue.value);
        var search_value = escape(document.contractForm.search.value);
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
    if (document.contractForm.doView)
        document.contractForm.doView.value = "no";
    if (document.contractForm.doCancel)
        document.contractForm.doCancel.value = "yes";
    if (document.contractForm.doSave)
        document.contractForm.doSave.value = "no";
    document.contractForm.action = "../contract/ContractAction.do";
    document.contractForm.submit();
}

function sortForm(colid, updown)
{
    for (i = 1; i <= 3; i++)
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
    var url_sort = "../ajax/contract/sort.jsp";
    var getstr = "";
    var nextValue = 0;
    if (document.contractForm.nextValue)
        nextValue = document.contractForm.nextValue.value;
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
    document.contractForm.doModify.value = "no";
    document.contractForm.doView.value = "no";
    document.contractForm.doAdd.value = "yes";
    document.contractForm.action = "../contract/ContractAction.do";
    document.contractForm.submit();
}

function modifyForm(id)
{
    document.contractForm.doModify.value = "yes";
    document.contractForm.doView.value = "no";
    document.contractForm.doAdd.value = "no";
    document.contractForm.contractId.value = id;
    document.contractForm.action = "../contract/ContractAction.do";
    document.contractForm.submit();
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
    if (checkContract())
    {
        document.getElementById('submitdiv').innerHTML = "<img src='../assets/images/loading.gif' align='absmiddle' />";
        document.contractForm.doSave.value = "yes";
        document.contractForm.doCancel.value = "no";
        document.contractForm.action = "../contract/ContractAction.do";
        document.contractForm.submit();
    }
}

function checkContract()
{
    if ((document.contractForm.clientId.value) == "-1")
    {
     Swal.fire({
        title: "Please select client.",
        didClose:() => {
          document.contractForm.clientId.focus();
        }
        }) 
        return false;
    }
    if ((document.contractForm.assetId.value) == "-1")
    {
     Swal.fire({
        title: "Please select asset.",
        didClose:() => {
          document.contractForm.assetId.focus();
        }
        }) 
        return false;
    }
    if (trim(document.contractForm.name.value) == "")
    {
     Swal.fire({
        title: "Please enter name.",
        didClose:() => {
          document.contractForm.name.focus();
        }
        }) 
        return false;
    }
    if (validname(document.contractForm.name) == false)
    {
        return false;
    }
    if ((document.contractForm.assetId.value) > 0)
    {
        if ((document.contractForm.type.value) == "2")
        {
            if (trim(document.contractForm.refId.value) == "-1")
            {
             Swal.fire({
                title: "Please select main contract.",
                didClose:() => {
                  document.contractForm.refId.focus();
                }
                }) 
                return false;
            }
        }
    }
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
    if (document.contractForm.contractfile.value != "")
    {
        if (!(document.contractForm.contractfile.value).match(/(\.(png)|(jpg)|(jpeg))$/i))
        {
            Swal.fire({
                title: "Only .png, .jpg, .jpeg are allowed.",
                didClose: () => {
                    document.contractForm.contractfile.focus();
                }
            })
            return false;
        }
        var input = document.contractForm.contractfile;
        if (input.files)
        {
            var file = input.files[0];
            if (file.size > 1024 * 1024 * 5)
            {
                Swal.fire({
                    title: "File size should not exceed 5 MB.",
                    didClose: () => {
                        document.contractForm.contractfile.focus();
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
    document.contractForm.reset();
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
            var url = "../ajax/contract/getinfo.jsp";
            var getstr = "";
            var httploc = getHTTPObject();
            var next_value = escape(document.contractForm.nextValue.value);
            var next_del = "-1";
            if (document.contractForm.nextDel)
                next_del = escape(document.contractForm.nextDel.value);
            var search_value = escape(document.contractForm.search.value);
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
    document.contractForm.action = "../contract/ContractExportAction.do";
    document.contractForm.submit();
}

function copyTemplate(id)
{
    var copyText = trim(document.getElementById(id).innerHTML);
    navigator.clipboard.writeText(copyText);
}

function showhide()
{
    if(document.forms[0].type[1].checked)
    {
        document.getElementById("subDiv").style.display = "";        
    }
    else
    {
        document.getElementById("subDiv").style.display = "none";
        document.contractForm.refId.value = "-1";
    }
}

function setClass(tp)
{
    document.getElementById("upload_link_" + tp).className = "attache_btn uploaded_img";
}

function setAssetsDDL()
{
    var url = "../ajax/contract/getassetDDL.jsp";
    var httploc = getHTTPObject();
    var getstr = "clientId=" + document.forms[0].clientId.value;
    getstr += "&assetId=" + document.forms[0].assetId.value;
    httploc.open("POST", url, true);
    httploc.onreadystatechange = function ()
    {
        if (httploc.readyState == 4)
        {
            if (httploc.status == 200)
            {
                var response = httploc.responseText;
                document.getElementById("assetId").innerHTML = '';
                document.getElementById("assetId").innerHTML = response;
                setContractDDL();
            }
        }
    };
    httploc.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    httploc.setRequestHeader("Content-length", getstr.length);
    httploc.setRequestHeader("Connection", "close");
    httploc.send(getstr);
}

function setContractDDL()
{
    var url = "../ajax/contract/getContractDDL.jsp";
    var httploc = getHTTPObject();
    var getstr = "assetId=" + document.forms[0].assetId.value;
    getstr += "&refId=" + document.forms[0].refId.value;
    httploc.open("POST", url, true);
    httploc.onreadystatechange = function ()
    {
        if (httploc.readyState == 4)
        {
            if (httploc.status == 200)
            {
                var response = httploc.responseText;
                document.getElementById("refId").innerHTML = '';
                document.getElementById("refId").innerHTML = response;
            }
        }
    };
    httploc.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    httploc.setRequestHeader("Content-length", getstr.length);
    httploc.setRequestHeader("Connection", "close");
    httploc.send(getstr);
}

function deleteFile(id)
{
    var s = "<span>The watermark will be <b>deleted.</b></span>";
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
            document.getElementById('submitdiv').innerHTML = "<img src='../assets/images/loading.gif' align='absmiddle' />";
            document.contractForm.doDeleteFile.value = "yes";
            document.contractForm.contractId.value = id;
            document.contractForm.action = "../contract/ContractAction.do";
            document.contractForm.submit();
        }
    })
}

function setIframe(uval)
{
    var url_v = "", classname = "";
    if (uval.includes(".doc") || uval.includes(".docx") || uval.includes("wordprocessingml.document")) 
    {
        url_v = "https://docs.google.com/gview?url=" + uval + "&embedded=true";
        classname = "doc_mode";
    }
    else if (uval.includes(".pdf"))
    {
        url_v = uval+"#toolbar=0&page=1&view=fitH,100";
        classname = "pdf_mode";
    }
    else
    {
        url_v = uval;
        classname = "img_mode";
    }
    window.top.$('#iframe').attr('src', 'about:blank');
    setTimeout(function () {
        window.top.$('#iframe').attr('src', url_v);
         document.getElementById("iframe").className=classname;
         document.getElementById("diframe").href =uval;
    }, 1000);

    $("#iframe").on("load", function() {
            let head = $("#iframe").contents().find("head");
            let css = '<style>img{margin: 0px auto;}</style>';
            $(head).append(css);
        });
}