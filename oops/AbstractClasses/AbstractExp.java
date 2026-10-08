abstract class Employee {
    String name;

    Employee(String name) {
        this.name = name;
    }
/*
 abstract void display() {
        System.out.println(this.name);
    }
    AbstractExp.java:8: error: abstract methods cannot have a body
   abstract void display() {
                 ^
AbstractExp.java:15: error: Developer is not abstract and does not override abstract method display() in Employee
class Developer extends Employee {
^
2 errors

AbstractExp.java:24: error: abstract methods cannot have a body
    abstract void display() {
                  ^
1 error

---- exp --------
 abstract methods cannot have a body

 */

//    abstract void display() {
//        System.out.println(this.name);
//    }
   void display() {
       System.out.println(this.name);
    }

   abstract void work();
}

class Developer extends Employee {
    Developer(String name) {
        super(name);
    }

    @Override
    void work() {
        System.out.println("developer");
    }
    @Override
    void display(){
        System.out.println("abstract method overriden");
    }
}
abstract class Tester extends Employee {
    Tester(String name) {
        super(name);
    }
}

public class AbstractExp {
    public static void main(String[] args) {
        Employee e = new Developer("Meenakshi");
        e.display();
        e.work();

        Employee e1 = new Tester("gvfwgsh");
        e1.display();
        e1.work();
        /*
            AbstractExp.java:65: error: Tester is abstract; cannot be instantiated
        Employee e1 = new Tester("gvfwgsh");
                      ^
1 error

becoz tester is an abstract class

         */
    }
}

/*
 ------------------ output --------------

 Meenakshi
 developer
 */