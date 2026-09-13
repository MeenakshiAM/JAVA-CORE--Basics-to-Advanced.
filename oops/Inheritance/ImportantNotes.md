# Important Notes - Inheritance 

---

-> Remember : the overridin will only work if the method signature and no. of parameters are same else it will become over loading

-> the return type is not part of the method signature used for overloading. You cannot overload a method merely by changing its return type.

-> Reference type determines what methods you are allowed to call. Actual object type determines which overridden implementation runs.

ie,
say u have a parent class Employee, with methods:
```
---- the parent class ----

Employee methods
↓
displayDetails()
calculateAnnualSalary()
giveRaise()
isHighEarner()
```
this parent class has a subclasses :
```declarative
Tester methods
    ↓
displayDetails()  -> overriden the method of employee class 
displayDetails(String) ->  Tester-specific overload
testSoftware()   ← Tester-specific method
```
now when we create a reference variable of employee to store the object of the subclass Tester ..
during the compile time the compiler can only have the access to the methods defined in the Employee class only ..
----> this inclue the OVERRIDEN METHODS BUT NOT THE OVERLOADED METHODS ..
Because the overriden methods are still in the employee class which is overriden but over loaded or any subclass specific methods are only belong to the parent class only ...

---


That's a beautiful example of the difference:

                Employee e2 = new Tester()
                         │
                         │
             reference type = Employee
                         │
             ┌───────────┴───────────┐
             │                       │
     overriding               overloading
     runtime decision         compile-time decision
             │                       │
     displayDetails()        displayDetails(String)
             │                       │
     actual object matters    reference type matters


