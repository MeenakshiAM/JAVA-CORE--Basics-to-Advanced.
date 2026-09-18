## Access Modifiers Notes 

You have two packages:

```text
one
 └── Parent

two
 ├── Child extends Parent
 └── AccessModifiers (main)
```

And the result is beautiful.

### 1. Inside `Child` — different package

```java
System.out.println(publicValue);       // ✓
System.out.println(protectedValue);    // ✓
System.out.println(defaultValue);      // ✗
System.out.println(privateValue);      // ✗
```

So:

| Member      | Child in different package |
| ----------- | -------------------------- |
| `public`    | ✓                          |
| `protected` | ✓                          |
| `default`   | ✗                          |
| `private`   | ✗                          |

**This is the reason `protected` exists.**

`Child` gets special access to `protectedValue` because it is actually a subclass.

---

### 2. Inside `main` — different package, NOT a subclass

You got:

```java
p.publicValue       // ✓
p.protectedValue    // ✗
p.defaultValue      // ✗
p.privateValue      // ✗
```

And importantly:

```java
c.publicValue       // ✓
c.protectedValue    // ✗
c.defaultValue      // ✗
c.privateValue      // ✗
```

This is the **sneaky part**.

You might have thought:

> "But `c` is a Child! Why can't I access `c.protectedValue`?"

Because **the code performing the access is in `AccessModifiers.main`, not in `Child`.**

The fact that the object happens to be a `Child` doesn't give `main` subclass privileges.

Think:

```text
package two

Child
 └── childTest()
       └── protectedValue ✓

AccessModifiers
 └── main()
       └── c.protectedValue ✗
```

`protected` grants the special cross-package access **to subclass code**, not to every piece of code holding a `Child` reference.

---

# And now the whole picture clicks

### Same package

```text
Parent package one
Child  package one
Main   package one

public      ✓ ✓ ✓
protected   ✓ ✓ ✓
default     ✓ ✓ ✓
private     ✓ ✗ ✗
```

### Different package

```text
Parent → package one

Child → package two
Main  → package two

                Child code    Main code
public              ✓             ✓
protected           ✓             ✗
default             ✗             ✗
private             ✗             ✗
```

That's the experiment.

And notice how this connects directly to your original question:

> "But isn't Child a class that inherits attributes and methods?"

**Yes.**

`Child` absolutely inherits the accessible parent members.

But Java distinguishes:

**inheritance relationship**

from

**who is allowed to directly access a member.**

`protected` basically says:

> "If you're my subclass, I'll give you special access even across a package boundary."

`default` says:

> "I don't care whether you're my subclass. If you're outside my package, you're out."

That's the distinction you were missing.

---
You're **very close**, bro — and your second sentence is the important insight. But there's one correction that will make your `protected` mental model properly solid.

You said:

> `Child` is a subclass, so if `Child` accesses `p.protectedValue`, it should be allowed.

**Not necessarily.** In a different package, this is where Java gets sneaky.

Suppose:

```java
// package one
public class Parent {
    protected int protectedValue = 20;
}
```

```java
// package two
public class Child extends Parent {

    void childTest() {
        Parent p = new Parent();

        System.out.println(p.protectedValue); // ❌
    }
}
```

This **does not compile**.

But:

```java
void childTest() {
    System.out.println(protectedValue);       // ✓
    System.out.println(this.protectedValue);  // ✓
}
```

works.

### Why?

Because across packages, `protected` access is tied to the **subclass relationship AND the object/reference being accessed**.

Inside `Child`, you're allowed to access the inherited protected member through the `Child` side:

```java
this.protectedValue
```

But Java doesn't let `Child` use its subclass privilege as a way to reach into an arbitrary `Parent` object:

```java
Parent p = new Parent();

p.protectedValue; // ❌
```

That's actually a fantastic security/design rule:

```text
Child's own inherited state
        ↓
    protected ✓

arbitrary Parent object's protected state
        ↓
    protected ✗
```

And your second thought was **exactly right**:

> "just because it is allowed, you cannot indirectly make any other non-subclass through Child."

YES.

A non-subclass doesn't become privileged merely because it has a `Child` object:

```java
Child c = new Child();

c.protectedValue; // ❌ from unrelated class in another package
```

So the final mental model is:

> **Same package:** `protected` behaves like package access.
> **Different package:** `protected` gives special access to subclass code, but that privilege isn't transferable to arbitrary code or arbitrary `Parent` objects.
