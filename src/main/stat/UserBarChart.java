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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserBarChart {

    public static void getUserBarChart() {
        try {
            String jsonString = new String(Files.readAllBytes(Paths.get("src/main/ExportJSON/user.json")));

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

            CategoryChart barChart = new CategoryChartBuilder()
                    .width(800)
                    .height(600)
                    .title("Number of Posts Per Year")
                    .xAxisTitle("Year")
                    .yAxisTitle("Number of Posts")
                    .theme(ChartTheme.XChart)
                    .build();

            List<String> years = new ArrayList<>(yearCountMap.keySet());
            List<Integer> counts = new ArrayList<>(yearCountMap.values());

            barChart.addSeries("Posts Per Year", years, counts);

            BitmapEncoder.saveBitmap(barChart, "src/main/result/userBarChart.png", BitmapEncoder.BitmapFormat.PNG);

        } catch (IOException e) {
            System.out.print("Cannot create UserBarChart: " + e.getMessage());
        }
    }
}
