YES. **This is exactly what we should do now.** Not 20 variations of “two people book a cinema seat.” I want to break the pattern-recognition dependency.

You should get a problem where the **surface story is unfamiliar**, but underneath it tests the same Java concept.

And because you specifically want independent construction, I'm going to give you **questions only**. No solution skeletons.

I've also kept out **I/O, file handling, streams, sockets, `ExecutorService`, collections-heavy concurrency, etc.** We'll introduce those only when you've learned them.

---



### 1. The Three Robots

Three robots need to perform independent startup sequences.

Each robot should print:

```text
Robot X starting
Robot X checking system
Robot X ready
```

with a delay between each step.

**Questions:**

* Create each robot as a `Runnable`.
* Run all three concurrently.
* What changes if you call `run()` instead of `start()`?
* Can you predict whether Robot 1 will always start first?
* Can you predict the exact output order?
* Why or why not?

---

### 2. The Restaurant Kitchen

Create three cooks.

Each cook performs:

```text
Prepare
Cook
Serve
```

**Questions:**

* Run all three cooks concurrently.
* Make the main thread wait until all cooks finish.
* What happens if you remove `join()`?
* Can the main thread print `"Kitchen closed"` before all cooks finish?
* Can `join()` tell you which cook finishes first?

---



### 3. The Scoreboard

There is **one scoreboard**.

Three players have separate player objects.

Each player can add points to the scoreboard.

**Questions:**

* How many `Player` objects should exist?
* How many `Scoreboard` objects should exist?
* Which object should contain the score?
* Should each player create its own scoreboard?
* What happens if they accidentally do?
* How can you prove that all players are modifying the same scoreboard?

---

### 4. The Remote Control

There is one television and three remote-control objects.

Each remote can change the TV channel.

**Questions:**

* Should there be one TV or three TVs?
* Should there be one remote or three remotes?
* Which objects are shared?
* Which objects are private?
* If `Remote` has a `synchronized` method, what exactly does that protect?
* Is synchronizing the remote necessarily enough to protect the TV?

**This one is deliberately designed to attack the exact mistake you just made with `Printer`.**

---

`

### 5. The Elevator

Two threads represent two people requesting an elevator.

There is one elevator.

The elevator operation is:

```text
check whether available
sleep
enter elevator
sleep
mark occupied
```

**Questions:**

* Why does `sleep()` make a race easier to reproduce?
* Does `sleep()` itself create the race?
* What happens if you remove the sleeps?
* Can the race still theoretically exist?
* Which operations must be treated as one atomic operation?

---

### 6. The Race You Can't See

Create two threads incrementing a shared integer 100,000 times.

Run the program repeatedly.

**Questions:**

* Is the result always wrong?
* Is the result always correct?
* Why can the same program produce different results?
* Can you explain one possible interleaving of two increments?
* Does adding `sleep()` fix the race?
* If not, what does it actually do?

---


### 7. The One-Lane Bridge

There is one bridge.

Only one vehicle can be on the bridge at a time.

Create multiple vehicle threads.

Each vehicle:

```text
enters
crosses
exits
```

**Questions:**

* What is the shared resource?
* What object should own the monitor?
* Make crossing thread-safe.
* Should `Vehicle` be synchronized?
* Or should `Bridge` be synchronized?
* What happens if every vehicle has its own `Bridge`?

Yep. I'm deliberately attacking the same concept from another direction.

---

### 8. The Bathroom Problem

There is one bathroom.

Five people attempt to use it.

Each person spends a few seconds inside.

**Questions:**

* What is the critical section?
* What should be synchronized?
* Should the entire person's `run()` method be synchronized?
* Or only the bathroom operation?
* What happens to concurrency if you synchronize too much?

That last question matters.

**Thread safety ≠ synchronize everything.**

---

### 9. The Library Card

There is one shared `Library`.

Each student has their own `Student` object.

Each student calls:

```text
borrowBook()
```

**Questions:**

* If `borrowBook()` is synchronized inside `Student`, what monitor is being acquired?
* Does that protect the shared `Library`?
* If Student A and Student B have different objects, are they locking the same monitor?
* How would you redesign it?
* Can you explain the object graph before coding?

---

### 10. Three Doors

There are three doors:

```text
Door A
Door B
Door C
```

Two threads need to use Door A.

Two other threads need Door B.

Nobody uses Door C.

**Questions:**

* Should there be one lock or three locks?
* If you synchronize one giant `useDoor()` method, what happens?
* Could two threads safely use different doors simultaneously?
* Design synchronization so that:

    * Door A users block each other.
    * Door B users block each other.
    * Door A and Door B can be used simultaneously.

This one will make you think about **lock granularity**.

---

### 11. The Parking Lot

There are two parking spaces.

Three cars want spaces.

Use `ReentrantLock`.

**Questions:**

* What represents the shared resource?
* How will a car attempt to acquire a space?
* What happens when no space is immediately available?
* Use `tryLock()`.
* What does failure to acquire the lock mean?
* Does `tryLock()` make the resource itself thread-safe?
* What responsibility still belongs to your code?

---

### 12. The Photographer

There is one professional camera.

Three photographers want to use it.

Each photographer:

```text
tries to acquire camera
uses camera
releases camera
```

**Questions:**

* Use `ReentrantLock`.
* What happens if a photographer forgets `unlock()`?
* Where should unlocking happen?
* Why is `finally` important?
* Can you create a situation where one photographer cannot get the camera and immediately moves on?

---



### 13. The VIP Room

Two workers need access to a VIP room.

The room has one lock.

Each worker will only wait **2 seconds**.

**Questions:**

* Use timed `tryLock()`.
* What happens when the lock is unavailable for the full 2 seconds?
* Should the worker continue waiting?
* What should it do instead?
* What is the difference between:

    * `lock()`
    * `tryLock()`
    * `tryLock(timeout, unit)`

Don't just define them — demonstrate the behavioral difference.

---

### 14. The Coffee Machine

There is one expensive coffee machine.

A worker wants it.

If it can't acquire the machine within 1 second, it should give up.

**Questions:**

* Implement this using timed `tryLock()`.
* Make the machine intentionally busy.
* Observe what happens.
* Why is this different from blocking forever with `lock()`?

---


### 15. The Two Tools

A mechanic needs:

```text
Wrench
Hammer
```

Alice grabs the wrench first.

Bob grabs the hammer first.

Each then tries to acquire the other.

**Questions:**

* Reproduce deadlock.
* Draw the wait relationship.
* Identify:

    * mutual exclusion
    * hold-and-wait
    * no preemption
    * circular wait
* What happens if you remove the `sleep()`?
* Does removing `sleep()` logically eliminate deadlock?

---

### 16. The Two Scientists

Scientist A needs:

```text
Microscope → Computer
```

Scientist B needs:

```text
Computer → Microscope
```

**Questions:**

* Reproduce deadlock.
* Fix it using **consistent lock ordering**.
* Fix it again using **`tryLock()`**.
* Which solution changes the design?
* Which solution detects inability to proceed?

Different story, same underlying problem.

---


Now we finally bring back the thing you're rusty on.

### 17. The Charging Station

There is **one charging station**.

Five phones want to charge.

Only one phone can charge at a time.

If the station is occupied, the phone should **wait rather than repeatedly checking**.

When charging finishes, another waiting phone should be informed.

**Questions:**

* What condition is a phone waiting for?
* Which object should the phone wait on?
* Who should call `notify()` / `notifyAll()`?
* Why can't you call `wait()` randomly from anywhere?
* Why must `wait()` happen while owning the monitor?
* Why should the waiting thread check the condition again after waking?

---

### 18. The Doctor's Room

There is one examination room.

Patients arrive.

If the room is occupied, they wait.

When the doctor finishes with a patient, waiting patients are notified.

**Questions:**

* What shared state represents room availability?
* Where should `wait()` happen?
* Where should `notifyAll()` happen?
* What happens if you use `notify()` instead?
* Can a thread wake up and discover that it still cannot enter?
* Why must it check the condition again?

This one is particularly good for understanding **why `while` is used around `wait()`**.

---


### 19. The Night Watchman

A worker thread is waiting for a task.

The system administrator decides to shut down the worker.

**Questions:**

* How can the main thread tell the worker to stop waiting?
* What happens when the waiting thread is interrupted?
* Which exception occurs?
* What should the worker do after catching it?
* Should it silently continue?
* How would you make the worker terminate cleanly?

---

### 20. The Sleeping Guard

A guard thread sleeps for 10 seconds between patrols.

The supervisor wants to stop the guard immediately.

**Questions:**

* Can `sleep()` be interrupted?
* What happens?
* How is this different from simply setting a boolean?
* What should the guard do after interruption?

---

These are the ones I **really** want you to eventually do.

### 21. The Train Platform

There is one platform.

Multiple trains want to enter.

A train must:

```text
acquire platform
enter
stay for some time
leave
release platform
```

**Questions:**

* Model the platform as a shared object.
* Create multiple train tasks.
* Make platform access thread-safe.
* What happens if each train creates its own platform?
* What happens if the platform method is synchronized?
* Where does `sleep()` occur?
* Does the lock remain held during `sleep()`?
* Is that desirable here?

---

### 22. The Emergency Generator

There is one generator shared by multiple buildings.

A building may request it.

If another building is currently using it, the requester should wait.

When the current building finishes, another should be notified.

**Questions:**

Use:

```text
synchronized
wait()
notifyAll()
```

and answer:

* What is shared?
* What is the condition?
* Who waits?
* Who notifies?
* What monitor is used?
* What happens if you accidentally create one generator per building?
* What happens if you synchronize the building instead of the generator?

---

# And one special category for you

## "You Don't Get To Recognize The Pattern"

I'll give you something like:

> **A museum has one ancient artifact that can be examined by only one researcher at a time. Three researchers independently prepare their notes, then need access to the artifact. Each researcher takes different amounts of time. Design the system.**

