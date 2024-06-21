package main;

import main.airtable.*;
import main.stat.ExportJsonToExcelFile;
import main.stat.UserBarChart;
import main.stat.UserPieChart;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

public class Main {
    public static final String TEXT_RED = "\u001B[31m";
    public static final String TEXT_GREEN = "\u001B[32m";
    public static final String TEXT_RESET = "\u001B[0m";
    public static final String TEXT_CYAN = "\u001B[36m";
    public static final String TEXT_BLUE = "\u001B[34m";

    public static final String TEXT_YELLOW = "\u001B[33m";
    public static final String TEXT_BOLD = "\u001b[1m";
    public static final String TEXT_ITALIC = "\u001b[3m";
    public static final String TEXT_UNDERLINE = "\u001b[4m";

    public static String general = "----------------------------------------------\n" +
            TEXT_BOLD +TEXT_GREEN+ "                APPLICATION\n\n" + TEXT_RESET +

            "----------------" + TEXT_BOLD +TEXT_GREEN + "Facebook API" + TEXT_RESET +"------------------\n" +
            TEXT_ITALIC +TEXT_BOLD +TEXT_BLUE + "1. " + TEXT_RESET + "Get user's feeds.\n" +
            TEXT_ITALIC +TEXT_BOLD +TEXT_BLUE + "2. " + TEXT_RESET + "Get user's liked data.\n" +
            TEXT_ITALIC +TEXT_BOLD +TEXT_BLUE + "3. " + TEXT_RESET + "Get user's liked pages.\n" +

            "------------------" + TEXT_BOLD +TEXT_GREEN + "Airtable" + TEXT_RESET +"--------------------\n" +
            TEXT_ITALIC +TEXT_BOLD +TEXT_BLUE + "4. " + TEXT_RESET + "Get all data from airtable to json file.\n" +
            TEXT_ITALIC +TEXT_BOLD +TEXT_BLUE + "5. " + TEXT_RESET + "Get only readable link airtable base.\n" +

            "----------------" + TEXT_BOLD +TEXT_GREEN + "Facebook API" + TEXT_RESET +"------------------\n" +
            TEXT_ITALIC +TEXT_BOLD +TEXT_BLUE + "6. " + TEXT_RESET + "Create Excel file for data from Airtable.\n" +
            TEXT_ITALIC +TEXT_BOLD +TEXT_BLUE + "7. " + TEXT_RESET + "Create bar chart UserBarChart.\n" +
            TEXT_ITALIC +TEXT_BOLD +TEXT_BLUE + "8. " + TEXT_RESET + "Create pie chart UserPieChart.\n" +
            "----------------------------------------------\n" +
            "9. Help \n" +
            TEXT_RED + "10. Exit\n\n" +

            TEXT_YELLOW+TEXT_BOLD +"Please choose an option: " + TEXT_RESET;

    public static void main(String[] args) throws IOException {

        final String API_INFO = new String(Files.readAllBytes(Paths.get("src/main/java/main/airtable/token.json")));
        JSONObject jsonObject = new JSONObject(API_INFO);

        final String TOKEN_AIRTABLE = jsonObject.getString("APIToken");
        final String BASE_ID = jsonObject.getString("baseID");
        final String USER_TABLE = jsonObject.getString("userTable");
        final String ACCOUNT_TABLE = jsonObject.getString("accountTable");
        final String LIKE_TABLE = jsonObject.getString("likeTable");

        /*
        String responseUser = GetRequest.getFromAirtable(BASE_ID, USER_TABLE, TOKEN_AIRTABLE);
        GetRequest.toJsonFile(responseUser, "user.json");

        String responseManagePage = GetRequest.getFromAirtable(BASE_ID, ACCOUNT_TABLE, TOKEN_AIRTABLE);
        GetRequest.toJsonFile(responseManagePage, "manage.json");

        String responseLikePage = GetRequest.getFromAirtable(BASE_ID, LIKE_TABLE, TOKEN_AIRTABLE);
        GetRequest.toJsonFile(responseLikePage, "like.json");
        */

        Scanner s = new Scanner(System.in);
        while (true) {
            System.out.println(general);
            int option = s.nextInt();

            while (option > 10) {
                System.out.println(TEXT_RED + TEXT_UNDERLINE + "Invalid option!" + TEXT_RESET);
                System.out.print(TEXT_YELLOW+TEXT_BOLD +"Please choose an option: " + TEXT_RESET);
                option = s.nextInt();
            }
            if (option == 1) {
                System.out.println("--- Getting user's feeds ---");
                RecordUserFeed feed = new RecordUserFeed();
                feed.reformatData();
            } else if (option == 2) {
                System.out.println("--- Getting user's like pages ---");
                RecordLikedData likeData = new RecordLikedData();
                likeData.reformatData();
            } else if (option == 3) {
                System.out.println("--- Getting user's managed pages ---");
                RecordManagedPage managedData = new RecordManagedPage();
                managedData.reformatData();
            } else if (option == 4) {
                try {
                    System.out.println("--- Getting all data from airtable");
                    String responseUser = GetRequest.getFromAirtable(BASE_ID, USER_TABLE, TOKEN_AIRTABLE);
                    GetRequest.toJsonFile(responseUser, "user.json");
                    System.out.println("User's feed saved to user.json!");
                } catch (Exception e) {
                    System.out.println(TEXT_RED+ "The API Token of Airtable is broken. Contact admin to update the tool"+ TEXT_RESET);
                }

                try {
                    String responseManagePage = GetRequest.getFromAirtable(BASE_ID, ACCOUNT_TABLE, TOKEN_AIRTABLE);
                    GetRequest.toJsonFile(responseManagePage, "manage.json");
                    System.out.println("User's managed page saved to manage.json!");
                } catch (Exception e) {
                    System.out.println(TEXT_RED+ "The API Token of Airtable is broken. Contact admin to update the tool"+ TEXT_RESET);
                }

                try {
                    String responseLikePage = GetRequest.getFromAirtable(BASE_ID, LIKE_TABLE, TOKEN_AIRTABLE);
                    GetRequest.toJsonFile(responseLikePage, "like.json");
                    System.out.println("User's like page saved to like.json!");
                } catch (Exception e) {
                    System.out.println(TEXT_RED+ "The API Token of Airtable is broken. Contact admin to update the tool"+ TEXT_RESET);
                }

            } else if (option == 5) {
                System.out.println("Get readable link airtable base.");
                System.out.println("Link to airtable: https://airtable.com/appGwRmPbiRacFnAq/shrFXFC69Vyom5rzI");
            } else if (option == 6) {
                System.out.println("--Create Excel file for data from Airtable--");
                ExportJsonToExcelFile.toExcel();
                System.out.println(TEXT_CYAN + "Check file Airtable_Base_Data.xlsx in the folder 'result'" + TEXT_RESET);
            } else if (option == 7) {
                System.out.println("--Create bar chart UserBarChart--");
                UserBarChart.getUserBarChart();
                System.out.println(TEXT_CYAN + "Check file userBarChart.png in the folder 'result'" + TEXT_RESET);
            } else if (option == 8) {
                System.out.println("--Create pie chart UserPieChart--");
                UserPieChart.getUserPieChart();
                System.out.println(TEXT_CYAN + "Check file userPieChart.png in the folder 'result'" + TEXT_RESET);
            } else if (option == 9) {
                System.out.println(TEXT_YELLOW +"ENTER AN INTEGER FROM 1-8 TO USE ONE OF THESE FUNCTION:"+ TEXT_RESET);
                System.out.println(general);
            } else if (option == 10) {
                System.out.println(TEXT_YELLOW + "GOOD BYE, THANKS A LOT!" + TEXT_RESET);
                System.exit(0);
                break;
            }

            String choosing = s.nextLine();
            System.out.println("Do you want to continue using application? \n"
                    + TEXT_GREEN+ "\t Y/y/YES to continue\n" +TEXT_RESET
                    + TEXT_RED + "\t Any other key to exit" +TEXT_RESET);

            if (choosing == "Y" || choosing == "y" || choosing == "Yes" || choosing == "yes")
            {
                continue;
            }
            else
            {
                System.out.println(TEXT_YELLOW + "GOOD BYE! SEE YOU AGAIN" + TEXT_RESET);
                s.close();
                System.exit(0);
                break;
            }
        }
    }
}