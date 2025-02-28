function viewprofile()
{
    document.forms[0].target = "_blank";
    document.managetrainingForm.doView.value = "yes";
    document.managetrainingForm.doSearch.value = "no";
    document.managetrainingForm.action = "../talentpool/TalentpoolAction.do";
    document.managetrainingForm.submit();
}

function viewcandidate(candidateId)
{
    document.managetrainingForm.target = "_blank";
    document.managetrainingForm.doView.value = "yes";
    document.managetrainingForm.doSearch.value = "no";
    document.managetrainingForm.candidateId.value = candidateId;
    document.managetrainingForm.action = "../talentpool/TalentpoolAction.do";
    document.managetrainingForm.submit();
}

function resetFilter1()
{
    document.forms[0].search.value = "";
    document.managetrainingForm.positionIdIndex.value = "-1";
    document.managetrainingForm.mode.value = "1";
    searchForm();
}

function resetFilter2()
{
    document.forms[0].search.value = "";
    document.managetrainingForm.categoryIdIndex.value = "-1";
    document.managetrainingForm.subcategoryIdIndex.value = "-1";
    document.managetrainingForm.mode.value = "2"
    searchForm();
}

function searchForm()
{
    document.forms[0].target = "_self";
    document.managetrainingForm.doSearch.value = "yes";
    document.managetrainingForm.doSearchAsset.value = "no";
    document.managetrainingForm.action = "../managetraining/ManagetrainingAction.do";
    document.managetrainingForm.submit();
}

function searchFormAsset()
{
    document.forms[0].target = "_self";
    document.managetrainingForm.doSearchAsset.value = "yes";
    document.managetrainingForm.doSearch.value = "no";
    document.managetrainingForm.action = "../managetraining/ManagetrainingAction.do";
    document.managetrainingForm.submit();
}

function assign1(id, positionId)
{
    document.forms[0].target = "_self";
    document.managetrainingForm.candidateId.value = id;
    document.managetrainingForm.positionId.value = positionId;
    document.managetrainingForm.doAssign1.value = "yes";
    document.managetrainingForm.doSearchAsset.value = "no";
    document.managetrainingForm.doSearch.value = "no";
    document.managetrainingForm.action = "../managetraining/ManagetrainingAction.do";
    document.managetrainingForm.submit();
}

function assign2(id)
{
    document.forms[0].target = "_self";
    document.managetrainingForm.courseId.value = id;
    document.managetrainingForm.doAssign2.value = "yes";
    document.managetrainingForm.doSearchAsset.value = "no";
    document.managetrainingForm.doSearch.value = "no";
    document.managetrainingForm.action = "../managetraining/ManagetrainingAction.do";
    document.managetrainingForm.submit();
}

function handleKeySearch(e)
{
    var key = e.keyCode || e.which;
    if (key === 13)
    {
        e.preventDefault();
        searchForm();
    }
}

function goback()
{
    document.forms[0].target = "_self";
    if (document.managetrainingForm.doCancel)
        document.managetrainingForm.doCancel.value = "yes";
    if (document.managetrainingForm.doAssignSave1)
        document.managetrainingForm.doAssignSave1.value = "no";
    if (document.managetrainingForm.doAssignSave2)
        document.managetrainingForm.doAssignSave2.value = "no";
    if (document.managetrainingForm.doSearch)
        document.managetrainingForm.doSearch.value = "no";
    document.managetrainingForm.action = "../managetraining/ManagetrainingAction.do";
    document.managetrainingForm.submit();
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
    var url_sort = "../ajax/managetraining/sort.jsp";
    var getstr = "";
    var nextValue = 0;
    if (document.managetrainingForm.nextValue)
        nextValue = document.managetrainingForm.nextValue.value;
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
    document.managetrainingForm.reset();
}

function exporttoexcel(exptype)
{
    document.forms[0].target = "_blank";
    document.managetrainingForm.action = "../managetraining/ManagetrainingExportAction.do?exptype="+exptype;
    document.managetrainingForm.submit();
}

function setAssetDDL()
{
    var url = "../ajax/managetraining/getasset.jsp";
    document.getElementById("assetIdIndex").value = '-1';
    var httploc = getHTTPObject();
    var getstr = "clientIdIndex=" + document.managetrainingForm.clientIdIndex.value + "&from=asset";
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

function setSubcategory()
{
    var url = "../ajax/managetraining/getsubcategory.jsp";
    document.getElementById("subcategoryIdIndex").value = '-1';
    var httploc = getHTTPObject();
    var getstr = "categoryIdIndex=" + document.managetrainingForm.categoryIdIndex.value;
    getstr += "&assetIdIndex="+document.managetrainingForm.assetIdIndex.value;
    httploc.open("POST", url, true);
    httploc.onreadystatechange = function ()
    {
        if (httploc.readyState == 4)
        {
            if (httploc.status == 200)
            {
                var response = httploc.responseText;
                document.getElementById("subcategoryIdIndex").innerHTML = '';
                document.getElementById("subcategoryIdIndex").innerHTML = response;
                searchForm();
            }
        }
    };
    httploc.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    httploc.send(getstr);
}

function sortForm2(colid, updown)
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
    var url_sort = "../ajax/managetraining/sort2.jsp";
    var getstr = "";
    var nextValue = 0;
    if (document.managetrainingForm.nextValue)
        nextValue = document.managetrainingForm.nextValue.value;
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

function setSubcategorydetail()
{
    var url = "../ajax/managetraining/getsubcategory.jsp";
    document.getElementById("subcategoryIdDetail").value = '-1';
    var httploc = getHTTPObject();
    var getstr = "categoryIdIndex=" + document.managetrainingForm.categoryIdDetail.value;
    getstr += "&assetIdIndex="+document.managetrainingForm.assetIdIndex.value;
    httploc.open("POST", url, true);
    httploc.onreadystatechange = function ()
    {
        if (httploc.readyState == 4)
        {
            if (httploc.status == 200)
            {
                var response = httploc.responseText;
                document.getElementById("subcategoryIdDetail").innerHTML = '';
                document.getElementById("subcategoryIdDetail").innerHTML = response;
                searchFormDetail();
            }
        }
    };
    httploc.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    httploc.send(getstr);
}

function searchFormDetail()
{    
    var url = "../ajax/managetraining/searchdetail.jsp";
    var httploc = getHTTPObject();
    var getstr = "categoryIdDetail=" + document.managetrainingForm.categoryIdDetail.value;
    getstr += "&subcategoryIdDetail="+document.managetrainingForm.subcategoryIdDetail.value;
    getstr += "&ftype="+document.managetrainingForm.ftype.value;
    getstr += "&crewrotationId="+document.managetrainingForm.crewrotationId.value;
    getstr += "&clientIdIndex="+document.managetrainingForm.clientIdIndex.value;
    getstr += "&assetIdIndex="+document.managetrainingForm.assetIdIndex.value;
    getstr += "&positionId="+document.managetrainingForm.positionId.value;
    getstr += "&search="+escape(document.managetrainingForm.searchdetail.value);
    getstr += "&statusIndex="+escape(document.managetrainingForm.statusIndex.value);
    httploc.open("POST", url, true);
    httploc.onreadystatechange = function ()
    {
        if (httploc.readyState == 4)
        {
            if (httploc.status == 200)
            {
                var response = httploc.responseText;
                document.getElementById("detaildiv").innerHTML = '';
                document.getElementById("detaildiv").innerHTML = response;
            }
        }
    };
    httploc.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    httploc.send(getstr);
}

function setallcb()
{    
    var ct = 0;
    if(document.getElementById("coursecball").checked == true)
    {
        if(document.managetrainingForm.coursecb)
        {
            if(document.managetrainingForm.coursecb.length)
            {
                for(var i = 0; i < document.managetrainingForm.coursecb.length; i++)
                {
                    document.managetrainingForm.coursecb[i].checked = true;
                    ct++;
                }
            }
            else
            {
                document.managetrainingForm.coursecb.checked = true;
                ct++;
            }
        }
    }
    else
    {
        if(document.managetrainingForm.coursecb)
        {
            if(document.managetrainingForm.coursecb.length)
            {
                for(var i = 0; i < document.managetrainingForm.coursecb.length; i++)
                    document.managetrainingForm.coursecb[i].checked = false;
            }
            else
            {
                document.managetrainingForm.coursecb.checked = false;
            }
        }
    }
    if(document.getElementById("assignhref"))
    {
        if(Number(ct) > 0)
        {
            document.getElementById("assignhref").href="javascript: assignall();";
            document.getElementById("assignhref").className = "assign_training active_btn";
        }
        else
        {
            document.getElementById("assignhref").href="javascript:;";
            document.getElementById("assignhref").className = "assign_training inactive_btn";
        }
    }
}

function setcb()
{    
    var ct = 0;
    if(document.managetrainingForm.coursecb)
    {
        if(document.managetrainingForm.coursecb.length)
        {
            for(var i = 0; i < document.managetrainingForm.coursecb.length; i++)
            {
                if(document.managetrainingForm.coursecb[i].checked == true)
                    ct++;
            }
        }
        else
        {
            if(document.managetrainingForm.coursecb.checked == true)
                ct++;
        }
    }
    if(document.getElementById("assignhref"))
    {
        if(Number(ct) > 0)
        {
            document.getElementById("assignhref").href="javascript: assignall();";
            document.getElementById("assignhref").className = "assign_training active_btn";
        }
        else
        {
            document.getElementById("assignhref").href="javascript:;";
            document.getElementById("assignhref").className = "assign_training inactive_btn";
        }
    }
}

function setfilter(tp)
{
    document.managetrainingForm.ftype.value=tp;
    document.getElementById("spansel-1").className = "round_circle circle_courses";
    document.getElementById("spansel1").className = "round_circle circle_unassigned";
    document.getElementById("spansel2").className = "round_circle circle_pending";
    document.getElementById("spansel3").className = "round_circle circle_complete";
    document.getElementById("spansel4").className = "round_circle circle_exipry";
    document.getElementById("spansel5").className = "round_circle circle_exipry";
    document.getElementById("spansel6").className = "round_circle circle_exipry";
    document.getElementById("spansel7").className = "round_circle circle_exipred";
    if(tp == -1)
        document.getElementById("spansel-1").className = "round_circle circle_courses selected_circle";
    else if(tp == 1)
        document.getElementById("spansel1").className = "round_circle circle_unassigned selected_circle";
    else if(tp == 2)
        document.getElementById("spansel2").className = "round_circle circle_pending selected_circle";
    else if(tp == 3)
        document.getElementById("spansel3").className = "round_circle circle_complete selected_circle";
    else if(tp == 4)
        document.getElementById("spansel4").className = "round_circle circle_exipry selected_circle";
    else if(tp == 5)
        document.getElementById("spansel5").className = "round_circle circle_exipry selected_circle";
    else if(tp == 6)
        document.getElementById("spansel6").className = "round_circle circle_exipry selected_circle";
    else if(tp == 6)
        document.getElementById("spansel7").className = "round_circle circle_exipred selected_circle";
    document.getElementById("togid").className = "usefool_tool toggled-off_02";
    searchFormDetail();
}

function setfilter2(tp)
{
    document.managetrainingForm.ftype.value=tp;
    document.getElementById("spansel-1").className = "round_circle circle_courses";
    document.getElementById("spansel1").className = "round_circle circle_unassigned";
    document.getElementById("spansel2").className = "round_circle circle_pending";
    document.getElementById("spansel3").className = "round_circle circle_complete";
    document.getElementById("spansel4").className = "round_circle circle_exipry";
    document.getElementById("spansel5").className = "round_circle circle_exipry";
    document.getElementById("spansel6").className = "round_circle circle_exipry";
    document.getElementById("spansel7").className = "round_circle circle_exipred";
    if(tp == -1)
        document.getElementById("spansel-1").className = "round_circle circle_courses selected_circle";
    else if(tp == 1)
        document.getElementById("spansel1").className = "round_circle circle_unassigned selected_circle";
    else if(tp == 2)
        document.getElementById("spansel2").className = "round_circle circle_pending selected_circle";
    else if(tp == 3)
        document.getElementById("spansel3").className = "round_circle circle_complete selected_circle";
    else if(tp == 4)
        document.getElementById("spansel4").className = "round_circle circle_exipry selected_circle";
    else if(tp == 5)
        document.getElementById("spansel5").className = "round_circle circle_exipry selected_circle";
    else if(tp == 6)
        document.getElementById("spansel6").className = "round_circle circle_exipry selected_circle";
    else if(tp == 6)
        document.getElementById("spansel7").className = "round_circle circle_exipred selected_circle";
    document.getElementById("togid").className = "usefool_tool toggled-off_02";
    searchFormDetail2();
}

function searchFormDetail2()
{    
    var url = "../ajax/managetraining/searchdetail2.jsp";
    var httploc = getHTTPObject();
    var getstr = "ftype="+document.managetrainingForm.ftype.value;
    getstr += "&courseId="+document.managetrainingForm.courseId.value;
    getstr += "&clientIdIndex="+document.managetrainingForm.clientIdIndex.value;
    getstr += "&assetIdIndex="+document.managetrainingForm.assetIdIndex.value;
    getstr += "&search="+escape(document.managetrainingForm.searchdetail.value);
    getstr += "&statusIndex="+escape(document.managetrainingForm.statusIndex.value);
    getstr += "&positionId2Index="+escape(document.managetrainingForm.positionId2Index.value);
    httploc.open("POST", url, true);
    httploc.onreadystatechange = function ()
    {
        if (httploc.readyState == 4)
        {
            if (httploc.status == 200)
            {
                var response = httploc.responseText;
                document.getElementById("detaildiv").innerHTML = '';
                document.getElementById("detaildiv").innerHTML = response;
            }
        }
    };
    httploc.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    httploc.send(getstr);
}

function setallcb2()
{    
    var ct = 0;
    if(document.getElementById("coursecball").checked == true)
    {
        if(document.managetrainingForm.coursecb)
        {
            if(document.managetrainingForm.coursecb.length)
            {
                for(var i = 0; i < document.managetrainingForm.coursecb.length; i++)
                {
                    document.managetrainingForm.coursecb[i].checked = true;
                    ct++;
                }
            }
            else
            {
                document.managetrainingForm.coursecb.checked = true;
                ct++;
            }
        }
    }
    else
    {
        if(document.managetrainingForm.coursecb)
        {
            if(document.managetrainingForm.coursecb.length)
            {
                for(var i = 0; i < document.managetrainingForm.coursecb.length; i++)
                    document.managetrainingForm.coursecb[i].checked = false;
            }
            else
            {
                document.managetrainingForm.coursecb.checked = false;
            }
        }
    }
    if(Number(ct) > 0)
    {
        document.getElementById("assignhref").href="javascript: assignall2();";
        document.getElementById("assignhref").className = "assign_training active_btn";
    }
    else
    {
        document.getElementById("assignhref").href="javascript:;";
        document.getElementById("assignhref").className = "assign_training inactive_btn";
    }
}

function setcb2()
{    
    var ct = 0;
    if(document.managetrainingForm.coursecb)
    {
        if(document.managetrainingForm.coursecb.length)
        {
            for(var i = 0; i < document.managetrainingForm.coursecb.length; i++)
            {
                if(document.managetrainingForm.coursecb[i].checked == true)
                    ct++;
            }
        }
        else
        {
            if(document.managetrainingForm.coursecb.checked == true)
                ct++;
        }
    }
    if(Number(ct) > 0)
    {
        document.getElementById("assignhref").href="javascript: assignall2();";
        document.getElementById("assignhref").className = "assign_training active_btn";
    }
    else
    {
        document.getElementById("assignhref").href="javascript:;";
        document.getElementById("assignhref").className = "assign_training inactive_btn";
    }
}

function gotomode()
{
    document.forms[0].target = "_self";
    document.managetrainingForm.mode.value=document.managetrainingForm.mode_top.value;
    if (document.managetrainingForm.doSearch)
        document.managetrainingForm.doSearch.value = "yes";
    if (document.managetrainingForm.doCancel)
        document.managetrainingForm.doCancel.value = "no";
    if (document.managetrainingForm.doAssignSave1)
        document.managetrainingForm.doAssignSave1.value = "no";
    if (document.managetrainingForm.doAssignSave2)
        document.managetrainingForm.doAssignSave2.value = "no";
    document.managetrainingForm.action = "../managetraining/ManagetrainingAction.do";
    document.managetrainingForm.submit();
}

function setPersonalModal(candidateId,clientmatrixdetailId,typefrom)
{
    if(candidateId == -1)
        candidateId = document.managetrainingForm.candidateId.value;
    var url = "../ajax/managetraining/getpersonalmodal.jsp";
    var httploc = getHTTPObject();
    var getstr = "candidateId=" + candidateId;
    getstr += "&clientmatrixdetailId=" + clientmatrixdetailId;
    getstr += "&typefrom=" + typefrom;
    httploc.open("POST", url, true);
    httploc.onreadystatechange = function ()
    {
        if (httploc.readyState == 4)
        {
            if (httploc.status == 200)
            {
                var response = httploc.responseText;
                document.getElementById("personalmodal").innerHTML = '';
                document.getElementById("personalmodal").innerHTML = response;
                
                jQuery(document).ready(function () {
                    $(".kt-selectpicker").selectpicker();
                    $(".wesl_dt").datepicker({
                        todayHighlight: !0,
                        format: "dd-M-yyyy",
                        autoclose: "true",
                        orientation: "auto"
                    });
                });
                
                $(function(){
                    $("#upload_link_1").on('click', function (e) {
                        e.preventDefault();
                        $("#upload1:hidden").trigger('click');
                    });

                });
                changestype();
            }
        }
    };
    httploc.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    httploc.send(getstr);
}

function changestype()
{
    if(Number(document.forms[0].stype.value == 2))
    {
        document.getElementById("completeId").style.display = "none";
        document.getElementById("assignId").style.display = "";
        document.getElementById("reltrainId").style.display = "";
        document.getElementById("remarksId").style.display = "none";
        document.getElementById("descriId").style.display = "";
        document.getElementById("stype"). className = "assign form-select";
        
    }
    else if(Number(document.forms[0].stype.value == 3))
    {
        document.getElementById("completeId").style.display = "";
        document.getElementById("assignId").style.display = "none";
        document.getElementById("reltrainId").style.display = "none";
        document.getElementById("remarksId").style.display = "";
        document.getElementById("descriId").style.display = "none";
        document.getElementById("stype"). className = "complete form-select";
    }
    else
    {
        document.getElementById("completeId").style.display = "none";
        document.getElementById("assignId").style.display = "none";
        document.getElementById("reltrainId").style.display = "none";
        document.getElementById("remarksId").style.display = "none";
        document.getElementById("descriId").style.display = "none";
        document.getElementById("stype"). className = "form-select";
    }
}

function savePersonalmodal()
{
    if(checkpersonal())
    {
        document.forms[0].target = "_self";
        var candidateId = document.managetrainingForm.candidateId.value;
        if (document.managetrainingForm.doAssignSave1)
            document.managetrainingForm.doAssignSave1.value = "no";
        if (document.managetrainingForm.doSavepersonal)
            document.managetrainingForm.doSavepersonal.value = "yes";
        document.managetrainingForm.action = "../managetraining/ManagetrainingAction.do";
        document.managetrainingForm.submit();
    } 
}

function checkpersonal()
{
      if(Number(document.managetrainingForm.stype.value == -1)){
          Swal.fire({
            title: "Please select action.",
            didClose: () => {
                document.managetrainingForm.stype.focus();
            }
        })
        return false;
      }
    if(document.managetrainingForm.stype.value == 3){
    if (document.managetrainingForm.fromdate.value == "")
    {
        Swal.fire({
            title: "Please select fromdate.",
            didClose: () => {
                document.managetrainingForm.fromdate.focus();
            }
        })
        return false;
    }
    if(document.managetrainingForm.cbtodate.checked == false){
    if (document.managetrainingForm.todate.value == "")
    {
        Swal.fire({
            title: "Please select todate.",
            didClose: () => {
                document.managetrainingForm.todate.focus();
            }
        })
        return false;
    }
}
    if (document.managetrainingForm.attachment.value == "")
    {
        Swal.fire({
                title: "Please upload certificate.",
                didClose: () => {
                    document.managetrainingForm.attachment.focus();
                }
            })
            return false;
    }
    if (document.managetrainingForm.attachment.value != "")
    {
        if (!(document.managetrainingForm.attachment.value).match(/(\.(png)|(jpeg)|(pdf))$/i))
        {
            Swal.fire({
                title: "Only .jpeg, .png, .pdf are allowed.",
                didClose: () => {
                    document.managetrainingForm.attachment.focus();
                }
            })
            return false;
        }
        var input = document.managetrainingForm.attachment;
        if (input.files)
        {
            var file = input.files[0];
            if (file.size > 1024 * 1024 * 5)
            {
                Swal.fire({
                    title: "File size should not exceed 5 MB.",
                    didClose: () => {
                        document.managetrainingForm.attachment.focus();
                    }
                })
                return false;
            }
        }
    }
    }
    if(document.managetrainingForm.stype.value == 2){
    if (document.managetrainingForm.completeby.value == "")
    {
        Swal.fire({
            title: "Please enter completeby.",
            didClose: () => {
                document.managetrainingForm.completeby.focus();
            }
        })
        return false;
    }
}
    
    return true;
}

function openfiles(id)
{
    document.getElementById("personal_course_details_modal_01").style.zIndex = "1000";
    $('#view_resume_list').modal('show');
    viewimg(id);
}

function closeViewModel()
{
    document.getElementById("personal_course_details_modal_01").style.zIndex = "";
}

function viewimg(courseId)
{
    var url = "../ajax/managetraining/getimg.jsp";
    var httploc = getHTTPObject();
    var getstr = "courseId=" + courseId;
    httploc.open("POST", url, true);
    httploc.onreadystatechange = function ()
    {
        if (httploc.readyState == 4)
        {
            if (httploc.status == 200)
            {
                var response = httploc.responseText;
                document.getElementById('viewfilesdiv').innerHTML = '';
                document.getElementById('viewfilesdiv').innerHTML = response;

                $("#iframe").on("load", function () {
                    let head = $("#iframe").contents().find("head");
                    let css = '<style>img{margin: 0px auto; max-width:-webkit-fill-available;}</style>';
                    $(head).append(css);
                });

            }
        }
    };
    httploc.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    httploc.setRequestHeader("Content-length", getstr.length);
    httploc.setRequestHeader("Connection", "close");
    httploc.send(getstr);
    document.getElementById('viewfilesdiv').innerHTML = "<div><img src='../assets/images/loading.gif' align='absmiddle'/>Please wait. Loading...</div>";
}

function setIframe(uval)
{
    var url_v = "", classname = "";
    if (uval.includes(".doc") || uval.includes(".docx"))
    {
        url_v = "https://docs.google.com/gview?url=" + uval + "&embedded=true";
        classname = "doc_mode";
    } 
    else if(uval.includes(".ppt") || uval.includes(".pptx") || uval.includes(".presentation"))
    {
        url_v = "https://view.officeapps.live.com/op/embed.aspx?src=" + uval + "&embedded=true";
        classname = "doc_mode";
    }
    else if (uval.includes(".pdf"))
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
    }, 1000);
}

function setPersonalModalassign()
{
    var crids = $("input[name='coursecb']:checked").map(function() {
                return this.value;
            }).get().join(',');
    if(crids != "")
    {
        var candidateId = document.managetrainingForm.candidateId.value;
        var positionId = document.managetrainingForm.positionId.value;
        var url = "../ajax/managetraining/getpersonalmodalassign.jsp";
        var httploc = getHTTPObject();
        var getstr = "candidateId=" + candidateId;
        getstr += "&crids=" + crids;
        getstr += "&positionId=" + positionId;
        httploc.open("POST", url, true);
        httploc.onreadystatechange = function ()
        {
            if (httploc.readyState == 4)
            {
                if (httploc.status == 200)
                {
                    var response = httploc.responseText;
                    document.getElementById("personalmodalassign").innerHTML = '';
                    document.getElementById("personalmodalassign").innerHTML = response;

                    jQuery(document).ready(function () {
                        $(".kt-selectpicker").selectpicker();
                        $(".wesl_dt").datepicker({
                            todayHighlight: !0,
                            format: "dd-M-yyyy",
                            autoclose: "true",
                            orientation: "auto"
                        });
                    });

                    $(function(){
                        $("#upload_link_1").on('click', function (e) {
                            e.preventDefault();
                            $("#upload1:hidden").trigger('click');
                        });

                    });
                }
            }
        };
        httploc.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
        httploc.send(getstr);
    }
    else
    {
        Swal.fire("Please select course(s).");
    }
}

function savePersonalmodalassign()
{
    if(checkpersonalassign())
    {
        document.forms[0].target = "_self";
        if (document.managetrainingForm.doAssignSave1)
            document.managetrainingForm.doAssignSave1.value = "no";
        if (document.managetrainingForm.doSavepersonal)
            document.managetrainingForm.doSavepersonal.value = "no";
        if (document.managetrainingForm.doSavepersonalassign)
            document.managetrainingForm.doSavepersonalassign.value = "yes";
        document.managetrainingForm.action = "../managetraining/ManagetrainingAction.do";
        document.managetrainingForm.submit();
    } 
}

function checkpersonalassign()
{
    if (document.managetrainingForm.completeby.value == "")
    {
        Swal.fire({
            title: "Please enter completeby.",
            didClose: () => {
                document.managetrainingForm.completeby.focus();
            }
        })
        return false;
    }
    return true;
}

function setCourseModalassign()
{
    var crids = $("input[name='coursecb']:checked").map(function() {
                return this.value;
            }).get().join(',');
    if(crids != "")
    {
        var candidateId = document.managetrainingForm.candidateId.value;
        var courseId = document.managetrainingForm.courseId.value;
        var url = "../ajax/managetraining/getcoursemodalassign.jsp";
        var httploc = getHTTPObject();
        var getstr = "courseId=" + courseId;
        getstr += "&crids=" + crids;
        httploc.open("POST", url, true);
        httploc.onreadystatechange = function ()
        {
            if (httploc.readyState == 4)
            {
                if (httploc.status == 200)
                {
                    var response = httploc.responseText;
                    document.getElementById("coursemodalassign").innerHTML = '';
                    document.getElementById("coursemodalassign").innerHTML = response;

                    jQuery(document).ready(function () {
                        $(".kt-selectpicker").selectpicker();
                        $(".wesl_dt").datepicker({
                            todayHighlight: !0,
                            format: "dd-M-yyyy",
                            autoclose: "true",
                            orientation: "auto"
                        });
                    });

                    $(function(){
                        $("#upload_link_1").on('click', function (e) {
                            e.preventDefault();
                            $("#upload1:hidden").trigger('click');
                        });

                    });
                }
            }
        };
        httploc.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
        httploc.send(getstr);
    }
    else
    {
        Swal.fire("Please select personnel(s).");
    }
}

function saveCoursemodalassign()
{
    if(checkCourseassign())
    {
        document.forms[0].target = "_self";
        if (document.managetrainingForm.doAssignSave1)
            document.managetrainingForm.doAssignSave1.value = "no";
        if (document.managetrainingForm.doSavepersonal)
            document.managetrainingForm.doSavepersonal.value = "no";
        if (document.managetrainingForm.doSavepersonalassign)
            document.managetrainingForm.doSavepersonalassign.value = "no";
        if (document.managetrainingForm.doSavecourseassign)
            document.managetrainingForm.doSavecourseassign.value = "yes";
        document.managetrainingForm.action = "../managetraining/ManagetrainingAction.do";
        document.managetrainingForm.submit();
    } 
}

function checkCourseassign()
{
    if (document.managetrainingForm.completeby.value == "")
    {
        Swal.fire({
            title: "Please enter completeby.",
            didClose: () => {
                document.managetrainingForm.completeby.focus();
            }
        })
        return false;
    }
    return true;
}

function setCoursemail(typefrom)
{
    var url = "../ajax/managetraining/getcoursemailmodal.jsp";
    var httploc = getHTTPObject();
    var getstr = "";
    getstr += "typefrom=" + typefrom;
    httploc.open("POST", url, true);
    httploc.onreadystatechange = function ()
    {
        if (httploc.readyState == 4)
        {
            if (httploc.status == 200)
            {
                var response = httploc.responseText;
                document.getElementById("coursemailmodal").innerHTML = '';
                document.getElementById("coursemailmodal").innerHTML = response;                
            }
        }
    };
    httploc.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    httploc.send(getstr);
}

function submitcoursemailForm()
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
    if (checkcourseMail())
    {
        document.getElementById("dsavesendcoursemail").innerHTML = "<div><img src='../assets/images/loading.gif' align='absmiddle'/>Please wait. Loading...</div>";
        document.forms[0].target = "_self";
        if (document.managetrainingForm.doMail)
            document.managetrainingForm.doMail.value = "yes";
        document.managetrainingForm.action = "../managetraining/ManagetrainingAction.do";
        document.managetrainingForm.submit();
    }
}

function checkcourseMail()
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

function viewCoursecat(Id,subId)
{    
    document.forms[0].categoryId.value=Id;
    document.forms[0].subcategoryId.value=subId;
    document.forms[0].doIndexCourse.value="yes";
     document.forms[0].target = "_blank";
    document.forms[0].action="../createtraining/CreatetrainingAction.do";
    document.forms[0].submit();
}

function closeModal()
{
     $('#thank_you_modal').modal('hide');
}

function setClass(tp)
{
    document.getElementById("upload_link_" + tp).className = "attache_btn uploaded_img";
}