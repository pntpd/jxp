function resetFilter()
{
    document.forms[0].search.value = "";
    document.crewinsuranceForm.positionIdIndex.value = "-1";
    document.crewinsuranceForm.statusIndex.value = "";
    searchFormAjax('s','-1');
}

function handleKeySearch(e)
{
    var key=e.keyCode || e.which;
    if (key===13)
    {
        e.preventDefault();
        searchFormAjax('s','-1');
    }
}    

function checkSearch()
{
    if(trim(document.forms[0].search.value) != "")
    {
        if(validdesc(document.forms[0].search) == false)
        {
            document.forms[0].search.focus();
            return false;
        }
    }
    return true;
}

function searchFormAjax(v, v1)
{
    if ( checkSearch())
    {
        var url = "../ajax/crewinsurance/getinfo.jsp";
        var httploc = getHTTPObject();
        var getstr = "";
        var search_value = escape(document.crewinsuranceForm.search.value);
        var positionIdIndex = document.crewinsuranceForm.positionIdIndex.value;
        var clientIdIndex = document.crewinsuranceForm.clientIdIndex.value;
        var assetIdIndex = document.crewinsuranceForm.assetIdIndex.value;
        var statusIndex = document.crewinsuranceForm.statusIndex.value;
        getstr += "clientIdIndex="+clientIdIndex;
        getstr += "&statusIndex="+statusIndex;
        getstr += "&assetIdIndex="+assetIdIndex;
        getstr += "&positionIdIndex="+positionIdIndex;
        getstr += "&search="+search_value;
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
}

function goback()
{
    if(document.crewinsuranceForm.doView)
        document.crewinsuranceForm.doView.value="no";
    if(document.crewinsuranceForm.doCancel)
        document.crewinsuranceForm.doCancel.value="yes";  
    if(document.crewinsuranceForm.doSave)
        document.crewinsuranceForm.doSave.value="no";
    document.crewinsuranceForm.action="../crewinsurance/CrewinsuranceAction.do";
    document.crewinsuranceForm.submit();
}

function sortForm(colid, updown)
{
    for(i = 1; i <= 3; i++)
    {
        document.getElementById("img_"+i+"_2").className = "sort_arrow deactive_sort";
        document.getElementById("img_"+i+"_1").className = "sort_arrow deactive_sort";
    }
    if(updown == 2)
    {
        document.getElementById("img_"+colid+"_2").className = "sort_arrow active_sort";

    }
    else
    {
        document.getElementById("img_"+colid+"_"+updown).className = "sort_arrow active_sort";
    }
    var httploc = getHTTPObject();
    var url_sort = "../ajax/crewinsurance/sort.jsp";
    var getstr = "";
    getstr += "col="+colid;
    getstr += "&updown="+updown;
    httploc.open("POST", url_sort, true);
    httploc.onreadystatechange = function()
    {
        if (httploc.readyState == 4)
        {
            if(httploc.status == 200)
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

function exporttoexcel()
{    
    document.crewinsuranceForm.action = "../crewinsurance/CrewinsuranceExportAction.do";
    document.crewinsuranceForm.submit();
}

function setAssetDDL()
{
    var url = "../ajax/crewinsurance/getasset.jsp";
    document.getElementById("assetIdIndex").value = '-1';
    var httploc = getHTTPObject();
    var getstr = "clientIdIndex=" + document.crewinsuranceForm.clientIdIndex.value + "&from=asset";
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

function submitForm()
{
    if(document.forms[0].getElementsByTagName("input"))
    {
        var inputElements = document.forms[0].getElementsByTagName("input");
        for(i = 0; i < inputElements.length; i++)
        {
            if(inputElements[i].type == "text")
            {
                inputElements[i].value = trim(inputElements[i].value);
            }
        }
    }
    if (checkCrewinsurance())
    {
        document.getElementById('submitdiv').innerHTML = "<img src='../assets/images/loading.gif' align='absmiddle' />";
        document.crewinsuranceForm.doSave.value="yes";
        document.crewinsuranceForm.action="../crewinsurance/CrewinsuranceAction.do";
        document.crewinsuranceForm.submit();
    }
}

function checkCrewinsurance()
{
    if (document.crewinsuranceForm.fromDate.value == "")
    {
        Swal.fire({
            title: "Please select from date.",
            didClose: () => {
                document.crewinsuranceForm.fromDate.focus();
            }
        })
        return false;
    }
    if (document.crewinsuranceForm.toDate.value == "")
    {
        Swal.fire({
            title: "Please select to date.",
            didClose: () => {
                document.crewinsuranceForm.toDate.focus();
            }
        })
        return false;
    }
    if (comparisionTest(document.crewinsuranceForm.fromDate.value, document.crewinsuranceForm.toDate.value) == false)
    {
        Swal.fire({
            title: "Please check to date.",
            didClose: () => {
                document.crewinsuranceForm.toDate.value = "";
            }
        })
        return false;
    }    
    if (document.crewinsuranceForm.certificatefile.value != "")
    {
        if (!(document.crewinsuranceForm.certificatefile.value).match(/(\.(png)|(jpg)|(jpeg)|(pdf))$/i))
        {
            Swal.fire({
                title: "Only .jpg, .jpeg, .png, .pdf are allowed.",
                didClose: () => {
                    document.crewinsuranceForm.certificatefile.focus();
                }
            })
            return false;
        }
        var input = document.crewinsuranceForm.certificatefile;
        if (input.files)
        {
            var file = input.files[0];
            if (file.size > 1024 * 1024 * 5)
            {
                Swal.fire({
                    title: "File size should not exceed 5 MB.",
                    didClose: () => {
                        document.crewinsuranceForm.certificatefile.focus();
                    }
                })
                return false;
            }
        }
    }
    /*if(document.forms[0].cb1)
    {
        var cc = 0;
        if (document.forms[0].cb1.length) 
        {
            for (i = 0; i < document.forms[0].cb1.length; i++) 
            {
                if(document.forms[0].cb1[i].checked)
                    cc++;
            }
        }
        else 
        {
            if(document.forms[0].cb1.checked)
                cc++;
        }
        if(Number(cc) == 0)
        {
            Swal.fire({
            title: "Please select atleast one nominee.",
            didClose: () => {
                document.crewinsuranceForm.cb1.focus();
            }
        })
        return false;
        }
    }*/
    return true;
}

function setClass(tp)
{
    document.getElementById("upload_link_" + tp).className = "attache_btn uploaded_img";
}

function addForm()
{    
    document.crewinsuranceForm.doAdd.value="yes";
    document.crewinsuranceForm.action="../crewinsurance/CrewinsuranceAction.do";
    document.crewinsuranceForm.submit();
}

function searchFormAsset()
{
    document.crewinsuranceForm.search.value = "";
    document.crewinsuranceForm.positionIdIndex.value = "-1";
    document.crewinsuranceForm.statusIndex.value = "-1";
    document.crewinsuranceForm.doSearch.value = "yes";
    document.crewinsuranceForm.action = "../crewinsurance/CrewinsuranceAction.do";
    document.crewinsuranceForm.submit();
}

function setval()
{
    if (eval(document.crewinsuranceForm.dependent.value) <= 0 && eval(document.crewinsuranceForm.crewinsuranceId.value) <= 0)
        document.crewinsuranceForm.dependent.value = "";                
}

function getcertdetails(crewrotationId,crewinsuranceId, type)
{
    document.crewinsuranceForm.crewrotationId.value= crewrotationId;
    document.crewinsuranceForm.crewinsuranceId.value = crewinsuranceId;    
    $('#upload_insurance_certificate').modal('show');
    var url = "../ajax/crewinsurance/getcertdetails.jsp";
    var httploc = getHTTPObject();
    var getstr = "";
    getstr += "crewrotationId=" + crewrotationId;
    getstr += "&crewinsuranceId=" + crewinsuranceId;
    getstr += "&type=" + type;
    httploc.open("POST", url, true);
    httploc.onreadystatechange = function ()
    {
        if (httploc.readyState == 4)
        {
            if (httploc.status == 200)
            {
                var response = httploc.responseText;
                document.getElementById('modaldata').innerHTML = '';
                document.getElementById('modaldata').innerHTML = response;
                $("#upload_link_1").on('click', function (e) {
                    e.preventDefault();
                    $("#upload1:hidden").trigger('click');
                });
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
    httploc.setRequestHeader("Content-length", getstr.length);
    httploc.setRequestHeader("Connection", "close");
    httploc.send(getstr);
    document.getElementById('modaldata').innerHTML = "<div><img src='../assets/images/loading.gif' align='absmiddle'/>Please wait. Loading...</div>";
}

    function checkAll() 
    {
        var cc = 0;
        if (document.forms[0].cball.checked) 
        {
            if (document.forms[0].cb1.length) 
            {
                for (i = 0; i < document.forms[0].cb1.length; i++) 
                {
                    document.forms[0].cb1[i].checked = true;
                    cc++;
                }
            }
            else 
            {
                document.forms[0].cb1.checked = true;
                cc++;
            }
        }
        else 
        {
            if (document.forms[0].cb1.length) 
            {
                for (i = 0; i < document.forms[0].cb1.length; i++) 
                {
                    document.forms[0].cb1[i].checked = false;
                    cc--;
                }
            }
            else 
            {
                document.forms[0].cb1.checked = false;
                cc--;
            }
        }
        if(cc >0 )
            document.forms[0].dependent.value = cc;
        else
            document.forms[0].dependent.value = 0;
    }

    function checkcb() 
    {
         var cc = 0;
        if (document.forms[0].cb1.length) 
        {
            for (i = 0; i < document.forms[0].cb1.length; i++) 
            {
                if(document.forms[0].cb1[i].checked)
                cc++;
            }
        }
        else 
        {
            if(document.forms[0].cb1.checked)
            cc++;
        }
        if(cc >0 )
            document.forms[0].dependent.value = cc;
        else
            document.forms[0].dependent.value = 0;
    }