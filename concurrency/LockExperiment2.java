//import java.util.*;
import java.util.concurrent.locks.*;
//import java.util.concurrent.locks.ReentrantLock;

class Worker implements Runnable {
    Lock lock;

    Worker(Lock lock){
        this.lock = lock;
    }

    @Override
    public void run() {

        lock.lock(); // explicitly starting thr lock

        try {
            System.out.println(
                    Thread.currentThread().getName() + " acquired the lock"
            );

            Thread.sleep(2000);

            System.out.println(
                    Thread.currentThread().getName() + " leaving critical section"
            );

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();

        } finally { // here we need to use the unlocking mechanism in the finally block becoz
            // no matter what like if it throws an error or not we must release tbe lock
            // just becoz it got caught in the try block we should not hold the lock
            lock.unlock();

            System.out.println(
                    Thread.currentThread().getName() + " released the lock "
            );
        }
    }
}
class LockExperiment2{
    public static void main(String[] args) throws InterruptedException {

        Lock lock = new ReentrantLock();

        Thread t1 = new Thread(new Worker(lock), "worker1");
        Thread t2 = new Thread(new Worker(lock), "worker2");

        t1.start();
        t2.start();

        t1.join();
        t2.join();

    }
}

/*
---------------------- output ---------------------
worker1 acquired the lock
worker1 leaving critical section
worker2 acquired the lock
worker1 released the lock
worker2 leaving critical section
worker2 released the lock

 */