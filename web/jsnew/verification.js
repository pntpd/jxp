function resetFilter()
{
    document.forms[0].search.value = "";
    document.forms[0].positionIndex.value = "-1";
    document.forms[0].tabIndex.value = "-1";
    document.forms[0].statusIndex.value = "-1";
    searchFormAjax('s', '-1');
}

function showDetail(id)
{
    document.verificationForm.doView.value = "yes";
    document.verificationForm.verificationId.value = id;
    document.verificationForm.action = "../verification/VerificationAction.do";
    document.verificationForm.submit();
}

function view(type)
{
    document.verificationForm.doView.value = "yes";
    document.verificationForm.verificationId.value = type;
    document.verificationForm.action = "../verification/VerificationAction.do";
    document.verificationForm.submit();
}

function editfromverification(vdestination)
{

    document.forms[0].doCancel.value = "no";
    if (vdestination === "talent") {
        document.forms[0].action = "../talentpool/TalentpoolAction.do?doView=yes";
    } else {
        document.forms[0].action = "../candidate/CandidateAction.do?doModify=yes";
    }
    document.forms[0].submit();
}

function goback()
{
    if (document.verificationForm.doView)
        document.verificationForm.doView.value = "no";
    if (document.verificationForm.doCancel)
        document.verificationForm.doCancel.value = "yes";
    if (document.verificationForm.doVerify)
        document.verificationForm.doVerify.value = "no";
    document.verificationForm.action = "../verification/VerificationAction.do";
    document.verificationForm.submit();
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

function setTab(tp)
{
    for (var i = 1; i <= 11; i++)
    {
        if (Number(tp) == i)
            document.getElementById("tab_list" + i).className = "nav-item active";
        else
            document.getElementById("tab_list" + i).className = "nav-item";
    }
    if (Number(tp) == 1 && Number(document.forms[0].checktab1.value) > 0) {
        $('#thank_you_modal').modal('show');
        document.getElementById("modal_href").href = "javascript: settab('2');";
    }
    if (Number(tp) == 3 && Number(document.forms[0].checktab2.value) > 0) {
        $('#thank_you_modal').modal('show');
        document.getElementById("modal_href").href = "javascript: settab('4');";
    }
}

function searchFormAjax(v, v1)
{
    if (checkSearch())
    {
        var url = "../ajax/verification/getinfo.jsp";
        var httploc = getHTTPObject();
        var getstr = "";
        var next_value = escape(document.verificationForm.nextValue.value);
        var search_value = escape(document.verificationForm.search.value);
        getstr += "nextValue=" + next_value;
        getstr += "&next=" + v;
        getstr += "&search=" + search_value;
        getstr += "&doDirect=" + v1;
        getstr += "&statusIndex=" + document.verificationForm.statusIndex.value;
        getstr += "&positionIndex=" + document.verificationForm.positionIndex.value;
        getstr += "&tabIndex=" + document.verificationForm.tabIndex.value;
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
    var url_sort = "../ajax/verification/sort.jsp";
    var getstr = "";
    var nextValue = 0;
    if (document.verificationForm.nextValue)
        nextValue = document.verificationForm.nextValue.value;
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
    document.verificationForm.reset();
}

function exporttoexcel()
{
    document.verificationForm.action = "../verification/VerificationExportAction.do";
    document.verificationForm.submit();
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
        url_v = uval+"#toolbar=0&page=1&view=fitH,100";
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
}

function viewimg(candidateId, fn)
{
    var url = "../ajax/candidate/getimg.jsp";
    var httploc = getHTTPObject();
    var getstr = "candidateId=" + candidateId;
    getstr += "&type=1";
    getstr += "&fn=" + escape(fn);
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
                var v1 = arr[0];
                var v2 = trim(arr[1]);
                document.getElementById('viewfilesdiv').innerHTML = '';
                document.getElementById('viewfilesdiv').innerHTML = v1;
            }
        }
    };
    httploc.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    httploc.setRequestHeader("Content-length", getstr.length);
    httploc.setRequestHeader("Connection", "close");
    httploc.send(getstr);
    document.getElementById('viewfilesdiv').innerHTML = "<div><img src='../assets/images/loading.gif' align='absmiddle'/>Please wait. Loading...</div>";
}

function viewworkexpfiles(filename1, filename2)
{
    var url = "../ajax/candidate/getimg_exp.jsp";
    var httploc = getHTTPObject();
    var getstr = "filename1=" + filename1;
    getstr += "&filename2=" + filename2;
    httploc.open("POST", url, true);
    httploc.onreadystatechange = function ()
    {
        if (httploc.readyState == 4)
        {
            if (httploc.status == 200)
            {
                var response = httploc.responseText;
                document.getElementById('viewexpdiv').innerHTML = '';
                document.getElementById('viewexpdiv').innerHTML = response;
            }
        }
    };
    httploc.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    httploc.setRequestHeader("Content-length", getstr.length);
    httploc.setRequestHeader("Connection", "close");
    httploc.send(getstr);
    document.getElementById('viewfilesdiv').innerHTML = "<div><img src='../assets/images/loading.gif' align='absmiddle'/>Please wait. Loading...</div>";
}

function getVerifiation(tabno, childid, documentName, coursename, alertId)
{
    var url = "../ajax/verification/getverifieddetails.jsp";
    var httploc = getHTTPObject();
    var getstr = "";
    getstr += "candidateId=" + document.verificationForm.verificationId.value;
    getstr += "&documentName=" + documentName;    
    getstr += "&coursename=" + coursename;
    getstr += "&tabNo=" + tabno;
    getstr += "&childId=" + childid;
    getstr += "&alertId=" + document.verificationForm.alertId.value;
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
                document.getElementById('dverify').innerHTML = '';
                document.getElementById('dverify').innerHTML = trim(v1);
                if (trim(v2) == "Yes")
                    document.getElementById("verify_modal").className = "modal fade verify_modal com_pdf blur_modal show";
                else
                    document.getElementById("verify_modal").className = "modal fade verify_modal blur_modal show";
                $("#upload_link_1").on('click', function (e) {
                    e.preventDefault();
                    $("#upload1:hidden").trigger('click');
                });
                $(function ()
                {
                    $("#verificatonauthority").autocomplete({
                        source: function (request, response) {
                            $.ajax({
                                url: "../ajax/verification/autofillauthority.jsp",
                                type: 'post',
                                dataType: "json",
                                data: JSON.stringify({"search": request.term}),
                                success: function (data) {
                                    response(data);
                                }
                            });
                        },
                        select: function (event, ui) {
                            $('#verificatonauthority').val(ui.item.label); // display the selected text
                            return false;
                        },
                        focus: function (event, ui)
                        {
                            $('#verificatonauthority').val(ui.item.label); // display the selected text
                            return false;
                        }
                    });
                });
                
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
    document.getElementById('dverify').innerHTML = "<div><img src='../assets/images/loading.gif' align='absmiddle'/>Please wait. Loading...</div>";
}

function setClass(tp)
{
    document.getElementById("upload_link_" + tp).className = "attache_btn uploaded_img fix_width";
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
        url_v = uval+"#toolbar=0&page=1&view=fitH,100";
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
    if (checkVerification())
    {
        document.verificationForm.doVerify.value = "yes";
        document.verificationForm.doView.value = "no";
        document.verificationForm.action = "../verification/VerificationAction.do";
        document.verificationForm.submit();
    }
}

function checkVerification()
{
    if (Number(document.verificationForm.rbverification.value) <= 0)
    {
        Swal.fire({
            title: "Please select verification status.",
            didClose: () => {
                document.verificationForm.rbverification.focus();
            }
        })
        return false;
    }
    if (trim(document.verificationForm.verificatonauthority.value) != "")
    {
        if (validdesc(document.verificationForm.verificatonauthority) == false)
        {
            return false;
        }
    }
    if (document.getElementById("upload1"))
    {
        var filesSelected = document.getElementById("upload1").files;
        if (filesSelected.length > 0)
        {
            for (var i = 0; i < filesSelected.length; i++)
            {
                var file = document.getElementById("upload1").files[i];
                if (!(file.name).match(/(\.(pdf)|(doc)|(docx))$/i))
                {
                    swal.fire("Only .pdf, .doc and .docx are allowed.");
                    document.getElementById("upload1").focus();
                    return false;
                }
                if (file.size > 1024 * 1024 * 5)
                {
                    swal.fire("File size should not exceed 5 MB.");
                    document.getElementById("upload1").focus();
                    return false;
                }
            }
        }
    }
    return true;
}

function setStatuscb(tp)
{
    if (eval(tp) == 1)
    {
        if (document.getElementById("stcb1").checked)
        {
            document.getElementById("stcb2").checked = true;
            document.getElementById("stcb2").disabled = true;
            document.forms[0].rbverification.value = "2";
        } else
        {
            document.getElementById("stcb2").checked = false;
            document.getElementById("stcb2").disabled = false;
            document.forms[0].rbverification.value = "0";
        }
    } else
    {
        if (document.getElementById("stcb2").checked)
        {
            document.getElementById("stcb1").checked = false;
            document.forms[0].rbverification.value = "1";
        } else
        {
            document.getElementById("stcb1").disabled = false;
            document.forms[0].rbverification.value = "0";
        }
    }
}

function setIframeresume(uval)
{
    var url_v = "", classname = "";
    if (uval.includes(".doc") || uval.includes(".docx"))
    {
        url_v = "https://docs.google.com/gview?url=" + uval + "&embedded=true";
        classname = "doc_mode";
    } else if (uval.includes(".pdf"))
    {
        url_v = uval+"#toolbar=0&page=1&view=fitH,100";
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

function settab(tabno)
{
    for (i = 1; i <= 11; i++)
    {
        document.getElementById("tab_list" + i).className = "nav-item";
        document.getElementById("tab" + i).className = "tab-pane";
    }
    $('#thank_you_modal').modal('hide');
    document.getElementById("tab_list" + tabno).className = "nav-item active";
    document.getElementById("tab" + tabno).className = "tab-pane active";
}

function verifyother(id)
{
    document.verificationForm.tabIdOther.value = id;
    document.verificationForm.doVerify.value = "no";
    document.verificationForm.doVerifyOther.value = "yes";
    document.verificationForm.doView.value = "no";
    document.verificationForm.action = "../verification/VerificationAction.do";
    document.verificationForm.submit();    
}