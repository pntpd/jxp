function setClientDDLModal()
{
    var url = "../ajax/ddlgeneral/getclient.jsp";
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
                document.getElementById("clientIdModal").innerHTML = '';
                document.getElementById("clientIdModal").innerHTML = response;
            }
        }
    };
    httploc.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    httploc.send(getstr);
}

function setAssetDDLModal()
{
    if(Number(document.forms[0].clientIdModal.value) > 0)
    {
        var url = "../ajax/ddlgeneral/getasset.jsp";
        var httploc = getHTTPObject();
        var getstr = "clientId=" + document.forms[0].clientIdModal.value;
        httploc.open("POST", url, true);
        httploc.onreadystatechange = function ()
        {
            if (httploc.readyState == 4)
            {
                if (httploc.status == 200)
                {
                    var response = httploc.responseText;
                    document.getElementById("assetIdModal").innerHTML = '';
                    document.getElementById("assetIdModal").innerHTML = response;
                }
            }
        };
        httploc.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
        httploc.send(getstr);
    }
}

function setPositionDDLModal()
{
    if(Number(document.forms[0].assetIdModal.value) > 0)
    {
        var url = "../ajax/ddlgeneral/getposition.jsp";
        var httploc = getHTTPObject();
        var getstr = "assetId=" + document.forms[0].assetIdModal.value;
        httploc.open("POST", url, true);
        httploc.onreadystatechange = function ()
        {
            if (httploc.readyState == 4)
            {
                if (httploc.status == 200)
                {
                    var response = httploc.responseText;
                    document.getElementById("positionIdModal").innerHTML = '';
                    document.getElementById("positionIdModal").innerHTML = response;
                }
            }
        };
        httploc.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
        httploc.send(getstr);
    }
}

function submitOnboard(id)
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
    if (doSaveOnboardModal())
    {
        document.getElementById('submitdiv').innerHTML = "<img src='../assets/images/loading.gif' align='absmiddle' />";
        document.talentpoolForm.doSaveOnboardModal.value = "yes";
        document.talentpoolForm.doView.value = "no";
        document.talentpoolForm.doCancel.value = "no";
        document.talentpoolForm.candidateId.value = id;
        document.talentpoolForm.action = "../talentpool/TalentpoolAction.do";
        document.talentpoolForm.submit();
    }
}

function doSaveOnboardModal()
{
    if (document.talentpoolForm.clientIdModal.value == "-1")
    {
        Swal.fire({
            title: "Please select client.",
            didClose: () => {
                document.talentpoolForm.clientIdModal.focus();
            }
        })
        return false;
    }
    if (document.talentpoolForm.assetIdModal.value == "-1")
    {
        Swal.fire({
            title: "Please select asset.",
            didClose: () => {
                document.talentpoolForm.assetIdModal.focus();
            }
        })
        return false;
    }
    if (document.talentpoolForm.positionIdModal.value == "-1")
    {
        Swal.fire({
            title: "Please select Position-Rank.",
            didClose: () => {
                document.talentpoolForm.positionIdModal.focus();
            }
        })
        return false;
    }    
    if (document.talentpoolForm.onboardRemark.value == "")
    {
        Swal.fire({
            title: "Please enter remark.",
            didClose: () => {
                document.talentpoolForm.onboardRemark.focus();
            }
        })
        return false;
    }
    return true;
}

function dataDupm()
{
    document.talentpoolForm.action = "../talentpool/ExportData.do";
    document.talentpoolForm.submit();
}