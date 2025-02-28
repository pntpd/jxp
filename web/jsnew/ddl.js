function setCountryDDL()
    {            
        var url = "../ajax/ddl/getcountry.jsp";
        var httploc = getHTTPObject();
        var getstr = "clientId="+document.forms[0].clientIdIndex.value;
        httploc.open("POST", url, true);
        httploc.onreadystatechange = function()
        {
            if (httploc.readyState == 4)
            {
                if(httploc.status == 200)
                {
                    var response = httploc.responseText;
                    document.getElementById("countryIdIndex").innerHTML = '';
                    document.getElementById("countryIdIndex").innerHTML = response;
                    setAssetDDL();
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
        var url = "../ajax/ddl/getasset.jsp";
        var httploc = getHTTPObject();
        var getstr = "clientId="+document.forms[0].clientIdIndex.value;
        getstr += "&countryId="+document.forms[0].countryIdIndex.value;
        httploc.open("POST", url, true);
        httploc.onreadystatechange = function()
        {
            if (httploc.readyState == 4)
            {
                if(httploc.status == 200)
                {
                    var response = httploc.responseText;
                    document.getElementById("assetIdIndex").innerHTML = '';
                    document.getElementById("assetIdIndex").innerHTML = response;
                }
            }
        };
        httploc.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
        httploc.setRequestHeader("Content-length", getstr.length);
        httploc.setRequestHeader("Connection", "close");
        httploc.send(getstr);
    }
    
    function modifyDayrate(dayrateId, dayrateId2, tp)
    {
        var url = "../ajax/talentpool/dayRateEdit.jsp";
        var httploc = getHTTPObject();
        var getstr = "";
        getstr = "dayrateId=" + dayrateId;
        getstr += "&dayrateId2=" + dayrateId2;
        getstr += "&type=" + tp;
        httploc.open("POST", url, true);
        httploc.onreadystatechange = function ()
        {
            if (httploc.readyState == 4)
            {
                if (httploc.status == 200)
                {
                    var response = httploc.responseText;
                    document.getElementById("edit_div").innerHTML = '';
                    document.getElementById("edit_div").innerHTML = response;
                }
            }
            if(tp == 1)
            {
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
        };
        httploc.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
        httploc.setRequestHeader("Content-length", getstr.length);
        httploc.setRequestHeader("Connection", "close");
        httploc.send(getstr);
    }
    
    function updateDayRate()
    {
        if(checkUpdateDayRate())
        {
            document.getElementById('submitdiv').innerHTML = "<img src='../assets/images/loading.gif' align='absmiddle' />";
            document.talentpoolForm.doSaveDayrate.value = "yes";
            document.talentpoolForm.action = "../talentpool/TalentpoolAction.do";
            document.talentpoolForm.submit();
        }
    }
    
    function checkUpdateDayRate()
    {
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
        if (Number(document.getElementById("dayrate1").value) <= 0)
        {
            Swal.fire({
                title: "Please enter day rate.",
                didClose: () => {
                    document.getElementById("dayrate1").focus();
                }
            })
            return false;
        }
        return true;
    }