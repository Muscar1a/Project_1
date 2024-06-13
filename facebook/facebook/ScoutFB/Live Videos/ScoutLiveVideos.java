package facebook;

import com.restfb.*;
import com.restfb.exception.FacebookException;
import com.restfb.types.*;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class ScoutLiveVideos {
    private static final String accessToken = "EAAEEI6GMikcBO2g65NZB0IiYo0tomEjpMpu4DXhRH7M8nZCZBEaZAGLmoRzZBMW7BCUFlg4tk02evCF7XqsFTfuoB1vE2iRPsGkwolvD5B2YezBhEPO6ZCF3ZAAOvDzYGufXOjy8s9BCoWuJd8hFMcUROeE2pZBurBlvvc1mUzvCi4p5tYCAIZC1UbvW78yZB58jYl1mTYBbLhsfEqgEgwkv9NZAlhIiqBDwUeedZCMFP49KEHzeaZApdaM83qpZCs0sphqKBnD3wZD";

    private static List<LiveVideos> liveVideos = new ArrayList<>();

    public static void main(String[] args){
        scheduleFetchData();
    }

    private static void scheduleFetchData() {
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                fetchData();
                processLiveVideos();
                saveDataToJson();
            }
        };
        // Schedule the task to run every 2 months (adjust the interval as needed)
        long interval = 2L * 30L * 24L * 60L * 60L * 1000L; // 2 months in milliseconds
        timer.schedule(task, new Date(), interval);
    }

    private static void fetchData(){
        // Fetch data from the FB API (feed, accounts,
        Version version = Version.VERSION_20_ß;
        FacebookClient fbClient = new DefaultFacebookClient(accessToken, version);
        try {
            Connection<LiveVideo> liveVids = fbClient.fetchConnection("me/live_videos", LiveVideo.class, Parameter.with("data", "status, stream_url, embed_html"));
            for (List<LiveVideo> page : liveVids){
                for (LiveVideo liveVideo : page){
                    String status = liveVideo.getStatus() != null ? liveVideo.getStatus() : "";
                    String streamUrl = liveVideo.getStreamUrl() != null ? liveVideo.getStreamUrl() : "";
                    String embedHtml = liveVideo.getEmbedHtml() != null ? liveVideo.getEmbedHtml() : "";

                    LiveVideos liveVid = new LiveVideos(status, streamUrl, embedHtml);
                    liveVideos.add(liveVid);
                }
            }

        } catch (FacebookException e){
            e.printStackTrace();
        }
    }

    private static void processLiveVideos() {
        System.out.println("Fetched Live Videos:");
        for (LiveVideos liveVid : liveVideos) {
            System.out.println("Status: " + liveVid.getStatus());
            System.out.println("Stream URL: " + liveVid.getStreamUrl());
            System.out.println("Embed HTML: " + liveVid.getEmbedHtml());
            System.out.println();
        }
    }

    public static List<LiveVideos> getLiveVideos(){
        return liveVideos;
    }

    private static void saveDataToJson() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try (FileWriter writer = new FileWriter("liveVideos_data.json")) {
            gson.toJson(liveVideos, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
