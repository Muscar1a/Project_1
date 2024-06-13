package facebook;

import com.restfb.*;
import com.restfb.exception.FacebookException;
import com.restfb.types.*;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class ScoutUser {
    private static final String accessToken = "EAAEEI6GMikcBOySdoQhd8HFh7bS5gZBAfbBSi5Cau8nVpXJoYUvlce8wR8sPtmSsEQ5NnV7vh9FKEi3id7922Ui7dA5Xm2ORQj79T5HUBskcFhft2ICUA9msa2Qb4cReaT8Px5R14k8ZBvqrrAvRk90P2p2I9Qockx7GSHDUZC2ZAVvNbvBSLuxOTQCIQa7owImZCZBhZCaFiCuw95FctjYYnqZAnZA7KBM09ocEoDBZC6aaPOdYvlcB4GdrIe78Q4VrXzWCIZD";
    private static List<Users> users = new ArrayList<>();

    public static void main(String[] args){
        scheduleFetchData();
    }

    private static void scheduleFetchData() {
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                fetchData();
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
            User me = fbClient.fetchObject("me", User.class, Parameter.with("fields", "name, id"));
            users.clear();
            String name = me.getName() != null ? me.getName() : "";
            String id = me.getId() != null ? me.getId() : "";

            Users user = new Users(name, id);
            users.add(user);

        } catch (FacebookException e){
            e.printStackTrace();
        }
    }

    private static void saveDataToJson() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try (FileWriter writer = new FileWriter("user_data.json")) {
            gson.toJson(users, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
