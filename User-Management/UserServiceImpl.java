import java.util.*;
public class UserServiceImpl implements UserService {
    public static ArrayList<User> users = new ArrayList<>();

    public void display() {
        // System.out.println("| name = "+ u.getName()+" | age = " + u.getAge()+" |");
        for(User u : users){
            System.out.println("| name = "+ u.getName()+" | age = " + u.getAge()+" |");
        }
    }

    public void addUser() {
        Scanner sc = new Scanner(System.in);
        System.out.print("name = ");
        String name = sc.nextLine();
        System.out.print("age = ");
        int age = sc.nextInt();
        sc.nextLine();
        if (age < 13) {
            System.out.print(" Invalid user age ");
        } else {
            User us = new User(name, age);
            users.add(us);
        }
    }
    public void deleteUser() {
        Scanner sc = new Scanner(System.in);
        System.out.println("enter the user to be deleted = ");
        String name = sc.nextLine();
        boolean found = false;
        for(int i = 0; i < users.size(); i++) {
            if(users.get(i).getName().equals(name)){
                users.remove(i);
                found = true;
                break;
            }
        }
        if(!found) System.out.println("user not found. enter the correct name.");
    }
}