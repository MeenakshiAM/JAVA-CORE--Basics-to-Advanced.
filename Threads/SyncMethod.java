class SharedCounter {

    int counter = 0;

    public synchronized void increment() {
        counter++;
        System.out.println(
                Thread.currentThread().getName() + "-> " + counter
        );
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            System.out.println("Thread interrupted");
        }
    }
}
class NewThread implements Runnable{
    SharedCounter counter;

    NewThread(SharedCounter counter){
        this.counter = counter;
    }

    @Override
    public void run(){
       // try{
            for(int i = 0; i< 8; i++) {
                counter.increment();

            }
//                Thread.sleep(2);
//            }
//        }
//        catch (InterruptedException e){
//            System.out.println("thread interrupted");
//        }
    }
}

class SyncMethod {
    public static void main(String[] args) throws InterruptedException{
        SharedCounter counter =
                new SharedCounter();

        Thread t1 = new Thread(
                new NewThread(counter),
                "Worker-1"
        );

        Thread t2 = new Thread(
                new NewThread(counter),
                "Worker-2"
        );

        Thread t3 = new Thread(
                new NewThread(counter),
                "Worker-3"
        );

        t1.start();
        t2.start();
        t3.start();

        t1.join();
        t2.join();
        t3.join();

        System.out.println(
                "Final counter = "
                        + counter.counter
        );
    }
}

/*
*PS E:\E\CORE-JAVA\threads> java SyncMethod
Worker-2-> 1
Worker-2-> 2
Worker-2-> 3
Worker-3-> 4
Worker-3-> 5
Worker-3-> 6
Worker-3-> 7
Worker-3-> 8
Worker-1-> 9
Worker-1-> 10
Worker-1-> 11
Worker-3-> 12
Worker-3-> 13
Worker-2-> 14
Worker-2-> 15
Worker-2-> 16
Worker-3-> 17
Worker-1-> 18
Worker-1-> 19
Worker-1-> 20
Worker-1-> 21
Worker-1-> 22
Worker-2-> 23
Worker-2-> 24
Final counter = 24
*
* T1
 │
 ▼
increment()
 │
 🔒 acquire monitor
 │
 counter++
 │
 print
 │
 sleep 😴
 │
 │     T2 tries increment()
 │             │
 │             ▼
 │          BLOCKED
 │
T1 wakes
 │
 ▼
increment() ends
 │
 🔓 release monitor
 │
 ▼
T2 can enter
*
*
*
* if we try put the sleep in the run method then  it may coz the thread to sleep
* sleep() itself has nothing to do with synchronization
* This is another thing I want you to lock into your brain:

Thread.sleep(1000);

does not mean:

"Release the lock and let another thread enter."

It means:

"Pause the currently executing thread for approximately this duration."

If that thread owns a monitor, it continues owning it while sleeping.
*
*
*
* --------------------
* if we put the sleep in the run method then ...
*
*
* The flow is:

T1
 │
 ▼
increment()
 │
 🔒 acquire monitor
 │
 counter++
 │
 print
 │
 🔓 release monitor
 │
 ▼
sleep(1 sec)
 │
 😴

While T1 is sleeping, it no longer holds the SharedCounter monitor.

Therefore:

T1                         T2
│                          │
increment()                │
🔒                         │
counter++                  │
🔓                         │
│                          │
sleep 😴              increment()
                           🔒
                           │
                           counter++
                           🔓

So T2 can happily enter increment() while T1 is sleeping.
*/