function DrawCaptcha()  
{
    setcaptchasession("3");
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
                document.getElementById("txtCaptcha").value = response;
                document.crewloginForm.cap.value = "";
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
    if (document.crewloginForm.emailId.value === "")
    {
        Swal.fire({
        title: "Please enter Email ID.",
        didClose:() => {
          document.crewloginForm.emailId.focus();
        }
        }) 
        return false;
    }
    if(document.crewloginForm.cap.value == "")
    {
        Swal.fire({
        title: "Please enter captcha.",
        didClose:() => {
          document.crewloginForm.cap.focus();
        }
        }) 
        return false;
    }
    if(removeSpaces(document.crewloginForm.txtCaptcha.value) != removeSpaces(document.crewloginForm.cap.value))
    {
        Swal.fire({
        title: "Captcha does not match.",
        didClose:() => {
        document.crewloginForm.cap.value = "";
        document.crewloginForm.cap.focus();
        DrawCaptcha();
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
        var url_forgot = "/jxp/ajax/crewloginotp.jsp";
        document.getElementById("submitdiv").innerHTML="Processing...";
        var https = getHTTPObject();
        var getstr = "";
        var v1 = document.crewloginForm.emailId.value;
        getstr += "emailId=" + escape(v1);
        getstr += "&cap=" + document.crewloginForm.cap.value; 
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
                        location.assign("/jxp/crewlogin/verify_crewlogin.jsp");
                    }
                    else if(trim(val) == "NOMAIL")
                    {
                        Swal.fire({
                        title: "Something went wrong.",
                        didClose:() => {
                            DrawCaptcha();
                            document.crewloginForm.cap.value = "";
                            document.crewloginForm.cap.focus();
                            document.getElementById("submitdiv").innerHTML="<a href='javascript: generateotpForm();' class='btn btn-block text-center my-3'>Get OTP</a>";
                        }
                        }) 
                        return false;
                    }
                    else if(trim(val) == "INV")
                    {
                        Swal.fire({
                        title: "Please check captcha.",
                        didClose:() => {
                            DrawCaptcha();
                            document.crewloginForm.cap.value = "";
                            document.crewloginForm.cap.focus();
                            document.getElementById("submitdiv").innerHTML="<a href='javascript: generateotpForm();' class='btn btn-block text-center my-3'>Get OTP</a>";
                        }
                        }) 
                        return false;
                    }
                    else if(trim(val) == "NOTEXIST")
                    {
                        Swal.fire({
                        title: "Email Id not exist in our database.",
                        didClose:() => {
                        document.crewloginForm.cap.value = "";
                        document.crewloginForm.cap.focus();
                        DrawCaptcha();
                        document.getElementById("submitdiv").innerHTML="<a href='javascript: generateotpForm();' class='btn btn-block text-center my-3'>Get OTP</a>";
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
                            document.getElementById("submitdiv").innerHTML="<a href='javascript: generateotpForm();' class='btn btn-block text-center my-3'>Get OTP</a>";
                        }
                        }) 
                        return false;
                    }
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
    {
        document.getElementById("otp2").focus();
    }
    else if(tp == 2)
    { 
        document.getElementById("otp3").focus();
    }
    else if(tp == 3)
    {
        document.getElementById("otp4").focus();
    }
    else if(tp == 4)
    {
        document.getElementById("otp5").focus();
    }
    else if(tp == 5)
    {
        document.getElementById("otp6").focus();
    }
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
        var url = "/jxp/ajax/checkcrewotp.jsp";
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
                        title: "Invalid OTP.",
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
                    else if(response == "Yes")
                    {
                        location.assign("/jxp/feedback/feedback_welcome.jsp");
                    }
                    else if(response == "DIRECTFEEDBACK")
                    {
                        location.assign("/jxp/feedback/FeedbackAction.do?doView=yes");                        
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

function resendOTP()
{
    document.forms[0].action="../crewlogin/index.jsp";
    document.forms[0].submit();
}

var timerOn = false;
function timer(remaining)
{
    document.getElementById('timer').innerHTML = "";
    var m = Math.floor(Number(remaining) / 60);
    var s = Number(remaining) % 60;
    
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