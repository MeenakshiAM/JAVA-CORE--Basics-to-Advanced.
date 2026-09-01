Yep 😎. Now we're at one of the **most important pieces of Java's traditional thread coordination model**:

# `wait()`, `notify()`, `notifyAll()`

And I want to make one distinction crystal clear before we touch code:

> **`sleep()` and `wait()` are NOT the same thing.**

You already know `sleep()`:

```java
Thread.sleep(2000);
```

means:

> "This thread pauses for roughly 2 seconds."

But `wait()` means something much more interesting:

> **"I can't continue right now. I'm going to release this object's monitor and wait until another thread signals me."**

That word **release** is the key.

---

## 1. Remember our monitor

We had:

```java
Object lock = new Object();
```

Conceptually:

```text
lock
 │
 ▼
┌────────────────┐
│ Object         │
│                │
│ Monitor 🔒     │
└────────────────┘
```

Suppose Worker-1 owns it:

```text
Worker-1
   │
   ▼
owns 🔒
   │
   ▼
synchronized(lock)
```

Now Worker-1 says:

```java
lock.wait();
```

Something important happens:

```text
Worker-1
   │
   ▼
calls wait()
   │
   ├──────────────→ releases 🔓 monitor
   │
   ▼
WAITING
```

So another thread can now acquire the monitor.

---

# 2. Why would we want this?

Imagine a producer/consumer situation.

```text
Producer → produces data
Consumer → needs data
```

But the consumer arrives first.

There is nothing to consume.

Instead of doing:

```java
while (nothingAvailable) {
    // keep checking
}
```

which wastes CPU, it can say:

```java
wait();
```

Meaning:

> "Wake me when there's something useful to do."

Then the producer produces something and says:

```java
notify();
```

Meaning:

> "Hey! Something changed. A waiting thread should check again."

---

# 3. Tiny example

Let's create a shared box.

```java
class Box {

    int value;
    boolean available = false;

    Object lock = new Object();

    void consume() throws InterruptedException {

        synchronized (lock) {

            while (!available) {

                System.out.println(
                        "Consumer: nothing available, waiting..."
                );

                lock.wait();
            }

            System.out.println(
                    "Consumer received: " + value
            );

            available = false;
        }
    }

    void produce(int value) {

        synchronized (lock) {

            this.value = value;
            available = true;

            System.out.println(
                    "Producer produced: " + value
            );

            lock.notify();
        }
    }
}
```

Now:

```java
public class WaitNotifyDemo {

    public static void main(String[] args)
            throws InterruptedException {

        Box box = new Box();

        Thread consumer = new Thread(() -> {

            try {
                box.consume();
            }
            catch (InterruptedException e) {
                System.out.println("Consumer interrupted");
            }

        }, "Consumer");


        Thread producer = new Thread(() -> {

            try {
                Thread.sleep(2000);
            }
            catch (InterruptedException e) {
                return;
            }

            box.produce(42);

        }, "Producer");


        consumer.start();
        producer.start();

        consumer.join();
        producer.join();
    }
}
```

---

# Watch the flow

Initially:

```text
available = false
```

Consumer starts:

```text
Consumer
   ↓
synchronized(lock)
   ↓
available == false
   ↓
lock.wait()
```

At this moment:

```text
Consumer
   ↓
WAITING
   ↓
🔓 releases lock
```

Now Producer can enter:

```text
Producer
   ↓
synchronized(lock)
   ↓
value = 42
available = true
   ↓
notify()
```

Conceptually:

```text
                 lock
                  🔒
                   │
          ┌────────┴────────┐
          ↓                 ↓
      Consumer          Producer
      WAITING              │
          │                │
          │            produce 42
          │                │
          │              notify()
          │                │
          ◄────────────────┘
          │
     wakes up
          │
     tries to reacquire
          │
          ▼
    continues
```

### Important!

`notify()` does **not** mean:

> "Immediately start running!"

It means:

> **"A waiting thread can now become eligible to continue, after it can reacquire the monitor."**

That's an important distinction.

---

# 4. Why `while`, not `if`?

You'll often see:

```java
while (!available) {
    lock.wait();
}
```

rather than:

```java
if (!available) {
    lock.wait();
}
```

Because when the thread wakes up, it should **check the condition again**.

Think:

```text
wake up
   ↓
"Is the resource actually available?"
   ↓
YES → continue
NO  → wait again
```

This is a fundamental concurrency pattern:

> **Wait for a condition, then re-check the condition after waking.**

We'll dig deeper into *why* later.

---

# 5. `notifyAll()`

You also have:

```java
lock.notifyAll();
```

Suppose:

```text
Consumer-1 ── WAITING
Consumer-2 ── WAITING
Consumer-3 ── WAITING
```

Then:

```java
lock.notify();
```

generally wakes **one** waiting thread.

Whereas:

```java
lock.notifyAll();
```

wakes **all waiting threads**, which then compete to reacquire the monitor.

```text
             notifyAll()
                 │
       ┌─────────┼─────────┐
       ↓         ↓         ↓
 Consumer-1  Consumer-2  Consumer-3
       │         │         │
       └─────────┼─────────┘
                 ↓
          compete for lock
```

They don't all execute the synchronized section simultaneously.

Only one can own the monitor at a time.

---

# 🚨 One strict rule

You can't randomly do:

```java
lock.wait();
```

from anywhere.

The current thread must **own that object's monitor**.

Therefore:

```java
synchronized(lock) {
    lock.wait();
}
```

is valid.

But:

```java
lock.wait();   // ❌ generally IllegalMonitorStateException
```

is not.

Same idea for:

```java
lock.notify();
lock.notifyAll();
```

The calling thread must own `lock`'s monitor.

---

## 🧠 Connect everything we've learned

We're now building this:

```text
                 THREADS
                    │
          ┌─────────┴─────────┐
          ↓                   ↓
      Scheduling          Coordination
          │                   │
       priority          synchronized
                              │
                              ↓
                           monitor
                              │
                     ┌────────┼────────┐
                     ↓        ↓        ↓
                   lock     wait()   notify()
                                      │
                                      ↓
                                  notifyAll()
```

And this is **not yet the end**.

The next thing I want you to understand is **monitor ownership in detail**, because once you understand:

> "A thread owns the monitor → calls `wait()` → releases monitor → another thread acquires it → `notify()` → waiting thread eventually reacquires it"

then `wait/notify` stops looking like magic.

### 🧪 Your experiment

Don't jump into a giant producer-consumer project yet.

Run the tiny `Box` example above.

Then modify:

```java
lock.notify();
```

to:

```java
lock.notifyAll();
```

You'll eventually build a proper **Producer–Consumer system** with multiple producers and consumers. That will be one of our serious concurrency projects, not just a print-statement demo. 🔥
