import java.lang.*;

class Employee {
    private String name;
    private String employeeId;
    private Double salary;

    Employee (String name, String employeeId, Double salary) {
        this.name = name;
        this.employeeId = employeeId;
        this. salary = salary;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public Double getSalary() {

        return salary;

    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public void setSalary(Double salary) {
        if(salary > 10000){
            this.salary = salary;
        }
    }

    void displayDetails (){
        System.out.println("name : " + this.getName());
        System.out.println("employeeId : " + this.getEmployeeId());
        System.out.println("salary : "+ this.getSalary());
    }

    void calculateAnnualSalary() {
        System.out.println("the anual sal is : " + this.getSalary() * 12);

    }


    void giveRaise(double percentage){
        Double r = this.getSalary()+(this.getSalary()*(percentage/100));
        this.setSalary(r);
        System.out.println("the raise got is : "+ this.getSalary());
    }

    boolean isHighEarner(){
        return this.getSalary() > 75000;
    }
}

//--------- develoiper -------
class Developer extends Employee {
    private String progLang;

    Developer(String name, String employeeId, Double salary, String progLang){
        super(name,employeeId,salary);
        this.progLang = progLang;
    }

    public void setProgLang(String progLang) {
        this.progLang = progLang;
    }
    public String getProgLang(){
        return this.progLang;
    }

    void writeCode() {
        System.out.println(this.getName() + " writes code in " + this.getProgLang());
    }
    void debugCode(){
        System.out.println(this.getName() + " debug code in " + this.getProgLang());
    }


    @Override
    void displayDetails() {
        System.out.println("developer has over ridden the display method");
    }
}

// ---------- tester ---------

class Tester extends Employee {
    String testingTool;

    Tester(String name, String employeeId, Double salary, String testingTool) {
        super(name,employeeId,salary);
        this.testingTool = testingTool;
    }

    public String getTestingTool() {
        return testingTool;
    }

    public void setTestingTool(String testingTool) {
        this.testingTool = testingTool;
    }

    void testSoftware () {
        System.out.println(this.getName() + " usese " + this.getTestingTool());
    }

    @Override
    void displayDetails() {
        System.out.println("tester has overriden the method");
    }
    void displayDetails(String e) {
        System.out.println("tester has overloaded the method becoz of different signature "+e);
    }
}


class OverridingExp{
    public static void main(String[] args) {
        Developer d1  = new Developer("mee", "111",50000.0,"java");
        d1.writeCode();
        d1.displayDetails();       // inherited
        /*
        ------------------output-----------
mee writes code in java
developer has over ridden the display method
         */

        Tester t1 = new Tester("meeefgr", "111T",30000.0,"xyz");
       t1.displayDetails("t1 is printinf"); // Remember : the overridin will only work if the method signature and no. of parameters are same else it will become over loading
        //the return type is not part of the method signature used for overloading. You cannot overload a method merely by changing its return type.
        t1.displayDetails();

        /*
        tester has overriden the methodt1 is printinf
tester has overriden the method

         */

        System.out.println(" ");
//--------------------- Runtime Polymorphism ----------------
            Employee e1 = new Developer("mee", "111", 50000.0, "java");
            Employee e2 = new Tester("meeefgr", "111T", 30000.0, "xyz");
            Employee e3 = new Employee("abc", "222", 40000.0);
        System.out.println("------------------Runtime Polymorphysm checking-------- ");
            e1.displayDetails();
            e2.displayDetails();
        e2.displayDetails("-----Tester 2 ");
        e3.displayDetails();

    }
}

/*
------------------ output --------------------
PS E:\E\CORE-JAVA\oops\Inheritance> java OverridingExp
mee writes code in java
developer has over ridden the display method
tester has overloaded the method becoz of different signature t1 is printinf
tester has overriden the method

------------------Runtime Polymorphysm checking--------
developer has over ridden the display method
tester has overriden the method
name : abc
employeeId : 222
salary : 40000.0


---------------------------- for the access we tried ---------------------


PS E:\E\CORE-JAVA\oops\Inheritance> javac OverridingExp.java
OverridingExp.java:156: error: method displayDetails in class Employee cannot be applied to given types;
        e2.displayDetails("-----Tester 2 ");
          ^
  required: no arguments
  found:    String
  reason: actual and formal argument lists differ in length
1 error


----> Explaination:

What can I call?

Based on Employee:

e2.displayDetails();       // YES
e2.calculateAnnualSalary(); // YES
e2.giveRaise(10);           // YES
e2.isHighEarner();          // YES

But:

e2.testSoftware();          // NO
e2.displayDetails("hello"); // NO

because those aren't available in the Employee reference type.

Yet if displayDetails() is overridden:

e2.displayDetails();

the Tester implementation runs.

That is the heart of polymorphism.

4. Compare these two carefully
Case A
Tester t1 = new Tester(...);

t1.displayDetails("hello");
t1.testSoftware();
t1.displayDetails();

The reference is Tester.

So the compiler knows about:

Tester methods
    ↓
displayDetails()
displayDetails(String)
testSoftware()

Everything is available.

Case B
Employee e2 = new Tester(...);

Now the reference is Employee.

The compiler sees only the Employee interface of that object:

Employee methods
    ↓
displayDetails()
calculateAnnualSalary()
giveRaise()
isHighEarner()
...

But at runtime:

e2.displayDetails();

still goes to:

Tester.displayDetails()

because that method was overridden.
 */