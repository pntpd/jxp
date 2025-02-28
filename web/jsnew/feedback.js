function resetFilter()
{
    document.forms[0].search.value = "";
    searchFormAjax('s', '-1');
}

function resetStatus()
{
    document.feedbackForm.status.value = "";
    searchFormAjax('s', '-1');
    $(document).on('click', '.toggle-title_02', function () {
        $(this).parent()
                .toggleClass('toggled-on_02')
                .toggleClass('toggled-off_02');
    });
}

function showDetail(id)
{
    document.feedbackForm.doView.value = "yes";
    document.feedbackForm.doModify.value = "no";
    document.feedbackForm.doAdd.value = "no";
    document.feedbackForm.feedbackId.value = id;
    document.feedbackForm.action = "/jxp/feedback/FeedbackAction.do";
    document.feedbackForm.submit();
}

function showProfile()
{
    document.feedbackForm.doViewProfile.value = "yes";
    document.feedbackForm.action = "/jxp/feedback/FeedbackAction.do";
    document.feedbackForm.submit();
}

function handleKeySearch(e)
{
    var key = e.keyCode || e.which;
    if (key === 13)
    {
        e.preventDefault();
        searchFormAjax('s', '-1');
    }
}

function searchFormAjax(v, v1)
{
    var url = "/jxp/ajax/feedback/getinfo.jsp";
    var httploc = getHTTPObject();
    var getstr = "";
    var next_value = escape(document.feedbackForm.nextValue.value);
    var search_value = escape(document.feedbackForm.search.value);
    var fromDate = "";
    var toDate = "";
    if (document.feedbackForm.fromDate.value != "") {
        fromDate = document.feedbackForm.fromDate.value;
    } else if (document.feedbackForm.fromDate1.value != "") {
        fromDate = document.feedbackForm.fromDate1.value;
    }
    if (document.feedbackForm.toDate.value != "") {
        toDate = document.feedbackForm.toDate.value;
    } else if (document.feedbackForm.toDate1.value != "") {
        toDate = document.feedbackForm.toDate1.value;
    }
    var status = document.feedbackForm.status.value;
    getstr += "nextValue=" + next_value;
    getstr += "&fromDate=" + fromDate;
    getstr += "&toDate=" + toDate;
    getstr += "&status=" + status;
    getstr += "&next=" + v;
    getstr += "&search=" + search_value;
    getstr += "&doDirect=" + v1;
    httploc.open("POST", url, true);
    httploc.onreadystatechange = function ()
    {
        if (httploc.readyState == 4)
        {
            if (httploc.status == 200)
            {
                var response = httploc.responseText;
                document.getElementById('ajax_cat').innerHTML = '';
                document.getElementById('ajax_cat').innerHTML = response;
                $(document).on('click', '.toggle-title_02', function () {
                    $(this).parent()
                            .toggleClass('toggled-on_02')
                            .toggleClass('toggled-off_02');
                });
            }
        }
    };
    httploc.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    httploc.setRequestHeader("Content-length", getstr.length);
    httploc.setRequestHeader("Connection", "close");
    httploc.send(getstr);
    document.getElementById('ajax_cat').innerHTML = "<div><img src='/jxp/assets/images/loading.gif' align='absmiddle'/>Please wait. Loading...</div>";
}

function handleKeySearchCompetency(e)
{
    var key = e.keyCode || e.which;
    if (key === 13)
    {
        e.preventDefault();
        searchFormAjaxCompetency('s', '-1');
    }
}

function searchFormAjaxCompetency(v, v1)
{
    var url = "/jxp/ajax/feedback/getinfo_competency.jsp";
    var httploc = getHTTPObject();
    var getstr = "";
    var next_value = escape(document.feedbackForm.nextValue.value);
    var search_value = escape(document.feedbackForm.compsearch.value);
    var status = document.feedbackForm.compstatus.value;
    getstr += "nextValue=" + next_value;
    getstr += "&status=" + status;
    getstr += "&next=" + v;
    getstr += "&search=" + search_value;
    getstr += "&doDirect=" + v1;
    httploc.open("POST", url, true);
    httploc.onreadystatechange = function ()
    {
        if (httploc.readyState == 4)
        {
            if (httploc.status == 200)
            {
                var response = httploc.responseText;
                document.getElementById('ajax_cat').innerHTML = '';
                document.getElementById('ajax_cat').innerHTML = response;
                $(document).on('click', '.toggle-title_02', function () {
                    $(this).parent()
                            .toggleClass('toggled-on_02')
                            .toggleClass('toggled-off_02');
                });
            }
        }
    };
    httploc.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    httploc.setRequestHeader("Content-length", getstr.length);
    httploc.setRequestHeader("Connection", "close");
    httploc.send(getstr);
    document.getElementById('ajax_cat').innerHTML = "<div><img src='/jxp/assets/images/loading.gif' align='absmiddle'/>Please wait. Loading...</div>";
}

function goback()
{
    if (document.feedbackForm.doView)
        document.feedbackForm.doView.value = "no";
    if (document.feedbackForm.doCancel)
        document.feedbackForm.doCancel.value = "yes";
    if (document.feedbackForm.doSave)
        document.feedbackForm.doSave.value = "no";
    if (document.feedbackForm.doGetFeedbackList)
        document.feedbackForm.doGetFeedbackList.value = "no";
    document.feedbackForm.action = "/jxp/feedback/FeedbackAction.do";
    document.feedbackForm.submit();
}

function checkFeedback()
{
    if (trim(document.feedbackForm.name.value) == "")
    {
        Swal.fire({
            title: "Please enter name.",
            didClose: () => {
                document.feedbackForm.name.focus();
            }
        })
        return false;
    }
    if (validname(document.feedbackForm.name) == false)
    {
        return false;
    }
    return true;
}

function resetForm()
{
    document.feedbackForm.reset();
}

function exporttoexcel()
{
    document.feedbackForm.action = "/jxp/feedback/FeedbackExportAction.do";
    document.feedbackForm.submit();
}

function getFeedbacklist()
{
    document.forms[0].target = "_self";
    document.feedbackForm.doGetFeedbackList.value = "yes";
    document.feedbackForm.action = "/jxp/feedback/FeedbackAction.do";
    document.feedbackForm.submit();
}

function getCompetencylist()
{
    document.forms[0].target = "_self";
    document.feedbackForm.doCompetencyList.value = "yes";
    document.feedbackForm.action = "/jxp/feedback/FeedbackAction.do";
    document.feedbackForm.submit();
}

function setVal(srno, tp)
{
    document.getElementById("answer_" + srno).value = document.getElementById("qradio_" + srno + "_" + tp).value;
}

function checkForm()
{
    if (document.forms[0].answer)
    {
        if (document.forms[0].answer.length)
        {
            var len = document.forms[0].answer.length;
            for (var i = 0; i < len; i++)
            {
                if (trim(document.forms[0].answer[i].value) == "")
                {
                    Swal.fire("Please provide feedback for all question.");
                    document.forms[0].answer[i].focus();
                    return false;
                }
            }
        } else
        {
            if (trim(document.forms[0].answer.value) == "")
            {
                Swal.fire("Please provide feedback for all question.");
                document.forms[0].answer.focus();
                return false;
            }
        }
    } else
    {
        Swal.fire("No question available, please contact to admin.");
        return false;
    }
    return true;
}

function submitForm()
{
    if (checkForm())
    {
        document.feedbackForm.doSave.value = "yes";
        document.feedbackForm.action = "/jxp/feedback/FeedbackAction.do";
        document.feedbackForm.submit();
    }
}

function setStatusearch(tp)
{
    document.feedbackForm.status.value = tp;
    searchFormAjax('s', '-1');
    $(document).on('click', '.toggle-title_02', function () {
        $(this).parent()
                .toggleClass('toggled-on_02')
                .toggleClass('toggled-off_02');
    });
}

function viewSurvey(id)
{
    document.feedbackForm.surveyId.value = id;
    document.feedbackForm.doView.value = "yes";
    document.forms[0].action = "/jxp/feedback/FeedbackAction.do";
    document.forms[0].submit();
}

function backtohome()
{
    document.forms[0].target = "";
    document.forms[0].action = "/jxp/feedback/feedback_welcome.jsp";
    document.forms[0].submit();
}

function viewimg(fn)
{
    var url = "/jxp/ajax/feedback/getimg.jsp";
    var httploc = getHTTPObject();
    var getstr = "fn=" + escape(fn);
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
    document.getElementById('viewfilesdiv').innerHTML = "<div><img src='/jxp/assets/images/loading.gif' align='absmiddle'/>Please wait. Loading...</div>";
}

function setIframeresume(uval)
{
    var url_v = "", classname = "";
    if (uval.includes(".doc") || uval.includes(".docx") || uval.includes("wordprocessingml.document"))
    {
        url_v = "https://docs.google.com/gview?url=" + uval + "&embedded=true";
        classname = "doc_mode";
    } else if (uval.includes(".pdf"))
    {
        url_v = uval + "#toolbar=0&page=1&view=fitH,100";
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

function openTab(tp)
{
    if (tp == 2)
    {
        document.feedbackForm.doCancel.value = "no";
        if (document.feedbackForm.doViewlangdetail)
            document.feedbackForm.doViewlangdetail.value = "yes";
        if (document.feedbackForm.doViewhealthdetail)
            document.feedbackForm.doViewhealthdetail.value = "no";
        if (document.feedbackForm.doViewBanklist)
            document.feedbackForm.doViewBanklist.value = "no";
        if (document.feedbackForm.doViewvaccinationlist)
            document.feedbackForm.doViewvaccinationlist.value = "no";
        if (document.feedbackForm.doViewgovdocumentlist)
            document.feedbackForm.doViewgovdocumentlist.value = "no";
        if (document.feedbackForm.doViewtrainingcertlist)
            document.feedbackForm.doViewtrainingcertlist.value = "no";
        if (document.feedbackForm.doVieweducationlist)
            document.feedbackForm.doVieweducationlist.value = "no";
        if (document.feedbackForm.doViewexperiencelist)
            document.feedbackForm.doViewexperiencelist.value = "no";
        document.feedbackForm.action = "/jxp/feedback/FeedbackAction.do";
        document.feedbackForm.submit();
    }
    if (tp == 4)
    {
        document.feedbackForm.doCancel.value = "no";
        if (document.feedbackForm.doViewhealthdetail)
            document.feedbackForm.doViewhealthdetail.value = "yes";
        if (document.feedbackForm.doViewBanklist)
            document.feedbackForm.doViewBanklist.value = "no";
        if (document.feedbackForm.doViewvaccinationlist)
            document.feedbackForm.doViewvaccinationlist.value = "no";
        if (document.feedbackForm.doViewgovdocumentlist)
            document.feedbackForm.doViewgovdocumentlist.value = "no";
        if (document.feedbackForm.doViewtrainingcertlist)
            document.feedbackForm.doViewtrainingcertlist.value = "no";
        if (document.feedbackForm.doVieweducationlist)
            document.feedbackForm.doVieweducationlist.value = "no";
        if (document.feedbackForm.doViewexperiencelist)
            document.feedbackForm.doViewexperiencelist.value = "no";
        document.feedbackForm.action = "/jxp/feedback/FeedbackAction.do";
        document.feedbackForm.submit();
    }
    if (tp == 5)
    {
        document.feedbackForm.doCancel.value = "no";
        if (document.feedbackForm.doViewhealthdetail)
            document.feedbackForm.doViewhealthdetail.value = "no";
        if (document.feedbackForm.doViewBanklist)
            document.feedbackForm.doViewBanklist.value = "no";
        if (document.feedbackForm.doViewvaccinationlist)
            document.feedbackForm.doViewvaccinationlist.value = "yes";
        if (document.feedbackForm.doViewgovdocumentlist)
            document.feedbackForm.doViewgovdocumentlist.value = "no";
        if (document.feedbackForm.doViewtrainingcertlist)
            document.feedbackForm.doViewtrainingcertlist.value = "no";
        if (document.feedbackForm.doVieweducationlist)
            document.feedbackForm.doVieweducationlist.value = "no";
        if (document.feedbackForm.doViewexperiencelist)
            document.feedbackForm.doViewexperiencelist.value = "no";
        document.feedbackForm.action = "/jxp/feedback/FeedbackAction.do";
        document.feedbackForm.submit();
    }
    if (tp == 6)
    {
        document.feedbackForm.doCancel.value = "no";
        if (document.feedbackForm.doViewhealthdetail)
            document.feedbackForm.doViewhealthdetail.value = "no";
        if (document.feedbackForm.doViewBanklist)
            document.feedbackForm.doViewBanklist.value = "no";
        if (document.feedbackForm.doViewvaccinationlist)
            document.feedbackForm.doViewvaccinationlist.value = "no";
        if (document.feedbackForm.doViewgovdocumentlist)
            document.feedbackForm.doViewgovdocumentlist.value = "yes";
        if (document.feedbackForm.doViewtrainingcertlist)
            document.feedbackForm.doViewtrainingcertlist.value = "no";
        if (document.feedbackForm.doVieweducationlist)
            document.feedbackForm.doVieweducationlist.value = "no";
        if (document.feedbackForm.doViewexperiencelist)
            document.feedbackForm.doViewexperiencelist.value = "no";
        document.feedbackForm.action = "/jxp/feedback/FeedbackAction.do";
        document.feedbackForm.submit();
    }
    if (tp == 8)
    {
        document.feedbackForm.doCancel.value = "no";
        if (document.feedbackForm.doViewhealthdetail)
            document.feedbackForm.doViewhealthdetail.value = "no";
        if (document.feedbackForm.doViewBanklist)
            document.feedbackForm.doViewBanklist.value = "no";
        if (document.feedbackForm.doViewvaccinationlist)
            document.feedbackForm.doViewvaccinationlist.value = "no";
        if (document.feedbackForm.doViewgovdocumentlist)
            document.feedbackForm.doViewgovdocumentlist.value = "no";
        if (document.feedbackForm.doViewtrainingcertlist)
            document.feedbackForm.doViewtrainingcertlist.value = "yes";
        if (document.feedbackForm.doVieweducationlist)
            document.feedbackForm.doVieweducationlist.value = "no";
        if (document.feedbackForm.doViewexperiencelist)
            document.feedbackForm.doViewexperiencelist.value = "no";
        document.feedbackForm.action = "/jxp/feedback/FeedbackAction.do";
        document.feedbackForm.submit();
    }
    if (tp == 9)
    {
        document.feedbackForm.doCancel.value = "no";
        if (document.feedbackForm.doViewhealthdetail)
            document.feedbackForm.doViewhealthdetail.value = "no";
        if (document.feedbackForm.doViewBanklist)
            document.feedbackForm.doViewBanklist.value = "no";
        if (document.feedbackForm.doViewvaccinationlist)
            document.feedbackForm.doViewvaccinationlist.value = "no";
        if (document.feedbackForm.doViewgovdocumentlist)
            document.feedbackForm.doViewgovdocumentlist.value = "no";
        if (document.feedbackForm.doViewtrainingcertlist)
            document.feedbackForm.doViewtrainingcertlist.value = "no";
        if (document.feedbackForm.doVieweducationlist)
            document.feedbackForm.doVieweducationlist.value = "yes";
        if (document.feedbackForm.doViewexperiencelist)
            document.feedbackForm.doViewexperiencelist.value = "no";
        document.feedbackForm.action = "/jxp/feedback/FeedbackAction.do";
        document.feedbackForm.submit();
    }
    if (tp == 10)
    {
        document.feedbackForm.doCancel.value = "no";
        if (document.feedbackForm.doViewhealthdetail)
            document.feedbackForm.doViewhealthdetail.value = "no";
        if (document.feedbackForm.doViewBanklist)
            document.feedbackForm.doViewBanklist.value = "no";
        if (document.feedbackForm.doViewvaccinationlist)
            document.feedbackForm.doViewvaccinationlist.value = "no";
        if (document.feedbackForm.doViewgovdocumentlist)
            document.feedbackForm.doViewgovdocumentlist.value = "no";
        if (document.feedbackForm.doViewtrainingcertlist)
            document.feedbackForm.doViewtrainingcertlist.value = "no";
        if (document.feedbackForm.doVieweducationlist)
            document.feedbackForm.doVieweducationlist.value = "no";
        if (document.feedbackForm.doViewexperiencelist)
            document.feedbackForm.doViewexperiencelist.value = "yes";
        document.feedbackForm.action = "/jxp/feedback/FeedbackAction.do";
        document.feedbackForm.submit();
    }
    if (tp == 13)
    {
        document.feedbackForm.doCancel.value = "no";
        if (document.feedbackForm.doViewhealthdetail)
            document.feedbackForm.doViewhealthdetail.value = "no";
        if (document.feedbackForm.doViewBanklist)
            document.feedbackForm.doViewBanklist.value = "no";
        if (document.feedbackForm.doViewvaccinationlist)
            document.feedbackForm.doViewvaccinationlist.value = "no";
        if (document.feedbackForm.doViewgovdocumentlist)
            document.feedbackForm.doViewgovdocumentlist.value = "no";
        if (document.feedbackForm.doViewtrainingcertlist)
            document.feedbackForm.doViewtrainingcertlist.value = "no";
        if (document.feedbackForm.doVieweducationlist)
            document.feedbackForm.doVieweducationlist.value = "no";
        if (document.feedbackForm.doViewexperiencelist)
            document.feedbackForm.doViewexperiencelist.value = "no";
        if (document.feedbackForm.viewContractList)
            document.feedbackForm.viewContractList.value = "yes";
        document.feedbackForm.action = "/jxp/feedback/FeedbackAction.do";
        document.feedbackForm.submit();
    }
    if (tp == 14)
    {
        document.feedbackForm.doCancel.value = "no";
        if (document.feedbackForm.doViewhealthdetail)
            document.feedbackForm.doViewhealthdetail.value = "no";
        if (document.feedbackForm.doViewBanklist)
            document.feedbackForm.doViewBanklist.value = "no";
        if (document.feedbackForm.doViewvaccinationlist)
            document.feedbackForm.doViewvaccinationlist.value = "no";
        if (document.feedbackForm.doViewgovdocumentlist)
            document.feedbackForm.doViewgovdocumentlist.value = "no";
        if (document.feedbackForm.doViewtrainingcertlist)
            document.feedbackForm.doViewtrainingcertlist.value = "no";
        if (document.feedbackForm.doVieweducationlist)
            document.feedbackForm.doVieweducationlist.value = "no";
        if (document.feedbackForm.doViewexperiencelist)
            document.feedbackForm.doViewexperiencelist.value = "no";
        if (document.feedbackForm.viewContractList)
            document.feedbackForm.viewContractList.value = "no";
        if (document.feedbackForm.doViewBanklist)
            document.feedbackForm.doViewBanklist.value = "yes";
        document.feedbackForm.action = "/jxp/feedback/FeedbackAction.do";
        document.feedbackForm.submit();
    }
}

function view1()
{
    document.feedbackForm.doViewProfile.value = "yes";
    document.feedbackForm.doCancel.value = "no";
    if (document.feedbackForm.doViewlangdetail)
        document.feedbackForm.doViewlangdetail.value = "no";
    if (document.feedbackForm.doViewhealthdetail)
        document.feedbackForm.doViewhealthdetail.value = "no";
    if (document.feedbackForm.doViewvaccinationlist)
        document.feedbackForm.doViewvaccinationlist.value = "no";
    if (document.feedbackForm.doViewtrainingcertlist)
        document.feedbackForm.doViewtrainingcertlist.value = "no";
    if (document.feedbackForm.doVieweducationlist)
        document.feedbackForm.doVieweducationlist.value = "no";
    if (document.feedbackForm.doDeletelangdetail)
        document.feedbackForm.doDeletelangdetail.value = "no";
    if (document.feedbackForm.doViewexperiencelist)
        document.feedbackForm.doViewexperiencelist.value = "no";
    document.feedbackForm.action = "/jxp/feedback/FeedbackAction.do";
    document.feedbackForm.submit();
}

function viewexperiendetail(id)
{
    document.feedbackForm.experiencedetailId.value = id;
    document.feedbackForm.doViewProfile.value = "no";
    document.feedbackForm.doCancel.value = "no";
    if (document.feedbackForm.doViewlangdetail)
        document.feedbackForm.doViewlangdetail.value = "no";
    if (document.feedbackForm.doViewhealthdetail)
        document.feedbackForm.doViewhealthdetail.value = "no";
    if (document.feedbackForm.doViewvaccinationlist)
        document.feedbackForm.doViewvaccinationlist.value = "no";
    if (document.feedbackForm.doViewtrainingcertlist)
        document.feedbackForm.doViewtrainingcertlist.value = "no";
    if (document.feedbackForm.doVieweducationlist)
        document.feedbackForm.doVieweducationlist.value = "no";
    if (document.feedbackForm.doDeletelangdetail)
        document.feedbackForm.doDeletelangdetail.value = "no";
    if (document.feedbackForm.doViewexperiencelist)
        document.feedbackForm.doViewexperiencelist.value = "no";
    if (document.feedbackForm.doViewexperience)
        document.feedbackForm.doViewexperience.value = "yes";
    document.feedbackForm.action = "/jxp/feedback/FeedbackAction.do";
    document.feedbackForm.submit();
}

function viewcertificationdetail(id)
{
    document.feedbackForm.certificationdetaillId.value = id;
    document.feedbackForm.doViewProfile.value = "no";
    document.feedbackForm.doCancel.value = "no";
    if (document.feedbackForm.doViewlangdetail)
        document.feedbackForm.doViewlangdetail.value = "no";
    if (document.feedbackForm.doViewhealthdetail)
        document.feedbackForm.doViewhealthdetail.value = "no";
    if (document.feedbackForm.doViewvaccinationlist)
        document.feedbackForm.doViewvaccinationlist.value = "no";
    if (document.feedbackForm.doViewtrainingcertlist)
        document.feedbackForm.doViewtrainingcertlist.value = "no";
    if (document.feedbackForm.doVieweducationlist)
        document.feedbackForm.doVieweducationlist.value = "no";
    if (document.feedbackForm.doDeletelangdetail)
        document.feedbackForm.doDeletelangdetail.value = "no";
    if (document.feedbackForm.doViewexperiencelist)
        document.feedbackForm.doViewexperiencelist.value = "no";
    if (document.feedbackForm.doViewcertification)
        document.feedbackForm.doViewcertification.value = "yes";
    document.feedbackForm.action = "/jxp/feedback/FeedbackAction.do";
    document.feedbackForm.submit();
}

function viewimgdoc(govId)
{
    var url = "/jxp/ajax/feedback/getimg_doc.jsp";
    var httploc = getHTTPObject();
    var getstr = "govId=" + govId;
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
    document.getElementById('viewfilesdiv').innerHTML = "<div><img src='/jxp/assets/images/loading.gif' align='absmiddle'/>Please wait. Loading...</div>";
}

function viewworkexpfiles(filename1, filename2)
{
    var url = "/jxp/ajax/feedback/getimg_exp.jsp";
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
                document.getElementById('viewfilesdiv').innerHTML = '';
                document.getElementById('viewfilesdiv').innerHTML = response;

                $("#iframe").on("load", function () {
                    let head = $("#iframe").contents().find("head");
                    let css = '<style>img{margin: 0px auto;max-width:-webkit-fill-available;}</style>';
                    $(head).append(css);
                });

            }
        }
    };
    httploc.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    httploc.setRequestHeader("Content-length", getstr.length);
    httploc.setRequestHeader("Connection", "close");
    httploc.send(getstr);
    document.getElementById('viewfilesdiv').innerHTML = "<div><img src='/jxp/assets/images/loading.gif' align='absmiddle'/>Please wait. Loading...</div>";
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
        url_v = uval + "#toolbar=0&page=1&view=fitH,100";
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

function getCompetencyPendingModal(Id)
{
    var url = "/jxp/ajax/feedback/getCompetencyPending.jsp";
    var httploc = getHTTPObject();
    var getstr = "Id=" + Id;
    httploc.open("POST", url, true);
    httploc.onreadystatechange = function ()
    {
        if (httploc.readyState == 4)
        {
            if (httploc.status == 200)
            {
                var response = httploc.responseText;
                document.getElementById('dCompePendingModal').innerHTML = '';
                document.getElementById('dCompePendingModal').innerHTML = response;
                $("#user_comp_pending_modal").modal("show");
            }
        }
    };
    httploc.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    httploc.setRequestHeader("Content-length", getstr.length);
    httploc.setRequestHeader("Connection", "close");
    httploc.send(getstr);
    document.getElementById('dCompePendingModal').innerHTML = "<div><img src='/jxp/assets/images/loading.gif' align='absmiddle'/>Please wait. Loading...</div>";
}

function getCompetencyAppealModal(Id)
{
    var url = "/jxp/ajax/feedback/getCompetencyAppeal.jsp";
    var httploc = getHTTPObject();
    var getstr = "Id=" + Id;
    httploc.open("POST", url, true);
    httploc.onreadystatechange = function ()
    {
        if (httploc.readyState == 4)
        {
            if (httploc.status == 200)
            {
                var response = httploc.responseText;
                document.getElementById('dUserCompAssAppModal').innerHTML = '';
                document.getElementById('dUserCompAssAppModal').innerHTML = response;
                $("#user_comp_ass_app_request_modal").modal("show");
            }
        }
    };
    httploc.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    httploc.setRequestHeader("Content-length", getstr.length);
    httploc.setRequestHeader("Connection", "close");
    httploc.send(getstr);
    document.getElementById('dUserCompAssAppModal').innerHTML = "<div><img src='/jxp/assets/images/loading.gif' align='absmiddle'/>Please wait. Loading...</div>";
}

function getSendAppeal(Id) {
    if (Number(document.feedbackForm.appealId.value) <= 0) {
        Swal.fire({
            title: "Please select reason.",
            didClose: () => {
                document.feedbackForm.appealId.focus();
            }
        })
        return false;
    }
    if (document.feedbackForm.appealremarks.value == "") {
        Swal.fire({
            title: "Please enter remarks.",
            didClose: () => {
                document.feedbackForm.appealremarks.focus();
            }
        })
        return false;
    }
    document.feedbackForm.trackerId.value = Id;
    document.feedbackForm.doSendAppeal.value = "yes";
    $("#user_comp_ass_app_request_modal").modal("hide");
    document.feedbackForm.action = "/jxp/feedback/FeedbackAction.do";
    document.feedbackForm.submit();
}

function getSuccessModal() {
    $("#thank_you_modal").modal("show");
}

function getOnlineAssessment(Id) 
{
    document.feedbackForm.trackerId.value = Id;
    document.feedbackForm.doOnlineAssessment.value = "yes";
    document.feedbackForm.action = "/jxp/feedback/FeedbackAction.do";
    document.feedbackForm.submit();
}

function resetCompetency()
{
    var count = Number(document.feedbackForm.answer.length);
    if (count > 0) {
        for (var i = 0; i < count; i++) {
            document.feedbackForm.answer[i].value = "";
        }
    }
}

function getSaveAssessment() {
    var count = Number(document.feedbackForm.answer.length);
    if (count > 0) {
        var tempcount = 0;
        for (var i = 0; i < count; i++) {
            if (document.feedbackForm.answer[i].value != "") {
                tempcount++;
            }
        }
        if (tempcount == 0) {
            Swal.fire({
                title: "Please complete atleast one answer.",
            })
            return false;
        }
    }
    document.feedbackForm.doSaveAssessment.value = "yes";
    document.feedbackForm.action = "/jxp/feedback/FeedbackAction.do";
    document.feedbackForm.submit();
}

function checkassessment() {
    var count = Number(document.feedbackForm.answer.length);
    if (count > 0)
    {
        var tempcount = 0;
        for (var i = 0; i < count; i++) {
            if (document.feedbackForm.answer[i].value != "") {
                tempcount++;
            }
        }
        if (count != tempcount) {
            Swal.fire({
                title: "Please complete all answer.",
            })
            return false;
        }
    }

    if (!(document.getElementById("allwork").checked)) {
        Swal.fire({
            title: "Please confirm the checkbox",
        })
        return false;
    }
    return true;
}

function getSubmitAssessment()
{
    if (checkassessment()) 
    {
        var s = "Submit answers for knowledge assessment?";
        Swal.fire({
            title: s,
            confirmButtonColor: '#3085d6',
            cancelButtonColor: '#d33',
            confirmButtonText: 'Confirm',
            showCloseButton: true,
            allowOutsideClick: false,
            allowEscapeKey: false
        }).then((result) => {
            if (result.isConfirmed)
            {
                document.feedbackForm.doSubmitAssessment.value = "yes";
                document.feedbackForm.action = "/jxp/feedback/FeedbackAction.do";
                document.feedbackForm.submit();
            }
        });
    }
}

function getFeedback(Id) 
{
    var url = "/jxp/ajax/feedback/getCompetencyFeedback.jsp";
    var httploc = getHTTPObject();
    var getstr = "Id=" + Id;
    httploc.open("POST", url, true);
    httploc.onreadystatechange = function ()
    {
        if (httploc.readyState == 4)
        {
            if (httploc.status == 200)
            {
                var response = httploc.responseText;
                document.getElementById('dfeedback').innerHTML = '';
                document.getElementById('dfeedback').innerHTML = response;
                $("#user_comp_pending_modal").modal("hide");
                $("#user_comp_ass_complete_feedback_modal").modal("show");
            }
        }
    };
    httploc.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    httploc.setRequestHeader("Content-length", getstr.length);
    httploc.setRequestHeader("Connection", "close");
    httploc.send(getstr);
    document.getElementById('dfeedback').innerHTML = "<div><img src='/jxp/assets/images/loading.gif' align='absmiddle'/>Please wait. Loading...</div>";
}

function getSaveFeedback(Id) {
    document.feedbackForm.trackerId.value = Id;
    document.feedbackForm.doSaveFeedback.value = "yes";
    document.feedbackForm.action = "/jxp/feedback/FeedbackAction.do";
    document.feedbackForm.submit();
}

function getDownloadToPrint(fn, trackerId)
{
    if (fn != "")
    {
        window.open(fn, '_blank');
    } else
    {
        var url = "/jxp/ajax/feedback/generatepdf.jsp";
        var httploc = getHTTPObject();
        var getstr = "Id=" + trackerId;
        httploc.open("POST", url, true);
        httploc.onreadystatechange = function ()
        {
            if (httploc.readyState == 4)
            {
                if (httploc.status == 200)
                {
                    var response = trim(httploc.responseText);
                    document.getElementById("hrefid").src = "javascript: viewFile('" + response + "', '" + trackerId + "');";
                    window.open(response, '_blank');
                }
            }
        };
        httploc.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
        httploc.setRequestHeader("Content-length", getstr.length);
        httploc.setRequestHeader("Connection", "close");
        httploc.send(getstr);
    }
}

function gobackcomp()
{
    document.feedbackForm.doCancelCompetency.value = "yes";
    document.feedbackForm.action = "/jxp/feedback/FeedbackAction.do";
    document.feedbackForm.submit();
}

function getTopic()
{
    document.feedbackForm.doViewtopic.value = "yes";
    document.feedbackForm.action = "/jxp/feedback/FeedbackAction.do";
    document.feedbackForm.submit();
}

function viewTopicFiles(id)
{
    var url = "/jxp/ajax/feedback/gettopicfiles.jsp";
    var httploc = getHTTPObject();
    var getstr = "topicId=" + id;
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
    document.getElementById('viewfilesdiv').innerHTML = "<div><img src='/jxp/assets/images/loading.gif' align='absmiddle'/>Please wait. Loading...</div>";
}

function logout()
{
    var s = "Are you sure you want to logout?";
    Swal.fire({
        title: s,
        confirmButtonColor: '#3085d6',
        cancelButtonColor: '#d33',
        confirmButtonText: 'Confirm',
        showCloseButton: true,
        allowOutsideClick: false,
        allowEscapeKey: false
    }).then((result) => {
        if (result.isConfirmed)
        {
            document.forms[0].doLogout.value = "yes"
            document.forms[0].action="/jxp/feedback/FeedbackAction.do?doLogout=yes";    
            document.forms[0].submit();    
        }
    });
}

function getDocumentlist()
{
    document.forms[0].target = "_self";
    document.feedbackForm.documentList.value = "yes";
    document.feedbackForm.action = "/jxp/feedback/FeedbackAction.do";
    document.feedbackForm.submit();
}

function getTraininglist()
{
    document.forms[0].target = "_self";
    document.feedbackForm.trainingList.value = "yes";
    document.feedbackForm.action = "/jxp/feedback/FeedbackAction.do";
    document.feedbackForm.submit();
}

function modifydocumentdetailForm(id)
{
    document.feedbackForm.govdocumentId.value = id;
    document.feedbackForm.domodifygovdocumentdetail.value = "yes";
    document.feedbackForm.action = "../feedback/FeedbackAction.do";
    document.feedbackForm.submit();
}

function setDocumentissuedbyDDL()
{
    var url = "../ajax/feedback/getdocumentissuedby.jsp";
    var httploc = getHTTPObject();
    var getstr = "documentTypeId=" + document.feedbackForm.documentTypeId.value;
    httploc.open("POST", url, true);    
    httploc.onreadystatechange = function ()
    {
        if (httploc.readyState == 4)
        {
            if (httploc.status == 200)
            {
                var response = httploc.responseText;
                document.getElementById("documentissuedbyId").innerHTML = '';
                document.getElementById("documentissuedbyId").innerHTML = response;
            }
        }
    };
    httploc.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    httploc.setRequestHeader("Content-length", getstr.length);
    httploc.setRequestHeader("Connection", "close");
    httploc.send(getstr);
}

function submitdocumentform()
{
    if (document.forms[0].getElementsByTagName("input"))
    {
        var inputElements = document.forms[0].getElementsByTagName("input");
        for (i = 0; i < inputElements.length; i++)
        {
            if (inputElements[i].type == "text")
            {
                inputElements[i].value = trim(inputElements[i].value);
            }
        }
    }
    if (checkDocumentForm())
    {
        document.getElementById('submitdiv').innerHTML = "<img src='../assets/images/loading.gif' align='absmiddle' />";
        document.feedbackForm.doSavegovdocumentdetail.value = "yes";
        document.feedbackForm.action = "../feedback/FeedbackAction.do";
        document.feedbackForm.submit();
    }
}

function checkDocumentForm()
{
    if (document.feedbackForm.documentTypeId.value == "-1")
    {
        Swal.fire({
            title: "Please select document name.",
            didClose: () => {
                document.feedbackForm.documentTypeId.focus();
            }
        })
        return false;
    }
    if (trim(document.feedbackForm.documentNo.value) == "")
    {
        Swal.fire({
            title: "Please enter document no.",
            didClose: () => {
                document.feedbackForm.documentNo.focus();
            }
        })
        return false;
    }
    if (validdesc(document.feedbackForm.documentNo) == false)
    {
        return false;
    }
    if (document.feedbackForm.dateofissue.value != "")
    {
        if (comparisionTest(document.feedbackForm.dateofissue.value, document.feedbackForm.currentDate.value) == false)
        {
            Swal.fire({
                title: "Please check date of issue.",
                didClose: () => {
                    document.feedbackForm.dateofissue.value = "";
                }
            })
            return false;
        }
    }

    if (document.feedbackForm.dateofexpiry.value != "")
    {
        if (comparisionTest(document.feedbackForm.dateofissue.value, document.feedbackForm.dateofexpiry.value) == false)
        {
            Swal.fire({
                title: "Please check date of expiry.",
                didClose: () => {
                    document.feedbackForm.dateofexpiry.value = "";
                }
            })
            return false;
        }
    }
    if (document.feedbackForm.documentissuedbyId.value == "-1")
    {
        Swal.fire({
            title: "Please select document issued by.",
            didClose: () => {
                document.feedbackForm.documentissuedbyId.focus();
            }
        })
        return false;
    }
    return true;
}

function modifytrainingcertdetailForm(id)
{
    document.feedbackForm.trainingandcertId.value = id;
    document.feedbackForm.doaddtrainingcertdetail.value = "yes";
    document.feedbackForm.action = "../feedback/FeedbackAction.do";
    document.feedbackForm.submit();
}

function  submittrainingcertform()
{
    if (document.forms[0].getElementsByTagName("input"))
    {
        var inputElements = document.forms[0].getElementsByTagName("input");
        for (i = 0; i < inputElements.length; i++)
        {
            if (inputElements[i].type == "text")
            {
                inputElements[i].value = trim(inputElements[i].value);
            }
        }
    }
    if (checkCertificationDetails())
    {
        document.getElementById('submitdiv').innerHTML = "<img src='../assets/images/loading.gif' align='absmiddle' />";
        document.feedbackForm.doSavetrainingcertdetail.value = "yes";
        document.feedbackForm.action ="../feedback/FeedbackAction.do";
        document.feedbackForm.submit();
    }
}

function checkCertificationDetails()
{
    if (Number(document.feedbackForm.coursenameId.value) <= 0)
    {
        Swal.fire({
            title: "Please select course name.",
            didClose: () => {
                document.feedbackForm.coursenameId.focus();
            }
        })
        return false;
    }
    if (trim(document.feedbackForm.educationInstitute.value) == "")
    {
        Swal.fire({
            title: "Please enter educational institute.",
            didClose: () => {
                document.feedbackForm.educationInstitute.focus();
            }
        })
        return false;
    }
    if (validdesc(document.feedbackForm.educationInstitute) == false)
    {
        return false;
    }
    if (trim(document.feedbackForm.cityName.value) == "")
    {
        Swal.fire({
            title: "Please enter location of institute.",
            didClose: () => {
                document.feedbackForm.cityName.focus();
            }
        })
        return false;
    }
    if (validdesc(document.feedbackForm.cityName) == false)
    {
        return false;
    }
    if (eval(document.feedbackForm.locationofInstituteId.value) <= 0)
    {
        Swal.fire({
            title: "Please select location of institute using autofill.",
            didClose: () => {
                document.feedbackForm.cityName.focus();
            }
        })
        return false;
    }

    if (document.feedbackForm.dateofissue.value == "")
    {
        Swal.fire({
            title: "Please select date of issue.",
            didClose: () => {
                document.feedbackForm.dateofissue.focus();
            }
        })
        return false;
    }

    if (trim(document.feedbackForm.certificationno.value) == "")
    {
        Swal.fire({
            title: "Please enter certification no.",
            didClose: () => {
                document.feedbackForm.certificationno.focus();
            }
        })
        return false;
    }
    if (validdesc(document.feedbackForm.certificationno) == false)
    {
        return false;
    }
    if (document.feedbackForm.dateofexpiry.value != "") {
        if (comparisionTest(document.feedbackForm.dateofissue.value, document.feedbackForm.dateofexpiry.value) == false)
        {
            Swal.fire({
                title: "Please check date of expiry.",
                didClose: () => {
                    document.feedbackForm.dateofexpiry.value = "";
                }
            })
            return false;
        }
    }
    if (document.feedbackForm.upload1.value != "")
    {
        if (!(document.feedbackForm.upload1.value).match(/(\.(png)|(pdf)|(jpg)|(jpeg))$/i))
        {
            Swal.fire({
                title: "Only  .jpg, .jpeg, .png .pdf are allowed.",
                didClose: () => {
                    document.feedbackForm.upload1.focus();
                }
            })
            return false;
        }
        var input = document.feedbackForm.upload1;
        if (input.files)
        {
            var file = input.files[0];
            if (file.size > 1024 * 1024 * 5)
            {
                Swal.fire({
                    title: "File size should not exceed 5 MB.",
                    didClose: () => {
                        document.feedbackForm.upload1.focus();
                    }
                })
                return false;
            }
        }
    }
    return true;
}

function setClass(tp)
{
    document.getElementById("upload_link_" + tp).className = "attache_btn uploaded_img";
}

function viewContractDoc(id, file1, file2, file3)
{
    var url = "../ajax/feedback/getContractdoc.jsp";
    var httploc = getHTTPObject();
    var getstr = "contractdetailId=" + id;
    getstr += "&filename1=" + file1;
    getstr += "&filename2=" + file2;
    getstr += "&filename3=" + file3;
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
                $("#iframe").on("load", function () {
                    let head = $("#iframe").contents().find("head");
                    let css = '<style>img{margin: 0px auto;max-width:-webkit-fill-available;}</style>';
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
    
    //For contract approval
    function getCrewDetails(contractdetailId, type)
    {
        $('#crew_contract_modal').modal('show');
        var url = "../ajax/feedback/contractApprove.jsp";
        var httploc = getHTTPObject();
        var getstr = "";
        getstr += "contractdetailId=" + contractdetailId;
        getstr += "&candidateId=" + document.feedbackForm.candidateId.value;
        getstr += "&type=" + type;
        httploc.open("POST", url, true);
        httploc.onreadystatechange = function ()
        {
            if (httploc.readyState == 4)
            {
                if (httploc.status == 200)
                {
                    var response = httploc.responseText;
                    document.getElementById('crew_contractdata').innerHTML = '';
                    document.getElementById('crew_contractdata').innerHTML = response;
                    $("#upload_link_1").on('click', function (e) {
                        e.preventDefault();
                        $("#upload1:hidden").trigger('click');
                    });
                }
            }
        };
        httploc.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
        httploc.setRequestHeader("Content-length", getstr.length);
        httploc.setRequestHeader("Connection", "close");
        httploc.send(getstr);
        document.getElementById('crew_contractdata').innerHTML = "<div><img src='../assets/images/loading.gif' align='absmiddle'/>Please wait. Loading...</div>";
    }
    
    function submitApproveForm(contractdetailId, id)
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
        if (checkContractApprove())
        {
            document.getElementById('submitdiv').innerHTML = "<img src='../assets/images/loading.gif' align='absmiddle' />";
            document.forms[0].contractApprove.value="yes";
            document.forms[0].contractdetailId.value= contractdetailId;
            document.forms[0].type.value= id;
            document.forms[0].action="../feedback/FeedbackAction.do";
            document.forms[0].submit();
        }
    }
    
    function checkContractApprove()
    {
        if (document.forms[0].remark.value == "")
        {
            Swal.fire({
                title: "Please enter remark.",
                didClose: () => {
                    document.forms[0].remark.focus();
                }
            })
            return false;
        }
        if (document.forms[0].contractfile.value == "")
        {
            Swal.fire({
                title: "please upload file.",
                didClose: () => {
                    document.forms[0].contractfile.focus();
                }
            })
            return false;
        }
        if (document.forms[0].contractfile.value != "")
        {
            if (!(document.forms[0].contractfile.value).match(/(\.(pdf))$/i))
            {
                Swal.fire({
                    title: "Only .pdf are allowed.",
                    didClose: () => {
                        document.forms[0].contractfile.focus();
                    }
                })
                return false;
            }
            var input = document.forms[0].contractfile;
            if (input.files)
            {
                var file = input.files[0];
                if (file.size > 1024 * 1024 * 5)
                {
                    Swal.fire({
                        title: "File size should not exceed 5 MB.",
                        didClose: () => {
                            document.forms[0].contractfile.focus();
                        }
                    })
                    return false;
                }
            }
        }
        return true;
    }
    
    function searchDocument()
    {
        document.feedbackForm.searchDoc.value = "yes";
        document.feedbackForm.action = "../feedback/FeedbackAction.do";
        document.feedbackForm.submit();
    }
    
    function getCrewAsset()
    {
        var url = "../ajax/feedback/getCrewAsset.jsp";
        var httploc = getHTTPObject();
        var getstr = "";
        getstr += "crewClientId=" + document.feedbackForm.crewClientId.value;
        getstr += "&candidateId=" + document.feedbackForm.candidateId.value;
        httploc.open("POST", url, true);
        httploc.onreadystatechange = function ()
        {
            if (httploc.readyState == 4)
            {
                if (httploc.status == 200)
                {
                    var response = httploc.responseText;
                    document.getElementById("crewAssetId").innerHTML = '';
                    document.getElementById("crewAssetId").innerHTML = response;
                }
            }
        };
        httploc.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
        httploc.send(getstr);
    }
    
    function getModalOf(contractdetailId)
    {
        $('#user_comp_pending_modal').modal('show');
        var url = "../ajax/feedback/getContractFileModal.jsp";
        var httploc = getHTTPObject();
        var getstr = "";
        getstr += "contractdetailId=" + contractdetailId;
        getstr += "&candidateId=" + document.feedbackForm.candidateId.value;
        httploc.open("POST", url, true);
        httploc.onreadystatechange = function ()
        {
            if (httploc.readyState == 4)
            {
                if (httploc.status == 200)
                {
                    var response = httploc.responseText;
                    document.getElementById('modal_contract').innerHTML = '';
                    document.getElementById('modal_contract').innerHTML = response;
                }
            }
        };
        httploc.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
        httploc.setRequestHeader("Content-length", getstr.length);
        httploc.setRequestHeader("Connection", "close");
        httploc.send(getstr);
        document.getElementById('modal_contract').innerHTML = "<div><img src='../assets/images/loading.gif' align='absmiddle'/>Please wait. Loading...</div>";
    }
    
    function searchDocument()
    {
        document.feedbackForm.searchDoc.value = "yes";
        document.feedbackForm.action = "../feedback/FeedbackAction.do";
        document.feedbackForm.submit();
    }
    
    function searchContract()
    {
        document.feedbackForm.searchContract.value = "yes";
        document.feedbackForm.action = "../feedback/FeedbackAction.do";
        document.feedbackForm.submit();
    }
    
    
    function searchContract(v, v1)
    {
        var url = "/jxp/ajax/feedback/getinfo_contract.jsp";
        var httploc = getHTTPObject();
        var getstr = "";
        var next_contractvalue = escape(document.feedbackForm.nextContractValue.value);
        var search_value = escape(document.feedbackForm.search1.value);

        var crewClientId = (document.feedbackForm.crewClientId.value);
        var crewAssetId = (document.feedbackForm.crewAssetId.value);
        var fromDate = (document.feedbackForm.fromDate1.value);
        var toDate = (document.feedbackForm.toDate1.value);

        var fromDate = "";
        var toDate = "";
        if (document.feedbackForm.fromDate1.value != "") {
            fromDate = document.feedbackForm.fromDate1.value;
        } else if (document.feedbackForm.fromDateMob.value != "") {
            fromDate = document.feedbackForm.fromDateMob.value;
        }
        if (document.feedbackForm.toDate1.value != "") {
            toDate = document.feedbackForm.toDate1.value;
        } else if (document.feedbackForm.toDateMob.value != "") {
            toDate = document.feedbackForm.toDateMob.value;
        }        
        getstr += "crewClientId=" + crewClientId;
        getstr += "&crewAssetId=" + crewAssetId;
        getstr += "&fromDate=" + fromDate;
        getstr += "&toDate=" + toDate;
        getstr += "&next=" + v;
        getstr += "&nextContractValue=" + next_contractvalue;
        getstr += "&search=" + search_value;
        getstr += "&doDirect=" + v1;
        httploc.open("POST", url, true);
        httploc.onreadystatechange = function ()
        {
            if (httploc.readyState == 4)
            {
                if (httploc.status == 200)
                {
                    var response = httploc.responseText;
                    document.getElementById('ajax_contract').innerHTML = '';
                    document.getElementById('ajax_contract').innerHTML = response;
                }
            }
        };
        httploc.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
        httploc.setRequestHeader("Content-length", getstr.length);
        httploc.setRequestHeader("Connection", "close");
        httploc.send(getstr);
        document.getElementById('ajax_contract').innerHTML = "<div><img src='/jxp/assets/images/loading.gif' align='absmiddle'/>Please wait. Loading...</div>";
    }
    
    function getInterviewlist()
    {
        document.forms[0].target = "_self";
        document.feedbackForm.viewInterviewList.value = "yes";
        document.feedbackForm.action = "/jxp/feedback/FeedbackAction.do";
        document.feedbackForm.submit();
    }
    
    function setAvailability(interviewId)
    {
        var url = "../ajax/feedback/getAvailabilityDetail.jsp";
        var httploc = getHTTPObject();
        var getstr = "";
        getstr += "interviewId=" + interviewId;
        httploc.open("POST", url, true);
        httploc.onreadystatechange = function ()
        {
            if (httploc.readyState == 4)
            {
                if (httploc.status == 200)
                {
                    var response = httploc.responseText;
                    var arr = new Array();
                    document.getElementById('int_pending_div').innerHTML = '';
                    document.getElementById('int_pending_div').innerHTML = response;
                }
            }
        };
        httploc.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
        httploc.setRequestHeader("Content-length", getstr.length);
        httploc.setRequestHeader("Connection", "close");
        httploc.send(getstr);
        document.getElementById('int_pending_div').innerHTML = "<div><img src='../assets/images/loading.gif' align='absmiddle'/>Please wait. Loading...</div>";
    }
    
    function saveAvailability()
    {
        if(checkAvailability())
        {
            document.getElementById("submitdiv").innerHTML = "<img src='../assets/images/loading.gif' align='absmiddle' />";
            if (document.feedbackForm.doSaveAvailability)
                document.feedbackForm.doSaveAvailability.value = "yes";
            document.forms[0].target = "";
            document.feedbackForm.action = "/jxp/feedback/FeedbackAction.do";
            document.feedbackForm.submit();
        }
    }
    
    function checkAvailability()
    {
        if (document.feedbackForm.type.value <= 0) 
        {
            Swal.fire({
                title: "Please select availability",
                didClose: () => {
                    document.feedbackForm.type.focus();
                }
            })
            return false;
        }
        if (document.feedbackForm.remark.value == "") 
        {
            Swal.fire({
                title: "Please enter remarks",
                didClose: () => {
                    document.feedbackForm.remark.focus();
                }
            })
            return false;
        }
        return true;
    }
    
    function getOfferlist()
    {
        document.forms[0].target = "_self";
        document.feedbackForm.viewClientOfferwList.value = "yes";
        document.feedbackForm.action = "/jxp/feedback/FeedbackAction.do";
        document.feedbackForm.submit();
    }
    
    function replayToOffer(shortlistId)
    {
        var url = "../ajax/feedback/getReplayOffer.jsp";
        var httploc = getHTTPObject();
        var getstr = "";
        getstr += "shortlistId=" + shortlistId;
        httploc.open("POST", url, true);
        httploc.onreadystatechange = function ()
        {
            if (httploc.readyState == 4)
            {
                if (httploc.status == 200)
                {
                    var response = httploc.responseText;
                    var arr = new Array();
                    document.getElementById('offer_pending_div').innerHTML = '';
                    document.getElementById('offer_pending_div').innerHTML = response;
                }
            }
        };
        httploc.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
        httploc.setRequestHeader("Content-length", getstr.length);
        httploc.setRequestHeader("Connection", "close");
        httploc.send(getstr);
        document.getElementById('offer_pending_div').innerHTML = "<div><img src='../assets/images/loading.gif' align='absmiddle'/>Please wait. Loading...</div>";
    }
    
    function saveOffer(type)
    {
        if (document.feedbackForm.doSaveOffer)
            document.feedbackForm.doSaveOffer.value = "yes";
        document.feedbackForm.type.value = type;
        document.forms[0].target = "";
        document.feedbackForm.action = "/jxp/feedback/FeedbackAction.do";
        document.feedbackForm.submit();
    }
    
    function getEmailModal(shortlistId, type)
    {
        $('#mail_modal').modal('show');
        var url = "../ajax/feedback/getEmailModal.jsp";
        var httploc = getHTTPObject();
        var getstr = "";
        getstr += "shortlistId=" + shortlistId;
        getstr += "&type=" + type;
        httploc.open("POST", url, true);
        httploc.onreadystatechange = function ()
        {
            if (httploc.readyState == 4)
            {
                if (httploc.status == 200)
                {
                    var response = httploc.responseText;
                    var arr = new Array();
                    document.getElementById('emailmodal').innerHTML = '';
                    document.getElementById('emailmodal').innerHTML = response;
                    $("#upload_link_1").on('click', function (e) {
                        e.preventDefault();
                        $("#upload1:hidden").trigger('click');
                    });
                }
            }
        };
        httploc.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
        httploc.setRequestHeader("Content-length", getstr.length);
        httploc.setRequestHeader("Connection", "close");
        httploc.send(getstr);
        document.getElementById('emailmodal').innerHTML = "<div><img src='../assets/images/loading.gif' align='absmiddle'/>Please wait. Loading...</div>";
    }
    
    function sendmail()
    {
        if (document.forms[0].getElementsByTagName("input"))
        {
            var inputElements = document.forms[0].getElementsByTagName("input");
            for (i = 0; i < inputElements.length; i++)
            {
                if (inputElements[i].type == "text")
                {
                    inputElements[i].value = trim(inputElements[i].value);
                }
            }
        }
        if (checkMail())
        {
            document.getElementById("sendmaildiv").innerHTML = "<img src='../assets/images/loading.gif' align='absmiddle' />";
            document.feedbackForm.doSendmail.value = "yes";
            document.feedbackForm.action = "../feedback/FeedbackAction.do";
            document.feedbackForm.submit();
        }
    }
    
    function checkMail()
    {
        var toval = document.forms[0].toval.value;
        if (toval == "")
        {
            Swal.fire({
                title: "Please enter To email address.",
                didClose: () => {
                    document.forms[0].toval.focus();
                }
            })
            return false;
        }
        if (toval != "")
        {
            var arr = toval.split(',');
            var len = arr.length;
            for (var i = 0; i < len; i++)
            {
                if (checkEmailAddressVal(trim(arr[i])) == false)
                {
                    Swal.fire({
                        title: "please enter valid  email address.",
                        didClose: () => {
                            document.forms[0].toval.focus();
                        }
                    })
                    return false;
                }
            }
        }
        var ccval = document.forms[0].ccval.value;
        if (ccval != "")
        {
            var arr = ccval.split(',');
            var len = arr.length;
            for (var i = 0; i < len; i++)
            {
                if (checkEmailAddressVal(trim(arr[i])) == false)
                {
                    Swal.fire({
                        title: "please enter valid email address.",
                        didClose: () => {
                            document.forms[0].ccval.focus();
                        }
                    })
                    return false;
                }
            }
        }
        var bccval = document.forms[0].bccval.value;

        if (bccval != "")
        {
            var arr = bccval.split(',');
            var len = arr.length;
            for (var i = 0; i < len; i++)
            {
                if (checkEmailAddressVal(trim(arr[i])) == false)
                {
                    Swal.fire({
                        title: "please enter valid email address.",
                        didClose: () => {
                            document.forms[0].bccval.focus();
                        }
                    })
                    return false;
                }
            }
        }
        if (trim(document.forms[0].subject.value) == "")
        {
            Swal.fire({
                title: "Please enter subject.",
                didClose: () => {
                    document.forms[0].subject.focus();
                }
            })
            return false;
        }
        if (validdesc(document.forms[0].subject) == false)
        {
            return false;
        }
        if (trim(document.forms[0].description.value) == "")
        {
            Swal.fire({
                title: "Please enter Email Body.",
                didClose: () => {
                    document.forms[0].description.focus();
                }
            })
            return false;
        }
        if (validdesc(document.forms[0].description) == false)
        {
            return false;
        }
        if (document.forms[0].type.value == 1)
        {
            if (document.forms[0].offerFile.value == "")
            {
                Swal.fire({
                    title: "please upload file.",
                    didClose: () => {
                        document.forms[0].offerFile.focus();
                    }
                })
                return false;
            }
            if (document.forms[0].offerFile.value != "")
            {
                if (!(document.forms[0].offerFile.value).match(/(\.(pdf))$/i))
                {
                    Swal.fire({
                        title: "Only .pdf are allowed.",
                        didClose: () => {
                            document.forms[0].offerFile.focus();
                        }
                    })
                    return false;
                }
                var input = document.forms[0].offerFile;
                if (input.files)
                {
                    var file = input.files[0];
                    if (file.size > 1024 * 1024 * 5)
                    {
                        Swal.fire({
                            title: "File size should not exceed 5 MB.",
                            didClose: () => {
                                document.forms[0].offerFile.focus();
                            }
                        })
                        return false;
                    }
                }
            }
        }
        return true;
    }
    
    function setAvailability(id)
    {
        if (document.feedbackForm.doAddAvailability)
            document.feedbackForm.doAddAvailability.value = "yes";
        document.feedbackForm.interviewId.value = id;
        document.feedbackForm.action = "/jxp/feedback/FeedbackAction.do";
        document.feedbackForm.submit();
    }