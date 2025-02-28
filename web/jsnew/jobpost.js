function resetFilter()
{
    document.forms[0].search.value = "";
    document.jobpostForm.statusIndex.value = "-1";
    searchFormAjax('s', '-1');
}

function showDetail(id)
{
    document.jobpostForm.doView.value = "yes";
    document.jobpostForm.doModify.value = "no";
    document.jobpostForm.doAdd.value = "no";
    document.jobpostForm.jobpostId.value = id;
    document.jobpostForm.action = "../jobpost/JobPostAction.do";
    document.jobpostForm.submit();
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

function searchFormAjax(v, v1)
{
    var url = "../ajax/jobpost/getinfo.jsp";
    var httploc = getHTTPObject();
    var getstr = "";
    var next_value = escape(document.jobpostForm.nextValue.value);
    var search_value = escape(document.jobpostForm.search.value);
    getstr += "nextValue=" + next_value;
    getstr += "&next=" + v;
    getstr += "&search=" + search_value;
    getstr += "&doDirect=" + v1;
    getstr += "&statusIndex=" + document.jobpostForm.statusIndex.value;
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

function goback()
{
    if (document.jobpostForm.doView)
        document.jobpostForm.doView.value = "no";
    if (document.jobpostForm.doCancel)
        document.jobpostForm.doCancel.value = "yes";
    if (document.jobpostForm.doSave)
        document.jobpostForm.doSave.value = "no";
    document.jobpostForm.action = "../jobpost/JobPostAction.do";
    document.jobpostForm.submit();
}

function sortForm(colid, updown)
{
    for (i = 1; i <= 4; i++)
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
    var url_sort = "../ajax/jobpost/sort.jsp";
    var getstr = "";
    var nextValue = 0;
    if (document.jobpostForm.nextValue)
        nextValue = document.jobpostForm.nextValue.value;
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

function addForm()
{
    document.jobpostForm.doModify.value = "no";
    document.jobpostForm.doView.value = "no";
    document.jobpostForm.doAdd.value = "yes";
    document.jobpostForm.action = "../jobpost/JobPostAction.do";
    document.jobpostForm.submit();
}

function modifyForm(id)
{
    document.jobpostForm.doModify.value = "yes";
    document.jobpostForm.doView.value = "no";
    document.jobpostForm.jobpostId.value = id;
    document.jobpostForm.action = "../jobpost/JobPostAction.do";
    document.jobpostForm.submit();
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

    if (checkJobPost())
    {
        document.getElementById('submitdiv').innerHTML = "<img src='../assets/images/loading.gif' align='absmiddle' />";
        document.jobpostForm.doSave.value = "yes";
        document.jobpostForm.doCancel.value = "no";
        document.jobpostForm.action = "../jobpost/JobPostAction.do";
        document.jobpostForm.submit();
    }
}

function checkJobPost()
{
    if (trim(document.jobpostForm.clientId.value) == "-1")
    {
        Swal.fire({
            title: "Please select client name.",
            didClose: () => {
                document.jobpostForm.clientId.focus();
            }
        })
        return false;
    }
    if (document.jobpostForm.clientassetddl.value == "-1")
    {
        Swal.fire({
            title: "Please select asset name.",
            didClose: () => {
                document.jobpostForm.clientassetddl.focus();
            }
        })
        return false;
    }
    if (document.jobpostForm.positionddl.value == "-1")
    {
        Swal.fire({
            title: "Please select position.",
            didClose: () => {
                document.jobpostForm.positionddl.focus();
            }
        })
        return false;
    }
    if (document.jobpostForm.Gradeddl.value == "-1")
    {
        Swal.fire({
            title: "Please select rank.",
            didClose: () => {
                document.jobpostForm.Gradeddl.focus();
            }
        })
        return false;
    }
    if (document.jobpostForm.countryddl.value == "-1")
    {
        Swal.fire({
            title: "Please select country.",
            didClose: () => {
                document.jobpostForm.countryddl.focus();
            }
        })
        return false;
    }

    if (trim(document.jobpostForm.experiencemin.value) == "")
    {
        Swal.fire({
            title: "Please enter experience min value.",
            didClose: () => {
                document.jobpostForm.experiencemin.focus();
            }
        })
        return false;
    }
    if (validdouble(document.jobpostForm.experiencemin) == false)
    {
        return false;
    }
    if (trim(document.jobpostForm.experiencemax.value) == "")
    {
        Swal.fire({
            title: "Please enter experience max value.",
            didClose: () => {
                document.jobpostForm.experiencemax.focus();
            }
        })
        return false;
    }
    if (validdouble(document.jobpostForm.experiencemax) == false)
    {
        return false;
    }

    if (Number(document.jobpostForm.experiencemin.value) > Number(document.jobpostForm.experiencemax.value))
    {
        swal.fire('Please check experience max value. It should be greater than min value.');
        return false;
    }

    if (document.jobpostForm.educationtypeId.value == "-1")
    {
        Swal.fire({
            title: "Please select education level.",
            didClose: () => {
                document.jobpostForm.educationtypeId.focus();
            }
        })
        return false;
    }
if (trim(document.jobpostForm.description.value) != "")
{
    if (validdesc(document.jobpostForm.description) == false)
    {
        return false;
    }
}
    if (trim(document.jobpostForm.noofopening.value) == "")
    {
        Swal.fire({
            title: "Please enter no of openings.",
            didClose: () => {
                document.jobpostForm.noofopening.focus();
            }
        })
        return false;
    }
    if (validnum(document.jobpostForm.noofopening) == false)
    {
        return false;
    }
    if (trim(document.jobpostForm.targetmobdate.value) == "")
    {
        Swal.fire({
            title: "Please select target mobilization date.",
            didClose: () => {
                document.jobpostForm.targetmobdate.focus();
            }
        })
        return false;
    }
    if (validdate(document.jobpostForm.targetmobdate) == false)
    {
        return false;
    }
if (trim(document.jobpostForm.tenure.value) != "")
{
    if (validnum(document.jobpostForm.tenure) == false)
    {
        return false;
    }
}
if (trim(document.jobpostForm.currencyId.value) == "-1")
{
    Swal.fire({
        title: "Please select currency.",
        didClose: () => {
            document.jobpostForm.currencyId.focus();
        }
    })
    return false;
}
if (trim(document.jobpostForm.dayratevalue.value) <=0)
{
    Swal.fire({
        title: "Please enter day rate value.",
        didClose: () => {
            document.jobpostForm.dayratevalue.focus();
        }
    })
    return false;
}
if (validdouble(document.jobpostForm.dayratevalue) == false)
{
    return false;
}
if (trim(document.jobpostForm.vacancypostedby.value) != "")
{
    if (validname(document.jobpostForm.vacancypostedby) == false)
    {
        return false;
    }
}
if (trim(document.jobpostForm.additionalnote.value) != "")
{
    if (validdesc(document.jobpostForm.additionalnote) == false)
    {
        return false;
    }
}
    return true;
}

function resetForm()
{
    document.jobpostForm.reset();
}

function deleteForm(userId, status, id)
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
            var url = "../ajax/jobpost/getinfo.jsp";
            var getstr = "";
            var httploc = getHTTPObject();
            var next_value = escape(document.jobpostForm.nextValue.value);
            var next_del = "-1";
            if (document.jobpostForm.nextDel)
                next_del = escape(document.jobpostForm.nextDel.value);
            var search_value = escape(document.jobpostForm.search.value);
            getstr += "nextValue=" + next_value;
            getstr += "&nextDel=" + next_del;
            getstr += "&search=" + search_value;
            getstr += "&status=" + status;
            getstr += "&deleteVal=" + userId;
            getstr += "&statusIndex=" + document.jobpostForm.statusIndex.value;
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
                        if (trim(v2) != "")
                        {
                            swal.fire(v2);
                        }
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
    });
}

function exporttoexcel()
{
    document.jobpostForm.action = "../jobpost/JobPostExportAction.do";
    document.jobpostForm.submit();
}

function openTab(tp)
{
    if (tp == 1)
    {
        document.jobpostForm.doModify.value = "no";
        document.jobpostForm.doCancel.value = "no";
        if (document.jobpostForm.doView)
            document.jobpostForm.doView.value = "yes";
        if (document.jobpostForm.doBenefitsList)
            document.jobpostForm.doBenefitsList.value = "no";
        if (document.jobpostForm.doViewAssessmentList)
            document.jobpostForm.doViewAssessmentList.value = "no";
        document.jobpostForm.action = "../jobpost/JobPostAction.do";
        document.jobpostForm.submit();
    }
    if (tp == 2)
    {
        document.jobpostForm.doModify.value = "no";
        document.jobpostForm.doCancel.value = "no";
        if (document.jobpostForm.doView)
            document.jobpostForm.doView.value = "no";
        if (document.jobpostForm.doBenefitsList)
            document.jobpostForm.doBenefitsList.value = "yes";
        if (document.jobpostForm.doViewAssessmentList)
            document.jobpostForm.doViewAssessmentList.value = "no";
        document.jobpostForm.action = "../jobpost/JobPostAction.do";
        document.jobpostForm.submit();
    }
    if (tp == 3)
    {
        document.jobpostForm.doModify.value = "no";
        document.jobpostForm.doCancel.value = "no";
        if (document.jobpostForm.doView)
            document.jobpostForm.doView.value = "no";
        if (document.jobpostForm.doBenefitsList)
            document.jobpostForm.doBenefitsList.value = "no";
        if (document.jobpostForm.doViewAssessmentList)
            document.jobpostForm.doViewAssessmentList.value = "yes";
        document.jobpostForm.action = "../jobpost/JobPostAction.do";
        document.jobpostForm.submit();
    }
}

function addbenefitForm()
{
    document.jobpostForm.doAddBenefit.value = "yes";
    document.jobpostForm.action = "../jobpost/JobPostAction.do";
    document.jobpostForm.submit();
}

function deleteJobPostBenefit(id, status)
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
            document.jobpostForm.doDeleteJobPostBenefitDetail.value = "yes";
            document.jobpostForm.jobpostBenefitId.value = id;
            document.jobpostForm.action = "../jobpost/JobPostAction.do";
            document.jobpostForm.submit();
        } else
        {
            if (document.getElementById("flexSwitchCheckDefault_" + id).checked == true)
                document.getElementById("flexSwitchCheckDefault_" + id).checked = false;
            else
                document.getElementById("flexSwitchCheckDefault_" + id).checked = true;
        }
    });
}

function closePost(id)
{
    var s = "<span>The action will Close job Post permanently.</b></span>";
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
            document.jobpostForm.doClose.value = "yes";
            document.jobpostForm.jobpostId.value = id;
            document.jobpostForm.action = "../jobpost/JobPostAction.do";
            document.jobpostForm.submit();
        }
    });
}

function savebenefitForm()
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
    if (checkJobPostBenefit())
    {
        document.jobpostForm.doSaveBenefit.value = "yes";
        document.jobpostForm.action = "../jobpost/JobPostAction.do";
        document.jobpostForm.submit();
    }
}

function checkJobPostBenefit()
{
    if (trim(document.jobpostForm.benefitname.value) == "")
    {
        swal.fire('Please enter title.');
        document.jobpostForm.benefitname.focus();
        return false;
    }
    if (validdesc(document.jobpostForm.benefitname) == false)
    {
        return false;
    }
    if (document.jobpostForm.benefittypeId.value == "-1")
    {
        swal.fire('Please select type.');
        document.jobpostForm.benefittypeId.focus();
        return false;
    }
    return true;
}

function modifybenefitForm()
{
    document.jobpostForm.doModifyBenefit.value = "yes";
    document.jobpostForm.action = "../jobpost/JobPostAction.do";
    document.jobpostForm.submit();
}

function sethidden(id)
{
    document.getElementById("benefetquestionIdHidden_" + id).value = document.getElementById("benefitquestionId_" + id).value;
}

function checkBenefit()
{
    if (document.forms[0].posbenefitId)
    {
        if (document.forms[0].posbenefitId.length)
        {
            for (var i = 0; i < document.forms[0].posbenefitId.length; i++)
            {
                if (Number(document.forms[0].benefetquestionIdHidden[i].value) > 0)
                {
                    if (trim(document.forms[0].posdescription[i].value) == "")
                    {
                        swal.fire("Please enter information.");
                        document.forms[0].posdescription[i].focus();
                        return false;
                    }
                }
                if (trim(document.forms[0].posdescription[i].value) != "")
                {
                    if (eval(document.forms[0].benefetquestionIdHidden[i].value) <= 0)
                    {
                        swal.fire("Please select information type.");
                        document.forms[0].posdescription[i].focus();
                        return false;
                    }
                }
            }
        } else
        {
            if (eval(document.forms[0].benefetquestionIdHidden.value) > 0)
            {
                if (trim(document.forms[0].posdescription.value) == "")
                {
                    swal.fire("Please enter information.");
                    document.forms[0].posdescription.focus();
                    return false;
                }
            }
            if (trim(document.forms[0].posdescription.value) != "")
            {
                if (eval(document.forms[0].benefetquestionIdHidden.value) <= 0)
                {
                    swal.fire("Please select information type.");
                    document.forms[0].posdescription.focus();
                    return false;
                }
            }
        }
    }
    return true;
}

function saveposbenefitForm()
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

    if (checkBenefit())
    {
        document.getElementById('submitdiv').innerHTML = "<img src='../assets/images/loading.gif' align='absmiddle' />";
        document.jobpostForm.doSavePosBenefit.value = "yes";
        document.jobpostForm.action = "../jobpost/JobPostAction.do";
        document.jobpostForm.submit();
    }
}

function addAssessmentForm(id)
{
    document.jobpostForm.assessmentDetailId.value = id;
    document.jobpostForm.doModifyAssessmentDetail.value = "yes";
    document.jobpostForm.action = "../jobpost/JobPostAction.do";
    document.jobpostForm.submit();
}

function deleteAssessmentForm(id, status)
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
            document.jobpostForm.doDeleteAssessmentDetail.value = "yes";
            document.jobpostForm.assessmentDetailId.value = id;
            document.jobpostForm.status.value = status;
            document.jobpostForm.doModifyAssessmentDetail.value = "no";
            document.jobpostForm.action = "../jobpost/JobPostAction.do";
            document.jobpostForm.submit();
        } else
        {
            if (document.getElementById("flexSwitchCheckDefault_" + id).checked == true)
                document.getElementById("flexSwitchCheckDefault_" + id).checked = false;
            else
                document.getElementById("flexSwitchCheckDefault_" + id).checked = true;
        }
    });
}

function submitAssessmentDetailForm()
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
    if (checkAssessmentDetail())
    {
        document.getElementById('submitdiv').innerHTML = "<img src='../assets/images/loading.gif' align='absmiddle' />";
        document.jobpostForm.doSaveAssessmentDetail.value = "yes";
        document.jobpostForm.doModifyAssessmentDetail.value = "no";
        document.jobpostForm.doCancel.value = "no";
        document.jobpostForm.action = "../jobpost/JobPostAction.do";
        document.jobpostForm.submit();
    }
}

function checkAssessmentDetail()
{
    if (document.jobpostForm.assessmentId.value == "-1")
    {
        swal.fire('Please select assessment.');
        document.jobpostForm.assessmentId.focus();
        return false;
    }
    if (trim(document.jobpostForm.minScore.value) == "")
    {
        swal.fire('Please enter minimum score.');
        document.jobpostForm.minScore.focus();
        return false;
    }
    if (validnum(document.jobpostForm.minScore) == false)
    {
        return false;
    }
    return true;
}

function setidname()
{
    if (document.forms[0].assessmentId.value != "-1")
    {
        var url = "../ajax/jobpost/setidname.jsp";
        var httploc = getHTTPObject();
        var getstr = "assessmentId=" + document.forms[0].assessmentId.value;
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
                    var v2 = arr[1];
                    document.forms[0].code.value = v1;
                    document.forms[0].pname.value = v2;
                }
            }
        };
        httploc.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
        httploc.setRequestHeader("Content-length", getstr.length);
        httploc.setRequestHeader("Connection", "close");
        httploc.send(getstr);
    }
}

function setAssessments()
{
    var url = "../ajax/jobpost/assessments.jsp";
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
                document.getElementById("assessmentId").innerHTML = '';
                document.getElementById("assessmentId").innerHTML = response;
                document.getElementById("code").value = "";
                document.getElementById("pname").value = "";
            }
        }
    };
    httploc.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    httploc.setRequestHeader("Content-length", getstr.length);
    httploc.setRequestHeader("Connection", "close");
    httploc.send(getstr);
}

function setAssetDDL()
{
    var url = "../ajax/jobpost/getasset.jsp";
    var httploc = getHTTPObject();
    var getstr = "clientId=" + document.jobpostForm.clientId.value + "&from=asset";
    httploc.open("POST", url, true);
    httploc.onreadystatechange = function ()
    {
        if (httploc.readyState == 4)
        {
            if (httploc.status == 200)
            {
                var response = httploc.responseText;
                document.getElementById("clientassetddl").innerHTML = '';
                document.getElementById("clientassetddl").innerHTML = response;
                document.jobpostForm.positionname.value = "";
                setPositionDDL();
            }
        }
    };
    httploc.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    httploc.send(getstr);
}

function setPositionDDL()
{
    var url = "../ajax/jobpost/getasset.jsp";
    var httploc = getHTTPObject();
    var getstr = "clientassetId=" + document.jobpostForm.clientassetddl.value + "&from=position";
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
                document.jobpostForm.positionname.value = "";
                setGradeDDL();
            }
        }
    };
    httploc.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    httploc.send(getstr);
}

function setGradeDDL()
{
    var url = "../ajax/jobpost/getasset.jsp";
    var httploc = getHTTPObject();
    var positionId = document.getElementById("positionddl");
    var pname = positionId.options[positionId.selectedIndex].text;
    if (pname == "- Select -") {
        document.jobpostForm.positionname.value = "";
    } else {
        document.jobpostForm.positionname.value = pname;
    }
    var getstr = "clientassetId=" + document.jobpostForm.clientassetddl.value + "&positionnm=" + document.jobpostForm.positionname.value + "&from=grade";
    httploc.open("POST", url, true);
    httploc.onreadystatechange = function ()
    {
        if (httploc.readyState == 4)
        {
            if (httploc.status == 200)
            {
                var response = httploc.responseText;
                document.getElementById("Gradeddl").innerHTML = '';
                document.getElementById("Gradeddl").innerHTML = response;
            }
        }
    };
    httploc.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    httploc.send(getstr);
}

function searchFormShortlist(id)
{
    document.forms[0].jobpostId.value = id;
    document.forms[0].action = "../shortlisting/ShortlistingAction.do?doCandSearch=yes&fm=jp";
    document.forms[0].submit();
}

function addtomaster(type)
{
    document.jobpostForm.mtype.value = type;
    document.jobpostForm.mname.value = "";
    if (type == 1)
        document.getElementById("maddid").innerHTML = "Relation";
    else if (type == 2)
        document.getElementById("maddid").innerHTML = "Department";
    else if (type == 3)
    {
        if (Number(document.forms[0].countryId.value) <= 0) {
            Swal.fire({
                title: "Please select country.",
                didClose: () => {
                    document.forms[0].countryId.focus();
                    $('#relation_modal').modal('hide');
                }
            })
        }else{
            $('#relation_modal').modal('show');
        }
        document.getElementById("maddid").innerHTML = "City";
    }
}

function addtomasterajax()
{
    if (trim(document.forms[0].mname.value) != "")
    {
        var type = document.forms[0].mtype.value;
        var countryId;
        if (type == "3") {
            countryId = document.forms[0].countryId.value;
            if (countryId <= "0") {
                Swal.fire({
                    title: "Please select country.",
                    didClose: () => {
                        document.forms[0].countryId.focus();
                    }
                })
            }
        }
        var url = "../ajax/client/addtomaster.jsp";
        var httploc = getHTTPObject();
        var getstr = "type=" + type;
        getstr += "&name=" + escape(document.forms[0].mname.value);
        if (type == "3") {
            getstr += "&countryId=" + countryId;
        }
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
                        if (type == "1")
                            document.getElementById('relationdiv').innerHTML = response;
                        else if (type == "2")
                            document.getElementById('departmentdiv').innerHTML = response;
                        else if (type == "3") {
                            var arr = new Array();
                            arr = response.split('#@#');
                            var v1 = trim(arr[0]);
                            var v2 = arr[1];
                            document.forms[0].cityname.value = v1;
                            document.forms[0].cityId.value = v2;
                        }
                        document.forms[0].mtype.value = "0";
                        document.forms[0].mname.value = "";
                        $('#relation_modal').modal('hide');
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
            title: "Please enter name.",
            didClose: () => {
                document.jobpostForm.mname.focus();
            }
        })
    }
}

function clearcity()
{
    document.jobpostForm.cityId.value = "0";
    document.jobpostForm.cityname.value = "";
    var url = "../ajax/candidate/getcountrycode.jsp";
    var httploc = getHTTPObject();
    var getstr = "countryId=" + document.jobpostForm.countryId.value;
    httploc.open("POST", url, true);
    httploc.onreadystatechange = function ()
    {
        if (httploc.readyState == 4)
        {
            if (httploc.status == 200)
            {
                var response = trim(httploc.responseText);
                document.jobpostForm.cityId.value = response;
            }
        }
    };
    httploc.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    httploc.setRequestHeader("Content-length", getstr.length);
    httploc.setRequestHeader("Connection", "close");
    httploc.send(getstr);
}