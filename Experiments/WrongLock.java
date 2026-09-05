/*
-------------------------The Lock Is on the Wrong Object

Now we're going to intentionally create a bug.

Suppose:

class BookingTask implements Runnable {

    private final Cinema cinema;

    public synchronized void book() {
        cinema.bookSeat();
    }
}

You create:

Cinema #1
    ↑       ↑
Task #1   Task #2

and:

Thread A → Task #1
Thread B → Task #2
Your challenge

Determine whether the synchronization actually prevents both tasks from entering book() simultaneously.

Don't run it first.

Ask:

What object is being locked by synchronized?

That's the question I want you to train yourself to ask every time.
 */

class InsufficiantBalance extends Exception{
    InsufficiantBalance(){
        super("Insufficient Balance ");
    }
}

class Event {
    int seat = 1;

    void decrement(int nos) {
        try{
            if(seat < nos) throw new InsufficiantBalance();
            else{
                try {
                    Thread.sleep(2000);   // make the race obvious
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                seat = seat - nos;

                System.out.println(
                        Thread.currentThread().getName() +" has booked the ticket"
                );
            }
        } catch (InsufficiantBalance e) {
            System.out.println(
                    Thread.currentThread().getName() +" COULD NOT booked the ticket"
            );
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
    synchronized void book() {
        System.out.println(
                Thread.currentThread().getName()
                        + " acquired lock of Task object: "
                        + this
        );
        event.decrement(nos);
        System.out.println(
                Thread.currentThread().getName()
                        + " releasing lock of Task object: "
                        + this
        );
    }

    @Override
    public void run() {
//        try{

        book();
//        }
//        catch (InsufficiantBalance e) {
//            System.out.println("Insufficient balance");
//        }

    }
}
class WrongLock{
    public static void main(String[] args) throws InterruptedException{
        Event event = new Event();

        // it is baically for the same seat of the same event 2 people are competing.
        // so we need to use the same monitor of that particular event for both;

        NewThread task1 = new NewThread(event,1);
        NewThread task2 = new NewThread(event,1);

        Thread t1 = new Thread(task1, "worker1");
        Thread t2 = new Thread(task2, "worker2");

        t1.start();
        t2.start();

        t1.join();
        t2.join();
    }
}

/*
worker2 acquired lock of Task object: NewThread@412be3e4
worker1 acquired lock of Task object: NewThread@27cda136
worker1 has booked the ticket
worker2 has booked the ticket
worker1 releasing lock of Task object: NewThread@27cda136
worker2 releasing lock of Task object: NewThread@412be3e4

 */