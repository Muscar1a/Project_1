package facebook;

import com.restfb.*;
import com.restfb.exception.FacebookException;
import com.restfb.types.*;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class ScoutLikes {
    private static final String accessToken = "EAAEEI6GMikcBO1EXibLTSGZAwSo6DHbH4xXE6C6v8xlOubzqXJNfZCZBrWDz55dJVRvDrFzZAsRjDQTdZCOIQbEXZCkRUDB6ZB5294HALPL75rrjwiZAnwlttPC2pufZCNUIZAyyRytnv0Ab6ghhofATXiheftl0cMg1X5JxUYZC8hTKMsHCl4fnNDDx5mDW6nkcS9dFboWHdQlc8xstAvUmS4BfUd0U0CXMuctSVzw1IxMt6C2wZCbChA7XFsNFXWxkP1OrAQZDZD";

    private static List<Likes> likes = new ArrayList<>();

    public static void main(String[] args){
        scheduleFetchData();
    }

    private static void scheduleFetchData() {
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                fetchData();
                processLikes();
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
            Connection<Page> like = fbClient.fetchConnection("me/likes", Page.class, Parameter.with("data", "name, id"));

            likes.clear();

            for (List<Page> feedPage : like) {
                for (Page page : feedPage){
                    String name = page.getName() != null ? page.getName() : "";
                    String id = page.getId() != null ? page.getId() : "";

                    Likes l = new Likes(name, id);
                    likes.add(l);

                }
            }
        } catch (FacebookException e){
            e.printStackTrace();
        }
    }

    private static void processLikes() {
        System.out.println("Fetched Likes:");
        for (Likes like : likes) {
            System.out.println("Name: " + like.getName());
            System.out.println("Id: " + "fb.com/" + like.getId());
            System.out.println();
        }
    }

    public static List<Likes> getLikes(){
        return likes;
    }
    private static void saveDataToJson() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try (FileWriter writer = new FileWriter("likes_data.json")) {
            gson.toJson(likes, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
