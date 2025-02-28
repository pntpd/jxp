<%@page language="java"%>
<%@page import="com.web.jxp.base.*" %>
<%@taglib uri="/WEB-INF/struts-html-el.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/fmt.tld" prefix="fmt"%>
<%@taglib uri="/WEB-INF/c.tld" prefix="c"%>
<jsp:useBean id="base" class="com.web.jxp.base.Base" scope="page"/>
<!doctype html>
<html lang="en">
    <%
        try 
        {
            int mtp = 0, submtp = 0, ctp = 0;
            if (session.getAttribute("CREWLOGIN") == null)
            {

    %>
        <jsp:forward page="/crewloginindex1.jsp"/>
    <%
            }        
    %>
    <head>
        <meta charset="utf-8">
        <title>Privacy Policy</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <!-- App favicon -->
        <link rel="shortcut icon" href="../assets/images/favicon.png">
        <!-- Bootstrap Css -->
        <link href="../assets/css/bootstrap.min.css" id="bootstrap-style" rel="stylesheet" type="text/css">
        <link href="../assets/css/bootstrap-select.min.css" rel="stylesheet" type="text/css">
        <link href="../assets/css/bootstrap-datepicker3.min.css" rel="stylesheet" type="text/css">
        <!-- Icons Css -->
        <link href="../assets/css/icons.min.css" rel="stylesheet" type="text/css">
        <!-- App Css-->
        <link href="../assets/css/app.min.css" id="app-style" rel="stylesheet" type="text/css">
        <link href="../assets/css/style.css" rel="stylesheet" type="text/css">
        <script type="text/javascript" src="../jsnew/common.js"></script>
        <script type="text/javascript" src="../jsnew/feedback.js"></script>
        <script type="text/javascript" src="../jsnew/validation.js"></script>
    </head>
    <body data-sidebar="dark" data-keep-enlarged="true" class="ocs_feedback vertical-collpsed crew_user bg_image">
    <html:form action="/feedback/FeedbackAction.do" onsubmit="return false;">
        <div id="layout-wrapper">
            <%@include file="../header_crewlogin.jsp" %>
            <!-- Start right Content -->
            <div class="main-content">
                <div class="page-content">
                    <div class="container-fluid">
                        <div class="row">
                            <div class="col-md-12 col-xl-12 heading_title">
                                <h1><a href="/jxp/feedback/feedback_welcome.jsp"><i class="ion ion-ios-arrow-round-back"></i></a>  Privacy Policy</h1>
                            </div>

                            <div class="col-md-12 col-xl-12">
                                <div class="body-background online_assessment">
                                    <div class="col-xl-12 col-lg-12 col-md-12 col-sm-12 col-12 onlin_ass_ques about_privacy_area">
                                        <span>Last updated September 04, 2023</span>
                                        <p>
                                            This privacy notice for Planet NextGen Technologies ("we," "us," or "our"), 
                                            describes how and why we might collect, store, use, and/or share ("process") your information when you use our services ("Services"), 
                                            such as when you: 
                                        </p>	

                                        <p>	
                                            Visit our website at <b><%=base.getMainPath("web_path")%></b>, or any website of ours that links to this privacy notice <br/>
                                            Engage with us in other related ways, including any sales, marketing, or events 
                                        </p>	
                                        <p>	
                                            Questions or concerns? Reading this privacy 
                                            notice will help you understand your privacy rights and choices. If you do not agree with our policies and practices, please 
                                            do not use our Services. If you still have any questions or concerns, please contact us at <b>Info@planetngtech.com</b>.
                                        </p>

                                        <h3>SUMMARY OF KEY POINTS</h3>
                                        <p>
                                            This summary provides key points from our privacy notice, but you can find out more details about any of these 
                                            topics by clicking the link following each key point or by using our table of contents below to find the section you are 
                                            looking for. 
                                        </p>
                                        <p>
                                            What personal information do we process? When you visit, use, or navigate our Services, we may process personal 
                                            information depending on how you interact with us and the Services, the choices you make, and the products and features you use. 
                                            Learn more about personal information you disclose to us. 
                                        </p>

                                        <p>	
                                            Do we process any sensitive personal information? We do not process sensitive personal information. 
                                        </p>

                                        <p>	
                                            Do we receive any information from third parties? We do not receive any information from third parties. 
                                        </p>		

                                        <p>	
                                            How do we process your information? We process your information to provide, improve, and administer our Services, 
                                            communicate with you, for security and fraud prevention, and to comply with law. We may also process your information for 
                                            other purposes with your consent. We process your information only when we have a valid legal reason to do so. Learn more 
                                            about how we process your information. 
                                        </p>		
                                        <p>		
                                            In what situations and with which parties do we share personal information? We may 
                                            share information in specific situations and with specific third parties. Learn more about when and with whom we share your 
                                            personal information. 
                                        </p>		
                                        <p>		
                                            How do we keep your information safe? We have organizational and technical processes and procedures in 
                                            place to protect your personal information. However, no electronic transmission over the internet or information storage 
                                            technology can be guaranteed to be 100% secure, so we cannot promise or guarantee that hackers, cybercriminals, or other 
                                            unauthorized third parties will not be able to defeat our security and improperly collect, access, steal, or modify your 
                                            information. Learn more about how we keep your information safe. 
                                        </p>		
                                        <p>		
                                            What are your rights? Depending on where you are located 
                                            geographically, the applicable privacy law may mean you have certain rights regarding your personal information. Learn more about
                                            your privacy rights. 
                                        </p>		

                                        <p>		
                                            How do you exercise your rights? The easiest way to exercise your rights is by submitting a data subject access request, 
                                            or by contacting us. We will consider and act upon any request in accordance with applicable data protection laws.
                                        </p>		

                                        <p>		
                                            Want to learn more about what we do with any information we collect? Review the privacy notice in full. 
                                        </p>

                                        <h3>TABLE OF CONTENTS</h3>
                                        <ol type="1">
                                            <li>WHAT INFORMATION DO WE COLLECT?</li>
                                            <li>HOW DO WE PROCESS YOUR INFORMATION?</li>
                                            <li>WHEN AND WITH WHOM DO WE SHARE YOUR PERSONAL INFORMATION?</li>
                                            <li>HOW LONG DO WE KEEP YOUR INFORMATION?</li>
                                            <li>HOW DO WE KEEP YOUR INFORMATION SAFE?</li>
                                            <li>DO WE COLLECT INFORMATION FROM MINORS?</li>
                                            <li>WHAT ARE YOUR PRIVACY RIGHTS?</li>
                                            <li>CONTROLS FOR DO-NOT-TRACK FEATURES</li>
                                            <li>DO CALIFORNIA RESIDENTS HAVE SPECIFIC PRIVACY RIGHTS?</li>
                                            <li>DO WE MAKE UPDATES TO THIS NOTICE?</li>
                                            <li>HOW CAN YOU CONTACT US ABOUT THIS NOTICE?</li>
                                            <li>HOW CAN YOU REVIEW, UPDATE, OR DELETE THE DATA WE COLLECT FROM YOU?</li>
                                        </ol>


                                        <p><b>1. WHAT INFORMATION DO WE COLLECT?</b></p>		
                                        <p>Personal information you disclose to us</p>	
                                        <p>In Short: We collect personal information that you provide to us.</p>	
                                        <p>	
                                            We collect personal information that you voluntarily provide to us when you register on the Services, express an interest in obtaining 
                                            information about us or our products and Services, when you participate in activities on the Services, or otherwise when you contact us. 
                                        </p>	
                                        <p>	
                                            Personal Information Provided by You. The personal information that we collect depends on the context of your interactions with us and the 
                                            Services, the choices you make, and the products and features you use. The personal information we collect may include the following: <br/>
                                            names <br/>phone numbers <br/>email addresses <br/>job titles <br/>usernames <br/>passwords <br/>contact preferences<br/>
                                            next of kin name <br/>seaman specific medical fitness <br/> oguk medical ftw <br/>medical fitness certificate <br/>blood group <br/>
                                            health conditions <br/>educational institute <br/> bank account details <br/>identity documents <br/>
                                            training certification details <br/> Sensitive Information. We do not process sensitive information.<br/><br/>
                                            All personal information that you provide  to us must be true, complete, and accurate, and you must notify us of any changes to such 
                                            personal information. 
                                        </p>

                                        <p><b>2. HOW DO WE PROCESS YOUR INFORMATION?</b></p>	
                                        <p>
                                            In Short: We process your information to provide, improve, and administer our Services, communicate with you, 
                                            for security and fraud prevention, and to comply with law. We may also process your information for other purposes with your consent. 
                                        </p>	
                                        <p>	
                                            We process your personal information for a variety of reasons, depending on how you interact with our Services, including: 
                                        </p>
                                        <p>
                                            To facilitate account creation and authentication and otherwise manage user accounts. We may process your information so you can create and log in 
                                            to your account, as well as keep your account in working order. To deliver and facilitate delivery of services to the user. We may process your 
                                            information to provide you with the requested service. 
                                        </p><br/>	


                                        <p><b>3. WHEN AND WITH WHOM DO WE SHARE YOUR PERSONAL INFORMATION?</b></p>
                                        <p>
                                            In Short: We may share information in specific situations described in this section and/or with the following 
                                            third parties. 
                                        </p>
                                        <p>
                                            We may need to share your personal information in the following situations: <br/>
                                            Business Transfers. We may share or transfer your information in connection with, or during negotiations 
                                            of, any merger, sale of company assets, financing, or acquisition of all or a portion of our business to another company. 
                                        </p>

                                        <p><b>4. HOW LONG DO WE KEEP YOUR INFORMATION?</b></p>	
                                        <p>
                                            In Short: We keep your information for as long as necessary to fulfill the purposes outlined in this privacy notice unless 
                                            otherwise required by law. 
                                        </p>	
                                        <p>	
                                            We will only keep your personal information for as long as it is necessary for the purposes set out in this privacy 
                                            notice, unless a longer retention period is required or permitted by law (such as tax, accounting, or other legal 
                                            requirements). No purpose in this notice will require us keeping your personal information for longer than the 
                                            period of time in which users have an account with us. 
                                        </p>	
                                        <p>
                                            When we have no ongoing legitimate business need to process your personal information, we will either delete or 
                                            anonymize such information, or, if this is not possible (for example, because your personal information has been 
                                            stored in backup archives), then we will securely store your personal information and isolate it from any further 
                                            processing until deletion is possible. 
                                        </p>	

                                        <p><b>5. HOW DO WE KEEP YOUR INFORMATION SAFE?</b></p>
                                        <p>In Short: We aim to protect your personal information through a system of organizational and technical security measures.</p>	
                                        <p>	
                                            We have implemented appropriate and reasonable technical and organizational security measures designed to protect 
                                            the security of any personal information we process. However, despite our safeguards and efforts to secure your 
                                            information, no electronic transmission over the Internet or information storage technology can be guaranteed to 
                                            be 100% secure, so we cannot promise or guarantee that hackers, cybercriminals, or other unauthorized third parties 
                                            will not be able to defeat our security and improperly collect, access, steal, or modify your information. 
                                            Although we will do our best to protect your personal information, transmission of personal information to 
                                            and from our Services is at your own risk. You should only access the Services within a secure environment. 
                                        </p>	

                                        <p><b>6. DO WE COLLECT INFORMATION FROM MINORS?</b></p>	
                                        <p>In Short: We do not knowingly collect data from or market to children under 18 years of age.</p>	
                                        <p>	
                                            We do not knowingly solicit data from or market to children under 18 years of age. By 
                                            using the Services, you represent that you are at least 18 or that you are the parent or guardian of such a 
                                            minor and consent to such minor dependent's use of the Services. If we learn that personal information 
                                            from users less than 18 years of age has been collected, we will deactivate the account and take reasonable 
                                            measures to promptly delete such data from our records. If you become aware of any data we may 
                                            have collected from children under age 18, please contact us at Info@planetngtech.com. 
                                        </p>	

                                        <p><b>7. WHAT ARE YOUR PRIVACY RIGHTS?</b></p>
                                        <p>In Short: You may review, change, or terminate your account at any time.</p>
                                        <p>
                                            Withdrawing your consent: If we are relying on your consent to process your personal 
                                            information, which may be express and/or implied consent depending on the applicable law, you have the right 
                                            to withdraw your consent at any time. You can withdraw your consent at any time by contacting us by using 
                                            the contact details provided in the section "HOW CAN YOU CONTACT US ABOUT THIS NOTICE?" below. 
                                        </p>	
                                        <p>	
                                            However, please note that this will not affect the lawfulness of the processing before its withdrawal nor, when 
                                            applicable law allows, will it affect the processing of your personal information conducted in reliance on lawful processing grounds other 
                                            than consent. 
                                        </p>
                                        <p>Account Information</p>
                                        <p>
                                                If you would at any time like to review or change the information in your account or terminate your account, you can:<br/>
                                                Log in to your account settings and update your user account.<br/>
                                                Upon your request to terminate your account, we will deactivate or delete your account and information from our active databases. 
                                                However, we may retain some information in our files to prevent fraud, troubleshoot problems, assist with any investigations, 
                                                enforce our legal terms and/or comply with applicable legal requirements.
                                        </p>
                                        <p>If you have questions or comments about your privacy rights, you may email us at Info@planetngtech.com.</p>


                                        <p><b>8. CONTROLS FOR DO-NOT-TRACK FEATURES</b></p>
                                        <p>
                                            Most web browsers and some mobile operating systems and mobile applications include a Do-Not-Track ("DNT") feature or setting 
                                            you can activate to signal your privacy preference not to have data about your online browsing activities monitored and collected. 
                                            At this stage no uniform technology standard for recognizing and implementing DNT signals has been finalized. As such, we do not currently 
                                            respond to DNT browser signals or any other mechanism that automatically communicates your choice not to be tracked online. If a standard 
                                            for online tracking is adopted that we must follow in the future, we will inform you about that practice in a revised version of this privacy notice.
                                        </p>


                                        <p><b>9. DO CALIFORNIA RESIDENTS HAVE SPECIFIC PRIVACY RIGHTS?</b></p>
                                        <p>
                                            In Short: Yes, if you are a resident of California, you are granted specific rights regarding access to your
                                            personal information. 
                                        </p>	
                                        <p>
                                            California Civil Code Section 1798.83, also known as the "Shine The Light" law, permits our 
                                            users who are California residents to request and obtain from us, once a year and free of charge, information 
                                            about categories of personal 
                                            information (if any) we disclosed to third parties for direct marketing purposes and the names and addresses of all third parties with 
                                            which we shared personal information in the immediately preceding calendar year. If you are a California resident and would like to make 
                                            such a request, please submit your request in writing to us using the contact information provided below. 
                                        </p>	
                                        <p>	
                                            If you are under 18 years of age, reside in California, 
                                            and have a registered account with Services, you have the right to request removal of unwanted data that you publicly post on the Services. To 
                                            request removal of such data, please contact us using the contact information provided below and include the email address associated with your 
                                            account and a statement that you reside in California. We will make sure the data is not publicly displayed on the Services, but please be aware 
                                            that the data may not be completely or comprehensively removed from all our systems (e.g., backups, etc.). 
                                        </p>	

                                        <p><b>10. DO WE MAKE UPDATES TO THIS NOTICE? </b></p>
                                        <p>In Short: Yes, we will update this notice as necessary to stay compliant with relevant laws.</p>	
                                        <p>	
                                            We may update this privacy notice from time to time. 
                                            The updated version will be indicated by an updated "Revised" date and the updated version will be effective as soon as it is accessible. 
                                            If we make material changes to this privacy notice, we may notify you either by prominently posting a notice of such changes or by 
                                            directly sending you a notification. We encourage you to review this privacy notice frequently to be informed of how we are protecting 
                                            your information. 
                                        </p>	

                                        <p><b>11. HOW CAN YOU CONTACT US ABOUT THIS NOTICE?</b></p>	
                                        <p>	
                                            If you have questions or comments about this notice, you may email us at <b>Info@planetngtech.com</b> or contact us by post at: 
                                        </p>	
                                        <p>
                                            Planet NextGen Technologies<br/> 
                                            5, Jyoti Wire House, 23 A - Shah Industrial Estate, Off Veera, Desai Road<br/> Andheri (W) <br/>
                                            Mumbai, Maharashtra 400053<br/> 
                                            India 
                                        </p>	
                                        <p><b>12. HOW CAN YOU REVIEW, UPDATE, OR DELETE THE DATA WE COLLECT FROM YOU? </b></p>
                                        <p>
                                            You have the right to request access to the personal information we collect from you, change that information, or delete it. You can access 
                                            all your information from, JourneyXPro Crew Login. Using your credentials you can log into the application and track all your 
                                            infoamtion, also requrest updates in it.
                                        </p>
                                    </div>

                                </div>	
                            </div>
                        </div>
                    </div> 
                </div>
            </div>
        </div>
    <!-- JAVASCRIPT -->
    <script src="../assets/libs/jquery/jquery.min.js"></script>		
    <script src="../assets/js/bootstrap.min.js" type="text/javascript"></script>
    <script src="../assets/libs/bootstrap/js/bootstrap.bundle.min.js"></script> 
    <script src="../assets/libs/metismenu/metisMenu.min.js"></script>
    <script src="../assets/js/app.js"></script>        
    </html:form>
</body>
<%
    } catch (Exception e) {
        e.printStackTrace();
    }
%>
</html>
