/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.jxp.talentpool;

import com.web.jxp.base.Base;
import static com.web.jxp.base.Base.decipher;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 *
 * @author Suraj
 */
public class AnalyticsReportadvFun {
    Base base = new Base();
    int overallcrew =0;
         Connection conn = null;
         PreparedStatement pstmt = null;
         ResultSet rs = null;
     public int getStatusAvailbledata(){
        Base base = new Base();
         int id=0;
          
                String query = "SELECT COUNT(1) FROM t_candidate AS c LEFT JOIN t_clientasset ON ( c.i_clientassetid = t_clientasset.i_clientassetid ) LEFT JOIN t_country ON ( t_country.i_countryid = t_clientasset.i_countryid )\n" +
                                "LEFT JOIN t_city ON ( c.i_cityid = t_city.i_cityid ) LEFT JOIN t_position ON ( c.i_positionid = t_position.i_positionid ) LEFT JOIN t_grade ON (t_grade.i_gradeid = t_position.i_gradeid) \n" +
                                "LEFT JOIN t_client ON ( c.i_clientid = t_client.i_clientid ) WHERE 0 =0 AND c.i_vflag IN (3,4) AND c.i_pass =2  AND c.i_clientid >= 0 AND c.i_progressid =0 AND c.i_clientid =0 ";
            try 
            {
                conn = base.getConnection();
                pstmt = conn.prepareStatement(query);
//                System.out.println("AnaliticsReportAction.getclientid( )::"+pstmt.toString());
                rs = pstmt.executeQuery();
                while (rs.next()) 
                {
                id = rs.getInt(1);
                 System.out.println("id::"+id);
                }
            } 
            catch (Exception e) 
            {
                e.printStackTrace();
            } 
        finally {
            base.close(conn, pstmt, rs);
        }
        
       return id;   
    }
    public int getCandidateListForExcel(int clientId1,int assetID ){
    ArrayList list = new ArrayList();
        Base base = new Base();
        String name, countryName, date, positionName = "", prefferdept,
                    firstName, gradeName, clientName, clientAsset, position, city,
                    primarycontact, econtact, seccontact, email, assettype, candidatelocation,
                    dob, placeofbirth, gender, maritalstatus, Nextofkin, Relation, address1line1, address1line2, address1line3,
                    address2line1, address2line2, address2line3, employeeId, isdcode1, isdcode2, isdcode3, nationality, currency,
                    fromDate, remarks, endDate, reason;
            int candidateId, status, progressId, alertCount, vflag, clientId, ct1, ct2, ct3, ct4, ct5, ct6, ct7, ct8, ct9, expectedsalary;
            double rate1, rate2;
            String query = " SELECT c.i_candidateid,DATE_FORMAT(c.ts_regdate, '%d-%b-%Y'), CONCAT_WS(' ', c.s_firstname, c.s_middlename, c.s_lastname),  t_country1.s_name, c.i_status,  t_position.s_name,  c.s_firstname, t_client.s_name,t_clientasset.s_name, t_grade.s_name , c.i_progressid, a1.ct , c.i_vflag, c.i_clientid, t_city.s_name, c.s_contactno1, c.s_econtactno1, c.s_contactno2,  DATE_FORMAT(c.d_dob, '%d-%b-%Y') , c.s_placeofbirth, c.s_gender, t_maritialstatus.s_name, c.s_nextofkin, t_relation.s_name, c.s_address1line1, c.s_address1line2, c.s_address1line3, c.s_address2line1, c.s_address2line2, c.s_address2line3, c.s_email, c.s_code1, c.s_code2, c.s_code3, c.s_empno, t_assettype.s_name, t_country2.s_name, t_experiencedept.s_name, t1.ct, t2.ct, t3.ct, t4.ct, t5.ct, t6.ct, t8.ct, t9.ct, t_country1.s_nationality, t_currency.s_name, c.d_rate1, c.d_rate2, c.i_expectedsalary, DATE_FORMAT(c1.fromdate, '%d-%b-%Y'), tr2.remark, tr2.reason, enddate FROM t_candidate AS c  LEFT JOIN (SELECT i_candidateid, COUNT(1) AS ct FROM t_alert WHERE i_status = 1 GROUP BY i_candidateid) AS a1 ON ( c.i_candidateid = a1.i_candidateid ) LEFT JOIN t_clientasset ON ( c.i_clientassetid = t_clientasset.i_clientassetid ) LEFT JOIN t_country AS t_country1 ON ( t_country1.i_countryid = t_clientasset.i_countryid ) LEFT JOIN t_country AS t_country2 ON ( t_country2.i_countryid = c.i_countryid ) LEFT JOIN t_city ON ( c.i_cityid = t_city.i_cityid ) LEFT JOIN t_position ON ( c.i_positionid = t_position.i_positionid ) LEFT JOIN t_grade ON (t_grade.i_gradeid = t_position.i_gradeid) LEFT JOIN t_client ON ( c.i_clientid = t_client.i_clientid) LEFT JOIN t_maritialstatus ON (t_maritialstatus.i_maritialstatusid = c.i_maritialstatusid) LEFT JOIN t_relation ON (t_relation.i_relationid = c.i_relationid) LEFT JOIN t_assettype ON (t_assettype.i_assettypeid = c.i_assettypeid) LEFT JOIN t_experiencedept ON (t_experiencedept.i_experiencedeptid = c.i_departmentid) LEFT JOIN t_currency ON (t_currency.i_currencyid = c.i_currencyid) LEFT JOIN  (SELECT i_crewrotationid, i_candidateid FROM t_crewrotation GROUP BY i_candidateid) AS cr ON (c.i_candidateid = cr.i_candidateid ) LEFT JOIN (SELECT i_crewrotationid, MIN(ts_fromdate) AS fromdate FROM t_cractivity WHERE i_activityid = 6 GROUP BY  i_crewrotationid) AS c1 ON (cr.i_crewrotationid = c1.i_crewrotationid) LEFT JOIN (SELECT tr1.i_candidateid, tr1.s_remark AS remark, tr1.s_reason AS reason, DATE_FORMAT(tr1.s_enddate, '%d-%b-%Y') AS enddate FROM (SELECT * FROM t_transfer WHERE i_type = 2 ORDER BY s_enddate DESC, ts_regdate DESC) AS tr1 GROUP BY i_candidateid) AS tr2 ON (c.i_candidateid = tr2.i_candidateid) LEFT JOIN (SELECT i_candidateid, COUNT(1) AS ct FROM t_candlang GROUP BY i_candidateid) AS t1 ON (t1.i_candidateid = c.i_candidateid) LEFT JOIN (SELECT i_candidateid, COUNT(1) AS ct FROM t_healthdeclaration GROUP BY i_candidateid) AS t2 ON (t2.i_candidateid = c.i_candidateid) LEFT JOIN (SELECT i_candidateid, COUNT(1) AS ct FROM t_vbd GROUP BY i_candidateid) AS t3 ON (t3.i_candidateid = c.i_candidateid) LEFT JOIN (SELECT i_candidateid, COUNT(1) AS ct FROM t_workexperience GROUP BY i_candidateid) AS t4 ON (t4.i_candidateid = c.i_candidateid) LEFT JOIN (SELECT i_candidateid, COUNT(1) AS ct FROM t_eduqual GROUP BY i_candidateid) AS t5 ON (t5.i_candidateid = c.i_candidateid) LEFT JOIN (SELECT i_candidateid, COUNT(1) AS ct FROM t_trainingandcert GROUP BY i_candidateid) AS t6 ON (t6.i_candidateid = c.i_candidateid) LEFT JOIN (SELECT i_candidateid, COUNT(1) AS ct FROM t_bankdetail GROUP BY i_candidateid) AS t8 ON (t8.i_candidateid = c.i_candidateid) LEFT JOIN (SELECT i_candidateid, COUNT(1) AS ct FROM t_govdoc GROUP BY i_candidateid) AS t9 ON (t9.i_candidateid = c.i_candidateid) WHERE 0 = 0  AND c.i_vflag IN  (3,4) AND c.i_pass = 2   AND c.i_clientid >= 0 AND c.i_clientid = ? AND c.i_clientassetid = ? ORDER BY c.i_candidateid";
            try 
            {
                conn = base.getConnection();
                pstmt = conn.prepareStatement(query);
                 pstmt.setInt(1,clientId1);
                pstmt.setInt(2,assetID);
                rs = pstmt.executeQuery();
                while (rs.next()) 
            {
                candidateId = rs.getInt(1);
                date = rs.getString(2) != null ? rs.getString(2) : "";
                name = rs.getString(3) != null ? rs.getString(3) : "";
                countryName = rs.getString(4) != null ? rs.getString(4) : "";
                status = rs.getInt(5);
                position = rs.getString(6) != null ? rs.getString(6) : "";
                firstName = rs.getString(7) != null ? rs.getString(7) : "";
                clientName = rs.getString(8) != null ? rs.getString(8) : "";
                clientAsset = rs.getString(9) != null ? rs.getString(9) : "";
                gradeName = rs.getString(10) != null ? rs.getString(10) : "";
                progressId = rs.getInt(11);
                alertCount = rs.getInt(12);
                vflag = rs.getInt(13);
                clientId = rs.getInt(14);
                city = rs.getString(15) != null ? rs.getString(15) : "";
                primarycontact = decipher(rs.getString(16) != null ? rs.getString(16) : "");
                econtact = decipher(rs.getString(17) != null ? rs.getString(17) : "");
                seccontact = decipher(rs.getString(18) != null ? rs.getString(18) : "");
                dob = rs.getString(19) != null ? rs.getString(19) : "";
                placeofbirth = rs.getString(20) != null ? rs.getString(20) : "";
                gender = rs.getString(21) != null ? rs.getString(21) : "";
                maritalstatus = rs.getString(22) != null ? rs.getString(22) : "";
                Nextofkin = decipher(rs.getString(23) != null ? rs.getString(23) : "");
                Relation = rs.getString(24) != null ? rs.getString(24) : "";
                address1line1 = rs.getString(25) != null ? rs.getString(25) : "";
                address1line2 = rs.getString(26) != null ? rs.getString(26) : "";
                address1line3 = rs.getString(27) != null ? rs.getString(27) : "";
                address2line1 = rs.getString(28) != null ? rs.getString(28) : "";
                address2line2 = rs.getString(29) != null ? rs.getString(29) : "";
                address2line3 = rs.getString(30) != null ? rs.getString(30) : "";
                email = rs.getString(31) != null ? rs.getString(31) : "";
                isdcode1 = rs.getString(32) != null ? rs.getString(32) : "";
                isdcode2 = rs.getString(33) != null ? rs.getString(33) : "";
                isdcode3 = rs.getString(34) != null ? rs.getString(34) : "";
                employeeId = rs.getString(35) != null ? rs.getString(35) : "";
                assettype = rs.getString(36) != null ? rs.getString(36) : "";
                candidatelocation = rs.getString(37) != null ? rs.getString(37) : "";
                prefferdept = rs.getString(38) != null ? rs.getString(38) : "";
                ct1 = rs.getInt(39);
                ct2 = rs.getInt(40);
                ct3 = rs.getInt(41);
                ct4 = rs.getInt(42);
                ct5 = rs.getInt(43);
                ct6 = rs.getInt(44);
                ct8 = rs.getInt(45);
                ct9 = rs.getInt(46);
                nationality = rs.getString(47) != null ? rs.getString(47) : "";
                currency = rs.getString(48) != null ? rs.getString(48) : "";
                rate1 = rs.getDouble(49);
                rate2 = rs.getDouble(50);
                expectedsalary = rs.getInt(51);
                fromDate = rs.getString(52) != null ? rs.getString(52) : "";       
                remarks = rs.getString(53) != null ? rs.getString(53) : "";       
                reason = rs.getString(54) != null ? rs.getString(54) : "";       
                endDate = rs.getString(55) != null ? rs.getString(55) : "";       
                
                if (gradeName != null && !gradeName.equals("")) {
                    positionName = position + " | " + gradeName;
                }
                if (primarycontact != null && !primarycontact.equals("")) {
                    if (isdcode1 != null && !isdcode1.equals("")) {
                        primarycontact = "+" + isdcode1 + " " + primarycontact;
                    }
                }
                if (econtact != null && !econtact.equals("")) {
                    if (isdcode2 != null && !isdcode2.equals("")) {
                        econtact = "+" + isdcode2 + " " + econtact;
                    }
                }
                if (seccontact != null && !seccontact.equals("")) {
                    if (isdcode3 != null && !isdcode3.equals("")) {
                        seccontact = "+" + isdcode3 + " " + seccontact;
                    }
                }
                list.add(new TalentpoolInfo(candidateId, name, countryName, status, date, positionName, firstName, clientName, 
                    clientAsset, progressId, alertCount, vflag, clientId, gradeName, position, city, primarycontact, econtact, 
                    seccontact, dob, placeofbirth, gender, maritalstatus, Nextofkin, Relation, address1line1, address1line2, 
                    address1line3, address2line1, address2line2, address2line3, email, employeeId, assettype, candidatelocation, 
                    prefferdept, ct1, ct2, ct3, ct4, ct5, ct6, ct8, ct9, nationality,currency, rate1, rate2, expectedsalary, fromDate, remarks, reason, endDate));
            }
            } 

            catch (Exception e) 
            {
                e.printStackTrace();
            } 
          finally {
            base.close(conn, pstmt, rs);
           }
            float overallavarage =0;
            int total = list.size();
            
                for(int i = 0; i < total;i++)
                {
                TalentpoolInfo info = (TalentpoolInfo) list.get(i); 
                if(info != null)
                {      
                    float personalDataAvr=0;
                    float totalDataArv=0; 
                    String  certified = "", statusvalue = "", progress ="";
                    
                    if(info.getVflag() == 4)
                    certified = "Certified";
                    else
                    certified = "Uncertified";
                    
                    if(info.getClientId() > 0)
                    statusvalue = "Not Available";
                    else 
                    statusvalue = "Available";    
                    
                    if(info.getProgressId() > 0)
                    progress = "Locked";
                    else 
                    progress = "Unlocked";  
                    
                    //For Client Imp : we can also add blank data inside alse block( cell.setCellType(cell.CELL_TYPE_BLANK);)
                    if(info.getClientAsset() != null && !info.getClientAsset().equals("")){
                    ++personalDataAvr;
                    totalDataArv++;
                    }
                  
                    //for Asset
                     if(info.getAssettype() != null && !info.getAssettype().equals(""))
                     {
                     totalDataArv++;
                     ++personalDataAvr;
                     }
                    
                    // for Employee Id
                    if(info.getEmployeeId() != null && !info.getEmployeeId().equals(""))
                    {
                    ++personalDataAvr;
                    totalDataArv++;
                    }
                    //  for Name
                    if(info.getName()!= null && !info.getName().equals(""))
                    {
                    ++personalDataAvr;
                    totalDataArv++;
                    } 
                   //  for Position-Rank,
                    if(info.getPosition()!= null && ! info.getPosition().equals(""))
                    {
                     ++personalDataAvr;
                     totalDataArv++;
                    }
                    
                    //for Primary Contact Number
                     if(info.getContactno1() != null && !info.getContactno1().equals(""))
                     {
                        ++personalDataAvr;
                        totalDataArv++;
                     }
                     
                    //for Emergency Contact Number
                     if(info.getContactno1() != null && !info.getContactno1().equals(""))
                     {
                        ++personalDataAvr;
                        totalDataArv++;
                     }
                    
                    //For Contact Number 2
                    if(info.getContactno2() != null && !info.getContactno2().equals(""))
                    {
                        ++personalDataAvr;
                        totalDataArv++;
                       
                    }
                    
                  //  For Additional contact number
                    if(info.getContactno3() != null && !info.getContactno3().equals(""))
                    {
                        ++personalDataAvr;
                        totalDataArv++;
                    }
                   
                    
                    if(info.getEmailId() != null && !info.getEmailId().equals(""))
                    {
                        ++personalDataAvr;
                        totalDataArv++;
                    }
                    
                    
                    //For DOB
                    if(info.getDob() != null && !info.getDob().equals(""))
                    {
                        ++personalDataAvr;
                        totalDataArv++;
                    }
                    
                    //For Place of birth
                    if(info.getPlaceofbirth() != null && !info.getPlaceofbirth().equals(""))
                    {
                         ++personalDataAvr;
                         totalDataArv++;
                        
                    }
                    
                    
                    //For Gender
                    if(info.getGender() != null && !info.getGender().equals(""))
                    {
                         ++personalDataAvr;
                         totalDataArv++;
                    }
                   //for Marital Status
                    
                    if(info.getMaritialstatus() != null && !info.getMaritialstatus().equals(""))
                    {
                         ++personalDataAvr;
                         totalDataArv++;
                         
                    }
                    
                   // For Nextofkin()
                    
                    if(info.getNextofkin() != null && !info.getNextofkin().equals(""))
                    {
                         ++personalDataAvr;
                         totalDataArv++;
                    }
                    
                    //For getting relation
                    if(info.getRelation() != null && !info.getRelation().equals(""))
                    {
                         ++personalDataAvr;
                         totalDataArv++;
                    }
                    
                   // For getting cuontry
                    if(info.getCountryName() != null && !info.getCountryName().equals(""))
                    {
                         ++personalDataAvr;
                         totalDataArv++;
                         
                    }
                   
                    if(info.getCity() != null && !info.getCity().equals(""))
                    {
                         ++personalDataAvr;
                         totalDataArv++;
                    }
                    
                    if(info.getAddress1line1() != null && !info.getAddress1line1().equals(""))
                    {
                         ++personalDataAvr;
                         totalDataArv++;
                         
                    }
                   
                    if(info.getAddress1line2() != null && !info.getAddress1line2().equals(""))
                    {
                         ++personalDataAvr;
                         totalDataArv++;
                         
                    }
                    
                    if(info.getAddress1line3() != null && !info.getAddress1line3().equals(""))
                    {
                         ++personalDataAvr;
                         totalDataArv++;
                    }
                   
                    if(info.getAddress2line1() != null && !info.getAddress2line1().equals(""))
                    {
                         ++personalDataAvr;
                         totalDataArv++;
                    }
                    
                    if(info.getAddress2line2() != null && !info.getAddress2line2().equals(""))
                    {
                         ++personalDataAvr;
                         totalDataArv++;
                        
                    }
                    
                    if(info.getAddress2line3() != null && !info.getAddress2line3().equals(""))
                    {
                         ++personalDataAvr;
                         totalDataArv++;
                    }
                    
                    if(info.getNationality() != null && !info.getNationality().equals(""))
                    {
                         ++personalDataAvr;
                         totalDataArv++;
                        
                    }
                     
                    //for getting department
                    if(info.getDepartment() != null && !info.getDepartment().equals(""))
                    {
                         ++personalDataAvr;
                         totalDataArv++;
                    }
                   
                    
                    //for getting currency details
                   
                    if(info.getCurrency() != null && !info.getCurrency().equals(""))
                    {
                         ++personalDataAvr;
                         totalDataArv++;
                         
                    }
                    
                    
                    //for getting expacted salary
                    
                    if(info.getExpectedsalary() != 0)
                    {
                        ++personalDataAvr; 
                        totalDataArv++;
                        
                    }
                                   
               
                    //for Crew Rate 
                    
                    if(info.getRate1()!=0)
                    {
                        ++personalDataAvr;
                        totalDataArv++;
                    }   
                    
                    //Overtime
                   
                    if(info.getRate2()!=0)
                    {
                        ++personalDataAvr;
                        totalDataArv++;
                       
                    }   

                    float otherDataFlg =0;
                    if(info.getCt1()!=0)
                    {
                        otherDataFlg++;
                        totalDataArv++;
                    }
           
                    if(info.getCt2()!=0)
                        {
                        otherDataFlg++;
                        totalDataArv++;
                        
                        }
                  
                    if(info.getCt3()!=0)
                    {
                            otherDataFlg++;
                            totalDataArv++;
                            
                    }
                 
                    if(info.getCt4()!=0)
                    {
                        otherDataFlg++;
                        totalDataArv++;

                    }
                  
                    if(info.getCt5()!=0)
                    {                  
                        otherDataFlg++;   
                        totalDataArv++;
                    }
                   
                    //for certification
                    if(info.getCt6()!=0)  
                    {                  
                        otherDataFlg++; 
                        totalDataArv++;
                    }
                    //for bank details
                    if(info.getCt8()!=0) 
                    {                  
                        otherDataFlg++; 
                        totalDataArv++;
                    }

                    //for Documents
                    if(info.getCt9()!=0)  
                    {                  
                        otherDataFlg++; 
                        totalDataArv++;
                    }
                   
                    otherDataFlg = (otherDataFlg / 8);
                    otherDataFlg =otherDataFlg * 100;
                    
                   double otherroundoffvalue = Math.ceil(otherDataFlg);
                   int otherroundoffvalueint = (int)otherroundoffvalue;
                   
                    //for personal data
                    personalDataAvr = (personalDataAvr / 30);
                    personalDataAvr = personalDataAvr * 100;
                    double personalDataAvrroundoff = Math.ceil(personalDataAvr);
                    int personalDataAvrroundoffint = (int)personalDataAvrroundoff;
                    
                    //for totaldata avarage
                    totalDataArv = (totalDataArv/38);
                    totalDataArv =(totalDataArv * 100);
                    
                     double totalDataArvroundof = Math.ceil(totalDataArv);
                     int totalDataArvroundofint = (int)totalDataArvroundof;
                   
                    overallavarage =overallavarage + totalDataArv;
                }
                info = null;  
            }
                float overallavaragepercatage = (overallavarage/total);
                double overallavaragepercatageint = Math.ceil(overallavaragepercatage);
                int overallavaragepercatageintafter = (int)overallavaragepercatageint;
                return overallavaragepercatageintafter;
    }
    
    public int getrotations(int clientId,int assetID){
//    String toDate = "2023-01-01";
    String frmDate ="";
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    LocalDateTime now = LocalDateTime.now();
    frmDate = dtf.format(now);
    ArrayList<Integer> list = new ArrayList<>();
    String query ="select  t1.ct  from t_crewrotation as cr left join t_candidate as c on (c.i_candidateid = cr.i_candidateid) LEFT JOIN t_position ON (t_position.i_positionid = c.i_positionid) LEFT JOIN t_grade ON (t_grade.i_gradeid = t_position.i_gradeid)  left join t_rotation on (t_rotation.i_rotationid = t_position.i_rotationid) left join (select i_crewrotationid, count(1) as ct from t_cractivity where i_activityid = 6 and i_signoffid > 0 and (ts_todate >= '2023-01-01' and ts_todate <= ?) group by i_crewrotationid) as t1 on (t1.i_crewrotationid = cr.i_crewrotationid)  left join (select t_cractivity.i_crewrotationid, ts_fromdate, ts_todate, SUM(DATEDIFF(case when ts_todate>  ? then  ? when ts_todate < '2023-01-01' then '2023-01-01'  else ts_todate end, case when ts_fromdate < '2023-01-01' then '2023-01-01'  else ts_fromdate end)) as ct1 from t_cractivity  left join t_signonoff on (t_signonoff.i_signonoffid = t_cractivity.i_signoffid and t_signonoff.i_subtype in(1,2) and t_signonoff.i_type = 2 and t_cractivity.i_status = 2 and t_cractivity.i_activityid = 6) where t_cractivity.i_status = 2 and i_activityid = 6 and t_signonoff.i_subtype in(1,2) and t_signonoff.i_type = 2  and ((ts_fromdate <= '2023-01-01' and ts_todate >= '2023-01-01') or (ts_fromdate <= ? and ts_todate >= ?) or (ts_fromdate >= '2023-01-01' and ts_fromdate <= ?) or (ts_todate >= '2023-01-01' and ts_todate <= ?)) group by t_cractivity.i_crewrotationid) as t2 on (t2.i_crewrotationid = cr.i_crewrotationid)  left join (select t_cractivity.i_crewrotationid, ts_fromdate, ts_todate, SUM(DATEDIFF(case when ts_todate>   ? then   ? when ts_todate < '2023-01-01' then '2023-01-01'  else ts_todate end, case when ts_fromdate <  '2023-01-01' then  '2023-01-01'  else ts_fromdate end)) as ct1,SUM(DATEDIFF(case when ts_date2> ? then ? else ts_date2 end, case when ts_date1 > ? then ? else ts_date1 end)) as ct2 from t_cractivity  left join t_signonoff on (t_signonoff.i_signonoffid = t_cractivity.i_signoffid  and t_signonoff.i_subtype = 3 and t_signonoff.i_type = 2 and t_cractivity.i_status = 2) where t_cractivity.i_status = 2 and i_activityid = 6 and t_signonoff.i_subtype = 3 and t_signonoff.i_type = 2  and ts_overstaydate = '0000-00-00' and ((ts_fromdate <= '2023-01-01' and ts_todate >= '2023-01-01') or (ts_fromdate <= ? and ts_todate >= ?) or (ts_fromdate >= '2023-01-01' and ts_fromdate <= ?) or (ts_todate >= '2023-01-01' and ts_todate <= ?)) group by t_cractivity.i_crewrotationid) as t3 on (t3.i_crewrotationid = cr.i_crewrotationid)  left join (select t_cractivity.i_crewrotationid, ts_fromdate, ts_todate, SUM(DATEDIFF(case when ts_todate >  ? then  ? when ts_todate < '2023-01-01' then '2023-01-01'  else ts_todate end, case when ts_fromdate <  '2023-01-01' then  '2023-01-01'  else ts_fromdate end)) as ct1,SUM(DATEDIFF(case when ts_todate >  ? then  ? when ts_todate < '2023-01-01' then '2023-01-01'  else ts_todate end, case when ts_overstaydate >  ? then  ?  when ts_overstaydate <  '2023-01-01' then  '2023-01-01' else ts_overstaydate end)) as ct2, SUM(DATEDIFF(case when ts_date2>  ? then  ?  else ts_date2 end, case when ts_date1 >  ? then  ?  else ts_date1 end)) as ct3 from t_cractivity  left join t_signonoff on (t_signonoff.i_signonoffid = t_cractivity.i_signoffid and t_signonoff.i_type = 2 and t_signonoff.i_subtype = 3 and t_cractivity.i_status = 2) where t_cractivity.i_status = 2 and i_activityid = 6  and t_signonoff.i_subtype = 3 and ts_overstaydate != '0000-00-00' and ((ts_fromdate >= '2023-01-01' and ts_fromdate <= ?) or (ts_todate >= '2023-01-01' and ts_todate <= ?) or ('2023-01-01' >= ts_fromdate and '2023-01-01' <= ts_todate)) group by t_cractivity.i_crewrotationid) as t4 on (t4.i_crewrotationid = cr.i_crewrotationid)  left join (select t_cractivity.i_crewrotationid, ts_fromdate, ts_todate, SUM(DATEDIFF(case when ts_todate>  ? then  ? when ts_todate < '2023-01-01' then '2023-01-01'  else ts_todate end, case when ts_fromdate <  '2023-01-01' then  '2023-01-01'  else ts_fromdate end)+1) as noofdays from t_cractivity where t_cractivity.i_activityid =1 and ((ts_fromdate <= '2023-01-01' and ts_todate >= '2023-01-01') or (ts_fromdate <= ? and ts_todate >= ?) or(ts_fromdate >= '2023-01-01' and ts_fromdate <= ?) or (ts_todate >= '2023-01-01' and ts_todate <= ?)) group by t_cractivity.i_crewrotationid) as t5 on (t5.i_crewrotationid = cr.i_crewrotationid )  left join (select t_cractivity.i_crewrotationid, ts_fromdate, ts_todate, SUM(DATEDIFF(case when ts_todate>  ? then  ? when ts_todate < '2023-01-01' then '2023-01-01'  else ts_todate end, case when ts_fromdate <  '2023-01-01' then  '2023-01-01'  else ts_fromdate end)+1) as noofdays from t_cractivity where t_cractivity.i_activityid =2 and i_status = 1 and ((ts_fromdate <= '2023-01-01' and ts_todate >= '2023-01-01') or (ts_fromdate <= ? and ts_todate >= ?) or(ts_fromdate >= '2023-01-01' and ts_fromdate <= ?) or (ts_todate >= '2023-01-01' and ts_todate <= ?)) group by t_cractivity.i_crewrotationid) as t6 on (t6.i_crewrotationid = cr.i_crewrotationid )  left join (select t_cractivity.i_crewrotationid, ts_fromdate, ts_todate, SUM(DATEDIFF(case when ts_todate>  ? then  ? when ts_todate < '2023-01-01' then '2023-01-01'  else ts_todate end, case when ts_fromdate <  '2023-01-01' then  '2023-01-01'  else ts_fromdate end)+1) as noofdays from t_cractivity where t_cractivity.i_activityid =3 and ((ts_fromdate <= '2023-01-01' and ts_todate >= '2023-01-01') or (ts_fromdate <= ? and ts_todate >= ?) or(ts_fromdate >= '2023-01-01' and ts_fromdate <= ?) or (ts_todate >= '2023-01-01' and ts_todate <= ?)) group by t_cractivity.i_crewrotationid) as t7 on (t7.i_crewrotationid = cr.i_crewrotationid )  left join (select t_cractivity.i_crewrotationid, ts_fromdate, ts_todate, SUM(DATEDIFF(case when ts_todate>  ? then  ? when ts_todate < '2023-01-01' then '2023-01-01'  else ts_todate end, case when ts_fromdate <  '2023-01-01' then  '2023-01-01'  else ts_fromdate end)+1) as noofdays from t_cractivity where t_cractivity.i_activityid =4 and ((ts_fromdate <= '2023-01-01' and ts_todate >= '2023-01-01') or (ts_fromdate <= ? and ts_todate >= ?) or(ts_fromdate >= '2023-01-01' and ts_fromdate <= ?) or (ts_todate >= '2023-01-01' and ts_todate <= ?)) group by t_cractivity.i_crewrotationid) as t8 on (t8.i_crewrotationid = cr.i_crewrotationid )  left join (select t_cractivity.i_crewrotationid, ts_fromdate, ts_todate, SUM(DATEDIFF(case when ts_todate>  ? then  ? when ts_todate < '2023-01-01' then '2023-01-01'  else ts_todate end, case when ts_fromdate <  '2023-01-01' then  '2023-01-01'  else ts_fromdate end)+1) as noofdays from t_cractivity where t_cractivity.i_activityid =5 and ((ts_fromdate <= '2023-01-01' and ts_todate >= '2023-01-01') or (ts_fromdate <= ? and ts_todate >= ?) or(ts_fromdate >= '2023-01-01' and ts_fromdate <= ?) or (ts_todate >= '2023-01-01' and ts_todate <= ?)) group by t_cractivity.i_crewrotationid) as t9 on (t9.i_crewrotationid = cr.i_crewrotationid )  left join (select t_cractivity.i_crewrotationid, ts_fromdate, ts_todate, SUM(DATEDIFF(ts_todate, ts_fromdate)+1) as noofdays from t_cractivity where t_cractivity.i_activityid =3  group by t_cractivity.i_crewrotationid) as t10 on (t10.i_crewrotationid = cr.i_crewrotationid )  LEFT JOIN (select t_cractivity.i_crewrotationid, SUM(coalesce (DATEDIFF(case when ts_todate > CURRENT_DATE then  CURRENT_DATE when ts_todate >  ? then   ? else ts_todate end,  case when ts_fromdate <  '2023-01-01' then  '2023-01-01'  else ts_fromdate end), 0) -  coalesce (DATEDIFF(case when d_rdate2 =  '0000-00-00' then CURRENT_DATE  else d_rdate2 end, d_rdate1), 0) - coalesce (DATEDIFF( CURRENT_DATE, d_rdate2), 0)) as normal, SUM(coalesce (DATEDIFF(case when d_rdate2 =  '0000-00-00' then CURRENT_DATE  else d_rdate2 end, d_rdate1), 0)) as extended, SUM(coalesce (DATEDIFF( CURRENT_DATE, d_rdate2), 0)) as overstay  from t_cractivity  left join t_crewrotation on (t_crewrotation.i_crewrotationid = t_cractivity.i_crewrotationid and t_crewrotation.i_active = 1 and t_cractivity.i_status = 2 and t_cractivity.i_activityid = 6) where t_cractivity.i_signoffid <= 0 and CURRENT_DATE > ts_fromdate and ((ts_fromdate >= '2023-01-01' and ts_fromdate <= ?) or (ts_todate >= '2023-01-01' and ts_todate <= ?))  and t_cractivity.i_status = 2 and t_cractivity.i_activityid = 6  group by t_cractivity.i_crewrotationid) as t15 on (t15.i_crewrotationid = cr.i_crewrotationid)  left join (select t_cractivity.i_crewrotationid, ts_fromdate, ts_todate, SUM(DATEDIFF(case when ts_todate>  ? then  ? when ts_todate < '2023-01-01' then '2023-01-01'  else ts_todate end, case when ts_fromdate < '2023-01-01' then '2023-01-01'  else ts_fromdate end)) as ct1 from t_cractivity  left join t_signonoff on (t_signonoff.i_signonoffid = t_cractivity.i_signoffid and t_signonoff.i_subtype = 2 and t_signonoff.i_type = 2 and t_cractivity.i_status = 2 ) where t_cractivity.i_status = 2 and i_activityid = 6 and t_signonoff.i_subtype = 2 and t_signonoff.i_type = 2  and ((ts_fromdate <= '2023-01-01' and ts_todate >= '2023-01-01') or (ts_fromdate <= ? and ts_todate >= ?) or (ts_fromdate >= '2023-01-01' and ts_fromdate <= ?) or (ts_todate >= '2023-01-01' and ts_todate <= ?)) group by t_cractivity.i_crewrotationid) as t11 on (t11.i_crewrotationid = cr.i_crewrotationid)  where  cr.i_active = 1 AND cr.i_clientid = ? AND cr.i_clientassetid = ? order by c.s_firstname, c.s_middlename, c.s_lastname ";
    try 
            {
                conn = base.getConnection();
                pstmt = conn.prepareStatement(query);
                for(int i = 1; i <= 69 ; i++){
                pstmt.setString(i, frmDate);
                }
                pstmt.setInt(70,clientId);
                pstmt.setInt(71,assetID);
                rs = pstmt.executeQuery();
                while (rs.next()) 
                {
                int id = rs.getInt(1);
//                System.out.println(" rotations :: "+id);
                list.add(id);
                }
                
            } 

            catch (Exception e) 
            {
                e.printStackTrace();
            } 
          finally {
            base.close(conn, pstmt, rs);
        }
    
        int maxValue=0;
 
        // Check maximum element using for loop
        for (Integer integer : list) {
            if (integer > maxValue)
                maxValue = integer;
        }
//        System.out.println("The maximum value is "
//                           + maxValue);
        return maxValue;
    }
    
    
    public ArrayList<String> getAssetList(int clientId){
    ArrayList list = new ArrayList();
  
        Base base = new Base();
        
                String query = "SELECT  s_name from t_clientasset WHERE i_clientid = ?"; 
            try 
            {
                conn = base.getConnection();
                pstmt = conn.prepareStatement(query);
                pstmt.setInt(1, clientId);
                rs = pstmt.executeQuery();
                while (rs.next()) 
                {
                String name = rs.getString(1);
                System.out.println("i_clientid : "+clientId );
                System.out.println("id::"+name);
                list.add(name);
                }
            } 

            catch (Exception e) 
            {
                e.printStackTrace();
            } 
          finally {
            base.close(conn, pstmt, rs);
        }
         
    return list;
    }
    
    public ArrayList<String> gettingclientname(){
       ArrayList list = new ArrayList();
  
        Base base = new Base();
        
                String query = "select s_name from t_client";
            try 
            {
                conn = base.getConnection();
                pstmt = conn.prepareStatement(query);
                rs = pstmt.executeQuery();
                while (rs.next()) 
                {
                String name = rs.getString(1);
                System.out.println("id::"+name);
                list.add(name);
                }
            } 

            catch (Exception e) 
            {
                e.printStackTrace();
            } 
          finally {
            base.close(conn, pstmt, rs);
        }
         
    return list;
    }
    
    public int gettotalcrewcount(int cid,int aid)        
    {
         Base base = new Base();
         int id=0;
         int clientId =0; 
                String query = "SELECT COUNT(1) FROM t_candidate AS c LEFT JOIN t_clientasset ON ( c.i_clientassetid = t_clientasset.i_clientassetid ) LEFT JOIN t_country ON ( t_country.i_countryid = t_clientasset.i_countryid ) LEFT JOIN t_city ON ( c.i_cityid = t_city.i_cityid ) LEFT JOIN t_position ON ( c.i_positionid = t_position.i_positionid ) LEFT JOIN t_grade ON (t_grade.i_gradeid = t_position.i_gradeid) LEFT JOIN t_client ON ( c.i_clientid = t_client.i_clientid ) WHERE 0 =0 AND c.i_vflag IN (3,4) AND c.i_pass =2  AND c.i_clientid >= 0 AND c.i_clientid = ? AND c.i_clientassetid = ? ";
            try 
            {
                conn = base.getConnection();
                pstmt = conn.prepareStatement(query);
                pstmt.setInt(1, cid);
                pstmt.setInt(2, aid);
//                System.out.println("AnaliticsReportAction.getclientid( )::"+pstmt.toString());
                rs = pstmt.executeQuery();
                while (rs.next()) 
                {
                id = rs.getInt(1);
//               System.out.println("id::"+id);
                }
            } 
            catch (Exception e) 
            {
                e.printStackTrace();
            } 
        finally {
            base.close(conn, pstmt, rs);
        }
        
       return id; 
    }
    
    public int getassetid(int cid,String asset){
         Base base = new Base();
         int id=0;
         int clientId =0; 
                String query = "SELECT i_clientassetid from t_clientasset WHERE i_clientid = ? AND s_name = ?";
            try 
            {
                conn = base.getConnection();
                pstmt = conn.prepareStatement(query);
                pstmt.setInt(1, cid);
                pstmt.setString(2, asset);
//                System.out.println("AnaliticsReportAction.getclientid( )::"+pstmt.toString());
                rs = pstmt.executeQuery();
                while (rs.next()) 
                {
                id = rs.getInt(1);
//                System.out.println("id::"+id);
                }
            } 
            catch (Exception e) 
            {
                e.printStackTrace();
            } 
        finally {
            base.close(conn, pstmt, rs);
        }
       return id;
    }
    
    
    
    public int getclientid(String name)
    {
        int id=0;
        
//        System.out.println("getclientid inside clientid get fucntion");

        Base base = new Base();
        int clientId =0; 
                String query = "select i_clientid from t_client where s_name = ?";
            try 
            {
                conn = base.getConnection();
                pstmt = conn.prepareStatement(query);
                pstmt.setString(1, name);
//                System.out.println("AnaliticsReportAction.getclientid( )::"+pstmt.toString());
                rs = pstmt.executeQuery();
                while (rs.next()) 
                {
                id = rs.getInt(1);
//                System.out.println("id::"+id);
                }
            } 

            catch (Exception e) 
            {
                e.printStackTrace();
            } 
          finally {
            base.close(conn, pstmt, rs);
        }
         return id;   
    }
}
