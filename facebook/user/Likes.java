package facebook.user;
import facebook.GetData;
public class Likes extends GetData {
    public String order = new String("me?fields=likes%7Bname%2Ccreated_time%7D&access_token=");
    public Likes(String order){
        super(order);
        this.order = order;
    }
}
