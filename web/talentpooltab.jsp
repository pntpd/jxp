<html:hidden property="candidateId"/>
<html:hidden property="doSave"/>
<html:hidden property="doModify"/>
<html:hidden property="doCancel"/>
<html:hidden property="doView"/>
<html:hidden property="doAdd"/>
<html:hidden property="status"/>
<html:hidden property="search"/>

<html:hidden property="clientIndex"/>
<html:hidden property="assetIndex"/>
<html:hidden property="positionIndexId"/>
<html:hidden property="locationIndex"/>
<html:hidden property="employementstatus"/>

<html:hidden property="doViewBanklist"/>
<html:hidden property="doViewlangdetail"/>
<html:hidden property="doViewhealthdetail"/>
<html:hidden property="doViewvaccinationlist"/>
<html:hidden property="doViewgovdocumentlist"/>
<html:hidden property="doViewtrainingcertlist"/>
<html:hidden property="doVieweducationlist"/>
<html:hidden property="doViewexperiencelist"/>

<html:hidden property="doViewVerificationlist"/>
<html:hidden property="doViewAssessmentlist"/>
<html:hidden property="doViewCompliancelist"/>
<html:hidden property="doViewShortlistinglist"/>
<html:hidden property="doViewClientselectionlist"/>
<html:hidden property="doViewOnboardinglist"/>
<html:hidden property="doViewMobilizationlist"/>
<html:hidden property="doViewRotationlist"/>
<html:hidden property="doViewNotificationlist"/>
<html:hidden property="doViewTransferterminationlist"/>
<html:hidden property="doViewWellnessfeedbacklist"/>
<html:hidden property="doViewCompetencylist"/>
<html:hidden property="doViewoffshoredetail"/>
<html:hidden property="doViewNomineelist"/>
<html:hidden property="doViewPpelist"/>
<html:hidden property="doViewContractlist"/>
<html:hidden property="doViewAppraisallist"/>
<html:hidden property="doViewDayratelist"/>


<ul class='nav nav-tabs nav-tabs-custom ' id='tab_menu'>

    <li id='list_menu1' class='list_menu1 nav-item <%=(ctp == 1) ? " active" : ""%>'>
        <a class='nav-link' href="javascript: view1();">
            <span class='d-none d-md-block'>Personal</span><span class='d-block d-md-none'><i class='mdi mdi-home-variant h5'></i></span>
        </a>
    </li>    
    <li id='list_menu6' class='list_menu6 nav-item <%=(ctp == 6) ? " active" : ""%>'>
        <a class='nav-link' href="javascript: openTab('9');">
            <span class='d-none d-md-block'>Education</span><span class='d-block d-md-none'><i class='mdi mdi-account h5'></i></span>
        </a>
    </li>
    <li id='list_menu5' class='list_menu5 nav-item <%=(ctp == 5) ? " active" : ""%>'>
        <a class='nav-link' href="javascript: openTab('10');">
            <span class='d-none d-md-block'>Experience</span><span class='d-block d-md-none'><i class='mdi mdi-account h5'></i></span>
        </a>
    </li>
    <li id='list_menu4' class='list_menu4 nav-item <%=(ctp == 4) ? " active" : ""%>'>
        <a class='nav-link' href="javascript: openTab('5');">
            <span class='d-none d-md-block'>Vaccination</span><span class='d-block d-md-none'><i class='mdi mdi-account h5'></i></span>
        </a>
    </li>
    <li id='list_menu10' class='list_menu10 nav-item <%=(ctp == 10) ? " active" : ""%>'>
        <a class='nav-link' href="javascript: openTab('6');">
            <span class='d-none d-md-block'>Documents</span><span class='d-block d-md-none'><i class='mdi mdi-account h5'></i></span>
        </a>
    </li>
    <li id='list_menu7' class='list_menu7 nav-item <%=(ctp == 7) ? " active" : ""%>'>
        <a class='nav-link' href="javascript: openTab('8');">
            <span class='d-none d-md-block'>Certifications</span><span class='d-block d-md-none'><i class='mdi mdi-account h5'></i></span>
        </a>
    </li>
    <li id='list_menu3' class='list_menu3 nav-item <%=(ctp == 3) ? " active" : ""%>'>
        <a class='nav-link' href="javascript: openTab('4');">
            <span class='d-none d-md-block'>Health</span><span class='d-block d-md-none'><i class='mdi mdi-account h5'></i></span>
        </a>
    </li>
    <li id='list_menu2' class='list_menu2 nav-item <%=(ctp == 2) ? " active" : ""%>'>
        <a class='nav-link' href="javascript: openTab('2');">
            <span class='d-none d-md-block'>Languages</span><span class='d-block d-md-none'><i class='mdi mdi-account h5'></i></span>
        </a>
    </li>
    <li id='list_menu9' class='list_menu9 nav-item <%=(ctp == 9) ? " active" : ""%>'>
        <a class='nav-link' href="javascript: openTab('3');">
            <span class='d-none d-md-block'>Bank Details</span><span class='d-block d-md-none'><i class='mdi mdi-account h5'></i></span>
        </a>
    </li>   
    <li id='list_menu3' class='list_menu15 nav-item <%=(ctp == 23) ? " active" : ""%>'>
        <a class='nav-link' href="javascript: openTab('23');">
            <span class='d-none d-md-block'>Remarks</span><span class='d-block d-md-none'><i class='mdi mdi-account h5'></i></span>
        </a>
    </li>
    <li id='list_menu3' class='list_menu15 nav-item <%=(ctp == 24) ? " active" : ""%>'>
        <a class='nav-link' href="javascript: openTab('24');">
            <span class='d-none d-md-block'>Nominee</span><span class='d-block d-md-none'><i class='mdi mdi-account h5'></i></span>
        </a>
    </li> 
    <li id='list_menu3' class='list_menu15 nav-item <%=(ctp == 26) ? " active" : ""%>'>
        <a class='nav-link' href="javascript: openTab('26');">
            <span class='d-none d-md-block'>Contract</span><span class='d-block d-md-none'><i class='mdi mdi-account h5'></i></span>
        </a>
    </li>
    <li id='list_menu3' class='list_menu15 nav-item <%=(ctp == 25) ? " active" : ""%>'>
        <a class='nav-link' href="javascript: openTab('25');">
            <span class='d-none d-md-block'>PPE</span><span class='d-block d-md-none'><i class='mdi mdi-account h5'></i></span>
        </a>
    </li>    
    <li id='list_menu3' class='list_menu15 nav-item <%=(ctp == 27) ? " active" : ""%>'>
        <a class='nav-link' href="javascript: openTab('27');">
            <span class='d-none d-md-block'>Appraisals</span><span class='d-block d-md-none'><i class='mdi mdi-account h5'></i></span>
        </a>
    </li>   
    <li id='list_menu3' class='list_menu15 nav-item <%=(ctp == 28) ? " active" : ""%>'>
        <a class='nav-link' href="javascript: openTab('28');">
            <span class='d-none d-md-block'>Day Rate</span><span class='d-block d-md-none'><i class='mdi mdi-account h5'></i></span>
        </a>
    </li>
</ul> 

<ul class='nav nav-tabs nav-tabs-custom talent_pool_menu' id='tab_menu'>

    <li id='list_menu1' class='list_menu13 nav-item <%=(ctp == 11) ? " active" : ""%>'>
        <a class='nav-link' href="javascript: openTab('11');">
            <span class='d-none d-md-block'>Verification</span><span class='d-block d-md-none'><i class='mdi mdi-home-variant h5'></i></span>
        </a>
    </li>

    <li id='list_menu2' class='list_menu14 nav-item <%=(ctp == 12) ? " active" : ""%>'>
        <a class='nav-link' href="javascript: openTab('12');">
            <span class='d-none d-md-block'>Assessment</span><span class='d-block d-md-none'><i class='mdi mdi-account h5'></i></span>
        </a>
    </li>
    <li id='list_menu4' class='list_menu15 nav-item <%=(ctp == 14) ? " active" : ""%>'>
        <a class='nav-link' href="javascript: openTab('14');">
            <span class='d-none d-md-block'>Shortlisting</span><span class='d-block d-md-none'><i class='mdi mdi-account h5'></i></span>
        </a>
    </li>

    <li id='list_menu3' class='list_menu15 nav-item <%=(ctp == 13) ? " active" : ""%>'>
        <a class='nav-link' href="javascript: openTab('13');">
            <span class='d-none d-md-block'>Compliance</span><span class='d-block d-md-none'><i class='mdi mdi-account h5'></i></span>
        </a>
    </li>

    <li id='list_menu3' class='list_menu15 nav-item <%=(ctp == 15) ? " active" : ""%>'>
        <a class='nav-link' href="javascript: openTab('15');">
            <span class='d-none d-md-block'>Selection</span><span class='d-block d-md-none'><i class='mdi mdi-account h5'></i></span>
        </a>
    </li>
    <li id='list_menu3' class='list_menu15 nav-item <%=(ctp == 16) ? " active" : ""%>'>
        <a class='nav-link' href="javascript: openTab('16');">
            <span class='d-none d-md-block'>Onboarding</span><span class='d-block d-md-none'><i class='mdi mdi-account h5'></i></span>
        </a>
    </li>
    <li id='list_menu3' class='list_menu15 nav-item <%=(ctp == 17) ? " active" : ""%>'>
        <a class='nav-link' href="javascript: openTab('17');">
            <span class='d-none d-md-block'>Mobilization</span><span class='d-block d-md-none'><i class='mdi mdi-account h5'></i></span>
        </a>
    </li>
    <li id='list_menu3' class='list_menu15 nav-item <%=(ctp == 18) ? " active" : ""%>'>
        <a class='nav-link' href="javascript: openTab('18');">
            <span class='d-none d-md-block'>Rotation</span><span class='d-block d-md-none'><i class='mdi mdi-account h5'></i></span>
        </a>
    </li>
    <li id='list_menu3' class='list_menu15 nav-item <%=(ctp == 19) ? " active" : ""%>'>
        <a class='nav-link' href="javascript: openTab('19');">
            <span class='d-none d-md-block'>Notifications</span><span class='d-block d-md-none'><i class='mdi mdi-account h5'></i></span>
        </a>
    </li>
    <li id='list_menu3' class='list_menu15 nav-item <%=(ctp == 20) ? " active" : ""%>'>
        <a class='nav-link' href="javascript: openTab('20');">
            <span class='d-none d-md-block'>Transfer / Close Assignment</span><span class='d-block d-md-none'><i class='mdi mdi-account h5'></i></span>
        </a>
    </li>
    <li id='list_menu3' class='list_menu15 nav-item <%=(ctp == 21) ? " active" : ""%>'>
        <a class='nav-link' href="javascript: openTab('21');">
            <span class='d-none d-md-block'>Wellness</span><span class='d-block d-md-none'><i class='mdi mdi-account h5'></i></span>
        </a>
    </li>
    <li id='list_menu3' class='list_menu15 nav-item <%=(ctp == 22) ? " active" : ""%>'>
        <a class='nav-link' href="javascript: openTab('22');">
            <span class='d-none d-md-block'>Competency</span><span class='d-block d-md-none'><i class='mdi mdi-account h5'></i></span>
        </a>
    </li>
</ul>
     
