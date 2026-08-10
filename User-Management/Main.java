import java.util.*;
public class Main{

    public static void main(String[] args) {

        //users.add(new User("Meena", 22));
        UserService userService = new UserServiceImpl();
        Scanner sc = new Scanner(System.in);
        System.out.print("1- add user, 2-all user 3- delete by username");
        System.out.print("enter the no. = ");

        boolean flag = true;
        do {
            int key = sc.nextInt();
            sc.nextLine();
            switch (key) {
                case 1:
                    System.out.print("enter the no. of users = ");
                    int n = sc.nextInt();
                    sc.nextLine();
                    for (int i = 0; i < n; i++) {
                        userService.addUser();
                    }
                    break;
                case 2:
                    userService.display();
                    break;
                case 3:
                    userService.deleteUser();
                    break;
                case 0:
                    flag = false;
                    System.out.println("Exiting... ");
                    break;

                default:
                    System.out.println("Invalid choice");
            }
        } while(flag);


        userService.display();
    }
}