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

        if(e2 instanceof Tester){
             Tester d = (Tester) e2;
            d.work();
            d.writeCode();
        }


    }
}

/*
------------------output--------------
Developer works
Developer writes code
Tester works
Tester writes code


------------------- explanation ----------------------
this is becoz java allows the IS A relationship in Inheritance.
ie when dev and tester is an inherited object they are like
dev IS An employee
TesterIS AN employee
employee Is an employee.


instanceOf keyword basically checks if the two are related to each other.
 */