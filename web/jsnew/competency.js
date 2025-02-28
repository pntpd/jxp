function exporttoexcel()
{
    document.competencyForm.action = "../competency/CompetencyExportAction.do";
    document.competencyForm.submit();
}

function exporttoexcel2()
{
    document.competencyForm.action = "../competency/CompetencyExportDetails.do";
    document.competencyForm.submit();
}

function setAssetDDL()
{
    var url = "../ajax/competency/getassetDDL.jsp";
    var httploc = getHTTPObject();
    var getstr = "clientIdIndex=" + document.competencyForm.clientIdIndex.value;
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
    httploc.setRequestHeader("Content-length", getstr.length);
    httploc.setRequestHeader("Connection", "close");
    httploc.send(getstr);
}

function getCompetency()
{
    document.competencyForm.target = "_self";    
    document.competencyForm.doSearch.value = "yes";
    document.competencyForm.action = "../competency/CompetencyAction.do";
    document.competencyForm.submit();    
}

function showDetail(id)
{
    document.competencyForm.target = "_self";    
    document.competencyForm.searchDetails.value = "yes";
    document.competencyForm.expType.value = id;
    document.competencyForm.action = "../competency/CompetencyAction.do";
    document.competencyForm.submit();
}

function getDetail(id)
{
    document.competencyForm.target = "_self";    
    document.competencyForm.searchGraph.value = "yes";
    document.competencyForm.expType.value = id;
    document.competencyForm.action = "../competency/CompetencyAction.do";
    document.competencyForm.submit();
}

function searchPosition()
{
    document.competencyForm.target = "_self";    
    document.competencyForm.searchGraph.value = "no";
    document.competencyForm.doSearchPosition.value = "yes";
    document.competencyForm.action = "../competency/CompetencyAction.do";
    document.competencyForm.submit();
}

function setVal(id)
{
    document.competencyForm.competencyId.value = id;
    if(id == 1)
    {
        var ids = $("input[name='deptcb']:checked").map(function () 
        {
            return this.value;
        }).get().join(',');
        document.competencyForm.departmentcb.value = ids;
    }
    else if(id == 2)
    {
        var ids = $("input[name='pcb']:checked").map(function () 
        {
            return this.value;
        }).get().join(',');
        document.competencyForm.positioncb.value = ids;
    }
    else if(id == 3)
    {
        var ids = $("input[name='crewcb']:checked").map(function () 
        {
            return this.value;
        }).get().join(',');
        document.competencyForm.crewrotationcb.value = ids;
        
    }
    else if(id == 4)
    {
        var ids = $("input[name='rolecb']:checked").map(function () 
        {
            return this.value;
        }).get().join(',');
        document.competencyForm.comprolecb.value = ids;
    }
}

function clear(id)
{
    if(id == 1)
    {
        if(document.forms[0].deptcb)
        {
            if(document.forms[0].deptcb.length)
            {
                for(var i = 0; i < document.forms[0].deptcb.length; i++)
                {
                    document.forms[0].deptcb[i].checked = false;
                }
            }
            else
            {
                document.forms[0].deptcb.checked = false;
            }
        }
        if(document.forms[0].pcb)
        {
            if(document.forms[0].pcb.length)
            {
                for(var i = 0; i < document.forms[0].pcb.length; i++)
                {
                    document.forms[0].pcb[i].checked = false;
                }
            }
            else
            {
                document.forms[0].pcb.checked = false;
            }
        }  
        if(document.forms[0].crewcb)
        {
            if(document.forms[0].crewcb.length)
            {
                for(var i = 0; i < document.forms[0].crewcb.length; i++)
                {
                    document.forms[0].crewcb[i].checked = false;
                }
            }
            else
            {
                document.forms[0].crewcb.checked = false;
            }
        }  
        if(document.forms[0].rolecb)
        {
            if(document.forms[0].rolecb.length)
            {
                for(var i = 0; i < document.forms[0].rolecb.length; i++)
                {
                    document.forms[0].rolecb[i].checked = false;
                }
            }
            else
            {
                document.forms[0].rolecb.checked = false;
            }
        }
        document.forms[0].positioncb.value = "";
        document.forms[0].comprolecb.value = "";
        document.forms[0].departmentcb.value = "";
        document.forms[0].crewrotationcb.value = "";
        getCompetency();
    }
    else if(id == 2)
    {
        document.competencyForm.positionId.value = -1;
        document.competencyForm.search.value = "";
        searchPosition();
    }    
}

function setList(type)
{
    document.competencyForm.type.value = type;
    var url = "../ajax/competency/getdetails.jsp";
    var httploc = getHTTPObject();
    var getstr = "assetIdIndex=" + document.competencyForm.assetIdIndex.value;
    if(type ==1)
    {
        getstr += "&searchDept=" + document.competencyForm.searchDept.value;  
    }
    if(type == 2)
    {
        getstr += "&searchPosition=" + document.competencyForm.searchPosition.value;
    }
    if(type == 3)
    {
        getstr += "&searchName=" + document.competencyForm.searchName.value;
    }
    if(type == 4)
    {
        getstr += "&searchRole=" + document.competencyForm.searchRole.value;
    }
    httploc.open("POST", url, true);
    httploc.onreadystatechange = function ()
    {
        if (httploc.readyState == 4)
        {
            if (httploc.status == 200)
            {
                var response = httploc.responseText;
                if(type ==1)
                {
                    document.getElementById("sDept").innerHTML = '';
                    document.getElementById("sDept").innerHTML = response;
                }
                if(type ==2)
                {
                    document.getElementById("sPosition").innerHTML = '';
                    document.getElementById("sPosition").innerHTML = response;
                }
                if(type ==3)
                {
                    document.getElementById("sPersonnel").innerHTML = '';
                    document.getElementById("sPersonnel").innerHTML = response;
                }
                if(type ==4)
                {
                    document.getElementById("sRole").innerHTML = '';
                    document.getElementById("sRole").innerHTML = response;
                }
            }
        }
    };
    httploc.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    httploc.send(getstr);
}

function handleKeySearch(e)
{
    var key=e.keyCode || e.which;
    if (key===13)
    {
        e.preventDefault();
    }
}    

function getTrackerDetails(trackerId)
{
    var url = "../ajax/competency/trackermodal.jsp";
    var httploc = getHTTPObject();
    var getstr = "trackerId=" + trackerId;
    httploc.open("POST", url, true);
    httploc.onreadystatechange = function ()
    {
        if (httploc.readyState == 4)
        {
            if (httploc.status == 200)
            {
                var response = httploc.responseText;
                document.getElementById("personalmodal").innerHTML = '';
                document.getElementById("personalmodal").innerHTML = response;                
            }
        }
    };
    httploc.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    httploc.send(getstr);
}

function setIframe(url)
{
    $('#view_pdf').modal('show');
    var url_v = "", classname = "";
    if (url.includes(".pdf"))
    {
        url_v = url+"#toolbar=0&page=1&view=fitH,100";
        classname = "pdf_mode";
    } 
    window.top.$('#iframe').attr('src', 'about:blank');
    setTimeout(function () {
        window.top.$('#iframe').attr('src', url_v);
        document.getElementById("iframe").className = classname;
        document.getElementById("diframe").href = url;
    }, 1000);

    $("#iframe").on("load", function () {
        let head = $("#iframe").contents().find("head");
        let css = '<style>img{margin: 0px auto; max-width:-webkit-fill-available;}</style>';
        $(head).append(css);
    });
}

function goback()
{
    if(document.competencyForm.searchGraph)
        document.competencyForm.searchGraph.value="no";
    if(document.competencyForm.doSearchPosition)
        document.competencyForm.doSearchPosition.value="no";
    if(document.competencyForm.doCancel)
        document.competencyForm.doCancel.value="yes";  
    document.competencyForm.action="../competency/CompetencyAction.do";
    document.competencyForm.submit();
}

function getquestion(trackerId)
{
    var url = "../ajax/tracker/getquestion.jsp";
    var httploc = getHTTPObject();
    var getstr = "trackerId=" + trackerId;
    httploc.open("POST", url, true);
    httploc.onreadystatechange = function ()
    {
        if (httploc.readyState == 4)
        {
            if (httploc.status == 200)
            {
                var response = httploc.responseText;
                document.getElementById("questiondiv").innerHTML = '';
                document.getElementById("questiondiv").innerHTML = response;
            }
        }
    };
    httploc.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    httploc.send(getstr);
}

function gototracker()
{
    document.forms[0].action="../tracker/TrackerAction.do?doSearchAsset=yes";
    document.forms[0].submit();
}