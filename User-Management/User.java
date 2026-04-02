public class User {
    private String Name;
    private int age;

    public User(String Name, int age) {
        this.age = age;
        this.Name = Name;
    }

    public int getAge(){
        return this.age;
    }

    public String getName(){
        return this.Name;
    }

    public void setName(String name) {
         this.Name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }
}