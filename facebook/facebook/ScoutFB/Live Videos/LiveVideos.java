package facebook;

public class LiveVideos {
    private String status;
    private String streamUrl;
    private String embedHtml;
    public LiveVideos(String status, String streamUrl, String embedHtml){
        this.status = status;
        this.streamUrl = streamUrl;
        this.embedHtml = embedHtml;
    }
    public String getStatus(){
        return status;
    }
    public String getStreamUrl(){
        return streamUrl;
    }
    public String getEmbedHtml(){
        return embedHtml;
    }

}
