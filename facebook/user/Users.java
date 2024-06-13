package facebook.user;
import facebook.GetData;
public class Users extends GetData {
    public String order = new String("me?fields=id%2Cname%2Cemail&access_token=");

    public Users(String order){
        super(order);
        this.order = order;
    }
}
