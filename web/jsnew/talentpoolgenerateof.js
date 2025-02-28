function resetofForm()
{
    document.forms[0].reset();
    document.forms[0].clientId.value = "-1";
    document.forms[0].clientassetId.value = "-1";
    document.forms[0].formalityId.value = "-1";
    setformalitytemplate();
}

function viewback()
{
    document.talentpoolForm.doView.value = "yes";
    document.talentpoolForm.action = "../talentpool/TalentpoolAction.do";
    document.talentpoolForm.submit();
}

function setformalitytemplate()
{
    var url = "../ajax/talentpool/getformalitytemplate.jsp";
    var httploc = getHTTPObject();
    var clientId = document.forms[0].clientId.value;
    var getstr = "clientId=" + clientId;
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
                v1 = arr[0];
                v2 = arr[1];
                document.getElementById("formalityId").innerHTML = '';
                document.getElementById("formalityId").innerHTML = v1;
                document.getElementById("clientassetId").innerHTML = '';
                document.getElementById("clientassetId").innerHTML = v2;
                clearvalue();
            }
        }
    };
    httploc.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    httploc.setRequestHeader("Content-length", getstr.length);
    httploc.setRequestHeader("Connection", "close");
    httploc.send(getstr);
}

function getGenerateOF(candidateId)
{
    document.forms[0].candidateId.value = candidateId;
    if (document.forms[0].doGenerateOF)
        document.forms[0].doGenerateOF.value = "yes";
    document.forms[0].target = "";
    document.forms[0].action = "../talentpool/TalentpoolAction.do";
    document.forms[0].submit();
}

function chechpdf()
{
    if (document.forms[0].clientId.value == "-1")
    {
        Swal.fire({
            title: "Please select client.",
            didClose: () => {
                document.forms[0].clientId.focus();
            }
        })
        return false;
    }
    if (document.forms[0].clientassetId.value == "-1")
    {
        Swal.fire({
            title: "Please select asset.",
            didClose: () => {
                document.forms[0].clientassetId.focus();
            }
        })
        return false;
    }
    if (Number(document.forms[0].formalityId.value) <=  0)
    {
        Swal.fire({
            title: "Please select template.",
            didClose: () => {
                document.forms[0].formalityId.focus();
            }
        })
        return false;
    }
    return true;
}

function generatepdf()
{    
    if (chechpdf()) 
    {
        var url = "../ajax/talentpool/getformalitypdffile.jsp";
        var httploc = getHTTPObject();
        var getstr = "";        
        var clientassetId = escape(document.forms[0].clientassetId.value);
        var clientId = escape(document.forms[0].clientId.value);
        var formalityId = escape(document.forms[0].formalityId.value);
        var candidateId = escape(document.forms[0].candidateId.value);
        var template = document.getElementById("formalityId").options[document.getElementById("formalityId").selectedIndex].text;
        getstr += "clientassetId=" + clientassetId;
        getstr += "&clientId=" + clientId;
        getstr += "&formalityId=" + formalityId;
        getstr += "&candidateId=" + candidateId;
        getstr += "&cval1=" + document.forms[0].cval1.value;
        getstr += "&cval2=" + document.forms[0].cval2.value;
        getstr += "&cval3=" + document.forms[0].cval3.value;
        getstr += "&cval4=" + document.forms[0].cval4.value;
        getstr += "&cval5=" + document.forms[0].cval5.value;
        getstr += "&cval6=" + document.forms[0].cval6.value;
        getstr += "&cval7=" + document.forms[0].cval7.value;
        getstr += "&cval8=" + document.forms[0].cval8.value;
        getstr += "&cval9=" + document.forms[0].cval9.value;
        getstr += "&cval10=" + document.forms[0].cval10.value;
        getstr += "&template=" + template;
        getstr += "&hiddenFile=" + document.forms[0].hiddenFile.value;
        httploc.open("POST", url, true);
        httploc.onreadystatechange = function ()
        {
            if (httploc.readyState == 4)
            {
                if (httploc.status == 200)
                {
                    var response = httploc.responseText;    
                    setIframe(response);
                    getformalities();
                    var arr = new Array();
                    arr = response.split('#@#');
                    v1 = arr[0];                    
                    v2 = arr[1];
                    v3 = arr[2];
                    v4 = arr[3];
                    v5 = arr[4];
                    v6 = arr[5];
                    v7 = arr[6];
                    v8 = arr[7];
                    v9 = arr[8];
                    v10 = arr[9];
                    v11 = arr[10];
                    v12 = arr[11];                    
                    v13 = arr[12];    
                    v14 = arr[13];    
                    v15 = arr[14];    
                    v16 = arr[15];   
                    getformalitieslist(candidateId);
                    
                    document.forms[0].cval1.value = v2;
                    document.forms[0].cval2.value = v3;
                    document.forms[0].cval3.value = v4;
                    document.forms[0].cval4.value = v5;
                    document.forms[0].cval5.value = v6;
                    document.forms[0].cval6.value = v7;
                    document.forms[0].cval7.value = v8;
                    document.forms[0].cval8.value = v9;
                    document.forms[0].cval9.value = v10;
                    document.forms[0].cval10.value = v11;
                    document.forms[0].hiddenFile.value = v12;
                    document.forms[0].onboardingId.value = v13;
                    document.forms[0].clientId.value = v14;
                    document.forms[0].clientassetId.value = v15;
                    document.forms[0].formalityId.value = v16;
                    document.getElementById("generatepdfdiv").style.display ="";
                    document.getElementById("downloadzipId2").style.display = "";
                }
            }
        };
        httploc.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
        httploc.setRequestHeader("Content-length", getstr.length);
        httploc.setRequestHeader("Connection", "close");
        httploc.send(getstr);        
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


function getformalitieslist(candidateId)
{
    var url = "../ajax/talentpool/getformalitieslist.jsp";
    var httploc = getHTTPObject();
    var getstr = "";
    getstr +="candidateId="+candidateId;
    httploc.open("POST", url, true);
    httploc.onreadystatechange = function ()
    {
        if (httploc.readyState == 4)
        {
            if (httploc.status == 200)
            {
                var response = httploc.responseText;
                arr = response.split('#@#');
                v1 = arr[0];
                v2 = arr[1];
                v3 = arr[2];
                document.forms[0].generatedlistsize.value = v2;
                onboardingId = v3;
                document.getElementById('ajax_cat').innerHTML = '';
                document.getElementById('ajax_cat').innerHTML = v1;
                if (Number(v2) <= 0)
                {           
                    document.getElementById('downloadzipId2').style.display = "none";
                }else
                {
                    document.getElementById('downloadzipId2').style.display = "";
                }
            }
        }
    };
    httploc.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    httploc.setRequestHeader("Content-length", getstr.length);
    httploc.setRequestHeader("Connection", "close");
    httploc.send(getstr);
}

function delfromlist(onboardingfileId)
{
    var candidateId = document.forms[0].candidateId.value;
    var url = "../ajax/talentpool/deletelist.jsp";
    var httploc = getHTTPObject();
    var getstr = "";
    getstr += "onboardingfileId=" + onboardingfileId;
    httploc.open("POST", url, true);
    httploc.onreadystatechange = function ()
    {
        if (httploc.readyState == 4)
        {
            if (httploc.status == 200)
            {
                var response = httploc.responseText;
                getformalitieslist(candidateId);
                getformalities();
                clearvalue();
            }
        }
    };
    httploc.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    httploc.setRequestHeader("Content-length", getstr.length);
    httploc.setRequestHeader("Connection", "close");
    httploc.send(getstr);
}

function getformalities()
{
    var url = "../ajax/talentpool/getformalities.jsp";
    var httploc = getHTTPObject();
    var getstr = "";
    var clientId = escape(document.forms[0].clientId.value);
    getstr += "clientId=" + clientId;
    httploc.open("POST", url, true);
    httploc.onreadystatechange = function ()
    {
        if (httploc.readyState == 4)
        {
            if (httploc.status == 200)
            {
                var response = httploc.responseText;
                document.getElementById('formalityId').innerHTML = '';
                document.getElementById('formalityId').innerHTML = response;
            }
        }
    };
    httploc.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    httploc.setRequestHeader("Content-length", getstr.length);
    httploc.setRequestHeader("Connection", "close");
    httploc.send(getstr);
}

function showformality(pdfile)
{
    setIframe(pdfile);
}

function clearvalue()
{
    document.forms[0].cval1.value = "";
    document.forms[0].cval2.value = "";
    document.forms[0].cval3.value = "";
    document.forms[0].cval4.value = "";
    document.forms[0].cval5.value = "";
    document.forms[0].cval6.value = "";
    document.forms[0].cval7.value = "";
    document.forms[0].cval8.value = "";
    document.forms[0].cval9.value = "";
    document.forms[0].cval10.value = "";
}

function setformalitytemplatenew(cid, caid, fid)
{
    var url = "../ajax/talentpool/getformalitytemplate.jsp";
    var httploc = getHTTPObject();
    var getstr = "clientId=" + cid;
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
                v1 = arr[0];
                v2 = arr[1];
                document.getElementById("formalityId").innerHTML = '';
                document.getElementById("formalityId").innerHTML = v1;
                document.getElementById("clientassetId").innerHTML = '';
                document.getElementById("clientassetId").innerHTML = v2;
                document.forms[0].clientassetId.value = trim(v15);
                document.forms[0].formalityId.value = trim(fid);
            }
        }
    };
    httploc.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    httploc.setRequestHeader("Content-length", getstr.length);
    httploc.setRequestHeader("Connection", "close");
    httploc.send(getstr);
}

function getCustomValue(id)
{
    var url = "../ajax/talentpool/getformalitydata.jsp";
    var httploc = getHTTPObject();
    var getstr = "onboardingfileId="+id;   
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
                v1 = arr[0];                    
                v2 = arr[1];
                v3 = arr[2];
                v4 = arr[3];
                v5 = arr[4];
                v6 = arr[5];
                v7 = arr[6];
                v8 = arr[7];
                v9 = arr[8];
                v10 = arr[9];
                v11 = arr[10];
                v12 = arr[11];                    
                v13 = arr[12];    
                v14 = arr[13];    
                v15 = arr[14];    
                v16 = arr[15];
                document.forms[0].clientId.value = trim(v14);
                
                setformalitytemplatenew(trim(v14), trim(v15), trim(v16));
                
                document.forms[0].cval1.value = v2;
                document.forms[0].cval2.value = v3;
                document.forms[0].cval3.value = v4;
                document.forms[0].cval4.value = v5;
                document.forms[0].cval5.value = v6;
                document.forms[0].cval6.value = v7;
                document.forms[0].cval7.value = v8;
                document.forms[0].cval8.value = v9;
                document.forms[0].cval9.value = v10;
                document.forms[0].cval10.value = v11;
                document.forms[0].hiddenFile.value = v12;                
                document.forms[0].clientassetId.value = trim(v15);
                document.forms[0].formalityId.value = trim(v16);  
                document.getElementById("generatepdfdiv").style.display ="";
                document.getElementById("downloadzipId2").style.display = "";
            }
        }
    };
    httploc.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    httploc.setRequestHeader("Content-length", getstr.length);
    httploc.setRequestHeader("Connection", "close");
    httploc.send(getstr);
}