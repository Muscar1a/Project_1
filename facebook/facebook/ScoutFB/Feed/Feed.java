package facebook;
import java.util.Date;
public class Feed {
    private String message;
    private Date created_time;
    public Feed(String message, Date created_time){
        this.message = message;
        this.created_time = created_time;
    }
    public String getMessage(){
        return message;
    }
    public Date getCreatedTime(){
        return created_time;
    }

}
