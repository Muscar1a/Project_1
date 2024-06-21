package main.stat;

import org.knowm.xchart.*;
import org.knowm.xchart.style.Styler.ChartTheme;

import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONObject;

import java.nio.file.Files;
import java.nio.file.Paths;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import java.util.HashMap;
import java.util.Map;

public class UserPieChart {

    public static void getUserPieChart() {
        try {
            String jsonString = new String(Files.readAllBytes(Paths.get("/Users/macbook/Documents/GitHub/Project_1.Instagram/src/main/java/main/user.json")));

            JSONObject jsonObject = new JSONObject(jsonString);
            JSONArray recordsArray = jsonObject.getJSONArray("records");

            Map<String, Integer> yearCountMap = new HashMap<>();

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssZ");

            for (int i = 0; i < recordsArray.length(); i++) {
                JSONObject record = recordsArray.getJSONObject(i);
                JSONObject fields = record.getJSONObject("fields");
                String createdTime = fields.getString("created time");
                ZonedDateTime dateTime = ZonedDateTime.parse(createdTime, formatter);
                String year = String.valueOf(dateTime.getYear());

                yearCountMap.put(year, yearCountMap.getOrDefault(year, 0) + 1);
            }

            PieChart pieChart = new PieChartBuilder()
                    .width(800)
                    .height(600)
                    .title("Distribution of Posts Per Year")
                    .theme(ChartTheme.XChart)
                    .build();

            for (Map.Entry<String, Integer> entry : yearCountMap.entrySet()) {
                pieChart.addSeries(entry.getKey(), entry.getValue());
            }

            BitmapEncoder.saveBitmap(pieChart, "/Users/macbook/Documents/GitHub/Project_1.Instagram/src/main/java/main/result/userPieChart.png", BitmapEncoder.BitmapFormat.PNG);

        } catch (IOException e) {
            System.out.print("Cannot create UserPieChart: " + e.getMessage());
        }
    }
}
