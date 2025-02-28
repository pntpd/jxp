function resetFilter()
{
    document.clientselectionForm.search.value = "";
    document.clientselectionForm.clientIdIndex.value = "-1";
    document.clientselectionForm.assetIdIndex.value = "-1";
    document.clientselectionForm.pgvalue.value = "";
    searchFormAjax('s', '-1');
    setAssetDDL();
}

function resetclientFilter()
{
    document.clientselectionForm.search.value = "";
    document.clientselectionForm.assetIdIndex.value = "-1";
    setclientPositionDDL();
    searchClientFormAjax('s', '-1');
}

function view(jobpostId, from)
{
    if (document.clientselectionForm.doView)
        document.clientselectionForm.doView.value = "yes";
    document.clientselectionForm.jobpostId.value = jobpostId;
    document.clientselectionForm.from.value = from;
    document.forms[0].target = "";
    document.clientselectionForm.action = "../clientselection/ClientselectionAction.do";
    document.clientselectionForm.submit();
}

function gobackview()
{
    if (document.clientselectionForm.doView)
        document.clientselectionForm.doView.value = "yes";
    document.forms[0].target = "";
    document.clientselectionForm.action = "../clientselection/ClientselectionAction.do";
    document.clientselectionForm.submit();
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

function handleKeyClientSearch(e)
{
    var key = e.keyCode || e.which;
    if (key === 13)
    {
        e.preventDefault();
        searchClientFormAjax('s', '-1');
    }
}

function checkSearch()
{
    if (trim(document.forms[0].search.value) != "")
    {
        if (validdescsearch(document.forms[0].search) == false)
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
        var url = "../ajax/clientselection/getinfo.jsp";
        var httploc = getHTTPObject();
        var getstr = "";
        var next_value = escape(document.clientselectionForm.nextValue.value);
        var search_value = escape(document.clientselectionForm.search.value);
        var pgvalue = escape(document.clientselectionForm.pgvalue.value);
        var assetIdIndex = escape(document.clientselectionForm.assetIdIndex.value);
        var clientIdIndex = escape(document.clientselectionForm.clientIdIndex.value);
        getstr += "nextValue=" + next_value;
        getstr += "&next=" + v;
        getstr += "&search=" + search_value;
        getstr += "&pgvalue=" + pgvalue;
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

function searchClientFormAjax(v, v1)
{
    if (checkSearch()) {
        var url = "../ajax/clientselection/client_getinfo.jsp";
        var httploc = getHTTPObject();
        var getstr = "";
        var next_value = escape(document.clientselectionForm.nextValue.value);
        var search_value = escape(document.clientselectionForm.search.value);
        var pgvalue = escape(document.clientselectionForm.pgvalue.value);
        var assetIdIndex = escape(document.clientselectionForm.assetIdIndex.value);
        getstr += "nextValue=" + next_value;
        getstr += "&next=" + v;
        getstr += "&search=" + search_value;
        getstr += "&pgvalue=" + pgvalue;
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
    if (document.clientselectionForm.doView)
        document.clientselectionForm.doView.value = "no";
    if (document.clientselectionForm.doCancel)
        document.clientselectionForm.doCancel.value = "yes";
    if (document.clientselectionForm.doSave)
        document.clientselectionForm.doSave.value = "no";
    document.forms[0].target = "";
    document.clientselectionForm.action = "../clientselection/ClientselectionAction.do";
    document.clientselectionForm.submit();
}

function sortForm(colid, updown)
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
    var url_sort = "../ajax/clientselection/sort.jsp";
    var getstr = "";
    var nextValue = 0;
    if (document.clientselectionForm.nextValue)
        nextValue = document.clientselectionForm.nextValue.value;
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

function sortclientForm(colid, updown)
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
    var url_sort = "../ajax/clientselection/client_sort.jsp";
    var getstr = "";
    var nextValue = 0;
    if (document.clientselectionForm.nextValue)
        nextValue = document.clientselectionForm.nextValue.value;
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
    document.clientselectionForm.reset();
}

function setPosition()
{
    var url = "../ajax/candidate/positions.jsp";
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
                document.getElementById("positionddl").innerHTML = '';
                document.getElementById("positionddl").innerHTML = response;
            }
        }
    };
    httploc.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    httploc.setRequestHeader("Content-length", getstr.length);
    httploc.setRequestHeader("Connection", "close");
    httploc.send(getstr);
}

function exporttoexcel()
{
    document.clientselectionForm.action = "../clientselection/ClientselectionExportAction.do";
    document.clientselectionForm.submit();
}

function exporttoexcelclinet()
{
    document.clientselectionForm.action = "../clientselection/ClientselectionclientExportAction.do";
    document.clientselectionForm.submit();
}

function setStatuscb(tp)
{
    if (eval(tp) == 1)
    {
        if (document.getElementById("stcb1").checked)
        {
            document.getElementById("stcb2").checked = true;
            document.getElementById("stcb2").disabled = true;
            document.forms[0].rbchecked.value = "2";
        } else
        {
            document.getElementById("stcb2").checked = false;
            document.getElementById("stcb2").disabled = false;
            document.forms[0].rbchecked.value = "0";
        }
    } else
    {
        if (document.getElementById("stcb2").checked)
        {
            document.getElementById("stcb1").checked = false;
            document.forms[0].rbchecked.value = "1";
        } else
        {
            document.getElementById("stcb1").disabled = false;
            document.forms[0].rbchecked.value = "0";
        }
    }
}

function setAssetDDL()
{
    var url = "../ajax/clientselection/getasset.jsp";
    document.getElementById("assetIdIndex").value = '-1';
    document.getElementById("pgvalue").value = '';
    var httploc = getHTTPObject();
    var getstr = "clientIdIndex=" + document.clientselectionForm.clientIdIndex.value + "&from=asset";
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
                setPositionDDL();
            }
        }
    };
    httploc.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    httploc.send(getstr);
    searchFormAjax('s', '-1');
}

function setPositionDDL()
{
    var url = "../ajax/clientselection/getasset.jsp";
    document.getElementById("pgvalue").value = '';
    var httploc = getHTTPObject();
    var getstr = "assetIdIndex=" + document.clientselectionForm.assetIdIndex.value + "&from=position";
    httploc.open("POST", url, true);
    httploc.onreadystatechange = function ()
    {
        if (httploc.readyState == 4)
        {
            if (httploc.status == 200)
            {
                var response = httploc.responseText;
                document.getElementById("pgvalue").innerHTML = '';
                document.getElementById("pgvalue").innerHTML = response;
            }
        }
    };
    httploc.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    httploc.send(getstr);
    searchFormAjax('s', '-1');
}

function setclientPositionDDL()
{
    var url = "../ajax/clientselection/getasset.jsp";
    document.getElementById("pgvalue").value = '';
    var httploc = getHTTPObject();
    var getstr = "assetIdIndex=" + document.clientselectionForm.assetIdIndex.value + "&from=position";
    httploc.open("POST", url, true);
    httploc.onreadystatechange = function ()
    {
        if (httploc.readyState == 4)
        {
            if (httploc.status == 200)
            {
                var response = httploc.responseText;
                document.getElementById("pgvalue").innerHTML = '';
                document.getElementById("pgvalue").innerHTML = response;
            }
        }
    };
    httploc.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    httploc.send(getstr);
    searchClientFormAjax('s', '-1');
}

function generatepdf()
{
    if (document.clientselectionForm.resumetempId.value == '-1')
    {
        Swal.fire({
            title: "Please select template.",
            didClose: () => {
                document.clientselectionForm.resumetempId.focus();
            }
        })
        return false;
    }
    if (document.clientselectionForm.resumetempId.value > 0) {
        document.getElementById("generatepdfdiv").innerHTML = "<img src='../assets/images/loading.gif' align='absmiddle' />";
        var url = "../ajax/clientselection/getresumepdffile.jsp";
        var httploc = getHTTPObject();
        var getstr = "";
        getstr += "resumetempId=" + document.clientselectionForm.resumetempId.value;
        getstr += "&shortlistId=" + document.clientselectionForm.shortlistId.value;
        getstr += "&candidateId=" + document.clientselectionForm.candidateId.value;
        getstr += "&cval1=" + document.clientselectionForm.cval1.value;
        getstr += "&cval2=" + document.clientselectionForm.cval2.value;
        getstr += "&cval3=" + document.clientselectionForm.cval3.value;
        getstr += "&cval4=" + document.clientselectionForm.cval4.value;
        getstr += "&cval5=" + document.clientselectionForm.cval5.value;
        getstr += "&cval6=" + document.clientselectionForm.cval6.value;
        getstr += "&cval7=" + document.clientselectionForm.cval7.value;
        getstr += "&cval8=" + document.clientselectionForm.cval8.value;
        getstr += "&cval9=" + document.clientselectionForm.cval9.value;
        getstr += "&cval10=" + document.clientselectionForm.cval10.value;
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

function generateofferpdf(assetname)
{
    if (document.clientselectionForm.resumetempId.value == '-1')
    {
        Swal.fire({
            title: "Please select template.",
            didClose: () => {
                document.clientselectionForm.resumetempId.focus();
            }
        })
        return false;
    }
    document.getElementById("generatepdfdiv").innerHTML = "<img src='../assets/images/loading.gif' align='absmiddle' />";
    var url = "../ajax/clientselection/getofferpdffile.jsp";
    var httploc = getHTTPObject();
    var getstr = "";
    getstr += "resumetempId=" + document.clientselectionForm.resumetempId.value;
    getstr += "&shortlistId=" + document.clientselectionForm.shortlistId.value;
    getstr += "&candidateId=" + document.clientselectionForm.candidateId.value;
    getstr += "&clientId=" + document.clientselectionForm.clientId.value;
    getstr += "&cval1=" + document.clientselectionForm.cval1.value;
    getstr += "&cval2=" + document.clientselectionForm.cval2.value;
    getstr += "&cval3=" + document.clientselectionForm.cval3.value;
    getstr += "&cval4=" + document.clientselectionForm.cval4.value;
    getstr += "&cval5=" + document.clientselectionForm.cval5.value;
    getstr += "&cval6=" + document.clientselectionForm.cval6.value;
    getstr += "&cval7=" + document.clientselectionForm.cval7.value;
    getstr += "&cval8=" + document.clientselectionForm.cval8.value;
    getstr += "&cval9=" + document.clientselectionForm.cval9.value;
    getstr += "&cval10=" + document.clientselectionForm.cval10.value;
    getstr += "&validfrom=" + document.clientselectionForm.validfrom.value;
    getstr += "&validto=" + document.clientselectionForm.validto.value;
    getstr += "&contractor=" + document.clientselectionForm.contractor.value;
    getstr += "&edate=" + document.clientselectionForm.edate.value;
    getstr += "&dmname=" + document.clientselectionForm.dmname.value;
    getstr += "&designation=" + document.clientselectionForm.designation.value;
    getstr += "&comments=" + document.clientselectionForm.comments.value;
    getstr += "&variablepay=" + document.clientselectionForm.variablepay.value;
    getstr += "&jobpostId=" + document.clientselectionForm.jobpostId.value;
    getstr += "&clientasset=" + assetname;
    httploc.open("POST", url, true);
    httploc.onreadystatechange = function ()
    {
        if (httploc.readyState == 4)
        {
            if (httploc.status == 200)
            {
                var response = httploc.responseText;
                setIframe(response);
                document.getElementById("generatepdfdiv").innerHTML = '<a href="javascript: generateofferpdf('+assetname+');" class="gen_btn"> Generate</a>';
            }
        }
    };
    httploc.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    httploc.setRequestHeader("Content-length", getstr.length);
    httploc.setRequestHeader("Connection", "close");
    httploc.send(getstr);
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

function ViewJobpost(id)
{
    if (id == "") {
        var id = document.forms[0].jobpostId.value;
    }
    document.forms[0].jobpostId.value = id;
    document.forms[0].target = "_blank";
    document.forms[0].action = "../jobpost/JobPostAction.do?doView=yes";
    document.forms[0].submit();
}

function getModel(shortlistId, sflag)
{
    var url = "../ajax/clientselection/getmailmodaldetails.jsp";
    var httploc = getHTTPObject();
    var getstr = "";
    getstr += "shortlistId=" + shortlistId;
    getstr += "&sflag=" + sflag;
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

function getModelforoffer(shortlistId, sflag, oflag)
{
    var url = "../ajax/clientselection/getoffermailmodaldetails.jsp";
    var httploc = getHTTPObject();
    var getstr = "";
    getstr += "shortlistId=" + shortlistId;
    getstr += "&sflag=" + sflag;
    getstr += "&oflag=" + oflag;
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
                    $("#upload_link_1").on('click', function (e) {
                        e.preventDefault();
                        $("#upload1:hidden").trigger('click');
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

function checkMailoffer()
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

    if (document.forms[0].isattached.checked)
    {
        if (document.forms[0].isattached.value != 1) {
            Swal.fire({
                title: "",
                didClose: () => {
                    document.forms[0].isattached.focus();
                }
            })
            return false;
        }
    } else if (!(document.forms[0].attachfile.value).match(/(\.(pdf))$/i))
    {
        Swal.fire({
            title: "Only .pdf are allowed.",
            didClose: () => {
                document.forms[0].attachfile.focus();
            }
        })
        return false;
    } else if (document.forms[0].attachfile.value != "")
    {
        var input = document.forms[0].attachfile;
        if (input.files)
        {
            var file = input.files[0];
            if (file.size > (1024 * 1024 * 5))
            {
                Swal.fire({
                    title: "File size should not exceed 5 MB.",
                    didClose: () => {
                        document.forms[0].attachfile.focus();
                    }
                })
                return false;
            }
        }
    }
    return true;
}

function submitmailofferForm(candidatename, filename, clientname, shortlistId)
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
    if (checkMailoffer())
    {
        document.getElementById('sendmaildiv').innerHTML = "<img src='../assets/images/loading.gif' align='absmiddle' />";
        var fromval = document.forms[0].fromval.value;
        var toval = document.forms[0].toval.value;
        var ccval = document.forms[0].ccval.value;
        var bccval = document.forms[0].bccval.value;
        var subject = document.forms[0].subject.value;
        var description = document.forms[0].description.value;
        var attachfile = document.forms[0].attachfile.value;
        var fromDate = document.forms[0].validfrom.value;
        var toDate = document.forms[0].validto.value;
        var clientId = document.forms[0].clientId.value;
        var assetId = document.forms[0].assetId.value;
        var type = document.forms[0].type.value;
        var isattached = 0;
        if (document.forms[0].isattached.checked)
        {
            isattached = document.forms[0].isattached.value;
        }
        var url = "../ajax/clientselection/sendoffermail.jsp";
        var httploc = getHTTPObject();
        var getstr = "";
        getstr += "fromval=" + fromval;
        getstr += "&toval=" + toval;
        getstr += "&ccval=" + ccval;
        getstr += "&bccval=" + bccval;
        getstr += "&subject=" + subject;
        getstr += "&description=" + description;
        getstr += "&candidatename=" + candidatename;
        getstr += "&filename=" + filename;
        getstr += "&clientname=" + clientname;
        getstr += "&shortlistId=" + shortlistId;
        getstr += "&isattached=" + isattached;
        getstr += "&fromDate=" + fromDate;
        getstr += "&toDate=" + toDate;
        getstr += "&clientId=" + clientId;
        getstr += "&assetId=" + assetId;
        getstr += "&type=" + type;
        httploc.open("POST", url, true);
        httploc.onreadystatechange = function ()
        {
            if (httploc.readyState == 4)
            {
                if (httploc.status == 200)
                {
                    var response = httploc.responseText;
                    var jobpostId = document.forms[0].jobpostId.value;
                    $('#mail_modal').modal('hide');
                    $('#thank_you_modalmail').modal('show');
                }
            }
        };
        getstr += "&attachfile=" + encodeURIComponent(document.getElementById("offerpdfId").value);
        httploc.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
        httploc.setRequestHeader("Content-length", getstr.length);
        httploc.setRequestHeader("Connection", "close");
        httploc.send(getstr);
    }
}


function setofferfilebase()
{
    document.getElementById("upload_link_1").className = "attache_btn uploaded_img";
    if (document.forms[0].attachfile.value != "")
    {
        var input = document.forms[0].attachfile;
        if (input.files)
        {
            var file = input.files[0];
            if (file.size > (1024 * 1024 * 5))
            {
                Swal.fire({
                    title: "File size should not exceed 5 MB.",
                    didClose: () => {
                        document.forms[0].attachfile.focus();
                    }
                });
            } else
            {
                var filesSelected = document.getElementById("upload1").files;
                if (filesSelected.length > 0)
                {
                    var fileToLoad = filesSelected[0];
                    var fileReader = new FileReader();
                    fileReader.onload = function (fileLoadedEvent)
                    {
                        var srcData = fileLoadedEvent.target.result; // <--- data: base64
                        document.getElementById("offerpdfId").value = srcData;
                    }
                    fileReader.readAsDataURL(fileToLoad);
                }
            }
        }
    }
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

function submitmailForm(candidatename, filename, clientname, shortlistId)
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
    if (checkMail())
    {
        document.getElementById('sendmaildiv').innerHTML = "<img src='../assets/images/loading.gif' align='absmiddle' />";
        var fromval = document.forms[0].fromval.value;
        var toval = document.forms[0].toval.value;
        var ccval = document.forms[0].ccval.value;
        var bccval = document.forms[0].bccval.value;
        var subject = document.forms[0].subject.value;
        var description = document.forms[0].description.value;
        var assetId = document.forms[0].assetId.value;
        var clientId = document.forms[0].clientId.value;
        var jobpostId = document.forms[0].jobpostId.value;
        var url = "../ajax/clientselection/sendcvmail.jsp";
        var httploc = getHTTPObject();
        var getstr = "";
        getstr += "fromval=" + fromval;
        getstr += "&toval=" + toval;
        getstr += "&ccval=" + ccval;
        getstr += "&bccval=" + bccval;
        getstr += "&subject=" + subject;
        getstr += "&description=" + description;
        getstr += "&candidatename=" + candidatename;
        getstr += "&filename=" + filename;
        getstr += "&clientname=" + clientname;
        getstr += "&shortlistId=" + shortlistId;
        getstr += "&assetId=" + assetId;
        getstr += "&clientId=" + clientId;
        getstr += "&jobpostId=" + jobpostId;
        httploc.open("POST", url, true);
        httploc.onreadystatechange = function ()
        {
            if (httploc.readyState == 4)
            {
                if (httploc.status == 200)
                {
                    var response = httploc.responseText;
                    var jobpostId = document.forms[0].jobpostId.value;
                    $('#mail_modal').modal('hide');
                    $('#thank_you_modalmail').modal('show');
                    //view(jobpostId, '');
                }
            }
        };
        httploc.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
        httploc.setRequestHeader("Content-length", getstr.length);
        httploc.setRequestHeader("Connection", "close");
        httploc.send(getstr);
    }
}

function getSearchShortCandList()
{
    if (Number(document.clientselectionForm.selstatus.value) == 2 && Number(document.clientselectionForm.from.value) == 1) {
        document.getElementById("dsubstatus").style.display = "";
    } else {
        document.getElementById("selsubstatus").value = 0;
        document.getElementById("dsubstatus").style.display = "none";
    }
    var httploc = getHTTPObject();
    var url_sort = "../ajax/clientselection/getshortlistedcandidate.jsp";
    var getstr = "";
    getstr += "jobpostid=" + document.clientselectionForm.jobpostId.value;
    getstr += "&status=" + document.clientselectionForm.selstatus.value;
    getstr += "&substatus=" + document.clientselectionForm.selsubstatus.value;
    getstr += "&search=" + document.clientselectionForm.search2.value;
    httploc.open("POST", url_sort, true);
    httploc.onreadystatechange = function ()
    {
        if (httploc.readyState == 4)
        {
            if (httploc.status == 200)
            {
                var response = httploc.responseText;
                document.getElementById('dshortlistedcandidate').innerHTML = '';
                document.getElementById('dshortlistedcandidate').innerHTML = response;
            }
        }
    };
    httploc.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    httploc.setRequestHeader("Content-length", getstr.length);
    httploc.setRequestHeader("Connection", "close");
    httploc.send(getstr);
    document.getElementById('dshortlistedcandidate').innerHTML = "<div><img src='../assets/images/loading.gif' align='absmiddle'/>Please wait. Loading...</div>";
}

function getGenerateCV(shortlistId, sflag, candidateId)
{
    document.forms[0].shortlistId.value = shortlistId;
    document.forms[0].candidateId.value = candidateId;
    document.forms[0].sflag.value = sflag;
    if (document.clientselectionForm.doView)
        document.clientselectionForm.doView.value = "no";
    if (document.clientselectionForm.doCancel)
        document.clientselectionForm.doCancel.value = "no";
    if (document.clientselectionForm.doSave)
        document.clientselectionForm.doSave.value = "no";
    if (document.clientselectionForm.doGenerate)
        document.clientselectionForm.doGenerate.value = "yes";
    document.forms[0].target = "";
    document.clientselectionForm.action = "../clientselection/ClientselectionAction.do";
    document.clientselectionForm.submit();
}

function getGenerateoffer(shortlistId, sflag, candidateId, oflag, tp)
{
    document.forms[0].shortlistId.value = shortlistId;
    document.forms[0].candidateId.value = candidateId;
    document.forms[0].sflag.value = sflag;
    document.forms[0].oflag.value = oflag;
    document.forms[0].type.value = tp;
    if (document.clientselectionForm.doView)
        document.clientselectionForm.doView.value = "no";
    if (document.clientselectionForm.doCancel)
        document.clientselectionForm.doCancel.value = "no";
    if (document.clientselectionForm.doSave)
        document.clientselectionForm.doSave.value = "no";
    if (document.clientselectionForm.doGenerate)
        document.clientselectionForm.doGenerate.value = "no";
    if (document.clientselectionForm.doGenerateoffer)
        document.clientselectionForm.doGenerateoffer.value = "yes";
    document.forms[0].target = "";
    document.clientselectionForm.action = "../clientselection/ClientselectionAction.do";
    document.clientselectionForm.submit();
}

function getSelectCandidate(name, location, jobpostref, shortlistId) {
    var s = "<span>You have selected " + name + " from <br>" + location + " against Job Post " + jobpostref + "</span></br> ";
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
            document.clientselectionForm.shortlistId.value = shortlistId;
            if (document.clientselectionForm.doSelect)
                document.clientselectionForm.doSelect.value = "yes";
            document.forms[0].target = "";
            document.clientselectionForm.action = "../clientselection/ClientselectionAction.do";
            document.clientselectionForm.submit();
        }
    })
}

function getsetModal(shortlistId, candidateId, sflag, oflag) {
    document.forms[0].shortlistId.value = shortlistId;
    document.forms[0].candidateId.value = candidateId;
    if (sflag != '0' && oflag != '0') {
        document.getElementById("modalwithdecline").href = "javascript:  getGenerateoffer('" + shortlistId + "','" + sflag + "','" + candidateId + "','" + oflag + "');";
    }
}

function getRejectCandidate() {
    if (Rejectvalidation()) {
        if (document.forms[0].doReject)
            document.forms[0].doReject.value = "yes";
        document.forms[0].target = "";
        document.forms[0].action = "../clientselection/ClientselectionAction.do";
        document.forms[0].submit();
    }
}

function Rejectvalidation() {
    if (trim(document.forms[0].rejectReason.value) == "")
    {
        Swal.fire({
            title: "Please select atleast one reason.",
            didClose: () => {
                document.forms[0].rejectReason.focus();
            }
        })
        return false;
    }
    if (trim(document.forms[0].rejectRemark.value) == "")
    {
        Swal.fire({
            title: "Please enter remarks.",
            didClose: () => {
                document.forms[0].rejectRemark.focus();
            }
        })
        return false;
    }
    return true;
}

function viewCandidate(id)
{
    document.forms[0].doView.value = "yes";
    document.forms[0].candidateId.value = id;
    document.forms[0].target = "_blank";
    document.forms[0].action = "/jxp/talentpool/TalentpoolAction.do";
    document.forms[0].submit();
}

function ViewClient(id)
{
    document.forms[0].clientId.value = id;
    document.forms[0].doView.value = "yes";
    document.forms[0].target = "_blank";
    document.forms[0].action = "../client/ClientAction.do?tabno=1";
    document.forms[0].submit();
}

function getCandidateSummary(shortlistId)
{
    document.clientselectionForm.shortlistId.value = shortlistId;
    if (document.clientselectionForm.doView)
        document.clientselectionForm.doView.value = "no";
    if (document.clientselectionForm.doSummary)
        document.clientselectionForm.doSummary.value = "yes";
    document.clientselectionForm.target = "";
    document.clientselectionForm.action = "../clientselection/ClientselectionAction.do";
    document.clientselectionForm.submit();
}

function acceptvalidation() {
    if (trim(document.forms[0].txtAcceptremark.value) == "")
    {
        Swal.fire({
            title: "Please enter remarks.",
            didClose: () => {
                document.forms[0].txtAcceptremark.focus();
            }
        })
        return false;
    }
    return true;
}

function getAcceptCandidate() {
    if (acceptvalidation()) {
        if (document.forms[0].doAccept)
            document.forms[0].doAccept.value = "yes";
        document.forms[0].target = "";
        document.forms[0].action = "../clientselection/ClientselectionAction.do";
        document.forms[0].submit();
    }
}

function Declinevalidation() {
    if (trim(document.forms[0].declineReason.value) == "")
    {
        Swal.fire({
            title: "Please select atleast one reason.",
            didClose: () => {
                document.forms[0].declineReason.focus();
            }
        })
        return false;
    }
    if (trim(document.forms[0].txtDeclineremark.value) == "")
    {
        Swal.fire({
            title: "Please enter remarks.",
            didClose: () => {
                document.forms[0].txtDeclineremark.focus();
            }
        })
        return false;
    }
    return true;
}

function getDeclineCandidate() 
{
    if (Declinevalidation()) 
    {
        var s = "<span>The Candidate will be removed from the <br/> job post selection list for the client.</span></br> ";
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
                if (document.forms[0].doDecline)
                    document.forms[0].doDecline.value = "yes";
                document.forms[0].target = "";
                document.forms[0].action = "../clientselection/ClientselectionAction.do";
                document.forms[0].submit();
            }
        })
    }
}

function getEmailSummaryDetails(maillogid, sflag, shortlistid)
{
    var url = "../ajax/clientselection/getemaildetails.jsp";
    var httploc = getHTTPObject();
    var getstr = "";
    getstr += "maillogid=" + maillogid;
    getstr += "&sflag=" + sflag;
    getstr += "&shortlistid=" + shortlistid;
    httploc.open("POST", url, true);
    httploc.onreadystatechange = function ()
    {
        if (httploc.readyState == 4)
        {
            if (httploc.status == 200)
            {
                var response = httploc.responseText;
                document.getElementById('demailsummary').innerHTML = '';
                document.getElementById('demailsummary').innerHTML = response;
            }
        }
    };
    httploc.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    httploc.setRequestHeader("Content-length", getstr.length);
    httploc.setRequestHeader("Connection", "close");
    httploc.send(getstr);
    document.getElementById('demailsummary').innerHTML = "<div><img src='../assets/images/loading.gif' align='absmiddle'/>Please wait. Loading...</div>";
}

function releaseCandidate(candidateId, shortlistId)
{
    var s = "<span>The Candidate will be release from the <br/> job post selection list for the client.</span></br> ";
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
            if (document.forms[0].doRelease)
                document.forms[0].doRelease.value = "yes";
            document.clientselectionForm.candidateId.value = candidateId;
            document.clientselectionForm.shortlistId.value = shortlistId;
            document.forms[0].target = "";
            document.forms[0].action = "../clientselection/ClientselectionAction.do";
            document.forms[0].submit();
        }
    })
}