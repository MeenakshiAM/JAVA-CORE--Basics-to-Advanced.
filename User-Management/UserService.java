import java.util.*;

public class UserService {
    public static ArrayList<User> users = new ArrayList<>();

    public static void display() {
        // System.out.println("| name = "+ u.getName()+" | age = " + u.getAge()+" |");
        for(User u : users){
            System.out.println("| name = "+ u.getName()+" | age = " + u.getAge()+" |");
        }
    }

    public static void addUser() {
        Scanner sc = new Scanner(System.in);
        System.out.print("name = ");
        String name = sc.nextLine();
        System.out.print("age = ");
        int age = sc.nextInt();
        sc.nextLine();
        if(age<13) {
            System.out.print(" Invalid user age ");
        }
        else {
            User us = new User(name, age);
            users.add(us);
        }
    }
}