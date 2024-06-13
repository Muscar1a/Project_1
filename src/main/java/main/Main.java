package main;

import main.airtable.RecordAccountData;
import main.airtable.RecordLikedData;
import main.airtable.RecordUserFeed;
import main.facebook.user.User;
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


//        RecordAccountData accountData = new RecordAccountData();
//        accountData.reformatData();

//        RecordUserFeed userFb = new RecordUserFeed();
//        userFb.reformatData();

        RecordLikedData like = new RecordLikedData();
        like.reformatData();

    }
}