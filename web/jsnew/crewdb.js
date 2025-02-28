function resetFilter()
{
    document.forms[0].search.value = "";
    searchFormAjax('s','-1');
}

function showDetail(id)
{
    document.crewdbForm.doView.value="yes";
    document.crewdbForm.crewdbId.value=id;
    document.crewdbForm.action="../crewdb/CrewdbAction.do";
    document.crewdbForm.submit();
}

function view(type)
{
    document.crewdbForm.doView.value="yes";
    document.crewdbForm.crewdbId.value=type;
    document.crewdbForm.action="../crewdb/CrewdbAction.do";
    document.crewdbForm.submit();
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
        var url = "../ajax/crewdb/getinfo.jsp";
        var httploc = getHTTPObject();
        var getstr = "";
        var next_value = escape(document.crewdbForm.nextValue.value);
        var search_value = escape(document.crewdbForm.search.value);
        getstr += "nextValue="+next_value;
        getstr += "&next="+v;
        getstr += "&search="+search_value;
        getstr += "&doDirect="+v1;
        getstr += "&statusIndex="+document.crewdbForm.statusIndex.value;
        getstr += "&positionIndex="+document.crewdbForm.positionIndex.value;
        getstr += "&clientIdIndex="+document.crewdbForm.clientIdIndex.value;
        getstr += "&countryIdIndex="+document.crewdbForm.countryIdIndex.value;
        getstr += "&assetIdIndex="+document.crewdbForm.assetIdIndex.value;
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
    if(document.crewdbForm.doView)
        document.crewdbForm.doView.value="no";
    if(document.crewdbForm.doCancel)
        document.crewdbForm.doCancel.value="yes";  
    if(document.crewdbForm.doSave)
        document.crewdbForm.doSave.value="no";
    if(document.crewdbForm.doSaveAsset)
        document.crewdbForm.doSaveAsset.value="no";
    if(document.crewdbForm.doDeleteAsset)
        document.crewdbForm.doDeleteAsset.value="no";
    if(document.crewdbForm.doModifyAsset)
        document.crewdbForm.doModifyAsset.value="no";
    document.crewdbForm.action="../crewdb/CrewdbAction.do";
    document.crewdbForm.submit();
}

function sortForm(colid, updown)
{
    for(i = 1; i <= 6; i++)
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
    var url_sort = "../ajax/crewdb/sort.jsp";
    var getstr = "";
    var nextValue = 0;
    if(document.crewdbForm.nextValue)
        nextValue = document.crewdbForm.nextValue.value;
    getstr += "nextValue="+nextValue;
    getstr += "&col="+colid;
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

function resetForm()
{
    document.crewdbForm.reset();
}

function exporttoexcel()
{    
    document.crewdbForm.action = "../crewdb/CrewdbExportAction.do";
    document.crewdbForm.submit();
}

function setIframe(uval)
{
    var url_v = "", classname = "";
    if (uval.includes(".doc") || uval.includes(".docx") || uval.includes("wordprocessingml.document"))
    {
        url_v = "https://docs.google.com/gview?url=" + uval + "&embedded=true";
        classname = "doc_mode";
    }
    else if (uval.includes(".pdf"))
    {
        url_v = uval+"#toolbar=0&page=1&view=fitH,100";
        classname = "pdf_mode";
    }
    else
    {
        url_v = uval;
        classname = "img_mode";
    }
    window.top.$('#iframe').attr('src', 'about:blank');
    setTimeout(function () {
        window.top.$('#iframe').attr('src', url_v);
         document.getElementById("iframe").className=classname;
         document.getElementById("diframe").href =uval;
    }, 1000);
}

function setIframeresume(uval)
{
    var url_v = "", classname = "";
    if (uval.includes(".doc") || uval.includes(".docx") || uval.includes("wordprocessingml.document"))
    {
        url_v = "https://docs.google.com/gview?url=" + uval + "&embedded=true";
        classname = "doc_mode";
    }
    else if (uval.includes(".pdf"))
    {
        url_v = uval+"#toolbar=0&page=1&view=fitH,100";
        classname = "pdf_mode";
    }
    else
    {
        url_v = uval;
        classname = "img_mode";
    }
    window.top.$('#iframe').attr('src', 'about:blank');
    setTimeout(function () {
        window.top.$('#iframe').attr('src', url_v);
         document.getElementById("iframe").className=classname;
    }, 1000);
}

function viewimg(candidateId, fn)
{
    var url = "../ajax/candidate/getimg.jsp";
    var httploc = getHTTPObject();
    var getstr = "candidateId=" + candidateId;
    getstr += "&type=1";
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
            }
        }
    };
    httploc.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    httploc.setRequestHeader("Content-length", getstr.length);
    httploc.setRequestHeader("Connection", "close");
    httploc.send(getstr);
    document.getElementById('viewfilesdiv').innerHTML = "<div><img src='../assets/images/loading.gif' align='absmiddle'/>Please wait. Loading...</div>";
}

function setAssetDDL()
{            
    var url = "../ajax/crewdb/getasset.jsp";
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
    
function setCountryDDL()
{            
    var url = "../ajax/crewdb/getcountry.jsp";
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
    