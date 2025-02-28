function resetFilter()
{
    document.forms[0].search.value = "";
    document.forms[0].assessmentparameterIndex.value = "-1";
    document.forms[0].questiontypeIndex.value = "-1";
    setquestionparameters();
    searchFormAjax('s','-1');
}

function showDetail(id)
{
    document.assessmentForm.doView.value="yes";
    document.assessmentForm.doModify.value="no";
    document.assessmentForm.doAdd.value="no";
    document.assessmentForm.assessmentId.value=id;
    document.assessmentForm.action="../assessment/AssessmentAction.do";
    document.assessmentForm.submit();
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

function searchFormAjax(v, v1)
{
    var url = "../ajax/assessment/getinfo.jsp";
    var httploc = getHTTPObject();
    var getstr = "";
    var next_value = escape(document.assessmentForm.nextValue.value);
    var search_value = escape(document.assessmentForm.search.value);
    var parameterId = escape(document.assessmentForm.assessmentparameterIndex.value);
    var questionId = escape(document.assessmentForm.questiontypeIndex.value);    
    getstr += "nextValue="+next_value;
    getstr += "&next="+v;
    getstr += "&search="+search_value;
    getstr += "&parameterId="+parameterId;
    getstr += "&questionId="+questionId;
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

function goback()
{
    if(document.assessmentForm.doView)
        document.assessmentForm.doView.value="no";
    if(document.assessmentForm.doCancel)
        document.assessmentForm.doCancel.value="yes";  
    if(document.assessmentForm.doSave)
        document.assessmentForm.doSave.value="no";
    document.assessmentForm.action="../assessment/AssessmentAction.do";
    document.assessmentForm.submit();
}

function sortForm(colid, updown)
{
    for(i = 1; i <= 4; i++)
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
    var url_sort = "../ajax/assessment/sort.jsp";
    var getstr = "";
    var nextValue = 0;
    if(document.assessmentForm.nextValue)
        nextValue = document.assessmentForm.nextValue.value;
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

function addForm()
{    
    document.assessmentForm.doModify.value="no";
    document.assessmentForm.doView.value="no";
    document.assessmentForm.doAdd.value="yes";
    document.assessmentForm.action="../assessment/AssessmentAction.do";
    document.assessmentForm.submit();
}

function modifyForm(id)
{
    document.assessmentForm.doModify.value="yes";
    document.assessmentForm.doView.value="no";
    document.assessmentForm.doAdd.value="no";
    document.assessmentForm.assessmentId.value=id;
    document.assessmentForm.action="../assessment/AssessmentAction.do";
    document.assessmentForm.submit();
}

function modifyFormview(id)
{
    document.assessmentForm.doModify.value="yes";
    document.assessmentForm.assessmentId.value=id;
    document.assessmentForm.action="../assessment/AssessmentAction.do";
    document.assessmentForm.submit();
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
    if (checkAssessment())
    {
        document.getElementById('submitdiv').innerHTML = "<img src='../assets/images/loading.gif' align='absmiddle' />";
        document.assessmentForm.doSave.value="yes";
        document.assessmentForm.doCancel.value="no";
        document.assessmentForm.action="../assessment/AssessmentAction.do";
        document.assessmentForm.submit();
    }
}

function checkAssessment()
{
    if (trim(document.assessmentForm.name.value) == "")
    {
     Swal.fire({
        title: "Please enter name.",
        didClose:() => {
          document.assessmentForm.name.focus();
        }
        }) 
        return false;
    }
    if (validname(document.assessmentForm.name) == false)
    {
        return false;
    }
    
    if (trim(document.assessmentForm.mode.value) == "")
    {
     Swal.fire({
        title: "Please enter mode.",
        didClose:() => {
          document.assessmentForm.mode.focus();
        }
        }) 
        return false;
    }
    if (validname(document.assessmentForm.mode) == false)
    {
        return false;
    }
    
    var ids = $('input[name="assparaameter"]:checked').map(function () {  
        return this.value;
        }).get().join(",");  
     if (ids == "")
    {
     Swal.fire({
        title: "Please select atleast 1 parameter.",
        didClose:() => {
          document.assessmentForm.assparaameter.focus();
        }
        }) 
        return false;
    }
  return true;
}

function resetForm()
{
    document.assessmentForm.reset();
}

function deleteForm(userId, status, id)
{
    var s = "";
    if(eval(status) == 1)
        s = "<span>The selected item will be <b>deactivated.</b></span>";
    else
        s = "<span>The selected item will be <b>activated.</b></span>";
    Swal.fire({
        title: s + 'Are you sure?',
        confirmButtonColor: '#3085d6',
        cancelButtonColor: '#d33',
        confirmButtonText: 'Confirm',
        showCloseButton: true,
        allowOutsideClick: false,
        allowEscapeKey: false
    }).then((result) => {
    if (result.isConfirmed) 
    {
        var url = "../ajax/assessment/getinfo.jsp";
        var getstr = "";
        var httploc = getHTTPObject();
        var next_value = escape(document.assessmentForm.nextValue.value);
        var next_del = "-1";
        if(document.assessmentForm.nextDel)
            next_del = escape(document.assessmentForm.nextDel.value);
        var search_value = escape(document.assessmentForm.search.value);
        var parameterId = escape(document.assessmentForm.assessmentparameterIndex.value);
        var questionId = escape(document.assessmentForm.questiontypeIndex.value);
        getstr += "nextValue="+next_value;
        getstr += "&nextDel="+next_del;
        getstr += "&search="+search_value;
        getstr += "&status="+status;
        getstr += "&deleteVal="+userId;
        getstr += "&parameterId="+parameterId;
        getstr += "&questionId="+questionId;
        httploc.open("POST", url, true);
        httploc.onreadystatechange = function()
        {
            if (httploc.readyState == 4)
            {
                if(httploc.status == 200)
                {
                    var response = httploc.responseText;
                    var arr = new Array();
                    arr = response.split('##');
                    var v1 = arr[0];
                    var v2 = trim(arr[1]);
                    document.getElementById('ajax_cat').innerHTML = '';
                    document.getElementById('ajax_cat').innerHTML = v1;
                    if(trim(v2) != "")
                    {
                        Swal.fire(v2)
                    }
                }
            }
        };
        httploc.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
        httploc.setRequestHeader("Content-length", getstr.length);
        httploc.setRequestHeader("Connection", "close");
        httploc.send(getstr);
    }
    else
    {
        if(document.getElementById("flexSwitchCheckDefault_"+id).checked == true)
            document.getElementById("flexSwitchCheckDefault_"+id).checked = false;
        else
            document.getElementById("flexSwitchCheckDefault_"+id).checked = true;
    }
    })
}

function exporttoexcel()
{    
    document.assessmentForm.action = "../assessment/AssessmentExportAction.do";
    document.assessmentForm.submit();
}

function setparameter()
{
    var url = "../ajax/assessment/setparameter.jsp";
    var httploc = getHTTPObject();
    var ids = $('input[name="assparaameter"]:checked').map(function () {  
        return this.value;
        }).get().join(",");
    var getstr = "ids="+ids;
    httploc.open("POST", url, true);
    httploc.onreadystatechange = function()
    {
        if (httploc.readyState == 4)
        {
            if(httploc.status == 200)
            {
                var response = trim(httploc.responseText);
                document.getElementById("parameterul").innerHTML = response;
            }
        }
    };
    httploc.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    httploc.setRequestHeader("Content-length", getstr.length);
    httploc.setRequestHeader("Connection", "close");
    httploc.send(getstr);
}

function checkaddparameter()
{
    if(document.forms[0].name_modal.value == "")
    {
        Swal.fire({
        title: "Please enter name.",
        didClose:() => {
         document.forms[0].name_modal.focus();
        }
        }) 
        return false;
    }
    if (validname(document.forms[0].name_modal) == false)
    {
        return false;
    }
    if(document.forms[0].description_modal.value != "")
    {
        if (validdesc(document.forms[0].description_modal) == false)
        {
            return false;
        }
    }
    return true;
}

function addparameter()
{
    if(checkaddparameter())
    {
        var url = "../ajax/assessment/addtomaster.jsp";
        var httploc = getHTTPObject();
        var getstr = "name="+escape(document.forms[0].name_modal.value);
        getstr += "&description="+escape(document.forms[0].description_modal.value);
        httploc.open("POST", url, true);
        httploc.onreadystatechange = function()
        {
            if (httploc.readyState == 4)
            {
                if(httploc.status == 200)
                {
                    var response = trim(httploc.responseText);
                    if(response == "Yes")
                        setparameter();
                    else
                        Swal.fire(response);                    
                    $('#par_add_modal').modal('hide');                    
                    document.forms[0].name_modal.value = "";
                    document.forms[0].description_modal.value = "";
                }
            }
        };
        httploc.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
        httploc.setRequestHeader("Content-length", getstr.length);
        httploc.setRequestHeader("Connection", "close");
        httploc.send(getstr);
    }
}
    
function setCheckedassessmentparameters()
{
    var url = "../ajax/assessment/setCheckedassessmentparameters.jsp";
    var httploc = getHTTPObject();
    var ids = $('input[name="assparaameter"]:checked').map(function () {  
        return this.value;
        }).get().join(",");
    var getstr = "ids="+ids;
    httploc.open("POST", url, true);
    httploc.onreadystatechange = function()
    {
        if (httploc.readyState == 4)
        {
            if(httploc.status == 200)
            {
                var response = trim(httploc.responseText);
                document.getElementById("checkedassessmentparameterId").innerHTML = response;
                document.getElementById("checkedassessmentparameterId_model").innerHTML = response;
            }
        }
    };
    httploc.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    httploc.setRequestHeader("Content-length", getstr.length);
    httploc.setRequestHeader("Connection", "close");
    httploc.send(getstr);
}
    
function setCheckedassessmentquestions()
{
    var url = "../ajax/assessment/setCheckedassessmentparameters.jsp";
    var httploc = getHTTPObject();
    var ids = $('input[name="assparaameter"]:checked').map(function () {  
        return this.value;
        }).get().join(",");
    var getstr = "ids="+ids;
    httploc.open("POST", url, true);
    httploc.onreadystatechange = function()
    {
        if (httploc.readyState == 4)
        {
            if(httploc.status == 200)
            {
                var response = trim(httploc.responseText);
                document.getElementById("checkedassessmentparameterId").innerHTML = response;
                document.getElementById("checkedassessmentparameterId_model").innerHTML = response;
            }
        }
    };
    httploc.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    httploc.setRequestHeader("Content-length", getstr.length);
    httploc.setRequestHeader("Connection", "close");
    httploc.send(getstr);
}
       
function setCheckedquestionparameters(type)
{
    var url = "../ajax/assessment/setCheckedquestionparameters.jsp";
    var httploc = getHTTPObject();
    var assessmentparameterid =  0;
    if(type == -1)
        assessmentparameterid = document.assessmentForm.checkedassessmentparameterId.value;
    else
        assessmentparameterid = type;
    var getstr = "assessmentparameterid="+assessmentparameterid;
    httploc.open("POST", url, true);
    httploc.onreadystatechange = function()
    {
        if (httploc.readyState == 4)
        {
            if(httploc.status == 200)
            {
                var response = trim(httploc.responseText);
                document.getElementById("checkedassessmentquestions").innerHTML = response;
            }
        }
    };
    httploc.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    httploc.setRequestHeader("Content-length", getstr.length);
    httploc.setRequestHeader("Connection", "close");
    httploc.send(getstr);
}
           
function checkaddquestions()
{
    if(document.forms[0].question_modal.value == "")
    {
            Swal.fire({
            title: "Please enter question text.",
            didClose:() => {
            document.forms[0].question_modal.focus();
            }
            }) 
            return false;
        }
    if (validdesc(document.forms[0].question_modal) == false)
    {
        return false;
    }
    if(document.forms[0].assessmentanswerId.value <= "0")
    {
            Swal.fire({
            title: "Please select answer type.",
            didClose:() => {
            document.forms[0].assessmentanswerId.focus();
            }
            }) 
            return false;
        }
    if(document.forms[0].checkedassessmentparameterId_model.value <= "0")
    {
            Swal.fire({
            title: "Please select parameter.",
            didClose:() => {
            document.forms[0].checkedassessmentparameterId_model.focus();
            }
            }) 
            return false;
        }
    if(document.forms[0].externallink_modal.value != "")
    {
        if (validdesc(document.forms[0].externallink_modal) == false)
        {
            return false;
        }
    }
    return true;
}

function addquestions()
{
    if(checkaddquestions())
    {
        var url = "../ajax/assessment/addquestiontomaster.jsp";
        var httploc = getHTTPObject();
        var getstr = "question="+escape(document.forms[0].question_modal.value);
        getstr += "&answerId="+escape(document.forms[0].assessmentanswerId.value);
        getstr += "&parameterId="+escape(document.forms[0].checkedassessmentparameterId_model.value);
        getstr += "&externallink="+escape(document.forms[0].externallink_modal.value);
        httploc.open("POST", url, true);
        httploc.onreadystatechange = function()
        {
            if (httploc.readyState == 4)
            {
                if(httploc.status == 200)
                {
                    var response = trim(httploc.responseText);
                    if(response == "Yes")
                    {
                        setCheckedquestionparameters(document.forms[0].checkedassessmentparameterId_model.value);        
                        document.forms[0].checkedassessmentparameterId.value = document.forms[0].checkedassessmentparameterId_model.value;
                    }
                    else
                        Swal.fire(response);                    
                    $('#par_que_modal').modal('hide');                    
                    document.forms[0].question_modal.value = "";
                    document.forms[0].externallink_modal.value = "";
                    document.forms[0].checkedassessmentparameterId_model.value = "";
                    document.forms[0].assessmentanswerId.value = "";
                }
            }
        };
        httploc.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
        httploc.setRequestHeader("Content-length", getstr.length);
        httploc.setRequestHeader("Connection", "close");
        httploc.send(getstr);
    }
}
    
function setqid(id)
{
    var url = "../ajax/assessment/setq.jsp";
    var httploc = getHTTPObject();
    var type = 0, qid = 0;
    if(document.getElementById(id).checked)
        type = 1;
    else
        type = 2;
    var qid = document.getElementById(id).value;
    var getstr = "type="+type;
    getstr += "&qid="+qid;
    httploc.open("POST", url, true);
    httploc.onreadystatechange = function()
    {
        if (httploc.readyState == 4)
        {
            if(httploc.status == 200)
            {
                var response = trim(httploc.responseText);
            }
        }
    };
    httploc.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    httploc.setRequestHeader("Content-length", getstr.length);
    httploc.setRequestHeader("Connection", "close");
    httploc.send(getstr);
}

function setcheckedquestionparametersView()
{
    var url = "../ajax/assessment/setCheckedquestionparametersView.jsp";
    var httploc = getHTTPObject();
    var  assessmentparameterid = document.assessmentForm.checkedassessmentparameterId.value;
    var assessmentid = document.assessmentForm.assessmentId.value;
    var getstr = "assessmentparameterid="+assessmentparameterid;
    getstr += "&assessmentid="+assessmentid;
    httploc.open("POST", url, true);
    httploc.onreadystatechange = function()
    {
        if (httploc.readyState == 4)
        {
            if(httploc.status == 200)
            {
                var response = trim(httploc.responseText);
                document.getElementById("checkedassessmentview").innerHTML = response;
            }
        }
    };
    httploc.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    httploc.setRequestHeader("Content-length", getstr.length);
    httploc.setRequestHeader("Connection", "close");
    httploc.send(getstr);
}
    
function setcheckedquestionparametersViewdetail(id)
{
    var url = "../ajax/assessment/setquestionviewdetail.jsp";
    var httploc = getHTTPObject();
    var getstr = "assessmentquestionid="+id;
    httploc.open("POST", url, true);
    httploc.onreadystatechange = function()
    {
        if (httploc.readyState == 4)
        {
            if(httploc.status == 200)
            {
                var response = trim(httploc.responseText);
                document.getElementById("questviewmodal").innerHTML = response;

            }
        }
    };
    httploc.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    httploc.setRequestHeader("Content-length", getstr.length);
    httploc.setRequestHeader("Connection", "close");
    httploc.send(getstr);
}
    
function setquestionparameters()
{
    var url = "../ajax/assessment/setassessmentquestiondropdown.jsp";
    var httploc = getHTTPObject();
    var id = document.assessmentForm.assessmentparameterIndex.value;
    var getstr = "assessmentparameterid="+id;
    httploc.open("POST", url, true);
    httploc.onreadystatechange = function()
    {
        if (httploc.readyState == 4)
        {
            if(httploc.status == 200)
            {
                var response = trim(httploc.responseText);
                document.getElementById("questiontypeIndex").innerHTML = response;

            }
        }
    };
    httploc.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    httploc.setRequestHeader("Content-length", getstr.length);
    httploc.setRequestHeader("Connection", "close");
    httploc.send(getstr);        
}
    