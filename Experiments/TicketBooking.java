/*
🧪 Problem 2 — Synchronized Block

Concurrent Ticket Booking System

Create a Java program that simulates multiple users trying to book tickets for the same event simultaneously.

*The event has 10 tickets initially.
*Create at least 4 threads, each representing a different customer.
*Each customer should request a different number of tickets.
*The program must check availability before booking.
*No more than 10 tickets should ever be sold.
*Print the customer name, number of tickets requested, whether the booking succeeded or failed, and the remaining tickets.
*Add some normal processing before and after the actual ticket-count modification to simulate a real booking operation.
*Only the part that checks and modifies the shared ticket count should be synchronized.

-> Requirement: Solve the problem using a synchronized block, not a synchronized method.
 */
class InsufficientBalance extends Exception {
    InsufficientBalance() {
        super("Insufficient balance");
    }
}

class Event {
    int tickets = 10;


    Object lock = new Object();

    void decrement(int nos) throws InsufficientBalance{

        /*

        if we write this check outside the synchronized block
        if(tickets < nos) {
                throw new InsufficientBalance();
            }

        The check and modification together form one atomic operation.

You currently have:

             tickets = 10
                  │
        ┌─────────┴─────────┐
        │                   │
    Worker-1            Worker-2
        │                   │
   check tickets         check tickets
        │                   │
      okay                okay
        │                   │
        └───────┬───────────┘
                ↓
          modify tickets

Both threads can check the old value before either one modifies it.
         */
        synchronized (lock){
            if(tickets < nos) {
                throw new InsufficientBalance();
            }
            tickets -= nos;

            System.out.println("the customer - >" + Thread.currentThread().getName()
                    + " tickets - > "+ nos + " balance -> " +tickets);
        }
    }

}

class NewThread  implements Runnable{
    Event event;
    int nos;

    NewThread(Event event,int nos) {
        this.event = event;
        this.nos = nos;
    }

//    Event event = new Event();

    @Override
    public void run() { // always remember the run function that implements the runnable  method must be PUBLIC
        /*
        When implementing an interface → you cannot reduce visibility
        ITS THE RULE OF INTERFACE

            Suppose:

                        interface Animal {
                            void sound();
                        }

            That method is implicitly:

                        public abstract void sound();
         */
        try{
            event.decrement(this.nos);
        } catch (InsufficientBalance e) {
            System.out.println("Insufficient balance");
        }

    }
}
class TicketBooking {
    public static void main(String[] args) throws InterruptedException {
        Event event = new Event();

        NewThread task1 = new NewThread(event,2);
        NewThread task2= new NewThread(event,7);
        NewThread task3 = new NewThread(event,3);

        Thread t1 = new Thread(task1, "worker1");
        Thread t2 = new Thread(task2, "worker2");
        Thread t3 =  new Thread(task3, "worker3");

        t1.start();
        t2.start();
        t3.start();

        t1.join();
        t2.join();
        t3.join();

        System.out.println(
                "Final balance = ₹" + event.tickets
        );

    }
}

/*
CORE-JAVA\experiments> java TicketBooking
the customer - >worker1tickets - > 2balance -> 8
the customer - >worker2tickets - > 7balance -> 1
Insufficient balance
Final balance = ?1
CORE-JAVA\experiments> javac TicketBooking.java
E\CORE-JAVA\experiments> java TicketBooking
the customer - >worker2 tickets - > 7 balance -> 3
the customer - >worker3 tickets - > 3 balance -> 0
Insufficient balance
Final balance = ?0
*/