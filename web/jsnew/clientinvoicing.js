function resetFilter()
{
    document.forms[0].clientIdIndex.value = "-1";
    document.forms[0].assetIdIndex.value = "-1";
    document.forms[0].typeIndex.value = "-1";
    setAssetDDL();
    searchFormAjax('s', '-1');
}

function showDetail(id)
{
    document.clientinvoicingForm.doView.value = "yes";
    document.clientinvoicingForm.clientassetId.value = id;
    document.clientinvoicingForm.action = "../clientinvoicing/ClientinvoicingAction.do";
    document.clientinvoicingForm.submit();
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
    if (1)
    {
        var url = "../ajax/clientinvoicing/getinfo.jsp";
        var httploc = getHTTPObject();
        var getstr = "";
        var next_value = escape(document.clientinvoicingForm.nextValue.value);
        var typeIndex = document.clientinvoicingForm.typeIndex.value;
        var assetIdIndex = document.clientinvoicingForm.assetIdIndex.value;
        var clientIdIndex = document.clientinvoicingForm.clientIdIndex.value;
        getstr += "nextValue=" + next_value;
        getstr += "&next=" + v;
        getstr += "&clientIdIndex=" + clientIdIndex;
        getstr += "&assetIdIndex=" + assetIdIndex;
        getstr += "&typeIndex=" + typeIndex;
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

function searchFormAjaxTS(v, v1)
{
    var url = "../ajax/clientinvoicing/getinfots.jsp";
    var httploc = getHTTPObject();
    var getstr = "";
    var next_value = escape(document.clientinvoicingForm.nextValue.value);
    var search_value = escape(document.clientinvoicingForm.search.value);
    var invoicestatusIndex = document.clientinvoicingForm.invoicestatusIndex.value;
    var month = document.clientinvoicingForm.month.value;
    var yearId = document.clientinvoicingForm.yearId.value;
    var clientassetId = document.clientinvoicingForm.clientassetId.value;
    getstr += "nextValue=" + next_value;
    getstr += "&next=" + v;
    getstr += "&search=" + search_value;
    getstr += "&invoicestatusIndex=" + invoicestatusIndex;
    getstr += "&month=" + month;
    getstr += "&yearId=" + yearId;
    getstr += "&clientassetId=" + clientassetId;
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
    if (document.clientinvoicingForm.doView)
        document.clientinvoicingForm.doView.value = "no";
    if (document.clientinvoicingForm.doCancel)
        document.clientinvoicingForm.doCancel.value = "yes";
    if (document.clientinvoicingForm.doSave)
        document.clientinvoicingForm.doSave.value = "no";
    document.clientinvoicingForm.action = "../clientinvoicing/ClientinvoicingAction.do";
    document.clientinvoicingForm.submit();
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
    var url_sort = "../ajax/clientinvoicing/sort.jsp";
    var getstr = "";
    var nextValue = 0;
    if (document.clientinvoicingForm.nextValue)
        nextValue = document.clientinvoicingForm.nextValue.value;
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

function sortFormTS(colid, updown)
{
    for (i = 1; i <= 6; i++)
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
    var url_sort = "../ajax/clientinvoicing/sortts.jsp";
    var getstr = "";
    var nextValue = 0;
    if (document.clientinvoicingForm.nextValue)
        nextValue = document.clientinvoicingForm.nextValue.value;
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
    document.clientinvoicingForm.doModify.value = "no";
    document.clientinvoicingForm.doView.value = "no";
    document.clientinvoicingForm.doAdd.value = "yes";
    document.clientinvoicingForm.action = "../clientinvoicing/ClientinvoicingAction.do";
    document.clientinvoicingForm.submit();
}

function modifyForm(id)
{
    document.clientinvoicingForm.doModify.value = "yes";
    document.clientinvoicingForm.doView.value = "no";
    document.clientinvoicingForm.doAdd.value = "no";
    document.clientinvoicingForm.clientinvoicingId.value = id;
    document.clientinvoicingForm.action = "../clientinvoicing/ClientinvoicingAction.do";
    document.clientinvoicingForm.submit();
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
    if (checkClientinvoicing())
    {
        document.getElementById('submitdiv').innerHTML = "<img src='../assets/images/loading.gif' align='absmiddle' />";
        document.clientinvoicingForm.doSave.value = "yes";
        document.clientinvoicingForm.doCancel.value = "no";
        document.clientinvoicingForm.action = "../clientinvoicing/ClientinvoicingAction.do";
        document.clientinvoicingForm.submit();
    }
}

function checkClientinvoicing()
{
    if (trim(document.clientinvoicingForm.name.value) == "")
    {
        Swal.fire({
            title: "Please enter name.",
            didClose: () => {
                document.clientinvoicingForm.name.focus();
            }
        })
        return false;
    }
    if (validname(document.clientinvoicingForm.name) == false)
    {
        return false;
    }
    return true;
}

function resetForm()
{
    document.clientinvoicingForm.reset();
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
            var url = "../ajax/clientinvoicing/getinfo.jsp";
            var getstr = "";
            var httploc = getHTTPObject();
            var next_value = escape(document.clientinvoicingForm.nextValue.value);
            var next_del = "-1";
            if (document.clientinvoicingForm.nextDel)
                next_del = escape(document.clientinvoicingForm.nextDel.value);
            var search_value = escape(document.clientinvoicingForm.search.value);
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

function exporttoexcel(type)
{
    document.clientinvoicingForm.type.value = type;
    document.clientinvoicingForm.action = "../clientinvoicing/ClientinvoicingExportAction.do";
    document.clientinvoicingForm.submit();
}

function setAssetDDL()
{
    var url = "../ajax/clientinvoicing/getasset.jsp";
    document.getElementById("assetIdIndex").value = '-1';
    var httploc = getHTTPObject();
    var getstr = "clientIdIndex=" + document.clientinvoicingForm.clientIdIndex.value + "&from=asset";
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

function doGenerateinvoice(id)
{
    document.clientinvoicingForm.doGenerate.value = "yes";
    document.clientinvoicingForm.timesheetId.value = id;
    document.clientinvoicingForm.action = "../clientinvoicing/ClientinvoicingAction.do";
    document.clientinvoicingForm.submit();
}

function doPayment(id)
{
    document.clientinvoicingForm.doPayment.value = "yes";
    document.clientinvoicingForm.doGenerate.value = "no";
    document.clientinvoicingForm.doModify.value = "no";
    document.clientinvoicingForm.doView.value = "no";
    document.clientinvoicingForm.doAdd.value = "no";
    document.clientinvoicingForm.timesheetId.value = id;
    document.clientinvoicingForm.action = "../clientinvoicing/ClientinvoicingAction.do";
    document.clientinvoicingForm.submit();
}

function doPaymentView(id)
{
    document.clientinvoicingForm.doViewPayment.value = "yes";
    document.clientinvoicingForm.doPayment.value = "no";
    document.clientinvoicingForm.doGenerate.value = "no";
    document.clientinvoicingForm.doModify.value = "no";
    document.clientinvoicingForm.doView.value = "no";
    document.clientinvoicingForm.doAdd.value = "no";
    document.clientinvoicingForm.timesheetId.value = id;
    document.clientinvoicingForm.action = "../clientinvoicing/ClientinvoicingAction.do";
    document.clientinvoicingForm.submit();
}

function generatepdf()
{    
    if(document.clientinvoicingForm.taxName.value != "")
    {
        if (Number(document.clientinvoicingForm.percentage.value) <= 0)
        {
            Swal.fire({
                title: "Please enter percentage.",
                didClose: () => {
                    document.clientinvoicingForm.percentage.focus();
                }
            })
        }
    }
    
    if (Number(document.clientinvoicingForm.percentage.value) > 0)
    {
        if(document.clientinvoicingForm.taxName.value == "")
        {
            Swal.fire({
                title: "Please enter tax name.",
                didClose: () => {
                    document.clientinvoicingForm.taxName.focus();
                }
            })
        }
    }
    
    if(document.clientinvoicingForm.taxName1.value != "")
    {
        if (Number(document.clientinvoicingForm.percentage1.value) <= 0)
        {
            Swal.fire({
                title: "Please enter percentage.",
                didClose: () => {
                    document.clientinvoicingForm.percentage1.focus();
                }
            })
        }
    }
     
    if (Number(document.clientinvoicingForm.percentage1.value) > 0)
    {
        if(document.clientinvoicingForm.taxName1.value == "")
        {
            Swal.fire({
                title: "Please enter tax name.",
                didClose: () => {
                    document.clientinvoicingForm.taxName1.focus();
                }
            })
        }
    }
    
    if(document.clientinvoicingForm.taxName2.value != "")
    {
        if (Number(document.clientinvoicingForm.percentage2.value) <= 0)
        {
            Swal.fire({
                title: "Please enter percentage.",
                didClose: () => {
                    document.clientinvoicingForm.percentage2.focus();
                }
            })
        }
    }
    
    if (Number(document.clientinvoicingForm.percentage2.value) > 0)
    {
        if(document.clientinvoicingForm.taxName2.value == "")
        {
            Swal.fire({
                title: "Please enter tax name.",
                didClose: () => {
                    document.clientinvoicingForm.taxName2.focus();
                }
            })
        }
    }
    
    if(document.clientinvoicingForm.taxName3.value != "")
    {
        if (Number(document.clientinvoicingForm.percentage3.value) <= 0)
        {
            Swal.fire({
                title: "Please enter percentage.",
                didClose: () => {
                    document.clientinvoicingForm.percentage3.focus();
                }
            })
        }
    }
    
    if (Number(document.clientinvoicingForm.percentage3.value) > 0)
    {
        if(document.clientinvoicingForm.taxName3.value == "")
        {
            Swal.fire({
                title: "Please enter tax name.",
                didClose: () => {
                    document.clientinvoicingForm.taxName3.focus();
                }
            })
        }
    }
    
     if(document.clientinvoicingForm.taxName4.value != "")
    {
        if (Number(document.clientinvoicingForm.percentage4.value) <= 0)
        {
            Swal.fire({
                title: "Please enter percentage.",
                didClose: () => {
                    document.clientinvoicingForm.percentage4.focus();
                }
            })
        }
    }
    
    if (Number(document.clientinvoicingForm.percentage4.value) > 0)
    {
        if(document.clientinvoicingForm.taxName4.value == "")
        {
            Swal.fire({
                title: "Please enter tax name.",
                didClose: () => {
                    document.clientinvoicingForm.taxName4.focus();
                }
            })
        }
    }
   
   if (Number(document.clientinvoicingForm.invoicetempId.value) <= 0)
    {
        Swal.fire({
            title: "Please select template.",
            didClose: () => {
                document.clientinvoicingForm.invoicetempId.focus();
            }
        })
    }
    else
    {
        document.getElementById("generatepdfdiv").innerHTML = "<img src='../assets/images/loading.gif' align='absmiddle' />";
        var url = "../ajax/clientinvoicing/getinvoicepdffile.jsp";
        var httploc = getHTTPObject();
        var getstr = "";
        getstr += "invoicetempId=" + document.clientinvoicingForm.invoicetempId.value;
        getstr += "&timesheetId=" + document.clientinvoicingForm.timesheetId.value;
        getstr += "&invoiceno=" + document.clientinvoicingForm.invoiceno.value;
        getstr += "&invoicedate=" + document.clientinvoicingForm.invoicedate.value;
        getstr += "&bankId=" + document.clientinvoicingForm.bankId.value;
        getstr += "&amount1=" + document.clientinvoicingForm.amount1.value;
        getstr += "&amount2=" + document.clientinvoicingForm.amount2.value;
        getstr += "&amount3=" + document.clientinvoicingForm.amount3.value;
        getstr += "&taxName=" + document.clientinvoicingForm.taxName.value;
        getstr += "&percentage=" + document.clientinvoicingForm.percentage.value;
        getstr += "&taxName1=" + document.clientinvoicingForm.taxName1.value;
        getstr += "&percentage1=" + document.clientinvoicingForm.percentage1.value;
        getstr += "&taxName2=" + document.clientinvoicingForm.taxName2.value;
        getstr += "&percentage2=" + document.clientinvoicingForm.percentage2.value;
        getstr += "&taxName3=" + document.clientinvoicingForm.taxName3.value;
        getstr += "&percentage3=" + document.clientinvoicingForm.percentage3.value;
        getstr += "&taxName4=" + document.clientinvoicingForm.taxName4.value;
        getstr += "&percentage4=" + document.clientinvoicingForm.percentage4.value;
        httploc.open("POST", url, true);
        httploc.onreadystatechange = function ()
        {
            if (httploc.readyState == 4)
            {
                if (httploc.status == 200)
                {
                    var response = httploc.responseText;
                    setIframe(response);
                    document.getElementById("generatepdfdiv").innerHTML = '<a href="javascript: generatepdf();" class="gen_btn"> Generate</a>';
                }
            }
        };
        httploc.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
        httploc.setRequestHeader("Content-length", getstr.length);
        httploc.setRequestHeader("Connection", "close");
        httploc.send(getstr);
    }
}

function setIframe(uval)
{
    var url_v = "", classname = "";
    if (uval.includes(".doc") || uval.includes(".docx") || uval.includes("wordprocessingml.document"))
    {
        url_v = "https://docs.google.com/gview?url=" + uval + "&embedded=true";
        classname = "doc_mode";
    } else if (uval.includes(".pdf"))
    {
        url_v = uval + "#toolbar=0&page=1&view=fitH,100";
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
        document.getElementById("diframe").href = uval;
    }, 1000);

    $("#iframe").on("load", function () {
        let head = $("#iframe").contents().find("head");
        let css = '<style>img{margin: 0px auto; max-width:-webkit-fill-available;}</style>';
        $(head).append(css);
    });
}

function getModel(timesheetId, clientId)
{
    var url = "../ajax/clientinvoicing/getmailmodaldetails.jsp";
    var httploc = getHTTPObject();
    var getstr = "";
    getstr += "timesheetId=" + timesheetId;
    getstr += "&clientId=" + clientId;
    httploc.open("POST", url, true);
    httploc.onreadystatechange = function ()
    {
        if (httploc.readyState == 4)
        {
            if (httploc.status == 200)
            {
                var response = httploc.responseText;
                var arr = new Array();
                arr = response.split('#@#');
                v1 = arr[0];
                v2 = arr[1];
                document.getElementById('mailmodal').innerHTML = '';
                document.getElementById('mailmodal').innerHTML = trim(v1);

                $(function () {
                    $("#upload_link_2").on('click', function (e) {
                        e.preventDefault();
                        $("#upload2:hidden").trigger('click');
                    });
                });

            }
        }
    };
    httploc.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    httploc.setRequestHeader("Content-length", getstr.length);
    httploc.setRequestHeader("Connection", "close");
    httploc.send(getstr);
    document.getElementById('mailmodal').innerHTML = "<div><img src='../assets/images/loading.gif' align='absmiddle'/>Please wait. Loading...</div>";
}

function submitmailForm()
{
    document.getElementById("sendmaildiv").innerHTML = "<img src='../assets/images/loading.gif' align='absmiddle' />";
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
    if (checkMail())
    {
        document.clientinvoicingForm.doMailInvoice.value = "yes";
        document.clientinvoicingForm.action = "../clientinvoicing/ClientinvoicingAction.do";
        document.clientinvoicingForm.submit();
    }
}

function setClass(tp)
{
    document.getElementById("upload_link_" + tp).className = "attache_btn attache_btn_white uploaded_img";
}

function checkMail()
{
    var toval = document.forms[0].toval.value;
    if (toval == "")
    {
        Swal.fire({
            title: "Please enter To email address.",
            didClose: () => {
                document.forms[0].toval.focus();
            }
        })
        return false;
    }
    if (toval != "")
    {
        var arr = toval.split(',');
        var len = arr.length;
        for (var i = 0; i < len; i++)
        {
            if (checkEmailAddressVal(trim(arr[i])) == false)
            {
                Swal.fire({
                    title: "please enter valid  email address.",
                    didClose: () => {
                        document.forms[0].toval.focus();
                    }
                })
                return false;
            }
        }
    }
    var ccval = document.forms[0].ccval.value;
    if (ccval != "")
    {
        var arr = ccval.split(',');
        var len = arr.length;
        for (var i = 0; i < len; i++)
        {
            if (checkEmailAddressVal(trim(arr[i])) == false)
            {
                Swal.fire({
                    title: "please enter valid email address.",
                    didClose: () => {
                        document.forms[0].ccval.focus();
                    }
                })
                return false;
            }
        }
    }
    var bccval = document.forms[0].bccval.value;

    if (bccval != "")
    {
        var arr = bccval.split(',');
        var len = arr.length;
        for (var i = 0; i < len; i++)
        {
            if (checkEmailAddressVal(trim(arr[i])) == false)
            {
                Swal.fire({
                    title: "please enter valid email address.",
                    didClose: () => {
                        document.forms[0].bccval.focus();
                    }
                })
                return false;
            }
        }
    }
    if (trim(document.forms[0].subject.value) == "")
    {
        Swal.fire({
            title: "Please enter subject.",
            didClose: () => {
                document.forms[0].subject.focus();
            }
        })
        return false;
    }
    if (validdesc(document.forms[0].subject) == false)
    {
        return false;
    }
    if (trim(document.forms[0].description.value) == "")
    {
        Swal.fire({
            title: "Please enter Email Body.",
            didClose: () => {
                document.forms[0].description.focus();
            }
        })
        return false;
    }
    if (validdesc(document.forms[0].description) == false)
    {
        return false;
    }
    return true;
}

function submitPaymentadviceForm()
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
    if (checkPaymentRecieved())
    {
        document.clientinvoicingForm.doSavePayment.value = "yes";
        document.clientinvoicingForm.action = "../clientinvoicing/ClientinvoicingAction.do";
        document.clientinvoicingForm.submit();
    }
}

function checkPaymentRecieved()
{
    if (trim(document.forms[0].paymentremark.value) == "")
    {
        Swal.fire({
            title: "Please enter remarks.",
            didClose: () => {
                document.forms[0].subject.focus();
            }
        })
        return false;
    }
    if (validdesc(document.forms[0].paymentremark) == false)
    {
        return false;
    }
    return true;
}

function getviewmailmodal(maillogId)
{
    var url = "../ajax/clientinvoicing/getmailmodaldetailsview.jsp";
    var httploc = getHTTPObject();
    var getstr = "";
    getstr += "maillogId=" + maillogId;
    httploc.open("POST", url, true);
    httploc.onreadystatechange = function ()
    {
        if (httploc.readyState == 4)
        {
            if (httploc.status == 200)
            {
                var response = httploc.responseText;
                var arr = new Array();
                arr = response.split('#@#');
                v1 = arr[0];
                v2 = arr[1];
                document.getElementById('mailmodal').innerHTML = '';
                document.getElementById('mailmodal').innerHTML = trim(v1);
            }
        }
    };
    httploc.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    httploc.setRequestHeader("Content-length", getstr.length);
    httploc.setRequestHeader("Connection", "close");
    httploc.send(getstr);
    document.getElementById('mailmodal').innerHTML = "<div><img src='../assets/images/loading.gif' align='absmiddle'/>Please wait. Loading...</div>";
}

function seturl(url)
{
    $('#view_pdf').modal('show');
    var url_v = "", classname = "";
    if (url.includes(".pdf"))
    {
        classname = "pdf_mode";
    }
    window.top.$('#iframe').attr('src', 'about:blank');
    setTimeout(function () {
        window.top.$('#iframe').attr('src', url);
        document.getElementById("iframe").className = classname;
        document.getElementById("diframe").href = url;
    }, 1000);

    $("#iframe").on("load", function () {
        let head = $("#iframe").contents().find("head");
        let css = '<style>img{margin: 0px auto; max-width:-webkit-fill-available;}</style>';
        $(head).append(css);
    });
}

function getPaySlip()
{
    var url = "../ajax/clientinvoicing/getpayslip.jsp";
    var httploc = getHTTPObject();
    var getstr = "clientassetId="+document.forms[0].clientassetId.value;
    httploc.open("POST", url, true);
    httploc.onreadystatechange = function ()
    {
        if (httploc.readyState == 4)
        {
            if (httploc.status == 200)
            {
                var response = httploc.responseText;
                document.getElementById('dpayslip').innerHTML = '';
                document.getElementById('dpayslip').innerHTML = response;
                $("#pay_slip_modal").modal("show");
                $("#upload_link_2").on('click', function (e) {
                    e.preventDefault();
                    $("#upload2:hidden").trigger('click');
                });
            }
        }
    };
    httploc.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    httploc.setRequestHeader("Content-length", getstr.length);
    httploc.setRequestHeader("Connection", "close");
    httploc.send(getstr);
    document.getElementById('dpayslip').innerHTML = "<div><img src='../assets/images/loading.gif' align='absmiddle'/>Please wait. Loading...</div>";
}

function checkpayslip() {
    if (document.clientinvoicingForm.upload2.value == "")
    {
        Swal.fire({
            title: "Please upload file.",
            didClose: () => {
                document.clientinvoicingForm.upload2.focus();
            }
        })
        return false;
    }
    if (!(document.clientinvoicingForm.upload2.value).match(/(\.(zip))$/i))
    {
        Swal.fire({
            title: "Only .zip are allowed.",
            didClose: () => {
                document.clientinvoicingForm.upload2.focus();
            }
        })
        return false;
    }
    if (document.clientinvoicingForm.upload2.value != "")
    {
        var input = document.clientinvoicingForm.upload2;
        if (input.files)
        {
            var file = input.files[0];
            if (file.size > (1024 * 1024 * 20))
            {
                Swal.fire({
                    title: "File size should not exceed 20 MB.",
                    didClose: () => {
                        document.clientinvoicingForm.upload2.focus();
                    }
                })
                return false;
            }
        }
    }
    return true;
}

function getSavePaySlip()
{
    if (checkpayslip()) 
    {
        document.clientinvoicingForm.doSavePaySlip.value = "yes";
        document.clientinvoicingForm.action = "../clientinvoicing/ClientinvoicingAction.do";
        document.clientinvoicingForm.submit();
    }
}

function setClass(tp)
{
    document.getElementById("upload_link_" + tp).className = "attache_btn attache_btn_white uploaded_img";
}