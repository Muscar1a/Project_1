package main.facebook;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GetData {
    protected String order;
    protected static String accessToken;
    public static String getAccessToken(){
        return accessToken;
    }
    public static void setAccessToken(String accessToken){
        GetData.accessToken = accessToken;
    }
    protected GetData(String order){
        this.order = order;
    }
    public static String getData(String accessToken, String order) throws IOException{
        URL url = new URL("https://graph.facebook.com/v20.0/" + order + accessToken);

        HttpURLConnection httpConnect = (HttpURLConnection) url.openConnection();
        httpConnect.setRequestMethod("GET");
        InputStream responseStream = httpConnect.getResponseCode() / 100 == 2
                ? httpConnect.getInputStream()
                : httpConnect.getErrorStream();

        Scanner scn = new Scanner(responseStream).useDelimiter("\\A");
        String res = scn.hasNext() ? scn.next() : "";
//        String response = GetData.getData(GetData.getAccessToken(), order);
//        System.out.println(res);
        scn.close();

        return res;
    }

}