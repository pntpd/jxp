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
        var url_login = "/jxp/ajax/checklogin.jsp";
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
                        location.assign("/jxp/dashboard/welcome.jsp");
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
        var url_forgot = "/jxp/ajax/forgot.jsp";
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
                        document.getElementById("submitdiv").innerHTML="<a href='javascript: forgotForm();' class='forgot_pd'> Forgot Password?</a>";
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
