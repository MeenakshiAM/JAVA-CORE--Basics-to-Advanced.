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

/*
--------------------- output ------------------
Worker-1 acquired Lock A
Worker-2 acquired Lock B
Worker-1 trying to acquire Lock B
Worker-2 trying to acquire Lock A
Worker-1 could NOT acquire Lock B
Worker-1 could NOT acquire Lock A

Explanation:
Now imagine:

A → Worker-1
B → Worker-2

Worker 1 reaches:

lockB.tryLock()

B is occupied.

So:

tryLock()
   ↓
false

Worker 1 doesn't sit there waiting for B.

It can leave its critical section and:

lockA.unlock();

Now:

Before:

A → Worker-1
B → Worker-2

Worker-1 can't get B
        ↓
Worker-1 releases A

After:

A → FREE
B → Worker-2

That is already enough to break the circular wait from Worker 1's side.

Compare this directly with lock()
lock()
Worker-1
   │
   ├── holds A
   │
   └── asks for B
             │
             ↓
          B is busy
             │
             ↓
           WAIT

Worker 1 continues holding A while waiting.

That's dangerous.

tryLock()
Worker-1
   │
   ├── holds A
   │
   └── tries B
             │
             ↓
          B is busy
             │
             ↓
          false
             │
             ↓
       release A

That is why tryLock() can help with deadlocks.

The thread doesn't remain stuck while holding its first lock.

But there's an important catch!

Don't think:

"tryLock() = deadlock impossible."

 Not automatically.

You have to write your failure handling correctly.

For example:

if (lockB.tryLock()) {

    try {
        // use A and B
    }
    finally {
        lockB.unlock();
    }

} else {

    // couldn't get B
    // release A
    // maybe retry later
}

The important idea is:

Could not get second resource
          ↓
Don't keep first resource unnecessarily
          ↓
Release first resource
          ↓
Try again / abort / do something else
And THIS connects beautifully to what you just learned

Remember our confusion:

"Why are Worker 1 and Worker 2 separate objects, but the locks are shared?"

Now you can see why.

Worker 1 ──────┐
               ├── Lock A
Worker 2 ──────┘

Worker 1 ──────┐
               ├── Lock B
Worker 2 ──────┘

The workers are two independent actors.

The locks represent shared resources.

And tryLock() is simply giving each worker the ability to say:

"I need this shared resource, but if I can't get it, I don't want to sit here forever holding something else."
 */