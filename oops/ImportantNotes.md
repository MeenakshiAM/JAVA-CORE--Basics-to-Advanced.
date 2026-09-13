# Important Notes - Polymorphism

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

----
### 1. @Override does NOT cause overriding

This is subtle and important.

You have:
```
@Override
void displayDetails() {
 ....
}
```
@Override is basically a compiler check.

It tells Java:

"I believe this method is overriding an inherited method. Please verify that for me."


### 2. Overriding has access-level rules

This one is commonly overlooked.

Suppose parent has:

```
class Employee {
public void displayDetails() {
}
}

```

You cannot do:
```
class Developer extends Employee {
@Override
private void displayDetails() {   // ERROR
}
}
```
You cannot reduce visibility.

Think:
```
Parent:     public
Child:      public       ✓
Child:      protected    ✗
Child:      private      ✗
```


Similarly:
```
Parent:     protected
Child:      protected    ✓
Child:      public       ✓
Child:      private      ✗
```

You can make access more permissive, not more restrictive.

### 3. private methods are NOT overridden

This is a big one.

Imagine:
```
class Employee {

    private void secret() {
        System.out.println("Employee secret");
    }
}
```
Then:
```
class Developer extends Employee {

    void secret() {
        System.out.println("Developer secret");
    }
}
```
It might look like overriding.

It isn't.

The parent's private method isn't inherited by Developer in the normal sense, so the child method is a completely separate method.

Therefore:

private method
↓
not overridden

This becomes important when you study inheritance more deeply.

### 4. static methods are NOT overridden either

Another classic trap.
```
class Employee {

    static void print() {
        System.out.println("Employee");
    }
}
class Developer extends Employee {

    static void print() {
        System.out.println("Developer");
    }
}
```
This is method hiding, not overriding.

So don't apply your runtime-polymorphism rule blindly to static methods.

The rule you're learning applies to instance methods.

### 5. Constructors cannot be overridden

This one sounds obvious once you think about it, but people still say things like "constructor overriding."

Impossible.
```
class Employee {
Employee() {}
}

class Developer extends Employee {
Developer() {}
}
```
_**Developer() does not override Employee().**_

_Constructors aren't inherited._

They participate in constructor chaining through:
`
super();
`
but not overriding.

So:

Methods       → can override
Constructors  → cannot override

### 6. Return type has one more subtle rule

You correctly wrote:

Return type isn't part of the method signature for overloading.

Exactly.

But with overriding, Java allows something called a covariant return type.

For example:
```
class Employee {

    Employee getEmployee() {
        return this;
    }
}
```
A subclass can do:

```class Developer extends Employee {

    @Override
    Developer getEmployee() {
        return this;
    }
}
```

That's valid because Developer is an Employee.

You don't need to deeply study this right now, but put a small note in your notebook:

Overloading:
return type alone cannot distinguish methods.

Overriding:
same return type OR compatible covariant return type.
### 7. super.method() bypasses the overridden version

This one is very important.

Suppose:
```
class Employee {

    void displayDetails() {
        System.out.println("Employee");
    }
}
```
and:
```
class Developer extends Employee {

    @Override
    void displayDetails() {
        System.out.println("Developer");
    }
}
```
Normally:
```
Developer d = new Developer();
d.displayDetails();
```
prints:

Developer

But inside Developer:

`super.displayDetails();`

means:

"Call the parent implementation."

So:
```
@Override
void displayDetails() {

    super.displayDetails();

    System.out.println("Developer");
}
```


prints:
`
Employee
Developer
`
This is the connection between overriding and super.

### 8. Overloading is resolved at compile time

You already understood this, but I'd make it more precise.

Suppose:
```
class Tester {

    void displayDetails() {
    }

    void displayDetails(String x) {
    }
}
```
When you write:

`t.displayDetails("hello");`

Java chooses the overloaded method during compilation based primarily on the compile-time types/signature information.

That's why your experiment:

`Employee e2 = new Tester(...);`

`e2.displayDetails("hello");`

failed.

The compiler doesn't say:

"Let me look inside the actual Tester object and see what extra methods it has."

It first asks:

"What can an Employee reference legally call?"

That's the key.

### 9. But overridden method selection happens at runtime

This is the other half.

Employee e2 = new Tester(...);

e2.displayDetails();

Compilation says:
```
Employee has displayDetails()
↓
LEGAL

Then runtime says:

Actual object = Tester
↓
Tester overrides displayDetails()
↓
call Tester.displayDetails()
```

```
Overloading
↓
compile-time

Overriding
↓
runtime
```


## 10. A subclass reference can access subclass-specific methods

You should add this contrast explicitly.
`
Employee e = new Tester(...);
`
You cannot:

`e.testSoftware();
`
But:

`Tester t = new Tester(...);
t.testSoftware();
`
works.

And this is why this is important:

`Employee e = new Tester(...);`

doesn't mean:

"The object magically became an Employee."

The object is still a Tester.

You've simply chosen to view/reference it through the Employee type.

That's a really useful mental model.

## 11. You can cast back — but don't start using casting as a substitute for polymorphism

For example:

`Employee e = new Tester(...);`

You could write:

`Tester t = (Tester) e;
t.testSoftware();
`
Now it works.

But this introduces downcasting, which we'll study separately.

For now, remember:

`Employee e = new Tester();`

upcasting → generally safe

`Tester t = (Tester) e;`

downcasting → must actually be a Tester


Otherwise you can get:

``` text
ClassCastException
```

This is also where your earlier instanceof discussion becomes useful.

### 12. instanceof checks the actual object

This connects directly to what you were asking earlier.
```
Employee e = new Tester(...);

System.out.println(e instanceof Employee); // true
System.out.println(e instanceof Tester);   // true
System.out.println(e instanceof Developer); // false
```
Why?

Because:

reference type → Employee
actual object  → Tester

The object is a Tester, and because Tester extends Employee, it is also an Employee.

This will become important when we study casting.

### 13. One BIG rule: not every method call is polymorphic

Don't accidentally generalize:

"Java always looks at the actual object."

No.

For ordinary overridden instance methods, yes.

But remember:
```
instance methods      → runtime overriding
static methods        → method hiding
private methods       → not overridden
constructors          → not overridden
fields                → not polymorphic
```
That last one — fields — is especially interesting.

For example:
```
class Employee {
    String name = "Employee";
}

class Developer extends Employee {
    String name = "Developer";
}
```
Then:
```
Employee e = new Developer();

System.out.println(e.name);
```
does not behave like an overridden method.

Fields are resolved based on the reference type.

That's a very common interview trap.
