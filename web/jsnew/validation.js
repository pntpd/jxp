    function fnValidatePAN(Obj)
    {
        if (Obj.value != "")
        {
            var ObjVal = Obj.value;
            var panPat = /^([A-Z]{5})(\d{4})([A-Z]{1})$/;
            if (ObjVal.search(panPat) == -1)
            {
                Swal.fire({
            title: "Invalid Pan No.\n Please enter pan no in 'AAAAA0000A' format..",
            didClose:() => {
              field.focus();
            }
            }) 
            return false;
        }
            }
        return true;
    }
    
    function validname(field)
    {
        var re = /^[a-z0-9,\.\'\- ]+$/i;
        if (!field.value.match(re))
        {
        Swal.fire({
        title: "Only a-z 0-9 , . - and single quote allowed.",
        didClose:() => {
          field.focus();
        }
        }) 
        return false;
    }
        return true;
    }
    
    function validnum(field)
    {
        var re = /^[0-9]+$/i;
        if (!field.value.match(re))
        {
            Swal.fire({
            title: "Only numbers allowed.",
            didClose:() => {
              field.focus();
            }
            }) 
            return false;
        }
        return true;
    }
    
    function validdouble(field)
    {
        var re = /^[0-9\.]+$/i;
        if (!field.value.match(re))
        {
        Swal.fire({
        title: "Only numbers and dot allowed.",
        didClose:() => {
          field.focus();
        }
        }) 
        return false;
    }
        return true;
    }
    
    function validnamenum(field)
    {
        var re = /^[a-z0-9\.\-\s]+$/i;
        if (!field.value.match(re))
        {
        Swal.fire({
        title: "Only a-z 0-9 . - and single space allowed.",
        didClose:() => {
          field.focus();
        }
        }) 
        return false;
    }
        return true;
    }
    
    function validdesc(field)
    {
        var re = /^[a-z0-9\.,;:=$\-_\+\'\"/\\!@`#%&\*()#\?\n\r\\? ]+$/i;
        if (!field.value.match(re))
        {
        Swal.fire({
        title: "Only a-z0-9\.,;:=$\^\-_\+\'\"/\!@`#%&\*()\n\r are allowed.",
        didClose:() => {
          field.focus();
        }
        }) 
        return false;
    }
        return true;
    }
    
    function validdescsearch(field)
    {
        var re = /^[a-z0-9\.,;:=$\-_\+\'\"/\\!@`#%&\*()#\?\n\r\\? ]+$/i;
        if (!field.value.match(re))
        {
        Swal.fire({
        title: "Only alphanumeric characters are allowed.",
        didClose:() => {
          field.focus();
        }
        }) 
        return false;
    }
        return true;
    }
    
    function validdeschtml(field)
    {
        var re = /^[a-z0-9\.,;=$\-_\+\'\"/\\!@`#%&\*()#\?:<>\n\r ]+$/i;
        if (!field.value.match(re))
        {
            Swal.fire({
        title: "Only a-z0-9\.,;=$\-_\+\'\"/\!@`#%&\*()\n\r<> are allowed.",
        didClose:() => {
          field.focus();
        }
        }) 
        return false;
    }
        return true;
    }
    
    function validreg(field)
    {
        var re = /^[a-z0-9\-/\\]+$/i;
        if (!field.value.match(re))
        {
            Swal.fire({
            title: "Only a-z 0-9. - and single quote allowed.",
            didClose:() => {
              field.focus();
            }
            }) 
            return false;
        }
        return true;
    }
    
    function validnamewithand(field)
    {
        var re = /^[a-z\.\'\-& ]+$/i;
        if (!field.value.match(re))
        {
        Swal.fire({
                title: "Only a-z . - and single quote allowed.",
                didClose:() => {
                  field.focus();
                }
                }) 
                return false;
            }
        return true;
    }
    
    function validtime(field)
    {
        var re = /^[0-9AMP\-: ]+$/i;
        if (!field.value.match(re))
        {
             Swal.fire({
                title: "Only 0-9 - : AM PM allowed.",
                didClose:() => {
                  field.focus();
                }
                }) 
                return false;
            }
        return true;
    }
    
    function validnumnegative(field)
    {
        var re = /^[0-9\-]+$/i;
        if (!field.value.match(re))
        {
        Swal.fire({
                title: "Only numbers allowed.",
                didClose:() => {
                  field.focus();
                }
                }) 
                return false;
            }
        return true;
    }
    
    function validdate(field)
    {
        var re = /^[0-9A-Z\-]+$/i;
        if (!field.value.match(re))
        {
        Swal.fire({
                title: "Only 0-9 A-Z - allowed.",
                didClose:() => {
                  field.focus();
                }
                }) 
                return false;
            }
        return true;
    }
    
     function validnamenumwithspace(field)
    {
        var re = /^[a-z0-9\s]+$/i;
        if (!field.value.match(re))
        {
         Swal.fire({
                title: "Only a-z0-9  - space and single quote allowed.",
                didClose:() => {
                  field.focus();
                }
                }) 
                return false;
            }
        return true;
    }
       function validnameonlycomma(field)
    {
        var re = /^[a-z0-9,]+$/i;
        if (!field.value.match(re))
        {
        Swal.fire({
        title: "Only a-z 0-9 , allowed.",
        didClose:() => {
          field.focus();
        }
        }) 
        return false;
    }
        return true;
    }