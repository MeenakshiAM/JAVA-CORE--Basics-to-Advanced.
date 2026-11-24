

I've prepared 20 sneaky Java MCQs, gradually increasing in difficulty. These cover:

* `this` vs `super`

* Inheritance and constructor execution

* Field hiding vs method overriding

* `protected` access and packages

* Encapsulation and access modifiers

* Runtime polymorphism and reference types

* Static members, method overloading and overriding

* Object references and tricky output predictions

No interfaces. No abstract classes. No abstraction concepts.

Rules: Assume each code snippet is independent. Where packages are mentioned, assume separate source files and correct imports. All code is otherwise valid unless the question asks about compilation.

I've kept the answer key at the bottom so you can attempt them first. Don't peek! 😈

### Java inheritance & OOP: 20 tricky MCQs

### Level 1 — Warm-up, but watch carefully

Q1. What is the output?

```java
class Parent {
    int x = 10;
}

class Child extends Parent {
    int x = 20;

    void show() {
        System.out.println(this.x);
        System.out.println(super.x);
    }
}

class Main {
    public static void main(String[] args) {
        new Child().show();
    }
}
```

* A. `10 20`

* B. `20 10`

* C. `20 20`

* D. Compilation error

Q2. Predict the output.

```java
class A {
    A() {
        System.out.print("A ");
    }
}

class B extends A {
    B() {
        System.out.print("B ");
    }
}

class C extends B {
    C() {
        System.out.print("C ");
    }
}

class Main {
    public static void main(String[] args) {
        new C();
    }
}
```

* A. `C B A`

* B. `A B C`

* C. `A C B`

* D. `B A C`

Q3. What happens?

```java
class Parent {
    protected int value = 50;
}

class Child extends Parent {
    void show() {
        int value = 100;
        System.out.println(value);
        System.out.println(this.value);
        System.out.println(super.value);
    }
}
```

* A. `50 50 50`

* B. `100 100 50`

* C. `100 50 50`

* D. Compilation error

Q4. What is the output?

```java
class Parent {
    void display() {
        System.out.println("Parent");
    }
}

class Child extends Parent {
    void display() {
        System.out.println("Child");
    }
}

class Main {
    public static void main(String[] args) {
        Parent obj = new Child();
        obj.display();
    }
}
```

* A. Parent

* B. Child

* C. Compilation error

* D. Runtime error

Q5. What is the output?

```java
class Parent {
    int x = 10;

    void show() {
        System.out.println(x);
    }
}

class Child extends Parent {
    int x = 20;

    void display() {
        show();
    }
}

class Main {
    public static void main(String[] args) {
        new Child().display();
    }
}
```

* A. 10

* B. 20

* C. 0

* D. Compilation error

### Level 2 — The compiler is watching you

Q6. Predict the output.

```java
class Parent {
    Parent() {
        show();
    }

    void show() {
        System.out.println("Parent");
    }
}

class Child extends Parent {
    int x = 100;

    void show() {
        System.out.println(x);
    }
}

class Main {
    public static void main(String[] args) {
        new Child();
    }
}
```

* A. Parent

* B. 100

* C. 0

* D. Compilation error

Q7. What happens?

```java
class Parent {
    private int x = 10;
}

class Child extends Parent {
    void show() {
        System.out.println(x);
    }
}
```

* A. Prints 10

* B. Prints 0

* C. Compilation error

* D. Runtime error

Q8. What is the output?

```java
class Parent {
    static void show() {
        System.out.println("Parent");
    }
}

class Child extends Parent {
    static void show() {
        System.out.println("Child");
    }
}

class Main {
    public static void main(String[] args) {
        Parent obj = new Child();
        obj.show();
    }
}
```

* A. Parent

* B. Child

* C. Compilation error

* D. Runtime error

Q9. What happens?

```java
class Parent {
    protected int x = 10;
}

class Child extends Parent {
    void show(Parent p, Child c) {
        System.out.println(this.x);
        System.out.println(c.x);
        System.out.println(p.x);
    }
}
```

Assume `Parent` is in package `A`, and `Child` is in package `B`.

* A. All three print 10

* B. First two work, third causes compilation error

* C. Only `this.x` works

* D. All cause compilation errors

Q10. What is the output?

```java
class Parent {
    Parent(int x) {
        System.out.print("P ");
    }
}

class Child extends Parent {
    Child() {
        this(10);
        System.out.print("C ");
    }

    Child(int x) {
        super(x);
        System.out.print("D ");
    }
}

class Main {
    public static void main(String[] args) {
        new Child();
    }
}
```

* A. `P C D`

* B. `P D C`

* C. `D P C`

* D. Compilation error

### Level 3 — Sneaky combinations

Q11. Predict the output.

```java
class A {
    int x = 10;

    void show() {
        System.out.println(x);
    }
}

class B extends A {
    int x = 20;

    void show() {
        System.out.println(x);
    }
}

class Main {
    public static void main(String[] args) {
        A obj = new B();
        System.out.println(obj.x);
        obj.show();
    }
}
```

* A. `10 10`

* B. `20 20`

* C. `10 20`

* D. `20 10`

Q12. What happens?

```java
class Parent {
    public void display() {}
}

class Child extends Parent {
    protected void display() {}
}
```

* A. Valid overriding

* B. Compilation error because visibility is reduced

* C. Compilation error because return type is missing

* D. Runtime error

Q13. Predict the output.

```java
class A {
    A() {
        System.out.print("A ");
    }
}

class B extends A {
    B() {
        System.out.print("B ");
    }

    B(int x) {
        System.out.print("C ");
    }
}

class Main {
    public static void main(String[] args) {
        new B(5);
    }
}
```

* A. `A C`

* B. `A B C`

* C. `C`

* D. Compilation error

Q14. What is the output?

```java
class Parent {
    int x = 10;

    Parent() {
        System.out.print(x + " ");
        show();
    }

    void show() {
        System.out.print("Parent ");
    }
}

class Child extends Parent {
    int x = 20;

    Child() {
        System.out.print(x + " ");
    }

    void show() {
        System.out.print(x + " ");
    }
}

class Main {
    public static void main(String[] args) {
        new Child();
    }
}
```

* A. `10 Parent 20`

* B. `10 0 20`

* C. `20 20 20`

* D. `10 20 20`

Q15. Which statement is correct?

```java
class Parent {
    void show() {}
}

class Child extends Parent {
    void show(int x) {}
}
```

* A. Child overrides `show()`

* B. Child overloads `show()`

* C. Child hides `show()`

* D. Compilation error

### Level 4 — Interview-level traps

Q16. What is the output?

```java
class A {
    int x = 10;

    void display() {
        System.out.println(this.x);
    }
}

class B extends A {
    int x = 30;

    void display() {
        System.out.println(super.x);
    }
}

class Main {
    public static void main(String[] args) {
        A obj = new B();
        obj.display();
    }
}
```

* A. 10

* B. 30

* C. Compilation error

* D. 0

Q17. What happens?

```java
class Parent {
    protected int x = 10;
}

class Child extends Parent {
    void show() {
        Parent p = new Parent();
        System.out.println(p.x);
    }
}
```

Assume both classes are in different packages, and the code is inside `Child`.

* A. Prints 10

* B. Compilation error

* C. Runtime error

* D. Prints 0

Q18. Predict the output.

```java
class Parent {
    void show() {
        System.out.println("Parent");
    }
}

class Child extends Parent {
    void show() {
        super.show();
        System.out.println("Child");
    }
}

class Main {
    public static void main(String[] args) {
        Parent p = new Child();
        p.show();
    }
}
```

* A. Parent

* B. Child

* C. Parent followed by Child

* D. Compilation error

Q19. What is the output?

```java
class Parent {
    int x = 10;

    void show() {
        System.out.println("Parent " + x);
    }
}

class Child extends Parent {
    int x = 20;

    void show() {
        System.out.println("Child " + x);
    }
}

class Main {
    public static void main(String[] args) {
        Child c = new Child();
        Parent p = c;

        c.x = 50;
        p.x = 70;

        c.show();
        p.show();
        System.out.println(c.x + " " + p.x);
    }
}
```

* A. `Child 70`, `Child 70`, `70 70`

* B. `Child 50`, `Child 70`, `50 70`

* C. `Child 50`, `Child 70`, `50 70` — but both references share the same field

* D. `Child 50`, `Parent 70`, `50 70`

Q20. Final boss. Predict the output.

```java
class A {
    int x = 10;

    A() {
        print();
    }

    void print() {
        System.out.println("A " + x);
    }
}

class B extends A {
    int x = 20;

    B() {
        print();
    }

    void print() {
        System.out.println("B " + x);
    }
}

class Main {
    public static void main(String[] args) {
        A obj = new B();
        System.out.println(obj.x);
        obj.print();
    }
}
```

* A. `A 10`, `B 20`, `10`, `B 20`

* B. `B 0`, `B 20`, `10`, `B 20`

* C. `B 0`, `B 20`, `20`, `B 20`

* D. `A 10`, `A 10`, `10`, `A 10`

### Answer key

Spoiler zone — attempt all 20 first!

Answer key

20 explanations

Hide answer key

Q1

B

this.x resolves to Child.x; super.x selects Parent.x.

Q2

B

Constructors execute from the topmost superclass down to the child.

Q3

C

The local variable wins for the bare name; this and super both resolve to the inherited field here.

Q4

B

Overridden instance methods are selected at runtime based on the actual object.

Q5

A

Parent.show() accesses Parent's own x. Field hiding doesn't change that field reference.

Q6

C

Parent constructor calls the overridden Child.show(), before Child's field initializer runs. x is still 0.

Q7

C

Private members are not directly accessible in Child.

Q8

A

Static method calls are resolved using the reference's declared type, not runtime polymorphism.

Q9

B

Cross-package subclass access permits this and Child-typed references, not Parent-typed references.

Q10

B

Child() calls this(10); Child(int) calls super(x), then prints D. Finally Child() prints C.

Q11

C

Fields are resolved by reference type: obj.x is A.x. Methods dispatch to B.show().

Q12

B

An override cannot reduce visibility from public to protected.

Q13

A

B(int) implicitly calls super() and does not call B().

Q14

B

Parent initializes first and calls overridden Child.show() while Child.x is still 0; then Child initializes x to 20 and prints it.

Q15

B

Different parameter lists mean overloading, not overriding.

Q16

A

B.display() explicitly prints super.x, which is A.x = 10.

Q17

B

The Parent-typed reference is not permitted for protected access from a cross-package subclass.

Q18

C

Runtime dispatch enters Child.show(); super.show() explicitly invokes Parent.show().

Q19

D

Parent.x and Child.x are separate fields. p.x changes Parent.x; c.x changes Child.x. Both method calls dispatch to Child.show(), which reads Child.x = 50.

Q20

B

Parent constructor dispatches to B.print() before B.x is initialized, so it prints 0. Later B.x becomes 20. obj.x accesses A.x, while obj.print() dispatches to B.print().

