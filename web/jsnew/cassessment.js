function resetFilter()
{
    document.forms[0].search.value = "";
    document.forms[0].assettypeIndex.value = "-1";
    document.forms[0].positionIndex.value = "-1";
    document.forms[0].vstatusIndex.value = "-1";
    document.forms[0].astatusIndex.value = "-1";
    searchFormAjax('s', '-1');
}

function resetFilterAssessor()
{
    document.forms[0].search.value = "";
    document.forms[0].assettypeIndex.value = "-1";
    document.forms[0].assessorIndex.value = "-1";
    document.forms[0].aPositionIndex.value = "-1";
    document.forms[0].passessmentIndex.value = "-1";
    gobackassessor();
}

function showDetail(id)
{
    document.cassessmentForm.doView.value = "yes";
    document.cassessmentForm.candidateId.value = id;
    document.cassessmentForm.action = "../cassessment/CassessmentAction.do";
    document.cassessmentForm.submit();
}

function retakeDetail(cid, paid)
{
    var s = "<span>Retake the assessment.</span></br> ";
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
            document.cassessmentForm.doRetake.value = "yes";
            document.cassessmentForm.cassessmentId.value = cid;
            document.cassessmentForm.pAssessmentId.value = paid;
            document.cassessmentForm.action = "../cassessment/CassessmentAction.do";
            document.cassessmentForm.submit();
        }
    })
}

function viewCandidate(id, passflag)
{
    document.forms[0].doView.value = "yes";
    document.forms[0].candidateId.value = id;
    document.forms[0].target = "_blank";
    if (Number(passflag) == 2) {
        document.forms[0].action = "/jxp/talentpool/TalentpoolAction.do";
    } else {
        document.forms[0].action = "/jxp/candidate/CandidateAction.do";
    }
    document.forms[0].submit();
}

function viewAssessment(id)
{
    document.forms[0].doView.value = "yes";
    document.forms[0].assessmentId.value = id;
    document.forms[0].target = "_blank";
    document.forms[0].action = "/jxp/assessment/AssessmentAction.do";
    document.forms[0].submit();
}

function goback()
{
    if (document.cassessmentForm.doView)
        document.cassessmentForm.doView.value = "no";
    if (document.cassessmentForm.doCancel)
        document.cassessmentForm.doCancel.value = "yes";
    document.cassessmentForm.target = "_self";
    document.cassessmentForm.action = "../cassessment/CassessmentAction.do";
    document.cassessmentForm.submit();
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

function checkSearch()
{
    if (trim(document.forms[0].search.value) != "")
    {
        if (validdesc(document.forms[0].search) == false)
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
        var url = "../ajax/cassessment/getinfo.jsp";
        var httploc = getHTTPObject();
        var getstr = "";
        var next_value = escape(document.cassessmentForm.nextValue.value);
        var search_value = escape(document.cassessmentForm.search.value);
        getstr += "nextValue=" + next_value;
        getstr += "&next=" + v;
        getstr += "&search=" + search_value;
        getstr += "&doDirect=" + v1;
        getstr += "&astatusIndex=" + document.cassessmentForm.astatusIndex.value;
        getstr += "&vstatusIndex=" + document.cassessmentForm.vstatusIndex.value;
        getstr += "&positionIndex=" + document.cassessmentForm.positionIndex.value;
        getstr += "&assettypeIndex=" + document.cassessmentForm.assettypeIndex.value;
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

function sortForm(colid, updown)
{
    for (i = 1; i <= 5; i++)
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
    var url_sort = "../ajax/cassessment/sort.jsp";
    var getstr = "";
    var nextValue = 0;
    if (document.cassessmentForm.nextValue)
        nextValue = document.cassessmentForm.nextValue.value;
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
    document.cassessmentForm.reset();
}

function exporttoexcel()
{
    document.cassessmentForm.action = "../cassessment/CassessmentExportAction.do";
    document.cassessmentForm.submit();
}

function exporttoexcelassessor()
{
    document.cassessmentForm.action = "../cassessment/CassessmentAExportAction.do";
    document.cassessmentForm.submit();
}

function searchFormViewAjax()
{
    var url = "../ajax/cassessment/getAssessments.jsp";
    var httploc = getHTTPObject();
    var getstr = "";
    getstr += "scheduleIndex=" + document.cassessmentForm.scheduleddl.value;
    httploc.open("POST", url, true);
    httploc.onreadystatechange = function ()
    {
        if (httploc.readyState == 4)
        {
            if (httploc.status == 200)
            {
                var response = httploc.responseText;
                document.getElementById('dassessments').innerHTML = '';
                document.getElementById('ajax_div').innerHTML = '';
                document.getElementById('dassessments').innerHTML = response;
            }
        }
    };
    httploc.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    httploc.setRequestHeader("Content-length", getstr.length);
    httploc.setRequestHeader("Connection", "close");
    httploc.send(getstr);
    document.getElementById('dassessments').innerHTML = "<div><img src='../assets/images/loading.gif' align='absmiddle'/>Please wait. Loading...</div>";
}

function setDivClass(len, srno) {
    for (i = 1; i <= len; i++)
    {
        if (i == Number(srno))
            document.getElementById("leftli" + i).className = "active_box";
        else
            document.getElementById("leftli" + i).className = "";
    }
}

function getViewEditData(passId, cId, para) 
{
    if (para === "onview") {
        para = "view";
        cId = Number(document.cassessmentForm.cassessmentId.value);
    } else {
        document.cassessmentForm.cassessmentId.value = cId;
    }
    document.cassessmentForm.pAssessmentId.value = passId;
    var url = "../ajax/cassessment/edit_cassessment.jsp";
    var httploc = getHTTPObject();
    var getstr = "";
    getstr += "passid=" + passId;
    getstr += "&candidateid=" + document.cassessmentForm.candidateId.value;
    getstr += "&cid=" + cId;
    getstr += "&para=" + para;
    httploc.open("POST", url, true);
    httploc.onreadystatechange = function ()
    {
        if (httploc.readyState == 4)
        {
            if (httploc.status == 200)
            {
                var response = httploc.responseText;
                document.getElementById('ajax_div').innerHTML = '';
                document.getElementById('ajax_div').innerHTML = response;
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
    document.getElementById('ajax_div').innerHTML = "<div><img src='../assets/images/loading.gif' align='absmiddle'/>Please wait. Loading...</div>";
}

function getCassessmentScore(cassessmentId) {

    var url = "../ajax/cassessment/edit_cassessmentscore.jsp";
    var httploc = getHTTPObject();
    var getstr = "";
    getstr += "cassessmentid=" + cassessmentId;
    httploc.open("POST", url, true);
    httploc.onreadystatechange = function ()
    {
        if (httploc.readyState == 4)
        {
            if (httploc.status == 200)
            {
                var response = httploc.responseText;
                document.getElementById('ajax_div').innerHTML = '';
                document.getElementById('ajax_div').innerHTML = response;
            }
        }
    };
    httploc.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    httploc.setRequestHeader("Content-length", getstr.length);
    httploc.setRequestHeader("Connection", "close");
    httploc.send(getstr);
    document.getElementById('ajax_div').innerHTML = "<div><img src='../assets/images/loading.gif' align='absmiddle'/>Please wait. Loading...</div>";
}

function getonoffvalue() {
    if (document.getElementById("selMode").value == "Online") {
        document.getElementById("onofflbl").innerHTML = "Link";
    } else if (document.getElementById("selMode").value == "Offline") {
        document.getElementById("onofflbl").innerHTML = "Location";
    }
}

function getChgedatetime() {
    var url = "../ajax/cassessment/getChangedatetime.jsp";
    var httploc = getHTTPObject();
    var getstr = "";
    var tz1 = document.getElementById("selCandTimeZone").options[document.getElementById("selCandTimeZone").selectedIndex].text;
    var tz2 = document.getElementById("selAssessTimeZone").options[document.getElementById("selAssessTimeZone").selectedIndex].text;
    if (document.getElementById("txtstartdate").value != "" && document.getElementById("txttime").value != "" && tz1 != "" && tz2 != "")
    {
        getstr += "inputdate=" + document.getElementById("txtstartdate").value + " " + document.getElementById("txttime").value;
        getstr += "&intz=" + tz1;
        getstr += "&outtz=" + tz2;
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
    if (checkCassessment())
    {
        document.getElementById("submitdiv").innerHTML="<img src='../assets/images/loading.gif' align='absmiddle' />";
        document.cassessmentForm.doSave.value = "yes";
        document.cassessmentForm.doCancel.value = "no";
        document.cassessmentForm.action = "../cassessment/CassessmentAction.do";
        document.cassessmentForm.submit();
    }
}

function checkCassessment()
{
    if (trim(document.cassessmentForm.txtstartdate.value) == "")
    {
        Swal.fire({
            title: "Please select date.",
            didClose: () => {
                document.cassessmentForm.txtstartdate.focus();
            }
        })
        return false;
    }
    if (validdate(document.cassessmentForm.txtstartdate) == false)
    {
        return false;
    }

    if (document.cassessmentForm.selMode.value == "mode")
    {
        Swal.fire({
            title: "Please select mode.",
            didClose: () => {
                document.cassessmentForm.selMode.focus();
            }
        })
        return false;
    }

    if (document.cassessmentForm.selDuration.value <= 0)
    {
        Swal.fire({
            title: "Please select duration (minutes).",
            didClose: () => {
                document.cassessmentForm.selDuration.focus();
            }
        })
        return false;
    }

    if (trim(document.cassessmentForm.txttime.value) == "")
    {
        Swal.fire({
            title: "Please select time.",
            didClose: () => {
                document.cassessmentForm.txttime.focus();
            }
        })
        return false;
    }
    if (validtime(document.cassessmentForm.txttime) == false)
    {
        return false;
    }

    if (document.cassessmentForm.selAssessor.value <= 0)
    {
        Swal.fire({
            title: "Please select assesor.",
            didClose: () => {
                document.cassessmentForm.selAssessor.focus();
            }
        })
        return false;
    }

    if (document.cassessmentForm.selCandTimeZone.value <= 0)
    {
        Swal.fire({
            title: "Please select assesor country - time zone.",
            didClose: () => {
                document.cassessmentForm.selCandTimeZone.focus();
            }
        })
        return false;
    }

    if (document.cassessmentForm.selAssessTimeZone.value <= 0)
    {
        Swal.fire({
            title: "Please select candidate country - time zone.",
            didClose: () => {
                document.cassessmentForm.selAssessTimeZone.focus();
            }
        })
        return false;
    }

    if (trim(document.cassessmentForm.txtDate.value) == "")
    {
        Swal.fire({
            title: "Please enter date.",
            didClose: () => {
                document.cassessmentForm.txtDate.focus();
            }
        })
        return false;
    }

    if (trim(document.cassessmentForm.txtLocLink.value) == "")
    {
        var vtitle = " location."
        if (document.cassessmentForm.selMode.value == "Online") {
            vtitle = " link."
        }

        Swal.fire({
            title: "Please enter" + vtitle,
            didClose: () => {
                document.cassessmentForm.txtLocLink.focus();
            }
        })
        return false;
    }
    if (validdesc(document.cassessmentForm.txtLocLink) == false)
    {
        return false;
    }
    return true;
}

function setRole()
{
    document.cassessmentForm.doChange.value = "yes";
    document.cassessmentForm.action = "../cassessment/CassessmentAction.do";
    document.cassessmentForm.submit();
}

function gobackassessor()
{
    document.cassessmentForm.doCancelAssessor.value = "yes";
    document.cassessmentForm.action = "../cassessment/CassessmentAction.do";
    document.cassessmentForm.submit();
}

function handleKeySearchAssesor(e)
{
    var key = e.keyCode || e.which;
    if (key === 13)
    {
        e.preventDefault();
        searchFormAjaxAssessor('s', '-1');
    }
}

function searchFormAjaxAssessor(v, v1)
{
    if (checkSearch())
    {
        var url = "../ajax/cassessment/getinfo_assessor.jsp";
        var httploc = getHTTPObject();
        var getstr = "";
        var search_value = escape(document.cassessmentForm.search.value);
        var next_value = escape(document.cassessmentForm.nextValue.value);
        getstr += "nextValue=" + next_value;
        getstr += "&search=" + search_value;
        getstr += "&next=" + v;
        getstr += "&doDirect=" + v1;
        getstr += "&passessmentIndex=" + document.cassessmentForm.passessmentIndex.value;
        if (document.cassessmentForm.assessorIndex)
            getstr += "&assessorIndex=" + document.cassessmentForm.assessorIndex.value;
        getstr += "&aPositionIndex=" + document.cassessmentForm.aPositionIndex.value;
        getstr += "&assettypeIndex=" + document.cassessmentForm.assettypeIndex.value;
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

function sortFormAssessor(colid, updown)
{
    for (i = 1; i <= 5; i++)
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
    var url_sort = "../ajax/cassessment/sort_assessor.jsp";
    var getstr = "";
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

function showAssessorDetail(id, id1, id2)
{
    document.forms[0].doAssessorView.value = "yes";
    document.forms[0].candidateId.value = id;
    document.forms[0].cassessmentId.value = id1;
    document.forms[0].pAssessmentId.value = id2;
    document.cassessmentForm.action = "../cassessment/CassessmentAction.do";
    document.cassessmentForm.submit();
}

function getAssessorScore() {

    var url = "../ajax/cassessment/edit_assessorscore.jsp";
    var httploc = getHTTPObject();
    var getstr = "";
    getstr += "cassessmentid=" + document.cassessmentForm.cassessmentId.value;
    httploc.open("POST", url, true);
    httploc.onreadystatechange = function ()
    {
        if (httploc.readyState == 4)
        {
            if (httploc.status == 200)
            {
                var response = httploc.responseText;
                document.getElementById('ajax_assessor_div').innerHTML = '';
                document.getElementById('ajax_assessor_div').innerHTML = response;
            }
        }
    };
    httploc.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    httploc.setRequestHeader("Content-length", getstr.length);
    httploc.setRequestHeader("Connection", "close");
    httploc.send(getstr);
    document.getElementById('ajax_assessor_div').innerHTML = "<div><img src='../assets/images/loading.gif' align='absmiddle'/>Please wait. Loading...</div>";
}

function getAssessmentquestions(assessmentId, parameterId, title)
{
    var url = "../ajax/cassessment/getAssessmentsQuestion.jsp";
    var httploc = getHTTPObject();
    var getstr = "";
    getstr += "assessmentId=" + assessmentId;
    getstr += "&parameterId=" + parameterId;
    getstr += "&title=" + title;
    httploc.open("POST", url, true);
    httploc.onreadystatechange = function ()
    {
        if (httploc.readyState == 4)
        {
            if (httploc.status == 200)
            {
                var response = httploc.responseText;
                document.getElementById('camodel').innerHTML = '';
                document.getElementById('camodel').innerHTML = response;
            }
        }
    };
    httploc.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    httploc.setRequestHeader("Content-length", getstr.length);
    httploc.setRequestHeader("Connection", "close");
    httploc.send(getstr);
    document.getElementById('camodel').innerHTML = "<div><img src='../assets/images/loading.gif' align='absmiddle'/>Please wait. Loading...</div>";
}

function setMarks()
{
    var totalmarks = 0, averagemarks = 0;
    var len = 0;

    if (document.forms[0].marks)
    {
        if (document.forms[0].marks.length)
        {
            len = document.forms[0].marks.length;
            for (i = 0; i < len; i++)
            {
                if (Number(document.forms[0].marks[i].value) > 100)
                {
                    Swal.fire({
                        title: "Marks should not be greater that 100.",
                        didClose: () => {
                            document.forms[0].marks[i].focus();
                        }
                    })
                    document.forms[0].marks[i].value = '';
                }

                totalmarks += Number(document.forms[0].marks[i].value);
            }
        } else
        {
            len = 1;
            if (Number(document.forms[0].marks.value) > 100)
            {
                Swal.fire({
                    title: "Marks should not be greater that 100.",
                    didClose: () => {
                        document.forms[0].marks.focus();
                    }
                })
                document.forms[0].marks.value = '';
            }
            totalmarks = Number(document.forms[0].marks.value);
        }
        averagemarks = (totalmarks / len).toFixed(2);
        var pass;
        if (Number(document.forms[0].hdnpassFlag.value) == 1)
        {
            if (Number(averagemarks) >= Number(document.forms[0].hdnminScore.value))
            {
                pass = 1;
            } else
            {
                pass = 0;
            }
        } else
        {
            if (Number(averagemarks) >= 0)
                pass = 1;
        }
        if (Number(pass) == 1)
            document.getElementById("smarks").className = "avg_scrore_value passed_mark";
        else
            document.getElementById("smarks").className = "avg_scrore_value";
        document.forms[0].hdnmarks.value = averagemarks;
        document.getElementById("smarks").innerHTML = averagemarks;
    }
}

function submitAssessorForm()
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
    if (checkMarks())
    {
        document.cassessmentForm.doAssessorSave.value = "yes";
        document.cassessmentForm.doSave.value = "no";
        document.cassessmentForm.doCancel.value = "no";
        document.cassessmentForm.action = "../cassessment/CassessmentAction.do";
        document.cassessmentForm.submit();
    }
}

function submitScoreForm()
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
    if (checkMarks())
    {
        document.cassessmentForm.doSaveScore.value = "yes";
        document.cassessmentForm.doSave.value = "no";
        document.cassessmentForm.doCancel.value = "no";
        document.cassessmentForm.action = "../cassessment/CassessmentAction.do";
        document.cassessmentForm.submit();
    }
}

function checkMarks()
{
    if (document.forms[0].marks)
    {
        if (document.forms[0].marks.length)
        {
            var len = document.forms[0].marks.length;
            for (i = 0; i < len; i++)
            {
                if (Number(document.forms[0].marks[i].value) > 100)
                {
                    Swal.fire({
                        title: "Marks should not be greater that 100.",
                        didClose: () => {
                            document.forms[0].marks[i].focus();
                        }
                    })
                    return false;
                } else if (Number(document.forms[0].marks[i].value) < 0)
                {
                    Swal.fire({
                        title: "Please enter marks.",
                        didClose: () => {
                            document.forms[0].marks[i].focus();
                        }
                    })
                    return false;
                }
            }
        } else
        {
            len = 1;
            if (Number(document.forms[0].marks.value) > 100)
            {
                Swal.fire({
                    title: "Marks should not be greater that 100.",
                    didClose: () => {
                        document.forms[0].marks.focus();
                    }
                })
                return false;
            } else if (Number(document.forms[0].marks.value) < 0)
            {
                Swal.fire({
                    title: "Please enter marks.",
                    didClose: () => {
                        document.forms[0].marks.focus();
                    }
                })
                return false;
            }
        }
    } else
    {
        Swal.fire("Please check parameters.");
        return false;
    }

    if (Number(document.forms[0].hdnmarks.value) < 0)
    {
        Swal.fire({
            title: "Please enter marks.",
            didClose: () => {
                document.forms[0].marks.focus();
            }
        })
        return false;
    }

    if (trim(document.forms[0].txtremarks.value) == "")
    {
        Swal.fire({
            title: "Please enter remarks.",
            didClose: () => {
                document.forms[0].txtremarks.focus();
            }
        })
        return false;
    }
    if (validdesc(document.forms[0].txtremarks) == false)
    {
        return false;
    }
    return true;
}

function showDetailAssessNow(id)
{
    document.cassessmentForm.doViewAssessNow.value = "yes";
    document.cassessmentForm.candidateId.value = id;
    document.cassessmentForm.action = "../cassessment/CassessmentAction.do";
    document.cassessmentForm.submit();
}

function saveAssessNow(id)
{
    if(checkAssessNow())
    {
    var s = "";
    if (eval(id) == 2)
        s = "<span>Are you sure you want to approve the candidate?</span>";
    else
        s = "<span>Are you sure you want to reject the candidate?</span>";
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
    document.cassessmentForm.radio18.value = id;
    document.cassessmentForm.doSaveAssessNow.value = "yes";
    document.cassessmentForm.action = "../cassessment/CassessmentAction.do";
    document.cassessmentForm.submit();
    }
    })
}
}

function checkAssessNow()
{
    if (Number(document.cassessmentForm.radio1.value) <= "0")
    {
        Swal.fire({
            title: "Please select rating for Dress up / presentation.",
            didClose: () => {
            }
        })
        return false;
    }
    if (Number(document.cassessmentForm.radio2.value) <= "0")
    {
        Swal.fire({
            title: "Please select rating for Composure.",
            didClose: () => {
            }
        })
        return false;
    }
    if (Number(document.cassessmentForm.radio3.value) <= "0")
    {
        Swal.fire({
            title: "Please select rating for Attitude.",
            didClose: () => {
            }
        })
        return false;
    }
    if (Number(document.cassessmentForm.radio4.value) <= "0")
    {
        Swal.fire({
            title: "Please select rating for Motivation.",
            didClose: () => {
            }
        })
        return false;
    }
    if (Number(document.cassessmentForm.radio5.value) <= "0")
    {
        Swal.fire({
            title: "Please select rating for Communication.",
            didClose: () => {
            }
        })
        return false;
    }
    if (Number(document.cassessmentForm.radio6.value) <= "0")
    {
        Swal.fire({
            title: "Please select rating for Assertiveness.",
            didClose: () => {
            }
        })
        return false;
    }
    if (Number(document.cassessmentForm.radio7.value) <= "0")
    {
        Swal.fire({
            title: "Please select rating for Verbal / Persuasiveness.",
            didClose: () => {
            }
        })
        return false;
    }
    if (Number(document.cassessmentForm.radio8.value) <= "0")
    {
        Swal.fire({
            title: "Please select rating for Education/Professional.",
            didClose: () => {
            }
        })
        return false;
    }
    if (Number(document.cassessmentForm.radio9.value) <= "0")
    {
        Swal.fire({
            title: "Please select rating for Relevant Experience.",
            didClose: () => {
            }
        })
        return false;
    }
    if (Number(document.cassessmentForm.radio10.value) <= "0")
    {
        Swal.fire({
            title: "Please select rating for Digital Proficiency.",
            didClose: () => {
            }
        })
        return false;
    }
    if (Number(document.cassessmentForm.radio12.value) <= "0")
    {
        Swal.fire({
            title: "Please select rating for Business Knowledge.",
            didClose: () => {
            }
        })
        return false;
    }
    if (Number(document.cassessmentForm.radio13.value) <= "0")
    {
        Swal.fire({
            title: "Please select rating for Job Knowledge.",
            didClose: () => {
            }
        })
        return false;
    }
    if (Number(document.cassessmentForm.radio14.value) <= "0")
    {
        Swal.fire({
            title: "Please select rating for Clarity of thought.",
            didClose: () => {
            }
        })
        return false;
    }
    if (Number(document.cassessmentForm.radio15.value) <= "0")
    {
        Swal.fire({
            title: "Please select rating for Understands questions.",
            didClose: () => {
            }
        })
        return false;
    }
    if (Number(document.cassessmentForm.radio16.value) <= "0")
    {
        Swal.fire({
            title: "Please select rating for Logic.",
            didClose: () => {
            }
        })
        return false;
    }
    if (Number(document.cassessmentForm.radio17.value) <= "0")
    {
        Swal.fire({
            title: "Please select rating for Overall Rating.",
            didClose: () => {
            }
        })
        return false;
    }
    return true;
}