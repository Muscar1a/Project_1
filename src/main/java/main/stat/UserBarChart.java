package main.stat;

import org.knowm.xchart.*;
import org.knowm.xchart.style.Styler.ChartTheme;
import org.knowm.xchart.style.Styler.LegendPosition;

import org.json.JSONArray;
import org.json.JSONObject;

import java.nio.file.Files;
import java.nio.file.Paths;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserBarChart {
    public static void getUserBarChart() {
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

            CategoryChart userBarChart = new CategoryChartBuilder()
                    .width(690)
                    .height(860)
                    .title("Number of Posts per Year")
                    .xAxisTitle("Year")
                    .yAxisTitle("Number of Posts")
                    .theme(ChartTheme.XChart)
                    .build();

            List<String> years = new ArrayList<>(postsPerYear.keySet());
            List<Integer> userCounts = new ArrayList<>(postsPerYear.values());
            userBarChart.getStyler().setLegendPosition(LegendPosition.InsideNW);
            userBarChart.addSeries("Posts", years, userCounts);

            BitmapEncoder.saveBitmap(userBarChart, "userBarChart.png", BitmapEncoder.BitmapFormat.PNG);
        } catch (Exception e) {
            System.out.print("Cannot create the bar chart");
        }
    }
}