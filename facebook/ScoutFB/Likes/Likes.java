package facebook;

public class Likes {
    private String name;
    private String id;
    public Likes(String name, String id){
        this.name = name;
        this.id = id;
    }
    public String getName(){
        return name;
    }
    public String getId(){
        return id;
    }
}
