function compareDateTime(v1, v2)
{
    var to1 = new Array;
    to1 = v1.split(" ");
    var value1 = to1[0];
    var time1 = to1[1] + ":00";

    var to2 = new Array;
    to2 = v2.split(" ");
    var value2 = to2[0];
    var time2 = to2[1] + ":00";
    if (value1 != "" && value2 != "")
    {
        var firstIndex1 = value1.indexOf("-");
        var lastIndex1 = value1.lastIndexOf("-");
        day1 = value1.substring(0, firstIndex1);
        month1 = eval(getValueByname(value1.substring(firstIndex1 + 1, lastIndex1)));
        year1 = value1.substring(lastIndex1 + 1, value1.length);
        var firstIndexTime1 = time1.indexOf(":");
        var lastIndexTime1 = time1.lastIndexOf(":");
        hour1 = time1.substring(0, firstIndexTime1);
        minute1 = time1.substring(firstIndexTime1 + 1, lastIndexTime1);
        second1 = time1.substring(lastIndexTime1 + 1, time1.length);

        var firstIndex2 = value2.indexOf("-");
        var lastIndex2 = value2.lastIndexOf("-");
        day2 = value2.substring(0, firstIndex2);
        month2 = eval(getValueByname(value2.substring(firstIndex2 + 1, lastIndex2)));
        year2 = value2.substring(lastIndex2 + 1, value2.length)

        var firstIndexTime2 = time2.indexOf(":");
        var lastIndexTime2 = time2.lastIndexOf(":");
        hour2 = time2.substring(0, firstIndexTime2);
        minute2 = time2.substring(firstIndexTime2 + 1, lastIndexTime2);
        second2 = time2.substring(lastIndexTime2 + 1, time2.length);

        var startdate = new Date(year1 - 0, month1 - 1, day1 - 0, hour1 - 0, minute1 - 0, 0, 0);
        var enddate = new Date(year2 - 0, month2 - 1, day2 - 0, hour2 - 0, minute2 - 0, 0, 0);
        if (startdate < enddate)
        {
            return true;
        } else
        {
            return false;
        }
    }
    return true;
}

function getHTTPObject()
{
    var xmlhttp;
    if (window.XMLHttpRequest)
    {
        xmlhttp = new XMLHttpRequest();
    } else if (window.ActiveXObject)
    {
        xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
    }
    return xmlhttp;
}

function NewWindow(mypage, myname, w, h, scroll, pos)
{
    if (pos === "center")
    {
        LeftPosition = (screen.width) ? (screen.width - w) / 2 : 100;
        TopPosition = (screen.height) ? (screen.height - h) / 2 : 100;
    }
    settings = 'height=' + h + ',width=' + w + ',top=' + TopPosition + ',left=' + LeftPosition + ',scrollbars=' + scroll + ',resizable=no';
    win = window.open(mypage, myname, settings);
    if (win.focus) {
        win.focus();
    }
}

function getValueByname(n)
{
    if (n === "Jan")
        return "01";
    if (n === "Feb")
        return "02";
    if (n === "Mar")
        return "03";
    if (n === "Apr")
        return "04";
    if (n === "May")
        return "05";
    if (n === "Jun")
        return "06";
    if (n === "Jul")
        return "07";
    if (n === "Aug")
        return "08";
    if (n === "Sep")
        return "09";
    if (n === "Oct")
        return "10";
    if (n === "Nov")
        return "11";
    if (n === "Dec")
        return "12";
}

var dtCh = "-";
var minYear = 1900;
var maxYear = 2100;
function isInteger(s)
{
    var i;
    for (i = 0; i < s.length; i++)
    {
        // Check that current character is number.
        var c = s.charAt(i);
        if (((c < "0") || (c > "9")))
            return false;
    }
    // All characters are numbers.
    return true;
}

function stripCharsInBag(s, bag)
{
    var i;
    var returnString = "";
    // Search through string's characters one by one.
    // If character is not in bag, append to returnString.
    for (i = 0; i < s.length; i++)
    {
        var c = s.charAt(i);
        if (bag.indexOf(c) === -1)
            returnString += c;
    }
    return returnString;
}
function daysInFebruary(year)
{
    // February has 29 days in any year evenly divisible by four,
    // EXCEPT for centurial years which are not also divisible by 400.
    return (((year % 4 === 0) && ((!(year % 100 === 0)) || (year % 400 === 0))) ? 29 : 28);
}
function DaysArray(n)
{
    for (var i = 1; i <= n; i++)
    {
        this[i] = 31;
        if (i === 4 || i === 6 || i === 9 || i === 11) {
            this[i] = 30;
        }
        if (i === 2) {
            this[i] = 29;
        }
    }
    return this;
}

function disablePaste(id)
{
    var input = document.getElementById(id);
    if (input)
    {
        input.onpaste = function (e) {
            e.preventDefault();
        };
    }
}

function DisableRightClick(event)
{
    if (event.button === 2)
    {
        Swal.fire("No Right Click Allowed.");
    }
}

function isDate(dtStr)
{
    var daysInMonth = DaysArray(12);
    var pos1 = dtStr.indexOf(dtCh);
    var pos2 = dtStr.indexOf(dtCh, pos1 + 1);
    var strDay = dtStr.substring(0, pos1);
    var strMonth = dtStr.substring(pos1 + 1, pos2);
    var strYear = dtStr.substring(pos2 + 1);
    strYr = strYear;
    if (strDay.charAt(0) === "0" && strDay.length > 1)
        strDay = strDay.substring(1);
    if (strMonth.charAt(0) === "0" && strMonth.length > 1)
        strMonth = strMonth.substring(1);
    for (var i = 1; i <= 3; i++)
    {
        if (strYr.charAt(0) === "0" && strYr.length > 1)
            strYr = strYr.substring(1);
    }
    month = parseInt(strMonth);
    day = parseInt(strDay);
    year = parseInt(strYr);
    if (pos1 === -1 || pos2 === -1)
    {
        Swal.fire("The date format should be : dd-mm-yyyy");
        return false;
    }
    if (strMonth.length < 1 || month < 1 || month > 12)
    {
        Swal.fire("Please enter a valid month");
        return false;
    }
    if (strDay.length < 1 || day < 1 || day > 31 || (month === 2 && day > daysInFebruary(year)) || day > daysInMonth[month])
    {
        Swal.fire("Please enter a valid day");
        return false;
    }
    if (strYear.length !== 4 || year === 0 || year < minYear || year > maxYear)
    {
        Swal.fire("Please enter a valid 4 digit year between " + minYear + " and " + maxYear);
        return false;
    }
    if (dtStr.indexOf(dtCh, pos2 + 1) !== -1 || isInteger(stripCharsInBag(dtStr, dtCh)) === false)
    {
        Swal.fire("Please enter a valid date");
        return false;
    }
    return true;
}

function checkTime(val)
{
    re = /^(\d{1,2}):(\d{2}):(\d{2})?$/;
    if (val.match(re))
    {
        var hh1 = new Array;
        hh1 = val.split(":");
        var hour = hh1[0];
        var minute = hh1[1];
        var second = hh1[2];

        if ((hour > 23) || (hour < 0))
        {
            Swal.fire("Please enter a valid hour between 0 to 23.");
            return false;
        }
        if ((minute > 59) || (minute < 0))
        {
            Swal.fire("Please enter a valid minute between 0 to 59.");
            return false;
        }
        if ((second > 59) || (second < 0))
        {
            Swal.fire("Please enter a valid second between 0 to 59.");
            return false;
        }
        return true;
    } else
    {
        Swal.fire("The time format should be : HH:mm:ss");
        return false;
    }
    return true;
}

function checkPincode()
{
    var pin_code = document.getElementById("pincode");
    var pi = /^\d{6}$/;
    if (!pi.test(pin_code.value))
    {
        Swal.fire({
            title: "Pin code should be 6 digits.",
            didClose: () => {
                pin_code.focus();
            }
        })
        return false;
    }
    return true;
}

function isDateTime(dtStr)
{
    var pos1 = dtStr.indexOf(" ");
    var pos2 = dtStr.indexOf(" ", pos1 + 1);
    var val1 = dtStr.substring(0, pos1);
    var val2 = dtStr.substring(pos1 + 1, pos2);
    if (isDate(val1) === false || checkTime(val2) === false)
    {
        return false;
    }
    return true;
}

function checkEmailAddress(field)
{
    if (field.value.indexOf('@') === 0)
    {
        field.focus();
        field.select();
        return false;
    } else
    {
        var goodEmail = field.value.match(/^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/);
        if (goodEmail)
        {
            if (field.value.indexOf('@-') != -1)
            {
                field.focus();
                field.select();
                return false;
            }
            return true;
        } else
        {
            field.focus();
            field.select();
            return false;
        }
    }
}

function checkEmailAddressVal(val)
{
    if (val.indexOf('@') === 0)
    {
        return false;
    } 
    else
    {
        var goodEmail = val.match(/^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/);
        if (goodEmail)
        {
            return true;
        } else
        {
            return false;
        }
    }
}

function morethanoneEmailAddr(field)
{
    if (checkComma(field.value))
    {
        return true;
    }
    if (checkAtTheRate(field.value))
    {
        return true;
    }
    return false;
}

function checkComma(fieldValue)
{
    var count = 0;
    for (i = 0; i < fieldValue.length; i++)
    {
        if (fieldValue.charAt(i) === ",")
            count++;
    }
    if (count >= 1)
    {
        return true;
    }
    return false;
}

function checkAtTheRate(fieldValue)
{
    var count = 0;
    for (i = 0; i < fieldValue.length; i++)
    {
        if (fieldValue.charAt(i) === "@")
            count++;
    }
    if (count > 1)
    {
        return true;
    }
    return false;
}

function checkContact(s)
{
    for (i = 0; i < s.length; i++)
    {
        var c = s.charAt(i);
        if (!((c >= "0") && (c <= "9") || (c === " ") || (c === "+") || (c === "-") || (c === "/")))
            return false;
    }
    if (!(s.length >= 6 && s.length <= 40))
    {
        return false;
    }
    return true;
}

function allowTelephone(e)
{
    var c = (e.which) ? e.which : event.keyCode

    if (!((c >= 48) && (c <= 57) || (c === 32) || (c === 43) || (c === 45) || (c === 47)))
    {
        return false;
    }
    return true;
}


function trim(stringToTrim)
{
    return stringToTrim.replace(/^\s+|\s+$/g, "");
}

function comparisionTest(value1, value2)
{
    if (value1 != "" && value2 != "")
    {
        var firstIndex1 = value1.indexOf("-");
        var lastIndex1 = value1.lastIndexOf("-");
        day1 = value1.substring(0, firstIndex1);
        month1 = eval(getValueByname(value1.substring(firstIndex1 + 1, lastIndex1)));
        year1 = value1.substring(lastIndex1 + 1, value1.length);

        var firstIndex2 = value2.indexOf("-");
        var lastIndex2 = value2.lastIndexOf("-");
        day2 = value2.substring(0, firstIndex2);
        month2 = eval(getValueByname(value2.substring(firstIndex2 + 1, lastIndex2)));
        year2 = value2.substring(lastIndex2 + 1, value2.length)

        var startdate = new Date(year1 - 0, month1 - 1, day1 - 0);
        var enddate = new Date(year2 - 0, month2 - 1, day2 - 0);

        if (startdate < enddate)
        {
            return true;
        } else
        {
            return false;
        }
    }
    return true;
}

function comparision(value1, value2)
{
    if (value1 != "" && value2 != "")
    {
        var firstIndex1 = value1.indexOf("-");
        var lastIndex1 = value1.lastIndexOf("-");
        day1 = value1.substring(0, firstIndex1);
        month1 = eval(getValueByname(value1.substring(firstIndex1 + 1, lastIndex1)));
        year1 = value1.substring(lastIndex1 + 1, value1.length);

        var firstIndex2 = value2.indexOf("-");
        var lastIndex2 = value2.lastIndexOf("-");
        day2 = value2.substring(0, firstIndex2);
        month2 = eval(getValueByname(value2.substring(firstIndex2 + 1, lastIndex2)));
        year2 = value2.substring(lastIndex2 + 1, value2.length)

        var startdate = new Date(year1 - 0, month1 - 1, day1 - 0);
        var enddate = new Date(year2 - 0, month2 - 1, day2 - 0);

        if (startdate <= enddate)
        {
            return true;
        } else
        {
            return false;
        }
    }
    return true;
}

function isSingleDot(varNo)
{
    var i;
    var dots = 0;
    for (i = 0; i < varNo.length; i++)
    {
        if (varNo.charAt(i) === ".") {
            dots++;
        }
        if (dots > 1)
            return false;
    }
    return true;
}
function dateCharacter(e)
{
    var charCode = (e.which) ? e.which : event.keyCode
    if (charCode > 31 && (charCode <= 46 || charCode > 57) || charCode === 47)
    {
        if (charCode === 45)
            return true;
        else
            return false;
    }
    return true;
}
function allowPositiveNumber(e)
{
    var charCode = (e.which) ? e.which : event.keyCode
    if (charCode > 31 && (charCode < 46 || charCode > 57) || charCode === 47)
    {
        return false;
    }
    return true;
}
function allowLetters(e)
{
    var charCode = (e.which) ? e.which : event.keyCode
    if (charCode < 65 || (charCode > 90 &&  charCode < 97) || charCode > 122 )
    {
        return false;
    }
    return true;
}
function allowPositiveNumber1(e)
{
    var charCode = (e.which) ? e.which : event.keyCode
    if (charCode > 31 && (charCode <= 46 || charCode > 57) || charCode === 47)
    {
        return false;
    }
    return true;
}

function checkDateSearch(sDate, eDate)
{
    sval = (sDate.value != "DD-MM-YYYY" ? sDate.value : '');
    eval = (eDate.value != "DD-MM-YYYY" ? eDate.value : '');
    if (sval != "")
    {
        if (isDate(sval) == false)
        {
            sDate.focus();
            return false;
        }
    }
    if (eval != "")
    {
        if (isDate(eval) == false)
        {
            eDate.focus();
            return false;
        }
    }
    if (comparision(sval, eval) == false)
    {
        sDate.focus();
        return false;
    }
    return true;
}


function checkmobile(s)
{
    for (var i = 0; i < s.length; i++)
    {
        var c = s.charAt(i);
        if (!((c >= "0") && (c <= "9") || (c === "+")))
            return false;
    }
    return true;
}

function openCPForm()
{
    document.getElementById('passworddetailId').style.display = '';
}

function closeCPDiv()
{
    document.getElementById('passworddetailId').style.display = 'none';
}

function checkPassword()
{
    if (trim(document.forms[0].oldPassword.value) === '')
    {
        Swal.fire({
            title: "Please enter old password.",
            didClose: () => {
                document.forms[0].oldPassword.focus();
            }
        })
        return false;
    }
    if (trim(document.forms[0].newPassword.value) === '')
    {
        Swal.fire({
            title: "Please enter new password.",
            didClose: () => {
                document.forms[0].newPassword.focus();
            }
        })
        return false;
    }
    if (isValidPassword(document.forms[0].newPassword) == false)
    {
        return false;
    }
    if (trim(document.forms[0].topconfirmPassword.value) === '')
    {
        Swal.fire({
            title: "Please enter confirm password.",
            didClose: () => {
                document.forms[0].topconfirmPassword.focus();
            }
        })
        return false;
    }
    if (trim(document.forms[0].oldPassword.value) === document.forms[0].newPassword.value)
    {
        Swal.fire({
            title: "Old password and new password can not be same.",
            didClose: () => {
                document.forms[0].newPassword.focus();
            }
        })
        return false;
    }
    if (trim(document.forms[0].newPassword.value) != document.forms[0].topconfirmPassword.value)
    {
        Swal.fire({
            title: "New password and confirm password should be same.",
            didClose: () => {
                document.forms[0].newPassword.focus();
            }
        })
        return false;
    }
    return true;
}
function submitPassword()
{
    if (document.forms[0].getElementsByTagName("input"))
    {
        var inputElements = document.forms[0].getElementsByTagName("input");
        for (i = 0; i < inputElements.length; i++)
        {
            if (inputElements[i].type === "text" || inputElements[i].type === "password")
            {
                inputElements[i].value = trim(inputElements[i].value);
            }
        }
    }
    if (checkPassword())
    {
        var url_password = "../ajax/changepassword.jsp";
        var httploc = getHTTPObject();
        var oldPassword = document.forms[0].oldPassword.value;
        var newPassword = document.forms[0].newPassword.value;
        var topconfirmPassword = document.forms[0].topconfirmPassword.value;
        var getstr = "oldPassword=" + oldPassword + "&";
        getstr += "newPassword=" + newPassword + "&";
        getstr += "topconfirmPassword=" + topconfirmPassword;
        httploc.open("POST", url_password, true);
        httploc.onreadystatechange = function ()
        {
            if (httploc.readyState === 4)
            {
                if (httploc.status === 200)
                {
                    var response = httploc.responseText;
                    var val = response;
                    if (trim(val) === 'N')
                    {
                        Swal.fire({
                            title: "Your old password is incorrect.",
                            didClose: () => {
                                document.forms[0].oldPassword.focus();
                            }
                        })
                    } else
                    {
                        document.getElementById('passworddetailId').style.display = 'none';
                        document.forms[0].oldPassword.value = "";
                        document.forms[0].newPassword.value = "";
                        document.forms[0].topconfirmPassword.value = "";
                        Swal.fire("Your password has been changed successfully.");
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


function isValidPassword(val)
{
    if ((val.value.length) <= 5)
    {
        Swal.fire({
            title: "Strong passwords must be at min 6 characters to max 15 characters long.",
            didClose: () => {
            }
        })
        return false;
    }
    if ((val.value.length) > 15)
    {
        Swal.fire({
            title: "Strong passwords must be at min 6 characters to max 15 characters long.",
            didClose: () => {
            }
        })
        return false;
    }
    return true;
}

function alpha(field)
{
    var re = /^[a-z ]+$/i;
    if (!field.value.match(re))
    {
        Swal.fire({
            title: "Special characters and numeric values are not allowed, please check it.",
            didClose: () => {
                field.focus();
            }
        })
        return false;
    }
    return true;
}

function comparisionDueDate(value1, value2)
{
    if (value1 != "" && value2 != "")
    {
        var firstIndex1 = value1.indexOf("-");
        var lastIndex1 = value1.lastIndexOf("-");
        day1 = value1.substring(0, firstIndex1);
        month1 = value1.substring(firstIndex1 + 1, lastIndex1);
        year1 = value1.substring(lastIndex1 + 1, value1.length);

        var firstIndex2 = value2.indexOf("-");
        var lastIndex2 = value2.lastIndexOf("-");
        day2 = value2.substring(0, firstIndex2);
        month2 = value2.substring(firstIndex2 + 1, lastIndex2);
        year2 = value2.substring(lastIndex2 + 1, value2.length)

        var startdate = new Date(year1 - 0, month1 - 1, day1 - 0);
        var enddate = new Date(year2 - 0, month2 - 1, day2 - 0);
        if (startdate <= enddate)
        {
            return true;
        } else
        {
            return false;
        }
    }
    return true;
}

function checkPAN(val)
{
    if (val.match(/[A-Za-z]{5}\d{4}[A-Za-z]{1}/))
    {
        return true;
    } else
    {
        return false;
    }
}

function myFunction(id)
{
    var popup = document.getElementById('myPopup' + id);
    popup.classList.toggle('show');
}

function allowMobile(e)
{
    var charCode = (e.which) ? e.which : event.keyCode;
    if (((charCode > 31 && (charCode < 46 || charCode > 57)) || charCode === 47) && charCode !== 43)
    {
        return false;
    }
    return true;
}

function printPage(id)
{
    var LeftPosition = (screen.width) ? (screen.width - 820) / 2 : 100;
    var TopPosition = (screen.height) ? (screen.height - 650) / 2 : 100;
    var settings = 'height=650, width=820, top=' + TopPosition + ', left=' + LeftPosition + ', scrollbars=yes,resizable=no';
    var content = "";
    if (document.getElementById(id))
        content = document.getElementById(id).innerHTML;
    var html = ("<!DOCTYPE html><html lang='en'><head><meta charset='utf-8' />");
    html += ("<link href='/jxp/assets/css/rwd-table.min.css' rel='stylesheet' type='text/css' />");
    html += ("<link href='/jxp/assets/css/bootstrap.min.css' rel='stylesheet' type='text/css' />");
    html += ("<link href='/jxp/assets/css/icons.min.css' rel='stylesheet' type='text/css' />");
    html += ("<link href='/assets/css/app.min.css' rel='stylesheet' type='text/css' />");
    html += ("<link href='/jxp/assets/css/style.css' rel='stylesheet' type='text/css' />");
    html += ("</head><body id='mainContainer' onload='javascript: window.print();'>");
    html += ("<div class='main-content' id='main-content'><div class='page-content' style='padding-top: 50px;'>" + content + "</div></div></body></html>");

    var uniqueName = new Date();
    var windowName = 'Print' + uniqueName.getTime();

    var printWin = window.open('about:blank', windowName, settings);
    printWin.document.write(html);
    printWin.document.close();
    printWin.focus();
}

function openinnewtab()
{
    var url = window.location;
    window.open(url, '_blank');
}

function backtoold()
{
    document.forms[0].target = "";
    document.forms[0].action = "/jxp/indexf.jsp";
    document.forms[0].submit();
}

function autoheader()
{
    var val = document.getElementById("searchheader").value;
    document.getElementById("headersdiv").style.display = "none";
    if(val != '' && val.length > 1)
    {
        var url = "../ajax/search/searchlist.jsp";
        document.getElementById("headersdiv").style.display="";
        var httpinst = getHTTPObject();
        var getstr = "val="+val;
        httpinst.open("POST", url, true);
        httpinst.onreadystatechange = function()
        {
            if (httpinst.readyState == 4)
            {
                if(httpinst.status == 200)
                {
                    var response = httpinst.responseText;
                    document.getElementById("headersdiv").innerHTML = '';
                    document.getElementById("headersdiv").innerHTML = response; 
                    $('#cul').find('li').first().attr( 'tabIndex', -1);
                    $('#cul').find('li').first().focus();
//                    $('#cul').find('li').find('a').first().focus();
                    document.forms[0].searchheader.focus();
//                    var objDiv = document.getElementById("your_div");
//                    objDiv.scrollTop = objDiv.scrollHeight;
                }
            }
        };
        httpinst.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
        httpinst.send(getstr);
    }
}

function setCandidateNameHeader(candidateId, name)
{  
    document.getElementById("searchheader").value = name;
    document.getElementById("headersdiv").innerHTML = '';
    document.getElementById("headersdiv").style.display="none"; 
    gotoaction(candidateId);
}

function clearheader()
{
    document.getElementById("searchheader").value = "";
    document.getElementById("headersdiv").style.display="none";
}

function showsearch()
{
    document.getElementById("headsearch").style.display = "";        
}
    
function handleKeyHeader(e, candidateId, name)
{
    var key=e.keyCode || e.which;
    if (key===13)
    {
        e.preventDefault();
        document.getElementById("searchheader").value = name;
        document.getElementById("headersdiv").style.display="none";
        gotoaction(candidateId);
    }
}

function opensearchbox()
{
    if(document.getElementById("headersearchdiv").style.display == "none")
        document.getElementById("headersearchdiv").style.display = "";
    else
        document.getElementById("headersearchdiv").style.display = "none";
}

function gotoaction(candidateId)
{
    var url = "/jxp/ajax/search/checkflag.jsp";
    var httploc = getHTTPObject();
    var getstr = "candidateId="+candidateId;
    httploc.open("POST", url, true);
    httploc.onreadystatechange = function()
    {
        if (httploc.readyState == 4)
        {
            if(httploc.status == 200)
            {
                var response = httploc.responseText;
                response = trim(response);  
                if(response == 'Yes')
                {                    
                    document.forms[0].doViewHeader.value = "yes";
                    document.forms[0].candidateIdHeader.value = candidateId;
                    document.forms[0].action = "/jxp/talentpool/TalentpoolAction.do?doViewHeader=yes";
                    document.forms[0].submit();
                }
                else
                {
                    document.forms[0].doViewHeader.value = "yes";
                    document.forms[0].candidateIdHeader.value = candidateId;
                    document.forms[0].action = "/jxp/candidate/CandidateAction.do?doViewHeader=yes";
                    document.forms[0].submit();
                }
            }
        }
    }
    httploc.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    httploc.setRequestHeader("Content-length", getstr.length);
    httploc.setRequestHeader("Connection", "close");
    httploc.send(getstr);
}
