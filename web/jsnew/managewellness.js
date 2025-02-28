function viewprofile()
{
    document.forms[0].target = "_blank";
    document.managewellnessForm.doView.value = "yes";
    document.managewellnessForm.doSearch.value = "no";
    document.managewellnessForm.action = "../talentpool/TalentpoolAction.do";
    document.managewellnessForm.submit();
}

function viewcandidate(candidateId)
{
    document.managewellnessForm.target = "_blank";
    document.managewellnessForm.doView.value = "yes";
    document.managewellnessForm.doSearch.value = "no";
    document.managewellnessForm.candidateId.value = candidateId;
    document.managewellnessForm.action = "../talentpool/TalentpoolAction.do";
    document.managewellnessForm.submit();
}

function viewSubcategory(categoryId)
{
    document.managewellnessForm.target = "_blank";
    document.managewellnessForm.categoryId.value=categoryId;
    document.managewellnessForm.doIndexSubcategory.value="yes";
    document.managewellnessForm.action="../wellnessfb/WellnessfbAction.do";
    document.managewellnessForm.submit();
}

function resetFilter1()
{
    document.forms[0].search.value = "";
    document.managewellnessForm.positionIdIndex.value = "-1";
    document.managewellnessForm.mode.value = "1";
    searchForm();
}

function resetFilter2()
{
    document.forms[0].search.value = "";
    document.managewellnessForm.categoryIdIndex.value = "-1";
    document.managewellnessForm.subcategoryIdIndex.value = "-1";
    document.managewellnessForm.mode.value = "2"
    searchForm();
}

function searchForm()
{
    document.forms[0].target = "_self";
    document.managewellnessForm.doSearch.value = "yes";
    document.managewellnessForm.doSearchAsset.value = "no";
    document.managewellnessForm.action = "../managewellness/ManagewellnessAction.do";
    document.managewellnessForm.submit();
}

function searchFormposition()
{
    document.forms[0].target = "_self";
    document.managewellnessForm.doAssign2.value = "yes";
    document.managewellnessForm.doSearch.value = "no";
    document.managewellnessForm.action = "../managewellness/ManagewellnessAction.do";
    document.managewellnessForm.submit();
}

function searchFormAsset()
{
    document.forms[0].target = "_self";
    document.managewellnessForm.doSearchAsset.value = "yes";
    document.managewellnessForm.doSearch.value = "no";
    document.managewellnessForm.action = "../managewellness/ManagewellnessAction.do";
    document.managewellnessForm.submit();
}

function assign1(id)
{
    document.forms[0].target = "_self";
    document.managewellnessForm.crewrotationId.value = id;
    document.managewellnessForm.doAssign1.value = "yes";
    document.managewellnessForm.doSearchAsset.value = "no";
    document.managewellnessForm.doSearch.value = "no";
    document.managewellnessForm.action = "../managewellness/ManagewellnessAction.do";
    document.managewellnessForm.submit();
}

function assign2(id)
{
    document.forms[0].target = "_self";
    document.managewellnessForm.subcategoryId.value = id;
    document.managewellnessForm.doAssign2.value = "yes";
    document.managewellnessForm.doSearchAsset.value = "no";
    document.managewellnessForm.doSearch.value = "no";
    document.managewellnessForm.action = "../managewellness/ManagewellnessAction.do";
    document.managewellnessForm.submit();
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
    if (document.managewellnessForm.doCancel)
        document.managewellnessForm.doCancel.value = "yes";
    if (document.managewellnessForm.doAssignSave1)
        document.managewellnessForm.doAssignSave1.value = "no";
    if (document.managewellnessForm.doAssignSave2)
        document.managewellnessForm.doAssignSave2.value = "no";
    if (document.managewellnessForm.doSearch)
        document.managewellnessForm.doSearch.value = "no";
    document.managewellnessForm.action = "../managewellness/ManagewellnessAction.do";
    document.managewellnessForm.submit();
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
    var url_sort = "../ajax/managewellness/sort.jsp";
    var getstr = "";
    var nextValue = 0;
    if (document.managewellnessForm.nextValue)
        nextValue = document.managewellnessForm.nextValue.value;
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
    document.managewellnessForm.reset();
}

function exporttoexcel(exptype)
{
    document.forms[0].target = "_blank";
    document.managewellnessForm.action = "../managewellness/ManagewellnessExportAction.do?exptype="+exptype;
    document.managewellnessForm.submit();
}

function setAssetDDL()
{
    var url = "../ajax/managewellness/getasset.jsp";
    document.getElementById("assetIdIndex").value = '-1';
    var httploc = getHTTPObject();
    var getstr = "clientIdIndex=" + document.managewellnessForm.clientIdIndex.value + "&from=asset";
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
    var url = "../ajax/managewellness/getsubcategory.jsp";
    document.getElementById("subcategoryIdIndex").value = '-1';
    var httploc = getHTTPObject();
    var getstr = "categoryIdIndex=" + document.managewellnessForm.categoryIdIndex.value;
    getstr += "&assetIdIndex="+document.managewellnessForm.assetIdIndex.value;
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
    var url_sort = "../ajax/managewellness/sort2.jsp";
    var getstr = "";
    var nextValue = 0;
    if (document.managewellnessForm.nextValue)
        nextValue = document.managewellnessForm.nextValue.value;
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
    var url = "../ajax/managewellness/getsubcategory.jsp";
    document.getElementById("subcategoryIdDetail").value = '-1';
    var httploc = getHTTPObject();
    var getstr = "categoryIdIndex=" + document.managewellnessForm.categoryIdDetail.value;
    getstr += "&assetIdIndex="+document.managewellnessForm.assetIdIndex.value;
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
    var url = "../ajax/managewellness/searchdetail.jsp";
    var httploc = getHTTPObject();
    var getstr = "clientIdIndex="+document.managewellnessForm.clientIdIndex.value;
    getstr += "&assetIdIndex="+document.managewellnessForm.assetIdIndex.value;
    getstr += "&positionId="+document.managewellnessForm.positionId.value;
    getstr += "&categoryIdDetail="+document.managewellnessForm.categoryIdDetail.value;
    getstr += "&subcategoryIdDetail="+document.managewellnessForm.subcategoryIdDetail.value;
    getstr += "&search="+escape(document.managewellnessForm.searchdetail.value);
    getstr += "&statusIndex="+escape(document.managewellnessForm.statusIndex.value);
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
    if(document.getElementById("subcategorycball").checked == true)
    {
        if(document.managewellnessForm.subcategorycb)
        {
            if(document.managewellnessForm.subcategorycb.length)
            {
                for(var i = 0; i < document.managewellnessForm.subcategorycb.length; i++)
                {
                    document.managewellnessForm.subcategorycb[i].checked = true;
                    ct++;
                }
            }
            else
            {
                document.managewellnessForm.subcategorycb.checked = true;
                ct++;
            }
        }
    }
    else
    {
        if(document.managewellnessForm.subcategorycb)
        {
            if(document.managewellnessForm.subcategorycb.length)
            {
                for(var i = 0; i < document.managewellnessForm.subcategorycb.length; i++)
                    document.managewellnessForm.subcategorycb[i].checked = false;
            }
            else
            {
                document.managewellnessForm.subcategorycb.checked = false;
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
    if(document.managewellnessForm.subcategorycb)
    {
        if(document.managewellnessForm.subcategorycb.length)
        {
            for(var i = 0; i < document.managewellnessForm.subcategorycb.length; i++)
            {
                if(document.managewellnessForm.subcategorycb[i].checked == true)
                    ct++;
            }
        }
        else
        {
            if(document.managewellnessForm.subcategorycb.checked == true)
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
    document.managewellnessForm.ftype.value=tp;
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
    document.managewellnessForm.ftype.value=tp;
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
    var url = "../ajax/managewellness/searchdetail2.jsp";
    var httploc = getHTTPObject();
    var getstr = "clientIdIndex="+document.managewellnessForm.clientIdIndex.value;
    getstr += "&assetIdIndex="+document.managewellnessForm.assetIdIndex.value;
    getstr += "&search="+escape(document.managewellnessForm.searchdetail.value);
    getstr += "&categoryIdIndex="+escape(document.managewellnessForm.categoryIdIndex.value);
    getstr += "&subcategoryIdIndex="+escape(document.managewellnessForm.subcategoryIdIndex.value);
    httploc.open("POST", url, true);
    httploc.onreadystatechange = function ()
    {
        if (httploc.readyState == 4)
        {
            if (httploc.status == 200)
            {
                var response = httploc.responseText;
                document.getElementById("sort_id").innerHTML = '';
                document.getElementById("sort_id").innerHTML = response;
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
        if(document.managewellnessForm.coursecb)
        {
            if(document.managewellnessForm.coursecb.length)
            {
                for(var i = 0; i < document.managewellnessForm.coursecb.length; i++)
                {
                    document.managewellnessForm.coursecb[i].checked = true;
                    ct++;
                }
            }
            else
            {
                document.managewellnessForm.coursecb.checked = true;
                ct++;
            }
        }
    }
    else
    {
        if(document.managewellnessForm.coursecb)
        {
            if(document.managewellnessForm.coursecb.length)
            {
                for(var i = 0; i < document.managewellnessForm.coursecb.length; i++)
                    document.managewellnessForm.coursecb[i].checked = false;
            }
            else
            {
                document.managewellnessForm.coursecb.checked = false;
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
    if(document.managewellnessForm.coursecb)
    {
        if(document.managewellnessForm.coursecb.length)
        {
            for(var i = 0; i < document.managewellnessForm.coursecb.length; i++)
            {
                if(document.managewellnessForm.coursecb[i].checked == true)
                    ct++;
            }
        }
        else
        {
            if(document.managewellnessForm.coursecb.checked == true)
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
    document.managewellnessForm.mode.value=document.managewellnessForm.mode_top.value;
    if (document.managewellnessForm.doSearch)
        document.managewellnessForm.doSearch.value = "yes";
    if (document.managewellnessForm.doCancel)
        document.managewellnessForm.doCancel.value = "no";
    if (document.managewellnessForm.doAssignSave1)
        document.managewellnessForm.doAssignSave1.value = "no";
    if (document.managewellnessForm.doAssignSave2)
        document.managewellnessForm.doAssignSave2.value = "no";
    document.managewellnessForm.action = "../managewellness/ManagewellnessAction.do";
    document.managewellnessForm.submit();
}

function setQuestionModal(subcategoryId,positionId,crewrotationId)
{
    if(crewrotationId < 0)
    {
        crewrotationId =  document.managewellnessForm.crewrotationId.value;
    }
        
    var url = "../ajax/managewellness/getQuestionmodal.jsp";
    var httploc = getHTTPObject();
    var getstr = "crewrotationId=" + crewrotationId;
    getstr += "&assetIdIndex=" + document.managewellnessForm.assetIdIndex.value;
    getstr += "&subcategoryId=" + subcategoryId;
    getstr += "&positionId=" + positionId;
    httploc.open("POST", url, true);
    httploc.onreadystatechange = function ()
    {
        if (httploc.readyState == 4)
        {
            if (httploc.status == 200)
            {
                var response = httploc.responseText;
                document.getElementById("questionmodal").innerHTML = '';
                document.getElementById("questionmodal").innerHTML = response;
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
        if (document.managewellnessForm.doAssignSave1)
            document.managewellnessForm.doAssignSave1.value = "no";
        if (document.managewellnessForm.doSavepersonal)
            document.managewellnessForm.doSavepersonal.value = "yes";
        document.managewellnessForm.action = "../managewellness/ManagewellnessAction.do";
        document.managewellnessForm.submit();
    } 
}

function checkpersonal()
{
      if(Number(document.managewellnessForm.stype.value == -1)){
          Swal.fire({
            title: "Please select action.",
            didClose: () => {
                document.managewellnessForm.stype.focus();
            }
        })
        return false;
      }
    if(document.managewellnessForm.stype.value == 3){
    if (document.managewellnessForm.fromdate.value == "")
    {
        Swal.fire({
            title: "Please select fromdate.",
            didClose: () => {
                document.managewellnessForm.fromdate.focus();
            }
        })
        return false;
    }
    if(document.managewellnessForm.cbtodate.checked == false){
    if (document.managewellnessForm.todate.value == "")
    {
        Swal.fire({
            title: "Please select todate.",
            didClose: () => {
                document.managewellnessForm.todate.focus();
            }
        })
        return false;
    }
}
    if (document.managewellnessForm.attachment.value == "")
    {
        Swal.fire({
                title: "Please upload certificate.",
                didClose: () => {
                    document.managewellnessForm.attachment.focus();
                }
            })
            return false;
    }
    if (document.managewellnessForm.attachment.value != "")
    {
        if (!(document.managewellnessForm.attachment.value).match(/(\.(png)|(jpeg)|(pdf))$/i))
        {
            Swal.fire({
                title: "Only .jpeg, .png, .pdf are allowed.",
                didClose: () => {
                    document.managewellnessForm.attachment.focus();
                }
            })
            return false;
        }
        var input = document.managewellnessForm.attachment;
        if (input.files)
        {
            var file = input.files[0];
            if (file.size > 1024 * 1024 * 5)
            {
                Swal.fire({
                    title: "File size should not exceed 5 MB.",
                    didClose: () => {
                        document.managewellnessForm.attachment.focus();
                    }
                })
                return false;
            }
        }
    }
    }
    if(document.managewellnessForm.stype.value == 2){
    if (document.managewellnessForm.completeby.value == "")
    {
        Swal.fire({
            title: "Please enter completeby.",
            didClose: () => {
                document.managewellnessForm.completeby.focus();
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
    var url = "../ajax/managewellness/getimg.jsp";
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

function saveQuestionmodal(subcategoryId,schedulecb)
{
    var crids = "0";
    if(subcategoryId > 0)
    {
        crids = subcategoryId;
    }
    else if(subcategoryId = - 1)
    {
        crids = $("input[name='subcategorycb']:checked").map(function() {
        return this.value;
        }).get().join(',');
    }
    var url = "../ajax/managewellness/saveQuestionmodal.jsp";
    var httploc = getHTTPObject();
    var getstr = "clientIdIndex=" + document.managewellnessForm.clientIdIndex.value;
    getstr += "&assetIdIndex=" + document.managewellnessForm.assetIdIndex.value;
    getstr += "&crids=" + crids;
    getstr += "&schedulecb=" + schedulecb;
    httploc.open("POST", url, true);
    httploc.onreadystatechange = function ()
    {
        if (httploc.readyState == 4)
        {
            if (httploc.status == 200)
            {
                var response = httploc.responseText;
                document.getElementById("questionmodalassign").innerHTML = '';
                document.getElementById("questionmodalassign").innerHTML = response;
                jQuery(document).ready(function () {
                $(".kt-selectpicker").selectpicker();
                        $(".wesl_dt").datepicker({
                todayHighlight: !0,
                        format: "dd-M-yyyy",
                        autoclose: "true",
                        orientation: "bottom"
                });
                });
            }
        }
    };
    httploc.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    httploc.send(getstr);
}

function deletedateForm(subcategoryId)
{
    document.managewellnessForm.subcategoryId.value = subcategoryId;
    document.forms[0].target = "_self";
    if (document.managewellnessForm.doDeletedate)
        document.managewellnessForm.doDeletedate.value = "yes";
    document.managewellnessForm.action = "../managewellness/ManagewellnessAction.do";
    document.managewellnessForm.submit();
}

function checkdate(schedulecb)
{
    var currentDate = document.managewellnessForm.currentDate.value;
    if (document.managewellnessForm.startdate.value == "")
    {
        Swal.fire({
            title: "Please select fromdate.",
            didClose: () => {
                document.managewellnessForm.startdate.focus();
            }
        })
        return false;
    }
    if(schedulecb != 1)
    {
    if (comparisionTest(currentDate, document.managewellnessForm.startdate.value) == false)
    {
        Swal.fire({
            title: "Please select fromdate.",
            didClose: () => {
                document.managewellnessForm.startdate.focus();
            }
        })
        return false;
        }
    }else
    {
       if (comparision(currentDate, document.managewellnessForm.startdate.value) == false)
        {
            Swal.fire({
                title: "Please select fromdate.",
                didClose: () => {
                    document.managewellnessForm.startdate.focus();
                }
            })
            return false;
        } 
    }
    if(schedulecb == 1)
    {
    if (document.managewellnessForm.enddate.value == "")
    {
        Swal.fire({
            title: "Please select todate.",
            didClose: () => {
                document.managewellnessForm.enddate.focus();
            }
        })
        return false;
    }
    if (comparisionTest(document.managewellnessForm.startdate.value, document.managewellnessForm.enddate.value) == false)
    {
        Swal.fire({
            title: "Please check todate.",
            didClose: () => {
                document.managewellnessForm.enddate.value = "";
            }
        })
        return false;
    }
    }
    return true;
}
 
function savedateForm(schedulecb)
{
    if(checkdate(schedulecb))
    {
        document.forms[0].target = "_self";
        if (document.managewellnessForm.doSaveschedule)
            document.managewellnessForm.doSaveschedule.value = "yes";
        document.managewellnessForm.action = "../managewellness/ManagewellnessAction.do";
        document.managewellnessForm.submit();
    }
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

function getqlist(subcategoryId)
{
    var url = "../ajax/managewellness/getqlist.jsp";
    var httploc = getHTTPObject();
    var getstr = "subcategoryId=" + subcategoryId;
    getstr += "&assetId=" + document.forms[0].assetIdIndex.value;
    httploc.open("POST", url, true);
    httploc.onreadystatechange = function ()
    {
        if (httploc.readyState == 4)
        {
            if (httploc.status == 200)
            {
                var response = httploc.responseText;
                document.getElementById('qlistdiv').innerHTML = '';
                document.getElementById('qlistdiv').innerHTML = response;
            }
        }
    };
    httploc.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    httploc.setRequestHeader("Content-length", getstr.length);
    httploc.setRequestHeader("Connection", "close");
    httploc.send(getstr);
}