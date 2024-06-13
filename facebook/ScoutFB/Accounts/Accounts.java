package facebook;

public class Accounts {
    private String name;
    private String id;
    private String category;
    public Accounts(String name, String id, String category){
        this.name = name;
        this.id = id;
        this.category = category;
    }

    public String getName(){
        return name;
    }

    public String getId(){
        return id;
    }

    public String getCategory(){
        return category;
    }

}
