import java.util.*;
//class InvalidAgeException extends Exception
class Error {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("enter the age");
        //int d = sc.nextInt();
        try {
            int age = sc.nextInt();
            //int x = 10 / d;
            //int age = sc.nextInt();
            if(age < 13) {
                throw new InvalidAgeException();
            }
        }
//        catch (Exception e){
//            throw e;
//        }
        catch (InputMismatchException e) {
            System.out.println("Please enter a number");
            throw new InputMismatchException();
        }
        catch (ArithmeticException e) {
            System.out.println("Cannot divide by zero");
        }
        catch (InvalidAgeException e) {
            System.out.println(e.getMessage());
        }

        System.out.print("dont worry its working");
    }
}