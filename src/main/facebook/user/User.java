package main.facebook.user;

import main.facebook.GetData;

public class User extends GetData {
    public String order = new String("me?fields=id%2Cname%2Cemail&access_token=");
    public User(String order) {
        super(order);
        this.order = order;
    }
}