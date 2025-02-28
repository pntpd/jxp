function gotoaction(candidateId)
{    
    var url = "/jxp/ajax/search/checkflag.jsp";
    var httploc = getHTTPObject();
    var getstr = "candidateId="+candidateId;
    httploc.open("POST", url, true);
    httploc.onreadystatechange = function()
    {
        if (httploc.readyState == 4)
        {
            if(httploc.status == 200)
            {
                var response = httploc.responseText;
                response = trim(response);  
                if(response == 'Yes')
                {
                    document.forms[0].doView.value = "yes";
                    document.forms[0].candidateId.value = candidateId;
                    document.forms[0].action = "/jxp/talentpool/TalentpoolAction.do";
                    document.forms[0].submit();
                }
                else
                {
                    document.forms[0].doView.value = "yes";
                    document.forms[0].candidateId.value = candidateId;
                    document.forms[0].action = "/jxp/candidate/CandidateAction.do";
                    document.forms[0].submit();
                }
            }
        }
    }
    httploc.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    httploc.setRequestHeader("Content-length", getstr.length);
    httploc.setRequestHeader("Connection", "close");
    httploc.send(getstr);
}