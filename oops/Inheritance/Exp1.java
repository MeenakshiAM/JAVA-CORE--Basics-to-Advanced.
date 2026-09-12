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
        System.out.println("the raise got is : "+ r);
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

}

class Exp1 {
    public static void main(String[] args) {
        Developer d1  = new Developer("mee", "111",50000,"java");
        d1.writeCode();
        d1.displayDetails();       // inherited
        d1.calculateAnnualSalary();// inherited
        d1.giveRaise(10);         // inherited
        d1.writeCode();           // Developer's own

    }
}