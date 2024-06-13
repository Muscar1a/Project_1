package main.airtable;

import main.facebook.GetData;
import main.facebook.user.Account;
import main.facebook.user.Feed;
import main.facebook.user.User;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class RecordUser extends PostRequest{

    @Override
    public String reformatData() throws IOException {

        User userFb = new User("me?fields=id%2Cname%2Cemail&access_token=");
        Feed userFeed = new Feed("me?fields=feed%7Bmessage%2Cid%7D&access_token=");

        String userFBOrder = userFb.order;
        String userFeedOrder = userFeed.order;

        final String API_INFO = new String(Files.readAllBytes(Paths.get("src/main/java/main/airtable/token.json")));
        JSONObject airtableObject = new JSONObject(API_INFO);

        final String TOKEN_AIRTABLE = airtableObject.getString("APIToken");
        final String BASE_ID = airtableObject.getString("baseID");
        final String TABLE_ID = airtableObject.getString("userTable");

        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter your access token:");
        String accessToken = scanner.nextLine();
        User.setAccessToken(accessToken);
        Feed.setAccessToken(accessToken);

        try {
            String userRes = GetData.getData(accessToken, userFBOrder);
            String userFeedRes = GetData.getData(accessToken, userFeedOrder);
            JSONObject jsonUser = new JSONObject(userRes);
            JSONObject jsonFeed = new JSONObject(userFeedRes);
//            System.out.println(jsonFeed);

            JSONArray jsonFeedArray = jsonFeed.getJSONObject("feed").getJSONArray("data");
//            System.out.println(jsonFeedArray);

            for (int i = 0; i < jsonFeedArray.length(); i++) {
                JSONArray recordsArray = new JSONArray();
                JSONObject fieldsObject = new JSONObject();
                JSONObject dataObject = jsonFeedArray.getJSONObject(i);

                if (dataObject.has("message")) {
                    fieldsObject.put("message", dataObject.getString("message"));
                    fieldsObject.put("userId", jsonUser.getString("id"));
                    fieldsObject.put("fullname", jsonUser.getString("name"));

                    JSONObject recordObject = new JSONObject();
                    recordObject.put("fields", fieldsObject);
                    recordsArray.put(recordObject);
                    JSONObject outputObject = new JSONObject();
                    outputObject.put("records", recordsArray);
                    String resData = outputObject.toString();
//                    System.out.println(resData);
                    POSTRequest(BASE_ID, TABLE_ID, TOKEN_AIRTABLE, resData);

                }



            }

            /*
            JSONObject newJsonObject = new JSONObject();
            JSONArray recordsArray = new JSONArray();
            JSONObject fieldsObject = new JSONObject();

            fieldsObject.put("userId", jsonUser.getString("id"));
            fieldsObject.put("fullname", jsonUser.getString("name"));
//            fieldsObject.put("message", jsonFeed.getString("message"));
//            fieldsObject.put("created_time", jsonFeed.getString("created_time"));
            JSONObject fieldsWrapper = new JSONObject();
            fieldsWrapper.put("fields", fieldsObject);
            recordsArray.put(fieldsWrapper);
            newJsonObject.put("records", recordsArray);

            String resData = newJsonObject.toString();
            System.out.println(resData);
//            POSTRequest(BASE_ID, TABLE_ID, TOKEN_AIRTABLE, resData);
            System.out.println("DONE!");
             */

//            return resData;
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