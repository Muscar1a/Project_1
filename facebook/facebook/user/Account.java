package facebook.user;
import facebook.GetData;
public class Account extends GetData {
    public String order = new String("me?fields=accounts%7Bcategory_list%2Ctasks%2Cname%7D&access_token=");
    public Account(String order){
        super(order);
        this.order = order;
    }
}
