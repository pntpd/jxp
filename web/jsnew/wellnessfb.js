function resetFilter()
{
    document.forms[0].search.value = "";
    searchFormAjax('s','-1');
}

function showDetail(id)
{
    document.wellnessfbForm.doView.value="yes";
    document.wellnessfbForm.doModify.value="no";
    document.wellnessfbForm.doAdd.value="no";
    document.wellnessfbForm.categoryId.value=id;
    document.wellnessfbForm.action="../wellnessfb/WellnessfbAction.do";
    document.wellnessfbForm.submit();
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
        var url = "../ajax/wellnessfb/getinfo.jsp";
        var httploc = getHTTPObject();
        var getstr = "";
        var next_value = escape(document.wellnessfbForm.nextValue.value);
        var search_value = escape(document.wellnessfbForm.search.value);
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

function goback()
{
    if(document.wellnessfbForm.doView)
        document.wellnessfbForm.doView.value="no";
    if(document.wellnessfbForm.doCancel)
        document.wellnessfbForm.doCancel.value="yes";  
    if(document.wellnessfbForm.doSave)
        document.wellnessfbForm.doSave.value="no";
    document.wellnessfbForm.action="../wellnessfb/WellnessfbAction.do";
    document.wellnessfbForm.submit();
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
    var url_sort = "../ajax/wellnessfb/sort.jsp";
    var getstr = "";
    var nextValue = 0;
    if(document.wellnessfbForm.nextValue)
        nextValue = document.wellnessfbForm.nextValue.value;
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
    document.wellnessfbForm.doModify.value="no";
    document.wellnessfbForm.doView.value="no";
    document.wellnessfbForm.doAdd.value="yes";
    document.wellnessfbForm.action="../wellnessfb/WellnessfbAction.do";
    document.wellnessfbForm.submit();
}

function modifyForm(id)
{
    document.wellnessfbForm.doModify.value="yes";
    document.wellnessfbForm.doView.value="no";
    document.wellnessfbForm.doAdd.value="no";
    document.wellnessfbForm.categoryId.value=id;
    document.wellnessfbForm.action="../wellnessfb/WellnessfbAction.do";
    document.wellnessfbForm.submit();
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
    if (checkcategory())
    {
        document.getElementById('submitdiv').innerHTML = "<img src='../assets/images/loading.gif' align='absmiddle' />";
        document.wellnessfbForm.doSave.value="yes";
        document.wellnessfbForm.doCancel.value="no";
        document.wellnessfbForm.action="../wellnessfb/WellnessfbAction.do";
        document.wellnessfbForm.submit();
    }
}

function checkcategory()
{
    if (trim(document.wellnessfbForm.name.value) == "")
    {
            Swal.fire({
        title: "Please enter category name.",
        didClose:() => {
          document.wellnessfbForm.name.focus();
        }
        }) 
        return false;
    }
    if (validdesc(document.wellnessfbForm.name) == false)
    {
        return false;
    }
    return true;
}

function checksubcategory()
{
    if (trim(document.wellnessfbForm.name.value) == "")
    {
            Swal.fire({
        title: "Please enter subcategory name.",
        didClose:() => {
          document.wellnessfbForm.name.focus();
        }
        }) 
        return false;
    }
    if (validdesc(document.wellnessfbForm.name) == false)
    {
        return false;
    }
    if (document.wellnessfbForm.schedulecb.checked  == true)
    {
        if (document.wellnessfbForm.repeatdp.value == "-1")
        {
         Swal.fire({
        title: "Please select schedule.",
        didClose:() => {
          document.wellnessfbForm.repeatdp.focus();
        }
        })
        return false;
        }
        if (document.wellnessfbForm.notificationdp.value == "-1" && document.wellnessfbForm.repeatdp.value != "1")
        {
         Swal.fire({
        title: "Please select notification schedule.",
        didClose:() => {
          document.wellnessfbForm.notificationdp.focus();
        }
        })
        return false;
        }
        if (document.wellnessfbForm.notificationdp.value == "" && document.wellnessfbForm.repeatdp.value > "1")
        {
         Swal.fire({
        title: "Please select notification schedule.",
        didClose:() => {
          document.wellnessfbForm.notificationdp.focus();
        }
        })
        return false;
        }
        if (document.wellnessfbForm.schedulevalue.value == "" && document.wellnessfbForm.repeatdp.value == "2")
        {
         Swal.fire({
        title: "Please select date.",
        didClose:() => {
          document.wellnessfbForm.notificationdp.focus();
        }
        })
        return false;
        }
        if (document.wellnessfbForm.schedulevalue.value == "" && document.wellnessfbForm.repeatdp.value == "3")
        {
         Swal.fire({
        title: "Please select date.",
        didClose:() => {
          document.wellnessfbForm.notificationdp.focus();
        }
        })
        return false;
        }
    }
    else
    {
        document.wellnessfbForm.repeatdp.value = "";
        document.wellnessfbForm.notificationdp.value = "";
    }
    return true;
}

function resetForm()
{
    document.wellnessfbForm.reset();
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
        var url = "../ajax/wellnessfb/getinfo.jsp";
        var getstr = "";
        var httploc = getHTTPObject();
        var next_value = escape(document.wellnessfbForm.nextValue.value);
        var next_del = "-1";
        if(document.wellnessfbForm.nextDel)
            next_del = escape(document.wellnessfbForm.nextDel.value);
        var search_value = escape(document.wellnessfbForm.search.value);
        getstr += "nextValue="+next_value;
        getstr += "&nextDel="+next_del;
        getstr += "&search="+search_value;
        getstr += "&status="+status;
        getstr += "&deleteVal="+userId;
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
    document.wellnessfbForm.action = "../wellnessfb/WellnessfbExportAction.do";
    document.wellnessfbForm.submit();
}

function viewSubcategory(categoryId)
{    
    if(categoryId > 0)
    {
        document.wellnessfbForm.categoryId.value=categoryId;
    }
    document.wellnessfbForm.doIndexSubcategory.value="yes";
    document.wellnessfbForm.action="../wellnessfb/WellnessfbAction.do";
    document.wellnessfbForm.submit();
}

function addsubcategoryForm(categoryId)
{   
    if(categoryId > 0)
    {
        document.wellnessfbForm.categoryId.value=categoryId;
    }
    document.wellnessfbForm.doIndexSubcategory.value="no";
    document.wellnessfbForm.doAddSubcategory.value="yes";
    document.wellnessfbForm.action="../wellnessfb/WellnessfbAction.do";
    document.wellnessfbForm.submit();
}

function modifySubcategoryForm(id)
{    
    document.wellnessfbForm.subcategoryId.value=id;
    document.wellnessfbForm.doIndexSubcategory.value="no";
    document.wellnessfbForm.doAddSubcategory.value="no";
    document.wellnessfbForm.doModifysubcategory.value="yes";
    document.wellnessfbForm.action="../wellnessfb/WellnessfbAction.do";
    document.wellnessfbForm.submit();
}

function submitsubcategoryForm()
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
    if (checksubcategory())
    {
        document.getElementById('submitdiv').innerHTML = "<img src='../assets/images/loading.gif' align='absmiddle' />";
        document.wellnessfbForm.doSaveSubcategory.value="yes";
        document.wellnessfbForm.doCancel.value="no";
        document.wellnessfbForm.action="../wellnessfb/WellnessfbAction.do";
        document.wellnessfbForm.submit();
    }
}

function showDetailcategory(id)
{
    document.wellnessfbForm.doView.value="yes";
    document.wellnessfbForm.action="../wellnessfb/WellnessfbAction.do";
    document.wellnessfbForm.submit();
}

function viewQuestion(id)
{    
    if(id <= 0)
    {
        id = document.wellnessfbForm.subcategoryIdIndex.value
    }
    document.wellnessfbForm.doIndexQuestion.value="yes";
    document.wellnessfbForm.doIndexSubcategory.value="no";
    document.wellnessfbForm.subcategoryIdIndex.value=id;
    document.wellnessfbForm.doView.value="no";
    document.wellnessfbForm.action="../wellnessfb/WellnessfbAction.do";
    document.wellnessfbForm.submit();
}

function viewQuestioncat(id)
{    
    if(id > 0)
    {
        document.wellnessfbForm.categoryId.value=id
    }
    document.wellnessfbForm.doIndexQuestion.value="yes";
    document.wellnessfbForm.doIndexSubcategory.value="no";
    document.wellnessfbForm.doView.value="no";
    document.wellnessfbForm.action="../wellnessfb/WellnessfbAction.do";
    document.wellnessfbForm.submit();
}

function viewQuestioncatsub(categoryId,subcategoryId)
{
    document.wellnessfbForm.categoryId.value=categoryId;
    document.wellnessfbForm.subcategoryIdIndex.value=subcategoryId;
    document.wellnessfbForm.doIndexQuestion.value="yes";
    document.wellnessfbForm.doIndexSubcategory.value="no";
    document.wellnessfbForm.doView.value="no";
    document.wellnessfbForm.action="../wellnessfb/WellnessfbAction.do";
    document.wellnessfbForm.submit();
}

function addquestionForm()
{    
    document.wellnessfbForm.doIndexQuestion.value="no";
    document.wellnessfbForm.doAddQuestion.value="yes";
    document.wellnessfbForm.action="../wellnessfb/WellnessfbAction.do";
    document.wellnessfbForm.submit();
}

function addquestionsubForm(subcategoryId)
{    
    if(subcategoryId > 0)
    {
        document.wellnessfbForm.esubcategoryId.value=subcategoryId;
    }
    document.wellnessfbForm.doIndexQuestion.value="no";
    document.wellnessfbForm.doIndexSubcategory.value="no";
    document.wellnessfbForm.doAddQuestion.value="yes";
    document.wellnessfbForm.action="../wellnessfb/WellnessfbAction.do";
    document.wellnessfbForm.submit();
}

function modifyquestionForm(questionId,subcategoryId)
{    
    document.wellnessfbForm.questionId.value = questionId;
    document.wellnessfbForm.subcategoryId.value = subcategoryId;
    document.wellnessfbForm.doIndexQuestion.value="no";
    document.wellnessfbForm.doAddQuestion.value="no";
    document.wellnessfbForm.doModifyquestion.value="yes";
    document.wellnessfbForm.action="../wellnessfb/WellnessfbAction.do";
    document.wellnessfbForm.submit();
}

function viewimg(questionId)
{
    var url = "../ajax/wellnessfb/getimg.jsp";
    var httploc = getHTTPObject();
    var getstr = "questionId=" + questionId;
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

                $("#iframe").on("load", function () {
                    let head = $("#iframe").contents().find("head");
                    let css = '<style>img{margin: 0px auto; max-width:-webkit-fill-available;}</style>';
                    $(head).append(css);
                });

            }
        }
    };
    httploc.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    httploc.setRequestHeader("Content-length", getstr.length);
    httploc.setRequestHeader("Connection", "close");
    httploc.send(getstr);
    document.getElementById('viewfilesdiv').innerHTML = "<div><img src='../assets/images/loading.gif' align='absmiddle'/>Please wait. Loading...</div>";
}

function delpic(questionattachmentId, questionId)
{
    var s = "<span>File will be <b>deleted.<b></span>";
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
            var url = "../ajax/wellnessfb/delimg.jsp";
            var httploc = getHTTPObject();
            var getstr = "questionattachmentId=" + questionattachmentId;
            httploc.open("POST", url, true);
            httploc.onreadystatechange = function ()
            {
                if (httploc.readyState == 4)
                {
                    if (httploc.status == 200)
                    {
                        var response = trim(httploc.responseText);
                        if (response == "Yes")
                        {
                            viewimg(questionId);
                        }
                    }
                }
            };
            httploc.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
            httploc.setRequestHeader("Content-length", getstr.length);
            httploc.setRequestHeader("Connection", "close");
            httploc.send(getstr);
            document.getElementById('viewfilesdiv').innerHTML = "<div><img src='../assets/images/loading.gif' align='absmiddle'/>Please wait. Loading...</div>";
        }
    })
}

function submitquestionForm()
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
    if (checkquestion())
    {
        document.getElementById('submitdiv').innerHTML = "<img src='../assets/images/loading.gif' align='absmiddle' />";
        document.wellnessfbForm.doSaveQuestion.value="yes";
        document.wellnessfbForm.doIndexQuestion.value="no";
        document.wellnessfbForm.doCancel.value="no";
        document.wellnessfbForm.action="../wellnessfb/WellnessfbAction.do";
        document.wellnessfbForm.submit();
    }
}

function checkquestion()
{
     if (document.wellnessfbForm.esubcategoryId.value <= "0")
    {
            Swal.fire({
        title: "Please select subcategory.",
        didClose:() => {
          document.wellnessfbForm.esubcategoryId.focus();
        }
        }) 
        return false;
    }
     if (document.wellnessfbForm.question.value == "")
    {
            Swal.fire({
        title: "Please enter question.",
        didClose:() => {
          document.wellnessfbForm.question.focus();
        }
        }) 
        return false;
    }
    if (document.wellnessfbForm.answertypeId.value <= "0")
    {
            Swal.fire({
        title: "Please select answertype.",
        didClose:() => {
          document.wellnessfbForm.answertypeId.focus();
        }
        }) 
        return false;
    }
    if (document.wellnessfbForm.answertypeId.value == "2")
    {
        if (document.wellnessfbForm.addvalue.value == "")
        {
            Swal.fire({
                title: "Please enter addvalue.",
                didClose: () => {
                    document.wellnessfbForm.addvalue.focus();
                }
            })
            return false;
        }
    }
    if (document.wellnessfbForm.addvalue.value != "")
    {
        if (validnameonlycomma(document.wellnessfbForm.addvalue) == false)
        {
            Swal.fire({
                title: "Only , allowed.",
                didClose: () => {
                    document.wellnessfbForm.addvalue.focus();
                }
            })
            return false;
        }
    }
    if (document.wellnessfbForm.assettypemultiselect_dd.value == "")
    {
        Swal.fire({
            title: "Please select atleast one assettype.",
            didClose: () => {
                document.wellnessfbForm.assettypemultiselect_dd.focus();
            }
        })
        return false;
    }
    
    if (document.wellnessfbForm.respondermultiselect_dd.value == "")
    {
        Swal.fire({
            title: "Please select atleast one responder.",
            didClose: () => {
                document.wellnessfbForm.respondermultiselect_dd.focus();
            }
        })
        return false;
    }
    if (document.wellnessfbForm.recipientId.value <= "0")
    {
        Swal.fire({
        title: "Please select recipient.",
        didClose:() => {
          document.wellnessfbForm.recipientId.focus();
        }
        }) 
        return false;
    }    
    return true;
}

function deletesubcategoryForm(subcategoryId, status, id)
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
        document.wellnessfbForm.dodeletesubcategory.value="yes";
        document.wellnessfbForm.doIndexSubcategory.value="no";
        document.wellnessfbForm.subcategoryId.value=subcategoryId;
        document.wellnessfbForm.substatus.value=status;
        document.wellnessfbForm.action="../wellnessfb/WellnessfbAction.do";
        document.wellnessfbForm.submit();
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

function deletequestionForm(questionId, status, id)
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
        document.wellnessfbForm.dodeleteQuestion.value="yes";
        document.wellnessfbForm.doIndexQuestion.value="no";
        document.wellnessfbForm.questionId.value=questionId;
        document.wellnessfbForm.substatus.value=status;
        document.wellnessfbForm.action="../wellnessfb/WellnessfbAction.do";
        document.wellnessfbForm.submit();
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

function showcategory()
{
    document.wellnessfbForm.doView.value="no";
    document.wellnessfbForm.doIndexSubcategory.value="no";
    document.wellnessfbForm.action="../wellnessfb/WellnessfbAction.do";
    document.wellnessfbForm.submit();
}

function setIframe(uval)
{
    var url_v = "", classname = "";
    if (uval.includes(".doc") || uval.includes(".docx"))
    {
        url_v = "https://docs.google.com/gview?url=" + uval + "&embedded=true";
        classname = "doc_mode";
    } 
    else if(uval.includes(".ppt") || uval.includes(".pptx") || uval.includes(".presentation"))
    {
        url_v = "https://view.officeapps.live.com/op/embed.aspx?src=" + uval + "&embedded=true";
        classname = "doc_mode";
    }
    else if (uval.includes(".pdf"))
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
    }, 1000);
}

function setaddvaluedisplay()
{
    if(document.getElementById('answertypeId').value == "2")
    {
        document.getElementById('addvalueId').style.display = "";
    }
    else
    {
        document.getElementById('addvalueId').style.display = "none";
    }
}

function showhideschedule()
{
    document.forms[0].schedulevalue.value = "";   
    
    if (document.wellnessfbForm.schedulecb.checked)
    {
        document.getElementById('ShowMeDIV').style.display = "";
        if(document.wellnessfbForm.repeatdp.value == "1")
        {
            document.getElementById('all_scheduledetails').style.display = "";
            document.getElementById('all_notificationdetails').style.display = "none";
            document.getElementById('weekdaysId').style.display = "none";
            document.getElementById('yeardaysId').style.display = "none";
            document.getElementById('monthdaysId').style.display = "none";
        }
        else if(document.wellnessfbForm.repeatdp.value == "2")
        {
            document.getElementById('all_scheduledetails').style.display = "";
            document.getElementById('weekdaysId').style.display = "block";
            document.getElementById('all_notificationdetails').style.display = "block";
            document.getElementById('monthdaysId').style.display = "none";
            document.getElementById('yeardaysId').style.display = "none";
            setweekdays(0);
        }
        else if(document.wellnessfbForm.repeatdp.value == "3")
        {
            document.getElementById('all_scheduledetails').style.display = "block";
            document.getElementById('monthdaysId').style.display = "block";
            document.getElementById('weekdaysId').style.display = "none";
            document.getElementById('yeardaysId').style.display = "none";
            document.getElementById('all_notificationdetails').style.display = "block";
            setmonthdays(0);
        }
        else if(document.wellnessfbForm.repeatdp.value == "4")
        {
            document.getElementById('all_scheduledetails').style.display = "";
            document.getElementById('yeardaysId').style.display = "block";
            document.getElementById('monthdaysId').style.display = "none";
            document.getElementById('weekdaysId').style.display = "none";
            document.getElementById('all_notificationdetails').style.display = "block";
        }
        else
        {
            document.getElementById('weekdaysId').style.display = "none";
            document.getElementById('all_scheduledetails').style.display = "";
            document.getElementById('all_notificationdetails').style.display = "none";
            document.getElementById('monthdaysId').style.display = "none";
            document.getElementById('yeardaysId').style.display = "none";
        }
    }
    else
    {
        document.getElementById('ShowMeDIV').style.display = "none";
        document.getElementById('all_scheduledetails').style.display = "none";
        document.getElementById('all_notificationdetails').style.display = "none";
        document.getElementById('weekdaysId').style.display = "none";
        document.getElementById('yeardaysId').style.display = "none";
        document.getElementById('monthdaysId').style.display = "none";
    }
}

function checkWeek(mvalue)
{
    var week = "";
    week = document.forms[0].schedulevalue.value;
    var weeks = new Array();
    weeks = week.split(",");
    if(!weeks.includes(mvalue))
    {
        addWeek(mvalue);
        setweekdays(mvalue);
    }
    else
    {
        deleteWeek(mvalue);
        setweekdays(document.forms[0].schedulevalue.value);
    }
}

function addWeek(mvalue)
{
    if (document.forms[0].schedulevalue)
    {
        if (document.forms[0].schedulevalue.value == "")
        {
            document.forms[0].schedulevalue.value = mvalue;
        } else
        {
            document.forms[0].schedulevalue.value += ","+mvalue;
        }
    }
}

function deleteWeek(mvalue)
{
    var week = "";
    week = document.forms[0].schedulevalue.value;
    var weeks = new Array();
    weeks = week.split(",");
    var wvalue = "";
    for (let i = 0; i < weeks.length; i++) 
    {
      if(mvalue != weeks[i])
      {
        if (wvalue == "")
        {
            wvalue = weeks[i];
        } else
        {
            wvalue += ","+weeks[i];
        }
      }
    }
    document.forms[0].schedulevalue.value = wvalue;
}

function checkmonth(mvalue)
{
    var month = "";
    month = document.forms[0].schedulevalue.value;
    var months = new Array();
    months = month.split(",");
    var astatus = 2;
    for (let i = 0; i < months.length; i++) 
    {
      if(mvalue == months[i])
      {
          astatus = 1;
          break;
      }
    }
    
    if(astatus == 2)
    {
        addMonth(mvalue);
        setmonthdays(mvalue);
    }
    else if(astatus == 1)
    {
        deleteMonth(mvalue);
        setmonthdays(document.forms[0].schedulevalue.value);
    }
}

function addMonth(mvalue)
{
    if (document.forms[0].schedulevalue)
    {
        if (document.forms[0].schedulevalue.value == "")
        {
            document.forms[0].schedulevalue.value = mvalue;
        } else
        {
            document.forms[0].schedulevalue.value += ","+mvalue;
        }
    }
}

function deleteMonth(mvalue)
{
    var month =  document.forms[0].schedulevalue.value;
    var months = new Array();
    months = month.split(",");
    var wvalue = "";
    for (let i = 0; i < months.length; i++) 
    {
      if(mvalue != months[i])
      {
        if (wvalue == "")
        {
            wvalue = months[i];
        } else
        {
            wvalue += ","+months[i];
        }
      }
    }
    document.forms[0].schedulevalue.value = wvalue;
}

function setweekdays(weekId)
{
    var url = "../ajax/wellnessfb/getweekdays.jsp";
    var httploc = getHTTPObject();
    var getstr = "schedulevalue=" + document.forms[0].schedulevalue.value;
    httploc.open("POST", url, true);
    httploc.onreadystatechange = function ()
    {
        if (httploc.readyState == 4)
        {
            if (httploc.status == 200)
            {
                var response = httploc.responseText;
                document.getElementById('weekviewId').innerHTML = '';
                document.getElementById('weekviewId').innerHTML = response;
            }
        }
    };
    httploc.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    httploc.setRequestHeader("Content-length", getstr.length);
    httploc.setRequestHeader("Connection", "close");
    httploc.send(getstr);
    document.getElementById('weekviewId').innerHTML = "<div><img src='../assets/images/loading.gif' align='absmiddle'/>Please wait. Loading...</div>";
}

function setmonthdays(monthId)
{
    var url = "../ajax/wellnessfb/getmonthdays.jsp";
    var httploc = getHTTPObject();
    var getstr = "schedulevalue=" + document.forms[0].schedulevalue.value;
    httploc.open("POST", url, true);
    httploc.onreadystatechange = function ()
    {
        if (httploc.readyState == 4)
        {
            if (httploc.status == 200)
            {
                var response = httploc.responseText;
                document.getElementById('monthdaysId').innerHTML = '';
                document.getElementById('monthdaysId').innerHTML = response;
            }
        }
    };
    httploc.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    httploc.setRequestHeader("Content-length", getstr.length);
    httploc.setRequestHeader("Connection", "close");
    httploc.send(getstr);
    document.getElementById('monthdaysId').innerHTML = "<div><img src='../assets/images/loading.gif' align='absmiddle'/>Please wait. Loading...</div>";
}

function setyeardays(dayvalue,add)
{
    var monthval = document.forms[0].monthdp.value;
    if(dayvalue == 0 && add == 0)
    {
        monthval = "1";
    }
    var url = "../ajax/wellnessfb/addtolist.jsp";
    var httploc = getHTTPObject();
    var getstr = "month=" + monthval;
    getstr += "&day=" + dayvalue;
    getstr += "&action=" + add;
    httploc.open("POST", url, true);
    httploc.onreadystatechange = function ()
    {
        if (httploc.readyState == 4)
        {
            if (httploc.status == 200)
            {
                var response = httploc.responseText;
                document.getElementById('yearviewId').innerHTML = '';
                document.getElementById('yearviewId').innerHTML = response;
            }
        }
    };
    httploc.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    httploc.setRequestHeader("Content-length", getstr.length);
    httploc.setRequestHeader("Connection", "close");
    httploc.send(getstr);
    document.getElementById('yearviewId').innerHTML = "<div><img src='../assets/images/loading.gif' align='absmiddle'/>Please wait. Loading...</div>";
}
    
function deleteyeardays(dayvalue,monthvalue,del)
{
    var url = "../ajax/wellnessfb/addtolist.jsp";
    var httploc = getHTTPObject();
    var getstr = "month=" + monthvalue;
    getstr += "&day=" + dayvalue;
    getstr += "&action=" +del;
    httploc.open("POST", url, true);
    httploc.onreadystatechange = function ()
    {
        if (httploc.readyState == 4)
        {
            if (httploc.status == 200)
            {
                var response = httploc.responseText;
                document.getElementById('yearviewId').innerHTML = '';
                document.getElementById('yearviewId').innerHTML = response;
            }
        }
    };
    httploc.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    httploc.setRequestHeader("Content-length", getstr.length);
    httploc.setRequestHeader("Connection", "close");
    httploc.send(getstr);
    document.getElementById('yearviewId').innerHTML = "<div><img src='../assets/images/loading.gif' align='absmiddle'/>Please wait. Loading...</div>";
}

function setSCedit()
{
    var repeat = document.forms[0].repeatdp.value;
    if(Number(repeat) == 2)
    {
        setweekdays('0');
    }
    else if(Number(repeat) == 3)
    {
        setmonthdays('0');
    }
    else if(Number(repeat) == 4)
    {
        setyeardays('0','0');
    }
    showhidescheduleonload();
}

function showhidescheduleonload()
{
    if (document.wellnessfbForm.schedulecb.checked)
    {
        document.getElementById('ShowMeDIV').style.display = "";
        if(document.wellnessfbForm.repeatdp.value == "1")
        {
            document.getElementById('all_scheduledetails').style.display = "";
            document.getElementById('all_notificationdetails').style.display = "none";
            document.getElementById('weekdaysId').style.display = "none";
            document.getElementById('yeardaysId').style.display = "none";
            document.getElementById('monthdaysId').style.display = "none";
        }
        else if(document.wellnessfbForm.repeatdp.value == "2")
        {
            document.getElementById('all_scheduledetails').style.display = "";
            document.getElementById('weekdaysId').style.display = "block";
            document.getElementById('all_notificationdetails').style.display = "block";
            document.getElementById('monthdaysId').style.display = "none";
            document.getElementById('yeardaysId').style.display = "none";
        }
        else if(document.wellnessfbForm.repeatdp.value == "3")
        {
            document.getElementById('all_scheduledetails').style.display = "block";
            document.getElementById('monthdaysId').style.display = "block";
            document.getElementById('weekdaysId').style.display = "none";
            document.getElementById('yeardaysId').style.display = "none";
            document.getElementById('all_notificationdetails').style.display = "block";
        }
        else if(document.wellnessfbForm.repeatdp.value == "4")
        {
            document.getElementById('all_scheduledetails').style.display = "";
            document.getElementById('yeardaysId').style.display = "block";
            document.getElementById('monthdaysId').style.display = "none";
            document.getElementById('weekdaysId').style.display = "none";
            document.getElementById('all_notificationdetails').style.display = "block";
        }
        else
        {
            document.getElementById('weekdaysId').style.display = "none";
            document.getElementById('all_notificationdetails').style.display = "none";
            document.getElementById('all_scheduledetails').style.display = "";        
        }
    }
    else
    {
        document.getElementById('ShowMeDIV').style.display = "none";
        document.getElementById('all_scheduledetails').style.display = "none";
        document.getElementById('all_notificationdetails').style.display = "none";
        document.getElementById('weekdaysId').style.display = "none";
        document.getElementById('yeardaysId').style.display = "none";
        document.getElementById('monthdaysId').style.display = "none";
    }
}