package facebook;

import com.restfb.*;
import com.restfb.exception.FacebookException;
import com.restfb.types.*;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class ScoutFeed {
    private static final String accessToken = "EAAEEI6GMikcBO1EXibLTSGZAwSo6DHbH4xXE6C6v8xlOubzqXJNfZCZBrWDz55dJVRvDrFzZAsRjDQTdZCOIQbEXZCkRUDB6ZB5294HALPL75rrjwiZAnwlttPC2pufZCNUIZAyyRytnv0Ab6ghhofATXiheftl0cMg1X5JxUYZC8hTKMsHCl4fnNDDx5mDW6nkcS9dFboWHdQlc8xstAvUmS4BfUd0U0CXMuctSVzw1IxMt6C2wZCbChA7XFsNFXWxkP1OrAQZDZD";

    public static void main(String[] args){
        scheduleFetchData();
    }
    private static List<Feed> feeds = new ArrayList<>();
    private static void scheduleFetchData() {
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                fetchData();
                processFeeds();
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
            Connection<Post> feed = fbClient.fetchConnection("me/feed", Post.class, Parameter.with("data", "created_time, message"));

            feeds.clear();

            for (List<Post> page : feed){
                for (Post aPost : page){
                    String message = aPost.getMessage() != null ? aPost.getMessage() : "";
                    Date created_time = aPost.getCreatedTime() != null ? aPost.getCreatedTime() : new Date();

                    Feed f = new Feed(message, created_time);
                    feeds.add(f);
                }
            }

        } catch (FacebookException e){
            e.printStackTrace();
        }
    }

    private static void processFeeds() {
        System.out.println("Fetched Feeds:");
        for (Feed feed : feeds) {
            System.out.println("Created Time: " + feed.getCreatedTime());
            System.out.println("Message: " + feed.getMessage());
            System.out.println();
        }
    }

    public static List<Feed> getFeeds(){
        return feeds;
    }

    private static void saveDataToJson() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try (FileWriter writer = new FileWriter("feed_data.json")) {
            gson.toJson(feeds, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
