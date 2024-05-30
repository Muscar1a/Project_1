package facebook;

import com.restfb.Connection;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Version;
import com.restfb.exception.FacebookException;
import com.restfb.types.*;

import java.util.*;

public class ScoutFB {
    private static final String accessToken = "EAAEEI6GMikcBO1yxRYvyt7wRWN2qfJoXU7iWUlPeSlg3dZBUxvCckRveZAVSqw5RZCdqhCwZC1ZBlmlarGDqS8UhZB8FYke45zhlVYl1fvrNFXeVAZC298p6AGybpt0o3y0M22BTw1stAQEZCbl79qUaRi7F8s8Wnas5GYIe6onTog8ERfKhMZCgX36whCZB2izw0MhXVZBuJB5r4Am6y5gwb5MZBZALN6IZBd0ZB6IOMnSChgV4hvdZCeY2Ox6eK5LoX8mHZBQwy4AZDZD";

    public static void main(String[] args){
        scheduleFetchData();
    }

    private static void scheduleFetchData() {
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                fetchData();
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
            User me = fbClient.fetchObject("me", User.class);
            System.out.println(me.getName());
            System.out.println(me.getId());

            Connection<Post> feed = fbClient.fetchConnection("me/feed", Post.class);
            for (List<Post> page : feed){
                for (Post aPost : page){
                    System.out.println(aPost.getMessage());
                    System.out.println(aPost.getCreatedTime());
                }
            }

            Connection<Page> acc = fbClient.fetchConnection("me/accounts", Page.class);
            for (List<Page> fPage : acc) {
                for (Page page : fPage) {
                    System.out.println(page.getName());
                    System.out.println("fb.com/" + page.getId());
                }
            }

            Connection<Page> likes = fbClient.fetchConnection("me/likes", Page.class);
            for (List<Page> feedPage : likes) {
                for (Page page : feedPage){
                    System.out.println(page.getName());
                    System.out.println("fb.com/" + page.getId());
                }
            }

            Connection<LiveVideo> liveVids = fbClient.fetchConnection("me/live_videos", LiveVideo.class);
            for (List<LiveVideo> page : liveVids){
                for (LiveVideo liveVideo : page){
                    System.out.println(liveVideo.getStatus());
                    System.out.println(liveVideo.getStreamUrl());
                    System.out.println(liveVideo.getEmbedHtml());
                }
            }

        } catch (FacebookException fe){
            fe.printStackTrace();
        }
    }
}
