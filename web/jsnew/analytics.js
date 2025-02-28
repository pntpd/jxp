function setAssetDDL()
{
    var url = "../ajax/dashboard/getasset.jsp";
    document.getElementById("assetIdIndex").value = '-1';
    var httploc = getHTTPObject();
    var getstr = "clientIdIndex=" + document.analyticsForm.clientIdIndex.value + "&from=asset";
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

function searchform()
{
    if (checkanalytics())
    {
        document.analyticsForm.doCrew.value = "yes";
        document.analyticsForm.action = "../analytics/AnalyticsAction.do";
        document.analyticsForm.submit();
    }
}

function checkanalytics()
{
    if(document.analyticsForm.clientIdIndex.value <= "0")
    {
        Swal.fire({
            title: "Please select client.",
            didClose:() => {
              document.analyticsForm.clientIdIndex.focus();
            }
            }) 
        return false;
    }
    if(document.analyticsForm.assetIdIndex.value <= "0")
    {
        Swal.fire({
            title: "Please select asset.",
            didClose:() => {
              document.analyticsForm.assetIdIndex.focus();
            }
            }) 
        return false;
    }
    return true;    
}

function searchtraining()
{
    if (checkanalytics())
    {
        document.analyticsForm.doTraining.value = "yes";
        document.analyticsForm.action = "../analytics/AnalyticsAction.do";
        document.analyticsForm.submit();
    }
}

function searchformdashboard()
{
    if (checkanalytics())
    {
        document.forms[0].action = "../dashboard/DashboardAction.do?doCR=yes";
        document.forms[0].submit();
    }
}

function checkval()
{
    if (trim(document.forms[0].fromDate.value) == "")
    {
        Swal.fire({
        title: "Please select from date.",
        didClose:() => {
          document.forms[0].fromDate.focus();
        }
        }) 
        return false;
    }
    if (trim(document.forms[0].toDate.value) == "")
    {
        Swal.fire({
        title: "Please select to date.",
        didClose:() => {
          document.forms[0].toDate.focus();
        }
        }) 
        return false;
    }
    if(document.forms[0].clientId.value == "-1")
    {
        Swal.fire({
        title: "Please select client.",
        didClose:() => {
          document.forms[0].clientId.focus();
        }
        }) 
        return false;
    }
    if(document.forms[0].assetId.value == "-1")
    {
        Swal.fire({
        title: "Please select asset.",
        didClose:() => {
          document.forms[0].assetId.focus();
        }
        }) 
        return false;
    }
    return true;
}

function showattendance()
{
    if(checkval())
    {
        var clientId = document.forms[0].clientId.value;
        var assetId = document.forms[0].assetId.value;
        var fromdate = document.forms[0].fromDate.value;
        var todate = document.forms[0].toDate.value;
        var url = "https://uxp.planetcp.com:1115/PCP/ReportSchedulerJXP/www/MobilePages/DynamicHtmlWeb.aspx?ReportID=90&UserID=10710&UnitID=0&ClientId="+clientId+"&AssetId="+assetId+"&Fromdate="+fromdate+"&Todate="+todate+"&JXPReport=1";
        window.open(url, '_blank');
    }
}

function setAssetDDL2()
{
    var url = "../ajax/dashboard/getasset.jsp";
    document.getElementById("assetIdIndex").value = '-1';
    var httploc = getHTTPObject();
    var getstr = "clientIdIndex=" + document.analyticsForm.clientId.value + "&from=asset";
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