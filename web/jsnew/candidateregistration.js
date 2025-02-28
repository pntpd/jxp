function resetForm()
{
    document.candidateRegistrationForm.reset();
}

function DrawCaptcha()
{
    setcaptchasession();
}

function setcaptchasession()
{
    var url = "../ajax/candidateregistration/captchasession.jsp";
    var httploc = getHTTPObject();
    var getstr = "type=ocs";
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
                document.forms[0].cap.value = "";
            }
        }
    }
    httploc.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    httploc.send(getstr);
}


function checkEmail()
{
    if (document.forms[0].emailId.value === "")
    {
        Swal.fire({
            title: "Please enter Email ID.",
            didClose: () => {
                document.forms[0].emailId.focus();
            }
        })
        return false;
    }
    if (document.forms[0].cap.value == "")
    {
        Swal.fire({
            title: "Please enter captcha.",
            didClose: () => {
                document.forms[0].cap.focus();
            }
        })
        return false;
    }
    if (removeSpaces(document.forms[0].txtCaptcha.value) != removeSpaces(document.forms[0].cap.value))
    {
        Swal.fire({
            title: "Captcha does not match.",
            didClose: () => {
                document.forms[0].cap.value = "";
                document.forms[0].cap.focus();
            }
        })
        return false;
    }
    return true;
}

function removeSpaces(string)
{
    return string.split(' ').join('');
}

function handleKeyGebOTP(e)
{
    var key = e.keyCode || e.which;
    if (key === 13)
    {
        e.preventDefault();
        generateotpForm();
    }
}

function generateotpForm()
{
    if (checkEmail())
    {
        var url_forgot = "../ajax/candidateregistration/generateotp.jsp";
        document.getElementById("submitdiv").innerHTML = "<center><img src='../assets/pnt-images/loading.gif' align='absmiddle' /> Processing...</center>";
        var https = getHTTPObject();
        var getstr = "";
        var v1 = document.forms[0].emailId.value;
        getstr += "emailId=" + escape(v1);
        getstr += "&cap=" + document.forms[0].cap.value;
        https.open("POST", url_forgot, true);
        https.onreadystatechange = function ()
        {
            if (https.readyState === 4)
            {
                if (https.status === 200)
                {
                    var response = https.responseText;
                    var val = response;
                    if (trim(val) == "CTA")
                    {
                        document.getElementById("submitdiv").innerHTML = "<a class='btn loginBtn btn-block text-center my-3 get-otp' onclick=\"javascript: generateotpForm();\">Get OTP</a>";
                        DrawCaptcha();
                        Swal.fire({
                            title: "Your profile already exist with us.",
                            didClose: () => {
                            }
                        })
                        return false;
                    } else if (trim(val) === 'S1')
                    {
                        location.assign("/jxp/candidateregistration/verify_registration.jsp");
                    } else if (trim(val) == "NOMAIL")
                    {
                        Swal.fire({
                            title: "Something went wrong.",
                            didClose: () => {
                                document.forms[0].cap.value = "";
                                document.forms[0].cap.focus();
                            }
                        })
                        return false;
                    } else if (trim(val) == "INV")
                    {
                        Swal.fire({
                            title: "Please check captcha.",
                            didClose: () => {
                                document.forms[0].cap.value = "";
                                document.forms[0].cap.focus();
                            }
                        })
                        return false;
                    } else
                    {
                        document.getElementById("submitdiv").innerHTML = "<a class='btn loginBtn btn-block text-center my-3 get-otp' onclick=\"javascript: generateotpForm();\">Get OTP</a>";
                        DrawCaptcha();
                        Swal.fire({
                            title: "OTP cannot be sent. Please check your email Id or contact your Administrator.",
                            didClose: () => {
                            }
                        })
                        return false;
                    }
                    document.getElementById("submitdiv").innerHTML = "<a class='btn loginBtn btn-block text-center my-3 get-otp' onclick=\"javascript: generateotpForm();\">Get OTP</a>";
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

function calltimer()
{
    timerOn = true;
    timer('300');
}

function timer(remaining)
{
    document.getElementById('timer').innerHTML = "";
    var m = Math.floor(remaining / 60);
    var s = remaining % 60;

    m = m < 10 ? '0' + m : m;
    s = s < 10 ? '0' + s : s;
    document.getElementById('timer').innerHTML = m + ':' + s;
    remaining -= 1;
    if (remaining >= 0 && timerOn) {
        setTimeout(function () {
            timer(remaining);
        }, 1000);
        return;
    }
    if (!timerOn)
    {
        timerOn = false;
        return;
    }
    resendOTP();
}

function resendOTP()
{
    document.forms[0].action = "../candidateregistration/level1.jsp";
    document.forms[0].submit();
}

function handleKeyLogin(e)
{
    var key = e.keyCode || e.which;
    if (key === 13)
    {
        e.preventDefault();
        submitotpForm();
    }
}


function checkOTP()
{
    if (document.forms[0].otp1.value == "")
    {
        Swal.fire({
            title: "Please enter otp.",
            didClose: () => {
                document.forms[0].otp1.focus();
            }
        })
        return false;
    }
    if (document.forms[0].otp2.value == "")
    {
        Swal.fire({
            title: "Please enter otp.",
            didClose: () => {
                document.forms[0].otp2.focus();
            }
        })
        return false;
    }
    if (document.forms[0].otp3.value == "")
    {
        Swal.fire({
            title: "Please enter otp.",
            didClose: () => {
                document.forms[0].otp3.focus();
            }
        })
        return false;
    }
    if (document.forms[0].otp4.value == "")
    {
        Swal.fire({
            title: "Please enter otp.",
            didClose: () => {
                document.forms[0].otp4.focus();
            }
        })
        return false;
    }
    if (document.forms[0].otp5.value == "")
    {
        Swal.fire({
            title: "Please enter otp.",
            didClose: () => {
                document.forms[0].otp5.focus();
            }
        })
        return false;
    }
    if (document.forms[0].otp6.value == "")
    {
        Swal.fire({
            title: "Please enter otp.",
            didClose: () => {
                document.forms[0].otp6.focus();
            }
        })
        return false;
    }
    return true;
}

function moveNext(index) {
    let currentInput = document.getElementById("otp" + index);
    let nextInput = document.getElementById("otp" + (index + 1));

    if (currentInput.value.length === 1 && nextInput) {
        nextInput.focus();
    }
}

function moveBack(event, index) {
    let currentInput = document.getElementById("otp" + index);
    let prevInput = document.getElementById("otp" + (index - 1));

    if (event.key === "Backspace" && currentInput.value === "") {
        if (prevInput) {
            prevInput.value = ""; // Clear the previous input
            prevInput.focus();
        }
    }
}

function submitotpForm()
{
    if (checkOTP())
    {
        var url = "../ajax/candidateregistration/checkotp.jsp";
        var httploc = getHTTPObject();
        var otp = document.forms[0].otp1.value + "" + document.forms[0].otp2.value + "" + document.forms[0].otp3.value + "" + document.forms[0].otp4.value + "" + document.forms[0].otp5.value + "" + document.forms[0].otp6.value;
        var getstr = "";
        getstr += "otp=" + escape(otp);
        httploc.open("POST", url, true);
        httploc.onreadystatechange = function ()
        {
            if (httploc.readyState == 4)
            {
                if (httploc.status == 200)
                {
                    var response = trim(httploc.responseText);
                    if (response == "INVALIDOTP")
                    {
                        Swal.fire({
                            title: "Please check OTP.",
                            didClose: () => {
                                document.forms[0].otp1.value = "";
                                document.forms[0].otp2.value = "";
                                document.forms[0].otp3.value = "";
                                document.forms[0].otp4.value = "";
                                document.forms[0].otp5.value = "";
                                document.forms[0].otp6.value = "";
                            }
                        })
                        return false;
                    } else if (response == "CTA")
                    {
                        Swal.fire({
                            title: "Your profile already exist with us.",
                            didClose: () => {
                            }
                        })
                        return false;
                    } else if (response == "EXISTING")
                    {
                        document.forms[0].doModify.value = "yes";
                        document.forms[0].action = "../candidateregistration/CandidateRegistrationAction.do";
                        document.forms[0].submit();
                    } else if (response == "NEW")
                    {
                        document.forms[0].action = "../candidateregistration/CandidateRegistrationAction.do";
                        document.forms[0].submit();
                    } else
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

function getshowhide(id) {
    for (var i = 1; i < 10; i++) {
        let imgElement = document.getElementById("img" + i);
        let divElement = document.getElementById("div" + i);

        if (i == id) {
            if (imgElement.alt === "Down Arrow") {
                divElement.style.display = "block";
                imgElement.alt = "Up Arrow";
                imgElement.src = "../assets/images/white-up-arrow.png";
                scrollToTopOnClick('mySection' + id);
            } else {
                divElement.style.display = "none";
                imgElement.alt = "Down Arrow";
                imgElement.src = "../assets/images/white-down-arrow.png";
            }
        } else {
            divElement.style.display = "none";
            imgElement.alt = "Down Arrow";
            imgElement.src = "../assets/images/white-down-arrow.png";
        }
    }
}

function getAlert(id) {
    for (var i = 1; i < 10; i++) {
        let imgElement = document.getElementById("img" + i);
        let divElement = document.getElementById("div" + i);

        if (i == id) {
            if (imgElement.alt === "Down Arrow") {
                divElement.style.display = "block";
                imgElement.alt = "Up Arrow";
                imgElement.src = "../assets/images/white-up-arrow.png";
                scrollToTopOnClick('mySection' + id);
            }
        } else {
            divElement.style.display = "none";
            imgElement.alt = "Down Arrow";
            imgElement.src = "../assets/images/white-down-arrow.png";
        }
    }
}

function scrollToTopOnClick(id) {
    var targetDiv = document.getElementById(id);

    var div1 = $('.header');
    var div2 = $('#' + id);
    var div1Position = div1.offset();
    var div2Position = div2.offset();

    if (targetDiv) {
        targetDiv.scrollIntoView({
            behavior: 'smooth',
            block: 'nearest'
        });
        window.scrollBy(0, div2Position.top - (div1Position.top + div1.outerHeight()));
    }
}

function setClass(tp)
{
    document.getElementById("upload_link_" + tp).className = "attache_btn uploaded_ocs_img";
}


function getCommunicationAddress(id) {
    document.getElementById("dcommunication").style.display = "";
    if (id.checked) {
        document.getElementById("dcommunication").style.display = "none";
    }
}

function checkFresher(id) {
    document.getElementById("subdiv7_1").style.display = "";
    document.getElementById("subdiv7_2").style.display = "";
    if (id.checked) {
        document.getElementById("subdiv7_1").style.display = "none";
        document.getElementById("subdiv7_2").style.display = "none";
    }
}

function checkCurrent(id, val) {
    if (val == 1) {
        document.getElementById("workenddate").value = "";
        document.getElementById("workenddate").disabled = false;
        if (id.checked) {
            document.getElementById("workenddate").disabled = true;
        }
    } else if (val == 2) {
        document.getElementById("dateofexpiry").value = "";
        document.getElementById("dateofexpiry").disabled = false;
        if (id.checked) {
            document.getElementById("dateofexpiry").disabled = true;
        }
    }
}

function getCurrentAge(dob) {
    const birthDate = new Date(dob.value);
    const today = new Date();
    let age = today.getFullYear() - birthDate.getFullYear();
    const monthDifference = today.getMonth() - birthDate.getMonth();
    if (monthDifference < 0 || (monthDifference === 0 && today.getDate() < birthDate.getDate())) {
        age--;
    }
    document.forms[0].age.value = age;
}

function checkLanguage1() {
    if (document.getElementById("speak1").checked || document.getElementById("read1").checked || document.getElementById("write1").checked) {
        document.getElementById("dontKnow1").checked = false;
    }
}
function checkLanguage2() {
    if (document.getElementById("dontKnow1").checked) {
        document.getElementById("speak1").checked = false;
        document.getElementById("read1").checked = false;
        document.getElementById("write1").checked = false;
    }
}

function getDraftSave() {
    if (document.candidateRegistrationForm.fname.value == "" && document.candidateRegistrationForm.localFile.value == "")
    {
        getAlert('1');
        Swal.fire({
            title: "Please attach resume.",
            didClose: () => {
                document.candidateRegistrationForm.fname.focus();
            }
        })
        return false;
    }

    if (trim(document.candidateRegistrationForm.firstname.value) == "")
    {
        getAlert('2');
        Swal.fire({
            title: "Please enter first name.",
            didClose: () => {
                document.candidateRegistrationForm.firstname.focus();
            }
        })
        return false;
    }
    if (validname(document.candidateRegistrationForm.firstname) == false)
    {
        getAlert('2');
        return false;
    }

    document.getElementById('submitdiv').innerHTML = "<img src='../assets/pnt-images/loading.gif' align='absmiddle' />";
    document.candidateRegistrationForm.doSaveDraft.value = "yes";
    document.candidateRegistrationForm.action = "../candidateregistration/CandidateRegistrationAction.do";
    document.candidateRegistrationForm.submit();
}

function getSubmit() {
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
    if (checkCandidateRegistration())
    {
        document.getElementById('submitdiv').innerHTML = "<img src='../assets/pnt-images/loading.gif' align='absmiddle' />";
        document.candidateRegistrationForm.doSave.value = "yes";
        document.candidateRegistrationForm.action = "../candidateregistration/CandidateRegistrationAction.do";
        document.candidateRegistrationForm.submit();
    }
}

function checkCandidateRegistration() {
    if (document.candidateRegistrationForm.fname.value == "" && document.candidateRegistrationForm.localFile.value == "")
    {
        getAlert('1');
        Swal.fire({
            title: "Please attach resume.",
            didClose: () => {
                document.candidateRegistrationForm.fname.focus();
            }
        })
        return false;
    }

    if (trim(document.candidateRegistrationForm.firstname.value) == "")
    {
        getAlert('2');
        Swal.fire({
            title: "Please enter first name.",
            didClose: () => {
                document.candidateRegistrationForm.firstname.focus();
            }
        })
        return false;
    }
    if (validname(document.candidateRegistrationForm.firstname) == false)
    {
        getAlert('2');
        return false;
    }

    if (trim(document.candidateRegistrationForm.gender.value) == "Gender")
    {
        getAlert('2');
        Swal.fire({
            title: "Please select gender.",
            didClose: () => {
                document.candidateRegistrationForm.gender.focus();
            }
        })
        return false;
    }

    if (document.candidateRegistrationForm.maritalstatusId.value <= 0)
    {
        getAlert('2');
        Swal.fire({
            title: "Please select marital status.",
            didClose: () => {
                document.candidateRegistrationForm.maritalstatusId.focus();
            }
        })
        return false;
    }

    if (document.candidateRegistrationForm.middlename.value != "")
    {

        if (validname(document.candidateRegistrationForm.middlename) == false)
        {
            getAlert('2');
            document.candidateRegistrationForm.middlename.focus();
            return false;
        }
    }

    if (document.candidateRegistrationForm.nationalityId.value <= 0)
    {
        getAlert('2');
        Swal.fire({
            title: "Please select nationality.",
            didClose: () => {
                document.candidateRegistrationForm.nationalityId.focus();
            }
        })
        return false;
    }

    if (document.candidateRegistrationForm.religion.value != "")
    {

        if (validname(document.candidateRegistrationForm.religion) == false)
        {
            getAlert('2');
            document.candidateRegistrationForm.religion.focus();
            return false;
        }
    }

    if (document.candidateRegistrationForm.lastname.value == "")
    {
        getAlert('2');
        Swal.fire({
            title: "Please enter last name.",
            didClose: () => {
                document.candidateRegistrationForm.lastname.focus();
            }
        })
        return false;
    }
    if (validname(document.candidateRegistrationForm.lastname) == false)
    {
        getAlert('2');
        return false;
    }

    if (document.candidateRegistrationForm.dob.value == "")
    {
        getAlert('2');
        Swal.fire({
            title: "Please enter date of birth.",
            didClose: () => {
                document.candidateRegistrationForm.dob.focus();
            }
        })
        return false;
    } else {
        if (validdate(document.candidateRegistrationForm.dob) == false)
        {
            getAlert('2');
            return false;
        }
    }
    if (comparisionTest(document.candidateRegistrationForm.dob.value, document.candidateRegistrationForm.currentDate.value) == false)
    {
        getAlert('2');
        Swal.fire({
            title: "Please check date of birth.",
            didClose: () => {
                document.candidateRegistrationForm.dob.focus();
            }
        })
        return false;
    }

    if (isNaN(document.candidateRegistrationForm.age.value)) {
        getAlert('2');
        Swal.fire({
            title: "Please enter valid age.",
            didClose: () => {
                document.candidateRegistrationForm.age.focus();
            }
        })
        return false;
    }

    if (document.candidateRegistrationForm.placeofbirth.value != "")
    {
        getAlert('2');
        if (validname(document.candidateRegistrationForm.placeofbirth) == false)
        {
            document.candidateRegistrationForm.placeofbirth.focus();
            return false;
        }
    }

    if (document.candidateRegistrationForm.adhaar.value == "")
    {
        getAlert('2');
        Swal.fire({
            title: "Please enter adhaar number.",
            didClose: () => {
                document.candidateRegistrationForm.adhaar.focus();
            }
        })
        return false;
    } else {
        if (isNaN(document.candidateRegistrationForm.adhaar.value) || document.candidateRegistrationForm.adhaar.value.length < 12) {
            getAlert('2');
            Swal.fire({
                title: "Please enter valid adhaar number.",
                didClose: () => {
                    document.candidateRegistrationForm.adhaar.focus();
                }
            })
            return false;
        }
    }


    if (document.candidateRegistrationForm.adhaarfile.value == "" && document.candidateRegistrationForm.hdnadhaarfile.value == "")
    {
        getAlert('2');
        Swal.fire({
            title: "please upload adhaar.",
            didClose: () => {
                document.candidateRegistrationForm.adhaarfile.focus();
            }
        })
        return false;
    }

    if (document.candidateRegistrationForm.adhaarfile.value != "")
    {
        if (!(document.candidateRegistrationForm.adhaarfile.value).match(/(\.(png)|(jpg)|(jpeg)|(pdf))$/i))
        {
            getAlert('2');
            Swal.fire({
                title: "Only .jpg, .jpeg, .png, .pdf are allowed",
                didClose: () => {
                    document.candidateRegistrationForm.adhaarfile.focus();
                }
            })
            return false;
        }
        var input = document.candidateRegistrationForm.adhaarfile;
        if (input.files)
        {
            var file = input.files[0];
            if (file.size > 1024 * 1024 * 5)
            {
                getAlert('2');
                Swal.fire({
                    title: "File size should not exceed 5 MB.",
                    didClose: () => {
                        document.candidateRegistrationForm.adhaarfile.focus();
                    }
                })
                return false;
            }
        }
    }

    if (document.candidateRegistrationForm.pancard.value == "")
    {
        getAlert('2');
        Swal.fire({
            title: "Please enter pan card number.",
            didClose: () => {
                document.candidateRegistrationForm.pancard.focus();
            }
        })
        return false;
    } else {
        if (!checkPAN(document.candidateRegistrationForm.pancard.value)) {
            getAlert('2');
            Swal.fire({
                title: "Please enter valid pan card number.",
                didClose: () => {
                    document.candidateRegistrationForm.pancard.focus();
                }
            })
            return false;
        }
    }

    if (document.candidateRegistrationForm.panfile.value == "" && document.candidateRegistrationForm.hdnpanfile.value == "")
    {
        getAlert('2');
        Swal.fire({
            title: "please upload pan.",
            didClose: () => {
                document.candidateRegistrationForm.panfile.focus();
            }
        })
        return false;
    }

    if (document.candidateRegistrationForm.panfile.value != "")
    {
        if (!(document.candidateRegistrationForm.panfile.value).match(/(\.(png)|(jpg)|(jpeg)|(pdf))$/i))
        {
            getAlert('2');
            Swal.fire({
                title: "Only .jpg, .jpeg, .png, .pdf are allowed",
                didClose: () => {
                    document.candidateRegistrationForm.panfile.focus();
                }
            })
            return false;
        }
        var input = document.candidateRegistrationForm.panfile;
        if (input.files)
        {
            var file = input.files[0];
            if (file.size > 1024 * 1024 * 5)
            {
                getAlert('2');
                Swal.fire({
                    title: "File size should not exceed 5 MB.",
                    didClose: () => {
                        document.candidateRegistrationForm.panfile.focus();
                    }
                })
                return false;
            }
        }
    }

    if (document.candidateRegistrationForm.code1Id.value <= 0)
    {
        getAlert('3');
        Swal.fire({
            title: "Please select country code.",
            didClose: () => {
                document.candidateRegistrationForm.code1Id.focus();
            }
        })
        return false;
    }
    if (document.candidateRegistrationForm.contactno1.value == "")
    {
        getAlert('3');
        Swal.fire({
            title: "Please enter primary contact number.",
            didClose: () => {
                document.candidateRegistrationForm.contactno1.focus();
            }
        })
        return false;
    } else {
        if (!checkContact(document.candidateRegistrationForm.contactno1.value))
        {
            getAlert('3');
            Swal.fire({
                title: "Please enter valid primary contact number.",
                didClose: () => {
                    document.candidateRegistrationForm.contactno1.focus();
                }
            })
            return false;
        }
    }

    if (trim(document.candidateRegistrationForm.address1line1.value) == "")
    {
        getAlert('4');
        Swal.fire({
            title: "Please enter permanent address in line 1.",
            didClose: () => {
                document.candidateRegistrationForm.address1line1.focus();
            }
        })
        return false;
    }
    if (trim(document.candidateRegistrationForm.address1line2.value) == "")
    {
        getAlert('4');
        Swal.fire({
            title: "Please enter permanent address in line 2.",
            didClose: () => {
                document.candidateRegistrationForm.address1line2.focus();
            }
        })
        return false;
    }

    if (document.candidateRegistrationForm.countryId.value <= 0)
    {
        getAlert('4');
        Swal.fire({
            title: "Please select country.",
            didClose: () => {
                document.candidateRegistrationForm.countryId.focus();
            }
        })
        return false;
    }

    if (document.candidateRegistrationForm.stateId.value <= 0)
    {
        getAlert('4');
        Swal.fire({
            title: "Please select state.",
            didClose: () => {
                document.candidateRegistrationForm.stateId.focus();
            }
        })
        return false;
    }

    if (eval(document.candidateRegistrationForm.cityId.value) <= 0)
    {
        getAlert('4');
        Swal.fire({
            title: "Please select city using autofill.",
            didClose: () => {
                document.candidateRegistrationForm.cityName.focus();
            }
        })
        return false;
    }
    if (trim(document.candidateRegistrationForm.cityName.value) == "")
    {
        getAlert('4');
        Swal.fire({
            title: "Please select city using autofill.",
            didClose: () => {
                document.candidateRegistrationForm.cityName.focus();
            }
        })
        return false;
    }
    if (validdesc(document.candidateRegistrationForm.cityName) == false)
    {
        getAlert('4');
        return false;
    }

    if (document.candidateRegistrationForm.assettypeId.value == "-1")
    {
        getAlert('5');
        Swal.fire({
            title: "Please select asset type.",
            didClose: () => {
                document.candidateRegistrationForm.assettypeId.focus();
            }
        })
        return false;
    }

    if (document.candidateRegistrationForm.positionId.value == "-1")
    {
        getAlert('5');
        Swal.fire({
            title: "Please select position applied for.",
            didClose: () => {
                document.candidateRegistrationForm.positionId.focus();
            }
        })
        return false;
    }

    if (document.candidateRegistrationForm.kindId.value == "-1")
    {
        getAlert('6');
        Swal.fire({
            title: "Please select highest qualification.",
            didClose: () => {
                document.candidateRegistrationForm.kindId.focus();
            }
        })
        return false;
    }

    if (document.candidateRegistrationForm.degreeId.value == "-1")
    {
        getAlert('6');
        Swal.fire({
            title: "Please select field of study.",
            didClose: () => {
                document.candidateRegistrationForm.degreeId.focus();
            }
        })
        return false;
    }

    if (document.getElementById("isFresher").checked) {
    } else {
        if (Number(document.candidateRegistrationForm.expCount.value) <= 0) {
            getAlert('7');
            Swal.fire({
                title: "Please add at least one experience.",
                didClose: () => {
                    document.candidateRegistrationForm.companyname.focus();
                }
            })
            return false;
        }
    }

    var checkboxes = document.querySelectorAll('input[name="languageProf1"]:checked');
    if (checkboxes.length === 0) {
        getAlert('8');
        Swal.fire({
            title: "Please select at least one option for language.",
            didClose: () => {
                document.candidateRegistrationForm.languageProf1.focus();
            }
        })
        return false;
    }

    if (Number(document.candidateRegistrationForm.certCount.value) <= 0) {
        getAlert('9');
        Swal.fire({
            title: "Please add at least one certification.",
            didClose: () => {
                document.candidateRegistrationForm.coursenameId.focus();
            }
        })
        return false;
    }
    return true;
}

//---------------------------------------------------------------------- Candidate --------------------------------------------------
function clearcity(id)
{
    if (id == "1") {
        document.candidateRegistrationForm.cityId.value = "0";
        document.candidateRegistrationForm.cityName.value = "";
    } else if (id == "2") {
        document.candidateRegistrationForm.cityId2.value = "0";
        document.candidateRegistrationForm.cityName2.value = "";
    }
    setState(id);
}

function setState(id)
{
    var url = "../ajax/candidateregistration/countryState.jsp";
    var httploc = getHTTPObject();
    var getstr
    if (id == "1") {
        getstr = "countryId=" + document.forms[0].countryId.value;
    } else if (id == "2") {
        getstr = "countryId=" + document.forms[0].countryId2.value;
    }
    httploc.open("POST", url, true);
    httploc.onreadystatechange = function ()
    {
        if (httploc.readyState == 4)
        {
            if (httploc.status == 200)
            {
                var response = httploc.responseText;
                if (id == "1") {
                    document.getElementById("stateId").innerHTML = '';
                    document.getElementById("stateId").innerHTML = response;
                } else if (id == "2") {
                    document.getElementById("stateId2").innerHTML = '';
                    document.getElementById("stateId2").innerHTML = response;
                }
            }
        }
    };
    httploc.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    httploc.setRequestHeader("Content-length", getstr.length);
    httploc.setRequestHeader("Connection", "close");
    httploc.send(getstr);
}

function setAssetPosition()
{
    var url = "../ajax/candidateregistration/assetpositions.jsp";
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

function setAssetPosition2()
{
    var assettypeId = document.forms[0].expassettypeId.value;
    var url = "../ajax/candidateregistration/assetpositions2.jsp";
    var httploc = getHTTPObject();
    var getstr = "assettypeId=" + assettypeId;
    getstr += "&positionId=" + document.forms[0].positionId.value;
    httploc.open("POST", url, true);
    httploc.onreadystatechange = function ()
    {
        if (httploc.readyState == 4)
        {
            if (httploc.status == 200)
            {
                var response = httploc.responseText;
                document.getElementById("positionId2").innerHTML = '';
                document.getElementById("positionId2").innerHTML = response;
            }
        }
    };
    httploc.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    httploc.setRequestHeader("Content-length", getstr.length);
    httploc.setRequestHeader("Connection", "close");
    httploc.send(getstr);
}

function checkWorkExp() {
    if (trim(document.candidateRegistrationForm.companyname.value) == "")
    {
        Swal.fire({
            title: "Please enter company name.",
            didClose: () => {
                document.candidateRegistrationForm.companyname.focus();
            }
        })
        return false;
    }
    if (validname(document.candidateRegistrationForm.companyname) == false)
    {
        return false;
    }

    if (document.candidateRegistrationForm.expassettypeId.value == "-1")
    {
        Swal.fire({
            title: "Please select asset type.",
            didClose: () => {
                document.candidateRegistrationForm.expassettypeId.focus();
            }
        })
        return false;
    }

    if (trim(document.candidateRegistrationForm.assetname.value) == "")
    {
        Swal.fire({
            title: "Please enter asset name.",
            didClose: () => {
                document.candidateRegistrationForm.assetname.focus();
            }
        })
        return false;
    }
    if (validdesc(document.candidateRegistrationForm.assetname) == false)
    {
        return false;
    }

    if (document.candidateRegistrationForm.exppositionId.value == "-1")
    {
        Swal.fire({
            title: "Please select position applied for.",
            didClose: () => {
                document.candidateRegistrationForm.exppositionId.focus();
            }
        })
        return false;
    }

    if (document.candidateRegistrationForm.workstartdate.value == "")
    {
        Swal.fire({
            title: "Please enter work start date.",
            didClose: () => {
                document.candidateRegistrationForm.workstartdate.focus();
            }
        })
        return false;
    }
    if (comparisionTest(document.candidateRegistrationForm.workstartdate.value, document.candidateRegistrationForm.currentDate.value) == false)
    {
        Swal.fire({
            title: "Please check work start date.",
            didClose: () => {
                document.candidateRegistrationForm.workstartdate.focus();
            }
        })
        return false;
    }
    if (document.candidateRegistrationForm.currentworkingstatus.checked == false)
    {
        if (document.candidateRegistrationForm.workenddate.value == "")
        {
            Swal.fire({
                title: "Please enter work end date.",
                didClose: () => {
                    document.candidateRegistrationForm.workenddate.focus();
                }
            })
            return false;
        }
        if (comparisionTest(document.candidateRegistrationForm.workstartdate.value, document.candidateRegistrationForm.workenddate.value) == false)
        {
            Swal.fire({
                title: "Please select work end date.",
                didClose: () => {
                    document.candidateRegistrationForm.workenddate.focus();
                }
            })
            return false;
        }
        if (comparisionTest(document.candidateRegistrationForm.workenddate.value, document.candidateRegistrationForm.currentDate.value) == false)
        {
            Swal.fire({
                title: "Please check work end date.",
                didClose: () => {
                    document.candidateRegistrationForm.workenddate.focus();
                }
            })
            return false;
        }
    }
    return true;
}
function addExperience()
{
    if (checkWorkExp()) {
        var selectElement1 = document.forms[0].expassettypeId;
        var selectedOption1 = selectElement1.options[selectElement1.selectedIndex];
        var expassettypeId = selectedOption1.value;
        var expassettype = selectedOption1.text;
        var selectElement2 = document.forms[0].exppositionId;
        var selectedOption2 = selectElement2.options[selectElement2.selectedIndex];
        var exppositionId = selectedOption2.value;
        var expposition = selectedOption2.text;

        var url = "../ajax/candidateregistration/getAddWorkExp.jsp";
        var httploc = getHTTPObject();
        var getstr = "companyname=" + document.forms[0].companyname.value;
        getstr += "&assetname=" + document.forms[0].assetname.value;
        getstr += "&workstartdate=" + document.forms[0].workstartdate.value;
        getstr += "&currentworkingstatus=" + document.forms[0].currentworkingstatus.checked;
        getstr += "&pastOCSemp=" + document.forms[0].pastOCSemp.checked;
        getstr += "&expassettypeId=" + expassettypeId;
        getstr += "&expassettype=" + expassettype;
        getstr += "&exppositionId=" + exppositionId;
        getstr += "&expposition=" + expposition;
        getstr += "&workenddate=" + document.forms[0].workenddate.value;
        httploc.open("POST", url, true);
        httploc.onreadystatechange = function ()
        {
            if (httploc.readyState == 4)
            {
                if (httploc.status == 200)
                {
                    var response = httploc.responseText;
                    document.forms[0].companyname.value = "";
                    document.forms[0].assetname.value = "";
                    document.forms[0].workstartdate.value = "";
                    document.forms[0].workenddate.value = "";
                    document.forms[0].currentworkingstatus.checked = false;
                    document.getElementById("workenddate").disabled = false;
                    document.forms[0].pastOCSemp.checked = false;
                    document.forms[0].expassettypeId.value = "-1";
                    document.forms[0].exppositionId.value = "-1";
                    var arr = new Array();
                    arr = trim(response).split("#@#@#");
                    document.getElementById("exptbody").innerHTML = '';
                    document.getElementById("exptbody").innerHTML = trim(arr[0]);
                    document.getElementById("expmobdiv").innerHTML = '';
                    document.getElementById("expmobdiv").innerHTML = trim(arr[1]);
                    document.forms[0].expCount.value = trim(arr[2]);
                    if (trim(arr[2]) > 0) {
                        document.getElementById("dFresher").style.display = "none";
                    } else {
                        document.getElementById("dFresher").style.display = "";
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

function getEditExp(companyname, exppositionId)
{
    var url = "../ajax/candidateregistration/getModify.jsp";
    var httploc = getHTTPObject();
    var getstr = "editexp=edit";
    getstr += "&companyname=" + companyname;
    getstr += "&exppositionId=" + exppositionId;
    httploc.open("POST", url, true);
    httploc.onreadystatechange = function ()
    {
        if (httploc.readyState == 4)
        {
            if (httploc.status == 200)
            {
                var response = httploc.responseText;
                var arr = new Array();
                var newarr = new Array();
                arr = trim(response).split("#@#@#");
                document.getElementById("exptbody").innerHTML = '';
                document.getElementById("exptbody").innerHTML = trim(arr[0]);
                document.getElementById("expmobdiv").innerHTML = '';
                document.getElementById("expmobdiv").innerHTML = trim(arr[1]);
                newarr = trim(arr[2]).split("#@#");
                document.forms[0].companyname.value = trim(newarr[0]);
                document.forms[0].assetname.value = trim(newarr[1]);
                document.forms[0].workstartdate.value = trim(newarr[2]);
                if (trim(newarr[3]) == 1) {
                    document.forms[0].currentworkingstatus.checked = true;
                } else {
                    document.forms[0].currentworkingstatus.checked = false;
                }
                if (trim(newarr[4]) == 1) {
                    document.forms[0].pastOCSemp.checked = true;
                } else {
                    document.forms[0].pastOCSemp.checked = false;
                }
                document.forms[0].expassettypeId.value = trim(newarr[5]);
                setAssetPosition2();
                setTimeout(function () {
                    document.forms[0].exppositionId.value = trim(newarr[7]);
                }, 200);
                document.forms[0].workenddate.value = trim(newarr[9]);
                document.forms[0].expCount.value = trim(arr[3]);
                if (trim(arr[3]) > 0) {
                    document.getElementById("dFresher").style.display = "none";
                } else {
                    document.getElementById("dFresher").style.display = "";
                }
            }
        }
    };
    httploc.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    httploc.setRequestHeader("Content-length", getstr.length);
    httploc.setRequestHeader("Connection", "close");
    httploc.send(getstr);
}

function getRemoveExp(companyname, exppositionId)
{
    var url = "../ajax/candidateregistration/getRemove.jsp";
    var httploc = getHTTPObject();
    var getstr = "removeexp=remove";
    getstr += "&companyname=" + companyname;
    getstr += "&exppositionId=" + exppositionId;
    httploc.open("POST", url, true);
    httploc.onreadystatechange = function ()
    {
        if (httploc.readyState == 4)
        {
            if (httploc.status == 200)
            {
                var response = httploc.responseText;
                var arr = new Array();
                arr = trim(response).split("#@#@#");
                document.getElementById("exptbody").innerHTML = '';
                document.getElementById("exptbody").innerHTML = trim(arr[0]);
                document.getElementById("expmobdiv").innerHTML = '';
                document.getElementById("expmobdiv").innerHTML = trim(arr[1]);
                document.forms[0].expCount.value = trim(arr[2]);
                if (trim(arr[2]) > 0) {
                    document.getElementById("dFresher").style.display = "none";
                } else {
                    document.getElementById("dFresher").style.display = "";
                }
            }
        }
    };
    httploc.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    httploc.setRequestHeader("Content-length", getstr.length);
    httploc.setRequestHeader("Connection", "close");
    httploc.send(getstr);
}

function checkCertificate() {

    if (document.candidateRegistrationForm.coursenameId.value == "-1")
    {
        Swal.fire({
            title: "Please select Certificates.",
            didClose: () => {
                document.candidateRegistrationForm.coursenameId.focus();
            }
        })
        return false;
    }

    if (document.candidateRegistrationForm.dateofissue.value == "")
    {
        Swal.fire({
            title: "Please enter date of issue.",
            didClose: () => {
                document.candidateRegistrationForm.dateofissue.focus();
            }
        })
        return false;
    }
    if (comparisionTest(document.candidateRegistrationForm.dateofissue.value, document.candidateRegistrationForm.currentDate.value) == false)
    {
        Swal.fire({
            title: "Please check date of issue.",
            didClose: () => {
                document.candidateRegistrationForm.dateofissue.focus();
            }
        })
        return false;
    }
    if (document.candidateRegistrationForm.isexpiry.checked == false)
    {
        if (document.candidateRegistrationForm.dateofexpiry.value == "")
        {
            Swal.fire({
                title: "Please enter expiry date.",
                didClose: () => {
                    document.candidateRegistrationForm.dateofexpiry.focus();
                }
            })
            return false;
        }
        if (comparisionTest(document.candidateRegistrationForm.dateofissue.value, document.candidateRegistrationForm.dateofexpiry.value) == false)
        {
            Swal.fire({
                title: "Please check expiry date.",
                didClose: () => {
                    document.candidateRegistrationForm.dateofexpiry.focus();
                }
            })
            return false;
        }
    }

    if (document.candidateRegistrationForm.tempfname.value == "")
    {
        Swal.fire({
            title: "Please attach certificate.",
            didClose: () => {
                document.candidateRegistrationForm.tempfname.focus();
            }
        })
        return false;
    }
    return true;
}

function getAddCertificate()
{
    if (checkCertificate()) {
        var selectElement1 = document.forms[0].coursenameId;
        var selectedOption1 = selectElement1.options[selectElement1.selectedIndex];
        var coursenameId = selectedOption1.value;
        var coursename = selectedOption1.text;
        var selectElement2 = document.forms[0].approvedbyId;
        var selectedOption2 = selectElement2.options[selectElement2.selectedIndex];
        var approvedbyId = selectedOption2.value;
        var approvedby = selectedOption2.text;

        var url = "../ajax/candidateregistration/getAddWorkExp.jsp";
        var httploc = getHTTPObject();
        var getstr = "coursenameId=" + coursenameId;
        getstr += "&coursename=" + coursename;
        getstr += "&dateofissue=" + document.forms[0].dateofissue.value;
        getstr += "&dateofexpiry=" + document.forms[0].dateofexpiry.value;
        getstr += "&isexpiry=" + document.forms[0].isexpiry.checked;
        getstr += "&tempfname=" + document.forms[0].tempfname.value;
        getstr += "&templocalFile=" + document.forms[0].templocalFile.value;
        getstr += "&approvedbyId=" + approvedbyId;
        getstr += "&approvedby=" + approvedby;
        httploc.open("POST", url, true);
        httploc.onreadystatechange = function ()
        {
            if (httploc.readyState == 4)
            {
                if (httploc.status == 200)
                {
                    var response = httploc.responseText;
                    document.forms[0].coursenameId.value = "-1";
                    document.forms[0].dateofissue.value = "";
                    document.forms[0].dateofexpiry.value = "";
                    document.forms[0].isexpiry.checked = false;
                    document.forms[0].tempfname.value = "";
                    document.forms[0].templocalFile.value = "";
                    document.forms[0].approvedbyId.value = "-1";
                    document.getElementById("dateofexpiry").disabled = false;
                    document.getElementById("upload_link_3").className = "attache_btn uploaded_img1";
                    var arr = new Array();
                    arr = trim(response).split("#@#@#");
                    document.getElementById("certtbody").innerHTML = '';
                    document.getElementById("certtbody").innerHTML = trim(arr[0]);
                    document.getElementById("certmobdiv").innerHTML = '';
                    document.getElementById("certmobdiv").innerHTML = trim(arr[1]);
                    document.forms[0].certCount.value = trim(arr[2]);
                }
            }
        };
        httploc.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
        httploc.setRequestHeader("Content-length", getstr.length);
        httploc.setRequestHeader("Connection", "close");
        httploc.send(getstr);
    }
}

function getEditCert(courseId)
{
    var url = "../ajax/candidateregistration/getModify.jsp";
    var httploc = getHTTPObject();
    var getstr = "editcert=edit";
    getstr += "&coursenameId=" + courseId;
    httploc.open("POST", url, true);
    httploc.onreadystatechange = function ()
    {
        if (httploc.readyState == 4)
        {
            if (httploc.status == 200)
            {
                var response = httploc.responseText;
                var arr = new Array();
                var newarr = new Array();
                arr = trim(response).split("#@#@#");

                document.getElementById("certtbody").innerHTML = '';
                document.getElementById("certtbody").innerHTML = trim(arr[0]);
                document.getElementById("certmobdiv").innerHTML = '';
                document.getElementById("certmobdiv").innerHTML = trim(arr[1]);
                newarr = trim(arr[2]).split("#@#");
                if (trim(newarr[0]) == 1) {
                    document.forms[0].isexpiry.checked = true;
                    document.getElementById("dateofexpiry").disabled = true;
                } else {
                    document.forms[0].isexpiry.checked = false;
                    document.getElementById("dateofexpiry").disabled = false;
                }
                document.forms[0].coursenameId.value = trim(newarr[1]);
                document.forms[0].dateofissue.value = trim(newarr[3]);
                document.forms[0].dateofexpiry.value = trim(newarr[4]);
                document.forms[0].approvedbyId.value = trim(newarr[5]);
                document.forms[0].tempfname.value = trim(newarr[7]);
                document.forms[0].templocalFile.value = trim(newarr[8]);
//                document.getElementById("upload_link_3").className = "attache_btn uploaded_img1";
                document.forms[0].certCount.value = trim(arr[3]);
            }
        }
    };
    httploc.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    httploc.setRequestHeader("Content-length", getstr.length);
    httploc.setRequestHeader("Connection", "close");
    httploc.send(getstr);
}

function getRemoveCert(courseId)
{
    var url = "../ajax/candidateregistration/getRemove.jsp";
    var httploc = getHTTPObject();
    var getstr = "removecert=remove";
    getstr += "&coursenameId=" + courseId;
    httploc.open("POST", url, true);
    httploc.onreadystatechange = function ()
    {
        if (httploc.readyState == 4)
        {
            if (httploc.status == 200)
            {
                var response = httploc.responseText;
                var arr = new Array();
                arr = trim(response).split("#@#@#");
                document.getElementById("certtbody").innerHTML = '';
                document.getElementById("certtbody").innerHTML = trim(arr[0]);
                document.getElementById("certmobdiv").innerHTML = '';
                document.getElementById("certmobdiv").innerHTML = trim(arr[1]);
                document.forms[0].certCount.value = trim(arr[2]);
            }
        }
    };
    httploc.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    httploc.setRequestHeader("Content-length", getstr.length);
    httploc.setRequestHeader("Connection", "close");
    httploc.send(getstr);
}

function getCloseRegistration()
{
    var url = "../ajax/candidateregistration/regsession.jsp";
    var httploc = getHTTPObject();
    var email = "";
    if (document.forms[0].emailId)
        email = document.forms[0].emailId.value;
    var getstr = "sessionActive=" + email;
    httploc.open("POST", url, true);
    httploc.onreadystatechange = function ()
    {
        if (httploc.readyState == 4)
        {
            if (httploc.status == 200)
            {
                var response = httploc.responseText;
                if (response.trim() == "success") {
                    window.close();
                    location.reload();

                }
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