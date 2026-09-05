/*
tryLock() Challenge

Now we introduce your last confusion.

Imagine a restaurant reservation system.

Two workers need access to:

Table
Payment Terminal

Instead of waiting forever for a lock, use a Lock and tryLock().

The rule:

"Try to acquire the lock. If you can't get it within a specified amount of time, don't wait indefinitely. Cancel the operation and report that the reservation could not be completed."

Questions
How is tryLock() different from synchronized?
What happens when the lock is unavailable?
Why could this be useful in a real system?
How could it help avoid a thread getting stuck indefinitely?
What is the relationship between:
synchronized
Lock
tryLock()
 */