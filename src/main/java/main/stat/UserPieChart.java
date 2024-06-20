package main.stat;

import org.knowm.xchart.*;
import org.knowm.xchart.style.Styler.ChartTheme;

import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONObject;

import java.nio.file.Files;
import java.nio.file.Paths;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;

public class UserPieChart {
    public static void getUserPieChart() {
        try {
            String jsonString = new String(Files.readAllBytes(Paths.get("user.json")));
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONArray recordsArray = jsonObject.getJSONArray("records");

            Map<String, Integer> postsPerYear = new HashMap<>();
            DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;

            for (int i = 0; i < recordsArray.length(); i++) {
                JSONObject record = recordsArray.getJSONObject(i);
                String createdTime = record.getJSONObject("fields").getString("created time");
                LocalDateTime crTime = LocalDateTime.parse(createdTime, formatter);

                String year = String.valueOf(crTime.getYear());
                postsPerYear.put(year, postsPerYear.getOrDefault(year, 0) + 1);
            }

            PieChart userPieChart = new PieChartBuilder()
                    .width(800)
                    .height(600)
                    .title("Distribution of Feed Posts by Year")
                    .theme(ChartTheme.XChart)
                    .build();
            
            List<String> years = new ArrayList<>(postsPerYear.keySet());
            List<Integer> postCounts = new ArrayList<>(postsPerYear.values());
            for (int i = 0; i < years.size(); i++) {
                userPieChart.addSeries(years.get(i), postCounts.get(i));
            }

            BitmapEncoder.saveBitmap(userPieChart, "userPieChart.png", BitmapEncoder.BitmapFormat.PNG);

        } catch (IOException e) {
            System.err.println("Cannot create the pie chart");
        }
    }
}
