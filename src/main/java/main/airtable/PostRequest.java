package main.airtable;

import javax.swing.plaf.synth.SynthTextAreaUI;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public abstract class PostRequest {
    public abstract String reformatData() throws IOException;

    public void POSTRequest(String baseID, String tableID, String airtableAPIToken, String res) throws IOException, URISyntaxException {
//        System.out.println("POSTRequest");

        URI uri = new URI("https", "api.airtable.com", "/v0/" + baseID + "/" + tableID, null);
        URL url = uri.toURL();


        HttpURLConnection http = (HttpURLConnection)url.openConnection();
        http.setRequestMethod("POST");
        http.setDoOutput(true);

        http.setRequestProperty("Authorization", "Bearer " + airtableAPIToken);
        http.setRequestProperty("content-type", "application/json");

        try (OutputStream os = http.getOutputStream()) {
            byte[] postData = res.getBytes(StandardCharsets.UTF_8);
            os.write(postData, 0, postData.length);
        }



        System.out.println(http.getResponseCode() + " " + http.getResponseMessage());
        http.disconnect();

//        System.out.println("Disconnect http");

    }
}
