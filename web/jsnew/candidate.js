function resetFilter()
{
    document.candidateForm.search.value = "";
    document.candidateForm.onlineFlag.value = "0";
    document.candidateForm.statustype.value = "0";
    document.candidateForm.assettypeIdIndex.value = "-1";
    document.candidateForm.positionIdIndex.value = "-1";
    getPositionRank();
    searchFormAjax('s', '-1');
}

function showDetail(id)
{
    document.candidateForm.doView.value = "yes";
    document.candidateForm.candidateId.value = id;
    document.candidateForm.action = "../candidate/CandidateAction.do";
    document.candidateForm.submit();
}

function view(id)
{
    document.candidateForm.doView.value = "yes";
    document.candidateForm.candidateId.value = id;
    document.candidateForm.action = "../candidate/CandidateAction.do";
    document.candidateForm.submit();
}

function view1()
{
    document.candidateForm.doView.value = "yes";
    document.candidateForm.doCancel.value = "no";
    if (document.candidateForm.doViewlangdetail)
        document.candidateForm.doViewlangdetail.value = "no";
    if (document.candidateForm.doViewhealthdetail)
        document.candidateForm.doViewhealthdetail.value = "no";
    if (document.candidateForm.doViewBanklist)
        document.candidateForm.doViewBanklist.value = "no";
    if (document.candidateForm.doViewvaccinationlist)
        document.candidateForm.doViewvaccinationlist.value = "no";
    if (document.candidateForm.doViewgovdocumentlist)
        document.candidateForm.doViewgovdocumentlist.value = "no";
    if (document.candidateForm.doViewtrainingcertlist)
        document.candidateForm.doViewtrainingcertlist.value = "no";
    if (document.candidateForm.doVieweducationlist)
        document.candidateForm.doVieweducationlist.value = "no";
    if (document.candidateForm.doDeletelangdetail)
        document.candidateForm.doDeletelangdetail.value = "no";
    if (document.candidateForm.doViewexperiencelist)
        document.candidateForm.doViewexperiencelist.value = "no";
    document.candidateForm.action = "../candidate/CandidateAction.do";
    document.candidateForm.submit();
}

function setCandidateClass(tp)
{
    if (!(document.candidateForm.photofile.value).match(/(\.(png)|(jpg)|(jpeg))$/i))
    {
        Swal.fire({
            title: "Only .jpg, .jpeg, .png are allowed.",
            didClose: () => {
                document.candidateForm.photofile.focus();
            }
        })
    }
    if (document.candidateForm.photofile.value != "")
    {
        var input = document.candidateForm.photofile;
        if (input.files)
        {
            var file = input.files[0];
            if (file.size > (1024 * 1024 * 5))
            {
                Swal.fire({
                    title: "File size should not exceed 5 MB.",
                    didClose: () => {
                        document.candidateForm.photofile.focus();
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
    document.candidateForm.doModify.value = "no";
    document.candidateForm.doView.value = "no";
    document.candidateForm.doCancel.value = "no";
    document.candidateForm.doAdd.value = "yes";
    document.candidateForm.action = "../candidate/CandidateAction.do";
    document.candidateForm.submit();
}

function modifyForm(id)
{
    document.candidateForm.doModify.value = "yes";
    document.candidateForm.doView.value = "no";
    document.candidateForm.doAdd.value = "no";
    document.candidateForm.doCancel.value = "no";
    document.candidateForm.candidateId.value = id;
    document.candidateForm.action = "../candidate/CandidateAction.do";
    document.candidateForm.submit();
}

function modifyFormview()
{
    document.candidateForm.doModify.value = "yes";
    document.candidateForm.doCancel.value = "no";
    document.candidateForm.action = "../candidate/CandidateAction.do";
    document.candidateForm.submit();
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
        var url = "../ajax/candidate/getinfo.jsp";
        var httploc = getHTTPObject();
        var getstr = "";
        var next_value = escape(document.candidateForm.nextValue.value);
        var search_value = escape(document.candidateForm.search.value);
        var onlineflag = escape(document.candidateForm.onlineFlag.value);
        var statustype = escape(document.candidateForm.statustype.value);
        var assettypeIdIndex = escape(document.candidateForm.assettypeIdIndex.value);
        var positionIdIndex = escape(document.candidateForm.positionIdIndex.value);
        getstr += "nextValue=" + next_value;
        getstr += "&next=" + v;
        getstr += "&search=" + search_value;
        getstr += "&statustype=" + statustype;
        getstr += "&onlineflag=" + onlineflag;
        getstr += "&assettypeIdIndex=" + assettypeIdIndex;
        getstr += "&positionIdIndex=" + positionIdIndex;
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
            var url = "../ajax/candidate/getinfo.jsp";
            var getstr = "";
            var httploc = getHTTPObject();
            var next_value = escape(document.candidateForm.nextValue.value);
            var next_del = "-1";
            if (document.candidateForm.nextDel)
                next_del = escape(document.candidateForm.nextDel.value);
            var search_value = escape(document.candidateForm.search.value);
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
    if (document.candidateForm.doView)
        document.candidateForm.doView.value = "no";
    if (document.candidateForm.doCancel)
        document.candidateForm.doCancel.value = "yes";
    if (document.candidateForm.doSave)
        document.candidateForm.doSave.value = "no";
    if (document.candidateForm.doModify)
        document.candidateForm.doModify.value = "no";
    document.candidateForm.action = "../candidate/CandidateAction.do";
    document.candidateForm.submit();
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
    var url_sort = "../ajax/candidate/sort.jsp";
    var getstr = "";
    var nextValue = 0;
    if (document.candidateForm.nextValue)
        nextValue = document.candidateForm.nextValue.value;
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
    document.candidateForm.reset();
}

function resetFormcandidateindex()
{
    document.candidateForm.reset();
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
    var url = "../ajax/candidate/getdocumentissuedby.jsp";
    var httploc = getHTTPObject();
    var getstr = "documentTypeId=" + document.candidateForm.documentTypeId.value;
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
    var url = "../ajax/candidate/getcity.jsp";
    var httploc = getHTTPObject();
    var getstr = "countrytId=" + document.candidateForm.countryId.value;
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
    if (tp == 3)
    {
        document.candidateForm.doModify.value = "no";
        document.candidateForm.doCancel.value = "no";
        if (document.candidateForm.doaddlangdetail)
            document.candidateForm.doaddlangdetail.value = "no";
        if (document.candidateForm.doViewhealthdetail)
            document.candidateForm.doViewhealthdetail.value = "no";
        if (document.candidateForm.doViewBanklist)
            document.candidateForm.doViewBanklist.value = "yes";
        if (document.candidateForm.doViewvaccinationlist)
            document.candidateForm.doViewvaccinationlist.value = "no";
        if (document.candidateForm.doViewgovdocumentlist)
            document.candidateForm.doViewgovdocumentlist.value = "no";
        if (document.candidateForm.doViewtrainingcertlist)
            document.candidateForm.doViewtrainingcertlist.value = "no";
        if (document.candidateForm.doVieweducationlist)
            document.candidateForm.doVieweducationlist.value = "no";
        if (document.candidateForm.doViewexperiencelist)
            document.candidateForm.doViewexperiencelist.value = "no";
        document.candidateForm.action = "../candidate/CandidateAction.do";
        document.candidateForm.submit();
    }
    if (tp == 2)
    {
        document.candidateForm.doModify.value = "no";
        document.candidateForm.doCancel.value = "no";
        if (document.candidateForm.doViewlangdetail)
            document.candidateForm.doViewlangdetail.value = "yes";
        if (document.candidateForm.doViewhealthdetail)
            document.candidateForm.doViewhealthdetail.value = "no";
        if (document.candidateForm.doViewBanklist)
            document.candidateForm.doViewBanklist.value = "no";
        if (document.candidateForm.doViewvaccinationlist)
            document.candidateForm.doViewvaccinationlist.value = "no";
        if (document.candidateForm.doViewgovdocumentlist)
            document.candidateForm.doViewgovdocumentlist.value = "no";
        if (document.candidateForm.doViewtrainingcertlist)
            document.candidateForm.doViewtrainingcertlist.value = "no";
        if (document.candidateForm.doVieweducationlist)
            document.candidateForm.doVieweducationlist.value = "no";
        if (document.candidateForm.doDeletelangdetail)
            document.candidateForm.doDeletelangdetail.value = "no";
        if (document.candidateForm.doViewexperiencelist)
            document.candidateForm.doViewexperiencelist.value = "no";
        document.candidateForm.action = "../candidate/CandidateAction.do";
        document.candidateForm.submit();
    }
    if (tp == 4)
    {
        document.candidateForm.doModify.value = "no";
        document.candidateForm.doCancel.value = "no";
        if (document.candidateForm.doaddlangdetail)
            document.candidateForm.doaddlangdetail.value = "no";
        if (document.candidateForm.doSavehealthdetail)
            document.candidateForm.doSavehealthdetail.value = "no";
        if (document.candidateForm.doViewhealthdetail)
            document.candidateForm.doViewhealthdetail.value = "yes";
        if (document.candidateForm.doViewBanklist)
            document.candidateForm.doViewBanklist.value = "no";
        if (document.candidateForm.doViewvaccinationlist)
            document.candidateForm.doViewvaccinationlist.value = "no";
        if (document.candidateForm.doViewgovdocumentlist)
            document.candidateForm.doViewgovdocumentlist.value = "no";
        if (document.candidateForm.doViewtrainingcertlist)
            document.candidateForm.doViewtrainingcertlist.value = "no";
        if (document.candidateForm.doVieweducationlist)
            document.candidateForm.doVieweducationlist.value = "no";
        if (document.candidateForm.doViewexperiencelist)
            document.candidateForm.doViewexperiencelist.value = "no";
        document.candidateForm.action = "../candidate/CandidateAction.do";
        document.candidateForm.submit();
    }
    if (tp == 5)
    {
        document.candidateForm.doModify.value = "no";
        document.candidateForm.doCancel.value = "no";
        if (document.candidateForm.doaddlangdetail)
            document.candidateForm.doaddlangdetail.value = "no";
        if (document.candidateForm.doViewhealthdetail)
            document.candidateForm.doViewhealthdetail.value = "no";
        if (document.candidateForm.doViewBanklist)
            document.candidateForm.doViewBanklist.value = "no";
        if (document.candidateForm.doViewvaccinationlist)
            document.candidateForm.doViewvaccinationlist.value = "yes";
        if (document.candidateForm.doViewgovdocumentlist)
            document.candidateForm.doViewgovdocumentlist.value = "no";
        if (document.candidateForm.doViewtrainingcertlist)
            document.candidateForm.doViewtrainingcertlist.value = "no";
        if (document.candidateForm.doVieweducationlist)
            document.candidateForm.doVieweducationlist.value = "no";
        if (document.candidateForm.doViewexperiencelist)
            document.candidateForm.doViewexperiencelist.value = "no";
        document.candidateForm.action = "../candidate/CandidateAction.do";
        document.candidateForm.submit();
    }
    if (tp == 6)
    {
        document.candidateForm.doModify.value = "no";
        document.candidateForm.doCancel.value = "no";
        if (document.candidateForm.doaddlangdetail)
            document.candidateForm.doaddlangdetail.value = "no";
        if (document.candidateForm.doViewhealthdetail)
            document.candidateForm.doViewhealthdetail.value = "no";
        if (document.candidateForm.doViewBanklist)
            document.candidateForm.doViewBanklist.value = "no";
        if (document.candidateForm.doViewvaccinationlist)
            document.candidateForm.doViewvaccinationlist.value = "no";
        if (document.candidateForm.doViewgovdocumentlist)
            document.candidateForm.doViewgovdocumentlist.value = "yes";
        if (document.candidateForm.doViewtrainingcertlist)
            document.candidateForm.doViewtrainingcertlist.value = "no";
        if (document.candidateForm.doVieweducationlist)
            document.candidateForm.doVieweducationlist.value = "no";
        if (document.candidateForm.doViewexperiencelist)
            document.candidateForm.doViewexperiencelist.value = "no";
        document.candidateForm.action = "../candidate/CandidateAction.do";
        document.candidateForm.submit();
    }
    if (tp == 7)
    {
        document.candidateForm.doModify.value = "no";
        document.candidateForm.doCancel.value = "no";
        if (document.candidateForm.doaddlangdetail)
            document.candidateForm.doaddlangdetail.value = "no";
        if (document.candidateForm.doViewhealthdetail)
            document.candidateForm.doViewhealthdetail.value = "no";
        if (document.candidateForm.doViewBanklist)
            document.candidateForm.doViewBanklist.value = "no";
        if (document.candidateForm.doViewvaccinationlist)
            document.candidateForm.doViewvaccinationlist.value = "no";
        if (document.candidateForm.doViewgovdocumentlist)
            document.candidateForm.doViewgovdocumentlist.value = "no";
        if (document.candidateForm.doViewtrainingcertlist)
            document.candidateForm.doViewtrainingcertlist.value = "no";
        if (document.candidateForm.doVieweducationlist)
            document.candidateForm.doVieweducationlist.value = "no";
        if (document.candidateForm.doViewexperiencelist)
            document.candidateForm.doViewexperiencelist.value = "no";
        document.candidateForm.action = "../candidate/CandidateAction.do";
        document.candidateForm.submit();
    }
    if (tp == 8)
    {
        document.candidateForm.doModify.value = "no";
        document.candidateForm.doCancel.value = "no";
        if (document.candidateForm.doaddlangdetail)
            document.candidateForm.doaddlangdetail.value = "no";
        if (document.candidateForm.doViewhealthdetail)
            document.candidateForm.doViewhealthdetail.value = "no";
        if (document.candidateForm.doViewBanklist)
            document.candidateForm.doViewBanklist.value = "no";
        if (document.candidateForm.doViewvaccinationlist)
            document.candidateForm.doViewvaccinationlist.value = "no";
        if (document.candidateForm.doViewgovdocumentlist)
            document.candidateForm.doViewgovdocumentlist.value = "no";
        if (document.candidateForm.doViewtrainingcertlist)
            document.candidateForm.doViewtrainingcertlist.value = "yes";
        if (document.candidateForm.doVieweducationlist)
            document.candidateForm.doVieweducationlist.value = "no";
        if (document.candidateForm.doViewexperiencelist)
            document.candidateForm.doViewexperiencelist.value = "no";
        document.candidateForm.action = "../candidate/CandidateAction.do";
        document.candidateForm.submit();
    }
    if (tp == 9)
    {
        document.candidateForm.doModify.value = "no";
        document.candidateForm.doCancel.value = "no";
        if (document.candidateForm.doaddlangdetail)
            document.candidateForm.doaddlangdetail.value = "no";
        if (document.candidateForm.doViewhealthdetail)
            document.candidateForm.doViewhealthdetail.value = "no";
        if (document.candidateForm.doViewBanklist)
            document.candidateForm.doViewBanklist.value = "no";
        if (document.candidateForm.doViewvaccinationlist)
            document.candidateForm.doViewvaccinationlist.value = "no";
        if (document.candidateForm.doViewgovdocumentlist)
            document.candidateForm.doViewgovdocumentlist.value = "no";
        if (document.candidateForm.doViewtrainingcertlist)
            document.candidateForm.doViewtrainingcertlist.value = "no";
        if (document.candidateForm.doVieweducationlist)
            document.candidateForm.doVieweducationlist.value = "yes";
        if (document.candidateForm.doViewexperiencelist)
            document.candidateForm.doViewexperiencelist.value = "no";
        document.candidateForm.action = "../candidate/CandidateAction.do";
        document.candidateForm.submit();
    }
    if (tp == 10)
    {
        document.candidateForm.doModify.value = "no";
        document.candidateForm.doCancel.value = "no";
        if (document.candidateForm.doaddlangdetail)
            document.candidateForm.doaddlangdetail.value = "no";
        if (document.candidateForm.doViewhealthdetail)
            document.candidateForm.doViewhealthdetail.value = "no";
        if (document.candidateForm.doViewBanklist)
            document.candidateForm.doViewBanklist.value = "no";
        if (document.candidateForm.doViewvaccinationlist)
            document.candidateForm.doViewvaccinationlist.value = "no";
        if (document.candidateForm.doViewgovdocumentlist)
            document.candidateForm.doViewgovdocumentlist.value = "no";
        if (document.candidateForm.doViewtrainingcertlist)
            document.candidateForm.doViewtrainingcertlist.value = "no";
        if (document.candidateForm.doVieweducationlist)
            document.candidateForm.doVieweducationlist.value = "no";
        if (document.candidateForm.doViewexperiencelist)
            document.candidateForm.doViewexperiencelist.value = "yes";
        document.candidateForm.action = "../candidate/CandidateAction.do";
        document.candidateForm.submit();
    }
    if (tp == 11)
    {
        document.candidateForm.doModify.value = "no";
        document.candidateForm.doCancel.value = "no";
        if (document.candidateForm.doaddlangdetail)
            document.candidateForm.doaddlangdetail.value = "no";
        if (document.candidateForm.doViewhealthdetail)
            document.candidateForm.doViewhealthdetail.value = "no";
        if (document.candidateForm.doViewBanklist)
            document.candidateForm.doViewBanklist.value = "no";
        if (document.candidateForm.doViewvaccinationlist)
            document.candidateForm.doViewvaccinationlist.value = "no";
        if (document.candidateForm.doViewgovdocumentlist)
            document.candidateForm.doViewgovdocumentlist.value = "no";
        if (document.candidateForm.doViewtrainingcertlist)
            document.candidateForm.doViewtrainingcertlist.value = "no";
        if (document.candidateForm.doVieweducationlist)
            document.candidateForm.doVieweducationlist.value = "no";
        if (document.candidateForm.doViewexperiencelist)
            document.candidateForm.doViewexperiencelist.value = "no";
        if (document.candidateForm.doViewNomineelist)
            document.candidateForm.doViewNomineelist.value = "yes";
        document.candidateForm.action = "../candidate/CandidateAction.do";
        document.candidateForm.submit();
    }
}

function modifyForm1()
{
    document.candidateForm.doModify.value = "yes";
    document.candidateForm.doCancel.value = "no";
    if (document.candidateForm.doaddlangdetail)
        document.candidateForm.doaddlangdetail.value = "no";
    if (document.candidateForm.doViewhealthdetail)
        document.candidateForm.doViewhealthdetail.value = "no";
    if (document.candidateForm.doViewBanklist)
        document.candidateForm.doViewBanklist.value = "no";
    if (document.candidateForm.doViewvaccinationlist)
        document.candidateForm.doViewvaccinationlist.value = "no";
    if (document.candidateForm.doViewgovdocumentlist)
        document.candidateForm.doViewgovdocumentlist.value = "no";
    if (document.candidateForm.doViewtrainingcertlist)
        document.candidateForm.doViewtrainingcertlist.value = "no";
    if (document.candidateForm.doVieweducationlist)
        document.candidateForm.doVieweducationlist.value = "no";
    if (document.candidateForm.doViewexperiencelist)
        document.candidateForm.doViewexperiencelist.value = "no";
    document.candidateForm.action = "../candidate/CandidateAction.do";
    document.candidateForm.submit();
}

function modifyvaccinationdetailForm(id)
{
    document.candidateForm.doDeletevaccinationdetail.value = "no";
    document.candidateForm.candidatevaccineId.value = id;
    document.candidateForm.doaddvaccinationdetail.value = "yes";
    document.candidateForm.action = "../candidate/CandidateAction.do";
    document.candidateForm.submit();
}

function deletevaccinationForm(id, status)
{
    document.candidateForm.doDeletevaccinationdetail.value = "yes";
    document.candidateForm.candidatevaccineId.value = id;
    document.candidateForm.status.value = status;
    document.candidateForm.doaddvaccinationdetail.value = "no";
    document.candidateForm.action = "../candidate/CandidateAction.do";
    document.candidateForm.submit();
}

function modifybankdetailForm(id)
{
    document.candidateForm.bankdetailId.value = id;
    document.candidateForm.doManageBankdetail.value = "yes";
    document.candidateForm.action = "../candidate/CandidateAction.do";
    document.candidateForm.submit();
}

function deletebankForm(id, status)
{
    document.candidateForm.doDeleteBankdetail.value = "yes";
    document.candidateForm.bankdetailId.value = id;
    document.candidateForm.status.value = status;
    document.candidateForm.doManageBankdetail.value = "no";
    document.candidateForm.action = "../candidate/CandidateAction.do";
    document.candidateForm.submit();
}

function modifydocumentdetailForm(id)
{
    document.candidateForm.govdocumentId.value = id;
    document.candidateForm.domodifygovdocumentdetail.value = "yes";
    document.candidateForm.action = "../candidate/CandidateAction.do";
    document.candidateForm.submit();
}

function deletedocumentForm(id, status)
{
    document.candidateForm.doDeletegovdocumentdetail.value = "yes";
    document.candidateForm.govdocumentId.value = id;
    document.candidateForm.status.value = status;
    document.candidateForm.domodifygovdocumentdetail.value = "no";
    document.candidateForm.action = "../candidate/CandidateAction.do";
    document.candidateForm.submit();
}

function modifytrainingcertdetailForm(id)
{
    document.candidateForm.trainingandcertId.value = id;
    document.candidateForm.doaddtrainingcertdetail.value = "yes";
    document.candidateForm.action = "../candidate/CandidateAction.do";
    document.candidateForm.submit();
}

function deletetrainingcertForm(id, status)
{
    document.candidateForm.doDeletetrainingcertdetail.value = "yes";
    document.candidateForm.trainingandcertId.value = id;
    document.candidateForm.status.value = status;
    document.candidateForm.doaddtrainingcertdetail.value = "no";
    document.candidateForm.action = "../candidate/CandidateAction.do";
    document.candidateForm.submit();
}

function modifyeducationForm(id)
{
    document.candidateForm.educationdetailId.value = id;
    document.candidateForm.doaddeducationdetail.value = "yes";
    document.candidateForm.action = "../candidate/CandidateAction.do";
    document.candidateForm.submit();
}

function deleteeducationForm(id, status)
{
    document.candidateForm.doDeleteeducationdetail.value = "yes";
    document.candidateForm.educationdetailId.value = id;
    document.candidateForm.status.value = status;
    document.candidateForm.doaddeducationdetail.value = "no";
    document.candidateForm.action = "../candidate/CandidateAction.do";
    document.candidateForm.submit();
}

function modifyexperienceForm(id)
{
    document.candidateForm.experiencedetailId.value = id;
    document.candidateForm.doaddexperiencedetail.value = "yes";
    document.candidateForm.action = "../candidate/CandidateAction.do";
    document.candidateForm.submit();
}

function deleteexperienceForm(id, status)
{
    document.candidateForm.doDeleteexperiencedetail.value = "yes";
    document.candidateForm.experiencedetailId.value = id;
    document.candidateForm.status.value = status;
    document.candidateForm.doaddexperiencedetail.value = "no";
    document.candidateForm.action = "../candidate/CandidateAction.do";
    document.candidateForm.submit();
}

function modifylanguageForm(id)
{
    document.candidateForm.doSavelangdetail.value = "no";
    document.candidateForm.candidateLangId.value = id;
    document.candidateForm.doaddlangdetail.value = "yes";
    document.candidateForm.action = "../candidate/CandidateAction.do";
    document.candidateForm.submit();
}

function deletelangaugeForm(id, status)
{
    document.candidateForm.doDeletelangdetail.value = "yes";
    document.candidateForm.candidateLangId.value = id;
    document.candidateForm.status.value = status;
    document.candidateForm.doaddlangdetail.value = "no";
    document.candidateForm.action = "../candidate/CandidateAction.do";
    document.candidateForm.submit();
}

function modifyhealthForm()
{
    document.candidateForm.doaddhealthdetail.value = "yes";
    document.candidateForm.action = "../candidate/CandidateAction.do";
    document.candidateForm.submit();
}

function showhideexperience()
{
    if (document.candidateForm.currentworkingstatus.checked)
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

function setIframeresume(uval)
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
    }, 1000);
}

//--------------------------------------------------- Bank Deatils -----------------------------------------------------
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
        document.candidateForm.domodifygovdocumentdetail.value = "no";
        document.candidateForm.doSavegovdocumentdetail.value = "yes";
        document.candidateForm.action = "../candidate/CandidateAction.do";
        document.candidateForm.submit();
    }
}

function checkDocumentForm()
{
    if (document.candidateForm.documentTypeId.value == "-1")
    {
        Swal.fire({
            title: "Please select document name.",
            didClose: () => {
                document.candidateForm.documentTypeId.focus();
            }
        })
        return false;
    }
    if (trim(document.candidateForm.documentNo.value) == "")
    {
        Swal.fire({
            title: "Please enter document no.",
            didClose: () => {
                document.candidateForm.documentNo.focus();
            }
        })
        return false;
    }
    if (validdesc(document.candidateForm.documentNo) == false)
    {
        return false;
    }
    if (document.candidateForm.dateofissue.value != "")
    {
        if (comparisionTest(document.candidateForm.dateofissue.value, document.candidateForm.currentDate.value) == false)
        {
            Swal.fire({
                title: "Please check date of issue.",
                didClose: () => {
                    document.candidateForm.dateofissue.value = "";
                }
            })
            return false;
        }
    }
    if (document.candidateForm.dateofexpiry.value != "")
    {
        if (comparisionTest(document.candidateForm.dateofissue.value, document.candidateForm.dateofexpiry.value) == false)
        {
            Swal.fire({
                title: "Please check date of expiry.",
                didClose: () => {
                    document.candidateForm.dateofexpiry.value = "";
                }
            })
            return false;
        }
    }
    if (document.candidateForm.documentissuedbyId.value == "-1")
    {
        Swal.fire({
            title: "Please select document issued by.",
            didClose: () => {
                document.candidateForm.documentissuedbyId.focus();
            }
        })
        return false;
    }
    return true;
}

function checkBankDetail()
{
    if (trim(document.candidateForm.bankName.value) == "")
    {
        Swal.fire({
            title: "Please enter bank name.",
            didClose: () => {
                document.candidateForm.bankName.focus();
            }
        })
        return false;
    }
    if (validname(document.candidateForm.bankName) == false)
    {
        return false;
    }
    if (trim(document.candidateForm.accountTypeId.value) == "-1")
    {
        Swal.fire({
            title: "Please select type of account.",
            didClose: () => {
                document.candidateForm.accountTypeId.focus();
            }
        })
        return false;
    }
    if (trim(document.candidateForm.savingAccountNo.value) == "")
    {
        Swal.fire({
            title: "Please enter account no.",
            didClose: () => {
                document.candidateForm.savingAccountNo.focus();
            }
        })
        return false;
    }
    if (trim(document.candidateForm.accountHolder.value) != "")
    {
        if (validname(document.candidateForm.accountHolder) == false)
        {
            return false;
        }
    }
    if (trim(document.candidateForm.branch.value) == "")
    {
        Swal.fire({
            title: "Please enter branch.",
            didClose: () => {
                document.candidateForm.branch.focus();
            }
        })
        return false;
    }
    if (validnamenum(document.candidateForm.branch) == false)
    {
        return false;
    }

    if (trim(document.candidateForm.IFSCCode.value) == "")
    {
        Swal.fire({
            title: "Please enter IFSC code.",
            didClose: () => {
                document.candidateForm.IFSCCode.focus();
            }
        })
        return false;
    }
    if (document.candidateForm.accountTypeId.value == "-1")
    {
        Swal.fire({
            title: "Please select account type.",
            didClose: () => {
                document.candidateForm.accountTypeId.focus();
            }
        })
        return false;
    }
    if (document.candidateForm.bankfile.value != "")
    {
        if (!(document.candidateForm.bankfile.value).match(/(\.(png)|(jpg)|(jpeg)|(pdf))$/i))
        {
            Swal.fire({
                title: "Only .jpg, .jpeg, .png, .pdf are allowed",
                didClose: () => {
                    document.candidateForm.bankfile.focus();
                }
            })
            return false;
        }
        var input = document.candidateForm.bankfile;
        if (input.files)
        {
            var file = input.files[0];
            if (file.size > 1024 * 1024 * 5)
            {
                Swal.fire({
                    title: "File size should not exceed 5 MB.",
                    didClose: () => {
                        document.candidateForm.bankfile.focus();
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
        document.candidateForm.doSaveBankdetail.value = "yes";
        document.candidateForm.doManageBankdetail.value = "no";
        document.candidateForm.doCancel.value = "no";
        document.candidateForm.action = "../candidate/CandidateAction.do";
        document.candidateForm.submit();
    }
}

//-------------------------------------------------------------------------------------- Health ------------------------------------------
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
        document.candidateForm.doaddhealthdetail.value = "no";
        document.candidateForm.doSavehealthdetail.value = "yes";
        document.candidateForm.action = "../candidate/CandidateAction.do";
        document.candidateForm.submit();
    }
}

function checkHealthDetail()
{
    if (trim(document.candidateForm.ssmf.value) == "")
    {
        Swal.fire({
            title: "Please select seaman specific medical fitness.",
            didClose: () => {
                document.candidateForm.ssmf.focus();
            }
        })
        return false;
    }
    if (trim(document.candidateForm.ssmf.value) == "No")
    {
        if (trim(document.candidateForm.ogukmedicalftw.value) == "")
        {
            Swal.fire({
                title: "Please enter OGUK medical FTW.",
                didClose: () => {
                    document.candidateForm.ogukmedicalftw.focus();
                }
            })
            return false;
        }
        if (validdesc(document.candidateForm.ogukmedicalftw) == false)
        {
            return false;
        }
        if (document.candidateForm.ogukexp.value == "")
        {
            Swal.fire({
                title: "Please select OGUK expiry.",
                didClose: () => {
                    document.candidateForm.ogukexp.focus();
                }
            })
            return false;
        }
    }
    if (trim(document.candidateForm.ssmf.value) == "Yes")
    {
        if (trim(document.candidateForm.medifitcert.value) == "")
        {
            Swal.fire({
                title: "Please select medical fitness certificate.",
                didClose: () => {
                    document.candidateForm.medifitcert.focus();
                }
            })
            return false;
        }
        if (document.candidateForm.medifitcertexp.value == "")
        {
            Swal.fire({
                title: "Please enter medical fitness certificate expiry.",
                didClose: () => {
                    document.candidateForm.medifitcertexp.focus();
                }
            })
            return false;
        }
    }
    if (trim(document.candidateForm.cov192doses.value) == "")
    {
        Swal.fire({
            title: "Please select covid-19 2 doses.",
            didClose: () => {
                document.candidateForm.cov192doses.focus();
            }
        })
        return false;
    }
    return true;
}

//------------------------------------------------------------------------------Language----------------------------------------------
function checkLanguageDetail()
{
    if (document.candidateForm.languageId.value == "-1")
    {
        Swal.fire({
            title: "Please select language.",
            didClose: () => {
                document.candidateForm.languageId.focus();
            }
        })
        return false;
    }
    if (document.candidateForm.proficiencyId.value == "-1")
    {
        Swal.fire({
            title: "Please select language proficiency.",
            didClose: () => {
                document.candidateForm.proficiencyId.focus();
            }
        })
        return false;
    }
    if (document.candidateForm.langfile.value != "")
    {
        if (document.candidateForm.candidateLangId.value < 0 || document.candidateForm.langfilehidden.value == "")
        {
            if (document.candidateForm.langfile.value == "")
            {
                Swal.fire({
                    title: "please upload certificate.",
                    didClose: () => {
                        document.candidateForm.langfile.focus();
                    }
                })
                return false;
            }
        }
    }
    if (document.candidateForm.langfile.value != "")
    {
        if (!(document.candidateForm.langfile.value).match(/(\.(png)|(jpg)|(jpeg)|(pdf))$/i))
        {
            Swal.fire({
                title: "Only .jpg, .jpeg, .png, .pdf are allowed.",
                didClose: () => {
                    document.candidateForm.langfile.focus();
                }
            })
            return false;
        }
        var input = document.candidateForm.langfile;
        if (input.files)
        {
            var file = input.files[0];
            if (file.size > 1024 * 1024 * 5)
            {
                Swal.fire({
                    title: "File size should not exceed 5 MB.",
                    didClose: () => {
                        document.candidateForm.langfile.focus();
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
        document.candidateForm.doaddlangdetail.value = "no";
        document.candidateForm.doSavelangdetail.value = "yes";
        document.candidateForm.action = "../candidate/CandidateAction.do";
        document.candidateForm.submit();
    }
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
        document.candidateForm.doaddvaccinationdetail.value = "no";
        document.candidateForm.doSavevaccinationdetail.value = "yes";
        document.candidateForm.action = "../candidate/CandidateAction.do";
        document.candidateForm.submit();
    }
}

function checkVaccineDetail()
{
    if (document.candidateForm.vaccinationNameId.value == "-1")
    {
        Swal.fire({
            title: "Please select vaccination name.",
            didClose: () => {
                document.candidateForm.vaccinationNameId.focus();
            }
        })
        return false;
    }
    if (document.candidateForm.vacinationTypeId.value == "-1")
    {
        Swal.fire({
            title: "Please select vaccination type.",
            didClose: () => {
                document.candidateForm.vacinationTypeId.focus();
            }
        })
        return false;
    }
    if (trim(document.candidateForm.cityName.value) != "")
    {
        if (validdesc(document.candidateForm.cityName) == false)
        {
            return false;
        }
        if (eval(document.candidateForm.placeofapplicationId.value) <= 0)
        {
            Swal.fire({
                title: "Please enter place of application using autofill.",
                didClose: () => {
                    document.candidateForm.cityName.focus();
                }
            })
            return false;
        }
    }
    if (document.candidateForm.dateofapplication.value != "")
    {
        if (comparisionTest(document.candidateForm.dateofapplication.value, document.candidateForm.currentDate.value) == false)
        {
            Swal.fire({
                title: "Please check date of application.",
                didClose: () => {
                    document.candidateForm.dateofapplication.focus();
                }
            })
            return false;
        }
    }
    if (document.candidateForm.dateofexpiry.value != "") {
        if (comparisionTest(document.candidateForm.dateofapplication.value, document.candidateForm.dateofexpiry.value) == false)
        {
            Swal.fire({
                title: "Please check date of expiry.",
                didClose: () => {
                    document.candidateForm.dateofexpiry.focus();
                }
            })
            return false;
        }
    }
    if (document.candidateForm.vaccinedetailfile.value != "")
    {
        if (!(document.candidateForm.vaccinedetailfile.value).match(/(\.(png)|(jpeg)|(jpg)|(pdf))$/i))
        {
            Swal.fire({
                title: "Only .jpeg, .jpg, .png, .pdf are allowed.",
                didClose: () => {
                    document.candidateForm.vaccinedetailfile.focus();
                }
            })
            return false;
        }
        var input = document.candidateForm.vaccinedetailfile;
        if (input.files)
        {
            var file = input.files[0];
            if (file.size > 1024 * 1024 * 5)
            {
                Swal.fire({
                    title: "File size should not exceed 5 MB.",
                    didClose: () => {
                        document.candidateForm.vaccinedetailfile.focus();
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
    if (Number(document.candidateForm.coursenameId.value) <= 0)
    {
        Swal.fire({
            title: "Please select course name.",
            didClose: () => {
                document.candidateForm.coursenameId.focus();
            }
        })
        return false;
    }
    if (trim(document.candidateForm.educationInstitute.value) != "")
    {
        if (validdesc(document.candidateForm.educationInstitute) == false)
        {
            return false;
        }
    }
    if (trim(document.candidateForm.cityName.value) != "")
    {
        if (validdesc(document.candidateForm.cityName) == false)
        {
            return false;
        }
        if (eval(document.candidateForm.locationofInstituteId.value) <= 0)
        {
            Swal.fire({
                title: "Please select location of institute using autofill.",
                didClose: () => {
                    document.candidateForm.cityName.focus();
                }
            })
            return false;
        }
    }
    if (document.candidateForm.dateofissue.value != "")
    {
        if (comparision(document.candidateForm.dateofissue.value, document.candidateForm.currentDate.value) == false)
        {
            Swal.fire({
                title: "Please check date of issue.",
                didClose: () => {
                    document.candidateForm.dateofissue.value = "";
                }
            })
            return false;
        }
    }
    if (trim(document.candidateForm.certificationno.value) != "")
    {       
        if (validdesc(document.candidateForm.certificationno) == false)
        {
            return false;
        }
    }
    if (document.candidateForm.upload1.value != "")
    {
        if (!(document.candidateForm.upload1.value).match(/(\.(png)|(pdf)|(jpg)|(jpeg))$/i))
        {
            Swal.fire({
                title: "Only  .jpg, .jpeg, .png .pdf are allowed.",
                didClose: () => {
                    document.candidateForm.upload1.focus();
                }
            })
            return false;
        }
        var input = document.candidateForm.upload1;
        if (input.files)
        {
            var file = input.files[0];
            if (file.size > 1024 * 1024 * 5)
            {
                Swal.fire({
                    title: "File size should not exceed 5 MB.",
                    didClose: () => {
                        document.candidateForm.upload1.focus();
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
        document.candidateForm.doaddtrainingcertdetail.value = "no";
        document.candidateForm.doSavetrainingcertdetail.value = "yes";
        document.candidateForm.action = "../candidate/CandidateAction.do";
        document.candidateForm.submit();
    }
}

//---------------------------------------------------------------------- Candidate --------------------------------------------------
function clearcity()
{
    document.candidateForm.cityId.value = "0";
    document.candidateForm.cityName.value = "";
    var url = "../ajax/candidate/getcountrycode.jsp";
    var httploc = getHTTPObject();
    var getstr = "countryId=" + document.candidateForm.countryId.value;
    httploc.open("POST", url, true);
    httploc.onreadystatechange = function ()
    {
        if (httploc.readyState == 4)
        {
            if (httploc.status == 200)
            {
                var response = trim(httploc.responseText);
                document.candidateForm.code1Id.value = response;
                document.candidateForm.code2Id.value = response;
                document.candidateForm.code3Id.value = response;
                document.candidateForm.ecode1Id.value = response;
                document.candidateForm.ecode2Id.value = response;
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
        document.candidateForm.doModify.value = "no";
        document.candidateForm.doSave.value = "yes";
        document.candidateForm.doCancel.value = "no";
        document.candidateForm.action = "../candidate/CandidateAction.do";
        document.candidateForm.submit();
    }
}

function checkCandidate()
{
    if (document.candidateForm.photofile.value != "")
    {
        if (!(document.candidateForm.photofile.value).match(/(\.(png)|(jpg)|(jpeg))$/i))
        {
            Swal.fire({
                title: "Only .jpg, .jpeg, .png are allowed.",
                didClose: () => {
                    document.candidateForm.photofile.focus();
                }
            })
            return false;
        }
        var input = document.candidateForm.photofile;
        if (input.files)
        {
            var file = input.files[0];
            if (file.size > 1024 * 1024 * 5)
            {
                Swal.fire({
                    title: "File size should not exceed 5 MB.",
                    didClose: () => {
                        document.candidateForm.photofile.focus();
                    }
                })
                return false;
            }
        }
    }
    if (trim(document.candidateForm.firstname.value) == "")
    {
        Swal.fire({
            title: "Please enter first name.",
            didClose: () => {
                document.candidateForm.firstname.focus();
            }
        })
        return false;
    }
    if (validname(document.candidateForm.firstname) == false)
    {
        return false;
    }
    if (document.candidateForm.middlename.value != "")
    {

        if (validname(document.candidateForm.middlename) == false)
        {
            document.candidateForm.middlename.focus();
            return false;
        }
    }
    if (document.candidateForm.lastname.value == "")
    {
        Swal.fire({
            title: "Please enter last name.",
            didClose: () => {
                document.candidateForm.lastname.focus();
            }
        })
        return false;
    }
    if (validname(document.candidateForm.lastname) == false)
    {
        return false;
    }

    if (document.candidateForm.candidateId.value < 0)
    {
        if (document.candidateForm.fname.value == "")
        {
            Swal.fire({
                title: "Please attach resume.",
                didClose: () => {
                    document.candidateForm.fname.focus();
                }
            })
            return false;
        }
    }
    if (document.candidateForm.dob.value == "")
    {
        Swal.fire({
            title: "Please enter date of birth.",
            didClose: () => {
                document.candidateForm.dob.focus();
            }
        })
        return false;
    }
    if (comparisionTest(document.candidateForm.dob.value, document.candidateForm.currentDate.value) == false)
    {
        Swal.fire({
            title: "Please check date of birth.",
            didClose: () => {
                document.candidateForm.dob.focus();
            }
        })
        return false;
    }
    if (trim(document.candidateForm.placeofbirth.value) != "")
    {
        if (validname(document.candidateForm.placeofbirth) == false)
        {
            document.candidateForm.placeofbirth.focus();
            return false;
        }
    }
    if (document.candidateForm.gender.value == "Gender")
    {
        Swal.fire({
            title: "Please select gender.",
            didClose: () => {
                document.candidateForm.gender.focus();
            }
        })
        return false;
    }
    if (document.candidateForm.maritalstatusId.value <= 0)
    {
        Swal.fire({
            title: "Please select marital status.",
            didClose: () => {
                document.candidateForm.maritalstatusId.focus();
            }
        })
        return false;
    }
    if (document.candidateForm.countryId.value <= 0)
    {
        Swal.fire({
            title: "Please select country.",
            didClose: () => {
                document.candidateForm.countryId.focus();
            }
        })
        return false;
    }
    if (eval(document.candidateForm.cityId.value) <= 0)
    {
        Swal.fire({
            title: "Please select city using autofill.",
            didClose: () => {
                document.candidateForm.cityName.focus();
            }
        })
        return false;
    }
    if (trim(document.candidateForm.cityName.value) == "")
    {
        Swal.fire({
            title: "Please select city using autofill.",
            didClose: () => {
                document.candidateForm.cityName.focus();
            }
        })
        return false;
    }
    if (validdesc(document.candidateForm.cityName) == false)
    {
        return false;
    }
    if (document.candidateForm.nationalityId.value <= 0)
    {
        Swal.fire({
            title: "Please select nationality.",
            didClose: () => {
                document.candidateForm.nationalityId.focus();
            }
        })
        return false;
    }
    if (trim(document.candidateForm.religion.value) != "")
    {
       if (validdesc(document.candidateForm.religion) == false)
        {
            return false;
        }
    }    
    if (document.candidateForm.emailId.value == "")
    {
        Swal.fire({
            title: "Please enter email ID.",
            didClose: () => {
                document.candidateForm.emailId.focus();
            }
        })
        return false;
    }
    if (document.candidateForm.emailId.value != "")
    {
        if (checkEmailAddress(document.candidateForm.emailId) == false)
        {
            Swal.fire({
                title: "Please enter valid email ID.",
                didClose: () => {
                    document.candidateForm.emailId.focus();
                }
            })
            return false;
        }
    }
    if (document.candidateForm.code1Id.value == "")
    {
        Swal.fire({
            title: "Please select ISD code of primary contact number.",
            didClose: () => {
                document.candidateForm.code1Id.focus();
            }
        })
        return false;
    }
    if (document.candidateForm.contactno1.value == "")
    {
        Swal.fire({
            title: "Please enter primary contact number.",
            didClose: () => {
                document.candidateForm.contactno1.focus();
            }
        })
        return false;
    }
    if (document.candidateForm.contactno1.value != "")
    {
        if (document.candidateForm.contactno1.value.length == "")
        {
            Swal.fire({
                title: "Please enter primary contact number.",
                didClose: () => {
                    document.candidateForm.contactno1.focus();
                }
            })
            return false;
        }
        if (!checkContact(document.candidateForm.contactno1.value))
        {
            Swal.fire({
                title: "Please enter valid primary contact number.",
                didClose: () => {
                    document.candidateForm.contactno1.focus();
                }
            })
            return false;
        }
    }
    if (document.candidateForm.contactno2.value != "")
    {
        if (document.candidateForm.code2Id.value == "")
        {
            Swal.fire({
                title: "Please select ISD code of secondary contact number.",
                didClose: () => {
                    document.candidateForm.code2Id.focus();
                }
            })
            return false;
        }
    }
    if (document.candidateForm.contactno2.value != "")
    {
        if (document.candidateForm.contactno2.value == "")
        {
            Swal.fire({
                title: "Please enter secondary contact number.",
                didClose: () => {
                    document.candidateForm.contactno2.focus();
                }
            })
            return false;
        }
        if (document.candidateForm.contactno2.value != "")
        {
            if (document.candidateForm.contactno2.value.length == "")
            {
                Swal.fire({
                    title: "Please enter secondary contact number.",
                    didClose: () => {
                        document.candidateForm.contactno2.focus();
                    }
                })
                return false;
            }
            if (!checkContact(document.candidateForm.contactno2.value))
            {
                Swal.fire({
                    title: "Please enter valid secondary contact number.",
                    didClose: () => {
                        document.candidateForm.contactno2.focus();
                    }
                })
                return false;
            }
        }
    }
    if (document.candidateForm.contactno3.value != "")
    {
        if (document.candidateForm.code3Id.value == "")
        {
            Swal.fire({
                title: "Please select ISD code of additional contact number.",
                didClose: () => {
                    document.candidateForm.code3Id.focus();
                }
            })
            return false;
        }
    }
    if (document.candidateForm.contactno3.value != "")
    {
        if (document.candidateForm.contactno3.value == "")
        {
            Swal.fire({
                title: "Please enter additional contact number.",
                didClose: () => {
                    document.candidateForm.contactno3.focus();
                }
            })
            return false;
        }
        if (document.candidateForm.contactno3.value != "")
        {
            if (document.candidateForm.contactno3.value.length == "")
            {
                Swal.fire({
                    title: "Please enter additional contact number.",
                    didClose: () => {
                        document.candidateForm.contactno3.focus();
                    }
                })
                return false;
            }
            if (!checkContact(document.candidateForm.contactno3.value))
            {
                Swal.fire({
                    title: "Please enter valid additional contact number.",
                    didClose: () => {
                        document.candidateForm.contactno3.focus();
                    }
                })
                return false;
            }
        }
    }
    if (document.candidateForm.ecode1Id.value == "")
    {
        Swal.fire({
            title: "Please select ISD code of emergency contact number1.",
            didClose: () => {
                document.candidateForm.ecode1Id.focus();
            }
        })
        return false;
    }
    if (document.candidateForm.econtactno1.value == "")
    {
        Swal.fire({
            title: "Please enter emergency contact number1.",
            didClose: () => {
                document.candidateForm.econtactno1.focus();
            }
        })
        return false;
    }
    if (document.candidateForm.econtactno1.value != "")
    {
        if (document.candidateForm.econtactno1.value.length == "")
        {
            Swal.fire({
                title: "Please enter emergency contact number1.",
                didClose: () => {
                    document.candidateForm.econtactno1.focus();
                }
            })
            return false;
        }
        if (!checkContact(document.candidateForm.econtactno1.value))
        {
            Swal.fire({
                title: "Please enter valid emergency contact number1.",
                didClose: () => {
                    document.candidateForm.econtactno1.focus();
                }
            })
            return false;
        }
    }
    if (document.candidateForm.econtactno2.value != "")
    {
        if (document.candidateForm.ecode2Id.value == "")
        {
            Swal.fire({
                title: "Please select ISD code of emergency contact number2.",
                didClose: () => {
                    document.candidateForm.ecode2Id.focus();
                }
            })
            return false;
        }
    }
    if (document.candidateForm.econtactno2.value != "")
    {
        if (document.candidateForm.econtactno2.value == "")
        {
            Swal.fire({
                title: "Please enter emergency contact number2.",
                didClose: () => {
                    document.candidateForm.econtactno2.focus();
                }
            })
            return false;
        }
        if (document.candidateForm.econtactno2.value != "")
        {
            if (document.candidateForm.econtactno2.value.length == "")
            {
                Swal.fire({
                    title: "Please enter emergency contact number2.",
                    didClose: () => {
                        document.candidateForm.econtactno2.focus();
                    }
                })
                return false;
            }
            if (!checkContact(document.candidateForm.econtactno2.value))
            {
                Swal.fire({
                    title: "Please enter valid emergency contact number2.",
                    didClose: () => {
                        document.candidateForm.econtactno2.focus();
                    }
                })
                return false;
            }
        }
    }
    if (trim(document.candidateForm.nextofkin.value) != "")
    {
        if (validname(document.candidateForm.nextofkin) == false)
        {
            document.candidateForm.nextofkin.focus();
            return false;
        }
    }
    if (trim(document.candidateForm.address1line1.value) == "")
    {
        Swal.fire({
            title: "Please enter permanent address in line 1.",
            didClose: () => {
                document.candidateForm.address1line1.focus();
            }
        })
        return false;
    }
    if (trim(document.candidateForm.address1line2.value) == "")
    {
        Swal.fire({
            title: "Please enter permanent address in line 2.",
            didClose: () => {
                document.candidateForm.address1line2.focus();
            }
        })
        return false;
    }
    if (document.candidateForm.assettypeId.value == "-1")
    {
        Swal.fire({
            title: "Please select asset type.",
            didClose: () => {
                document.candidateForm.assettypeId.focus();
            }
        })
        return false;
    }
    if (document.candidateForm.employeeid.value != "")
    {
        if (validdesc(document.candidateForm.employeeid) == false)
        {
            document.candidateForm.employeeid.focus();
            return false;
        }
    }
    if (document.candidateForm.positionId.value == "-1")
    {
        Swal.fire({
            title: "Please select position applied for.",
            didClose: () => {
                document.candidateForm.positionId.focus();
            }
        })
        return false;
    }
//    if (document.candidateForm.departmentId.value == "-1")
//    {
//        Swal.fire({
//            title: "Please select preferred department.",
//            didClose: () => {
//                document.candidateForm.departmentId.focus();
//            }
//        })
//        return false;
//    }
    if (document.candidateForm.currencyId.value != "-1")
    {
        if (trim(document.candidateForm.expectedsalary.value) == "")
        {
            Swal.fire({
                title: "Please enter expected salary.",
                didClose: () => {
                    document.candidateForm.expectedsalary.focus();
                }
            })
            return false;
        }
        if (trim(document.candidateForm.expectedsalary.value) <= "0")
        {
            Swal.fire({
                title: "Please enter valid expected salary.",
                didClose: () => {
                    document.candidateForm.expectedsalary.focus();
                }
            })
            return false;
        }
        if (validnum(document.candidateForm.expectedsalary) == false)
        {
            return false;
        }
    }
    return true;
}

function checkExperience()
{
    if (trim(document.candidateForm.companyname.value) == "")
    {
        Swal.fire({
            title: "Please enter company name.",
            didClose: () => {
                document.candidateForm.companyname.focus();
            }
        })
        return false;
    }
    if (validname(document.candidateForm.companyname) == false)
    {
        return false;
    }
    if (document.candidateForm.companyindustryId.value == "-1")
    {
        Swal.fire({
            title: "Please select company industry.",
            didClose: () => {
                document.candidateForm.companyindustryId.focus();
            }
        })
        return false;
    }
    if (document.candidateForm.assettypeId.value == "-1")
    {
        Swal.fire({
            title: "Please select Asset Type.",
            didClose: () => {
                document.candidateForm.assettypeId.focus();
            }
        })
        return false;
    }
    if (trim(document.candidateForm.assetname.value) == "")
    {
        Swal.fire({
            title: "Please enter asset name.",
            didClose: () => {
                document.candidateForm.assetname.focus();
            }
        })
        return false;
    }
    if (validdesc(document.candidateForm.assetname) == false)
    {
        return false;
    }
    if (document.candidateForm.positionId.value == "-1")
    {
        Swal.fire({
            title: "Please select position.",
            didClose: () => {
                document.candidateForm.positionId.focus();
            }
        })
        return false;
    }
//    if (document.candidateForm.departmentId.value == "-1")
//    {
//        Swal.fire({
//            title: "Please select department/function.",
//            didClose: () => {
//                document.candidateForm.departmentId.focus();
//            }
//        })
//        return false;
//    }    
//    if (trim(document.candidateForm.clientpartyname.value) == "")
//    {
//        Swal.fire({
//            title: "Please enter operator.",
//            didClose: () => {
//                document.candidateForm.clientpartyname.focus();
//            }
//        })
//        return false;
//    }
    if (trim(document.candidateForm.clientpartyname.value) != "")
    {
        if (validdesc(document.candidateForm.clientpartyname) == false)
        {
            return false;
        }
    }
    if (document.candidateForm.lastdrawnsalary.value != "") {
        if (validdesc(document.candidateForm.lastdrawnsalary) == false)
        {
            return false;
        }
    }
    if (document.candidateForm.workstartdate.value == "")
    {
        Swal.fire({
            title: "Please enter work start date.",
            didClose: () => {
                document.candidateForm.workstartdate.focus();
            }
        })
        return false;
    }
    if (comparisionTest(document.candidateForm.workstartdate.value, document.candidateForm.currentDate.value) == false)
    {
        Swal.fire({
            title: "Please check work start date.",
            didClose: () => {
                document.candidateForm.workstartdate.focus();
            }
        })
        return false;
    }
    if (document.candidateForm.currentworkingstatus.checked == false)
    {
        if (document.candidateForm.workenddate.value == "")
        {
            Swal.fire({
                title: "Please enter work end date.",
                didClose: () => {
                    document.candidateForm.workenddate.focus();
                }
            })
            return false;
        }
        if (comparisionTest(document.candidateForm.workstartdate.value, document.candidateForm.workenddate.value) == false)
        {
            Swal.fire({
                title: "Please select work end date.",
                didClose: () => {
                    document.candidateForm.workenddate.focus();
                }
            })
            return false;
        }
        if (comparisionTest(document.candidateForm.workenddate.value, document.candidateForm.currentDate.value) == false)
        {
            Swal.fire({
                title: "Please check work end date.",
                didClose: () => {
                    document.candidateForm.workenddate.focus();
                }
            })
            return false;
        }
    }
    if (document.candidateForm.experiencefile.value != "")
    {
        if (!(document.candidateForm.experiencefile.value).match(/(\.(png)|(jpg)|(jpeg)|(pdf))$/i))
        {
            Swal.fire({
                title: "Only .jpg, .jpeg, .png, .pdf are allowed.",
                didClose: () => {
                    document.candidateForm.experiencefile.focus();
                }
            })
            return false;
        }
        var input = document.candidateForm.experiencefile;
        if (input.files)
        {
            var file = input.files[0];
            if (file.size > 1024 * 1024 * 5)
            {
                Swal.fire({
                    title: "File size should not exceed 5 MB.",
                    didClose: () => {
                        document.candidateForm.experiencefile.focus();
                    }
                })
                return false;
            }
        }
    }
    if (trim(document.candidateForm.ownerpool.value) != "")
    {
        if (validdesc(document.candidateForm.ownerpool) == false)
        {
            return false;
        }
    }
    if (document.candidateForm.workingfile.value != "")
    {
        if (!(document.candidateForm.workingfile.value).match(/(\.(png)|(jpg)|(jpeg)|(pdf))$/i))
        {
            Swal.fire({
                title: "Only .jpg, .jpeg, .png, .pdf are allowed.",
                didClose: () => {
                    document.candidateForm.workingfile.focus();
                }
            })
            return false;
        }
        var input = document.candidateForm.workingfile;
        if (input.files)
        {
            var file = input.files[0];
            if (file.size > 1024 * 1024 * 5)
            {
                Swal.fire({
                    title: "File size should not exceed 5 MB.",
                    didClose: () => {
                        document.candidateForm.workingfile.focus();
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
        document.candidateForm.doaddexperiencedetail.value = "no";
        document.candidateForm.doSaveexperiencedetail.value = "yes";
        document.candidateForm.action = "../candidate/CandidateAction.do";
        document.candidateForm.submit();
    }
}

function checkEducationDeatail()
{
    if (document.candidateForm.kindId.value == "-1")
    {
        Swal.fire({
            title: "Please select education kind.",
            didClose: () => {
                document.candidateForm.kindId.focus();
            }
        })
        return false;
    }
    if (document.candidateForm.degreeId.value == "-1")
    {
        Swal.fire({
            title: "Please select education degree.",
            didClose: () => {
                document.candidateForm.degreeId.focus();
            }
        })
        return false;
    }
    if (document.candidateForm.coursestarted.value != "")
    {
        if (comparisionTest(document.candidateForm.coursestarted.value, document.candidateForm.currentDate.value) == false)
        {
            Swal.fire({
                title: "Please check course start date.",
                didClose: () => {
                    document.candidateForm.coursestarted.value = "";
                }
            })
            return false;
        }
    }
    if (document.candidateForm.passingdate.value != "")
    {
        if (comparisionTest(document.candidateForm.coursestarted.value, document.candidateForm.passingdate.value) == false)
        {
            Swal.fire({
                title: "Please check passing date.",
                didClose: () => {
                    document.candidateForm.passingdate.value = "";
                }
            })
            return false;
        }
    }
    if (document.candidateForm.educationfile.value != "")
    {
        if (!(document.candidateForm.educationfile.value).match(/(\.(png)|(jpg)|(jpeg)|(pdf))$/i))
        {
            Swal.fire({
                title: "Only .jpg, .jpeg, .png, .pdf are allowed.",
                didClose: () => {
                    document.candidateForm.educationfile.focus();
                }
            })
            return false;
        }
        var input = document.candidateForm.educationfile;
        if (input.files)
        {
            var file = input.files[0];
            if (file.size > 1024 * 1024 * 5)
            {
                Swal.fire({
                    title: "File size should not exceed 5 MB.",
                    didClose: () => {
                        document.candidateForm.educationfile.focus();
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
        document.candidateForm.doaddeducationdetail.value = "no";
        document.candidateForm.doSaveeducationdetail.value = "yes";
        document.candidateForm.action = "../candidate/CandidateAction.do";
        document.candidateForm.submit();
    }
}

function viewworkexpfiles(filename1, filename2)
{
    var url = "../ajax/candidate/getimg_exp.jsp";
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
    var url = "../ajax/candidate/getimg.jsp";
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
                var arr = new Array();
                arr = response.split('#@#');
                var v1 = arr[0];
                var v2 = trim(arr[1]);

                document.getElementById('viewfilesdiv').innerHTML = '';
                document.getElementById('viewfilesdiv').innerHTML = v1;
                if(document.getElementById("rcount"))
                    document.getElementById("rcount").innerHTML = v2;
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

function delpic(clientassetpicId, clientassetId)
{
    var url = "../ajax/candidate/delimg.jsp";
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

//function addtomaster(type)
//{
//    document.candidateForm.mtype.value = type;
//    document.candidateForm.mname.value = "";
//    if (type == 1)
//        document.getElementById("maddid").innerHTML = "Relation";
//    else if (type == 2)
//        document.getElementById("maddid").innerHTML = "Department";
//    else if (type == 3)
//        document.getElementById("maddid").innerHTML = "City";
//}

function addtomaster(type)
{
    document.candidateForm.mtype.value = type;
    document.candidateForm.mname.value = "";
    if (type == 1)
        document.getElementById("maddid").innerHTML = "Relation";
    else if (type == 2)
        document.getElementById("maddid").innerHTML = "Department";    
    else if (type == 3 || type == 4)
    {
        if (Number(document.forms[0].countryId.value) <= 0) 
        {
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
                        else if (type == "3") 
                        {
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
                document.candidateForm.mname.focus();
            }
        })
    }
}

function checkEmailAjaxCandidate()
{
    var url_email = "../ajax/candidate/checkemail.jsp";
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
    var url = "../ajax/candidate/positions.jsp";
    var httploc = getHTTPObject();
    var getstr = "assettypeId="+document.candidateForm.assettypeId.value;
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
    var assettypeId = document.forms[0].assettypeId.value;
    var url = "../ajax/candidate/assetpositions2.jsp";
    var httploc = getHTTPObject();
    var getstr = "assettypeId=" + assettypeId;
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
    document.candidateForm.action = "../candidate/CandidateIndexExportAction.do";
    document.candidateForm.submit();
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
                childId = document.candidateForm.candidateLangId.value;
            else if (type == 2 || type == 3)
                childId = document.candidateForm.experiencedetailId.value;
            var url = "../ajax/candidate/delfile.jsp";
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
                                document.candidateForm.langfilehidden.value = "";
                            } else if (type == 2)
                            {
                                document.getElementById("preview_2").style.display = "none";
                                document.candidateForm.experiencehiddenfile.value = "";
                            } else if (type == 3)
                            {
                                document.getElementById("preview_3").style.display = "none";
                                document.candidateForm.workinghiddenfile.value = "";
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
    if (document.candidateForm.ssmf.value == "Yes")
    {
        document.getElementById('spanId3').innerHTML = "*";
        document.getElementById('spanId4').innerHTML = "*";
    } else if (document.candidateForm.ssmf.value == "No")
    {
        document.getElementById('spanId1').innerHTML = "*";
        document.getElementById('spanId2').innerHTML = "*";
    }
}

function viewimgdoc(govId)
{
    var url = "../ajax/candidate/getimg_doc.jsp";
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
            var url = "../ajax/candidate/delimg_doc.jsp";
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

function getPositionRank()
{
    var url = "../ajax/candidate/assetpositions.jsp";
    var httploc = getHTTPObject();
    var getstr = "assettypeId=" + document.forms[0].assettypeIdIndex.value;
    httploc.open("POST", url, true);
    httploc.onreadystatechange = function ()
    {
        if (httploc.readyState == 4)
        {
            if (httploc.status == 200)
            {
                var response = httploc.responseText;
                document.getElementById("positionIdIndex").innerHTML = '';
                document.getElementById("positionIdIndex").innerHTML = response;
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
    var url = "../ajax/candidate/getinfo_certificate.jsp";
    var httploc = getHTTPObject();
    var getstr = "";
    var next_certificatevalue = (document.candidateForm.nextCertificateValue.value);
    var candidateId = document.candidateForm.candidateId.value;
    var courseIndex = (document.candidateForm.courseIndex.value);
    getstr += "courseIndex=" + courseIndex;
    getstr += "&nextCertificateValue="+next_certificatevalue;
    getstr += "&candidateId="+candidateId;
    getstr += "&next="+v;
    getstr += "&doDirect="+v1;
    httploc.open("POST", url, true);
    httploc.onreadystatechange = function()
    {
        if (httploc.readyState == 4)
        {
            if(httploc.status == 200)
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

function exporttoexcelnew()
{
    document.candidateForm.action = "../candidate/ExportExcelAction.do";
    document.candidateForm.submit();
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
            document.candidateForm.healthfileId.value = healthId;
            document.candidateForm.doDeleteHealthFile.value = "yes";    
            document.candidateForm.action = "../candidate/CandidateAction.do";
            document.candidateForm.submit();   
        }
    });
}

function modifynomineedetailForm(id)
{
    document.candidateForm.nomineedetailId.value = id;
    document.candidateForm.doManageNomineedetail.value = "yes";
    document.candidateForm.action = "../candidate/CandidateAction.do";
    document.candidateForm.submit();
}

function deletenomineeForm(id, status)
{
    document.candidateForm.doDeleteNomineedetail.value = "yes";
    document.candidateForm.nomineedetailId.value = id;
    document.candidateForm.status.value = status;
    document.candidateForm.doManageNomineedetail.value = "no";
    document.candidateForm.action = "../candidate/CandidateAction.do";
    document.candidateForm.submit();
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
        document.candidateForm.doSaveNomineedetail.value = "yes";
        document.candidateForm.doManageNomineedetail.value = "no";
        document.candidateForm.doCancel.value = "no";
        document.candidateForm.action = "../candidate/CandidateAction.do";
        document.candidateForm.submit();
    }
}

function checkNomineeDetail()
{
    if (trim(document.candidateForm.nomineeName.value) == "")
    {
        Swal.fire({
            title: "Please enter nominee name.",
            didClose: () => {
                document.candidateForm.nomineeName.focus();
            }
        })
        return false;
    }
    if (validdesc(document.candidateForm.nomineeName) == false)
    {
        return false;
    }
    if (document.candidateForm.code1Id.value == "")
    {
        Swal.fire({
            title: "Please select ISD code of contact number.",
            didClose: () => {
                document.candidateForm.code1Id.focus();
            }
        })
        return false;
    }
    if (trim(document.candidateForm.nomineeContactno.value) == "")
    {
        Swal.fire({
            title: "Please enter nominee contact number.",
            didClose: () => {
                document.candidateForm.nomineeContactno.focus();
            }
        })
        return false;
    }
    if (!checkContact(document.candidateForm.nomineeContactno.value))
    {
        Swal.fire({
            title: "Please enter valid contact number.",
            didClose: () => {
                document.candidateForm.nomineeContactno.focus();
            }
        })
        return false;
    }
    if (trim(document.candidateForm.relationId.value) == "-1")
    {
        Swal.fire({
            title: "Please select nominee relation.",
            didClose: () => {
                document.candidateForm.relationId.focus();
            }
        })
        return false;
    }
    if (Number(document.candidateForm.age.value) >100)
    {
        Swal.fire({
            title: "Please enter valid age.",
            didClose: () => {
                document.candidateForm.age.focus();
            }
        })
        return false;
    }
    if (Number(document.candidateForm.percentage.value) >100)
    {
        Swal.fire({
            title: "Please enter valid Percentage of distribution.",
            didClose: () => {
                document.candidateForm.percentage.focus();
            }
        })
        return false;
    }
    return true;
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