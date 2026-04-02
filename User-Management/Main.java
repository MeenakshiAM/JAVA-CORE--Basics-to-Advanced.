import java.util.*;
public class Main{



    public static void main(String[] args) {

        //users.add(new User("Meena", 22));
        UserService userService = new UserService();
        Scanner sc = new Scanner(System.in);
        System.out.print("enter the no. of users = ");
        int n = sc.nextInt();
        sc.nextLine();

        for(int i = 0; i<n; i++) {
            userService.addUser();
        }
        userService.display();
    }
}