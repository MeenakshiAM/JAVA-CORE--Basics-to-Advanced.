class Employee {
    void work() {
        System.out.println("Employee works");
    }
}

class Developer extends Employee {
    void writeCode() {
        System.out.println("Developer writes code");
    }

    @Override
    void work() {
        System.out.println("Developer works");
    }
}
public class DowncastingExp {
    public static void main(String[] args) {

        Employee e = new Employee();
       // Developer d = (Developer) e; // throws error
        // instead u can ask
        if (e instanceof Developer) {
            Developer d = (Developer) e;
            d.writeCode();
        }

    }
}

/*
 Downcasting is compile-time allowed when the types are related,
but it succeeds at runtime only if the actual object is an instance
of the target type.

e1 ───────► Developer object
             ↑
             └── also an Employee

e2 ───────► Tester object
             ↑
             └── also an Employee

e3 ───────► Employee object


-------------------------------- Output --------------
PS E:\E\CORE-JAVA\oops\downcasting> java InstanceOfExp
Developer works
Developer writes code
true
true
false
true
false
true
true
false
false


 */