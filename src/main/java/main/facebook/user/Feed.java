package main.facebook.user;

import main.facebook.GetData;

public class Feed extends GetData {
//    public String order = new String("me?fields=id%2Cname%2Cemail&access_token=");
    public String order = new String("me?fields=feed.limit(1000)&access_token=");

    public Feed(String order){
        super(order);
        this.order = order;
    }
}
