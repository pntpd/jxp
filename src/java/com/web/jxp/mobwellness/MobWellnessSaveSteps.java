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

public class MobWellnessSaveSteps extends HttpServlet {

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
        String dailySteps = jsonRequest.optString("dailySteps", "0");
        String date = jsonRequest.optString("date", "");
        int userId = Integer.parseInt(ids);
        int dailyStep = Integer.parseInt(dailySteps);
        JSONObject jo = new JSONObject();

        if (userId > 0 && dailyStep > 0 && date != null && !date.isEmpty()) {
            int cc = crewlogin.doSaveWellnessStepsDetails(userId, dailyStep, date);
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
