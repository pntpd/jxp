package com.web.jxp.mobwellness;

import com.web.jxp.crewlogin.Crewlogin;
import com.web.jxp.crewlogin.CrewloginInfo;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONArray;
import org.json.JSONObject;

public class MobWellnessAction extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Crewlogin crewlogin = new Crewlogin();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream()));
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            stringBuilder.append(line);
        }

        String requestBody = stringBuilder.toString();
        JSONObject jsonRequest = new JSONObject(requestBody);
        String id = jsonRequest.optString("id", "0");
        String action = jsonRequest.optString("action", "");
        int userId = Integer.parseInt(id);
        JSONObject jo = new JSONObject();

        if (action != null && !action.equals("")) {
            if (action.equals("dailysteps") || action.equals("sleephours") || action.equals("activehours") || action.equals("standhours")) {
                String from = jsonRequest.optString("from", "");
                String to = jsonRequest.optString("to", "");
                if (userId > 0) {
                    ArrayList list = crewlogin.getWellnessDetails(userId, from, to);
                    JSONArray ja = new JSONArray();
                    if (list.size() > 0) {
                        jo.put("status", "success");
                        jo.put("message", "Data found successfully.");
                        CrewloginInfo info = null;
                        for (int i = 0; i < list.size(); i++) {
                            info = (CrewloginInfo) list.get(i);
                            if (info != null) {
                                JSONObject jo1 = new JSONObject();
                                jo1.put("wellnessId", info.getMobwellnessid());
                                jo1.put("date", info.getDate());
                                jo1.put("dailySteps", info.getDailysteps());
                                jo1.put("sleepTime", info.getSleeptime());
                                jo1.put("wakeupTime", info.getWakeuptime());
                                jo1.put("sleepHours", info.getSleephours());
                                jo1.put("activityHours", info.getActivityhours());
                                jo1.put("standHours", info.getStandhours());
                                ja.put(jo1);
                            }
                        }
                    } else {
                        jo.put("status", "error");
                        jo.put("message", "As per entered details no record found.");
                    }
                    jo.put("details", ja);
                } else {
                    jo.put("status", "error");
                    jo.put("message", "An unexpected error occurred. Please try again later.");
                }
            } else if (action.equals("bmi")) {
                double height = 0, weight = 0;
                if (userId > 0) {
                    CrewloginInfo info = crewlogin.getHeightAndWeight(userId);
                    if (info != null) {
                        height = info.getHeight();
                        weight = info.getWeight();
                        jo.put("status", "success");
                        jo.put("message", "Data found successfully.");
                    } else {
                        jo.put("status", "error");
                        jo.put("message", "Data not found.");
                    }
                } else {
                    jo.put("status", "error");
                    jo.put("message", "Something went wrong.");
                }
                jo.put("height", height);
                jo.put("weight", weight);
            } else {
                jo.put("status", "error");
                jo.put("message", "Something went wrong.");
            }
        } else {
            jo.put("status", "error");
            jo.put("message", "Something went wrong.");
        }
        PrintWriter out = response.getWriter();
        out.print(jo.toString());
        out.flush();
    }
}
