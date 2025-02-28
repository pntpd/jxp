function resetFilter()
{
    document.forms[0].search.value = "";
    document.userForm.permissionIndex.checked = false;
    document.userForm.permissionIndex.value = "";
    document.userForm.clientIndex.value = "-1";
    searchFormAjax('s', '-1');
}

function showDetail(id)
{
    document.userForm.doModify.value = "no";
    document.userForm.doAdd.value = "no";
    document.userForm.doDetail.value = "yes";
    document.userForm.userId.value = id;
    document.userForm.action = "../user/UserAction.do";
    document.userForm.submit();
}

function addForm()
{
    document.userForm.doAdd.value = "yes";
    document.userForm.doDetail.value = "no";
    document.userForm.doModify.value = "no";
    document.userForm.action = "../user/UserAction.do";
    document.userForm.submit();
}

function modifyForm(id)
{
    document.userForm.doModify.value = "yes";
    document.userForm.doAdd.value = "no";
    document.userForm.doDetail.value = "no";
    document.userForm.userId.value = id;
    document.userForm.action = "../user/UserAction.do";
    document.userForm.submit();
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
    var url = "../ajax/user/getinfo.jsp";
    var httploc = getHTTPObject();
    var getstr = "";
    var next_value = escape(document.userForm.nextValue.value);
    var search_value = escape(document.userForm.search.value);
    var clientIndex = document.userForm.clientIndex.value;
    var permissionIndex = "";
    if (document.userForm.permissionIndex.checked)
        permissionIndex = document.userForm.permissionIndex.value;
    getstr += "nextValue=" + next_value;
    getstr += "&next=" + v;
    getstr += "&search=" + search_value;
    getstr += "&clientIndex=" + clientIndex;
    getstr += "&permissionIndex=" + permissionIndex;
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
            }
        }
    };
    httploc.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    httploc.setRequestHeader("Content-length", getstr.length);
    httploc.setRequestHeader("Connection", "close");
    httploc.send(getstr);
    document.getElementById('ajax_cat').innerHTML = "<div><img src='../assets/images/loading.gif' align='absmiddle'/>Please wait. Loading...</div>";
}

function deleteForm(userId, status, id)
{
    var s = "";
    if (eval(status) == 1)
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
            var url = "../ajax/user/getinfo.jsp";
            var getstr = "";
            var httploc = getHTTPObject();
            var next_value = 1;
            if (document.userForm.nextValue)
                next_value = escape(document.userForm.nextValue.value);
            var next_del = "-1";
            if (document.userForm.nextDel)
                next_del = escape(document.userForm.nextDel.value);
            var search_value = escape(document.userForm.search.value);
            var permissionIndex = "";
            if (document.userForm.permissionIndex.checked)
                permissionIndex = document.userForm.permissionIndex.value;
            var clientIndex = document.userForm.clientIndex.value;
            getstr += "nextValue=" + next_value;
            getstr += "&nextDel=" + next_del;
            getstr += "&status=" + status;
            getstr += "&search=" + search_value;
            getstr += "&clientIndex=" + clientIndex;
            getstr += "&permissionIndex=" + permissionIndex;
            getstr += "&deleteVal=" + userId;
            httploc.open("POST", url, true);
            httploc.onreadystatechange = function ()
            {
                if (httploc.readyState == 4)
                {
                    if (httploc.status == 200)
                    {
                        var response = httploc.responseText;
                        var arr = new Array();
                        arr = response.split('##');
                        var v1 = arr[0];
                        var v2 = trim(arr[1]);
                        document.getElementById('ajax_cat').innerHTML = '';
                        document.getElementById('ajax_cat').innerHTML = v1;
                        if (trim(v2) != "")
                        {
                            Swal.fire(v2);
                        }
                    }
                }
            };
            httploc.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
            httploc.setRequestHeader("Content-length", getstr.length);
            httploc.setRequestHeader("Connection", "close");
            httploc.send(getstr);
        } else
        {
            if (document.getElementById("flexSwitchCheckDefault_" + id).checked == true)
                document.getElementById("flexSwitchCheckDefault_" + id).checked = false;
            else
                document.getElementById("flexSwitchCheckDefault_" + id).checked = true;
        }
    })
}

function sortForm(colid, updown)
{
    for (i = 1; i <= 4; i++)
    {
        document.getElementById("img_" + i + "_2").className = "sort_arrow deactive_sort";
        document.getElementById("img_" + i + "_1").className = "sort_arrow deactive_sort";
    }
    if (updown == 2)
    {
        document.getElementById("img_" + colid + "_2").className = "sort_arrow active_sort";

    } else
    {
        document.getElementById("img_" + colid + "_" + updown).className = "sort_arrow active_sort";
    }
    var httploc = getHTTPObject();
    var url_sort = "../ajax/user/sort.jsp";
    var getstr = "";
    var nextValue = 0;
    if (document.userForm.nextValue)
        nextValue = document.userForm.nextValue.value;
    getstr += "nextValue=" + nextValue;
    getstr += "&col=" + colid;
    getstr += "&updown=" + updown;
    httploc.open("POST", url_sort, true);
    httploc.onreadystatechange = function ()
    {
        if (httploc.readyState == 4)
        {
            if (httploc.status == 200)
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

function checkUser()
{
    if (document.userForm.photo && document.userForm.photo.value != "")
    {
        if (!(document.userForm.photo.value).match(/(\.(png)|(jpg)|(jpeg))$/i))
        {
            Swal.fire({
                title: "Only .jpg, .jpeg, .png are allowed.",
                didClose: () => {
                    document.userForm.photo.focus();
                }
            })
            return false;
        }
        var input = document.getElementById('upload1');
        if (input.files)
        {
            var file = input.files[0];
            if (file.size > 1024 * 1024 * 5)
            {
                Swal.fire({
                    title: "File size should not exceed 5 MB.",
                    didClose: () => {
                        document.userForm.photo.focus();
                    }
                })
                return false;
            }
        }
    }   
    if (document.userForm.name.value == "")
    {
        Swal.fire({
            title: "Please enter name.",
            didClose: () => {
                document.userForm.name.focus();
            }
        })
        return false;
    }
    if (document.userForm.contact1.value == "")
    {
        Swal.fire({
            title: "Please enter contact number 1.",
            didClose: () => {
                document.userForm.contact1.focus();
            }
        })
        return false;
    }
    if (trim(document.userForm.email.value) == "")
    {
        Swal.fire({
            title: "Please enter email id.",
            didClose: () => {
                document.userForm.email.focus();
            }
        })
        return false;
    }
    if (checkEmailAddress(document.userForm.email) == false)
    {
        Swal.fire({
            title: "Please enter valid email id.",
            didClose: () => {
                document.userForm.email.focus();
            }
        })
        return false;
    }
    if (trim(document.userForm.userName.value) == "")
    {
        Swal.fire({
            title: "Please enter username.",
            didClose: () => {
                document.userForm.userName.focus();
            }
        })
        return false;
    }
    if (validdesc(document.userForm.userName) == false)
    {
        document.userForm.userName.focus();
        return false;
    }
    if (trim(document.userForm.code.value) == "")
    {
        Swal.fire({
            title: "Please enter employee code.",
            didClose: () => {
                document.userForm.code.focus();
            }
        })
        return false;
    }    
    if(document.userForm.cassessor.checked == true)
    {
        if(document.userForm.cvfile.value == "" &&  document.userForm.cvHidden.value == "" )
        {
            if (document.userForm.cvfile.value == "")
            {
                 Swal.fire({
                    title: "Please upload CV.",
                    didClose: () => {
                        document.userForm.cvfile.focus();
                    }
                })
                return false;
            }
        }
        if (document.userForm.cvfile.value != "")
        {                
            if (!(document.userForm.cvfile.value).match(/(\.(pdf))$/i))            
            {

                Swal.fire({
                    title: "Only .pdf file allowed.",
                    didClose: () => {
                        document.userForm.cvfile.focus();
                    }
                })
                return false;
            }
        }
    }    
    return true;
}

function submitUserForm()
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
    if (checkUser())
    {
        document.userForm.doSaveUser.value = "yes";
        document.userForm.doCancel.value = "no";
        document.userForm.action = "../user/UserAction.do";
        document.userForm.submit();
        document.getElementById('submitdiv').innerHTML = "<div><img src='../assets/images/loading.gif' align='absmiddle'/>Please wait. Loading...</div>";
    }
}

function resetUserForm()
{
    document.userForm.reset();
}

function checkPageAjax()
{
    if (document.userForm.page.value == "" || document.userForm.page.value == "Page No" ||
            isNaN(document.userForm.page.value) ||
            (!isInteger(document.userForm.page.value)) ||
            eval(document.userForm.page.value) <= 0)
    {
        Swal.fire({
            title: "Please enter only positive numeric value.",
            didClose: () => {
                document.userForm.page.focus();
            }
        })
        return false;
    }
    if (document.userForm.page.value > document.userForm.totalpage.value)
    {
        Swal.fire({
            title: 'Search page between one to total page i.e. ' + document.userForm.totalpage.value + '.',
            didClose: () => {
                document.userForm.page.focus();
            }
        })
        return false;
    }
    return true;
}

function goback()
{
    if (document.userForm.doSaveUser)
        document.userForm.doSaveUser.value = "no";
    document.userForm.doCancel.value = "yes";
    document.userForm.action = "../user/UserAction.do";
    document.userForm.submit();
}

function selectSection(id, size)
{
    if (document.getElementById("sec_" + id).checked == true)
    {
        for (var i = 1; i <= size; i++)
            document.getElementById("subSec" + id + "_" + i).checked = true;
    } else
    {
        for (var j = 1; j <= size; j++)
            document.getElementById("subSec" + id + "_" + j).checked = false;
    }

}

function selectAllModule(id, size, val)
{
    if (val == 1)
    {
        if (document.getElementById("add_" + id).checked == true)
        {
            for (i = 0; i < size; i++)
                document.getElementById("add_" + id + "_" + i).checked = true;
        } 
        else
        {
            for (j = 0; j < size; j++)
                document.getElementById("add_" + id + "_" + j).checked = false;
        }
    }
    if (val == 2)
    {
        if (document.getElementById("edit_" + id).checked == true)
        {
            for (i = 0; i < size; i++)
                document.getElementById("edit_" + id + "_" + i).checked = true;
        } else
        {
            for (j = 0; j < size; j++)
                document.getElementById("edit_" + id + "_" + j).checked = false;
        }
    }
    if (val == 3)
    {
        if (document.getElementById("delete_" + id).checked == true)
        {
            for (i = 0; i < size; i++)
                document.getElementById("delete_" + id + "_" + i).checked = true;

        } else
        {
            for (j = 0; j < size; j++)
                document.getElementById("delete_" + id + "_" + j).checked = false;
        }
    }
    if (val == 4)
    {
        if (document.getElementById("approve_" + id).checked == true)
        {
            for (i = 0; i < size; i++)
                document.getElementById("approve_" + id + "_" + i).checked = true;

        } else
        {
            for (j = 0; j < size; j++)
                document.getElementById("approve_" + id + "_" + j).checked = false;
        }
    }
    if (val == 5)
    {
        if (document.getElementById("adminapprove_" + id).checked == true)
        {
            for (i = 0; i < size; i++)
                document.getElementById("adminapprove_" + id + "_" + i).checked = true;

        } else
        {
            for (j = 0; j < size; j++)
                document.getElementById("adminapprove_" + id + "_" + j).checked = false;
        }
    }
    if ((val === "1") || (val === "2") || (val === "3") || (val === "4") || (val === "5") || (val === "6"))
    {
        if ((val === "1") || (val === "2") || (val === "3") || (val === "4") || (val === "5"))
        {
            if (document.getElementById("add_" + id).checked === true || document.getElementById("edit_" + id).checked === true ||
                    document.getElementById("delete_" + id).checked === true || document.getElementById("approve_" + id).checked === true)
            {
                document.getElementById("view_" + id).checked = true;
            } else
            {
                document.getElementById("view_" + id).checked = false;
            }
            if (document.getElementById("view_" + id).checked === true)
            {
                for (i = 0; i < size; i++)
                    document.getElementById("view_" + id + "_" + i).checked = true;
            } else
            {
                for (i = 0; i < size; i++)
                    document.getElementById("view_" + id + "_" + i).checked = false;
            }
        } else if (val === "6")
        {
            if (document.getElementById("view_" + id).checked === true)
            {
                for (i = 0; i < size; i++)
                    document.getElementById("view_" + id + "_" + i).checked = true;
            } else
            {
                if (document.getElementById("add_" + id).checked === true || document.getElementById("edit_" + id).checked === true ||
                        document.getElementById("delete_" + id).checked === true || document.getElementById("approve_" + id).checked === true)
                {
                    Swal.fire("You cannot deselect it.");
                    document.getElementById("view_" + id).checked = true;
                } else
                {
                    for (j = 0; j < size; j++)
                    {
                        if (document.getElementById("add_" + id + "_" + j).checked === false && document.getElementById("edit_" + id + "_" + j).checked === false &&
                                document.getElementById("delete_" + id + "_" + j).checked === false && document.getElementById("approve_" + id + "_" + j).checked === false)
                        {
                            document.getElementById("view_" + id + "_" + j).checked = false;
                        }
                    }
                }

            }
        }
    }
}

function getSelectedPermission(id, size, val, incr)
{
    var x = 0;
    if (val === "1")
    {
        for (i = 0; i < size; i++)
        {
            if (document.getElementById("add_" + id + "_" + i).checked === true)
            {
                x++;
            }
        }
        if (x === size)
            document.getElementById("add_" + id).checked = true;
        else
        {
            document.getElementById("add_" + id).checked = false;
        }
    } else if (val === "2")
    {
        for (i = 0; i < size; i++)
        {
            if (document.getElementById("edit_" + id + "_" + i).checked === true)
            {
                x++;
            }
        }
        if (x === size)
            document.getElementById("edit_" + id).checked = true;
        else
        {
            document.getElementById("edit_" + id).checked = false;
        }
    } else if (val === "3")
    {
        for (i = 0; i < size; i++)
        {
            if (document.getElementById("delete_" + id + "_" + i).checked === true)
            {
                x++;
            }
        }
        if (x === size)
            document.getElementById("delete_" + id).checked = true;
        else
        {
            document.getElementById("delete_" + id).checked = false;
        }
    } else if (val === "4")
    {
        for (i = 0; i < size; i++)
        {
            if (document.getElementById("approve_" + id + "_" + i).checked === true)
            {
                x++;
            }
        }
        if (x == size)
        {
            document.getElementById("approve_" + id).checked = true;
        } else
        {
            document.getElementById("approve_" + id).checked = false;
        }
    }
    if ((val === "1") || (val === "2") || (val === "3") || (val === "4") || (val === "5") || (val === "6"))
    {
        var y = 0;
        if ((val === "1") || (val === "2") || (val === "3") || (val === "4") || (val === "5"))
        {
            for (i = 0; i < size; i++)
            {
                if (i == incr)
                {
                    if (document.getElementById("add_" + id + "_" + i).checked === true || document.getElementById("edit_" + id + "_" + i).checked === true ||
                            document.getElementById("delete_" + id + "_" + i).checked === true || document.getElementById("approve_" + id + "_" + i).checked === true)
                    {
                        document.getElementById("view_" + id + "_" + i).checked = true;
                    } else
                    {
                        document.getElementById("view_" + id + "_" + i).checked = false;
                    }
                }
                if (document.getElementById("view_" + id + "_" + i).checked === true)
                {
                    y++;
                }
            }
        } else if (val === "6")
        {
            for (i = 0; i < size; i++)
            {
                if (document.getElementById("add_" + id + "_" + i).checked === true || document.getElementById("edit_" + id + "_" + i).checked === true ||
                        document.getElementById("delete_" + id + "_" + i).checked === true || document.getElementById("approve_" + id + "_" + i).checked === true)
                {
                    if (document.getElementById("view_" + id + "_" + i).checked === false)
                        Swal.fire("You cannot deselect it.");
                    document.getElementById("view_" + id + "_" + i).checked = true;
                }
                if (document.getElementById("view_" + id + "_" + i).checked === true)
                {
                    y++;
                }
            }
        }
        if (y == size)
            document.getElementById("view_" + id).checked = true;
        else
        {
            document.getElementById("view_" + id).checked = false;
        }
    }
}

function submitNewPassword()
{
    if (checkNewPassword())
    {
        var url_sort = "../ajax/user/setnewpassword.jsp";
        var https = getHTTPObject();
        var getstr = "password=" + document.userForm.password.value;
        https.open("POST", url_sort, true);
        https.onreadystatechange = function ()
        {
            if (https.readyState == 4)
            {
                if (https.status == 200)
                {
                    var response = https.responseText;
                    var val = response;
                    if (trim(val) == 'S')
                    {
                        window.location.href = "../dashboard/DashboardAction.do?doDashboard=yes";
                    } else
                    {
                        Swal.fire("There is some issue for updating your password.\nPlease contact administrator.");
                    }
                }
            }
        };
        https.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
        https.setRequestHeader("Content-length", getstr.length);
        https.setRequestHeader("Connection", "close");
        https.send(getstr);
    }
}

function checkNewPassword()
{
    if (document.userForm.password.value == "")
    {
        Swal.fire({
            title: "Please enter password.",
            didClose: () => {
                document.userForm.password.focus();
            }
        })
        return false;
    }
    if (isValidPassword(document.userForm.password.value) == false)
    {
        return false;
    }
    if (document.userForm.confirmPassword.value == "")
    {
        Swal.fire({
            title: "Please enter confirm password.",
            didClose: () => {
                document.userForm.confirmPassword.focus();
            }
        })
        return false;
    }
    if (isValidPassword(document.userForm.confirmPassword.value) == false)
    {
        return false;
    }

    if (document.userForm.password.value != document.userForm.confirmPassword.value)
    {
        Swal.fire({
            title: "Password and confirm password should be equal.",
            didClose: () => {
                document.userForm.confirmPassword.focus();
            }
        })
        return false;
    }
    return true;
}

function resetPassword()
{
    var agree = confirm("This will reset the user password and an email will be sent to the user.\n Do you wish to reset?");
    if (agree)
    {
        var userId = document.userForm.userId.value;
        var name = document.userForm.name.value;
        var email = document.userForm.email.value;
        if (eval(userId) > 0)
        {
            var url = "../ajax/user/resetpassword.jsp";
            var httpinst = getHTTPObject();
            var getstr = "userId=" + userId;
            getstr += "&name=" + escape(name);
            getstr += "&email=" + escape(email);
            httpinst.open("POST", url, true);
            httpinst.onreadystatechange = function ()
            {
                if (httpinst.readyState == 4)
                {
                    if (httpinst.status == 200)
                    {
                        var response = httpinst.responseText;
                        Swal.fire(trim(response));
                        document.getElementById('resetDiv').innerHTML = "<a href='javascript:resetPassword();' type='submit' class='btn btn-transparent green btn-outline btn-circle btn-sm active'>Reset Password</a>";
                    }
                }
            };
            httpinst.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
            httpinst.setRequestHeader("Content-length", getstr.length);
            httpinst.setRequestHeader("Connection", "close");
            httpinst.send(getstr);
            document.getElementById('resetDiv').innerHTML = "<div><img src='../assets/images/loading.gif' align='absmiddle'/>Please wait. Loading...</div>";
        } else
        {
            Swal.fire("There are some issue, try again later.");
        }
    }
}

function submitpersonaliseForm()
{
    if (checkInfoPassword())
    {
        var url = "../ajax/changepassword.jsp";
        var http = getHTTPObject();
        var oldPassword = document.userForm.chpassword.value;
        var newPassword = document.userForm.chnewpassword.value;
        var topconfirmPassword = document.userForm.chnewpasswordconfirm.value;
        var getstr = "oldPassword=" + oldPassword + "&";
        getstr += "&newPassword=" + newPassword + "&";
        getstr += "&topconfirmPassword=" + topconfirmPassword;
        http.open("POST", url, true);
        http.onreadystatechange = function ()
        {
            if (http.readyState == 4)
            {
                if (http.status == 200)
                {
                    var response = http.responseText;
                    var val = response;
                    if (trim(val) == 'N')
                    {
                        Swal.fire({
                            title: "Your current password is incorrect.",
                            didClose: () => {
                                document.userForm.oldPassword.focus();
                                DrawCaptcha();
                            }
                        })
                        return false;
                    } else
                    {
                        Swal.fire("Your password has been changed successfully.");
                        document.userForm.chpassword.value = "";
                        document.userForm.chnewpassword.value = "";
                        document.userForm.chnewpasswordconfirm.value = "";
                        document.userForm.action = "/jxp/dashboard/DashboardAction.do?doDashboard=yes";
                        document.userForm.submit();
                    }
                }
            }
        };
        http.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
        http.setRequestHeader("Content-length", getstr.length);
        http.setRequestHeader("Connection", "close");
        http.send(getstr);
    }
}

function checkInfoPassword()
{
    if (trim(document.userForm.chpassword.value) == '')
    {
        Swal.fire({
            title: "Please enter current password.",
            didClose: () => {
                document.userForm.chpassword.focus();
            }
        })
        return false;
    }
    if (trim(document.userForm.chnewpassword.value) == '')
    {
        Swal.fire({
            title: "Please enter new password.",
            didClose: () => {
                ocument.userForm.chnewpassword.focus();
            }
        })
        return false;
    }
    if (trim(document.userForm.chpassword.value) == document.userForm.chnewpassword.value)
    {
        Swal.fire({
            title: "Current password and new password cannot be same.",
            didClose: () => {
                document.userForm.chnewpassword.focus();
            }
        })
        return false;
    }
    if (trim(document.userForm.chnewpassword.value) != document.userForm.chnewpasswordconfirm.value)
    {
        Swal.fire({
            title: "New password and confirm new password should be same.",
            didClose: () => {
                document.userForm.chnewpasswordconfirm.focus();
            }
        })
        return false;
    }
    if (document.userForm.cap)
    {
        if (document.userForm.cap.value == "")
        {
            Swal.fire("Please enter verification code.");
            return false;
        }
        if (removeSpaces(document.userForm.txtCaptcha.value) != removeSpaces(document.userForm.cap.value))
        {
            Swal.fire({
                title: "Captcha does not match.",
                didClose: () => {
                    DrawCaptcha();
                    document.userForm.cap.value = "";
                    document.userForm.cap.focus();
                }
            })
            return false;
        }
    }
    return true;
}

function removeSpaces(string)
{
    return string.split(' ').join('');
}

function setCountryDDL()
{
    var url = "../ajax/user/getcountry.jsp";
    var httploc = getHTTPObject();
    var getstr = "clientId=" + document.userForm.clientId.value;
    httploc.open("POST", url, true);
    httploc.onreadystatechange = function ()
    {
        if (httploc.readyState == 4)
        {
            if (httploc.status == 200)
            {
                var response = httploc.responseText;
                document.getElementById("countryddl").innerHTML = '';
                document.getElementById("countryddl").innerHTML = response;
                setAssetDDL();                
            }
        }
    };
    httploc.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    httploc.setRequestHeader("Content-length", getstr.length);
    httploc.setRequestHeader("Connection", "close");
    httploc.send(getstr);
}

function setAssetDDL()
{
    var url = "../ajax/user/getasset.jsp";
    var httploc = getHTTPObject();
    var getstr = "clientId=" + document.userForm.clientId.value;
    getstr += "&countryId=" + document.userForm.countryId.value;
    httploc.open("POST", url, true);
    httploc.onreadystatechange = function ()
    {
        if (httploc.readyState == 4)
        {
            if (httploc.status == 200)
            {
                var response = httploc.responseText;
                document.getElementById("assetdiv").innerHTML = '';
                document.getElementById("assetdiv").innerHTML = response;
                $('#multiselect_dd').multiselect({
                    includeSelectAllOption: true,
                    //maxHeight: 400,
                    dropUp: true,
                    nonSelectedText: '- Select -',
                    maxHeight: 200,
                    enableFiltering: false,
                    enableCaseInsensitiveFiltering: false,
                    buttonWidth: '100%'
                });
            }
        }
    };
    httploc.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    httploc.setRequestHeader("Content-length", getstr.length);
    httploc.setRequestHeader("Connection", "close");
    httploc.send(getstr);
}

function checkList()
{
    if (document.userForm.clientId.value == "-1")
    {
        Swal.fire({
            title: "Please select client.",
            didClose: () => {
                document.userForm.clientId.focus();
            }
        })
        return false;
    }
    var assets = $("#multiselect_dd").val();
    if (assets == null || assets == "")
    {
        Swal.fire({
            title: "Please select assets.",
            didClose: () => {
                document.userForm.assetId.focus();
            }
        })
        return false;
    }
    if(document.userForm.cassessor.checked == true)
    {
            var positions = $("#position_rank").val();
            if (positions == null || positions == "")
            {
                Swal.fire({
                    title: "Please select Position-Rank.",
                    didClose: () => {
                        document.userForm.positionRank.focus();
                    }
                })
                return false;
            }
    }
    return true;
}

function addtolist()
{
    if (checkList())
    {
        var url = "../ajax/user/list.jsp";
        var httploc = getHTTPObject();         
        var cassessor = 0;
        if(document.userForm.cassessor.checked)
            cassessor = 1;      
        var clientId = document.getElementById("clientId");
        var clientName = clientId.options[clientId.selectedIndex].text;
        var countryId = document.getElementById("countryddl");
        var countryName = countryId.options[countryId.selectedIndex].text;
        var assets = $("#multiselect_dd").val();
        var assets_text = $('#multiselect_dd option:selected').toArray().map(item => item.text).join();
        var positions = $("#position_rank").val();
        var positions_text = $('#position_rank option:selected').toArray().map(item => item.text).join();
        var getstr = "";
        getstr += "clientId=" + document.userForm.clientId.value;
        getstr += "&clientName=" + escape(clientName);
        getstr += "&countryId=" + document.userForm.countryId.value;
        getstr += "&countryName=" + escape(countryName);
        getstr += "&assets=" + escape(assets);
        getstr += "&assets_text=" + escape(assets_text);
        getstr += "&positions=" + escape(positions);
        getstr += "&positions_text=" + escape(positions_text);
        getstr += "&action=add";
        getstr += "&cassessor="+cassessor;
        httploc.open("POST", url, true);
        httploc.onreadystatechange = function ()
        {
            if (httploc.readyState == 4)
            {
                if (httploc.status == 200)
                {
                    var response = httploc.responseText;
                    document.getElementById('ajax_cat').innerHTML = response;
                    document.userForm.clientId.value = "-1";
                    setCountryDDL();
                    document.userForm.countryId.value = "-1";
                    setPositionRank();
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

function delfromlist(id)
{
    var url = "../ajax/user/list.jsp";
    var httploc = getHTTPObject();
    var cassessor = 0;
    if(document.userForm.cassessor.checked)
        cassessor = 1;
    var getstr = "";
    getstr += "id=" + id;
    getstr += "&action=delete";
    getstr += "&cassessor="+cassessor;
    httploc.open("POST", url, true);
    httploc.onreadystatechange = function ()
    {
        if (httploc.readyState == 4)
        {
            if (httploc.status == 200)
            {
                var response = httploc.responseText;
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

function delall()
{
    var url = "../ajax/user/list.jsp";
    var httploc = getHTTPObject();
    var cassessor = 0;
    if(document.userForm.cassessor.checked)
            cassessor = 1;
    var getstr = "";
    getstr += "&action=deleteall";
    getstr += "&cassessor="+cassessor;
    httploc.open("POST", url, true);
    httploc.onreadystatechange = function ()
    {
        if (httploc.readyState == 4)
        {
            if (httploc.status == 200)
            {
                var response = httploc.responseText;
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

function addalledit()
{
    var url = "../ajax/user/list.jsp";
    var httploc = getHTTPObject();
    var getstr = "";
    var cassessor = 0;
    if(document.userForm.cassessor.checked)
    {
        cassessor = 1;
        document.getElementById("prid").style.display = "";
    }
    else
        document.getElementById("prid").style.display = "none";
    getstr += "&action=onload";
    getstr += "&cassessor="+cassessor;
    httploc.open("POST", url, true);
    httploc.onreadystatechange = function ()
    {
        if (httploc.readyState == 4)
        {
            if (httploc.status == 200)
            {
                var response = httploc.responseText;
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

function exporttoexcel()
{
    document.userForm.action = "../user/UserExportAction.do";
    document.userForm.submit();
}

function setUserClass(tp)
{
    if (!(document.userForm.photo.value).match(/(\.(png)|(jpg)|(jpeg))$/i))
    {
        Swal.fire({
            title: "Only .jpg, .jpeg, .png are allowed.",
            didClose: () => {
                document.userForm.photo.focus();
            }
        })
    }
    if (document.userForm.photo.value != "")
    {
        var input = document.userForm.photo;
        if (input.files)
        {
            var file = input.files[0];
            if (file.size > (1024 * 1024 * 5))
            {
                Swal.fire({
                    title: "File size should not exceed 5 MB.",
                    didClose: () => {
                        document.userForm.photo.focus();
                    }
                })
            } else
            {
                var filesSelected = document.getElementById("upload1").files;
                if (filesSelected.length > 0)
                {
                    var fileToLoad = filesSelected[0];
                    var fileReader = new FileReader();
                    fileReader.onload = function (fileLoadedEvent)
                    {
                        var srcData = fileLoadedEvent.target.result; // <--- data: base64
                        document.getElementById("l").src = srcData;
                    }
                    fileReader.readAsDataURL(fileToLoad);
                }
            }
        }
    }
}

function setFlag(tp)
{
    if (eval(tp) == 1)
    {
        if (document.userForm.coordinator.checked == true)
        {
            document.userForm.assessor.checked = false;
            document.userForm.permission.checked = false;
            showhide();
        }
    } else
    {
        if (document.userForm.assessor.checked == true) 
        {
            document.userForm.coordinator.checked = false;
            document.userForm.permission.checked = false;
            showhide();
        }
    }
}


function setFlag2(tp)
{
    if (eval(tp) == 1)
    {
        if (document.userForm.isManager.checked == true)
        {
            document.userForm.isRecruiter.checked = false;
        }
    } else
    {
        if (document.userForm.isRecruiter.checked == true) 
        {
            document.userForm.isManager.checked = false;
        }
    }
}

function setClass(tp)
{
    document.getElementById("upload_link_" + tp).className="attache_btn uploaded_img";
}

function showhide()
{
    if (document.userForm.permission.checked)
    {
        document.getElementById('all_permission').style.display = "none";
        document.userForm.assessor.checked = false;
        document.userForm.cassessor.checked = false;
        document.userForm.coordinator.checked = false;
    } else
    {
        document.getElementById('all_permission').style.display = "";
    }
}

function showhideclient()
{
    if (document.userForm.allclient.checked)
    {
        document.getElementById('all_client').style.display = "none";
        document.userForm.cassessor.checked = false;
        document.getElementById('show_cassessor').style.display = "none";
        document.getElementById('ca_cv').style.display = "none";
        document.getElementById("prid").style.display = "none";
        delall();
    } else
    {
        document.getElementById('all_client').style.display = "";
    }
}

function showhidecassessor()
{
    if (document.userForm.cassessor.checked)
    {
        document.userForm.permission.checked = false;
        document.userForm.allclient.checked = false;
        document.getElementById('show_cassessor').style.display = "";
        document.getElementById('ca_cv').style.display = "";
        document.getElementById("prid").style.display = "";
        showhide();
        showhideclient();
    } 
    else
    {
        document.getElementById('show_cassessor').style.display = "none";
        document.getElementById('ca_cv').style.display = "none";
        document.getElementById("prid").style.display = "none";
        showhide();
        showhideclient();
    }
    addalledit();
}

function setPositionRank()
{
    var assets = $("#multiselect_dd").val();
    if(assets != "")
    {
        var url = "../ajax/user/getpositiorank.jsp";
        var getstr = "assetId=" +  assets;
        var httploc = getHTTPObject();
        httploc.open("POST", url, true);
        httploc.onreadystatechange = function ()
        {
            if (httploc.readyState == 4)
            {
                if (httploc.status == 200)
                {
                    var response = httploc.responseText;
                    document.getElementById('positionrankdiv').innerHTML = '';
                    document.getElementById('positionrankdiv').innerHTML = response;
                    $('#position_rank').multiselect({
                        includeSelectAllOption: true,
                        dropUp: true,
                        nonSelectedText: 'Select Position-Rank',
                        maxHeight: 200,
                        enableFiltering: false,
                        enableCaseInsensitiveFiltering: false,
                        buttonWidth: '100%'
                    });
                }
            }
        };
        httploc.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
        httploc.send(getstr);
    }
}

function seturl(url)
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