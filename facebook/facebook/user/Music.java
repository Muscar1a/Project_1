package facebook.user;
import facebook.GetData;
public class Music extends GetData {
    public String order = new String("me?fields=music%7Bname%2Ccreated_time%7D&access_token=");
    public Music(String order){
        super(order);
        this.order = order;
    }
}
