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

        try {
            System.out.println("Worker-1 acquired Lock A");

            Thread.sleep(1000);

            System.out.println("Worker-1 trying to acquire Lock B"); // upto this the worker A part

            lockB.lock(); // this is another part that is in hold by another thread.
            // this worker 1 or thread A is in need to that data too so it is trying to accuire it

            try {
                System.out.println("Worker-1 acquired Lock B");
            }
            finally {
                lockB.unlock();
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

            lockA.lock();

            try {
                System.out.println("Worker-2 acquired Lock A");
            }
            finally {
                lockA.unlock();
            }

        } catch (InterruptedException e) {

            Thread.currentThread().interrupt();

        } finally {

            lockB.unlock();
        }
    }
}

public class DeadlockExp {

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

/*
--------------------- output ------------------

Worker-2 acquired Lock B
Worker-1 acquired Lock A
Worker-2 trying to acquire Lock A
Worker-1 trying to acquire Lock B

it encountered an deadlock situation

Explanation:

At the beginning:

lockA = FREE
lockB = FREE
Worker-1:
lockA.lock();

Now:

Lock A → Worker-1
Lock B → FREE
Worker-2:
lockB.lock();

Now:

Lock A → Worker-1
Lock B → Worker-2

Then Worker-1 reaches:

lockB.lock();

But B belongs to Worker-2.

So:

Worker-1 → waiting for B

Then Worker-2 reaches:

lockA.lock();

But A belongs to Worker-1.

So:

Worker-2 → waiting for A

And now:

Worker-1
   │
   │ holds A
   │
   └────── wants B ──────┐
                         ↓
                       Worker-2
                         │
                         │ holds B
                         │
                         └────── wants A ──────┐
                                                ↓
                                           Worker-1

 Circular wait.

Nobody can move.

And look at how perfectly this maps to the OS conditions:

Deadlock condition	   Our experiment
Mutual exclusion	   Each lock has one owner
Hold and wait	       Each worker holds one lock while requesting another
No preemption	       One worker can't forcibly take the other's lock
Circular wait	       W1 → B → W2 → A → W1
 */