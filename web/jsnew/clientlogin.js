function checkLogin()
{
    var st = "";
    if (document.loginForm.username.value == "")
    {
        if(st == "")
            st += "Please enter user ID.";
    }    
    if (document.loginForm.password.value == "")
    {
        if(st == "")
            st += "<br/>Please enter password.";
        else
            st += "<br/>Please enter password.";
    }
    if(document.loginForm.cap.value == "")
    {
        if(st == "")
            st += "<br/>Please enter captcha.";
        else
            st += "<br/>Please enter captcha.";
    }    
    if(st != "")
    {
        Swal.fire({
        title: st,
        imageWidth: 230,
        didClose:() => {
          document.loginForm.cap.focus();
        }
        })
        return false;
    }
    if(removeSpaces(document.loginForm.txtCaptcha.value) != removeSpaces(document.loginForm.cap.value))
    {
        DrawCaptcha();
        document.loginForm.cap.value = "";
        Swal.fire({
        title: "Please check captcha.",
        imageWidth: 230,
        didClose:() => {
          document.loginForm.cap.focus();
        }
        })
        return false;        
    }
    return true;
}

function submitForm()
{
    if(checkLogin())
    {
        var url_login = "/jxp/ajax/checkmanagerlogin.jsp";
        var https = getHTTPObject();
        var v1 = document.loginForm.username.value;
        var getstr = "Username=" + encodeURIComponent(v1) + "&";
        v1 = document.loginForm.password.value;
        getstr += "&Password=" + escape(v1);
        getstr += "&cap=" + document.loginForm.cap.value;        
        https.open("POST", url_login, true);
        https.onreadystatechange = function()
        {
            if (https.readyState == 4)
            {
                if(https.status == 200)
                {
                    var response = https.responseText;
                    var val = response;
                    if(trim(val) == "S")
                    {
                        location.assign("/jxp/clientlogin/ClientloginAction.do");
                    }
                    else if(trim(val) == "INV")
                    {
                        Swal.fire({
                        title: "Please check captcha.",
                        didClose:() => {
                        DrawCaptcha();
                        }
                        }) 
                        return false;
                    }
                    else if(trim(val) == "EXP")
                    {
                        Swal.fire({
                        title: "Your account has been expired, please contact to administrator.",
                        didClose:() => {
                          DrawCaptcha();
                        }
                        }) 
                        return false;
                    }
                    else
                    {
                    Swal.fire({
                        title: "Invalid user id or password.",
                        didClose:() => {
                          DrawCaptcha();
                        }
                        }) 
                        return false;
                    }
                }                
            }
        };
        //document.getElementById("submitdiv").innerHTML="Processing...";
        https.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
        https.setRequestHeader("Content-length", getstr.length);
        https.setRequestHeader("Connection", "close");
        https.send(getstr);
    }
}

function handleKeyLogin(e)
{
    var key=e.keyCode || e.which;
    if (key===13)
    {
        e.preventDefault();
        submitForm();
    }
}    

function resetForm()
{
    DrawCaptcha();
    document.loginForm.reset();
}

function checkEmail()
{
    if (document.loginForm.username.value === "")
    {
        Swal.fire({
        title: "Please enter user ID.",
        didClose:() => {
          document.loginForm.username.focus();
        }
        }) 
        return false;
    }
    if(document.loginForm.cap.value == "")
    {
        Swal.fire({
        title: "Please enter captcha.",
        didClose:() => {
          document.loginForm.cap.focus();
        }
        }) 
        return false;
    }
    if(removeSpaces(document.loginForm.txtCaptcha.value) != removeSpaces(document.loginForm.cap.value))
    {
        Swal.fire({
        title: "Captcha does not match.",
        didClose:() => {
        document.loginForm.cap.value = "";
        document.loginForm.cap.focus();
        }
        }) 
        return false;
    }
    return true;
}

function forgotForm()
{
    if(checkEmail())
    {
        var url_forgot = "/jxp/ajax/forgot_clientlogin.jsp";
        document.getElementById("submitdiv").innerHTML="Processing...";
        var https = getHTTPObject();
        var getstr = "";
        var v1 = document.loginForm.username.value;
        getstr += "Username=" + escape(v1);
        getstr += "&cap=" + document.loginForm.cap.value; 
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
                        Swal.fire({
                        title: "Password has been sent to your email",
                        didClose:() => {
                        DrawCaptcha();
                        document.loginForm.cap.value = "";
                        document.loginForm.cap.focus();
                        document.getElementById("submitdiv").innerHTML="<a href='javascript: submitForm();' class='btn btn-block text-center my-3'> Log In</a>";
                        }
                        }) 
                        return false;
                    }
                    else if(trim(val) == "INV")
                    {
                        Swal.fire({
                            title: "Please check captcha.",
                            didClose:() => {
                            document.loginForm.cap.value = "";
                            document.loginForm.cap.focus();
                            document.getElementById("submitdiv").innerHTML="<a href='javascript: forgotForm();' class='forgot_pd'> Forgot Password?</a>";
                            }
                        }) 
                        return false;
                    }
                    else
                    {
                        DrawCaptcha();
                        Swal.fire({
                            title: "Password cannot be sent. Please check your email Id or contact your Administrator.",
                            didClose:() => {
                            document.getElementById("submitdiv").innerHTML="<a href='javascript: forgotForm();' class='forgot_pd'> Forgot Password?</a>";
                            }
                        }) 
                        return false;
                    }
                    document.getElementById("submitdiv").innerHTML="<a href='javascript: forgotForm();' class='forgot_pd'> Forgot Password?</a>";
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

function DrawCaptcha()  
{
    setcaptchasession("1");
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
                if (tp == "1")
                {
                    document.getElementById("txtCaptcha").value = response;
                    document.loginForm.cap.value = "";
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

function logout()
{
    var s = "Are you sure you want to logout?";
    Swal.fire({
        title: s,
        confirmButtonColor: '#3085d6',
        cancelButtonColor: '#d33',
        confirmButtonText: 'Confirm',
        showCloseButton: true,
        allowOutsideClick: false,
        allowEscapeKey: false
    }).then((result) => {
        if (result.isConfirmed)
        {
            document.forms[0].doLogout.value = "yes"
            document.forms[0].action="/jxp/clientlogin/ClientloginAction.do?doLogout=yes";    
            document.forms[0].submit();    
        }
    });
}

function view(jobpostId)
{
    if (document.clientloginForm.doView)
        document.clientloginForm.doView.value = "yes";
    document.forms[0].jobpostId.value = jobpostId;
    document.forms[0].target = "";
    document.clientloginForm.action = "../clientlogin/ClientloginAction.do";
    document.clientloginForm.submit();
}

function getModel(shortlistId)
{
    var url = "../ajax/clientlogin/getdetails.jsp";
    var httploc = getHTTPObject();
    var getstr = "";
    getstr += "shortlistId=" + shortlistId;
    httploc.open("POST", url, true);
    httploc.onreadystatechange = function ()
    {
        if (httploc.readyState == 4)
        {
            if (httploc.status == 200)
            {
                var response = httploc.responseText;
                var arr = new Array();
                document.getElementById('int_div').innerHTML = '';
                document.getElementById('int_div').innerHTML = response;                
                jQuery(document).ready(function () {
                    $(".kt-selectpicker").selectpicker();
                    $(".wesl_dt").datepicker({
                        todayHighlight: !0,
                        format: "dd-M-yyyy",
                        autoclose: "true",
                        orientation: "bottom",
                        startDate: new Date()
                    });
                });
                $(".timepicker-24").timepicker({
                    format: 'mm:mm',
                    autoclose: !0,
                    minuteStep: 5,
                    showSeconds: !1,
                    showMeridian: !1
                });                
            }
        }
    };
    httploc.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    httploc.setRequestHeader("Content-length", getstr.length);
    httploc.setRequestHeader("Connection", "close");
    httploc.send(getstr);
    document.getElementById('int_div').innerHTML = "<div><img src='../assets/images/loading.gif' align='absmiddle'/>Please wait. Loading...</div>";
}

function getRescheduleModel(interviewId)
{
    var url = "../ajax/clientlogin/getdetails.jsp";
    var httploc = getHTTPObject();
    var getstr = "";
    getstr += "interviewId=" + interviewId;
    httploc.open("POST", url, true);
    httploc.onreadystatechange = function ()
    {
        if (httploc.readyState == 4)
        {
            if (httploc.status == 200)
            {
                var response = httploc.responseText;
                var arr = new Array();
                document.getElementById('intre_div').innerHTML = '';
                document.getElementById('intre_div').innerHTML = response;     
                var date = new Date();
                date.setDate(date.getDate());
                jQuery(document).ready(function () {
                    $(".kt-selectpicker").selectpicker();
                    $(".wesl_dt").datepicker({
                        todayHighlight: !0,
                        format: "dd-M-yyyy",
                        autoclose: "true",
                        orientation: "bottom",
                        startDate: date,
                        clearBtn: true
                    });
                });
                $(".timepicker-24").timepicker({
                    format: 'mm:mm',
                    autoclose: !0,
                    minuteStep: 5,
                    showSeconds: !1,
                    showMeridian: !1
                });
                
            }
        }
    };
    httploc.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    httploc.setRequestHeader("Content-length", getstr.length);
    httploc.setRequestHeader("Connection", "close");
    httploc.send(getstr);
    document.getElementById('intre_div').innerHTML = "<div><img src='../assets/images/loading.gif' align='absmiddle'/>Please wait. Loading...</div>";
}

function goback()
{
    if (document.clientloginForm.doView)
        document.clientloginForm.doView.value = "no";
    if (document.clientloginForm.doCancel)
        document.clientloginForm.doCancel.value = "yes";
    document.forms[0].target = "";
    document.clientloginForm.action = "../clientlogin/ClientloginAction.do";
    document.clientloginForm.submit();
}

function getChgedatetime() 
{
    var url = "../ajax/clientlogin/getChangedatetime.jsp";
    var httploc = getHTTPObject();
    var getstr = "";
    var tz1 = document.getElementById("selInterviewerTimeZone").options[document.getElementById("selInterviewerTimeZone").selectedIndex].text;
    var tz2 = document.getElementById("selCandTimeZone").options[document.getElementById("selCandTimeZone").selectedIndex].text;
    if (document.getElementById("txtstartdate").value != "" && document.getElementById("txttime").value != "" && tz1 != "" && tz2 != "")
    {
        getstr += "inputdate=" + document.getElementById("txtstartdate").value + " " + document.getElementById("txttime").value;
        getstr += "&intz=" + tz2;
        getstr += "&outtz=" + tz1;
        httploc.open("POST", url, true);
        httploc.onreadystatechange = function ()
        {
            if (httploc.readyState == 4)
            {
                if (httploc.status == 200)
                {
                    var response = httploc.responseText;
                    document.getElementById('txtDate').value = '';
                    document.getElementById('txtDate').value = response;
                    document.getElementById('sDate').innerHTML = response;
                }
            }
        };
        httploc.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
        httploc.setRequestHeader("Content-length", getstr.length);
        httploc.setRequestHeader("Connection", "close");
        httploc.send(getstr);
    }
}

function getonoffvalue()
{
    if (document.getElementById("selMode").value == "Online") {
        document.getElementById("onofflbl").innerHTML = "Link";
    } 
    else if (document.getElementById("selMode").value == "Offline") {
        document.getElementById("onofflbl").innerHTML = "Location";
    }
}

function saveInterview()
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
    if (checkInterview())
    {
        document.getElementById("submitdiv").innerHTML="<img src='../assets/images/loading.gif' align='absmiddle' />";
        document.clientloginForm.doSaveInterview.value = "yes";
        document.clientloginForm.doCancel.value = "no";
        document.clientloginForm.action = "../clientlogin/ClientloginAction.do";
        document.clientloginForm.submit();
        
    }
}

function checkInterview()
{
    if (trim(document.clientloginForm.txtstartdate.value) == "")
    {
        Swal.fire({
            title: "Please select date.",
            didClose: () => {
                document.clientloginForm.txtstartdate.focus();
            }
        })
        return false;
    }
    if (validdate(document.clientloginForm.txtstartdate) == false)
    {
        return false;
    }
    if (document.clientloginForm.selMode.value == "mode")
    {
        Swal.fire({
            title: "Please select mode.",
            didClose: () => {
                document.clientloginForm.selMode.focus();
            }
        })
        return false;
    }
    if (document.clientloginForm.selDuration.value <= 0)
    {
        Swal.fire({
            title: "Please select duration (minutes).",
            didClose: () => {
                document.clientloginForm.selDuration.focus();
            }
        })
        return false;
    }
    if (trim(document.clientloginForm.txttime.value) == "")
    {
        Swal.fire({
            title: "Please select time.",
            didClose: () => {
                document.clientloginForm.txttime.focus();
            }
        })
        return false;
    }
    if (validtime(document.clientloginForm.txttime) == false)
    {
        return false;
    }
    if (document.clientloginForm.selInterviewer.value <= 0)
    {
        Swal.fire({
            title: "Please select Interviewer.",
            didClose: () => {
                document.clientloginForm.selInterviewer.focus();
            }
        })
        return false;
    }
    
    if (document.clientloginForm.selInterviewerTimeZone.value <= 0)
    {
        Swal.fire({
            title: "Please select Interviewer country - time zone.",
            didClose: () => {
                document.clientloginForm.selInterviewerTimeZone.focus();
            }
        })
        return false;
    }
    if (document.clientloginForm.selCandTimeZone.value <= 0)
    {
        Swal.fire({
            title: "Please select candidate country - time zone.",
            didClose: () => {
                document.clientloginForm.selCandTimeZone.focus();
            }
        })
        return false;
    }
    if (trim(document.clientloginForm.txtDate.value) == "")
    {
        Swal.fire({
            title: "Please enter date.",
            didClose: () => {
                document.clientloginForm.txtDate.focus();
            }
        })
        return false;
    }
    if (trim(document.clientloginForm.txtLocLink.value) == "")
    {
        Swal.fire({
            title: "Please enter details.",
            didClose: () => {
                document.clientloginForm.txtLocLink.focus();
            }
        })
        return false;
    }
    if (validdesc(document.clientloginForm.txtLocLink) == false)
    {
        return false;
    }
    return true;
}

function getEmailModal(shortlistId, intvId)
{
    $('#mail_modal').modal('show');
    var url = "../ajax/clientlogin/getEmailModal.jsp";
    var httploc = getHTTPObject();
    var getstr = "";
    getstr += "shortlistId=" + shortlistId;
    getstr += "&interviewId=" + intvId;
    httploc.open("POST", url, true);
    httploc.onreadystatechange = function ()
    {
        if (httploc.readyState == 4)
        {
            if (httploc.status == 200)
            {
                var response = httploc.responseText;
                var arr = new Array();
                document.getElementById('emailmodal').innerHTML = '';
                document.getElementById('emailmodal').innerHTML = response;
            }
        }
    };
    httploc.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    httploc.setRequestHeader("Content-length", getstr.length);
    httploc.setRequestHeader("Connection", "close");
    httploc.send(getstr);
    document.getElementById('emailmodal').innerHTML = "<div><img src='../assets/images/loading.gif' align='absmiddle'/>Please wait. Loading...</div>";
}

function mailToCandidate(interviewId, jobpostId)
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
        var url = "../ajax/clientlogin/sendCandidatemail.jsp";
        var httploc = getHTTPObject();
        var getstr = "";
        getstr += "fromval=" + fromval;
        getstr += "&toval=" + toval;
        getstr += "&ccval=" + ccval;
        getstr += "&bccval=" + bccval;
        getstr += "&subject=" + subject;
        getstr += "&description=" + description;
        getstr += "&interviewId=" + interviewId;
        getstr += "&jobpostId=" + jobpostId;
        httploc.open("POST", url, true);
        httploc.onreadystatechange = function ()
        {
            if (httploc.readyState == 4)
            {
                if (httploc.status == 200)
                {
                    var response = httploc.responseText;
                    $('#mail_modal').modal('hide');
                    $('#thank_you_modal_1').modal('show');
                }
            }
        };
        httploc.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
        httploc.setRequestHeader("Content-length", getstr.length);
        httploc.setRequestHeader("Connection", "close");
        httploc.send(getstr);
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

function getScheduledModal(id, type)
{
    var url = "../ajax/clientlogin/getScheduleDetail.jsp";
    var httploc = getHTTPObject();
    var getstr = "";
    getstr += "interviewId=" + id;
    getstr += "&type=" + type;
    httploc.open("POST", url, true);
    httploc.onreadystatechange = function ()
    {
        if (httploc.readyState == 4)
        {
            if (httploc.status == 200)
            {
                var response = httploc.responseText;
                var arr = new Array();
                document.getElementById('scint_div').innerHTML = '';
                document.getElementById('scint_div').innerHTML = response;
            }
        }
    };
    httploc.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    httploc.setRequestHeader("Content-length", getstr.length);
    httploc.setRequestHeader("Connection", "close");
    httploc.send(getstr);
    document.getElementById('scint_div').innerHTML = "<div><img src='../assets/images/loading.gif' align='absmiddle'/>Please wait. Loading...</div>";
}

function getEvaluateModal(id, type)
{
    var url = "../ajax/clientlogin/getScheduleDetail.jsp";
    var httploc = getHTTPObject();
    var getstr = "";
    getstr += "interviewId=" + id;
    getstr += "&type=" + type;
    httploc.open("POST", url, true);
    httploc.onreadystatechange = function ()
    {
        if (httploc.readyState == 4)
        {
            if (httploc.status == 200)
            {
                var response = httploc.responseText;
                var arr = new Array();
                document.getElementById('evalint_div').innerHTML = '';
                document.getElementById('evalint_div').innerHTML = response;
            }
        }
    };
    httploc.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    httploc.setRequestHeader("Content-length", getstr.length);
    httploc.setRequestHeader("Connection", "close");
    httploc.send(getstr);
    document.getElementById('scint_div').innerHTML = "<div><img src='../assets/images/loading.gif' align='absmiddle'/>Please wait. Loading...</div>";
}

function evaluateCandidate(id)
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
    if (checkEvaluation())
    {       
        document.getElementById("saveEV").innerHTML="<img src='../assets/images/loading.gif' align='absmiddle' />";
        document.clientloginForm.type.value = id;
        document.clientloginForm.doEvaluation.value = "yes";
        document.clientloginForm.doCancel.value = "no";
        document.clientloginForm.action = "../clientlogin/ClientloginAction.do";
        document.clientloginForm.submit();        
    }
}

function checkEvaluation()
{
    if (trim(document.clientloginForm.score.value) <= 0)
    {
        Swal.fire({
            title: "Please enter score.",
            didClose: () => {
                document.clientloginForm.score.focus();
            }
        })
        return false;
    }
    if (trim(document.clientloginForm.score.value) > 100)
    {
        Swal.fire({
            title: "score between 0-100.",
            didClose: () => {
                document.clientloginForm.score.focus();
            }
        })
        return false;
    }
    if (trim(document.clientloginForm.remark.value) == "")
    {
        Swal.fire({
            title: "Please enter remarks.",
            didClose: () => {
                document.clientloginForm.remark.focus();
            }
        })
        return false;
    }
    return true;
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
        var url = "../ajax/clientlogin/getinfo.jsp";
        var httploc = getHTTPObject();
        var getstr = "";
        var next_value = escape(document.clientloginForm.nextValue.value);
        var search_value = escape(document.clientloginForm.search.value);
        var positionIndex = escape(document.clientloginForm.positionIndex.value);
        var assetIdIndex = escape(document.clientloginForm.assetIdIndex.value);
        var clientIdIndex = escape(document.clientloginForm.clientIdIndex.value);
        getstr += "nextValue=" + next_value;
        getstr += "&next=" + v;
        getstr += "&search=" + search_value;
        getstr += "&positionIndex=" + positionIndex;
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

function setAssetDDL()
{
    var url = "../ajax/clientlogin/getasset.jsp";
    document.getElementById("assetIdIndex").value = '-1';
    document.getElementById("positionIndex").value = '-1';
    var httploc = getHTTPObject();
    var getstr = "clientIdIndex=" + document.clientloginForm.clientIdIndex.value;
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
                searchFormAjax('s', '-1');
                setPositionDDL();
            }
        }
    };
    httploc.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    httploc.send(getstr);
}

function setPositionDDL()
{
    var url = "../ajax/clientlogin/getAssetPosition.jsp";
    var httploc = getHTTPObject();
    document.getElementById("positionIndex").value = '-1';
    var getstr = "assetIdIndex=" + document.clientloginForm.assetIdIndex.value;
    httploc.open("POST", url, true);
    httploc.onreadystatechange = function ()
    {
        if (httploc.readyState == 4)
        {
            if (httploc.status == 200)
            {
                var response = httploc.responseText;
                document.getElementById("positionIndex").innerHTML = '';
                document.getElementById("positionIndex").innerHTML = response;
                searchFormAjax('s', '-1');
            }
        }
    };
    httploc.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    httploc.send(getstr);
}

function resetFilter()
{
    document.clientloginForm.search.value = "";
    document.clientloginForm.clientIdIndex.value = "-1";
    document.clientloginForm.assetIdIndex.value = "-1";
    document.clientloginForm.positionIndex.value = "-1";
    searchFormAjax('s', '-1');
    setAssetDDL();
}

function getRejectModal(shortlistId)
{
    $('#reject_modal').modal('show');
    var url = "../ajax/clientlogin/getRejectMail.jsp";
    var httploc = getHTTPObject();
    var getstr = "";
    getstr += "shortlistId=" + shortlistId;
    httploc.open("POST", url, true);
    httploc.onreadystatechange = function ()
    {
        if (httploc.readyState == 4)
        {
            if (httploc.status == 200)
            {
                var response = httploc.responseText;
                var arr = new Array();
                document.getElementById('rejectModal').innerHTML = '';
                document.getElementById('rejectModal').innerHTML = response;
            }
        }
    };
    httploc.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    httploc.setRequestHeader("Content-length", getstr.length);
    httploc.setRequestHeader("Connection", "close");
    httploc.send(getstr);
    document.getElementById('rejectModal').innerHTML = "<div><img src='../assets/images/loading.gif' align='absmiddle'/>Please wait. Loading...</div>";
}

function mailToCoordinator(shortlistId)
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
        var url = "../ajax/clientlogin/sendRejectionMail.jsp";
        var httploc = getHTTPObject();
        var getstr = "";
        getstr += "fromval=" + fromval;
        getstr += "&toval=" + toval;
        getstr += "&ccval=" + ccval;
        getstr += "&bccval=" + bccval;
        getstr += "&subject=" + subject;
        getstr += "&description=" + description;
        getstr += "&jobpostId=" + document.forms[0].jobpostId.value;
        getstr += "&shortlistId=" + shortlistId;
        httploc.open("POST", url, true);
        httploc.onreadystatechange = function ()
        {
            if (httploc.readyState == 4)
            {
                if (httploc.status == 200)
                {
                    var response = httploc.responseText;
                    $('#reject_modal').modal('hide');
                    $('#thank_you_modal_3').modal('show');
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
    var httploc = getHTTPObject();
    var url_sort = "../ajax/clientlogin/getshortlistedcandidate.jsp";
    var getstr = "";
    getstr += "jobpostid=" + document.clientloginForm.jobpostId.value;
    getstr += "&search=" + document.clientloginForm.search2.value;
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