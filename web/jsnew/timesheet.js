function resetFilter()
{
    document.timesheetForm.clientIdIndex.value = "-1"
    document.timesheetForm.assetIdIndex.value = "-1"
    document.forms[0].typevalue.value = "-1";
    setAssetDDL();
    searchFormAjax('s', '-1');
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
    var url = "../ajax/timesheet/getinfo.jsp";
    var httploc = getHTTPObject();
    var getstr = "";
    var next_value = escape(document.timesheetForm.nextValue.value);
    var assetIdIndex = escape(document.timesheetForm.assetIdIndex.value);
    var clientIdIndex = escape(document.timesheetForm.clientIdIndex.value);
    var type = escape(document.timesheetForm.typevalue.value);
    getstr += "nextValue=" + next_value;
    getstr += "&next=" + v;
    getstr += "&type=" + type;
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

function goback()
{
    if (document.timesheetForm.doView)
        document.timesheetForm.doView.value = "no";
    if (document.timesheetForm.doSave)
        document.timesheetForm.doSave.value = "no";
    if (document.timesheetForm.doCancel)
        document.timesheetForm.doCancel.value = "yes";
    document.timesheetForm.action = "../timesheet/TimesheetAction.do";
    document.timesheetForm.submit();
}

function gobackpage()
{
    if (document.timesheetForm.doView)
        document.timesheetForm.doView.value = "yes"; 
    document.timesheetForm.action = "../timesheet/TimesheetAction.do";
    document.timesheetForm.submit();
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
    var url_sort = "../ajax/timesheet/sort.jsp";
    var getstr = "";
    var nextValue = 0;
    if (document.timesheetForm.nextValue)
        nextValue = document.timesheetForm.nextValue.value;
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
    document.timesheetForm.reset();
}

function exporttoexcel()
{
    document.timesheetForm.action = "../timesheet/TimesheetExportAction.do";
    document.timesheetForm.submit();
}

function exportDetails(typeId)
{
    document.timesheetForm.typeId.value = typeId;
    document.timesheetForm.action = "../timesheet/TimesheetDetailExport.do";
    document.timesheetForm.submit();
}

function setAssetDDL()
{
    var url = "../ajax/timesheet/getasset.jsp";
    document.getElementById("assetIdIndex").value = '-1';
    var httploc = getHTTPObject();
    var getstr = "clientIdIndex=" + document.timesheetForm.clientIdIndex.value + "&from=asset";
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

function showDetail(id)
{    
    document.forms[0].target = "_self";
    document.timesheetForm.doView.value = "yes";
    document.timesheetForm.assetId.value = id;
    document.timesheetForm.action = "../timesheet/TimesheetAction.do";
    document.timesheetForm.submit();
}

function showTimesheetDetail(id, rid)
{
    document.forms[0].target = "_self";
    document.timesheetForm.doView.value = "no";
    document.timesheetForm.doSentApproval.value = "no";
    document.timesheetForm.doGetDetails.value = "yes";
    document.timesheetForm.timesheetId.value = id;
    document.timesheetForm.repeatId.value = rid;
    document.timesheetForm.action = "../timesheet/TimesheetAction.do";
    document.timesheetForm.submit();
}

function deleteForm(id, rid)
{
    var s = "<span>The selected Timesheet will be <b>Deleted.</b></span>";
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
        document.timesheetForm.doDelete.value = "yes";
        document.timesheetForm.doView.value = "no";
        document.timesheetForm.doSearch.value = "no";
        document.timesheetForm.doSentApproval.value = "no";
        document.timesheetForm.doGetDetails.value = "no";
        document.timesheetForm.timesheetId.value = id;
        document.timesheetForm.repeatId.value = rid;
        document.timesheetForm.action = "../timesheet/TimesheetAction.do";
        document.timesheetForm.submit();
    }
    })
}

function createSheet()
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
    if (checkDate())
    {
        document.getElementById('submitdiv').innerHTML = "<img src='../assets/images/loading.gif' align='absmiddle' />";
        document.timesheetForm.doCreateSheet.value = "yes";
        document.timesheetForm.doDelete.value = "no";
        document.timesheetForm.doView.value = "no";
        document.timesheetForm.doSearch.value = "no";
        document.timesheetForm.doSentApproval.value = "no";
        document.timesheetForm.doGetDetails.value = "no";
        document.timesheetForm.action = "../timesheet/TimesheetAction.do";
        document.timesheetForm.submit();
    }
}

function checkDate()
{
    if (document.timesheetForm.fromDate.value == "")
    {
         Swal.fire({
                title: "Please select from date.",
                didClose: () => {
                    document.timesheetForm.fromDate.value = "";
                }
            })
            return false;
    }
    if (comparisionTest(document.timesheetForm.fromDate.value, document.timesheetForm.currentDate.value) == false)
    {
        Swal.fire({
            title: "Please check from date .",
            didClose: () => {
                document.timesheetForm.fromDate.value = "";
            }
        })
        return false;
    }
    if (document.timesheetForm.toDate.value == "")
    {
         Swal.fire({
                title: "Please select to date.",
                didClose: () => {
                    document.timesheetForm.toDate.value = "";
                }
            })
            return false;
    }
    if (comparisionTest(document.timesheetForm.fromDate.value, document.timesheetForm.toDate.value) == false)
    {
        Swal.fire({
            title: "Please check to date .",
            didClose: () => {
                document.timesheetForm.toDate.value = "";
            }
        })
        return false;
    }
    return true;
}

function searchTimesheet(v, v1)
{   
    var url = "../ajax/timesheet/getinfots.jsp";
    var httploc = getHTTPObject();
    var getstr = "";
    var next_tsvalue = escape(document.timesheetForm.nextTsValue.value);
    var search_value = escape(document.timesheetForm.search.value);
    var statusIndex = escape(document.timesheetForm.statusIndex.value);
    var month = escape(document.timesheetForm.month.value);
    var yearId = escape(document.timesheetForm.yearId.value);
    var clientassetId = escape(document.timesheetForm.assetId.value);
    var fromDateIndex = escape(document.timesheetForm.fromDateIndex.value);
    var toDateIndex = escape(document.timesheetForm.toDateIndex.value);
    getstr += "nextTsValue="+next_tsvalue;
    getstr += "&next="+v;
    getstr += "&statusIndex="+statusIndex;
    getstr += "&clientassetId="+clientassetId;
    getstr += "&month="+month;
    getstr += "&yearId="+yearId;
    getstr += "&search="+search_value;
    getstr += "&doDirect="+v1;
    getstr += "&fromDateIndex="+fromDateIndex;
    getstr += "&toDateIndex="+toDateIndex;
    httploc.open("POST", url, true);
    httploc.onreadystatechange = function()
    {
        if (httploc.readyState == 4)
        {
            if(httploc.status == 200)
            {
                var response = httploc.responseText;
                document.getElementById('ajax_ts').innerHTML = '';
                document.getElementById('ajax_ts').innerHTML = response;
            }
        }
    };
    httploc.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    httploc.setRequestHeader("Content-length", getstr.length);
    httploc.setRequestHeader("Connection", "close");
    httploc.send(getstr);
    document.getElementById('ajax_ts').innerHTML = "<div><img src='../assets/images/loading.gif' align='absmiddle'/>Please wait. Loading...</div>";    
}

function saveDraft()
{
    if(checktsdetail())
    {        
        document.timesheetForm.doView.value = "no";
        document.timesheetForm.doSaveDraft.value = "yes";    
        var id = document.timesheetForm.timesheetId.value;    
        document.timesheetForm.action = "../timesheet/TimesheetAction.do";
        document.timesheetForm.submit();
    }
}

function viewReason(timesheetId)
{
    $('#view_reason_modal').modal('show');
    var url = "../ajax/timesheet/viewreason.jsp";
    var httploc = getHTTPObject();
    var getstr = "";
    getstr += "timesheetId=" + timesheetId;
    httploc.open("POST", url, true);
    httploc.onreadystatechange = function ()
    {
        if (httploc.readyState == 4)
        {
            if (httploc.status == 200)
            {
                var response = httploc.responseText;
                document.getElementById('viewReasonDiv').innerHTML = '';
                document.getElementById('viewReasonDiv').innerHTML = response;
            }
        }
    };
    httploc.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    httploc.setRequestHeader("Content-length", getstr.length);
    httploc.setRequestHeader("Connection", "close");
    httploc.send(getstr);
}

function addReason(timesheetId)
{
    $('#request_timesheet_revision_modal').modal('show');
    var url = "../ajax/timesheet/addreason.jsp";
    var httploc = getHTTPObject();
    var getstr = "";
    getstr += "timesheetId=" + timesheetId;
    httploc.open("POST", url, true);
    httploc.onreadystatechange = function ()
    {
        if (httploc.readyState == 4)
        {
            if (httploc.status == 200)
            {
                var response = httploc.responseText;
                document.getElementById('addReasonDiv').innerHTML = '';
                document.getElementById('addReasonDiv').innerHTML = response;
            }
        }
    };
    httploc.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    httploc.setRequestHeader("Content-length", getstr.length);
    httploc.setRequestHeader("Connection", "close");
    httploc.send(getstr);
}

function appoveInvoice(timesheetId)
{
    $('#approve_timesheet_invoicing_modal').modal('show');
    var url = "../ajax/timesheet/approveinvoice.jsp";
    var httploc = getHTTPObject();
    var getstr = "";
    getstr += "timesheetId=" + timesheetId;
    httploc.open("POST", url, true);
    httploc.onreadystatechange = function ()
    {
        if (httploc.readyState == 4)
        {
            if (httploc.status == 200)
            {
                var response = httploc.responseText;
                document.getElementById('invoiceDiv').innerHTML = '';
                document.getElementById('invoiceDiv').innerHTML = response;
                $("#upload_link_1").on('click', function (e) {
                    e.preventDefault();
                    $("#upload1:hidden").trigger('click');
                });
            }            
        }
    };
    httploc.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    httploc.setRequestHeader("Content-length", getstr.length);
    httploc.setRequestHeader("Connection", "close");
    httploc.send(getstr);
}

function SentforApproval(timesheetId, type, repeatId)
{
    $('#email_timesheet_modal').modal('show');
    var url = "../ajax/timesheet/sentforApproval.jsp";
    var httploc = getHTTPObject();
    var getstr = "";
    getstr += "timesheetId=" + timesheetId;
    getstr += "&type=" + type;
    getstr += "&repeatId=" + repeatId;
    httploc.open("POST", url, true);
    httploc.onreadystatechange = function ()
    {
        if (httploc.readyState == 4)
        {
            if (httploc.status == 200)
            {
                var response = httploc.responseText;
                document.getElementById('approveDiv').innerHTML = '';
                document.getElementById('approveDiv').innerHTML = response;
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
}

function saveReasonForm(id)
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
    if (saveReason())
    {
        document.getElementById('submitdiv').innerHTML = "<img src='../assets/images/loading.gif' align='absmiddle' />";
        document.getElementById('submitdiv').innerHTML = "<img src='../assets/images/loading.gif' align='absmiddle' />";
        document.timesheetForm.doSave.value = "yes";   
        document.timesheetForm.timesheetId.value = id;   
        document.timesheetForm.action = "../timesheet/TimesheetAction.do";
        document.timesheetForm.submit();
    }
}

function saveReason()
{
    if (document.timesheetForm.revisionId.value == "-1")
    {
        Swal.fire({
                title: "Please select reason.",
                didClose: () => {
                    document.timesheetForm.revisionId.focus();
                }
            })
            return false;
    }
    if (document.timesheetForm.remarks.value == "")
    {
        Swal.fire({
                title: "Please enter remarks.",
                didClose: () => {
                    document.timesheetForm.remarks.focus();
                }
            })
            return false;
    }   
    if (validdesc(document.timesheetForm.remarks) == false)
    {
        document.timesheetForm.remarks.focus();
        return false;
    }
    return true;
}

function approveInvoiceForm(id)
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
    if (approveInvoice())
    {
        document.getElementById('submitdiv').innerHTML = "<img src='../assets/images/loading.gif' align='absmiddle' />";
        document.timesheetForm.doApproveInvoice.value = "yes";
        document.timesheetForm.doSave.value = "no";
        document.timesheetForm.timesheetId.value = id;   
        document.timesheetForm.action = "../timesheet/TimesheetAction.do";
        document.timesheetForm.submit();
    }
}

function approveInvoice()
{
    if (document.timesheetForm.approveRemarks.value == "")
    {
        Swal.fire({
                title: "Please enter remarks.",
                didClose: () => {
                    document.timesheetForm.approveRemarks.focus();
                }
            })
            return false;
    }
    if (validdesc(document.timesheetForm.approveRemarks) == false)
    {
        document.timesheetForm.approveRemarks.focus();
        return false;
    }
    if (document.timesheetForm.invoiceFile.value == "")
    {
        Swal.fire({
                title: "Please upload document.",
                didClose: () => {
                    document.timesheetForm.invoiceFile.focus();
                }
            })
            return false;
    }
    if (!(document.timesheetForm.invoiceFile.value).match(/(\.(pdf))$/i))
    {
        Swal.fire({
            title: "Only .pdf are allowed.",
            didClose: () => {
                document.timesheetForm.invoiceFile.focus();
            }
        })
        return false;
    }
    var input = document.timesheetForm.invoiceFile;
    if (input.files)
    {
        var file = input.files[0];
        if (file.size > 1024 * 1024 * 5)
        {
            Swal.fire({
                title: "File size should not exceed 5 MB.",
                didClose: () => {
                    document.timesheetForm.invoiceFile.focus();
                }
            })
            return false;
        }
    }
    return true;
}

function setClass(tp)
{
    document.getElementById("upload_link_" + tp).className = "attache_btn uploaded_img";
}

function sentApproval(id)
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
    if (checkSendApp())
    {
        document.getElementById('submitdiv').innerHTML = "<img src='../assets/images/loading.gif' align='absmiddle' />";
        document.timesheetForm.doSentApproval.value = "yes";
        document.timesheetForm.doApproveInvoice.value = "no";
        document.timesheetForm.doSave.value = "no";
        document.timesheetForm.timesheetId.value = id;   
        document.timesheetForm.action = "../timesheet/TimesheetAction.do";
        document.timesheetForm.submit();
    }
}
function sentApprovalMail(id)
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
    if (checkSendApp())
    {
        document.getElementById('submitdiv').innerHTML = "<img src='../assets/images/loading.gif' align='absmiddle' />";
        document.timesheetForm.doSentApproval.value = "yes";
        document.timesheetForm.timesheetId.value = id;   
        document.timesheetForm.action = "../timesheet/TimesheetAction.do";
        document.timesheetForm.submit();
    }
}

function checkSendApp()
{
    if (document.timesheetForm.email.value == "")
    {
        Swal.fire({
                title: "Please enter to field.",
                didClose: () => {
                    document.timesheetForm.email.focus();
                }
            })
            return false;
    }
    if (document.timesheetForm.email.value != "")
    {
        if (checkEmailAddress(document.timesheetForm.email) == false)
        {
            Swal.fire({
                title: "Please enter valid email ID.",
                didClose: () => {
                    document.timesheetForm.email.focus();
                }
            })
            return false;
        }
    }
    if (document.timesheetForm.ccaddress.value != "")
    {
        if (checkEmailAddress(document.timesheetForm.ccaddress) == false)
        {
            Swal.fire({
                title: "Please enter valid cc email ID.",
                didClose: () => {
                    document.timesheetForm.ccaddress.focus();
                }
            })
            return false;
        }
    }
    if (document.timesheetForm.bccaddress.value != "")
    {
        if (checkEmailAddress(document.timesheetForm.bccaddress) == false)
        {
            Swal.fire({
                title: "Please enter valid bcc email ID.",
                didClose: () => {
                    document.timesheetForm.bccaddress.focus();
                }
            })
            return false;
        }
    }
    if (document.timesheetForm.subject.value == "")
    {
        Swal.fire({
                title: "Please enter subject.",
                didClose: () => {
                    document.timesheetForm.subject.focus();
                }
            })
            return false;
    }
    if (document.timesheetForm.message.value == "")
    {
        Swal.fire({
                title: "Please enter Email body.",
                didClose: () => {
                    document.timesheetForm.message.focus();
                }
            })
            return false;
    }
    if(document.timesheetForm.checktype.checked == false)
    {        
        if (document.timesheetForm.sendApproveFile.value == "")
        {
            Swal.fire({
                    title: "Please upload document.",
                    didClose: () => {
                        document.timesheetForm.sendApproveFile.focus();
                    }
                })
                return false;
        }
        if (!(document.timesheetForm.sendApproveFile.value).match(/(\.(pdf))$/i))
        {
            Swal.fire({
                title: "Only .pdf are allowed.",
                didClose: () => {
                    document.timesheetForm.sendApproveFile.focus();
                }
            })
            return false;
        }
        var input = document.timesheetForm.sendApproveFile;
        if (input.files)
        {
            var file = input.files[0];
            if (file.size > 1024 * 1024 * 5)
            {
                Swal.fire({
                    title: "File size should not exceed 5 MB.",
                    didClose: () => {
                        document.timesheetForm.sendApproveFile.focus();
                    }
                })
                return false;
            }
        } 
    }
    return true;
}

function seturl(url)
{
    $('#view_pdf').modal('show');
    var url_v = "", classname = "";
    if (url.includes(".pdf"))
    {
        url_v = url+"#toolbar=0&page=1&view=fitH,100";
        classname = "pdf_mode";
    } 
    window.top.$('#iframe').attr('src', 'about:blank');
    setTimeout(function () {
        window.top.$('#iframe').attr('src', url_v);
        document.getElementById("iframe").className = classname;
        document.getElementById("diframe").href = url;
    }, 1000);
    $("#iframe").on("load", function () {
        let head = $("#iframe").contents().find("head");
        let css = '<style>img{margin: 0px auto; max-width:-webkit-fill-available;}</style>';
        $(head).append(css);
    });
}

function setTotal(crewId, srno, positionId)
{
    
    var days = document.getElementById("days_"+crewId+"_"+srno+"_"+positionId).value;
    var rate = document.getElementById("rate_"+crewId+"_"+srno+"_"+positionId).value;
    var amount = (Number(days) * Number(rate)).toFixed(2); 
    document.getElementById("amount_"+crewId+"_"+srno+"_"+positionId).value = amount;
    document.getElementById("amounttd_"+crewId+"_"+srno+"_"+positionId).innerHTML = amount;    
    setGrandTotal(crewId, srno,positionId);
}
		
function setGrandTotal(crewId, srno, positionId)
{  
        if(document.getElementById("cb_"+crewId+"_"+srno+"_"+positionId).checked)
        {
            document.getElementById("flag_"+crewId+"_"+srno+"_"+positionId).value = "1";
        }else {
            document.getElementById("flag_"+crewId+"_"+srno+"_"+positionId).value = "0";
        }
        var totalcrew = Number(document.getElementById("totalcrew_"+crewId).value);
        var gtotal = 0;
        var vv;
        for(var i = 1; i <= totalcrew; i++)
        {
            vv = document.getElementById("cb_"+crewId+"_"+i+"_"+positionId);
            if(vv && vv.checked)
            {
                if(document.getElementById("amount_"+crewId+"_"+i+"_"+positionId).value != "")
                {
                    gtotal += Number(document.getElementById("amount_"+crewId+"_"+i+"_"+positionId).value);   
                }    
            }
        }
        document.getElementById("gtotal_"+crewId+"_"+positionId).value = gtotal.toFixed(2);
        document.getElementById("gtotaltd_"+crewId+"_"+positionId).innerHTML = gtotal.toFixed(2);
}

function setStandby(crewId, srno,positionId)
{
    var days = document.getElementById("days_"+crewId+"_"+srno+"_"+positionId).value;
    var rate = Number(document.getElementById("rate_"+crewId+"_"+srno+"_"+positionId).value) * Number(document.getElementById("srate_"+crewId+"_"+srno+"_"+positionId).value) / 100.0;
    var amount = (Number(days) * Number(rate)).toFixed(2);
    document.getElementById("ratetd_"+crewId+"_"+srno+"_"+positionId).innerHTML = rate.toFixed(2);
    document.getElementById("amount_"+crewId+"_"+srno+"_"+positionId).value = amount;
    document.getElementById("amounttd_"+crewId+"_"+srno+"_"+positionId).innerHTML = amount;
    setGrandTotal(crewId, srno,positionId);
}

function checktsdetail()
{
    if(document.forms[0].cb)
    {
        var len = document.forms[0].cb.length;
        for(var i = 0; i < len; i++)
        {
            if(document.forms[0].cb[i].checked)
            {
                if(Number(document.forms[0].type[i].value) == 2)
                {
                    if(Number(document.forms[0].days[i].value) <= 0)
                    {
                        Swal.fire({
                        title: "Please enter value for overtime hour.",
                        didClose: () => {
                            document.forms[0].days[i].focus();
                        }
                    })
                    return false;
                    }
                }
                else if(Number(document.forms[0].type[i].value) == 4)
                {
                    if(Number(document.forms[0].srate[i].value) <= 0)
                    {                        
                        Swal.fire({
                        title: "Please enter standby rate.",
                        didClose: () => {
                            document.forms[0].srate[i].focus();
                        }
                    })
                    return false;
                    }
                }
            }
        }
    }
    else
    {
        Swal.fire("Something went wrong.");
        return false;
    }
    return true;
}

function viewRotation(crewrotationId, clientId, clientassetId)
{
    if (document.forms[0].doSummary)
        document.forms[0].doSummary.value = "yes";
    document.forms[0].crewId.value = crewrotationId;
    document.forms[0].clientId.value = clientId;
    document.forms[0].clientassetId.value = clientassetId;
    document.forms[0].target = "_blank";
    document.forms[0].action = "../crewrotation/CrewrotationAction.do";
    document.forms[0].submit();
}

function regenerateTimesheet(id)
{
    document.forms[0].target = "_self";
    document.timesheetForm.regenerate.value = "yes";
    document.timesheetForm.timesheetId.value = id;
    document.timesheetForm.action = "../timesheet/TimesheetAction.do";
    document.timesheetForm.submit();
}
