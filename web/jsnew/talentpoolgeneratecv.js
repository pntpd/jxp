function resetgeneratecvForm()
{
    document.forms[0].reset();
    setresumetemplate();
}

function resetContractForm()
{
    document.forms[0].reset();
}

function viewback()
{
    document.talentpoolForm.doView.value = "yes";
    document.talentpoolForm.action = "../talentpool/TalentpoolAction.do";
    document.talentpoolForm.submit();
}

function setresumetemplate()
{
    var url = "../ajax/talentpool/getresumetemplate.jsp";
    var httploc = getHTTPObject();
    var clientId = document.forms[0].clientId.value;
    var getstr = "clientId=" + clientId;
    httploc.open("POST", url, true);
    httploc.onreadystatechange = function ()
    {
        if (httploc.readyState == 4)
        {
            if (httploc.status == 200)
            {
                var response = httploc.responseText;
                document.getElementById("resumetempId").innerHTML = '';
                document.getElementById("resumetempId").innerHTML = response;
            }
        }
    };
    httploc.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    httploc.setRequestHeader("Content-length", getstr.length);
    httploc.setRequestHeader("Connection", "close");
    httploc.send(getstr);
}

function getGenerateCV(candidateId)
{
    document.forms[0].candidateId.value = candidateId;
    if (document.forms[0].doGenerate)
        document.forms[0].doGenerate.value = "yes";
    document.forms[0].target = "";
    document.forms[0].action = "../talentpool/TalentpoolAction.do";
    document.forms[0].submit();
}

function checkTemplate()
{
    if (document.forms[0].clientId.value == '-1')
    {
        Swal.fire({
            title: "Please select client.",
            didClose: () => {
                document.forms[0].clientId.focus();
            }
        })
        return false;
    }
    if (document.forms[0].resumetempId.value == '-1')
    {
        Swal.fire({
            title: "Please select template.",
            didClose: () => {
                document.forms[0].resumetempId.focus();
            }
        })
        return false;
    }
    return true;
}

function generatepdf()
{
    if(checkTemplate())
    {
        if (document.forms[0].resumetempId.value > 0) 
        {
            document.getElementById("generatepdfdiv").innerHTML = "<img src='../assets/images/loading.gif' align='absmiddle' />";
            var url = "../ajax/talentpool/getresumepdffile.jsp";
            var httploc = getHTTPObject();
            var getstr = "";
            getstr += "resumetempId=" + document.forms[0].resumetempId.value;
            getstr += "&candidateId=" + document.forms[0].candidateId.value;
            getstr += "&cval1=" + document.forms[0].cval1.value;
            getstr += "&cval2=" + document.forms[0].cval2.value;
            getstr += "&cval3=" + document.forms[0].cval3.value;
            getstr += "&cval4=" + document.forms[0].cval4.value;
            getstr += "&cval5=" + document.forms[0].cval5.value;
            getstr += "&cval6=" + document.forms[0].cval6.value;
            getstr += "&cval7=" + document.forms[0].cval7.value;
            getstr += "&cval8=" + document.forms[0].cval8.value;
            getstr += "&cval9=" + document.forms[0].cval9.value;
            getstr += "&cval10=" + document.forms[0].cval10.value;
            httploc.open("POST", url, true);
            httploc.onreadystatechange = function ()
            {
                if (httploc.readyState == 4)
                {
                    if (httploc.status == 200)
                    {
                        var response = httploc.responseText;
                        setIframe(response);
                        document.getElementById("dmail").style.display = "";
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
}

function getModel()
{
    var clientName = document.getElementById("clientId").options[document.getElementById("clientId").selectedIndex].text;
    var url = "../ajax/talentpool/getmailmodaldetails.jsp";
    var httploc = getHTTPObject();
    var getstr = "";
    getstr += "candidateId=" + document.forms[0].candidateId.value;
    getstr += "&clientName=" + clientName;
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

function submitmailForm(candidatename, filename, clientname, candidateId)
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
        var url = "../ajax/talentpool/sendcvmail.jsp";
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
        getstr += "&candidateId=" + candidateId;
        httploc.open("POST", url, true);
        httploc.onreadystatechange = function ()
        {
            if (httploc.readyState == 4)
            {
                if (httploc.status == 200)
                {
                    var response = httploc.responseText;
                    $('#mail_modal').modal('hide');
                }
            }
        };
        httploc.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
        httploc.setRequestHeader("Content-length", getstr.length);
        httploc.setRequestHeader("Connection", "close");
        httploc.send(getstr);
    }
}

//For contract ----------------------------
function getGenerateContract(candidateId)
{
    document.forms[0].candidateId.value = candidateId;
    if (document.forms[0].doGenerateContarct)
        document.forms[0].doGenerateContarct.value = "yes";
    document.forms[0].target = "";
    document.forms[0].action = "../talentpool/TalentpoolAction.do";
    document.forms[0].submit();
}

function generateContractpdf()
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
    if (checkgenerateContractpdf())
    {
        document.getElementById("pdfdiv").innerHTML = "<img src='../assets/images/loading.gif' align='absmiddle' />";
        var url = "../ajax/talentpool/getContractpdffile.jsp";
        var httploc = getHTTPObject();
        var getstr = "";
        getstr += "contractId=" + document.forms[0].contractId.value;
        getstr += "&contractdetailId=" + document.forms[0].contractdetailId.value;
        getstr += "&candidateId=" + document.forms[0].candidateId.value;
        getstr += "&clientassetId=" + document.forms[0].clientassetId.value;
        getstr += "&fromdate=" + document.forms[0].dateofissue.value;
        getstr += "&todate=" + document.forms[0].dateofexpiry.value;
        getstr += "&cval1=" + document.forms[0].cval1.value;
        getstr += "&cval2=" + document.forms[0].cval2.value;
        getstr += "&cval3=" + document.forms[0].cval3.value;
        getstr += "&cval4=" + document.forms[0].cval4.value;
        getstr += "&cval5=" + document.forms[0].cval5.value;
        getstr += "&cval6=" + document.forms[0].cval6.value;
        getstr += "&cval7=" + document.forms[0].cval7.value;
        getstr += "&cval8=" + document.forms[0].cval8.value;
        getstr += "&cval9=" + document.forms[0].cval9.value;
        getstr += "&cval10=" + document.forms[0].cval10.value;
        getstr += "&type=" + document.forms[0].type.value;
        getstr += "&filename=" + document.forms[0].cfilehidden.value;
        httploc.open("POST", url, true);
        httploc.onreadystatechange = function ()
        {
            if (httploc.readyState == 4)
            {
                if (httploc.status == 200)
                {
                    
                    var response = trim(httploc.responseText);
                    if(response == "DUP")
                    {
                        Swal.fire("Contract already exists.");
                        document.getElementById("dmail").style.display = "none";
                        document.getElementById("pdfdiv2").innerHTML = '<a href="javascript: gotoContractlist();" class="cl_btn">Close</a>';
                        document.getElementById("pdfdiv").innerHTML = '<a href="javascript: generateContractpdf();" class="gen_btn">Generate</a>';
                    }else
                    {
                        var arr = new Array();
                        arr = response.split('#@#');
                        var v1 = arr[0];
                        var v2 = trim(arr[1]);
                        var v3 = trim(arr[2]);
                        setIframe(v1);
                        document.forms[0].cfilehidden.value = v2;
                        document.getElementById("dmail").style.display = "";
                        document.getElementById("pdfdiv2").innerHTML = '<a href="javascript: gotoContractlist();" class="cl_btn">Close</a>';
                        document.getElementById("pdfdiv").innerHTML = '<a href="javascript: generateContractpdf();" class="gen_btn">Re-Generate</a>';
                    }
                }
            }
        };
        httploc.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
        httploc.setRequestHeader("Content-length", getstr.length);
        httploc.setRequestHeader("Connection", "close");
        httploc.send(getstr);
    }
}

function checkgenerateContractpdf()
{
    if (document.forms[0].contractId.value == "-1")
    {
        Swal.fire({
            title: "Please select contract.",
            didClose: () => {
                document.forms[0].contractId.focus();
            }
        })
        return false;
    }
    if (document.talentpoolForm.dateofissue.value == "")
    {
        Swal.fire({
            title: "Please enter from date.",
            didClose: () => {
                document.talentpoolForm.dateofissue.focus();
            }
        })
        return false;
    }
    if (document.talentpoolForm.dateofexpiry.value == "")
    {
        Swal.fire({
            title: "Please enter to date.",
            didClose: () => {
                document.talentpoolForm.dateofexpiry.focus();
            }
        })
        return false;
    }
    if (document.talentpoolForm.dateofexpiry.value != "")
    {
        if (comparisionTest(document.talentpoolForm.dateofissue.value, document.talentpoolForm.dateofexpiry.value) == false)
        {
            Swal.fire({
                title: "Please check to date.",
                didClose: () => {
                    document.talentpoolForm.dateofexpiry.value = "";
                }
            })
            return false;
        }
    }
    return true;
}

//contract mail 
function getContarctModel(filename)
{
    var url = "../ajax/talentpool/getmailmodalcontract.jsp";
    var httploc = getHTTPObject();
    var getstr = "";
    getstr += "candidateId=" + document.forms[0].candidateId.value;
    getstr += "&filename=" + filename;
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

function submitContractmailForm(candidatename, clientname)
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
    if (checkgenerateContractpdf())
    {
        document.getElementById('dmail').innerHTML = "<img src='../assets/images/loading.gif' align='absmiddle' />";
        var url = "../ajax/talentpool/sendcontractmail.jsp";
        var httploc = getHTTPObject();
        var getstr = "";
        getstr += "candidatename=" + candidatename;
        getstr += "&filename=" + document.forms[0].cfilehidden.value;
        getstr += "&clientname=" + clientname;
        getstr += "&candidateId=" + document.forms[0].candidateId.value;        
        getstr += "&contractId=" + document.forms[0].contractId.value;
        getstr += "&contractdetailId=" + document.forms[0].contractdetailId.value;
        getstr += "&clientassetId=" + document.forms[0].clientassetId.value;
        getstr += "&fromdate=" + document.forms[0].dateofissue.value;
        getstr += "&todate=" + document.forms[0].dateofexpiry.value;
        getstr += "&cval1=" + document.forms[0].cval1.value;
        getstr += "&cval2=" + document.forms[0].cval2.value;
        getstr += "&cval3=" + document.forms[0].cval3.value;
        getstr += "&cval4=" + document.forms[0].cval4.value;
        getstr += "&cval5=" + document.forms[0].cval5.value;
        getstr += "&cval6=" + document.forms[0].cval6.value;
        getstr += "&cval7=" + document.forms[0].cval7.value;
        getstr += "&cval8=" + document.forms[0].cval8.value;
        getstr += "&cval9=" + document.forms[0].cval9.value;
        getstr += "&cval10=" + document.forms[0].cval10.value;
        httploc.open("POST", url, true);
        httploc.onreadystatechange = function ()
        {
            if (httploc.readyState == 4)
            {
                if (httploc.status == 200)
                {
                    var response = httploc.responseText;
                    document.forms[0].action = "../talentpool/TalentpoolAction.do?doViewContractlist=yes";
                    document.forms[0].submit();
                }
            }
        };
        httploc.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
        httploc.setRequestHeader("Content-length", getstr.length);
        httploc.setRequestHeader("Connection", "close");
        httploc.send(getstr);
    }
}


function sendContractMail(candidatename, candidateId)
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
    if (checkContractMail())
    {
        document.getElementById('sendmaildiv').innerHTML = "<img src='../assets/images/loading.gif' align='absmiddle' />";
        var fromval = document.forms[0].fromval.value;
        var toval = document.forms[0].toval.value;
        var ccval = document.forms[0].ccval.value;
        var bccval = document.forms[0].bccval.value;
        var subject = document.forms[0].subject.value;
        var description = document.forms[0].description.value;
        var url = "../ajax/talentpool/sendcontractmail.jsp";
        var httploc = getHTTPObject();
        var getstr = "";
        getstr += "fromval=" + fromval;
        getstr += "&toval=" + toval;
        getstr += "&ccval=" + ccval;
        getstr += "&bccval=" + bccval;
        getstr += "&subject=" + subject;
        getstr += "&description=" + description;
        getstr += "&candidatename=" + candidatename;
        httploc.open("POST", url, true);
        httploc.onreadystatechange = function ()
        {
            if (httploc.readyState == 4)
            {
                if (httploc.status == 200)
                {
                    var response = httploc.responseText;
                    $('#mail_modal').modal('hide');
                    document.forms[0].action = "../talentpool/TalentpoolAction.do?doViewContractlist=yes";
                    document.forms[0].submit();
                }
            }
        };
        httploc.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
        httploc.setRequestHeader("Content-length", getstr.length);
        httploc.setRequestHeader("Connection", "close");
        httploc.send(getstr);
    }
}

function checkContractMail()
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