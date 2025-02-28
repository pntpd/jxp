function resetFilter()
{
    document.onboardingForm.search.value = "";
    document.onboardingForm.clientIdIndex.value = "-1";
    document.onboardingForm.assetIdIndex.value = "-1";
    searchFormAjax('s', '-1');
    setAssetDDL();
}

function view(clientId, clientassetId, assetcountryId)
{
    if (document.onboardingForm.doView)
        document.onboardingForm.doView.value = "yes";
    document.onboardingForm.clientId.value = clientId;
    document.onboardingForm.clientassetId.value = clientassetId;
    document.forms[0].assetcountryId.value = assetcountryId;
    document.forms[0].target = "";
    document.onboardingForm.action = "../onboarding/OnboardingAction.do";
    document.onboardingForm.submit();
}

function save()
{
    var ids = "";
    if (document.forms[0].onboardingkitIds)
    {
        if (document.forms[0].onboardingkitIds.length)
        {
            for (i = 0; i < document.forms[0].onboardingkitIds.length; i++)
            {
                if (document.forms[0].onboardingkitIds[i].checked)
                {
                    if (ids == '')
                        ids += (document.forms[0].onboardingkitIds[i].value);
                    else
                        ids += ("," + document.forms[0].onboardingkitIds[i].value);
                }
            }
        } else
        {
            if (document.forms[0].onboardingkitIds.checked)
            {
                ids += (document.forms[0].onboardingkitIds.value);
            }
        }
    }
    if (ids == "") {
        Swal.fire({
            title: "Please select atleast one kit.",
            didClose: () => {
                document.forms[0].toval.focus();
            }
        })
        return false;
    } else {
        if (document.onboardingForm.doSave)
            document.onboardingForm.doSave.value = "yes";
        document.forms[0].target = "";
        document.onboardingForm.action = "../onboarding/OnboardingAction.do";
        document.onboardingForm.submit();
    }
}

function viewgenerateformalities(shortlistId)
{
    document.onboardingForm.shortlistId.value = shortlistId;
    if (document.onboardingForm.doGenerate)
        document.onboardingForm.doGenerate.value = "yes";
    document.forms[0].target = "";
    document.onboardingForm.action = "../onboarding/OnboardingAction.do";
    document.onboardingForm.submit();
}

function checkUpload()
{

    if (document.forms[0].attachfile.value != "")
    {
        if (!(document.forms[0].attachfile.value).match(/(\.(pdf))$/i))
        {
            Swal.fire({
                title: "Only .pdf are allowed.",
                didClose: () => {
                    document.forms[0].attachfile.focus();
                }
            })
            return false;
        }
        var input = document.forms[0].attachfile;
        if (input.files)
        {
            var file = input.files[0];
            if (file.size > (1024 * 1024 * 20))
            {
                Swal.fire({
                    title: "File size should not exceed 20 MB.",
                    didClose: () => {
                        document.forms[0].attachfile.focus();
                    }
                })
                return false;
            }
        }
    } else
    {
        Swal.fire({
            title: "Please attachment.",
            didClose: () => {
                document.forms[0].attachfile.focus();
            }
        })
        return false;
    }
    return true;
}

function uploadedformalities(shortlistId)
{
    if (checkUpload())
    {
        document.onboardingForm.shortlistId.value = shortlistId;
        if (document.onboardingForm.doUpload)
            document.onboardingForm.doUpload.value = "yes";
        document.forms[0].target = "";
        document.onboardingForm.action = "../onboarding/OnboardingAction.do";
        document.onboardingForm.submit();
    }
}

function checkUploadzip()
{
    if (document.forms[0].attachfile.value != "")
    {
        if (!(document.forms[0].attachfile.value).match(/(\.(zip))$/i))
        {
            Swal.fire({
                title: "Only .zip are allowed.",
                didClose: () => {
                    document.forms[0].attachfile.focus();
                }
            })
            return false;
        }
        var input = document.forms[0].attachfile;
        if (input.files)
        {
            var file = input.files[0];
            if (file.size > (1024 * 1024 * 20))
            {
                Swal.fire({
                    title: "File size should not exceed 20 MB.",
                    didClose: () => {
                        document.forms[0].attachfile.focus();
                    }
                })
                return false;
            }
        }
    } else
    {
        Swal.fire({
            title: "Please attachment.",
            didClose: () => {
                document.forms[0].attachfile.focus();
            }
        })
        return false;
    }
    return true;
}

function uploadedformalitieszip(shortlistId)
{
    if (checkUploadzip())
    {
        document.onboardingForm.shortlistId.value = shortlistId;
        if (document.onboardingForm.doUpload)
            document.onboardingForm.doUpload.value = "yes";
        document.forms[0].target = "";
        document.onboardingForm.action = "../onboarding/OnboardingAction.do";
        document.onboardingForm.submit();
    }
}

function viewuploadedformalities(shortlistId)
{
    document.onboardingForm.shortlistId.value = shortlistId;
    if (document.onboardingForm.doUpload)
        document.onboardingForm.doUpload.value = "yes";
    document.forms[0].target = "";
    document.onboardingForm.action = "../onboarding/OnboardingAction.do";
    document.onboardingForm.submit();
}

function setOnboarddocId(onboarddocId)
{
    document.onboardingForm.onboarddocId.value = onboarddocId;
}

function addtolist()
{
    if (document.onboardingForm.formalityId.value > 0)
    {
        var url = "../ajax/onboarding/addlist.jsp";
        var httploc = getHTTPObject();
        var getstr = "";
        var clientassetId = escape(document.onboardingForm.clientassetId.value);
        var clientId = escape(document.onboardingForm.clientId.value);
        var formalityId = escape(document.onboardingForm.formalityId.value);
        var shortlistId = escape(document.onboardingForm.shortlistId.value);
        var candidateId = escape(document.onboardingForm.candidateId.value);
        getstr += "clientassetId=" + clientassetId;
        getstr += "&clientId=" + clientId;
        getstr += "&formalityId=" + formalityId;
        getstr += "&shortlistId=" + shortlistId;
        getstr += "&candidateId=" + candidateId;
        getstr += "&cval1=" + document.onboardingForm.cval1.value;
        getstr += "&cval2=" + document.onboardingForm.cval2.value;
        getstr += "&cval3=" + document.onboardingForm.cval3.value;
        getstr += "&cval4=" + document.onboardingForm.cval4.value;
        getstr += "&cval5=" + document.onboardingForm.cval5.value;
        getstr += "&cval6=" + document.onboardingForm.cval6.value;
        getstr += "&cval7=" + document.onboardingForm.cval7.value;
        getstr += "&cval8=" + document.onboardingForm.cval8.value;
        getstr += "&cval9=" + document.onboardingForm.cval9.value;
        getstr += "&cval10=" + document.onboardingForm.cval10.value;
        httploc.open("POST", url, true);
        httploc.onreadystatechange = function ()
        {
            if (httploc.readyState == 4)
            {
                if (httploc.status == 200)
                {
                    var response = httploc.responseText;
                    showformality(response.trim());
                    getformalitieslist();
                    document.getElementById('downloadzipId1').style.display = "";
                    document.getElementById('downloadzipId2').style.display = "none";
                    document.getElementById('downloadzipId3').style.display = "none";
                }
            }
        };
        httploc.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
        httploc.setRequestHeader("Content-length", getstr.length);
        httploc.setRequestHeader("Connection", "close");
        httploc.send(getstr);
    } else
    {
        Swal.fire({
            title: "Please select form.",
            didClose: () => {
                document.forms[0].toval.focus();
            }
        })
    }
}

function delfromlist(onboarddocId)
{
    var url = "../ajax/onboarding/deletelist.jsp";
    var httploc = getHTTPObject();
    var getstr = "";
    var shortlistId = escape(document.onboardingForm.shortlistId.value);
    getstr += "shortlistId=" + shortlistId;
    getstr += "&onboarddocId=" + onboarddocId;
    httploc.open("POST", url, true);
    httploc.onreadystatechange = function ()
    {
        if (httploc.readyState == 4)
        {
            if (httploc.status == 200)
            {
                var response = httploc.responseText;
                getformalities();
                getformalitieslist();
            }
        }
    };
    httploc.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    httploc.setRequestHeader("Content-length", getstr.length);
    httploc.setRequestHeader("Connection", "close");
    httploc.send(getstr);
}

function getformalities()
{
    var url = "../ajax/onboarding/getformalities.jsp";
    var httploc = getHTTPObject();
    var getstr = "";
    var clientassetId = escape(document.onboardingForm.clientassetId.value);
    var clientId = escape(document.onboardingForm.clientId.value);
    getstr += "clientassetId=" + clientassetId;
    getstr += "&clientId=" + clientId;
    httploc.open("POST", url, true);
    httploc.onreadystatechange = function ()
    {
        if (httploc.readyState == 4)
        {
            if (httploc.status == 200)
            {
                var response = httploc.responseText;
                document.getElementById('formalityId').innerHTML = '';
                document.getElementById('formalityId').innerHTML = response;
            }
        }
    };
    httploc.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    httploc.setRequestHeader("Content-length", getstr.length);
    httploc.setRequestHeader("Connection", "close");
    httploc.send(getstr);
}

function deluploadfromlist(onboarddocId)
{
    var url = "../ajax/onboarding/deleteuploadlist.jsp";
    var httploc = getHTTPObject();
    var getstr = "";
    var clientassetId = escape(document.onboardingForm.clientassetId.value);
    var clientId = escape(document.onboardingForm.clientId.value);
    var shortlistId = escape(document.onboardingForm.shortlistId.value);
    getstr += "clientassetId=" + clientassetId;
    getstr += "&clientId=" + clientId;
    getstr += "&shortlistId=" + shortlistId;
    getstr += "&onboarddocId=" + onboarddocId;
    httploc.open("POST", url, true);
    httploc.onreadystatechange = function ()
    {
        if (httploc.readyState == 4)
        {
            if (httploc.status == 200)
            {
                var response = httploc.responseText;
                viewuploadedformalities(shortlistId);

            }
        }
    };
    httploc.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    httploc.setRequestHeader("Content-length", getstr.length);
    httploc.setRequestHeader("Connection", "close");
    httploc.send(getstr);
}

function getformalitieslist()
{
    var url = "../ajax/onboarding/getformalitieslist.jsp";
    var httploc = getHTTPObject();
    var getstr = "";
    var clientassetId = escape(document.onboardingForm.clientassetId.value);
    var clientId = escape(document.onboardingForm.clientId.value);
    var shortlistId = escape(document.onboardingForm.shortlistId.value);
    getstr += "clientassetId=" + clientassetId;
    getstr += "&clientId=" + clientId;
    getstr += "&shortlistId=" + shortlistId;
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
                document.getElementById('ajax_cat').innerHTML = '';
                document.getElementById('ajax_cat').innerHTML = v1;
                if (Number(v2) <= 0)
                {                    
                    document.getElementById('downloadzipId1').style.display = "none";
                    document.getElementById('downloadzipId2').style.display = "none";
                    document.getElementById('downloadzipId3').style.display = "";
                }
                else
                {
                    document.getElementById('downloadzipId1').style.display = "";
                    document.getElementById('downloadzipId2').style.display = "none";
                    document.getElementById('downloadzipId3').style.display = "none";
                    document.onboardingForm.generatedlistsize.value = v2;
                }
            }
        }
    };
    httploc.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    httploc.setRequestHeader("Content-length", getstr.length);
    httploc.setRequestHeader("Connection", "close");
    httploc.send(getstr);
    document.getElementById('ajax_cat').innerHTML = "<div><img src='../assets/images/loading.gif' align='absmiddle'/>Please wait. Loading...</div>";
}

function showformality(pdfile)
{
    setIframe(pdfile);
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

function gettravelModel(onboardflag, shortlistId)
{
    document.onboardingForm.shortlistId.value = shortlistId;
    var url = "../ajax/onboarding/getmailtravelmodaldetails.jsp";
    var httploc = getHTTPObject();
    var getstr = "";
    getstr += "shortlistId=" + shortlistId;
    getstr += "&onboardflag=" + onboardflag;
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
                document.getElementById('travelmailmodel').innerHTML = '';
                document.getElementById('travelmailmodel').innerHTML = trim(v1);
                $(function () {
                    $("#upload_link_4").on('click', function (e) {
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
    document.getElementById('travelmailmodel').innerHTML = "<div><img src='../assets/images/loading.gif' align='absmiddle'/>Please wait. Loading...</div>";
}

function getModelexternal(onboardflag, shortlistId)
{
    document.onboardingForm.shortlistId.value = shortlistId;
    var url = "../ajax/onboarding/getmailmodaldetailsext.jsp";
    var httploc = getHTTPObject();
    var getstr = "";
    getstr += "shortlistId=" + shortlistId;
    getstr += "&onboardflag=" + onboardflag;
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
                    $("#upload_link_3").on('click', function (e) {
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

function getModel(onboardflag)
{
    var url = "../ajax/onboarding/getmailmodaldetails.jsp";
    var httploc = getHTTPObject();
    var getstr = "";
    getstr += "shortlistId=" + escape(document.onboardingForm.shortlistId.value);
    getstr += "&onboardflag=" + onboardflag;
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
                document.onboardingForm.zipfilename.value = v2.trim();
            }
        }
    };
    httploc.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    httploc.setRequestHeader("Content-length", getstr.length);
    httploc.setRequestHeader("Connection", "close");
    httploc.send(getstr);
    document.getElementById('mailmodal').innerHTML = "<div><img src='../assets/images/loading.gif' align='absmiddle'/>Please wait. Loading...</div>";
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
    if (document.forms[0].attachfile.value != "")
    {
        if (!(document.forms[0].attachfile.value).match(/(\.(zip))$/i))
        {
            Swal.fire({
                title: "Only .zip are allowed.",
                didClose: () => {
                    document.forms[0].attachfile.focus();
                }
            })
            return false;
        }
        var input = document.forms[0].attachfile;
        if (input.files)
        {
            var file = input.files[0];
            if (file.size > (1024 * 1024 * 20))
            {
                Swal.fire({
                    title: "File size should not exceed 20 MB.",
                    didClose: () => {
                        document.forms[0].attachfile.focus();
                    }
                })
                return false;
            }
        }
    } else
    {
        Swal.fire({
            title: "Please attachment.",
            didClose: () => {
                document.forms[0].attachfile.focus();
            }
        })
        return false;
    }
    return true;
}

function checkMailnoattach()
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

function submitmailForm()
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
    if (checkMailnoattach())
    {
        document.getElementById("dsavesendmail").innerHTML = "<div><img src='../assets/images/loading.gif' align='absmiddle'/>Please wait. Loading...</div>";
        if (document.onboardingForm.doMail)
            document.onboardingForm.doMail.value = "yes";
        document.forms[0].target = "";
        document.onboardingForm.action = "../onboarding/OnboardingAction.do";
        document.onboardingForm.submit();
    }
}

function submittravelmailForm()
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
    if (checkMailtravel())
    {
        document.getElementById("dsavesendtravelmail").innerHTML = "<div><img src='../assets/images/loading.gif' align='absmiddle'/>Please wait. Loading...</div>";
        if (document.onboardingForm.doMailtravel)
            document.onboardingForm.doMailtravel.value = "yes";
        document.forms[0].target = "";
        document.onboardingForm.action = "../onboarding/OnboardingAction.do";
        document.onboardingForm.submit();
    }
}

function gobackview()
{
    if (document.onboardingForm.doView)
        document.onboardingForm.doView.value = "yes";
    document.forms[0].target = "";
    document.onboardingForm.action = "../onboarding/OnboardingAction.do";
    document.onboardingForm.submit();
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
        var url = "../ajax/onboarding/getinfo.jsp";
        var httploc = getHTTPObject();
        var getstr = "";
        var next_value = escape(document.onboardingForm.nextValue.value);
        var search_value = escape(document.onboardingForm.search.value);
        var assetIdIndex = escape(document.onboardingForm.assetIdIndex.value);
        var clientIdIndex = escape(document.onboardingForm.clientIdIndex.value);
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
    if (document.onboardingForm.doView)
        document.onboardingForm.doView.value = "no";
    if (document.onboardingForm.doCancel)
        document.onboardingForm.doCancel.value = "yes";
    if (document.onboardingForm.doSave)
        document.onboardingForm.doSave.value = "no";
    document.forms[0].target = "";
    document.onboardingForm.action = "../onboarding/OnboardingAction.do";
    document.onboardingForm.submit();
}

function sortForm(colid, updown)
{
    for (i = 1; i <= 2; i++)
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
    var url_sort = "../ajax/onboarding/sort.jsp";
    var getstr = "";
    var nextValue = 0;
    if (document.onboardingForm.nextValue)
        nextValue = document.onboardingForm.nextValue.value;
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
    document.onboardingForm.reset();
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
    document.onboardingForm.action = "../onboarding/OnboardingExportAction.do";
    document.onboardingForm.submit();
}

function setAssetDDL()
{
    var url = "../ajax/onboarding/getasset.jsp";
    document.getElementById("assetIdIndex").value = '-1';
    var httploc = getHTTPObject();
    var getstr = "clientIdIndex=" + document.onboardingForm.clientIdIndex.value + "&from=asset";
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
    var url = "../ajax/onboarding/getasset.jsp";
    document.getElementById("pgvalue").value = '';
    var httploc = getHTTPObject();
    var getstr = "assetIdIndex=" + document.onboardingForm.assetIdIndex.value + "&from=position";
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
    searchFormAjax('s', '-1');
}

function ViewAsset(id)
{
    if (id == "") {
        var id = document.forms[0].clientid.value;
    }
    document.forms[0].clientId.value = id;
    document.forms[0].doView.value = "yes";
    document.forms[0].target = "_blank";
    document.forms[0].action = "../client/ClientAction.do?tabno=2";
    document.forms[0].submit();
}

function getSearchSelectedCandList()
{
    var httploc = getHTTPObject();
    var url_sort = "../ajax/onboarding/getselectedcandidate.jsp";
    var getstr = "";
    getstr += "onstatus=" + document.onboardingForm.onstatus.value;
    getstr += "&searchon=" + document.onboardingForm.txtsearch.value;
    getstr += "&clientId=" + document.onboardingForm.clientId.value;
    getstr += "&clientassetId=" + document.onboardingForm.clientassetId.value;
    httploc.open("POST", url_sort, true);
    httploc.onreadystatechange = function ()
    {
        if (httploc.readyState == 4)
        {
            if (httploc.status == 200)
            {
                var response = httploc.responseText;
                document.getElementById('dselectedcandidate').innerHTML = '';
                document.getElementById('dselectedcandidate').innerHTML = response;
                $(function () {
                    $('[data-toggle="tooltip"]').tooltip()
                });
            }
        }
    };
    httploc.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    httploc.setRequestHeader("Content-length", getstr.length);
    httploc.setRequestHeader("Connection", "close");
    httploc.send(getstr);
    document.getElementById('dselectedcandidate').innerHTML = "<div><img src='../assets/images/loading.gif' align='absmiddle'/>Please wait. Loading...</div>";
}

function viewCandidate(id)
{
    document.forms[0].doView.value = "yes";
    document.forms[0].candidateId.value = id;
    document.forms[0].target = "_blank";
    document.forms[0].action = "/jxp/talentpool/TalentpoolAction.do";
    document.forms[0].submit();
}

function ViewClient()
{
    document.forms[0].doView.value = "yes";
    document.forms[0].target = "_blank";
    document.forms[0].action = "../client/ClientAction.do?tabno=1";
    document.forms[0].submit();
}

function getTraveldetails(id, type) 
{
    document.onboardingForm.shortlistId.value = id;
    var httploc = getHTTPObject();
    var url_sort = "../ajax/onboarding/gettraveldtls.jsp";
    var getstr = "";
    getstr += "shortlistid=" + id;
    getstr += "&type=" + type;
    httploc.open("POST", url_sort, true);
    httploc.onreadystatechange = function ()
    {
        if (httploc.readyState == 4)
        {
            if (httploc.status == 200)
            {
                var response = httploc.responseText;
                document.getElementById('dTravelDtls').innerHTML = '';
                document.getElementById('dTravelDtls').innerHTML = response;
                //Datepicker
                $(".kt-selectpicker").selectpicker();
                $(".wesl_dt").datepicker({
                    todayHighlight: !0,
                    format: "dd-M-yyyy",
                    autoclose: "true",
                    orientation: "bottom"
                });
                //Timepicker
                $(".timepicker-24").timepicker({
                    format: 'mm:mm',
                    autoclose: !0,
                    minuteStep: 5,
                    showSeconds: !1,
                    showMeridian: !1
                });
                $("#upload_link_1").on('click', function (e) {
                    e.preventDefault();
                    $("#upload1:hidden").trigger('click');
                });
                $(".wrapper1").scroll(function () {
                    $(".wrapper2")
                            .scrollLeft($(".wrapper1").scrollLeft());
                });
                $(".wrapper2").scroll(function () {
                    $(".wrapper1")
                            .scrollLeft($(".wrapper2").scrollLeft());
                });

                $(".wrapper01").scroll(function () {
                    $(".wrapper02")
                            .scrollLeft($(".wrapper01").scrollLeft());
                });
                $(".wrapper02").scroll(function () {
                    $(".wrapper01")
                            .scrollLeft($(".wrapper02").scrollLeft());
                });
            }
        }
    };
    httploc.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    httploc.setRequestHeader("Content-length", getstr.length);
    httploc.setRequestHeader("Connection", "close");
    httploc.send(getstr);
    document.getElementById('dTravelDtls').innerHTML = "<div><img src='../assets/images/loading.gif' align='absmiddle'/>Please wait. Loading...</div>";
    $('#add_travel_details_modal').modal('show');
}

function getAccommDetails(id, type) 
{
    if (id == "") {
        id = document.onboardingForm.shortlistId.value;
    } else {
        document.onboardingForm.shortlistId.value = id;
    }
    var httploc = getHTTPObject();
    var url_sort = "../ajax/onboarding/getaccommdtls.jsp";
    var getstr = "";
    getstr += "shortlistid=" + id;
    getstr += "&type=" + type;
    httploc.open("POST", url_sort, true);
    httploc.onreadystatechange = function ()
    {
        if (httploc.readyState == 4)
        {
            if (httploc.status == 200)
            {
                var response = httploc.responseText;
                document.getElementById('dAccommDtls').innerHTML = '';
                document.getElementById('dAccommDtls').innerHTML = response;
                //Datepicker
                $(".kt-selectpicker").selectpicker();
                $(".wesl_dt").datepicker({
                    todayHighlight: !0,
                    format: "dd-M-yyyy",
                    autoclose: "true",
                    orientation: "bottom"
                });
                //Timepicker
                $(".timepicker-24").timepicker({
                    format: 'mm:mm',
                    autoclose: !0,
                    minuteStep: 5,
                    showSeconds: !1,
                    showMeridian: !1
                });
                $("#upload_link_1").on('click', function (e) {
                    e.preventDefault();
                    $("#upload1:hidden").trigger('click');
                });
                $(".wrapper1").scroll(function () {
                    $(".wrapper2")
                            .scrollLeft($(".wrapper1").scrollLeft());
                });
                $(".wrapper2").scroll(function () {
                    $(".wrapper1")
                            .scrollLeft($(".wrapper2").scrollLeft());
                });

                $(".wrapper01").scroll(function () {
                    $(".wrapper02")
                            .scrollLeft($(".wrapper01").scrollLeft());
                });
                $(".wrapper02").scroll(function () {
                    $(".wrapper01")
                            .scrollLeft($(".wrapper02").scrollLeft());
                });
            }
        }
    };
    httploc.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    httploc.setRequestHeader("Content-length", getstr.length);
    httploc.setRequestHeader("Connection", "close");
    httploc.send(getstr);
    document.getElementById('dAccommDtls').innerHTML = "<div><img src='../assets/images/loading.gif' align='absmiddle'/>Please wait. Loading...</div>";
    $('#add_accom_details_modal').modal('show');
}

function checkMobDtls(type)
{
    var tempval = "";
    if (Number(document.forms[0].val1.value) == "")
    {
        tempval = "";
        if (type == 1) {
            tempval = "Please select mode.";
        } else if (type == 2) {
            tempval = "Please enter hotel name.";
        }
        Swal.fire({
            title: tempval,
            didClose: () => {
                document.forms[0].val1.focus();
            }
        })
        return false;
    }
    if (type == 2) {
        if (validdesc(document.forms[0].val1) == false)
        {
            return false;
        }
    }
    if (trim(document.forms[0].val2.value) == "")
    {
        tempval = "";
        if (type == 1) {
            tempval = "Please enter departure location.";
        } else if (type == 2) {
            tempval = "Please enter address.";
        }
        Swal.fire({
            title: tempval,
            didClose: () => {
                document.forms[0].val2.focus();
            }
        })
        return false;
    }
    if (validdesc(document.forms[0].val2) == false)
    {
        return false;
    }
    if (type == 2) 
    {
        if (trim(document.forms[0].val3.value) == "")
        {
            Swal.fire({
                title: "Please enter contact number",
                didClose: () => {
                    document.forms[0].val3.focus();
                }
            })
            return false;
        }
        if (validdesc(document.forms[0].val3) == false)
        {
            return false;
        }
    }
    if (type == 2) 
    {
        if (trim(document.forms[0].val4.value) == "")
        {
            Swal.fire({
                title: "Please enter room no.",
                didClose: () => {
                    document.forms[0].val4.focus();
                }
            })
            return false;
        }
        if (validdesc(document.forms[0].val4) == false)
        {
            return false;
        }
    }
    if (type == 1) 
    {
        if (trim(document.forms[0].val5.value) == "")
        {
            Swal.fire({
                title: "Please enter Flight/train/Bus/Car no.",
                didClose: () => {
                    document.forms[0].val5.focus();
                }
            })
            return false;
        }
        if (validdesc(document.forms[0].val5) == false)
        {
            return false;
        }
    }
    if (document.forms[0].vald9.value == "")
    {
        tempval = "";
        if (type == 1) {
            tempval = "Please select ETD - date.";
        } else if (type == 2) {
            tempval = "Please select check in date.";
        }
        Swal.fire({
            title: tempval,
            didClose: () => {
                document.forms[0].vald9.focus();
            }
        })
        return false;
    }
    if (document.forms[0].valt9.value == "")
    {
        tempval = "";
        if (type == 1) {
            tempval = "Please select ETD - time.";
        } else if (type == 2) {
            tempval = "Please select check in time.";
        }
        Swal.fire({
            title: tempval,
            didClose: () => {
                document.forms[0].valt9.focus();
            }
        })
        return false;
    }
    if (document.forms[0].vald10.value == "")
    {
        tempval = "";
        if (type == 1) {
            tempval = "Please select ETA - date.";
        } else if (type == 2) {
            tempval = "Please select check out date.";
        }
        Swal.fire({
            title: tempval,
            didClose: () => {
                document.forms[0].vald10.focus();
            }
        })
        return false;
    }
    if (document.forms[0].valt10.value == "")
    {
        tempval = "";
        if (type == 1) {
            tempval = "Please select ETA - time.";
        } else if (type == 2) {
            tempval = "Please select check out time.";
        }
        Swal.fire({
            title: tempval,
            didClose: () => {
                document.forms[0].valt10.focus();
            }
        })
        return false;
    }
    if (compareDateTime(document.forms[0].vald9.value + " " + document.forms[0].valt9.value, document.forms[0].vald10.value + " " + document.forms[0].valt10.value) == false) {
        tempval = "";
        if (type == 1) {
            tempval = "Arrival date time should be greater than departure date time.";
        } else if (type == 2) {
            tempval = "Check out date time should be greater than check in date time.";
        }
        Swal.fire({
            title: tempval,
        })
        return false;
    }
    if (trim(document.forms[0].val6.value) == "")
    {
        tempval = "";
        if (type == 1) {
            tempval = "Please enter arrival location.";
        } else if (type == 2) {
            tempval = "Please enter remarks.";
        }
        Swal.fire({
            title: tempval,
            didClose: () => {
                document.forms[0].val6.focus();
            }
        })
        return false;
    }
    if (validdesc(document.forms[0].val6) == false)
    {
        return false;
    }
    if (document.forms[0].upload1.value != "")
    {
        if (!(document.forms[0].upload1.value).match(/(\.(pdf))$/i))
        {
            Swal.fire({
                title: "Only .pdf are allowed.",
                didClose: () => {
                    document.forms[0].upload1.focus();
                }
            })
            return false;
        }
        var input = document.forms[0].upload1;
        if (input.files)
        {
            var file = input.files[0];
            if (file.size > (1024 * 1024 * 5))
            {
                Swal.fire({
                    title: "File size should not exceed 5 MB.",
                    didClose: () => {
                        document.forms[0].upload1.focus();
                    }
                })
                return false;
            }
        }
    }
    return true;
}

function getSaveMobi(id, type) 
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
    if (checkMobDtls(type))
    {
        var httploc = getHTTPObject();
        var url_sort = "../ajax/onboarding/insert_ondtls.jsp";
        var getstr = "";
        getstr += "shortlistid=" + id;
        getstr += "&val1=" + document.onboardingForm.val1.value;
        getstr += "&val1=" + document.onboardingForm.val1.value;
        document.onboardingForm.val1.value = "";
        getstr += "&val2=" + document.onboardingForm.val2.value;
        document.onboardingForm.val2.value = "";
        getstr += "&val3=" + document.onboardingForm.val3.value;
        document.onboardingForm.val3.value = "";
        getstr += "&val4=" + document.onboardingForm.val4.value;
        document.onboardingForm.val4.value = "";
        getstr += "&val5=" + document.onboardingForm.val5.value;
        document.onboardingForm.val5.value = "";
        getstr += "&val6=" + document.onboardingForm.val6.value;
        document.onboardingForm.val6.value = "";
        if (document.onboardingForm.val7) {
            getstr += "&val7=" + document.onboardingForm.val7.value;
            document.onboardingForm.val7.value = "";
        }
        if (document.onboardingForm.val8) {
            getstr += "&val8=" + document.onboardingForm.val8.value;
            document.onboardingForm.val8.value = "";
        }
        getstr += "&vald9=" + document.onboardingForm.vald9.value;
        document.onboardingForm.vald9.value = "";
        getstr += "&valt9=" + document.onboardingForm.valt9.value;
        getstr += "&vald10=" + document.onboardingForm.vald10.value;
        document.onboardingForm.vald10.value = "";
        getstr += "&valt10=" + document.onboardingForm.valt10.value;
        getstr += "&filename=" + document.onboardingForm.upload1.value;
        document.getElementById("upload_link_1").className = "attache_btn attache_btn_white uploaded_img1";
        getstr += "&type=" + type;
        httploc.open("POST", url_sort, true);
        httploc.onreadystatechange = function ()
        {
            if (httploc.readyState == 4)
            {
                if (httploc.status == 200)
                {
                    var response = httploc.responseText;
                    if (type == 1) {
                        document.getElementById('tbodytravel').innerHTML = '';
                        document.getElementById('tbodytravel').innerHTML = response;
                    } else if (type == 2) {
                        document.getElementById('tbodyaccomm').innerHTML = '';
                        document.getElementById('tbodyaccomm').innerHTML = response;
                    }
                }
            }
        };
        getstr += "&attachfile=" + encodeURIComponent(document.getElementById("hdnfilename").value);
        document.getElementById("hdnfilename").value = "";
        httploc.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
        httploc.setRequestHeader("Content-length", getstr.length);
        httploc.setRequestHeader("Connection", "close");
        httploc.send(getstr);
        if (type == 1) {
            document.getElementById('tbodytravel').innerHTML = "<div><img src='../assets/images/loading.gif' align='absmiddle'/>Please wait. Loading...</div>";
        } else if (type == 2) {
            document.getElementById('tbodyaccomm').innerHTML = "<div><img src='../assets/images/loading.gif' align='absmiddle'/>Please wait. Loading...</div>";
        }
    }
}

function getTotoFrom(sId, countryId, from) 
{
    $('#add_accom_details_modal').modal('hide');
    $('#add_travel_details_modal').modal('hide');
    if (from == 'travel') {
        getAccommodationDtls(sId, countryId, 'view')
    } else if (from == 'accomm') {
        getMobandAcc(sId, countryId, 'view');
    }
}

function getFillTarvelDtls(val) 
{
    if (val == 1) {
        document.onboardingForm.arrFlightName.value = document.onboardingForm.depFlightName.value;
    } else if (val == 2) {
        document.onboardingForm.arrFlightNumber.value = document.onboardingForm.depFlightNumber.value;
    } else if (val == 3) {
        document.onboardingForm.arrSeatNumber.value = document.onboardingForm.depSeatNumber.value;
    }
}

function getRemoveRDO() 
{
    document.getElementById("arrFlightName").readOnly = false;
    document.getElementById("arrFlightNumber").readOnly = false;
    document.getElementById("arrSeatNumber").readOnly = false;
}

function getNotReqTravel(type) 
{
    if (type == "1") 
    {
        if (document.getElementById("chkNotReqTravel").checked == true) {
            document.getElementById("dMobAccNotReq").style.display = "none";
            document.getElementById("dnotereqsize").className = "row client_position_table";
        } else {
            document.getElementById("dMobAccNotReq").style.display = "";
            document.getElementById("dnotereqsize").className = "row client_position_table1";
        }
    } else if (type == 2)
    {
        if (document.getElementById("chkAccommodation").checked == true) {
            document.getElementById("dAccommodationNotReq").style.display = "none";
            document.getElementById("dnotereqsize").className = "row client_position_table";
        } else {
            document.getElementById("dAccommodationNotReq").style.display = "";
            document.getElementById("dnotereqsize").className = "row client_position_table1";
        }
    }
}

function getReqdoc(sId) 
{
    document.onboardingForm.shortlistId.value = sId;
    if (document.getElementById("chkreqdoc" + sId).checked == true) 
    {
        $("#chkreqdoc" + sId).prop("checked", false);
    }
    var httploc = getHTTPObject();
    var url_sort = "../ajax/onboarding/getreqdoclist.jsp";
    var getstr = "";
    getstr += "shortlistid=" + sId;
    httploc.open("POST", url_sort, true);
    httploc.onreadystatechange = function ()
    {
        if (httploc.readyState == 4)
        {
            if (httploc.status == 200)
            {
                var response = httploc.responseText;
                document.getElementById('dmodalreqdoc').innerHTML = '';
                document.getElementById('dmodalreqdoc').innerHTML = response;
            }
        }
    };
    httploc.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    httploc.setRequestHeader("Content-length", getstr.length);
    httploc.setRequestHeader("Connection", "close");
    httploc.send(getstr);
    document.getElementById('dmodalreqdoc').innerHTML = "<div><img src='../assets/images/loading.gif' align='absmiddle'/>Please wait. Loading...</div>";
    $('#req_documents_onboarding_modal').modal('show');
}

function getDocCheckList(sId, from) 
{
    document.onboardingForm.shortlistId.value = sId;
    var httploc = getHTTPObject();
    var url_sort = "../ajax/onboarding/getdocchecklist.jsp";
    var getstr = "";
    getstr += "shortlistid=" + sId;
    getstr += "&from=" + from;
    httploc.open("POST", url_sort, true);
    httploc.onreadystatechange = function ()
    {
        if (httploc.readyState == 4)
        {
            if (httploc.status == 200)
            {
                var response = httploc.responseText;
                document.getElementById('dmodaldocchecklist').innerHTML = '';
                document.getElementById('dmodaldocchecklist').innerHTML = response;
            }
        }
    };
    httploc.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    httploc.setRequestHeader("Content-length", getstr.length);
    httploc.setRequestHeader("Connection", "close");
    httploc.send(getstr);
    document.getElementById('dmodaldocchecklist').innerHTML = "<div><img src='../assets/images/loading.gif' align='absmiddle'/>Please wait. Loading...</div>";
    $('#req_documents_checklist_modal').modal('show');
}


function submitReqDoc() 
{
    document.onboardingForm.doSaveReqDoc.value = "yes";
    document.forms[0].target = "";
    document.onboardingForm.action = "../onboarding/OnboardingAction.do";
    document.onboardingForm.submit();
}

function submitDocCheckList() 
{
    var chval = 1;
    if (document.forms[0].chkdoccheckListId)
    {
        if (document.forms[0].chkdoccheckListId.length)
        {
            for (i = 0; i < document.forms[0].chkdoccheckListId.length; i++)
            {
                if (document.forms[0].chkdoccheckListId[i].checked == false)
                {
                    chval = 0;
                    break;
                }
            }
        } else
        {
            if (document.forms[0].chkdoccheckListId.checked == false)
            {
                chval = 0
            }
        }
    }
    document.forms[0].reqcheckstatus.value = chval;
    document.onboardingForm.doSaveDocCheck.value = "yes";
    document.forms[0].target = "";
    document.onboardingForm.action = "../onboarding/OnboardingAction.do";
    document.onboardingForm.submit();
}

function setClass(tp)
{
    document.getElementById("upload_link_" + tp).className = "attache_btn attache_btn_white uploaded_img";
}

//----------------------------------------------------externalmail----------------------------------------------

function submitexternalmailForm()
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
        document.getElementById('dexternalmail').innerHTML = "<div><img src='../assets/images/loading.gif' align='absmiddle'/>Please wait. Loading...</div>";
        if (document.onboardingForm.doMail)
            document.onboardingForm.doMail.value = "yes";
        document.forms[0].target = "";
        document.onboardingForm.action = "../onboarding/OnboardingAction.do";
        document.onboardingForm.submit();
    }
}

//------------------------------------------------------------travel--------------------------------------------
function checkMailtravel()
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

    if (document.forms[0].attachfile.value != "")
    {
        if (!(document.forms[0].attachfile.value).match(/(\.(pdf))$/i))
        {
            Swal.fire({
                title: "Only .pdf are allowed.",
                didClose: () => {
                    document.forms[0].attachfile.focus();
                }
            })
            return false;
        }
        var input = document.forms[0].attachfile;
        if (input.files)
        {
            var allowedfilesize = Number(document.forms[0].tafilesize.value);
            var file = input.files[0];
            var filesize = ((file.size) / (1024 * 1024));
            if (filesize > (allowedfilesize))
            {
                Swal.fire({
                    title: "File size should not exceed " + allowedfilesize + "MB.",
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

function addReqDocForm()
{
    document.forms[0].doAdd.value = "yes";
    document.forms[0].target = "_blank";
    document.forms[0].action = "../documenttype/DocumentTypeAction.do";
    document.forms[0].submit();
}

function deleteExternalfile()
{
    if (document.onboardingForm.doDeleteExt)
        document.onboardingForm.doDeleteExt.value = "yes";
    document.forms[0].target = "";
    document.onboardingForm.action = "../onboarding/OnboardingAction.do";
    document.onboardingForm.submit();
}

function getSummaryDtls(ShortlistId)
{
    document.onboardingForm.shortlistId.value = ShortlistId;
    if (document.onboardingForm.doSummary)
        document.onboardingForm.doSummary.value = "yes";
    document.forms[0].target = "";
    document.onboardingForm.action = "../onboarding/OnboardingAction.do";
    document.onboardingForm.submit();
}

function gobackonclick()
{
    var glistsize = document.onboardingForm.generatedlistsize.value;
    if (glistsize > 0)
    {
        for (var i = 1; i <= glistsize; i++)
        {
            document.getElementById("dellistid" + i + "").innerHTML = "";
        }
    }
    setTimeout(function ()
    {
        if (document.onboardingForm.doView) {
            document.onboardingForm.doView.value = "yes";
        }
        document.forms[0].action = "../onboarding/OnboardingAction.do";
        document.forms[0].submit();
    }, 1000);
}

function dvalidation(onboardflag)
{
    Swal.fire({
        title: "Please generate atleast 1 file.",
        didClose: () => {
            document.forms[0].formalityId.focus();
        }
    })
}

//----------------------------------------------------------- NEW TRAVEL & ACCOMMODATION DTLS -------------------------------------------------------------

function getDeleteMobDtls(onid, type) 
{
    var httploc = getHTTPObject();
    var url_sort = "../ajax/onboarding/delete_ondtls.jsp";
    var getstr = "";
    getstr += "onid=" + onid;
    getstr += "&type=" + type;
    httploc.open("POST", url_sort, true);
    httploc.onreadystatechange = function ()
    {
        if (httploc.readyState == 4)
        {
            if (httploc.status == 200)
            {
                var response = httploc.responseText;
                if (type == 1) {
                    document.getElementById('tbodytravel').innerHTML = '';
                    document.getElementById('tbodytravel').innerHTML = response;
                } else if (type == 2) {
                    document.getElementById('tbodyaccomm').innerHTML = '';
                    document.getElementById('tbodyaccomm').innerHTML = response;
                }
            }
        }
    };
    httploc.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    httploc.setRequestHeader("Content-length", getstr.length);
    httploc.setRequestHeader("Connection", "close");
    httploc.send(getstr);
    if (type == 1) {
        document.getElementById('tbodytravel').innerHTML = "<div><img src='../assets/images/loading.gif' align='absmiddle'/>Please wait. Loading...</div>";
    } else if (type == 2) {
        document.getElementById('tbodyaccomm').innerHTML = "<div><img src='../assets/images/loading.gif' align='absmiddle'/>Please wait. Loading...</div>";
    }
}

function setofferfilebase()
{
    document.getElementById("upload_link_1").className = "attache_btn uploaded_img";
    if (document.forms[0].upload1.value != "")
    {
        var input = document.forms[0].upload1;
        if (input.files)
        {
            var file = input.files[0];
            if (file.size > (1024 * 1024 * 5))
            {
                Swal.fire({
                    title: "File size should not exceed 5 MB.",
                    didClose: () => {
                        document.forms[0].upload1.focus();
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
                        document.getElementById("hdnfilename").value = srcData;
                        //   attachfile = srcData;
                    }
                    fileReader.readAsDataURL(fileToLoad);
                }
            }
        }
    }
}

function getSaveTravel() 
{
    if (document.getElementById("chkNotReqTravel").checked == false) {
        if (Number(document.getElementById("mobcount").value) <= 0) {
            Swal.fire({
                title: "Please add atleast one data.",
            })
            return false;
        }
    }
    if (document.onboardingForm.doSaveTravel)
        document.onboardingForm.doSaveTravel.value = "yes";
    document.forms[0].target = "";
    document.onboardingForm.action = "../onboarding/OnboardingAction.do";
    document.onboardingForm.submit();
}

function getSaveAccomm() 
{
    if (document.getElementById("chkAccommodation").checked == false) {
        if (Number(document.getElementById("mobcount").value) <= 0) {
            Swal.fire({
                title: "Please add atleast one data.",
            })
            return false;
        }
    }
    if (document.onboardingForm.doSaveAccomm)
        document.onboardingForm.doSaveAccomm.value = "yes";
    document.forms[0].target = "";
    document.onboardingForm.action = "../onboarding/OnboardingAction.do";
    document.onboardingForm.submit();
}

function getViewTravel(id, type) 
{
    document.onboardingForm.shortlistId.value = id;
    document.onboardingForm.type.value = type;
    if (document.onboardingForm.doMobTravel)
        document.onboardingForm.doMobTravel.value = "yes";
    document.forms[0].target = "";
    document.onboardingForm.action = "../onboarding/OnboardingAction.do";
    document.onboardingForm.submit();
}

function getViewAccomm(id, type) {
    document.onboardingForm.shortlistId.value = id;
    document.onboardingForm.type.value = type;
    if (document.onboardingForm.doMobAccomm)
        document.onboardingForm.doMobAccomm.value = "yes";
    document.forms[0].target = "";
    document.onboardingForm.action = "../onboarding/OnboardingAction.do";
    document.onboardingForm.submit();
}

function openTab(obj)
{
    if (obj == '1') 
    {
        document.onboardingForm.type.value = obj;
        if (document.onboardingForm.doMobTravel)
            document.onboardingForm.doMobTravel.value = "yes";
    } else if (obj == '2')
    {
        document.onboardingForm.type.value = obj;
        if (document.onboardingForm.doMobAccomm)
            document.onboardingForm.doMobAccomm.value = "yes";
    }
    document.forms[0].target = "";
    document.onboardingForm.action = "../onboarding/OnboardingAction.do";
    document.onboardingForm.submit();
}

function viewcancel()
{
    if (document.onboardingForm.doViewCancel)
        document.onboardingForm.doViewCancel.value = "yes";
    document.forms[0].target = "";
    document.onboardingForm.action = "../onboarding/OnboardingAction.do";
    document.onboardingForm.submit();
}

function getOnboardingkits(Ids)
{
    var httploc = getHTTPObject();
    var url_sort = "../ajax/onboarding/getkits.jsp";
    var getstr = "";
    getstr += "ids=" + Ids;
    httploc.open("POST", url_sort, true);
    httploc.onreadystatechange = function ()
    {
        if (httploc.readyState == 4)
        {
            if (httploc.status == 200)
            {
                var response = httploc.responseText;
                document.getElementById('donboardkitmodal').innerHTML = '';
                document.getElementById('donboardkitmodal').innerHTML = response;
            }
        }
    };
    httploc.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    httploc.setRequestHeader("Content-length", getstr.length);
    httploc.setRequestHeader("Connection", "close");
    httploc.send(getstr);
    document.getElementById('donboardkitmodal').innerHTML = "<div><img src='../assets/images/loading.gif' align='absmiddle'/>Please wait. Loading...</div>";
}

function getselectionmodal(maillogId)
{
    var url = "../ajax/onboarding/getselectionmodal.jsp";
    var httploc = getHTTPObject();
    var getstr = "";
    getstr += "&maillogId=" + maillogId;
    httploc.open("POST", url, true);
    httploc.onreadystatechange = function ()
    {
        if (httploc.readyState == 4)
        {
            if (httploc.status == 200)
            {
                var response = httploc.responseText;
                document.getElementById("donboardmailviewmodal").innerHTML = '';
                document.getElementById("donboardmailviewmodal").innerHTML = response;
            }
        }
    };
    httploc.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    httploc.setRequestHeader("Content-length", getstr.length);
    httploc.setRequestHeader("Connection", "close");
    httploc.send(getstr);
}

function clearvalue()
{
    document.onboardingForm.cval1.value = "";
    document.onboardingForm.cval2.value = "";
    document.onboardingForm.cval3.value = "";
    document.onboardingForm.cval4.value = "";
    document.onboardingForm.cval5.value = "";
    document.onboardingForm.cval6.value = "";
    document.onboardingForm.cval7.value = "";
    document.onboardingForm.cval8.value = "";
    document.onboardingForm.cval9.value = "";
    document.onboardingForm.cval10.value = "";
}