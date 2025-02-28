function resetFilter()
{
    document.forms[0].search.value = "";
    document.trainingmatrixForm.clientIdIndex.value = "-1";
    document.trainingmatrixForm.assetIdIndex.value = "-1";
    setAssetDDL();
    searchFormAjax('s', '-1');
}

function showDetail(clientId, assetId)
{
    document.forms[0].target = "_self";
    document.trainingmatrixForm.doView.value = "yes";
    document.trainingmatrixForm.clientId.value = clientId;
    document.trainingmatrixForm.assetId.value = assetId;
    document.trainingmatrixForm.action = "../trainingmatrix/TrainingmatrixAction.do";
    document.trainingmatrixForm.submit();
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
        var url = "../ajax/trainingmatrix/getinfo.jsp";
        var httploc = getHTTPObject();
        var getstr = "";
        var next_value = escape(document.trainingmatrixForm.nextValue.value);
        var search_value = escape(document.trainingmatrixForm.search.value);
        var assetIdIndex = escape(document.trainingmatrixForm.assetIdIndex.value);
        var clientIdIndex = escape(document.trainingmatrixForm.clientIdIndex.value);
        getstr += "nextValue=" + next_value;
        getstr += "&next=" + v;
        getstr += "&search=" + search_value;
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

function goback()
{
    if (document.trainingmatrixForm.doView)
        document.trainingmatrixForm.doView.value = "no";
    if (document.trainingmatrixForm.doCancel)
        document.trainingmatrixForm.doCancel.value = "yes";
    if (document.trainingmatrixForm.doSave)
        document.trainingmatrixForm.doSave.value = "no";
    if (document.trainingmatrixForm.doAssign)
        document.trainingmatrixForm.doAssign.value = "no";
    if (document.trainingmatrixForm.doDeleteDetail)
        document.trainingmatrixForm.doDeleteDetail.value = "no";
    document.trainingmatrixForm.action = "../trainingmatrix/TrainingmatrixAction.do";
    document.trainingmatrixForm.submit();
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
    var url_sort = "../ajax/trainingmatrix/sort.jsp";
    var getstr = "";
    var nextValue = 0;
    if (document.trainingmatrixForm.nextValue)
        nextValue = document.trainingmatrixForm.nextValue.value;
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
    document.trainingmatrixForm.reset();
}

function exporttoexcel()
{
    document.trainingmatrixForm.action = "../trainingmatrix/TrainingmatrixExportAction.do";
    document.trainingmatrixForm.submit();
}

function changeCategory()
{    
    if(Number(document.trainingmatrixForm.positionId.value) <= 0)
    {
        Swal.fire({
            title: "Please select position.",
            didClose: () => {
                document.trainingmatrixForm.positionId.focus();
            }
        })
    }
    if(Number(document.trainingmatrixForm.categoryId.value) <= 0)
    {
        Swal.fire({
            title: "Please select category.",
            didClose: () => {
                document.trainingmatrixForm.categoryId.focus();
            }
        })
    }
    else
    {
        document.forms[0].target = "_self";
        document.trainingmatrixForm.doCategory.value = "yes";
        if (document.trainingmatrixForm.doView)
            document.trainingmatrixForm.doView.value = "no";
        if (document.trainingmatrixForm.doSaveCourse)
            document.trainingmatrixForm.doSaveCourse.value = "no";
        document.trainingmatrixForm.action = "../trainingmatrix/TrainingmatrixAction.do";
        document.trainingmatrixForm.submit();
    }
}

function changeCategorychange()
{    
    if(Number(document.trainingmatrixForm.positionId.value) > 0 && Number(document.trainingmatrixForm.categoryId.value) > 0)
    {
        document.forms[0].target = "_self";
        document.trainingmatrixForm.doCategory.value = "yes";
        if (document.trainingmatrixForm.doView)
            document.trainingmatrixForm.doView.value = "no";
        if (document.trainingmatrixForm.doSaveCourse)
            document.trainingmatrixForm.doSaveCourse.value = "no";
        document.trainingmatrixForm.action = "../trainingmatrix/TrainingmatrixAction.do";
        document.trainingmatrixForm.submit();
    }
}

function setSubcat(size)
{
    if (document.getElementById("catcb").checked)
    {
        for (var i = 1; i <= size; i++)
        {
            document.getElementById("subcategorycb_" + i).checked = true;
            var subcategoryId = document.getElementById("subcategorycb_" + i).value;
            setcourse(subcategoryId, i, document.getElementById("coursesize_" + subcategoryId).value);
        }
    } else
    {
        for (var i = 1; i <= size; i++)
        {
            document.getElementById("subcategorycb_" + i).checked = false;
            var subcategoryId = document.getElementById("subcategorycb_" + i).value;
            setcourse(subcategoryId, i, document.getElementById("coursesize_" + subcategoryId).value);
        }
    }
}

function setcourse(subcategoryId, tp, size)
{
    if (document.getElementById("subcategorycb_" + tp).checked)
    {
        for (var i = 1; i <= size; i++)
        {
            document.getElementById("coursecb_" + subcategoryId + "_" + i).checked = true;
            document.getElementById("courseId_" + subcategoryId + "_" + i).value = document.getElementById("coursecb_" + subcategoryId + "_" + i).value;
            document.getElementById("subcategoryId_" + subcategoryId + "_" + i).value = subcategoryId;
        }
    } else
    {
        for (var i = 1; i <= size; i++)
        {
            document.getElementById("coursecb_" + subcategoryId + "_" + i).checked = false;
            document.getElementById("courseId_" + subcategoryId + "_" + i).value = "-1";
            document.getElementById("subcategoryId_" + subcategoryId + "_" + i).value = "-1";
            document.getElementById("tctypeIdddl_" + subcategoryId + "_" + i).value = "-1";
            document.getElementById("trainingIdddl_" + subcategoryId + "_" + i).value = "-1";
            document.getElementById("tctypeId_" + subcategoryId + "_" + i).value = "-1";
            document.getElementById("trainingId_" + subcategoryId + "_" + i).value = "-1";
        }
    }
}

function checkcourse()
{
    var x = 0;
    if (Number(document.trainingmatrixForm.positionId.value) <= 0)
    {
        Swal.fire({
            title: "Please select position.",
            didClose: () => {
                document.trainingmatrixForm.positionId.focus();
            }
        })
        return false;
    }
    if (Number(document.trainingmatrixForm.categoryId.value) <= 0)
    {
        Swal.fire({
            title: "Please select category.",
            didClose: () => {
                document.trainingmatrixForm.categoryId.focus();
            }
        })
        return false;
    }    
    if (document.trainingmatrixForm.courseId)
    {
        if (document.trainingmatrixForm.courseId.length)
        {
            for (var i = 0; i < document.trainingmatrixForm.courseId.length; i++)
            {                
                if (eval(document.trainingmatrixForm.courseId[i].value) > 0)
                {
                    x++;
                    if (eval(document.trainingmatrixForm.tctypeId[i].value) <= 0)
                    {
                        Swal.fire({
                            title: "Please select course type.",
                            didClose: () => {
                                document.trainingmatrixForm.tctypeIdddl[i].focus();
                            }
                        })
                        return false;
                    }
                    if (eval(document.trainingmatrixForm.trainingId[i].value) <= 0)
                    {
                        Swal.fire({
                            title: "Please select priority.",
                            didClose: () => {
                                document.trainingmatrixForm.trainingIdddl[i].focus();
                            }
                        })
                        return false;
                    }
                    if (eval(document.trainingmatrixForm.levelId[i].value) > 0)
                    {
                        if (eval(document.trainingmatrixForm.courseIdrel[i].value) <= 0)
                        {
                            Swal.fire({
                                title: "Please select relative training.",
                                didClose: () => {
                                    document.trainingmatrixForm.courseIdrelddl[i].focus();
                                }
                            })
                            return false;
                        }
                    }
                    if (eval(document.trainingmatrixForm.courseIdrel[i].value) > 0)
                    {
                        if (eval(document.trainingmatrixForm.levelId[i].value) <= 0)
                        {
                            Swal.fire({
                                title: "Please select level.",
                                didClose: () => {
                                    document.trainingmatrixForm.levelIdddl[i].focus();
                                }
                            })
                            return false;
                        }
                    }
                }
            }
        } else
        {
            if (eval(document.trainingmatrixForm.courseId.value) > 0)
            {
                x++;
                if (eval(document.trainingmatrixForm.tctypeId.value) <= 0)
                {
                    Swal.fire({
                        title: "Please select course type (trainingmatrix).",
                        didClose: () => {
                            document.trainingmatrixForm.tctypeIdddl.focus();
                        }
                    })
                    return false;
                }
                if (eval(document.trainingmatrixForm.trainingId.value) <= 0)
                {
                    Swal.fire({
                        title: "Please select minimum / optimum.",
                        didClose: () => {
                            document.trainingmatrixForm.trainingIdddl.focus();
                        }
                    })
                    return false;
                }
                if (eval(document.trainingmatrixForm.levelId.value) > 0)
                {
                    if (eval(document.trainingmatrixForm.courseIdrel.value) <= 0)
                    {
                        Swal.fire({
                            title: "Please select relative training.",
                            didClose: () => {
                                document.trainingmatrixForm.courseIdrelddl.focus();
                            }
                        })
                        return false;
                    }
                }                
                if (eval(document.trainingmatrixForm.courseIdrel.value) > 0)
                {
                    if (eval(document.trainingmatrixForm.levelId.value) <= 0)
                    {
                        Swal.fire({
                            title: "Please select level.",
                            didClose: () => {
                                document.trainingmatrixForm.levelIdddl.focus();
                            }
                        })
                        return false;
                    }
                }
            }
        }
    }
    if (Number(x) <= 0)
    {
        Swal.fire({
            title: "Please select atleast one course.",
            didClose: () => {
            }
        })
        return false;
    }
    return true;
}

function save()
{
    if (checkcourse())
    {
        document.forms[0].target = "_self";
        document.trainingmatrixForm.doCancel.value = "no";
        document.trainingmatrixForm.doCategory.value = "no";
        document.trainingmatrixForm.doView.value = "no";
        document.trainingmatrixForm.doSaveCourse.value = "yes";
        document.trainingmatrixForm.categoryIdHidden.value = document.trainingmatrixForm.categoryId.value;
        document.trainingmatrixForm.positionIdHidden.value = document.trainingmatrixForm.positionId.value;
        document.trainingmatrixForm.action = "../trainingmatrix/TrainingmatrixAction.do";
        document.trainingmatrixForm.submit();
    }
}

function setTraingKind(subcategoryId, tp)
{
    document.getElementById("tctypeId_" + subcategoryId + "_" + tp).value = document.getElementById("tctypeIdddl_" + subcategoryId + "_" + tp).value;
}

function setTraingType(subcategoryId, tp)
{
    document.getElementById("trainingId_" + subcategoryId + "_" + tp).value = document.getElementById("trainingIdddl_" + subcategoryId + "_" + tp).value;
}

function setlevel(subcategoryId, tp)
{
    document.getElementById("levelId_" + subcategoryId + "_" + tp).value = document.getElementById("levelIdddl_" + subcategoryId + "_" + tp).value;
}

function setcourseIdrel(subcategoryId, tp)
{
    document.getElementById("courseIdrel_" + subcategoryId + "_" + tp).value = document.getElementById("courseIdrelddl_" + subcategoryId + "_" + tp).value;
}

function setcoursehidden(subcategoryId, tp)
{
    if (document.getElementById("coursecb_" + subcategoryId + "_" + tp).checked)
    {
        document.getElementById("courseId_" + subcategoryId + "_" + tp).value = document.getElementById("coursecb_" + subcategoryId + "_" + tp).value;
    } else
    {
        document.getElementById("courseId_" + subcategoryId + "_" + tp).value = "-1";
        document.getElementById("tctypeIdddl_" + subcategoryId + "_" + tp).value = "-1";
        document.getElementById("trainingIdddl_" + subcategoryId + "_" + tp).value = "-1";
        document.getElementById("tctypeId_" + subcategoryId + "_" + tp).value = "-1";
        document.getElementById("trainingId_" + subcategoryId + "_" + tp).value = "-1";
    }
}

function setAssetDDL()
{
    var url = "../ajax/trainingmatrix/getasset.jsp";
    document.getElementById("assetIdIndex").value = '-1';
    var httploc = getHTTPObject();
    var getstr = "clientIdIndex=" + document.trainingmatrixForm.clientIdIndex.value + "&from=asset";
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
    searchFormAjax('s', '-1');
}

function viewasset()
{
    document.forms[0].target = "_blank";
    document.forms[0].doCancel.value = "no";
    document.forms[0].doView.value = "yes";
    document.forms[0].action = "../client/ClientAction.do?tabno=2";
    document.forms[0].submit();
}