package facebook;

import com.restfb.*;
import com.restfb.exception.FacebookException;
import com.restfb.types.*;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

import facebook.Accounts;

public class ScoutAccounts {
    private static final String accessToken = "EAAEEI6GMikcBO1ZAT4rMZBeCJ7yX9X1iYzzzNSKpFJ4YMkoxBUWVfRRZAew2sZAlEC3ZBVmTjLrq07sEu1aR0ym5ZBMZAfaZCnBmXDQnngZBQqcurKsZC4H92vv3ajjAeI7ziHSHMBgLcwuZAjsq5BIu1KK94sHyNZBpNVT4fmmWustnJx0xviPEcebBQ87CAgb0dzCZCAi5uF0Jr0vde098hhQmlRkeaZANV75qphaLDJteX9VpDhgIZCBbB1U26VYZAWBCB7GduuoZD";

    private static List<Accounts> accounts = new ArrayList<>();

    public static void main(String[] args) {
        scheduleFetchData();
    }

    private static void scheduleFetchData() {
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                fetchData();
                processAccounts();
                saveDataToJson();
            }
        };
        // Schedule the task to run every 2 months (adjust the interval as needed)
        long interval = 2L * 30L * 24L * 60L * 60L * 1000L; // 2 months in milliseconds
        timer.schedule(task, new Date(), interval);
    }

    private static void fetchData() {
        // Fetch data from the FB API (feed, accounts,
        Version version = Version.VERSION_20_ß;
        FacebookClient fbClient = new DefaultFacebookClient(accessToken, version);
        try {
            Connection<Page> acc = fbClient.fetchConnection("me/accounts", Page.class, Parameter.with("data", "name, id, category"));

            accounts.clear();
            for (List<Page> fPage : acc) {
                for (Page page : fPage) {
                    String name = page.getName() != null ? page.getName() : "";
                    String id = page.getId() != null ? page.getId() : "";
                    String category = page.getCategory() != null ? page.getCategory() : "";

                    Accounts account = new Accounts(name, id, category);
                    accounts.add(account);
                }
            }

        } catch (FacebookException e) {
            e.printStackTrace();
        }
    }

    private static void processAccounts() {
        System.out.println("Fetched Accounts:");
        for (Accounts account : accounts) {
            System.out.println("Name: " + account.getName());
            System.out.println("Id: " + "fb.com/" + account.getId());
            System.out.println("Category: " + account.getCategory());
            System.out.println();
        }
    }

    public static List<Accounts> getAccounts() {
        return accounts;
    }

    private static void saveDataToJson() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try (FileWriter writer = new FileWriter("accounts_data.json")) {
            gson.toJson(accounts, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
