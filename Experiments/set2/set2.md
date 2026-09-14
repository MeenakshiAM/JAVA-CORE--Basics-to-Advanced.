Absolutely bro. This is better. **Pure question-form experiments**, no solution, no hints unless you ask.

And I'm keeping them within what you currently know: **threads, `Runnable`, shared objects, `synchronized`, `ReentrantLock`, `tryLock`, timed `tryLock`, deadlock, `sleep`, `join`, `interrupt`**. No file handling, streams, sockets, etc.

## Experiment 1 — Shared Bank Counter

**Question:**

Create a `Bank` class with:

```text
balance = 10,000
transactionCount = 0
```

Create **5 threads**. Each thread should perform **100 withdrawals of ₹10**.

1. What should the final balance be?
2. What should the final transaction count be?
3. Run the program **without synchronization**.
4. Do you always get the expected result?
5. If not, explain exactly how the wrong result can happen.
6. Fix the program using `synchronized`.
7. Which object should own the lock?
8. Why is protecting `balance` but not `transactionCount` incorrect?

---

## Experiment 2 — Shared Printer

Create a `Printer` object shared by **3 worker threads**.

Each worker has a different document:

```text
Worker 1 → "JAVA"
Worker 2 → "THREADS"
Worker 3 → "LOCK"
```

The printer prints one character at a time.

For example:

```text
JAVA
```

is printed as:

```text
J
A
V
A
```

with a small `sleep()` between characters.

### Questions

1. What happens if all three threads print simultaneously?
2. Can the output become mixed?
3. Why does `sleep()` make the problem easier to observe?
4. Make the printer thread-safe.
5. Should the **worker** be synchronized or should the **printer** be synchronized?
6. What exactly is the shared resource?
7. What object should the lock belong to?

---

## Experiment 3 — ATM Withdrawal Race

Create one shared `BankAccount`:

```text
balance = ₹10,000
```

Two threads:

```text
Alice → withdraw ₹7,000
Bob   → withdraw ₹7,000
```

The withdrawal operation should conceptually do:

```text
check balance
↓
wait a little
↓
subtract amount
```

### Questions

1. Run it without synchronization.
2. Can both Alice and Bob successfully withdraw?
3. If yes, how can that happen even though the balance started at only ₹10,000?
4. Identify the **critical section**.
5. Synchronize the correct section.
6. Why isn't synchronizing only the subtraction enough?
7. After fixing it, which user should succeed and which should fail?
8. Does the answer depend on which thread reaches the account first?

---

# Experiment 4 — The Wrong Lock

Create:

```text
class TicketCounter
```

with:

```text
tickets = 1
```

Create two separate worker objects:

```text
Worker A
Worker B
```

Both workers reference the **same `TicketCounter`**.

Both attempt to book the final ticket.

### Questions

1. Put `synchronized` on the worker's booking method.
2. Run two threads.
3. Does this actually protect the ticket?
4. Why or why not?
5. What object are the threads actually locking?
6. Change the synchronization so that both threads compete for the **same monitor**.
7. What changes in the output?

This one is important. I want you to be able to **spot a wrong lock instantly**.

---

# Experiment 5 — Resource Pool

Now the one you liked.

Imagine your application has only **2 printers**:

```text
Printer 1
Printer 2
```

But there are **5 workers** who want to use a printer.

A worker must:

```text
request printer
↓
if none available → wait
↓
get printer
↓
use printer
↓
release printer
↓
notify waiting workers
```

### Questions

1. How will you represent the available printers?
2. What shared state needs protection?
3. What should happen when all printers are occupied?
4. Which thread should wait?
5. On which object should `wait()` be called?
6. Who should call `notify()` / `notifyAll()`?
7. Why must the availability check happen while holding the monitor?
8. Why should the worker release the printer after use?
9. What happens if a worker forgets to release it?
10. Why might `notifyAll()` be safer than `notify()` here?

**Do this one with `synchronized + wait/notifyAll()` first.**

Do **not** use `Condition` yet.

---

# Experiment 6 — Job Queue

Create a shared `JobQueue`.

Initially:

```text
queue is empty
```

Three worker threads continuously try to obtain jobs.

Another thread adds jobs:

```text
Job-1
Job-2
Job-3
Job-4
Job-5
```

### Questions

1. What should a worker do when the queue is empty?
2. Should it continuously check the queue in a tight loop?
3. Can it use `wait()`?
4. What should happen when a new job is added?
5. Who should call `notifyAll()`?
6. What shared state requires synchronization?
7. Why should the worker check the condition again after waking?
8. What happens if two workers wake up but only one job exists?

This will reinforce `wait()` properly.

---

# Experiment 7 — Two Locks, One Deadlock

You already did Alice/Bob with cinema seats.

Now create a different situation:

```text
Account A
Account B
```

Alice transfers money:

```text
Account A → Account B
```

Bob transfers money:

```text
Account B → Account A
```

Both operations require locking **both accounts**.

### Questions

1. How can Alice acquire A and then wait for B?
2. How can Bob acquire B and then wait for A?
3. Can you reproduce a deadlock?
4. Draw the resource relationship.
5. What is the circular wait?
6. Fix the program using a **consistent lock ordering**.
7. Fix it again using `tryLock()`.
8. Which solution prevents the deadlock by **design**?
9. Which solution detects failure and backs out?

---

# Experiment 8 — Deadlock Recovery with Timed `tryLock()`

Take the previous two-account transfer.

This time, both threads may acquire their first lock.

But when trying to acquire the second:

```text
tryLock(timeout)
```

### Questions

1. What should happen if the second lock cannot be acquired within the timeout?
2. Which lock must be released?
3. Should the thread keep the first lock while retrying?
4. Why could that itself cause another problem?
5. Design a retry mechanism.
6. Can you make the transfer eventually succeed without deadlocking?

---

# Experiment 9 — Interrupt a Waiting Worker

Create a worker that waits for a resource using:

```java
wait()
```

Another thread acts as a controller.

### Questions

1. Can the waiting worker be interrupted?
2. What exception does `wait()` produce when interrupted?
3. What should the worker do when interrupted?
4. Should it continue waiting?
5. Should it terminate?
6. How would you cleanly communicate:

```text
"Stop waiting. We are shutting down."
```

This connects your **`interrupt()` knowledge** with `wait()`.

---

# Experiment 10 — Race Condition Detective

This is the big one.

I'll eventually give you a deliberately broken program.

Your job will be to answer **without immediately running it**:

1. What is shared?
2. Which variables are mutable?
3. Which threads can access them?
4. What operations form the critical section?
5. What race condition exists?
6. Give one possible bad interleaving.
7. Which object should own the lock?
8. Is `synchronized` on the method sufficient?
9. If not, why?
10. What is the **minimum correct synchronization** needed?
11. Could your solution introduce deadlock?
12. Could your solution unnecessarily reduce concurrency?

That last one is where we're eventually going to stop thinking like:

> "`synchronized` = make it safe"

and start thinking:

> **"What exactly must be protected, for how long, and by whom?"**

### Our immediate order

I would do:

**1 → 2 → 3 → 4 → 5 → 6 → 7 → 8 → 9 → 10**

And **Experiment 5 (Resource Pool)** is where we'll properly bring your `wait()/notify()/notifyAll()` knowledge back to life before touching `Condition`.

No IO. No file handling. No sockets. No giant backend architecture. Just **solid concurrency reasoning and experiments**.
