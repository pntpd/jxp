function resetFilter()
{
    document.crewrotationForm.search.value = "";
    document.crewrotationForm.clientIdIndex.value = "-1";
    document.crewrotationForm.assetIdIndex.value = "-1";
    document.crewrotationForm.countryId.value = "-1";
    searchFormAjax('s', '-1');
    setAssetDDL();
}

function resetclientFilter()
{
    document.crewrotationForm.search.value = "";
    document.crewrotationForm.assetIdIndex.value = "-1";
    searchClientFormAjax('s', '-1');
}

function view(clientId, clientassetId)
{
    if (document.crewrotationForm.doView)
        document.crewrotationForm.doView.value = "yes";
    document.crewrotationForm.clientId.value = clientId;
    document.crewrotationForm.clientassetId.value = clientassetId;
    document.forms[0].target = "";
    document.crewrotationForm.action = "../crewrotation/CrewrotationAction.do";
    document.crewrotationForm.submit();
}

function searchview()
{
    if (document.crewrotationForm.doView)
        document.crewrotationForm.doView.value = "yes";
    document.forms[0].target = "";
    document.crewrotationForm.action = "../crewrotation/CrewrotationAction.do";
    document.crewrotationForm.submit();
}

function viewActivity(crewrotationId, clientId, clientassetId)
{
    if (document.crewrotationForm.doSummary)
        document.crewrotationForm.doSummary.value = "yes";
    document.crewrotationForm.clientId.value = clientId;
    document.crewrotationForm.clientassetId.value = clientassetId;
    document.crewrotationForm.crewrotationId.value = crewrotationId;
    document.forms[0].target = "";
    document.crewrotationForm.action = "../crewrotation/CrewrotationAction.do";
    document.crewrotationForm.submit();
}

function searchActivity()
{
    if (document.crewrotationForm.doSummary)
        document.crewrotationForm.doSummary.value = "yes";
    document.forms[0].target = "";
    document.crewrotationForm.action = "../crewrotation/CrewrotationAction.do";
    document.crewrotationForm.submit();
}

function getActivityModelview(crewrotationId, status, cractivityId)
{
    var url = "../ajax/crewrotation/getactivitymodaldetailsview.jsp";
    var httploc = getHTTPObject();
    var getstr = "";
    getstr += "crewrotationId=" + crewrotationId;
    getstr += "&status=" + status;
    getstr += "&cractivityId=" + cractivityId;
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
                document.getElementById('activitymodalview').innerHTML = '';
                document.getElementById('activitymodalview').innerHTML = trim(v1);

                jQuery(document).ready(function () {
                    $(".kt-selectpicker").selectpicker();
                    $(".wesl_dt").datepicker({
                        todayHighlight: !0,
                        format: "dd-M-yyyy",
                        autoclose: "true",
                        orientation: "auto"
                    });
                });

            }
        }
    };
    httploc.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    httploc.setRequestHeader("Content-length", getstr.length);
    httploc.setRequestHeader("Connection", "close");
    httploc.send(getstr);
    document.getElementById('activitymodalview').innerHTML = "<div><img src='../assets/images/loading.gif' align='absmiddle'/>Please wait. Loading...</div>";
}

function getActivityModeledit(crewrotationId, status, cractivityId)
{
    var url = "../ajax/crewrotation/getactivitymodaldetailsedit.jsp";
    var httploc = getHTTPObject();
    var getstr = "";
    getstr += "crewrotationId=" + crewrotationId;
    getstr += "&status=" + status;
    getstr += "&cractivityId=" + cractivityId;
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
                document.getElementById('activitymodaledit').innerHTML = '';
                document.getElementById('activitymodaledit').innerHTML = trim(v1);

                jQuery(document).ready(function () {
                    $(".kt-selectpicker").selectpicker();
                    $(".wesl_dt").datepicker({
                        todayHighlight: !0,
                        format: "dd-M-yyyy",
                        autoclose: "true",
                        orientation: "auto"
                    });
                });

            }
        }
    };
    httploc.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    httploc.setRequestHeader("Content-length", getstr.length);
    httploc.setRequestHeader("Connection", "close");
    httploc.send(getstr);
    document.getElementById('activitymodalview').innerHTML = "<div><img src='../assets/images/loading.gif' align='absmiddle'/>Please wait. Loading...</div>";
}

function getActivityModel(crewrotationId, status, clientassetId)
{
    var url = "../ajax/crewrotation/getactivitymodaldetails.jsp";
    var httploc = getHTTPObject();
    var getstr = "";
    getstr += "crewrotationId=" + crewrotationId;
    getstr += "&clientassetId=" + clientassetId;
    getstr += "&status=" + status;
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
                document.getElementById('activitymodal').innerHTML = '';
                document.getElementById('activitymodal').innerHTML = trim(v1);

                jQuery(document).ready(function () {
                    $(".kt-selectpicker").selectpicker();
                    $(".wesl_dt").datepicker({
                        todayHighlight: !0,
                        format: "dd-M-yyyy",
                        autoclose: "true",
                        orientation: "auto"
                    });
                });
                showhidetp();
            }
        }
    };
    httploc.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    httploc.setRequestHeader("Content-length", getstr.length);
    httploc.setRequestHeader("Connection", "close");
    httploc.send(getstr);
    document.getElementById('activitymodal').innerHTML = "<div><img src='../assets/images/loading.gif' align='absmiddle'/>Please wait. Loading...</div>";
}

function showhidetp()
{
    if (document.getElementById('activityId').value == "7")
    {
        document.getElementById('prom_position').style.display = "";
        document.getElementById('positionIdActivityDiv').style.display = "none";
    } else
    {
        document.getElementById('prom_position').style.display = "none";
        document.getElementById('positionIdActivityDiv').style.display = "";
    }
}

function checkActivity()
{
    if (document.forms[0].activityId.value == "")
    {
        Swal.fire({
            title: "Please select activity.",
            didClose: () => {
                document.forms[0].activityId.focus();
            }
        })
        return false;
    }
    if (document.forms[0].activityId.value == "7" && document.forms[0].positionId.value <= "0")
    {
        Swal.fire({
            title: "Please select position.",
            didClose: () => {
                document.forms[0].positionId.focus();
            }
        })
        return false;
    }
    if (document.forms[0].astartdate.value == "")
    {
        Swal.fire({
            title: "Please select fromdate.",
            didClose: () => {
                document.forms[0].astartdate.focus();
            }
        })
        return false;
    }
    if (document.forms[0].aenddate.value == "")
    {
        Swal.fire({
            title: "Please select todate.",
            didClose: () => {
                document.forms[0].aenddate.focus();
            }
        })
        return false;
    }
    if (comparision(document.forms[0].astartdate.value, document.forms[0].aenddate.value) == false)
    {
        Swal.fire({
            title: "Please check date.",
            didClose: () => {
                document.forms[0].aenddate.focus();
            }
        })
        return false;
    }
    if (trim(document.forms[0].remarks.value) == "")
    {
        Swal.fire({
            title: "Please enter remarks.",
            didClose: () => {
                document.forms[0].remarks.focus();
            }
        })
        return false;
    }
    return true;
}

function submitactivityForm(crewrotationId, status)
{
    document.crewrotationForm.crewrotationId.value = crewrotationId;
    document.crewrotationForm.status.value = status;
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
    if (checkActivity())
    {
        document.getElementById("saveactivitydiv").innerHTML = "<div><img src='../assets/images/loading.gif' align='absmiddle'/>Please wait. Loading...</div>";
        if (document.crewrotationForm.doSaveActivity)
            document.crewrotationForm.doSaveActivity.value = "yes";
        document.forms[0].target = "";
        document.crewrotationForm.action = "../crewrotation/CrewrotationAction.do";
        document.crewrotationForm.submit();
    }
}

function submitupdateactivityForm(crewrotationId, cractivityId)
{
    document.crewrotationForm.crewrotationId.value = crewrotationId;
    document.crewrotationForm.cractivityId.value = cractivityId;
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
    if (checkActivity())
    {
        document.getElementById("saveditactivitydiv").innerHTML = "<div><img src='../assets/images/loading.gif' align='absmiddle'/>Please wait. Loading...</div>";
        if (document.crewrotationForm.doSaveActivity)
            document.crewrotationForm.doSaveActivity.value = "yes";
        document.forms[0].target = "";
        document.crewrotationForm.action = "../crewrotation/CrewrotationAction.do";
        document.crewrotationForm.submit();
    }
}

function setSuggestedDate()
{
    if (document.forms[0].rdateId[1].checked || document.forms[0].rdateId[2].checked)
    {
        document.getElementById('dvPinNo').style.display = "";
        document.getElementById('reasonId').innerHTML = "Reasons";
        document.getElementById('dreasons').style.display = "none";
        document.getElementById('reasonDiv').style.display = "";
    } else
    {
        document.getElementById('dvPinNo').style.display = "none";
        document.getElementById('reasonId').innerHTML = "Remarks";
        document.getElementById('dreasons').style.display = "";
        document.getElementById('reasonDiv').style.display = "none";
    }
}

function showTextArea()
{
    if (document.getElementById('reasonsDDL').value == "Others")
    {
        document.getElementById('dreasons').style.display = "";
    } else
    {
        document.getElementById('dreasons').style.display = "none";
    }
}

function getsignoffModel(crewrotationId, noofdays, crewrota)
{
    var url = "../ajax/crewrotation/getsignoffmodaldetails.jsp";
    var httploc = getHTTPObject();
    var getstr = "";
    getstr += "crewrotationId=" + crewrotationId;
    getstr += "&noofdays=" + noofdays;
    getstr += "&crewrota=" + crewrota;
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
                document.getElementById('signoffmodal').innerHTML = '';
                document.getElementById('signoffmodal').innerHTML = trim(v1);

                jQuery(document).ready(function () {
                    $(".kt-selectpicker").selectpicker();
                    $(".wesl_dt").datepicker({
                        todayHighlight: !0,
                        format: "dd-M-yyyy",
                        autoclose: "true",
                        orientation: "auto"
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
    document.getElementById('signoffmodal').innerHTML = "<div><img src='../assets/images/loading.gif' align='absmiddle'/>Please wait. Loading...</div>";
}

function checksignoff()
{
    var crewrotationId = document.crewrotationForm.crewrotationId.value;
    var lstdate = document.getElementById("executeondate_" + crewrotationId).value;
    if (document.forms[0].edate.value == "")
    {
        Swal.fire({
            title: "Please select date.",
            didClose: () => {
                document.forms[0].edate.focus();
            }
        })
        return false;
    }
    if (document.forms[0].etime.value == "")
    {
        Swal.fire({
            title: "Please select time.",
            didClose: () => {
                document.forms[0].etime.focus();
            }
        })
        return false;
    }

    if (lstdate != "")
    {
        if (comparisionTest(lstdate, document.forms[0].edate.value) == false)
        {
            Swal.fire({
                title: "Please check date.",
                didClose: () => {
                    document.forms[0].edate.focus();
                }
            })
            return false;
        }
    }
    if (Number(document.forms[0].rdateId.value) == 2 || Number(document.forms[0].rdateId.value) == 3)
    {
        if (document.getElementById('reasonsDDL').value == "")
        {
            Swal.fire({
                title: "Please select reason.",
                didClose: () => {
                    document.forms[0].reasonsDDL.focus();
                }
            })
            return false;
        }
        if (document.getElementById('reasonsDDL').value == "Others")
        {
            if (trim(document.forms[0].reasons.value) == "")
            {
                Swal.fire({
                    title: "Please enter reason.",
                    didClose: () => {
                        document.forms[0].reasons.focus();
                    }
                })
                return false;
            }
        }
    }
//    if (Number(document.getElementById("hdnCrewRota").value) != 2)
//    {
//        if (document.forms[0].sdate.value == "")
//        {
//            Swal.fire({
//                title: "Please select date.",
//                didClose: () => {
//                    document.forms[0].sdate.focus();
//                }
//            })
//            return false;
//        }
//        if (document.forms[0].stime.value == "")
//        {
//            Swal.fire({
//                title: "Please select time.",
//                didClose: () => {
//                    document.forms[0].aenddate.focus();
//                }
//            })
//            return false;
//        }
//    }
    if (document.forms[0].rdateId.value == "3")
    {
        if (comparisionTest(document.forms[0].edate.value, document.forms[0].sdate.value) == false)
        {
            Swal.fire({
                title: "Please check date.",
                didClose: () => {
                    document.forms[0].sdate.focus();
                }
            })
            return false;
        }
    }
    if (document.forms[0].rdateId.value == "2")
    {
        if (comparisionTest(document.forms[0].sdate.value, document.forms[0].edate.value) == false)
        {
            Swal.fire({
                title: "Please check date.",
                didClose: () => {
                    document.forms[0].sdate.focus();
                }
            })
            return false;
        }
    }
    return true;
}

function submitsignoffForm(crewrotationId, noofdays)
{
    document.crewrotationForm.crewrotationId.value = crewrotationId;
    document.crewrotationForm.noofdays.value = noofdays;

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
    if (checksignoff())
    {
        document.getElementById("savesignoffdiv").innerHTML = "<div><img src='../assets/images/loading.gif' align='absmiddle'/>Please wait. Loading...</div>";
        if (document.crewrotationForm.doSaveSignoff)
            document.crewrotationForm.doSaveSignoff.value = "yes";
        document.forms[0].target = "";
        document.crewrotationForm.action = "../crewrotation/CrewrotationAction.do";
        document.crewrotationForm.submit();
    }
}

function getsignonModel(crewrotationId, noofdays, crewrota)
{
    if (crewrotationId == 0)
    {
        crewrotationId = document.crewrotationForm.crewrotationId.value;
        noofdays = document.crewrotationForm.noofdays.value;
    }
    var url = "../ajax/crewrotation/getsignonmodaldetails.jsp";
    var httploc = getHTTPObject();
    var getstr = "";
    getstr += "crewrotationId=" + crewrotationId;
    getstr += "&noofdays=" + noofdays;
    getstr += "&crewrota=" + crewrota;
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
                document.getElementById('signonmodal').innerHTML = '';
                document.getElementById('signonmodal').innerHTML = trim(v1);

                jQuery(document).ready(function () {
                    $(".kt-selectpicker").selectpicker();
                    $(".wesl_dt").datepicker({
                        todayHighlight: !0,
                        format: "dd-M-yyyy",
                        autoclose: "true",
                        orientation: "auto"
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
    document.getElementById('signonmodal').innerHTML = "<div><img src='../assets/images/loading.gif' align='absmiddle'/>Please wait. Loading...</div>";
}

function submitsignonForm(crewrotationId, noofdays)
{
    document.crewrotationForm.crewrotationId.value = crewrotationId;
    document.crewrotationForm.noofdays.value = noofdays;
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
    if (checksignoff())
    {
        document.getElementById("savesignondiv").innerHTML = "<div><img src='../assets/images/loading.gif' align='absmiddle'/>Please wait. Loading...</div>";
        if (document.crewrotationForm.doSaveSignon)
            document.crewrotationForm.doSaveSignon.value = "yes";
        document.forms[0].target = "";
        document.crewrotationForm.action = "../crewrotation/CrewrotationAction.do";
        document.crewrotationForm.submit();
    }
}

function getsignonoffModelview(crewrotationId, cractivityId, noofdays)
{
    var url = "../ajax/crewrotation/getsignonoffmodaldetails.jsp";
    var httploc = getHTTPObject();
    var getstr = "";
    getstr += "crewrotationId=" + crewrotationId;
    getstr += "&cractivityId=" + cractivityId;
    getstr += "&noofdays=" + noofdays;
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
                document.getElementById('signonoffmodalview').innerHTML = '';
                document.getElementById('signonoffmodalview').innerHTML = trim(v1);

                jQuery(document).ready(function () {
                    $(".kt-selectpicker").selectpicker();
                    $(".wesl_dt").datepicker({
                        todayHighlight: !0,
                        format: "dd-M-yyyy",
                        autoclose: "true",
                        orientation: "auto"
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
    document.getElementById('signonoffmodalview').innerHTML = "<div><img src='../assets/images/loading.gif' align='absmiddle'/>Please wait. Loading...</div>";
}

function callsignonoffedit(crewrotationId, cractivityId, noofdays)
{
    $('#sign_on_off_details_modalview').modal('hide');
    $('#edit_sign_on_off_details_modal').modal('show');
    getsignonoffModeledit(crewrotationId, cractivityId, noofdays);
}

function callactivityedit(crewrotationId, status, cractivityId)
{
    $('#valueModalview').modal('hide');
    $('#valueModalsedit').modal('show');
    getActivityModeledit(crewrotationId, status, cractivityId);
}

function showTextAreasignon()
{
    if (document.getElementById('reasonsDDL').value == "Others")
    {
        document.getElementById('div02_11').style.display = "";
        document.getElementById('div02_12').style.display = "none";
    } else if (document.getElementById('reasonsDDL1').value == "Others")
    {
        document.getElementById('div02_12').style.display = "";
        document.getElementById('div02_11').style.display = "none";
    } else
    {
        document.getElementById('div02_12').style.display = "none";
        document.getElementById('div02_11').style.display = "none";
    }
}

function showTextAreasignoff()
{
    if (document.getElementById('reasonsDDL2').value == "Others")
    {
        document.getElementById('div02_011').style.display = "";
        document.getElementById('div02_012').style.display = "none";
    } else if (document.getElementById('reasonsDDL3').value == "Others")
    {
        document.getElementById('div02_012').style.display = "";
        document.getElementById('div02_011').style.display = "none";
    } else
    {
        document.getElementById('div02_011').style.display = "none";
        document.getElementById('div02_012').style.display = "none";
    }
}

function getsignonoffModeledit(crewrotationId, cractivityId, noofdays)
{
    var url = "../ajax/crewrotation/getsignonoffmodaldetailsedit.jsp";
    var httploc = getHTTPObject();
    var getstr = "";
    getstr += "crewrotationId=" + crewrotationId;
    getstr += "&noofdays=" + noofdays;
    getstr += "&cractivityId=" + cractivityId;
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
                document.getElementById('signonoffmodaledit').innerHTML = '';
                document.getElementById('signonoffmodaledit').innerHTML = trim(v1);

                jQuery(document).ready(function () {
                    $(".kt-selectpicker").selectpicker();
                    $(".wesl_dt").datepicker({
                        todayHighlight: !0,
                        format: "dd-M-yyyy",
                        autoclose: "true",
                        orientation: "auto"
                    });
                });
                $(".timepicker-24").timepicker({
                    format: 'mm:mm',
                    autoclose: !0,
                    minuteStep: 5,
                    showSeconds: !1,
                    showMeridian: !1
                });

                $(document).ready(function () {
                    $("input[name$='rdateId']").click(function () {
                        var radio_value = $(this).val();
                        if (radio_value == '1') {
                            $("#div02_1").show("");
                            $("#div02_2").hide("");
                            $("#div02_3").hide("");
                            $("#reasonDiv1").hide("");
                            $("#reasonDiv").hide("");
                        } else if (radio_value == '2') {
                            $("#div02_1").hide("");
                            $("#div02_2").show("");
                            $("#div02_3").hide("");
                            $("#reasonDiv").show("");
                            $("#reasonDiv1").hide("");
                        } else if (radio_value == '3') {
                            $("#div02_1").hide("");
                            $("#div02_2").hide("");
                            $("#div02_3").show("");
                            $("#reasonDiv1").show("");
                            $("#reasonDiv").hide("");
                        }
                    });
                    $('[name="rdateId"]:checked').trigger('click');
                });

                $(document).ready(function () {
                    $("input[name$='rdateid1']").click(function () {
                        var radio_value = $(this).val();
                        if (radio_value == '1') {
                            $("#div02_01").show("");
                            $("#div02_02").hide("");
                            $("#div02_03").hide("");
                        } else if (radio_value == '2') {
                            $("#div02_01").hide("");
                            $("#div02_02").show("");
                            $("#div02_03").hide("");
                            $("#reasonDiv2").show("");
                            $("#reasonDiv3").hide("");
                        } else if (radio_value == '3') {
                            $("#div02_01").hide("");
                            $("#div02_02").hide("");
                            $("#div02_03").show("");
                            $("#reasonDiv3").show("");
                            $("#reasonDiv2").hide("");
                        }
                    });
                    $('[name="rdateid1"]:checked').trigger('click');
                });
            }
        }
    };
    httploc.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    httploc.setRequestHeader("Content-length", getstr.length);
    httploc.setRequestHeader("Connection", "close");
    httploc.send(getstr);
    document.getElementById('signonoffmodaledit').innerHTML = "<div><img src='../assets/images/loading.gif' align='absmiddle'/>Please wait. Loading...</div>";
}

function checksignonedit()
{
    if (document.forms[0].edate.value == "")
    {
        Swal.fire({
            title: "Please select date.",
            didClose: () => {
                document.forms[0].edate.focus();
            }
        })
        return false;
    }
    if (document.forms[0].etime.value == "")
    {
        Swal.fire({
            title: "Please select time.",
            didClose: () => {
                document.forms[0].etime.focus();
            }
        })
        return false;
    }
    if (document.forms[0].rdateId.value == "2")
    {
        if (document.forms[0].sdate.value == "")
        {
            Swal.fire({
                title: "Please select date.",
                didClose: () => {
                    document.forms[0].sdate.focus();
                }
            })
            return false;
        }
        if (document.forms[0].stime.value == "")
        {
            Swal.fire({
                title: "Please select time.",
                didClose: () => {
                    document.forms[0].stime.focus();
                }
            })
            return false;
        }
    }
    if (document.forms[0].rdateId.value == "3")
    {
        if (document.forms[0].sdate1.value == "")
        {
            Swal.fire({
                title: "Please select date.",
                didClose: () => {
                    document.forms[0].sdate1.focus();
                }
            })
            return false;
        }
        if (document.forms[0].stime1.value == "")
        {
            Swal.fire({
                title: "Please select time.",
                didClose: () => {
                    document.forms[0].stime1.focus();
                }
            })
            return false;
        }
    }
    if (document.forms[0].rdateId.value == "2")
    {
        if (comparisionTest(document.forms[0].sdate.value, document.forms[0].edate.value) == false)
        {
            Swal.fire({
                title: "Please check date.",
                didClose: () => {
                    document.forms[0].sdate.focus();
                }
            })
            return false;
        }
    }
    if (document.forms[0].rdateId.value == "3")
    {
        if (comparisionTest(document.forms[0].edate.value, document.forms[0].sdate1.value) == false)
        {
            Swal.fire({
                title: "Please check date.",
                didClose: () => {
                    document.forms[0].sdate1.focus();
                }
            })
            return false;
        }
    }
    return true;
}

function submitsignoneditForm(crewrotationId, noofdays, signonoffId, cractivityId)
{
    document.crewrotationForm.crewrotationId.value = crewrotationId;
    document.crewrotationForm.signonoffId.value = signonoffId;
    document.crewrotationForm.cractivityId.value = cractivityId;
    document.crewrotationForm.noofdays.value = noofdays;
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
    if (checksignonedit())
    {
        document.getElementById("savediv1signon").innerHTML = "<div><img src='../assets/images/loading.gif' align='absmiddle'/>Please wait. Loading...</div>";
        if (document.crewrotationForm.doSaveSignon)
            document.crewrotationForm.doSaveSignon.value = "yes";
        document.forms[0].target = "";
        document.crewrotationForm.action = "../crewrotation/CrewrotationAction.do";
        document.crewrotationForm.submit();
    }
}

function checksignoffedit()
{
    if (document.forms[0].edate1.value == "")
    {
        Swal.fire({
            title: "Please select date.",
            didClose: () => {
                document.forms[0].edate1.focus();
            }
        })
        return false;
    }
    if (document.forms[0].etime1.value == "")
    {
        Swal.fire({
            title: "Please select time.",
            didClose: () => {
                document.forms[0].etime1.focus();
            }
        })
        return false;
    }
    if (document.forms[0].rdateid1.value == "2")
    {
        if (document.forms[0].sdate2.value == "")
        {
            Swal.fire({
                title: "Please select date.",
                didClose: () => {
                    document.forms[0].sdate2.focus();
                }
            })
            return false;
        }
        if (document.forms[0].stime2.value == "")
        {
            Swal.fire({
                title: "Please select time.",
                didClose: () => {
                    document.forms[0].stime2.focus();
                }
            })
            return false;
        }
    }
    if (document.forms[0].rdateid1.value == "3")
    {
        if (document.forms[0].sdate3.value == "")
        {
            Swal.fire({
                title: "Please select date.",
                didClose: () => {
                    document.forms[0].sdate3.focus();
                }
            })
            return false;
        }
        if (document.forms[0].stime3.value == "")
        {
            Swal.fire({
                title: "Please select time.",
                didClose: () => {
                    document.forms[0].stime3.focus();
                }
            })
            return false;
        }
    }
    if (document.forms[0].rdateid1.value == "2")
    {
        if (comparisionTest(document.forms[0].sdate2.value, document.forms[0].edate1.value) == false)
        {
            Swal.fire({
                title: "Please check date.",
                didClose: () => {
                    document.forms[0].sdate2.focus();
                }
            })
            return false;
        }
    }
    if (document.forms[0].rdateid1.value == "3")
    {
        if (comparisionTest(document.forms[0].edate1.value, document.forms[0].sdate3.value) == false)
        {
            Swal.fire({
                title: "Please check date.",
                didClose: () => {
                    document.forms[0].sdate2.focus();
                }
            })
            return false;
        }
    }
    return true;
}

function submitsignoffeditForm(crewrotationId, noofdays, signonoffId, cractivityId)
{
    document.crewrotationForm.crewrotationId.value = crewrotationId;
    document.crewrotationForm.noofdays.value = noofdays;
    document.crewrotationForm.signonoffId.value = signonoffId;
    document.crewrotationForm.cractivityId.value = cractivityId;
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
    if (checksignoffedit())
    {
        document.getElementById("savediv1signoff").innerHTML = "<div><img src='../assets/images/loading.gif' align='absmiddle'/>Please wait. Loading...</div>";
        if (document.crewrotationForm.doSaveSignoff)
            document.crewrotationForm.doSaveSignoff.value = "yes";
        document.forms[0].target = "";
        document.crewrotationForm.action = "../crewrotation/CrewrotationAction.do";
        document.crewrotationForm.submit();
    }
}

function setval(id)
{
    document.getElementById("ddlhidden_" + id).value = document.getElementById("answertype_" + id).value;
}

function getadocumentmodal(crewrotationId, clientId, clientassetId, noofdays)
{
    document.crewrotationForm.crewrotationId.value = crewrotationId;
    document.crewrotationForm.noofdays.value = noofdays;
    var url = "../ajax/crewrotation/getadocumentmodaldetails.jsp";
    var httploc = getHTTPObject();
    var getstr = "";
    getstr += "crewrotationId=" + crewrotationId;
    getstr += "&clientId=" + clientId;
    getstr += "&clientassetId=" + clientassetId;
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
                document.getElementById('requireddocadd').innerHTML = '';
                document.getElementById('requireddocadd').innerHTML = trim(v1);

            }
        }
    };
    httploc.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    httploc.setRequestHeader("Content-length", getstr.length);
    httploc.setRequestHeader("Connection", "close");
    httploc.send(getstr);
    document.getElementById('requireddocadd').innerHTML = "<div><img src='../assets/images/loading.gif' align='absmiddle'/>Please wait. Loading...</div>";
}

function getvdocumentmodal(crewrotationId, clientId, clientassetId)
{
    var url = "../ajax/crewrotation/getvdocumentmodaldetails.jsp";
    var httploc = getHTTPObject();
    var getstr = "";
    getstr += "crewrotationId=" + crewrotationId;
    getstr += "&clientId=" + clientId;
    getstr += "&clientassetId=" + clientassetId;
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
                document.getElementById('requireddocview').innerHTML = '';
                document.getElementById('requireddocview').innerHTML = trim(v1);

            }
        }
    };
    httploc.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    httploc.setRequestHeader("Content-length", getstr.length);
    httploc.setRequestHeader("Connection", "close");
    httploc.send(getstr);
    document.getElementById('requireddocadd').innerHTML = "<div><img src='../assets/images/loading.gif' align='absmiddle'/>Please wait. Loading...</div>";
}

function submitrequireddocForm(crewrotationId)
{
    document.crewrotationForm.crewrotationId.value = crewrotationId;

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
    if (checkdoc())
    {
        document.getElementById("savedocdiv").innerHTML = "<div><img src='../assets/images/loading.gif' align='absmiddle'/>Please wait. Loading...</div>";
        if (document.crewrotationForm.doSavereqdoc)
            document.crewrotationForm.doSavereqdoc.value = "yes";
        document.forms[0].target = "";
        document.crewrotationForm.action = "../crewrotation/CrewrotationAction.do";
        document.crewrotationForm.submit();
    }
}

function gobackview()
{
    if (document.crewrotationForm.doView)
        document.crewrotationForm.doView.value = "yes";
    document.forms[0].target = "";
    document.crewrotationForm.action = "../crewrotation/CrewrotationAction.do";
    document.crewrotationForm.submit();
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
        var url = "../ajax/crewrotation/getinfo.jsp";
        var httploc = getHTTPObject();
        var getstr = "";
        var next_value = escape(document.crewrotationForm.nextValue.value);
        var search_value = escape(document.crewrotationForm.search.value);
        var assetIdIndex = escape(document.crewrotationForm.assetIdIndex.value);
        var clientIdIndex = escape(document.crewrotationForm.clientIdIndex.value);
        var countryId = escape(document.crewrotationForm.countryId.value);
        getstr += "nextValue=" + next_value;
        getstr += "&next=" + v;
        getstr += "&search=" + search_value;
        getstr += "&clientIdIndex=" + clientIdIndex;
        getstr += "&assetIdIndex=" + assetIdIndex;
        getstr += "&countryId=" + countryId;
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
    if (checkSearch())
    {
        var url = "../ajax/crewrotation/client_getinfo.jsp";
        var httploc = getHTTPObject();
        var getstr = "";
        var next_value = escape(document.crewrotationForm.nextValue.value);
        var search_value = escape(document.crewrotationForm.search.value);
        var pgvalue = escape(document.crewrotationForm.pgvalue.value);
        var assetIdIndex = escape(document.crewrotationForm.assetIdIndex.value);
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
    if (document.crewrotationForm.doView)
        document.crewrotationForm.doView.value = "no";
    if (document.crewrotationForm.doCancel)
        document.crewrotationForm.doCancel.value = "yes";
    if (document.crewrotationForm.doSave)
        document.crewrotationForm.doSave.value = "no";
    document.forms[0].target = "";
    document.crewrotationForm.action = "../crewrotation/CrewrotationAction.do";
    document.crewrotationForm.submit();
}

function sortForm(colid, updown)
{
    for (i = 1; i <= 3; i++)
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
    var url_sort = "../ajax/crewrotation/sort.jsp";
    var getstr = "";
    var nextValue = 0;
    if (document.crewrotationForm.nextValue)
        nextValue = document.crewrotationForm.nextValue.value;
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
    document.crewrotationForm.reset();
}

function setAssetDDL()
{
    var url = "../ajax/crewrotation/getasset.jsp";
    document.getElementById("assetIdIndex").value = '-1';
    var httploc = getHTTPObject();
    var getstr = "clientIdIndex=" + document.crewrotationForm.clientIdIndex.value + "&from=asset";
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
            }
        }
    };
    httploc.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    httploc.send(getstr);
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

function showdynamicId()
{
    var id = document.crewrotationForm.activityDropdown.value;
    if (document.crewrotationForm.dynamicId.value == "0")
    {
        document.getElementById("div1").style.display = "none";
        document.getElementById("div2").style.display = "none";
        document.getElementById("div3").style.display = "none";
        document.getElementById("div4").style.display = "none";
        document.crewrotationForm.crdate.value = "";
        document.crewrotationForm.activityDropdown.value = "0";
        document.crewrotationForm.searchcr.value = "";
        document.crewrotationForm.positionIdIndex.value = "-1";
    } else if (document.crewrotationForm.dynamicId.value == "1")
    {
        document.getElementById("div1").style.display = "";
        document.getElementById("div2").style.display = "none";
        document.getElementById("div3").style.display = "none";
        document.getElementById("div4").style.display = "none";
        document.crewrotationForm.activityDropdown.value = "0";
        document.crewrotationForm.searchcr.value = "";
        document.crewrotationForm.positionIdIndex.value = "-1";
    } else if (document.crewrotationForm.dynamicId.value == "2")
    {
        document.getElementById("div1").style.display = "";
        document.getElementById("div2").style.display = "none";
        document.getElementById("div3").style.display = "none";
        document.getElementById("div4").style.display = "none";
        document.crewrotationForm.activityDropdown.value = "0";
        document.crewrotationForm.searchcr.value = "";
        document.crewrotationForm.positionIdIndex.value = "-1";
    } else if (document.crewrotationForm.dynamicId.value == "3")
    {
        document.getElementById("div1").style.display = "none";
        document.getElementById("div2").style.display = "";
        document.getElementById("div3").style.display = "none";
        document.getElementById("div4").style.display = "none";
        document.crewrotationForm.crdate.value = "";
        document.crewrotationForm.searchcr.value = "";
        document.crewrotationForm.positionIdIndex.value = "-1";
        document.crewrotationForm.activityDropdown.value = id;
    } else if (document.crewrotationForm.dynamicId.value == "4")
    {
        document.getElementById("div1").style.display = "none";
        document.getElementById("div2").style.display = "none";
        document.getElementById("div3").style.display = "";
        document.getElementById("div4").style.display = "none";
        document.crewrotationForm.crdate.value = "";
        document.crewrotationForm.activityDropdown.value = "0";
        document.crewrotationForm.positionIdIndex.value = "-1";
    } else if (document.crewrotationForm.dynamicId.value == "5")
    {
        document.getElementById("div1").style.display = "none";
        document.getElementById("div2").style.display = "none";
        document.getElementById("div3").style.display = "none";
        document.getElementById("div4").style.display = "";
        document.crewrotationForm.crdate.value = "";
        document.crewrotationForm.activityDropdown.value = "0";
        document.crewrotationForm.searchcr.value = "";
    }
}

function checkdoc()
{
    if (document.forms[0].ddlhidden)
    {
        if (document.forms[0].ddlhidden.length)
        {
            for (var i = 0; i < document.forms[0].ddlhidden.length; i++)
            {
                if (Number(document.forms[0].ddlhidden[i].value) <= 0)
                {
                    Swal.fire({
                        title: "Please select from drop down list.",
                        didClose: () => {
                            document.forms[0].ddlhidden[i].focus();
                        }
                    })
                    return false;
                }
            }
        } else
        {
            if (Number(document.forms[0].ddlhidden.value) <= 0)
            {
                Swal.fire({
                    title: "Please select from drop down list.",
                    didClose: () => {
                        document.forms[0].ddlhidden.focus();
                    }
                })
                return false;
            }
        }
    }
    return true;
}

function exporttoexcel()
{
    document.crewrotationForm.action = "../crewrotation/CrewrotationExportAction.do";
    document.crewrotationForm.submit();
}

function exporttoexcelActivity()
{
    document.crewrotationForm.action = "../crewrotation/CrewrotationActivityExportAction.do";
    document.crewrotationForm.submit();
}

function exporttoexcelDetails()
{
    document.crewrotationForm.action = "../crewrotation/CrewrotationDetailsExport.do";
    document.crewrotationForm.submit();
}

function deleteForm(cractivityId, status, id, rota)
{
    var s = "";
    if (eval(status) == 1)
        s = "<span>The selected entry will be <b>Deleted.</b> from history</span>";
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
            if (document.crewrotationForm.doDelete)
                document.crewrotationForm.doDelete.value = "yes";
            document.forms[0].target = "";
            document.forms[0].cractivityId.value = cractivityId;
            document.forms[0].rota.value = rota;
            document.crewrotationForm.action = "../crewrotation/CrewrotationAction.do";
            document.crewrotationForm.submit();
        } else
        {
            if (document.getElementById("flexSwitchCheckDefault_" + id).checked == true)
                document.getElementById("flexSwitchCheckDefault_" + id).checked = false;
            else
                document.getElementById("flexSwitchCheckDefault_" + id).checked = true;
        }
    })
}

function viewplan(clientId, clientassetId)
{
    if (document.crewrotationForm.doPlanning)
        document.crewrotationForm.doPlanning.value = "yes";
    document.crewrotationForm.clientId.value = clientId;
    document.crewrotationForm.clientassetId.value = clientassetId;
    document.forms[0].target = "";
    document.crewrotationForm.action = "../crewrotation/CrewrotationAction.do";
    document.crewrotationForm.submit();
}

function viewplanSearch()
{
    if (document.crewrotationForm.doPlanning)
        document.crewrotationForm.doPlanning.value = "yes";
    document.forms[0].target = "";
    document.crewrotationForm.action = "../crewrotation/CrewrotationAction.do";
    document.crewrotationForm.submit();
}

function exporttoexcelplanning()
{
    document.crewrotationForm.action = "../crewrotation/CrewrotationplanningExportAction.do";
    document.crewrotationForm.submit();
}

function gotodashboard()
{
    if (document.forms[0].doSearchCR)
        document.forms[0].doSearchCR.value = "yes";
    document.forms[0].clientIdIndex.value = document.forms[0].clientId.value;
    document.forms[0].assetIdIndex.value = document.forms[0].clientassetId.value;
    document.forms[0].action = "../dashboard/DashboardAction.do";
    document.forms[0].submit();
}

function viewStatusIndex(clientId, clientassetId)
{
    if (document.crewrotationForm.doView)
        document.crewrotationForm.doView.value = "yes";
    document.crewrotationForm.clientId.value = clientId;
    document.crewrotationForm.clientassetId.value = clientassetId;
    document.crewrotationForm.statusindex.value = '5';
    document.forms[0].target = "";
    document.crewrotationForm.action = "../crewrotation/CrewrotationAction.do";
    document.crewrotationForm.submit();
}