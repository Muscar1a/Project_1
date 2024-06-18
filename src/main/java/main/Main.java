package main;

import main.airtable.*;
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
            TEXT_BOLD +TEXT_GREEN+ "               APPLICATION\n\n" + TEXT_RESET +

            TEXT_ITALIC +TEXT_BOLD +TEXT_BLUE + "1. " + TEXT_RESET +"Facebook API\n" +
            TEXT_ITALIC +TEXT_BOLD +TEXT_BLUE + "2. " + TEXT_RESET + "Airtable\n" +
            TEXT_ITALIC +TEXT_BOLD +TEXT_BLUE + "3. " + TEXT_RESET + "Statistic data to Excel and Charts\n" +
            "----------------------------------------------\n" +
            "4. Help \n" +
            TEXT_RED + "5. Exit\n\n" +

            TEXT_YELLOW+TEXT_BOLD +"Please choose an option: " + TEXT_RESET;

    public static String fbAPI = "----------------------------------------------\n" +
            TEXT_BOLD +TEXT_GREEN+ "               Facebook API\n\n" + TEXT_RESET +

            TEXT_ITALIC +TEXT_BOLD +TEXT_BLUE + "1. " + TEXT_RESET + "Get user's feeds\n" +
            TEXT_ITALIC +TEXT_BOLD +TEXT_BLUE + "2. " + TEXT_RESET + "Get user's liked data\n" +
            TEXT_ITALIC +TEXT_BOLD +TEXT_BLUE + "3. " + TEXT_RESET + "Get user's liked pages\n" +
            "----------------------------------------------\n" +
            TEXT_RED + "4. Back\n\n" +

            TEXT_YELLOW+TEXT_BOLD +"Please choose an option: " + TEXT_RESET;

    public static void fbProcess() throws IOException {
        while (true) {
            System.out.println(fbAPI);
            Scanner fbs = new Scanner(System.in);
            int option = fbs.nextInt();

            while (option > 4) {
                System.out.println(TEXT_RED + TEXT_UNDERLINE + "Invalid option!" + TEXT_RESET);
                System.out.print(TEXT_YELLOW+TEXT_BOLD +"Please choose an option: " + TEXT_RESET);
                option = fbs.nextInt();
            }

            if (option == 1) {
                System.out.println("--- Getting user's feeds ---");
                RecordUserFeed feed = new RecordUserFeed();
                feed.reformatData();
            } else if (option == 2) {
                System.out.println("--- Getting user's liked data ---");
                RecordLikedData likeData = new RecordLikedData();
                likeData.reformatData();
            } else if (option == 3) {
                System.out.println("--- Getting user's liked pages ---");
                RecordManagedPage managedData = new RecordManagedPage();
                managedData.reformatData();
            } else {
                fbs.close();
                break;
            }
        }
    }

    public static void main(String[] args) throws IOException {

        final String API_INFO = new String(Files.readAllBytes(Paths.get("src/main/java/main/airtable/token.json")));
        JSONObject jsonObject = new JSONObject(API_INFO);

        final String TOKEN_AIRTABLE = jsonObject.getString("APIToken");
        final String BASE_ID = jsonObject.getString("baseID");
        final String TABLE_ID = jsonObject.getString("userTable");

//        String responseUser = GetRequest.getFromAirtable(BASE_ID, TABLE_ID, TOKEN_AIRTABLE);
//        GetRequest.toJsonFile(responseUser, "user.json");

        RecordLikedData like = new RecordLikedData();
        like.reformatData();

        /*
        while (true) {
            System.out.println(general);
            Scanner s = new Scanner(System.in);
            int option = s.nextInt();

            while (option > 5) {
                System.out.println(TEXT_RED + TEXT_UNDERLINE + "Invalid option!" + TEXT_RESET);
                System.out.print(TEXT_YELLOW+TEXT_BOLD +"Please choose an option: " + TEXT_RESET);
                option = s.nextInt();
            }

            if (option == 1) {
                fbProcess();
            }
        }

         */
    }
}