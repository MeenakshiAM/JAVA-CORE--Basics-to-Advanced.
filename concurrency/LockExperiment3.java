import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockExperiment3 {

    public static void main(String[] args) {

        Lock lock = new ReentrantLock();

        Thread t1 = new Thread(() -> {

            lock.lock();

            System.out.println("Worker-1 acquired the lock 🔒");

            // Intentionally NOT unlocking!
            System.out.println("Worker-1 forgot to release the lock");

        }, "Worker-1");


        Thread t2 = new Thread(() -> {

            System.out.println("Worker-2 trying to acquire lock...");

            lock.lock();

            try {
                System.out.println("Worker-2 acquired the lock");
            }
            finally {
                lock.unlock();
            }

        }, "Worker-2");


        t1.start();
        t2.start();
    }
}
/*
------------------ output ---------------------------

Worker-1 acquired the lock
Worker-1 forgot to release the lock
Worker-2 trying to acquire lock...
\CORE-JAVA\concurrency>


here as it did not unlock the worker 2 cannot take on ...
this condition is called dead lock
 */