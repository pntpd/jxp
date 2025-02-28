    function resetFilter()
    {
        document.forms[0].search.value = "";
        searchFormAjax('s','-1');
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
        var url = "../ajax/access/getinfo.jsp";
        var httploc = getHTTPObject()
        var getstr = "";
        var next_value = escape(document.accessForm.nextValue.value);
        var search_value = escape(document.accessForm.search.value);
        getstr += "nextValue="+next_value;
        getstr += "&next="+v;
        getstr += "&search="+search_value;
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
    }

    function deleteForm(id)
    {
        var agree=confirm("This will delete the record. \n Do you wish to continue?");
        if (agree)
        {
            var url = "../ajax/access/getinfo.jsp";
            var getstr = "";
            var httploc = getHTTPObject()
            var next_value = escape(document.accessForm.nextValue.value);
            var next_del = "-1";
            if(document.accessForm.nextDel)
                next_del = escape(document.accessForm.nextDel.value);
            var search_value = escape(document.accessForm.search.value);
            getstr += "nextValue="+next_value;
            getstr += "&nextDel="+next_del;
            getstr += "&search="+search_value;
            getstr += "&deleteVal="+id;
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

function handleKeySearch(e)
{
    var key=e.keyCode || e.which;
    if (key===13)
    {
        e.preventDefault();
        searchFormAjax('s','-1');
    }
}   

function exporttoexcel()
{    
    document.accessForm.action = "../access/AccessExportAction.do";
    document.accessForm.submit();
}