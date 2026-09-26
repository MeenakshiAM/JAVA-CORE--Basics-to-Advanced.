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

class Tester extends Employee {
    void writeCode() {
        System.out.println("Tester writes code");
    }

    @Override
    void work() {
        System.out.println("Tester works");
    }
}

public class InstanceOfExp {
    public static void main(String[] args) {

        Employee e1 = new Developer();
        Employee e2 = new Tester();
        Employee e3 = new Employee();

        if(e1 instanceof Developer){
            Developer d = (Developer) e1;
            d.work();
            d.writeCode();
        }
        System.out.println(e1 instanceof Employee);
        System.out.println(e1 instanceof Developer);
        System.out.println(e1 instanceof Tester);

        System.out.println(e2 instanceof Employee);
        System.out.println(e2 instanceof Developer);
        System.out.println(e2 instanceof Tester);

        System.out.println(e3 instanceof Employee);
        System.out.println(e3 instanceof Developer);
        System.out.println(e3 instanceof Tester);
    }
}