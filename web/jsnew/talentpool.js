function resetFilter()
{
    document.talentpoolForm.search.value = "";
    document.talentpoolForm.clientIndex.value = "-1"
    document.talentpoolForm.assetIndex.value = "-1"
    document.talentpoolForm.positionIndexId.value = "0"
    document.talentpoolForm.locationIndex.value = "-1"
    document.talentpoolForm.employementstatus.value = "-1"
    document.talentpoolForm.assettypeIdIndex.value = "-1"
    document.talentpoolForm.positionFilterId.value = "-1"
    setAssetDDL();
    setPositionFilter();
    searchFormAjax('s', '-1');
}

function showDetail(id)
{
    document.talentpoolForm.doView.value = "yes";
    document.talentpoolForm.candidateId.value = id;
    document.talentpoolForm.action = "../talentpool/TalentpoolAction.do";
    document.talentpoolForm.submit();
}

function view(id)
{
    document.talentpoolForm.doView.value = "yes";
    document.talentpoolForm.candidateId.value = id;
    document.talentpoolForm.action = "../talentpool/TalentpoolAction.do";
    document.talentpoolForm.submit();
}

function view1()
{
    document.talentpoolForm.doView.value = "yes";
    document.talentpoolForm.doCancel.value = "no";
    if (document.talentpoolForm.doViewlangdetail)
        document.talentpoolForm.doViewlangdetail.value = "no";
    if (document.talentpoolForm.doViewhealthdetail)
        document.talentpoolForm.doViewhealthdetail.value = "no";
    if (document.talentpoolForm.doViewBanklist)
        document.talentpoolForm.doViewBanklist.value = "no";
    if (document.talentpoolForm.doViewvaccinationlist)
        document.talentpoolForm.doViewvaccinationlist.value = "no";
    if (document.talentpoolForm.doViewgovdocumentlist)
        document.talentpoolForm.doViewgovdocumentlist.value = "no";
    if (document.talentpoolForm.doViewtrainingcertlist)
        document.talentpoolForm.doViewtrainingcertlist.value = "no";
    if (document.talentpoolForm.doVieweducationlist)
        document.talentpoolForm.doVieweducationlist.value = "no";
    if (document.talentpoolForm.doDeletelangdetail)
        document.talentpoolForm.doDeletelangdetail.value = "no";
    if (document.talentpoolForm.doViewexperiencelist)
        document.talentpoolForm.doViewexperiencelist.value = "no";
    if (document.talentpoolForm.doViewWellnessfeedbacklist)
        document.talentpoolForm.doViewWellnessfeedbacklist.value = "no";
    document.talentpoolForm.action = "../talentpool/TalentpoolAction.do";
    document.talentpoolForm.submit();
}

function setCandidateClass(tp)
{
    if (!(document.talentpoolForm.photofile.value).match(/(\.(png)|(jpg)|(jpeg))$/i))
    {
        Swal.fire({
            title: "Only .jpg, .jpeg, .png are allowed.",
            didClose: () => {
                document.talentpoolForm.photofile.focus();
            }
        })
    }
    if (document.talentpoolForm.photofile.value != "")
    {
        var input = document.talentpoolForm.photofile;
        if (input.files)
        {
            var file = input.files[0];
            if (file.size > (1024 * 1024 * 5))
            {
                Swal.fire({
                    title: "File size should not exceed 5 MB.",
                    didClose: () => {
                        document.talentpoolForm.photofile.focus();
                    }
                })
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
                        document.getElementById("photo_profile_id").src = srcData;
                    }
                    fileReader.readAsDataURL(fileToLoad);
                }
            }
        }
    }
}

function addForm()
{
    document.talentpoolForm.doModify.value = "no";
    document.talentpoolForm.doView.value = "no";
    document.talentpoolForm.doCancel.value = "no";
    document.talentpoolForm.doAdd.value = "yes";
    document.talentpoolForm.action = "../talentpool/TalentpoolAction.do";
    document.talentpoolForm.submit();
}

function modifyForm(id)
{
    document.talentpoolForm.doModify.value = "yes";
    document.talentpoolForm.doView.value = "no";
    document.talentpoolForm.doAdd.value = "no";
    document.talentpoolForm.doCancel.value = "no";
    document.talentpoolForm.candidateId.value = id;
    document.talentpoolForm.action = "../talentpool/TalentpoolAction.do";
    document.talentpoolForm.submit();
}

function modifyFormview()
{
    document.talentpoolForm.doModify.value = "yes";
    document.talentpoolForm.doCancel.value = "no";
    document.talentpoolForm.action = "../talentpool/TalentpoolAction.do";
    document.talentpoolForm.submit();
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
        var url = "../ajax/talentpool/getinfo.jsp";
        var httploc = getHTTPObject();
        var getstr = "";
        var verified = 0;
        if ((document.getElementById("switch1")) && (document.getElementById("switch1").checked))
            verified = 1;
        var next_value = escape(document.talentpoolForm.nextValue.value);
        var search_value = escape(document.talentpoolForm.search.value);
        getstr += "nextValue=" + next_value;
        getstr += "&next=" + v;
        getstr += "&search=" + search_value;
        getstr += "&positionIndexId=" + document.talentpoolForm.positionIndexId.value;
        getstr += "&clientIndex=" + document.talentpoolForm.clientIndex.value;
        getstr += "&locationIndex=" + document.talentpoolForm.locationIndex.value;
        getstr += "&assetIndex=" + document.talentpoolForm.assetIndex.value;
        getstr += "&employementIndex=" + document.talentpoolForm.employementstatus.value;
        getstr += "&assettypeIdIndex=" + document.talentpoolForm.assettypeIdIndex.value;
        getstr += "&positionFilter=" + document.talentpoolForm.positionFilterId.value;
        getstr += "&verified=" + verified;
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

function deleteForm(candidateId, status, id)
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
            var url = "../ajax/talentpool/getinfo.jsp";
            var getstr = "";
            var httploc = getHTTPObject();
            var next_value = escape(document.talentpoolForm.nextValue.value);
            var next_del = "-1";
            if (document.talentpoolForm.nextDel)
                next_del = escape(document.talentpoolForm.nextDel.value);
            var search_value = escape(document.talentpoolForm.search.value);
            getstr += "nextValue=" + next_value;
            getstr += "&nextDel=" + next_del;
            getstr += "&search=" + search_value;
            getstr += "&status=" + status;
            getstr += "&deleteVal=" + candidateId;
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
    })
}

function goback()
{
    if (document.talentpoolForm.doView)
        document.talentpoolForm.doView.value = "no";
    if (document.talentpoolForm.doCancel)
        document.talentpoolForm.doCancel.value = "yes";
    if (document.talentpoolForm.doSave)
        document.talentpoolForm.doSave.value = "no";
    if (document.talentpoolForm.doModify)
        document.talentpoolForm.doModify.value = "no";
    document.talentpoolForm.action = "../talentpool/TalentpoolAction.do";
    document.talentpoolForm.submit();
}

function sortForm(colid, updown)
{
    for (i = 1; i <= 6; i++)
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
    var url_sort = "../ajax/talentpool/sort.jsp";
    var getstr = "";
    var nextValue = 0;
    if (document.talentpoolForm.nextValue)
        nextValue = document.talentpoolForm.nextValue.value;
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
    document.talentpoolForm.reset();
}

function resetFormcandidateindex()
{
    document.talentpoolForm.reset();
}

function showhidetr(tp)
{
    if (document.getElementById("tr_" + tp).style.display == "none")
        document.getElementById("tr_" + tp).style.display = "";
    else
        document.getElementById("tr_" + tp).style.display = "none";
}

function setClass(tp)
{
    document.getElementById("upload_link_" + tp).className = "attache_btn uploaded_img";
}

function setDocumentissuedbyDDL()
{
    var url = "../ajax/talentpool/getdocumentissuedby.jsp";
    var httploc = getHTTPObject();
    var getstr = "documentTypeId=" + document.talentpoolForm.documentTypeId.value;
    httploc.open("POST", url, true);
    httploc.onreadystatechange = function ()
    {
        if (httploc.readyState == 4)
        {
            if (httploc.status == 200)
            {
                var response = httploc.responseText;
                document.getElementById("documentissuedbyId").innerHTML = '';
                document.getElementById("documentissuedbyId").innerHTML = response;
            }
        }
    };
    httploc.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    httploc.setRequestHeader("Content-length", getstr.length);
    httploc.setRequestHeader("Connection", "close");
    httploc.send(getstr);
}

function setcitybyDDL()
{
    var url = "../ajax/talentpool/getcity.jsp";
    var httploc = getHTTPObject();
    var getstr = "countrytId=" + document.talentpoolForm.countryId.value;
    httploc.open("POST", url, true);
    httploc.onreadystatechange = function ()
    {
        if (httploc.readyState == 4)
        {
            if (httploc.status == 200)
            {
                var response = httploc.responseText;
                document.getElementById("cityId").innerHTML = '';
                document.getElementById("cityId").innerHTML = response;
            }
        }
    };
    httploc.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    httploc.setRequestHeader("Content-length", getstr.length);
    httploc.setRequestHeader("Connection", "close");
    httploc.send(getstr);
}

function openTab(tp)
{
    document.forms[0].target = "_self";
    if (tp == 3)
    {
        document.talentpoolForm.doModify.value = "no";
        document.talentpoolForm.doCancel.value = "no";
        if (document.talentpoolForm.doaddlangdetail)
            document.talentpoolForm.doaddlangdetail.value = "no";
        if (document.talentpoolForm.doViewhealthdetail)
            document.talentpoolForm.doViewhealthdetail.value = "no";
        if (document.talentpoolForm.doViewBanklist)
            document.talentpoolForm.doViewBanklist.value = "yes";
        if (document.talentpoolForm.doViewvaccinationlist)
            document.talentpoolForm.doViewvaccinationlist.value = "no";
        if (document.talentpoolForm.doViewgovdocumentlist)
            document.talentpoolForm.doViewgovdocumentlist.value = "no";
        if (document.talentpoolForm.doViewtrainingcertlist)
            document.talentpoolForm.doViewtrainingcertlist.value = "no";
        if (document.talentpoolForm.doVieweducationlist)
            document.talentpoolForm.doVieweducationlist.value = "no";
        if (document.talentpoolForm.doViewexperiencelist)
            document.talentpoolForm.doViewexperiencelist.value = "no";
        document.talentpoolForm.action = "../talentpool/TalentpoolAction.do";
        document.talentpoolForm.submit();
    }
    if (tp == 2)
    {
        document.talentpoolForm.doModify.value = "no";
        document.talentpoolForm.doCancel.value = "no";
        if (document.talentpoolForm.doViewlangdetail)
            document.talentpoolForm.doViewlangdetail.value = "yes";
        if (document.talentpoolForm.doViewhealthdetail)
            document.talentpoolForm.doViewhealthdetail.value = "no";
        if (document.talentpoolForm.doViewBanklist)
            document.talentpoolForm.doViewBanklist.value = "no";
        if (document.talentpoolForm.doViewvaccinationlist)
            document.talentpoolForm.doViewvaccinationlist.value = "no";
        if (document.talentpoolForm.doViewgovdocumentlist)
            document.talentpoolForm.doViewgovdocumentlist.value = "no";
        if (document.talentpoolForm.doViewtrainingcertlist)
            document.talentpoolForm.doViewtrainingcertlist.value = "no";
        if (document.talentpoolForm.doVieweducationlist)
            document.talentpoolForm.doVieweducationlist.value = "no";
        if (document.talentpoolForm.doDeletelangdetail)
            document.talentpoolForm.doDeletelangdetail.value = "no";
        if (document.talentpoolForm.doViewexperiencelist)
            document.talentpoolForm.doViewexperiencelist.value = "no";
        document.talentpoolForm.action = "../talentpool/TalentpoolAction.do";
        document.talentpoolForm.submit();
    }
    if (tp == 4)
    {
        document.talentpoolForm.doModify.value = "no";
        document.talentpoolForm.doCancel.value = "no";
        if (document.talentpoolForm.doaddlangdetail)
            document.talentpoolForm.doaddlangdetail.value = "no";
        if (document.talentpoolForm.doSavehealthdetail)
            document.talentpoolForm.doSavehealthdetail.value = "no";
        if (document.talentpoolForm.doViewhealthdetail)
            document.talentpoolForm.doViewhealthdetail.value = "yes";
        if (document.talentpoolForm.doViewBanklist)
            document.talentpoolForm.doViewBanklist.value = "no";
        if (document.talentpoolForm.doViewvaccinationlist)
            document.talentpoolForm.doViewvaccinationlist.value = "no";
        if (document.talentpoolForm.doViewgovdocumentlist)
            document.talentpoolForm.doViewgovdocumentlist.value = "no";
        if (document.talentpoolForm.doViewtrainingcertlist)
            document.talentpoolForm.doViewtrainingcertlist.value = "no";
        if (document.talentpoolForm.doVieweducationlist)
            document.talentpoolForm.doVieweducationlist.value = "no";
        if (document.talentpoolForm.doViewexperiencelist)
            document.talentpoolForm.doViewexperiencelist.value = "no";
        document.talentpoolForm.action = "../talentpool/TalentpoolAction.do";
        document.talentpoolForm.submit();
    }
    if (tp == 5)
    {
        document.talentpoolForm.doModify.value = "no";
        document.talentpoolForm.doCancel.value = "no";
        if (document.talentpoolForm.doaddlangdetail)
            document.talentpoolForm.doaddlangdetail.value = "no";
        if (document.talentpoolForm.doViewhealthdetail)
            document.talentpoolForm.doViewhealthdetail.value = "no";
        if (document.talentpoolForm.doViewBanklist)
            document.talentpoolForm.doViewBanklist.value = "no";
        if (document.talentpoolForm.doViewvaccinationlist)
            document.talentpoolForm.doViewvaccinationlist.value = "yes";
        if (document.talentpoolForm.doViewgovdocumentlist)
            document.talentpoolForm.doViewgovdocumentlist.value = "no";
        if (document.talentpoolForm.doViewtrainingcertlist)
            document.talentpoolForm.doViewtrainingcertlist.value = "no";
        if (document.talentpoolForm.doVieweducationlist)
            document.talentpoolForm.doVieweducationlist.value = "no";
        if (document.talentpoolForm.doViewexperiencelist)
            document.talentpoolForm.doViewexperiencelist.value = "no";
        document.talentpoolForm.action = "../talentpool/TalentpoolAction.do";
        document.talentpoolForm.submit();
    }
    if (tp == 6)
    {
        document.talentpoolForm.doModify.value = "no";
        document.talentpoolForm.doCancel.value = "no";
        if (document.talentpoolForm.doaddlangdetail)
            document.talentpoolForm.doaddlangdetail.value = "no";
        if (document.talentpoolForm.doViewhealthdetail)
            document.talentpoolForm.doViewhealthdetail.value = "no";
        if (document.talentpoolForm.doViewBanklist)
            document.talentpoolForm.doViewBanklist.value = "no";
        if (document.talentpoolForm.doViewvaccinationlist)
            document.talentpoolForm.doViewvaccinationlist.value = "no";
        if (document.talentpoolForm.doViewgovdocumentlist)
            document.talentpoolForm.doViewgovdocumentlist.value = "yes";
        if (document.talentpoolForm.doViewtrainingcertlist)
            document.talentpoolForm.doViewtrainingcertlist.value = "no";
        if (document.talentpoolForm.doVieweducationlist)
            document.talentpoolForm.doVieweducationlist.value = "no";
        if (document.talentpoolForm.doViewexperiencelist)
            document.talentpoolForm.doViewexperiencelist.value = "no";
        document.talentpoolForm.action = "../talentpool/TalentpoolAction.do";
        document.talentpoolForm.submit();
    }
    if (tp == 7)
    {
        document.talentpoolForm.doModify.value = "no";
        document.talentpoolForm.doCancel.value = "no";
        if (document.talentpoolForm.doaddlangdetail)
            document.talentpoolForm.doaddlangdetail.value = "no";
        if (document.talentpoolForm.doViewhealthdetail)
            document.talentpoolForm.doViewhealthdetail.value = "no";
        if (document.talentpoolForm.doViewBanklist)
            document.talentpoolForm.doViewBanklist.value = "no";
        if (document.talentpoolForm.doViewvaccinationlist)
            document.talentpoolForm.doViewvaccinationlist.value = "no";
        if (document.talentpoolForm.doViewgovdocumentlist)
            document.talentpoolForm.doViewgovdocumentlist.value = "no";
        if (document.talentpoolForm.doViewtrainingcertlist)
            document.talentpoolForm.doViewtrainingcertlist.value = "no";
        if (document.talentpoolForm.doVieweducationlist)
            document.talentpoolForm.doVieweducationlist.value = "no";
        if (document.talentpoolForm.doViewexperiencelist)
            document.talentpoolForm.doViewexperiencelist.value = "no";
        document.talentpoolForm.action = "../talentpool/TalentpoolAction.do";
        document.talentpoolForm.submit();
    }
    if (tp == 8)
    {
        document.talentpoolForm.doModify.value = "no";
        document.talentpoolForm.doCancel.value = "no";
        if (document.talentpoolForm.doaddlangdetail)
            document.talentpoolForm.doaddlangdetail.value = "no";
        if (document.talentpoolForm.doViewhealthdetail)
            document.talentpoolForm.doViewhealthdetail.value = "no";
        if (document.talentpoolForm.doViewBanklist)
            document.talentpoolForm.doViewBanklist.value = "no";
        if (document.talentpoolForm.doViewvaccinationlist)
            document.talentpoolForm.doViewvaccinationlist.value = "no";
        if (document.talentpoolForm.doViewgovdocumentlist)
            document.talentpoolForm.doViewgovdocumentlist.value = "no";
        if (document.talentpoolForm.doViewtrainingcertlist)
            document.talentpoolForm.doViewtrainingcertlist.value = "yes";
        if (document.talentpoolForm.doVieweducationlist)
            document.talentpoolForm.doVieweducationlist.value = "no";
        if (document.talentpoolForm.doViewexperiencelist)
            document.talentpoolForm.doViewexperiencelist.value = "no";
        document.talentpoolForm.action = "../talentpool/TalentpoolAction.do";
        document.talentpoolForm.submit();
    }
    if (tp == 9)
    {
        document.talentpoolForm.doModify.value = "no";
        document.talentpoolForm.doCancel.value = "no";
        if (document.talentpoolForm.doaddlangdetail)
            document.talentpoolForm.doaddlangdetail.value = "no";
        if (document.talentpoolForm.doViewhealthdetail)
            document.talentpoolForm.doViewhealthdetail.value = "no";
        if (document.talentpoolForm.doViewBanklist)
            document.talentpoolForm.doViewBanklist.value = "no";
        if (document.talentpoolForm.doViewvaccinationlist)
            document.talentpoolForm.doViewvaccinationlist.value = "no";
        if (document.talentpoolForm.doViewgovdocumentlist)
            document.talentpoolForm.doViewgovdocumentlist.value = "no";
        if (document.talentpoolForm.doViewtrainingcertlist)
            document.talentpoolForm.doViewtrainingcertlist.value = "no";
        if (document.talentpoolForm.doVieweducationlist)
            document.talentpoolForm.doVieweducationlist.value = "yes";
        if (document.talentpoolForm.doViewexperiencelist)
            document.talentpoolForm.doViewexperiencelist.value = "no";
        document.talentpoolForm.action = "../talentpool/TalentpoolAction.do";
        document.talentpoolForm.submit();
    }
    if (tp == 10)
    {
        document.talentpoolForm.doModify.value = "no";
        document.talentpoolForm.doCancel.value = "no";
        if (document.talentpoolForm.doaddlangdetail)
            document.talentpoolForm.doaddlangdetail.value = "no";
        if (document.talentpoolForm.doViewhealthdetail)
            document.talentpoolForm.doViewhealthdetail.value = "no";
        if (document.talentpoolForm.doViewBanklist)
            document.talentpoolForm.doViewBanklist.value = "no";
        if (document.talentpoolForm.doViewvaccinationlist)
            document.talentpoolForm.doViewvaccinationlist.value = "no";
        if (document.talentpoolForm.doViewgovdocumentlist)
            document.talentpoolForm.doViewgovdocumentlist.value = "no";
        if (document.talentpoolForm.doViewtrainingcertlist)
            document.talentpoolForm.doViewtrainingcertlist.value = "no";
        if (document.talentpoolForm.doVieweducationlist)
            document.talentpoolForm.doVieweducationlist.value = "no";
        if (document.talentpoolForm.doViewexperiencelist)
            document.talentpoolForm.doViewexperiencelist.value = "yes";
        document.talentpoolForm.action = "../talentpool/TalentpoolAction.do";
        document.talentpoolForm.submit();
    }
    if (tp == 11)
    {
        document.talentpoolForm.doModify.value = "no";
        document.talentpoolForm.doCancel.value = "no";
        if (document.talentpoolForm.doaddlangdetail)
            document.talentpoolForm.doaddlangdetail.value = "no";
        if (document.talentpoolForm.doViewhealthdetail)
            document.talentpoolForm.doViewhealthdetail.value = "no";
        if (document.talentpoolForm.doViewBanklist)
            document.talentpoolForm.doViewBanklist.value = "no";
        if (document.talentpoolForm.doViewvaccinationlist)
            document.talentpoolForm.doViewvaccinationlist.value = "no";
        if (document.talentpoolForm.doViewgovdocumentlist)
            document.talentpoolForm.doViewgovdocumentlist.value = "no";
        if (document.talentpoolForm.doViewtrainingcertlist)
            document.talentpoolForm.doViewtrainingcertlist.value = "no";
        if (document.talentpoolForm.doVieweducationlist)
            document.talentpoolForm.doVieweducationlist.value = "no";
        if (document.talentpoolForm.doViewexperiencelist)
            document.talentpoolForm.doViewexperiencelist.value = "no";
        if (document.talentpoolForm.doViewVerificationlist)
            document.talentpoolForm.doViewVerificationlist.value = "yes";
        document.talentpoolForm.action = "../talentpool/TalentpoolAction.do";
        document.talentpoolForm.submit();
    }
    if (tp == 12)
    {
        document.talentpoolForm.doModify.value = "no";
        document.talentpoolForm.doCancel.value = "no";
        if (document.talentpoolForm.doaddlangdetail)
            document.talentpoolForm.doaddlangdetail.value = "no";
        if (document.talentpoolForm.doViewhealthdetail)
            document.talentpoolForm.doViewhealthdetail.value = "no";
        if (document.talentpoolForm.doViewBanklist)
            document.talentpoolForm.doViewBanklist.value = "no";
        if (document.talentpoolForm.doViewvaccinationlist)
            document.talentpoolForm.doViewvaccinationlist.value = "no";
        if (document.talentpoolForm.doViewgovdocumentlist)
            document.talentpoolForm.doViewgovdocumentlist.value = "no";
        if (document.talentpoolForm.doViewtrainingcertlist)
            document.talentpoolForm.doViewtrainingcertlist.value = "no";
        if (document.talentpoolForm.doVieweducationlist)
            document.talentpoolForm.doVieweducationlist.value = "no";
        if (document.talentpoolForm.doViewexperiencelist)
            document.talentpoolForm.doViewexperiencelist.value = "no";
        if (document.talentpoolForm.doViewVerificationlist)
            document.talentpoolForm.doViewVerificationlist.value = "no";
        if (document.talentpoolForm.doViewAssessmentlist)
            document.talentpoolForm.doViewAssessmentlist.value = "yes";
        document.talentpoolForm.action = "../talentpool/TalentpoolAction.do";
        document.talentpoolForm.submit();
    }
    if (tp == 13)
    {
        document.talentpoolForm.doModify.value = "no";
        document.talentpoolForm.doCancel.value = "no";
        if (document.talentpoolForm.doaddlangdetail)
            document.talentpoolForm.doaddlangdetail.value = "no";
        if (document.talentpoolForm.doViewhealthdetail)
            document.talentpoolForm.doViewhealthdetail.value = "no";
        if (document.talentpoolForm.doViewBanklist)
            document.talentpoolForm.doViewBanklist.value = "no";
        if (document.talentpoolForm.doViewvaccinationlist)
            document.talentpoolForm.doViewvaccinationlist.value = "no";
        if (document.talentpoolForm.doViewgovdocumentlist)
            document.talentpoolForm.doViewgovdocumentlist.value = "no";
        if (document.talentpoolForm.doViewtrainingcertlist)
            document.talentpoolForm.doViewtrainingcertlist.value = "no";
        if (document.talentpoolForm.doVieweducationlist)
            document.talentpoolForm.doVieweducationlist.value = "no";
        if (document.talentpoolForm.doViewexperiencelist)
            document.talentpoolForm.doViewexperiencelist.value = "no";
        if (document.talentpoolForm.doViewVerificationlist)
            document.talentpoolForm.doViewVerificationlist.value = "no";
        if (document.talentpoolForm.doViewAssessmentlist)
            document.talentpoolForm.doViewAssessmentlist.value = "no";
        if (document.talentpoolForm.doViewCompliancelist)
            document.talentpoolForm.doViewCompliancelist.value = "yes";
        document.talentpoolForm.action = "../talentpool/TalentpoolAction.do";
        document.talentpoolForm.submit();
    }
    if (tp == 14)
    {
        document.talentpoolForm.doModify.value = "no";
        document.talentpoolForm.doCancel.value = "no";
        if (document.talentpoolForm.doaddlangdetail)
            document.talentpoolForm.doaddlangdetail.value = "no";
        if (document.talentpoolForm.doViewhealthdetail)
            document.talentpoolForm.doViewhealthdetail.value = "no";
        if (document.talentpoolForm.doViewBanklist)
            document.talentpoolForm.doViewBanklist.value = "no";
        if (document.talentpoolForm.doViewvaccinationlist)
            document.talentpoolForm.doViewvaccinationlist.value = "no";
        if (document.talentpoolForm.doViewgovdocumentlist)
            document.talentpoolForm.doViewgovdocumentlist.value = "no";
        if (document.talentpoolForm.doViewtrainingcertlist)
            document.talentpoolForm.doViewtrainingcertlist.value = "no";
        if (document.talentpoolForm.doVieweducationlist)
            document.talentpoolForm.doVieweducationlist.value = "no";
        if (document.talentpoolForm.doViewexperiencelist)
            document.talentpoolForm.doViewexperiencelist.value = "no";
        if (document.talentpoolForm.doViewVerificationlist)
            document.talentpoolForm.doViewVerificationlist.value = "no";
        if (document.talentpoolForm.doViewAssessmentlist)
            document.talentpoolForm.doViewAssessmentlist.value = "no";
        if (document.talentpoolForm.doViewCompliancelist)
            document.talentpoolForm.doViewCompliancelist.value = "no";
        if (document.talentpoolForm.doViewShortlistinglist)
            document.talentpoolForm.doViewShortlistinglist.value = "yes";
        document.talentpoolForm.action = "../talentpool/TalentpoolAction.do";
        document.talentpoolForm.submit();
    }
    if (tp == 15)
    {
        document.talentpoolForm.doModify.value = "no";
        document.talentpoolForm.doCancel.value = "no";
        if (document.talentpoolForm.doaddlangdetail)
            document.talentpoolForm.doaddlangdetail.value = "no";
        if (document.talentpoolForm.doViewhealthdetail)
            document.talentpoolForm.doViewhealthdetail.value = "no";
        if (document.talentpoolForm.doViewBanklist)
            document.talentpoolForm.doViewBanklist.value = "no";
        if (document.talentpoolForm.doViewvaccinationlist)
            document.talentpoolForm.doViewvaccinationlist.value = "no";
        if (document.talentpoolForm.doViewgovdocumentlist)
            document.talentpoolForm.doViewgovdocumentlist.value = "no";
        if (document.talentpoolForm.doViewtrainingcertlist)
            document.talentpoolForm.doViewtrainingcertlist.value = "no";
        if (document.talentpoolForm.doVieweducationlist)
            document.talentpoolForm.doVieweducationlist.value = "no";
        if (document.talentpoolForm.doViewexperiencelist)
            document.talentpoolForm.doViewexperiencelist.value = "no";
        if (document.talentpoolForm.doViewVerificationlist)
            document.talentpoolForm.doViewVerificationlist.value = "no";
        if (document.talentpoolForm.doViewAssessmentlist)
            document.talentpoolForm.doViewAssessmentlist.value = "no";
        if (document.talentpoolForm.doViewCompliancelist)
            document.talentpoolForm.doViewCompliancelist.value = "no";
        if (document.talentpoolForm.doViewShortlistinglist)
            document.talentpoolForm.doViewShortlistinglist.value = "no";
        if (document.talentpoolForm.doViewClientselectionlist)
            document.talentpoolForm.doViewClientselectionlist.value = "yes";
        document.talentpoolForm.action = "../talentpool/TalentpoolAction.do";
        document.talentpoolForm.submit();
    }
    if (tp == 16)
    {
        document.talentpoolForm.doModify.value = "no";
        document.talentpoolForm.doCancel.value = "no";
        if (document.talentpoolForm.doaddlangdetail)
            document.talentpoolForm.doaddlangdetail.value = "no";
        if (document.talentpoolForm.doViewhealthdetail)
            document.talentpoolForm.doViewhealthdetail.value = "no";
        if (document.talentpoolForm.doViewBanklist)
            document.talentpoolForm.doViewBanklist.value = "no";
        if (document.talentpoolForm.doViewvaccinationlist)
            document.talentpoolForm.doViewvaccinationlist.value = "no";
        if (document.talentpoolForm.doViewgovdocumentlist)
            document.talentpoolForm.doViewgovdocumentlist.value = "no";
        if (document.talentpoolForm.doViewtrainingcertlist)
            document.talentpoolForm.doViewtrainingcertlist.value = "no";
        if (document.talentpoolForm.doVieweducationlist)
            document.talentpoolForm.doVieweducationlist.value = "no";
        if (document.talentpoolForm.doViewexperiencelist)
            document.talentpoolForm.doViewexperiencelist.value = "no";
        if (document.talentpoolForm.doViewVerificationlist)
            document.talentpoolForm.doViewVerificationlist.value = "no";
        if (document.talentpoolForm.doViewAssessmentlist)
            document.talentpoolForm.doViewAssessmentlist.value = "no";
        if (document.talentpoolForm.doViewCompliancelist)
            document.talentpoolForm.doViewCompliancelist.value = "no";
        if (document.talentpoolForm.doViewShortlistinglist)
            document.talentpoolForm.doViewShortlistinglist.value = "no";
        if (document.talentpoolForm.doViewClientselectionlist)
            document.talentpoolForm.doViewClientselectionlist.value = "no";
        if (document.talentpoolForm.doViewOnboardinglist)
            document.talentpoolForm.doViewOnboardinglist.value = "yes";
        document.talentpoolForm.action = "../talentpool/TalentpoolAction.do";
        document.talentpoolForm.submit();
    }
    if (tp == 17)
    {
        document.talentpoolForm.doModify.value = "no";
        document.talentpoolForm.doCancel.value = "no";
        if (document.talentpoolForm.doaddlangdetail)
            document.talentpoolForm.doaddlangdetail.value = "no";
        if (document.talentpoolForm.doViewhealthdetail)
            document.talentpoolForm.doViewhealthdetail.value = "no";
        if (document.talentpoolForm.doViewBanklist)
            document.talentpoolForm.doViewBanklist.value = "no";
        if (document.talentpoolForm.doViewvaccinationlist)
            document.talentpoolForm.doViewvaccinationlist.value = "no";
        if (document.talentpoolForm.doViewgovdocumentlist)
            document.talentpoolForm.doViewgovdocumentlist.value = "no";
        if (document.talentpoolForm.doViewtrainingcertlist)
            document.talentpoolForm.doViewtrainingcertlist.value = "no";
        if (document.talentpoolForm.doVieweducationlist)
            document.talentpoolForm.doVieweducationlist.value = "no";
        if (document.talentpoolForm.doViewexperiencelist)
            document.talentpoolForm.doViewexperiencelist.value = "no";
        if (document.talentpoolForm.doViewVerificationlist)
            document.talentpoolForm.doViewVerificationlist.value = "no";
        if (document.talentpoolForm.doViewAssessmentlist)
            document.talentpoolForm.doViewAssessmentlist.value = "no";
        if (document.talentpoolForm.doViewCompliancelist)
            document.talentpoolForm.doViewCompliancelist.value = "no";
        if (document.talentpoolForm.doViewShortlistinglist)
            document.talentpoolForm.doViewShortlistinglist.value = "no";
        if (document.talentpoolForm.doViewClientselectionlist)
            document.talentpoolForm.doViewClientselectionlist.value = "no";
        if (document.talentpoolForm.doViewOnboardinglist)
            document.talentpoolForm.doViewOnboardinglist.value = "no";
        if (document.talentpoolForm.doViewMobilizationlist)
            document.talentpoolForm.doViewMobilizationlist.value = "yes";
        document.talentpoolForm.action = "../talentpool/TalentpoolAction.do";
        document.talentpoolForm.submit();
    }
    if (tp == 18)
    {
        document.talentpoolForm.doModify.value = "no";
        document.talentpoolForm.doCancel.value = "no";
        if (document.talentpoolForm.doaddlangdetail)
            document.talentpoolForm.doaddlangdetail.value = "no";
        if (document.talentpoolForm.doViewhealthdetail)
            document.talentpoolForm.doViewhealthdetail.value = "no";
        if (document.talentpoolForm.doViewBanklist)
            document.talentpoolForm.doViewBanklist.value = "no";
        if (document.talentpoolForm.doViewvaccinationlist)
            document.talentpoolForm.doViewvaccinationlist.value = "no";
        if (document.talentpoolForm.doViewgovdocumentlist)
            document.talentpoolForm.doViewgovdocumentlist.value = "no";
        if (document.talentpoolForm.doViewtrainingcertlist)
            document.talentpoolForm.doViewtrainingcertlist.value = "no";
        if (document.talentpoolForm.doVieweducationlist)
            document.talentpoolForm.doVieweducationlist.value = "no";
        if (document.talentpoolForm.doViewexperiencelist)
            document.talentpoolForm.doViewexperiencelist.value = "no";
        if (document.talentpoolForm.doViewVerificationlist)
            document.talentpoolForm.doViewVerificationlist.value = "no";
        if (document.talentpoolForm.doViewAssessmentlist)
            document.talentpoolForm.doViewAssessmentlist.value = "no";
        if (document.talentpoolForm.doViewCompliancelist)
            document.talentpoolForm.doViewCompliancelist.value = "no";
        if (document.talentpoolForm.doViewShortlistinglist)
            document.talentpoolForm.doViewShortlistinglist.value = "no";
        if (document.talentpoolForm.doViewClientselectionlist)
            document.talentpoolForm.doViewClientselectionlist.value = "no";
        if (document.talentpoolForm.doViewOnboardinglist)
            document.talentpoolForm.doViewOnboardinglist.value = "no";
        if (document.talentpoolForm.doViewMobilizationlist)
            document.talentpoolForm.doViewMobilizationlist.value = "no";
        if (document.talentpoolForm.doViewRotationlist)
            document.talentpoolForm.doViewRotationlist.value = "yes";
        document.talentpoolForm.action = "../talentpool/TalentpoolAction.do";
        document.talentpoolForm.submit();
    }
    if (tp == 19)
    {
        document.talentpoolForm.doModify.value = "no";
        document.talentpoolForm.doCancel.value = "no";
        if (document.talentpoolForm.doaddlangdetail)
            document.talentpoolForm.doaddlangdetail.value = "no";
        if (document.talentpoolForm.doViewhealthdetail)
            document.talentpoolForm.doViewhealthdetail.value = "no";
        if (document.talentpoolForm.doViewBanklist)
            document.talentpoolForm.doViewBanklist.value = "no";
        if (document.talentpoolForm.doViewvaccinationlist)
            document.talentpoolForm.doViewvaccinationlist.value = "no";
        if (document.talentpoolForm.doViewgovdocumentlist)
            document.talentpoolForm.doViewgovdocumentlist.value = "no";
        if (document.talentpoolForm.doViewtrainingcertlist)
            document.talentpoolForm.doViewtrainingcertlist.value = "no";
        if (document.talentpoolForm.doVieweducationlist)
            document.talentpoolForm.doVieweducationlist.value = "no";
        if (document.talentpoolForm.doViewexperiencelist)
            document.talentpoolForm.doViewexperiencelist.value = "no";
        if (document.talentpoolForm.doViewVerificationlist)
            document.talentpoolForm.doViewVerificationlist.value = "no";
        if (document.talentpoolForm.doViewAssessmentlist)
            document.talentpoolForm.doViewAssessmentlist.value = "no";
        if (document.talentpoolForm.doViewCompliancelist)
            document.talentpoolForm.doViewCompliancelist.value = "no";
        if (document.talentpoolForm.doViewShortlistinglist)
            document.talentpoolForm.doViewShortlistinglist.value = "no";
        if (document.talentpoolForm.doViewClientselectionlist)
            document.talentpoolForm.doViewClientselectionlist.value = "no";
        if (document.talentpoolForm.doViewOnboardinglist)
            document.talentpoolForm.doViewOnboardinglist.value = "no";
        if (document.talentpoolForm.doViewMobilizationlist)
            document.talentpoolForm.doViewMobilizationlist.value = "no";
        if (document.talentpoolForm.doViewRotationlist)
            document.talentpoolForm.doViewRotationlist.value = "no";
        if (document.talentpoolForm.doViewNotificationlist)
            document.talentpoolForm.doViewNotificationlist.value = "yes";
        document.talentpoolForm.action = "../talentpool/TalentpoolAction.do";
        document.talentpoolForm.submit();
    }
    if (tp == 20)
    {
        document.talentpoolForm.doModify.value = "no";
        document.talentpoolForm.doCancel.value = "no";
        if (document.talentpoolForm.doaddlangdetail)
            document.talentpoolForm.doaddlangdetail.value = "no";
        if (document.talentpoolForm.doViewhealthdetail)
            document.talentpoolForm.doViewhealthdetail.value = "no";
        if (document.talentpoolForm.doViewBanklist)
            document.talentpoolForm.doViewBanklist.value = "no";
        if (document.talentpoolForm.doViewvaccinationlist)
            document.talentpoolForm.doViewvaccinationlist.value = "no";
        if (document.talentpoolForm.doViewgovdocumentlist)
            document.talentpoolForm.doViewgovdocumentlist.value = "no";
        if (document.talentpoolForm.doViewtrainingcertlist)
            document.talentpoolForm.doViewtrainingcertlist.value = "no";
        if (document.talentpoolForm.doVieweducationlist)
            document.talentpoolForm.doVieweducationlist.value = "no";
        if (document.talentpoolForm.doViewexperiencelist)
            document.talentpoolForm.doViewexperiencelist.value = "no";
        if (document.talentpoolForm.doViewVerificationlist)
            document.talentpoolForm.doViewVerificationlist.value = "no";
        if (document.talentpoolForm.doViewAssessmentlist)
            document.talentpoolForm.doViewAssessmentlist.value = "no";
        if (document.talentpoolForm.doViewCompliancelist)
            document.talentpoolForm.doViewCompliancelist.value = "no";
        if (document.talentpoolForm.doViewShortlistinglist)
            document.talentpoolForm.doViewShortlistinglist.value = "no";
        if (document.talentpoolForm.doViewClientselectionlist)
            document.talentpoolForm.doViewClientselectionlist.value = "no";
        if (document.talentpoolForm.doViewOnboardinglist)
            document.talentpoolForm.doViewOnboardinglist.value = "no";
        if (document.talentpoolForm.doViewMobilizationlist)
            document.talentpoolForm.doViewMobilizationlist.value = "no";
        if (document.talentpoolForm.doViewRotationlist)
            document.talentpoolForm.doViewRotationlist.value = "no";
        if (document.talentpoolForm.doViewNotificationlist)
            document.talentpoolForm.doViewNotificationlist.value = "no";
        if (document.talentpoolForm.doViewTransferterminationlist)
            document.talentpoolForm.doViewTransferterminationlist.value = "yes";
        document.talentpoolForm.action = "../talentpool/TalentpoolAction.do";
        document.talentpoolForm.submit();
    }
    if (tp == 21)
    {
        document.talentpoolForm.doModify.value = "no";
        document.talentpoolForm.doCancel.value = "no";
        if (document.talentpoolForm.doaddlangdetail)
            document.talentpoolForm.doaddlangdetail.value = "no";
        if (document.talentpoolForm.doViewhealthdetail)
            document.talentpoolForm.doViewhealthdetail.value = "no";
        if (document.talentpoolForm.doViewBanklist)
            document.talentpoolForm.doViewBanklist.value = "no";
        if (document.talentpoolForm.doViewvaccinationlist)
            document.talentpoolForm.doViewvaccinationlist.value = "no";
        if (document.talentpoolForm.doViewgovdocumentlist)
            document.talentpoolForm.doViewgovdocumentlist.value = "no";
        if (document.talentpoolForm.doViewtrainingcertlist)
            document.talentpoolForm.doViewtrainingcertlist.value = "no";
        if (document.talentpoolForm.doVieweducationlist)
            document.talentpoolForm.doVieweducationlist.value = "no";
        if (document.talentpoolForm.doViewexperiencelist)
            document.talentpoolForm.doViewexperiencelist.value = "no";
        if (document.talentpoolForm.doViewVerificationlist)
            document.talentpoolForm.doViewVerificationlist.value = "no";
        if (document.talentpoolForm.doViewAssessmentlist)
            document.talentpoolForm.doViewAssessmentlist.value = "no";
        if (document.talentpoolForm.doViewCompliancelist)
            document.talentpoolForm.doViewCompliancelist.value = "no";
        if (document.talentpoolForm.doViewShortlistinglist)
            document.talentpoolForm.doViewShortlistinglist.value = "no";
        if (document.talentpoolForm.doViewClientselectionlist)
            document.talentpoolForm.doViewClientselectionlist.value = "no";
        if (document.talentpoolForm.doViewOnboardinglist)
            document.talentpoolForm.doViewOnboardinglist.value = "no";
        if (document.talentpoolForm.doViewMobilizationlist)
            document.talentpoolForm.doViewMobilizationlist.value = "no";
        if (document.talentpoolForm.doViewRotationlist)
            document.talentpoolForm.doViewRotationlist.value = "no";
        if (document.talentpoolForm.doViewNotificationlist)
            document.talentpoolForm.doViewNotificationlist.value = "no";
        if (document.talentpoolForm.doViewTransferterminationlist)
            document.talentpoolForm.doViewTransferterminationlist.value = "no";
        if (document.talentpoolForm.doViewWellnessfeedbacklist)
            document.talentpoolForm.doViewWellnessfeedbacklist.value = "yes";
        document.talentpoolForm.action = "../talentpool/TalentpoolAction.do";
        document.talentpoolForm.submit();
    }
    if (tp == 22)
    {
        document.talentpoolForm.doModify.value = "no";
        document.talentpoolForm.doCancel.value = "no";
        if (document.talentpoolForm.doaddlangdetail)
            document.talentpoolForm.doaddlangdetail.value = "no";
        if (document.talentpoolForm.doViewhealthdetail)
            document.talentpoolForm.doViewhealthdetail.value = "no";
        if (document.talentpoolForm.doViewBanklist)
            document.talentpoolForm.doViewBanklist.value = "no";
        if (document.talentpoolForm.doViewvaccinationlist)
            document.talentpoolForm.doViewvaccinationlist.value = "no";
        if (document.talentpoolForm.doViewgovdocumentlist)
            document.talentpoolForm.doViewgovdocumentlist.value = "no";
        if (document.talentpoolForm.doViewtrainingcertlist)
            document.talentpoolForm.doViewtrainingcertlist.value = "no";
        if (document.talentpoolForm.doVieweducationlist)
            document.talentpoolForm.doVieweducationlist.value = "no";
        if (document.talentpoolForm.doViewexperiencelist)
            document.talentpoolForm.doViewexperiencelist.value = "no";
        if (document.talentpoolForm.doViewVerificationlist)
            document.talentpoolForm.doViewVerificationlist.value = "no";
        if (document.talentpoolForm.doViewAssessmentlist)
            document.talentpoolForm.doViewAssessmentlist.value = "no";
        if (document.talentpoolForm.doViewCompliancelist)
            document.talentpoolForm.doViewCompliancelist.value = "no";
        if (document.talentpoolForm.doViewShortlistinglist)
            document.talentpoolForm.doViewShortlistinglist.value = "no";
        if (document.talentpoolForm.doViewClientselectionlist)
            document.talentpoolForm.doViewClientselectionlist.value = "no";
        if (document.talentpoolForm.doViewOnboardinglist)
            document.talentpoolForm.doViewOnboardinglist.value = "no";
        if (document.talentpoolForm.doViewMobilizationlist)
            document.talentpoolForm.doViewMobilizationlist.value = "no";
        if (document.talentpoolForm.doViewRotationlist)
            document.talentpoolForm.doViewRotationlist.value = "no";
        if (document.talentpoolForm.doViewNotificationlist)
            document.talentpoolForm.doViewNotificationlist.value = "no";
        if (document.talentpoolForm.doViewTransferterminationlist)
            document.talentpoolForm.doViewTransferterminationlist.value = "no";
        if (document.talentpoolForm.doViewWellnessfeedbacklist)
            document.talentpoolForm.doViewWellnessfeedbacklist.value = "no";
        if (document.talentpoolForm.doViewCompetencylist)
            document.talentpoolForm.doViewCompetencylist.value = "yes";
        document.talentpoolForm.action = "../talentpool/TalentpoolAction.do";
        document.talentpoolForm.submit();
    }
    if (tp == 23)
    {
        document.talentpoolForm.doModify.value = "no";
        document.talentpoolForm.doCancel.value = "no";
        if (document.talentpoolForm.doaddlangdetail)
            document.talentpoolForm.doaddlangdetail.value = "no";
        if (document.talentpoolForm.doViewhealthdetail)
            document.talentpoolForm.doViewhealthdetail.value = "no";
        if (document.talentpoolForm.doViewBanklist)
            document.talentpoolForm.doViewBanklist.value = "no";
        if (document.talentpoolForm.doViewvaccinationlist)
            document.talentpoolForm.doViewvaccinationlist.value = "no";
        if (document.talentpoolForm.doViewgovdocumentlist)
            document.talentpoolForm.doViewgovdocumentlist.value = "no";
        if (document.talentpoolForm.doViewtrainingcertlist)
            document.talentpoolForm.doViewtrainingcertlist.value = "no";
        if (document.talentpoolForm.doVieweducationlist)
            document.talentpoolForm.doVieweducationlist.value = "no";
        if (document.talentpoolForm.doViewexperiencelist)
            document.talentpoolForm.doViewexperiencelist.value = "no";
        if (document.talentpoolForm.doViewVerificationlist)
            document.talentpoolForm.doViewVerificationlist.value = "no";
        if (document.talentpoolForm.doViewAssessmentlist)
            document.talentpoolForm.doViewAssessmentlist.value = "no";
        if (document.talentpoolForm.doViewCompliancelist)
            document.talentpoolForm.doViewCompliancelist.value = "no";
        if (document.talentpoolForm.doViewShortlistinglist)
            document.talentpoolForm.doViewShortlistinglist.value = "no";
        if (document.talentpoolForm.doViewClientselectionlist)
            document.talentpoolForm.doViewClientselectionlist.value = "no";
        if (document.talentpoolForm.doViewOnboardinglist)
            document.talentpoolForm.doViewOnboardinglist.value = "no";
        if (document.talentpoolForm.doViewMobilizationlist)
            document.talentpoolForm.doViewMobilizationlist.value = "no";
        if (document.talentpoolForm.doViewRotationlist)
            document.talentpoolForm.doViewRotationlist.value = "no";
        if (document.talentpoolForm.doViewNotificationlist)
            document.talentpoolForm.doViewNotificationlist.value = "no";
        if (document.talentpoolForm.doViewTransferterminationlist)
            document.talentpoolForm.doViewTransferterminationlist.value = "no";
        if (document.talentpoolForm.doViewWellnessfeedbacklist)
            document.talentpoolForm.doViewWellnessfeedbacklist.value = "no";
        if (document.talentpoolForm.doViewCompetencylist)
            document.talentpoolForm.doViewCompetencylist.value = "no";
        if (document.talentpoolForm.doViewoffshoredetail)
            document.talentpoolForm.doViewoffshoredetail.value = "yes";
        document.talentpoolForm.action = "../talentpool/TalentpoolAction.do";
        document.talentpoolForm.submit();
    }
    if (tp == 24)
    {
        document.talentpoolForm.doModify.value = "no";
        document.talentpoolForm.doCancel.value = "no";
        if (document.talentpoolForm.doaddlangdetail)
            document.talentpoolForm.doaddlangdetail.value = "no";
        if (document.talentpoolForm.doViewhealthdetail)
            document.talentpoolForm.doViewhealthdetail.value = "no";
        if (document.talentpoolForm.doViewBanklist)
            document.talentpoolForm.doViewBanklist.value = "no";
        if (document.talentpoolForm.doViewvaccinationlist)
            document.talentpoolForm.doViewvaccinationlist.value = "no";
        if (document.talentpoolForm.doViewgovdocumentlist)
            document.talentpoolForm.doViewgovdocumentlist.value = "no";
        if (document.talentpoolForm.doViewtrainingcertlist)
            document.talentpoolForm.doViewtrainingcertlist.value = "no";
        if (document.talentpoolForm.doVieweducationlist)
            document.talentpoolForm.doVieweducationlist.value = "no";
        if (document.talentpoolForm.doViewexperiencelist)
            document.talentpoolForm.doViewexperiencelist.value = "no";
        if (document.talentpoolForm.doViewVerificationlist)
            document.talentpoolForm.doViewVerificationlist.value = "no";
        if (document.talentpoolForm.doViewAssessmentlist)
            document.talentpoolForm.doViewAssessmentlist.value = "no";
        if (document.talentpoolForm.doViewCompliancelist)
            document.talentpoolForm.doViewCompliancelist.value = "no";
        if (document.talentpoolForm.doViewShortlistinglist)
            document.talentpoolForm.doViewShortlistinglist.value = "no";
        if (document.talentpoolForm.doViewClientselectionlist)
            document.talentpoolForm.doViewClientselectionlist.value = "no";
        if (document.talentpoolForm.doViewOnboardinglist)
            document.talentpoolForm.doViewOnboardinglist.value = "no";
        if (document.talentpoolForm.doViewMobilizationlist)
            document.talentpoolForm.doViewMobilizationlist.value = "no";
        if (document.talentpoolForm.doViewRotationlist)
            document.talentpoolForm.doViewRotationlist.value = "no";
        if (document.talentpoolForm.doViewNotificationlist)
            document.talentpoolForm.doViewNotificationlist.value = "no";
        if (document.talentpoolForm.doViewTransferterminationlist)
            document.talentpoolForm.doViewTransferterminationlist.value = "no";
        if (document.talentpoolForm.doViewWellnessfeedbacklist)
            document.talentpoolForm.doViewWellnessfeedbacklist.value = "no";
        if (document.talentpoolForm.doViewCompetencylist)
            document.talentpoolForm.doViewCompetencylist.value = "no";
        if (document.talentpoolForm.doViewoffshoredetail)
            document.talentpoolForm.doViewoffshoredetail.value = "no";
        if (document.talentpoolForm.doViewNomineelist)
            document.talentpoolForm.doViewNomineelist.value = "yes";
        document.talentpoolForm.action = "../talentpool/TalentpoolAction.do";
        document.talentpoolForm.submit();
    }
    if (tp == 25)
    {
        document.talentpoolForm.doModify.value = "no";
        document.talentpoolForm.doCancel.value = "no";
        if (document.talentpoolForm.doaddlangdetail)
            document.talentpoolForm.doaddlangdetail.value = "no";
        if (document.talentpoolForm.doViewhealthdetail)
            document.talentpoolForm.doViewhealthdetail.value = "no";
        if (document.talentpoolForm.doViewBanklist)
            document.talentpoolForm.doViewBanklist.value = "no";
        if (document.talentpoolForm.doViewvaccinationlist)
            document.talentpoolForm.doViewvaccinationlist.value = "no";
        if (document.talentpoolForm.doViewgovdocumentlist)
            document.talentpoolForm.doViewgovdocumentlist.value = "no";
        if (document.talentpoolForm.doViewtrainingcertlist)
            document.talentpoolForm.doViewtrainingcertlist.value = "no";
        if (document.talentpoolForm.doVieweducationlist)
            document.talentpoolForm.doVieweducationlist.value = "no";
        if (document.talentpoolForm.doViewexperiencelist)
            document.talentpoolForm.doViewexperiencelist.value = "no";
        if (document.talentpoolForm.doViewVerificationlist)
            document.talentpoolForm.doViewVerificationlist.value = "no";
        if (document.talentpoolForm.doViewAssessmentlist)
            document.talentpoolForm.doViewAssessmentlist.value = "no";
        if (document.talentpoolForm.doViewCompliancelist)
            document.talentpoolForm.doViewCompliancelist.value = "no";
        if (document.talentpoolForm.doViewShortlistinglist)
            document.talentpoolForm.doViewShortlistinglist.value = "no";
        if (document.talentpoolForm.doViewClientselectionlist)
            document.talentpoolForm.doViewClientselectionlist.value = "no";
        if (document.talentpoolForm.doViewOnboardinglist)
            document.talentpoolForm.doViewOnboardinglist.value = "no";
        if (document.talentpoolForm.doViewMobilizationlist)
            document.talentpoolForm.doViewMobilizationlist.value = "no";
        if (document.talentpoolForm.doViewRotationlist)
            document.talentpoolForm.doViewRotationlist.value = "no";
        if (document.talentpoolForm.doViewNotificationlist)
            document.talentpoolForm.doViewNotificationlist.value = "no";
        if (document.talentpoolForm.doViewTransferterminationlist)
            document.talentpoolForm.doViewTransferterminationlist.value = "no";
        if (document.talentpoolForm.doViewWellnessfeedbacklist)
            document.talentpoolForm.doViewWellnessfeedbacklist.value = "no";
        if (document.talentpoolForm.doViewCompetencylist)
            document.talentpoolForm.doViewCompetencylist.value = "no";
        if (document.talentpoolForm.doViewoffshoredetail)
            document.talentpoolForm.doViewoffshoredetail.value = "no";
        if (document.talentpoolForm.doViewNomineelist)
            document.talentpoolForm.doViewNomineelist.value = "no";
        if (document.talentpoolForm.doViewPpelist)
            document.talentpoolForm.doViewPpelist.value = "yes";
        document.talentpoolForm.action = "../talentpool/TalentpoolAction.do";
        document.talentpoolForm.submit();
    }
    if (tp == 26)
    {
        document.talentpoolForm.doModify.value = "no";
        document.talentpoolForm.doCancel.value = "no";
        if (document.talentpoolForm.doaddlangdetail)
            document.talentpoolForm.doaddlangdetail.value = "no";
        if (document.talentpoolForm.doViewhealthdetail)
            document.talentpoolForm.doViewhealthdetail.value = "no";
        if (document.talentpoolForm.doViewBanklist)
            document.talentpoolForm.doViewBanklist.value = "no";
        if (document.talentpoolForm.doViewvaccinationlist)
            document.talentpoolForm.doViewvaccinationlist.value = "no";
        if (document.talentpoolForm.doViewgovdocumentlist)
            document.talentpoolForm.doViewgovdocumentlist.value = "no";
        if (document.talentpoolForm.doViewtrainingcertlist)
            document.talentpoolForm.doViewtrainingcertlist.value = "no";
        if (document.talentpoolForm.doVieweducationlist)
            document.talentpoolForm.doVieweducationlist.value = "no";
        if (document.talentpoolForm.doViewexperiencelist)
            document.talentpoolForm.doViewexperiencelist.value = "no";
        if (document.talentpoolForm.doViewVerificationlist)
            document.talentpoolForm.doViewVerificationlist.value = "no";
        if (document.talentpoolForm.doViewAssessmentlist)
            document.talentpoolForm.doViewAssessmentlist.value = "no";
        if (document.talentpoolForm.doViewCompliancelist)
            document.talentpoolForm.doViewCompliancelist.value = "no";
        if (document.talentpoolForm.doViewShortlistinglist)
            document.talentpoolForm.doViewShortlistinglist.value = "no";
        if (document.talentpoolForm.doViewClientselectionlist)
            document.talentpoolForm.doViewClientselectionlist.value = "no";
        if (document.talentpoolForm.doViewOnboardinglist)
            document.talentpoolForm.doViewOnboardinglist.value = "no";
        if (document.talentpoolForm.doViewMobilizationlist)
            document.talentpoolForm.doViewMobilizationlist.value = "no";
        if (document.talentpoolForm.doViewRotationlist)
            document.talentpoolForm.doViewRotationlist.value = "no";
        if (document.talentpoolForm.doViewNotificationlist)
            document.talentpoolForm.doViewNotificationlist.value = "no";
        if (document.talentpoolForm.doViewTransferterminationlist)
            document.talentpoolForm.doViewTransferterminationlist.value = "no";
        if (document.talentpoolForm.doViewWellnessfeedbacklist)
            document.talentpoolForm.doViewWellnessfeedbacklist.value = "no";
        if (document.talentpoolForm.doViewCompetencylist)
            document.talentpoolForm.doViewCompetencylist.value = "no";
        if (document.talentpoolForm.doViewoffshoredetail)
            document.talentpoolForm.doViewoffshoredetail.value = "no";
        if (document.talentpoolForm.doViewNomineelist)
            document.talentpoolForm.doViewNomineelist.value = "no";
        if (document.talentpoolForm.doViewPpelist)
            document.talentpoolForm.doViewPpelist.value = "no";
        if (document.talentpoolForm.doViewContractlist)
            document.talentpoolForm.doViewContractlist.value = "yes";
        document.talentpoolForm.action = "../talentpool/TalentpoolAction.do";
        document.talentpoolForm.submit();
    }
    if (tp == 27)
    {
        document.talentpoolForm.doModify.value = "no";
        document.talentpoolForm.doCancel.value = "no";
        if (document.talentpoolForm.doaddlangdetail)
            document.talentpoolForm.doaddlangdetail.value = "no";
        if (document.talentpoolForm.doViewhealthdetail)
            document.talentpoolForm.doViewhealthdetail.value = "no";
        if (document.talentpoolForm.doViewBanklist)
            document.talentpoolForm.doViewBanklist.value = "no";
        if (document.talentpoolForm.doViewvaccinationlist)
            document.talentpoolForm.doViewvaccinationlist.value = "no";
        if (document.talentpoolForm.doViewgovdocumentlist)
            document.talentpoolForm.doViewgovdocumentlist.value = "no";
        if (document.talentpoolForm.doViewtrainingcertlist)
            document.talentpoolForm.doViewtrainingcertlist.value = "no";
        if (document.talentpoolForm.doVieweducationlist)
            document.talentpoolForm.doVieweducationlist.value = "no";
        if (document.talentpoolForm.doViewexperiencelist)
            document.talentpoolForm.doViewexperiencelist.value = "no";
        if (document.talentpoolForm.doViewVerificationlist)
            document.talentpoolForm.doViewVerificationlist.value = "no";
        if (document.talentpoolForm.doViewAssessmentlist)
            document.talentpoolForm.doViewAssessmentlist.value = "no";
        if (document.talentpoolForm.doViewCompliancelist)
            document.talentpoolForm.doViewCompliancelist.value = "no";
        if (document.talentpoolForm.doViewShortlistinglist)
            document.talentpoolForm.doViewShortlistinglist.value = "no";
        if (document.talentpoolForm.doViewClientselectionlist)
            document.talentpoolForm.doViewClientselectionlist.value = "no";
        if (document.talentpoolForm.doViewOnboardinglist)
            document.talentpoolForm.doViewOnboardinglist.value = "no";
        if (document.talentpoolForm.doViewMobilizationlist)
            document.talentpoolForm.doViewMobilizationlist.value = "no";
        if (document.talentpoolForm.doViewRotationlist)
            document.talentpoolForm.doViewRotationlist.value = "no";
        if (document.talentpoolForm.doViewNotificationlist)
            document.talentpoolForm.doViewNotificationlist.value = "no";
        if (document.talentpoolForm.doViewTransferterminationlist)
            document.talentpoolForm.doViewTransferterminationlist.value = "no";
        if (document.talentpoolForm.doViewWellnessfeedbacklist)
            document.talentpoolForm.doViewWellnessfeedbacklist.value = "no";
        if (document.talentpoolForm.doViewCompetencylist)
            document.talentpoolForm.doViewCompetencylist.value = "no";
        if (document.talentpoolForm.doViewoffshoredetail)
            document.talentpoolForm.doViewoffshoredetail.value = "no";
        if (document.talentpoolForm.doViewNomineelist)
            document.talentpoolForm.doViewNomineelist.value = "no";
        if (document.talentpoolForm.doViewPpelist)
            document.talentpoolForm.doViewPpelist.value = "no";
        if (document.talentpoolForm.doViewContractlist)
            document.talentpoolForm.doViewContractlist.value = "no";
        if (document.talentpoolForm.doViewAppraisallist)
            document.talentpoolForm.doViewAppraisallist.value = "yes";
        document.talentpoolForm.action = "../talentpool/TalentpoolAction.do";
        document.talentpoolForm.submit();
    }
    if (tp == 28)
    {
        document.talentpoolForm.doModify.value = "no";
        document.talentpoolForm.doCancel.value = "no";
        if (document.talentpoolForm.doaddlangdetail)
            document.talentpoolForm.doaddlangdetail.value = "no";
        if (document.talentpoolForm.doViewhealthdetail)
            document.talentpoolForm.doViewhealthdetail.value = "no";
        if (document.talentpoolForm.doViewBanklist)
            document.talentpoolForm.doViewBanklist.value = "no";
        if (document.talentpoolForm.doViewvaccinationlist)
            document.talentpoolForm.doViewvaccinationlist.value = "no";
        if (document.talentpoolForm.doViewgovdocumentlist)
            document.talentpoolForm.doViewgovdocumentlist.value = "no";
        if (document.talentpoolForm.doViewtrainingcertlist)
            document.talentpoolForm.doViewtrainingcertlist.value = "no";
        if (document.talentpoolForm.doVieweducationlist)
            document.talentpoolForm.doVieweducationlist.value = "no";
        if (document.talentpoolForm.doViewexperiencelist)
            document.talentpoolForm.doViewexperiencelist.value = "no";
        if (document.talentpoolForm.doViewVerificationlist)
            document.talentpoolForm.doViewVerificationlist.value = "no";
        if (document.talentpoolForm.doViewAssessmentlist)
            document.talentpoolForm.doViewAssessmentlist.value = "no";
        if (document.talentpoolForm.doViewCompliancelist)
            document.talentpoolForm.doViewCompliancelist.value = "no";
        if (document.talentpoolForm.doViewShortlistinglist)
            document.talentpoolForm.doViewShortlistinglist.value = "no";
        if (document.talentpoolForm.doViewClientselectionlist)
            document.talentpoolForm.doViewClientselectionlist.value = "no";
        if (document.talentpoolForm.doViewOnboardinglist)
            document.talentpoolForm.doViewOnboardinglist.value = "no";
        if (document.talentpoolForm.doViewMobilizationlist)
            document.talentpoolForm.doViewMobilizationlist.value = "no";
        if (document.talentpoolForm.doViewRotationlist)
            document.talentpoolForm.doViewRotationlist.value = "no";
        if (document.talentpoolForm.doViewNotificationlist)
            document.talentpoolForm.doViewNotificationlist.value = "no";
        if (document.talentpoolForm.doViewTransferterminationlist)
            document.talentpoolForm.doViewTransferterminationlist.value = "no";
        if (document.talentpoolForm.doViewWellnessfeedbacklist)
            document.talentpoolForm.doViewWellnessfeedbacklist.value = "no";
        if (document.talentpoolForm.doViewCompetencylist)
            document.talentpoolForm.doViewCompetencylist.value = "no";
        if (document.talentpoolForm.doViewoffshoredetail)
            document.talentpoolForm.doViewoffshoredetail.value = "no";
        if (document.talentpoolForm.doViewNomineelist)
            document.talentpoolForm.doViewNomineelist.value = "no";
        if (document.talentpoolForm.doViewPpelist)
            document.talentpoolForm.doViewPpelist.value = "no";
        if (document.talentpoolForm.doViewContractlist)
            document.talentpoolForm.doViewContractlist.value = "no";
        if (document.talentpoolForm.doViewAppraisallist)
            document.talentpoolForm.doViewAppraisallist.value = "no";
        if (document.talentpoolForm.doViewDayratelist)
            document.talentpoolForm.doViewDayratelist.value = "yes";
        document.talentpoolForm.action = "../talentpool/TalentpoolAction.do";
        document.talentpoolForm.submit();
    }
}

function modifyForm1()
{
    document.talentpoolForm.doModify.value = "yes";
    document.talentpoolForm.doCancel.value = "no";
    if (document.talentpoolForm.doaddlangdetail)
        document.talentpoolForm.doaddlangdetail.value = "no";
    if (document.talentpoolForm.doViewhealthdetail)
        document.talentpoolForm.doViewhealthdetail.value = "no";
    if (document.talentpoolForm.doViewBanklist)
        document.talentpoolForm.doViewBanklist.value = "no";
    if (document.talentpoolForm.doViewvaccinationlist)
        document.talentpoolForm.doViewvaccinationlist.value = "no";
    if (document.talentpoolForm.doViewgovdocumentlist)
        document.talentpoolForm.doViewgovdocumentlist.value = "no";
    if (document.talentpoolForm.doViewtrainingcertlist)
        document.talentpoolForm.doViewtrainingcertlist.value = "no";
    if (document.talentpoolForm.doVieweducationlist)
        document.talentpoolForm.doVieweducationlist.value = "no";
    if (document.talentpoolForm.doViewexperiencelist)
        document.talentpoolForm.doViewexperiencelist.value = "no";
    document.talentpoolForm.action = "../talentpool/TalentpoolAction.do";
    document.talentpoolForm.submit();
}

function modifyvaccinationdetailForm(id)
{
    document.talentpoolForm.doDeletevaccinationdetail.value = "no";
    document.talentpoolForm.candidatevaccineId.value = id;
    document.talentpoolForm.doaddvaccinationdetail.value = "yes";
    document.talentpoolForm.action = "../talentpool/TalentpoolAction.do";
    document.talentpoolForm.submit();
}

function deletevaccinationForm(id, status)
{
    document.talentpoolForm.doDeletevaccinationdetail.value = "yes";
    document.talentpoolForm.candidatevaccineId.value = id;
    document.talentpoolForm.status.value = status;
    document.talentpoolForm.doaddvaccinationdetail.value = "no";
    document.talentpoolForm.action = "../talentpool/TalentpoolAction.do";
    document.talentpoolForm.submit();
}

function modifybankdetailForm(id)
{
    document.talentpoolForm.bankdetailId.value = id;
    document.talentpoolForm.doManageBankdetail.value = "yes";
    document.talentpoolForm.action = "../talentpool/TalentpoolAction.do";
    document.talentpoolForm.submit();
}

function deletebankForm(id, status)
{
    document.talentpoolForm.doDeleteBankdetail.value = "yes";
    document.talentpoolForm.bankdetailId.value = id;
    document.talentpoolForm.status.value = status;
    document.talentpoolForm.doManageBankdetail.value = "no";
    document.talentpoolForm.action = "../talentpool/TalentpoolAction.do";
    document.talentpoolForm.submit();
}

function modifydocumentdetailForm(id)
{
    document.talentpoolForm.govdocumentId.value = id;
    document.talentpoolForm.domodifygovdocumentdetail.value = "yes";
    document.talentpoolForm.action = "../talentpool/TalentpoolAction.do";
    document.talentpoolForm.submit();
}

function deletedocumentForm(id, status)
{
    document.talentpoolForm.doDeletegovdocumentdetail.value = "yes";
    document.talentpoolForm.govdocumentId.value = id;
    document.talentpoolForm.status.value = status;
    document.talentpoolForm.domodifygovdocumentdetail.value = "no";
    document.talentpoolForm.action = "../talentpool/TalentpoolAction.do";
    document.talentpoolForm.submit();
}

function modifytrainingcertdetailForm(id)
{
    document.talentpoolForm.trainingandcertId.value = id;
    document.talentpoolForm.doaddtrainingcertdetail.value = "yes";
    document.talentpoolForm.action = "../talentpool/TalentpoolAction.do";
    document.talentpoolForm.submit();
}

function deletetrainingcertForm(id, status)
{
    document.talentpoolForm.doDeletetrainingcertdetail.value = "yes";
    document.talentpoolForm.trainingandcertId.value = id;
    document.talentpoolForm.status.value = status;
    document.talentpoolForm.doaddtrainingcertdetail.value = "no";
    document.talentpoolForm.action = "../talentpool/TalentpoolAction.do";
    document.talentpoolForm.submit();
}

function modifyeducationForm(id)
{
    document.talentpoolForm.educationdetailId.value = id;
    document.talentpoolForm.doaddeducationdetail.value = "yes";
    document.talentpoolForm.action = "../talentpool/TalentpoolAction.do";
    document.talentpoolForm.submit();
}

function deleteeducationForm(id, status)
{
    document.talentpoolForm.doDeleteeducationdetail.value = "yes";
    document.talentpoolForm.educationdetailId.value = id;
    document.talentpoolForm.status.value = status;
    document.talentpoolForm.doaddeducationdetail.value = "no";
    document.talentpoolForm.action = "../talentpool/TalentpoolAction.do";
    document.talentpoolForm.submit();
}

function modifyexperienceForm(id)
{
    document.talentpoolForm.experiencedetailId.value = id;
    document.talentpoolForm.doaddexperiencedetail.value = "yes";
    document.talentpoolForm.action = "../talentpool/TalentpoolAction.do";
    document.talentpoolForm.submit();
}

function deleteexperienceForm(id, status)
{
    document.talentpoolForm.doDeleteexperiencedetail.value = "yes";
    document.talentpoolForm.experiencedetailId.value = id;
    document.talentpoolForm.status.value = status;
    document.talentpoolForm.doaddexperiencedetail.value = "no";
    document.talentpoolForm.action = "../talentpool/TalentpoolAction.do";
    document.talentpoolForm.submit();
}

function modifylanguageForm(id)
{
    document.talentpoolForm.doSavelangdetail.value = "no";
    document.talentpoolForm.candidateLangId.value = id;
    document.talentpoolForm.doaddlangdetail.value = "yes";
    document.talentpoolForm.action = "../talentpool/TalentpoolAction.do";
    document.talentpoolForm.submit();
}

function deletelangaugeForm(id, status)
{
    document.talentpoolForm.doDeletelangdetail.value = "yes";
    document.talentpoolForm.candidateLangId.value = id;
    document.talentpoolForm.status.value = status;
    document.talentpoolForm.doaddlangdetail.value = "no";
    document.talentpoolForm.action = "../talentpool/TalentpoolAction.do";
    document.talentpoolForm.submit();
}

//for offshore update
function modifyoffshoreForm(id)
{
    document.talentpoolForm.doSaveoffshoredetail.value = "no";
    document.talentpoolForm.offshoreId.value = id;
    document.talentpoolForm.doaddoffshoredetail.value = "yes";
    document.talentpoolForm.action = "../talentpool/TalentpoolAction.do";
    document.talentpoolForm.submit();
}

function deleteOffshoreForm(id, status)
{
    document.talentpoolForm.doDeleteoffshoredetail.value = "yes";
    document.talentpoolForm.offshoreId.value = id;
    document.talentpoolForm.status.value = status;
    document.talentpoolForm.doaddoffshoredetail.value = "no";
    document.talentpoolForm.action = "../talentpool/TalentpoolAction.do";
    document.talentpoolForm.submit();
}

function modifyhealthForm()
{
    document.talentpoolForm.doaddhealthdetail.value = "yes";
    document.talentpoolForm.action = "../talentpool/TalentpoolAction.do";
    document.talentpoolForm.submit();
}

function showhideexperience()
{
    if (document.talentpoolForm.currentworkingstatus.checked)
    {
        document.getElementById('all_workenddate').style.display = "none";
        document.getElementById('all_currentworking').style.display = "";
    } else
    {
        document.getElementById('all_workenddate').style.display = "";
        document.getElementById('all_currentworking').style.display = "none";
    }
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
        url_v = uval + "#toolbar=0&page=1&view=fitH,100";
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
        if(document.getElementById("diframe_doc"))
            document.getElementById("diframe_doc").href = uval.replace(".pdf", ".doc");
    }, 1000);

    $("#iframe").on("load", function () {
        let head = $("#iframe").contents().find("head");
        let css = '<style>img{margin: 0px auto; max-width:-webkit-fill-available;}</style>';
        $(head).append(css);
    });
}

function setIframeresume(uval)
{
    var url_v = "", classname = "";
    if (uval.includes(".doc") || uval.includes(".docx") || uval.includes("wordprocessingml.document"))
    {
        url_v = "https://docs.google.com/gview?url=" + uval + "&embedded=true";
        classname = "doc_mode";
    } else if (uval.includes(".pdf"))
    {
        url_v = uval + "#toolbar=0&page=1&view=fitH,100";
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

//validation and submit-------------------------- Bank Deatils -----------------------------------------------------

function submitdocumentform()
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
    if (checkDocumentForm())
    {
        document.getElementById('submitdiv').innerHTML = "<img src='../assets/images/loading.gif' align='absmiddle' />";
        document.talentpoolForm.domodifygovdocumentdetail.value = "no";
        document.talentpoolForm.doSavegovdocumentdetail.value = "yes";
        document.talentpoolForm.action = "../talentpool/TalentpoolAction.do";
        document.talentpoolForm.submit();
    }
}

function checkDocumentForm()
{
    if (document.talentpoolForm.documentTypeId.value == "-1")
    {
        Swal.fire({
            title: "Please select document name.",
            didClose: () => {
                document.talentpoolForm.documentTypeId.focus();
            }
        })
        return false;
    }
    if (trim(document.talentpoolForm.documentNo.value) == "")
    {
        Swal.fire({
            title: "Please enter document no.",
            didClose: () => {
                document.talentpoolForm.documentNo.focus();
            }
        })
        return false;
    }
    if (validdesc(document.talentpoolForm.documentNo) == false)
    {
        return false;
    }
    if (trim(document.talentpoolForm.cityName.value) != "")
    {
        if (eval(document.talentpoolForm.placeofapplicationId.value) <= 0)
        {
            Swal.fire({
                title: "Please select city using autofill.",
                didClose: () => {
                    document.talentpoolForm.cityName.focus();
                }
            })
            return false;
        }
    }
    if (document.talentpoolForm.dateofissue.value != "")
    {
        if (comparisionTest(document.talentpoolForm.dateofissue.value, document.talentpoolForm.currentDate.value) == false)
        {
            Swal.fire({
                title: "Please check date of issue.",
                didClose: () => {
                    document.talentpoolForm.dateofissue.value = "";
                }
            })
            return false;
        }
    }
    if (document.talentpoolForm.dateofexpiry.value != "")
    {
        if (comparisionTest(document.talentpoolForm.dateofissue.value, document.talentpoolForm.dateofexpiry.value) == false)
        {
            Swal.fire({
                title: "Please check date of expiry.",
                didClose: () => {
                    document.talentpoolForm.dateofexpiry.value = "";
                }
            })
            return false;
        }
    }
    if (document.talentpoolForm.documentissuedbyId.value == "-1")
    {
        Swal.fire({
            title: "Please select document issued by.",
            didClose: () => {
                document.talentpoolForm.documentissuedbyId.focus();
            }
        })
        return false;
    }
    return true;
}

function checkBankDetail()
{
    if (trim(document.talentpoolForm.bankName.value) == "")
    {
        Swal.fire({
            title: "Please enter bank name.",
            didClose: () => {
                document.talentpoolForm.bankName.focus();
            }
        })
        return false;
    }
    if (validname(document.talentpoolForm.bankName) == false)
    {
        return false;
    }
    if (trim(document.talentpoolForm.accountTypeId.value) == "-1")
    {
        Swal.fire({
            title: "Please select type of account.",
            didClose: () => {
                document.talentpoolForm.accountTypeId.focus();
            }
        })
        return false;
    }
    if (trim(document.talentpoolForm.savingAccountNo.value) == "")
    {
        Swal.fire({
            title: "Please enter account no.",
            didClose: () => {
                document.talentpoolForm.savingAccountNo.focus();
            }
        })
        return false;
    }
    if (trim(document.talentpoolForm.accountHolder.value) != "")
    {
        if (validname(document.talentpoolForm.accountHolder) == false)
        {
            return false;
        }
    }
    if (trim(document.talentpoolForm.branch.value) == "")
    {
        Swal.fire({
            title: "Please enter branch.",
            didClose: () => {
                document.talentpoolForm.branch.focus();
            }
        })
        return false;
    }
    if (validnamenum(document.talentpoolForm.branch) == false)
    {
        return false;
    }
    if (trim(document.talentpoolForm.IFSCCode.value) == "")
    {
        Swal.fire({
            title: "Please enter IFSC code.",
            didClose: () => {
                document.talentpoolForm.IFSCCode.focus();
            }
        })
        return false;
    }
    if (document.talentpoolForm.accountTypeId.value == "-1")
    {
        Swal.fire({
            title: "Please select account type.",
            didClose: () => {
                document.talentpoolForm.accountTypeId.focus();
            }
        })
        return false;
    }
    if (document.talentpoolForm.bankfile.value != "")
    {
        if (!(document.talentpoolForm.bankfile.value).match(/(\.(png)|(jpg)|(jpeg)|(pdf))$/i))
        {
            Swal.fire({
                title: "Only .jpg, .jpeg, .png, .pdf are allowed",
                didClose: () => {
                    document.talentpoolForm.bankfile.focus();
                }
            })
            return false;
        }
        var input = document.talentpoolForm.bankfile;
        if (input.files)
        {
            var file = input.files[0];
            if (file.size > 1024 * 1024 * 5)
            {
                Swal.fire({
                    title: "File size should not exceed 5 MB.",
                    didClose: () => {
                        document.talentpoolForm.bankfile.focus();
                    }
                })
                return false;
            }
        }
    }
    return true;
}

function submitbankform()
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
    if (checkBankDetail())
    {
        document.getElementById('submitdiv').innerHTML = "<img src='../assets/images/loading.gif' align='absmiddle' />";
        document.talentpoolForm.doSaveBankdetail.value = "yes";
        document.talentpoolForm.doManageBankdetail.value = "no";
        document.talentpoolForm.doCancel.value = "no";
        document.talentpoolForm.action = "../talentpool/TalentpoolAction.do";
        document.talentpoolForm.submit();
    }
}

//------------------------ Health ------------------------------------------
function  submithealthform()
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
    if (checkHealthDetail())
    {
        document.getElementById('submitdiv').innerHTML = "<img src='../assets/images/loading.gif' align='absmiddle' />";
        document.talentpoolForm.doaddhealthdetail.value = "no";
        document.talentpoolForm.doSavehealthdetail.value = "yes";
        document.talentpoolForm.action = "../talentpool/TalentpoolAction.do";
        document.talentpoolForm.submit();
    }
}

function checkHealthDetail()
{
    if (trim(document.talentpoolForm.ssmf.value) == "")
    {
        Swal.fire({
            title: "Please select seaman specific medical fitness.",
            didClose: () => {
                document.talentpoolForm.ssmf.focus();
            }
        })
        return false;
    }
    if (trim(document.talentpoolForm.ssmf.value) == "No")
    {
        if (trim(document.talentpoolForm.ogukmedicalftw.value) == "")
        {
            Swal.fire({
                title: "Please enter OGUK medical FTW.",
                didClose: () => {
                    document.talentpoolForm.ogukmedicalftw.focus();
                }
            })
            return false;
        }
        if (validdesc(document.talentpoolForm.ogukmedicalftw) == false)
        {
            return false;
        }
        if (document.talentpoolForm.ogukexp.value == "")
        {
            Swal.fire({
                title: "Please select OGUK expiry.",
                didClose: () => {
                    document.talentpoolForm.ogukexp.focus();
                }
            })
            return false;
        }
    }
    if (trim(document.talentpoolForm.ssmf.value) == "Yes")
    {
        if (trim(document.talentpoolForm.medifitcert.value) == "")
        {
            Swal.fire({
                title: "Please select medical fitness certificate.",
                didClose: () => {
                    document.talentpoolForm.medifitcert.focus();
                }
            })
            return false;
        }
        if (document.talentpoolForm.medifitcertexp.value == "")
        {
            Swal.fire({
                title: "Please enter medical fitness certificate expiry.",
                didClose: () => {
                    document.talentpoolForm.medifitcertexp.focus();
                }
            })
            return false;
        }
    }
    if (trim(document.talentpoolForm.cov192doses.value) == "")
    {
        Swal.fire({
            title: "Please select covid-19 2 doses.",
            didClose: () => {
                document.talentpoolForm.cov192doses.focus();
            }
        })
        return false;
    }
    return true;
}

//-----------------------Language-----------------
function checkLanguageDetail()
{
    if (document.talentpoolForm.languageId.value == "-1")
    {
        Swal.fire({
            title: "Please select language.",
            didClose: () => {
                document.talentpoolForm.languageId.focus();
            }
        })
        return false;
    }
    if (document.talentpoolForm.proficiencyId.value == "-1")
    {
        Swal.fire({
            title: "Please select language proficiency.",
            didClose: () => {
                document.talentpoolForm.proficiencyId.focus();
            }
        })
        return false;
    }
    if (document.talentpoolForm.langfile.value != "")
    {
        if (document.talentpoolForm.candidateLangId.value < 0 || document.talentpoolForm.langfilehidden.value == "")
        {
            if (document.talentpoolForm.langfile.value == "")
            {
                Swal.fire({
                    title: "please upload certificate.",
                    didClose: () => {
                        document.talentpoolForm.langfile.focus();
                    }
                })
                return false;
            }
        }
    }
    if (document.talentpoolForm.langfile.value != "")
    {
        if (!(document.talentpoolForm.langfile.value).match(/(\.(png)|(jpg)|(jpeg)|(pdf))$/i))
        {
            Swal.fire({
                title: "Only .jpg, .jpeg, .png, .pdf are allowed.",
                didClose: () => {
                    document.talentpoolForm.langfile.focus();
                }
            })
            return false;
        }
        var input = document.talentpoolForm.langfile;
        if (input.files)
        {
            var file = input.files[0];
            if (file.size > 1024 * 1024 * 5)
            {
                Swal.fire({
                    title: "File size should not exceed 5 MB.",
                    didClose: () => {
                        document.talentpoolForm.langfile.focus();
                    }
                })
                return false;
            }
        }
    }
    return true;
}

function submitlangaugeform()
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
    if (checkLanguageDetail())
    {
        document.getElementById('submitdiv').innerHTML = "<img src='../assets/images/loading.gif' align='absmiddle' />";
        document.talentpoolForm.doaddlangdetail.value = "no";
        document.talentpoolForm.doSavelangdetail.value = "yes";
        document.talentpoolForm.action = "../talentpool/TalentpoolAction.do";
        document.talentpoolForm.submit();
    }
}

function  submitOffshoreform()
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
    if (checkOffshoreDetail())
    {
        document.getElementById('submitdiv').innerHTML = "<img src='../assets/images/loading.gif' align='absmiddle' />";
        document.talentpoolForm.doaddoffshoredetail.value = "no";
        document.talentpoolForm.doSaveoffshoredetail.value = "yes";
        document.talentpoolForm.action = "../talentpool/TalentpoolAction.do";
        document.talentpoolForm.submit();
    }
}

function checkOffshoreDetail()
{
    if (document.talentpoolForm.date.value == "")
    {
        Swal.fire({
            title: "Please select date.",
            didClose: () => {
                document.talentpoolForm.date.focus();
            }
        })
        return false;
    }
    if (comparision(document.talentpoolForm.date.value, document.talentpoolForm.currentDate.value) == false)
    {
        Swal.fire({
            title: "Please check date.",
            didClose: () => {
                document.talentpoolForm.date.value = "";
            }
        })
        return false;
    }
    if (document.talentpoolForm.clientId.value == "-1")
    {
        Swal.fire({
            title: "Please select client.",
            didClose: () => {
                document.talentpoolForm.clientId.focus();
            }
        })
        return false;
    }
    if (document.talentpoolForm.clientassetId.value == "-1")
    {
        Swal.fire({
            title: "Please select asset.",
            didClose: () => {
                document.talentpoolForm.clientassetId.focus();
            }
        })
        return false;
    }
    if (document.talentpoolForm.remarktypeId.value == "-1")
    {
        Swal.fire({
            title: "Please select remark type.",
            didClose: () => {
                document.talentpoolForm.remarktypeId.focus();
            }
        })
        return false;
    }
    if (document.talentpoolForm.remark.value == "")
    {
        Swal.fire({
            title: "Please enter remark.",
            didClose: () => {
                document.talentpoolForm.remark.focus();
            }
        })
        return false;
    }
    if (validdesc(document.talentpoolForm.remark) == false)
    {
        return false;
    }
    if (document.talentpoolForm.offshorefile.value != "")
    {
        if (!(document.talentpoolForm.offshorefile.value).match(/(\.(png)|(jpeg)|(jpg)|(pdf))$/i))
        {
            Swal.fire({
                title: "Only .jpeg, .jpg, .png, .pdf are allowed.",
                didClose: () => {
                    document.talentpoolForm.offshorefile.focus();
                }
            })
            return false;
        }
        var input = document.talentpoolForm.offshorefile;
        if (input.files)
        {
            var file = input.files[0];
            if (file.size > 1024 * 1024 * 5)
            {
                Swal.fire({
                    title: "File size should not exceed 5 MB.",
                    didClose: () => {
                        document.talentpoolForm.offshorefile.focus();
                    }
                })
                return false;
            }
        }
    }
    return true;
}

//--------------------------------------------------------------------- Vaccinenation ---------------------------------------
function  submitvaccinatonform()
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
    if (checkVaccineDetail())
    {
        document.getElementById('submitdiv').innerHTML = "<img src='../assets/images/loading.gif' align='absmiddle' />";
        document.talentpoolForm.doaddvaccinationdetail.value = "no";
        document.talentpoolForm.doSavevaccinationdetail.value = "yes";
        document.talentpoolForm.action = "../talentpool/TalentpoolAction.do";
        document.talentpoolForm.submit();
    }
}

function checkVaccineDetail()
{
    if (document.talentpoolForm.vaccinationNameId.value == "-1")
    {
        Swal.fire({
            title: "Please select vaccination name.",
            didClose: () => {
                document.talentpoolForm.vaccinationNameId.focus();
            }
        })
        return false;
    }
    if (document.talentpoolForm.vacinationTypeId.value == "-1")
    {
        Swal.fire({
            title: "Please select vaccination type.",
            didClose: () => {
                document.talentpoolForm.vacinationTypeId.focus();
            }
        })
        return false;
    }
    if (trim(document.talentpoolForm.cityName.value) != "")
    {
        if (validdesc(document.talentpoolForm.cityName) == false)
        {
            return false;
        }
        if (eval(document.talentpoolForm.placeofapplicationId.value) <= 0)
        {
            Swal.fire({
                title: "Please enter place of application using autofill.",
                didClose: () => {
                    document.talentpoolForm.cityName.focus();
                }
            })
            return false;
        }
    }
    if (document.talentpoolForm.dateofapplication.value != "")
    {
        if (comparisionTest(document.talentpoolForm.dateofapplication.value, document.talentpoolForm.currentDate.value) == false)
        {
            Swal.fire({
                title: "Please check date of application.",
                didClose: () => {
                    document.talentpoolForm.dateofapplication.focus();
                }
            })
            return false;
        }
    }
    if (document.talentpoolForm.dateofexpiry.value != "")
    {
        if (comparisionTest(document.talentpoolForm.dateofapplication.value, document.talentpoolForm.dateofexpiry.value) == false)
        {
            Swal.fire({
                title: "Please check date of expiry.",
                didClose: () => {
                    document.talentpoolForm.dateofexpiry.focus();
                }
            })
            return false;
        }
    }
    if (document.talentpoolForm.vaccinedetailfile.value != "")
    {
        if (!(document.talentpoolForm.vaccinedetailfile.value).match(/(\.(png)|(jpeg)|(jpg)|(pdf))$/i))
        {
            Swal.fire({
                title: "Only .jpeg, .jpg, .png, .pdf are allowed.",
                didClose: () => {
                    document.talentpoolForm.vaccinedetailfile.focus();
                }
            })
            return false;
        }
        var input = document.talentpoolForm.vaccinedetailfile;
        if (input.files)
        {
            var file = input.files[0];
            if (file.size > 1024 * 1024 * 5)
            {
                Swal.fire({
                    title: "File size should not exceed 5 MB.",
                    didClose: () => {
                        document.talentpoolForm.vaccinedetailfile.focus();
                    }
                })
                return false;
            }
        }
    }
    return true;
}

//--------------------------------------------------------- Certification --------------------------
function checkCertificationDetails()
{
    if (Number(document.talentpoolForm.coursenameId.value) <= 0)
    {
        Swal.fire({
            title: "Please select course name.",
            didClose: () => {
                document.talentpoolForm.coursenameId.focus();
            }
        })
        return false;
    }
    if (trim(document.talentpoolForm.educationInstitute.value) != "")
    {
        if (validname(document.talentpoolForm.educationInstitute) == false)
        {
            return false;
        }
    }
    if (trim(document.talentpoolForm.cityName.value) != "")
    {
        if (validdesc(document.talentpoolForm.cityName) == false)
        {
            return false;
        }    
        if (eval(document.talentpoolForm.locationofInstituteId.value) <= 0)
        {
            Swal.fire({
                title: "Please select location of institute using autofill.",
                didClose: () => {
                    document.talentpoolForm.cityName.focus();
                }
            })
            return false;
        }
    }
    if (document.talentpoolForm.dateofissue.value != "")
    {
        if (comparision(document.talentpoolForm.dateofissue.value, document.talentpoolForm.currentDate.value) == false)
        {
            Swal.fire({
                title: "Please check date of issue.",
                didClose: () => {
                    document.talentpoolForm.dateofissue.value = "";
                }
            })
            return false;
        }
    }
    if (trim(document.talentpoolForm.certificationno.value) != "")
    {
        if (validdesc(document.talentpoolForm.certificationno) == false)
        {
            return false;
        }
    }
    if (document.talentpoolForm.upload1.value != "")
    {
        if (!(document.talentpoolForm.upload1.value).match(/(\.(png)|(pdf)|(jpg)|(jpeg))$/i))
        {
            Swal.fire({
                title: "Only  .jpg, .jpeg, .png .pdf are allowed.",
                didClose: () => {
                    document.talentpoolForm.upload1.focus();
                }
            })
            return false;
        }
        var input = document.talentpoolForm.upload1;
        if (input.files)
        {
            var file = input.files[0];
            if (file.size > 1024 * 1024 * 5)
            {
                Swal.fire({
                    title: "File size should not exceed 5 MB.",
                    didClose: () => {
                        document.talentpoolForm.upload1.focus();
                    }
                })
                return false;
            }
        }
    }
    return true;
}

function  submittrainingcertform()
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
    if (checkCertificationDetails())
    {
        document.getElementById('submitdiv').innerHTML = "<img src='../assets/images/loading.gif' align='absmiddle' />";
        document.talentpoolForm.doaddtrainingcertdetail.value = "no";
        document.talentpoolForm.doSavetrainingcertdetail.value = "yes";
        document.talentpoolForm.action = "../talentpool/TalentpoolAction.do";
        document.talentpoolForm.submit();
    }
}

//---------------------------------------------------------------------- Candidate --------------------------------------------------
function clearcity()
{
    document.talentpoolForm.cityId.value = "0";
    document.talentpoolForm.cityName.value = "";
    var url = "../ajax/candidate/getcountrycode.jsp";
    var httploc = getHTTPObject();
    var getstr = "countryId=" + document.talentpoolForm.countryId.value;
    httploc.open("POST", url, true);
    httploc.onreadystatechange = function ()
    {
        if (httploc.readyState == 4)
        {
            if (httploc.status == 200)
            {
                var response = trim(httploc.responseText);
                document.talentpoolForm.code1Id.value = response;
                document.talentpoolForm.code2Id.value = response;
                document.talentpoolForm.code3Id.value = response;
                document.talentpoolForm.ecode1Id.value = response;
                document.talentpoolForm.ecode2Id.value = response;
            }
        }
    };
    httploc.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    httploc.setRequestHeader("Content-length", getstr.length);
    httploc.setRequestHeader("Connection", "close");
    httploc.send(getstr);
    setState();
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
    if (checkCandidate())
    {
        document.getElementById('submitdiv').innerHTML = "<img src='../assets/images/loading.gif' align='absmiddle' />";
        document.talentpoolForm.doModify.value = "no";
        document.talentpoolForm.doSave.value = "yes";
        document.talentpoolForm.doCancel.value = "no";
        document.talentpoolForm.action = "../talentpool/TalentpoolAction.do";
        document.talentpoolForm.submit();
    }
}

function checkCandidate()
{
    if (document.talentpoolForm.photofile.value != "")
    {
        if (!(document.talentpoolForm.photofile.value).match(/(\.(png)|(jpg)|(jpeg))$/i))
        {
            Swal.fire({
                title: "Only .jpg, .jpeg, .png are allowed.",
                didClose: () => {
                    document.talentpoolForm.photofile.focus();
                }
            })
            return false;
        }
        var input = document.talentpoolForm.photofile;
        if (input.files)
        {
            var file = input.files[0];
            if (file.size > 1024 * 1024 * 5)
            {
                Swal.fire({
                    title: "File size should not exceed 5 MB.",
                    didClose: () => {
                        document.talentpoolForm.photofile.focus();
                    }
                })
                return false;
            }
        }
    }
    if (trim(document.talentpoolForm.firstname.value) == "")
    {
        Swal.fire({
            title: "Please enter first name.",
            didClose: () => {
                document.talentpoolForm.firstname.focus();
            }
        })
        return false;
    }
    if (validname(document.talentpoolForm.firstname) == false)
    {
        return false;
    }
    if (document.talentpoolForm.middlename.value != "")
    {
        if (validname(document.talentpoolForm.middlename) == false)
        {
            document.talentpoolForm.middlename.focus();
            return false;
        }
    }
    if (document.talentpoolForm.lastname.value == "")
    {
        Swal.fire({
            title: "Please enter last name.",
            didClose: () => {
                document.talentpoolForm.lastname.focus();
            }
        })
        return false;
    }
    if (validname(document.talentpoolForm.lastname) == false)
    {
        return false;
    }
    if (document.talentpoolForm.candidateId.value < 0)
    {
        if (document.talentpoolForm.fname.value == "")
        {
            Swal.fire({
                title: "Please attach resume.",
                didClose: () => {
                    document.talentpoolForm.fname.focus();
                }
            })
            return false;
        }
    }
    if (document.talentpoolForm.dob.value == "")
    {
        Swal.fire({
            title: "Please enter date of birth.",
            didClose: () => {
                document.talentpoolForm.dob.focus();
            }
        })
        return false;
    }
    if (comparisionTest(document.talentpoolForm.dob.value, document.talentpoolForm.currentDate.value) == false)
    {
        Swal.fire({
            title: "Please check date of birth.",
            didClose: () => {
                document.talentpoolForm.dob.focus();
            }
        })
        return false;
    }
    if (document.talentpoolForm.placeofbirth.value != "")
    {
        if (validname(document.talentpoolForm.placeofbirth) == false)
        {
            document.talentpoolForm.placeofbirth.focus();
            return false;
        }
    }
    if (document.talentpoolForm.gender.value == "Gender")
    {
        Swal.fire({
            title: "Please select gender.",
            didClose: () => {
                document.talentpoolForm.gender.focus();
            }
        })
        return false;
    }
    if (document.talentpoolForm.maritalstatusId.value <= 0)
    {
        Swal.fire({
            title: "Please select marital status.",
            didClose: () => {
                document.talentpoolForm.maritalstatusId.focus();
            }
        })
        return false;
    }
    if (document.talentpoolForm.countryId.value <= 0)
    {
        Swal.fire({
            title: "Please select country.",
            didClose: () => {
                document.talentpoolForm.countryId.focus();
            }
        })
        return false;
    }
    if (eval(document.talentpoolForm.cityId.value) <= 0)
    {
        Swal.fire({
            title: "Please select city using autofill.",
            didClose: () => {
                document.talentpoolForm.cityName.focus();
            }
        })
        return false;
    }
    if (trim(document.talentpoolForm.cityName.value) == "")
    {
        Swal.fire({
            title: "Please select city using autofill.",
            didClose: () => {
                document.talentpoolForm.cityName.focus();
            }
        })
        return false;
    }
    if (validdesc(document.talentpoolForm.cityName) == false)
    {
        return false;
    }
    if (document.talentpoolForm.nationalityId.value <= 0)
    {
        Swal.fire({
            title: "Please select nationality.",
            didClose: () => {
                document.talentpoolForm.nationalityId.focus();
            }
        })
        return false;
    }
    if (trim(document.talentpoolForm.religion.value) != "")
    {
       if (validdesc(document.talentpoolForm.religion) == false)
        {
            return false;
        }
    }    
    if (document.talentpoolForm.emailId.value == "")
    {
        Swal.fire({
            title: "Please enter email ID.",
            didClose: () => {
                document.talentpoolForm.emailId.focus();
            }
        })
        return false;
    }
    if (document.talentpoolForm.emailId.value != "")
    {
        if (checkEmailAddress(document.talentpoolForm.emailId) == false)
        {
            Swal.fire({
                title: "Please enter valid email ID.",
                didClose: () => {
                    document.talentpoolForm.emailId.focus();
                }
            })
            return false;
        }
    }
    if (document.talentpoolForm.code1Id.value == "")
    {
        Swal.fire({
            title: "Please select ISD code of primary contact number.",
            didClose: () => {
                document.talentpoolForm.code1Id.focus();
            }
        })
        return false;
    }
    if (document.talentpoolForm.contactno1.value == "")
    {
        Swal.fire({
            title: "Please enter primary contact number.",
            didClose: () => {
                document.talentpoolForm.contactno1.focus();
            }
        })
        return false;
    }
    if (document.talentpoolForm.contactno1.value != "")
    {
        if (document.talentpoolForm.contactno1.value.length == "")
        {
            Swal.fire({
                title: "Please enter primary contact number.",
                didClose: () => {
                    document.talentpoolForm.contactno1.focus();
                }
            })
            return false;
        }
        if (!checkContact(document.talentpoolForm.contactno1.value))
        {
            Swal.fire({
                title: "Please enter valid primary contact number.",
                didClose: () => {
                    document.talentpoolForm.contactno1.focus();
                }
            })
            return false;
        }
    }
    if (document.talentpoolForm.contactno2.value != "")
    {
        if (document.talentpoolForm.code2Id.value == "")
        {
            Swal.fire({
                title: "Please select ISD code of secondary contact number.",
                didClose: () => {
                    document.talentpoolForm.code2Id.focus();
                }
            })
            return false;
        }
    }
    if (document.talentpoolForm.contactno2.value != "")
    {
        if (document.talentpoolForm.contactno2.value == "")
        {
            Swal.fire({
                title: "Please enter secondary contact number.",
                didClose: () => {
                    document.talentpoolForm.contactno2.focus();
                }
            })
            return false;
        }
        if (document.talentpoolForm.contactno2.value != "")
        {
            if (document.talentpoolForm.contactno2.value.length == "")
            {
                Swal.fire({
                    title: "Please enter secondary contact number.",
                    didClose: () => {
                        document.talentpoolForm.contactno2.focus();
                    }
                })
                return false;
            }
            if (!checkContact(document.talentpoolForm.contactno2.value))
            {
                Swal.fire({
                    title: "Please enter valid secondary contact number.",
                    didClose: () => {
                        document.talentpoolForm.contactno2.focus();
                    }
                })
                return false;
            }
        }
    }
    if (document.talentpoolForm.contactno3.value != "")
    {
        if (document.talentpoolForm.code3Id.value == "")
        {
            Swal.fire({
                title: "Please select ISD code of additional contact number.",
                didClose: () => {
                    document.talentpoolForm.code3Id.focus();
                }
            })
            return false;
        }
    }
    if (document.talentpoolForm.contactno3.value != "")
    {
        if (document.talentpoolForm.contactno3.value == "")
        {
            Swal.fire({
                title: "Please enter additional contact number.",
                didClose: () => {
                    document.talentpoolForm.contactno3.focus();
                }
            })
            return false;
        }
        if (document.talentpoolForm.contactno3.value != "")
        {
            if (document.talentpoolForm.contactno3.value.length == "")
            {
                Swal.fire({
                    title: "Please enter additional contact number.",
                    didClose: () => {
                        document.talentpoolForm.contactno3.focus();
                    }
                })
                return false;
            }
            if (!checkContact(document.talentpoolForm.contactno3.value))
            {
                Swal.fire({
                    title: "Please enter valid additional contact number.",
                    didClose: () => {
                        document.talentpoolForm.contactno3.focus();
                    }
                })
                return false;
            }
        }
    }
    if (document.talentpoolForm.ecode1Id.value == "")
    {
        Swal.fire({
            title: "Please select ISD code of emergency contact number1.",
            didClose: () => {
                document.talentpoolForm.ecode1Id.focus();
            }
        })
        return false;
    }
    if (document.talentpoolForm.econtactno1.value == "")
    {
        Swal.fire({
            title: "Please enter emergency contact number1.",
            didClose: () => {
                document.talentpoolForm.econtactno1.focus();
            }
        })
        return false;
    }
    if (document.talentpoolForm.econtactno1.value != "")
    {
        if (document.talentpoolForm.econtactno1.value.length == "")
        {
            Swal.fire({
                title: "Please enter emergency contact number1.",
                didClose: () => {
                    document.talentpoolForm.econtactno1.focus();
                }
            })
            return false;
        }
        if (!checkContact(document.talentpoolForm.econtactno1.value))
        {
            Swal.fire({
                title: "Please enter valid emergency contact number1.",
                didClose: () => {
                    document.talentpoolForm.econtactno1.focus();
                }
            })
            return false;
        }
    }
    if (document.talentpoolForm.econtactno2.value != "")
    {
        if (document.talentpoolForm.ecode2Id.value == "")
        {
            Swal.fire({
                title: "Please select ISD code of emergency contact number2.",
                didClose: () => {
                    document.talentpoolForm.ecode2Id.focus();
                }
            })
            return false;
        }
    }
    if (document.talentpoolForm.econtactno2.value != "")
    {
        if (document.talentpoolForm.econtactno2.value == "")
        {
            Swal.fire({
                title: "Please enter emergency contact number2.",
                didClose: () => {
                    document.talentpoolForm.econtactno2.focus();
                }
            })
            return false;
        }
        if (document.talentpoolForm.econtactno2.value != "")
        {
            if (document.talentpoolForm.econtactno2.value.length == "")
            {
                Swal.fire({
                    title: "Please enter emergency contact number2.",
                    didClose: () => {
                        document.talentpoolForm.econtactno2.focus();
                    }
                })
                return false;
            }
            if (!checkContact(document.talentpoolForm.econtactno2.value))
            {
                Swal.fire({
                    title: "Please enter valid emergency contact number2.",
                    didClose: () => {
                        document.talentpoolForm.econtactno2.focus();
                    }
                })
                return false;
            }
        }
    }
    if (document.talentpoolForm.nextofkin.value != "")
    {
        if (validname(document.talentpoolForm.nextofkin) == false)
        {
            document.talentpoolForm.nextofkin.focus();
            return false;
        }
    }
    if (trim(document.talentpoolForm.address1line1.value) == "")
    {
        Swal.fire({
            title: "Please enter permanent address in line 1.",
            didClose: () => {
                document.talentpoolForm.address1line1.focus();
            }
        })
        return false;
    }
    if (trim(document.talentpoolForm.address1line2.value) == "")
    {
        Swal.fire({
            title: "Please enter permanent address in line 2.",
            didClose: () => {
                document.talentpoolForm.address1line2.focus();
            }
        })
        return false;
    }
    if (document.talentpoolForm.assettypeId.value == "-1")
    {
        Swal.fire({
            title: "Please select asset type.",
            didClose: () => {
                document.talentpoolForm.assettypeId.focus();
            }
        })
        return false;
    }
    if (document.talentpoolForm.employeeid.value != "")
    {
        if (validdesc(document.talentpoolForm.employeeid) == false)
        {
            document.talentpoolForm.employeeid.focus();
            return false;
        }
    }
    if (document.talentpoolForm.positionId.value == "-1")
    {
        Swal.fire({
            title: "Please select position applied for.",
            didClose: () => {
                document.talentpoolForm.positionId.focus();
            }
        })
        return false;
    }
    if (document.talentpoolForm.currencyId.value != "-1")
    {
        if (trim(document.talentpoolForm.expectedsalary.value) == "")
        {
            Swal.fire({
                title: "Please enter expected salary.",
                didClose: () => {
                    document.talentpoolForm.expectedsalary.focus();
                }
            })
            return false;
        }
        if (trim(document.talentpoolForm.expectedsalary.value) <= "0")
        {
            Swal.fire({
                title: "Please enter valid expected salary.",
                didClose: () => {
                    document.talentpoolForm.expectedsalary.focus();
                }
            })
            return false;
        }
        if (validnum(document.talentpoolForm.expectedsalary) == false)
        {
            document.talentpoolForm.expectedsalary.focus();
            return false;
        }
    }
    if (document.talentpoolForm.airport1.value != "")
    {
        if (validdesc(document.talentpoolForm.airport1) == false)
        {
            document.talentpoolForm.airport1.focus();
            return false;
        }
    }
    if (document.talentpoolForm.airport2.value != "")
    {
        if (validdesc(document.talentpoolForm.airport2) == false)
        {
            document.talentpoolForm.airport2.focus();
            return false;
        }
    }
    return true;
}

function checkExperience()
{
    if (trim(document.talentpoolForm.companyname.value) == "")
    {
        Swal.fire({
            title: "Please enter company name.",
            didClose: () => {
                document.talentpoolForm.companyname.focus();
            }
        })
        return false;
    }
    if (validname(document.talentpoolForm.companyname) == false)
    {
        return false;
    }
    if (document.talentpoolForm.companyindustryId.value == "-1")
    {
        Swal.fire({
            title: "Please select company industry.",
            didClose: () => {
                document.talentpoolForm.companyindustryId.focus();
            }
        })
        return false;
    }
    if (document.talentpoolForm.assettypeId.value == "-1")
    {
        Swal.fire({
            title: "Please select Asset Type.",
            didClose: () => {
                document.talentpoolForm.assettypeId.focus();
            }
        })
        return false;
    }
    if (trim(document.talentpoolForm.assetname.value) == "")
    {
        Swal.fire({
            title: "Please enter asset name.",
            didClose: () => {
                document.talentpoolForm.assetname.focus();
            }
        })
        return false;
    }
    if (validdesc(document.talentpoolForm.assetname) == false)
    {
        return false;
    }
    if (document.talentpoolForm.positionId.value == "-1")
    {
        Swal.fire({
            title: "Please select position.",
            didClose: () => {
                document.talentpoolForm.positionId.focus();
            }
        })
        return false;
    }
   /* if (document.talentpoolForm.departmentId.value == "-1")
    {
        Swal.fire({
            title: "Please select department/function.",
            didClose: () => {
                document.talentpoolForm.departmentId.focus();
            }
        })
        return false;
    }
    if (trim(document.talentpoolForm.clientpartyname.value) == "")
    {
        Swal.fire({
            title: "Please enter operator.",
            didClose: () => {
                document.talentpoolForm.clientpartyname.focus();
            }
        })
        return false;
    }*/
    if (trim(document.talentpoolForm.clientpartyname.value) != "")
    {
        if (validdesc(document.talentpoolForm.clientpartyname) == false)
        {
            return false;
        }
    }
    if (document.talentpoolForm.lastdrawnsalary.value != "") {
        if (validdesc(document.talentpoolForm.lastdrawnsalary) == false)
        {
            return false;
        }
    }
    if (document.talentpoolForm.workstartdate.value == "")
    {
        Swal.fire({
            title: "Please enter work start date.",
            didClose: () => {
                document.talentpoolForm.workstartdate.focus();
            }
        })
        return false;
    }
    if (comparisionTest(document.talentpoolForm.workstartdate.value, document.talentpoolForm.currentDate.value) == false)
    {
        Swal.fire({
            title: "Please check work start date.",
            didClose: () => {
                document.talentpoolForm.workstartdate.focus();
            }
        })
        return false;
    }
    if (document.talentpoolForm.currentworkingstatus.checked == false)
    {
        if (document.talentpoolForm.workenddate.value == "")
        {
            Swal.fire({
                title: "Please enter work end date.",
                didClose: () => {
                    document.talentpoolForm.workenddate.focus();
                }
            })
            return false;
        }
        if (comparisionTest(document.talentpoolForm.workstartdate.value, document.talentpoolForm.workenddate.value) == false)
        {
            Swal.fire({
                title: "Please select work end date.",
                didClose: () => {
                    document.talentpoolForm.workenddate.focus();
                }
            })
            return false;
        }
        if (comparisionTest(document.talentpoolForm.workenddate.value, document.talentpoolForm.currentDate.value) == false)
        {
            Swal.fire({
                title: "Please check work end date.",
                didClose: () => {
                    document.talentpoolForm.workenddate.focus();
                }
            })
            return false;
        }
    }
    if (document.talentpoolForm.experiencefile.value != "")
    {
        if (!(document.talentpoolForm.experiencefile.value).match(/(\.(png)|(jpg)|(jpeg)|(pdf))$/i))
        {
            Swal.fire({
                title: "Only .jpg, .jpeg, .png, .pdf are allowed.",
                didClose: () => {
                    document.talentpoolForm.experiencefile.focus();
                }
            })
            return false;
        }
        var input = document.talentpoolForm.experiencefile;
        if (input.files)
        {
            var file = input.files[0];
            if (file.size > 1024 * 1024 * 5)
            {
                Swal.fire({
                    title: "File size should not exceed 5 MB.",
                    didClose: () => {
                        document.talentpoolForm.experiencefile.focus();
                    }
                })
                return false;
            }
        }
    }
    if (trim(document.talentpoolForm.ownerpool.value) != "")
    {
        if (validdesc(document.talentpoolForm.ownerpool) == false)
        {
            return false;
        }
    }
    if (document.talentpoolForm.workingfile.value != "")
    {
        if (!(document.talentpoolForm.workingfile.value).match(/(\.(png)|(jpg)|(jpeg)|(pdf))$/i))
        {
            Swal.fire({
                title: "Only .jpg, .jpeg, .png, .pdf are allowed.",
                didClose: () => {
                    document.talentpoolForm.workingfile.focus();
                }
            })
            return false;
        }
        var input = document.talentpoolForm.workingfile;
        if (input.files)
        {
            var file = input.files[0];
            if (file.size > 1024 * 1024 * 5)
            {
                Swal.fire({
                    title: "File size should not exceed 5 MB.",
                    didClose: () => {
                        document.talentpoolForm.workingfile.focus();
                    }
                })
                return false;
            }
        }
    }
    return true;
}

function  submitexperienceform()
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
    if (checkExperience())
    {
        document.getElementById('submitdiv').innerHTML = "<img src='../assets/images/loading.gif' align='absmiddle' />";
        document.talentpoolForm.doaddexperiencedetail.value = "no";
        document.talentpoolForm.doSaveexperiencedetail.value = "yes";
        document.talentpoolForm.action = "../talentpool/TalentpoolAction.do";
        document.talentpoolForm.submit();
    }
}

function checkEducationDeatail() {
    if (document.talentpoolForm.kindId.value == "-1")
    {
        Swal.fire({
            title: "Please select education kind.",
            didClose: () => {
                document.talentpoolForm.kindId.focus();
            }
        })
        return false;
    }
    if (document.talentpoolForm.degreeId.value == "-1")
    {
        Swal.fire({
            title: "Please select education degree.",
            didClose: () => {
                document.talentpoolForm.degreeId.focus();
            }
        })
        return false;
    }
    if (document.talentpoolForm.coursestarted.value != "")
    {
        if (comparisionTest(document.talentpoolForm.coursestarted.value, document.talentpoolForm.currentDate.value) == false)
        {
            Swal.fire({
                title: "Please check course start date.",
                didClose: () => {
                    document.talentpoolForm.coursestarted.focus();
                }
            })
            return false;
        }
    }
    if (document.talentpoolForm.passingdate.value != "")
    {
        if (comparisionTest(document.talentpoolForm.coursestarted.value, document.talentpoolForm.passingdate.value) == false)
        {
            Swal.fire({
                title: "Please check passing date.",
                didClose: () => {
                    document.talentpoolForm.passingdate.value = "";
                }
            })
            return false;
        }
    }
    if (document.talentpoolForm.educationfile.value != "")
    {
        if (!(document.talentpoolForm.educationfile.value).match(/(\.(png)|(jpg)|(jpeg)|(pdf))$/i))
        {
            Swal.fire({
                title: "Only .jpg, .jpeg, .png, .pdf are allowed.",
                didClose: () => {
                    document.talentpoolForm.educationfile.focus();
                }
            })
            return false;
        }
        var input = document.talentpoolForm.educationfile;
        if (input.files)
        {
            var file = input.files[0];
            if (file.size > 1024 * 1024 * 5)
            {
                Swal.fire({
                    title: "File size should not exceed 5 MB.",
                    didClose: () => {
                        document.talentpoolForm.educationfile.focus();
                    }
                })
                return false;
            }
        }
    }
    return true;
}

function  submiteducationform()
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
    if (checkEducationDeatail())
    {
        document.getElementById('submitdiv').innerHTML = "<img src='../assets/images/loading.gif' align='absmiddle' />";
        document.talentpoolForm.doaddeducationdetail.value = "no";
        document.talentpoolForm.doSaveeducationdetail.value = "yes";
        document.talentpoolForm.action = "../talentpool/TalentpoolAction.do";
        document.talentpoolForm.submit();
    }
}

function viewworkexpfiles(filename1, filename2)
{
    var url = "../ajax/talentpool/getimg_exp.jsp";
    var httploc = getHTTPObject();
    var getstr = "filename1=" + filename1;
    getstr += "&filename2=" + filename2;
    httploc.open("POST", url, true);
    httploc.onreadystatechange = function ()
    {
        if (httploc.readyState == 4)
        {
            if (httploc.status == 200)
            {
                var response = httploc.responseText;
                document.getElementById('viewexpdiv').innerHTML = '';
                document.getElementById('viewexpdiv').innerHTML = response;
                $("#iframe").on("load", function () {
                    let head = $("#iframe").contents().find("head");
                    let css = '<style>img{margin: 0px auto;max-width:-webkit-fill-available;}</style>';
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

function viewimg(candidateId, fn)
{
    var url = "../ajax/talentpool/getimg.jsp";
    var httploc = getHTTPObject();
    var getstr = "candidateId=" + candidateId;
    getstr += "&fn=" + escape(fn);
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
            }
        }
    };
    httploc.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    httploc.setRequestHeader("Content-length", getstr.length);
    httploc.setRequestHeader("Connection", "close");
    httploc.send(getstr);
    document.getElementById('viewfilesdiv').innerHTML = "<div><img src='../assets/images/loading.gif' align='absmiddle'/>Please wait. Loading...</div>";
}

function delpic(clientassetpicId, clientassetId)
{
    var url = "../ajax/talentpool/delimg.jsp";
    var httploc = getHTTPObject();
    var getstr = "clientassetpicId=" + clientassetpicId;
    httploc.open("POST", url, true);
    httploc.onreadystatechange = function ()
    {
        if (httploc.readyState == 4)
        {
            if (httploc.status == 200)
            {
                var response = trim(httploc.responseText);
                if (response == "Yes")
                {
                    viewimg(clientassetId);
                }
            }
        }
    };
    httploc.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    httploc.setRequestHeader("Content-length", getstr.length);
    httploc.setRequestHeader("Connection", "close");
    httploc.send(getstr);
    document.getElementById('viewfilesdiv').innerHTML = "<div><img src='../assets/images/loading.gif' align='absmiddle'/>Please wait. Loading...</div>";
}

function addtomaster(type)
{
    document.talentpoolForm.mtype.value = type;
    document.talentpoolForm.mname.value = "";
    if (type == 1)
        document.getElementById("maddid").innerHTML = "Relation";
    else if (type == 2)
        document.getElementById("maddid").innerHTML = "Department";
    else if (type == 3 || type == 4)
    {
        if (Number(document.forms[0].countryId.value) <= 0) {
            Swal.fire({
                title: "Please select country.",
                didClose: () => {
                    document.forms[0].countryId.focus();
                    $('#relation_modal').modal('hide');
                }
            })
        } else {
            $('#relation_modal').modal('show');
        }
        if (type == 3)
            document.getElementById("maddid").innerHTML = "City";
        if (type == 4)
            document.getElementById("maddid").innerHTML = "State";
    }
}

function addtomasterajax()
{
    if (trim(document.forms[0].mname.value) != "")
    {
        var type = document.forms[0].mtype.value;
        var countryId;
        if (type == "3" || type == "4") {
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
        if (type == "3" || type == "4") {
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
                        else if (type == "4")
                            document.getElementById('stateId').innerHTML = response;
                        else if (type == "3") {
                            var arr = new Array();
                            arr = response.split('#@#');
                            var v1 = trim(arr[0]);
                            var v2 = arr[1];
                            document.forms[0].cityName.value = v1;
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
                document.talentpoolForm.mname.focus();
            }
        })
    }
}

function checkEmailAjaxCandidate()
{
    var url_email = "../ajax/talentpool/checkemail.jsp";
    var emailValue = document.forms[0].emailId.value;
    var httploc1 = getHTTPObject();
    var getstr = "";
    getstr += "email=" + escape(emailValue);
    getstr += "&candidateId=" + document.forms[0].candidateId.value;
    httploc1.open("POST", url_email, true);
    httploc1.onreadystatechange = function ()
    {
        if (httploc1.readyState == 4)
        {
            if (httploc1.status == 200)
            {
                var response = httploc1.responseText;
                response = trim(response);
                if (response == "YES")
                {
                    document.forms[0].emailId.value = "";
                    Swal.fire("Email Id already exist in database.");
                }

            }
        }
    };
    httploc1.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    httploc1.setRequestHeader("Content-length", getstr.length);
    httploc1.setRequestHeader("Connection", "close");
    httploc1.send(getstr);
}

function setPosition()
{
    var url = "../ajax/talentpool/positions.jsp";
    var httploc = getHTTPObject();
    var getstr = "assettypeId=" + document.forms[0].assettypeId.value;
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
            }
        }
    };
    httploc.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    httploc.setRequestHeader("Content-length", getstr.length);
    httploc.setRequestHeader("Connection", "close");
    httploc.send(getstr);
}

function setAssetPosition()
{
    var url = "../ajax/candidate/assetpositions.jsp";
    var httploc = getHTTPObject();
    var getstr = "assettypeId=" + document.forms[0].assettypeId.value;
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
                setAssetPosition2();
            }
        }
    };
    httploc.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    httploc.setRequestHeader("Content-length", getstr.length);
    httploc.setRequestHeader("Connection", "close");
    httploc.send(getstr);
}

function setAssetPosition2()
{
    var url = "../ajax/candidate/assetpositions2.jsp";
    var httploc = getHTTPObject();
    var getstr = "assettypeId=" + document.forms[0].assettypeId.value;
    getstr += "&positionId=" + document.forms[0].positionId.value;
    httploc.open("POST", url, true);
    httploc.onreadystatechange = function ()
    {
        if (httploc.readyState == 4)
        {
            if (httploc.status == 200)
            {
                var response = httploc.responseText;
                document.getElementById("positionId2").innerHTML = '';
                document.getElementById("positionId2").innerHTML = response;
            }
        }
    };
    httploc.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    httploc.setRequestHeader("Content-length", getstr.length);
    httploc.setRequestHeader("Connection", "close");
    httploc.send(getstr);
}

function exporttoexcel()
{
    document.talentpoolForm.action = "../talentpool/TalentpoolIndexExportAction.do";
    document.talentpoolForm.submit();
}

function delfileedit(type)
{
    var s = "<span>File will be <b>deleted.<b></span>";
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
            var childId = 0;
            if (type == 1)
                childId = document.talentpoolForm.candidateLangId.value;
            else if (type == 2 || type == 3)
                childId = document.talentpoolForm.experiencedetailId.value;
            var url = "../ajax/talentpool/delfile.jsp";
            var httploc = getHTTPObject();
            var getstr = "type=" + type;
            getstr += "&childId=" + childId;
            httploc.open("POST", url, true);
            httploc.onreadystatechange = function ()
            {
                if (httploc.readyState == 4)
                {
                    if (httploc.status == 200)
                    {
                        var response = trim(httploc.responseText);
                        if (response == "Yes")
                        {
                            $('#view_pdf').modal('hide');
                            if (type == 1)
                            {
                                document.getElementById("preview_1").style.display = "none";
                                document.talentpoolForm.langfilehidden.value = "";
                            } else if (type == 2)
                            {
                                document.getElementById("preview_2").style.display = "none";
                                document.talentpoolForm.experiencehiddenfile.value = "";
                            } else if (type == 3)
                            {
                                document.getElementById("preview_3").style.display = "none";
                                document.talentpoolForm.workinghiddenfile.value = "";
                            }
                        }
                    }
                }
            };
            httploc.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
            httploc.setRequestHeader("Content-length", getstr.length);
            httploc.setRequestHeader("Connection", "close");
            httploc.send(getstr);
        }
    })
}

function viewAlertmodal(candidateId, name, position, grade)
{
    var url = "../ajax/talentpool/viewAlertmodal.jsp";
    var httploc = getHTTPObject();
    var getstr = "";
    getstr = "candidateId=" + candidateId;
    getstr += "&name=" + name;
    getstr += "&position=" + position;
    getstr += "&grade=" + grade;
    getstr += "&typeId=" + -1;
    httploc.open("POST", url, true);
    httploc.onreadystatechange = function ()
    {
        if (httploc.readyState == 4)
        {
            if (httploc.status == 200)
            {
                var response = httploc.responseText;
                document.getElementById("alertmodal").innerHTML = '';
                document.getElementById("alertmodal").innerHTML = response;
            }
        }
    };
    httploc.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    httploc.setRequestHeader("Content-length", getstr.length);
    httploc.setRequestHeader("Connection", "close");
    httploc.send(getstr);
}

function addAssessment()
{
    document.forms[0].doView.value = "yes";
    document.forms[0].candidateId.value = document.forms[0].candidateId.value;
    document.forms[0].action = "../cassessment/CassessmentAction.do";
    document.forms[0].submit();
}

function getRetake(id, cid, paid)
{
    document.forms[0].candidateId.value = id;
    document.forms[0].cassessmentId.value = cid;
    document.forms[0].pAssessmentId.value = paid;
    document.forms[0].action = "../cassessment/CassessmentAction.do?doRetake=yes";
    document.forms[0].submit();
}

function getselectionmodal(shortlistId, maillogId, sflag, usertype, oflag)
{
    var url = "../ajax/talentpool/getselectionmodal.jsp";
    var httploc = getHTTPObject();
    var getstr = "";
    getstr += "shortlistId=" + shortlistId;
    getstr += "&maillogId=" + maillogId;
    getstr += "&sflag=" + sflag;
    getstr += "&oflag=" + oflag;
    getstr += "&usertype=" + usertype;
    httploc.open("POST", url, true);
    httploc.onreadystatechange = function ()
    {
        if (httploc.readyState == 4)
        {
            if (httploc.status == 200)
            {
                var response = httploc.responseText;
                document.getElementById("csmodel").innerHTML = '';
                document.getElementById("csmodel").innerHTML = response;
                document.getElementsByClassName("modal-content maildetails")[0].innerHTML = response;

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
    var url = "../ajax/talentpool/getasset.jsp";
    var httploc = getHTTPObject();
    var getstr = "clientIndex=" + document.talentpoolForm.clientIndex.value + "&from=asset";
    httploc.open("POST", url, true);
    httploc.onreadystatechange = function ()
    {
        if (httploc.readyState == 4)
        {
            if (httploc.status == 200)
            {
                var response = httploc.responseText;
                document.getElementById("assetIndex").innerHTML = '';
                document.getElementById("assetIndex").innerHTML = response;
                setPositionDDL();
                searchFormAjax('s', '-1');
            }
        }
    };
    httploc.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    httploc.send(getstr);
}

function setPositionDDL()
{
    var url = "../ajax/talentpool/getasset.jsp";
    var httploc = getHTTPObject();
    var getstr = "assetIndex=" + document.talentpoolForm.assetIndex.value + "&from=position";
    httploc.open("POST", url, true);
    httploc.onreadystatechange = function ()
    {
        if (httploc.readyState == 4)
        {
            if (httploc.status == 200)
            {
                var response = httploc.responseText;
                document.getElementById("positionIndexId").innerHTML = '';
                document.getElementById("positionIndexId").innerHTML = response;
            }
        }
    };
    httploc.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    httploc.send(getstr);
    searchFormAjax('s', '-1');
}

function viewrotationActivity(crewrotationId, clientId, clientassetId)
{
    if (document.forms[0].doSummary)
        document.forms[0].doSummary.value = "yes";
    document.forms[0].clientId.value = clientId;
    document.forms[0].clientassetId.value = clientassetId;
    document.forms[0].crewrotationId.value = crewrotationId;
    document.forms[0].target = "_blank";
    document.forms[0].action = "../crewrotation/CrewrotationAction.do";
    document.forms[0].submit();
}

function setClients()
{
    document.talentpoolForm.toAssetId.value = "-1";
    document.talentpoolForm.toPositionId.value = "-1";
    document.talentpoolForm.toCurrencyId.value = "-1";
    document.getElementById("currencydiv").innerHTML = "&nbsp;";
    document.talentpoolForm.torate1.value = "";
    document.talentpoolForm.torate2.value = "";
    document.talentpoolForm.torate3.value = "";
    document.talentpoolForm.transferRemark.value = "";
    
    document.talentpoolForm.toPositionId2.value = "-1";
    document.talentpoolForm.toCurrencyId2.value = "-1";
    document.getElementById("currencydiv2").innerHTML = "&nbsp;";
    document.talentpoolForm.top2rate1.value = "";
    document.talentpoolForm.top2rate2.value = "";
    document.talentpoolForm.top2rate3.value = "";
    var url = "../ajax/talentpool/getclientDDL.jsp";
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
                document.getElementById("clientdiv").innerHTML = '';
                document.getElementById("clientdiv").innerHTML = response;
            }
        }
    };
    httploc.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    httploc.send(getstr);
}

function setAssets()
{
    document.talentpoolForm.toPositionId.value = "-1";
    document.talentpoolForm.toCurrencyId.value = "-1";
    document.getElementById("currencydiv").innerHTML = "&nbsp;";
    document.talentpoolForm.torate1.value = "";
    document.talentpoolForm.torate2.value = "";
    document.talentpoolForm.torate3.value = "";
    document.talentpoolForm.transferRemark.value = "";
    
    document.talentpoolForm.toPositionId2.value = "-1";
    document.talentpoolForm.toCurrencyId2.value = "-1";
    document.getElementById("currencydiv2").innerHTML = "&nbsp;";
    document.talentpoolForm.top2rate1.value = "";
    document.talentpoolForm.top2rate2.value = "";
    document.talentpoolForm.top2rate3.value = "";
    
    var url = "../ajax/talentpool/getassetDDL.jsp";
    var httploc = getHTTPObject();
    var getstr = "clientId=" + document.forms[0].toClientId.value;
    getstr += "&clientassetId=" + document.forms[0].clientassetId.value;
    httploc.open("POST", url, true);
    httploc.onreadystatechange = function ()
    {
        if (httploc.readyState == 4)
        {
            if (httploc.status == 200)
            {
                var response = httploc.responseText;
                document.getElementById("assetdiv").innerHTML = '';
                document.getElementById("assetdiv").innerHTML = response;
            }
        }
    };
    httploc.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    httploc.setRequestHeader("Content-length", getstr.length);
    httploc.setRequestHeader("Connection", "close");
    httploc.send(getstr);
}

function setPositions()
{
    document.talentpoolForm.toCurrencyId.value = "-1";
    document.getElementById("currencydiv").innerHTML = "&nbsp;";
    document.talentpoolForm.torate1.value = "";
    document.talentpoolForm.torate2.value = "";
    document.talentpoolForm.torate3.value = "";
    document.talentpoolForm.transferRemark.value = "";

    document.talentpoolForm.toCurrencyId2.value = "-1";
    document.getElementById("currencydiv2").innerHTML = "&nbsp;";
    document.talentpoolForm.top2rate1.value = "";
    document.talentpoolForm.top2rate2.value = "";
    document.talentpoolForm.top2rate3.value = "";
    
    var url = "../ajax/talentpool/getasset.jsp";
    var httploc = getHTTPObject();
    var getstr = "assetIndex=" + document.forms[0].toAssetId.value + "&from=position";
    httploc.open("POST", url, true);
    httploc.onreadystatechange = function ()
    {
        if (httploc.readyState == 4)
        {
            if (httploc.status == 200)
            {
                var response = httploc.responseText;
                document.getElementById("positiondiv").innerHTML = '';
                document.getElementById("positiondiv").innerHTML = response;
            }
        }
    };
    httploc.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    httploc.setRequestHeader("Content-length", getstr.length);
    httploc.setRequestHeader("Connection", "close");
    httploc.send(getstr);
    setCurrency();
}

function getPositions2()
{    
    var url = "../ajax/talentpool/getPosition2.jsp";
    var httploc = getHTTPObject();
    var getstr = "toPositionId=" + document.forms[0].toPositionId.value;
    getstr += "&toAssetId=" + document.forms[0].toAssetId.value;
    httploc.open("POST", url, true);
    httploc.onreadystatechange = function ()
    {
        if (httploc.readyState == 4)
        {
            if (httploc.status == 200)
            {
                var response = httploc.responseText;
                document.getElementById("positiondiv2").innerHTML = '';
                document.getElementById("positiondiv2").innerHTML = response;
            }
        }
    };
    httploc.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    httploc.setRequestHeader("Content-length", getstr.length);
    httploc.setRequestHeader("Connection", "close");
    httploc.send(getstr);
    setCurrency();
}

function setCurrency()
{
    var url = "../ajax/talentpool/getcurrencyDtls.jsp";
    var httploc = getHTTPObject();
    var getstr = "assettypeId=" + document.forms[0].toAssetId.value;
    httploc.open("POST", url, true);
    httploc.onreadystatechange = function ()
    {
        if (httploc.readyState == 4)
        {
            if (httploc.status == 200)
            {
                var response = httploc.responseText;
                var arr = (response.trim()).split("#@#@#");
                var v1 = arr[0];
                var v2 = arr[1];
                document.talentpoolForm.toCurrencyId.value = '-1';
                document.getElementById("currencydiv").innerHTML = '';
                document.talentpoolForm.toCurrencyId.value = v1;
                document.getElementById("currencydiv").innerHTML = v2;
                
                document.talentpoolForm.toCurrencyId2.value = '-1';
                document.getElementById("currencydiv2").innerHTML = '';
                document.talentpoolForm.toCurrencyId2.value = v1;
                document.getElementById("currencydiv2").innerHTML = v2;
            }
        }
    };
    httploc.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    httploc.setRequestHeader("Content-length", getstr.length);
    httploc.setRequestHeader("Connection", "close");
    httploc.send(getstr);
}

function submitTransferForm(id)
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
    if (doSaveTransferModal())
    {
        document.getElementById('submitdiv').innerHTML = "<img src='../assets/images/loading.gif' align='absmiddle' />";
        document.talentpoolForm.doSaveTransferModal.value = "yes";
        document.talentpoolForm.doView.value = "no";
        document.talentpoolForm.doCancel.value = "no";
        document.talentpoolForm.candidateId.value = id;
        document.talentpoolForm.action = "../talentpool/TalentpoolAction.do";
        document.talentpoolForm.submit();
    }
}

function doSaveTransferModal()
{
    if (document.talentpoolForm.toClientId.value == "-1")
    {
        Swal.fire({
            title: "Please select client.",
            didClose: () => {
                document.talentpoolForm.toClientId.focus();
            }
        })
        return false;
    }
    if (document.talentpoolForm.toAssetId.value == "-1")
    {
        Swal.fire({
            title: "Please select asset.",
            didClose: () => {
                document.talentpoolForm.toAssetId.focus();
            }
        })
        return false;
    }
    if (document.talentpoolForm.toPositionId.value <= 0)
    {
        Swal.fire({
            title: "Please select select position | rank.",
            didClose: () => {
                document.talentpoolForm.toPositionId.focus();
            }
        })
        return false;
    }
    if (Number(document.talentpoolForm.torate1.value) <= 0)
    {
        Swal.fire({
            title: "Please enter rate.",
            didClose: () => {
                document.talentpoolForm.torate1.focus();
            }
        })
        return false;
    }    
    if (document.talentpoolForm.joiningDate1.value == "")
    {
        Swal.fire({
            title: "Please select primary position joining date.",
            didClose: () => {
                document.talentpoolForm.joiningDate1.focus();
            }
        })
        return false;
    }    
    if (document.talentpoolForm.endDate1.value != "")
    {        
        if (comparisionTest(document.talentpoolForm.joiningDate1.value, document.talentpoolForm.endDate1.value) == false)
        {
            Swal.fire({
                title: "Please check primary position end date.",
                didClose: () => {
                    document.talentpoolForm.endDate1.value = "";
                }
            })
            return false;
        }
    }
    if(document.talentpoolForm.toPositionId2.value > 0)
    {
        if (Number(document.talentpoolForm.top2rate1.value) <= 0)
        {
            Swal.fire({
                title: "Please enter second position rate.",
                didClose: () => {
                    document.talentpoolForm.top2rate1.focus();
                }
            })
            return false;
        }
        if (document.talentpoolForm.joiningDate2.value == "")
        {
            Swal.fire({
                title: "Please select secondary position joining date.",
                didClose: () => {
                    document.talentpoolForm.joiningDate2.focus();
                }
            })
            return false;
        }
        if (document.talentpoolForm.endDate2.value != "")
        {        
            if (comparisionTest(document.talentpoolForm.joiningDate2.value, document.talentpoolForm.endDate2.value) == false)
            {
                Swal.fire({
                    title: "Please check secondary position end date.",
                    didClose: () => {
                        document.talentpoolForm.endDate2.value = "";
                    }
                })
                return false;
            }
        }
    }
    if (document.talentpoolForm.transferRemark.value == "")
    {
        Swal.fire({
            title: "Please enter remark.",
            didClose: () => {
                document.talentpoolForm.transferRemark.focus();
            }
        })
        return false;
    }    
    return true;
}

function submitTerminateForm(id)
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
    if (doSaveTerminateModal())
    {
        document.getElementById('submitdiv').innerHTML = "<img src='../assets/images/loading.gif' align='absmiddle' />";
        document.talentpoolForm.doSaveTerminateModal.value = "yes";
        document.talentpoolForm.doView.value = "no";
        document.talentpoolForm.doCancel.value = "no";
        document.talentpoolForm.candidateId.value = id;
        document.talentpoolForm.action = "../talentpool/TalentpoolAction.do";
        document.talentpoolForm.submit();
    }
}

function doSaveTerminateModal()
{
    if (document.talentpoolForm.joiningDate.value == "")
    {
        Swal.fire({
            title: "Please enter joining date.",
            didClose: () => {
                document.talentpoolForm.joiningDate.focus();
            }
        })
        return false;
    }
    if (document.talentpoolForm.endDate.value == "")
    {
        Swal.fire({
            title: "Please enter end date.",
            didClose: () => {
                document.talentpoolForm.endDate.focus();
            }
        })
        return false;
    }
    if (document.talentpoolForm.reason.value == "")
    {
        Swal.fire({
            title: "Please select reason.",
            didClose: () => {
                document.talentpoolForm.reason.focus();
            }
        })
        return false;
    }
    if (document.talentpoolForm.terminateRemark.value == "")
    {
        Swal.fire({
            title: "Please enter remark.",
            didClose: () => {
                document.talentpoolForm.terminateRemark.focus();
            }
        })
        return false;
    }
    if (document.talentpoolForm.terminatefile.value != "")
    {
        if (!(document.talentpoolForm.terminatefile.value).match(/(\.(png)|(jpg)|(jpeg)|(pdf))$/i))
        {
            Swal.fire({
                title: "Only .jpg, .jpeg, .png, .pdf are allowed.",
                didClose: () => {
                    document.talentpoolForm.terminatefile.focus();
                }
            })
            return false;
        }
        var input = document.talentpoolForm.terminatefile;
        if (input.files)
        {
            var file = input.files[0];
            if (file.size > 1024 * 1024 * 5)
            {
                Swal.fire({
                    title: "File size should not exceed 5 MB.",
                    didClose: () => {
                        document.talentpoolForm.terminatefile.focus();
                    }
                })
                return false;
            }
        }
    }
    return true;
}

function addtomasterajaxedu()
{
    if (trim(document.forms[0].mname.value) != "" && Number(document.forms[0].countryId.value) > 0)
    {
        var url = "../ajax/candidate/addtomasteredu.jsp";
        var httploc = getHTTPObject();
        var getstr = "countryId=" + document.forms[0].countryId.value;
        getstr += "&name=" + escape(document.forms[0].mname.value);
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
                        document.forms[0].countryId.value = "-1";
                        document.forms[0].mname.value = "";
                        $('#city_modal').modal('hide');
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
            title: "Please select country and enter name.",
            didClose: () => {
                document.forms[0].mname.focus();
            }
        })
    }
}

function getSpanId()
{
    document.getElementById('spanId3').innerHTML = "";
    document.getElementById('spanId4').innerHTML = "";
    document.getElementById('spanId1').innerHTML = "";
    document.getElementById('spanId2').innerHTML = "";
    if (document.talentpoolForm.ssmf.value == "Yes")
    {
        document.getElementById('spanId3').innerHTML = "*";
        document.getElementById('spanId4').innerHTML = "*";
    } else if (document.talentpoolForm.ssmf.value == "No")
    {
        document.getElementById('spanId1').innerHTML = "*";
        document.getElementById('spanId2').innerHTML = "*";
    }
}

function exporttoexcelnew(extype)
{
    document.talentpoolForm.action = "../talentpool/ExportdetailAction.do?EXTYPE=" + extype + "";
    document.talentpoolForm.submit();
}

function viewimgdoc(govId)
{
    var url = "../ajax/talentpool/getimg_doc.jsp";
    var httploc = getHTTPObject();
    var getstr = "govId=" + govId;
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

function deldocumentfiles(fileId, govId)
{
    var s = "<span>File will be <b>deleted.<b></span>";
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
            var url = "../ajax/talentpool/delimg_doc.jsp";
            var httploc = getHTTPObject();
            var getstr = "fileId=" + fileId;
            httploc.open("POST", url, true);
            httploc.onreadystatechange = function ()
            {
                if (httploc.readyState == 4)
                {
                    if (httploc.status == 200)
                    {
                        var response = trim(httploc.responseText);
                        if (response == "Yes")
                        {
                            viewimgdoc(govId);
                        }
                    }
                }
            };
            httploc.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
            httploc.setRequestHeader("Content-length", getstr.length);
            httploc.setRequestHeader("Connection", "close");
            httploc.send(getstr);
            document.getElementById('viewfilesdiv').innerHTML = "<div><img src='../assets/images/loading.gif' align='absmiddle'/>Please wait. Loading...</div>";
        }
    })
}

function setQuestionModal(id)
{
    document.talentpoolForm.surveyId.value = id;
    var url = "../ajax/talentpool/getQuestionmodal.jsp";
    var httploc = getHTTPObject();
    var getstr = "surveyId=" + document.talentpoolForm.surveyId.value;
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

function searchFeedback(v, v1)
{
    var url = "../ajax/talentpool/getinfo_feedback.jsp";
    var httploc = getHTTPObject();
    var getstr = "";
    var next_feedbackvalue = (document.talentpoolForm.nextFeedbackValue.value);
    var statusId = (document.talentpoolForm.statusIndex.value);
    var candidateId = document.talentpoolForm.candidateId.value;
    getstr += "statusId=" + document.talentpoolForm.statusIndex.value;
    getstr += "&nextFeedbackValue=" + next_feedbackvalue;
    getstr += "&candidateId=" + candidateId;
    getstr += "&next=" + v;
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

function setAssessNowModal(id)
{
    var url = "../ajax/talentpool/getAssessNowModal.jsp";
    var httploc = getHTTPObject();
    var getstr = "candidateId=" + id;
    httploc.open("POST", url, true);
    httploc.onreadystatechange = function ()
    {
        if (httploc.readyState == 4)
        {
            if (httploc.status == 200)
            {
                var response = httploc.responseText;
                document.getElementById("assessnowmodal").innerHTML = '';
                document.getElementById("assessnowmodal").innerHTML = response;
            }
        }
    };
    httploc.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    httploc.send(getstr);
}

function updatetracker(trackerId)
{
    var url = "../ajax/talentpool/trackermodal.jsp";
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
                document.getElementById("personalmodal").innerHTML = '';
                document.getElementById("personalmodal").innerHTML = response;
            }
        }
    };
    httploc.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    httploc.send(getstr);
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

function getCompetencyFeedback(trackerId)
{
    var url = "../ajax/talentpool/getCompetencyFeedback.jsp";
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
                document.getElementById("feedbackdiv").innerHTML = '';
                document.getElementById("feedbackdiv").innerHTML = response;
            }
        }
    };
    httploc.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    httploc.send(getstr);
}

//For Competency
function setCompasset()
{
    var url = "../ajax/talentpool/getCompasset.jsp";
    var httploc = getHTTPObject();
    var getstr = "clientId=" + document.talentpoolForm.clientId.value;
    httploc.open("POST", url, true);
    httploc.onreadystatechange = function ()
    {
        if (httploc.readyState == 4)
        {
            if (httploc.status == 200)
            {
                var response = httploc.responseText;
                document.getElementById("assetIdcom").innerHTML = '';
                document.getElementById("assetIdcom").innerHTML = response;
                setComPosition();
                setComDept();
                searchCompetency('s', '-1');
            }
        }
    };
    httploc.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    httploc.send(getstr);
}

function setComDept()
{
    var url = "../ajax/talentpool/getCompasset.jsp";
    var httploc = getHTTPObject();
    var getstr = "assetIdcom=" + document.talentpoolForm.assetIdcom.value;
    httploc.open("POST", url, true);
    httploc.onreadystatechange = function ()
    {
        if (httploc.readyState == 4)
        {
            if (httploc.status == 200)
            {
                var response = httploc.responseText;
                document.getElementById("pdeptId").innerHTML = '';
                document.getElementById("pdeptId").innerHTML = response;
                setComPosition();
                searchCompetency('s', '-1');
            }
        }
    };
    httploc.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    httploc.send(getstr);
}

function setComPosition()
{
    var url = "../ajax/talentpool/getCompasset.jsp";
    var httploc = getHTTPObject();
    var getstr = "pdeptId=" + document.talentpoolForm.pdeptId.value;
    getstr += "&clientassetId=" + document.talentpoolForm.assetIdcom.value;
    httploc.open("POST", url, true);
    httploc.onreadystatechange = function ()
    {
        if (httploc.readyState == 4)
        {
            if (httploc.status == 200)
            {
                var response = httploc.responseText;
                document.getElementById("positionIdcom").innerHTML = '';
                document.getElementById("positionIdcom").innerHTML = response;
                searchCompetency('s', '-1');
            }
        }
    };
    httploc.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    httploc.send(getstr);
}

function searchCompetency(v, v1)
{
    var url = "../ajax/talentpool/getinfo_competency.jsp";
    var httploc = getHTTPObject();
    var getstr = "";
    var next_competencyvalue = 0;
    if (document.talentpoolForm.nextCompetencyValue)
        next_competencyvalue = document.talentpoolForm.nextCompetencyValue.value;
    var candidateId = document.talentpoolForm.candidateId.value;
    getstr += "nextCompetencyValue=" + next_competencyvalue;
    getstr += "&candidateId=" + candidateId;
    getstr += "&clientId=" + document.talentpoolForm.clientId.value;
    getstr += "&clientassetId=" + document.talentpoolForm.assetIdcom.value;
    getstr += "&pdeptId=" + document.talentpoolForm.pdeptId.value;
    getstr += "&positionId=" + document.talentpoolForm.positionIdcom.value;
    getstr += "&next=" + v;
    getstr += "&doDirect=" + v1;
    httploc.open("POST", url, true);
    httploc.onreadystatechange = function ()
    {
        if (httploc.readyState == 4)
        {
            if (httploc.status == 200)
            {
                var response = httploc.responseText;
                document.getElementById('comp_cat').innerHTML = '';
                document.getElementById('comp_cat').innerHTML = response;
            }
        }
    };
    httploc.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    httploc.setRequestHeader("Content-length", getstr.length);
    httploc.setRequestHeader("Connection", "close");
    httploc.send(getstr);
    document.getElementById('comp_cat').innerHTML = "<div><img src='../assets/images/loading.gif' align='absmiddle'/>Please wait. Loading...</div>";
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

function setPositionFilter()
{
    var url = "../ajax/talentpool/getassettypePosition.jsp";
    var httploc = getHTTPObject();
    var getstr = "assettypeIdIndex=" + document.talentpoolForm.assettypeIdIndex.value;
    httploc.open("POST", url, true);
    httploc.onreadystatechange = function ()
    {
        if (httploc.readyState == 4)
        {
            if (httploc.status == 200)
            {
                var response = httploc.responseText;
                document.getElementById("positionFilterId").innerHTML = '';
                document.getElementById("positionFilterId").innerHTML = response;
                searchFormAjax('s', '-1');
            }
        }
    };
    httploc.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    httploc.setRequestHeader("Content-length", getstr.length);
    httploc.setRequestHeader("Connection", "close");
    httploc.send(getstr);
}

function searchCertificate(v, v1)
{
    var url = "../ajax/talentpool/getinfo_certificate.jsp";
    var httploc = getHTTPObject();
    var getstr = "";
    var next_certificatevalue = (document.talentpoolForm.nextCertificateValue.value);
    var candidateId = document.talentpoolForm.candidateId.value;
    var courseIndex = (document.talentpoolForm.courseIndex.value);
    getstr += "courseIndex=" + courseIndex;
    getstr += "&nextCertificateValue=" + next_certificatevalue;
    getstr += "&candidateId=" + candidateId;
    getstr += "&next=" + v;
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

function gotoMob(id, type)
{
    document.forms[0].crewrotationId.value = id;
    document.forms[0].type.value = type;
    document.forms[0].doMobTravel.value = "yes";
    document.forms[0].search.value = "";
    document.forms[0].target = "_blank";
    document.forms[0].action = "../mobilization/MobilizationAction.do?";
    document.forms[0].submit();
}

function deleteHealthFile(healthId)
{
    var s = "Are you sure you want to delete?";
    Swal.fire({
        title: s,
        confirmButtonColor: '#3085d6',
        cancelButtonColor: '#d33',
        confirmButtonText: 'Confirm',
        showCloseButton: true,
        allowOutsideClick: false,
        allowEscapeKey: false
    }).then((result) => {
        if (result.isConfirmed)
        {
            document.talentpoolForm.healthfileId.value = healthId;
            document.talentpoolForm.doDeleteHealthFile.value = "yes";
            document.talentpoolForm.action = "../talentpool/TalentpoolAction.do";
            document.talentpoolForm.submit();
        }
    });
}

function exporttoexcelfordatareport()
{
    document.talentpoolForm.action = "../talentpool/ExcelDetails.do";
    document.talentpoolForm.submit();
}
function exporttoexcelfordatareport2()
{
    document.talentpoolForm.action = "../talentpool/DataReportAction.do";
    document.talentpoolForm.submit();
}

function exporttoexcelforanaliticsreport()
{
    document.talentpoolForm.action = "../talentpool/AnaliticsReportAction.do";
    document.talentpoolForm.submit();
}

function setAssetsOff()
{
    var url = "../ajax/talentpool/getassetOffshore.jsp";
    var httploc = getHTTPObject();
    var getstr = "clientId=" + document.forms[0].clientId.value;
    getstr += "&clientassetId=" + document.forms[0].clientassetId.value;
    httploc.open("POST", url, true);
    httploc.onreadystatechange = function ()
    {
        if (httploc.readyState == 4)
        {
            if (httploc.status == 200)
            {
                var response = httploc.responseText;
                document.getElementById("clientassetId").innerHTML = '';
                document.getElementById("clientassetId").innerHTML = response;
            }
        }
    };
    httploc.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    httploc.setRequestHeader("Content-length", getstr.length);
    httploc.setRequestHeader("Connection", "close");
    httploc.send(getstr);
}

function modifynomineedetailForm(id)
{
    document.talentpoolForm.nomineedetailId.value = id;
    document.talentpoolForm.doManageNomineedetail.value = "yes";
    document.talentpoolForm.action = "../talentpool/TalentpoolAction.do";
    document.talentpoolForm.submit();
}

function deletenomineeForm(id, status)
{
    document.talentpoolForm.doDeleteNomineedetail.value = "yes";
    document.talentpoolForm.nomineedetailId.value = id;
    document.talentpoolForm.status.value = status;
    document.talentpoolForm.doManageNomineedetail.value = "no";
    document.talentpoolForm.action = "../talentpool/TalentpoolAction.do";
    document.talentpoolForm.submit();
}

function submitnomineeform()
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
    if (checkNomineeDetail())
    {
        document.getElementById('submitdiv').innerHTML = "<img src='../assets/images/loading.gif' align='absmiddle' />";
        document.talentpoolForm.doSaveNomineedetail.value = "yes";
        document.talentpoolForm.doManageNomineedetail.value = "no";
        document.talentpoolForm.doCancel.value = "no";
        document.talentpoolForm.action = "../talentpool/TalentpoolAction.do";
        document.talentpoolForm.submit();
    }
}

function checkNomineeDetail()
{
    if (trim(document.talentpoolForm.nomineeName.value) == "")
    {
        Swal.fire({
            title: "Please enter nominee name.",
            didClose: () => {
                document.talentpoolForm.nomineeName.focus();
            }
        })
        return false;
    }
    if (validdesc(document.talentpoolForm.nomineeName) == false)
    {
        return false;
    }
    if (document.talentpoolForm.code1Id.value == "")
    {
        Swal.fire({
            title: "Please select ISD code of contact number.",
            didClose: () => {
                document.talentpoolForm.code1Id.focus();
            }
        })
        return false;
    }
    if (trim(document.talentpoolForm.nomineeContactno.value) == "")
    {
        Swal.fire({
            title: "Please enter nominee contact number.",
            didClose: () => {
                document.talentpoolForm.nomineeContactno.focus();
            }
        })
        return false;
    }
    if (!checkContact(document.talentpoolForm.nomineeContactno.value))
    {
        Swal.fire({
            title: "Please enter valid contact number.",
            didClose: () => {
                document.talentpoolForm.nomineeContactno.focus();
            }
        })
        return false;
    }
    if (trim(document.talentpoolForm.relationId.value) == "-1")
    {
        Swal.fire({
            title: "Please select nominee relation.",
            didClose: () => {
                document.talentpoolForm.relationId.focus();
            }
        })
        return false;
    }
    if (Number(document.talentpoolForm.age.value) >100)
    {
        Swal.fire({
            title: "Please enter valid age.",
            didClose: () => {
                document.talentpoolForm.age.focus();
            }
        })
        return false;
    }
    if (Number(document.talentpoolForm.percentage.value) >100)
    {
        Swal.fire({
            title: "Please enter valid Percentage of distribution.",
            didClose: () => {
                document.talentpoolForm.percentage.focus();
            }
        })
        return false;
    }
    return true;
}

//for PPE 
function modifyppedetailForm(id)
{
    document.talentpoolForm.ppedetailId.value = id;
    document.talentpoolForm.doManagePpedetail.value = "yes";
    document.talentpoolForm.action = "../talentpool/TalentpoolAction.do";
    document.talentpoolForm.submit();
}

function deleteppeForm(id, status)
{
    document.talentpoolForm.doDeletePpedetail.value = "yes";
    document.talentpoolForm.ppedetailId.value = id;
    document.talentpoolForm.status.value = status;
    document.talentpoolForm.doManagePpedetail.value = "no";
    document.talentpoolForm.action = "../talentpool/TalentpoolAction.do";
    document.talentpoolForm.submit();
}

function submitppeform()
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
    if (checkPpeDetail())
    {
        document.getElementById('submitdiv').innerHTML = "<img src='../assets/images/loading.gif' align='absmiddle' />";
        document.talentpoolForm.doSavePpedetail.value = "yes";
        document.talentpoolForm.doManagePpedetail.value = "no";
        document.talentpoolForm.doCancel.value = "no";
        document.talentpoolForm.action = "../talentpool/TalentpoolAction.do";
        document.talentpoolForm.submit();
    }
}

function checkPpeDetail()
{
    if (trim(document.talentpoolForm.ppetypeId.value) == "-1")
    {
        Swal.fire({
            title: "Please select Item.",
            didClose: () => {
                document.talentpoolForm.ppetypeId.focus();
            }
        })
        return false;
    }
    if (trim(document.talentpoolForm.remark.value) == "")
    {
        Swal.fire({
            title: "Please check size.",
            didClose: () => {
                document.talentpoolForm.remark.focus();
            }
        })
        return false;
    }
    if (validdesc(document.talentpoolForm.remark) == false)
    {
        return false;
    }
    return true;
}

//For Contract
function addContractdetailForm(id, refId, type)
{
    document.talentpoolForm.contractdetailId.value = id;
    document.talentpoolForm.refId.value = refId;
    document.talentpoolForm.type.value = type;
    document.talentpoolForm.doManageContractdetail.value = "yes";
    document.talentpoolForm.action = "../talentpool/TalentpoolAction.do";
    document.talentpoolForm.submit();
}

function modifyContract(type, id)
{
    document.talentpoolForm.contractdetailId.value = id;
    document.talentpoolForm.type.value = type;
    document.talentpoolForm.doGeneratedContract.value = "yes";
    document.talentpoolForm.action = "../talentpool/TalentpoolAction.do";
    document.talentpoolForm.submit();
}

function repeatContract(type, contractId, id)
{
    document.talentpoolForm.type.value = type;
    document.talentpoolForm.refId.value = contractId;
    document.talentpoolForm.contractdetailId.value = id;
    document.talentpoolForm.repeatContract.value = "yes";
    document.talentpoolForm.action = "../talentpool/TalentpoolAction.do";
    document.talentpoolForm.submit();
}

function viewContractDoc(id, file1, file2, file3)
{
    var url = "../ajax/talentpool/getContractdoc.jsp";
    var httploc = getHTTPObject();
    var getstr = "contractdetailId=" + id;
    getstr += "&filename1=" + file1;
    getstr += "&filename2=" + file2;
    getstr += "&filename3=" + file3;
    httploc.open("POST", url, true);
    httploc.onreadystatechange = function ()
    {
        if (httploc.readyState == 4)
        {
            if (httploc.status == 200)
            {
                var response = httploc.responseText;
                document.getElementById('viewexpdiv').innerHTML = '';
                document.getElementById('viewexpdiv').innerHTML = response;
                $("#iframe").on("load", function () {
                    let head = $("#iframe").contents().find("head");
                    let css = '<style>img{margin: 0px auto;max-width:-webkit-fill-available;}</style>';
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

//For contract approval
function getCrewDetails(contractdetailId, type, filename)
{
    document.talentpoolForm.contractdetailId.value = contractdetailId;
    $('#crew_contract_modal').modal('show');
    var url = "../ajax/talentpool/contractApprove.jsp";
    var httploc = getHTTPObject();
    var getstr = "";
    getstr += "contractdetailId=" + contractdetailId;
    getstr += "&candidateId=" + document.talentpoolForm.candidateId.value;
    getstr += "&type=" + type;
    getstr += "&filename=" + filename;
    httploc.open("POST", url, true);
    httploc.onreadystatechange = function ()
    {
        if (httploc.readyState == 4)
        {
            if (httploc.status == 200)
            {
                var response = httploc.responseText;
                document.getElementById('crew_contractdata').innerHTML = '';
                document.getElementById('crew_contractdata').innerHTML = response;
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
    document.getElementById('crew_contractdata').innerHTML = "<div><img src='../assets/images/loading.gif' align='absmiddle'/>Please wait. Loading...</div>";
}

function submitApproveForm(contractdetailId, id)
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
    if (checkContractApprove())
    {
        document.getElementById('submitdiv').innerHTML = "<img src='../assets/images/loading.gif' align='absmiddle' />";
        document.talentpoolForm.contractApprove.value = "yes";
        document.forms[0].contractdetailId.value = contractdetailId;
        document.forms[0].typeId.value = id;
        document.talentpoolForm.action = "../talentpool/TalentpoolAction.do";
        document.talentpoolForm.submit();
    }
}

function checkContractApprove()
{
    if (document.forms[0].remark.value == "")
    {
        Swal.fire({
            title: "Please enter remark.",
            didClose: () => {
                document.forms[0].remark.focus();
            }
        })
        return false;
    }
    
    if((document.forms[0].checktype && document.forms[0].typeId.value == 2 && document.forms[0].checktype.checked == false) || document.forms[0].typeId.value == 1)
    {
        if (document.talentpoolForm.contractfile.value == "")
        {
            Swal.fire({
                title: "please upload file.",
                didClose: () => {
                    document.talentpoolForm.contractfile.focus();
                }
            })
            return false;
        }
        if (document.talentpoolForm.contractfile.value != "")
        {
            if (!(document.talentpoolForm.contractfile.value).match(/(\.(pdf))$/i))
            {
                Swal.fire({
                    title: "Only .pdf are allowed.",
                    didClose: () => {
                        document.forms[0].remark.focus();
                    }
                })
                return false;
            }
            var input = document.talentpoolForm.contractfile;
            if (input.files)
            {
                var file = input.files[0];
                if (file.size > 1024 * 1024 * 5)
                {
                    Swal.fire({
                        title: "File size should not exceed 5 MB.",
                        didClose: () => {
                            document.talentpoolForm.contractfile.focus();
                        }
                    })
                    return false;
                }
            }
        }
    }
    if(document.forms[0].typeId.value == 2)
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
    }
    return true;
}

function deleteContractForm(id, status, i)
{
    var s = "Are you sure you want to delete?";
    Swal.fire({
        title: s,
        confirmButtonColor: '#3085d6',
        cancelButtonColor: '#d33',
        confirmButtonText: 'Confirm',
        showCloseButton: true,
        allowOutsideClick: false,
        allowEscapeKey: false
    }).then((result) => {
        if (result.isConfirmed)
        {
            document.talentpoolForm.deleteContract.value = "yes";
            document.talentpoolForm.contractdetailId.value = id;
            document.talentpoolForm.status.value = status;
            document.talentpoolForm.action = "../talentpool/TalentpoolAction.do";
            document.talentpoolForm.submit();           
        }
        else
        {
            if (document.getElementById("flexSwitchCheckDefault_" + i).checked == true)
                document.getElementById("flexSwitchCheckDefault_" + i).checked = false;
            else
                document.getElementById("flexSwitchCheckDefault_" + i).checked = true;
        }
    });
}

function deleteFile(contractdetailId, id)
{
    document.talentpoolForm.deleteContractFile.value = "yes";
    document.talentpoolForm.fileId.value = id;
    document.talentpoolForm.contractdetailId.value = contractdetailId;
    document.talentpoolForm.action = "../talentpool/TalentpoolAction.do";
    document.talentpoolForm.submit();
}

function gotoContractlist()
{
    document.forms[0].cancelContract.value = "yes";
    document.forms[0].action = "../talentpool/TalentpoolAction.do";
    document.forms[0].submit();
}

//for contract
function setAssetContract()
{
    var url = "../ajax/talentpool/getcontractasset.jsp";
    var httploc = getHTTPObject();
    var getstr = "clientIdContract=" + document.talentpoolForm.clientIdContract.value;
    httploc.open("POST", url, true);
    httploc.onreadystatechange = function ()
    {
        if (httploc.readyState == 4)
        {
            if (httploc.status == 200)
            {
                var response = httploc.responseText;
                document.getElementById("assetIdContract").innerHTML = '';
                document.getElementById("assetIdContract").innerHTML = response;
            }
        }
    };
    httploc.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    httploc.send(getstr);
}

function getCrewRemarkDetails(contractdetailId)
{
    $('#crew_contractremarks').modal('show');
    var url = "../ajax/talentpool/contractRemarks.jsp";
    var httploc = getHTTPObject();
    var getstr = "";
    getstr += "contractdetailId=" + contractdetailId;
    getstr += "&candidateId=" + document.talentpoolForm.candidateId.value;
    httploc.open("POST", url, true);
    httploc.onreadystatechange = function ()
    {
        if (httploc.readyState == 4)
        {
            if (httploc.status == 200)
            {
                var response = httploc.responseText;
                document.getElementById('crew_contractremarksdata').innerHTML = '';
                document.getElementById('crew_contractremarksdata').innerHTML = response;
            }
        }
    };
    httploc.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    httploc.setRequestHeader("Content-length", getstr.length);
    httploc.setRequestHeader("Connection", "close");
    httploc.send(getstr);
    document.getElementById('crew_contractdata').innerHTML = "<div><img src='../assets/images/loading.gif' align='absmiddle'/>Please wait. Loading...</div>";
}

function showExpiryDetails(candidateId, typeId)
{
    var url = "../ajax/talentpool/viewAlertmodal2.jsp";
    var httploc = getHTTPObject();
    var getstr = "";
    getstr = "candidateId=" + candidateId;
    getstr += "&typeId=" + document.talentpoolForm.typeId.value;
    httploc.open("POST", url, true);
    httploc.onreadystatechange = function ()
    {
        if (httploc.readyState == 4)
        {
            if (httploc.status == 200)
            {
                var response = httploc.responseText;
                document.getElementById("tds").innerHTML = '';
                document.getElementById("tds").innerHTML = response;
            }
        }
    };
    httploc.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    httploc.setRequestHeader("Content-length", getstr.length);
    httploc.setRequestHeader("Connection", "close");
    httploc.send(getstr);
}

function searchContract(v, v1)
{
    var url = "../ajax/talentpool/getinfo_contract.jsp";
    var httploc = getHTTPObject();
    var getstr = "";
    var next_contractvalue = (document.talentpoolForm.nextContractValue.value);
    var candidateId = document.talentpoolForm.candidateId.value;
    var clientIdContract = (document.talentpoolForm.clientIdContract.value);
    var assetIdContract = (document.talentpoolForm.assetIdContract.value);
    var contractStatus = (document.talentpoolForm.contractStatus.value);
    var fromDate = (document.talentpoolForm.fromDate.value);
    var toDate = (document.talentpoolForm.toDate.value);    
    getstr += "clientIdContract=" + clientIdContract;
    getstr += "&nextContractValue=" + next_contractvalue;
    getstr += "&candidateId=" + candidateId;
    getstr += "&assetIdContract=" + assetIdContract;
    getstr += "&contractStatus=" + contractStatus;
    getstr += "&fromDate=" + fromDate;
    getstr += "&toDate=" + toDate;
    getstr += "&next=" + v;
    getstr += "&doDirect=" + v1;
    httploc.open("POST", url, true);
    httploc.onreadystatechange = function ()
    {
        if (httploc.readyState == 4)
        {
            if (httploc.status == 200)
            {
                var response = httploc.responseText;
                document.getElementById('ajax_contract').innerHTML = '';
                document.getElementById('ajax_contract').innerHTML = response;
            }
        }
    };
    httploc.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    httploc.setRequestHeader("Content-length", getstr.length);
    httploc.setRequestHeader("Connection", "close");
    httploc.send(getstr);
    document.getElementById('ajax_contract').innerHTML = "<div><img src='../assets/images/loading.gif' align='absmiddle'/>Please wait. Loading...</div>";
}

function showratediv()
{
    if(document.talentpoolForm.toPositionId2.value > 0)
    {    
        document.getElementById("rateDiv").style.display = "";
        document.talentpoolForm.top2rate1.value = "";
        document.talentpoolForm.top2rate2.value = "";
        document.talentpoolForm.top2rate3.value = "";
    }
    else
    {
        document.getElementById("rateDiv").style.display = "none";
        document.talentpoolForm.toCurrencyId2.value = "-1";
        document.talentpoolForm.top2rate1.value = "";
        document.talentpoolForm.top2rate2.value = "";
        document.talentpoolForm.top2rate3.value = "";
    }
}

function getPpevalue()
{
    var url = "../ajax/talentpool/getPpevalue.jsp";
    var httploc = getHTTPObject();
    var getstr = "";
    getstr = "ppetypeId=" + document.talentpoolForm.ppetypeId.value;
    getstr += "&ppedetailId=" + document.talentpoolForm.ppedetailId.value;
    httploc.open("POST", url, true);
    httploc.onreadystatechange = function ()
    {
        if (httploc.readyState == 4)
        {
            if (httploc.status == 200)
            {
                var response = httploc.responseText;
                document.getElementById("ppeDiv").innerHTML = '';
                document.getElementById("ppeDiv").innerHTML = response;
            }
        }
    };
    httploc.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    httploc.setRequestHeader("Content-length", getstr.length);
    httploc.setRequestHeader("Connection", "close");
    httploc.send(getstr);
}

function modifyAppraisalForm(id)
{
    document.talentpoolForm.doSaveAppraisaldetail.value = "no";
    document.talentpoolForm.appraisalId.value = id;
    document.talentpoolForm.doAddAppraisaldetail.value = "yes";
    document.talentpoolForm.action = "../talentpool/TalentpoolAction.do";
    document.talentpoolForm.submit();
}

function setPositionCurrency()
{
    var url = "../ajax/talentpool/getcurrencyDtls.jsp";
    var httploc = getHTTPObject();
    var getstr = "assettypeId=" + document.forms[0].clientAssetId.value;
    httploc.open("POST", url, true);
    httploc.onreadystatechange = function ()
    {
        if (httploc.readyState == 4)
        {
            if (httploc.status == 200)
            {
                var response = httploc.responseText;
                var arr = (response.trim()).split("#@#@#");
                var v1 = arr[0];
                var v2 = arr[1];
                document.talentpoolForm.currency.value = '-1';
                document.getElementById("currencydiv").innerHTML = '';
                document.talentpoolForm.currency.value = v2;
                document.getElementById("currencydiv").innerHTML = v2;
            }
        }
    };
    httploc.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    httploc.setRequestHeader("Content-length", getstr.length);
    httploc.setRequestHeader("Connection", "close");
    httploc.send(getstr);
}

function setPositionRate()
{
    var url = "../ajax/talentpool/getcurrencyRate.jsp";
    var httploc = getHTTPObject();    
    var getstr = "candidateId=" + document.forms[0].candidateId.value;
    getstr += "&positionId=" + document.forms[0].positionId.value;
    getstr += "&clientAssetId=" + document.forms[0].clientAssetId.value;
    httploc.open("POST", url, true);
    httploc.onreadystatechange = function ()
    {
        if (httploc.readyState == 4)
        {
            if (httploc.status == 200)
            {
                var response = httploc.responseText;     
                var arr = (response.trim()).split("@");
                var v1 = arr[0];
                var v2 = arr[1];
                var v3 = arr[2];
                if(v1 != "")
                {
                    document.getElementById("rate1").innerHTML = '';
                    document.talentpoolForm.rate1.value = v1;
                    document.getElementById("rate1").innerHTML = v1;
                }else{
                    v1 = "0.0";
                    document.getElementById("rate1").innerHTML = '';
                    document.talentpoolForm.rate1.value = v1;
                    document.getElementById("rate1").innerHTML = v1;
                }
                if(v2 > 0)
                {
                    document.getElementById("rate2").innerHTML = '';
                    document.talentpoolForm.rate2.value = v2;
                    document.getElementById("rate2").innerHTML = v2;
                }else{
                    v2 = "0.0";
                    document.getElementById("rate2").innerHTML = '';
                    document.talentpoolForm.rate2.value = v2;
                    document.getElementById("rate2").innerHTML = v2;
                }
                if(v3 >0)
                {
                    document.getElementById("rate3").innerHTML = '';
                    document.talentpoolForm.rate3.value = v3;
                    document.getElementById("rate3").innerHTML = v3;
                }else{
                    v3 = "0.0";
                    document.getElementById("rate3").innerHTML = '';
                    document.talentpoolForm.rate3.value = v3;
                    document.getElementById("rate3").innerHTML = v3;
                }
                setAppraisalPosition();
            }
        }
    };
    httploc.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    httploc.setRequestHeader("Content-length", getstr.length);
    httploc.setRequestHeader("Connection", "close");
    httploc.send(getstr);    
}

function submitAppraisalForm()
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
    if (checkAppraisal())
    {
        document.getElementById('submitdiv').innerHTML = "<img src='../assets/images/loading.gif' align='absmiddle' />";
        document.talentpoolForm.doSaveAppraisaldetail.value = "yes";
        document.talentpoolForm.action = "../talentpool/TalentpoolAction.do";
        document.talentpoolForm.submit();
    }
}

function checkAppraisal()
{
    if (document.talentpoolForm.positionId.value == "-1")
    {
        Swal.fire({
            title: "Please select position.",
            didClose: () => {
                document.talentpoolForm.positionId.focus();
            }
        })
        return false;
    }
    if(document.talentpoolForm.checktype.checked == false)
    {
        if (document.talentpoolForm.positionId2.value <= 0)
        {
            Swal.fire({
                title: "Please select new Position.",
                didClose: () => {
                    document.talentpoolForm.positionId2.focus();
                }
            })
            return false;
        }
    }
    if (document.talentpoolForm.fromDate.value == "")
    {
        Swal.fire({
            title: "Please select from date.",
            didClose: () => {
                document.talentpoolForm.fromDate.focus();
            }
        })
        return false;
    }
    if (document.talentpoolForm.appraisalFile.value != "")
    {
        if (!(document.talentpoolForm.appraisalFile.value).match(/(\.(pdf))$/i))
        {
            Swal.fire({
                title: "Only .pdf allowed",
                didClose: () => {
                    document.talentpoolForm.appraisalFile.focus();
                }
            })
            return false;
        }
        var input = document.talentpoolForm.appraisalFile;
        if (input.files)
        {
            var file = input.files[0];
            if (file.size > 1024 * 1024 * 5)
            {
                Swal.fire({
                    title: "File size should not exceed 5 MB.",
                    didClose: () => {
                        document.talentpoolForm.appraisalFile.focus();
                    }
                })
                return false;
            }
        }
    }
    return true;
}

function showP()
{
    if(document.talentpoolForm.checktype.checked == true)
    {
        document.getElementById("pidDiv").style.display = "";
            document.getElementById("pidDiv").style.display = "none";
        if (document.talentpoolForm.positionId2.value > 0)
        {
            document.talentpoolForm.positionId2.value = "0";            
        }
    }else{
        document.getElementById("pidDiv").style.display = "none";
            document.getElementById("pidDiv").style.display = "";
    }
}

function setAppraisalPosition()
{
    var url = "../ajax/talentpool/getPosition2.jsp";
    var httploc = getHTTPObject();
    var getstr = "toPositionId=" + document.forms[0].positionId.value;
    getstr += "&toAssetId=" + document.forms[0].clientAssetId.value;
    httploc.open("POST", url, true);
    httploc.onreadystatechange = function ()
    {
        if (httploc.readyState == 4)
        {
            if (httploc.status == 200)
            {
                var response = httploc.responseText;
                document.getElementById("positionId2").innerHTML = '';
                document.getElementById("positionId2").innerHTML = response;
            }
        }
    };
    httploc.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    httploc.setRequestHeader("Content-length", getstr.length);
    httploc.setRequestHeader("Connection", "close");
    httploc.send(getstr);
}

function  setState()
{
    var url = "../ajax/candidate/countryState.jsp";
    var httploc = getHTTPObject();
    var getstr = "countryId=" + document.forms[0].countryId.value;
    httploc.open("POST", url, true);
    httploc.onreadystatechange = function ()
    {
        if (httploc.readyState == 4)
        {
            if (httploc.status == 200)
            {
                var response = httploc.responseText;
                document.getElementById("stateId").innerHTML = '';
                document.getElementById("stateId").innerHTML = response;
            }
        }
    };
    httploc.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    httploc.setRequestHeader("Content-length", getstr.length);
    httploc.setRequestHeader("Connection", "close");
    httploc.send(getstr);
}

function getAppraisalHistory(appraisalId)
{
    var url = "../ajax/talentpool/getAppraisalHistory.jsp";
    var httploc = getHTTPObject();
    var getstr = "";
    getstr = "appraisalId=" + appraisalId;
    httploc.open("POST", url, true);
    httploc.onreadystatechange = function ()
    {
        if (httploc.readyState == 4)
        {
            if (httploc.status == 200)
            {
                var response = httploc.responseText;
                document.getElementById("appHistory").innerHTML = '';
                document.getElementById("appHistory").innerHTML = response;
            }
        }
    };
    httploc.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    httploc.setRequestHeader("Content-length", getstr.length);
    httploc.setRequestHeader("Connection", "close");
    httploc.send(getstr);
}

function modifyDayrateForm(id)
{
    document.talentpoolForm.dayrateId.value = id;
    document.talentpoolForm.doAddDayratedetail.value = "yes";
    document.talentpoolForm.action = "../talentpool/TalentpoolAction.do";
    document.talentpoolForm.submit();
}


function submitDayrateForm()
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
    if (checkDayrate())
    {
        document.getElementById('submitdiv').innerHTML = "<img src='../assets/images/loading.gif' align='absmiddle' />";
        document.talentpoolForm.doSaveDayratedetail.value = "yes";
        document.talentpoolForm.action = "../talentpool/TalentpoolAction.do";
        document.talentpoolForm.submit();
    }
}

function checkDayrate()
{
    if (document.talentpoolForm.positionId.value == "-1")
    {
        Swal.fire({
            title: "Please select position.",
            didClose: () => {
                document.talentpoolForm.positionId.focus();
            }
        })
        return false;
    }
    if (document.talentpoolForm.fromDate.value == "")
    {
        Swal.fire({
            title: "Please select from date.",
            didClose: () => {
                document.talentpoolForm.fromDate.focus();
            }
        })
        return false;
    }
    if (document.talentpoolForm.toDate.value != "")
    {
        if (comparisionTest(document.talentpoolForm.fromDate.value, document.talentpoolForm.toDate.value) == false)
        {
            Swal.fire({
                title: "Please check to date.",
                didClose: () => {
                    document.talentpoolForm.toDate.value = "";
                }
            })
            return false;
        }
    }
    if (Number(document.getElementById("rate1").value) <= 0)
    {
        Swal.fire({
            title: "Please enter day rate.",
            didClose: () => {
                document.getElementById("rate1").focus();
            }
        })
        return false;
    }
    return true;
}