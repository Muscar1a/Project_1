package main.facebook.user;

import main.facebook.GetData;

public class Feed extends GetData {
    public String order = new String("me?fields=feed%7Bmessage%2Cid%7D&access_token=");
    public Feed(String order){
        super(order);
        this.order = order;
    }
}
