import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Worker1 implements Runnable {

    private Lock lock;

    Worker1(Lock lock) {
        this.lock = lock;
    }

    @Override
    public void run() {

        lock.lock();

        try {
            System.out.println("Worker-1 acquired the lock");

            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

        } finally {
            lock.unlock();
            System.out.println("Worker-1 released the lock");
        }
    }
}

class Worker2 implements Runnable {

    private Lock lock;

    Worker2(Lock lock) {
        this.lock = lock;
    }

    @Override
    public void run() {

        System.out.println("Worker-2 trying to acquire the lock");

        try {

            if (lock.tryLock(2, TimeUnit.SECONDS)) {// tries for 2 sec and then if it is released it will use it else moveon

                try {
                    System.out.println("Worker-2 acquired the lock");
                } finally {
                    lock.unlock();
                }

            } else {

                System.out.println(
                        "Worker-2 could NOT acquire the lock within 2 seconds"
                );
            }

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

public class TimedTryOut {

    public static void main(String[] args) {

        Lock lock = new ReentrantLock();

        Thread t1 = new Thread(
                new Worker1(lock),
                "Worker-1"
        );

        Thread t2 = new Thread(
                new Worker2(lock),
                "Worker-2"
        );

        t1.start();
        t2.start();
    }
}


/*
Worker-1 acquired the lock
Worker-2 trying to acquire the lock
Worker-2 could NOT acquire the lock within 2 seconds
Worker-1 released the lock


            tryLock()
                ↓
            Try immediately
                ↓
            No → false


            tryLock(2, SECONDS)
                ↓
            Try immediately
                ↓
            No
                ↓
            Wait up to 2 seconds
                ↓
            Got it? ── YES → true
                │
                NO
                ↓
            false
 */