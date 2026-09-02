/*

Problem

Create a ReentrantLock. The main thread should:

Acquire the lock.
Execute some work.
Release the lock.
Print messages so we can observe the sequence.

working:

                          lock.lock()
                              ↓
                        🔒 acquired
                              ↓
                       critical section
                              ↓
                         finally
                              ↓
                        lock.unlock()
                              ↓
                        🔓 released
 */

import java.util.*;

class LockExperiment1 {
    public static void main(String[] args) {
        Lock lock = new ReentrantLock();

        System.out.println("Before acquiring lock");

        lock.lock(); // lock acuires

        try {
            System.out.println("Lock acquired");
            System.out.println("Doing some work...");
        }
        finally {
            lock.unlock();
            System.out.println("Lock released");
        }

        System.out.println("Program continues...");
    }
}