function printpage()
{
    var url = "print.jsp";       
    document.forms[0].action=NewWindow(url,'print','750','720','yes','center');
}

function setAssetDDL()
{
    var url = "../ajax/dashboard/getasset.jsp";
    document.getElementById("assetIdIndex").value = '-1';
    var httploc = getHTTPObject();
    var getstr = "clientIdIndex=" + document.dashboardForm.clientIdIndex.value + "&from=asset";
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

function searchcr()
{
    if (checkdashboard())
    {
        document.dashboardForm.doSearchCR.value = "yes";
        document.dashboardForm.action = "../dashboard/DashboardAction.do";
        document.dashboardForm.submit();
    }
}

function checkdashboard()
{
     if(document.dashboardForm.clientIdIndex.value <= "0")
    {
     Swal.fire({
        title: "Please select client.",
        didClose:() => {
          document.dashboardForm.clientIdIndex.focus();
        }
        }) 
        return false;
    }
     if(document.dashboardForm.assetIdIndex.value <= "0")
    {
     Swal.fire({
        title: "Please select asset.",
        didClose:() => {
          document.dashboardForm.assetIdIndex.focus();
        }
        }) 
        return false;
    }
     if(document.dashboardForm.month.value == "-1")
    {
     Swal.fire({
        title: "Please select month.",
        didClose:() => {
          document.dashboardForm.month.focus();
        }
        }) 
        return false;
    }
    if (document.dashboardForm.year.value == "-1")
    {
        Swal.fire({
        title: "Please select year.",
        didClose:() => {
          document.dashboardForm.year.focus();
        }
        }) 
        return false;
    }
    return true;    
}

function searchcrpp()
{
    if(checkdate())
    {
        document.dashboardForm.doSearchCRpp.value = "yes";
        document.dashboardForm.action = "../dashboard/DashboardAction.do";
        document.dashboardForm.submit();
    }
}

 function checkdate()
 {
      if (document.dashboardForm.fromdate.value == "" || document.dashboardForm.todate.value == "" )
    {
        Swal.fire({
        title: "Please select date.",
        didClose:() => {
          document.dashboardForm.fromdate.focus();
        }
        }) 
        return false;
    }
    return true;
}

function resetsearchcr()
{
    document.dashboardForm.reset();
    document.dashboardForm.month.value = -1;
    document.dashboardForm.year.value = -1;
    document.dashboardForm.doCR.value = "yes";
    document.dashboardForm.action = "../dashboard/DashboardAction.do";
    document.dashboardForm.submit();
}

function setValposition()
{
    var crids = $("input[name='pcb']:checked").map(function () {
        return this.value;
    }).get().join(',');
    document.dashboardForm.positioncb.value = crids;
}

function setValperson()
{
    var crids = $("input[name='crewcb']:checked").map(function () {
        return this.value;
    }).get().join(',');
    document.dashboardForm.crewrotationcb.value = crids;
}

function setPersonnellist()
{
    var url = "../ajax/dashboard/getpersonnel.jsp";
    var httploc = getHTTPObject();
    var getstr = "searchPersonnel=" + document.dashboardForm.searchPersonnel.value;
    getstr += "&clientIdIndex=" + document.dashboardForm.clientIdIndex.value;
    getstr += "&assetIdIndex=" + document.dashboardForm.assetIdIndex.value;
    httploc.open("POST", url, true);
    httploc.onreadystatechange = function ()
    {
        if (httploc.readyState == 4)
        {
            if (httploc.status == 200)
            {
                var response = httploc.responseText;
                document.getElementById("sPersonnel").innerHTML = '';
                document.getElementById("sPersonnel").innerHTML = response;
            }
        }
    };
    httploc.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    httploc.send(getstr);
}

function setPositionlist()
{
    var url = "../ajax/dashboard/getposition.jsp";
    var httploc = getHTTPObject();
    var getstr = "searchPosition=" + document.dashboardForm.searchPosition.value;
    getstr += "&clientIdIndex=" + document.dashboardForm.clientIdIndex.value;
    getstr += "&assetIdIndex=" + document.dashboardForm.assetIdIndex.value;
    httploc.open("POST", url, true);
    httploc.onreadystatechange = function ()
    {
        if (httploc.readyState == 4)
        {
            if (httploc.status == 200)
            {
                var response = httploc.responseText;
                document.getElementById("sPosition").innerHTML = '';
                document.getElementById("sPosition").innerHTML = response;
            }
        }
    };
    httploc.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    httploc.send(getstr);
}

function setlistbycase(type)
{
    document.dashboardForm.ttype.value = type;
    var url = "../ajax/dashboard/getlistbycase.jsp";
    var httploc = getHTTPObject();
    var getstr = "type=" + type;
    getstr += "&clientIdIndex=" + document.dashboardForm.clientIdIndex.value;
    getstr += "&assetIdIndex=" + document.dashboardForm.assetIdIndex.value;
    httploc.open("POST", url, true);
    httploc.onreadystatechange = function ()
    {
        if (httploc.readyState == 4)
        {
            if (httploc.status == 200)
            {
                var response = trim(httploc.responseText);
                document.getElementById("listbycasebody").innerHTML = '';
                document.getElementById("listbycasebody").innerHTML = response;
                jQuery(document).ready(function() {
                jQuery(".main-table1").clone(false).appendTo('#listbycasebody').addClass('clone');
                });
            }
        }
    };
    httploc.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    httploc.send(getstr);
}

function chechddl()
{
    if (Number(document.dashboardForm.clientIdIndex.value) <= 0)
    {
        Swal.fire({
            title: "Please select client.",
            didClose: () => {
                document.dashboardForm.clientIdIndex.focus();
            }
        })
        return false;
    }
    if (Number(document.dashboardForm.assetIdIndex.value) <= 0)
    {
        Swal.fire({
            title: "Please select asset.",
            didClose: () => {
                document.dashboardForm.assetIdIndex.focus();
            }
        })
        return false;
    }
    return true;
}

function searchcount1()
{
    if(chechddl())
    {
        document.dashboardForm.doDashboardSearch.value = "yes";
        document.dashboardForm.action = "../dashboard/DashboardAction.do";
        document.dashboardForm.submit();
    }
}

function exporttoexcel(exptype)
{
    document.dashboardForm.action = "../dashboard/ExportdetailAction.do?exptype=" + exptype;
    document.dashboardForm.submit();
}

function gotod(tp)
{
    if(Number(tp) == 1)
        document.dashboardForm.action = "../dashboard/DashboardAction.do?doCR=yes";
    document.dashboardForm.submit();
}

function gotohome()
{
    document.dashboardForm.action = "/jxp/dashboard/DashboardAction.do?doDashboardSearch=yes";
    document.dashboardForm.submit();
}

function setsfmodal(crewrotationId,date)
{
    var url = "../ajax/dashboard/getsfmodal.jsp";
    var httploc = getHTTPObject();
    var getstr = "crewrotationId=" + crewrotationId;
    getstr += "&date=" + date;
    httploc.open("POST", url, true);
    httploc.onreadystatechange = function ()
    {
        if (httploc.readyState == 4)
        {
            if (httploc.status == 200)
            {
                var response = httploc.responseText;
                document.getElementById("sfmodal").innerHTML = '';
                document.getElementById("sfmodal").innerHTML = response;
            }
        }
    };
    httploc.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    httploc.send(getstr);
}

function setearlymodal(crewrotationId,date)
{
    var url = "../ajax/dashboard/getearlymodal.jsp";
    var httploc = getHTTPObject();
    var getstr = "crewrotationId=" + crewrotationId;
    getstr += "&date=" + date;
    httploc.open("POST", url, true);
    httploc.onreadystatechange = function ()
    {
        if (httploc.readyState == 4)
        {
            if (httploc.status == 200)
            {
                var response = httploc.responseText;
                document.getElementById("sfmodal").innerHTML = '';
                document.getElementById("sfmodal").innerHTML = response;
            }
        }
    };
    httploc.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    httploc.send(getstr);
}

function settrainingmodal(crewrotationId,date)
{
    var url = "../ajax/dashboard/gettrainingmodal.jsp";
    var httploc = getHTTPObject();
    var getstr = "crewrotationId=" + crewrotationId;
    getstr += "&date=" + date;
    httploc.open("POST", url, true);
    httploc.onreadystatechange = function ()
    {
        if (httploc.readyState == 4)
        {
            if (httploc.status == 200)
            {
                var response = httploc.responseText;
                document.getElementById("sfmodal").innerHTML = '';
                document.getElementById("sfmodal").innerHTML = response;
            }
        }
    };
    httploc.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    httploc.send(getstr);
}

function setTpmodal(crewrotationId,date)
{
    var url = "../ajax/dashboard/gettpmodal.jsp";
    var httploc = getHTTPObject();
    var getstr = "crewrotationId=" + crewrotationId;
    getstr += "&date=" + date;
    httploc.open("POST", url, true);
    httploc.onreadystatechange = function ()
    {
        if (httploc.readyState == 4)
        {
            if (httploc.status == 200)
            {
                var response = httploc.responseText;
                document.getElementById("sfmodal").innerHTML = '';
                document.getElementById("sfmodal").innerHTML = response;
            }
        }
    };
    httploc.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    httploc.send(getstr);
}

function setSbymodal(crewrotationId,date)
{
    var url = "../ajax/dashboard/getsbymodal.jsp";
    var httploc = getHTTPObject();
    var getstr = "crewrotationId=" + crewrotationId;
    getstr += "&date=" + date;
    httploc.open("POST", url, true);
    httploc.onreadystatechange = function ()
    {
        if (httploc.readyState == 4)
        {
            if (httploc.status == 200)
            {
                var response = httploc.responseText;
                document.getElementById("sfmodal").innerHTML = '';
                document.getElementById("sfmodal").innerHTML = response;
            }
        }
    };
    httploc.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    httploc.send(getstr);
}

function view()
{
    if (document.forms[0].doView)
        document.forms[0].doView.value = "yes";
    document.forms[0].clientId.value = document.forms[0].clientIdIndex.value;
    document.forms[0].clientassetId.value = document.forms[0].assetIdIndex.value;
    document.forms[0].target = "";
    document.forms[0].action = "../crewrotation/CrewrotationAction.do";
    document.forms[0].submit();
}

function searchtraingcount()
{
    document.forms[0].target = "_self";
    document.forms[0].doTrainingSearch.value = "yes";
    document.forms[0].action = "../dashboard/DashboardAction.do";
    document.forms[0].submit();
}

function searchcrecruitcount()
{
    document.forms[0].target = "_self";
    document.forms[0].doCrecruitmentSearch.value = "yes";
    document.forms[0].action = "../dashboard/DashboardAction.do";
    document.forms[0].submit();
}

function dashboard1()
{
    document.forms[0].target = "_self";    
    document.forms[0].doCR.value = "no";
    document.forms[0].doSearchCR.value = "yes";
    document.forms[0].action = "../dashboard/DashboardAction.do";
    document.forms[0].submit();
}

function directdashboard()
{
    document.forms[0].target = "_self";
    if(Number(document.forms[0].stype.value) == 1)
    {
        var assetIdIndex = 0;
        if(document.forms[0].assetIdIndex)
            assetIdIndex = document.forms[0].assetIdIndex.value;
        if(Number(assetIdIndex) > 0)
        {            
            document.forms[0].action = "../dashboard/DashboardAction.do?doSearchCR=yes";
        }
        else
            document.forms[0].action = "../dashboard/DashboardAction.do?doCR=yes";
        document.forms[0].submit();
    }
    else if(Number(document.forms[0].stype.value) == 2)
    {   
        document.forms[0].action = "../dashboard/DashboardAction.do?doCrewEnr=yes";
        document.forms[0].submit();
    }   
    else if(Number(document.forms[0].stype.value) == 3)
    {   
        document.forms[0].action = "../dashboard/DashboardAction.do?doCrecruitmentSearch=yes";
        document.forms[0].submit();
    } 
    else if(Number(document.forms[0].stype.value) == 4)
    {    
        document.forms[0].action = "../dashboard/DashboardAction.do?doTrainingSearch=yes";
        document.forms[0].submit();
    } 
}