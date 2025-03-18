package com.web.jxp.mobwellness;

import com.web.jxp.crewlogin.Crewlogin;
import com.web.jxp.crewlogin.CrewloginInfo;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONObject;

public class MobWellnessScheduleAction extends HttpServlet {

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
        int userId = Integer.parseInt(id);
        JSONObject jo = new JSONObject();
        CrewloginInfo info = null;
        if (userId > 0) {
            info = crewlogin.getWellnessDetailsById(userId);
            if (info != null) {
                jo.put("status", "success");
                jo.put("message", "Data found successfully.");
                jo.put("sleepTime", info.getSleeptime());
                jo.put("wakeupTime", info.getWakeuptime());
                jo.put("sleepHours", info.getSleephours());
                jo.put("activityHours", info.getActivityhours());
                jo.put("standHours", info.getStandhours());
            } else {
                jo.put("status", "error");
                jo.put("message", "Data not found.");
                jo.put("sleepTime", "");
                jo.put("wakeupTime", "");
                jo.put("sleepHours", "");
                jo.put("activityHours", "");
                jo.put("standHours", "");
            }
        } else {
            jo.put("status", "error");
            jo.put("message", "An unexpected error occurred. Please try again later.");
        }
        PrintWriter out = response.getWriter();
        out.print(jo.toString());
        out.flush();
    }
}
