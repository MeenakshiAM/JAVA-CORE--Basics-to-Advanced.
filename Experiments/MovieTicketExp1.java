/*
Movie Ticket Booking



A cinema has ONE seat: A10.

Two customers attempt to book A10 simultaneously.

Version A

One Cinema object:

Cinema #1
   ↑
   |
BookingTask #1
   ↑       ↑
Thread A Thread B

Both try:

Book A10

Make the booking operation synchronized.

Questions
What is the shared resource?
What is the shared state?
What object owns the lock?
Why can't both successfully book the seat?
 */
//---------------------------------- soln -------------------------
/*
* the requirement says
*           have 1 seat
*           -> 2 people must try to acuire it simultaneously.
* */

class InsufficiantBalance extends Exception{
    InsufficiantBalance(){
        super("Insufficient Balance ");
    }
}

class Event {
    int seat = 1;

    synchronized void decrement(int nos) {
        try{
            if(seat < nos) throw new InsufficiantBalance();
            else{
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

    @Override
    public void run() {
//        try{
            event.decrement(this.nos);
//        }
//        catch (InsufficiantBalance e) {
//            System.out.println("Insufficient balance");
//        }

    }
}
class MovieTicketExp1{
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
------------output------------
java MovieTicketExp1

worker1 has booked the ticket
worker2 COULD NOT booked the ticket

 */