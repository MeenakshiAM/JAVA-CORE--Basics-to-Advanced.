/*
* Problem 1 — Synchronized Method

Bank Account Concurrent Withdrawal

Create a Java program that simulates multiple users withdrawing money from the same bank account simultaneously.

*The account initially has ₹10,000.
*Create at least 3 threads, each representing a user trying to withdraw a different amount.
*Each withdrawal should first check whether sufficient balance exists and then deduct the amount.
*Make sure that two threads cannot modify the balance at the same time.
*Print which user successfully withdrew money and the remaining balance.
*If insufficient balance exists, print an appropriate message.
*At the end, print the final balance.

-> Requirement: Solve the problem using a synchronized method.
* */
class InsufficientBalance extends Exception {
    InsufficientBalance() {
        super("Insufficient balance");
    }
}
/*
* this is how we declare  the custom exception class inside the same java source file.
* with one condition that it must not be declared with public keyword ...
* */

class Bank {
    int balance = 10000;

    public synchronized void decrement (int amount) throws InsufficientBalance{

            if(amount > balance){
                throw new InsufficientBalance();
            }

            balance = balance - amount;

            System.out.println(
                    Thread.currentThread().getName()
                            + " withdrew ₹"
                            + amount
                            + ". Remaining balance: ₹"
                            + balance
            );

    }
}
class NewThread implements Runnable{
    Bank  bank ;
    int amount;

    NewThread (Bank bank, int amount) {
        this.bank = bank;
        this.amount = amount;
    }

    @Override
    public void run(){
        try{
            bank.decrement(this.amount);
        } catch (InsufficientBalance e) {
            System.out.println("Insufficient balance");
        }
    }
}
class BankAccount {
    public static void main(String[] args) throws InterruptedException {
        Bank bank = new Bank();
        NewThread task1 = new NewThread(bank,2000);
        NewThread task2= new NewThread(bank,7000);
        NewThread task3 = new NewThread(bank,3000);

        Thread t1 = new Thread(task1, "worker1");
        Thread t2 = new Thread(task2, "worker2");
        Thread t3 =  new Thread(task3, "worker3");

        t1.start();
        t2.start();
        t3.start();

        t1.join();
        t2.join();
        t3.join();

        System.out.println(
                "Final balance = ₹" + bank.balance
        );
    }
}

/* ---------------output -------------
PS E:\E\CORE-JAVA\experiments> java BankAccount
worker3 withdrew ?3000. Remaining balance: ?7000
worker2 withdrew ?7000. Remaining balance: ?0
Exception in thread "worker1" java.lang.RuntimeException: InsufficientBalance: Insufficient balance
        at NewThread.run(BankAccount.java:59)
        at java.base/java.lang.Thread.run(Thread.java:1583)
Caused by: InsufficientBalance: Insufficient balance
        at Bank.decrement(BankAccount.java:30)
        at NewThread.run(BankAccount.java:57)
        ... 1 more
PS E:\E\CORE-JAVA\experiments> javac BankAccount.java
PS E:\E\CORE-JAVA\experiments> java BankAccount
worker2 withdrew ?7000. Remaining balance: ?3000
worker3 withdrew ?3000. Remaining balance: ?0
Insufficient balance
Final balance = ?0
PS E:\E\CORE-JAVA\experiments>

 */

/*
------------------Explanations------------

Let's look at your two runs.

### Run 1

```text
worker3 → ₹3000  → balance ₹7000
worker2 → ₹7000  → balance ₹0
worker1 → FAIL
```

So:

```text
10000
  ↓ worker3 takes 3000
  ↓
7000
  ↓ worker2 takes 7000
  ↓
0
  ↓ worker1 asks for 2000
  ↓
insufficient
```

### Run 2

```text
worker2 → ₹7000 → balance ₹3000
worker3 → ₹3000 → balance ₹0
worker1 → FAIL
```

Different order:

```text
10000
  ↓ worker2 takes 7000
  ↓
3000
  ↓ worker3 takes 3000
  ↓
0
  ↓ worker1 asks for 2000
  ↓
 insufficient
```

And both are **correct executions**.

---

## 🚨 But here's the REALLY important distinction

You just discovered two different concepts:

### 1. Thread scheduling is unpredictable

The JVM/OS decides which runnable thread gets CPU time.

So you **cannot assume**:

```text
worker1
   ↓
worker2
   ↓
worker3
```

just because you called:

```java
t1.start();
t2.start();
t3.start();
```

The order in which you call `start()` is **not a guaranteed execution order**.

---

### 2. Synchronization gives you safety, NOT ordering

Your:

```java
public synchronized void decrement(...)
```

does **not** mean:

```text
worker1 gets lock
       ↓
worker2 gets lock
       ↓
worker3 gets lock
```

It means:

```text
              ┌── worker1 ──┐
              │             │
worker1 ──────┤             │
worker2 ──────┤ 🔒 BANK     │
worker3 ──────┤             │
              │             │
              └─────────────┘
                    ↑
             only ONE at a time
```

Who gets the lock first is not something you should generally rely on.

So:

> **Synchronization controls simultaneous access, not execution order.**

🔥 **That distinction is extremely important for everything you're eventually going toward.**

---

## And look at something else you accidentally demonstrated

Your exception:

```text
Exception in thread "worker1"
```

is telling you **which thread encountered the failure**.

That's because each thread independently executes:

```java
bank.decrement(amount);
```

and if that thread doesn't properly handle the exception, that exception terminates **that thread**.

It doesn't kill the other workers.

That's another fundamental property of multithreading:

```text
MAIN
 │
 ├──── worker1 ❌ exception → dies
 │
 ├──── worker2 ✅ completes
 │
 └──── worker3 ✅ completes
```

The other threads can continue.

---

### 🎯 And now we're ready for the fun part

You've seen:

```text
Thread creation
      ↓
Multiple threads
      ↓
Shared state
      ↓
Race-condition possibility
      ↓
synchronized method
      ↓
Mutual exclusion
      ↓
Scheduling ≠ ordering
```

Now I want you to do **one experiment before we touch synchronized blocks**:

> **Remove `synchronized` from `decrement()` and run the program many times.**

But don't just look for a weird final balance.

**Look specifically at this sequence:**

```java
if (amount > balance)
    throw ...

balance = balance - amount;
```

 */