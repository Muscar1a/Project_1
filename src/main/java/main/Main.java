package main;

import main.airtable.RecordUser;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        /*
        final String API_INFO = new String(Files.readAllBytes(Paths.get("src/main/java/main/airtable/token.json")));
        JSONObject jsonObject = new JSONObject(API_INFO);

        final String TOKEN_AIRTABLE = jsonObject.getString("APIToken");
        final String BASE_ID = jsonObject.getString("BaseID");
        final String TABLE_ID = jsonObject.getString("userTable");
        */
//        Scanner scanner = new Scanner(System.in);
//        String option = scanner.nextLine();

        RecordUser user = new RecordUser();
        user.reformatData();

    }
}