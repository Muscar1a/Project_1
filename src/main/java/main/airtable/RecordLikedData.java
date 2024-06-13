package main.airtable;

import main.facebook.GetData;
import main.facebook.user.Feed;
import main.facebook.user.Likes;
import main.facebook.user.User;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class RecordLikedData extends PostRequest{

    @Override
    public String reformatData() throws IOException {

        User userFb = new User("me?fields=id%2Cname%2Cemail&access_token=");
        Likes likedData = new Likes("me?fields=likes%7Bname%2Ccreated_time%7D&access_token=");

        String userFBOrder = userFb.order;
        String likedDataOrder = likedData.order;

        final String API_INFO = new String(Files.readAllBytes(Paths.get("src/main/java/main/airtable/token.json")));
        JSONObject airtableObject = new JSONObject(API_INFO);

        final String TOKEN_AIRTABLE = airtableObject.getString("APIToken");
        final String BASE_ID = airtableObject.getString("baseID");
        final String TABLE_ID = airtableObject.getString("likeTable");

        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter your access token:");
        String accessToken = scanner.nextLine();
        User.setAccessToken(accessToken);
        Feed.setAccessToken(accessToken);

        try {
            String userRes = GetData.getData(accessToken, userFBOrder);
            String likedRes = GetData.getData(accessToken, likedDataOrder);
            JSONObject jsonUser = new JSONObject(userRes);
            JSONObject jsonLiked = new JSONObject(likedRes);
            System.out.println(jsonLiked);

            JSONArray jsonLikeArray = jsonLiked.getJSONObject("likes").getJSONArray("data");
//            System.out.println(jsonLikeArray);

            for (int i = 0; i < jsonLikeArray.length(); i++) {
                JSONArray recordsArray = new JSONArray();
                JSONObject fieldsObject = new JSONObject();
                JSONObject dataObject = jsonLikeArray.getJSONObject(i);

                fieldsObject.put("liked page", dataObject.getString("name"));
                fieldsObject.put("liked page id", dataObject.getString("id"));
                fieldsObject.put("userId", jsonUser.getString("id"));
                fieldsObject.put("fullname", jsonUser.getString("name"));

                JSONObject recordObject = new JSONObject();
                recordObject.put("fields", fieldsObject);
                recordsArray.put(recordObject);
                JSONObject outputObject = new JSONObject();
                outputObject.put("records", recordsArray);
                String resData = outputObject.toString();
                System.out.println(resData);
                POSTRequest(BASE_ID, TABLE_ID, TOKEN_AIRTABLE, resData);
                System.out.println("DONE!");
            }

            return null;
        } catch(JSONException e) {
            System.out.println("Error: Not found required field");
            return null;
        } catch(IOException e) {
            System.out.println("Error: I/O");
            return null;
        } catch(NoSuchElementException e) {
            System.out.println("Error: No token found");
            return null;
        } catch(Exception e) {
            System.out.println("Unknown error occured");
            return null;
        }

    }
}

// EAAEEI6GMikcBOwiZCkyiZC4gUeUNmoW1vaNErTmenAGLJZAzALeHcPNDtxCnZAHqk6Geo3Vgxtj1RzQHZAGHF9bdSmiyVTdxbgLFD769CWJK9XI70BfZCOxqT1hUyqX1ZC9xYZBsRpPDOwAJjlQIF8WUqHX8qp5WsMWUF9luudlV9d4zA7BFw8MQaZCIFlRkoakMdDZCCFEAjA51qXEq8J8SmMu4WwbZBlbDgHORE7XsIRcDRsRtCgFjTlCZCObUlIwjVFFBIAZDZD