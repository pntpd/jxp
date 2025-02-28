
import com.web.jxp.base.Base;
import static com.web.jxp.base.Base.getDateAfter;
import com.web.jxp.base.Template;
import com.web.jxp.clientselection.Clientselection;
import com.web.jxp.onboarding.Onboarding;
import com.web.jxp.timesheet.Timesheet;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.zefer.pd4ml.PD4Constants;
import org.zefer.pd4ml.PD4ML;

public class Test extends Base {

//     public String changeNum(int num, int digit) {
//        DecimalFormat nf = new DecimalFormat("0");
//        nf.setMinimumIntegerDigits(digit);         
//        return nf.format(num);
//    }
    /*public String getYMD(int days)
    {
        String s = "";
        int y = 0, m = 0, d = 0;
        if(days > 0)
        {
//            y = days / 365;
//            int yper = days % 365;
            m = days / 30;
//            d = (int)(yper % 30);
        }
//        if(y > 0)
//        {
//            s = y + " Year(s), ";
//        }
        if(m > 0)
        {
            if(s.equals("")) 
                if(d > 0)
                    s = m + " Month(s) ";
                else
                    s = m + " Month(s) ";
            else
                if(d > 0)
                    s += m + " Month(s) ";
                else
                    s += m + " Month(s) ";
        }
//        if(d > 0)
//        {
//            if(s.equals("")) 
//                s = d+ " Day(s)";
//            else
//                s += d+ " Day(s)";
//        }        
        return s;
    }*/
 /*public String getYM(int days)
    {
        String s = "";
        int y = 0, m = 0, d = 0;
        if(days > 0)
        {
            y = days / 365;
            int yper = days % 365;
            m = yper / 30;
            d = (int)(yper % 30);
        }
        if(y > 0)
        {
            s = y + " Year(s), ";
        }
        if(m > 0)
        {
            if(s.equals("")) 
                s = m + " Month(s) ";
            else
                s += m + " Month(s) ";
        }     
        return s;
    }*/
    public static void main(String args[]) throws IOException, Exception {
//        try 
//        {
        Base base = new Base();
//            Onboarding ob = new Onboarding();
        Test tt = new Test();
        String s = "1234";
        try {
            System.out.println("Val :: " + base.cipher(s));
            System.out.println("Val :: " + base.decipher(s));
        } catch (Exception e) {
            e.printStackTrace();
        }

//            String s = "<ul><li>Test</li><li>Design</li><li>Coding</li></ul>";
//            String p1 = s.replaceAll("<ul>", "").replaceAll("</ul>", "").replaceAll("<li>", "").replaceAll("</li>", "\n");
//            System.out.println(p1);
        //Timesheet tm = new Timesheet();
//            tm.getExcelList(3);
//            String ss = tt.getYMD(510);
//            System.out.println("getYMD :: "+ss);
//            ss = tt.getYM(325);
//            System.out.println("getYM :: "+ss);
        //System.out.println("Val :: " + base.decipher("9E0AB26D9E2B37E76D223CFE03D3B732"));
        //System.out.println(t.changeNum(2, 3));
        //createOnboardingFormFile(int shortlistId, int formalityId, int clientId, int assetId, int candidateId) 
        // ob.createOnboardingFormFile(62, 14, 7, 10, 127,  "", "", "", "", "", "", "", "", "", "" );
//            String s = base.decipher("C53B0B039642FDDC32C53D852C10465A");
//            int d = base.getDiffTwoDateInt("2023-04-10", "2023-05-22", "yyyy-MM-dd");
//            System.out.println("d :: " + d);
//            try{
        Clientselection clientselection1 = new Clientselection();
        //String langcontent = clientselection1.createResumeTeamplateFile(370, 2, "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", 49, 0, "", 0.0);
//            }catch(Exception e){
//                e.printStackTrace();
//            }
//        Onboarding documentType = new Onboarding();
//        //documentType.createOnboardingFormFile(121, 17, 11,44, 7,7);
////        Totp obj = new Totp();
////        //String arr[] = obj.getimg(); 
//        Base base = new Base();
//        HashMap hashmap = new HashMap();
//        Template template = new Template("C:\\Pradhyumn\\PradhyumnP\\pd4ml\\1.html");
//        hashmap.put("NAME", "संतोष सिंह");
//        hashmap.put("EMAIL", "santosh@webelementinc.com");
//        String content = template.patch(hashmap);
//        hashmap.clear();
//        content = "संतोष सिंह";
        String content = "BOSIET: Basic Offshore Safety Induction and Emergency Training Crew code will be substituted with crew name for interviews.\n"
                + "Resume submitted by candidate will be attached to the crew profile for the interview round.\n"
                + "\n"
                + "Disclaimer\n"
                + "\n"
                + "All information contained herein is strictly confidential and intended solely for recruitment purposes for the client. Candidates must be contacted and interviewed exclusively through OCS. The company assumes no responsibility for information or verification if contacted directly or through other agents. BOSIET: Basic Offshore Safety Induction and Emergency Training Crew code will be substituted with crew name for interviews.\n"
                + "Resume submitted by candidate will be attached to the crew profile for the interview round.\n"
                + "\n"
                + "Disclaimer\n"
                + "\n"
                + "Disclaimer\n"
                + "\n"
                + "All information contained herein is strictly confidential and intended solely for recruitment purposes for the client. Candidates must be contacted and interviewed exclusively through OCS. The company assumes no responsibility for information or verification if contacted directly or through other agents."
                + ""
                + ""
                + ""
                + ""
                + ""
                + "BOSIET: Basic Offshore Safety Induction and Emergency Training Crew code will be substituted with crew name for interviews.\n"
                + "Resume submitted by candidate will be attached to the crew profile for the interview round.\n"
                + "\n"
                + "Disclaimer\n"
                + "\n"
                + "All information contained herein is strictly confidential and intended solely for recruitment purposes for the client. Candidates must be contacted and interviewed exclusively through OCS. The company assumes no responsibility for information or verification if contacted directly or through other agents."
                + "\n"
                + "All information contained herein is strictly confidential and intended solely for recruitment purposes for the client. Candidates must be contacted and interviewed exclusively through OCS. The company assumes no responsibility for information or verification if contacted directly or through other agents."
                + ""
                + ""
                + "BOSIET: Basic Offshore Safety Induction and Emergency Training Crew code will be substituted with crew name for interviews.\n"
                + "Resume submitted by candidate will be attached to the crew profile for the interview round.\n"
                + "\n"
                + "Disclaimer\n"
                + "\n"
                + "All information contained herein is strictly confidential and intended solely for recruitment purposes for the client. Candidates must be contacted and interviewed exclusively through OCS. The company assumes no responsibility for information or verification if contacted directly or through other agents.";
        String header = "<table style=\"width: 100%;\" align=\"center\">\n"
                + "<tbody>\n"
                + "<tr>\n"
                + "<td style=\"width: 300px;\"><span style=\"font-family: 'arial black', 'avant garde';\"><strong><img src='https://system.journeyxpro.com/jxp/assets/images/header-logo.png' alt=\"\" /><br /><span style=\"font-family: georgia, palatino; font-size: 12px;\">&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; Talent Assurance<br /></span></strong></span></td>\n"
                + "<td style=\"width: 300px; text-align: center;\"><span style=\"font-family: Cambria, serif; font-size: 30px; font-weight: bold;\"> Crew Profile </span></td>\n"
                + "<td style=\"width: 300px;\">&nbsp;</td>\n"
                + "</tr>\n"
                + "</tbody>\n"
                + "</table>";
        //String fbody = "<p><span style=\"text-decoration-line: underline;\"><br />Disclaimer<br /></span>All information contained herein is strictly confidential and intended solely for recruitment purposes for <strong>the client.</strong> Candidates must be contacted and interviewed exclusively through <strong>OCS</strong>. The company assumes no responsibility for information or verification if contacted directly or through other agents.</p>";
        //System.out.println("content :: " + content);
        //File outputPDFFile = new File("C:\\Users\\wesld\\OneDrive\\Documents\\OCS\\lang55.pdf");
        //base.generatePDFString(langcontent, outputPDFFile, PD4Constants.A4, "", header, fbody,"https://system.journeyxpro.com/jxp_data/watermark_Sample.jpg", 1050);
//        try
//        {
//            System.out.println("Val :: " + base.decipher("D3A4C5ACCF70D007CE33B8D94B012578"));
//        }
//        catch(Exception e)
//        {
//            e.printStackTrace();
//        }
//
//        String a =new String("santosh");
//        String b;
//        b=a.intern();
//        System.out.println(a.hashCode() + " $$ " + b.hashCode());
        //System.out.println( getDateAfter("26-01-2023", 1, -1, "dd-MM-yyyy", "yyyy-MM-dd"));

        /*Base base = new Base();
            String curryear = "2023";
            int type = 1;  //1: onshore, 2: offshore
            String date = "2023-03-11";
            if(type == 2)
                date = getDateAfter(date, 1, 28, "yyyy-MM-dd", "yyyy-MM-dd");
            while(base.getDiffTwoDateInt(date, curryear+"-12-31", "yyyy-MM-dd") > 0)
            {    
                String todate = getDateAfter(date, 1, 28, "yyyy-MM-dd", "yyyy-MM-dd");
                System.out.println("From Date :: " + date + ", To Date :: " + todate);
                date = getDateAfter(todate, 1, 28, "yyyy-MM-dd", "yyyy-MM-dd");
            }  */
//        FileWriter writer = new FileWriter("C:\\New folder\\testout.txt");  
//        BufferedWriter buffer = new BufferedWriter(writer);  
//        buffer.write("Welcome to javaTpoint.");  
//        buffer.close();  
//        System.out.println("Success");  
        // System.out.println("Val :: " + base.changeDate1("2024-03-25 12:24:00".substring(0,11)));
//            base.getLocalIp();
        // }
//        catch (Exception ex) 
//        {
//            ex.printStackTrace();
//            System.out.println(ex);
//        }   
    }
}
