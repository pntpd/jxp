function resetFilter()
{
    document.forms[0].search.value = "";
    document.clientForm.countryIndexId.value = "-1";
    document.clientForm.assettypeIndexId.value = "-1";
    searchFormAjax('s', '-1');
}

function showDetail(id)
{
    if (eval(id) <= 0)
        id = document.clientForm.clientId.value;
    else
        document.clientForm.clientId.value = id;
    document.clientForm.doView.value = "yes";
    document.clientForm.doModify.value = "no";
    document.clientForm.doAdd.value = "no";
    document.clientForm.action = "../client/ClientAction.do?tabno=1";
    document.clientForm.submit();
}

function viewasset(id)
{
    if (eval(id) <= 0)
        id = document.clientForm.clientId.value;
    else
        document.clientForm.clientId.value = id;
    document.clientForm.doView.value = "yes";
    document.clientForm.doModify.value = "no";
    document.clientForm.doAdd.value = "no";
    document.clientForm.clientId.value = id;
    document.clientForm.action = "../client/ClientAction.do?tabno=2";
    document.clientForm.submit();
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

function searchFormAjax(v, v1)
{
    var url = "../ajax/client/getinfo.jsp";
    var httploc = getHTTPObject();
    var getstr = "";
    var next_value = escape(document.clientForm.nextValue.value);
    var search_value = escape(document.clientForm.search.value);
    var countryIndexId = escape(document.clientForm.countryIndexId.value);
    var assettypeIndexId = escape(document.clientForm.assettypeIndexId.value);
    getstr += "nextValue=" + next_value;
    getstr += "&next=" + v;
    getstr += "&search=" + search_value;
    getstr += "&countryIndexId=" + countryIndexId;
    getstr += "&assettypeIndexId=" + assettypeIndexId;
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

function goback()
{
    if (document.clientForm.doView)
        document.clientForm.doView.value = "no";
    if (document.clientForm.doCancel)
        document.clientForm.doCancel.value = "yes";
    if (document.clientForm.doSave)
        document.clientForm.doSave.value = "no";
    if (document.clientForm.doSaveAsset)
        document.clientForm.doSaveAsset.value = "no";
    if (document.clientForm.doDeleteAsset)
        document.clientForm.doDeleteAsset.value = "no";
    if (document.clientForm.doModifyAsset)
        document.clientForm.doModifyAsset.value = "no";
    document.clientForm.action = "../client/ClientAction.do";
    document.clientForm.submit();
}

function gobackasset()
{
    if (document.clientForm.doView)
        document.clientForm.doView.value = "yes";
    if (document.clientForm.doCancel)
        document.clientForm.doCancel.value = "no";
    if (document.clientForm.doSave)
        document.clientForm.doSave.value = "no";
    if (document.clientForm.doSaveAsset)
        document.clientForm.doSaveAsset.value = "no";
    if (document.clientForm.doDeleteAsset)
        document.clientForm.doDeleteAsset.value = "no";
    document.clientForm.action = "../client/ClientAction.do?tabno=2";
    document.clientForm.submit();
}

function sortForm(colid, updown)
{
    for (i = 1; i <= 5; i++)
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
    var url_sort = "../ajax/client/sort.jsp";
    var getstr = "";
    var nextValue = 0;
    if (document.clientForm.nextValue)
        nextValue = document.clientForm.nextValue.value;
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

function sortFormCA(colid, updown)
{
    for (i = 1; i <= 5; i++)
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
    var url_sort = "../ajax/client/sortca.jsp";
    var getstr = "";
    getstr += "col=" + colid;
    getstr += "&updown=" + updown;
    httploc.open("POST", url_sort, true);
    httploc.onreadystatechange = function ()
    {
        if (httploc.readyState == 4)
        {
            if (httploc.status == 200)
            {
                var response = httploc.responseText;
                document.getElementById('sortca_id').innerHTML = '';
                document.getElementById('sortca_id').innerHTML = response;
            }
        }
    };
    httploc.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    httploc.setRequestHeader("Content-length", getstr.length);
    httploc.setRequestHeader("Connection", "close");
    httploc.send(getstr);
    document.getElementById('sortca_id').innerHTML = "<div><img src='../assets/images/loading.gif' align='absmiddle'/>Please wait. Loading...</div>";
}

function updateclientassetlist()
{
    var clientId = document.clientForm.clientId.value;
    var httploc = getHTTPObject();
    var url_sort = "../ajax/client/getclientassetlist.jsp";
    var getstr = "";
    getstr += "clientId=" + clientId;

    httploc.open("POST", url_sort, true);
    httploc.onreadystatechange = function ()
    {
        if (httploc.readyState == 4)
        {
            if (httploc.status == 200)
            {
                var response = httploc.responseText;
                document.getElementById('sortca_id').innerHTML = '';
                document.getElementById('sortca_id').innerHTML = response;
            }
        }
    };
    httploc.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    httploc.setRequestHeader("Content-length", getstr.length);
    httploc.setRequestHeader("Connection", "close");
    httploc.send(getstr);
}

function addForm()
{
    document.clientForm.doModify.value = "no";
    document.clientForm.doView.value = "no";
    document.clientForm.doAdd.value = "yes";
    document.clientForm.action = "../client/ClientAction.do";
    document.clientForm.submit();
}

function modifyForm(id)
{
    if (eval(id) <= 0)
        id = document.clientForm.clientId.value;
    else
        document.clientForm.clientId.value = id;
    document.clientForm.doModify.value = "yes";
    document.clientForm.doView.value = "no";
    document.clientForm.doAdd.value = "no";
    document.clientForm.clientId.value = id;
    document.clientForm.action = "../client/ClientAction.do";
    document.clientForm.submit();
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
    if (checkClient())
    {
        document.getElementById('submitdiv').innerHTML = "<img src='../assets/images/loading.gif' align='absmiddle' />";
        document.clientForm.doSave.value = "yes";
        document.clientForm.doCancel.value = "no";
        document.clientForm.action = "../client/ClientAction.do";
        document.clientForm.submit();
    }
}

function checkClient()
{
    if (trim(document.clientForm.name.value) == "")
    {
        Swal.fire({
            title: "Please enter name.",
            didClose: () => {
                document.clientForm.name.focus();
            }
        })
        return false;
    }
    if (validdesc(document.clientForm.name) == false)
    {
        return false;
    }
    if (trim(document.clientForm.headofficeaddress.value) == "")
    {
        Swal.fire({
            title: "Please enter head office address.",
            didClose: () => {
                document.clientForm.headofficeaddress.focus();
            }
        })
        return false;
    }
    if (validdesc(document.clientForm.headofficeaddress) == false)
    {
        return false;
    }
    if (trim(document.clientForm.countryId.value) == "-1")
    {
        Swal.fire({
            title: "Please select country.",
            didClose: () => {
                document.clientForm.countryId.focus();
            }
        })
        return false;
    }

    if (trim(document.clientForm.inchargename.value) == "")
    {
        Swal.fire({
            title: "Please enter incharge name (point of contact).",
            didClose: () => {
                document.clientForm.inchargename.focus();
            }
        })
        return false;
    }
    if (validdesc(document.clientForm.inchargename) == false)
    {
        return false;
    }

    if (document.clientForm.email.value == "")
    {
        Swal.fire({
            title: "Please enter email.",
            didClose: () => {
                document.clientForm.email.focus();
            }
        })
        return false;
    }
    if (checkEmailAddress(document.clientForm.email) == false)
    {
        Swal.fire({
            title: "Please enter valid email.",
            didClose: () => {
                document.clientForm.email.focus();
            }
        })
        return false;
    }

    if (document.clientForm.ISDcode.value == "-1")
    {
        Swal.fire({
            title: "Please select ISD code.",
            didClose: () => {
                document.clientForm.ISDcode.focus();
            }
        })
        return false;
    }

    if (trim(document.clientForm.contact.value) == "")
    {
        Swal.fire({
            title: "Please enter contact number.",
            didClose: () => {
                document.clientForm.contact.focus();
            }
        })
        return false;
    }
    if (validnum(document.clientForm.contact) == false)
    {
        return false;
    }

    if (document.clientForm.ocsusermultiselect_dd.value == "")
    {
        Swal.fire({
            title: "Please select atleast one client co-ordinator from OCS.",
            didClose: () => {
                document.clientForm.ocsusermultiselect_dd.focus();
            }
        })
        return false;
    }
    if (document.clientForm.managermultiselect_dd.value == "")
    {
        Swal.fire({
            title: "Please select atleast one manager.",
            didClose: () => {
                document.clientForm.managermultiselect_dd.focus();
            }
        })
        return false;
    }
    if (document.clientForm.recruitermultiselect_dd.value == "")
    {
        Swal.fire({
            title: "Please select atleast one recruiter.",
            didClose: () => {
                document.clientForm.recruitermultiselect_dd.focus();
            }
        })
        return false;
    }
    if (document.clientForm.shortName.value == "")
    {
        Swal.fire({
            title: "Please enter shortName.",
            didClose: () => {
                document.clientForm.shortName.focus();
            }
        })
        return false;
    }
    if (validdesc(document.clientForm.shortName) == false)
    {
        return false;
    }

    if (document.clientForm.colorCode.value == "")
    {
        Swal.fire({
            title: "Please enter colorCode.",
            didClose: () => {
                document.clientForm.colorCode.focus();
            }
        })
        return false;
    }
    if (validdesc(document.clientForm.colorCode) == false)
    {
        return false;
    }
    return true;
}

function resetForm()
{
    document.clientForm.reset();
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
            var url = "../ajax/client/getinfo.jsp";
            var getstr = "";
            var httploc = getHTTPObject();
            var next_value = escape(document.clientForm.nextValue.value);
            var next_del = "-1";
            if (document.clientForm.nextDel)
                next_del = escape(document.clientForm.nextDel.value);
            var search_value = escape(document.clientForm.search.value);
            var countryIndexId = escape(document.clientForm.countryIndexId.value);
            var assettypeIndexId = escape(document.clientForm.assettypeIndexId.value);
            getstr += "nextValue=" + next_value;
            getstr += "&nextDel=" + next_del;
            getstr += "&search=" + search_value;
            getstr += "&status=" + status;
            getstr += "&deleteVal=" + userId;
            getstr += "&countryIndexId=" + countryIndexId;
            getstr += "&assettypeIndexId=" + assettypeIndexId;
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

function showlink(tp)
{
    for (i = 1; i <= 6; i++)
    {
        if (document.getElementById("linkid" + i))
            document.getElementById("linkid" + i).style.display = "none";
    }
    if (document.getElementById("linkid" + tp))
        document.getElementById("linkid" + tp).style.display = "";
}

function submitassetform()
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
    if (checkClientAsset())
    {
        if (document.clientForm.doView)
            document.clientForm.doView.value = "no";
        if (document.clientForm.doCancel)
            document.clientForm.doCancel.value = "no";
        document.clientForm.doSaveAsset.value = "yes";
        document.clientForm.action = "../client/ClientAction.do";
        document.clientForm.submit();
    }
}

function checkClientAsset()
{
    if (trim(document.clientForm.name.value) == "")
    {
        Swal.fire({
            title: "Please enter name.",
            didClose: () => {
                document.clientForm.name.focus();
            }
        })
        return false;
    }
    if (validname(document.clientForm.name) == false)
    {
        return false;
    }
    
    if (trim(document.clientForm.countryId.value) == "-1")
    {
        Swal.fire({
            title: "Please select location.",
            didClose: () => {
                document.clientForm.countryId.focus();
            }
        })
        return false;
    }

    if (trim(document.clientForm.assettypeId.value) == "-1")
    {
        Swal.fire({
            title: "Please select asset type.",
            didClose: () => {
                document.clientForm.assettypeId.focus();
            }
        })
        return false;
    }

    if (document.clientForm.currencyId.value == "-1")
    {
        Swal.fire({
            title: "Please select currency.",
            didClose: () => {
                document.clientForm.currencyId.focus();
            }
        })
        return false;
    }

    if (trim(document.clientForm.helpno.value) == "")
    {
        Swal.fire({
            title: "Please enter help number.",
            didClose: () => {
                document.clientForm.helpno.focus();
            }
        })
        return false;
    }
    if (validnum(document.clientForm.helpno) == false)
    {
        return false;
    }

    if (document.clientForm.helpemail.value == "")
    {
        Swal.fire({
            title: "Please enter help email.",
            didClose: () => {
                document.clientForm.helpemail.focus();
            }
        })
        return false;
    }
    if (checkEmailAddress(document.clientForm.helpemail) == false)
    {
        Swal.fire({
            title: "Please enter valid help email.",
            didClose: () => {
                document.clientForm.helpemail.focus();
            }
        })
        return false;
    }

    if (trim(document.clientForm.ratetype.value) == "")
    {
        Swal.fire({
            title: "Please select rate type.",
            didClose: () => {
                document.clientForm.ratetype.focus();
            }
        })
        return false;
    }
    if (trim(document.clientForm.crewrota.value) == "2" && trim(document.clientForm.startrota.value) == "2")
    {
        Swal.fire({
            title: "For Crew Rota *No* you can't select Start Rota *Automatic*.",
            didClose: () => {
                document.clientForm.startrota.focus();
            }
        })
        return false;
    }
    if (Number(document.clientForm.allowance.value) > 100)
    {
        Swal.fire({
            title: "Travel Allowance must be between 0-100.",
            didClose: () => {
                document.clientForm.allowance.focus();
            }
        })
        return false;
    }
    return true;
}

function deleteAssetForm(clientassetid, status, id)
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
            document.clientForm.doCancel.value = "no";
            document.clientForm.doSaveAsset.value = "no";
            document.clientForm.doModifyAsset.value = "no";
            document.clientForm.doDeleteAsset.value = "yes";
            document.clientForm.status.value = status;
            document.clientForm.clientassetId.value = clientassetid;
            document.clientForm.action = "../client/ClientAction.do";
            document.clientForm.submit();
        } else
        {
            if (document.getElementById("flexSwitchCheckDefault_" + id).checked == true)
                document.getElementById("flexSwitchCheckDefault_" + id).checked = false;
            else
                document.getElementById("flexSwitchCheckDefault_" + id).checked = true;
        }
    })
}

function modifyAssetForm(id)
{
    document.clientForm.doCancel.value = "no";
    document.clientForm.doSaveAsset.value = "no";
    document.clientForm.doDeleteAsset.value = "no";
    document.clientForm.doModifyAsset.value = "yes";
    document.clientForm.clientassetId.value = id;
    document.clientForm.action = "../client/ClientAction.do";
    document.clientForm.submit();
}

function viewimg(clientassetId)
{
    var url = "../ajax/client/getimg.jsp";
    var httploc = getHTTPObject();
    var getstr = "clientassetId=" + clientassetId;
    httploc.open("POST", url, true);
    httploc.onreadystatechange = function ()
    {
        if (httploc.readyState == 4)
        {
            if (httploc.status == 200)
            {
                var response = httploc.responseText;
                document.getElementById('viewfilesdiv').innerHTML = '';
                document.getElementById('viewfilesdiv').innerHTML = response;

                $("#iframe").on("load", function () {
                    let head = $("#iframe").contents().find("head");
                    let css = '<style>img{margin: 0px auto; max-width:-webkit-fill-available;}</style>';
                    $(head).append(css);
                });

            }
        }
    };
    httploc.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    httploc.setRequestHeader("Content-length", getstr.length);
    httploc.setRequestHeader("Connection", "close");
    httploc.send(getstr);
    document.getElementById('viewfilesdiv').innerHTML = "<div><img src='../assets/images/loading.gif' align='absmiddle'/>Please wait. Loading...</div>";
}

function setClass(tp)
{
    document.getElementById("upload_link" + tp).className = "uploaded_img";
}

function exporttoexcel()
{
    document.clientForm.action = "../client/ClientExportAction.do";
    document.clientForm.submit();
}

function delpic(clientassetpicId, clientassetId)
{
    var s = "<span>File will be <b>deleted.<b></span>";
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
            var url = "../ajax/client/delimg.jsp";
            var httploc = getHTTPObject();
            var getstr = "clientassetpicId=" + clientassetpicId;
            httploc.open("POST", url, true);
            httploc.onreadystatechange = function ()
            {
                if (httploc.readyState == 4)
                {
                    if (httploc.status == 200)
                    {
                        var response = trim(httploc.responseText);
                        if (response == "Yes")
                        {
                            viewimg(clientassetId);
                        }
                    }
                }
            };
            httploc.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
            httploc.setRequestHeader("Content-length", getstr.length);
            httploc.setRequestHeader("Connection", "close");
            httploc.send(getstr);
            document.getElementById('viewfilesdiv').innerHTML = "<div><img src='../assets/images/loading.gif' align='absmiddle'/>Please wait. Loading...</div>";
        }
    })
}

function setIframe(uval)
{
    var url_v = "", classname = "";
    if (uval.includes(".doc") || uval.includes(".docx"))
    {
        url_v = "https://docs.google.com/gview?url=" + uval + "&embedded=true";
        classname = "doc_mode";
    } else if (uval.includes(".pdf"))
    {
        url_v = uval;
        classname = "pdf_mode";
    } else
    {
        url_v = uval;
        classname = "img_mode";
    }
    window.top.$('#iframe').attr('src', 'about:blank');
    setTimeout(function () {
        window.top.$('#iframe').attr('src', url_v);
        document.getElementById("iframe").className = classname;
    }, 1000);
}

function setUsers()
{
    var url = "../ajax/client/ocsusers.jsp";
    var httploc = getHTTPObject();
    var getstr = "";
    httploc.open("POST", url, true);
    httploc.onreadystatechange = function ()
    {
        if (httploc.readyState == 4)
        {
            if (httploc.status == 200)
            {
                var response = httploc.responseText;
                document.getElementById("ocsuserddl").innerHTML = '';
                document.getElementById("ocsuserddl").innerHTML = response;
            }
        }
    };
    httploc.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    httploc.setRequestHeader("Content-length", getstr.length);
    httploc.setRequestHeader("Connection", "close");
    httploc.send(getstr);
}

function setports()
{
    var url = "../ajax/client/setports.jsp";
    var httploc = getHTTPObject();
    var countryId = document.clientForm.countryId.value;
    var getstr = "countryId=" + countryId;
    httploc.open("POST", url, true);
    httploc.onreadystatechange = function ()
    {
        if (httploc.readyState == 4)
        {
            if (httploc.status == 200)
            {
                var response = httploc.responseText;
                document.getElementById("setportsddl").innerHTML = '';
                document.getElementById("setportsddl").innerHTML = response;
            }
        }
    };
    httploc.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    httploc.setRequestHeader("Content-length", getstr.length);
    httploc.setRequestHeader("Connection", "close");
    httploc.send(getstr);
}

function setpositionmodel(clientassetid, assettypeId)
{
    var url = "../ajax/client/setpositionmodel.jsp";
    var httploc = getHTTPObject();
    var getstr = "clientassetid=" + clientassetid;
    getstr += "&assettypeId=" + assettypeId;
    httploc.open("POST", url, true);
    httploc.onreadystatechange = function ()
    {
        if (httploc.readyState == 4)
        {
            if (httploc.status == 200)
            {
                var response = trim(httploc.responseText);
                document.getElementById("positionmodel").innerHTML = response;
                $('#positionIds').multiselect({
                    includeSelectAllOption: true,
                    dropUp: true,
                    nonSelectedText: '- Select -',
                    maxHeight: 200,
                    enableFiltering: true,
                    enableCaseInsensitiveFiltering: true,
                    buttonWidth: '100%'
                });
            }
        }
    };
    httploc.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    httploc.setRequestHeader("Content-length", getstr.length);
    httploc.setRequestHeader("Connection", "close");
    httploc.send(getstr);
}

function addtoclientassetposition(clientassetId, assettypeId)
{
    var positionids = $("#positionIds").val();
    if (positionids != "" && positionids != null) {
        document.getElementById('godiv').innerHTML = "<img src='../assets/images/loading.gif' align='absmiddle' />";
        var url = "../ajax/client/addclientassetposition.jsp";
        var httploc = getHTTPObject();

        var getstr = "";
        getstr += "clientassetId=" + clientassetId;
        getstr += "&positionids=" + positionids;
        httploc.open("POST", url, true);
        httploc.onreadystatechange = function ()
        {
            if (httploc.readyState == 4)
            {
                if (httploc.status == 200)
                {
                    var response = trim(httploc.responseText);
                    if (response == "yes")
                    {
                        setpositionmodel(clientassetId, assettypeId); //call your method of modal and pass clientassetId
                        updateclientassetlist();
                        document.getElementById('godiv').innerHTML = "<a href=\"javascript: addtoclientassetposition('" + clientassetId + "', '" + assettypeId + "');\" class='save_page mt_10 pull_right'><img src=\"../assets/images/save.png\"> Save</a>";
                    }
                }
            }
        };
        httploc.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
        httploc.setRequestHeader("Content-length", getstr.length);
        httploc.setRequestHeader("Connection", "close");
        httploc.send(getstr);
    } else {
        Swal.fire('Please select atleast one position.');
    }
}

function deleteclientassetposition(clientassetpositionId, clientassetId, id, assettypeId)
{
    var s = "";
    s = "This will remove the record.";
    Swal.fire({
        title: 'Are you sure?',
        text: s,
        confirmButtonColor: '#3085d6',
        cancelButtonColor: '#d33',
        confirmButtonText: 'Confirm',
        showCloseButton: true,
        allowOutsideClick: false,
        allowEscapeKey: false
    }).then((result) => {
        if (result.isConfirmed)
        {
            var url = "../ajax/client/deleteclientassetposition.jsp";
            var httploc = getHTTPObject();
            var getstr = "clientassetpositionId=" + clientassetpositionId;
            httploc.open("POST", url, true);
            httploc.onreadystatechange = function ()
            {
                if (httploc.readyState == 4)
                {
                    if (httploc.status == 200)
                    {
                        var response = trim(httploc.responseText);
                        if (response == "yes")
                        {
                            Swal.fire("Position removed");
                            setpositionmodel(clientassetId, assettypeId); //call your method of modal and pass clientassetId
                            updateclientassetlist();
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
            if (document.getElementById("flexSwitchCheckDefaultchild_" + id).checked == true)
                document.getElementById("flexSwitchCheckDefaultchild_" + id).checked = false;
            else
                document.getElementById("flexSwitchCheckDefaultchild_" + id).checked = true;
        }

    })
}

function setdeptmodel(clientassetId, assettypeId, srno)
{
    if (Number(srno) > 0)
        document.getElementById("srnoModal").value = srno;
    var url = "../ajax/client/setdeptmodal.jsp";
    var httploc = getHTTPObject();
    var getstr = "clientassetId=" + clientassetId;
    getstr += "&assettypeId=" + assettypeId;
    getstr += "&srno=" + srno;
    httploc.open("POST", url, true);
    httploc.onreadystatechange = function ()
    {
        if (httploc.readyState == 4)
        {
            if (httploc.status == 200)
            {
                var response = trim(httploc.responseText);
                document.getElementById("deptmodal").innerHTML = response;
            }
        }
    };
    httploc.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    httploc.setRequestHeader("Content-length", getstr.length);
    httploc.setRequestHeader("Connection", "close");
    httploc.send(getstr);
}

function deletedept(deptId, clientassetId, assettypeId)
{
    var s = "<span>The selected department will be deattached from respective positions.</b></span>";
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
            var url = "../ajax/client/updatedept.jsp";
            var getstr = "";
            var httploc = getHTTPObject();
            getstr += "clientassetId=" + clientassetId;
            getstr += "&deptId=" + deptId;
            httploc.open("POST", url, true);
            httploc.onreadystatechange = function ()
            {
                if (httploc.readyState == 4)
                {
                    if (httploc.status == 200)
                    {
                        var response = trim(httploc.responseText);
                        if (response == "Yes")
                        {
                            setdeptmodel(clientassetId, assettypeId, -1);
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

function setpost(clientassetId, assettypeId, deptId)
{
    var url = "../ajax/client/setposmodal.jsp";
    var httploc = getHTTPObject();
    var getstr = "clientassetId=" + clientassetId;
    getstr += "&assettypeId=" + assettypeId;
    getstr += "&deptId=" + deptId;
    httploc.open("POST", url, true);
    httploc.onreadystatechange = function ()
    {
        if (httploc.readyState == 4)
        {
            if (httploc.status == 200)
            {
                var response = trim(httploc.responseText);
                document.getElementById("positiondeptid").innerHTML = response;
            }
        }
    };
    httploc.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    httploc.setRequestHeader("Content-length", getstr.length);
    httploc.setRequestHeader("Connection", "close");
    httploc.send(getstr);
}

function setdepttopos(capId, clientassetId, assettypeId, pdeptId, setval)
{
    var s = "";
    if (eval(setval) == 2)
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
            var url = "../ajax/client/updateposision.jsp";
            var httploc = getHTTPObject();
            var getstr = "clientassetId=" + clientassetId;
            getstr += "&positionId=" + capId;
            getstr += "&deptId=" + pdeptId;
            getstr += "&setval=" + setval;
            httploc.open("POST", url, true);
            httploc.onreadystatechange = function ()
            {
                if (httploc.readyState == 4)
                {
                    if (httploc.status == 200)
                    {
                        var response = trim(httploc.responseText);
                        if (response == "Yes")
                        {
                            setpost(clientassetId, assettypeId, pdeptId);
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
            if (document.getElementById("flexSwitchCheckDefaultchild_dept_" + capId).checked == true)
                document.getElementById("flexSwitchCheckDefaultchild_dept_" + capId).checked = false;
            else
                document.getElementById("flexSwitchCheckDefaultchild_dept_" + capId).checked = true;
        }
    })
}

function setdeptcount()
{
    var url = "../ajax/client/setdeptcount.jsp";
    var httploc = getHTTPObject();
    var getstr = "clientassetId=" + document.getElementById("assetIdModal").value;
    httploc.open("POST", url, true);
    httploc.onreadystatechange = function ()
    {
        if (httploc.readyState == 4)
        {
            if (httploc.status == 200)
            {
                var response = trim(httploc.responseText);
                var srno = document.getElementById("srnoModal").value;
                document.getElementById("deptspan_" + srno).innerHTML = response;
            }
        }
    };
    httploc.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    httploc.setRequestHeader("Content-length", getstr.length);
    httploc.setRequestHeader("Connection", "close");
    httploc.send(getstr);
}

function checkinstr(str1, str2)
{
    if(str1 != "" && str2 != "")
    {
        var arr1 = new Array();
        var arr2 = new Array();
        arr1 = str1.split(",");
        arr2 = str2.split(",");
        for(i = 0; i < arr1.length; i++)
        {
            for(j = 0; j < arr2.lenght; j++)
            {
                if(arr1[i] == arr2[j])
                {
                    return false;                    
                }
            }
        }
    }
    return true;
}