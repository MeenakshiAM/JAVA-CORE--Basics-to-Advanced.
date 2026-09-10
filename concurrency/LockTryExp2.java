import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Worker1 implements Runnable {

    private Lock lockA;
    private Lock lockB;

    Worker1(Lock lockA, Lock lockB) {
        this.lockA = lockA;
        this.lockB = lockB;
    }

    @Override
    public void run() {

        lockA.lock();// lock A is the thing that is actually done by the worker 1
        // lock A ensures to lock the changeable part ot the shared part currently accuired
        //lock() basically says:
        //"If B isn't available, I am willing to wait."

        try {
            System.out.println("Worker-1 acquired Lock A");

            Thread.sleep(1000);

            System.out.println("Worker-1 trying to acquire Lock B"); // upto this the worker A part

            if(lockB.tryLock()){
                // this is another part that is in hold by another thread.
                // this worker 1 or thread A is in need to that data too so it is trying to accuire it

                try {
                    System.out.println("Worker-1 acquired Lock B");
                }
                finally {
                    lockB.unlock();
                }
            }
            else {
                System.out.println("Worker-1 could NOT acquire Lock B");
            }


        } catch (InterruptedException e) {

            Thread.currentThread().interrupt();

        } finally {

            lockA.unlock();
        }
    }
}

class Worker2 implements Runnable {

    private Lock lockA;
    private Lock lockB;

    Worker2(Lock lockA, Lock lockB) {
        this.lockA = lockA;
        this.lockB = lockB;
    }

    @Override
    public void run() {

        lockB.lock();

        try {
            System.out.println("Worker-2 acquired Lock B");

            Thread.sleep(1000);

            System.out.println("Worker-2 trying to acquire Lock A");

            if(lockA.tryLock()){
                // this is another part that is in hold by another thread.
                // this worker 1 or thread A is in need to that data too so it is trying to accuire it

                try {
                    System.out.println("Worker-1 acquired Lock A");
                }
                finally {
                    lockA.unlock();
                }
            }
            else {
                System.out.println("Worker-1 could NOT acquire Lock A");
            }

        } catch (InterruptedException e) {

            Thread.currentThread().interrupt();

        } finally {

            lockB.unlock();
        }
    }
}

public class LockTryExp {

    public static void main(String[] args) {

        Lock lockA = new ReentrantLock();
        Lock lockB = new ReentrantLock();

        Thread t1 = new Thread(
                new Worker1(lockA, lockB),
                "Worker-1"
        );

        Thread t2 = new Thread(
                new Worker2(lockA, lockB),
                "Worker-2"
        );

        t1.start();
        t2.start();
    }
}