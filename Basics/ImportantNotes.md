# ImportantNotes- Basic Programming 

- even main method is a method so u can return from the main when needed.. as it is 

- main() is also just a method. It has a special role as the program's entry point, but it is still a method.

Consider this:

```
public class Main {
public static void main(String[] args) {

        int age = 15;

        if (age < 18) {
            System.out.println("Invalid age");
            return;
        }

        System.out.println("You are eligible");
    }
}

```
What happens?
1. JVM starts executing main().
2. age is 15.
3. The condition age < 18 is true.
4. It prints Invalid age.
5. return; immediately exits the main() method.
6. The remaining statements are skipped.
   Output:
   Invalid age