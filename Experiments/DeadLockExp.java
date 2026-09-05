/*
Deadlock: Two People, Two Keys

Now we'll attack your "why did we create two Worker objects?" confusion.

Imagine two people:

Alice owns Key-A
Bob owns Key-B

Alice needs:

Key-A + Key-B

Bob also needs:

Key-B + Key-A

Create:

Worker #1 → Thread A
Worker #2 → Thread B

Each worker attempts to acquire two different locks.

Conceptually:

Thread A              Thread B

lock A                lock B
  ↓                     ↓
wait for B             wait for A
  ↓                     ↓
   └──── DEADLOCK ──────┘
Your questions
Why do we need two lock objects?
Why can't one lock demonstrate this particular deadlock?
What does Thread A own?
What does Thread B own?
What happens when A waits for B while B waits for A?
Imagine a cinema has two different seats:

Cinema
├── Seat A10
└── Seat A11

But there's a more interesting rule:

To complete a special booking, the system needs to reserve both seats.

Now imagine two customers/processes:

Customer 1

Wants:

A10 + A11
Customer 2

Also wants:

A11 + A10

This is where the deadlock appears.
 */
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class InsufficientBalance extends Exception {
    InsufficientBalance() {
        super(" insufficient balance .... ");
    }
}

class Seat {
    String seatName;
    Boolean isBooked;

    Seat(String seatName) {
        this.seatName = seatName;
        this.isBooked = false;
    }
}


class Alice implements Runnable {
    private Lock lockA;
    private Lock lockB;
    private Seat seatA10;
    private Seat seatA11;

    Alice(Lock lockA, Lock lockB, Seat seatA10, Seat seatA11) {
        this.lockA = lockA;
        this.lockB = lockB;
        this.seatA10 = seatA10;
        this.seatA11 = seatA11;
    }

//    Seat seatA10 = new Seat("A10");
//    Seat seatA11 = new Seat("A11");

    @Override
    public void run() {
        // we are booking the seat A10 for Alice

        lockA.lock();

        try {

            System.out.println(Thread.currentThread().getName() + " acquired lock A10");

            seatA10.isBooked = true; // seat A10 is booked by Alice now she tries to book A11 too..

            System.out.println(Thread.currentThread().getName() + " booked " + seatA10.seatName);
            System.out.println(Thread.currentThread().getName() + " trying to acquire lock A11");

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            lockB.lock();
            try{
                System.out.println(Thread.currentThread().getName() + " acquired lock A11");

                seatA11.isBooked = true;

                System.out.println(Thread.currentThread().getName() + " booked " + seatA11.seatName);
            }
            finally {
                lockB.unlock();
            }
        }
        finally{
            lockA.unlock();
        }
    }
}

class Bob implements Runnable {
    private Lock lockA;
    private Lock lockB;
    private Seat seatA10;
    private Seat seatA11;

    Bob(Lock lockA, Lock lockB, Seat seatA10, Seat seatA11) {
        this.lockA = lockA;
        this.lockB = lockB;
        this.seatA10 = seatA10;
        this.seatA11 = seatA11;
    }


    @Override
    public void run() {
        lockB.lock();

        try {
            System.out.println(Thread.currentThread().getName() + " acquired lock A11");

            seatA11.isBooked = true;

            System.out.println(Thread.currentThread().getName() + " booked " + seatA11.seatName);
            System.out.println(Thread.currentThread().getName() + " trying to acquire lock A10");

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            lockA.lock();

            try {
                System.out.println(Thread.currentThread().getName() + " acquired lock A10");

                seatA10.isBooked = true;

                System.out.println(Thread.currentThread().getName() + " booked " + seatA10.seatName);
            }
            finally {
                lockA.unlock();
            }
        }
        finally{
            lockB.unlock();
        }
    }
}

class DeadLockExp {
    public static void main(String[] args) throws InterruptedException{
        Lock lockA = new ReentrantLock();
        Lock lockB = new ReentrantLock();

        Seat seatA10 = new Seat("A10");
        Seat seatA11 = new Seat("A11");

        Alice alice  =  new Alice(lockA,lockB, seatA10, seatA11);
        Bob bob  =  new Bob( lockA, lockB, seatA11,seatA10);

        Thread t1 =  new Thread(alice,"Alice");
        Thread t2 = new Thread(bob, "Bob");

        t1.start();
        t2.start();

        t1.join();
        t2.join();
    }
}

/*
Alice acquired lock A10
Bob acquired lock A11
Alice booked A10
Bob booked A10
Alice trying to acquire lock A11
Bob trying to acquire lock A10

 */