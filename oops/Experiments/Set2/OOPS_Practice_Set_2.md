# OOPS Practice Set 2: Base Class + Two Partial Subclasses

**Pattern:** one fully implemented parent class, two child classes where you fill in the `// To_Trainee` methods, and a Tester that runs many cases. Each question has its own **Expected output** block. Run the Tester, then compare line by line. A line that differs tells you exactly which rule you missed (the test labels in the Tester are descriptive on purpose).

Suggested time: 20 to 25 minutes per question. Don't open the answer key until you have tried.

---

## Step 0: Decode the wording for this pattern

| The question says | You write |
|---|---|
| "Invoke `calculateAdvanceAmount()` method of `BuildingForRent` class" | `super.calculateAdvanceAmount();` |
| "Set the `basicAmount` with `advanceAmount` of `BuildingForRent` class" | `float basicAmount = this.getAdvanceAmount();` (the parent method already stored its result in the field; read it through the getter) |
| "Invoke `validateNoOfBedrooms()` method" | `Boolean valid = this.validateNoOfBedrooms();` |
| "If either A is -1.0f or the method returns false or `facilities` is null, set the amount to -1.0f" | `if (basicAmount == -1.0f \|\| !valid \|\| this.facilities == null) { this.setAdvanceAmount(-1.0f); } else { ... }` |
| "set the `advanceAmount` to ..." | `this.setAdvanceAmount(value);` (the field is private in the parent) |
| "For every facility present in `facilities`, check if it is present in the first array of `facilitiesDetailsArr`; if present, identify the corresponding charge from the second array" | a loop over `facilities`, an inner loop over `arr[0]`, and the charge is `arr[1][ind]` with the **same** `ind`. Charges are Strings, so use `Float.parseFloat(arr[1][ind])` |
| "Perform case insensitive comparison" | `.equalsIgnoreCase(...)` for Strings; `Character.toUpperCase(ch)` for a `char` |
| "validate ... and return Boolean" | `return cond;` (or `return false;` early, `return true;` at the end) |
| "boundary values excluded / included" | excluded is `>` and `<`; included is `>=` and `<=` |
| "This method identifies and returns X using the table" | declare `int x = 0;` (already in the template), set it with `if / else if / else`, and let the last `else` give -1 |

**Rules of this pattern that cost marks:**
1. **No extra fields or methods** in the given classes. Local variables (including local arrays) are fine.
2. Read *every* "Note:" line. Some questions count repeated items every time, others only once. Some treat null as invalid, others as "none".
3. The order of the early `-1.0f` exit matters: validate (including nulls) first, so no loop ever touches a null array.
4. Percent of X is `X * percent / 100`. Multiply first, divide last.
5. A boundary mentioned in the wording ("both included", "more than", "or more") almost always has a test on it.

---

## Question 1: Parcel Delivery (duplicates, nulls, GST, strict boundary)

**Notes:**
 * Do not include any extra instance/static variables and instance/static methods in the given classes
 * Case in-sensitive comparison is required to be done unless until specified explicitly
 * Do not change any value or case of the given variables.
 * Read notes and examples for better understanding of the logic

**Implementation Details:**

| Class name | Implementation details |
|---|---|
| ParcelDelivery | Fully implemented |
| DomesticParcel | Partially implemented |
| InternationalParcel | Partially implemented |

**ParcelDelivery Class** (supplied)
 * `calculateDeliveryCharge()` sets the deliveryCharge (float) as `weightInGrams / 10.0f` if weightInGrams is greater than 0. Otherwise it sets deliveryCharge to -1.0f.

**DomesticParcel Class:**

addOnDetailsArr:
 * This is a static 2D-array (String[][]) which contains the array of addOnService (String[]) as its first element and array of addOnCharge (String[]) as its second element
 * The elements of addOnService have one to one correspondence with addOnCharge
 * The initial values of addOnDetailsArr are given below:

| addOnDetailsArr | {{"Insurance", "Packaging", "SMS-Alert", "Fragile-Tag"}, {"150", "80", "10", "120"}} |
|---|---|

Note:
 * The above 2D-array is supplied. Hence no need to code
 * Do not change the CASE of elements in the array

validatePincode():
 * This method validates the pincode (int) and returns Boolean
 * If the pincode is between 100000 and 999999 (both boundary values included), return true
 * Otherwise return false

Example: If pincode is 560001, this method should return true

calculateDeliveryCharge():
 * This method calculates and sets the deliveryCharge (float) of ParcelDelivery class
 * Invoke calculateDeliveryCharge() method of ParcelDelivery class
 * Set the basicCharge (float) with deliveryCharge of ParcelDelivery class
 * Invoke validatePincode() method of DomesticParcel class
 * If either basicCharge is equal to -1.0f or validatePincode() method returns false or addOnServices (String[]) is equal to null, then set the deliveryCharge to -1.0f
 * Otherwise,
   * For every addOnService (String) present in the addOnServices, check if addOnService is present as one of the elements in first array of addOnDetailsArr
   * If present, identify the corresponding addOnCharge (float) for the addOnService from second array of addOnDetailsArr
   * Add all the above identified addOnCharge to obtain the totalAddOnCharge
     Note: Perform case insensitive comparison
     Note: Each add-on service must be charged only once, even if it is present more than once in addOnServices. Some elements of addOnServices can be null; ignore them
   * If totalAddOnCharge is not equal to 0.0f,
     * Add totalAddOnCharge to basicCharge and increase the sum by 5% (GST). Set the result as the deliveryCharge
   * Otherwise, set the deliveryCharge to basicCharge (no GST)

For Example: If weightInGrams is 2500, pincode is 560001 and addOnServices is {"insurance", "PACKAGING", "Insurance", null, "Gift-Wrap"}, then basicCharge is 250.0, totalAddOnCharge is 230.0 and the deliveryCharge will be 504.0 currency

**InternationalParcel Class:**

identifyZoneSurcharge():
 * This method identifies and returns the zoneSurcharge (int) based on the zone (char) using the table below:

| zone | zone_surcharge (in Rs.) |
|---|---|
| 'A' | 1200 |
| 'B' | 1800 |
| 'C' | 2500 |
| Others | -1 |

Note: Perform case insensitive comparison

For example: If the zone is 'B' then zoneSurcharge would be 1800 currency

calculateDeliveryCharge():
 * This method calculates and sets the deliveryCharge (float) of ParcelDelivery class
 * Invoke calculateDeliveryCharge() method of ParcelDelivery class
 * Set the basicCharge (float) with deliveryCharge of ParcelDelivery class
 * Invoke identifyZoneSurcharge() method to get the zoneSurcharge (int)
 * If either basicCharge is -1.0f or zoneSurcharge is -1, then set the deliveryCharge to -1.0f
 * Otherwise,
   * Obtain the charge (float) as the sum of basicCharge and zoneSurcharge
   * If declaredValue (int) is more than 50000, add customs duty to the charge. The customs duty is 2% of declaredValue
   * Set the deliveryCharge as the charge

For Example: If weightInGrams is 4000, zone is 'b' and declaredValue is 75000, then deliveryCharge would be 3700.0 currency

**Code in Java**
```java
import java.util.Arrays;

class ParcelDelivery {
    private int weightInGrams;
    private float deliveryCharge;

    public ParcelDelivery(int weightInGrams) {
        this.weightInGrams = weightInGrams;
    }

    public float getDeliveryCharge() {
        return this.deliveryCharge;
    }

    public void setDeliveryCharge(float deliveryCharge) {
        this.deliveryCharge = deliveryCharge;
    }

    public int getWeightInGrams() {
        return this.weightInGrams;
    }

    public void calculateDeliveryCharge() {
        float deliveryCharge = -1.0f;
        if (this.weightInGrams > 0) {
            deliveryCharge = this.weightInGrams / 10.0f;
        }
        this.deliveryCharge = deliveryCharge;
    }

    @Override
    public String toString() {
        return "ParcelDelivery (weightInGrams=" + this.weightInGrams + ")";
    }
}

class DomesticParcel extends ParcelDelivery {
    private static String[][] addOnDetailsArr = {{"Insurance", "Packaging", "SMS-Alert", "Fragile-Tag"}, {"150", "80", "10", "120"}};
    private String[] addOnServices;
    private int pincode;

    public DomesticParcel(int weightInGrams, int pincode, String[] addOnServices) {
        super(weightInGrams);
        this.pincode = pincode;
        this.addOnServices = addOnServices;
    }

    public int getPincode() {
        return this.pincode;
    }

    public String[] getAddOnServices() {
        return this.addOnServices;
    }

    // To_Trainee
    public Boolean validatePincode() {
        // write your code here

        // change return statement accordingly
        return null;
    }

    // To_Trainee
    @Override
    public void calculateDeliveryCharge() {
        // write your code here
    }

    @Override
    public String toString() {
        return "DomesticParcel (ParcelDelivery (weightInGrams=" + this.getWeightInGrams()
                + ") addOnServices=" + Arrays.toString(this.addOnServices)
                + ", pincode=" + this.pincode + ")";
    }
}

class InternationalParcel extends ParcelDelivery {
    private char zone;
    private int declaredValue;

    public InternationalParcel(int weightInGrams, char zone, int declaredValue) {
        super(weightInGrams);
        this.zone = zone;
        this.declaredValue = declaredValue;
    }

    // To_Trainee
    public int identifyZoneSurcharge() {
        int zoneSurcharge = 0;
        // write your code here
        return zoneSurcharge;
    }

    // To_Trainee
    @Override
    public void calculateDeliveryCharge() {
        // write your code here
    }

    @Override
    public String toString() {
        return "InternationalParcel (ParcelDelivery (weightInGrams=" + this.getWeightInGrams()
                + ") zone=" + this.zone + ", declaredValue=" + this.declaredValue + ")";
    }
}

class Tester {
    public static void main(String[] args) {
        String[] s1 = {"insurance", "PACKAGING", "Insurance", null, "Gift-Wrap"};
        String[] s2 = {"Gift-Wrap", "Wrapping"};
        String[] s3 = {"sms-alert", "Fragile-Tag"};

        DomesticParcel d1 = new DomesticParcel(2500, 560001, s1);
        d1.calculateDeliveryCharge();
        System.out.println("T1 Domestic mixed add-ons: " + d1.getDeliveryCharge());

        DomesticParcel d2 = new DomesticParcel(2500, 560001, s2);
        d2.calculateDeliveryCharge();
        System.out.println("T2 Domestic unknown add-ons only: " + d2.getDeliveryCharge());

        DomesticParcel d3 = new DomesticParcel(2500, 12345, s1);
        d3.calculateDeliveryCharge();
        System.out.println("T3 Domestic short pincode: " + d3.getDeliveryCharge());

        DomesticParcel d4 = new DomesticParcel(2500, 560001, null);
        d4.calculateDeliveryCharge();
        System.out.println("T4 Domestic null add-ons: " + d4.getDeliveryCharge());

        DomesticParcel d5 = new DomesticParcel(0, 560001, s1);
        d5.calculateDeliveryCharge();
        System.out.println("T5 Domestic zero weight: " + d5.getDeliveryCharge());

        DomesticParcel d6 = new DomesticParcel(1000, 999999, s3);
        d6.calculateDeliveryCharge();
        System.out.println("T6 Domestic highest pincode: " + d6.getDeliveryCharge());

        DomesticParcel d7 = new DomesticParcel(1000, 1000000, s3);
        d7.calculateDeliveryCharge();
        System.out.println("T7 Domestic 7-digit pincode: " + d7.getDeliveryCharge());

        InternationalParcel i1 = new InternationalParcel(4000, 'b', 75000);
        i1.calculateDeliveryCharge();
        System.out.println("T8 International high value: " + i1.getDeliveryCharge());

        InternationalParcel i2 = new InternationalParcel(4000, 'B', 50000);
        i2.calculateDeliveryCharge();
        System.out.println("T9 International value exactly 50000: " + i2.getDeliveryCharge());

        InternationalParcel i3 = new InternationalParcel(4000, 'D', 75000);
        i3.calculateDeliveryCharge();
        System.out.println("T10 International unknown zone: " + i3.getDeliveryCharge());

        InternationalParcel i4 = new InternationalParcel(-5, 'A', 1000);
        i4.calculateDeliveryCharge();
        System.out.println("T11 International negative weight: " + i4.getDeliveryCharge());

        InternationalParcel i5 = new InternationalParcel(2000, 'a', 10000);
        i5.calculateDeliveryCharge();
        System.out.println("T12 International lowercase zone: " + i5.getDeliveryCharge());
    }
}
```

**Expected output of the Tester (when your code is correct)**
```
T1 Domestic mixed add-ons: 504.0
T2 Domestic unknown add-ons only: 250.0
T3 Domestic short pincode: -1.0
T4 Domestic null add-ons: -1.0
T5 Domestic zero weight: -1.0
T6 Domestic highest pincode: 241.5
T7 Domestic 7-digit pincode: -1.0
T8 International high value: 3700.0
T9 International value exactly 50000: 2200.0
T10 International unknown zone: -1.0
T11 International negative weight: -1.0
T12 International lowercase zone: 1400.0
```

---

## Question 2: Employee Salary (percent of different bases, repeated items, tax on the excess)

**Notes:**
 * Do not include any extra instance/static variables and instance/static methods in the given classes
 * Case in-sensitive comparison is required to be done unless until specified explicitly
 * Do not change any value or case of the given variables.
 * Read notes and examples for better understanding of the logic

**Implementation Details:**

| Class name | Implementation details |
|---|---|
| Employee | Fully implemented |
| PermanentEmployee | Partially implemented |
| ContractEmployee | Partially implemented |

**Employee Class** (supplied)
 * `calculateSalary()` sets the salary (float) as the sum of basicPay and 40% of basicPay (house rent allowance) if basicPay is greater than 0. Otherwise it sets salary to -1.0f.
 * The private basicPay can be read with `getBasicPay()`.

**PermanentEmployee Class:**

allowanceDetailsArr:
 * This is a static 2D-array (String[][]) which contains the array of allowance (String[]) as its first element and array of allowancePercent (String[]) as its second element
 * The elements of allowance have one to one correspondence with allowancePercent. The percentage is to be taken on the basicPay
 * The initial values of allowanceDetailsArr are given below:

| allowanceDetailsArr | {{"Medical", "Travel", "Internet", "Food"}, {"5", "3", "2", "4"}} |
|---|---|

Note:
 * The above 2D-array is supplied. Hence no need to code
 * Do not change the CASE of elements in the array

validateYearsOfService():
 * This method validates the yearsOfService (int) and returns Boolean
 * If the yearsOfService is between 0 and 40 (both boundary values included), return true
 * Otherwise return false

Example: If yearsOfService is 12, this method should return true

calculateSalary():
 * This method calculates and sets the salary (float) of Employee class
 * Invoke calculateSalary() method of Employee class
 * Set the grossSalary (float) with salary of Employee class
 * Invoke validateYearsOfService() method of PermanentEmployee class
 * If either grossSalary is equal to -1.0f or validateYearsOfService() method returns false or allowances (String[]) is equal to null, then set the salary to -1.0f
 * Otherwise,
   * For every allowance (String) present in the allowances, check if allowance is present as one of the elements in first array of allowanceDetailsArr
   * If present, identify the corresponding allowancePercent (float) for the allowance from second array of allowanceDetailsArr
   * For each identified allowance, the allowance amount is allowancePercent of basicPay. Add all the allowance amounts to obtain the totalAllowance
     Note: Perform case insensitive comparison
     Note: If an allowance is present more than once in allowances, every occurrence is counted. Some elements of allowances can be null; ignore them
   * If totalAllowance is not equal to 0.0f,
     * Obtain the netSalary (float) as the sum of grossSalary and totalAllowance
     * If yearsOfService is 10 or more, add a loyalty bonus of 5% of grossSalary to the netSalary
     * Deduct provident fund of 12% of basicPay from the netSalary
     * Set the salary as the netSalary
   * Otherwise, set the salary to -1.0f

For Example: If basicPay is 30000, yearsOfService is 12 and allowances is {"medical", "TRAVEL", "Gym"}, then grossSalary is 42000.0, totalAllowance is 2400.0 and the salary will be 42900.0 currency

**ContractEmployee Class:**

identifyMonthlyAllowance():
 * This method identifies and returns the monthlyAllowance (int) based on the grade (char) using the table below:

| grade | monthly_allowance (in Rs.) |
|---|---|
| 'J' | 3000 |
| 'M' | 5000 |
| 'S' | 8000 |
| Others | -1 |

Note: Perform case insensitive comparison

For example: If the grade is 'M' then monthlyAllowance would be 5000 currency

calculateSalary():
 * This method calculates and sets the salary (float) of Employee class
 * Invoke calculateSalary() method of Employee class
 * Set the grossSalary (float) with salary of Employee class
 * Invoke identifyMonthlyAllowance() method to get the monthlyAllowance (int)
 * If either grossSalary is -1.0f or monthlyAllowance is -1 or contractMonths (int) is not between 1 and 36 (both boundary values included), then set the salary to -1.0f
 * Otherwise,
   * Obtain the salary (float) as the sum of grossSalary and monthlyAllowance
   * If contractMonths is 12 or more, add a completion bonus of 10% of grossSalary to the salary
   * If the salary is more than 50000, deduct tax (TDS). The tax is 10% of the amount by which the salary exceeds 50000
   * Set the salary of Employee class

For Example: If basicPay is 40000, grade is 'm' and contractMonths is 18, then the salary would be 64940.0 currency (grossSalary 56000.0, plus 5000, plus bonus 5600 gives 66600.0, tax is 1660.0)

**Code in Java**
```java
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

        // change return statement accordingly
        return null;
    }

    // To_Trainee
    @Override
    public void calculateSalary() {
        // write your code here
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
        int monthlyAllowance = 0;
        // write your code here
        return monthlyAllowance;
    }

    // To_Trainee
    @Override
    public void calculateSalary() {
        // write your code here
    }

    @Override
    public String toString() {
        return "ContractEmployee (Employee (basicPay=" + this.getBasicPay()
                + ") grade=" + this.grade + ", contractMonths=" + this.contractMonths + ")";
    }
}

class Tester {
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
```

**Expected output of the Tester (when your code is correct)**
```
T1 Permanent 12 years: 42900.0
T2 Permanent 5 years: 40800.0
T3 Permanent no valid allowance: -1.0
T4 Permanent 41 years: -1.0
T5 Permanent null allowances: -1.0
T6 Permanent exactly 10 years, repeated allowance: 28600.0
T7 Permanent zero basic: -1.0
T8 Permanent 0 years: 26000.0
T9 Contract above 50000: 64940.0
T10 Contract short term: 31000.0
T11 Contract exactly 12 months: 46500.0
T12 Contract salary exactly 50000: 50000.0
T13 Contract unknown grade: -1.0
T14 Contract 37 months: -1.0
T15 Contract 36 months: 33800.0
T16 Contract 0 months: -1.0
```

---

## Question 3: Online Orders (parallel arrays, slabs, array validation)

**Notes:**
 * Do not include any extra instance/static variables and instance/static methods in the given classes
 * Case in-sensitive comparison is required to be done unless until specified explicitly
 * Do not change any value or case of the given variables.
 * Read notes and examples for better understanding of the logic

**Implementation Details:**

| Class name | Implementation details |
|---|---|
| OnlineOrder | Fully implemented |
| GroceryOrder | Partially implemented |
| ElectronicsOrder | Partially implemented |

**OnlineOrder Class** (supplied)
 * `calculateTotalAmount()` sets the totalAmount (float) to the delivery charge: -1.0f if deliveryDistanceKm is 0 or less; 0.0f if it is 5 or less; otherwise 10 currency for every km beyond 5.

**GroceryOrder Class:**

priceDetailsArr:
 * This is a static 2D-array (String[][]) which contains the array of item (String[]) as its first element and array of pricePerUnit (String[]) as its second element
 * The elements of item have one to one correspondence with pricePerUnit
 * The initial values of priceDetailsArr are given below:

| priceDetailsArr | {{"Rice", "Milk", "Eggs", "Bread", "Oil"}, {"60", "25", "6", "40", "150"}} |
|---|---|

Note:
 * The above 2D-array is supplied. Hence no need to code
 * Do not change the CASE of elements in the array
 * The array items (String[]) and the array quantities (int[]) have one to one correspondence, i.e. quantities[0] is the quantity of items[0]

validateOrder():
 * This method validates the order and returns Boolean
 * Return false if items is null or quantities is null
 * Return false if the length of items is not equal to the length of quantities
 * Return false if the number of items is not between 1 and 5 (both boundary values included)
 * Return false if any of the quantities is 0 or less
 * Otherwise return true

Example: If items is {"Rice", "Milk"} and quantities is {5, 8}, this method should return true

calculateTotalAmount():
 * This method calculates and sets the totalAmount (float) of OnlineOrder class
 * Invoke calculateTotalAmount() method of OnlineOrder class
 * Set the deliveryCharge (float) with totalAmount of OnlineOrder class
 * Invoke validateOrder() method of GroceryOrder class
 * If either deliveryCharge is equal to -1.0f or validateOrder() method returns false, then set the totalAmount to -1.0f
 * Otherwise,
   * For every item (String) present in the items, check if item is present as one of the elements in first array of priceDetailsArr
   * If present, identify the corresponding pricePerUnit (float) for the item from second array of priceDetailsArr and multiply it with the quantity of that item
   * Add all the above amounts to obtain the itemsTotal
     Note: Perform case insensitive comparison
     Note: An item that is not present in priceDetailsArr is ignored. Some elements of items can be null; ignore them
   * If itemsTotal is equal to 0.0f, then set the totalAmount to -1.0f
   * Otherwise,
     * Identify the discount (float) using the table below:

| itemsTotal | discount |
|---|---|
| 1000 or more | 10% of itemsTotal |
| 500 or more (but less than 1000) | 5% of itemsTotal |
| Less than 500 | 0 |

     * The totalAmount is calculated and set as itemsTotal minus discount, plus deliveryCharge

For Example: If deliveryDistanceKm is 12, items is {"rice", "EGGS", "Milk", "Soap"} and quantities is {5, 10, 4, 2}, then itemsTotal is 460.0, deliveryCharge is 70.0 and the totalAmount will be 530.0 currency

**ElectronicsOrder Class:**

validateWarrantyYears():
 * This method validates the warrantyYears (int) and returns Boolean
 * If the warrantyYears is between 1 and 5 (both boundary values included), return true
 * Otherwise return false

identifyGstPercent():
 * This method identifies and returns the gstPercent (int) based on the category (char) using the table below:

| category | gst_percent |
|---|---|
| 'M' (mobile) | 12 |
| 'L' (laptop) | 18 |
| 'A' (accessory) | 5 |
| Others | -1 |

Note: Perform case insensitive comparison

For example: If the category is 'L' then gstPercent would be 18

calculateTotalAmount():
 * This method calculates and sets the totalAmount (float) of OnlineOrder class
 * Invoke calculateTotalAmount() method of OnlineOrder class
 * Set the deliveryCharge (float) with totalAmount of OnlineOrder class
 * Invoke identifyGstPercent() method to get the gstPercent (int)
 * Invoke validateWarrantyYears() method of ElectronicsOrder class
 * If either deliveryCharge is -1.0f or gstPercent is -1 or validateWarrantyYears() returns false or productPrice (int) is 0 or less, then set the totalAmount to -1.0f
 * Otherwise,
   * Identify the warrantyCharge (float) using the table below:

| warrantyYears | warrantyCharge |
|---|---|
| 1 | 0 |
| 2 | 5% of productPrice |
| 3 | 8% of productPrice |
| 4 or 5 | 12% of productPrice |

   * Obtain the taxableAmount (float) as the sum of productPrice and warrantyCharge
   * Identify the gstAmount (float) as gstPercent of taxableAmount
   * The totalAmount is calculated and set as the sum of taxableAmount, gstAmount and deliveryCharge

For Example: If deliveryDistanceKm is 4, productPrice is 40000, category is 'l' and warrantyYears is 3, then warrantyCharge is 3200.0, gstAmount is 7776.0 and the totalAmount will be 50976.0 currency

**Code in Java**
```java
import java.util.Arrays;

class OnlineOrder {
    private int deliveryDistanceKm;
    private float totalAmount;

    public OnlineOrder(int deliveryDistanceKm) {
        this.deliveryDistanceKm = deliveryDistanceKm;
    }

    public int getDeliveryDistanceKm() {
        return this.deliveryDistanceKm;
    }

    public float getTotalAmount() {
        return this.totalAmount;
    }

    public void setTotalAmount(float totalAmount) {
        this.totalAmount = totalAmount;
    }

    public void calculateTotalAmount() {
        float totalAmount = -1.0f;
        if (this.deliveryDistanceKm > 0) {
            if (this.deliveryDistanceKm <= 5) {
                totalAmount = 0.0f;
            } else {
                totalAmount = (this.deliveryDistanceKm - 5) * 10;
            }
        }
        this.totalAmount = totalAmount;
    }

    @Override
    public String toString() {
        return "OnlineOrder (deliveryDistanceKm=" + this.deliveryDistanceKm + ")";
    }
}

class GroceryOrder extends OnlineOrder {
    private static String[][] priceDetailsArr = {{"Rice", "Milk", "Eggs", "Bread", "Oil"}, {"60", "25", "6", "40", "150"}};
    private String[] items;
    private int[] quantities;

    public GroceryOrder(int deliveryDistanceKm, String[] items, int[] quantities) {
        super(deliveryDistanceKm);
        this.items = items;
        this.quantities = quantities;
    }

    public String[] getItems() {
        return this.items;
    }

    public int[] getQuantities() {
        return this.quantities;
    }

    // To_Trainee
    public Boolean validateOrder() {
        // write your code here

        // change return statement accordingly
        return null;
    }

    // To_Trainee
    @Override
    public void calculateTotalAmount() {
        // write your code here
    }

    @Override
    public String toString() {
        return "GroceryOrder (OnlineOrder (deliveryDistanceKm=" + this.getDeliveryDistanceKm()
                + ") items=" + Arrays.toString(this.items)
                + ", quantities=" + Arrays.toString(this.quantities) + ")";
    }
}

class ElectronicsOrder extends OnlineOrder {
    private int productPrice;
    private int warrantyYears;
    private char category;

    public ElectronicsOrder(int deliveryDistanceKm, int productPrice, char category, int warrantyYears) {
        super(deliveryDistanceKm);
        this.productPrice = productPrice;
        this.category = category;
        this.warrantyYears = warrantyYears;
    }

    // To_Trainee
    public Boolean validateWarrantyYears() {
        // write your code here

        // change return statement accordingly
        return null;
    }

    // To_Trainee
    public int identifyGstPercent() {
        int gstPercent = 0;
        // write your code here
        return gstPercent;
    }

    // To_Trainee
    @Override
    public void calculateTotalAmount() {
        // write your code here
    }

    @Override
    public String toString() {
        return "ElectronicsOrder (OnlineOrder (deliveryDistanceKm=" + this.getDeliveryDistanceKm()
                + ") productPrice=" + this.productPrice + ", category=" + this.category
                + ", warrantyYears=" + this.warrantyYears + ")";
    }
}

class Tester {
    public static void main(String[] args) {
        String[] i1 = {"rice", "EGGS", "Milk", "Soap"};
        int[] q1 = {5, 10, 4, 2};
        GroceryOrder g1 = new GroceryOrder(12, i1, q1);
        g1.calculateTotalAmount();
        System.out.println("T1 Grocery below 500: " + g1.getTotalAmount());

        String[] i2 = {"Rice", "Oil", "Bread"};
        int[] q2 = {6, 4, 10};
        GroceryOrder g2 = new GroceryOrder(3, i2, q2);
        g2.calculateTotalAmount();
        System.out.println("T2 Grocery above 1000: " + g2.getTotalAmount());

        String[] i3 = {"Rice", "Milk"};
        int[] q3 = {5, 8};
        GroceryOrder g3 = new GroceryOrder(5, i3, q3);
        g3.calculateTotalAmount();
        System.out.println("T3 Grocery items total exactly 500: " + g3.getTotalAmount());

        String[] i4 = {"Oil", "Bread"};
        int[] q4 = {4, 10};
        GroceryOrder g4 = new GroceryOrder(10, i4, q4);
        g4.calculateTotalAmount();
        System.out.println("T4 Grocery items total exactly 1000: " + g4.getTotalAmount());

        GroceryOrder g5 = new GroceryOrder(10, i2, new int[]{1, 2});
        g5.calculateTotalAmount();
        System.out.println("T5 Grocery length mismatch: " + g5.getTotalAmount());

        GroceryOrder g6 = new GroceryOrder(10, i2, new int[]{1, 0, 3});
        g6.calculateTotalAmount();
        System.out.println("T6 Grocery zero quantity: " + g6.getTotalAmount());

        String[] i7 = {"Rice", "Milk", "Eggs", "Bread", "Oil", "Rice"};
        int[] q7 = {1, 1, 1, 1, 1, 1};
        GroceryOrder g7 = new GroceryOrder(10, i7, q7);
        g7.calculateTotalAmount();
        System.out.println("T7 Grocery six items: " + g7.getTotalAmount());

        String[] i8 = {"Soap", "Pen"};
        int[] q8 = {1, 1};
        GroceryOrder g8 = new GroceryOrder(10, i8, q8);
        g8.calculateTotalAmount();
        System.out.println("T8 Grocery all unknown items: " + g8.getTotalAmount());

        GroceryOrder g9 = new GroceryOrder(10, null, q8);
        g9.calculateTotalAmount();
        System.out.println("T9 Grocery null items: " + g9.getTotalAmount());

        GroceryOrder g10 = new GroceryOrder(0, i3, q3);
        g10.calculateTotalAmount();
        System.out.println("T10 Grocery zero distance: " + g10.getTotalAmount());

        String[] i11 = {"Rice", null};
        int[] q11 = {2, 1};
        GroceryOrder g11 = new GroceryOrder(6, i11, q11);
        g11.calculateTotalAmount();
        System.out.println("T11 Grocery null element: " + g11.getTotalAmount());

        ElectronicsOrder e1 = new ElectronicsOrder(4, 40000, 'l', 3);
        e1.calculateTotalAmount();
        System.out.println("T12 Electronics laptop 3 years: " + e1.getTotalAmount());

        ElectronicsOrder e2 = new ElectronicsOrder(9, 15000, 'M', 2);
        e2.calculateTotalAmount();
        System.out.println("T13 Electronics mobile 2 years: " + e2.getTotalAmount());

        ElectronicsOrder e3 = new ElectronicsOrder(5, 2000, 'A', 1);
        e3.calculateTotalAmount();
        System.out.println("T14 Electronics accessory 1 year: " + e3.getTotalAmount());

        ElectronicsOrder e4 = new ElectronicsOrder(5, 2000, 'Z', 1);
        e4.calculateTotalAmount();
        System.out.println("T15 Electronics unknown category: " + e4.getTotalAmount());

        ElectronicsOrder e5 = new ElectronicsOrder(5, 2000, 'A', 6);
        e5.calculateTotalAmount();
        System.out.println("T16 Electronics 6 years: " + e5.getTotalAmount());

        ElectronicsOrder e6 = new ElectronicsOrder(5, 2000, 'A', 0);
        e6.calculateTotalAmount();
        System.out.println("T17 Electronics 0 years: " + e6.getTotalAmount());

        ElectronicsOrder e7 = new ElectronicsOrder(5, 0, 'A', 2);
        e7.calculateTotalAmount();
        System.out.println("T18 Electronics zero price: " + e7.getTotalAmount());

        ElectronicsOrder e8 = new ElectronicsOrder(5, 60000, 'L', 5);
        e8.calculateTotalAmount();
        System.out.println("T19 Electronics laptop 5 years: " + e8.getTotalAmount());
    }
}
```

**Expected output of the Tester (when your code is correct)**
```
T1 Grocery below 500: 530.0
T2 Grocery above 1000: 1224.0
T3 Grocery items total exactly 500: 475.0
T4 Grocery items total exactly 1000: 950.0
T5 Grocery length mismatch: -1.0
T6 Grocery zero quantity: -1.0
T7 Grocery six items: -1.0
T8 Grocery all unknown items: -1.0
T9 Grocery null items: -1.0
T10 Grocery zero distance: -1.0
T11 Grocery null element: 130.0
T12 Electronics laptop 3 years: 50976.0
T13 Electronics mobile 2 years: 17680.0
T14 Electronics accessory 1 year: 2100.0
T15 Electronics unknown category: -1.0
T16 Electronics 6 years: -1.0
T17 Electronics 0 years: -1.0
T18 Electronics zero price: -1.0
T19 Electronics laptop 5 years: 79296.0
```

---

## Question 4: Student Fees (string ID validation, parsing, slabs, caps)

**Notes:**
 * Do not include any extra instance/static variables and instance/static methods in the given classes
 * Case in-sensitive comparison is required to be done unless until specified explicitly
 * Do not change any value or case of the given variables.
 * Read notes and examples for better understanding of the logic

**Implementation Details:**

| Class name | Implementation details |
|---|---|
| StudentFee | Fully implemented |
| HostelStudent | Partially implemented |
| DayScholar | Partially implemented |

**StudentFee Class** (supplied)
 * `calculateTotalFee()` sets the totalFee (float) as the sum of tuitionFee and an admission fee of 1000 if tuitionFee is greater than 0. Otherwise it sets totalFee to -1.0f.

**HostelStudent Class:**

roomDetailsArr:
 * This is a static 2D-array (String[][]) which contains the array of roomType (String[]) as its first element and array of yearlyRent (String[]) as its second element
 * The elements of roomType have one to one correspondence with yearlyRent
 * The initial values of roomDetailsArr are given below:

| roomDetailsArr | {{"Single", "Double", "Triple"}, {"60000", "42000", "30000"}} |
|---|---|

Note:
 * The above 2D-array is supplied. Hence no need to code
 * Do not change the CASE of elements in the array

validateStudentId():
 * This method validates the studentId (String) and returns Boolean
 * Return false if studentId is null
 * Return false if the length of studentId is not 7
 * Return false if the first two characters of studentId are not "HS" (Perform case insensitive comparison)
 * Return false if any of the remaining five characters of studentId is not a digit
 * Otherwise return true

Example: If studentId is "hs10234", this method should return true. If studentId is "HS25A10", it should return false

calculateTotalFee():
 * This method calculates and sets the totalFee (float) of StudentFee class
 * Invoke calculateTotalFee() method of StudentFee class
 * Set the basicFee (float) with totalFee of StudentFee class
 * Invoke validateStudentId() method of HostelStudent class
 * If either basicFee is equal to -1.0f or validateStudentId() method returns false or mealsPerDay (int) is not between 1 and 3 (both boundary values included), then set the totalFee to -1.0f
 * Otherwise,
   * Check if roomType (String) is present as one of the elements in first array of roomDetailsArr
     Note: Perform case insensitive comparison
   * If present, identify the corresponding yearlyRent as roomRent (float) from second array of roomDetailsArr. Otherwise set the totalFee to -1.0f
   * If roomType is present,
     * Obtain the admissionNo (int) from the five digits at the end of studentId
     * If admissionNo is less than 20000, a concession of 10% is given on roomRent (only on roomRent), i.e. reduce the roomRent by 10% of roomRent
     * Identify the mealsCharge (int) as the product of mealsPerDay and 9000 currency
     * The totalFee is calculated and set as the sum of basicFee, roomRent and mealsCharge

For Example: If tuitionFee is 50000, studentId is "hs10234", roomType is "double" and mealsPerDay is 2, then basicFee is 51000.0, roomRent is 37800.0 (after concession) and the totalFee will be 106800.0 currency

**DayScholar Class:**

identifyTransportFee():
 * This method identifies and returns the transportFee (int) based on the distanceKm (int) using the table below:

| distanceKm | transport_fee (in Rs.) |
|---|---|
| 1 to 5 (both included) | 8000 |
| 6 to 15 (both included) | 14000 |
| 16 to 30 (both included) | 20000 |
| Others | -1 |

For example: If the distanceKm is 10 then transportFee would be 14000 currency

calculateTotalFee():
 * This method calculates and sets the totalFee (float) of StudentFee class
 * Invoke calculateTotalFee() method of StudentFee class
 * Set the basicFee (float) with totalFee of StudentFee class
 * Invoke identifyTransportFee() method to get the transportFee (int)
 * If either basicFee is -1.0f or transportFee is -1 or noOfSiblings (int) is less than 0, then set the totalFee to -1.0f
 * Otherwise,
   * Identify the concessionPercent (int) as 5 for every sibling (noOfSiblings multiplied by 5). The concessionPercent cannot be more than 15. If it is, set it to 15
   * Identify the concession (float) as concessionPercent of basicFee
   * The totalFee is calculated and set as the sum of basicFee and transportFee, minus concession

For Example: If tuitionFee is 40000, distanceKm is 10 and noOfSiblings is 2, then basicFee is 41000.0, concession is 4100.0 and the totalFee will be 50900.0 currency

**Code in Java**
```java
import java.util.Arrays;

class StudentFee {
    private int tuitionFee;
    private float totalFee;

    public StudentFee(int tuitionFee) {
        this.tuitionFee = tuitionFee;
    }

    public int getTuitionFee() {
        return this.tuitionFee;
    }

    public float getTotalFee() {
        return this.totalFee;
    }

    public void setTotalFee(float totalFee) {
        this.totalFee = totalFee;
    }

    public void calculateTotalFee() {
        float totalFee = -1.0f;
        if (this.tuitionFee > 0) {
            totalFee = this.tuitionFee + 1000;
        }
        this.totalFee = totalFee;
    }

    @Override
    public String toString() {
        return "StudentFee (tuitionFee=" + this.tuitionFee + ")";
    }
}

class HostelStudent extends StudentFee {
    private static String[][] roomDetailsArr = {{"Single", "Double", "Triple"}, {"60000", "42000", "30000"}};
    private String studentId;
    private String roomType;
    private int mealsPerDay;

    public HostelStudent(int tuitionFee, String studentId, String roomType, int mealsPerDay) {
        super(tuitionFee);
        this.studentId = studentId;
        this.roomType = roomType;
        this.mealsPerDay = mealsPerDay;
    }

    public String getStudentId() {
        return this.studentId;
    }

    public String getRoomType() {
        return this.roomType;
    }

    // To_Trainee
    public Boolean validateStudentId() {
        // write your code here

        // change return statement accordingly
        return null;
    }

    // To_Trainee
    @Override
    public void calculateTotalFee() {
        // write your code here
    }

    @Override
    public String toString() {
        return "HostelStudent (StudentFee (tuitionFee=" + this.getTuitionFee()
                + ") studentId=" + this.studentId + ", roomType=" + this.roomType
                + ", mealsPerDay=" + this.mealsPerDay + ")";
    }
}

class DayScholar extends StudentFee {
    private int distanceKm;
    private int noOfSiblings;

    public DayScholar(int tuitionFee, int distanceKm, int noOfSiblings) {
        super(tuitionFee);
        this.distanceKm = distanceKm;
        this.noOfSiblings = noOfSiblings;
    }

    // To_Trainee
    public int identifyTransportFee() {
        int transportFee = 0;
        // write your code here
        return transportFee;
    }

    // To_Trainee
    @Override
    public void calculateTotalFee() {
        // write your code here
    }

    @Override
    public String toString() {
        return "DayScholar (StudentFee (tuitionFee=" + this.getTuitionFee()
                + ") distanceKm=" + this.distanceKm + ", noOfSiblings=" + this.noOfSiblings + ")";
    }
}

class Tester {
    public static void main(String[] args) {
        HostelStudent h1 = new HostelStudent(50000, "hs10234", "double", 2);
        h1.calculateTotalFee();
        System.out.println("T1 Hostel early admission: " + h1.getTotalFee());

        HostelStudent h2 = new HostelStudent(50000, "HS25010", "Double", 2);
        h2.calculateTotalFee();
        System.out.println("T2 Hostel later admission: " + h2.getTotalFee());

        HostelStudent h3 = new HostelStudent(50000, "HS2501", "Double", 2);
        h3.calculateTotalFee();
        System.out.println("T3 Hostel 6-char id: " + h3.getTotalFee());

        HostelStudent h4 = new HostelStudent(50000, "HX25010", "Double", 2);
        h4.calculateTotalFee();
        System.out.println("T4 Hostel wrong prefix: " + h4.getTotalFee());

        HostelStudent h5 = new HostelStudent(50000, "HS25A10", "Double", 2);
        h5.calculateTotalFee();
        System.out.println("T5 Hostel letter in digits: " + h5.getTotalFee());

        HostelStudent h6 = new HostelStudent(50000, "HS25010", "Dorm", 2);
        h6.calculateTotalFee();
        System.out.println("T6 Hostel unknown room: " + h6.getTotalFee());

        HostelStudent h7 = new HostelStudent(50000, "HS25010", "Double", 4);
        h7.calculateTotalFee();
        System.out.println("T7 Hostel 4 meals: " + h7.getTotalFee());

        HostelStudent h8 = new HostelStudent(50000, "HS25010", "Double", 0);
        h8.calculateTotalFee();
        System.out.println("T8 Hostel 0 meals: " + h8.getTotalFee());

        HostelStudent h9 = new HostelStudent(30000, "HS20000", "single", 3);
        h9.calculateTotalFee();
        System.out.println("T9 Hostel admission number exactly 20000: " + h9.getTotalFee());

        HostelStudent h10 = new HostelStudent(30000, "hs19999", "single", 1);
        h10.calculateTotalFee();
        System.out.println("T10 Hostel admission number 19999: " + h10.getTotalFee());

        HostelStudent h11 = new HostelStudent(0, "HS10234", "single", 1);
        h11.calculateTotalFee();
        System.out.println("T11 Hostel zero tuition: " + h11.getTotalFee());

        HostelStudent h12 = new HostelStudent(50000, null, "single", 1);
        h12.calculateTotalFee();
        System.out.println("T12 Hostel null id: " + h12.getTotalFee());

        DayScholar d1 = new DayScholar(40000, 10, 2);
        d1.calculateTotalFee();
        System.out.println("T13 Day scholar 10 km, 2 siblings: " + d1.getTotalFee());

        DayScholar d2 = new DayScholar(40000, 10, 5);
        d2.calculateTotalFee();
        System.out.println("T14 Day scholar 5 siblings: " + d2.getTotalFee());

        DayScholar d3 = new DayScholar(40000, 5, 0);
        d3.calculateTotalFee();
        System.out.println("T15 Day scholar 5 km: " + d3.getTotalFee());

        DayScholar d4 = new DayScholar(40000, 6, 1);
        d4.calculateTotalFee();
        System.out.println("T16 Day scholar 6 km: " + d4.getTotalFee());

        DayScholar d5 = new DayScholar(40000, 16, 3);
        d5.calculateTotalFee();
        System.out.println("T17 Day scholar 16 km, 3 siblings: " + d5.getTotalFee());

        DayScholar d6 = new DayScholar(40000, 30, 0);
        d6.calculateTotalFee();
        System.out.println("T18 Day scholar 30 km: " + d6.getTotalFee());

        DayScholar d7 = new DayScholar(40000, 31, 0);
        d7.calculateTotalFee();
        System.out.println("T19 Day scholar 31 km: " + d7.getTotalFee());

        DayScholar d8 = new DayScholar(40000, 0, 0);
        d8.calculateTotalFee();
        System.out.println("T20 Day scholar 0 km: " + d8.getTotalFee());

        DayScholar d9 = new DayScholar(40000, 10, -1);
        d9.calculateTotalFee();
        System.out.println("T21 Day scholar negative siblings: " + d9.getTotalFee());
    }
}
```

**Expected output of the Tester (when your code is correct)**
```
T1 Hostel early admission: 106800.0
T2 Hostel later admission: 111000.0
T3 Hostel 6-char id: -1.0
T4 Hostel wrong prefix: -1.0
T5 Hostel letter in digits: -1.0
T6 Hostel unknown room: -1.0
T7 Hostel 4 meals: -1.0
T8 Hostel 0 meals: -1.0
T9 Hostel admission number exactly 20000: 118000.0
T10 Hostel admission number 19999: 94000.0
T11 Hostel zero tuition: -1.0
T12 Hostel null id: -1.0
T13 Day scholar 10 km, 2 siblings: 50900.0
T14 Day scholar 5 siblings: 48850.0
T15 Day scholar 5 km: 49000.0
T16 Day scholar 6 km: 52950.0
T17 Day scholar 16 km, 3 siblings: 54850.0
T18 Day scholar 30 km: 61000.0
T19 Day scholar 31 km: -1.0
T20 Day scholar 0 km: -1.0
T21 Day scholar negative siblings: -1.0
```

---

## Question 5: Insurance Premium (caps, null rules, depreciation then discount)

**Notes:**
 * Do not include any extra instance/static variables and instance/static methods in the given classes
 * Case in-sensitive comparison is required to be done unless until specified explicitly
 * Do not change any value or case of the given variables.
 * Read notes and examples for better understanding of the logic

**Implementation Details:**

| Class name | Implementation details |
|---|---|
| InsurancePolicy | Fully implemented |
| HealthPolicy | Partially implemented |
| VehiclePolicy | Partially implemented |

**InsurancePolicy Class** (supplied)
 * `calculatePremium()` sets the premium (float) as 2% of sumInsured if sumInsured is greater than 0. Otherwise it sets premium to -1.0f.
 * The private sumInsured can be read with `getSumInsured()`.

**HealthPolicy Class:**

conditionLoadingArr:
 * This is a static 2D-array (String[][]) which contains the array of condition (String[]) as its first element and array of loadingPercent (String[]) as its second element
 * The elements of condition have one to one correspondence with loadingPercent
 * The initial values of conditionLoadingArr are given below:

| conditionLoadingArr | {{"Diabetes", "Asthma", "Hypertension", "Thyroid"}, {"20", "10", "15", "5"}} |
|---|---|

Note:
 * The above 2D-array is supplied. Hence no need to code
 * Do not change the CASE of elements in the array

validateMembers():
 * This method validates the memberAges (int[]) and returns Boolean
 * Return false if memberAges is null
 * Return false if the number of members is not between 1 and 6 (both boundary values included)
 * Return false if the age of any member is not between 0 and 80 (both boundary values included)
 * Otherwise return true

Example: If memberAges is {30, 28, 5, 62}, this method should return true

calculatePremium():
 * This method calculates and sets the premium (float) of InsurancePolicy class
 * Invoke calculatePremium() method of InsurancePolicy class
 * Set the basicPremium (float) with premium of InsurancePolicy class
 * Invoke validateMembers() method of HealthPolicy class
 * If either basicPremium is equal to -1.0f or validateMembers() method returns false, then set the premium to -1.0f
 * Otherwise,
   * Identify the ageCharge (float) by adding the charge of every member, based on the age of the member using the table below:

| age of member | charge |
|---|---|
| 25 or less | 1000 |
| 26 to 45 (both included) | 2500 |
| 46 to 60 (both included) | 5000 |
| More than 60 | 9000 |

   * The conditions (String[]) can be null. In that case there is no loading, but the premium is still calculated. Some elements of conditions can be null; ignore them
   * For every condition (String) present in the conditions, check if condition is present as one of the elements in first array of conditionLoadingArr
   * If present, identify the corresponding loadingPercent (float) for the condition from second array of conditionLoadingArr
   * Add all the above identified loadingPercent to obtain the totalLoadingPercent
     Note: Perform case insensitive comparison
     Note: Each condition must be considered only once, even if it is present more than once in conditions
   * The totalLoadingPercent cannot be more than 30. If it is, set it to 30
   * Identify the loadingAmount (float) as totalLoadingPercent of basicPremium
   * Obtain the totalPremium (float) as the sum of basicPremium, ageCharge and loadingAmount
   * If the number of members is 4 or more, a family discount of 10% of totalPremium is given, i.e. reduce the totalPremium by 10%
   * Set the premium as the totalPremium

For Example: If sumInsured is 500000, memberAges is {30, 28, 5, 62} and conditions is {"diabetes", "Asthma", "DIABETES", null, "Allergy"}, then basicPremium is 10000.0, ageCharge is 15000.0, loadingAmount is 3000.0 and the premium will be 25200.0 currency

**VehiclePolicy Class:**

identifyOwnDamageRate():
 * This method identifies and returns the damageRate (int) based on the vehicleType (char) using the table below. The damageRate is a percentage of the sumInsured:

| vehicle_type | damage_rate |
|---|---|
| 'C' (car) | 3 |
| 'B' (bike) | 2 |
| 'T' (truck) | 5 |
| Others | -1 |

Note: Perform case insensitive comparison

For example: If the vehicleType is 'T' then damageRate would be 5

calculatePremium():
 * This method calculates and sets the premium (float) of InsurancePolicy class
 * Invoke calculatePremium() method of InsurancePolicy class
 * Set the thirdPartyPremium (float) with premium of InsurancePolicy class
 * Invoke identifyOwnDamageRate() method to get the damageRate (int)
 * If either thirdPartyPremium is -1.0f or damageRate is -1 or vehicleAge (int) is not between 0 and 15 (both boundary values included) or noClaimYears (int) is less than 0, then set the premium to -1.0f
 * Otherwise,
   * Identify the ownDamage (float) as damageRate percent of sumInsured
   * Depreciation: reduce the ownDamage by 20% of ownDamage if vehicleAge is between 6 and 10 (both included), or by 40% of ownDamage if vehicleAge is between 11 and 15 (both included). No reduction if vehicleAge is 5 or less
   * Identify the ncbPercent (int) as 10 for every noClaimYear (noClaimYears multiplied by 10). The ncbPercent cannot be more than 50. If it is, set it to 50
   * Reduce the ownDamage (the value obtained after depreciation) by ncbPercent of that ownDamage
   * The premium is calculated and set as the sum of thirdPartyPremium and ownDamage

For Example: If sumInsured is 600000, vehicleType is 'c', vehicleAge is 7 and noClaimYears is 3, then thirdPartyPremium is 12000.0, ownDamage is 18000.0 (14400.0 after depreciation, 10080.0 after no-claim discount) and the premium will be 22080.0 currency

**Code in Java**
```java
import java.util.Arrays;

class InsurancePolicy {
    private int sumInsured;
    private float premium;

    public InsurancePolicy(int sumInsured) {
        this.sumInsured = sumInsured;
    }

    public int getSumInsured() {
        return this.sumInsured;
    }

    public float getPremium() {
        return this.premium;
    }

    public void setPremium(float premium) {
        this.premium = premium;
    }

    public void calculatePremium() {
        float premium = -1.0f;
        if (this.sumInsured > 0) {
            premium = this.sumInsured * 2 / 100;
        }
        this.premium = premium;
    }

    @Override
    public String toString() {
        return "InsurancePolicy (sumInsured=" + this.sumInsured + ")";
    }
}

class HealthPolicy extends InsurancePolicy {
    private static String[][] conditionLoadingArr = {{"Diabetes", "Asthma", "Hypertension", "Thyroid"}, {"20", "10", "15", "5"}};
    private int[] memberAges;
    private String[] conditions;

    public HealthPolicy(int sumInsured, int[] memberAges, String[] conditions) {
        super(sumInsured);
        this.memberAges = memberAges;
        this.conditions = conditions;
    }

    public int[] getMemberAges() {
        return this.memberAges;
    }

    public String[] getConditions() {
        return this.conditions;
    }

    // To_Trainee
    public Boolean validateMembers() {
        // write your code here

        // change return statement accordingly
        return null;
    }

    // To_Trainee
    @Override
    public void calculatePremium() {
        // write your code here
    }

    @Override
    public String toString() {
        return "HealthPolicy (InsurancePolicy (sumInsured=" + this.getSumInsured()
                + ") memberAges=" + Arrays.toString(this.memberAges)
                + ", conditions=" + Arrays.toString(this.conditions) + ")";
    }
}

class VehiclePolicy extends InsurancePolicy {
    private char vehicleType;
    private int vehicleAge;
    private int noClaimYears;

    public VehiclePolicy(int sumInsured, char vehicleType, int vehicleAge, int noClaimYears) {
        super(sumInsured);
        this.vehicleType = vehicleType;
        this.vehicleAge = vehicleAge;
        this.noClaimYears = noClaimYears;
    }

    // To_Trainee
    public int identifyOwnDamageRate() {
        int damageRate = 0;
        // write your code here
        return damageRate;
    }

    // To_Trainee
    @Override
    public void calculatePremium() {
        // write your code here
    }

    @Override
    public String toString() {
        return "VehiclePolicy (InsurancePolicy (sumInsured=" + this.getSumInsured()
                + ") vehicleType=" + this.vehicleType + ", vehicleAge=" + this.vehicleAge
                + ", noClaimYears=" + this.noClaimYears + ")";
    }
}

class Tester {
    public static void main(String[] args) {
        int[] ages1 = {30, 28, 5, 62};
        String[] c1 = {"diabetes", "Asthma", "DIABETES", null, "Allergy"};
        HealthPolicy h1 = new HealthPolicy(500000, ages1, c1);
        h1.calculatePremium();
        System.out.println("T1 Health 4 members, repeated condition: " + h1.getPremium());

        HealthPolicy h2 = new HealthPolicy(500000, new int[]{40}, null);
        h2.calculatePremium();
        System.out.println("T2 Health 1 member, null conditions: " + h2.getPremium());

        String[] c3 = {"Diabetes", "Hypertension", "Asthma"};
        HealthPolicy h3 = new HealthPolicy(500000, new int[]{20, 50}, c3);
        h3.calculatePremium();
        System.out.println("T3 Health loading above cap: " + h3.getPremium());

        HealthPolicy h4 = new HealthPolicy(500000, new int[]{30, 81}, c3);
        h4.calculatePremium();
        System.out.println("T4 Health age 81: " + h4.getPremium());

        HealthPolicy h5 = new HealthPolicy(500000, new int[]{30, 30, 30, 30, 30, 30, 30}, c3);
        h5.calculatePremium();
        System.out.println("T5 Health 7 members: " + h5.getPremium());

        HealthPolicy h6 = new HealthPolicy(500000, new int[]{}, c3);
        h6.calculatePremium();
        System.out.println("T6 Health empty members: " + h6.getPremium());

        HealthPolicy h7 = new HealthPolicy(500000, null, c3);
        h7.calculatePremium();
        System.out.println("T7 Health null members: " + h7.getPremium());

        HealthPolicy h8 = new HealthPolicy(500000, new int[]{0, 80}, null);
        h8.calculatePremium();
        System.out.println("T8 Health ages 0 and 80: " + h8.getPremium());

        HealthPolicy h9 = new HealthPolicy(0, new int[]{30}, null);
        h9.calculatePremium();
        System.out.println("T9 Health zero sum insured: " + h9.getPremium());

        HealthPolicy h10 = new HealthPolicy(500000, new int[]{25, 26, 45, 46, 60, 61}, null);
        h10.calculatePremium();
        System.out.println("T10 Health 6 members, age slab edges: " + h10.getPremium());

        VehiclePolicy v1 = new VehiclePolicy(600000, 'c', 7, 3);
        v1.calculatePremium();
        System.out.println("T11 Vehicle car age 7: " + v1.getPremium());

        VehiclePolicy v2 = new VehiclePolicy(100000, 'b', 2, 7);
        v2.calculatePremium();
        System.out.println("T12 Vehicle bike, NCB capped: " + v2.getPremium());

        VehiclePolicy v3 = new VehiclePolicy(1000000, 't', 12, 0);
        v3.calculatePremium();
        System.out.println("T13 Vehicle truck age 12: " + v3.getPremium());

        VehiclePolicy v4 = new VehiclePolicy(600000, 'c', 16, 3);
        v4.calculatePremium();
        System.out.println("T14 Vehicle age 16: " + v4.getPremium());

        VehiclePolicy v5 = new VehiclePolicy(600000, 'X', 7, 3);
        v5.calculatePremium();
        System.out.println("T15 Vehicle unknown type: " + v5.getPremium());

        VehiclePolicy v6 = new VehiclePolicy(200000, 'c', 15, 1);
        v6.calculatePremium();
        System.out.println("T16 Vehicle age 15: " + v6.getPremium());

        VehiclePolicy v7 = new VehiclePolicy(100000, 'c', 5, 0);
        v7.calculatePremium();
        System.out.println("T17 Vehicle age 5: " + v7.getPremium());

        VehiclePolicy v8 = new VehiclePolicy(100000, 'c', 6, 0);
        v8.calculatePremium();
        System.out.println("T18 Vehicle age 6: " + v8.getPremium());

        VehiclePolicy v9 = new VehiclePolicy(100000, 'c', 11, 0);
        v9.calculatePremium();
        System.out.println("T19 Vehicle age 11: " + v9.getPremium());

        VehiclePolicy v10 = new VehiclePolicy(100000, 'c', 3, -1);
        v10.calculatePremium();
        System.out.println("T20 Vehicle negative no-claim years: " + v10.getPremium());

        VehiclePolicy v11 = new VehiclePolicy(0, 'c', 3, 1);
        v11.calculatePremium();
        System.out.println("T21 Vehicle zero sum insured: " + v11.getPremium());
    }
}
```

**Expected output of the Tester (when your code is correct)**
```
T1 Health 4 members, repeated condition: 25200.0
T2 Health 1 member, null conditions: 12500.0
T3 Health loading above cap: 19000.0
T4 Health age 81: -1.0
T5 Health 7 members: -1.0
T6 Health empty members: -1.0
T7 Health null members: -1.0
T8 Health ages 0 and 80: 20000.0
T9 Health zero sum insured: -1.0
T10 Health 6 members, age slab edges: 31500.0
T11 Vehicle car age 7: 22080.0
T12 Vehicle bike, NCB capped: 3000.0
T13 Vehicle truck age 12: 50000.0
T14 Vehicle age 16: -1.0
T15 Vehicle unknown type: -1.0
T16 Vehicle age 15: 7240.0
T17 Vehicle age 5: 5000.0
T18 Vehicle age 6: 4400.0
T19 Vehicle age 11: 3800.0
T20 Vehicle negative no-claim years: -1.0
T21 Vehicle zero sum insured: -1.0
```
