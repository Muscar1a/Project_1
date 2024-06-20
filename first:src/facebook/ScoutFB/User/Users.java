package facebook;

public class Users {
    private String name;
    private String id;
    public Users(String name, String id){
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
