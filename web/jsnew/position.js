function resetFilter()
{
    document.forms[0].search.value = "";
    searchFormAjax('s', '-1');
}

function showDetail(id)
{
    document.positionForm.doView.value = "yes";
    document.positionForm.doModify.value = "no";
    document.positionForm.doAdd.value = "no";
    document.positionForm.positionId.value = id;
    document.positionForm.action = "../position/PositionAction.do";
    document.positionForm.submit();
}

function checkSearch()
{
    if(trim(document.positionForm.search.value) != "")
    {
        if(validdescsearch(document.positionForm.search) == false)
        {
            document.positionForm.search.focus();
            return false;
        }
    }
    return true;
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
    if(checkSearch())
    {
        var url = "../ajax/position/getinfo.jsp";
        var httploc = getHTTPObject();
        var getstr = "";
        var next_value = escape(document.positionForm.nextValue.value);
        var search_value = escape(document.positionForm.search.value);
        getstr += "nextValue=" + next_value;
        getstr += "&next=" + v;
        getstr += "&search=" + search_value;
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
    if (document.positionForm.doView)
        document.positionForm.doView.value = "no";
    if (document.positionForm.doCancel)
        document.positionForm.doCancel.value = "yes";
    if (document.positionForm.doSave)
        document.positionForm.doSave.value = "no";
    document.positionForm.action = "../position/PositionAction.do";
    document.positionForm.submit();
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
    var url_sort = "../ajax/position/sort.jsp";
    var getstr = "";
    var nextValue = 0;
    if (document.positionForm.nextValue)
        nextValue = document.positionForm.nextValue.value;
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
    document.positionForm.doModify.value = "no";
    document.positionForm.doView.value = "no";
    document.positionForm.doAdd.value = "yes";
    document.positionForm.action = "../position/PositionAction.do";
    document.positionForm.submit();
}

function modifyForm(id)
{
    document.positionForm.doModify.value = "yes";
    document.positionForm.doView.value = "no";
    document.positionForm.positionId.value = id;
    document.positionForm.action = "../position/PositionAction.do";
    document.positionForm.submit();
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
    if (checkPosition())
    {
        document.getElementById('submitdiv').innerHTML = "<img src='../assets/images/loading.gif' align='absmiddle' />";
        document.positionForm.doSave.value = "yes";
        document.positionForm.doCancel.value = "no";
        document.positionForm.action = "../position/PositionAction.do";
        document.positionForm.submit();
    }
}

function checkPosition()
{
    if (trim(document.positionForm.positiontitle.value) == "")
    {
        Swal.fire({
        title: "Please enter position title.",
        didClose:() => {
          document.positionForm.positiontitle.focus();
        }
        })
        return false;
    }
    if (validdesc(document.positionForm.positiontitle) == false)
    {
        return false;
    }
    if (document.positionForm.assettypeId.value == "-1")
    {
        Swal.fire({
        title: "Please select asset type.",
        didClose:() => {
          document.positionForm.assettypeId.focus();
        }
        })
        return false;
    }
    if (document.positionForm.gradeId.value == "-1")
    {
        Swal.fire({
        title: "Please select rank.",
        didClose:() => {
          document.positionForm.gradeId.focus();
        }
        })
        return false;
    }
    return true;
}

function resetForm()
{
    document.positionForm.reset();
}

function deleteForm(userId, status, id)
{
    if (true)
    {
        var url = "../ajax/position/getinfo.jsp";
        var getstr = "";
        var httploc = getHTTPObject();
        var next_value = escape(document.positionForm.nextValue.value);
        var next_del = "-1";
        if (document.positionForm.nextDel)
            next_del = escape(document.positionForm.nextDel.value);
        var search_value = escape(document.positionForm.search.value);
        getstr += "nextValue=" + next_value;
        getstr += "&nextDel=" + next_del;
        getstr += "&search=" + search_value;
        getstr += "&status=" + status;
        getstr += "&deleteVal=" + userId;
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
    }
}

function exporttoexcel()
{
    document.positionForm.action = "../position/PositionExportAction.do";
    document.positionForm.submit();
}

function openTab(tp)
{
    if (tp == 1)
    {
        document.positionForm.doModify.value = "no";
        document.positionForm.doCancel.value = "no";
        if (document.positionForm.doView)
            document.positionForm.doView.value = "yes";
        if (document.positionForm.doBenefitsList)
            document.positionForm.doBenefitsList.value = "no";
        if (document.positionForm.doViewAssessmentList)
            document.positionForm.doViewAssessmentList.value = "no";
        document.positionForm.action = "../position/PositionAction.do";
        document.positionForm.submit();
    }
    if (tp == 2)
    {
        document.positionForm.doModify.value = "no";
        document.positionForm.doCancel.value = "no";
        if (document.positionForm.doView)
            document.positionForm.doView.value = "no";
        if (document.positionForm.doBenefitsList)
            document.positionForm.doBenefitsList.value = "yes";
        if (document.positionForm.doViewAssessmentList)
            document.positionForm.doViewAssessmentList.value = "no";
        document.positionForm.action = "../position/PositionAction.do";
        document.positionForm.submit();
    }
    if (tp == 3)
    {
        document.positionForm.doModify.value = "no";
        document.positionForm.doCancel.value = "no";
        if (document.positionForm.doView)
            document.positionForm.doView.value = "no";
        if (document.positionForm.doBenefitsList)
            document.positionForm.doBenefitsList.value = "no";
        if (document.positionForm.doViewAssessmentList)
            document.positionForm.doViewAssessmentList.value = "yes";
        document.positionForm.action = "../position/PositionAction.do";
        document.positionForm.submit();
    }
}

function addbenefitForm()
{
    document.positionForm.doAddBenefit.value = "yes";
    document.positionForm.action = "../position/PositionAction.do";
    document.positionForm.submit();
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
    if (checkPositionBenefit())
    {
        document.positionForm.doSaveBenefit.value = "yes";
        document.positionForm.action = "../position/PositionAction.do";
        document.positionForm.submit();
    }
}

function checkPositionBenefit()
{
    if (trim(document.positionForm.benefitname.value) == "")
    {
        Swal.fire({
        title: "Please enter title.",
        didClose:() => {
          document.positionForm.benefitname.focus();
        }
        }) 
        return false;
    }
    if (validdesc(document.positionForm.benefitname) == false)
    {
        return false;
    }

    if (document.positionForm.benefittypeId.value == "-1")
    {
        Swal.fire({
        title: "Please select type.",
        didClose:() => {
          document.positionForm.benefittypeId.focus();
        }
        }) 
        return false;
    }
    return true;
}

function deleteBenefitForm(id, status)
{
    var s = "";
    if(eval(status) == 1)
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
            document.positionForm.doDeleteBenefit.value = "yes";
            document.positionForm.positionBenefitId.value = id;
            document.positionForm.statusBenifit.value = status;
            document.positionForm.action = "../position/PositionAction.do";
            document.positionForm.submit();
        }
        else
        {
        if (document.getElementById("flexSwitchCheckDefault_" + id).checked == true)
            document.getElementById("flexSwitchCheckDefault_" + id).checked = false;
        else
            document.getElementById("flexSwitchCheckDefault_" + id).checked = true;
        }
    });
}


function modifybenefitForm()
{
    document.positionForm.doModifyBenefit.value = "yes";
    document.positionForm.action = "../position/PositionAction.do";
    document.positionForm.submit();
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
        document.positionForm.doSavePosBenefit.value = "yes";
        document.positionForm.action = "../position/PositionAction.do";
        document.positionForm.submit();
    }
}

function addAssessmentForm(id)
{
    document.positionForm.assessmentDetailId.value = id;
    document.positionForm.doModifyAssessmentDetail.value = "yes";
    document.positionForm.action = "../position/PositionAction.do";
    document.positionForm.submit();
}

function deleteAssessmentForm(id, status)
{
    var s = "";
    if(eval(status) == 1)
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
            document.positionForm.doDeleteAssessmentDetail.value = "yes";
            document.positionForm.assessmentDetailId.value = id;
            document.positionForm.status.value = status;
            document.positionForm.doModifyAssessmentDetail.value = "no";
            document.positionForm.action = "../position/PositionAction.do";
            document.positionForm.submit();
        }
        else
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
        document.positionForm.doSaveAssessmentDetail.value = "yes";
        document.positionForm.doModifyAssessmentDetail.value = "no";
        document.positionForm.doCancel.value = "no";
        document.positionForm.action = "../position/PositionAction.do";
        document.positionForm.submit();
    }
}

function checkAssessmentDetail()
{
    if (document.positionForm.assessmentId.value == "-1")
    {
        Swal.fire({
        title: "Please select assessment.",
        didClose:() => {
          document.positionForm.assessmentId.focus();
        }
        }) 
        return false;
    }
    if (trim(document.positionForm.minScore.value) == "")
    {
        Swal.fire({
        title: "Please enter minimum score.",
        didClose:() => {
          document.positionForm.minScore.focus();
        }
        }) 
        return false;
    }
    if (validnum(document.positionForm.minScore) == false)
    {
        return false;
    }
    return true;
}

function setidname()
{
    if (document.forms[0].assessmentId.value != "-1")
    {
        var url = "../ajax/position/setidname.jsp";
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
    var url = "../ajax/position/assessments.jsp";
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