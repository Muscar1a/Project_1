package main.airtable;

import org.json.JSONException;
import org.json.JSONObject;

import javax.net.ssl.HttpsURLConnection;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;

public class GetRequest {
    public static String getFromAirtable(String baseID, String tableID, String airtableAPIToken) throws IOException {
        URL url = new URL("https://api.airtable.com/v0/" + baseID +"/" + tableID); // +"?maxRecords=1000&view=Grid%20view" + "");
        HttpsURLConnection httpConn = (HttpsURLConnection) url.openConnection();
        httpConn.setRequestMethod("GET");

        httpConn.setRequestProperty("Authorization", "Bearer " + airtableAPIToken);

        InputStream responseStream = httpConn.getResponseCode() / 100 == 2
                    ? httpConn.getInputStream()
                    : httpConn.getErrorStream();
        Scanner s = new Scanner((responseStream)).useDelimiter("\\A");
        String response = s.hasNext() ? s.next() : "";
//        System.out.println(response);
        s.close();
        return response;
    }

    public static void toJsonFile(String response, String fileName) {
        FileWriter fileWriter = null;
        try {
            JSONObject jsonObject = new JSONObject(response);
//            System.out.println("Try to export");
            fileWriter = new FileWriter(fileName);
//            System.out.println("Exporting");
            fileWriter.write(jsonObject.toString());
//            System.out.println("Export successful");
        } catch (JSONException e) {
            System.out.println("Required files not found");
        }
        catch (IOException e) {
            System.out.println("I/O error");
        } catch (Exception e) {
            System.out.println("Unexpected error");
        } finally {
            if (fileWriter != null) {
                 try {
                     fileWriter.close();
                 } catch (IOException e) {
                     System.out.println("Error closing file writer");;
                 }
            }
        }
    }
}
