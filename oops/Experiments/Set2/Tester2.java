import java.util.Arrays;

class Employee {
    private int basicPay;
    private float salary;

    public Employee(int basicPay) {
        this.basicPay = basicPay;
    }

    public int getBasicPay() {
        return this.basicPay;
    }

    public float getSalary() {
        return this.salary;
    }

    public void setSalary(float salary) {
        this.salary = salary;
    }

    public void calculateSalary() {
        float salary = -1.0f;
        if (this.basicPay > 0) {
            salary = this.basicPay + this.basicPay * 40 / 100;
        }
        this.salary = salary;
    }

    @Override
    public String toString() {
        return "Employee (basicPay=" + this.basicPay + ")";
    }
}

class PermanentEmployee extends Employee {
    private static String[][] allowanceDetailsArr = {{"Medical", "Travel", "Internet", "Food"}, {"5", "3", "2", "4"}};
    private String[] allowances;
    private int yearsOfService;

    public PermanentEmployee(int basicPay, int yearsOfService, String[] allowances) {
        super(basicPay);
        this.yearsOfService = yearsOfService;
        this.allowances = allowances;
    }

    public String[] getAllowances() {
        return this.allowances;
    }

    public int getYearsOfService() {
        return this.yearsOfService;
    }

    // To_Trainee
    public Boolean validateYearsOfService() {
        // write your code here
        if(this.getYearsOfService()>=0 && this.getYearsOfService() <=40){
            return true;
        }
        // change return statement accordingly
        return false;
    }

    // To_Trainee
    @Override
    public void calculateSalary() {
        // write your code here
      //  int
        super.calculateSalary();
        float grossSalary = super.getSalary();
        boolean flag = this.validateYearsOfService();
        if (grossSalary == -1.0f || !flag || PermanentEmployee.allowanceDetailsArr == null ||this.allowances == null ){
            super.setSalary(-1.0f);
        }
        else {

            //boolean seen[] = new boolean[PermanentEmployee.allowanceDetailsArr[0].length];
            float total = 0;
            boolean flag1 = false;
            int idx = -1;
            for(int i = 0; i< this.getAllowances().length; i++) {
                String s1 = this.getAllowances()[i];
                flag1 = false;
                idx = -1;
                for(int j = 0 ;j < PermanentEmployee.allowanceDetailsArr[0].length; j++){
                    String s2 = PermanentEmployee.allowanceDetailsArr[0][j];
                    if(s1 != null && s1.equalsIgnoreCase(s2)){
                        flag1 = true;
                        idx = j;
                        break;
                    }
                }

                if(flag1){
                    float per = Integer.parseInt(PermanentEmployee.allowanceDetailsArr[1][idx]);
                    total += per/100.0f *super.getBasicPay();
                }
            }
            if(total != 0.0f) {
                float netSalary  = grossSalary + total;

                if(this.getYearsOfService() >= 10){
                    netSalary += 0.05f * grossSalary;
                }
                netSalary -= 0.12 * super.getBasicPay();
                super.setSalary(netSalary);
            }
            else {
                super.setSalary(-1.0f); // i missed this  too aaa!!!
            }
        }
        //boolean[] seen =
    }

    @Override
    public String toString() {
        return "PermanentEmployee (Employee (basicPay=" + this.getBasicPay()
                + ") allowances=" + Arrays.toString(this.allowances)
                + ", yearsOfService=" + this.yearsOfService + ")";
    }
}

class ContractEmployee extends Employee {
    private char grade;
    private int contractMonths;

    public ContractEmployee(int basicPay, char grade, int contractMonths) {
        super(basicPay);
        this.grade = grade;
        this.contractMonths = contractMonths;
    }

    // To_Trainee
    public int identifyMonthlyAllowance() {
        int monthlyAllowance = -1;
        if(this.grade == 'J' || this.grade == 'j'){
            monthlyAllowance = 3000;
        } else if (this.grade == 'M' || this.grade == 'm') {
            monthlyAllowance = 5000;
        } else if (this.grade == 'S' || this.grade == 's') {
            monthlyAllowance = 8000;
        }

        return monthlyAllowance;
    }

    // To_Trainee
    @Override
    public void calculateSalary() {
        // write your code here
        super.calculateSalary();
        float grossSalary = super.getSalary();

        int monthlyAllowance = this.identifyMonthlyAllowance();

        if(grossSalary == -1.0f || monthlyAllowance == -1 || this.contractMonths <1 || this.contractMonths>36){
            super.setSalary(-1.0f);
        }
        else{
            float salary = grossSalary + monthlyAllowance;

            if(this.contractMonths >= 12) {
                salary += 0.1f * grossSalary;

            }

            if(salary > 50000.0f){
                salary -=  0.1f * (salary - 50000.0f );
            }
            super.setSalary(salary);
        }
    }

    @Override
    public String toString() {
        return "ContractEmployee (Employee (basicPay=" + this.getBasicPay()
                + ") grade=" + this.grade + ", contractMonths=" + this.contractMonths + ")";
    }
}

class Tester2 {
    public static void main(String[] args) {
        String[] a1 = {"medical", "TRAVEL", "Gym"};
        String[] a2 = {"Gym", "Pool"};
        String[] a3 = {"Food", "food"};
        String[] a4 = {"Internet"};

        PermanentEmployee p1 = new PermanentEmployee(30000, 12, a1);
        p1.calculateSalary();
        System.out.println("T1 Permanent 12 years: " + p1.getSalary());

        PermanentEmployee p2 = new PermanentEmployee(30000, 5, a1);
        p2.calculateSalary();
        System.out.println("T2 Permanent 5 years: " + p2.getSalary());

        PermanentEmployee p3 = new PermanentEmployee(30000, 12, a2);
        p3.calculateSalary();
        System.out.println("T3 Permanent no valid allowance: " + p3.getSalary());

        PermanentEmployee p4 = new PermanentEmployee(30000, 41, a1);
        p4.calculateSalary();
        System.out.println("T4 Permanent 41 years: " + p4.getSalary());

        PermanentEmployee p5 = new PermanentEmployee(30000, 12, null);
        p5.calculateSalary();
        System.out.println("T5 Permanent null allowances: " + p5.getSalary());

        PermanentEmployee p6 = new PermanentEmployee(20000, 10, a3);
        p6.calculateSalary();
        System.out.println("T6 Permanent exactly 10 years, repeated allowance: " + p6.getSalary());

        PermanentEmployee p7 = new PermanentEmployee(0, 12, a1);
        p7.calculateSalary();
        System.out.println("T7 Permanent zero basic: " + p7.getSalary());

        PermanentEmployee p8 = new PermanentEmployee(20000, 0, a4);
        p8.calculateSalary();
        System.out.println("T8 Permanent 0 years: " + p8.getSalary());

        ContractEmployee c1 = new ContractEmployee(40000, 'm', 18);
        c1.calculateSalary();
        System.out.println("T9 Contract above 50000: " + c1.getSalary());

        ContractEmployee c2 = new ContractEmployee(20000, 'J', 6);
        c2.calculateSalary();
        System.out.println("T10 Contract short term: " + c2.getSalary());

        ContractEmployee c3 = new ContractEmployee(25000, 'S', 12);
        c3.calculateSalary();
        System.out.println("T11 Contract exactly 12 months: " + c3.getSalary());

        ContractEmployee c4 = new ContractEmployee(30000, 'S', 6);
        c4.calculateSalary();
        System.out.println("T12 Contract salary exactly 50000: " + c4.getSalary());

        ContractEmployee c5 = new ContractEmployee(30000, 'X', 6);
        c5.calculateSalary();
        System.out.println("T13 Contract unknown grade: " + c5.getSalary());

        ContractEmployee c6 = new ContractEmployee(20000, 'J', 37);
        c6.calculateSalary();
        System.out.println("T14 Contract 37 months: " + c6.getSalary());

        ContractEmployee c7 = new ContractEmployee(20000, 'J', 36);
        c7.calculateSalary();
        System.out.println("T15 Contract 36 months: " + c7.getSalary());

        ContractEmployee c8 = new ContractEmployee(20000, 'J', 0);
        c8.calculateSalary();
        System.out.println("T16 Contract 0 months: " + c8.getSalary());
    }
}
