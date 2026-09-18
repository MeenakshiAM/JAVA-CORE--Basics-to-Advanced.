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

- Overriding does NOT apply to:
```
    constructors
    private methods
    static methods
```
- Fields are NOT polymorphic.

- @Override is only a compiler check.

- super.method() can explicitly invoke
- the parent implementation.

-Subclass-specific methods are unavailable
- through a parent reference unless you cast.

- Downcasting must match the actual object type.
- privateValue has private access in Parent
- super doesn't give you permission to access private members.private isn't "available to subclasses but not objects."

- It's stricter:

- Only code inside the declaring class can directly access it.
> **Same package:** `protected` behaves like package access.
> **Different package:** `protected` gives special access to subclass code, but that privilege isn't transferable to arbitrary code or arbitrary `Parent` objects.


### Access Modifiers — Quick Notes

- public → Accessible from anywhere if the class itself is accessible.
- protected → Accessible within the same package and by subclasses in other packages.
- default/package-private → Accessible only within the same package, including subclasses in that package.
- private → Accessible only inside the class that declares it.
- Inheritance and access control are separate concepts — inheriting from a class does not automatically bypass access restrictions.
- A subclass in the same package can access public, protected, and default members directly, but not private.
- A subclass in a different package can directly access public and protected, but not default or private.
- In a different package, protected access is granted to subclass code, not arbitrary code holding a subclass object.
super does not bypass private access.
- default is not a keyword; it means package-private access when no modifier is specified.