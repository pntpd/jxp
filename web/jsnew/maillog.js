function resetFilter()
{
    document.forms[0].search.value = "";
    document.forms[0].fromDate.value = "";
    document.forms[0].toDate.value = "";
    searchFormAjax('s','-1');
}
    
function checksearch()
{     
    if(document.maillogForm.fromDate.value != "" && document.maillogForm.toDate.value != "")
    {
        if(comparision(document.maillogForm.fromDate.value,document.maillogForm.toDate.value) == false)
        {
            Swal.fire({
            title: "Please check from date and to date.",
            didClose:() => {
            document.maillogForm.fromDate.focus();
            }
            }) 
            return false;
        }
    }
    return true;
}

function searchFormAjax(v, v1)
{
    if(checksearch())
    {
        var url = "../ajax/maillog/getinfo.jsp";
        var httploc = getHTTPObject()
        var getstr = "";
        var next_value = escape(document.maillogForm.nextValue.value);
        var search_value = escape(document.maillogForm.search.value);
        getstr += "nextValue="+next_value;
        getstr += "&next="+v;
        getstr += "&search="+search_value;
        var fromDate = document.maillogForm.fromDate.value;
        var toDate = document.maillogForm.toDate.value;
        getstr += "&fromDate="+fromDate;
        getstr += "&toDate="+toDate;
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

    
function exporttoexcel()
{    
    document.maillogForm.action = "../maillog/MaillogExportAction.do";
    document.maillogForm.submit();
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

function showDetail(id)
{
    document.maillogForm.doView.value="yes";
    document.maillogForm.maillogId.value=id;
    document.maillogForm.action="../maillog/MaillogAction.do";
    document.maillogForm.submit();
}

function goback()
{
    if(document.maillogForm.doView)
        document.maillogForm.doView.value="no";
    if(document.maillogForm.doCancel)
        document.maillogForm.doCancel.value="yes";    
    document.maillogForm.action="../maillog/MaillogAction.do";
    document.maillogForm.submit();
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