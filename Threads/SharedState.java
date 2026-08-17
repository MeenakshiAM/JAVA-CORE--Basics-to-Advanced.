
class NewThread implements Runnable{
    int counter = 0; // this is a variable that is manipulated by all the working threads
    public void run(){
        try{
            for(int i = 0; i< 8; i++) {
                counter++;
                System.out.println(
                        Thread.currentThread().getName()+"-> "+counter
                );
                Thread.sleep(2);
            }
        }
        catch (InterruptedException e){
            System.out.println("thread interrupted");
        }
    }
}

class SharedState{
    public static void main(String[] args) {
        NewThread task = new NewThread();

        Thread t = new Thread(task, "demo");
        Thread t1 = new Thread(task, "Worker-1");
        Thread t2 = new Thread(task, "Worker-2");
        Thread t3 = new Thread(task, "Worker-3");

        t1.start();
        t2.start();
        t3.start();
        t.start();
    }
}

/*---------------------current output---------------
* Worker-1-> 1
Worker-3-> 4
Worker-2-> 3
demo-> 2
demo-> 7
Worker-1-> 5
Worker-3-> 8
Worker-2-> 6
Worker-2-> 9   ------------> SEE HERE BOTH READ THE VALUE
Worker-3-> 9   -----------> BUT BOTH UPDATED EQUALLY RESULTING IN LOSS OF INCREMENTATIONS
demo-> 10
Worker-1-> 11
Worker-2-> 13
Worker-3-> 12
Worker-1-> 14
demo-> 15
Worker-1-> 16
demo-> 16
Worker-3-> 17
Worker-2-> 18
Worker-1-> 19
demo-> 19
Worker-3-> 20
Worker-2-> 21
demo-> 24
Worker-3-> 22
Worker-1-> 23
Worker-2-> 25
Worker-3-> 27
demo-> 27
Worker-1-> 28
Worker-2-> 29

*And you have:
*4 threads × 8 increments = 32 increments
*So you'd expect the final counter to be:32
*But you got:29
* You have lost increments.
* 🔥 And THAT is the race condition we're looking for.
* if we notice here the same counter or mutable variable is manipulateed and chaged by every other working thread
*
*                     ┌─────────────────┐
                    │  ONE NewThread  │
                    │      task       │
                    └────────┬────────┘
                             │
             ┌───────────────┼───────────────┐
             │               │               │
             ▼               ▼               ▼
         Worker-1         Worker-2        Worker-3
             │               │               │
             └───────────────┼───────────────┘
                             │
                             ▼
                         same run()
                         *
* counter++ is not an atomic operation.

Because it consists of multiple steps.

And therefore:

multiple threads
       ↓
shared mutable state
       ↓
counter++
       ↓
multiple threads can interleave
       ↓
race condition
       ↓
lost updates
*

* */