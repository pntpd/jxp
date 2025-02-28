function viewprofile()
{
    document.forms[0].target = "_blank";
    document.trackerForm.doView.value = "yes";
    document.trackerForm.doSearch.value = "no";
    document.trackerForm.action = "../talentpool/TalentpoolAction.do";
    document.trackerForm.submit();
}

function viewCompetency()
{    
    document.forms[0].target = "_blank";
    document.trackerForm.doView.value = "no";
    document.trackerForm.doSearch.value = "no";
    document.trackerForm.action = "../pcode/PcodeAction.do";
    document.trackerForm.submit();
}

function viewcandidate(candidateId)
{
    document.trackerForm.target = "_blank";
    document.trackerForm.doView.value = "yes";
    document.trackerForm.doSearch.value = "no";
    document.trackerForm.candidateId.value = candidateId;
    document.trackerForm.action = "../talentpool/TalentpoolAction.do";
    document.trackerForm.submit();
}

function resetFilter1()
{
    document.forms[0].search.value = "";
    document.trackerForm.positionIdIndex.value = "-1";
    document.trackerForm.mode.value = "1";
    searchForm();
}

function resetFilter2()
{
    document.forms[0].search.value = "";
    document.trackerForm.pcodeIdIndex.value = "-1";
    searchForm();
}

function searchForm()
{
    document.forms[0].target = "_self";
    document.trackerForm.doSearch.value = "yes";
    document.trackerForm.doSearchAsset.value = "no";
    document.trackerForm.action = "../tracker/TrackerAction.do";
    document.trackerForm.submit();
}

function searchFormAsset()
{
    document.forms[0].target = "_self";
    document.trackerForm.doSearchAsset.value = "yes";
    document.trackerForm.doSearch.value = "no";
    document.trackerForm.action = "../tracker/TrackerAction.do";
    document.trackerForm.submit();
}

function assign1(id, positionId)
{
    document.forms[0].target = "_self";
    document.trackerForm.candidateId.value = id;
    document.trackerForm.positionId.value = positionId;
    document.trackerForm.doAssign1.value = "yes";
    document.trackerForm.doSearchAsset.value = "no";
    document.trackerForm.doSearch.value = "no";
    document.trackerForm.action = "../tracker/TrackerAction.do";
    document.trackerForm.submit();
}

function assign2(id)
{
    document.forms[0].target = "_self";
    document.trackerForm.pcodeId.value = id;
    document.trackerForm.doAssign2.value = "yes";
    document.trackerForm.doSearchAsset.value = "no";
    document.trackerForm.doSearch.value = "no";
    document.trackerForm.action = "../tracker/TrackerAction.do";
    document.trackerForm.submit();
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
    if (document.trackerForm.doCancel)
        document.trackerForm.doCancel.value = "yes";
    if (document.trackerForm.doAssignSave1)
        document.trackerForm.doAssignSave1.value = "no";
    if (document.trackerForm.doAssignSave2)
        document.trackerForm.doAssignSave2.value = "no";
    if (document.trackerForm.doSearch)
        document.trackerForm.doSearch.value = "no";
    document.trackerForm.action = "../tracker/TrackerAction.do";
    document.trackerForm.submit();
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
    var url_sort = "../ajax/tracker/sort.jsp";
    var getstr = "";
    var nextValue = 0;
    if (document.trackerForm.nextValue)
        nextValue = document.trackerForm.nextValue.value;
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
    document.trackerForm.reset();
}

function exporttoexcel(exptype)
{
    document.forms[0].target = "_blank";
    document.trackerForm.action = "../tracker/TrackerExportAction.do?exptype="+exptype;
    document.trackerForm.submit();
}

function setAssetDDL()
{
    var url = "../ajax/tracker/getasset.jsp";
    document.getElementById("assetIdIndex").value = '-1';
    var httploc = getHTTPObject();
    var getstr = "clientIdIndex=" + document.trackerForm.clientIdIndex.value + "&from=asset";
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

function sortForm2(colid, updown)
{
    
    for (i = 1; i <= 2; i++)
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
    var url_sort = "../ajax/tracker/sort2.jsp";
    var getstr = "";
    var nextValue = 0;
    if (document.trackerForm.nextValue)
        nextValue = document.trackerForm.nextValue.value;
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

function searchFormDetail()
{    
    var url = "../ajax/tracker/searchdetail.jsp";
    var httploc = getHTTPObject();
    var getstr = "passessmenttypeId=" + document.trackerForm.passessmenttypeId.value;
    getstr += "&priorityId="+document.trackerForm.priorityId.value;
    getstr += "&ftype="+document.trackerForm.ftype.value;
    getstr += "&crewrotationId="+document.trackerForm.crewrotationId.value;
    getstr += "&assetIdIndex="+document.trackerForm.assetIdIndex.value;
    getstr += "&positionId="+document.trackerForm.positionId.value;
    getstr += "&search="+escape(document.trackerForm.searchdetail.value);
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
    if(document.getElementById("fcrolecball").checked == true)
    {
        if(document.trackerForm.fcrolecb)
        {
            if(document.trackerForm.fcrolecb.length)
            {
                for(var i = 0; i < document.trackerForm.fcrolecb.length; i++)
                {
                    document.trackerForm.fcrolecb[i].checked = true;
                    ct++;
                }
            }
            else
            {
                document.trackerForm.fcrolecb.checked = true;
                ct++;
            }
        }
    }
    else
    {
        if(document.trackerForm.fcrolecb)
        {
            if(document.trackerForm.fcrolecb.length)
            {
                for(var i = 0; i < document.trackerForm.fcrolecb.length; i++)
                    document.trackerForm.fcrolecb[i].checked = false;
            }
            else
            {
                document.trackerForm.fcrolecb.checked = false;
            }
        }
    }
    if(document.getElementById("assignhref"))
    {
        if(Number(ct) > 0)
        {
            document.getElementById("assignhref").href="javascript: setPersonalModalassign();";
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
    if(document.trackerForm.fcrolecb)
    {
        if(document.trackerForm.fcrolecb.length)
        {
            for(var i = 0; i < document.trackerForm.fcrolecb.length; i++)
            {
                if(document.trackerForm.fcrolecb[i].checked == true)
                    ct++;
            }
        }
        else
        {
            if(document.trackerForm.fcrolecb.checked == true)
                ct++;
        }
    }
    if(document.getElementById("assignhref"))
    {
        if(Number(ct) > 0)
        {
            document.getElementById("assignhref").href="javascript: setPersonalModalassign();";
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
    document.trackerForm.ftype.value=tp;
    document.getElementById("spansel-1").className = "round_circle circle_courses";
    document.getElementById("spansel1").className = "round_circle circle_unassigned";
    document.getElementById("spansel2").className = "round_circle circle_pending";
    document.getElementById("spansel3").className = "round_circle circle_pending";
    document.getElementById("spansel4").className = "round_circle circle_complete";
    document.getElementById("spansel5").className = "round_circle circle_exipred";
    document.getElementById("spansel6").className = "round_circle circle_exipry";
    if(tp == -1)
        document.getElementById("spansel-1").className = "round_circle circle_courses selected_circle";
    else if(tp == 1)
        document.getElementById("spansel1").className = "round_circle circle_unassigned selected_circle";
    else if(tp == 2)
        document.getElementById("spansel2").className = "round_circle circle_pending selected_circle";
    else if(tp == 3)
        document.getElementById("spansel3").className = "round_circle circle_pending selected_circle";
    else if(tp == 4)
        document.getElementById("spansel4").className = "round_circle circle_complete selected_circle";
    else if(tp == 5)
        document.getElementById("spansel5").className = "round_circle circle_exipred selected_circle";
    else if(tp == 6)
        document.getElementById("spansel6").className = "round_circle circle_exipry selected_circle";
    document.getElementById("togid").className = "usefool_tool toggled-off_02";
    searchFormDetail();
}

function gotomode()
{
    document.forms[0].target = "_self";
    document.trackerForm.mode.value=document.trackerForm.mode_top.value;
    if (document.trackerForm.doSearch)
        document.trackerForm.doSearch.value = "yes";
    if (document.trackerForm.doCancel)
        document.trackerForm.doCancel.value = "no";
    if (document.trackerForm.doAssignSave1)
        document.trackerForm.doAssignSave1.value = "no";
    if (document.trackerForm.doAssignSave2)
        document.trackerForm.doAssignSave2.value = "no";
    document.trackerForm.action = "../tracker/TrackerAction.do";
    document.trackerForm.submit();
}

function setPersonalModal(candidateId, trackerId, pcodeId, typefrom, positionId)
{
    if(candidateId == -1)
        candidateId = document.trackerForm.candidateId.value;
    var url = "../ajax/tracker/getpersonalmodal.jsp";
    var httploc = getHTTPObject();
    var getstr = "candidateId=" + candidateId;
    getstr += "&trackerId=" + trackerId;
    getstr += "&pcodeId=" + pcodeId;
    getstr += "&typefrom=" + typefrom;
    getstr += "&positionId=" + positionId;
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
            }
        }
    };
    httploc.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    httploc.send(getstr);
}

function updatetracker(trackerId, typefrom,positionId)
{   
    var url = "../ajax/tracker/updatetrackermodal.jsp";
    var httploc = getHTTPObject();
    var getstr = "trackerId=" + trackerId;
    getstr += "&typefrom=" + typefrom;
    getstr += "&positionId=" + positionId;
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
                $("#upload_link_2").on('click', function (e) {
                    e.preventDefault();
                    $("#upload2:hidden").trigger('click');
                });
                $("#upload_link_3").on('click', function (e) {
                    e.preventDefault();
                    $("#upload3:hidden").trigger('click');
                });
            }
        }
    };
    httploc.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    httploc.send(getstr);
}

function savePersonalmodal()
{
    if(checkpersonal())
    {
        document.forms[0].target = "_self";
        if (document.trackerForm.doAssignSave1)
            document.trackerForm.doAssignSave1.value = "no";
        if (document.trackerForm.doSavepersonal)
            document.trackerForm.doSavepersonal.value = "yes";
        document.trackerForm.action = "../tracker/TrackerAction.do";
        document.trackerForm.submit();
    } 
}

function checkpersonal()
{
    if(Number(document.trackerForm.stype.value == -1))
    {
          Swal.fire({
            title: "Please select action.",
            didClose: () => {
                document.trackerForm.stype.focus();
            }
        })
        return false;
    }
    if(document.trackerForm.stype.value == 3)
    {
        if (document.trackerForm.fromdate.value == "")
        {
            Swal.fire({
                title: "Please select fromdate.",
                didClose: () => {
                    document.trackerForm.fromdate.focus();
                }
            })
            return false;
        }
        if(document.trackerForm.cbtodate.checked == false)
        {
            if (document.trackerForm.todate.value == "")
            {
                Swal.fire({
                    title: "Please select todate.",
                    didClose: () => {
                        document.trackerForm.todate.focus();
                    }
                })
                return false;
            }
        }
        if (document.trackerForm.attachment.value == "")
        {
            Swal.fire({
                    title: "Please upload certificate.",
                    didClose: () => {
                        document.trackerForm.attachment.focus();
                    }
                })
                return false;
        }
        if (document.trackerForm.attachment.value != "")
        {
            if (!(document.trackerForm.attachment.value).match(/(\.(png)|(jpeg)|(pdf))$/i))
            {
                Swal.fire({
                    title: "Only .jpeg, .png, .pdf are allowed.",
                    didClose: () => {
                        document.trackerForm.attachment.focus();
                    }
                })
                return false;
            }
            var input = document.trackerForm.attachment;
            if (input.files)
            {
                var file = input.files[0];
                if (file.size > 1024 * 1024 * 5)
                {
                    Swal.fire({
                        title: "File size should not exceed 5 MB.",
                        didClose: () => {
                            document.trackerForm.attachment.focus();
                        }
                    })
                    return false;
                }
            }
        }
        }
        if(document.trackerForm.stype.value == 2)
        {
            if (document.trackerForm.completeby.value == "")
            {
                Swal.fire({
                    title: "Please enter completeby.",
                    didClose: () => {
                        document.trackerForm.completeby.focus();
                    }
                })
                return false;
            }
        }    
        return true;
    }

function setIframe(url)
{
    $('#view_pdf').modal('show');
    var url_v = "", classname = "";
    if (url.includes(".pdf"))
    {
        url_v = url+"#toolbar=0&page=1&view=fitH,100";
        classname = "pdf_mode";
    } 
    window.top.$('#iframe').attr('src', 'about:blank');
    setTimeout(function () {
        window.top.$('#iframe').attr('src', url_v);
        document.getElementById("iframe").className = classname;
        document.getElementById("diframe").href = url;
    }, 1000);

    $("#iframe").on("load", function () {
        let head = $("#iframe").contents().find("head");
        let css = '<style>img{margin: 0px auto; max-width:-webkit-fill-available;}</style>';
        $(head).append(css);
    });
}

function setPersonalModalassign()
{
    $('#unassigned_details_modal_01').modal('show');    
    var pcodeids = $("input[name='fcrolecb']:checked").map(function() {
                return this.value;}).get().join(',');  
    var candidateId = document.trackerForm.candidateId.value;
    var positionId = document.trackerForm.positionId.value;
    var url = "../ajax/tracker/getpersonalmodal.jsp";
    var httploc = getHTTPObject();
    var getstr = "candidateId=" + candidateId;
    getstr += "&pcodeids=" + pcodeids;
    getstr += "&positionId=" + positionId;
    getstr += "&typefrom=1";
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
            }
        }
    };
    httploc.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    httploc.send(getstr);
}

function savePersonalmodalassign()
{
    if(checkpersonalassign())
    {
        document.getElementById("assigndiv").innerHTML = "<div><img src='../assets/images/loading.gif' align='absmiddle'/></div>";
        document.forms[0].target = "_self";
        if (document.trackerForm.doAssignSave1)
            document.trackerForm.doAssignSave1.value = "no";
        if (document.trackerForm.doSavepersonal)
            document.trackerForm.doSavepersonal.value = "no";
        if (document.trackerForm.doSavepersonalassign)
            document.trackerForm.doSavepersonalassign.value = "yes";
        document.trackerForm.action = "../tracker/TrackerAction.do";
        document.trackerForm.submit();
    } 
}

function checkpersonalassign()
{
    if (document.trackerForm.completebydate1.value == "")
    {
        Swal.fire({
            title: "Please enter competed by (Knowledge Assessment) date.",
            didClose: () => {
                document.trackerForm.completebydate1.focus();
            }
        })
        return false;
    }
    if (document.trackerForm.completebydate2.value == "")
    {
        Swal.fire({
            title: "Please enter competed by (Practical Assessment) date.",
            didClose: () => {
                document.trackerForm.completebydate2.focus();
            }
        })
        return false;
    }    
    if(document.trackerForm.auserId)
    {
        if((Number(document.trackerForm.auserId.value)) <= 0)
        {
            Swal.fire({
                title: "Please select competency assessor.",
                didClose: () => {                    
                }
            })
            return false;
        }
    }
    else
    {
        Swal.fire({
            title: "Please check competency assessor.",
            didClose: () => {                    
            }
        })
        return false;
    }
    return true;
}

function SubmitTrackForm(id)
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
    if (checkTrackForm())
    {
        document.getElementById("updatetrack").innerHTML = "<div><img src='../assets/images/loading.gif' align='absmiddle'/>Please wait. Loading...</div>";
            document.trackerForm.doUpdateTrack.value = "yes";
            document.trackerForm.trackerId.value = id;
        document.trackerForm.action = "../tracker/TrackerAction.do";
        document.trackerForm.submit();
    }
}

function checkTrackForm()
{
    if(Number(document.trackerForm.statusModal.value == 2))
    {
        if(document.trackerForm.averagescore.value > 0)
            {
                if ( (document.trackerForm.trackerFile.value == "")  && (document.trackerForm.trackerFilehidden.value = ""))
                {
                    Swal.fire({
                            title: "Please upload knowledge sheet.",
                            didClose: () => {
                                document.trackerForm.trackerFile.focus();
                            }
                        })
                        return false;
                }
                if (document.trackerForm.trackerFile.value != "")
                {
                    if (!(document.trackerForm.trackerFile.value).match(/(\.(pdf))$/i) )
                    {
                        Swal.fire({
                            title: "Only .pdf are allowed.",
                            didClose: () => {
                                document.trackerForm.trackerFile.focus();
                            }
                        })
                        return false;
                    }
                    var input = document.trackerForm.trackerFile;
                    if (input.files)
                    {
                        var file = input.files[0];
                        if (file.size > 1024 * 1024 * 5)
                        {
                            Swal.fire({
                                title: "File size should not exceed 5 MB.",
                                didClose: () => {
                                    document.trackerForm.trackerFile.focus();
                                }
                            })
                            return false;
                        }
                    }
                }
                if(document.trackerForm.score.value == "" )
                {
                     Swal.fire({
                            title: "Please enter score.",
                            didClose: () => {
                                document.trackerForm.score.focus();
                            }
                        })
                        return false;
                }
                if((document.trackerForm.score.value > 100))
                {
                     Swal.fire({
                            title: "Please enter score between 1 to 100.",
                            didClose: () => {
                                document.trackerForm.score.focus();
                            }
                        })
                        return false;
                }
                if(document.trackerForm.resultId.value == "-1")
                {
                     Swal.fire({
                            title: "Please select  result.",
                            didClose: () => {
                                document.trackerForm.resultId.focus();
                            }
                        })
                        return false;
                }
                 if (document.trackerForm.remarks.value == "")
                {
                    Swal.fire({
                            title: "Please enter remarks.",
                            didClose: () => {
                                document.trackerForm.remarks.focus();
                            }
                        })
                        return false;
                }
            if ( (document.trackerForm.trackerFile2.value == "")  && (document.trackerForm.trackerFilehidden2.value = ""))
                {
                    Swal.fire({
                            title: "Please upload prcatical competency document.",
                            didClose: () => {
                                document.trackerForm.trackerFile2.focus();
                            }
                        })
                        return false;
                }
                if(document.trackerForm.trackerFile2.value != "")
                {
                    if (!(document.trackerForm.trackerFile2.value).match(/(\.(pdf))$/i))
                    {
                        Swal.fire({
                            title: "Only .pdf are allowed.",
                            didClose: () => {
                                document.trackerForm.trackerFile2.focus();
                            }
                        })
                        return false;
                    }
                    var input = document.trackerForm.trackerFile2;
                    if (input.files)
                    {
                        var file = input.files[0];
                        if (file.size > 1024 * 1024 * 5)
                        {
                            Swal.fire({
                                title: "File size should not exceed 5 MB.",
                                didClose: () => {
                                    document.trackerForm.trackerFile2.focus();
                                }
                            })
                            return false;
                        }
                    }
                }
                if(document.trackerForm.practicalscore.value == "")
                {
                     Swal.fire({
                            title: "Please enter score.",
                            didClose: () => {
                                document.trackerForm.practicalscore.focus();
                            }
                        })
                        return false;
                }
                if((document.trackerForm.practicalscore.value > 100))
                {
                     Swal.fire({
                            title: "Please enter score between 1 to 100.",
                            didClose: () => {
                                document.trackerForm.practicalscore.focus();
                            }
                        })
                        return false;
                }
                if(document.trackerForm.resultId2.value == "-1")
                {
                     Swal.fire({
                            title: "Please select  result.",
                            didClose: () => {
                                document.trackerForm.resultId2.focus();
                            }
                        })
                        return false;
                }
                 if (document.trackerForm.practicalremarks.value == "")
                {
                    Swal.fire({
                            title: "Please enter remarks.",
                            didClose: () => {
                                document.trackerForm.practicalremarks.focus();
                            }
                        })
                        return false;
                }
            }
        }
        if(Number(document.trackerForm.statusModal.value == 3))
        {
            if(document.trackerForm.outcomeId.value == "-1")
           {
               Swal.fire({
                       title: "Please select outcome.",
                       didClose: () => {
                           document.trackerForm.outcomeId.focus();
                       }
                   })
                   return false;
           }
           if(document.trackerForm.outcomeId.value == "2")
           {
               if(document.trackerForm.trainingId.value == "-1")
              {
                  Swal.fire({
                          title: "Please select training.",
                          didClose: () => {
                              document.trackerForm.trainingId.focus();
                          }
                      })
                      return false;
              }
          }
           if (document.trackerForm.outcomeremarks.value == "")
          {
              Swal.fire({
                      title: "Please enter remarks.",
                      didClose: () => {
                          document.trackerForm.outcomeremarks.focus();
                      }
                  })
                  return false;
          }
      }      
      if(Number(document.trackerForm.statusModal.value == 6))
        {
            if(document.trackerForm.rejection.value == "")
           {
               Swal.fire({
                       title: "Please select appeal decision.",
                       didClose: () => {
                           document.trackerForm.rejection.focus();
                       }
                   })
                   return false;
           }
           if(document.trackerForm.rejection.value == "Reject")
           {
               if(document.trackerForm.reasonId.value == "-1")
              {
                  Swal.fire({
                          title: "Please select reason.",
                          didClose: () => {
                              document.trackerForm.reasonId.focus();
                          }
                      })
                      return false;
              }
          }         
      }
        return true;
    }


function closeModal()
{
     $('#thank_you_modal').modal('hide');
}

function setClass(tp)
{
    document.getElementById("upload_link_" + tp).className = "attache_btn uploaded_img";
}

function setmultiplerole(pcodeids)
{
    var url = "../ajax/tracker/getmultiplerole.jsp";
    var httploc = getHTTPObject();
    var getstr = "pcodeids=" + pcodeids;
    httploc.open("POST", url, true);
    httploc.onreadystatechange = function ()
    {
        if (httploc.readyState == 4)
        {
            if (httploc.status == 200)
            {
                var response = httploc.responseText;
                document.getElementById("pcodemultiple").innerHTML = '';
                document.getElementById("pcodemultiple").innerHTML = response;
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

function setfilter2(tp)
{
    document.trackerForm.ftype.value=tp;
    document.getElementById("spansel-1").className = "round_circle circle_courses";
    document.getElementById("spansel1").className = "round_circle circle_unassigned";
    document.getElementById("spansel2").className = "round_circle circle_pending";
    document.getElementById("spansel3").className = "round_circle circle_pending";
    document.getElementById("spansel4").className = "round_circle circle_complete";
    document.getElementById("spansel5").className = "round_circle circle_exipred";
    document.getElementById("spansel6").className = "round_circle circle_exipry";
    if(tp == -1)
        document.getElementById("spansel-1").className = "round_circle circle_courses selected_circle";
    else if(tp == 1)
        document.getElementById("spansel1").className = "round_circle circle_unassigned selected_circle";
    else if(tp == 2)
        document.getElementById("spansel2").className = "round_circle circle_pending selected_circle";
    else if(tp == 3)
        document.getElementById("spansel3").className = "round_circle circle_pending selected_circle";
    else if(tp == 4)
        document.getElementById("spansel4").className = "round_circle circle_complete selected_circle";
    else if(tp == 5)
        document.getElementById("spansel5").className = "round_circle circle_exipred selected_circle";
    else if(tp == 6)
        document.getElementById("spansel6").className = "round_circle circle_exipry selected_circle";
    document.getElementById("togid").className = "usefool_tool toggled-off_02";
    searchFormDetail2();
}

function searchFormDetail2()
{    
    var url = "../ajax/tracker/searchdetail2.jsp";
    var httploc = getHTTPObject();
    var getstr = "ftype="+document.trackerForm.ftype.value;
    getstr += "&pcodeId="+document.trackerForm.pcodeId.value;
    getstr += "&assetIdIndex="+document.trackerForm.assetIdIndex.value;
    getstr += "&search="+escape(document.trackerForm.searchdetail.value);
    getstr += "&positionId2Index="+escape(document.trackerForm.positionId2Index.value);
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

function calculateScore()
{
    var v1 = document.trackerForm.score.value;
    var v2 = document.trackerForm.practicalscore.value;    
    if((Number(v1 >0) ) && (Number(v2 > 0)))
    {
        var total = ((Number(v1) +(Number(v2)))/2).toFixed(0);        
        document.trackerForm.averagescore.value = total;
    }
}

function showhide()
{
    if (document.trackerForm.outcomeId.value == 2)
    {
        document.getElementById('trId').style.display = "";
    } 
    else
    {
        document.getElementById('trId').style.display = "none";
    }
}

function showhide2()
{
    if (document.trackerForm.rejection.value == "Reject")
    {
        document.getElementById('reasondiv').style.display = "";
    } 
    else
    {
        document.getElementById('reasondiv').style.display = "none";
    }
}

function gethistory(trackerId)
{
    var url = "../ajax/tracker/gethistory.jsp";
    var httploc = getHTTPObject();
    var getstr = "trackerId=" + trackerId;
    httploc.open("POST", url, true);
    httploc.onreadystatechange = function ()
    {
        if (httploc.readyState == 4)
        {
            if (httploc.status == 200)
            {
                var response = httploc.responseText;
                document.getElementById("historydiv").innerHTML = '';
                document.getElementById("historydiv").innerHTML = response;
            }
        }
    };
    httploc.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    httploc.send(getstr);
}

function getquestion(trackerId)
{
    var url = "../ajax/tracker/getquestion.jsp";
    var httploc = getHTTPObject();
    var getstr = "trackerId=" + trackerId;
    httploc.open("POST", url, true);
    httploc.onreadystatechange = function ()
    {
        if (httploc.readyState == 4)
        {
            if (httploc.status == 200)
            {
                var response = httploc.responseText;
                document.getElementById("questiondiv").innerHTML = '';
                document.getElementById("questiondiv").innerHTML = response;
            }
        }
    };
    httploc.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    httploc.send(getstr);
}

function gotoCompetency()
{
    document.forms[0].action="../competency/CompetencyAction.do?doSearch=yes";
    document.forms[0].submit();
}