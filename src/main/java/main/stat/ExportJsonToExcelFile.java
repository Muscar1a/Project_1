package main.stat;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ExportJsonToExcelFile {

    public static void toExcel() {
        try {
            String jsonString = new String(Files.readAllBytes(Paths.get("user.json")));

            Workbook workbook = new XSSFWorkbook();

            Sheet sheet = workbook.createSheet("User Data");

            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("Full Name");
            headerRow.createCell(1).setCellValue("User ID");
            headerRow.createCell(2).setCellValue("Message");
            headerRow.createCell(3).setCellValue("Created Time");

            JSONObject jsonObject = new JSONObject(jsonString);
            JSONArray recordsArray = jsonObject.getJSONArray("records");

            for (int i = 0; i < recordsArray.length(); i++) {
                JSONObject record = recordsArray.getJSONObject(i);
                JSONObject fields = record.getJSONObject("fields");
                String userId = fields.getString("userId");
                String fullName = fields.getString("fullname");
                String message = fields.getString("message");
                String createdTime = fields.getString("created time");
                Row row = sheet.createRow(i + 1);
                row.createCell(0).setCellValue(fullName);
                row.createCell(1).setCellValue(userId);
                row.createCell(2).setCellValue(message);
                row.createCell(3).setCellValue(createdTime);
            }

            for (int i = 0; i < headerRow.getLastCellNum(); i++) {
                sheet.autoSizeColumn(i);
            }


            String jsonString2 = new String(Files.readAllBytes(Paths.get("manage.json")));
            Sheet sheet2 = workbook.createSheet("Managed Page");

            Row headerRow2 = sheet2.createRow(0);
            headerRow2.createCell(0).setCellValue("Name");
            headerRow2.createCell(1).setCellValue("Page ID");
            headerRow2.createCell(2).setCellValue("Category");


            JSONObject jsonObject2 = new JSONObject(jsonString2);
            JSONArray recordsArray2 = jsonObject2.getJSONArray("records");

            for (int i = 0; i < recordsArray2.length(); i++) {
                JSONObject record = recordsArray2.getJSONObject(i);
                JSONObject fields = record.getJSONObject("fields");
                String id = fields.getString("id");
                String name = fields.getString("name");
                String category = fields.getString("category");

                Row row2 = sheet2.createRow(i + 1);
                row2.createCell(0).setCellValue(name);
                row2.createCell(1).setCellValue(id);
                row2.createCell(2).setCellValue(category);
            }

            for (int i = 0; i < headerRow2.getLastCellNum(); i++) {
                sheet.autoSizeColumn(i);
            }

            String jsonString3 = new String(Files.readAllBytes(Paths.get("like.json")));
            Sheet sheet3 = workbook.createSheet("Liked Page");

            Row headerRow3 = sheet3.createRow(0);
            headerRow3.createCell(0).setCellValue("Name");
            headerRow3.createCell(1).setCellValue("Page ID");
            headerRow3.createCell(2).setCellValue("User's Name");
            headerRow3.createCell(3).setCellValue("User's ID");

            JSONObject jsonObject3 = new JSONObject(jsonString3);
            JSONArray recordsArray3 = jsonObject3.getJSONArray("records");

            for (int i = 0; i < recordsArray3.length(); i++) {
                JSONObject record = recordsArray3.getJSONObject(i);
                JSONObject fields = record.getJSONObject("fields");
                String pageId = fields.getString("liked page id");
                String userName = fields.getString("fullname");
                String pageName = fields.getString("liked page");
                String userId = fields.getString("userId");

                Row row3 = sheet3.createRow(i + 1);
                row3.createCell(0).setCellValue(pageName);
                row3.createCell(1).setCellValue(pageId);
                row3.createCell(2).setCellValue(userName);
                row3.createCell(3).setCellValue(userId);

            }

            for (int i = 0; i < headerRow3.getLastCellNum(); i++) {
                sheet.autoSizeColumn(i);
            }


            FileOutputStream outputStream = new FileOutputStream(new File("src/main/java/main/result/Airtable_Base_Data.xlsx"));

            workbook.write(outputStream);
            workbook.close();
        }
        catch(IOException e)
        {
            System.out.print("Cannot write to Excel");
        }
    }
}