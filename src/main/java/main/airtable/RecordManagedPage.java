package main.airtable;

import main.facebook.GetData;
import main.facebook.user.Managed;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class RecordManagedPage extends PostRequest{

    @Override
    public String reformatData() throws IOException {
        Managed accountData = new Managed("me?fields=accounts%7Bcategory_list%2Ctasks%2Cname%7D&access_token=");
        String order = accountData.order;

        final String API_INFO = new String(Files.readAllBytes(Paths.get("src/main/java/main/airtable/token.json")));
        JSONObject airtableObject = new JSONObject(API_INFO);

        final String TOKEN_AIRTABLE = airtableObject.getString("APIToken");
        final String BASE_ID = airtableObject.getString("baseID");
        final String TABLE_ID = airtableObject.getString("accountTable");

        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter your access token:");
        String accessToken = scanner.nextLine();
        Managed.setAccessToken(accessToken);

        try {
            String res = GetData.getData(accessToken, order);
            JSONObject jsonObject = new JSONObject(res);
//            System.out.println(jsonObject);


            JSONArray jsonArray = jsonObject.getJSONObject("accounts").getJSONArray("data");
//            System.out.println(jsonArray);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONArray recordsArray = new JSONArray();
                JSONObject fieldsObject = new JSONObject();
                JSONObject dataObject = jsonArray.getJSONObject(i);

//                System.out.println(dataObject);
//                System.out.println("\n\n");

                fieldsObject.put("name", dataObject.getString("name"));
                fieldsObject.put("id", dataObject.getString("id"));
//                System.out.println(fieldsObject);
//                System.out.println("\n\n");


                JSONArray categoryList = dataObject.getJSONArray("category_list");
                String categoryName = "";
                for (int j = 0; j < categoryList.length(); j++) {
                    JSONObject categoryObject = categoryList.getJSONObject(j);
                    categoryName = categoryObject.getString("name");
                    break;
                }
                fieldsObject.put("category", categoryName);

                JSONObject recordObject = new JSONObject();
                recordObject.put("fields", fieldsObject);
                recordsArray.put(recordObject);
                JSONObject outputObject = new JSONObject();
                outputObject.put("records", recordsArray);
                String resData = outputObject.toString();
                System.out.println(resData);
                POSTRequest(BASE_ID, TABLE_ID, TOKEN_AIRTABLE, resData);
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
            System.out.println("Unknown error occurred");
            return null;
        }

    }
}
// EAAEEI6GMikcBOwiZCkyiZC4gUeUNmoW1vaNErTmenAGLJZAzALeHcPNDtxCnZAHqk6Geo3Vgxtj1RzQHZAGHF9bdSmiyVTdxbgLFD769CWJK9XI70BfZCOxqT1hUyqX1ZC9xYZBsRpPDOwAJjlQIF8WUqHX8qp5WsMWUF9luudlV9d4zA7BFw8MQaZCIFlRkoakMdDZCCFEAjA51qXEq8J8SmMu4WwbZBlbDgHORE7XsIRcDRsRtCgFjTlCZCObUlIwjVFFBIAZDZD
