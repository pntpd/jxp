function checkshortlist()
{
    if (trim(document.forms[0].jobpostsearch.value) == "") {
        Swal.fire({
            title: "Please select jobpost.",
            didClose: () => {
                document.forms[0].jobpostsearch.focus();
            }
        })
        return false;
    }

    if (Number(document.forms[0].jobpostId.value) <= 0) {
        Swal.fire({
            title: "Please select jobpost using autofill.",
        })
        return false;
    }
    return true;
}

function searchFormShortlist()
{
    if(checkshortlist())
    {
        var id = document.forms[0].jobpostId.value;
        document.forms[0].doSave.value = "no";
        document.forms[0].doCandSearch.value = "yes";
        document.forms[0].jobpostId.value = id;
        document.forms[0].jobpostsearch.value = "";
        document.forms[0].action = "../shortlisting/ShortlistingAction.do";
        document.forms[0].submit();
    }
}

function ViewJobpost()
{
    var id = document.forms[0].jobpostId.value;
    document.forms[0].jobpostId.value = id;
    document.forms[0].target = "_blank";
    document.forms[0].action = "../jobpost/JobPostAction.do?doView=yes";
    document.forms[0].submit();
}

function addtoSortedSet(candidateId, from)
{
    var url = "../ajax/shortlisting/shortlistcandidate.jsp";
    if (from === "ADD") {
        document.getElementById("btnadd_" + candidateId).style.display = "none";
    }
    if (from === "REMOVE") {
        if (document.getElementById("btnadd_" + candidateId)) {
            document.getElementById("btnadd_" + candidateId).style.display = "";
        }
    }
    var httploc = getHTTPObject();
    var getstr = "";
    getstr += "candidateid=" + candidateId;
    getstr += "&from=" + from;
    httploc.open("POST", url, true);
    httploc.onreadystatechange = function ()
    {
        if (httploc.readyState == 4)
        {
            if (httploc.status == 200)
            {
                var response = httploc.responseText;
                document.getElementById('dsortedcandidate').innerHTML = '';
                document.getElementById('dsortedcandidate').innerHTML = response;
            }
        }
    };
    httploc.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    httploc.setRequestHeader("Content-length", getstr.length);
    httploc.setRequestHeader("Connection", "close");
    httploc.send(getstr);
    document.getElementById('dsortedcandidate').innerHTML = "<div><img src='../assets/images/loading.gif' align='absmiddle'/>Please wait. Loading...</div>";
}

function viewTalentPool(id)
{
    document.forms[0].candidateId.value = id;
    document.forms[0].target = "_blank";
    document.forms[0].action = "../talentpool/TalentpoolAction.do?doView=yes";
    document.forms[0].submit();
}

function submitShortlistForm()
{
    if (Number(document.getElementById("sortedcount").value) <= 0) {
        Swal.fire({
            title: "Please shortlist atleast one candidate.",
        })
        return false;
    }
    document.forms[0].doSave.value = "yes";
    document.forms[0].target = "";
    document.forms[0].action = "../shortlisting/ShortlistingAction.do";
    document.forms[0].submit();
}

function getpills(id, flag)
{
    var url = "../ajax/shortlisting/shortlistbypills.jsp";
    var httploc = getHTTPObject();
    var getstr = "";
    getstr += "id=" + id;
    getstr += "&flag=" + flag;
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
                v1 = arr[0] != null ? trim(arr[0]) : "";
                v2 = arr[1] != null ? trim(arr[1]) : "";
                document.getElementById('dsearchparameter').innerHTML = '';
                document.getElementById('dsearchparameter').innerHTML = trim(v1);
                document.getElementById('dcandidate').innerHTML = '';
                document.getElementById('dcandidate').innerHTML = trim(v2);
            }
        }
    };
    httploc.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    httploc.setRequestHeader("Content-length", getstr.length);
    httploc.setRequestHeader("Connection", "close");
    httploc.send(getstr);
    document.getElementById('dsearchparameter').innerHTML = "<div><img src='../assets/images/loading.gif' align='absmiddle'/>Please wait. Loading...</div>";
    document.getElementById('dcandidate').innerHTML = "<div><img src='../assets/images/loading.gif' align='absmiddle'/>Please wait. Loading...</div>";
}

function closemodal()
{
    document.forms[0].doCandSearch.value = "yes";
    document.forms[0].action = "../shortlisting/ShortlistingAction.do";
    document.forms[0].submit();
}

function gotocompliancecheck()
{
    var id = document.forms[0].clientIdIndex.value;
    document.forms[0].clientIdIndex.value = id;
    document.forms[0].action = "../compliancecheck/CompliancecheckAction.do";
    document.forms[0].submit();
}
