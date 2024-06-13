package main.airtable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class RecordUser extends PostRequest{

    @Override
    public String reformatData() throws IOException {

        final String API_INFO = new String(Files.readAllBytes(Paths.get("src/main/java/main/airtable/token.json")));
        JSONObject airtableObject = new JSONObject(API_INFO);

        final String TOKEN_AIRTABLE = airtableObject.getString("APIToken");
        final String BASE_ID = airtableObject.getString("baseID");
        final String TABLE_ID = airtableObject.getString("userTable");

//        Scanner scanner = new Scanner(System.in);
        try {
//            String res = GetData
            String temp = new String(Files.readAllBytes(Paths.get("src/main/java/main/ExportJSON/user_data.json")));


            JSONArray tempJsonArray = new JSONArray(temp);

            JSONObject jsonObject = new JSONObject();

            for (int i = 0; i < tempJsonArray.length(); i++) {
                jsonObject = tempJsonArray.getJSONObject(i);
                // since tempJsonArray.length() = 1
            }

            System.out.println(jsonObject);


//            System.out.println(temp);

//            JSONObject jsonObject = new JSONObject(temp);

            JSONObject newJsonObject = new JSONObject();
            JSONArray recordsArray = new JSONArray();
            JSONObject fieldsObject = new JSONObject();
            fieldsObject.put("userId", jsonObject.getString("id"));
            fieldsObject.put("fullname", jsonObject.getString("name"));
            JSONObject fieldsWrapper = new JSONObject();


            fieldsWrapper.put("fields", fieldsObject);
            recordsArray.put(fieldsWrapper);
            newJsonObject.put("records", recordsArray);


            String resData = newJsonObject.toString();
            System.out.println(resData);
            POSTRequest(BASE_ID, TABLE_ID, TOKEN_AIRTABLE, resData);
            System.out.println("DONE!");

            return resData;

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
