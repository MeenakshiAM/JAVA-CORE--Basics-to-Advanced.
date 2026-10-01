# Important Notes - Abstract class 


## 1. What is an abstract class?

An **abstract class** is a class designed to act as a **base/foundation for other classes**.

It is declared using:

```
abstract class Employee {
}
```

The most important property:

> **You cannot directly create an object of an abstract class.**

```
Employee e = new Employee(); // ❌
```

But a subclass can be instantiated:

```
Developer d = new Developer(); // ✓
```

if `Developer extends Employee`.

---

# 2. Why do we need an abstract class?

Consider:

```
              Employee
             /        \
            /          \
      Developer        Tester
```

`Developer` **IS-A** `Employee`.

`Tester` **IS-A** `Employee`.

So inheritance makes sense.

But suppose our application only deals with **specific employee roles**.

We don't want someone to create:

```
new Employee();
```

because `Employee` is being used only as the **general foundation** for Developer, Tester, Manager, etc.

So we can make it:

```
abstract class Employee
```

Now:

```
new Employee();    // ❌
new Developer();   // ✓
new Tester();      // ✓
```

### Important!

**Inheritance itself does NOT require creating an Employee object first.**

You already correctly noticed this.

```java
class Developer extends Employee {
}
```

allows:

```java
Developer d = new Developer();
```

There is no need to separately do:

```java
Employee e = new Employee();
```

The purpose of `abstract` is to **prevent direct instantiation of the parent**, not to make inheritance possible.

---

# 3. Can an abstract class contain implemented methods?

## YES! Absolutely.

This is probably your biggest doubt.

An abstract class can contain **normal, fully implemented methods**.

For example:

```java
abstract class Employee {

    void displayDetails() {
        System.out.println("Displaying employee details");
    }
}
```

This is completely valid.

A `Developer` inherits that implementation:

```java
class Developer extends Employee {
}
```

So:

```java
Developer d = new Developer();
d.displayDetails();
```

works.

Therefore:

> **Abstract class ≠ class where everything is abstract.**

---

# 4. Can an abstract class contain abstract methods?

## YES.

For example:

```
abstract class Employee {

    abstract void work();
}
```

An abstract method has **no implementation/body**.

It basically says:

> "Every concrete subclass must provide its own implementation of `work()`."

Then:

```
class Developer extends Employee {

    @Override
    void work() {
        System.out.println("Developer writes software");
    }
}
```

and:

```
class Tester extends Employee {

    @Override
    void work() {
        System.out.println("Tester tests software");
    }
}
```

---

# 5. An abstract class can contain BOTH

This is the important mental model:

```java
abstract class Employee {

    // implemented method
    void displayDetails() {
        System.out.println("Common employee details");
    }

    // abstract method
    abstract void work();
}
```

So:

```text
                Employee
           abstract class
                 |
       ┌─────────┴─────────┐
       |                   |
 implemented            abstract
   method                method
       |                   |
displayDetails()         work()
       |                   |
 already defined      child MUST define
```

That's why abstract classes are powerful.

They can provide:

### Common implementation

> "All employees can use this."

AND

### Required behavior

> "Every concrete employee must define this differently."

---

# 6. What happens if the child doesn't implement an abstract method?

Suppose:

```java
abstract class Employee {

    abstract void work();
}
```

Then:

```java
class Developer extends Employee {
}
```

`Developer` has failed to provide `work()`.

So Java says essentially:

> Developer hasn't fulfilled the contract required by Employee.

Therefore, `Developer` must either:

1. implement `work()`, **or**
2. itself be declared `abstract`.

For example:

```java
abstract class Developer extends Employee {
}
```

Now Developer is also incomplete, so it doesn't have to implement `work()` yet.

---

# 7. Abstract class vs normal class

### Normal class

```java
class Employee {
}
```

You can do:

```java
Employee e = new Employee(); // ✓
```

### Abstract class

```java
abstract class Employee {
}
```

You cannot:

```java
Employee e = new Employee(); // ❌
```

But inheritance still works:

```java
class Developer extends Employee {
}
```

```java
Developer d = new Developer(); // ✓
```

---

# 8. Your "risk" idea

You said something very important:

> "So this can be at risk... even the parent class itself... to avoid that we're using abstract?"

**Yes — with one refinement.**

It's not that ordinary inheritance is inherently dangerous.

The design situation is:

```text
Employee
   ↓
Developer
Tester
Manager
```

The parent `Employee` is useful as a **common abstraction**, but your design may not want `Employee` itself to represent a concrete object.

A normal class permits:

```java
new Employee();
```

An abstract class prevents that.

So:

> **Abstract class can enforce that the parent is used as a foundation rather than as a directly instantiated object.**

And abstract methods can additionally enforce:

> **Concrete subclasses must provide certain behavior.**

---

# 9. The two purposes — remember this

When you see:

```java
abstract class Employee
```

think:

### Purpose 1 — Prevent direct instantiation

```java
new Employee(); // ❌
```

### Purpose 2 — Allow incomplete/common design

The class can contain:

```java
// common implementation
void displayDetails() { ... }

// required child-specific behavior
abstract void work();
```

So the mental model is:

```text
              ABSTRACT CLASS
                    |
          ┌─────────┴──────────┐
          ↓                    ↓
   common implementation    required behavior
          |                    |
   "you can reuse this"   "you must implement this"
```

---

# 10. Abstract class is NOT the same as "everything is abstract"

This is the misconception I especially want you to avoid.

❌ Wrong:

> "Abstract class means its methods cannot have implementations."

✅ Correct:

> **An abstract class may contain normal implemented methods, abstract methods, fields, constructors, static members, etc.**

The special restriction is that:

> **You cannot directly instantiate the abstract class.**

And an abstract method specifically means:

> **The method is declared but its implementation is left to a concrete subclass.**

---

## One-line definition for your notes

> **An abstract class is a non-instantiable base class that can provide common state/implemented behavior while also declaring abstract methods that concrete subclasses must implement.**

And your inheritance connection:

> **Inheritance allows a subclass such as `Developer` to reuse/extend `Employee`; it does not require an `Employee` object to be created first. Making `Employee` abstract simply prevents direct creation of an `Employee` object when the design intends it to be only a common foundation.**

T