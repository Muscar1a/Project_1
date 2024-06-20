package main.facebook.user;

import main.facebook.GetData;

public class Managed extends GetData {
    public String order = new String("me?fields=accounts.limit(1000)%7Bcategory_list%2Ctasks%2Cname%7D&access_token=");
    public Managed(String order){
        super(order);
        this.order = order;
    }
}
