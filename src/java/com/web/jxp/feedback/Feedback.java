package com.web.jxp.feedback;

import com.web.jxp.base.Base;
import com.web.jxp.base.StatsInfo;
import com.web.jxp.base.Template;
import static com.web.jxp.common.Common.*;
import com.web.jxp.documentexpiry.Documentexpiry;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.zefer.pd4ml.PD4Constants;

public class Feedback extends Base {

    private static final Logger logger = Logger.getLogger(Class.class.getName());
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    public ArrayList getFeedbackByName(int crewrotationId, String search, int statusIndex, int next, int count, String fromDate, String toDate, int positionId, int clientassetId) {
        ArrayList list = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT t_survey.i_surveyid, t_subcategorywf.s_name, DATE_FORMAT(t_survey.d_completedon, '%d-%b-%Y'), ");
        sb.append("t_survey.i_status, t1.ct, t2.ct ");
        sb.append("FROM t_survey ");
        sb.append("LEFT JOIN t_subcategorywf ON (t_subcategorywf.i_subcategorywfid = t_survey.i_subcategorywfid) ");
        sb.append("LEFT JOIN (SELECT i_surveyid, COUNT(1) AS ct FROM t_surveydetail GROUP BY i_surveyid) AS t1 ON (t_survey.i_surveyid = t1.i_surveyid)");
        sb.append("LEFT JOIN (SELECT i_subcategorywfid, count(1) AS ct FROM t_wellnessmatrix where i_clientassetid = ? AND i_positionid = ? GROUP BY i_subcategorywfid) AS t2 ON (t2.i_subcategorywfid = t_survey.i_subcategorywfid) ");
        sb.append("WHERE t_survey.i_crewrotationid = ? ");
        if (search != null && !search.equals("")) {
            sb.append("AND (t_subcategorywf.s_name LIKE ?) ");
        }

        if (fromDate != null && !fromDate.equals("") && !fromDate.equals("DD-MMM-YYYY") && toDate != null && !toDate.equals("") && !toDate.equals("DD-MMM-YYYY")) {
            sb.append(" AND (DATE_FORMAT(t_survey.d_completedon, '%Y-%m-%d') >= ? AND DATE_FORMAT(t_survey.d_completedon, '%Y-%m-%d') <= ?) ");
        }
        if (statusIndex > 0) {
            if (statusIndex == 2) {
                sb.append("AND t_survey.i_status = 2 ");
            } else if (statusIndex == 1) {
                sb.append("AND t_survey.i_status = 1 AND NOW() < t_survey.ts_expirydate ");
            }
        } else {
            sb.append(" AND (NOW() < t_survey.ts_expirydate OR t_survey.i_status = 2) ");
        }
        sb.append(" ORDER BY t_survey.i_status, t_subcategorywf.s_name ");
        if (count > 0) {
            sb.append(" LIMIT ?,?");
        }
        String query = (sb.toString()).intern();
        sb.setLength(0);

        sb.append(" SELECT COUNT(1) FROM t_survey ");
        sb.append("LEFT JOIN t_subcategorywf ON  (t_subcategorywf.i_subcategorywfid = t_survey.i_subcategorywfid) ");
        sb.append("WHERE t_survey.i_crewrotationid = ? ");
        if (search != null && !search.equals("")) {
            sb.append("AND (t_subcategorywf.s_name LIKE ?) ");
        }
        if (fromDate != null && !fromDate.equals("") && !fromDate.equals("DD-MMM-YYYY") && toDate != null && !toDate.equals("") && !toDate.equals("DD-MMM-YYYY")) {
            sb.append(" AND (DATE_FORMAT(t_survey.d_completedon, '%Y-%m-%d') >= ? AND DATE_FORMAT(t_survey.d_completedon, '%Y-%m-%d') <= ?) ");
        }
        if (statusIndex > 0) {
            if (statusIndex == 2) {
                sb.append("AND t_survey.i_status = 2 ");
            } else if (statusIndex == 1) {
                sb.append("AND t_survey.i_status = 1 AND NOW() <= t_survey.ts_expirydate ");
            }
        } else {
            sb.append(" AND (NOW() <= t_survey.ts_expirydate OR t_survey.i_status = 2) ");
        }
        String countquery = (sb.toString()).intern();
        sb.setLength(0);
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            int scc = 0;
            pstmt.setInt(++scc, clientassetId);
            pstmt.setInt(++scc, positionId);
            pstmt.setInt(++scc, crewrotationId);
            if (search != null && !search.equals("")) {
                pstmt.setString(++scc, "%" + (search) + "%");
            }
            if (fromDate != null && !fromDate.equals("") && !fromDate.equals("DD-MMM-YYYY")) {
                pstmt.setString(++scc, changeDate1(fromDate));
                pstmt.setString(++scc, changeDate1(toDate));
            }
            if (count > 0) {
                pstmt.setInt(++scc, next * count);
                pstmt.setInt(++scc, count);
            }
            logger.info("getFeedbackByName :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String feedback, submissionDate;
            int surveyId, status, questionCount;
            while (rs.next()) 
            {
                surveyId = rs.getInt(1);
                feedback = rs.getString(2) != null ? rs.getString(2) : "";
                submissionDate = rs.getString(3) != null ? rs.getString(3) : "";
                status = rs.getInt(4);
                if (status == 2) {
                    questionCount = rs.getInt(5);
                } else {
                    questionCount = rs.getInt(6);
                }
                list.add(new FeedbackInfo(surveyId, feedback, submissionDate, status, questionCount));
            }
            rs.close();

            pstmt = conn.prepareStatement(countquery);
            scc = 0;
            pstmt.setInt(++scc, crewrotationId);
            if (search != null && !search.equals("")) {
                pstmt.setString(++scc, "%" + (search) + "%");
            }
            if (fromDate != null && !fromDate.equals("") && !fromDate.equals("DD-MMM-YYYY")) {
                pstmt.setString(++scc, changeDate1(fromDate));
                pstmt.setString(++scc, changeDate1(toDate));
            }
            print(this, "getFeedbackByName :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                surveyId = rs.getInt(1);
                list.add(new FeedbackInfo(surveyId, "", "", 0, 0));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return list;
    }

    public FeedbackInfo getSurveyInfo(int surveyIdTop) {
        FeedbackInfo info = null;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT s.i_surveyid, s.i_status, DATE_FORMAT(s.ts_regdate, '%d-%b-%Y'), ");
        sb.append("DATE_FORMAT(s.d_completedon, '%d-%b-%Y'), swf.s_name, s.i_clientassetid, s.i_subcategorywfid, ");
        sb.append("NOW() >= s.ts_expirydate ");
        sb.append("FROM t_survey AS s LEFT JOIN t_subcategorywf AS swf ON (swf.i_subcategorywfid = s.i_subcategorywfid) ");
        sb.append("WHERE s.i_surveyid = ?");
        String query = sb.toString();
        sb.setLength(0);
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, surveyIdTop);
            logger.info(" getSurveyInfo:: " + pstmt.toString());
            rs = pstmt.executeQuery();
            int surveyId, status, clientassetId, subcategorywfId, expired;
            String submissionDate, filleddate, feedback;
            while (rs.next()) 
            {
                surveyId = rs.getInt(1);
                status = rs.getInt(2);
                submissionDate = rs.getString(3) != null ? rs.getString(3) : "";
                filleddate = rs.getString(4) != null ? rs.getString(4) : "";
                feedback = rs.getString(5) != null ? rs.getString(5) : "";
                clientassetId = rs.getInt(6);
                subcategorywfId = rs.getInt(7);
                if (status == 1) {
                    expired = rs.getInt(8);
                } else {
                    expired = 0;
                }
                info = new FeedbackInfo(surveyId, clientassetId, status, submissionDate, filleddate,
                        feedback, subcategorywfId, expired);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return info;
    }

    public ArrayList getQuestionList(int clientassetId, int subcategorywfId, int positionId, int surveyId, int status) {
        ArrayList list = new ArrayList();
        if (status == 1 || status == 2) {
            StringBuilder sb = new StringBuilder();
            if (status == 2) {
                sb.append("SELECT t_question.s_question, sd.s_answer, 0, 0 FROM t_surveydetail AS sd ");
                sb.append(" LEFT JOIN t_question ON (t_question.i_questionid = sd.i_questionid) ");
                sb.append("WHERE sd.i_surveyid = ? ORDER BY t_question.s_question");
            } else if (status == 1) {
                sb.append("SELECT t_question.s_question, t_question.s_addvalues, t_question.i_answertypeid, t_question.i_questionid FROM t_wellnessmatrix AS wm ");
                sb.append(" LEFT JOIN t_question ON (t_question.i_questionid = wm.i_questionid) ");
                sb.append(" LEFT JOIN t_subcategorywf ON (t_subcategorywf.i_subcategorywfid = wm.i_subcategorywfid) ");
                sb.append(" WHERE wm.i_clientassetid = ? AND wm.i_subcategorywfid = ? AND wm.i_positionid = ? ");
                sb.append(" ORDER BY t_question.s_question ");
            }
            String query = sb.toString();
            sb.setLength(0);
            try 
            {
                conn = getConnection();
                pstmt = conn.prepareStatement(query);
                if (status == 2) {
                    pstmt.setInt(1, surveyId);
                } else if (status == 1) {
                    pstmt.setInt(1, clientassetId);
                    pstmt.setInt(2, subcategorywfId);
                    pstmt.setInt(3, positionId);
                }
                logger.info(" getQuestionList:: " + pstmt.toString());
                rs = pstmt.executeQuery();
                String question, answer;
                int answertypeid, questionId;
                while (rs.next()) 
                {
                    question = rs.getString(1) != null ? rs.getString(1) : "";
                    answer = rs.getString(2) != null ? rs.getString(2) : "";
                    answertypeid = rs.getInt(3);
                    questionId = rs.getInt(4);
                    list.add(new FeedbackInfo(question, answer, answertypeid, questionId));
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                close(conn, pstmt, rs);
            }
        }
        return list;
    }

    public String getAnswer(int srno, int type, String ans, int quetstionId) {
        StringBuilder sb = new StringBuilder();
        if (type == 2 && ans != null && !ans.equals("")) {
            String arr[] = ans.split(",");
            int len = arr.length;
            sb.append("<input type='hidden' name='answer' id='answer_" + srno + "' />");
            sb.append("<input type='hidden' name='questionId' value='" + quetstionId + "' />");
            sb.append("<div class='full_width'>");
            sb.append("<div class='mt-radio-inline'>");
            for (int i = 0; i < len; i++) {
                String val = arr[i] != null ? arr[i] : "";
                sb.append("<label class='mt-radio'>");
                sb.append("<input type='radio' name='qradio" + (srno) + "' id='qradio_" + srno + "_" + (i + 1) + "' value='" + val + "' onchange=\"javascript: setVal('" + srno + "', '" + (i + 1) + "');\"> " + val);
                sb.append("<span></span>");
                sb.append("</label>");
            }
            sb.append("</div>");
            sb.append("</div>");
        } else {
            sb.append("<input type='hidden' name='questionId' value='" + quetstionId + "' />");
            sb.append("<textarea name='answer' id='answer_" + srno + "' class='form-control' rows='4' max length='1000'></textarea>");
        }
        String s = sb.toString();
        sb.setLength(0);
        return s;
    }

    public int[] getcountsurvey(int crewrotationId) {
        int arr[] = new int[3];
        int pending = 0, completed = 0, total_count = 0;
        String query = "SELECT COUNT(1) FROM t_survey WHERE i_status = 1 AND NOW() <= ts_expirydate AND i_crewrotationid = ?";
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, crewrotationId);
            logger.info("getcountsurvey_pending :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            while (rs.next()) 
            {
                pending = rs.getInt(1);
            }
            rs.close();
            
            query = "SELECT COUNT(1) FROM t_survey WHERE i_status = 2 AND i_crewrotationid = ?";
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, crewrotationId);
            logger.info("getcountsurvey_completed :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                completed = rs.getInt(1);
            }
            rs.close();
            
            total_count = pending + completed;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        arr[0] = total_count;
        arr[1] = pending;
        arr[2] = completed;
        return arr;
    }

    public int createsurveydetail(int surveyId, int questionId[], String answer[]) {
        int cc = 0;
        int len = questionId.length;
        if (len > 0) 
        {
            try
            {
                String currdate = currDate1();
                String query = "UPDATE t_survey SET i_status = 2, d_completedon = ?, ts_moddate = ? WHERE i_surveyid = ?";
                conn = getConnection();
                pstmt = conn.prepareStatement(query);
                pstmt.setString(1, currDate());
                pstmt.setString(2, currdate);
                pstmt.setInt(3, surveyId);
                print(this, "createsurveydetail :: " + pstmt.toString());
                cc = pstmt.executeUpdate();
                if (cc > 0) 
                {
                    query = "INSERT INTO t_surveydetail (i_surveyid, i_questionid, s_answer, ts_regdate) VALUES (?,?,?,?)";
                    pstmt = conn.prepareStatement(query);
                    for (int i = 0; i < len; i++) {
                        pstmt.setInt(1, surveyId);
                        pstmt.setInt(2, questionId[i]);
                        pstmt.setString(3, answer[i]);
                        pstmt.setString(4, currdate);
                        pstmt.executeUpdate();
                    }
                }
            } catch (Exception exception) {
                print(this, "createsurveydetail :: " + exception.getMessage());
            } finally {
                close(conn, pstmt, rs);
            }
        }
        return cc;
    }

    public ArrayList getCompetencyByName(int candidateId, String search, int statusIndex, int next, int count) {
        ArrayList list = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT t_tracker.i_trackerid, t_pcode.s_role, DATE_FORMAT(t_tracker.d_completebydate1, '%d %b %Y'), DATE_FORMAT(t_tracker.d_onlinedate, '%d %b %Y'), ");
        sb.append("t_tracker.i_online, t_tracker.i_status FROM t_tracker ");
        sb.append("LEFT JOIN t_fcrole ON (t_fcrole.i_fcroleid = t_tracker.i_fcroleid) ");
        sb.append("LEFT JOIN t_pcode ON (t_pcode.i_pcodeid = t_fcrole.i_pcodeid) ");
        sb.append("WHERE t_tracker.i_active =1 AND t_fcrole.i_status =1 AND t_pcode.i_status =1 AND t_tracker.i_candidateid =? ");
        if (search != null && !search.equals("")) {
            sb.append("AND (t_pcode.s_role LIKE ?) ");
        }
        if (statusIndex >= 0) {
            if (statusIndex == 2) {
                sb.append("AND (t_tracker.i_status =3 OR t_tracker.i_online =2)");
            } else if (statusIndex >= 4) {
                sb.append("AND (t_tracker.i_status =?) ");
            } else if (statusIndex < 4) {
                sb.append("AND (t_tracker.i_online =?) ");
            }
        }
        sb.append("ORDER BY t_tracker.d_completebydate1 DESC ");
        if (count > 0) {
            sb.append("LIMIT ?, ? ");
        }
        String query = (sb.toString()).intern();
        sb.setLength(0);

        sb.append("SELECT COUNT(1) FROM t_tracker ");
        sb.append("LEFT JOIN t_fcrole ON (t_fcrole.i_fcroleid = t_tracker.i_fcroleid) ");
        sb.append("LEFT JOIN t_pcode ON (t_pcode.i_pcodeid = t_fcrole.i_pcodeid) ");
        sb.append("WHERE t_tracker.i_active =1 AND t_fcrole.i_status =1 AND t_pcode.i_status =1 AND t_tracker.i_candidateid =? ");
        if (search != null && !search.equals("")) {
            sb.append("AND (t_pcode.s_role LIKE ?) ");
        }
        if (statusIndex >= 0) {
            if (statusIndex == 2) {
                sb.append("AND (t_tracker.i_status =3 OR t_tracker.i_online =2)");
            } else if (statusIndex >= 4) {
                sb.append("AND (t_tracker.i_status =?) ");
            } else if (statusIndex < 4) {
                sb.append("AND (t_tracker.i_online =?) ");
            }
        }

        String countquery = (sb.toString()).intern();
        sb.setLength(0);
        try
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            int scc = 0;
            pstmt.setInt(++scc, candidateId);
            if (search != null && !search.equals("")) {
                pstmt.setString(++scc, "%" + (search) + "%");
            }
            if (statusIndex >= 0 && statusIndex != 2) {
                if (statusIndex >= 4) {
                    pstmt.setInt(++scc, statusIndex);
                } else if (statusIndex < 4) {
                    pstmt.setInt(++scc, statusIndex);
                }
            }
            if (count > 0) {
                pstmt.setInt(++scc, next * count);
                pstmt.setInt(++scc, count);
            }
            logger.info("getCompetencyByName :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            int trackerId, onlineflag, status;
            String role, completeByDate, onlineDate;
            while (rs.next()) 
            {
                trackerId = rs.getInt(1);
                role = rs.getString(2) != null ? rs.getString(2) : "";
                completeByDate = rs.getString(3) != null ? rs.getString(3) : "";
                onlineDate = rs.getString(4) != null ? rs.getString(4) : "";
                onlineflag = rs.getInt(5);
                status = rs.getInt(6);
                list.add(new FeedbackInfo(trackerId, role, completeByDate, onlineDate, onlineflag, status));
            }
            rs.close();

            pstmt = conn.prepareStatement(countquery);
            scc = 0;
            pstmt.setInt(++scc, candidateId);
            if (search != null && !search.equals("")) {
                pstmt.setString(++scc, "%" + (search) + "%");
            }
            if (statusIndex >= 0 && statusIndex != 2) {
                if (statusIndex >= 4) {
                    pstmt.setInt(++scc, statusIndex);
                } else if (statusIndex < 4) {
                    pstmt.setInt(++scc, statusIndex);
                }
            }
            print(this, "getCompetencyByName COUNT :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                trackerId = rs.getInt(1);
                list.add(new FeedbackInfo(trackerId, "", "", "", 0, 0));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return list;
    }

    public FeedbackInfo getPcodeInfo(int TrackId) {
        FeedbackInfo info = null;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT t.i_status, t.i_online, t_pcode.s_role, t_pcode.s_description, t_clientasset.s_name, t_clientasset.s_helpno, t_clientasset.s_helpemail, ");
        sb.append("DATE_FORMAT(t.d_onlinedate, '%d %b %Y'), t.i_fcroleid, DATE_FORMAT(t.d_completebydate1, '%d %b %Y'), t.s_feedbackremarks, t.s_filename, ");
        sb.append("t_client.s_name ");
        sb.append("FROM t_tracker AS t ");
        sb.append("LEFT JOIN t_fcrole ON (t_fcrole.i_fcroleid = t.i_fcroleid) ");
        sb.append("LEFT JOIN t_pcode ON (t_pcode.i_pcodeid = t_fcrole.i_pcodeid) ");
        sb.append("LEFT JOIN t_clientasset ON (t_clientasset.i_clientassetid = t.i_clientassetid) ");
        sb.append("LEFT JOIN t_client ON (t_client.i_clientid = t_clientasset.i_clientid) ");
        sb.append("WHERE t.i_trackerid =? ");
        String query = sb.toString();
        sb.setLength(0);
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, TrackId);
            logger.info("getPcodeInfo :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            int status, onlineflag, fcroleId;
            String role, description, clientasset, helpno, helpemail, onlinedate, completeByDate, feedbackremarks, filename, clientName;
            while (rs.next()) 
            {
                status = rs.getInt(1);
                onlineflag = rs.getInt(2);
                role = rs.getString(3) != null ? rs.getString(3) : "";
                description = rs.getString(4) != null ? rs.getString(4) : "";
                clientasset = rs.getString(5) != null ? rs.getString(5) : "";
                helpno = rs.getString(6) != null ? rs.getString(6) : "";
                helpemail = rs.getString(7) != null ? rs.getString(7) : "";
                onlinedate = rs.getString(8) != null ? rs.getString(8) : "";
                fcroleId = rs.getInt(9);
                completeByDate = rs.getString(10) != null ? rs.getString(10) : "";
                feedbackremarks = rs.getString(11) != null ? rs.getString(11) : "";
                filename = rs.getString(12) != null ? rs.getString(12) : "";
                clientName = rs.getString(13);
                info = new FeedbackInfo(TrackId, status, onlineflag, role, description, clientasset, helpno, helpemail, onlinedate, fcroleId, completeByDate,
                        feedbackremarks, filename, clientName);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return info;
    }

    public Collection getAppealsforclientindex() {
        Collection coll = new LinkedList();
        String xml_path = getMainPath("json_path");
        String str = readHTMLFile("appeal.json", xml_path);
        JSONArray arr = new JSONArray(str);
        if (arr != null) {
            int len = arr.length();
            for (int i = 0; i < len; i++) {
                JSONObject jobj = arr.optJSONObject(i);
                if (jobj != null) {
                    coll.add(new FeedbackInfo(jobj.optInt("id"), jobj.optString("name")));
                }
            }
        }
        return coll;
    }

    public int getAppeal(int Id, int aId, String remark, String crewname) {
        int cc = 0;
        StringBuilder sb = new StringBuilder();
        sb.append("UPDATE t_tracker SET i_appealid =?, s_remarks4 =?, i_status =6 WHERE i_trackerid =?");
        String query = sb.toString().intern();
        sb.setLength(0);
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            int scc = 0;
            pstmt.setInt(++scc, aId);
            pstmt.setString(++scc, remark);
            pstmt.setInt(++scc, Id);
            logger.info(" getAppeal : : " + pstmt.toString());
            cc = pstmt.executeUpdate();
            if (cc > 0) {
                createHistory(conn, Id, crewname + " (Crew)", "Appeal");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cc;
    }

    public ArrayList getQuestionListById(FeedbackInfo info) 
    {
        ArrayList list = new ArrayList();
        if (info != null)
        {
            StringBuilder sb = new StringBuilder();
            if (info.getOnlineflag() > 0) {
                sb.append("SELECT t_trackerdetail.i_trackerdetailid, t_pquestion.i_pquestionid, t_pquestion.s_name, t_pcategory.s_name, t_trackerdetail.s_answer ");
                sb.append("FROM t_trackerdetail ");
                sb.append("LEFT JOIN t_pquestion ON (t_pquestion.i_pquestionid = t_trackerdetail.i_pquestionid) ");
                sb.append("LEFT JOIN t_pcategory ON (t_pcategory.i_pcategoryid = t_pquestion.i_pcategoryid) ");
                sb.append("WHERE t_trackerdetail.i_trackerid =? ORDER BY t_pcategory.s_name, t_pquestion.s_name ");
            } else {
                sb.append("SELECT 0, t_fcroledetail.i_pquestionid, t_pquestion.s_name, t_pcategory.s_name, '' ");
                sb.append("FROM t_fcroledetail ");
                sb.append("LEFT JOIN t_pquestion ON (t_pquestion.i_pquestionid = t_fcroledetail.i_pquestionid) ");
                sb.append("LEFT JOIN t_pcategory ON (t_pcategory.i_pcategoryid = t_fcroledetail.i_pcategoryid) ");
                sb.append("WHERE t_fcroledetail.i_fcroleid =? ORDER BY t_pcategory.s_name, t_pquestion.s_name ");
            }
            String query = sb.toString();
            sb.setLength(0);
            try 
            {
                conn = getConnection();
                pstmt = conn.prepareStatement(query);
                if (info.getOnlineflag() > 0) {
                    pstmt.setInt(1, info.getTrackerId());
                } else {
                    pstmt.setInt(1, info.getFcroleId());
                }
                logger.info(" getQuestionListById:: " + pstmt.toString());
                rs = pstmt.executeQuery();
                int trackerdtlsId, questionId;
                String question, category, answer;
                while (rs.next()) 
                {
                    trackerdtlsId = rs.getInt(1);
                    questionId = rs.getInt(2);
                    question = rs.getString(3) != null ? rs.getString(3) : "";
                    category = rs.getString(4) != null ? rs.getString(4) : "";
                    answer = rs.getString(5) != null ? rs.getString(5) : "";
                    list.add(new FeedbackInfo(trackerdtlsId, questionId, question, category, answer));
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                close(conn, pstmt, rs);
            }
        }
        return list;
    }

    public ArrayList getQuestionListByFcroaleId(int FcroaleId)
    {
        ArrayList list = new ArrayList();
        if (FcroaleId > 0) 
        {
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT t_pquestion.s_name, t_pcategory.s_name FROM t_fcroledetail ");
            sb.append("LEFT JOIN t_pquestion ON (t_pquestion.i_pquestionid = t_fcroledetail.i_pquestionid) ");
            sb.append("LEFT JOIN t_pcategory ON (t_pcategory.i_pcategoryid = t_fcroledetail.i_pcategoryid) ");
            sb.append("WHERE t_fcroledetail.i_fcroleid =? ORDER BY t_pcategory.s_name, t_pquestion.s_name ");
            String query = sb.toString();
            sb.setLength(0);
            try
            {
                conn = getConnection();
                pstmt = conn.prepareStatement(query);
                pstmt.setInt(1, FcroaleId);
                logger.info(" getQuestionListByFcroaleId:: " + pstmt.toString());
                rs = pstmt.executeQuery();
                String question = "", category = "", temp = "";
                while (rs.next()) 
                {
                    question = rs.getString(1) != null ? rs.getString(1) : "";
                    category = rs.getString(2) != null ? rs.getString(2) : "";
                    if (!temp.equals(category)) {
                        list.add(new FeedbackInfo(0, 0, question, category, ""));
                        temp = category;
                    } else {
                        list.add(new FeedbackInfo(0, 0, question, "", ""));
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                close(conn, pstmt, rs);
            }
        }
        return list;
    }

    public int getUpdateAssessment(int trackerId, int qId[], int tId[], String ans[], int online)
    {
        int cc = 0;
        String query = "";
        if (tId.length > 0) {
            try 
            {
                conn = getConnection();
                String currdate = currDate1();
                int scc = 0;
                for (int i = 0; i < tId.length; i++)
                {
                    scc = 0;
                    if (tId[i] > 0) {
                        query = "UPDATE t_trackerdetail SET s_answer =?, ts_moddate =? WHERE i_trackerdetailid =?";
                        pstmt = conn.prepareStatement(query);
                        pstmt.setString(++scc, ans[i]);
                        pstmt.setString(++scc, currdate);
                        pstmt.setInt(++scc, tId[i]);
                        logger.info(" getUpdateAssessment Update:: " + pstmt.toString());
                        cc = pstmt.executeUpdate();
                    } else {
                        query = "INSERT INTO t_trackerdetail (i_trackerid, i_pquestionid, s_answer, i_status, ts_regdate, ts_moddate) VALUES(?, ?, ?, ?, ?, ?)";
                        pstmt = conn.prepareStatement(query);
                        pstmt.setInt(++scc, trackerId);
                        pstmt.setInt(++scc, qId[i]);
                        pstmt.setString(++scc, ans[i]);
                        pstmt.setInt(++scc, 1);
                        pstmt.setString(++scc, currdate);
                        pstmt.setString(++scc, currdate);
                        logger.info(" getUpdateAssessment Insert:: " + pstmt.toString());
                        cc = pstmt.executeUpdate();
                    }
                }
                if (cc > 0) {
                    scc = 0;
                    query = "";
                    StringBuilder sb = new StringBuilder();
                    sb.append("UPDATE t_tracker SET i_online =? ");
                    if (online > 1) {
                        sb.append(",d_onlinedate =?  ");
                    }
                    sb.append("WHERE i_trackerid =? ");
                    query = sb.toString().intern();
                    sb.setLength(0);
                    pstmt = conn.prepareStatement(query);
                    pstmt.setInt(++scc, online);
                    if (online > 1) {
                        pstmt.setString(++scc, currdate);
                    }
                    pstmt.setInt(++scc, trackerId);
                    cc = pstmt.executeUpdate();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return cc;
    }

    public int getUpdateFeedback(int trackerId, String remarks, String crewName)
    {
        int cc = 0;
        if (trackerId > 0) 
        {
            try 
            {
                conn = getConnection();
                int scc = 0;
                pstmt = conn.prepareStatement("UPDATE t_tracker SET s_feedbackremarks =?, d_feedbackdate =? WHERE i_trackerid =?");
                pstmt.setString(++scc, remarks);
                pstmt.setString(++scc, currDate1());
                pstmt.setInt(++scc, trackerId);
                cc = pstmt.executeUpdate();
                createHistory(conn, trackerId, crewName, "Feedback Submitted");
                logger.info(" getUpdateFeedback :: " + pstmt.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return cc;
    }

    public String getContent(String date, String role, ArrayList list, String clientName, String assetName,  String name, String position) 
    {
        HashMap hashmap = new HashMap();
        int list_size = list.size();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list_size; i++) {
            FeedbackInfo info = (FeedbackInfo) list.get(i);
            if (info != null) {
                String categoryName = info.getCategory() != null ? info.getCategory() : "";
                if (!categoryName.equals("")) {
                    sb.append("<tr><td colspan='2' style='height:10px;'></td></tr>");
                    sb.append("<tr>");
                    sb.append("<td colspan='2'>");
                    sb.append("<h4 style='margin: 0px 0px 5px 0px;'>" + categoryName + "</h4>");
                    sb.append("<h5 style='margin: 5px 0px;font-size:13px;>Q" + (i + 1) + ") " + (info.getQuestion() != null ? info.getQuestion() : "") + "</h5>");
                    sb.append("<div style='height:100px;border: 1px solid #000000;'></div>");
                    sb.append("</td>");
                    sb.append("</tr>");
                } else {
                    sb.append("<tr>");
                    sb.append("<td colspan='2'>");
                    sb.append("<h5 style='margin: 5px 0px;font-size:13px;'>Q" + (i + 1) + ") " + (info.getQuestion() != null ? info.getQuestion() : "") + "</h5>");
                    sb.append("<div style='height:100px;border: 1px solid #000000;'></div>");
                    sb.append("</td>");
                    sb.append("</tr>");
                }
            }
        }
        String qlist = sb.toString();
        sb.setLength(0);
        Template template = new Template(getMainPath("template_path") + "question.html");
        hashmap.put("DATE", date);
        hashmap.put("CLIENTNAME", clientName);
        hashmap.put("ASSETNAME", assetName);
        hashmap.put("NAME", name);
        hashmap.put("POSITION", position);
        hashmap.put("ROLE", role);
        hashmap.put("QLIST", qlist);
        String content = template.patch(hashmap);
        hashmap.clear();
        return content;
    }

    public String getGeneratePDF(String role, String date, ArrayList list, int trackerId, String clientName, String assetName, String name, String position) {
        String filePath = getMainPath("add_tracker_file");
        String viewPath = getMainPath("view_trackerfiles");
        java.util.Date now = new java.util.Date();
        String fname = String.valueOf(now.getTime()) + ".pdf";
        String htmlFolderName = dateFolder();
        File dir = new File(filePath + htmlFolderName);
        if (!dir.exists()) {
            dir.mkdir();
        }
        String pdffilename = htmlFolderName + "/" + fname;
        File pdfFile = new File(filePath + pdffilename);
        String content = getContent(date, role, list, clientName, assetName, name, position);
        generatePDFString(content, pdfFile, PD4Constants.A4, "", "","", "", 1050);
        try
        {
            conn = getConnection();
            pstmt = conn.prepareStatement("UPDATE t_tracker SET s_filename =? WHERE i_trackerid =?");
            pstmt.setString(1, pdffilename);
            pstmt.setInt(2, trackerId);
            logger.info(" getGeneratePDF :: " + pstmt.toString());
            pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return viewPath + pdffilename;
    }

    public int createHistory(Connection conn, int trackerId, String username, String action) {
        int clientId = 0;
        try 
        {
            String query = ("INSERT INTO t_thistory (i_trackerid, s_username, s_action, ts_regdate) VALUES (?, ?, ?, ?)");
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, trackerId);
            pstmt.setString(2, username);
            pstmt.setString(3, action);
            pstmt.setString(4, currDate1());
            print(this, "createHistory :: " + pstmt.toString());
            pstmt.executeUpdate();
        } catch (Exception exception) {
            print(this, "createHistory :: " + exception.getMessage());
        } finally {
            close(null, pstmt, rs);
        }
        return clientId;
    }

    // Topiclist
    public ArrayList getTopicList(int clientassetId, int positionId) 
    {
        ArrayList list = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT t_topic.i_topicid, t_topic.s_name ");
        sb.append("FROM t_knowledgematrix ");
        sb.append("LEFT JOIN t_topic ON (t_topic.i_topicid = t_knowledgematrix.i_topicid)  ");
        sb.append("WHERE t_knowledgematrix.i_status = 1 AND t_topic.i_status =1 AND t_knowledgematrix.i_clientassetid = ? AND t_knowledgematrix.i_positionid = ? ");
        sb.append("ORDER BY t_topic.s_name ");
        String query = (sb.toString()).intern();
        sb.setLength(0);
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, clientassetId);
            pstmt.setInt(2, positionId);
            logger.info("getTopicList :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String name;
            int topicId;
            while (rs.next()) 
            {
                topicId = rs.getInt(1);
                name = rs.getString(2) != null ? rs.getString(2) : "";
                list.add(new FeedbackInfo(topicId, name));
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return list;
    }

    // Topic multi files.
    public ArrayList getTopicFiles(int topicId) 
    {
        ArrayList list = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT i_type, s_filename, s_displayname FROM t_topicattachment WHERE i_topicid = ? AND i_status =1 ORDER BY i_type");
        String query = (sb.toString()).intern();
        sb.setLength(0);
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, topicId);
            logger.info("getPics :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String filename, displayname;
            int type;
            while (rs.next())
            {
                type = rs.getInt(1);
                filename = rs.getString(2) != null ? rs.getString(2) : "";
                displayname = rs.getString(3) != null ? rs.getString(3) : "";                
                list.add(new FeedbackInfo(type, filename, displayname));
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return list;
    }
  
    public ArrayList getDocumentListing(int clientId, int clientassetId, String  docsearch)
    {
        ArrayList list = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT s_name, s_filename FROM t_policy WHERE i_clientid = ? AND (i_clientassetid = ? OR i_clientassetid <= 0) ");
        if (docsearch != null && !docsearch.equals("")) {
            sb.append("AND (s_name LIKE ?) ");
        }
        sb.append("ORDER BY s_name");
        String query = sb.toString();
        sb.setLength(0);
        try
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            int scc = 0;
            pstmt.setInt(++scc, clientId);
            pstmt.setInt(++scc, clientassetId);
            if (docsearch != null && !docsearch.equals("")) {
                pstmt.setString(++scc, "%" + (docsearch) + "%");
            }
            logger.info(" getDocumentListing:: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String name, filename;
            while (rs.next()) 
            {
                name = rs.getString(1) != null ? rs.getString(1) : "";
                filename = rs.getString(2) != null ? rs.getString(2) : "";                
                list.add(new FeedbackInfo(name, filename));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
    return list;
    }
    
    public ArrayList getTrainingListing(int candidateId, String tcsearch)
    {
        ArrayList list = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT t_coursename.s_name, t_course.s_elearningfile, t_managetraining.i_managetrainingid ");
        sb.append("FROM t_managetraining ");
        sb.append("LEFT JOIN t_course ON (t_managetraining.i_courseid = t_course.i_courseid) ");
        sb.append("LEFT JOIN t_coursename ON (t_coursename.i_coursenameid = t_course.i_coursenameid) ");
        sb.append("WHERE t_managetraining.i_candidateid = ? AND t_managetraining.i_status = 2 ");
        if (tcsearch != null && !tcsearch.equals("")) {
            sb.append("AND (t_coursename.s_name LIKE ?) ");
        }
        sb.append("ORDER BY t_coursename.s_name");
        String query = sb.toString();
        sb.setLength(0);
        try
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            int scc = 0;
            pstmt.setInt(++scc, candidateId);
            if (tcsearch != null && !tcsearch.equals("")) {
                pstmt.setString(++scc, "%" +(tcsearch) + "%");
            }
            logger.info(" getTrainingListing:: " + pstmt.toString());
            rs = pstmt.executeQuery();
            String coursename, filename;
            int trainingid;
            while (rs.next()) 
            {
                coursename = rs.getString(1) != null ? rs.getString(1) : "";
                filename = rs.getString(2) != null ? rs.getString(2) : "";                
                trainingid = rs.getInt(3);
                list.add(new FeedbackInfo(coursename, filename, trainingid));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
    return list;
    }
    
    public Collection getDocumentTypes()
    {
        Collection coll = new LinkedList();
        coll.add(new FeedbackInfo(-1, "- Select -"));
        String xml_path = getMainPath("json_path");
        String str = readHTMLFile("documenttype.json", xml_path);
        JSONArray arr = new JSONArray(str);
        if(arr != null)
        {
            int len = arr.length();
            for(int i = 0; i < len; i++)
            {
                JSONObject jobj = arr.optJSONObject(i);
                if(jobj != null)
                {
                    coll.add(new FeedbackInfo(jobj.optInt("id"), jobj.optString("name")));
                }
            }
        }
        return coll;
    }
    
    public Collection getDocumentTypesForModule(int mid, int smid)
    {
        Collection coll = new LinkedList();
        coll.add(new FeedbackInfo(-1, "- Select -"));
        String xml_path = getMainPath("json_path");
        String str = readHTMLFile("documenttype.json", xml_path);
        JSONArray arr = new JSONArray(str);
        if(arr != null)
        {
            int len = arr.length();
            for(int i = 0; i < len; i++)
            {
                JSONObject jobj = arr.optJSONObject(i);
                if(jobj != null)
                {
                    String mids = jobj.optString("docmodule");
                    String msids = jobj.optString("docsubmodule");
                    if(mids.contains(""+mid) && msids.contains(""+smid)) 
                        coll.add(new FeedbackInfo(jobj.optInt("id"), jobj.optString("name")));
                }
            }
        }
        return coll;
    }
    
    //Contract listing
    public ArrayList getContractListing(int candidateId, String search,String validFrom, String validTo, 
            int clientId, int assetId, int next, int count) 
    {
        ArrayList list = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT t_contractdetail.i_contractdetailid, t_client.s_name, t_clientasset.s_name, t_position.s_name, t_grade.s_name, t_contract.i_type, "); 
        sb.append("DATE_FORMAT(t_contractdetail.d_fromdate, '%d-%b-%Y '), DATE_FORMAT(t_contractdetail.d_todate, '%d-%b-%Y'), ");
        sb.append("t_contractdetail.s_file1, t_contractdetail.s_file2, t_contractdetail.s_file3, t_contractdetail.i_status, ");
        sb.append("t_contractdetail.i_approval1, t_contractdetail.i_approval2, ");
        sb.append("CURRENT_DATE() < t_contractdetail.d_fromdate , CURRENT_DATE() <= t_contractdetail.d_todate && CURRENT_DATE() >= t_contractdetail.d_fromdate , ");
        sb.append(" CURRENT_DATE() >t_contractdetail.d_todate, t_contractdetail.i_userid, t_contractdetail.i_contractid, DATE_FORMAT(DATE_ADD(t_contractdetail.d_fromdate, INTERVAL -2 DAY), '%d-%b-%Y'), ");
        sb.append("t_contract.s_name, t1.s_name ");
        sb.append("FROM t_contractdetail ");
        sb.append("LEFT JOIN t_clientasset ON (t_clientasset.i_clientassetid = t_contractdetail.i_clientassetid) ");
        sb.append("LEFT JOIN t_client ON ( t_client.i_clientid = t_clientasset.i_clientid) ");
        sb.append("LEFT JOIN t_position ON (t_position.i_positionid = t_contractdetail.i_positionid) ");
        sb.append("LEFT JOIN t_grade ON (t_grade.i_gradeid = t_position.i_gradeid) ");
        sb.append("LEFT JOIN t_contract ON(t_contract.i_contractid = t_contractdetail.i_contractid) ");
        sb.append("LEFT JOIN t_crewrotation ON (t_crewrotation.i_candidateid = t_contractdetail.i_candidateid) ");
        sb.append("LEFT JOIN t_contract AS t1 ON (t_contract.i_refid = t1.i_contractid) ");
        sb.append("WHERE t_contractdetail.i_candidateid = ? AND t_crewrotation.i_active = 1 AND t_contractdetail.i_approval2 = 1 AND t_contractdetail.i_status = 1 ");
        if (search != null && !search.equals("")) {
            sb.append("AND (t_contract.s_name LIKE ?) ");
        }
        if (clientId > 0) {
            sb.append("AND t_client.i_clientid = ? ");
        }
        if (assetId > 0) {
            sb.append("AND t_contractdetail.i_clientassetid = ? ");
        }
        if (validFrom != null && !validFrom.equals("") && !validFrom.equals("DD-MMM-YYYY") && validTo != null && !validTo.equals("") && !validTo.equals("DD-MMM-YYYY")) {
            sb.append(" AND ((t_contractdetail.d_fromdate >= ? AND t_contractdetail.d_fromdate <= ?) ");
            sb.append("OR (t_contractdetail.d_todate >= ? AND t_contractdetail.d_todate <= ?)) ");
        }
        sb.append(" ORDER BY t_contractdetail.i_status, t_contractdetail.d_fromdate DESC ");
        if (count > 0) {
            sb.append(" LIMIT ").append(next * count).append(", ").append(count);
        }
        String query = (sb.toString()).intern();
        sb.setLength(0);
        
        sb.append("SELECT COUNT(1) FROM t_contractdetail ");
        sb.append("LEFT JOIN t_clientasset ON (t_clientasset.i_clientassetid = t_contractdetail.i_clientassetid) ");
        sb.append("LEFT JOIN t_client ON ( t_client.i_clientid = t_clientasset.i_clientid) ");
        sb.append("LEFT JOIN t_position ON (t_position.i_positionid = t_contractdetail.i_positionid) ");
        sb.append("LEFT JOIN t_grade ON (t_grade.i_gradeid = t_position.i_gradeid) ");
        sb.append("LEFT JOIN t_contract ON(t_contract.i_contractid = t_contractdetail.i_contractid) ");
        sb.append("LEFT JOIN t_crewrotation ON (t_crewrotation.i_candidateid = t_contractdetail.i_candidateid) ");
        sb.append("LEFT JOIN t_contract AS t1 ON (t_contract.i_refid = t1.i_contractid) ");
        sb.append("WHERE t_contractdetail.i_candidateid = ? AND t_crewrotation.i_active = 1 AND t_contractdetail.i_approval2 = 1 AND t_contractdetail.i_status = 1 ");
        if (search != null && !search.equals("")) {
            sb.append("AND (t_contract.s_name LIKE ?) ");
        }
        if (clientId > 0) {
            sb.append("AND t_client.i_clientid = ? ");
        }
        if (assetId > 0) {
            sb.append("AND t_contractdetail.i_clientassetid = ? ");
        }
        if (validFrom != null && !validFrom.equals("") && !validFrom.equals("DD-MMM-YYYY") && validTo != null && !validTo.equals("") && !validTo.equals("DD-MMM-YYYY")) {
            sb.append(" AND ((t_contractdetail.d_fromdate >= ? AND t_contractdetail.d_fromdate <= ?) ");
            sb.append("OR (t_contractdetail.d_todate >= ? AND t_contractdetail.d_todate <= ?)) ");
        }
        sb.append(" ORDER BY t_contractdetail.i_status, t_contractdetail.d_fromdate DESC ");
        
        String countquery = (sb.toString()).intern();
        sb.setLength(0);
        
        try
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            int scc = 0;
            pstmt.setInt(++scc, candidateId);
            if (search != null && !search.equals(""))
            {
                pstmt.setString(++scc, "%" + (search) + "%");
            }
            if (clientId > 0) {
                pstmt.setInt(++scc, clientId);
            }
            if (assetId > 0) {
                pstmt.setInt(++scc, assetId);
            }
            if (validFrom != null && !validFrom.equals("") && !validFrom.equals("DD-MMM-YYYY") && validTo != null && !validTo.equals("") && !validTo.equals("DD-MMM-YYYY")) {
                pstmt.setString(++scc, changeDate1(validFrom));
                pstmt.setString(++scc, changeDate1(validTo));
                pstmt.setString(++scc, changeDate1(validFrom));
                pstmt.setString(++scc, changeDate1(validTo));
            }
            logger.info("getContractListing :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            
            while (rs.next())
            {
                int contractdetailId = rs.getInt(1);
                String clientName = rs.getString(2) != null ? rs.getString(2) : "";
                String assetName = rs.getString(3) != null ? rs.getString(3) : "";
                String positionName = rs.getString(4) != null ? rs.getString(4) : "";
                String gradeName = rs.getString(5) != null ? rs.getString(5) : "";
                int type = rs.getInt(6);
                String fromDate = rs.getString(7) != null ? rs.getString(7) : "";
                String toDate = rs.getString(8) != null ? rs.getString(8) : "";
                String file1 = rs.getString(9) != null ? rs.getString(9) : "";
                String file2 = rs.getString(10) != null ? rs.getString(10) : "";
                String file3 = rs.getString(11) != null ? rs.getString(11) : "";
                int status = rs.getInt(12);             
                int approval1 = rs.getInt(13);             
                int approval2 = rs.getInt(14);             
                int flag1 = rs.getInt(15);
                int flag2 = rs.getInt(16);
                int flag3 = rs.getInt(17);
                int userId = rs.getInt(18);
                int contractId = rs.getInt(19);
                String acceptanceDate = rs.getString(20) != null ? rs.getString(20) : "";
                String stval = "";
                String main = rs.getString(21) != null ? rs.getString(21) : "";
                String sub = rs.getString(22) != null ? rs.getString(22) : "";     
                if (!sub.equals("")) {
                    if (!main.equals("")) {
                        main += "("+sub+")";
                    }
                }
                if(status == 2)
                {
                    stval = "In-Active";
                }else{
                    stval = getDateStatusVal(flag1, flag2, flag3);
                }                
                list.add(new FeedbackInfo(contractdetailId, clientName, assetName, positionName, 
                        gradeName, type,fromDate, toDate, file1, file2, file3, status, approval1,
                        approval2, stval, userId, contractId, acceptanceDate, main));
            }
            rs.close();
            
            pstmt = conn.prepareStatement(countquery);
            scc = 0;
            pstmt.setInt(++scc, candidateId);
            if (search != null && !search.equals(""))
            {
                pstmt.setString(++scc, "%" + (search) + "%");
            }
            if (clientId > 0) {
                pstmt.setInt(++scc, clientId);
            }
            if (assetId > 0) {
                pstmt.setInt(++scc, assetId);
            }
            if (validFrom != null && !validFrom.equals("") && !validFrom.equals("DD-MMM-YYYY") && validTo != null && !validTo.equals("") && !validTo.equals("DD-MMM-YYYY")) {
                pstmt.setString(++scc, changeDate1(validFrom));
                pstmt.setString(++scc, changeDate1(validTo));
                pstmt.setString(++scc, changeDate1(validFrom));
                pstmt.setString(++scc, changeDate1(validTo));
            }
            print(this, "getContractListing :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                int contractdetailId = rs.getInt(1);
                list.add(new FeedbackInfo(contractdetailId, "", "", "", "", 0, "", "", "", "", "", 0, 0, 0, "", 0, 0, "", ""));
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return list;
    }    
    
    public String getDateStatusVal(int flag1, int flag2, int flag3)
    {
        String str = "";
        if(flag1 == 1)
        {
            str = "New";
        }
        if(flag2 == 1)
        {
            str = "Ongoing";
        }
        if(flag3 == 1)
        {
            str = "Expired";
        }
        return str;
    }
    
    public String getUpdateStatusVal(int approval1, int flag4)
    {
        String str = "";
        if(flag4 == 1)
            str = "Expired";
        else
        {
            if(approval1 == 0)
                str = "Pending";
            else if(approval1 == 1)
                str = "Completed";
        }
        return str;
    }
    
    public int contractApprove(int candidateId, int contractIdetailId, String file2, String remarks, 
            String userName, int uId, int type) {
        int cc = 0;
        try
        {
            StringBuilder sb = new StringBuilder();
            if(type == 1)
            {
                sb.append("UPDATE t_contractdetail SET ");
                sb.append("s_file3 = ?, ");
                sb.append(" ts_file3ts = ?, ");
                sb.append(" s_username3 = ?, ");
                sb.append("i_userid = ?, ");
                sb.append("i_status = ?, ");
                sb.append("i_approval1 = ?, ");
                sb.append("s_remarks2 = ?, ");
                sb.append(" ts_regdate = ?, ");
                sb.append(" ts_moddate = ? ");
                sb.append("WHERE i_contractdetailid = ? AND i_candidateid = ?");
            }else
            {
                sb.append("UPDATE t_contractdetail SET ");
                sb.append("s_file2 = ?, ");
                sb.append(" ts_file2ts = ?, ");
                sb.append(" s_username2 = ?, ");
                sb.append("i_userid = ?, ");
                sb.append("i_status = ?, ");
                sb.append("i_approval2 = ?, ");
                sb.append("s_remarks1 = ?, ");
                sb.append(" ts_regdate = ?, ");
                sb.append(" ts_moddate = ? ");
                sb.append("WHERE i_contractdetailid = ? AND i_candidateid = ?");
            }
            String query = (sb.toString()).intern();
            sb.setLength(0);

            conn = getConnection();
            pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            int scc = 0;
            pstmt.setString(++scc, file2);
            pstmt.setString(++scc, currDate1());
            pstmt.setString(++scc, userName); 
            pstmt.setInt(++scc, uId);
            pstmt.setInt(++scc, 1);
            pstmt.setInt(++scc, 1);
            pstmt.setString(++scc, remarks);
            pstmt.setString(++scc, currDate1());
            pstmt.setString(++scc, currDate1());
            pstmt.setInt(++scc, contractIdetailId);
            pstmt.setInt(++scc, candidateId);
            print(this,"contractApprove :: " + pstmt.toString());
            pstmt.executeUpdate();
            rs = pstmt.getGeneratedKeys();
            while (rs.next())
            {
                cc = rs.getInt(1);
            }
        }
        catch (Exception exception)
        {
            print(this,"contractApprove1 :: " + exception.getMessage());
        }
        finally
        {
            close(conn, pstmt, rs);
        }
        return cc;
    }
    
    //For user detail modal
    public FeedbackInfo getDetailsForModal(int contractDetailId) 
    {
        FeedbackInfo info = null;
        StringBuilder sb = new StringBuilder();
        try
        {
            sb.append("SELECT t_contractdetail.i_contractdetailid, t_contractdetail.s_file1, t_contractdetail.s_file2, "); 
            sb.append("t_contractdetail.s_file3, DATE_FORMAT(t_contractdetail.ts_file1ts, '%d-%b-%Y %H:%i'), "); 
            sb.append("DATE_FORMAT(t_contractdetail.ts_file2ts, '%d-%b-%Y %H:%i'), ");
            sb.append("DATE_FORMAT(t_contractdetail.ts_file3ts, '%d-%b-%Y %H:%i'), t_contractdetail.s_username1, "); 
            sb.append("t_contractdetail.s_username2, t_contractdetail.s_username3 , t_contract.s_name, t_contractdetail.i_approval1, ");
            sb.append("CURRENT_DATE() > DATE_ADD(t_contractdetail.d_fromdate, INTERVAL -2 DAY), t_contractdetail.i_approval2 ");
            sb.append("FROM t_contractdetail ");
            sb.append("LEFT JOIN t_contract ON(t_contract.i_contractid = t_contractdetail.i_contractid) ");
            sb.append("WHERE t_contractdetail.i_contractdetailid = ? ");
            String query = sb.toString().intern();
            sb.setLength(0);
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, contractDetailId);
            print(this, "getDetailsForModal :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            while (rs.next())
            {
                int contractId = rs.getInt(1);
                String file1 = rs.getString(2) != null ? rs.getString(2) : "";
                String file2 = rs.getString(3) != null ? rs.getString(3) : "";
                String file3 = rs.getString(4) != null ? rs.getString(4) : "";                
                String date1 = rs.getString(5) != null ? rs.getString(5) : "";                
                String date2 = rs.getString(6) != null ? rs.getString(6) : "";      
                String date3 = rs.getString(7) != null ? rs.getString(7) : "";   
                String uername1 = rs.getString(8) != null ? rs.getString(8) : "";   
                String uername2 = rs.getString(9) != null ? rs.getString(9) : "";   
                String uername3 = rs.getString(10) != null ? rs.getString(10) : "";   
                String contractName = rs.getString(11) != null ? rs.getString(11) : "";
                int approval = rs.getInt(12);
                int flag4 = rs.getInt(13);
                int approval2 = rs.getInt(13);
                String val = getUpdateStatusVal(approval, flag4);
                
                info = new FeedbackInfo(contractId, file1, file2, file3, date1, date2, date3, uername1, uername2, uername3, contractName,approval, flag4, approval2,val );
            }

        } catch (Exception exception) {
            print(this, "getDetailsForModal :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return info;
    }
    
    //Client ddl
    public Collection getCrewClients(int candidateId) 
    {
        Collection coll = new LinkedList();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT DISTINCT t_clientasset.i_clientid, t_client.s_name ");
        sb.append("FROM t_contractdetail ");
        sb.append("LEFT JOIN t_clientasset ON( t_clientasset.i_clientassetid = t_contractdetail.i_clientassetid) ");
        sb.append("LEFT JOIN t_client ON(t_client.i_clientid = t_clientasset.i_clientid) ");
        sb.append("WHERE t_contractdetail.i_status = 1 AND t_contractdetail.i_candidateid = ? ORDER BY t_client.s_name");
        String query = (sb.toString()).intern();
        sb.setLength(0);
        coll.add(new FeedbackInfo(-1, "Select Client"));
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, candidateId);
            print(this, "getCrewClients :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            int refId;
            String refName;
            while (rs.next()) 
            {
                refId = rs.getInt(1);
                refName = rs.getString(2) != null ? rs.getString(2): "";
                coll.add(new FeedbackInfo(refId, refName));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return coll;
    }
    
    public Collection getCrewAssets(int candidateId, int clientId) 
    {
        Collection coll = new LinkedList();
        if (clientId > 0) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT DISTINCT t_contractdetail.i_clientassetid, t_clientasset.s_name  ");
        sb.append("FROM t_contractdetail ");
        sb.append("LEFT JOIN t_clientasset ON(t_clientasset.i_clientassetid = t_contractdetail.i_clientassetid) ");
        sb.append("WHERE t_contractdetail.i_status = 1 AND t_contractdetail.i_candidateid = ? AND t_clientasset.i_clientid = ? ");
        sb.append("ORDER BY t_clientasset.s_name");
        String query = (sb.toString()).intern();
        sb.setLength(0);
        coll.add(new FeedbackInfo(-1, " Select Asset"));
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, candidateId);
            pstmt.setInt(2, clientId);
            print(this, "getCrewAssets :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            int refId;
            String refName;
            while (rs.next()) 
            {
                refId = rs.getInt(1);
                refName = rs.getString(2) != null ? rs.getString(2): "";
                coll.add(new FeedbackInfo(refId, refName));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        } else {
            coll.add(new FeedbackInfo(-1, " Select Asset "));
        }
        return coll;
    }    
    
    public ArrayList getInterviewList(int candidateId) {
        ArrayList list = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT t_interview.i_interviewid, t_userlogin.s_name, t_interview.s_linkloc, t_interview.s_mode, ");
        sb.append("DATE_FORMAT(t_interview.d_datec, '%d-%b-%Y %H:%i:%s'), t_interview.i_iflag, t_interview.i_avflag, CASE WHEN NOW() > t_interview.d_datec THEN '2' ELSE '1' END ");
        sb.append("FROM t_interview ");
        sb.append("LEFT JOIN t_userlogin ON (t_userlogin.i_userid  = t_interview.i_managerid) ");
        sb.append("WHERE t_interview.i_candidateid = ? ");        
        sb.append("ORDER BY t_interview.d_datec ");
        String query = (sb.toString()).intern();
        sb.setLength(0);
        
        try
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            int scc = 0;
            pstmt.setInt(++scc, candidateId);
            logger.info("getInterviewList :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            int interviewId, iflag, avflag, type;
            String username, link, mode, date;
            while (rs.next()) 
            {
                interviewId = rs.getInt(1);
                username = rs.getString(2) != null ? rs.getString(2) : "";
                link = rs.getString(3) != null ? rs.getString(3) : "";
                mode = rs.getString(4) != null ? rs.getString(4) : "";
                date = rs.getString(5) != null ? rs.getString(5) : "";
                iflag = rs.getInt(6);
                avflag = rs.getInt(7);
                type = rs.getInt(8);
                
                list.add(new FeedbackInfo(interviewId, username, link, mode, date, iflag, avflag, type));
            }
            rs.close();
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return list;
    }
    
    public FeedbackInfo getEmailById(int interviewId) 
    {
        FeedbackInfo info = null;
        StringBuilder sb = new StringBuilder();
        try 
        {
            sb.append("SELECT t_interview.i_managerid, t_userlogin.s_name, t_userlogin.s_email ");
            sb.append("FROM t_interview ");
            sb.append("LEFT JOIN t_userlogin ON (t_userlogin.i_userid = t_interview.i_managerid) ");
            sb.append("WHERE t_interview.i_interviewid = ? ");
            String query = sb.toString().intern();
            sb.setLength(0);
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, interviewId);
            print(this, "getEmailById :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            while (rs.next()) 
            {
                int managerId = rs.getInt(1);
                String name = rs.getString(2) != null ? rs.getString(2) : "";
                String email = rs.getString(3) != null ? rs.getString(3) : "";
                
                info = new FeedbackInfo(interviewId, managerId, name, email);
            }

        } catch (Exception exception) {
            print(this, "getEmailById :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return info;
    }
    
    public int saveAvailability(int interviewId, int userId, int avflag, String username, String remark )
    {
        int cc = 0;
        try
        {
            StringBuilder sb = new StringBuilder();
            sb.append("UPDATE t_interview SET ");
            sb.append("i_avflag = ?, ");
            if(avflag == 1)
                sb.append("i_iflag = 4, ");
            else
                sb.append("i_iflag = 3, ");
            sb.append("i_userid = ?, ");
            sb.append("s_username = ?, ");
            sb.append("s_remarks = ?, ");
            sb.append("ts_moddate = ? ");
            sb.append("WHERE i_interviewid = ? ");
            String query = (sb.toString()).intern();
            sb.setLength(0);

            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            int scc = 0;
            pstmt.setInt(++scc, avflag);
            pstmt.setInt(++scc, userId);
            pstmt.setString(++scc, username);
            pstmt.setString(++scc, remark);
            pstmt.setString(++scc, currDate1());
            pstmt.setInt(++scc, interviewId);
            print(this,"saveAvailability :: " + pstmt.toString());
            cc = pstmt.executeUpdate();
            if(cc > 0)
            {
                sendmail(interviewId, username, userId);
            }
        }
        catch (Exception exception)
        {
            print(this,"saveAvailability :: " + exception.getMessage());
        }
        finally
        {
            close(conn, pstmt, rs);
        }
        return cc;
    }
    
    public int sendmail(int interviewId, String username, int userId) 
    {
        int sent = 0;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT t_interview.i_managerid, t_candidate.s_email, t2.emails, t_interview.s_remarks, ");
        sb.append("t_interview.i_avflag, t_interview.i_clientid, t_interview.i_clientassetid, t_interview.s_linkloc, ");
        sb.append("t_interview.s_mode, t_interview.i_timezoneid1, t_interview.i_timezoneid2, t_interview.d_datec , t_interview.ts_moddate, ");
        sb.append("t_interview.i_duration, t_interview.d_date, t_interview.i_shortlistid, t_interview.i_iflag, t_interview.i_positionid,  ");
        sb.append("t4.emails, t_userlogin.s_email, t_interview.i_jobpostid, DATE_FORMAT(t_interview.ts_moddate, '%D-%b-%y'), ");
        sb.append("t_position.s_name, t_grade.s_name ");
        sb.append("FROM t_interview ");
        sb.append("LEFT JOIN t_userlogin ON (t_userlogin.i_userid = t_interview.i_managerid) ");
        sb.append("LEFT JOIN t_candidate on (t_candidate.i_candidateid = t_interview.i_candidateid) "); 
        sb.append("LEFT JOIN t_client ON (t_client.i_clientid = t_interview.i_clientid) ");
        sb.append("LEFT JOIN t_position on (t_interview.i_positionid = t_position.i_positionid) ");
        sb.append("LEFT JOIN t_grade on (t_position.i_gradeid = t_grade.i_gradeid) ");
        sb.append("LEFT JOIN (SELECT t_client.i_clientid, GROUP_CONCAT(DISTINCT t1.s_email ORDER BY t1.s_email SEPARATOR ', ') AS emails FROM t_client "); 
        sb.append("LEFT JOIN t_userlogin AS t1 ON FIND_IN_SET( t1.i_userid, t_client.s_ocsuserids) GROUP BY t_client.i_clientid) AS t2 ON (t2.i_clientid = t_client.i_clientid) "); 
        sb.append("LEFT JOIN (SELECT t_client.i_clientid, GROUP_CONCAT(DISTINCT t3.s_email ORDER BY t3.s_email SEPARATOR ', ') AS emails FROM t_client ");  
        sb.append("LEFT JOIN t_userlogin AS t3 ON FIND_IN_SET( t3.i_userid, t_client.s_rids) GROUP BY t_client.i_clientid) AS t4 ON (t4.i_clientid = t_client.i_clientid) ");
        sb.append("WHERE t_interview.i_interviewid = ? ");
        String query = sb.toString();
        sb.setLength(0);
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);           
            pstmt.setInt(1, interviewId);
            String position = "",  grade= "", email = "", co_email ="", re_email ="", ma_email ="", ccval = "", linkloc = "", 
            mode = "", date ="", datec = "", remarks = "", modDate, str= "", respDate="";
            int avflag = 0, managerId = 0, clientId = 0, assetId = 0, tz1= 0, tz2= 0, duration = 0,
            shortlistId= 0, iflag = 0, positionId= 0, jobpostId= 0;
            print(this, "sendmail :: " + pstmt.toString());
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) 
            {
                managerId = rs.getInt(1);
                email = rs.getString(2) != null ? rs.getString(2) : "";
                co_email = rs.getString(3) != null ? rs.getString(3) : "";
                remarks = rs.getString(4) != null ? rs.getString(4) : "";
                avflag = rs.getInt(5);
                clientId = rs.getInt(6);    
                assetId = rs.getInt(7);    
                linkloc = rs.getString(8) != null ? rs.getString(8) : "";
                mode = rs.getString(9) != null ? rs.getString(9) : "";
                tz1 = rs.getInt(10);    
                tz2 = rs.getInt(11);    
                datec = rs.getString(12) != null ? rs.getString(12) : "";
                modDate = rs.getString(13) != null ? rs.getString(13) : "";
                duration = rs.getInt(14);    
                date = rs.getString(15) != null ? rs.getString(15) : "";
                shortlistId = rs.getInt(16);    
                iflag = rs.getInt(17);    
                positionId = rs.getInt(18);    
                re_email = rs.getString(19) != null ? rs.getString(19) : "";
                ma_email = rs.getString(20) != null ? rs.getString(20) : "";
                jobpostId = rs.getInt(21);    
                respDate = rs.getString(22) != null ? rs.getString(22) : "";
                position = rs.getString(23) != null ? rs.getString(23) : "";
                grade = rs.getString(24) != null ? rs.getString(24) : "";
                if(!grade.equals("")){
                    position += " | "+grade;
                }
                if(!email.equals(""))
                {
                    ccval = email;
                }
                if(!re_email.equals(""))
                {
                    ccval += ","+re_email;
                }
                if(!co_email.equals(""))
                {
                    ccval += ","+co_email;
                }    
                int tp =0;
                if(avflag == 1){
                    str = "Available";
                    tp = 3;
                }
                else{
                    str = "Unavailable"; 
                    tp = 4;
                }
                if(!email.equals(""))
                {
                    String message = "Dear Manager,"+"\n\nThe candidate "+username+" has responded to the interview invitation.\n\n"+
                    
                    "Response Details:\n"+
                    "Position: "+position+"\n"+
                    "Candidate's Response: "+str+"\n"+
                    "Candidate's Remark : "+remarks+"\n"+
                    "Date: "+respDate;
                            
                    String subject = "Interview Invitation Response Received";
                    String sdescription = message.replaceAll("\n", "<br/>");
                    
                    String messageBody = getpatchbody(sdescription, "interview_availability.html");
                    String file_maillog = getMainPath("file_maillog");
                    java.util.Date nowmail = new java.util.Date();
                    String fn_mail = "Interview_Response-"+String.valueOf(nowmail.getTime())+".html";
                    String filePath = createFolder(file_maillog);
                    String fname = writeHTMLFile(messageBody, file_maillog+"/"+filePath, fn_mail);
                    String receipent[] = new String[1];
                    receipent[0] = ma_email;
                    String from = "";
                    String cc[] = parseCommaDelimString(ccval); 
                    String bcc[] = new String[0];
                    try
                    {
                        StatsInfo sinfo = postMailAttach(receipent, cc, bcc, messageBody, subject, "", "", -1);
                        int flag = 0;
                        if(sinfo != null)
                        {
                            flag = sinfo.getDdlValue();
                            from = sinfo.getDdlLabel();                            
                        }
                        Documentexpiry doc = new Documentexpiry();
                        int notificationId = doc.createNotification(userId, 1, tp, userId, clientId, assetId, str, username);
                        if(notificationId > 0){
                           sent = createMailLogForInterview(conn, 20, username, ma_email, ccval, "", from, subject, userId, username, filePath+ "/" +fname, notificationId);
                        }
                        if(sent > 0)
                        {
                            StringBuilder sb2 = new StringBuilder();
                            sb2.append("INSERT INTO t_interviewh ");
                            sb2.append("(i_candidateid, i_clientassetid, i_managerid, s_linkloc, s_mode, i_timezoneid1, i_timezoneid2,  d_datec, ");//8
                            sb2.append("ts_regdate, i_duration, d_date, i_shortlistid, i_userid, i_iflag, s_username, i_positionid, i_clientid, i_avflag, s_remarks, i_jobpostid) ");//11
                            sb2.append("VALUES (?, ?, ?, ?, ?,  ?, ?, ?, ?, ?,  ?, ?, ?, ?, ?,   ?, ?, ?,?, ?)");//19
                            String query2 = (sb2.toString()).intern();
                            sb2.setLength(0);
                            int scc = 0;
                            pstmt = conn.prepareStatement(query2, PreparedStatement.RETURN_GENERATED_KEYS);
                            pstmt.setInt(++scc, (userId));
                            pstmt.setInt(++scc, (assetId));
                            pstmt.setInt(++scc, (managerId));
                            pstmt.setString(++scc, (linkloc));
                            pstmt.setString(++scc, (mode));
                            pstmt.setInt(++scc, (tz1));
                            pstmt.setInt(++scc, (tz2));
                            pstmt.setString(++scc, (datec));
                            pstmt.setString(++scc, modDate);
                            pstmt.setInt(++scc, (duration));
                            pstmt.setString(++scc, (date));
                            pstmt.setInt(++scc, (shortlistId));
                            pstmt.setInt(++scc, (userId));
                            pstmt.setInt(++scc, iflag);
                            pstmt.setString(++scc, (username));
                            pstmt.setInt(++scc, positionId);
                            pstmt.setInt(++scc, clientId);
                            pstmt.setInt(++scc, avflag);
                            pstmt.setString(++scc, (remarks));
                            pstmt.setInt(++scc, jobpostId);
                            print(this,"Insert into t_interviewh  :: " + pstmt.toString());
                            pstmt.executeUpdate();                
                        }
                    }
                    catch(Exception e)
                    {
                       print(this, "sendmail :: " + e.getMessage());
                    }
                }
            }
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return sent;
    }
    
    public String getpatchbody(String description, String htmlFile) {
        String adminpage = getMainPath("template_path") + htmlFile;
        HashMap hashmap = new HashMap();
        Template template = new Template(adminpage);
        hashmap.put("MESSAGE", description);
        return template.patch(hashmap);
    }
    
    public int createMailLogForInterview(Connection conn, int type, String name, String to, String cc, 
            String bcc, String from, String subject, int candidateId, String username, String filename, int notificationId) 
    {
        int count = 0;
        try 
        {
            StringBuilder sb = new StringBuilder();
            sb.append("INSERT INTO t_maillog ");
            sb.append("(i_type, s_name, s_to, s_cc, s_bcc, s_from, s_subject, s_filename, ts_regdate, ts_moddate, i_candidateid, s_sendby, i_notificationid) ");
            sb.append("VALUES (?, ?, ?, ?, ?,   ?, ?, ?, ?, ?,   ?, ?, ?)");
            String query = sb.toString().intern();
            sb.setLength(0);
            pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            pstmt.setInt(1, type);
            pstmt.setString(2, name);
            pstmt.setString(3, to);
            pstmt.setString(4, cc);
            pstmt.setString(5, bcc);
            pstmt.setString(6, from);
            pstmt.setString(7, subject);
            pstmt.setString(8, filename);
            pstmt.setString(9, currDate1());
            pstmt.setString(10, currDate1());
            pstmt.setInt(11, candidateId);
            pstmt.setString(12, username);
            pstmt.setInt(13, notificationId);
            print(this, "createMailLogForInterview :: " + pstmt.toString());
            pstmt.executeUpdate();
            rs = pstmt.getGeneratedKeys();
            while (rs.next()) {
                count = rs.getInt(1);
            }
        } 
        catch (Exception exception) 
        {
            print(this, "createMailLogForInterview :: " + exception.getMessage());
        } 
        finally 
        {
            close(null, pstmt, null);
        }
        return count;
    }
    
    public String changeDateTime(String date) 
    {
        String str = "0000-00-00 00:00:00";
        try 
        {
            if (date != null && !date.equals("")) 
            {
                SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MMM-yyyy HH:mm");
                SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                str = sdf2.format(sdf1.parse(date));
            }
        } catch (Exception e) {
            print(this, "Error in changeDateTime :: " + e.getMessage());
        }
        return str;
    }
    
    public ArrayList getClientOfferList(int candidateId) 
    {
        ArrayList list = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT t_shortlist.i_shortlistid,  t_shortlist.s_offerpdffilename, t_shortlist.s_omailby, ");
        sb.append("DATE_FORMAT(t_shortlist.ts_omaildate,'%d-%b-%Y %H:%i:%s'), ");
        sb.append("t_shortlist.i_userid, t_shortlist.i_oflag, t_position.s_name, t_grade.s_name ");
        sb.append("FROM t_shortlist ");
        sb.append("LEFT JOIN t_jobpost ON(t_jobpost.i_jobpostid = t_shortlist.i_jobpostid) ");
        sb.append("LEFT JOIN t_position ON(t_position.i_positionid = t_jobpost.i_positionid) ");
        sb.append("LEFT JOIN t_grade ON(t_grade.i_gradeid = t_position.i_gradeid) ");
        sb.append("WHERE i_candidateid = ? AND i_oflag = 3 ");
        
        String query = (sb.toString()).intern();
        sb.setLength(0);
        
        try
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            int scc = 0;
            pstmt.setInt(++scc, candidateId);
            logger.info("getClientOfferList :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            int shortlistId, userId, oflag ;
            String username, date, filename, position, grade;
            while (rs.next()) 
            {
                shortlistId = rs.getInt(1);
                filename = rs.getString(2) != null ? rs.getString(2) : "";
                username = rs.getString(3) != null ? rs.getString(3) : "";
                date = rs.getString(4) != null ? rs.getString(4) : "";
                userId = rs.getInt(5);
                oflag = rs.getInt(6);
                position = rs.getString(7) != null ? rs.getString(7) : "";
                grade = rs.getString(8) != null ? rs.getString(8) : "";
                if(!grade.equals(""))
                {
                    position += " | "+grade;
                }
                
                list.add(new FeedbackInfo(candidateId, shortlistId, filename, username, date, userId, oflag, position));
            }
            rs.close();
            
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("getClientOfferList :: " + pstmt.toString());
        } finally {
            close(conn, pstmt, rs);
        }
        return list;
    }
    
    
    public int saveOffer(int shortlistId, String username, String reasonRemark, int uId, int type) {
        int cc = 0;
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("UPDATE t_shortlist SET ");
            if(type == 1)
            {
                sb.append("s_oadby = ?, ");
                sb.append("ts_oaddate = ?, ");
                sb.append("s_oadremarks = ? ");
            }else{
                sb.append("s_srby = ?, ");
                sb.append("ts_srdate = ?, ");
                sb.append("s_remarks = ? ");
            }
            sb.append("WHERE i_shortlistid = ? ");
            String query = (sb.toString()).intern();
            sb.setLength(0);

            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            int scc = 0;
            pstmt.setString(++scc, username);
            pstmt.setString(++scc, currDate1());
            pstmt.setString(++scc, reasonRemark);
            pstmt.setInt(++scc, shortlistId);
            print(this, "saveOffer :: " + pstmt.toString());
            cc = pstmt.executeUpdate();
            if(cc > 0)
            {
                int tp = 0;
                if(type == 1)
                {
                    tp = 4;
                }else{
                    tp = 5;
                }
                insertclientselectionhistory(shortlistId, tp,  0, uId, 4, username);
            }

        } catch (Exception exception) {
            print(this, "saveOffer :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return cc;
    }
    
    public int updateSflag(int shortlistId, String username, int uId, int type) {
        int cc = 0;
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("UPDATE t_shortlist SET ");
            if(type == 1)
            {
                sb.append("s_oadby = ?, ");
                sb.append("ts_oaddate = ?, ");
                sb.append("i_sflag = 4 ");
                sb.append("i_oflag = 4 ");
            }else{
                sb.append("s_srby = ?, ");
                sb.append("ts_srdate = ?, ");
                sb.append("i_sflag = 5 ");
                sb.append("i_oflag = 5 ");
            }
            sb.append("WHERE i_shortlistid = ? ");
            String query = (sb.toString()).intern();
            sb.setLength(0);

            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            int scc = 0;
            pstmt.setString(++scc, username);
            pstmt.setString(++scc, currDate1());
            pstmt.setInt(++scc, shortlistId);
            print(this, "updateSflag :: " + pstmt.toString());
            cc = pstmt.executeUpdate();
            if(cc > 0)
            {
                int tp = 0;
                if(type == 1)
                {
                    tp = 4;
                }else{
                    tp = 5;
                }
                insertclientselectionhistory(shortlistId, tp,  0, uId, 4, username);
            }

        } catch (Exception exception) {
            print(this, "updateSflag :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return cc;
    }
    
    public int insertclientselectionhistory(int shortlistId, int sflag, int maillogId, 
            int userId, int oflag, String username) {
        int id = 0;
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("insert into t_clientselectionh  ");
            sb.append("( i_shortlistid, i_sflag, i_oflag, i_maillogid, i_userid, ts_regdate) ");
            sb.append("values (?, ?, ?, ?, ?, ?)");
            String query = (sb.toString()).intern();
            sb.setLength(0);
            conn = getConnection();
            pstmt = conn.prepareStatement(query, com.mysql.jdbc.Statement.RETURN_GENERATED_KEYS);
            int scc = 0;
            pstmt.setInt(++scc, shortlistId);
            pstmt.setInt(++scc, sflag);
            pstmt.setInt(++scc, oflag);
            pstmt.setInt(++scc, maillogId);
            pstmt.setInt(++scc, userId);
            pstmt.setString(++scc, currDate1());
            pstmt.executeUpdate();
            rs = pstmt.getGeneratedKeys();
            while (rs.next()) 
            {
                id = rs.getInt(1);
            }
        } catch (Exception exception) {
            print(this, "insertclientselectionhistory :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return id;
    }
    
    public FeedbackInfo getDetailsById(int shortlistId, int type) {
        FeedbackInfo info = null;
        StringBuilder sb = new StringBuilder();
        if(type == 2)
        {
            sb.append("SELECT t_shortlist.s_srby, DATE_FORMAT(t_shortlist.ts_srdate, '%d-%b-%Y %H:%i:%s'), t_shortlist.s_remarks, ");            
        }else{
            sb.append("SELECT t_shortlist.s_oadby, DATE_FORMAT(t_shortlist.ts_oaddate, '%d-%b-%Y %H:%i:%s'), t_shortlist.s_oadremarks, ");
        }
        sb.append("t_position.s_name, t_grade.s_name, t4.emails, t6.emails, t_jobpost.i_clientid, t_jobpost.i_clientassetid ");
        
        sb.append("FROM t_shortlist ");
        sb.append("LEFT JOIN t_jobpost on (t_jobpost.i_jobpostid = t_shortlist.i_jobpostid) ");
        sb.append("LEFT JOIN t_position on (t_jobpost.i_positionid = t_position.i_positionid) ");
        sb.append("LEFT JOIN t_grade on (t_jobpost.i_gradeid = t_grade.i_gradeid) ");
        
        sb.append("LEFT JOIN t_client on (t_client.i_clientid = t_jobpost.i_clientid) ");
        sb.append("LEFT JOIN (SELECT t_client.i_clientid, GROUP_CONCAT(DISTINCT t3.s_email order by t3.s_email SEPARATOR ', ') as emails from t_client ");
        sb.append("LEFT JOIN t_userlogin as t3 on find_in_set(t3.i_userid, t_client.s_ocsuserids) group by t_client.i_clientid) as t4 on (t4.i_clientid = t_client.i_clientid) ");
        sb.append("LEFT JOIN (SELECT t_client.i_clientid, GROUP_CONCAT(DISTINCT t5.s_email order by t5.s_email SEPARATOR ', ') as emails from t_client "); 
        sb.append("LEFT JOIN t_userlogin as t5 on find_in_set(t5.i_userid, t_client.s_rids) group by t_client.i_clientid) as t6 on (t6.i_clientid = t_client.i_clientid) "); 
        sb.append("WHERE t_shortlist.i_shortlistid = ? ");

        String query = sb.toString();
        sb.setLength(0);
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);           
            pstmt.setInt(1, shortlistId);
            
            String position = "",  grade= "", co_email ="", re_email ="",  user = "", 
            date ="", remarks = "";
            print(this, "getDetailsById :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            while (rs.next()) 
            {
                user = rs.getString(1) != null ? rs.getString(1) : "";
                date = rs.getString(2) != null ? rs.getString(2) : "";
                remarks = rs.getString(3) != null ? rs.getString(3) : "";
                position = rs.getString(4) != null ? rs.getString(4) : "";
                grade = rs.getString(5) != null ? rs.getString(5) : "";
                co_email = rs.getString(6) != null ? rs.getString(6) : "";
                re_email = rs.getString(7) != null ? rs.getString(7) : "";  
                int clientId = rs.getInt(8);
                int assetId = rs.getInt(9);
                if(!grade.equals("")){
                    position += " - "+grade;
                }
                
                info = new FeedbackInfo(shortlistId, user, date, remarks, position, co_email, re_email, clientId, assetId);
            }
            rs.close();
        } catch (Exception exception) {
            print(this, "getDetailsById :: " + exception.getMessage());
        } finally {
            close(conn, pstmt, rs);
        }
        return info;
    }
    
    
    public int sendmailOfferReplay(String fromval, String toval, String ccval, String bccval, String subject, String description,
           String attachedfile, int shortlistId, String username, int clientId, int assetId, int type, int userId) {
        int sent = 0, id =0;
        String toaddress[] = parseCommaDelimString(toval);
        String ccaddress[] = parseCommaDelimString(ccval);
        String bccaddress[] = parseCommaDelimString(bccval);
        String sdescription = description.replaceAll("\n", "<br/>");

        String messageBody = getpatchbody(sdescription, "interview_availability.html");
        String file_maillog = getMainPath("file_maillog");
        java.util.Date nowmail = new java.util.Date();
        String fn_mail = "Offer_Letter-" + String.valueOf(nowmail.getTime()) + ".html";
        String filePath = createFolder(file_maillog);
        String fname = writeHTMLFile(description, file_maillog + "/" + filePath, fn_mail);
        String attachfile = getMainPath("add_resumetemplate_pdf");
        String attachfile_fn = "", attachfile_ext = "";
        if (!attachedfile.equals("")) {
            attachfile_fn = attachfile + attachedfile;
            if (attachedfile.indexOf(".") != -1) {
                attachfile_ext = "Offer" + attachedfile.substring(attachedfile.lastIndexOf("."));
            }
        }
        String from = "";
        try 
        {
            StatsInfo sinfo = postMailAttach(toaddress, ccaddress, bccaddress, messageBody, subject, attachfile_fn, attachfile_ext, -1);
            int flag = 0;
            if (sinfo != null) {
                flag = sinfo.getDdlValue();
                from = sinfo.getDdlLabel();
            }
            Documentexpiry doc = new Documentexpiry();
            int tp = 0;
            String str = "";
            if(type == 1){
                tp = 10;
                str = "Offer Accepted";
            }
            else{
                tp = 11;
                str = "Offer Rejected";
            }
            int notificationId = doc.createNotification(userId, 1, tp, userId, clientId, assetId, str, username);
            if(notificationId > 0)
            {
                conn = getConnection();
                sent = createMailLogForInterview(conn, 20, username, toval, ccval, "", from, subject, userId, username, filePath+ "/" +fname, notificationId);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("sendmailOfferReplay :: "+e.getMessage());
        }
        if(sent > 0)
        {
            try
            {
                int flag =0;
                if(type == 1)
                {
                    flag = 4;
                }else{                    
                    flag = 5;
                }
                StringBuilder sb = new StringBuilder();
                sb.append("UPDATE t_shortlist SET ");
                if(attachedfile != null && !attachedfile.equals(""))
                {
                    sb.append("s_offerpdffilename2 = ?, ");                        
                }                
                if(type == 1)
                {
                    sb.append("i_oflag = ?, ");
                    sb.append("s_oadby = ?, ");                    
                    sb.append("ts_oaddate = ? ");
                }else{
                    sb.append("i_sflag = ?, ");
                    sb.append("s_srby = ?, ");
                    sb.append("ts_srdate = ? ");
                }
                sb.append("WHERE i_shortlistid = ? ");
                String query = (sb.toString()).intern();
                sb.setLength(0);

                conn = getConnection();
                pstmt = conn.prepareStatement(query);
                int scc = 0;
                if(attachedfile != null && !attachedfile.equals(""))
                {
                    pstmt.setString(++scc, attachedfile);
                }
                pstmt.setInt(++scc, flag);
                pstmt.setString(++scc, username);
                pstmt.setString(++scc, currDate1());
                pstmt.setInt(++scc, shortlistId);
                print(this, "UPDATE t_shortlist :: " + pstmt.toString());
                sent = pstmt.executeUpdate();
                
                try
                {
                    if(sent > 0)
                    {
                        query = "INSERT INTO t_clientselectionh (i_shortlistid, ";
                        if(type == 1)        
                            query += "i_oflag, ";
                        else
                            query += "i_sflag, ";
                        if(attachedfile != null && !attachedfile.equals(""))
                        {
                            query += " s_offerpdffilename2, ";
                        }
                        query += " ts_regdate ) ";
                        if(attachedfile != null && !attachedfile.equals(""))
                        {
                            query += "VALUES (?,?,?, ? )";
                        }else{
                            query += "VALUES (?,?,?)";
                        }
                        pstmt = conn.prepareStatement(query, com.mysql.jdbc.Statement.RETURN_GENERATED_KEYS);
                        scc =0;
                        pstmt.setInt(++scc, shortlistId);
                        pstmt.setInt(++scc, flag);
                        if(attachedfile != null && !attachedfile.equals(""))
                        {
                            pstmt.setString(++scc, attachedfile);                        
                        }
                        pstmt.setString(++scc, currDate1());
                        print(this, "INSERT s_offerpdffilename2 :: " + pstmt.toString());
                        pstmt.executeUpdate();
                        rs = pstmt.getGeneratedKeys();
                        while (rs.next()) 
                        {
                            id = rs.getInt(1);
                        }
                    }
                 } catch (Exception e) {
                    e.printStackTrace();
                    print(this, "INSERT s_offerpdffilename2 :: " + pstmt.toString());
                }                 
            } catch (Exception e) {
                e.printStackTrace();
                print(this, "UPDATE t_shortlist :: " + pstmt.toString());
            }        
        }
        return id;
    }
    
    public int getTypeById(int interviewId) {
        int id = 0;
        String query = "SELECT i_iflag FROM t_interview WHERE i_interviewid = ? ";
        try 
        {
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, interviewId);
            logger.info("getTypeById :: " + pstmt.toString());
            rs = pstmt.executeQuery();
            while (rs.next()) 
            {
                id = rs.getInt(1);
            }
            rs.close();
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return id;
    }
}
