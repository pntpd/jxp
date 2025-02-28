function DrawCaptcha()  
{
    setcaptchasession("2");
}

function setcaptchasession(tp)
{
    var url = "/jxp/ajax/captchasession.jsp";
    var httploc = getHTTPObject();
    var getstr = "type=" + tp;
    httploc.open("POST", url, true);
    httploc.onreadystatechange = function ()
    {
        if (httploc.readyState == 4)
        {
            if (httploc.status == 200)
            {
                var response = httploc.responseText;
                response = trim(response);
                if (tp == "2")
                {
                    document.getElementById("txtCaptcha").value = response;
                    document.homeForm.cap.value = "";
                }
            }
        }
    }
    httploc.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    httploc.send(getstr);
}

function removeSpaces(string)
{
    return string.split(' ').join('');
}

function checkEmail()
{
    if (document.homeForm.emailId.value === "")
    {
        Swal.fire({
        title: "Please enter Email ID.",
        didClose:() => {
          document.homeForm.emailId.focus();
        }
        }) 
        return false;
    }
    if(document.homeForm.cap.value == "")
    {
        Swal.fire({
        title: "Please enter captcha.",
        didClose:() => {
          document.homeForm.cap.focus();
        }
        }) 
        return false;
    }
    if(removeSpaces(document.homeForm.txtCaptcha.value) != removeSpaces(document.homeForm.cap.value))
    {
        Swal.fire({
        title: "Captcha does not match.",
        didClose:() => {
        document.homeForm.cap.value = "";
        document.homeForm.cap.focus();
        }
        }) 
        return false;
    }
    return true;
}

function generateotpForm()
{
    if(checkEmail())
    {
        var url_forgot = "/jxp/ajax/generateotp.jsp";
        document.getElementById("submitdiv").innerHTML="Processing...";
        var https = getHTTPObject();
        var getstr = "";
        var v1 = document.homeForm.emailId.value;
        getstr += "emailId=" + escape(v1);
        getstr += "&cap=" + document.homeForm.cap.value; 
        https.open("POST", url_forgot, true);
        https.onreadystatechange = function()
        {
            if (https.readyState === 4)
            {
                if(https.status === 200)
                {
                    var response = https.responseText;
                    var val = response;
                    if(trim(val) === 'S1')
                    {
                    location.assign("/jxp/home/verify_home.jsp");
                    }
                    else if(trim(val) == "NOMAIL")
                    {
                        Swal.fire({
                        title: "Something went wrong.",
                        didClose:() => {
                        document.homeForm.cap.value = "";
                        document.homeForm.cap.focus();
                        }
                        }) 
                        return false;
                    }
                    else if(trim(val) == "INV")
                    {
                        Swal.fire({
                        title: "Please check captcha.",
                        didClose:() => {
                        document.homeForm.cap.value = "";
                        document.homeForm.cap.focus();
                        }
                        }) 
                        return false;
                    }
                    else
                    {
                        DrawCaptcha();
                    Swal.fire({
                        title: "OTP cannot be sent. Please check your email Id or contact your Administrator.",
                        didClose:() => {
                        }
                        }) 
                        return false;
                    }
                    document.getElementById("submitdiv").innerHTML="<a href='javascript: generateotpForm();' class='forgot_pd'> Forgot Password?</a>";
                }
            }
        };
        https.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
        https.setRequestHeader("Content-length", getstr.length);
        https.setRequestHeader("Connection", "close");
        https.send(getstr);
        https.send(getstr);
    }
}

function handleKeySearch(e, tp)
{
    if(tp == 1)
            document.forms[0].otp2.focus();
    else if(tp == 2)
            document.forms[0].otp3.focus();
    else if(tp == 3)
            document.forms[0].otp4.focus();
    else if(tp == 4)
            document.forms[0].otp5.focus();
    else if(tp == 5)
            document.forms[0].otp6.focus();
}  

function checkOTP()
{
    if (document.forms[0].otp1.value == "")
    {
        Swal.fire({
        title: "Please enter otp.",
        didClose:() => {
        document.forms[0].otp1.focus();
        }
        })
        return false;
    }
    if (document.forms[0].otp2.value == "")
    {
        Swal.fire({
        title: "Please enter otp.",
        didClose:() => {
        document.forms[0].otp2.focus();
        }
        })
        return false;
    }
    if (document.forms[0].otp3.value == "")
    {
        Swal.fire({
        title: "Please enter otp.",
        didClose:() => {
        document.forms[0].otp3.focus();
        }
        })
        return false;
    }
    if (document.forms[0].otp4.value == "")
    {
        Swal.fire({
        title: "Please enter otp.",
        didClose:() => {
        document.forms[0].otp4.focus();
        }
        })
        return false;
    }
    if (document.forms[0].otp5.value == "")
    {
         Swal.fire({
        title: "Please enter otp.",
        didClose:() => {
        document.forms[0].otp5.focus();
        }
        })
        return false;
    }
    if (document.forms[0].otp6.value == "")
    {
         Swal.fire({
        title: "Please enter otp.",
        didClose:() => {
        document.forms[0].otp6.focus();
        }
        })
        return false;
    }
    return true;
}

function submitotpForm()
{
    if(checkOTP())
    {
        var url = "/jxp/ajax/checkotp.jsp";
        var httploc = getHTTPObject();
        var otp = document.forms[0].otp1.value +""+ document.forms[0].otp2.value +""+ document.forms[0].otp3.value +""+ document.forms[0].otp4.value+""+ document.forms[0].otp5.value+""+ document.forms[0].otp6.value;
        var getstr = "";
        getstr += "otp="+escape(otp);
        httploc.open("POST", url, true);
        httploc.onreadystatechange = function()
        {
            if (httploc.readyState == 4)
            {
                if(httploc.status == 200)
                {
                    var response = trim(httploc.responseText);
                    if(response == "INVALIDOTP")
                    {
                        Swal.fire({
                        title: "Please check OTP.",
                        didClose:() => {
                            document.forms[0].otp1.value = "";
                            document.forms[0].otp2.value = "";
                            document.forms[0].otp3.value = "";
                            document.forms[0].otp4.value = "";
                            document.forms[0].otp5.value = "";
                            document.forms[0].otp6.value = "";
                        }
                        }) 
                        return false;
                    }
                    else if(response == "CTA")
                    {
                        Swal.fire({
                        title: "Contact to Administrator.",
                        didClose:() => {
                        }
                        }) 
                        return false;
                    }
                    else if(response == "EXISTING")
                    {
                        document.forms[0].doView.value = "yes";
                        document.forms[0].action="../home/HomeAction.do";
                        document.forms[0].submit();
                    }
                    else if(response == "NEW")
                    {
                        document.forms[0].doAdd.value = "yes";
                        document.forms[0].action="../home/HomeAction.do";
                        document.forms[0].submit();
                    }
                    else
                    {
                        Swal.fire("Something went wrong.");
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

function handleKeyLogin(e)
{
    var key=e.keyCode || e.which;
    if (key===13)
    {
        e.preventDefault();
        submitotpForm();
    }
} 

//candidate.js   start

function showDetail(id)
{
    document.homeForm.doView.value = "yes";
    document.homeForm.candidateId.value = id;
    document.homeForm.action = "../home/HomeAction.do";
    document.homeForm.submit();
}

function view(id)
{
    document.homeForm.doView.value = "yes";
    document.homeForm.candidateId.value = id;
    document.homeForm.action = "../home/HomeAction.do";
    document.homeForm.submit();
}

function view1()
{
    document.homeForm.doView.value = "yes";
    document.homeForm.doCancel.value = "no";
    if (document.homeForm.doViewlangdetail)
        document.homeForm.doViewlangdetail.value = "no";
    if (document.homeForm.doViewhealthdetail)
        document.homeForm.doViewhealthdetail.value = "no";
    if (document.homeForm.doViewvaccinationlist)
        document.homeForm.doViewvaccinationlist.value = "no";
    if (document.homeForm.doViewtrainingcertlist)
        document.homeForm.doViewtrainingcertlist.value = "no";
    if (document.homeForm.doVieweducationlist)
        document.homeForm.doVieweducationlist.value = "no";
    if (document.homeForm.doDeletelangdetail)
        document.homeForm.doDeletelangdetail.value = "no";
    if (document.homeForm.doViewexperiencelist)
        document.homeForm.doViewexperiencelist.value = "no";
    document.homeForm.action = "../home/HomeAction.do";
    document.homeForm.submit();
}

function setCandidateClass(tp)
{
    if (!(document.homeForm.photofile.value).match(/(\.(png)|(jpg)|(jpeg))$/i))
    {
        Swal.fire({
            title: "Only .jpg, .jpeg, .png are allowed.",
            didClose: () => {
                document.homeForm.photofile.focus();
            }
        })
    }
    if (document.homeForm.photofile.value != "")
    {
        var input = document.homeForm.photofile;
        if (input.files)
        {
            var file = input.files[0];
            if (file.size > (1024 * 1024 * 5))
            {
                Swal.fire({
                    title: "File size should not exceed 5 MB.",
                    didClose: () => {
                        document.homeForm.photofile.focus();
                    }
                })
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
                        document.getElementById("photo_profile_id").src = srcData;
                    }
                    fileReader.readAsDataURL(fileToLoad);
                }
            }
        }
    }
}

function addForm()
{
    document.homeForm.doModify.value = "no";
    document.homeForm.doView.value = "no";
    document.homeForm.doCancel.value = "no";
    document.homeForm.doAdd.value = "yes";
    document.homeForm.action = "../home/HomeAction.do";
    document.homeForm.submit();
}

function modifyForm(id)
{
    document.homeForm.doModify.value = "yes";
    document.homeForm.doView.value = "no";
    document.homeForm.doAdd.value = "no";
    document.homeForm.doCancel.value = "no";
    document.homeForm.candidateId.value = id;
    document.homeForm.action = "../home/HomeAction.do";
    document.homeForm.submit();
}

function modifyFormview()
{
    document.homeForm.doModify.value = "yes";
    document.homeForm.doCancel.value = "no";
    document.homeForm.action = "../home/HomeAction.do";
    document.homeForm.submit();
}

function deleteForm(candidateId, status, id)
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
            var url = "../ajax/candidate/getinfo.jsp";
            var getstr = "";
            var httploc = getHTTPObject();
            var next_value = escape(document.homeForm.nextValue.value);
            var next_del = "-1";
            if (document.homeForm.nextDel)
                next_del = escape(document.homeForm.nextDel.value);
            var search_value = escape(document.homeForm.search.value);
            getstr += "nextValue=" + next_value;
            getstr += "&nextDel=" + next_del;
            getstr += "&search=" + search_value;
            getstr += "&status=" + status;
            getstr += "&deleteVal=" + candidateId;
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
                    }
                }
            };
            httploc.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
            httploc.setRequestHeader("Content-length", getstr.length);
            httploc.setRequestHeader("Connection", "close");
            httploc.send(getstr);
            document.getElementById('ajax_cat').innerHTML = "<div><img src='../assets/images/loading.gif' align='absmiddle'/>Please wait. Loading...</div>";
        } else
        {
            if (document.getElementById("flexSwitchCheckDefault_" + id).checked == true)
                document.getElementById("flexSwitchCheckDefault_" + id).checked = false;
            else
                document.getElementById("flexSwitchCheckDefault_" + id).checked = true;
        }
    })
}

function goback()
{
    if (document.homeForm.doView)
        document.homeForm.doView.value = "no";
    if (document.homeForm.doCancel)
        document.homeForm.doCancel.value = "yes";
    if (document.homeForm.doSave)
        document.homeForm.doSave.value = "no";
    if (document.homeForm.doModify)
        document.homeForm.doModify.value = "no";
    document.homeForm.action = "../home/HomeAction.do";
    document.homeForm.submit();
}

function resetForm()
{
    document.homeForm.reset();
}

function showhidetr(tp)
{

    if (document.getElementById("tr_" + tp).style.display == "none")
        document.getElementById("tr_" + tp).style.display = "";
    else
        document.getElementById("tr_" + tp).style.display = "none";
}

function setClass(tp)
{
    document.getElementById("upload_link_" + tp).className = "attache_btn uploaded_img";
}

function setDocumentissuedbyDDL()
{
    var url = "../ajax/candidate/getdocumentissuedby.jsp";
    var httploc = getHTTPObject();
    var getstr = "documentTypeId=" + document.homeForm.documentTypeId.value;
    httploc.open("POST", url, true);
    httploc.onreadystatechange = function ()
    {
        if (httploc.readyState == 4)
        {
            if (httploc.status == 200)
            {
                var response = httploc.responseText;
                document.getElementById("documentissuedbyId").innerHTML = '';
                document.getElementById("documentissuedbyId").innerHTML = response;
            }
        }
    };
    httploc.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    httploc.setRequestHeader("Content-length", getstr.length);
    httploc.setRequestHeader("Connection", "close");
    httploc.send(getstr);
}

function setcitybyDDL()
{
    var url = "../ajax/candidate/getcity.jsp";
    var httploc = getHTTPObject();
    var getstr = "countrytId=" + document.homeForm.countryId.value;
    httploc.open("POST", url, true);
    httploc.onreadystatechange = function ()
    {
        if (httploc.readyState == 4)
        {
            if (httploc.status == 200)
            {
                var response = httploc.responseText;
                document.getElementById("cityId").innerHTML = '';
                document.getElementById("cityId").innerHTML = response;
            }
        }
    };
    httploc.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    httploc.setRequestHeader("Content-length", getstr.length);
    httploc.setRequestHeader("Connection", "close");
    httploc.send(getstr);
}

function openTab(tp)
{
    if (tp == 2)
    {
        document.homeForm.doModify.value = "no";
        document.homeForm.doCancel.value = "no";
        if (document.homeForm.doViewlangdetail)
            document.homeForm.doViewlangdetail.value = "yes";
        if (document.homeForm.doViewhealthdetail)
            document.homeForm.doViewhealthdetail.value = "no";
        if (document.homeForm.doViewBanklist)
            document.homeForm.doViewBanklist.value = "no";
        if (document.homeForm.doViewvaccinationlist)
            document.homeForm.doViewvaccinationlist.value = "no";
        if (document.homeForm.doViewgovdocumentlist)
            document.homeForm.doViewgovdocumentlist.value = "no";
        if (document.homeForm.doViewhitchlist)
            document.homeForm.doViewhitchlist.value = "no";
        if (document.homeForm.doViewtrainingcertlist)
            document.homeForm.doViewtrainingcertlist.value = "no";
        if (document.homeForm.doVieweducationlist)
            document.homeForm.doVieweducationlist.value = "no";
        if (document.homeForm.doDeletelangdetail)
            document.homeForm.doDeletelangdetail.value = "no";
        if (document.homeForm.doViewexperiencelist)
            document.homeForm.doViewexperiencelist.value = "no";
        document.homeForm.action = "../home/HomeAction.do";
        document.homeForm.submit();
    }
    if (tp == 4)
    {
        document.homeForm.doModify.value = "no";
        document.homeForm.doCancel.value = "no";
        if (document.homeForm.doaddlangdetail)
            document.homeForm.doaddlangdetail.value = "no";
        if (document.homeForm.doSavehealthdetail)
            document.homeForm.doSavehealthdetail.value = "no";
        if (document.homeForm.doViewhealthdetail)
            document.homeForm.doViewhealthdetail.value = "yes";
        if (document.homeForm.doViewBanklist)
            document.homeForm.doViewBanklist.value = "no";
        if (document.homeForm.doViewvaccinationlist)
            document.homeForm.doViewvaccinationlist.value = "no";
        if (document.homeForm.doViewgovdocumentlist)
            document.homeForm.doViewgovdocumentlist.value = "no";
        if (document.homeForm.doViewhitchlist)
            document.homeForm.doViewhitchlist.value = "no";
        if (document.homeForm.doViewtrainingcertlist)
            document.homeForm.doViewtrainingcertlist.value = "no";
        if (document.homeForm.doVieweducationlist)
            document.homeForm.doVieweducationlist.value = "no";
        if (document.homeForm.doViewexperiencelist)
            document.homeForm.doViewexperiencelist.value = "no";
        document.homeForm.action = "../home/HomeAction.do";
        document.homeForm.submit();
    }
    if (tp == 5)
    {
        document.homeForm.doModify.value = "no";
        document.homeForm.doCancel.value = "no";
        if (document.homeForm.doaddlangdetail)
            document.homeForm.doaddlangdetail.value = "no";
        if (document.homeForm.doViewhealthdetail)
            document.homeForm.doViewhealthdetail.value = "no";
        if (document.homeForm.doViewBanklist)
            document.homeForm.doViewBanklist.value = "no";
        if (document.homeForm.doViewvaccinationlist)
            document.homeForm.doViewvaccinationlist.value = "yes";
        if (document.homeForm.doViewgovdocumentlist)
            document.homeForm.doViewgovdocumentlist.value = "no";
        if (document.homeForm.doViewhitchlist)
            document.homeForm.doViewhitchlist.value = "no";
        if (document.homeForm.doViewtrainingcertlist)
            document.homeForm.doViewtrainingcertlist.value = "no";
        if (document.homeForm.doVieweducationlist)
            document.homeForm.doVieweducationlist.value = "no";
        if (document.homeForm.doViewexperiencelist)
            document.homeForm.doViewexperiencelist.value = "no";
        document.homeForm.action = "../home/HomeAction.do";
        document.homeForm.submit();
    }
    if (tp == 6)
    {
        document.homeForm.doModify.value = "no";
        document.homeForm.doCancel.value = "no";
        if (document.homeForm.doaddlangdetail)
            document.homeForm.doaddlangdetail.value = "no";
        if (document.homeForm.doViewhealthdetail)
            document.homeForm.doViewhealthdetail.value = "no";
        if (document.homeForm.doViewBanklist)
            document.homeForm.doViewBanklist.value = "no";
        if (document.homeForm.doViewvaccinationlist)
            document.homeForm.doViewvaccinationlist.value = "no";
        if (document.homeForm.doViewgovdocumentlist)
            document.homeForm.doViewgovdocumentlist.value = "yes";
        if (document.homeForm.doViewtrainingcertlist)
            document.homeForm.doViewtrainingcertlist.value = "no";
        if (document.homeForm.doVieweducationlist)
            document.homeForm.doVieweducationlist.value = "no";
        if (document.homeForm.doViewexperiencelist)
            document.homeForm.doViewexperiencelist.value = "no";
        document.homeForm.action = "../home/HomeAction.do";
        document.homeForm.submit();
    }
    if (tp == 8)
    {
        document.homeForm.doModify.value = "no";
        document.homeForm.doCancel.value = "no";
        if (document.homeForm.doaddlangdetail)
            document.homeForm.doaddlangdetail.value = "no";
        if (document.homeForm.doViewhealthdetail)
            document.homeForm.doViewhealthdetail.value = "no";
        if (document.homeForm.doViewBanklist)
            document.homeForm.doViewBanklist.value = "no";
        if (document.homeForm.doViewvaccinationlist)
            document.homeForm.doViewvaccinationlist.value = "no";
        if (document.homeForm.doViewgovdocumentlist)
            document.homeForm.doViewgovdocumentlist.value = "no";
        if (document.homeForm.doViewhitchlist)
            document.homeForm.doViewhitchlist.value = "no";
        if (document.homeForm.doViewtrainingcertlist)
            document.homeForm.doViewtrainingcertlist.value = "yes";
        if (document.homeForm.doVieweducationlist)
            document.homeForm.doVieweducationlist.value = "no";
        if (document.homeForm.doViewexperiencelist)
            document.homeForm.doViewexperiencelist.value = "no";
        document.homeForm.action = "../home/HomeAction.do";
        document.homeForm.submit();
    }
    if (tp == 9)
    {
        document.homeForm.doModify.value = "no";
        document.homeForm.doCancel.value = "no";
        if (document.homeForm.doaddlangdetail)
            document.homeForm.doaddlangdetail.value = "no";
        if (document.homeForm.doViewhealthdetail)
            document.homeForm.doViewhealthdetail.value = "no";
        if (document.homeForm.doViewBanklist)
            document.homeForm.doViewBanklist.value = "no";
        if (document.homeForm.doViewvaccinationlist)
            document.homeForm.doViewvaccinationlist.value = "no";
        if (document.homeForm.doViewgovdocumentlist)
            document.homeForm.doViewgovdocumentlist.value = "no";
        if (document.homeForm.doViewhitchlist)
            document.homeForm.doViewhitchlist.value = "no";
        if (document.homeForm.doViewtrainingcertlist)
            document.homeForm.doViewtrainingcertlist.value = "no";
        if (document.homeForm.doVieweducationlist)
            document.homeForm.doVieweducationlist.value = "yes";
        if (document.homeForm.doViewexperiencelist)
            document.homeForm.doViewexperiencelist.value = "no";
        document.homeForm.action = "../home/HomeAction.do";
        document.homeForm.submit();
    }
    if (tp == 10)
    {
        document.homeForm.doModify.value = "no";
        document.homeForm.doCancel.value = "no";
        if (document.homeForm.doaddlangdetail)
            document.homeForm.doaddlangdetail.value = "no";
        if (document.homeForm.doViewhealthdetail)
            document.homeForm.doViewhealthdetail.value = "no";
        if (document.homeForm.doViewBanklist)
            document.homeForm.doViewBanklist.value = "no";
        if (document.homeForm.doViewvaccinationlist)
            document.homeForm.doViewvaccinationlist.value = "no";
        if (document.homeForm.doViewgovdocumentlist)
            document.homeForm.doViewgovdocumentlist.value = "no";
        if (document.homeForm.doViewhitchlist)
            document.homeForm.doViewhitchlist.value = "no";
        if (document.homeForm.doViewtrainingcertlist)
            document.homeForm.doViewtrainingcertlist.value = "no";
        if (document.homeForm.doVieweducationlist)
            document.homeForm.doVieweducationlist.value = "no";
        if (document.homeForm.doViewexperiencelist)
            document.homeForm.doViewexperiencelist.value = "yes";
        document.homeForm.action = "../home/HomeAction.do";
        document.homeForm.submit();
    }
}

function modifyForm1()
{
    document.homeForm.doModify.value = "yes";
    document.homeForm.doCancel.value = "no";
    if (document.homeForm.doaddlangdetail)
        document.homeForm.doaddlangdetail.value = "no";
    if (document.homeForm.doViewhealthdetail)
        document.homeForm.doViewhealthdetail.value = "no";
    if (document.homeForm.doViewvaccinationlist)
        document.homeForm.doViewvaccinationlist.value = "no";
    if (document.homeForm.doViewtrainingcertlist)
        document.homeForm.doViewtrainingcertlist.value = "no";
    if (document.homeForm.doVieweducationlist)
        document.homeForm.doVieweducationlist.value = "no";
    if (document.homeForm.doViewexperiencelist)
        document.homeForm.doViewexperiencelist.value = "no";
    document.homeForm.action = "../home/HomeAction.do";
    document.homeForm.submit();
}

function modifyvaccinationdetailForm(id)
{
    document.homeForm.doDeletevaccinationdetail.value = "no";
    document.homeForm.candidatevaccineId.value = id;
    document.homeForm.doaddvaccinationdetail.value = "yes";
    document.homeForm.action = "../home/HomeAction.do";
    document.homeForm.submit();
}

function deletevaccinationForm(id, status)
{
    document.homeForm.doDeletevaccinationdetail.value = "yes";
    document.homeForm.candidatevaccineId.value = id;
    document.homeForm.status.value = status;
    document.homeForm.doaddvaccinationdetail.value = "no";
    document.homeForm.action = "../home/HomeAction.do";
    document.homeForm.submit();
}

function modifytrainingcertdetailForm(id)
{
    document.homeForm.trainingandcertId.value = id;
    document.homeForm.doaddtrainingcertdetail.value = "yes";
    document.homeForm.action = "../home/HomeAction.do";
    document.homeForm.submit();
}

function deletetrainingcertForm(id, status)
{
    document.homeForm.doDeletetrainingcertdetail.value = "yes";
    document.homeForm.trainingandcertId.value = id;
    document.homeForm.status.value = status;
    document.homeForm.doaddtrainingcertdetail.value = "no";
    document.homeForm.action = "../home/HomeAction.do";
    document.homeForm.submit();
}

function modifyeducationForm(id)
{
    document.homeForm.educationdetailId.value = id;
    document.homeForm.doaddeducationdetail.value = "yes";
    document.homeForm.action = "../home/HomeAction.do";
    document.homeForm.submit();
}

function deleteeducationForm(id, status)
{
    document.homeForm.doDeleteeducationdetail.value = "yes";
    document.homeForm.educationdetailId.value = id;
    document.homeForm.status.value = status;
    document.homeForm.doaddeducationdetail.value = "no";
    document.homeForm.action = "../home/HomeAction.do";
    document.homeForm.submit();
}

function modifyexperienceForm(id)
{
    document.homeForm.experiencedetailId.value = id;
    document.homeForm.doaddexperiencedetail.value = "yes";
    document.homeForm.action = "../home/HomeAction.do";
    document.homeForm.submit();
}

function deleteexperienceForm(id, status)
{
    document.homeForm.doDeleteexperiencedetail.value = "yes";
    document.homeForm.experiencedetailId.value = id;
    document.homeForm.status.value = status;
    document.homeForm.doaddexperiencedetail.value = "no";
    document.homeForm.action = "../home/HomeAction.do";
    document.homeForm.submit();
}

function modifylanguageForm(id)
{
    document.homeForm.doSavelangdetail.value = "no";
    document.homeForm.candidateLangId.value = id;
    document.homeForm.doaddlangdetail.value = "yes";
    document.homeForm.action = "../home/HomeAction.do";
    document.homeForm.submit();
}

function deletelangaugeForm(id, status)
{
    document.homeForm.doDeletelangdetail.value = "yes";
    document.homeForm.candidateLangId.value = id;
    document.homeForm.status.value = status;
    document.homeForm.doaddlangdetail.value = "no";
    document.homeForm.action = "../home/HomeAction.do";
    document.homeForm.submit();
}

function modifyhealthForm()
{
    document.homeForm.doaddhealthdetail.value = "yes";
    document.homeForm.action = "../home/HomeAction.do";
    document.homeForm.submit();
}

function showhideexperience()
{
    if (document.homeForm.currentworkingstatus.checked)
    {
        document.getElementById('all_workenddate').style.display = "none";
    } else
    {
        document.getElementById('all_workenddate').style.display = "";
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

function setIframeresume(uval)
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
    }, 1000);
}

//validation and submit
//-------------------------------------------------------------------------------------- Health ------------------------------------------

function  submithealthform()
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
    if (checkHealthDetail())
    {
        document.getElementById('submitdiv').innerHTML = "<img src='../assets/images/loading.gif' align='absmiddle' />";
        document.homeForm.doaddhealthdetail.value = "no";
        document.homeForm.doSavehealthdetail.value = "yes";
        document.homeForm.action = "../home/HomeAction.do";
        document.homeForm.submit();
    }
}

function checkHealthDetail()
{
    if (trim(document.homeForm.ssmf.value) == "")
    {
        Swal.fire({
            title: "Please select seaman specific medical fitness.",
            didClose: () => {
                document.homeForm.ssmf.focus();
            }
        })
        return false;
    }
    if (trim(document.homeForm.ssmf.value) == "No")
    {
        if (trim(document.homeForm.ogukmedicalftw.value) == "")
        {
            Swal.fire({
                title: "Please enter OGUK medical FTW.",
                didClose: () => {
                    document.homeForm.ogukmedicalftw.focus();
                }
            })
            return false;
        }
        if (validdesc(document.homeForm.ogukmedicalftw) == false)
        {
            return false;
        }

        if (document.homeForm.ogukexp.value == "")
        {
            Swal.fire({
                title: "Please select OGUK expiry.",
                didClose: () => {
                    document.homeForm.ogukexp.focus();
                }
            })
            return false;
        }
    }
    if (trim(document.homeForm.ssmf.value) == "Yes")
    {
        if (trim(document.homeForm.medifitcert.value) == "")
        {
            Swal.fire({
                title: "Please select medical fitness certificate.",
                didClose: () => {
                    document.homeForm.medifitcert.focus();
                }
            })
            return false;
        }
        if (document.homeForm.medifitcertexp.value == "")
        {
            Swal.fire({
                title: "Please enter medical fitness certificate expiry.",
                didClose: () => {
                    document.homeForm.medifitcertexp.focus();
                }
            })
            return false;
        }
    }
    if (trim(document.homeForm.cov192doses.value) == "")
    {
        Swal.fire({
            title: "Please select covid-19 2 doses.",
            didClose: () => {
                document.homeForm.cov192doses.focus();
            }
        })
        return false;
    }
    return true;
}

//------------------------------------------------------------------------------Language----------------------------------------------
function checkLanguageDetail()
{
    if (document.homeForm.languageId.value == "-1")
    {
        Swal.fire({
            title: "Please select language.",
            didClose: () => {
                document.homeForm.languageId.focus();
            }
        })
        return false;
    }
    if (document.homeForm.proficiencyId.value == "-1")
    {
        Swal.fire({
            title: "Please select language proficiency.",
            didClose: () => {
                document.homeForm.proficiencyId.focus();
            }
        })
        return false;
    }
    if (document.homeForm.langfile.value != "")
    {
        if (document.homeForm.candidateLangId.value < 0 || document.homeForm.langfilehidden.value == "")
        {
            if (document.homeForm.langfile.value == "")
            {
                Swal.fire({
                    title: "please upload certificate.",
                    didClose: () => {
                        document.homeForm.langfile.focus();
                    }
                })
                return false;
            }
        }
    }
    if (document.homeForm.langfile.value != "")
    {
        if (!(document.homeForm.langfile.value).match(/(\.(png)|(jpg)|(jpeg)|(pdf))$/i))
        {
            Swal.fire({
                title: "Only .jpg, .jpeg, .png, .pdf are allowed.",
                didClose: () => {
                    document.homeForm.langfile.focus();
                }
            })
            return false;
        }
        var input = document.homeForm.langfile;
        if (input.files)
        {
            var file = input.files[0];
            if (file.size > 1024 * 1024 * 5)
            {
                Swal.fire({
                    title: "File size should not exceed 5 MB.",
                    didClose: () => {
                        document.homeForm.langfile.focus();
                    }
                })
                return false;
            }
        }
    }
    return true;
}

function submitlangaugeform()
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
    if (checkLanguageDetail())
    {
        document.getElementById('submitdiv').innerHTML = "<img src='../assets/images/loading.gif' align='absmiddle' />";
        document.homeForm.doaddlangdetail.value = "no";
        document.homeForm.doSavelangdetail.value = "yes";
        document.homeForm.action = "../home/HomeAction.do";
        document.homeForm.submit();
    }
}

//--------------------------------------------------------------------- Vaccinenation ---------------------------------------
function  submitvaccinatonform()
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
    if (checkVaccineDetail())
    {
        document.getElementById('submitdiv').innerHTML = "<img src='../assets/images/loading.gif' align='absmiddle' />";
        document.homeForm.doaddvaccinationdetail.value = "no";
        document.homeForm.doSavevaccinationdetail.value = "yes";
        document.homeForm.action = "../home/HomeAction.do";
        document.homeForm.submit();
    }
}

function checkVaccineDetail()
{
    if (document.homeForm.vaccinationNameId.value == "-1")
    {
        Swal.fire({
            title: "Please select vaccination name.",
            didClose: () => {
                document.homeForm.vaccinationNameId.focus();
            }
        })
        return false;
    }
    if (document.homeForm.vacinationTypeId.value == "-1")
    {
        Swal.fire({
            title: "Please select vaccination type.",
            didClose: () => {
                document.homeForm.vacinationTypeId.focus();
            }
        })
        return false;
    }
    if (trim(document.homeForm.cityName.value) == "")
    {
        Swal.fire({
            title: "Please enter place of application using autofill.",
            didClose: () => {
                document.homeForm.cityName.focus();
            }
        })
        return false;
    }
    if (validdesc(document.homeForm.cityName) == false)
    {
        return false;
    }
    if (eval(document.homeForm.placeofapplicationId.value) <= 0)
    {
        Swal.fire({
            title: "Please enter place of application using autofill.",
            didClose: () => {
                document.homeForm.cityName.focus();
            }
        })
        return false;
    }
    if (document.homeForm.dateofapplication.value == "")
    {
        Swal.fire({
            title: "Please enter date of application.",
            didClose: () => {
                document.homeForm.dateofapplication.focus();
            }
        })
        return false;
    }
    if (comparisionTest(document.homeForm.dateofapplication.value, document.homeForm.currentDate.value) == false)
    {
        Swal.fire({
            title: "Please check date of application.",
            didClose: () => {
                document.homeForm.dateofapplication.focus();
            }
        })
        return false;
    }

    if (document.homeForm.dateofexpiry.value != "") {
        if (comparisionTest(document.homeForm.dateofapplication.value, document.homeForm.dateofexpiry.value) == false)
        {
            Swal.fire({
                title: "Please check date of expiry.",
                didClose: () => {
                    document.homeForm.dateofexpiry.focus();
                }
            })
            return false;
        }
    }

    if (document.homeForm.candidatevaccineId.value < 0)
    {
        if (document.homeForm.vaccinedetailfile.value == "")
        {
            Swal.fire({
                title: "Please attach certificate.",
                didClose: () => {
                    document.homeForm.vaccinedetailfile.focus();
                }
            })
            return false;
        }
    }
    if (document.homeForm.vaccinedetailfile.value != "")
    {
        if (!(document.homeForm.vaccinedetailfile.value).match(/(\.(png)|(jpeg)|(jpg)|(pdf))$/i))
        {
            Swal.fire({
                title: "Only .jpeg, .jpg, .png, .pdf are allowed.",
                didClose: () => {
                    document.homeForm.vaccinedetailfile.focus();
                }
            })
            return false;
        }
        var input = document.homeForm.vaccinedetailfile;
        if (input.files)
        {
            var file = input.files[0];
            if (file.size > 1024 * 1024 * 5)
            {
                Swal.fire({
                    title: "File size should not exceed 5 MB.",
                    didClose: () => {
                        document.homeForm.vaccinedetailfile.focus();
                    }
                })
                return false;
            }
        }
    }
    return true;
}

//--------------------------------------------------------- Certification --------------------------

function checkCertificationDetails()
{
    if (Number(document.homeForm.coursenameId.value) <= 0)
    {
        Swal.fire({
            title: "Please select course name.",
            didClose: () => {
                document.homeForm.coursenameId.focus();
            }
        })
        return false;
    }

    if (trim(document.homeForm.educationInstitute.value) == "")
    {
        Swal.fire({
            title: "Please enter educational institute.",
            didClose: () => {
                document.homeForm.educationInstitute.focus();
            }
        })
        return false;
    }
    if (validdesc(document.homeForm.educationInstitute) == false)
    {
        return false;
    }
    if (trim(document.homeForm.cityName.value) == "")
    {
        Swal.fire({
            title: "Please enter location of institute.",
            didClose: () => {
                document.homeForm.cityName.focus();
            }
        })
        return false;
    }
    if (validdesc(document.homeForm.cityName) == false)
    {
        return false;
    }
    if (eval(document.homeForm.locationofInstituteId.value) <= 0)
    {
        Swal.fire({
            title: "Please select location of institute using autofill.",
            didClose: () => {
                document.homeForm.cityName.focus();
            }
        })
        return false;
    }

    if (document.homeForm.dateofissue.value == "")
    {
        Swal.fire({
            title: "Please select date of issue.",
            didClose: () => {
                document.homeForm.dateofissue.focus();
            }
        })
        return false;
    }

    if (trim(document.homeForm.certificationno.value) == "")
    {
        Swal.fire({
            title: "Please enter certification no.",
            didClose: () => {
                document.homeForm.certificationno.focus();
            }
        })
        return false;
    }
    if (validdesc(document.homeForm.certificationno) == false)
    {
        return false;
    }
    if (document.homeForm.dateofexpiry.value != "") {
        if (comparisionTest(document.homeForm.dateofissue.value, document.homeForm.dateofexpiry.value) == false)
        {
            Swal.fire({
                title: "Please check date of expiry.",
                didClose: () => {
                    document.homeForm.dateofexpiry.value = "";
                }
            })
            return false;
        }
    }
    if (document.homeForm.upload1.value != "")
    {
        if (!(document.homeForm.upload1.value).match(/(\.(png)|(pdf)|(jpg)|(jpeg))$/i))
        {
            Swal.fire({
                title: "Only  .jpg, .jpeg, .png .pdf are allowed.",
                didClose: () => {
                    document.homeForm.upload1.focus();
                }
            })
            return false;
        }
        var input = document.homeForm.upload1;
        if (input.files)
        {
            var file = input.files[0];
            if (file.size > 1024 * 1024 * 5)
            {
                Swal.fire({
                    title: "File size should not exceed 5 MB.",
                    didClose: () => {
                        document.homeForm.upload1.focus();
                    }
                })
                return false;
            }
        }
    }
    return true;
}

function  submittrainingcertform()
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
    if (checkCertificationDetails())
    {
        document.getElementById('submitdiv').innerHTML = "<img src='../assets/images/loading.gif' align='absmiddle' />";
        document.homeForm.doaddtrainingcertdetail.value = "no";
        document.homeForm.doSavetrainingcertdetail.value = "yes";
        document.homeForm.action = "../home/HomeAction.do";
        document.homeForm.submit();
    }
}

//---------------------------------------------------------------------- Candidate --------------------------------------------------
function clearcity()
{
    document.homeForm.cityId.value = "0";
    document.homeForm.cityName.value = "";
    var url = "../ajax/home/getcountrycode.jsp";
    var httploc = getHTTPObject();
    var getstr = "countryId=" + document.homeForm.countryId.value;
    httploc.open("POST", url, true);
    httploc.onreadystatechange = function ()
    {
        if (httploc.readyState == 4)
        {
            if (httploc.status == 200)
            {
                var response = trim(httploc.responseText);
                document.homeForm.code1Id.value = response;
                document.homeForm.code2Id.value = response;
                document.homeForm.code3Id.value = response;
                document.homeForm.ecode1Id.value = response;
                document.homeForm.ecode2Id.value = response;
            }
        }
    };
    httploc.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    httploc.setRequestHeader("Content-length", getstr.length);
    httploc.setRequestHeader("Connection", "close");
    httploc.send(getstr);
    setState();
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
    if (checkCandidate())
    {
        document.getElementById('submitdiv').innerHTML = "<img src='../assets/images/loading.gif' align='absmiddle' />";
        document.homeForm.doSave.value = "yes";
        document.homeForm.doCancel.value = "no";
        document.homeForm.action = "../home/HomeAction.do";
        document.homeForm.submit();
    }
}

function checkCandidate()
{
    if (document.homeForm.photofile.value != "")
    {
        if (!(document.homeForm.photofile.value).match(/(\.(png)|(jpg)|(jpeg))$/i))
        {
            Swal.fire({
                title: "Only .jpg, .jpeg, .png are allowed.",
                didClose: () => {
                    document.homeForm.photofile.focus();
                }
            })
            return false;
        }
        var input = document.homeForm.photofile;
        if (input.files)
        {
            var file = input.files[0];
            if (file.size > 1024 * 1024 * 5)
            {
                Swal.fire({
                    title: "File size should not exceed 5 MB.",
                    didClose: () => {
                        document.homeForm.photofile.focus();
                    }
                })
                return false;
            }
        }
    }

    if (trim(document.homeForm.firstname.value) == "")
    {
        Swal.fire({
            title: "Please enter first name.",
            didClose: () => {
                document.homeForm.firstname.focus();
            }
        })
        return false;
    }
    if (validname(document.homeForm.firstname) == false)
    {
        return false;
    }

    if (document.homeForm.middlename.value != "")
    {

        if (validname(document.homeForm.middlename) == false)
        {
            document.homeForm.middlename.focus();
            return false;
        }
    }

    if (document.homeForm.lastname.value == "")
    {
        Swal.fire({
            title: "Please enter last name.",
            didClose: () => {
                document.homeForm.lastname.focus();
            }
        })
        return false;
    }
    if (validname(document.homeForm.lastname) == false)
    {
        return false;
    }

    if (document.homeForm.candidateId.value < 0)
    {
        if (document.homeForm.fname.value == "")
        {
            Swal.fire({
                title: "Please attach resume.",
                didClose: () => {
                    document.homeForm.fname.focus();
                }
            })
            return false;
        }
    }
    if (document.homeForm.dob.value == "")
    {
        Swal.fire({
            title: "Please enter date of birth.",
            didClose: () => {
                document.homeForm.dob.focus();
            }
        })
        return false;
    }
    if (comparisionTest(document.homeForm.dob.value, document.homeForm.currentDate.value) == false)
    {
        Swal.fire({
            title: "Please check date of birth.",
            didClose: () => {
                document.homeForm.dob.focus();
            }
        })
        return false;
    }

    if (document.homeForm.gender.value == "Gender")
    {
        Swal.fire({
            title: "Please select gender.",
            didClose: () => {
                document.homeForm.gender.focus();
            }
        })
        return false;
    }

    if (document.homeForm.maritalstatusId.value <= 0)
    {
        Swal.fire({
            title: "Please select marital status.",
            didClose: () => {
                document.homeForm.maritalstatusId.focus();
            }
        })
        return false;
    }

    if (document.homeForm.countryId.value <= 0)
    {
        Swal.fire({
            title: "Please select country.",
            didClose: () => {
                document.homeForm.countryId.focus();
            }
        })
        return false;
    }
  
    if (trim(document.homeForm.cityName.value) == "")
    {
        Swal.fire({
            title: "Please select city using autofill.",
            didClose: () => {
                document.homeForm.cityName.focus();
            }
        })
        return false;
    }
     if (document.homeForm.cityId.value <= 0)
    {
        Swal.fire({
            title: "Please select city using autofill.",
            didClose: () => {
                document.homeForm.cityName.focus();
            }
        })
        return false;
    }
    if (validdesc(document.homeForm.cityName) == false)
    {
        return false;
    }
   
    if (document.homeForm.nationalityId.value <= 0)
    {
        Swal.fire({
            title: "Please select nationality.",
            didClose: () => {
                document.homeForm.nationalityId.focus();
            }
        })
        return false;
    }

    if (document.homeForm.emailId.value == "")
    {
        Swal.fire({
            title: "Please enter email ID.",
            didClose: () => {
                document.homeForm.emailId.focus();
            }
        })
        return false;
    }
    if (document.homeForm.emailId.value != "")
    {
        if (checkEmailAddress(document.homeForm.emailId) == false)
        {
            Swal.fire({
                title: "Please enter valid email ID.",
                didClose: () => {
                    document.homeForm.emailId.focus();
                }
            })
            return false;
        }
    }
    if (document.homeForm.code1Id.value == "")
    {
        Swal.fire({
            title: "Please select ISD code of primary contact number.",
            didClose: () => {
                document.homeForm.code1Id.focus();
            }
        })
        return false;
    }

    if (document.homeForm.contactno1.value == "")
    {
        Swal.fire({
            title: "Please enter primary contact number.",
            didClose: () => {
                document.homeForm.contactno1.focus();
            }
        })
        return false;
    }
    if (document.homeForm.contactno1.value != "")
    {
        if (document.homeForm.contactno1.value.length == "")
        {
            Swal.fire({
                title: "Please enter primary contact number.",
                didClose: () => {
                    document.homeForm.contactno1.focus();
                }
            })
            return false;
        }
        if (!checkContact(document.homeForm.contactno1.value))
        {
            Swal.fire({
                title: "Please enter valid primary contact number.",
                didClose: () => {
                    document.homeForm.contactno1.focus();
                }
            })
            return false;
        }
    }
    if (document.homeForm.applytype.value <= "0")
    {
        Swal.fire({
            title: "Please select applying job for.",
            didClose: () => {
                document.homeForm.applytype.focus();
            }
        })
        return false;
    }

    if (trim(document.homeForm.address1line1.value) == "")
    {
        Swal.fire({
            title: "Please enter permanent address in line 1.",
            didClose: () => {
                document.homeForm.address1line1.focus();
            }
        })
        return false;
    }

    if (trim(document.homeForm.address1line2.value) == "")
    {
        Swal.fire({
            title: "Please enter permanent address in line 2.",
            didClose: () => {
                document.homeForm.address1line2.focus();
            }
        })
        return false;
    }
    if (document.homeForm.assettypeId.value == "-1")
    {
        Swal.fire({
            title: "Please select asset type.",
            didClose: () => {
                document.homeForm.assettypeId.focus();
            }
        })
        return false;
    }
    if (document.homeForm.positionId.value == "-1")
    {
        Swal.fire({
            title: "Please select position applied for.",
            didClose: () => {
                document.homeForm.positionId.focus();
            }
        })
        return false;
    }

    if (document.homeForm.currencyId.value != "-1")
    {
        if (trim(document.homeForm.expectedsalary.value) == "")
        {
            Swal.fire({
                title: "Please enter expected salary.",
                didClose: () => {
                    document.homeForm.expectedsalary.focus();
                }
            })
            return false;
        }
        if (trim(document.homeForm.expectedsalary.value) <= "0")
        {
            Swal.fire({
                title: "Please enter valid expected salary.",
                didClose: () => {
                    document.homeForm.expectedsalary.focus();
                }
            })
            return false;
        }
        if (validnum(document.homeForm.expectedsalary) == false)
        {
            return false;
        }
    }
    if (document.homeForm.airport1.value != "")
    {
        if (validdesc(document.homeForm.airport1) == false)
        {
            document.homeForm.airport1.focus();
            return false;
        }
    }
    if (document.homeForm.airport2.value != "")
    {
        if (validdesc(document.homeForm.airport2) == false)
        {
            document.homeForm.airport2.focus();
            return false;
        }
    }
    return true;
}

function checkExperience()
{
    if (trim(document.homeForm.companyname.value) == "")
    {
        Swal.fire({
            title: "Please enter company name.",
            didClose: () => {
                document.homeForm.companyname.focus();
            }
        })
        return false;
    }
    if (validname(document.homeForm.companyname) == false)
    {
        return false;
    }
    if (document.homeForm.companyindustryId.value == "-1")
    {
        Swal.fire({
            title: "Please select company industry.",
            didClose: () => {
                document.homeForm.companyindustryId.focus();
            }
        })
        return false;
    }
    if (document.homeForm.assettypeId.value == "-1")
    {
        Swal.fire({
            title: "Please select Asset Type.",
            didClose: () => {
                document.homeForm.assettypeId.focus();
            }
        })
        return false;
    }
    if (trim(document.homeForm.assetname.value) == "")
    {
        Swal.fire({
            title: "Please enter asset name.",
            didClose: () => {
                document.homeForm.assetname.focus();
            }
        })
        return false;
    }    
    if (validdesc(document.homeForm.assetname) == false)
    {
        return false;
    }
    if (document.homeForm.positionId.value == "-1")
    {
        Swal.fire({
            title: "Please select position.",
            didClose: () => {
                document.homeForm.positionId.focus();
            }
        })
        return false;
    }
    if (document.homeForm.lastdrawnsalary.value != "") {
        if (validdesc(document.homeForm.lastdrawnsalary) == false)
        {
            return false;
        }
    }

    if (document.homeForm.workstartdate.value == "")
    {
        Swal.fire({
            title: "Please enter work start date.",
            didClose: () => {
                document.homeForm.workstartdate.focus();
            }
        })
        return false;
    }
    if (comparisionTest(document.homeForm.workstartdate.value, document.homeForm.currentDate.value) == false)
    {
        Swal.fire({
            title: "Please check work start date.",
            didClose: () => {
                document.homeForm.workstartdate.focus();
            }
        })
        return false;
    }

    if (document.homeForm.currentworkingstatus.checked == false)
    {
        if (document.homeForm.workenddate.value == "")
        {
            Swal.fire({
                title: "Please enter work end date.",
                didClose: () => {
                    document.homeForm.workenddate.focus();
                }
            })
            return false;
        }

        if (comparisionTest(document.homeForm.workstartdate.value, document.homeForm.workenddate.value) == false)
        {
            Swal.fire({
                title: "Please select work end date.",
                didClose: () => {
                    document.homeForm.workenddate.focus();
                }
            })
            return false;
        }

        if (comparisionTest(document.homeForm.workenddate.value, document.homeForm.currentDate.value) == false)
        {
            Swal.fire({
                title: "Please check work end date.",
                didClose: () => {
                    document.homeForm.workenddate.focus();
                }
            })
            return false;
        }
    }

    if (document.homeForm.experiencefile.value != "")
    {
        if (!(document.homeForm.experiencefile.value).match(/(\.(png)|(jpg)|(jpeg)|(pdf))$/i))
        {
            Swal.fire({
                title: "Only .jpg, .jpeg, .png, .pdf are allowed.",
                didClose: () => {
                    document.homeForm.experiencefile.focus();
                }
            })
            return false;
        }
        var input = document.homeForm.experiencefile;
        if (input.files)
        {
            var file = input.files[0];
            if (file.size > 1024 * 1024 * 5)
            {
                Swal.fire({
                    title: "File size should not exceed 5 MB.",
                    didClose: () => {
                        document.homeForm.experiencefile.focus();
                    }
                })
                return false;
            }
        }
    }
    return true;
}

function  submitexperienceform()
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
    if (checkExperience())
    {
        document.getElementById('submitdiv').innerHTML = "<img src='../assets/images/loading.gif' align='absmiddle' />";
        document.homeForm.doaddexperiencedetail.value = "no";
        document.homeForm.doSaveexperiencedetail.value = "yes";
        document.homeForm.action = "../home/HomeAction.do";
        document.homeForm.submit();
    }
}

function checkEducationDeatail() {
    if (document.homeForm.kindId.value == "-1")
    {
        Swal.fire({
            title: "Please select education kind.",
            didClose: () => {
                document.homeForm.kindId.focus();
            }
        })
        return false;
    }
    if (document.homeForm.degreeId.value == "-1")
    {
        Swal.fire({
            title: "Please select education degree.",
            didClose: () => {
                document.homeForm.degreeId.focus();
            }
        })
        return false;
    }

    return true;
}

function  submiteducationform()
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
    if (checkEducationDeatail())
    {
        document.getElementById('submitdiv').innerHTML = "<img src='../assets/images/loading.gif' align='absmiddle' />";
        document.homeForm.doaddeducationdetail.value = "no";
        document.homeForm.doSaveeducationdetail.value = "yes";
        document.homeForm.action = "../home/HomeAction.do";
        document.homeForm.submit();
    }
}

function viewworkexpfiles(filename1, filename2)
{
    var url = "../ajax/home/getimg_exp.jsp";
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

                $("#iframe").on("load", function () {
                    let head = $("#iframe").contents().find("head");
                    let css = '<style>img{margin: 0px auto;max-width:-webkit-fill-available;}</style>';
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

function viewimg(candidateId, fn)
{
    var url = "../ajax/home/getimg.jsp";
    var httploc = getHTTPObject();
    var getstr = "candidateId=" + candidateId;
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
                if(document.getElementById("rcount"))
                    document.getElementById("rcount").innerHTML = v2;

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

function delpic(clientassetpicId, clientassetId)
{
    var url = "../ajax/home/delimg.jsp";
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

function checkEmailAjaxCandidate()
{
    var url_email = "../ajax/candidate/checkemail.jsp";
    var emailValue = document.forms[0].emailId.value;
    var httploc1 = getHTTPObject();
    var getstr = "";
    getstr += "email=" + escape(emailValue);
    getstr += "&candidateId=" + document.forms[0].candidateId.value;
    httploc1.open("POST", url_email, true);
    httploc1.onreadystatechange = function ()
    {
        if (httploc1.readyState == 4)
        {
            if (httploc1.status == 200)
            {
                var response = httploc1.responseText;
                response = trim(response);
                if (response == "YES")
                {
                    document.forms[0].emailId.value = "";
                    Swal.fire("Email Id already exist in database.");
                }

            }
        }
    };
    httploc1.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    httploc1.setRequestHeader("Content-length", getstr.length);
    httploc1.setRequestHeader("Connection", "close");
    httploc1.send(getstr);
}
function setAssetPosition()
{
    var url = "../ajax/home/assetpositions.jsp";
    var httploc = getHTTPObject();
    var getstr = "assettypeId=" + document.forms[0].assettypeId.value;
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

function delfileedit(type)
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
            var childId = 0;
            if (type == 1)
                childId = document.homeForm.candidateLangId.value;
            else if (type == 2 || type == 3)
                childId = document.homeForm.experiencedetailId.value;
            var url = "../ajax/home/delfile.jsp";
            var httploc = getHTTPObject();
            var getstr = "type=" + type;
            getstr += "&childId=" + childId;
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
                            $('#view_pdf').modal('hide');
                            if (type == 1)
                            {
                                document.getElementById("preview_1").style.display = "none";
                                document.homeForm.langfilehidden.value = "";
                            } else if (type == 2)
                            {
                                document.getElementById("preview_2").style.display = "none";
                                document.homeForm.experiencehiddenfile.value = "";
                            } else if (type == 3)
                            {
                                document.getElementById("preview_3").style.display = "none";
                                document.homeForm.workinghiddenfile.value = "";
                            }
                        }
                    }
                }
            };
            httploc.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
            httploc.setRequestHeader("Content-length", getstr.length);
            httploc.setRequestHeader("Connection", "close");
            httploc.send(getstr);
        }
    })
}

function getSpanId()
{
    document.getElementById('spanId3').innerHTML = "";
    document.getElementById('spanId4').innerHTML = "";
    document.getElementById('spanId1').innerHTML = "";
    document.getElementById('spanId2').innerHTML = "";
    if (document.homeForm.ssmf.value == "Yes")
    {
        document.getElementById('spanId3').innerHTML = "*";
        document.getElementById('spanId4').innerHTML = "*";
    } else if (document.homeForm.ssmf.value == "No")
    {
        document.getElementById('spanId1').innerHTML = "*";
        document.getElementById('spanId2').innerHTML = "*";
    }
}

function modifydocumentdetailForm(id)
{

    document.homeForm.govdocumentId.value = id;
    document.homeForm.domodifygovdocumentdetail.value = "yes";
    document.homeForm.action = "../home/HomeAction.do";
    document.homeForm.submit();
}

function deletedocumentForm(id, status)
{
    document.homeForm.doDeletegovdocumentdetail.value = "yes";
    document.homeForm.govdocumentId.value = id;
    document.homeForm.status.value = status;
    document.homeForm.domodifygovdocumentdetail.value = "no";
    document.homeForm.action = "../home/HomeAction.do";
    document.homeForm.submit();
}

function submitdocumentform()
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
    if (checkDocumentForm())
    {
        document.getElementById('submitdiv').innerHTML = "<img src='../assets/images/loading.gif' align='absmiddle' />";
        document.homeForm.domodifygovdocumentdetail.value = "no";
        document.homeForm.doSavegovdocumentdetail.value = "yes";
        document.homeForm.action = "../home/HomeAction.do";
        document.homeForm.submit();
    }
}

function checkDocumentForm()
{
    if (document.homeForm.documentTypeId.value == "-1")
    {
        Swal.fire({
            title: "Please select document name.",
            didClose: () => {
                document.homeForm.documentTypeId.focus();
            }
        })
        return false;
    }
    if (trim(document.homeForm.documentNo.value) == "")
    {
        Swal.fire({
            title: "Please enter document no.",
            didClose: () => {
                document.homeForm.documentNo.focus();
            }
        })
        return false;
    }
    if (validdesc(document.homeForm.documentNo) == false)
    {
        return false;
    }
    if (document.homeForm.dateofissue.value != "")
    {
        if (comparisionTest(document.homeForm.dateofissue.value, document.homeForm.currentDate.value) == false)
        {
            Swal.fire({
                title: "Please check date of issue.",
                didClose: () => {
                    document.homeForm.dateofissue.value = "";
                }
            })
            return false;
        }
    }

    if (document.homeForm.dateofexpiry.value != "")
    {
        if (comparisionTest(document.homeForm.dateofissue.value, document.homeForm.dateofexpiry.value) == false)
        {
            Swal.fire({
                title: "Please check date of expiry.",
                didClose: () => {
                    document.homeForm.dateofexpiry.value = "";
                }
            })
            return false;
        }
    }
    if (document.homeForm.documentissuedbyId.value == "-1")
    {
        Swal.fire({
            title: "Please select document issued by.",
            didClose: () => {
                document.homeForm.documentissuedbyId.focus();
            }
        })
        return false;
    }
    return true;
}

function setDocumentissuedbyDDL()
{
    var url = "../ajax/home/getdocumentissuedby.jsp";
    var httploc = getHTTPObject();
    var getstr = "documentTypeId=" + document.homeForm.documentTypeId.value;
    httploc.open("POST", url, true);
    httploc.onreadystatechange = function ()
    {
        if (httploc.readyState == 4)
        {
            if (httploc.status == 200)
            {
                var response = httploc.responseText;
                document.getElementById("documentissuedbyId").innerHTML = '';
                document.getElementById("documentissuedbyId").innerHTML = response;
            }
        }
    };
    httploc.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    httploc.setRequestHeader("Content-length", getstr.length);
    httploc.setRequestHeader("Connection", "close");
    httploc.send(getstr);
}

function addtomasterajaxedu()
{
    if (trim(document.forms[0].mname.value) != "" && Number(document.forms[0].countryId.value) > 0)
    {
        var url = "../ajax/home/addtomasteredu.jsp";
        var httploc = getHTTPObject();
        var getstr = "countryId=" + document.forms[0].countryId.value;
        getstr += "&name=" + escape(document.forms[0].mname.value);
        httploc.open("POST", url, true);
        httploc.onreadystatechange = function ()
        {
            if (httploc.readyState == 4)
            {
                if (httploc.status == 200)
                {
                    var response = trim(httploc.responseText);
                    if (response == "No")
                        Swal.fire("Data already exist.");
                    else
                    {
                        document.forms[0].countryId.value = "-1";
                        document.forms[0].mname.value = "";
                        $('#city_modal').modal('hide');
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
        Swal.fire({
            title: "Please select country and enter name.",
            didClose: () => {
                document.forms[0].mname.focus();
            }
        })
    }
}

function viewimgdoc(govId)
{
    var url = "../ajax/home/getimg_doc.jsp";
    var httploc = getHTTPObject();
    var getstr = "govId=" + govId;
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

function deldocumentfiles(fileId, govId)
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
            var url = "../ajax/home/delimg_doc.jsp";
            var httploc = getHTTPObject();
            var getstr = "fileId=" + fileId;
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
                            viewimgdoc(govId);
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

function setAssetPosition()
{
    var url = "../ajax/home/assetpositions.jsp";
    var httploc = getHTTPObject();
    var getstr = "assettypeId=" + document.forms[0].assettypeId.value;
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

function resendOTP()
{
    document.forms[0].action="../home/index.jsp";
    document.forms[0].submit();
}

var timerOn = false;
function timer(remaining)
{
    document.getElementById('timer').innerHTML = "";
    var m = Math.floor(remaining / 60);
    var s = remaining % 60;

    m = m < 10 ? '0' + m : m;
    s = s < 10 ? '0' + s : s;
    document.getElementById('timer').innerHTML = m + ':' + s;
    remaining -= 1;
    if(remaining >= 0 && timerOn) {
        setTimeout(function() {
        timer(remaining);
        }, 1000);
        return;
    }
    if(!timerOn)
    {
        timerOn = false;
        return;
    }
    resendOTP();
}

function calltimer()
{
    timerOn = true;
    timer('300');
}

function  setState()
{
    var url = "../ajax/home/countryState.jsp";
    var httploc = getHTTPObject();
    var getstr = "countryId=" + document.forms[0].countryId.value;
    httploc.open("POST", url, true);
    httploc.onreadystatechange = function ()
    {
        if (httploc.readyState == 4)
        {
            if (httploc.status == 200)
            {
                var response = httploc.responseText;
                document.getElementById("stateId").innerHTML = '';
                document.getElementById("stateId").innerHTML = response;
            }
        }
    };
    httploc.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    httploc.setRequestHeader("Content-length", getstr.length);
    httploc.setRequestHeader("Connection", "close");
    httploc.send(getstr);
}