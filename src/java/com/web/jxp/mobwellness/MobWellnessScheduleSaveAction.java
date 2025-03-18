package com.web.jxp.mobwellness;

import com.web.jxp.crewlogin.Crewlogin;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONObject;

public class MobWellnessScheduleSaveAction extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Crewlogin crewlogin = new Crewlogin();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream()));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }

        String requestBody = sb.toString();
        JSONObject jsonRequest = new JSONObject(requestBody);
        String ids = jsonRequest.optString("id", "0");
        String wakeupTime = jsonRequest.optString("wakeupTime", "");
        String sleepTime = jsonRequest.optString("sleepTime", "");
        String standHours = jsonRequest.optString("standHours", "0");
        String activityHours = jsonRequest.optString("activityHours", "0");
        String sleepHours = jsonRequest.optString("sleepHours", "0");
        int userId = Integer.parseInt(ids);
        double standHour = Double.parseDouble(standHours);
        double activityHour = Double.parseDouble(activityHours);
        double sleepHour = Double.parseDouble(sleepHours);
        JSONObject jo = new JSONObject();

        if (userId > 0 && ((sleepTime != null && !sleepTime.isEmpty()) || (wakeupTime != null && !wakeupTime.isEmpty()) || 
                sleepHour > 0 || activityHour > 0 || standHour > 0)) {
            int cc = crewlogin.doSaveScheduleDetails(userId, sleepTime, wakeupTime, sleepHour, activityHour, standHour);
            if (cc > 0) {
                jo.put("status", "success");
                jo.put("message", "Data save successfully.");
            } else {
                jo.put("status", "error");
                jo.put("message", "Data not save.");
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
