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
public class UpcastingExperiment {
    public static void main(String[] args) {

        Developer d = new Developer(); // the subclass


        Employee e = d; // this is called upcasting ... upcasting to parent class

        e.work();
        e.writeCode();
    }
}

/*
             Developer object
             ┌───────────────┐
d ──────────►│               │
             │ work()        │
             │ writeCode()   │
             └───────────────┘
                    ▲
                    │
e ──────────────────┘
   Employee reference


   --------------------------- output -------------
   UpcastingExperiment.java:25: error: cannot find symbol
        e.writeCode();
         ^
  symbol:   method writeCode()
  location: variable e of type Employee
1 error

 */