function resetFilter()
{
    document.mobilizationForm.search.value = "";
    document.mobilizationForm.clientIdIndex.value = "-1";
    document.mobilizationForm.assetIdIndex.value = "-1";
    document.mobilizationForm.countryId.value = "-1";
    searchFormAjax('s', '-1');
    setAssetDDL();
}

function exporttoexcel()
{
    document.mobilizationForm.action = "../mobilization/MobilizationExportAction.do";
    document.mobilizationForm.submit();
}

function view(clientId, clientassetId)
{
    if (document.mobilizationForm.doView)
        document.mobilizationForm.doView.value = "yes";
    document.mobilizationForm.clientId.value = clientId;
    document.mobilizationForm.clientassetId.value = clientassetId;
    document.forms[0].target = "";
    document.mobilizationForm.action = "../mobilization/MobilizationAction.do";
    document.mobilizationForm.submit();
}

function gobackview()
{
    if (document.mobilizationForm.doView)
        document.mobilizationForm.doView.value = "yes";
    document.forms[0].target = "";
    document.mobilizationForm.action = "../mobilization/MobilizationAction.do";
    document.mobilizationForm.submit();
}

function goback()
{
    if (document.mobilizationForm.doView)
        document.mobilizationForm.doView.value = "no";
    if (document.mobilizationForm.doCancel)
        document.mobilizationForm.doCancel.value = "yes";
    if (document.mobilizationForm.doSave)
        document.mobilizationForm.doSave.value = "no";
    document.forms[0].target = "";
    document.mobilizationForm.action = "../mobilization/MobilizationAction.do";
    document.mobilizationForm.submit();
}

function viewcancel()
{
    if (document.mobilizationForm.doViewCancel)
        document.mobilizationForm.doViewCancel.value = "yes";
    document.forms[0].target = "";
    document.mobilizationForm.action = "../mobilization/MobilizationAction.do";
    document.mobilizationForm.submit();
}

function setClass(tp)
{
    document.getElementById("upload_link_" + tp).className = "attache_btn attache_btn_white uploaded_img";
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
        var url = "../ajax/mobilization/getinfo.jsp";
        var httploc = getHTTPObject();
        var getstr = "";
        var next_value = escape(document.mobilizationForm.nextValue.value);
        var search_value = escape(document.mobilizationForm.search.value);
        var assetIdIndex = escape(document.mobilizationForm.assetIdIndex.value);
        var clientIdIndex = escape(document.mobilizationForm.clientIdIndex.value);
        var countryId = escape(document.mobilizationForm.countryId.value);
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
    var url_sort = "../ajax/mobilization/sort.jsp";
    var getstr = "";
    var nextValue = 0;
    if (document.mobilizationForm.nextValue)
        nextValue = document.mobilizationForm.nextValue.value;
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
    document.mobilizationForm.reset();
}

function setAssetDDL()
{
    var url = "../ajax/mobilization/getassetDDL.jsp";
    document.mobilizationForm.assetIdIndex.value = "-1";
    var httploc = getHTTPObject();
    var getstr = "clientIdIndex=" + document.mobilizationForm.clientIdIndex.value + "&from=asset";
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

function ViewJobpost(id)
{
    if (id == "") 
    {
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

function viewDocCheck(id, assetId)
{
    document.forms[0].target = "_blank";
    document.forms[0].action = "/jxp/doccheck/DoccheckAction.do?clientIndex=" + id + "&assetIndex=" + assetId;
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

function getmobtraveldtls(id, type)
{
    document.mobilizationForm.crewrotationId.value = id;
    document.mobilizationForm.type.value = type;
    if (document.mobilizationForm.doMobTravel)
        document.mobilizationForm.doMobTravel.value = "yes";
    document.forms[0].target = "";
    document.mobilizationForm.action = "../mobilization/MobilizationAction.do";
    document.mobilizationForm.submit();
}

function getsavetraveldtls(id)
{
    if (document.getElementById("mobcount").value == "0") 
    {
        Swal.fire({
            title: "Please add atleast one record",
        })
        return false;
    }
    document.mobilizationForm.crewrotationId.value = id;
    if (document.mobilizationForm.doSaveTravel)
        document.mobilizationForm.doSaveTravel.value = "yes";
    document.forms[0].target = "";
    document.mobilizationForm.action = "../mobilization/MobilizationAction.do";
    document.mobilizationForm.submit();
}

function getmobaccommdtls(id, type)
{
    document.mobilizationForm.crewrotationId.value = id;
    document.mobilizationForm.type.value = type;
    if (document.mobilizationForm.doMobAccomm)
        document.mobilizationForm.doMobAccomm.value = "yes";
    document.forms[0].target = "";
    document.mobilizationForm.action = "../mobilization/MobilizationAction.do";
    document.mobilizationForm.submit();
}

function getsaveaccommdtls(id)
{
    if (document.getElementById("mobcount").value == "0") {
        Swal.fire({
            title: "Please add atleast one record",
        })
        return false;
    }
    document.mobilizationForm.crewrotationId.value = id;
    if (document.mobilizationForm.doSaveAccomm)
        document.mobilizationForm.doSaveAccomm.value = "yes";
    document.forms[0].target = "";
    document.mobilizationForm.action = "../mobilization/MobilizationAction.do";
    document.mobilizationForm.submit();
}

function openTab(obj)
{
    if (obj == '1') {
        document.mobilizationForm.type.value = obj;
        if (document.mobilizationForm.doMobTravel)
            document.mobilizationForm.doMobTravel.value = "yes";
    } else if (obj == '2') {
        document.mobilizationForm.type.value = obj;
        if (document.mobilizationForm.doMobAccomm)
            document.mobilizationForm.doMobAccomm.value = "yes";
    }
    document.forms[0].target = "";
    document.mobilizationForm.action = "../mobilization/MobilizationAction.do";
    document.mobilizationForm.submit();
}

function ViewAsset(id)
{
    if (id == "") {
        id = document.forms[0].clientId.value;
    }
    document.forms[0].clientId.value = id;
    document.forms[0].doView.value = "yes";
    document.forms[0].target = "_blank";
    document.forms[0].action = "../client/ClientAction.do?tabno=2";
    document.forms[0].submit();
}


function getReqDocList() 
{
    var httploc = getHTTPObject();
    var url_sort = "../ajax/mobilization/getdoclist.jsp";
    var getstr = "";
    getstr += "clientid=" + document.mobilizationForm.clientId.value;
    getstr += "&clientassetId=" + document.mobilizationForm.clientassetId.value;
    httploc.open("POST", url_sort, true);
    httploc.onreadystatechange = function ()
    {
        if (httploc.readyState == 4)
        {
            if (httploc.status == 200)
            {
                var response = httploc.responseText;
                document.getElementById('dReqDocMob').innerHTML = '';
                document.getElementById('dReqDocMob').innerHTML = response;
            }
        }
    };
    httploc.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    httploc.setRequestHeader("Content-length", getstr.length);
    httploc.setRequestHeader("Connection", "close");
    httploc.send(getstr);
    document.getElementById('dReqDocMob').innerHTML = "<div><img src='../assets/images/loading.gif' align='absmiddle'/>Please wait. Loading...</div>";
    $('#view_doc_list_modal').modal('show');
}

function getTraveldetails(id, type) {
    var httploc = getHTTPObject();
    var url_sort = "../ajax/mobilization/gettraveldtls.jsp";
    var getstr = "";
    getstr += "crewroationid=" + id;
    getstr += "&type=" + type;
    httploc.open("POST", url_sort, true);
    httploc.onreadystatechange = function ()
    {
        if (httploc.readyState == 4)
        {
            if (httploc.status == 200)
            {
                var response = httploc.responseText;
                document.getElementById('dTravelDtls').innerHTML = '';
                document.getElementById('dTravelDtls').innerHTML = response;
                //Datepicker
                $(".kt-selectpicker").selectpicker();
                $(".wesl_dt").datepicker({
                    todayHighlight: !0,
                    format: "dd-M-yyyy",
                    autoclose: "true",
                    orientation: "bottom"
                });
                //Timepicker
                $(".timepicker-24").timepicker({
                    format: 'mm:mm',
                    autoclose: !0,
                    minuteStep: 5,
                    showSeconds: !1,
                    showMeridian: !1
                });
                $("#upload_link_1").on('click', function (e) {
                    e.preventDefault();
                    $("#upload1:hidden").trigger('click');
                });
                $(".wrapper1").scroll(function () {
                    $(".wrapper2")
                            .scrollLeft($(".wrapper1").scrollLeft());
                });
                $(".wrapper2").scroll(function () {
                    $(".wrapper1")
                            .scrollLeft($(".wrapper2").scrollLeft());
                });

                $(".wrapper01").scroll(function () {
                    $(".wrapper02")
                            .scrollLeft($(".wrapper01").scrollLeft());
                });
                $(".wrapper02").scroll(function () {
                    $(".wrapper01")
                            .scrollLeft($(".wrapper02").scrollLeft());
                });
            }
        }
    };
    httploc.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    httploc.setRequestHeader("Content-length", getstr.length);
    httploc.setRequestHeader("Connection", "close");
    httploc.send(getstr);
    document.getElementById('dTravelDtls').innerHTML = "<div><img src='../assets/images/loading.gif' align='absmiddle'/>Please wait. Loading...</div>";
    $('#add_travel_details_modal').modal('show');
}

function getAccommDetails(id, type) 
{
    var httploc = getHTTPObject();
    var url_sort = "../ajax/mobilization/getaccommdtls.jsp";
    var getstr = "";
    getstr += "crewroationid=" + id;
    getstr += "&type=" + type;
    httploc.open("POST", url_sort, true);
    httploc.onreadystatechange = function ()
    {
        if (httploc.readyState == 4)
        {
            if (httploc.status == 200)
            {
                var response = httploc.responseText;
                document.getElementById('dAccommDtls').innerHTML = '';
                document.getElementById('dAccommDtls').innerHTML = response;
                //Datepicker
                $(".kt-selectpicker").selectpicker();
                $(".wesl_dt").datepicker({
                    todayHighlight: !0,
                    format: "dd-M-yyyy",
                    autoclose: "true",
                    orientation: "bottom"
                });
                //Timepicker
                $(".timepicker-24").timepicker({
                    format: 'mm:mm',
                    autoclose: !0,
                    minuteStep: 5,
                    showSeconds: !1,
                    showMeridian: !1
                });
                $("#upload_link_1").on('click', function (e) {
                    e.preventDefault();
                    $("#upload1:hidden").trigger('click');
                });
                $(".wrapper1").scroll(function () {
                    $(".wrapper2")
                            .scrollLeft($(".wrapper1").scrollLeft());
                });
                $(".wrapper2").scroll(function () {
                    $(".wrapper1")
                            .scrollLeft($(".wrapper2").scrollLeft());
                });

                $(".wrapper01").scroll(function () {
                    $(".wrapper02")
                            .scrollLeft($(".wrapper01").scrollLeft());
                });
                $(".wrapper02").scroll(function () {
                    $(".wrapper01")
                            .scrollLeft($(".wrapper02").scrollLeft());
                });
            }
        }
    };
    httploc.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    httploc.setRequestHeader("Content-length", getstr.length);
    httploc.setRequestHeader("Connection", "close");
    httploc.send(getstr);
    document.getElementById('dAccommDtls').innerHTML = "<div><img src='../assets/images/loading.gif' align='absmiddle'/>Please wait. Loading...</div>";
    $('#add_accom_details_modal').modal('show');
}

function checkMobDtls(type)
{
    var tempval = "";
    if (Number(document.forms[0].val1.value) == "")
    {
        tempval = "";
        if (type == 1) {
            tempval = "Please select mode.";
        } else if (type == 2) {
            tempval = "Please enter hotel name.";
        }
        Swal.fire({
            title: tempval,
            didClose: () => {
                document.forms[0].val1.focus();
            }
        })
        return false;
    }
    if (type == 2) 
    {
        if (validdesc(document.forms[0].val1) == false)
        {
            return false;
        }
    }
    if (trim(document.forms[0].val2.value) == "")
    {
        tempval = "";
        if (type == 1) {
            tempval = "Please enter departure location.";
        } else if (type == 2) {
            tempval = "Please enter address.";
        }
        Swal.fire({
            title: tempval,
            didClose: () => {
                document.forms[0].val2.focus();
            }
        })
        return false;
    }
    if (validdesc(document.forms[0].val2) == false)
    {
        return false;
    }
    if (type == 2) 
    {
        if (trim(document.forms[0].val3.value) == "")
        {
            Swal.fire({
                title: "Please enter contact number",
                didClose: () => {
                    document.forms[0].val3.focus();
                }
            })
            return false;
        }
        if (validdesc(document.forms[0].val3) == false)
        {
            return false;
        }
    }
    if (type == 2)
    {
        if (trim(document.forms[0].val4.value) == "")
        {
            Swal.fire({
                title: "Please enter room no.",
                didClose: () => {
                    document.forms[0].val4.focus();
                }
            })
            return false;
        }
        if (validdesc(document.forms[0].val4) == false)
        {
            return false;
        }
    }
    if (type == 1) 
    {
        if (trim(document.forms[0].val5.value) == "")
        {
            Swal.fire({
                title: "Please enter Flight/train/bus/car no.",
                didClose: () => {
                    document.forms[0].val5.focus();
                }
            })
            return false;
        }
        if (validdesc(document.forms[0].val5) == false)
        {
            return false;
        }
    }
    if (document.forms[0].vald9.value == "")
    {
        tempval = "";
        if (type == 1) {
            tempval = "Please select ETD - date.";
        } else if (type == 2) {
            tempval = "Please select check in date.";
        }
        Swal.fire({
            title: tempval,
            didClose: () => {
                document.forms[0].vald9.focus();
            }
        })
        return false;
    }
    if (document.forms[0].valt9.value == "")
    {
        tempval = "";
        if (type == 1) {
            tempval = "Please select ETD - time.";
        } else if (type == 2) {
            tempval = "Please select check in time.";
        }
        Swal.fire({
            title: tempval,
            didClose: () => {
                document.forms[0].valt9.focus();
            }
        })
        return false;
    }
    if (document.forms[0].vald10.value == "")
    {
        tempval = "";
        if (type == 1) {
            tempval = "Please select ETA - date.";
        } else if (type == 2) {
            tempval = "Please select check out date.";
        }
        Swal.fire({
            title: tempval,
            didClose: () => {
                document.forms[0].vald10.focus();
            }
        })
        return false;
    }
    if (document.forms[0].valt10.value == "")
    {
        tempval = "";
        if (type == 1) {
            tempval = "Please select ETA - time.";
        } else if (type == 2) {
            tempval = "Please select check out time.";
        }
        Swal.fire({
            title: tempval,
            didClose: () => {
                document.forms[0].valt10.focus();
            }
        })
        return false;
    }
    if (compareDateTime(document.forms[0].vald9.value + " " + document.forms[0].valt9.value, document.forms[0].vald10.value + " " + document.forms[0].valt10.value) == false) {
        tempval = "";
        if (type == 1) {
            tempval = "Arrival date time should be greater than departure date time.";
        } else if (type == 2) {
            tempval = "Check out date time should be greater than check in date time.";
        }
        Swal.fire({
            title: tempval,
        })
        return false;
    }
    if (trim(document.forms[0].val6.value) == "")
    {
        tempval = "";
        if (type == 1) {
            tempval = "Please enter arrival location.";
        } else if (type == 2) {
            tempval = "Please enter remarks.";
        }
        Swal.fire({
            title: tempval,
            didClose: () => {
                document.forms[0].val6.focus();
            }
        })
        return false;
    }
    if (validdesc(document.forms[0].val6) == false)
    {
        return false;
    }
    if (document.forms[0].upload1.value != "")
    {
        if (!(document.forms[0].upload1.value).match(/(\.(pdf))$/i))
        {
            Swal.fire({
                title: "Only .pdf are allowed.",
                didClose: () => {
                    document.forms[0].upload1.focus();
                }
            })
            return false;
        }
        var input = document.forms[0].upload1;
        if (input.files)
        {
            var file = input.files[0];
            if (file.size > (1024 * 1024 * 5))
            {
                Swal.fire({
                    title: "File size should not exceed 5 MB.",
                    didClose: () => {
                        document.forms[0].upload1.focus();
                    }
                })
                return false;
            }
        }
    }
    return true;
}

function getSaveMobi(id, type) 
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
    if (checkMobDtls(type))
    {
        var httploc = getHTTPObject();
        var url_sort = "../ajax/mobilization/insert_mobdtls.jsp";
        var getstr = "";
        getstr += "crewroationid=" + id;
        getstr += "&val1=" + document.mobilizationForm.val1.value;
        document.mobilizationForm.val1.value = "";
        getstr += "&val2=" + document.mobilizationForm.val2.value;
        document.mobilizationForm.val2.value = "";
        getstr += "&val3=" + document.mobilizationForm.val3.value;
        document.mobilizationForm.val3.value = "";
        getstr += "&val4=" + document.mobilizationForm.val4.value;
        document.mobilizationForm.val4.value = "";
        getstr += "&val5=" + document.mobilizationForm.val5.value;
        document.mobilizationForm.val5.value = "";
        getstr += "&val6=" + document.mobilizationForm.val6.value;
        document.mobilizationForm.val6.value = "";
        if (document.mobilizationForm.val7) {
            getstr += "&val7=" + document.mobilizationForm.val7.value;
            document.mobilizationForm.val7.value = "";
        }
        if (document.mobilizationForm.val8) {
            getstr += "&val8=" + document.mobilizationForm.val8.value;
            document.mobilizationForm.val8.value = "";
        }
        getstr += "&vald9=" + document.mobilizationForm.vald9.value;
        document.mobilizationForm.vald9.value = "";
        getstr += "&valt9=" + document.mobilizationForm.valt9.value;
        getstr += "&vald10=" + document.mobilizationForm.vald10.value;
        document.mobilizationForm.vald10.value = "";
        getstr += "&valt10=" + document.mobilizationForm.valt10.value;
        getstr += "&filename=" + document.mobilizationForm.upload1.value;
        document.getElementById("upload_link_1").className = "attache_btn attache_btn_white uploaded_img1";
        getstr += "&type=" + type;
        httploc.open("POST", url_sort, true);
        httploc.onreadystatechange = function ()
        {
            if (httploc.readyState == 4)
            {
                if (httploc.status == 200)
                {
                    var response = httploc.responseText;
                    if (type == 1) {
                        document.getElementById('tbodytravel').innerHTML = '';
                        document.getElementById('tbodytravel').innerHTML = response;
                    } else if (type == 2) {
                        document.getElementById('tbodyaccomm').innerHTML = '';
                        document.getElementById('tbodyaccomm').innerHTML = response;
                    }
                }
            }
        };
        getstr += "&attachfile=" + encodeURIComponent(document.getElementById("hdnfilename").value);
        document.getElementById("hdnfilename").value = "";
        httploc.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
        httploc.setRequestHeader("Content-length", getstr.length);
        httploc.setRequestHeader("Connection", "close");
        httploc.send(getstr);
        if (type == 1) {
            document.getElementById('tbodytravel').innerHTML = "<div><img src='../assets/images/loading.gif' align='absmiddle'/>Please wait. Loading...</div>";
        } else if (type == 2) {
            document.getElementById('tbodyaccomm').innerHTML = "<div><img src='../assets/images/loading.gif' align='absmiddle'/>Please wait. Loading...</div>";
        }
    }
}

function getDeleteMobDtls(mobid, batchid, type) 
{
    var httploc = getHTTPObject();
    var url_sort = "../ajax/mobilization/delete_mobdtls.jsp";
    var getstr = "";
    getstr += "mobid=" + mobid;
    getstr += "&batchid=" + batchid;
    getstr += "&type=" + type;
    httploc.open("POST", url_sort, true);
    httploc.onreadystatechange = function ()
    {
        if (httploc.readyState == 4)
        {
            if (httploc.status == 200)
            {
                var response = httploc.responseText;
                if (type == 1) {
                    document.getElementById('tbodytravel').innerHTML = '';
                    document.getElementById('tbodytravel').innerHTML = response;
                } else if (type == 2) {
                    document.getElementById('tbodyaccomm').innerHTML = '';
                    document.getElementById('tbodyaccomm').innerHTML = response;
                }
            }
        }
    };
    httploc.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    httploc.setRequestHeader("Content-length", getstr.length);
    httploc.setRequestHeader("Connection", "close");
    httploc.send(getstr);
    if (type == 1) {
        document.getElementById('tbodytravel').innerHTML = "<div><img src='../assets/images/loading.gif' align='absmiddle'/>Please wait. Loading...</div>";
    } else if (type == 2) {
        document.getElementById('tbodyaccomm').innerHTML = "<div><img src='../assets/images/loading.gif' align='absmiddle'/>Please wait. Loading...</div>";
    }
}

function getMailDetails(id, type) 
{
    var httploc = getHTTPObject();
    var url_sort = "../ajax/mobilization/getmobsendmail.jsp";
    var getstr = "";
    if (id == "") {
        getstr += "crewroationid=" + document.mobilizationForm.crewrotationId.value;
    } else {
        getstr += "crewroationid=" + id;
    }
    getstr += "&clientid=" + document.mobilizationForm.clientId.value;
    getstr += "&clientassetid=" + document.mobilizationForm.clientassetId.value;
    getstr += "&type=" + type;
    httploc.open("POST", url_sort, true);
    httploc.onreadystatechange = function ()
    {
        if (httploc.readyState == 4)
        {
            if (httploc.status == 200)
            {
                var response = httploc.responseText;
                document.getElementById('dmaildetails').innerHTML = '';
                document.getElementById('dmaildetails').innerHTML = response;
            }
        }
    };
    httploc.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    httploc.setRequestHeader("Content-length", getstr.length);
    httploc.setRequestHeader("Connection", "close");
    httploc.send(getstr);
    document.getElementById('dmaildetails').innerHTML = "<div><img src='../assets/images/loading.gif' align='absmiddle'/>Please wait. Loading...</div>";
    $('#email_candidate_modal').modal('show');
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
        document.getElementById("diframe").href = uval;
    }, 1000);

    $("#iframe").on("load", function () {
        let head = $("#iframe").contents().find("head");
        let css = '<style>img{margin: 0px auto; max-width:-webkit-fill-available;}</style>';
        $(head).append(css);
    });
}
function setofferfilebase()
{
    document.getElementById("upload_link_1").className = "attache_btn uploaded_img";
    if (document.forms[0].upload1.value != "")
    {
        var input = document.forms[0].upload1;
        if (input.files)
        {
            var file = input.files[0];
            if (file.size > (1024 * 1024 * 5))
            {
                Swal.fire({
                    title: "File size should not exceed 5 MB.",
                    didClose: () => {
                        document.forms[0].upload1.focus();
                    }
                });
            } else
            {
                var filesSelected = document.getElementById("upload1").files;
                if (filesSelected.length > 0)
                {
                    var fileToLoad = filesSelected[0];
                    var fileReader = new FileReader();
                    fileReader.onload = function (fileLoadedEvent)
                    {
                        var srcData = fileLoadedEvent.target.result; // <--- data: base64
                        document.getElementById("hdnfilename").value = srcData;
                        //   attachfile = srcData;
                    }
                    fileReader.readAsDataURL(fileToLoad);
                }
            }
        }
    }
}

function checkMail()
{
    var toval = document.forms[0].mailto.value;
    if (toval == "")
    {
        Swal.fire({
            title: "Please enter To email address.",
            didClose: () => {
                document.forms[0].mailto.focus();
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
                        document.forms[0].mailto.focus();
                    }
                })
                return false;
            }
        }
    }
    var ccval = document.forms[0].mailcc.value;
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
                        document.forms[0].mailcc.focus();
                    }
                })
                return false;
            }
        }
    }
    var bccval = document.forms[0].mailbcc.value;
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
                        document.forms[0].mailbcc.focus();
                    }
                })
                return false;
            }
        }
    }
    if (trim(document.forms[0].mailsubject.value) == "")
    {
        Swal.fire({
            title: "Please enter subject.",
            didClose: () => {
                document.forms[0].mailsubject.focus();
            }
        })
        return false;
    }
    if (validdesc(document.forms[0].mailsubject) == false)
    {
        return false;
    }
    if (trim(document.forms[0].maildescription.value) == "")
    {
        Swal.fire({
            title: "Please enter Email Body.",
            didClose: () => {
                document.forms[0].maildescription.focus();
            }
        })
        return false;
    }
    if (validdesc(document.forms[0].maildescription) == false)
    {
        return false;
    }
    return true;
}

function sendmobmail(crewId) 
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
    if (checkMail())
    {
        document.getElementById("dsendmail").innerHTML = "<div><img src='../assets/images/loading.gif' align='absmiddle'/></div>";
        document.mobilizationForm.crewrotationId.value = crewId;
        if (document.mobilizationForm.doMobMail)
            document.mobilizationForm.doMobMail.value = "yes";
        document.forms[0].target = "";
        document.mobilizationForm.action = "../mobilization/MobilizationAction.do";
        document.mobilizationForm.submit();
    }
}

function sendmobhistmail(crewId) 
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
    if (checkMail())
    {
        document.getElementById("dsendhistmail").innerHTML = "<div><img src='../assets/images/loading.gif' align='absmiddle'/></div>";
        document.mobilizationForm.crewrotationId.value = crewId;
        if (document.mobilizationForm.doMobhistMail)
            document.mobilizationForm.doMobhistMail.value = "yes";
        document.forms[0].target = "";
        document.mobilizationForm.action = "../mobilization/MobilizationAction.do";
        document.mobilizationForm.submit();
    }
}

function getEditTraveldetails(id) 
{
    var httploc = getHTTPObject();
    var url_sort = "../ajax/mobilization/getupdatetraveldtls.jsp";
    var getstr = "";
    getstr += "mobilizationid=" + id;
    httploc.open("POST", url_sort, true);
    httploc.onreadystatechange = function ()
    {
        if (httploc.readyState == 4)
        {
            if (httploc.status == 200)
            {
                var response = httploc.responseText;
                document.getElementById('dedittravel').innerHTML = '';
                document.getElementById('dedittravel').innerHTML = response;
                //Datepicker
                $(".kt-selectpicker").selectpicker();
                $(".wesl_dt").datepicker({
                    todayHighlight: !0,
                    format: "dd-M-yyyy",
                    autoclose: "true",
                    orientation: "bottom"
                });
                //Timepicker
                $(".timepicker-24").timepicker({
                    format: 'mm:mm',
                    autoclose: !0,
                    minuteStep: 5,
                    showSeconds: !1,
                    showMeridian: !1
                });
                $("#upload_link_1").on('click', function (e) {
                    e.preventDefault();
                    $("#upload1:hidden").trigger('click');
                });
            }
        }
    };
    httploc.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    httploc.setRequestHeader("Content-length", getstr.length);
    httploc.setRequestHeader("Connection", "close");
    httploc.send(getstr);
    document.getElementById('dedittravel').innerHTML = "<div><img src='../assets/images/loading.gif' align='absmiddle'/>Please wait. Loading...</div>";
    $('#edit_travel_details_modal').modal('show');
}

function getEditAccommdetails(id)
{
    var httploc = getHTTPObject();
    var url_sort = "../ajax/mobilization/getupdateaccommdtls.jsp";
    var getstr = "";
    getstr += "mobilizationid=" + id;
    httploc.open("POST", url_sort, true);
    httploc.onreadystatechange = function ()
    {
        if (httploc.readyState == 4)
        {
            if (httploc.status == 200)
            {
                var response = httploc.responseText;
                document.getElementById('deditaccomm').innerHTML = '';
                document.getElementById('deditaccomm').innerHTML = response;
                //Datepicker
                $(".kt-selectpicker").selectpicker();
                $(".wesl_dt").datepicker({
                    todayHighlight: !0,
                    format: "dd-M-yyyy",
                    autoclose: "true",
                    orientation: "bottom"
                });
                //Timepicker
                $(".timepicker-24").timepicker({
                    format: 'mm:mm',
                    autoclose: !0,
                    minuteStep: 5,
                    showSeconds: !1,
                    showMeridian: !1
                });
                $("#upload_link_1").on('click', function (e) {
                    e.preventDefault();
                    $("#upload1:hidden").trigger('click');
                });
            }
        }
    };
    httploc.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    httploc.setRequestHeader("Content-length", getstr.length);
    httploc.setRequestHeader("Connection", "close");
    httploc.send(getstr);
    document.getElementById('deditaccomm').innerHTML = "<div><img src='../assets/images/loading.gif' align='absmiddle'/>Please wait. Loading...</div>";
    $('#edit_accom_details_modal').modal('show');
}


function getSaveMobibyId(id, type) 
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
    if (checkMobDtls(type))
    {
        document.mobilizationForm.mobilizationId.value = id;
        if (document.mobilizationForm.doSaveMob)
            document.mobilizationForm.doSaveMob.value = "yes";
        document.forms[0].target = "";
        document.mobilizationForm.action = "../mobilization/MobilizationAction.do";
        document.mobilizationForm.submit();
    }
}

function handleKeySearch1(e)
{
    var key = e.keyCode || e.which;
    if (key === 13)
    {
        e.preventDefault();
        getCrewCandList();
    }
}

function getCrewCandList() {
    var httploc = getHTTPObject();
    var url_sort = "../ajax/mobilization/getmobilizationdtls.jsp";
    var getstr = "";
    getstr += "search=" + document.getElementById("txtsearch").value;
    getstr += "&clientid=" + document.mobilizationForm.clientId.value;
    getstr += "&assetid=" + document.mobilizationForm.clientassetId.value;
    getstr += "&positionid=" + document.mobilizationForm.positionId.value;
    httploc.open("POST", url_sort, true);
    httploc.onreadystatechange = function ()
    {
        if (httploc.readyState == 4)
        {
            if (httploc.status == 200)
            {
                var response = httploc.responseText;
                document.getElementById('dCRCandidateList').innerHTML = '';
                document.getElementById('dCRCandidateList').innerHTML = response;
            }
        }
    };
    httploc.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    httploc.setRequestHeader("Content-length", getstr.length);
    httploc.setRequestHeader("Connection", "close");
    httploc.send(getstr);
    document.getElementById('dCRCandidateList').innerHTML = "<div><img src='../assets/images/loading.gif' align='absmiddle'/>Please wait. Loading...</div>";
}

function getHistoryMailDetails(crewId, type) {
    var httploc = getHTTPObject();
    var url_sort = "../ajax/mobilization/getmobmailsendhistory.jsp";
    var count = Number(document.getElementById("mobFileMailCount").value);
    var ids = "";
    for (var i = 0; i < count; i++) {
        if (document.getElementById("chkmobId_" + i) && document.getElementById("chkmobId_" + i).checked == true)
            ids += document.getElementById("chkmobId_" + i).value + ",";
    }
    if (ids != "") {
        ids = ids.substr(0, ids.length - 1);
    }
    if (ids == "") {
        Swal.fire({
            title: "Please select atleast one data.",
                })
        return false;
    }
    var getstr = "";
    getstr += "mobids=" + ids;
    getstr += "&crewroationid=" + crewId;
    getstr += "&clientid=" + document.mobilizationForm.clientId.value;
    getstr += "&clientassetid=" + document.mobilizationForm.clientassetId.value;
    getstr += "&type=" + type;
    httploc.open("POST", url_sort, true);
    httploc.onreadystatechange = function ()
    {
        if (httploc.readyState == 4)
        {
            if (httploc.status == 200)
            {
                var response = httploc.responseText;
                document.getElementById('dhistorymaildetails').innerHTML = '';
                document.getElementById('dhistorymaildetails').innerHTML = response;
            }
        }
    };
    httploc.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    httploc.setRequestHeader("Content-length", getstr.length);
    httploc.setRequestHeader("Connection", "close");
    httploc.send(getstr);
    document.getElementById('dhistorymaildetails').innerHTML = "<div><img src='../assets/images/loading.gif' align='absmiddle'/>Please wait. Loading...</div>";
    $('#email_candidate_modal').modal('show');
}

function exporttoexcelTravel()
{
    document.mobilizationForm.action = "../mobilization/MobilizationExportTravelAction.do";
    document.mobilizationForm.submit();
}

function exporttoexcelAccom()
{
    document.mobilizationForm.action = "../mobilization/MobilizationAccomExportAction.do";
    document.mobilizationForm.submit();
}