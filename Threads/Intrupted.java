class NewThread implements Runnable{
    int counter = 0; // this is a variable that is manipulated by all the working threads
    public void run() {
        try{
            for (int i = 1; i <= 10000; i++) {
                Thread.sleep(1000);
                System.out.println(
                        Thread.currentThread().getName() + " -> " + i
                );
            }
        }
        catch(InterruptedException e){
            System.out.println("Execution interrupted");
        }


        System.out.println("Worker finished");
    }
}

class Worker  implements Runnable{
    // int counter = 0; // this is a variable that is manipulated by all the working threads
    public void run() {

            for (int i = 1; i <= 10000; i++) {
                if(Thread.currentThread().isInterrupted()){// here we dont have any interrupt handling fns so need to check manually and break it
                    System.out.println("this thread is interrupted so stopping ...");
                    break;
                }
                System.out.println(
                        Thread.currentThread().getName() + " -> " + i
                );
            }

        System.out.println("Worker finished");
    }
}

class Intrupted {
    public static void main(String[] args)
            throws InterruptedException {

        NewThread task = new NewThread();
        Thread t1 = new Thread(task, "Worker-1");
        t1.start();

        Thread.sleep(3000);

        t1.interrupt();

        Worker taskw = new Worker();
        Thread t2 = new Thread(taskw, "Worker-2");
        t2.start();
        Thread.sleep(5);
        t2.interrupt();// on 5th sec the interruption will occur
    }
    /*
    * --------------------------output----------------------
    * Worker-1 -> 1
Worker-1 -> 2
Execution interrupted
Worker finished
Worker-2 -> 1
Worker-2 -> 2
Worker-2 -> 3
Worker-2 -> 4
Worker-2 -> 5
Worker-2 -> 6
Worker-2 -> 7
Worker-2 -> 8
Worker-2 -> 9
Worker-2 -> 10
Worker-2 -> 11
Worker-2 -> 12
Worker-2 -> 13
Worker-2 -> 14
this thread is interrupted so stopping ...
Worker finished

    * */
}

/*
                    MAIN THREAD
                        │
                        │
                    t1.start()
                        │
          ┌─────────────┴──────────────┐
          │                            │
          ▼                            ▼
        MAIN                       WORKER-1
          │                            │
     sleep(3000)                 run() starts
          │                            │
          │                       print 1
          │                            │
          │                       sleep(1000)
          │                            │
          │                       print 2
          │                            │
          │                       sleep(1000)
          │                            │
          │                            │
     3 seconds pass                   │
          │                            │
          ▼                            │
    t1.interrupt() ───────────────────►│
                                       │
                              Worker is sleeping
                                       │
                                       ▼
                             InterruptedException
                                       │
                                       ▼
                                  catch block
                                       │
                                       ▼
                               "Worker finished"
                                       │
                                       ▼
                                  run() ends
                                       │
                                       ▼
                                  TERMINATED*/