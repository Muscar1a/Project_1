package facebook.user;
import facebook.GetData;
public class LiveVideos extends GetData {
    public String order = new String("me?fields=live_videos&access_token=");
    public LiveVideos(String order){
        super(order);
        this.order = order;
    }
}
