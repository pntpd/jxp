<%
    int smarr[] = new int[200];
    if (session.getAttribute("MARR") != null) {
        smarr = (int[]) session.getAttribute("MARR");
    }
%>
<%!
    public boolean cst(int arr[], int ch) {
        boolean b = false;
        if (arr != null) {
            int tm = arr.length;
            for (int m = 0; m < tm; m++) {
                if (arr[m] == ch) {
                    b = true;
                    break;
                }
            }
        }
        return b;
    }
%>
<div class="vertical-menu">
    <div data-simplebar class="h-100">		
        <div id="sidebar-menu">
            <ul class="metismenu list-unstyled" id="side-menu">	              
                <li class="menu2 <%=(mtp == -1) ? "mm-active active" : ""%>">
                    <a href="/jxp/dashboard/DashboardAction.do?doCrewEnr=yes" class="waves-effect"><img src="/jxp/assets/images/dashboard.png"/> <strong>Dashboard</strong></a>                                   
                </li>
                <% if (cst(smarr, 7) || cst(smarr, 46) || cst(smarr, 14) || cst(smarr, 4) || cst(smarr, 45) || cst(smarr, 49) || cst(smarr, 47) || cst(smarr, 51) || cst(smarr, 56) || cst(smarr, 59) || cst(smarr, 57)) {%>
                <li class="menu2 <%=(mtp == 2) ? "mm-active active" : ""%>">
                    <a href="javascript:;" class="waves-effect"><img src="/jxp/assets/images/crew-management.png"/> <strong>Crew Management</strong></a>
                    <ul class="sub-menu mm-collapse" aria-expanded="false">
                        <% if (cst(smarr, 7)) {%><li class="<%=(submtp == 7 ? "mm-active" : "")%>"><a class="<%=(submtp == 7 ? "active" : "")%>" href="/jxp/candidate/CandidateAction.do">Enroll Candidate</a></li><%}%>
                        <% if (cst(smarr, 46)) {%><li class="<%=(submtp == 46 ? "mm-active" : "")%>"><a class="<%=(submtp == 46 ? "active" : "")%>" href="/jxp/verification/VerificationAction.do">Verification</a></li><%}%>
                        <% if (cst(smarr, 14)) {%><li class="<%=(submtp == 14 ? "mm-active" : "")%>"><a class="<%=(submtp == 14 ? "active" : "")%>" href="/jxp/cassessment/CassessmentAction.do">Assessments</a></li><%}%>
                        <% if (cst(smarr, 4)) {%><li class="<%=(submtp == 4 ? "mm-active" : "")%>"><a class="<%=(submtp == 4 ? "active" : "")%>" href="/jxp/talentpool/TalentpoolAction.do">Talent Pool</a></li><%}%>
                        <% if (cst(smarr, 45)) {%><li class="<%=(submtp == 45 ? "mm-active" : "")%>"><a class="<%=(submtp == 45 ? "active" : "")%>" href="/jxp/jobpost/JobPostAction.do">Job Posting</a></li><%}%>
                        <% if (cst(smarr, 49)) {%><li class="<%=(submtp == 49 ? "mm-active" : "")%>"><a class="<%=(submtp == 49 ? "active" : "")%>" href="/jxp/shortlisting/ShortlistingAction.do">Shortlisting</a></li><%}%>
                        <% if (cst(smarr, 47)) {%><li class="<%=(submtp == 47 ? "mm-active" : "")%>"><a class="<%=(submtp == 47 ? "active" : "")%>" href="/jxp/compliancecheck/CompliancecheckAction.do">Compliance Checks</a></li><%}%>
                        <% if (cst(smarr, 51)) {%><li class="<%=(submtp == 51 ? "mm-active" : "")%>"><a class="<%=(submtp == 51 ? "active" : "")%>" href="/jxp/clientselection/ClientselectionAction.do">Client Selection</a></li><%}%>
                        <% if (cst(smarr, 56)) {%><li class="<%=(submtp == 56 ? "mm-active" : "")%>"><a class="<%=(submtp == 56 ? "active" : "")%>" href="/jxp/onboarding/OnboardingAction.do">Onboarding</a></li><%}%>
                        <% if (cst(smarr, 59)) {%><li class="<%=(submtp == 59 ? "mm-active" : "")%>"><a class="<%=(submtp == 59 ? "active" : "")%>" href="/jxp/mobilization/MobilizationAction.do">Mobilization</a></li><%}%>
                        <% if (cst(smarr, 57)) {%><li class="<%=(submtp == 57 ? "mm-active" : "")%>"><a class="<%=(submtp == 57 ? "active" : "")%>" href="/jxp/crewrotation/CrewrotationAction.do">Crew Rotation</a></li><%}%>
                    </ul>
                </li>
                <% } %>
                <% if (cst(smarr, 66) || cst(smarr, 67)) {%>
                <li class="menu3 <%=(mtp == 4) ? "mm-active active" : ""%>">
                    <a href="javascript: void(0);" class="waves-effect">
                        <img src="/jxp/assets/images/training-and-dev.png"/> <strong>Training & Development</strong>
                    </a>
                    <ul class="sub-menu mm-collapse" aria-expanded="false">
                        <% if (cst(smarr, 66)) {%><li class="<%=(submtp == 66 ? "mm-active" : "")%>"><a class="<%=(submtp == 66 ? "active" : "")%>" href="/jxp/trainingmatrix/TrainingmatrixAction.do">Training Matrix</a></li><%}%>
                        <% if (cst(smarr, 67)) {%><li class="<%=(submtp == 67 ? "mm-active" : "")%>"><a class="<%=(submtp == 67 ? "active" : "")%>" href="/jxp/managetraining/ManagetrainingAction.do">Manage Training</a></li><%}%>
                    </ul>
                </li>
                <% } %>
                <% if (cst(smarr, 87) || cst(smarr, 90)) {%>
                <li class="menu4 well_menu <%=(mtp == 5) ? "mm-active active" : ""%>">
                    <a href="javascript: void(0);" class="waves-effect">
                        <img src="/jxp/assets/images/competency-mgmt.png"/> <strong>Competency Management</strong>
                    </a>
                    <ul class="sub-menu mm-collapse" aria-expanded="false">
                        <% if (cst(smarr, 87)) {%><li class="<%=(submtp == 87 ? "mm-active" : "")%>"><a class="<%=(submtp == 87 ? "active" : "")%>" href="/jxp/framework/FrameworkAction.do">Framework Management</a></li><%}%>
                        <% if (cst(smarr, 90)) {%><li class="<%=(submtp == 90 ? "mm-active" : "")%>"><a class="<%=(submtp == 90 ? "active" : "")%>" href="/jxp/tracker/TrackerAction.do">Competency Tracker</a></li><%}%>
                    </ul>
                </li>
                <%}%>
                <% if (cst(smarr, 73) || cst(smarr, 71)) {%>
                <li class="menu5 well_menu <%=(mtp == 6) ? "mm-active active" : ""%>">
                    <a href="javascript: void(0);" class="waves-effect">
                        <img src="/jxp/assets/images/wellness-mgmt.png"/> <strong>Wellness Management</strong>
                    </a>
                    <ul class="sub-menu mm-collapse mm-show menu_scroll" aria-expanded="false">
                        <% if (cst(smarr, 73)) {%><li class="<%=(submtp == 73 ? "mm-active" : "")%>"><a class="<%=(submtp == 73 ? "active" : "")%>" href="/jxp/managewellness/ManagewellnessAction.do">Manage Wellness</a></li><%}%>
                        <% if (cst(smarr, 71)) {%><li class="<%=(submtp == 71 ? "mm-active" : "")%>"><a class="<%=(submtp == 71 ? "active" : "")%>" href="/jxp/crewinsurance/CrewinsuranceAction.do">Insurance</a></li><%}%>
                    </ul>
                </li>      
                <%}%>
                <% if (cst(smarr, 76) || cst(smarr, 77)) {%>
                <li class="menu6 bill_menu <%=(mtp == 9) ? "mm-active active" : ""%>">
                    <a href="javascript: void(0);" class="waves-effect">
                        <img src="/jxp/assets/images/billing.png"/> <strong>Billing</strong>
                    </a>
                    <ul class="sub-menu mm-collapse menu_scroll" aria-expanded="false">                        
                        <% if (cst(smarr, 76)) {%><li class="<%=(submtp == 76 ? "mm-active" : "")%>"><a class="<%=(submtp == 76 ? "active" : "")%>" href="/jxp/timesheet/TimesheetAction.do">Timesheet Management</a></li><%}%>
                        <% if (cst(smarr, 77)) {%><li class="<%=(submtp == 77 ? "mm-active" : "")%>"><a class="<%=(submtp == 77 ? "active" : "")%>" href="/jxp/clientinvoicing/ClientinvoicingAction.do">Invoicing</a></li><%}%>
                    </ul>
                </li>
                <%}%>
                <% if (cst(smarr, 53) || cst(smarr, 41) || cst(smarr, 40) || cst(smarr, 42) || cst(smarr, 13) || cst(smarr, 6) || cst(smarr, 39) || cst(smarr, 33) || cst(smarr, 43) || cst(smarr, 31) || cst(smarr, 24) || cst(smarr, 2) || cst(smarr, 27)
                            || cst(smarr, 5) || cst(smarr, 36) || cst(smarr, 35) || cst(smarr, 58) || cst(smarr, 28) || cst(smarr, 10) || cst(smarr, 29) || cst(smarr, 37) || cst(smarr, 34) || cst(smarr, 23) || cst(smarr, 38) || cst(smarr, 20)
                            || cst(smarr, 22) || cst(smarr, 8) || cst(smarr, 19) || cst(smarr, 55) || cst(smarr, 54) || cst(smarr, 44) || cst(smarr, 12) || cst(smarr, 9) || cst(smarr, 52) || cst(smarr, 18) || cst(smarr, 96) || cst(smarr, 11) || cst(smarr, 50)
                            || cst(smarr, 21) || cst(smarr, 32) || cst(smarr, 16) || cst(smarr, 1) || cst(smarr, 25) || cst(smarr, 26) || cst(smarr, 15) || cst(smarr, 30) || cst(smarr, 3) || cst(smarr, 17) || cst(smarr, 48)
                            || cst(smarr, 65) || cst(smarr, 63) || cst(smarr, 61) || cst(smarr, 62) || cst(smarr, 60) || cst(smarr, 64) || cst(smarr, 69) || cst(smarr, 72) || cst(smarr, 74) || cst(smarr, 75) || cst(smarr, 78) || cst(smarr, 79) || cst(smarr, 80)
                            || cst(smarr, 81) || cst(smarr, 82) || cst(smarr, 83) || cst(smarr, 84) || cst(smarr, 85) || cst(smarr, 86) || cst(smarr, 88) || cst(smarr, 89) || cst(smarr, 91) || cst(smarr, 95) || cst(smarr, 97) || cst(smarr, 98) || cst(smarr, 99) ) {%>
                <li class="menu7 conf_menu <%=(mtp == 7) ? "mm-active active" : ""%>">
                    <a href="javascript: void(0);" class="waves-effect" >
                        <img src="/jxp/assets/images/settings.png"/> <strong>Configurations</strong>
                    </a>
                    <ul class="sub-menu mm-collapse mm-show menu_scroll" aria-expanded="false" id="myTable">
                        <li class="search_menu_list"><input id="myInput" type="text" class="form-control" placeholder="Enter here" styleClass="form-control" maxlength="200" onkeypress="javascript: handleKeySearch(event);" readonly="true" onfocus="if (this.hasAttribute('readonly')) {
                                    this.removeAttribute('readonly');
                                    this.blur();
                                    this.focus();
                                }"/></li>
                            <% if (cst(smarr, 53) || cst(smarr, 41) || cst(smarr, 40) || cst(smarr, 42) || cst(smarr, 13) || cst(smarr, 6) || cst(smarr, 39) || cst(smarr, 33) || cst(smarr, 43) || cst(smarr, 31) || cst(smarr, 24) || cst(smarr, 2) || cst(smarr, 27)
                                        || cst(smarr, 5) || cst(smarr, 36) || cst(smarr, 35) || cst(smarr, 58) || cst(smarr, 28) || cst(smarr, 10) || cst(smarr, 29) || cst(smarr, 37) || cst(smarr, 34) || cst(smarr, 23) || cst(smarr, 38) || cst(smarr, 20)
                                        || cst(smarr, 22) || cst(smarr, 8) || cst(smarr, 19) || cst(smarr, 55) || cst(smarr, 54) || cst(smarr, 44) || cst(smarr, 12) || cst(smarr, 9) || cst(smarr, 52) || cst(smarr, 18) || cst(smarr, 96) || cst(smarr, 11) || cst(smarr, 50)
                                    || cst(smarr, 21) || cst(smarr, 32) || cst(smarr, 16) || cst(smarr, 1) || cst(smarr, 25) || cst(smarr, 26) || cst(smarr, 15) || cst(smarr, 30) || cst(smarr, 3) || cst(smarr, 17) || cst(smarr, 95) || cst(smarr, 97) || cst(smarr, 98) || cst(smarr, 99)) {%>
                        <li><a href="javascript:;"><b>GENERAL</b></a>
                        <% if (cst(smarr, 53)) {%><li class="<%=(submtp == 53 ? "mm-active" : "")%>"><a class="<%=(submtp == 53 ? "active" : "")%>" href="/jxp/airport/AirportAction.do">Airports</a></li><%}%>
                        <% if (cst(smarr, 41)) {%><li class="<%=(submtp == 41 ? "mm-active" : "")%>"><a class="<%=(submtp == 41 ? "active" : "")%>" href="/jxp/assessmentanswertype/AssessmentAnswerTypeAction.do">Assessment Answer Type</a></li><%}%>
                        <% if (cst(smarr, 40)) {%><li class="<%=(submtp == 40 ? "mm-active" : "")%>"><a class="<%=(submtp == 40 ? "active" : "")%>" href="/jxp/assessmentparameter/AssessmentParameterAction.do">Assessment Parameter</a></li><%}%>
                        <% if (cst(smarr, 42)) {%><li class="<%=(submtp == 42 ? "mm-active" : "")%>"><a class="<%=(submtp == 42 ? "active" : "")%>" href="/jxp/assessmentquestion/AssessmentQuestionAction.do">Assessment Question</a></li><%}%>
                        <% if (cst(smarr, 13)) {%><li class="<%=(submtp == 13 ? "mm-active" : "")%>"><a class="<%=(submtp == 13 ? "active" : "")%>" href="/jxp/assessment/AssessmentAction.do">Assessments</a></li><%}%>
                        <% if (cst(smarr, 6)) {%><li class="<%=(submtp == 6 ? "mm-active" : "")%>"><a class="<%=(submtp == 6 ? "active" : "")%>" href="/jxp/assettype/AssettypeAction.do">Asset Type</a></li><%}%>
                        <% if (cst(smarr, 39)) {%><li class="<%=(submtp == 39 ? "mm-active" : "")%>"><a class="<%=(submtp == 39 ? "active" : "")%>" href="/jxp/bankaccounttype/BankAccountTypeAction.do">Bank Account Type</a></li><%}%>
                        <% if (cst(smarr, 33)) {%><li class="<%=(submtp == 33 ? "mm-active" : "")%>"><a class="<%=(submtp == 33 ? "active" : "")%>" href="/jxp/benefitinformationtype/BenefitinformationtypeAction.do">Benefit Information Type</a></li><%}%>
                        <% if (cst(smarr, 43)) {%><li class="<%=(submtp == 43 ? "mm-active" : "")%>"><a class="<%=(submtp == 43 ? "active" : "")%>" href="/jxp/benefit/BenefitAction.do">Benefit(Positions)</a></li><%}%>
                        <% if (cst(smarr, 31)) {%><li class="<%=(submtp == 31 ? "mm-active" : "")%>"><a class="<%=(submtp == 31 ? "active" : "")%>" href="/jxp/bloodpressure/BloodpressureAction.do">Blood Pressure</a></li><%}%>     
                        <% if (cst(smarr, 24)) {%><li class="<%=(submtp == 24 ? "mm-active" : "")%>"><a class="<%=(submtp == 24 ? "active" : "")%>" href="/jxp/city/CityAction.do">Cities</a></li><%}%>
                        <% if (cst(smarr, 2)) {%><li class="<%=(submtp == 2 ? "mm-active" : "")%>"><a class="<%=(submtp == 2 ? "active" : "")%>" href="/jxp/client/ClientAction.do">Clients</a></li><%}%>
                        <% if (cst(smarr, 27)) {%><li class="<%=(submtp == 27 ? "mm-active" : "")%>"><a class="<%=(submtp == 27 ? "active" : "")%>" href="/jxp/companyindustry/CompanyindustryAction.do">Company Industry</a></li><%}%>
                        <% if (cst(smarr, 98)) {%><li class="<%=(submtp == 98 ? "mm-active" : "")%>"><a class="<%=(submtp == 98 ? "active" : "")%>" href="/jxp/contract/ContractAction.do">Contract</a></li><%}%>
                        <% if (cst(smarr, 5)) {%><li class="<%=(submtp == 5 ? "mm-active" : "")%>"><a class="<%=(submtp == 5 ? "active" : "")%>" href="/jxp/country/CountryAction.do">Country</a></li><%}%>
                        <% if (cst(smarr, 36)) {%><li class="<%=(submtp == 36 ? "mm-active" : "")%>"><a class="<%=(submtp == 36 ? "active" : "")%>" href="/jxp/approvedby/ApprovedbyAction.do">Course Approved By</a></li><%}%>
                        <% if (cst(smarr, 35)) {%><li class="<%=(submtp == 35 ? "mm-active" : "")%>"><a class="<%=(submtp == 35 ? "active" : "")%>" href="/jxp/coursetype/CourseTypeAction.do">Course Type</a></li><%}%>
                        <% if (cst(smarr, 58)) {%><li class="<%=(submtp == 58 ? "mm-active" : "")%>"><a class="<%=(submtp == 58 ? "active" : "")%>" href="/jxp/doccheck/DoccheckAction.do">Doc Check(Crew Rotation)</a></li><%}%>
                        <% if (cst(smarr, 95)) {%><li class="<%=(submtp == 95 ? "mm-active" : "")%>"><a class="<%=(submtp == 95 ? "active" : "")%>" href="/jxp/policy/PolicyAction.do">Documents</a></li><%}%>
                        <% if (cst(smarr, 28)) {%><li class="<%=(submtp == 28 ? "mm-active" : "")%>"><a class="<%=(submtp == 28 ? "active" : "")%>" href="/jxp/crewtype/CrewtypeAction.do">Crew Type</a></li><%}%>
                        <% if (cst(smarr, 10)) {%><li class="<%=(submtp == 10 ? "mm-active" : "")%>"><a class="<%=(submtp == 10 ? "active" : "")%>" href="/jxp/currency/CurrencyAction.do">Currency</a></li><%}%> 
                        <% if (cst(smarr, 29)) {%><li class="<%=(submtp == 29 ? "mm-active" : "")%>"><a class="<%=(submtp == 29 ? "active" : "")%>" href="/jxp/experiencedept/ExperienceDeptAction.do"> Departments/Functions</a></li><%}%>
                        <% if (cst(smarr, 37)) {%><li class="<%=(submtp == 37 ? "mm-active" : "")%>"><a class="<%=(submtp == 37 ? "active" : "")%>" href="/jxp/documentissuedby/DocumentissuedbyAction.do">Document Issued By</a></li><%}%>
                        <% if (cst(smarr, 34)) {%><li class="<%=(submtp == 34 ? "mm-active" : "")%>"><a class="<%=(submtp == 34 ? "active" : "")%>" href="/jxp/degree/DegreeAction.do">Education Degree</a></li><%}%>   
                        <% if (cst(smarr, 23)) {%><li class="<%=(submtp == 23 ? "mm-active" : "")%>"><a class="<%=(submtp == 23 ? "active" : "")%>" href="/jxp/qualificationtype/QualificationTypeAction.do">Education Kind</a></li><%}%>
                        <% if (cst(smarr, 38)) {%><li class="<%=(submtp == 38 ? "mm-active" : "")%>"><a class="<%=(submtp == 38 ? "active" : "")%>" href="/jxp/enginetype/EnginetypeAction.do">Engine Type</a></li><%}%>
                        <% if (cst(smarr, 20)) {%><li class="<%=(submtp == 20 ? "mm-active" : "")%>"><a class="<%=(submtp == 20 ? "active" : "")%>" href="/jxp/grade/GradeAction.do"> Grades</a></li><%}%>
                        <% if (cst(smarr, 22)) {%><li class="<%=(submtp == 22 ? "mm-active" : "")%>"><a class="<%=(submtp == 22 ? "active" : "")%>" href="/jxp/hoursofwork/HoursOfWorkAction.do"> Hours Of Work</a></li><%}%>
                        <% if (cst(smarr, 8)) {%><li class="<%=(submtp == 8 ? "mm-active" : "")%>"><a class="<%=(submtp == 8 ? "active" : "")%>" href="/jxp/language/LanguageAction.do">Language</a></li><%}%>
                        <% if (cst(smarr, 19)) {%><li class="<%=(submtp == 19 ? "mm-active" : "")%>"><a class="<%=(submtp == 19 ? "active" : "")%>" href="/jxp/maritialstatus/MaritialStatusAction.do">Marital Status</a></li><%}%>
                        <% if (cst(smarr, 55)) {%><li class="<%=(submtp == 55 ? "mm-active" : "")%>"><a class="<%=(submtp == 55 ? "active" : "")%>" href="/jxp/formality/FormalityAction.do">Onboarding Formality</a></li><%}%>
                        <% if (cst(smarr, 54)) {%><li class="<%=(submtp == 54 ? "mm-active" : "")%>"><a class="<%=(submtp == 54 ? "active" : "")%>" href="/jxp/onboardingkit/OnboardingkitAction.do">Onboarding Kit</a></li><%}%>
                        <% if (cst(smarr, 44)) {%><li class="<%=(submtp == 44 ? "mm-active" : "")%>"><a class="<%=(submtp == 44 ? "active" : "")%>" href="/jxp/port/PortAction.do">Port</a></li><%}%> 
                        <% if (cst(smarr, 12)) {%><li class="<%=(submtp == 12 ? "mm-active" : "")%>"><a class="<%=(submtp == 12 ? "active" : "")%>" href="/jxp/position/PositionAction.do">Positions</a></li><%}%> 
                        <% if (cst(smarr, 97)) {%><li class="<%=(submtp == 97 ? "mm-active" : "")%>"><a class="<%=(submtp == 97 ? "active" : "")%>" href="/jxp/ppetype/PpetypeAction.do">PPE Master</a></li><%}%> 
                        <% if (cst(smarr, 9)) {%><li class="<%=(submtp == 9 ? "mm-active" : "")%>"><a class="<%=(submtp == 9 ? "active" : "")%>" href="/jxp/proficiency/ProficiencyAction.do">Proficiency</a></li><%}%>
                        <% if (cst(smarr, 52)) {%><li class="<%=(submtp == 52 ? "mm-active" : "")%>"><a class="<%=(submtp == 52 ? "active" : "")%>" href="/jxp/rejectionreason/RejectionreasonAction.do">Rejection Reason</a></li><%}%>
                        <% if (cst(smarr, 18)) {%><li class="<%=(submtp == 18 ? "mm-active" : "")%>"><a class="<%=(submtp == 18 ? "active" : "")%>" href="/jxp/relation/RelationAction.do">Relation</a></li><%}%>
                        <% if (cst(smarr, 96)) {%><li class="<%=(submtp == 96 ? "mm-active" : "")%>"><a class="<%=(submtp == 96 ? "active" : "")%>" href="/jxp/remark/RemarkAction.do">Remark Type</a></li><%}%>                        
                        <% if (cst(smarr, 11)) {%><li class="<%=(submtp == 11 ? "mm-active" : "")%>"><a class="<%=(submtp == 11 ? "active" : "")%>" href="/jxp/documenttype/DocumentTypeAction.do">Required Document List</a></li><%}%>
                        <% if (cst(smarr, 50)) {%><li class="<%=(submtp == 50 ? "mm-active" : "")%>"><a class="<%=(submtp == 50 ? "active" : "")%>" href="/jxp/resumetemplate/ResumetemplateAction.do">Resume Template</a></li><%}%>
                        <% if (cst(smarr, 21)) {%><li class="<%=(submtp == 21 ? "mm-active" : "")%>"><a class="<%=(submtp == 21 ? "active" : "")%>" href="/jxp/rotation/RotationAction.do"> Rotations</a></li><%}%>
                        <% if (cst(smarr, 32)) {%><li class="<%=(submtp == 32 ? "mm-active" : "")%>"><a class="<%=(submtp == 32 ? "active" : "")%>" href="/jxp/skills/SkillsAction.do">Skills</a></li><%}%>
                        <% if (cst(smarr, 99)) {%><li class="<%=(submtp == 99 ? "mm-active" : "")%>"><a class="<%=(submtp == 99 ? "active" : "")%>" href="/jxp/state/StateAction.do">State</a></li><%}%>
                        <% if (cst(smarr, 16)) {%><li class="<%=(submtp == 16 ? "mm-active" : "")%>"><a class="<%=(submtp == 16 ? "active" : "")%>" href="/jxp/timezone/TimezoneAction.do">Time Zone</a></li><%}%>
                        <% if (cst(smarr, 1)) {%><li class="<%=(submtp == 1 ? "mm-active" : "")%>"><a class="<%=(submtp == 1 ? "active" : "")%>" href="/jxp/user/UserAction.do">Users</a></li><%}%>
                        <% if (cst(smarr, 25)) {%><li class="<%=(submtp == 25 ? "mm-active" : "")%>"><a class="<%=(submtp == 25 ? "active" : "")%>" href="/jxp/vaccine/VaccineAction.do">Vaccination Name</a></li><%}%>
                        <% if (cst(smarr, 26)) {%><li class="<%=(submtp == 26 ? "mm-active" : "")%>"><a class="<%=(submtp == 26 ? "active" : "")%>" href="/jxp/vaccinetype/VaccinetypeAction.do">Vaccination Type</a></li><%}%>
                        <% if (cst(smarr, 15)) {%><li class="<%=(submtp == 15 ? "mm-active" : "")%>"><a class="<%=(submtp == 15 ? "active" : "")%>" href="/jxp/verificationcheckpoint/VerificationcheckpointAction.do">Verification Checkpoints</a></li><%}%>
                        <% if (cst(smarr, 30)) {%><li class="<%=(submtp == 30 ? "mm-active" : "")%>"><a class="<%=(submtp == 30 ? "active" : "")%>" href="/jxp/experiencewaterdepth/ExperienceWaterDepthAction.do"> Water Depth</a></li><%}%>
                        <% if (cst(smarr, 3)) {%><li class="<%=(submtp == 3 ? "mm-active" : "")%>"><a class="<%=(submtp == 3 ? "active" : "")%>" href="/jxp/access/AccessAction.do">Access Report</a></li><%}%>
                        <% if (cst(smarr, 17)) {%><li class="<%=(submtp == 17 ? "mm-active" : "")%>"><a class="<%=(submtp == 17 ? "active" : "")%>" href="/jxp/maillog/MaillogAction.do">Maillog Report</a></li><%}%>
                            <%}%>

                        <% if (cst(smarr, 48)) {%>
                        <li><a href="javascript:;"><b>CREW MANAGEMENT</b></a>
                        <% if (cst(smarr, 48)) {%><li class="<%=(submtp == 48 ? "mm-active" : "")%>"><a class="<%=(submtp == 48 ? "active" : "")%>" href="/jxp/checkpoint/CheckPointAction.do">Compliance Checkpoint</a></li><%}%>
                            <%}%>
                            <% if (cst(smarr, 65) || cst(smarr, 63) || cst(smarr, 61) || cst(smarr, 62) || cst(smarr, 60) || cst(smarr, 64)) {%>
                        <li><a href="javascript:;"><b>TRAINING & DEVELOPMENT</b></a>
                        <% if (cst(smarr, 65)) {%><li class="<%=(submtp == 65 ? "mm-active" : "")%>"><a class="<%=(submtp == 65 ? "active" : "")%>" href="/jxp/coursename/CoursenameAction.do">Course Name</a></li><%}%>    
                        <% if (cst(smarr, 63)) {%><li class="<%=(submtp == 63 ? "mm-active" : "")%>"><a class="<%=(submtp == 63 ? "active" : "")%>" href="/jxp/level/LevelAction.do">Level</a></li><%}%>
                        <% if (cst(smarr, 61)) {%><li class="<%=(submtp == 61 ? "mm-active" : "")%>"><a class="<%=(submtp == 61 ? "active" : "")%>" href="/jxp/tctype/TctypeAction.do">Course Type</a></li><%}%>
                        <% if (cst(smarr, 62)) {%><li class="<%=(submtp == 62 ? "mm-active" : "")%>"><a class="<%=(submtp == 62 ? "active" : "")%>" href="/jxp/training/TrainingAction.do">Priority</a></li><%}%>
                        <% if (cst(smarr, 60)) {%><li class="<%=(submtp == 60 ? "mm-active" : "")%>"><a class="<%=(submtp == 60 ? "active" : "")%>" href="/jxp/createtraining/CreatetrainingAction.do">Create Training</a></li><%}%>
                        <% if (cst(smarr, 64)) {%><li class="<%=(submtp == 64 ? "mm-active" : "")%>"><a class="<%=(submtp == 64 ? "active" : "")%>" href="/jxp/matrix/MatrixAction.do">Configure Matrix</a></li><%}%>
                            <%}%>
                            <% if (cst(smarr, 69) || cst(smarr, 72)) {%>
                        <li><a href="javascript:;"><b>WELLNESS MANAGEMENT</b></a>
                        <% if (cst(smarr, 69)) {%><li class="<%=(submtp == 69 ? "mm-active" : "")%>"><a class="<%=(submtp == 69 ? "active" : "")%>" href="/jxp/wellnessfb/WellnessfbAction.do">Wellness Feedback</a></li><%}%>
                        <% if (cst(smarr, 72)) {%><li class="<%=(submtp == 72 ? "mm-active" : "")%>"><a class="<%=(submtp == 72 ? "active" : "")%>" href="/jxp/wellnessmatrix/WellnessmatrixAction.do">Wellness Matrix</a></li><%}%>    
                            <%}%>
                            <% if (cst(smarr, 74) || cst(smarr, 75) || cst(smarr, 78) || cst(smarr, 79) || cst(smarr, 80) || cst(smarr, 81)) {%>
                        <li><a href="javascript:;"><b>BILLING</b></a></li>
                        <% if (cst(smarr, 74)) {%><li class="<%=(submtp == 74 ? "mm-active" : "")%>"><a class="<%=(submtp == 74 ? "active" : "")%>" href="/jxp/crewdayrate/CrewdayrateAction.do">Crew Day Rate</a></li><%}%>
                        <% if (cst(smarr, 75)) {%><li class="<%=(submtp == 75 ? "mm-active" : "")%>"><a class="<%=(submtp == 75 ? "active" : "")%>" href="/jxp/billingcycle/BillingcycleAction.do">Billing Cycle</a></li><%}%>
                        <% if (cst(smarr, 79)) {%><li class="<%=(submtp == 79 ? "mm-active" : "")%>"><a class="<%=(submtp == 79 ? "active" : "")%>" href="/jxp/revision/RevisionAction.do">Revision Reason</a></li><%}%>
                        <% if (cst(smarr, 80)) {%><li class="<%=(submtp == 80 ? "mm-active" : "")%>"><a class="<%=(submtp == 80 ? "active" : "")%>" href="/jxp/invoicetemplate/InvoicetemplateAction.do">Invoice Template</a></li><%}%>
                        <% if (cst(smarr, 81)) {%><li class="<%=(submtp == 81 ? "mm-active" : "")%>"><a class="<%=(submtp == 81 ? "active" : "")%>" href="/jxp/bank/BankAction.do">Bank Module</a></li><%}%>
                            <%}%>
                            <% if (cst(smarr, 82) || cst(smarr, 83) || cst(smarr, 84) || cst(smarr, 85) || cst(smarr, 86) || cst(smarr, 88) || cst(smarr, 89) || cst(smarr, 91)) {%>
                        <li><a href="javascript:;"><b>COMPETENCY</b></a></li>
                        <% if (cst(smarr, 82)) {%><li class="<%=(submtp == 82 ? "mm-active" : "")%>"><a class="<%=(submtp == 82 ? "active" : "")%>" href="/jxp/pdept/PdeptAction.do">Departments</a></li><%}%>
                        <% if (cst(smarr, 83)) {%><li class="<%=(submtp == 83 ? "mm-active" : "")%>"><a class="<%=(submtp == 83 ? "active" : "")%>" href="/jxp/pcategory/PcategoryAction.do">Competency Assessments</a></li><%}%>
                        <% if (cst(smarr, 84)) {%><li class="<%=(submtp == 84 ? "mm-active" : "")%>"><a class="<%=(submtp == 84 ? "active" : "")%>" href="/jxp/competencyassessments/CompetencyassessmentsAction.do">Assessment Type</a></li><%}%>
                        <% if (cst(smarr, 85)) {%><li class="<%=(submtp == 85 ? "mm-active" : "")%>"><a class="<%=(submtp == 85 ? "active" : "")%>" href="/jxp/competencypriorities/CompetencyprioritiesAction.do">Competency Priorities</a></li><%}%>
                        <% if (cst(smarr, 86)) {%><li class="<%=(submtp == 86 ? "mm-active" : "")%>"><a class="<%=(submtp == 86 ? "active" : "")%>" href="/jxp/pcode/PcodeAction.do">Competency Framework</a></li><%}%>
                        <% if (cst(smarr, 88)) {%><li class="<%=(submtp == 88 ? "mm-active" : "")%>"><a class="<%=(submtp == 88 ? "active" : "")%>" href="/jxp/appeal/AppealAction.do">Appeal Reason</a></li><%}%>
                        <% if (cst(smarr, 89)) {%><li class="<%=(submtp == 89 ? "mm-active" : "")%>"><a class="<%=(submtp == 89 ? "active" : "")%>" href="/jxp/appealreason/AppealreasonAction.do">Competency Result</a></li><%}%>
                        <% if (cst(smarr, 91)) {%><li class="<%=(submtp == 91 ? "mm-active" : "")%>"><a class="<%=(submtp == 91 ? "active" : "")%>" href="/jxp/appealrejection/AppealrejectionAction.do">Appeal Rejection Reason</a></li><%}%>
                            <% } %>
                    </ul>
                </li>
                <%}%>
                <% if (cst(smarr, 93) || cst(smarr, 94)) {%>
                <li class="menu8 bill_menu know_menu <%=(mtp == 10) ? "mm-active active" : ""%>">
                    <a href="javascript: void(0);" class="waves-effect">
                        <img src="/jxp/assets/images/knowledge.png"/> <strong>Knowledge Base</strong>
                    </a>
                    <ul class="sub-menu mm-collapse menu_scroll" aria-expanded="false">                        
                        <% if (cst(smarr, 93)) {%><li class="<%=(submtp == 93 ? "mm-active" : "")%>"><a class="<%=(submtp == 93 ? "active" : "")%>" href="/jxp/knowledgecontent/KnowledgecontentAction.do">Content Management</a></li><%}%>
                        <% if (cst(smarr, 94)) {%><li class="<%=(submtp == 94 ? "mm-active" : "")%>"><a class="<%=(submtp == 94 ? "active" : "")%>" href="/jxp/knowledgebasematrix/KnowledgebasematrixAction.do">Knowledge Base Matrix</a></li><%}%>
                    </ul>
                </li>
                <%}%>
                <% if (cst(smarr, 68) || cst(smarr, 92) || cst(smarr, 100)) {%>
                <li class="menu9 analy_menu <%=(mtp == 8) ? "mm-active active" : ""%>">
                    <a href="javascript: void(0);" class="waves-effect">
                        <img src="/jxp/assets/images/analytics.png"/> <strong>Analytics & Reports</strong>
                    </a>
                    <ul class="sub-menu mm-collapse" aria-expanded="false">
                        <% if (cst(smarr, 68)) {%><li class="<%=(submtp == 68 ? "mm-active" : "")%>"><a class="<%=(submtp == 68 ? "active" : "")%>" href="/jxp/dashboard/DashboardAction.do?doCR=yes">Crew Rotation</a></li><%}%>
                        <% if (cst(smarr, 68)) {%><li class="<%=(submtp == 68 ? "mm-active" : "")%>"><a class="<%=(submtp == 68 ? "active" : "")%>" href="/jxp/analytics/AnalyticsAction.do?doTraining=yes">Training</a></li><%}%>
                        <% if (cst(smarr, 92)) {%><li class="<%=(submtp == 92 ? "mm-active" : "")%>"><a class="<%=(submtp == 92 ? "active" : "")%>" href="/jxp/competency/CompetencyAction.do">Competency Assessment</a></li><%}%>
                        <% if (cst(smarr, 92)) {%><li class="<%=(submtp == 100 ? "mm-active" : "")%>"><a class="<%=(submtp == 100 ? "active" : "")%>" href="/jxp/analytics/AnalyticsAction.do?doAttendance=yes">Attendance Report</a></li><%}%>
                    </ul>
                </li>
                <%}%>
            </ul>
        </div>
    </div>
</div>
